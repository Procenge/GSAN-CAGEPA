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

package gcom.gui.faturamento.conta;

import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Anderson Italo
 * @date 06/12/2013
 */
public class RetirarRevisaoConjuntoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterContaConjuntoImovel");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		Fachada fachada = Fachada.getInstancia();
		ManterContaConjuntoImovelActionForm manterContaConjuntoImovelActionForm = (ManterContaConjuntoImovelActionForm) actionForm;
		
		boolean temPermissaoAtualizarDebitosExecFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.ATUALIZAR_DEBITOS_EXECUCAO_FISCAL, this.getUsuarioLogado(httpServletRequest));		

		// Carregando o identificador das contas selecionadas
		String identificadoresConta = manterContaConjuntoImovelActionForm.getContaSelected();

		Collection idsConta = new ArrayList();

		String[] arrayIdentificadores = identificadoresConta.split(",");
		StringBuffer parametroExecucaoFiscal = new StringBuffer();

		for(int i = 0; i < arrayIdentificadores.length; i++){

			String dadosConta = arrayIdentificadores[i];
			String[] idUltimaAlteracao = dadosConta.split("-");
			Integer idConta = new Integer(idUltimaAlteracao[0]);

			// [FS0021] Verificar situação da conta
			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
			filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);
			filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITOS_COBRADOS);
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
			Collection colecaoConta = fachada.pesquisar(filtroConta, Conta.class.getName());
			Conta contaSelecao = (Conta) colecaoConta.iterator().next();

			if(contaSelecao != null){

				if(!fachada.verificarSituacaoContaPermitida(contaSelecao)){

					throw new ActionServletException("atencao.conta_em_situacao_nao_permitida", contaSelecao
									.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao(), "ação");
				}
				
				/**
				 * [UC0146] Manter Conta
				 * [SB0040] Verificar Existência de Conta em Execução Fiscal
				 * 
				 * @author Gicevalter Couto
				 * @date 07/08/2014
				 * @param colecaoContas
				 */

				ContaValoresHelper contaValores = new ContaValoresHelper();
				contaValores.setConta(contaSelecao);

				Collection<ContaValoresHelper> colecaoContaValores = new ArrayList<ContaValoresHelper>();
				colecaoContaValores.add(contaValores);

				if(fachada.verificarExecucaoFiscal(colecaoContaValores, null, null) && !temPermissaoAtualizarDebitosExecFiscal){
					parametroExecucaoFiscal.append(contaSelecao.getReferenciaFormatada());
				}
									
			}

			idsConta.add(idConta);
		}

		String parametroMensagemExecFiscal = parametroExecucaoFiscal.toString();

		if(!Util.isVazioOuBranco(parametroMensagemExecFiscal)){
			parametroMensagemExecFiscal = parametroMensagemExecFiscal.substring(0, parametroMensagemExecFiscal.length() - 2);

			throw new ActionServletException("atencao.conta.debito.execucao.fiscal", usuarioLogado.getNomeUsuario().toString(),
							parametroMensagemExecFiscal);
		}

		// [FS0017] Verificar ocorrência de conta(s) em revisão por ação do usuário
		Collection<Conta> contasRevisaoAcaoUsuario = fachada.obterContasEmRevisaoPorAcaoUsuario(idsConta);

		String enderecoURL = httpServletRequest.getServletPath();
		if(idsConta != null && !idsConta.isEmpty()){

			fachada.verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(idsConta, usuarioLogado, false, false, enderecoURL);
		}

		if(identificadoresConta != null && !identificadoresConta.equalsIgnoreCase("")){

			// Retirando de revisão uma ou mais contas
			fachada.retirarRevisaoConta(contasRevisaoAcaoUsuario, identificadoresConta, usuarioLogado);
		}

		return retorno;
	}
}