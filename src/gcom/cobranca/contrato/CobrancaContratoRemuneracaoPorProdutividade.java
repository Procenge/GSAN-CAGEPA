
package gcom.cobranca.contrato;

import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Date;

public class CobrancaContratoRemuneracaoPorProdutividade
				extends ObjetoTransacao {

	private Integer id;

	private CobrancaContrato cobrancaContrato;

	private ServicoTipo servicoTipo;

	private BigDecimal valor;

	private Date ultimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public CobrancaContrato getCobrancaContrato(){

		return cobrancaContrato;
	}

	public void setCobrancaContrato(CobrancaContrato cobrancaContrato){

		this.cobrancaContrato = cobrancaContrato;
	}

	public ServicoTipo getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date dataUltimaAlteracao){

		this.ultimaAlteracao = dataUltimaAlteracao;
	}

	public String getValorFormatado(){

		return Util.formatarMoedaReal(this.valor);
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}
}
