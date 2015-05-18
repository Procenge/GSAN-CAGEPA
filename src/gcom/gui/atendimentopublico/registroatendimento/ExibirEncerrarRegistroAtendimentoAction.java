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
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.geografico.BairroArea;
import gcom.cadastro.imovel.Imovel;
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ExibirTramitarRegistroAtendimentoAction
 * 
 * @author Leonardo Regis
 * @date 16/08/2006
 */
public class ExibirEncerrarRegistroAtendimentoAction
				extends GcomAction {

	/**
	 * Exibe a Tela para Encerrar o RA
	 * 
	 * @author Leonardo Regis
	 * @date 25/08/2006
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("encerrarRegistroAtendimento");
		HttpSession sessao = httpServletRequest.getSession(false);
		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		EncerrarRegistroAtendimentoActionForm encerrarRegistroAtendimentoActionForm = (EncerrarRegistroAtendimentoActionForm) actionForm;
		// Reseta Tramitação
		if(encerrarRegistroAtendimentoActionForm.getResetarEncerramento().equalsIgnoreCase("true")){
			encerrarRegistroAtendimentoActionForm.resetarEncerramento();
		}

		Fachada fachada = Fachada.getInstancia();

		ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper = null;

		if(encerrarRegistroAtendimentoActionForm.getNumeroRA() != null){
			registroAtendimentoHelper = fachada.obterDadosRegistroAtendimento(new Integer(encerrarRegistroAtendimentoActionForm
							.getNumeroRA()));
		}else{
			registroAtendimentoHelper = fachada.obterDadosRegistroAtendimento(new Integer(httpServletRequest.getAttribute("numeroRA")
							.toString()));
		}

		setUsuarioRegistro(encerrarRegistroAtendimentoActionForm, usuario);
		setDadosRA(encerrarRegistroAtendimentoActionForm, registroAtendimentoHelper);
		setDadosSolicitante(encerrarRegistroAtendimentoActionForm, registroAtendimentoHelper);
		setDadosEnderecoOcorrencia(encerrarRegistroAtendimentoActionForm, registroAtendimentoHelper);
		setUnidades(encerrarRegistroAtendimentoActionForm, registroAtendimentoHelper);
		encerrarRegistroAtendimentoActionForm.setDataEncerramento(Util.formatarData(new Date()));
		encerrarRegistroAtendimentoActionForm.setHoraEncerramento(Util.formatarHoraSemSegundos(new Date()));
		// Valida Pré-Encerramento
		// fachada.validarPreEncerramentoRASemTarifaSocial(registroAtendimentoHelper.getRegistroAtendimento(),
		// usuario, new Short(
		// encerrarRegistroAtendimentoActionForm.getIndicadorAutorizacaoManutencaoRA()));
		getMotivoEncerramentoCollection(encerrarRegistroAtendimentoActionForm, sessao);
		// Número do RA de Referência
		if(encerrarRegistroAtendimentoActionForm.getNumeroRAReferencia() != null
						&& !encerrarRegistroAtendimentoActionForm.getNumeroRAReferencia().equals("")){
			getNumeroRAReferencia(encerrarRegistroAtendimentoActionForm, sessao);
		}
		// Valida Indicador Duplicidade
		if(encerrarRegistroAtendimentoActionForm.getMotivoEncerramentoId() != null
						&& !encerrarRegistroAtendimentoActionForm.getMotivoEncerramentoId().equals("")){
			if(!encerrarRegistroAtendimentoActionForm.getMotivoEncerramentoId().equals("-1")){
				getIndicadorDuplicidade(encerrarRegistroAtendimentoActionForm, sessao);
			}else{
				encerrarRegistroAtendimentoActionForm.setIndicadorDuplicidade("2");
			}
		}else{
			encerrarRegistroAtendimentoActionForm.setIndicadorDuplicidade("2");
		}
		return retorno;
	}

	/**
	 * Carrega Usuário de Registro
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param form
	 */
	private void setUsuarioRegistro(EncerrarRegistroAtendimentoActionForm form, Usuario usuario){

		form.setUsuarioRegistroId(usuario.getId() + "");
		form.setUsuarioRegistroNome(usuario.getNomeUsuario());
		form.setUsuarioRegistroUnidade(usuario.getUnidadeOrganizacional().getId() + "");
		form.setUsuarioRegistroUnidadeIndicadorTarifaSocial(usuario.getUnidadeOrganizacional().getIndicadorTarifaSocial() + "");
		form.setUsuarioRegistroUnidadeIndicadorCentralAtendimento(usuario.getUnidadeOrganizacional().getIndicadorCentralAtendimento() + "");
	}

	/**
	 * Carrega Unidades (Atendimento e Atual)
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param form
	 * @param registroAtendimentoHelper
	 */
	private void setUnidades(EncerrarRegistroAtendimentoActionForm form, ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper){

		UnidadeOrganizacional unidadeAtendimento = registroAtendimentoHelper.getUnidadeAtendimento();
		if(unidadeAtendimento != null){
			form.setUnidadeAtendimentoId("" + unidadeAtendimento.getId());
			form.setUnidadeAtendimentoDescricao(unidadeAtendimento.getDescricao());
		}
		UnidadeOrganizacional unidadeAtual = registroAtendimentoHelper.getUnidadeAtual();
		Fachada fachada = Fachada.getInstancia();
		if(unidadeAtual != null){
			form.setUnidadeAtualId("" + unidadeAtual.getId());
			form.setIndicadorAutorizacaoManutencaoRA(fachada.obterIndicadorAutorizacaoManutencaoRA(unidadeAtual.getId(), new Integer(form
							.getUsuarioRegistroId()))
							+ "");
			form.setUnidadeAtualDescricao(unidadeAtual.getDescricao());
			if(unidadeAtual.getUnidadeTipo() != null){
				form.setUnidadeAtualCodigoTipo(unidadeAtual.getUnidadeTipo().getCodigoTipo());
			}
			if(unidadeAtual.getUnidadeCentralizadora() != null){
				form.setUnidadeAtualIdCentralizadora(unidadeAtual.getUnidadeCentralizadora().getId() + "");
			}
		}
	}

	/**
	 * Carrega Dados do RA
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param form
	 * @param registroAtendimentoHelper
	 */
	private void setDadosRA(EncerrarRegistroAtendimentoActionForm form, ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper){

		RegistroAtendimento registroAtendimento = registroAtendimentoHelper.getRegistroAtendimento();
		// Dados Gerais do Registro de Atendimento
		form.setDataConcorrenciaRA(registroAtendimento.getUltimaAlteracao());
		form.setNumeroRA("" + registroAtendimento.getId());
		form.setSituacaoRA(registroAtendimentoHelper.getDescricaoSituacaoRA());
		form.setCodigoSituacaoRA(registroAtendimento.getCodigoSituacao() + "");
		if(registroAtendimentoHelper.getRAAssociado() != null){
			form.setNumeroRaAssociado("" + registroAtendimentoHelper.getRAAssociado().getId());
			form.setSituacaoRaAssociado(registroAtendimentoHelper.getDescricaoSituacaoRAAssociado());
		}
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = registroAtendimento.getSolicitacaoTipoEspecificacao();
		if(solicitacaoTipoEspecificacao != null){
			if(solicitacaoTipoEspecificacao.getSolicitacaoTipo() != null){
				form.setTipoSolicitacaoId(solicitacaoTipoEspecificacao.getSolicitacaoTipo().getId() + "");
				form.setTipoSolicitacaoDescricao(solicitacaoTipoEspecificacao.getSolicitacaoTipo().getDescricao());
				form.setTipoSolicitacaoIndicadorTarifaSocial(solicitacaoTipoEspecificacao.getSolicitacaoTipo().getIndicadorTarifaSocial()
								+ "");
			}
			form.setEspecificacaoId(solicitacaoTipoEspecificacao.getId() + "");
			form.setEspecificacaoDescricao(solicitacaoTipoEspecificacao.getDescricao());
			form.setEspecificacaoIndicadorParecer(solicitacaoTipoEspecificacao.getIndicadorParecerEncerramento() + "");
		}
		if(registroAtendimento.getMeioSolicitacao() != null){
			form.setMeioSolicitacaoId(registroAtendimento.getMeioSolicitacao().getId() + "");
			form.setMeioSolicitacaoDescricao(registroAtendimento.getMeioSolicitacao().getDescricao());
		}
		// Imovel
		Imovel imovel = registroAtendimento.getImovel();
		if(imovel != null){
			form.setMatriculaImovel("" + imovel.getId());
			form.setInscricaoImovel(imovel.getInscricaoFormatada());
		}
		Date dataAtendimento = registroAtendimento.getRegistroAtendimento();
		form.setDataAtendimento(Util.formatarData(dataAtendimento));
		form.setHoraAtendimento(Util.formatarHoraSemData(dataAtendimento));
		form.setDataPrevista(Util.formatarData(registroAtendimento.getDataPrevistaAtual()) + " "
						+ Util.formatarHoraSemSegundos(registroAtendimento.getDataPrevistaAtual()));
		// Encerramento
		setDadosEncerramento(form, registroAtendimento);
	}

	/**
	 * Carrega Dados do RA
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param form
	 * @param registroAtendimento
	 */
	private void setDadosEncerramento(EncerrarRegistroAtendimentoActionForm form, RegistroAtendimento registroAtendimento){

		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = registroAtendimento.getAtendimentoMotivoEncerramento();
		if(atendimentoMotivoEncerramento != null){
			form.setIdMotivoEncerramento("" + atendimentoMotivoEncerramento.getId());
			form.setMotivoEncerramento(atendimentoMotivoEncerramento.getDescricao());
			Date dataEncerramento = registroAtendimento.getDataEncerramento();
			form.setDataEncerramentoRA(Util.formatarData(dataEncerramento));
			form.setDataEncerramento(Util.formatarData(new Date()));
			form.setHoraEncerramento(Util.formatarHoraSemSegundos(new Date()));
		}
	}

	/**
	 * Carrega Dados do Solicitante
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param form
	 * @param registroAtendimentoHelper
	 */
	private void setDadosSolicitante(EncerrarRegistroAtendimentoActionForm form,
					ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper){

		// Dados do Solicitante
		RegistroAtendimentoSolicitante registroAtendimentoSolicitante = registroAtendimentoHelper.getSolicitante();
		if(registroAtendimentoSolicitante != null){
			Cliente cliente = registroAtendimentoSolicitante.getCliente();
			UnidadeOrganizacional unidadeSolicitante = registroAtendimentoSolicitante.getUnidadeOrganizacional();
			// Caso o principal solicitante do registro de atendimento seja um cliente
			// obter os dados do cliente
			if(cliente != null){
				form.setIdClienteSolicitante("" + cliente.getId());
				form.setClienteSolicitante(cliente.getNome());
				// Caso o principal solicitante do registro de atendimento seja uma unidade
				// obter os dados da unidade
			}else if(unidadeSolicitante != null){
				form.setIdUnidadeSolicitante("" + unidadeSolicitante.getId());
				form.setUnidadeSolicitante(unidadeSolicitante.getDescricao());
				// Caso o principal solicitante do registro de atendimento não seja um cliente, nem
				// uma unidade
				// obter os dados do solicitante
			}else{
				form.setNomeSolicitante(registroAtendimentoSolicitante.getSolicitante());
			}
		}
	}

	/**
	 * Carrega Dados do Endereço de Ocorrência
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param form
	 * @param registroAtendimentoHelper
	 */
	private void setDadosEnderecoOcorrencia(EncerrarRegistroAtendimentoActionForm form,
					ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper){

		String enderecoOcorrencia = registroAtendimentoHelper.getEnderecoOcorrencia();
		form.setEnderecoOcorrencia(enderecoOcorrencia);
		form.setPontoReferencia(registroAtendimentoHelper.getRegistroAtendimento().getPontoReferencia());

		// Caso o registro atendimento esteja associado a uma área de bairro,
		// obter os dados da área do bairro
		BairroArea bairroArea = registroAtendimentoHelper.getRegistroAtendimento().getBairroArea();
		if(bairroArea != null){
			form.setBairroId("" + bairroArea.getBairro().getId());
			form.setBairroDescricao(bairroArea.getBairro().getNome());
			form.setAreaBairroId("" + bairroArea.getId());
			form.setAreaBairroDescricao(bairroArea.getNome());
		}
		Localidade localidade = registroAtendimentoHelper.getRegistroAtendimento().getLocalidade();
		if(localidade != null){
			form.setLocalidadeId("" + localidade.getId());
			form.setLocalidadeDescricao(localidade.getDescricao());
		}
		SetorComercial setorComercial = registroAtendimentoHelper.getRegistroAtendimento().getSetorComercial();
		if(setorComercial != null){
			form.setSetorComercialId("" + setorComercial.getId());
			form.setSetorComercialDescricao(setorComercial.getDescricao());
		}
		Quadra quadra = registroAtendimentoHelper.getRegistroAtendimento().getQuadra();
		if(quadra != null){
			form.setQuadraId("" + quadra.getId());
			form.setQuadraDescricao("" + quadra.getNumeroQuadra());
		}
		DivisaoEsgoto divisaoEsgoto = registroAtendimentoHelper.getRegistroAtendimento().getDivisaoEsgoto();
		if(divisaoEsgoto != null){
			form.setDivisaoEsgotoId("" + divisaoEsgoto.getId());
			form.setDivisaoEsgotoDescricao(divisaoEsgoto.getDescricao());
		}
	}

	/**
	 * Carrega coleção de motivo de encerramento.
	 * 
	 * @author Leonardo Regis
	 * @date 25/08/2006
	 * @param sessao
	 */
	private void getMotivoEncerramentoCollection(EncerrarRegistroAtendimentoActionForm form, HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();
		// Filtra Motivo de Encerramento
		FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();
		filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(FiltroAtendimentoMotivoEncerramento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroAtendimentoMotivoEncerramento.setCampoOrderBy(FiltroAtendimentoMotivoEncerramento.DESCRICAO);

		Collection colecaoAtendimentoMotivoEncerramento = fachada.pesquisar(filtroAtendimentoMotivoEncerramento,
						AtendimentoMotivoEncerramento.class.getName());
		if(colecaoAtendimentoMotivoEncerramento != null && !colecaoAtendimentoMotivoEncerramento.isEmpty()){
			sessao.setAttribute("colecaoMotivoEncerramento", colecaoAtendimentoMotivoEncerramento);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Motivo do Encerramento");
		}
	}

	/**
	 * Recupera RA de Referência
	 * 
	 * @author Leonardo Regis
	 * @date 25/08/2006
	 * @param form
	 * @param sessao
	 */
	private void getNumeroRAReferencia(EncerrarRegistroAtendimentoActionForm form, HttpSession sessao){

		// [F0003] Valida RA de Referência
		// if (isValidateObject(form)) {
		Fachada fachada = Fachada.getInstancia();
		RegistroAtendimento registroAtendimento = fachada.validarRAReferencia(new Integer(form.getNumeroRA()), new Integer(form
						.getNumeroRAReferencia()));
		if(registroAtendimento != null){
			form.setNumeroRAReferenciaRetorno(registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao());
			form.setValidaNumeroRAReferencia("false");
			sessao.setAttribute("raEncontrada", "true");
		}else{
			sessao.removeAttribute("raEncontrada");
			form.setNumeroRAReferencia("");
			form.setNumeroRAReferenciaRetorno("RA - Registro de Atendimento Inexistente");
		}
		// }
	}

	/**
	 * Valida Objeto com <Enter> da tela
	 * 
	 * @author Leonardo Regis
	 * @date 24/08/2006
	 * @return está validando algum objeto através do enter?
	 */
	/*
	 * private boolean isValidateObject(EncerrarRegistroAtendimentoActionForm form) {
	 * boolean toReturn = false;
	 * if (form.getValidaNumeroRAReferencia().equalsIgnoreCase("true")) {
	 * toReturn = true;
	 * }
	 * return toReturn;
	 * }
	 */

	/**
	 * Seta indicador de duplicidade.
	 * 
	 * @author Leonardo Regis
	 * @date 28/09/2006
	 * @param form
	 * @param sessao
	 */
	private void getIndicadorDuplicidade(EncerrarRegistroAtendimentoActionForm form, HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();
		// Filtra Motivo de Encerramento
		FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();
		filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(FiltroAtendimentoMotivoEncerramento.ID, form
						.getMotivoEncerramentoId()));
		filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(FiltroAtendimentoMotivoEncerramento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroAtendimentoMotivoEncerramento.setCampoOrderBy(FiltroAtendimentoMotivoEncerramento.DESCRICAO);

		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = null;
		Collection colecaoAtendimentoMotivoEncerramento = fachada.pesquisar(filtroAtendimentoMotivoEncerramento,
						AtendimentoMotivoEncerramento.class.getName());
		if(colecaoAtendimentoMotivoEncerramento != null && !colecaoAtendimentoMotivoEncerramento.isEmpty()){
			atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento) Util
							.retonarObjetoDeColecao(colecaoAtendimentoMotivoEncerramento);
			form.setIndicadorDuplicidade(atendimentoMotivoEncerramento.getIndicadorDuplicidade() + "");
		}
	}
}