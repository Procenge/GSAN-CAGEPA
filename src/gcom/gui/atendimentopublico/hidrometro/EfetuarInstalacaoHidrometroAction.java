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

package gcom.gui.atendimentopublico.hidrometro;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Ana Maria
 * @date 30/06/2006
 */

public class EfetuarInstalacaoHidrometroAction
				extends GcomAction {

	/**
	 * Este caso de uso permite efetuar instala��o de hidr�metro
	 * [UC0362] Efetuar Instala��o de Hidr�metro
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		EfetuarInstalacaoHidrometroActionForm efetuarInstalacaoHidrometroActionForm = (EfetuarInstalacaoHidrometroActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String numeroHidrometro = efetuarInstalacaoHidrometroActionForm.getNumeroHidrometro();

		String idServicoMotivoNaoCobranca = efetuarInstalacaoHidrometroActionForm.getMotivoNaoCobranca();
		String valorPercentual = efetuarInstalacaoHidrometroActionForm.getPercentualCobranca();
		String qtdParcelas = efetuarInstalacaoHidrometroActionForm.getQuantidadeParcelas();

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();

		OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
		Imovel imovel = null;

		if(ordemServico.getImovel() == null){
			imovel = ordemServico.getRegistroAtendimento().getImovel();
		}else{
			imovel = ordemServico.getImovel();
		}

		Integer idHidrometro = null;
		if(numeroHidrometro != null){
			// Constr�i o filtro para pesquisa do Hidr�metro
			FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
			filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.NUMERO_HIDROMETRO, numeroHidrometro));
			// Realiza a pesquisa do Hidr�metro
			Collection colecaoHidrometro = fachada.pesquisar(filtroHidrometro, Hidrometro.class.getName());

			// verificar se o n�mero do hidr�metro n�o est� cadastrado
			if(colecaoHidrometro == null || colecaoHidrometro.isEmpty()){
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}
			Iterator iteratorHidrometro = colecaoHidrometro.iterator();
			Hidrometro hidrometro = (Hidrometro) iteratorHidrometro.next();
			hidrometroInstalacaoHistorico.setHidrometro(hidrometro);
			idHidrometro = hidrometro.getId();
		}

		/*
		 * [UC0107] Registrar Transa��o
		 */
		RegistradorOperacao registradorOperacao = null;
		if(idHidrometro == null){
			registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSTALACAO_HIDROMETRO_EFETUAR, new UsuarioAcaoUsuarioHelper(
							usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		}else{
			registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSTALACAO_HIDROMETRO_EFETUAR, idHidrometro, idHidrometro,
							new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		}

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSTALACAO_HIDROMETRO_EFETUAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// [UC0107] -Fim- Registrar Transa��o

		// regitrando operacao
		hidrometroInstalacaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);

		// Atualiza a entidade com os valores do formul�rio
		if(efetuarInstalacaoHidrometroActionForm.getDataInstalacao() != null
						&& !efetuarInstalacaoHidrometroActionForm.getDataInstalacao().equals("")
						&& efetuarInstalacaoHidrometroActionForm.getMatriculaImovel() != null
						&& !efetuarInstalacaoHidrometroActionForm.getMatriculaImovel().equals("")){
			LigacaoAgua ligacaoAgua = fachada.pesquisarLigacaoAgua(Integer.valueOf(efetuarInstalacaoHidrometroActionForm
							.getMatriculaImovel()));

			if(ligacaoAgua != null && ligacaoAgua.getDataLigacao() != null){

				if(ligacaoAgua != null) if((Util.converteStringParaDate(efetuarInstalacaoHidrometroActionForm.getDataInstalacao()))
								.before(ligacaoAgua.getDataLigacao())){
					throw new ActionServletException("atencao.data_instalacao_hidrometro_anterior_ligacao_agua");
				}
			}

		}
		efetuarInstalacaoHidrometroActionForm.setFormValues(hidrometroInstalacaoHistorico);

		if(ordemServico != null && efetuarInstalacaoHidrometroActionForm.getIdTipoDebito() != null){

			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			BigDecimal valorAtual = new BigDecimal(0);

			if(efetuarInstalacaoHidrometroActionForm.getValorDebito() != null){
				String valorDebito = efetuarInstalacaoHidrometroActionForm.getValorDebito().toString().replace(".", "");

				valorDebito = valorDebito.replace(",", ".");

				valorAtual = new BigDecimal(valorDebito);

				ordemServico.setValorAtual(valorAtual);
			}

			if(idServicoMotivoNaoCobranca != null
							&& !idServicoMotivoNaoCobranca.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(Integer.valueOf(idServicoMotivoNaoCobranca));
			}
			ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);

			if(valorPercentual != null){
				ordemServico.setPercentualCobranca(new BigDecimal(efetuarInstalacaoHidrometroActionForm.getPercentualCobranca()));
			}

			ordemServico.setUltimaAlteracao(new Date());
		}
		ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
		ordemServico.setUltimaAlteracao(new Date());
		ordemServico.setOperacaoEfetuada(operacaoEfetuada);
		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

		integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
		integracaoComercialHelper.setOrdemServico(ordemServico);
		integracaoComercialHelper.setQtdParcelas(qtdParcelas);
		integracaoComercialHelper.setUsuarioLogado(usuario);

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			try{

				fachada.verificarQuantidadeParcelas(integracaoComercialHelper.getUsuarioLogado(), Util.obterShort(qtdParcelas));
			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarInstalacaoHidrometroAction.do");
				throw ex;
			}
		}

		if(efetuarInstalacaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

			try{

				fachada.efetuarInstalacaoHidrometro(integracaoComercialHelper);

			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarInstalacaoHidrometroAction.do");
				throw ex;
			}

		}else{

			try{

				fachada.validacaoInstalacaoHidrometro(numeroHidrometro);
			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarInstalacaoHidrometroAction.do");
				throw ex;

			}
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);
			sessao.setAttribute("integracaoComercialHelper", integracaoComercialHelper);

			if(sessao.getAttribute("semMenu") == null){
				retorno = actionMapping.findForward("encerrarOrdemServicoAction");
			}else{
				retorno = actionMapping.findForward("encerrarOrdemServicoPopupAction");
			}
			sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
		}
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			// Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Instala��o de Hidr�metro para o im�vel " + imovel.getId() + " efetuada com sucesso.",
							"Efetuar outra Instala��o de Hidr�metro", "exibirEfetuarInstalacaoHidrometroAction.do");
		}

		sessao.removeAttribute("EfetuarInstalacaoHidrometroActionForm");

		return retorno;
	}

}
