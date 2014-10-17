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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroMeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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

/**
 * Esta classe tem por finalidade exibir para o usuário a tela que receberá os
 * parâmetros para realização da atualização de um R.A (Aba nº 03 - Dados do
 * solicitante)
 * 
 * @author Sávio Luiz
 * @date 10/08/2006
 */
public class ExibirAtualizarRegistroAtendimentoDadosSolicitanteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarRegistroAtendimentoDadosSolicitante");

		AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm = (AtualizarRegistroAtendimentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.setAttribute("idEspecificacao", atualizarRegistroAtendimentoActionForm.getEspecificacao());

		// caso a escolha seja de remover o registro de atendimento solicitante
		if(httpServletRequest.getParameter("idRASolicitante") != null){
			// recupera o id do solicitante para ser removido
			long idRASolicitante = Util.converterStringParaLong(httpServletRequest.getParameter("idRASolicitante"));
			// cria ou recupera a coleção de RA solicitante removidos
			Collection colecaoRASolicitanteRemovidas = null;
			if(sessao.getAttribute("colecaoRASolicitanteRemovidas") != null){
				colecaoRASolicitanteRemovidas = (Collection) sessao.getAttribute("colecaoRASolicitanteRemovidas");
			}else{
				colecaoRASolicitanteRemovidas = new ArrayList();
			}
			// recupera a coleção de RA solicitante
			Collection colecaoRASolicitante = (Collection) sessao.getAttribute("colecaoRASolicitante");
			Iterator iteratorRASolicitante = colecaoRASolicitante.iterator();
			while(iteratorRASolicitante.hasNext()){
				RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) iteratorRASolicitante
								.next();
				if(registroAtendimentoSolicitante.getUltimaAlteracao().getTime() == idRASolicitante){
					if(registroAtendimentoSolicitante.getID() != null && !registroAtendimentoSolicitante.getID().equals("")){
						colecaoRASolicitanteRemovidas.add(registroAtendimentoSolicitante);
					}
					iteratorRASolicitante.remove();
					break;
				}
			}
			sessao.setAttribute("colecaoRASolicitanteRemovidas", colecaoRASolicitanteRemovidas);
		}

		Fachada fachada = Fachada.getInstancia();
		// caso seja a primeira vez então pesquisa a coleção de solicitante
		if(sessao.getAttribute("colecaoRASolicitante") == null){

			FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();
			filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

			filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(
							FiltroRegistroAtendimentoSolicitante.REGISTRO_ATENDIMENTO_ID, atualizarRegistroAtendimentoActionForm
											.getNumeroRA()));

			Collection colecaoRASolicitante = fachada.pesquisar(filtroRegistroAtendimentoSolicitante, RegistroAtendimentoSolicitante.class
							.getName());

			Collection colecaoRASolicitanteComNome = null;

			if(colecaoRASolicitante != null && !colecaoRASolicitante.isEmpty()){

				Iterator iteRASolicitante = colecaoRASolicitante.iterator();
				colecaoRASolicitanteComNome = new ArrayList();
				while(iteRASolicitante.hasNext()){
					RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) iteRASolicitante
									.next();
					String nomeRASolicitante = fachada.obterNomeSolicitanteRA(registroAtendimentoSolicitante.getID());
					registroAtendimentoSolicitante.setSolicitante(nomeRASolicitante);
					colecaoRASolicitanteComNome.add(registroAtendimentoSolicitante);
				}
			}

			sessao.setAttribute("colecaoRASolicitante", colecaoRASolicitanteComNome);
		}else{
			Collection<RegistroAtendimentoSolicitante> colecaoRegistroAtendimentoSolicitante = (ArrayList<RegistroAtendimentoSolicitante>) sessao
							.getAttribute("colecaoRASolicitante");

			if(!colecaoRegistroAtendimentoSolicitante.isEmpty()){
				for(RegistroAtendimentoSolicitante registroAtendimentoSolicitante : colecaoRegistroAtendimentoSolicitante){
					if(registroAtendimentoSolicitante.getCliente() != null){

						Integer idEspecificacao = null;
						Integer idImovel = null;

						if(!Util.isVazioOuBranco(atualizarRegistroAtendimentoActionForm.getEspecificacao())){
							idEspecificacao = Integer.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao());
						}

						if(!Util.isVazioOuBranco(atualizarRegistroAtendimentoActionForm.getIdImovel())){
							idImovel = Integer.valueOf(atualizarRegistroAtendimentoActionForm.getIdImovel());
						}

						fachada.verificarDebitosImovelCliente(idEspecificacao, idImovel, registroAtendimentoSolicitante.getCliente()
										.getId());
					}
				}

			}

		}

		return retorno;
	}

	private void carregarDadosCliente(InserirRegistroAtendimentoActionForm form,
					RegistroAtendimentoSolicitante registroAtendimentoSolicitante){

		if(registroAtendimentoSolicitante.getClienteTipo() != null){
			form.setClienteTipo(registroAtendimentoSolicitante.getClienteTipo().toString());
			if(Integer.valueOf(form.getClienteTipo()) == ConstantesSistema.INDICADOR_PESSOA_JURIDICA.intValue()){
				form.setNumeroCnpj(registroAtendimentoSolicitante.getNumeroCnpj());
			}else{
				form.setNumeroCpf(registroAtendimentoSolicitante.getNumeroCpf());
				form.setNumeroRG(registroAtendimentoSolicitante.getNumeroRG());
				if(registroAtendimentoSolicitante.getOrgaoExpedidorRg() != null){
					form.setOrgaoExpedidorRg(registroAtendimentoSolicitante.getOrgaoExpedidorRg().getId().toString());
				}
				if(registroAtendimentoSolicitante.getUnidadeFederacaoRG() != null){
					form.setUnidadeFederacaoRG(registroAtendimentoSolicitante.getUnidadeFederacaoRG().getId().toString());
				}
			}
		}
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
		if(form.getTipoSolicitacao() != null){
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(filtroSolicitacaoTipoEspecificacao.ID, form
							.getTipoSolicitacao()));
			solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName()));
			if(solicitacaoTipoEspecificacao != null){
				if(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeRgRA() != null){
					form.setIndicadorRg(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeRgRA().toString());
				}
				if(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA() != null){
					form.setIndicadorCpfCnpj(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA().toString());
				}
			}
		}

		this.carregarDadosMeioSolicitacao(form);

	}

	private void carregarDadosMeioSolicitacao(InserirRegistroAtendimentoActionForm form){

		MeioSolicitacao meioSolicitacao = null;
		if(form.getMeioSolicitacao() != null){
			FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();
			filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.ID, form.getEspecificacao()));
			meioSolicitacao = (MeioSolicitacao) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroMeioSolicitacao,
							MeioSolicitacao.class.getName()));

			// Permite o não preenchimento do campo de doc de identificação da aba solicitante caso
			// o indicador de liberação para preenchimento do meio de solicitação esteja ativado,
			// independente dos indicadores de permissão do tipo de especificação
			if(meioSolicitacao != null
							&& Fachada.getInstancia().isMeioSolicitacaoLiberaDocumentoIdentificacaoRA(
											Integer.valueOf(form.getMeioSolicitacao()))){

				if(form.getIndicadorRg() != null){
					form.setIndicadorRg(ConstantesSistema.NAO.toString());
				}

				if(form.getIndicadorCpfCnpj() != null){
					form.setIndicadorCpfCnpj(ConstantesSistema.NAO.toString());
				}

			}
		}
	}
}
