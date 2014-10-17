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

package gcom.gui.atendimentopublico.ligacaoesgoto;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o processamento da p�gina de efetuar corte de liga��o de
 * �gua
 * 
 * @author Leandro Cavalcanti
 * @date 12/07/2006
 */

public class EfetuarMudancaSituacaoFaturamentoLigacaoEsgotoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);
		EfetuarMudancaSituacaoFaturamentoLigacaoEsgotoActionForm mudancaFaturamentoLigacaoAguaActionForm = (EfetuarMudancaSituacaoFaturamentoLigacaoEsgotoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		/*
		 * [UC0107] Registrar Transa��o
		 */

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_MUDANCA_SITUACAO_FATURAMENTO_LIGACAO_ESGOTO,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_MUDANCA_SITUACAO_FATURAMENTO_LIGACAO_ESGOTO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// [UC0107] -Fim- Registrar Transa��o

		String ordemServicoId = mudancaFaturamentoLigacaoAguaActionForm.getIdOrdemServico();
		// String volumeMinimoFixado =
		// mudancaFaturamentoLigacaoAguaActionForm.getVolumeMinimoFixado();
		// String novaSituacaoEsgoto =
		// mudancaFaturamentoLigacaoAguaActionForm.getNovaSitLigacaoEsgoto();
		// String tipoServico =
		// mudancaFaturamentoLigacaoAguaActionForm.getTipoServico();
		String idServicoMotivoNaoCobranca = mudancaFaturamentoLigacaoAguaActionForm.getMotivoNaoCobranca();
		String valorPercentual = mudancaFaturamentoLigacaoAguaActionForm.getPercentualCobranca();

		OrdemServico ordemServico = null;
		// Validar Ordem de Servi�o
		if(ordemServicoId != null && !ordemServicoId.equals("")){
			ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
			if(ordemServico == null){
				throw new ActionServletException("atencao.ordem_servico_inexistente", null, "ORDEM DE SERVI�O INEXISTENTE");

			}
		}

		// Imovel imovel = null;
		// if (sessao.getAttribute("imovel") != null) {
		// imovel = (Imovel) sessao.getAttribute("imovel");
		// }
		/*---------------------  In�cio Dados do Corte da Liga��o de �gua  ------------------------*/

		// Validar Data da Mudan�a recebida do encerramento da Ordem de Servi�o.
		// Date dataMudanca = null;
		// dataMudanca =
		// Util.converteStringParaDate(mudancaFaturamentoLigacaoAguaActionForm
		// .getDataMudanca());

		// regitrando operacao
		ordemServico.setOperacaoEfetuada(operacaoEfetuada);
		ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(ordemServico);

		if(ordemServico != null && mudancaFaturamentoLigacaoAguaActionForm.getIdTipoDebito() != null){

			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);

			if(idServicoMotivoNaoCobranca != null
							&& !idServicoMotivoNaoCobranca.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
			}
			ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);

			if(valorPercentual != null){
				ordemServico.setPercentualCobranca(new BigDecimal(mudancaFaturamentoLigacaoAguaActionForm.getPercentualCobranca()));
			}

			BigDecimal valorAtual = new BigDecimal(0);
			if(mudancaFaturamentoLigacaoAguaActionForm.getValorDebito() != null){
				String valorDebito = mudancaFaturamentoLigacaoAguaActionForm.getValorDebito().toString().replace(".", "");

				valorDebito = valorDebito.replace(",", ".");

				valorAtual = new BigDecimal(valorDebito);

				ordemServico.setValorAtual(valorAtual);
			}
		}

		String qtdParcelas = mudancaFaturamentoLigacaoAguaActionForm.getQuantidadeParcelas();

		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

		integracaoComercialHelper.setLigacaoEsgoto((LigacaoEsgoto) sessao.getAttribute("ligacaoEsgoto"));
		integracaoComercialHelper.setOrdemServico(ordemServico);
		integracaoComercialHelper.setQtdParcelas(qtdParcelas);
		integracaoComercialHelper.setUsuarioLogado(usuario);

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			try{

				fachada.verificarQuantidadeParcelas(integracaoComercialHelper.getUsuarioLogado(), Util.obterShort(integracaoComercialHelper
						.getQtdParcelas()));
			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarMudancaSituacaoFaturamentoLigacaoEsgotoAction.do");
				throw ex;
			}
		}

		if(mudancaFaturamentoLigacaoAguaActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

			try{

				fachada.efetuarMudancaSituacaoFaturamentoLiagacaoEsgoto(integracaoComercialHelper);
			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarMudancaSituacaoFaturamentoLigacaoEsgotoAction.do");
				throw ex;

			}

		}else{
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);
			sessao.setAttribute("integracaoComercialHelper", integracaoComercialHelper);

			if(sessao.getAttribute("semMenu") == null){
				retorno = actionMapping.findForward("encerrarOrdemServicoAction");
			}else{
				retorno = actionMapping.findForward("encerrarOrdemServicoPopupAction");
			}
			sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
		}

		/*
		 * if(ordemServico.getServicoTipo().getDebitoTipo() != null &&
		 * (ordemServico.getServicoNaoCobrancaMotivo() == null ||
		 * ordemServico.getServicoNaoCobrancaMotivo().getId() ==
		 * ConstantesSistema.NUMERO_NAO_INFORMADO)){
		 * fachada.gerarDebitoOrdemServico(ordemServico.getId(),
		 * ordemServico.getServicoTipo().getDebitoTipo().getId(),
		 * Util.calcularValorDebitoComPorcentagem(valorAtual,
		 * mudancaFaturamentoLigacaoAguaActionForm.getPercentualCobranca()) ,
		 * new
		 * Integer(mudancaFaturamentoLigacaoAguaActionForm.getQuantidadeParcelas()).intValue()); }
		 */

		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			// Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Mudan�a de Faturamento da Liga��o de Esgoto" + " efetuada com Sucesso", "",
							"exibirEfetuarMudancaSituacaoFaturamentoLigacaoEsgotoAction.do");
		}

		return retorno;
	}
}
