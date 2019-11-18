import React from 'react';
import Link from '@material-ui/core/Link';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import './Signup.css';

function Signup() {
  return (
    <div className="signup__container">
      <img
        className="signup__logo"
        src={require('../../assets/logo.png')}
      />
      <form className="form__container">
        <TextField
          label="First name"
          required
        />
        <TextField
          label="Last name"
          required
        />
        <TextField
          label="Organization name"
        />
        <TextField
          label="Email address"
          required
        />
        <TextField
          label="Website"
          required
        />
        <div className="signup__actions">
          <Button variant="contained" color="primary">
            Register
          </Button>
          <Link>
            Have an account? Log in
          </Link>
        </div>
      </form>
    </div>
  );
}

export default Signup;
