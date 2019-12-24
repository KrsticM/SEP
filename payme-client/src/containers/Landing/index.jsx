import React from 'react';
import { withRouter } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import './Landing.css';

function Landing(props) {

  const handleViewDocumentation = () => {
    props.history.push('/get_started');
  };

  const handleGoToDashboard = () => {
    const jwt = sessionStorage.getItem('authUser');
    if (!jwt) {
      props.history.push('/login');
    } else {
      props.history.push('/get_started');
    }
  };

  return (
    <div className="landing__container">
      <img
        className="landing__logo"
        src={require('../../assets/logo.png')}
      />
      <div className="landing__buttons">
        <Button
          variant="outlined"
          color="primary"
          onClick={handleViewDocumentation}
        >
          View documentation
        </Button>
        <Button
          variant="contained"
          color="primary"
          onClick={handleGoToDashboard}
        >
          Go to dashboard
        </Button>
      </div>
    </div>
  );
}

export default withRouter(Landing);
