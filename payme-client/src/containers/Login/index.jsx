import React, {useState} from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';
import Link from '@material-ui/core/Link';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

function Login(props) {
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

  const handleLogin = async (event) => {
    event.preventDefault();
    if (!validateForm()) {
      return;
    }
    try {
      const { data, status } = await axios.post('http://localhost:8100/auth/login', state);
      if (!data.token || status !== 200) {
        toast.error('Something went wrong');
        return;
      }
      toast.success('Login successful!');
      sessionStorage.setItem('authUser', data.token);
      props.history.replace('/');
    } catch (error) {
      toast.error(error.message || 'Unspecified error occured', {
        autoClose: 3000,
      });
    }
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
          helperText={errors.email}
          required
        />
        <TextField
          label="Password"
          name="password"
          value={state.password}
          onChange={handleChange}
          error={errors.password}
          helperText={errors.password}
          required
          type="password"
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

export default withRouter(Login);
