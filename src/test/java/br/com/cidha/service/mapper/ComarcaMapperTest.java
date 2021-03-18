package br.com.cidha.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComarcaMapperTest {
    private ComarcaMapper comarcaMapper;

    @BeforeEach
    public void setUp() {
        comarcaMapper = new ComarcaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(comarcaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(comarcaMapper.fromId(null)).isNull();
    }
}
