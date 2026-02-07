package com.MusicRestApi.RestApiForMusic.Controller;

import com.MusicRestApi.RestApiForMusic.Service.MusicService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api")
public class MusicController {
private final MusicService service;

    public MusicController(MusicService service) {
        this.service = service;
    }

    @PostMapping("/songs/upload")
    public void responseEntity(@RequestParam MultipartFile uploadFile) throws Exception {
        service.upload(uploadFile);
    }


}
