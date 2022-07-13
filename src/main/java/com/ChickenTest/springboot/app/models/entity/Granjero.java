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

	private int precioGallinaCompra;

	private int precioHuevoCompra;

	private int precioGallinaVenta;

	private int precioHuevoVenta;

	private int cantGallinasMax;

	private int cantHuevosMax;

	public int getCantGallinasMax() {
		return cantGallinasMax;
	}

	public void setCantGallinasMax(int cantGallinasMax) {
		this.cantGallinasMax = cantGallinasMax;
	}

	public int getCantHuevosMax() {
		return cantHuevosMax;
	}

	public void setCantHuevosMax(int cantHuevosMax) {
		this.cantHuevosMax = cantHuevosMax;
	}

	public int getPrecioGallinaCompra() {
		return precioGallinaCompra;
	}

	public void setPrecioGallinaCompra(int precioGallinaCompra) {
		this.precioGallinaCompra = precioGallinaCompra;
	}

	public int getPrecioHuevoCompra() {
		return precioHuevoCompra;
	}

	public void setPrecioHuevoCompra(int precioHuevoCompra) {
		this.precioHuevoCompra = precioHuevoCompra;
	}

	public int getPrecioGallinaVenta() {
		return precioGallinaVenta;
	}

	public void setPrecioGallinaVenta(int precioGallinaVenta) {
		this.precioGallinaVenta = precioGallinaVenta;
	}

	public int getPrecioHuevoVenta() {
		return precioHuevoVenta;
	}

	public void setPrecioHuevoVenta(int precioHuevoVenta) {
		this.precioHuevoVenta = precioHuevoVenta;
	}

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

	public void removeGallina() {
		this.gallinas.remove(0);
	}

	public void removeGallina(Gallina gallina) {
		this.gallinas.remove(gallina);
	}

	public void removeHuevo() {
		this.huevos.remove(0);
	}

	public void removeHuevo(Huevo huevo) {
		this.huevos.remove(huevo);
	}

	public long getId() {
		return id;
	}

	public Granjero() {
		this.gallinas = new ArrayList<Gallina>();
		this.huevos = new ArrayList<Huevo>();
		this.dinero = 100;
		this.cantGallinasMax = 10;
		this.cantHuevosMax = 20;
		this.precioGallinaCompra = 50;
		this.precioGallinaVenta = 40;
		this.precioHuevoCompra = 20;
		this.precioHuevoVenta = 15;
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

}
