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
	 * @author Virg�nia Melo
	 * @date 19/02/2009
	 *       Retirada verifica��o de erro de concorr�ncia. Essa verifica��o � realizada no
	 *       controlador.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirColocarRevisaoConta");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		ColocarRevisaoContaActionForm colocarRevisaoContaActionForm = (ColocarRevisaoContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Contas selecionadas pelo usu�rio
		String identificadoresConta = colocarRevisaoContaActionForm.getContaSelected();

		// MotivoRevisaoConta selecinado pelo usu�rio
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
			 * // [FS0022] Validar motivo da retifica��o/revis�o da conta.
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
			 * .getDescricaoDebitoCreditoSituacao(), "a��o");
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

			// [FS0038] - Verificar Bloqueio Colocar Conta em Revis�o por Motivo de Conta Retida
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
			fachada.verificarBloqueioColocarContaRevisaoMotivoContaRetida(contaMotivoRevisao.getId(), usuarioLogado);

			// Cancelando uma ou v�rias contas
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