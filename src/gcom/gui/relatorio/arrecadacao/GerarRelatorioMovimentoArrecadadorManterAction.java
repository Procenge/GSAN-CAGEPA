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

package gcom.gui.relatorio.arrecadacao;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.fachada.Fachada;
import gcom.gui.arrecadacao.FiltrarMovimentoArrecadadoresActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioManterMovimentoArrecadador;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
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
 * Realiza o filtro do aviso bancario de acordo com os parâmetros informados
 * 
 * @author Vivianne Sousa
 * @created 13/03/2006
 */

public class GerarRelatorioMovimentoArrecadadorManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		/**
		 * @author Rafael Corrêa
		 * @date 05/09/2006
		 * @param actionMapping
		 * @param actionForm
		 * @param httpServletRequest
		 * @param httpServletResponse
		 * @return
		 */

		ActionForward retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// HttpSession sessao = httpServletRequest.getSession(false);

		// FiltroArrecadadorMovimento filtroArrecadadorMovimento = (FiltroArrecadadorMovimento)
		// sessao.getAttribute("filtroArrecadadorMovimento");

		FiltrarMovimentoArrecadadoresActionForm filtrarMovimentoArrecadadoresActionForm = (FiltrarMovimentoArrecadadoresActionForm) actionForm;

		String codigoBanco = "";
		String codigoRemessa = "";
		String descricaoIdentificacaoServico = "";
		String numeroSequencialArquivo = "";
		Date dataGeracaoInicio = null;
		Date dataGeracaoFim = null;
		Date ultimaAlteracaoInicio = null;
		Date ultimaAlteracaoFim = null;
		String descricaoOcorrencia = "";
		String indicadorAceitacao = "";
		String indicadorAbertoFechado = "";
		String idConcessionaria = "";

		if(filtrarMovimentoArrecadadoresActionForm.getBanco() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getBanco().equalsIgnoreCase("")){

			codigoBanco = filtrarMovimentoArrecadadoresActionForm.getBanco().trim();

		}

		if(filtrarMovimentoArrecadadoresActionForm.getRemessa() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getRemessa().equalsIgnoreCase("")){

			codigoRemessa = filtrarMovimentoArrecadadoresActionForm.getRemessa().trim();

		}

		if(filtrarMovimentoArrecadadoresActionForm.getIdentificacaoServico() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getIdentificacaoServico().equalsIgnoreCase("")
						&& !filtrarMovimentoArrecadadoresActionForm.getIdentificacaoServico().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			descricaoIdentificacaoServico = filtrarMovimentoArrecadadoresActionForm.getIdentificacaoServico().trim();

		}

		if(filtrarMovimentoArrecadadoresActionForm.getNsa() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getNsa().equalsIgnoreCase("")){

			numeroSequencialArquivo = filtrarMovimentoArrecadadoresActionForm.getNsa().trim();

		}

		if(filtrarMovimentoArrecadadoresActionForm.getDataGeracaoMovimentoInicio() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getDataGeracaoMovimentoInicio().equalsIgnoreCase("")){

			dataGeracaoInicio = Util.converteStringParaDate(filtrarMovimentoArrecadadoresActionForm.getDataGeracaoMovimentoInicio());
			dataGeracaoFim = dataGeracaoInicio;

		}

		if(filtrarMovimentoArrecadadoresActionForm.getDataGeracaoMovimentoFim() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getDataGeracaoMovimentoFim().equalsIgnoreCase("")){

			dataGeracaoFim = Util.converteStringParaDate(filtrarMovimentoArrecadadoresActionForm.getDataGeracaoMovimentoFim());

		}

		if(filtrarMovimentoArrecadadoresActionForm.getDataProcessamentoMovimentoInicio() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getDataProcessamentoMovimentoInicio().equalsIgnoreCase("")){

			ultimaAlteracaoInicio = Util.converteStringParaDate(filtrarMovimentoArrecadadoresActionForm
							.getDataProcessamentoMovimentoInicio());
			ultimaAlteracaoFim = ultimaAlteracaoInicio;

		}

		if(filtrarMovimentoArrecadadoresActionForm.getDataProcessamentoMovimentoFim() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getDataProcessamentoMovimentoFim().equalsIgnoreCase("")){

			ultimaAlteracaoFim = Util.converteStringParaDate(filtrarMovimentoArrecadadoresActionForm.getDataProcessamentoMovimentoFim());

		}

		if(filtrarMovimentoArrecadadoresActionForm.getMovimentoItemOcorrencia() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getMovimentoItemOcorrencia().equalsIgnoreCase("")
						&& !filtrarMovimentoArrecadadoresActionForm.getMovimentoItemOcorrencia().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			descricaoOcorrencia = filtrarMovimentoArrecadadoresActionForm.getMovimentoItemOcorrencia().trim();

		}

		if(filtrarMovimentoArrecadadoresActionForm.getMovimentoItemAceito() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getMovimentoItemAceito().equalsIgnoreCase("")
						&& !filtrarMovimentoArrecadadoresActionForm.getMovimentoItemAceito().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			indicadorAceitacao = filtrarMovimentoArrecadadoresActionForm.getMovimentoItemAceito().trim();

		}

		if(filtrarMovimentoArrecadadoresActionForm.getIdConcessionaria() != null
						&& !filtrarMovimentoArrecadadoresActionForm.getIdConcessionaria().equalsIgnoreCase("")
						&& !filtrarMovimentoArrecadadoresActionForm.getIdConcessionaria().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			idConcessionaria = filtrarMovimentoArrecadadoresActionForm.getIdConcessionaria().trim();
		}

		// String arrecadadorCodAgente = filtrarMovimentoArrecadadoresActionForm
		// .getBanco();
		// String remessa = filtrarMovimentoArrecadadoresActionForm.getRemessa();
		// String identificacaoServico = filtrarMovimentoArrecadadoresActionForm
		// .getIdentificacaoServico();
		// String sequencial = filtrarMovimentoArrecadadoresActionForm.getNsa();
		// String periodoGeracaoInicialString = filtrarMovimentoArrecadadoresActionForm
		// .getDataGeracaoMovimentoInicio();
		// String periodoGeracaoFinalString = filtrarMovimentoArrecadadoresActionForm
		// .getDataGeracaoMovimentoFim();
		// String periodoProcessamentoInicialString = filtrarMovimentoArrecadadoresActionForm
		// .getDataProcessamentoMovimentoInicio();
		// String periodoProcessamentoFinalString = filtrarMovimentoArrecadadoresActionForm
		// .getDataProcessamentoMovimentoFim();
		// String itemEmOcorrenciaPesquisa = filtrarMovimentoArrecadadoresActionForm
		// .getMovimentoItemOcorrencia();
		// String itemNaoAceitoPesquisa = filtrarMovimentoArrecadadoresActionForm
		// .getMovimentoItemAceito();
		// String movimentoAbertoFechadoPesquisa = filtrarMovimentoArrecadadoresActionForm
		// .getMovimentoAbertoFechado();
		//
		// ArrecadadorMovimento arrecadadorMovimentoParametrosInicial = new ArrecadadorMovimento();
		//
		// ArrecadadorMovimento arrecadadorMovimentoParametrosFinal = new ArrecadadorMovimento();
		//
		Arrecadador arrecadadorParametros = null;
		//		
		//		
		// // String codigoBanco, String codigoRemessa, String descricaoIdentificacaoServico, String
		// numeroSequencialArquivo, Date dataGeracaoInicio, Date dataGeracaoFim, Date
		// ultimaAlteracaoInicio, Date ultimaAlteracaoFim, String descricaoOcorrencia, String
		// indicadorAceitacao, String indicadorAbertoFechado
		//		
		//		
		//		
		// // Passando os Parâmetros para os filtros...
		//
		// Arrecadador
		if((codigoBanco != null) && (!codigoBanco.trim().equals(""))){

			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();

			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, codigoBanco));

			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");

			Collection<Arrecadador> colecaoArrecadadores = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());

			if(colecaoArrecadadores != null && !colecaoArrecadadores.isEmpty()){
				arrecadadorParametros = colecaoArrecadadores.iterator().next();
			}
		}
		//
		// // Remessa
		// if ((remessa != null) && (!remessa.trim().equals(""))
		// && (!remessa.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))) {
		//			
		// arrecadadorMovimentoParametrosInicial.setCodigoRemessa(new Short(remessa));
		//			
		// }
		//		
		// // Identificação do Serviço
		// if ((identificacaoServico != null) && (!identificacaoServico.trim().equals(""))
		// && (!identificacaoServico.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))) {
		//			
		// arrecadadorMovimentoParametrosInicial.setDescricaoIdentificacaoServico(identificacaoServico);
		//			
		// }
		//		
		// // Número Sequencial Arquivo
		// if (sequencial != null && !sequencial.equals("")) {
		// arrecadadorMovimentoParametrosInicial.setNumeroSequencialArquivo(new
		// Integer(sequencial));
		// }
		//
		// // Perído Geração
		// if ((periodoGeracaoInicialString != null)
		// && (!periodoGeracaoInicialString.equals(""))) {
		//
		// Date periodoGeracaoInicial = null;
		//
		// periodoGeracaoInicial = Util
		// .converteStringParaDate(periodoGeracaoInicialString);
		//
		// arrecadadorMovimentoParametrosInicial
		// .setDataGeracao(periodoGeracaoInicial);
		// }
		//
		// if ((periodoGeracaoFinalString != null)
		// && (!periodoGeracaoFinalString.equals(""))) {
		//
		// Date periodoGeracaoFinal = null;
		//
		// periodoGeracaoFinal = Util
		// .converteStringParaDate(periodoGeracaoFinalString);
		//
		// arrecadadorMovimentoParametrosFinal
		// .setDataGeracao(periodoGeracaoFinal);
		// }
		//		
		// // Perído Processamento
		// if ((periodoProcessamentoInicialString != null)
		// && (!periodoProcessamentoInicialString.equals(""))) {
		//
		// Date periodoProcessamentoInicial = null;
		//
		// periodoProcessamentoInicial = Util
		// .converteStringParaDate(periodoProcessamentoInicialString);
		//
		// arrecadadorMovimentoParametrosInicial
		// .setUltimaAlteracao(periodoProcessamentoInicial);
		// }
		//
		// if ((periodoProcessamentoFinalString != null)
		// && (!periodoProcessamentoFinalString.equals(""))) {
		//
		// Date periodoProcessamentoFinal = null;
		//
		// periodoProcessamentoFinal = Util
		// .converteStringParaDate(periodoProcessamentoFinalString);
		//
		// arrecadadorMovimentoParametrosFinal
		// .setUltimaAlteracao(periodoProcessamentoFinal);
		// }
		//
		// // Ítens em Ocorrência
		// String itemEmOcorrencia = "";
		//		
		// if ((itemEmOcorrenciaPesquisa != null) && (!itemEmOcorrenciaPesquisa.trim().equals(""))
		// && (!itemEmOcorrenciaPesquisa.trim().equals("" +
		// ConstantesSistema.NUMERO_NAO_INFORMADO))) {
		//			
		// itemEmOcorrencia = itemEmOcorrenciaPesquisa;
		//			
		// }
		//		
		// // Ítens Não Aceitos
		// String itemNaoAceito = "";
		//		
		// if ((itemNaoAceitoPesquisa != null) && (!itemNaoAceitoPesquisa.trim().equals(""))
		// && (!itemNaoAceitoPesquisa.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))) {
		//			
		// itemNaoAceito = itemNaoAceitoPesquisa;
		//			
		// }
		//
		// // Movimento Abertos/Fechados
		// String movimentoAbertoFechado = "";
		//		
		// if (movimentoAbertoFechadoPesquisa != null && (!movimentoAbertoFechadoPesquisa.equals(""
		// + ConstantesSistema.NUMERO_NAO_INFORMADO))) {
		// movimentoAbertoFechado = movimentoAbertoFechadoPesquisa;
		// }

		// cria uma instância da classe do relatório
		// RelatorioManterMovimentoArrecadador relatorioManterMovimentoArrecadador = new
		// RelatorioManterMovimentoArrecadador(
		// (Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		// relatorioManterMovimentoArrecadador.addParametro("filtroArrecadadorMovimento",filtroArrecadadorMovimento);
		// relatorioManterMovimentoArrecadador.addParametro("arrecadadorMovimentoParametrosInicial",arrecadadorMovimentoParametrosInicial);
		// relatorioManterMovimentoArrecadador.addParametro("arrecadadorMovimentoParametrosFinal",
		// arrecadadorMovimentoParametrosFinal);
		// relatorioManterMovimentoArrecadador.addParametro("arrecadadorParametros",arrecadadorParametros);
		// relatorioManterMovimentoArrecadador.addParametro("movimentoAbertoFechado",movimentoAbertoFechado);
		// relatorioManterMovimentoArrecadador.addParametro("itemEmOcorrencia",itemEmOcorrencia);
		// relatorioManterMovimentoArrecadador.addParametro("itemNaoAceito",itemNaoAceito);

		RelatorioManterMovimentoArrecadador relatorioManterMovimentoArrecadador = new RelatorioManterMovimentoArrecadador(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterMovimentoArrecadador.addParametro("codigoBanco", codigoBanco);
		relatorioManterMovimentoArrecadador.addParametro("codigoRemessa", codigoRemessa);
		relatorioManterMovimentoArrecadador.addParametro("descricaoIdentificacaoServico", descricaoIdentificacaoServico);
		relatorioManterMovimentoArrecadador.addParametro("numeroSequencialArquivo", numeroSequencialArquivo);
		relatorioManterMovimentoArrecadador.addParametro("dataGeracaoInicio", dataGeracaoInicio);
		relatorioManterMovimentoArrecadador.addParametro("dataGeracaoFim", dataGeracaoFim);
		relatorioManterMovimentoArrecadador.addParametro("ultimaAlteracaoInicio", ultimaAlteracaoInicio);
		relatorioManterMovimentoArrecadador.addParametro("ultimaAlteracaoFim", ultimaAlteracaoFim);
		relatorioManterMovimentoArrecadador.addParametro("descricaoOcorrencia", descricaoOcorrencia);
		relatorioManterMovimentoArrecadador.addParametro("indicadorAceitacao", indicadorAceitacao);
		relatorioManterMovimentoArrecadador.addParametro("indicadorAbertoFechado", indicadorAbertoFechado);
		relatorioManterMovimentoArrecadador.addParametro("arrecadadorParametros", arrecadadorParametros);
		relatorioManterMovimentoArrecadador.addParametro("idConcessionaria", idConcessionaria);

		// chama o metódo de gerar relatório passando o código da analise
		// como parâmetro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterMovimentoArrecadador.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		try{
			retorno = processarExibicaoRelatorio(relatorioManterMovimentoArrecadador, tipoRelatorio, httpServletRequest,
							httpServletResponse, actionMapping);

		}catch(SistemaException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}
}
