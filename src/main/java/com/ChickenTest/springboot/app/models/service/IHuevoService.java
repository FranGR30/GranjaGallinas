package com.ChickenTest.springboot.app.models.service;

import java.util.List;

import com.ChickenTest.springboot.app.models.entity.Huevo;

public interface IHuevoService {
	
	public List<Huevo> findAll();

	public void save(Huevo huevo);
	
	public Huevo findOne(Long id);
	
	public void delete(Long id);

}
