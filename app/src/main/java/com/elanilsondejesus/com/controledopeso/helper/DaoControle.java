package com.elanilsondejesus.com.controledopeso.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elanilsondejesus.com.controledopeso.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DaoControle implements ControleDao {

private SQLiteDatabase escrever;
private SQLiteDatabase ler;

    public DaoControle(Context context){
        Bando_bd db = new Bando_bd(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public Boolean salvar(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put("nome",usuario.getNome());
        cv.put("pesoInicial",usuario.getPesoInicial());
        cv.put("pesoatual",usuario.getPesoAtual());
        cv.put("mudanca",usuario.getMudanca());
        cv.put("maiorpeso",usuario.getPesoMaior());
     //   cv.put("datamudanca",usuario.getDatamudanca());

        cv.put("meta",usuario.getMeta());
        cv.put("dataMeta",usuario.getData());
        cv.put("dataNascimento",usuario.getNascimento());
        cv.put("altura",usuario.getAltura());
        cv.put("sexo",usuario.getSexo());
        cv.put("imc",usuario.getImc());
        cv.put("maiorimc",usuario.getImcMaior());

        try {
            escrever.insert(Bando_bd.TABELA_DADOS,null,cv);
            Log.i("INFO", "Dados salva com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao salvar Dados " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Boolean atualizar(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put("nome",usuario.getNome());
        cv.put("pesoInicial",usuario.getPesoInicial());
        cv.put("pesoatual",usuario.getPesoAtual());
        cv.put("mudanca",usuario.getMudanca());
        cv.put("meta",usuario.getMeta());
        cv.put("dataMeta",usuario.getData());
        cv.put("dataNascimento",usuario.getNascimento());
        cv.put("altura",usuario.getAltura());
        cv.put("sexo",usuario.getSexo());
        cv.put("maiorpeso",usuario.getPesoMaior());
        cv.put("imc",usuario.getImc());
        cv.put("maiorimc",usuario.getImcMaior());



        try {
            String [] args ={usuario.getId().toString()};
            escrever.update(Bando_bd.TABELA_DADOS,cv,"id=?",args);
            Log.i("INFO", "Dados Atualizado com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao Atualizar Dados " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Boolean deletar(Usuario usuario) {

        try{
            String [] args = {usuario.getId().toString()};
            escrever.delete(Bando_bd.TABELA_DADOS,"id=?",args);
            Log.i("INFO", "Dados deletado com sucesso!");

        }catch (Exception e){
            e.printStackTrace();
            Log.e("INFO", "Erro ao deletar dados" + e.getMessage() );
            return  false;

        }
        return true;
    }

    @Override
    public List<Usuario> listar() {

        List<Usuario> dados =new ArrayList<>();
        String sql ="SELECT * FROM "+Bando_bd.TABELA_DADOS +" WHERE id=1;";
        Cursor c = ler.rawQuery(sql,null);

        while (c.moveToNext()){
        Usuario dadosDoUsuario = new Usuario();

        Long id =c.getLong(c.getColumnIndex("id"));
        float pesoinicial = c.getFloat(c.getColumnIndex("pesoInicial"));
        float pesoatual = c.getFloat(c.getColumnIndex("pesoatual"));
        float meta = c.getFloat(c.getColumnIndex("meta"));
        double mudanca = c.getDouble(c.getColumnIndex("mudanca"));
        float maiorpeso = c.getFloat(c.getColumnIndex("maiorpeso"));
        String nome = c.getString(c.getColumnIndex("nome"));
       // String datamudanca = c.getString(c.getColumnIndex("datamudanca"));
        String sexo = c.getString(c.getColumnIndex("sexo"));
        String data = c.getString(c.getColumnIndex("dataMeta"));
        int dataNascimento = c.getInt(c.getColumnIndex("dataNascimento"));
        float altura = c.getFloat(c.getColumnIndex("altura"));
        float imc = c.getFloat(c.getColumnIndex("imc"));
        float maiorimc = c.getFloat(c.getColumnIndex("maiorimc"));


        dadosDoUsuario.setId(id);
        dadosDoUsuario.setPesoInicial(pesoinicial);
        dadosDoUsuario.setPesoAtual(pesoatual);
        dadosDoUsuario.setMeta(meta);
        dadosDoUsuario.setNome(nome);
        dadosDoUsuario.setData(data);
        dadosDoUsuario.setNascimento(dataNascimento);
        dadosDoUsuario.setAltura(altura);
        dadosDoUsuario.setSexo(sexo);
        dadosDoUsuario.setMudanca(mudanca);
        dadosDoUsuario.setPesoMaior(maiorpeso);
        dadosDoUsuario.setImc(imc);
        dadosDoUsuario.setImcMaior(maiorimc);
      //  dadosDoUsuario.setDatamudanca(datamudanca);

        dados.add(dadosDoUsuario);
            Log.i("Lista:", "Os dados estão sendo listados" );



        }
        return dados;
    }

}
