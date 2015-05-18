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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.Atendimento;
import gcom.cadastro.atendimento.FiltroAtendimento;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.DocumentoEmissaoForma;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

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
 * Description of the Class
 * 
 * @author compesa
 * @created
 */
/**
 * Descrição da classe
 * 
 * @author Administrador
 * @date 07/03/2006
 */
public class FiltrarAtendimentosRealizadosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarAtendimentosRealizadosResultado");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltrarAtendimentosRealizadosActionForm form = (FiltrarAtendimentosRealizadosActionForm) actionForm;

		FiltroAtendimento filtroAtendimento = new FiltroAtendimento();

		boolean informUm = false;

		// Período de Data de Emissão
		// ==============================================================================
		String dataInicial = form.getdataHoraInicioAtendimento();
		String dataFinal = form.getdataHoraFimAtendimento();

		if((dataInicial.trim().length() == 10) && (dataFinal.trim().length() == 10)){

			Calendar calendarInicio = new GregorianCalendar();
			Calendar calendarFim = new GregorianCalendar();

			calendarInicio.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dataInicial.substring(0, 2)).intValue());
			calendarInicio.set(Calendar.MONTH, Integer.valueOf(dataInicial.substring(3, 5)).intValue() - 1);
			calendarInicio.set(Calendar.YEAR, Integer.valueOf(dataInicial.substring(6, 10)).intValue());
			calendarInicio.set(Calendar.HOUR_OF_DAY, 00);
			calendarInicio.set(Calendar.MINUTE, 00);
			calendarInicio.set(Calendar.SECOND, 00);

			calendarFim.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dataFinal.substring(0, 2)).intValue());
			calendarFim.set(Calendar.MONTH, Integer.valueOf(dataFinal.substring(3, 5)).intValue() - 1);
			calendarFim.set(Calendar.YEAR, Integer.valueOf(dataFinal.substring(6, 10)).intValue());
			calendarFim.set(Calendar.HOUR_OF_DAY, 23);
			calendarFim.set(Calendar.MINUTE, 59);
			calendarFim.set(Calendar.SECOND, 59);

			if(calendarFim.compareTo(calendarInicio) < 0){
				throw new ActionServletException("atencao.data_fim_menor_inicio");
			}

			filtroAtendimento.adicionarParametro(new Intervalo(FiltroAtendimento.DATA_INICIO_ATENDIMENTO, calendarInicio.getTime(),
							calendarFim.getTime()));

			informUm = true;
		}


		if(form.getIdFuncionalidade() != null && !form.getIdFuncionalidade().equals("")
						&& !form.getIdFuncionalidade().equals(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			filtroAtendimento.adicionarParametro(new ParametroSimples(FiltroAtendimento.ID_PROCEDIMENTO_ATEND_FUNCIONALIDADE, form
							.getIdFuncionalidade()));

			informUm = true;
		}

		if(form.getIdCliente()  != null
						&& !form.getIdCliente().equals("")
						&& !form.getIdCliente().equals(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			filtroAtendimento.adicionarParametro(new ParametroSimples(FiltroAtendimento.ID_CLIENTE,
							form.getIdCliente()));

			informUm = true;
		}

		if(form.getIdImovel() != null && !form.getIdImovel().equals("")
						&& !form.getIdImovel().equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			filtroAtendimento.adicionarParametro(new ParametroSimples(FiltroAtendimento.ID_IMOVEL, form.getIdImovel()));

			informUm = true;
		}

		if(form.getIdSolicitacaoTipo() != null && !form.getIdSolicitacaoTipo().equals("")
						&& !form.getIdSolicitacaoTipo().equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			filtroAtendimento.adicionarParametro(new ParametroSimples(FiltroAtendimento.ID_PROCEDIMENTO_ATEND_TIPO_ESPECIFICACAO_TIPO, form
							.getIdSolicitacaoTipo()));

			informUm = true;
		}

		if(form.getIdSolicitacaoTipoEspecificacao() != null && !form.getIdSolicitacaoTipoEspecificacao().equals("")
						&& !form.getIdSolicitacaoTipoEspecificacao().equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			filtroAtendimento.adicionarParametro(new ParametroSimples(FiltroAtendimento.ID_PROCEDIMENTO_ATEND_TIPO_ESPECIFICACAO, form
							.getIdSolicitacaoTipoEspecificacao()));

			informUm = true;
		}

		if(!informUm && httpServletRequest.getParameter("page.offset") == null){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATENDIMENTO);
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATEND_FUNCIONALIDADE);
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATEND_TIPO_ESPECIFICACAO);
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATEND_TIPO_ESPECIFICACAO_TIPO);
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.IMOVEL);
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.CLIENTE);
		filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.USUARIO);
		filtroAtendimento.setCampoOrderByDesc(FiltroAtendimento.DATA_INICIO_ATENDIMENTO);

		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroAtendimento, Atendimento.class.getName());

		Collection colecaoAtendimento = (Collection) resultado.get("colecaoRetorno");

		retorno = (ActionForward) resultado.get("destinoActionForward");

		if(colecaoAtendimento == null || colecaoAtendimento.isEmpty()){
			// [FS0010] Nenhum registro encontrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "");
		}

		sessao.setAttribute("colecaoAtendimento", colecaoAtendimento);

		return retorno;

	}

	private String obterDescricaoCobrancaAcao(CobrancaDocumento cobrancaDocumento){

		String retorno = "";

		if(cobrancaDocumento.getDocumentoEmissaoForma() != null && cobrancaDocumento.getCobrancaAcao() != null){

			if(cobrancaDocumento.getDocumentoEmissaoForma().getId().intValue() == DocumentoEmissaoForma.CRONOGRAMA.intValue()){

				retorno = cobrancaDocumento.getCobrancaAcao().getDescricaoCobrancaAcao();

			}else if(cobrancaDocumento.getDocumentoEmissaoForma().getId().intValue() == DocumentoEmissaoForma.EVENTUAL.intValue()){

				retorno = cobrancaDocumento.getCobrancaAcao().getDescricaoCobrancaAcao();

			}

		}

		return retorno;

	}

}
