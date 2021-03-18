import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IConcessaoLiminar } from 'app/shared/model/concessao-liminar.model';
import { getEntities as getConcessaoLiminars } from 'app/entities/concessao-liminar/concessao-liminar.reducer';
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
  const [concessaoLiminarId, setConcessaoLiminarId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { processoEntity, concessaoLiminars, comarcas, quilombos, loading, updating } = props;

  const { assunto, numeroProcessoJudicialPrimeiraInstanciaObservacoes } = processoEntity;

  const handleClose = () => {
    props.history.push('/processo' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getConcessaoLiminars();
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
        <Col md="12">
          <h2 id="cidhaApp.processo.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.processo.home.createOrEditLabel">Create or edit a Processo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col  md="12">
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
                <Label id="linkTrfLabel" for="processo-linkTrf">
                  <Translate contentKey="cidhaApp.processo.linkTrf">Link Trf</Translate>
                </Label>
                <AvField id="processo-linkTrf" type="text" name="linkTrf" />
              </AvGroup>
              <AvGroup>
                <Label id="subsecaoJudiciariaLabel" for="processo-subsecaoJudiciaria">
                  <Translate contentKey="cidhaApp.processo.subsecaoJudiciaria">Subsecao Judiciaria</Translate>
                </Label>
                <AvField id="processo-subsecaoJudiciaria" type="text" name="subsecaoJudiciaria" />
              </AvGroup>
              <AvGroup>
                <Label id="turmaTrf1Label" for="processo-turmaTrf1">
                  <Translate contentKey="cidhaApp.processo.turmaTrf1">Turma Trf 1</Translate>
                </Label>
                <AvField id="processo-turmaTrf1" type="text" name="turmaTrf1" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoAdministrativoLabel" for="processo-numeroProcessoAdministrativo">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoAdministrativo">Numero Processo Administrativo</Translate>
                </Label>
                <AvField id="processo-numeroProcessoAdministrativo" type="text" name="numeroProcessoAdministrativo" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoJudicialPrimeiraInstanciaLabel" for="processo-numeroProcessoJudicialPrimeiraInstancia">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstancia">
                    Numero Processo Judicial Primeira Instancia
                  </Translate>
                </Label>
                <AvField id="processo-numeroProcessoJudicialPrimeiraInstancia" type="text" name="numeroProcessoJudicialPrimeiraInstancia" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoJudicialPrimeiraInstanciaLinkLabel" for="processo-numeroProcessoJudicialPrimeiraInstanciaLink">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaLink">
                    Numero Processo Judicial Primeira Instancia Link
                  </Translate>
                </Label>
                <AvField
                  id="processo-numeroProcessoJudicialPrimeiraInstanciaLink"
                  type="text"
                  name="numeroProcessoJudicialPrimeiraInstanciaLink"
                />
              </AvGroup>
              <AvGroup>
                <Label
                  id="numeroProcessoJudicialPrimeiraInstanciaObservacoesLabel"
                  for="processo-numeroProcessoJudicialPrimeiraInstanciaObservacoes"
                >
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaObservacoes">
                    Numero Processo Judicial Primeira Instancia Observacoes
                  </Translate>
                </Label>
                <AvInput
                  id="processo-numeroProcessoJudicialPrimeiraInstanciaObservacoes"
                  type="textarea"
                  name="numeroProcessoJudicialPrimeiraInstanciaObservacoes"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="parecerLabel">
                  <AvInput id="processo-parecer" type="checkbox" className="form-check-input" name="parecer" />
                  <Translate contentKey="cidhaApp.processo.parecer">Parecer</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="processo-concessaoLiminar">
                  <Translate contentKey="cidhaApp.processo.concessaoLiminar">Concessao Liminar</Translate>
                </Label>
                <AvInput id="processo-concessaoLiminar" type="select" className="form-control" name="concessaoLiminar.id">
                  <option value="" key="0" />
                  {concessaoLiminars
                    ? concessaoLiminars.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
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
  concessaoLiminars: storeState.concessaoLiminar.entities,
  comarcas: storeState.comarca.entities,
  quilombos: storeState.quilombo.entities,
  processoEntity: storeState.processo.entity,
  loading: storeState.processo.loading,
  updating: storeState.processo.updating,
  updateSuccess: storeState.processo.updateSuccess,
});

const mapDispatchToProps = {
  getConcessaoLiminars,
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
