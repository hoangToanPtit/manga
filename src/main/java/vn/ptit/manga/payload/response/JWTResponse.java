package vn.ptit.manga.payload.response;

import java.util.List;

import lombok.Data;
import vn.ptit.manga.dto.UserDTO;

@Data
public class JWTResponse {
	private String token;
	private String type = "Bearer";
	private UserDTO userDTO;
	private List<String> roles;

	public JWTResponse(String accesstoken, UserDTO userDTO, List<String> roles) {
		this.token = accesstoken;
		this.userDTO = userDTO;
		this.roles = roles;
	}
}
