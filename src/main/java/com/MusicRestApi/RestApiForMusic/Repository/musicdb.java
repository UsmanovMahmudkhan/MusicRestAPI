package com.MusicRestApi.RestApiForMusic.Repository;

import com.MusicRestApi.RestApiForMusic.Model.MusicModel;
import org.springframework.data.repository.CrudRepository;

public interface musicdb extends CrudRepository<MusicModel, Integer> {

}