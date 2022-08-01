package com.ChickenTest.springboot.app.models.service;

import java.util.List;

import com.ChickenTest.springboot.app.models.entity.Granjero;

public interface IGranjeroService {
	
	public List<Granjero> findAll();

	public void save(Granjero granjero);
	
	public Granjero findOne(Long id);
	
	public void delete(Long id);
	
}
