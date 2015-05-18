
package gcom.gui.faturamento.conta;

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

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
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

/**
 * [UC3156] Simular C�lculo da Conta Dados Reais
 * 
 * @author Anderson Italo
 * @date 21/09/2014
 */
public class ExibirSimularCalculoContaDadosReaisFiltrarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirSimularCalculoContaDadosReaisFiltrar");

		SimularCalculoContaDadosReaisFiltrarActionForm form = (SimularCalculoContaDadosReaisFiltrarActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Im�vel
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("1"))){

			this.pesquisarImovel(form, fachada, httpServletRequest);
		}

		// Faz a pesquisa das Situa��es de Liga��o de �gua
		this.pesquisarLigacaoAguaSituacao(httpServletRequest);

		// Faz a pesquisa das Situa��es de Liga��o de Esgoto
		this.pesquisarLigacaoEsgotoSituacao(httpServletRequest);

		// Faz a pesquisa das Tarifas de Consumo
		this.pesquisarConsumoTarifa(httpServletRequest);

		// Faz a pesquisa das Categorias
		this.pesquisarCategoria(httpServletRequest);

		// Faz a pesquisa dos Grupos de Faturamento
		this.pesquisarFaturamentoGrupo(httpServletRequest);

		// Seta os request�s encontrados
		this.setaRequest(httpServletRequest, form);

		return retorno;
	}

	private void pesquisarImovel(SimularCalculoContaDadosReaisFiltrarActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		Imovel imovel = fachada.pesquisarImovel(Util.obterInteger(form.getIdImovel()));

		if(imovel != null){

			form.setInscricaoImovel(imovel.getInscricaoFormatada());
			form.setIdImovel(imovel.getId().toString());
		}else{

			form.setIdImovel(null);
			form.setInscricaoImovel("Im�vel inexistente");

		}

	}

	/**
	 * Seta os request com os id encontrados
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, SimularCalculoContaDadosReaisFiltrarActionForm form){

		// Imovel
		if(!Util.isVazioOuBranco(form.getIdImovel()) && !Util.isVazioOuBranco(form.getInscricaoImovel())){

			httpServletRequest.setAttribute("imovelEncontrado", "true");
		}
	}

	/**
	 * Pesquisar Situa��o de Liga��o de �gua
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	private void pesquisarLigacaoAguaSituacao(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();

		filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Situa��od e Liga��o de �gua
		Collection colecaoLigacaoAguaSituacao = this.getFachada().pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoLigacaoAguaSituacao == null || colecaoLigacaoAguaSituacao.isEmpty()){

			// Nenhum registro na tabela ligacao_agua_situacao foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Liga��o �gua Situa��o");
		}else{

			httpServletRequest.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);
		}
	}

	/**
	 * Pesquisar Situa��o de Liga��o de Esgoto
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	private void pesquisarLigacaoEsgotoSituacao(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Situa��o de Liga��o de Esgoto
		Collection colecaoLigacaoEsgotoSituacao = this.getFachada().pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoLigacaoEsgotoSituacao == null || colecaoLigacaoEsgotoSituacao.isEmpty()){

			// Nenhum registro na tabela ligacao_esgoto_situacao foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Liga��o Esgoto Situa��o");
		}else{

			httpServletRequest.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);
		}
	}

	/**
	 * Pesquisar Tarifa de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	private void pesquisarConsumoTarifa(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

		filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);

		filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Tarifa de Consumo
		Collection colecaoConsumoTarifa = this.getFachada().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoConsumoTarifa == null || colecaoConsumoTarifa.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Consumo Tarifa");
		}else{

			httpServletRequest.setAttribute("colecaoConsumoTarifa", colecaoConsumoTarifa);
		}
	}

	/**
	 * Pesquisar Categoria
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	private void pesquisarCategoria(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna categoria
		Collection colecaoCategoria = this.getFachada().pesquisar(filtroCategoria, Categoria.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoCategoria == null || colecaoCategoria.isEmpty()){

			// Nenhum registro na tabela categoria foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Categoria");
		}else{

			httpServletRequest.setAttribute("colecaoCategoria", colecaoCategoria);
		}
	}

	/**
	 * Pesquisar Grupo de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	private void pesquisarFaturamentoGrupo(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);

		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna categoria
		Collection colecaoFaturamentoGrupo = this.getFachada().pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoFaturamentoGrupo == null || colecaoFaturamentoGrupo.isEmpty()){

			// Nenhum registro na tabela faturamento_grupo foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Faturamento Grupo");
		}else{

			httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}
	}

}

