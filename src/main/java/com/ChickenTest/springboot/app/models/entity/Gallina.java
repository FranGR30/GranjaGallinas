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
	
	public final int maxHuevosAPoner = 2;
	
	public final int minHuevosAPoner = 1;
	
	public final int maxDiaDeMuerte = 5;
	
	public final int minDiaDeMuerte = 5;

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
	
	//Funcion para determinar el dia de muerte de la gallina (numero random entre maxDiaDeMuerte y minDiaDeMuerte)
	public int definirDiaMuerte() {
		Random numAleatorio = new Random();
		int diaMuerte = numAleatorio.nextInt(maxDiaDeMuerte - minDiaDeMuerte + 1) + minDiaDeMuerte;
		return diaMuerte;
	}
	
	//Funcion para determinar la cantidad de huevos a poner por la gallina (numero random entre maxHuevosAPoner y minHuevosAPoner)
	public int definirHuevosAPoner() {
		Random numAleatorio = new Random();
		int huevosAPoner = numAleatorio.nextInt(maxHuevosAPoner - minHuevosAPoner + 1) + minHuevosAPoner;
		return huevosAPoner;
	}

	public Gallina() {
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
