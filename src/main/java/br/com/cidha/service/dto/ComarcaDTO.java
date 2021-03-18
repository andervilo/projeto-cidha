package br.com.cidha.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link br.com.cidha.domain.Comarca} entity.
 */
public class ComarcaDTO implements Serializable {
    private Long id;

    private String nome;

    private BigDecimal codigoCnj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getCodigoCnj() {
        return codigoCnj;
    }

    public void setCodigoCnj(BigDecimal codigoCnj) {
        this.codigoCnj = codigoCnj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComarcaDTO)) {
            return false;
        }

        return id != null && id.equals(((ComarcaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComarcaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigoCnj=" + getCodigoCnj() +
            "}";
    }
}
