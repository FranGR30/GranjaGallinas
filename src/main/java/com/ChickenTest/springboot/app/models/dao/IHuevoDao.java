package com.ChickenTest.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ChickenTest.springboot.app.models.entity.Huevo;

public interface IHuevoDao extends CrudRepository<Huevo, Long>{

}
