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

package gcom.gui.gerencial.micromedicao;

import gcom.fachada.Fachada;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.micromedicao.ResumoAnormalidadeConsultaHelper;
import gcom.gui.GcomAction;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Administrador
 * @date 07/03/2006
 */
public class ExibirResultadoConsultaResumoAnormalidadeAction
				extends GcomAction {

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Administrador
	 * @date 07/03/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("resultadoResumoAnormalidade");
		iniciarProcessamentoDeAtribuirValores(httpServletRequest);

		return retorno;
	}

	public static void iniciarProcessamentoDeAtribuirValores(HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando implementar a parte de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		List listResumoAnormalidadeAgua = (List) sessao.getAttribute("colecaoResumoAnormalidadeAgua");
		List listResumoAnormalidadePoco = (List) sessao.getAttribute("colecaoResumoAnormalidadePoco");

		List listResumoAnormalidadeConsumoAgua = (List) sessao.getAttribute("colecaoResumoAnormalidadeConsumoAgua");
		List listResumoAnormalidadeConsumoEsgoto = (List) sessao.getAttribute("colecaoResumoAnormalidadeConsumoEsgoto");

		List<ResumoAnormalidadeConsultaHelper> colecaoResumoAnormalidadeAgua = new ArrayList<ResumoAnormalidadeConsultaHelper>();
		List<ResumoAnormalidadeConsultaHelper> colecaoResumoAnormalidadePoco = new ArrayList<ResumoAnormalidadeConsultaHelper>();

		List<ResumoAnormalidadeConsultaHelper> colecaoResumoAnormalidadeConsumoAgua = new ArrayList<ResumoAnormalidadeConsultaHelper>();
		List<ResumoAnormalidadeConsultaHelper> colecaoResumoAnormalidadeConsumoEsgoto = new ArrayList<ResumoAnormalidadeConsultaHelper>();

		InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper = (InformarDadosGeracaoRelatorioConsultaHelper) sessao
						.getAttribute("informarDadosGeracaoRelatorioConsultaHelper");

		List<ResumoAnormalidadeConsultaHelper> colecaoResumoAnormalidadeRelatorio = new ArrayList<ResumoAnormalidadeConsultaHelper>();
		List<ResumoAnormalidadeConsultaHelper> colecaoResumoAnormalidadeRelatorioConsumo = new ArrayList<ResumoAnormalidadeConsultaHelper>();

		Iterator iteratorAgua = listResumoAnormalidadeAgua.iterator();
		Iterator iteratorPoco = listResumoAnormalidadePoco.iterator();

		Iterator iteratorConsumoAgua = listResumoAnormalidadeConsumoAgua.iterator();
		Iterator iteratorConsumoEsgoto = listResumoAnormalidadeConsumoEsgoto.iterator();

		Object[] resumoAnormalidadeAgua = null;
		Object[] resumoAnormalidadePoco = null;
		Object[] resumoAnormalidadeConsumoAgua = null;
		Object[] resumoAnormalidadeConsumoEsgoto = null;

		ResumoAnormalidadeConsultaHelper resumoAnormalidadeConsultaHelper = null;

		int somatorioAnormalidadeAgua = 0;
		int somatorioAnormalidadePoco = 0;
		int somatorioAnormalidadeConsumoAgua = 0;
		int somatorioAnormalidadeConsumoEsgoto = 0;
		/**
		 * Divide o resultado da coleção em dois subresultados
		 * um por ligação água e outro por ligação esgoto
		 */
		while(iteratorAgua.hasNext()){

			resumoAnormalidadeAgua = (Object[]) iteratorAgua.next();
			resumoAnormalidadeConsultaHelper = new ResumoAnormalidadeConsultaHelper();

			resumoAnormalidadeConsultaHelper.setIdMedicaoTipo(MedicaoTipo.LIGACAO_AGUA + "");
			resumoAnormalidadeConsultaHelper.setTipoLigacao("TIPO DE MEDIÇÃO: " + MedicaoTipo.DESC_LIGACAO_AGUA);

			setarValoresNoHelperDoVetor(resumoAnormalidadeAgua, resumoAnormalidadeConsultaHelper);

			resumoAnormalidadeConsultaHelper.setParametrosQuebra(resumoAnormalidadeAgua);

			somatorioAnormalidadeAgua += Integer.parseInt(resumoAnormalidadeConsultaHelper.getQuantidadeMedicao());
			colecaoResumoAnormalidadeAgua.add(resumoAnormalidadeConsultaHelper);

		}
		// Ordenação de água.
		atribuirValorPercentual(colecaoResumoAnormalidadeAgua, somatorioAnormalidadeAgua);

		while(iteratorPoco.hasNext()){

			resumoAnormalidadePoco = (Object[]) iteratorPoco.next();
			resumoAnormalidadeConsultaHelper = new ResumoAnormalidadeConsultaHelper();

			resumoAnormalidadeConsultaHelper.setIdMedicaoTipo(MedicaoTipo.POCO + "");
			resumoAnormalidadeConsultaHelper.setTipoLigacao("TIPO DE MEDIÇÃO: " + MedicaoTipo.DESC_POCO);

			setarValoresNoHelperDoVetor(resumoAnormalidadePoco, resumoAnormalidadeConsultaHelper);

			resumoAnormalidadeConsultaHelper.setParametrosQuebra(resumoAnormalidadePoco);
			somatorioAnormalidadePoco += Integer.parseInt(resumoAnormalidadeConsultaHelper.getQuantidadeMedicao());
			colecaoResumoAnormalidadePoco.add(resumoAnormalidadeConsultaHelper);
		}
		// Ordenação de poço.
		atribuirValorPercentual(colecaoResumoAnormalidadePoco, somatorioAnormalidadePoco);

		while(iteratorConsumoAgua.hasNext()){

			resumoAnormalidadeConsumoAgua = (Object[]) iteratorConsumoAgua.next();
			resumoAnormalidadeConsultaHelper = new ResumoAnormalidadeConsultaHelper();

			resumoAnormalidadeConsultaHelper.setIdMedicaoTipo(LigacaoTipo.LIGACAO_AGUA + "");
			resumoAnormalidadeConsultaHelper.setTipoLigacao("TIPO DE LIGAÇÃO: " + LigacaoTipo.DESC_LIGACAO_AGUA);

			setarValoresNoHelperDoVetor(resumoAnormalidadeConsumoAgua, resumoAnormalidadeConsultaHelper);

			resumoAnormalidadeConsultaHelper.setParametrosQuebra(resumoAnormalidadeConsumoAgua);
			somatorioAnormalidadeConsumoAgua += Integer.parseInt(resumoAnormalidadeConsultaHelper.getQuantidadeMedicao());
			colecaoResumoAnormalidadeConsumoAgua.add(resumoAnormalidadeConsultaHelper);
		}
		// Ordenação de poço.
		atribuirValorPercentual(colecaoResumoAnormalidadeConsumoAgua, somatorioAnormalidadeConsumoAgua);

		while(iteratorConsumoEsgoto.hasNext()){

			resumoAnormalidadeConsumoEsgoto = (Object[]) iteratorConsumoEsgoto.next();
			resumoAnormalidadeConsultaHelper = new ResumoAnormalidadeConsultaHelper();

			resumoAnormalidadeConsultaHelper.setIdMedicaoTipo(LigacaoTipo.LIGACAO_ESGOTO + "");
			resumoAnormalidadeConsultaHelper.setTipoLigacao("TIPO DE LIGAÇÃO: " + LigacaoTipo.DESC_LIGACAO_ESGOTO);

			setarValoresNoHelperDoVetor(resumoAnormalidadeConsumoEsgoto, resumoAnormalidadeConsultaHelper);

			resumoAnormalidadeConsultaHelper.setParametrosQuebra(resumoAnormalidadeConsumoEsgoto);
			somatorioAnormalidadeConsumoEsgoto += Integer.parseInt(resumoAnormalidadeConsultaHelper.getQuantidadeMedicao());
			colecaoResumoAnormalidadeConsumoEsgoto.add(resumoAnormalidadeConsultaHelper);
		}
		// Ordenação de poço.
		atribuirValorPercentual(colecaoResumoAnormalidadeConsumoEsgoto, somatorioAnormalidadeConsumoEsgoto);

		// Adiciona a Lista de todos os resumos de anormalidade para (Água e Poço )
		colecaoResumoAnormalidadeRelatorio.addAll(colecaoResumoAnormalidadeAgua);
		colecaoResumoAnormalidadeRelatorio.addAll(colecaoResumoAnormalidadePoco);

		colecaoResumoAnormalidadeRelatorioConsumo.addAll(colecaoResumoAnormalidadeConsumoAgua);
		colecaoResumoAnormalidadeRelatorioConsumo.addAll(colecaoResumoAnormalidadeConsumoEsgoto);

		/** Cria coleção de agrupamento */
		Collection colecaoAgrupamento = fachada.criarColecaoAgrupamentoResumos(informarDadosGeracaoRelatorioConsultaHelper);

		sessao.setAttribute("colecaoResumoAnormalidadeRelatorio", colecaoResumoAnormalidadeRelatorio);
		sessao.setAttribute("colecaoResumoAnormalidadeRelatorioConsumo", colecaoResumoAnormalidadeRelatorioConsumo);

		httpServletRequest.setAttribute("somatorioAnormalidadeAgua", somatorioAnormalidadeAgua + "");
		httpServletRequest.setAttribute("somatorioAnormalidadePoco", somatorioAnormalidadePoco + "");
		httpServletRequest.setAttribute("somatorioAnormalidadeConsumoAgua", somatorioAnormalidadeConsumoAgua + "");
		httpServletRequest.setAttribute("somatorioAnormalidadeConsumoEsgoto", somatorioAnormalidadeConsumoEsgoto + "");

		sessao.setAttribute("colecaoAgrupamento", colecaoAgrupamento);
		sessao.setAttribute("mesAnoReferencia",
						Util.formatarAnoMesParaMesAno(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia()));

		httpServletRequest.setAttribute("colecaoResumoAnormalidadeAgua", colecaoResumoAnormalidadeAgua);
		httpServletRequest.setAttribute("colecaoResumoAnormalidadePoco", colecaoResumoAnormalidadePoco);
		httpServletRequest.setAttribute("colecaoResumoAnormalidadeConsumoAgua", colecaoResumoAnormalidadeConsumoAgua);
		httpServletRequest.setAttribute("colecaoResumoAnormalidadeConsumoEsgoto", colecaoResumoAnormalidadeConsumoEsgoto);
	}

	private static void setarValoresNoHelperDoVetor(Object[] resumoAnormalidadeAgua,
					ResumoAnormalidadeConsultaHelper resumoAnormalidadeConsultaHelper){

		resumoAnormalidadeConsultaHelper.setCodigoLeituraAnormalidadeFaturada(resumoAnormalidadeAgua[1] + "");
		resumoAnormalidadeConsultaHelper.setDescricaoLeituraAnormalidadeFaturada((String) resumoAnormalidadeAgua[2]);
		resumoAnormalidadeConsultaHelper.setDescricaoConsumoAnormalidade((String) resumoAnormalidadeAgua[2]);
		String qtdeMedicao = resumoAnormalidadeAgua[3] == null ? "0" : resumoAnormalidadeAgua[3].toString();
		resumoAnormalidadeConsultaHelper.setQuantidadeMedicao(qtdeMedicao);

	}

	/**
	 * Método que seta o calor do percentual a partir do valor total já somado.
	 * 
	 * @param colecaoResumoDasAnormalidades
	 *            coleção da entidade de resumo de anormalidade por tipo Água ou Poço.
	 * @param somatorioDaAnormalidade
	 *            somatório da anormalidade
	 */
	private static void atribuirValorPercentual(List<ResumoAnormalidadeConsultaHelper> colecaoResumoDasAnormalidades,
					int somatorioDaAnormalidade){

		for(ResumoAnormalidadeConsultaHelper item : colecaoResumoDasAnormalidades){
			String percentual = Util.calcularPercentual(item.getQuantidadeMedicao(), String.valueOf(somatorioDaAnormalidade), 5);
			item.setPercentual(percentual);
		}
	}

}
