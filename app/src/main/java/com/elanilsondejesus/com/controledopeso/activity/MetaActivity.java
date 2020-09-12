package com.elanilsondejesus.com.controledopeso.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elanilsondejesus.com.controledopeso.R;
import com.elanilsondejesus.com.controledopeso.helper.DaoControle;
import com.elanilsondejesus.com.controledopeso.helper.Preferencias;
import com.elanilsondejesus.com.controledopeso.model.Usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MetaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView lblDate;
    private TextView lblTime;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private Usuario usuario = new Usuario();
    private Button buttonSalvar;
    private EditText meta, pesoaltual;
    private TextView datameta, diasRestantes;
    private  int dia,mes,ano;
    private String dataAtual;
    private List<Usuario>dados = new ArrayList<>();
    private Preferencias preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta);
        preferencias = new Preferencias(getApplicationContext());
        iniciarComponentes();




        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
      //  timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        lblDate = (TextView) findViewById(R.id.textViewDataDados);
       // lblTime = (TextView) findViewById(R.id.lblTime);

        //update();
        lblDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MetaActivity.this, MetaActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                update();


            }
        });

        recuperarDados();
       // pegandoDataAtual();
        update();
    }

    public void pegandoDataAtual(){
//        Date data = new Date();
//        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
//
//        dataAtual = formatador.format( data );
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataDe = sdf.parse("2020-01-01");
            Date dataAte = sdf.parse("2019-01-01");

            long diferencaSegundos = (dataAte.getTime() - dataDe.getTime()) / (1000);
            long diferencaMinutos = (dataAte.getTime() - dataDe.getTime()) / (1000*60);
            long diferencaHoras = (dataAte.getTime() - dataDe.getTime()) / (1000*60*60);
            long diferencaDias = (dataAte.getTime() - dataDe.getTime()) / (1000*60*60*24);
            long diferencaMeses = (dataAte.getTime() - dataDe.getTime()) / (1000*60*60*24) / 30;
            long diferencaAnos = ((dataAte.getTime() - dataDe.getTime()) / (1000*60*60*24) / 30) / 12;

            System.out.println(String.format("Diferença em Segundos: ", diferencaSegundos));
            System.out.println(String.format("Diferença em Minutos: ", diferencaMinutos));
            System.out.println(String.format("Diferença em Horas: ", diferencaHoras));
            System.out.println(String.format("Diferença em Dias: ", diferencaDias));
            System.out.println(String.format("Diferença em Meses: ", diferencaMeses));
            System.out.println(String.format("Diferença em Anos: ", diferencaAnos));
            Toast.makeText(this, ""+diferencaDias, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }


       // Toast.makeText(this, ""+b, Toast.LENGTH_SHORT).show();
    }
    private void update() {

        lblDate.setText(+dia+"-"+(mes+1)+"-"+ano);
       // lblDate.setText(dateFormat.format(calendar.getTime()));
       // lblTime.setText(timeFormat.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        dia = dayOfMonth; mes = monthOfYear; ano = year;
        //Toast.makeText(this, ""+dia+"/"+mes+"/"+ano, Toast.LENGTH_SHORT).show();
        String data = +ano+"-"+(mes+1)+"-"+dia;/// data seleciona  que é a meta data
        usuario.setData(data);
        DaoControle dao = new DaoControle(getApplicationContext());

        if(dao.atualizar(usuario)){
            Toast.makeText(MetaActivity.this, "Sucesso data", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(MetaActivity.this, "falla data", Toast.LENGTH_SHORT).show();
        }

        update();
    }
    public void recuperarDados(){
        DaoControle dao = new DaoControle(getApplicationContext());
        dados = dao.listar();
        for(Usuario user: dados){
            usuario.setId(user.getId());
            usuario.setNome(user.getNome());
            usuario.setNascimento(user.getNascimento());
            usuario.setPesoInicial(user.getPesoInicial());
            usuario.setAltura(user.getAltura());
            usuario.setMeta(user.getMeta());
            usuario.setData(user.getData());
            usuario.setImcMaior(user.getImcMaior());
            usuario.setImc(user.getImc());
            usuario.setGorduraPorcentage(user.getGorduraPorcentage());
            usuario.setGorduraKG(user.getGorduraKG());
            usuario.setSexo(user.getSexo());
            usuario.setMudanca(user.getMudanca());
            usuario.setPesoAtual(user.getPesoAtual());

        }
        meta.setText(""+usuario.getMeta());
        pesoaltual.setText(""+usuario.getPesoAtual());
        datameta.setText(""+usuario.getData());


    }
    public void salvar ( View view){
        DaoControle dao = new DaoControle(this);
        float meta = Float.parseFloat(this.meta.getText().toString());
    float peso = Float.parseFloat(pesoaltual.getText().toString());

//        String data = +ano+"-"+(mes+1)+"-"+dia;/// data seleciona  que é a meta data
//        Usuario usuario1 = new Usuario();
//
//


        if (usuario.getMeta() == 0.0 || usuario.getData().isEmpty() || usuario.getData() == null || usuario.getData().equals("")) {
          preferencias.salvarMeta(meta);
            usuario.setMeta(meta);
            usuario.setPesoAtual(peso);

            //  usuario.setData(data);

            if (dao.atualizar(usuario)) {
                Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Falha", Toast.LENGTH_SHORT).show();

            }
        } else {

            // usuario.setData(data);
            usuario.setPesoAtual(peso);
            usuario.setMeta(meta);
            if (dao.atualizar(usuario)) {
                Toast.makeText(this, "Atualizado", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "flha Atualiza", Toast.LENGTH_SHORT).show();
            }
        }


    }






    public void iniciarComponentes(){
        buttonSalvar = findViewById(R.id.buttonSalvarDadosMeta);
        meta = findViewById(R.id.editMetaDados);
        pesoaltual = findViewById(R.id.editMetaPesoAtualDados);
        datameta = findViewById(R.id.textViewDataDados);
        diasRestantes = findViewById(R.id.textDiasRestante);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarDados();
    }
}