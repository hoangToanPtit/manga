package vn.ptit.manga.dto;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@Data
public class MangaDTO {

	private Long id;

	@JsonProperty("title")
	private String title;

	@JsonProperty("description")
	private String description;

	@JsonProperty("path")
	private String path;

	@JsonProperty("created_at")
	private Date created_at;

	@JsonProperty("author")
	private String author;

	@JsonProperty("category")
	private String category;

	@JsonIgnore
	private MultipartFile multipartFile;

}
