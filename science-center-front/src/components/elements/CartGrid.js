import React from 'react';

import { StyledGrid } from '../style/StyledGrid'
import { StyledCartItem } from '../style/StyledCartItem'

const CartGrid = ( {children} ) => (
    <StyledGrid>
        <StyledCartItem>{children}</StyledCartItem>
    </StyledGrid>
)

export default CartGrid;