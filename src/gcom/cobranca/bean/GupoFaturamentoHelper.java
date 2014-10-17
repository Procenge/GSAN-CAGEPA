
package gcom.cobranca.bean;

import java.io.Serializable;

/**
 * [UC3055] Encerrar Faturamento
 * 
 * @author Hebert Falcão
 * @date 01/04/2012
 */
public class GupoFaturamentoHelper
				implements Serializable {

	private static final long serialVersionUID = -6642202957811019727L;

	private String id;

	private String descricao;

	private String referencia;

	private String situacao;

	private String dataUltimoFaturamento;

	private String qtRotasNaoFaturadas;

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getDataUltimoFaturamento(){

		return dataUltimoFaturamento;
	}

	public void setDataUltimoFaturamento(String dataUltimoFaturamento){

		this.dataUltimoFaturamento = dataUltimoFaturamento;
	}

	public String getQtRotasNaoFaturadas(){

		return qtRotasNaoFaturadas;
	}

	public void setQtRotasNaoFaturadas(String qtRotasNaoFaturadas){

		this.qtRotasNaoFaturadas = qtRotasNaoFaturadas;
	}

}
