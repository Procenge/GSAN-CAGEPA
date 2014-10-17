package gcom.integracao.piramide;

import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Date;


public class TabelaIntegracaoConslDiaNfcl {

	private Integer codigoSequenciaOrigem;

	private String codigoFilialOrigem;

	private String codigoSistemaOrigem;

	private String codigoModelo;

	private String codigoMunicipioOrigem;

	private String codigoSerieOrigem;

	private String codigoSubSerieOrigem;

	private String codigoClasseConsumo;

	private Integer qtdDocumentosConsolidados;

	private Integer qtdDocumentosCancelados;

	private Date dataDocumentosConsolidado;

	private BigDecimal valorTotalDocumentos;

	private BigDecimal valorAcumuladoDescontos;

	private BigDecimal valorConsumoAcumulado;

	private BigDecimal valorAcumuladoFornecimento;

	private BigDecimal valorServicoNaoTributados;

	private BigDecimal valorCobradoTerceiros;

	private BigDecimal valorDespesasAcessoria;

	private BigDecimal valorBaseIcms;

	private BigDecimal valorIcms;

	private BigDecimal valorBaseIcmsSubstituicaoTributaria;

	private BigDecimal valorIcmsRetidoSubstituicaoTributaria;

	private BigDecimal valorAcumuladoPisPasep;

	private BigDecimal valorAcumuladoCofins;

	private String codigoOperacaoRegistro;

	private String codigoStatusRegistro;

	private String descricaoErroRegistro;

	public TabelaIntegracaoConslDiaNfcl(Integer codigoSequenciaOrigem, String codigoFilialOrigem, String codigoSistemaOrigem, String codigoModelo,
								String codigoMunicipioOrigem, String codigoSerieOrigem, String codigoSubSerieOrigem,
								String codigoClasseConsumo, Integer qtdDocumentosConsolidados, Integer qtdDocumentosCancelados,
								Date dataDocumentosConsolidado, BigDecimal valorTotalDocumentos, BigDecimal valorAcumuladoDescontos,
								BigDecimal valorConsumoAcumulado, BigDecimal valorAcumuladoFornecimento,
								BigDecimal valorServicoNaoTributados,
								BigDecimal valorCobradoTerceiros, BigDecimal valorDespesasAcessoria, BigDecimal valorBaseIcms,
								BigDecimal valorIcms, BigDecimal valorBaseIcmsSubstituicaoTributaria,
								BigDecimal valorIcmsRetidoSubstituicaoTributaria, BigDecimal valorAcumuladoPisPasep,
										BigDecimal valorAcumuladoCofins, String codigoOperacaoRegistro, String codigoStatusRegistro,
								String descricaoErroRegistro) {

		this();
		this.codigoSequenciaOrigem = codigoSequenciaOrigem;
		this.codigoFilialOrigem = codigoFilialOrigem;
		this.codigoSistemaOrigem = codigoSistemaOrigem;
		this.codigoModelo = codigoModelo;
		this.codigoMunicipioOrigem = codigoMunicipioOrigem;
		this.codigoSerieOrigem = codigoSerieOrigem;
		this.codigoSubSerieOrigem = codigoSubSerieOrigem;
		this.codigoClasseConsumo = codigoClasseConsumo;
		this.qtdDocumentosConsolidados = qtdDocumentosConsolidados;
		this.qtdDocumentosCancelados = qtdDocumentosCancelados;
		this.dataDocumentosConsolidado = dataDocumentosConsolidado;
		this.valorTotalDocumentos = valorTotalDocumentos;
		this.valorAcumuladoDescontos = valorAcumuladoDescontos;
		this.valorConsumoAcumulado = valorConsumoAcumulado;
		this.valorAcumuladoFornecimento = valorAcumuladoFornecimento;
		this.valorServicoNaoTributados = valorServicoNaoTributados;
		this.valorCobradoTerceiros = valorCobradoTerceiros;
		this.valorDespesasAcessoria = valorDespesasAcessoria;
		this.valorBaseIcms = valorBaseIcms;
		this.valorIcms = valorIcms;
		this.valorBaseIcmsSubstituicaoTributaria = valorBaseIcmsSubstituicaoTributaria;
		this.valorIcmsRetidoSubstituicaoTributaria = valorIcmsRetidoSubstituicaoTributaria;
		this.valorAcumuladoPisPasep = valorAcumuladoPisPasep;
		this.valorAcumuladoCofins = valorAcumuladoCofins;
		this.codigoOperacaoRegistro = codigoOperacaoRegistro;
		this.codigoStatusRegistro = codigoStatusRegistro;
		this.descricaoErroRegistro = descricaoErroRegistro;
	}

