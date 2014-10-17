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

package gcom.gui.relatorio.faturamento.conta;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaCategoriaConsumoFaixa;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.faturamento.conta.ManterContaConjuntoImovelActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.conta.Relatorio2ViaConta;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0482] Emitir 2� Via de Conta
 * 
 * @author Vivianne Sousa
 * @date 15/09/2006
 */

public class GerarRelatorio2ViaContaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	private static final Integer CODIGO_SEGUNDA_VIA = 1;

	private static final Integer CODIGO_ENTRADA_PARCELAMENTO = 2;

	private static final Integer CODIGO_CAUCAO = 3;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ManterContaConjuntoImovelActionForm manterContaConjuntoImovelActionForm = (ManterContaConjuntoImovelActionForm) actionForm;
		
		Collection idsConta = new ArrayList();
		Integer idContaHistorico = null;
		String contaSelected = null;
		String idNomeRelatorio = httpServletRequest.getParameter("idNomeRelatorio");
		httpServletRequest.setAttribute("idNomeRelatorio", idNomeRelatorio);
		boolean validouContasPrescritas = false;

		// Indicador da opera��o caucionamento de conta
		String indicadorOperacao = null;
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("indicadorOperacao"))){
			indicadorOperacao = (String) httpServletRequest.getParameter("indicadorOperacao");
		}else{
			indicadorOperacao = (String) sessao.getAttribute("indicadorOperacao");
		}
		
		// Armazena as contas selecionadas
		Collection<Conta> colecaoContasSelecionadas = new ArrayList<Conta>();

		// Carregando contas selecionadas
		contaSelected = manterContaConjuntoImovelActionForm.getContaSelected();

		if(!Util.isVazioOuBranco(contaSelected)){
			// Contas selecionadas pelo usu�rio
			String[] arrayIdentificadores = contaSelected.split(",");

			for(int i = 0; i < arrayIdentificadores.length; i++){

				String dadosConta = arrayIdentificadores[i];
				String[] idContaArray = dadosConta.split("-");
				Integer idConta = new Integer(idContaArray[0]);

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

			// [FS0039] - Verifica exist�ncia de d�bito prescrito
			fachada.verificarContaPrescrita(getUsuarioLogado(httpServletRequest), colecaoContasSelecionadas);
			validouContasPrescritas = true;

			sessao.setAttribute("colecaoContasSelecionadas", colecaoContasSelecionadas);
		}

		// Recupera dados do caucionamento
		Map<Conta, Collection<Collection<ContaCategoriaConsumoFaixa>>> caucionamento = (Map<Conta, Collection<Collection<ContaCategoriaConsumoFaixa>>>) sessao
						.getAttribute("caucionamento");

		if(sessao.getAttribute("idContaHistorico") != null && !sessao.getAttribute("idContaHistorico").equals("")){
			// Consultar Imovel (aba Faturamento)
			idContaHistorico = new Integer("" + sessao.getAttribute("idContaHistorico"));
			idsConta.add(idContaHistorico);
		}else if(sessao.getAttribute("idConta") != null && !sessao.getAttribute("idConta").equals("")){
			idsConta.add(new Integer("" + sessao.getAttribute("idConta")));
		}else if(httpServletRequest.getParameter("idConta") != null && !httpServletRequest.getParameter("idConta").equals("")){
			idsConta.add(new Integer("" + httpServletRequest.getParameter("idConta")));
		}else if(sessao.getAttribute("idsContaEP") != null){
			// Parcelamento
			idsConta = (Collection) sessao.getAttribute("idsContaEP");
		}else if(sessao.getAttribute("colecaoImovel") != null){

			Integer anoMes = 0;
			if(httpServletRequest.getParameter("mesAno") != null){
				anoMes = Util.formatarMesAnoComBarraParaAnoMes(httpServletRequest.getParameter("mesAno"));
				sessao.setAttribute("anoMes", anoMes);
			}else{
				if(!Util.isVazioOuBranco(sessao.getAttribute("anoMes"))){
					anoMes = new Integer("" + sessao.getAttribute("anoMes"));
				}
			}

			Integer anoMesFim = anoMes;
			if(httpServletRequest.getParameter("mesAnoFim") != null){
				anoMesFim = Util.formatarMesAnoComBarraParaAnoMes(httpServletRequest.getParameter("mesAnoFim"));
				sessao.setAttribute("anoMesFim", anoMesFim);
			}else if(sessao.getAttribute("anoMesFim") != null){
				anoMesFim = new Integer("" + sessao.getAttribute("anoMesFim"));
				sessao.removeAttribute("anoMesFim");
			}

			Date dataVencimentoContaInicio = null;
			Date dataVencimentoContaFim = null;
			Integer idGrupoFaturamento = null;

			String dataVencimentoContaInicioParam = httpServletRequest.getParameter("dataVencimentoContaInicial");

			if(!Util.isVazioOuBranco(dataVencimentoContaInicioParam)){

				dataVencimentoContaInicio = Util.converteStringParaDate(httpServletRequest.getParameter("dataVencimentoContaInicial"));
				sessao.setAttribute("dataVencimentoContaInicial", dataVencimentoContaInicio);
			}else{
				if(!Util.isVazioOuBranco(sessao.getAttribute("dataVencimentoContaInicial"))){
					dataVencimentoContaInicio = (Date) sessao.getAttribute("dataVencimentoContaInicial");
				}

			}

			String dataVencimentoContaFimParam = httpServletRequest.getParameter("dataVencimentoContaFinal");
			if(!Util.isVazioOuBranco(dataVencimentoContaFimParam)){

				dataVencimentoContaFim = Util.converteStringParaDate(httpServletRequest.getParameter("dataVencimentoContaFinal"));
				sessao.setAttribute("dataVencimentoContaFinal", dataVencimentoContaFim);
			}else{
				if(!Util.isVazioOuBranco(sessao.getAttribute("dataVencimentoContaFinal"))){
					dataVencimentoContaFim = (Date) sessao.getAttribute("dataVencimentoContaFinal");
				}

			}

			String idGrupoFaturamentoParam = httpServletRequest.getParameter("idGrupoFaturamento");
			if(idGrupoFaturamentoParam != null && !idGrupoFaturamentoParam.equals("")){

				idGrupoFaturamento = new Integer((String) httpServletRequest.getParameter("idGrupoFaturamento"));
				sessao.setAttribute("idGrupoFaturamento", idGrupoFaturamento);
			}else{
				if(!Util.isVazioOuBranco(sessao.getAttribute("idGrupoFaturamento"))){
					idGrupoFaturamento = (Integer) sessao.getAttribute("idGrupoFaturamento");
				}

			}

			Collection colecaoImovel = (Collection) sessao.getAttribute("colecaoImovel");

			// Recupera as contas selecionadas
			Collection<Conta> colecaoImoveisConjuntoContasSelecionadas = (Collection) sessao.getAttribute("colecaoContasSelecionadas");

			if(!Util.isVazioOrNulo(colecaoImoveisConjuntoContasSelecionadas)){
				// Contas selecionadas pelo usu�rio
				for(Conta conta : colecaoImoveisConjuntoContasSelecionadas){
					idsConta.add(conta.getId());
				}
			}else{

				Integer codigoCliente = null;
				if(sessao.getAttribute("codigoCliente") != null){
					codigoCliente = new Integer((String) sessao.getAttribute("codigoCliente"));
				}
				if(codigoCliente != null){
					Integer relacaoTipo = null;
					if(sessao.getAttribute("relacaoTipo") != null){
						relacaoTipo = new Integer(sessao.getAttribute("relacaoTipo") + "");
					}
					idsConta = fachada.pesquisarConjuntoContaClienteEmitir2Via(codigoCliente, relacaoTipo, anoMes,
									dataVencimentoContaInicio, dataVencimentoContaFim, anoMesFim);
				}else if(idGrupoFaturamento != null){

					idsConta = fachada.pesquisarConjuntoContaEmitir2Via(idGrupoFaturamento, anoMes, dataVencimentoContaInicio,
									dataVencimentoContaFim, anoMesFim);
				}else{

					idsConta = fachada.pesquisarConjuntoContaEmitir2Via(colecaoImovel, anoMes, dataVencimentoContaInicio,
									dataVencimentoContaFim, anoMesFim);
				}
			}

			Integer qtdeContas = Integer.valueOf("0");

			if(!Util.isVazioOuBranco(httpServletRequest.getParameter("qtdeContas"))){
				qtdeContas = new Integer(httpServletRequest.getParameter("qtdeContas"));
				sessao.setAttribute("qtdeContas", qtdeContas);
			}else if(!Util.isVazioOuBranco(sessao.getAttribute("qtdeContas"))){
				qtdeContas = (Integer) sessao.getAttribute("qtdeContas");
			}
		}else{
			// a partir do Manter Conta
			contaSelected = httpServletRequest.getParameter("conta");

			if(contaSelected == null || contaSelected.equals("")){
				if(sessao.getAttribute("contaSelected") != null){
					contaSelected = "" + sessao.getAttribute("contaSelected");
					sessao.removeAttribute("contaSelected");
				}else{
					throw new ActionServletException("atencao.conta.nao.localizada");
				}
			}

			String[] arrayIdentificadores = contaSelected.split(",");

			for(int i = 0; i < arrayIdentificadores.length; i++){
				String dadosConta = arrayIdentificadores[i];
				String[] idContaArray = dadosConta.split("-");
				Integer idConta = new Integer(idContaArray[0]);

				// [FS0021] Verificar situa��o da conta
				FiltroConta filtroConta = new FiltroConta();
				filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
				filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);
				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
				Collection colecaoConta = fachada.pesquisar(filtroConta, Conta.class.getName());
				Conta contaSelecao = (Conta) colecaoConta.iterator().next();
				if(contaSelecao != null){
					if(!fachada.verificarSituacaoContaPermitida(contaSelecao)){
						throw new ActionServletException("atencao.conta_em_situacao_nao_permitida", contaSelecao
										.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao(), "a��o");
					}
				}

				idsConta.add(idConta);
			}
		}

		Collection colecaoContaSelecaoImpressaoSegundaVia = null;
		FiltroConta filtroConta = new FiltroConta();
		filtroConta.adicionarParametro(new ParametroSimplesColecao(FiltroConta.ID, idsConta));
		colecaoContaSelecaoImpressaoSegundaVia = fachada.pesquisar(filtroConta, Conta.class.getName());
		Conta contaSelecaoMotivo = (Conta) Util.retonarObjetoDeColecao(colecaoContaSelecaoImpressaoSegundaVia);

		if(!validouContasPrescritas){

			// [FS0039] - Verifica exist�ncia de d�bito prescrito
			fachada.verificarContaPrescrita(getUsuarioLogado(httpServletRequest), colecaoContaSelecaoImpressaoSegundaVia);
		}

		boolean caucionarConta = false;
		if(!Util.isVazioOuBranco(indicadorOperacao) && indicadorOperacao.equals("caucionar")){

			caucionarConta = true;
		}

		String enderecoURL = httpServletRequest.getServletPath();
		// Caso n�o seja caucionamento verifica se a conta est� em revis�o
		if(idsConta != null && !idsConta.isEmpty() && !caucionarConta){

			// Caso n�o seja conta de entrada de parcelamento critica todos os motivos de revis�o
			if(sessao.getAttribute("idsContaEP") == null){

				fachada.verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(idsConta, getUsuarioLogado(httpServletRequest), true, true,
								enderecoURL);
			}else{

				// Caso contr�rio critica apenas os motivos de revis�o parametrizados
				fachada.verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(idsConta, getUsuarioLogado(httpServletRequest), true,
								false, enderecoURL);
			}
		}


		// [FS0013] � Verificar se conta em d�bito autom�tico
		// . Caso a conta cadastrada para d�bito autom�tico (CNTA_ICCONTADEBITO = 1) e o indicador
		// de Emiss�o de 2�. Via de Conta Conta com c�digo de barras para clientes com d�bito
		// autom�tico com valor igual a N�O (PASI_VLPARAMETRO = 2 na da tabela PARAMETRO_SISTEMA com
		// PASI_CDPARAMETRO=� P_EMITE_2VIA_COM_CDBARRAS_CTA_DEB_AUTOM�), gerar linha em branco.
		Short contaSemCodigoBarras = ConstantesSistema.NAO;
		if(contaSelecaoMotivo != null && contaSelecaoMotivo.getIndicadorDebitoConta().equals(ConstantesSistema.SIM)){

			String pEmite2ViaCodBarrasContaDebAut = null;
			try{
				pEmite2ViaCodBarrasContaDebAut = ParametroFaturamento.P_EMITE_2VIA_COM_CDBARRAS_CTA_DEB_AUTOM.executar();
				if(!Util.isVazioOuBrancoOuZero(pEmite2ViaCodBarrasContaDebAut)
								&& pEmite2ViaCodBarrasContaDebAut.equals(ConstantesSistema.NAO.toString())){
					contaSemCodigoBarras = ConstantesSistema.SIM;
					sessao.setAttribute("contaSemCodigoBarras", contaSemCodigoBarras);
				}

			}catch(ControladorException e){
				throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_EMITE_2VIA_COM_CDBARRAS_CTA_DEB_AUTOM");
			}

		}else{
			if(httpServletRequest.getParameter("contaSemCodigoBarras") != null
							&& httpServletRequest.getParameter("contaSemCodigoBarras").equals("1")){
				contaSemCodigoBarras = ConstantesSistema.SIM;
				sessao.setAttribute("contaSemCodigoBarras", contaSemCodigoBarras);
			}else if(sessao.getAttribute("contaSemCodigoBarras") != null){
				contaSemCodigoBarras = (Short) sessao.getAttribute("contaSemCodigoBarras");
			}

		}

		boolean cobrarTaxaEmissaoConta = true;
		String confirmado = httpServletRequest.getParameter("confirmado");
		boolean permissaoNaoCobrarTaxaSegundaVia = fachada.verificarPermissaoNaoCobrarTaxaSegundaVia(getUsuarioLogado(httpServletRequest));

		// Verifica se � poss�vel gerar d�bito ao emitir 2a. via
		if(httpServletRequest.getParameter("cobrarTaxaEmissaoConta") != null
						&& httpServletRequest.getParameter("cobrarTaxaEmissaoConta").equals("N")){
			cobrarTaxaEmissaoConta = false;
			sessao.setAttribute("cobrarTaxaEmissaoConta", cobrarTaxaEmissaoConta);

		}else{

			// Verifica se j� entrou na tela de confirma��o
			if(confirmado != null){

				// Caso o usu�rio tenha informado na tela para N�O gerar d�bito
				if(confirmado.equals("cancelar")){
					cobrarTaxaEmissaoConta = false;
				}else{
					// Caso o usu�rio tenha informado na tela para SIM gerar d�bito
					cobrarTaxaEmissaoConta = true;
				}

				// Caso n�o tenha entrado na tela de confirma��o ainda
			}else{
				httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/gerarRelatorio2ViaContaAction.do");
				httpServletRequest.setAttribute("cancelamento", "TRUE");
				httpServletRequest.setAttribute("nomeBotao1", "Sim");
				httpServletRequest.setAttribute("nomeBotao2", "N�o");

				if(!permissaoNaoCobrarTaxaSegundaVia){
					httpServletRequest.setAttribute("disableBotao2", "disable");
					httpServletRequest
									.setAttribute("titleBotao2", "Acesso negado. Voc� n�o tem permiss�o de acesso a esta funcionalidade.");
				}
				httpServletRequest.setAttribute("voltar", "TRUE");

				if(httpServletRequest.getParameter("idConta") != null && !httpServletRequest.getParameter("idConta").equals("")){
					sessao.setAttribute("idConta", httpServletRequest.getParameter("idConta"));
				}

				if(contaSelected != null && !contaSelected.equals("")){
					sessao.setAttribute("contaSelected", contaSelected);
				}

				return montarPaginaConfirmacao("atencao.confirmacao.2_via_conta.gerar_debito", httpServletRequest, actionMapping);
			}
		}

		if(!permissaoNaoCobrarTaxaSegundaVia && !cobrarTaxaEmissaoConta){
			throw new ActionServletException("atencao.usuario.permissao_negada");
		}

		Integer qtdeContas = new Integer("0");

		if(httpServletRequest.getParameter("qtdeContas") != null){
			qtdeContas = new Integer(httpServletRequest.getParameter("qtdeContas"));
			sessao.setAttribute("qtdeContas", qtdeContas);
		}else if(sessao.getAttribute("qtdeContas") != null){
			qtdeContas = (Integer) sessao.getAttribute("qtdeContas");
		}

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		String nomeEmpresa = sistemaParametro.getNomeAbreviadoEmpresa();

		// Parte que vai mandar o relat�rio para a tela
		// cria uma inst�ncia da classe do relat�rio
		Relatorio2ViaConta relatorio2ViaConta = new Relatorio2ViaConta(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorio2ViaConta.addParametro("idsConta", idsConta);
		relatorio2ViaConta.addParametro("cobrarTaxaEmissaoConta", cobrarTaxaEmissaoConta);
		relatorio2ViaConta.addParametro("contaSemCodigoBarras", contaSemCodigoBarras);
		relatorio2ViaConta.addParametro("qtdeContas", qtdeContas);

		String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";

		relatorio2ViaConta.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		relatorio2ViaConta.addParametro("nomeEmpresa", nomeEmpresa);
		relatorio2ViaConta.addParametro("nomeRelatorio", this.obterNomeRelatorio(idNomeRelatorio));
		relatorio2ViaConta.addParametro("idContaHistorico", idContaHistorico);

		// Verifica se a opera��o � de caucionamento de conta
		if(!Util.isVazioOuBranco(indicadorOperacao) && indicadorOperacao.equals("caucionar")){
			if(!Util.isVazioOuBranco(sessao.getAttribute("caucionamento"))){
				// Incluir o indicador de caucioamento como parametro do relat�rio
				relatorio2ViaConta.addParametro("indicadorOperacao", indicadorOperacao);
				// Incluir a colecao com a conta caucionada como parametro do relat�rio
				relatorio2ViaConta.addParametro("caucionamento", caucionamento);
				// Incluir a colecao dos dados medi��o leitura
				relatorio2ViaConta.addParametro("colecaoDadosMedicaoLeitura", sessao.getAttribute("colecaoDadosMedicaoLeitura"));
			}
		}

		try{

			retorno = processarExibicaoRelatorio(relatorio2ViaConta, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		}catch(RelatorioVazioException ex){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}

	private String obterNomeRelatorio(String idNomeRelatorio){

		String retorno = "";

		if(idNomeRelatorio != null && StringUtils.isNumeric(idNomeRelatorio)){

			Integer id = Integer.valueOf(idNomeRelatorio);

			if(id.equals(CODIGO_SEGUNDA_VIA)){

				retorno = "2� VIA";

			}else if(id.equals(CODIGO_ENTRADA_PARCELAMENTO)){

				retorno = "ENTRADA DE PARCELAMENTO";

			}else if(id.equals(CODIGO_CAUCAO)){

				retorno = "CAU��O";

			}

		}

		return retorno;

	}

}
