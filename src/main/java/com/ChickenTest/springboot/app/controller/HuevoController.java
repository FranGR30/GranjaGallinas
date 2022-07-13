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
	
	//Funcion para listar huevos pertenecientes a un granjero
	//Parametros: id granjero
	@RequestMapping(value = "/listarHuevos/{id}")
	public String listar(@PathVariable(value = "id") Long idGranjero, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(idGranjero);//Busca granjero por id
		if (granjero.getHuevos().size() == 0) {//En caso de no haber gallinas muestra un mennsaje de error
			model.addAttribute("mensaje", "No hay huevos en la granja");
		}
		model.addAttribute("titulo", "Listado de huevos");
		model.addAttribute("granjero", granjero);
		model.addAttribute("listaHuevos", granjero.getHuevos());
		return "listarHuevos";
	}
	
	//Funcion para vender un determinado huevo perteneciente a un granjero
	//Parametros: id granjero, id huevo
	@RequestMapping(value = "/listarHuevos/venderHuevo/{granjeroId}/{huevoId}")
	public String venderGallina(@PathVariable(value = "granjeroId") Long granjeroId,@PathVariable(value = "huevoId") Long huevoId, Model model) {
		Granjero granjero = null;
		granjero = granjeroService.findOne(granjeroId);//Busca granjero por id
		for (int i = 0 ; i < granjero.getHuevos().size() ; i ++) {//Busca el id del huevo recibido como parametro en las gallinas del granjero
			if (granjero.getHuevos().get(i).getId() == huevoId) {
				Huevo huevo = granjero.getHuevos().get(i);
				granjero.removeHuevo(huevo);//remueve el huevo pasada como parametro
			}
		}
		granjero.setDinero(granjero.getDinero() + granjero.getPrecioHuevoCompra());//suma el dinero de la venta del huevo
		granjeroService.save(granjero);
		model.addAttribute("granjero", granjero);
		model.addAttribute("titulo", "Listado de huevos");
		model.addAttribute("listaHuevos", granjero.getHuevos());
		return "listarHuevos";
	}

}
