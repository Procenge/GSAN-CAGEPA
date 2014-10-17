package gcom.atendimentopublico.ordemservico;

import gcom.cadastro.localidade.Localidade;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Ado Rocha
 * @date 23/04/2014
 **/

public class ServicoTipoValorLocalidade
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Localidade localidade;

	private ServicoTipo servicoTipo;

	private BigDecimal valorServico;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	public ServicoTipoValorLocalidade() {

	}

	public ServicoTipoValorLocalidade(Integer id, Localidade localidade, ServicoTipo servicoTipo, BigDecimal valorServico,
										Short indicadorUso, Date ultimaAlteracao) {

		this.id = id;
		this.localidade = localidade;
		this.servicoTipo = servicoTipo;
		this.valorServico = valorServico;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}


	public Integer getId(){

		return id;
	}


	public void setId(Integer id){

		this.id = id;
	}

	public Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public ServicoTipo getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}


	public BigDecimal getValorServico(){

		return valorServico;
	}


	public void setValorServico(BigDecimal valorServico){

		this.valorServico = valorServico;
	}


	public Short getIndicadorUso(){

		return indicadorUso;
	}


	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}


	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}


	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroServicoTipoValorLocalidade filtroServicoTipoValorLocalidade = new FiltroServicoTipoValorLocalidade();
		filtroServicoTipoValorLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoTipoValorLocalidade.LOCALIDADE);
		filtroServicoTipoValorLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoTipoValorLocalidade.SERVICO_TIPO);

		return filtroServicoTipoValorLocalidade;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

}
