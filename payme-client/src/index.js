import React from 'react';
import axios from 'axios';
import ReactDOM from 'react-dom';
import App from './App';
import * as serviceWorker from './serviceWorker';

const access_token = sessionStorage.getItem('authUser');
if (access_token) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${access_token}`;
}

ReactDOM.render(<App />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
