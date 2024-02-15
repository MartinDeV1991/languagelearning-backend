package com.devteam.languagelearning.api;

import com.devteam.languagelearning.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/db-to-csv")
    public ResponseEntity<?> convertDBFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty.");
        }
        try {
            if (!"vocab.db".equals(file.getOriginalFilename())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This is not a vocab.db file. Please upload the original vocab.db file from your Kindle.");
            }
//            String dbTables = fileService.printDBTables(file);
            String csvData = fileService.ensureTablesAndConvert(file);
            return ResponseEntity.status(HttpStatus.OK).body(csvData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Conversion failed: " + e.getMessage()) ;
        }
    }
}
