package br.com.cidha.service;

import br.com.cidha.domain.ConcessaoLiminarCassada;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ConcessaoLiminarCassada}.
 */
public interface ConcessaoLiminarCassadaService {
    /**
     * Save a concessaoLiminarCassada.
     *
     * @param concessaoLiminarCassada the entity to save.
     * @return the persisted entity.
     */
    ConcessaoLiminarCassada save(ConcessaoLiminarCassada concessaoLiminarCassada);

    /**
     * Get all the concessaoLiminarCassadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConcessaoLiminarCassada> findAll(Pageable pageable);

    /**
     * Get the "id" concessaoLiminarCassada.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConcessaoLiminarCassada> findOne(Long id);

    /**
     * Delete the "id" concessaoLiminarCassada.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
