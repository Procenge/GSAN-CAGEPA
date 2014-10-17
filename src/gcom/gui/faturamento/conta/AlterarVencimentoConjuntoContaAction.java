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

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AlterarVencimentoConjuntoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterConjuntoConta");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		AlterarVencimentoContaActionForm alterarVencimentoContaActionForm = (AlterarVencimentoContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Data de vencimento
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		Date dataVencimentoConta;

		try{
			dataVencimentoConta = formatoData.parse(alterarVencimentoContaActionForm.getDataVencimento());
		}catch(ParseException ex){
			dataVencimentoConta = null;
		}

		if(sessao.getAttribute("colecaoImovel") != null){

			Collection colecaoImovel = (Collection) sessao.getAttribute("colecaoImovel");

			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			if(dataVencimentoConta != null && Util.getAno(dataVencimentoConta) < Util.obterAno(sistemaParametro.getAnoMesFaturamento())){
				throw new ActionServletException("atencao.data_vencimento_conta_inferior_ano_faturamento");
			}

			Integer anoMes = null;
			if(sessao.getAttribute("anoMes") != null){
				anoMes = (Integer) sessao.getAttribute("anoMes");
			}

			Integer anoMesFim = null;
			if(sessao.getAttribute("anoMesFim") != null){
				anoMesFim = (Integer) sessao.getAttribute("anoMesFim");
			}

			Date dataVencimentoContaInicio = null;
			Date dataVencimentoContaFim = null;

			if(sessao.getAttribute("dataVencimentoContaInicial") != null){

				dataVencimentoContaInicio = (Date) sessao.getAttribute("dataVencimentoContaInicial");
			}

			if(sessao.getAttribute("dataVencimentoContaFinal") != null){

				dataVencimentoContaFim = (Date) sessao.getAttribute("dataVencimentoContaFinal");
			}

			// Usuario logado no sistema
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

			// Recupera as contas selecionadas
			Collection<Conta> colecaoContasSelecionadas = new ArrayList<Conta>();

			// Carregando contas selecionadas
			String contaSelected = alterarVencimentoContaActionForm.getContaSelected();
			Collection idsConta = new ArrayList();

			if(!Util.isVazioOuBranco(contaSelected)){
				// Contas selecionadas pelo usu�rio
				String[] arrayIdentificadores = contaSelected.split(",");

				for(int i = 0; i < arrayIdentificadores.length; i++){

					String dadosConta = arrayIdentificadores[i];
					String[] idContaArray = dadosConta.split("-");
					Integer idConta = new Integer(idContaArray[0]);
					idsConta.add(idConta);

					// [FS0021] Verificar situa��o da conta
					FiltroConta filtroConta = new FiltroConta();
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LOCALIDADE);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);

					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
					Collection colecaoContas = fachada.pesquisar(filtroConta, Conta.class.getName());
					Conta contaSelecao = (Conta) colecaoContas.iterator().next();

					if(contaSelecao != null){
						if(!fachada.verificarSituacaoContaPermitida(contaSelecao)){
							throw new ActionServletException("atencao.conta_em_situacao_nao_permitida", contaSelecao
											.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao(), "a��o");
						}
					}

					colecaoContasSelecionadas.add(contaSelecao);
				}

			}

			String enderecoURL = httpServletRequest.getServletPath();

			fachada.verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(idsConta, usuarioLogado, false, false, enderecoURL);

			// Alterando a data de vencimento de uma ou mais contas
			Integer codigoCliente = null;

			if(sessao.getAttribute("codigoCliente") != null){
				codigoCliente = new Integer((String) sessao.getAttribute("codigoCliente"));
			}
			if(codigoCliente != null){
				Integer relacaoTipo = null;
				if(sessao.getAttribute("relacaoTipo") != null){
					relacaoTipo = new Integer(sessao.getAttribute("relacaoTipo") + "");
				}

				fachada.alterarVencimentoConjuntoContaCliente(codigoCliente, relacaoTipo, dataVencimentoConta, anoMes,
								dataVencimentoContaInicio, dataVencimentoContaFim, anoMesFim, usuarioLogado, colecaoContasSelecionadas);
			}else{

				fachada.alterarVencimentoConjuntoConta(colecaoImovel, dataVencimentoConta, anoMes, dataVencimentoContaInicio,
								dataVencimentoContaFim, anoMesFim, usuarioLogado, colecaoContasSelecionadas);
			}

			// Realizar um reload na tela de manter conjunto conta
			httpServletRequest.setAttribute("reloadPage", "OK");

		}

		sessao.removeAttribute("anoMes");
		return retorno;
	}

}
