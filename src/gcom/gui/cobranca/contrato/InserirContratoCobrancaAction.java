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
 * 
 * GSANPCG
 * Virg�nia Melo
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

package gcom.gui.cobranca.contrato;

import gcom.cadastro.empresa.Empresa;
import gcom.cobranca.contrato.CobrancaContrato;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorProdutividade;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorSucesso;
import gcom.cobranca.contrato.ContratoTipoRemuneracao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela inclus�o de um Contrato de Cobran�a
 * 
 * @author Virg�nia Melo
 * @date 17/11/2008
 *       Alteracao para contemplar remunera�ao por Produtividade
 * @author Andre Nishimura, William Mathias.
 * @date 15/04/2010
 */
public class InserirContratoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		InserirContratoCobrancaActionForm inserirContratoCobrancaActionForm = (InserirContratoCobrancaActionForm) actionForm;

		// Obtendo a cole��o de remunera��o sucesso
		Collection<CobrancaContratoRemuneracaoPorSucesso> colecaoCobrancaContratoRemuneracaoPorSucesso = new ArrayList<CobrancaContratoRemuneracaoPorSucesso>();
		if(inserirContratoCobrancaActionForm.getRemuneracaoTipo() != null
						&& inserirContratoCobrancaActionForm.getRemuneracaoTipo().equalsIgnoreCase(
										"" + ContratoTipoRemuneracao.TIPO_SUCESSO)){
			colecaoCobrancaContratoRemuneracaoPorSucesso = (Collection) sessao.getAttribute("colecaoRemuneracaoSucesso");
		}

		// Obtendo a cole��o de remunera��o por produtividade
		Collection<CobrancaContratoRemuneracaoPorProdutividade> colecaoCobrancaContratoRemuneracaoPorProdutividade = null;
		if(inserirContratoCobrancaActionForm.getRemuneracaoTipo() != null
						&& inserirContratoCobrancaActionForm.getRemuneracaoTipo().equalsIgnoreCase(
										"" + ContratoTipoRemuneracao.TIPO_PRODUTIVIDADE)){
			colecaoCobrancaContratoRemuneracaoPorProdutividade = (Collection<CobrancaContratoRemuneracaoPorProdutividade>) sessao
							.getAttribute("colecaoRemuneracaoProdutividade");
		}

		// Valida��es b�sicas do form
		verificarPreenchimentoTipoRemuneracao(inserirContratoCobrancaActionForm, colecaoCobrancaContratoRemuneracaoPorSucesso,
						colecaoCobrancaContratoRemuneracaoPorProdutividade);

		CobrancaContrato cobrancaContrato = new CobrancaContrato();

		montarCobrancaContrato(inserirContratoCobrancaActionForm, cobrancaContrato);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		fachada.inserirCobrancaContrato(cobrancaContrato, colecaoCobrancaContratoRemuneracaoPorSucesso,
						colecaoCobrancaContratoRemuneracaoPorProdutividade, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Contrato de cobran�a de Nr. " + inserirContratoCobrancaActionForm.getNumeroContrato()
						+ " inserido com sucesso.", "Inserir outro Contrato de Cobran�a", "exibirInserirContratoCobrancaAction.do?menu=sim");

		return retorno;

	}

	/**
	 * M�todo respons�vel por realizar algumas valida��es b�sicas do formul�rio.
	 */
	private void verificarPreenchimentoTipoRemuneracao(InserirContratoCobrancaActionForm form,
					Collection<CobrancaContratoRemuneracaoPorSucesso> colecaoRemuneracaoVencimento,
					Collection<CobrancaContratoRemuneracaoPorProdutividade> collCobrancaContratoServicoValor){

		// Informe pelo menos um tipo de remunera��o
		if(form.getRemuneracaoTipo() == null || form.getRemuneracaoTipo().equals("")){
			throw new ActionServletException("atencao.informe_pelo_menos_um_tipo_remuneracao");
		}

		// Adicione uma remunera��o por sucesso ou desmarque essa op��o
		if((form.getRemuneracaoTipo() != null && form.getRemuneracaoTipo().equalsIgnoreCase("" + ContratoTipoRemuneracao.TIPO_SUCESSO))
						&& (colecaoRemuneracaoVencimento == null || colecaoRemuneracaoVencimento.isEmpty())){
			throw new ActionServletException("atencao.adicione_uma_remuneracao_sucesso_ou_desmarque");
		}

		if((form.getRemuneracaoTipo() != null && form.getRemuneracaoTipo()
						.equalsIgnoreCase("" + ContratoTipoRemuneracao.TIPO_PRODUTIVIDADE))
						&& (collCobrancaContratoServicoValor == null || collCobrancaContratoServicoValor.isEmpty())){
			throw new ActionServletException("atencao.adicione_uma_remuneracao_produtividade_ou_desmarque");
		}

	}

	/**
	 * M�todo respons�vel por montar um objeto do tipo Cobranca Contrato a partir do form.
	 * 
	 * @param form
	 * @param cobrancaContrato
	 */
	private void montarCobrancaContrato(InserirContratoCobrancaActionForm form, CobrancaContrato cobrancaContrato){

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
}