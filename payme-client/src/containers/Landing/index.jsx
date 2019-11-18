import React from 'react';
import Button from '@material-ui/core/Button';
import './Landing.css';

function Landing() {
  return (
    <div className="landing__container">
      <img
        className="landing__logo"
        src={require('../../assets/logo.png')}
      />
      <div className="landing__buttons">
        <Button variant="outlined" color="primary">
          View documentation
        </Button>
        <Button variant="contained" color="primary">
          Go to dashboard
        </Button>
      </div>
    </div>
  );
}

export default Landing;
