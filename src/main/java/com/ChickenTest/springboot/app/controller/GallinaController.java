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

	//Funcion para listar gallinas pertenecientes a un granjero
	//Parametros: id granjero
	@RequestMapping(value = "/listarGallinas/{id}")
	public String listar(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id); //Busca granjero por id
		if (granjero.getGallinas().size() == 0) {//En caso de no haber gallinas muestra un mennsaje de error
			model.addAttribute("mensaje", "No hay gallinas en la granja");
		}
		model.addAttribute("titulo", "Listado de gallinas");
		model.addAttribute("granjero", granjero);
		model.addAttribute("listaGallinas", granjero.getGallinas());//Se pasa la lista de gallinas
		return "listarGallinas";
	}
	
	//Funcion para volver a la granja desde el listar gallinas
	//Parametros: id granjero
	@RequestMapping(value = "/granja/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(id);//Busca granjero por id
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Granja");
		return "granja";
	}
	
	//Funcion para vender una determinada gallina perteneciente a un granjero
	//Parametros: id granjero, id gallina
	@RequestMapping(value = "/listarGallinas/venderGallina/{granjeroId}/{gallinaId}")
	public String venderGallina(@PathVariable(value = "granjeroId") Long granjeroId,@PathVariable(value = "gallinaId") Long gallinaId, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(granjeroId);//Busca granjero por id
		for (int i = 0 ; i < granjero.getGallinas().size() ; i ++) {//Busca el id de la gallina recibida como parametro en las gallinas del granjero
			if (granjero.getGallinas().get(i).getId() == gallinaId) {
				Gallina gallina = granjero.getGallinas().get(i);
				granjero.removeGallina(gallina);//remueve la gallina pasada como parametro
			}
		}
		granjero.setDinero(granjero.getDinero() + granjero.getPrecioGallinaCompra());//suma el dinero de la venta de la gallina
		granjeroService.save(granjero);
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Listado de gallinas");
		model.addAttribute("listaGallinas", granjero.getGallinas());
		return "listarGallinas";
	}
	
}
