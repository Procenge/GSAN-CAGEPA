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

package gcom.gui.relatorio.gerencial;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gerencial.bean.FiltroQuadroComparativoFaturamentoArrecadacaoHelper;
import gcom.gui.ActionServletException;
import gcom.gui.gerencial.ConsultarQuadroComparativoFaturamentoArrecadacaoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.gerencial.RelatorioQuadroComparativoFaturamentoArrecadacao;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por gerar o relat�rio quadro comparativo de faturamento e arrecada��o
 * 
 * @author Luciano Galv�o
 * @date 10/05/2013
 */

public class GerarRelatorioQuadroComparativoFaturamentoArrecadacaoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("gerarRelatorioQuadroComparativoFaturamentoArrecadacao");

		Fachada fachada = Fachada.getInstancia();

		// Form
		ConsultarQuadroComparativoFaturamentoArrecadacaoActionForm form = (ConsultarQuadroComparativoFaturamentoArrecadacaoActionForm) actionForm;

		// ==============================================

		String mesAnoReferenciaStr = form.getMesAnoReferencia();
		String opcaoTotalizacao = form.getOpcaoTotalizacao();

		String gerenciaRegional = null;
		String localidade = null;

		FiltroQuadroComparativoFaturamentoArrecadacaoHelper filtro = new FiltroQuadroComparativoFaturamentoArrecadacaoHelper();

		if(!Util.isVazioOuBranco(mesAnoReferenciaStr)){
			filtro.setAnoMesReferencia(Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaStr));
		}else{
			throw new ActionServletException("Informe o M�s/Ano de Refer�ncia");
		}

		if(!Util.isVazioOuBranco(opcaoTotalizacao)){

			filtro.setOpcaoTotalizacao(Integer.parseInt(opcaoTotalizacao));

			if(filtro.getOpcaoTotalizacao() == FiltroQuadroComparativoFaturamentoArrecadacaoHelper.OPCAO_TOTALIZACAO_GERENCIA_REGIONAL){

				gerenciaRegional = form.getGerenciaRegional();

				if(!Util.isVazioOuBranco(gerenciaRegional)){
					filtro.setIdGerenciaRegional(Integer.valueOf(gerenciaRegional));

					FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
					filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, filtro
									.getIdGerenciaRegional()));

					GerenciaRegional gerencia = (GerenciaRegional) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroGerenciaRegional,
									GerenciaRegional.class.getName()));

					if(gerencia != null){
						filtro.setNomeGerenciaRegional(gerencia.getNome());
					}

				}else{
					throw new ActionServletException("Informe a Ger�ncia Regional");
				}

			}else if(filtro.getOpcaoTotalizacao() == FiltroQuadroComparativoFaturamentoArrecadacaoHelper.OPCAO_TOTALIZACAO_LOCALIDADE){

				localidade = form.getLocalidade();

				if(!Util.isVazioOuBranco(localidade)){
					filtro.setIdLocalidade(Integer.valueOf(localidade));

					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, filtro.getIdLocalidade()));

					Localidade local = (Localidade) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLocalidade,
									Localidade.class.getName()));

					if(local != null){
						filtro.setDescricaoLocalidade(local.getDescricaoComId());
					}
				}else{
					throw new ActionServletException("Informe a Localidade");
				}
			}

		}else{
			throw new ActionServletException("Informe a Op��o de Totaliza��o");
		}

		// ==============================================


		String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";

		RelatorioQuadroComparativoFaturamentoArrecadacao relatorio = new RelatorioQuadroComparativoFaturamentoArrecadacao(
						this.getUsuarioLogado(httpServletRequest));

		relatorio.addParametro("filtroQuadroComparativoFaturamentoArrecadacaoHelper", filtro);
		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}