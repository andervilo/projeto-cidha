import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/comarca">
      <Translate contentKey="global.menu.entities.comarca" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/quilombo">
      <Translate contentKey="global.menu.entities.quilombo" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/processo">
      <Translate contentKey="global.menu.entities.processo" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/concessao-liminar">
      <Translate contentKey="global.menu.entities.concessaoLiminar" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-decisao">
      <Translate contentKey="global.menu.entities.tipoDecisao" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/concessao-liminar-cassada">
      <Translate contentKey="global.menu.entities.concessaoLiminarCassada" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
