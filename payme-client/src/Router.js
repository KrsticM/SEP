import React from 'react';
import {
  BrowserRouter as AppRouter,
  Switch,
  Redirect,
  Route
} from 'react-router-dom';

import Landing from './containers/Landing';
import Documentation from './containers/Docs';
import Signup from './containers/Signup';
import Login from './containers/Login';

const AuthRoute = ({ path, children }) => {
  const isAuth = sessionStorage.getItem('authUser');
  return (
    <Route
      path={path}
      render={() =>
        isAuth
          ? (<Redirect to="/" />)
          : (
            <React.Fragment>
              {children}
              <img
                className="paymee__bg"
                src={require('./assets/bg.png')}
              />
            </React.Fragment>
          )
      }
    />
  );
}

const PrivateRoute = ({ path, children }) => {
  const isAuth = sessionStorage.getItem('authUser');
  return (
    <Route
      path={path}
      render={() =>
        !isAuth
          ? (<Redirect to="/login" />)
          : (
            <React.Fragment>
              {children}
            </React.Fragment>
          )
      }
    />
  );
}

function Router() {
  return (
    <AppRouter>
      <Switch>
        <Route path="/documentation" component={Documentation} />
        <AuthRoute path="/signup">
          <Signup />
        </AuthRoute>
        <AuthRoute path="/login">
          <Login />
        </AuthRoute>
        <Route path="/">
          <Landing />
          <img
            className="paymee__bg"
            src={require('./assets/bg.png')}
          />
        </Route>
      </Switch>
    </AppRouter>
  );
}

export default Router;
