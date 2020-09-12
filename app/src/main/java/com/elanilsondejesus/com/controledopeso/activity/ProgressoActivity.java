package com.elanilsondejesus.com.controledopeso.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elanilsondejesus.com.controledopeso.R;
import com.elanilsondejesus.com.controledopeso.helper.DaoControle;
import com.elanilsondejesus.com.controledopeso.helper.DaoHistorico;
import com.elanilsondejesus.com.controledopeso.helper.DaoProgress;
import com.elanilsondejesus.com.controledopeso.helper.Preferencias;
import com.elanilsondejesus.com.controledopeso.helper.ProgressoDao;
import com.elanilsondejesus.com.controledopeso.model.HistoricoItem;
import com.elanilsondejesus.com.controledopeso.model.Progress;
import com.elanilsondejesus.com.controledopeso.model.Usuario;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class ProgressoActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {
    private AlertDialog dialog;
    private TextView maiorPeso, maiorIMc,porcentoGordura,kgGordura;
    private Usuario usuario = new Usuario();
    private List<Usuario> dados = new ArrayList<>();
    private Dialog dialox;
    private EditText camponovoPesoAtual;
    private Button salvarnovoPeso;
    private List<Progress> progresso = new ArrayList<>();
    private Progress progress = new Progress();
    private  int dia,mes,ano;
    private Calendar calendar;
    private TextView lblDate;
    private DateFormat dateFormat;
    private String datamudanca ="";
    float porcentual =0;
    private TextView mesSelecionado, anoSelecionado;
    private Preferencias preferencias;
    private  int mesRec=0 , anoRec=0;
    private   Progress banco = new Progress();
    private float recuperadoMeta =0;
    private float recuperadoProgresso =0;
    private float novoPesoFormatado =0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progresso);
        preferencias = new Preferencias(getApplicationContext());
