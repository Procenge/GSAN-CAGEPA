
package gcom.relatorio.financeiro;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioSaldoContasAReceberContabilBean
				implements RelatorioBean {

	private Integer idTotalizador;

	private String descTotalizador;

	private Integer idTotalizadorSecundario;

	private String descTotalizadorSecundario;

	private Integer idTotalizadorTerciario;

	private String descTotalizadorTerciario;

	private BigDecimal valorTotalItemLancamento;

	private BigDecimal valorTotalItemLancamentoCatParticular;

	private BigDecimal valorTotalItemLancamentoCatPublica;

	private List<RelatorioSaldoContasAReceberContabilBean> dados;

	private List<RelatorioSaldoContasAReceberContabilDetalheBean> dadosDetalhe;

	public Integer getIdTotalizador(){

		return idTotalizador;
	}

	public void setIdTotalizador(Integer idTotalizador){

		this.idTotalizador = idTotalizador;
	}

	public String getDescTotalizador(){

		return descTotalizador;
	}

	public void setDescTotalizador(String descTotalizador){

		this.descTotalizador = descTotalizador;
	}

	public List<RelatorioSaldoContasAReceberContabilBean> getDados(){

		return dados;
	}

	public List<RelatorioSaldoContasAReceberContabilDetalheBean> getDadosDetalhe(){

		return dadosDetalhe;
	}

	public BigDecimal getValorTotalItemLancamento(){

		return valorTotalItemLancamento;
	}

	public BigDecimal getValorTotalItemLancamentoCatParticular(){

		return valorTotalItemLancamentoCatParticular;
	}

	public BigDecimal getValorTotalItemLancamentoCatPublica(){

		return valorTotalItemLancamentoCatPublica;
	}

	public void setValorTotalItemLancamentoCatPublica(BigDecimal valorItemLancamentoCatPublica){

		this.valorTotalItemLancamentoCatPublica = valorItemLancamentoCatPublica;
	}

	public void addDados(List<RelatorioSaldoContasAReceberContabilBean> dados){

		for(RelatorioSaldoContasAReceberContabilBean dado : dados){

			this.addDado(dado);

		}

	}

	public void addDado(RelatorioSaldoContasAReceberContabilBean dado){

		if(dados != null){

			dados.add(dado);

		}else{

			dados = new ArrayList<RelatorioSaldoContasAReceberContabilBean>();
			dados.add(dado);

		}

		this.addValorItemLancamento(dado.getValorTotalItemLancamento());
		this.addValorItemLancamentoCatParticular(dado.getValorTotalItemLancamentoCatParticular());
		this.addValorItemLancamentoCatPublica(dado.getValorTotalItemLancamentoCatPublica());

	}

	public void addDetalhes(List<RelatorioSaldoContasAReceberContabilDetalheBean> detalhes){

		for(RelatorioSaldoContasAReceberContabilDetalheBean detalhe : detalhes){

			this.addDetalhe(detalhe);

		}

	}

	private void addDetalhe(RelatorioSaldoContasAReceberContabilDetalheBean detalhe){

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

		if(valorTotalItemLancamento != null){

			valorTotalItemLancamento = valorTotalItemLancamento.add(valor);

		}else{

			valorTotalItemLancamento = valor;

		}

	}

	private void addValorItemLancamentoCatParticular(BigDecimal valor){

		if(valorTotalItemLancamentoCatParticular != null){

			valorTotalItemLancamentoCatParticular = valorTotalItemLancamentoCatParticular.add(valor);

		}else{

			valorTotalItemLancamentoCatParticular = valor;

		}

	}

	private void addValorItemLancamentoCatPublica(BigDecimal valor){

		if(valorTotalItemLancamentoCatPublica != null){

			valorTotalItemLancamentoCatPublica = valorTotalItemLancamentoCatPublica.add(valor);

		}else{

			valorTotalItemLancamentoCatPublica = valor;

		}

	}

	public Integer getIdTotalizadorSecundario(){

		return idTotalizadorSecundario;
	}

	public void setIdTotalizadorSecundario(Integer idTotalizadorSecundario){

		this.idTotalizadorSecundario = idTotalizadorSecundario;
	}

	public String getDescTotalizadorSecundario(){

		return descTotalizadorSecundario;
	}

	public void setDescTotalizadorSecundario(String descTotalizadorSecundario){

		this.descTotalizadorSecundario = descTotalizadorSecundario;
	}

	public Integer getIdTotalizadorTerciario(){

		return idTotalizadorTerciario;
	}

	public void setIdTotalizadorTerciario(Integer idTotalizadorTerciario){

		this.idTotalizadorTerciario = idTotalizadorTerciario;
	}

	public String getDescTotalizadorTerciario(){

		return descTotalizadorTerciario;
	}

	public void setDescTotalizadorTerciario(String descTotalizadorTerciario){

		this.descTotalizadorTerciario = descTotalizadorTerciario;
	}

}
