package com.MusicRestApi.RestApiForMusic.Service;

import com.MusicRestApi.RestApiForMusic.Model.MusicModel;
import com.MusicRestApi.RestApiForMusic.Repository.musicdb;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class MusicService {

    private final musicdb musicdb;

    public MusicService(musicdb musicdb) {
        this.musicdb = musicdb;
    }

    public boolean upload(MultipartFile file) throws Exception{
        Path path= Paths.get("/Users/mahmudkhonusmonov/Desktop/RestApiForMusic/src");
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        Path target=path.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(),target, StandardCopyOption.REPLACE_EXISTING);
        MusicModel model = new MusicModel();
        model.setPath(String.valueOf(target));
        musicdb.save(model);
        return true;
    }

    public List<MusicModel> getAllFiles() throws IOException {
       return (List<MusicModel>) musicdb.findAll();

    }

}
