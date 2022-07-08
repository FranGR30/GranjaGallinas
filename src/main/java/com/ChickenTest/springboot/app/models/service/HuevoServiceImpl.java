package com.ChickenTest.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ChickenTest.springboot.app.models.dao.IHuevoDao;
import com.ChickenTest.springboot.app.models.entity.Huevo;

public class HuevoServiceImpl implements IHuevoService {

	@Autowired
	private IHuevoDao huevoDao;
	
	@Override
	public List<Huevo> findAll() {
		return (List<Huevo>) huevoDao.findAll();
	}

	@Override
	public void save(Huevo huevo) {
		// TODO Auto-generated method stub
		huevoDao.save(huevo);
	}

	@Override
	public Huevo findOne(Long id) {
		// TODO Auto-generated method stub
		return huevoDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		huevoDao.deleteById(id);
	}

}
