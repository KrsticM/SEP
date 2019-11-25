import React from 'react';
import { ToastContainer } from 'react-toastify';
import Router from './Router';
import 'react-toastify/dist/ReactToastify.css';
import './App.css';

function App() {
  return (
    <div>
      <Router />
      <ToastContainer />
    </div>
  );
}

export default App;
