package br.com.cidha.service.mapper;

import br.com.cidha.domain.*;
import br.com.cidha.service.dto.ComarcaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comarca} and its DTO {@link ComarcaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ComarcaMapper extends EntityMapper<ComarcaDTO, Comarca> {
    @Mapping(target = "processos", ignore = true)
    @Mapping(target = "removeProcesso", ignore = true)
    Comarca toEntity(ComarcaDTO comarcaDTO);

    default Comarca fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comarca comarca = new Comarca();
        comarca.setId(id);
        return comarca;
    }
}
