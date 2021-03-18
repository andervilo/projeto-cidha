package br.com.cidha.web.rest;

import br.com.cidha.service.QuilomboService;
import br.com.cidha.service.dto.QuilomboDTO;
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
 * REST controller for managing {@link br.com.cidha.domain.Quilombo}.
 */
@RestController
@RequestMapping("/api")
public class QuilomboResource {
    private final Logger log = LoggerFactory.getLogger(QuilomboResource.class);

    private static final String ENTITY_NAME = "quilombo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuilomboService quilomboService;

    public QuilomboResource(QuilomboService quilomboService) {
        this.quilomboService = quilomboService;
    }

    /**
     * {@code POST  /quilombos} : Create a new quilombo.
     *
     * @param quilomboDTO the quilomboDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quilomboDTO, or with status {@code 400 (Bad Request)} if the quilombo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quilombos")
    public ResponseEntity<QuilomboDTO> createQuilombo(@RequestBody QuilomboDTO quilomboDTO) throws URISyntaxException {
        log.debug("REST request to save Quilombo : {}", quilomboDTO);
        if (quilomboDTO.getId() != null) {
            throw new BadRequestAlertException("A new quilombo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuilomboDTO result = quilomboService.save(quilomboDTO);
        return ResponseEntity
            .created(new URI("/api/quilombos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quilombos} : Updates an existing quilombo.
     *
     * @param quilomboDTO the quilomboDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quilomboDTO,
     * or with status {@code 400 (Bad Request)} if the quilomboDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quilomboDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quilombos")
    public ResponseEntity<QuilomboDTO> updateQuilombo(@RequestBody QuilomboDTO quilomboDTO) throws URISyntaxException {
        log.debug("REST request to update Quilombo : {}", quilomboDTO);
        if (quilomboDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuilomboDTO result = quilomboService.save(quilomboDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quilomboDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quilombos} : get all the quilombos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quilombos in body.
     */
    @GetMapping("/quilombos")
    public ResponseEntity<List<QuilomboDTO>> getAllQuilombos(Pageable pageable) {
        log.debug("REST request to get a page of Quilombos");
        Page<QuilomboDTO> page = quilomboService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quilombos/:id} : get the "id" quilombo.
     *
     * @param id the id of the quilomboDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quilomboDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quilombos/{id}")
    public ResponseEntity<QuilomboDTO> getQuilombo(@PathVariable Long id) {
        log.debug("REST request to get Quilombo : {}", id);
        Optional<QuilomboDTO> quilomboDTO = quilomboService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quilomboDTO);
    }

    /**
     * {@code DELETE  /quilombos/:id} : delete the "id" quilombo.
     *
     * @param id the id of the quilomboDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quilombos/{id}")
    public ResponseEntity<Void> deleteQuilombo(@PathVariable Long id) {
        log.debug("REST request to delete Quilombo : {}", id);
        quilomboService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
