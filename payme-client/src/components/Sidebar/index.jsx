import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';
import Drawer from '@material-ui/core/Drawer';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import './Sidebar.css';

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
    background: theme.palette.primary.dark,
  },
  divider: {
    background: 'rgba(255,255,255,0.2)',
    margin: '0 10px'
  },
  linkText: {
    color: 'white'
  }
}));

function Sidebar() {
  const classes = useStyles();
  const isLoggedIn = sessionStorage.getItem('authUser');
  const handleLogout = () => {
    sessionStorage.removeItem('authUser');
    window.location.reload();
  }
  return (
    <Drawer
      className={classes.drawer}
      variant="permanent"
      classes={{
        paper: classes.drawerPaper,
      }}
      anchor="left"
    >
      <List>
        <ListItem>
          <Link href="/get_started">
            <ListItemText className={classes.linkText}>
              Get started
            </ListItemText>
          </Link>
        </ListItem>
      </List>
      <Divider className={classes.divider} />
      {
        !!isLoggedIn && (
          <React.Fragment>
            <List>
              <ListItem>
                <Link href="/settings/applications">
                  <ListItemText className={classes.linkText}>
                    My applications
                  </ListItemText>
                </Link>
              </ListItem>
            </List>
            <Divider className={classes.divider} />
          </React.Fragment>
        )
      }
      <List>
        <ListItem>
          <Link href="/documentation">
            <ListItemText className={classes.linkText}>
              API References
            </ListItemText>
          </Link>
        </ListItem>
      </List>
      {
        !!isLoggedIn && (
          <div className="sidebar__signout">
            <List>
              <ListItem onClick={handleLogout}>

                  <ListItemText className={classes.linkText}>
                    Log out
                  </ListItemText>
              </ListItem>
            </List>
          </div>
        )
      }
    </Drawer>
  );
}

export default Sidebar;
