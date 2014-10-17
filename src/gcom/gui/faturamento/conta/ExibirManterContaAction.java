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
 * 
 * GSANPCG
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 * Vitor Cavalcante Hora
 */

package gcom.gui.faturamento.conta;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.ManterContaHelper;
import gcom.seguranca.acesso.Abrangencia;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ControladorException{

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterConta");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		String exibirCaucionar = ((String) ParametroFaturamento.P_HABILITAR_CAUCIONAMENTO.executar());

		sessao.setAttribute("EXIBIRCAUCIONAR", exibirCaucionar);

		// Inst�ncia do formul�rio que est� sendo utilizado
		ManterContaActionForm manterContaActionForm = (ManterContaActionForm) actionForm;
		String limparForm = httpServletRequest.getParameter("limparForm");

		// Defini��o que ir� auxiliar o retorno dos popus
		sessao.setAttribute("UseCase", "MANTERCONTA");

		// Removendo cole��es da sess�o
		sessao.removeAttribute("contaID");

		if(!Util.isVazioOuBranco(sessao.getAttribute("colecaoMotivoRetificacaoConta"))){
			sessao.removeAttribute("colecaoMotivoRetificacaoConta");
		}

		if(!Util.isVazioOuBranco(sessao.getAttribute("indicadorOperacao"))){
			sessao.removeAttribute("indicadorOperacao");
		}

		if(limparForm != null && !limparForm.equalsIgnoreCase("")){
			sessao.removeAttribute("colecaoContaImovel");
			manterContaActionForm.reset(actionMapping, httpServletRequest);
		}

		if(sessao.getAttribute("erroConcorrencia") != null && !sessao.getAttribute("erroConcorrencia").equals("")){
			sessao.removeAttribute("erroConcorrencia");
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		// Pesquisar os motivos para conta em revis�o
		this.pesquisarContaMotivoRevisao(httpServletRequest);

		// Seta o valor inicial fixo para contas em revis�o
		if(manterContaActionForm.getInContasRevisao() == null){
			manterContaActionForm.setInContasRevisao(ConstantesSistema.NAO.toString());
		}

		// Pesquisar o im�vel a partir da matr�cula do im�vel
		String idImovel = manterContaActionForm.getIdImovel();
		String idImovelRequest = httpServletRequest.getParameter("idImovelRequest");

		if((idImovel != null && !idImovel.equalsIgnoreCase("")) || (idImovelRequest != null && !idImovelRequest.equalsIgnoreCase(""))){

			/**
			 * alterado por pedro alexandre dia 22/11/2006
			 * Recupera o usu�rio logado para passar no met�do de colocar conta em revis�o
			 * para verificar se o usu�rio tem abrang�ncia para colocar uma conta em revis�o
			 * para o im�vel informado.
			 */
			Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
			Collection colecaoImovel = new ArrayList();

			Imovel imovel = null;
			if(idImovel != null && !idImovel.equalsIgnoreCase("")){
				imovel = fachada.pesquisarImovelRegistroAtendimento(new Integer(idImovel));
			}else{
				imovel = fachada.pesquisarImovelRegistroAtendimento(new Integer(idImovelRequest));
			}

			colecaoImovel.add(imovel);

			// ------------ CONTROLE DE ABRANGENCIA ----------------
			Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovel);

			if(!fachada.verificarAcessoAbrangencia(abrangencia)){
				throw new ActionServletException("atencao.acesso.negado.abrangencia");
			}

			// ------------ FIM CONTROLE DE ABRANGENCIA ------------

			/** fim altera��o ***************************************************************/

			// [FS0002] - Verificar exist�ncia da matr�cula do im�vel
			if(colecaoImovel == null || colecaoImovel.isEmpty() || colecaoImovel.iterator().next() == null){
				// throw new ActionServletException("atencao.imovel.inexistente");

				httpServletRequest.setAttribute("corInscricao", "exception");
				manterContaActionForm.setIdImovel("");
				manterContaActionForm.setInscricaoImovel("Matr�cula Inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
				manterContaActionForm.setNomeClienteUsuario("");
				manterContaActionForm.setSituacaoAguaImovel("");
				manterContaActionForm.setSituacaoEsgotoImovel("");
				sessao.removeAttribute("colecaoContaImovel");
			}else{

				this.validarCampos(manterContaActionForm);

				Imovel objetoImovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
				String matriculaImovel = null;

				if(idImovel != null && !idImovel.equalsIgnoreCase("")){
					matriculaImovel = idImovel;
				}else{
					matriculaImovel = idImovelRequest;
				}

				Cliente cliente = fachada.pesquisarClienteUsuarioImovel(new Integer(matriculaImovel));

				// Verifica exist�ncia do cliente usu�rio
				if(cliente == null){
					throw new ActionServletException("atencao.naocadastrado", null, "cliente do tipo usu�rio foi");
				}

				Cliente clienteResposavel = fachada.pesquisarClienteResponsavelImovel(new Integer(matriculaImovel));

				// Monta o helper para obter as contas
				ManterContaHelper manterContaHelper = new ManterContaHelper();

				manterContaHelper.setImovel(objetoImovel);
				manterContaHelper.setInContasRevisao(manterContaActionForm.getInContasRevisao());

				if(!Util.isVazioOuBranco(manterContaActionForm.getAnoMesReferenciaContaInicio())){
					manterContaHelper.setAnoMesReferenciaContaInicio(Util.formatarMesAnoComBarraParaAnoMes(manterContaActionForm
									.getAnoMesReferenciaContaInicio()));
				}

				if(!Util.isVazioOuBranco(manterContaActionForm.getAnoMesReferenciaContaFim())){
					manterContaHelper.setAnoMesReferenciaContaFim(Util.formatarMesAnoComBarraParaAnoMes(manterContaActionForm
									.getAnoMesReferenciaContaFim()));
				}

				if(!Util.isVazioOuBranco(manterContaActionForm.getDataPagamentoContaInicio())){
					manterContaHelper.setDataPagamentoContaInicio(Util.converteStringParaDate(manterContaActionForm
									.getDataPagamentoContaInicio()));
				}

				if(!Util.isVazioOuBranco(manterContaActionForm.getDataPagamentoContaFim())){
					manterContaHelper.setDataPagamentoContaFim(Util
									.converteStringParaDate(manterContaActionForm.getDataPagamentoContaFim()));
				}

				if(!Util.isVazioOrNulo(manterContaActionForm.getMotivosRevisaoDisponiveis())){
					// Tratar para retirar op��o "vazia" da lista de motivos
					ArrayList<String> listaMotivos = new ArrayList(Arrays.asList(manterContaActionForm.getMotivosRevisaoDisponiveis()));
					String contaMotivoRevisaoPrimeiroElemento = (String) Util.retonarObjetoDeColecao(listaMotivos);
					if(contaMotivoRevisaoPrimeiroElemento.equals("")){
						listaMotivos.remove(contaMotivoRevisaoPrimeiroElemento);
					}

					if(!Util.isVazioOrNulo(listaMotivos)){
						String[] arrayMotivos = new String[listaMotivos.size()];
						Object[] arrayMotivosObject = listaMotivos.toArray();
						for(int i = 0; i < listaMotivos.size(); i++){
							arrayMotivos[i] = arrayMotivosObject[i].toString();
						}

						manterContaHelper.setMotivosRevisaoDisponiveisComoInteger(arrayMotivos);
					}

				}

				// O sistema exibe uma lista das contas do im�vel com situa��o atual normal,
				// retificada ou inclu�da
				Collection<Conta> colecaoContaImovel = fachada.obterContasImovelManter(manterContaHelper);

				if(!Util.isVazioOrNulo(colecaoContaImovel)){
					if(!Util.isVazioOuBranco(idImovel) || !Util.isVazioOuBranco(idImovelRequest)){
						if(Util.isVazioOuBranco(idImovel) && !Util.isVazioOuBranco(idImovelRequest)){
							idImovel = idImovelRequest;
						}
						// [SB0011 � Validar autoriza��o de acesso ao im�vel pelos usu�rios das
						// empresas de cobran�a administrativa]
						this.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuarioLogado, idImovel);

						// [SB0012 � Validar autoriza��o de acesso ao im�vel em cobran�a
						// administrativa pelos usu�rios da empresa contratante]
						this
										.validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(usuarioLogado, idImovel,
														colecaoContaImovel);

						// [SB0013 � Verificar D�bito em Cobran�a Administrativa]
						this.verificarDebitoCobrancaAdministrativa(usuarioLogado, Integer.valueOf(idImovel), colecaoContaImovel);
					}
				}

				if(httpServletRequest.getParameter("limpaTela") != null && !httpServletRequest.getParameter("limpaTela").equals("")){

					manterContaActionForm.setNomeClienteUsuario("");
					manterContaActionForm.setSituacaoAguaImovel("");
					manterContaActionForm.setSituacaoEsgotoImovel("");
					sessao.removeAttribute("colecaoContaImovel");

				}else if((colecaoContaImovel == null || colecaoContaImovel.isEmpty()) && sessao.getAttribute("cancelar") == null){

					// [FS0003] - Verificar exist�ncia de alguma conta
					throw new ActionServletException("atencao.pesquisa.nenhuma.conta_imovel", objetoImovel.getId().toString(),
									"exibirManterContaAction.do?limpaTela=1");
				}

				// Carregando as informa��es do im�vel no formul�rio de exibi��o.
				if(idImovel == null || idImovel.equalsIgnoreCase("")){
					manterContaActionForm.setIdImovel(idImovelRequest);
				}else{
					manterContaActionForm.setIdImovel(idImovel);
				}

				manterContaActionForm.setInscricaoImovel(objetoImovel.getInscricaoFormatada());
				manterContaActionForm.setNomeClienteUsuario(cliente.getNome());
				manterContaActionForm.setNomeClienteResponsavel(clienteResposavel != null ? clienteResposavel.getNome() : "");
				manterContaActionForm.setSituacaoAguaImovel(objetoImovel.getLigacaoAguaSituacao().getDescricao());
				manterContaActionForm.setSituacaoEsgotoImovel(objetoImovel.getLigacaoEsgotoSituacao().getDescricao());

				// Ordenando a cole��o por m�s/ano de refer�ncia descendente
				List<Conta> colecaoContasOrdenadaImovel = new ArrayList<Conta>();
				colecaoContasOrdenadaImovel.addAll(colecaoContaImovel);
				List sortFields = new ArrayList();
				sortFields.add(new BeanComparator("referencia"));
				ComparatorChain multiSort = new ComparatorChain(sortFields);
				Collections.sort((List) colecaoContasOrdenadaImovel, multiSort);
				Collections.reverse(colecaoContasOrdenadaImovel);

				// Coloca na sess�o a cole��o com as contas do im�vel selecionado
				sessao.setAttribute("colecaoContaImovel", colecaoContasOrdenadaImovel);

				if(idImovel != null && !idImovel.equalsIgnoreCase("")){
					sessao.setAttribute("imovel", idImovel);
				}else{
					sessao.setAttribute("imovel", idImovelRequest);
				}
			}
		}

		sessao.removeAttribute("cancelar");
		return retorno;
	}

	/**
	 * Pesquisa Conta Motivo Revis�o
	 * 
	 * @author Hugo Lima
	 * @date 08/05/2012
	 */
	private void pesquisarContaMotivoRevisao(HttpServletRequest httpServletRequest){

		// Motivo de Revis�o
		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao(FiltroContaMotivoRevisao.DESCRICAO);

		Collection colecaoContaMotivoRevisao = Fachada.getInstancia().pesquisar(filtroContaMotivoRevisao,
						ContaMotivoRevisao.class.getName());

		((ArrayList<ContaMotivoRevisao>) colecaoContaMotivoRevisao).add(0, new ContaMotivoRevisao());

		httpServletRequest.setAttribute("colecaoContaMotivoRevisao", colecaoContaMotivoRevisao);
	}

	private void validarCampos(ManterContaActionForm manterContaActionForm){

		if(!Util.isVazioOuBranco(manterContaActionForm.getAnoMesReferenciaContaInicio())
						&& Util.validarAnoMes(manterContaActionForm.getAnoMesReferenciaContaInicio())){
			throw new ActionServletException("atencao.ano_mes_referencia.invalida");
		}

		if(!Util.isVazioOuBranco(manterContaActionForm.getAnoMesReferenciaContaFim())
						&& Util.validarAnoMes(manterContaActionForm.getAnoMesReferenciaContaFim())){
			throw new ActionServletException("atencao.ano_mes_referencia.invalida");
		}

		if(!Util.isVazioOuBranco(manterContaActionForm.getAnoMesReferenciaContaInicio())
						&& !Util.isVazioOuBranco(manterContaActionForm.getAnoMesReferenciaContaFim())
						&& Util.compararAnoMesReferencia(Util.formatarMesAnoParaAnoMesSemBarra(manterContaActionForm
										.getAnoMesReferenciaContaFim()), Util.formatarMesAnoParaAnoMesSemBarra(manterContaActionForm
										.getAnoMesReferenciaContaInicio()), "<")){
			throw new ActionServletException("atencao.periodo_ref_final.anterior.periodo_ref_inicial");
		}

		if(!Util.isVazioOuBranco(manterContaActionForm.getDataPagamentoContaInicio())
						&& Util.validarDiaMesAno(manterContaActionForm.getDataPagamentoContaInicio())){
			throw new ActionServletException("atencao.data.inicio.invalida");
		}

		if(!Util.isVazioOuBranco(manterContaActionForm.getDataPagamentoContaFim())
						&& Util.validarDiaMesAno(manterContaActionForm.getDataPagamentoContaFim())){
			throw new ActionServletException("atencao.data.inicio.invalida");
		}

		if(!Util.isVazioOuBranco(manterContaActionForm.getDataPagamentoContaInicio())
						&& !Util.isVazioOuBranco(manterContaActionForm.getDataPagamentoContaFim())
						&& Util.compararData(Util.converteStringParaDate(manterContaActionForm.getDataPagamentoContaFim()), Util
										.converteStringParaDate(manterContaActionForm.getDataPagamentoContaInicio())) < 0){
			throw new ActionServletException("atencao.data_final_periodo_anterior_data_inicial");
		}
	}

	/**
	 * [SB0011] Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de cobran�a
	 * administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 */
	private void validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, String idImovel){

		if(Fachada.getInstancia().obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, Integer.valueOf(idImovel),
						ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA).equals(ConstantesSistema.NAO)){
			throw new ActionServletException("atencao.usuario_empresa_cobranca_administrativa_sem_acesso_imovel", usuario.getNomeUsuario(),
							idImovel);
		}
	}

	/**
	 * [SB0012] Validar autoriza��o de acesso ao im�vel em cobran�a administrativa pelos usu�rios
	 * da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 * @param colecaoContas
	 */
	private void validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(Usuario usuario, String idImovel,
					Collection<Conta> colecaoContas){

		Collection<Conta> colecaoContasEmCobrancaAdministrativa = new ArrayList<Conta>();
		boolean indicadorTodasAsContasEmCobrancaAdministrativa = true;

		for(Conta conta : colecaoContas){
			// Consulta se existe conta que n�o est� em cobranca administrativa
			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, conta.getId()));

			Conta contaPesquisada = (Conta) Util.retonarObjetoDeColecao(Fachada.getInstancia()
							.pesquisar(filtroConta, Conta.class.getName()));
			// 1.1. Caso existam, na lista de contas selecionadas, contas em cobran�a
			// administrativa
			if(contaPesquisada.getIndicadorCobrancaAdministrativa().equals(ConstantesSistema.SIM)){
				colecaoContasEmCobrancaAdministrativa.add(contaPesquisada);
			}else{
				// Caso exista conta que n�o esteja em cobran�a administrativa
				// modifica o indicador de todas as contas em cobran�a administrativa.
				indicadorTodasAsContasEmCobrancaAdministrativa = false;
			}
		}

		// 1.1.2. Caso o usu�rio n�o tenha autoriza��o de acesso ao im�vel
		if(Fachada.getInstancia().obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, Integer.valueOf(idImovel),
						ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE).equals(ConstantesSistema.NAO)){
			// 1.1.2.1. Caso todas as contas da lista de contas selecionadas
			// estejam em cobran�a administrativa
			if(indicadorTodasAsContasEmCobrancaAdministrativa){
				throw new ActionServletException("atencao.usuario_empresa_sem_acesso_imovel_conta_cobranca_administrativa", idImovel,
								usuario.getNomeUsuario());
			}else{
				// 1.1.2.2. Caso contr�rio, ou seja, caso nem todas as contas da lista de contas
				// selecionadas estejam em cobran�a administrativa
				// 1.1.2.2.1. Retirar da lista de contas selecionadas
				// as contas que estejam em cobran�a administrativa
				if(!Util.isVazioOuBranco(colecaoContasEmCobrancaAdministrativa)){
					for(Conta contaRemover : colecaoContasEmCobrancaAdministrativa){
						colecaoContas.remove(contaRemover);
					}
				}
			}
		}
	}

	/**
	 * [SB0013] Verificar D�bito em Cobran�a Administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 * @param colecaoContas
	 */
	private void verificarDebitoCobrancaAdministrativa(Usuario usuario, Integer idImovel, Collection<Conta> colecaoContas){

		Collection<Integer> colecaoIdsContasRemover = null;
		boolean idsConsultados = false;

		// 1. Caso o usu�rio logado perten�a a uma empresa de cobran�a administrativa
		if(Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

			if(!Util.isVazioOrNulo(colecaoContas)){
				Collection<Conta> colecaoContasClone = (ArrayList<Conta>) ((ArrayList<Conta>) colecaoContas).clone();
				Iterator dadoscolecaoContas = colecaoContasClone.iterator();
				while(dadoscolecaoContas.hasNext()){
					Conta contaRemover = (Conta) dadoscolecaoContas.next();

					// Consulta o indicador de cobranca administrativa
					FiltroConta filtroConta = new FiltroConta();
					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, contaRemover.getId()));

					Conta contaPesquisada = (Conta) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroConta,
									Conta.class.getName()));
					// 1.1. Caso existam, na lista de contas selecionadas, contas em cobran�a
					// administrativa
					if(contaPesquisada.getIndicadorCobrancaAdministrativa().equals(ConstantesSistema.SIM)){

						// Consulta os ids de contas a remover apenas uma vez caso seja
						// constatada a ocorrencia de pelo menos uma conta em cobranca
						// administrativa
						if(!idsConsultados){
							// Pesquisa os ids das contas a serem excluidos
							colecaoIdsContasRemover = Fachada.getInstancia().obterIdsContasCobrancaAdministrativaEmpresaDiferente(
											usuario.getEmpresa().getId(), idImovel);
							idsConsultados = true;
						}

						// Caso o id da conta em cobran�a administrativa exista na cole��o de
						// Ids a serem removidos exclui a conta da cole��o
						if(colecaoIdsContasRemover.contains(contaRemover.getId())){
							colecaoContas.remove(contaRemover);
						}
					}
				}
			}
		}
	}

}