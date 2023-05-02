package vn.ptit.manga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ptit.manga.constant.ERole;
import vn.ptit.manga.dto.RoleDTO;
import vn.ptit.manga.repository.RoleRepository;
import vn.ptit.manga.service.IRoleService;
import vn.ptit.manga.util.RoleUtil;

import java.util.Objects;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleDTO findByName(ERole name) {
		return RoleUtil._EntitytoDTO(Objects.requireNonNull(roleRepository.findByName(name).orElse(null)));
	}
	
}
