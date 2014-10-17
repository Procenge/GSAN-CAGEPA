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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.EspecificacaoTramite;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
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
import gcom.operacional.Bacia;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroBacia;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroSetorAbastecimento;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubBacia;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.operacional.SetorAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubBacia;
import gcom.operacional.SubsistemaEsgoto;
import gcom.operacional.ZonaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

			Collection<SistemaAbastecimento> colecaoSistemaAbastecimento = (Collection<SistemaAbastecimento>) sessao
							.getAttribute("colecaoSistemaAbastecimento");

			if(colecaoSistemaAbastecimento == null){
				colecaoSistemaAbastecimento = getColecaoSistemaAbastecimento();
				sessao.setAttribute("colecaoSistemaAbastecimento", colecaoSistemaAbastecimento);
			}

			Collection<DistritoOperacional> colecaoDistritoOperacional = (Collection<DistritoOperacional>) sessao
							.getAttribute("colecaoDistritoOperacional");

			if(colecaoDistritoOperacional == null){
				colecaoDistritoOperacional = getColecaoDistritoOperacional(null, null, null);
				sessao.setAttribute("colecaoDistritoOperacional", colecaoDistritoOperacional);
			}

			Collection<ZonaAbastecimento> colecaoZonaAbastecimento = (Collection<ZonaAbastecimento>) sessao
							.getAttribute("colecaoZonaAbastecimento");

			if(colecaoZonaAbastecimento == null){
				colecaoZonaAbastecimento = getColecaoZonaAbastecimento(null, null, null);
				sessao.setAttribute("colecaoZonaAbastecimento", colecaoZonaAbastecimento);
			}

			Collection<SetorAbastecimento> colecaoSetorAbastecimento = (Collection<SetorAbastecimento>) sessao
							.getAttribute("colecaoSetorAbastecimento");

			if(colecaoSetorAbastecimento == null){
				colecaoSetorAbastecimento = getColecaoSetorAbastecimento(null, null);
				sessao.setAttribute("colecaoSetorAbastecimento", colecaoSetorAbastecimento);
			}

			Collection<SistemaEsgoto> colecaoSistemaEsgoto = (Collection<SistemaEsgoto>) sessao.getAttribute("colecaoSistemaEsgoto");

			if(colecaoSistemaEsgoto == null){
				colecaoSistemaEsgoto = getColecaoSistemaEsgoto();
				sessao.setAttribute("colecaoSistemaEsgoto", colecaoSistemaEsgoto);
			}

			Collection<SubsistemaEsgoto> colecaoSubsistemaEsgoto = (Collection<SubsistemaEsgoto>) sessao
							.getAttribute("colecaoSubsistemaEsgoto");

			if(colecaoSubsistemaEsgoto == null){
				colecaoSubsistemaEsgoto = getColecaoSubsistemaEsgoto(null);
				sessao.setAttribute("colecaoSubsistemaEsgoto", colecaoSubsistemaEsgoto);
			}

			Collection<Bacia> colecaoBacia = (Collection<Bacia>) sessao.getAttribute("colecaoBacia");

			if(colecaoBacia == null){
				colecaoBacia = getColecaoBacia(null, null);
				sessao.setAttribute("colecaoBacia", colecaoBacia);
			}

			Collection<SubBacia> colecaoSubBacia = (Collection<SubBacia>) sessao.getAttribute("colecaoSubBacia");

			if(colecaoSubBacia == null){
				colecaoSubBacia = getColecaoSubBacia(null);
				sessao.setAttribute("colecaoSubBacia", colecaoSubBacia);
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
		if(form.getSistemaAbastecimento() != null && !form.getSistemaAbastecimento().equals("")
						&& !form.getSistemaAbastecimento().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();

			filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.ID, form
							.getSistemaAbastecimento()));

			Collection sistemaAbastecimentoEncontrado = fachada.pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

			if(sistemaAbastecimentoEncontrado != null && !sistemaAbastecimentoEncontrado.isEmpty()){
				SistemaAbastecimento sistemaAbastecimento = (SistemaAbastecimento) ((List) sistemaAbastecimentoEncontrado).get(0);
				especificacaoTramite.setSistemaAbastecimento(sistemaAbastecimento);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Sistema de Abastecimento");
			}
		}
		if(form.getDistritoOperacional() != null && !form.getDistritoOperacional().equals("")
						&& !form.getDistritoOperacional().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, form.getDistritoOperacional()));

			Collection distritoOperacionalEncontrado = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

			if(distritoOperacionalEncontrado != null && !distritoOperacionalEncontrado.isEmpty()){
				DistritoOperacional distritoOperacional = (DistritoOperacional) ((List) distritoOperacionalEncontrado).get(0);
				especificacaoTramite.setDistritoOperacional(distritoOperacional);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Distrito Operacional");
			}
		}
		if(form.getZonaAbastecimento() != null && !form.getZonaAbastecimento().equals("")
						&& !form.getZonaAbastecimento().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.ID, form.getZonaAbastecimento()));

			Collection zonaAbastecimentoEncontrada = fachada.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

			if(zonaAbastecimentoEncontrada != null && !zonaAbastecimentoEncontrada.isEmpty()){
				ZonaAbastecimento zonaAbastecimento = (ZonaAbastecimento) ((List) zonaAbastecimentoEncontrada).get(0);
				especificacaoTramite.setZonaAbastecimento(zonaAbastecimento);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Zona de Abastecimento");
			}
		}
		if(form.getSetorAbastecimento() != null && !form.getSetorAbastecimento().equals("")
						&& !form.getSetorAbastecimento().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroSetorAbastecimento filtroSetorAbastecimento = new FiltroSetorAbastecimento();

			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ID, form.getSetorAbastecimento()));

			Collection setorAbastecimentoEncontrado = fachada.pesquisar(filtroSetorAbastecimento, SetorAbastecimento.class.getName());

			if(setorAbastecimentoEncontrado != null && !setorAbastecimentoEncontrado.isEmpty()){
				SetorAbastecimento setorAbastecimento = (SetorAbastecimento) ((List) setorAbastecimentoEncontrado).get(0);
				especificacaoTramite.setSetorAbastecimento(setorAbastecimento);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor de Abastecimento");
			}
		}
		if(form.getSistemaEsgoto() != null && !form.getSistemaEsgoto().equals("")
						&& !form.getSistemaEsgoto().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();

			filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.ID, form.getSistemaEsgoto()));

			Collection sistemaEsgotoEncontrado = fachada.pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

			if(sistemaEsgotoEncontrado != null && !sistemaEsgotoEncontrado.isEmpty()){
				SistemaEsgoto sistemaEsgoto = (SistemaEsgoto) ((List) sistemaEsgotoEncontrado).get(0);
				especificacaoTramite.setSistemaEsgoto(sistemaEsgoto);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Sistema de Esgoto");
			}
		}
		if(form.getSubsistemaEsgoto() != null && !form.getSubsistemaEsgoto().equals("")
						&& !form.getSubsistemaEsgoto().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();

			filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.ID, form.getSubsistemaEsgoto()));

			Collection subsistemaEsgotoEncontrado = fachada.pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

			if(subsistemaEsgotoEncontrado != null && !subsistemaEsgotoEncontrado.isEmpty()){
				SubsistemaEsgoto subsistemaEsgoto = (SubsistemaEsgoto) ((List) subsistemaEsgotoEncontrado).get(0);
				especificacaoTramite.setSubsistemaEsgoto(subsistemaEsgoto);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Subsistema de Esgoto");
			}
		}
		if(form.getBacia() != null && !form.getBacia().equals("") && !form.getBacia().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroBacia filtroBacia = new FiltroBacia();

			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.ID, form.getBacia()));

			Collection baciaEncontrada = fachada.pesquisar(filtroBacia, Bacia.class.getName());

			if(baciaEncontrada != null && !baciaEncontrada.isEmpty()){
				Bacia bacia = (Bacia) ((List) baciaEncontrada).get(0);
				especificacaoTramite.setBacia(bacia);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bacia");
			}
		}
		if(form.getSubBacia() != null && !form.getSubBacia().equals("")
						&& !form.getSubBacia().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			FiltroSubBacia filtroSubBacia = new FiltroSubBacia();

			filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.ID, form.getSubBacia()));

			Collection subBaciaEncontrada = fachada.pesquisar(filtroSubBacia, SubBacia.class.getName());

			if(subBaciaEncontrada != null && !subBaciaEncontrada.isEmpty()){
				SubBacia subBacia = (SubBacia) ((List) subBaciaEncontrada).get(0);
				especificacaoTramite.setSubBacia(subBacia);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Subbacia");
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

		// hora e data correntes
		especificacaoTramite.setUltimaAlteracao(new Date());

		// adiciona na coleção o tipo de solicitação especificado
		if(!colecaoTramiteEspecificacao.contains(especificacaoTramite)){
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
		form.setSistemaAbastecimento("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setDistritoOperacional("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setZonaAbastecimento("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setSetorAbastecimento("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setSistemaEsgoto("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setSubsistemaEsgoto("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setBacia("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		form.setSubBacia("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
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

	/**
	 * Retorna Coleção de Sistema de Abastecimento
	 */
	@SuppressWarnings("unchecked")
	private Collection<SistemaAbastecimento> getColecaoSistemaAbastecimento(){

		FiltroSistemaAbastecimento filtro = new FiltroSistemaAbastecimento();
		filtro.setCampoOrderBy(FiltroSistemaAbastecimento.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SistemaAbastecimento> colecao = fachada.pesquisar(filtro, SistemaAbastecimento.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Sistema de Abastecimento");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Distrito Operacional
	 */
	@SuppressWarnings("unchecked")
	private Collection<DistritoOperacional> getColecaoDistritoOperacional(String idLocalidade, String idBairro,
					String idSistemaAbastecimento){

		FiltroDistritoOperacional filtro = new FiltroDistritoOperacional();
		filtro.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(idLocalidade != null && !idLocalidade.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.LOCALIDADE_ID, idLocalidade));
		}

		if(idBairro != null && !idBairro.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.BAIRRO_ID, idBairro));
		}

		if(idSistemaAbastecimento != null && !idSistemaAbastecimento.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.SISTEMA_ABASTECIMENTO_ID, idSistemaAbastecimento));
		}

		Fachada fachada = Fachada.getInstancia();

		Collection<DistritoOperacional> colecao = fachada.pesquisar(filtro, DistritoOperacional.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Distrito Operacional");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Zona de Abastecimento
	 */
	@SuppressWarnings("unchecked")
	private Collection<ZonaAbastecimento> getColecaoZonaAbastecimento(String idLocalidade, String idSistemaAbastecimento,
					String idDistritoOperacional){

		FiltroZonaAbastecimento filtro = new FiltroZonaAbastecimento();
		filtro.setCampoOrderBy(FiltroZonaAbastecimento.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(idLocalidade != null && !idLocalidade.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.LOCALIDADE_ID, idLocalidade));
		}

		if(idSistemaAbastecimento != null && !idSistemaAbastecimento.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.SISTEMA_ABASTECIMENTO_ID, idSistemaAbastecimento));
		}

		if(idDistritoOperacional != null && !idDistritoOperacional.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL_ID, idDistritoOperacional));
		}

		Fachada fachada = Fachada.getInstancia();

		Collection<ZonaAbastecimento> colecao = fachada.pesquisar(filtro, ZonaAbastecimento.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Zona de Abastecimento");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Setor de Abastecimento
	 */
	@SuppressWarnings("unchecked")
	private Collection<SetorAbastecimento> getColecaoSetorAbastecimento(String idSistemaAbastecimento, String idZonaAbastecimento){

		FiltroSetorAbastecimento filtro = new FiltroSetorAbastecimento();
		filtro.setCampoOrderBy(FiltroSetorAbastecimento.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(idSistemaAbastecimento != null && !idSistemaAbastecimento.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.SISTEMA_ABASTECIMENTO_ID, idSistemaAbastecimento));
		}

		if(idZonaAbastecimento != null && !idZonaAbastecimento.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO_ID, idZonaAbastecimento));
		}

		Fachada fachada = Fachada.getInstancia();

		Collection<SetorAbastecimento> colecao = fachada.pesquisar(filtro, SetorAbastecimento.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Setor de Abastecimento");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Sistema de Esgoto
	 */
	@SuppressWarnings("unchecked")
	private Collection<SistemaEsgoto> getColecaoSistemaEsgoto(){

		FiltroSistemaEsgoto filtro = new FiltroSistemaEsgoto();
		filtro.setCampoOrderBy(FiltroSistemaEsgoto.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Fachada fachada = Fachada.getInstancia();

		Collection<SistemaEsgoto> colecao = fachada.pesquisar(filtro, SistemaEsgoto.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Sistema de Esgoto");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Subsistema de Esgoto
	 */
	@SuppressWarnings("unchecked")
	private Collection<SubsistemaEsgoto> getColecaoSubsistemaEsgoto(String idSistemaEsgoto){

		FiltroSubsistemaEsgoto filtro = new FiltroSubsistemaEsgoto();
		filtro.setCampoOrderBy(FiltroSubsistemaEsgoto.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(idSistemaEsgoto != null && !idSistemaEsgoto.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID, idSistemaEsgoto));
		}

		Fachada fachada = Fachada.getInstancia();

		Collection<SubsistemaEsgoto> colecao = fachada.pesquisar(filtro, SubsistemaEsgoto.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Subsistema de Esgoto");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de Bacia
	 */
	@SuppressWarnings("unchecked")
	private Collection<Bacia> getColecaoBacia(String idSistemaEsgoto, String idSubsistemaEsgoto){

		FiltroBacia filtro = new FiltroBacia();
		filtro.setCampoOrderBy(FiltroBacia.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(idSistemaEsgoto != null && !idSistemaEsgoto.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_SISTEMA_ESGOTO_ID, idSistemaEsgoto));
		}

		if(idSubsistemaEsgoto != null && !idSubsistemaEsgoto.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_ID, idSubsistemaEsgoto));
		}

		Fachada fachada = Fachada.getInstancia();

		Collection<Bacia> colecao = fachada.pesquisar(filtro, Bacia.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Bacia");
		}

		return colecao;
	}

	/**
	 * Retorna Coleção de SubBacia
	 */
	@SuppressWarnings("unchecked")
	private Collection<SubBacia> getColecaoSubBacia(String idBacia){

		FiltroSubBacia filtro = new FiltroSubBacia();
		filtro.setCampoOrderBy(FiltroSubBacia.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroSubBacia.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(idBacia != null && !idBacia.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroSubBacia.BACIA_ID, idBacia));
		}

		Fachada fachada = Fachada.getInstancia();

		Collection<SubBacia> colecao = fachada.pesquisar(filtro, SubBacia.class.getName());

		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Subbacia");
		}

		return colecao;
	}
}
