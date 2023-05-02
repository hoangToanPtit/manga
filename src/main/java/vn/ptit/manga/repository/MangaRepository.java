package vn.ptit.manga.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.ptit.manga.entity.MangaEntity;

@Repository
public interface MangaRepository extends JpaRepository<MangaEntity, Long> {
	Page<MangaEntity> findAllByUserId(Pageable pageable, Long userId);
	long countByUserId(Long userId);
}
