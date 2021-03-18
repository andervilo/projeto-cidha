package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Comarca;
import br.com.cidha.repository.ComarcaRepository;
import br.com.cidha.service.ComarcaService;
import br.com.cidha.service.dto.ComarcaDTO;
import br.com.cidha.service.mapper.ComarcaMapper;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ComarcaResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ComarcaResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CODIGO_CNJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_CODIGO_CNJ = new BigDecimal(2);

    @Autowired
    private ComarcaRepository comarcaRepository;

    @Autowired
    private ComarcaMapper comarcaMapper;

    @Autowired
    private ComarcaService comarcaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComarcaMockMvc;

    private Comarca comarca;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comarca createEntity(EntityManager em) {
        Comarca comarca = new Comarca().nome(DEFAULT_NOME).codigoCnj(DEFAULT_CODIGO_CNJ);
        return comarca;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comarca createUpdatedEntity(EntityManager em) {
        Comarca comarca = new Comarca().nome(UPDATED_NOME).codigoCnj(UPDATED_CODIGO_CNJ);
        return comarca;
    }

    @BeforeEach
    public void initTest() {
        comarca = createEntity(em);
    }

    @Test
    @Transactional
    public void createComarca() throws Exception {
        int databaseSizeBeforeCreate = comarcaRepository.findAll().size();
        // Create the Comarca
        ComarcaDTO comarcaDTO = comarcaMapper.toDto(comarca);
        restComarcaMockMvc
            .perform(post("/api/comarcas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comarcaDTO)))
            .andExpect(status().isCreated());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeCreate + 1);
        Comarca testComarca = comarcaList.get(comarcaList.size() - 1);
        assertThat(testComarca.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testComarca.getCodigoCnj()).isEqualTo(DEFAULT_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void createComarcaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comarcaRepository.findAll().size();

        // Create the Comarca with an existing ID
        comarca.setId(1L);
        ComarcaDTO comarcaDTO = comarcaMapper.toDto(comarca);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComarcaMockMvc
            .perform(post("/api/comarcas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comarcaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComarcas() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList
        restComarcaMockMvc
            .perform(get("/api/comarcas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comarca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigoCnj").value(hasItem(DEFAULT_CODIGO_CNJ.intValue())));
    }

    @Test
    @Transactional
    public void getComarca() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get the comarca
        restComarcaMockMvc
            .perform(get("/api/comarcas/{id}", comarca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comarca.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.codigoCnj").value(DEFAULT_CODIGO_CNJ.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingComarca() throws Exception {
        // Get the comarca
        restComarcaMockMvc.perform(get("/api/comarcas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComarca() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();

        // Update the comarca
        Comarca updatedComarca = comarcaRepository.findById(comarca.getId()).get();
        // Disconnect from session so that the updates on updatedComarca are not directly saved in db
        em.detach(updatedComarca);
        updatedComarca.nome(UPDATED_NOME).codigoCnj(UPDATED_CODIGO_CNJ);
        ComarcaDTO comarcaDTO = comarcaMapper.toDto(updatedComarca);

        restComarcaMockMvc
            .perform(put("/api/comarcas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comarcaDTO)))
            .andExpect(status().isOk());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
        Comarca testComarca = comarcaList.get(comarcaList.size() - 1);
        assertThat(testComarca.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testComarca.getCodigoCnj()).isEqualTo(UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void updateNonExistingComarca() throws Exception {
        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();

        // Create the Comarca
        ComarcaDTO comarcaDTO = comarcaMapper.toDto(comarca);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComarcaMockMvc
            .perform(put("/api/comarcas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comarcaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComarca() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        int databaseSizeBeforeDelete = comarcaRepository.findAll().size();

        // Delete the comarca
        restComarcaMockMvc
            .perform(delete("/api/comarcas/{id}", comarca.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
