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

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
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

		// recupera as condições de pagamento à vista
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

		// recupera as condições de pagamento à vista
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

		montarPaginaSucesso(httpServletRequest, "Resolução de Diretoria " + resolucaoDiretoria.getNumeroResolucaoDiretoria()
						+ " atualizado com sucesso.", "Realizar outra Manutenção de Resolução de Diretoria",
						"exibirFiltrarResolucaoDiretoriaAction.do?menu=sim");

		return retorno;

	}
}
