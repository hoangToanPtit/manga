package vn.ptit.manga.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;
import vn.ptit.manga.dto.UserDTO;
import vn.ptit.manga.payload.response.MessageResponse;
import vn.ptit.manga.util.UserUtil;
import vn.ptit.manga.dto.MangaDTO;
import vn.ptit.manga.payload.response.MangaResponse;
import vn.ptit.manga.service.IMangaService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MangaAPI {

	@Value("${tuanhoang.app.storage}")
	private String storage;

	@Autowired
	private IMangaService mangaService;

	@GetMapping("/mangas")
	public ResponseEntity<?> getMangas(
			@RequestParam(required = true, value = "page") int page,
			@RequestParam(required = false, value = "limit") Optional<Integer> limit,
			@RequestParam(required = false, value = "sortParam") String sortParam,
			@RequestParam(required = false, value = "searchParam") String searchParam
	) {
		Pageable pageable = PageRequest.of(page - 1, limit.orElse(12));
		MangaResponse mangaResponse = mangaService.findAll(pageable);
		return ResponseEntity.ok(mangaResponse);
	}

	@GetMapping("/mangas/{mangaid}")
	public ResponseEntity<?> getManga(@PathVariable Long mangaid) {
		MangaResponse mangaResponse = mangaService.findById(mangaid);
		return new ResponseEntity<>(mangaResponse, HttpStatus.OK);
	}

	@GetMapping("/users/{userid}/mangas")
	public ResponseEntity<?> getMangasOfUser(
			@PathVariable Long userid,
			@RequestParam(required = true, value = "page") int page,
			@RequestParam(required = false, value = "limit") Optional<Integer> limit,
			@RequestParam(required = false, value = "sortParam") String sortParam
	) {
		Pageable pageable = PageRequest.of(page - 1, limit.orElse(12));
		MangaResponse mangaResponse = mangaService.findAllByUserId(pageable, userid);
		return ResponseEntity.ok(mangaResponse);
	}

	@PostMapping(value = "/users/{userid}/mangas", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> saveManga(@RequestParam(required = true, value = "user") String requestUserDTO,
			@RequestParam(required = true, value = "fileInfors") String fileInfors,
			@RequestParam(required = true, value = "fileContents") MultipartFile[] fileList) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		UserDTO userDTO = UserUtil._JsonToObject(requestUserDTO);
		List<MangaDTO> mangaDTOS = objectMapper.readValue(fileInfors, new TypeReference<List<MangaDTO>>() {});
		for (int i = 0; i < mangaDTOS.size(); i++)
			mangaDTOS.get(i).setMultipartFile(fileList[i]);
		MangaResponse mangaResponse = mangaService.save(userDTO, mangaDTOS);
		return ResponseEntity.ok(mangaResponse);
	}

	@DeleteMapping(value = "/mangas/{mangaid}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteManga(@PathVariable long mangaid) {
		mangaService.deleteById(mangaid);
		return ResponseEntity.ok(new MessageResponse("Delete manga successfully!"));
	}

}
