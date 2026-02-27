package com.glitch.main.repositories;

import org.springframework.data.repository.CrudRepository;

import com.glitch.main.models.NightImage;

public interface NightImageRepository extends CrudRepository<NightImage, Integer> {

}
