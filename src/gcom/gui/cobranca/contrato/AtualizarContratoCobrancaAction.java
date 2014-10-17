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
import gcom.cobranca.contrato.CobrancaContrato;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorProdutividade;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorSucesso;
import gcom.cobranca.contrato.ContratoTipoRemuneracao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe responsável por atualizar uma Empresa.
 * 
 * @author Virgínia Melo
 * @date: 06/08/08
 *        Alteracao para contemplar remuneraçao por Produtividade
 * @author Andre Nishimura, William Mathias.
 * @date 15/04/2010
 * @author isilva
 * @date: 07/06/2010
 */
public class AtualizarContratoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarContratoCobrancaActionForm form = (AtualizarContratoCobrancaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtendo a coleção de remuneração por produtividade
		Collection<CobrancaContratoRemuneracaoPorProdutividade> colecaoCobrancaContratoRemuneracaoPorProdutividade = null;
		if((form.getRemuneracaoTipo() != null && form.getRemuneracaoTipo()
						.equalsIgnoreCase("" + ContratoTipoRemuneracao.TIPO_PRODUTIVIDADE))){
			colecaoCobrancaContratoRemuneracaoPorProdutividade = (Collection<CobrancaContratoRemuneracaoPorProdutividade>) sessao
							.getAttribute("colecaoRemuneracaoProdutividade");
		}

		// Obtendo a coleção de remuneração sucesso
		Collection<CobrancaContratoRemuneracaoPorSucesso> colecaoCobrancaContratoRemuneracaoPorSucesso = null;
		if((form.getRemuneracaoTipo() != null && form.getRemuneracaoTipo().equalsIgnoreCase("" + ContratoTipoRemuneracao.TIPO_SUCESSO))){
			colecaoCobrancaContratoRemuneracaoPorSucesso = (Collection<CobrancaContratoRemuneracaoPorSucesso>) sessao
							.getAttribute("colecaoRemuneracaoSucesso");
		}

		// Validações básicas do form
		verificarPreenchimentoTipoRemuneracao(form, colecaoCobrancaContratoRemuneracaoPorSucesso,
						colecaoCobrancaContratoRemuneracaoPorProdutividade);

		CobrancaContrato cobrancaContrato = (CobrancaContrato) sessao.getAttribute("contratoCobrancaAtualizar");
		montarCobrancaContrato(form, cobrancaContrato);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		fachada.atualizarCobrancaContrato(cobrancaContrato, colecaoCobrancaContratoRemuneracaoPorSucesso,
						colecaoCobrancaContratoRemuneracaoPorProdutividade, usuarioLogado);

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_NEGATIVADOR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();

		operacao.setId(Operacao.OPERACAO_ATUALIZAR_NEGATIVADOR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		sessao.removeAttribute("idContratoCobranca");

		sessao.removeAttribute("contratoCobrancaAtualizar");

		montarPaginaSucesso(httpServletRequest, "Contrato de Cobrança de número " + cobrancaContrato.getNumeroContrato()
						+ " atualizado com sucesso.", "Manter outro Contrato de Cobranca",
						"exibirFiltrarContratoCobrancaAction.do?menu=sim");

		return retorno;

	}

	/**
	 * Método responsável por montar um objeto do tipo Cobranca Contrato a partir do form.
	 * 
	 * @param form
	 * @param cobrancaContrato
	 */
	private void montarCobrancaContrato(AtualizarContratoCobrancaActionForm form, CobrancaContrato cobrancaContrato){


		try{
			SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

			if(form.getNumeroContrato() != null && !form.getNumeroContrato().equals("")){
				cobrancaContrato.setNumeroContrato(form.getNumeroContrato());
			}

			if(form.getIdEmpresa() != null && !form.getIdEmpresa().equals("")){
				Empresa empresa = new Empresa();
				empresa.setId(Integer.valueOf(form.getIdEmpresa()));
				cobrancaContrato.setEmpresa(empresa);
			}

			if(form.getDtInicioContrato() != null && !form.getDtInicioContrato().equals("")){
				cobrancaContrato.setDataInicial(dataFormatada.parse(form.getDtInicioContrato()));
			}

			if(form.getDtFimContrato() != null && !form.getDtFimContrato().equals("")){
				cobrancaContrato.setDataFinal(dataFormatada.parse(form.getDtFimContrato()));
			}

			if(form.getDtEncerramentoContrato() != null && !form.getDtEncerramentoContrato().equals("")){
				cobrancaContrato.setDataEncerramento(dataFormatada.parse(form.getDtEncerramentoContrato()));
			}

			if(form.getQtMinimaDiasVencidos() != null && !form.getQtMinimaDiasVencidos().equals("")){
				cobrancaContrato.setQuantidadeMinimaDiasVencidos(Integer.valueOf(form.getQtMinimaDiasVencidos()));
			}

			if(form.getQtDiasReincidencia() != null && !form.getQtDiasReincidencia().equals("")){
				cobrancaContrato.setQuantidadeDiasReincidencia(Integer.valueOf(form.getQtDiasReincidencia()));
			}

			if(form.getPercRemuneracaoReincidencia() != null && !form.getPercRemuneracaoReincidencia().equals("")){
				cobrancaContrato.setPercentualRemuneracaoReincidencia(new BigDecimal(form.getPercRemuneracaoReincidencia()));
			}

			ContratoTipoRemuneracao contratoTipoRemuneracao = new ContratoTipoRemuneracao();
			contratoTipoRemuneracao.setId(Integer.valueOf(form.getRemuneracaoTipo()));

			cobrancaContrato.setContratoTipoRemuneracao(contratoTipoRemuneracao);

			cobrancaContrato.setUltimaAlteracao(new Date());

		}catch(ParseException ex){
			throw new ActionServletException("erro.sistema");
		}	
	}

	/**
	 * Método responsável por realizar algumas validações básicas do formulário.
	 */
	private void verificarPreenchimentoTipoRemuneracao(AtualizarContratoCobrancaActionForm form,
					Collection<CobrancaContratoRemuneracaoPorSucesso> colecaoRemuneracaoVencimento,
					Collection<CobrancaContratoRemuneracaoPorProdutividade> colecaoRemuneracaoProdutividade){

		// Informe pelo menos um tipo de remuneração
		if(form.getRemuneracaoTipo() == null || form.getRemuneracaoTipo().equals("")){
			throw new ActionServletException("atencao.informe_pelo_menos_um_tipo_remuneracao");
		}

		// Adicione uma remuneração por sucesso ou desmarque essa opção
		if((form.getRemuneracaoTipo() != null && form.getRemuneracaoTipo().equalsIgnoreCase("" + ContratoTipoRemuneracao.TIPO_SUCESSO))
						&& (colecaoRemuneracaoVencimento == null || colecaoRemuneracaoVencimento.isEmpty())){
			throw new ActionServletException("atencao.adicione_uma_remuneracao_sucesso_ou_desmarque");
		}

		if((form.getRemuneracaoTipo() != null && form.getRemuneracaoTipo()
						.equalsIgnoreCase("" + ContratoTipoRemuneracao.TIPO_PRODUTIVIDADE))
						&& (colecaoRemuneracaoProdutividade == null || colecaoRemuneracaoProdutividade.isEmpty())){
			throw new ActionServletException("atencao.adicione_uma_remuneracao_produtividade_ou_desmarque");
		}

		if((colecaoRemuneracaoVencimento != null && !colecaoRemuneracaoVencimento.isEmpty())
						&& (colecaoRemuneracaoProdutividade != null && !colecaoRemuneracaoProdutividade.isEmpty())){
			throw new ActionServletException("atencao.informe_remuneracao_sucesso_ou_produtividade");
		}

	}
}