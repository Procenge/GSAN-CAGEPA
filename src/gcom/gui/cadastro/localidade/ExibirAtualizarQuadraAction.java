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

public class ExibirAtualizarQuadraAction
				extends GcomAction {

	private String localidadeID;

	private Collection colecaoPesquisa;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("atualizarQuadra");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarQuadraActionForm atualizarQuadraActionForm = (AtualizarQuadraActionForm) actionForm;

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		String quadraID = null;
		if((objetoConsulta == null || objetoConsulta.equalsIgnoreCase("")) && (httpServletRequest.getParameter("desfazer") == null)){

			// Recupera o id da Quadra que vai ser atualizada
			if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
				quadraID = httpServletRequest.getParameter("idRegistroInseridoAtualizar");
				// Definindo a volta do botão Voltar p Filtrar Quadra
				sessao.setAttribute("voltar", "filtrar");
				sessao.setAttribute("idRegistroAtualizar", quadraID);
				sessao.setAttribute("indicadorUso", "3");
			}else if(httpServletRequest.getParameter("idRegistroAtualizar") == null){
				quadraID = (String) sessao.getAttribute("idRegistroAtualizar");
				// Definindo a volta do botão Voltar p Filtrar Quadra
				sessao.setAttribute("voltar", "filtrar");
			}else if(httpServletRequest.getParameter("idRegistroAtualizar") != null){
				quadraID = httpServletRequest.getParameter("idRegistroAtualizar");
				// Definindo a volta do botão Voltar p Manter Quadra
				sessao.setAttribute("voltar", "manter");
				sessao.setAttribute("idRegistroAtualizar", quadraID);
			}

		}else{
			quadraID = (String) sessao.getAttribute("idRegistroAtualizar");
		}

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")){

			// Recebe o valor do campo localidadeID do formulário.
			localidadeID = atualizarQuadraActionForm.getLocalidadeID();

			switch(Integer.parseInt(objetoConsulta)){

				// Localidade
				case 1:

					pesquisarLocalidade(atualizarQuadraActionForm, fachada, httpServletRequest);
					break;

				// Setor Comercial
				case 2:

					pesquisarLocalidade(atualizarQuadraActionForm, fachada, httpServletRequest);
					pesquisarSetorComercial(atualizarQuadraActionForm, fachada, httpServletRequest);
					break;

				// Zona Abastecimento
				case 5:
					// Recebe o valor do campo distritoOperacionalID do formulário.
					String distritoOperacionalID = atualizarQuadraActionForm.getDistritoOperacionalID();

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
						atualizarQuadraActionForm.setZonaAbastecimentoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.setAttribute("colecaoZonaAbastecimento", new ArrayList());
					}

					break;

				// Setor censitário
				case 6:

					// Recebe o valor do campo setorCensitarioID do formulário.
					String setorCensitarioID = atualizarQuadraActionForm.getSetorCensitarioID();
					FiltroIbgeSetorCensitario filtroIbgeSetorCensitario = new FiltroIbgeSetorCensitario();
					filtroIbgeSetorCensitario.adicionarParametro(new ParametroSimples(FiltroIbgeSetorCensitario.ID, setorCensitarioID));
					filtroIbgeSetorCensitario.adicionarParametro(new ParametroSimples(FiltroIbgeSetorCensitario.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna Setor censitario
					colecaoPesquisa = fachada.pesquisar(filtroIbgeSetorCensitario, IbgeSetorCensitario.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						// Setor censitario nao encontrado
						// Limpa o campo setorCensitarioID do formulário
						atualizarQuadraActionForm.setSetorCensitarioID("");
						atualizarQuadraActionForm.setSetorCensitarioDescricao("Setor censitário inexistente.");
						httpServletRequest.setAttribute("corSetorCensitario", "exception");

						httpServletRequest.setAttribute("nomeCampo", "setorCensitarioID");
					}else{
						IbgeSetorCensitario objetoIbgeSetorCensitario = (IbgeSetorCensitario) Util.retonarObjetoDeColecao(colecaoPesquisa);
						atualizarQuadraActionForm.setSetorCensitarioID(String.valueOf(objetoIbgeSetorCensitario.getId()));
						atualizarQuadraActionForm.setSetorCensitarioDescricao(objetoIbgeSetorCensitario.getDescricao());

						httpServletRequest.setAttribute("corSetorCensitario", "valor");
						httpServletRequest.setAttribute("nomeCampo", "zeisID");
					}

					break;

				// SubSistemaEsgoto
				case 7:
					// Recebe o valor do campo sistemaEsgotoID do formulário.
					String sistemaEsgotoID = atualizarQuadraActionForm.getSistemaEsgotoID();

					if(sistemaEsgotoID != null && !sistemaEsgotoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
						FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
						filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID,
										sistemaEsgotoID));
						filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna uma coleção de subsistemaesgoto
						colecaoPesquisa = fachada.pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

						sessao.setAttribute("colecaoSubSistemaEsgoto", colecaoPesquisa);

					}else{
						atualizarQuadraActionForm.setSubSistemaEsgotoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.removeAttribute("colecaoSubSistemaEsgoto");
					}

					atualizarQuadraActionForm.setBaciaID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
					sessao.removeAttribute("colecaoBacia");

					break;

				// Rota
				case 8:

					if(!Util.isVazioOuBranco(atualizarQuadraActionForm.getSetorComercialID())){

						String codigoRota = atualizarQuadraActionForm.getRotaID();
						String codigoSetorComercial = atualizarQuadraActionForm.getSetorComercialID();

						FiltroRota filtroRota = new FiltroRota();
						filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.LEITURA_TIPO);
						filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, atualizarQuadraActionForm
										.getLocalidadeID()));
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, codigoSetorComercial));
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, codigoRota));
						filtroRota
										.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO,
														ConstantesSistema.INDICADOR_USO_ATIVO));
						filtroRota.setCampoOrderBy(FiltroRota.CODIGO_ROTA);

						colecaoPesquisa = fachada.pesquisar(filtroRota, Rota.class.getName());
					}else{

						throw new ActionServletException("atencao.setor_comercial_nao_informado");
					}

					if(Util.isVazioOrNulo(colecaoPesquisa)){

						// Rota nao encontrada
						atualizarQuadraActionForm.setRotaID("");
						atualizarQuadraActionForm.setRotaDescricao("Rota inexistente.");
						httpServletRequest.setAttribute("corRotaMensagem", "exception");
						httpServletRequest.setAttribute("nomeCampo", "rotaID");
					}else{

						Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisa);
						atualizarQuadraActionForm.setRotaID(String.valueOf(objetoRota.getCodigo()));
						atualizarQuadraActionForm.setRotaDescricao(objetoRota.getDescricao());
						httpServletRequest.setAttribute("corRotaMensagem", "valor");
						httpServletRequest.setAttribute("nomeCampo", "indicadorUso");
					}

					break;

				// Distrito Operacional
				case 9:
					// Recebe o valor do campo sistemaAbastecimentoID do formulário.
					String sistemaAbastecimentoID = atualizarQuadraActionForm.getSistemaAbastecimentoID();

					if(sistemaAbastecimentoID != null
									&& !sistemaAbastecimentoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

						filtroDistritoOperacional.adicionarParametro(new ParametroSimples(
										FiltroDistritoOperacional.SISTEMA_ABASTECIMENTO_ID, sistemaAbastecimentoID));

						filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						filtroDistritoOperacional.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);

						// Retorna uma coleção de bacias
						colecaoPesquisa = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

						sessao.setAttribute("colecaoDistritoOperacional", colecaoPesquisa);

					}else{
						atualizarQuadraActionForm.setDistritoOperacionalID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.setAttribute("colecaoDistritoOperacional", new ArrayList());
					}

					break;
				// Bacia
				case 10:

					// Recebe o valor do campo subSistemaEsgotoID do formulário.
					String subSistemaEsgotoID = atualizarQuadraActionForm.getSubSistemaEsgotoID();

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
						atualizarQuadraActionForm.setBaciaID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
						sessao.removeAttribute("colecaoBacia");
					}
				default:

					break;
			}

		}else if(httpServletRequest.getParameter("desfazer") == null){

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

			sessao.setAttribute("colecaoDistritoOperacional", new ArrayList());
			sessao.setAttribute("colecaoZonaAbastecimento", new ArrayList());

			// ZEIS
			FiltroZeis filtroZeis = new FiltroZeis();
			filtroZeis.adicionarParametro(new ParametroSimples(FiltroZeis.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna ZEIS
			colecaoPesquisa = fachada.pesquisar(filtroZeis, Zeis.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Nenhum registro na tabela ZEIS foi encontrado
			}else{
				sessao.setAttribute("colecaoZeis", colecaoPesquisa);
			}

			exibirQuadra(quadraID, atualizarQuadraActionForm, fachada, sessao, httpServletRequest);

		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){
			exibirQuadra(quadraID, atualizarQuadraActionForm, fachada, sessao, httpServletRequest);
		}

		if(httpServletRequest.getAttribute("nomeCampo") == null || httpServletRequest.getAttribute("nomeCampo").toString().equals("")){

			httpServletRequest.setAttribute("nomeCampo", "bairroID");
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

	private void pesquisarLocalidade(AtualizarQuadraActionForm atualizarQuadraActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		if(localidadeID == null || localidadeID.trim().equalsIgnoreCase("")){
			// Limpa os campos localidadeID e setorComercialID do formulario
			atualizarQuadraActionForm.setLocalidadeNome("Informe Localidade");
			httpServletRequest.setAttribute("corLocalidade", "exception");
		}else{

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Localidade nao encontrada
				// Limpa o campo localidadeID do formulário
				atualizarQuadraActionForm.setLocalidadeID("");
				atualizarQuadraActionForm.setLocalidadeNome("Localidade inexistente.");
				httpServletRequest.setAttribute("corLocalidade", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeID");
			}else{
				Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				atualizarQuadraActionForm.setLocalidadeID(String.valueOf(objetoLocalidade.getId()));
				atualizarQuadraActionForm.setLocalidadeNome(objetoLocalidade.getDescricao());
				httpServletRequest.setAttribute("corLocalidade", "valor");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");
			}
		}

	}

	private void pesquisarSetorComercial(AtualizarQuadraActionForm atualizarQuadraActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		if(localidadeID == null || localidadeID.trim().equalsIgnoreCase("")){
			// Limpa os campos setorComercialCD e setorComercialID do formulario
			atualizarQuadraActionForm.setSetorComercialCD("");
			atualizarQuadraActionForm.setSetorComercialID("");
			atualizarQuadraActionForm.setSetorComercialNome("Informe Localidade.");

			httpServletRequest.setAttribute("corSetorComercial", "exception");
			httpServletRequest.setAttribute("nomeCampo", "localidadeID");
		}else{
			// Recebe o valor do campo setorComercialCD do formulário.
			String setorComercialCD = atualizarQuadraActionForm.getSetorComercialCD();

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna setorComercial
			colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// setorComercial nao encontrado
				// Limpa os campos setorComercialCD e setorComercialID do
				// formulario
				atualizarQuadraActionForm.setSetorComercialCD("");
				atualizarQuadraActionForm.setSetorComercialID("");
				atualizarQuadraActionForm.setSetorComercialNome("Setor comercial inexistente.");
				httpServletRequest.setAttribute("corSetorComercial", "exception");

				httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");
			}else{
				SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				atualizarQuadraActionForm.setSetorComercialCD(String.valueOf(objetoSetorComercial.getCodigo()));
				atualizarQuadraActionForm.setSetorComercialID(String.valueOf(objetoSetorComercial.getId()));
				atualizarQuadraActionForm.setSetorComercialNome(objetoSetorComercial.getDescricao());
				httpServletRequest.setAttribute("corSetorComercial", "valor");

				httpServletRequest.setAttribute("nomeCampo", "quadraNM");
			}

		}
	}

	private void exibirQuadra(String quadraID, AtualizarQuadraActionForm atualizarQuadraActionForm, Fachada fachada, HttpSession sessao,
					HttpServletRequest httpServletRequest){

		FiltroQuadra filtroQuadra = new FiltroQuadra();

		// Objetos que serão retornados pelo hibernate
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.SETOR_COMERCIAL_LOCALIDADE);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.QUADRA_PERFIL);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.SISTEMA_ESGOTO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.SISTEMA_ABASTECIMENTO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.IBGE_SETOR_CENSITARIO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ZEIS);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.LEITURA_TIPO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.BAIRRO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ZONA_ABASTECIMENTO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.AREA_TIPO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ROTA_SETOR_COMERCIAL_LOCALIDADE);

		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, quadraID));

		// Retorna quadra escolhida
		colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

		Quadra quadraSelect = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);

		// Coloca o objeto na sessão
		sessao.setAttribute("quadraManter", quadraSelect);

		// Adiciona os valores que estão na base no formulário que será
		// visualizado pelo usuário

		// ID da quadra
		atualizarQuadraActionForm.setQuadraID(String.valueOf(quadraSelect.getId().intValue()));

		// Localidade
		atualizarQuadraActionForm.setLocalidadeID(String.valueOf(quadraSelect.getSetorComercial().getLocalidade().getId()));
		atualizarQuadraActionForm.setLocalidadeNome(quadraSelect.getSetorComercial().getLocalidade().getDescricao());

		// Setor comercial
		atualizarQuadraActionForm.setSetorComercialID(String.valueOf(quadraSelect.getSetorComercial().getId()));
		atualizarQuadraActionForm.setSetorComercialCD(String.valueOf(quadraSelect.getSetorComercial().getCodigo()));
		atualizarQuadraActionForm.setSetorComercialNome(quadraSelect.getSetorComercial().getDescricao());

		// Número da quadra
		atualizarQuadraActionForm.setQuadraNM(String.valueOf(quadraSelect.getNumeroQuadra()));

		// Combo Bairro
		FiltroBairro filtroBairro = new FiltroBairro();
		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, quadraSelect.getSetorComercial().getMunicipio()
						.getId()));
		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna uma coleção de bairros
		colecaoPesquisa = fachada.pesquisar(filtroBairro, Bairro.class.getName());
		sessao.setAttribute("colecaoBairro", colecaoPesquisa);

		// Bairro
		if(quadraSelect.getBairro() != null){
			atualizarQuadraActionForm.setBairroID(String.valueOf(quadraSelect.getBairro().getId()));
		}

		// Perfil da quadra
		if(quadraSelect.getQuadraPerfil() != null && !quadraSelect.getQuadraPerfil().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			atualizarQuadraActionForm.setPerfilQuadra(String.valueOf(quadraSelect.getQuadraPerfil().getId()));
		}

		// Area Tipo/Localização
		if(quadraSelect.getAreaTipo() != null && !quadraSelect.getAreaTipo().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			atualizarQuadraActionForm.setAreaTipoID(String.valueOf(quadraSelect.getAreaTipo().getId()));
		}

		// Indicador de rede de água
		if(quadraSelect.getIndicadorRedeAgua().intValue() != 0){
			atualizarQuadraActionForm.setIndicadorRedeAguaAux(String.valueOf(quadraSelect.getIndicadorRedeAgua()));
		}else{
			// atualizarQuadraActionForm.setIndicadorRedeAguaAux(String
			// .valueOf(Quadra.SEM_REDE));
			atualizarQuadraActionForm.setIndicadorRedeAguaAux("");
		}

		// Indicador de rede de esgoto
		if(quadraSelect.getIndicadorRedeEsgoto().intValue() != 0){
			atualizarQuadraActionForm.setIndicadorRedeEsgotoAux(String.valueOf(quadraSelect.getIndicadorRedeEsgoto()));
		}else{
			// atualizarQuadraActionForm.setIndicadorRedeEsgotoAux(String
			// .valueOf(Quadra.SEM_REDE));
			atualizarQuadraActionForm.setIndicadorRedeEsgotoAux("");
		}

		// Sistema de esgoto
		SetorComercial setorComercial = quadraSelect.getSetorComercial();

		if(setorComercial != null){
			Localidade localidade = setorComercial.getLocalidade();

			if(localidade != null){
				Integer idLocalidade = localidade.getId();

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

				Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(!Util.isVazioOrNulo(colecaoLocalidade)){
					localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

					GerenciaRegional gerenciaRegional = localidade.getGerenciaRegional();

					if(gerenciaRegional != null){
						Integer idGerenciaRegional = gerenciaRegional.getId();

						FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();
						filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.GERENCIA_REGIONAL_ID,
										idGerenciaRegional));
						filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna Sistema_Esgoto
						colecaoPesquisa = fachada.pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

						if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
							// Nenhum registro na tabela Sistema_Esgoto foi encontrado
							throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Sistema_Esgoto");
						}else{
							sessao.setAttribute("colecaoSistemaEsgoto", colecaoPesquisa);
						}
					}
				}
			}
		}

		// Sistema de esgoto
		if(quadraSelect.getBacia() != null && quadraSelect.getBacia().getSubsistemaEsgoto() != null){

			FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
			filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID, quadraSelect.getBacia()
							.getSubsistemaEsgoto().getSistemaEsgoto().getId()));
			filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = fachada.pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

			if(quadraSelect.getBacia().getSubsistemaEsgoto().getSistemaEsgoto() != null){
				atualizarQuadraActionForm.setSistemaEsgotoID(String.valueOf(quadraSelect.getBacia().getSubsistemaEsgoto()
								.getSistemaEsgoto().getId()));
			}

			sessao.setAttribute("colecaoSubSistemaEsgoto", colecaoPesquisa);
		}else if(quadraSelect.getBacia() != null && quadraSelect.getBacia().getSistemaEsgoto() != null){

			FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
			filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID, quadraSelect.getBacia()
							.getSistemaEsgoto().getId()));
			filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = fachada.pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

			if(quadraSelect.getBacia().getSistemaEsgoto() != null){
				atualizarQuadraActionForm.setSistemaEsgotoID(String.valueOf(quadraSelect.getBacia().getSistemaEsgoto().getId()));
			}

			sessao.setAttribute("colecaoSubSistemaEsgoto", colecaoPesquisa);
		}else{
			sessao.removeAttribute("colecaoSubSistemaEsgoto");
		}

		// Bacia e Sub-Sistema de esgoto
		if(quadraSelect.getBacia() != null && quadraSelect.getBacia().getSubsistemaEsgoto() != null){
			atualizarQuadraActionForm.setBaciaID(String.valueOf(quadraSelect.getBacia().getId()));

			if(quadraSelect.getBacia().getSubsistemaEsgoto().getSistemaEsgoto() != null){
				atualizarQuadraActionForm.setSistemaEsgotoID(String.valueOf(quadraSelect.getBacia().getSubsistemaEsgoto()
								.getSistemaEsgoto().getId()));
			}
			atualizarQuadraActionForm.setSubSistemaEsgotoID(String.valueOf(quadraSelect.getBacia().getSubsistemaEsgoto().getId()));
			FiltroBacia filtroBacia = new FiltroBacia();
			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_ID, quadraSelect.getBacia()
							.getSubsistemaEsgoto().getId()));
			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			atualizarQuadraActionForm.setBaciaID(String.valueOf(quadraSelect.getBacia().getId()));

			// Retorna uma coleção de bacias
			colecaoPesquisa = fachada.pesquisar(filtroBacia, Bacia.class.getName());

			sessao.setAttribute("colecaoBacia", colecaoPesquisa);
		}else{
			atualizarQuadraActionForm.setSistemaEsgotoID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			atualizarQuadraActionForm.setSubSistemaEsgotoID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			atualizarQuadraActionForm.setBaciaID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			sessao.removeAttribute("colecaoBacia");
		}

		// Distrito operacional
		if(quadraSelect.getDistritoOperacional() != null){
			atualizarQuadraActionForm.setDistritoOperacionalID(String.valueOf(quadraSelect.getDistritoOperacional().getId()));
			atualizarQuadraActionForm.setDistritoOperacionalIDAnterior(String.valueOf(quadraSelect.getDistritoOperacional().getId()));

			FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL_ID, quadraSelect
							.getDistritoOperacional().getId()));

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = fachada.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

			atualizarQuadraActionForm.setSistemaAbastecimentoID(quadraSelect.getDistritoOperacional().getSistemaAbastecimento().getId()
							+ "");
			atualizarQuadraActionForm.setDistritoOperacionalID(quadraSelect.getDistritoOperacional().getId() + "");
			if(quadraSelect.getZonaAbastecimento() != null){
				atualizarQuadraActionForm.setZonaAbastecimentoID(quadraSelect.getZonaAbastecimento().getId() + "");
			}

			sessao.setAttribute("colecaoZonaAbastecimento", colecaoPesquisa);

		}else{
			atualizarQuadraActionForm.setSistemaAbastecimentoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			atualizarQuadraActionForm.setDistritoOperacionalID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			atualizarQuadraActionForm.setZonaAbastecimentoID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			sessao.setAttribute("colecaoZonaAbastecimento", new ArrayList());
		}

		// Sistema Abastecimento
		if(quadraSelect.getDistritoOperacional() != null && quadraSelect.getDistritoOperacional().getSistemaAbastecimento() != null){

			String sistemaAbastecimentoID = atualizarQuadraActionForm.getSistemaAbastecimentoID();

			if(sistemaAbastecimentoID != null
							&& !sistemaAbastecimentoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

				FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

				filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.SISTEMA_ABASTECIMENTO_ID,
								sistemaAbastecimentoID));

				filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroDistritoOperacional.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);

				colecaoPesquisa = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

				sessao.setAttribute("colecaoDistritoOperacional", colecaoPesquisa);

			}else{
				sessao.setAttribute("colecaoDistritoOperacional", new ArrayList());
			}
		}

		// Zona Abastecimento
		if(quadraSelect.getZonaAbastecimento() != null){
			atualizarQuadraActionForm.setZonaAbastecimentoID(String.valueOf(quadraSelect.getZonaAbastecimento().getId()));
		}

		// Setor censitário
		if(quadraSelect.getIbgeSetorCensitario() != null){
			atualizarQuadraActionForm.setSetorCensitarioID(String.valueOf(quadraSelect.getIbgeSetorCensitario().getId()));
			atualizarQuadraActionForm.setSetorCensitarioDescricao(quadraSelect.getIbgeSetorCensitario().getDescricao());
		}

		// ZEIS
		if(quadraSelect.getZeis() != null && !quadraSelect.getZeis().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			atualizarQuadraActionForm.setZeisID(String.valueOf(quadraSelect.getZeis().getId()));
		}else{
			atualizarQuadraActionForm.setZeisID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		}

		// Rota
		if(quadraSelect.getRota() != null){
			atualizarQuadraActionForm.setRotaID(String.valueOf(quadraSelect.getRota().getCodigo()));
			atualizarQuadraActionForm.setIdRotaAnterior(quadraSelect.getRota().getId().toString());
			atualizarQuadraActionForm.setRotaDescricao(quadraSelect.getRota().getDescricao());
		}

		// Indicador uso
		if(quadraSelect.getIndicadorUso() != null){
			atualizarQuadraActionForm.setIndicadorUso(String.valueOf(quadraSelect.getIndicadorUso()));
		}
	}
}
