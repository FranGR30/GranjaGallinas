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

	@RequestMapping(value = {"/index","","/"})
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
	
	@RequestMapping(value = "/granja/comprarGallina/{id}")
	public String comprarGallina(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		Gallina gallina = new Gallina();
		gallina.setDiaMuerte(gallina.definirDiaMuerte());
		gallina.setHuevosAPoner(gallina.definirHuevosAPoner());
		if (granjero.getDinero() >= granjero.getPrecioGallinaCompra()) {
			gallina.setGranjero(granjero);
			granjero.addGallina(gallina);
			granjero.setDinero(granjero.getDinero() - granjero.getPrecioGallinaCompra());
			granjeroService.save(granjero);
		}else {
			model.addAttribute("mensaje", "Dinero insuficiente para comprar una gallina");
		}
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}
	
	@RequestMapping(value = "/granja/venderGallina/{id}")
	public String venderGallina(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		if (granjero.getGallinas().size() >= 1) {
			granjero.removeGallina();
			granjero.setDinero(granjero.getDinero() + granjero.getPrecioGallinaVenta());
			granjeroService.save(granjero);
		}else {
			model.addAttribute("mensaje", "No tienes gallinas para vender");
		}
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}
	
	@RequestMapping(value = "/granja/diaSiguiente/{id}")
	public String diaSiguiente(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		int contGalinaRemove = 0;
		int contHuevoNuevo = 0;
		int contGallinaNueva = 0;
		if (!granjero.getGallinas().isEmpty()) {
			for (int i = 0 ; i < granjero.getGallinas().size() ; i ++) {
				Gallina gallina = granjero.getGallinas().get(i);
				gallina.setDiasDeVida(gallina.getDiasDeVida() + 1);
				if (gallina.getDiasDeVida() >= gallina.getDiaMuerte()) {
					granjero.getGallinas().remove(i);
					contGalinaRemove ++;					
				}
				Random numAleatorio = new Random();
				int ponerHuevo = numAleatorio.nextInt(2 - 0 + 1) + 0;
				if (ponerHuevo == 1) {
					if (gallina.getHuevosAPoner() > 0) {
						gallina.setHuevosAPoner(gallina.getHuevosAPoner() - 1);
						Huevo huevo = new Huevo();
						huevo.setGranjero(granjero);
						huevo.setDiaNacimiento(huevo.diasParaNacer());
						granjero.addHuevo(huevo);
						contHuevoNuevo ++;
					}
				}
			}
			granjeroService.save(granjero);
		}
		System.out.println("test3");
		if (!granjero.getHuevos().isEmpty()) {
			for (int j = 0 ; j < granjero.getHuevos().size() ; j++) {
				Huevo huevo = granjero.getHuevos().get(j);
				huevo.setDiasDeVida(huevo.getDiasDeVida() + 1);
				if (huevo.getDiaNacimiento() <= huevo.getDiasDeVida()) {
					Gallina gallina = new Gallina();
					gallina.setGranjero(granjero);
					gallina.setDiaMuerte(gallina.definirDiaMuerte());
					gallina.setHuevosAPoner(gallina.definirHuevosAPoner());
					granjero.addGallina(gallina);
					contGallinaNueva ++;
					granjero.getHuevos().remove(j);
				}
			}
			granjeroService.save(granjero);
		}
		model.addAttribute("mensajeDiaSiguiente", "Durante el dia nacieron " + contGallinaNueva + " gallina/s, las gallinas pusieron " + contHuevoNuevo + " huevo/s y murieron " + contGalinaRemove + " gallina/s.");
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}
	
	@RequestMapping(value = "/tienda/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "tienda");
		return "tienda";
	}
	
}
