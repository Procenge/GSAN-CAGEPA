/**
 * 
 */

package gcom.cadastro.aguaparatodos;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import gcom.cadastro.imovel.Imovel;
import gcom.interceptor.ObjetoGcom;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FaturamentoHistoricoAguaParaTodos
				extends ObjetoGcom {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Imovel imovel;

	private Date dataHabilitacao;

	private Integer anoMesReferencia;

	private Integer tarifa;

	private BigDecimal consumoMes;

	private BigDecimal valorConsumo;

	/**
	 * 
	 */
	public FaturamentoHistoricoAguaParaTodos() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Filtro retornaFiltro(){

		FiltroFaturamentoHistoricoAguaParaTodos filtroFaturamentoHistoricoAguaParaTodos = new FiltroFaturamentoHistoricoAguaParaTodos();

		filtroFaturamentoHistoricoAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroFaturamentoHistoricoAguaParaTodos.ID, this
						.getId()));

		return filtroFaturamentoHistoricoAguaParaTodos;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataHabilitacao(){

		return dataHabilitacao;
	}

	public void setDataHabilitacao(Date dataHabilitacao){

		this.dataHabilitacao = dataHabilitacao;
	}

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public String getAnoMesReferenciaFormatado(){

		return formataAnoMes(anoMesReferencia);
	}

	private String formataAnoMes(Integer anoMes){

		String resultado = anoMes.toString();
		resultado = resultado.substring(4) + "/" + resultado.substring(0, 4);
		return resultado;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Integer getTarifa(){

		return tarifa;
	}

	public void setTarifa(Integer tarifa){

		this.tarifa = tarifa;
	}

	public BigDecimal getConsumoMes(){

		return consumoMes;
	}

	public void setConsumoMes(BigDecimal consumoMes){

		this.consumoMes = consumoMes;
	}

	public BigDecimal getValorConsumo(){

		return valorConsumo;
	}

	public void setValorConsumo(BigDecimal valorConsumo){

		this.valorConsumo = valorConsumo;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

}
