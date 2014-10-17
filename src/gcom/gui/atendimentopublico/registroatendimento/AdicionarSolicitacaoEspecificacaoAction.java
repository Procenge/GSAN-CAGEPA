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

import gcom.atendimentopublico.ordemservico.EspecificacaoServicoTipo;
import gcom.atendimentopublico.registroatendimento.EspecificacaoImovelSituacao;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacaoMensagem;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacaoMensagem;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela pre-exibição da pagina de inserir bairro
 * 
 * @author Sávio Luiz
 * @created 28 de Julho de 2006
 */
public class AdicionarSolicitacaoEspecificacaoAction
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

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("adicionarSolicitacaoEspecificacao");

		Collection colecaoSolicitacaoTipoEspecificacao = null;
		if(sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao") == null){
			colecaoSolicitacaoTipoEspecificacao = new ArrayList();
		}else{
			colecaoSolicitacaoTipoEspecificacao = (Collection) sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao");
		}

		AdicionarSolicitacaoEspecificacaoActionForm adicionarSolicitacaoEspecificacaoActionForm = (AdicionarSolicitacaoEspecificacaoActionForm) actionForm;

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// seta os campos do form no objeto SolicitacaoTipoEspecificacao
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();

		solicitacaoTipoEspecificacao.setIndicadorSolicitante(Short.valueOf("1"));

		// indicador de coletor
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorColetor(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorColetor()));
		}

		if(adicionarSolicitacaoEspecificacaoActionForm.getPrazoPrevisaoAtendimento() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getPrazoPrevisaoAtendimento().equals("")){
			// Prazo de previsão de atendimento
			solicitacaoTipoEspecificacao.setHorasPrazo(Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getPrazoPrevisaoAtendimento()));
		}
		// Descrição da especificação
		solicitacaoTipoEspecificacao.setDescricao(adicionarSolicitacaoEspecificacaoActionForm.getDescricaoSolicitacao());

		// Pavimento de calçada obrigatório
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorPavimentoCalcada(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorPavimentoCalcada()));
		}
		// Pavimento de rua obrigatório
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorPavimentoRua(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorPavimentoRua()));
		}

		// refere-se a ligação de agua
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorLigacaoAgua(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorLigacaoAgua()));
		}
		// Cobrança de material
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorCobrancaMaterial(Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorCobrancaMaterial()));
		}
		// Matricula do imóvel obrigatório
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorMatricula(Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorMatriculaImovel()));
		}
		// Parecer de encerramento obrigatório
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorParecerEncerramento(Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorParecerEncerramento()));
		}
		// Gera débito
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorGeracaoDebito(Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorGerarDebito()));
		}
		// Gera Crédito
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorGeracaoCredito(Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorGerarCredito()));
		}
		// Gera Ordem de Serviço
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarOrdemServico() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarOrdemServico().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorGerarOrdemServico()));
		}

		// indicador de obrigatoriedade de preenchimento do RG na RA
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorObrigatoriedadeRgRA(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorObrigatoriedadeRgRA()));
		}
		// indicador de obrigatoriedade de preenchimento do CPF/CNPJ na RA
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorObrigatoriedadeCpfCnpjRA(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorObrigatoriedadeCpfCnpjRA()));
		}
		// hora e data correntes
		solicitacaoTipoEspecificacao.setUltimaAlteracao(new Date());

		// id do tipo da solicitação gerada
		solicitacaoTipoEspecificacao.setServicoTipo(null);

		// Gera ordem de serviço
		// GSAN_DES_HFALCAO_20120204_CR99605_CR107764
		// solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(ConstantesSistema.SIM);

		// Cliente Obrigatório
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente().equals("")){

			solicitacaoTipoEspecificacao.setIndicadorCliente(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorCliente()));
		}

		// Indicador verfificcação de débito
		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorVerificarDebito() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorVerificarDebito().equals("")){

			solicitacaoTipoEspecificacao.setIndicadorVerificarDebito(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorVerificarDebito()));
			if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorTipoVerificarDebito() != null
							&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorTipoVerificarDebito().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorTipoVerificarDebito(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorTipoVerificarDebito()));

				if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().toString()
								.equals(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_IMOVEIS.toString())
								|| solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().toString()
												.equals(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_AMBOS.toString())){

					if(!Util.isVazioOuBranco(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorConsiderarApenasDebitoTitularAtual())){

						solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(Short
										.valueOf(adicionarSolicitacaoEspecificacaoActionForm
														.getIndicadorConsiderarApenasDebitoTitularAtual()));
					}else{

						solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
					}

					if(solicitacaoTipoEspecificacao.getIndicadorConsiderarApenasDebitoTitularAtual().equals(ConstantesSistema.NAO)
									|| Util.isVazioOuBranco(adicionarSolicitacaoEspecificacaoActionForm.getIdClienteRelacaoTipo())
									|| adicionarSolicitacaoEspecificacaoActionForm.getIdClienteRelacaoTipo().equals(
													ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){

						solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
					}else{

						ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();
						clienteRelacaoTipo.setId(Util.obterInteger(adicionarSolicitacaoEspecificacaoActionForm.getIdClienteRelacaoTipo()));
						solicitacaoTipoEspecificacao.setClienteRelacaoTipo(clienteRelacaoTipo);
					}
				}else{

					solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
					solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
				}
			}else{

				solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
				solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
			}
		}else{

			solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
			solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
		}

		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento().equals("")){

			solicitacaoTipoEspecificacao.setIndicadorCalculoDataPrevistaAtendimento(Short
							.valueOf(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()));
		}

		// Situação imovel
		if(adicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel().equals("")){
			EspecificacaoImovelSituacao especificacaoImovelSituacao = new EspecificacaoImovelSituacao();
			especificacaoImovelSituacao.setId(Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel()));
			solicitacaoTipoEspecificacao.setEspecificacaoImovelSituacao(especificacaoImovelSituacao);
		}

		if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento() != null
						&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento().equals("")){
			solicitacaoTipoEspecificacao.setIndicadorCalculoDataPrevistaAtendimento(Short
							.valueOf(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()));
		}

		Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = (Collection) sessao
						.getAttribute("colecaoEspecificacaoServicoTipo");

		// recupera a coleção de especificacao servico tipo
		if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){
			// [SF0004] - Validar Valor Ordem de Serviço 2ª parte
			fachada.verificarOrdemExecucaoForaOrdem(colecaoEspecificacaoServicoTipo);

			// Ordem de Serviço Automática
			for(EspecificacaoServicoTipo especificacaoServicoTipo : colecaoEspecificacaoServicoTipo){
				Date ultimaAlteracao = especificacaoServicoTipo.getUltimaAlteracao();
				long timeUltimaAlteracao = ultimaAlteracao.getTime();

				String parametroIndicadorGeracaoAutomatica = "indicadorGeracaoAutomatica" + timeUltimaAlteracao;
				String indicadorGeracaoAutomaticaStr = httpServletRequest.getParameter(parametroIndicadorGeracaoAutomatica);

				if(!Util.isVazioOuBranco(indicadorGeracaoAutomaticaStr)){
					Short indicadorGeracaoAutomatica = Short.valueOf(indicadorGeracaoAutomaticaStr);
					especificacaoServicoTipo.setIndicadorGeracaoAutomatica(indicadorGeracaoAutomatica);
				}
			}

			solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(new HashSet(colecaoEspecificacaoServicoTipo));
			sessao.removeAttribute("colecaoEspecificacaoServicoTipo");
		}

		if(!Util.isVazioOuBranco(adicionarSolicitacaoEspecificacaoActionForm.getIdSolicitacaoTipoEspecificacaoMensagem())){
			FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();
			filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacaoMensagem.ID, Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm
											.getIdSolicitacaoTipoEspecificacaoMensagem())));
			filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacaoMensagem.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			SolicitacaoTipoEspecificacaoMensagem objetoSolicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) Util
							.retonarObjetoDeColecao(fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMensagem,
											SolicitacaoTipoEspecificacaoMensagem.class.getName()));

			if(objetoSolicitacaoTipoEspecificacaoMensagem == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Mensagem Automática Padrão");
			}

			solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(objetoSolicitacaoTipoEspecificacaoMensagem);
		}else{
			solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(null);
		}

		if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
			// Indicador Conta em Revisão.
			if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao() != null
							&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorContaEmRevisao(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorContaEmRevisao()));
				if(Short.valueOf(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao()) == ConstantesSistema.SIM
								.shortValue()){
					if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorMensagemAlertaRevisao() != null
									&& !adicionarSolicitacaoEspecificacaoActionForm.getIndicadorMensagemAlertaRevisao().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorMensagemAlertaRevisao(Short
										.valueOf(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorMensagemAlertaRevisao()));
					}

				}
			}

		}

		// indicador de uso ativo
		solicitacaoTipoEspecificacao.setIndicadorUso(Short.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO));

		// adiciona na coleção o tipo de solicitação especificado
		if(!colecaoSolicitacaoTipoEspecificacao.contains(solicitacaoTipoEspecificacao)){
			colecaoSolicitacaoTipoEspecificacao.add(solicitacaoTipoEspecificacao);
		}

		sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

		// manda um parametro para fechar o popup
		httpServletRequest.setAttribute("fecharPopup", 1);

		return retorno;
	}
}
