
package gcom.relatorio.financeiro;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioSaldoContasAReceberContabilDetalheBean
				implements RelatorioBean {

	public RelatorioSaldoContasAReceberContabilDetalheBean(Integer idTipoLancamento, String descTipoLancamento) {

		super();
		this.idTipoLancamento = idTipoLancamento;
		this.descTipoLancamento = descTipoLancamento;

	}

	public RelatorioSaldoContasAReceberContabilDetalheBean(Integer idItemLancamento, String descItemLancamento,
															BigDecimal valorItemLancamentoCatPublica,
															BigDecimal valorItemLancamentoCatParticular, BigDecimal valorItemLancamento) {

		super();

		this.idItemLancamento = idItemLancamento;
		this.descItemLancamento = descItemLancamento;
		this.valorItemLancamentoCatPublica = valorItemLancamentoCatPublica;
		this.valorItemLancamentoCatParticular = valorItemLancamentoCatParticular;
		this.valorItemLancamento = valorItemLancamento;

	}

	private Integer numeroSequenciaTipoLancamento;

	private Integer numeroSequenciaItemTipoLancamento;

	private Integer idTipoLancamento;

	private String descTipoLancamento;

	private Integer idItemLancamento;

	private String descItemLancamento;

	private BigDecimal valorItemLancamentoCatPublica;

	private BigDecimal valorItemLancamentoCatParticular;

	private BigDecimal valorItemLancamento;

	private List<RelatorioSaldoContasAReceberContabilDetalheBean> dadosDetalhe;

	public Integer getNumeroSequenciaTipoLancamento(){

		return numeroSequenciaTipoLancamento;
	}

	public void setNumeroSequenciaTipoLancamento(Integer numeroSequenciaTipoLancamento){

		this.numeroSequenciaTipoLancamento = numeroSequenciaTipoLancamento;
	}

	public Integer getNumeroSequenciaItemTipoLancamento(){

		return numeroSequenciaItemTipoLancamento;
	}

	public void setNumeroSequenciaItemTipoLancamento(Integer numeroSequenciaItemTipoLancamento){

		this.numeroSequenciaItemTipoLancamento = numeroSequenciaItemTipoLancamento;
	}

	public BigDecimal getValorItemLancamentoCatPublica(){

		return valorItemLancamentoCatPublica;
	}

	public BigDecimal getValorItemLancamentoCatParticular(){

		return valorItemLancamentoCatParticular;
	}

	public BigDecimal getValorItemLancamento(){

		return valorItemLancamento;
	}

	public Integer getIdTipoLancamento(){

		return idTipoLancamento;
	}

	public void setIdTipoLancamento(Integer idTipoLancamento){

		this.idTipoLancamento = idTipoLancamento;
	}

	public String getDescTipoLancamento(){

		return descTipoLancamento;
	}

	public void setDescTipoLancamento(String descTipoLancamento){

		this.descTipoLancamento = descTipoLancamento;
	}

	public Integer getIdItemLancamento(){

		return idItemLancamento;
	}

	public void setIdItemLancamento(Integer idItemLancamento){

		this.idItemLancamento = idItemLancamento;
	}

	public String getDescItemLancamento(){

		return descItemLancamento;
	}

	public void setDescItemLancamento(String descItemLancamento){

		this.descItemLancamento = descItemLancamento;
	}

	public List<RelatorioSaldoContasAReceberContabilDetalheBean> getDadosDetalhe(){

		return dadosDetalhe;
	}

	public void addDetalhe(RelatorioSaldoContasAReceberContabilDetalheBean detalhe){

		if(dadosDetalhe != null){

			dadosDetalhe.add(detalhe);

		}else{

			dadosDetalhe = new ArrayList<RelatorioSaldoContasAReceberContabilDetalheBean>();
			dadosDetalhe.add(detalhe);

		}

		this.addValorItemLancamento(detalhe.getValorItemLancamento());
		this.addValorItemLancamentoCatParticular(detalhe.getValorItemLancamentoCatParticular());
		this.addValorItemLancamentoCatPublica(detalhe.getValorItemLancamentoCatPublica());

	}

	private void addValorItemLancamento(BigDecimal valor){

		if(valorItemLancamento != null){

			valorItemLancamento = valorItemLancamento.add(valor);

		}else{

			valorItemLancamento = valor;

		}

	}

	private void addValorItemLancamentoCatParticular(BigDecimal valor){

		if(valorItemLancamentoCatParticular != null){

			valorItemLancamentoCatParticular = valorItemLancamentoCatParticular.add(valor);

		}else{

			valorItemLancamentoCatParticular = valor;

		}

	}

	private void addValorItemLancamentoCatPublica(BigDecimal valor){

		if(valorItemLancamentoCatPublica != null){

			valorItemLancamentoCatPublica = valorItemLancamentoCatPublica.add(valor);

		}else{

			valorItemLancamentoCatPublica = valor;

		}

	}

}
