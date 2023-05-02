package vn.ptit.manga.service;

import vn.ptit.manga.constant.ERole;
import vn.ptit.manga.dto.RoleDTO;

public interface IRoleService {
	RoleDTO findByName(ERole name);
}
