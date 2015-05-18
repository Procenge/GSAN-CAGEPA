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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.seguranca.acesso.transacao;

import gcom.cobranca.parcelamento.FiltroParcelamentoPerfil;
import gcom.cobranca.parcelamento.ParcelamentoPerfil;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Argumento;
import gcom.seguranca.acesso.FiltroFuncionalidade;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarOperacaoEfetuadaAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @author toscano / Romulo Aurelio - Correcao de Bug
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author eduardo Henrique
	 * @date 21/05/2008
	 *       Incluído filtro de múltipla escolha de operações para visualização de Transações
	 *       efetuadas.
	 *       Filtro Argumento de Pesquisa foi retirado por enquanto, até revisão desta
	 *       funcionalidade.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultaOperacaoEfetuada");

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.removeAttribute("usuarioAcao");
		sessao.removeAttribute("idOperacaoEfetuada");
		Fachada fachada = Fachada.getInstancia();

		Integer idUsuarioAcao = null;
		Integer idFuncionalidade = null;
		String[] idOperacoes = null;
		String idUsuario = null;
		Date dataInicial = null;
		Date dataFinal = null;
		Date horaInicial = null;
		Date horaFinal = null;
		Integer idTabela = null;
		Integer[] idTabelaColuna = null;
		Integer id1 = null;
		Hashtable<Integer, String> argumentos = new Hashtable<Integer, String>();
		String idResolucaoDiretoria = form.getResolucaoDiretoria();
		String idImovelSituacaoTipo = form.getImovelSituacaoTipo();

		if(httpServletRequest.getParameter("funcionalidadeLimpa") != null
						&& httpServletRequest.getParameter("funcionalidadeLimpa").equals("1")){
			sessao.removeAttribute("idFuncionalidade");
			sessao.removeAttribute("argumentos");
			sessao.removeAttribute("nomeOperacaoSelecionada");
		}

		if(!"".equals(form.getIdFuncionalidade()) && form.getIdFuncionalidade() != null
						&& !form.getIdFuncionalidade().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			idFuncionalidade = new Integer(form.getIdFuncionalidade());
			sessao.setAttribute("idFuncionalidade", idFuncionalidade);
		}else{

			if(sessao.getAttribute("idFuncionalidade") != null
							&& !sessao.getAttribute("idFuncionalidade").equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				idFuncionalidade = (Integer) sessao.getAttribute("idFuncionalidade");
			}
		}
		if(httpServletRequest.getParameter("usuarioLimpo") != null && httpServletRequest.getParameter("usuarioLimpo").equals("1")){
			sessao.removeAttribute("idUsuario");
			sessao.removeAttribute("nomeUsuario");
		}
		if(httpServletRequest.getParameter("dataLimpa") != null && httpServletRequest.getParameter("dataLimpa").equals("1")){
			sessao.removeAttribute("dataInicial");
			sessao.removeAttribute("dataFinal");
		}
		if(httpServletRequest.getParameter("horaLimpa") != null && httpServletRequest.getParameter("horaLimpa").equals("1")){
			sessao.removeAttribute("horaInicial");
			sessao.removeAttribute("horaFinal");
		}
		if(httpServletRequest.getParameter("argumentoLimpo") != null && httpServletRequest.getParameter("argumentoLimpo").equals("1")){
			sessao.removeAttribute("argumentos");
		}

		if(form.getNomeFuncionalidade() != null && !form.getNomeFuncionalidade().equals("")){
			sessao.setAttribute("nomeOperacaoSelecionada", form.getNomeFuncionalidade());
		}

		if(form.getOperacoes() != null){
			idOperacoes = form.getOperacoes();
			if(idOperacoes.length > 1){
				sessao.setAttribute("variasOperacoesSelecionadas", "true");
			}else{
				sessao.setAttribute("variasOperacoesSelecionadas", "false");
				if(idOperacoes[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					idOperacoes = null;
					idFuncionalidade = null;
				}
				if(form.getNomeFuncionalidade() == null || form.getNomeFuncionalidade().equals("") && idFuncionalidade != null){
					FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
					filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, idFuncionalidade));

					Collection<Funcionalidade> colecaoFuncionalidade = fachada.pesquisar(filtroFuncionalidade,
									Funcionalidade.class.getName());
					if(colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()){
						sessao.setAttribute("nomeOperacaoSelecionada", colecaoFuncionalidade.iterator().next().getDescricao());
					}
				}
			}

		}else{
			sessao.setAttribute("variasOperacoesSelecionadas", "false");
		}

		if(!"".equals(form.getIdUsuario()) && form.getIdUsuario() != null){
			idUsuario = form.getIdUsuario();
			sessao.setAttribute("idUsuario", idUsuario);
		}else{
			// sessao.removeAttribute("idUsuario");
			if(sessao.getAttribute("idUsuario") != null){
				idUsuario = (String) sessao.getAttribute("idUsuario");
			}
		}
		// Neste caso, foi selecionado um usuario, sem especificar as operacoes
		if(idUsuario != null && (form.getOperacoes() == null || form.getOperacoes().length == 0)){
			sessao.setAttribute("variasOperacoesSelecionadas", "true");
		}

		if(!"".equals(form.getDataInicial()) && form.getDataInicial() != null){
			dataInicial = Util.converteStringParaDate(form.getDataInicial());
			sessao.setAttribute("dataInicial", dataInicial);
		}else{
			// sessao.removeAttribute("dataInicial");
			if(sessao.getAttribute("dataInicial") != null){
				// String data = (String)sessao.getAttribute("dataInicial");
				dataInicial = (Date) sessao.getAttribute("dataInicial"); /*
																		 * Util.converteStringParaDate
																		 * (data);
																		 */
			}
		}

		if(!"".equals(form.getDataFinal()) && form.getDataFinal() != null){
			dataFinal = Util.converteStringParaDate(form.getDataFinal());
			sessao.setAttribute("dataFinal", dataFinal);
		}else{
			// sessao.removeAttribute("dataFinal");
			if(sessao.getAttribute("dataFinal") != null){
				dataFinal = (Date) sessao.getAttribute("dataFinal");
				// Util.converteStringParaDate((String)sessao.getAttribute("dataFinal"));
			}
		}

		if(!"".equals(form.getHoraInicial()) && form.getHoraInicial() != null){
			horaInicial = Util.converterStringParaHoraMinuto(form.getHoraInicial());
			sessao.setAttribute("horaInicial", horaInicial);
		}else{
			// sessao.removeAttribute("horaInicial");
			if(sessao.getAttribute("horaInicial") != null){
				horaInicial = /* Util.converterStringParaHoraMinuto( */(Date) sessao.getAttribute("horaInicial");
			}
		}

		if(!"".equals(form.getHoraFinal()) && form.getHoraFinal() != null){
			horaFinal = Util.converterStringParaHoraMinuto(form.getHoraFinal());
			sessao.setAttribute("horaFinal", horaFinal);
		}else{
			// sessao.removeAttribute("horaFinal");
			if(sessao.getAttribute("horaFinal") != null){
				horaFinal = (Date) sessao.getAttribute("horaFinal");
			}
		}

		if(!"".equals(form.getIdTabela()) && form.getIdTabela() != null){
			idTabela = new Integer(form.getIdTabela());
			sessao.setAttribute("idTabela", idTabela);
		}else{
			if(sessao.getAttribute("idTabela") != null){
				idTabela = (Integer) sessao.getAttribute("idTabela");
			}
		}

		if(form.getIdTabelaColuna() != null && form.getIdTabelaColuna().length > 0){
			idTabelaColuna = new Integer[form.getIdTabelaColuna().length];
			for(int i = 0; i < form.getIdTabelaColuna().length; i++){
				try{
					if(form.getIdTabelaColuna()[i] != null && !"".equals(form.getIdTabelaColuna()[i])){
						Integer integer = new Integer(form.getIdTabelaColuna()[i]);
						if(integer != null && integer.intValue() > 0){
							idTabelaColuna[i] = integer;
						}
					}
				}catch(Exception e){
				}
			}
		}
		if(form.getIdTabelaColuna().length == 0 && !"".equals(form.getIdTabelaColunaSemOperacao())
						&& form.getIdTabelaColunaSemOperacao() != null){
			idTabelaColuna = new Integer[1];
			try{
				idTabelaColuna[0] = new Integer(form.getIdTabelaColunaSemOperacao());
			}catch(Exception e){
			}
		}

		if(dataInicial != null && dataFinal != null && dataFinal.getTime() < dataInicial.getTime()){
			throw new ActionServletException("atencao.data_fim_menor_inicio");
		}

		if(horaInicial != null && horaFinal != null && horaFinal.getTime() < horaInicial.getTime()){
			throw new ActionServletException("atencao.hora_fim_menor_inicio");
		}

		// 1º Passo - Pegar o total de registros através de um count da consulta que aparecerá na
		// tela

		// Em argumentosAdicionados é guardado o par (nome do argumento = id da coluna do argumento
		// de pesquisa, valor do argumento)

		// if(form.getValorArgumentoPesquisa() != null &&
		// !form.getValorArgumentoPesquisa().equals("")){
		// argumentos.put(form.getIdArgumentoPesquisa(), form.getValorArgumentoPesquisa());
		// form.setValorArgumentoPesquisa("");
		// sessao.setAttribute("argumentos", argumentos);
		// }else{
		// Object arg = sessao.getAttribute("argumentos");
		// if(arg != null){
		// argumentos = (Hashtable) arg;
		// }
		// }

		// ---------------------------------------------------

		if(Util.isNaoNuloBrancoZero(form.getImovelArgumento())){
			argumentos.put(Argumento.IMOVEL, form.getImovelArgumento());
		}
		if(Util.isNaoNuloBrancoZero(form.getClienteArgumento())){
			argumentos.put(Argumento.CLIENTE, form.getClienteArgumento());
		}
		if(Util.isNaoNuloBrancoZero(form.getOrdemServicoArgumento())){
			argumentos.put(Argumento.ORDEM_SERVICO, form.getOrdemServicoArgumento());
		}
		if(Util.isNaoNuloBrancoZero(form.getIdHidrometroArgumento())){
			argumentos.put(Argumento.HIDROMETRO, form.getIdHidrometroArgumento() + "");
		}
		if(Util.isNaoNuloBrancoZero(form.getLocalidadeArgumento())){
			argumentos.put(Argumento.LOCALIDADE, form.getLocalidadeArgumento());
		}
		if(Util.isNaoNuloBrancoZero(form.getSetorComercialArgumento())){
			argumentos.put(Argumento.SETOR_COMERCIAL, form.getSetorComercialArgumento());
		}
		if(Util.isNaoNuloBrancoZero(form.getMunicipioArgumento())){
			argumentos.put(Argumento.MUNICIPIO, form.getMunicipioArgumento());
		}
		if(Util.isNaoNuloBrancoZero(form.getIdAvisoBancario())){
			argumentos.put(Argumento.AVISO_BANCARIO, form.getIdAvisoBancario() + "");
		}
		if(Util.isNaoNuloBrancoZero(form.getRegistroAtendimentoArgumento())){
			argumentos.put(Argumento.REGISTRO_ATENDIMENTO, form.getRegistroAtendimentoArgumento() + "");
		}

		if(Util.isNaoNuloBrancoZero(idResolucaoDiretoria)){

			if(!idResolucaoDiretoria.equalsIgnoreCase("-1") && idFuncionalidade == 111){
				argumentos.put(Argumento.RESOLUCAO_DIRETORIA, idResolucaoDiretoria + "");
			}

			if(!idResolucaoDiretoria.equalsIgnoreCase("-1") && idFuncionalidade == 3){

				FiltroParcelamentoPerfil filtroParcelamentoPerfil = new FiltroParcelamentoPerfil();
				filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(FiltroParcelamentoPerfil.RESOLUCAO_DIRETORIA_ID,
								idResolucaoDiretoria));

				if(idImovelSituacaoTipo.equalsIgnoreCase("-1")){
					throw new ActionServletException("atencao.informe_tipo_situacao_imovel_perfil_parcelamento");
				}

				if(idImovelSituacaoTipo != null && !idImovelSituacaoTipo.equalsIgnoreCase("-1")){
					filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(FiltroParcelamentoPerfil.IMOVEL_SITUACAO_TIPO_ID, form
									.getImovelSituacaoTipo()));
				}
				Collection colecaoParcelamentoPerfil = fachada.pesquisar(filtroParcelamentoPerfil, ParcelamentoPerfil.class.getName());
				if(colecaoParcelamentoPerfil != null){
					Iterator iteratorColecaoParcelamentoPerfil = colecaoParcelamentoPerfil.iterator();
					while(iteratorColecaoParcelamentoPerfil.hasNext()){
						ParcelamentoPerfil parcelamentoPerfil = (ParcelamentoPerfil) iteratorColecaoParcelamentoPerfil.next();
						argumentos.put(Argumento.RESOLUCAO_DIRETORIA_PARCELAMENTO, parcelamentoPerfil.getId() + "");
					}

				}else{
					argumentos.put(Argumento.RESOLUCAO_DIRETORIA_PARCELAMENTO, null + "");
				}
			}
		}


		sessao.setAttribute("argumentos", argumentos);

		// ---------------------------------------------------

		Integer totalRegistros = (Integer) fachada.pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlCount(idUsuarioAcao, idOperacoes,
						idUsuario, dataInicial, dataFinal, horaInicial, horaFinal, argumentos, id1);

		// 2º Passo - Chamar a função de Paginação passando o total de registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		// 3º Passo - Obter a coleção da consulta que aparecerá na tela passando o numero de paginas
		// da pesquisa que está no request

		// Integer idUsuarioInt = (idUsuario == null) ? null : Integer.parseInt(idUsuario);

		Collection coll = Fachada.getInstancia().pesquisarUsuarioAlteracaoDasOperacoesEfetuadas(idUsuarioAcao, idOperacoes, idUsuario,
						dataInicial, dataFinal, horaInicial, horaFinal, argumentos, id1,
						(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

		// Collection coll = (Collection) fachada.pesquisarUsuarioAlteracaoDasOperacoesEfetuadas(
		// idUsuarioAcao,idOperacao, idUsuario,dataInicial, dataFinal, horaInicial, horaFinal,
		// idTabela, idTabelaColuna, id1,
		// (Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

		Collection resumos = new ArrayList();
		if(coll == null || coll.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Transação");
		}else{
			for(Iterator iter = coll.iterator(); iter.hasNext();){
				OperacaoEfetuada operacaoEfet = (OperacaoEfetuada) iter.next();
				resumos.add(consultarInformacoesExtrasOperacaoEfetuada(operacaoEfet));
			}
		}

		sessao.removeAttribute("descricaoArgumento");

		sessao.setAttribute("resumosDados", resumos);
		sessao.setAttribute("operacaoEfetuada", coll);
		sessao.setAttribute("idUsuarioAcao", idUsuarioAcao);
		sessao.setAttribute("idFuncionalidade", idFuncionalidade);
		sessao.setAttribute("idUsuario", idUsuario);
		sessao.setAttribute("dataInicial", dataInicial);
		sessao.setAttribute("dataFinal", dataFinal);
		sessao.setAttribute("horaInicial", horaInicial);
		sessao.setAttribute("horaFinal", horaFinal);
		sessao.setAttribute("idTabela", idTabela);
		sessao.setAttribute("idTabelaColuna", idTabelaColuna);
		sessao.setAttribute("id1", id1);
		sessao.setAttribute("operacoes", idOperacoes);

		return retorno;
	}

	private HashMap consultarInformacoesExtrasOperacaoEfetuada(OperacaoEfetuada operacaoEfetuada){

		// Fachada fachada = Fachada.getInstancia();

		HashMap resumo = new HashMap();

		// FiltroTabelaLinhaColunaAlteracao filtro = new FiltroTabelaLinhaColunaAlteracao();
		// filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA_COLUNA);
		// filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA);
		// filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.OPERACAO_EFETUADA);
		// filtro.adicionarParametro(new
		// ParametroSimples(FiltroTabelaLinhaColunaAlteracao.OPERACAO_EFETUADA_ID,
		// operacaoEfetuada.getId()));
		//
		// // Consultando a operacao referente a operacao efetuada
		// FiltroOperacao filtroOperacao = new FiltroOperacao();
		// filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(
		// FiltroOperacao.ARGUMENTO_PESQUISA);
		// filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(
		// FiltroOperacao.ARGUMENTO_PESQUISA_TABELA);
		// filtroOperacao.adicionarParametro(new
		// ParametroSimples(FiltroOperacao.ID,operacaoEfetuada.
		// getOperacao().getId()));
		//
		// Collection collOperacao = Fachada.getInstancia().pesquisar(filtroOperacao,
		// Operacao.class.getName());
		// Operacao operacao = (Operacao) collOperacao.iterator().next();

		// if (operacao.getArgumentoPesquisa() != null){
		// filtro.adicionarParametro(new
		// ParametroSimples(FiltroTabelaLinhaColunaAlteracao.TABELA_ID,
		// operacao.getArgumentoPesquisa().getTabela().getId()));
		// }

		// FiltroTabelaLinhaAlteracao filtro = new FiltroTabelaLinhaAlteracao();
		// filtro.adicionarParametro(
		// new ParametroSimples(FiltroTabelaLinhaAlteracao.OPERACAO_EFETUADA_ID,
		// operacaoEfetuada.getId()));
		// filtro.adicionarParametro(new
		// ParametroSimples(FiltroTabelaLinhaAlteracao.INDICADOR_PRINCIPAL,
		// TabelaLinhaAlteracao.INDICADOR_TABELA_LINHA_ALTERACAO_PRINCIPAL));
		//
		// Collection coll = fachada.pesquisar(filtro, TabelaLinhaAlteracao.class.getName());
		// if (coll != null && !coll.isEmpty()){
		// TabelaLinhaAlteracao alteracao = (TabelaLinhaAlteracao)
		// Util.retonarObjetoDeColecao(coll);
		// resumo = fachada.consultarResumoInformacoesOperacaoEfetuada(operacaoEfetuada,
		// alteracao.getId1());
		// }

		String dadosAdicionais = operacaoEfetuada.getDadosAdicionais();
		if(dadosAdicionais != null){

			StringTokenizer stk = new StringTokenizer(dadosAdicionais, "$");
			while(stk.hasMoreElements()){
				String element = (String) stk.nextElement();
				int ind = element.indexOf(":");
				if(ind != -1){
					String label = element.substring(0, ind);
					String valor = element.substring(ind + 1, element.length());
					resumo.put(label, valor);
				}

			}

		}
		return resumo;
	}

}
