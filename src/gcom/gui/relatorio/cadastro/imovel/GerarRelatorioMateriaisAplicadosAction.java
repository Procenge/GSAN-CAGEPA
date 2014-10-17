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

package gcom.gui.relatorio.cadastro.imovel;

import gcom.gui.ActionServletException;
import gcom.gui.relatorio.atendimentopublico.GerarRelatorioMateriaisAplicadosActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.imovel.RelatorioMateriaisAplicados;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3135] Gerar Relatório de Materiais Aplicados
 * 
 * @author Felipe Rosacruz
 * @date 31/01/2014
 */
public class GerarRelatorioMateriaisAplicadosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioMateriaisAplicados");

		// Form
		GerarRelatorioMateriaisAplicadosActionForm form = (GerarRelatorioMateriaisAplicadosActionForm) actionForm;

		String idLocalidade = form.getIdLocalidade();
		String cdSetorComercial = form.getCdSetorComercial();
		String[] tipoServicoArray = form.getTipoServicoSelecionados();
		String[] materialArray = form.getMaterialSelecionados();
		String[] equipeArray = form.getEquipeSelecionados();
		String dataExecucaoInicial = form.getDataExecucaoInicial();
		String dataExecucaoFinal = form.getDataExecucaoFinal();

		RelatorioMateriaisAplicados relatorio = new RelatorioMateriaisAplicados(this.getUsuarioLogado(httpServletRequest));
		
		if(Util.isNaoNuloBrancoZero(idLocalidade)){
			relatorio.addParametro(RelatorioMateriaisAplicados.P_ID_LOCALIDADE, Integer.valueOf(idLocalidade));
		}
		if(Util.isNaoNuloBrancoZero(cdSetorComercial)){
			relatorio.addParametro(RelatorioMateriaisAplicados.P_CD_SETOR, Integer.valueOf(cdSetorComercial));
		}
		relatorio.addParametro(RelatorioMateriaisAplicados.P_DATA_INICIAL, Util.converteStringParaDate(dataExecucaoInicial));
		relatorio.addParametro(RelatorioMateriaisAplicados.P_DATA_FINAL, Util.converteStringParaDate(dataExecucaoFinal));

		Collection<Integer> colecaoIdServicoTipo = new ArrayList<Integer>();
		Collection<Integer> colecaoIdMaterial = new ArrayList<Integer>();
		Collection<Integer> colecaoIdEquipe = new ArrayList<Integer>();

		if(tipoServicoArray != null){
		for(String idServicoTipo : tipoServicoArray){
				if(!idServicoTipo.equals("-1")){
			colecaoIdServicoTipo.add(Integer.valueOf(idServicoTipo));
			}
			}
		}
		if(materialArray != null){
		for(String idMaterial : materialArray){
				if(!idMaterial.equals("-1")){
			colecaoIdMaterial.add(Integer.valueOf(idMaterial));
				}
		}
		}
		if(equipeArray != null){
		for(String idEquipe : equipeArray){
				if(!idEquipe.equals("-1")){
			colecaoIdEquipe.add(Integer.valueOf(idEquipe));
			}
			}
		}

		relatorio.addParametro(RelatorioMateriaisAplicados.P_TIPO_SERVICO, colecaoIdServicoTipo);
		relatorio.addParametro(RelatorioMateriaisAplicados.P_MATERIAL, colecaoIdMaterial);
		relatorio.addParametro(RelatorioMateriaisAplicados.P_EQUIPE, colecaoIdEquipe);
		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(TarefaRelatorio.TIPO_PDF + ""));

		if(relatorio.calcularTotalRegistrosRelatorio() < 1){
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_PDF, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}