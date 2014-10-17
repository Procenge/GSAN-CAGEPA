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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import gcom.cadastro.dadocensitario.FiltroFonteDadosCensitario;
import gcom.cadastro.dadocensitario.FonteDadosCensitario;
import gcom.cadastro.dadocensitario.LocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.MunicipioDadosCensitario;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] Manter Dados Censit�rios
 * 
 * @author Anderson Italo
 * @date 11/02/2011
 */
public class AtualizarDadosCensitariosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarDadosCensitariosActionForm form = (AtualizarDadosCensitariosActionForm) actionForm;
		Usuario usuario = (Usuario) httpServletRequest.getSession(false).getAttribute("usuarioLogado");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Atualiza��o de dados censit�rios para localidade.
		if(sessao.getAttribute("atualizarLocalidadeDadosCensitario") != null){

			LocalidadeDadosCensitario localidadeDadosCensitario = (LocalidadeDadosCensitario) sessao
							.getAttribute("atualizarLocalidadeDadosCensitario");

			// [FS0002] - Verificar preenchimento dos campos
			if(form.getIdFonteDadosCensitarios() != null && !form.getIdFonteDadosCensitarios().equals("")){

				FonteDadosCensitario fonteDadosCensitario = new FonteDadosCensitario();

				FiltroFonteDadosCensitario filtroFonteDadosCensitario = new FiltroFonteDadosCensitario();
				filtroFonteDadosCensitario.adicionarParametro(new ParametroSimples(FiltroFonteDadosCensitario.ID, new Integer(form
								.getIdFonteDadosCensitarios())));

				Collection colecaoFonteDadosCensitarios = fachada.pesquisar(filtroFonteDadosCensitario, FonteDadosCensitario.class
								.getName());

				if(colecaoFonteDadosCensitarios != null && !colecaoFonteDadosCensitarios.isEmpty()){
					fonteDadosCensitario = (FonteDadosCensitario) Util.retonarObjetoDeColecao(colecaoFonteDadosCensitarios);
				}else{
					throw new ActionServletException("pesquisa.fontedadoscensitarios.inexistente");
				}

				localidadeDadosCensitario.setFonteDadosCensitario(fonteDadosCensitario);
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Fonte de Informa��o");
			}

			if(form.getPopulacaoUrbana() != null && !form.getPopulacaoUrbana().equals("")){

				localidadeDadosCensitario.setNumeroPopulacaoUrbana(new Integer(form.getPopulacaoUrbana()));
			}else{

				localidadeDadosCensitario.setNumeroPopulacaoUrbana(null);
			}

			if(form.getTaxaCrescimentoAnualPopulacaoUrbana() != null && !form.getTaxaCrescimentoAnualPopulacaoUrbana().equals("")){

				localidadeDadosCensitario.setTaxaAnualCrescimentoPopulacaoUrbana((new BigDecimal(form
								.getTaxaCrescimentoAnualPopulacaoUrbana().replace(",", "."))));
			}else{

				localidadeDadosCensitario.setTaxaAnualCrescimentoPopulacaoUrbana(null);
			}

			if(form.getTaxaOcupacionalPopulacaoUrbana() != null && !form.getTaxaOcupacionalPopulacaoUrbana().equals("")){

				localidadeDadosCensitario
								.setTaxaOcupacaoUrbana((new BigDecimal(form.getTaxaOcupacionalPopulacaoUrbana().replace(",", "."))));
			}else{

				localidadeDadosCensitario.setTaxaOcupacaoUrbana(null);
			}

			if(form.getPopulacaoRural() != null && !form.getPopulacaoRural().equals("")){

				localidadeDadosCensitario.setNumeroPopulacaoRural(new Integer(form.getPopulacaoRural()));
			}else{

				localidadeDadosCensitario.setNumeroPopulacaoRural(null);
			}

			if(form.getTaxaCrescimentoAnualPopulacaoRural() != null && !form.getTaxaCrescimentoAnualPopulacaoRural().equals("")){

				localidadeDadosCensitario.setTaxaCrescimentoPopulacaoRural((new BigDecimal(form.getTaxaCrescimentoAnualPopulacaoRural()
								.replace(",", "."))));
			}else{

				localidadeDadosCensitario.setTaxaCrescimentoPopulacaoRural(null);
			}

			if(form.getTaxaOcupacionalPopulacaoRural() != null && !form.getTaxaOcupacionalPopulacaoRural().equals("")){

				localidadeDadosCensitario.setTaxaOcupacaoRural((new BigDecimal(form.getTaxaOcupacionalPopulacaoRural().replace(",", "."))));
			}else{

				localidadeDadosCensitario.setTaxaOcupacaoRural(null);
			}

			localidadeDadosCensitario.setUltimaAlteracao(new Date());
			fachada.atualizarDadosCensitarios(localidadeDadosCensitario, usuario);

			// Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Dados Censit�rios da Localidade "
							+ localidadeDadosCensitario.getLocalidade().getDescricaoComId().toUpperCase() + " do M�s/Ano "
							+ form.getAnoMesReferencia() + " atualizado com sucesso.", "Realizar outra Manuten��o de Dados Censit�rios",
							"exibirFiltrarDadosCensitariosAction.do?menu=sim");

		}else if(sessao.getAttribute("atualizarMunicipioDadosCensitario") != null){

			// Atualiza��o dos dados censit�rios do munic�pio
			MunicipioDadosCensitario municipioDadosCensitario = (MunicipioDadosCensitario) sessao
							.getAttribute("atualizarMunicipioDadosCensitario");

			if(form.getIdFonteDadosCensitarios() != null && !form.getIdFonteDadosCensitarios().equals("")){

				FonteDadosCensitario fonteDadosCensitario = new FonteDadosCensitario();

				FiltroFonteDadosCensitario filtroFonteDadosCensitario = new FiltroFonteDadosCensitario();
				filtroFonteDadosCensitario.adicionarParametro(new ParametroSimples(FiltroFonteDadosCensitario.ID, new Integer(form
								.getIdFonteDadosCensitarios())));

				Collection colecaoFonteDadosCensitarios = fachada.pesquisar(filtroFonteDadosCensitario, FonteDadosCensitario.class
								.getName());

				if(colecaoFonteDadosCensitarios != null && !colecaoFonteDadosCensitarios.isEmpty()){
					fonteDadosCensitario = (FonteDadosCensitario) Util.retonarObjetoDeColecao(colecaoFonteDadosCensitarios);
				}else{
					throw new ActionServletException("pesquisa.fontedadoscensitarios.inexistente");
				}

				municipioDadosCensitario.setFonteDadosCensitario(fonteDadosCensitario);
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Fonte de Informa��o");
			}

			if(form.getPopulacaoUrbana() != null && !form.getPopulacaoUrbana().equals("")){

				municipioDadosCensitario.setNumeroPopulacaoUrbana(new Integer(form.getPopulacaoUrbana()));
			}else{

				municipioDadosCensitario.setNumeroPopulacaoUrbana(null);
			}

			if(form.getTaxaCrescimentoAnualPopulacaoUrbana() != null && !form.getTaxaCrescimentoAnualPopulacaoUrbana().equals("")){

				municipioDadosCensitario.setTaxaAnualCrescimentoPopulacaoUrbana((new BigDecimal(form
								.getTaxaCrescimentoAnualPopulacaoUrbana().replace(",", "."))));
			}else{

				municipioDadosCensitario.setTaxaAnualCrescimentoPopulacaoUrbana(null);
			}

			if(form.getTaxaOcupacionalPopulacaoUrbana() != null && !form.getTaxaOcupacionalPopulacaoUrbana().equals("")){

				municipioDadosCensitario
								.setTaxaOcupacaoUrbana((new BigDecimal(form.getTaxaOcupacionalPopulacaoUrbana().replace(",", "."))));
			}else{

				municipioDadosCensitario.setTaxaOcupacaoUrbana(null);
			}

			if(form.getPopulacaoRural() != null && !form.getPopulacaoRural().equals("")){

				municipioDadosCensitario.setNumeroPopulacaoRural(new Integer(form.getPopulacaoRural()));
			}else{

				municipioDadosCensitario.setNumeroPopulacaoRural(null);
			}

			if(form.getTaxaCrescimentoAnualPopulacaoRural() != null && !form.getTaxaCrescimentoAnualPopulacaoRural().equals("")){

				municipioDadosCensitario.setTaxaCrescimentoPopulacaoRural((new BigDecimal(form.getTaxaCrescimentoAnualPopulacaoRural()
								.replace(",", "."))));
			}else{

				municipioDadosCensitario.setTaxaCrescimentoPopulacaoRural(null);
			}

			if(form.getTaxaOcupacionalPopulacaoRural() != null && !form.getTaxaOcupacionalPopulacaoRural().equals("")){

				municipioDadosCensitario.setTaxaOcupacaoRural((new BigDecimal(form.getTaxaOcupacionalPopulacaoRural().replace(",", "."))));
			}else{

				municipioDadosCensitario.setTaxaOcupacaoRural(null);
			}

			municipioDadosCensitario.setUltimaAlteracao(new Date());

			fachada.atualizarDadosCensitarios(municipioDadosCensitario, usuario);

			// Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Dados Censit�rios do Munic�pio "
							+ municipioDadosCensitario.getMunicipio().getNomeComId().toUpperCase() + " do M�s/Ano "
							+ form.getAnoMesReferencia() + " atualizado com sucesso.", "Realizar outra Manuten��o de Dados Censit�rios",
							"exibirFiltrarDadosCensitariosAction.do?menu=sim");
		}

		return retorno;
	}
}
