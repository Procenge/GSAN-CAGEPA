
package gcom.cobranca.bean;

import java.io.Serializable;

public class FiltroRelatorioDebitoPorResponsavelHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idClienteInicial;

	private Integer idClienteFinal;

	private Integer idTipoClienteInicial;

	private Integer idTipoClienteFinal;

	private Integer referenciaDebitoInicial;

	private Integer referenciaDebitoFinal;

	private String indicadorTipoRelatorio;

	private String indicadorResponsabilidade;

	private Short indicadorContasEmRevisao;

	private Integer idMotivoRevisao;

	private Short indicadorValorCorrigido;

	private Integer idEsferaPoder;

	public FiltroRelatorioDebitoPorResponsavelHelper() {

	}

	public Integer getIdClienteInicial(){

		return idClienteInicial;
	}

	public void setIdClienteInicial(Integer idClienteInicial){

		this.idClienteInicial = idClienteInicial;
	}

	public Integer getIdClienteFinal(){

		return idClienteFinal;
	}

	public void setIdClienteFinal(Integer idClienteFinal){

		this.idClienteFinal = idClienteFinal;
	}

	public Integer getIdTipoClienteInicial(){

		return idTipoClienteInicial;
	}

	public void setIdTipoClienteInicial(Integer idTipoClienteInicial){

		this.idTipoClienteInicial = idTipoClienteInicial;
	}

	public Integer getIdTipoClienteFinal(){

		return idTipoClienteFinal;
	}

	public void setIdTipoClienteFinal(Integer idTipoClienteFinal){

		this.idTipoClienteFinal = idTipoClienteFinal;
	}

	public Integer getReferenciaDebitoInicial(){

		return referenciaDebitoInicial;
	}

	public void setReferenciaDebitoInicial(Integer referenciaDebitoInicial){

		this.referenciaDebitoInicial = referenciaDebitoInicial;
	}

	public Integer getReferenciaDebitoFinal(){

		return referenciaDebitoFinal;
	}

	public void setReferenciaDebitoFinal(Integer referenciaDebitoFinal){

		this.referenciaDebitoFinal = referenciaDebitoFinal;
	}

	public String getIndicadorTipoRelatorio(){

		return indicadorTipoRelatorio;
	}

	public void setIndicadorTipoRelatorio(String indicadorTipoRelatorio){

		this.indicadorTipoRelatorio = indicadorTipoRelatorio;
	}

	public String getIndicadorResponsabilidade(){

		return indicadorResponsabilidade;
	}

	public void setIndicadorResponsabilidade(String indicadorResponsabilidade){

		this.indicadorResponsabilidade = indicadorResponsabilidade;
	}

	public Integer getIdMotivoRevisao(){

		return idMotivoRevisao;
	}

	public void setIdMotivoRevisao(Integer idMotivoRevisao){

		this.idMotivoRevisao = idMotivoRevisao;
	}

	public Short getIndicadorContasEmRevisao(){

		return indicadorContasEmRevisao;
	}

	public void setIndicadorContasEmRevisao(Short indicadorContasEmRevisao){

		this.indicadorContasEmRevisao = indicadorContasEmRevisao;
	}

	public Short getIndicadorValorCorrigido(){

		return indicadorValorCorrigido;
	}

	public void setIndicadorValorCorrigido(Short indicadorValorCorrigido){

		this.indicadorValorCorrigido = indicadorValorCorrigido;
	}

	public Integer getIdEsferaPoder(){

		return idEsferaPoder;
	}

	public void setIdEsferaPoder(Integer idEsferaPoder){

		this.idEsferaPoder = idEsferaPoder;
	}

}
