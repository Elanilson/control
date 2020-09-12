package com.elanilsondejesus.com.controledopeso.adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elanilsondejesus.com.controledopeso.R;
import com.elanilsondejesus.com.controledopeso.model.HistoricoItem;

import java.util.List;

public class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.MyViewHolder> {
        List<HistoricoItem>  itens;


    public AdapterHistorico(List<HistoricoItem> itens) {
        this.itens = itens;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lista= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico,parent,false);


        return new MyViewHolder(lista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HistoricoItem itemHistorico = itens.get(position);
        String item = itemHistorico.getMudanca();
        if(item.contains("+")){
               holder.mudanca.setTextColor(Color.rgb(34,139,34));

        }else{
           // holder.mudanca.setTextColor(Color.RED);
            holder.mudanca.setTextColor(Color.rgb(128,0,0));

        }
        holder.data.setText(itemHistorico.getData());
        holder.mudanca.setText(itemHistorico.getMudanca()+" Kg");
        holder.peso.setText(itemHistorico.getPeso()+" Kg");
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView data, peso, mudanca;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.textViewDataHistorico);
            peso = itemView.findViewById(R.id.textViewkgHistorico);
            mudanca = itemView.findViewById(R.id.textViewmudancaHistorico);
        }
    }
}
