package br.com.cidha.service.impl;

import br.com.cidha.domain.TipoDecisao;
import br.com.cidha.repository.TipoDecisaoRepository;
import br.com.cidha.service.TipoDecisaoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoDecisao}.
 */
@Service
@Transactional
public class TipoDecisaoServiceImpl implements TipoDecisaoService {
    private final Logger log = LoggerFactory.getLogger(TipoDecisaoServiceImpl.class);

    private final TipoDecisaoRepository tipoDecisaoRepository;

    public TipoDecisaoServiceImpl(TipoDecisaoRepository tipoDecisaoRepository) {
        this.tipoDecisaoRepository = tipoDecisaoRepository;
    }

    @Override
    public TipoDecisao save(TipoDecisao tipoDecisao) {
        log.debug("Request to save TipoDecisao : {}", tipoDecisao);
        return tipoDecisaoRepository.save(tipoDecisao);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoDecisao> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDecisaos");
        return tipoDecisaoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDecisao> findOne(Long id) {
        log.debug("Request to get TipoDecisao : {}", id);
        return tipoDecisaoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDecisao : {}", id);
        tipoDecisaoRepository.deleteById(id);
    }
}
