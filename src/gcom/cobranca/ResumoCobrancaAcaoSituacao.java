
package gcom.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hugo Lima
 */
public class ResumoCobrancaAcaoSituacao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date ultimaAlteracao;

	private Integer quantidadeDocumentos;

	private BigDecimal valorDocumentos;

	private ResumoCobrancaAcaoEventual resumoCobrancaAcaoEventual;

	private DocumentoTipo documentoTipo;

	private CobrancaDebitoSituacao cobrancaDebitoSituacao;

	public ResumoCobrancaAcaoSituacao() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getQuantidadeDocumentos(){

		return quantidadeDocumentos;
	}

	public void setQuantidadeDocumentos(Integer quantidadeDocumentos){

		this.quantidadeDocumentos = quantidadeDocumentos;
	}

	public BigDecimal getValorDocumentos(){

		return valorDocumentos;
	}

	public void setValorDocumentos(BigDecimal valorDocumentos){

		this.valorDocumentos = valorDocumentos;
	}

	public ResumoCobrancaAcaoEventual getResumoCobrancaAcaoEventual(){

		return resumoCobrancaAcaoEventual;
	}

	public void setResumoCobrancaAcaoEventual(ResumoCobrancaAcaoEventual resumoCobrancaAcaoEventual){

		this.resumoCobrancaAcaoEventual = resumoCobrancaAcaoEventual;
	}

	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public CobrancaDebitoSituacao getCobrancaDebitoSituacao(){

		return cobrancaDebitoSituacao;
	}

	public void setCobrancaDebitoSituacao(CobrancaDebitoSituacao cobrancaDebitoSituacao){

		this.cobrancaDebitoSituacao = cobrancaDebitoSituacao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;

		ResumoCobrancaAcaoSituacao other = (ResumoCobrancaAcaoSituacao) obj;

		// cobrancaDebitoSituacao.id
		if(cobrancaDebitoSituacao == null || cobrancaDebitoSituacao.getId() == null){
			if(other.cobrancaDebitoSituacao != null && other.cobrancaDebitoSituacao.getId() != null) return false;
		}else if(other.cobrancaDebitoSituacao == null || other.cobrancaDebitoSituacao.getId() == null
						|| !cobrancaDebitoSituacao.getId().equals(other.cobrancaDebitoSituacao.getId())) return false;

		// resumoCobrancaAcaoEventual.id
		if(resumoCobrancaAcaoEventual == null){
			if(other.resumoCobrancaAcaoEventual != null) return false;
		}else if(other.resumoCobrancaAcaoEventual == null || !resumoCobrancaAcaoEventual.equals(other.resumoCobrancaAcaoEventual)) return false;

		// documentoTipo.id
		if(documentoTipo == null || documentoTipo.getId() == null){
			if(other.documentoTipo != null && other.documentoTipo.getId() != null) return false;
		}else if(other.documentoTipo == null || other.documentoTipo.getId() == null
						|| !documentoTipo.getId().equals(other.documentoTipo.getId())) return false;

		return true;
	}

}
