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

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.*;
import gcom.atendimentopublico.registroatendimento.EspecificacaoTipoValidacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteConta;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.imovel.bean.ImovelMicromedicao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.faturamento.conta.*;
import gcom.faturamento.credito.*;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.faturamento.ExecutorParametrosFaturamento;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirRetificarContaAction
				extends GcomAction
				implements Parametrizacao {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirRetificarConta");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Instância do formulário que está sendo utilizado
		RetificarContaActionForm retificarContaActionForm = (RetificarContaActionForm) actionForm;

		if(getFachada().verificarPermissaoEspecial(PermissaoEspecial.RETIFICAR_DATA_VENCIMENTO_ANTERIOR_OU_POSTERIOR_DATA_CORRENTE, usuario)){
			httpServletRequest.setAttribute("permiteAlterarVencimentoAnteriorOuPosteriorDataCorrente", true);
		}else{
			httpServletRequest.setAttribute("permiteAlterarVencimentoAnteriorOuPosteriorDataCorrente", false);
		}

		sessao.removeAttribute("consumoAguaFaturada");
		sessao.removeAttribute("leituraFaturamento");

		if(httpServletRequest.getParameter("idMotivoRetificacao") != null
						&& !httpServletRequest.getParameter("idMotivoRetificacao").equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			// [FS0027] Validade motivo da retificação informado.
			Integer idMotivoRetificacao = Util.converterStringParaInteger(httpServletRequest.getParameter("idMotivoRetificacao"));
			Integer parametroMotivoRetificaoOcorrenciaConsumo = null;
			try{
				parametroMotivoRetificaoOcorrenciaConsumo = Util
								.converterStringParaInteger((String) ParametroFaturamento.P_MOTIVO_RETIFICACAO_OCORRENCIA_CONSUMO
												.executar(this));
			}catch(ControladorException e){
				throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
								new String[e.getParametroMensagem().size()]));
			}

			if(idMotivoRetificacao.equals(parametroMotivoRetificaoOcorrenciaConsumo)){
				Integer qtdContasRetificadasPorMotivoRetificacao;
				Integer idImovel = null;
				try{
					if(httpServletRequest.getParameter("idImovel") != null){
						idImovel = Util.converterStringParaInteger(httpServletRequest.getParameter("idImovel"));
					}

					qtdContasRetificadasPorMotivoRetificacao = Fachada.getInstancia().obterQtdContasRetificadasPorMotivoRetificacao(
													idImovel,
													Util.obterInteger(((String) ParametroFaturamento.P_MOTIVO_RETIFICACAO_OCORRENCIA_CONSUMO
																	.executar(this))));
				}catch(ControladorException e){
					throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
									new String[e.getParametroMensagem().size()]));
				}
				Integer qtdRAEncerradaAnoParametro;
				try{
					qtdRAEncerradaAnoParametro = Util
									.converterStringParaInteger((String) ParametroFaturamento.P_QUANTIDADE_RA_ENCERRADA_ANO_BLOQUEIO_RETIFICACAO
													.executar(this));
				}catch(ControladorException e){
					throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
									new String[e.getParametroMensagem().size()]));
				}
				if(qtdContasRetificadasPorMotivoRetificacao.intValue() > qtdRAEncerradaAnoParametro.intValue()){
					retificarContaActionForm.setMotivoRetificacaoID("-1");

					throw new ActionServletException("atencao.quantidade.contas.retificadas.ocorrencia.consumo.maior",
									String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
				}
			}
		}

		/*
		 * Colocado por Raphael Rossiter em 16/04/2007
		 * Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a
		 * empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());

		// Conta selecionada
		String reloadPage = httpServletRequest.getParameter("reloadPage");
		String idConta = null;
		if(httpServletRequest.getParameter("contaID") != null){
			idConta = httpServletRequest.getParameter("contaID");
		}else{
			idConta = sessao.getAttribute("contaID").toString();
		}

		// Caso a chamada tenha sido feita pelo Caucionar Conta
		String indicadorOperacao = httpServletRequest.getParameter("indicadorOperacao");
		boolean temPermissaoAtualizarDebitosExecFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.ATUALIZAR_DEBITOS_EXECUCAO_FISCAL, this.getUsuarioLogado(httpServletRequest));
		StringBuffer parametroExecucaoFiscal = new StringBuffer();

		if(!Util.isVazioOuBranco(indicadorOperacao) && indicadorOperacao.equals("caucionar")){

			sessao.setAttribute("indicadorOperacao", indicadorOperacao);

			String[] arrayIdentificadores = idConta.split(",");

			for(int i = 0; i < arrayIdentificadores.length; i++){
				String dadosConta = arrayIdentificadores[i];
				String[] idContaArray = dadosConta.split("-");
				Integer idContaParametro = new Integer(idContaArray[0]);

				idConta = idContaParametro.toString();

				/**
				 * [UC0146] Manter Conta
				 * [SB0040] Verificar Existência de Conta em Execução Fiscal
				 * 
				 * @author Gicevalter Couto
				 * @date 07/08/2014
				 */
				if(idConta != null && !temPermissaoAtualizarDebitosExecFiscal){
					FiltroConta filtroConta = new FiltroConta();
					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITOS_COBRADOS);

					Conta conta = (Conta) Util.retonarObjetoDeColecao(((Collection<Conta>) fachada.pesquisar(filtroConta,
									Conta.class.getName())));
					ContaValoresHelper contaValores = new ContaValoresHelper();
					contaValores.setConta(conta);

					Collection<ContaValoresHelper> colecaoContaValores = new ArrayList<ContaValoresHelper>();
					colecaoContaValores.add(contaValores);

					if(fachada.verificarExecucaoFiscal(colecaoContaValores, null, null)){
						parametroExecucaoFiscal.append(conta.getReferenciaFormatada());
						parametroExecucaoFiscal.append(", ");
					}
				}
			}
		}else{
			/**
			 * [UC0146] Manter Conta
			 * [SB0040] Verificar Existência de Conta em Execução Fiscal
			 * 
			 * @author Gicevalter Couto
			 * @date 07/08/2014
			 * @param colecaoContas
			 */
			if(idConta != null && !temPermissaoAtualizarDebitosExecFiscal){
				FiltroConta filtroConta = new FiltroConta();
				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
				filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITOS_COBRADOS);

				Conta conta = (Conta) Util
								.retonarObjetoDeColecao(((Collection<Conta>) fachada.pesquisar(filtroConta, Conta.class.getName())));
				ContaValoresHelper contaValores = new ContaValoresHelper();
				contaValores.setConta(conta);

				Collection<ContaValoresHelper> colecaoContaValores = new ArrayList<ContaValoresHelper>();
				colecaoContaValores.add(contaValores);

				if(fachada.verificarExecucaoFiscal(colecaoContaValores, null, null)){
					parametroExecucaoFiscal.append(conta.getReferenciaFormatada());
					parametroExecucaoFiscal.append(", ");
				}
			}
		}

		String parametroMensagemExecFiscal = parametroExecucaoFiscal.toString();

		if(!Util.isVazioOuBranco(parametroMensagemExecFiscal)){
			parametroMensagemExecFiscal = parametroMensagemExecFiscal.substring(0, parametroMensagemExecFiscal.length() - 2);

			throw new ActionServletException("atencao.conta.debito.execucao.fiscal", usuario.getNomeUsuario().toString(),
							parametroMensagemExecFiscal);
		}

		if(httpServletRequest.getParameter("idImovel") != null){
			sessao.setAttribute("idImovel", httpServletRequest.getParameter("idImovel"));
		}

		/*
		 * Código comentado para satisfazer as especificações da ADA no UC0146
		 * removendo o [FS0019] - Verificar Conta Paga.
		 * Saulo Lima 10/09/2008
		 */

		Map<String, String[]> requestMap = httpServletRequest.getParameterMap();

		// DEFINIÇÃO QUE IRÁ AUXILIAR O RETORNO DOS POPUPS
		sessao.setAttribute("UseCase", "RETIFICARCONTA");

		Collection colecaoMotivoRetificacaoConta, colecaoSituacaoLigacaoAgua, colecaoSituacaoLigacaoEsgoto, colecaoAnormalidadeLeitura;

		Collection<ConsumoTarifa> colecaoConsumoTarifa;

		// Removendo coleções da sessão
		if(idConta != null && !idConta.equalsIgnoreCase("") && reloadPage == null){
			sessao.removeAttribute("contaRetificar");
			sessao.removeAttribute("colecaoCategoria");
			sessao.removeAttribute("colecaoDebitoCobrado");
			sessao.removeAttribute("colecaoCreditoRealizado");
			sessao.removeAttribute("colecaoAdicionarCategoria");
			sessao.removeAttribute("colecaoAdicionarDebitoTipo");
		}

		Integer qtdDiasVencimentoConta = null;
		try{
			qtdDiasVencimentoConta = Util.converterStringParaInteger((String) ParametroFaturamento.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA
							.executar(this));
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Carregar a data corrente do sistema
		// ====================================
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataCorrente = new GregorianCalendar();

		// Data Corrente
		httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));

		// Data Corrente + P_QUANTIDADE_DIAS_VENCIMENTO_CONTA dias
		dataCorrente.add(Calendar.DATE, qtdDiasVencimentoConta.intValue());
		httpServletRequest.setAttribute("dataAtual60", formatoData.format(dataCorrente.getTime()));
		httpServletRequest.setAttribute("qtdDiasVencimentoConta", qtdDiasVencimentoConta.toString());

		sessao.setAttribute("dataAtual60", formatoData.format(dataCorrente.getTime()));
		sessao.setAttribute("qtdDiasVencimentoConta", qtdDiasVencimentoConta.toString());
		sessao.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));

		/*
		 * Costante que informa o ano limite para o campo anoMesReferencia da conta
		 */
		httpServletRequest.setAttribute("anoLimite", ConstantesSistema.ANO_LIMITE);

		if(sessao.getAttribute("colecaoMotivoRetificacaoConta") == null){
			/*
			 * Motivo do Caucionamento (Carregar coleção) e exibe o motivo referente a caucionamento
			 * e não permite alteração
			 * ======================================================================
			 */

			// Caso a função seja “caucionamento”
			if(!Util.isVazioOuBranco(indicadorOperacao) && indicadorOperacao.equals("caucionar")){
				// exibe o motivo referente a caucionamento e não permite alteração
				// (CMRV_CDCONSTANTE = ‘CAUCIONAMENTO’ na tabela CONTA_MOTIVO_REVISAO)
				FiltroMotivoRevisaoConta filtroMotivoRevisaoConta = new FiltroMotivoRevisaoConta(
								FiltroMotivoRevisaoConta.DESCRICAO_MOTIVO_REVISAO_CONTA);
				filtroMotivoRevisaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRevisaoConta.ID,
								ContaMotivoRevisao.CAUCIONAMENTO));
				filtroMotivoRevisaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRevisaoConta.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoMotivoRetificacaoConta = fachada.pesquisar(filtroMotivoRevisaoConta, ContaMotivoRevisao.class.getName());

				sessao.setAttribute("colecaoMotivoRetificacaoConta", colecaoMotivoRetificacaoConta);

				if(colecaoMotivoRetificacaoConta == null || colecaoMotivoRetificacaoConta.isEmpty()){

					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_CAUCIONAR_CONTA");

				}

			}else{
				/*
				 * Motivo da Retificação (Carregar coleção) e remover as coleções que ainda estão na
				 * sessão
				 * ======================================================================
				 */

				// Caso a função seja “retificação”,(seleciona a partir da tabela
				// MOTIVO_RETIFICAÇÃO_CONTA)
				FiltroMotivoRetificacaoConta filtroMotivoRetificacaoConta = new FiltroMotivoRetificacaoConta(
								FiltroMotivoRetificacaoConta.DESCRICAO_MOTIVO_RETIFICACAO_CONTA);

				filtroMotivoRetificacaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRetificacaoConta.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoMotivoRetificacaoConta = fachada.pesquisar(filtroMotivoRetificacaoConta, ContaMotivoRetificacao.class.getName());

				sessao.setAttribute("colecaoMotivoRetificacaoConta", colecaoMotivoRetificacaoConta);

				if(colecaoMotivoRetificacaoConta == null || colecaoMotivoRetificacaoConta.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_RETIFICACAO_CONTA");
				}
			}
		}

		// ====================================================================

		if(sessao.getAttribute("colecaoConsumoTarifa") == null){
			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoConsumoTarifa = fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			if(Util.isVazioOrNulo(colecaoConsumoTarifa)){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "CONSUMO_TARIFA");
			}else{
				sessao.setAttribute("colecaoConsumoTarifa", colecaoConsumoTarifa);
			}
		}

		/*
		 * Situação Ligação Água (Carregar coleção)
		 * ======================================================================
		 */
		if(sessao.getAttribute("colecaoSituacaoLigacaoAgua") == null){

			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.DESCRICAO);

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoSituacaoLigacaoAgua = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

			if(colecaoSituacaoLigacaoAgua == null || colecaoSituacaoLigacaoAgua.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LIGACAO_AGUA_SITUACAO");
			}else{
				sessao.setAttribute("colecaoSituacaoLigacaoAgua", colecaoSituacaoLigacaoAgua);
			}
		}
		// =====================================================================

		/*
		 * Situação Ligação Esgoto (Carregar coleção)
		 * ======================================================================
		 */
		if(sessao.getAttribute("colecaoSituacaoLigacaoEsgoto") == null){

			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao(FiltroLigacaoEsgotoSituacao.DESCRICAO);

			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroLigacaoEsgotoSituacao.ID,
							LigacaoEsgotoSituacao.EM_FISCALIZACAO));// TODO Modificar por ESGOTO
			// SUBJUDICE

			colecaoSituacaoLigacaoEsgoto = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

			if(colecaoSituacaoLigacaoEsgoto == null || colecaoSituacaoLigacaoEsgoto.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LIGACAO_ESGOTO_SITUACAO");
			}else{
				sessao.setAttribute("colecaoSituacaoLigacaoEsgoto", colecaoSituacaoLigacaoEsgoto);
			}
		}

		// =====================================================================
		/*
		 * Anormalidade de Leitura de Faturamento (Carregar coleção)
		 * ======================================================================
		 */
		if(sessao.getAttribute("colecaoAnormalidadeLeitura") == null){
			FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade(FiltroLeituraAnormalidade.DESCRICAO);

			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoAnormalidadeLeitura = fachada.pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class.getName());

			if(colecaoAnormalidadeLeitura == null || colecaoAnormalidadeLeitura.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LEITURA_ANORMALIDADE");
			}else{
				sessao.setAttribute("colecaoAnormalidadeLeitura", colecaoAnormalidadeLeitura);
			}
		}

		// ====================================================================

		/*
		 * Pesquisando a conta a partir do id recebido
		 * =====================================================================
		 */
		Conta contaSelecao = null;

		if(idConta != null && !idConta.equalsIgnoreCase("")){

			contaSelecao = fachada.pesquisarContaRetificacao(Integer.valueOf(idConta));

			if(contaSelecao == null){
				throw new ActionServletException("atencao.pesquisa.conta.inexistente");
			}

			// Colocando o objeto conta selecionado na sessão
			sessao.setAttribute("contaRetificar", contaSelecao);

		}else if(sessao.getAttribute("contaRetificar") != null){
			contaSelecao = (Conta) sessao.getAttribute("contaRetificar");
		}else{
			throw new ActionServletException("atencao.pesquisa.conta.inexistente");
		}
		// ====================================================================

		String pPermitirAlterarClienteConta = null;

		try{

			pPermitirAlterarClienteConta = (String) ParametroFaturamento.P_PERMITIR_ALTERAR_CLIENTE_CONTA.executar(this);
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		if(idConta != null && !idConta.equalsIgnoreCase("") && (reloadPage == null || reloadPage.equalsIgnoreCase(""))){

			/*
			 * Pesquisar o imóvel a partir da matrícula do imóvel
			 * ======================================================================
			 */
			String idImovel = null;

			if(httpServletRequest.getParameter("idImovel") != null){
				idImovel = httpServletRequest.getParameter("idImovel");
				sessao.setAttribute("idImovel", httpServletRequest.getParameter("idImovel"));
			}else{
				idImovel = (String) sessao.getAttribute("idImovel");
			}

			Imovel objetoImovel = contaSelecao.getImovel();
			// ====================================================================

			Collection idsConta = new ArrayList();
			idsConta.add(Integer.parseInt(idConta));

			if(!Util.isVazioOuBranco(indicadorOperacao) && indicadorOperacao.equals("caucionar")){
				// [FS0016] - Verificar contas que estejam em revisão
				fachada.verificarContasRevisao((Collection) sessao.getAttribute("colecaoContaImovel"), idConta);
			}

			String enderecoURL = httpServletRequest.getServletPath();

			// [FS0022 – Verificar bloqueio funcionalidade por motivo da retificação/revisão da
			// conta]
			fachada.verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(idsConta, usuario, false, false, enderecoURL);

			// [FS0021] Verificar situação da conta
			if(contaSelecao != null){
				if(!fachada.verificarSituacaoContaPermitida(contaSelecao)){
					throw new ActionServletException("atencao.conta_em_situacao_nao_permitida", contaSelecao
									.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao(), "ação");
				}
			}

			String pIdMotivoRetencaoContaPreFaturada = null;

			try{

				pIdMotivoRetencaoContaPreFaturada = (String) ParametroFaturamento.P_MOTIVO_RETENCAO_CONTA_PREFAT.executar();
			}catch(ControladorException e){

				throw new ActionServletException("atencao.sistemaparametro_inexistente", null, "P_MOTIVO_RETENCAO_CONTA_PREFAT");
			}

			boolean temPermissaoRetificarContaRetida = fachada.verificarPermissaoRetificarContaRetida(usuario);

			// Atribui "2" (Não) ao Indicador de Conta em Revisão por Motivo Permitido com Permissão
			// Especial.
			Short indicadorContaEmRevisaoPorMotivoPermitidoUsuarioPermissaoEspecial = ConstantesSistema.NAO;

			/*
			 * Caso a conta esteja em retenção por motivo de revisão que permita retificação de
			 * conta sem RA (CMRV_ID da tabela CONTA igual ao valor do parâmetro (PASI_VLPARAMETRO
			 * na tabela PARAMETRO_SISTEMA com PASI_DSPARAMETRO="P_MOTIVO_RETENCAO_CONTA_PREFAT")),
			 * e o usuário possua permissão especial para RETIFICAR CONTA RETIDA (existir ocorrência
			 * na tabela USUARIO_PERMISSAO_ESPECIAL com USUR_ID=identificação do usuário e
			 * PMEP_ID=114)
			 */
			if(contaSelecao != null && contaSelecao.getContaMotivoRevisao() != null
							&& pIdMotivoRetencaoContaPreFaturada.equals(contaSelecao.getContaMotivoRevisao().getId().toString())
							&& temPermissaoRetificarContaRetida){

				// Atribui "1" (Sim) ao Indicador de Conta em Revisão por Motivo Permitido com
				// Permissão Especial
				indicadorContaEmRevisaoPorMotivoPermitidoUsuarioPermissaoEspecial = ConstantesSistema.SIM;
			}

			boolean temPermissaoRetificarContaSemRA = fachada.verificarPermissaoRetificarContaSemRA(usuario);

			/*
			 * Caso o usuário não possua permissão especial para RETIFICAR CONTA SEM RA (não existir
			 * ocorrência na tabela USUARIO_PERMISSAO_ESPECIAL com USUR_ID=identificação do usuário
			 * e PMEP_ID=48) e Indicador de Conta em Revisão por Motivo Permitido com Permissão
			 * Especial esteja com valor "2" (Não)
			 */
			if(!temPermissaoRetificarContaSemRA
							&& indicadorContaEmRevisaoPorMotivoPermitidoUsuarioPermissaoEspecial.equals(ConstantesSistema.NAO)){

				// [FS0001] - Verificar Existência de RA
				fachada.verificarExistenciaRegistroAtendimento(Integer.valueOf(idImovel), "atencao.conta_existencia_registro_atendimento",
								EspecificacaoTipoValidacao.ALTERACAO_CONTA);

			}

			fachada.verificarVencimentoContaDebitoAutomatico(contaSelecao);

			/*
			 * Pesquisar o cliente usuário do imóvel selecionado
			 * ======================================================================
			 */
			String nomeClienteUsuarioImovel = fachada.consultarClienteUsuarioImovel(contaSelecao.getImovel().getId());

			Cliente clienteResposavelImovel = fachada.pesquisarClienteResponsavelImovel(new Integer(contaSelecao.getImovel().getId()));
			// ====================================================================

			// PREPARANDO OS OBJETOS PARA SEREM EXIBIDOS PELO FORMULÁRIO
			// ===================================================================

			if(sistemaParametro.getNomeAbreviadoEmpresa().trim().equalsIgnoreCase(SistemaParametro.EMPRESA_CAERN)){

				// [UC0108] - Quantidade de economias por categoria
				Collection colecaoSubcategoria = fachada.obterQuantidadeEconomiasContaCategoriaPorSubcategoria(contaSelecao);

				sessao.setAttribute("colecaoSubcategoria", colecaoSubcategoria);

				Collection colecaoSubcategoriaInicial = new ArrayList();
				colecaoSubcategoriaInicial.addAll(colecaoSubcategoria);
				sessao.setAttribute("colecaoSubcategoriaInicial", colecaoSubcategoriaInicial);
			}else{

				// [UC0108] - Quantidade de economias por categoria
				Collection colecaoCategoria = fachada.obterQuantidadeEconomiasContaCategoria(contaSelecao);

				sessao.setAttribute("colecaoCategoria", colecaoCategoria);

				Collection colecaoCategoriaInicial = new ArrayList();
				colecaoCategoriaInicial.addAll(colecaoCategoria);
				sessao.setAttribute("colecaoCategoriaInicial", colecaoCategoriaInicial);
			}

			// Consumo Poço
			if((retificarContaActionForm.getConsumoFixoPoco() == null || StringUtils.isEmpty(retificarContaActionForm.getConsumoFixoPoco()
							.trim())) && contaSelecao.getConsumoPoco() != null){

				retificarContaActionForm.setConsumoFixoPoco(contaSelecao.getConsumoPoco().toString());

			}

			String pAcumularConsumoEsgotoPoco = null;
			try{

				pAcumularConsumoEsgotoPoco = ParametroMicromedicao.P_ACUMULA_CONSUMO_ESGOTO_POCO.executar();
			}catch(ControladorException e){

				throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
								new String[e.getParametroMensagem().size()]));
			}

			// Caso a empresa não acumule o volume do poço com o volume da ligação de água para
			// cálculo
			// do valor de esgoto
			if(pAcumularConsumoEsgotoPoco.equals(ConstantesSistema.NAO.toString())){

				LigacaoEsgoto ligacaoEsgoto = (LigacaoEsgoto) fachada.pesquisar(objetoImovel.getId(), LigacaoEsgoto.class);

				// Caso o imóvel possua volume fixo para poço
				if(ligacaoEsgoto != null && ligacaoEsgoto.getNumeroConsumoFixoPoco() != null){

					retificarContaActionForm.setHabilitarConsumoFixoPoco(ConstantesSistema.SIM.toString());
				}else{

					retificarContaActionForm.setHabilitarConsumoFixoPoco(ConstantesSistema.NAO.toString());
					retificarContaActionForm.setConsumoFixoPoco(null);
				}

			}else{

				retificarContaActionForm.setHabilitarConsumoFixoPoco(ConstantesSistema.NAO.toString());
				retificarContaActionForm.setConsumoFixoPoco(null);
			}

			// Obtendo os débitos cobrados da conta
			Collection colecaoDebitoCobrado = fachada.obterDebitosCobradosConta(contaSelecao);
			sessao.setAttribute("colecaoDebitoCobrado", colecaoDebitoCobrado);

			Collection colecaoDebitoCobradoInicial = new ArrayList();
			colecaoDebitoCobradoInicial.addAll(colecaoDebitoCobrado);
			sessao.setAttribute("colecaoDebitoCobradoInicial", colecaoDebitoCobradoInicial);

			// Obtendo os créditos realizados da conta
			Collection colecaoCreditoRealizado = fachada.obterCreditosRealizadosConta(contaSelecao);
			Collection colecaoCreditoRealizadoInicial = fachada.obterCreditosRealizadosConta(contaSelecao);

			// Totalizando o valor dos créditos
			BigDecimal valorTotalCredito = new BigDecimal("0.00");

			if(colecaoCreditoRealizado != null && !colecaoCreditoRealizado.isEmpty()){

				Iterator colecaoCreditoRealizadoIt = colecaoCreditoRealizado.iterator();
				CreditoRealizado creditoRealizadoColecao;

				while(colecaoCreditoRealizadoIt.hasNext()){
					creditoRealizadoColecao = (CreditoRealizado) colecaoCreditoRealizadoIt.next();

					valorTotalCredito = valorTotalCredito.add(creditoRealizadoColecao.getValorCredito());

					Boolean pgtAssociado = this.existePagamentoAssociado(creditoRealizadoColecao);

					creditoRealizadoColecao.setPgtAssociado(pgtAssociado);

				}

			}

			sessao.setAttribute("colecaoCreditoRealizado", colecaoCreditoRealizado);
			// colecaoCreditoRealizadoInicial.addAll(colecaoCreditoRealizado);
			sessao.setAttribute("colecaoCreditoRealizadoInicial", colecaoCreditoRealizadoInicial);


			// Totalizando o valor dos débitos
			BigDecimal valorTotalDebito = new BigDecimal("0.00");
			if(colecaoDebitoCobrado != null && !colecaoDebitoCobrado.isEmpty()){

				Iterator colecaoDebitoCobradoIt = colecaoDebitoCobrado.iterator();
				DebitoCobrado debitoCobradoColecao;

				while(colecaoDebitoCobradoIt.hasNext()){
					debitoCobradoColecao = (DebitoCobrado) colecaoDebitoCobradoIt.next();

					valorTotalDebito = valorTotalDebito.add(debitoCobradoColecao.getValorPrestacao());
				}
			}

			BigDecimal valorTotalAgua = new BigDecimal("0.00");
			BigDecimal valorTotalEsgoto = new BigDecimal("0.00");

			if(contaSelecao.getValorAgua() != null){
				valorTotalAgua = contaSelecao.getValorAgua();
			}

			if(contaSelecao.getValorEsgoto() != null){
				valorTotalEsgoto = contaSelecao.getValorEsgoto();
			}

			BigDecimal valorTotalConta = new BigDecimal("0.00");
			valorTotalConta = contaSelecao.getValorTotal();

			// Arredondando os valores obtidos para duas casas decimais
			valorTotalAgua.setScale(2, BigDecimal.ROUND_HALF_UP);
			valorTotalEsgoto.setScale(2, BigDecimal.ROUND_HALF_UP);
			valorTotalDebito.setScale(2, BigDecimal.ROUND_HALF_UP);
			valorTotalConta.setScale(2, BigDecimal.ROUND_HALF_UP);

			// Dados da conta
			retificarContaActionForm.setMesAnoConta(Util.formatarMesAnoReferencia(contaSelecao.getReferencia()));

			String vencimentoConta = Util.formatarData(contaSelecao.getDataVencimentoConta());
			retificarContaActionForm.setVencimentoConta(vencimentoConta);
			if(Util.isVazioOuBranco(retificarContaActionForm.getVencimentoContaAnterior())){
				retificarContaActionForm.setVencimentoContaAnterior(vencimentoConta);
			}

			String idConsumoTarifaStr = Integer.toString(ConstantesSistema.NUMERO_NAO_INFORMADO);
			ConsumoTarifa consumoTarifa = contaSelecao.getConsumoTarifa();

			if(consumoTarifa != null){
				Integer idConsumoTarifa = consumoTarifa.getId();
				idConsumoTarifaStr = Integer.toString(idConsumoTarifa);
			}

			if(retificarContaActionForm.getConsumoTarifaId() == null
							|| StringUtils.isEmpty(retificarContaActionForm.getConsumoTarifaId().trim())){

				retificarContaActionForm.setConsumoTarifaId(idConsumoTarifaStr);

			}

			// Exibir o campo "Tarifa de Consumo"
			try{
				if(ParametroFaturamento.P_RETIFICACAO_TARIFA.executar().equals(ConstantesSistema.SIM.toString())){
					sessao.setAttribute("exibirConsumoTarifa", true);
				}else{
					sessao.removeAttribute("exibirConsumoTarifa");
				}
			}catch(ControladorException e){
				throw new ActionServletException(e.getMessage(), e);
			}

			if(retificarContaActionForm.getSituacaoAguaConta() == null
							|| StringUtils.isEmpty(retificarContaActionForm.getSituacaoAguaConta().trim())){

				retificarContaActionForm.setSituacaoAguaConta(String.valueOf(contaSelecao.getLigacaoAguaSituacao().getId().intValue()));

			}

			if(retificarContaActionForm.getSituacaoEsgotoConta() == null
							|| StringUtils.isEmpty(retificarContaActionForm.getSituacaoEsgotoConta().trim())){

				retificarContaActionForm.setSituacaoEsgotoConta(String.valueOf(contaSelecao.getLigacaoEsgotoSituacao().getId().intValue()));

			}

			if(retificarContaActionForm.getPercentualEsgoto() == null
							|| StringUtils.isEmpty(retificarContaActionForm.getPercentualEsgoto().trim())){

				retificarContaActionForm.setPercentualEsgoto(Util.formatarMoedaReal(contaSelecao.getPercentualEsgoto()));

			}

			// ......................................................................................................................
			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.DESCRICAO);
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, contaSelecao
							.getLigacaoAguaSituacao().getId()));
			colecaoSituacaoLigacaoAgua = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

			LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(colecaoSituacaoLigacaoAgua);

			// Alteração conforme OC0857313
			// ......................................................................................................................
			// Consumo de água (Conta)
			// if((contaSelecao.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)
			// || contaSelecao.getLigacaoAguaSituacao()
			// .getId().equals(LigacaoAguaSituacao.CORTADO))
			// && contaSelecao.getConsumoAgua() != null){

			if(ligacaoAguaSituacao != null && ligacaoAguaSituacao.getIndicadorAjusteConsumo().equals(LigacaoAguaSituacao.FATURAMENTO_ATIVO)
							&& contaSelecao.getConsumoAgua() != null){

				if(retificarContaActionForm.getConsumoAgua() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getConsumoAgua().trim())){

					retificarContaActionForm.setConsumoAgua(String.valueOf(contaSelecao.getConsumoAgua().intValue()));
					sessao.setAttribute("consumoAguaFaturada", String.valueOf(contaSelecao.getConsumoAgua().intValue()));

				}

			}

			if(retificarContaActionForm.getIndicadorAguaFaturavel() == null
							|| StringUtils.isEmpty(retificarContaActionForm.getIndicadorAguaFaturavel().trim())){

				retificarContaActionForm.setIndicadorAguaFaturavel(ligacaoAguaSituacao.getIndicadorFaturamentoSituacao().toString());

			}

			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao(FiltroLigacaoEsgotoSituacao.DESCRICAO);
			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID, contaSelecao
							.getLigacaoEsgotoSituacao().getId()));// TODO Modificar por ESGOTO
			colecaoSituacaoLigacaoEsgoto = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());
			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) Util.retonarObjetoDeColecao(colecaoSituacaoLigacaoEsgoto);

			// Consumo de esgoto (Conta)
			if(ligacaoEsgotoSituacao != null
							&& ligacaoEsgotoSituacao.getIndicadorFaturamentoSituacao().equals(LigacaoEsgotoSituacao.FATURAMENTO_ATIVO)
							&& contaSelecao.getConsumoEsgoto() != null){

				retificarContaActionForm.setConsumoEsgoto(contaSelecao.getConsumoEsgoto().toString());
			}

			// Percentual Esgoto (Conta)
			if(ligacaoEsgotoSituacao != null
							&& ligacaoEsgotoSituacao.getIndicadorFaturamentoSituacao().equals(LigacaoEsgotoSituacao.FATURAMENTO_ATIVO)
							&& contaSelecao.getPercentualEsgoto() != null){
				retificarContaActionForm.setPercentualEsgoto(Util.formatarMoedaReal(contaSelecao.getPercentualEsgoto()));
			}

			retificarContaActionForm.setFixoEsgoto(contaSelecao.getConsumoMinimoEsgoto() != null ? contaSelecao.getConsumoMinimoEsgoto()
							.toString() : "");
			// Exibindo os valores calculados
			retificarContaActionForm.setValorAgua(Util.formatarMoedaReal(valorTotalAgua));
			retificarContaActionForm.setValorEsgoto(Util.formatarMoedaReal(valorTotalEsgoto));
			retificarContaActionForm.setValorDebito(Util.formatarMoedaReal(valorTotalDebito));
			retificarContaActionForm.setValorTotal(Util.formatarMoedaReal(valorTotalConta));

			// Valor total dos créditos
			retificarContaActionForm.setValorCredito(Util.formatarMoedaReal(valorTotalCredito));

			// Dados do imóvel
			retificarContaActionForm.setIdImovel(String.valueOf(objetoImovel.getId().intValue()));
			retificarContaActionForm.setInscricaoImovel(objetoImovel.getInscricaoFormatada());
			// retificarContaActionForm.setNomeClienteUsuario(objetoClienteImovel.getCliente().getNome());
			retificarContaActionForm.setNomeClienteUsuario(nomeClienteUsuarioImovel);
			retificarContaActionForm.setNomeClienteResponsavel(clienteResposavelImovel != null ? clienteResposavelImovel.getNome() : "");
			retificarContaActionForm.setSituacaoAguaImovel(objetoImovel.getLigacaoAguaSituacao().getDescricao());
			retificarContaActionForm.setSituacaoEsgotoImovel(objetoImovel.getLigacaoEsgotoSituacao().getDescricao());
			retificarContaActionForm.setSituacaoAguaConta(String.valueOf(contaSelecao.getLigacaoAguaSituacao().getId().intValue()));
			retificarContaActionForm.setSituacaoEsgotoConta(String.valueOf(contaSelecao.getLigacaoEsgotoSituacao().getId().intValue()));

			// Caso a situação da Ligação de Esgoto seja faturavel, habilita campos perfil,
			// consumo e percentual dos dados da ligação de esgoto
			retificarContaActionForm.setIndicadorEsgotoFaturavel(contaSelecao.getLigacaoEsgotoSituacao().getIndicadorFaturamentoSituacao()
							.toString());

			// Verifica se existe a coleção de ligação de esgoto perfil
			if(sessao.getAttribute("colecaoLigacaoEsgotoPerfil") == null){

				FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();
				filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection colecaoLigacaoEsgotoPerfil = fachada.pesquisar(filtroLigacaoEsgotoPerfil, LigacaoEsgotoPerfil.class.getName());

				// Verificar existência de dados
				if(!Util.isVazioOrNulo(colecaoLigacaoEsgotoPerfil)){

					sessao.setAttribute("colecaoLigacaoEsgotoPerfil", colecaoLigacaoEsgotoPerfil);
				}else{

					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Ligação Esgoto Perfil");
				}
			}

			boolean possuiMedicaoHistoricoAguaOuPoco = false;

			// Dados de Leitura, caso existam
			ImovelMicromedicao dadosMedicaoImovel = fachada.carregarDadosMedicaoResumido(objetoImovel.getId(), true,
							String.valueOf(contaSelecao.getReferencia()));
			Imovel imovel = fachada.pesquisarImovel(contaSelecao.getImovel().getId());

			if(dadosMedicaoImovel != null && dadosMedicaoImovel.getMedicaoHistorico() != null){

				Object[] dadosLeituraAnterior = fachada.obterDadosLeituraAnterior(contaSelecao.getReferencia(), MedicaoTipo.LIGACAO_AGUA,
								imovel);

				possuiMedicaoHistoricoAguaOuPoco = true;
				retificarContaActionForm.setMesAnoMedicaoHistoricoAgua(dadosMedicaoImovel.getMedicaoHistorico().getMesAno());

				if(retificarContaActionForm.getNumeroLeituraAnteriorMedicaoHistoricoAgua() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getNumeroLeituraAnteriorMedicaoHistoricoAgua().trim())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getLeituraAnteriorFaturamento() != null){

						retificarContaActionForm.setNumeroLeituraAnteriorMedicaoHistoricoAgua(dadosMedicaoImovel.getMedicaoHistorico()
										.getLeituraAnteriorFaturamento().toString());
					}else if(dadosLeituraAnterior[0] != null){

						retificarContaActionForm.setNumeroLeituraAnteriorMedicaoHistoricoAgua(dadosLeituraAnterior[0].toString());
					}
				}

				if(retificarContaActionForm.getNumeroLeituraAtualMedicaoHistoricoAgua() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getNumeroLeituraAtualMedicaoHistoricoAgua().trim())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getLeituraAtualFaturamento() != null){

						retificarContaActionForm.setNumeroLeituraAtualMedicaoHistoricoAgua(dadosMedicaoImovel.getMedicaoHistorico()
										.getLeituraAtualFaturamento().toString());

						sessao.setAttribute("leituraFaturamento", dadosMedicaoImovel.getMedicaoHistorico().getLeituraAtualFaturamento()
										.toString());
					}

				}

				if(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua().trim())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getDataLeituraAtualFaturamento() != null){

						retificarContaActionForm.setDataLeituraAtualMedicaoHistoricoAgua(Util.formatarData(dadosMedicaoImovel
										.getMedicaoHistorico().getDataLeituraAtualFaturamento()));
					}else{
						String mesAnoContaStr = retificarContaActionForm.getMesAnoConta();
						String anoMesContaStr = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoContaStr);
						Integer anoMesConta = Integer.valueOf(anoMesContaStr);

						retificarContaActionForm.setDataLeituraAtualMedicaoHistoricoAgua(Util.formatarData(buscarDataLeituraCronograma(
										objetoImovel, false, anoMesConta, fachada)));
					}

				}

				if(retificarContaActionForm.getDataLeituraAnteriorMedicaoHistoricoAgua() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getDataLeituraAnteriorMedicaoHistoricoAgua())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getDataLeituraAnteriorFaturamento() != null){

						retificarContaActionForm.setDataLeituraAnteriorMedicaoHistoricoAgua(Util.formatarData(dadosMedicaoImovel
										.getMedicaoHistorico().getDataLeituraAnteriorFaturamento()));

					}else if(dadosLeituraAnterior[1] != null){

						retificarContaActionForm.setDataLeituraAtualMedicaoHistoricoAgua(Util.formatarData((Date) dadosLeituraAnterior[1]));
					}else{
						String mesAnoContaStr = retificarContaActionForm.getMesAnoConta();
						String anoMesContaStr = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoContaStr);
						Integer anoMesConta = Integer.valueOf(anoMesContaStr);

						retificarContaActionForm.setDataLeituraAnteriorMedicaoHistoricoAgua(Util.formatarData(buscarDataLeituraCronograma(
										objetoImovel, true, anoMesConta, fachada)));
					}

				}

				if(retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua()
												.trim())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getLeituraAnormalidadeFaturamento() != null){
						retificarContaActionForm.setLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua(dadosMedicaoImovel
										.getMedicaoHistorico().getLeituraAnormalidadeFaturamento().getId().toString());
					}

				}

			}

			dadosMedicaoImovel = fachada.carregarDadosMedicaoResumido(objetoImovel.getId(), false,
							String.valueOf(contaSelecao.getReferencia()));
			if(dadosMedicaoImovel != null && dadosMedicaoImovel.getMedicaoHistorico() != null){

				Object[] dadosLeituraAnterior = fachada.obterDadosLeituraAnterior(contaSelecao.getReferencia(), MedicaoTipo.POCO, imovel);

				possuiMedicaoHistoricoAguaOuPoco = true;

				if(retificarContaActionForm.getMesAnoMedicaoHistoricoEsgoto() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getMesAnoMedicaoHistoricoEsgoto())){

					retificarContaActionForm.setMesAnoMedicaoHistoricoEsgoto(dadosMedicaoImovel.getMedicaoHistorico().getMesAno());

				}

				if(retificarContaActionForm.getNumeroLeituraAnteriorMedicaoHistoricoEsgoto() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getNumeroLeituraAnteriorMedicaoHistoricoEsgoto().trim())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getLeituraAnteriorFaturamento() != null){

						retificarContaActionForm.setNumeroLeituraAnteriorMedicaoHistoricoEsgoto(dadosMedicaoImovel.getMedicaoHistorico()
										.getLeituraAnteriorFaturamento().toString());
					}else if(dadosLeituraAnterior[0] != null){

						retificarContaActionForm.setNumeroLeituraAnteriorMedicaoHistoricoEsgoto(dadosLeituraAnterior[0].toString());
					}
				}

				if(retificarContaActionForm.getNumeroLeituraAtualMedicaoHistoricoEsgoto() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getNumeroLeituraAtualMedicaoHistoricoEsgoto().trim())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getLeituraAtualFaturamento() != null){
						retificarContaActionForm.setNumeroLeituraAtualMedicaoHistoricoEsgoto(dadosMedicaoImovel.getMedicaoHistorico()
										.getLeituraAtualFaturamento().toString());
					}

				}

				if(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoEsgoto() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoEsgoto().trim())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getDataLeituraAtualFaturamento() != null){
						retificarContaActionForm.setDataLeituraAtualMedicaoHistoricoEsgoto(Util.formatarData(dadosMedicaoImovel
										.getMedicaoHistorico().getDataLeituraAtualFaturamento()));
					}

				}

				if(retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto() == null
								|| StringUtils.isEmpty(retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto())){

					if(dadosMedicaoImovel.getMedicaoHistorico().getLeituraAnormalidadeFaturamento() != null){
						retificarContaActionForm.setLeituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto(dadosMedicaoImovel
										.getMedicaoHistorico().getLeituraAnormalidadeFaturamento().getId().toString());
					}

				}

			}

			if(possuiMedicaoHistoricoAguaOuPoco){

				sessao.removeAttribute("campoLeituraFaturamentoBloqueado");
			}else{

				sessao.setAttribute("campoLeituraFaturamentoBloqueado", true);
			}

			String pExibirCampoLeituraAnteriorRetificarConta = null;

			try{

				pExibirCampoLeituraAnteriorRetificarConta = (String) ParametroFaturamento.P_EXIBIR_CAMPO_LEITURA_ANTERIOR_RETIFICAR_CONTA
								.executar(this);
			}catch(ControladorException e){

				throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
								new String[e.getParametroMensagem().size()]));
			}

			if(possuiMedicaoHistoricoAguaOuPoco
							&& (Util.isNaoNuloBrancoZero(pExibirCampoLeituraAnteriorRetificarConta) && pExibirCampoLeituraAnteriorRetificarConta
											.equals(ConstantesSistema.SIM.toString()))){

				sessao.removeAttribute("exibirCampoLeituraAnteriorRetificacao");
			}else{

				sessao.setAttribute("exibirCampoLeituraAnteriorRetificacao", true);
			}

			if(Util.isNaoNuloBrancoZero(pPermitirAlterarClienteConta)
							&& pPermitirAlterarClienteConta.equals(ConstantesSistema.SIM.toString())){

				sessao.setAttribute("permitirAlterarClienteConta", true);

				if(Util.isVazioOuBranco(retificarContaActionForm.getIdCliente())){

					ClienteConta clienteConta = fachada.pesquisarClienteContaPorTipoRelacao(contaSelecao.getId(),
									ClienteRelacaoTipo.RESPONSAVEL);

					if(clienteConta != null){

						retificarContaActionForm.setIdCliente(clienteConta.getCliente().getId().toString());
						retificarContaActionForm.setNomeCliente(clienteConta.getCliente().getNome());
					}
				}
			}
		}

		if(Util.isNaoNuloBrancoZero(pPermitirAlterarClienteConta) && pPermitirAlterarClienteConta.equals(ConstantesSistema.SIM.toString())){

			String idCliente = retificarContaActionForm.getIdCliente();

			if(!Util.isVazioOuBranco(idCliente)){

				FiltroCliente filtroCliente = new FiltroCliente();
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, Integer.valueOf(idCliente)));

				Collection<Cliente> colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

				if(!Util.isVazioOrNulo(colecaoCliente)){

					Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
					retificarContaActionForm.setIdCliente(cliente.getId().toString());
					retificarContaActionForm.setNomeCliente(cliente.getNome());
				}else{

					httpServletRequest.setAttribute("codigoClienteNaoEncontrado", "true");
					retificarContaActionForm.setIdCliente("");
					retificarContaActionForm.setNomeCliente("CLIENTE INEXISTENTE");
				}
			}
		}else{

			retificarContaActionForm.setIdCliente(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);
			sessao.removeAttribute("permitirAlterarClienteConta");
		}

		if(sessao.getAttribute("colecaoCategoria") != null){
			Collection colecao = (Collection) sessao.getAttribute("colecaoCategoria");
			Iterator iteratorColecaoCategoria = colecao.iterator();

			Categoria categoria = null;
			String quantidadeEconomia = null;
			int valor = 0;
			while(iteratorColecaoCategoria.hasNext()){
				categoria = (Categoria) iteratorColecaoCategoria.next();
				valor++;
				if(requestMap.get("categoria" + categoria.getId()) != null){

					quantidadeEconomia = (requestMap.get("categoria" + categoria.getId()))[0];

					if(quantidadeEconomia == null || quantidadeEconomia.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Quantidade de Economias");
					}

					categoria.setQuantidadeEconomiasCategoria(Integer.valueOf(quantidadeEconomia));
				}
			}
			if(valor == 0){
				sessao.setAttribute("existeColecao", "nao");
			}else{
				sessao.removeAttribute("existeColecao");
			}
		}
		if(sessao.getAttribute("colecaoSubcategoria") != null){
			Collection colecao = (Collection) sessao.getAttribute("colecaoSubcategoria");
			Iterator iteratorColecaoSubcategoria = colecao.iterator();

			Subcategoria subcategoria = null;
			String quantidadeEconomia = null;
			int valor = 0;
			while(iteratorColecaoSubcategoria.hasNext()){
				subcategoria = (Subcategoria) iteratorColecaoSubcategoria.next();
				valor++;
				if(requestMap.get("subcategoria" + subcategoria.getId()) != null){

					quantidadeEconomia = (requestMap.get("subcategoria" + subcategoria.getId()))[0];

					if(quantidadeEconomia == null || quantidadeEconomia.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Quantidade de Economias");
					}

					subcategoria.setQuantidadeEconomias(Integer.valueOf(quantidadeEconomia));
				}
			}
			if(valor == 0){
				sessao.setAttribute("existeColecao", "nao");
			}else{
				sessao.removeAttribute("existeColecao");
			}
		}
		if(sessao.getAttribute("colecaoDebitoCobrado") != null){
			Collection colecaoDebito = (Collection) sessao.getAttribute("colecaoDebitoCobrado");
			Iterator iteratorColecaoDebito = colecaoDebito.iterator();

			DebitoCobrado debitoCobrado = null;
			String valor = null;
			BigDecimal valor2 = new BigDecimal("0.00");

			while(iteratorColecaoDebito.hasNext()){
				debitoCobrado = (DebitoCobrado) iteratorColecaoDebito.next();
				// valor minimo
				if(requestMap.get("debitoCobrado" + GcomAction.obterTimestampIdObjeto(debitoCobrado)) != null){

					valor = (requestMap.get("debitoCobrado" + GcomAction.obterTimestampIdObjeto(debitoCobrado)))[0];

					if(valor == null || valor.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Débito Cobrado");
					}else{
						valor2 = Util.formatarMoedaRealparaBigDecimal(valor);
					}

					debitoCobrado.setValorPrestacao(valor2);
				}
			}
		}
		if(sessao.getAttribute("colecaoCreditoRealizado") != null){
			Collection colecaoCredito = (Collection) sessao.getAttribute("colecaoCreditoRealizado");
			Iterator iteratorColecaoCredito = colecaoCredito.iterator();

			CreditoRealizado creditoRealizado = null;
			String valor = null;
			BigDecimal valor2 = new BigDecimal("0.00");

			while(iteratorColecaoCredito.hasNext()){
				creditoRealizado = (CreditoRealizado) iteratorColecaoCredito.next();
				// valor minimo
				if(requestMap.get("creditoRealizado" + GcomAction.obterTimestampIdObjeto(creditoRealizado)) != null){

					valor = (requestMap.get("creditoRealizado" + GcomAction.obterTimestampIdObjeto(creditoRealizado)))[0];

					if(valor == null || valor.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Crédito Realizado");
					}else{
						valor2 = Util.formatarMoedaRealparaBigDecimal(valor);
					}

					creditoRealizado.setValorCredito(valor2);
				}
			}
		}
		if(sessao.getAttribute("situacaoAguaContaAntes") == null){
			sessao.setAttribute("situacaoAguaContaAntes", retificarContaActionForm.getSituacaoAguaConta());
		}
		if(sessao.getAttribute("situacaoEsgotoContaAntes") == null){
			sessao.setAttribute("situacaoEsgotoContaAntes", retificarContaActionForm.getSituacaoEsgotoConta());
		}
		if(sessao.getAttribute("consumoEsgotoAntes") == null){
			sessao.setAttribute("consumoEsgotoAntes", retificarContaActionForm.getConsumoEsgoto());
		}
		if(sessao.getAttribute("consumoAguaAntes") == null){
			sessao.setAttribute("consumoAguaAntes", retificarContaActionForm.getConsumoAgua());
		}
		if(sessao.getAttribute("percentualEsgotoAntes") == null){
			String percentualEsgotoAntes = null;
			if(retificarContaActionForm.getPercentualEsgoto() != null){
				percentualEsgotoAntes = retificarContaActionForm.getPercentualEsgoto().toString().replace(",", "");
				percentualEsgotoAntes = percentualEsgotoAntes.toString().replace(".", "");
			}else{
				percentualEsgotoAntes = "1";
			}
			sessao.setAttribute("percentualEsgotoAntes", percentualEsgotoAntes);
		}
		if(sessao.getAttribute("vencimentoContaAntes") == null){
			sessao.setAttribute("vencimentoContaAntes", retificarContaActionForm.getVencimentoConta());
		}
		if(sessao.getAttribute("numeroLeituraAtualMedicaoHistoricoAguaAntes") == null){
			sessao.setAttribute("numeroLeituraAtualMedicaoHistoricoAguaAntes",
							retificarContaActionForm.getNumeroLeituraAtualMedicaoHistoricoAgua());
		}
		sessao.setAttribute("contaID", idConta);

		// Limpando os campos após remoção ou inserção de categorias ou débitos
		if(reloadPage != null && !reloadPage.equalsIgnoreCase("")){

			retificarContaActionForm.setValorAgua("");
			retificarContaActionForm.setValorEsgoto("");
			retificarContaActionForm.setValorDebito("");
			retificarContaActionForm.setValorCredito("");
			retificarContaActionForm.setValorTotal("");
		}

		boolean temPermissaoAlterarDataLeitura = false;
		String isPermitidoAlterarDataLeituraAnterior = "";

		if(!temPermissaoAlterarDataLeitura){
			temPermissaoAlterarDataLeitura = fachada.verificarPermissaoAlterarDataLeituraManterHidrometro(usuario);
		}

		if(temPermissaoAlterarDataLeitura){
			isPermitidoAlterarDataLeituraAnterior = ConstantesSistema.SIM.toString();
		}

		httpServletRequest.setAttribute("isPermitidoAlterarDataLeituraAnterior", isPermitidoAlterarDataLeituraAnterior);
		sessao.setAttribute("isPermitidoAlterarDataLeituraAnterior", isPermitidoAlterarDataLeituraAnterior);

		// Permissão para alterar percentual de esgoto
		boolean temPermissaoAlterarPercentualEsgoto = getFachada().verificarPermissaoEspecial(
						PermissaoEspecial.ALTERAR_PERCENTUAL_DE_ESGOTO, usuario);

		String indicadorPermissaoAlterarPercentualEsgoto = ConstantesSistema.NAO.toString();

		if(temPermissaoAlterarPercentualEsgoto){
			indicadorPermissaoAlterarPercentualEsgoto = ConstantesSistema.SIM.toString();
		}

		retificarContaActionForm.setIndicadorPermissaoAlterarPercentualEsgoto(indicadorPermissaoAlterarPercentualEsgoto);

		// Verifica se a situação da ligação de esgoto é faturável, caso não seja limpa e desabilita
		// campos relativos aos dados da ligação de esgoto.
		if(retificarContaActionForm.getIndicadorEsgotoFaturavel() == null
						|| retificarContaActionForm.getIndicadorEsgotoFaturavel().equals(ConstantesSistema.NAO.toString())){

			retificarContaActionForm.setConsumoEsgoto(null);
			retificarContaActionForm.setPercentualEsgoto(null);
			retificarContaActionForm.setLigacaoEsgotoPerfilId(null);
		}

		if(reloadPage == null){

			httpServletRequest.getSession().setAttribute(
							ConstantesSistema.CREDITO_TOTAL_DISPONIVEL,
							Fachada.getInstancia().obterValorTotalDisponivelCreditoArealizarImovel(
											Integer.valueOf(retificarContaActionForm.getIdImovel())));

		}

		String indicadorDuplicarConsumoAguaParaConsumoEsgoto = null;

		try{
			indicadorDuplicarConsumoAguaParaConsumoEsgoto = (String) ParametroFaturamento.P_DUPLICAR_VALOR_CONSUMO_AGUA_PARA_CONSUMO_ESGOTO
							.executar();
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		httpServletRequest.setAttribute("indicadorDuplicarConsumoAguaParaConsumoEsgoto", indicadorDuplicarConsumoAguaParaConsumoEsgoto);

		httpServletRequest.setAttribute("SITUACAO_AGUA_NA_BASE", retificarContaActionForm.getSituacaoAguaConta());
		httpServletRequest.setAttribute("CONSUMO_AGUA_NA_BASE", retificarContaActionForm.getConsumoAgua());

		return retorno;
	}

	/**
	 * Pesquisa a data de leitura (Campo FTAC_TMREALIZACAO da tabela
	 * FATURAMENTO_ATIVIDADE_CRONOGRAMA)
	 * 
	 * @author Ailton Sousa
	 * @date 22/07/2011
	 * @param imovel
	 * @param situacao
	 * @param anoMesReferencia
	 * @param fachada
	 */
	public Date buscarDataLeituraCronograma(Imovel imovel, boolean situacao, Integer anoMesReferencia, Fachada fachada){

		return fachada.buscarDataLeituraCronograma(imovel, situacao, anoMesReferencia);
	}

	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosFaturamento.getInstancia();
	}

	private Boolean existePagamentoAssociado(CreditoRealizado creditoRealizado){

		Boolean retorno = Boolean.FALSE;

		if(creditoRealizado.getCreditoARealizarGeral() != null){

			FiltroCreditoARealizar filtroCreditoARealizar = new FiltroCreditoARealizar();
			filtroCreditoARealizar.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID, creditoRealizado
							.getCreditoARealizarGeral().getId()));
			filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.PAGAMENTO_HISTORICO);

			CreditoARealizar creditoARealizar = (CreditoARealizar) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroCreditoARealizar, CreditoARealizar.class.getName()));

			if(creditoARealizar != null){

				retorno = Boolean.TRUE;

			}else{

				FiltroCreditoARealizarHistorico filtroCreditoARealizarHistorico = new FiltroCreditoARealizarHistorico();
				filtroCreditoARealizarHistorico.adicionarParametro(new ParametroSimples(FiltroCreditoARealizarHistorico.ID,
								creditoRealizado.getCreditoARealizarGeral().getId()));
				filtroCreditoARealizarHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizarHistorico.PAGAMENTO_HISTORICO);

				CreditoARealizarHistorico creditoARealizarHistorico = (CreditoARealizarHistorico) Util.retonarObjetoDeColecao(Fachada
								.getInstancia().pesquisar(filtroCreditoARealizarHistorico, CreditoARealizarHistorico.class.getName()));

				if(creditoARealizarHistorico != null){

					retorno = Boolean.TRUE;

				}

			}

		}

		return retorno;

	}

}
