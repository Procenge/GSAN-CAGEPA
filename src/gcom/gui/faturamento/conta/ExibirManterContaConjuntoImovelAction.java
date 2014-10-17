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
 * [UC0407] Filtrar Imóveis para Inserir ou Manter Conta
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
			// Parâmetro utilizado para decidir se o botão
			// "Retirar de Água ou Esgoto de uma ou mais conta" será exibido.
			String paramPermitirRetirarValorAguaEsgotoConta = (String) ParametroFaturamento.P_PERMITIR_RETIRAR_VALOR_AGUA_ESGOTO_CONTA
							.executar();
			sessao.setAttribute("paramPermitirRetirarValorAguaEsgotoConta", paramPermitirRetirarValorAguaEsgotoConta);
		}catch(ControladorException e){
			e.printStackTrace();
		}

		/*
		 * Alterar Responsável de uma ou mais contas [SB0015 - Alterar Cliente Responsável de um
		 * Conjunto de Contas] (exibir esse opção apenas se o parâmetro
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

		// Limpa dados da tabela da sessão
		sessao.removeAttribute("colecaoContaImovel");
		sessao.removeAttribute("valorAgua");
		sessao.removeAttribute("valorEsgoto");
		sessao.removeAttribute("valorDebito");
		sessao.removeAttribute("valorCredito");
		sessao.removeAttribute("valorImposto");
		sessao.removeAttribute("valorConta");

		// Seta o valor inicial fixo para contas em revisão
		if(manterContaConjuntoImovelActionForm.getInContasRevisao() == null){
			manterContaConjuntoImovelActionForm.setInContasRevisao(ConstantesSistema.NAO.toString());
		}

		manterContaConjuntoImovelActionForm.setQuatidadeImovel(Integer.toString(colecaoImovel.size()));

		// Validação para quantidade de contas OC1090191
		int limiteQtdeImoveis = 0;

		try{
			String pLimiteQtdImoveis = ParametroFaturamento.P_LIMITE_QTD_IMOVEIS_MANTER_CONTA_CONJUNTO_IMOVEIS.executar();

			if(pLimiteQtdImoveis != null){
				limiteQtdeImoveis = Util.converterStringParaInteger(pLimiteQtdImoveis);
			}
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema.inexistente");
		}

		// Se o parâmetro de sistema informa um limite para a quantidade de imóveis, o sistema
		// verifica. Caso contrário, não restringe a quantidade.
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
		// Cliente Imóvel
		if(sessao.getAttribute("codigoCliente") != null && sessao.getAttribute("nomeCliente") != null){
			manterContaConjuntoImovelActionForm.setCodigoCliente((String) sessao.getAttribute("codigoCliente"));
			manterContaConjuntoImovelActionForm.setNomeCliente((String) sessao.getAttribute("nomeCliente"));
			httpServletRequest.setAttribute("cliente", "ok");
			// tipoPesquisaConta = 1;
		}

		// Inscrição Imóvel
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

		// Limpa os motivos de revisão
		if(sessao.getAttribute("telaFiltroImoveisConta") != null
						&& !Util.isVazioOrNulo(manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis())){
			manterContaConjuntoImovelActionForm.limparMotivosRevisaoDisponiveisComoInteger();
		}

		// Pesquisar os motivos para conta em revisão
		this.pesquisarContaMotivoRevisao(httpServletRequest);
		
		if(!Util.isVazioOrNulo(manterContaConjuntoImovelActionForm.getMotivosRevisaoDisponiveis())){
			// Tratar para retirar opção "vazia" da lista de motivos
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

			// Chama o método obter contas conjunto de imóveis
			// e adiciona a quantidade de contas ao form
			quantidadeConta = fachada.obterContasConjuntoImoveis(anoMes, colecaoImovel, codigoCliente, relacaoTipo,
							dataVencimentoContaInicio, dataVencimentoContaFim, idGrupoFaturamento, anoMesFinal, inContasRevisao,
							motivosRevisaoDisponiveis);

			// Se o parâmetro de sistema informa um limite para a quantidade de imóveis, o sistema
			// verifica. Caso contrário, não restringe a quantidade.
			if(limiteQtdeImoveis > 0){

				if(quantidadeConta >= limiteQtdeImoveis){
					ActionServletException actionServletException = new ActionServletException(
									"atencao.filtro_quantidade_registros_ultrapassa_limite", String.valueOf(limiteQtdeImoveis));
					actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelInserirManterContaAction.do");
					throw actionServletException;

				}
			}

			manterContaConjuntoImovelActionForm.setQuatidadeConta(quantidadeConta.toString());


			// Envia a coleção de contas a serem exibidas no grid
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

			// Ordenando a coleção por mês/ano de referência descendente
			List<Conta> colecaoContasOrdenadaImovel = new ArrayList<Conta>();
			colecaoContasOrdenadaImovel.addAll(colecaoContaImovel);
			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("referencia"));
			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort((List) colecaoContasOrdenadaImovel, multiSort);
			Collections.reverse(colecaoContasOrdenadaImovel);

			// Adiciona coleção na sessão
			sessao.setAttribute("colecaoContaImovel", colecaoContasOrdenadaImovel);

			// Adiciona acumuladores na sessão
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
					// Consulta Conta e insere o Motivo de Revisão e a Situação Atual
					FiltroConta filtroConta = new FiltroConta();
					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, contaArquivo.getId()));
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.CONTA_MOTIVO_REVISAO);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);

					colecaoContaImovel.addAll(fachada.pesquisar(filtroConta, Conta.class.getName()));

				}
				if(!Util.isVazioOrNulo(colecaoContaImovel)){
					// Adiciona coleção na sessão
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

					// Adiciona coleção na sessão
					sessao.setAttribute("colecaoContaImovel", colecaoContaImovel);

					// Adiciona acumuladores na sessão
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

		// Fechar popup de retirar débito cobrado
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
	 * Pesquisa Conta Motivo Revisão
	 * 
	 * @author Carlos Chrystian
	 * @date 09/05/2013
	 */
	private void pesquisarContaMotivoRevisao(HttpServletRequest httpServletRequest){

		// Motivo de Revisão
		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao(FiltroContaMotivoRevisao.DESCRICAO);

		Collection colecaoContaMotivoRevisao = Fachada.getInstancia().pesquisar(filtroContaMotivoRevisao,
						ContaMotivoRevisao.class.getName());

		((ArrayList<ContaMotivoRevisao>) colecaoContaMotivoRevisao).add(0, new ContaMotivoRevisao());

		httpServletRequest.setAttribute("colecaoContaMotivoRevisao", colecaoContaMotivoRevisao);
	}
}