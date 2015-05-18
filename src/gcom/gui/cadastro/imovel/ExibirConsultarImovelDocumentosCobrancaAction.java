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

package gcom.gui.cadastro.imovel;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterIndicadorExistenciaHidrometroHelper;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.*;
import gcom.cobranca.bean.CobrancaDocumentoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 8� Aba - Dopcumento de Cobran�a e ordens de Servi�oi de Cobran�a Emitidos
 * para o Im�vel
 * 
 * @author Rafael Santos
 * @since 05/09/2006
 */
public class ExibirConsultarImovelDocumentosCobrancaAction
				extends GcomAction {

	/**
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

		ActionForward retorno = actionMapping.findForward("consultarImovelDocumentosCobranca");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		// id do imovel da aba documento de cobranca
		String idImovelDocumentosCobranca = consultarImovelActionForm.getIdImovelDocumentosCobranca();
		String limparForm = httpServletRequest.getParameter("limparForm");
		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelPrincipalAba = null;
		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}

		if(limparForm != null && !limparForm.equals("")){
			// limpar os dados
			httpServletRequest.setAttribute("idImovelDocumentosCobrancaNaoEncontrado", null);

			sessao.removeAttribute("imovelClientes");

			consultarImovelActionForm.setIdImovelDadosComplementares(null);
			consultarImovelActionForm.setIdImovelDadosCadastrais(null);
			consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
			consultarImovelActionForm.setIdImovelDebitos(null);
			consultarImovelActionForm.setIdImovelPagamentos(null);
			consultarImovelActionForm.setIdImovelDevolucoesImovel(null);
			consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
			consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setIdImovelRegistroAtendimento(null);

			sessao.removeAttribute("imovelDocumentosCobranca");
			sessao.removeAttribute("colecaoDocumentoCobranca");
			sessao.removeAttribute("idImovelPrincipalAba");
			consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
			consultarImovelActionForm.setMatriculaImovelDocumentosCobranca(null);
			consultarImovelActionForm.setDigitoVerificadorImovelDocumentosCobranca(null);
			consultarImovelActionForm.setSituacaoAguaDocumentosCobranca(null);
			consultarImovelActionForm.setSituacaoEsgotoDocumentosCobranca(null);
			consultarImovelActionForm.setTipoLigacao(null);
			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);

			if(indicadorNovo == null || indicadorNovo.equals("")){
				idImovelDocumentosCobranca = null;
				idImovelPrincipalAba = null;
			}
		}

		if((idImovelDocumentosCobranca != null && !idImovelDocumentosCobranca.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelDocumentosCobranca != null && !idImovelDocumentosCobranca.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){
						consultarImovelActionForm.setIdImovelDocumentosCobranca(idImovelDocumentosCobranca);
						// idImovelDocumentosCobranca = idImovelDocumentosCobranca;

					}else if(!(idImovelDocumentosCobranca.equals(idImovelPrincipalAba))){
						consultarImovelActionForm.setIdImovelDocumentosCobranca(idImovelPrincipalAba);
						idImovelDocumentosCobranca = idImovelPrincipalAba;
					}

				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
				idImovelDocumentosCobranca = idImovelPrincipalAba;
			}

			// Obt�m a inst�ncia da Fachada
			Fachada fachada = Fachada.getInstancia();
			Imovel imovel = null;
			// verifica se o objeto imovel � o mesmo ja pesquisado, para n�o
			// precisar pesquisar mas.
			boolean imovelNovoPesquisado = false;
			if(sessao.getAttribute("imovelDocumentosCobranca") != null){
				imovel = (Imovel) sessao.getAttribute("imovelDocumentosCobranca");
				if(!(imovel.getId().toString().equals(idImovelDocumentosCobranca.trim()))){
					imovel = fachada.consultarImovelHistoricoFaturamento(new Integer(idImovelDocumentosCobranca.trim()));
					imovelNovoPesquisado = true;
				}
			}else{
				imovel = fachada.consultarImovelHistoricoFaturamento(new Integer(idImovelDocumentosCobranca.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){
				sessao.setAttribute("imovelDocumentosCobranca", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelDocumentosCobranca(imovel.getId().toString());

				// caso o imovel pesquisado seja diferente do pesquisado
				// anterior ou seja a primeira vez que se esteja pesquisando
				if(imovelNovoPesquisado){
					// seta na tela a inscri��o do imovel
					httpServletRequest.setAttribute("idImovelDocumentosCobrancaNaoEncontrado", null);

					consultarImovelActionForm.setMatriculaImovelDocumentosCobranca(fachada.pesquisarInscricaoImovel(new Integer(
									idImovelDocumentosCobranca.trim()), true));

					try{
						if(ParametroCadastro.P_MATRICULA_COM_DIGITO_VERIFICADOR.executar().toString()
										.equals(ConstantesSistema.NAO.toString())){
							if(ParametroCadastro.P_METODO_CALCULO_DIGITO_VERIFICADOR.executar().toString().equals("1")){
								consultarImovelActionForm.setDigitoVerificadorImovelDocumentosCobranca(Imovel
												.getDigitoVerificadorMatricula(idImovelDocumentosCobranca.trim()));
							}
						}
					}catch(ControladorException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new ActionServletException(e1.getMessage(), e1);
					}

					// seta a situa��o de agua
					if(imovel.getLigacaoAguaSituacao() != null){
						consultarImovelActionForm.setSituacaoAguaDocumentosCobranca(imovel.getLigacaoAguaSituacao().getDescricao());
					}
					// seta a situa��o de esgoto
					if(imovel.getLigacaoEsgotoSituacao() != null){
						consultarImovelActionForm.setSituacaoEsgotoDocumentosCobranca(imovel.getLigacaoEsgotoSituacao().getDescricao());
					}

					// seta o tipo de liga��o
					if(idImovelDocumentosCobranca != null || idImovelDocumentosCobranca != ""){
						boolean tipoLigacaoBoolean = false;
						ObterIndicadorExistenciaHidrometroHelper obterIndicadorExistenciaHidrometroHelper = fachada
										.obterIndicadorExistenciaHidrometroLigacaoAguaPoco(Util.obterInteger(idImovelDocumentosCobranca),
														tipoLigacaoBoolean);
						if(obterIndicadorExistenciaHidrometroHelper.getIndicadorLigacaoAgua().intValue() == 1
										|| obterIndicadorExistenciaHidrometroHelper.getIndicadorPoco().intValue() == 1){
							consultarImovelActionForm.setTipoLigacao("Hidrometrado");
						}else{
							consultarImovelActionForm.setTipoLigacao("Consumo Fixo");
						}

					}
					// 1� Passo - Pegar o total de registros atrav�s de um count
					// da consulta que aparecer� na tela
					// int totalRegistros = fachada
					// .consultarQuantidadeImovelDocumentosCobranca(new
					// Integer(idImovelDocumentosCobranca.trim()));

					// 2� Passo - Chamar a fun��o de Pagina��o passando o total
					// de registros
					// retorno = this.controlarPaginacao(httpServletRequest,
					// retorno,
					// totalRegistros);

					// 3� Passo - Obter a cole��o da consulta que aparecer� na
					// tela passando o numero de paginas
					// da pesquisa que est� no request
					// Collection colecaoDocumentoCobranca =
					// fachada.consultarImovelDocumentosCobranca(new
					// Integer(idImovelDocumentosCobranca
					// .trim()), 0);

					FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();

					filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("documentoEmissaoForma");
					filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("imovel");
					filtroCobrancaDocumento
									.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma.cobrancaAcao");
					filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoAtividadeComando.cobrancaAcao");
					filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcao.cobrancaAcaoPredecessora");
					filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
					filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.COBRANCA_ACAO_SITUACAO);
					filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.COBRANCA_DEBITO_SITUACAO);

					filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.IMOVEL_ID, new Integer(
									idImovelDocumentosCobranca.trim())));

					filtroCobrancaDocumento.setCampoOrderBy(FiltroCobrancaDocumento.NUMERO_SEQUENCIAL + " DESC");

					Collection<CobrancaDocumento> colecaoDocumentoCobranca = fachada.pesquisar(filtroCobrancaDocumento,
									CobrancaDocumento.class.getName());

					/*
					 * if (colecaoDocumentoCobranca == null ||
					 * colecaoDocumentoCobranca.isEmpty()) {
					 * httpServletRequest.setAttribute(
					 * "idImovelDocumentosCobrancaNaoEncontrado", null);
					 * sessao.removeAttribute("imovelDocumentosCobranca");
					 * sessao.removeAttribute("colecaoDocumentoCobranca");
					 * sessao.removeAttribute("idImovelPrincipalAba");
					 * consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
					 * consultarImovelActionForm.setMatriculaImovelDocumentosCobranca(null);
					 * consultarImovelActionForm.setSituacaoAguaDocumentosCobranca(null);
					 * consultarImovelActionForm.setSituacaoEsgotoDocumentosCobranca(null); //
					 * [FS0010] Nenhum registro encontrado throw new
					 * ActionServletException(
					 * "atencao.pesquisa.nenhumresultado", null, ""); }
					 */

					if(colecaoDocumentoCobranca != null && !colecaoDocumentoCobranca.isEmpty()){
						FiltroCobrancaDebitoSituacao filtroCobrancaDebitoSituacao = new FiltroCobrancaDebitoSituacao();
						filtroCobrancaDebitoSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaDebitoSituacao.ID,
										CobrancaDebitoSituacao.PAGO));
						CobrancaDebitoSituacao cobrancaDebitoSituacaoPago = (CobrancaDebitoSituacao) Util.retonarObjetoDeColecao(fachada
										.pesquisar(filtroCobrancaDebitoSituacao, CobrancaDebitoSituacao.class.getName()));

						Iterator colecaoDocumentoCobrancaIterator = colecaoDocumentoCobranca.iterator();
						CobrancaDocumentoHelper cobrancaDocumentoHelper = null;
						CobrancaDocumento cobrancaDocumento = null;
						Collection<CobrancaDocumentoHelper> colecaoCobrancaDocumentoHelper = new ArrayList();
						Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoItem = null;
						FiltroCobrancaDocumentoItem filtroCobrancaDocumentoItem = new FiltroCobrancaDocumentoItem();

						while(colecaoDocumentoCobrancaIterator.hasNext()){
							cobrancaDocumento = (CobrancaDocumento) colecaoDocumentoCobrancaIterator.next();

							filtroCobrancaDocumentoItem.limparListaParametros();
							filtroCobrancaDocumentoItem.adicionarParametro(new ParametroSimples(
											FiltroCobrancaDocumentoItem.COBRANCA_DOCUMENTO_ID, cobrancaDocumento.getId()));

							colecaoCobrancaDocumentoItem = fachada.pesquisar(filtroCobrancaDocumentoItem,
											CobrancaDocumentoItem.class.getName());

							cobrancaDocumentoHelper = new CobrancaDocumentoHelper();
							cobrancaDocumentoHelper.setCobrancaDocumento(cobrancaDocumento);
							cobrancaDocumentoHelper.setCobrancaDocumentoAcaoCobranca(Fachada.getInstancia()
											.obterCobrancaDocumentoGeradoAcaoCobranca(cobrancaDocumento));

							cobrancaDocumentoHelper.setDescricaoCobrancaAcao(this.obterDescricaoCobrancaAcao(cobrancaDocumento));

							Object[] dadosOrdemServico = fachada.pesquisarDadosOrdemServicoDocumentoCobranca(cobrancaDocumento.getId());
							if(dadosOrdemServico != null){
								if(dadosOrdemServico[0] != null){
									cobrancaDocumentoHelper.setIdOrdemServico((Integer) dadosOrdemServico[0]);
								}
								if(dadosOrdemServico[1] != null){
									Short situacaoOS = (Short) dadosOrdemServico[1];
									if(situacaoOS.equals(OrdemServico.SITUACAO_PENDENTE)){
										cobrancaDocumentoHelper.setSituacaoOrdemServico(OrdemServico.SITUACAO_DESCRICAO_PENDENTE);
									}
									if(situacaoOS.equals(OrdemServico.SITUACAO_ENCERRADO)){
										ObterDescricaoSituacaoOSHelper obterDescricaoSituacaoOSHelper = fachada
														.obterDescricaoSituacaoOS((Integer) dadosOrdemServico[0]);
										cobrancaDocumentoHelper.setSituacaoOrdemServico(obterDescricaoSituacaoOSHelper
														.getDescricaoSituacao());
									}
									if(situacaoOS.equals(OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO)){
										cobrancaDocumentoHelper
														.setSituacaoOrdemServico(OrdemServico.SITUACAO_DESC_ABREV_EXECUCAO_EM_ANDAMENTO);
									}
									if(situacaoOS.equals(OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO)){
										cobrancaDocumentoHelper
														.setSituacaoOrdemServico(OrdemServico.SITUACAO_DESC_ABREV_AGUARDANDO_LIBERACAO);
									}
								}
							}

							FiltroCobrancaDocumento filtro = new FiltroCobrancaDocumento();

							filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID, cobrancaDocumentoHelper
											.getCobrancaDocumento().getId()));

							Collection collection = fachada.pesquisar(filtro, CobrancaDocumento.class.getName());

							CobrancaDocumento cobrancaDocumentoAux = new CobrancaDocumento();

							if(!Util.isVazioOrNulo(collection)){
								cobrancaDocumentoAux = (CobrancaDocumento) collection.iterator().next();
							}

							cobrancaDocumentoHelper.getCobrancaDocumento().setDescricaoParecer(cobrancaDocumentoAux.getDescricaoParecer());

							if(colecaoCobrancaDocumentoItem == null || colecaoCobrancaDocumentoItem.isEmpty()){
								cobrancaDocumentoHelper.setQuantidadeItensCobrancaDocumento(0);
							}else{
								cobrancaDocumentoHelper.setQuantidadeItensCobrancaDocumento(colecaoCobrancaDocumentoItem.size());
							}

							colecaoCobrancaDocumentoHelper.add(cobrancaDocumentoHelper);

							// [SB0003] Verificar Documento de Cobran�a do Tipo Extrato de D�bito
							if(cobrancaDocumento.getDocumentoTipo().getId().equals(DocumentoTipo.EXTRATO_DE_DEBITO)){
								boolean todosItensPagos = true;
								Date maiorDataDebitoSituacao = null;
								for(CobrancaDocumentoItem cobrancaDocumentoItem : colecaoCobrancaDocumentoItem){
									if(!cobrancaDocumentoItem.getCobrancaDebitoSituacao().getId().equals(CobrancaDebitoSituacao.PAGO)){
										todosItensPagos = false;
										break;
									}

									if((maiorDataDebitoSituacao == null)
													|| (maiorDataDebitoSituacao.before(cobrancaDocumentoItem.getDataSituacaoDebito()))){
										maiorDataDebitoSituacao = cobrancaDocumentoItem.getDataSituacaoDebito();
									}
								}

								if(todosItensPagos){
									cobrancaDocumento.setCobrancaDebitoSituacao(cobrancaDebitoSituacaoPago);
									cobrancaDocumento.setDataSituacaoDebito(maiorDataDebitoSituacao);
								}

							}

						}

						// Track No. 619 : Ordenar por data de emiss�o
						Collections.sort((List) colecaoCobrancaDocumentoHelper, new Comparator() {

							public int compare(Object a, Object b){

								String data1 = ((CobrancaDocumentoHelper) a).getCobrancaDocumento().getEmissao().toString();
								String data2 = ((CobrancaDocumentoHelper) b).getCobrancaDocumento().getEmissao().toString();

								data1 = data1.substring(0, 4) + data1.substring(5, 7) + data1.substring(8, 10);
								data2 = data2.substring(0, 4) + data2.substring(5, 7) + data2.substring(8, 10);

								Integer dtEmissao1 = Integer.decode(data1);
								Integer dtEmissao2 = Integer.decode(data2);

								// String dtEmissao1 = ((CobrancaDocumentoHelper)
								// a).getCobrancaDocumento().getEmissao().toString();
								// String dtEmissao2 = ((CobrancaDocumentoHelper)
								// b).getCobrancaDocumento().getEmissao().toString();

								return dtEmissao2.compareTo(dtEmissao1);
							}
						});

						sessao.setAttribute("colecaoDocumentoCobranca", colecaoCobrancaDocumentoHelper);

					}else{
						sessao.removeAttribute("colecaoDocumentoCobranca");
					}
				}
			}else{
				httpServletRequest.setAttribute("idImovelDocumentosCobrancaNaoEncontrado", "true");
				consultarImovelActionForm.setMatriculaImovelDocumentosCobranca("IM�VEL INEXISTENTE");

				// limpar os dados pesquisados
				sessao.removeAttribute("imovelDocumentosCobranca");
				sessao.removeAttribute("colecaoDocumentoCobranca");

				consultarImovelActionForm.setDigitoVerificadorImovelDocumentosCobranca(null);
				consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
				consultarImovelActionForm.setSituacaoAguaDocumentosCobranca(null);
				consultarImovelActionForm.setSituacaoEsgotoDocumentosCobranca(null);
				consultarImovelActionForm.setTipoLigacao(null);

			}
		}else{
			consultarImovelActionForm.setIdImovelDocumentosCobranca(idImovelDocumentosCobranca);

			httpServletRequest.setAttribute("idImovelDocumentosCobrancaNaoEncontrado", null);

			sessao.removeAttribute("imovelDocumentosCobranca");
			sessao.removeAttribute("colecaoDocumentoCobranca");
			sessao.removeAttribute("idImovelPrincipalAba");
			consultarImovelActionForm.setMatriculaImovelDocumentosCobranca(null);
			consultarImovelActionForm.setDigitoVerificadorImovelDocumentosCobranca(null);
			consultarImovelActionForm.setSituacaoAguaDocumentosCobranca(null);
			consultarImovelActionForm.setSituacaoEsgotoDocumentosCobranca(null);
			consultarImovelActionForm.setTipoLigacao(null);

		}

		try{
			if(ParametroCadastro.P_MATRICULA_COM_DIGITO_VERIFICADOR.executar().toString().equals(ConstantesSistema.NAO.toString())){
				if(ParametroCadastro.P_METODO_CALCULO_DIGITO_VERIFICADOR.executar().toString().equals("1")){
					httpServletRequest.setAttribute("matriculaSemDigitoVerificador", '1');
				}else{
					throw new ControladorException("erro.parametro.nao.informado", null, "P_METODO_CALCULO_DIGITO_VERIFICADOR");
				}

			}else{
				httpServletRequest.setAttribute("matriculaSemDigitoVerificador", '0');
			}
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ActionServletException(e.getMessage(), e);
		}

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
