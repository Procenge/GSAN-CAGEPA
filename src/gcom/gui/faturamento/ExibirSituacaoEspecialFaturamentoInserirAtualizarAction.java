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

package gcom.gui.faturamento;

import gcom.fachada.Fachada;
import gcom.faturamento.*;
import gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirSituacaoEspecialFaturamentoInserirAtualizarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirSituacaoEspecialFaturamentoInserirAtualizar");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);
		// Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);

		SituacaoEspecialFaturamentoInformarActionForm formTeste = (SituacaoEspecialFaturamentoInformarActionForm) sessao
						.getAttribute("situacaoEspecialFaturamentoInformarActionForm");
		
		SituacaoEspecialFaturamentoActionForm situacaoEspecialFaturamentoActionForm = (SituacaoEspecialFaturamentoActionForm) actionForm;

		Collection<Integer> SEMSituacaoEspecialFaturamento = (Collection<Integer>) sessao.getAttribute("SEMSituacaoEspecialFaturamento");

		FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo();
		Collection<FaturamentoSituacaoTipo> collectionFaturamentoSituacaoTipo = fachada.pesquisar(filtroFaturamentoSituacaoTipo,
						FaturamentoSituacaoTipo.class.getName());
		httpServletRequest.setAttribute("collectionFaturamentoSituacaoTipo", collectionFaturamentoSituacaoTipo);

		FiltroFaturamentoSituacaoMotivo filtroFaturamentoSituacaoMotivo = new FiltroFaturamentoSituacaoMotivo();
		Collection<FaturamentoSituacaoMotivo> collectionFaturamentoSituacaoMotivo = fachada.pesquisar(filtroFaturamentoSituacaoMotivo,
						FaturamentoSituacaoMotivo.class.getName());
		httpServletRequest.setAttribute("collectionFaturamentoSituacaoMotivo", collectionFaturamentoSituacaoMotivo);

		// Variável que indicará se usuário está:
		// 1 - Inserindo imóvel na Situacao Especial de Faturamento; ou
		// 2 - Manter imóveis na Situacao.
		// Obs: Zero (0) indica que não foi escolhida nenhuma das duas opções;
		String opcaoEscolhida = "0";
		if(situacaoEspecialFaturamentoActionForm.getOpcaoEscolhida() != null
						&& !situacaoEspecialFaturamentoActionForm.getOpcaoEscolhida().equals("")){
			opcaoEscolhida = situacaoEspecialFaturamentoActionForm.getOpcaoEscolhida();
		}

		if(httpServletRequest.getParameter("consultarImoveis") != null){

			// Caso o usuário pesquise o cliente pela matrícula.
			if(!httpServletRequest.getParameter("consultarImoveis").equals("S")){
				String codigoCliente = httpServletRequest.getParameter("consultarImoveis");
				SEMSituacaoEspecialFaturamento = new ArrayList<Integer>();
				SEMSituacaoEspecialFaturamento.add(new Integer(codigoCliente));
			}

			// Pegar as descrições dos TIPO e MOTIVO para colocar no Form.
			situacaoEspecialFaturamentoActionForm = colocarDescricoesTipoMotivo(situacaoEspecialFaturamentoActionForm);

			situacaoEspecialFaturamentoActionForm.setOpcaoEscolhida("1");
			if(SEMSituacaoEspecialFaturamento != null && !SEMSituacaoEspecialFaturamento.isEmpty()){

				// Cria a Instância da lista de Situação especial Faturamento.
				situacaoEspecialFaturamentoActionForm
								.setColecaoSituacaoEspecialFaturamentoHelper(new ArrayList<SituacaoEspecialFaturamentoHelper>());

				Iterator<Integer> iteratorSEMSituacaoEspecialFaturamento = SEMSituacaoEspecialFaturamento.iterator();
				while(iteratorSEMSituacaoEspecialFaturamento.hasNext()){

					// Cria a entidade a ser adicionada na lista.
					SituacaoEspecialFaturamentoHelper situacaoHelper = new SituacaoEspecialFaturamentoHelper();
					situacaoHelper.setIdImovel(((Integer) iteratorSEMSituacaoEspecialFaturamento.next()).toString());
					situacaoHelper.setIdFaturamentoSituacaoTipo(situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo());
					situacaoHelper.setIdFaturamentoSituacaoMotivo(situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoMotivo());
					situacaoHelper.setTipoSituacaoEspecialFaturamento(situacaoEspecialFaturamentoActionForm
									.getTipoSituacaoEspecialFaturamento());
					situacaoHelper.setMotivoSituacaoEspecialFaturamento(situacaoEspecialFaturamentoActionForm
									.getMotivoSituacaoEspecialFaturamento());
					situacaoHelper.setMesAnoReferenciaFaturamentoInicial(situacaoEspecialFaturamentoActionForm
									.getMesAnoReferenciaFaturamentoInicial());
					situacaoHelper.setMesAnoReferenciaFaturamentoFinal(situacaoEspecialFaturamentoActionForm
									.getMesAnoReferenciaFaturamentoFinal());
					situacaoHelper.setVolume(situacaoEspecialFaturamentoActionForm.getVolume());
					situacaoHelper.setPercentualEsgoto(situacaoEspecialFaturamentoActionForm.getPercentualEsgoto());

					Integer mesAnoFaturamento = fachada
									.pesquisarAnoMesReferenciaFaturamentoGrupo(new Integer(situacaoHelper.getIdImovel()));

					if(mesAnoFaturamento != null){
						int anoMes = Util.formatarMesAnoParaAnoMes(mesAnoFaturamento.intValue());
						situacaoHelper.setMesAnoReferenciaMenorIgualInicial(anoMes <= (Util.formatarMesAnoComBarraParaAnoMes(situacaoHelper
										.getMesAnoReferenciaFaturamentoInicial())).intValue());
						situacaoHelper.setMesAnoReferenciaMenorIgualFinal(anoMes <= (Util
										.formatarMesAnoComBarraParaAnoMes(situacaoHelper.getMesAnoReferenciaFaturamentoFinal())).intValue());
					}

					situacaoHelper.setIndicadorNovo("1");

					situacaoEspecialFaturamentoActionForm.getColecaoSituacaoEspecialFaturamentoHelper().add(situacaoHelper);
				}
			}
		}

		if(sessao.getAttribute("botaoOrigem") != null && ((String) sessao.getAttribute("botaoOrigem")).equals("Manter")
						&& httpServletRequest.getParameter("removerImovel") == null){
			situacaoEspecialFaturamentoActionForm.setOpcaoEscolhida("2");

			Collection<FaturamentoSituacaoHistorico> colecaoFaturamentoSituacaoHistorico = (Collection<FaturamentoSituacaoHistorico>) sessao
							.getAttribute("colecaoFaturamentoSituacaoHistorico");

			Iterator<FaturamentoSituacaoHistorico> colecaoFaturamentoSituacaoHistoricoIterator = colecaoFaturamentoSituacaoHistorico
							.iterator();
			situacaoEspecialFaturamentoActionForm.getColecaoSituacaoEspecialFaturamentoHelper().clear();
			while(colecaoFaturamentoSituacaoHistoricoIterator.hasNext()){
				FaturamentoSituacaoHistorico historico = (FaturamentoSituacaoHistorico) colecaoFaturamentoSituacaoHistoricoIterator.next();
				if(historico.getImovel() != null){
					SituacaoEspecialFaturamentoHelper situacaoHelper = new SituacaoEspecialFaturamentoHelper();

					filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(filtroFaturamentoSituacaoTipo.ID, historico
									.getFaturamentoSituacaoTipo().getId()));
					Collection<FaturamentoSituacaoTipo> tipos = fachada.pesquisar(filtroFaturamentoSituacaoTipo,
									FaturamentoSituacaoTipo.class.getName());
					filtroFaturamentoSituacaoTipo.limparListaParametros();
					FaturamentoSituacaoTipo faturamentoSituacaoTipo = null;
					if(tipos != null && !tipos.isEmpty()){
						faturamentoSituacaoTipo = (FaturamentoSituacaoTipo) tipos.iterator().next();
					}

					filtroFaturamentoSituacaoMotivo.adicionarParametro(new ParametroSimples(filtroFaturamentoSituacaoMotivo.ID, historico
									.getFaturamentoSituacaoMotivo().getId()));
					Collection<FaturamentoSituacaoMotivo> motivos = fachada.pesquisar(filtroFaturamentoSituacaoMotivo,
									FaturamentoSituacaoMotivo.class.getName());
					filtroFaturamentoSituacaoMotivo.limparListaParametros();
					FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = null;
					if(motivos != null && !motivos.isEmpty()){
						faturamentoSituacaoMotivo = (FaturamentoSituacaoMotivo) motivos.iterator().next();
					}

					situacaoHelper.setIdImovel(historico.getImovel().getId().toString());
					if(faturamentoSituacaoTipo != null){
						situacaoHelper.setIdFaturamentoSituacaoTipo(faturamentoSituacaoTipo.getId().toString());
						if(faturamentoSituacaoTipo.getDescricao() != null){
							situacaoHelper.setTipoSituacaoEspecialFaturamento(faturamentoSituacaoTipo.getDescricao());
						}
					}
					if(faturamentoSituacaoMotivo != null){
						situacaoHelper.setIdFaturamentoSituacaoMotivo(faturamentoSituacaoMotivo.getId().toString());
						if(faturamentoSituacaoMotivo.getDescricao() != null){
							situacaoHelper.setMotivoSituacaoEspecialFaturamento(faturamentoSituacaoMotivo.getDescricao());
						}
					}
					situacaoHelper.setMesAnoReferenciaFaturamentoInicial(Util.formatarAnoMesSemBarraParaMesAnoComBarra(historico
									.getAnoMesFaturamentoSituacaoInicio()));
					situacaoHelper.setMesAnoReferenciaFaturamentoFinal(Util.formatarAnoMesSemBarraParaMesAnoComBarra(historico
									.getAnoMesFaturamentoSituacaoFim()));
					if(historico.getVolume() != null){
						situacaoHelper.setVolume(historico.getVolume().toString());
					}
					if(historico.getPercentualEsgoto() != null){
						situacaoHelper.setPercentualEsgoto(historico.getPercentualEsgoto().toString());
					}

					situacaoEspecialFaturamentoActionForm.getColecaoSituacaoEspecialFaturamentoHelper().add(situacaoHelper);
				}
			}
		}

		if(httpServletRequest.getParameter("removerImovel") != null){
			// Integer idImovelRemovido = new
			// Integer(httpServletRequest.getParameter("removerImovel"));
			this.removeImovel(situacaoEspecialFaturamentoActionForm, opcaoEscolhida);
		}

		if(httpServletRequest.getParameter("limparImoveis") != null){
			situacaoEspecialFaturamentoActionForm
							.setColecaoSituacaoEspecialFaturamentoHelper(new ArrayList<SituacaoEspecialFaturamentoHelper>());
			situacaoEspecialFaturamentoActionForm
							.setColecaoSituacaoEspecialFaturamentoHelperRemovidas(new ArrayList<SituacaoEspecialFaturamentoHelper>());
		}

		// Exibir quantidade de Imoveis com situacao especial de faturamento
		if(httpServletRequest.getParameter("bloquear") != null){
			if(httpServletRequest.getParameter("bloquear").equals("matricula")){
				httpServletRequest.setAttribute("bloquear", "matricula");
			}else{
				httpServletRequest.setAttribute("bloquear", "todos");
			}
		}else{
			httpServletRequest.setAttribute("bloquear", "");
		}

		// Manda o parametro que veio do validar enter para, se preciso,
		// desabilitar os campos posterior ao intervalo, que não são iguais.
		if(httpServletRequest.getParameter("campoDesabilita") != null && !httpServletRequest.getParameter("campoDesabilita").equals("")){
			httpServletRequest.setAttribute("campoDesabilita", httpServletRequest.getParameter("campoDesabilita"));
		}
		return retorno;
	}

	// Pegar as descrições dos TIPO e MOTIVO para colocar no Form.
	private SituacaoEspecialFaturamentoActionForm colocarDescricoesTipoMotivo(
					SituacaoEspecialFaturamentoActionForm situacaoEspecialFaturamentoActionForm){

		Fachada fachada = Fachada.getInstancia();

		if(situacaoEspecialFaturamentoActionForm != null){
			if(situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo() != null
							&& !situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo().equals("")){
				FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo();
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(filtroFaturamentoSituacaoTipo.ID,
								situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo()));
				Collection<FaturamentoSituacaoTipo> tipos = fachada.pesquisar(filtroFaturamentoSituacaoTipo, FaturamentoSituacaoTipo.class
								.getName());

				if(tipos != null && !tipos.isEmpty()){
					FaturamentoSituacaoTipo tipo = (FaturamentoSituacaoTipo) tipos.iterator().next();
					situacaoEspecialFaturamentoActionForm.setTipoSituacaoEspecialFaturamento(tipo.getDescricao());
				}
			}
			if(situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoMotivo() != null
							&& !situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoMotivo().equals("")){
				FiltroFaturamentoSituacaoMotivo filtroFaturamentoSituacaoMotivo = new FiltroFaturamentoSituacaoMotivo();
				filtroFaturamentoSituacaoMotivo.adicionarParametro(new ParametroSimples(filtroFaturamentoSituacaoMotivo.ID,
								situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoMotivo()));
				Collection<FaturamentoSituacaoMotivo> motivos = fachada.pesquisar(filtroFaturamentoSituacaoMotivo,
								FaturamentoSituacaoMotivo.class.getName());

				if(motivos != null && !motivos.isEmpty()){
					FaturamentoSituacaoMotivo motivo = (FaturamentoSituacaoMotivo) motivos.iterator().next();
					situacaoEspecialFaturamentoActionForm.setMotivoSituacaoEspecialFaturamento(motivo.getDescricao());
				}
			}
		}
		return situacaoEspecialFaturamentoActionForm;
	}

	/**
	 * Remove Imovel da lista
	 * 
	 * @author Saulo Lima
	 * @date 27/08/2008
	 * @param SituacaoEspecialFaturamentoActionForm
	 *            , idImovelRemovido
	 * @return SituacaoEspecialFaturamentoActionForm
	 */
	private SituacaoEspecialFaturamentoActionForm removeImovel(SituacaoEspecialFaturamentoActionForm form, String opcaoEscolhida){

		ArrayList<SituacaoEspecialFaturamentoHelper> alImoveisGrid = new ArrayList(form.getColecaoSituacaoEspecialFaturamentoHelper());
		Collection<SituacaoEspecialFaturamentoHelper> colImoveisRemover = new ArrayList();
		String[] idImoveisSelecionados = form.getItensSelecionados();

		SituacaoEspecialFaturamentoHelper wrapper = new SituacaoEspecialFaturamentoHelper();
		for(String idImovelSelecionado: idImoveisSelecionados){

			wrapper.setIdImovel(idImovelSelecionado);
			int indice = alImoveisGrid.indexOf(wrapper);
			if(indice != -1){
				colImoveisRemover.add(alImoveisGrid.get(indice));
			}

		}

		alImoveisGrid.removeAll(colImoveisRemover);

		form.setColecaoSituacaoEspecialFaturamentoHelper(alImoveisGrid);
		form.setColecaoSituacaoEspecialFaturamentoHelperRemovidas(colImoveisRemover);
		return form;
	}

}