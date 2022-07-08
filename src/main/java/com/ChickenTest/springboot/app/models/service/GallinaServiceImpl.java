package com.ChickenTest.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ChickenTest.springboot.app.models.dao.IGallinaDao;
import com.ChickenTest.springboot.app.models.entity.Gallina;

public class GallinaServiceImpl implements IGallinaService {

	@Autowired
	private IGallinaDao gallinaDao;
	
	@Override
	public List<Gallina> findAll() {
		return (List<Gallina>) gallinaDao.findAll();
	}

	@Override
	public void save(Gallina gallina) {
		// TODO Auto-generated method stub
		gallinaDao.save(gallina);
	}

	@Override
	public Gallina findOne(Long id) {
		// TODO Auto-generated method stub
		return gallinaDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		gallinaDao.deleteById(id);
	}

}
