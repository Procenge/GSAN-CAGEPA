
package gcom.atendimentopublico.registroatendimento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.Filtro;

import java.io.Serializable;
import java.util.Date;

public class AtendimentoIncompleto
				extends ObjetoTransacao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Short ddd;

	private Integer numeroTelefone;

	private String nomeContato;

	private String descricaoObservacao;

	private Short indicadorRetornoChamada;

	private Date duracaoChamada;

	private Date ultimaAlteracao;

	private AtendimentoIncompletoMotivo atendimentoIncompletoMotivo;

	private SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao;

	private SolicitacaoTipo solicitacaoTipo;

	private Cliente cliente;

	private Imovel imovel;

	private RegistroAtendimento registroAtendimento;

	private UnidadeOrganizacional atendimentoUnidadeOrganizacional;

	private UnidadeOrganizacional retornoChamadaUnidadeOrganizacional;

	private Usuario atendimentoUsuario;

	private Usuario retornoChamadaUsuario;

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public Filtro retornaFiltro(){

		return null;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String[] retornaCamposChavePrimaria(){

		return null;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Short getDdd(){

		return ddd;
	}

	public void setDdd(Short ddd){

		this.ddd = ddd;
	}

	public Integer getNumeroTelefone(){

		return numeroTelefone;
	}

	public void setNumeroTelefone(Integer numeroTelefone){

		this.numeroTelefone = numeroTelefone;
	}

	public String getNomeContato(){

		return nomeContato;
	}

	public void setNomeContato(String nomeContato){

		this.nomeContato = nomeContato;
	}

	public String getDescricaoObservacao(){

		return descricaoObservacao;
	}

	public void setDescricaoObservacao(String descricaoObservacao){

		this.descricaoObservacao = descricaoObservacao;
	}

	public Short getIndicadorRetornoChamada(){

		return indicadorRetornoChamada;
	}

	public void setIndicadorRetornoChamada(Short indicadorRetornoChamada){

		this.indicadorRetornoChamada = indicadorRetornoChamada;
	}

	public AtendimentoIncompletoMotivo getAtendimentoIncompletoMotivo(){

		return atendimentoIncompletoMotivo;
	}

	public void setAtendimentoIncompletoMotivo(AtendimentoIncompletoMotivo atendimentoIncompletoMotivo){

		this.atendimentoIncompletoMotivo = atendimentoIncompletoMotivo;
	}

	public SolicitacaoTipoEspecificacao getSolicitacaoTipoEspecificacao(){

		return solicitacaoTipoEspecificacao;
	}

	public void setSolicitacaoTipoEspecificacao(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao){

		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
	}

	public SolicitacaoTipo getSolicitacaoTipo(){

		return solicitacaoTipo;
	}

	public void setSolicitacaoTipo(SolicitacaoTipo solicitacaoTipo){

		this.solicitacaoTipo = solicitacaoTipo;
	}

	public Cliente getCliente(){

		return cliente;
	}

	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public RegistroAtendimento getRegistroAtendimento(){

		return registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public UnidadeOrganizacional getAtendimentoUnidadeOrganizacional(){

		return atendimentoUnidadeOrganizacional;
	}

	public void setAtendimentoUnidadeOrganizacional(UnidadeOrganizacional atendimentoUnidadeOrganizacional){

		this.atendimentoUnidadeOrganizacional = atendimentoUnidadeOrganizacional;
	}

	public UnidadeOrganizacional getRetornoChamadaUnidadeOrganizacional(){

		return retornoChamadaUnidadeOrganizacional;
	}

	public void setRetornoChamadaUnidadeOrganizacional(UnidadeOrganizacional retornoChamadaUnidadeOrganizacional){

		this.retornoChamadaUnidadeOrganizacional = retornoChamadaUnidadeOrganizacional;
	}

	public Usuario getAtendimentoUsuario(){

		return atendimentoUsuario;
	}

	public void setAtendimentoUsuario(Usuario atendimentoUsuario){

		this.atendimentoUsuario = atendimentoUsuario;
	}

	public Usuario getRetornoChamadaUsuario(){

		return retornoChamadaUsuario;
	}

	public void setRetornoChamadaUsuario(Usuario retornoChamadaUsuario){

		this.retornoChamadaUsuario = retornoChamadaUsuario;
	}

	public Date getDuracaoChamada(){

		return duracaoChamada;
	}

	public void setDuracaoChamada(Date duracaoChamada){

		this.duracaoChamada = duracaoChamada;
	}

}
