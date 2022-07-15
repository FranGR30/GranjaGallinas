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
@Table(name = "huevos")
public class Huevo implements Serializable {

	public final int maxDiaDeNacimiento = 4;
	
	public final int minDiaDeNacimiento = 3;
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int diasDeVida;
	
	private int diaNacimiento;

	public int getDiaNacimiento() {
		return diaNacimiento;
	}

	public void setDiaNacimiento(int diaNacimiento) {
		this.diaNacimiento = diaNacimiento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	private Granjero granjero;

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
	
	//Funcion para determinar el dia a nacer del huevo (numero random entre maxDiaDeNacimiento y minDiaDeNacimiento)
	public int diasParaNacer() {
		Random numAleatorio = new Random();
		int diaNacimiento = numAleatorio.nextInt(maxDiaDeNacimiento - minDiaDeNacimiento + 1) + minDiaDeNacimiento;
		return diaNacimiento;
	}

	public Huevo() {
	}
	
}
