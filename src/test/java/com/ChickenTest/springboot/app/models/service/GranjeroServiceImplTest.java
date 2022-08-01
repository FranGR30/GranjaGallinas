package com.ChickenTest.springboot.app.models.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ChickenTest.springboot.app.models.entity.Granjero;

@ExtendWith(MockitoExtension.class)
class GranjeroServiceImplTest {
	
	@Mock
	private IGranjeroService IGranjeroService;
	
	@InjectMocks
	private GranjeroServiceImpl granjeroServiceImpl;
	
	private Granjero granjero;

	@Test
	void findAllTest() {
		List<Granjero> granjeros = new ArrayList<Granjero>();
		granjero = new Granjero();
		granjero.setNombre("Test");
		granjero.setId(1);
		granjero.setDinero(100);
		granjeros.add(granjero);
		when(IGranjeroService.findAll()).thenReturn(granjeros);
		assertEquals(granjeros,IGranjeroService.findAll());
	}
	
	@Test
	void findOneTest() {
		granjero = new Granjero();
		granjero.setNombre("Test");
		granjero.setId(1);
		when(IGranjeroService.findOne(granjero.getId())).thenReturn(granjero);
		assertEquals(granjero,IGranjeroService.findOne(granjero.getId()));
	}
	
	void deleteTest() {
		granjero = new Granjero();
		granjero.setNombre("Test");
		granjero.setId(1);
		verify(IGranjeroService,times(1)).delete(granjero.getId());
	}

}
