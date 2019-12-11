import React from 'react';

const CartItemArticle = ( {item} ) => {

    return (
       <p>Proizvod: {item.name} -  Cena: {item.price} €</p> 
    )
}
    
export default CartItemArticle;