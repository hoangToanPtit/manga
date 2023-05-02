package vn.ptit.manga.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("user")
@Data
public class UserDTO implements UserDetails {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id;

	@NotBlank
	@JsonProperty("username")
	private String username;

	@JsonProperty("fullname")
	private String fullname;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank
	private String password;

	@JsonProperty("email")
	private String email;

	@JsonProperty("avatar")
	private String avatar;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private MultipartFile multipartFile;

	@JsonIgnore
	private Set<RoleDTO> roles = new HashSet<>();

	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorities;


	public UserDTO() {

	}

	public UserDTO(String username, String fullname, String email, String password) {
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

}
