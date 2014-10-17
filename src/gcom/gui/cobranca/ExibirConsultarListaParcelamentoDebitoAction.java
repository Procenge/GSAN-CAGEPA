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

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.parcelamento.FiltroParcelamento;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirConsultarListaParcelamentoDebitoAction
				extends GcomAction {

	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @author Saulo Lima
	 * @date 26/01/2009
	 *       Altera��o para pegar o cliente usu�rio do imovel que tenha dataFimRelacao igual a nulo
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta a a��o de retorno
		ActionForward retorno = actionMapping.findForward("consultarListaParcelamentoDebito");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		ParcelamentoDebitoActionForm parcelamentoDebitoActionForm = (ParcelamentoDebitoActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// Obt�m a facahda
		Fachada fachada = Fachada.getInstancia();

		// Pega os codigos que o usuario digitou para a pesquisa direta de imovel
		String codigoImovel = httpServletRequest.getParameter("codigoImovel");

		if(codigoImovel != null && !codigoImovel.trim().equals("")){

			// Pesquisa o imovel na base
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, codigoImovel));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
							ClienteRelacaoTipo.USUARIO));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial.municipio.unidadeFederacao");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.imovelPerfil");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoAguaSituacao");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoEsgotoSituacao");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");

			Collection<ClienteImovel> imovelPesquisado = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			// Se nenhum imovel for encontrado a mensagem � enviada para a p�gina
			if(imovelPesquisado != null && imovelPesquisado.isEmpty()){
				httpServletRequest.setAttribute("enderecoFormatado", "".toUpperCase());
				parcelamentoDebitoActionForm.setNomeCliente("");
				parcelamentoDebitoActionForm.setCpfCnpj("");
				parcelamentoDebitoActionForm.setSituacaoAgua("");
				parcelamentoDebitoActionForm.setSituacaoEsgoto("");
				httpServletRequest.setAttribute("corImovel", "exception");
				parcelamentoDebitoActionForm.setInscricao(ConstantesSistema.CODIGO_IMOVEL_INEXISTENTE);
			}
			// obtem o imovel pesquisado
			if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){

				ClienteImovel dadosImovel = (ClienteImovel) ((List) imovelPesquisado).get(0);

				// O endere�o foi encontrado
				if(dadosImovel.getImovel().getId() != null){
					parcelamentoDebitoActionForm.setCodigoImovel("" + dadosImovel.getImovel().getId());
				}
				if(dadosImovel.getImovel().getInscricaoFormatada() != null){
					parcelamentoDebitoActionForm.setInscricao("" + dadosImovel.getImovel().getInscricaoFormatada());
				}
				if(dadosImovel.getImovel().getLigacaoAguaSituacao() != null){
					parcelamentoDebitoActionForm.setSituacaoAgua("" + dadosImovel.getImovel().getLigacaoAguaSituacao().getDescricao());
				}
				if(dadosImovel.getImovel().getLigacaoEsgotoSituacao() != null){
					parcelamentoDebitoActionForm.setSituacaoEsgoto("" + dadosImovel.getImovel().getLigacaoEsgotoSituacao().getDescricao());
				}
				if(dadosImovel.getCliente().getNome() != null){
					parcelamentoDebitoActionForm.setNomeCliente("" + dadosImovel.getCliente().getNome());
				}
				if(dadosImovel.getImovel().getImovelPerfil() != null){
					parcelamentoDebitoActionForm.setImovelPerfil("" + dadosImovel.getImovel().getImovelPerfil().getDescricao());
				}
				if(dadosImovel.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica() == 1){
					if(dadosImovel.getCliente().getCpfFormatado() != null){
						parcelamentoDebitoActionForm.setCpfCnpj("" + dadosImovel.getCliente().getCpfFormatado());
					}
				}else{
					if(dadosImovel.getCliente().getCnpjFormatado() != null){
						parcelamentoDebitoActionForm.setCpfCnpj("" + dadosImovel.getCliente().getCnpjFormatado());
					}
				}
				if(dadosImovel.getImovel().getNumeroParcelamento() != null){
					parcelamentoDebitoActionForm.setParcelamento("" + dadosImovel.getImovel().getNumeroParcelamento());
				}
				if(dadosImovel.getImovel().getNumeroReparcelamento() != null){
					parcelamentoDebitoActionForm.setReparcelamento("" + dadosImovel.getImovel().getNumeroReparcelamento());
				}
				if(dadosImovel.getImovel().getNumeroReparcelamentoConsecutivos() != null){
					parcelamentoDebitoActionForm.setReparcelamentoConsecutivo(""
									+ dadosImovel.getImovel().getNumeroReparcelamentoConsecutivos());
				}
				// Manda a colecao pelo request
				httpServletRequest.setAttribute("imovelPesquisado", imovelPesquisado);
				String enderecoFormatado = "";
				try{
					enderecoFormatado = fachada.pesquisarEnderecoFormatado(Integer.valueOf(codigoImovel));
				}catch(NumberFormatException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				httpServletRequest.setAttribute("enderecoFormatado", enderecoFormatado);

				Collection<Parcelamento> colecaoParcelamento = fachada.consultarImovelParcelamentoDebito(Integer.valueOf(codigoImovel));

				if(colecaoParcelamento == null || colecaoParcelamento.isEmpty()){
					throw new ActionServletException("atencao.parcelamento.inexistente");
				}

				// [SB0002 � Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de
				// cobran�a administrativa]
				this.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario, codigoImovel, colecaoParcelamento);

				// this.validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(usuario,
				// codigoImovel, colecaoParcelamento);

				// [SB0004 � Verificar parcelamentos de cobran�a administrativa]
				this.verificarParcelamentosCobrancaAdministrativa(usuario, Integer.valueOf(codigoImovel), colecaoParcelamento);

				if(colecaoParcelamento != null && !colecaoParcelamento.isEmpty()){
					httpServletRequest.setAttribute("colecaoParcelamento", colecaoParcelamento);
				}else{
					throw new ActionServletException("atencao.parcelamento.inexistente");
				}
			}
		}
		// httpServletRequest.setAttribute("ParcelamentoDebitoActionForm",parcelamentoDebitoActionForm);
		return retorno;
	}

	/**
	 * [SB0002] � Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de cobran�a
	 * administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 */
	private void validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, String idImovel,
					Collection<Parcelamento> colecaoParcelamento){

		// 1.2. Caso o usu�rio n�o tenha autoriza��o de acesso ao im�vel
		if(Fachada.getInstancia().obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, Integer.valueOf(idImovel),
						ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA).equals(ConstantesSistema.NAO)){

			Collection<Integer> tiposDebito = Fachada.getInstancia().getTiposDebitoParcelamento();

			boolean parcelamentoEfetuadoPelaEmpresaCobrancaAdministrativa = false;

			if(!Util.isVazioOrNulo(colecaoParcelamento)){
				for(Parcelamento parcelamento : colecaoParcelamento){
					if(parcelamento.getUsuario() != null && parcelamento.getUsuario().getEmpresa() != null){
						if(parcelamento.getUsuario().getEmpresa().getId().equals(usuario.getEmpresa().getId())){
							parcelamentoEfetuadoPelaEmpresaCobrancaAdministrativa = true;
							break;
						}
					}
				}
			}

			FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
			filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID, idImovel));
			Collection<DebitoACobrar> colecaoDebitosACobrar = Fachada.getInstancia().pesquisar(filtroDebitoACobrar,
							DebitoACobrar.class.getName());

			boolean parcelamentoRemuneravel = false;
			if(!Util.isVazioOrNulo(colecaoDebitosACobrar)){
				for(DebitoACobrar debitoACobrar : colecaoDebitosACobrar){
					if(tiposDebito.contains(debitoACobrar.getDebitoTipo().getId())){
						parcelamentoRemuneravel = true;
						break;
					}
				}
			}

			if(!parcelamentoRemuneravel){

				Collection<Integer> tiposDebitoGuia = new ArrayList<Integer>();
				tiposDebitoGuia.addAll(tiposDebito);
				tiposDebitoGuia.add(DebitoTipo.ENTRADA_PARCELAMENTO);
				tiposDebitoGuia.add(DebitoTipo.ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA);

				FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
				filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_IMOVEL_ID,
								idImovel));
				filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);

				Collection<GuiaPagamentoPrestacao> colecaoGuiasPagamento = Fachada.getInstancia().pesquisar(filtroGuiaPagamentoPrestacao,
								GuiaPagamentoPrestacao.class.getName());

				if(!Util.isVazioOrNulo(colecaoGuiasPagamento)){
					for(GuiaPagamentoPrestacao guiaPagamentoPrestacao : colecaoGuiasPagamento){
						if(tiposDebitoGuia.contains(guiaPagamentoPrestacao.getDebitoTipo().getId())){
							parcelamentoRemuneravel = true;
							break;
						}
					}
				}
			}

			boolean temPermissao = true;

			// 1.2.4. Caso algum dos parcelamentos do im�vel seja remuner�vel
			if(parcelamentoRemuneravel){

				// 1.2.4.1. Caso nenhum dos parcelamentos do im�vel tenha sido efetuado pela empresa
				// de cobran�a administrativa do usu�rio logado
				if(!parcelamentoEfetuadoPelaEmpresaCobrancaAdministrativa){
					temPermissao = false;
				}
			}else{

				// 1.2.5. Caso contr�rio, ou seja, nenhum dos parcelamentos do im�vel seja
				// remuner�vel:
				temPermissao = false;
			}

			if(!temPermissao){
				throw new ActionServletException("atencao.usuario_empresa_cobranca_administrativa_sem_acesso_imovel_desparcelamento",
								usuario.getNomeUsuario(), idImovel);

			}
		}
	}

	/**
	 * [SB0003] � Validar autoriza��o de acesso ao im�vel em cobran�a administrativa pelos usu�rios
	 * da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @author Saulo Lima
	 * @date 01/08/2013
	 * @param usuario
	 * @param colecaoParcelamento
	 */
	private void validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(Usuario usuario, String codigoImovel,
					Collection<Parcelamento> colecaoParcelamento){

		Collection<Integer> colecaoIdsDebitoACobrarNaoPermitidos = new ArrayList<Integer>();
		boolean temPermissao = true;

		// Monta a lista contendo os debitos a cobrar n�o permitidos
		try{
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
											.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos
							.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
											.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema", e, e.getMessage());
		}

		Short retornoValidacao = Fachada.getInstancia().obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario,
						Integer.valueOf(codigoImovel), ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE);

		boolean temPermissaoEspecial = Fachada.getInstancia().verificarPermissaoEspecial(
						PermissaoEspecial.ACESSAR_DADOS_IMOVEL_COBRANCA_ADMINISTRATIVA, usuario);

		for(Parcelamento parcelamentoRetorno : colecaoParcelamento){

			Integer idParcelamento = parcelamentoRetorno.getId();

			// 1. O sistema valida a autoriza��o de acesso ao im�vel em cobran�a administrativa
			// pelos usu�rios da empresa contratante de acordo com as seguintes regras
			FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
			filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, idParcelamento));
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
			filtroParcelamento.setInitializeLazy(true);

			Parcelamento parcelamento = (Parcelamento) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroParcelamento,
							Parcelamento.class.getName()));

			Integer idCobrancaForma = parcelamento.getCobrancaForma().getId();

			// 1.1. Caso a forma de cobran�a do parcelamento seja "Cobran�a em Conta"
			if(idCobrancaForma.equals(CobrancaForma.COBRANCA_EM_CONTA)){
				Collection<DebitoACobrar> colecaoDebitoACobrarItem = Fachada.getInstancia().pesquisarItensDebitosACobrarPorParcelamento(
								idParcelamento);

				// 1.1.1. Caso existam, na lista de d�bitos a cobrar do parcelamento
				if(!Util.isVazioOrNulo(colecaoDebitoACobrarItem)){

					// d�bitos a cobrar de cobran�a administrativa
					for(DebitoACobrar debitoACobrarItem : colecaoDebitoACobrarItem){
						if(colecaoIdsDebitoACobrarNaoPermitidos.contains(debitoACobrarItem.getDebitoTipo().getId())
										&& retornoValidacao.equals(ConstantesSistema.NAO)){
							// 1.1.1.2. Caso o usu�rio n�o tenha autoriza��o de acesso ao im�vel
							temPermissao = false;
							break;
						}
					}

					// 1.1.2. Caso contr�rio, ou seja, n�o existam, na lista de d�bitos a cobrar do
					// parcelamento, d�bitos a cobrar de cobran�a administrativa
					if(temPermissao){
						// 1.1.2.1. Caso o usu�rio logado no sistema n�o perten�a a uma empresa de
						// cobran�a administrativa
						if(!Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

							// 1.1.2.1.1.1. Caso o usu�rio logado no sistema n�o possua permiss�o
							// especial para acesso aos dados do im�vel em cobran�a administrativa
							if(!temPermissaoEspecial){

								// 1.1.2.1.1. Caso existam, na lista de d�bitos a cobrar do
								// parcelamento, d�bitos a cobrar remuner�veis
								for(DebitoACobrar debitoACobrarItem : colecaoDebitoACobrarItem){
									if(debitoACobrarItem.getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.SIM)){
										throw new ActionServletException(
														"atencao.usuario_empresa_sem_acesso_imovel_desparcelamento_cobranca_administrativa_item",
														codigoImovel, usuario.getNomeUsuario());
									}
								}

							}
						}

					}
				}

			}else{
				// 1.2. Caso contr�rio, ou seja, caso a forma de cobran�a do parcelamento n�o seja
				// "Cobran�a em Conta"
				Collection<Integer> colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento = Fachada.getInstancia()
								.obterDebitosGuiasPagamentoPrestacoesParcelamento(idParcelamento);

				// 1.2.1. Caso existam, na lista de guias de pagamento do parcelamento
				if(!Util.isVazioOrNulo(colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento)){
					for(Integer idDebito : colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento){
						if(colecaoIdsDebitoACobrarNaoPermitidos.contains(idDebito) && retornoValidacao.equals(ConstantesSistema.NAO)){
							// 1.2.1.2. Caso o usu�rio n�o tenha autoriza��o de acesso ao im�vel
							temPermissao = false;
							break;
						}
					}

					// Caso contr�rio, ou seja, n�o existam, na lista de guias de pagamento do
					// parcelamento, guias de pagamento de cobran�a administrativa
					if(temPermissao){
						// 1.2.2.1. Caso o usu�rio logado no sistema n�o perten�a a uma empresa de
						// cobran�a administrativa
						if(!Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

							// 1.2.2.1.1.1. Caso o usu�rio logado no sistema n�o possua permiss�o
							// especial para acesso aos dados do im�vel em cobran�a administrativa
							if(!temPermissaoEspecial){

								FiltroGuiaPagamento filtro = new FiltroGuiaPagamento();
								filtro.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, idParcelamento));

								Collection<GuiaPagamento> colecaoGuias = Fachada.getInstancia().pesquisar(filtro,
												GuiaPagamento.class.getName());

								if(!Util.isVazioOrNulo(colecaoGuias)){

									for(GuiaPagamento guiaPagamento : colecaoGuias){

										FiltroGuiaPagamentoPrestacao filtroPrestacao = new FiltroGuiaPagamentoPrestacao();
										filtroPrestacao.adicionarParametro(new ParametroSimples(
														FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID, guiaPagamento.getId()));

										Collection<GuiaPagamentoPrestacao> colecaoGuiaPretacao = Fachada.getInstancia().pesquisar(
														filtroPrestacao, GuiaPagamentoPrestacao.class.getName());

										if(!Util.isVazioOrNulo(colecaoGuiaPretacao)){
											for(GuiaPagamentoPrestacao guiaPagamentoPrestacao : colecaoGuiaPretacao){

												// 1.2.2.1.1. Caso existam, na lista de guias de
												// pagamento do parcelamento
												if(guiaPagamentoPrestacao.getIndicadorRemuneraCobrancaAdministrativa()
																.equals(ConstantesSistema.SIM)){
													throw new ActionServletException(
																	"atencao.usuario_empresa_sem_acesso_imovel_desparcelamento_cobranca_administrativa_item",
																	codigoImovel, usuario.getNomeUsuario());
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if(!temPermissao){
			throw new ActionServletException("atencao.usuario_empresa_sem_acesso_imovel_desparcelamento_cobranca_administrativa",
							codigoImovel, usuario.getNomeUsuario());
		}
	}

	/**
	 * [SB0004] � Verificar parcelamentos de cobran�a administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 * @param colecaoParcelamentos
	 */
	private void verificarParcelamentosCobrancaAdministrativa(Usuario usuario, Integer idImovel,
					Collection<Parcelamento> colecaoParcelamentos){

		Collection<Integer> colecaoIdsParcelamentosRemover = null;
		boolean idsConsultados = false;

		// 1. Caso o usu�rio logado perten�a a uma empresa de cobran�a administrativa
		if(Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

			if(!Util.isVazioOrNulo(colecaoParcelamentos)){
				Collection<Parcelamento> colecaoParcelamentosClone = (ArrayList<Parcelamento>) ((ArrayList<Parcelamento>) colecaoParcelamentos)
								.clone();
				Iterator dadosColecaoParcelamentos = colecaoParcelamentosClone.iterator();
				while(dadosColecaoParcelamentos.hasNext()){
					Parcelamento parcelamento = (Parcelamento) dadosColecaoParcelamentos.next();

					// Consulta os ids de parcelamentos a remover apenas uma vez
					if(!idsConsultados){
						// Pesquisa os ids de parcelamentos a serem excluidos

						colecaoIdsParcelamentosRemover = Fachada.getInstancia().obterIdsParcelamentoEmpresaDiferente(
										usuario.getEmpresa().getId(), idImovel);
						idsConsultados = true;
					}

					// Caso o id do parcelamento exista na cole��o de
					// Ids a serem removidos exclui o parcelamento da cole��o passada como parametro
					if(colecaoIdsParcelamentosRemover.contains(parcelamento.getId())){
						colecaoParcelamentos.remove(parcelamento);
					}
				}
			}
		}
	}
}
