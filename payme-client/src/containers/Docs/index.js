import React from 'react';
import { withRouter } from 'react-router-dom';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import withSidebar from '../../components/Sidebar/withSidebar';

function Docs(props) {
  return (
    <Card className="card__container smaller__container">
      <CardContent>
        <Typography variant="h5" component="h2" color="primary">
          {"Getting started"}
        </Typography>
        <br />
        <br />
        <Typography variant="body2" component="p" >
          {"To get started using PayMe API, you first need to register an account if you don't have one."}
          {"You can do so by following "}<a href="/signup">this link</a>{"."}
        </Typography>
        <br />
        <Typography variant="body2" component="p" >
          {"After registering, you need to add new application. You will then be able to configure payment methods for your applications."}
          {"This app id will then be further used for further payment management."}
          {"Please take care of how you store your app ids."}
        </Typography>
        <div
          className="card__button"
        >
          <Button
            variant="contained"
            color="primary"
            onClick={() => props.history.push('/settings/applications')}
          >
            See my apps
          </Button>
        </div>
        <br />
        <br />
        <Typography variant="body2" component="p" color="primary">
          {"You can find API documentation by following "}<a href="/documentation">next link</a>{"."}
        </Typography>
      </CardContent>
    </Card>
  );
}

export default withRouter(
  withSidebar(Docs)
);
