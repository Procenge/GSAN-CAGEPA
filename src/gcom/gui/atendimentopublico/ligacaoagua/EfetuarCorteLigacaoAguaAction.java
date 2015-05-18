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

package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.*;
import gcom.atendimentopublico.ligacaoagua.bean.DadosEfetuacaoCorteLigacaoAguaHelper;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ImovelDadosMedicaoHistoricoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o processamento da p�gina de efetuar corte de liga��o de �gua
 * 
 * @author Leandro Cavalcanti
 * @date 12/07/2006
 *       Refeito
 * @author Leonardo Regis
 * @date 27/09/2006
 */
public class EfetuarCorteLigacaoAguaAction
				extends GcomAction {

	/**
	 * Efetuar Corte Liga��o �gua
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return forward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		EfetuarCorteLigacaoAguaActionForm corteLigacaoAguaActionForm = (EfetuarCorteLigacaoAguaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// [UC0107] Registrar Transa��o
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CORTE_LIGACAO_AGUA_EFETUAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CORTE_LIGACAO_AGUA_EFETUAR);

		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// [UC0107] -Fim- Registrar Transa��o

		if(corteLigacaoAguaActionForm.getIdOrdemServico() != null && !corteLigacaoAguaActionForm.getIdOrdemServico().equals("")){

			OrdemServico ordemServico = new OrdemServico();
			Imovel imovel = new Imovel();
			LigacaoAgua ligacaoAgua = new LigacaoAgua();
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;
			if(sessao.getAttribute("ordemServico") != null){
				ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
			}else{
				ordemServico = this.getFachada().recuperaOSPorId(Integer.valueOf(corteLigacaoAguaActionForm.getIdOrdemServico()));
				sessao.setAttribute("ordemServico", ordemServico);
			}

			// Imovel
			imovel = ordemServico.getImovel();

			// Motivo do Corte
			String idMotivoCorteStr = corteLigacaoAguaActionForm.getMotivoCorte();

			FiltroMotivoCorte filtroMotivoCorte = new FiltroMotivoCorte();
			filtroMotivoCorte.adicionarParametro(new ParametroSimples(FiltroMotivoCorte.ID, idMotivoCorteStr));

			Collection<MotivoCorte> colecaoMotivoCorte = fachada.pesquisar(filtroMotivoCorte, MotivoCorte.class.getName());

			MotivoCorte motivoCorte = (MotivoCorte) Util.retonarObjetoDeColecao(colecaoMotivoCorte);

			Integer indicadorCortePedido = motivoCorte.getIndicadorCortePedido();

			// Situa��o da Liga��o de �gua
			Integer idLigacaoAguaSituacao = null;

			if(indicadorCortePedido != null && indicadorCortePedido.equals(Integer.valueOf(ConstantesSistema.SIM))){
				idLigacaoAguaSituacao = LigacaoAguaSituacao.CORTADO_PEDIDO;
			}else{
				idLigacaoAguaSituacao = LigacaoAguaSituacao.CORTADO;
			}

			LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
			ligacaoAguaSituacao.setId(idLigacaoAguaSituacao);
			imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);

			imovel.setUltimaAlteracao(new Date());

			// Liga��o �gua
			ligacaoAgua.setId(imovel.getId());

			Date dataCorte = null;

			if(corteLigacaoAguaActionForm.getDataCorte() != null && corteLigacaoAguaActionForm.getDataCorte() != ""){
				dataCorte = Util.converteStringParaDate(corteLigacaoAguaActionForm.getDataCorte());
			}else{
				throw new ActionServletException("atencao.required", null, " Data do Corte");
			}

			ligacaoAgua.setDataCorte(dataCorte);

			if(corteLigacaoAguaActionForm.getNumSeloCorte() != null && !corteLigacaoAguaActionForm.getNumSeloCorte().equals("")){
				ligacaoAgua.setNumeroSeloCorte(Integer.valueOf(corteLigacaoAguaActionForm.getNumSeloCorte()));
			}else{
				ligacaoAgua.setNumeroSeloCorte(null);
			}

			CorteTipo corteTipo = new CorteTipo();
			corteTipo.setId(Integer.valueOf(corteLigacaoAguaActionForm.getTipoCorte()));
			ligacaoAgua.setCorteTipo(corteTipo);

			ligacaoAgua.setMotivoCorte(motivoCorte);
			ligacaoAgua.setUltimaAlteracao(new Date());

			String idFuncionario = corteLigacaoAguaActionForm.getIdFuncionario();

			if(idFuncionario != null && !idFuncionario.equals("")){
				Funcionario func = new Funcionario();
				func.setId(new Integer(idFuncionario));
				ligacaoAgua.setFuncionarioCorte(func);
			}

			if(ligacaoAgua.getMotivoCorte() != null
							&& ligacaoAgua.getMotivoCorte().getIndicadorCorteFaltaPagamento().equals(new Integer("1"))){
				if(ligacaoAgua.getNumeroCorteFaltaPagamento() == null){
					ligacaoAgua.setNumeroCorteFaltaPagamento(1);
				}else{
					ligacaoAgua.setNumeroCorteFaltaPagamento(ligacaoAgua.getNumeroCorteFaltaPagamento() + 1);
				}
			}else if(ligacaoAgua.getMotivoCorte() != null
							&& ligacaoAgua.getMotivoCorte().getIndicadorCorteInfracao().equals(new Integer("1"))){
				if(ligacaoAgua.getNumeroCorteInfracao() == null){
					ligacaoAgua.setNumeroCorteInfracao(1);
				}else{
					ligacaoAgua.setNumeroCorteInfracao(ligacaoAgua.getNumeroCorteInfracao() + 1);
				}
			}else if(ligacaoAgua.getMotivoCorte() != null
							&& ligacaoAgua.getMotivoCorte().getIndicadorCortePedido().equals(new Integer("1"))){
				if(ligacaoAgua.getNumeroCortePedido() == null){
					ligacaoAgua.setNumeroCortePedido(1);
				}else{
					ligacaoAgua.setNumeroCortePedido(ligacaoAgua.getNumeroCortePedido() + 1);
				}
			}

			// Hidrometro Instala��o Hist�rico
			if(imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
				hidrometroInstalacaoHistorico = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico();

				// Validar N�mero de Leitura do Corte / N�mero do Selo de Corte
				if(corteLigacaoAguaActionForm.getNumLeituraCorte() != null && !corteLigacaoAguaActionForm.getNumLeituraCorte().equals("")){
					hidrometroInstalacaoHistorico.setNumeroLeituraCorte(Integer.valueOf(corteLigacaoAguaActionForm.getNumLeituraCorte()));
				}else{

					// OC1356300[UC0355][SB0001] - Atualizar Im�vel/Liga��o de �gua/Hist�rico de
					// Instala��o de Hidr�metro
					ImovelDadosMedicaoHistoricoHelper imovelDadosMedicaoHistoricoHelper = new ImovelDadosMedicaoHistoricoHelper();
					imovelDadosMedicaoHistoricoHelper = fachada.obterDadosMedicaoMaiorReferenciaLeitura(ordemServico.getImovel(),
									MedicaoTipo.LIGACAO_AGUA);

					if(imovelDadosMedicaoHistoricoHelper != null && imovelDadosMedicaoHistoricoHelper.getLeituraAtual() != null){
						hidrometroInstalacaoHistorico.setNumeroLeituraCorte(imovelDadosMedicaoHistoricoHelper.getLeituraAtual());
					}else{
						hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);
					}
				}
				hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());
			}
			ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			imovel.setLigacaoAgua(ligacaoAgua);
			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;
			if(corteLigacaoAguaActionForm.getMotivoNaoCobranca() != null
							&& !corteLigacaoAguaActionForm.getMotivoNaoCobranca().equals(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(Integer.valueOf(corteLigacaoAguaActionForm.getMotivoNaoCobranca()));
			}
			ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);

			BigDecimal valorAtual = new BigDecimal(0);
			if(corteLigacaoAguaActionForm.getValorDebito() != null){
				String valorDebito = corteLigacaoAguaActionForm.getValorDebito().toString().replace(".", "");

				valorDebito = valorDebito.replace(",", ".");

				valorAtual = new BigDecimal(valorDebito);

				ordemServico.setValorAtual(valorAtual);
			}

			if(corteLigacaoAguaActionForm.getPercentualCobranca() != null && !corteLigacaoAguaActionForm.getPercentualCobranca().equals("")){
				ordemServico.setPercentualCobranca(new BigDecimal(corteLigacaoAguaActionForm.getPercentualCobranca()));
			}
			ordemServico.setUltimaAlteracao(new Date());

			// Preenche Helper
			DadosEfetuacaoCorteLigacaoAguaHelper dadosHelper = new DadosEfetuacaoCorteLigacaoAguaHelper();
			dadosHelper.setImovel(imovel);
			dadosHelper.setLigacaoAgua(ligacaoAgua);
			dadosHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			dadosHelper.setOrdemServico(ordemServico);
			if(corteLigacaoAguaActionForm.getVeioEncerrarOS().equalsIgnoreCase("true")){
				dadosHelper.setVeioEncerrarOS(true);
			}else{
				dadosHelper.setVeioEncerrarOS(false);
			}
			if(corteLigacaoAguaActionForm.getQuantidadeParcelas() != null && !corteLigacaoAguaActionForm.getQuantidadeParcelas().equals("")){
				dadosHelper.setQtdeParcelas(Integer.valueOf(corteLigacaoAguaActionForm.getQuantidadeParcelas()).intValue());
			}else{
				dadosHelper.setQtdeParcelas(0);
			}

			integracaoComercialHelper.setDadosEfetuacaoCorteLigacaoAguaHelper(dadosHelper);
			integracaoComercialHelper.setOrdemServico(ordemServico);
			integracaoComercialHelper.setUsuarioLogado(usuario);

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){
				try{

					fachada.verificarQuantidadeParcelas(integracaoComercialHelper.getUsuarioLogado(), Util.obterShort(String
								.valueOf(dadosHelper
							.getQtdeParcelas())));
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarCorteLigacaoAguaAction.do");
					throw ex;
				}
			}

			// efetuar Corte Liga��o de �gua

			if(corteLigacaoAguaActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

				try{

					fachada.efetuarCorteLigacaoAgua(integracaoComercialHelper);
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarCorteLigacaoAguaAction.do");
					throw ex;

				}
			}else{
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
				// registrando operacao
				imovel.setOperacaoEfetuada(operacaoEfetuada);
				imovel.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(imovel);

				ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ligacaoAgua);

				if(hidrometroInstalacaoHistorico != null){
					hidrometroInstalacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
					hidrometroInstalacaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);
				}

				if(!dadosHelper.isVeioEncerrarOS()){
					ordemServico.setOperacaoEfetuada(operacaoEfetuada);
					ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(ordemServico);
				}

				montarPaginaSucesso(httpServletRequest, "Corte de Liga��o de �gua do im�vel " + imovel.getId() + " efetuada com Sucesso",
								"Efetuar outra Corte de Liga��o de �gua", "exibirEfetuarCorteLigacaoAguaAction.do?menu=sim",
								"exibirAtualizarCorteLigacaoAguaAction.do?idOrdemServico" + ordemServico.getId(),
								"Atualiza��o Corte Liga��o de �gua efetuada");
			}

			return retorno;
		}else{
			throw new ActionServletException("atencao.required", null, "Ordem de Servi�o");
		}
	}

}