//        getSupportActionBar().hide();

        iniciarComponentes();
        carregar();
        iniciarAtribuicao();
        carregarProgress();

        calcularImc();
        calcularPorcentagemdeGordura();
        recuperarPreferences();

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        dialox = new Dialog(this);


        graficoProgressoMes();
        graficoProgresCirculosoMes();
        progresso();


        FloatingActionButton floatAdiconarPEso = findViewById(R.id.adicionarPeso);
        floatAdiconarPEso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adicionarnovoPeso();
            }
        });

        mesSelecionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarMes();
            }
        });

    }
        public void recuperarPreferences(){
            //recuperando dados
            String mesRecuperado = preferencias.recuperarMes();
            String anoRecuperado = preferencias.recuperarAno();

            //verificar se ta nulo ou nao
            if(!mesRecuperado.equals("") && !anoRecuperado.equals("")){
                //se nao tive nulo entra aqui
                mesSelecionado.setText(mesRecuperado);
                anoSelecionado.setText(anoRecuperado);
                anoRec = Integer.parseInt(anoRecuperado);
                mesRec = Integer.parseInt(mesRecuperado);

                Toast.makeText(this, "Mes: "+mesRec+" ano: "+anoRec, Toast.LENGTH_SHORT).show();




            }

        }
        public void calcularPorcentagemdeGordura(){
            if(usuario.getImc() != 0.0){
                Date data = new Date();
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy");

                String anoatual =  formatador.format( data );
                int ano =Integer.parseInt(anoatual);
                int idade =ano - usuario.getNascimento();
                float imc = usuario.getImc();
                int sexo =0;
                if(usuario.getSexo().equalsIgnoreCase("Masculino")){
                    sexo=1;
                }else{
                    sexo=0;
                }

                porcentual = (float) ((1.20 * imc) + (0.23  * idade ) - (10.8 * sexo) - 5.4);
                DecimalFormat df = new DecimalFormat("#.##");
                String resultadoFormatado = df.format(porcentual).replace(",",".");
                porcentual = Float.parseFloat(resultadoFormatado);
               // Toast.makeText(this, "idade: "+idade, Toast.LENGTH_SHORT).show();
            }
        }
        public void calcularImc(){
                float peso=usuario.getPesoAtual(), altura=usuario.getAltura();
                float imcRecuperado = usuario.getImcMaior();
                 float imc=0;
                    DecimalFormat df = new DecimalFormat("#.##");

                imc = peso /(altura * altura);
                DaoControle dao = new DaoControle(getApplicationContext());
                String imcformatado = df.format(imc).replace(",",".");
                imc = Float.parseFloat(imcformatado);
                if(imc > imcRecuperado){

                usuario.setImcMaior(imc);
                  //  Toast.makeText(this, "IMC maior -----"+usuario.getImcMaior(), Toast.LENGTH_SHORT).show();
                }
                usuario.setImc(imc);
                if(dao.atualizar(usuario)){
                 //   Toast.makeText(this, "IMC adicionado:  "+usuario.getImc(), Toast.LENGTH_SHORT).show();
                }else{
                  //  Toast.makeText(this, "IMC falha", Toast.LENGTH_SHORT).show();

                }
    }
        public void graficoProgresCirculosoMes(){
            ProgressoDao dao = new DaoProgress(getApplicationContext());

            PieChart pieChart = findViewById(R.id.piechart002);
            ArrayList<PieEntry> visito  = new ArrayList<>();
            for(Progress pro: dao.listar()) {
                if(pro.getMes()==mesRec && pro.getAno()==anoRec) {
                  //  if (pro.getPeso() >= pro.getPeso() && pro.getAno() == anoRec) {

                        visito.add(new PieEntry(pro.getPeso(), String.valueOf(pro.getMes())));
                   // }/
                }


            }

    ///=================================================== circular


            PieDataSet pieDataSet = new PieDataSet(visito,"Meses");
            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(16f);
            PieData pieData = new PieData(pieDataSet);

            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false);
            pieChart.setCenterText("Seletores");
            pieChart.animate();
        }
        public void maiorPeso(float novo){
          //  Toast.makeText(this, "================"+novo, Toast.LENGTH_SHORT).show();
                double pesoAtual = usuario.getPesoAtual();

                DaoControle dao = new DaoControle(getApplicationContext());
            DecimalFormat df = new DecimalFormat("#.##");
            String novoPesoFormatado = df.format(novo);
            novo = Float.parseFloat(novoPesoFormatado);
            if ( novo > pesoAtual && pesoAtual == 0.0) {
                    usuario.setPesoMaior(novo);
                    if (dao.salvar(usuario)) {
                     //   Toast.makeText(this, "____Novo peso salvo", Toast.LENGTH_SHORT).show();
                    } else {
                      //  Toast.makeText(this, "falhaa Novo peso", Toast.LENGTH_SHORT).show();

                    }
                } else if (pesoAtual != 0.0 && novo > pesoAtual) {
                    usuario.setPesoMaior(novo);
                    if (dao.atualizar(usuario)) {
                     //   Toast.makeText(this, "-____Novo peso atualizadooo", Toast.LENGTH_SHORT).show();
                    } else {
                      //  Toast.makeText(this, "falhaa ao atualizaaaa", Toast.LENGTH_SHORT).show();

                    }
                }

            maiorPeso.setText(""+usuario.getPesoMaior()+" Kg");
        }
        public void graficoProgressoMes(){
            ProgressoDao dao = new DaoProgress(getApplicationContext(),8);

            final BarChart barChart = findViewById(R.id.barchart001);
            final ArrayList<BarEntry> visitors = new ArrayList<>();
            for(Progress pro: dao.listar()) {
                if(pro.getMes()==mesRec && pro.getAno()==anoRec){

                visitors.add(new BarEntry(pro.getDia(),pro.getPeso()));
                System.out.println(pro.getDia()+" - "+pro.getMes()+" - "+pro.getAno());
                Log.e("INFO", pro.getDia()+" - "+pro.getMes()+" - "+pro.getAno() );
                }


            }

            BarDataSet barDataSet = new BarDataSet(visitors,"Meses");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(16f);

            BarData barData = new BarData(barDataSet);
            barChart.setFitBars(true);
            barChart.setData(barData);
            barChart.getDescription().setText("Progresso anual");
            barChart.animateY(2000);
        }
        public void selecionarMes(){
            dialox.setContentView(R.layout.selecionarmeseano);
            dialox.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));

            lblDate = (TextView) dialox.findViewById(R.id.textDataPesoAtual);
            salvarnovoPeso = dialox.findViewById(R.id.buttonSalvarNovoPEsoAtual);
            lblDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(ProgressoActivity.this, ProgressoActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    update();


                }
            });
            salvarnovoPeso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //==============================================
                    //converter mes e ano
                    String messelecionado = String.valueOf(mes);
                    String anoselecionado = String.valueOf(ano);
                    if (!anoselecionado.equals("") && !messelecionado.equals("")) {

                        preferencias.salvarDados(messelecionado, anoselecionado);
                     //   Toast.makeText(ProgressoActivity.this, "Filtro selecionado", Toast.LENGTH_SHORT).show();
                    }
                            dialox.dismiss();
                }


            });

            dialox.show();


        }
        public void novoPeso(){
        //insere valor na batela historico que controle principal
            String pesoAtual = camponovoPesoAtual.getText().toString();//recebe o pesso atual4
            float novoPeso = Float.parseFloat(pesoAtual);// converte o pesso atual recebido
            maiorPeso(novoPeso);
            DaoHistorico daoHistorico = new DaoHistorico(getApplicationContext()); // cotem os historico
            DaoControle dao = new DaoControle(getApplicationContext()); // contem os conteudos principais


            HistoricoItem item = new HistoricoItem();
            float pesoexistente = usuario.getPesoAtual();
            String mudanca ="";
            if(pesoexistente == 0.0){
                pesoexistente = usuario.getPesoInicial();

            }
            double resultado = novoPeso - pesoexistente;

            DecimalFormat df = new DecimalFormat("#.##");
            resultado = Double.parseDouble(df.format(resultado).replace(",","."));

            if(novoPeso < pesoexistente){ // se o novo peso for menor ele recebe sinal -
                //    Toast.makeText(ProgressoActivity.this, "Novo peso: -"+resultado, Toast.LENGTH_SHORT).show();
                mudanca =""+resultado;
            }else{ // se o valor do novo peso for maior ele recebe o sinal +
                //   Toast.makeText(ProgressoActivity.this, "Novo peso: +"+resultado, Toast.LENGTH_SHORT).show();
                mudanca ="+"+resultado;

            }

            //double pesoformatado = Double.parseDouble(novoPeso);
            Float pesoFormatado = Float.parseFloat(df.format(novoPeso).replace(",","."));
            item.setData(datamudanca);
            item.setPeso(pesoFormatado);
            item.setMudanca(mudanca);

            usuario.setPesoAtual(novoPeso);

            if(daoHistorico.salvar(item)){ // adiciona novo valor ao historico
                //   Toast.makeText(ProgressoActivity.this, "Historico salvo", Toast.LENGTH_SHORT).show();
            }else{
                //  Toast.makeText(ProgressoActivity.this, "Falha ao salvar historico", Toast.LENGTH_SHORT).show();
            }
            if(dao.atualizar(usuario)){ // atualiza o novo peso no tabela princial
                //  Toast.makeText(ProgressoActivity.this, "Novo peso atualizado", Toast.LENGTH_SHORT).show();

            }else{
                //  Toast.makeText(ProgressoActivity.this, "Falhaa", Toast.LENGTH_SHORT).show();
            }
        }
        public Boolean progresso(){



            Boolean atualizar = false;
            for(Progress x: progresso){
                if(x.getDia() == dia && x.getMes() == mes && x.getAno() == ano){
                    atualizar=true;
                Toast.makeText(this, "id:"+x.getId()+"-Dia"+x.getDia()+"Mes"+x.getMes()+"Ano"+x.getAno()+"-Peso"+x.getPeso(), Toast.LENGTH_SHORT).show();
             banco.setId(x.getId());
             banco.setDia(x.getDia());
             banco.setMes(x.getMes());
             banco.setAno(x.getAno());
             banco.setPeso(x.getPeso());
              break;
                }else{
                    //atualizar = false;
                }
            }




                return atualizar;
                /*
                verifica ser os dados tem que atualizar ou salvaar como novo , ele tem que se recarregado tada vez que sofrer uma ação


                 */

        }
        public void adicionarnovoPeso(){
            dialox.setContentView(R.layout.adicionar_peso);
            dialox.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
            camponovoPesoAtual = dialox.findViewById(R.id.editnovoPesoAtual);
            lblDate = (TextView) dialox.findViewById(R.id.textDataPesoAtual);
            salvarnovoPeso = dialox.findViewById(R.id.buttonSalvarNovoPEsoAtual);
            lblDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(ProgressoActivity.this, ProgressoActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    update();


                }
            });
            salvarnovoPeso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DaoProgress dao = new DaoProgress(getApplicationContext());
                    String peso = camponovoPesoAtual.getText().toString();
                    float novoPeso = Float.parseFloat(peso);
                    novoPesoFormatado = novoPeso;
                //==============================================
                    novoPeso();



                    if(progresso()){ // recebe verdadeiro ou falso
                        Toast.makeText(getApplicationContext(), "atualizar", Toast.LENGTH_SHORT).show();
                        banco.setPeso(novoPesoFormatado);
                        if(dao.atualizar(banco)){



                            Toast.makeText(getApplicationContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                            dialox.dismiss();

                        }else{
                            Toast.makeText(getApplicationContext(), "Falha ao atualizar", Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(getApplicationContext(), "salvar", Toast.LENGTH_SHORT).show();
                        banco.setPeso(novoPesoFormatado);
                        banco.setDia(dia);
                        banco.setMes(mes);
                        banco.setAno(ano);
                        if(dao.salvar(banco)){
                            Toast.makeText(getApplicationContext(), "Salvar com sucesso", Toast.LENGTH_SHORT).show();
                            dialox.dismiss();

                        }else{
                            Toast.makeText(getApplicationContext(), "falha ao salvar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    graficoProgressoMes();
                    graficoProgresCirculosoMes();

                }


            });

            dialox.show();


        }
        public void iniciarComponentes(){
        maiorPeso = findViewById(R.id.textViewMaiorPEso);
        maiorIMc = findViewById(R.id.textViewMaiorIMC);
        porcentoGordura = findViewById(R.id.textViewPGordura);
//        kgGordura = findViewById(R.id.textViewGorduraKg);
        mesSelecionado = findViewById(R.id.textviewMesSelecionado);
        anoSelecionado = findViewById(R.id.textViewAnoSelecionado);

        }
        public void iniciarAtribuicao(){
        maiorPeso.setText(usuario.getPesoMaior()+" Kg");
        maiorIMc.setText(usuario.getImcMaior()+" Kg");
        porcentoGordura.setText(porcentual+"%");
        }
        public  void carregar(){


            DaoControle dao = new DaoControle(getApplicationContext());
            dados = dao.listar();
            for(Usuario user: dados){
                usuario.setId(user.getId());
                usuario.setPesoAtual(user.getPesoAtual());
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
                usuario.setPesoMaior(user.getPesoMaior());
              //  usuario.setDatamudanca(user.getDatamudanca());

            }

        }
        public void carregarProgress(){
            DaoProgress dao = new DaoProgress(getApplicationContext());
            progresso =dao.listar();
            for (Progress x: progresso){
                progress.setId(x.getId());
                progress.setDia(x.getDia());
                progress.setMes(x.getMes());
                progress.setAno(x.getAno());
                progress.setPeso(x.getPeso());

            }

        }
        @Override
        protected void onRestart() {
        super.onRestart();
        carregar();
        calcularImc();
        carregarProgress();

    }

        @Override
        protected void onResume() {
            super.onResume();
            carregar();
            carregarProgress();

        }

        @Override
        protected void onStart() {
            super.onStart();
            carregar();
            iniciarAtribuicao();
            calcularImc();
            calcularPorcentagemdeGordura();
            recuperarPreferences();
            progresso();
            graficoProgressoMes();
            graficoProgresCirculosoMes();


        }

        public void dialogData(){
            final BottomSheetDialog sheetDialog  = new BottomSheetDialog(
                    ProgressoActivity.this,R.style.BottomSheetDialogTheme
            );

            View sheetView = LayoutInflater.from(getApplicationContext()
            ).inflate(R.layout.dialog_calendario,(LinearLayout)findViewById(R.id.layoutDialogData));
            sheetView.findViewById(R.id.buttonSalvarData).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Toast.makeText(ProgressoActivity.this, "Funcionandoo....", Toast.LENGTH_SHORT).show();
                    sheetDialog.dismiss();
                }
            });
            sheetDialog.setContentView(sheetView);
            sheetDialog.show();

        }
        private void update() {

            lblDate.setText(+dia+"-"+(mes)+"-"+ano);
            // lblDate.setText(dateFormat.format(calendar.getTime()));
            // lblTime.setText(timeFormat.format(calendar.getTime()));
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(year, monthOfYear, dayOfMonth);
            dia = dayOfMonth; mes = monthOfYear+1; ano = year;
            //Toast.makeText(this, ""+dia+"/"+mes+"/"+ano, Toast.LENGTH_SHORT).show();
            String data = +ano+"-"+(mes)+"-"+dia;/// data seleciona  que é a meta data
            datamudanca = dia+"/"+mes+"/"+ano;
            String messelecionado = String.valueOf(mes);
            String anoselecionado = String.valueOf(ano);
            if (!anoselecionado.equals("") && !messelecionado.equals("")) {

                preferencias.salvarDados(messelecionado, anoselecionado);
               // Toast.makeText(ProgressoActivity.this, "Filtro selecionado", Toast.LENGTH_SHORT).show();

            }
            recuperarPreferences();
            update();
            graficoProgressoMes();
            graficoProgresCirculosoMes();

        }

    }