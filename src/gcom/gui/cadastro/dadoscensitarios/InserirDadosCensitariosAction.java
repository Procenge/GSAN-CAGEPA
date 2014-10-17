/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.gui.cadastro.dadoscensitarios;

import gcom.cadastro.dadocensitario.FiltroFonteDadosCensitario;
import gcom.cadastro.dadocensitario.FiltroLocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.FiltroMunicipioDadosCensitario;
import gcom.cadastro.dadocensitario.FonteDadosCensitario;
import gcom.cadastro.dadocensitario.LocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.MunicipioDadosCensitario;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC1017] Inserir Dados Censitários
 * 
 * @author Anderson Italo
 * @date 08/02/2011
 */
public class InserirDadosCensitariosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirDadosCensitariosActionForm form = (InserirDadosCensitariosActionForm) actionForm;
		Integer codigoDadosCensitarioInserido = null;
		Usuario usuario = (Usuario) httpServletRequest.getSession(false).getAttribute("usuarioLogado");
		Fachada fachada = Fachada.getInstancia();

		// Inclusão de dados censitários para localidade.
		if(form.getIndicadorLocalidadeMunicipio().equals(ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE)){

			// 6. Sistema inclui os dados censitários na tabela LOCALIDADE_DADOS_CENSITARIOS
			LocalidadeDadosCensitario localidadeDadosCensitario = new LocalidadeDadosCensitario();

			// [FS0002] - Verificar preenchimento dos campos
			if(form.getCodigoLocalidade() != null && !form.getCodigoLocalidade().equals("")){
				Localidade localidade = new Localidade();

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(form.getCodigoLocalidade())));

				Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
					localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
				}else{
					throw new ActionServletException("atencao.localidade.inexistente");
				}

				localidadeDadosCensitario.setLocalidade(localidade);
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Localidade");
			}

			if(form.getAnoMesReferencia() != null && !form.getAnoMesReferencia().equals("")){

				if(!Util.validarMesAno(form.getAnoMesReferencia())){
					throw new ActionServletException("atencao.campo.invalido", null, "Mês/Ano de Referência");
				}

				localidadeDadosCensitario.setAnoMesReferencia(new Integer(Util.formatarMesAnoParaAnoMes(form.getAnoMesReferencia())));
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Mês/Ano de Referência");
			}

			// 4.1. Caso Localidade: se a combinação Código da localidade e
			// Referência já existir na tabela LOCALIDADE_DADOS_CENSITARIOS
			FiltroLocalidadeDadosCensitario filtroLocalidadeDadosCensitario = new FiltroLocalidadeDadosCensitario();
			filtroLocalidadeDadosCensitario.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadosCensitario.LOCALIDADE_ID,
							new Integer(form.getCodigoLocalidade())));
			filtroLocalidadeDadosCensitario.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadosCensitario.ANO_MES, new Integer(
							Util.formatarMesAnoParaAnoMes(form.getAnoMesReferencia()))));

			Collection colecaoLocalidadeDadosCensitarios = fachada.pesquisar(filtroLocalidadeDadosCensitario,
							LocalidadeDadosCensitario.class.getName());

			if(colecaoLocalidadeDadosCensitarios != null && !colecaoLocalidadeDadosCensitarios.isEmpty()){
				throw new ActionServletException("atencao.dados.censitarios.ja.cadastrado", null, "Localidade");
			}

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
				throw new ActionServletException("atencao.informe_campo", null, "Fonte de Informação");
			}

			if(form.getPopulacaoUrbana() != null && !form.getPopulacaoUrbana().equals("")){

				localidadeDadosCensitario.setNumeroPopulacaoUrbana(new Integer(form.getPopulacaoUrbana()));
			}

			if(form.getTaxaCrescimentoAnualPopulacaoUrbana() != null && !form.getTaxaCrescimentoAnualPopulacaoUrbana().equals("")){

				localidadeDadosCensitario.setTaxaAnualCrescimentoPopulacaoUrbana((new BigDecimal(form
								.getTaxaCrescimentoAnualPopulacaoUrbana().replace(",", "."))));
			}

			if(form.getTaxaOcupacionalPopulacaoUrbana() != null && !form.getTaxaOcupacionalPopulacaoUrbana().equals("")){

				localidadeDadosCensitario
								.setTaxaOcupacaoUrbana((new BigDecimal(form.getTaxaOcupacionalPopulacaoUrbana().replace(",", "."))));
			}

			if(form.getPopulacaoRural() != null && !form.getPopulacaoRural().equals("")){

				localidadeDadosCensitario.setNumeroPopulacaoRural(new Integer(form.getPopulacaoRural()));
			}

			if(form.getTaxaCrescimentoAnualPopulacaoRural() != null && !form.getTaxaCrescimentoAnualPopulacaoRural().equals("")){

				localidadeDadosCensitario.setTaxaCrescimentoPopulacaoRural((new BigDecimal(form.getTaxaCrescimentoAnualPopulacaoRural()
								.replace(",", "."))));
			}

			if(form.getTaxaOcupacionalPopulacaoRural() != null && !form.getTaxaOcupacionalPopulacaoRural().equals("")){

				localidadeDadosCensitario.setTaxaOcupacaoRural((new BigDecimal(form.getTaxaOcupacionalPopulacaoRural().replace(",", "."))));
			}

			localidadeDadosCensitario.setUltimaAlteracao(new Date());
			codigoDadosCensitarioInserido = (Integer) fachada.inserirLocalidadeDadosCensitario(localidadeDadosCensitario, usuario);

			if(codigoDadosCensitarioInserido == null || codigoDadosCensitarioInserido.intValue() == 0){
				throw new ActionServletException("erro.sistema");
			}else{

				montarPaginaSucesso(httpServletRequest, "Dados Censitários da Localidade "
								+ localidadeDadosCensitario.getLocalidade().getDescricaoComId().toUpperCase() + " do Mês/Ano "
								+ form.getAnoMesReferencia() + " inserido com sucesso.", "Inserir outro Dados Censitário",
								"exibirInserirDadosCensitariosAction.do?menu=sim",
								"exibirAtualizarDadosCensitariosAction.do?menu=sim&idRegistroAtualizacao=" + codigoDadosCensitarioInserido
												+ "&dadosLocalidade=true", "Atualizar Dados Censitário Inserido");
			}

		}else{

			// Inclusão de dados censitários para município.

			// 6. Sistema inclui os dados censitários na tabela MUNICIPIO_DADOS_CENSITARIOS
			MunicipioDadosCensitario municipioDadosCensitario = new MunicipioDadosCensitario();

			// [FS0002] - Verificar preenchimento dos campos
			if(form.getCodigoMunicipio() != null && !form.getCodigoMunicipio().equals("")){
				Municipio municipio = new Municipio();

				FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, new Integer(form.getCodigoMunicipio())));

				Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

				if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){
					municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
				}else{
					throw new ActionServletException("atencao.municipio.inexistente");
				}

				municipioDadosCensitario.setMunicipio(municipio);
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Municipio");
			}

			if(form.getAnoMesReferencia() != null && !form.getAnoMesReferencia().equals("")){

				if(!Util.validarMesAno(form.getAnoMesReferencia())){
					throw new ActionServletException("atencao.campo.invalido", null, "Mês/Ano de Referência");
				}

				municipioDadosCensitario.setAnoMesReferencia(new Integer(Util.formatarMesAnoParaAnoMes(form.getAnoMesReferencia())));
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Mês/Ano de Referência");
			}

			// 4.2. Caso Municipio: se a combinação Código do Municipio e
			// Referência já existir na tabela MUNICIPIO_DADOS_CENSITARIOS
			FiltroMunicipioDadosCensitario filtroMunicipioDadosCensitario = new FiltroMunicipioDadosCensitario();
			filtroMunicipioDadosCensitario.adicionarParametro(new ParametroSimples(FiltroMunicipioDadosCensitario.MUNICIPIO_ID,
							new Integer(form.getCodigoMunicipio())));
			filtroMunicipioDadosCensitario.adicionarParametro(new ParametroSimples(FiltroMunicipioDadosCensitario.ANO_MES, new Integer(Util
							.formatarMesAnoParaAnoMes(form.getAnoMesReferencia()))));

			Collection colecaoMunicipioDadosCensitarios = fachada.pesquisar(filtroMunicipioDadosCensitario, MunicipioDadosCensitario.class
							.getName());

			if(colecaoMunicipioDadosCensitarios != null && !colecaoMunicipioDadosCensitarios.isEmpty()){
				throw new ActionServletException("atencao.dados.censitarios.ja.cadastrado", null, "Município");
			}

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
				throw new ActionServletException("atencao.informe_campo", null, "Fonte de Informação");
			}

			if(form.getPopulacaoUrbana() != null && !form.getPopulacaoUrbana().equals("")){

				municipioDadosCensitario.setNumeroPopulacaoUrbana(new Integer(form.getPopulacaoUrbana()));
			}

			if(form.getTaxaCrescimentoAnualPopulacaoUrbana() != null && !form.getTaxaCrescimentoAnualPopulacaoUrbana().equals("")){

				municipioDadosCensitario.setTaxaAnualCrescimentoPopulacaoUrbana((new BigDecimal(form
								.getTaxaCrescimentoAnualPopulacaoUrbana().replace(",", "."))));
			}

			if(form.getTaxaOcupacionalPopulacaoUrbana() != null && !form.getTaxaOcupacionalPopulacaoUrbana().equals("")){

				municipioDadosCensitario
								.setTaxaOcupacaoUrbana((new BigDecimal(form.getTaxaOcupacionalPopulacaoUrbana().replace(",", "."))));
			}

			if(form.getPopulacaoRural() != null && !form.getPopulacaoRural().equals("")){

				municipioDadosCensitario.setNumeroPopulacaoRural(new Integer(form.getPopulacaoRural()));
			}

			if(form.getTaxaCrescimentoAnualPopulacaoRural() != null && !form.getTaxaCrescimentoAnualPopulacaoRural().equals("")){

				municipioDadosCensitario.setTaxaCrescimentoPopulacaoRural((new BigDecimal(form.getTaxaCrescimentoAnualPopulacaoRural()
								.replace(",", "."))));
			}

			if(form.getTaxaOcupacionalPopulacaoRural() != null && !form.getTaxaOcupacionalPopulacaoRural().equals("")){

				municipioDadosCensitario.setTaxaOcupacaoRural((new BigDecimal(form.getTaxaOcupacionalPopulacaoRural().replace(",", "."))));
			}

			municipioDadosCensitario.setUltimaAlteracao(new Date());

			codigoDadosCensitarioInserido = (Integer) fachada.inserirMunicipioDadosCensitario(municipioDadosCensitario, usuario);

			if(codigoDadosCensitarioInserido == null || codigoDadosCensitarioInserido.intValue() == 0){
				throw new ActionServletException("erro.sistema");
			}else{

				montarPaginaSucesso(httpServletRequest, "Dados Censitários do Município "
								+ municipioDadosCensitario.getMunicipio().getNomeComId().toUpperCase() + " do Mês/Ano "
								+ form.getAnoMesReferencia() + " inserido com sucesso.", "Inserir outro Dados Censitário",
								"exibirInserirDadosCensitariosAction.do?menu=sim",
								"exibirAtualizarDadosCensitariosAction.do?menu=sim&idRegistroAtualizacao=" + codigoDadosCensitarioInserido
												+ "&dadosLocalidade=false", "Atualizar Dados Censitário Inserido");
			}
		}

		return retorno;
	}
}
