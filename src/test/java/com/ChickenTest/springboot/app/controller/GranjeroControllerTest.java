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
	//Test para evaluar el caso en el que no hay dinero suficiente para comprar 1 gallina
	//Se evalua que el dinero no se modifique
	//Se evalua que la cantidad de gallinas no se modifique
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
	//Test para evaluar el caso en el que no hay dinero suficiente para comprar 1 huevo
	//Se evalua que el dinero no se modifique
	//Se evalua que la cantidad de huevos no se modifique
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
	//Test para comprar 1 gallina
	//Se evalue que se reste el dinero correspondiente
	//Se evalua que se sume 1 gallina al stock de gallinas
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
	//Test para comprar 1 huevo
	//Se evalue que se reste el dinero correspondiente
	//Se evalua que se sume 1 huevo al stock de huevos
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
	//Test para comprar varias gallinas
	//Se evalue que se reste el dinero correspondiente
	//Se evalua que el stock de gallinas sea el que se compro
	void comprarTest_ComprarVariasGallinas() {
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
	//Test para comprar varios huevos
	//Se evalue que se reste el dinero correspondiente
	//Se evalua que el stock de huevos sea el que se compro
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
	//Test para vender varios huevos
	//Se evalua que se reste la cantidad de huevos del stock
	//Se evalua que se sume la cantidad de dinero equivalente a la venta
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
	//Test para vender varias gallinas
	//Se evalua que se reste la cantidad de gallinas del stock
	//Se evalua que se sume la cantidad de dinero equivalente a la venta
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
	//Test para evaluar cuando no hay suficiente stock de gallinas para vender
	//Se evalua que la cantidad de gallinas no disminulla cuando se quiere vender mas del stock disponible
	//Se evalua que la cantidad de dinero no se modifique
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
	//Test para evaluar cuando no hay suficiente stock de huevos para vender
	//Se evalua que la cantidad de huevos no disminulla cuando se quiere vender mas del stock disponible
	//Se evalua que la cantidad de dinero no se modifique
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
	//Test para evaluar el metodo dia siguiente
	//Se evalue que sume un dia de vida a cada gallina y huevo
	//Se evalua que el dinero no se modifique
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
	//Test para evaluar metodo para crear varias gallinas
	void crearObjetoIterableTest_crearGallinas() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjeroController.crearObjetoIterable(5, granjero, 0);
		assertThat(granjero.cantGallinas(),is(5));
	}
	
	@Test
	//Test para evaluar metodo para elminar varias gallinas
	void eliminarObjetoIterableTest_eliminarGallinas() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjeroController.crearObjetoIterable(5, granjero, 0);
		granjeroController.eliminarObjetoIterable(5, granjero, 0);
		assertThat(granjero.cantGallinas(),is(0));
	}
	
	@Test
	//Test para evaluar metodo para crear varios huevos
	void crearObjetoIterableTest_crearHuevos() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjeroController.crearObjetoIterable(5, granjero, 1);
		assertThat(granjero.cantHuevos(),is(5));
	}
	
	@Test
	//Test para evaluar metodo para eliminar varios huevos
	void eliminarObjetoIterableTest_eliminarHuevos() {
		Granjero granjero = new Granjero();
		granjero.setId(1);
		granjeroController.crearObjetoIterable(5, granjero, 1);
		granjeroController.eliminarObjetoIterable(5, granjero, 1);
		assertThat(granjero.cantHuevos(),is(0));
	}

}