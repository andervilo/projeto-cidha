package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ProcessoRepository;
import br.com.cidha.service.ProcessoService;
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

    @Autowired
    private ProcessoRepository processoRepository;

    @Mock
    private ProcessoRepository processoRepositoryMock;

    @Mock
    private ProcessoService processoServiceMock;

    @Autowired
    private ProcessoService processoService;

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
            .parecer(DEFAULT_PARECER);
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
            .parecer(UPDATED_PARECER);
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
            .andExpect(jsonPath("$.[*].parecer").value(hasItem(DEFAULT_PARECER.booleanValue())));
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
            .andExpect(jsonPath("$.parecer").value(DEFAULT_PARECER.booleanValue()));
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
            .parecer(UPDATED_PARECER);

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
