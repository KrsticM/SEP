import React from 'react';
import Sidebar from '../Sidebar';
import AppBar from '../AppBar';

function withSidebar(ComposedComponent) {
  return (props) => (
    <React.Fragment>
      <Sidebar />
      <AppBar />
      <div className="withsidebar_layout">
        <ComposedComponent
          {...props}
        />
      </div>
    </React.Fragment>
  );
}

export default withSidebar;
