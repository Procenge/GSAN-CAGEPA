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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.cobranca.contrato;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cobranca.FiltroCobrancaProdutividade;
import gcom.cobranca.FiltroCobrancaSucesso;
import gcom.cobranca.contrato.CobrancaContrato;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorProdutividade;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorSucesso;
import gcom.cobranca.contrato.ContratoTipoRemuneracao;
import gcom.cobranca.contrato.FiltroCobrancaContrato;
import gcom.cobranca.contrato.FiltroCobrancaContratoRemuneracao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarContratoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarContratoCobranca");
		AtualizarContratoCobrancaActionForm form = (AtualizarContratoCobrancaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
			sessao.removeAttribute("colecaoRemuneracaoSucesso");
		}

		// DEFINIÇÃO QUE IRÁ AUXILIAR O RETORNO DOS POPUPS
		sessao.setAttribute("UseCase", "MANTERCONTRATO");

		String reloadPage = httpServletRequest.getParameter("reloadPage");
		if(reloadPage != null){

			// Obtendo a coleção de remuneração sucesso
			if(form.getRemuneracaoTipo() != null && form.getRemuneracaoTipo().equalsIgnoreCase("" + ContratoTipoRemuneracao.TIPO_SUCESSO)){
				// Ordenar a coleção do form
				BeanComparator comparador = new BeanComparator("diasVencidos");
				Collection<CobrancaContratoRemuneracaoPorSucesso> colecaoCobrancaContratoRemuneracaoVencimento = (Collection<CobrancaContratoRemuneracaoPorSucesso>) sessao
								.getAttribute("colecaoRemuneracaoSucesso");

				sessao.removeAttribute("colecaoRemuneracaoSucesso");
				sessao.removeAttribute("colecaoRemuneracaoProdutividade");

				if(colecaoCobrancaContratoRemuneracaoVencimento != null && !colecaoCobrancaContratoRemuneracaoVencimento.isEmpty()){
					Collections.sort((List) colecaoCobrancaContratoRemuneracaoVencimento, comparador);
					sessao.setAttribute("colecaoRemuneracaoSucesso", colecaoCobrancaContratoRemuneracaoVencimento);
				}

			}else{
				// Obtendo a coleção de remuneração por produtividade
				/* BeanComparator comparador = new BeanComparator("diasVencidos"); */
				Collection<CobrancaContratoRemuneracaoPorProdutividade> collCobrancaContratoServicoValor = (Collection<CobrancaContratoRemuneracaoPorProdutividade>) sessao
								.getAttribute("colecaoRemuneracaoProdutividade");

				sessao.removeAttribute("colecaoRemuneracaoProdutividade");
				sessao.removeAttribute("colecaoRemuneracaoSucesso");

				if(collCobrancaContratoServicoValor != null && !collCobrancaContratoServicoValor.isEmpty()){
					/* Collections.sort((List) collCobrancaContratoServicoValor, comparador); */
					sessao.setAttribute("colecaoRemuneracaoProdutividade", collCobrancaContratoServicoValor);
				}
			}

		}else{

			// Empresa
			Collection colecaoEmpresa = null;
			if(sessao.getAttribute("colecaoEmpresa") == null){
				colecaoEmpresa = new ArrayList();

				FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
				filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);

				filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna empresa
				colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

				if(colecaoEmpresa == null || colecaoEmpresa.isEmpty()){
					// Nenhum registro na tabela localidade_porte foi encontrado
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Empresa");
				}else{
					sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);
				}
			}

			String idContrato = null;

			if(httpServletRequest.getParameter("idContrato") == null || httpServletRequest.getParameter("idContrato").equals("")){
				CobrancaContrato cobrancaContrato = (CobrancaContrato) sessao.getAttribute("objetoContratoCobranca");
				idContrato = cobrancaContrato.getId().toString();
			}else{
				// if (!form.getColecaoRemuneracaoVencimento().isEmpty() ) {
				// form.getColecaoRemuneracaoVencimento().clear();
				// }
				idContrato = (String) httpServletRequest.getParameter("idContrato");
				sessao.setAttribute("idContrato", idContrato);
			}

			httpServletRequest.setAttribute("idContrato", idContrato);
			sessao.setAttribute("idContrato", idContrato);

			// Filtro Cobranca Contrato
			FiltroCobrancaContrato filtroContratoCobranca = new FiltroCobrancaContrato();
			filtroContratoCobranca.adicionarParametro(new ParametroSimples(FiltroCobrancaContrato.ID, idContrato));

			// Pesquisando o contrato que foi escolhida
			Collection<CobrancaContrato> colecaoCobrancaContrato = fachada.pesquisar(filtroContratoCobranca, CobrancaContrato.class
							.getName());

			// Não foi encontrado o registro
			if(colecaoCobrancaContrato == null || colecaoCobrancaContrato.isEmpty()){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}

			httpServletRequest.setAttribute("colecaoCobrancaContrato", colecaoCobrancaContrato);
			CobrancaContrato contratoCobranca = (CobrancaContrato) colecaoCobrancaContrato.iterator().next();

			Collection<CobrancaContratoRemuneracaoPorSucesso> colecaoCobrancaSucesso = null;
			Collection<CobrancaContratoRemuneracaoPorProdutividade> colecaoCobrancaProdutividade = null;

			if(contratoCobranca != null){
				if(contratoCobranca.getContratoTipoRemuneracao().getId() == ContratoTipoRemuneracao.TIPO_SUCESSO){
					FiltroCobrancaSucesso filtroCobrancaSucesso = new FiltroCobrancaSucesso();

					filtroCobrancaSucesso.adicionarParametro(new ParametroSimples(FiltroCobrancaSucesso.COBRANCA_CONTRATO, contratoCobranca
									.getId()));

					colecaoCobrancaSucesso = fachada
									.pesquisar(filtroCobrancaSucesso, CobrancaContratoRemuneracaoPorSucesso.class.getName());

				}else if(contratoCobranca.getContratoTipoRemuneracao().getId() == ContratoTipoRemuneracao.TIPO_PRODUTIVIDADE){
					FiltroCobrancaProdutividade filtroCobrancaProdutividade = new FiltroCobrancaProdutividade();

					filtroCobrancaProdutividade.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaProdutividade.SERVICO_TIPO);

					filtroCobrancaProdutividade.adicionarParametro(new ParametroSimples(FiltroCobrancaProdutividade.COBRANCA_CONTRATO,
									contratoCobranca
									.getId()));

					colecaoCobrancaProdutividade = fachada.pesquisar(filtroCobrancaProdutividade,
									CobrancaContratoRemuneracaoPorProdutividade.class.getName());
				}

				if(colecaoCobrancaSucesso != null && !colecaoCobrancaSucesso.isEmpty()){
					sessao.removeAttribute("colecaoRemuneracaoProdutividade");
					sessao.setAttribute("colecaoRemuneracaoSucesso", colecaoCobrancaSucesso);
				}
				if(colecaoCobrancaProdutividade != null && !colecaoCobrancaProdutividade.isEmpty()){
					sessao.removeAttribute("colecaoRemuneracaoSucesso");
					sessao.setAttribute("colecaoRemuneracaoProdutividade", colecaoCobrancaProdutividade);
				}

				FiltroCobrancaContratoRemuneracao filtroRemuneracao = new FiltroCobrancaContratoRemuneracao();
				filtroRemuneracao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaContratoRemuneracao.COBRANCA_CONTRATO_REMUNERACAO_VENCIMENTOS);
				filtroRemuneracao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaContratoRemuneracao.COBRANCA_CONTRATO_SERVICO_VALORES);
				filtroRemuneracao.adicionarParametro(new ParametroSimples(FiltroCobrancaContratoRemuneracao.COBRANCA_CONTRATO_ID,
								contratoCobranca.getId().toString()));

				Collection<CobrancaContratoRemuneracaoPorProdutividade> collCobrancaContratoServicoValor = null;

			}

			// Formato para a conversão de datas
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

			// Setando valores no form para ser exibido na tela de atualizar.
			form.setNumeroContrato(contratoCobranca.getNumeroContrato().toString());
			form.setIdEmpresa(contratoCobranca.getEmpresa().getId().toString());
			form.setDtInicioContrato(formatoData.format(contratoCobranca.getDataInicial()));
			form.setRemuneracaoTipo(contratoCobranca.getContratoTipoRemuneracao().getId().toString());

			if(contratoCobranca.getDataFinal() != null){
				form.setDtFimContrato(formatoData.format(contratoCobranca.getDataFinal()));
			}
			if(contratoCobranca.getDataEncerramento() != null){
				form.setDtEncerramentoContrato(formatoData.format(contratoCobranca.getDataEncerramento()));
			}
			if(contratoCobranca.getQuantidadeMinimaDiasVencidos() != null){
				form.setQtMinimaDiasVencidos(contratoCobranca.getQuantidadeMinimaDiasVencidos().toString());
			}
			if(contratoCobranca.getQuantidadeDiasReincidencia() != null){
				form.setQtDiasReincidencia(contratoCobranca.getQuantidadeDiasReincidencia().toString());
			}
			if(contratoCobranca.getPercentualRemuneracaoReincidencia() != null){
				form.setPercRemuneracaoReincidencia(contratoCobranca.getPercentualRemuneracaoReincidencia().toString());
			}

			form.setIdContrato(contratoCobranca.getId().toString());

			sessao.setAttribute("contratoCobrancaAtualizar", contratoCobranca);
		}
		return retorno;
	}

}