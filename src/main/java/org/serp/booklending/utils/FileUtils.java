package org.serp.booklending.utils;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.StringUtil;
import org.springframework.boot.autoconfigure.ssl.SslProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {
    public static byte[] readFileFromLocation(String bookCover) {
        if(!StringUtils.isBlank(bookCover)){
            return null;
        }
        Path path = Paths.get(bookCover);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
