
package gcom.arrecadacao;

import gcom.cobranca.DocumentoTipo;

import java.io.Serializable;
import java.util.Date;

/**
 * Codigo Barras Layout
 * 
 * @author Hebert Falcão
 * @date 18/05/2012
 */
public class CodigoBarrasLayout
				implements Serializable {

	private Integer id;

	private Integer posicaoInicio;

	private Integer tamanho;

	private Date ultimaAlteracao;

	private Short indicadorCampoObrigatorio;

	private Concessionaria concessionaria;

	private DocumentoTipo documentoTipo;

	private CodigoBarrasCampos codigoBarrasCampos;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getPosicaoInicio(){

		return posicaoInicio;
	}

	public void setPosicaoInicio(Integer posicaoInicio){

		this.posicaoInicio = posicaoInicio;
	}

	public Integer getTamanho(){

		return tamanho;
	}

	public void setTamanho(Integer tamanho){

		this.tamanho = tamanho;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Short getIndicadorCampoObrigatorio(){

		return indicadorCampoObrigatorio;
	}

	public void setIndicadorCampoObrigatorio(Short indicadorCampoObrigatorio){

		this.indicadorCampoObrigatorio = indicadorCampoObrigatorio;
	}

	public Concessionaria getConcessionaria(){

		return concessionaria;
	}

	public void setConcessionaria(Concessionaria concessionaria){

		this.concessionaria = concessionaria;
	}

	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public CodigoBarrasCampos getCodigoBarrasCampos(){

		return codigoBarrasCampos;
	}

	public void setCodigoBarrasCampos(CodigoBarrasCampos codigoBarrasCampos){

		this.codigoBarrasCampos = codigoBarrasCampos;
	}

}
