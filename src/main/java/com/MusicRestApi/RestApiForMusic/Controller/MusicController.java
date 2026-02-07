package com.MusicRestApi.RestApiForMusic.Controller;

import com.MusicRestApi.RestApiForMusic.Model.MusicModel;
import com.MusicRestApi.RestApiForMusic.Service.MusicService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MusicController {
private final MusicService service;

    public MusicController(MusicService service) {
        this.service = service;
    }

    // POST - /api/songs/upload
    @PostMapping("/songs/upload")
    public void responseEntity(@RequestParam MultipartFile uploadFile) throws Exception {
        service.upload(uploadFile);
    }

    //GET - /api/songs
    @GetMapping("/songs")
    public ResponseEntity<List<MusicModel>>getAll() throws IOException {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(service.getAllFiles());
    }

    //GET- /api/songs/{id}
    @GetMapping("/songs/{id}")
    public ResponseEntity<Resource>getbyID(@PathVariable int id) throws MalformedURLException {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename:"+service.getByID(id).getFilename())
                .contentType(MediaType.valueOf("audio/mpeg"))
                .body(service.getByID(id));

    }


}
