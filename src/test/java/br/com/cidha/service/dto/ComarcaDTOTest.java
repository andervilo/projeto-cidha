package br.com.cidha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ComarcaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComarcaDTO.class);
        ComarcaDTO comarcaDTO1 = new ComarcaDTO();
        comarcaDTO1.setId(1L);
        ComarcaDTO comarcaDTO2 = new ComarcaDTO();
        assertThat(comarcaDTO1).isNotEqualTo(comarcaDTO2);
        comarcaDTO2.setId(comarcaDTO1.getId());
        assertThat(comarcaDTO1).isEqualTo(comarcaDTO2);
        comarcaDTO2.setId(2L);
        assertThat(comarcaDTO1).isNotEqualTo(comarcaDTO2);
        comarcaDTO1.setId(null);
        assertThat(comarcaDTO1).isNotEqualTo(comarcaDTO2);
    }
}
