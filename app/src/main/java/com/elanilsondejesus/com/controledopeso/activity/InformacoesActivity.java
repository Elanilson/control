package com.elanilsondejesus.com.controledopeso.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.elanilsondejesus.com.controledopeso.R;
import com.elanilsondejesus.com.controledopeso.helper.DaoControle;
import com.elanilsondejesus.com.controledopeso.model.Usuario;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class InformacoesActivity extends AppCompatActivity {
    private Switch camposexo;
    private EditText campNascimento,campNome,campoPeso,campoaltura;
    private AlertDialog dialog;
   private Usuario usuario = new Usuario();
   private List<Usuario> dados = new ArrayList<>();
   private  String sexo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);
        carregarCompontes();
        recuperarDados();

        camposexo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sexo ="Masculino";
                    usuario.setSexo(sexo);
                }else{
                    sexo ="Feminino";
                    usuario.setSexo(sexo);

                }
            }
        });

        if(usuario.getSexo() != null && usuario.getSexo().equalsIgnoreCase("Masculino")){
            camposexo.setChecked(true);

        }else{
            camposexo.setChecked(false);
        }

    }

    public void carregarCompontes(){
        camposexo = findViewById(R.id.switchSexo);
        campNascimento = findViewById(R.id.editPerfilNascimento);
        campoPeso = findViewById(R.id.editPerfilpeso);
        campNome = findViewById(R.id.editPerfilNome);
        campoaltura = findViewById(R.id.editPerfilAltura);
       // linearLayout = findViewById(R.id.layoutInformacoes);
    }
public void loading(){
    dialog = new SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Carregando dados")
            .setCancelable( false )
            .build();
    dialog.show();
}
public void dialogData(){
        final BottomSheetDialog sheetDialog  = new BottomSheetDialog(
                InformacoesActivity.this,R.style.BottomSheetDialogTheme
        );

        View sheetView = LayoutInflater.from(getApplicationContext()
    ).inflate(R.layout.dialog_calendario,(LinearLayout)findViewById(R.id.layoutDialogData));
        sheetView.findViewById(R.id.buttonSalvarData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InformacoesActivity.this, "Funcionandoo....", Toast.LENGTH_SHORT).show();
                sheetDialog.dismiss();
            }
        });
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();

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
            usuario.setAltura(user.getAltura());
            usuario.setData(user.getData());
            usuario.setImcMaior(user.getImcMaior());
            usuario.setImc(user.getImc());
            usuario.setGorduraPorcentage(user.getGorduraPorcentage());
            usuario.setGorduraKG(user.getGorduraKG());
            usuario.setSexo(user.getSexo());
            usuario.setMudanca(user.getMudanca());

        }


        if(usuario.getNome() != null && usuario.getAltura() != 0.0 && usuario.getPesoInicial() != 0.0 && usuario.getNascimento() !=0){

            campNome.setText(""+usuario.getNome());
            campoaltura.setText(""+usuario.getAltura());
            campoPeso.setText(""+usuario.getPesoInicial());
            campNascimento.setText(""+usuario.getNascimento());
        }



    }
    public void salvarDadosPerfil( View view){
        String nome = campNome.getText().toString();
        int nascimento = Integer.parseInt(campNascimento.getText().toString());// erro se o campo tiver vazio
        float peso = Float.parseFloat(campoPeso.getText().toString());
        float altura = Float.parseFloat(campoaltura.getText().toString());


            DaoControle dao = new DaoControle(getApplicationContext());
        final Usuario novousuario = new Usuario();

        novousuario.setId(usuario.getId());
        novousuario.setSexo(usuario.getSexo());
        novousuario.setNome(nome);
        novousuario.setNascimento(nascimento);
        novousuario.setPesoInicial(peso);
        novousuario.setAltura(altura);





        if(novousuario.getId() ==null) {



            if(dao.salvar(novousuario)){
                startActivity( new Intent(InformacoesActivity.this,HomeActivity.class));

                Toast.makeText(this, "Dados Salvo", Toast.LENGTH_SHORT).show();
                    finish();
            }else{
                Toast.makeText(this, "Erro em salvar", Toast.LENGTH_SHORT).show();
            }
        }
         else if(!nome.isEmpty() && nascimento !=0 && peso !=0.0 && altura != 0.0 && sexo != null){

            if(dao.atualizar(novousuario)){
                startActivity( new Intent(InformacoesActivity.this,HomeActivity.class));

                finish();

                Toast.makeText(this, "Dados Atualizados"+usuario.getPesoInicial(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show();
            }
        }

                Toast.makeText(this, "======"+novousuario.getSexo(), Toast.LENGTH_SHORT).show();
    }








    @Override
    protected void onStart() {
        super.onStart();
recuperarDados();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recuperarDados();
    }
}