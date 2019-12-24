import React from 'react';
import {
  BrowserRouter as AppRouter,
  Switch,
  Redirect,
  Route
} from 'react-router-dom';

import Sidebar from './components/Sidebar';
import Landing from './containers/Landing';
import GettingStarted from './containers/Docs';
import ApiRefs from './containers/Docs/ApiRefs';
import Signup from './containers/Signup';
import Login from './containers/Login';
import PaymentServices from './containers/Settings/PaymentServices';
import Applications from './containers/Settings/Applications';
import TokenGenerating from './containers/Settings/TokenGenerating';

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
              <Sidebar />
              <div className="withsidebar_layout">
                {children}
              </div>
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
        <Route path="/get_started" component={GettingStarted} />
        <Route path="/documentation" component={ApiRefs} />
        <AuthRoute path="/signup">
          <Signup />
        </AuthRoute>
        <AuthRoute path="/login">
          <Login />
        </AuthRoute>
        <PrivateRoute path="/settings/applications/:appId">
          <PaymentServices />
        </PrivateRoute>
        <PrivateRoute path="/settings/applications">
          <Applications />
        </PrivateRoute>
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
