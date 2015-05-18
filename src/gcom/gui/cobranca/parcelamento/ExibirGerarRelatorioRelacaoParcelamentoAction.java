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

package gcom.gui.cobranca.parcelamento;

import gcom.cadastro.localidade.*;
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
 * [UC0594] Gerar Rela��o de Parcelamento
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

		// Ger�ncia Regional
		if(sessao.getAttribute("gerenciasRegionais") == null){
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection gerenciasRegionais = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(gerenciasRegionais == null || gerenciasRegionais.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Ger�ncia Regional");
			}else{
				sessao.setAttribute("gerenciasRegionais", gerenciasRegionais);
			}
		}

		// Unidade de Neg�cio
		if(sessao.getAttribute("colecaoUnidadeNegocio") == null){
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Unidade de Neg�cio");
			}else{
				sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
			}
		}

		// Situa��o Parcelamento
		if(sessao.getAttribute("colecaoSituacaoParcelamento") == null){
			// Pesquisando Situa��o do parcelamento
			FiltroParcelamentoSituacao filtroParcelamentoSituacao = new FiltroParcelamentoSituacao();

			Collection colecaoSituacaoParcelamento = Fachada.getInstancia().pesquisar(filtroParcelamentoSituacao,
							ParcelamentoSituacao.class.getName());

			if(colecaoSituacaoParcelamento == null || colecaoSituacaoParcelamento.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Situa��o do Parcelamento");
			}

			sessao.setAttribute("colecaoSituacaoParcelamento", colecaoSituacaoParcelamento);
		}

		// Pesquisando local de instala��o
		FiltroParcelamentoMotivoDesfazer filtroParcelamentoMotivoDesfazer = new FiltroParcelamentoMotivoDesfazer();

		Collection colecaoMotivoDesfazimento = Fachada.getInstancia().pesquisar(filtroParcelamentoMotivoDesfazer,
						ParcelamentoMotivoDesfazer.class.getName());

		if(colecaoMotivoDesfazimento == null || colecaoMotivoDesfazimento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Situa��o do Parcelamento");
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

		// Localidade
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

		// Setor Comercial
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