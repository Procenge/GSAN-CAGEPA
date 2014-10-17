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
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipoValorLocalidade;
import gcom.financeiro.FinanciamentoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

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
 * @date 15/03/2007
 */

public class AtualizarTipoDebitoAction
				extends GcomAction {

	/**
	 * Este caso de uso permite alterar e remover um Tipo de D�bito
	 * [UC0530] Manter Tipo de D�bito
	 * 
	 * @author R�mulo Aur�lio
	 * @date 15/03/2007
	 * @author eduardo henrique
	 * @date 08/07/2008
	 *       Inclus�o dos Atributos indicadorIncidenciaMulta, indicadorIncidenciaJurosMora
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping,

	ActionForm actionForm, HttpServletRequest httpServletRequest,

	HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarTipoDebitoActionForm form = (AtualizarTipoDebitoActionForm) actionForm;

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		String id = (String) sessao.getAttribute("idTipoDebito");

		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();

		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, id));

		Collection colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

		DebitoTipo debitoTipo = (DebitoTipo) colecaoDebitoTipo.iterator().next();

		String descricao = form.getDescricao();

		String descricaoAbreviada = form.getDescricaoAbreviada();

		String idTipoFinanciamento = form.getFinanciamentoTipo();

		String indicadorGeracaoDebitoAutomatica = form.getIndicadorGeracaoDebitoAutomatica();

		String indicadorGeracaoDebitoConta = form.getIndicadorGeracaoDebitoConta();

		String indicadorUso = form.getIndicadorUso();

		String idLancamentoItemContabil = form.getLancamentoItemContabil();

		String valorLimiteDebito = form.getValorLimiteDebito();

		String indicadorIncidenciaMulta = form.getIndicadorIncidenciaMulta();

		String indicadorIncidenciaJurosMora = form.getIndicadorIncidenciaJurosMora();

		debitoTipo.setDescricao(descricao.toUpperCase());

		if(descricaoAbreviada != null && !descricaoAbreviada.equalsIgnoreCase("")){
			debitoTipo.setDescricaoAbreviada(descricaoAbreviada.toUpperCase());
		}

		debitoTipo.setIndicadorGeracaoAutomatica(Util.obterShort(indicadorGeracaoDebitoAutomatica));

		debitoTipo.setIndicadorGeracaoConta(Util.obterShort(indicadorGeracaoDebitoConta));

		debitoTipo.setIndicadorIncidenciaMulta(Util.obterShort(indicadorIncidenciaMulta));

		debitoTipo.setIndicadorIncidenciaJurosMora(Util.obterShort(indicadorIncidenciaJurosMora));

		if(idTipoFinanciamento != null && !idTipoFinanciamento.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			FinanciamentoTipo financiamentoTipo = new FinanciamentoTipo();

			financiamentoTipo.setId(Util.obterInteger(idTipoFinanciamento));

			debitoTipo.setFinanciamentoTipo(financiamentoTipo);
		}

		if(idLancamentoItemContabil != null && !idLancamentoItemContabil.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			LancamentoItemContabil lancamentoItemContabil = new LancamentoItemContabil();

			lancamentoItemContabil.setId(Util.obterInteger(idLancamentoItemContabil));

			debitoTipo.setLancamentoItemContabil(lancamentoItemContabil);

		}

		// valor Limite Debito
		if(valorLimiteDebito != null && !valorLimiteDebito.trim().equalsIgnoreCase("")){
			debitoTipo.setValorLimite(Util.formatarMoedaRealparaBigDecimal(valorLimiteDebito, 2));
		}

		debitoTipo.setIndicadorUso(new Short(indicadorUso));

		debitoTipo.setUltimaAlteracao(new Date());

		// Altera��es pra NILA
		debitoTipo.setIndicadorValorCalcular(Util.obterShort(form.getIndicadorValorCalcular()));

		// VALOR PADR�O
		if(GenericValidator.isBlankOrNull(form.getValorPadrao())){
			debitoTipo.setValorPadrao(null);
		}else{
			debitoTipo.setValorPadrao(Util.formatarMoedaRealparaBigDecimal(form.getValorPadrao(), 2));
		}


		// OC1213341 - Inserir cole��o de Servi�os tipos e valores por Localidade
		Collection colecaoDebitoTipoValorLocalidade = form.getDebitoTipoValorLocalidade();
		if(Util.isVazioOrNulo(colecaoDebitoTipoValorLocalidade)){
			colecaoDebitoTipoValorLocalidade = (ArrayList) sessao.getAttribute("colecaoServicoTipoValorLocalidade");
		}

		// Recuperando cole��o de servi�o tipo valor localidade
		FiltroDebitoTipoValorLocalidade filtroDebitoTipoValorLocalidade = new FiltroDebitoTipoValorLocalidade();
		filtroDebitoTipoValorLocalidade.adicionarParametro(new ParametroSimples(FiltroDebitoTipoValorLocalidade.DEBITO_TIPO_ID, debitoTipo
						.getId()));

		Collection colecaoDebitoTipoValorLocalidadeNaBase = fachada.pesquisar(filtroDebitoTipoValorLocalidade,
						DebitoTipoValorLocalidade.class.getName());

		if(!Util.isVazioOrNulo(colecaoDebitoTipoValorLocalidade)){

			checarDebitoTipoValorLocalidade(colecaoDebitoTipoValorLocalidade, debitoTipo);

			// atualiza na base de dados Tipo Servi�o
			fachada.atualizarDebitoTipo(debitoTipo, usuarioLogado);

			colecaoDebitoTipoValorLocalidade = (Collection) inserirDebitoTipoValorLocalidade(colecaoDebitoTipoValorLocalidade, debitoTipo,
							debitoTipo.getId());

			fachada.removerColecaoDebitoTipoValorLocalidade(colecaoDebitoTipoValorLocalidadeNaBase);
			fachada.inserirColecaoDebitoTipoValorLocalidade(colecaoDebitoTipoValorLocalidade);
		}else{
			// atualiza na base de dados Tipo Servi�o
			fachada.atualizarDebitoTipo(debitoTipo, usuarioLogado);

			if(!Util.isVazioOrNulo(colecaoDebitoTipoValorLocalidadeNaBase)){
				fachada.removerColecaoDebitoTipoValorLocalidade(colecaoDebitoTipoValorLocalidadeNaBase);
			}
		}

		montarPaginaSucesso(httpServletRequest, "Tipo de D�bito de c�digo " + id + " atualizado com sucesso.",
						"Realizar outra Manuten��o Tipo de D�bito", "exibirFiltrarTipoDebitoAction.do?menu=sim");

		return retorno;

	}

	public void checarDebitoTipoValorLocalidade(Collection colecao, DebitoTipo debitoTipo){

		if(!Util.isVazioOrNulo(colecao)){
			for(Iterator iter = colecao.iterator(); iter.hasNext();){
				DebitoTipoValorLocalidade dtvl = (DebitoTipoValorLocalidade) iter.next();

				BigDecimal valorNaColecao = BigDecimal.ZERO;
				if(dtvl.getValorDebitoTipo() != null){
					valorNaColecao = dtvl.getValorDebitoTipo();
				}

				BigDecimal valorNoForm = BigDecimal.ZERO;
				if(debitoTipo.getValorPadrao() != null){
					valorNoForm = debitoTipo.getValorPadrao();
				}

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
