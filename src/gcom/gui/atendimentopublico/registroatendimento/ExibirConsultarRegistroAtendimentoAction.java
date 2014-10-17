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
import gcom.cadastro.sistemaparametro.SistemaParametro;
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
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de consultar RA
 * 
 * @author Rafael Pinto
 * @created 25/07/2006
 */
public class ExibirConsultarRegistroAtendimentoAction
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
		ActionForward retorno = actionMapping.findForward("consultarRegistroAtendimento");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarRegistroAtendimentoActionForm consultarRegistroAtendimentoActionForm = (ConsultarRegistroAtendimentoActionForm) actionForm;
		// consultarRegistroAtendimentoActionForm.setNomeSolicitante("");

		if(httpServletRequest.getParameter("raReativado") != null){
			String isRAReativado = httpServletRequest.getParameter("raReativado");

			if(isRAReativado.equals("1")){
				sessao.setAttribute("raReativado", "SIM");
				consultarRegistroAtendimentoActionForm.setIsRAReativado("1");
			}else if(isRAReativado.equals("0")){
				sessao.removeAttribute("raReativado");
				consultarRegistroAtendimentoActionForm.setIsRAReativado("0");
			}
		}else{
			sessao.removeAttribute("raReativado");
			consultarRegistroAtendimentoActionForm.setIsRAReativado("0");
		}

		Integer idRA = null;
		if(consultarRegistroAtendimentoActionForm.getNumeroRA() != null
						&& !consultarRegistroAtendimentoActionForm.getNumeroRA().equalsIgnoreCase("")){

			idRA = Integer.valueOf(consultarRegistroAtendimentoActionForm.getNumeroRA());

			if(httpServletRequest.getParameter("pesquisaUnitaria") != null){
				consultarRegistroAtendimentoActionForm.reset(actionMapping, httpServletRequest);
				sessao.removeAttribute("colecaoCompleta");
				sessao.setAttribute("naoHabilitarNavegacao", "OK");
			}else if(sessao.getAttribute("colecaoCompleta") != null){
				sessao.removeAttribute("naoHabilitarNavegacao");
			}else{
				sessao.setAttribute("naoHabilitarNavegacao", "OK");
			}
		}else{
			idRA = (Integer) sessao.getAttribute("numeroOS");
			sessao.removeAttribute("colecaoCompleta");

			sessao.setAttribute("naoHabilitarNavegacao", "OK");
		}

		if(sessao.getAttribute("colecaoCompleta") != null){

			Collection colecaoCompleta = (Collection) sessao.getAttribute("colecaoCompleta");
			Integer index = null;

			index = this.obterIndexRAColecao(colecaoCompleta, idRA);

			/*
			 * Verifica existência da utilização dos botões de navegação
			 */
			if(httpServletRequest.getParameter("raAnterior") != null || httpServletRequest.getParameter("proximoRA") != null){

				// Navegando na coleção
				if(httpServletRequest.getParameter("raAnterior") != null){

					index = index - 1;

				}

				if(httpServletRequest.getParameter("proximoRA") != null){

					index = index + 1;

				}
			}
			if(index >= colecaoCompleta.size()){
				index = colecaoCompleta.size() - 1;
			}

			idRA = ((RegistroAtendimento) ((List) colecaoCompleta).get(index)).getId();

			if(index == 0){
				sessao.setAttribute("desabilitaBotaoAnterior", 1);
			}else{
				sessao.removeAttribute("desabilitaBotaoAnterior");
			}

			if((index + 1) >= colecaoCompleta.size()){
				sessao.setAttribute("desabilitaBotaoProximo", 1);
			}else{
				sessao.removeAttribute("desabilitaBotaoProximo");
			}

		}

		ObterDadosRegistroAtendimentoHelper obterDadosRegistroAtendimentoHelper = fachada.obterDadosRegistroAtendimento(Integer
						.valueOf(idRA));

		if(obterDadosRegistroAtendimentoHelper == null || obterDadosRegistroAtendimentoHelper.getRegistroAtendimento() == null){

			throw new ActionServletException("atencao.naocadastrado", null, "Registro Atendimento");
		}

		RegistroAtendimento registroAtendimento = obterDadosRegistroAtendimentoHelper.getRegistroAtendimento();

		// Dados Gerais do Registro de Atendimento
		consultarRegistroAtendimentoActionForm.setNumeroRAPesquisado(String.valueOf(registroAtendimento.getId()));

		if(!Util.isVazioOuBranco(registroAtendimento.getIndicadorProcessoAdmJud())){
			if(registroAtendimento.getIndicadorProcessoAdmJud() == ConstantesSistema.SIM.shortValue()){
				consultarRegistroAtendimentoActionForm.setIndicadorProcessoAdmJud("SIM");
			}else{
				consultarRegistroAtendimentoActionForm.setIndicadorProcessoAdmJud("NÃO");
			}
		}else{
			consultarRegistroAtendimentoActionForm.setIndicadorProcessoAdmJud("");
		}

		if(!Util.isVazioOuBranco(registroAtendimento.getNumeroProcessoAgencia())){
			consultarRegistroAtendimentoActionForm.setNumeroProcessoAgencia(registroAtendimento.getNumeroProcessoAgencia());
		}else{
			consultarRegistroAtendimentoActionForm.setNumeroProcessoAgencia("");
		}

		if(registroAtendimento.getReiteracao() != null){
			consultarRegistroAtendimentoActionForm.setRaReiterado(String.valueOf(registroAtendimento.getReiteracao()));
		}else{
			consultarRegistroAtendimentoActionForm.setRaReiterado("");
		}
		if(registroAtendimento.getManual() != null){
			int tamanhoNumeracao = registroAtendimento.getManual().toString().length();
			String numeracao = registroAtendimento.getManual().toString().substring(0, tamanhoNumeracao - 1);
			consultarRegistroAtendimentoActionForm.setNumeroRAManual(Util.formatarNumeracaoRAManual(Integer.valueOf(numeracao)));
		}else{
			consultarRegistroAtendimentoActionForm.setNumeroRAManual("");
		}

		consultarRegistroAtendimentoActionForm.setCodigoSituacao(String.valueOf(registroAtendimento.getCodigoSituacao()));

		// Caso de Uso [UC0420]
		ObterDescricaoSituacaoRAHelper situacaoRA = fachada.obterDescricaoSituacaoRA(registroAtendimento.getId());

		consultarRegistroAtendimentoActionForm.setSituacaoRA(situacaoRA.getDescricaoSituacao());

		// Caso de Uso [UC0433]
		ObterRAAssociadoHelper obterRAAssociadoHelper = fachada.obterRAAssociado(registroAtendimento.getId());

		if(obterRAAssociadoHelper != null && obterRAAssociadoHelper.getRegistroAtendimentoAssociado() != null){
			consultarRegistroAtendimentoActionForm.setNumeroRaAssociado(String.valueOf(obterRAAssociadoHelper
							.getRegistroAtendimentoAssociado().getId()));

			ObterDescricaoSituacaoRAHelper situacaoRAssociado = fachada.obterDescricaoSituacaoRA(obterRAAssociadoHelper
							.getRegistroAtendimentoAssociado().getId());

			consultarRegistroAtendimentoActionForm.setSituacaoRaAssociado(situacaoRAssociado.getDescricaoSituacao());

			if(obterRAAssociadoHelper.getCodigoExistenciaRAAssociado() == RegistroAtendimento.CODIGO_ASSOCIADO_RA_REFERENCIA){
				consultarRegistroAtendimentoActionForm.setDescricaoRAAssociada("Número do RA de Referência:");
				consultarRegistroAtendimentoActionForm.setDescricaoSituacaoRAAssociada("Situação do RA de Referência:");
			}else if(obterRAAssociadoHelper.getCodigoExistenciaRAAssociado() == RegistroAtendimento.CODIGO_ASSOCIADO_RA_ATUAL){
				consultarRegistroAtendimentoActionForm.setDescricaoRAAssociada("Número do RA Atual:");
				consultarRegistroAtendimentoActionForm.setDescricaoSituacaoRAAssociada("Situação do RA Atual:");
			}else if(obterRAAssociadoHelper.getCodigoExistenciaRAAssociado() == RegistroAtendimento.CODIGO_ASSOCIADO_RA_ANTERIOR){
				consultarRegistroAtendimentoActionForm.setDescricaoRAAssociada("Número do RA Anterior:");
				consultarRegistroAtendimentoActionForm.setDescricaoSituacaoRAAssociada("Situação do RA Anterior:");
			}

			httpServletRequest.setAttribute("existeRaAssociado", true);
		}

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = registroAtendimento.getSolicitacaoTipoEspecificacao();

		if(solicitacaoTipoEspecificacao != null){

			if(solicitacaoTipoEspecificacao.getSolicitacaoTipo() != null){
				consultarRegistroAtendimentoActionForm.setIdTipoSolicitacao(String.valueOf(solicitacaoTipoEspecificacao
								.getSolicitacaoTipo().getId()));
				consultarRegistroAtendimentoActionForm.setTipoSolicitacao(solicitacaoTipoEspecificacao.getSolicitacaoTipo().getDescricao());
			}
			consultarRegistroAtendimentoActionForm.setIdEspecificacao(String.valueOf(solicitacaoTipoEspecificacao.getId()));
			consultarRegistroAtendimentoActionForm.setEspecificacao(solicitacaoTipoEspecificacao.getDescricao());
		}

		consultarRegistroAtendimentoActionForm.setTipoAtendimento(String.valueOf(registroAtendimento.getIndicadorAtendimentoOnline()));

		Date dataAtendimento = registroAtendimento.getRegistroAtendimento();

		consultarRegistroAtendimentoActionForm.setDataAtendimento(Util.formatarData(dataAtendimento));
		consultarRegistroAtendimentoActionForm.setHoraAtendimento(Util.formatarHoraSemSegundos(dataAtendimento));

		consultarRegistroAtendimentoActionForm
						.setTempoEsperaInicio(Util.formatarHoraSemSegundos(registroAtendimento.getDataInicioEspera()));
		consultarRegistroAtendimentoActionForm.setTempoEsperaTermino(Util.formatarHoraSemSegundos(registroAtendimento.getDataFimEspera()));

		SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute(SistemaParametro.SISTEMA_PARAMETRO);
		if(sistemaParametro.getParmId().shortValue() == SistemaParametro.INDICADOR_EMPRESA_ADA){
			consultarRegistroAtendimentoActionForm.setDataPrevista(Util.formatarData(registroAtendimento.getDataPrevistaAtual()) + " "
							+ Util.formatarHoraSemSegundos(registroAtendimento.getDataPrevistaAtual()));
		}else if(sistemaParametro.getParmId().shortValue() == SistemaParametro.INDICADOR_EMPRESA_DESO){
			consultarRegistroAtendimentoActionForm.setDataPrevista(Util.formatarData(registroAtendimento.getDataPrevistaAtual()));
		}

		if(registroAtendimento.getSenhaAtendimento() != null){
			consultarRegistroAtendimentoActionForm.setSenhaAtendimento(String.valueOf(registroAtendimento.getSenhaAtendimento()));
		}else{
			consultarRegistroAtendimentoActionForm.setSenhaAtendimento("");
		}

		if(registroAtendimento.getMeioSolicitacao() != null){
			consultarRegistroAtendimentoActionForm.setIdMeioSolicitacao(String.valueOf(registroAtendimento.getMeioSolicitacao().getId()));
			consultarRegistroAtendimentoActionForm.setMeioSolicitacao(registroAtendimento.getMeioSolicitacao().getDescricao());
		}

		// Caso de Uso [UC0421]
		UnidadeOrganizacional unidadeAtendimento = fachada.obterUnidadeAtendimentoRA(registroAtendimento.getId());

		if(unidadeAtendimento != null){

			consultarRegistroAtendimentoActionForm.setIdUnidadeAtendimento(String.valueOf(unidadeAtendimento.getId()));
			consultarRegistroAtendimentoActionForm.setUnidadeAtendimento(unidadeAtendimento.getDescricao());

			RegistroAtendimentoUnidade registroAtendimentoUnidade = this.consultarRegistroAtendimentoUnidade(registroAtendimento.getId(),
							unidadeAtendimento.getId());

			Usuario usuario = registroAtendimentoUnidade.getUsuario();

			if(usuario != null){

				consultarRegistroAtendimentoActionForm.setIdUsuario(String.valueOf(usuario.getId()));
				consultarRegistroAtendimentoActionForm.setUsuario(usuario.getNomeUsuario());
			}

		}

		// Caso de Uso [UC0418]
		UnidadeOrganizacional unidadeAtual = fachada.obterUnidadeAtualRA(registroAtendimento.getId());

		if(unidadeAtual != null){
			consultarRegistroAtendimentoActionForm.setIdUnidadeAtual(String.valueOf(unidadeAtual.getId()));
			consultarRegistroAtendimentoActionForm.setUnidadeAtual(unidadeAtual.getDescricao());
		}

		consultarRegistroAtendimentoActionForm.setObservacao(registroAtendimento.getObservacao());

		// Dados do Local da Ocorrencia
		Imovel imovel = registroAtendimento.getImovel();
		if(imovel != null){
			Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

			Integer idImovel = imovel.getId();
			Integer idRegistroAtendimento = registroAtendimento.getId();

			// [SB0013] - Validar Manutenção de RA pelos Usuários das Empresas de Cobrança
			// Administrativa
			if(fachada.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuarioLogado, idImovel,
							ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA).equals(ConstantesSistema.NAO)){
				throw new ActionServletException("atencao.usuario_sem_acesso_manutencao_ra_imovel_cobranca_administrativa",
								usuarioLogado.getLogin(), Integer.toString(idRegistroAtendimento), Integer.toString(idImovel));
			}

			consultarRegistroAtendimentoActionForm.setMatriculaImovel(String.valueOf(idImovel));
			consultarRegistroAtendimentoActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());
			consultarRegistroAtendimentoActionForm.setRota(obterDadosRegistroAtendimentoHelper.getCodigoRota().toString());

			if(obterDadosRegistroAtendimentoHelper.getSequencialRota() != null){
				consultarRegistroAtendimentoActionForm
								.setSequencialRota(obterDadosRegistroAtendimentoHelper.getSequencialRota().toString());
			}
		}

		// Caso de Uso [UC0422]
		String enderecoOcorrencia = fachada.obterEnderecoOcorrenciaRA(registroAtendimento.getId());

		consultarRegistroAtendimentoActionForm.setEnderecoOcorrencia(enderecoOcorrencia);
		consultarRegistroAtendimentoActionForm.setPontoReferencia(registroAtendimento.getPontoReferencia());

		// Caso o registro atendimento esteja associado a uma área de bairro,
		// obter os dados da área do bairro
		BairroArea bairroArea = registroAtendimento.getBairroArea();

		if(bairroArea != null){

			consultarRegistroAtendimentoActionForm.setIdMunicipio(String.valueOf(bairroArea.getBairro().getMunicipio().getId()));
			consultarRegistroAtendimentoActionForm.setMunicipio(bairroArea.getBairro().getMunicipio().getNome());

			consultarRegistroAtendimentoActionForm.setIdBairro(String.valueOf(bairroArea.getBairro().getId()));
			consultarRegistroAtendimentoActionForm.setBairro(bairroArea.getBairro().getNome());

			consultarRegistroAtendimentoActionForm.setIdAreaBairro(String.valueOf(bairroArea.getId()));
			consultarRegistroAtendimentoActionForm.setAreaBairro(bairroArea.getNome());

		}

		Localidade localidade = registroAtendimento.getLocalidade();

		if(localidade != null){

			consultarRegistroAtendimentoActionForm.setIdLocalidade(String.valueOf(localidade.getId()));
			consultarRegistroAtendimentoActionForm.setLocalidade(localidade.getDescricao());
		}

		SetorComercial setorComercial = registroAtendimento.getSetorComercial();

		if(setorComercial != null){
			consultarRegistroAtendimentoActionForm.setIdSetorComercial(String.valueOf(setorComercial.getCodigo()));
			consultarRegistroAtendimentoActionForm.setSetorComercial(setorComercial.getDescricao());
		}

		Quadra quadra = registroAtendimento.getQuadra();

		if(quadra != null){
			consultarRegistroAtendimentoActionForm.setIdQuadra(String.valueOf(quadra.getNumeroQuadra()));
		}

		DivisaoEsgoto divisaoEsgoto = registroAtendimento.getDivisaoEsgoto();

		if(divisaoEsgoto != null){

			consultarRegistroAtendimentoActionForm.setIdDivisaoEsgoto(String.valueOf(divisaoEsgoto.getId()));
			consultarRegistroAtendimentoActionForm.setDivisaoEsgoto(divisaoEsgoto.getDescricao());
		}

		LocalOcorrencia localOcorrencia = registroAtendimento.getLocalOcorrencia();

		if(localOcorrencia != null){
			consultarRegistroAtendimentoActionForm.setLocalOcorrencia(localOcorrencia.getDescricao());
		}

		PavimentoRua pavimentoRua = registroAtendimento.getPavimentoRua();

		if(pavimentoRua != null){
			consultarRegistroAtendimentoActionForm.setPavimentoRua(pavimentoRua.getDescricao());
		}

		PavimentoCalcada pavimentoCalcada = registroAtendimento.getPavimentoCalcada();

		if(pavimentoCalcada != null){
			consultarRegistroAtendimentoActionForm.setPavimentoCalcada(pavimentoCalcada.getDescricao());
		}

		consultarRegistroAtendimentoActionForm.setDescricaoLocalOcorrencia(registroAtendimento.getDescricaoLocalOcorrencia());

		// Dados do Solicitante

		RegistroAtendimentoSolicitante registroAtendimentoSolicitante = this.consultarRegistroAtendimentoSolicitante(registroAtendimento
						.getId());

		if(registroAtendimentoSolicitante != null){

			Cliente cliente = registroAtendimentoSolicitante.getCliente();
			UnidadeOrganizacional unidadeSolicitante = registroAtendimentoSolicitante.getUnidadeOrganizacional();

			// Caso o principal solicitante do registro de atendimento seja um cliente
			// obter os dados do cliente
			if(cliente != null){

				consultarRegistroAtendimentoActionForm.setIdClienteSolicitante(String.valueOf(cliente.getId()));
				consultarRegistroAtendimentoActionForm.setClienteSolicitante(cliente.getNome());

				// Caso o principal solicitante do registro de atendimento seja uma unidade
				// obter os dados da unidade
			}else if(unidadeSolicitante != null){

				consultarRegistroAtendimentoActionForm.setIdUnidadeSolicitante(String.valueOf(unidadeSolicitante.getId()));
				consultarRegistroAtendimentoActionForm.setUnidadeSolicitante(unidadeSolicitante.getDescricao());

				// Caso o principal solicitante do registro de atendimento não seja um cliente, nem
				// uma unidade
				// obter os dados do solicitante
			}else{
				consultarRegistroAtendimentoActionForm.setNomeSolicitante(registroAtendimentoSolicitante.getSolicitante());
			}

			Funcionario funcionario = registroAtendimentoSolicitante.getFuncionario();

			if(funcionario != null){
				consultarRegistroAtendimentoActionForm.setIdFuncionarioResponsavel(String.valueOf(funcionario.getId()));
				consultarRegistroAtendimentoActionForm.setFuncionarioResponsavel(funcionario.getNome());
			}

			// Caso de Uso [UC0423]
			String enderecoSolicitante = fachada.obterEnderecoSolicitanteRA(registroAtendimentoSolicitante.getID());

			consultarRegistroAtendimentoActionForm.setEnderecoSolicitante(enderecoSolicitante);
			consultarRegistroAtendimentoActionForm.setPontoReferenciaSolicitante(registroAtendimentoSolicitante.getPontoReferencia());

			SolicitanteFone solicitanteFone = consultarSolicitanteFone(registroAtendimentoSolicitante.getID());

			if(solicitanteFone != null){
				consultarRegistroAtendimentoActionForm.setFoneDDD(String.valueOf(solicitanteFone.getDdd()));
				consultarRegistroAtendimentoActionForm.setFone(solicitanteFone.getFone());
				consultarRegistroAtendimentoActionForm.setFoneRamal(solicitanteFone.getRamal());

			}
			if(registroAtendimentoSolicitante.getClienteTipo() != null){
				consultarRegistroAtendimentoActionForm.setClienteTipo(registroAtendimentoSolicitante.getClienteTipo().toString());
				if(registroAtendimentoSolicitante.getClienteTipo().shortValue() == ConstantesSistema.INDICADOR_PESSOA_FISICA.shortValue()){
					consultarRegistroAtendimentoActionForm.setNumeroCpf(registroAtendimentoSolicitante.getNumeroCpf());
					consultarRegistroAtendimentoActionForm.setNumeroRG(registroAtendimentoSolicitante.getNumeroRG());
					if(registroAtendimentoSolicitante.getOrgaoExpedidorRg() != null){
						consultarRegistroAtendimentoActionForm.setOrgaoExpedidorRg(registroAtendimentoSolicitante.getOrgaoExpedidorRg()
										.getDescricaoParaRegistroTransacao());
					}

					if(registroAtendimentoSolicitante.getUnidadeFederacaoRG() != null){
						consultarRegistroAtendimentoActionForm.setUnidadeFederacaoRG(registroAtendimentoSolicitante.getUnidadeFederacaoRG()
										.getDescricaoParaRegistroTransacao());
					}
				}else if(registroAtendimentoSolicitante.getClienteTipo().shortValue() == ConstantesSistema.INDICADOR_PESSOA_JURIDICA
								.shortValue()){
					consultarRegistroAtendimentoActionForm.setNumeroCnpj(registroAtendimentoSolicitante.getNumeroCnpj());
				}
			}else{
				consultarRegistroAtendimentoActionForm.setClienteTipo("");
			}

		}

		// Dados da Ultima Tramitação

		Tramite tramite = fachada.recuperarTramiteMaisAtualPorRA(registroAtendimento.getId());

		if(tramite != null){

			UnidadeOrganizacional unidadeOrigem = tramite.getUnidadeOrganizacionalOrigem();

			if(unidadeOrigem != null){

				consultarRegistroAtendimentoActionForm.setIdUnidadeOrigem(String.valueOf(unidadeOrigem.getId()));
				consultarRegistroAtendimentoActionForm.setUnidadeOrigem(unidadeOrigem.getDescricao());
			}

			UnidadeOrganizacional unidadeDestino = tramite.getUnidadeOrganizacionalDestino();

			if(unidadeDestino != null){

				consultarRegistroAtendimentoActionForm.setIdUnidadeAtualTramitacao(String.valueOf(unidadeDestino.getId()));
				consultarRegistroAtendimentoActionForm.setUnidadeAtualTramitacao(unidadeDestino.getDescricao());

			}

			Date dataTramite = tramite.getDataTramite();

			consultarRegistroAtendimentoActionForm.setDataTramite(Util.formatarData(dataTramite));
			consultarRegistroAtendimentoActionForm.setHoraTramite(Util.formatarHoraSemSegundos(dataTramite));

			consultarRegistroAtendimentoActionForm.setParecerTramite(tramite.getParecerTramite());
			if(tramite.getMotivoTramite() != null && !GenericValidator.isBlankOrNull(tramite.getMotivoTramite().getDescricao())){
				consultarRegistroAtendimentoActionForm.setMotivoTramite(tramite.getMotivoTramite().getDescricao());
			}

			Usuario usuarioResponsavel = tramite.getUsuarioResponsavel();

			if(usuarioResponsavel != null){
				consultarRegistroAtendimentoActionForm.setIdUsuarioResponsavel(String.valueOf(usuarioResponsavel.getId()));
				consultarRegistroAtendimentoActionForm.setUsuarioResponsavel(usuarioResponsavel.getNomeUsuario());
			}
		}

		// Dados da Reiteração

		// Caso o registro atendimento tenha sido reiterado,
		// exibir os dados da reiteração
		if(registroAtendimento.getQuantidadeReiteracao() != null){

			Date dataUltimaReiteracao = registroAtendimento.getUltimaReiteracao();

			consultarRegistroAtendimentoActionForm.setQuantidade(String.valueOf(registroAtendimento.getQuantidadeReiteracao()));
			consultarRegistroAtendimentoActionForm.setDataUltimaReiteracao(Util.formatarData(dataUltimaReiteracao));
			consultarRegistroAtendimentoActionForm.setHoraUltimaReiteracao(Util.formatarHoraSemSegundos(dataUltimaReiteracao));

		}

		// Dados da Reativação

		// Caso o registro atendimento tenha sido reativado
		// exibir os dados da reativação
		Short codigoAssociado = obterRAAssociadoHelper.getCodigoExistenciaRAAssociado();

		RegistroAtendimento registroAtendimentoAssociado = obterRAAssociadoHelper.getRegistroAtendimentoAssociado();

		// Caso de Uso [UC0420]
		ObterDescricaoSituacaoRAHelper situacaoRAAssociado = null;
		if(registroAtendimentoAssociado != null){
			situacaoRAAssociado = fachada.obterDescricaoSituacaoRA(registroAtendimentoAssociado.getId());
		}

		if(codigoAssociado == RegistroAtendimento.CODIGO_ASSOCIADO_RA_ATUAL && registroAtendimentoAssociado != null){

			consultarRegistroAtendimentoActionForm.setNumeroRaAtual(String.valueOf(registroAtendimentoAssociado.getId()));
			consultarRegistroAtendimentoActionForm.setSituacaoRaAtual(situacaoRAAssociado.getDescricaoSituacao());

			RaMotivoReativacao raMotivoReativacao = registroAtendimentoAssociado.getRaMotivoReativacao();
			if(raMotivoReativacao != null){
				consultarRegistroAtendimentoActionForm.setIdMotivoReativacao(String.valueOf(raMotivoReativacao.getId()));
				consultarRegistroAtendimentoActionForm.setDescricaoMotivoReativacao(raMotivoReativacao.getDescricao());
			}

			Date dataRegistro = registroAtendimentoAssociado.getRegistroAtendimento();
			Date dataPrevista = registroAtendimentoAssociado.getDataPrevistaAtual();

			consultarRegistroAtendimentoActionForm.setDataReativacao(Util.formatarData(dataRegistro));
			consultarRegistroAtendimentoActionForm.setHoraReativacao(Util.formatarHoraSemSegundos(dataRegistro));

			consultarRegistroAtendimentoActionForm.setDataPrevistaRaAtual(Util.formatarData(dataPrevista) + " "
							+ Util.formatarHoraSemSegundos(dataPrevista));

			// Caso de Uso [UC0421]
			UnidadeOrganizacional unidadeReativacao = fachada.obterUnidadeAtendimentoRA(registroAtendimentoAssociado.getId());

			if(unidadeReativacao != null){
				consultarRegistroAtendimentoActionForm.setIdUnidadeReativacao(String.valueOf(unidadeReativacao.getId()));
				consultarRegistroAtendimentoActionForm.setUnidadeReativacao(unidadeReativacao.getDescricao());
			}

			// Caso de Uso [UC0418]
			UnidadeOrganizacional unidadeRAAtual = fachada.obterUnidadeAtualRA(registroAtendimentoAssociado.getId());

			if(unidadeRAAtual != null){
				consultarRegistroAtendimentoActionForm.setIdUnidadeRaAtual(String.valueOf(unidadeRAAtual.getId()));
				consultarRegistroAtendimentoActionForm.setUnidadeRaAtual(unidadeRAAtual.getDescricao());
			}

			consultarRegistroAtendimentoActionForm.setObservacaoReativacao(registroAtendimentoAssociado.getObservacao());
		}

		// Dados do encerramento

		// Caso o registro atendimento seja encerrado,
		// exibir os dados do encerramento
		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = registroAtendimento.getAtendimentoMotivoEncerramento();

		if(atendimentoMotivoEncerramento != null){

			consultarRegistroAtendimentoActionForm.setIdMotivoEncerramento(String.valueOf(atendimentoMotivoEncerramento.getId()));
			consultarRegistroAtendimentoActionForm.setMotivoEncerramento(atendimentoMotivoEncerramento.getDescricao());

			if(codigoAssociado == RegistroAtendimento.CODIGO_ASSOCIADO_RA_REFERENCIA && registroAtendimentoAssociado != null){

				consultarRegistroAtendimentoActionForm.setNumeroRaReferencia(String.valueOf(registroAtendimentoAssociado.getId()));

				// Caso de Uso [UC0420]
				consultarRegistroAtendimentoActionForm.setSituacaoRaReferencia(situacaoRAAssociado.getDescricaoSituacao());

			}

			// Caso de Uso [UC0434]
			UnidadeOrganizacional unidadeEncerramento = fachada.obterUnidadeEncerramentoRA(registroAtendimento.getId());

			if(unidadeEncerramento != null){

				consultarRegistroAtendimentoActionForm.setIdUnidadeEncerramento(String.valueOf(unidadeEncerramento.getId()));
				consultarRegistroAtendimentoActionForm.setUnidadeEncerramento(unidadeEncerramento.getDescricao());

				RegistroAtendimentoUnidade registroAtendimentoUnidade = this.consultarRegistroAtendimentoUnidadeEncerramento(
								registroAtendimento.getId(), unidadeEncerramento.getId());

				Usuario usuario = registroAtendimentoUnidade.getUsuario();
				if(usuario != null){

					consultarRegistroAtendimentoActionForm.setIdUsuarioEncerramento(String.valueOf(usuario.getId()));
					consultarRegistroAtendimentoActionForm.setUsuarioEncerramento(usuario.getNomeUsuario());
				}
			}

			Date dataEncerramento = registroAtendimento.getDataEncerramento();

			consultarRegistroAtendimentoActionForm.setDataEncerramento(Util.formatarData(dataEncerramento));
			consultarRegistroAtendimentoActionForm.setHoraEncerramento(Util.formatarHoraSemSegundos(dataEncerramento));

			consultarRegistroAtendimentoActionForm.setDataPrevistaEncerramento(Util.formatarData(registroAtendimento.getDataPrevistaAtual()
							+ " " + Util.formatarHoraSemSegundos(registroAtendimento.getDataPrevistaAtual())));

			consultarRegistroAtendimentoActionForm.setParecerEncerramento(registroAtendimento.getParecerEncerramento());

		}

		// Colocado por Raphael Rossiter em 26/10/2006
		consultarRegistroAtendimentoActionForm.setNumeroRA("");
		httpServletRequest.setAttribute("nomeCampo", "numeroRA");

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
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("orgaoExpedidorRg");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("unidadeFederacaoRG");

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

		filtroRegistroAtendimentoUnidade.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoUnidade.USUARIO);

		colecaoRegistroAtendimentoUnidade = fachada.pesquisar(filtroRegistroAtendimentoUnidade, RegistroAtendimentoUnidade.class.getName());

		if(colecaoRegistroAtendimentoUnidade != null && !colecaoRegistroAtendimentoUnidade.isEmpty()){
			retorno = (RegistroAtendimentoUnidade) Util.retonarObjetoDeColecao(colecaoRegistroAtendimentoUnidade);

		}

		return retorno;
	}

	/**
	 * Consulta o Registro Atendimento Unidade pelo id da RA
	 * 
	 * @author Carlos Chrystian
	 * @created 12/04/2012
	 */
	private RegistroAtendimentoUnidade consultarRegistroAtendimentoUnidadeEncerramento(Integer idRA, Integer idUnidade){

		RegistroAtendimentoUnidade retorno = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoRegistroAtendimentoUnidade = null;

		FiltroRegistroAtendimentoUnidade filtroRegistroAtendimentoUnidade = new FiltroRegistroAtendimentoUnidade();

		filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimentoUnidade.REGISTRO_ATENDIMENTO_ID,
						idRA));

		filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(
						FiltroRegistroAtendimentoUnidade.UNIDADE_ORGANIZACIONAL_ID, idUnidade));
		filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(
						FiltroRegistroAtendimentoUnidade.ATENDIMENTO_RELACAO_TIPO_ID, AtendimentoRelacaoTipo.ENCERRAR));

		filtroRegistroAtendimentoUnidade.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoUnidade.USUARIO);

		colecaoRegistroAtendimentoUnidade = fachada.pesquisar(filtroRegistroAtendimentoUnidade, RegistroAtendimentoUnidade.class.getName());

		if(colecaoRegistroAtendimentoUnidade != null && !colecaoRegistroAtendimentoUnidade.isEmpty()){
			retorno = (RegistroAtendimentoUnidade) Util.retonarObjetoDeColecao(colecaoRegistroAtendimentoUnidade);

		}

		return retorno;
	}

	private Integer obterIndexRAColecao(Collection colecaoRAHelper, Integer idRA){

		int i = 0;
		Iterator iterator = colecaoRAHelper.iterator();
		RegistroAtendimento registroAtendimento = null;
		while(iterator.hasNext()){

			registroAtendimento = (RegistroAtendimento) iterator.next();

			if(!registroAtendimento.getId().equals(Integer.valueOf(idRA))){
				i = i + 1;
			}else{
				break;
			}
		}

		return i;
	}

}
