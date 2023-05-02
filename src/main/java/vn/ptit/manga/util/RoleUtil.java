package vn.ptit.manga.util;

import org.springframework.stereotype.Component;

import vn.ptit.manga.dto.RoleDTO;
import vn.ptit.manga.entity.RoleEntity;

@Component
public class RoleUtil {
	public static RoleEntity _DTOtoEntity(RoleDTO roleDTO) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setId(roleDTO.getId());
		roleEntity.setName(roleDTO.getName());
		return roleEntity;
		
	}
	public static RoleDTO _EntitytoDTO(RoleEntity roleEntity) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(roleEntity.getId());
		roleDTO.setName(roleEntity.getName());
		return roleDTO;
		
	}
}
