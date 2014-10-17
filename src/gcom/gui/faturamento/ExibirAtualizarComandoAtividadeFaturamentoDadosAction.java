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

package gcom.gui.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.faturamento.FiltroFaturamentoAtivCronRota;
import gcom.faturamento.FiltroFaturamentoAtividadeCronograma;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Este caso de uso permite alterar ou excluir um comando de atividade de faturamento
 * [UC0104] Manter Comando Atividade de Faturamento
 * 
 * @author Roberta Costa
 * @date 20/07/2006
 */
public class ExibirAtualizarComandoAtividadeFaturamentoDadosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarComandoAtividadeFaturamentoDados");

		// Carrega a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Carrega o objeto sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		InserirComandoAtividadeFaturamentoActionForm inserirComandoAtividadeFaturamentoActionForm = (InserirComandoAtividadeFaturamentoActionForm) actionForm;

		// Faturamento Cronograma Atividade selecionado
		String faturamentoCronogramaAtividadeID = httpServletRequest.getParameter("faturamentoAtividadeCronogramaID");

		if(faturamentoCronogramaAtividadeID == null){

			faturamentoCronogramaAtividadeID = (String) httpServletRequest.getSession().getAttribute("faturamentoAtividadeCronogramaID");

		}

		if(faturamentoCronogramaAtividadeID != null && !faturamentoCronogramaAtividadeID.equalsIgnoreCase("")){

			// Removendo a condicional referente ao campo de vencimento do grupo
			// Tem como fun��o ocultar ou n�o o campo citado acima
			sessao.removeAttribute("exibirCampoVencimentoGrupo");

			// Gerando o objeto faturamentoCronogramaAtividade a partir do ID recebido
			Collection colecaoFaturamentoCronogramaAtividade;
			FaturamentoAtividadeCronograma faturamentoAtividadeCronograma;

			FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = new FiltroFaturamentoAtividadeCronograma();

			// Objetos que ser�o retornados pelo hibernate
			filtroFaturamentoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("faturamentoAtividade");
			filtroFaturamentoAtividadeCronograma
							.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupoCronogramaMensal.faturamentoGrupo");

			filtroFaturamentoAtividadeCronograma.adicionarParametro(new ParametroSimples(FiltroFaturamentoAtividadeCronograma.ID,
							faturamentoCronogramaAtividadeID));

			colecaoFaturamentoCronogramaAtividade = fachada.pesquisar(filtroFaturamentoAtividadeCronograma,
							FaturamentoAtividadeCronograma.class.getName());

			faturamentoAtividadeCronograma = (FaturamentoAtividadeCronograma) Util
							.retonarObjetoDeColecao(colecaoFaturamentoCronogramaAtividade);

			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

			// Dados do comando que ser�o disponibilizados na tela
			// Grupo
			FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal = faturamentoAtividadeCronograma
							.getFaturamentoGrupoCronogramaMensal();
			FaturamentoGrupo faturamentoGrupo = faturamentoGrupoCronogramaMensal.getFaturamentoGrupo();
			inserirComandoAtividadeFaturamentoActionForm.setGrupoFaturamentoID(faturamentoGrupo.getDescricao());

			sessao.setAttribute("faturamentoGrupoId", faturamentoGrupo.getId());

			// Ano e M�s de referencia
			inserirComandoAtividadeFaturamentoActionForm.setReferenciaFaturamento(Util
							.formatarAnoMesParaMesAno(faturamentoGrupoCronogramaMensal.getAnoMesReferencia().intValue()));
			// Atividade
			FaturamentoAtividade faturamentoAtividade = faturamentoAtividadeCronograma.getFaturamentoAtividade();
			inserirComandoAtividadeFaturamentoActionForm.setAtividadeFaturamentoID(faturamentoAtividade.getDescricao());

			// Data de vencimento do grupo
			Integer idFaturamentoAtividade = faturamentoAtividade.getId();

			/*
			 * N�mero m�nimo de dias (PARM_NNMINIMODIASEMISSAOVENCIMENTO da
			 * tabela SISTEMA_PARAMETROS)
			 */
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			if(sessao.getAttribute("dataCorrente") == null && sistemaParametro != null){

				if(sistemaParametro.getNumeroMinimoDiasEmissaoVencimento() != null){

					Calendar dataCorrente = new GregorianCalendar();
					dataCorrente.add(Calendar.DATE, sistemaParametro.getNumeroMinimoDiasEmissaoVencimento().intValue());

					sessao.setAttribute("dataCorrente", formatoData.format(dataCorrente.getTime()));
				}

			}

			/*
			 * Lista das rotas do comando de atividade de faturamento
			 */
			FiltroFaturamentoAtivCronRota filtroFaturamentoAtividadeCronogramaRota = new FiltroFaturamentoAtivCronRota();

			filtroFaturamentoAtividadeCronogramaRota.setCampoOrderBy(FiltroFaturamentoAtivCronRota.FATURAMENTO_GRUPO_ID,
							FiltroFaturamentoAtivCronRota.GERENCIA_REGIONAL_NOME_ABREVIADO,
							FiltroFaturamentoAtivCronRota.LOCALIDADE_DESCRICAO, FiltroFaturamentoAtivCronRota.SETOR_COMERCIAL_CODIGO,
							FiltroFaturamentoAtivCronRota.COMP_ID_ROTA_ID);

			filtroFaturamentoAtividadeCronogramaRota
							.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoAtivCronRota.UNIDADE_NEGOCIO);

			filtroFaturamentoAtividadeCronogramaRota.setConsultaSemLimites(true);

			// Objetos que ser�o retornados pelo hibernate.
			filtroFaturamentoAtividadeCronogramaRota
							.adicionarCaminhoParaCarregamentoEntidade("faturamentoAtividadeCronograma.faturamentoGrupoCronogramaMensal.faturamentoGrupo");
			filtroFaturamentoAtividadeCronogramaRota
							.adicionarCaminhoParaCarregamentoEntidade("rota.setorComercial.localidade.gerenciaRegional");

			filtroFaturamentoAtividadeCronogramaRota.adicionarParametro(new ParametroSimples(
							FiltroFaturamentoAtivCronRota.COMP_ID_FATURAMENTO_ATIVIDADE_CRONOGRAMA_ID, faturamentoAtividadeCronograma
											.getId()));

			filtroFaturamentoAtividadeCronogramaRota
							.adicionarParametro(new ParametroSimples(
											FiltroFaturamentoAtivCronRota.COMP_ID_FATURAMENTO_ATIVIDADE_CRONOGRAMA_FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ANO_MES_REFERENCIA,
											faturamentoGrupoCronogramaMensal.getAnoMesReferencia()));

			Collection colecaoFaturamentoAtividadeCronogramaRota = fachada.pesquisar(filtroFaturamentoAtividadeCronogramaRota,
							FaturamentoAtivCronRota.class.getName());

			// [FS0001] � Verificar exist�ncia de dados
			if(colecaoFaturamentoAtividadeCronogramaRota == null || colecaoFaturamentoAtividadeCronogramaRota.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "FATURAMENTO_ATIV_CRON_ROTA");
			}else{
				sessao.setAttribute("faturamentoAtividadeCronograma", faturamentoAtividadeCronograma);
				sessao.setAttribute("colecaoFaturamentoAtividadeCronogramaRota", colecaoFaturamentoAtividadeCronogramaRota);
			}

			if(FaturamentoAtividade.FATURAR_GRUPO.equals(idFaturamentoAtividade)
							|| FaturamentoAtividade.GERAR_ARQUIVO_LEITURA.equals(idFaturamentoAtividade)){

				String pExibirDataComando = "";
				try{
					pExibirDataComando = ParametroFaturamento.P_EXIBIR_DATA_INFOMADA_COMANDO_FATUAMENTO.executar();
				}catch(ControladorException e){
					throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
									new String[e.getParametroMensagem().size()]));
				}

				Date dataVencimento = null;

				if(ConstantesSistema.SIM.toString().equals(pExibirDataComando)){
					FaturamentoAtivCronRota faturamentoAtivCronRota = (FaturamentoAtivCronRota) Util
									.retonarObjetoDeColecao(colecaoFaturamentoAtividadeCronogramaRota);
					dataVencimento = faturamentoAtivCronRota.getDataContaVencimento();
				}else if(faturamentoGrupo.getDiaVencimento() != null){
					// [UC0618] Obter Data de Vencimento do Grupo
					dataVencimento = fachada.obterDataVencimentoGrupo(faturamentoGrupo.getId());
				}

				if(dataVencimento != null){
					Boolean habilitarDataVencimento = Boolean.TRUE;
					try{
						habilitarDataVencimento = ConstantesSistema.SIM.equals(Short
										.valueOf(ParametroFaturamento.P_HABILITAR_DATA_VENCIMENTO.executar()));

					}catch(ControladorException e){
						// Se n�o tem o par�metro n�o d� erro e sim exibe normal.
						e.printStackTrace();
					}

					if(habilitarDataVencimento){
						inserirComandoAtividadeFaturamentoActionForm.setVencimentoGrupo(Util.formatarData(dataVencimento));
						sessao.setAttribute("exibirCampoVencimentoGrupo", formatoData.format(dataVencimento));
					}
				}
			}
		}

		return retorno;
	}
}
