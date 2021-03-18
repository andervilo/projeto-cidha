package br.com.cidha.service;

import br.com.cidha.domain.Quilombo;
import br.com.cidha.repository.QuilomboRepository;
import br.com.cidha.service.dto.QuilomboDTO;
import br.com.cidha.service.mapper.QuilomboMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Quilombo}.
 */
@Service
@Transactional
public class QuilomboService {
    private final Logger log = LoggerFactory.getLogger(QuilomboService.class);

    private final QuilomboRepository quilomboRepository;

    private final QuilomboMapper quilomboMapper;

    public QuilomboService(QuilomboRepository quilomboRepository, QuilomboMapper quilomboMapper) {
        this.quilomboRepository = quilomboRepository;
        this.quilomboMapper = quilomboMapper;
    }

    /**
     * Save a quilombo.
     *
     * @param quilomboDTO the entity to save.
     * @return the persisted entity.
     */
    public QuilomboDTO save(QuilomboDTO quilomboDTO) {
        log.debug("Request to save Quilombo : {}", quilomboDTO);
        Quilombo quilombo = quilomboMapper.toEntity(quilomboDTO);
        quilombo = quilomboRepository.save(quilombo);
        return quilomboMapper.toDto(quilombo);
    }

    /**
     * Get all the quilombos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuilomboDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quilombos");
        return quilomboRepository.findAll(pageable).map(quilomboMapper::toDto);
    }

    /**
     * Get one quilombo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuilomboDTO> findOne(Long id) {
        log.debug("Request to get Quilombo : {}", id);
        return quilomboRepository.findById(id).map(quilomboMapper::toDto);
    }

    /**
     * Delete the quilombo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Quilombo : {}", id);
        quilomboRepository.deleteById(id);
    }
}
