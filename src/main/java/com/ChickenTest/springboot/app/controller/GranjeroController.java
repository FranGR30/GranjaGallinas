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

	@RequestMapping(value = { "/index", "", "/" })
	public String crear(Map<String, Object> model) {
		Granjero granjero = new Granjero();
		model.put("granjero", granjero);
		model.put("titulo", "Crear Granjero");
		return "index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String guardar(Model model, Granjero granjero) {
		granjero.setPrecioGallinaCompra(50);
		granjero.setPrecioGallinaVenta(30);
		granjero.setPrecioHuevoCompra(20);
		granjero.setPrecioHuevoVenta(10);
		granjeroService.save(granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}

	@RequestMapping(value = "/granja")
	public String ver(Model model, Granjero granjero) {
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}

	@RequestMapping(value = "/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de granjeros");
		model.addAttribute("granjeros", granjeroService.findAll());
		return "listar";
	}

	@RequestMapping(value = "/tienda/comprar/{id}/{cantGallinaTienda}/{cantHuevoTienda}/{tipo}")
	public String comprar(@PathVariable(value = "id") Long id,
			@PathVariable(value = "cantGallinaTienda") int cantGallinaTienda,
			@PathVariable(value = "cantHuevoTienda") int cantHuevoTienda, @PathVariable(value = "tipo") int tipo,
			Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		if (tipo == 0) {
			if (cantGallinaTienda * granjero.getPrecioGallinaCompra() <= granjero.getDinero()) {
				for (int i = 1; i <= cantGallinaTienda; i++) {
					Gallina gallina = new Gallina();
					gallina.setDiaMuerte(gallina.definirDiaMuerte());
					gallina.setHuevosAPoner(gallina.definirHuevosAPoner());
					gallina.setGranjero(granjero);
					granjero.addGallina(gallina);
					granjeroService.save(granjero);
				}
				granjero.setDinero(granjero.getDinero() - (granjero.getPrecioGallinaCompra() * cantGallinaTienda));
				granjeroService.save(granjero);
				cantGallinaTienda = 0;
			} else {
				model.addAttribute("mensajeGallina",
						"Dinero insuficiente para comprar " + cantGallinaTienda + " gallina/s");
			}
		} else if (tipo == 1) {
			if (cantHuevoTienda * granjero.getPrecioHuevoCompra() <= granjero.getDinero()) {
				for (int i = 1; i <= cantHuevoTienda; i++) {
					Huevo huevo = new Huevo();
					huevo.setDiaNacimiento(huevo.diasParaNacer());
					huevo.setGranjero(granjero);
					granjero.addHuevo(huevo);
					granjeroService.save(granjero);
				}
				granjero.setDinero(granjero.getDinero() - (cantHuevoTienda * granjero.getPrecioHuevoCompra()));
				granjeroService.save(granjero);
				cantHuevoTienda = 0;
			} else {
				model.addAttribute("mensajeHuevo", "Dinero insuficiente para comprar " + cantHuevoTienda + " huevo/s");
			}
		}
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		model.addAttribute("cantGallinaTienda", cantGallinaTienda);
		model.addAttribute("cantHuevoTienda", cantHuevoTienda);
		return "tienda";
	}

	@RequestMapping(value = "/tienda/vender/{id}/{cantGallinaTienda}/{cantHuevoTienda}/{tipo}")
	public String vender(@PathVariable(value = "id") Long id,
			@PathVariable(value = "cantGallinaTienda") int cantGallinaTienda,
			@PathVariable(value = "cantHuevoTienda") int cantHuevoTienda, 
			@PathVariable(value = "tipo") int tipo,
			Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		if (tipo == 0) {
			if (granjero.getGallinas().size() >= cantGallinaTienda) {
				for (int i = 1; i <= cantGallinaTienda; i++) {
					granjero.removeGallina();
					granjeroService.save(granjero);
				}
				granjero.setDinero(granjero.getDinero() + (granjero.getPrecioGallinaVenta() * cantGallinaTienda));
				granjeroService.save(granjero);
				cantGallinaTienda = 0;
			} else {
				model.addAttribute("mensajeGallina", "No tienes esa cantidad de gallinas");
			}
		}else if (tipo == 1) {
			if (granjero.getHuevos().size() >= cantHuevoTienda) {
				for (int i = 1; i <= cantHuevoTienda; i++) {
					granjero.removeHuevo();
					granjeroService.save(granjero);
				}
				granjero.setDinero(granjero.getDinero() + (granjero.getPrecioHuevoVenta() * cantHuevoTienda));
				granjeroService.save(granjero);
				cantHuevoTienda = 0;
			}else {
				model.addAttribute("mensajeHuevo", "No tienes esa cantidad de huevos");
			}
		}
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		model.addAttribute("cantGallinaTienda", cantGallinaTienda);
		model.addAttribute("cantHuevoTienda", cantHuevoTienda);
		return "tienda";
	}

	@RequestMapping(value = "/granja/diaSiguiente/{id}")
	public String diaSiguiente(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		int contGalinaRemove = 0;
		int contHuevoNuevo = 0;
		int contGallinaNueva = 0;
		if (!granjero.getGallinas().isEmpty()) {
			for (int i = 0; i < granjero.getGallinas().size(); i++) {
				Gallina gallina = granjero.getGallinas().get(i);
				gallina.setDiasDeVida(gallina.getDiasDeVida() + 1);
				if (gallina.getDiasDeVida() >= gallina.getDiaMuerte()) {
					granjero.getGallinas().remove(i);
					contGalinaRemove++;
				}
				Random numAleatorio = new Random();
				int ponerHuevo = numAleatorio.nextInt(1 - 0 + 1) + 0;
				if (ponerHuevo == 1) {
					if (gallina.getHuevosAPoner() > 0) {
						gallina.setHuevosAPoner(gallina.getHuevosAPoner() - 1);
						Huevo huevo = new Huevo();
						huevo.setGranjero(granjero);
						huevo.setDiaNacimiento(huevo.diasParaNacer());
						granjero.addHuevo(huevo);
						contHuevoNuevo++;
					}
				}
			}
			granjeroService.save(granjero);
		}
		if (!granjero.getHuevos().isEmpty()) {
			for (int j = 0; j < granjero.getHuevos().size(); j++) {
				Huevo huevo = granjero.getHuevos().get(j);
				huevo.setDiasDeVida(huevo.getDiasDeVida() + 1);
				if (huevo.getDiaNacimiento() <= huevo.getDiasDeVida()) {
					Gallina gallina = new Gallina();
					gallina.setGranjero(granjero);
					gallina.setDiaMuerte(gallina.definirDiaMuerte());
					gallina.setHuevosAPoner(gallina.definirHuevosAPoner());
					granjero.addGallina(gallina);
					contGallinaNueva++;
					granjero.getHuevos().remove(j);
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

	@RequestMapping(value = "/tienda/{id}")
	public String verTienda(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		int cantGallinaTienda = 0;
		int cantHuevoTienda = 0;
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "tienda");
		model.addAttribute("cantGallinaTienda", cantGallinaTienda);
		model.addAttribute("cantHuevoTienda", cantHuevoTienda);
		return "tienda";
	}

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

}
