
package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.seguranca.acesso.usuario.Usuario;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Helper para construção do Relatório Estatístico de Registro de Atendimento
 * 
 * @author Luciano Galvão
 * @date 31/08/2012
 */
public class RelatorioEstatisticoRegistroAtendimentoHelper
				implements Serializable {

	public static final String SITUACAO_TODOS = "0";

	public static final String SITUACAO_PENDENTES = "1";

	public static final String SITUACAO_ENCERRADOS = "2";

	public static final String SITUACAO_REITERADOS = "3";

	private UnidadeOrganizacional unidadeSuperior;

	private UnidadeOrganizacional unidadeAtendimento;

	private Usuario usuario;

	private String situacao;

	private Collection<SolicitacaoTipo> tiposSolicitacao;

	private Collection<SolicitacaoTipoEspecificacao> especificacoes;

	private UnidadeOrganizacional unidadeAtual;

	private Date periodoAtendimentoInicial;

	private Date periodoAtendimentoFinal;

	/**
	 * Construtor da classe
	 * 
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 */
	public RelatorioEstatisticoRegistroAtendimentoHelper(Date periodoAtendimentoInicial, Date periodoAtendimentoFinal) {

		this.periodoAtendimentoInicial = periodoAtendimentoInicial;
		this.periodoAtendimentoFinal = periodoAtendimentoFinal;
	}

	public UnidadeOrganizacional getUnidadeSuperior(){

		return unidadeSuperior;
	}

	public void setUnidadeSuperior(UnidadeOrganizacional unidadeSuperior){

		this.unidadeSuperior = unidadeSuperior;
	}

	public UnidadeOrganizacional getUnidadeAtendimento(){

		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(UnidadeOrganizacional unidadeAtendimento){

		this.unidadeAtendimento = unidadeAtendimento;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	public Collection<SolicitacaoTipo> getTiposSolicitacao(){

		return tiposSolicitacao;
	}

	public void setTiposSolicitacao(Collection<SolicitacaoTipo> tiposSolicitacao){

		this.tiposSolicitacao = tiposSolicitacao;
	}

	public Collection<SolicitacaoTipoEspecificacao> getEspecificacoes(){

		return especificacoes;
	}

	public void setEspecificacoes(Collection<SolicitacaoTipoEspecificacao> especificacoes){

		this.especificacoes = especificacoes;
	}

	public UnidadeOrganizacional getUnidadeAtual(){

		return unidadeAtual;
	}

	public void setUnidadeAtual(UnidadeOrganizacional unidadeAtual){

		this.unidadeAtual = unidadeAtual;
	}

	public Date getPeriodoAtendimentoInicial(){

		return periodoAtendimentoInicial;
	}

	public void setPeriodoAtendimentoInicial(Date periodoAtendimentoInicial){

		this.periodoAtendimentoInicial = periodoAtendimentoInicial;
	}

	public Date getPeriodoAtendimentoFinal(){

		return periodoAtendimentoFinal;
	}

	public void setPeriodoAtendimentoFinal(Date periodoAtendimentoFinal){

		this.periodoAtendimentoFinal = periodoAtendimentoFinal;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getSituacao(){

		return situacao;
	}

}
