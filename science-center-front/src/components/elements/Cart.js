import React, {useEffect, useState} from 'react';
import CartGrid from '../elements/CartGrid';
import CartItem from '../elements/CartItem';
import axios from 'axios';

const Cart = ( ) => {

    const [state, setState] = useState({items: [], merchants: []});


    const fetchItems = () => {
        var results = JSON.parse(sessionStorage.getItem('itemsInCart'));   

        console.log("R: " , results);
        var merchantsResult = new Set();
        for(let result of results) {
            console.log("in for: ",  result)
            merchantsResult.add(result.merchantId)
        }


        var newResult = [];
        for(let m of merchantsResult) {
            newResult.push(m);
        }


        console.log(newResult)

        setState(prev => ({
            ...prev,
            items: [...results],
            merchants: [...newResult]
        }))
    }

    useEffect(() => {
        fetchItems();
    }, [])

    return (
        <CartGrid>
            <h2>Korpa:</h2>
        {state.merchants.map(merchant => {
            
            var merchantItems = [];
            for(let item of state.items) {
                if(item.merchantId === merchant) {
                    merchantItems.push(item);
                }
            }

            console.log('Proizvodi prodavca: ' + merchant);
            console.log(merchantItems);

            function buy(e) {
                e.preventDefault();
                console.log('The button BUY clicked.');
                console.log(merchantItems);

                const config = {
                    headers: {
                      'Content-Type': 'application/json'
                    },
                  };

                var itemsIds = [];
                for(let i of merchantItems) {
                    itemsIds.push(i.id);
                }

                axios.post('http://localhost:8008/item/pay', 
                    JSON.stringify({"ids": itemsIds}), 
                    config)
                  .then(function (response) {
                    console.log("Odgovor!");
                    console.log(response);
                    window.location.href = response.data;
                    
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
                
            }

            return (
                <div key={merchant}>
                    <h4>Prodavac: {merchant}</h4>
                    <CartItem 
                    key ={merchant}
                    items = {merchantItems}
                    />
                    <hr></hr>
                    <button onClick={buy}>Buy from this seller</button>
                </div>
                
            )
        })}
        </CartGrid>
    )

}

export default Cart;