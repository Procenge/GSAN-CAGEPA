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

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		String exibirCaucionar = ((String) ParametroFaturamento.P_HABILITAR_CAUCIONAMENTO.executar());

		sessao.setAttribute("EXIBIRCAUCIONAR", exibirCaucionar);

		// Instância do formulário que está sendo utilizado
		ManterContaActionForm manterContaActionForm = (ManterContaActionForm) actionForm;
		String limparForm = httpServletRequest.getParameter("limparForm");

		// Definição que irá auxiliar o retorno dos popus
		sessao.setAttribute("UseCase", "MANTERCONTA");

		// Removendo coleções da sessão
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

		// Pesquisar os motivos para conta em revisão
		this.pesquisarContaMotivoRevisao(httpServletRequest);

		// Seta o valor inicial fixo para contas em revisão
		if(manterContaActionForm.getInContasRevisao() == null){
			manterContaActionForm.setInContasRevisao(ConstantesSistema.NAO.toString());
		}

		// Pesquisar o imóvel a partir da matrícula do imóvel
		String idImovel = manterContaActionForm.getIdImovel();
		String idImovelRequest = httpServletRequest.getParameter("idImovelRequest");

		if((idImovel != null && !idImovel.equalsIgnoreCase("")) || (idImovelRequest != null && !idImovelRequest.equalsIgnoreCase(""))){

			/**
			 * alterado por pedro alexandre dia 22/11/2006
			 * Recupera o usuário logado para passar no metódo de colocar conta em revisão
			 * para verificar se o usuário tem abrangência para colocar uma conta em revisão
			 * para o imóvel informado.
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

			/** fim alteração ***************************************************************/

			// [FS0002] - Verificar existência da matrícula do imóvel
			if(colecaoImovel == null || colecaoImovel.isEmpty() || colecaoImovel.iterator().next() == null){
				// throw new ActionServletException("atencao.imovel.inexistente");

				httpServletRequest.setAttribute("corInscricao", "exception");
				manterContaActionForm.setIdImovel("");
				manterContaActionForm.setInscricaoImovel("Matrícula Inexistente");
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

				// Verifica existência do cliente usuário
				if(cliente == null){
					throw new ActionServletException("atencao.naocadastrado", null, "cliente do tipo usuário foi");
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
					// Tratar para retirar opção "vazia" da lista de motivos
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

				// O sistema exibe uma lista das contas do imóvel com situação atual normal,
				// retificada ou incluída
				Collection<Conta> colecaoContaImovel = fachada.obterContasImovelManter(manterContaHelper);

				if(!Util.isVazioOrNulo(colecaoContaImovel)){
					if(!Util.isVazioOuBranco(idImovel) || !Util.isVazioOuBranco(idImovelRequest)){
						if(Util.isVazioOuBranco(idImovel) && !Util.isVazioOuBranco(idImovelRequest)){
							idImovel = idImovelRequest;
						}
						// [SB0011 – Validar autorização de acesso ao imóvel pelos usuários das
						// empresas de cobrança administrativa]
						this.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuarioLogado, idImovel);

						// [SB0012 – Validar autorização de acesso ao imóvel em cobrança
						// administrativa pelos usuários da empresa contratante]
						this
										.validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(usuarioLogado, idImovel,
														colecaoContaImovel);

						// [SB0013 – Verificar Débito em Cobrança Administrativa]
						this.verificarDebitoCobrancaAdministrativa(usuarioLogado, Integer.valueOf(idImovel), colecaoContaImovel);
					}
				}

				if(httpServletRequest.getParameter("limpaTela") != null && !httpServletRequest.getParameter("limpaTela").equals("")){

					manterContaActionForm.setNomeClienteUsuario("");
					manterContaActionForm.setSituacaoAguaImovel("");
					manterContaActionForm.setSituacaoEsgotoImovel("");
					sessao.removeAttribute("colecaoContaImovel");

				}else if((colecaoContaImovel == null || colecaoContaImovel.isEmpty()) && sessao.getAttribute("cancelar") == null){

					// [FS0003] - Verificar existência de alguma conta
					throw new ActionServletException("atencao.pesquisa.nenhuma.conta_imovel", objetoImovel.getId().toString(),
									"exibirManterContaAction.do?limpaTela=1");
				}

				// Carregando as informações do imóvel no formulário de exibição.
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

				// Ordenando a coleção por mês/ano de referência descendente
				List<Conta> colecaoContasOrdenadaImovel = new ArrayList<Conta>();
				colecaoContasOrdenadaImovel.addAll(colecaoContaImovel);
				List sortFields = new ArrayList();
				sortFields.add(new BeanComparator("referencia"));
				ComparatorChain multiSort = new ComparatorChain(sortFields);
				Collections.sort((List) colecaoContasOrdenadaImovel, multiSort);
				Collections.reverse(colecaoContasOrdenadaImovel);

				// Coloca na sessão a coleção com as contas do imóvel selecionado
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
	 * Pesquisa Conta Motivo Revisão
	 * 
	 * @author Hugo Lima
	 * @date 08/05/2012
	 */
	private void pesquisarContaMotivoRevisao(HttpServletRequest httpServletRequest){

		// Motivo de Revisão
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
	 * [SB0011] Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
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
	 * [SB0012] Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
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
			// Consulta se existe conta que não está em cobranca administrativa
			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, conta.getId()));

			Conta contaPesquisada = (Conta) Util.retonarObjetoDeColecao(Fachada.getInstancia()
							.pesquisar(filtroConta, Conta.class.getName()));
			// 1.1. Caso existam, na lista de contas selecionadas, contas em cobrança
			// administrativa
			if(contaPesquisada.getIndicadorCobrancaAdministrativa().equals(ConstantesSistema.SIM)){
				colecaoContasEmCobrancaAdministrativa.add(contaPesquisada);
			}else{
				// Caso exista conta que não esteja em cobrança administrativa
				// modifica o indicador de todas as contas em cobrança administrativa.
				indicadorTodasAsContasEmCobrancaAdministrativa = false;
			}
		}

		// 1.1.2. Caso o usuário não tenha autorização de acesso ao imóvel
		if(Fachada.getInstancia().obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, Integer.valueOf(idImovel),
						ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE).equals(ConstantesSistema.NAO)){
			// 1.1.2.1. Caso todas as contas da lista de contas selecionadas
			// estejam em cobrança administrativa
			if(indicadorTodasAsContasEmCobrancaAdministrativa){
				throw new ActionServletException("atencao.usuario_empresa_sem_acesso_imovel_conta_cobranca_administrativa", idImovel,
								usuario.getNomeUsuario());
			}else{
				// 1.1.2.2. Caso contrário, ou seja, caso nem todas as contas da lista de contas
				// selecionadas estejam em cobrança administrativa
				// 1.1.2.2.1. Retirar da lista de contas selecionadas
				// as contas que estejam em cobrança administrativa
				if(!Util.isVazioOuBranco(colecaoContasEmCobrancaAdministrativa)){
					for(Conta contaRemover : colecaoContasEmCobrancaAdministrativa){
						colecaoContas.remove(contaRemover);
					}
				}
			}
		}
	}

	/**
	 * [SB0013] Verificar Débito em Cobrança Administrativa
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

		// 1. Caso o usuário logado pertença a uma empresa de cobrança administrativa
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
					// 1.1. Caso existam, na lista de contas selecionadas, contas em cobrança
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

						// Caso o id da conta em cobrança administrativa exista na coleção de
						// Ids a serem removidos exclui a conta da coleção
						if(colecaoIdsContasRemover.contains(contaRemover.getId())){
							colecaoContas.remove(contaRemover);
						}
					}
				}
			}
		}
	}

}