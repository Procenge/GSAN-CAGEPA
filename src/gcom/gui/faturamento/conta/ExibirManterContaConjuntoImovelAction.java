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
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0407] Filtrar Im�veis para Inserir ou Manter Conta
 * 
 * @author Ana Maria
 * @created 16/03/2007
 */
public class ExibirManterContaConjuntoImovelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("manterContaConjuntoImovel");

		HttpSession sessao = httpServletRequest.getSession(false);

		ManterContaConjuntoImovelActionForm manterContaConjuntoImovelActionForm = (ManterContaConjuntoImovelActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		try{
			// Par�metro utilizado para decidir se o bot�o
			// "Retirar de �gua ou Esgoto de uma ou mais conta" ser� exibido.
			String paramPermitirRetirarValorAguaEsgotoConta = (String) ParametroFaturamento.P_PERMITIR_RETIRAR_VALOR_AGUA_ESGOTO_CONTA
							.executar();
			sessao.setAttribute("paramPermitirRetirarValorAguaEsgotoConta", paramPermitirRetirarValorAguaEsgotoConta);
		}catch(ControladorException e){
			e.printStackTrace();
		}

		/*
		 * Alterar Respons�vel de uma ou mais contas [SB0015 - Alterar Cliente Respons�vel de um
		 * Conjunto de Contas] (exibir esse op��o apenas se o par�metro
		 * P_PERMITIR_ALTERAR_CLIENTE_CONTA tiver valor 1 - "SIM")
		 */
		String pPermitirAlterarClienteConta = null;

		try{

			pPermitirAlterarClienteConta = (String) ParametroFaturamento.P_PERMITIR_ALTERAR_CLIENTE_CONTA.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		if(Util.isNaoNuloBrancoZero(pPermitirAlterarClienteConta) && pPermitirAlterarClienteConta.equals(ConstantesSistema.SIM.toString())){

			sessao.setAttribute("permitirAlterarClienteConta", true);
		}else{

			sessao.removeAttribute("permitirAlterarClienteConta");
		}

		Collection colecaoImovel = new ArrayList();

		if(sessao.getAttribute("colecaoImovel") != null){
			colecaoImovel = (Collection) sessao.getAttribute("colecaoImovel");
		}

		// Limpa dados da tabela da sess�o
		sessao.removeAttribute("colecaoContaImovel");
		sessao.removeAttribute("valorAgua");
		sessao.removeAttribute("valorEsgoto");
		sessao.removeAttribute("valorDebito");
		sessao.removeAttribute("valorCredito");
		sessao.removeAttribute("valorImposto");
		sessao.removeAttribute("valorConta");

		// Seta o valor inicial fixo para contas em revis�o
		if(manterContaConjuntoImovelActionForm.getInContasRevisao() == null){
			manterContaConjuntoImovelActionForm.setInContasRevisao(ConstantesSistema.NAO.toString());
		}

		manterContaConjuntoImovelActionForm.setQuatidadeImovel(Integer.toString(colecaoImovel.size()));

		// Valida��o para quantidade de contas OC1090191
		int limiteQtdeImoveis = 0;

		try{
			String pLimiteQtdImoveis = ParametroFaturamento.P_LIMITE_QTD_IMOVEIS_MANTER_CONTA_CONJUNTO_IMOVEIS.executar();

			if(pLimiteQtdImoveis != null){
				limiteQtdeImoveis = Util.converterStringParaInteger(pLimiteQtdImoveis);
			}
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema.inexistente");
		}

		// Se o par�metro de sistema informa um limite para a quantidade de im�veis, o sistema
		// verifica. Caso contr�rio, n�o restringe a quantidade.
		if(limiteQtdeImoveis > 0){
			
			if(colecaoImovel.size() >= limiteQtdeImoveis){
				ActionServletException actionServletException = new ActionServletException(
								"atencao.filtro_quantidade_registros_ultrapassa_limite", String.valueOf(limiteQtdeImoveis));

				actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelInserirManterContaAction.do");

				throw actionServletException;
			}
		}

		Date dataVencimentoContaInicio = null;
		Date dataVencimentoContaFim = null;

		if(manterContaConjuntoImovelActionForm.getDataVencimentoInicial() != null
						&& !manterContaConjuntoImovelActionForm.getDataVencimentoInicial().equals("")){

			dataVencimentoContaInicio = Util.converteStringParaDate(manterContaConjuntoImovelActionForm.getDataVencimentoInicial());
		}

		if(manterContaConjuntoImovelActionForm.getDataVencimentoFinal() != null
						&& !manterContaConjuntoImovelActionForm.getDataVencimentoFinal().equals("")){

			dataVencimentoContaFim = Util.converteStringParaDate(manterContaConjuntoImovelActionForm.getDataVencimentoFinal());
		}

		// Integer tipoPesquisaConta = 0;
		// Cliente Im�vel
		if(sessao.getAttribute("codigoCliente") != null && sessao.getAttribute("nomeCliente") != null){
			manterContaConjuntoImovelActionForm.setCodigoCliente((String) sessao.getAttribute("codigoCliente"));
			manterContaConjuntoImovelActionForm.setNomeCliente((String) sessao.getAttribute("nomeCliente"));
			httpServletRequest.setAttribute("cliente", "ok");
			// tipoPesquisaConta = 1;
		}

		// Inscri��o Im�vel
		if(sessao.getAttribute("inscricaoInicialImovel") != null){
			manterContaConjuntoImovelActionForm.setInscricaoInicial((String) sessao.getAttribute("inscricaoInicialImovel"));
		}else{
			manterContaConjuntoImovelActionForm.setInscricaoInicial("");
		}

		if(sessao.getAttribute("inscricaoDestinoImovel") != null){
			manterContaConjuntoImovelActionForm.setInscricaoFinal((String) sessao.getAttribute("inscricaoDestinoImovel"));
		}else{
			manterContaConjuntoImovelActionForm.setInscricaoFinal("");
		}

		String idGrupoFaturamentoSessao = (String) sessao.getAttribute("grupoFaturamento");
		Integer idGrupoFaturamento = null;

		if(idGrupoFaturamentoSessao != null && !idGrupoFaturamentoSessao.equals("")){
			manterContaConjuntoImovelActionForm.setIdGrupoFaturamento(idGrupoFaturamentoSessao);
			idGrupoFaturamento = new Integer(idGrupoFaturamentoSessao);
		}else{
			manterContaConjuntoImovelActionForm.setIdGrupoFaturamento("");
		}

		Integer quantidadeConta = 0;
		Integer codigoCliente = null;
		if(sessao.getAttribute("codigoCliente") != null){
			codigoCliente = new Integer((String) sessao.getAttribute("codigoCliente"));
		}

		Integer relacaoTipo = null;
		if(sessao.getAttribute("relacaoTipo") != null){
			relacaoTipo = new Integer(sessao.getAttribute("relacaoTipo") + "");
		}

		// Limpa os motivos de revis�o
		if(sessao.getAttribute("telaFiltroImoveisConta") != null
						&& !Util.isVazioOrNulo(manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis())){
			manterContaConjuntoImovelActionForm.limparMotivosRevisaoDisponiveisComoInteger();
		}

		// Pesquisar os motivos para conta em revis�o
		this.pesquisarContaMotivoRevisao(httpServletRequest);
		
		if(!Util.isVazioOrNulo(manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis())){
			// Tratar para retirar op��o "vazia" da lista de motivos
			ArrayList<Integer> listaMotivos = new ArrayList(
							Arrays.asList(manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis()));
			Integer contaMotivoRevisaoPrimeiroElemento = (Integer) Util.retonarObjetoDeColecao(listaMotivos);
			if(Util.isVazioOuBrancoOuZero(contaMotivoRevisaoPrimeiroElemento)){
				listaMotivos.remove(contaMotivoRevisaoPrimeiroElemento);
			}

			if(!Util.isVazioOrNulo(listaMotivos)){
				String[] arrayMotivos = new String[listaMotivos.size()];
				Object[] arrayMotivosObject = listaMotivos.toArray();
				for(int i = 0; i < listaMotivos.size(); i++){
					arrayMotivos[i] = arrayMotivosObject[i].toString();
				}

				manterContaConjuntoImovelActionForm.setMotivosRevisaoDisponiveisComoInteger(arrayMotivos);
			}else{
				manterContaConjuntoImovelActionForm.limparMotivosRevisaoDisponiveisComoInteger();
			}

		}
		


		if(httpServletRequest.getParameter("quantidadeConta") != null){


			Integer anoMes = Util.formatarMesAnoComBarraParaAnoMes(manterContaConjuntoImovelActionForm.getMesAnoConta());
			Integer anoMesFinal = Util.formatarMesAnoComBarraParaAnoMes(manterContaConjuntoImovelActionForm.getMesAnoContaFinal());

			String inContasRevisao = manterContaConjuntoImovelActionForm.getInContasRevisao();

			Integer[] motivosRevisaoDisponiveis = null;

			if(!Util.isVazioOuBrancoOuZero(manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis())){
				motivosRevisaoDisponiveis = new Integer[manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis().length];

				if(!Util.isVazioOrNuloOuMenosUm(manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis())){
					int indice = 0;
					for(Integer valorMotivoRevisao : manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis()){
						motivosRevisaoDisponiveis[indice] = valorMotivoRevisao;

						indice++;
					}
				}
			}

			// Chama o m�todo obter contas conjunto de im�veis
			// e adiciona a quantidade de contas ao form
			quantidadeConta = fachada.obterContasConjuntoImoveis(anoMes, colecaoImovel, codigoCliente, relacaoTipo,
							dataVencimentoContaInicio, dataVencimentoContaFim, idGrupoFaturamento, anoMesFinal, inContasRevisao,
							motivosRevisaoDisponiveis);

			// Se o par�metro de sistema informa um limite para a quantidade de im�veis, o sistema
			// verifica. Caso contr�rio, n�o restringe a quantidade.
			if(limiteQtdeImoveis > 0){

				if(quantidadeConta >= limiteQtdeImoveis){
					ActionServletException actionServletException = new ActionServletException(
									"atencao.filtro_quantidade_registros_ultrapassa_limite", String.valueOf(limiteQtdeImoveis));
					actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelInserirManterContaAction.do");
					throw actionServletException;

				}
			}

			manterContaConjuntoImovelActionForm.setQuatidadeConta(quantidadeConta.toString());


			// Envia a cole��o de contas a serem exibidas no grid
			Collection<Conta> colecaoContaImovel = fachada.recuperarContasConjuntoImoveis(anoMes, colecaoImovel, codigoCliente,
							relacaoTipo, dataVencimentoContaInicio, dataVencimentoContaFim, idGrupoFaturamento, anoMesFinal,
							inContasRevisao, motivosRevisaoDisponiveis);

			// Acumuladores dos valores
			BigDecimal valorTotalAgua = BigDecimal.ZERO;
			BigDecimal valorTotalEsgoto = BigDecimal.ZERO;
			BigDecimal valorTotalDebito = BigDecimal.ZERO;
			BigDecimal valorTotalCredito = BigDecimal.ZERO;
			BigDecimal valorTotalImpostos = BigDecimal.ZERO;
			BigDecimal valorTotalContas = BigDecimal.ZERO;

			for(Conta conta : colecaoContaImovel){

				valorTotalAgua = valorTotalAgua.add(conta.getValorAgua());
				valorTotalEsgoto = valorTotalEsgoto.add(conta.getValorEsgoto());
				valorTotalDebito = valorTotalDebito.add(conta.getDebitos());
				valorTotalCredito = valorTotalCredito.add(conta.getValorCreditos());
				valorTotalImpostos = valorTotalImpostos.add(conta.getValorImposto());
				valorTotalContas = valorTotalContas.add(Util.formatarMoedaRealparaBigDecimal(conta.getValorTotalConta()));
			}

			// Ordenando a cole��o por m�s/ano de refer�ncia descendente
			List<Conta> colecaoContasOrdenadaImovel = new ArrayList<Conta>();
			colecaoContasOrdenadaImovel.addAll(colecaoContaImovel);
			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("referencia"));
			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort((List) colecaoContasOrdenadaImovel, multiSort);
			Collections.reverse(colecaoContasOrdenadaImovel);

			// Adiciona cole��o na sess�o
			sessao.setAttribute("colecaoContaImovel", colecaoContasOrdenadaImovel);

			// Adiciona acumuladores na sess�o
			sessao.setAttribute("valorAgua", Util.formatarMoedaReal(valorTotalAgua));
			sessao.setAttribute("valorEsgoto", Util.formatarMoedaReal(valorTotalEsgoto));
			sessao.setAttribute("valorDebito", Util.formatarMoedaReal(valorTotalDebito));
			sessao.setAttribute("valorCredito", Util.formatarMoedaReal(valorTotalCredito));
			sessao.setAttribute("valorImposto", Util.formatarMoedaReal(valorTotalImpostos));
			sessao.setAttribute("valorConta", Util.formatarMoedaReal(valorTotalContas));

		}else if(sessao.getAttribute("retornoArquivo") != null){
			if(sessao.getAttribute("arquivoQuantidadeContas") == null){
				throw new ActionServletException("atencao.pesquisa.conta.inexistente",
								"/gsan/exibirFiltrarImovelInserirManterContaAction.do?menu=sim", "");
			}
			Integer arquivoQuantidadeConta = (Integer) sessao.getAttribute("arquivoQuantidadeContas");
			manterContaConjuntoImovelActionForm.setQuatidadeConta(arquivoQuantidadeConta.toString());

			Collection<Conta> colecaoContas = (Collection<Conta>) sessao.getAttribute("colecaoContas");

			if(!Util.isVazioOrNulo(colecaoContas)){
				ArrayList<Conta> colecaoContaImovel = new ArrayList<Conta>();
				for(Conta contaArquivo : colecaoContas){
					// Consulta Conta e insere o Motivo de Revis�o e a Situa��o Atual
					FiltroConta filtroConta = new FiltroConta();
					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, contaArquivo.getId()));
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.CONTA_MOTIVO_REVISAO);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);

					colecaoContaImovel.addAll(fachada.pesquisar(filtroConta, Conta.class.getName()));

				}
				if(!Util.isVazioOrNulo(colecaoContaImovel)){
					// Adiciona cole��o na sess�o
					BigDecimal valorTotalAgua = BigDecimal.ZERO;
					BigDecimal valorTotalEsgoto = BigDecimal.ZERO;
					BigDecimal valorTotalDebito = BigDecimal.ZERO;
					BigDecimal valorTotalCredito = BigDecimal.ZERO;
					BigDecimal valorTotalImpostos = BigDecimal.ZERO;
					BigDecimal valorTotalContas = BigDecimal.ZERO;

					for(Conta conta : colecaoContaImovel){

						valorTotalAgua = valorTotalAgua.add(conta.getValorAgua());
						valorTotalEsgoto = valorTotalEsgoto.add(conta.getValorEsgoto());
						valorTotalDebito = valorTotalDebito.add(conta.getDebitos());
						valorTotalCredito = valorTotalCredito.add(conta.getValorCreditos());
						valorTotalImpostos = valorTotalImpostos.add(conta.getValorImposto());
						valorTotalContas = valorTotalContas.add(Util.formatarMoedaRealparaBigDecimal(conta.getValorTotalConta()));
					}

					// Adiciona cole��o na sess�o
					sessao.setAttribute("colecaoContaImovel", colecaoContaImovel);

					// Adiciona acumuladores na sess�o
					sessao.setAttribute("valorAgua", Util.formatarMoedaReal(valorTotalAgua));
					sessao.setAttribute("valorEsgoto", Util.formatarMoedaReal(valorTotalEsgoto));
					sessao.setAttribute("valorDebito", Util.formatarMoedaReal(valorTotalDebito));
					sessao.setAttribute("valorCredito", Util.formatarMoedaReal(valorTotalCredito));
					sessao.setAttribute("valorImposto", Util.formatarMoedaReal(valorTotalImpostos));
					sessao.setAttribute("valorConta", Util.formatarMoedaReal(valorTotalContas));

				}
			}
		}else if(httpServletRequest.getParameter("cancelar") == null){
			manterContaConjuntoImovelActionForm.setMesAnoConta("");
			manterContaConjuntoImovelActionForm.setMesAnoContaFinal("");
			manterContaConjuntoImovelActionForm.setDataVencimentoInicial("");
			manterContaConjuntoImovelActionForm.setDataVencimentoFinal("");
			manterContaConjuntoImovelActionForm.setQuatidadeConta("");
		}

		// Seleciona a quantidade de contas depois do cancelamento
		if(httpServletRequest.getParameter("cancelar") != null){
			httpServletRequest.setAttribute("reloadPage", "ok");
		}

		// Fechar popup de retirar d�bito cobrado
		if(httpServletRequest.getParameter("fecharPopup") != null){
			sessao.removeAttribute("debitosTipoRetirar");
			sessao.removeAttribute("RetirarDebitoCobradoActionForm");
		}

		if(sessao.getAttribute("telaFiltroImoveisConta") != null){
			sessao.removeAttribute("telaFiltroImoveisConta");	
		}
		
		if(httpServletRequest.getParameter("quantidadeConta") == null
						&& Util.isVazioOuBranco(manterContaConjuntoImovelActionForm.getQuatidadeConta())){
			manterContaConjuntoImovelActionForm.setQuatidadeConta("");
		}

		return retorno;
	}

	/**
	 * Pesquisa Conta Motivo Revis�o
	 * 
	 * @author Carlos Chrystian
	 * @date 09/05/2013
	 */
	private void pesquisarContaMotivoRevisao(HttpServletRequest httpServletRequest){

		// Motivo de Revis�o
		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao(FiltroContaMotivoRevisao.DESCRICAO);

		Collection colecaoContaMotivoRevisao = Fachada.getInstancia().pesquisar(filtroContaMotivoRevisao,
						ContaMotivoRevisao.class.getName());

		((ArrayList<ContaMotivoRevisao>) colecaoContaMotivoRevisao).add(0, new ContaMotivoRevisao());

		httpServletRequest.setAttribute("colecaoContaMotivoRevisao", colecaoContaMotivoRevisao);
	}
}