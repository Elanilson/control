package com.elanilsondejesus.com.controledopeso.helper;

import com.elanilsondejesus.com.controledopeso.model.Progress;
import com.elanilsondejesus.com.controledopeso.model.Usuario;

import java.util.List;

public interface ProgressoDao {
public Boolean salvar(Progress progress);
public Boolean atualizar(Progress progress);
public Boolean deletar(Progress progress);
public List<Progress> listar( );
}
