package com.elanilsondejesus.com.controledopeso.model;

public class HistoricoItem {

    private Long id;
    private String data;
    private float peso;
    private String mudanca;

    public HistoricoItem() {
    }

    public HistoricoItem(String data, float peso, String mudanca) {
        this.data = data;
        this.peso = peso;
        this.mudanca = mudanca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getMudanca() {
        return mudanca;
    }

    public void setMudanca(String mudanca) {
        this.mudanca = mudanca;
    }
}
