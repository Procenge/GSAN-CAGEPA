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

package gcom.gui.cobranca;

import gcom.cobranca.FiltroResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoriaGrupo;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoriaParametrosPagamentoAVista;
import gcom.cobranca.ResolucaoDiretoriaParametrosValorEntrada;
import gcom.cobranca.parcelamento.ParcelamentoSituacaoEspecial;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Grupo;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarResolucaoDiretoriaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarResolucaoDiretoriaActionForm atualizarResolucaoDiretoriaActionForm = (AtualizarResolucaoDiretoriaActionForm) actionForm;

		ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) sessao.getAttribute("resolucaoDiretoriaAtualizar");

		resolucaoDiretoria.setNumeroResolucaoDiretoria(atualizarResolucaoDiretoriaActionForm.getNumero());
		resolucaoDiretoria.setDescricaoAssunto(atualizarResolucaoDiretoriaActionForm.getAssunto());
		resolucaoDiretoria.setDataVigenciaInicio(Util.converteStringParaDate(atualizarResolucaoDiretoriaActionForm.getDataInicio()));

		resolucaoDiretoria
						.setIndicadorParcelamentoUnico(Short.valueOf(atualizarResolucaoDiretoriaActionForm.getIndicadorUsoRDParcImovel()));
		resolucaoDiretoria.setIndicadorUtilizacaoLivre(Short.valueOf(atualizarResolucaoDiretoriaActionForm.getIndicadorUsoRDUsuarios()));
		resolucaoDiretoria
						.setIndicadorDescontoSancoes(Short.valueOf(atualizarResolucaoDiretoriaActionForm.getIndicadorUsoRDDebitoCobrar()));
		resolucaoDiretoria.setIndicadorEmissaoAssuntoConta(Short.valueOf(atualizarResolucaoDiretoriaActionForm
						.getIndicadorEmissaoAssuntoConta()));
		resolucaoDiretoria.setIndicadorTrataMediaAtualizacaoMonetaria(Short.valueOf(atualizarResolucaoDiretoriaActionForm
						.getIndicadorTrataMediaAtualizacaoMonetaria()));
		resolucaoDiretoria.setIndicadorCobrarDescontosArrasto(Short.valueOf(atualizarResolucaoDiretoriaActionForm
						.getIndicadorCobrarDescontosArrasto()));
		resolucaoDiretoria.setIndicadorArrasto(Short.valueOf(atualizarResolucaoDiretoriaActionForm.getIndicadorArrasto()));

		Collection<Integer> colecaoIds = new ArrayList<Integer>();
		for(int i = 0; i < atualizarResolucaoDiretoriaActionForm.getIdsResolucaoDiretoriaLayout().length; i++){
			colecaoIds.add(Integer.valueOf(atualizarResolucaoDiretoriaActionForm.getIdsResolucaoDiretoriaLayout()[i]));
		}

		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();
		Collection<ResolucaoDiretoriaLayout> colecaoResolucaoDiretoriaLayout = fachada.pesquisar(colecaoIds,
						filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName());

		ResolucaoDiretoriaLayout resolucaoDiretoriaLayout = (ResolucaoDiretoriaLayout) Util
						.retonarObjetoDeColecao(colecaoResolucaoDiretoriaLayout);

		resolucaoDiretoria.setResolucaoDiretoriaLayout(resolucaoDiretoriaLayout);
		if(atualizarResolucaoDiretoriaActionForm.getDataFim() != null && !atualizarResolucaoDiretoriaActionForm.getDataFim().equals("")){
			resolucaoDiretoria.setDataVigenciaFim(Util.converteStringParaDate(atualizarResolucaoDiretoriaActionForm.getDataFim()));
		}else{
			resolucaoDiretoria.setDataVigenciaFim(null);
		}

		// recupera grupos habilitados
		Collection colecaoGruposAcessoHabilitados = (Collection) sessao.getAttribute("colecaoGruposAcessoHabilitados");
		if(!Util.isVazioOrNulo(colecaoGruposAcessoHabilitados)){

			Set<ResolucaoDiretoriaGrupo> gruposHabilitadosSet = new HashSet<ResolucaoDiretoriaGrupo>();

			for(Object grupoHabilitadoTemp : colecaoGruposAcessoHabilitados){
				Grupo grupoHabilitado = (Grupo) grupoHabilitadoTemp;
				ResolucaoDiretoriaGrupo resolucaoDiretoriaGrupo = new ResolucaoDiretoriaGrupo();
				resolucaoDiretoriaGrupo.setGrupo(grupoHabilitado);
				resolucaoDiretoriaGrupo.setResolucaoDiretoria(resolucaoDiretoria);
				resolucaoDiretoriaGrupo.setUltimaAlteracao(new Date());
				resolucaoDiretoriaGrupo.setIndicadorUso(new Short("1"));
				gruposHabilitadosSet.add(resolucaoDiretoriaGrupo);
			}
			resolucaoDiretoria.setResolucaoDiretoriaGrupos(gruposHabilitadosSet);
		}else{
			resolucaoDiretoria.setResolucaoDiretoriaGrupos(null);
		}

		// recupera as restricoes de debito
		Collection colecaoRestricaoDebitoSelecionados = (Collection) sessao.getAttribute("colecaoRestricaoDebitoSelecionados");
		if(!Util.isVazioOrNulo(colecaoRestricaoDebitoSelecionados)){

			Set<ParcelamentoSituacaoEspecial> parcelamentoSituacaoEspecialSet = new HashSet<ParcelamentoSituacaoEspecial>();

			for(Object parcelamentoSituacaoEspecialTemp : colecaoRestricaoDebitoSelecionados){
				ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = (ParcelamentoSituacaoEspecial) parcelamentoSituacaoEspecialTemp;
				parcelamentoSituacaoEspecial.setResolucaoDiretoria(resolucaoDiretoria);
				parcelamentoSituacaoEspecial.setUltimaAlteracao(new Date());
				parcelamentoSituacaoEspecialSet.add(parcelamentoSituacaoEspecial);
			}
			resolucaoDiretoria.setParcelamentosSituacaoEspecial(parcelamentoSituacaoEspecialSet);
		}

		// recupera as condi��es de pagamento � vista
		Collection colecaoCondicaoPagamentoAVistaSelecionados = (Collection) sessao
						.getAttribute("colecaoCondicaoPagamentoAVistaSelecionados");
		if(!Util.isVazioOrNulo(colecaoCondicaoPagamentoAVistaSelecionados)){

			Set<ResolucaoDiretoriaParametrosPagamentoAVista> condicaoPagtoAVistaSet = new HashSet<ResolucaoDiretoriaParametrosPagamentoAVista>();

			for(Object condicaoPagtoAVistaTemp : colecaoCondicaoPagamentoAVistaSelecionados){
				ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagtoAVista = (ResolucaoDiretoriaParametrosPagamentoAVista) condicaoPagtoAVistaTemp;
				condicaoPagtoAVista.setResolucaoDiretoria(resolucaoDiretoria);
				condicaoPagtoAVista.setUltimaAlteracao(new Date());
				condicaoPagtoAVistaSet.add(condicaoPagtoAVista);
			}
			resolucaoDiretoria.setResolucaoDiretoriaCondicoesPagtoAVista(condicaoPagtoAVistaSet);
		}

		// recupera as condi��es de pagamento � vista
		Collection colecaoCondicaoValorEntradaSelecionados = (Collection) sessao.getAttribute("colecaoCondicaoValorEntradaSelecionados");
		if(!Util.isVazioOrNulo(colecaoCondicaoValorEntradaSelecionados)){

			Set<ResolucaoDiretoriaParametrosValorEntrada> condicaoValorEntradaSet = new HashSet<ResolucaoDiretoriaParametrosValorEntrada>();

			for(Object condicaoValorEntradaTemp : colecaoCondicaoValorEntradaSelecionados){
				ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada = (ResolucaoDiretoriaParametrosValorEntrada) condicaoValorEntradaTemp;
				condicaoValorEntrada.setResolucaoDiretoria(resolucaoDiretoria);
				condicaoValorEntrada.setUltimaAlteracao(new Date());
				condicaoValorEntradaSet.add(condicaoValorEntrada);
			}
			resolucaoDiretoria.setResolucaoDiretoriaCondicoesValorEntrada(condicaoValorEntradaSet);
		}

		fachada.atualizarResolucaoDiretoria(resolucaoDiretoria, this.getUsuarioLogado(httpServletRequest));

		montarPaginaSucesso(httpServletRequest, "Resolu��o de Diretoria " + resolucaoDiretoria.getNumeroResolucaoDiretoria()
						+ " atualizado com sucesso.", "Realizar outra Manuten��o de Resolu��o de Diretoria",
						"exibirFiltrarResolucaoDiretoriaAction.do?menu=sim");

		return retorno;

	}
}
