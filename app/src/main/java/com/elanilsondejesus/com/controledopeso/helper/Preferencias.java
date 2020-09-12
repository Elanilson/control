package com.elanilsondejesus.com.controledopeso.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context context;
    final String     NOME_ARQUIVO="data.preference";
    final String CHAVE_MES="mes";
    final String CHAVE_ANO="ano";
    final String CHAVE_META="meta";
    final String CHAVE_PROGRESSO="progresso";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO,0);
        editor = preferences.edit();
    }

    public void salvarDados(String mes,String ano){
        editor.putString(CHAVE_MES,mes);
        editor.putString(CHAVE_ANO,ano);
        editor.commit();

    }
    public void salvarMeta(float meta){
        editor.putFloat(CHAVE_META,meta);
        editor.commit();
    }
    public void salvarProgresso(float progresso){
        editor.putFloat(CHAVE_PROGRESSO,progresso);
        editor.commit();
    }
    public float recuperarMeta(){
        return preferences.getFloat(CHAVE_META,0);
    }
    public float recuperarProgrsso(){
        return preferences.getFloat(CHAVE_META,0);
    }

    public String recuperarMes(){



        return preferences.getString(CHAVE_MES,"");

    }
    public String recuperarAno(){
        return preferences.getString(CHAVE_ANO,"");
    }
}
