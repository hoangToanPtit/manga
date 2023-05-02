package vn.ptit.manga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MangaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MangaApplication.class, args);
	}

}
