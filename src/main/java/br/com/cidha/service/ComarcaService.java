package br.com.cidha.service;

import br.com.cidha.domain.Comarca;
import br.com.cidha.repository.ComarcaRepository;
import br.com.cidha.service.dto.ComarcaDTO;
import br.com.cidha.service.mapper.ComarcaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comarca}.
 */
@Service
@Transactional
public class ComarcaService {
    private final Logger log = LoggerFactory.getLogger(ComarcaService.class);

    private final ComarcaRepository comarcaRepository;

    private final ComarcaMapper comarcaMapper;

    public ComarcaService(ComarcaRepository comarcaRepository, ComarcaMapper comarcaMapper) {
        this.comarcaRepository = comarcaRepository;
        this.comarcaMapper = comarcaMapper;
    }

    /**
     * Save a comarca.
     *
     * @param comarcaDTO the entity to save.
     * @return the persisted entity.
     */
    public ComarcaDTO save(ComarcaDTO comarcaDTO) {
        log.debug("Request to save Comarca : {}", comarcaDTO);
        Comarca comarca = comarcaMapper.toEntity(comarcaDTO);
        comarca = comarcaRepository.save(comarca);
        return comarcaMapper.toDto(comarca);
    }

    /**
     * Get all the comarcas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComarcaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comarcas");
        return comarcaRepository.findAll(pageable).map(comarcaMapper::toDto);
    }

    /**
     * Get one comarca by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComarcaDTO> findOne(Long id) {
        log.debug("Request to get Comarca : {}", id);
        return comarcaRepository.findById(id).map(comarcaMapper::toDto);
    }

    /**
     * Delete the comarca by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Comarca : {}", id);
        comarcaRepository.deleteById(id);
    }
}
