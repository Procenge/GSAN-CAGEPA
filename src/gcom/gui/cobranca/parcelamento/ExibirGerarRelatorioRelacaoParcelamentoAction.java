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

package gcom.gui.cobranca.parcelamento;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cobranca.parcelamento.FiltroParcelamentoMotivoDesfazer;
import gcom.cobranca.parcelamento.FiltroParcelamentoSituacao;
import gcom.cobranca.parcelamento.ParcelamentoMotivoDesfazer;
import gcom.cobranca.parcelamento.ParcelamentoSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0594] Gerar Relação de Parcelamento
 * 
 * @author Ana Maria
 * @date 30/05/2007
 */
public class ExibirGerarRelatorioRelacaoParcelamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioRelacaoParcelamento");

		Fachada fachada = Fachada.getInstancia();

		GerarRelatorioRelacaoParcelamentoActionForm form = (GerarRelatorioRelacaoParcelamentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		// Gerência Regional
		if(sessao.getAttribute("gerenciasRegionais") == null){
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection gerenciasRegionais = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(gerenciasRegionais == null || gerenciasRegionais.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Gerência Regional");
			}else{
				sessao.setAttribute("gerenciasRegionais", gerenciasRegionais);
			}
		}

		// Unidade de Negócio
		if(sessao.getAttribute("colecaoUnidadeNegocio") == null){
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Unidade de Negócio");
			}else{
				sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
			}
		}

		if(sessao.getAttribute("colecaoSituacaoParcelamento") == null){
			// Pesquisando Situação do parcelamento
			FiltroParcelamentoSituacao filtroParcelamentoSituacao = new FiltroParcelamentoSituacao();

			Collection colecaoSituacaoParcelamento = Fachada.getInstancia().pesquisar(filtroParcelamentoSituacao,
							ParcelamentoSituacao.class.getName());

			if(colecaoSituacaoParcelamento == null || colecaoSituacaoParcelamento.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Situação do Parcelamento");
			}

			sessao.setAttribute("colecaoSituacaoParcelamento", colecaoSituacaoParcelamento);
		}

		// Pesquisando local de instalação
		FiltroParcelamentoMotivoDesfazer filtroParcelamentoMotivoDesfazer = new FiltroParcelamentoMotivoDesfazer();

		Collection colecaoMotivoDesfazimento = Fachada.getInstancia().pesquisar(filtroParcelamentoMotivoDesfazer,
						ParcelamentoMotivoDesfazer.class.getName());

		if(colecaoMotivoDesfazimento == null || colecaoMotivoDesfazimento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Situação do Parcelamento");
		}

		httpServletRequest.setAttribute("colecaoMotivoDesfazimento", colecaoMotivoDesfazimento);

		if(httpServletRequest.getParameter("idSituacaoParcelamento") != null){
			Integer idSituacaoParcelamento = new Integer(httpServletRequest.getParameter("idSituacaoParcelamento"));
			if(idSituacaoParcelamento.equals(ParcelamentoSituacao.DESFEITO)){
				httpServletRequest.setAttribute("Desfeito", "sim");
			}/*
			 * else{
			 * form.setIdsMotivoDesfazimento(""+ConstantesSistema.NUMERO_NAO_INFORMADO);
			 * }
			 */
		}

		String idLocalidade = form.getIdLocalidade();

		if(idLocalidade != null && !idLocalidade.trim().equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
				Localidade localidade = (Localidade) colecaoLocalidade.iterator().next();

				form.setIdLocalidade(idLocalidade);
				form.setDescricaoLocalidade(localidade.getDescricao());

			}else{
				form.setIdLocalidade("");
				form.setDescricaoLocalidade("LOCALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("localidadeInexistente", true);
			}
		}else{
			form.setDescricaoLocalidade("");
		}

		String codigoSetorComercial = form.getIdSetorComercial();

		if(codigoSetorComercial != null && !codigoSetorComercial.trim().equals("")){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));

			filtroSetorComercial
							.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercial));

			Collection colecaosetoComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaosetoComercial != null && !colecaosetoComercial.isEmpty()){
				SetorComercial setorComercial = (SetorComercial) colecaosetoComercial.iterator().next();

				form.setIdSetorComercial(codigoSetorComercial);
				form.setDescricaoSetorComercial(setorComercial.getDescricao());

			}else{
				form.setIdSetorComercial("");
				form.setDescricaoSetorComercial("SETOR COMERCIAL INEXISTENTE");
				httpServletRequest.setAttribute("setorComercialInexistente", true);
			}
		}else{
			form.setDescricaoSetorComercial("");
		}

		return retorno;

	}
}