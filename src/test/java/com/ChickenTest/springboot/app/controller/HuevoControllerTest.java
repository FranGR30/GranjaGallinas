package com.ChickenTest.springboot.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.ChickenTest.springboot.app.models.entity.Gallina;
import com.ChickenTest.springboot.app.models.entity.Granjero;
import com.ChickenTest.springboot.app.models.entity.Huevo;
import com.ChickenTest.springboot.app.models.service.IGranjeroService;

@ExtendWith(MockitoExtension.class)
class HuevoControllerTest {
	
	@Mock
	private IGranjeroService granjeroService;
	
	@InjectMocks
	private GranjeroController granjeroController;
	
	@InjectMocks
	private HuevoController huevoController;
	
	@Mock
	private Model model;

	@Mock
	private Huevo huevo;

	@Mock
	private Gallina gallina;

	@Test
	//Test para evaluar vender un huevo por su ID
	//Evalue que se reste el huevo del stock de huevos totales
	//Se evalua que se sume la cantidad de dinero equivalente a la venta
	void venderHuevoTest() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjero.crearHuevo(granjero);
		granjero.setDinero(0);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		huevoController.venderHuevo(1L, granjero.getHuevos().get(0).getId(), model);
		assertThat(granjero.cantHuevos(),is(0));
		assertThat(granjero.getDinero(),is(10));
	}

}
