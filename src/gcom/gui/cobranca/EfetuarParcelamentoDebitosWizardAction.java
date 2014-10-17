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

package gcom.gui.cobranca;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.parcelamento.ParcelamentoPerfil;
import gcom.cobranca.parcelamento.ParcelamentoSituacaoEspecial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.WizardAction;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * [UC0214] - Efetuar Parcelamento de D�bitos
 * Permite Efetuar Parcelamento de D�bitos de um im�vel
 * Navega��o das Abas e Conclus�o
 * 
 * @author Rodrigo Avellar/Roberta Costa
 * @created 24/01/2006
 */
public class EfetuarParcelamentoDebitosWizardAction
				extends WizardAction {

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da
	 * aba 1
	 */
	public ActionForward exibirProcesso1Action(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ExibirEfetuarParcelamentoDebitosProcesso1Action().execute(actionMapping, actionForm, httpServletRequest,
						httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da
	 * aba 1
	 */
	public ActionForward exibirEfetuarParcelamentoDebitosProcesso1Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ExibirEfetuarParcelamentoDebitosProcesso1Action().execute(actionMapping, actionForm, httpServletRequest,
						httpServletResponse);

	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da
	 * aba 2
	 */
	public ActionForward exibirProcesso2Action(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ExibirEfetuarParcelamentoDebitosProcesso2Action().execute(actionMapping, actionForm, httpServletRequest,
						httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da
	 * aba 2
	 */
	public ActionForward exibirEfetuarParcelamentoDebitosProcesso2Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ExibirEfetuarParcelamentoDebitosProcesso2Action().execute(actionMapping, actionForm, httpServletRequest,
						httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da
	 * aba 3
	 */
	public ActionForward exibirProcesso3Action(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ExibirEfetuarParcelamentoDebitosProcesso3Action().execute(actionMapping, actionForm, httpServletRequest,
						httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da
	 * aba 3
	 */
	public ActionForward exibirEfetuarParcelamentoDebitosProcesso3Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ExibirEfetuarParcelamentoDebitosProcesso3Action().execute(actionMapping, actionForm, httpServletRequest,
						httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da
	 * aba 4
	 */
	public ActionForward exibirProcesso4Action(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ExibirEfetuarParcelamentoDebitosProcesso4Action().execute(actionMapping, actionForm, httpServletRequest,
						httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da
	 * aba 4
	 */
	public ActionForward exibirEfetuarParcelamentoDebitosProcesso4Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ExibirEfetuarParcelamentoDebitosProcesso4Action().execute(actionMapping, actionForm, httpServletRequest,
						httpServletResponse);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de
	 * neg�cio) da aba 1
	 */
	public ActionForward processarProcesso1Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		new ProcessarEfetuarParcelamentoDebitosProcesso1Action()
						.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de
	 * neg�cio) da aba 1
	 */
	public ActionForward processarEfetuarParcelamentoDebitosProcesso1Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		new ProcessarEfetuarParcelamentoDebitosProcesso1Action()
						.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de
	 * neg�cio) da aba 2
	 */
	public ActionForward processarProcesso2Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		new ProcessarEfetuarParcelamentoDebitosProcesso2Action()
						.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de
	 * neg�cio) da aba 2
	 */
	public ActionForward processarEfetuarParcelamentoDebitosProcesso2Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		new ProcessarEfetuarParcelamentoDebitosProcesso2Action()
						.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de
	 * neg�cio) da aba 3
	 */
	public ActionForward processarProcesso3Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		new ProcessarEfetuarParcelamentoDebitosProcesso3Action()
						.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de
	 * neg�cio) da aba 3
	 */
	public ActionForward processarEfetuarParcelamentoDebitosProcesso3Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		new ProcessarEfetuarParcelamentoDebitosProcesso3Action()
						.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de
	 * neg�cio) da aba 4
	 */
	public ActionForward processarProcesso4Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		new ProcessarEfetuarParcelamentoDebitosProcesso4Action()
						.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de
	 * neg�cio) da aba 4
	 */
	public ActionForward processarEfetuarParcelamentoDebitosProcesso4Action(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		new ProcessarEfetuarParcelamentoDebitosProcesso4Action()
						.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);
	}

	public ActionForward concluirProcessoAction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO);

		return new ConcluirEfetuarParcelamentoDebitosAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	public static boolean isCobrancaBancaria(String idMotivoRevisao){

		String[] idsMotivoRevisao = null;
		try{
			idsMotivoRevisao = ((String) ParametroCobranca.P_MOTIVO_REVISAO_COBRANCA_BANCARIA.executar(ExecutorParametrosCobranca
							.getInstancia())).split(",");
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(idsMotivoRevisao != null){

			if(idMotivoRevisao != null){
				for(int i = 0; i < idsMotivoRevisao.length; i++){
					if(idsMotivoRevisao[i].equals(idMotivoRevisao)){
						return true;
					}
				}
			}

		}
		return false;
	}

	public static ResolucaoDiretoria obterResolucaoDiretoria(Fachada fachada, DynaActionForm form){

		String resolucaoDiretoria = (String) (form.get("resolucaoDiretoria"));

		ResolucaoDiretoria rd = null;

		if(!resolucaoDiretoria.equals("") && resolucaoDiretoria != null){

			FiltroResolucaoDiretoria filtroRD = new FiltroResolucaoDiretoria();
			filtroRD.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.CODIGO, Util.obterInteger(resolucaoDiretoria)));
			Collection<ResolucaoDiretoria> collRD = fachada.pesquisar(filtroRD, ResolucaoDiretoria.class.getName());

			rd = (ResolucaoDiretoria) Util.retonarObjetoDeColecao(collRD);

		}

		return rd;
	}

	/**
	 * [FS0035] - Verificar RD com restri��o de uso por localidade/per�odo de d�bito para o im�vel.
	 * 
	 * @author Yara Souza
	 * @date 13/01/2012
	 * @param rd
	 * @param idImovel
	 * @param inicioIntervaloParcelamento
	 * @param fimIntervaloParcelamento
	 * @param fachada
	 * @param sessao
	 */

	public static void verificarRDComRestricao(ResolucaoDiretoria rd, String idImovel, String inicioIntervaloParcelamento,
					String fimIntervaloParcelamento, Fachada fachada, HttpSession sessao, DynaActionForm form){

		String[] listaParametros = null;

		Collection<ParcelamentoSituacaoEspecial> collecaoParcelamentoSituacaoEspecial = fachada.verificarRDComRestricao(rd.getId());

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.IMOVEL_PERFIL);

		Collection collImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
		Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(collImovel);

		Localidade localidade = imovel.getLocalidade();
		Integer idLocalidade = localidade.getId();

		// 1. Caso o uso da RD selecionada n�o esteja restrito a determinada localidade e per�odo de
		// d�bito (n�o existe ocorr�ncia na tabela PARCELAMENTO_SITUACAO_ESPECIAL com
		// RDIR_ID=RDIR_ID da RD selecionada)
		if(collecaoParcelamentoSituacaoEspecial == null || collecaoParcelamentoSituacaoEspecial.isEmpty()){

			Integer intervaloParcelamentoInicio = new Integer(Util.formatarMesAnoParaAnoMesSemBarra((String) form
							.get("inicioIntervaloParcelamento")));
			Integer intervaloParcelamentoFim = new Integer(Util.formatarMesAnoParaAnoMesSemBarra((String) form
							.get("fimIntervaloParcelamento")));

			Collection<ParcelamentoSituacaoEspecial> collParcelamentoPerfilSitEspecial = fachada
							.pesquisarParcelamentoSituacaoEspecialPorLocalidade(idLocalidade, intervaloParcelamentoInicio,
											intervaloParcelamentoFim);

			if(collParcelamentoPerfilSitEspecial != null && !collParcelamentoPerfilSitEspecial.isEmpty()){
				// 1.2. Caso o parcelamento para a localidade e o per�odo de d�bito do im�vel
				// informado esteja restrito a determinada RD

				ParcelamentoSituacaoEspecial parcelamentoPerfilSitEspecial = (ParcelamentoSituacaoEspecial) Util
								.retonarObjetoDeColecao(collParcelamentoPerfilSitEspecial);

				ResolucaoDiretoria resolucaoDiretoria = parcelamentoPerfilSitEspecial.getResolucaoDiretoria();

				if(resolucaoDiretoria != null){
					Date dataVigenciaInicio = resolucaoDiretoria.getDataVigenciaInicio();
					Date dataVigenciaFim = resolucaoDiretoria.getDataVigenciaFim();

					// Verifica se a RD est� vigente
					if((dataVigenciaInicio != null && Util.compararDiaMesAno(dataVigenciaInicio, new Date()) != -1)
									&& (dataVigenciaFim == null || (dataVigenciaFim != null && Util.compararDiaMesAno(dataVigenciaInicio,
													new Date()) != 1))){
						listaParametros = new String[4];

						listaParametros[0] = localidade.getDescricao();
						listaParametros[1] = form.get("inicioIntervaloParcelamento") + " � " + form.get("fimIntervaloParcelamento");
						listaParametros[2] = imovel.getId().toString();
						listaParametros[3] = resolucaoDiretoria.getDescricaoAssunto();

						throw new ActionServletException("atencao.parcelamento.localidade.restrito.rd", null, listaParametros);
					}
				}
			}else{
				if(rd != null && idImovel != null){
					ParcelamentoPerfil parcelamentoPerfil = obterPerfilParcelamentoImovel(rd, idImovel, fachada, sessao, form);

					if(parcelamentoPerfil != null){
						Integer anoMesReferenciaLimiteInferior = parcelamentoPerfil.getAnoMesReferenciaLimiteInferior();
						Integer anoMesReferenciaLimiteSuperior = parcelamentoPerfil.getAnoMesReferenciaLimiteSuperior();

						if(anoMesReferenciaLimiteInferior != null && anoMesReferenciaLimiteSuperior != null){
							Short indicadorAlteracaoPeriodoParcelamento = ConstantesSistema.NAO;
							sessao.setAttribute("indicadorAlteracaoPeriodoParcelamento", indicadorAlteracaoPeriodoParcelamento);

							form.set("inicioIntervaloParcelamento", Util
											.formatarAnoMesSemBarraParaMesAnoComBarra(anoMesReferenciaLimiteInferior));
							form.set("fimIntervaloParcelamento", Util
											.formatarAnoMesSemBarraParaMesAnoComBarra(anoMesReferenciaLimiteSuperior));
						}
					}
				}
			}
		}else{
			// 1.3. Caso contr�rio, ou seja, o uso da RD selecionada esteja restrito a
			// determinada localidade e per�odo de d�bito
			// (existe ocorr�ncia na tabela PARCELAMENTO_SITUACAO_ESPECIAL com RDIR_ID=RDIR_ID
			// da RD selecionada)

			ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = null;

			Localidade localidadeAux = null;
			Integer idLocalidadeAux = null;

			boolean existeLocalidadeIgual = false;

			for(ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecialAux : collecaoParcelamentoSituacaoEspecial){
				parcelamentoSituacaoEspecial = parcelamentoSituacaoEspecialAux;

				localidadeAux = parcelamentoSituacaoEspecialAux.getLocalidade();
				idLocalidadeAux = localidadeAux.getId();

				if(idLocalidadeAux.equals(idLocalidade)){
					existeLocalidadeIgual = true;
					break;
				}
			}

			// 1.3.1. Caso a localidade da RD (LOCA_ID da tabela PARCELAMENTO_SITUACAO_ESPECIAL)
			// seja diferente da localidade do im�vel.
			if(!existeLocalidadeIgual){
				ResolucaoDiretoria resolucaoDiretoria = parcelamentoSituacaoEspecial.getResolucaoDiretoria();

				listaParametros = new String[2];
				listaParametros[0] = resolucaoDiretoria.getDescricaoAssunto();
				listaParametros[1] = localidade.getDescricao();

				throw new ActionServletException("atencao.parcelamento.rd.restrito.localidade", null, listaParametros);
			}else{
				// 1.3.2.1. Atribuir o per�odo de d�bito da restri��o (PCSE_AMREFERENCIADEBITOINICIO
				// e PCSE_AMREFERENCIADEBITOFIM da tabela PARCELAMENTO_SITUACAO_ESPECIAL) ao
				// intervalo do parcelamento (M�s/Ano de refer�ncia inicial e M�s/Ano de refer�ncia
				// final ).

				Integer inicioIntervaloParcelamentoRd = parcelamentoSituacaoEspecial.getAnoMesReferenciaDebitoInicio();
				Integer fimIntervaloParcelamentoRd = parcelamentoSituacaoEspecial.getAnoMesReferenciaDebitoFim();

				// 1.3.2.2. Desabilitar o campo �Intervalo do parcelamento�.
				Short indicadorAlteracaoPeriodoParcelamento = ConstantesSistema.NAO;
				sessao.setAttribute("indicadorAlteracaoPeriodoParcelamento", indicadorAlteracaoPeriodoParcelamento);
				form.set("inicioIntervaloParcelamento", Util.formatarAnoMesSemBarraParaMesAnoComBarra(inicioIntervaloParcelamentoRd));
				form.set("fimIntervaloParcelamento", Util.formatarAnoMesSemBarraParaMesAnoComBarra(fimIntervaloParcelamentoRd));
			}
		}
	}

	// [FS0011] - Verificar Permiss�o do Usu�rio para Parcelar SEM Acr�scimos
	public static ParcelamentoPerfil obterPerfilParcelamentoImovel(ResolucaoDiretoria rd, String idImovel, Fachada fachada,
					HttpSession sessao, DynaActionForm form){

		ImovelSituacao imovelSituacao = null;
		// 1. Obtem a situa��o do imovel
		Integer situacaoAguaId = Integer.valueOf((String) (form.get("situacaoAguaId")));
		Integer situacaoEsgotoId = Integer.valueOf((String) (form.get("situacaoEsgotoId")));

		// [SB0004] � Verificar Situa��o do Im�vel e Perfil Parcelamento.
		// Condi��o 1
		if(situacaoAguaId != null && situacaoEsgotoId != null){

			imovelSituacao = fachada.obterSituacaoImovel(situacaoAguaId, situacaoEsgotoId);

			// Condi��o 2
			if(imovelSituacao == null){
				imovelSituacao = fachada.obterSituacaoImovel(situacaoAguaId, null);
			}
		}

		// [FS004] Verificar exist�ncia da situa��o do im�vel
		if(imovelSituacao == null){
			throw new ActionServletException("atencao.nao.existe.situacao.imovel.correspondente.situacao.agua.esgoto");
		}

		sessao.setAttribute("imovelSituacao", imovelSituacao);

		// 2. Obtem o perfil do parcelamento para o im�vel
		Integer perfilImovelId = Integer.valueOf((String) (form.get("perfilImovel")));
		ParcelamentoPerfil parcelamentoPerfil = new ParcelamentoPerfil();
		if(imovelSituacao != null){
			// Pega a subcategoria do im�vel
			Imovel imovel = new Imovel();
			imovel.setId(Integer.valueOf(idImovel));
			Collection colecaoImovelSubCategoria = fachada.obterColecaoImovelSubcategorias(imovel, 1);
			Subcategoria subcategoria = null;
			if(colecaoImovelSubCategoria != null && !colecaoImovelSubCategoria.isEmpty()){
				Iterator iteretorImovelSubCategoria = colecaoImovelSubCategoria.iterator();
				int quantidadeEconomisas = 0;
				int maiorQuantidadeEconomisas = 0;
				while(iteretorImovelSubCategoria.hasNext()){
					ImovelSubcategoria imovelSubCategoria = (ImovelSubcategoria) iteretorImovelSubCategoria.next();
					quantidadeEconomisas = imovelSubCategoria.getQuantidadeEconomias();
					if(quantidadeEconomisas > maiorQuantidadeEconomisas){
						maiorQuantidadeEconomisas = quantidadeEconomisas;
						subcategoria = imovelSubCategoria.getComp_id().getSubcategoria();
					}
				}
			}
			Integer subcategoriaId = null;
			if(subcategoria != null){
				subcategoriaId = subcategoria.getId();
			}
			// Condi��o 1 - iper_id = iper_id do imovel e scat_id = scat_id do imovel
			parcelamentoPerfil = fachada.obterPerfilParcelamento(Integer.valueOf(idImovel), imovelSituacao.getImovelSituacaoTipo().getId(),
							perfilImovelId, subcategoriaId, rd.getId());
			// Condi��o 2 - iper_id = iper_id do imovel e scat_id = null do imovel
			if(parcelamentoPerfil == null){
				parcelamentoPerfil = fachada.obterPerfilParcelamento(Integer.valueOf(idImovel), imovelSituacao.getImovelSituacaoTipo()
								.getId(), perfilImovelId, null, rd.getId());
				// Condi��o 3 - iper_id = null do imovel e scat_id = scat_id do imovel
				if(parcelamentoPerfil == null){
					parcelamentoPerfil = fachada.obterPerfilParcelamento(Integer.valueOf(idImovel), imovelSituacao.getImovelSituacaoTipo()
									.getId(), null, subcategoriaId, rd.getId());
					// Condi��o 4 - iper_id = null do imovel e scat_id = null
					if(parcelamentoPerfil == null){
						parcelamentoPerfil = fachada.obterPerfilParcelamento(Integer.valueOf(idImovel), imovelSituacao
										.getImovelSituacaoTipo().getId(), null, null, rd.getId());
					}
				}
			}
		}
		// [FS005] Verificar exist�ncia do perfil de parcelamento
		if(parcelamentoPerfil == null){

			ActionServletException actionServletException = new ActionServletException(
							"atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
			actionServletException.setUrlBotaoVoltar("exibirEfetuarParcelamentoDebitosAction.do");
			throw actionServletException;
		}

		return parcelamentoPerfil;

	}

	public static Collection<Integer> getSitLigacaoAguaRestabelecimento() throws ControladorException{

		Collection<Integer> idLigacaoAguaSituacao = new ArrayList<Integer>();


		String paramSitLeigacaoAguaRestabelecimento = ParametroCobranca.P_SIT_LIGACAO_AGUA_RESTABELECIMENTO.executar();

		for(String cdSitLiacagoAgua : paramSitLeigacaoAguaRestabelecimento.split(",")){
			try{
				LigacaoAguaSituacao ligacaoAguaSituacao = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(cdSitLiacagoAgua,
								LigacaoAguaSituacao.class);
				if(ligacaoAguaSituacao != null){
					idLigacaoAguaSituacao.add(ligacaoAguaSituacao.getId());
				}

			}catch(ErroRepositorioException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return idLigacaoAguaSituacao;
	}

}