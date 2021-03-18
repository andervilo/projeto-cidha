package br.com.cidha.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.cidha.domain.Quilombo} entity.
 */
public class QuilomboDTO implements Serializable {
    private Long id;

    private String nome;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuilomboDTO)) {
            return false;
        }

        return id != null && id.equals(((QuilomboDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuilomboDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
