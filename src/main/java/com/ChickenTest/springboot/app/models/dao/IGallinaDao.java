package com.ChickenTest.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ChickenTest.springboot.app.models.entity.Gallina;

public interface IGallinaDao extends CrudRepository<Gallina, Long>{

}
