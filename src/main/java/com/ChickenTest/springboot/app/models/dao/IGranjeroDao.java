package com.ChickenTest.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ChickenTest.springboot.app.models.entity.Granjero;

public interface IGranjeroDao extends CrudRepository<Granjero, Long>{

}
