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

package gcom.gui.faturamento.consumotarifa;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FiltroFaturamentoAtividadeCronograma;
import gcom.faturamento.consumotarifa.*;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.consumotarifa.bean.CategoriaFaixaConsumoTarifaHelper;
import gcom.micromedicao.consumo.CalculoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.*;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * UC0169-Manter Tarifa de Consumo
 * 
 * @author Administrador,Rafael Santos
 * @since 18/07/2006
 */
public class ExibirManterConsumoTarifaExistenteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("manterConsumoTarifaExistenteAction");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		String inTarifaEsgotoPropria = null;

		// se recuperou no request, seta na sessão
		if(httpServletRequest.getParameter("indicadorTarifaEsgotoPropria") != null){
			inTarifaEsgotoPropria = httpServletRequest.getParameter("indicadorTarifaEsgotoPropria");
		}else{
			inTarifaEsgotoPropria = (String) sessao.getAttribute("indicadorTarifaEsgotoPropria");
		}

		// se o indicador for SIM (1), seta na sessão
		if(inTarifaEsgotoPropria != null && inTarifaEsgotoPropria.equals(ConstantesSistema.SIM.toString())){
			sessao.setAttribute("indicadorTarifaEsgotoPropria", ConstantesSistema.SIM.toString());
		}else{
			sessao.removeAttribute("indicadorTarifaEsgotoPropria");
		}

		String limparForm = (String) httpServletRequest.getParameter("limparForm");

		InserirConsumoTarifaActionForm inserirConsumoTarifaActionForm = (InserirConsumoTarifaActionForm) actionForm;
		
		String pQuantidadeDecimaisValorTarifa = null;

		try{

			pQuantidadeDecimaisValorTarifa = (String) ParametroFaturamento.P_QUANTIDADE_DECIMAIS_VALOR_TARIFA.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		sessao.setAttribute("pQuantidadeDecimaisValorTarifa", pQuantidadeDecimaisValorTarifa);

		String idVigencia = null;

		if(httpServletRequest.getParameter("idVigencia") != null){
			idVigencia = (String) httpServletRequest.getParameter("idVigencia");
		}else if(httpServletRequest.getAttribute("idVigencia") != null){
			idVigencia = (String) httpServletRequest.getAttribute("idVigencia");
		}

		if((idVigencia != null) && (!idVigencia.equals(""))){

			if(httpServletRequest.getParameter("recarregar") == null){
				String[] ids = new String[1];
				ids[0] = idVigencia;

				// Colocado para ao tentar manter uma tarifa ja usada, so poder visualizar
				// recebe os ids das vigencias a ser excluídas do action e faz um
				// interator para fazer a verificacao de vigencia por vigencia
				for(int i = 0; i < ids.length; i++){
					// monta o filtro pra recuperar a data da vigencia
					FiltroConsumoTarifaVigencia filtroConsumoTarifaVigencia = new FiltroConsumoTarifaVigencia();
					filtroConsumoTarifaVigencia.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaVigencia.ID, ids[i]));
					Collection<ConsumoTarifaVigencia> colecaoConsumoTarifaVigencia = fachada.pesquisar(filtroConsumoTarifaVigencia,
									ConsumoTarifaVigencia.class.getName());

					// jogaa a data em nessa variável
					Date dataVigencia = colecaoConsumoTarifaVigencia.iterator().next().getDataVigencia();

					// monta o filtro para pegar todas os FaturamentoAtividadeCronograma
					// a data de realizacao tem que ser maior ou igual a data da
					// vigencia o id da atividade tem q ser igual a 2 (EFETUAR LEITURA)

					FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = new FiltroFaturamentoAtividadeCronograma();
					filtroFaturamentoAtividadeCronograma.adicionarParametro(new MaiorQue(
									FiltroFaturamentoAtividadeCronograma.DATA_REALIZADA, dataVigencia));
					// valor correspondente a EFETUAR LEITURA
					filtroFaturamentoAtividadeCronograma.adicionarParametro(new ParametroSimplesColecao(
									FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID, FaturamentoAtividade.EFETUAR_LEITURA
													.toString()));

					filtroFaturamentoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("faturamentoAtividade");
					filtroFaturamentoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupoCronogramaMensal");
					Collection<FaturamentoAtividadeCronograma> colecaoFaturamentoAtividadeCronograma = fachada.pesquisar(
									filtroFaturamentoAtividadeCronograma, FaturamentoAtividadeCronograma.class.getName());

					// verifica se há dados na colecao e faz um iterator pra recuperar
					// todos que tem o id da atividade igual a 5 que a data de
					// realização
					// seja diferente de null e que o id do cronograma grupo mensal seja
					// igual a da colecao de FatAtvCronograma vigente no iterator
					// anterior
					if(colecaoFaturamentoAtividadeCronograma != null && !colecaoFaturamentoAtividadeCronograma.isEmpty()){
						for(Iterator it = colecaoFaturamentoAtividadeCronograma.iterator(); it.hasNext();){
							FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = (FaturamentoAtividadeCronograma) it.next();

							FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma2 = new FiltroFaturamentoAtividadeCronograma();
							// valor correspondente a FATURAR GRUPO
					
							
							filtroFaturamentoAtividadeCronograma2.adicionarParametro(new ParametroSimplesColecao(
											FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID, FaturamentoAtividade.FATURAR_GRUPO.toString(),
											ConectorOr.CONECTOR_OR));
							
							filtroFaturamentoAtividadeCronograma2.adicionarParametro(new ParametroSimplesColecao(
											FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID,
											FaturamentoAtividade.REGISTRAR_FATURAMENTO_IMEDIATO.toString()));
							
							filtroFaturamentoAtividadeCronograma2.adicionarParametro(new ParametroSimples(
											FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ID,
											faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal().getId()));
							filtroFaturamentoAtividadeCronograma2.adicionarParametro(new ParametroNaoNulo(
											FiltroFaturamentoAtividadeCronograma.DATA_REALIZADA));
							filtroFaturamentoAtividadeCronograma2
											.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupoCronogramaMensal");

							Collection<FaturamentoAtividadeCronograma> colecaoRetornoFaturamentoAtividadeCronograma = fachada.pesquisar(
											filtroFaturamentoAtividadeCronograma2, FaturamentoAtividadeCronograma.class.getName());

							// se a colecao retornar dados, jogasse a excessão
							if(colecaoRetornoFaturamentoAtividadeCronograma != null
											&& !colecaoRetornoFaturamentoAtividadeCronograma.isEmpty()){
								httpServletRequest.setAttribute("caminhoActionConclusao",
												"/gsan/exibirManterConsumoTarifaExistenteAction.do?idVigencia=" + ids[i]
																+ "&recarregar=false");

								return montarPaginaConfirmacao("atencao.data_vigencia_usada.confirma", httpServletRequest, actionMapping,
												Util.formatarData(dataVigencia));

							}
						}
					}

				}
			}

			//
			FiltroConsumoTarifaVigencia filtroConsumoTarifaVigencia = new FiltroConsumoTarifaVigencia();

			filtroConsumoTarifaVigencia.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaVigencia.ID, idVigencia));
			filtroConsumoTarifaVigencia.adicionarCaminhoParaCarregamentoEntidade("consumoTarifa");
			filtroConsumoTarifaVigencia.adicionarCaminhoParaCarregamentoEntidade("calculoTipo");
			Collection colecaoVigencia = fachada.pesquisar(filtroConsumoTarifaVigencia, ConsumoTarifaVigencia.class.getName());
			ConsumoTarifaVigencia consumoTarifaVigencia = (ConsumoTarifaVigencia) Util.retonarObjetoDeColecao(colecaoVigencia);

			FiltroCalculoTipo filtroCalculoTipo = new FiltroCalculoTipo();
			filtroCalculoTipo.adicionarParametro(new ParametroSimples(FiltroCalculoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroCalculoTipo.setCampoOrderBy(FiltroCalculoTipo.DESCRICAO);

			Collection colecaoCalculoTipo = fachada.pesquisar(filtroCalculoTipo, CalculoTipo.class.getName());
			sessao.setAttribute("colecaoCalculoTipo", colecaoCalculoTipo);

			if(consumoTarifaVigencia.getCalculoTipo().getId() != null){
				filtroCalculoTipo = new FiltroCalculoTipo();
				filtroCalculoTipo.adicionarParametro(new ParametroSimples(FiltroCalculoTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroCalculoTipo.adicionarParametro(new ParametroSimples(FiltroCalculoTipo.ID, consumoTarifaVigencia.getCalculoTipo()
								.getId()));
				filtroCalculoTipo.setCampoOrderBy(FiltroCalculoTipo.DESCRICAO);

				colecaoCalculoTipo = fachada.pesquisar(filtroCalculoTipo, CalculoTipo.class.getName());
				CalculoTipo calculoTipo = (CalculoTipo) Util.retonarObjetoDeColecao(colecaoCalculoTipo);

				sessao.setAttribute("calculoTipoSelected", calculoTipo.getId().toString());
			}

			if(consumoTarifaVigencia.getConsumoTarifa().getIcTarifaEsgotoPropria().equals(ConstantesSistema.SIM)){
				sessao.setAttribute("indicadorTarifaEsgotoPropria", ConstantesSistema.SIM.toString());
			}

			FiltroConsumoTarifaCategoria filtroConsumoTarifaCategoria = new FiltroConsumoTarifaCategoria();

			filtroConsumoTarifaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaCategoria.CONSUMO_VIGENCIA_ID,
							idVigencia));

			filtroConsumoTarifaCategoria.adicionarCaminhoParaCarregamentoEntidade("consumoTarifaVigencia");
			filtroConsumoTarifaCategoria.adicionarCaminhoParaCarregamentoEntidade("subCategoria");
			filtroConsumoTarifaCategoria.adicionarCaminhoParaCarregamentoEntidade("categoria");
			// filtroConsumoTarifaCategoria.adicionarCaminhoParaCarregamentoEntidade("consumoTarifaFaixas");

			Collection colecaoCategoria = fachada.pesquisar(filtroConsumoTarifaCategoria, ConsumoTarifaCategoria.class.getName());
			Iterator iterator = colecaoCategoria.iterator();
			Collection colecaoHelpers = new ArrayList();
			while(iterator.hasNext()){
				ConsumoTarifaCategoria consumoTarifaCategoria = (ConsumoTarifaCategoria) iterator.next();
				CategoriaFaixaConsumoTarifaHelper categoriaFaixaConsumoTarifaHelper = new CategoriaFaixaConsumoTarifaHelper(0,
								consumoTarifaCategoria);

				FiltroConsumoTarifaFaixa filtroConsumoTarifaFaixa = new FiltroConsumoTarifaFaixa();
				filtroConsumoTarifaFaixa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaFaixa.CONSUMO_TARIFA_CATEGORIA_ID,
								consumoTarifaCategoria.getId()));
				Collection colecaoFaixa = fachada.pesquisar(filtroConsumoTarifaFaixa, ConsumoTarifaFaixa.class.getName());

				categoriaFaixaConsumoTarifaHelper.setColecaoFaixas(colecaoFaixa);
				colecaoHelpers.add(categoriaFaixaConsumoTarifaHelper);
			}

			sessao.setAttribute("colecaoVigencia", colecaoVigencia);
			sessao.setAttribute("colecaoCategoria", colecaoHelpers);

		}
		if(limparForm != null && !limparForm.trim().equalsIgnoreCase("")){
			inserirConsumoTarifaActionForm.reset(actionMapping, httpServletRequest);
		}

		try{
			String indicadorTarifaCosumoPorSubCategoria = (String) ParametroFaturamento.P_INDICADOR_TARIFA_CONSUMO_SUBCATEGORIA.executar();
			if(indicadorTarifaCosumoPorSubCategoria.equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorTarifaCosumoPorSubCategoria", "S");
			}else{
				sessao.removeAttribute("indicadorTarifaCosumoPorSubCategoria");
			}

		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		return retorno;

	}
}
