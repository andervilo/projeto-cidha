package br.com.cidha.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comarca.
 */
@Entity
@Table(name = "comarca")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comarca implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo_cnj", precision = 21, scale = 2)
    private BigDecimal codigoCnj;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Comarca nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getCodigoCnj() {
        return codigoCnj;
    }

    public Comarca codigoCnj(BigDecimal codigoCnj) {
        this.codigoCnj = codigoCnj;
        return this;
    }

    public void setCodigoCnj(BigDecimal codigoCnj) {
        this.codigoCnj = codigoCnj;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comarca)) {
            return false;
        }
        return id != null && id.equals(((Comarca) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comarca{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigoCnj=" + getCodigoCnj() +
            "}";
    }
}
