package net.kkennib.house.s3.controller;

import lombok.RequiredArgsConstructor;
import net.kkennib.house.s3.service.S3Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class S3Controller {

  private final S3Service s3Service;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
    String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
    String url = s3Service.uploadFile(key, file);
    return ResponseEntity.ok(url);
  }

  @GetMapping("/download")
  public ResponseEntity<byte[]> downloadFile(@RequestParam String key) {
    byte[] fileBytes = s3Service.downloadFile(key);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
            .body(fileBytes);
  }
}