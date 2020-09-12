package com.elanilsondejesus.com.controledopeso.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elanilsondejesus.com.controledopeso.model.HistoricoItem;

import java.util.ArrayList;
import java.util.List;

public class DaoHistorico implements HistoricoDao{

private SQLiteDatabase escrever;
private SQLiteDatabase ler;

    public DaoHistorico(Context context){
        Bando_bd db = new Bando_bd(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public Boolean salvar (HistoricoItem itemHistorico) {
        ContentValues cv = new ContentValues();
        cv.put("data",itemHistorico.getData());
        cv.put("novopeso",itemHistorico.getPeso());
        cv.put("mudanca",itemHistorico.getMudanca());




        try {
            escrever.insert(Bando_bd.TABELA_HISTORICO,null,cv);
            Log.i("INFO", "Dados salva com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao salvar Dados " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Boolean atualizar(HistoricoItem itemHistorico) {
        ContentValues cv = new ContentValues();
        cv.put("id",itemHistorico.getId());
        cv.put("data",itemHistorico.getData());
        cv.put("novopeso",itemHistorico.getPeso());
        cv.put("mudanca",itemHistorico.getMudanca());




        try {
            String [] args ={itemHistorico.getId().toString()};
            escrever.update(Bando_bd.TABELA_HISTORICO,cv,"id=?",args);
            Log.i("INFO", "Dados Atualizado com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao Atualizar Dados " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Boolean deletar(HistoricoItem itemHistorico) {

        try{
            String [] args = {itemHistorico.getId().toString()};
            escrever.delete(Bando_bd.TABELA_HISTORICO,"id=?",args);
            Log.i("INFO", "Dados deletado com sucesso!");

        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao deletar dados" + e.getMessage() );
            return  false;

        }
        return true;
    }

    @Override
    public List<HistoricoItem> listar() {

        List<HistoricoItem> itens =new ArrayList<>();
        String sql ="SELECT * FROM "+Bando_bd.TABELA_HISTORICO +";";
        Cursor c = ler.rawQuery(sql,null);

        while (c.moveToNext()){
            HistoricoItem item = new HistoricoItem();

        Long id =c.getLong(c.getColumnIndex("id"));
        String data = c.getString(c.getColumnIndex("data"));
        float peso = c.getFloat(c.getColumnIndex("novopeso"));
        String mudanca = c.getString(c.getColumnIndex("mudanca"));


        item.setId(id);
        item.setData(data);
        item.setPeso(peso);
        item.setMudanca(mudanca);
        itens.add(item);
            Log.i("Lista:", "Os dados estão sendo listados" );



        }
        return itens;
    }
}
