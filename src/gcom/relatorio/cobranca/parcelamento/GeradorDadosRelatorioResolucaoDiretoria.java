
package gcom.relatorio.cobranca.parcelamento;

import gcom.cobranca.parcelamento.Parcelamento;
import gcom.tarefa.ParametroTarefa;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Essa classe representa um gerador de dados para o relatório de resolucao diretoria
 * 
 * @author Cinthya
 */
public abstract class GeradorDadosRelatorioResolucaoDiretoria {

	@SuppressWarnings("unchecked")
	private Set parametroTarefa = new HashSet();

	/**
	 * @param parcelamento
	 * @return Uma coleção com os dados para o relatório
	 * @throws GeradorRelatorioParcelamentoException
	 */
	public abstract List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> gerarDados(Parcelamento parcelamento,
					int idFuncionalidadeIniciada) throws GeradorRelatorioParcelamentoException;

	/**
	 * 2 ou mais parcelamento por página
	 * 
	 * @author Cinthya
	 * @param colecaoParcelamento
	 * @return Uma coleção com os dados para o relatório
	 * @throws GeradorRelatorioParcelamentoException
	 */
	public abstract List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> gerarDados(Collection<Parcelamento> colecaoParcelamento,
					int idFuncionalidadeIniciada) throws GeradorRelatorioParcelamentoException;

	@SuppressWarnings("unchecked")
	public Set getParametroTarefa(){

		return parametroTarefa;

	}

	@SuppressWarnings("unchecked")
	public void setParametroTarefa(Set parametroTarefa){

		this.parametroTarefa = parametroTarefa;
	}

	/**
	 * Método que adiciona um Short
	 * 
	 * @param nome
	 * @param valor
	 */
	@SuppressWarnings("unchecked")
	public void addParametro(String nome, Object valor){

		parametroTarefa.add(new ParametroTarefa(nome, valor));
	}

	/**
	 * @author isilva
	 * @date 15/09/2010
	 * @param nome
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getParametro(String nome){

		Object retorno = null;
		Iterator it = parametroTarefa.iterator();
		while(it.hasNext()){
			ParametroTarefa parametroTarefa = (ParametroTarefa) it.next();
			if(nome != null && nome.equals(parametroTarefa.getNome())){
				retorno = parametroTarefa.getValor();
			}
		}
		return retorno;
	}

}
