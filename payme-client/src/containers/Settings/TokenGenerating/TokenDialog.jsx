import React, { useRef } from 'react';
import { toast } from 'react-toastify';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import InputBase from '@material-ui/core/InputBase';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import CopyIcon from '@material-ui/icons/FileCopy';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles(theme => ({
  root: {
    margin: '20px 15px',
    padding: '2px 4px',
    display: 'flex',
    alignItems: 'center'
  },
  input: {
    marginLeft: theme.spacing(1),
    flex: 1,
  },
  iconButton: {
    padding: 10,
  },
  divider: {
    height: 28,
    margin: 4,
  },
}));

function TokenDialog(props) {
  const classes = useStyles();
  const inputEl = useRef(null);
  const {
    apiKey, handleClose
  } = props;

  const copyToClipboard = (e) => {
    inputEl.current.select()
    document.execCommand('copy');

    toast.info('API key copied successfully!', {
      position: toast.POSITION_TOP_CENTER
    });
    if (window.getSelection) {
      // All browsers, except IE <=8
      window.getSelection().removeAllRanges();
    } else if (document.selection) {
      // IE <=8
      document.selection.empty();
    }
  };

  return (
    <Dialog
      open={!!apiKey}
      onClose={handleClose}
    >
      <DialogTitle>
        <Typography variant="h5" component="h2" color="primary">
          {"Here's your new API key"}
        </Typography>
      </DialogTitle>
      <DialogContent>
        <Typography variant="body2" component="p">
          {"Make sure you note it down and update your previously used API key."}
        </Typography>
        <Paper component="form" className={classes.root}>
          <InputBase
            inputRef={inputEl}
            className={classes.input}
            value={apiKey}
            readOnly
            inputProps={{ 'aria-label': 'search google maps' }}
          />
          <Divider className={classes.divider} orientation="vertical" />
          <IconButton onClick={copyToClipboard} color="primary" className={classes.iconButton}>
            <CopyIcon />
          </IconButton>
        </Paper>
        <Typography variant="body2" component="p">
          <Typography component="p" color="primary">
            WARNING!
          </Typography>
          {"This is last time you'll be seeing this API key."}
        </Typography>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="primary">
          Close
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default TokenDialog;