	/**
	 * TabelaIntegracaoConslDiaNfcl
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoConslDiaNfcl.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 14/03/2013
	 */
	public TabelaIntegracaoConslDiaNfcl() {

		super();
	}

	public Integer getCodigoSequenciaOrigem(){

		return codigoSequenciaOrigem;
	}

	public void setCodigoSequenciaOrigem(Integer codigoSequenciaOrigem){

		this.codigoSequenciaOrigem = codigoSequenciaOrigem;
	}

	public String getCodigoFilialOrigem(){

		return codigoFilialOrigem;
	}

	public void setCodigoFilialOrigem(String codigoFilialOrigem){

		this.codigoFilialOrigem = codigoFilialOrigem;
	}

	public String getCodigoSistemaOrigem(){

		return codigoSistemaOrigem;
	}

	public void setCodigoSistemaOrigem(String codigoSistemaOrigem){

		this.codigoSistemaOrigem = codigoSistemaOrigem;
	}

	public String getCodigoModelo(){

		return codigoModelo;
	}

	public void setCodigoModelo(String codigoModelo){

		this.codigoModelo = codigoModelo;
	}

	public String getCodigoMunicipioOrigem(){

		return codigoMunicipioOrigem;
	}

	public void setCodigoMunicipioOrigem(String codigoMunicipioOrigem){

		this.codigoMunicipioOrigem = codigoMunicipioOrigem;
	}

	public String getCodigoSerieOrigem(){

		return codigoSerieOrigem;
	}

	public void setCodigoSerieOrigem(String codigoSerieOrigem){

		this.codigoSerieOrigem = codigoSerieOrigem;
	}

	public String getCodigoSubSerieOrigem(){

		return codigoSubSerieOrigem;
	}

	public void setCodigoSubSerieOrigem(String codigoSubSerieOrigem){

		this.codigoSubSerieOrigem = codigoSubSerieOrigem;
	}

	public String getCodigoClasseConsumo(){

		return codigoClasseConsumo;
	}

	public void setCodigoClasseConsumo(String codigoClasseConsumo){

		this.codigoClasseConsumo = codigoClasseConsumo;
	}

	public Integer getQtdDocumentosConsolidados(){

		return qtdDocumentosConsolidados;
	}

	public void setQtdDocumentosConsolidados(Integer qtdDocumentosConsolidados){

		this.qtdDocumentosConsolidados = qtdDocumentosConsolidados;
	}

	public Integer getQtdDocumentosCancelados(){

		return qtdDocumentosCancelados;
	}

	public void setQtdDocumentosCancelados(Integer qtdDocumentosCancelados){

		this.qtdDocumentosCancelados = qtdDocumentosCancelados;
	}

	public Date getDataDocumentosConsolidado(){

		return dataDocumentosConsolidado;
	}

	public void setDataDocumentosConsolidado(Date dataDocumentosConsolidado){

		this.dataDocumentosConsolidado = dataDocumentosConsolidado;
	}

	public BigDecimal getValorTotalDocumentos(){

		return valorTotalDocumentos;
	}

	public void setValorTotalDocumentos(BigDecimal valorTotalDocumentos){

		this.valorTotalDocumentos = valorTotalDocumentos;
	}

	public BigDecimal getValorAcumuladoDescontos(){

		return valorAcumuladoDescontos;
	}

