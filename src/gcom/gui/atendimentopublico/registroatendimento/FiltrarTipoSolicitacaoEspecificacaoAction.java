/**
 * 
 */
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

import gcom.atendimentopublico.ordemservico.EspecificacaoServicoTipo;
import gcom.atendimentopublico.ordemservico.FiltroEspecificacaoServicoTipo;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipoReferencia;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 07/11/2006
 */
public class FiltrarTipoSolicitacaoEspecificacaoAction
				extends GcomAction {

	/**
	 * [UC0400] Filtrar Tipo de Solicitação com Especificações
	 * Este caso de uso cria um filtro que será usado na pesquisa de Tipo de
	 * Solicitação com Especificações
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterTipoSolicitacaoEspecificacaoAction");

		FiltrarTipoSolicitacaoEspecificacaoActionForm filtrarTipoSolicitacaoEspecificacaoActionForm = (FiltrarTipoSolicitacaoEspecificacaoActionForm) actionForm;

		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();

		boolean peloMenosUmParametroInformado = false;

		Fachada fachada = Fachada.getInstancia();

		// descrição da solicitação tipo
		if(filtrarTipoSolicitacaoEspecificacaoActionForm.getDescricao() != null
						&& !filtrarTipoSolicitacaoEspecificacaoActionForm.getDescricao().equals("")){

			peloMenosUmParametroInformado = true;

			filtroSolicitacaoTipo.adicionarParametro(new ComparacaoTexto(FiltroSolicitacaoTipo.DESCRICAO,
							filtrarTipoSolicitacaoEspecificacaoActionForm.getDescricao()));

		}
		// id do grupo de solicitação da descrição selecionada
		if(filtrarTipoSolicitacaoEspecificacaoActionForm.getIdgrupoTipoSolicitacao() != null
						&& !filtrarTipoSolicitacaoEspecificacaoActionForm.getIdgrupoTipoSolicitacao().equals("")){

			peloMenosUmParametroInformado = true;

			filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.SOLICITACAO_TIPO_GRUPO_ID,
							filtrarTipoSolicitacaoEspecificacaoActionForm.getIdgrupoTipoSolicitacao()));

		}

		// indicativo de falta d'agua
		if(filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorFaltaAgua() != null
						&& !filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorFaltaAgua().equals("3")){

			peloMenosUmParametroInformado = true;

			filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.INDICADOR_FALTA_AGUA,
							filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorFaltaAgua()));

		}
		// indicativo de tarifa solcial
		if(filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorTarifaSocial() != null
						&& !filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorTarifaSocial().equals("3")){

			peloMenosUmParametroInformado = true;

			filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.INDICADOR_TARIFA_SOCIAL,
							filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorTarifaSocial()));

		}

		// indicativo de falta d'agua
		if(filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorUso() != null
						&& !filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorUso().equals("3")){

			peloMenosUmParametroInformado = true;

			filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.INDICADOR_USO,
							filtrarTipoSolicitacaoEspecificacaoActionForm.getIndicadorUso()));

		}

		// [FS0002] – Validar Tipo de Serviço
		String idServicoTipoStr = filtrarTipoSolicitacaoEspecificacaoActionForm.getIdServicoTipo();

		if(!Util.isVazioOuBranco(idServicoTipoStr)){
			peloMenosUmParametroInformado = true;

			Collection<Integer> idsSolicitacaoTipo = new ArrayList<Integer>();

			FiltroServicoTipo filtro = new FiltroServicoTipo();
			filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipoStr));
			filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoTipo.SERVICO_TIPO_REFERENCIA);

			Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtro, ServicoTipo.class.getName());

			if(!Util.isVazioOrNulo(colecaoServicoTipo)){
				ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

				ServicoTipoReferencia servicoTipoReferencia = servicoTipo.getServicoTipoReferencia();

				if(servicoTipoReferencia != null){
					short indicadorExistenciaOsReferencia = servicoTipoReferencia.getIndicadorExistenciaOsReferencia();

					if(indicadorExistenciaOsReferencia == ConstantesSistema.SIM.shortValue()){
						throw new ActionServletException("atencao.tipo.servico.geracao.automatica");
					}
				}

				FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();
				filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(FiltroEspecificacaoServicoTipo.SERVICO_TIPO,
								idServicoTipoStr));
				filtroEspecificacaoServicoTipo
								.adicionarCaminhoParaCarregamentoEntidade(FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_SOLICITACAO_TIPO);

				Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = fachada.pesquisar(filtroEspecificacaoServicoTipo,
								EspecificacaoServicoTipo.class.getName());

				if(!Util.isVazioOrNulo(colecaoEspecificacaoServicoTipo)){
					SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
					SolicitacaoTipo solicitacaoTipo = null;
					Integer idSolicitacaoTipo = null;

					for(EspecificacaoServicoTipo especificacaoServicoTipo : colecaoEspecificacaoServicoTipo){
						solicitacaoTipoEspecificacao = especificacaoServicoTipo.getSolicitacaoTipoEspecificacao();

						if(solicitacaoTipoEspecificacao != null){
							solicitacaoTipo = solicitacaoTipoEspecificacao.getSolicitacaoTipo();

							if(solicitacaoTipo != null){
								idSolicitacaoTipo = solicitacaoTipo.getId();

								if(!idsSolicitacaoTipo.contains(idSolicitacaoTipo)){
									idsSolicitacaoTipo.add(idSolicitacaoTipo);
								}
							}
						}
					}
				}
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Serviço Tipo");
			}

			if(Util.isVazioOrNulo(idsSolicitacaoTipo)){
				idsSolicitacaoTipo.add(-1);
			}

			filtroSolicitacaoTipo.adicionarParametro(new ParametroSimplesColecao(FiltroSolicitacaoTipo.ID, idsSolicitacaoTipo));
		}

		// Erro caso o usuário mandou Pesquisar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		filtroSolicitacaoTipo.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoGrupo");

		// Verifica se o checkbox Atualizar está marcado e em caso afirmativo
		// manda pelo um request uma variável para o
		// ExibirManterFuncionalidadeAction e nele verificar se irá para o
		// atualizar ou para o manter
		if(filtrarTipoSolicitacaoEspecificacaoActionForm.getAtualizar() != null
						&& filtrarTipoSolicitacaoEspecificacaoActionForm.getAtualizar().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", filtrarTipoSolicitacaoEspecificacaoActionForm.getAtualizar());
		}

		// Manda o filtro pelo sessao para o
		// ExibirManterTipoSolicitacaoEspecificacaoAction

		sessao.setAttribute("filtroSolicitacaoTipo", filtroSolicitacaoTipo);

		httpServletRequest.setAttribute("filtroSolicitacaoTipo", filtroSolicitacaoTipo);

		return retorno;
	}
}
