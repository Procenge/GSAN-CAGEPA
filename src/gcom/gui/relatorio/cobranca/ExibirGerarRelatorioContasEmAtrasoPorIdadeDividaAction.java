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

package gcom.gui.relatorio.cobranca;

import gcom.cadastro.localidade.*;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.procenge.geradorrelatorio.impl.validador.ContasEmAtrasoPorIdadeDividaValidadorImpl;

/**
 * [UC00730] Gerar Relat�rio de Im�veis com Faturas Recentes em Dia e Faturas Antigas em Atraso
 * 
 * @author Luciano Galv�o
 * @date 28/03/2012
 */

public class ExibirGerarRelatorioContasEmAtrasoPorIdadeDividaAction
				extends GcomAction {

	/*
	 * Constantes dos par�metros do formul�rio, caso precise passar valores para eles.
	 */

	private static final String NOME_LOCALIDADE_INICIAL = "nomeLocalidadeInicial";

	private static final String NOME_LOCALIDADE_FINAL = "nomeLocalidadeFinal";

	private static final String NOME_SETOR_FATURAMENTO_INICIAL = "nomeSetorFaturamentoInicial";

	private static final String NOME_SETOR_FATURAMENTO_FINAL = "nomeSetorFaturamentoFinal";

	/*
	 * Constantes que indicam cada campo de pesquisa
	 */
	private static final Integer LOCALIDADE_INICIAL = Integer.valueOf(1);

	private static final Integer LOCALIDADE_FINAL = Integer.valueOf(2);

	private static final Integer SETOR_FATURAMENTO_INICIAL = Integer.valueOf(3);

	private static final Integer SETOR_FATURAMENTO_FINAL = Integer.valueOf(4);

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioContasEmAtrasoPorIdadeDividaAction");

		DynaActionForm dynaForm = (DynaActionForm) actionForm;

		// Limpa os dados do formul�rio.
		// this.limparCampos(httpServletRequest, form);

		// Carrega os campos de lista
		this.pesquisarGerenciaRegional(httpServletRequest);

		this.pesquisarUnidadeNegocio(httpServletRequest);

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsultaStr = httpServletRequest.getParameter("objetoConsulta");

		Integer objetoConsulta = null;
		if(objetoConsultaStr != null){
			objetoConsulta = Integer.valueOf(objetoConsultaStr);
		}

		if(objetoConsulta != null){
			if((objetoConsulta.equals(LOCALIDADE_INICIAL)) || (objetoConsulta.equals(LOCALIDADE_FINAL))){
				pesquisarLocalidade(dynaForm, objetoConsulta);
			}else if((objetoConsulta.equals(SETOR_FATURAMENTO_INICIAL)) || (objetoConsulta.equals(SETOR_FATURAMENTO_FINAL))){
				pesquisarSetorComercial(dynaForm, objetoConsulta);
			}
		}

		return retorno;
	}

	/**
	 * Pesquisa Localidade Inicial ou Final
	 * 
	 * @author Luciano Galv�o
	 * @date 30/03/2012
	 */
	private void pesquisarLocalidade(DynaActionForm form, Integer objetoConsulta){

		Integer localidadeId = null;
		String localidadeIdStr = null;
		Localidade localidade = null;

		// Recupera o id da localidade de acordo com a vari�vel objetoConsulta, que indica qual
		// campo do formul�rio foi informado para pesquisa
		if(objetoConsulta.equals(LOCALIDADE_INICIAL)){
			localidadeIdStr = (String) form.get(ContasEmAtrasoPorIdadeDividaValidadorImpl.P_LOCALIDADE_INICIAL);
		}else if(objetoConsulta.equals(LOCALIDADE_FINAL)){
			localidadeIdStr = (String) form.get(ContasEmAtrasoPorIdadeDividaValidadorImpl.P_LOCALIDADE_FINAL);
		}

		if(!Util.isVazioOuBranco(localidadeIdStr)){
			localidadeId = Integer.valueOf(localidadeIdStr);

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeId));

			// Identifica quais as propriedades do formul�rio que ser�o atualizadas
			String campoLocalidadeId = ContasEmAtrasoPorIdadeDividaValidadorImpl.P_LOCALIDADE_INICIAL;
			String campoLocalidadeNome = NOME_LOCALIDADE_INICIAL;

			if(objetoConsulta.equals(LOCALIDADE_FINAL)){
				campoLocalidadeId = ContasEmAtrasoPorIdadeDividaValidadorImpl.P_LOCALIDADE_FINAL;
				campoLocalidadeNome = NOME_LOCALIDADE_FINAL;
			}

			// Recupera Localidade para preencher o formul�rio
			Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
				localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
			}

			// Se encontrou a localidade, preenche as propriedades do formul�rio
			if(localidade != null){
				form.set(campoLocalidadeId, localidade.getId().toString());
				form.set(campoLocalidadeNome, localidade.getDescricao());
			}else{
				form.set(campoLocalidadeId, null);
				form.set(campoLocalidadeNome, "Localidade inexistente");
			}

			// Replica para a localidade final
			if(objetoConsulta.equals(LOCALIDADE_INICIAL)){
				if(localidade != null){
					form.set(ContasEmAtrasoPorIdadeDividaValidadorImpl.P_LOCALIDADE_FINAL, localidade.getId().toString());
					form.set(NOME_LOCALIDADE_FINAL, localidade.getDescricao());
				}else{
					form.set(ContasEmAtrasoPorIdadeDividaValidadorImpl.P_LOCALIDADE_FINAL, null);
					form.set(NOME_LOCALIDADE_FINAL, "Localidade inexistente");
				}
			}

		}
	}

	/**
	 * Pesquisa Setor Comercial Inicial ou Final
	 * 
	 * @author Luciano Galv�o
	 * @date 30/03/2012
	 */
	private void pesquisarSetorComercial(DynaActionForm form, Integer objetoConsulta){

		Integer setorComercialId = null;
		String setorComercialIdStr = null;
		SetorComercial setorComercial = null;

		// Recupera o id da localidade de acordo com a vari�vel objetoConsulta, que indica qual
		// campo do formul�rio foi informado para pesquisa
		if(objetoConsulta.equals(SETOR_FATURAMENTO_INICIAL)){
			setorComercialIdStr = (String) form.get(ContasEmAtrasoPorIdadeDividaValidadorImpl.P_SETOR_FATURAMENTO_INICIAL);
		}else if(objetoConsulta.equals(SETOR_FATURAMENTO_FINAL)){
			setorComercialIdStr = (String) form.get(ContasEmAtrasoPorIdadeDividaValidadorImpl.P_SETOR_FATURAMENTO_FINAL);
		}

		if(!Util.isVazioOuBranco(setorComercialIdStr)){
			setorComercialId = Integer.valueOf(setorComercialIdStr);

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, setorComercialId));

			// Identifica quais as propriedades do formul�rio que ser�o atualizadas
			String campoSetorComercialId = ContasEmAtrasoPorIdadeDividaValidadorImpl.P_SETOR_FATURAMENTO_INICIAL;
			String campoSetorComercialNome = NOME_SETOR_FATURAMENTO_INICIAL;

			if(objetoConsulta.equals(SETOR_FATURAMENTO_FINAL)){
				campoSetorComercialId = ContasEmAtrasoPorIdadeDividaValidadorImpl.P_SETOR_FATURAMENTO_FINAL;
				campoSetorComercialNome = NOME_SETOR_FATURAMENTO_FINAL;
			}

			// Recupera Setor Comercial para preencher o formul�rio
			Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){
				setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
			}

			// Se encontrou o setor comercial, preenche as propriedades do formul�rio
			if(setorComercial != null){
				form.set(campoSetorComercialId, setorComercial.getId().toString());
				form.set(campoSetorComercialNome, setorComercial.getDescricao());
			}else{
				form.set(campoSetorComercialId, null);
				form.set(campoSetorComercialNome, "Setor inexistente");
			}

			// Replica para o setor final
			if(objetoConsulta.equals(SETOR_FATURAMENTO_INICIAL)){
				if(setorComercial != null){
					form.set(ContasEmAtrasoPorIdadeDividaValidadorImpl.P_SETOR_FATURAMENTO_FINAL, setorComercial.getId().toString());
					form.set(NOME_SETOR_FATURAMENTO_FINAL, setorComercial.getDescricao());
				}else{
					form.set(ContasEmAtrasoPorIdadeDividaValidadorImpl.P_SETOR_FATURAMENTO_FINAL, null);
					form.set(NOME_SETOR_FATURAMENTO_FINAL, "Setor inexistente");
				}
			}

		}
	}

	/**
	 * Pesquisa Gerencial Regional
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 */
	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.ID);
		// Os analistas Fernando e Alessando acharam que faria sentido o relat�rio exibir as contas
		// em atraso das Ger�ncias Regionais que est�o inativas hoje, mas que no passado eram
		// ativas.

		// filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Ger�ncia Regional");
		}else{
			httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
		}
	}

	/**
	 * Pesquisa Unidade Neg�cio
	 * 
	 * @author Diogo Monteiro
	 * @date 19/07/2012
	 */
	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest){

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.setConsultaSemLimites(true);
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.ID);

		// Os analistas Fernando e Alessando acharam que faria sentido o relat�rio exibir as contas
		// em atraso das Unidades de Neg�cio que est�o inativas hoje, mas que no passado eram
		// ativas.

		// filtroUnidadeNegocio.adicionarParametro(new
		// ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoUnidadeNegocio = this.getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Unidade de Neg�cio");
		}else{
			httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
		}
	}

}
