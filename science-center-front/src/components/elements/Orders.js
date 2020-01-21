import React, { useState, useEffect } from 'react';
//import { StyledGrid, StyledGridContent } from '../style/StyledGrid';

const Orders = ( {children} ) => {

    const [state, setState] = useState({orders: []});

    console.log(state);

    const fetchItems = async endpoint => {

        try {
            const result = await (await fetch(endpoint)).json();
            console.log(result);
            setState(prev => ({
                ...prev,
                orders: [...result]
            }))
        } catch(error) {
            console.log(error);
        }
    }

    useEffect(() => {
        fetchItems('http://localhost:8008/order');
    }, [])

    return (<table>
        <thead>
            <tr><th>order_id</th><th>order_price</th><th>order_status</th></tr>
        </thead>
         <tbody>
        {state.orders.map(order => {
            return (<tr key ={order.id}>
                <td>{order.id}</td>
                <td>{order.price}</td>
                <td>{order.status}</td>
                </tr>)
              })}
        </tbody>
    </table>)
}

export default Orders;