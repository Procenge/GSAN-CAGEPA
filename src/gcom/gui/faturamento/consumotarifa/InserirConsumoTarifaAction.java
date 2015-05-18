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
import gcom.faturamento.consumotarifa.*;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.consumotarifa.bean.CategoriaFaixaConsumoTarifaHelper;
import gcom.micromedicao.consumo.CalculoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descrição da Classe>>
 * 
 * @author Tiago Moreno
 */
public class InserirConsumoTarifaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirConsumoTarifaActionForm inserirConsumoTarifaActionForm = (InserirConsumoTarifaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);
		// Variavel para testar se o campo naum obrigatorio esta vazio

		String indicadorTarifaCosumoPorSubCategoria = ConstantesSistema.NAO.toString();
		try{
			indicadorTarifaCosumoPorSubCategoria = (String) ParametroFaturamento.P_INDICADOR_TARIFA_CONSUMO_SUBCATEGORIA.executar();

		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		String descTarifa = inserirConsumoTarifaActionForm.getDescTarifa();
		String slcDescTarifa = inserirConsumoTarifaActionForm.getSlcDescTarifa();
		String dataVigencia = inserirConsumoTarifaActionForm.getDataVigencia();
		String slcCalculoTipo = inserirConsumoTarifaActionForm.getSlcCalculoTipo();
		String inTarifaEsgotoPropria = inserirConsumoTarifaActionForm.getInTarifaEsgotoPropria();

		// Carregando o objeto consumoTarifa
		ConsumoTarifa consumoTarifa = new ConsumoTarifa();

		if((descTarifa == null) || (descTarifa.equalsIgnoreCase(""))){
			consumoTarifa.setId(new Integer(slcDescTarifa));
		}else{
			consumoTarifa.setDescricao(descTarifa);
			consumoTarifa.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
			consumoTarifa.setUltimaAlteracao(new Date());
		}

		if(inTarifaEsgotoPropria != null && !inTarifaEsgotoPropria.equals("")){
			consumoTarifa.setIcTarifaEsgotoPropria(Short.parseShort(inTarifaEsgotoPropria));
		}

		// Carregando o objeto consumoTarifaVigencia

		// Data de vigência da tarifa
		// Para auxiliar na formatação de uma data
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		Date dataVencimentoTarifa;

		try{
			dataVencimentoTarifa = formatoData.parse(dataVigencia);
		}catch(ParseException ex){
			dataVencimentoTarifa = null;
		}

		ConsumoTarifaVigencia consumoTarifaVigencia = new ConsumoTarifaVigencia();

		consumoTarifaVigencia.setDataVigencia(dataVencimentoTarifa);
		if(consumoTarifa.getId() != null){
			consumoTarifaVigencia.setConsumoTarifa(consumoTarifa);
		}
		consumoTarifaVigencia.setUltimaAlteracao(new Date());

		// OBS - O objeto ConsumoTarifa será carregado no controlador

		Collection<CategoriaFaixaConsumoTarifaHelper> colecaoConsumoTarifaCategoriaHelper = new ArrayList();
		Collection<ConsumoTarifaCategoria> colecaoConsumoTarifaCategoria = new ArrayList();

		if(sessao.getAttribute("colecaoConsumoTarifaCategoria") != null
						&& !((Collection) sessao.getAttribute("colecaoConsumoTarifaCategoria")).isEmpty()){
			colecaoConsumoTarifaCategoriaHelper = (Collection) sessao.getAttribute("colecaoConsumoTarifaCategoria");
			Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
			String consumoMinimo = null;
			String tarifaMinima = null;

			for(CategoriaFaixaConsumoTarifaHelper categoriaFaixaConsumoTarifaHelper : colecaoConsumoTarifaCategoriaHelper){

				colecaoConsumoTarifaCategoria.add(categoriaFaixaConsumoTarifaHelper.getConsumoTarifaCategoria());

				// valor minimo
				if(requestMap.get("ValorConMinimo."
								+ categoriaFaixaConsumoTarifaHelper.getConsumoTarifaCategoria().getCategoria().getDescricao()) != null){

					consumoMinimo = (requestMap.get("ValorConMinimo."
									+ categoriaFaixaConsumoTarifaHelper.getConsumoTarifaCategoria().getCategoria().getDescricao()))[0];

					if(consumoMinimo == null || consumoMinimo.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Consumo Mínimo");
					}

					categoriaFaixaConsumoTarifaHelper.getConsumoTarifaCategoria().setNumeroConsumoMinimo(new Integer(consumoMinimo));
				}

				String pQuantidadeDecimaisValorTarifa = null;

				try{

					pQuantidadeDecimaisValorTarifa = (String) ParametroFaturamento.P_QUANTIDADE_DECIMAIS_VALOR_TARIFA.executar();
				}catch(ControladorException e){

					throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
									new String[e.getParametroMensagem().size()]));
				}

				// valor da tarifa minima
				if(requestMap.get("ValorTarMin."
								+ categoriaFaixaConsumoTarifaHelper.getConsumoTarifaCategoria().getCategoria().getDescricao()) != null){

					tarifaMinima = (requestMap.get("ValorTarMin."
									+ categoriaFaixaConsumoTarifaHelper.getConsumoTarifaCategoria().getCategoria().getDescricao()))[0];

					if(tarifaMinima == null || tarifaMinima.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Tarifa Mínima");
					}

					categoriaFaixaConsumoTarifaHelper.getConsumoTarifaCategoria().setValorTarifaMinima(
									Util.formatarMoedaRealparaBigDecimal(tarifaMinima, Util.obterInteger(pQuantidadeDecimaisValorTarifa)));
				}

				// Atribuindo a colecao faixa valores da categoria
				if((categoriaFaixaConsumoTarifaHelper.getColecaoFaixas() != null)
								&& (!categoriaFaixaConsumoTarifaHelper.getColecaoFaixas().isEmpty())){
					Iterator colecaoFaixaIt = categoriaFaixaConsumoTarifaHelper.getColecaoFaixas().iterator();
					while(colecaoFaixaIt.hasNext()){
						ConsumoTarifaFaixa consumoTarifaFaixa = (ConsumoTarifaFaixa) colecaoFaixaIt.next();
						if(new Integer(consumoMinimo).intValue() > consumoTarifaFaixa.getNumeroConsumoFaixaIFim().intValue()){
							throw new ActionServletException("atencao.consumo_minimo.maior.faixa_limite_superior_menor_existe");
						}

					}
				}

			}

		}else{
			if(indicadorTarifaCosumoPorSubCategoria.equals(ConstantesSistema.SIM.toString())){
				throw new ActionServletException("atencao.nenhuma_subcategoria_tarifa");
			}else{
				throw new ActionServletException("atencao.nenhuma_categoria_tarifa");
			}
		}

		FiltroCalculoTipo filtroCalculoTipo = new FiltroCalculoTipo();

		filtroCalculoTipo.adicionarParametro(new ParametroSimples(FiltroCalculoTipo.ID, slcCalculoTipo));

		Collection colecaoCalculoTipo = fachada.pesquisar(filtroCalculoTipo, CalculoTipo.class.getName());

		CalculoTipo calculoTipoSelected = (CalculoTipo) Util.retonarObjetoDeColecao(colecaoCalculoTipo);
		sessao.setAttribute("slcCalculoTipo", slcCalculoTipo);

		consumoTarifaVigencia.setCalculoTipo(calculoTipoSelected);

		consumoTarifaVigencia.setDescricaoAtoAdministrativo(inserirConsumoTarifaActionForm.getDescricaoAtoAdministrativo());

		fachada.inserirConsumoTarifa(consumoTarifa, consumoTarifaVigencia, colecaoConsumoTarifaCategoria,
						this.getUsuarioLogado(httpServletRequest));

		if(consumoTarifa.getDescricao() == null){
			String idConsumo = inserirConsumoTarifaActionForm.getSlcDescTarifa();
			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, idConsumo));
			Collection colecaoConsumoSelect = (Collection) fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoSelect = (ConsumoTarifa) gcom.util.Util.retonarObjetoDeColecao(colecaoConsumoSelect);

			montarPaginaSucesso(httpServletRequest, consumoSelect.getDescricao() + " de vigência "
							+ gcom.util.Util.formatarData(consumoTarifaVigencia.getDataVigencia()) + " inserida com sucesso.",
							"Inserir outra Tarifa de Consumo", "exibirInserirConsumoTarifaAction.do?menu=sim",
							"exibirManterConsumoTarifaExistenteAction.do?idVigencia=" + consumoTarifaVigencia.getId().toString(),
							"Atualizar Tarifa de Consumo Inserida");

		}else{
			montarPaginaSucesso(httpServletRequest, consumoTarifa.getDescricao() + " de vigência "
							+ gcom.util.Util.formatarData(consumoTarifaVigencia.getDataVigencia()) + " inserida com sucesso.",
							"Inserir outra Tarifa de Consumo", "exibirInserirConsumoTarifaAction.do?menu=sim",
							"exibirManterConsumoTarifaExistenteAction.do?idVigencia=" + consumoTarifaVigencia.getId().toString(),
							"Atualizar Tarifa de Consumo Inserida");
		}

		return retorno;
	}
}