import React, {useState} from 'react';
import Link from '@material-ui/core/Link';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

function Login() {
  const [state, setState] = useState({
    email: '',
    password: ''
  });

  const [errors, setErrors] = useState({});

  const handleChange = (event) => {
    const { name, value } = event.target;
    setState({
      ...state,
      [name]: value
    });
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: undefined
      });
    }
  };

  const validateForm = () => {
    const validationErrors = {};
    let isValid = true;
    if (!state.email) {
      validationErrors.email = "Email required";
      isValid = false;
    }
    if (!state.password) {
      validationErrors.password = "Password required";
      isValid = false;
    }
    setErrors(validationErrors);
    return isValid;
  };

  const handleLogin = (event) => {
    event.preventDefault();
    if (!validateForm()) {
      return;
    }
    // handle login afterwards
  };

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
          name="email"
          value={state.email}
          onChange={handleChange}
          error={errors.email}
          required
        />
        <TextField
          label="Password"
          name="password"
          value={state.password}
          onChange={handleChange}
          error={errors.password}
          required
        />
        <div className="signup__actions">
          <Button
            variant="contained"
            color="primary"
            onClick={handleLogin}
          >
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
