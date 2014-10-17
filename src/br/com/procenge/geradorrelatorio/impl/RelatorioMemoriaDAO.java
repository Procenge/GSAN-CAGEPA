
package br.com.procenge.geradorrelatorio.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.procenge.geradorrelatorio.api.Relatorio;
import br.com.procenge.geradorrelatorio.api.RelatorioDAO;

public class RelatorioMemoriaDAO
				implements RelatorioDAO {

	private List<Relatorio> relatorios = new ArrayList<Relatorio>();

	public List<Relatorio> getRelatorios(){

		return relatorios;
	}

	public void setRelatorios(List<Relatorio> relatorios){

		this.relatorios = relatorios;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.com.procenge.geradorrelatorio.api.RelatorioDAO#obterRelatorioPorNome(java.lang.String)
	 */
	public Relatorio obterRelatorioPorNome(String nome){

		Relatorio relatorio = null;

		for(Relatorio relatorioIteracao : this.relatorios){
			String nomeArquivo = relatorioIteracao.getArquivo();
			nomeArquivo = nomeArquivo.replace("crystal/", "");
			if(nomeArquivo.equals(nome)){
				relatorio = relatorioIteracao;
				break;
			}
		}

		return relatorio;
	}

}
