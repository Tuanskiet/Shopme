package com.shopme.admin.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUploadUtils {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile ) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Saving photo in path : "+ filePath);
        }catch(IOException ex){
            throw new IOException("Could not found file with name : " + fileName);
        }
    }

    public static void cleanDir(String dir) throws IOException {
        Path dirPath = Paths.get(dir);
        try {
            Files.list(dirPath).forEach( file ->{
                if(Files.isDirectory(file)){
                    try{
                        log.info("Deleting file : "+ file);
                        Files.delete(file);
                    } catch(IOException ex){
                        log.info("Could not found file with name : " + file);
                    }
                }
            });


        }catch (IOException exception){
            log.info("Could not found list directory: " + dirPath);
        }
    }


}
