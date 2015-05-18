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

package gcom.gui.faturamento.conta;

import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRetificacao;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.conta.FiltroMotivoRetificacaoConta;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirRetirarDebitoCobradoConjuntoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirRetirarDebitoCobradoConjuntoConta");

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		RetirarDebitoCobradoActionForm retirarDebitoCobradoActionForm = (RetirarDebitoCobradoActionForm) actionForm;

		String primeiraVez = httpServletRequest.getParameter("primeiraVez");

		boolean temPermissaoAtualizarDebitosExecFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.ATUALIZAR_DEBITOS_EXECUCAO_FISCAL, this.getUsuarioLogado(httpServletRequest));
		if(!temPermissaoAtualizarDebitosExecFiscal){
			/**
			 * [UC0146] Manter Conta
			 * [SB0040] Verificar Exist�ncia de Conta em Execu��o Fiscal
			 * 
			 * @author Gicevalter Couto
			 * @date 07/08/2014
			 * @param colecaoContas
			 */
			StringBuffer parametroExecucaoFiscal = new StringBuffer();

			// Carregando contas selecionadas
			String contaSelected = httpServletRequest.getParameter("idsContasSelecionadas");
			if(!Util.isVazioOuBranco(contaSelected)){
				// Contas selecionadas pelo usu�rio
				String[] arrayIdentificadores = contaSelected.split(",");

				for(int i = 0; i < arrayIdentificadores.length; i++){

					String dadosConta = arrayIdentificadores[i];
					String[] idContaArray = dadosConta.split("-");
					Integer idConta = new Integer(idContaArray[0]);

					if(idConta != null){
						FiltroConta filtroConta = new FiltroConta();
						filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
						filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITOS_COBRADOS);

						Conta conta = (Conta) Util.retonarObjetoDeColecao(((Collection<Conta>) fachada.pesquisar(filtroConta,
										Conta.class.getName())));
						ContaValoresHelper contaValores = new ContaValoresHelper();
						contaValores.setConta(conta);

						Collection<ContaValoresHelper> colecaoContaValores = new ArrayList<ContaValoresHelper>();
						colecaoContaValores.add(contaValores);

						if(fachada.verificarExecucaoFiscal(colecaoContaValores, null, null)){
							parametroExecucaoFiscal.append(conta.getReferenciaFormatada());
							parametroExecucaoFiscal.append(", ");
						}
					}
				}

				String parametroMensagemExecFiscal = parametroExecucaoFiscal.toString();

				if(!Util.isVazioOuBranco(parametroMensagemExecFiscal)){
					parametroMensagemExecFiscal = parametroMensagemExecFiscal.substring(0, parametroMensagemExecFiscal.length() - 2);

					throw new ActionServletException("atencao.conta.debito.execucao.fiscal", usuario.getNomeUsuario().toString(),
									parametroMensagemExecFiscal);
				}
			}
		}



		if(primeiraVez != null && primeiraVez.equals("sim")){
			retirarDebitoCobradoActionForm.setContaSelected("");
			retirarDebitoCobradoActionForm.setDescricaoDebito("");
			retirarDebitoCobradoActionForm.setIdMotivoRetificacao("");
			retirarDebitoCobradoActionForm.setIdTipoDebito("");

			sessao.removeAttribute("debitosTipoRetirar");
		}

		Integer anoMes = null;
		if(httpServletRequest.getParameter("mesAno") != null){
			anoMes = Util.formatarMesAnoComBarraParaAnoMes(httpServletRequest.getParameter("mesAno"));
			sessao.setAttribute("anoMes", anoMes);
		}

		Integer anoMesFim = null;
		if(httpServletRequest.getParameter("mesAnoFim") != null){
			anoMesFim = Util.formatarMesAnoComBarraParaAnoMes(httpServletRequest.getParameter("mesAnoFim"));
			sessao.setAttribute("anoMesFim", anoMesFim);
		}

		Date dataVencimentoContaInicio = null;
		Date dataVencimentoContaFim = null;
		Integer idGrupoFaturamento = null;

		if(httpServletRequest.getParameter("dataVencimentoContaInicial") != null){

			dataVencimentoContaInicio = Util.converteStringParaDate(httpServletRequest.getParameter("dataVencimentoContaInicial"));
			sessao.setAttribute("dataVencimentoContaInicial", dataVencimentoContaInicio);
		}

		if(httpServletRequest.getParameter("dataVencimentoContaFinal") != null){

			dataVencimentoContaFim = Util.converteStringParaDate(httpServletRequest.getParameter("dataVencimentoContaFinal"));
			sessao.setAttribute("dataVencimentoContaFinal", dataVencimentoContaFim);
		}

		// if(httpServletRequest.getParameter("idGrupoFaturamento") != null){
		//
		// idGrupoFaturamento = new Integer((String)
		// httpServletRequest.getParameter("idGrupoFaturamento"));
		// sessao.setAttribute("idGrupoFaturamento", idGrupoFaturamento);
		// }

		// Carregar: Lista dos motivos de retificacao da conta
		if(sessao.getAttribute("colecaoMotivoRetificacaoConta") == null){

			FiltroMotivoRetificacaoConta filtroMotivoRetificacaoConta = new FiltroMotivoRetificacaoConta(
							FiltroMotivoRetificacaoConta.DESCRICAO_MOTIVO_RETIFICACAO_CONTA);

			filtroMotivoRetificacaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRetificacaoConta.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoMotivoRetificacaoConta = fachada.pesquisar(filtroMotivoRetificacaoConta, ContaMotivoRetificacao.class
							.getName());

			if(colecaoMotivoRetificacaoConta == null || colecaoMotivoRetificacaoConta.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_RETIFICACAO_CONTA");
			}
			// Disponibiliza a cole��o pela sess�o
			sessao.setAttribute("colecaoMotivoRetificacaoConta", colecaoMotivoRetificacaoConta);
		}

		String tipoDebito = (String) retirarDebitoCobradoActionForm.getIdTipoDebito();
		// PESQUISAR CLIENTE
		if(tipoDebito != null && !tipoDebito.toString().trim().equalsIgnoreCase("")){
			this.pesquisarTipoDebito(tipoDebito, retirarDebitoCobradoActionForm, fachada, httpServletRequest);
		}

		if(httpServletRequest.getParameter("adicionar") != null){
			Collection<DebitoTipo> debitosTipoRetirar = null;
			if(sessao.getAttribute("debitosTipoRetirar") != null){
				debitosTipoRetirar = (Collection) sessao.getAttribute("debitosTipoRetirar");
			}else{
				debitosTipoRetirar = new ArrayList();
			}
			this.pesquisarTipoDebito(tipoDebito, retirarDebitoCobradoActionForm, fachada, httpServletRequest);
			DebitoTipo debitoTipo = new DebitoTipo();
			debitoTipo.setId(Integer.parseInt(retirarDebitoCobradoActionForm.getIdTipoDebito()));
			debitoTipo.setDescricao(retirarDebitoCobradoActionForm.getDescricaoDebito());
			if(!debitosTipoRetirar.contains(debitoTipo)){
				debitosTipoRetirar.add(debitoTipo);
				sessao.setAttribute("debitosTipoRetirar", debitosTipoRetirar);
			}
			retirarDebitoCobradoActionForm.setIdTipoDebito("");
			retirarDebitoCobradoActionForm.setDescricaoDebito("");
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			if(httpServletRequest.getParameter("tipoConsulta").equals("tipoDebito")){
				retirarDebitoCobradoActionForm.setIdTipoDebito(httpServletRequest.getParameter("idCampoEnviarDados"));
				retirarDebitoCobradoActionForm.setDescricaoDebito(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
			}

		}



		// Remover TipoDebito
		String removerTipoDebito = httpServletRequest.getParameter("removerTipoDebito");

		if(removerTipoDebito != null && !removerTipoDebito.equalsIgnoreCase("")){

			Integer objetoRemocao = (Integer.valueOf(httpServletRequest.getParameter("removerTipoDebito")));
			Collection colecao = (Collection) sessao.getAttribute("debitosTipoRetirar");
			Iterator iteratorColecao = colecao.iterator();
			DebitoTipo debitoTipo = null;

			while(iteratorColecao.hasNext()){
				debitoTipo = (DebitoTipo) iteratorColecao.next();

				if(debitoTipo.getId().equals(objetoRemocao)){
					colecao.remove(debitoTipo);
					break;
				}
			}

		}

		return retorno;
	}

	public void pesquisarTipoDebito(String tipoDebito, RetirarDebitoCobradoActionForm form, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();

		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, tipoDebito));
		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection debitoTipoEncontrado = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

		if(debitoTipoEncontrado != null && !debitoTipoEncontrado.isEmpty()){
			// O Cliente foi encontrado
			if(((DebitoTipo) ((List) debitoTipoEncontrado).get(0)).getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){
				throw new ActionServletException("atencao.cliente.inativo", null, ""
								+ ((DebitoTipo) ((List) debitoTipoEncontrado).get(0)).getId());
			}

			form.setIdTipoDebito(((DebitoTipo) ((List) debitoTipoEncontrado).get(0)).getId().toString());
			form.setDescricaoDebito(((DebitoTipo) ((List) debitoTipoEncontrado).get(0)).getDescricao());

		}else{
			throw new ActionServletException("atencao.tipo_debito_inexistente");
		}
	}

}
