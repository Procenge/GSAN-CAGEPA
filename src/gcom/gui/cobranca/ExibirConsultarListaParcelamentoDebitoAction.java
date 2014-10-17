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
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class ExibirConsultarListaParcelamentoDebitoAction
				extends GcomAction {

	/**
	 * <<Descrição do método>>
	 * 
	 * @author Saulo Lima
	 * @date 26/01/2009
	 *       Alteração para pegar o cliente usuário do imovel que tenha dataFimRelacao igual a nulo
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta a ação de retorno
		ActionForward retorno = actionMapping.findForward("consultarListaParcelamentoDebito");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		ParcelamentoDebitoActionForm parcelamentoDebitoActionForm = (ParcelamentoDebitoActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// Obtém a facahda
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

			// Se nenhum imovel for encontrado a mensagem é enviada para a página
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

				// O endereço foi encontrado
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

				// [SB0002 – Validar autorização de acesso ao imóvel pelos usuários das empresas de
				// cobrança administrativa]
				this.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario, codigoImovel, colecaoParcelamento);

				// this.validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(usuario,
				// codigoImovel, colecaoParcelamento);

				// [SB0004 – Verificar parcelamentos de cobrança administrativa]
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
	 * [SB0002] – Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 */
	private void validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, String idImovel,
					Collection<Parcelamento> colecaoParcelamento){

		// 1.2. Caso o usuário não tenha autorização de acesso ao imóvel
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

			// 1.2.4. Caso algum dos parcelamentos do imóvel seja remunerável
			if(parcelamentoRemuneravel){

				// 1.2.4.1. Caso nenhum dos parcelamentos do imóvel tenha sido efetuado pela empresa
				// de cobrança administrativa do usuário logado
				if(!parcelamentoEfetuadoPelaEmpresaCobrancaAdministrativa){
					temPermissao = false;
				}
			}else{

				// 1.2.5. Caso contrário, ou seja, nenhum dos parcelamentos do imóvel seja
				// remunerável:
				temPermissao = false;
			}

			if(!temPermissao){
				throw new ActionServletException("atencao.usuario_empresa_cobranca_administrativa_sem_acesso_imovel_desparcelamento",
								usuario.getNomeUsuario(), idImovel);

			}
		}
	}

	/**
	 * [SB0003] – Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
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

		// Monta a lista contendo os debitos a cobrar não permitidos
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

			// 1. O sistema valida a autorização de acesso ao imóvel em cobrança administrativa
			// pelos usuários da empresa contratante de acordo com as seguintes regras
			FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
			filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, idParcelamento));
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
			filtroParcelamento.setInitializeLazy(true);

			Parcelamento parcelamento = (Parcelamento) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroParcelamento,
							Parcelamento.class.getName()));

			Integer idCobrancaForma = parcelamento.getCobrancaForma().getId();

			// 1.1. Caso a forma de cobrança do parcelamento seja "Cobrança em Conta"
			if(idCobrancaForma.equals(CobrancaForma.COBRANCA_EM_CONTA)){
				Collection<DebitoACobrar> colecaoDebitoACobrarItem = Fachada.getInstancia().pesquisarItensDebitosACobrarPorParcelamento(
								idParcelamento);

				// 1.1.1. Caso existam, na lista de débitos a cobrar do parcelamento
				if(!Util.isVazioOrNulo(colecaoDebitoACobrarItem)){

					// débitos a cobrar de cobrança administrativa
					for(DebitoACobrar debitoACobrarItem : colecaoDebitoACobrarItem){
						if(colecaoIdsDebitoACobrarNaoPermitidos.contains(debitoACobrarItem.getDebitoTipo().getId())
										&& retornoValidacao.equals(ConstantesSistema.NAO)){
							// 1.1.1.2. Caso o usuário não tenha autorização de acesso ao imóvel
							temPermissao = false;
							break;
						}
					}

					// 1.1.2. Caso contrário, ou seja, não existam, na lista de débitos a cobrar do
					// parcelamento, débitos a cobrar de cobrança administrativa
					if(temPermissao){
						// 1.1.2.1. Caso o usuário logado no sistema não pertença a uma empresa de
						// cobrança administrativa
						if(!Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

							// 1.1.2.1.1.1. Caso o usuário logado no sistema não possua permissão
							// especial para acesso aos dados do imóvel em cobrança administrativa
							if(!temPermissaoEspecial){

								// 1.1.2.1.1. Caso existam, na lista de débitos a cobrar do
								// parcelamento, débitos a cobrar remuneráveis
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
				// 1.2. Caso contrário, ou seja, caso a forma de cobrança do parcelamento não seja
				// "Cobrança em Conta"
				Collection<Integer> colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento = Fachada.getInstancia()
								.obterDebitosGuiasPagamentoPrestacoesParcelamento(idParcelamento);

				// 1.2.1. Caso existam, na lista de guias de pagamento do parcelamento
				if(!Util.isVazioOrNulo(colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento)){
					for(Integer idDebito : colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento){
						if(colecaoIdsDebitoACobrarNaoPermitidos.contains(idDebito) && retornoValidacao.equals(ConstantesSistema.NAO)){
							// 1.2.1.2. Caso o usuário não tenha autorização de acesso ao imóvel
							temPermissao = false;
							break;
						}
					}

					// Caso contrário, ou seja, não existam, na lista de guias de pagamento do
					// parcelamento, guias de pagamento de cobrança administrativa
					if(temPermissao){
						// 1.2.2.1. Caso o usuário logado no sistema não pertença a uma empresa de
						// cobrança administrativa
						if(!Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

							// 1.2.2.1.1.1. Caso o usuário logado no sistema não possua permissão
							// especial para acesso aos dados do imóvel em cobrança administrativa
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
	 * [SB0004] – Verificar parcelamentos de cobrança administrativa
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

		// 1. Caso o usuário logado pertença a uma empresa de cobrança administrativa
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

					// Caso o id do parcelamento exista na coleção de
					// Ids a serem removidos exclui o parcelamento da coleção passada como parametro
					if(colecaoIdsParcelamentosRemover.contains(parcelamento.getId())){
						colecaoParcelamentos.remove(parcelamento);
					}
				}
			}
		}
	}
}
