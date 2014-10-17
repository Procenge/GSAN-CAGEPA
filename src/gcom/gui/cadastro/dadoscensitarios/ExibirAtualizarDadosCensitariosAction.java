/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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

package gcom.gui.cadastro.dadoscensitarios;

import gcom.cadastro.dadocensitario.FiltroFonteDadosCensitario;
import gcom.cadastro.dadocensitario.FiltroLocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.FiltroMunicipioDadosCensitario;
import gcom.cadastro.dadocensitario.FonteDadosCensitario;
import gcom.cadastro.dadocensitario.LocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.MunicipioDadosCensitario;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * [UC0053] Manter Dados Censit�rios
 * [SB0001 � Atualizar Dados Censit�rios]
 * 
 * @author Anderson Italo
 * @date 11/02/2011
 */
public class ExibirAtualizarDadosCensitariosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("dadosCensitariosAtualizar");

		AtualizarDadosCensitariosActionForm form = (AtualizarDadosCensitariosActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String id = null;
		String dadosLocalidade = null;

		if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
			id = httpServletRequest.getParameter("idRegistroAtualizacao");
		}else{
			id = ((Integer) sessao.getAttribute("idRegistroAtualizacao")).toString();
		}

		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		if(httpServletRequest.getParameter("dadosLocalidade") != null){

			dadosLocalidade = httpServletRequest.getParameter("dadosLocalidade");
		}else{

			if(sessao.getAttribute("dadosLocalidade") != null){

				dadosLocalidade = (sessao.getAttribute("dadosLocalidade")).toString();
			}
		}

		if(httpServletRequest.getParameter("desfazerLocalidadeOuMunicipio") != null){

			if(httpServletRequest.getParameter("desfazerLocalidadeOuMunicipio").toString().equals(
							ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE)){

				dadosLocalidade = "true";
			}else{

				dadosLocalidade = "false";
			}
		}

		if(dadosLocalidade != null && !dadosLocalidade.equals("") && dadosLocalidade.equals("true")){

			LocalidadeDadosCensitario localidadeDadosCensitario = new LocalidadeDadosCensitario();

			if(id != null && !id.trim().equals("") && Integer.parseInt(id) > 0){

				FiltroLocalidadeDadosCensitario filtro = new FiltroLocalidadeDadosCensitario();

				filtro.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadosCensitario.ID, id));
				filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidadeDadosCensitario.LOCALIDADE);
				filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidadeDadosCensitario.FONTE_DADOS_CENSITARIO);

				Collection colecaoLocalidadeDadosCensitarios = fachada.pesquisar(filtro, LocalidadeDadosCensitario.class.getName());

				if(colecaoLocalidadeDadosCensitarios != null && !colecaoLocalidadeDadosCensitarios.isEmpty()){

					localidadeDadosCensitario = (LocalidadeDadosCensitario) Util.retonarObjetoDeColecao(colecaoLocalidadeDadosCensitarios);
				}

				FiltroFonteDadosCensitario filtroFonteDadosCensitario = new FiltroFonteDadosCensitario();
				filtroFonteDadosCensitario.setCampoOrderBy(FiltroFonteDadosCensitario.DESCRICAO);

				Collection colecaoFonteDadosCensitarios = fachada.pesquisar(filtroFonteDadosCensitario, FonteDadosCensitario.class
								.getName());

				if(colecaoFonteDadosCensitarios != null && !colecaoFonteDadosCensitarios.isEmpty()){
					httpServletRequest.setAttribute("colecaoFonteDadosCensitarios", colecaoFonteDadosCensitarios);
				}else{
					if(colecaoFonteDadosCensitarios == null || colecaoFonteDadosCensitarios.isEmpty()){
						throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "FONTE_DADOS_CENSITARIOS");
					}
				}

				form.setIndicadorLocalidadeMunicipio(ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE);
				form.setId(localidadeDadosCensitario.getId().toString());
				form.setLocalidade(localidadeDadosCensitario.getLocalidade().getDescricaoComId());
				form.setAnoMesReferencia(Util.formatarAnoMesParaMesAno(localidadeDadosCensitario.getAnoMesReferencia()));
				form.setIdFonteDadosCensitarios(localidadeDadosCensitario.getFonteDadosCensitario().getId().toString());

				if(localidadeDadosCensitario.getNumeroPopulacaoUrbana() != null){
					form.setPopulacaoUrbana(localidadeDadosCensitario.getNumeroPopulacaoUrbana().toString());
				}

				if(localidadeDadosCensitario.getTaxaAnualCrescimentoPopulacaoUrbana() != null){
					form.setTaxaCrescimentoAnualPopulacaoUrbana(Util.formataBigDecimal(localidadeDadosCensitario
									.getTaxaAnualCrescimentoPopulacaoUrbana(), 2, false));
				}

				if(localidadeDadosCensitario.getTaxaOcupacaoUrbana() != null){
					form.setTaxaOcupacionalPopulacaoUrbana(Util.formataBigDecimal(localidadeDadosCensitario.getTaxaOcupacaoUrbana(), 2,
									false));
				}

				if(localidadeDadosCensitario.getNumeroPopulacaoRural() != null){
					form.setPopulacaoRural(localidadeDadosCensitario.getNumeroPopulacaoRural().toString());
				}

				if(localidadeDadosCensitario.getTaxaCrescimentoPopulacaoRural() != null){
					form.setTaxaCrescimentoAnualPopulacaoRural(Util.formataBigDecimal(localidadeDadosCensitario
									.getTaxaCrescimentoPopulacaoRural(), 2, false));
				}

				if(localidadeDadosCensitario.getTaxaOcupacaoRural() != null){
					form.setTaxaOcupacionalPopulacaoRural(Util
									.formataBigDecimal(localidadeDadosCensitario.getTaxaOcupacaoRural(), 2, false));
				}

				sessao.setAttribute("atualizarLocalidadeDadosCensitario", localidadeDadosCensitario);

				if(sessao.getAttribute("colecaoLocalidadeDadosCensitarios") != null){
					sessao.setAttribute("caminhoRetornoVoltar", "/gsan/filtrarDadosCensitariosAction.do");
				}else{
					sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarDadosCensitariosAction.do");
				}

			}
		}
		if(dadosLocalidade != null && !dadosLocalidade.equals("") && dadosLocalidade.equals("false")){

			MunicipioDadosCensitario municipioDadosCensitario = new MunicipioDadosCensitario();

			if(id != null && !id.trim().equals("") && Integer.parseInt(id) > 0){

				FiltroMunicipioDadosCensitario filtro = new FiltroMunicipioDadosCensitario();

				filtro.adicionarParametro(new ParametroSimples(FiltroMunicipioDadosCensitario.ID, id));
				filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroMunicipioDadosCensitario.MUNICIPIO);
				filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroMunicipioDadosCensitario.FONTE_DADOS_CENSITARIO);

				Collection colecaoMunicipioDadosCensitarios = fachada.pesquisar(filtro, MunicipioDadosCensitario.class.getName());

				if(colecaoMunicipioDadosCensitarios != null && !colecaoMunicipioDadosCensitarios.isEmpty()){

					municipioDadosCensitario = (MunicipioDadosCensitario) Util.retonarObjetoDeColecao(colecaoMunicipioDadosCensitarios);
				}

				FiltroFonteDadosCensitario filtroFonteDadosCensitario = new FiltroFonteDadosCensitario();
				filtroFonteDadosCensitario.setCampoOrderBy(FiltroFonteDadosCensitario.DESCRICAO);

				Collection colecaoFonteDadosCensitarios = fachada.pesquisar(filtroFonteDadosCensitario, FonteDadosCensitario.class
								.getName());

				if(colecaoFonteDadosCensitarios != null && !colecaoFonteDadosCensitarios.isEmpty()){
					httpServletRequest.setAttribute("colecaoFonteDadosCensitarios", colecaoFonteDadosCensitarios);
				}else{
					if(colecaoFonteDadosCensitarios == null || colecaoFonteDadosCensitarios.isEmpty()){
						throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "FONTE_DADOS_CENSITARIOS");
					}
				}

				form.setIndicadorLocalidadeMunicipio(ConstantesSistema.DADOS_CENSITARIOS_MUNICIPIO);
				form.setId(municipioDadosCensitario.getId().toString());
				form.setMunicipio(municipioDadosCensitario.getMunicipio().getNomeComId());
				form.setAnoMesReferencia(Util.formatarAnoMesParaMesAno(municipioDadosCensitario.getAnoMesReferencia()));
				form.setIdFonteDadosCensitarios(municipioDadosCensitario.getFonteDadosCensitario().getId().toString());

				if(municipioDadosCensitario.getNumeroPopulacaoUrbana() != null){
					form.setPopulacaoUrbana(municipioDadosCensitario.getNumeroPopulacaoUrbana().toString());
				}

				if(municipioDadosCensitario.getTaxaAnualCrescimentoPopulacaoUrbana() != null){
					form.setTaxaCrescimentoAnualPopulacaoUrbana(Util.formataBigDecimal(municipioDadosCensitario
									.getTaxaAnualCrescimentoPopulacaoUrbana(), 2, false));
				}

				if(municipioDadosCensitario.getTaxaOcupacaoUrbana() != null){
					form.setTaxaOcupacionalPopulacaoUrbana(Util.formataBigDecimal(municipioDadosCensitario.getTaxaOcupacaoUrbana(), 2,
									false));
				}

				if(municipioDadosCensitario.getNumeroPopulacaoRural() != null){
					form.setPopulacaoRural(municipioDadosCensitario.getNumeroPopulacaoRural().toString());
				}

				if(municipioDadosCensitario.getTaxaCrescimentoPopulacaoRural() != null){
					form.setTaxaCrescimentoAnualPopulacaoRural(Util.formataBigDecimal(municipioDadosCensitario
									.getTaxaCrescimentoPopulacaoRural(), 2, false));
				}

				if(municipioDadosCensitario.getTaxaOcupacaoRural() != null){
					form
									.setTaxaOcupacionalPopulacaoRural(Util.formataBigDecimal(municipioDadosCensitario
													.getTaxaOcupacaoRural(), 2, false));
				}

				sessao.setAttribute("atualizarMunicipioDadosCensitario", municipioDadosCensitario);

				if(sessao.getAttribute("colecaoMunicipioDadosCensitarios") != null){
					sessao.setAttribute("caminhoRetornoVoltar", "/gsan/filtrarDadosCensitariosAction.do");
				}else{
					sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarDadosCensitariosAction.do");
				}

			}
		}

		return retorno;
	}
}