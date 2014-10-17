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

package gcom.gui.faturamento;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.gui.faturamento.bean.ListaDadosPrestacaoGuiaHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirGuiaPagamentoAction
				extends GcomAction {

	/**
	 * @author eduardo henrique
	 * @date 04/08/2008
	 *       Alteração no [UC0187], para inclusão de prestações do débito.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		InserirGuiaPagamentoActionForm inserirGuiaPagamentoActionForm = (InserirGuiaPagamentoActionForm) actionForm;

		String idImovel = inserirGuiaPagamentoActionForm.getIdImovel().trim();
		String codigoCliente = inserirGuiaPagamentoActionForm.getCodigoCliente();

		String idOrdemServico = inserirGuiaPagamentoActionForm.getOrdemServico();
		String dataVencimento = inserirGuiaPagamentoActionForm.getDataVencimento();
		String numeroContratoParcelOrgaoPublico = null;

		GuiaPagamento guiaPagamento = new GuiaPagamento();

		Imovel imovel = new Imovel();
		if(idImovel != null && !idImovel.equals("")){
			imovel.setId(new Integer(idImovel));
		}
		guiaPagamento.setImovel(imovel);

		Cliente cliente = new Cliente();
		if(codigoCliente != null && !codigoCliente.equals("")){
			cliente.setId(new Integer(codigoCliente));

		}
		guiaPagamento.setCliente(cliente);

		OrdemServico ordemServico = new OrdemServico();

		if(idOrdemServico != null && !idOrdemServico.equals("")){

			ordemServico = Fachada.getInstancia().recuperaOSPorId(new Integer(idOrdemServico));

			// Verifica se a pesquisa retornou algum objeto para a coleção
			if(ordemServico != null){
				Fachada.getInstancia().validarExibirInserirGuiaPagamento(null, ordemServico, imovel.getId(), cliente.getId());

				RegistroAtendimento ra = ordemServico.getRegistroAtendimento();

				inserirGuiaPagamentoActionForm.setRegistroAtendimento("" + ra.getId());
				inserirGuiaPagamentoActionForm.setNomeRegistroAtendimento(ra.getSolicitacaoTipoEspecificacao().getDescricao());

				if(ordemServico.getServicoTipo().getDebitoTipo() != null){

					inserirGuiaPagamentoActionForm.setIdTipoDebito("" + ordemServico.getServicoTipo().getDebitoTipo().getId());

					inserirGuiaPagamentoActionForm.setDescricaoTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getDescricao());
				}

			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Ordem de Serviço");
			}

		}
		guiaPagamento.setOrdemServico(ordemServico);

		String idRegistroAtendimento = inserirGuiaPagamentoActionForm.getRegistroAtendimento();

		RegistroAtendimento registroAtendimento = new RegistroAtendimento();
		if(idRegistroAtendimento != null && !idRegistroAtendimento.equals("")){

			ObterDadosRegistroAtendimentoHelper obterDadosRegistroAtendimentoHelper = Fachada.getInstancia().obterDadosRegistroAtendimento(
							new Integer(idRegistroAtendimento));

			if(obterDadosRegistroAtendimentoHelper.getRegistroAtendimento() != null){

				registroAtendimento = obterDadosRegistroAtendimentoHelper.getRegistroAtendimento();
				fachada.validarExibirInserirGuiaPagamento(registroAtendimento, null, imovel.getId(), cliente.getId());

			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "RA - Registro de Atendimento");
			}

		}

		guiaPagamento.setRegistroAtendimento(registroAtendimento);

		guiaPagamento.setNumeroPrestacaoTotal(Short.valueOf(inserirGuiaPagamentoActionForm.getNumeroTotalPrestacao()));

		if(inserirGuiaPagamentoActionForm.getIndicadorEmissaoObservacaoRA() != null
						&& !inserirGuiaPagamentoActionForm.getIndicadorEmissaoObservacaoRA().equals("")){
			guiaPagamento.setIndicadorEmissaoObservacaoRA(Short.valueOf(inserirGuiaPagamentoActionForm.getIndicadorEmissaoObservacaoRA()));
		}else{
			guiaPagamento.setIndicadorEmissaoObservacaoRA((short) 2);
		}

		// [FS0020] - Validar número do contrato parcelamento órgão público
		if(!Util.isVazioOuBranco(inserirGuiaPagamentoActionForm.getNumeroContratoParcelOrgaoPublico())){

			numeroContratoParcelOrgaoPublico = inserirGuiaPagamentoActionForm.getNumeroContratoParcelOrgaoPublico();

			FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();

			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.NUMERO_CONTRATO_PARCELAMENTO_ORGAO_PUBLICO,
							numeroContratoParcelOrgaoPublico));

			Collection<GuiaPagamento> colecaoGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

			// Caso o No. Contrato Parcelamento Órgão Público informado
			// já esteja associado a alguma guia exibir a mensagem
			if(!Util.isVazioOrNulo(colecaoGuiaPagamento)){
				GuiaPagamento guiaPagamentoAux = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);

				throw new ActionServletException("atencao.faturamento.inserir_guia_pagamento_contrato_parc_orgao_publico_ja_associado",
								null, guiaPagamentoAux.getId().toString());
			}
		}

		Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacao = null;

		if(sessao.getAttribute("colecaoGuiaPrestacaoHelper") != null && !sessao.getAttribute("colecaoGuiaPrestacaoHelper").equals("")){
			colecaoGuiaPagamentoPrestacao = (Collection) sessao.getAttribute("colecaoGuiaPrestacaoHelper");
		}

		Collection<ListaDadosPrestacaoGuiaHelper> colecaoListaDadosPrestacoesGuia = null;

		if(!Util.isVazioOuBranco(sessao.getAttribute("colecaoListaDadosPrestacoesGuia"))){
			colecaoListaDadosPrestacoesGuia = (Collection<ListaDadosPrestacaoGuiaHelper>) sessao
							.getAttribute("colecaoListaDadosPrestacoesGuia");
		}

		/**
		 * alterado por pedro alexandre dia 20/11/2006
		 * Recupera o usuário logado para passar no metódo de inserir guia de pagamento
		 * para verificar se o usuário tem abrangência para inserir a guia de pagamento
		 * para o imóvel caso a guia de pagamentoseja para o imóvel.
		 */

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		Integer idGuiaPagamento = fachada.inserirGuiaPagamento(guiaPagamento, usuarioLogado, dataVencimento, new Integer(
						inserirGuiaPagamentoActionForm.getQtdeDiasVencimento()), colecaoGuiaPagamentoPrestacao,
						colecaoListaDadosPrestacoesGuia, numeroContratoParcelOrgaoPublico);

		// Seta atributos que serão usados caso o usuário deseje imprimir a Guia após inclusão
		sessao.setAttribute("idGuiaPagamento", idGuiaPagamento);
		sessao.setAttribute("guiasPagamentos", colecaoGuiaPagamentoPrestacao);

		if(idImovel != null && !idImovel.equals("")){

			montarPaginaSucesso(httpServletRequest, "Guia de Pagamento de Nr. " + idGuiaPagamento + " para o imóvel " + idImovel
							+ " inserida com sucesso.", "Inserir outra Guia de Pagamento", "exibirInserirGuiaPagamentoAction.do?menu=sim",
							"exibirManterGuiaPagamentoAction.do?idImovel=" + idImovel, "Cancelar Guia(s) de Pagamento do imóvel "
											+ idImovel, "Imprimir Guia de Pagamento", "gerarRelatorioEmitirGuiaPagamentoActionInserir.do");

		}else{

			montarPaginaSucesso(httpServletRequest, "Guia de Pagamento de Nr. " + idGuiaPagamento + " para o cliente " + codigoCliente
							+ " inserida com sucesso.", "Inserir outra Guia de Pagamento", "exibirInserirGuiaPagamentoAction.do?menu=sim",
							"exibirManterGuiaPagamentoAction.do?idCliente=" + codigoCliente, "Cancelar Guia(s) de Pagamento do cliente "
											+ codigoCliente, "Imprimir Guia de Pagamento",
							"gerarRelatorioEmitirGuiaPagamentoActionInserir.do");

		}

		return retorno;
	}

}
