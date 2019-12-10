import React, {useEffect, useState} from 'react';

const Cart = ( ) => {

    const [state, setState] = useState({items: []});

    const fetchItems = () => {
        var results = JSON.parse(sessionStorage.getItem('itemsInCart'));   

        console.log("R: " , results);
        var merchants = new Set();
        for(let result of results) {
            console.log("in for: ",  result)
            merchants.add(result.merchantId)
        }

        console.log(merchants)

        setState(prev => ({
            ...prev,
            items: [...results]
        }))
    }

    useEffect(() => {
        fetchItems();
    }, [])

    return (
        <div>
        {state.items.map(item => {
            console.log(item);
        })}</div>
    )

}

export default Cart;