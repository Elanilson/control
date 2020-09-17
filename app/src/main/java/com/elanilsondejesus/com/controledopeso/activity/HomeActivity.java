package com.elanilsondejesus.com.controledopeso.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elanilsondejesus.com.controledopeso.R;
import com.elanilsondejesus.com.controledopeso.helper.DaoControle;
import com.elanilsondejesus.com.controledopeso.helper.DaoHistorico;
import com.elanilsondejesus.com.controledopeso.helper.HistoricoDao;
import com.elanilsondejesus.com.controledopeso.helper.Preferencias;
import com.elanilsondejesus.com.controledopeso.model.HistoricoItem;
import com.elanilsondejesus.com.controledopeso.model.Usuario;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ProgressBar progressobar;
    private List<Usuario> dados = new ArrayList<>();
    private Usuario usuario = new Usuario();
    private String verificar="" ;// verificar se o usuario já forneceu as informações antes
    private TextView campoPesoInicial, campoPesoAtual,campoMeta,camposDiasRestantes,camposMudançaDePeso,
    kgPesoQueFalta;
    private String dataAtual="";
    private Long diasRestantes =Long.parseLong("0");
    private Long progress =Long.parseLong("0");
    private float kgRestante =0;
    private  int diatu ;// dia atual sem formatacao para colocar no progressobar
    private HistoricoItem item  = new HistoricoItem();
    private AlertDialog alerta;
    private Dialog dialox;
    private Button adicionarNovaMeta;
    private ImageButton closeMeta;
    private Preferencias preferencias;
    private Boolean alertaMetaBatida =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        preferencias = new Preferencias(getApplicationContext());

        inicializarComponentes();
        kgRestante();
        carregar();
        progressoDias();
        atriobuicao();
        diasRestantes();
        diasRestantes();



        Toast.makeText(this, "peso: "+usuario.getPesoAtual(), Toast.LENGTH_SHORT).show();
        dialox = new Dialog(this);
    }
