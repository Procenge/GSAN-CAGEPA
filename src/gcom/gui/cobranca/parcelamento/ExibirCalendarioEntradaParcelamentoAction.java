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

package gcom.gui.cobranca.parcelamento;

import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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
 * @date 20/09/2006
 */
public class ExibirCalendarioEntradaParcelamentoAction
				extends GcomAction {

	/**
	 * @author
	 * @date
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("calendarioEntradaParcelamentoAction");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ExibirCalendarioEntradaParcelamentoActionForm form = (ExibirCalendarioEntradaParcelamentoActionForm) actionForm;

		sessao.removeAttribute("elaboracao");
		sessao.removeAttribute("acompanhamento");

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String mesAnoReferencia = (String) httpServletRequest.getParameter("mesAnoReferencia");

		// Recebe o ano e o mes
		String ano = null;

		String mes = null;

		String anoCalendario = (String) httpServletRequest.getParameter("ano");

		String mesCalendario = (String) httpServletRequest.getParameter("mes");

		String anoMesReferencia = null;

		// Verifica se o mes/ano foram mudados no calendario, senao continua o
		// fluxo verificando se o mesanoReferencia foi informado, caso
		// contrario, assume o corrente.
		if(anoCalendario != null && !anoCalendario.equalsIgnoreCase("") && mesCalendario != null && !mesCalendario.equalsIgnoreCase("")){

			Integer aux = new Integer(mesCalendario);

			aux = aux + 1;

			if(aux < 10){

				mesCalendario = "0" + aux.toString();
			}else{
				mesCalendario = aux.toString();
			}

			// [FS0001] Validar mês e ano de referencia

			String dataAuxiliar = null;

			dataAuxiliar = "01/" + mesCalendario + "/" + anoCalendario;

			Date dataValidacao = Util.converteStringParaDate(dataAuxiliar);

			Date dataAtual = new Date();

			if(httpServletRequest.getParameter("elaboracao") != null){

				Calendar dataAtualVerificacao = new GregorianCalendar();
				dataAtualVerificacao.setTime(dataAtual);

				Calendar dataValidacaoVerificacao = new GregorianCalendar();
				dataValidacaoVerificacao.setTime(dataValidacao);

				if(mesCalendario != null && anoCalendario != null){

					String anoMesCalendario = anoCalendario + mesCalendario;

					Date dataCorrente = new Date();
					String dataCorrenteTexto = Util.formatarData(dataCorrente);
					ano = dataCorrenteTexto.substring(6, 10);
					mes = dataCorrenteTexto.substring(3, 5);

					String anoMesAtual = ano + mes;

					if(Util.compararAnoMesReferencia(new Integer(anoMesCalendario), new Integer(anoMesAtual), "<")){
						throw new ActionServletException("atencao.ano_mes_referencia.invalida");
					}

				}else{

					if((dataValidacaoVerificacao.compareTo(dataAtualVerificacao)) < 0){
						throw new ActionServletException("atencao.ano_mes_referencia.invalida");
					}
				}

			}

			httpServletRequest.setAttribute("ano", anoCalendario);
			httpServletRequest.setAttribute("mes", mesCalendario);

			ano = anoCalendario;
			mes = mesCalendario;

			anoMesReferencia = ano + mes;

		}else if(mesAnoReferencia == null || mesAnoReferencia.equals("")){

			Date dataCorrente = new Date();
			String dataCorrenteTexto = Util.formatarData(dataCorrente);
			ano = dataCorrenteTexto.substring(6, 10);
			mes = dataCorrenteTexto.substring(3, 5);
			String anoMesCorrente = ano + mes;
			anoMesReferencia = anoMesCorrente;

		}else{

			boolean mesAnoValido = Util.validarMesAno(mesAnoReferencia);

			if(mesAnoValido == false){
				throw new ActionServletException("atencao.ano_mes_referencia.invalida");
			}

			// [FS0001] Validar mês e ano de referencia
			String dataAuxiliar = null;

			dataAuxiliar = "01/" + mesAnoReferencia;

			Date dataValidacao = Util.converteStringParaDate(dataAuxiliar);

			Date dataAtual = new Date();

			if(httpServletRequest.getParameter("elaboracao") != null){

				Integer difMeses = Util.dataDiff(dataAtual, dataValidacao);

				if(difMeses > 0){
					throw new ActionServletException("atencao.ano_mes_referencia.invalida");

				}
			}

			mes = mesAnoReferencia.substring(0, 2);
			ano = mesAnoReferencia.substring(3, 7);

			anoMesReferencia = ano + mes;

		}

		Calendar calendario = Calendar.getInstance();
		int ultimoDia = 0;

		httpServletRequest.setAttribute("ano", ano);
		httpServletRequest.setAttribute("mes", new Integer(mes) - 1);

		calendario.set(new Integer(ano), new Integer(mes), 1);
		// ultimoDia = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

		ultimoDia = 31;
		Integer[] vetorEstilos = new Integer[ultimoDia];

		mesAnoReferencia = null;

		mesAnoReferencia = mes + "/" + ano;

		// calcular a opção de datas com base no número de dias vencimento da entrada da RD
		Integer numeroDiasVencimentoDaEntrada = (Integer) sessao.getAttribute("numeroDiasVencimentoDaEntrada");

		String idMunicipio = ConstantesAplicacao.get("empresa.municipio");
		Municipio municipio = new Municipio();
		municipio.setId(Util.converterStringParaInteger(idMunicipio));

		Collection collOpcaoDataVencEntrada = new ArrayList();
		if(numeroDiasVencimentoDaEntrada != null){
			for(int i = numeroDiasVencimentoDaEntrada - 1; i >= 0; i--){
				Date dataRetorno = Util.adicionarNumeroDiasDeUmaData(new Date(), i);
				if(Util.getMes(dataRetorno) == Util.converterStringParaInteger(mes)){
					Date dataUtil = this.getFachada().verificarDataUtilVencimento(dataRetorno, municipio);
					collOpcaoDataVencEntrada.add(Util.formatarData(dataUtil));
				}
			}
		}else{
			Date dataUtil = this.getFachada().verificarDataUtilVencimento(new Date(), municipio);
			collOpcaoDataVencEntrada.add(Util.formatarData(dataUtil));
		}

		sessao.setAttribute("acompanhamento", "acompanhamento");
		form.setCabecalho("Acompanhamento");
		form.setTitulo("acompanhamento");

		if(collOpcaoDataVencEntrada != null){

			Iterator it = collOpcaoDataVencEntrada.iterator();
			Date dataEntrada;

			// Integer indicador

			while(it.hasNext()){

				String data = (String) it.next();
				dataEntrada = Util.converteStringParaDate(data);
				/*
				 * Caso exista um intervalo maior do que um dia no abastecimento, verifica a qtde de
				 * dias entre o inicio e o fim as e jogo num vetor
				 * informando o estilo deste dia para o calendário
				 */

				Calendar obterDia = Calendar.getInstance();

				int i = 0;

				form.setCabecalho("Acompanhamento");
				form.setTitulo("acompanhamento");
				while(i < ultimoDia){

					if(collOpcaoDataVencEntrada.size() == 0){
						obterDia.setTime(dataEntrada);
						vetorEstilos[obterDia.get(Calendar.DAY_OF_MONTH) - 1] = ConstantesSistema.SITUACAO_ROTEIRO_ACOMPANHAMENTO_FECHAMENTO_NULO;
					}else{

						obterDia.setTime(dataEntrada);
						vetorEstilos[obterDia.get(Calendar.DAY_OF_MONTH) - 1] = ConstantesSistema.SITUACAO_ROTEIRO_ACOMPANHAMENTO_FECHAMENTO_PREENCHIDO;
					}

					i++;
				}

			}

		}

		int cont = 0;

		Date dataAtual = new Date();

		Integer dia = Util.getDiaMes(dataAtual);

		if(mesCalendario != null && anoCalendario != null && httpServletRequest.getParameter("elaboracao") != null){

			String anoMesCalendario = anoCalendario + mesCalendario;

			Date dataCorrente = new Date();
			String dataCorrenteTexto = Util.formatarData(dataCorrente);
			ano = dataCorrenteTexto.substring(6, 10);
			mes = dataCorrenteTexto.substring(3, 5);

			String anoMesAtual = ano + mes;

			if(!Util.compararAnoMesReferencia(new Integer(anoMesCalendario), new Integer(anoMesAtual), "=")){

				String diaAux = "01";
				dia = new Integer(diaAux);
			}

		}

		String situacoes = "";
		while(cont < vetorEstilos.length){

			if((cont + 2 <= dia.intValue()) && (sessao.getAttribute("elaboracao") != null)){

				vetorEstilos[cont] = ConstantesSistema.SITUACAO_ROTEIRO_ACOMPANHAMENTO_ABERTO;

			}

			if((vetorEstilos[cont] == null) && (sessao.getAttribute("elaboracao") == null)){

				vetorEstilos[cont] = ConstantesSistema.SITUACAO_ROTEIRO_ACOMPANHAMENTO_ABERTO;

			}

			if((vetorEstilos[cont] == null) && (sessao.getAttribute("elaboracao") != null)){

				vetorEstilos[cont] = ConstantesSistema.SITUACAO_ROTEIRO_ELABORACAO_ABERTO;

			}

			if(situacoes.length() < 1){
				situacoes = vetorEstilos[cont].toString();
			}else{
				situacoes = situacoes + "," + vetorEstilos[cont].toString();
			}

			cont++;
		}

		httpServletRequest.setAttribute("situacoes", situacoes);

		return retorno;

	}
}
