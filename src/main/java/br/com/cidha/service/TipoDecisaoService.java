package br.com.cidha.service;

import br.com.cidha.domain.TipoDecisao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoDecisao}.
 */
public interface TipoDecisaoService {
    /**
     * Save a tipoDecisao.
     *
     * @param tipoDecisao the entity to save.
     * @return the persisted entity.
     */
    TipoDecisao save(TipoDecisao tipoDecisao);

    /**
     * Get all the tipoDecisaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoDecisao> findAll(Pageable pageable);

    /**
     * Get the "id" tipoDecisao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoDecisao> findOne(Long id);

    /**
     * Delete the "id" tipoDecisao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
