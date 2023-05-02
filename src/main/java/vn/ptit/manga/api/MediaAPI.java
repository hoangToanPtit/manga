package vn.ptit.manga.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
public class MediaAPI {

    @Value("${tuanhoang.app.storage}")
    private String storage;

    @GetMapping(value = "/media/{username}/{filename}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> getImage(@PathVariable String username, @PathVariable String filename) throws IOException {
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                storage + "\\" + username + "\\" + filename
        )));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(inputStream.getByteArray(), headers, HttpStatus.OK);
    }

}
