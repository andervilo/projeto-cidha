package br.com.cidha.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Lob;

/**
 * A DTO for the {@link br.com.cidha.domain.Processo} entity.
 */
public class ProcessoDTO implements Serializable {
    private Long id;

    private String oficio;

    @Lob
    private String assunto;

    private String linkUnico;

    private String secaoJudiciaria;

    private Set<ComarcaDTO> comarcas = new HashSet<>();
    private Set<QuilomboDTO> quilombos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getLinkUnico() {
        return linkUnico;
    }

    public void setLinkUnico(String linkUnico) {
        this.linkUnico = linkUnico;
    }

    public String getSecaoJudiciaria() {
        return secaoJudiciaria;
    }

    public void setSecaoJudiciaria(String secaoJudiciaria) {
        this.secaoJudiciaria = secaoJudiciaria;
    }

    public Set<ComarcaDTO> getComarcas() {
        return comarcas;
    }

    public void setComarcas(Set<ComarcaDTO> comarcas) {
        this.comarcas = comarcas;
    }

    public Set<QuilomboDTO> getQuilombos() {
        return quilombos;
    }

    public void setQuilombos(Set<QuilomboDTO> quilombos) {
        this.quilombos = quilombos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessoDTO)) {
            return false;
        }

        return id != null && id.equals(((ProcessoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoDTO{" +
            "id=" + getId() +
            ", oficio='" + getOficio() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", linkUnico='" + getLinkUnico() + "'" +
            ", secaoJudiciaria='" + getSecaoJudiciaria() + "'" +
            ", comarcas='" + getComarcas() + "'" +
            ", quilombos='" + getQuilombos() + "'" +
            "}";
    }
}
