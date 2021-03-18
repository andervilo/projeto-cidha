package br.com.cidha.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuilomboMapperTest {
    private QuilomboMapper quilomboMapper;

    @BeforeEach
    public void setUp() {
        quilomboMapper = new QuilomboMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(quilomboMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(quilomboMapper.fromId(null)).isNull();
    }
}
