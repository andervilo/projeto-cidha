package br.com.cidha.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link br.com.cidha.domain.Processo} entity. This class is used
 * in {@link br.com.cidha.web.rest.ProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessoCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter oficio;

    private StringFilter linkUnico;

    private StringFilter linkTrf;

    private StringFilter subsecaoJudiciaria;

    private StringFilter turmaTrf1;

    private StringFilter numeroProcessoAdministrativo;

    private StringFilter numeroProcessoJudicialPrimeiraInstancia;

    private StringFilter numeroProcessoJudicialPrimeiraInstanciaLink;

    private BooleanFilter parecer;

    private LongFilter concessaoLiminarId;

    private LongFilter comarcaId;

    private LongFilter quilomboId;

    private LongFilter tipoDecisaoId;

    public ProcessoCriteria() {}

    public ProcessoCriteria(ProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.oficio = other.oficio == null ? null : other.oficio.copy();
        this.linkUnico = other.linkUnico == null ? null : other.linkUnico.copy();
        this.linkTrf = other.linkTrf == null ? null : other.linkTrf.copy();
        this.subsecaoJudiciaria = other.subsecaoJudiciaria == null ? null : other.subsecaoJudiciaria.copy();
        this.turmaTrf1 = other.turmaTrf1 == null ? null : other.turmaTrf1.copy();
        this.numeroProcessoAdministrativo = other.numeroProcessoAdministrativo == null ? null : other.numeroProcessoAdministrativo.copy();
        this.numeroProcessoJudicialPrimeiraInstancia =
            other.numeroProcessoJudicialPrimeiraInstancia == null ? null : other.numeroProcessoJudicialPrimeiraInstancia.copy();
        this.numeroProcessoJudicialPrimeiraInstanciaLink =
            other.numeroProcessoJudicialPrimeiraInstanciaLink == null ? null : other.numeroProcessoJudicialPrimeiraInstanciaLink.copy();
        this.parecer = other.parecer == null ? null : other.parecer.copy();
        this.concessaoLiminarId = other.concessaoLiminarId == null ? null : other.concessaoLiminarId.copy();
        this.comarcaId = other.comarcaId == null ? null : other.comarcaId.copy();
        this.quilomboId = other.quilomboId == null ? null : other.quilomboId.copy();
        this.tipoDecisaoId = other.tipoDecisaoId == null ? null : other.tipoDecisaoId.copy();
    }

    @Override
    public ProcessoCriteria copy() {
        return new ProcessoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOficio() {
        return oficio;
    }

    public void setOficio(StringFilter oficio) {
        this.oficio = oficio;
    }

    public StringFilter getLinkUnico() {
        return linkUnico;
    }

    public void setLinkUnico(StringFilter linkUnico) {
        this.linkUnico = linkUnico;
    }

    public StringFilter getLinkTrf() {
        return linkTrf;
    }

    public void setLinkTrf(StringFilter linkTrf) {
        this.linkTrf = linkTrf;
    }

    public StringFilter getSubsecaoJudiciaria() {
        return subsecaoJudiciaria;
    }

    public void setSubsecaoJudiciaria(StringFilter subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
    }

    public StringFilter getTurmaTrf1() {
        return turmaTrf1;
    }

    public void setTurmaTrf1(StringFilter turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
    }

    public StringFilter getNumeroProcessoAdministrativo() {
        return numeroProcessoAdministrativo;
    }

    public void setNumeroProcessoAdministrativo(StringFilter numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
    }

    public StringFilter getNumeroProcessoJudicialPrimeiraInstancia() {
        return numeroProcessoJudicialPrimeiraInstancia;
    }

    public void setNumeroProcessoJudicialPrimeiraInstancia(StringFilter numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
    }

    public StringFilter getNumeroProcessoJudicialPrimeiraInstanciaLink() {
        return numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaLink(StringFilter numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public BooleanFilter getParecer() {
        return parecer;
    }

    public void setParecer(BooleanFilter parecer) {
        this.parecer = parecer;
    }

    public LongFilter getConcessaoLiminarId() {
        return concessaoLiminarId;
    }

    public void setConcessaoLiminarId(LongFilter concessaoLiminarId) {
        this.concessaoLiminarId = concessaoLiminarId;
    }

    public LongFilter getComarcaId() {
        return comarcaId;
    }

    public void setComarcaId(LongFilter comarcaId) {
        this.comarcaId = comarcaId;
    }

    public LongFilter getQuilomboId() {
        return quilomboId;
    }

    public void setQuilomboId(LongFilter quilomboId) {
        this.quilomboId = quilomboId;
    }

    public LongFilter getTipoDecisaoId() {
        return tipoDecisaoId;
    }

    public void setTipoDecisaoId(LongFilter tipoDecisaoId) {
        this.tipoDecisaoId = tipoDecisaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcessoCriteria that = (ProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(oficio, that.oficio) &&
            Objects.equals(linkUnico, that.linkUnico) &&
            Objects.equals(linkTrf, that.linkTrf) &&
            Objects.equals(subsecaoJudiciaria, that.subsecaoJudiciaria) &&
            Objects.equals(turmaTrf1, that.turmaTrf1) &&
            Objects.equals(numeroProcessoAdministrativo, that.numeroProcessoAdministrativo) &&
            Objects.equals(numeroProcessoJudicialPrimeiraInstancia, that.numeroProcessoJudicialPrimeiraInstancia) &&
            Objects.equals(numeroProcessoJudicialPrimeiraInstanciaLink, that.numeroProcessoJudicialPrimeiraInstanciaLink) &&
            Objects.equals(parecer, that.parecer) &&
            Objects.equals(concessaoLiminarId, that.concessaoLiminarId) &&
            Objects.equals(comarcaId, that.comarcaId) &&
            Objects.equals(quilomboId, that.quilomboId) &&
            Objects.equals(tipoDecisaoId, that.tipoDecisaoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            oficio,
            linkUnico,
            linkTrf,
            subsecaoJudiciaria,
            turmaTrf1,
            numeroProcessoAdministrativo,
            numeroProcessoJudicialPrimeiraInstancia,
            numeroProcessoJudicialPrimeiraInstanciaLink,
            parecer,
            concessaoLiminarId,
            comarcaId,
            quilomboId,
            tipoDecisaoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (oficio != null ? "oficio=" + oficio + ", " : "") +
                (linkUnico != null ? "linkUnico=" + linkUnico + ", " : "") +
                (linkTrf != null ? "linkTrf=" + linkTrf + ", " : "") +
                (subsecaoJudiciaria != null ? "subsecaoJudiciaria=" + subsecaoJudiciaria + ", " : "") +
                (turmaTrf1 != null ? "turmaTrf1=" + turmaTrf1 + ", " : "") +
                (numeroProcessoAdministrativo != null ? "numeroProcessoAdministrativo=" + numeroProcessoAdministrativo + ", " : "") +
                (numeroProcessoJudicialPrimeiraInstancia != null ? "numeroProcessoJudicialPrimeiraInstancia=" + numeroProcessoJudicialPrimeiraInstancia + ", " : "") +
                (numeroProcessoJudicialPrimeiraInstanciaLink != null ? "numeroProcessoJudicialPrimeiraInstanciaLink=" + numeroProcessoJudicialPrimeiraInstanciaLink + ", " : "") +
                (parecer != null ? "parecer=" + parecer + ", " : "") +
                (concessaoLiminarId != null ? "concessaoLiminarId=" + concessaoLiminarId + ", " : "") +
                (comarcaId != null ? "comarcaId=" + comarcaId + ", " : "") +
                (quilomboId != null ? "quilomboId=" + quilomboId + ", " : "") +
                (tipoDecisaoId != null ? "tipoDecisaoId=" + tipoDecisaoId + ", " : "") +
            "}";
    }
}
