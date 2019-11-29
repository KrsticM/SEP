import React from 'react';
import Sidebar from '../Sidebar';

function withSidebar(children) {
  return () => (
    <React.Fragment>
      <Sidebar />
      <div className="withsidebar_layout">
        {children}
      </div>
    </React.Fragment>
  );
}

export default withSidebar;
