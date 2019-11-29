import React from 'react';
import { withRouter } from 'react-router-dom';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import withSidebar from '../../../components/Sidebar/withSidebar';

function ApiRefs(props) {
  return (
    <Card className="card__container smaller__container">
      <CardContent>
        <Typography variant="h5" component="h2" color="primary">
          {"API references"}
        </Typography>
        <br />
        <br />
        <Typography variant="body2" component="p" >
          {"To be implemented"}
        </Typography>
      </CardContent>
    </Card>
  );
}

export default withRouter(
  withSidebar(ApiRefs)
);
