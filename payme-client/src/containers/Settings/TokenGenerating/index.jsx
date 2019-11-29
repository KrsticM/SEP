import React, { useState } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import { toast } from 'react-toastify';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Icon from '@material-ui/core/Icon';

function GenerateToken(props) {
  const [errors, setErrors] = useState({});


  const handleGenerateToken = async (event) => {
    event.preventDefault();
      // const { data, status } = await axios.post('http://localhost:8100/auth/register', state);
  };

  return (
    <div className="token__container">
      <Button
        variant="contained"
        color="primary"
        onClick={handleGenerateToken}
      >
        {"Generate new token"}
      </Button>
    </div>
  );
}

export default withRouter(GenerateToken);
