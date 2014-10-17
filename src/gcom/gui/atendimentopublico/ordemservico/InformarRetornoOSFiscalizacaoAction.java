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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.OSEncerramentoHelper;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author S�vio Luiz
 * @date 13/11/2006
 */
public class InformarRetornoOSFiscalizacaoAction
				extends GcomAction {

	private static final String ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER = "autorizarServicoAssociadoHelper";

	private static final String CAMINHO_ENCERRAR_ORDEM_SERVICO = "informarRetornoOSFiscalizacaoAction";

	private static final String CAMINHO_RETORNO_CONSULTAR_OS = "exibirInformarRetornoOSFiscalizacaoAction";

	/**
	 * [UC0448] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author Saulo Lima
	 * @date 20/05/2009
	 *       Altera��o para autorizar OS's associadas, quando for o caso
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InformarRetornoOSFiscalizacaoActionForm informarRetornoOSFiscalizacaoActionForm = (InformarRetornoOSFiscalizacaoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		String idOS = informarRetornoOSFiscalizacaoActionForm.getIdOrdemServico();
		String nomeOS = informarRetornoOSFiscalizacaoActionForm.getNomeOrdemServico();
		String msgFinal = "";

		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = null;

		Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados = (Map<Integer, ServicoAssociadoAutorizacaoHelper>) httpServletRequest
						.getAttribute("mapServicosAutorizados");

		if(mapServicosAutorizados == null || mapServicosAutorizados.isEmpty()){

			// parte que valida o enter
			if((idOS != null && !idOS.trim().equals("")) && (nomeOS == null || nomeOS.equals(""))){

				// [FS0001 - Validar Ordem de Servi�o]
				fachada.validarOrdemServico(Util.converterStringParaInteger(idOS));

				/*
				 * Colocado por Raphael Rossiter em 26/01/2007 Verifica se a OS j� foi fiscalizada
				 */
				fachada.verificarOSJaFiscalizada(new Integer(idOS));

				sessao.setAttribute("idOS", idOS);

				Object[] parmsOS = fachada.pesquisarParmsOS(Util.converterStringParaInteger(idOS));

				if(parmsOS != null){

					String nomeOSPesquisar = "";
					Integer idImovel = null;
					String descricaoSituacaoAgua = "";
					String descricaoSituacaoEsgoto = "";
					String ocorrencia = "";

					if(parmsOS[0] != null){
						nomeOSPesquisar = (String) parmsOS[0];
					}
					if(parmsOS[1] != null){
						idImovel = (Integer) parmsOS[1];
					}
					if(parmsOS[2] != null){
						descricaoSituacaoAgua = (String) parmsOS[2];
					}
					if(parmsOS[3] != null){
						descricaoSituacaoEsgoto = (String) parmsOS[3];
					}
					if(parmsOS[4] != null){
						ocorrencia = (String) parmsOS[4];
					}

					informarRetornoOSFiscalizacaoActionForm.setNomeOrdemServico(nomeOSPesquisar);
					informarRetornoOSFiscalizacaoActionForm.setMatriculaImovel("" + idImovel);

					// Inscri��o Im�vel
					String inscricaoImovel = fachada.pesquisarInscricaoImovel(idImovel, true);
					informarRetornoOSFiscalizacaoActionForm.setInscricaoImovel(inscricaoImovel);

					// Situa��o da Liga��o de Agua
					informarRetornoOSFiscalizacaoActionForm.setSituacaoLigacaoAgua(descricaoSituacaoAgua);

					// Situa��o da Liga��o de Esgoto
					informarRetornoOSFiscalizacaoActionForm.setSituacaoLigacaoEsgoto(descricaoSituacaoEsgoto);

					// Cliente Usu�rio
					this.pesquisarCliente(informarRetornoOSFiscalizacaoActionForm);

					// ocorrencia
					informarRetornoOSFiscalizacaoActionForm.setOcorrencia(ocorrencia);

				}else{
					throw new ActionServletException("atencao.naocadastrado", null, "Ordem de Servi�o inexistente");

				}

			}

			// Carregando os atributos do objeto FiscalizacaoSituacao selecionado
			FiltroFiscalizacaoSituacao filtroFiscalizacaoSituacao = new FiltroFiscalizacaoSituacao();

			filtroFiscalizacaoSituacao.adicionarParametro(new ParametroSimples(FiltroFiscalizacaoSituacao.ID,
							informarRetornoOSFiscalizacaoActionForm.getSituacao()));

			Collection<FiscalizacaoSituacao> colecaoFiscalizacaoSituacao = fachada.pesquisar(filtroFiscalizacaoSituacao,
							FiscalizacaoSituacao.class.getName());

			FiscalizacaoSituacao fiscalizacaoSituacao = (FiscalizacaoSituacao) Util.retonarObjetoDeColecao(colecaoFiscalizacaoSituacao);

			// LigacaoAguaSituacao do Im�vel
			Integer idLigacaoAguaSituacaoImovel = fachada.pesquisarIdLigacaoAguaSituacao(Util
							.converterStringParaInteger(informarRetornoOSFiscalizacaoActionForm.getMatriculaImovel()));

			// LigacaoEsgotoSituacao do Im�vel
			Integer idLigacaoEsgotoSituacaoImovel = fachada.pesquisarIdLigacaoEsgotoSituacao(Util
							.converterStringParaInteger(informarRetornoOSFiscalizacaoActionForm.getMatriculaImovel()));

			// Usu�rio logado no sistema
			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

			// Colocado por Raphael Rossiter em 03/03/2007
			if(informarRetornoOSFiscalizacaoActionForm.getIndicadorGeracaoDebito() == null
							|| informarRetornoOSFiscalizacaoActionForm.getIndicadorGeracaoDebito().equalsIgnoreCase("")){

				informarRetornoOSFiscalizacaoActionForm.setIndicadorGeracaoDebito("1");
			}

			// [UC0488] Informar Retorno Ordem de Fiscaliza��o
			Integer[] idsGerados = fachada.informarRetornoOSFiscalizacao(Util
							.converterStringParaInteger(informarRetornoOSFiscalizacaoActionForm.getIdOrdemServico()), fiscalizacaoSituacao,
							informarRetornoOSFiscalizacaoActionForm.getIndicadorDocumentoEntregue(), idLigacaoAguaSituacaoImovel,
							idLigacaoEsgotoSituacaoImovel, Util.converterStringParaInteger(informarRetornoOSFiscalizacaoActionForm
											.getMatriculaImovel()), informarRetornoOSFiscalizacaoActionForm.getIndicadorTipoMedicao(),
							informarRetornoOSFiscalizacaoActionForm.getIndicadorGeracaoDebito(), usuario);

			msgFinal = "Ordem de Servi�o de c�digo " + idsGerados[0] + " atualizada com sucesso.";

			if(idsGerados[1] != null || idsGerados[2] != null){

				// Registro de Atendimento gerado
				if(idsGerados[1] != null){
					msgFinal = msgFinal + " Registro(s) de atendimento(s) com c�digo(s) " + idsGerados[1];

					if(idsGerados[2] != null){
						msgFinal = msgFinal + ", " + idsGerados[2];
					}
				}else{
					msgFinal = msgFinal + " Registro(s) de atendimento(s) com c�digo(s) " + idsGerados[2];
				}

				if(idsGerados[3] != null || idsGerados[4] != null){

					// Ordem de Servi�o
					if(idsGerados[3] != null){
						msgFinal = msgFinal + " e ordem(ns) de servi�o(s) com c�digo(s) " + idsGerados[3];

						if(idsGerados[4] != null){
							msgFinal = msgFinal + ", " + idsGerados[4];
						}
					}else{
						msgFinal = msgFinal + " e ordem(ns) de servi�o(s) com c�digo(s) " + idsGerados[4];
					}
				}

				msgFinal = msgFinal + " gerado(s) com sucesso.";
			}

			sessao.setAttribute("msgFinal", msgFinal);
			sessao.setAttribute("idOS", idOS);

			/*
			 * retorno = montarPaginaConfirmacaoWizard( "atencao.deseja_encerrar_os",
			 * httpServletRequest, actionMapping, "");
			 */

			if(!informarRetornoOSFiscalizacaoActionForm.getIndicadorEncerramentoOS().equals("2")){

				Integer numeroOS = Integer.valueOf(idOS);
				Date dataAtual = new Date();

				if(informarRetornoOSFiscalizacaoActionForm.getAtendimentoMotivoEncerramento() != null
								&& !informarRetornoOSFiscalizacaoActionForm.getAtendimentoMotivoEncerramento().equals("")
								&& !informarRetornoOSFiscalizacaoActionForm.getAtendimentoMotivoEncerramento().equalsIgnoreCase("-1")){

					String idMotivoEncerramento = informarRetornoOSFiscalizacaoActionForm.getAtendimentoMotivoEncerramento();

					OSEncerramentoHelper osEncerramentoHelper = new OSEncerramentoHelper();
					osEncerramentoHelper.setNumeroOS(numeroOS);
					osEncerramentoHelper.setDataExecucao(dataAtual);
					osEncerramentoHelper.setUsuarioLogado(usuario);
					osEncerramentoHelper.setIdMotivoEncerramento(idMotivoEncerramento);
					osEncerramentoHelper.setUltimaAlteracao(dataAtual);
					osEncerramentoHelper.setParecerEncerramento("ORDEM DE SERVICO ENCERRADA ATRAVES DA FUNCIONALIDADE DE FISCALIZACAO.");
					osEncerramentoHelper.setIndicadorPavimento(ServicoTipo.INDICADOR_PAVIMENTO_NAO);
					osEncerramentoHelper.setIndicadorVistoriaServicoTipo(ServicoTipo.INDICADOR_VISTORIA_SERVICO_TIPO_NAO);

					AtendimentoMotivoEncerramento motivoEncerramento = fachada.pesquisarAtendimentoMotivoEncerramentoPorId(Integer
									.valueOf(idMotivoEncerramento));

					// Se houve execu��o, verificar se existem Servi�os Associados a serem
					// autorizados
					if(motivoEncerramento.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){

						OrdemServico ordemServico = fachada.consultarDadosOrdemServico(numeroOS);
						ServicoTipo servicoTipo = ordemServico.getServicoTipo();

						List<ServicoAssociadoAutorizacaoHelper> servicoAssociadoAutorizacaoHelperList = fachada
										.verificarServicosAssociadosParaAutorizacao(servicoTipo,
														EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO,
														OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, numeroOS);

						if(servicoAssociadoAutorizacaoHelperList != null && !servicoAssociadoAutorizacaoHelperList.isEmpty()){

							// Parametros obrigatorios para a tela de autoriza��o
							autorizarServicoAssociadoHelper = new AutorizarServicoAssociadoHelper();

							Map<String, Object> parametrosCaminhoAutorizacao = new HashMap<String, Object>();
							parametrosCaminhoAutorizacao.put("osEncerramentoHelper", osEncerramentoHelper);
							parametrosCaminhoAutorizacao.put("msgFinal", msgFinal);
							autorizarServicoAssociadoHelper.setParametrosCaminhoAutorizacao(parametrosCaminhoAutorizacao);
							autorizarServicoAssociadoHelper.setCaminhoAutorizacao(CAMINHO_ENCERRAR_ORDEM_SERVICO);

							Map<String, Object> parametrosCaminhoRetorno = new HashMap<String, Object>();
							parametrosCaminhoRetorno.put("numeroOS", numeroOS);
							autorizarServicoAssociadoHelper.setParametrosCaminhoRetorno(parametrosCaminhoRetorno);
							autorizarServicoAssociadoHelper.setCaminhoRetorno(CAMINHO_RETORNO_CONSULTAR_OS);

							autorizarServicoAssociadoHelper.setServicosParaAutorizacao(servicoAssociadoAutorizacaoHelperList);

							sessao.setAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER, autorizarServicoAssociadoHelper);

							retorno = actionMapping.findForward("mostrarAutorizarServicoAssociado");

							return retorno;

						}
					}

					fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, null,
									OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, null);

				}else{
					throw new ActionServletException("atencao.Informe_entidade", null, "Motivo Encerramento Ordem Servi�o");
				}
			}
		}else{

			autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
							.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);
			Map<String, Object> parametrosCaminhoAutorizacao = autorizarServicoAssociadoHelper.getParametrosCaminhoAutorizacao();
			OSEncerramentoHelper osEncerramentoHelper = (OSEncerramentoHelper) parametrosCaminhoAutorizacao.get("osEncerramentoHelper");
			msgFinal = (String) parametrosCaminhoAutorizacao.get("msgFinal");

			fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, mapServicosAutorizados,
							OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, null);
		}

		// montando p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, msgFinal, "Informar outra fiscaliza��o",
						"/exibirInformarRetornoOSFiscalizacaoAction.do?menu=sim");

		return retorno;
	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author S�vio Luiz
	 * @date 13/11/2006
	 */
	private void pesquisarCliente(InformarRetornoOSFiscalizacaoActionForm informarRetornoOSFiscalizacaoActionForm){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, informarRetornoOSFiscalizacaoActionForm
						.getMatriculaImovel()));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection<ClienteImovel> colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel,
						ClienteImovel.class.getName());

		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel.iterator().next();

			Cliente cliente = clienteImovel.getCliente();

			String documento = "";

			if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
				documento = cliente.getCpfFormatado();
			}else{
				documento = cliente.getCnpjFormatado();
			}
			// Cliente Nome/CPF-CNPJ
			informarRetornoOSFiscalizacaoActionForm.setClienteUsuario(cliente.getNome());
			informarRetornoOSFiscalizacaoActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

}