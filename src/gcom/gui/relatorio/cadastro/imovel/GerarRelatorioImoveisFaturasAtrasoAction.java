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
 * Vitor Hora
 */

package gcom.gui.relatorio.cadastro.imovel;

import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.imovel.FiltrarRelatorioImoveisFaturasAtrasoHelper;
import gcom.relatorio.cadastro.imovel.RelatorioImoveisFaturasAtraso;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC00725] Gerar Relatório de Imóveis por Situação da Ligação de Água
 * 
 * @author Rafael Pinto
 * @date 28/11/2007
 */

public class GerarRelatorioImoveisFaturasAtrasoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("emitirRelatorioImoveisFaturasAtraso");

		// Form
		GerarRelatorioImoveisFaturasAtrasoActionForm form = (GerarRelatorioImoveisFaturasAtrasoActionForm) actionForm;

		FiltrarRelatorioImoveisFaturasAtrasoHelper filtro = new FiltrarRelatorioImoveisFaturasAtrasoHelper();

		// Gerência Regional
		if(form.getGerenciaRegional() != null && !form.getGerenciaRegional().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			filtro.setGerenciaRegional(new Integer(form.getGerenciaRegional()));
		}

		// Unidade de Negocio
		if(form.getUnidadeNegocio() != null && !form.getUnidadeNegocio().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			filtro.setUnidadeNegocio(new Integer(form.getUnidadeNegocio()));
		}

		// Localidade Inicial
		if(form.getLocalidadeInicial() != null && !form.getLocalidadeInicial().equals("")){

			filtro.setLocalidadeInicial(new Integer(form.getLocalidadeInicial()));
		}

		// Setor Comercial Inicial
		if(form.getSetorComercialInicial() != null && !form.getSetorComercialInicial().equals("")){

			filtro.setSetorComercialInicial(new Integer(form.getSetorComercialInicial()));
		}

		// Rota Inicial
		if(form.getRotaInicial() != null && !form.getRotaInicial().equals("")){

			filtro.setRotaInicial(new Integer(form.getRotaInicial()));
		}

		// Sequencial Rota Inicial
		if(form.getSequencialRotaInicial() != null && !form.getSequencialRotaInicial().equals("")){

			filtro.setSequencialRotalInicial(new Integer(form.getSequencialRotaInicial()));
		}

		// Localidade Final
		if(form.getLocalidadeFinal() != null && !form.getLocalidadeFinal().equals("")){

			filtro.setLocalidadeFinal(new Integer(form.getLocalidadeFinal()));
		}

		// Setor Comercial Final
		if(form.getSetorComercialFinal() != null && !form.getSetorComercialFinal().equals("")){

			filtro.setSetorComercialFinal(new Integer(form.getSetorComercialFinal()));
		}

		// Rota Final
		if(form.getRotaFinal() != null && !form.getRotaFinal().equals("")){

			filtro.setRotaFinal(new Integer(form.getRotaFinal()));
		}

		// Sequencial Rota Final
		if(form.getSequencialRotaFinal() != null && !form.getSequencialRotaFinal().equals("")){

			filtro.setSequencialRotalFinal(new Integer(form.getSequencialRotaFinal()));
		}

		// Situacao da Ligacao de agua
		if(form.getSituacaoLigacaoAgua() != null && form.getSituacaoLigacaoAgua().length > 0){

			Collection<Integer> colecao = new ArrayList();

			String[] array = form.getSituacaoLigacaoAgua();
			for(int i = 0; i < array.length; i++){
				if(new Integer(array[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecao.add(new Integer(array[i]));
				}
			}

			filtro.setSituacaoLigacaoAgua(colecao);
		}

		// Categorias
		if(form.getCategorias() != null && form.getCategorias().length > 0){

			Collection<Integer> colecao = new ArrayList();

			String[] array = form.getCategorias();
			for(int i = 0; i < array.length; i++){
				if(new Integer(array[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecao.add(new Integer(array[i]));
				}
			}

			filtro.setCategorias(colecao);
		}

		// Quantidade de Faturas Inicial
		if(form.getQuantidadeFaturasAtrasoInicial() != null && !form.getQuantidadeFaturasAtrasoInicial().equals("")){

			filtro.setQuantidadeFaturasAtrasoInicial(new Integer(form.getQuantidadeFaturasAtrasoInicial()));
		}

		// Quantidade de Faturas Final
		if(form.getQuantidadeFaturasAtrasoFinal() != null && !form.getQuantidadeFaturasAtrasoFinal().equals("")){

			filtro.setQuantidadeFaturasAtrasoFinal(new Integer(form.getQuantidadeFaturasAtrasoFinal()));
		}

		// Valor de Faturas Inicial
		if(form.getValorFaturasAtrasoInicial() != null && !form.getValorFaturasAtrasoInicial().equals("")){

			filtro.setValorFaturasAtrasoInicial(new BigDecimal(form.getValorFaturasAtrasoInicial()));
		}

		// Valor de Faturas Final
		if(form.getValorFaturasAtrasoFinal() != null && !form.getValorFaturasAtrasoFinal().equals("")){

			filtro.setValorFaturasAtrasoFinal(new BigDecimal(form.getValorFaturasAtrasoFinal()));
		}

		// Referencia de Faturas Inicial
		if(form.getReferenciaFaturasAtrasoInicial() != null && !form.getReferenciaFaturasAtrasoInicial().equals("")){

			filtro.setReferenciaFaturasAtrasoInicial(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaFaturasAtrasoInicial()));
		}

		// Referencia de Faturas Final
		if(form.getReferenciaFaturasAtrasoFinal() != null && !form.getReferenciaFaturasAtrasoFinal().equals("")){

			filtro.setReferenciaFaturasAtrasoFinal(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaFaturasAtrasoFinal()));
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioImoveisFaturasAtraso relatorio = new RelatorioImoveisFaturasAtraso(this.getUsuarioLogado(httpServletRequest));

		relatorio.addParametro("filtrarRelatorioImoveisFaturasAtrasoHelper", filtro);
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}