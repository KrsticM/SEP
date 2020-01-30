import React from 'react';
import './App.css';
import Header from './components/elements/Header'
import { Router } from '@reach/router';
import Home from './components/elements/Home'
import Cart from './components/elements/Cart'
import Orders from './components/elements/Orders';

function App() {

  return (
    <div className="App">
      <header className="App-header">
        <Header></Header>
      </header>
      <div className="App-body">
        <Router>
          <Home path="/"/>
          <Cart path="/cart"/>
          <Orders path = "/completed"/>
        </Router>
        
      </div>
    </div>
  );
}

export default App;



