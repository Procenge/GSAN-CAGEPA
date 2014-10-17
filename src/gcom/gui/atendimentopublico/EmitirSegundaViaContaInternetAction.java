/**
 * 
 */
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

package gcom.gui.atendimentopublico;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 17/01/2007
 */
public class EmitirSegundaViaContaInternetAction
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

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirResultadoEmitirSegundaViaContaInternetAction");

		HttpSession sessao = httpServletRequest.getSession(true);

		if(sessao.getAttribute("acessoGeral") != null){
			retorno = actionMapping.findForward("exibirResultadoEmitirSegundaViaContaInternetAcessoGeralAction");

		}
		// Action Base ExibirEfetuarParcelamentoDebitosProcesso1Action
		SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute("sistemaParametro");

		EmitirSegundaViaContaInternetActionForm form = (EmitirSegundaViaContaInternetActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Imovei testado 11/05/2007 15675756

		Integer matricula = new Integer(form.getMatricula());

		// Verificar existencia do im�vel

		Integer matriculaExistente = fachada.verificarExistenciaImovel(matricula);

		Collection colecaoLocalidadeElo = null;

		if(matriculaExistente == 1){

			form.setMatricula(matricula.toString());

			Imovel imovel = fachada.pesquisarImovel(new Integer(matricula));

			Integer idLocalidade = imovel.getLocalidade().getId();

			colecaoLocalidadeElo = this.pesquisarLocalidade(idLocalidade.toString());

			Localidade localidade = null;

			if(colecaoLocalidadeElo != null && !colecaoLocalidadeElo.isEmpty())

			localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidadeElo);
			// Localidade � o Elo
			if(localidade.getId().intValue() == localidade.getLocalidade().getId().intValue()){

				form.setElo(localidade.getDescricao());

			}else{
				// Localidade nao � o Elo
				colecaoLocalidadeElo = this.pesquisarLocalidade(localidade.getLocalidade().getId().toString());

				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidadeElo);

				form.setElo(localidade.getDescricao());

			}

			// Data do Debito

			/**
			 * Alterado por R�mulo Aur�lio
			 * Data: 11/05/2007
			 */

			BigDecimal valorTotalAcrescimoImpontualidadeGuias = new BigDecimal("0.00");
			BigDecimal totalContas = new BigDecimal("0.00");
			BigDecimal totalAcrescimosImpontualidade = new BigDecimal("0.00");
			BigDecimal valorTotalAcrescimoImpontualidadeContas = new BigDecimal("0.00");

			Short nDiasVencimentoCobranca = sistemaParametro.getNumeroDiasVencimentoCobranca();

			Date dataDebito = new Date();

			Calendar calendar = Calendar.getInstance();
			// Data Atual - Numero de dias vencimento Cobranca
			calendar.add(Calendar.DAY_OF_MONTH, -nDiasVencimentoCobranca.shortValue());

			dataDebito = calendar.getTime();

			form.setDataDebito(Util.formatarData(dataDebito));
			// Data do Debito

			// Ano mes Atual.
			String ano;
			String mes;

			Date dataCorrente = new Date();
			String dataCorrenteTexto = Util.formatarData(dataCorrente);
			ano = dataCorrenteTexto.substring(6, 10);
			mes = dataCorrenteTexto.substring(3, 5);

			String anoMesInicialReferenciaDebito = "198501";

			String anoMesFinalReferenciaDebito = ano + mes;

			// data inicio vencimento debito
			Calendar dataInicioVencimentoDebito = new GregorianCalendar();
			dataInicioVencimentoDebito.set(Calendar.YEAR, new Integer("1985").intValue());
			dataInicioVencimentoDebito.set(Calendar.MONTH, 0);
			dataInicioVencimentoDebito.set(Calendar.DATE, new Integer("01").intValue());

			// data final de vencimento de debito

			String anoMesFimVencimentoDebito = "999912";

			Calendar dataFimVencimentoDebito = new GregorianCalendar();
			dataFimVencimentoDebito.set(Calendar.YEAR, new Integer(anoMesFimVencimentoDebito.substring(0, 4)).intValue());
			dataFimVencimentoDebito.set(Calendar.MONTH, new Integer(anoMesFimVencimentoDebito.substring(4, 6)).intValue());
			dataFimVencimentoDebito.set(Calendar.DAY_OF_MONTH, dataFimVencimentoDebito.getMaximum(Calendar.DAY_OF_MONTH));

			// dataVencimentoFinal = "31/12/9999"

			Date aux1 = dataInicioVencimentoDebito.getTime();

			Date aux2 = dataFimVencimentoDebito.getTime();

			String tipoRelacao = "-1";

			ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = (ObterDebitoImovelOuClienteHelper) fachada
							.obterDebitoImovelOuCliente(1, form.getMatricula(), null, Integer.valueOf(tipoRelacao),
											anoMesInicialReferenciaDebito, anoMesFinalReferenciaDebito, aux1, aux2, 1, 1, 1, 1, 1, 1, 1,
											null, sistemaParametro, null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
											ConstantesSistema.SIM);
			Collection colecaoContasValores = obterDebitoImovelOuClienteHelper.getColecaoContasValores();
			if(colecaoContasValores == null || colecaoContasValores.isEmpty()){
				throw new ActionServletException("atencao.imovel_sem_debitos");
			}else{

				Iterator colecaoContasValoresIterator = colecaoContasValores.iterator();

				while(colecaoContasValoresIterator.hasNext()){
					ContaValoresHelper contaValoresHelper = (ContaValoresHelper) colecaoContasValoresIterator.next();
					totalContas = totalContas.add(contaValoresHelper.getValorTotalConta());
					valorTotalAcrescimoImpontualidadeContas = valorTotalAcrescimoImpontualidadeContas.add(contaValoresHelper
									.getValorTotalContaValores());

				}

				httpServletRequest.setAttribute("totalContas", totalContas);

				form.setValorDebito(Util.formatarMoedaReal(totalContas));

			}

			httpServletRequest.setAttribute("colecaoContasValores", colecaoContasValores);

			form.setNomeCliente(fachada.pesquisarNomeClientePorImovel(matricula));

			/**
			 * Valor do debito a cobrar
			 */

			/*
			 * // Guias de Pagamento
			 * Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresImovel =
			 * obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores();
			 * if (colecaoGuiaPagamentoValoresImovel != null &&
			 * !colecaoGuiaPagamentoValoresImovel.isEmpty()) {
			 * Iterator guiaPagamentoValores = colecaoGuiaPagamentoValoresImovel.iterator();
			 * while (guiaPagamentoValores.hasNext()) {
			 * GuiaPagamentoValoresHelper guiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper)
			 * guiaPagamentoValores.next();
			 * // Para c�lculo do Acrescimo de Impontualidade
			 * if (guiaPagamentoValoresHelper.getValorAcrescimosImpontualidade() != null
			 * && !guiaPagamentoValoresHelper.getValorAcrescimosImpontualidade().equals("")) {
			 * valorTotalAcrescimoImpontualidadeGuias.setScale(
			 * Parcelamento.CASAS_DECIMAIS,Parcelamento.TIPO_ARREDONDAMENTO);
			 * valorTotalAcrescimoImpontualidadeGuias = valorTotalAcrescimoImpontualidadeGuias.
			 * add(guiaPagamentoValoresHelper.getValorAcrescimosImpontualidade());
			 * }
			 * }
			 * }
			 */

			ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(1, // Indicador
							// d�bito
							// im�vel
							matricula.toString(), // Matr�cula do im�vel
							null, // C�digo do cliente
							null, // Tipo de rela��o do cliento com o
							// im�vel
							"000101", // Refer�ncia inicial do d�bito
							"999912", // Refer�ncia final do d�bito
							Util.converteStringParaDate("01/01/0001"), // Inicio
							// Vencimento
							Util.converteStringParaDate("31/12/9999"), // Final
							// Vencimento
							1, // Indicador pagamento
							1, // Indicador conta em revis�o
							1, // Indicador d�bito a cobrar
							1, // Indicador cr�dito a realizar
							1, // Indicador notas promiss�rias
							1, // Indicador guias de pagamento
							1, // Indicador acr�scimos por impontualidade
							null,// Indicador Contas
							sistemaParametro, null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM); // sistemaParametro

			Collection colecaoDebitoACobrar = colecaoDebitoImovel.getColecaoDebitoACobrar();

			BigDecimal valorTotalDebitoACobrar = new BigDecimal("0.00");

			if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
				Iterator debitoACobrarValores = colecaoDebitoACobrar.iterator();

				while(debitoACobrarValores.hasNext()){

					DebitoACobrar debitoACobrar = (DebitoACobrar) debitoACobrarValores.next();

					valorTotalDebitoACobrar = valorTotalDebitoACobrar.add(debitoACobrar.getValorTotal());

				}

			}

			// Acrescimos por Impotualidade
			BigDecimal retornoSoma = new BigDecimal("0.00");
			retornoSoma = retornoSoma.add(valorTotalAcrescimoImpontualidadeContas);
			valorTotalDebitoACobrar = valorTotalDebitoACobrar.add(retornoSoma);

			form.setDebitoACobrar(Util.formatarMoedaReal(valorTotalDebitoACobrar));

		}else{
			throw new ActionServletException("atencao.matricula.imovel.inexistente");
		}
		return retorno;
	}

	public Collection pesquisarLocalidade(String idLocalidade){

		Collection colecaoLocalidadeElo = null;

		FiltroLocalidade filtrolocalidade = new FiltroLocalidade();

		filtrolocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

		colecaoLocalidadeElo = Fachada.getInstancia().pesquisar(filtrolocalidade, Localidade.class.getName());

		return colecaoLocalidadeElo;
	}
}
