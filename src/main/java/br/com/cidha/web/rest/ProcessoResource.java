package br.com.cidha.web.rest;

import br.com.cidha.service.ProcessoService;
import br.com.cidha.service.dto.ProcessoDTO;
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
 * REST controller for managing {@link br.com.cidha.domain.Processo}.
 */
@RestController
@RequestMapping("/api")
public class ProcessoResource {
    private final Logger log = LoggerFactory.getLogger(ProcessoResource.class);

    private static final String ENTITY_NAME = "processo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessoService processoService;

    public ProcessoResource(ProcessoService processoService) {
        this.processoService = processoService;
    }

    /**
     * {@code POST  /processos} : Create a new processo.
     *
     * @param processoDTO the processoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processoDTO, or with status {@code 400 (Bad Request)} if the processo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processos")
    public ResponseEntity<ProcessoDTO> createProcesso(@RequestBody ProcessoDTO processoDTO) throws URISyntaxException {
        log.debug("REST request to save Processo : {}", processoDTO);
        if (processoDTO.getId() != null) {
            throw new BadRequestAlertException("A new processo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessoDTO result = processoService.save(processoDTO);
        return ResponseEntity
            .created(new URI("/api/processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processos} : Updates an existing processo.
     *
     * @param processoDTO the processoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processoDTO,
     * or with status {@code 400 (Bad Request)} if the processoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processos")
    public ResponseEntity<ProcessoDTO> updateProcesso(@RequestBody ProcessoDTO processoDTO) throws URISyntaxException {
        log.debug("REST request to update Processo : {}", processoDTO);
        if (processoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessoDTO result = processoService.save(processoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /processos} : get all the processos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processos in body.
     */
    @GetMapping("/processos")
    public ResponseEntity<List<ProcessoDTO>> getAllProcessos(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Processos");
        Page<ProcessoDTO> page;
        if (eagerload) {
            page = processoService.findAllWithEagerRelationships(pageable);
        } else {
            page = processoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /processos/:id} : get the "id" processo.
     *
     * @param id the id of the processoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processos/{id}")
    public ResponseEntity<ProcessoDTO> getProcesso(@PathVariable Long id) {
        log.debug("REST request to get Processo : {}", id);
        Optional<ProcessoDTO> processoDTO = processoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processoDTO);
    }

    /**
     * {@code DELETE  /processos/:id} : delete the "id" processo.
     *
     * @param id the id of the processoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processos/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        log.debug("REST request to delete Processo : {}", id);
        processoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
