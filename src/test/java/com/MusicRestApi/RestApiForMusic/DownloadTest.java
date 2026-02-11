package com.MusicRestApi.RestApiForMusic;

import com.MusicRestApi.RestApiForMusic.Controller.MusicController;
import com.MusicRestApi.RestApiForMusic.Service.MusicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import java.nio.file.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MusicController.class)
class DownloadTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MusicService service;

    @Test
    void testDownloadSuccess() throws Exception {
        Path path = Path.of("/Users/mahmudkhonusmonov/Desktop/RestApiForMusic/src/How-You-Can-Become-a-Professor-at-Sejong-University-2025-2.wav");
        when(service.download(1)).thenReturn(path);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/1/download"))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
    }
}