	public void setValorAcumuladoDescontos(BigDecimal valorAcumuladoDescontos){

		this.valorAcumuladoDescontos = valorAcumuladoDescontos;
	}

	public BigDecimal getValorConsumoAcumulado(){

		return valorConsumoAcumulado;
	}

	public void setValorConsumoAcumulado(BigDecimal valorConsumoAcumulado){

		this.valorConsumoAcumulado = valorConsumoAcumulado;
	}

	public BigDecimal getValorAcumuladoFornecimento(){

		return valorAcumuladoFornecimento;
	}

	public void setValorAcumuladoFornecimento(BigDecimal valorAcumuladoFornecimento){

		this.valorAcumuladoFornecimento = valorAcumuladoFornecimento;
	}

	public BigDecimal getValorServicoNaoTributados(){

		return valorServicoNaoTributados;
	}

	public void setValorServicoNaoTributados(BigDecimal valorServicoNaoTributados){

		this.valorServicoNaoTributados = valorServicoNaoTributados;
	}

	public BigDecimal getValorCobradoTerceiros(){

		return valorCobradoTerceiros;
	}

	public void setValorCobradoTerceiros(BigDecimal valorCobradoTerceiros){

		this.valorCobradoTerceiros = valorCobradoTerceiros;
	}

	public BigDecimal getValorDespesasAcessoria(){

		return valorDespesasAcessoria;
	}

	public void setValorDespesasAcessoria(BigDecimal valorDespesasAcessoria){

		this.valorDespesasAcessoria = valorDespesasAcessoria;
	}

	public BigDecimal getValorBaseIcms(){

		return valorBaseIcms;
	}

	public void setValorBaseIcms(BigDecimal valorBaseIcms){

		this.valorBaseIcms = valorBaseIcms;
	}

	public BigDecimal getValorIcms(){

		return valorIcms;
	}

	public void setValorIcms(BigDecimal valorIcms){

		this.valorIcms = valorIcms;
	}

	public BigDecimal getValorBaseIcmsSubstituicaoTributaria(){

		return valorBaseIcmsSubstituicaoTributaria;
	}

	public void setValorBaseIcmsSubstituicaoTributaria(BigDecimal valorBaseIcmsSubstituicaoTributaria){

		this.valorBaseIcmsSubstituicaoTributaria = valorBaseIcmsSubstituicaoTributaria;
	}

	public BigDecimal getValorIcmsRetidoSubstituicaoTributaria(){

		return valorIcmsRetidoSubstituicaoTributaria;
	}

	public void setValorIcmsRetidoSubstituicaoTributaria(BigDecimal valorIcmsRetidoSubstituicaoTributaria){

		this.valorIcmsRetidoSubstituicaoTributaria = valorIcmsRetidoSubstituicaoTributaria;
	}

	public BigDecimal getValorAcumuladoPisPasep(){

		return valorAcumuladoPisPasep;
	}

	public void setValorAcumuladoPisPasep(BigDecimal valorAcumuladoPisPasep){

		this.valorAcumuladoPisPasep = valorAcumuladoPisPasep;
	}

	public String getCodigoOperacaoRegistro(){

		return codigoOperacaoRegistro;
	}

	public void setCodigoOperacaoRegistro(String codigoOperacaoRegistro){

		this.codigoOperacaoRegistro = codigoOperacaoRegistro;
	}

	public String getCodigoStatusRegistro(){

		return codigoStatusRegistro;
	}

	public void setCodigoStatusRegistro(String codigoStatusRegistro){

		this.codigoStatusRegistro = codigoStatusRegistro;
	}

	public String getDescricaoErroRegistro(){

		return descricaoErroRegistro;
	}

