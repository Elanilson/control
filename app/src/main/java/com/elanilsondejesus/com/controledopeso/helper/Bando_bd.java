package com.elanilsondejesus.com.controledopeso.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Bando_bd extends SQLiteOpenHelper {

    public static int VERSION= 1;
    public static String NOME_BD = "CONTROLE_DO_PESO";
    public static String TABELA_DADOS = "DADOS";
    public static String TABELA_PROGRESSO = "PROGRESSO";
    public static String TABELA_HISTORICO = "HISTORICO";

    public Bando_bd( Context context) {
        super(context, NOME_BD,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = " CREATE TABLE IF NOT EXISTS "+TABELA_DADOS
                +"(id INTEGER PRIMARY KEY AUTOINCREMENT,  nome varchar(15)," +
                "pesoInicial double,meta float,dataMeta varchar(10)," +
                "dataNascimento int,altura float,sexo varchar(9),pesoatual float,mudanca double,maiorpeso float,imc float,maiorimc float);";


        String sql2 = "CREATE TABLE IF NOT EXISTS "+TABELA_PROGRESSO+"(id INTEGER PRIMARY KEY AUTOINCREMENT, dia int, mes int,ano int, peso float);";

        String sql3 = "CREATE TABLE IF NOT EXISTS "+TABELA_HISTORICO+"(id INTEGER PRIMARY KEY AUTOINCREMENT, data varchar(10), novopeso float, mudanca varchar(8));";
        try{
            db.execSQL(sql);
            db.execSQL(sql2);
            db.execSQL(sql3);
            Log.i("INFO DB", "Sucesso ao criar TABELA" );
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar TABELA" );

            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
