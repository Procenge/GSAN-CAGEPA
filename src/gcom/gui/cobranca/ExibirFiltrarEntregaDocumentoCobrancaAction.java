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

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeComando;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * FiltrarEntregaDocumentoCobrancaAction
 * 
 * @author Cinthya Cavalcanti
 */
public class ExibirFiltrarEntregaDocumentoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("filtrarEntregaDocumentoCobrancaAction");

		FiltrarEntregaDocumentoCobrancaActionForm filtrarEntregaDocumentoCobrancaActionForm = (FiltrarEntregaDocumentoCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("limparForm") != null && !httpServletRequest.getParameter("limparForm").equals("")){

			filtrarEntregaDocumentoCobrancaActionForm.setIndicadorDocumentos(String.valueOf(ConstantesSistema.SIM));
			filtrarEntregaDocumentoCobrancaActionForm.setIndicadorOrdenacaoDocumentos(String.valueOf(ConstantesSistema.SIM));
			filtrarEntregaDocumentoCobrancaActionForm.setCobrancaAcaoAtividadeComando("");
			filtrarEntregaDocumentoCobrancaActionForm.setCobrancaAcaoAtividadeComandoDescricao("");
			filtrarEntregaDocumentoCobrancaActionForm.setSequencialInicialDocumentos("");
			filtrarEntregaDocumentoCobrancaActionForm.setSequencialFinalDocumentos("");
			filtrarEntregaDocumentoCobrancaActionForm.setLocalidadeInicial("");
			filtrarEntregaDocumentoCobrancaActionForm.setDescricaoLocalidadeInicial("");
			filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialInicial("");
			filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialInicialCodigo("");
			filtrarEntregaDocumentoCobrancaActionForm.setDescricaoSetorComercialInicial("");
			filtrarEntregaDocumentoCobrancaActionForm.setQuadraInicial("");
			filtrarEntregaDocumentoCobrancaActionForm.setLocalidadeFinal("");
			filtrarEntregaDocumentoCobrancaActionForm.setDescricaoLocalidadeFinal("");
			filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialFinal("");
			filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialFinalCodigo("");
			filtrarEntregaDocumentoCobrancaActionForm.setDescricaoSetorComercialFinal("");
			filtrarEntregaDocumentoCobrancaActionForm.setQuadraFinal("");
			filtrarEntregaDocumentoCobrancaActionForm.setImovel("");
			filtrarEntregaDocumentoCobrancaActionForm.setInscricaoImovel("");
			filtrarEntregaDocumentoCobrancaActionForm.setDataInicialGeracao("");
			filtrarEntregaDocumentoCobrancaActionForm.setDataFinalGeracao("");
			filtrarEntregaDocumentoCobrancaActionForm.setEmpresa("");
			filtrarEntregaDocumentoCobrancaActionForm.setEmpresaCriterioPeloComando("");

		}else if(httpServletRequest.getParameter("menu") != null && !httpServletRequest.getParameter("menu").equals("")){

			filtrarEntregaDocumentoCobrancaActionForm.setIndicadorDocumentos(String.valueOf(ConstantesSistema.SIM));
			filtrarEntregaDocumentoCobrancaActionForm.setIndicadorOrdenacaoDocumentos(String.valueOf(ConstantesSistema.SIM));
		}else{

			// Preenche o campo comando
			preencherComando(httpServletRequest, filtrarEntregaDocumentoCobrancaActionForm, fachada);

			// Preenche o campo localidade inicial e final
			preencherLocalidade(httpServletRequest, filtrarEntregaDocumentoCobrancaActionForm, fachada);

			// Preenche o campo setor comercial inicial e final
			preencherSetorComercial(httpServletRequest, filtrarEntregaDocumentoCobrancaActionForm, fachada);

			// Preenche o campo rota inicial e final
			preencherQuadra(httpServletRequest, filtrarEntregaDocumentoCobrancaActionForm, fachada);

			// Preenche o campo imóvel
			preencherImovel(httpServletRequest, filtrarEntregaDocumentoCobrancaActionForm, fachada);

		}

		// Preenche o campo Empresa
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

		filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		if(colecaoEmpresa == null || colecaoEmpresa.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Empresa");
		}

		sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);

		// Preenche o campo Ação de Cobrança
		preencherCobrancaAcao(fachada, sessao);

		return retorno;
	}

	// Preenche o campo localidade inicial e final
	private void preencherLocalidade(HttpServletRequest httpServletRequest,
					FiltrarEntregaDocumentoCobrancaActionForm filtrarEntregaDocumentoCobrancaActionForm, Fachada fachada){

		if(filtrarEntregaDocumentoCobrancaActionForm.getLocalidadeInicial() != null
						&& !filtrarEntregaDocumentoCobrancaActionForm.getLocalidadeInicial().trim().equalsIgnoreCase("")){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, filtrarEntregaDocumentoCobrancaActionForm
							.getLocalidadeInicial()));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){

				httpServletRequest.setAttribute("idLocalidadeInicialNaoEncontrado", "exception");

				filtrarEntregaDocumentoCobrancaActionForm.setLocalidadeInicial("");

				filtrarEntregaDocumentoCobrancaActionForm.setDescricaoLocalidadeInicial("LOCALIDADE INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "localidadeInicial");

			}else{

				// Preenche Localidade Inicial
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				filtrarEntregaDocumentoCobrancaActionForm.setLocalidadeInicial(String.valueOf(localidade.getId().toString()));

				filtrarEntregaDocumentoCobrancaActionForm.setDescricaoLocalidadeInicial(localidade.getDescricao());

				if(filtrarEntregaDocumentoCobrancaActionForm.getLocalidadeFinal() == null
								|| filtrarEntregaDocumentoCobrancaActionForm.getLocalidadeFinal().equals("")){
					// Preenche Localidade Final
					filtrarEntregaDocumentoCobrancaActionForm.setLocalidadeFinal(String.valueOf(localidade.getId().toString()));

					filtrarEntregaDocumentoCobrancaActionForm.setDescricaoLocalidadeFinal(localidade.getDescricao());
				}
			}
		}

		if(filtrarEntregaDocumentoCobrancaActionForm.getLocalidadeFinal() != null
						&& !filtrarEntregaDocumentoCobrancaActionForm.getLocalidadeFinal().trim().equalsIgnoreCase("")){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, filtrarEntregaDocumentoCobrancaActionForm
							.getLocalidadeFinal()));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){

				httpServletRequest.setAttribute("idLocalidadeFinalNaoEncontrado", "exception");

				filtrarEntregaDocumentoCobrancaActionForm.setLocalidadeFinal("");

				filtrarEntregaDocumentoCobrancaActionForm.setDescricaoLocalidadeFinal("LOCALIDADE INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "localidadeFinal");

			}else{
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				filtrarEntregaDocumentoCobrancaActionForm.setLocalidadeFinal(String.valueOf(localidade.getId().toString()));

				filtrarEntregaDocumentoCobrancaActionForm.setDescricaoLocalidadeFinal(localidade.getDescricao());

			}
		}
	}

	// Preenche o campo setor comercial inicial e final
	private void preencherSetorComercial(HttpServletRequest httpServletRequest,
					FiltrarEntregaDocumentoCobrancaActionForm filtrarEntregaDocumentoCobrancaActionForm, Fachada fachada){

		if(filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialInicialCodigo() != null
						&& !filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialInicialCodigo().trim().equalsIgnoreCase("")){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
							filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialInicialCodigo()));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){

				httpServletRequest.setAttribute("idSetorComercialInicialNaoEncontrado", "exception");

				filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialInicial("");

				filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialInicialCodigo("");

				filtrarEntregaDocumentoCobrancaActionForm.setDescricaoSetorComercialInicial("SETOR COMERCIAL INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "setorComercialInicial");

			}else{

				// Preenche Setor Comercial Inicial
				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialInicial(String.valueOf(setorComercial.getId().toString()));

				filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialInicialCodigo(String.valueOf(setorComercial.getCodigo()));

				filtrarEntregaDocumentoCobrancaActionForm.setDescricaoSetorComercialInicial(setorComercial.getDescricao());

				if(filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialFinalCodigo() == null
								|| filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialFinalCodigo().equals("")){

					// Preenche Setor Comercial Final
					filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialFinal(String.valueOf(setorComercial.getId().toString()));

					filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialFinalCodigo(String.valueOf(setorComercial.getCodigo()));

					filtrarEntregaDocumentoCobrancaActionForm.setDescricaoSetorComercialFinal(setorComercial.getDescricao());
				}
			}
		}

		if(filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialFinalCodigo() != null
						&& !filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialFinalCodigo().trim().equalsIgnoreCase("")){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
							filtrarEntregaDocumentoCobrancaActionForm.getSetorComercialFinalCodigo()));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){

				httpServletRequest.setAttribute("idSetorComercialFinalNaoEncontrado", "exception");

				filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialFinal("");

				filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialFinalCodigo("");

				filtrarEntregaDocumentoCobrancaActionForm.setDescricaoSetorComercialFinal("SETOR COMERCIAL INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "setorComercialFinal");

			}else{
				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialFinal(String.valueOf(setorComercial.getId().toString()));

				filtrarEntregaDocumentoCobrancaActionForm.setSetorComercialFinalCodigo(String.valueOf(setorComercial.getCodigo()));

				filtrarEntregaDocumentoCobrancaActionForm.setDescricaoSetorComercialFinal(setorComercial.getDescricao());

			}
		}

	}

	// Preenche o campo rota inicial e final
	private void preencherQuadra(HttpServletRequest httpServletRequest,
					FiltrarEntregaDocumentoCobrancaActionForm filtrarEntregaDocumentoCobrancaActionForm, Fachada fachada){

		if(filtrarEntregaDocumentoCobrancaActionForm.getQuadraInicial() != null
						&& !filtrarEntregaDocumentoCobrancaActionForm.getQuadraInicial().trim().equalsIgnoreCase("")){

			FiltroQuadra filtroQuadra = new FiltroQuadra();

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, filtrarEntregaDocumentoCobrancaActionForm
							.getQuadraInicial()));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

				httpServletRequest.setAttribute("idQuadraInicialNaoEncontrado", "exception");

				filtrarEntregaDocumentoCobrancaActionForm.setQuadraInicial("");

				httpServletRequest.setAttribute("nomeCampo", "quadraInicial");

			}else{

				// Preenche Quadra Inicial
				Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);
				filtrarEntregaDocumentoCobrancaActionForm.setQuadraInicial(String.valueOf(quadra.getId().toString()));

				if(filtrarEntregaDocumentoCobrancaActionForm.getQuadraFinal() == null
								|| filtrarEntregaDocumentoCobrancaActionForm.getQuadraFinal().equals("")){
					// Preenche Quadra Final
					filtrarEntregaDocumentoCobrancaActionForm.setQuadraFinal(String.valueOf(quadra.getId().toString()));
				}

			}
		}

		if(filtrarEntregaDocumentoCobrancaActionForm.getQuadraFinal() != null
						&& !filtrarEntregaDocumentoCobrancaActionForm.getQuadraFinal().trim().equalsIgnoreCase("")){

			FiltroQuadra filtroQuadra = new FiltroQuadra();

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, filtrarEntregaDocumentoCobrancaActionForm
							.getQuadraFinal()));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

				httpServletRequest.setAttribute("idQuadraFinalNaoEncontrado", "exception");

				filtrarEntregaDocumentoCobrancaActionForm.setQuadraFinal("");

				httpServletRequest.setAttribute("nomeCampo", "quadraInicial");

			}else{
				Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				filtrarEntregaDocumentoCobrancaActionForm.setQuadraFinal(String.valueOf(quadra.getId().toString()));

			}
		}

	}

	// Preenche o campo comando
	private void preencherComando(HttpServletRequest httpServletRequest,
					FiltrarEntregaDocumentoCobrancaActionForm filtrarEntregaDocumentoCobrancaActionForm, Fachada fachada){

		if(filtrarEntregaDocumentoCobrancaActionForm.getCobrancaAcaoAtividadeComando() != null
						&& !filtrarEntregaDocumentoCobrancaActionForm.getCobrancaAcaoAtividadeComando().trim().equalsIgnoreCase("")){

			FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando = new FiltroCobrancaAcaoAtividadeComando();

			filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeComando.ID,
							filtrarEntregaDocumentoCobrancaActionForm.getCobrancaAcaoAtividadeComando()));

			Collection colecaoCobrancaAcaoAtividadeComando = fachada.pesquisar(filtroCobrancaAcaoAtividadeComando,
							CobrancaAcaoAtividadeComando.class.getName());

			if(colecaoCobrancaAcaoAtividadeComando == null || colecaoCobrancaAcaoAtividadeComando.isEmpty()){

				httpServletRequest.setAttribute("idCobrancaAcaoAtividadeComandoNaoEncontrado", "exception");

				filtrarEntregaDocumentoCobrancaActionForm.setCobrancaAcaoAtividadeComando("");

				filtrarEntregaDocumentoCobrancaActionForm.setCobrancaAcaoAtividadeComandoDescricao("COMANDO INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "cobrancaAcaoAtividadeComando");

			}else{
				CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = (CobrancaAcaoAtividadeComando) Util
								.retonarObjetoDeColecao(colecaoCobrancaAcaoAtividadeComando);

				filtrarEntregaDocumentoCobrancaActionForm.setCobrancaAcaoAtividadeComando(String.valueOf(cobrancaAcaoAtividadeComando
								.getId().toString()));

				filtrarEntregaDocumentoCobrancaActionForm.setCobrancaAcaoAtividadeComandoDescricao(cobrancaAcaoAtividadeComando
								.getDescricaoTitulo());

			}
		}
	}

	// Preenche o campo imóvel
	private void preencherImovel(HttpServletRequest httpServletRequest,
					FiltrarEntregaDocumentoCobrancaActionForm filtrarEntregaDocumentoCobrancaActionForm, Fachada fachada){

		if(filtrarEntregaDocumentoCobrancaActionForm.getImovel() != null
						&& !filtrarEntregaDocumentoCobrancaActionForm.getImovel().trim().equalsIgnoreCase("")){

			FiltroImovel filtroImovel = new FiltroImovel();

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, filtrarEntregaDocumentoCobrancaActionForm.getImovel()));

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel == null || colecaoImovel.isEmpty()){

				httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");

				filtrarEntregaDocumentoCobrancaActionForm.setImovel("");

				filtrarEntregaDocumentoCobrancaActionForm.setInscricaoImovel("IMÓVEL INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "imovel");

			}else{
				Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

				filtrarEntregaDocumentoCobrancaActionForm.setImovel(String.valueOf(imovel.getId().toString()));

				filtrarEntregaDocumentoCobrancaActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());

			}
		}
	}

	// Preenche o campo Ação de Cobrança
	private void preencherCobrancaAcao(Fachada fachada, HttpSession sessao){

		// retorna os valores do parâmetro P_COBRANCA_ACAO_COM_ENTREGA_DOCUMENTO
		String[] idsCobrancaAcaoComEntregaDocumento = null;
		try{
			idsCobrancaAcaoComEntregaDocumento = ((String) ParametroCobranca.P_COBRANCA_ACAO_COM_ENTREGA_DOCUMENTO
							.executar(ExecutorParametrosCobranca.getInstancia())).split(",");
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collection<Integer> colecaoIds = new ArrayList<Integer>();
		if(idsCobrancaAcaoComEntregaDocumento != null){
			for(int i = 0; i < idsCobrancaAcaoComEntregaDocumento.length; i++){
				colecaoIds.add(Integer.valueOf(idsCobrancaAcaoComEntregaDocumento[i]));
			}
		}

		// Preenche o campo Ação de Cobrança
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();

		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
		filtroCobrancaAcao
						.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoCobrancaAcao = fachada.pesquisar(colecaoIds, filtroCobrancaAcao, CobrancaAcao.class.getName());
		sessao.setAttribute("collCobrancaAcao", colecaoCobrancaAcao);
	}

}
