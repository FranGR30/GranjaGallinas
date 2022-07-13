package com.ChickenTest.springboot.app.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ChickenTest.springboot.app.models.entity.Gallina;
import com.ChickenTest.springboot.app.models.entity.Granjero;
import com.ChickenTest.springboot.app.models.entity.Huevo;
import com.ChickenTest.springboot.app.models.service.IGranjeroService;

@Controller
public class GranjeroController {

	@Autowired
	private IGranjeroService granjeroService;
	
	//Funcion para crear un granjero
	@RequestMapping(value = { "/index", "", "/" })
	public String crear(Map<String, Object> model) {
		Granjero granjero = new Granjero();
		model.put("granjero", granjero);
		model.put("titulo", "Crear Granjero");
		return "index";
	}
	
	//Funcion para guardar el granjero creado
	//Parametros: objeto granjero
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String guardar(Model model, Granjero granjero) {
		granjeroService.save(granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}

	//Funcion para mostrar la vista de la granja
	@RequestMapping(value = "/granja")
	public String ver(Model model, Granjero granjero) {
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}
	
	//Funcion para listar los granjeros guardados en la BD
	@RequestMapping(value = "/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de granjeros");
		model.addAttribute("granjeros", granjeroService.findAll());
		return "listar";
	}
	
