import styled from 'styled-components';

export const StyledHeader = styled.div`
  background: #282c34;
  padding: 0 20px;
  box-sizing: border-box;

  .header-content {
    max-width: 1280px;
    min-height: 120px;
    padding: 20px 0px;
    margin: 0 auto;
    box-sizing: border-box;

    @media screen and (max-width: 500px) {
      max-width: 1280px;
      min-height: 0px;
    }
  }

  p {
    display:inline-block;
    margin-right:400px;
    color: #ffffff;
  }

  
`;
