package vn.ptit.manga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ptit.manga.constant.ERole;
import vn.ptit.manga.entity.RoleEntity;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByName(ERole name);
}
