package com.elanilsondejesus.com.controledopeso.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.elanilsondejesus.com.controledopeso.activity.ProgressoActivity;
import com.elanilsondejesus.com.controledopeso.model.Progress;
import com.elanilsondejesus.com.controledopeso.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DaoProgress implements ProgressoDao{
private  Context context;
private SQLiteDatabase escrever;
private SQLiteDatabase ler;
int x;


    public DaoProgress(Context context, int v ){
        x=v;
        this.context = context;
        Bando_bd db = new Bando_bd(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();

    }


    public DaoProgress(Context context ){
        this.context = context;
        Bando_bd db = new Bando_bd(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();

    }

    @Override
    public Boolean salvar(Progress progress) {
        ContentValues cv = new ContentValues();
        cv.put("dia",progress.getDia());
        cv.put("mes",progress.getMes());
        cv.put("ano",progress.getAno());
        cv.put("peso",progress.getPeso());




        try {
            escrever.insert(Bando_bd.TABELA_PROGRESSO,null,cv);
            Log.i("INFO", "Dados salva com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao salvar Dados " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Boolean atualizar(Progress progress) {
        ContentValues cv = new ContentValues();
        cv.put("id",progress.getId());
        cv.put("dia",progress.getDia());
        cv.put("mes",progress.getMes());
        cv.put("ano",progress.getAno());
        cv.put("peso",progress.getPeso());




        try {
            String [] args ={progress.getId().toString()};
            escrever.update(Bando_bd.TABELA_PROGRESSO,cv,"id=?",args);
            Log.i("INFO", "Dados Atualizado com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao Atualizar Dados " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Boolean deletar(Progress progress) {

        try{
            String [] args = {progress.getId().toString()};
            escrever.delete(Bando_bd.TABELA_PROGRESSO,"id=?",args);
            Log.i("INFO", "Dados deletado com sucesso!");

        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao deletar dados" + e.getMessage() );
            return  false;

        }
        return true;
    }

    @Override
    public List<Progress> listar() {

        List<Progress> dados =new ArrayList<>();

        
        String sql ="SELECT * FROM "+Bando_bd.TABELA_PROGRESSO +"   ;";
            Cursor c = ler.rawQuery(sql,null);

            while (c.moveToNext()){
                Progress dadosDoUsuario = new Progress();

                Long id =c.getLong(c.getColumnIndex("id"));
                float peso = c.getFloat(c.getColumnIndex("peso"));
                int dia = c.getInt(c.getColumnIndex("dia"));
                int mes = c.getInt(c.getColumnIndex("mes"));
                int ano = c.getInt(c.getColumnIndex("ano"));


                dadosDoUsuario.setId(id);
                dadosDoUsuario.setDia(dia);
                dadosDoUsuario.setMes(mes);
                dadosDoUsuario.setAno(ano);
                dadosDoUsuario.setPeso(peso);


                dados.add(dadosDoUsuario);
                Log.i("Lista:", "Os dados estão sendo listados : =0" );



            }

            return dados;

    }


}
