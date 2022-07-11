package com.ChickenTest.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ChickenTest.springboot.app.models.entity.Granjero;
import com.ChickenTest.springboot.app.models.entity.Huevo;
import com.ChickenTest.springboot.app.models.service.IGranjeroService;

@Controller
public class HuevoController {
	
	@Autowired
	private IGranjeroService granjeroService;
	
	@RequestMapping(value = "/listarHuevos/{id}")
	public String listar(@PathVariable(value = "id") Long idGranjero, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(idGranjero);
		if (granjero.getHuevos().size() == 0) {
			model.addAttribute("mensaje", "No hay huevos en la granja");
		}
		model.addAttribute("titulo", "Listado de huevos");
		model.addAttribute("granjero", granjero);
		model.addAttribute("listaHuevos", granjero.getHuevos());
		return "listarHuevos";
	}
	
	@RequestMapping(value = "/listarHuevos/venderHuevo/{granjeroId}/{huevoId}")
	public String venderGallina(@PathVariable(value = "granjeroId") Long granjeroId,@PathVariable(value = "huevoId") Long huevoId, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(granjeroId);
		for (int i = 0 ; i < granjero.getHuevos().size() ; i ++) {
			if (granjero.getHuevos().get(i).getId() == huevoId) {
				Huevo huevo = granjero.getHuevos().get(i);
				granjero.removeHuevo(huevo);
			}
		}
		granjero.setDinero(granjero.getDinero() + granjero.getPrecioHuevoCompra());
		granjeroService.save(granjero);
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Listado de huevos");
		model.addAttribute("listaHuevos", granjero.getHuevos());
		return "listarHuevos";
	}

}
