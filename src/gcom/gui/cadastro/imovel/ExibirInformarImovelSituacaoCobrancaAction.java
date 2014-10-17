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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirInformarImovelSituacaoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInformarImovelSituacaoCobranca");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		// HttpSession sessao = httpServletRequest.getSession(false);

		InformarImovelSituacaoCobrancaActionForm informarImovelSituacaoCobrancaActionForm = (InformarImovelSituacaoCobrancaActionForm) actionForm;

		if(httpServletRequest.getParameter("objetoConsulta") != null){
			Integer idImovel = new Integer(informarImovelSituacaoCobrancaActionForm.getIdImovel());
			if(idImovel != null){
				String matriculaImovel = fachada.pesquisarInscricaoImovel(idImovel, true);
				Imovel imovel = fachada.pesquisarImovelComSituacaoAguaEsgoto(idImovel);
				httpServletRequest.setAttribute("imovel", imovel);
				if(matriculaImovel != null && !matriculaImovel.equalsIgnoreCase("")){
					String enderecoImovel = fachada.pesquisarEndereco(idImovel);
					httpServletRequest.setAttribute("endereco", enderecoImovel);
					httpServletRequest.setAttribute("matriculaImovel", matriculaImovel);

					FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, idImovel
									.toString()));
					filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
					filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cliente");
					filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoAguaSituacao");
					filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoEsgotoSituacao");
					Collection colecaoImovelCobrancaSituacao = fachada.pesquisar(filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class
									.getName());

					/**
					 * [UC0490] Informar Situa��o de Cobran�a
					 * [FS0001] - Verificar situa��o de cobran�a do im�vel
					 */
					if(colecaoImovelCobrancaSituacao != null && !colecaoImovelCobrancaSituacao.isEmpty()){
						httpServletRequest.setAttribute("situacoes", colecaoImovelCobrancaSituacao);

						for(Iterator iter = colecaoImovelCobrancaSituacao.iterator(); iter.hasNext();){
							ImovelCobrancaSituacao imovelCobrancaSituacao = (ImovelCobrancaSituacao) iter.next();

							/*
							 * Caso o im�vel possua situa��o de cobran�a n�o encerrada (existe
							 * ocorr�ncia de situa��o de cobran�a para o im�vel com
							 * ISCB_DTRETIRADACOBRANCA com valor igual a nulo):
							 */
							if(imovelCobrancaSituacao.getDataRetiradaCobranca() == null){

								/*
								 * Caso a retirada da situa��o de cobran�a do im�vel n�o esteja
								 * bloqueada (CBST_ICBLOQUEIRETIRADA com o valor correspondente a
								 * 2-dois
								 * para CBST_ID=CBST_ID da tabela IMOVEL_COBRANCA_SITUACAO),
								 */
								if(imovelCobrancaSituacao.getCobrancaSituacao() != null
												&& imovelCobrancaSituacao.getCobrancaSituacao().getIndicadorBloqueioRetirada() != null
												&& imovelCobrancaSituacao.getCobrancaSituacao().getIndicadorBloqueioRetirada().intValue() == ConstantesSistema.NAO
																.intValue()){
									// habilitar a op��o de Retirar situa��o de cobran�a do im�vel
									httpServletRequest.removeAttribute("escondeRetirar");
									break;
								}else{
									// caso contr�rio, desabilitar a op��o de Retirar situa��o de
									// cobran�a do im�vel.
									httpServletRequest.setAttribute("escondeRetirar", "sim");
								}

							}else{
								/*
								 * . Caso contr�rio, desabilitar a op��o de Retirar situa��o de
								 * cobran�a
								 * do im�vel e habilitar a op��o Inserir situa��o de cobran�a para o
								 * im�vel.
								 */
								httpServletRequest.setAttribute("escondeRetirar", "sim");
							}
						}

					}else{
						httpServletRequest.setAttribute("escondeRetirar", "sim");
					}

				}else{
					httpServletRequest.setAttribute("inexistente", 1);
				}
			}
		}else{

			httpServletRequest.setAttribute("escondeRetirar", "sim");
		}

		return retorno;

	}

}
