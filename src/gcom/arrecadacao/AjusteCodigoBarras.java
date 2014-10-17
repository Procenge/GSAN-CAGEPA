
package gcom.arrecadacao;

import java.io.Serializable;
import java.util.Date;

public class AjusteCodigoBarras
				implements Serializable {

	private Integer idArrecadadorMovimentoItem;

	private String codigoBarrasAntigo;

	private String codigoBarrasNovo;

	private String descricaoOcorrenciaMovimento;

	private Integer indicadorAceitacaoRegistro;

	private Integer idPagamento;

	private Date ultimaAlteracao;

	private Integer idImovel;

	private Integer referencia;

	private Integer idCobrancaDocumento;

	public Integer getIdArrecadadorMovimentoItem(){

		return idArrecadadorMovimentoItem;
	}

	public void setIdArrecadadorMovimentoItem(Integer idArrecadadorMovimentoItem){

		this.idArrecadadorMovimentoItem = idArrecadadorMovimentoItem;
	}

	public String getCodigoBarrasAntigo(){

		return codigoBarrasAntigo;
	}

	public void setCodigoBarrasAntigo(String codigoBarrasAntigo){

		this.codigoBarrasAntigo = codigoBarrasAntigo;
	}

	public String getCodigoBarrasNovo(){

		return codigoBarrasNovo;
	}

	public void setCodigoBarrasNovo(String codigoBarrasNovo){

		this.codigoBarrasNovo = codigoBarrasNovo;
	}

	public String getDescricaoOcorrenciaMovimento(){

		return descricaoOcorrenciaMovimento;
	}

	public void setDescricaoOcorrenciaMovimento(String descricaoOcorrenciaMovimento){

		this.descricaoOcorrenciaMovimento = descricaoOcorrenciaMovimento;
	}

	public Integer getIndicadorAceitacaoRegistro(){

		return indicadorAceitacaoRegistro;
	}

	public void setIndicadorAceitacaoRegistro(Integer indicadorAceitacaoRegistro){

		this.indicadorAceitacaoRegistro = indicadorAceitacaoRegistro;
	}

	public Integer getIdPagamento(){

		return idPagamento;
	}

	public void setIdPagamento(Integer idPagamento){

		this.idPagamento = idPagamento;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getReferencia(){

		return referencia;
	}

	public void setReferencia(Integer referencia){

		this.referencia = referencia;
	}

	public Integer getIdCobrancaDocumento(){

		return idCobrancaDocumento;
	}

	public void setIdCobrancaDocumento(Integer idCobrancaDocumento){

		this.idCobrancaDocumento = idCobrancaDocumento;
	}

}
