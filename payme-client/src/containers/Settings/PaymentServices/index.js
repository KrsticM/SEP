import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import { toast } from 'react-toastify';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';

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
      .then(async (response) => {
        const { data } = response;

        const methodResponse = await axios.get('http://localhost:8100/user/my-payment-methods');
        if (!methodResponse.data) {
          toast.error('Unspecified error occured', {
            autoClose: 3000,
          });
          return;
        }
        console.log(methodResponse.data);
        const availableMethods = data.map((service) => ({
          name: service,
          enabled: !!methodResponse.data.find((item) => (item.name === service))
        }));
        setState({
          availableMethods,
          loaded: true
        });
      })
      .catch((error) => {
        toast.error(error.message || 'Unspecified error occured', {
          autoClose: 3000,
        });
      });
  }, [state.loaded]);

  const enableMethod = (methodName) => {
    axios.post(`http://localhost:8100/user/add-method/${methodName}`)
      .then(async (response) => {
        const { status } = response;
        if (status !== 200) {
          toast.error('Unspecified error occured', {
            autoClose: 3000,
          });
          return
        }
        const availableMethods = state.availableMethods.map((method) => ({
          name: method.name,
          enabled: (method.name === methodName) ? true : method.enabled
        }));
        setState({
          availableMethods,
          loaded: true
        });
        toast.success("Payment method disabled successfully");
      })
      .catch((error) => {
        toast.error(error.message || 'Unspecified error occured', {
          autoClose: 3000,
        });
      });
  }

  const disableMethod = (methodName) => {
    axios.delete(`http://localhost:8100/user/remove-method/${methodName}`)
      .then(async (response) => {
        const { status } = response;
        if (status !== 200) {
          toast.error('Unspecified error occured', {
            autoClose: 3000,
          });
          return
        }
        const availableMethods = state.availableMethods.map((method) => ({
          name: method.name,
          enabled: (method.name === methodName) ? false : method.enabled
        }));
        setState({
          availableMethods,
          loaded: true
        });
        toast.success("Payment method disabled successfully");
      })
      .catch((error) => {
        toast.error(error.message || 'Unspecified error occured', {
          autoClose: 3000,
        });
      });
  }

  if (!state.loaded) {
    // render spinner
    return null;
  }

  return (
    <Card className="card__container">
      <CardContent>
        <Typography variant="h5" component="h2" color="primary">
          {"Available payment methods"}
        </Typography>
        <List>
          {
            state.availableMethods.map((item) => (
              <ListItem key={item.name}>
                <ListItemText
                  primary={item.name}
                  secondary={item.enabled ? 'Method enabled' : undefined}
                />
                <ListItemSecondaryAction>
                  {
                    item.enabled
                      ? (
                        <React.Fragment>
                          <Button
                            variant="contained"
                            color="primary"
                            disabled
                          >
                            Configure
                          </Button>
                          <Button
                            variant="contained"
                            color="secondary"
                            onClick={() => disableMethod(item.name)}
                          >
                            Disable
                          </Button>
                        </React.Fragment>
                      )
                      : (
                        <Button
                          variant="contained"
                          color="primary"
                          onClick={() => enableMethod(item.name)}
                        >
                          Enable
                        </Button>
                      )
                  }
                </ListItemSecondaryAction>
              </ListItem>
            ))
          }
        </List>
      </CardContent>
    </Card>
  );
}

export default withRouter(PaymentServices);
