package com.ChickenTest.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChickenTest.springboot.app.models.dao.IGranjeroDao;
import com.ChickenTest.springboot.app.models.entity.Granjero;

@Service
public class GranjeroServiceImpl implements IGranjeroService {
	
	@Autowired
	private IGranjeroDao granjeroDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Granjero> findAll() {
		// TODO Auto-generated method stub
		return (List<Granjero>) granjeroDao.findAll();
	}

	@Override
	@Transactional
	public void save(Granjero granjero) {
		// TODO Auto-generated method stub
		granjeroDao.save(granjero);
	}

	@Override
	public Granjero findOne(Long id) {
		// TODO Auto-generated method stub
		return granjeroDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		granjeroDao.deleteById(id);
	}

}
