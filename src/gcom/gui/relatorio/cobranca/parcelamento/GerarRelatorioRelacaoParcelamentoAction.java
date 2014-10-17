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

package gcom.gui.relatorio.cobranca.parcelamento;

import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.bean.FiltrarRelacaoParcelamentoHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.cobranca.parcelamento.GerarRelatorioRelacaoParcelamentoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.parcelamento.RelacaoParcelamentoRelatorioHelper;
import gcom.relatorio.cobranca.parcelamento.RelatorioRelacaoParcelamento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0594] Gerar Rela��o de Parcelamento
 * 
 * @author Ana Maria
 * @date 30/05/2007
 */
public class GerarRelatorioRelacaoParcelamentoAction
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

		Fachada fachada = Fachada.getInstancia();

		// HttpSession sessao = httpServletRequest.getSession(false);

		// cria uma inst�ncia da classe do relat�rio
		RelatorioRelacaoParcelamento relatorioRelacaoParcelamento = new RelatorioRelacaoParcelamento((Usuario) (httpServletRequest
						.getSession(false)).getAttribute("usuarioLogado"));

		GerarRelatorioRelacaoParcelamentoActionForm form = (GerarRelatorioRelacaoParcelamentoActionForm) actionForm;

		String idLocalidade = form.getIdLocalidade();
		String codigoSetorComercial = form.getIdSetorComercial();
		String nnQuadra = form.getIdQuadra();

		String idSituacaoParcelamento = form.getIdSituacaoParcelamento();

		Parcelamento parcelamento = new Parcelamento();
		FiltrarRelacaoParcelamentoHelper filtroParcelamento = new FiltrarRelacaoParcelamentoHelper();

		boolean peloMenosUmParametroInformado = false;

		// Insere os par�metros informados no filtro
		if(idLocalidade != null && !idLocalidade.trim().equals("")){

			peloMenosUmParametroInformado = true;
			Localidade localidade = new Localidade();
			localidade.setId(new Integer(idLocalidade));
			parcelamento.setLocalidade(localidade);

			if(codigoSetorComercial != null && !codigoSetorComercial.trim().equals("")){
				parcelamento.setCodigoSetorComercial(new Integer(codigoSetorComercial));
			}
			if(nnQuadra != null && !nnQuadra.trim().equals("")){
				parcelamento.setNumeroQuadra(new Integer(nnQuadra));
			}
		}

		Integer idGerencia = null;
		if(form.getIdGerenciaRegional() != null && !form.getIdGerenciaRegional().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			idGerencia = new Integer(form.getIdGerenciaRegional());
		}

		Integer idUnidadeNegocio = null;
		if(form.getIdUnidadeNegocio() != null && !form.getIdUnidadeNegocio().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			idUnidadeNegocio = new Integer(form.getIdUnidadeNegocio());
		}

		if(idSituacaoParcelamento != null && !idSituacaoParcelamento.trim().equals("")){
			peloMenosUmParametroInformado = true;

			ParcelamentoSituacao parcelamentoSituacao = new ParcelamentoSituacao();
			parcelamentoSituacao.setId(new Integer(idSituacaoParcelamento));
			parcelamento.setParcelamentoSituacao(parcelamentoSituacao);
		}

		Collection<Integer> idsMotivoDesfazimento = new ArrayList<Integer>();
		if(form.getIdsMotivoDesfazimento() != null && form.getIdsMotivoDesfazimento().length > 0){
			String[] motivoDesfazimento = form.getIdsMotivoDesfazimento();
			for(int i = 0; i < motivoDesfazimento.length; i++){
				if(new Integer(motivoDesfazimento[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					idsMotivoDesfazimento.add(Integer.valueOf(motivoDesfazimento[i]));
					// passar a cole��o de especifica��o por par�metro
					peloMenosUmParametroInformado = true;
				}
			}
		}

		Date dataParcelamentoInicial = null;
		Date dataParcelamentoFinal = null;

		if(form.getDataParcelamentoInicial() != null && !form.getDataParcelamentoInicial().equals("")){

			dataParcelamentoInicial = Util.converteStringParaDate(form.getDataParcelamentoInicial());
			dataParcelamentoInicial = Util.formatarDataInicial(dataParcelamentoInicial);

			if(form.getDataParcelamentoFinal() != null && !form.getDataParcelamentoFinal().equals("")){
				dataParcelamentoFinal = Util.converteStringParaDate(form.getDataParcelamentoFinal());
				dataParcelamentoFinal = Util.adaptarDataFinalComparacaoBetween(dataParcelamentoFinal);
			}else{
				dataParcelamentoFinal = new Date();
				dataParcelamentoFinal = Util.formatarDataFinal(dataParcelamentoFinal);
			}
			// [FS005] Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataParcelamentoInicial, dataParcelamentoFinal);
			if(qtdeDias < 0){
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
			// passar as datas de atendimento por par�metro
			peloMenosUmParametroInformado = true;
		}

		String valorDebitoInicial = form.getValorDebitoInicial();
		String valorDebitoFinal = form.getValorDebitoFinal();
		BigDecimal valorInicial = null;
		BigDecimal valorFinal = null;

		// Verifica se o campo valorDebitoInicial e valorDebitoFinal foram informados
		if(valorDebitoInicial != null && !valorDebitoInicial.trim().equalsIgnoreCase("") && valorDebitoFinal != null
						&& !valorDebitoFinal.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			String valorSemPontosInicial = valorDebitoInicial.replace(".", "");
			valorDebitoInicial = valorSemPontosInicial.replace(",", ".");

			String valorSemPontosFinal = valorDebitoFinal.replace(".", "");
			valorDebitoFinal = valorSemPontosFinal.replace(",", ".");

			valorInicial = new BigDecimal(valorDebitoInicial);
			valorFinal = new BigDecimal(valorDebitoFinal);
			Integer resultado = valorInicial.compareTo(valorFinal);

			if(resultado == 1){
				throw new ActionServletException("atencao.valor_servico_final_menor_valor_servico_inicial");
			}
		}

		Collection<RelacaoParcelamentoRelatorioHelper> colecaoRelacaoParcelamento = null;

		if(peloMenosUmParametroInformado){

			colecaoRelacaoParcelamento = new ArrayList<RelacaoParcelamentoRelatorioHelper>();

			filtroParcelamento.setParcelamento(parcelamento);
			filtroParcelamento.setDataParcelamentoInicial(dataParcelamentoInicial);
			filtroParcelamento.setDataParcelamentoFinal(dataParcelamentoFinal);
			filtroParcelamento.setIdsMotivoDesfazimento(idsMotivoDesfazimento);
			filtroParcelamento.setValorDebitoInicial(valorInicial);
			filtroParcelamento.setValorDebitoFinal(valorFinal);
			filtroParcelamento.setIdGerencia(idGerencia);
			filtroParcelamento.setIdUnidadeNegocio(idUnidadeNegocio);

			colecaoRelacaoParcelamento = fachada.filtrarRelacaoParcelamento(filtroParcelamento);

		}else{
			throw new ActionServletException("atencao.filtrar_informar_um_filtro");
		}

		if(colecaoRelacaoParcelamento == null || colecaoRelacaoParcelamento.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		relatorioRelacaoParcelamento.addParametro("colecaoRelacaoParcelamento", colecaoRelacaoParcelamento);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		Integer idSituacao = new Integer(form.getIdSituacaoParcelamento());
		String situacao = null;
		if(idSituacao.equals(ParcelamentoSituacao.NORMAL)){
			situacao = "NORMAL";
		}else{
			situacao = "DESFEITO";
		}

		String faixaValores = null;
		if(!valorDebitoInicial.equals("") && !valorDebitoFinal.equals("")){
			faixaValores = Util.formatarMoedaReal(valorInicial) + " a " + Util.formatarMoedaReal(valorFinal);
		}

		String periodo = null;
		if(dataParcelamentoInicial != null && dataParcelamentoFinal != null){
			periodo = Util.formatarData(dataParcelamentoInicial) + " a " + Util.formatarData(dataParcelamentoFinal);
		}

		String cabecalho = "RELA��O DE PARCELAMENTOS - " + situacao + " - MARKETING ATIVO";
		relatorioRelacaoParcelamento.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		relatorioRelacaoParcelamento.addParametro("cabecalho", cabecalho);
		relatorioRelacaoParcelamento.addParametro("faixaValores", faixaValores);
		relatorioRelacaoParcelamento.addParametro("periodo", periodo);

		try{

			retorno = processarExibicaoRelatorio(relatorioRelacaoParcelamento, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(SistemaException ex){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;

	}
}
