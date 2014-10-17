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

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.FiltroResolucaoDiretoriaGrupo;
import gcom.cobranca.FiltroResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoriaGrupo;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoriaParametrosPagamentoAVista;
import gcom.cobranca.ResolucaoDiretoriaParametrosValorEntrada;
import gcom.cobranca.parcelamento.FiltroParcelamento;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoSituacaoEspecial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroGrupo;
import gcom.seguranca.acesso.Grupo;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarResolucaoDiretoriaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirAtualizarResolucaoDiretoria");

		AtualizarResolucaoDiretoriaActionForm atualizarResolucaoDiretoriaActionForm = (AtualizarResolucaoDiretoriaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Variável que indica quando o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		httpServletRequest.removeAttribute("idLocalidadeEncontrado");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("inserir") != null && !httpServletRequest.getParameter("inserir").equals("")){
			String inserir = httpServletRequest.getParameter("inserir");
			httpServletRequest.setAttribute("inserir", inserir);
		}

		if(sessao.getAttribute("resolucaoDiretoria") != null){

			ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) sessao.getAttribute("resolucaoDiretoria");

			atualizarResolucaoDiretoriaActionForm.setNumero(resolucaoDiretoria.getNumeroResolucaoDiretoria());
			atualizarResolucaoDiretoriaActionForm.setAssunto(resolucaoDiretoria.getDescricaoAssunto());
			atualizarResolucaoDiretoriaActionForm.setDataInicio(Util.formatarData(resolucaoDiretoria.getDataVigenciaInicio()));
			atualizarResolucaoDiretoriaActionForm.setDataFim(Util.formatarData(resolucaoDiretoria.getDataVigenciaFim()));
			atualizarResolucaoDiretoriaActionForm
							.setIndicadorUsoRDParcImovel(resolucaoDiretoria.getIndicadorParcelamentoUnico().toString());
			atualizarResolucaoDiretoriaActionForm.setIndicadorUsoRDUsuarios(resolucaoDiretoria.getIndicadorUtilizacaoLivre().toString());
			atualizarResolucaoDiretoriaActionForm
							.setIndicadorUsoRDDebitoCobrar(resolucaoDiretoria.getIndicadorDescontoSancoes().toString());
			atualizarResolucaoDiretoriaActionForm.setIndicadorEmissaoAssuntoConta(resolucaoDiretoria.getIndicadorEmissaoAssuntoConta()
							.toString());
			atualizarResolucaoDiretoriaActionForm.setIndicadorTrataMediaAtualizacaoMonetaria(resolucaoDiretoria
							.getIndicadorTrataMediaAtualizacaoMonetaria().toString());
			atualizarResolucaoDiretoriaActionForm.setIndicadorCobrarDescontosArrasto(resolucaoDiretoria
							.getIndicadorCobrarDescontosArrasto().toString());
			atualizarResolucaoDiretoriaActionForm.setIndicadorArrasto(resolucaoDiretoria.getIndicadorArrasto().toString());

			// Limpa os dados vindos da tela do filtrar
			atualizarResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoInicio(null);
			atualizarResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoFim(null);

			if(resolucaoDiretoria.getResolucaoDiretoriaLayout() != null){
				String[] arrayString = new String[1];
				arrayString[0] = String.valueOf(resolucaoDiretoria.getResolucaoDiretoriaLayout().getId());
				atualizarResolucaoDiretoriaActionForm.setIdsResolucaoDiretoriaLayout(arrayString);
			}

			// Carrega grupos habilitados recuperados
			if(!Util.isVazioOrNulo(resolucaoDiretoria.getResolucaoDiretoriaGrupos())){
				Collection<ResolucaoDiretoriaGrupo> resolucaoDiretoriaGrupo = resolucaoDiretoria.getResolucaoDiretoriaGrupos();
				Collection objColecaoGruposHabilitados = new ArrayList();

				for(Object objResolucaoDiretoriaGrupo : resolucaoDiretoriaGrupo){
					ResolucaoDiretoriaGrupo resolucaoDiretoriaGrupoTemp = (ResolucaoDiretoriaGrupo) objResolucaoDiretoriaGrupo;
					Grupo grupoHabilitado = resolucaoDiretoriaGrupoTemp.getGrupo();
					objColecaoGruposHabilitados.add(grupoHabilitado);
				}
				// adiciona coleção à sessão
				sessao.setAttribute("colecaoGruposAcessoHabilitados", objColecaoGruposHabilitados);
				sessao.setAttribute("colecaoGruposAcessoOriginais", objColecaoGruposHabilitados);
			}

			if(!Util.isVazioOrNulo(resolucaoDiretoria.getParcelamentosSituacaoEspecial())){
				Collection<ParcelamentoSituacaoEspecial> colecaoParcelamentoSituacaoEspecial = resolucaoDiretoria
								.getParcelamentosSituacaoEspecial();
				Collection colecaoRestricaoDebitoSelecionados = new ArrayList();

				for(Object parcelamentoSituacaoEspecialTemp : colecaoParcelamentoSituacaoEspecial){
					ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = (ParcelamentoSituacaoEspecial) parcelamentoSituacaoEspecialTemp;
					colecaoRestricaoDebitoSelecionados.add(parcelamentoSituacaoEspecial);
				}
				// adiciona coleção à sessão
				sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionados);
				sessao.setAttribute("colecaoRestricaoDebitoOriginais", colecaoRestricaoDebitoSelecionados);

			}

			if(!Util.isVazioOrNulo(resolucaoDiretoria.getResolucaoDiretoriaCondicoesPagtoAVista())){
				Collection<ResolucaoDiretoriaParametrosPagamentoAVista> colecaoCondicaoPagamentoAVista = resolucaoDiretoria
								.getResolucaoDiretoriaCondicoesPagtoAVista();
				Collection colecaoCondicaoPagamentoAVistaSelecionados = new ArrayList();

				for(Object condicaoPagtoAVistaTemp : colecaoCondicaoPagamentoAVista){
					ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagtoAVista = (ResolucaoDiretoriaParametrosPagamentoAVista) condicaoPagtoAVistaTemp;
					colecaoCondicaoPagamentoAVistaSelecionados.add(condicaoPagtoAVista);
				}
				// adiciona coleção à sessão
				sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionados);
				sessao.setAttribute("colecaoCondicaoPagamentoAVistaOriginais", colecaoCondicaoPagamentoAVistaSelecionados);

			}

			if(!Util.isVazioOrNulo(resolucaoDiretoria.getResolucaoDiretoriaCondicoesValorEntrada())){
				Collection<ResolucaoDiretoriaParametrosValorEntrada> colecaoCondicaoValorEntrada = resolucaoDiretoria
								.getResolucaoDiretoriaCondicoesValorEntrada();
				Collection colecaoCondicaoValorEntradaSelecionados = new ArrayList();

				for(Object condicaoValorEntradaTemp : colecaoCondicaoValorEntrada){
					ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada = (ResolucaoDiretoriaParametrosValorEntrada) condicaoValorEntradaTemp;
					colecaoCondicaoValorEntradaSelecionados.add(condicaoValorEntrada);
				}
				// adiciona coleção à sessão
				sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionados);
				sessao.setAttribute("colecaoCondicaoValorEntradaOriginais", colecaoCondicaoValorEntradaSelecionados);

			}

			sessao.setAttribute("resolucaoDiretoriaAtualizar", resolucaoDiretoria);
			sessao.removeAttribute("resolucaoDiretoria");

		}else{

			if(httpServletRequest.getParameter("resolucaoDiretoriaID") != null){

				String idResolucaoDiretoria = httpServletRequest.getParameter("resolucaoDiretoriaID");

				FiltroResolucaoDiretoria filtroResolucaoDiretoria = new FiltroResolucaoDiretoria();

				Collection colecaoResolucaoDiretoria = new ArrayList();

				// desfazer as alterações
				if(httpServletRequest.getParameter("desfazer") != null){
					filtroResolucaoDiretoria
									.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.PARCELAMENTO_SITUACAO_ESPECIAL);
					filtroResolucaoDiretoria
									.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_CONDICOES_PAGTO_A_VISTA);
					filtroResolucaoDiretoria
									.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_CONDICOES_VALOR_ENTRADA);

					filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.NUMERO, httpServletRequest
									.getParameter("desfazer")));

					sessao.setAttribute("adicionaGrupoAcesso", null);
					sessao.setAttribute("removeGrupoAcesso", null);

					colecaoResolucaoDiretoria = fachada.pesquisar(filtroResolucaoDiretoria, ResolucaoDiretoria.class.getName());

					ResolucaoDiretoria resolucaoDiretoria = new ResolucaoDiretoria();

					if(!Util.isVazioOrNulo(colecaoResolucaoDiretoria)){
						resolucaoDiretoria = (ResolucaoDiretoria) Util.retonarObjetoDeColecao(colecaoResolucaoDiretoria);
					}

					carregaResolucaoDiretoriaGrupos(resolucaoDiretoria, fachada);

					FiltroResolucaoDiretoriaGrupo filtroResolucaoDiretoriaGrupo = new FiltroResolucaoDiretoriaGrupo();
					filtroResolucaoDiretoriaGrupo.adicionarParametro(new ParametroSimples("resolucaoDiretoria", resolucaoDiretoria));
					Collection colecaoResolucaoDiretoriaGruposAntigos = fachada.pesquisar(filtroResolucaoDiretoriaGrupo,
									ResolucaoDiretoriaGrupo.class.getName());
					Collection gruposAntigos = new ArrayList();

					if(!Util.isVazioOrNulo(colecaoResolucaoDiretoriaGruposAntigos)){
						for(Object resolucaoDiretoriaGrupoTemp : colecaoResolucaoDiretoriaGruposAntigos){
							ResolucaoDiretoriaGrupo resolucaoDiretoriaGrupo = (ResolucaoDiretoriaGrupo) resolucaoDiretoriaGrupoTemp;
							Grupo grupoAntigo = resolucaoDiretoriaGrupo.getGrupo();
							gruposAntigos.add(grupoAntigo);
						}
					}
					sessao.setAttribute("colecaoGruposAcessoHabilitados", gruposAntigos);

					Collection colecaoRestricaoDebitoSelecionados = new ArrayList();
					for(ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial : resolucaoDiretoria.getParcelamentosSituacaoEspecial()){
						colecaoRestricaoDebitoSelecionados.add(parcelamentoSituacaoEspecial);
					}
					sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionados);

					Collection colecaoCondicaoPagamentoAVistaSelecionados = new ArrayList();
					for(ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagtoAVista : resolucaoDiretoria
									.getResolucaoDiretoriaCondicoesPagtoAVista()){
						colecaoCondicaoPagamentoAVistaSelecionados.add(condicaoPagtoAVista);
					}
					sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionados);

					Collection colecaoCondicaoValorEntradaSelecionados = new ArrayList();
					for(ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada : resolucaoDiretoria
									.getResolucaoDiretoriaCondicoesValorEntrada()){
						colecaoCondicaoValorEntradaSelecionados.add(condicaoValorEntrada);
					}
					sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionados);

					// quando vem do filtrar
				}else{
					filtroResolucaoDiretoria
									.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.CODIGO, idResolucaoDiretoria));
					filtroResolucaoDiretoria.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoriaGrupos");
					filtroResolucaoDiretoria
									.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.PARCELAMENTO_SITUACAO_ESPECIAL);
					filtroResolucaoDiretoria
									.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_CONDICOES_PAGTO_A_VISTA);
					filtroResolucaoDiretoria
									.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_CONDICOES_VALOR_ENTRADA);

					colecaoResolucaoDiretoria = fachada.pesquisar(filtroResolucaoDiretoria, ResolucaoDiretoria.class.getName());
				}

				if(Util.isVazioOrNulo(colecaoResolucaoDiretoria)){
					throw new ActionServletException("atencao.atualizacao.timestamp");
				}

				ResolucaoDiretoria resolucaoDiretoria = new ResolucaoDiretoria();
				if(!Util.isVazioOrNulo(colecaoResolucaoDiretoria)){
					resolucaoDiretoria = (ResolucaoDiretoria) Util.retonarObjetoDeColecao(colecaoResolucaoDiretoria);
				}

				atualizarResolucaoDiretoriaActionForm.setNumero(resolucaoDiretoria.getNumeroResolucaoDiretoria());
				atualizarResolucaoDiretoriaActionForm.setAssunto(resolucaoDiretoria.getDescricaoAssunto());
				atualizarResolucaoDiretoriaActionForm.setDataInicio(Util.formatarData(resolucaoDiretoria.getDataVigenciaInicio()));
				atualizarResolucaoDiretoriaActionForm.setDataFim(Util.formatarData(resolucaoDiretoria.getDataVigenciaFim()));
				atualizarResolucaoDiretoriaActionForm.setIndicadorUsoRDParcImovel(resolucaoDiretoria.getIndicadorParcelamentoUnico()
								.toString());
				atualizarResolucaoDiretoriaActionForm
								.setIndicadorUsoRDUsuarios(resolucaoDiretoria.getIndicadorUtilizacaoLivre().toString());
				atualizarResolucaoDiretoriaActionForm.setIndicadorUsoRDDebitoCobrar(resolucaoDiretoria.getIndicadorDescontoSancoes()
								.toString());
				atualizarResolucaoDiretoriaActionForm.setIndicadorEmissaoAssuntoConta(resolucaoDiretoria.getIndicadorEmissaoAssuntoConta()
								.toString());
				atualizarResolucaoDiretoriaActionForm.setIndicadorTrataMediaAtualizacaoMonetaria(resolucaoDiretoria
								.getIndicadorTrataMediaAtualizacaoMonetaria().toString());
				atualizarResolucaoDiretoriaActionForm.setIndicadorCobrarDescontosArrasto(resolucaoDiretoria
								.getIndicadorCobrarDescontosArrasto().toString());
				atualizarResolucaoDiretoriaActionForm.setIndicadorArrasto(resolucaoDiretoria.getIndicadorArrasto().toString());

				if(resolucaoDiretoria.getResolucaoDiretoriaLayout() != null){
					String[] arrayString = new String[1];
					arrayString[0] = resolucaoDiretoria.getResolucaoDiretoriaLayout().getId().toString();
					atualizarResolucaoDiretoriaActionForm.setIdsResolucaoDiretoriaLayout(arrayString);
				}

				Collection<ResolucaoDiretoriaGrupo> resolucaoDiretoriaGrupoCarregar = new HashSet<ResolucaoDiretoriaGrupo>();
				Collection<Grupo> gruposCarregar = new ArrayList<Grupo>();
				carregaResolucaoDiretoriaGrupos(resolucaoDiretoria, fachada);
				if(!Util.isVazioOrNulo(resolucaoDiretoria.getResolucaoDiretoriaGrupos())){
					resolucaoDiretoriaGrupoCarregar = resolucaoDiretoria.getResolucaoDiretoriaGrupos();
					for(ResolucaoDiretoriaGrupo resolucaoDiretoriaGrupoTemp : resolucaoDiretoriaGrupoCarregar){
						gruposCarregar.add(resolucaoDiretoriaGrupoTemp.getGrupo());
					}
				}

				Collection<ParcelamentoSituacaoEspecial> parcelamentoSituacaoEspecialSet = new HashSet<ParcelamentoSituacaoEspecial>();
				Collection<ParcelamentoSituacaoEspecial> colecaoRestricaoDebitoSelecionados = new ArrayList<ParcelamentoSituacaoEspecial>();
				if(!Util.isVazioOrNulo(resolucaoDiretoria.getParcelamentosSituacaoEspecial())){
					parcelamentoSituacaoEspecialSet = resolucaoDiretoria.getParcelamentosSituacaoEspecial();
					for(ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial : parcelamentoSituacaoEspecialSet){
						colecaoRestricaoDebitoSelecionados.add(parcelamentoSituacaoEspecial);
					}
				}

				Collection<ResolucaoDiretoriaParametrosPagamentoAVista> condicoesPagtoAVistaSet = new HashSet<ResolucaoDiretoriaParametrosPagamentoAVista>();
				Collection<ResolucaoDiretoriaParametrosPagamentoAVista> colecaoCondicaoPagamentoAVistaSelecionados = new ArrayList<ResolucaoDiretoriaParametrosPagamentoAVista>();
				if(!Util.isVazioOrNulo(resolucaoDiretoria.getResolucaoDiretoriaCondicoesPagtoAVista())){
					condicoesPagtoAVistaSet = resolucaoDiretoria.getResolucaoDiretoriaCondicoesPagtoAVista();
					for(ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagtoAVista : condicoesPagtoAVistaSet){
						colecaoCondicaoPagamentoAVistaSelecionados.add(condicaoPagtoAVista);
					}
				}

				Collection<ResolucaoDiretoriaParametrosValorEntrada> condicoesValorEntradaSet = new HashSet<ResolucaoDiretoriaParametrosValorEntrada>();
				Collection<ResolucaoDiretoriaParametrosValorEntrada> colecaoCondicaoValorEntradaSelecionados = new ArrayList<ResolucaoDiretoriaParametrosValorEntrada>();
				if(!Util.isVazioOrNulo(resolucaoDiretoria.getResolucaoDiretoriaCondicoesValorEntrada())){
					condicoesValorEntradaSet = resolucaoDiretoria.getResolucaoDiretoriaCondicoesValorEntrada();
					for(ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada : condicoesValorEntradaSet){
						colecaoCondicaoValorEntradaSelecionados.add(condicaoValorEntrada);
					}
				}

				sessao.setAttribute("colecaoGruposAcessoHabilitados", gruposCarregar);
				sessao.setAttribute("colecaoGruposAcessoOriginais", gruposCarregar);
				sessao.setAttribute("resolucaoDiretoriaAtualizar", resolucaoDiretoria);
				sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionados);
				sessao.setAttribute("colecaoRestricaoDebitoOriginais", colecaoRestricaoDebitoSelecionados);
				sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionados);
				sessao.setAttribute("colecaoCondicaoPagamentoAVistaOriginais", colecaoCondicaoPagamentoAVistaSelecionados);
				sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionados);
				sessao.setAttribute("colecaoCondicaoValorEntradaOriginais", colecaoCondicaoValorEntradaSelecionados);
			}
		}

		// Preenche o filtro de ResolucaoDiretoriaLayout (Termo de Confissão de Dívida)
		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();

		filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.INDICADOR_USO, 1));

		filtroResolucaoDiretoriaLayout.setCampoOrderBy(FiltroResolucaoDiretoriaLayout.DESCRICAO);

		preencherColecao(filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName(), "colecaoResolucaoDiretoriaLayout",
						sessao, atualizarResolucaoDiretoriaActionForm, fachada, atualizarResolucaoDiretoriaActionForm
										.getIdsResolucaoDiretoriaLayout(), ResolucaoDiretoriaLayout.class);

		// Preenche o filtro de Grupos de Acesso Disponíveis

		FiltroGrupo filtroGruposAcesso = new FiltroGrupo();

		filtroGruposAcesso.adicionarParametro(new ParametroSimples(FiltroGrupo.INDICADOR_USO, 1));

		filtroGruposAcesso.setCampoOrderBy(FiltroGrupo.DESCRICAO);

		preencherColecao(filtroGruposAcesso, Grupo.class.getName(), "colecaoGruposAcessoDisponiveis", sessao,
						atualizarResolucaoDiretoriaActionForm, fachada, atualizarResolucaoDiretoriaActionForm
										.getIdsGruposAcessoDisponiveis(), Grupo.class);

		// Adiciona Grupos de Acesso Selecionado à coleção de Grupos de Acesso Habilitados
		if(httpServletRequest.getParameter("adicionaGrupoAcesso") != null){

			// recupera grupos habilitados
			Collection colecaoGruposAcessoHabilitados = (Collection) sessao.getAttribute("colecaoGruposAcessoHabilitados");
			// recupera id do grupo disponível selecionado
			Integer idGrupoSelecionado = Integer.parseInt(atualizarResolucaoDiretoriaActionForm.getIdsGruposAcessoDisponiveis()[0]);
			// recupera grupo disponíveis
			Collection colecaoGruposAcessoDisponiveis = (Collection) sessao.getAttribute("colecaoGruposAcessoDisponiveis");

			if(colecaoGruposAcessoHabilitados != null && colecaoGruposAcessoHabilitados.size() > 0){
				// verifica se grupo já está inserido na lista de habilitados
				for(Object gruposAcessoHabilitados : colecaoGruposAcessoHabilitados){
					Grupo grupoHabilitado = (Grupo) gruposAcessoHabilitados;
					if(grupoHabilitado.getId().equals(idGrupoSelecionado)){
						throw new ActionServletException("atencao.grupo_acesso_ja_associado_resolucao");
					}
				}
				// recupera e adiciona grupo disponível selecionado à lista de Habilitados
				for(Object gruposAcessoDisponivel : colecaoGruposAcessoDisponiveis){
					Grupo grupoDisponivel = (Grupo) gruposAcessoDisponivel;
					if(grupoDisponivel.getId().equals(idGrupoSelecionado)){
						colecaoGruposAcessoHabilitados.add(grupoDisponivel);
					}
				}
			}else{
				for(Object gruposAcessoDisponivel : colecaoGruposAcessoDisponiveis){
					Grupo grupoDisponivel = (Grupo) gruposAcessoDisponivel;
					if(grupoDisponivel.getId().equals(idGrupoSelecionado)){
						colecaoGruposAcessoHabilitados = new ArrayList();
						colecaoGruposAcessoHabilitados.add(grupoDisponivel);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoGruposAcessoHabilitados", colecaoGruposAcessoHabilitados);
		}

		// Adiciona Grupos de Acesso Selecionado à coleção de Grupos de Acesso Habilitados
		if(httpServletRequest.getParameter("adicionaRestricaoDebito") != null){
			Collection colecaoRestricaoDebitoSelecionados = (Collection) sessao.getAttribute("colecaoRestricaoDebitoSelecionados");

			// Valida as datas inseridas
			if(!Util.validarMesAno(atualizarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio())
							|| !Util.validarMesAno(atualizarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim())){
				throw new ActionServletException("atencao.ano_mes_referencia.invalida");
			}

			if(Util.formatarMesAnoComBarraParaAnoMes(atualizarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim()) < Util
							.formatarMesAnoComBarraParaAnoMes(atualizarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio())){
				throw new ActionServletException("atencao.resolucao_diretoria.restricao_debito.periodo.invalido");
			}

			// Valida a localidade
			if(!this.isLocalidadeValida(Integer.valueOf(atualizarResolucaoDiretoriaActionForm.getIdLocalidade()))){
				throw new ActionServletException("atencao.localidade.inexistente");
			}

			if(colecaoRestricaoDebitoSelecionados != null && colecaoRestricaoDebitoSelecionados.size() > 0){
				// verifica se já está inserido na lista
				for(Object parcelamentoSituacaoEspecialTemp : colecaoRestricaoDebitoSelecionados){
					ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = (ParcelamentoSituacaoEspecial) parcelamentoSituacaoEspecialTemp;
					if(parcelamentoSituacaoEspecial.getLocalidade().getId().equals(
									Integer.valueOf(atualizarResolucaoDiretoriaActionForm.getIdLocalidade()))){
						throw new ActionServletException("atencao.pesquisa_localidade_inserida_lista");
					}
				}

				ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = new ParcelamentoSituacaoEspecial();
				parcelamentoSituacaoEspecial.setLocalidade(new Localidade(Integer.valueOf(atualizarResolucaoDiretoriaActionForm
								.getIdLocalidade())));
				parcelamentoSituacaoEspecial.setAnoMesReferenciaDebitoInicio(Util
								.formatarMesAnoComBarraParaAnoMes(atualizarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio()));
				if(!Util.isVazioOuBranco(atualizarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim())){
					parcelamentoSituacaoEspecial
									.setAnoMesReferenciaDebitoFim(Util
													.formatarMesAnoComBarraParaAnoMes(atualizarResolucaoDiretoriaActionForm
																	.getAnoMesReferenciaDebitoFim()));
				}

				colecaoRestricaoDebitoSelecionados.add(parcelamentoSituacaoEspecial);

			}else{

				ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = new ParcelamentoSituacaoEspecial();
				parcelamentoSituacaoEspecial.setLocalidade(new Localidade(Integer.valueOf(atualizarResolucaoDiretoriaActionForm
								.getIdLocalidade())));
				parcelamentoSituacaoEspecial.setAnoMesReferenciaDebitoInicio(Util
								.formatarMesAnoComBarraParaAnoMes(atualizarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio()));
				if(!Util.isVazioOuBranco(atualizarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim())){
					parcelamentoSituacaoEspecial
									.setAnoMesReferenciaDebitoFim(Util
													.formatarMesAnoComBarraParaAnoMes(atualizarResolucaoDiretoriaActionForm
																	.getAnoMesReferenciaDebitoFim()));
				}

				colecaoRestricaoDebitoSelecionados = new ArrayList();
				colecaoRestricaoDebitoSelecionados.add(parcelamentoSituacaoEspecial);
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionados);

			atualizarResolucaoDiretoriaActionForm.setIdLocalidade(null);
			atualizarResolucaoDiretoriaActionForm.setNomeLocalidade(null);
			httpServletRequest.setAttribute("idLocalidadeEncontrado", false);
			atualizarResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoInicio(null);
			atualizarResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoFim(null);
		}

		// Adiciona Grupos de Acesso Selecionado à coleção de restrições de débitos
		if(httpServletRequest.getParameter("adicionaCondicaoPagamentoAVista") != null){

			Date dataPagamentoInicio = null;
			Date dataPagamentoFinal = null;
			BigDecimal percentualDescontoMulta = BigDecimal.ZERO;
			BigDecimal percentualDescontoJurosMora = BigDecimal.ZERO;
			BigDecimal percentualDescontoCorrecaoMonetaria = BigDecimal.ZERO;

			Collection colecaoCondicaoPagamentoAVistaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoPagamentoAVistaSelecionados");

			// Valida as datas inseridas
			if(atualizarResolucaoDiretoriaActionForm.getDataPagamentoInicio() == null
							|| Util.validarDiaMesAno(atualizarResolucaoDiretoriaActionForm.getDataPagamentoInicio())){
				throw new ActionServletException("atencao.data.invalida", "Data de Pagamento Inicial");
			}

			if(atualizarResolucaoDiretoriaActionForm.getDataPagamentoFinal() == null
							|| Util.validarDiaMesAno(atualizarResolucaoDiretoriaActionForm.getDataPagamentoFinal())){
				throw new ActionServletException("atencao.data.invalida", "Data de Pagamento Final");
			}

			dataPagamentoInicio = Util.converteStringParaDate(atualizarResolucaoDiretoriaActionForm.getDataPagamentoInicio(), false);
			dataPagamentoFinal = Util.converteStringParaDate(atualizarResolucaoDiretoriaActionForm.getDataPagamentoFinal(), false);

			if(Util.compararData(dataPagamentoInicio, dataPagamentoFinal) > 0){
				throw new ActionServletException("atencao.resolucao_diretoria.condicao_pagto_vista.periodo.invalido");
			}

			if(atualizarResolucaoDiretoriaActionForm.getPercentualDescontoMulta() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(atualizarResolucaoDiretoriaActionForm
											.getPercentualDescontoMulta().replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual de Desconto na Multa");
			}else{
				percentualDescontoMulta = Util.formatarMoedaRealparaBigDecimal(atualizarResolucaoDiretoriaActionForm
								.getPercentualDescontoMulta());

				if(percentualDescontoMulta.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "de Desconto na Multa");
				}
			}

			if(atualizarResolucaoDiretoriaActionForm.getPercentualDescontoJurosMora() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(atualizarResolucaoDiretoriaActionForm
											.getPercentualDescontoJurosMora().replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual de Desconto nos Juros Mora");
			}else{
				percentualDescontoJurosMora = Util.formatarMoedaRealparaBigDecimal(atualizarResolucaoDiretoriaActionForm
								.getPercentualDescontoJurosMora());

				if(percentualDescontoJurosMora.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "de Desconto nos Juros Mora");
				}
			}

			if(atualizarResolucaoDiretoriaActionForm.getPercentualDescontoCorrecaoMonetaria() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(atualizarResolucaoDiretoriaActionForm
											.getPercentualDescontoCorrecaoMonetaria().replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual de Desconto na Correção Monetária");
			}else{
				percentualDescontoCorrecaoMonetaria = Util.formatarMoedaRealparaBigDecimal(atualizarResolucaoDiretoriaActionForm
								.getPercentualDescontoCorrecaoMonetaria());

				if(percentualDescontoCorrecaoMonetaria.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "de Desconto na Correção Monetária");
				}
			}

			ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagamentoAVista = null;

			if(colecaoCondicaoPagamentoAVistaSelecionados != null && colecaoCondicaoPagamentoAVistaSelecionados.size() > 0){
				// verifica se já está inserido na lista
				for(Object condicaoPagamentoAVistaTemp : colecaoCondicaoPagamentoAVistaSelecionados){
					condicaoPagamentoAVista = (ResolucaoDiretoriaParametrosPagamentoAVista) condicaoPagamentoAVistaTemp;
					if(Util.periodoContemData(dataPagamentoInicio, dataPagamentoFinal, condicaoPagamentoAVista.getDataPagamentoInicio())
									|| Util.periodoContemData(dataPagamentoInicio, dataPagamentoFinal, condicaoPagamentoAVista
													.getDataPagamentoFinal())){
						throw new ActionServletException("atencao.resolucao_diretoria.periodo_ja_existente", Util.formatarData(Util
										.adicionarNumeroDiasDeUmaData(condicaoPagamentoAVista.getDataPagamentoFinal(), 1)));
					}
				}

			}else{
				colecaoCondicaoPagamentoAVistaSelecionados = new ArrayList();
			}

			condicaoPagamentoAVista = new ResolucaoDiretoriaParametrosPagamentoAVista();
			condicaoPagamentoAVista.setDataPagamentoInicio(dataPagamentoInicio);
			condicaoPagamentoAVista.setDataPagamentoFinal(dataPagamentoFinal);
			condicaoPagamentoAVista.setPercentualDescontoMulta(percentualDescontoMulta);
			condicaoPagamentoAVista.setPercentualDescontoJurosMora(percentualDescontoJurosMora);
			condicaoPagamentoAVista.setPercentualDescontoCorrecaoMonetaria(percentualDescontoCorrecaoMonetaria);
			condicaoPagamentoAVista.setDescricaoMensagemExtrato(atualizarResolucaoDiretoriaActionForm.getDescricaoMensagemExtrato());

			colecaoCondicaoPagamentoAVistaSelecionados.add(condicaoPagamentoAVista);

			// adiciona coleção à sessão
			sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionados);

			// limpando os campos do formulário
			atualizarResolucaoDiretoriaActionForm.setDataPagamentoInicio(null);
			atualizarResolucaoDiretoriaActionForm.setDataPagamentoFinal(null);
			atualizarResolucaoDiretoriaActionForm.setPercentualDescontoMulta(null);
			atualizarResolucaoDiretoriaActionForm.setPercentualDescontoJurosMora(null);
			atualizarResolucaoDiretoriaActionForm.setPercentualDescontoCorrecaoMonetaria(null);
			atualizarResolucaoDiretoriaActionForm.setDescricaoMensagemExtrato(null);
		}

		// Remove Condição de Pagamento à Vista Selecionado da coleção
		if(httpServletRequest.getParameter("removeCondicaoPagtoAVista") != null){

			Collection colecaoCondicaoPagamentoAVistaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoPagamentoAVistaSelecionados");

			// recupera a data inicial de pagamento da condição removida
			Date dataPagamentoInicio = Util.converteStringInvertidaSemBarraParaDate(httpServletRequest
							.getParameter("removeCondicaoPagtoAVista"));

			// recupera grupo disponíveis
			Collection colecaoCondicaoPagamentoAVistaSelecionadosNovos = new ArrayList();

			if(colecaoCondicaoPagamentoAVistaSelecionados != null && colecaoCondicaoPagamentoAVistaSelecionados.size() > 0){

				// recupera e remove grupo selecionado da lista de Habilitados
				for(Object condicaoPagamentoAVistaTemp : colecaoCondicaoPagamentoAVistaSelecionados){
					ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagamentoAVista = (ResolucaoDiretoriaParametrosPagamentoAVista) condicaoPagamentoAVistaTemp;
					if(Util.compararData(condicaoPagamentoAVista.getDataPagamentoInicio(), dataPagamentoInicio) != 0){
						colecaoCondicaoPagamentoAVistaSelecionadosNovos.add(condicaoPagamentoAVista);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionadosNovos);

			// limpando os campos do formulário
			atualizarResolucaoDiretoriaActionForm.setDataPagamentoInicio(null);
			atualizarResolucaoDiretoriaActionForm.setDataPagamentoFinal(null);
			atualizarResolucaoDiretoriaActionForm.setPercentualDescontoMulta(null);
			atualizarResolucaoDiretoriaActionForm.setPercentualDescontoJurosMora(null);
			atualizarResolucaoDiretoriaActionForm.setPercentualDescontoCorrecaoMonetaria(null);
			atualizarResolucaoDiretoriaActionForm.setDescricaoMensagemExtrato(null);
		}

		// Adiciona condições de entrada Selecionadas à coleção
		if(httpServletRequest.getParameter("adicionaCondicaoValorEntrada") != null){

			Date dataNegociacaoInicio = null;
			Date dataNegociacaoFinal = null;
			BigDecimal percentualMinimoEntrada = BigDecimal.ZERO;

			Collection colecaoCondicaoValorEntradaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoValorEntradaSelecionados");

			// Valida as datas inseridas
			if(atualizarResolucaoDiretoriaActionForm.getDataNegociacaoInicio() == null
							|| Util.validarDiaMesAno(atualizarResolucaoDiretoriaActionForm.getDataNegociacaoInicio())){
				throw new ActionServletException("atencao.data.invalida", "Data de Negociação Inicial");
			}

			if(atualizarResolucaoDiretoriaActionForm.getDataNegociacaoFinal() == null
							|| Util.validarDiaMesAno(atualizarResolucaoDiretoriaActionForm.getDataNegociacaoFinal())){
				throw new ActionServletException("atencao.data.invalida", "Data de Negociação Final");
			}

			dataNegociacaoInicio = Util.converteStringParaDate(atualizarResolucaoDiretoriaActionForm.getDataNegociacaoInicio(), false);
			dataNegociacaoFinal = Util.converteStringParaDate(atualizarResolucaoDiretoriaActionForm.getDataNegociacaoFinal(), false);

			if(Util.compararData(dataNegociacaoInicio, dataNegociacaoFinal) > 0){
				throw new ActionServletException("atencao.resolucao_diretoria.condicao_valor_entrada.periodo.invalido");
			}

			if(atualizarResolucaoDiretoriaActionForm.getPercentualMinimoEntrada() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(atualizarResolucaoDiretoriaActionForm
											.getPercentualMinimoEntrada().replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual Mínimo de Entrada");
			}else{
				percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(atualizarResolucaoDiretoriaActionForm
								.getPercentualMinimoEntrada());

				if(percentualMinimoEntrada.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "Mínimo de Entrada");
				}
			}

			ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada = null;

			if(colecaoCondicaoValorEntradaSelecionados != null && colecaoCondicaoValorEntradaSelecionados.size() > 0){
				// verifica se já está inserido na lista
				for(Object condicaoValorEntradaTemp : colecaoCondicaoValorEntradaSelecionados){
					condicaoValorEntrada = (ResolucaoDiretoriaParametrosValorEntrada) condicaoValorEntradaTemp;
					if(Util.periodoContemData(dataNegociacaoInicio, dataNegociacaoFinal, condicaoValorEntrada.getDataNegociacaoInicio())
									|| Util.periodoContemData(dataNegociacaoInicio, dataNegociacaoFinal, condicaoValorEntrada
													.getDataNegociacaoFinal())){
						throw new ActionServletException("atencao.resolucao_diretoria.periodo_ja_existente", Util.formatarData(Util
										.adicionarNumeroDiasDeUmaData(condicaoValorEntrada.getDataNegociacaoFinal(), 1)));
					}
				}

			}else{
				colecaoCondicaoValorEntradaSelecionados = new ArrayList();
			}

			condicaoValorEntrada = new ResolucaoDiretoriaParametrosValorEntrada();
			condicaoValorEntrada.setDataNegociacaoInicio(dataNegociacaoInicio);
			condicaoValorEntrada.setDataNegociacaoFinal(dataNegociacaoFinal);
			condicaoValorEntrada.setPercentualMinimoEntrada(percentualMinimoEntrada);

			colecaoCondicaoValorEntradaSelecionados.add(condicaoValorEntrada);

			// adiciona coleção à sessão
			sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionados);

			// limpando os campos do formulário
			atualizarResolucaoDiretoriaActionForm.setDataNegociacaoInicio(null);
			atualizarResolucaoDiretoriaActionForm.setDataNegociacaoFinal(null);
			atualizarResolucaoDiretoriaActionForm.setPercentualMinimoEntrada(null);
		}

		// Remove Condição de Valor de Entrada Selecionado da coleção
		if(httpServletRequest.getParameter("removeCondicaoValorEntrada") != null){

			Collection colecaoCondicaoValorEntradaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoValorEntradaSelecionados");

			// recupera a data inicial de negociação da condição removida
			Date dataNegociacaoInicio = Util.converteStringInvertidaSemBarraParaDate(httpServletRequest
							.getParameter("removeCondicaoValorEntrada"));

			// recupera grupo disponíveis
			Collection colecaoCondicaoValorEntradaSelecionadosNovos = new ArrayList();

			if(colecaoCondicaoValorEntradaSelecionados != null && colecaoCondicaoValorEntradaSelecionados.size() > 0){

				// recupera e remove grupo selecionado da lista de Habilitados
				for(Object condicaoValorEntradaTemp : colecaoCondicaoValorEntradaSelecionados){
					ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada = (ResolucaoDiretoriaParametrosValorEntrada) condicaoValorEntradaTemp;
					if(Util.compararData(condicaoValorEntrada.getDataNegociacaoInicio(), dataNegociacaoInicio) != 0){
						colecaoCondicaoValorEntradaSelecionadosNovos.add(condicaoValorEntrada);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionadosNovos);

			// limpando os campos do formulário
			atualizarResolucaoDiretoriaActionForm.setDataNegociacaoInicio(null);
			atualizarResolucaoDiretoriaActionForm.setDataNegociacaoFinal(null);
			atualizarResolucaoDiretoriaActionForm.setPercentualMinimoEntrada(null);
		}

		// Remove Grupo de Acesso Selecionado da coleção Grupo de Acesso Habilitados
		if(httpServletRequest.getParameter("removeGrupoAcesso") != null){

			// recupera grupos habilitados
			Collection colecaoGruposAcessoHabilitados = (Collection) sessao.getAttribute("colecaoGruposAcessoHabilitados");
			// recupera id do grupo disponível selecionado
			Integer idGrupoSelecionado = Integer.parseInt(httpServletRequest.getParameter("removeGrupoAcesso"));
			// recupera grupo disponíveis
			Collection colecaoGruposAcessoHabilitadosNovos = new ArrayList();

			// Verifica se o grupo pode ser Excluido
			if(!this.isGrupoAptoExclusao(sessao, idGrupoSelecionado)){
				throw new ActionServletException("atencao.resolucao_diretoria.remocao.grupo.nao_permitido");
			}

			if(colecaoGruposAcessoHabilitados != null && colecaoGruposAcessoHabilitados.size() > 0){

				// recupera e remove grupo selecionado da lista de Habilitados
				for(Object grupoAcessoHabilitado : colecaoGruposAcessoHabilitados){
					Grupo grupoHabilitado = (Grupo) grupoAcessoHabilitado;
					if(!grupoHabilitado.getId().equals(idGrupoSelecionado)){
						colecaoGruposAcessoHabilitadosNovos.add(grupoHabilitado);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoGruposAcessoHabilitados", colecaoGruposAcessoHabilitadosNovos);
		}

		if(httpServletRequest.getParameter("removeRestricaoDebito") != null){

			Collection colecaoRestricaoDebitoSelecionados = (Collection) sessao.getAttribute("colecaoRestricaoDebitoSelecionados");
			// recupera id do grupo disponível selecionado
			Integer idLocalidade = Integer.parseInt(httpServletRequest.getParameter("removeRestricaoDebito"));
			// recupera grupo disponíveis
			Collection colecaoRestricaoDebitoSelecionadosNovos = new ArrayList();

			// Verifica se a restricao pode ser excluida
			if(!this.isRestricaoDebitoAptoExclusao(sessao, idLocalidade)){
				throw new ActionServletException("atencao.resolucao_diretoria.remocao.restricao.nao_permitido");
			}

			if(colecaoRestricaoDebitoSelecionados != null && colecaoRestricaoDebitoSelecionados.size() > 0){

				// recupera e remove grupo selecionado da lista de Habilitados
				for(Object parcelamentoSituacaoEspecialTemp : colecaoRestricaoDebitoSelecionados){
					ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = (ParcelamentoSituacaoEspecial) parcelamentoSituacaoEspecialTemp;
					if(!parcelamentoSituacaoEspecial.getLocalidade().getId().equals(idLocalidade)){
						colecaoRestricaoDebitoSelecionadosNovos.add(parcelamentoSituacaoEspecial);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionadosNovos);

			atualizarResolucaoDiretoriaActionForm.setIdLocalidade(null);
			atualizarResolucaoDiretoriaActionForm.setNomeLocalidade(null);
			httpServletRequest.setAttribute("idLocalidadeEncontrado", false);
			atualizarResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoInicio(null);
			atualizarResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoFim(null);
		}

		if(httpServletRequest.getParameter("limparGrupos") != null){
			sessao.setAttribute("colecaoGruposAcessoHabilitados", null);
		}

		// Localidade
		if((!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1"))
						&& !Util.isVazioOuBranco(atualizarResolucaoDiretoriaActionForm.getIdLocalidade())){

			// [UC0023 - Pesquisar Localidade]
			this.pesquisarLocalidade(atualizarResolucaoDiretoriaActionForm, httpServletRequest);
		}else{
			atualizarResolucaoDiretoriaActionForm.setIdLocalidade(null);
			atualizarResolucaoDiretoriaActionForm.setNomeLocalidade("");
		}

		return retorno;

	}

	public String[] preencherColecao(Filtro filtro, String nomeClasse, String nomeAtributo, HttpSession sessao,
					AtualizarResolucaoDiretoriaActionForm atualizarResolucaoDiretoriaActionForm, Fachada fachada, String[] Ids, Class object){

		Collection colecao = fachada.pesquisar(filtro, nomeClasse);
		sessao.setAttribute(nomeAtributo, colecao);

		String[] idsRetorno = null;
		if(Ids == null || Ids.length == 0){
			Iterator iter = colecao.iterator();
			idsRetorno = new String[colecao.size()];
			int i = 0;
			while(iter.hasNext()){
				Object obj = (Object) iter.next();
				if(obj instanceof ResolucaoDiretoriaLayout){
					idsRetorno[i++] = ((ResolucaoDiretoriaLayout) obj).getId() + "";
				}else if(obj instanceof Grupo){
					idsRetorno[i++] = ((Grupo) obj).getId() + "";
				}
			}
		}
		return idsRetorno;
	}

	public ResolucaoDiretoria carregaResolucaoDiretoriaGrupos(ResolucaoDiretoria resolucaoDiretoria, Fachada fachada){

		FiltroResolucaoDiretoriaGrupo filtroResolucaoDiretoriaGrupo = new FiltroResolucaoDiretoriaGrupo();
		filtroResolucaoDiretoriaGrupo.adicionarParametro(new ParametroSimples("resolucaoDiretoria", resolucaoDiretoria));
		Collection colecaoResolucaoDiretoriaGruposAntigos = fachada.pesquisar(filtroResolucaoDiretoriaGrupo, ResolucaoDiretoriaGrupo.class
						.getName());
		Set<ResolucaoDiretoriaGrupo> resolucaoDiretoriaGrupoSet = new HashSet<ResolucaoDiretoriaGrupo>();

		if(!Util.isVazioOrNulo(colecaoResolucaoDiretoriaGruposAntigos)){
			for(Object resolucaoDiretoriaGrupoTemp : colecaoResolucaoDiretoriaGruposAntigos){
				ResolucaoDiretoriaGrupo resolucaoDiretoriaGrupo = (ResolucaoDiretoriaGrupo) resolucaoDiretoriaGrupoTemp;
				resolucaoDiretoriaGrupoSet.add(resolucaoDiretoriaGrupo);
			}
			resolucaoDiretoria.setResolucaoDiretoriaGrupos(resolucaoDiretoriaGrupoSet);
		}else{
			resolucaoDiretoria.setResolucaoDiretoriaGrupos(null);
		}
		return resolucaoDiretoria;
	}

	private void pesquisarLocalidade(AtualizarResolucaoDiretoriaActionForm form, HttpServletRequest request){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(form.getIdLocalidade())));

		Collection<Localidade> colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// [FS0008 – Verificar existência da localidade]
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setIdLocalidade(localidade.getId().toString());
			form.setNomeLocalidade(localidade.getDescricao());
			request.setAttribute("idLocalidadeEncontrado", true);

		}else{

			form.setIdLocalidade("");
			form.setNomeLocalidade("Localidade Inexistente");
		}
	}

	private boolean isLocalidadeValida(Integer idLocalidade){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

		Collection<Localidade> colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// [FS0008 – Verificar existência da localidade]
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	private boolean isGrupoAptoExclusao(HttpSession sessao, Integer idGrupo){

		// Verifica se não existe ocorrência na tabela de parcelamento

		// só será possível a remoção de um grupo já associado à RD, caso a RD não tenha sido
		// utilizada em nenhum parcelamento (não existe ocorrência na tabela PARCELAMENTO com
		// RDIR_ID=RDIR_ID da tabela RESOLUCAO_DIRETORIA))
		ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) sessao.getAttribute("resolucaoDiretoriaAtualizar");

		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.RESOLUCAO_DIRETORIA_ID, resolucaoDiretoria.getId()));

		Collection colecaoParcelamento = Fachada.getInstancia().pesquisar(filtroParcelamento, Parcelamento.class.getName());

		if(Util.isVazioOrNulo(colecaoParcelamento)){
			return true;
		}

		if(!Util.isVazioOuBranco(sessao.getAttribute("colecaoGruposAcessoOriginais"))){
			Collection<Grupo> colecaoGrupos = (Collection<Grupo>) sessao.getAttribute("colecaoGruposAcessoOriginais");
			if(!Util.isVazioOrNulo(colecaoGrupos)){
				for(Grupo grupo : colecaoGrupos){
					if(idGrupo.equals(grupo.getId())){
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean isRestricaoDebitoAptoExclusao(HttpSession sessao, Integer idLocalidade){

		// Verifica se não existe ocorrência na tabela de parcelamento

		// só será possível a remoção de uma restrição já associada à RD, caso a RD não tenha sido
		// utilizada em nenhum parcelamento (não existe ocorrência na tabela PARCELAMENTO com
		// RDIR_ID=RDIR_ID da tabela RESOLUCAO_DIRETORIA)):
		ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) sessao.getAttribute("resolucaoDiretoriaAtualizar");

		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.RESOLUCAO_DIRETORIA_ID, resolucaoDiretoria.getId()));

		Collection colecaoParcelamento = Fachada.getInstancia().pesquisar(filtroParcelamento, Parcelamento.class.getName());

		if(Util.isVazioOrNulo(colecaoParcelamento)){
			return true;
		}

		if(!Util.isVazioOuBranco(sessao.getAttribute("colecaoRestricaoDebitoOriginais"))){
			Collection<ParcelamentoSituacaoEspecial> colecaoRestricoes = (Collection<ParcelamentoSituacaoEspecial>) sessao
							.getAttribute("colecaoRestricaoDebitoOriginais");
			if(!Util.isVazioOrNulo(colecaoRestricoes)){
				for(ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial : colecaoRestricoes){
					if(idLocalidade.equals(parcelamentoSituacaoEspecial.getLocalidade().getId())){
						return false;
					}
				}
			}
		}
		return true;
	}

}