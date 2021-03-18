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
import br.com.cidha.service.dto.ProcessoDTO;
import br.com.cidha.service.mapper.ProcessoMapper;
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

    private static final String DEFAULT_SECAO_JUDICIARIA = "AAAAAAAAAA";
    private static final String UPDATED_SECAO_JUDICIARIA = "BBBBBBBBBB";

    @Autowired
    private ProcessoRepository processoRepository;

    @Mock
    private ProcessoRepository processoRepositoryMock;

    @Autowired
    private ProcessoMapper processoMapper;

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
            .secaoJudiciaria(DEFAULT_SECAO_JUDICIARIA);
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
            .secaoJudiciaria(UPDATED_SECAO_JUDICIARIA);
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
        ProcessoDTO processoDTO = processoMapper.toDto(processo);
        restProcessoMockMvc
            .perform(post("/api/processos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isCreated());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate + 1);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getOficio()).isEqualTo(DEFAULT_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(DEFAULT_LINK_UNICO);
        assertThat(testProcesso.getSecaoJudiciaria()).isEqualTo(DEFAULT_SECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    public void createProcessoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // Create the Processo with an existing ID
        processo.setId(1L);
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoMockMvc
            .perform(post("/api/processos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
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
            .andExpect(jsonPath("$.[*].secaoJudiciaria").value(hasItem(DEFAULT_SECAO_JUDICIARIA)));
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
            .andExpect(jsonPath("$.secaoJudiciaria").value(DEFAULT_SECAO_JUDICIARIA));
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
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo
        Processo updatedProcesso = processoRepository.findById(processo.getId()).get();
        // Disconnect from session so that the updates on updatedProcesso are not directly saved in db
        em.detach(updatedProcesso);
        updatedProcesso
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .secaoJudiciaria(UPDATED_SECAO_JUDICIARIA);
        ProcessoDTO processoDTO = processoMapper.toDto(updatedProcesso);

        restProcessoMockMvc
            .perform(put("/api/processos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(UPDATED_LINK_UNICO);
        assertThat(testProcesso.getSecaoJudiciaria()).isEqualTo(UPDATED_SECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    public void updateNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(put("/api/processos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

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
