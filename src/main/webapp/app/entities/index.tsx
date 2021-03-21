import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Comarca from './comarca';
import Quilombo from './quilombo';
import Processo from './processo';
import ConcessaoLiminar from './concessao-liminar';
import TipoDecisao from './tipo-decisao';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}comarca`} component={Comarca} />
      <ErrorBoundaryRoute path={`${match.url}quilombo`} component={Quilombo} />
      <ErrorBoundaryRoute path={`${match.url}processo`} component={Processo} />
      <ErrorBoundaryRoute path={`${match.url}concessao-liminar`} component={ConcessaoLiminar} />
      <ErrorBoundaryRoute path={`${match.url}tipo-decisao`} component={TipoDecisao} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