	//Funcion para comprar huevos o gallinas
	//Parametros: id granjero, cantidad de gallinas en el contador de la tienda, cantidad de huevos en el contador de la tienda, el tipo de objeto a comprar (0=gallinna, 1=huevo)
	@RequestMapping(value = "/tienda/comprar/{id}/{cantGallinaTienda}/{cantHuevoTienda}/{tipo}")
	public String comprar(@PathVariable(value = "id") Long id,
			@PathVariable(value = "cantGallinaTienda") int cantGallinaTienda,
			@PathVariable(value = "cantHuevoTienda") int cantHuevoTienda, @PathVariable(value = "tipo") int tipo,
			Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);//Busca granjero por id
		if (tipo == 0) {//En caso de que el tipo sea = 0 procede a agregar gallinas
			if ((granjero.getGallinas().size() + cantGallinaTienda) > granjero.getCantGallinasMax()) {//En caso se superar la cantidad de gallinas maximas en inventario, arroja error
				model.addAttribute("mensajeGallina",
						"La cantidad de gallinas a comprar supera la cantidad maxima de gallinas posibles en granja ("
								+ granjero.getCantGallinasMax() + " gallinas)");
			} else if (cantGallinaTienda * granjero.getPrecioGallinaCompra() <= granjero.getDinero()) {
				crearObjetoIterable(cantGallinaTienda,granjero,tipo);//La funcion crea la cantidad de gallinas a comprar por el usuario y las asigna al granjero
				granjero.setDinero(granjero.getDinero() - (granjero.getPrecioGallinaCompra() * cantGallinaTienda));//Se descuenta el dinero del granjero
				granjeroService.save(granjero);
				cantGallinaTienda = 0;//Se resetea el contador de la tienda
			} else {//En caso de no tener dinero suficiente arroja error
				model.addAttribute("mensajeGallina",
						"Dinero insuficiente para comprar " + cantGallinaTienda + " gallina/s");
			}
		} else if (tipo == 1) {//en caso de que el tipo sea = 1 procede a agregar huevos
			if ((granjero.getHuevos().size() + cantHuevoTienda) > granjero.getCantHuevosMax()) {//En caso se superar la cantidad de huevos maximos en inventario, arroja error
				model.addAttribute("mensajeHuevo",
						"La cantidad de huevos a comprar supera la cantidad maxima de huevos posibles en granja ("
								+ granjero.getCantHuevosMax() + " huevos)");
			} else if (cantHuevoTienda * granjero.getPrecioHuevoCompra() <= granjero.getDinero()) {
				crearObjetoIterable(cantHuevoTienda,granjero,tipo);//La funcion crea la cantidad de huevos a comprar por el usuario y los asigna al granjero
				granjero.setDinero(granjero.getDinero() - (cantHuevoTienda * granjero.getPrecioHuevoCompra()));//Se descuenta el dinero del granjero
				granjeroService.save(granjero);
				cantHuevoTienda = 0;//Se resetea el contador de la tienda
			} else {//En caso de no tener dinero suficiente arroja error
				model.addAttribute("mensajeHuevo", "Dinero insuficiente para comprar " + cantHuevoTienda + " huevo/s");
			}
		}
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		model.addAttribute("cantGallinaTienda", cantGallinaTienda);
		model.addAttribute("cantHuevoTienda", cantHuevoTienda);
		return "tienda";
	}
	
	//Funcion para vender huevos o gallinas
	//Parametros: id granjero, cantidad de gallinas en el contador de la tienda, cantidad de huevos en el contador de la tienda, el tipo de objeto a comprar (0=gallinna, 1=huevo)
	@RequestMapping(value = "/tienda/vender/{id}/{cantGallinaTienda}/{cantHuevoTienda}/{tipo}")
	public String vender(@PathVariable(value = "id") Long id,
			@PathVariable(value = "cantGallinaTienda") int cantGallinaTienda,
			@PathVariable(value = "cantHuevoTienda") int cantHuevoTienda, @PathVariable(value = "tipo") int tipo,
			Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);//Busca granjero por id
		if (tipo == 0) {//Valida si es gallina
			if (granjero.getGallinas().size() >= cantGallinaTienda) {//Valida si tiene la cantidad de producto que se quiere vender
				eliminarObjetoIterable(cantGallinaTienda,granjero,tipo);//Elimina la cantidad a vender seleccionada por el usuario
				granjero.setDinero(granjero.getDinero() + (granjero.getPrecioGallinaVenta() * cantGallinaTienda));//Suma el dinero
				granjeroService.save(granjero);
				cantGallinaTienda = 0;//Resetea el contador de la tienda
			} else {//Mensaje de error en caso de no tener la cantidad del producto que se quiere vender
				model.addAttribute("mensajeGallina", "No tienes esa cantidad de gallinas");
			}
		} else if (tipo == 1) {//Valida si es huevo
			if (granjero.getHuevos().size() >= cantHuevoTienda) {
				eliminarObjetoIterable(cantHuevoTienda,granjero,tipo);//Elimina la cantidad a vender seleccionada por el usuario
				granjero.setDinero(granjero.getDinero() + (granjero.getPrecioHuevoVenta() * cantHuevoTienda));//Suma el dinero
				granjeroService.save(granjero);
				cantHuevoTienda = 0;//Resetea el contador de la tienda
			} else {//Mensaje de error en caso de no tener la cantidad del producto que se quiere vender
				model.addAttribute("mensajeHuevo", "No tienes esa cantidad de huevos");
			}
		}
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		model.addAttribute("cantGallinaTienda", cantGallinaTienda);
		model.addAttribute("cantHuevoTienda", cantHuevoTienda);
		return "tienda";
	}
	
	//Funcion para pasar al dia siguiente poniendo huevos, naciendo gallinas o muriendo gallinas
	//Parametros: id granjero
	@RequestMapping(value = "/granja/diaSiguiente/{id}")
	public String diaSiguiente(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);//Busca granjero por id pasado como parametro
		int contGalinaRemove = 0;
		int contHuevoNuevo = 0;
		int contGallinaNueva = 0;//Contadores que se muestran en el mensaje de lo sucedido durante el dia
		if (!granjero.getGallinas().isEmpty()) {
			for (int i = 0; i < granjero.getGallinas().size(); i++) {//Itera segun cuantas gallinas tenga el granjero
				Gallina gallina = granjero.getGallinas().get(i);//Identifica la gallina
				gallina.setDiasDeVida(gallina.getDiasDeVida() + 1);//suma un dia de vida a la gallina
				if (gallina.getDiasDeVida() >= gallina.getDiaMuerte()) {//Valida si los dias de vida de la gallina son iguales al dia de muerte de la gallina (generado de forma aleatoria)
					granjero.getGallinas().remove(i);
					contGalinaRemove++;
				}
				Random numAleatorio = new Random();//Genera un numero random entre 0 y 1. De esta forma, cada gallina tiene un 50% de chance de poner un huevo cada dia hasta que su contador de huevos a poner llegue a cero
				int ponerHuevo = numAleatorio.nextInt(1 - 0 + 1) + 0;
				if (ponerHuevo == 1) {//En caso de que el numero random es 1, pone un huevo
					if (gallina.getHuevosAPoner() > 0) {
						if ((granjero.getHuevos().size() + 1) <= granjero.getCantHuevosMax()) {//Valida que no se pase del maximo de huevos posibles para el granjero
							gallina.setHuevosAPoner(gallina.getHuevosAPoner() - 1);//Descuenta 1 de los huevos a poner
							crearHuevo(granjero);
							contHuevoNuevo++;
						}
					}
				}
			}
			granjeroService.save(granjero);
		}
		if (!granjero.getHuevos().isEmpty()) {
			for (int j = 0; j < granjero.getHuevos().size(); j++) {//Itera segun la cantidad de huevos tenga el granjero
				Huevo huevo = granjero.getHuevos().get(j);//Identifica al huevo
				huevo.setDiasDeVida(huevo.getDiasDeVida() + 1);//suma 1 a los dias de vida del huevo
				if (huevo.getDiaNacimiento() <= huevo.getDiasDeVida()) {//Verifica si el huevo tiene dias de vida suficientes para nacer
					if ((granjero.getGallinas().size() + 1) <= granjero.getCantGallinasMax()) {
						crearGallina(granjero);
						contGallinaNueva++;
						granjero.getHuevos().remove(j);
					}
				}
			}
			granjeroService.save(granjero);
		}
		model.addAttribute("mensajeDiaSiguiente",
				"Durante el dia nacieron " + contGallinaNueva + " gallina/s, las gallinas pusieron " + contHuevoNuevo
						+ " huevo/s y murieron " + contGalinaRemove + " gallina/s.");
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}
	
	//Funcion para ver la tienda
	//Parametros: id granjero
	@RequestMapping(value = "/tienda/{id}")
	public String verTienda(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		int cantGallinaTienda = 0; //Setea contadores en 0
		int cantHuevoTienda = 0;
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "tienda");
		model.addAttribute("cantGallinaTienda", cantGallinaTienda);
		model.addAttribute("cantHuevoTienda", cantHuevoTienda);
		return "tienda";
	}
	
	//Funcion para sumar contadores de la tienda
	//Parametros: id granjero, contador de gallina, contador de huevo, el tipo de producto que se quiera sumar(gallina o huevo)
	@RequestMapping(value = "/tienda/sumar/{id}/{cantGallinaTienda}/{cantHuevoTienda}/{tipo}")
	public String sumar(@PathVariable(value = "id") Long id,
			@PathVariable(value = "cantGallinaTienda") int cantGallinaTienda,
			@PathVariable(value = "cantHuevoTienda") int cantHuevoTienda, @PathVariable(value = "tipo") int tipo,
			Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		if (tipo == 0) {
			cantGallinaTienda++;
		} else if (tipo == 1) {
			cantHuevoTienda++;
		}
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "tienda");
		model.addAttribute("cantGallinaTienda", cantGallinaTienda);
		model.addAttribute("cantHuevoTienda", cantHuevoTienda);
		return "tienda";
	}
	
	//Funcion para restar contadores de la tienda
	//Parametros: id granjero, contador de gallina, contador de huevo, el tipo de producto que se quiera restar(gallina o huevo)
	@RequestMapping(value = "/tienda/restar/{id}/{cantGallinaTienda}/{cantHuevoTienda}/{tipo}")
	public String restar(@PathVariable(value = "id") Long id,
			@PathVariable(value = "cantGallinaTienda") int cantGallinaTienda,
			@PathVariable(value = "cantHuevoTienda") int cantHuevoTienda, @PathVariable(value = "tipo") int tipo,
			Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		if (tipo == 0) {
			if (cantGallinaTienda > 0) {
				cantGallinaTienda--;
			}
		} else if (tipo == 1) {
			if (cantHuevoTienda > 0) {
				cantHuevoTienda--;
			}
		}
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "tienda");
		model.addAttribute("cantGallinaTienda", cantGallinaTienda);
		model.addAttribute("cantHuevoTienda", cantHuevoTienda);
		return "tienda";
	}
	
	//Funcion para crear una gallina para un granjero determinado
	//Parametros: granjero
	public void crearGallina(Granjero granjero) {
		Gallina gallina = new Gallina();
		gallina.setDiaMuerte(gallina.definirDiaMuerte());
		gallina.setHuevosAPoner(gallina.definirHuevosAPoner());
		gallina.setGranjero(granjero);
		granjero.addGallina(gallina);
	}
	
	//Funcion para crear un huevo para un granjero determinado
	//Parametros: granjero
	public void crearHuevo(Granjero granjero) {
		Huevo huevo = new Huevo();
		huevo.setGranjero(granjero);
		huevo.setDiaNacimiento(huevo.diasParaNacer());
		granjero.addHuevo(huevo);
	}
	
	//Funcion para crear una determinada cantidad de objetos para un granjero
	//Parametros: granjero, cantidad de objetos a crear, el tipo de objero a crear
	public void crearObjetoIterable(int cantACrear, Granjero granjero, int tipo) {
		if (tipo == 0) {
			for (int i = 1; i <= cantACrear; i++) {
				crearGallina(granjero);
				granjeroService.save(granjero);
			}
		}else if (tipo == 1) {
			for (int i = 1; i <= cantACrear; i++) {
				crearHuevo(granjero);
				granjeroService.save(granjero);
			}
		}
	}
	
	//Funcion para eliminar una determinada cantidad de objetos para un granjero
	//Parametros: granjero, cantidad de objetos a crear, el tipo de objero a crear
	public void eliminarObjetoIterable(int cantAEliminar, Granjero granjero, int tipo) {
		if (tipo == 0) {
			for (int i = 1; i <= cantAEliminar; i++) {
				granjero.removeGallina();
				granjeroService.save(granjero);
			}
		}else if (tipo == 1) {
			for (int i = 1; i <= cantAEliminar; i++) {
				granjero.removeHuevo();
				granjeroService.save(granjero);
			}
		}
	}

}
