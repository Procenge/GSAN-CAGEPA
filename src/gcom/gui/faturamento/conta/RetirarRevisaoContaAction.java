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

package gcom.gui.faturamento.conta;

import gcom.atendimentopublico.registroatendimento.EspecificacaoTipoValidacao;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.faturamento.ExecutorParametrosFaturamento;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RetirarRevisaoContaAction
				extends GcomAction
				implements Parametrizacao {

	/**
	 * @author Virgínia Melo
	 * @date 19/02/2009
	 *       Retirada verificação de erro de concorrência. Essa verificação é realizada no
	 *       controlador.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterConta");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		Fachada fachada = Fachada.getInstancia();

		// Carregando o identificador das contas selecionadas
		String identificadoresConta = httpServletRequest.getParameter("conta");

		Collection idsConta = new ArrayList();
		String idImovel = httpServletRequest.getParameter("idImovel");

		String[] arrayIdentificadores = identificadoresConta.split(",");

		for(int i = 0; i < arrayIdentificadores.length; i++){
			String dadosConta = arrayIdentificadores[i];
			String[] idUltimaAlteracao = dadosConta.split("-");

			Integer idConta = new Integer(idUltimaAlteracao[0]);

			// [FS0021] Verificar situação da conta
			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
			filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
			Collection colecaoConta = fachada.pesquisar(filtroConta, Conta.class.getName());
			Conta contaSelecao = (Conta) colecaoConta.iterator().next();
			if(contaSelecao != null){
				if(!fachada.verificarSituacaoContaPermitida(contaSelecao)){
					throw new ActionServletException("atencao.conta_em_situacao_nao_permitida", contaSelecao
									.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao(), "ação");
				}
			}

			idsConta.add(idConta);
		}

		// [FS0017] Verificar ocorrência de conta(s) em revisão por ação do usuário - Vivianne Sousa
		// 14/05/2007
		Collection<Conta> contasRevisaoAcaoUsuario = fachada.obterContasEmRevisaoPorAcaoUsuario(idsConta);

		if(contasRevisaoAcaoUsuario != null && !contasRevisaoAcaoUsuario.isEmpty()){

			for(Conta contaSelecao : contasRevisaoAcaoUsuario){

				String pIdMotivoRetencaoContaPreFaturada = null;

				try{

					pIdMotivoRetencaoContaPreFaturada = (String) ParametroFaturamento.P_MOTIVO_RETENCAO_CONTA_PREFAT.executar();
				}catch(ControladorException e){

					throw new ActionServletException("atencao.sistemaparametro_inexistente", null, "P_MOTIVO_RETENCAO_CONTA_PREFAT");
				}

				boolean temPermissaoRetirarContaRetidaDeRevisao = fachada.verificarPermissaoRetirarContaRetidaDeRevisao(usuarioLogado);

				// Atribui "2" (Não) ao Indicador de Conta em Revisão por Motivo Permitido com
				// Permissão
				// Especial.
				Short indicadorContaEmRevisaoPorMotivoPermitidoUsuarioPermissaoEspecial = ConstantesSistema.NAO;

				/*
				 * Caso a conta esteja em retenção por motivo de revisão que permita retificação de
				 * conta sem RA (CMRV_ID da tabela CONTA igual ao valor do parâmetro
				 * (PASI_VLPARAMETRO na tabela PARAMETRO_SISTEMA com
				 * PASI_DSPARAMETRO="P_MOTIVO_RETENCAO_CONTA_PREFAT")), e o usuário possua permissão
				 * especial para RETIFICAR CONTA RETIDA (existir ocorrência na tabela
				 * USUARIO_PERMISSAO_ESPECIAL com USUR_ID=identificação do usuário e PMEP_ID=114).
				 */
				if(contaSelecao != null && contaSelecao.getContaMotivoRevisao() != null
								&& pIdMotivoRetencaoContaPreFaturada.equals(contaSelecao.getContaMotivoRevisao().getId().toString())
								&& temPermissaoRetirarContaRetidaDeRevisao){

					// Atribui "1" (Sim) ao Indicador de Conta em Revisão por Motivo Permitido com
					// Permissão Especial
					indicadorContaEmRevisaoPorMotivoPermitidoUsuarioPermissaoEspecial = ConstantesSistema.SIM;
				}

				boolean temPermissaoRetirarContaRevisaoSemRA = fachada.verificarPermissaoRetirarContaRevisaoSemRA(usuarioLogado);

				/*
				 * Caso o usuário não possua permissão especial para RETIFICAR CONTA SEM RA (não
				 * existir ocorrência na tabela USUARIO_PERMISSAO_ESPECIAL com USUR_ID=identificação
				 * do usuário e PMEP_ID=48) e Indicador de Conta em Revisão por Motivo Permitido com
				 * Permissão Especial esteja com valor "2" (Não)
				 */
				if(!temPermissaoRetirarContaRevisaoSemRA
								&& indicadorContaEmRevisaoPorMotivoPermitidoUsuarioPermissaoEspecial.equals(ConstantesSistema.NAO)){

					// [FS0001] - Verificar Existência de RA
					fachada.verificarExistenciaRegistroAtendimento(Integer.valueOf(idImovel),
									"atencao.conta_existencia_registro_atendimento", EspecificacaoTipoValidacao.ALTERACAO_CONTA);

				}
			}
		}

		String enderecoURL = httpServletRequest.getServletPath();
		if(idsConta != null && !idsConta.isEmpty()){
			fachada.verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(idsConta, usuarioLogado, false, false, enderecoURL);
		}

		if(sessao.getAttribute("colecaoContaImovel") != null
						&& (identificadoresConta != null && !identificadoresConta.equalsIgnoreCase(""))){

			Collection<Conta> colecaoContaImovel = (Collection) sessao.getAttribute("colecaoContaImovel");

			/*
			 * Iterator it = colecaoContaImovel.iterator();
			 * while(it.hasNext()){
			 * Conta conta = (Conta) it.next();
			 * boolean verificarConta = false;
			 * for(int i = 0; i < arrayIdentificadores.length; i++){
			 * Integer contaVerificacao = Util.obterInteger(arrayIdentificadores[i].split("-")[0]);
			 * if(conta.getId().equals(contaVerificacao)){
			 * verificarConta = true;
			 * }
			 * }
			 * if(verificarConta){
			 * // [FS0022] Validar motivo da retificação/revisão da conta.
			 * String[] parametroMotivoRetificaoNaoPermitida;
			 * try{
			 * parametroMotivoRetificaoNaoPermitida = ((String)
			 * ParametroFaturamento.P_MOTIVO_RETIFICACAO_NAO_PERMITIDA
			 * .executar(this)).split(",");
			 * }catch(ControladorException e){
			 * throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
			 * new String[e.getParametroMensagem().size()]));
			 * }
			 * boolean motivoRetificaoNaoPermitida = true;
			 * if(conta.getContaMotivoRetificacao() != null){
			 * for(int i = 0; i < parametroMotivoRetificaoNaoPermitida.length; i++){
			 * 
			 * if(parametroMotivoRetificaoNaoPermitida[i].equals(conta.getContaMotivoRetificacao().getId
			 * ().toString())){
			 * motivoRetificaoNaoPermitida = false;
			 * break;
			 * }
			 * }
			 * }
			 * if(!motivoRetificaoNaoPermitida){
			 * throw new ActionServletException("atencao.conta_em_situacao_nao_permitida",
			 * conta.getDebitoCreditoSituacaoAtual()
			 * .getDescricaoDebitoCreditoSituacao(), "ação");
			 * }
			 * }
			 * }
			 */

			// Retirando de revisão uma ou mais contas
			fachada.retirarRevisaoConta(colecaoContaImovel, identificadoresConta, usuarioLogado);
		}

		return retorno;
	}

	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosFaturamento.getInstancia();
	}
}