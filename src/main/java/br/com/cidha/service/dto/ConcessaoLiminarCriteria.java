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
 * Criteria class for the {@link br.com.cidha.domain.ConcessaoLiminar} entity. This class is used
 * in {@link br.com.cidha.web.rest.ConcessaoLiminarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /concessao-liminars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConcessaoLiminarCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter processosId;

    public ConcessaoLiminarCriteria() {}

    public ConcessaoLiminarCriteria(ConcessaoLiminarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.processosId = other.processosId == null ? null : other.processosId.copy();
    }

    @Override
    public ConcessaoLiminarCriteria copy() {
        return new ConcessaoLiminarCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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
        final ConcessaoLiminarCriteria that = (ConcessaoLiminarCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(processosId, that.processosId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, processosId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConcessaoLiminarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (processosId != null ? "processosId=" + processosId + ", " : "") +
            "}";
    }
}
