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

package gcom.gui.relatorio.micromedicao;

import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.procenge.geradorrelatorio.impl.validador.RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl;

/**
 * [UC3073] Gerar Relatório de Consumidores com Ocorrência de Leituras
 * 
 * @author Carlos Chrystian
 * @date 19/09/2012
 */

public class ExibirRelatorioConsumidoresOcorrenciaLeiturasAction
				extends GcomAction {

	/*
	 * Constantes dos parâmetros do formulário, caso precise passar valores para eles.
	 */
	private static final String NOME_LOCALIDADE = "nomeLocalidade";

	private static final String NOME_LOCALIDADE_VINCULADA = "nomeLocalidadeVinculada";

	private static final String NOME_SETOR_COMERCIAL = "nomeSetorComercial";

	/*
	 * Constantes que indicam cada campo de pesquisa
	 */
	private static final Integer LOCALIDADE_VINCULADA = Integer.valueOf(1);

	private static final Integer LOCALIDADE = Integer.valueOf(2);

	private static final Integer SETOR_COMERCIAL = Integer.valueOf(3);

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirRelatorioConsumidoresOcorrenciaLeiturasAction");

		DynaActionForm dynaForm = (DynaActionForm) actionForm;

		this.pesquisarGerenciaRegional(httpServletRequest);
		this.pesquisarUnidadeNegocio(httpServletRequest, dynaForm);
		this.pesquisarGrupoFaturamento(httpServletRequest);
		this.pesquisarUnidadeFederacao(httpServletRequest);
		this.pesquisarQuadra(httpServletRequest, dynaForm);

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsultaStr = httpServletRequest.getParameter("objetoConsulta");

		Integer objetoConsulta = null;
		if(objetoConsultaStr != null){
			objetoConsulta = Integer.valueOf(objetoConsultaStr);
		}

		if(objetoConsulta != null){
			if(objetoConsulta.equals(LOCALIDADE_VINCULADA)){
				this.pesquisarEloPolo(dynaForm);
			}else if(objetoConsulta.equals(LOCALIDADE)){
				this.pesquisarLocalidade(dynaForm);
			}else if(objetoConsulta.equals(SETOR_COMERCIAL)){
				this.pesquisarSetorComercial(dynaForm);
			}
		}

		return retorno;
	}

	/**
	 * Pesquisa Gerencial Regional
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.ID);

		filtroGerenciaRegional.setConsultaSemLimites(true);

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Gerência Regional");
		}else{
			httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
		}
	}

	/**
	 * Pesquisa Unidade Negocio
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest, DynaActionForm form){

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.ID);
		filtroUnidadeNegocio.setConsultaSemLimites(true);
		Collection colecaoUnidadeNegocio = this.getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Unidade de Negócio");
		}else{
			httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
		}
	}

	/**
	 * Pesquias os grupos de faturamentos.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 * @param httpServletRequest
	 */
	private void pesquisarGrupoFaturamento(HttpServletRequest httpServletRequest){

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.ID);

		filtroFaturamentoGrupo.setConsultaSemLimites(true);

		Collection colecaoFaturamentoGrupo = this.getFachada().pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		if(colecaoFaturamentoGrupo == null || colecaoFaturamentoGrupo.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Grupo de Faturamento");
		}else{
			httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}
	}

	/**
	 * Pesquias os grupos de faturamentos.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 * @param httpServletRequest
	 */
	private void pesquisarUnidadeFederacao(HttpServletRequest httpServletRequest){

		// UNIDADE FEDERAÇÃO

		FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();

		filtroUnidadeFederacao.setCampoOrderBy(FiltroUnidadeFederacao.ID);

		Collection<UnidadeFederacao> colecaoUnidadeFederacao = this.getFachada().pesquisar(filtroUnidadeFederacao,
						UnidadeFederacao.class.getName());

		if(colecaoUnidadeFederacao == null || colecaoUnidadeFederacao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Unidade Federação");
		}else{
			httpServletRequest.setAttribute("colecaoUnidadeFederacao", colecaoUnidadeFederacao);
		}
	}

	/**
	 * Pesquisa Elo Pólo
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void pesquisarEloPolo(DynaActionForm form){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, (String) form
						.get(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_LOCALIDADE_VINCULADA)));

		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

		// Recupera Elo Pólo
		Collection colecaoEloPolo = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoEloPolo != null && !colecaoEloPolo.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoEloPolo);

			localidade = localidade.getLocalidade();

			form.set(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_LOCALIDADE_VINCULADA, localidade.getId().toString());
			form.set(NOME_LOCALIDADE_VINCULADA, localidade.getDescricao());

		}else{
			form.set(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_LOCALIDADE_VINCULADA, null);
			form.set(NOME_LOCALIDADE_VINCULADA, "Localidade inexistente");
		}

	}

	/**
	 * Pesquisa Localidade
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void pesquisarLocalidade(DynaActionForm form){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, (String) form
						.get(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_LOCALIDADE)));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.set(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_LOCALIDADE, localidade.getId().toString());
			form.set(NOME_LOCALIDADE, localidade.getDescricao());

		}else{
			form.set(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_LOCALIDADE, null);
			form.set(NOME_LOCALIDADE, "Localidade inexistente");
		}

	}

	/**
	 * Pesquisa Setor comercial
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void pesquisarSetorComercial(DynaActionForm form){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, (String) form
						.get(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_CD_SETOR_COMERCIAL)));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, (String) form
						.get(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_LOCALIDADE)));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			form.set(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_CD_SETOR_COMERCIAL, Integer
							.toString(setorComercial.getCodigo()));
			form.set(NOME_SETOR_COMERCIAL, setorComercial.getDescricao());

		}else{
			form.set(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_CD_SETOR_COMERCIAL, null);
			form.set(NOME_SETOR_COMERCIAL, "Setor Comercial inexistente");
		}

	}

	/**
	 * Pesquisa Quadra
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void pesquisarQuadra(HttpServletRequest httpServletRequest, DynaActionForm form){

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, (String) form
						.get(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_QUADRA)));

		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, (String) form
						.get(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_CD_SETOR_COMERCIAL)));

		// Recupera Quadra
		Collection colecaoQuadra = this.getFachada().pesquisar(filtroQuadra, Quadra.class.getName());

		if(colecaoQuadra != null && !colecaoQuadra.isEmpty()){

			Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

			form.set(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_QUADRA, Integer.toString(quadra.getNumeroQuadra()));
		}else{
			form.set(RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl.P_QUADRA, null);
			httpServletRequest.setAttribute("msgQuadra", "QUADRA INEXISTENTE");

		}
	}

}
