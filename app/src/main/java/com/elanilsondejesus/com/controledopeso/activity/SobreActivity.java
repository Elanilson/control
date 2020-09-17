package com.elanilsondejesus.com.controledopeso.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.elanilsondejesus.com.controledopeso.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        String descricao ="A evolução dos conceitos sobre Qualidade ao longo " +
                "do tempo, na chegada à chamada era atual da Gestão da Qualidade," +
                " ou da Qualidade Total está presente em vários livros, artigos," +
                " estudos de caso, normas e documentos emitidos pela ISO,";
        Element versao = new Element();
        versao.setTitle("Versão 1.0");
        View aboutPage = new AboutPage(this)
                .setImage(R.drawable.sobre)
                .setDescription(descricao)

                .addGroup("Entre em contato")
                .addEmail("elanilsonpp@hotmail.com","Envie um e-mail")
                .addWebsite("www.apkdoandroid.com","Acesse nosso site")

                .addGroup("Redes sociais")
                .addFacebook("elanilsondejesus","Facebook")
                .addInstagram("elanilsondejesus","Instagram")
                .addTwitter("elanilsondejesus","Twitter")
                .addYoutube("elanilsondejesus","Youtube")
                .addGitHub("elanilsondejesus","GitHub")
                .addPlayStore("elanilsondejesus","Download App")

                .addItem(versao)


                .create();
        setContentView(aboutPage);
    }

}