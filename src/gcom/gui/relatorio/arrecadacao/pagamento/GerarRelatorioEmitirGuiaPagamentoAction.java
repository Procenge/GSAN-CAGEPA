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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.gui.relatorio.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacaoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.arrecadacao.pagamento.ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm;
import gcom.gui.cadastro.imovel.CategoriaActionForm;
import gcom.gui.cobranca.ParcelamentoDebitoActionForm;
import gcom.gui.faturamento.ManterGuiaPagamentoActionForm;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.gui.faturamento.guiapagamento.AtualizarGuiaPagamentoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.pagamento.RelatorioEmitirGuiaPagamento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0379] Emitir Guia de Pagamento
 * 
 * @author Vivianne Sousa
 * @date 22/09/2006
 * @author eduardo henrique
 * @date 20/08/2008
 *       Alteração para Impressão de Guia de Pagamento por Prestação
 */

public class GerarRelatorioEmitirGuiaPagamentoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		String[] ids = null;
		Collection<GuiaPagamentoPrestacaoHelper> guiasPagamentoPrestacao = (Collection<GuiaPagamentoPrestacaoHelper>) sessao
						.getAttribute("guiasPagamentos");
		Integer idGuiaPagamento = null;

		if(actionForm instanceof ManterGuiaPagamentoActionForm){
			// tela de Cancelar Guia de Parcelamento
			ManterGuiaPagamentoActionForm manterGuiaPagamentoActionForm = (ManterGuiaPagamentoActionForm) actionForm;
			ids = manterGuiaPagamentoActionForm.getIdRegistrosRemocao();

			// Forma antiga de imprimir a guia
		} /*
		 * else if (httpServletRequest.getParameter("idGuiaPagamento") != null) {
		 * // tela de efetuar parcelamento
		 * ids = new String[1];
		 * String guiaPagamento = (String) httpServletRequest.getParameter("idGuiaPagamento");
		 * ids[0] = guiaPagamento;
		 * // se a impressão vem da categoria ou tela de efetuar parcelamento
		 * }
		 */
		else if((actionForm instanceof CategoriaActionForm)){
			// Tenta obter identificação da Guia pela sessão
			idGuiaPagamento = (Integer) sessao.getAttribute("idGuiaPagamento");

			if(idGuiaPagamento == null){
				idGuiaPagamento = new Integer(httpServletRequest.getParameter("idGuiaPagamento"));
			}

			FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, idGuiaPagamento));

			Collection colecaoGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());
			GuiaPagamento guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);

			// escolheu imprimir a guia após inclusão, neste caso, irá se 'criar' a seleção como
			// sendo todas as parcelas
			List idSelecionados = new ArrayList();
			guiasPagamentoPrestacao = this.montarHelperPrestacoesImpressaoGuiaPagamento(guiaPagamento);
			for(GuiaPagamentoPrestacaoHelper guiaPrestacaoHelper : guiasPagamentoPrestacao){
				idSelecionados.add(guiaPrestacaoHelper.getId().toString());
			}

			ids = (String[]) idSelecionados.toArray(new String[idSelecionados.size()]);

		}else if(actionForm instanceof AtualizarGuiaPagamentoActionForm){
			// tela de Manter Guia de Parcelamento
			AtualizarGuiaPagamentoActionForm atualizarGuiaPagamentoActionForm = (AtualizarGuiaPagamentoActionForm) actionForm;
			ids = atualizarGuiaPagamentoActionForm.getIdRegistrosRemocao();
			guiasPagamentoPrestacao = (Collection<GuiaPagamentoPrestacaoHelper>) sessao.getAttribute("colecaoGuiasPrestacoes");

			GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper = (GuiaPagamentoPrestacaoHelper) Util
							.retonarObjetoDeColecao(guiasPagamentoPrestacao);
			idGuiaPagamento = guiaPagamentoPrestacaoHelper.getIdGuiaPagamento();

			Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
			String idImovel = atualizarGuiaPagamentoActionForm.getIdImovel();

			// Monta a colecao com as guias contempladas no checklist
			Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacaoValidacao = new ArrayList<GuiaPagamentoPrestacaoHelper>();
			Collection<String> listaArray = Arrays.asList(ids);
			for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacao : (Collection<GuiaPagamentoPrestacaoHelper>) guiasPagamentoPrestacao){
				if(listaArray.contains(guiaPagamentoPrestacao.getId().toString())){
					colecaoGuiaPagamentoPrestacaoValidacao.add(guiaPagamentoPrestacao);
				}
			}

			if(!Util.isVazioOuBranco(idImovel)){
				// [SB0005] – Validar autorização de acesso a prestação da guia de imóvel
				fachada.validarAutorizacaoAcessoPrestacaoGuiaImovel(usuarioLogado, Integer.valueOf(idImovel),
								colecaoGuiaPagamentoPrestacaoValidacao);
			}

			// [SB0010] Verificar Guia de Pagamento Prescrita
			fachada.verificarGuiaPagamentoPrescrita(usuarioLogado, colecaoGuiaPagamentoPrestacaoValidacao);

			// Verifica se a guia pode ser impressa
			Collection colecaoBoletos = fachada.pesquisarBoletoEmissaoGuiaPagamento(idGuiaPagamento);

			if(!Util.isVazioOrNulo(colecaoBoletos)){
				/*
				 * Caso já tenha sido gerado o boleto para a guia (existe ocorrência na tabela
				 * BOLETO_BANCARIO com GPAG_ID=Id da Guia selecionada e BBCO_NNPRESTACAO=Número da
				 * prestação da Guia selecionada) e o boleto esteja ativo (BBST_IDATUAL com o valor
				 * correspondente a “EM CARTEIRA” ou “EM CARTORIO” na tabela BOLETO_BANCARIO)
				 * Caso contrário, caso a prestação da guia selecionada para ser impressa não
				 * corresponda a primeira prestação da guia (GPPR_NNPRESTACAO com o valor diferente
				 * de 1 (um))
				 */
				FiltroGuiaPagamentoPrestacaoHistorico filtroGuiaPagamentoPrestacaoHistorico = null;
				Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoPrestacaoHistorico = null;

				for(String idRemocao : ids){
					for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacao : guiasPagamentoPrestacao){
						if(idRemocao.equals(guiaPagamentoPrestacao.getId().toString())){

							filtroGuiaPagamentoPrestacaoHistorico = new FiltroGuiaPagamentoPrestacaoHistorico();
							filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacaoHistorico.GUIA_PAGAMENTO_ID, guiaPagamentoPrestacao
															.getIdGuiaPagamento()));
							filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacaoHistorico.NUMERO_PRESTACAO, guiaPagamentoPrestacao
															.getNumeroPrestacao()));

							colecaoGuiaPagamentoPrestacaoHistorico = fachada.pesquisar(filtroGuiaPagamentoPrestacaoHistorico,
											GuiaPagamentoPrestacaoHistorico.class.getName());

							// Caso a prestação da guia não esteja no histórico
							if(Util.isVazioOrNulo(colecaoGuiaPagamentoPrestacaoHistorico)){
								Collection<Object[]> colecaoRetorno = fachada.pesquisarBoletoGeradoGuiaPagamento(
												guiaPagamentoPrestacao.getIdGuiaPagamento(), guiaPagamentoPrestacao.getNumeroPrestacao());

								if(!Util.isVazioOrNulo(colecaoRetorno)){
									Object[] parametroMensagem = (Object[]) Util.retonarObjetoDeColecao(colecaoRetorno);
									throw new ActionServletException("atencao.guia_nao_pode_ser_impressa_situacao",
													(String) parametroMensagem[0],
													(String) parametroMensagem[1]);
								}else if(guiaPagamentoPrestacao.getNumeroPrestacao() != 1){
									String parametroMensagem = (String) Util.retonarObjetoDeColecao(colecaoBoletos);
									throw new ActionServletException("atencao.guia_nao_pode_ser_impressa", parametroMensagem);
								}
							}
						}
					}
				}
			}

		}else if(actionForm instanceof ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm){
			// tela de Cancelar Guia de Parcelamento
			ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm exibirFiltrarGuiaPagtGeracaoDebAutomActionForm = (ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm) actionForm;
			guiasPagamentoPrestacao = (Collection<GuiaPagamentoPrestacaoHelper>) sessao.getAttribute("colecaoGuiaPagamentoPrestacao");
			Collection<GuiaPagamentoPrestacaoHelper> colecaoPrestacaoSelecionadas = new ArrayList<GuiaPagamentoPrestacaoHelper>();
			List<Integer> colecaoIdGuiaPrestacaoSelecionadaAux = new ArrayList<Integer>();

			for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : guiasPagamentoPrestacao){

				for(String idGuiaPrestacaoHelper : exibirFiltrarGuiaPagtGeracaoDebAutomActionForm.getIdRegistrosGeracaoDebitoAutomatico()){

					String[] guiaPrestacaoSelecionados = idGuiaPrestacaoHelper.split(",");
					Integer idGuia = Util.obterInteger(guiaPrestacaoSelecionados[0]);
					Short numeroPrestacao = Util.obterShort(guiaPrestacaoSelecionados[1]);

					if(guiaPagamentoPrestacaoHelper.getId().intValue() == idGuia.intValue()
									&& guiaPagamentoPrestacaoHelper.getNumeroPrestacao().shortValue() == numeroPrestacao.shortValue()){

						colecaoPrestacaoSelecionadas.add(guiaPagamentoPrestacaoHelper);

						if(!colecaoIdGuiaPrestacaoSelecionadaAux.contains(guiaPagamentoPrestacaoHelper.getIdGuiaPagamento())){

							colecaoIdGuiaPrestacaoSelecionadaAux.add(guiaPagamentoPrestacaoHelper.getIdGuiaPagamento());
						}
					}
				}
			}

			// Recupera os ids das guias selecionadas
			ids = new String[colecaoIdGuiaPrestacaoSelecionadaAux.size()];
			for(int i = 0; i < colecaoIdGuiaPrestacaoSelecionadaAux.size(); i++){

				ids[i] = colecaoIdGuiaPrestacaoSelecionadaAux.get(i).toString();
			}

			GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper = (GuiaPagamentoPrestacaoHelper) Util
							.retonarObjetoDeColecao(guiasPagamentoPrestacao);
			idGuiaPagamento = guiaPagamentoPrestacaoHelper.getIdGuiaPagamento();

			// Verifica se a guia pode ser impressa
			Collection colecaoBoletos = fachada.pesquisarBoletoEmissaoGuiaPagamento(idGuiaPagamento);

			if(!Util.isVazioOrNulo(colecaoBoletos)){
				for(String IdRegistrosGeracaoDebitoAutomatico : ids){
					for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacao : guiasPagamentoPrestacao){
						if(IdRegistrosGeracaoDebitoAutomatico.equals(guiaPagamentoPrestacao.getId().toString())){

							Collection<Object[]> colecaoRetorno = fachada.pesquisarBoletoGeradoGuiaPagamento(
											guiaPagamentoPrestacao.getIdGuiaPagamento(), guiaPagamentoPrestacao.getNumeroPrestacao());

							if(!Util.isVazioOrNulo(colecaoRetorno)){
								Object[] parametroMensagem = (Object[]) Util.retonarObjetoDeColecao(colecaoRetorno);
								throw new ActionServletException("atencao.guia_nao_pode_ser_impressa_situacao",
												(String) parametroMensagem[0], (String) parametroMensagem[1]);
							}else if(guiaPagamentoPrestacao.getNumeroPrestacao() != 1){
								String parametroMensagem = (String) Util.retonarObjetoDeColecao(colecaoBoletos);
								throw new ActionServletException("atencao.guia_nao_pode_ser_impressa", parametroMensagem);
							}
						}
					}
				}
			}

			RelatorioEmitirGuiaPagamento relatorioEmitirGuiaPagamento = new RelatorioEmitirGuiaPagamento(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorioEmitirGuiaPagamento.addParametro("ids", ids);
			relatorioEmitirGuiaPagamento.addParametro("colecaoPrestacoesSelecionadas", colecaoPrestacaoSelecionadas);

			if(sessao.getAttribute("exibirNuContratoParcOrgaoPublico") != null){
				relatorioEmitirGuiaPagamento.addParametro("exibirNuContratoParcOrgaoPublico",
								sessao.getAttribute("exibirNuContratoParcOrgaoPublico"));
			}

			String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";

			relatorioEmitirGuiaPagamento.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

			try{

				retorno = processarExibicaoRelatorio(relatorioEmitirGuiaPagamento, tipoRelatorio, httpServletRequest, httpServletResponse,
								actionMapping);

			}catch(RelatorioVazioException ex){

				reportarErros(httpServletRequest, "atencao.relatorio.vazio");
				retorno = actionMapping.findForward("telaAtencaoPopup");
			}

			return retorno;

		}else if(actionForm instanceof ParcelamentoDebitoActionForm){

			// tela de Manter Guia de Parcelamento
			ParcelamentoDebitoActionForm form = (ParcelamentoDebitoActionForm) actionForm;
			List idSelecionados = new ArrayList();

			Integer idParcelamento = null;
			FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
			if(httpServletRequest.getParameter("idParcelamento") != null){
				idParcelamento = new Integer(httpServletRequest.getParameter("idParcelamento"));
				filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, idParcelamento));
			}else{

				idGuiaPagamento = (Integer) sessao.getAttribute("idGuiaPagamento");
				if(idGuiaPagamento == null){
					idGuiaPagamento = new Integer(httpServletRequest.getParameter("idGuiaPagamento"));
				}
				filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, idGuiaPagamento));
			}

			Collection colecaoGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());
			GuiaPagamento guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
			guiasPagamentoPrestacao = this.montarHelperPrestacoesImpressaoGuiaPagamento(guiaPagamento);

			// Verifica se a guia pode ser impressa
			Collection colecaoBoletos = fachada.pesquisarBoletoEmissaoGuiaPagamento(guiaPagamento.getId());

			if(!Util.isVazioOrNulo(colecaoBoletos)){
				/*
				 * Caso já tenha sido gerado o boleto para a guia (existe ocorrência na tabela
				 * BOLETO_BANCARIO com GPAG_ID=Id da Guia selecionada e BBCO_NNPRESTACAO=Número da
				 * prestação da Guia selecionada) e o boleto esteja ativo (BBST_IDATUAL com o valor
				 * correspondente a “EM CARTEIRA” ou “EM CARTORIO” na tabela BOLETO_BANCARIO)
				 * Caso contrário, caso a prestação da guia selecionada para ser impressa não
				 * corresponda a primeira prestação da guia (GPPR_NNPRESTACAO com o valor diferente
				 * de 1 (um))
				 */
				for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacao : guiasPagamentoPrestacao){

					if(guiaPagamentoPrestacao.getNumeroPrestacao() == 1){
						idSelecionados.add(guiaPagamentoPrestacao.getId().toString());
						ids = (String[]) idSelecionados.toArray(new String[idSelecionados.size()]);
						break;
					}

				}

			}else{
				for(GuiaPagamentoPrestacaoHelper guiaPrestacaoHelper : guiasPagamentoPrestacao){
					idSelecionados.add(guiaPrestacaoHelper.getId().toString());
				}

				ids = (String[]) idSelecionados.toArray(new String[idSelecionados.size()]);
			}

		}else{
			// Forma antiga de imprimir a guia

			// tela de Consultar Parcelamento
			String idParcelamento = (String) sessao.getAttribute("idParcelamento");
			if(idParcelamento == null){
				idParcelamento = new String(httpServletRequest.getParameter("idParcelamento"));
			}

			FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, idParcelamento));

			Collection collectionGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());
			GuiaPagamento guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(collectionGuiaPagamento);

			// escolheu imprimir a guia após inclusão, neste caso, irá se 'criar' a seleção como
			// sendo todas as parcelas
			List idSelecionados = new ArrayList();
			guiasPagamentoPrestacao = this.montarHelperPrestacoesImpressaoGuiaPagamento(guiaPagamento);
			for(GuiaPagamentoPrestacaoHelper guiaPrestacaoHelper : guiasPagamentoPrestacao){
				idSelecionados.add(guiaPrestacaoHelper.getId().toString());
			}

			ids = (String[]) idSelecionados.toArray(new String[idSelecionados.size()]);
		}

		// Parte que vai mandar o relatório para a tela
		// cria uma instância da classe do relatório
		RelatorioEmitirGuiaPagamento relatorioEmitirGuiaPagamento = new RelatorioEmitirGuiaPagamento(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		relatorioEmitirGuiaPagamento.addParametro("ids", ids);
		relatorioEmitirGuiaPagamento.addParametro("colecaoPrestacoesSelecionadas", guiasPagamentoPrestacao);

		if(sessao.getAttribute("exibirNuContratoParcOrgaoPublico") != null){
			relatorioEmitirGuiaPagamento.addParametro("exibirNuContratoParcOrgaoPublico",
							sessao.getAttribute("exibirNuContratoParcOrgaoPublico"));
		}

		String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";

		relatorioEmitirGuiaPagamento.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		try{
			retorno = processarExibicaoRelatorio(relatorioEmitirGuiaPagamento, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(RelatorioVazioException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");

		}

		return retorno;
	}

	/**
	 * [UC0379] Emitir Guia de Pagamento
	 * 
	 * @author eduardo henrique
	 * @date 31/01/2009
	 *       Método auxiliar para a montagem de Helpers que serão utilizados na impressão da Guia
	 */
	private List<GuiaPagamentoPrestacaoHelper> montarHelperPrestacoesImpressaoGuiaPagamento(GuiaPagamento guiaPagamento){

		List colecaoPrestacaoHelper = new ArrayList<GuiaPagamentoPrestacaoHelper>();
		for(int i = 1; i <= guiaPagamento.getNumeroPrestacaoTotal().intValue(); i++){
			GuiaPagamentoPrestacaoHelper beanHelper = new GuiaPagamentoPrestacaoHelper();
			beanHelper.setIdGuiaPagamento(guiaPagamento.getId());
			beanHelper.setNumeroPrestacao(new Short(String.valueOf(i)));
			beanHelper.setId(Long.valueOf((guiaPagamento.getId().toString() + i)));

			colecaoPrestacaoHelper.add(beanHelper);
		}
		return colecaoPrestacaoHelper;
	}

}
