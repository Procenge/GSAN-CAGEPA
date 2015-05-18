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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.relatorio.cobranca;

import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.cobranca.ConsultarDebitoImovelActionForm;
import gcom.interceptor.RegistradorOperacao;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.*;
import gcom.seguranca.acesso.Argumento;
import gcom.seguranca.acesso.DocumentoEmissaoEfetuada;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.cobranca.ModeloRelatorioCertidaoNegativaDebitos;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

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
 * Action responsável pela exibição do relatório de certidão negativa
 * 
 * @author Virgínia Melo
 * @date 06/02/2009
 * @author eduardo henrique
 * @date 08/02/2009
 *       Alterações/Correções solicitadas por ADA
 */
public class GerarRelatorioCertidaoNegativaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		ConsultarDebitoImovelActionForm consultarDebitoImovelActionForm = (ConsultarDebitoImovelActionForm) actionForm;

		// Verificar se o valor dos débitos é zero
		String valorDebitosRequest = httpServletRequest.getParameter("valorDebitos");

		// Código do Imóvel
		String codigoImovel = "";

		if(sessao.getAttribute("codigoImovel") != null){

			codigoImovel = (String) sessao.getAttribute("codigoImovel");
		}else{

			codigoImovel = consultarDebitoImovelActionForm.getCodigoImovel();
		}

		Cliente clienteUsuarioAtualImovel = fachada.pesquisarClienteUsuarioImovel(new Integer(codigoImovel));

		// pesquisar cliente usuário
		String nomeCliente = "";
		if(clienteUsuarioAtualImovel != null){

			nomeCliente = clienteUsuarioAtualImovel.getNome();
		}else{

			throw new ActionServletException("atencao.cliente_usuario_nao_encontrado");
		}

		Collection<ContaValoresHelper> colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
		Collection<DebitoACobrar> colecaoDebitoACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar");
		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = (Collection<GuiaPagamentoValoresHelper>) sessao
						.getAttribute("colecaoGuiaPagamentoValores");

		boolean possuiApenasDebitosClienteDiferenteAtual = true;

		if(!Util.isVazioOrNulo(colecaoContaValores)){

			for(ContaValoresHelper contaValoresHelper : colecaoContaValores){

				if(contaValoresHelper.getConta().getDataVencimentoConta() != null
								&& Util.compararData(contaValoresHelper.getConta().getDataVencimentoConta(), new Date()) == -1){

					ClienteConta clienteContaUsuarioDebito = fachada.pesquisarClienteContaPorTipoRelacao(contaValoresHelper.getConta()
									.getId(),
									ClienteRelacaoTipo.USUARIO);

					if(clienteContaUsuarioDebito.getCliente().getId().equals(clienteUsuarioAtualImovel.getId())){

						possuiApenasDebitosClienteDiferenteAtual = false;
						break;
					}
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){

			for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){

				FiltroClienteDebitoACobrar filtroClienteDebitoACobrar = new FiltroClienteDebitoACobrar();
				filtroClienteDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroClienteDebitoACobrar.DEBITO_A_COBRAR_ID,
								debitoACobrar.getId()));
				filtroClienteDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroClienteDebitoACobrar.CLIENTE_ID,
								clienteUsuarioAtualImovel.getId()));

				Collection<ClienteDebitoACobrar> colecaoClienteDebitoACobrar = fachada.pesquisar(filtroClienteDebitoACobrar,
								ClienteDebitoACobrar.class.getName());

				if(!Util.isVazioOrNulo(colecaoClienteDebitoACobrar)){

					possuiApenasDebitosClienteDiferenteAtual = false;
					break;
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){

			for(GuiaPagamentoValoresHelper guiaPagamentoValoresHelper : colecaoGuiaPagamentoValores){

				FiltroClienteGuiaPagamento filtroClienteGuiaPagamento = new FiltroClienteGuiaPagamento();
				filtroClienteGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroClienteGuiaPagamento.GUIA_PAGAMENTO_ID,
								guiaPagamentoValoresHelper.getIdGuiaPagamento()));
				filtroClienteGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroClienteGuiaPagamento.CLIENTE_ID,
								clienteUsuarioAtualImovel.getId()));

				Collection<ClienteGuiaPagamento> colecaoClienteGuia = fachada.pesquisar(filtroClienteGuiaPagamento,
								ClienteGuiaPagamento.class.getName());

				if(!Util.isVazioOrNulo(colecaoClienteGuia)){

					possuiApenasDebitosClienteDiferenteAtual = false;
					break;
				}
			}
		}

		boolean permiteGerarCertidaoNegativa = false;

		BigDecimal valorDebitos = BigDecimal.ZERO;

		if(valorDebitosRequest != null && !valorDebitosRequest.equals("")){

			valorDebitos = Util.formatarMoedaRealparaBigDecimal(valorDebitosRequest);
		}

		// Se o valor zero ou os débitos existentes pertecerem a clientes diferentes do cliente
		// usuário atual do imóvel
		if(valorDebitos.compareTo(BigDecimal.ZERO) == 0 || possuiApenasDebitosClienteDiferenteAtual){

			permiteGerarCertidaoNegativa = true;
		}

		if(!permiteGerarCertidaoNegativa){

			throw new ActionServletException("atencao.emissao_certidao_nao_permitida");
		}

		String enderecoFormatado = "";
		String periodoInicial = "";
		String periodoFinal = "";

		Imovel imovelCertidao = fachada.pesquisarImovel(Integer.valueOf(codigoImovel));
		if(imovelCertidao == null){
			throw new ActionServletException("atencao.imovel_certidao_nao_encontrado");
		}

		// Endereço do Cliente
		enderecoFormatado = httpServletRequest.getParameter("enderecoFormatado");
		periodoInicial = consultarDebitoImovelActionForm.getReferenciaInicial();
		periodoFinal = consultarDebitoImovelActionForm.getReferenciaFinal();

		Usuario usuario = null;

		if(sessao.getAttribute("usuarioLogado") != null){
			usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		}

		String parametroModeloRelatorioCertidao = "";
		try{

			parametroModeloRelatorioCertidao = ParametroCobranca.P_MODELO_RELATORIO_CERTIDAO_NEGATIVA_DEBITOS.executar();
		}catch(ControladorException e){

			e.printStackTrace();
		}

		if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.UM.getValor())){

			// Verifica se o período de ref. foi informado.
			if((periodoInicial == null || periodoInicial.equals("")) || (periodoFinal == null || periodoFinal.equals(""))){
				throw new ActionServletException("atencao.informar_periodo_referencia_debito");
			}

			// Verifica se o período final é maior do que a data atual
			int anoMesAtual = Util.getAnoMesComoInt(new Date());
			String anoMesAtualSemBarra = String.valueOf(anoMesAtual);
			String anoMesFinalSemBarra = Util.formatarMesAnoParaAnoMes(periodoFinal);

			if(Util.compararAnoMesReferencia(anoMesFinalSemBarra, anoMesAtualSemBarra, ">")){
				throw new ActionServletException("atencao.periodo_referencia_maior_hoje");
			}
		}

		// Verifica endereço
		if(enderecoFormatado == null){
			throw new ActionServletException("atencao.endereco_cliente_nao_encontrado");
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		TarefaRelatorio relatorio = null;

		SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute("sistemaParametro");

		if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.UM.getValor())){

			relatorio = new RelatorioCertidaoNegativaModelo1((Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			if(sistemaParametro != null){
				relatorio.addParametro("empresa", sistemaParametro.getNomeEmpresa());
			}

			// Dados do Imóvel (Inscrição, Matrícula, Dados Hidrômetro)
			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			// Hidrometro
			if(imovelCertidao.getLigacaoAgua() != null && imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
							&& imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro() != null){
				relatorio.addParametro("numeroHidrometro", imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico()
								.getHidrometro().getNumero());
			}else{
				relatorio.addParametro("numeroHidrometro", "NAO INSTALADO");
			}

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}

			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoFormatado);
			relatorio.addParametro("periodoInicial", periodoInicial);
			relatorio.addParametro("periodoFinal", periodoFinal);
			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

			DocumentoEmissaoEfetuada documentoEmissaoEfetuada = new DocumentoEmissaoEfetuada();

			// ------------ REGISTRAR TRANSação----------------------------

			documentoEmissaoEfetuada.setImovel(imovelCertidao);
			documentoEmissaoEfetuada.setUsuario(usuario);
			DocumentoTipo documentoTipo = new DocumentoTipo();
			documentoTipo.setId(DocumentoTipo.CERTIDAO_NEGATIVA);
			documentoEmissaoEfetuada.setDocumentoTipo(documentoTipo);
			documentoEmissaoEfetuada.setUltimaAlteracao(new Date());

			if(!Util.isVazioOuBranco(periodoInicial)){
				documentoEmissaoEfetuada.setReferenciaInicialDebito(periodoInicial);
			}
			if(!Util.isVazioOuBranco(periodoFinal)){
				documentoEmissaoEfetuada.setReferenciaFinalDebito(periodoFinal);
			}

			Argumento argumento = new Argumento();
			argumento.setId(Argumento.IMOVEL);
			

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_NEGATIVA,
							imovelCertidao.getId(), argumento, imovelCertidao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			RegistradorOperacao.set(registradorOperacao);
			documentoEmissaoEfetuada.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(documentoEmissaoEfetuada);
			this.getFachada().inserir(documentoEmissaoEfetuada);

			// ------------ REGISTRAR TRANSAÇÃO ----------------

		}else if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.DOIS.getValor())){

			DocumentoEmissaoEfetuada documentoEmissaoEfetuada = new DocumentoEmissaoEfetuada();

			// ------------ REGISTRAR TRANSação----------------------------

			documentoEmissaoEfetuada.setImovel(imovelCertidao);
			documentoEmissaoEfetuada.setUsuario(usuario);
			DocumentoTipo documentoTipo = new DocumentoTipo();
			documentoTipo.setId(DocumentoTipo.CERTIDAO_NEGATIVA);
			documentoEmissaoEfetuada.setDocumentoTipo(documentoTipo);
			documentoEmissaoEfetuada.setUltimaAlteracao(new Date());

			if(!Util.isVazioOuBranco(periodoInicial)){
				documentoEmissaoEfetuada.setReferenciaInicialDebito(periodoInicial);
			}
			if(!Util.isVazioOuBranco(periodoFinal)){
				documentoEmissaoEfetuada.setReferenciaFinalDebito(periodoFinal);
			}

			Argumento argumento = new Argumento();
			argumento.setId(Argumento.IMOVEL);
			

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_NEGATIVA,
							imovelCertidao.getId(), argumento, imovelCertidao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			RegistradorOperacao.set(registradorOperacao);
			documentoEmissaoEfetuada.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(documentoEmissaoEfetuada);
			this.getFachada().inserir(documentoEmissaoEfetuada);

			// ------------ REGISTRAR TRANSAÇÃO ----------------


			relatorio = new RelatorioCertidaoNegativaModelo2((Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			if(periodoInicial == null){
				periodoInicial = "";
			}

			if(periodoFinal == null){
				periodoFinal = "";
			}

			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoFormatado);
			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			relatorio.addParametro("periodoInicial", periodoInicial);
			relatorio.addParametro("periodoFinal", periodoFinal);

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}

		}else if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.TRES.getValor())){

			DocumentoEmissaoEfetuada documentoEmissaoEfetuada = new DocumentoEmissaoEfetuada();

			// ------------ REGISTRAR TRANSação----------------------------

			documentoEmissaoEfetuada.setImovel(imovelCertidao);
			documentoEmissaoEfetuada.setUsuario(usuario);
			DocumentoTipo documentoTipo = new DocumentoTipo();
			documentoTipo.setId(DocumentoTipo.CERTIDAO_NEGATIVA);
			documentoEmissaoEfetuada.setDocumentoTipo(documentoTipo);
			documentoEmissaoEfetuada.setUltimaAlteracao(new Date());

			if(!Util.isVazioOuBranco(periodoInicial)){
				documentoEmissaoEfetuada.setReferenciaInicialDebito(periodoInicial);
			}
			if(!Util.isVazioOuBranco(periodoFinal)){
				documentoEmissaoEfetuada.setReferenciaFinalDebito(periodoFinal);
			}

			Argumento argumento = new Argumento();
			argumento.setId(Argumento.IMOVEL);

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_NEGATIVA,
							imovelCertidao.getId(), argumento, imovelCertidao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			RegistradorOperacao.set(registradorOperacao);
			documentoEmissaoEfetuada.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(documentoEmissaoEfetuada);
			this.getFachada().inserir(documentoEmissaoEfetuada);

			// ------------ REGISTRAR TRANSAÇÃO ----------------

			relatorio = new RelatorioCertidaoNegativaModelo3((Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoFormatado);
			String cidade = "";
			if(imovelCertidao.getLogradouroCep() != null && imovelCertidao.getLogradouroCep().getCep() != null){
				cidade = imovelCertidao.getLogradouroCep().getCep().getMunicipio();
			
			}
			relatorio.addParametro("cidade", cidade);
			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}
		}else if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.QUATRO.getValor())){

			DocumentoEmissaoEfetuada documentoEmissaoEfetuada = new DocumentoEmissaoEfetuada();

			// ------------ REGISTRAR TRANSação----------------------------

			documentoEmissaoEfetuada.setImovel(imovelCertidao);
			documentoEmissaoEfetuada.setUsuario(usuario);
			DocumentoTipo documentoTipo = new DocumentoTipo();
			documentoTipo.setId(DocumentoTipo.CERTIDAO_NEGATIVA);
			documentoEmissaoEfetuada.setDocumentoTipo(documentoTipo);
			documentoEmissaoEfetuada.setUltimaAlteracao(new Date());

			if(!Util.isVazioOuBranco(periodoInicial)){
				documentoEmissaoEfetuada.setReferenciaInicialDebito(periodoInicial);
			}
			if(!Util.isVazioOuBranco(periodoFinal)){
				documentoEmissaoEfetuada.setReferenciaFinalDebito(periodoFinal);
			}

			Argumento argumento = new Argumento();
			argumento.setId(Argumento.IMOVEL);

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_NEGATIVA,
							imovelCertidao.getId(), argumento, imovelCertidao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			RegistradorOperacao.set(registradorOperacao);
			documentoEmissaoEfetuada.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(documentoEmissaoEfetuada);
			this.getFachada().inserir(documentoEmissaoEfetuada);

			// ------------ REGISTRAR TRANSAÇÃO ----------------

			relatorio = new RelatorioCertidaoNegativaModelo4(usuario);

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){

				relatorio.addParametro("usuarioEmissor", usuario.getFuncionario().getId().toString() + "-"
								+ usuario.getFuncionario().getNome());
			}else{

				relatorio.addParametro("usuarioEmissor", usuario.getLogin() + "-" + usuario.getNomeUsuario());
			}

			relatorio.addParametro("enderecoFormatado", enderecoFormatado);
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId());
			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		}

		String parametroEmiteCertidaoNegativaComEfeitoPositiva = "";
		try{

			parametroEmiteCertidaoNegativaComEfeitoPositiva = ParametroCobranca.P_EMITE_CERTIDAO_NEGATIVA_EFEITO_POSITIVA.executar();
		}catch(ControladorException e){

			e.printStackTrace();
		}

		// Caso a empresa emita Certidão Negativa de Débito - Com Efeito Positiva
		if(parametroEmiteCertidaoNegativaComEfeitoPositiva.equals(ConstantesSistema.SIM.toString())){


			// E caso o imóvel possua apenas débitos a cobrar de parcelamentos associado ao cliente
			// usuário atual no imóvel, o sistema gera a Certidão Negativa de Débito - Com Efeito
			// Positiva para o imóvel
			boolean possuiApenasDebitosACobrarParcelamentosClienteAtualImovel = true;

			if(!Util.isVazioOrNulo(colecaoContaValores)){

				possuiApenasDebitosACobrarParcelamentosClienteAtualImovel = false;
			}else if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){

				possuiApenasDebitosACobrarParcelamentosClienteAtualImovel = false;
			}else if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){

				for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){

					FiltroClienteDebitoACobrar filtroClienteDebitoACobrar = new FiltroClienteDebitoACobrar();
					filtroClienteDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroClienteDebitoACobrar.DEBITO_A_COBRAR_ID,
									debitoACobrar.getId()));
					filtroClienteDebitoACobrar.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroClienteDebitoACobrar.CLIENTE_ID,
									clienteUsuarioAtualImovel.getId()));
					filtroClienteDebitoACobrar
									.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteDebitoACobrar.DEBITO_A_COBRAR_PARCELAMENTO);
					filtroClienteDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteDebitoACobrar.CLIENTE);

					Collection<ClienteDebitoACobrar> colecaoClienteDebitoACobrar = fachada.pesquisar(filtroClienteDebitoACobrar,
									ClienteDebitoACobrar.class.getName());

					if(!Util.isVazioOrNulo(colecaoClienteDebitoACobrar)){

						for(ClienteDebitoACobrar clienteDebitoACobrar : colecaoClienteDebitoACobrar){

							if(clienteDebitoACobrar.getDebitoACobrar().getParcelamento() == null){

								possuiApenasDebitosACobrarParcelamentosClienteAtualImovel = false;
								break;
							}else if(clienteDebitoACobrar.getDebitoACobrar().getParcelamento() != null
											&& !clienteDebitoACobrar.getCliente().getId().equals(clienteUsuarioAtualImovel.getId())){

								possuiApenasDebitosACobrarParcelamentosClienteAtualImovel = false;
								break;
							}
						}
					}
				}
			}else{

				possuiApenasDebitosACobrarParcelamentosClienteAtualImovel = false;
			}

			if(possuiApenasDebitosACobrarParcelamentosClienteAtualImovel){

				relatorio = new RelatorioCertidaoNegativaComEfeitoPositivaModelo4(usuario);

				DocumentoEmissaoEfetuada documentoEmissaoEfetuada = new DocumentoEmissaoEfetuada();

				// ------------ REGISTRAR TRANSação----------------------------

				documentoEmissaoEfetuada.setImovel(imovelCertidao);
				documentoEmissaoEfetuada.setUsuario(usuario);
				DocumentoTipo documentoTipo = new DocumentoTipo();
				documentoTipo.setId(DocumentoTipo.CERTIDAO_NEGATIVAO_COM_EFEITO_POSITIVA);
				documentoEmissaoEfetuada.setDocumentoTipo(documentoTipo);
				documentoEmissaoEfetuada.setUltimaAlteracao(new Date());

				if(!Util.isVazioOuBranco(periodoInicial)){
					documentoEmissaoEfetuada.setReferenciaInicialDebito(periodoInicial);
				}
				if(!Util.isVazioOuBranco(periodoFinal)){
					documentoEmissaoEfetuada.setReferenciaFinalDebito(periodoFinal);
				}

				Argumento argumento = new Argumento();
				argumento.setId(Argumento.IMOVEL);

				RegistradorOperacao registradorOperacao = new RegistradorOperacao(
								Operacao.OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_NEGATIVA_COM_EFEITO_POSITIVA,
								imovelCertidao.getId(), argumento, imovelCertidao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
												UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
				RegistradorOperacao.set(registradorOperacao);
				documentoEmissaoEfetuada.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(documentoEmissaoEfetuada);
				this.getFachada().inserir(documentoEmissaoEfetuada);

				// ------------ REGISTRAR TRANSAÇÃO ----------------

				if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){

					relatorio.addParametro("usuarioEmissor", usuario.getFuncionario().getId().toString() + "-"
									+ usuario.getFuncionario().getNome());
				}else{

					relatorio.addParametro("usuarioEmissor", usuario.getLogin() + "-" + usuario.getNomeUsuario());
				}

				relatorio.addParametro("enderecoFormatado", enderecoFormatado);
				relatorio.addParametro("matriculaImovel", imovelCertidao.getId());
				relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			}
		}

		if(tipoRelatorio == null){

			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}
}
