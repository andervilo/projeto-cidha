package br.com.cidha.repository;

import br.com.cidha.domain.Processo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Processo entity.
 */
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    @Query(
        value = "select distinct processo from Processo processo left join fetch processo.comarcas left join fetch processo.quilombos",
        countQuery = "select count(distinct processo) from Processo processo"
    )
    Page<Processo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct processo from Processo processo left join fetch processo.comarcas left join fetch processo.quilombos")
    List<Processo> findAllWithEagerRelationships();

    @Query(
        "select processo from Processo processo left join fetch processo.comarcas left join fetch processo.quilombos where processo.id =:id"
    )
    Optional<Processo> findOneWithEagerRelationships(@Param("id") Long id);
}
