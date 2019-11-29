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
  const isLoggedIn = sessionStorage.getItem('auth');
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
          <Link href="/documentation">
            <ListItemText className={classes.linkText}>
              Get started
            </ListItemText>
          </Link>
        </ListItem>
      </List>
      <Divider className={classes.divider} />
      {
        !isLoggedIn && (
          <React.Fragment>
            <List>
              <ListItem>
                <ListItemText className={classes.linkText}>
                  My application
                </ListItemText>
              </ListItem>
              <ListItem>
                <List>
                  <ListItem>
                    <Link href="/settings/payment_methods">
                      <ListItemText className={classes.linkText}>
                        Payment methods
                      </ListItemText>
                    </Link>
                  </ListItem>
                  <ListItem>
                    <Link href="/settings/generate_token">
                      <ListItemText className={classes.linkText}>
                        Settings
                      </ListItemText>
                    </Link>
                  </ListItem>
                </List>
              </ListItem>
            </List>
            <Divider className={classes.divider} />
          </React.Fragment>
        )
      }
      <List>
        <ListItem>
          <ListItemText className={classes.linkText}>
            API References
          </ListItemText>
        </ListItem>
      </List>
    </Drawer>
  );
}

export default Sidebar;
