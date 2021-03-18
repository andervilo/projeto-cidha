package br.com.cidha.web.rest;

import br.com.cidha.domain.ConcessaoLiminar;
import br.com.cidha.service.ConcessaoLiminarService;
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
 * REST controller for managing {@link br.com.cidha.domain.ConcessaoLiminar}.
 */
@RestController
@RequestMapping("/api")
public class ConcessaoLiminarResource {
    private final Logger log = LoggerFactory.getLogger(ConcessaoLiminarResource.class);

    private static final String ENTITY_NAME = "concessaoLiminar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcessaoLiminarService concessaoLiminarService;

    public ConcessaoLiminarResource(ConcessaoLiminarService concessaoLiminarService) {
        this.concessaoLiminarService = concessaoLiminarService;
    }

    /**
     * {@code POST  /concessao-liminars} : Create a new concessaoLiminar.
     *
     * @param concessaoLiminar the concessaoLiminar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concessaoLiminar, or with status {@code 400 (Bad Request)} if the concessaoLiminar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concessao-liminars")
    public ResponseEntity<ConcessaoLiminar> createConcessaoLiminar(@RequestBody ConcessaoLiminar concessaoLiminar)
        throws URISyntaxException {
        log.debug("REST request to save ConcessaoLiminar : {}", concessaoLiminar);
        if (concessaoLiminar.getId() != null) {
            throw new BadRequestAlertException("A new concessaoLiminar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConcessaoLiminar result = concessaoLiminarService.save(concessaoLiminar);
        return ResponseEntity
            .created(new URI("/api/concessao-liminars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concessao-liminars} : Updates an existing concessaoLiminar.
     *
     * @param concessaoLiminar the concessaoLiminar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concessaoLiminar,
     * or with status {@code 400 (Bad Request)} if the concessaoLiminar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concessaoLiminar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concessao-liminars")
    public ResponseEntity<ConcessaoLiminar> updateConcessaoLiminar(@RequestBody ConcessaoLiminar concessaoLiminar)
        throws URISyntaxException {
        log.debug("REST request to update ConcessaoLiminar : {}", concessaoLiminar);
        if (concessaoLiminar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConcessaoLiminar result = concessaoLiminarService.save(concessaoLiminar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concessaoLiminar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concessao-liminars} : get all the concessaoLiminars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concessaoLiminars in body.
     */
    @GetMapping("/concessao-liminars")
    public ResponseEntity<List<ConcessaoLiminar>> getAllConcessaoLiminars(Pageable pageable) {
        log.debug("REST request to get a page of ConcessaoLiminars");
        Page<ConcessaoLiminar> page = concessaoLiminarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /concessao-liminars/:id} : get the "id" concessaoLiminar.
     *
     * @param id the id of the concessaoLiminar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concessaoLiminar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concessao-liminars/{id}")
    public ResponseEntity<ConcessaoLiminar> getConcessaoLiminar(@PathVariable Long id) {
        log.debug("REST request to get ConcessaoLiminar : {}", id);
        Optional<ConcessaoLiminar> concessaoLiminar = concessaoLiminarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concessaoLiminar);
    }

    /**
     * {@code DELETE  /concessao-liminars/:id} : delete the "id" concessaoLiminar.
     *
     * @param id the id of the concessaoLiminar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concessao-liminars/{id}")
    public ResponseEntity<Void> deleteConcessaoLiminar(@PathVariable Long id) {
        log.debug("REST request to delete ConcessaoLiminar : {}", id);
        concessaoLiminarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
