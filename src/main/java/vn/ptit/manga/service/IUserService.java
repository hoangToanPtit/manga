package vn.ptit.manga.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import vn.ptit.manga.dto.UserDTO;

public interface IUserService extends UserDetailsService {
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	UserDTO save(UserDTO userDTO);

	UserDTO changeAvatar(UserDTO userDTO);
	UserDTO findById(Long id, String username);

}
