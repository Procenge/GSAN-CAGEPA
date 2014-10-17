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

package gcom.gui.cadastro.localidade;

import gcom.cadastro.dadocensitario.FiltroIbgeSetorCensitario;
import gcom.cadastro.dadocensitario.IbgeSetorCensitario;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.operacional.*;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Administrador, R�mulo Aur�lio
 * @date 08/07/2006
 */

public class ExibirInserirQuadraAction
				extends GcomAction {

	private String localidadeID;

	private Collection colecaoPesquisa;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descri��o sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descri��o sobre o fluxo secund�rio>
	 * <Identificador e nome do fluxo secund�rio>
	 * 
	 * @author Administrador, R�mulo Aur�lio
	 * @date 08/07/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirQuadra");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		InserirQuadraActionForm inserirQuadraActionForm = (InserirQuadraActionForm) actionForm;
		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")){

			localidadeID = inserirQuadraActionForm.getLocalidadeID();

			switch(Integer.parseInt(objetoConsulta)){

				// Localidade
				case 1:
					pesquisarLocalidade(inserirQuadraActionForm, fachada, httpServletRequest);
					break;

				// Setor Comercial
				case 2:
					pesquisarLocalidade(inserirQuadraActionForm, fachada, httpServletRequest);
					pesquisarSetorComercial(inserirQuadraActionForm, fachada, httpServletRequest);
					break;

				// Zona Abastecimento
				case 5:
					// Recebe o valor do campo distritoOperacionalID do formul�rio.
					String distritoOperacionalID = inserirQuadraActionForm.getDistritoOperacionalID();

					if(distritoOperacionalID != null
									&& !distritoOperacionalID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();

						filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL_ID,
										distritoOperacionalID));

						filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna uma cole��o de bacias
						colecaoPesquisa = fachada.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

						sessao.setAttribute("colecaoZonaAbastecimento", colecaoPesquisa);

					}else{
						inserirQuadraActionForm.setZonaAbastecimentoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.setAttribute("colecaoZonaAbastecimento", new ArrayList());
					}
					break;

				// Setor censit�rio
				case 6:

					// Recebe o valor do campo setorCensitarioID do formul�rio.
					String setorCensitarioID = inserirQuadraActionForm.getSetorCensitarioID();
					FiltroIbgeSetorCensitario filtroIbgeSetorCensitario = new FiltroIbgeSetorCensitario();
					filtroIbgeSetorCensitario.adicionarParametro(new ParametroSimples(FiltroIbgeSetorCensitario.ID, setorCensitarioID));
					filtroIbgeSetorCensitario.adicionarParametro(new ParametroSimples(FiltroIbgeSetorCensitario.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna Setor censitario
					colecaoPesquisa = fachada.pesquisar(filtroIbgeSetorCensitario, IbgeSetorCensitario.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						// Setor censitario nao encontrado
						// Limpa o campo setorCensitarioID do formul�rio
						inserirQuadraActionForm.setSetorCensitarioID("");
						inserirQuadraActionForm.setSetorCensitarioDescricao("Setor censit�rio inexistente.");
						httpServletRequest.setAttribute("corSetorCensitario", "exception");

						httpServletRequest.setAttribute("nomeCampo", "setorCensitarioID");
					}else{
						IbgeSetorCensitario objetoIbgeSetorCensitario = (IbgeSetorCensitario) Util.retonarObjetoDeColecao(colecaoPesquisa);
						inserirQuadraActionForm.setSetorCensitarioID(String.valueOf(objetoIbgeSetorCensitario.getId()));
						inserirQuadraActionForm.setSetorCensitarioDescricao(objetoIbgeSetorCensitario.getDescricao());
						httpServletRequest.setAttribute("corSetorCensitario", "valor");

						httpServletRequest.setAttribute("nomeCampo", "zeisID");
					}
					break;

				// SubSistemaEsgoto
				case 7:
					if(!Util.isVazioOuBranco(localidadeID)){
						// Recebe o valor do campo sistemaEsgotoID do formul�rio.
						String sistemaEsgotoID = inserirQuadraActionForm.getSistemaEsgotoID();

						if(sistemaEsgotoID != null
										&& !sistemaEsgotoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
							FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
							filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID,
											sistemaEsgotoID));
							filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));

							// Retorna uma cole��o de subsistemaesgoto
							colecaoPesquisa = fachada.pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

							sessao.setAttribute("colecaoSubSistemaEsgoto", colecaoPesquisa);

						}else{
							inserirQuadraActionForm.setSubSistemaEsgotoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
							sessao.removeAttribute("colecaoSubSistemaEsgoto");
						}

						inserirQuadraActionForm.setBaciaID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.removeAttribute("colecaoBacia");
					}else{
						// Limpa Sistema Esgoto
						inserirQuadraActionForm.setSistemaEsgotoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.removeAttribute("colecaoSistemaEsgoto");

						// Limpa Sub-Sistema Esgoto
						inserirQuadraActionForm.setSubSistemaEsgotoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.removeAttribute("colecaoSubSistemaEsgoto");

						// Limpa Bacia
						inserirQuadraActionForm.setBaciaID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.removeAttribute("colecaoBacia");
					}

					break;

				// Rota
				case 8:

					if(!Util.isVazioOuBranco(inserirQuadraActionForm.getSetorComercialID())){

						String codigoRota = inserirQuadraActionForm.getRotaID();

						FiltroRota filtroRota = new FiltroRota();
						filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.LEITURA_TIPO);
						filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, inserirQuadraActionForm
										.getLocalidadeID()));
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, inserirQuadraActionForm
										.getSetorComercialCD()));
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, codigoRota));
						filtroRota
										.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO,
														ConstantesSistema.INDICADOR_USO_ATIVO));

						colecaoPesquisa = fachada.pesquisar(filtroRota, Rota.class.getName());
					}else{

						throw new ActionServletException("atencao.setor_comercial_nao_informado");
					}

					if(Util.isVazioOrNulo(colecaoPesquisa)){

						// Rota nao encontrada
						// Limpa o campo rotaID do formul�rio
						inserirQuadraActionForm.setRotaID("");
						inserirQuadraActionForm.setRotaDescricao("Rota inexistente.");
						httpServletRequest.setAttribute("corRotaMensagem", "exception");
						httpServletRequest.setAttribute("nomeCampo", "rotaID");
					}else{

						Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisa);
						inserirQuadraActionForm.setRotaID(String.valueOf(objetoRota.getCodigo()));
						inserirQuadraActionForm.setRotaDescricao(objetoRota.getDescricao());
						httpServletRequest.setAttribute("corRotaMensagem", "valor");
						httpServletRequest.setAttribute("nomeCampo", "rotaID");
					}

					break;
				// Distrito Operacional
				case 9:
					// Recebe o valor do campo sistemaAbastecimentoID do formul�rio.
					String sistemaAbastecimentoID = inserirQuadraActionForm.getSistemaAbastecimentoID();

					if(sistemaAbastecimentoID != null
									&& !sistemaAbastecimentoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

						filtroDistritoOperacional.adicionarParametro(new ParametroSimples(
										FiltroDistritoOperacional.SISTEMA_ABASTECIMENTO_ID, sistemaAbastecimentoID));

						filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna uma cole��o de bacias
						colecaoPesquisa = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

						sessao.setAttribute("colecaoDistritoOperacional", colecaoPesquisa);

					}else{
						inserirQuadraActionForm.setDistritoOperacionalID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.setAttribute("colecaoDistritoOperacional", new ArrayList());
					}

					break;
				// Bacia
				case 10:

					// Recebe o valor do campo subSistemaEsgotoID do formul�rio.
					String subSistemaEsgotoID = inserirQuadraActionForm.getSubSistemaEsgotoID();

					if(subSistemaEsgotoID != null
									&& !subSistemaEsgotoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
						FiltroBacia filtroBacia = new FiltroBacia();
						filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_ID, subSistemaEsgotoID));
						filtroBacia
										.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO,
														ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna uma cole��o de bacias
						colecaoPesquisa = fachada.pesquisar(filtroBacia, Bacia.class.getName());

						sessao.setAttribute("colecaoBacia", colecaoPesquisa);

					}else{
						inserirQuadraActionForm.setBaciaID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.removeAttribute("colecaoBacia");
					}

					break;

				case 11:
					// Limpa Sistema Esgoto
					inserirQuadraActionForm.setSistemaEsgotoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
					sessao.removeAttribute("colecaoSistemaEsgoto");

					// Limpa Sub-Sistema Esgoto
					inserirQuadraActionForm.setSubSistemaEsgotoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
					sessao.removeAttribute("colecaoSubSistemaEsgoto");

					// Limpa Bacia
					inserirQuadraActionForm.setBaciaID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
					sessao.removeAttribute("colecaoBacia");

					if(!Util.isVazioOuBranco(localidadeID)){
						this.pesquisarLocalidade(inserirQuadraActionForm, fachada, httpServletRequest);
					}

					break;

				default:
					break;
			}

		}else{

			// Carregamento inicial do formul�rio.

			// Quadra_Perfil
			FiltroQuadraPerfil filtroQuadraPerfil = new FiltroQuadraPerfil();
			filtroQuadraPerfil.adicionarParametro(new ParametroSimples(FiltroQuadraPerfil.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Quadra_Perfil
			colecaoPesquisa = fachada.pesquisar(filtroQuadraPerfil, QuadraPerfil.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Nenhum registro na tabela Quadra_Perfil foi encontrado
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Quadra_Perfil");
			}else{
				sessao.setAttribute("colecaoPerfilQuadra", colecaoPesquisa);
			}

			// Area_Tipo
			FiltroAreaTipo filtroAreaTipo = new FiltroAreaTipo();
			filtroAreaTipo.adicionarParametro(new ParametroSimples(FiltroAreaTipo.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Area_Tipo
			colecaoPesquisa = fachada.pesquisar(filtroAreaTipo, AreaTipo.class.getName());

			// if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
			// //Nenhum registro na tabela Area_Tipo foi encontrado
			// throw new ActionServletException(
			// "atencao.pesquisa.nenhum_registro_tabela", null,
			// "Area_Tipo");
			// } else {
			sessao.setAttribute("colecaoAreaTipo", colecaoPesquisa);
			// }

			// Sistema_Abastecimento
			FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
			filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Sistema_Abastecimento
			colecaoPesquisa = fachada.pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Nenhum registro na tabela Sistema_Esgoto foi encontrado
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Sistema_Abastecimento");
			}else{
				sessao.setAttribute("colecaoSistemaAbastecimento", colecaoPesquisa);
			}

			// ZEIS
			FiltroZeis filtroZeis = new FiltroZeis();
			filtroZeis.adicionarParametro(new ParametroSimples(FiltroZeis.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna ZEIS
			colecaoPesquisa = fachada.pesquisar(filtroZeis, Zeis.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Nenhum registro na tabela ZEIS foi encontrado
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "ZEIS");
			}else{
				sessao.setAttribute("colecaoZeis", colecaoPesquisa);
			}

			sessao.setAttribute("colecaoDistritoOperacional", new ArrayList());
			sessao.setAttribute("colecaoZonaAbastecimento", new ArrayList());

		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){
			// -------------- bt DESFAZER ---------------
			inserirQuadraActionForm.setIndicadorRedeAguaAux("");
			inserirQuadraActionForm.setIndicadorRedeEsgotoAux("");

			inserirQuadraActionForm.setBaciaID("");
			inserirQuadraActionForm.setDistritoOperacionalID("");
			inserirQuadraActionForm.setIndicadorUso("");
			inserirQuadraActionForm.setLocalidadeID("");
			inserirQuadraActionForm.setLocalidadeNome("");
			inserirQuadraActionForm.setPerfilQuadra("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirQuadraActionForm.setQuadraID("");
			inserirQuadraActionForm.setQuadraNM("");
			inserirQuadraActionForm.setRotaID("");
			inserirQuadraActionForm.setRotaDescricao("");
			inserirQuadraActionForm.setSetorCensitarioDescricao("");
			inserirQuadraActionForm.setSetorCensitarioID("");
			inserirQuadraActionForm.setSetorComercialCD("");
			inserirQuadraActionForm.setSetorComercialID("");
			inserirQuadraActionForm.setSetorComercialNome("");
			inserirQuadraActionForm.setSistemaEsgotoID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirQuadraActionForm.setZeisID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

	private void pesquisarLocalidade(InserirQuadraActionForm inserirQuadraActionForm, Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna localidade
		colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
			// Localidade nao encontrada
			// Limpa o campo localidadeID do formul�rio
			inserirQuadraActionForm.setLocalidadeID("");
			inserirQuadraActionForm.setLocalidadeNome("Localidade inexistente.");
			httpServletRequest.setAttribute("corLocalidade", "exception");

			httpServletRequest.setAttribute("nomeCampo", "localidadeID");

			// Limpa Sistema Esgoto
			httpServletRequest.getSession(false).removeAttribute("colecaoSistemaEsgoto");
		}else{

			Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
			inserirQuadraActionForm.setLocalidadeID(String.valueOf(objetoLocalidade.getId()));
			inserirQuadraActionForm.setLocalidadeNome(objetoLocalidade.getDescricao());
			httpServletRequest.setAttribute("corLocalidade", "valor");
			httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");

			// Sistema Esgoto
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

			GerenciaRegional gerenciaRegional = localidade.getGerenciaRegional();

			if(gerenciaRegional != null){
				Integer idGerenciaRegional = gerenciaRegional.getId();

				FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();
				filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.GERENCIA_REGIONAL_ID, idGerenciaRegional));
				filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna Sistema_Esgoto
				Collection<SistemaEsgoto> colecaoSistemaEsgoto = fachada.pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());
				httpServletRequest.getSession(false).setAttribute("colecaoSistemaEsgoto", colecaoSistemaEsgoto);
			}else{
				// Limpa Sistema Esgoto
				httpServletRequest.getSession(false).removeAttribute("colecaoSistemaEsgoto");
			}
		}

		// Limpa Sistema Esgoto
		inserirQuadraActionForm.setSistemaEsgotoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));

		// Limpa Sub-Sistema Esgoto
		inserirQuadraActionForm.setSubSistemaEsgotoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
		httpServletRequest.getSession(false).removeAttribute("colecaoSubSistemaEsgoto");

		// Limpa Bacia
		inserirQuadraActionForm.setBaciaID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
		httpServletRequest.getSession(false).removeAttribute("colecaoBacia");
	}

	private void pesquisarSetorComercial(InserirQuadraActionForm inserirQuadraActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		if(localidadeID == null || localidadeID.trim().equalsIgnoreCase("")){

			// Limpa os campos setorComercialCD e setorComercialID do formulario
			inserirQuadraActionForm.setSetorComercialCD("");
			inserirQuadraActionForm.setSetorComercialID("");
			inserirQuadraActionForm.setSetorComercialNome("Informe a localidade.");
			httpServletRequest.setAttribute("corSetorComercial", "exception");
			httpServletRequest.setAttribute("nomeCampo", "localidadeID");

		}else{

			// Recebe o valor do campo setorComercialCD do formul�rio.
			String setorComercialCD = inserirQuadraActionForm.getSetorComercialCD();

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna setorComercial
			colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){

				// setorComercial nao encontrado
				// Limpa os campos setorComercialCD e setorComercialID do formulario
				inserirQuadraActionForm.setSetorComercialCD("");
				inserirQuadraActionForm.setSetorComercialID("");
				inserirQuadraActionForm.setSetorComercialNome("Setor comercial inexistente.");
				httpServletRequest.setAttribute("corSetorComercial", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");

				inserirQuadraActionForm.setBairroID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
				httpServletRequest.getSession(false).removeAttribute("colecaoBairro");

			}else{
				SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				inserirQuadraActionForm.setSetorComercialCD(String.valueOf(objetoSetorComercial.getCodigo()));
				inserirQuadraActionForm.setSetorComercialID(String.valueOf(objetoSetorComercial.getId()));
				inserirQuadraActionForm.setSetorComercialNome(objetoSetorComercial.getDescricao());
				httpServletRequest.setAttribute("corSetorComercial", "valor");

				httpServletRequest.setAttribute("nomeCampo", "quadraNM");

				// Pesquisar bairros ligados ao setor comercial.
				if(objetoSetorComercial != null){
					FiltroBairro filtroBairro = new FiltroBairro();
					filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, objetoSetorComercial.getMunicipio()
									.getId()));
					filtroBairro
									.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO,
													ConstantesSistema.INDICADOR_USO_ATIVO));
					colecaoPesquisa = fachada.pesquisar(filtroBairro, Bairro.class.getName());
					httpServletRequest.getSession(false).setAttribute("colecaoBairro", colecaoPesquisa);
				}
			}

		}

	}

}
