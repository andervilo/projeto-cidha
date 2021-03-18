package br.com.cidha.web.rest;

import br.com.cidha.service.ComarcaService;
import br.com.cidha.service.dto.ComarcaDTO;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link br.com.cidha.domain.Comarca}.
 */
@RestController
@RequestMapping("/api")
public class ComarcaResource {
    private final Logger log = LoggerFactory.getLogger(ComarcaResource.class);

    private static final String ENTITY_NAME = "comarca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComarcaService comarcaService;

    public ComarcaResource(ComarcaService comarcaService) {
        this.comarcaService = comarcaService;
    }

    /**
     * {@code POST  /comarcas} : Create a new comarca.
     *
     * @param comarcaDTO the comarcaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comarcaDTO, or with status {@code 400 (Bad Request)} if the comarca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comarcas")
    public ResponseEntity<ComarcaDTO> createComarca(@RequestBody ComarcaDTO comarcaDTO) throws URISyntaxException {
        log.debug("REST request to save Comarca : {}", comarcaDTO);
        if (comarcaDTO.getId() != null) {
            throw new BadRequestAlertException("A new comarca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComarcaDTO result = comarcaService.save(comarcaDTO);
        return ResponseEntity
            .created(new URI("/api/comarcas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comarcas} : Updates an existing comarca.
     *
     * @param comarcaDTO the comarcaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comarcaDTO,
     * or with status {@code 400 (Bad Request)} if the comarcaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comarcaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comarcas")
    public ResponseEntity<ComarcaDTO> updateComarca(@RequestBody ComarcaDTO comarcaDTO) throws URISyntaxException {
        log.debug("REST request to update Comarca : {}", comarcaDTO);
        if (comarcaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComarcaDTO result = comarcaService.save(comarcaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comarcaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comarcas} : get all the comarcas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comarcas in body.
     */
    @GetMapping("/comarcas")
    public ResponseEntity<List<ComarcaDTO>> getAllComarcas(Pageable pageable) {
        log.debug("REST request to get a page of Comarcas");
        Page<ComarcaDTO> page = comarcaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comarcas/:id} : get the "id" comarca.
     *
     * @param id the id of the comarcaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comarcaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comarcas/{id}")
    public ResponseEntity<ComarcaDTO> getComarca(@PathVariable Long id) {
        log.debug("REST request to get Comarca : {}", id);
        Optional<ComarcaDTO> comarcaDTO = comarcaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comarcaDTO);
    }

    /**
     * {@code DELETE  /comarcas/:id} : delete the "id" comarca.
     *
     * @param id the id of the comarcaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comarcas/{id}")
    public ResponseEntity<Void> deleteComarca(@PathVariable Long id) {
        log.debug("REST request to delete Comarca : {}", id);
        comarcaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
