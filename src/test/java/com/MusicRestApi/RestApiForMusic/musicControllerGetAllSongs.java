package com.MusicRestApi.RestApiForMusic;

import com.MusicRestApi.RestApiForMusic.Controller.MusicController;
import com.MusicRestApi.RestApiForMusic.Model.MusicModel;
import com.MusicRestApi.RestApiForMusic.Service.MusicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static java.nio.file.Paths.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
public class musicControllerGetAllSongs {

    @Autowired
    public MockMvc mockMvc;

    @MockitoBean
    private MusicService service;

    @Test
    void getSongs() throws Exception {
        MusicModel model1=new MusicModel(1,"smth");
        MusicModel model2=new MusicModel(2,"smths2");

        when(service.getAllFiles())
                .thenReturn(List.of(model1,model2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2));


    }










}
