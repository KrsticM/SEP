import React, { useState } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import { toast } from 'react-toastify';
import Link from '@material-ui/core/Link';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';
import Icon from '@material-ui/core/Icon';
import './Signup.css';

const AUTHORITY = {
  PERSONAL: 'PERSONAL',
  ENTERPRISE: 'ENTERPRISE'
};

const requiredFields = [
  {
    value: 'email',
    label: 'Email'
  },
  {
    value: 'firstName',
    label: 'First name'
  },
  {
    value: 'lastName',
    label: 'Last name'
  },
  {
    value: 'password',
    label: 'Password'
  },
  {
    value: 'website',
    label: 'Website'
  }
];

function Signup(props) {
  const [state, setState] = useState({
    firstName: '',
    lastName: '',
    organizationName: '',
    email: '',
    password: '',
    website: '',
    authority: AUTHORITY.PERSONAL
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

  const selectAuthority = (type) => () => {
    setState({
      ...state,
      authority: type
    });
  }

  const validateForm = () => {
    const validationErrors = {};
    let isValid = true;
    requiredFields.forEach((field) => {
      if (!state[field.value]) {
        validationErrors[field.value] = `${[field.label]} required`;
        isValid = false;
      }
    });
    setErrors(validationErrors);
    return isValid;
  };

  const handleRegistration = async (event) => {
    event.preventDefault();
    if (!validateForm()) {
      return;
    }
    try {
      const { data, status } = await axios.post('http://localhost:8100/auth/register', state);
      if (status !== 200) {
        toast.error('Something went wrong');
        return;
      }
      toast.success('Registration successful! You can now log into your account.');
      props.history.replace('/login');
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
        {
          requiredFields.map((field) => (
            <TextField
              key={field.value}
              label={field.label}
              name={field.value}
              value={state[field.value]}
              error={errors[field.value]}
              helperText={errors[field.value]}
              onChange={handleChange}
              required
              type={(field.value === "password")
                ? 'password'
                : 'text'
              }
            />
          ))
        }
        <TextField
          label="Organization name"
          name="organizationName"
          value={state.organizationName}
          onChange={handleChange}
        />
        <FormControl component="fieldset">
          <FormLabel required>Account type</FormLabel>
          <ButtonGroup size="large">
            <Button
              variant="outlined"
              color={(state.authority === AUTHORITY.PERSONAL) ? 'primary' : 'default'}
              startIcon={<Icon>person</Icon>}
              onClick={selectAuthority(AUTHORITY.PERSONAL)}
            >
              Personal
            </Button>
            <Button
              variant="outlined"
              color={(state.authority === AUTHORITY.ENTERPRISE) ? 'primary' : 'default'}
              startIcon={<Icon>account_balance</Icon>}
              onClick={selectAuthority(AUTHORITY.ENTERPRISE)}
            >
              Enterprise
            </Button>
          </ButtonGroup>
        </FormControl>
        <div className="signup__actions">
          <Button
            variant="contained"
            color="primary"
            onClick={handleRegistration}
          >
            Register
          </Button>
          <Link href="/login">
            Have an account? Log in
          </Link>
        </div>
      </form>
    </div>
  );
}

export default withRouter(Signup);
