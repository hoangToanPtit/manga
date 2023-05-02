package vn.ptit.manga.service;

import org.springframework.data.domain.Pageable;
import vn.ptit.manga.dto.MangaDTO;
import vn.ptit.manga.dto.UserDTO;
import vn.ptit.manga.payload.response.MangaResponse;
import java.util.List;

public interface IMangaService {
	MangaResponse save(UserDTO userDTO, List<MangaDTO> mangaDTOS);
	MangaResponse update(UserDTO userDTO, List<MangaDTO> mangaDTOS);
	void deleteById(long mangaid);
	MangaResponse findById(Long mangaid);
	MangaResponse findAll(Pageable pageable);
	MangaResponse findAllByUserId(Pageable pageable, Long userid);
	long count();
	long countByUserId(Long userId);

}
