/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.seguranca.acesso.transacao;

import java.util.Collection;
import java.util.Vector;

import org.apache.struts.action.ActionForm;

public class FiltrarOperacaoEfetuadaActionForm
				extends ActionForm {

	private String nomeFuncionalidade = "";

	private String idFuncionalidade = "";

	private String funcionalidadeNaoEncontrada = "false";

	private static final long serialVersionUID = 1L;

	private String[] operacoes = new String[0];

	private String nomeUsuario = "";

	private String idUsuario = "";

	private String usuarioNaoEncontrada = "false";

	private String idUsuarioAcao = "";

	private String dataInicial = "";

	private String dataFinal = "";

	private String horaInicial = "";

	private String horaFinal = "";

	private String nomeTabela = "";

	private String idTabela = "";

	private String tabelaNaoEncontrada = "false";

	private String[] idTabelaColuna = new String[0];

	private String id1 = "";

	private String idTabelaColunaSemOperacao = "";

	private String lebelArgumentoPesquisa = "";

	private String idArgumentoPesquisa = "";

	private String nomeArgumentoPesquisa = "";

	private String valorArgumentoPesquisa = "";

	private String url = "";

	private String msgArgumentoPesquisaEncontrado = "";

	/** ATRIBUTO QUE SERVE COMO INDICADOR SE EH PRA HABILITAR OU NAO OS CAMPOS DESCRITOS */
	private String desabilitaPesquisarPor = "false";

	private String desabilitaArgumentoPesquisa = "false";

	private String desabilitaInformacoesDisponiveis = "";

	private Collection collUsuarioAcao;

	private Collection collTabelaColuna;

	private Collection collTabelaColunaSemOperacao;

	private String acao = "";

	// Argumentos que serão usados para consultar caso seja informado, de acordo com a lógica de argumentos.
	private String imovelArgumento = "";

	private String imovelNomeArgumento = "";

	private String clienteArgumento = "";

	private String clienteNomeArgumento = "";

	private String ordemServicoArgumento = "";

	private String ordemServicoNomeArgumento = "";

	private Integer idHidrometroArgumento = null;

	private String numeroHidrometroArgumento = "";

	private String hidrometroNomeArgumento = "";

	private String cobrancaAcaoArgumento = "";

	private String cobrancaAcaoNomeArgumento = "";

	private String localidadeArgumento = "";

	private String localidadeNomeArgumento = "";

	private String municipioArgumento = "";

	private String municipioNomeArgumento = "";

	private String setorComercialArgumento = "";

	private String setorComercialNomeArgumento = "";

	private String unidadeOperacionalArgumento = "";

	private String unidadeOperacionalNomeArgumento = "";

	private String idAvisoBancario = "";

	private String codigoAgenteArrecadador = "";

	private String dataLancamentoAviso = "";

	private String numeroSequencialAviso = "";

	private String registroAtendimentoArgumento = "";

	private String registroAtendimentoNomeArgumento = "";

	private String resolucaoDiretoria = "";

	private String imovelSituacaoTipo = "";

	/**
	 * @return Retorna o campo url.
	 */
	public String getUrl(){

		return url;
	}

	/**
	 * @param url
	 *            O url a ser setado.
	 */
	public void setUrl(String url){

		this.url = url;
	}

	/**
	 * @return Retorna o campo desabilitaArgumentoPesquisa.
	 */
	public String getDesabilitaArgumentoPesquisa(){

		return desabilitaArgumentoPesquisa;
	}

	/**
	 * @param desabilitaArgumentoPesquisa
	 *            O desabilitaArgumentoPesquisa a ser setado.
	 */
	public void setDesabilitaArgumentoPesquisa(String desabilitaArgumentoPesquisa){

		this.desabilitaArgumentoPesquisa = desabilitaArgumentoPesquisa;
		this.idArgumentoPesquisa = "";
		this.nomeArgumentoPesquisa = "";
	}

	/**
	 * @return Retorna o campo desabilitaInformacoesDisponíveis.
	 */
	public String getDesabilitaInformacoesDisponiveis(){

		return desabilitaInformacoesDisponiveis;
	}

	/**
	 * @param desabilitaInformacoesDisponíveis
	 *            O desabilitaInformacoesDisponíveis a ser setado.
	 */
	public void setDesabilitaInformacoesDisponiveis(String desabilitaInformacoesDisponiveis){

		this.desabilitaInformacoesDisponiveis = desabilitaInformacoesDisponiveis;
	}

	/**
	 * @return Retorna o campo desabilitaPesquisarPor.
	 */
	public String getDesabilitaPesquisarPor(){

		return desabilitaPesquisarPor;
	}

	/**
	 * @param desabilitaPesquisarPor
	 *            O desabilitaPesquisarPor a ser setado.
	 */
	public void setDesabilitaPesquisarPor(String desabilitaPesquisarPor){

		this.desabilitaPesquisarPor = desabilitaPesquisarPor;
	}

	/**
	 * @return Retorna o campo idArgumentoPesquisa.
	 */
	public String getIdArgumentoPesquisa(){

		return idArgumentoPesquisa;
	}

	/**
	 * @param idArgumentoPesquisa
	 *            O idArgumentoPesquisa a ser setado.
	 */
	public void setIdArgumentoPesquisa(String idArgumentoPesquisa){

		this.idArgumentoPesquisa = idArgumentoPesquisa;
	}

	/**
	 * @return Retorna o campo lebelArgumentoPesquisa.
	 */
	public String getLebelArgumentoPesquisa(){

		return lebelArgumentoPesquisa;
	}

	/**
	 * @param lebelArgumentoPesquisa
	 *            O lebelArgumentoPesquisa a ser setado.
	 */
	public void setLebelArgumentoPesquisa(String lebelArgumentoPesquisa){

		this.lebelArgumentoPesquisa = lebelArgumentoPesquisa;
	}

	/**
	 * @return Retorna o campo nomeArgumentoPesquisa.
	 */
	public String getNomeArgumentoPesquisa(){

		return nomeArgumentoPesquisa;
	}

	/**
	 * @param nomeArgumentoPesquisa
	 *            O nomeArgumentoPesquisa a ser setado.
	 */
	public void setNomeArgumentoPesquisa(String nomeArgumentoPesquisa){

		this.nomeArgumentoPesquisa = nomeArgumentoPesquisa;
	}

	/**
	 * @return Retorna o campo idTabelaColunaSemOperacao.
	 */
	public String getIdTabelaColunaSemOperacao(){

		return idTabelaColunaSemOperacao;
	}

	/**
	 * @param idTabelaColunaSemOperacao
	 *            O idTabelaColunaSemOperacao a ser setado.
	 */
	public void setIdTabelaColunaSemOperacao(String idTabelaColunaSemOperacao){

		this.idTabelaColunaSemOperacao = idTabelaColunaSemOperacao;
	}

	/**
	 * @return Retorna o campo collTabelaColunaSemOperacao.
	 */
	public Collection getCollTabelaColunaSemOperacao(){

		return collTabelaColunaSemOperacao;
	}

	/**
	 * @param collTabelaColunaSemOperacao
	 *            O collTabelaColunaSemOperacao a ser setado.
	 */
	public void setCollTabelaColunaSemOperacao(Collection collTabelaColunaSemOperacao){

		this.collTabelaColunaSemOperacao = collTabelaColunaSemOperacao;
	}

	public Collection getCollTabelaColuna(){

		return collTabelaColuna;
	}

	public void setCollTabelaColuna(Collection collTabelaColuna){

		this.collTabelaColuna = collTabelaColuna;
	}

	public Collection getCollUsuarioAcao(){

		return collUsuarioAcao;
	}

	public void setCollUsuarioAcao(Collection collUsuarioAcao){

		this.collUsuarioAcao = collUsuarioAcao;
	}

	public String getDataFinal(){

		return dataFinal;
	}

	public void setDataFinal(String dataFinal){

		this.dataFinal = dataFinal;
	}

	public String getDataInicial(){

		return dataInicial;
	}

	public void setDataInicial(String dataInicial){

		this.dataInicial = dataInicial;
	}

	public String getHoraFinal(){

		return horaFinal;
	}

	public void setHoraFinal(String horaFinal){

		this.horaFinal = horaFinal;
	}

	public String getHoraInicial(){

		return horaInicial;
	}

	public void setHoraInicial(String horaInicial){

		this.horaInicial = horaInicial;
	}

	public String[] getIdTabelaColuna(){

		return idTabelaColuna;
	}

	public void setIdTabelaColuna(String idTabelaColuna[]){

		Vector v = new Vector();
		if(idTabelaColuna != null){
			for(int i = 0; i < idTabelaColuna.length; i++){
				Integer integer = new Integer(idTabelaColuna[i]);
				if(integer.intValue() > 0){
					v.add(integer);
				}
			}
		}
		this.idTabelaColuna = new String[v.size()];
		for(int i = 0; i < v.size(); i++){
			this.idTabelaColuna[i] = v.get(i).toString();
		}
	}

	public String getIdFuncionalidade(){

		return idFuncionalidade;
	}

	public void setIdFuncionalidade(String idFuncionalidade){

		this.idFuncionalidade = idFuncionalidade;
	}

	public String getIdTabela(){

		return idTabela;
	}

	public void setIdTabela(String idTabela){

		this.idTabela = idTabela;
	}

	public String getIdUsuario(){

		return idUsuario;
	}

	public void setIdUsuario(String idUsuario){

		this.idUsuario = idUsuario;
	}

	public String getIdUsuarioAcao(){

		return idUsuarioAcao;
	}

	public void setIdUsuarioAcao(String idUsuarioAcao){

		this.idUsuarioAcao = idUsuarioAcao;
	}

	public String getNomeFuncionalidade(){

		return nomeFuncionalidade;
	}

	public void setNomeFuncionalidade(String nomeFuncionalidade){

		this.nomeFuncionalidade = nomeFuncionalidade;
	}

	public String getNomeTabela(){

		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela){

		this.nomeTabela = nomeTabela;
	}

	public String getNomeUsuario(){

		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario){

		this.nomeUsuario = nomeUsuario;
	}

	public String getFuncionalidadeNaoEncontrada(){

		return funcionalidadeNaoEncontrada;
	}

	public void setFuncionalidadeNaoEncontrada(String funcionalidadeNaoEncontrada){

		this.funcionalidadeNaoEncontrada = funcionalidadeNaoEncontrada;
	}

	public String getId1(){

		return id1;
	}

	public void setId1(String id1){

		this.id1 = id1;
	}

	public String getTabelaNaoEncontrada(){

		return tabelaNaoEncontrada;
	}

	public void setTabelaNaoEncontrada(String tabelaNaoEncontrada){

		this.tabelaNaoEncontrada = tabelaNaoEncontrada;
	}

	public String getUsuarioNaoEncontrada(){

		return usuarioNaoEncontrada;
	}

	public void setUsuarioNaoEncontrada(String usuarioNaoEncontrada){

		this.usuarioNaoEncontrada = usuarioNaoEncontrada;
	}

	public String getAcao(){

		return acao;
	}

	public void setAcao(String acao){

		this.acao = acao;
	}

	/**
	 * @return Returns the valorArgumentoPesquisa.
	 */
	public String getValorArgumentoPesquisa(){

		return valorArgumentoPesquisa;
	}

	/**
	 * @param valorArgumentoPesquisa
	 *            The valorArgumentoPesquisa to set.
	 */
	public void setValorArgumentoPesquisa(String valorArgumentoPesquisa){

		this.valorArgumentoPesquisa = valorArgumentoPesquisa;
	}

	/**
	 * @return Returns the valorArgumentoPesquisaNaoEncontrado.
	 */
	public String getMsgArgumentoPesquisaEncontrado(){

		return msgArgumentoPesquisaEncontrado;
	}

	/**
	 * @param valorArgumentoPesquisaNaoEncontrado
	 *            The valorArgumentoPesquisaNaoEncontrado to set.
	 */
	public void setMsgArgumentoPesquisaEncontrado(String msgArgumentoPesquisaEncontrado){

		this.msgArgumentoPesquisaEncontrado = msgArgumentoPesquisaEncontrado;
	}

	public String[] getOperacoes(){

		return operacoes;
	}

	public void setOperacoes(String[] operacoes){

		this.operacoes = operacoes;
	}

	/**
	 * @return the imovelArgumento
	 */
	public String getImovelArgumento(){

		return imovelArgumento;
	}

	/**
	 * @param imovelArgumento the imovelArgumento to set
	 */
	public void setImovelArgumento(String imovelArgumento){

		this.imovelArgumento = imovelArgumento;
	}

	/**
	 * @return the clienteArgumento
	 */
	public String getClienteArgumento(){

		return clienteArgumento;
	}

	/**
	 * @param clienteArgumento the clienteArgumento to set
	 */
	public void setClienteArgumento(String clienteArgumento){

		this.clienteArgumento = clienteArgumento;
	}

	/**
	 * @return the ordemServicoArgumento
	 */
	public String getOrdemServicoArgumento(){

		return ordemServicoArgumento;
	}

	/**
	 * @param ordemServicoArgumento the ordemServicoArgumento to set
	 */
	public void setOrdemServicoArgumento(String ordemServicoArgumento){

		this.ordemServicoArgumento = ordemServicoArgumento;
	}

	/**
	 * @return the hidrometroArgumento
	 */
	public String getNumeroHidrometroArgumento(){

		return numeroHidrometroArgumento;
	}

	/**
	 * @param numeroHidrometroArgumento the hidrometroArgumento to set
	 */
	public void setNumeroHidrometroArgumento(String numeroHidrometroArgumento){

		this.numeroHidrometroArgumento = numeroHidrometroArgumento;
	}

	/**
	 * @return the hidrometroArgumento
	 */
	public Integer getIdHidrometroArgumento(){

		return idHidrometroArgumento;
	}

	/**
	 * @param hidrometroArgumento the hidrometroArgumento to set
	 */
	public void setIdHidrometroArgumento(Integer idHidrometroArgumento){

		this.idHidrometroArgumento = idHidrometroArgumento;
	}

	/**
	 * @return the cobrancaAcaoArgumento
	 */
	public String getCobrancaAcaoArgumento(){

		return cobrancaAcaoArgumento;
	}

	/**
	 * @param cobrancaAcaoArgumento the cobrancaAcaoArgumento to set
	 */
	public void setCobrancaAcaoArgumento(String cobrancaAcaoArgumento){

		this.cobrancaAcaoArgumento = cobrancaAcaoArgumento;
	}

	/**
	 * @return the localidadeArgumento
	 */
	public String getLocalidadeArgumento(){

		return localidadeArgumento;
	}

	/**
	 * @param localidadeArgumento the localidadeArgumento to set
	 */
	public void setLocalidadeArgumento(String localidadeArgumento){

		this.localidadeArgumento = localidadeArgumento;
	}

	/**
	 * @return the municipioArgumento
	 */
	public String getMunicipioArgumento(){

		return municipioArgumento;
	}

	/**
	 * @param municipioArgumento the municipioArgumento to set
	 */
	public void setMunicipioArgumento(String municipioArgumento){

		this.municipioArgumento = municipioArgumento;
	}

	/**
	 * @return the setorComercialArgumento
	 */
	public String getSetorComercialArgumento(){

		return setorComercialArgumento;
	}

	/**
	 * @param setorComercialArgumento the setorComercialArgumento to set
	 */
	public void setSetorComercialArgumento(String setorComercialArgumento){

		this.setorComercialArgumento = setorComercialArgumento;
	}

	/**
	 * @return the unidadeOperacionalArgumento
	 */
	public String getUnidadeOperacionalArgumento(){

		return unidadeOperacionalArgumento;
	}

	/**
	 * @param unidadeOperacionalArgumento the unidadeOperacionalArgumento to set
	 */
	public void setUnidadeOperacionalArgumento(String unidadeOperacionalArgumento){

		this.unidadeOperacionalArgumento = unidadeOperacionalArgumento;
	}

	/**
	 * @return the imovelNomeArgumento
	 */
	public String getImovelNomeArgumento(){

		return imovelNomeArgumento;
	}

	/**
	 * @param imovelNomeArgumento the imovelNomeArgumento to set
	 */
	public void setImovelNomeArgumento(String imovelNomeArgumento){

		this.imovelNomeArgumento = imovelNomeArgumento;
	}

	/**
	 * @return the clienteNomeArgumento
	 */
	public String getClienteNomeArgumento(){

		return clienteNomeArgumento;
	}

	/**
	 * @param clienteNomeArgumento the clienteNomeArgumento to set
	 */
	public void setClienteNomeArgumento(String clienteNomeArgumento){

		this.clienteNomeArgumento = clienteNomeArgumento;
	}

	/**
	 * @return the ordemServicoNomeArgumento
	 */
	public String getOrdemServicoNomeArgumento(){

		return ordemServicoNomeArgumento;
	}

	/**
	 * @param ordemServicoNomeArgumento the ordemServicoNomeArgumento to set
	 */
	public void setOrdemServicoNomeArgumento(String ordemServicoNomeArgumento){

		this.ordemServicoNomeArgumento = ordemServicoNomeArgumento;
	}

	/**
	 * @return the hidrometroNomeArgumento
	 */
	public String getHidrometroNomeArgumento(){

		return hidrometroNomeArgumento;
	}

	/**
	 * @param hidrometroNomeArgumento the hidrometroNomeArgumento to set
	 */
	public void setHidrometroNomeArgumento(String hidrometroNomeArgumento){

		this.hidrometroNomeArgumento = hidrometroNomeArgumento;
	}

	/**
	 * @return the cobrancaAcaoNomeArgumento
	 */
	public String getCobrancaAcaoNomeArgumento(){

		return cobrancaAcaoNomeArgumento;
	}

	/**
	 * @param cobrancaAcaoNomeArgumento the cobrancaAcaoNomeArgumento to set
	 */
	public void setCobrancaAcaoNomeArgumento(String cobrancaAcaoNomeArgumento){

		this.cobrancaAcaoNomeArgumento = cobrancaAcaoNomeArgumento;
	}

	/**
	 * @return the localidadeNomeArgumento
	 */
	public String getLocalidadeNomeArgumento(){

		return localidadeNomeArgumento;
	}

	/**
	 * @param localidadeNomeArgumento the localidadeNomeArgumento to set
	 */
	public void setLocalidadeNomeArgumento(String localidadeNomeArgumento){

		this.localidadeNomeArgumento = localidadeNomeArgumento;
	}

	/**
	 * @return the municipioNomeArgumento
	 */
	public String getMunicipioNomeArgumento(){

		return municipioNomeArgumento;
	}

	/**
	 * @param municipioNomeArgumento the municipioNomeArgumento to set
	 */
	public void setMunicipioNomeArgumento(String municipioNomeArgumento){

		this.municipioNomeArgumento = municipioNomeArgumento;
	}

	/**
	 * @return the setorComercialNomeArgumento
	 */
	public String getSetorComercialNomeArgumento(){

		return setorComercialNomeArgumento;
	}

	/**
	 * @param setorComercialNomeArgumento the setorComercialNomeArgumento to set
	 */
	public void setSetorComercialNomeArgumento(String setorComercialNomeArgumento){

		this.setorComercialNomeArgumento = setorComercialNomeArgumento;
	}

	/**
	 * @return the unidadeOperacionalNomeArgumento
	 */
	public String getUnidadeOperacionalNomeArgumento(){

		return unidadeOperacionalNomeArgumento;
	}

	/**
	 * @param unidadeOperacionalNomeArgumento the unidadeOperacionalNomeArgumento to set
	 */
	public void setUnidadeOperacionalNomeArgumento(String unidadeOperacionalNomeArgumento){

		this.unidadeOperacionalNomeArgumento = unidadeOperacionalNomeArgumento;
	}

	/**
	 * @return the idAvisoBancario
	 */
	public String getIdAvisoBancario(){

		return idAvisoBancario;
	}

	/**
	 * @param idAvisoBancario the idAvisoBancario to set
	 */
	public void setIdAvisoBancario(String idAvisoBancario){

		this.idAvisoBancario = idAvisoBancario;
	}

	/**
	 * @return the codigoAgenteArrecadador
	 */
	public String getCodigoAgenteArrecadador(){

		return codigoAgenteArrecadador;
	}

	/**
	 * @param codigoAgenteArrecadador the codigoAgenteArrecadador to set
	 */
	public void setCodigoAgenteArrecadador(String codigoAgenteArrecadador){

		this.codigoAgenteArrecadador = codigoAgenteArrecadador;
	}

	/**
	 * @return the dataLancamentoAviso
	 */
	public String getDataLancamentoAviso(){

		return dataLancamentoAviso;
	}

	/**
	 * @param dataLancamentoAviso the dataLancamentoAviso to set
	 */
	public void setDataLancamentoAviso(String dataLancamentoAviso){

		this.dataLancamentoAviso = dataLancamentoAviso;
	}


	/**
	 * @return the numeroSequencialAviso
	 */
	public String getNumeroSequencialAviso(){

		return numeroSequencialAviso;
	}

	/**
	 * @param numeroSequencialAviso the numeroSequencialAviso to set
	 */
	public void setNumeroSequencialAviso(String numeroSequencialAviso){

		this.numeroSequencialAviso = numeroSequencialAviso;
	}

	public String getRegistroAtendimentoArgumento(){

		return registroAtendimentoArgumento;
	}

	public void setRegistroAtendimentoArgumento(String registroAtendimentoArgumento){

		this.registroAtendimentoArgumento = registroAtendimentoArgumento;
	}

	public String getRegistroAtendimentoNomeArgumento(){

		return registroAtendimentoNomeArgumento;
	}

	public void setRegistroAtendimentoNomeArgumento(String registroAtendimentoNomeArgumento){

		this.registroAtendimentoNomeArgumento = registroAtendimentoNomeArgumento;
	}

	public String getResolucaoDiretoria(){

		return resolucaoDiretoria;
	}

	public void setResolucaoDiretoria(String resolucaoDiretoria){

		this.resolucaoDiretoria = resolucaoDiretoria;
	}

	public String getImovelSituacaoTipo(){

		return imovelSituacaoTipo;
	}

	public void setImovelSituacaoTipo(String imovelSituacaoTipo){

		this.imovelSituacaoTipo = imovelSituacaoTipo;
	}

}