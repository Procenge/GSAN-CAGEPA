/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimentoUnidade;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ConsultarImovelRegistroAtendimentoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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

/**
 * Action respons�vel pela pre-exibi��o da pagina de inserir bairro
 * 
 * @author Thiago Ten�rio
 * @created 28 de Junho de 2004
 */
public class ExibirConsultarRegistroAtendimentoPendentesImovelAction
				extends GcomAction {

	/**
	 * Description of the Method
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

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarRegistroAtendimentoPendentesImovel");
		ConsultarRegistroAtendimentoPendentesImovelActionForm atendimentoPendentesImovelActionForm = (ConsultarRegistroAtendimentoPendentesImovelActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Variavel responsav�l pelo preenchimento do imovel no formul�rio
		/*
		 * String idOrdemServico = atendimentoPendentesImovelActionForm
		 * .getIdOrdemServico();
		 */

		String idImovel = httpServletRequest.getParameter("idImovel");
		String situacao = httpServletRequest.getParameter("situacao");

		sessao.removeAttribute("enderecoImovel");
		sessao.removeAttribute("colecaoConsultarImovelRegistroAtendimentoHelper");

		if(idImovel != null && !idImovel.trim().equals("")){

			Imovel imovelSelecionado = fachada.pesquisarImovelRegistroAtendimento(Util.converterStringParaInteger(idImovel));

			if(imovelSelecionado != null){

				Collection colecaoConsultarImovelRegistroAtendimentoHelper = null;
				Collection colecaoRegistroAtendimento = null;

				if(situacao != null && !situacao.equalsIgnoreCase("")){
					colecaoRegistroAtendimento = fachada.consultarRegistroAtendimentoImovel(new Integer(idImovel), situacao);
				}else{
					colecaoRegistroAtendimento = fachada.consultarRegistroAtendimentoImovel(new Integer(idImovel), null);
				}

				if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){

					Iterator iteratorColecaoRegistroAtendimento = colecaoRegistroAtendimento.iterator();

					colecaoConsultarImovelRegistroAtendimentoHelper = new ArrayList();

					while(iteratorColecaoRegistroAtendimento.hasNext()){
						RegistroAtendimento registroAtendimento = (RegistroAtendimento) iteratorColecaoRegistroAtendimento.next();

						ConsultarImovelRegistroAtendimentoHelper consultarImovelRegistroAtendimentoHelper = new ConsultarImovelRegistroAtendimentoHelper();

						// id registro atendimento
						if(registroAtendimento != null && registroAtendimento.getId() != null){
							consultarImovelRegistroAtendimentoHelper.setIdRegistroAtendimento(registroAtendimento.getId().toString());
						}

						// tipo de solicita��o
						if(registroAtendimento != null && registroAtendimento.getSolicitacaoTipoEspecificacao() != null
										&& registroAtendimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo() != null){

							consultarImovelRegistroAtendimentoHelper.setTipoSolicitacao(registroAtendimento
											.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getDescricao());
						}

						// especifica��o
						if(registroAtendimento != null && registroAtendimento.getSolicitacaoTipoEspecificacao() != null){
							consultarImovelRegistroAtendimentoHelper.setEspecificacao(registroAtendimento.getSolicitacaoTipoEspecificacao()
											.getDescricao());
						}

						// data de atendimento
						if(registroAtendimento != null && registroAtendimento.getRegistroAtendimento() != null){
							consultarImovelRegistroAtendimentoHelper.setDataAtendimento(Util.formatarData(registroAtendimento
											.getRegistroAtendimento()));
						}

						if(registroAtendimento != null && registroAtendimento.getId() != null){

							// situacao
							ObterDescricaoSituacaoRAHelper obterDescricaoSituacaoRAHelper = fachada
											.obterDescricaoSituacaoRA(registroAtendimento.getId());
							consultarImovelRegistroAtendimentoHelper.setSituacao(obterDescricaoSituacaoRAHelper
											.getDescricaoAbreviadaSituacao());

							// Unidade de Atendimento
							FiltroRegistroAtendimentoUnidade filtroRegistroAtendimentoUnidade = new FiltroRegistroAtendimentoUnidade();
							filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(
											FiltroRegistroAtendimentoUnidade.REGISTRO_ATENDIMENTO_ID, registroAtendimento.getId()));
							filtroRegistroAtendimentoUnidade
											.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoUnidade.UNIDADE_ORGANIZACIONAL);

							Collection colecaoRegistroAtendimentoUnidade = fachada.pesquisar(filtroRegistroAtendimentoUnidade,
											RegistroAtendimentoUnidade.class.getName());

							if(colecaoRegistroAtendimentoUnidade != null){
								RegistroAtendimentoUnidade registroAtendimentoUnidade = (RegistroAtendimentoUnidade) Util
												.retonarObjetoDeColecao(colecaoRegistroAtendimentoUnidade);

								if(registroAtendimentoUnidade != null && registroAtendimentoUnidade.getUnidadeOrganizacional() != null){
									consultarImovelRegistroAtendimentoHelper.setUnidadeAtendimento(registroAtendimentoUnidade
													.getUnidadeOrganizacional().getSigla());
								}

							}

						}

						colecaoConsultarImovelRegistroAtendimentoHelper.add(consultarImovelRegistroAtendimentoHelper);
					}

					sessao.setAttribute("colecaoConsultarImovelRegistroAtendimentoHelper", colecaoConsultarImovelRegistroAtendimentoHelper);
				}else{
					throw new ActionServletException("atencao.imovel_sem_ra_pendente");
				}

				atendimentoPendentesImovelActionForm.setMatriculaImovel(imovelSelecionado.getId().toString());
				atendimentoPendentesImovelActionForm.setInscricaoImovel(imovelSelecionado.getInscricaoFormatada());
				atendimentoPendentesImovelActionForm.setSituacaoLigacaoAgua(imovelSelecionado.getLigacaoAguaSituacao().getDescricao());
				atendimentoPendentesImovelActionForm.setSituacaoLigacaoEsgoto(imovelSelecionado.getLigacaoEsgotoSituacao().getDescricao());

				httpServletRequest.setAttribute("enderecoImovel", imovelSelecionado.getEnderecoFormatado());
			}
		}

		return retorno;
	}
}