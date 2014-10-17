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
 * Thiago Vieira
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

package gcom.gui.cobranca.spcserasa;

import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.FiltroNegativCritCobrGrupo;
import gcom.cobranca.FiltroNegativCritElo;
import gcom.cobranca.FiltroNegativCritGerReg;
import gcom.cobranca.FiltroNegativCritUndNeg;
import gcom.cobranca.FiltroNegativacaoComando;
import gcom.cobranca.FiltroNegativacaoCriterio;
import gcom.cobranca.FiltroNegativacaoCriterioClienteTipo;
import gcom.cobranca.FiltroNegativacaoCriterioCpfTipo;
import gcom.cobranca.FiltroNegativacaoCriterioImovelPerfil;
import gcom.cobranca.FiltroNegativacaoCriterioSubcategoria;
import gcom.cobranca.NegativCritCobrGrupo;
import gcom.cobranca.NegativCritElo;
import gcom.cobranca.NegativCritGerReg;
import gcom.cobranca.NegativCritUndNeg;
import gcom.cobranca.NegativacaoComando;
import gcom.cobranca.NegativacaoCriterio;
import gcom.cobranca.NegativacaoCriterioClienteTipo;
import gcom.cobranca.NegativacaoCriterioCpfTipo;
import gcom.cobranca.NegativacaoCriterioImovelPerfil;
import gcom.cobranca.NegativacaoCriterioSubcategoria;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Thiago Vieira
 */
