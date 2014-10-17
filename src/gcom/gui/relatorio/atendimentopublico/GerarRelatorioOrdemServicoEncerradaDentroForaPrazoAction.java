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

package gcom.gui.relatorio.atendimentopublico;

import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.ordemservico.GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioOrdemServicoEncerradaDentroForaPrazo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Victon Santos
 * @created 26/12/2013
 */
public class GerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm form = (GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm) actionForm;

		// Recupera os valores do form para serem passados como par�metros para
		// o RelatorioAcompanhamentoExecucaoOS e nele executar a pesquisa
		// solicitada pelo usu�rio
		String origemServico = form.getOrigemServico();
		String[] idsServicosTipos = form.getTipoServicoSelecionados();
		String idUnidadeAtendimento = form.getIdUnidadeAtendimento();
		String idUnidadeAtual = form.getIdUnidadeAtual();
		String idUnidadeEncerramento = form.getIdUnidadeEncerramento();
		String periodoAtendimentoInicial = form.getPeriodoAtendimentoInicial();
		String periodoAtendimentoFinal = form.getPeriodoAtendimentoFinal();
		String periodoEncerramentoInicial = form.getPeriodoEncerramentoInicial();
		String periodoEncerramentoFinal = form.getPeriodoEncerramentoFinal();
		String tipoOrdenacao = form.getTipoOrdenacao();
		String idLocalidade = form.getIdLocalidadeFiltro();

		// Verifica qual o relat�rio dever� ser gerado.
		String relatorioTipo = form.getRelatorioTipo();

		// Formata as datas para Date
		Date periodoAtendimentoInicialFormatado = null;

		if(periodoAtendimentoInicial != null && !periodoAtendimentoInicial.trim().equals("")){
			periodoAtendimentoInicialFormatado = Util.converteStringParaDate(periodoAtendimentoInicial);
		}

		Date periodoAtendimentoFinalFormatado = null;

		if(periodoAtendimentoFinal != null && !periodoAtendimentoFinal.trim().equals("")){
			periodoAtendimentoFinalFormatado = Util.converteStringParaDate(periodoAtendimentoFinal);
		}

		Date periodoEncerramentoInicialFormatado = null;

		if(periodoEncerramentoInicial != null && !periodoEncerramentoInicial.trim().equals("")){
			periodoEncerramentoInicialFormatado = Util.converteStringParaDate(periodoEncerramentoInicial);
		}

		Date periodoEncerramentoFinalFormatado = null;

		if(periodoEncerramentoFinal != null && !periodoEncerramentoFinal.trim().equals("")){
			periodoEncerramentoFinalFormatado = Util.converteStringParaDate(periodoEncerramentoFinal);
		}

		validarGeracaoRelatorio(origemServico, idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento,
						periodoAtendimentoInicialFormatado, periodoAtendimentoFinalFormatado, periodoEncerramentoInicialFormatado,
						periodoEncerramentoFinalFormatado, relatorioTipo, idLocalidade);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		TarefaRelatorio relatorio = null;
		relatorio = new RelatorioOrdemServicoEncerradaDentroForaPrazo(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));


		if(relatorio != null){
			relatorio.addParametro("origemServico", origemServico);
			relatorio.addParametro("idUnidadeAtual", idUnidadeAtual);
			relatorio.addParametro("idsServicosTipos", idsServicosTipos);
			relatorio.addParametro("idUnidadeAtendimento", idUnidadeAtendimento);
			relatorio.addParametro("idUnidadeEncerramento", idUnidadeEncerramento);
			relatorio.addParametro("periodoAtendimentoInicial", periodoAtendimentoInicialFormatado);
			relatorio.addParametro("periodoAtendimentoFinal", periodoAtendimentoFinalFormatado);
			relatorio.addParametro("periodoEncerramentoInicial", periodoEncerramentoInicialFormatado);
			relatorio.addParametro("periodoEncerramentoFinal", periodoEncerramentoFinalFormatado);
			relatorio.addParametro("tipoOrdenacao", tipoOrdenacao);
			relatorio.addParametro("idLocalidade", idLocalidade);

			if(tipoRelatorio == null){
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}

			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;

	}

	private void validarGeracaoRelatorio(String origemServico, String[] idsServicosTipos, String idUnidadeAtendimento,
					String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial, Date periodoAtendimentoFinal,
					Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String relatorioTipo, String idLocalidade){

		Fachada fachada = Fachada.getInstancia();

		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = null;
		Collection colecaoUnidade = null;

		// Verifica se a unidade de atendimento existe
		if(idUnidadeAtendimento != null && !idUnidadeAtendimento.equals("")){

			filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeAtendimento));

			colecaoUnidade = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(colecaoUnidade == null || colecaoUnidade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade de Atendimento");
			}

		}

		// Verifica se a unidade atual existe
		if(idUnidadeAtual != null && !idUnidadeAtual.equals("")){

			colecaoUnidade = null;

			filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeAtual));

			colecaoUnidade = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(colecaoUnidade == null || colecaoUnidade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Atual");
			}

		}

		// Verifica se a unidade de encerramento existe
		if(idUnidadeEncerramento != null && !idUnidadeEncerramento.equals("")){

			colecaoUnidade = null;

			filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeEncerramento));

			colecaoUnidade = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(colecaoUnidade == null || colecaoUnidade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade de Encerramento");
			}

		}

		// Verifica se a pesquisa retorno algum resultado
		int qtdeResultados = 0;

		qtdeResultados = fachada.pesquisaTotalRegistrosRelatorioOrdemServicoEncerradaDentroForaPrazo(origemServico,
						idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
						periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idLocalidade);

		if(qtdeResultados == 0){
			// Caso a pesquisa n�o retorne nenhum resultado comunica ao usu�rio;
			throw new ActionServletException("atencao.relatorio.vazio");
		}

	}
}
