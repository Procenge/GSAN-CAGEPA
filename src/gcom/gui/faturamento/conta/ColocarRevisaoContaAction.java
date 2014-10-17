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

import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.faturamento.ExecutorParametrosFaturamento;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ColocarRevisaoContaAction
				extends GcomAction
				implements Parametrizacao {

	/**
	 * @author Virgínia Melo
	 * @date 19/02/2009
	 *       Retirada verificação de erro de concorrência. Essa verificação é realizada no
	 *       controlador.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirColocarRevisaoConta");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Instância do formulário que está sendo utilizado
		ColocarRevisaoContaActionForm colocarRevisaoContaActionForm = (ColocarRevisaoContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Contas selecionadas pelo usuário
		String identificadoresConta = colocarRevisaoContaActionForm.getContaSelected();

		// MotivoRevisaoConta selecinado pelo usuário
		ContaMotivoRevisao contaMotivoRevisao = new ContaMotivoRevisao();
		contaMotivoRevisao.setId(new Integer(colocarRevisaoContaActionForm.getMotivoRevisaoContaID()));

		if(sessao.getAttribute("colecaoContaImovel") != null
						&& (identificadoresConta != null && !identificadoresConta.equalsIgnoreCase(""))){

			String[] arrayIdentificadores = identificadoresConta.split(",");

			Collection<Conta> colecaoContaImovel = (Collection) sessao.getAttribute("colecaoContaImovel");

			/*
			 * Iterator it = colecaoContaImovel.iterator();
			 * while(it.hasNext()){
			 * Conta conta = (Conta) it.next();
			 * boolean verificarConta = false;
			 * for(int i = 0; i < arrayIdentificadores.length; i++){
			 * Integer contaVerificacao = Util.obterInteger(arrayIdentificadores[i].split("-")[0]);
			 * if(conta.getId().equals(contaVerificacao)){
			 * verificarConta = true;
			 * }
			 * }
			 * if(verificarConta){
			 * // [FS0022] Validar motivo da retificação/revisão da conta.
			 * String[] parametroMotivoRetificaoNaoPermitida;
			 * try{
			 * parametroMotivoRetificaoNaoPermitida = ((String)
			 * ParametroFaturamento.P_MOTIVO_RETIFICACAO_NAO_PERMITIDA
			 * .executar(this)).split(",");
			 * }catch(ControladorException e){
			 * throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
			 * new String[e.getParametroMensagem().size()]));
			 * }
			 * boolean motivoRetificaoNaoPermitida = true;
			 * if(conta.getContaMotivoRetificacao() != null){
			 * for(int i = 0; i < parametroMotivoRetificaoNaoPermitida.length; i++){
			 * 
			 * if(parametroMotivoRetificaoNaoPermitida[i].equals(conta.getContaMotivoRetificacao().getId
			 * ().toString())){
			 * motivoRetificaoNaoPermitida = false;
			 * break;
			 * }
			 * }
			 * }
			 * if(!motivoRetificaoNaoPermitida){
			 * throw new ActionServletException("atencao.conta_em_situacao_nao_permitida",
			 * conta.getDebitoCreditoSituacaoAtual()
			 * .getDescricaoDebitoCreditoSituacao(), "ação");
			 * }
			 * }
			 * }
			 */

			Collection idsConta = new ArrayList();

			for(int i = 0; i < arrayIdentificadores.length; i++){
				String dadosConta = arrayIdentificadores[i];
				String[] idContaArray = dadosConta.split("-");
				Integer idConta = new Integer(idContaArray[0]);
				idsConta.add(idConta);
			}

			// [FS0038] - Verificar Bloqueio Colocar Conta em Revisão por Motivo de Conta Retida
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
			fachada.verificarBloqueioColocarContaRevisaoMotivoContaRetida(contaMotivoRevisao.getId(), usuarioLogado);

			// Cancelando uma ou várias contas
			fachada.colocarRevisaoConta(colecaoContaImovel, identificadoresConta, contaMotivoRevisao, usuarioLogado);

			// Realizar um reload na tela de manter conta
			httpServletRequest.setAttribute("reloadPage", "OK");
		}

		return retorno;
	}

	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosFaturamento.getInstancia();
	}
}