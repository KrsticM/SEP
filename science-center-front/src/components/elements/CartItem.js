import React from 'react';
import CartItemArticle from './CartItemArticle';

const CartItem = ( {merchant, items} ) => {

    return (
        items.map((item,index) => {
            
            return (<CartItemArticle key={item.id * 100 + index}
            item={item}>

            </CartItemArticle>)
        })
    )
}
    
export default CartItem;