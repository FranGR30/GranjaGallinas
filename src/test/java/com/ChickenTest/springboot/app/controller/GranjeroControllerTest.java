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
class GranjeroControllerTest {

	@Mock
	private IGranjeroService granjeroService;

	@InjectMocks
	private GranjeroController granjeroController;

	@Mock
	private Model model;

	@Mock
	private Huevo huevo;

	@Mock
	private Gallina gallina;

	@Test
	void comprarTest_dineroInsuficienteParaComprarGallina() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjero.setDinero(10);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para probar dinero insuficiente para comprar gallina:");
		granjeroController.comprar(1L, 1, 0, 0, model);
		assertThat(granjero.getGallinas().size(), is(0));
		assertThat(granjero.getDinero(), is(10));
	}

	@Test
	void comprarTest_dineroInsuficienteParaComprarHuevo() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjero.setDinero(10);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para probar dinero insuficiente para comprar huevo:");
		granjeroController.comprar(1L, 0, 1, 1, model);
		assertThat(granjero.getHuevos().size(), is(0));
		assertThat(granjero.getDinero(), is(10));
	}

	@Test
	void comprarTest_Comprar1Gallina() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para comprar una gallina:");
		granjero.setDinero(100);
		granjeroController.comprar(1L, 1, 0, 0, model);
		assertThat(granjero.getGallinas().size(), is(1));
		assertThat(granjero.getDinero(), is(100 - 50));
	}

	@Test
	void comprarTest_Comprar1Huevo() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para comprar un huevo:");
		granjero.setDinero(100);
		granjeroController.comprar(1L, 0, 1, 1, model);
		assertThat(granjero.getHuevos().size(), is(1));
		assertThat(granjero.getDinero(), is(100 - 20));
	}

	@Test
	void comprarTest_Comprar1VariasGallinas() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para comprar varias gallinas:");
		granjero.setDinero(1000);
		granjeroController.comprar(1L, 5, 0, 0, model);
		assertThat(granjero.getGallinas().size(), is(5));
		assertThat(granjero.getDinero(), is(1000 - (50 * 5)));
	}

	@Test
	void comprarTest_ComprarVariosHuevos() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para comprar varios huevos:");
		granjero.setDinero(1000);
		granjeroController.comprar(1L, 0, 5, 1, model);
		assertThat(granjero.getHuevos().size(), is(5));
		assertThat(granjero.getDinero(), is(1000 - (20 * 5)));
	}

	@Test
	void venderTest_VenderVariosHuevos() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjero.crearHuevo(granjero);
		granjero.crearHuevo(granjero);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para vender varios huevos:");
		granjero.setDinero(0);
		granjeroController.vender(1L, 0, 2, 1, model);
		assertThat(granjero.getHuevos().size(), is(0));
		assertThat(granjero.getDinero(), is(20));
	}

	@Test
	void venderTest_VenderVariasGallinas() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjero.crearGallina(granjero);
		granjero.crearGallina(granjero);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para vender varias gallinas:");
		granjero.setDinero(0);
		granjeroController.vender(1L, 2, 0, 0, model);
		assertThat(granjero.getHuevos().size(), is(0));
		assertThat(granjero.getDinero(), is(80));
	}

	@Test
	void venderTest_gallinasInsuficientesEnStock() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjero.crearGallina(granjero);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para gallinas insuficientes para vender:");
		granjero.setDinero(0);
		granjeroController.vender(1L, 3, 0, 0, model);
		assertThat(granjero.getGallinas().size(), is(1));
		assertThat(granjero.getDinero(), is(0));
	}

	@Test
	void venderTest_huevosInsuficientesEnStock() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjero.crearHuevo(granjero);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		System.out.println("Prueba para gallinas insuficientes para vender:");
		granjero.setDinero(0);
		granjeroController.vender(1L, 0, 3, 1, model);
		assertThat(granjero.getHuevos().size(), is(1));
		assertThat(granjero.getDinero(), is(0));
	}
	
	@Test
	void diaSiguienteTest() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjero.crearHuevo(granjero);
		granjero.crearGallina(granjero);
		granjero.setDinero(0);
		when(granjeroService.findOne(1L)).thenReturn(granjero);
		granjeroController.diaSiguiente(1L, model);
		assertThat(granjero.getDinero(), is(0));
		assertThat(granjero.getGallinas().get(0).getDiasDeVida(), is(1));
		assertThat(granjero.getHuevos().get(0).getDiasDeVida(), is(1));
	}
	
	@Test
	void crearObjetoIterableTest_crearGallinas() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjeroController.crearObjetoIterable(5, granjero, 0);
		assertThat(granjero.cantGallinas(),is(5));
	}
	
	@Test
	void eliminarObjetoIterableTest_eliminarGallinas() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjeroController.crearObjetoIterable(5, granjero, 0);
		granjeroController.eliminarObjetoIterable(5, granjero, 0);
		assertThat(granjero.cantGallinas(),is(0));
	}
	
	@Test
	void crearObjetoIterableTest_crearHuevos() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjeroController.crearObjetoIterable(5, granjero, 1);
		assertThat(granjero.cantHuevos(),is(5));
	}
	
	@Test
	void eliminarObjetoIterableTest_eliminarHuevos() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjeroController.crearObjetoIterable(5, granjero, 1);
		granjeroController.eliminarObjetoIterable(5, granjero, 1);
		assertThat(granjero.cantHuevos(),is(0));
	}

}