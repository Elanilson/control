package com.elanilsondejesus.com.controledopeso.model;

import android.bluetooth.le.ScanSettings;
import android.widget.Switch;

public class Progress {
    private Long id;
    private int dia;
    private int mes;
    private int ano;
    private  float peso;
    private int valor;
    private int anoSelecionado;

    public Progress() {
    }


    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getAnoSelecionado() {
        return anoSelecionado;
    }

    public void setAnoSelecionado(int anoSelecionado) {
        this.anoSelecionado = anoSelecionado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}
