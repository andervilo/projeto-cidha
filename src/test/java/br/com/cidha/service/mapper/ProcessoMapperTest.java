package br.com.cidha.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProcessoMapperTest {
    private ProcessoMapper processoMapper;

    @BeforeEach
    public void setUp() {
        processoMapper = new ProcessoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(processoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(processoMapper.fromId(null)).isNull();
    }
}
