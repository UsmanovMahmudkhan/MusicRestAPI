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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public Resource getByID(int id) throws MalformedURLException {
        Optional<MusicModel> response= musicdb.findById(id);
        return new FileUrlResource(response.get().getPath());
    }

    public byte[] getStream(int id, String bytes) throws IOException {
       var music= musicdb.findById(id);
       Path path=Paths.get(music.get().getPath());
       byte[] musicArray=Files.readAllBytes(path);
       long start=0;
       long finish=musicArray.length;

       if(bytes!=null){
           String []part=bytes.replace("bytes=","").split("-");
           start = Long.parseLong(String.valueOf(part[0]));
           if(part.length>1 && !part[1].isEmpty()){
               finish=Long.parseLong(String.valueOf(part[1]));
           }
       }

       long totalLength= finish-start+1;
       byte [] content= Arrays.copyOfRange(musicArray, (int) start, (int) finish+1);

       return content;
    }



}
