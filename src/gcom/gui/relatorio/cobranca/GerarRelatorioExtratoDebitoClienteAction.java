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

package gcom.gui.relatorio.cobranca;

import gcom.cadastro.cliente.Cliente;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.cobranca.ConsultarDebitoClienteActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioExtratoDebitoCliente;
import gcom.relatorio.cobranca.parcelamento.ExtratoDebitoRelatorioHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.arrecadacao.ParametroArrecadacao;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gerar e Emitir Extrato de Débito por Cliente
 * 
 * @author Ana Maria
 * @date 04/04/2007
 */

public class GerarRelatorioExtratoDebitoClienteAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarDebitoClienteActionForm consultarDebitoClienteActionForm = (ConsultarDebitoClienteActionForm) actionForm;

		// Linha 2
		String nomeCliente = "";

		// Linha 3
		String enderecoCliente = "";

		// Linha 4
		String tipoResponsavel = "";

		// Linha 11
		String dataEmissao = "";

		Collection<ContaValoresHelper> colecaoContas = null;
		BigDecimal contasValor = new BigDecimal("0.00");
		BigDecimal acrescimoImpontualidade = new BigDecimal("0.00");

		// Consultar Débito
		colecaoContas = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
		String debitosACobrar = (String) sessao.getAttribute("valorDebitoACobrar");
		acrescimoImpontualidade = Util.formatarMoedaRealparaBigDecimal(sessao.getAttribute("valorAcrescimo").toString());
		String valorContas = (String) sessao.getAttribute("valorConta");

		/*
		 * Alterado por Raphael Rossiter em 24/09/2007
		 * OBJ:Obter o codigo do cliente tanto pelo campo de cliente superior como pelo campo
		 * codigo do cliente.
		 */
		Integer idCliente = null;
		if(consultarDebitoClienteActionForm.getCodigoCliente() != null && !consultarDebitoClienteActionForm.getCodigoCliente().equals("")){
			idCliente = new Integer(consultarDebitoClienteActionForm.getCodigoCliente());
			nomeCliente = consultarDebitoClienteActionForm.getNomeCliente();
		}else{

			idCliente = new Integer(consultarDebitoClienteActionForm.getCodigoClienteSuperior());
			nomeCliente = consultarDebitoClienteActionForm.getNomeClienteSuperior();
		}

		Cliente cliente = fachada.pesquisarClienteDigitado(idCliente);

		if(consultarDebitoClienteActionForm.getTipoRelacao() != null){
			tipoResponsavel = consultarDebitoClienteActionForm.getTipoRelacao();
		}

		enderecoCliente = consultarDebitoClienteActionForm.getEnderecoCliente();

		// Parte que vai mandar o relatório para a tela cria uma instância da classe do relatório
		Usuario usuarioLogado = (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado");
		RelatorioExtratoDebitoCliente relatorioExtratoDebitoCliente = new RelatorioExtratoDebitoCliente(usuarioLogado);

		String tipo = (String) httpServletRequest.getParameter("tipo");

		BigDecimal valorDocumento = null;
		BigDecimal valorAcrescimosImpontualidade = null;

		if(httpServletRequest.getParameter("tipo") != null && tipo.equalsIgnoreCase("conta")){
			contasValor = Util.formatarMoedaRealparaBigDecimal((String) sessao.getAttribute("valorConta"));

			// Parâmetro que identifica se a empresa emite o documento com acrescimos
			String parametroTratarAcrescimosEmissaoDocumento = "";
			String parametroPermitirSelecaoAcrescimosExtrato = "";
			try{
				parametroTratarAcrescimosEmissaoDocumento = ParametroArrecadacao.P_TRATAR_ACRESCIMOS_EMISSAO_DOCUMENTO.executar()
								.toString();

				parametroPermitirSelecaoAcrescimosExtrato = ParametroArrecadacao.P_PERMITIR_SELECAO_ACRESCIMOS_EXTRATO.executar()
								.toString();
			}catch(ControladorException ex){
				throw new ActionServletException("erro.parametro.sistema", ex, ex.getMessage());
			}

			boolean temPermissaoEmitirExtratoDeDebitosSemAcrescimo = fachada
							.verificarPermissaoEmitirExtratoDeDebitosoSemAcrescimo(usuarioLogado);

			String pIndicadorEmissaoExtratoDebitoSemAcrescimo = ConstantesSistema.SIM.toString();
			if(temPermissaoEmitirExtratoDeDebitosSemAcrescimo){
				pIndicadorEmissaoExtratoDebitoSemAcrescimo = httpServletRequest.getParameter("acresImp");
			}
			relatorioExtratoDebitoCliente.addParametro("indicadorIncluirAcrescimosImpontualidade",
							pIndicadorEmissaoExtratoDebitoSemAcrescimo);

			if(parametroTratarAcrescimosEmissaoDocumento.equals(Short.toString(ConstantesSistema.SIM))
							|| parametroPermitirSelecaoAcrescimosExtrato.equals(Short.toString(ConstantesSistema.SIM))){

				if(Short.toString(ConstantesSistema.SIM).equals(pIndicadorEmissaoExtratoDebitoSemAcrescimo)){
					valorDocumento = contasValor.add(acrescimoImpontualidade);
					valorAcrescimosImpontualidade = acrescimoImpontualidade;

					relatorioExtratoDebitoCliente.addParametro("acrescimoImpontualidade", Util.formatarMoedaReal(acrescimoImpontualidade));
				}else{
					valorDocumento = contasValor;
					valorAcrescimosImpontualidade = BigDecimal.ZERO;
				}
			}else{
				valorDocumento = contasValor;
				valorAcrescimosImpontualidade = BigDecimal.ZERO;
			}

			ExtratoDebitoRelatorioHelper extratoDebitoRelatorioHelper = fachada.gerarEmitirExtratoDebito(null, new Short("0"),
							colecaoContas, null, null, valorAcrescimosImpontualidade, new BigDecimal("0.00"), valorDocumento, null,
							cliente, null, null);

			CobrancaDocumento documentoCobranca = extratoDebitoRelatorioHelper.getColecaoCobrancaDocumentoItemContas().iterator().next()
							.getCobrancaDocumento();
			// Linha 3
			String seqDocCobranca = "" + documentoCobranca.getNumeroSequenciaDocumento();
			relatorioExtratoDebitoCliente.addParametro("seqDocCobranca", seqDocCobranca);

			// Linha 4
			dataEmissao = Util.formatarData(documentoCobranca.getEmissao());

			String dataValidade = Util.formatarData(documentoCobranca.getDataValidade());

			// Linha 15
			relatorioExtratoDebitoCliente.addParametro("dataValidade", dataValidade);

			String representacaoNumericaCodBarra = "";

			// [UC0229] Obtém a representação numérica do código de barra

			representacaoNumericaCodBarra = fachada.obterRepresentacaoNumericaCodigoBarra(8, valorDocumento, 0, null, null, null, null,
							null, seqDocCobranca, documentoCobranca.getDocumentoTipo().getId(), idCliente, null, null, null, null);

			// Formata a representação númerica do código de barras
			String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11) + "-"
							+ representacaoNumericaCodBarra.substring(11, 12) + " " + representacaoNumericaCodBarra.substring(12, 23) + "-"
							+ representacaoNumericaCodBarra.substring(23, 24) + " " + representacaoNumericaCodBarra.substring(24, 35) + "-"
							+ representacaoNumericaCodBarra.substring(35, 36) + " " + representacaoNumericaCodBarra.substring(36, 47) + "-"
							+ representacaoNumericaCodBarra.substring(47, 48);

			relatorioExtratoDebitoCliente.addParametro("representacaoNumericaCodBarra", representacaoNumericaCodBarraFormatada);

			String representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarra.substring(0, 11)
							+ representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35)
							+ representacaoNumericaCodBarra.substring(36, 47);

			relatorioExtratoDebitoCliente.addParametro("representacaoNumericaCodBarraSemDigito", representacaoNumericaCodBarraSemDigito);
		}else{
			valorDocumento = Util.formatarMoedaRealparaBigDecimal((String) sessao.getAttribute("valorTotalComAcrescimo"));
			relatorioExtratoDebitoCliente.addParametro("debitosACobrar", debitosACobrar);
			relatorioExtratoDebitoCliente.addParametro("acrescimoImpontualidade", Util.formatarMoedaReal(acrescimoImpontualidade));
		}

		// Linha 2
		relatorioExtratoDebitoCliente.addParametro("nomeCliente", nomeCliente);
		relatorioExtratoDebitoCliente.addParametro("codigoClienteResponsavel", idCliente.toString());

		// Linha 3
		relatorioExtratoDebitoCliente.addParametro("enderecoCliente", enderecoCliente);

		// Linha 4
		relatorioExtratoDebitoCliente.addParametro("tipoResponsavel", tipoResponsavel);

		// Linhas 11
		relatorioExtratoDebitoCliente.addParametro("dataEmissao", dataEmissao);
		relatorioExtratoDebitoCliente.addParametro("valorContas", valorContas);

		relatorioExtratoDebitoCliente.addParametro("valorTotalContas", Util.formatarMoedaReal(valorDocumento));

		relatorioExtratoDebitoCliente.addParametro("colecaoContas", colecaoContas);

		String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";

		relatorioExtratoDebitoCliente.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		try{
			retorno = processarExibicaoRelatorio(relatorioExtratoDebitoCliente, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(RelatorioVazioException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");

		}

		return retorno;
	}

}
