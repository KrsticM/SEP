import React from 'react';
import {
  BrowserRouter as AppRouter,
  Switch,
  Route
} from 'react-router-dom';

import Landing from './containers/Landing';
import Signup from './containers/Signup';

function Router() {
  return (
    <AppRouter>
      <Switch>
        <Route path="/signup">
          <Signup />
        </Route>
        <Route path="/">
          <Landing />
        </Route>
      </Switch>
    </AppRouter>
  );
}

export default Router;
