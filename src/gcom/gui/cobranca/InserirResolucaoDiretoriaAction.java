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

/**
 * Action utilizado para inserir uma resolução de diretoria no banco
 * [UC0217] Inserir Resolução de Diretoria Permite inserir uma
 * ResolucaoDiretoria
 * 
 * @author Rafael Corrêa
 * @since 30/03/2006
 */
public class InserirResolucaoDiretoriaAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		InserirResolucaoDiretoriaActionForm inserirResolucaoDiretoriaActionForm = (InserirResolucaoDiretoriaActionForm) actionForm;

		// Cria uma resolução de diretoria setando os valores informados pelo
		// usuário na pagina para ser inserido no banco
		ResolucaoDiretoria resolucaoDiretoria = new ResolucaoDiretoria();

		resolucaoDiretoria.setNumeroResolucaoDiretoria(inserirResolucaoDiretoriaActionForm.getNumero());
		resolucaoDiretoria.setDescricaoAssunto(inserirResolucaoDiretoriaActionForm.getAssunto());
		resolucaoDiretoria.setDataVigenciaInicio(Util.converteStringParaDate(inserirResolucaoDiretoriaActionForm.getDataInicio()));
		resolucaoDiretoria.setIndicadorParcelamentoUnico(Short.valueOf(inserirResolucaoDiretoriaActionForm.getIndicadorUsoRDParcImovel()));
		resolucaoDiretoria.setIndicadorUtilizacaoLivre(Short.valueOf(inserirResolucaoDiretoriaActionForm.getIndicadorUsoRDUsuarios()));
		resolucaoDiretoria.setIndicadorDescontoSancoes(Short.valueOf(inserirResolucaoDiretoriaActionForm.getIndicadorUsoRDDebitoCobrar()));
		resolucaoDiretoria.setIndicadorEmissaoAssuntoConta(Short.valueOf(inserirResolucaoDiretoriaActionForm
						.getIndicadorEmissaoAssuntoConta()));
		resolucaoDiretoria.setIndicadorTrataMediaAtualizacaoMonetaria(Short.valueOf(inserirResolucaoDiretoriaActionForm
						.getIndicadorTrataMediaAtualizacaoMonetaria()));
		resolucaoDiretoria.setIndicadorCobrarDescontosArrasto(Short.valueOf(inserirResolucaoDiretoriaActionForm
						.getIndicadorCobrarDescontosArrasto()));
		resolucaoDiretoria.setIndicadorArrasto(Short.valueOf(inserirResolucaoDiretoriaActionForm.getIndicadorArrasto()));

		// seta ResolucaoDiretoriaLayout (Termo de Confissão de Divida)
		if(inserirResolucaoDiretoriaActionForm.getIdsResolucaoDiretoriaLayout() != null
						&& inserirResolucaoDiretoriaActionForm.getIdsResolucaoDiretoriaLayout().length > 0){
			if(!inserirResolucaoDiretoriaActionForm.getIdsResolucaoDiretoriaLayout()[0].equals("-1")){

				ResolucaoDiretoriaLayout resolucaoDiretoriaLayout = new ResolucaoDiretoriaLayout();
				resolucaoDiretoriaLayout.setId(Integer.valueOf(inserirResolucaoDiretoriaActionForm.getIdsResolucaoDiretoriaLayout()[0]));
				resolucaoDiretoria.setResolucaoDiretoriaLayout(resolucaoDiretoriaLayout);
			}
		}

		// recupera sessão
		HttpSession sessao = httpServletRequest.getSession(false);
		// recupera grupos habilitados
		Collection colecaoGruposAcessoHabilitados = (Collection) sessao.getAttribute("colecaoGruposAcessoHabilitados");
		if(colecaoGruposAcessoHabilitados != null && !colecaoGruposAcessoHabilitados.isEmpty()){

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

			Set<ResolucaoDiretoriaParametrosPagamentoAVista> condicoesPagtoAVistaSet = new HashSet<ResolucaoDiretoriaParametrosPagamentoAVista>();

			for(Object condicaoPagtoAVistaTemp : colecaoCondicaoPagamentoAVistaSelecionados){
				ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagtoAVista = (ResolucaoDiretoriaParametrosPagamentoAVista) condicaoPagtoAVistaTemp;
				condicaoPagtoAVista.setResolucaoDiretoria(resolucaoDiretoria);
				condicaoPagtoAVista.setUltimaAlteracao(new Date());
				condicoesPagtoAVistaSet.add(condicaoPagtoAVista);
			}
			resolucaoDiretoria.setResolucaoDiretoriaCondicoesPagtoAVista(condicoesPagtoAVistaSet);
		}

		// recupera as condições de valor de entrada
		Collection colecaoCondicaoValorEntradaSelecionados = (Collection) sessao.getAttribute("colecaoCondicaoValorEntradaSelecionados");
		if(!Util.isVazioOrNulo(colecaoCondicaoValorEntradaSelecionados)){

			Set<ResolucaoDiretoriaParametrosValorEntrada> condicoesValorEntradaSet = new HashSet<ResolucaoDiretoriaParametrosValorEntrada>();

			for(Object condicaoValorEntradaTemp : colecaoCondicaoValorEntradaSelecionados){
				ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada = (ResolucaoDiretoriaParametrosValorEntrada) condicaoValorEntradaTemp;
				condicaoValorEntrada.setResolucaoDiretoria(resolucaoDiretoria);
				condicaoValorEntrada.setUltimaAlteracao(new Date());
				condicoesValorEntradaSet.add(condicaoValorEntrada);
			}
			resolucaoDiretoria.setResolucaoDiretoriaCondicoesValorEntrada(condicoesValorEntradaSet);
		}

		// verifica se a data final foi digitada em caso afirmativo seta-a no
		// objeto
		if(inserirResolucaoDiretoriaActionForm.getDataFim() != null && !inserirResolucaoDiretoriaActionForm.getDataFim().equals("")){
			resolucaoDiretoria.setDataVigenciaFim(Util.converteStringParaDate(inserirResolucaoDiretoriaActionForm.getDataFim()));
		}

		// seta gurpos de acesso selecionados

		// Insere uma Resolução de Diretoria na base, mas fazendo, antes,
		// algumas verificações no ControladorCobrancaSEJB.
		Integer id = (Integer) fachada.inserirResolucaoDiretoria(resolucaoDiretoria, this.getUsuarioLogado(httpServletRequest));

		// Remove da sessao as colecoes de grupos e restricoes de debito
		sessao.removeAttribute("colecaoGruposAcessoHabilitados");
		sessao.removeAttribute("colecaoRestricaoDebitoSelecionados");

		// Monta a página de sucesso de acordo com o padrão do sistema.
		montarPaginaSucesso(httpServletRequest, "Resolução de Diretoria " + resolucaoDiretoria.getNumeroResolucaoDiretoria()
						+ " inserida com sucesso.", "Inserir outra Resolução de Diretoria",
						"exibirInserirResolucaoDiretoriaAction.do?menu=sim",
						"exibirAtualizarResolucaoDiretoriaAction.do?inserir=sim&resolucaoDiretoriaID=" + id,
						"Atualizar Resolução de Diretoria inserida");

		return retorno;

	}
}