import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

function ConfirmDialog(props) {
  const {
    open, handleClose, handleConfirm, children, dialogTitle
  } = props;
  return (
    <Dialog
      open={open}
      onClose={handleClose}
    >
      <DialogTitle>
        {dialogTitle}
      </DialogTitle>
      <DialogContent>
        <DialogContentText>
          { children }
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="primary">
          Cancel
        </Button>
        <Button onClick={handleConfirm} color="primary" autoFocus>
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default ConfirmDialog;
