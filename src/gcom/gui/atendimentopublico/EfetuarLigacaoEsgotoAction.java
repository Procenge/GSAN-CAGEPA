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

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.RamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoesgoto.*;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Leandro Cavalcanti
 * @created 20 de Junho de 2006
 */
public class EfetuarLigacaoEsgotoAction
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

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);

		EfetuarLigacaoEsgotoActionForm ligacaoEsgotoActionForm = (EfetuarLigacaoEsgotoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		/*
		 * [UC0107] Registrar Transação
		 */

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_LIGACAO_ESGOTO_EFETUAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_LIGACAO_ESGOTO_EFETUAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// [UC0107] -Fim- Registrar Transação

		String ordemServicoId = ligacaoEsgotoActionForm.getIdOrdemServico();
		LigacaoEsgoto ligacaoEsgoto = new LigacaoEsgoto();

		String materialLigacao = ligacaoEsgotoActionForm.getMaterialLigacao();
		String perfilLigacao = ligacaoEsgotoActionForm.getPerfilLigacao();
		String percentual = ligacaoEsgotoActionForm.getPercentualColeta().toString().replace(",", ".");
		String percentualEsgoto = ligacaoEsgotoActionForm.getPercentualEsgoto().toString().replace(",", ".");
		String idServicoMotivoNaoCobranca = ligacaoEsgotoActionForm.getMotivoNaoCobranca();
		String valorPercentual = ligacaoEsgotoActionForm.getPercentualCobranca();
		String indicadorCaixaGordura = ligacaoEsgotoActionForm.getIndicadorCaixaGordura();
		String idFuncionario = ligacaoEsgotoActionForm.getIdFuncionario();
		String localInstalacaoRamal = ligacaoEsgotoActionForm.getLocalInstalacaoRamal();

		OrdemServico ordemServico = null;
		if(ordemServicoId != null && !ordemServicoId.equals("")){
			ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
			if(ordemServico == null){
				throw new ActionServletException("atencao.ordem_servico_inexistente", null, "ORDEM DE SERVIÇO INEXISTENTE");

			}
			Imovel imovel = null;
			if(sessao.getAttribute("imovel") != null){
				imovel = (Imovel) sessao.getAttribute("imovel");
			}
			ligacaoEsgoto.setImovel(imovel);

			if(ligacaoEsgotoActionForm.getDataLigacao() != null && ligacaoEsgotoActionForm.getDataLigacao() != ""){
				Date data = Util.converteStringParaDate(ligacaoEsgotoActionForm.getDataLigacao(), false);
				ligacaoEsgoto.setDataLigacao(data);
			}else{
				throw new ActionServletException("atencao.informe_campo", null, " Data da Ligação");
			}
			/*---------------------  InícioDados da Ligação Esgoto  ------------------------*/
			// lesg_iccaixagordura
			ligacaoEsgoto.setIndicadorCaixaGordura(new Short(indicadorCaixaGordura));
			// lagu_tultimaalteracao
			ligacaoEsgoto.setUltimaAlteracao(new Date());
			// lest_id
			ligacaoEsgoto.setId(imovel.getId());
			// LEST_ID
			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
			ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);
			// ligacaoEsgoto.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);

			String diametroLigacao = ligacaoEsgotoActionForm.getDiametroLigacao();
			if(diametroLigacao != null && !diametroLigacao.equals("")
							&& !diametroLigacao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				LigacaoEsgotoDiametro ligacaoEsgotoDiametro = new LigacaoEsgotoDiametro();
				ligacaoEsgotoDiametro.setId(new Integer(diametroLigacao));
				ligacaoEsgoto.setLigacaoEsgotoDiametro(ligacaoEsgotoDiametro);
			}else{
				throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "Diametro da Ligação");
			}

			if(materialLigacao != null && !materialLigacao.equals("")
							&& !materialLigacao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				LigacaoEsgotoMaterial ligacaoEsgotoMaterialMaterial = new LigacaoEsgotoMaterial();
				ligacaoEsgotoMaterialMaterial.setId(new Integer(materialLigacao));
				ligacaoEsgoto.setLigacaoEsgotoMaterial(ligacaoEsgotoMaterialMaterial);
			}else{
				throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "Material da Ligação");
			}

			if(perfilLigacao != null && !perfilLigacao.equals("")
							&& !perfilLigacao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				LigacaoEsgotoPerfil ligacaoEsgotoPerfil = new LigacaoEsgotoPerfil();
				ligacaoEsgotoPerfil.setId(new Integer(perfilLigacao));
				ligacaoEsgoto.setLigacaoEsgotoPerfil(ligacaoEsgotoPerfil);
			}else{
				throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "Perfil da Ligação");
			}
			// item 4.5 - [FS006] caso 1,3
			if(percentual != null && !percentual.equals("")){

				BigDecimal percentualInformadoColeta = new BigDecimal(percentual);
				if(percentualInformadoColeta != null && !percentualInformadoColeta.equals("")
								&& (percentualInformadoColeta.intValue() <= ConstantesSistema.NUMERO_MAXIMO_CONSUMO_MINIMO_FIXADO)){
					ligacaoEsgoto.setPercentualAguaConsumidaColetada(percentualInformadoColeta);
				}
			}else{
				throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "Percentual de Coleta");
			}

			if(percentualEsgoto != null && !percentualEsgoto.equals("")){

				BigDecimal percentualEsgotoColeta = new BigDecimal(percentualEsgoto);
				ligacaoEsgoto.setPercentual(percentualEsgotoColeta);
			}

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			ligacaoEsgoto.setOperacaoEfetuada(operacaoEfetuada);
			ligacaoEsgoto.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ligacaoEsgoto);
			// ------------ FIM DE REGISTRAR TRANSAÇÃO ----------------

			if(ordemServico != null && ligacaoEsgotoActionForm.getIdTipoDebito() != null){

				ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;
				ordemServico.setOperacaoEfetuada(operacaoEfetuada);
				ordemServico.setIndicadorComercialAtualizado(new Short("1"));

				BigDecimal valorAtual = new BigDecimal(0);
				if(ligacaoEsgotoActionForm.getValorDebito() != null){
					String valorDebito = ligacaoEsgotoActionForm.getValorDebito().toString().replace(".", "");

					valorDebito = valorDebito.replace(",", ".");

					valorAtual = new BigDecimal(valorDebito);

					ordemServico.setValorAtual(valorAtual);
				}

				if(idServicoMotivoNaoCobranca != null && !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
					servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
					servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
				}
				ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);

				if(valorPercentual != null){
					ordemServico.setPercentualCobranca(new BigDecimal(ligacaoEsgotoActionForm.getPercentualCobranca()));
				}

				ordemServico.setUltimaAlteracao(new Date());

			}

			ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
			ordemServico.setUltimaAlteracao(new Date());
			String qtdParcelas = ligacaoEsgotoActionForm.getQuantidadeParcelas();
			IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

			if(localInstalacaoRamal != null && !localInstalacaoRamal.equals("") && !localInstalacaoRamal.equals("-1")){
				RamalLocalInstalacao ramalLocalInstalacao = new RamalLocalInstalacao();
				ramalLocalInstalacao.setId(new Integer(localInstalacaoRamal));
				ligacaoEsgoto.setRamalLocalInstalacao(ramalLocalInstalacao);
			}

			if(idFuncionario != null && !idFuncionario.equals("")){
				Funcionario func = new Funcionario();
				func.setId(new Integer(idFuncionario));
				ligacaoEsgoto.setFuncionarioLigacaoEsgoto(func);
			}

			integracaoComercialHelper.setLigacaoEsgoto(ligacaoEsgoto);
			integracaoComercialHelper.setImovel(imovel);
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
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarLigacaoEsgotoAction.do");
					throw ex;
				}

			}

			if(ligacaoEsgotoActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

				try{

					fachada.inserirLigacaoEsgoto(integracaoComercialHelper);

				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarLigacaoEsgotoAction.do");
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
				montarPaginaSucesso(httpServletRequest, "Ligação de Esgoto efetuada com Sucesso", "Efetuar outra Ligação de Esgoto",
								"exibirEfetuarLigacaoEsgotoAction.do?menu=sim",
								"exibirAtualizarLigacaoEsgotoAction.do?menu=sim&matriculaImovel=" + imovel.getId(),
								"Atualização Ligação de Esgoto efetuada");
			}

			return retorno;
		}else{
			throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "Ordem de Serviço");
		}
	}

}
