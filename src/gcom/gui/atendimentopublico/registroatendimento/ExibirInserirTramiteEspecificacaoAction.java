/*
 * Copyright (C) 2007-2007 the GSAN  Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place  Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN  Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela exibição da pagina de inserir Trâmite por Especificação
 * 
 * @author Ailton Sousa
 * @created 28 de Março de 2011
 */
public class ExibirInserirTramiteEspecificacaoAction
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

		HttpSession sessao = httpServletRequest.getSession(false);

		String objetoConsulta = "";
		String operacao = "";

		ActionForward retorno = actionMapping.findForward("inserirTramiteEspecificacao");
		InserirTramiteEspecificacaoActionForm inserirTramiteEspecificacaoActionForm = (InserirTramiteEspecificacaoActionForm) actionForm;

		// limpa os campos do formulário e da sessão
		if(httpServletRequest.getParameter("limpaSessao") != null && !httpServletRequest.getParameter("limpaSessao").equals("")){
			limparFormulario(inserirTramiteEspecificacaoActionForm, sessao);
		}

		objetoConsulta = httpServletRequest.getParameter("objetoConsulta");
		operacao = httpServletRequest.getParameter("operacao");

		Fachada fachada = Fachada.getInstancia();

		// Se for a operação de Adicionar
		if(operacao != null && operacao.equals("adicionar")){

			adicionarColecaoTramiteEspecificacao(httpServletRequest, inserirTramiteEspecificacaoActionForm, sessao, fachada);
			limparFormulario(inserirTramiteEspecificacaoActionForm, sessao);
		}else if(operacao != null && operacao.equals("remover")){
			removerColecaoTramiteEspecificacao(httpServletRequest, sessao);
		}

		if(Util.verificarIdNaoVazio(inserirTramiteEspecificacaoActionForm.getTipoSolicitacao())){

			sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", getColecaoSolicitacaoTipoEspecificacao(inserirTramiteEspecificacaoActionForm.getTipoSolicitacao()));
		}

		if((objetoConsulta == null || objetoConsulta.equals("")) || (operacao != null && operacao.equals("adicionar"))){

			Collection<SolicitacaoTipo> colecaoSolicitacaoTipo = (Collection<SolicitacaoTipo>) sessao
							.getAttribute("colecaoSolicitacaoTipo");

			if(colecaoSolicitacaoTipo == null){
				colecaoSolicitacaoTipo = getColecaoSolicitacaoTipo();
				sessao.setAttribute("colecaoSolicitacaoTipo", colecaoSolicitacaoTipo);
			}

		}else{
			pesquisaObjetoConsulta(objetoConsulta, inserirTramiteEspecificacaoActionForm, httpServletRequest);
		}

		// remove o parametro de retorno
		sessao.removeAttribute("retornarTela");

		return retorno;
	}

	private void removerColecaoTramiteEspecificacao(HttpServletRequest httpServletRequest, HttpSession sessao){

		Collection colecaoTramiteEspecificacao = (Collection) sessao.getAttribute("colecaoTramiteEspecificacao");

		Iterator iteratorTramiteEspecificacao = colecaoTramiteEspecificacao.iterator();
		String codigoTramiteEspecificacao = httpServletRequest.getParameter("codigoTramiteEspecificacao");
		while(iteratorTramiteEspecificacao.hasNext()){
			EspecificacaoTramite especificacaoTramite = (EspecificacaoTramite) iteratorTramiteEspecificacao.next();
			long valorTempo = especificacaoTramite.getUltimaAlteracao().getTime();
			if(valorTempo == Long.parseLong(codigoTramiteEspecificacao)){
				iteratorTramiteEspecificacao.remove();
			}
		}

		sessao.setAttribute("colecaoTramiteEspecificacao", colecaoTramiteEspecificacao);
	}

	private void adicionarColecaoTramiteEspecificacao(HttpServletRequest httpServletRequest, InserirTramiteEspecificacaoActionForm form,
					HttpSession sessao, Fachada fachada){

		Collection colecaoTramiteEspecificacao = null;

		if(sessao.getAttribute("colecaoTramiteEspecificacao") == null){
			colecaoTramiteEspecificacao = new ArrayList();
		}else{
			colecaoTramiteEspecificacao = (Collection) sessao.getAttribute("colecaoTramiteEspecificacao");
		}

		EspecificacaoTramite especificacaoTramite = new EspecificacaoTramite();

		if(form.getSolicitacaoTipoEspecificacao() != null && !form.getSolicitacaoTipoEspecificacao().equals("")
						&& !form.getSolicitacaoTipoEspecificacao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();

			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, form
							.getSolicitacaoTipoEspecificacao()));

			Collection especificacaoEncontrada = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class
							.getName());

			if(especificacaoEncontrada != null && !especificacaoEncontrada.isEmpty()){
				SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) ((List) especificacaoEncontrada)
								.get(0);
				especificacaoTramite.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Solicitação Tipo Especificação");
			}
		}
		if(form.getLocalidade() != null && !form.getLocalidade().equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, form.getLocalidade()));

			Collection localidadeEncontrada = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(localidadeEncontrada != null && !localidadeEncontrada.isEmpty()){
				Localidade localidade = (Localidade) ((List) localidadeEncontrada).get(0);
				especificacaoTramite.setLocalidade(localidade);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}
		}
		if(form.getCodigoSetorComercial() != null && !form.getCodigoSetorComercial().equals("")){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, form
							.getCodigoSetorComercial()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, form.getLocalidade()));

			Collection setorComercialEncontrado = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(setorComercialEncontrado != null && !setorComercialEncontrado.isEmpty()){
				SetorComercial setorComercial = (SetorComercial) ((List) setorComercialEncontrado).get(0);
				especificacaoTramite.setSetorComercial(setorComercial);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial");
			}
		}
		if(form.getCodigoBairro() != null && !form.getCodigoBairro().equals("")){
			FiltroBairro filtroBairro = new FiltroBairro();

			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, form.getCodigoBairro()));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, form.getMunicipio()));

			Collection bairroEncontrado = fachada.pesquisar(filtroBairro, Bairro.class.getName());

			if(bairroEncontrado != null && !bairroEncontrado.isEmpty()){
				Bairro bairro = (Bairro) ((List) bairroEncontrado).get(0);
				especificacaoTramite.setBairro(bairro);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bairro");
			}
		}

		if(form.getUnidadeOrganizacionalOrigem() != null && !form.getUnidadeOrganizacionalOrigem().equals("")){
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, form
							.getUnidadeOrganizacionalOrigem()));

			Collection unidadeOrganizacionalEncontrada = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class
							.getName());

			if(unidadeOrganizacionalEncontrada != null && !unidadeOrganizacionalEncontrada.isEmpty()){
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) ((List) unidadeOrganizacionalEncontrada).get(0);
				especificacaoTramite.setUnidadeOrganizacionalOrigem(unidadeOrganizacional);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Organizacional Origem");
			}
		}
		if(form.getUnidadeOrganizacionalDestino() != null && !form.getUnidadeOrganizacionalDestino().equals("")){
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, form
							.getUnidadeOrganizacionalDestino()));

			Collection unidadeOrganizacionalEncontrada = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class
							.getName());

			if(unidadeOrganizacionalEncontrada != null && !unidadeOrganizacionalEncontrada.isEmpty()){
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) ((List) unidadeOrganizacionalEncontrada).get(0);
				especificacaoTramite.setUnidadeOrganizacionalDestino(unidadeOrganizacional);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Organizacional Destino");
			}
		}

		// indicador de uso ativo
		especificacaoTramite.setIndicadorUso(Short.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO));

		if(form.getIndicadorPrimeiroTramite() != null && !form.getIndicadorPrimeiroTramite().equals("")){

			especificacaoTramite.setIndicadorPrimeiroTramite(Util.converterStringParaShort(form.getIndicadorPrimeiroTramite()));
		}else{
			especificacaoTramite.setIndicadorPrimeiroTramite(ConstantesSistema.NAO);
		}

		// hora e data correntes
		especificacaoTramite.setUltimaAlteracao(new Date());

		// adiciona na coleção o tipo de solicitação especificado

		if(!colecaoTramiteEspecificacao.contains(especificacaoTramite)){

			// [FS0001] - Verificar existência de trâmite para a especificação
			verificarExistenciaUnidadeDestinoEspecificacaoColecao(colecaoTramiteEspecificacao, especificacaoTramite);
			verificarExistenciaTramiteEspecificacao(especificacaoTramite, fachada);
					

			if(especificacaoTramite.getIndicadorPrimeiroTramite().equals(ConstantesSistema.SIM)){
				// [FS0002] - Verificar existência de primeiro trâmite para a especificação
				verificarExistenciaPrimeiroTramiteEspecificacaoColecao(colecaoTramiteEspecificacao, especificacaoTramite);
				verificarExistenciaPrimeiroTramiteEspecificacao(especificacaoTramite, fachada);

			}

			colecaoTramiteEspecificacao.add(especificacaoTramite);
		}else{
			throw new ActionServletException("atencao.especificacao_tramite_ja_existente");
		}

		sessao.setAttribute("colecaoTramiteEspecificacao", colecaoTramiteEspecificacao);
	}

	private void pesquisaObjetoConsulta(String objetoConsulta, InserirTramiteEspecificacaoActionForm form,
					HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();

		String idLocalidade = form.getLocalidade();
		String codigoSetorComercial = form.getCodigoSetorComercial();
		String idMunicipio = form.getMunicipio();
		String codigoBairro = form.getCodigoBairro();
		String idUnidadeOrigem = form.getUnidadeOrganizacionalOrigem();
		String idUnidadeDestino = form.getUnidadeOrganizacionalDestino();

		// Limpando campos de descrição quando o id/código é vazio
		if(idLocalidade != null && idLocalidade.trim().equals("")){
			form.setNomeLocalidade("");
		}

		if(codigoSetorComercial != null && codigoSetorComercial.trim().equals("")){
			form.setNomeSetorComercial("");
		}

		if(idMunicipio != null && idMunicipio.trim().equals("")){
			form.setNomeMunicipio("");
		}

		if(codigoBairro != null && codigoBairro.trim().equals("")){
			form.setNomeBairro("");
		}

		if(idUnidadeOrigem != null && idUnidadeOrigem.trim().equals("")){
			form.setNomeUnidadeOrganizacionalOrigem("");
		}

		if(idUnidadeDestino != null && idUnidadeDestino.trim().equals("")){
			form.setNomeUnidadeOrganizacionalDestino("");
		}

		if(objetoConsulta.equals("1")){
			// Localidade
			if(idLocalidade != null && !idLocalidade.trim().equals("")){
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

				Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(localidades != null && !localidades.isEmpty()){
					Localidade localidade = (Localidade) ((List) localidades).get(0);

					String id = Util.adicionarZerosEsqueda(3, idLocalidade);
					form.setLocalidade(id);

					String descricao = localidade.getDescricao();
					form.setNomeLocalidade(descricao);

					httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
				}else{
					httpServletRequest.setAttribute("localidadeNaoEncontrada", "true");

					form.setLocalidade("");
					form.setNomeLocalidade("LOCALIDADE INEXISTENTE");

					form.setCodigoSetorComercial("");
					form.setNomeSetorComercial("");

					httpServletRequest.setAttribute("nomeCampo", "localidade");
				}
			}
		}else if(objetoConsulta.equals("2")){
			// Setor Comercial
			if(codigoSetorComercial != null && !codigoSetorComercial.trim().equals("")){
				if(idLocalidade != null && !idLocalidade.trim().equals("")){
					FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
									codigoSetorComercial));

					Collection setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

					if(setorComerciais != null && !setorComerciais.isEmpty()){
						SetorComercial setorComercial = (SetorComercial) ((List) setorComerciais).get(0);

						String codigo = Util.adicionarZerosEsqueda(3, codigoSetorComercial);
						form.setCodigoSetorComercial(codigo);

						String descricao = setorComercial.getDescricao();
						form.setNomeSetorComercial(descricao);

						httpServletRequest.setAttribute("nomeCampo", "municipio");
					}else{
						httpServletRequest.setAttribute("setorComercialNaoEncontrado", "true");

						form.setCodigoSetorComercial("");
						form.setNomeSetorComercial("SETOR COMERCIAL INEXISTENTE");

						httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
					}
				}

			}else{
				form.setCodigoSetorComercial("");
				form.setNomeSetorComercial("");
			}
		}else if(objetoConsulta.equals("3")){
			// Municipio
			if(idMunicipio != null && !idMunicipio.trim().equals("")){
				FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));

				Collection municipios = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

				if(municipios != null && !municipios.isEmpty()){
					Municipio municipio = (Municipio) ((List) municipios).get(0);

					String id = Util.adicionarZerosEsqueda(4, idMunicipio);
					form.setMunicipio(id);

					String nome = municipio.getNome();
					form.setNomeMunicipio(nome);

					httpServletRequest.setAttribute("nomeCampo", "codigoBairro");
				}else{
					httpServletRequest.setAttribute("municipioNaoEncontrado", "true");

					form.setMunicipio("");
					form.setNomeMunicipio("MUNICIPIO INEXISTENTE");

					form.setCodigoBairro("");
					form.setNomeBairro("");

					httpServletRequest.setAttribute("nomeCampo", "municipio");
				}
			}else{
				form.setMunicipio("");
				form.setNomeMunicipio("");
			}
		}else if(objetoConsulta.equals("4")){
			// Bairro
			if(codigoBairro != null && !codigoBairro.trim().equals("")){
				if(idMunicipio != null && !idMunicipio.trim().equals("")){
					FiltroBairro filtroBairro = new FiltroBairro();
					filtroBairro
									.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO,
													ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipio));
					filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, codigoBairro));

					Collection bairros = fachada.pesquisar(filtroBairro, Bairro.class.getName());

					if(bairros != null && !bairros.isEmpty()){
						Bairro setorComercial = (Bairro) ((List) bairros).get(0);

						String codigo = Util.adicionarZerosEsqueda(4, codigoBairro);
						form.setCodigoBairro(codigo);

						String nome = setorComercial.getNome();
						form.setNomeBairro(nome);

						httpServletRequest.setAttribute("nomeCampo", "sistemaAbastecimento");
					}else{
						httpServletRequest.setAttribute("bairroNaoEncontrado", "true");

						form.setCodigoBairro("");
						form.setNomeBairro("BAIRRO INEXISTENTE");

						httpServletRequest.setAttribute("nomeCampo", "codigoBairro");
					}
				}

			}else{
				form.setCodigoBairro("");
				form.setNomeBairro("");
			}
		}else if(objetoConsulta.equals("5")){
			// Unidade Organizacional Origem
			if(idUnidadeOrigem != null && !idUnidadeOrigem.trim().equals("")){
				FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
				filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrigem));

				Collection unidades = fachada.pesquisar(filtro, UnidadeOrganizacional.class.getName());

				if(unidades != null && !unidades.isEmpty()){
					UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) ((List) unidades).get(0);

					String codigo = Util.adicionarZerosEsqueda(8, idUnidadeOrigem);
					form.setUnidadeOrganizacionalOrigem(codigo);

					String descricao = unidadeOrganizacional.getDescricao();
					form.setNomeUnidadeOrganizacionalOrigem(descricao);

					httpServletRequest.setAttribute("nomeCampo", "unidadeOrganizacionalDestino");
				}else{
					httpServletRequest.setAttribute("unidadeOrigemNaoEncontrado", "true");

					form.setUnidadeOrganizacionalOrigem("");
					form.setNomeUnidadeOrganizacionalOrigem("UNIDADE ORGANIZACIONAL INEXISTENTE");

					httpServletRequest.setAttribute("nomeCampo", "unidadeOrganizacionalOrigem");
				}
			}else{
				form.setUnidadeOrganizacionalOrigem("");
				form.setNomeUnidadeOrganizacionalOrigem("");
			}
		}else if(objetoConsulta.equals("6")){
			// Unidade Organizacional Destino
			if(idUnidadeDestino != null && !idUnidadeDestino.trim().equals("")){
				FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
				filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeDestino));

				Collection unidades = fachada.pesquisar(filtro, UnidadeOrganizacional.class.getName());

				if(unidades != null && !unidades.isEmpty()){
					UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) ((List) unidades).get(0);

					String codigo = Util.adicionarZerosEsqueda(8, idUnidadeDestino);
					form.setUnidadeOrganizacionalDestino(codigo);

					String descricao = unidadeOrganizacional.getDescricao();
					form.setNomeUnidadeOrganizacionalDestino(descricao);

				}else{
					httpServletRequest.setAttribute("unidadeDestinoNaoEncontrado", "true");

					form.setUnidadeOrganizacionalDestino("");
					form.setNomeUnidadeOrganizacionalDestino("UNIDADE ORGANIZACIONAL INEXISTENTE");

					httpServletRequest.setAttribute("nomeCampo", "unidadeOrganizacionalDestino");
				}
			}else{
				form.setUnidadeOrganizacionalDestino("");
				form.setNomeUnidadeOrganizacionalDestino("");
			}
		}

	}

	private void limparFormulario(InserirTramiteEspecificacaoActionForm form, HttpSession sessao){

		form.setTipoSolicitacao("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setSolicitacaoTipoEspecificacao("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setLocalidade("");
		form.setNomeLocalidade("");
		form.setCodigoSetorComercial("");
		form.setNomeSetorComercial("");
		form.setMunicipio("");
		form.setNomeMunicipio("");
		form.setCodigoBairro("");
		form.setNomeBairro("");

		form.setUnidadeOrganizacionalOrigem("");
		form.setNomeUnidadeOrganizacionalOrigem("");
		form.setUnidadeOrganizacionalDestino("");
		form.setNomeUnidadeOrganizacionalDestino("");

		sessao.removeAttribute("colecaoSolicitacaoTipo");
		sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacao");
		sessao.removeAttribute("colecaoSistemaAbastecimento");
		sessao.removeAttribute("colecaoDistritoOperacional");
		sessao.removeAttribute("colecaoZonaAbastecimento");
		sessao.removeAttribute("colecaoSetorAbastecimento");
		sessao.removeAttribute("colecaoSistemaEsgoto");
		sessao.removeAttribute("colecaoSubsistemaEsgoto");
		sessao.removeAttribute("colecaoBacia");
		sessao.removeAttribute("colecaoSubBacia");
	}

	/**
	 * Retorna Coleção de Solicitação Tipo
	 */
	@SuppressWarnings("unchecked")
	private Collection<SolicitacaoTipo> getColecaoSolicitacaoTipo(){

		FiltroSolicitacaoTipo filtro = new FiltroSolicitacaoTipo();
		filtro.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SolicitacaoTipo> colecao = fachada.pesquisar(filtro, SolicitacaoTipo.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Solicitação");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Solicitação Tipo Especificação
	 */
	@SuppressWarnings("unchecked")
	private Collection<SolicitacaoTipoEspecificacao> getColecaoSolicitacaoTipoEspecificacao(String idSolicitacaoTipo){

		FiltroSolicitacaoTipoEspecificacao filtro = new FiltroSolicitacaoTipoEspecificacao();
		filtro.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		if(idSolicitacaoTipo != null && !idSolicitacaoTipo.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, idSolicitacaoTipo));
		}

		Fachada fachada = Fachada.getInstancia();

		Collection<SolicitacaoTipoEspecificacao> colecao = fachada.pesquisar(filtro, SolicitacaoTipoEspecificacao.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Especificação");
		}

		return colecao;
	}



	// [FS0001] - Verificar existência de trâmite para a especificação
	private void verificarExistenciaTramiteEspecificacao(EspecificacaoTramite tramiteEspecificacao, Fachada fachada){

		// . Caso a unidade destino informada já exista para a especificação (existe ocorrência na
		// tabela ESPECIFICACAO_TRAMITE com STEP_ID=Id da especificação selecionada e (LOCA_ID=Id da
		// Localidade pesquisada, caso tenha pesquisado alguma localidade; caso contrário, LOCA_ID
		// com o valor nulo) e (STCM_ID=Id do Setor Comercial pesquisado, caso tenha pesquisado
		// algum setor comercial; caso contrário, STCM_ID com o valor nulo) e (BAIR_ID=Id do Bairro
		// pesquisado, caso tenha pesquisado algum bairro; caso contrário, BAIR_ID com o valor nulo)
		// e (UNID_IDORIGEM=Id da Unidade Origem pesquisada, caso tenha pesquisado alguma unidade
		// origem; caso contrário, UNID_IDORIGEM com o valor nulo) e UNID_IDDESTINO=Id da unidade
		// destino pesquisada):

		if(!Util.isVazioOuBranco(tramiteEspecificacao.getUnidadeOrganizacionalDestino())){

			Collection<UnidadeOrganizacional> colecao = fachada.obterUnidadeDestinoPorEspecificacao(tramiteEspecificacao, false);

			if(!Util.isVazioOrNulo(colecao)){
				// // . Exibir a mensagem "Trâmite por Especificação já existe no cadastro"
				// // . Retornar para o passo correspondente no fluxo.
				throw new ActionServletException("atencao.especificacao_tramite_ja_existente");

			}

		}


	}

	// [FS0002] - Verificar existência de primeiro trâmite para a especificação

	private void verificarExistenciaPrimeiroTramiteEspecificacao(EspecificacaoTramite tramiteEspecificacao, Fachada fachada){

		// . Caso a associação seja do primeiro trâmite para a especificação (campo "Unidade do
		// Primeiro
		// Trâmite?" com a opção "Sim" selecionada):

		UnidadeOrganizacional unidadeDestino = null;

		if(!Util.isVazioOuBranco(tramiteEspecificacao.getIndicadorPrimeiroTramite())
						&& tramiteEspecificacao.getIndicadorPrimeiroTramite().equals(ConstantesSistema.SIM)){



			unidadeDestino = tramiteEspecificacao.getUnidadeOrganizacionalDestino();

			tramiteEspecificacao.setUnidadeOrganizacionalDestino(null);

			Collection<UnidadeOrganizacional> colecao = fachada.obterUnidadeDestinoPorEspecificacao(tramiteEspecificacao, true);

			if(!Util.isVazioOrNulo(colecao)){

				throw new ActionServletException("atencao.indidador.primeiro.tramite.ja.definido", null, unidadeDestino.getId().toString());


			}
			
			tramiteEspecificacao.setUnidadeOrganizacionalDestino(unidadeDestino);

		}

	}

	/**
	 * @param colecao
	 * @param especificacaoTramiteParaAdd
	 */
	private void verificarExistenciaPrimeiroTramiteEspecificacaoColecao(Collection colecao,
					EspecificacaoTramite especificacaoTramiteParaAdd){

		Iterator it = colecao.iterator();

		while(it.hasNext()){
			EspecificacaoTramite especificacaoTramite = (EspecificacaoTramite) it.next();

			if((especificacaoTramite.getIndicadorPrimeiroTramite().equals(ConstantesSistema.SIM)
							&& especificacaoTramite.getSolicitacaoTipoEspecificacao().getId()
							.equals(especificacaoTramiteParaAdd.getSolicitacaoTipoEspecificacao().getId()))
							&& ((especificacaoTramite.getLocalidade() == null && especificacaoTramiteParaAdd.getLocalidade() == null) || especificacaoTramite
											.getLocalidade().getId().equals(especificacaoTramiteParaAdd.getLocalidade().getId()))
							&& ((especificacaoTramite.getSetorComercial() == null && especificacaoTramiteParaAdd.getSetorComercial() == null)
							|| especificacaoTramite.getSetorComercial().getId()
.equals(especificacaoTramiteParaAdd.getSetorComercial().getId()))
							&& ((especificacaoTramite.getBairro() == null && especificacaoTramiteParaAdd.getBairro() == null) || especificacaoTramite
											.getBairro().getId().equals(especificacaoTramiteParaAdd.getBairro().getId()))
							&& ((especificacaoTramite.getUnidadeOrganizacionalOrigem() == null && especificacaoTramiteParaAdd
											.getUnidadeOrganizacionalOrigem() == null)
							|| especificacaoTramite.getUnidadeOrganizacionalOrigem().getId()
											.equals(especificacaoTramiteParaAdd.getUnidadeOrganizacionalOrigem().getId()))){
				throw new ActionServletException("atencao.indidador.primeiro.tramite.ja.definido", null, especificacaoTramite
								.getUnidadeOrganizacionalDestino().getDescricao());
			}

		}
	}

	/**
	 * @param colecao
	 * @param especificacaoTramiteParaAdd
	 */
	private void verificarExistenciaUnidadeDestinoEspecificacaoColecao(Collection colecao, EspecificacaoTramite especificacaoTramiteParaAdd){

		Iterator it = colecao.iterator();

		while(it.hasNext()){
			EspecificacaoTramite especificacaoTramite = (EspecificacaoTramite) it.next();

			if((especificacaoTramite.getSolicitacaoTipoEspecificacao().getId().equals(especificacaoTramiteParaAdd
							.getSolicitacaoTipoEspecificacao().getId()))
							&& ((especificacaoTramite.getLocalidade() == null && especificacaoTramiteParaAdd.getLocalidade() == null) || especificacaoTramite
											.getLocalidade().getId().equals(especificacaoTramiteParaAdd.getLocalidade().getId()))
							&& ((especificacaoTramite.getSetorComercial() == null && especificacaoTramiteParaAdd.getSetorComercial() == null)
							|| especificacaoTramite.getSetorComercial().getId()
.equals(especificacaoTramiteParaAdd.getSetorComercial().getId()))
							&& ((especificacaoTramite.getBairro() == null && especificacaoTramiteParaAdd.getBairro() == null) || especificacaoTramite
											.getBairro().getId().equals(especificacaoTramiteParaAdd.getBairro().getId()))
							&& ((especificacaoTramite.getUnidadeOrganizacionalOrigem() == null && especificacaoTramiteParaAdd
											.getUnidadeOrganizacionalOrigem() == null)
							|| especificacaoTramite.getUnidadeOrganizacionalOrigem().getId()
											.equals(especificacaoTramiteParaAdd.getUnidadeOrganizacionalOrigem().getId()))
							&& (especificacaoTramite.getIndicadorPrimeiroTramite().equals(especificacaoTramiteParaAdd
											.getIndicadorPrimeiroTramite()))

			){
				throw new ActionServletException("atencao.especificacao_tramite_ja_existente");
			}

		}
	}

}
