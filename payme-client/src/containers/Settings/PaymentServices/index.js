import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import { toast } from 'react-toastify';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Icon from '@material-ui/core/Icon';

function PaymentServices(props) {
  const [state, setState] = useState({
    availableMethods: [],
    loaded: false
  });

  useEffect(() => {
    if (state.loaded) {
      return;
    }
    axios.get('http://localhost:8100/payment/all-services')
      .then((response) => {
        const { data } = response;
        setState({
          availableMethods: data,
          loaded: true
        });
      })
      .catch((error) => {
        toast.error(error.message || 'Unspecified error occured', {
          autoClose: 3000,
        });
      });
  }, [state.loaded]);

  if (!state.loaded) {
    // render spinner
    return null;
  }

  return (
    <div>
      Available payment methods
      {
        state.availableMethods.map((item) => (
          <div
            key={item}
            style={{flexDirection: 'row'}}
          >
            {item}
          </div>
        ))
      }
    </div>
  );
}

export default withRouter(PaymentServices);
