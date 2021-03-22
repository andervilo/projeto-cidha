package br.com.cidha.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A ConcessaoLiminar.
 */
@Entity
@Table(name = "concessao_liminar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConcessaoLiminar implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "concessaoLiminar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Processo> processos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public ConcessaoLiminar descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public ConcessaoLiminar processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public ConcessaoLiminar addProcessos(Processo processo) {
        this.processos.add(processo);
        processo.setConcessaoLiminar(this);
        return this;
    }

    public ConcessaoLiminar removeProcessos(Processo processo) {
        this.processos.remove(processo);
        processo.setConcessaoLiminar(null);
        return this;
    }

    public void setProcessos(Set<Processo> processos) {
        this.processos = processos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConcessaoLiminar)) {
            return false;
        }
        return id != null && id.equals(((ConcessaoLiminar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConcessaoLiminar{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
