package br.com.cidha.service.mapper;

import br.com.cidha.domain.*;
import br.com.cidha.service.dto.ProcessoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Processo} and its DTO {@link ProcessoDTO}.
 */
@Mapper(componentModel = "spring", uses = { ComarcaMapper.class, QuilomboMapper.class })
public interface ProcessoMapper extends EntityMapper<ProcessoDTO, Processo> {
    @Mapping(target = "removeComarca", ignore = true)
    @Mapping(target = "removeQuilombo", ignore = true)
    default Processo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Processo processo = new Processo();
        processo.setId(id);
        return processo;
    }
}
