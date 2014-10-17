/**
 * 
 */
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

package gcom.gui.faturamento.debito;

import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.DebitoTipoValorLocalidade;
import gcom.financeiro.FinanciamentoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 09/03/2007
 */
public class InserirTipoDebitoAction
				extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de um novo Tipo de D�bito
	 * [UC0529] Inserir Tipo de D�bito
	 * 
	 * @author R�mulo Aur�lio
	 * @date 09/03/2007
	 * @author eduardo henrique
	 * @date 08/07/2008
	 *       Inclus�o dos Atributos indicadorIncidenciaMulta, indicadorIncidenciaJurosMora
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		InserirTipoDebitoActionForm form = (InserirTipoDebitoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		DebitoTipo debitoTipo = new DebitoTipo();

		debitoTipo.setDescricao(form.getDescricao());

		if(form.getDescricaoAbreviada() != null && !form.getDescricaoAbreviada().equalsIgnoreCase("")){
			debitoTipo.setDescricaoAbreviada(form.getDescricaoAbreviada());
		}
		debitoTipo.setIndicadorGeracaoAutomatica(Util.obterShort(form.getIndicadorGeracaoDebitoAutomatica()));

		debitoTipo.setIndicadorGeracaoConta(Util.obterShort(form.getIndicadorGeracaoDebitoConta()));

		debitoTipo.setIndicadorIncidenciaMulta(Util.obterShort(form.getIndicadorIncidenciaMulta()));

		debitoTipo.setIndicadorIncidenciaJurosMora(Util.obterShort(form.getIndicadorIncidenciaJurosMora()));

		if(form.getFinanciamentoTipo() != null && !form.getFinanciamentoTipo().trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			FinanciamentoTipo financiamentoTipo = new FinanciamentoTipo();

			financiamentoTipo.setId(Util.obterInteger(form.getFinanciamentoTipo()));

			debitoTipo.setFinanciamentoTipo(financiamentoTipo);

		}

		if(form.getLancamentoItemContabil() != null
						&& !form.getLancamentoItemContabil().trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			LancamentoItemContabil lancamentoItemContabil = new LancamentoItemContabil();

			lancamentoItemContabil.setId(Util.obterInteger(form.getLancamentoItemContabil()));

			debitoTipo.setLancamentoItemContabil(lancamentoItemContabil);

		}

		// valor Limite Debito
		if(form.getValorLimiteDebito() != null && !form.getValorLimiteDebito().trim().equalsIgnoreCase("")){
			debitoTipo.setValorLimite(Util.formatarMoedaRealparaBigDecimal(form.getValorLimiteDebito(), 2));
		}

		debitoTipo.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		debitoTipo.setUltimaAlteracao(new Date());

		// Altera��es pra NILA
		debitoTipo.setIndicadorValorCalcular(Util.obterShort(form.getIndicadorValorCalcular()));

		// VALOR PADR�O
		if(!GenericValidator.isBlankOrNull(form.getValorPadrao())){
			debitoTipo.setValorPadrao(Util.formatarMoedaRealparaBigDecimal(form.getValorPadrao(), 2));
		}

		Collection colecaoDebitoTipoValorLocalidade = form.getDebitoTipoValorLocalidade();
		Integer idDebitoTipo = null;
		if(!Util.isVazioOrNulo(colecaoDebitoTipoValorLocalidade)){

			checarDebitoTipoValorLocalidade(colecaoDebitoTipoValorLocalidade, debitoTipo);

			// Insere na base de dados
			idDebitoTipo = (Integer) fachada.inserirDebitoTipo(debitoTipo, usuarioLogado);

			colecaoDebitoTipoValorLocalidade = (Collection) inserirDebitoTipoValorLocalidade(colecaoDebitoTipoValorLocalidade, debitoTipo,
							idDebitoTipo);

			fachada.inserirColecaoServicoTipoValorLocalidade(colecaoDebitoTipoValorLocalidade);
		}else{
			// Insere na base de dados
			idDebitoTipo = (Integer) fachada.inserirDebitoTipo(debitoTipo, usuarioLogado);
		}


		montarPaginaSucesso(httpServletRequest, "Tipo de D�bito de c�digo " + idDebitoTipo + " inserido com sucesso.",
						"Inserir outro Tipo de D�bito", "exibirInserirTipoDebitoAction.do?menu=sim"
		/*
		 * ,"exibirAtualizarFuncionalidadeAction.do?idFuncionalidade=" +
		 * idFuncionalidade , "Atualizar Funcionalidade inserida"
		 */);

		return retorno;
	}

	public void checarDebitoTipoValorLocalidade(Collection colecao, DebitoTipo debitoTipo){

		if(!Util.isVazioOrNulo(colecao)){
			for(Iterator iter = colecao.iterator(); iter.hasNext();){
				DebitoTipoValorLocalidade dtvl = (DebitoTipoValorLocalidade) iter.next();
				BigDecimal valorNaColecao = dtvl.getValorDebitoTipo();
				BigDecimal valorNoForm = debitoTipo.getValorPadrao();
				if(valorNaColecao.compareTo(valorNoForm) == 0){
					throw new ActionServletException("atencao.valor_diferente_padrao");
				}

			}
		}
	}

	public Collection inserirDebitoTipoValorLocalidade(Collection colecao, DebitoTipo debitoTipo, Integer idDebitoTipo){

		Collection colecaoRetorno = null;

		if(!Util.isVazioOrNulo(colecao)){

			colecaoRetorno = new ArrayList();

			for(Iterator iter = colecao.iterator(); iter.hasNext();){
				DebitoTipoValorLocalidade dtvl = (DebitoTipoValorLocalidade) iter.next();
				debitoTipo.setId(idDebitoTipo);
				dtvl.setDebitoTipo(debitoTipo);
				colecaoRetorno.add(dtvl);
			}
		}
		return colecaoRetorno;
	}

}
