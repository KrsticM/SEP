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

function Applications(props) {
  const [state, setState] = useState({
    applications: [],
    loaded: false
  });

  useEffect(() => {
    if (state.loaded) {
      return;
    }
    loadApps();
  }, [state.loaded]);

  const loadApps = () => {
    axios.get('http://localhost:8762/payment-concentrator/app')
      .then(async (response) => {
        const { data } = response;
        console.log(data);
        const applications = data.map((application) => ({
          name: application.name,
          appId: application.id
        }));
        setState({
          applications,
          loaded: true
        });
      })
      .catch((error) => {
        toast.error(error.message || 'Unspecified error occured', {
          autoClose: 3000,
        });
      });
  };

  const addApp = (methodName) => {
    var name = prompt("Please enter app name");
    if (!name) {
      alert("No name specified!");
      return;
    }
    axios.post(`http://localhost:8762/payment-concentrator/app`, {
      name
    })
      .then(async (response) => {
        const { status } = response;
        if (status !== 201) {
          toast.error('Unspecified error occured', {
            autoClose: 3000,
          });
          return
        }
        loadApps();
        toast.success("Application added successfully");
      })
      .catch((error) => {
        toast.error(error.message || 'Unspecified error occured', {
          autoClose: 3000,
        });
      });
  }

  const configureApp = (appId) => {
    props.history.push(`/settings/applications/${appId}`);
  }

  const deleteApp = (appId) => {
    axios.delete(`http://localhost:8762/payment-concentrator/app/${appId}`)
      .then(async (response) => {
        const { status } = response;
        if (status !== 200) {
          toast.error('Unspecified error occured', {
            autoClose: 3000,
          });
          return
        }
        loadApps();
        toast.success("Application deleted successfully");
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
        <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', margin: '0 20px 10px 0'}}>
          <Typography variant="h5" component="h2" color="primary">
            {"My applications"}
          </Typography>
          <Button
            variant="contained"
            color="primary"
            onClick={addApp}
          >
            Add application
          </Button>
        </div>
        <List>
          {
            state.applications.map((item) => (
              <ListItem key={item.appId}>
                <ListItemText
                  primary={item.name}
                />
                <ListItemSecondaryAction>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => configureApp(item.appId)}
                  >
                    Configure
                  </Button>
                  <Button
                    variant="contained"
                    color="secondary"
                    onClick={() => deleteApp(item.appId)}
                  >
                    Delete
                  </Button>
                </ListItemSecondaryAction>
              </ListItem>
            ))
          }
        </List>
      </CardContent>
    </Card>
  );
}

export default withRouter(Applications);
