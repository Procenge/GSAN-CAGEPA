
package gcom.faturamento;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.faturamento.debito.DebitoTipo;

import java.math.BigDecimal;

/**
 * @author Hibernate CodeGenerator
 * @created 3 de Agosto de 2011
 * @author Cinthya Cavalcanti
 * @date 03/08/2011
 */

public class HistogramaServico {

	private Integer id;

	private Integer qtdTotalDebitoCobrado;

	private BigDecimal valorTotalDebitoCobrado;

	private Localidade localidade;

	private SetorComercial setorComercial;

	private Categoria categoria;

	private DebitoTipo debitoTipo;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getQtdTotalDebitoCobrado(){

		return qtdTotalDebitoCobrado;
	}

	public void setQtdTotalDebitoCobrado(Integer qtdTotalDebitoCobrado){

		this.qtdTotalDebitoCobrado = qtdTotalDebitoCobrado;
	}

	public BigDecimal getValorTotalDebitoCobrado(){

		return valorTotalDebitoCobrado;
	}

	public void setValorTotalDebitoCobrado(BigDecimal valorTotalDebitoCobrado){

		this.valorTotalDebitoCobrado = valorTotalDebitoCobrado;
	}

	public Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public SetorComercial getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public DebitoTipo getDebitoTipo(){

		return debitoTipo;
	}

	public void setDebitoTipo(DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

}
