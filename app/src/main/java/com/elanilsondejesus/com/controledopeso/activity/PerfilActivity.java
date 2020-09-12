package com.elanilsondejesus.com.controledopeso.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.elanilsondejesus.com.controledopeso.R;
import com.elanilsondejesus.com.controledopeso.helper.DaoControle;
import com.elanilsondejesus.com.controledopeso.model.Usuario;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilActivity extends AppCompatActivity {
    private EditText  campoIdade, campoPeso, campoAltura;
    private TextView textViewPeso, textViewImc, textViewGordura ,campoNome;
    private  Usuario usuario = new Usuario();
    float porcentual =0;
    float imc=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        inicializarComponentes();
        carregarDados();
        calcularPorcentagemdeGordura();
        calcularImc();
    }
public  void carregarDados(){
    DaoControle dao = new DaoControle(getApplicationContext());

    for(Usuario user: dao.listar()){
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

    }
    campoNome.setText(usuario.getNome());
    campoIdade.setText(calcularIdade()+" anos");
    campoPeso.setText(usuario.getPesoInicial()+" Kg");
    campoAltura.setText(usuario.getAltura()+"");
    textViewPeso.setText(usuario.getPesoAtual()+" Kg");
    textViewImc.setText(imc+"");
    textViewGordura.setText(porcentual+" ");

}
public void editarPerfil(View view){
    startActivity( new Intent(PerfilActivity.this, InformacoesActivity.class));

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

        DecimalFormat df = new DecimalFormat("#.##");

        imc = peso /(altura * altura);
        DaoControle dao = new DaoControle(getApplicationContext());
        String imcformatado = df.format(imc).replace(",",".");
        imc = Float.parseFloat(imcformatado);

    }
public int calcularIdade(){
    Date data = new Date();
    SimpleDateFormat formatador = new SimpleDateFormat("yyyy");

    String anoatual =  formatador.format( data );
    int ano =Integer.parseInt(anoatual);
    int idade =ano - usuario.getNascimento();
    return idade;
}

    public void inicializarComponentes(){
        campoNome = findViewById(R.id.TextTextPerfilNome);
        campoIdade = findViewById(R.id.editTextTextPerfilIdade);
        campoPeso = findViewById(R.id.editTextTextPerfilPeso);
        campoAltura = findViewById(R.id.editTextTextPerfilAltura);
        textViewPeso = findViewById(R.id.textViewPerfilPainelPEso);
        textViewImc = findViewById(R.id.textViewPerfilPainelImc);
        textViewGordura = findViewById(R.id.textViewPerfilPainelGordura);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarDados();
    }
}