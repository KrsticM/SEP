import React from 'react';
import { withRouter } from 'react-router-dom';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';

function ButtonAppBar({ history }) {
  const isLoggedIn = sessionStorage.getItem('authUser');
  return (
    <div className="payme__appbar">
      <AppBar position="fixed" color="inherit">
        <Toolbar>
          {
            !isLoggedIn && (
              <Button
                color="primary"
                onClick={() => history.push('/login')}
              >
                Login
              </Button>
            )
          }
          {
            !isLoggedIn && (
              <Button
                color="primary"
                variant="outlined"
                onClick={() => history.push('/signup')}
              >
                Register
              </Button>
            )
          }
        </Toolbar>
      </AppBar>
    </div>
  );
}

export default withRouter(ButtonAppBar);
