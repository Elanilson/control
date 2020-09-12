package com.elanilsondejesus.com.controledopeso.model;

import java.util.Date;

public class Usuario {
    private Long id;

    private float  altura;
    private float pesoMaior;
    private float pesoAtual;
    private float  pesoInicial;
    private float imc;
    private float imcMaior;
    private float  meta;
    private double  gorduraKG;
    private double  gorduraPorcentage;
    private double  mudanca;
    private String datamudanca;
    private String sexo;
    private String nome;
    private  int nascimento;
    private String data;


    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatamudanca() {
        return datamudanca;
    }

    public void setDatamudanca(String datamudanca) {
        this.datamudanca = datamudanca;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPesoAtual() {
        return pesoAtual;
    }

    public void setPesoAtual(float pesoAtual) {
        this.pesoAtual = pesoAtual;
    }

    public float getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(float pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    public float getPesoMaior() {
        return pesoMaior;
    }

    public void setPesoMaior(float pesoMaior) {
        this.pesoMaior = pesoMaior;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public float getImcMaior() {
        return imcMaior;
    }

    public void setImcMaior(float imcMaior) {
        this.imcMaior = imcMaior;
    }

    public float getMeta() {
        return meta;
    }

    public void setMeta(float meta) {
        this.meta = meta;
    }

    public double getGorduraKG() {
        return gorduraKG;
    }

    public void setGorduraKG(double gorduraKG) {
        this.gorduraKG = gorduraKG;
    }

    public double getGorduraPorcentage() {
        return gorduraPorcentage;
    }

    public void setGorduraPorcentage(double gorduraPorcentage) {
        this.gorduraPorcentage = gorduraPorcentage;
    }

    public double getMudanca() {
        return mudanca;
    }

    public void setMudanca(double mudanca) {
        this.mudanca = mudanca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNascimento() {
        return nascimento;
    }

    public void setNascimento(int nascimento) {
        this.nascimento = nascimento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
