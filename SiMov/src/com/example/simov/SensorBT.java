package com.example.simov;

import java.util.ArrayList;

public class SensorBT {

	private String name;
	private double distAtivacao;
	private String tipo;
	private String alertType;
	private double alertMax;
	private double alertMin;
	private boolean alert;
	private int id;
	private int ligado;
	
	public int getLigado() {
		return ligado;
	}

	public void setLigado(int ligado) {
		this.ligado = ligado;
	}

	private ArrayList<String> valores = new ArrayList<String>();

	public ArrayList<String> getValores() {
		return valores;
	}

	public void setValores(ArrayList<String> valores) {
		this.valores = valores;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SensorBT(String n, double dt, String t, String at, double amax,
			double amin, boolean a, int ligado, int id) {
		setName(n);
		setDistAtivacao(dt);
		setTipo(t);
		setAlertType(at);
		setAlertMax(amax);
		setAlertMin(amin);
		setAlert(a);
		setLigado(ligado);
		setId(id);
	}

	public SensorBT() {
	}

	public double getDistAtivacao() {
		return distAtivacao;
	}

	public void setDistAtivacao(double distAtivacao) {
		this.distAtivacao = distAtivacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public double getAlertMax() {
		return alertMax;
	}

	public void setAlertMax(double alertMax) {
		this.alertMax = alertMax;
	}

	public double getAlertMin() {
		return alertMin;
	}

	public void setAlertMin(double alertMin) {
		this.alertMin = alertMin;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