	public void setDescricaoErroRegistro(String descricaoErroRegistro){

		this.descricaoErroRegistro = descricaoErroRegistro;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoClasseConsumo == null) ? 0 : codigoClasseConsumo.hashCode());
		result = prime * result + ((codigoFilialOrigem == null) ? 0 : codigoFilialOrigem.hashCode());
		result = prime * result + ((codigoModelo == null) ? 0 : codigoModelo.hashCode());
		result = prime * result + ((codigoMunicipioOrigem == null) ? 0 : codigoMunicipioOrigem.hashCode());
		result = prime * result + ((codigoOperacaoRegistro == null) ? 0 : codigoOperacaoRegistro.hashCode());
		result = prime * result + ((codigoSequenciaOrigem == null) ? 0 : codigoSequenciaOrigem.hashCode());
		result = prime * result + ((codigoSerieOrigem == null) ? 0 : codigoSerieOrigem.hashCode());
		result = prime * result + ((codigoSistemaOrigem == null) ? 0 : codigoSistemaOrigem.hashCode());
		result = prime * result + ((codigoStatusRegistro == null) ? 0 : codigoStatusRegistro.hashCode());
		result = prime * result + ((codigoSubSerieOrigem == null) ? 0 : codigoSubSerieOrigem.hashCode());
		result = prime * result + ((dataDocumentosConsolidado == null) ? 0 : dataDocumentosConsolidado.hashCode());
		result = prime * result + ((descricaoErroRegistro == null) ? 0 : descricaoErroRegistro.hashCode());
		result = prime * result + ((qtdDocumentosCancelados == null) ? 0 : qtdDocumentosCancelados.hashCode());
		result = prime * result + ((qtdDocumentosConsolidados == null) ? 0 : qtdDocumentosConsolidados.hashCode());
		result = prime * result + ((valorAcumuladoCofins == null) ? 0 : valorAcumuladoCofins.hashCode());
		result = prime * result + ((valorAcumuladoDescontos == null) ? 0 : valorAcumuladoDescontos.hashCode());
		result = prime * result + ((valorAcumuladoFornecimento == null) ? 0 : valorAcumuladoFornecimento.hashCode());
		result = prime * result + ((valorAcumuladoPisPasep == null) ? 0 : valorAcumuladoPisPasep.hashCode());
		result = prime * result + ((valorBaseIcms == null) ? 0 : valorBaseIcms.hashCode());
		result = prime * result + ((valorBaseIcmsSubstituicaoTributaria == null) ? 0 : valorBaseIcmsSubstituicaoTributaria.hashCode());
		result = prime * result + ((valorCobradoTerceiros == null) ? 0 : valorCobradoTerceiros.hashCode());
		result = prime * result + ((valorConsumoAcumulado == null) ? 0 : valorConsumoAcumulado.hashCode());
		result = prime * result + ((valorDespesasAcessoria == null) ? 0 : valorDespesasAcessoria.hashCode());
		result = prime * result + ((valorIcms == null) ? 0 : valorIcms.hashCode());
		result = prime * result + ((valorIcmsRetidoSubstituicaoTributaria == null) ? 0 : valorIcmsRetidoSubstituicaoTributaria.hashCode());
		result = prime * result + ((valorServicoNaoTributados == null) ? 0 : valorServicoNaoTributados.hashCode());
		result = prime * result + ((valorTotalDocumentos == null) ? 0 : valorTotalDocumentos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoConslDiaNfcl other = (TabelaIntegracaoConslDiaNfcl) obj;
		if(codigoClasseConsumo == null){
			if(other.codigoClasseConsumo != null) return false;
		}else if(!codigoClasseConsumo.equals(other.codigoClasseConsumo)) return false;
		if(codigoFilialOrigem == null){
			if(other.codigoFilialOrigem != null) return false;
		}else if(!codigoFilialOrigem.equals(other.codigoFilialOrigem)) return false;
		if(codigoModelo == null){
			if(other.codigoModelo != null) return false;
		}else if(!codigoModelo.equals(other.codigoModelo)) return false;
		if(codigoMunicipioOrigem == null){
			if(other.codigoMunicipioOrigem != null) return false;
		}else if(!codigoMunicipioOrigem.equals(other.codigoMunicipioOrigem)) return false;
		if(codigoOperacaoRegistro == null){
			if(other.codigoOperacaoRegistro != null) return false;
		}else if(!codigoOperacaoRegistro.equals(other.codigoOperacaoRegistro)) return false;
		if(codigoSequenciaOrigem == null){
			if(other.codigoSequenciaOrigem != null) return false;
		}else if(!codigoSequenciaOrigem.equals(other.codigoSequenciaOrigem)) return false;
		if(codigoSerieOrigem == null){
			if(other.codigoSerieOrigem != null) return false;
		}else if(!codigoSerieOrigem.equals(other.codigoSerieOrigem)) return false;
		if(codigoSistemaOrigem == null){
			if(other.codigoSistemaOrigem != null) return false;
		}else if(!codigoSistemaOrigem.equals(other.codigoSistemaOrigem)) return false;
		if(codigoStatusRegistro == null){
			if(other.codigoStatusRegistro != null) return false;
		}else if(!codigoStatusRegistro.equals(other.codigoStatusRegistro)) return false;
		if(codigoSubSerieOrigem == null){
			if(other.codigoSubSerieOrigem != null) return false;
		}else if(!codigoSubSerieOrigem.equals(other.codigoSubSerieOrigem)) return false;
		if(dataDocumentosConsolidado == null){
			if(other.dataDocumentosConsolidado != null) return false;
		}else if(!dataDocumentosConsolidado.equals(other.dataDocumentosConsolidado)) return false;
		if(descricaoErroRegistro == null){
			if(other.descricaoErroRegistro != null) return false;
		}else if(!descricaoErroRegistro.equals(other.descricaoErroRegistro)) return false;
		if(qtdDocumentosCancelados == null){
			if(other.qtdDocumentosCancelados != null) return false;
		}else if(!qtdDocumentosCancelados.equals(other.qtdDocumentosCancelados)) return false;
		if(qtdDocumentosConsolidados == null){
			if(other.qtdDocumentosConsolidados != null) return false;
		}else if(!qtdDocumentosConsolidados.equals(other.qtdDocumentosConsolidados)) return false;
		if(valorAcumuladoCofins == null){
			if(other.valorAcumuladoCofins != null) return false;
		}else if(!valorAcumuladoCofins.equals(other.valorAcumuladoCofins)) return false;
		if(valorAcumuladoDescontos == null){
			if(other.valorAcumuladoDescontos != null) return false;
		}else if(!valorAcumuladoDescontos.equals(other.valorAcumuladoDescontos)) return false;
		if(valorAcumuladoFornecimento == null){
			if(other.valorAcumuladoFornecimento != null) return false;
		}else if(!valorAcumuladoFornecimento.equals(other.valorAcumuladoFornecimento)) return false;
		if(valorAcumuladoPisPasep == null){
			if(other.valorAcumuladoPisPasep != null) return false;
		}else if(!valorAcumuladoPisPasep.equals(other.valorAcumuladoPisPasep)) return false;
		if(valorBaseIcms == null){
			if(other.valorBaseIcms != null) return false;
		}else if(!valorBaseIcms.equals(other.valorBaseIcms)) return false;
		if(valorBaseIcmsSubstituicaoTributaria == null){
			if(other.valorBaseIcmsSubstituicaoTributaria != null) return false;
		}else if(!valorBaseIcmsSubstituicaoTributaria.equals(other.valorBaseIcmsSubstituicaoTributaria)) return false;
		if(valorCobradoTerceiros == null){
			if(other.valorCobradoTerceiros != null) return false;
		}else if(!valorCobradoTerceiros.equals(other.valorCobradoTerceiros)) return false;
		if(valorConsumoAcumulado == null){
			if(other.valorConsumoAcumulado != null) return false;
		}else if(!valorConsumoAcumulado.equals(other.valorConsumoAcumulado)) return false;
		if(valorDespesasAcessoria == null){
			if(other.valorDespesasAcessoria != null) return false;
		}else if(!valorDespesasAcessoria.equals(other.valorDespesasAcessoria)) return false;
		if(valorIcms == null){
			if(other.valorIcms != null) return false;
		}else if(!valorIcms.equals(other.valorIcms)) return false;
		if(valorIcmsRetidoSubstituicaoTributaria == null){
			if(other.valorIcmsRetidoSubstituicaoTributaria != null) return false;
		}else if(!valorIcmsRetidoSubstituicaoTributaria.equals(other.valorIcmsRetidoSubstituicaoTributaria)) return false;
		if(valorServicoNaoTributados == null){
			if(other.valorServicoNaoTributados != null) return false;
		}else if(!valorServicoNaoTributados.equals(other.valorServicoNaoTributados)) return false;
		if(valorTotalDocumentos == null){
			if(other.valorTotalDocumentos != null) return false;
		}else if(!valorTotalDocumentos.equals(other.valorTotalDocumentos)) return false;
		return true;
	}

	@Override
	public String toString(){

		return "TI_CONSL_DIA_NFCL [codigoSequenciaOrigem=" + codigoSequenciaOrigem + ", codigoFilialOrigem=" + codigoFilialOrigem
						+ ", codigoSistemaOrigem=" + codigoSistemaOrigem + ", codigoModelo=" + codigoModelo + ", codigoMunicipioOrigem="
						+ codigoMunicipioOrigem + ", codigoSerieOrigem=" + codigoSerieOrigem + ", codigoSubSerieOrigem="
						+ codigoSubSerieOrigem + ", codigoClasseConsumo=" + codigoClasseConsumo + ", qtdDocumentosConsolidados="
						+ qtdDocumentosConsolidados + ", qtdDocumentosCancelados=" + qtdDocumentosCancelados
						+ ", dataDocumentosConsolidado=" + dataDocumentosConsolidado + ", valorTotalDocumentos=" + valorTotalDocumentos
						+ ", valorAcumuladoDescontos=" + valorAcumuladoDescontos + ", valorConsumoAcumulado=" + valorConsumoAcumulado
						+ ", valorAcumuladoFornecimento=" + valorAcumuladoFornecimento + ", valorServicoNaoTributados="
						+ valorServicoNaoTributados + ", valorCobradoTerceiros=" + valorCobradoTerceiros + ", valorDespesasAcessoria="
						+ valorDespesasAcessoria + ", valorBaseIcms=" + valorBaseIcms + ", valorIcms=" + valorIcms
						+ ", valorBaseIcmsSubstituicaoTributaria=" + valorBaseIcmsSubstituicaoTributaria
						+ ", valorIcmsRetidoSubstituicaoTributaria=" + valorIcmsRetidoSubstituicaoTributaria + ", valorAcumuladoPisPasep="
						+ valorAcumuladoPisPasep + ", valorAcumuladoConfins=" + valorAcumuladoCofins + ", codigoOperacaoRegistro="
						+ codigoOperacaoRegistro + ", codigoStatusRegistro=" + codigoStatusRegistro + ", descricaoErroRegistro="
						+ descricaoErroRegistro + "]";
	}

	public BigDecimal getValorAcumuladoCofins(){

		return valorAcumuladoCofins;
	}

	public void setValorAcumuladoCofins(BigDecimal valorAcumuladoCofins){

		this.valorAcumuladoCofins = valorAcumuladoCofins;
	}

	/**
	 * Método addQtdDocumentosConsolidados
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param i
	 * @author Marlos Ribeiro
	 * @since 22/03/2013
	 */
	public void addQtdDocumentosConsolidados(int i){

		if(qtdDocumentosCancelados == null) qtdDocumentosCancelados = 0;
		qtdDocumentosCancelados += i;
	}

	public void formatarDadosEntidade(){

		// codigoFilialOrigem = Util.completarStringZeroEsquerda(codigoFilialOrigem, 3);
		codigoMunicipioOrigem = Util.completarStringZeroEsquerda(codigoMunicipioOrigem, 10);
		codigoClasseConsumo = Util.completarStringZeroEsquerda(codigoClasseConsumo, 2);

	}

}
