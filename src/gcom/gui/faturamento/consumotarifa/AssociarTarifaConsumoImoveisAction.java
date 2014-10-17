/**
 * 
 */
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

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 07/12/2006
 */
public class AssociarTarifaConsumoImoveisAction
				extends GcomAction {

	/**
	 * Este caso de uso permite associar a tarifa de consumo para um ou mais
	 * imóveis.
	 * [UC0378] Associar Tarifa de Consumo a Imóveis
	 * 
	 * @author Rômulo Aurélio
	 * @date 07/12/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		AssociarTarifaConsumoImoveisActionForm associarTarifaConsumoImoveisActionForm = (AssociarTarifaConsumoImoveisActionForm) actionForm;

		Collection colecaoImoveis = null;

		String matricula = associarTarifaConsumoImoveisActionForm.getIdImovel();

		String tarifaAtual = associarTarifaConsumoImoveisActionForm.getTarifaAtual();

		String tarifaEspecial = associarTarifaConsumoImoveisActionForm.getTarifaEspecial();

		String dataValidadeTarifaEspecial = associarTarifaConsumoImoveisActionForm.getDataValidadeTarifaEspecial();

		// [FS0009] – Validar data. Caso a data esteja inválida, exibir a mensagem “Data inválida” e
		// retornar para o passo correspondente no fluxo principal.
		if(!Util.isVazioOuBranco(dataValidadeTarifaEspecial) && Util.validarDiaMesAno(dataValidadeTarifaEspecial)){
			throw new ActionServletException("atencao.data.inicio.invalida");
		}

		if(tarifaAtual.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			tarifaAtual = "";
		}

		if(tarifaEspecial.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			tarifaEspecial = "";
		}

		if(sessao.getAttribute("colecaoImoveis") != null){

			colecaoImoveis = (Collection) sessao.getAttribute("colecaoImoveis");

			// atualizacao de imoveis.

			fachada.atualizarImoveisTarifaConsumo(matricula, tarifaAtual, tarifaEspecial, Util
							.converteStringParaDate(dataValidadeTarifaEspecial), colecaoImoveis);

		}else{

			Imovel imovel = new Imovel();

			imovel.setId(new Integer(matricula));

			if(!Util.isVazioOuBranco(tarifaAtual)){
				ConsumoTarifa consumoTarifa = new ConsumoTarifa();

				consumoTarifa.setId(new Integer(tarifaAtual));

				imovel.setConsumoTarifa(consumoTarifa);
			}

			if(!Util.isVazioOuBranco(tarifaEspecial)){
				ConsumoTarifa consumoTarifa = new ConsumoTarifa();

				consumoTarifa.setId(new Integer(tarifaEspecial));

				imovel.setConsumoTarifaTemporaria(consumoTarifa);
			}

			if(!Util.isVazioOuBranco(dataValidadeTarifaEspecial)){
				imovel.setDataValidadeTarifaTemporaria(Util.converteStringParaDate(dataValidadeTarifaEspecial));
			}

			colecaoImoveis = new ArrayList();

			colecaoImoveis.add(imovel);

			// atualiza um unico imovel

			fachada.atualizarImoveisTarifaConsumo(matricula, tarifaAtual, tarifaEspecial, Util
							.converteStringParaDate(dataValidadeTarifaEspecial), colecaoImoveis);

		}

		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

		if(!Util.isVazioOuBranco(tarifaAtual) && !Util.isVazioOuBranco(tarifaEspecial)){

			ArrayList lista = new ArrayList();

			lista.add(Integer.valueOf(tarifaAtual));
			lista.add(Integer.valueOf(tarifaEspecial));

			filtroConsumoTarifa.adicionarParametro(new ParametroSimplesColecao(FiltroConsumoTarifa.ID, lista));

			Collection colecaoConsumoTarifa = fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			Iterator dadosConsumoTarifa = colecaoConsumoTarifa.iterator();

			String descricaoFormatada = "";
			if(colecaoConsumoTarifa.size() > 1){
				descricaoFormatada = ((ConsumoTarifa) dadosConsumoTarifa.next()).getDescricao() + " e "
								+ ((ConsumoTarifa) dadosConsumoTarifa.next()).getDescricao();
			}else{
				String descricaoTarifa = ((ConsumoTarifa) dadosConsumoTarifa.next()).getDescricao();
				descricaoFormatada = descricaoTarifa + " e " + descricaoTarifa;
			}

			montarPaginaSucesso(httpServletRequest, "Tarifas de Consumo: " + descricaoFormatada + " associadas com sucesso.",
							"Associar Tarifa de Consumo a outro Imóvel", "exibirAssociarTarifaConsumoImoveisAction.do?menu=sim");
		}else{
			String tarifa = tarifaAtual + tarifaEspecial;

			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, Integer.valueOf(tarifa)));

			Collection colecaoConsumoTarifa = fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			ConsumoTarifa consumoTarifaAux = (ConsumoTarifa) colecaoConsumoTarifa.iterator().next();

			montarPaginaSucesso(httpServletRequest, "Tarifa de Consumo " + consumoTarifaAux.getDescricao() + " associada com sucesso.",
							"Associar Tarifa de Consumo a outro Imóvel", "exibirAssociarTarifaConsumoImoveisAction.do?menu=sim");
		}

		return retorno;
	}
}
