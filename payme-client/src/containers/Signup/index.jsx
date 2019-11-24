import React, { useState } from 'react';
import Link from '@material-ui/core/Link';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
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

function Signup() {
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

  const handleRegistration = (event) => {
    event.preventDefault();
    if (!validateForm()) {
      return;
    }
    // handle registration afterwards
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
              onChange={handleChange}
              required
            />
          ))
        }
        <TextField
          label="Organization name"
          name="organizationName"
          value={state.organizationName}
          onChange={handleChange}
        />
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

export default Signup;
