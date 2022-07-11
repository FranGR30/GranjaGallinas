package com.ChickenTest.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ChickenTest.springboot.app.models.entity.Gallina;
import com.ChickenTest.springboot.app.models.entity.Granjero;
import com.ChickenTest.springboot.app.models.service.IGranjeroService;

@Controller
public class GallinaController {
	@Autowired
	private IGranjeroService granjeroService;

	
	@RequestMapping(value = "/listarGallinas/{id}")
	public String listar(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		if (granjero.getGallinas().size() == 0) {
			model.addAttribute("mensaje", "No hay gallinas en la granja");
		}
		model.addAttribute("titulo", "Listado de gallinas");
		model.addAttribute("granjero", granjero);
		model.addAttribute("listaGallinas", granjero.getGallinas());
		return "listarGallinas";
	}
	
	@RequestMapping(value = "/granja/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}
	
	@RequestMapping(value = "/listarGallinas/venderGallina/{granjeroId}/{gallinaId}")
	public String venderGallina(@PathVariable(value = "granjeroId") Long granjeroId,@PathVariable(value = "gallinaId") Long gallinaId, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(granjeroId);
		for (int i = 0 ; i < granjero.getGallinas().size() ; i ++) {
			if (granjero.getGallinas().get(i).getId() == gallinaId) {
				Gallina gallina = granjero.getGallinas().get(i);
				granjero.removeGallina(gallina);
			}
		}
		granjero.setDinero(granjero.getDinero() + granjero.getPrecioGallinaCompra());
		granjeroService.save(granjero);
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Listado de gallinas");
		model.addAttribute("listaGallinas", granjero.getGallinas());
		return "listarGallinas";
	}
	
}