public class ExibirConsultarComandoNegativacaoDadosGeraisAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirConsultarDadosInclusoes");
		HttpSession sessao = httpServletRequest.getSession(false);
		ConsultarDadosNegativacaoComandoActionForm form = (ConsultarDadosNegativacaoComandoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		if(httpServletRequest.getParameter("idSelecionado") != null && !httpServletRequest.getParameter("idSelecionado").equals("")){

			String idComandoNegativacaoSelecionado = httpServletRequest.getParameter("idSelecionado");

			FiltroNegativacaoComando filtroNegativacaoComando = new FiltroNegativacaoComando();
			filtroNegativacaoComando.adicionarParametro(new ParametroSimples(FiltroNegativacaoComando.ID, Integer
							.valueOf(idComandoNegativacaoSelecionado)));
			filtroNegativacaoComando.adicionarCaminhoParaCarregamentoEntidade("negativador.cliente");
			filtroNegativacaoComando.adicionarCaminhoParaCarregamentoEntidade("usuario");

			Collection collNegativacaoComando = fachada.pesquisar(filtroNegativacaoComando, NegativacaoComando.class.getName());
			NegativacaoComando negativacaoComando = (NegativacaoComando) collNegativacaoComando.iterator().next();

			if(negativacaoComando.getNegativador().getCliente().getNome() != null){
				form.setNegativador(negativacaoComando.getNegativador().getCliente().getNome());
			}

			FiltroNegativacaoCriterio filtroNegativacaoCriterio = new FiltroNegativacaoCriterio();
			filtroNegativacaoCriterio.adicionarParametro(new ParametroSimples(FiltroNegativacaoCriterio.ID_NEGATIVACAO_COMANDO,
							negativacaoComando.getId()));
			filtroNegativacaoCriterio.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");
			filtroNegativacaoCriterio.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroNegativacaoCriterio.adicionarCaminhoParaCarregamentoEntidade("localidadeInicial");
			filtroNegativacaoCriterio.adicionarCaminhoParaCarregamentoEntidade("localidadeFinal");

			Collection collNegativacaoCriterio = fachada.pesquisar(filtroNegativacaoCriterio, NegativacaoCriterio.class.getName());
			NegativacaoCriterio negativacaoCriterio = (NegativacaoCriterio) collNegativacaoCriterio.iterator().next();

			if(negativacaoCriterio.getDescricaoTitulo() != null){
				form.setTituloComando(negativacaoCriterio.getDescricaoTitulo());
			}

			if(negativacaoCriterio.getDescricaoSolicitacao() != null){
				form.setDescricaoSolicitacao(negativacaoCriterio.getDescricaoSolicitacao());
			}

			if(Integer.valueOf(negativacaoComando.getIndicadorSimulacao()) != null){
				form.setSimularNegativacao(Integer.valueOf(negativacaoComando.getIndicadorSimulacao()).toString());
			}

			if(negativacaoComando.getDataPrevista() != null){
				form.setDataPrevistaExecucao(Util.formatarData(negativacaoComando.getDataPrevista()));
			}

			if(negativacaoComando.getUsuario().getNomeUsuario() != null){
				form.setUsuarioResponsavel(negativacaoComando.getUsuario().getNomeUsuario());
			}

			if(negativacaoCriterio.getQuantidadeMaximaInclusoes() != null){
				form.setQuantidadeMaximaInclusoes(negativacaoCriterio.getQuantidadeMaximaInclusoes().toString());
			}

			FiltroNegativacaoCriterioCpfTipo filtroNegativacaoCriterioCpfTipo = new FiltroNegativacaoCriterioCpfTipo();
			filtroNegativacaoCriterioCpfTipo.adicionarParametro(new ParametroSimples(
							FiltroNegativacaoCriterioCpfTipo.ID_NEGATIVACAO_CRITERIO, negativacaoCriterio.getId()));
			filtroNegativacaoCriterioCpfTipo.adicionarCaminhoParaCarregamentoEntidade("cpfTipo");
			filtroNegativacaoCriterioCpfTipo.adicionarCaminhoParaCarregamentoEntidade("negativacaoCriterio.cliente");
			filtroNegativacaoCriterioCpfTipo.adicionarCaminhoParaCarregamentoEntidade("negativacaoCriterio.clienteRelacaoTipo");

			Collection collNegativacaoCriterioCpfTipo = fachada.pesquisar(filtroNegativacaoCriterioCpfTipo,
							NegativacaoCriterioCpfTipo.class.getName());
			sessao.setAttribute("collNegativacaoCriterioCpfTipo", collNegativacaoCriterioCpfTipo);

			if(negativacaoCriterio.getAnoMesReferenciaContaInicial() != null){
				form
								.setPeriodoReferenciaDebitoInicial(Util.formatarAnoMesParaMesAno(negativacaoCriterio
												.getAnoMesReferenciaContaInicial()));
			}

			if(negativacaoCriterio.getAnoMesReferenciaContaFinal() != null){
				form.setPeriodoReferenciaDebitoFinal(Util.formatarAnoMesParaMesAno(negativacaoCriterio.getAnoMesReferenciaContaFinal()));
			}

			if(negativacaoCriterio.getDataVencimentoDebitoInicial() != null){
				form.setPeriodoVencimentoDebitoInicial(Util.formatarData(negativacaoCriterio.getDataVencimentoDebitoInicial()));
			}

			if(negativacaoCriterio.getDataVencimentoDebitoFinal() != null){
				form.setPeriodoVencimentoDebitoFinal(Util.formatarData(negativacaoCriterio.getDataVencimentoDebitoFinal()));
			}

			if(negativacaoCriterio.getValorMinimoDebito() != null){
				form.setValorDebitoInicial(negativacaoCriterio.getValorMinimoDebito().toString());
			}

			if(negativacaoCriterio.getValorMaximoDebito() != null){
				form.setValorDebitoFinal(negativacaoCriterio.getValorMaximoDebito().toString());
			}

			if(Integer.valueOf(negativacaoCriterio.getQuantidadeMinimaContas()) != null){
				form.setNumeroContasInicial(Integer.valueOf(negativacaoCriterio.getQuantidadeMinimaContas()).toString());
			}

			if(Integer.valueOf(negativacaoCriterio.getQuantidadeMaximaContas()) != null){
				form.setNumeroContasFinal(Integer.valueOf(negativacaoCriterio.getQuantidadeMaximaContas()).toString());
			}

			if(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoContaRevisao()) != null){
				form.setConsiderarContasRevisao(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoContaRevisao()).toString());
			}

			if(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoGuiaPagamento()) != null){
				form.setConsiderarGuiasPagamento(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoGuiaPagamento()).toString());
			}

			if(Short.valueOf(negativacaoCriterio.getIndicadorParcelamentoAtraso()) != null){
				form.setParcelamentoAtraso(Short.valueOf(negativacaoCriterio.getIndicadorParcelamentoAtraso()).toString());
			}

			if(negativacaoCriterio.getNumeroDiasParcelamentoAtraso() != null){
				form.setDiasAtrasoParcelamento(negativacaoCriterio.getNumeroDiasParcelamentoAtraso().toString());
			}

			if(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoRecebimentoCartaParcelamento()) != null){
				form.setRecebeuCartaParcelamentoAtraso(Short.valueOf(
								negativacaoCriterio.getIndicadorNegativacaoRecebimentoCartaParcelamento()).toString());
			}

			if(negativacaoCriterio.getNumeroDiasAtrasoRecebimentoCartaParcelamento() != null){
				form.setDiasAtrasoAposRecebimentoCarta(negativacaoCriterio.getNumeroDiasAtrasoRecebimentoCartaParcelamento().toString());
			}

			if(negativacaoCriterio.getCliente() != null){
				if(negativacaoCriterio.getCliente().getNome() != null){
					form.setCliente(negativacaoCriterio.getCliente().getNome());
				}

				if(negativacaoCriterio.getCliente().getId() != null){
					form.setIdCliente(negativacaoCriterio.getCliente().getId().toString());
				}

			}

			if(negativacaoCriterio.getClienteRelacaoTipo() != null){
				if(negativacaoCriterio.getClienteRelacaoTipo().getDescricao() != null){
					form.setTipoRelacaoCliente(negativacaoCriterio.getClienteRelacaoTipo().getDescricao());
				}

			}

			if(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoImovelParalisacao()) != null){
				form.setImovelSitEspecialCobranca(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoImovelParalisacao()).toString());
			}

			if(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoImovelSituacaoCobranca()) != null){
				form.setImovelSitCobranca(Short.valueOf(negativacaoCriterio.getIndicadorNegativacaoImovelSituacaoCobranca()).toString());
			}

			if(negativacaoCriterio.getLocalidadeInicial() != null){
				form.setLocalidadeInicial(negativacaoCriterio.getLocalidadeInicial().getDescricao());
			}

			if(negativacaoCriterio.getLocalidadeFinal() != null){
				form.setLocalidadeFinal(negativacaoCriterio.getLocalidadeFinal().getDescricao());
			}

			if(negativacaoCriterio.getLocalidadeInicial() != null){
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
								negativacaoCriterio.getCodigoSetorComercialInicial()));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, negativacaoCriterio
								.getLocalidadeInicial().getId()));
				Collection collSetorComercialInicial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(collSetorComercialInicial != null && !collSetorComercialInicial.isEmpty()){
					SetorComercial setorComercialInicial = (SetorComercial) collSetorComercialInicial.iterator().next();
					if(setorComercialInicial != null){
						form.setSetorComercialInicial(setorComercialInicial.getDescricao());
					}

				}

				filtroSetorComercial.limparListaParametros();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
								negativacaoCriterio.getCodigoSetorComercialFinal()));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, negativacaoCriterio
								.getLocalidadeInicial().getId()));
				Collection collSetorComercialFinal = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(collSetorComercialFinal != null && !collSetorComercialFinal.isEmpty()){
					SetorComercial setorComercialFinal = (SetorComercial) collSetorComercialFinal.iterator().next();
					if(setorComercialFinal != null){
						form.setSetorComercialFinal(setorComercialFinal.getDescricao());
					}
				}
			}

			// Lista de subcategoria
			FiltroNegativacaoCriterioSubcategoria filtroNegativacaoCriterioSubcategoria = new FiltroNegativacaoCriterioSubcategoria();
			filtroNegativacaoCriterioSubcategoria.adicionarParametro(new ParametroSimples(
							FiltroNegativacaoCriterioSubcategoria.COMP_ID_NEGATIVACAO_CRITERIO_ID, negativacaoCriterio.getId()));
			filtroNegativacaoCriterioSubcategoria.adicionarCaminhoParaCarregamentoEntidade("comp_id.subcategoria");
			Collection collNegativacaoCriterioSubcategoria = fachada.pesquisar(filtroNegativacaoCriterioSubcategoria,
							NegativacaoCriterioSubcategoria.class.getName());
			sessao.setAttribute("collNegativacaoCriterioSubcategoria", collNegativacaoCriterioSubcategoria);

			// //Lista de Perfil do Imovel
			FiltroNegativacaoCriterioImovelPerfil filtroNegativacaoCriterioImovelPerfil = new FiltroNegativacaoCriterioImovelPerfil();
			filtroNegativacaoCriterioImovelPerfil.adicionarParametro(new ParametroSimples(
							FiltroNegativacaoCriterioImovelPerfil.COMP_ID_NEGATIVACAO_CRITERIO_ID, negativacaoCriterio.getId()));
			filtroNegativacaoCriterioImovelPerfil.adicionarCaminhoParaCarregamentoEntidade("comp_id.imovelPerfil");
			Collection collNegativacaoCriterioImovelPerfil = fachada.pesquisar(filtroNegativacaoCriterioImovelPerfil,
							NegativacaoCriterioImovelPerfil.class.getName());
			sessao.setAttribute("collNegativacaoCriterioImovelPerfil", collNegativacaoCriterioImovelPerfil);

			// Lista de Perfil do Imovel
			FiltroNegativacaoCriterioClienteTipo filtroNegativacaoCriterioClienteTipo = new FiltroNegativacaoCriterioClienteTipo();
			filtroNegativacaoCriterioClienteTipo.adicionarParametro(new ParametroSimples(
							FiltroNegativacaoCriterioClienteTipo.COMP_ID_NEGATIVACAO_CRITERIO_ID, negativacaoCriterio.getId()));
			filtroNegativacaoCriterioClienteTipo.adicionarCaminhoParaCarregamentoEntidade("comp_id.clienteTipo");
			Collection collNegativacaoCriterioClienteTipo = fachada.pesquisar(filtroNegativacaoCriterioClienteTipo,
							NegativacaoCriterioClienteTipo.class.getName());
			sessao.setAttribute("collNegativacaoCriterioClienteTipo", collNegativacaoCriterioClienteTipo);

			// Lista de Grupo de Cobrança
			FiltroNegativCritCobrGrupo filtroNegativCritCobrGrupo = new FiltroNegativCritCobrGrupo();
			filtroNegativCritCobrGrupo.adicionarParametro(new ParametroSimples(FiltroNegativCritCobrGrupo.COMP_ID_NEGATIVACAO_CRITERIO_ID,
							negativacaoCriterio.getId()));
			filtroNegativCritCobrGrupo.adicionarCaminhoParaCarregamentoEntidade("comp_id.cobrancaGrupo");
			Collection collNegativCritCobrGrupo = fachada.pesquisar(filtroNegativCritCobrGrupo, NegativCritCobrGrupo.class.getName());
			sessao.setAttribute("collNegativCritCobrGrupo", collNegativCritCobrGrupo);

			// Lista de Gerencia Regional
			FiltroNegativCritGerReg filtroNegativCritGerReg = new FiltroNegativCritGerReg();
			filtroNegativCritGerReg.adicionarParametro(new ParametroSimples(FiltroNegativCritGerReg.COMP_ID_NEGATIVACAO_CRITERIO_ID,
							negativacaoCriterio.getId()));
			filtroNegativCritGerReg.adicionarCaminhoParaCarregamentoEntidade("comp_id.gerenciaRegional");
			Collection collNegativCritGerReg = fachada.pesquisar(filtroNegativCritGerReg, NegativCritGerReg.class.getName());
			sessao.setAttribute("collNegativCritGerReg", collNegativCritGerReg);

			// Lista de Unidade de Negócio
			FiltroNegativCritUndNeg filtroNegativCritUndNeg = new FiltroNegativCritUndNeg();
			filtroNegativCritUndNeg.adicionarParametro(new ParametroSimples(FiltroNegativCritUndNeg.COMP_ID_NEGATIVACAO_CRITERIO_ID,
							negativacaoCriterio.getId()));
			filtroNegativCritUndNeg.adicionarCaminhoParaCarregamentoEntidade("comp_id.unidadeNegocio");
			Collection collNegativCritUndNeg = fachada.pesquisar(filtroNegativCritUndNeg, NegativCritUndNeg.class.getName());
			sessao.setAttribute("collNegativCritUndNeg", collNegativCritUndNeg);

			// Lista de Elo Polo
			FiltroNegativCritElo filtroNegativCritElo = new FiltroNegativCritElo();
			filtroNegativCritElo.adicionarParametro(new ParametroSimples(FiltroNegativCritElo.COMP_ID_NEGATIVACAO_CRITERIO_ID,
							negativacaoCriterio.getId()));
			filtroNegativCritElo.adicionarCaminhoParaCarregamentoEntidade("comp_id.localidade");
			Collection collNegativCritElo = fachada.pesquisar(filtroNegativCritElo, NegativCritElo.class.getName());
			sessao.setAttribute("collNegativCritElo", collNegativCritElo);

		}
		return retorno;

	}
}