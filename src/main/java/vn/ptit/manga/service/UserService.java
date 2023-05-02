package vn.ptit.manga.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.manga.constant.ERole;
import vn.ptit.manga.dto.UserDTO;
import vn.ptit.manga.entity.RoleEntity;
import vn.ptit.manga.entity.UserEntity;
import vn.ptit.manga.repository.RoleRepository;
import vn.ptit.manga.repository.UserRepository;
import vn.ptit.manga.util.UserUtil;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserUtil userUtil;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		;
		return UserUtil._EntitytoDTO(userEntity);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	@Transactional
	public UserDTO save(UserDTO userDTO) {
		RoleEntity roleEntity = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		UserEntity userEntity = UserUtil._DTOtoEntity(userDTO);
		userEntity.getRoles().add(roleEntity);
		return UserUtil._EntitytoDTO(userRepository.save(userEntity));
	}

	@Override
	public UserDTO findById(Long id, String username) {
		UserEntity userEntity = userRepository.findById(id).orElse(null);
		if (userEntity == null)
			return null;
		return UserUtil._EntitytoDTO(userEntity);
	}

	@Override
	public UserDTO changeAvatar(UserDTO userDTO) {
		try {
			UserEntity userEntity = userUtil.changeAvatar(userDTO);
			userEntity = userRepository.save(userEntity);
			return UserUtil._EntitytoDTO(userEntity);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
