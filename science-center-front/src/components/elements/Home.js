import React, { useState, useEffect } from 'react';
import NoImage from '../../images/no_image.jpg';
import Grid from '../../components/elements/Grid';
import Item from '../../components/elements/Item';

const Home = ( ) => {

    const [state, setState] = useState({items: []});

    console.log(state);

    const fetchItems = async endpoint => {

        try {
            const result = await (await fetch(endpoint)).json();
            console.log(result);
            setState(prev => ({
                ...prev,
                items: [...result]
            }))
        } catch(error) {
            console.log(error);
        }
    }

    useEffect(() => {
        fetchItems('http://localhost:8008/item');
    }, [])

    return (<Grid>
         
        {state.items.map(item => {
            console.log(item);
            console.log(item.imageUrl)
            return (<Item
                key ={item.id}
                image = {
                  item.imageUrl ? item.imageUrl : NoImage
                }
                itemId = {item.id}
                itemName = {item.name}
                itemPrice = {item.price}
                itemMerchantId = {item.merchantId}
                item = {item}
              />)
              })}

    </Grid>)
}
    


export default Home;