package vn.ptit.manga.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.manga.dto.UserDTO;
import vn.ptit.manga.payload.response.JWTResponse;
import vn.ptit.manga.payload.response.MessageResponse;
import vn.ptit.manga.security.JWTUtil;
import vn.ptit.manga.service.IUserService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationAPI {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JWTUtil jwtUtil;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticationUser(@Valid @RequestBody UserDTO requestUserDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(requestUserDTO.getUsername(), requestUserDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);

		UserDTO userDTO = (UserDTO) authentication.getPrincipal();
		List<String> roles = userDTO.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JWTResponse(jwt, userDTO, roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO requestUserDTO) {
		if (userService.existsByUsername(requestUserDTO.getUsername()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		if (userService.existsByEmail(requestUserDTO.getEmail()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		UserDTO userDTO = new UserDTO(requestUserDTO.getUsername(), requestUserDTO.getFullname(),
				requestUserDTO.getEmail(), passwordEncoder.encode(requestUserDTO.getPassword()));
		userDTO = userService.save(userDTO);
		return ResponseEntity.ok(userDTO);
	}

}
