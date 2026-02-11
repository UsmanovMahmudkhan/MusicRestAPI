package com.MusicRestApi.RestApiForMusic;

import com.MusicRestApi.RestApiForMusic.Controller.MusicController;
import com.MusicRestApi.RestApiForMusic.Service.MusicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
class MusicControllerUploadTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MusicService musicService;

    @Test
    void uploadSong_success() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "uploadFile",
                "song.mp3",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "fake-mp3-content".getBytes()
        );

        mockMvc.perform(
                        multipart("/api/songs/upload")
                                .file(file)
                )
                .andExpect(status().isOk());

        verify(musicService).upload(file);
    }
}