package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Processo.
 */
@Entity
@Table(name = "processo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Processo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "oficio")
    private String oficio;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "assunto")
    private String assunto;

    @Column(name = "link_unico")
    private String linkUnico;

    @Column(name = "link_trf")
    private String linkTrf;

    @Column(name = "subsecao_judiciaria")
    private String subsecaoJudiciaria;

    @Column(name = "turma_trf_1")
    private String turmaTrf1;

    @Column(name = "numero_processo_administrativo")
    private String numeroProcessoAdministrativo;

    @Column(name = "numero_processo_judicial_primeira_instancia")
    private String numeroProcessoJudicialPrimeiraInstancia;

    @Column(name = "numero_processo_judicial_primeira_instancia_link")
    private String numeroProcessoJudicialPrimeiraInstanciaLink;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "numero_processo_judicial_primeira_instancia_observacoes")
    private String numeroProcessoJudicialPrimeiraInstanciaObservacoes;

    @Column(name = "parecer")
    private Boolean parecer;

    @Column(name = "folhas_processo_concessao_liminar")
    private String folhasProcessoConcessaoLiminar;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "concessao_limnar_observacoes")
    private String concessaoLimnarObservacoes;

    @Column(name = "folhas_processo_cassacao")
    private String folhasProcessoCassacao;

    @ManyToOne
    @JsonIgnoreProperties(value = "processos", allowSetters = true)
    private ConcessaoLiminar concessaoLiminar;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "processo_comarca",
        joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "comarca_id", referencedColumnName = "id")
    )
    private Set<Comarca> comarcas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "processo_quilombo",
        joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "quilombo_id", referencedColumnName = "id")
    )
    private Set<Quilombo> quilombos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "processos", allowSetters = true)
    private TipoDecisao tipoDecisao;

    @ManyToOne
    @JsonIgnoreProperties(value = "processos", allowSetters = true)
    private ConcessaoLiminarCassada concessaoLiminarCassada;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOficio() {
        return oficio;
    }

    public Processo oficio(String oficio) {
        this.oficio = oficio;
        return this;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getAssunto() {
        return assunto;
    }

    public Processo assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getLinkUnico() {
        return linkUnico;
    }

    public Processo linkUnico(String linkUnico) {
        this.linkUnico = linkUnico;
        return this;
    }

    public void setLinkUnico(String linkUnico) {
        this.linkUnico = linkUnico;
    }

    public String getLinkTrf() {
        return linkTrf;
    }

    public Processo linkTrf(String linkTrf) {
        this.linkTrf = linkTrf;
        return this;
    }

    public void setLinkTrf(String linkTrf) {
        this.linkTrf = linkTrf;
    }

    public String getSubsecaoJudiciaria() {
        return subsecaoJudiciaria;
    }

    public Processo subsecaoJudiciaria(String subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
        return this;
    }

    public void setSubsecaoJudiciaria(String subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
    }

    public String getTurmaTrf1() {
        return turmaTrf1;
    }

    public Processo turmaTrf1(String turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
        return this;
    }

    public void setTurmaTrf1(String turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
    }

    public String getNumeroProcessoAdministrativo() {
        return numeroProcessoAdministrativo;
    }

    public Processo numeroProcessoAdministrativo(String numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
        return this;
    }

    public void setNumeroProcessoAdministrativo(String numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
    }

    public String getNumeroProcessoJudicialPrimeiraInstancia() {
        return numeroProcessoJudicialPrimeiraInstancia;
    }

    public Processo numeroProcessoJudicialPrimeiraInstancia(String numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstancia(String numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
    }

    public String getNumeroProcessoJudicialPrimeiraInstanciaLink() {
        return numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public Processo numeroProcessoJudicialPrimeiraInstanciaLink(String numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaLink(String numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public String getNumeroProcessoJudicialPrimeiraInstanciaObservacoes() {
        return numeroProcessoJudicialPrimeiraInstanciaObservacoes;
    }

    public Processo numeroProcessoJudicialPrimeiraInstanciaObservacoes(String numeroProcessoJudicialPrimeiraInstanciaObservacoes) {
        this.numeroProcessoJudicialPrimeiraInstanciaObservacoes = numeroProcessoJudicialPrimeiraInstanciaObservacoes;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaObservacoes(String numeroProcessoJudicialPrimeiraInstanciaObservacoes) {
        this.numeroProcessoJudicialPrimeiraInstanciaObservacoes = numeroProcessoJudicialPrimeiraInstanciaObservacoes;
    }

    public Boolean isParecer() {
        return parecer;
    }

    public Processo parecer(Boolean parecer) {
        this.parecer = parecer;
        return this;
    }

    public void setParecer(Boolean parecer) {
        this.parecer = parecer;
    }

    public String getFolhasProcessoConcessaoLiminar() {
        return folhasProcessoConcessaoLiminar;
    }

    public Processo folhasProcessoConcessaoLiminar(String folhasProcessoConcessaoLiminar) {
        this.folhasProcessoConcessaoLiminar = folhasProcessoConcessaoLiminar;
        return this;
    }

    public void setFolhasProcessoConcessaoLiminar(String folhasProcessoConcessaoLiminar) {
        this.folhasProcessoConcessaoLiminar = folhasProcessoConcessaoLiminar;
    }

    public String getConcessaoLimnarObservacoes() {
        return concessaoLimnarObservacoes;
    }

    public Processo concessaoLimnarObservacoes(String concessaoLimnarObservacoes) {
        this.concessaoLimnarObservacoes = concessaoLimnarObservacoes;
        return this;
    }

    public void setConcessaoLimnarObservacoes(String concessaoLimnarObservacoes) {
        this.concessaoLimnarObservacoes = concessaoLimnarObservacoes;
    }

    public String getFolhasProcessoCassacao() {
        return folhasProcessoCassacao;
    }

    public Processo folhasProcessoCassacao(String folhasProcessoCassacao) {
        this.folhasProcessoCassacao = folhasProcessoCassacao;
        return this;
    }

    public void setFolhasProcessoCassacao(String folhasProcessoCassacao) {
        this.folhasProcessoCassacao = folhasProcessoCassacao;
    }

    public ConcessaoLiminar getConcessaoLiminar() {
        return concessaoLiminar;
    }

    public Processo concessaoLiminar(ConcessaoLiminar concessaoLiminar) {
        this.concessaoLiminar = concessaoLiminar;
        return this;
    }

    public void setConcessaoLiminar(ConcessaoLiminar concessaoLiminar) {
        this.concessaoLiminar = concessaoLiminar;
    }

    public Set<Comarca> getComarcas() {
        return comarcas;
    }

    public Processo comarcas(Set<Comarca> comarcas) {
        this.comarcas = comarcas;
        return this;
    }

    public Processo addComarca(Comarca comarca) {
        this.comarcas.add(comarca);
        comarca.getProcessos().add(this);
        return this;
    }

    public Processo removeComarca(Comarca comarca) {
        this.comarcas.remove(comarca);
        comarca.getProcessos().remove(this);
        return this;
    }

    public void setComarcas(Set<Comarca> comarcas) {
        this.comarcas = comarcas;
    }

    public Set<Quilombo> getQuilombos() {
        return quilombos;
    }

    public Processo quilombos(Set<Quilombo> quilombos) {
        this.quilombos = quilombos;
        return this;
    }

    public Processo addQuilombo(Quilombo quilombo) {
        this.quilombos.add(quilombo);
        quilombo.getProcessos().add(this);
        return this;
    }

    public Processo removeQuilombo(Quilombo quilombo) {
        this.quilombos.remove(quilombo);
        quilombo.getProcessos().remove(this);
        return this;
    }

    public void setQuilombos(Set<Quilombo> quilombos) {
        this.quilombos = quilombos;
    }

    public TipoDecisao getTipoDecisao() {
        return tipoDecisao;
    }

    public Processo tipoDecisao(TipoDecisao tipoDecisao) {
        this.tipoDecisao = tipoDecisao;
        return this;
    }

    public void setTipoDecisao(TipoDecisao tipoDecisao) {
        this.tipoDecisao = tipoDecisao;
    }

    public ConcessaoLiminarCassada getConcessaoLiminarCassada() {
        return concessaoLiminarCassada;
    }

    public Processo concessaoLiminarCassada(ConcessaoLiminarCassada concessaoLiminarCassada) {
        this.concessaoLiminarCassada = concessaoLiminarCassada;
        return this;
    }

    public void setConcessaoLiminarCassada(ConcessaoLiminarCassada concessaoLiminarCassada) {
        this.concessaoLiminarCassada = concessaoLiminarCassada;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processo)) {
            return false;
        }
        return id != null && id.equals(((Processo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Processo{" +
            "id=" + getId() +
            ", oficio='" + getOficio() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", linkUnico='" + getLinkUnico() + "'" +
            ", linkTrf='" + getLinkTrf() + "'" +
            ", subsecaoJudiciaria='" + getSubsecaoJudiciaria() + "'" +
            ", turmaTrf1='" + getTurmaTrf1() + "'" +
            ", numeroProcessoAdministrativo='" + getNumeroProcessoAdministrativo() + "'" +
            ", numeroProcessoJudicialPrimeiraInstancia='" + getNumeroProcessoJudicialPrimeiraInstancia() + "'" +
            ", numeroProcessoJudicialPrimeiraInstanciaLink='" + getNumeroProcessoJudicialPrimeiraInstanciaLink() + "'" +
            ", numeroProcessoJudicialPrimeiraInstanciaObservacoes='" + getNumeroProcessoJudicialPrimeiraInstanciaObservacoes() + "'" +
            ", parecer='" + isParecer() + "'" +
            ", folhasProcessoConcessaoLiminar='" + getFolhasProcessoConcessaoLiminar() + "'" +
            ", concessaoLimnarObservacoes='" + getConcessaoLimnarObservacoes() + "'" +
            ", folhasProcessoCassacao='" + getFolhasProcessoCassacao() + "'" +
            "}";
    }
}
