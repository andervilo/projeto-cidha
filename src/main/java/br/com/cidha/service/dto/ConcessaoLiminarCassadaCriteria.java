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
 * Criteria class for the {@link br.com.cidha.domain.ConcessaoLiminarCassada} entity. This class is used
 * in {@link br.com.cidha.web.rest.ConcessaoLiminarCassadaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /concessao-liminar-cassadas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConcessaoLiminarCassadaCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private LongFilter processosId;

    public ConcessaoLiminarCassadaCriteria() {}

    public ConcessaoLiminarCassadaCriteria(ConcessaoLiminarCassadaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.processosId = other.processosId == null ? null : other.processosId.copy();
    }

    @Override
    public ConcessaoLiminarCassadaCriteria copy() {
        return new ConcessaoLiminarCassadaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public LongFilter getProcessosId() {
        return processosId;
    }

    public void setProcessosId(LongFilter processosId) {
        this.processosId = processosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConcessaoLiminarCassadaCriteria that = (ConcessaoLiminarCassadaCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(descricao, that.descricao) && Objects.equals(processosId, that.processosId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, processosId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConcessaoLiminarCassadaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (processosId != null ? "processosId=" + processosId + ", " : "") +
            "}";
    }
}
