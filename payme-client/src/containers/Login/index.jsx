import React from 'react';
import Link from '@material-ui/core/Link';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

function Login() {
  return (
    <div className="signup__container">
      <Link href="/">
        <img
          className="signup__logo"
          src={require('../../assets/logo.png')}
        />
      </Link>
      <form className="form__container">
        <TextField
          label="Email"
          required
        />
        <TextField
          label="Password"
          required
        />
        <div className="signup__actions">
          <Button variant="contained" color="primary">
            Login
          </Button>
          <Link href="/signup">
            Don't have an account? Register
          </Link>
        </div>
      </form>
    </div>
  );
}

export default Login;
