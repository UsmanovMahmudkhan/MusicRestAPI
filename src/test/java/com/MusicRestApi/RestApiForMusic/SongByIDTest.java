package com.MusicRestApi.RestApiForMusic;

import com.MusicRestApi.RestApiForMusic.Controller.MusicController;
import com.MusicRestApi.RestApiForMusic.Service.MusicService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
class SongByIDTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MusicService musicService;

    @Test
    void getById() throws Exception {

        byte[] fakeAudio = "test".getBytes();
        var resource = new org.springframework.core.io.ByteArrayResource(fakeAudio);

        when(musicService.getByID(1))
                .thenReturn(resource);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/1"))
                .andExpect(status().isAccepted());
    }


    @Test
    void shouldReturn404WhenSongNotFound() throws Exception {

        when(musicService.getByID(99)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/99"))
                .andExpect(status().isNotFound());
    }
}