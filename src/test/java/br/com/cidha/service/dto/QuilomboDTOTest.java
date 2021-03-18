package br.com.cidha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class QuilomboDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuilomboDTO.class);
        QuilomboDTO quilomboDTO1 = new QuilomboDTO();
        quilomboDTO1.setId(1L);
        QuilomboDTO quilomboDTO2 = new QuilomboDTO();
        assertThat(quilomboDTO1).isNotEqualTo(quilomboDTO2);
        quilomboDTO2.setId(quilomboDTO1.getId());
        assertThat(quilomboDTO1).isEqualTo(quilomboDTO2);
        quilomboDTO2.setId(2L);
        assertThat(quilomboDTO1).isNotEqualTo(quilomboDTO2);
        quilomboDTO1.setId(null);
        assertThat(quilomboDTO1).isNotEqualTo(quilomboDTO2);
    }
}
