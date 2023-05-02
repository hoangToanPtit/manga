package vn.ptit.manga.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class InitializeStorage {
    @Value("${tuanhoang.app.storage}")
    private String storage;

    @EventListener(ApplicationReadyEvent.class)
    public void initStorage() {
        File directory = new File(storage);
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Folder Storage Created");
        }
    }
}
