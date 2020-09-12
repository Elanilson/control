package com.elanilsondejesus.com.controledopeso.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.elanilsondejesus.com.controledopeso.R;
import com.elanilsondejesus.com.controledopeso.adapter.AdapterHistorico;
import com.elanilsondejesus.com.controledopeso.helper.DaoHistorico;
import com.elanilsondejesus.com.controledopeso.model.HistoricoItem;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterHistorico adapter;
    private HistoricoItem item;
    private List<HistoricoItem> itens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        recuperarDados();
        iniciarComponentes();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterHistorico(itens);
        recyclerView.setAdapter(adapter);

    }
    public void iniciarComponentes(){
        recyclerView = findViewById(R.id.recyclerviewHistorico);

    }
    public void recuperarDados(){
        DaoHistorico  dao = new DaoHistorico(getApplicationContext());
       for(HistoricoItem ht: dao.listar()){
           itens.add(ht);
       }
    }
}