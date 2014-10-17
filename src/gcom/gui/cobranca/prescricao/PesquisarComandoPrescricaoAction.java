/*
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

package gcom.gui.cobranca.prescricao;

import gcom.cobranca.prescricao.FiltroPrescricaoComando;
import gcom.cobranca.prescricao.PrescricaoComando;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3142] Pesquisar Comando de Prescrição
 * 
 * @author Anderson Italo
 * @since 13/04/2014
 */
public class PesquisarComandoPrescricaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("retornarPesquisarComandoPrescricao");
		HttpSession sessao = httpServletRequest.getSession(false);

		PesquisarComandoPrescricaoActionForm pesquisarComandoPrescricaoActionForm = (PesquisarComandoPrescricaoActionForm) actionForm;

		String periodoGeracaoComandoInicial = pesquisarComandoPrescricaoActionForm.getPeriodoGeracaoComandoInicial();
		String periodoGeracaoComandoFinal = pesquisarComandoPrescricaoActionForm.getPeriodoGeracaoComandoFinal();

		String periodoRealizacaoComandoInicial = pesquisarComandoPrescricaoActionForm.getPeriodoRealizacaoComandoInicial();
		String periodoRealizacaoComandoFinal = pesquisarComandoPrescricaoActionForm.getPeriodoRealizacaoComandoFinal();

		// [FS0003] - Verificar data final menor que data inicial
		if((periodoGeracaoComandoInicial != null && !periodoGeracaoComandoInicial.equals(""))
						&& (periodoGeracaoComandoFinal != null && !periodoGeracaoComandoFinal.equals(""))){

			// inicial
			boolean valida = Util.validarDiaMesAno(periodoGeracaoComandoInicial);
			if(valida){

				throw new ActionServletException("atencao.data_incial_comando.invalida");
			}

			// final
			String anoFinal = periodoGeracaoComandoFinal.substring(6, 10);
			String mesFinal = periodoGeracaoComandoFinal.substring(3, 5);
			String diaFinal = periodoGeracaoComandoFinal.substring(0, 2);

			boolean validaFinal = Util.validarDiaMesAno(periodoGeracaoComandoFinal);
			if(validaFinal){

				throw new ActionServletException("atencao.data_final_comando.invalida");
			}

			Calendar periodoFinal = new GregorianCalendar();
			periodoFinal.set(Calendar.DATE, Integer.valueOf(diaFinal).intValue());
			periodoFinal.set(Calendar.MONTH, (Integer.valueOf(mesFinal).intValue() - 1));
			periodoFinal.set(Calendar.YEAR, Integer.valueOf(anoFinal).intValue());

			if(periodoFinal.compareTo(new GregorianCalendar()) > 0){

				// Data Final do Período de Geração do Comando posterior à data corrente
				throw new ActionServletException("atencao.data_final_comando.maior.data_corrente");
			}

			Calendar calendarPeriodoGeracaoComandoInicial = new GregorianCalendar();
			calendarPeriodoGeracaoComandoInicial.setTime(Util.converteStringParaDate(periodoGeracaoComandoInicial, true));

			Calendar calendarPeriodoGeracaoComandoFinal = new GregorianCalendar();
			calendarPeriodoGeracaoComandoFinal.setTime(Util.converteStringParaDate(periodoGeracaoComandoFinal, true));

			if(calendarPeriodoGeracaoComandoInicial.compareTo(calendarPeriodoGeracaoComandoFinal) > 0){

				httpServletRequest.setAttribute("nomeCampo", "periodoGeracaoComandoFinal");
				throw new ActionServletException("atencao.geracao.data_inicial_comando.maior_data_final_comando");
			}
		}

		// [FS0003] - Verificar data final menor que data inicial
		if((periodoRealizacaoComandoInicial != null && !periodoRealizacaoComandoInicial.equals(""))
						&& (periodoRealizacaoComandoFinal != null && !periodoRealizacaoComandoFinal.equals(""))){

			// inicial
			boolean valida = Util.validarDiaMesAno(periodoRealizacaoComandoInicial);
			if(valida){

				throw new ActionServletException("atencao.data_inicial_realizacao.invalida");
			}

			// final
			boolean validaFinal = Util.validarDiaMesAno(periodoRealizacaoComandoFinal);
			if(validaFinal){

				throw new ActionServletException("atencao.data_final_realizacao.invalida");
			}

			// final
			String anoFinal = periodoRealizacaoComandoFinal.substring(6, 10);
			String mesFinal = periodoRealizacaoComandoFinal.substring(3, 5);
			String diaFinal = periodoRealizacaoComandoFinal.substring(0, 2);

			Calendar periodoFinal = new GregorianCalendar();
			periodoFinal.set(Calendar.DATE, Integer.valueOf(diaFinal).intValue());
			periodoFinal.set(Calendar.MONTH, (Integer.valueOf(mesFinal).intValue() - 1));
			periodoFinal.set(Calendar.YEAR, Integer.valueOf(anoFinal).intValue());

			if(periodoFinal.compareTo(new GregorianCalendar()) > 0){

				// Data Final do Período de Execução do Comando posterior à data corrente
				throw new ActionServletException("atencao.data_final_realizacao.maior.data_corrente");
			}

			Calendar calendarPeriodoRealizacaoComandoInicial = new GregorianCalendar();

			calendarPeriodoRealizacaoComandoInicial.setTime(Util.converteStringParaDate(periodoRealizacaoComandoInicial, true));

			Calendar calendarPeriodoRealizacaoComandoFinal = new GregorianCalendar();
			calendarPeriodoRealizacaoComandoFinal.setTime(Util.converteStringParaDate(periodoRealizacaoComandoFinal, true));

			if(calendarPeriodoRealizacaoComandoInicial.compareTo(calendarPeriodoRealizacaoComandoFinal) > 0){

				// Data Final do Período de Execução do Comando anterior à Data Inicial
				httpServletRequest.setAttribute("nomeCampo", "periodoRealizacaoComandoFinal");
				throw new ActionServletException("atencao.data_inicial_realizacao_comando.maior_data_final_comando");
			}
		}

		FiltroPrescricaoComando filtroPrescricaoComando = new FiltroPrescricaoComando();

		// Título do Comando
		String tituloComando = pesquisarComandoPrescricaoActionForm.getTituloComando();
		String tipoPesquisa = (String) pesquisarComandoPrescricaoActionForm.getTipoPesquisa();

		if(tituloComando != null && !tituloComando.trim().equalsIgnoreCase("")){

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroPrescricaoComando.adicionarParametro(new ComparacaoTextoCompleto(FiltroPrescricaoComando.TITULO_COMANDO,
								tituloComando));
			}else{

				filtroPrescricaoComando.adicionarParametro(new ComparacaoTexto(FiltroPrescricaoComando.TITULO_COMANDO, tituloComando));
			}
		}

		// Período do Comando
		if((periodoGeracaoComandoInicial != null && !periodoGeracaoComandoInicial.equals(""))
						&& (periodoGeracaoComandoFinal != null && !periodoGeracaoComandoFinal.equals(""))){

			Intervalo intervalo = new Intervalo(FiltroPrescricaoComando.DATA_COMANDO,
							Util
							.converteStringParaDateHora(periodoGeracaoComandoInicial + " 00:00:00"), Util
							.converteStringParaDateHora(periodoGeracaoComandoFinal + " 23:59:59"));

			filtroPrescricaoComando.adicionarParametro(intervalo);
		}

		// Período de Realização do Comando
		if((periodoRealizacaoComandoInicial != null && !periodoRealizacaoComandoInicial.equals(""))
						&& (periodoRealizacaoComandoFinal != null && !periodoRealizacaoComandoFinal.equals(""))){

			Intervalo intervalo = new Intervalo(FiltroPrescricaoComando.DATA_REALIZACAO,
							Util.converteStringParaDateHora(periodoRealizacaoComandoInicial + " 00:00:00"),
							Util.converteStringParaDateHora(periodoRealizacaoComandoFinal + " 23:59:59"));

			filtroPrescricaoComando.adicionarParametro(intervalo);
		}

		// Usuário que Gerou o Comando
		if(pesquisarComandoPrescricaoActionForm.getIdUsuario() != null && !pesquisarComandoPrescricaoActionForm.getIdUsuario().equals("")){

			filtroPrescricaoComando.adicionarParametro(new ParametroSimples(FiltroPrescricaoComando.USUARIO_ID,
							pesquisarComandoPrescricaoActionForm.getIdUsuario()));
		}


		/*
		 * Na seleção dos comandos, além dos critérios do filtro, considerar apenas os comandos de
		 * geração (PRCM_ICSIMULACAO com o valor 2 (dois) na tabela PRESCRICAO_COMANDO) e os
		 * comandos realizados (PRCM_TMREALIZACAO com o valor diferente de nulo na tabela
		 * PRESCRICAO_COMANDO)
		 */
		filtroPrescricaoComando
						.adicionarParametro(new ParametroSimples(FiltroPrescricaoComando.INDICADOR_SIMULACAO, ConstantesSistema.NAO));
		filtroPrescricaoComando.adicionarParametro(new ParametroNaoNulo(FiltroPrescricaoComando.DATA_REALIZACAO));

		filtroPrescricaoComando.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComando.USUARIO);
		filtroPrescricaoComando.setCampoOrderByDesc(FiltroPrescricaoComando.DATA_COMANDO);

		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroPrescricaoComando, PrescricaoComando.class.getName());

		Collection colecaoPrescricaoComando = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		sessao.setAttribute("colecaoPrescricaoComando", colecaoPrescricaoComando);

		// [FS0005 - Nenhum registro encontrado]
		if(colecaoPrescricaoComando == null || colecaoPrescricaoComando.isEmpty()){

			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}

}
