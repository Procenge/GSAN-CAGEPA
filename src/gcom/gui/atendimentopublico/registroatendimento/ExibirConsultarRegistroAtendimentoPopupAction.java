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

import gcom.atendimentopublico.registroatendimento.*;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterRAAssociadoHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.BairroArea;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.PavimentoCalcada;
import gcom.cadastro.imovel.PavimentoRua;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DivisaoEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pr�-processamento da p�gina de exibir consultar RA Popup
 * 
 * @author Rafael Pinto
 * @created 11/08/2006
 */
public class ExibirConsultarRegistroAtendimentoPopupAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarRegistroAtendimentoPopup");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarRegistroAtendimentoPopupActionForm consultarRegistroAtendimentoPopupActionForm = (ConsultarRegistroAtendimentoPopupActionForm) actionForm;

		ObterDadosRegistroAtendimentoHelper obterDadosRegistroAtendimentoHelper = fachada.obterDadosRegistroAtendimento(Integer
						.valueOf(consultarRegistroAtendimentoPopupActionForm.getNumeroRA()));

		if(obterDadosRegistroAtendimentoHelper == null || obterDadosRegistroAtendimentoHelper.getRegistroAtendimento() == null){
			throw new ActionServletException("atencao.naocadastrado", null, "Registro Atendimento");
		}

		RegistroAtendimento registroAtendimento = obterDadosRegistroAtendimentoHelper.getRegistroAtendimento();

		// Dados Gerais do Registro de Atendimento
		consultarRegistroAtendimentoPopupActionForm.setNumeroRA("" + registroAtendimento.getId());

		if(registroAtendimento.getReiteracao() != null){
			consultarRegistroAtendimentoPopupActionForm.setRaReiterado(String.valueOf(registroAtendimento.getReiteracao()));
		}else{
			consultarRegistroAtendimentoPopupActionForm.setRaReiterado("");
		}
		if(registroAtendimento.getManual() != null){
			int tamanhoNumeracao = registroAtendimento.getManual().toString().length();
			String numeracao = registroAtendimento.getManual().toString().substring(0, tamanhoNumeracao - 1);
			consultarRegistroAtendimentoPopupActionForm.setNumeroRAManual(Util.formatarNumeracaoRAManual(Integer.valueOf(numeracao)));
		}else{
			consultarRegistroAtendimentoPopupActionForm.setNumeroRAManual("");
		}

		consultarRegistroAtendimentoPopupActionForm.setCodigoSituacao("" + registroAtendimento.getCodigoSituacao());

		// Caso de Uso [UC0420]
		ObterDescricaoSituacaoRAHelper situacaoRA = fachada.obterDescricaoSituacaoRA(registroAtendimento.getId());

		consultarRegistroAtendimentoPopupActionForm.setSituacaoRA(situacaoRA.getDescricaoSituacao());

		// Caso de Uso [UC0433]
		ObterRAAssociadoHelper obterRAAssociadoHelper = fachada.obterRAAssociado(registroAtendimento.getId());

		if(obterRAAssociadoHelper != null && obterRAAssociadoHelper.getRegistroAtendimentoAssociado() != null){
			consultarRegistroAtendimentoPopupActionForm.setNumeroRaAssociado(""
							+ obterRAAssociadoHelper.getRegistroAtendimentoAssociado().getId());

			ObterDescricaoSituacaoRAHelper situacaoRAssociado = fachada.obterDescricaoSituacaoRA(obterRAAssociadoHelper
							.getRegistroAtendimentoAssociado().getId());

			consultarRegistroAtendimentoPopupActionForm.setSituacaoRaAssociado(situacaoRAssociado.getDescricaoSituacao());

			httpServletRequest.setAttribute("existeRaAssociado", true);
		}

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = registroAtendimento.getSolicitacaoTipoEspecificacao();

		if(solicitacaoTipoEspecificacao != null){

			if(solicitacaoTipoEspecificacao.getSolicitacaoTipo() != null){
				consultarRegistroAtendimentoPopupActionForm.setIdTipoSolicitacao(""
								+ solicitacaoTipoEspecificacao.getSolicitacaoTipo().getId());
				consultarRegistroAtendimentoPopupActionForm.setTipoSolicitacao(solicitacaoTipoEspecificacao.getSolicitacaoTipo()
								.getDescricao());
			}

			consultarRegistroAtendimentoPopupActionForm.setIdEspecificacao("" + solicitacaoTipoEspecificacao.getId());
			consultarRegistroAtendimentoPopupActionForm.setEspecificacao(solicitacaoTipoEspecificacao.getDescricao());
		}

		consultarRegistroAtendimentoPopupActionForm.setTipoAtendimento("" + registroAtendimento.getIndicadorAtendimentoOnline());

		Date dataAtendimento = registroAtendimento.getRegistroAtendimento();

		consultarRegistroAtendimentoPopupActionForm.setDataAtendimento(Util.formatarData(dataAtendimento));
		consultarRegistroAtendimentoPopupActionForm.setHoraAtendimento(Util.formatarHoraSemSegundos(dataAtendimento));

		consultarRegistroAtendimentoPopupActionForm.setTempoEsperaInicio(Util.formatarHoraSemSegundos(registroAtendimento
						.getDataInicioEspera()));
		consultarRegistroAtendimentoPopupActionForm.setTempoEsperaTermino(Util.formatarHoraSemSegundos(registroAtendimento
						.getDataFimEspera()));

		consultarRegistroAtendimentoPopupActionForm.setDataPrevista(Util.formatarData(registroAtendimento.getDataPrevistaAtual()) + " "
						+ Util.formatarHoraSemSegundos(registroAtendimento.getDataPrevistaAtual()));

		if(registroAtendimento.getMeioSolicitacao() != null){
			consultarRegistroAtendimentoPopupActionForm.setIdMeioSolicitacao("" + registroAtendimento.getMeioSolicitacao().getId());
			consultarRegistroAtendimentoPopupActionForm.setMeioSolicitacao(registroAtendimento.getMeioSolicitacao().getDescricao());
		}

		// Caso de Uso [UC0421]
		UnidadeOrganizacional unidadeAtendimento = fachada.obterUnidadeAtendimentoRA(registroAtendimento.getId());

		if(unidadeAtendimento != null){
			consultarRegistroAtendimentoPopupActionForm.setIdUnidadeAtendimento("" + unidadeAtendimento.getId());
			consultarRegistroAtendimentoPopupActionForm.setUnidadeAtendimento(unidadeAtendimento.getDescricao());
		}
		RegistroAtendimentoUnidade registroAtendimentoUnidade = this.consultarRegistroAtendimentoUnidade(registroAtendimento.getId(),
						unidadeAtendimento.getId());

		Usuario usuarioAtendimento = registroAtendimentoUnidade.getUsuario();

		if(usuarioAtendimento != null){

			consultarRegistroAtendimentoPopupActionForm.setIdUsuario(String.valueOf(usuarioAtendimento.getId()));
			consultarRegistroAtendimentoPopupActionForm.setUsuario(usuarioAtendimento.getNomeUsuario());
		}

		// Caso de Uso [UC0418]
		UnidadeOrganizacional unidadeAtual = fachada.obterUnidadeAtualRA(registroAtendimento.getId());

		if(unidadeAtual != null){
			consultarRegistroAtendimentoPopupActionForm.setIdUnidadeAtual("" + unidadeAtual.getId());
			consultarRegistroAtendimentoPopupActionForm.setUnidadeAtual(unidadeAtual.getDescricao());
		}

		consultarRegistroAtendimentoPopupActionForm.setObservacao(registroAtendimento.getObservacao());

		// Dados do Local da Ocorrencia
		Imovel imovel = registroAtendimento.getImovel();
		if(imovel != null){
			Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

			Integer idImovel = imovel.getId();
			Integer idRegistroAtendimento = registroAtendimento.getId();

			// [SB0013] - Validar Manuten��o de RA pelos Usu�rios das Empresas de Cobran�a
			// Administrativa
			if(fachada.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuarioLogado, idImovel,
							ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA).equals(ConstantesSistema.NAO)){
				throw new ActionServletException("atencao.usuario_sem_acesso_manutencao_ra_imovel_cobranca_administrativa",
								usuarioLogado.getLogin(), Integer.toString(idRegistroAtendimento), Integer.toString(idImovel));
			}

			consultarRegistroAtendimentoPopupActionForm.setMatriculaImovel(String.valueOf(idImovel));
			consultarRegistroAtendimentoPopupActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());
			consultarRegistroAtendimentoPopupActionForm.setRota(obterDadosRegistroAtendimentoHelper.getCodigoRota().toString());

			if(obterDadosRegistroAtendimentoHelper.getSequencialRota() != null){
				consultarRegistroAtendimentoPopupActionForm.setSequencialRota(obterDadosRegistroAtendimentoHelper.getSequencialRota()
								.toString());
			}
		}

		// Caso de Uso [UC0422]
		String enderecoOcorrencia = fachada.obterEnderecoOcorrenciaRA(registroAtendimento.getId());

		consultarRegistroAtendimentoPopupActionForm.setEnderecoOcorrencia(enderecoOcorrencia);
		consultarRegistroAtendimentoPopupActionForm.setPontoReferencia(registroAtendimento.getPontoReferencia());

		// Caso o registro atendimento esteja associado a uma �rea de bairro, obter os dados da �rea
		// do bairro
		BairroArea bairroArea = registroAtendimento.getBairroArea();

		if(bairroArea != null){

			consultarRegistroAtendimentoPopupActionForm.setIdMunicipio("" + bairroArea.getBairro().getMunicipio().getId());
			consultarRegistroAtendimentoPopupActionForm.setMunicipio(bairroArea.getBairro().getMunicipio().getNome());

			consultarRegistroAtendimentoPopupActionForm.setIdBairro("" + bairroArea.getBairro().getId());
			consultarRegistroAtendimentoPopupActionForm.setBairro(bairroArea.getBairro().getNome());

			consultarRegistroAtendimentoPopupActionForm.setIdAreaBairro("" + bairroArea.getId());
			consultarRegistroAtendimentoPopupActionForm.setAreaBairro(bairroArea.getNome());
		}

		Localidade localidade = registroAtendimento.getLocalidade();
		if(localidade != null){
			consultarRegistroAtendimentoPopupActionForm.setIdLocalidade("" + localidade.getId());
			consultarRegistroAtendimentoPopupActionForm.setLocalidade(localidade.getDescricao());
		}

		SetorComercial setorComercial = registroAtendimento.getSetorComercial();
		if(setorComercial != null){
			consultarRegistroAtendimentoPopupActionForm.setIdSetorComercial("" + setorComercial.getCodigo());
			consultarRegistroAtendimentoPopupActionForm.setSetorComercial(setorComercial.getDescricao());
		}

		Quadra quadra = registroAtendimento.getQuadra();
		if(quadra != null){
			consultarRegistroAtendimentoPopupActionForm.setIdQuadra("" + quadra.getNumeroQuadra());
		}

		DivisaoEsgoto divisaoEsgoto = registroAtendimento.getDivisaoEsgoto();
		if(divisaoEsgoto != null){
			consultarRegistroAtendimentoPopupActionForm.setIdDivisaoEsgoto("" + divisaoEsgoto.getId());
			consultarRegistroAtendimentoPopupActionForm.setDivisaoEsgoto(divisaoEsgoto.getDescricao());
		}

		LocalOcorrencia localOcorrencia = registroAtendimento.getLocalOcorrencia();
		if(localOcorrencia != null){
			consultarRegistroAtendimentoPopupActionForm.setLocalOcorrencia(localOcorrencia.getDescricao());
		}

		PavimentoRua pavimentoRua = registroAtendimento.getPavimentoRua();
		if(pavimentoRua != null){
			consultarRegistroAtendimentoPopupActionForm.setPavimentoRua(pavimentoRua.getDescricao());
		}

		PavimentoCalcada pavimentoCalcada = registroAtendimento.getPavimentoCalcada();
		if(pavimentoCalcada != null){
			consultarRegistroAtendimentoPopupActionForm.setPavimentoCalcada(pavimentoCalcada.getDescricao());
		}

		consultarRegistroAtendimentoPopupActionForm.setDescricaoLocalOcorrencia(registroAtendimento.getDescricaoLocalOcorrencia());

		// Dados do Solicitante
		RegistroAtendimentoSolicitante registroAtendimentoSolicitante = this.consultarRegistroAtendimentoSolicitante(registroAtendimento
						.getId());

		if(registroAtendimentoSolicitante != null){

			Cliente cliente = registroAtendimentoSolicitante.getCliente();
			UnidadeOrganizacional unidadeSolicitante = registroAtendimentoSolicitante.getUnidadeOrganizacional();

			// Caso o principal solicitante do registro de atendimento seja um cliente obter os
			// dados do cliente
			if(cliente != null){

				consultarRegistroAtendimentoPopupActionForm.setIdClienteSolicitante("" + cliente.getId());
				consultarRegistroAtendimentoPopupActionForm.setClienteSolicitante(cliente.getNome());

				// Caso o principal solicitante do registro de atendimento seja uma unidade obter os
				// dados da unidade
			}else if(unidadeSolicitante != null){

				consultarRegistroAtendimentoPopupActionForm.setIdUnidadeSolicitante("" + unidadeSolicitante.getId());
				consultarRegistroAtendimentoPopupActionForm.setUnidadeSolicitante(unidadeSolicitante.getDescricao());

				// Caso o principal solicitante do registro de atendimento n�o seja um cliente, nem
				// uma unidade obter os dados do solicitante
			}else{
				consultarRegistroAtendimentoPopupActionForm.setNomeSolicitante(registroAtendimentoSolicitante.getSolicitante());
			}

			Funcionario funcionario = registroAtendimentoSolicitante.getFuncionario();
			if(funcionario != null){
				consultarRegistroAtendimentoPopupActionForm.setIdFuncionarioResponsavel("" + funcionario.getId());
				consultarRegistroAtendimentoPopupActionForm.setFuncionarioResponsavel(funcionario.getNome());
			}

			// Caso de Uso [UC0423]
			String enderecoSolicitante = fachada.obterEnderecoSolicitanteRA(registroAtendimentoSolicitante.getID());

			consultarRegistroAtendimentoPopupActionForm.setEnderecoSolicitante(enderecoSolicitante);
			consultarRegistroAtendimentoPopupActionForm.setPontoReferenciaSolicitante(registroAtendimentoSolicitante.getPontoReferencia());

			SolicitanteFone solicitanteFone = consultarSolicitanteFone(registroAtendimentoSolicitante.getID());

			if(solicitanteFone != null){
				consultarRegistroAtendimentoPopupActionForm.setFoneDDD("" + solicitanteFone.getDdd());
				consultarRegistroAtendimentoPopupActionForm.setFone(solicitanteFone.getFone());
				consultarRegistroAtendimentoPopupActionForm.setFoneRamal(solicitanteFone.getRamal());

			}
		}

		// Dados da Ultima Tramita��o
		Tramite tramite = fachada.recuperarTramiteMaisAtualPorRA(registroAtendimento.getId());

		if(tramite != null){

			UnidadeOrganizacional unidadeOrigem = tramite.getUnidadeOrganizacionalOrigem();
			if(unidadeOrigem != null){
				consultarRegistroAtendimentoPopupActionForm.setIdUnidadeOrigem("" + unidadeOrigem.getId());
				consultarRegistroAtendimentoPopupActionForm.setUnidadeOrigem(unidadeOrigem.getDescricao());
			}

			UnidadeOrganizacional unidadeDestino = tramite.getUnidadeOrganizacionalDestino();
			if(unidadeDestino != null){
				consultarRegistroAtendimentoPopupActionForm.setIdUnidadeAtualTramitacao("" + unidadeDestino.getId());
				consultarRegistroAtendimentoPopupActionForm.setUnidadeAtualTramitacao(unidadeDestino.getDescricao());
			}

			Date dataTramite = tramite.getDataTramite();

			consultarRegistroAtendimentoPopupActionForm.setDataTramite(Util.formatarData(dataTramite));
			consultarRegistroAtendimentoPopupActionForm.setHoraTramite(Util.formatarHoraSemSegundos(dataTramite));
			consultarRegistroAtendimentoPopupActionForm.setParecerTramite(tramite.getParecerTramite());
			if(tramite.getMotivoTramite() != null && !GenericValidator.isBlankOrNull(tramite.getMotivoTramite().getDescricao())){
				consultarRegistroAtendimentoPopupActionForm.setMotivoTramite(tramite.getMotivoTramite().getDescricao());
			}

			if(tramite.getUsuarioResponsavel() != null && tramite.getUsuarioResponsavel().getNomeUsuario() != null){
				consultarRegistroAtendimentoPopupActionForm.setIdUsuarioResponsavel(tramite.getUsuarioResponsavel().getId().toString());
				consultarRegistroAtendimentoPopupActionForm.setUsuarioResponsavel(tramite.getUsuarioResponsavel().getNomeUsuario());
			}

		}

		// Dados da Reitera��o

		// Caso o registro atendimento tenha sido reiterado, exibir os dados da reitera��o
		if(registroAtendimento.getQuantidadeReiteracao() != null){

			Date dataUltimaReiteracao = registroAtendimento.getUltimaReiteracao();

			consultarRegistroAtendimentoPopupActionForm.setQuantidade("" + registroAtendimento.getQuantidadeReiteracao());
			consultarRegistroAtendimentoPopupActionForm.setDataUltimaReiteracao(Util.formatarData(dataUltimaReiteracao));
			consultarRegistroAtendimentoPopupActionForm.setHoraUltimaReiteracao(Util.formatarHoraSemSegundos(dataUltimaReiteracao));
		}

		// Dados da Reativa��o

		// Caso o registro atendimento tenha sido reativado exibir os dados da reativa��o
		Short codigoAssociado = obterRAAssociadoHelper.getCodigoExistenciaRAAssociado();

		RegistroAtendimento registroAtendimentoAssociado = obterRAAssociadoHelper.getRegistroAtendimentoAssociado();

		// Caso de Uso [UC0420]
		ObterDescricaoSituacaoRAHelper situacaoRAAssociado = null;
		if(registroAtendimentoAssociado != null){
			situacaoRAAssociado = fachada.obterDescricaoSituacaoRA(registroAtendimentoAssociado.getId());
		}

		if(codigoAssociado == RegistroAtendimento.CODIGO_ASSOCIADO_RA_ATUAL && registroAtendimentoAssociado != null){

			consultarRegistroAtendimentoPopupActionForm.setNumeroRaAtual("" + registroAtendimentoAssociado.getId());
			consultarRegistroAtendimentoPopupActionForm.setSituacaoRaAtual(situacaoRAAssociado.getDescricaoSituacao());

			RaMotivoReativacao raMotivoReativacao = registroAtendimentoAssociado.getRaMotivoReativacao();
			if(raMotivoReativacao != null){
				consultarRegistroAtendimentoPopupActionForm.setIdMotivoReativacao("" + raMotivoReativacao.getId());
				consultarRegistroAtendimentoPopupActionForm.setMotivoReativacao(raMotivoReativacao.getDescricao());
			}

			Date dataRegistro = registroAtendimentoAssociado.getRegistroAtendimento();
			Date dataPrevista = registroAtendimentoAssociado.getDataPrevistaAtual();

			consultarRegistroAtendimentoPopupActionForm.setDataReativacao(Util.formatarData(dataRegistro));
			consultarRegistroAtendimentoPopupActionForm.setHoraReativacao(Util.formatarHoraSemSegundos(dataRegistro));

			consultarRegistroAtendimentoPopupActionForm.setDataPrevistaRaAtual(Util.formatarData(dataPrevista) + " "
							+ Util.formatarHoraSemSegundos(registroAtendimento.getDataPrevistaAtual()));

			// Caso de Uso [UC0421]
			UnidadeOrganizacional unidadeReativacao = fachada.obterUnidadeAtendimentoRA(registroAtendimentoAssociado.getId());
			if(unidadeReativacao != null){
				consultarRegistroAtendimentoPopupActionForm.setIdUnidadeReativacao("" + unidadeReativacao.getId());
				consultarRegistroAtendimentoPopupActionForm.setUnidadeReativacao(unidadeReativacao.getDescricao());
			}


			// Caso de Uso [UC0418]
			UnidadeOrganizacional unidadeRAAtual = fachada.obterUnidadeAtualRA(registroAtendimentoAssociado.getId());
			if(unidadeRAAtual != null){
				consultarRegistroAtendimentoPopupActionForm.setIdUnidadeRaAtual("" + unidadeRAAtual.getId());
				consultarRegistroAtendimentoPopupActionForm.setUnidadeRaAtual(unidadeRAAtual.getDescricao());
			}

			consultarRegistroAtendimentoPopupActionForm.setObservacaoReativacao(registroAtendimentoAssociado.getObservacao());
		}

		// Dados do encerramento

		// Caso o registro atendimento seja encerrado, exibir os dados do encerramento
		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = registroAtendimento.getAtendimentoMotivoEncerramento();

		if(atendimentoMotivoEncerramento != null){

			consultarRegistroAtendimentoPopupActionForm.setIdMotivoEncerramento("" + atendimentoMotivoEncerramento.getId());
			consultarRegistroAtendimentoPopupActionForm.setMotivoEncerramento(atendimentoMotivoEncerramento.getDescricao());

			if(codigoAssociado == RegistroAtendimento.CODIGO_ASSOCIADO_RA_REFERENCIA && registroAtendimentoAssociado != null){

				consultarRegistroAtendimentoPopupActionForm.setNumeroRaReferencia("" + registroAtendimentoAssociado.getId());

				// Caso de Uso [UC0420]
				consultarRegistroAtendimentoPopupActionForm.setSituacaoRaReferencia(situacaoRAAssociado.getDescricaoSituacao());
			}

			// Caso de Uso [UC0434]
			UnidadeOrganizacional unidadeEncerramento = fachada.obterUnidadeEncerramentoRA(registroAtendimento.getId());
			if(unidadeEncerramento != null){

				consultarRegistroAtendimentoPopupActionForm.setIdUnidadeEncerramento("" + unidadeEncerramento.getId());
				consultarRegistroAtendimentoPopupActionForm.setUnidadeEncerramento(unidadeEncerramento.getDescricao());

				Usuario usuario = registroAtendimentoUnidade.getUsuario();
				if(usuario != null){

					consultarRegistroAtendimentoPopupActionForm.setIdUsuarioEncerramento("" + usuario.getId());
					consultarRegistroAtendimentoPopupActionForm.setUsuarioEncerramento(usuario.getNomeUsuario());
				}
			}

			Date dataEncerramento = registroAtendimento.getDataEncerramento();

			consultarRegistroAtendimentoPopupActionForm.setDataEncerramento(Util.formatarData(dataEncerramento));
			consultarRegistroAtendimentoPopupActionForm.setHoraEncerramento(Util.formatarHoraSemSegundos(dataEncerramento));

			consultarRegistroAtendimentoPopupActionForm.setDataPrevistaEncerramento(Util.formatarData(registroAtendimento
							.getDataPrevistaAtual())
							+ " " + Util.formatarHoraSemSegundos(registroAtendimento.getDataPrevistaAtual()));

			consultarRegistroAtendimentoPopupActionForm.setParecerEncerramento(registroAtendimento.getParecerEncerramento());

		}

		// Colocado especialmente para trabalhar com o retorno da pesquisa de OS - Raphael Rossiter
		// em 13/02/2007
		String caminhoTelaPesquisaRetorno = httpServletRequest.getParameter("caminhoTelaPesquisaRetorno");
		if(caminhoTelaPesquisaRetorno != null && !caminhoTelaPesquisaRetorno.equals("")){
			httpServletRequest.setAttribute("caminhoTelaPesquisaRetorno", caminhoTelaPesquisaRetorno);
		}

		return retorno;
	}

	/**
	 * Consulta o registro atendimento solicitante pelo id do registro atendimento
	 * 
	 * @author Rafael Pinto
	 * @created 09/08/2006
	 */
	private RegistroAtendimentoSolicitante consultarRegistroAtendimentoSolicitante(Integer idRegistroAtendimento){

		RegistroAtendimentoSolicitante retorno = null;
		Fachada fachada = Fachada.getInstancia();
		Collection colecaoRegistroAtendimento = null;

		FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimento = new FiltroRegistroAtendimentoSolicitante();

		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimentoSolicitante.REGISTRO_ATENDIMENTO_ID,
						idRegistroAtendimento));

		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(
						FiltroRegistroAtendimentoSolicitante.INDICADOR_SOLICITANTE_PRINCIPAL, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("funcionario");

		colecaoRegistroAtendimento = fachada.pesquisar(filtroRegistroAtendimento, RegistroAtendimentoSolicitante.class.getName());

		if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){
			retorno = (RegistroAtendimentoSolicitante) Util.retonarObjetoDeColecao(colecaoRegistroAtendimento);
		}

		return retorno;
	}

	/**
	 * Consulta o solicitante fone pelo id do registro atendimentoSolicitante
	 * 
	 * @author Rafael Pinto
	 * @created 09/08/2006
	 */
	private SolicitanteFone consultarSolicitanteFone(Integer idRegistroAtendimentoSolicitante){

		SolicitanteFone retorno = null;
		Fachada fachada = Fachada.getInstancia();
		Collection colecaoSolicitanteFone = null;

		FiltroSolicitanteFone filtroSolicitanteFone = new FiltroSolicitanteFone();

		filtroSolicitanteFone.adicionarParametro(new ParametroSimples(FiltroSolicitanteFone.REGISTRO_ATENDIMENTO_SOLICITANTE_ID,
						idRegistroAtendimentoSolicitante));

		colecaoSolicitanteFone = fachada.pesquisar(filtroSolicitanteFone, SolicitanteFone.class.getName());

		if(colecaoSolicitanteFone != null && !colecaoSolicitanteFone.isEmpty()){
			retorno = (SolicitanteFone) Util.retonarObjetoDeColecao(colecaoSolicitanteFone);
		}

		return retorno;
	}

	/**
	 * Consulta o Registro Atendimento Unidade pelo id da RA
	 * 
	 * @author Rafael Pinto
	 * @created 09/08/2006
	 */
	private RegistroAtendimentoUnidade consultarRegistroAtendimentoUnidade(Integer idRA, Integer idUnidade){

		RegistroAtendimentoUnidade retorno = null;
		Fachada fachada = Fachada.getInstancia();
		Collection colecaoRegistroAtendimentoUnidade = null;

		FiltroRegistroAtendimentoUnidade filtroRegistroAtendimentoUnidade = new FiltroRegistroAtendimentoUnidade();

		filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimentoUnidade.REGISTRO_ATENDIMENTO_ID,
						idRA));

		filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(
						FiltroRegistroAtendimentoUnidade.UNIDADE_ORGANIZACIONAL_ID, idUnidade));

		filtroRegistroAtendimentoUnidade.adicionarCaminhoParaCarregamentoEntidade("usuario");

		colecaoRegistroAtendimentoUnidade = fachada.pesquisar(filtroRegistroAtendimentoUnidade, RegistroAtendimentoUnidade.class.getName());

		if(colecaoRegistroAtendimentoUnidade != null && !colecaoRegistroAtendimentoUnidade.isEmpty()){
			retorno = (RegistroAtendimentoUnidade) Util.retonarObjetoDeColecao(colecaoRegistroAtendimentoUnidade);
		}

		return retorno;
	}
}
