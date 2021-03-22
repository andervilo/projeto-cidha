package br.com.cidha.service.impl;

import br.com.cidha.domain.ConcessaoLiminarCassada;
import br.com.cidha.repository.ConcessaoLiminarCassadaRepository;
import br.com.cidha.service.ConcessaoLiminarCassadaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ConcessaoLiminarCassada}.
 */
@Service
@Transactional
public class ConcessaoLiminarCassadaServiceImpl implements ConcessaoLiminarCassadaService {
    private final Logger log = LoggerFactory.getLogger(ConcessaoLiminarCassadaServiceImpl.class);

    private final ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository;

    public ConcessaoLiminarCassadaServiceImpl(ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository) {
        this.concessaoLiminarCassadaRepository = concessaoLiminarCassadaRepository;
    }

    @Override
    public ConcessaoLiminarCassada save(ConcessaoLiminarCassada concessaoLiminarCassada) {
        log.debug("Request to save ConcessaoLiminarCassada : {}", concessaoLiminarCassada);
        return concessaoLiminarCassadaRepository.save(concessaoLiminarCassada);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConcessaoLiminarCassada> findAll(Pageable pageable) {
        log.debug("Request to get all ConcessaoLiminarCassadas");
        return concessaoLiminarCassadaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConcessaoLiminarCassada> findOne(Long id) {
        log.debug("Request to get ConcessaoLiminarCassada : {}", id);
        return concessaoLiminarCassadaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConcessaoLiminarCassada : {}", id);
        concessaoLiminarCassadaRepository.deleteById(id);
    }
}
