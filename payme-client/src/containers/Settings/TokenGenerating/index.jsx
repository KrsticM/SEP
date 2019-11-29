import React, { useState } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import { toast } from 'react-toastify';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import ConfirmDialog from '../../../components/ConfirmDialog';
import TokenDialog from './TokenDialog';
import './TokenGenerating.css';

function GenerateToken(props) {
  const [errors, setErrors] = useState({});
  const [open, setOpen] = useState(false);
  const [apiKey, setGenerated] = useState(undefined);

  const handleToggleConfirm = () => {
    setOpen(!open);
  };

  const handleFinishGenerate = () => {
    setGenerated(undefined);
  };

  const handleGenerateToken = async (event) => {
    event.preventDefault();
    axios.post('http://localhost:8100/user/regenerate-token')
      .then((response) => {
        const { data, status } = response;
        if (status !== 200) {
          toast.error('Something went wrong', {
            autoClose: 3000,
          });
          return;
        }
        setOpen(false);
        setGenerated(data);
      })
      .catch((error) => {
        toast.error(error.message || 'Unspecified error occured', {
          autoClose: 3000,
        });
      });
  };

  return (
    <Card className="card__container smaller__container">
      <CardContent>
        <Typography variant="h5" component="h2" color="primary">
          {"Generate new API key"}
        </Typography>
        <br />
        <br />
        <Typography variant="body2" component="p">
          {
            "Use this API key to access our endpoints and make payments."
          }
        </Typography>
        <Typography variant="body2" component="p">
          {
            "Please take care of how you store your API keys."
          }
        </Typography>
        <Typography variant="body2" component="p">
          {
            "Once API key is generated, the one you had before will stop working."
          }
        </Typography>
        <Typography variant="body2" component="p">
          {
            "You can not access API key again after it's generated, so make sure to note it down."
          }
        </Typography>
      </CardContent>
      <CardActions>
        <Button
          variant="contained"
          color="primary"
          onClick={handleToggleConfirm}
        >
          {"Generate new API KEY"}
        </Button>
      </CardActions>
      <ConfirmDialog
        open={open}
        dialogTitle="Generate new API KEY?"
        handleClose={handleToggleConfirm}
        handleConfirm={handleGenerateToken}
      >
        {
          "Once API key is generated, the one you had before will stop working."
        }
        <br/>
        {
          "You can not access API key again after it's generated, so make sure to note it down."
        }
      </ConfirmDialog>
      <TokenDialog
        apiKey={apiKey}
        handleClose={handleFinishGenerate}
      />
    </Card>
  );
}

export default withRouter(GenerateToken);
