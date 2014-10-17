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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroLocalOcorrencia;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosIdentificacaoLocalOcorrenciaHelper;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DivisaoEsgoto;
import gcom.operacional.FiltroDivisaoEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
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
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os
 * par�metros para realiza��o da Atualiza��o de um R.A (Aba n� 02 - Dados do
 * local de ocorr�ncia)
 * 
 * @author S�vio Luiz
 * @date 10/08/2006
 */
public class ExibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarRegistroAtendimentoDadosLocalOcorrencia");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		httpServletRequest.setAttribute("abrirPopUpEnviarContaRevisao", "N");

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm = (AtualizarRegistroAtendimentoActionForm) actionForm;

		controlarFormularioSelecionarContaRevisao(httpServletRequest, sessao, atualizarRegistroAtendimentoActionForm);

		/*
		 * Carregamento inicial da tela respons�vel pelo redebimento das
		 * informa��es referentes ao local da ocorr�ncia (ABA N� 02)
		 * ==========================================================================================
		 * ==================
		 */

		/*
		 * Adicionar endere�o
		 */
		String adicionarEndereco = httpServletRequest.getParameter("tipoPesquisaEndereco");

		if(adicionarEndereco != null && !adicionarEndereco.trim().equalsIgnoreCase("")){

			retorno = actionMapping.findForward("informarEndereco");
		}else{

			/*
			 * Divis�o de Esgoto - Carregando a cole��o que ir� ficar dispon�vel
			 * para escolha do usu�rio
			 * [FS0003] � Verificar exist�ncia de dados
			 */
			Collection colecaoDivisaoEsgoto = (Collection) sessao.getAttribute("colecaoDivisaoEsgoto");

			if(colecaoDivisaoEsgoto == null){

				FiltroDivisaoEsgoto filtroDivisaoEsgoto = new FiltroDivisaoEsgoto(FiltroDivisaoEsgoto.DESCRICAO);

				filtroDivisaoEsgoto.adicionarParametro(new ParametroSimples(FiltroDivisaoEsgoto.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroDivisaoEsgoto.setConsultaSemLimites(true);

				colecaoDivisaoEsgoto = fachada.pesquisar(filtroDivisaoEsgoto, DivisaoEsgoto.class.getName());

				if(colecaoDivisaoEsgoto == null || colecaoDivisaoEsgoto.isEmpty()){
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "DIVISAO_ESGOTO");
				}else{
					sessao.setAttribute("colecaoDivisaoEsgoto", colecaoDivisaoEsgoto);
				}
			}

			/*
			 * Local de Ocorr�ncia - Carregando a cole��o que ir� ficar
			 * dispon�vel para escolha do usu�rio
			 * [FS0003] � Verificar exist�ncia de dados
			 */
			Collection colecaoLocalOcorrencia = (Collection) sessao.getAttribute("colecaoLocalOcorrencia");

			if(colecaoLocalOcorrencia == null){

				FiltroLocalOcorrencia filtroLocalOcorrencia = new FiltroLocalOcorrencia(FiltroLocalOcorrencia.DESCRICAO);

				filtroLocalOcorrencia.adicionarParametro(new ParametroSimples(FiltroLocalOcorrencia.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroLocalOcorrencia.setConsultaSemLimites(true);

				colecaoLocalOcorrencia = fachada.pesquisar(filtroLocalOcorrencia, LocalOcorrencia.class.getName());

				if(colecaoLocalOcorrencia == null || colecaoLocalOcorrencia.isEmpty()){
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "LOCAL_OCORRENCIA");
				}else{
					sessao.setAttribute("colecaoLocalOcorrencia", colecaoLocalOcorrencia);
				}
			}

			/*
			 * Pavimento Rua - Carregando a cole��o que ir� ficar dispon�vel
			 * para escolha do usu�rio
			 * [FS0003] � Verificar exist�ncia de dados
			 */
			Collection colecaoPavimentoRua = (Collection) sessao.getAttribute("colecaoPavimentoRua");

			if(colecaoPavimentoRua == null){

				FiltroPavimentoRua filtroPavimentoRua = new FiltroPavimentoRua(FiltroPavimentoRua.DESCRICAO);

				filtroPavimentoRua.adicionarParametro(new ParametroSimples(FiltroPavimentoRua.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroPavimentoRua.setConsultaSemLimites(true);

				colecaoPavimentoRua = fachada.pesquisar(filtroPavimentoRua, PavimentoRua.class.getName());

				if(colecaoPavimentoRua == null || colecaoPavimentoRua.isEmpty()){
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "PAVIMENTO_RUA");
				}else{
					sessao.setAttribute("colecaoPavimentoRua", colecaoPavimentoRua);
				}
			}

			/*
			 * Pavimento Cal�ada - Carregando a cole��o que ir� ficar dispon�vel
			 * para escolha do usu�rio
			 * [FS0003] � Verificar exist�ncia de dados
			 */
			Collection colecaoPavimentoCalcada = (Collection) sessao.getAttribute("colecaoPavimentoCalcada");

			if(colecaoPavimentoCalcada == null){

				FiltroPavimentoCalcada filtroPavimentoCalcada = new FiltroPavimentoCalcada(FiltroPavimentoCalcada.DESCRICAO);

				filtroPavimentoCalcada.adicionarParametro(new ParametroSimples(FiltroPavimentoCalcada.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroPavimentoCalcada.setConsultaSemLimites(true);

				colecaoPavimentoCalcada = fachada.pesquisar(filtroPavimentoCalcada, PavimentoCalcada.class.getName());

				if(colecaoPavimentoCalcada == null || colecaoPavimentoCalcada.isEmpty()){
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "PAVIMENTO_CALCADA");
				}else{
					sessao.setAttribute("colecaoPavimentoCalcada", colecaoPavimentoCalcada);
				}
			}

			// [SB0002] � Habilita/Desabilita Munic�pio, Bairro, �rea do Bairro
			// e
			// Divis�o de Esgoto
			ObterDadosIdentificacaoLocalOcorrenciaHelper habilitaGeograficoDivisaoEsgoto = fachada.habilitarGeograficoDivisaoEsgoto(Integer
							.valueOf((atualizarRegistroAtendimentoActionForm.getTipoSolicitacao())));

			if(habilitaGeograficoDivisaoEsgoto != null){
				if(habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoFaltaAgua()){
					sessao.setAttribute("solicitacaoTipoRelativoFaltaAgua", "SIM");

					// Verificar carregamento do Munic�pio e Bairro de acordo com o tipo de
					// solicita��o
					if(atualizarRegistroAtendimentoActionForm.getIdImovel() != null
									&& !atualizarRegistroAtendimentoActionForm.getIdImovel().equalsIgnoreCase("")){

						ObterDadosIdentificacaoLocalOcorrenciaHelper dadosIdentificacaoLocalOcorrencia = fachada
										.obterDadosIdentificacaoLocalOcorrenciaAtualizar(
														Integer.valueOf(atualizarRegistroAtendimentoActionForm.getIdImovel()),
														Integer.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao()),
														Integer.valueOf(atualizarRegistroAtendimentoActionForm.getTipoSolicitacao()),
														Integer.valueOf(atualizarRegistroAtendimentoActionForm.getNumeroRA()), false,
														usuarioLogado);

						this.carregarMunicipioBairroParaImovel(habilitaGeograficoDivisaoEsgoto, dadosIdentificacaoLocalOcorrencia,
										atualizarRegistroAtendimentoActionForm, sessao, fachada);
					}
				}else{
					sessao.setAttribute("solicitacaoTipoRelativoFaltaAgua", "NAO");
				}

				if(habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto()){
					sessao.setAttribute("solicitacaoTipoRelativoAreaEsgoto", "SIM");
				}else{
					if(atualizarRegistroAtendimentoActionForm.getIdImovel() == null
									|| atualizarRegistroAtendimentoActionForm.getIdImovel().equals("")){
						sessao.setAttribute("solicitacaoTipoRelativoAreaEsgoto", "NAO");
					}else{
						sessao.setAttribute("solicitacaoTipoRelativoAreaEsgoto", "SIM");
					}
				}
			}else{
				sessao.setAttribute("solicitacaoTipoRelativoFaltaAgua", "SIM");
				sessao.setAttribute("solicitacaoTipoRelativoAreaEsgoto", "SIM");
			}
			/*
			 * Fim do carregamento inicial
			 * ======================================================================================
			 * ======================
			 * ======================================================================================
			 * ======================
			 */

			/*
			 * [FS0013] � Verificar compatibilidade entre divis�o de esgoto e
			 * localidade/setor/quadra [SB0007] � Define Unidade Destino da
			 * Divis�o de Esgoto
			 */
			String verificarCompatibilidade = httpServletRequest.getParameter("verificarCompatibilidade");

			if(verificarCompatibilidade != null && !verificarCompatibilidade.equalsIgnoreCase("")){

				this.verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(fachada, atualizarRegistroAtendimentoActionForm,
								habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto());

				httpServletRequest.setAttribute("nomeCampo", "idUnidadeDestino");
			}

			/*
			 * Removendo ColecaoBairroArea
			 */
			String removerColecaoBairroArea = httpServletRequest.getParameter("removerColecaoBairroArea");

			if(removerColecaoBairroArea != null && !removerColecaoBairroArea.equalsIgnoreCase("")){
				sessao.removeAttribute("colecaoBairroArea");
				httpServletRequest.setAttribute("nomeCampo", httpServletRequest.getParameter("campoFoco"));
			}

			/*
			 * Removendo endere�o
			 */
			String removerEndereco = httpServletRequest.getParameter("removerEndereco");

			if(removerEndereco != null && !removerEndereco.trim().equalsIgnoreCase("")){

				if(sessao.getAttribute("colecaoEnderecos") != null){

					Collection enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

					if(!enderecos.isEmpty()){
						enderecos.remove(enderecos.iterator().next());
					}
				}
			}

			/*
			 * Pesquisas realizadas a partir do ENTER
			 * ======================================================================================
			 * =====================
			 */

			/*
			 * Dados da identifica��o do local de ocorr�ncia
			 * [FS0019] � Verificar endere�o do im�vel
			 */
			// caso esses parametros forem nulos ent�o verifica os enter
			if(removerColecaoBairroArea == null && verificarCompatibilidade == null && removerEndereco == null){

				String idImovel = atualizarRegistroAtendimentoActionForm.getIdImovel();
				String inscricaoImovel = atualizarRegistroAtendimentoActionForm.getInscricaoImovel();

				int indValidacaoMatriculaImovel = 0;
				if(atualizarRegistroAtendimentoActionForm.getIndValidacaoMatriculaImovel() == null
								|| atualizarRegistroAtendimentoActionForm.getIndValidacaoMatriculaImovel().equals("")){
					// [SB0024] - Verifica registro de Atendimento Sem
					// Identifica��o
					// do
					// Local da Ocorr�ncia
					if(idImovel != null && !idImovel.equals("")){
						indValidacaoMatriculaImovel = fachada.verificarRASemIdentificacaoLO(Integer.valueOf(idImovel),
										Integer.valueOf(atualizarRegistroAtendimentoActionForm.getNumeroRA()));

						atualizarRegistroAtendimentoActionForm.setIndValidacaoMatriculaImovel("" + indValidacaoMatriculaImovel);
						if(indValidacaoMatriculaImovel != 1){

							ObterDadosIdentificacaoLocalOcorrenciaHelper dadosIdentificacaoLocalOcorrencia = fachada
											.obterDadosIdentificacaoLocalOcorrenciaAtualizar(
															Integer.valueOf(atualizarRegistroAtendimentoActionForm.getIdImovel()),
															Integer.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao()),
															Integer.valueOf(atualizarRegistroAtendimentoActionForm.getTipoSolicitacao()),
															Integer.valueOf(atualizarRegistroAtendimentoActionForm.getNumeroRA()), false,
															usuarioLogado);

							if(dadosIdentificacaoLocalOcorrencia.isHabilitarAlteracaoEndereco()){
								sessao.setAttribute("habilitarAlteracaoEndereco", "SIM");
							}else{
								sessao.setAttribute("habilitarAlteracaoEndereco", "NAO");
							}
							if(dadosIdentificacaoLocalOcorrencia.getEnderecoDescritivo() != null
											&& !dadosIdentificacaoLocalOcorrencia.getEnderecoDescritivo().equals("")){
								sessao.setAttribute("desabilitarMunicipioBairro", "OK");
							}

						}
					}
				}else{
					indValidacaoMatriculaImovel = Integer.parseInt(atualizarRegistroAtendimentoActionForm.getIndValidacaoMatriculaImovel());
				}
				// caso seja a pesquisa do enter do im�vel ou o indicador de
				// valida��o de matr�cula do im�vel seja 1
				if((idImovel != null && !idImovel.equalsIgnoreCase("") && (inscricaoImovel == null || inscricaoImovel.equals("")))
								|| indValidacaoMatriculaImovel == 1){

					/*
					 * [SB0004] � Obt�m e Habilita/Desabilita Dados da
					 * Identifica��o do Local da Ocorr�ncia e Dados do
					 * Solicitante
					 * [FS0019] � Verificar endere�o do im�vel [FS0020] -
					 * Verificar exist�ncia de registro de atendimento para o
					 * im�vel com a mesma especifica��o
					 * [FS0053] - Verificar exist�ncia de servi�o em andamento para solicita��es do
					 * tipo
					 * religa��o ou restabelecimento
					 * [SB0020] � Verifica Situa��o do Im�vel e Especifica��o
					 * [SB0032] � Verifica se o im�vel informado tem d�bito
					 */
					ObterDadosIdentificacaoLocalOcorrenciaHelper dadosIdentificacaoLocalOcorrencia = fachada
									.obterDadosIdentificacaoLocalOcorrenciaAtualizar(
													Integer.valueOf(atualizarRegistroAtendimentoActionForm.getIdImovel()),
													Integer.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao()),
													Integer.valueOf(atualizarRegistroAtendimentoActionForm.getTipoSolicitacao()),
													Integer.valueOf(atualizarRegistroAtendimentoActionForm.getNumeroRA()), false,
													usuarioLogado);

					if(dadosIdentificacaoLocalOcorrencia.getImovel() != null){

						boolean msgAlert = false;

						// [SB0021] � Verifica Exist�ncia de Registro de
						// Atendimento
						// Pendente para o Im�vel
						boolean raPendente = fachada.verificaExistenciaRAPendenteImovel(dadosIdentificacaoLocalOcorrencia.getImovel()
										.getId());

						if(raPendente){
							httpServletRequest.setAttribute("msgAlert", "Aten��o! "
											+ "Existe RA - Registro de Atendimento pendente para o im�vel "
											+ dadosIdentificacaoLocalOcorrencia.getImovel().getId().toString());
							msgAlert = true;
						}

						// [SB0020] � Verifica Situa��o do Im�vel e
						// Especifica��o
						fachada.verificarSituacaoImovelEspecificacao(dadosIdentificacaoLocalOcorrencia.getImovel(),
										Integer.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao()));

						// [SB0032] � Verifica se o im�vel informado tem d�bito
						fachada.verificarImovelComDebitos(Integer.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao()),
										dadosIdentificacaoLocalOcorrencia.getImovel().getId());

						atualizarRegistroAtendimentoActionForm
										.setIdImovel(dadosIdentificacaoLocalOcorrencia.getImovel().getId().toString());

						atualizarRegistroAtendimentoActionForm.setInscricaoImovel(dadosIdentificacaoLocalOcorrencia.getImovel()
										.getInscricaoFormatada());

						if(!dadosIdentificacaoLocalOcorrencia.isInformarEndereco()){
							Collection colecaoEnderecos = new ArrayList();
							colecaoEnderecos.add(dadosIdentificacaoLocalOcorrencia.getImovel());
							sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
							sessao.setAttribute("enderecoPertenceImovel", "OK");
						}else if(dadosIdentificacaoLocalOcorrencia.getEnderecoDescritivo() != null){
							atualizarRegistroAtendimentoActionForm.setDescricaoLocalOcorrencia(dadosIdentificacaoLocalOcorrencia
											.getEnderecoDescritivo());

							if(msgAlert){
								httpServletRequest.setAttribute("msgAlert2", "O RA - Registro de Atendimento ficar� bloqueado at� "
												+ " que seja informado o logradouro para o im�vel");
							}else{
								httpServletRequest.setAttribute("msgAlert", "O RA - Registro de Atendimento ficar� bloqueado at� "
												+ " que seja informado o logradouro para o im�vel");
							}

							sessao.removeAttribute("colecaoEnderecos");
							sessao.removeAttribute("enderecoPertenceImovel");
						}else{
							sessao.removeAttribute("colecaoEnderecos");
							sessao.removeAttribute("enderecoPertenceImovel");
						}

						if(dadosIdentificacaoLocalOcorrencia.isHabilitarAlteracaoEndereco()){
							sessao.setAttribute("habilitarAlteracaoEndereco", "SIM");
						}else{
							sessao.setAttribute("habilitarAlteracaoEndereco", "NAO");
						}

						this.carregarMunicipioBairroParaImovel(habilitaGeograficoDivisaoEsgoto, dadosIdentificacaoLocalOcorrencia,
										atualizarRegistroAtendimentoActionForm, sessao, fachada);

						atualizarRegistroAtendimentoActionForm.setIdLocalidade(dadosIdentificacaoLocalOcorrencia.getImovel()
										.getLocalidade().getId().toString());

						atualizarRegistroAtendimentoActionForm.setDescricaoLocalidade(dadosIdentificacaoLocalOcorrencia.getImovel()
										.getLocalidade().getDescricao());

						atualizarRegistroAtendimentoActionForm.setIdSetorComercial(dadosIdentificacaoLocalOcorrencia.getImovel()
										.getSetorComercial().getId().toString());

						atualizarRegistroAtendimentoActionForm.setCdSetorComercial(String.valueOf(dadosIdentificacaoLocalOcorrencia
										.getImovel().getSetorComercial().getCodigo()));

						atualizarRegistroAtendimentoActionForm.setDescricaoSetorComercial(dadosIdentificacaoLocalOcorrencia.getImovel()
										.getSetorComercial().getDescricao());

						atualizarRegistroAtendimentoActionForm.setIdQuadra(String.valueOf(dadosIdentificacaoLocalOcorrencia.getImovel()
										.getQuadra().getId()));

						atualizarRegistroAtendimentoActionForm.setNnQuadra(String.valueOf(dadosIdentificacaoLocalOcorrencia.getImovel()
										.getQuadra().getNumeroQuadra()));

						sessao.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");

					}else{

						atualizarRegistroAtendimentoActionForm.setIdImovel("");
						atualizarRegistroAtendimentoActionForm.setInscricaoImovel("Im�vel Inexistente");

						httpServletRequest.setAttribute("corImovel", "exception");
						httpServletRequest.setAttribute("nomeCampo", "idImovel");
					}
				}else if(!Util.isVazioOuBranco(idImovel)){
					configurarColocarContaRevisao(httpServletRequest, sessao, fachada, atualizarRegistroAtendimentoActionForm);
				}

				String idMunicipio = atualizarRegistroAtendimentoActionForm.getIdMunicipio();
				String descricaoMunicipio = atualizarRegistroAtendimentoActionForm.getDescricaoMunicipio();

				if(idMunicipio != null && !idMunicipio.equalsIgnoreCase("")
								&& (descricaoMunicipio == null || descricaoMunicipio.equals(""))){

					FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

					filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, atualizarRegistroAtendimentoActionForm
									.getIdMunicipio()));

					filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

					if(colecaoMunicipio == null || colecaoMunicipio.isEmpty()){

						atualizarRegistroAtendimentoActionForm.setIdMunicipio("");
						atualizarRegistroAtendimentoActionForm.setDescricaoMunicipio("Munic�pio Inexistente");

						httpServletRequest.setAttribute("corMunicipio", "exception");
						httpServletRequest.setAttribute("nomeCampo", "idMunicipio");

					}else{
						Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);

						atualizarRegistroAtendimentoActionForm.setIdMunicipio(municipio.getId().toString());
						atualizarRegistroAtendimentoActionForm.setDescricaoMunicipio(municipio.getNome());

						httpServletRequest.setAttribute("nomeCampo", "cdBairro");
					}
				}

				String codigoBairro = atualizarRegistroAtendimentoActionForm.getCdBairro();
				String descricaoBairro = atualizarRegistroAtendimentoActionForm.getDescricaoBairro();

				if(codigoBairro != null && !codigoBairro.equalsIgnoreCase("")){

					if((descricaoBairro == null || descricaoBairro.equals(""))){

						FiltroBairro filtroBairro = new FiltroBairro();

						filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, atualizarRegistroAtendimentoActionForm
										.getCdBairro()));

						filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID,
										atualizarRegistroAtendimentoActionForm.getIdMunicipio()));

						filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

						if(colecaoBairro == null || colecaoBairro.isEmpty()){

							atualizarRegistroAtendimentoActionForm.setCdBairro("");
							atualizarRegistroAtendimentoActionForm.setDescricaoBairro("Bairro Inexistente");

							httpServletRequest.setAttribute("corBairro", "exception");
							httpServletRequest.setAttribute("nomeCampo", "cdBairro");

						}else{
							Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

							atualizarRegistroAtendimentoActionForm.setCdBairro(String.valueOf(bairro.getCodigo()));
							atualizarRegistroAtendimentoActionForm.setCdBairro(String.valueOf(bairro.getId()));
							atualizarRegistroAtendimentoActionForm.setDescricaoBairro(bairro.getNome());
							this.pesquisarBairroArea(bairro.getId(), fachada, sessao);
							httpServletRequest.setAttribute("nomeCampo", "idBairroArea");

						}
					}
				}

				String idLocalidade = atualizarRegistroAtendimentoActionForm.getIdLocalidade();
				String descricaoLocalidade = atualizarRegistroAtendimentoActionForm.getDescricaoBairro();

				if(idLocalidade != null && !idLocalidade.equalsIgnoreCase("")
								&& (descricaoLocalidade == null || descricaoLocalidade.equals(""))){
					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, atualizarRegistroAtendimentoActionForm
									.getIdLocalidade()));

					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

					if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){

						atualizarRegistroAtendimentoActionForm.setIdLocalidade("");
						atualizarRegistroAtendimentoActionForm.setDescricaoLocalidade("Localidade Inexistente");

						httpServletRequest.setAttribute("corLocalidade", "exception");
						httpServletRequest.setAttribute("nomeCampo", "idLocalidade");

					}else{
						Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

						atualizarRegistroAtendimentoActionForm.setIdLocalidade(localidade.getId().toString());
						atualizarRegistroAtendimentoActionForm.setDescricaoLocalidade(localidade.getDescricao());

						httpServletRequest.setAttribute("nomeCampo", "cdSetorComercial");

					}
				}

				String cdSetorComercial = atualizarRegistroAtendimentoActionForm.getCdSetorComercial();
				String descricaoSetorComercial = atualizarRegistroAtendimentoActionForm.getDescricaoSetorComercial();

				if(cdSetorComercial != null && !cdSetorComercial.equalsIgnoreCase("")
								&& (descricaoSetorComercial == null || descricaoSetorComercial.equals(""))){

					FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE,
									atualizarRegistroAtendimentoActionForm.getIdLocalidade()));

					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
									atualizarRegistroAtendimentoActionForm.getCdSetorComercial()));

					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

					if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){

						atualizarRegistroAtendimentoActionForm.setIdSetorComercial("");
						atualizarRegistroAtendimentoActionForm.setCdSetorComercial("");
						atualizarRegistroAtendimentoActionForm.setDescricaoSetorComercial("Setor Comercial Inexistente");

						httpServletRequest.setAttribute("corSetorComercial", "exception");
						httpServletRequest.setAttribute("nomeCampo", "cdSetorComercial");

					}else{
						SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

						atualizarRegistroAtendimentoActionForm.setIdSetorComercial(setorComercial.getId().toString());
						atualizarRegistroAtendimentoActionForm.setCdSetorComercial(String.valueOf(setorComercial.getCodigo()));
						atualizarRegistroAtendimentoActionForm.setDescricaoSetorComercial(setorComercial.getDescricao());

						httpServletRequest.setAttribute("nomeCampo", "nnQuadra");

					}
				}

				String nnQuadra = atualizarRegistroAtendimentoActionForm.getNnQuadra();
				String pesquisarQuadra = httpServletRequest.getParameter("pesquisarQuadra");

				if(nnQuadra != null && !nnQuadra.equalsIgnoreCase("") && (pesquisarQuadra != null && pesquisarQuadra.equals(""))){

					FiltroQuadra filtroQuadra = new FiltroQuadra();

					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL,
									atualizarRegistroAtendimentoActionForm.getIdSetorComercial()));

					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, atualizarRegistroAtendimentoActionForm
									.getNnQuadra()));

					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

					if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

						atualizarRegistroAtendimentoActionForm.setIdQuadra("");
						atualizarRegistroAtendimentoActionForm.setNnQuadra("");

						httpServletRequest.setAttribute("msgQuadra", "QUADRA INEXISTENTE");
						httpServletRequest.setAttribute("nomeCampo", "nnQuadra");

					}else{
						Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

						atualizarRegistroAtendimentoActionForm.setIdQuadra(quadra.getId().toString());
						atualizarRegistroAtendimentoActionForm.setNnQuadra(String.valueOf(quadra.getNumeroQuadra()));

						// [SB0006] � Obt�m Divis�o de Esgoto
						DivisaoEsgoto divisaoEsgoto = fachada.obterDivisaoEsgoto(quadra.getId(),
										habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto());

						if(divisaoEsgoto != null){
							atualizarRegistroAtendimentoActionForm.setIdDivisaoEsgoto(divisaoEsgoto.getId().toString());

							/*
							 * [FS0013] � Verificar compatibilidade entre
							 * divis�o de esgoto e localidade/setor/quadra
							 * [SB0007] � Define Unidade Destino da Divis�o de
							 * Esgoto
							 */
							this.verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(fachada,
											atualizarRegistroAtendimentoActionForm,
											habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto());

						}else{
							httpServletRequest.setAttribute("nomeCampo", "idDivisaoEsgoto");
						}

					}
				}

			}

			String limparContaRevisao = httpServletRequest.getParameter("limparContaRevisao");

			if(!Util.isVazioOuBranco(limparContaRevisao) && limparContaRevisao.equalsIgnoreCase("OK")){
				sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
				httpServletRequest.setAttribute("abrirPopUpEnviarContaRevisao", "N");
			}

			/*
			 * Fim das pesquisas realizadas pelo ENTER
			 * ======================================================================================
			 * =====================
			 * ======================================================================================
			 * =====================
			 */

			/*
			 * Limpar Im�vel
			 */
			String limparImovel = httpServletRequest.getParameter("limparImovel");

			if(limparImovel != null && !limparImovel.trim().equalsIgnoreCase("")){

				this.limparImovel(atualizarRegistroAtendimentoActionForm, sessao);
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
			}
		}

		return retorno;

	} /*
	 * M�todos auxiliares
	 * ==============================================================================================
	 * ===============
	 */

	public void verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(Fachada fachada,
					AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm, boolean solicitacaoTipoRelativoAreaEsgoto){

		fachada.verificarCompatibilidadeDivisaoEsgotoLocalidadeSetorQuadra(
						Util.converterStringParaInteger(atualizarRegistroAtendimentoActionForm.getIdLocalidade()),
						Util.converterStringParaInteger(atualizarRegistroAtendimentoActionForm.getIdSetorComercial()),
						Util.converterStringParaInteger(atualizarRegistroAtendimentoActionForm.getIdQuadra()),
						Util.converterStringParaInteger(atualizarRegistroAtendimentoActionForm.getIdDivisaoEsgoto()));

	}

	public void pesquisarBairroArea(Integer idBairro, Fachada fachada, HttpSession sessao){

		FiltroBairroArea filtroBairroArea = new FiltroBairroArea();

		filtroBairroArea.adicionarParametro(new ParametroSimples(FiltroBairroArea.ID_BAIRRO, idBairro));

		Collection colecaoBairroArea = fachada.pesquisar(filtroBairroArea, BairroArea.class.getName());

		if(colecaoBairroArea == null || colecaoBairroArea.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "BAIRRO_AREA");
		}else{
			sessao.setAttribute("colecaoBairroArea", colecaoBairroArea);
		}
	}

	public void carregarMunicipioBairroParaImovel(ObterDadosIdentificacaoLocalOcorrenciaHelper habilitaGeograficoDivisaoEsgoto,
					ObterDadosIdentificacaoLocalOcorrenciaHelper obterDadosIdentificacaoLocalOcorrenciaHelper,
					AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm, HttpSession sessao, Fachada fachada){

		if(habilitaGeograficoDivisaoEsgoto != null && habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoFaltaAgua()
						&& obterDadosIdentificacaoLocalOcorrenciaHelper.getEnderecoDescritivo() == null){

			atualizarRegistroAtendimentoActionForm.setIdMunicipio(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getMunicipio().getId().toString());

			atualizarRegistroAtendimentoActionForm.setDescricaoMunicipio(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getMunicipio().getNome());

			atualizarRegistroAtendimentoActionForm.setIdBairro(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getId().toString());

			atualizarRegistroAtendimentoActionForm.setCdBairro(String.valueOf(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getCodigo()));

			atualizarRegistroAtendimentoActionForm.setDescricaoBairro(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getNome());

			this.pesquisarBairroArea(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro().getBairro().getId(),
							fachada, sessao);

			sessao.setAttribute("desabilitarMunicipioBairro", "OK");

		}else{

			atualizarRegistroAtendimentoActionForm.setIdMunicipio("");

			atualizarRegistroAtendimentoActionForm.setDescricaoMunicipio("");

			atualizarRegistroAtendimentoActionForm.setIdBairro("");

			atualizarRegistroAtendimentoActionForm.setCdBairro("");

			atualizarRegistroAtendimentoActionForm.setDescricaoBairro("");

			sessao.removeAttribute("colecaoBairroArea");

			sessao.removeAttribute("desabilitarMunicipioBairro");
		}
	}

	private void limparImovel(AtualizarRegistroAtendimentoActionForm form, HttpSession sessao){

		form.setIdImovel("");
		form.setInscricaoImovel("");
		form.setDescricaoLocalOcorrencia("");
		form.setIdMunicipio("");
		form.setDescricaoMunicipio("");
		form.setIdBairro("");
		form.setCdBairro("");
		form.setDescricaoBairro("");
		form.setIdLocalidade("");
		form.setDescricaoLocalidade("");
		form.setIdSetorComercial("");
		form.setCdSetorComercial("");
		form.setDescricaoSetorComercial("");
		form.setIdQuadra("");
		form.setNnQuadra("");
		form.setIdPavimentoRua("");
		form.setIdPavimentoCalcada("");
		form.setPontoReferencia("");

		sessao.removeAttribute("colecaoEnderecos");
		sessao.removeAttribute("enderecoPertenceImovel");
		sessao.removeAttribute("habilitarAlteracaoEndereco");
		sessao.removeAttribute("colecaoBairroArea");
	}

	// =================================================================================================================

	/**
	 * @author isilva
	 * @param httpServletRequest
	 * @param fachada
	 * @param atualizarRegistroAtendimentoActionForm
	 */
	private void configurarColocarContaRevisao(HttpServletRequest httpServletRequest, HttpSession sessao, Fachada fachada,
					AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){

			if(!Util.isVazioOuBranco(atualizarRegistroAtendimentoActionForm.getIdImovel())){

				// SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm =
				// (SelecionarContaRevisaoActionForm)
				// sessao.getAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
				//
				// if (!Util.isVazioOuBranco(selecionarContaRevisaoActionForm)) {
				// if (!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getCodigoImovel())) {
				// if
				// (!inserirRegistroAtendimentoActionForm.getIdImovel().equalsIgnoreCase(selecionarContaRevisaoActionForm.getCodigoImovel()))
				// {
				// sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
				// }
				// }
				// } else {
				// sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
				// }

				// [SB0033] � Verifica se coloca Contas em Revis�o.
				FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, Integer
								.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao())));
				filtroSolicitacaoTipoEspecificacao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);
				filtroSolicitacaoTipoEspecificacao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

				SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util
								.retonarObjetoDeColecao(fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
												SolicitacaoTipoEspecificacao.class.getName()));

				if(solicitacaoTipoEspecificacao == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Especifica��o");
				}

				// httpServletRequest.setAttribute("especificacao",
				// String.valueOf(solicitacaoTipoEspecificacao.getId()));

				// 1. Caso a especifica��o informada para o RA tenha indicativo que � para colocar
				// contas em revis�o (STEP_ICCOLOCACONTASEMREVISAO da tabela
				// SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1))
				if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao())
								&& solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao().intValue() == ConstantesSistema.SIM.intValue()){

					// 1.1. Caso o im�vel informado tenha conta(s) [FS0047 � Verificar exist�ncia de
					// alguma conta].

					Collection<Conta> contas = pesquisarContasImovel(sessao, fachada, atualizarRegistroAtendimentoActionForm.getIdImovel());

					if(contas != null && !contas.isEmpty()){

						// 1.1.1. Caso a especifica��o informada para o RA tenha indicativo que �
						// para
						// exibir mensagem de alerta (STEP_ICMENSAGEMALERTA da tabela
						// SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1)
						if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorMensagemAlertaRevisao())
										&& solicitacaoTipoEspecificacao.getIndicadorMensagemAlertaRevisao().intValue() == ConstantesSistema.SIM
														.intValue()){

							// 1.1.1.1. O sistema exibe a mensagem �Aten��o, existem faturas
							// pendentes para o im�vel <<IMOV_ID da tabela REGISTRO_ATENDIMENTO>>.
							// Deseja colocar as contas em revis�o?�
							httpServletRequest.setAttribute("msgEnviarContaRevisao", "Aten��o, existem faturas pendentes para o im�vel "
											+ atualizarRegistroAtendimentoActionForm.getIdImovel()
											+ ". Deseja colocar as contas em revis�o?");

							httpServletRequest.setAttribute("abrirPopUpEnviarContaRevisao", SelecionarContaRevisaoActionForm.COM_MENSAGEM);
							// 1.1.1.1.1. Caso Sim, [SB0034 � Listar Contas]

						}else{
							// 1.1.2. Caso N�o, [SB0034 � Listar Contas]
							httpServletRequest.setAttribute("abrirPopUpEnviarContaRevisao", SelecionarContaRevisaoActionForm.SEM_MENSAGEM);
						}
					}
				}
			}
		}
	}

	/**
	 * @author isilva
	 * @param sessao
	 * @param fachada
	 * @param codigoImovel
	 */
	private Collection<Conta> pesquisarContasImovel(HttpSession sessao, Fachada fachada, String codigoImovel){

		// Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		Imovel imovel = fachada.pesquisarImovelRegistroAtendimento(Integer.valueOf(codigoImovel));
		Collection<Conta> colecaoContaImovelRetornoConsulta = fachada.obterContasImovelManter(imovel);
		Collection<Conta> colecaoContaImovel = new ArrayList<Conta>();

		if(colecaoContaImovelRetornoConsulta != null && !colecaoContaImovelRetornoConsulta.isEmpty()){
			for(Conta conta : colecaoContaImovelRetornoConsulta){
				if(conta.getDataRevisao() == null){
					colecaoContaImovel.add(conta);
				}
			}
		}

		// // ------------ CONTROLE DE ABRANGENCIA ----------------
		// Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovel);
		//
		// if (!fachada.verificarAcessoAbrangencia(abrangencia)) {
		// throw new ActionServletException("atencao.acesso.negado.abrangencia");
		// }
		// // ------------ FIM CONTROLE DE ABRANGENCIA ------------

		// Coloca na sess�o a cole��o com as contas do im�vel selecionado
		return colecaoContaImovel;
	}

	/**
	 * @author isilva
	 * @param httpServletRequest
	 * @param sessao
	 * @param atualizarRegistroAtendimentoActionForm
	 */
	private void controlarFormularioSelecionarContaRevisao(HttpServletRequest httpServletRequest, HttpSession sessao,
					AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){

			SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm = (SelecionarContaRevisaoActionForm) sessao
							.getAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);

			boolean removeFormulario = false;

			if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm)){
				if(Util.isVazioOuBranco(atualizarRegistroAtendimentoActionForm.getIdImovel())){
					removeFormulario = true;
				}else{
					if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getCodigoImovel())){
						if(!atualizarRegistroAtendimentoActionForm.getIdImovel().equalsIgnoreCase(
										selecionarContaRevisaoActionForm.getCodigoImovel())){
							removeFormulario = true;
						}
					}else{
						removeFormulario = true;
					}
				}

				if(Util.isVazioOuBranco(atualizarRegistroAtendimentoActionForm.getEspecificacao())){
					removeFormulario = true;
				}else{
					if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getEspecificacao())){
						if(!atualizarRegistroAtendimentoActionForm.getEspecificacao().equalsIgnoreCase(
										selecionarContaRevisaoActionForm.getEspecificacao())){
							removeFormulario = true;
						}
					}else{
						removeFormulario = true;
					}
				}
			}

			if(removeFormulario){
				sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
			}

		}
	}

}
