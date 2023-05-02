package vn.ptit.manga.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.manga.dto.MangaDTO;
import vn.ptit.manga.dto.UserDTO;
import vn.ptit.manga.entity.MangaEntity;
import vn.ptit.manga.payload.response.MangaResponse;
import vn.ptit.manga.repository.MangaRepository;
import vn.ptit.manga.repository.UserRepository;
import vn.ptit.manga.util.MangaUtil;

@Service
public class MangaService implements IMangaService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MangaRepository mangaRepository;

	@Autowired
	private MangaUtil mangaUtil;

	@Override
	@Transactional
	public MangaResponse save(UserDTO userDTO, List<MangaDTO> mangaDTOS) {
		try {
			MangaResponse mangaResponse = new MangaResponse();
			List<MangaEntity> mangaEntities = mangaUtil.save(userDTO, mangaDTOS);
			List<MangaDTO> mangaDTOsSaved = new ArrayList<>();
			mangaEntities = mangaRepository.saveAll(mangaEntities);
			mangaEntities.forEach((mangaEntity) -> {
				mangaDTOsSaved.add(mangaUtil._EntitytoDTO(mangaEntity));
			});
			mangaResponse.setPage(1);
			mangaResponse.setTotal(mangaDTOsSaved.size());
			mangaResponse.setLimit(mangaDTOsSaved.size());
			mangaResponse.setData(mangaDTOsSaved);
			return mangaResponse;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MangaResponse update(UserDTO userDTO, List<MangaDTO> mangaDTOS) {
		return null;
	}

	@Override
	public void deleteById(long mangaid) {
		mangaRepository.delete(mangaRepository.getReferenceById(mangaid));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
	public MangaResponse findById(Long mangaid) {
		MangaResponse mangaResponse = new MangaResponse();
		List<MangaDTO> mangaDTOS = new ArrayList<>();
		MangaEntity mangaEntity = mangaRepository.findById(mangaid).orElse(null);
		if (mangaEntity != null) {
			mangaDTOS.add(mangaUtil._EntitytoDTO(mangaEntity));
			mangaResponse.setData(mangaDTOS);
			mangaResponse.setPage(1);
			mangaResponse.setTotal(1);
			mangaResponse.setLimit(1);
		}
		else {
			mangaResponse.setData(mangaDTOS);
			mangaResponse.setPage(1);
			mangaResponse.setTotal(0);
			mangaResponse.setLimit(1);
		}
		return mangaResponse;
	}

	@Override
	public MangaResponse findAll(Pageable pageable) {
		List<MangaEntity> mangaEntities = mangaRepository.findAll(pageable).getContent();
		List<MangaDTO> mangaDTOS = new ArrayList<>();
		MangaResponse mangaResponse = new MangaResponse();
		mangaEntities.forEach((mangaEntity) -> {
			mangaDTOS.add(mangaUtil._EntitytoDTO(mangaEntity));
		});
		mangaResponse.setData(mangaDTOS);
		mangaResponse.setLimit(pageable.getPageSize());
		mangaResponse.setPage(pageable.getPageNumber()+1);
		mangaResponse.setTotal(count());
		return mangaResponse;
	}

	@Override
	public MangaResponse findAllByUserId(Pageable pageable, Long userid) {
		List<MangaEntity> mangaEntities = mangaRepository.findAllByUserId(pageable, userid).getContent();
		List<MangaDTO> mangaDTOS = new ArrayList<>();
		MangaResponse mangaResponse = new MangaResponse();
		mangaEntities.forEach((mangaEntity) -> {
			mangaDTOS.add(mangaUtil._EntitytoDTO(mangaEntity));
		});
		mangaResponse.setData(mangaDTOS);
		mangaResponse.setLimit(pageable.getPageSize());
		mangaResponse.setPage(pageable.getPageNumber()+1);
		mangaResponse.setTotal(countByUserId(userid));
		return mangaResponse;
	}

	@Override
	public long count() {
		return mangaRepository.count();
	}

	@Override
	public long countByUserId(Long userId) {
		return mangaRepository.countByUserId(userId);
	}

}
