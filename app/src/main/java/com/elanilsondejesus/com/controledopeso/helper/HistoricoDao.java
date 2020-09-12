package com.elanilsondejesus.com.controledopeso.helper;

import com.elanilsondejesus.com.controledopeso.model.HistoricoItem;

import java.util.List;

public interface HistoricoDao {
public Boolean salvar(HistoricoItem itemHistorico);
public Boolean atualizar(HistoricoItem itemHistorico);
public Boolean deletar(HistoricoItem itemHistorico);
public List<HistoricoItem> listar();

}
