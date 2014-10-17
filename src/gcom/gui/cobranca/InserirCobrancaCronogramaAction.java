/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.gui.cobranca;

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaAcaoCronograma;
import gcom.cobranca.CobrancaAtividade;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.CobrancaGrupoCronogramaMes;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.cobranca.FiltroCobrancaGrupoCronogramaMes;
import gcom.cobranca.bean.CobrancaCronogramaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável por inserir um cronograma de cobrança;
 * 
 * @author Administrador
 *         Alterações para a v0.05
 * @author Virgínia Melo
 * @date 09/09/2008
 *       Desfazer alterações para a v0.06
 * @author Virgínia Melo
 * @date 31/11/2008
 * @author Virgínia Melo
 * @date 10/08/2009
 *       Merge com o código mais atualizado.
 */
public class InserirCobrancaCronogramaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		CobrancaActionForm cobrancaActionForm = (CobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection acoesCobranca = (Collection) sessao.getAttribute("acoesCobranca");
		Collection atividadesCobranca = (Collection) sessao.getAttribute("atividadesCobranca");
		Collection atividadesCobrancaObrigatoriedadeAtivo = (Collection) sessao.getAttribute("atividadesCobrancaObrigatoriedadeAtivo");

		Collection cobrancasAtividadesParaInsercao = new ArrayList();
		Collection colecaoCobrancaCronogramaHelper = new ArrayList();

		CobrancaAcao cobrancaAcao = null;
		CobrancaAtividade cobrancaAtividade = null;
		CobrancaGrupoCronogramaMes cobrancaGrupoCronogramaMes = null;
		CobrancaAcaoCronograma cobrancaAcaoCronograma = null;
		CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = null;
		CobrancaGrupo cobrancaGrupo = new CobrancaGrupo();
		CobrancaCronogramaHelper cobrancaCronogramaHelper = null;

		cobrancaGrupo.setId(Integer.valueOf(cobrancaActionForm.getIdGrupoCobranca()));

		Iterator iteratorAcaoCobranca = acoesCobranca.iterator();

		String idAcaoCobranca = "";
		String qtdMaximaDocumentos = "";
		String dataPrevista = "";
		String anoMes = "";
		String mesAno = "";

		int contadorAtividades = 0;

		while(iteratorAcaoCobranca.hasNext()){
			Iterator iteratorAtividadesCobranca = atividadesCobranca.iterator();
			cobrancaCronogramaHelper = new CobrancaCronogramaHelper();
			cobrancasAtividadesParaInsercao = new ArrayList();

			cobrancaAcao = (CobrancaAcao) iteratorAcaoCobranca.next();

			// seta os valores no objeto CobrancaGrupoCronogramaMes
			cobrancaGrupoCronogramaMes = new CobrancaGrupoCronogramaMes();
			cobrancaGrupoCronogramaMes.setCobrancaGrupo(cobrancaGrupo);
			mesAno = cobrancaActionForm.getMesAno();
			String mes = mesAno.substring(0, 2);
			String ano = mesAno.substring(3, 7);
			anoMes = ano + "" + mes;
			cobrancaGrupoCronogramaMes.setAnoMesReferencia(Integer.parseInt(anoMes));
			// cobrancaGrupoCronogramaMes.setUltimaAlteracao(new Date());

			cobrancaCronogramaHelper.setCobrancaGrupoCronogramaMes(cobrancaGrupoCronogramaMes);

			// contador utilizado para verificar se ha alguma atividade com
			// data preenchida quando comandaer for nulo
			int verificaDataPreenchida = 0;

			// seta os valores no objeto CobrancaAcaoCronograma
			cobrancaAcaoCronograma = new CobrancaAcaoCronograma();
			cobrancaAcaoCronograma.setCobrancaAcao(cobrancaAcao);
			cobrancaCronogramaHelper.setCobrancaAcaoCronograma(cobrancaAcaoCronograma);

			// se o indicador de obrigatoriedade for igual a sim(1)
			/**
			 * Caso o usuário não informe data prevista para todas as atividades
			 * das ações que obrigatoriamente devem constar no
			 * cronograma(CBAC_ICOBRIGATORIEDADE=1), exibir a mensagem "É
			 * necessário informar a data prevista para as atividades das ações
			 * que obrigatoriamente devem constar no cronograma" e retornar para
			 * o passo correspodente no fluxo principal.
			 */
			if(cobrancaAcao.getIndicadorObrigatoriedade().intValue() == 1){

				while(iteratorAtividadesCobranca.hasNext()){

					contadorAtividades += 1;

					cobrancaAtividade = (CobrancaAtividade) iteratorAtividadesCobranca.next();

					qtdMaximaDocumentos = (String) httpServletRequest.getParameter("qtd_a" + cobrancaAcao.getId().toString() + "n"
									+ cobrancaAtividade.getId().toString());

					dataPrevista = "";
					dataPrevista = (String) httpServletRequest.getParameter("a" + cobrancaAcao.getId().toString() + "n"
									+ cobrancaAtividade.getId().toString());

					if(dataPrevista.trim().equals("") && cobrancaAtividade.getIndicadorObrigatoriedade() == 1){
						throw new ActionServletException("atencao.cobranca.data_prevista_acao_obrigatoria");
					}else{
						// cobrancaAcao =
						// (CobrancaAcao)iteratorAcaoCobranca.next();
						// --------pega o valor de comandar.Ex: comandar2
						idAcaoCobranca = (String) httpServletRequest.getParameter("comandar" + cobrancaAcao.getId().toString()
										+ "atividade" + cobrancaAtividade.getId());
						// -------- separa o id da string comandar
						// ----seta os valores no objeto
						// CobrancaAcaoAtividadeCronograma
						cobrancaAcaoAtividadeCronograma = new CobrancaAcaoAtividadeCronograma();
						cobrancaAcaoAtividadeCronograma.setCobrancaAtividade(cobrancaAtividade);

						// Verifica se foi preenchido o campo de quantidade maxima de documento e
						// seta no objeto
						if(qtdMaximaDocumentos != null && !qtdMaximaDocumentos.equals("")){
							cobrancaAcaoAtividadeCronograma.setQuantidadeMaximaDocumentos(Integer.valueOf(qtdMaximaDocumentos));
						}

						if(!dataPrevista.trim().equals("")){
							cobrancaAcaoAtividadeCronograma.setDataPrevista(Util.converteStringParaDate(dataPrevista));
							if(idAcaoCobranca != null && idAcaoCobranca.trim().equals("1")){
								cobrancaAcaoAtividadeCronograma.setComando(Util.converteStringParaDateHora(dataPrevista + " "
												+ Util.formatarHoraSemData(new Date())));
							}else{
								cobrancaAcaoAtividadeCronograma.setComando(null);
							}

							cobrancasAtividadesParaInsercao.add(cobrancaAcaoAtividadeCronograma);
						}
					}
				}
			}else{
				verificaDataPreenchida = 0;
				while(iteratorAtividadesCobranca.hasNext()){
					contadorAtividades += 1;

					cobrancaAtividade = (CobrancaAtividade) iteratorAtividadesCobranca.next();

					qtdMaximaDocumentos = (String) httpServletRequest.getParameter("qtd_a" + cobrancaAcao.getId().toString() + "n"
									+ cobrancaAtividade.getId().toString());

					dataPrevista = "";
					dataPrevista = (String) httpServletRequest.getParameter("a" + cobrancaAcao.getId().toString() + "n"
									+ cobrancaAtividade.getId().toString());

					cobrancaAcaoAtividadeCronograma = new CobrancaAcaoAtividadeCronograma();
					cobrancaAcaoAtividadeCronograma.setCobrancaAtividade(cobrancaAtividade);

					// Verifica se foi preenchido o campo de quantidade maxima de documento e
					// seta no objeto
					if(qtdMaximaDocumentos != null && !qtdMaximaDocumentos.equals("")){
						cobrancaAcaoAtividadeCronograma.setQuantidadeMaximaDocumentos(Integer.valueOf(qtdMaximaDocumentos));
					}

					if(!dataPrevista.trim().equals("")
									|| cobrancaAtividade.getIndicadorObrigatoriedade().equals(
													CobrancaAtividade.INDICADOR_OBRIGATORIEDADE_ATIVO)){
						verificaDataPreenchida += 1;

						if(!dataPrevista.trim().equals("")){
							// --------pega o valor de comandar.Ex: comandar2
							idAcaoCobranca = (String) httpServletRequest.getParameter("comandar" + cobrancaAcao.getId().toString()
											+ "atividade" + cobrancaAtividade.getId());
							// -------- separa o id da string comandar

							// ----seta os valores no objeto
							// CobrancaAcaoAtividadeCronograma
							cobrancaAcaoAtividadeCronograma.setDataPrevista(Util.converteStringParaDate(dataPrevista));
							if(idAcaoCobranca != null && idAcaoCobranca.trim().equals("1")){
								cobrancaAcaoAtividadeCronograma.setComando(Util.converteStringParaDateHora(dataPrevista + " "
												+ Util.formatarHoraSemData(new Date())));
							}else{
								cobrancaAcaoAtividadeCronograma.setComando(null);
							}
						}
					}else{
						cobrancaAcaoAtividadeCronograma.setDataPrevista(null);
						cobrancaAcaoAtividadeCronograma.setComando(null);
					}
					if(cobrancaAcaoAtividadeCronograma.getDataPrevista() != null
									&& !cobrancaAcaoAtividadeCronograma.getDataPrevista().equals("")){
						cobrancasAtividadesParaInsercao.add(cobrancaAcaoAtividadeCronograma);
					}
				}
				/**
				 * Caso o usuario informe a data prevista somente para algumas
				 * atividades da acao, exibir a mensagem "É necessário informar
				 * a data prevista para todas as atividades da ação."
				 */
				if((verificaDataPreenchida > 0) && (verificaDataPreenchida < atividadesCobrancaObrigatoriedadeAtivo.size())){
					throw new ActionServletException("atencao.cobranca.data_prevista_algumas_atividades");
				}
			}

			cobrancaCronogramaHelper.setCobrancasAtividadesParaInsercao(cobrancasAtividadesParaInsercao);
			colecaoCobrancaCronogramaHelper.add(cobrancaCronogramaHelper);
		}

		if(contadorAtividades == 0){
			throw new ActionServletException("atencao.cobranca.nenhuma_atividade");
		}

		// -----Chama o metodo inserirCobrancaCronograma da fachada
		fachada.inserirCobrancaCronograma(colecaoCobrancaCronogramaHelper, this.getUsuarioLogado(httpServletRequest));

		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, cobrancaGrupo.getId()));

		Collection colecaoCobrancaGrupo = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
		CobrancaGrupo cobrancaGrupoExibicao = (CobrancaGrupo) colecaoCobrancaGrupo.iterator().next();

		FiltroCobrancaGrupoCronogramaMes filtroCobrancaGrupoCronogramaMes = new FiltroCobrancaGrupoCronogramaMes();
		filtroCobrancaGrupoCronogramaMes.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupoCronogramaMes.ID_COBRANCA_GRUPO,
						cobrancaGrupo.getId()));
		filtroCobrancaGrupoCronogramaMes.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupoCronogramaMes.ANO_MES_REFERENCIA, Util
						.formatarMesAnoParaAnoMesSemBarra(mesAno)));

		Collection colecaoCobrancaGrupoCronogramaMes = fachada.pesquisar(filtroCobrancaGrupoCronogramaMes, CobrancaGrupoCronogramaMes.class
						.getName());
		CobrancaGrupoCronogramaMes cobrancaGrupoCronogramaMesAtualizacao = null;
		cobrancaGrupoCronogramaMesAtualizacao = (CobrancaGrupoCronogramaMes) colecaoCobrancaGrupoCronogramaMes.iterator().next();

		montarPaginaSucesso(httpServletRequest, "Cronograma de Cobrança do " + cobrancaGrupoExibicao.getDescricao() + " referente a "
						+ Util.formatarAnoMesParaMesAno(cobrancaGrupoCronogramaMes.getAnoMesReferencia()) + " inserido com sucesso.",
						"Inserir outro Cronograma de Cobrança", "exibirInserirCronogramaCobrancaAction.do?menu=sim",
						"exibirAtualizarCobrancaCronogramaAction.do?filtro=S&menu=sim&idRegistroAtualizacao="
										+ cobrancaGrupoCronogramaMesAtualizacao.getId().toString(),
						"Atualizar o Cronograma de Cobrança inserido");

		return retorno;
	}

}
