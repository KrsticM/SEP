import React from 'react';

import { StyledItem } from '../style/StyledItem';

const Item = ({ image, itemId, itemName, itemPrice, item }) => {

    function addToCart(e) {
        e.preventDefault();
        console.log('The button was clicked.');
        var items = [];

        if(sessionStorage.itemsInCart) {
            items = JSON.parse(sessionStorage.getItem('itemsInCart'));
            console.log(items);
        }
    
        items.push(item);
        sessionStorage.setItem('itemsInCart', JSON.stringify(items));
        alert("Uspešno dodato u korpu!");
    }

    console.log("item image: "  + image)
    return (
        <StyledItem>
            <img width="280" height="340" src={image} alt="itemthumb"/>
            <p>{itemName}</p> <p>{itemPrice}€</p> <button onClick={addToCart}>Add to cart</button>
        </StyledItem>
    )
}
    


export default Item;