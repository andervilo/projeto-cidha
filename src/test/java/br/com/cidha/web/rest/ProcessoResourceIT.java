package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Comarca;
import br.com.cidha.domain.ConcessaoLiminar;
import br.com.cidha.domain.ConcessaoLiminarCassada;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.Quilombo;
import br.com.cidha.domain.TipoDecisao;
import br.com.cidha.repository.ProcessoRepository;
import br.com.cidha.service.ProcessoQueryService;
import br.com.cidha.service.ProcessoService;
import br.com.cidha.service.dto.ProcessoCriteria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProcessoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcessoResourceIT {
    private static final String DEFAULT_OFICIO = "AAAAAAAAAA";
    private static final String UPDATED_OFICIO = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_UNICO = "AAAAAAAAAA";
    private static final String UPDATED_LINK_UNICO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_TRF = "AAAAAAAAAA";
    private static final String UPDATED_LINK_TRF = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSECAO_JUDICIARIA = "AAAAAAAAAA";
    private static final String UPDATED_SUBSECAO_JUDICIARIA = "BBBBBBBBBB";

    private static final String DEFAULT_TURMA_TRF_1 = "AAAAAAAAAA";
    private static final String UPDATED_TURMA_TRF_1 = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARECER = false;
    private static final Boolean UPDATED_PARECER = true;

    private static final String DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR = "BBBBBBBBBB";

    private static final String DEFAULT_CONCESSAO_LIMNAR_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_CONCESSAO_LIMNAR_OBSERVACOES = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_PROCESSO_CASSACAO = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_PROCESSO_CASSACAO = "BBBBBBBBBB";

    @Autowired
    private ProcessoRepository processoRepository;

    @Mock
    private ProcessoRepository processoRepositoryMock;

    @Mock
    private ProcessoService processoServiceMock;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProcessoQueryService processoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessoMockMvc;

    private Processo processo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createEntity(EntityManager em) {
        Processo processo = new Processo()
            .oficio(DEFAULT_OFICIO)
            .assunto(DEFAULT_ASSUNTO)
            .linkUnico(DEFAULT_LINK_UNICO)
            .linkTrf(DEFAULT_LINK_TRF)
            .subsecaoJudiciaria(DEFAULT_SUBSECAO_JUDICIARIA)
            .turmaTrf1(DEFAULT_TURMA_TRF_1)
            .numeroProcessoAdministrativo(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(DEFAULT_PARECER)
            .folhasProcessoConcessaoLiminar(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)
            .concessaoLimnarObservacoes(DEFAULT_CONCESSAO_LIMNAR_OBSERVACOES)
            .folhasProcessoCassacao(DEFAULT_FOLHAS_PROCESSO_CASSACAO);
        return processo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createUpdatedEntity(EntityManager em) {
        Processo processo = new Processo()
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .subsecaoJudiciaria(UPDATED_SUBSECAO_JUDICIARIA)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(UPDATED_PARECER)
            .folhasProcessoConcessaoLiminar(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)
            .concessaoLimnarObservacoes(UPDATED_CONCESSAO_LIMNAR_OBSERVACOES)
            .folhasProcessoCassacao(UPDATED_FOLHAS_PROCESSO_CASSACAO);
        return processo;
    }

    @BeforeEach
    public void initTest() {
        processo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcesso() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();
        // Create the Processo
        restProcessoMockMvc
            .perform(post("/api/processos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isCreated());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate + 1);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getOficio()).isEqualTo(DEFAULT_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(DEFAULT_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(DEFAULT_LINK_TRF);
        assertThat(testProcesso.getSubsecaoJudiciaria()).isEqualTo(DEFAULT_SUBSECAO_JUDICIARIA);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(DEFAULT_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.isParecer()).isEqualTo(DEFAULT_PARECER);
        assertThat(testProcesso.getFolhasProcessoConcessaoLiminar()).isEqualTo(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
        assertThat(testProcesso.getConcessaoLimnarObservacoes()).isEqualTo(DEFAULT_CONCESSAO_LIMNAR_OBSERVACOES);
        assertThat(testProcesso.getFolhasProcessoCassacao()).isEqualTo(DEFAULT_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    public void createProcessoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // Create the Processo with an existing ID
        processo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoMockMvc
            .perform(post("/api/processos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProcessos() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList
        restProcessoMockMvc
            .perform(get("/api/processos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].oficio").value(hasItem(DEFAULT_OFICIO)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO.toString())))
            .andExpect(jsonPath("$.[*].linkUnico").value(hasItem(DEFAULT_LINK_UNICO)))
            .andExpect(jsonPath("$.[*].linkTrf").value(hasItem(DEFAULT_LINK_TRF)))
            .andExpect(jsonPath("$.[*].subsecaoJudiciaria").value(hasItem(DEFAULT_SUBSECAO_JUDICIARIA)))
            .andExpect(jsonPath("$.[*].turmaTrf1").value(hasItem(DEFAULT_TURMA_TRF_1)))
            .andExpect(jsonPath("$.[*].numeroProcessoAdministrativo").value(hasItem(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO)))
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstancia")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA))
            )
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstanciaLink")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK))
            )
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstanciaObservacoes")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES.toString()))
            )
            .andExpect(jsonPath("$.[*].parecer").value(hasItem(DEFAULT_PARECER.booleanValue())))
            .andExpect(jsonPath("$.[*].folhasProcessoConcessaoLiminar").value(hasItem(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)))
            .andExpect(jsonPath("$.[*].concessaoLimnarObservacoes").value(hasItem(DEFAULT_CONCESSAO_LIMNAR_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].folhasProcessoCassacao").value(hasItem(DEFAULT_FOLHAS_PROCESSO_CASSACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllProcessosWithEagerRelationshipsIsEnabled() throws Exception {
        when(processoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoMockMvc.perform(get("/api/processos?eagerload=true")).andExpect(status().isOk());

        verify(processoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllProcessosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(processoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoMockMvc.perform(get("/api/processos?eagerload=true")).andExpect(status().isOk());

        verify(processoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get the processo
        restProcessoMockMvc
            .perform(get("/api/processos/{id}", processo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processo.getId().intValue()))
            .andExpect(jsonPath("$.oficio").value(DEFAULT_OFICIO))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO.toString()))
            .andExpect(jsonPath("$.linkUnico").value(DEFAULT_LINK_UNICO))
            .andExpect(jsonPath("$.linkTrf").value(DEFAULT_LINK_TRF))
            .andExpect(jsonPath("$.subsecaoJudiciaria").value(DEFAULT_SUBSECAO_JUDICIARIA))
            .andExpect(jsonPath("$.turmaTrf1").value(DEFAULT_TURMA_TRF_1))
            .andExpect(jsonPath("$.numeroProcessoAdministrativo").value(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO))
            .andExpect(jsonPath("$.numeroProcessoJudicialPrimeiraInstancia").value(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA))
            .andExpect(
                jsonPath("$.numeroProcessoJudicialPrimeiraInstanciaLink").value(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            )
            .andExpect(
                jsonPath("$.numeroProcessoJudicialPrimeiraInstanciaObservacoes")
                    .value(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES.toString())
            )
            .andExpect(jsonPath("$.parecer").value(DEFAULT_PARECER.booleanValue()))
            .andExpect(jsonPath("$.folhasProcessoConcessaoLiminar").value(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR))
            .andExpect(jsonPath("$.concessaoLimnarObservacoes").value(DEFAULT_CONCESSAO_LIMNAR_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.folhasProcessoCassacao").value(DEFAULT_FOLHAS_PROCESSO_CASSACAO));
    }

    @Test
    @Transactional
    public void getProcessosByIdFiltering() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        Long id = processo.getId();

        defaultProcessoShouldBeFound("id.equals=" + id);
        defaultProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllProcessosByOficioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio equals to DEFAULT_OFICIO
        defaultProcessoShouldBeFound("oficio.equals=" + DEFAULT_OFICIO);

        // Get all the processoList where oficio equals to UPDATED_OFICIO
        defaultProcessoShouldNotBeFound("oficio.equals=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    public void getAllProcessosByOficioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio not equals to DEFAULT_OFICIO
        defaultProcessoShouldNotBeFound("oficio.notEquals=" + DEFAULT_OFICIO);

        // Get all the processoList where oficio not equals to UPDATED_OFICIO
        defaultProcessoShouldBeFound("oficio.notEquals=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    public void getAllProcessosByOficioIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio in DEFAULT_OFICIO or UPDATED_OFICIO
        defaultProcessoShouldBeFound("oficio.in=" + DEFAULT_OFICIO + "," + UPDATED_OFICIO);

        // Get all the processoList where oficio equals to UPDATED_OFICIO
        defaultProcessoShouldNotBeFound("oficio.in=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    public void getAllProcessosByOficioIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio is not null
        defaultProcessoShouldBeFound("oficio.specified=true");

        // Get all the processoList where oficio is null
        defaultProcessoShouldNotBeFound("oficio.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByOficioContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio contains DEFAULT_OFICIO
        defaultProcessoShouldBeFound("oficio.contains=" + DEFAULT_OFICIO);

        // Get all the processoList where oficio contains UPDATED_OFICIO
        defaultProcessoShouldNotBeFound("oficio.contains=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    public void getAllProcessosByOficioNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio does not contain DEFAULT_OFICIO
        defaultProcessoShouldNotBeFound("oficio.doesNotContain=" + DEFAULT_OFICIO);

        // Get all the processoList where oficio does not contain UPDATED_OFICIO
        defaultProcessoShouldBeFound("oficio.doesNotContain=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkUnicoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico equals to DEFAULT_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.equals=" + DEFAULT_LINK_UNICO);

        // Get all the processoList where linkUnico equals to UPDATED_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.equals=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkUnicoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico not equals to DEFAULT_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.notEquals=" + DEFAULT_LINK_UNICO);

        // Get all the processoList where linkUnico not equals to UPDATED_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.notEquals=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkUnicoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico in DEFAULT_LINK_UNICO or UPDATED_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.in=" + DEFAULT_LINK_UNICO + "," + UPDATED_LINK_UNICO);

        // Get all the processoList where linkUnico equals to UPDATED_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.in=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkUnicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico is not null
        defaultProcessoShouldBeFound("linkUnico.specified=true");

        // Get all the processoList where linkUnico is null
        defaultProcessoShouldNotBeFound("linkUnico.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkUnicoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico contains DEFAULT_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.contains=" + DEFAULT_LINK_UNICO);

        // Get all the processoList where linkUnico contains UPDATED_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.contains=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkUnicoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico does not contain DEFAULT_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.doesNotContain=" + DEFAULT_LINK_UNICO);

        // Get all the processoList where linkUnico does not contain UPDATED_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.doesNotContain=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkTrfIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf equals to DEFAULT_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.equals=" + DEFAULT_LINK_TRF);

        // Get all the processoList where linkTrf equals to UPDATED_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.equals=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkTrfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf not equals to DEFAULT_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.notEquals=" + DEFAULT_LINK_TRF);

        // Get all the processoList where linkTrf not equals to UPDATED_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.notEquals=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkTrfIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf in DEFAULT_LINK_TRF or UPDATED_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.in=" + DEFAULT_LINK_TRF + "," + UPDATED_LINK_TRF);

        // Get all the processoList where linkTrf equals to UPDATED_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.in=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkTrfIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf is not null
        defaultProcessoShouldBeFound("linkTrf.specified=true");

        // Get all the processoList where linkTrf is null
        defaultProcessoShouldNotBeFound("linkTrf.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkTrfContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf contains DEFAULT_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.contains=" + DEFAULT_LINK_TRF);

        // Get all the processoList where linkTrf contains UPDATED_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.contains=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    public void getAllProcessosByLinkTrfNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf does not contain DEFAULT_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.doesNotContain=" + DEFAULT_LINK_TRF);

        // Get all the processoList where linkTrf does not contain UPDATED_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.doesNotContain=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    public void getAllProcessosBySubsecaoJudiciariaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria equals to DEFAULT_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.equals=" + DEFAULT_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria equals to UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.equals=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    public void getAllProcessosBySubsecaoJudiciariaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria not equals to DEFAULT_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.notEquals=" + DEFAULT_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria not equals to UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.notEquals=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    public void getAllProcessosBySubsecaoJudiciariaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria in DEFAULT_SUBSECAO_JUDICIARIA or UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.in=" + DEFAULT_SUBSECAO_JUDICIARIA + "," + UPDATED_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria equals to UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.in=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    public void getAllProcessosBySubsecaoJudiciariaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria is not null
        defaultProcessoShouldBeFound("subsecaoJudiciaria.specified=true");

        // Get all the processoList where subsecaoJudiciaria is null
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosBySubsecaoJudiciariaContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria contains DEFAULT_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.contains=" + DEFAULT_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria contains UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.contains=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    public void getAllProcessosBySubsecaoJudiciariaNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria does not contain DEFAULT_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.doesNotContain=" + DEFAULT_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria does not contain UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.doesNotContain=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    public void getAllProcessosByTurmaTrf1IsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 equals to DEFAULT_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.equals=" + DEFAULT_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 equals to UPDATED_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.equals=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    public void getAllProcessosByTurmaTrf1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 not equals to DEFAULT_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.notEquals=" + DEFAULT_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 not equals to UPDATED_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.notEquals=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    public void getAllProcessosByTurmaTrf1IsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 in DEFAULT_TURMA_TRF_1 or UPDATED_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.in=" + DEFAULT_TURMA_TRF_1 + "," + UPDATED_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 equals to UPDATED_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.in=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    public void getAllProcessosByTurmaTrf1IsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 is not null
        defaultProcessoShouldBeFound("turmaTrf1.specified=true");

        // Get all the processoList where turmaTrf1 is null
        defaultProcessoShouldNotBeFound("turmaTrf1.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByTurmaTrf1ContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 contains DEFAULT_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.contains=" + DEFAULT_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 contains UPDATED_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.contains=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    public void getAllProcessosByTurmaTrf1NotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 does not contain DEFAULT_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.doesNotContain=" + DEFAULT_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 does not contain UPDATED_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.doesNotContain=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoAdministrativoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo equals to DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.equals=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);

        // Get all the processoList where numeroProcessoAdministrativo equals to UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.equals=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoAdministrativoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo not equals to DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.notEquals=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);

        // Get all the processoList where numeroProcessoAdministrativo not equals to UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.notEquals=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoAdministrativoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo in DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO or UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound(
            "numeroProcessoAdministrativo.in=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO + "," + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        );

        // Get all the processoList where numeroProcessoAdministrativo equals to UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.in=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoAdministrativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo is not null
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.specified=true");

        // Get all the processoList where numeroProcessoAdministrativo is null
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoAdministrativoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo contains DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.contains=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);

        // Get all the processoList where numeroProcessoAdministrativo contains UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.contains=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoAdministrativoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo does not contain DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.doesNotContain=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);

        // Get all the processoList where numeroProcessoAdministrativo does not contain UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.doesNotContain=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia equals to DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.equals=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.equals=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia not equals to DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.notEquals=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia not equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.notEquals=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia in DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA or UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.in=" +
            DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA +
            "," +
            UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.in=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia is not null
        defaultProcessoShouldBeFound("numeroProcessoJudicialPrimeiraInstancia.specified=true");

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia is null
        defaultProcessoShouldNotBeFound("numeroProcessoJudicialPrimeiraInstancia.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia contains DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.contains=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia contains UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.contains=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia does not contain DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.doesNotContain=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia does not contain UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.doesNotContain=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink equals to DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.equals=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.equals=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink not equals to DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.notEquals=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink not equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.notEquals=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink in DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK or UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.in=" +
            DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK +
            "," +
            UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.in=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink is not null
        defaultProcessoShouldBeFound("numeroProcessoJudicialPrimeiraInstanciaLink.specified=true");

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink is null
        defaultProcessoShouldNotBeFound("numeroProcessoJudicialPrimeiraInstanciaLink.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink contains DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.contains=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink contains UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.contains=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink does not contain DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.doesNotContain=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink does not contain UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.doesNotContain=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    public void getAllProcessosByParecerIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where parecer equals to DEFAULT_PARECER
        defaultProcessoShouldBeFound("parecer.equals=" + DEFAULT_PARECER);

        // Get all the processoList where parecer equals to UPDATED_PARECER
        defaultProcessoShouldNotBeFound("parecer.equals=" + UPDATED_PARECER);
    }

    @Test
    @Transactional
    public void getAllProcessosByParecerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where parecer not equals to DEFAULT_PARECER
        defaultProcessoShouldNotBeFound("parecer.notEquals=" + DEFAULT_PARECER);

        // Get all the processoList where parecer not equals to UPDATED_PARECER
        defaultProcessoShouldBeFound("parecer.notEquals=" + UPDATED_PARECER);
    }

    @Test
    @Transactional
    public void getAllProcessosByParecerIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where parecer in DEFAULT_PARECER or UPDATED_PARECER
        defaultProcessoShouldBeFound("parecer.in=" + DEFAULT_PARECER + "," + UPDATED_PARECER);

        // Get all the processoList where parecer equals to UPDATED_PARECER
        defaultProcessoShouldNotBeFound("parecer.in=" + UPDATED_PARECER);
    }

    @Test
    @Transactional
    public void getAllProcessosByParecerIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where parecer is not null
        defaultProcessoShouldBeFound("parecer.specified=true");

        // Get all the processoList where parecer is null
        defaultProcessoShouldNotBeFound("parecer.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoConcessaoLiminarIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar equals to DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.equals=" + DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);

        // Get all the processoList where folhasProcessoConcessaoLiminar equals to UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.equals=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoConcessaoLiminarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar not equals to DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.notEquals=" + DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);

        // Get all the processoList where folhasProcessoConcessaoLiminar not equals to UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.notEquals=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoConcessaoLiminarIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar in DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR or UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound(
            "folhasProcessoConcessaoLiminar.in=" +
            DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR +
            "," +
            UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        );

        // Get all the processoList where folhasProcessoConcessaoLiminar equals to UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.in=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoConcessaoLiminarIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar is not null
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.specified=true");

        // Get all the processoList where folhasProcessoConcessaoLiminar is null
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoConcessaoLiminarContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar contains DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.contains=" + DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);

        // Get all the processoList where folhasProcessoConcessaoLiminar contains UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.contains=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoConcessaoLiminarNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar does not contain DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.doesNotContain=" + DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);

        // Get all the processoList where folhasProcessoConcessaoLiminar does not contain UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.doesNotContain=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoCassacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao equals to DEFAULT_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound("folhasProcessoCassacao.equals=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO);

        // Get all the processoList where folhasProcessoCassacao equals to UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.equals=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoCassacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao not equals to DEFAULT_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.notEquals=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO);

        // Get all the processoList where folhasProcessoCassacao not equals to UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound("folhasProcessoCassacao.notEquals=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoCassacaoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao in DEFAULT_FOLHAS_PROCESSO_CASSACAO or UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound(
            "folhasProcessoCassacao.in=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO + "," + UPDATED_FOLHAS_PROCESSO_CASSACAO
        );

        // Get all the processoList where folhasProcessoCassacao equals to UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.in=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoCassacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao is not null
        defaultProcessoShouldBeFound("folhasProcessoCassacao.specified=true");

        // Get all the processoList where folhasProcessoCassacao is null
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoCassacaoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao contains DEFAULT_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound("folhasProcessoCassacao.contains=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO);

        // Get all the processoList where folhasProcessoCassacao contains UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.contains=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    public void getAllProcessosByFolhasProcessoCassacaoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao does not contain DEFAULT_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.doesNotContain=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO);

        // Get all the processoList where folhasProcessoCassacao does not contain UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound("folhasProcessoCassacao.doesNotContain=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    public void getAllProcessosByConcessaoLiminarIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ConcessaoLiminar concessaoLiminar = ConcessaoLiminarResourceIT.createEntity(em);
        em.persist(concessaoLiminar);
        em.flush();
        processo.setConcessaoLiminar(concessaoLiminar);
        processoRepository.saveAndFlush(processo);
        Long concessaoLiminarId = concessaoLiminar.getId();

        // Get all the processoList where concessaoLiminar equals to concessaoLiminarId
        defaultProcessoShouldBeFound("concessaoLiminarId.equals=" + concessaoLiminarId);

        // Get all the processoList where concessaoLiminar equals to concessaoLiminarId + 1
        defaultProcessoShouldNotBeFound("concessaoLiminarId.equals=" + (concessaoLiminarId + 1));
    }

    @Test
    @Transactional
    public void getAllProcessosByComarcaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        Comarca comarca = ComarcaResourceIT.createEntity(em);
        em.persist(comarca);
        em.flush();
        processo.addComarca(comarca);
        processoRepository.saveAndFlush(processo);
        Long comarcaId = comarca.getId();

        // Get all the processoList where comarca equals to comarcaId
        defaultProcessoShouldBeFound("comarcaId.equals=" + comarcaId);

        // Get all the processoList where comarca equals to comarcaId + 1
        defaultProcessoShouldNotBeFound("comarcaId.equals=" + (comarcaId + 1));
    }

    @Test
    @Transactional
    public void getAllProcessosByQuilomboIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        Quilombo quilombo = QuilomboResourceIT.createEntity(em);
        em.persist(quilombo);
        em.flush();
        processo.addQuilombo(quilombo);
        processoRepository.saveAndFlush(processo);
        Long quilomboId = quilombo.getId();

        // Get all the processoList where quilombo equals to quilomboId
        defaultProcessoShouldBeFound("quilomboId.equals=" + quilomboId);

        // Get all the processoList where quilombo equals to quilomboId + 1
        defaultProcessoShouldNotBeFound("quilomboId.equals=" + (quilomboId + 1));
    }

    @Test
    @Transactional
    public void getAllProcessosByTipoDecisaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        TipoDecisao tipoDecisao = TipoDecisaoResourceIT.createEntity(em);
        em.persist(tipoDecisao);
        em.flush();
        processo.setTipoDecisao(tipoDecisao);
        processoRepository.saveAndFlush(processo);
        Long tipoDecisaoId = tipoDecisao.getId();

        // Get all the processoList where tipoDecisao equals to tipoDecisaoId
        defaultProcessoShouldBeFound("tipoDecisaoId.equals=" + tipoDecisaoId);

        // Get all the processoList where tipoDecisao equals to tipoDecisaoId + 1
        defaultProcessoShouldNotBeFound("tipoDecisaoId.equals=" + (tipoDecisaoId + 1));
    }

    @Test
    @Transactional
    public void getAllProcessosByConcessaoLiminarCassadaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ConcessaoLiminarCassada concessaoLiminarCassada = ConcessaoLiminarCassadaResourceIT.createEntity(em);
        em.persist(concessaoLiminarCassada);
        em.flush();
        processo.setConcessaoLiminarCassada(concessaoLiminarCassada);
        processoRepository.saveAndFlush(processo);
        Long concessaoLiminarCassadaId = concessaoLiminarCassada.getId();

        // Get all the processoList where concessaoLiminarCassada equals to concessaoLiminarCassadaId
        defaultProcessoShouldBeFound("concessaoLiminarCassadaId.equals=" + concessaoLiminarCassadaId);

        // Get all the processoList where concessaoLiminarCassada equals to concessaoLiminarCassadaId + 1
        defaultProcessoShouldNotBeFound("concessaoLiminarCassadaId.equals=" + (concessaoLiminarCassadaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessoShouldBeFound(String filter) throws Exception {
        restProcessoMockMvc
            .perform(get("/api/processos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].oficio").value(hasItem(DEFAULT_OFICIO)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO.toString())))
            .andExpect(jsonPath("$.[*].linkUnico").value(hasItem(DEFAULT_LINK_UNICO)))
            .andExpect(jsonPath("$.[*].linkTrf").value(hasItem(DEFAULT_LINK_TRF)))
            .andExpect(jsonPath("$.[*].subsecaoJudiciaria").value(hasItem(DEFAULT_SUBSECAO_JUDICIARIA)))
            .andExpect(jsonPath("$.[*].turmaTrf1").value(hasItem(DEFAULT_TURMA_TRF_1)))
            .andExpect(jsonPath("$.[*].numeroProcessoAdministrativo").value(hasItem(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO)))
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstancia")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA))
            )
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstanciaLink")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK))
            )
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstanciaObservacoes")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES.toString()))
            )
            .andExpect(jsonPath("$.[*].parecer").value(hasItem(DEFAULT_PARECER.booleanValue())))
            .andExpect(jsonPath("$.[*].folhasProcessoConcessaoLiminar").value(hasItem(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)))
            .andExpect(jsonPath("$.[*].concessaoLimnarObservacoes").value(hasItem(DEFAULT_CONCESSAO_LIMNAR_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].folhasProcessoCassacao").value(hasItem(DEFAULT_FOLHAS_PROCESSO_CASSACAO)));

        // Check, that the count call also returns 1
        restProcessoMockMvc
            .perform(get("/api/processos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessoShouldNotBeFound(String filter) throws Exception {
        restProcessoMockMvc
            .perform(get("/api/processos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessoMockMvc
            .perform(get("/api/processos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProcesso() throws Exception {
        // Get the processo
        restProcessoMockMvc.perform(get("/api/processos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcesso() throws Exception {
        // Initialize the database
        processoService.save(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo
        Processo updatedProcesso = processoRepository.findById(processo.getId()).get();
        // Disconnect from session so that the updates on updatedProcesso are not directly saved in db
        em.detach(updatedProcesso);
        updatedProcesso
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .subsecaoJudiciaria(UPDATED_SUBSECAO_JUDICIARIA)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(UPDATED_PARECER)
            .folhasProcessoConcessaoLiminar(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)
            .concessaoLimnarObservacoes(UPDATED_CONCESSAO_LIMNAR_OBSERVACOES)
            .folhasProcessoCassacao(UPDATED_FOLHAS_PROCESSO_CASSACAO);

        restProcessoMockMvc
            .perform(
                put("/api/processos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedProcesso))
            )
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(UPDATED_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(UPDATED_LINK_TRF);
        assertThat(testProcesso.getSubsecaoJudiciaria()).isEqualTo(UPDATED_SUBSECAO_JUDICIARIA);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(UPDATED_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.isParecer()).isEqualTo(UPDATED_PARECER);
        assertThat(testProcesso.getFolhasProcessoConcessaoLiminar()).isEqualTo(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
        assertThat(testProcesso.getConcessaoLimnarObservacoes()).isEqualTo(UPDATED_CONCESSAO_LIMNAR_OBSERVACOES);
        assertThat(testProcesso.getFolhasProcessoCassacao()).isEqualTo(UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(put("/api/processos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcesso() throws Exception {
        // Initialize the database
        processoService.save(processo);

        int databaseSizeBeforeDelete = processoRepository.findAll().size();

        // Delete the processo
        restProcessoMockMvc
            .perform(delete("/api/processos/{id}", processo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
