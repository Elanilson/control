package com.elanilsondejesus.com.controledopeso.helper;

import com.elanilsondejesus.com.controledopeso.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface ControleDao {
public Boolean salvar(Usuario usuario);
public Boolean atualizar(Usuario usuario);
public Boolean deletar(Usuario usuario);
public List<Usuario> listar();

}
