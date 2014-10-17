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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.gui.cobranca;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.spcserasa.FiltroNegativador;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pre- processamento para atualiza o criterio da cobran�a
 * 
 * @author S�vio Luiz
 * @date 06/11/2006
 * @author eduardo henrique
 * @date 26/08/2008
 *       Altera��es no [UC0645] para a v0.04
 * @author Virg�nia Melo
 * @date 31/10/2008
 *       Desfazer altera��es para a v0.06
 * @author eduardo henrique
 * @date 01/12/2008 - Retirada da valida��o de exist�ncia de A��o de Cobran�a
 */
public class ExibirAtualizarAcaoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("atualizarAcaoCobranca");

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		// caso seja a primeira vez
		if(httpServletRequest.getParameter("menu") != null){
			sessao.removeAttribute("voltar");
		}

		FiltroNegativador filtroNegativador = new FiltroNegativador();
		filtroNegativador.adicionarCaminhoParaCarregamentoEntidade(FiltroNegativador.CLIENTE);
		filtroNegativador.adicionarParametro(new ParametroSimples(FiltroNegativador.INDICADOR_USO, ConstantesSistema.SIM));
		filtroNegativador.adicionarParametro(new ParametroNaoNulo(FiltroNegativador.NEGATIVADOR_CLIENTE));

		Collection colecaoNegativador = getFachada().pesquisar(filtroNegativador, Negativador.class.getName());

		sessao.setAttribute("colecaoNegativador", colecaoNegativador);

		AcaoCobrancaAtualizarActionForm acaoCobrancaAtualizarActionForm = (AcaoCobrancaAtualizarActionForm) actionForm;
		if((httpServletRequest.getParameter("idRegistroAtualizar") != null && !httpServletRequest.getParameter("idRegistroAtualizar")
						.equals(""))
						|| (sessao.getAttribute("cobrancaAcao") != null && !sessao.getAttribute("cobrancaAcao").equals(""))){

			if(httpServletRequest.getParameter("objetoConsulta") == null){

				CobrancaAcao cobrancaAcao = null;
				if(httpServletRequest.getParameter("idRegistroAtualizar") != null
								&& !httpServletRequest.getParameter("idRegistroAtualizar").equals("")){
					String idAcaoCobranca = httpServletRequest.getParameter("idRegistroAtualizar");
					FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
					filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, idAcaoCobranca));
					filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade("cobrancaCriterio");
					filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");
					filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.ACAO_COBRANCA_EFEITO);

					Collection colecaoCobrancaAcao = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());
					if(colecaoCobrancaAcao != null && !colecaoCobrancaAcao.isEmpty()){

						cobrancaAcao = (CobrancaAcao) Util.retonarObjetoDeColecao(colecaoCobrancaAcao);
					}
					sessao.setAttribute("voltar", "manter");
				}else{
					cobrancaAcao = (CobrancaAcao) sessao.getAttribute("cobrancaAcao");
					sessao.setAttribute("voltar", "filtrar");
				}

				if(cobrancaAcao != null && !cobrancaAcao.equals("")){

					if(cobrancaAcao.getDescricaoCobrancaAcao() != null){
						acaoCobrancaAtualizarActionForm.setDescricaoAcao(cobrancaAcao.getDescricaoCobrancaAcao());
					}

					if(cobrancaAcao.getCobrancaCriterio() != null){
						acaoCobrancaAtualizarActionForm.setIdCobrancaCriterio("" + cobrancaAcao.getCobrancaCriterio().getId());
						acaoCobrancaAtualizarActionForm.setDescricaoCobrancaCriterio(cobrancaAcao.getCobrancaCriterio()
										.getDescricaoCobrancaCriterio());
					}

					if(cobrancaAcao.getNegativador() != null){
						acaoCobrancaAtualizarActionForm.setNegativador(cobrancaAcao.getNegativador().getId().toString());
					}

					acaoCobrancaAtualizarActionForm.setIcClienteUsuarioSemCPFCNPJ(cobrancaAcao.getIndicadorCPFCNPJ().toString());

					acaoCobrancaAtualizarActionForm.setIcEnderecoSemCEP(cobrancaAcao.getIndicadorCEP().toString());

					if(cobrancaAcao.getServicoTipo() != null){
						acaoCobrancaAtualizarActionForm.setIdServicoTipo("" + cobrancaAcao.getServicoTipo().getId());
						acaoCobrancaAtualizarActionForm.setDescricaoServicoTipo(cobrancaAcao.getServicoTipo().getDescricao());
					}

					if(cobrancaAcao.getIndicadorObrigatoriedade() != null){
						acaoCobrancaAtualizarActionForm.setIcAcaoObrigatoria("" + cobrancaAcao.getIndicadorObrigatoriedade());
					}

					if(cobrancaAcao.getIndicadorAcrescimoImpontualidade() != null){
						acaoCobrancaAtualizarActionForm.setIcAcrescimosImpontualidade(""
										+ cobrancaAcao.getIndicadorAcrescimoImpontualidade());
					}

					if(cobrancaAcao.getIndicadorCronograma() != null){
						acaoCobrancaAtualizarActionForm.setIcCompoeCronograma("" + cobrancaAcao.getIndicadorCronograma());
					}

					if(cobrancaAcao.getIndicadorCobrancaDebACobrar() != null){
						acaoCobrancaAtualizarActionForm.setIcDebitosACobrar("" + cobrancaAcao.getIndicadorCobrancaDebACobrar());
					}

					if(cobrancaAcao.getIndicadorBoletim() != null){
						acaoCobrancaAtualizarActionForm.setIcEmitirBoletimCadastro("" + cobrancaAcao.getIndicadorBoletim());
					}

					if(cobrancaAcao.getIndicadorGeracaoTaxa() != null){
						acaoCobrancaAtualizarActionForm.setIcGeraTaxa("" + cobrancaAcao.getIndicadorGeracaoTaxa());
					}

					if(cobrancaAcao.getIndicadorDebito() != null){
						acaoCobrancaAtualizarActionForm.setIcImoveisSemDebitos("" + cobrancaAcao.getIndicadorDebito());
					}

					if(cobrancaAcao.getIndicadorRepeticao() != null){
						acaoCobrancaAtualizarActionForm.setIcRepetidaCiclo("" + cobrancaAcao.getIndicadorRepeticao());
					}

					if(cobrancaAcao.getIndicadorSuspensaoAbastecimento() != null){
						acaoCobrancaAtualizarActionForm.setIcSuspensaoAbastecimento("" + cobrancaAcao.getIndicadorSuspensaoAbastecimento());
					}

					if(cobrancaAcao.getCobrancaAcaoPredecessora() != null){
						acaoCobrancaAtualizarActionForm.setIdAcaoPredecessora("" + cobrancaAcao.getCobrancaAcaoPredecessora().getId());
					}

					if(cobrancaAcao.getCobrancaSituacao() != null){
						acaoCobrancaAtualizarActionForm.setIdSituacaoCobranca("" + cobrancaAcao.getCobrancaSituacao().getId());
					}

					if(cobrancaAcao.getLigacaoAguaSituacao() != null){
						acaoCobrancaAtualizarActionForm.setIdSituacaoLigacaoAgua("" + cobrancaAcao.getLigacaoAguaSituacao().getId());
					}

					if(cobrancaAcao.getLigacaoEsgotoSituacao() != null){
						acaoCobrancaAtualizarActionForm.setIdSituacaoLigacaoEsgoto("" + cobrancaAcao.getLigacaoEsgotoSituacao().getId());
					}

					if((httpServletRequest.getParameter("mudouTipoDocumento") != null && !httpServletRequest.getParameter(
									"mudouTipoDocumento").equals(""))){

						if(acaoCobrancaAtualizarActionForm.getIdTipoDocumentoGerado() == null
										|| acaoCobrancaAtualizarActionForm.getIdTipoDocumentoGerado().equals("")
										|| !acaoCobrancaAtualizarActionForm.getIdTipoDocumentoGerado().equals(
														DocumentoTipo.CARTA_OPCAO_PARCELAMENTO.toString())){

							acaoCobrancaAtualizarActionForm.setIndicadorConsideraCreditoRealizar(null);
							acaoCobrancaAtualizarActionForm.setIndicadorConsideraGuiaPagamento(null);
							acaoCobrancaAtualizarActionForm.setIdPrimeiraResolucaoDiretoria(null);
							acaoCobrancaAtualizarActionForm.setIdSegundaResolucaoDiretoria(null);
							acaoCobrancaAtualizarActionForm.setIdTerceiraResolucaoDiretoria(null);
							sessao.removeAttribute("colecaoResolucaoDiretoriaAtualizar");
						}else{

							// [FS0012] - Verificar se o usu�rio possui autoriza��o para utilizar a
							// RD
							boolean temPermissaoResolucaoDiretoria = fachada.verificarPermissaoEspecial(
											PermissaoEspecial.USAR_PLANO_PAI_PARA_ORGAO_PUBLICO, usuario);
							Collection<ResolucaoDiretoria> colecaoResolucaoDiretoria = null;

							/*
							 * Caso o usu�rio possua permiss�o especial para utilizar qualquer RD
							 * (existe ocorr�ncia
							 * na tabela USUARIO_PERMISSAO_ESPECIAL com USUR_ID=Id do usu�rio logado
							 * e PMEP_ID=19
							 * (PARCELAR - USAR PLANO PAI PARA ORGAO PUBLICO))
							 */
							if(temPermissaoResolucaoDiretoria){

								colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaDataVigenciaFimMaiorIgualDataAtual();

							}else{

								/*
								 * Caso contr�rio, ou seja, o usu�rio n�o possua permiss�o especial
								 * para utilizar
								 * qualquer RD, selecionar as RD�s de uso livre e as RD�s que o
								 * usu�rio possua
								 * permiss�o para utilizar
								 */
								Integer idUsuarioLogado = usuario.getId();
								Collection<Grupo> colecaoGrupoUsuario = fachada.pesquisarGruposUsuario(idUsuarioLogado);

								Collection<Integer> idsGrupoUsuario = new ArrayList();

								if(!Util.isVazioOrNulo(colecaoGrupoUsuario)){

									Integer idGrupo = null;

									for(Grupo grupo : colecaoGrupoUsuario){

										idGrupo = grupo.getId();
										idsGrupoUsuario.add(idGrupo);
									}
								}

								colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaMaiorDataVigenciaInicio(idsGrupoUsuario);

								Collection<ResolucaoDiretoria> colecaoResolucaoDiretoriaAux = fachada
												.pesquisarResolucaoDiretoriaPermitidaAoUsuario(usuario.getId());

								if(!Util.isVazioOrNulo(colecaoResolucaoDiretoriaAux)){

									if(colecaoResolucaoDiretoria == null){

										colecaoResolucaoDiretoria = new ArrayList<ResolucaoDiretoria>();
									}

									colecaoResolucaoDiretoria.addAll(colecaoResolucaoDiretoriaAux);
								}
							}

							// [FS0001] - Verificar exist�ncia de dados
							if(!Util.isVazioOrNulo(colecaoResolucaoDiretoria)){

								sessao.setAttribute("colecaoResolucaoDiretoriaAtualizar", colecaoResolucaoDiretoria);
							}else{

								throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Resolu��o Diretoria");
							}
						}
					}else{

						if(cobrancaAcao.getDocumentoTipo() != null){
							acaoCobrancaAtualizarActionForm.setIdTipoDocumentoGerado("" + cobrancaAcao.getDocumentoTipo().getId());
						}

						if(cobrancaAcao.getPrimeiraResolucaoDiretoria() != null){
							acaoCobrancaAtualizarActionForm.setIdPrimeiraResolucaoDiretoria(""
											+ cobrancaAcao.getPrimeiraResolucaoDiretoria().getId());
						}

						if(cobrancaAcao.getSegundaResolucaoDiretoria() != null){
							acaoCobrancaAtualizarActionForm.setIdSegundaResolucaoDiretoria(""
											+ cobrancaAcao.getSegundaResolucaoDiretoria().getId());
						}

						if(cobrancaAcao.getTerceiraResolucaoDiretoria() != null){
							acaoCobrancaAtualizarActionForm.setIdTerceiraResolucaoDiretoria(""
											+ cobrancaAcao.getTerceiraResolucaoDiretoria().getId());
						}

						if(cobrancaAcao.getIndicadorConsideraGuiaPagamento() != null){
							acaoCobrancaAtualizarActionForm.setIndicadorConsideraGuiaPagamento(""
											+ cobrancaAcao.getIndicadorConsideraGuiaPagamento());
						}

						if(cobrancaAcao.getIndicadorConsideraCreditoRealizar() != null){
							acaoCobrancaAtualizarActionForm.setIndicadorConsideraCreditoRealizar(""
											+ cobrancaAcao.getIndicadorConsideraCreditoRealizar());
						}
					}

					if(cobrancaAcao.getNumeroDiasMinimoAcaoPrecedente() != null){
						acaoCobrancaAtualizarActionForm.setNumeroDiasEntreAcoes("" + cobrancaAcao.getNumeroDiasMinimoAcaoPrecedente());
					}

					if(cobrancaAcao.getNumeroDiasValidade() != null){
						acaoCobrancaAtualizarActionForm.setNumeroDiasValidade("" + cobrancaAcao.getNumeroDiasValidade());
					}

					if(cobrancaAcao.getQtdDiasRealizacao() != null){
						acaoCobrancaAtualizarActionForm.setQtdDiasRealizacao("" + cobrancaAcao.getQtdDiasRealizacao());
					}

					if(cobrancaAcao.getOrdemRealizacao() != null){
						acaoCobrancaAtualizarActionForm.setOrdemCronograma("" + cobrancaAcao.getOrdemRealizacao());
					}

					if(cobrancaAcao.getNumeroDiasVencimento() != null){
						acaoCobrancaAtualizarActionForm.setNumeroDiasVencimento("" + cobrancaAcao.getNumeroDiasVencimento());
					}

					if(cobrancaAcao.getAcaoCobrancaEfeito() != null){
						acaoCobrancaAtualizarActionForm.setAcaoCobrancaEfeito("" + cobrancaAcao.getAcaoCobrancaEfeito().getId());
					}
				}
				// faz as pesquisas obrigat�rias
				pesquisasObrigatorias(fachada, sessao, usuario);

				// seta o objeto na sess�o para ser atualizado
				sessao.setAttribute("cobrancaAcao", cobrancaAcao);

			}
		}

		// pesquisa os dados do enter
		pesquisarEnter(acaoCobrancaAtualizarActionForm, httpServletRequest, fachada);

		return retorno;
	}

	/**
	 * M�todo respons�vel por realizar as pesquisas ao teclar enter
	 * 
	 * @param acaoCobrancaAtualizarActionForm
	 * @param httpServletRequest
	 * @param fachada
	 */
	private void pesquisarEnter(AcaoCobrancaAtualizarActionForm acaoCobrancaAtualizarActionForm, HttpServletRequest httpServletRequest,
					Fachada fachada){

		// pesquisa enter de crit�rio de cobran�a
		if(acaoCobrancaAtualizarActionForm.getIdCobrancaCriterio() != null
						&& !acaoCobrancaAtualizarActionForm.getIdCobrancaCriterio().equals("")
						&& (acaoCobrancaAtualizarActionForm.getDescricaoCobrancaCriterio() == null || acaoCobrancaAtualizarActionForm
										.getDescricaoCobrancaCriterio().equals(""))){

			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();

			try{
				filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, Integer
								.valueOf(acaoCobrancaAtualizarActionForm.getIdCobrancaCriterio())));
			}catch(NumberFormatException ex){
				throw new ActionServletException("atencao.campo_texto.numero_obrigatorio", null, "Crit�rio de Cobran�a");
			}

			filtroCobrancaCriterio.setCampoOrderBy(FiltroCobrancaCriterio.DESCRICAO_COBRANCA_CRITERIO);
			Collection colecaoCobrancaCriterio = fachada.pesquisar(filtroCobrancaCriterio, CobrancaCriterio.class.getName());

			if(colecaoCobrancaCriterio != null && !colecaoCobrancaCriterio.isEmpty()){
				CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) Util.retonarObjetoDeColecao(colecaoCobrancaCriterio);
				acaoCobrancaAtualizarActionForm.setDescricaoCobrancaCriterio(cobrancaCriterio.getDescricaoCobrancaCriterio());
			}else{
				acaoCobrancaAtualizarActionForm.setIdCobrancaCriterio("");
				acaoCobrancaAtualizarActionForm.setDescricaoCobrancaCriterio("COBRAN�A CRIT�RIO INEXISTENTE");
			}

		}

		// pesquisa enter de tipo de servi�o
		if(acaoCobrancaAtualizarActionForm.getIdServicoTipo() != null
						&& !acaoCobrancaAtualizarActionForm.getIdServicoTipo().equals("")
						&& (acaoCobrancaAtualizarActionForm.getDescricaoServicoTipo() == null || acaoCobrancaAtualizarActionForm
										.getDescricaoServicoTipo().equals(""))){

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			try{
				filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, Integer
								.valueOf(acaoCobrancaAtualizarActionForm.getIdServicoTipo())));
			}catch(NumberFormatException ex){
				throw new ActionServletException("atencao.campo_texto.numero_obrigatorio", null, "Servi�o Tipo");
			}
			filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);
			Collection colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
				ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);
				acaoCobrancaAtualizarActionForm.setDescricaoServicoTipo(servicoTipo.getDescricao());
			}else{
				acaoCobrancaAtualizarActionForm.setIdServicoTipo("");
				acaoCobrancaAtualizarActionForm.setDescricaoServicoTipo("TIPO DE SERVI�O INEXISTENTE");
			}

		}
	}

	/**
	 * Pesquisas obrig�rias.
	 * 
	 * @author Eduardo Henrique
	 * @author Virg�nia Melo Desfazer altera��es para v0.06
	 * @param fachada
	 *            Fachada
	 * @param sessao
	 *            Sess�o
	 */
	private void pesquisasObrigatorias(Fachada fachada, HttpSession sessao, Usuario usuarioLogado){

		// pesquisa as a��es predecessoras
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
		filtroCobrancaAcao
						.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoAcaoPredecessora = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());
		sessao.setAttribute("colecaoAcaoPredecessora", colecaoAcaoPredecessora);

		// pesquisa os tipos de documentos
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

		if(colecaoDocumentoTipo == null || colecaoDocumentoTipo.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Documento Tipo");
		}
		sessao.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);

		// pesquisa cobranca_situacao
		FiltroCobrancaSituacao filtroCobrancaSituacao = new FiltroCobrancaSituacao();
		filtroCobrancaSituacao.setCampoOrderBy(FiltroCobrancaSituacao.DESCRICAO);
		filtroCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoCobrancaSituacao = fachada.pesquisar(filtroCobrancaSituacao, CobrancaSituacao.class.getName());

		if(colecaoCobrancaSituacao == null || colecaoCobrancaSituacao.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Cobran�a Situa��o");
		}else{
			sessao.setAttribute("colecaoCobrancaSituacao", colecaoCobrancaSituacao);
		}

		// pesquisa as situa��es de liga��es de agua
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
		filtroDocumentoTipo.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

		if(colecaoLigacaoAguaSituacao == null || colecaoLigacaoAguaSituacao.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Liga��o Agua Situa��o");
		}
		sessao.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);

		// pesquisa as situa��es de liga��es de agua
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroDocumentoTipo.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

		if(colecaoLigacaoEsgotoSituacao == null || colecaoLigacaoEsgotoSituacao.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Liga��o Esgoto Situa��o");
		}
		sessao.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);

		// Pesquisa as Contra A�oes.
		FiltroAcaoCobrancaEfeito filtroAcaoCobrancaEfeito = new FiltroAcaoCobrancaEfeito();
		filtroAcaoCobrancaEfeito.adicionarParametro(new ParametroSimples(FiltroAcaoCobrancaEfeito.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroAcaoCobrancaEfeito.setCampoOrderBy(FiltroAcaoCobrancaEfeito.DESCRICAO);
		Collection colecaoContraAcao = fachada.pesquisar(filtroAcaoCobrancaEfeito, AcaoCobrancaEfeito.class.getName());
		sessao.setAttribute("colecaoAcaoCobrancaEfeito", colecaoContraAcao);

		if(sessao.getAttribute("colecaoResolucaoDiretoriaAtualizar") == null){

			// [FS0012] - Verificar se o usu�rio possui autoriza��o para utilizar a RD
			boolean temPermissaoResolucaoDiretoria = fachada.verificarPermissaoEspecial(
							PermissaoEspecial.USAR_PLANO_PAI_PARA_ORGAO_PUBLICO, usuarioLogado);
			Collection<ResolucaoDiretoria> colecaoResolucaoDiretoria = null;

			/*
			 * Caso o usu�rio possua permiss�o especial para utilizar qualquer RD (existe ocorr�ncia
			 * na tabela USUARIO_PERMISSAO_ESPECIAL com USUR_ID=Id do usu�rio logado e PMEP_ID=19
			 * (PARCELAR - USAR PLANO PAI PARA ORGAO PUBLICO))
			 */
			if(temPermissaoResolucaoDiretoria){

				colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaDataVigenciaFimMaiorIgualDataAtual();

			}else{

				/*
				 * Caso contr�rio, ou seja, o usu�rio n�o possua permiss�o especial para utilizar
				 * qualquer RD, selecionar as RD�s de uso livre e as RD�s que o usu�rio possua
				 * permiss�o para utilizar
				 */
				Integer idUsuarioLogado = usuarioLogado.getId();
				Collection<Grupo> colecaoGrupoUsuario = fachada.pesquisarGruposUsuario(idUsuarioLogado);

				Collection<Integer> idsGrupoUsuario = new ArrayList();

				if(!Util.isVazioOrNulo(colecaoGrupoUsuario)){

					Integer idGrupo = null;

					for(Grupo grupo : colecaoGrupoUsuario){

						idGrupo = grupo.getId();
						idsGrupoUsuario.add(idGrupo);
					}
				}

				colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaMaiorDataVigenciaInicio(idsGrupoUsuario);

				Collection<ResolucaoDiretoria> colecaoResolucaoDiretoriaAux = fachada
								.pesquisarResolucaoDiretoriaPermitidaAoUsuario(usuarioLogado.getId());

				if(!Util.isVazioOrNulo(colecaoResolucaoDiretoriaAux)){

					if(colecaoResolucaoDiretoria == null){

						colecaoResolucaoDiretoria = new ArrayList<ResolucaoDiretoria>();
					}

					colecaoResolucaoDiretoria.addAll(colecaoResolucaoDiretoriaAux);
				}
			}

			// [FS0001] - Verificar exist�ncia de dados
			if(colecaoResolucaoDiretoria != null && !colecaoResolucaoDiretoria.isEmpty()){
				sessao.setAttribute("colecaoResolucaoDiretoriaAtualizar", colecaoResolucaoDiretoria);
			}else{
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Resolu��o Diretoria");
			}
		}
	}
}
