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
 * GSANPCG
 * Virgínia Melo
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
 * Descrição da classe
 * 
 * @author Administrador, Rômulo Aurélio
 * @date 08/07/2006
 */

public class ExibirInserirQuadraAction
				extends GcomAction {

	private String localidadeID;

	private Collection colecaoPesquisa;

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Administrador, Rômulo Aurélio
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
					// Recebe o valor do campo distritoOperacionalID do formulário.
					String distritoOperacionalID = inserirQuadraActionForm.getDistritoOperacionalID();

					if(distritoOperacionalID != null
									&& !distritoOperacionalID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();

						filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL_ID,
										distritoOperacionalID));

						filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna uma coleção de bacias
						colecaoPesquisa = fachada.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

						sessao.setAttribute("colecaoZonaAbastecimento", colecaoPesquisa);

					}else{
						inserirQuadraActionForm.setZonaAbastecimentoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.setAttribute("colecaoZonaAbastecimento", new ArrayList());
					}
					break;

				// Setor censitário
				case 6:

					// Recebe o valor do campo setorCensitarioID do formulário.
					String setorCensitarioID = inserirQuadraActionForm.getSetorCensitarioID();
					FiltroIbgeSetorCensitario filtroIbgeSetorCensitario = new FiltroIbgeSetorCensitario();
					filtroIbgeSetorCensitario.adicionarParametro(new ParametroSimples(FiltroIbgeSetorCensitario.ID, setorCensitarioID));
					filtroIbgeSetorCensitario.adicionarParametro(new ParametroSimples(FiltroIbgeSetorCensitario.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna Setor censitario
					colecaoPesquisa = fachada.pesquisar(filtroIbgeSetorCensitario, IbgeSetorCensitario.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						// Setor censitario nao encontrado
						// Limpa o campo setorCensitarioID do formulário
						inserirQuadraActionForm.setSetorCensitarioID("");
						inserirQuadraActionForm.setSetorCensitarioDescricao("Setor censitário inexistente.");
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
						// Recebe o valor do campo sistemaEsgotoID do formulário.
						String sistemaEsgotoID = inserirQuadraActionForm.getSistemaEsgotoID();

						if(sistemaEsgotoID != null
										&& !sistemaEsgotoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
							FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
							filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID,
											sistemaEsgotoID));
							filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));

							// Retorna uma coleção de subsistemaesgoto
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
						// Limpa o campo rotaID do formulário
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
					// Recebe o valor do campo sistemaAbastecimentoID do formulário.
					String sistemaAbastecimentoID = inserirQuadraActionForm.getSistemaAbastecimentoID();

					if(sistemaAbastecimentoID != null
									&& !sistemaAbastecimentoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

						filtroDistritoOperacional.adicionarParametro(new ParametroSimples(
										FiltroDistritoOperacional.SISTEMA_ABASTECIMENTO_ID, sistemaAbastecimentoID));

						filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna uma coleção de bacias
						colecaoPesquisa = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

						sessao.setAttribute("colecaoDistritoOperacional", colecaoPesquisa);

					}else{
						inserirQuadraActionForm.setDistritoOperacionalID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.setAttribute("colecaoDistritoOperacional", new ArrayList());
					}

					break;
				// Bacia
				case 10:

					// Recebe o valor do campo subSistemaEsgotoID do formulário.
					String subSistemaEsgotoID = inserirQuadraActionForm.getSubSistemaEsgotoID();

					if(subSistemaEsgotoID != null
									&& !subSistemaEsgotoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
						FiltroBacia filtroBacia = new FiltroBacia();
						filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_ID, subSistemaEsgotoID));
						filtroBacia
										.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO,
														ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna uma coleção de bacias
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

			// Carregamento inicial do formulário.

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
			// Limpa o campo localidadeID do formulário
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

			// Recebe o valor do campo setorComercialCD do formulário.
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