//    public void setProgressobar(){
//        // para perde peso
//        DaoHistorico hist = new DaoHistorico(getApplicationContext());
//        DaoControle dao = new DaoControle(getApplicationContext());
//
//        int max =0;
//        int progress = 0;
//
//            String mudanca="";
//        if(usuario.getPesoRestante() != 0){
//           float x = usuario.getPesoRestante();
//          max  = Math.round(x);
//          progressobar.setMax(max);
//
//
//            for(HistoricoItem item: hist.listar()){
//                mudanca = item.getMudanca().replace("+","");
//            float mudancaConvertida = Float.parseFloat(mudanca);
//            progress = Math.round(mudancaConvertida);
//            progress += progress;
//            progressobar.setProgress(progress);
//            }
//        }
//
//
//
//
//
//    }
public void recuperarPreferences(){
    //recuperando dados
    Boolean alerta = preferencias.recuperarALERTAMeta();


    //verificar se ta nulo ou nao
    if(alerta){
        //se nao tive nulo entra aqui
        alertaMetaBatida = alerta;
        Toast.makeText(this, "Alerta Ativo: "+alertaMetaBatida, Toast.LENGTH_SHORT).show();

    }

}
        public void diasRestantes(){

            Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");


        dataAtual = formatador.format( data );
            if (usuario.getData() != null) {
            try {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dataDe = sdf.parse(dataAtual);
                Date dataAte = sdf.parse(usuario.getData());

                    long diferencaSegundos = (dataAte.getTime() - dataDe.getTime()) / (1000);
                    long diferencaMinutos = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60);
                    long diferencaHoras = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60);
                    long diferencaDias = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60 * 24);
                    long diferencaDiast = (dataAte.getTime() + dataDe.getTime()) / (1000 * 60 * 60 * 24);
                    long diferencaMeses = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60 * 24) / 30;
                    long diferencaAnos = ((dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60 * 24) / 30) / 12;
                    diasRestantes = diferencaDias;


                System.out.println(String.format("Diferença em Segundos: ", diferencaSegundos));
                    System.out.println(String.format("Diferença em Minutos: ", diferencaMinutos));
                    System.out.println(String.format("Diferença em Horas: ", diferencaHoras));
                    System.out.println(String.format("Diferença em Dias: ", diferencaDias));
                    System.out.println(String.format("Diferença em Meses: ", diferencaMeses));
                    System.out.println(String.format("Diferença em Anos: ", diferencaAnos));
                    //Toast.makeText(this, "" + diferencaDias, Toast.LENGTH_SHORT).show();
                } catch(ParseException e){
                    e.printStackTrace();
                }

            }

        }


        public String progressoDias(){
            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
           String dia = formatador.format(data);

            dia = String.valueOf(camposDiasRestantes.getText());
            System.out.println("teste:   "+dia);



           return dia;
        }

        public void premium(View view){
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Adquerir premium");

            //define a mensagem
            builder.setMessage("Ao adquirir o premium você estará colaborando com nossos desenvolvedores a melhorar o aplicativo e assim poderá  remover ads ou propagandas.");

            //define um botão como positivo
    builder.setPositiveButton("Em breve", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });

            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
    alerta.show();
        }
    public void inicializarComponentes(){
        //progressBarFundo = findViewById(R.id.progressBarFundo);
        campoMeta = findViewById(R.id.textViewMeta);
        campoPesoAtual = findViewById(R.id.textViewPesoAtual);
       campoPesoInicial = findViewById(R.id.textViewPesoInicialHome);
        camposDiasRestantes = findViewById(R.id.textDiasRestante);
        camposMudançaDePeso = findViewById(R.id.textudancaHome);
        kgPesoQueFalta = findViewById(R.id.textViewPesoqueFalta);
        progressobar = findViewById(R.id.progressBarKg);


    }
    public Float kgRestante(){
            DecimalFormat df = new DecimalFormat("#.##");

         if(usuario.getPesoAtual() == usuario.getMeta()){
            kgRestante =   0 ;
//
             kgPesoQueFalta.setText("00.00");
            Toast.makeText(this, "444444444444444444", Toast.LENGTH_SHORT).show();

        }else if(usuario.getPesoAtual() > usuario.getMeta()){
                kgRestante =     usuario.getPesoAtual()-usuario.getMeta() ;
                Toast.makeText(this, "111111111111111", Toast.LENGTH_SHORT).show();

            }else if(usuario.getMeta() > usuario.getPesoAtual() ){
                kgRestante =     usuario.getMeta() -usuario.getPesoAtual() ;
                Toast.makeText(this, "222222222222", Toast.LENGTH_SHORT).show();

            }

        String valorFormatado = df.format(kgRestante).replace(",",".");
        kgRestante = Float.parseFloat(valorFormatado);

        if(kgRestante !=0.0) {
            return kgRestante;

        }else{
            return kgRestante= 00;
            }


    }

    public  void carregar(){


        DaoControle dao = new DaoControle(getApplicationContext());
        dados = dao.listar();
        for(Usuario user: dados){
            usuario.setId(user.getId());
            usuario.setNome(user.getNome());
            usuario.setNascimento(user.getNascimento());
            usuario.setPesoInicial(user.getPesoInicial());
            usuario.setAltura(user.getAltura());
            usuario.setMeta(user.getMeta());
            usuario.setAltura(user.getAltura());
            usuario.setData(user.getData());
            usuario.setImcMaior(user.getImcMaior());
            usuario.setImc(user.getImc());
            usuario.setGorduraPorcentage(user.getGorduraPorcentage());
            usuario.setGorduraKG(user.getGorduraKG());
            usuario.setSexo(user.getSexo());
            usuario.setMudanca(user.getMudanca());
            usuario.setPesoAtual(user.getPesoAtual());
            usuario.setPesoRestante(user.getPesoRestante());
            usuario.setPesoinicialmeta(user.getPesoinicialmeta());


        }





    }
    public void carregarDiasRestantes(){
        if(diasRestantes != 0){
            if(diasRestantes >= 2){
                camposDiasRestantes.setText(""+diasRestantes+" dias");
            }else{
                camposDiasRestantes.setText(""+diasRestantes+" dia");
            }
        }

        String teste = camposDiasRestantes.getText().toString();
        String x[] = teste.split(" dias");

        String xy[] = x[0].split(" dia");


        campoPesoAtual.setText(String.valueOf(usuario.getPesoAtual()));

        if(usuario.getMeta() != 0.0){
            campoMeta.setText(""+usuario.getMeta()+" Kg");
        }
        if(usuario.getPesoInicial() != 0.0){
            campoPesoInicial.setText(""+usuario.getPesoInicial()+" Kg");
        }


    }
    public void progresso (View view){
        startActivity(new Intent(HomeActivity.this,ProgressoActivity.class));
         onStop();

    }
    public void perfil(View view){
        startActivity(new Intent(HomeActivity.this, PerfilActivity.class));

    }
    public void meta(View view){
        startActivity(new Intent(HomeActivity.this,MetaActivity.class));

    }
    public void historico(View view){
        startActivity(new Intent(HomeActivity.this,HistoricoActivity.class));

    }
    public void compartilhar(View view){
        startActivity(new Intent(HomeActivity.this, SobreActivity.class));

    }
    public void dialo(){

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setCancelable(false);
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.parabens, null);

                TextView title = (TextView) view.findViewById(R.id.title);
                ImageButton imageButton = (ImageButton) view.findViewById(R.id.image);

                title.setText("Novo membro!");

                imageButton.setImageResource(R.drawable.membro);

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity( new Intent(HomeActivity.this, InformacoesActivity.class));                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HomeActivity.this, "SAindo....", Toast.LENGTH_LONG).show();
                        finish();

                    }
                });

                builder.setView(view);
                builder.show();



    }

    @Override
    protected void onStart() {
        super.onStart();
carregar();
diasRestantes();
progressoDias();
mudanca();
atriobuicao();
carregarDiasRestantes();
verficarSeAMetaFoiBatida();
        recuperarPreferences();

    }
    public void verficarSeAMetaFoiBatida(){

       if(alertaMetaBatida){
           if (usuario.getPesoinicialmeta() > usuario.getMeta() && usuario.getPesoAtual() <= usuario.getMeta()){
               alertaDemetaBatida();
           }else if (usuario.getPesoinicialmeta() < usuario.getMeta() && usuario.getPesoAtual() >= usuario.getMeta()){
               alertaDemetaBatida();

           }
       }

    }
    public void alertaDemetaBatida(){
        dialox.setContentView(R.layout.parabens);
        dialox.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        adicionarNovaMeta = dialox.findViewById(R.id.buttonNovameta);
        closeMeta = dialox.findViewById(R.id.imageButtonCloseMeta);
        adicionarNovaMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MetaActivity.class));
                preferencias.salvarAlertaMeta(false);
                recuperarPreferences();
                dialox.dismiss();
            }
        });
        closeMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencias.salvarAlertaMeta(false);
                recuperarPreferences();
                dialox.dismiss();
            }
        });



        dialox.show();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        diasRestantes();
        carregarDiasRestantes();
    }

    public void atriobuicao(){

        if(kgRestante() >0){

            kgPesoQueFalta.setText(kgRestante() +" Kg");
        }
        campoPesoAtual.setText(usuario.getPesoAtual()+" Kg");
        if(kgRestante() == 00.00){

            kgPesoQueFalta.setText(kgRestante() +" Kg");
        }
        if(usuario.getPesoAtual() > usuario.getMeta()){
            kgPesoQueFalta.setText("+"+kgRestante() +" Kg");
        }

    }

    public void mudanca(){
        HistoricoDao dao = new DaoHistorico(getApplicationContext());
        for(HistoricoItem item: dao.listar()){
            if(item.getMudanca() != null){
                this.item.setMudanca(item.getMudanca());

            }
        }
        if(item.getMudanca() != null){

        camposMudançaDePeso.setText(""+item.getMudanca()+" Kg");

            if(item.getMudanca().contains("+")){
                camposMudançaDePeso.setTextColor(Color.rgb(34,139,34));

            }else{
                // holder.mudanca.setTextColor(Color.RED);
                camposMudançaDePeso.setTextColor(Color.rgb(255,99,71));

            }
        }


    }




}