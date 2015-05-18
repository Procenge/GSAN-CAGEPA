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
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os
 * par�metros para realiza��o da transfer�ncia de d�bitos e cr�ditos entre im�veis
 * 
 * @author Raphael Rossiter
 * @date 06/06/2007
 */
public class ExibirTransferenciaDebitoCreditoDadosImovelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);
		
		ActionForward retorno = actionMapping.findForward("transferenciaDebitoCreditoDadosImovel");

		TransferenciaDebitoCreditoDadosImovelActionForm form = (TransferenciaDebitoCreditoDadosImovelActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		sessao.setAttribute("colecaoRelacaoImovel", null);
		form.setIdClienteImovelSelecionado(null);
		form.setIdsContasSelecionadas(null);
		form.setIdsDebitosSelecionadas(null);
		form.setIdsCreditosSelecionadas(null);
		form.setIdsGuiasSelecionadas(null);
		form.setIdClienteOrigemSelecionado(null);
		form.setIdClienteRelacaoOrigemSelecionado(null);
		form.setIdRelacaoClienteOrigem(null);

		form.getIdClienteOrigem().clear();
		form.getIdClienteRelacaoOrigem().clear();

		form.getIdsContas().clear();
		form.getIdsDebitos().clear();
		form.getIdsGuias().clear();
		form.getIdsCreditos().clear();
		
		Boolean indicadorFaturamentoTitularDebito = false;
		try{
			if(ParametroFaturamento.P_INDICADOR_FATURAMENTO_ATUAL_TITULAR_DEBITO_IMOVEL.executar().equals("1")){
				indicadorFaturamentoTitularDebito = true;
			}
		}catch(ControladorException e){
			e.printStackTrace();
		}		
		
		// REGISTRO ATENDIMENTO
		String pesquisarRA = httpServletRequest.getParameter("pesquisarRA");
		if(pesquisarRA != null && !pesquisarRA.equals("")){

			// [FS0004] Validar Registro Atendimento
			Object[] dadosRA = fachada.validarRegistroAtendimentoTransferenciaDebitoCredito(Integer
							.valueOf(form.getIdRegistroAtendimento()), false);

			String descricaoRA = (String) dadosRA[0];
			Short ocorreuErro = (Short) dadosRA[1];
			Integer idImovel = (Integer) dadosRA[2];

			if(ocorreuErro.equals(ConstantesSistema.SIM)){
				limparDadosRA(form);
				httpServletRequest.setAttribute("corRA", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idRegistroAtendimento");
			}else{

				// MATRICULA IMOVEL
				form.setIdImovelOrigem(idImovel.toString());

				// INSCRICAO IMOVEL
				String inscricaoImovel = fachada.pesquisarInscricaoImovel(idImovel, true);
				form.setInscricaoImovelOrigem(inscricaoImovel);

				// CLIENTE USUARIO DO IMOVEL
				Cliente cliente = fachada.pesquisarClienteUsuarioImovel(idImovel);
				form.setNomeClienteUsuarioImovelOrigem(cliente.getNome());

				// LIGACAO AGUA SITUACAO
				LigacaoAguaSituacao ligacaoAguaSituacao = fachada.pesquisarLigacaoAguaSituacao(idImovel);
				form.setDescricaoLigacaoAguaSituacaoImovelOrigem(ligacaoAguaSituacao.getDescricao());

				// LIGACAO ESOTO SITUACAO
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = fachada.pesquisarLigacaoEsgotoSituacao(idImovel);
				form.setDescricaoLigacaoEsgotoSituacaoImovelOrigem(ligacaoEsgotoSituacao.getDescricao());

				httpServletRequest.setAttribute("nomeCampo", "idImovelDestino");

			}

			// DESCRICAO RA
			form.setDescricaoEspecificacaoRA(descricaoRA);
		}

		// IMOVEL DESTINO
		String pesquisarImovelDestino = httpServletRequest.getParameter("pesquisarImovelDestino");
		if(pesquisarImovelDestino != null && !pesquisarImovelDestino.equals("")){

			Integer idImovelDestino = Integer.valueOf(form.getIdImovelDestino());
			String inscricaoImovel = fachada.pesquisarInscricaoImovel(idImovelDestino, true);

			if(inscricaoImovel == null){
				limparDadosImovelDestino(form);
				form.setInscricaoImovelDestino("IMOVEL INEXISTENTE");
				httpServletRequest.setAttribute("corImovelDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idImovelDestino");
			}else{

				// INSCRICAO IMOVEL
				form.setInscricaoImovelDestino(inscricaoImovel);

				// CLIENTE USUARIO DO IMOVEL
				Cliente cliente = fachada.pesquisarClienteUsuarioImovel(idImovelDestino);
				form.setNomeClienteUsuarioImovelDestino(cliente.getNome());

				// LIGACAO AGUA SITUACAO
				LigacaoAguaSituacao ligacaoAguaSituacao = fachada.pesquisarLigacaoAguaSituacao(idImovelDestino);
				form.setDescricaoLigacaoAguaSituacaoImovelDestino(ligacaoAguaSituacao.getDescricao());

				// LIGACAO ESOTO SITUACAO
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = fachada.pesquisarLigacaoEsgotoSituacao(idImovelDestino);
				form.setDescricaoLigacaoEsgotoSituacaoImovelDestino(ligacaoEsgotoSituacao.getDescricao());

				httpServletRequest.setAttribute("nomeCampo", "idImovelDestino");

			}
		}

		// IMOVEL DESTINO
		String pesquisarClienteDestino = httpServletRequest.getParameter("pesquisarClienteDestino");
		if(pesquisarClienteDestino != null && !pesquisarClienteDestino.equals("")){
			if(form.getIdClienteDestino() != null && !form.getIdClienteDestino().equals("")){
				Integer idCliente = Integer.valueOf(form.getIdClienteDestino());

				preencherDadosClienteDestino(actionMapping, form, httpServletRequest, idCliente);
			}
		}

		if(indicadorFaturamentoTitularDebito){
			httpServletRequest.setAttribute("indicadorFaturamentoTitularDebito", "S");
		}else{
			httpServletRequest.removeAttribute("indicadorFaturamentoTitularDebito");
		}
		
		return retorno;
	}

	private boolean preencherDadosClienteDestino(ActionMapping actionMapping, TransferenciaDebitoCreditoDadosImovelActionForm form,
					HttpServletRequest httpServletRequest, Integer idCliente){

		Fachada fachada = Fachada.getInstancia();

		boolean retorno = false;

		if(idCliente != null){
			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
			Collection<Cliente> clientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			Cliente cliente = null;
			if(clientes != null && !clientes.isEmpty()){
				cliente = clientes.iterator().next();

				if(cliente.getNome() != null){
					form.setNomeClienteDestino(cliente.getNome());
				}

				String cpfCnpjCliente = "";
				ClienteTipo clienteTipo = cliente.getClienteTipo();

				if(clienteTipo != null){
					Short indicadorPessoaFisicaJuridica = clienteTipo.getIndicadorPessoaFisicaJuridica();

					if(ClienteTipo.INDICADOR_PESSOA_FISICA.equals(indicadorPessoaFisicaJuridica)){
						cpfCnpjCliente = cliente.getCpfFormatado();
					}else{
						cpfCnpjCliente = cliente.getCnpjFormatado();
					}
				}

				form.setCpfCnpjClienteDestino(cpfCnpjCliente);
			}



		}else{
			httpServletRequest.setAttribute("clienteInexistente", "true");

			form.setNomeClienteDestino("CLIENTE INEXISTENTE");
			form.setCpfCnpjClienteDestino("");
		}

		return retorno;
	}

	private void limparDadosRA(TransferenciaDebitoCreditoDadosImovelActionForm form){

		form.setIdRegistroAtendimento("");
		form.setDescricaoEspecificacaoRA("");
		form.setIdImovelOrigem("");
		form.setInscricaoImovelOrigem("");
		form.setNomeClienteUsuarioImovelOrigem("");
		form.setDescricaoLigacaoAguaSituacaoImovelOrigem("");
		form.setDescricaoLigacaoEsgotoSituacaoImovelOrigem("");

	}

	private void limparDadosImovelDestino(TransferenciaDebitoCreditoDadosImovelActionForm form){

		form.setIdImovelDestino("");
		form.setInscricaoImovelDestino("");
		form.setNomeClienteUsuarioImovelDestino("");
		form.setDescricaoLigacaoAguaSituacaoImovelDestino("");
		form.setDescricaoLigacaoEsgotoSituacaoImovelDestino("");

	}

}
