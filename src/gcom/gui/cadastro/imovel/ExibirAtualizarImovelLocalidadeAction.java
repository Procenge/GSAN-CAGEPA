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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.micromedicao.bean.RotaHelper;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class ExibirAtualizarImovelLocalidadeAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		if(sessao.getAttribute("ExibirAbaLocalidade") != null && sessao.getAttribute("ExibirAbaLocalidade").equals("N")){
			retorno = actionMapping.findForward("atualizarImovelLocalidadeEndereco");
		}else{
			retorno = actionMapping.findForward("atualizarImovelLocalidade");
		}

		DynaValidatorForm atualizarImovelLocalidadeActionForm = (DynaValidatorForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Parte que verifica se o campo numero sequencial rota é obrigatório
		// dependendo da empresa
		((DynaValidatorForm) actionForm).set("flagObrigatorioNumeroSequencialRota", fachada.pesquisarParametrosDoSistema()
						.getNomeAbreviadoEmpresa());

		// cria as variaveis
		String idLocalidade = null;
		String codigoSetorComercial = null;
		String numeroQuadra = null;
		String idRota = null;
		String cdRota = null;
		String nnSegmento = null;
		String idDistritoOperacional = null;
		String idSetorComercial = null;

		// monta o campo de descrição da rota

		StringBuffer descricaoRota = new StringBuffer();

		// atribui os valores q vem pelo request as variaveis
		if(atualizarImovelLocalidadeActionForm.get("idLocalidade") != null
						&& !atualizarImovelLocalidadeActionForm.get("idLocalidade").toString().trim().equalsIgnoreCase("")){
			idLocalidade = (String) atualizarImovelLocalidadeActionForm.get("idLocalidade");

		}
		if(atualizarImovelLocalidadeActionForm.get("idSetorComercial") != null
						&& !atualizarImovelLocalidadeActionForm.get("idSetorComercial").toString().trim().equalsIgnoreCase("")){
			codigoSetorComercial = (String) atualizarImovelLocalidadeActionForm.get("idSetorComercial");

		}
		if(atualizarImovelLocalidadeActionForm.get("idQuadra") != null
						&& !atualizarImovelLocalidadeActionForm.get("idQuadra").toString().trim().equalsIgnoreCase("")){
			numeroQuadra = (String) atualizarImovelLocalidadeActionForm.get("idQuadra");
		}
		if(atualizarImovelLocalidadeActionForm.get("cdRota") != null
						&& !atualizarImovelLocalidadeActionForm.get("cdRota").toString().trim().equalsIgnoreCase("")){

			cdRota = (String) atualizarImovelLocalidadeActionForm.get("cdRota");
			idRota = (String) atualizarImovelLocalidadeActionForm.get("idRota");

			if(!fachada.verificarPermissaoEspecial(PermissaoEspecial.INFORMAR_ROTA_NAO_PERTENCENTE_AO_SETOR_COMERCIAL_DO_IMOVEL, usuario)){
				sessao.setAttribute("pesquisaRota", "1");

			}else{
				if(idRota != null && !"".equals(idRota)){
					descricaoRota = new StringBuffer(fachada.obterRota(Util.converterStringParaInteger(idRota)));
					atualizarImovelLocalidadeActionForm.set("descricaoRota", descricaoRota.toString());
				}

			}


		}
		if(atualizarImovelLocalidadeActionForm.get("nnSegmento") != null
						&& !atualizarImovelLocalidadeActionForm.get("nnSegmento").toString().trim().equalsIgnoreCase("")){
			nnSegmento = (String) atualizarImovelLocalidadeActionForm.get("nnSegmento");
		}

		if(atualizarImovelLocalidadeActionForm.get("idDistritoOperacional") != null
						&& !atualizarImovelLocalidadeActionForm.get("idDistritoOperacional").toString().trim().equalsIgnoreCase("")){
			idDistritoOperacional = (String) atualizarImovelLocalidadeActionForm.get("idDistritoOperacional");
		}

		// instancia o filtro imovel
		FiltroQuadra filtroQuadra = new FiltroQuadra();
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		FiltroRota filtroRota = new FiltroRota();

		// cria a colecao para receber a pesquisa
		Collection localidades = new HashSet();
		Collection setorComerciais = new HashSet();
		Collection quadras = new HashSet();
		Collection rotas = new HashSet();
		Collection colecaoDistritoOperacional = new HashSet();

		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
			filtroLocalidade.limparListaParametros();
			// coloca parametro no filtro
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(idLocalidade)));
			// pesquisa
			localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(localidades != null && !localidades.isEmpty()){
				// O cliente foi encontrado
				atualizarImovelLocalidadeActionForm.set("idLocalidade", Util.adicionarZerosEsquedaNumero(3, new Integer(
								((Localidade) ((List) localidades).get(0)).getId().toString()).toString()));
				atualizarImovelLocalidadeActionForm.set("localidadeDescricao", ((Localidade) ((List) localidades).get(0)).getDescricao());

				httpServletRequest.setAttribute("nomeCampo", "idSetorComercial");

			}else{
				httpServletRequest.setAttribute("codigoLocalidadeNaoEncontrada", "true");
				atualizarImovelLocalidadeActionForm.set("localidadeDescricao", "LOCALIDADE INEXISTENTE");

				atualizarImovelLocalidadeActionForm.set("idLocalidade", "");

				atualizarImovelLocalidadeActionForm.set("idSetorComercial", "");
				atualizarImovelLocalidadeActionForm.set("setorComercialDescricao", "");

				atualizarImovelLocalidadeActionForm.set("idQuadra", "");

				httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
			}
		}

		if(codigoSetorComercial != null && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")){
			if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){

				filtroSetorComercial.limparListaParametros();
				// coloca parametro no filtro
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, new Integer(idLocalidade)));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, new Integer(
								codigoSetorComercial)));
				// pesquisa
				setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
				if(setorComerciais != null && !setorComerciais.isEmpty()){
					atualizarImovelLocalidadeActionForm.set("idSetorComercial",

					""
									+ Util.adicionarZerosEsquedaNumero(3, new Integer(((SetorComercial) ((List) setorComerciais).get(0))
													.getCodigo()).toString()));
					atualizarImovelLocalidadeActionForm.set("setorComercialDescricao",

					((SetorComercial) ((List) setorComerciais).get(0)).getDescricao());

					idSetorComercial = ((SetorComercial) ((List) setorComerciais).get(0)).getId().toString();

					httpServletRequest.setAttribute("nomeCampo", "idQuadra");
				}else{
					atualizarImovelLocalidadeActionForm.set("idSetorComercial", "");
					httpServletRequest.setAttribute("codigoSetorComercialNaoEncontrada", "true");
					atualizarImovelLocalidadeActionForm.set("setorComercialDescricao", "SETOR COMERCIAL INEXISTENTE");

					httpServletRequest.setAttribute("nomeCampo", "idSetorComercial");

					atualizarImovelLocalidadeActionForm.set("idQuadra", "");
				}
			}

		}else{
			atualizarImovelLocalidadeActionForm.set("idSetorComercial", "");
			atualizarImovelLocalidadeActionForm.set("setorComercialDescricao", "");
		}

		// ....................................................................................

		String pesquisaQuadra = "0";
		if(httpServletRequest.getParameter("pesquisaQuadra") != null){
			pesquisaQuadra = httpServletRequest.getParameter("pesquisaQuadra");
		}else if(sessao.getAttribute("pesquisaQuadra") != null){
			pesquisaQuadra = (String) sessao.getAttribute("pesquisaQuadra");
		}


		if(pesquisaQuadra.equals("1") || httpServletRequest.getParameter("confirmado") != null){

			sessao.setAttribute("pesquisaQuadra", "0");

			if(numeroQuadra != null && !numeroQuadra.toString().trim().equalsIgnoreCase("")){

				if(codigoSetorComercial != null && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")){

					if(Util.isVazioOuBranco(idLocalidade)){
						throw new ActionServletException("atencao.required", null, "Localidade");
					}

					filtroQuadra.limparListaParametros();

					// Objetos que serão retornados pelo Hibernate
					filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ROTA);
					filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.SETOR_COMERCIAL);
					filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.SETOR_COMERCIAL_LOCALIDADE);
					filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.DISTRITO_OPERACIONAL);

					// coloca parametro no filtro
					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, new Integer(idLocalidade)));
					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, new Integer(
									codigoSetorComercial)));
					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, new Integer(numeroQuadra)));

					// pesquisa
					quadras = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

					if(!Util.isVazioOrNulo(quadras)){

						Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(quadras);
						Rota rota = quadra.getRota();

						SetorComercial setorComercial = quadra.getSetorComercial();
						Localidade localidade = setorComercial.getLocalidade();

						// 1. Caso a mudança da quadra acarrete a mudança da rota (ROTA_ID da tabela
						// IMOVEL é diferente de ROTA_ID da tabela QUADRA com QDRA_ID=Id da Nova
						// Quadra):
						if(!rota.getId().toString().equals(atualizarImovelLocalidadeActionForm.get("idRota"))){
							// 1.1. Caso o usuário não tenha permissão especial para informar rota
							// não pertencente ao
							// setor comercial do imóvel (não existe ocorrência na tabela
							// USUARIO_PERMISSAO_ESPECIAL com
							// USUR_ID=(USUR_ID da tabela USUARIO com USUR_NMLOGIN=Login do usuário)
							// e PMEP_ID=(PMEP_ID
							// da tabela PERMISSAO_ESPECIAL com PMEP_DSPERMISSAOESPECIAL=”INFORMAR
							// ROTA NAO PERTENCENTE
							// AO SETOR COMERCIAL DO IMOVEL”)):

							if(!fachada.verificarPermissaoEspecial(
											PermissaoEspecial.INFORMAR_ROTA_NAO_PERTENCENTE_AO_SETOR_COMERCIAL_DO_IMOVEL, usuario)){
								// 1.1.1. Determinar a rota da quadra – ROTA_CDROTA da tabela ROTA
								// com ROTA_ID=ROTA_ID
								// da
								// tabela QUADRA com QDRA_ID=Id da Nova Quadra;

								atualizarImovelLocalidadeActionForm.set("idQuadra", numeroQuadra);

								String exibirRota;
								try{
									exibirRota = ParametroCadastro.P_EXIBIR_ROTA.executar();

									if(exibirRota.equals(ConstantesSistema.SIM.toString())){
										idRota = Integer.toString(rota.getId());

										atualizarImovelLocalidadeActionForm.set("cdRota", Integer.toString(rota.getCodigo()));
										atualizarImovelLocalidadeActionForm.set("idRota", idRota);

										Integer idLocalidadeAux = localidade.getId();
										String idLocalidadeAuxStr = Integer.toString(idLocalidadeAux);
										idLocalidadeAuxStr = Util.adicionarZerosEsquedaNumero(3, idLocalidadeAuxStr);

										int codigoSetorComercialAux = setorComercial.getCodigo();
										String codigoSetorComercialAuxStr = Integer.toString(codigoSetorComercialAux);
										codigoSetorComercialAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoSetorComercialAuxStr);

										Short codigoRotaAux = rota.getCodigo();
										String codigoRotaAuxStr = Short.toString(codigoRotaAux);
										codigoRotaAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoRotaAuxStr);


										descricaoRota = new StringBuffer();
										descricaoRota.append(" LOCAL: " + idLocalidadeAuxStr + ";");
										descricaoRota.append(" SETOR: " + codigoSetorComercialAuxStr + ";");
										descricaoRota.append(" ROTA: " + codigoRotaAuxStr);

										atualizarImovelLocalidadeActionForm.set("descricaoRota", descricaoRota.toString());
									}else{
										idRota = null;
										cdRota = null;
										atualizarImovelLocalidadeActionForm.set("cdRota", "");
										atualizarImovelLocalidadeActionForm.set("idRota", "");
										atualizarImovelLocalidadeActionForm.set("descricaoRota", "");
									}

								}catch(ControladorException e){
									throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_EXIBIR_ROTA");
								}

								

							}else{
								// 1.2.1. Exibir a mensagem “Com a alteração da quadra, haverá a
								// alteração da rota do imóvel.”;

								atualizarImovelLocalidadeActionForm.set("cdRota", Integer.toString(rota.getCodigo()));

								String matricula = (String) sessao.getAttribute("idImovel");
								FiltroImovel filtroImovel = new FiltroImovel();
								filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, new Integer(matricula)));
								filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);

								Collection colecaoImovel = Fachada.getInstancia().pesquisar(filtroImovel, Imovel.class.getName());

								if(colecaoImovel != null && !colecaoImovel.isEmpty()){
									Imovel objetoImovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
									descricaoRota = new StringBuffer(fachada.obterRota(objetoImovel.getRota().getId()));
								}

								StringBuffer descricaoRotaImovel = descricaoRota;
								Integer idLocalidadeAux = localidade.getId();
								String idLocalidadeAuxStr = Integer.toString(idLocalidadeAux);
								idLocalidadeAuxStr = Util.adicionarZerosEsquedaNumero(3, idLocalidadeAuxStr);

								int codigoSetorComercialAux = setorComercial.getCodigo();
								String codigoSetorComercialAuxStr = Integer.toString(codigoSetorComercialAux);
								codigoSetorComercialAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoSetorComercialAuxStr);

								Short codigoRotaAux = rota.getCodigo();
								String codigoRotaAuxStr = Short.toString(codigoRotaAux);
								codigoRotaAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoRotaAuxStr);

								StringBuffer descricaoRotaQuadra = new StringBuffer();
								descricaoRotaQuadra.append(" LOCAL: " + idLocalidadeAuxStr + ";");
								descricaoRotaQuadra.append(" SETOR: " + codigoSetorComercialAuxStr + ";");
								descricaoRotaQuadra.append(" ROTA: " + codigoRotaAuxStr);

								String[] arg = new String[2];
								arg[0] = descricaoRotaImovel.toString();
								arg[1] = descricaoRotaQuadra.toString();

								if(httpServletRequest.getParameter("confirmado") == null){

									httpServletRequest.setAttribute("destino", "1");

									retorno = montarPaginaConfirmacaoWizard("atencao.confirmacao.alteracao.quadra.rota",
													httpServletRequest, actionMapping, arg);

								}else{

									// 1.2.3. Caso o usuário confirme:
									// 1.2.3.1. Determinar a rota da quadra – ROTA_CDROTA da tabela
									// ROTA com ROTA_ID=ROTA_ID da tabela QUADRA com QDRA_ID=Id da
									// Nova Quadra;
									// 1.2.3.2. Exibir o texto
									// “Localidade:<<LOCA_ID>>;Setor:<<STCM_CDSETORCOMERCIAL>>;Rota:<<ROTA_CDROTA>>”
									// numa caixa de texto suspensa (hint) e permitir que o usuário
									// selecione outra rota.

									// 1.1.1. Determinar a rota da quadra – ROTA_CDROTA da tabela
									// ROTA
									// com ROTA_ID=ROTA_ID
									// da
									// tabela QUADRA com QDRA_ID=Id da Nova Quadra;
									if(httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")){

										atualizarImovelLocalidadeActionForm.set("idQuadra", numeroQuadra);

										idRota = Integer.toString(rota.getId());

										atualizarImovelLocalidadeActionForm.set("cdRota", Integer.toString(rota.getCodigo()));
										atualizarImovelLocalidadeActionForm.set("idRota", idRota);

										atualizarImovelLocalidadeActionForm.set("descricaoRota", descricaoRotaQuadra.toString());

									}else{
										atualizarImovelLocalidadeActionForm.set("cdRota", cdRota);
									}
								}

							}
						}


						if(quadra.getDistritoOperacional() != null){
							atualizarImovelLocalidadeActionForm.set("idDistritoOperacional", quadra.getDistritoOperacional().getId()
											.toString());
						}

						httpServletRequest.setAttribute("nomeCampo", "lote");
						sessao.setAttribute("quadraCaracteristicas", quadras.iterator().next());
					}else{
						httpServletRequest.setAttribute("codigoQuadraNaoEncontrada", "true");
						httpServletRequest.setAttribute("msgQuadra", "QUADRA INEXISTENTE");

						atualizarImovelLocalidadeActionForm.set("idQuadra", "");

						httpServletRequest.setAttribute("nomeCampo", "idQuadra");
					}
				}

			}else{
				atualizarImovelLocalidadeActionForm.set("idQuadra", "");
			}
		}

		// .....................................................................................


		String pesquisaRota = "0";

		if(httpServletRequest.getParameter("pesquisaRota") != null){
			pesquisaRota = httpServletRequest.getParameter("pesquisaRota");
		}else if(sessao.getAttribute("pesquisaRota") != null){
			pesquisaRota = (String) sessao.getAttribute("pesquisaRota");
		}

		if(!pesquisaRota.equals("0")){

			Collection colecaoRotasPorCodigo = null;
			Collection colecaoRotas = new ArrayList();
			// 1. Caso o usuário tenha permissão especial para informar rota não pertencente ao
			// setor comercial do imóvel (existe ocorrência na tabela USUARIO_PERMISSAO_ESPECIAL
			// com
			// USUR_ID=(USUR_ID da tabela USUARIO com USUR_NMLOGIN=Login do usuário) e
			// PMEP_ID=(PMEP_ID da tabela PERMISSAO_ESPECIAL com
			// PMEP_DSPERMISSAOESPECIAL=”INFORMAR
			// ROTA NAO PERTENCENTE AO SETOR COMERCIAL DO IMOVEL”)):

			if(fachada.verificarPermissaoEspecial(PermissaoEspecial.INFORMAR_ROTA_NAO_PERTENCENTE_AO_SETOR_COMERCIAL_DO_IMOVEL, usuario)){

				if(!Util.isVazioOuBranco(cdRota)){
					filtroRota.limparListaParametros();
					filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
					filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL_LOCALIDADE);
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, cdRota));
					filtroRota.setCampoOrderBy(FiltroRota.LOCALIDADE_ID);
					filtroRota.setCampoOrderBy(FiltroRota.SETOR_COMERCIAL_CODIGO);

					rotas = fachada.pesquisar(filtroRota, Rota.class.getName());

					if(Util.isVazioOrNulo(rotas)){
						// 1.1. Caso a rota não exista na tabela ROTA (não existe ocorrência na
						// tabela ROTA com ROTA_CDROTA=Código da rota informado):
						// 1.1.1. Exibir a mensagem “Rota inexistente” e retornar para o passo
						// correspondente no fluxo principal.
						atualizarImovelLocalidadeActionForm.set("cdRota", "");
						atualizarImovelLocalidadeActionForm.set("descricaoRota", "ROTA INEXISTENTE");

						httpServletRequest.setAttribute("rotaNaoEncontrada", "true");
						httpServletRequest.setAttribute("nomeCampo", "idRota");
					}else{

						// 1.2. Caso contrário, ou seja, caso a rota exista na tabela ROTA
						// (existe
						// ocorrência na tabela ROTA com ROTA_CDROTA=Código da rota informado):
						// 1.2.1. Caso exista apenas uma rota com o código informado (existe
						// apenas
						// uma ocorrência na tabela ROTA com ROTA_CDROTA=Código da rota
						// informado):
						// 1.2.1.1. Exibir o texto
						// “Localidade:<<LOCA_ID>>;Setor:<<STCM_CDSETORCOMERCIAL>>;Rota:<<ROTA_CDROTA>>”
						// numa caixa de texto suspensa (hint).

						if(rotas.size() == 1){
							Rota rota = (Rota) Util.retonarObjetoDeColecao(rotas);
							SetorComercial setorComercial = rota.getSetorComercial();
							Localidade localidade = setorComercial.getLocalidade();

							idRota = rota.getId().toString();

							atualizarImovelLocalidadeActionForm.set("cdRota", rota.getCodigo().toString());
							atualizarImovelLocalidadeActionForm.set("idRota", rota.getId().toString());

							Integer idLocalidadeAux = localidade.getId();
							String idLocalidadeAuxStr = Integer.toString(idLocalidadeAux);
							idLocalidadeAuxStr = Util.adicionarZerosEsquedaNumero(3, idLocalidadeAuxStr);

							int codigoSetorComercialAux = setorComercial.getCodigo();
							String codigoSetorComercialAuxStr = Integer.toString(codigoSetorComercialAux);
							codigoSetorComercialAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoSetorComercialAuxStr);

							Short codigoRotaAux = rota.getCodigo();
							String codigoRotaAuxStr = Short.toString(codigoRotaAux);
							codigoRotaAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoRotaAuxStr);

							descricaoRota = new StringBuffer();
							descricaoRota.append("LOCAL: " + idLocalidadeAuxStr + ";");
							descricaoRota.append("SETOR: " + codigoSetorComercialAuxStr + ";");
							descricaoRota.append("ROTA: " + codigoRotaAuxStr + ";");

							atualizarImovelLocalidadeActionForm.set("descricaoRota", descricaoRota.toString());
						}else{
							colecaoRotasPorCodigo = rotas;
							
							if(!pesquisaRota.equals("3")){
								// 1.2.2. Caso contrário, ou seja, caso exista mais de uma rota com
								// o
								// código informado (existe mais de uma ocorrência na tabela ROTA
								// com
								// ROTA_CDROTA=Código da rota informado):

								// 1.2.2.1. Atribuir ao campo “Rota” a rota que corresponda ao setor
								// comercial do imóvel (ocorrência na tabela ROTA com
								// ROTA_CDROTA=Código
								// da rota informado e STCM_ID=Id do Setor Comercial informado),
								// caso
								// exista, e exibir o texto
								// “Localidade:<<LOCA_ID>>;Setor:<<STCM_CDSETORCOMERCIAL>>;Rota:<<ROTA_CDROTA>>”
								// numa caixa de texto suspensa (hint).
								filtroRota.limparListaParametros();
								filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
								filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL_LOCALIDADE);
								filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, cdRota));
								filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, new Integer(
												idSetorComercial)));

								rotas = fachada.pesquisar(filtroRota, Rota.class.getName());

								if(!Util.isVazioOrNulo(rotas)){

									Rota rota = (Rota) Util.retonarObjetoDeColecao(rotas);
									SetorComercial setorComercial = rota.getSetorComercial();
									Localidade localidade = setorComercial.getLocalidade();

									// idRota = Util.adicionarZerosEsquedaNumero(3, idRota);
									// idRota = rota.getId().toString();

									atualizarImovelLocalidadeActionForm.set("cdRota", rota.getCodigo().toString());
									atualizarImovelLocalidadeActionForm.set("idRota", rota.getId().toString());

									Integer idLocalidadeAux = localidade.getId();
									String idLocalidadeAuxStr = Integer.toString(idLocalidadeAux);
									idLocalidadeAuxStr = Util.adicionarZerosEsquedaNumero(3, idLocalidadeAuxStr);

									int codigoSetorComercialAux = setorComercial.getCodigo();
									String codigoSetorComercialAuxStr = Integer.toString(codigoSetorComercialAux);
									codigoSetorComercialAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoSetorComercialAuxStr);

									Short codigoRotaAux = rota.getCodigo();
									String codigoRotaAuxStr = Short.toString(codigoRotaAux);
									codigoRotaAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoRotaAuxStr);

									descricaoRota = new StringBuffer();
									descricaoRota.append("LOCAL: " + idLocalidadeAuxStr + ";");
									descricaoRota.append("SETOR: " + codigoSetorComercialAuxStr + ";");
									descricaoRota.append("ROTA: " + codigoRotaAuxStr + ";");

									atualizarImovelLocalidadeActionForm.set("descricaoRota", descricaoRota.toString());

									// 1.2.2.2. Exibir, na linha imediatamente seguinte à linha do
									// campo
									// “Rota”, as rotas selecionadas, ordenadas pela localidade
									// (LOCA_ID
									// da tabela SETOR_COMERCIAL com STCM_ID=STCM_ID da tabela ROTA)
									// e
									// setor comercial (STCM_CDSETORCOMERCIAL da tabela
									// SETOR_COMERCIAL
									// com STCM_ID=STCM_ID da tabela ROTA), e permitir que o usuário
									// selecione uma das rotas:
									// 1.2.2.2.1. Localidade (LOCA_ID da tabela SETOR_COMERCIAL com
									// STCM_ID=STCM_ID da tabela ROTA).
									// 1.2.2.2.2. Setor Comercial (STCM_CDSETORCOMERCIAL com
									// STCM_ID=STCM_ID da tabela ROTA).

									RotaHelper rotaHelper = null;
									Iterator it = colecaoRotasPorCodigo.iterator();
									while(it.hasNext()){
										Rota rotaEncontrada = (Rota) it.next();
										rotaHelper = new RotaHelper();
										rotaHelper.setId(rotaEncontrada.getId().toString());
										rotaHelper.setDescricao("LOCAL: " + rotaEncontrada.getSetorComercial().getLocalidade().getId()
														+ " ; " + "SETOR: " + rotaEncontrada.getSetorComercial().getCodigo());

										colecaoRotas.add(rotaHelper);
									}

									sessao.setAttribute("colecaoRotas", colecaoRotas);

								}
							}else{
								// 1.2.2.2. Exibir, na linha imediatamente seguinte à linha do
								// campo
								// “Rota”, as rotas selecionadas, ordenadas pela localidade
								// (LOCA_ID
								// da tabela SETOR_COMERCIAL com STCM_ID=STCM_ID da tabela ROTA)
								// e
								// setor comercial (STCM_CDSETORCOMERCIAL da tabela
								// SETOR_COMERCIAL
								// com STCM_ID=STCM_ID da tabela ROTA), e permitir que o usuário
								// selecione uma das rotas:
								// 1.2.2.2.1. Localidade (LOCA_ID da tabela SETOR_COMERCIAL com
								// STCM_ID=STCM_ID da tabela ROTA).
								// 1.2.2.2.2. Setor Comercial (STCM_CDSETORCOMERCIAL com
								// STCM_ID=STCM_ID da tabela ROTA).

								RotaHelper rotaHelper = null;
								Iterator it = colecaoRotasPorCodigo.iterator();
								while(it.hasNext()){
									Rota rotaEncontrada = (Rota) it.next();
									rotaHelper = new RotaHelper();
									rotaHelper.setId(rotaEncontrada.getId().toString());
									rotaHelper.setDescricao("LOCAL: " + rotaEncontrada.getSetorComercial().getLocalidade().getId() + " ; "
													+ "SETOR: " + rotaEncontrada.getSetorComercial().getCodigo());

									colecaoRotas.add(rotaHelper);
								}

								sessao.setAttribute("colecaoRotas", colecaoRotas);
							}

						}

					}
				}
			}else{

				// -------------------------------parte antiga

				if(!Util.isVazioOuBranco(idRota)){

			
							filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
							filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL_LOCALIDADE);
							filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, idRota));

							rotas = fachada.pesquisar(filtroRota, Rota.class.getName());

							if(!Util.isVazioOrNulo(rotas)){

								Rota rota = (Rota) Util.retonarObjetoDeColecao(rotas);
								atualizarImovelLocalidadeActionForm.set("cdRota", rota.getCodigo().toString());
								atualizarImovelLocalidadeActionForm.set("idRota", rota.getId().toString());
								atualizarImovelLocalidadeActionForm.set("descricaoRota", rota.getDescricao());
								

							}else{
								atualizarImovelLocalidadeActionForm.set("cdRota", "");
								atualizarImovelLocalidadeActionForm.set("descricaoRota", "ROTA INEXISTENTE");

								httpServletRequest.setAttribute("rotaNaoEncontrada", "true");
								httpServletRequest.setAttribute("nomeCampo", "idRota");
							}
				

				
					
				}else if(!Util.isVazioOuBranco(cdRota)){
					filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
					filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL_LOCALIDADE);
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, cdRota));
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidade));
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));

					rotas = fachada.pesquisar(filtroRota, Rota.class.getName());

					if(!Util.isVazioOrNulo(rotas)){

						Rota rota = (Rota) Util.retonarObjetoDeColecao(rotas);
						atualizarImovelLocalidadeActionForm.set("cdRota", rota.getCodigo().toString());
						atualizarImovelLocalidadeActionForm.set("idRota", rota.getId().toString());
						atualizarImovelLocalidadeActionForm.set("descricaoRota", rota.getDescricao());
					}else{
						atualizarImovelLocalidadeActionForm.set("cdRota", "");
						atualizarImovelLocalidadeActionForm.set("descricaoRota", "ROTA INEXISTENTE");

						httpServletRequest.setAttribute("rotaNaoEncontrada", "true");
						httpServletRequest.setAttribute("nomeCampo", "idRota");
				}
			}

			}

			if(Util.isNaoNuloBrancoZero(idLocalidade) && Util.isNaoNuloBrancoZero(codigoSetorComercial)
							&& Util.isNaoNuloBrancoZero(numeroQuadra) && Util.isNaoNuloBrancoZero(idRota)){
				fachada.verificarAlteracaoRota(Integer.valueOf(idLocalidade), Integer.valueOf(codigoSetorComercial),
								Integer.valueOf(numeroQuadra), Integer.valueOf(idRota));
			}

		}

		if(pesquisaRota.equals("2")){
			sessao.removeAttribute("colecaoRotas");
		}

		if(nnSegmento != null && !nnSegmento.toString().trim().equalsIgnoreCase("")){
			atualizarImovelLocalidadeActionForm.set("nnSegmento", ""
							+ Util.adicionarZerosEsquedaNumero(2, new Integer(nnSegmento.trim()).toString()));

		}else{
			atualizarImovelLocalidadeActionForm.set("nnSegmento", "");
		}

		colecaoDistritoOperacional = (Collection) sessao.getAttribute("colecaoDistritoOperacional");
		FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

		if(Util.isVazioOrNulo(colecaoDistritoOperacional)){

			filtroDistritoOperacional.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoDistritoOperacional = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

			if(!Util.isVazioOrNulo(colecaoDistritoOperacional)){
				sessao.setAttribute("colecaoDistritoOperacional", colecaoDistritoOperacional);
			}
		}

		if(!Util.isVazioOuBranco(idDistritoOperacional)
						&& (!Integer.valueOf(idDistritoOperacional).equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			atualizarImovelLocalidadeActionForm.set("idDistritoOperacional", idDistritoOperacional);
		}

		return retorno;
	}



}
