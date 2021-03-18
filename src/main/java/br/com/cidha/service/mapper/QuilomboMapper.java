package br.com.cidha.service.mapper;

import br.com.cidha.domain.*;
import br.com.cidha.service.dto.QuilomboDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quilombo} and its DTO {@link QuilomboDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuilomboMapper extends EntityMapper<QuilomboDTO, Quilombo> {
    @Mapping(target = "processos", ignore = true)
    @Mapping(target = "removeProcesso", ignore = true)
    Quilombo toEntity(QuilomboDTO quilomboDTO);

    default Quilombo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Quilombo quilombo = new Quilombo();
        quilombo.setId(id);
        return quilombo;
    }
}
