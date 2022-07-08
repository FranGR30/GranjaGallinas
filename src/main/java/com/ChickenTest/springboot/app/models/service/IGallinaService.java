package com.ChickenTest.springboot.app.models.service;

import java.util.List;

import com.ChickenTest.springboot.app.models.entity.Gallina;

public interface IGallinaService {
	
	public List<Gallina> findAll();

	public void save(Gallina gallina);
	
	public Gallina findOne(Long id);
	
	public void delete(Long id);

}
