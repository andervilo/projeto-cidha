import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Comarca from './comarca';
import Quilombo from './quilombo';
import Processo from './processo';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}comarca`} component={Comarca} />
      <ErrorBoundaryRoute path={`${match.url}quilombo`} component={Quilombo} />
      <ErrorBoundaryRoute path={`${match.url}processo`} component={Processo} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
