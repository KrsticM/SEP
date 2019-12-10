import React from 'react';
import { StyledHeader } from '../style/StyledHeader';
import { Link } from '@reach/router';

const Header = () => {    
    return (
    <StyledHeader>
        <Link to="/"><p>Prodaj brzo kupi lako</p></Link>
        
        <Link to="/cart">
            <button>Korpa</button>
        </Link>
       
    </StyledHeader>  
    )
}

export default Header;