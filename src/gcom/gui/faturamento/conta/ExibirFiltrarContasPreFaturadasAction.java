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

package gcom.gui.faturamento.conta;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.ConstantesSistema;
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
 * [UC3037] Filtrar Contas Pr�-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 06/02/2012
 *          Exibir Contas Pr�-Faturadas.
 */
public class ExibirFiltrarContasPreFaturadasAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirFiltrarContasPreFaturadas");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		if(!Util.isVazioOuBranco(sessao.getAttribute("colecaoFaturamentoGrupo"))){
			sessao.removeAttribute("colecaoFaturamentoGrupo");
		}

		// Obt�m Par�metros do resquest

		// ********* Recupera os campos que foram preenchidos no formul�rio
		FiltrarContasPreFaturadasActionForm filtrarContasPreFaturadasActionForm = (FiltrarContasPreFaturadasActionForm) actionForm;

		// Verifica se vai checar ou n�o o ATUALIZAR
		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){

			sessao.setAttribute("indicadorAtualizar", ConstantesSistema.SIM.toString());
		}

		// ********* Per�odo de Refer�ncia Faturamento Conta

		// Data refer�ncia inicial
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getDataReferenciaContaInicial())){
			// Guarda a data inicial de faturamento informada no formul�rio
			filtrarContasPreFaturadasActionForm.setDataReferenciaFaturamentoContaInicial(filtrarContasPreFaturadasActionForm
							.getDataReferenciaContaInicial());
		}

		// Data refer�ncia final
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getDataReferenciaContaFinal())){
			// Guarda a data final de faturamento informada no formul�rio
			filtrarContasPreFaturadasActionForm.setDataReferenciaContaFinal(filtrarContasPreFaturadasActionForm
							.getDataReferenciaContaFinal());
		}

		// ********* Per�odo de Vencimento Conta

		// Data vencimento conta inicial
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getDataVencimentoContaInicial())){
			// Guarda a data inicial de vencimento informada no formul�rio
			filtrarContasPreFaturadasActionForm.setDataVencimentoContaInicial(filtrarContasPreFaturadasActionForm
							.getDataVencimentoContaInicial());
		}

		// Data vencimento conta final
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getDataVencimentoContaFinal())){
			// Guarda a data final de vencimento informado no formul�rio
			filtrarContasPreFaturadasActionForm.setDataVencimentoContaFinal(filtrarContasPreFaturadasActionForm
							.getDataVencimentoContaFinal());
		}

		// ********* Im�vel
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getImovelID())){
			// Guarda o ID do im�vel informado no formul�rio
			filtrarContasPreFaturadasActionForm.setImovelID(filtrarContasPreFaturadasActionForm.getImovelID());
		}

		// ********* Faturamento Grupo
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getFaturamentoGrupoID())){
			// Guarda o ID do Faturamento Grupo informado no formul�rio
			filtrarContasPreFaturadasActionForm.setFaturamentoGrupoID(filtrarContasPreFaturadasActionForm.getFaturamentoGrupoID());
		}

		// Inclui a cole��o de Faturamento Grupo na sess�o.
		if(sessao.getAttribute("colecaoFaturamentoGrupo") == null){
			// Cria��o do filtro para faturamento grupo
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.DESCRICAO);
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

			if(Util.isVazioOrNulo(colecaoFaturamentoGrupo)){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "FATURAMENTO GRUPO");
			}

			sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}

		// ********* Localidade

		// Localidade Origem
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getLocalidadeOrigemID())){
			// Guarda a localidade origem informada no formul�rio
			filtrarContasPreFaturadasActionForm.setLocalidadeOrigemID(filtrarContasPreFaturadasActionForm.getLocalidadeOrigemID());
		}

		// Nome da Localidade Origem
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getNomeLocalidadeOrigem())){
			// Guarda o nome da localidade origem informada no formul�rio
			filtrarContasPreFaturadasActionForm.setNomeLocalidadeOrigem(filtrarContasPreFaturadasActionForm.getNomeLocalidadeOrigem());
		}

		// Localidade Destino
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getLocalidadeDestinoID())){
			// Guarda a localidade destino informada no formul�rio
			filtrarContasPreFaturadasActionForm.setLocalidadeDestinoID(filtrarContasPreFaturadasActionForm.getLocalidadeDestinoID());
		}

		// Nome da Localidade Destino
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getNomeLocalidadeDestino())){
			// Guarda a localidade destino informada no formul�rio
			filtrarContasPreFaturadasActionForm.setNomeLocalidadeDestino(filtrarContasPreFaturadasActionForm.getNomeLocalidadeDestino());
		}

		// ********* Setor Comercial

		// Setor Comercial Origem
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getSetorComercialOrigemCD())){
			// Guarda o setor comercial origem informado no formul�rio
			filtrarContasPreFaturadasActionForm.setSetorComercialOrigemCD(filtrarContasPreFaturadasActionForm.getSetorComercialOrigemCD());
		}

		// Setor Comercial Destino
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getSetorComercialDestinoCD())){
			// Guarda o setor comercial destino informado no formul�rio
			filtrarContasPreFaturadasActionForm
							.setSetorComercialDestinoCD(filtrarContasPreFaturadasActionForm.getSetorComercialDestinoCD());
		}

		// ********* Rota
		// Rota Origem
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getRotaOrigemID())){

			// Guarda a rota origem informada no formul�rio
			filtrarContasPreFaturadasActionForm.setRotaOrigemID(filtrarContasPreFaturadasActionForm.getRotaOrigemID());

			if(filtrarContasPreFaturadasActionForm.getDescricaoRotaOrigem() != null
							&& filtrarContasPreFaturadasActionForm.getDescricaoRotaOrigem().equals("Rota inexistente.")){

				httpServletRequest.setAttribute("corRotaMensagemOrigem", "exception");
			}else{

				httpServletRequest.setAttribute("corRotaMensagemOrigem", "valor");
			}
		}

		// Rota Destino
		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getRotaDestinoID())){

			// Guarda a rota destino informada no formul�rio
			filtrarContasPreFaturadasActionForm.setRotaDestinoID(filtrarContasPreFaturadasActionForm.getRotaDestinoID());

			if(filtrarContasPreFaturadasActionForm.getDescricaoRotaDestino() != null
							&& filtrarContasPreFaturadasActionForm.getDescricaoRotaDestino().equals("Rota inexistente.")){

				httpServletRequest.setAttribute("corRotaMensagemDestino", "exception");
			}else{

				httpServletRequest.setAttribute("corRotaMensagemDestino", "valor");
			}
		}

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		String inscricaoTipo = (String) httpServletRequest.getParameter("inscricaoTipo");

		// Consultas para recarregar p�gina.
		if(!Util.isVazioOuBranco(objetoConsulta)){

			switch(Integer.parseInt(objetoConsulta)){

				// Localidade
				case 1:

					if(!Util.isVazioOuBranco(inscricaoTipo)){
						pesquisarLocalidade(inscricaoTipo, filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);
					}

					break;
				// Setor Comercial
				case 2:
					if(!Util.isVazioOuBranco(inscricaoTipo)){
						pesquisarLocalidade(inscricaoTipo, filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);
						pesquisarSetorComercial(inscricaoTipo, filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);
					}
					break;

				// Im�vel
				case 3:

					pesquisarImovel(inscricaoTipo, filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);

					break;

				// Faturamento Grupo
				case 4:

					pesquisarFaturamentoGrupo(inscricaoTipo, filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);

					break;
				case 5:

					pesquisarSetorComercial(inscricaoTipo, filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);

					Collection<Rota> colecaoPesquisaRotaOrigem = null;

					if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getSetorComercialOrigemID())){

						String codigoRota = filtrarContasPreFaturadasActionForm.getRotaOrigemID();
						String idSetorComercial = filtrarContasPreFaturadasActionForm.getSetorComercialOrigemID();

						FiltroRota filtroRota = new FiltroRota();
						filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.LEITURA_TIPO);
						filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, filtrarContasPreFaturadasActionForm
										.getLocalidadeOrigemID()));
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, idSetorComercial));
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, codigoRota));
						filtroRota
										.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO,
														ConstantesSistema.INDICADOR_USO_ATIVO));

						filtroRota.setCampoOrderBy(FiltroRota.CODIGO_ROTA);

						colecaoPesquisaRotaOrigem = fachada.pesquisar(filtroRota, Rota.class.getName());
					}else{

						throw new ActionServletException("atencao.setor_comercial_nao_informado");
					}

					if(Util.isVazioOrNulo(colecaoPesquisaRotaOrigem)){

						// Rota nao encontrada
						filtrarContasPreFaturadasActionForm.setRotaOrigemID("");
						filtrarContasPreFaturadasActionForm.setDescricaoRotaOrigem("Rota inexistente.");
						httpServletRequest.setAttribute("corRotaMensagemOrigem", "exception");
					}else{

						Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisaRotaOrigem);
						filtrarContasPreFaturadasActionForm.setRotaOrigemID(String.valueOf(objetoRota.getId()));
						filtrarContasPreFaturadasActionForm.setDescricaoRotaOrigem(objetoRota.getDescricao());
						filtrarContasPreFaturadasActionForm.setRotaDestinoID(String.valueOf(objetoRota.getId()));
						filtrarContasPreFaturadasActionForm.setDescricaoRotaDestino(objetoRota.getDescricao());
						httpServletRequest.setAttribute("corRotaMensagemOrigem", "valor");
					}

					break;
				case 6:

					pesquisarSetorComercial(inscricaoTipo, filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);

					Collection<Rota> colecaoPesquisaRotaDestino = null;

					if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getSetorComercialDestinoID())){

						String codigoRota = filtrarContasPreFaturadasActionForm.getRotaDestinoID();
						String idSetorComercial = filtrarContasPreFaturadasActionForm.getSetorComercialDestinoID();

						FiltroRota filtroRota = new FiltroRota();
						filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.LEITURA_TIPO);
						filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, filtrarContasPreFaturadasActionForm
										.getLocalidadeDestinoID()));
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, idSetorComercial));
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, codigoRota));
						filtroRota
										.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO,
														ConstantesSistema.INDICADOR_USO_ATIVO));

						filtroRota.setCampoOrderBy(FiltroRota.CODIGO_ROTA);

						colecaoPesquisaRotaDestino = fachada.pesquisar(filtroRota, Rota.class.getName());
					}else{

						throw new ActionServletException("atencao.setor_comercial_nao_informado");
					}

					if(Util.isVazioOrNulo(colecaoPesquisaRotaDestino)){

						// Rota nao encontrada
						filtrarContasPreFaturadasActionForm.setRotaDestinoID("");
						filtrarContasPreFaturadasActionForm.setDescricaoRotaDestino("Rota inexistente.");
						httpServletRequest.setAttribute("corRotaMensagemDestino", "exception");
					}else{

						Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisaRotaDestino);
						filtrarContasPreFaturadasActionForm.setRotaDestinoID(String.valueOf(objetoRota.getId()));
						filtrarContasPreFaturadasActionForm.setDescricaoRotaDestino(objetoRota.getDescricao());
						httpServletRequest.setAttribute("corRotaMensagemDestino", "valor");
					}

					break;

				default:
					break;
			}
		}

		if(httpServletRequest.getAttribute("nomeCampo") == null || httpServletRequest.getAttribute("nomeCampo").toString().equals("")){

			httpServletRequest.setAttribute("nomeCampo", "dataReferenciaContaInicial");
		}

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("desfazer"))){
			String desfazer = (String) httpServletRequest.getParameter("desfazer");
			httpServletRequest.setAttribute("desfazer", desfazer);
		}

		return retorno;
	}

	/**
	 * Pesquisa a Localidade
	 * 
	 * @param inscricaoTipo
	 * @param imovelOutrosCriteriosActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	private void pesquisarImovel(String inscricaoTipo, FiltrarContasPreFaturadasActionForm filtrarContasPreFaturadasActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest, HttpSession sessao){

		// PESQUISAR IMOVEL
		Imovel imovel = null;

		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getImovelID())){
			imovel = fachada.pesquisarImovel(Integer.valueOf(filtrarContasPreFaturadasActionForm.getImovelID()));
		}

		if(!Util.isVazioOuBranco(imovel)){
			filtrarContasPreFaturadasActionForm.setImovelID(imovel.getId().toString());
			filtrarContasPreFaturadasActionForm.setMatriculaImovel(imovel.getInscricaoFormatada());
		}else{
			// Caso a cole��o n�o tenha retornado objetos
			filtrarContasPreFaturadasActionForm.setMatriculaImovel("MATRICULA INEXISTENTE");
			httpServletRequest.setAttribute("matInexistente", "ok");
			sessao.removeAttribute("imovel");

			filtrarContasPreFaturadasActionForm.setImovelID(null);
		}
	}

	/**
	 * Pesquisa a Localidade
	 * 
	 * @param inscricaoTipo
	 * @param imovelOutrosCriteriosActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	private void pesquisarFaturamentoGrupo(String inscricaoTipo, FiltrarContasPreFaturadasActionForm filtrarContasPreFaturadasActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest, HttpSession sessao){

		// PESQUISAR FATURAMENTO GRUPO
		FaturamentoGrupo faturamentoGrupo = null;

		if(!Util.isVazioOuBranco(filtrarContasPreFaturadasActionForm.getFaturamentoGrupoID())){
			faturamentoGrupo = fachada.pesquisarFaturamentoGrupoPorID(Integer.valueOf(filtrarContasPreFaturadasActionForm
							.getFaturamentoGrupoID()));
		}

		if(!Util.isVazioOuBranco(faturamentoGrupo)){
			filtrarContasPreFaturadasActionForm.setFaturamentoGrupoID(faturamentoGrupo.getId().toString());
		}else{
			sessao.removeAttribute("faturamentoGrupo");

			filtrarContasPreFaturadasActionForm.setFaturamentoGrupoID(null);

			// Caso a cole��o n�o tenha retornado objetos
			throw new ActionServletException("atencao.entidade.inexistente", null, "FATURAMENTO GRUPO");
		}

	}

	/**
	 * Pesquisa a Localidade
	 * 
	 * @param inscricaoTipo
	 * @param imovelOutrosCriteriosActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	private void pesquisarLocalidade(String inscricaoTipo, FiltrarContasPreFaturadasActionForm filtrarContasPreFaturadasActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest, HttpSession sessao){

		// Localidade
		String localidadeID = null;

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			filtrarContasPreFaturadasActionForm.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formul�rio.
			localidadeID = (String) filtrarContasPreFaturadasActionForm.getLocalidadeOrigemID();

			Localidade objetoLocalidade = fachada.obterLocalidadeGerenciaRegional(localidadeID);

			if(objetoLocalidade == null){
				// Localidade nao encontrada
				// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
				// formul�rio
				filtrarContasPreFaturadasActionForm.setLocalidadeOrigemID("");
				filtrarContasPreFaturadasActionForm.setNomeLocalidadeOrigem("Localidade Inexistente");
				httpServletRequest.setAttribute("corLocalidadeOrigem", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeOrigemID");

			}else{
				filtrarContasPreFaturadasActionForm.setLocalidadeOrigemID(String.valueOf(objetoLocalidade.getId()));
				filtrarContasPreFaturadasActionForm.setNomeLocalidadeOrigem(objetoLocalidade.getDescricao());
				httpServletRequest.setAttribute("corLocalidadeOrigem", "valor");

				String localidadeDestinoID = (String) filtrarContasPreFaturadasActionForm.getLocalidadeDestinoID();
				// verifica o valor das localidades, origem e final
				if(localidadeDestinoID != null){

					if(localidadeDestinoID.equals("")){
						filtrarContasPreFaturadasActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
						filtrarContasPreFaturadasActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
					}else{
						int localidadeDestino = Integer.valueOf(localidadeDestinoID).intValue();
						int localidadeOrigem = objetoLocalidade.getId().intValue();
						if(localidadeOrigem > localidadeDestino){
							filtrarContasPreFaturadasActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
							filtrarContasPreFaturadasActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
						}
					}
				}
				httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");

			}
		}else{
			// Recebe o valor do campo localidadeDestinoID do formul�rio.
			localidadeID = (String) filtrarContasPreFaturadasActionForm.getLocalidadeDestinoID();

			Localidade objetoLocalidade = fachada.obterLocalidadeGerenciaRegional(localidadeID);

			filtrarContasPreFaturadasActionForm.setInscricaoTipo("destino");

			if(objetoLocalidade == null){
				// Localidade nao encontrada
				// Limpa os campos localidadeDestinoID e nomeLocalidadeDestino
				// do formul�rio
				filtrarContasPreFaturadasActionForm.setLocalidadeDestinoID("");
				filtrarContasPreFaturadasActionForm.setNomeLocalidadeDestino("Localidade inexistente");
				httpServletRequest.setAttribute("corLocalidadeDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");
			}else{
				int localidadeDestino = objetoLocalidade.getId().intValue();

				String localidade = (String) filtrarContasPreFaturadasActionForm.getLocalidadeOrigemID();

				if(localidade != null && !localidade.equals("")){

					int localidadeOrigem = Integer.valueOf(localidade).intValue();
					if(localidadeDestino < localidadeOrigem){
						filtrarContasPreFaturadasActionForm.setLocalidadeDestinoID("");
						// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
						// .setNomeLocalidadeDestino("Loc. Final maior que a
						// Inicial");
						httpServletRequest.setAttribute("mensagem", "Localidae Final menor que o Inicial");
						filtrarContasPreFaturadasActionForm.setNomeLocalidadeDestino("");
						httpServletRequest.setAttribute("corLocalidadeDestino", "valor");

						httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");

					}else{
						filtrarContasPreFaturadasActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
						filtrarContasPreFaturadasActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
						httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
						httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");

						pesquisarLocalidade("origem", filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);
					}
				}else{
					filtrarContasPreFaturadasActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
					filtrarContasPreFaturadasActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
					httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
					httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");
					pesquisarLocalidade("origem", filtrarContasPreFaturadasActionForm, fachada, httpServletRequest, sessao);

				}
			}
		}

	}

	/**
	 * Pesquisa o Setor Comercial para ser usado no Filtrar Comandos de A��o de
	 * Cobran�a
	 * 
	 * @param inscricaoTipo
	 * @param filtrarComandosAcaoCobrancaEventualActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	private void pesquisarSetorComercial(String inscricaoTipo, FiltrarContasPreFaturadasActionForm filtrarContasPreFaturadasActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest, HttpSession sessao){

		// Localidade
		String localidadeID = null;

		// Setor Comercial
		String setorComercialCD = null;

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			filtrarContasPreFaturadasActionForm.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formul�rio.
			localidadeID = (String) filtrarContasPreFaturadasActionForm.getLocalidadeOrigemID();

			// Recebe o valor do campo localidadeOrigemID do formul�rio.
			setorComercialCD = (String) filtrarContasPreFaturadasActionForm.getSetorComercialOrigemCD();

			// O campo localidadeOrigemID ser� obrigat�rio
			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){

				SetorComercial objetoSetorComercial = fachada.obterSetorComercialLocalidade(localidadeID, setorComercialCD);

				if(objetoSetorComercial == null){
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialOrigemCD,
					// nomeSetorComercialOrigem e setorComercialOrigemID do
					// formul�rio
					filtrarContasPreFaturadasActionForm.setSetorComercialOrigemCD("");
					filtrarContasPreFaturadasActionForm.setSetorComercialOrigemID("");
					filtrarContasPreFaturadasActionForm.setNomeSetorComercialOrigem("Setor Comercial Inexistente");
					httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
					filtrarContasPreFaturadasActionForm.setRotaOrigemID(null);
					filtrarContasPreFaturadasActionForm.setRotaDestinoID(null);

					httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");

				}else{
					// setorComercialOrigem
					filtrarContasPreFaturadasActionForm.setSetorComercialOrigemCD(String.valueOf(objetoSetorComercial.getCodigo()));
					filtrarContasPreFaturadasActionForm.setSetorComercialOrigemID(String.valueOf(objetoSetorComercial.getId()));
					filtrarContasPreFaturadasActionForm.setNomeSetorComercialOrigem(objetoSetorComercial.getDescricao());
					httpServletRequest.setAttribute("corSetorComercialOrigem", "valor");

					String setorComercialDestinoCD = (String) filtrarContasPreFaturadasActionForm.getSetorComercialDestinoCD();

					// verifica o valor dos setores comerciais, origem e final
					if(setorComercialDestinoCD != null){

						if(setorComercialDestinoCD.equals("")){

							// setorComercialDestino
							filtrarContasPreFaturadasActionForm
											.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial.getCodigo()));
							filtrarContasPreFaturadasActionForm.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
							filtrarContasPreFaturadasActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());

						}else{

							int setorDestino = Integer.valueOf(setorComercialDestinoCD).intValue();
							int setorOrigem = objetoSetorComercial.getCodigo();
							if(setorOrigem > setorDestino){

								// setorComercialDestino
								filtrarContasPreFaturadasActionForm.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial
												.getCodigo()));
								filtrarContasPreFaturadasActionForm
												.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
								filtrarContasPreFaturadasActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());

							}
						}
						httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
					}
				}
			}else{
				// Limpa o campo setorComercialOrigemCD do formul�rio
				filtrarContasPreFaturadasActionForm.setSetorComercialOrigemCD("");
				filtrarContasPreFaturadasActionForm.setNomeSetorComercialOrigem("Informe a localidade da inscri��o de origem.");
				httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");
			}
		}else{

			filtrarContasPreFaturadasActionForm.setInscricaoTipo("destino");

			// Recebe o valor do campo localidadeDestinoID do formul�rio.
			localidadeID = (String) filtrarContasPreFaturadasActionForm.getLocalidadeDestinoID();

			// O campo localidadeOrigem ser� obrigat�rio
			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){

				setorComercialCD = (String) filtrarContasPreFaturadasActionForm.getSetorComercialDestinoCD();

				SetorComercial objetoSetorComercial = fachada.obterSetorComercialLocalidade(localidadeID, setorComercialCD);

				if(objetoSetorComercial == null){
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialDestinoCD,
					// nomeSetorComercialDestino e setorComercialDestinoID do
					// formul�rio
					filtrarContasPreFaturadasActionForm.setSetorComercialDestinoCD("");
					filtrarContasPreFaturadasActionForm.setSetorComercialDestinoID("");
					filtrarContasPreFaturadasActionForm.setNomeSetorComercialDestino("Setor Comercial Inexistente");
					httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
					filtrarContasPreFaturadasActionForm.setRotaOrigemID(null);
					filtrarContasPreFaturadasActionForm.setRotaDestinoID(null);
					httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
				}else{
					int setorDestino = objetoSetorComercial.getCodigo();

					String setor = (String) filtrarContasPreFaturadasActionForm.getSetorComercialOrigemCD();

					if(setor != null && !setor.equals("")){

						int setorOrigem = Integer.valueOf(setor).intValue();
						if(setorDestino < setorOrigem){

							filtrarContasPreFaturadasActionForm.setSetorComercialDestinoCD("");
							filtrarContasPreFaturadasActionForm.setSetorComercialDestinoID("");
							httpServletRequest.setAttribute("mensagem", "Setor Comercial Final menor que o Inicial");
							filtrarContasPreFaturadasActionForm.setNomeSetorComercialDestino("");
							httpServletRequest.setAttribute("corSetorComercialDestino", "valor");

							filtrarContasPreFaturadasActionForm.setRotaOrigemID(null);
							filtrarContasPreFaturadasActionForm.setRotaDestinoID(null);
							httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");

						}else{

							// setor comercial destino
							filtrarContasPreFaturadasActionForm
											.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial.getCodigo()));
							filtrarContasPreFaturadasActionForm.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
							filtrarContasPreFaturadasActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());
							httpServletRequest.setAttribute("corSetorComercialDestino", "valor");
							httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
						}
					}else{

						// setor comercial destino
						filtrarContasPreFaturadasActionForm.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial.getCodigo()));
						filtrarContasPreFaturadasActionForm.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
						filtrarContasPreFaturadasActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());
						httpServletRequest.setAttribute("corSetorComercialDestino", "valor");
						httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");

					}
				}
			}else{
				// Limpa o campo setorComercialDestinoCD do formul�rio
				filtrarContasPreFaturadasActionForm.setSetorComercialDestinoCD("");
				filtrarContasPreFaturadasActionForm.setNomeSetorComercialDestino("Informe a localidade da inscri��o de destino.");
				httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");

			}
		}

	}
}