package vn.ptit.manga.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import vn.ptit.manga.dto.MangaDTO;
import vn.ptit.manga.dto.UserDTO;
import vn.ptit.manga.entity.MangaEntity;

@Component
public class MangaUtil {

	@Value("${tuanhoang.app.storage}")
	private String storage;

	@Value("${tuanhoang.app.basemediaurl}")
	public String baseMediaUrl;

	public MangaEntity _DTOtoEntity(MangaDTO mangaDTO) {
		MangaEntity mangaEntity = new MangaEntity();
		mangaEntity.setId(mangaDTO.getId());
		mangaEntity.setTitle(mangaDTO.getTitle());
		mangaEntity.setDescription(mangaDTO.getDescription());
		mangaEntity.setCreated_at(mangaDTO.getCreated_at());
		mangaEntity.setAuthor(mangaDTO.getAuthor());
		mangaEntity.setCategory(mangaDTO.getCategory());
		mangaEntity.setPath(baseMediaUrl + mangaDTO.getPath());
		return mangaEntity;
	}

	public MangaDTO _EntitytoDTO(MangaEntity mangaEntity) {
		MangaDTO mangaDTO = new MangaDTO();
		mangaDTO.setId(mangaEntity.getId());
		mangaDTO.setTitle(mangaEntity.getTitle());
		mangaDTO.setDescription(mangaEntity.getDescription());
		mangaDTO.setCreated_at(mangaEntity.getCreated_at());
		mangaDTO.setAuthor(mangaEntity.getAuthor());
		mangaDTO.setCategory(mangaEntity.getCategory());
		mangaDTO.setPath(baseMediaUrl + mangaEntity.getPath());
		return mangaDTO;
	}


	
//	private static String writeToDirectory(String directory, String base64, String filename) {
//		String[] strings = base64.split(",");
//		String extension;
//		switch (strings[0]) {// check image's extension
//		case "data:image/jpeg;base64":
//			extension = "jpeg";
//			break;
//		case "data:image/png;base64":
//			extension = "png";
//			break;
//		default:// should write cases for more images types
//			extension = "jpg";
//			break;
//		}
//		// convert base64 string to binary data
//		byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
//		String path = directory + "\\" + filename + "." + extension;
//		File file = new File(path);
//		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
//			outputStream.write(data);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return filename + "." + extension;
//	}
	public static String writeToDirectory(String directory, MultipartFile argFile, String filename) {
		String orgName = argFile.getOriginalFilename();
		String extension = FilenameUtils.getExtension(orgName);
		String path = directory + "\\" + filename + "." + extension;
		File file = new File(path);
		try {
			argFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
		
			e.printStackTrace();
		}
		return filename + "." + extension;
	}
//	private void readMetadata(String filename) {
//		File savedFile = new File(filename);
//		try {
//			Metadata metadata = ImageMetadataReader.readMetadata(savedFile);
//
//			for (Directory directory : metadata.getDirectories()) {
//
//				for (Tag tag : directory.getTags()) {
//					System.out.println(tag);
//				}
//
//				for (String error : directory.getErrors()) {
//					System.err.println("ERROR: " + error);
//				}
//			}
//		} catch (ImageProcessingException e) {
//			System.out.println(e);
//		} catch (IOException e) {
//			System.out.println(e);
//		}
//	}

	public List<MangaEntity> save(UserDTO userDTO, List<MangaDTO> mangaDTOs) throws IOException {
		List<MangaEntity> mangaEntities = new ArrayList<>();
		String directoryString = storage + "\\" + userDTO.getUsername();
		File directory = new File(directoryString);
		if (!directory.exists()) {
			directory.mkdir();
			System.out.print("No Folder");
			System.out.print("Folder created");
		}
		int fileCount = directory.list().length + 1;

		for (int i = 0; i < mangaDTOs.size(); i++, fileCount++) {
			//base 64
//			String filename = writeToDirectory(directoryString, mangaDTOs.get(i).getContent(),
//					Integer.toString(fileCount));
//			readMetadata(storage + "\\" + userDTO.getUsername() + "\\" + filename);
			String filename = writeToDirectory(directoryString, mangaDTOs.get(i).getMultipartFile(), Integer.toString(fileCount));
			filename = userDTO.getUsername() + "/" + filename;
			MangaEntity mangaEntity = _DTOtoEntity(mangaDTOs.get(i));
			mangaEntity.setPath(filename);
			mangaEntity.setUser(UserUtil._DTOtoEntity(userDTO));
			mangaEntities.add(mangaEntity);
		}
		return mangaEntities;
	}
}
