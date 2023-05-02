package vn.ptit.manga.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.manga.dto.UserDTO;
import vn.ptit.manga.service.IUserService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserAPI {

	@Autowired
	IUserService userService;

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id, @RequestParam(value = "username") String username) {
		UserDTO userDTO = userService.findById(id, username);
		return userDTO == null ?  new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/users/{userid}/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> changeAvatar(@PathVariable Long userid,  
			@RequestParam(required = true, value = "avatar") MultipartFile file) {
		UserDTO userDTO = userService.findById(userid, null);
		userDTO.setMultipartFile(file);
		userDTO = userService.changeAvatar(userDTO);
		return ResponseEntity.ok(userDTO);
	}

}
