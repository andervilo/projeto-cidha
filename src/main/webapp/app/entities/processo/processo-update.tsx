import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IComarca } from 'app/shared/model/comarca.model';
import { getEntities as getComarcas } from 'app/entities/comarca/comarca.reducer';
import { IQuilombo } from 'app/shared/model/quilombo.model';
import { getEntities as getQuilombos } from 'app/entities/quilombo/quilombo.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './processo.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProcessoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessoUpdate = (props: IProcessoUpdateProps) => {
  const [idscomarca, setIdscomarca] = useState([]);
  const [idsquilombo, setIdsquilombo] = useState([]);
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { processoEntity, comarcas, quilombos, loading, updating } = props;

  const { assunto } = processoEntity;

  const handleClose = () => {
    props.history.push('/processo' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getComarcas();
    props.getQuilombos();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...processoEntity,
        ...values,
        comarcas: mapIdList(values.comarcas),
        quilombos: mapIdList(values.quilombos),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cidhaApp.processo.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.processo.home.createOrEditLabel">Create or edit a Processo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : processoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="processo-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="processo-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="oficioLabel" for="processo-oficio">
                  <Translate contentKey="cidhaApp.processo.oficio">Oficio</Translate>
                </Label>
                <AvField id="processo-oficio" type="text" name="oficio" />
              </AvGroup>
              <AvGroup>
                <Label id="assuntoLabel" for="processo-assunto">
                  <Translate contentKey="cidhaApp.processo.assunto">Assunto</Translate>
                </Label>
                <AvInput id="processo-assunto" type="textarea" name="assunto" />
              </AvGroup>
              <AvGroup>
                <Label id="linkUnicoLabel" for="processo-linkUnico">
                  <Translate contentKey="cidhaApp.processo.linkUnico">Link Unico</Translate>
                </Label>
                <AvField id="processo-linkUnico" type="text" name="linkUnico" />
              </AvGroup>
              <AvGroup>
                <Label id="secaoJudiciariaLabel" for="processo-secaoJudiciaria">
                  <Translate contentKey="cidhaApp.processo.secaoJudiciaria">Secao Judiciaria</Translate>
                </Label>
                <AvField id="processo-secaoJudiciaria" type="text" name="secaoJudiciaria" />
              </AvGroup>
              <AvGroup>
                <Label for="processo-comarca">
                  <Translate contentKey="cidhaApp.processo.comarca">Comarca</Translate>
                </Label>
                <AvInput
                  id="processo-comarca"
                  type="select"
                  multiple
                  className="form-control"
                  name="comarcas"
                  value={processoEntity.comarcas && processoEntity.comarcas.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {comarcas
                    ? comarcas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-quilombo">
                  <Translate contentKey="cidhaApp.processo.quilombo">Quilombo</Translate>
                </Label>
                <AvInput
                  id="processo-quilombo"
                  type="select"
                  multiple
                  className="form-control"
                  name="quilombos"
                  value={processoEntity.quilombos && processoEntity.quilombos.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {quilombos
                    ? quilombos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/processo" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  comarcas: storeState.comarca.entities,
  quilombos: storeState.quilombo.entities,
  processoEntity: storeState.processo.entity,
  loading: storeState.processo.loading,
  updating: storeState.processo.updating,
  updateSuccess: storeState.processo.updateSuccess,
});

const mapDispatchToProps = {
  getComarcas,
  getQuilombos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProcessoUpdate);
