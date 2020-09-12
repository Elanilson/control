package com.elanilsondejesus.com.controledopeso.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.elanilsondejesus.com.controledopeso.R;
import com.elanilsondejesus.com.controledopeso.helper.DaoControle;
import com.elanilsondejesus.com.controledopeso.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario  usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getSupportActionBar().hide();

            /// criar aquele tempo da tela do splash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               carregarTelaHome();
            }
        },3500);



    }
    public void carregarTelaHome(){
        DaoControle dao = new DaoControle(getApplicationContext());
        usuarios = dao.listar();
        for(Usuario user: usuarios){
            usuario.setId(user.getId());
        }
        if(usuario.getId() != null){

        startActivity( new Intent(SplashActivity.this,HomeActivity.class));
        }else{
            startActivity( new Intent(SplashActivity.this, InformacoesActivity.class));

        }
//        startActivity( new Intent(SplashActivity.this,InformacoesActivity.class));
        finish();
    }
}