package vn.ptit.manga.dto;

import lombok.Data;
import vn.ptit.manga.constant.ERole;

@Data
public class RoleDTO {
	private Integer id;

	private ERole name;

}
