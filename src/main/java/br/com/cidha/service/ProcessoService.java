package br.com.cidha.service;

import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ProcessoRepository;
import br.com.cidha.service.dto.ProcessoDTO;
import br.com.cidha.service.mapper.ProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Processo}.
 */
@Service
@Transactional
public class ProcessoService {
    private final Logger log = LoggerFactory.getLogger(ProcessoService.class);

    private final ProcessoRepository processoRepository;

    private final ProcessoMapper processoMapper;

    public ProcessoService(ProcessoRepository processoRepository, ProcessoMapper processoMapper) {
        this.processoRepository = processoRepository;
        this.processoMapper = processoMapper;
    }

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessoDTO save(ProcessoDTO processoDTO) {
        log.debug("Request to save Processo : {}", processoDTO);
        Processo processo = processoMapper.toEntity(processoDTO);
        processo = processoRepository.save(processo);
        return processoMapper.toDto(processo);
    }

    /**
     * Get all the processos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Processos");
        return processoRepository.findAll(pageable).map(processoMapper::toDto);
    }

    /**
     * Get all the processos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProcessoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return processoRepository.findAllWithEagerRelationships(pageable).map(processoMapper::toDto);
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessoDTO> findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        return processoRepository.findOneWithEagerRelationships(id).map(processoMapper::toDto);
    }

    /**
     * Delete the processo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        processoRepository.deleteById(id);
    }
}
