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

package gcom.gui.relatorio.micromedicao;

import gcom.cadastro.imovel.bean.ImovelMicromedicao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.micromedicao.LeituraConsumoActionForm;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.RelatorioAnaliseConsumo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Vivianne Sousa
 * @date 06/11/2007
 */

public class GerarRelatorioAnaliseConsumoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		LeituraConsumoActionForm leituraConsumoActionForm = (LeituraConsumoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		/*
		 * Colocado por Raphael Rossiter em 04/12/2007 - Analista: Claudio Lira
		 * OBJ: N�o perder os registros selecionados na pagina��o
		 */
		// ========================================================================================
		HashMap<String, String[]> idsImovelPorPagina = this.capturarSelecao(sessao, httpServletRequest);
		String[] idsImovel = null;

		if(idsImovelPorPagina == null){
			Collection colecaoImovelMedicao = (Collection) sessao.getAttribute("colecaoImovelMedicao");
			colecaoImovelMedicao = fachada.filtrarExcecoesLeiturasConsumos((FaturamentoGrupo) sessao.getAttribute("faturamentoGrupo"),
							(FiltroMedicaoHistoricoSql) sessao.getAttribute("filtroMedicaoHistoricoSql"), Integer.valueOf(0), true);
			int pagina = 0, item = 0;
			String[] grupo = new String[10];
			idsImovelPorPagina = new HashMap<String, String[]>();
			for(Object object : colecaoImovelMedicao){
				if(object != null){
					ImovelMicromedicao imovelMicromedicao = (ImovelMicromedicao) object;
					if(item == 10){
						idsImovelPorPagina.put(String.valueOf(pagina), grupo);
						pagina++;
						item = 0;
						grupo = new String[10];
						grupo[item] = String.valueOf(imovelMicromedicao.getImovel().getId());
						item++;
					}else{
						grupo[item] = String.valueOf(imovelMicromedicao.getImovel().getId());
						item++;
					}
				}
			}
			if(item != 10){
				idsImovelPorPagina.put(String.valueOf(pagina), grupo);
			}
			// throw new ActionServletException("atencao.relatorio.vazio");
		}

		// ORDENANDO POR PAGINA
		TreeMap<String, String[]> idsImovelOrdenadosPorPagina = new TreeMap(idsImovelPorPagina);

		idsImovel = this.gerarArray(idsImovelOrdenadosPorPagina);

		// ========================================================================================

		String valoresIdsImovel = "";

		for(int i = 0; i < idsImovel.length; i++){
			if(idsImovel != null && idsImovel[i] != null && !idsImovel[i].equals("")){
				valoresIdsImovel = valoresIdsImovel + idsImovel[i] + ",";
			}
		}

		valoresIdsImovel = valoresIdsImovel.substring(0, valoresIdsImovel.length() - 1);

		// --- Faturamento Grupo
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, Integer.valueOf(leituraConsumoActionForm
						.getIdGrupoFaturamentoFiltro())));
		Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoFaturamentoGrupo);
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// Parte que vai mandar o relat�rio para a tela
		// cria uma inst�ncia da classe do relat�rio
		RelatorioAnaliseConsumo relatorioAnaliseConsumo = new RelatorioAnaliseConsumo((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		relatorioAnaliseConsumo.addParametro("idsImovel", valoresIdsImovel);

		String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		relatorioAnaliseConsumo.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		relatorioAnaliseConsumo.addParametro("mesAnoFaturamento",
						Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesFaturamento().intValue()));
		relatorioAnaliseConsumo.addParametro("mesAnoArrecadacao", Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesArrecadacao()
						.intValue()));
		relatorioAnaliseConsumo.addParametro("grupo", faturamentoGrupo.getDescricao());

		try{
			retorno = processarExibicaoRelatorio(relatorioAnaliseConsumo, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(RelatorioVazioException ex){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}

	/**
	 * Capturar os im�veis selecionados pelo usu�rio
	 * 
	 * @author Raphael Rossiter
	 * @date 05/12/2007
	 */
	public HashMap<String, String[]> capturarSelecao(HttpSession sessao, HttpServletRequest httpServletRequest){

		HashMap<String, String[]> imoveisPorPagina = null;

		// CASO VENHA DA TELA DE ANALISE
		String telaAnalise = httpServletRequest.getParameter("concluir");

		if(telaAnalise == null){

			String paginaCorrente = httpServletRequest.getParameter("paginaCorrente");

			String idsImoveisJuntos = httpServletRequest.getParameter("idRegistrosImovel");
			String[] idsImovel = null;

			if(idsImoveisJuntos != null && idsImoveisJuntos.length() > 0){
				idsImovel = idsImoveisJuntos.split(",");
			}

			imoveisPorPagina = (HashMap) sessao.getAttribute("idsImoveisJaSelecionados");
			sessao.removeAttribute("idsImoveisJaSelecionados");

			if(imoveisPorPagina != null && !imoveisPorPagina.isEmpty()){

				if(imoveisPorPagina.containsKey(paginaCorrente)){

					if(idsImovel != null && idsImovel.length > 0){

						// ATUALIZA��O
						imoveisPorPagina.put(paginaCorrente, idsImovel);
					}else{

						// REMO��O
						// imoveisPorPagina.remove(paginaCorrente);
					}
				}else if(idsImovel != null && idsImovel.length > 0){
					// PAGINA NAO CADASTRADA
					imoveisPorPagina.put(paginaCorrente, idsImovel);
				}

			}else if(idsImovel != null && idsImovel.length > 0){

				// PRIMEIRA SELECAO
				imoveisPorPagina = new HashMap<String, String[]>();
				imoveisPorPagina.put(paginaCorrente, idsImovel);

				sessao.setAttribute("idsImoveisJaSelecionados", imoveisPorPagina);
			}
		}

		return imoveisPorPagina;
	}

	public String[] gerarArray(TreeMap<String, String[]> idsImovelOrdenadosPorPagina){

		String retorno[] = null;
		String paginaAtual[] = null;

		Iterator it = idsImovelOrdenadosPorPagina.values().iterator();

		while(it.hasNext()){

			paginaAtual = (String[]) it.next();

			if(retorno == null){
				retorno = paginaAtual;
			}else{

				String temp[] = retorno;
				String arrayPaginaAtual[] = paginaAtual;

				retorno = new String[temp.length + arrayPaginaAtual.length];
				System.arraycopy(temp, 0, retorno, 0, temp.length);

				System.arraycopy(arrayPaginaAtual, 0, retorno, temp.length, arrayPaginaAtual.length);
			}
		}

		return retorno;
	}

}
