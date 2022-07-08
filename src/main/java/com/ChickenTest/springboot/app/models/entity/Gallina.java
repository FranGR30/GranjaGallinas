package com.ChickenTest.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gallinas")
public class Gallina implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int diasDeVida;

	@ManyToOne(fetch = FetchType.LAZY)
	private Granjero granjero;

	private int diaMuerte;
	
	private int huevosAPoner;

	public Granjero getGranjero() {
		return granjero;
	}

	public void setGranjero(Granjero granjero) {
		this.granjero = granjero;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDiasDeVida() {
		return diasDeVida;
	}

	public void setDiasDeVida(int diasDeVida) {
		this.diasDeVida = diasDeVida;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int definirDiaMuerte() {
		Random numAleatorio = new Random();
		int diaMuerte = numAleatorio.nextInt(5 - 3 + 1) + 3;
		return diaMuerte;
	}
	
	public int definirHuevosAPoner() {
		Random numAleatorio = new Random();
		int huevosAPoner = numAleatorio.nextInt(2 - 1 + 1) + 1;
		return huevosAPoner;
	}

	public Gallina(Granjero granjero) {
		this.diasDeVida = 0;
		this.diaMuerte = 10;//definirDiaMuerte();
		this.huevosAPoner = 2;//definirHuevosAPoner();
		this.granjero = granjero;
	}

	public Gallina() {
	}

	public void morir(Gallina gallina) {
		
	}

	public int getDiaMuerte() {
		return diaMuerte;
	}

	public void setDiaMuerte(int diaMuerte) {
		this.diaMuerte = diaMuerte;
	}

	public int getHuevosAPoner() {
		return huevosAPoner;
	}

	public void setHuevosAPoner(int huevosAPoner) {
		this.huevosAPoner = huevosAPoner;
	}

}
