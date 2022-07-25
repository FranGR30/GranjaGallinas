package com.ChickenTest.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "granjeros")
public class Granjero implements Serializable {

	public final int cantGallinasMax = 10;

	public final int cantHuevosMax = 20;

	public final int precioGallinaCompra = 50;

	public final int precioGallinaVenta = 40;

	public final int precioHuevoCompra = 20;

	public final int precioHuevoVenta = 10;

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int dinero;

	private String nombre;

	@Autowired
	@OneToMany(mappedBy = "granjero", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Gallina> gallinas;

	@Autowired
	@OneToMany(mappedBy = "granjero", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Huevo> huevos;

	public int cantHuevos() {
		return this.huevos.size();
	}

	public int cantGallinas() {
		return this.gallinas.size();
	}

	public void addGallina(Gallina gallina) {
		this.gallinas.add(gallina);
	}

	public void addHuevo(Huevo huevo) {
		this.huevos.add(huevo);
	}

	// Funcion para eliminar el primer elemento de la lista de gallinas del granjero
	// Parametro:
	public void removeGallina() {
		this.gallinas.remove(0);
	}

	// Funcion para eliminar una gallina determinada dentro de la lista de gallinas
	// del granjero
	// Parametro: gallina
	// Por sobre carga de metodo determina que metodo utilizar
	public void removeGallina(Gallina gallina) {
		this.gallinas.remove(gallina);
	}

	// Funcion para eliminar el primer elemento de la lista de huevos del granjero
	// Parametro:
	public void removeHuevo() {
		this.huevos.remove(0);
	}

	// Funcion para eliminar un huevo determinado dentro de la lista de huevos del
	// granjero
	// Parametro: huevo
	// Por sobre carga de metodo determina que metodo utilizar
	public void removeHuevo(Huevo huevo) {
		this.huevos.remove(huevo);
	}

	public long getId() {
		return id;
	}

	// Contructor de la clase granjero
	public Granjero() {
		this.gallinas = new ArrayList<Gallina>();
		this.huevos = new ArrayList<Huevo>();
		this.dinero = 100;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDinero() {
		return dinero;
	}

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Gallina> getGallinas() {
		return gallinas;
	}

	public void setGallinas(List<Gallina> gallinas) {
		this.gallinas = gallinas;
	}

	public List<Huevo> getHuevos() {
		return huevos;
	}

	public void setHuevos(List<Huevo> huevos) {
		this.huevos = huevos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// Funcion para crear gallina
	// Parametro: granjero
	public void crearGallina(Granjero granjero) {
		Gallina gallina = new Gallina();
		gallina.setDiaMuerte(gallina.definirDiaMuerte());
		gallina.setHuevosAPoner(gallina.definirHuevosAPoner());
		gallina.setGranjero(granjero);
		granjero.addGallina(gallina);
	}

	// Funcion para crear huevo
	// Parametro: granjero
	public void crearHuevo(Granjero granjero) {
		Huevo huevo = new Huevo();
		huevo.setGranjero(granjero);
		huevo.setDiaNacimiento(huevo.diasParaNacer());
		granjero.addHuevo(huevo);
	}

}
