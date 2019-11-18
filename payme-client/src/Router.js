import React from 'react';
import {
  BrowserRouter as AppRouter,
  Switch,
  Route
} from 'react-router-dom';

import Landing from './containers/Landing';
import Documentation from './containers/Docs';
import Signup from './containers/Signup';
import Login from './containers/Login';

const AuthRoute = ({path, children}) => {
  return (
    <Route path={path}>
      {children}
      <img
        className="paymee__bg"
        src={require('./assets/bg.png')}
      />
    </Route>
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
        <AuthRoute path="/">
          <Landing />
        </AuthRoute>
      </Switch>
    </AppRouter>
  );
}

export default Router;
