package com.MusicRestApi.RestApiForMusic.Service;

import com.MusicRestApi.RestApiForMusic.Model.MusicModel;
import com.MusicRestApi.RestApiForMusic.Repository.musicdb;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class MusicService {

    private final musicdb musicdb;
    private final Path storageDir;

    public static record StreamChunk(byte[] content, long start, long end, long total) {}

    public MusicService(musicdb musicdb,
                        @org.springframework.beans.factory.annotation.Value("${app.storage.path}") String storagePath) {
        this.musicdb = musicdb;
        this.storageDir = Paths.get(storagePath).toAbsolutePath().normalize();
    }

    public boolean upload(MultipartFile file) throws Exception {
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }
        String originalName = file.getOriginalFilename();
        String safeName = Paths.get(originalName == null ? "" : originalName).getFileName().toString();
        if (safeName.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filename");
        }
        Path target = storageDir.resolve(safeName).normalize();
        if (!target.startsWith(storageDir)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filename");
        }
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        MusicModel model = new MusicModel();
        model.setPath(String.valueOf(target));
        musicdb.save(model);
        return true;
    }

    public List<MusicModel> getAllFiles() {
        return (List<MusicModel>) musicdb.findAll();
    }

    public Resource getByID(int id) throws MalformedURLException {
        Optional<MusicModel> response = musicdb.findById(id);
        if (response.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Music not found");
        }
        return new FileUrlResource(response.get().getPath());
    }

    public StreamChunk getStream(int id, String bytes) throws IOException {
        Optional<MusicModel> response = musicdb.findById(id);
        if (response.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Music not found");
        }

        Path path = Paths.get(response.get().getPath());
        byte[] musicArray = Files.readAllBytes(path);
        long start = 0;
        long finish = musicArray.length - 1;

        if (bytes != null && bytes.startsWith("bytes=")) {
            String[] part = bytes.replace("bytes=", "").split("-", 2);
            if (!part[0].isEmpty()) {
                start = Long.parseLong(part[0]);
            }
            if (part.length > 1 && !part[1].isEmpty()) {
                finish = Long.parseLong(part[1]);
            }
        }

        if (start < 0) {
            start = 0;
        }
        if (finish >= musicArray.length) {
            finish = musicArray.length - 1;
        }
        if (start > finish) {
            throw new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "Invalid range");
        }

        byte[] content = Arrays.copyOfRange(musicArray, (int) start, (int) finish + 1);
        return new StreamChunk(content, start, finish, musicArray.length);
    }


    public Path download(int id) throws IOException {
        var file=musicdb.findById(id).orElseThrow();
        return Paths.get(file.getPath());
    }

    public Boolean remove(int id){
        if(musicdb.existsById(id)){
            musicdb.deleteById(id);
            return true;
        }
        else{
            return false;
        }


    }


}
