import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './processo.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcessoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessoDetail = (props: IProcessoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { processoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.processo.detail.title">Processo</Translate> [<b>{processoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="oficio">
              <Translate contentKey="cidhaApp.processo.oficio">Oficio</Translate>
            </span>
          </dt>
          <dd>{processoEntity.oficio}</dd>
          <dt>
            <span id="assunto">
              <Translate contentKey="cidhaApp.processo.assunto">Assunto</Translate>
            </span>
          </dt>
          <dd>{processoEntity.assunto}</dd>
          <dt>
            <span id="linkUnico">
              <Translate contentKey="cidhaApp.processo.linkUnico">Link Unico</Translate>
            </span>
          </dt>
          <dd>{processoEntity.linkUnico}</dd>
          <dt>
            <span id="linkTrf">
              <Translate contentKey="cidhaApp.processo.linkTrf">Link Trf</Translate>
            </span>
          </dt>
          <dd>{processoEntity.linkTrf}</dd>
          <dt>
            <span id="subsecaoJudiciaria">
              <Translate contentKey="cidhaApp.processo.subsecaoJudiciaria">Subsecao Judiciaria</Translate>
            </span>
          </dt>
          <dd>{processoEntity.subsecaoJudiciaria}</dd>
          <dt>
            <span id="turmaTrf1">
              <Translate contentKey="cidhaApp.processo.turmaTrf1">Turma Trf 1</Translate>
            </span>
          </dt>
          <dd>{processoEntity.turmaTrf1}</dd>
          <dt>
            <span id="numeroProcessoAdministrativo">
              <Translate contentKey="cidhaApp.processo.numeroProcessoAdministrativo">Numero Processo Administrativo</Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoAdministrativo}</dd>
          <dt>
            <span id="numeroProcessoJudicialPrimeiraInstancia">
              <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstancia">
                Numero Processo Judicial Primeira Instancia
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoJudicialPrimeiraInstancia}</dd>
          <dt>
            <span id="numeroProcessoJudicialPrimeiraInstanciaLink">
              <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaLink">
                Numero Processo Judicial Primeira Instancia Link
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoJudicialPrimeiraInstanciaLink}</dd>
          <dt>
            <span id="numeroProcessoJudicialPrimeiraInstanciaObservacoes">
              <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaObservacoes">
                Numero Processo Judicial Primeira Instancia Observacoes
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoJudicialPrimeiraInstanciaObservacoes}</dd>
          <dt>
            <span id="parecer">
              <Translate contentKey="cidhaApp.processo.parecer">Parecer</Translate>
            </span>
          </dt>
          <dd>{processoEntity.parecer ? 'true' : 'false'}</dd>
          <dt>
            <span id="folhasProcessoConcessaoLiminar">
              <Translate contentKey="cidhaApp.processo.folhasProcessoConcessaoLiminar">Folhas Processo Concessao Liminar</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasProcessoConcessaoLiminar}</dd>
          <dt>
            <span id="concessaoLimnarObservacoes">
              <Translate contentKey="cidhaApp.processo.concessaoLimnarObservacoes">Concessao Limnar Observacoes</Translate>
            </span>
          </dt>
          <dd>{processoEntity.concessaoLimnarObservacoes}</dd>
          <dt>
            <span id="folhasProcessoCassacao">
              <Translate contentKey="cidhaApp.processo.folhasProcessoCassacao">Folhas Processo Cassacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasProcessoCassacao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.concessaoLiminar">Concessao Liminar</Translate>
          </dt>
          <dd>{processoEntity.concessaoLiminar ? processoEntity.concessaoLiminar.descricao : ''}</dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.comarca">Comarca</Translate>
          </dt>
          <dd>
            {processoEntity.comarcas
              ? processoEntity.comarcas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {processoEntity.comarcas && i === processoEntity.comarcas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.quilombo">Quilombo</Translate>
          </dt>
          <dd>
            {processoEntity.quilombos
              ? processoEntity.quilombos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {processoEntity.quilombos && i === processoEntity.quilombos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.tipoDecisao">Tipo Decisao</Translate>
          </dt>
          <dd>{processoEntity.tipoDecisao ? processoEntity.tipoDecisao.descricao : ''}</dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.concessaoLiminarCassada">Concessao Liminar Cassada</Translate>
          </dt>
          <dd>{processoEntity.concessaoLiminarCassada ? processoEntity.concessaoLiminarCassada.descricao : ''}</dd>
        </dl>
        <Button tag={Link} to="/processo" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/processo/${processoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ processo }: IRootState) => ({
  processoEntity: processo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProcessoDetail);
