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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.faturamento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroQualidadeAgua;
import gcom.faturamento.QualidadeAgua;
import gcom.gui.GcomAction;
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
 * [UC0597] ATUALIZAR Qualidade de Agua
 * 
 * @author Marcio Roberto
 * @date 13/02/2007
 * @author eduardo henrique
 * @date 17/07/2008
 *       adição de novos atributos contidos no [UC0597]
 */

public class ExibirAtualizarQualidadeAguaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarQualidadeAgua");

		AtualizarQualidadeAguaActionForm form = (AtualizarQualidadeAguaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String idQualidadeAgua = httpServletRequest.getParameter("idRegistroAtualizacao");

		if(idQualidadeAgua == null){
			if(httpServletRequest.getAttribute("idRegistroAtualizacao") == null){
				idQualidadeAgua = (String) sessao.getAttribute("idRegistroAtualizacao");
			}else{
				idQualidadeAgua = (String) httpServletRequest.getAttribute("idRegistroAtualizacao").toString();
			}

		}else{
			sessao.setAttribute("i", true);
		}

		QualidadeAgua qualidadeAgua = null;

		String idLocalidade = null;

		String codigoSetor = null;

		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null && !objetoConsulta.equals("")){

			// Validamos o cliente
			if(form.getIdLocalidade() != null && !form.getIdLocalidade().trim().equals("")){
				FiltroLocalidade filtro = new FiltroLocalidade();

				filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, form.getIdLocalidade()));

				Collection colLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());

				if(colLocalidade == null || !colLocalidade.iterator().hasNext()){
					// O cliente não existe
					form.setIdLocalidade("");
					form.setLocalidadeDescricao("Localidade inexistente");
					httpServletRequest.setAttribute("localidadeEncontrada", "exception");

				}else{
					Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colLocalidade);
					form.setIdLocalidade(localidade.getId().toString());

					form.setLocalidadeDescricao(localidade.getDescricao());
				}
			}

			if(form.getIdSetorComercial() != null && !form.getIdSetorComercial().trim().equals("")){
				// Validamos o imovel
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, form
								.getIdSetorComercial()));

				Collection colSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colSetorComercial == null || colSetorComercial.isEmpty()){
					// O Imovel não existe
					form.setIdSetorComercial("");
					form.setSetorComercialDescricao("Setor Comercial inexistente");

					httpServletRequest.setAttribute("codigoSetorComercialEncontrado", "exception");

				}else{
					SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colSetorComercial);

					form.setIdSetorComercial(setorComercial.getCodigo() + "");

					form.setSetorComercialDescricao(setorComercial.getDescricao());
				}
			}
		}else{

			if(idQualidadeAgua != null && !idQualidadeAgua.trim().equals("") && Integer.parseInt(idQualidadeAgua) > 0){

				FiltroQualidadeAgua filtroQualidadeAgua = new FiltroQualidadeAgua();
				filtroQualidadeAgua.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroQualidadeAgua.adicionarCaminhoParaCarregamentoEntidade("setorComercial");

				filtroQualidadeAgua.adicionarParametro(new ParametroSimples(FiltroQualidadeAgua.ID, idQualidadeAgua));
				Collection colecaoQualidadeAgua = fachada.pesquisar(filtroQualidadeAgua, QualidadeAgua.class.getName());
				if(colecaoQualidadeAgua != null && !colecaoQualidadeAgua.isEmpty()){
					qualidadeAgua = (QualidadeAgua) Util.retonarObjetoDeColecao(colecaoQualidadeAgua);
				}
			}

			if(qualidadeAgua != null){
				if(qualidadeAgua.getLocalidade() != null && (idLocalidade == null || idLocalidade.trim().equals(""))){

					if(qualidadeAgua.getLocalidade() != null && !qualidadeAgua.getLocalidade().getId().toString().trim().equals("")){

						idLocalidade = qualidadeAgua.getLocalidade().getId().toString();

						FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

						filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

						Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

						Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

						form.setIdLocalidade(localidade.getId().toString());

						form.setLocalidadeDescricao(localidade.getDescricao());
					}
				}

				if(qualidadeAgua.getSetorComercial() != null && (codigoSetor == null || codigoSetor.trim().equals(""))){

					FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, qualidadeAgua.getSetorComercial()
									.getId().toString()));

					Collection colSetor = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

					SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colSetor);
					form.setIdSetorComercial(setorComercial.getCodigo() + "");
					form.setSetorComercialDescricao(setorComercial.getDescricao());

				}
				form.setIdQualidadeAgua(idQualidadeAgua);

				form.setFonteCaptacao(qualidadeAgua.getDescricaoFonteCaptacao());

				form.setReferencia(Util.formatarAnoMesParaMesAno(qualidadeAgua.getAnoMesReferencia()));

				SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

				if(qualidadeAgua.getAnoMesReferencia().intValue() >= sistemaParametro.getAnoMesFaturamento().intValue()){
					sessao.removeAttribute("desabilitaCampos");
				}else{
					sessao.setAttribute("desabilitaCampos", "S");
				}
				// dados do mes
				if(qualidadeAgua.getNumeroAmostrasRealizadasTurbidez() != null){
					form
									.setNumeroAmostrasRealizadasTurbidez(String.valueOf(qualidadeAgua.getNumeroAmostrasRealizadasTurbidez()
													.intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasRealizadasCor() != null){
					form.setNumeroAmostrasRealizadasCor(String.valueOf(qualidadeAgua.getNumeroAmostrasRealizadasCor().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasRealizadasCloro() != null){
					form.setNumeroAmostrasRealizadasCloro(String.valueOf(qualidadeAgua.getNumeroAmostrasRealizadasCloro().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasRealizadasPH() != null){
					form.setNumeroAmostrasRealizadasPH(String.valueOf(qualidadeAgua.getNumeroAmostrasRealizadasPH().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasRealizadasFluor() != null){
					form.setNumeroAmostrasRealizadasFluor(String.valueOf(qualidadeAgua.getNumeroAmostrasRealizadasFluor().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais() != null){
					form.setNumeroAmostrasRealizadasColiformesTotais(String.valueOf(qualidadeAgua
									.getNumeroAmostrasRealizadasColiformesTotais().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes() != null){
					form.setNumeroAmostrasRealizadasColiformesTermotolerantes(String.valueOf(qualidadeAgua
									.getNumeroAmostrasRealizadasColiformesTermotolerantes().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasConformesTurbidez() != null){
					form.setNumeroAmostrasConformesTurbidez(String.valueOf(qualidadeAgua.getNumeroAmostrasConformesTurbidez().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasConformesCor() != null){
					form.setNumeroAmostrasConformesCor(String.valueOf(qualidadeAgua.getNumeroAmostrasConformesCor().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasConformesCloro() != null){
					form.setNumeroAmostrasConformesCloro(String.valueOf(qualidadeAgua.getNumeroAmostrasConformesCloro().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasConformesPH() != null){
					form.setNumeroAmostrasConformesPH(String.valueOf(qualidadeAgua.getNumeroAmostrasConformesPH().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasConformesFluor() != null){
					form.setNumeroAmostrasConformesFluor(String.valueOf(qualidadeAgua.getNumeroAmostrasConformesFluor().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasConformesColiformesTotais() != null){
					form.setNumeroAmostrasConformesColiformesTotais(String.valueOf(qualidadeAgua
									.getNumeroAmostrasConformesColiformesTotais().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes() != null){
					form.setNumeroAmostrasConformesColiformesTermotolerantes(String.valueOf(qualidadeAgua
									.getNumeroAmostrasConformesColiformesTermotolerantes().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasMediaTurbidez() != null){
					form.setNumeroAmostrasMediaTurbidez(String.valueOf(qualidadeAgua.getNumeroAmostrasMediaTurbidez().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasMediaCor() != null){
					form.setNumeroAmostrasMediaCor(String.valueOf(qualidadeAgua.getNumeroAmostrasMediaCor().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasMediaCloro() != null){
					form.setNumeroAmostrasMediaCloro(String.valueOf(qualidadeAgua.getNumeroAmostrasMediaCloro().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasMediaPH() != null){
					form.setNumeroAmostrasMediaPH(String.valueOf(qualidadeAgua.getNumeroAmostrasMediaPH().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasMediaFluor() != null){
					form.setNumeroAmostrasMediaFluor(String.valueOf(qualidadeAgua.getNumeroAmostrasMediaFluor().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasMediaColiformesTotais() != null){
					form.setNumeroAmostrasMediaColiformesTotais(String.valueOf(qualidadeAgua.getNumeroAmostrasMediaColiformesTotais()
									.intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasMediaColiformesTermotolerantes() != null){
					form.setNumeroAmostrasMediaColiformesTermotolerantes(String.valueOf(qualidadeAgua
									.getNumeroAmostrasMediaColiformesTermotolerantes().intValue()));
				}

				if(qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas() != null){
					form.setDescricaoConclusaoAnalisesRealizadas(qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas().toString());
				}

				if(qualidadeAgua.getDescricaoPadraoTurbidez() != null && !qualidadeAgua.getDescricaoPadraoTurbidez().equals("")){

					form.setPadraoTurbidez(qualidadeAgua.getDescricaoPadraoTurbidez());

				}else{
					form.setPadraoTurbidez("");
				}

				if(qualidadeAgua.getDescricaoPadraoCor() != null && !qualidadeAgua.getDescricaoPadraoCor().equals("")){

					form.setPadraoCor(qualidadeAgua.getDescricaoPadraoCor());
				}else{
					form.setPadraoCor("");
				}

				if(qualidadeAgua.getDescricaoPadraoCloro() != null && !qualidadeAgua.getDescricaoPadraoCloro().equals("")){

					form.setPadraoCloro(qualidadeAgua.getDescricaoPadraoCloro());
				}else{
					form.setPadraoCloro("");
				}

				if(qualidadeAgua.getDescricaoPadraoPh() != null && !qualidadeAgua.getDescricaoPadraoPh().equals("")){

					form.setPadraoPH(qualidadeAgua.getDescricaoPadraoPh());
				}else{
					form.setPadraoPH("");
				}

				if(qualidadeAgua.getDescricaoPadraoFluor() != null && !qualidadeAgua.getDescricaoPadraoFluor().equals("")){

					form.setPadraoFluor(qualidadeAgua.getDescricaoPadraoFluor());
				}else{
					form.setPadraoFluor("");
				}

				if(qualidadeAgua.getDescricaoPadraoColiformesTotais() != null
								&& !qualidadeAgua.getDescricaoPadraoColiformesTotais().equals("")){

					form.setPadraoColiformesTotais(qualidadeAgua.getDescricaoPadraoColiformesTotais());
				}else{
					form.setPadraoColiformesTotais("");
				}

				if(qualidadeAgua.getDescricaoPadraoColiformesTermoto() != null
								&& !qualidadeAgua.getDescricaoPadraoColiformesTermoto().equals("")){

					form.setPadraoColiformesTermotolerantes(qualidadeAgua.getDescricaoPadraoColiformesTermoto());
				}else{
					form.setPadraoColiformesTermotolerantes("");
				}

				if(qualidadeAgua.getNumeroAmostrasExigidasTurbidez() != null){
					form.setNumeroAmostrasExigidasTurbidez(String.valueOf(qualidadeAgua.getNumeroAmostrasExigidasTurbidez().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasExigidasCor() != null){
					form.setNumeroAmostrasExigidasCor(String.valueOf(qualidadeAgua.getNumeroAmostrasExigidasCor().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasExigidasCloro() != null){
					form.setNumeroAmostrasExigidasCloro(String.valueOf(qualidadeAgua.getNumeroAmostrasExigidasCloro().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasExigidasPh() != null){
					form.setNumeroAmostrasExigidasPH(String.valueOf(qualidadeAgua.getNumeroAmostrasExigidasPh().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasExigidasFluor() != null){
					form.setNumeroAmostrasExigidasFluor(String.valueOf(qualidadeAgua.getNumeroAmostrasExigidasFluor().intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasExigidasColiformesTotais() != null){
					form.setNumeroAmostrasExigidasColiformesTotais(String.valueOf(qualidadeAgua.getNumeroAmostrasExigidasColiformesTotais()
									.intValue()));
				}

				if(qualidadeAgua.getNumeroAmostrasExigidasColiformesTermotolerantes() != null){
					form.setNumeroAmostrasExigidasColiformesTermotolerantes(String.valueOf(qualidadeAgua
									.getNumeroAmostrasExigidasColiformesTermotolerantes().intValue()));
				}else{
					form.setNumeroAmostrasExigidasColiformesTermotolerantes("");
				}

			}

			sessao.setAttribute("qualidadeAgua", qualidadeAgua);

			if(sessao.getAttribute("colecaoQualidadeAgua") != null){
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/filtrarQualidadeAguaAction.do");
			}else{
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarQualidadeAguaAction.do");
			}

		}

		return retorno;
	}

}
