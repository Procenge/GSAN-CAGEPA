/**
 * 
 */

package gcom.gui.faturamento.conta;

import gcom.util.Util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author isilva
 * @date 03/05/2011
 */
public class FuncionarioContaHelp {

	private String inclusao = "";

	private String retificacao = "";

	private String cancelamento = "";

	private String revisao = "";

	public FuncionarioContaHelp() {

	}

	/**
	 * @return the inclusao
	 */
	public String getInclusao(){

		return inclusao;
	}

	/**
	 * @param inclusao
	 *            the inclusao to set
	 */
	public void setInclusao(String inclusao){

		this.inclusao = inclusao;
	}

	/**
	 * @return the retificacao
	 */
	public String getRetificacao(){

		return retificacao;
	}

	/**
	 * @param retificacao
	 *            the retificacao to set
	 */
	public void setRetificacao(String retificacao){

		this.retificacao = retificacao;
	}

	/**
	 * @return the cancelamento
	 */
	public String getCancelamento(){

		return cancelamento;
	}

	/**
	 * @param cancelamento
	 *            the cancelamento to set
	 */
	public void setCancelamento(String cancelamento){

		this.cancelamento = cancelamento;
	}

	/**
	 * @return the revisao
	 */
	public String getRevisao(){

		return revisao;
	}

	/**
	 * @param revisao
	 *            the revisao to set
	 */
	public void setRevisao(String revisao){

		this.revisao = revisao;
	}

	/**
	 * Obtem o nome do último funcionário que executou uma operação em conta.
	 * 
	 * @author isilva
	 * @param nomeFuncionario
	 * @param dataInclusao
	 * @param dataRetificacao
	 * @param dataCancelamento
	 * @param dataRevisao
	 */
	public void verificarFuncionarioUltimaOperacao(String nomeFuncionario, Date dataInclusao, Date dataRetificacao, Date dataCancelamento,
					Date dataRevisao){

		if(!Util.isVazioOuBranco(nomeFuncionario)){
			Date maiorData = null;

			Set<Date> listaDatas = new HashSet<Date>();

			if(dataInclusao != null){
				listaDatas.add(dataInclusao);
			}

			if(dataRetificacao != null){
				listaDatas.add(dataRetificacao);
			}

			if(dataCancelamento != null){
				listaDatas.add(dataCancelamento);
			}

			if(dataRevisao != null){
				listaDatas.add(dataRevisao);
			}

			if(!Util.isVazioOrNulo(listaDatas)){
				for(Date data : listaDatas){
					if(maiorData == null || data.compareTo(maiorData) > 0){
						maiorData = data;
					}
				}

				if(maiorData != null){
					if(dataInclusao != null && maiorData.compareTo(dataInclusao) == 0){
						this.setInclusao(nomeFuncionario);
					}

					if(dataRetificacao != null && maiorData.compareTo(dataRetificacao) == 0){
						this.setRetificacao(nomeFuncionario);
					}

					if(dataCancelamento != null && maiorData.compareTo(dataCancelamento) == 0){
						this.setCancelamento(nomeFuncionario);
					}

					if(dataRevisao != null && maiorData.compareTo(dataRevisao) == 0){
						this.setRevisao(nomeFuncionario);
					}
				}
			}
		}
	}
}
