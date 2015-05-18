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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.cadastro.cliente;

import gcom.agenciavirtual.cadastro.cliente.ClienteJSONHelper;
import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.arrecadacao.banco.FiltroBanco;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocal;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocalHome;
import gcom.cadastro.*;
import gcom.cadastro.cliente.bean.ClienteEmitirBoletimCadastroHelper;
import gcom.cadastro.cliente.bean.ClienteImovelRelacaoHelper;
import gcom.cadastro.endereco.*;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.Imovel;
import gcom.gui.ActionServletException;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.*;
import gcom.util.email.ServicosEmail;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.brazilutils.br.cpfcnpj.CpfCnpj;

/**
 * Definição da lógica de negócio do Session Bean de ControladorCliente
 * 
 * @author Sávio Luiz
 * @created 25 de Abril de 2005
 */
public class ControladorClienteSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioCliente repositorioCliente = null;

	private IRepositorioClienteImovel repositorioClienteImovel = null;

	private IRepositorioClienteEndereco repositorioClienteEndereco = null;

	private IRepositorioCadastro repositorioCadastro = null;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioCliente = RepositorioClienteHBM.getInstancia();
		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();
		repositorioClienteEndereco = RepositorioClienteEnderecoHBM.getInstancia();
		repositorioCadastro = RepositorioCadastroHBM.getInstancia();

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorEndereco
	 * 
	 * @return O valor de controladorEndereco
	 */
	private ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorCadastro
	 * 
	 * @return O valor de controladorCadastro
	 */
	private ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	private ControladorRegistroAtendimentoLocal getControladorRegistroAtendimento(){

		ControladorRegistroAtendimentoLocalHome localHome = null;
		ControladorRegistroAtendimentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorRegistroAtendimentoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Insere um cliente no sistema
	 * 
	 * @author Saulo Lima
	 * @date 17/03/2009
	 *       Permitir que seja informado um CNPJ já cadastrado desde que seja igual ao do cliente
	 *       responsável superior
	 * @param cliente
	 *            Cliente a ser inserido
	 * @param telefones
	 *            Telefones do cliente
	 * @param enderecos
	 *            Endereços do cliente
	 * @param usuario
	 * @param responsavelCliente
	 * @param indDadosAdicionais
	 * @return Id do cliente inserido
	 * @throws ControladorException
	 */
	public Integer inserirCliente(Cliente cliente, Collection<ClienteFone> telefones, Collection<ClienteEndereco> enderecos,
					Usuario usuario, ClienteResponsavel responsavelCliente, String indDadosAdicionais) throws ControladorException{

		FiltroCliente filtroCliente = new FiltroCliente();

		// Validar CPF de cliente
		if(cliente.getCpf() != null && !cliente.getCpf().equals("")){

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CPF, cliente.getCpf()));
			Collection<Cliente> clienteComCpfExistente = getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());

			if(!clienteComCpfExistente.isEmpty()){
				Cliente clienteComCpf = (Cliente) clienteComCpfExistente.iterator().next();
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.cpf.cliente.ja_cadastrado", null, "" + clienteComCpf.getId());
			}
		}

		// Validar CNPJ de cliente
		if(cliente.getCnpj() != null && !cliente.getCnpj().equals("")){

			boolean permiteDuplicidade = false;
			boolean mensagemOutraEsfera = false;
			filtroCliente.limparListaParametros();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CNPJ, cliente.getCnpj()));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
			Collection<Cliente> clienteComCnpjExistente = getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());
			String idClientes = "";

			if(!clienteComCnpjExistente.isEmpty()){

				if(cliente.getCliente() != null){
					Cliente clienteResponsavelPesquisado = this.pesquisarCliente(cliente.getCliente().getId());
					Cliente clienteComCnpj = clienteComCnpjExistente.iterator().next();
					if(!clienteComCnpj.getCnpj().equals(clienteResponsavelPesquisado.getCnpj())){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.cnpj.cliente.ja_cadastrado", null, "" + clienteComCnpj.getId());
					}
				}else{

					try{
						if(ParametroCadastro.P_PERMITE_DUPLICIDADE_CNPJ.executar().equals(ConstantesSistema.SIM.toString())
										&& !EsferaPoder.PARTICULAR.toString().equals(
														cliente.getClienteTipo().getEsferaPoder().getId().toString())){

							Iterator clienteComCnpjExistenteIterator = clienteComCnpjExistente.iterator();

							int i = 0;

							while(clienteComCnpjExistenteIterator.hasNext()){
								Cliente clienteExistente = (Cliente) clienteComCnpjExistenteIterator.next();

								if(cliente.getClienteTipo().getEsferaPoder().getId()
												.equals(clienteExistente.getClienteTipo().getEsferaPoder().getId())){

									permiteDuplicidade = true;

								}else{
									mensagemOutraEsfera = true;
									idClientes = idClientes + clienteExistente.getId().toString() + ", ";
									i++;
								}
							}
						}

					}catch(ControladorException e){

						throw new ActionServletException(e.getMessage(), e);
					}

					if(!permiteDuplicidade && mensagemOutraEsfera){

						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.cnpj.cliente.ja_cadastrado_outra_esfera", null, idClientes);

					}

					if(!permiteDuplicidade){

						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.cnpj.cliente.ja_cadastrado", null, ""
										+ clienteComCnpjExistente.iterator().next().getId());

					}
				}
			}
		}

		// Validar Cliente com Número do Benefício Já Existente
		if(!Util.isVazioOuBranco(cliente.getNumeroBeneficio())){

			filtroCliente.limparListaParametros();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.NUMERO_BENEFICIO, cliente.getNumeroBeneficio()));

			Collection<Cliente> colecaoClienteBeneficioExistente = getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());

			if(!Util.isVazioOrNulo(colecaoClienteBeneficioExistente)){

				throw new ControladorException("atencao.numero_beneficio_cliente_ja_cadastrado", null, colecaoClienteBeneficioExistente
								.iterator()
								.next().getId().toString(), cliente.getNumeroBeneficio());
			}
		}

		cliente.setIndicadorGeraArquivoTexto(new Short("2"));

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_INSERIR, new UsuarioAcaoUsuarioHelper(
						usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CLIENTE_INSERIR);



		// Fim - Registrando as transações
		Integer chaveClienteGerada = null;
		try{
			chaveClienteGerada = (Integer) getControladorUtil().inserir(cliente);
			cliente.setId(chaveClienteGerada);
		}catch(Exception e){
			e.printStackTrace();
		}

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setArgumentoValor(cliente.getId());
		cliente.setOperacaoEfetuada(operacaoEfetuada);
		cliente.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(cliente);


		// Gerar Registro de Atendimento de Conta Braille
		if(cliente.getIndicadorContaBraille().equals(ConstantesSistema.SIM)){
			String idSolicitacaoTipoEspecificacaoContaBraille = ParametroCadastro.P_TIPO_SOLICITACAO_ESPECIFICACAO_CONTA_BRAILLE.executar();
			if(!idSolicitacaoTipoEspecificacaoContaBraille.equals(ConstantesSistema.VALOR_NAO_INFORMADO)){
				this.getControladorRegistroAtendimento().inserirRegistroAtendimentoContaBraille(cliente,
								Integer.parseInt(idSolicitacaoTipoEspecificacaoContaBraille), usuario, telefones);
			}
		}

		if(telefones != null){

			// Inserir os fones do cliente
			Iterator<ClienteFone> iteratorTelefones = telefones.iterator();

			while(iteratorTelefones.hasNext()){
				ClienteFone clienteFone = iteratorTelefones.next();
				clienteFone.setCliente(cliente);
				clienteFone.setUltimaAlteracao(new Date());

				// ------------ REGISTRAR TRANSAÇÃO----------------------------
				RegistradorOperacao registradorOperacaoInserirClienteFone = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_FONE_INSERIR,
								clienteFone.getCliente().getId(), clienteFone.getId(), new UsuarioAcaoUsuarioHelper(usuario,
												UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacaoInserirClienteFone = new Operacao();
				operacaoInserirClienteFone.setId(Operacao.OPERACAO_CLIENTE_FONE_INSERIR);

				OperacaoEfetuada operacaoEfetuadaInserirClienteFone = new OperacaoEfetuada();
				operacaoEfetuadaInserirClienteFone.setOperacao(operacaoInserirClienteFone);

				clienteFone.setOperacaoEfetuada(operacaoEfetuadaInserirClienteFone);
				clienteFone.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacaoInserirClienteFone.registrarOperacao(clienteFone);
				// ------------ REGISTRAR TRANSAÇÃO----------------------------

				Integer idClienteFone = (Integer) getControladorUtil().inserir(clienteFone);
				clienteFone.setId(idClienteFone);
			}
		}

		// Inserir os endereços do cliente
		Iterator<ClienteEndereco> iteratorEnderecos = enderecos.iterator();

		while(iteratorEnderecos.hasNext()){
			ClienteEndereco clienteEndereco = iteratorEnderecos.next();
			clienteEndereco.setCliente(cliente);
			clienteEndereco.setUltimaAlteracao(new Date());

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacaoInserirClienteEndereco = new RegistradorOperacao(
							Operacao.OPERACAO_CLIENTE_ENDERECO_INSERIR, clienteEndereco.getCliente().getId(), clienteEndereco.getId(),
							new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacaoInserirClienteEndereco = new Operacao();
			operacaoInserirClienteEndereco.setId(Operacao.OPERACAO_CLIENTE_ENDERECO_INSERIR);

			OperacaoEfetuada operacaoEfetuadaInserirClienteEndereco = new OperacaoEfetuada();
			operacaoEfetuadaInserirClienteEndereco.setOperacao(operacaoInserirClienteEndereco);
			operacaoEfetuadaInserirClienteEndereco.setArgumentoValor(cliente.getId());
			clienteEndereco.setOperacaoEfetuada(operacaoEfetuadaInserirClienteEndereco);
			clienteEndereco.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacaoInserirClienteEndereco.registrarOperacao(clienteEndereco);

			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			Integer idClienteEndereco = (Integer) getControladorUtil().inserir(clienteEndereco);
			clienteEndereco.setId(idClienteEndereco);
		}



		// ********************* Inserir Dados do Cliente Responsavel ******************************
		if(!Util.isVazioOuBranco(indDadosAdicionais) && new Boolean(indDadosAdicionais).booleanValue()){
			getControladorCadastro().inserirClienteResponsavel(responsavelCliente, usuario);
		}
		// *************************************************************************



		return chaveClienteGerada;
	}

	/**
	 * @param clienteJSONHelper
	 * @throws ControladorException
	 */
	public void atualizarCliente(ClienteJSONHelper clienteJSONHelper) throws ControladorException{

		Cliente cliente = this.obterCliente(clienteJSONHelper.getId());

		if(cliente == null){
			throw new ControladorException("atencao.cliente.inexistente");
		}

		this.popularClienteComNovasInformacoes(cliente, clienteJSONHelper, ConstantesSistema.ACAO_ATUALIZAR);

	}

	private void popularClienteComNovasInformacoes(Cliente cliente, ClienteJSONHelper clienteJSONHelper, String acao)
					throws ControladorException{

		cliente.setNome(clienteJSONHelper.getNome());
		cliente.setClienteTipo(this.obterClienteTipo(clienteJSONHelper.getIdTipoCliente()));

		CpfCnpj cpfCnpj = this.obterCpfCnpj(clienteJSONHelper.getCpfCnpj());

		if(cpfCnpj.isCpf()){

			cliente.setCpf(cpfCnpj.getNumber());
			cliente.setCnpj(null);

		}else if(cpfCnpj.isCnpj()){

			cliente.setCnpj(cpfCnpj.getNumber());
			cliente.setCpf(null);

		}

		if(clienteJSONHelper.getRg() != null){

			cliente.setRg(clienteJSONHelper.getRg());

		}else{

			throw new ControladorException("atencao.campo.informado", null, "RG");

		}

		cliente.setOrgaoExpedidorRg(this.obterOrgaoExpedidorRg(clienteJSONHelper.getIdOrgaoExpdidorRg()));

		cliente.setUnidadeFederacao(this.obterUnidadeFederacao(clienteJSONHelper.getIdUnidadeFederacao()));

		cliente.setPessoaSexo(this.obterPessoaSexo(clienteJSONHelper.getIdPessoaSexo()));

		cliente.setNomeAbreviado(clienteJSONHelper.getNomeAbreviado());

		cliente.setClienteTipoEspecial(this.obterClienteTipoEspecial(clienteJSONHelper.getIdTipoClienteEspecial()));

		cliente.setEmail(clienteJSONHelper.getEmail());

		if(clienteJSONHelper.getDataEmissaoRg() != null){

			cliente.setDataEmissaoRg(Util.converterStringParaData(clienteJSONHelper.getDataEmissaoRg()));

		}

		if(!Util.isVazioOuBranco(clienteJSONHelper.getDataNascimento())){

			cliente.setDataNascimento(Util.converterStringParaData(clienteJSONHelper.getDataNascimento()));

		}

		cliente.setProfissao(this.obterProfissao(clienteJSONHelper.getIdProfissao()));

		cliente.setRaca(this.obterRaca(clienteJSONHelper.getIdRaca()));

		cliente.setNacionalidade(this.obterNacionalidade(clienteJSONHelper.getIdNacionalidade()));

		cliente.setEstadoCivil(this.obterEstadoCivil(clienteJSONHelper.getIdEstadoCivil()));
		
		cliente.setIndicadorCobrancaAcrescimos(ConstantesSistema.NAO);
		
		cliente.setUltimaAlteracao(new Date());

		Collection<ClienteEndereco> collClienteEndereco = this.obterCollClienteEndereco(clienteJSONHelper);

		for(ClienteEndereco clienteEndereco : collClienteEndereco){

			clienteEndereco.setId(null);

		}

		Collection<ClienteFone> collClienteFone = this.obterCollClienteFone(clienteJSONHelper);

		for(ClienteFone clienteFone : collClienteFone){

			clienteFone.setId(null);

		}

		String indDadosAdicionais = null;

		String idRegistroAtualizacao = null;

		ClienteResponsavel clienteResponsavel = this.obterClienteResponsavel(clienteJSONHelper);

		if(clienteResponsavel != null){

			idRegistroAtualizacao = String.valueOf(clienteResponsavel.getId());

		}

		if(clienteJSONHelper.getIcInformarDadosResponsavel() != null){

			if(clienteJSONHelper.getIcInformarDadosResponsavel().intValue() == ConstantesSistema.SIM.intValue()){

				indDadosAdicionais = "true";

			}else{

				indDadosAdicionais = "false";

			}

		}else{

			indDadosAdicionais = "false";

		}

		Usuario usuario = new Usuario();
		usuario.setId(Usuario.getIdUsuarioBatchParametro());

		if(acao.equals(ConstantesSistema.ACAO_ATUALIZAR)){
			this.atualizarCliente(cliente, collClienteFone, collClienteEndereco, clienteResponsavel, usuario, indDadosAdicionais,
							idRegistroAtualizacao);
		}else{
			this.inserirCliente(cliente, collClienteFone, collClienteEndereco, usuario, clienteResponsavel, indDadosAdicionais);

		}

	}

	private ClienteResponsavel obterClienteResponsavel(ClienteJSONHelper clienteJSONHelper) throws ControladorException{

		FiltroClienteResponsavel filtroClienteResponsavel = new FiltroClienteResponsavel();
		filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.CLIENTE_ID, clienteJSONHelper.getId()));
		filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroClienteResponsavel.adicionarCaminhoParaCarregamentoEntidade("agencia.banco");

		ClienteResponsavel clienteResponsavel = (ClienteResponsavel) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(
						filtroClienteResponsavel, ClienteResponsavel.class.getName()));

		if(clienteResponsavel != null){

			clienteResponsavel.setIndMulta(clienteJSONHelper.getIcCobrancaMulta());

			clienteResponsavel.setIndJuros(clienteJSONHelper.getIcJuros());

			clienteResponsavel.setIndCorrecao(clienteJSONHelper.getIcCobrancaCorrecao());

			clienteResponsavel.setIndImpostoFederal(clienteJSONHelper.getIcCobrancaImpostoFederal());

			Banco banco = this.obterBanco(clienteJSONHelper.getIdBanco());

			Agencia agencia = this.obterAgencia(clienteJSONHelper.getIdAgencia());

			if(agencia != null){

				agencia.setBanco(banco);

			}

			clienteResponsavel.setAgencia(agencia);

			clienteResponsavel.setContaBancaria(clienteJSONHelper.getNumeroConta());

		}

		return clienteResponsavel;

	}

	private Collection<ClienteFone> obterCollClienteFone(ClienteJSONHelper clienteJSONHelper) throws ControladorException{

		FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
		filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, clienteJSONHelper.getId()));
		filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteFone.FONE_TIPO);

		Collection<ClienteFone> coll = this.getControladorUtil().pesquisar(filtroClienteFone, ClienteFone.class.getName());

		if(!coll.isEmpty()){

			ClienteFone clienteFone = (ClienteFone) Util.retonarObjetoDeColecao(coll);

			FoneTipo foneTipo = this.obterFoneTipo(clienteJSONHelper.getIdTipoTelefone());

			String ddd = clienteJSONHelper.getDdd();

			String numeroTelefone = clienteJSONHelper.getNumeroTelefone();

			String ramal = clienteJSONHelper.getRamal();

			clienteFone.setFoneTipo(foneTipo);

			clienteFone.setDdd(ddd);

			clienteFone.setTelefone(numeroTelefone);

			clienteFone.setRamal(ramal);

		}else{

			coll = new ArrayList();

			ClienteFone clienteFone = new ClienteFone();

			FoneTipo foneTipo = this.obterFoneTipo(clienteJSONHelper.getIdTipoTelefone());

			String ddd = clienteJSONHelper.getDdd();

			String numeroTelefone = clienteJSONHelper.getNumeroTelefone();

			String ramal = clienteJSONHelper.getRamal();

			clienteFone.setFoneTipo(foneTipo);

			clienteFone.setDdd(ddd);

			clienteFone.setTelefone(numeroTelefone);

			clienteFone.setRamal(ramal);

			coll.add(clienteFone);

		}

		return coll;

	}

	private Collection<ClienteEndereco> obterCollClienteEndereco(ClienteJSONHelper clienteJSONHelper) throws ControladorException{

		FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
		filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, clienteJSONHelper.getId()));
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.ENDERECO_TIPO);
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.ENDERECO_REFERENCIA);
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro");
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro");
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio");

		Collection<ClienteEndereco> coll = this.getControladorUtil().pesquisar(filtroClienteEndereco, ClienteEndereco.class.getName());

		if(!coll.isEmpty()){

			ClienteEndereco clienteEndereco = (ClienteEndereco) Util.retonarObjetoDeColecao(coll);

			clienteEndereco.setEnderecoTipo(this.obterEnderecoTipo(clienteJSONHelper.getIdEnderecoTipo()));

			clienteEndereco.setEnderecoReferencia(this.obterEnderecoReferencia(clienteJSONHelper.getIdEnderecoReferencia()));

			clienteEndereco.setComplemento(clienteJSONHelper.getComplementoEndereco());

			if(clienteJSONHelper.getNumero() == null){

				throw new ControladorException("atencao.campo.informado", null, "NUMERO");

			}else{

				clienteEndereco.setNumero(clienteJSONHelper.getNumero());

			}

			Logradouro logradouro = this.obterLogradouro(clienteJSONHelper.getIdLogradouro());

			Bairro bairro = this.obterBairro(clienteJSONHelper.getIdBairro());

			Municipio municipio = this.obterMunicipio(clienteJSONHelper.getIdMunicipio());

			FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();
			filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_LOGRADOURO, logradouro.getId()));
			filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_BAIRRO, bairro.getId()));
			filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouroBairro.BAIRRO);
			
			LogradouroBairro logradouroBairro = (LogradouroBairro) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(
							filtroLogradouroBairro, LogradouroBairro.class.getName()));

			if(logradouroBairro != null){
				
				logradouroBairro.getBairro().setMunicipio(municipio);
				clienteEndereco.setLogradouroBairro(logradouroBairro);
				
			}

			clienteEndereco.setEnderecoTipo(obterEnderecoTipo(clienteJSONHelper.getIdEnderecoTipo()));

		}else{

			coll = new ArrayList();

			ClienteEndereco clienteEndereco = new ClienteEndereco();
			clienteEndereco.setNumero(clienteJSONHelper.getNumero());

			Logradouro logradouro = this.obterLogradouro(clienteJSONHelper.getIdLogradouro());

			Bairro bairro = this.obterBairro(clienteJSONHelper.getIdBairro());

			Municipio municipio = this.obterMunicipio(clienteJSONHelper.getIdMunicipio());

			FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();
			filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_LOGRADOURO, logradouro.getId()));
			filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_BAIRRO, bairro.getId()));
			filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouroBairro.BAIRRO);

			LogradouroBairro logradouroBairro = (LogradouroBairro) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(
							filtroLogradouroBairro, LogradouroBairro.class.getName()));

			if(logradouroBairro != null){

				logradouroBairro.getBairro().setMunicipio(municipio);
				clienteEndereco.setLogradouroBairro(logradouroBairro);

			}

			clienteEndereco.setEnderecoTipo(obterEnderecoTipo(clienteJSONHelper.getIdEnderecoTipo()));

			coll.add(clienteEndereco);


		}

		return coll;
	}

	private Agencia obterAgencia(Integer idAgencia) throws ControladorException{

		Agencia agencia = null;

		if(idAgencia != null){

			FiltroAgencia filtroAgencia = new FiltroAgencia();
			filtroAgencia.adicionarParametro(new ParametroSimples(FiltroAgencia.ID, idAgencia));

			agencia = (Agencia) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroAgencia,
 Agencia.class.getName()));

			if(agencia == null){

				throw new ControladorException("atencao.campo.invalido", null, "AGENCIA");

			}

		}

		return agencia;

	}

	private Banco obterBanco(Integer idBanco) throws ControladorException{

		Banco banco = null;

		if(idBanco != null){

			FiltroBanco filtroBanco = new FiltroBanco();
			filtroBanco.adicionarParametro(new ParametroSimples(FiltroBanco.ID, idBanco));

			banco = (Banco) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroBanco, Banco.class.getName()));

			if(banco == null){

				throw new ControladorException("atencao.campo.invalido", null, "BANCO");

			}

		}

		return banco;

	}

	private Municipio obterMunicipio(Integer idMunicipio) throws ControladorException{

		Municipio municipio = null;

		if(idMunicipio != null){

			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));

			municipio = (Municipio) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroMunicipio,
							Municipio.class.getName()));

			if(municipio == null){

				throw new ControladorException("atencao.campo.invalido", null, "MUNICIPIO");

			}

		}

		return municipio;

	}

	private FoneTipo obterFoneTipo(Integer idFoneTipo) throws ControladorException{

		FoneTipo foneTipo = null;

		if(idFoneTipo != null){

			FiltroFoneTipo filtroFoneTipo = new FiltroFoneTipo();
			filtroFoneTipo.adicionarParametro(new ParametroSimples(FiltroFoneTipo.ID, idFoneTipo));

			foneTipo = (FoneTipo) Util
							.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroFoneTipo, FoneTipo.class.getName()));

			if(foneTipo == null){

				throw new ControladorException("atencao.campo.invalido", null, "TIPO TELEFONE");

			}

		}

		return foneTipo;

	}

	private EnderecoReferencia obterEnderecoReferencia(Integer idEnderecoReferencia) throws ControladorException{

		EnderecoReferencia enderecoReferencia = null;

		if(idEnderecoReferencia != null){

			FiltroEnderecoReferencia filtroEnderecoReferencia = new FiltroEnderecoReferencia();
			filtroEnderecoReferencia.adicionarParametro(new ParametroSimples(FiltroEnderecoReferencia.ID, idEnderecoReferencia));

			enderecoReferencia = (EnderecoReferencia) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(
							filtroEnderecoReferencia, EnderecoReferencia.class.getName()));

			if(enderecoReferencia == null){

				throw new ControladorException("atencao.campo.invalido", null, "ENDEREÇO REFERENCIA");

			}

		}

		return enderecoReferencia;

	}

	private EstadoCivil obterEstadoCivil(Integer idEstadoCivil) throws ControladorException{

		EstadoCivil estadoCivil = null;

		if(idEstadoCivil != null){

			FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
			filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.ID, idEstadoCivil));

			estadoCivil = (EstadoCivil) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroEstadoCivil,
							EstadoCivil.class.getName()));

			if(estadoCivil == null){

				throw new ControladorException("atencao.campo.invalido", null, "ESTADO CIVIL");

			}

		}

		return estadoCivil;

	}

	private Nacionalidade obterNacionalidade(Integer idNacionalidade) throws ControladorException{

		Nacionalidade nacionalidade = null;

		if(idNacionalidade != null){

			FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
			filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.ID, idNacionalidade));

			nacionalidade = (Nacionalidade) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroNacionalidade,
							Nacionalidade.class.getName()));

			if(nacionalidade == null){

				throw new ControladorException("atencao.campo.invalido", null, "NACIONALIDADE");

			}

		}

		return nacionalidade;

	}

	private Raca obterRaca(Integer idRaca) throws ControladorException{

		Raca raca = null;

		if(idRaca != null){

			FiltroRaca filtroRaca = new FiltroRaca();
			filtroRaca.adicionarParametro(new ParametroSimples(FiltroRaca.ID, idRaca));

			raca = (Raca) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroRaca, Raca.class.getName()));

			if(raca == null){

				throw new ControladorException("atencao.campo.invalido", null, "RAÇA");

			}

		}

		return raca;

	}

	private Profissao obterProfissao(Integer idProfissao) throws ControladorException{

		Profissao profissao = null;

		if(idProfissao != null){

			FiltroProfissao filtroProfissao = new FiltroProfissao();
			filtroProfissao.adicionarParametro(new ParametroSimples(FiltroProfissao.ID, idProfissao));

			profissao = (Profissao) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroProfissao,
							Profissao.class.getName()));

			if(profissao == null){

				throw new ControladorException("atencao.campo.invalido", null, "PROFISSÃO");

			}

		}

		return profissao;

	}

	private ClienteTipoEspecial obterClienteTipoEspecial(Integer idClienteTipoEspecial) throws ControladorException{

		ClienteTipoEspecial clienteTipoEspecial = null;

		if(idClienteTipoEspecial != null){

			FiltroClienteTipoEspecial filtroClienteTipoEspecial = new FiltroClienteTipoEspecial();
			filtroClienteTipoEspecial.adicionarParametro(new ParametroSimples(FiltroClienteTipoEspecial.ID, idClienteTipoEspecial));

			clienteTipoEspecial = (ClienteTipoEspecial) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(
							filtroClienteTipoEspecial, ClienteTipoEspecial.class.getName()));

			if(clienteTipoEspecial == null){

				throw new ControladorException("atencao.campo.invalido", null, "TIPO CLIENTE ESPECIAL");

			}

		}

		return clienteTipoEspecial;

	}

	private Bairro obterBairro(Integer idBairro) throws ControladorException{

		if(idBairro != null){

			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, idBairro));

			Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroBairro, Bairro.class.getName()));

			if(bairro != null){

				return bairro;

			}else{

				throw new ControladorException("atencao.campo.invalido", null, "BAIRRO");

			}

		}else{

			throw new ControladorException("atencao.campo.informado", null, "BAIRRO");

		}

	}

	private Logradouro obterLogradouro(Integer idLogradouro) throws ControladorException{

		if(idLogradouro != null){

			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, idLogradouro));

			Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroLogradouro,
							Logradouro.class.getName()));

			if(logradouro != null){

				return logradouro;

			}else{

				throw new ControladorException("atencao.campo.invalido", null, "LOGRADOURO");

			}

		}else{

			throw new ControladorException("atencao.campo.informado", null, "LOGRADOURO");

		}

	}

	private EnderecoTipo obterEnderecoTipo(Integer idEnderecoTipo) throws ControladorException{

		if(idEnderecoTipo != null){

			FiltroEnderecoTipo filtroEnderecoTipo = new FiltroEnderecoTipo();
			filtroEnderecoTipo.adicionarParametro(new ParametroSimples(FiltroEnderecoTipo.ID, idEnderecoTipo));

			EnderecoTipo enderecoTipo = (EnderecoTipo) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroEnderecoTipo,
							EnderecoTipo.class.getName()));

			if(enderecoTipo != null){

				return enderecoTipo;

			}else{

				throw new ControladorException("atencao.campo.invalido", null, "ENDEREÇO TIPO");

			}

		}else{

			throw new ControladorException("atencao.campo.informado", null, "ENDEREÇO TIPO");

		}

	}

	private PessoaSexo obterPessoaSexo(Integer idPessoaSexo) throws ControladorException{

		if(idPessoaSexo != null){

			FiltroPessoaSexo filtroPessoaSexo = new FiltroPessoaSexo();
			filtroPessoaSexo.adicionarParametro(new ParametroSimples(FiltroPessoaSexo.ID, idPessoaSexo));

			PessoaSexo pessoaSexo = (PessoaSexo) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroPessoaSexo,
							PessoaSexo.class.getName()));

			if(pessoaSexo != null){

				return pessoaSexo;

			}else{

				throw new ControladorException("atencao.campo.invalido", null, "PESSOA SEXO");

			}

		}else{

			throw new ControladorException("atencao.campo.informado", null, "PESSOA SEXO");

		}

	}

	private UnidadeFederacao obterUnidadeFederacao(Integer idUniadeFederacao) throws ControladorException{

		if(idUniadeFederacao != null){

			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
			filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, idUniadeFederacao));

			UnidadeFederacao unidadeFederacao = (UnidadeFederacao) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(
							filtroUnidadeFederacao, UnidadeFederacao.class.getName()));

			if(unidadeFederacao != null){

				return unidadeFederacao;

			}else{

				throw new ControladorException("atencao.campo.invalido", null, "UNIDADE FEDERAÇÃO");

			}

		}else{

			throw new ControladorException("atencao.campo.informado", null, "UNIDADE FEDERAÇÃO");

		}

	}

	private OrgaoExpedidorRg obterOrgaoExpedidorRg(Integer idOrgaoExpedidor) throws ControladorException{

		if(idOrgaoExpedidor != null){

			FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
			filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.ID, idOrgaoExpedidor));

			OrgaoExpedidorRg orgaoExpedidorRg = (OrgaoExpedidorRg) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(
							filtroOrgaoExpedidorRg, OrgaoExpedidorRg.class.getName()));

			if(orgaoExpedidorRg != null){

				return orgaoExpedidorRg;

			}else{

				throw new ControladorException("atencao.campo.invalido", null, "ORGÃO EXPEDIDOR RG");

			}

		}else{

			throw new ControladorException("atencao.campo.informado", null, "ORGÃO EXPEDIDOR RG");

		}

	}

	private CpfCnpj obterCpfCnpj(String cpfCnpj) throws ControladorException{

		if(cpfCnpj != null){

			CpfCnpj cc = new CpfCnpj(cpfCnpj);

			if(cc.isCpf()){

				if(cc.isValid()){

					return cc;

				}else{

					throw new ControladorException("atencao.campo.invalido", null, "CPF");

				}

			}else if(cc.isCnpj()){

				if(cc.isValid()){

					return cc;

				}else{

					throw new ControladorException("atencao.campo.invalido", null, "CNPJ");

				}

			}else{

				throw new ControladorException("atencao.campo.invalido", null, "CPF/CNPJ");

			}

		}else{

			throw new ControladorException("atencao.campo.informado", null, "CPF/CNPJ");

		}

	}

	private ClienteTipo obterClienteTipo(Integer idTipoCliente) throws ControladorException{

		if(idTipoCliente != null){

			FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
			filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, idTipoCliente));

			ClienteTipo clienteTipo = (ClienteTipo) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroClienteTipo,
							ClienteTipo.class.getName()));

			if(clienteTipo == null){

				throw new ControladorException("atencao.campo.invalido", null, "TIPO CLIENTE");

			}else{

				return clienteTipo;

			}

		}else{

			throw new ControladorException("atencao.campo.informado", null, "TIPO CLIENTE");

		}

	}

	private Cliente obterCliente(Integer idCliente) throws ControladorException{

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));

		Cliente cliente = (Cliente) Util
						.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName()));

		return cliente;

	}

	/**
	 * Atualiza um cliente no sistema
	 * 
	 * @author eduardo henrique
	 * @date 27/06/2008
	 *       Adição de Validação do CPF e RG de CLiente, caso exista
	 * @author Saulo Lima
	 * @date 17/03/2009
	 *       Permitir que seja informado um CNPJ já cadastrado desde que seja igual ao do cliente
	 *       responsável superior
	 * @param cliente
	 *            Cliente a ser atualizado
	 * @param telefones
	 *            Telefones do cliente
	 * @param enderecos
	 * @param responsavelCliente
	 * @param usuario
	 * @param indDadosAdicionais
	 * @param idRegistroAtualizacao
	 *            Endereços do cliente
	 * @throws ControladorException
	 */
	public void atualizarCliente(Cliente cliente, Collection<ClienteFone> telefones, Collection<ClienteEndereco> enderecos,
					ClienteResponsavel responsavelCliente, Usuario usuario, String indDadosAdicionais, String idRegistroAtualizacao)
					throws ControladorException{

		FiltroCliente filtroCliente = new FiltroCliente();

		try{
			// Verifica se já existe o cnpj cadastrado na base
			if(cliente.getCnpj() != null && !cliente.getCnpj().equals("")){

				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CNPJ, cliente.getCnpj()));
				filtroCliente.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroCliente.ID, cliente.getId()));
				filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);

				Collection<Cliente> colecaoClientes = getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());

				if(colecaoClientes != null && !colecaoClientes.isEmpty()){

					if(cliente.getCliente() != null){
						Cliente clienteResponsavelPesquisado = this.pesquisarCliente(cliente.getCliente().getId());
						Cliente clienteComCnpj = colecaoClientes.iterator().next();
						if(!clienteComCnpj.getCnpj().equals(clienteResponsavelPesquisado.getCnpj())){
							sessionContext.setRollbackOnly();
							throw new ControladorException("atencao.cnpj.cliente.ja_cadastrado", null, "" + clienteComCnpj.getId());
						}
					}else{

						boolean permiteDuplicidade = false;
						boolean mensagemOutraEsfera = false;
						String idClientes = "";

						try{
							if(ParametroCadastro.P_PERMITE_DUPLICIDADE_CNPJ.executar().equals(ConstantesSistema.SIM.toString())
											&& !EsferaPoder.PARTICULAR.toString().equals(
															cliente.getClienteTipo().getEsferaPoder().getId().toString())){

								Iterator colecaoClientesIterator = colecaoClientes.iterator();

								int i = 0;

								while(colecaoClientesIterator.hasNext()){
									Cliente clienteExistente = (Cliente) colecaoClientesIterator.next();

									if(cliente.getClienteTipo().getEsferaPoder().getId()
													.equals(clienteExistente.getClienteTipo().getEsferaPoder().getId())){

										permiteDuplicidade = true;

									}else{
										mensagemOutraEsfera = true;
										idClientes = idClientes + clienteExistente.getId().toString() + ", ";
										i++;
									}
								}
							}

						}catch(ControladorException e){

							throw new ActionServletException(e.getMessage(), e);
						}

						if(!permiteDuplicidade && mensagemOutraEsfera){

							sessionContext.setRollbackOnly();
							throw new ControladorException("atencao.cnpj.cliente.ja_cadastrado_outra_esfera", null, idClientes);

						}

						if(!permiteDuplicidade){

							sessionContext.setRollbackOnly();
							throw new ControladorException("atencao.cnpj.cliente.ja_cadastrado", null, ""
											+ colecaoClientes.iterator().next().getId());

						}
					}
				}
			}

			// Validar CPF de cliente
			if(cliente.getCpf() != null && !cliente.getCpf().equals("")){

				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CPF, cliente.getCpf()));
				filtroCliente.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroCliente.ID, cliente.getId()));

				Collection<Cliente> clienteComCpfExistente = getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());

				if(!clienteComCpfExistente.isEmpty()){
					Cliente clienteComCpf = clienteComCpfExistente.iterator().next();
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.cpf.cliente.ja_cadastrado", null, "" + clienteComCpf.getId());
				}
			}

			// Validar RG de cliente
			// if(cliente.getRg() != null && !cliente.getRg().equals("")){
			//
			// filtroCliente.limparListaParametros();
			// filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.RG,
			// cliente.getRg()));
			// filtroCliente.adicionarParametro(new
			// ParametroSimples(FiltroCliente.ORGAO_EXPEDIDOR_RG, cliente.getOrgaoExpedidorRg()));
			// filtroCliente.adicionarParametro(new
			// ParametroSimples(FiltroCliente.UNIDADE_FEDERACAO, cliente.getUnidadeFederacao()));
			// filtroCliente.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroCliente.ID,
			// cliente.getId()));
			//
			// Collection<Cliente> clienteComRgExistente =
			// getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());
			//
			// if(!clienteComRgExistente.isEmpty()){
			// Cliente clienteComRg = clienteComRgExistente.iterator().next();
			// sessionContext.setRollbackOnly();
			// throw new ControladorException("atencao.rg.cliente.ja_cadastrado", null, "" +
			// clienteComRg.getId());
			// }
			// }

			// Parte de Validacao com Timestamp
			filtroCliente.limparListaParametros();

			// Seta o filtro para buscar o cliente na base
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId()));

			Collection<Cliente> colecaoCliente = getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());

			// verifica se o cliente ainda existe na base, porque ele pode ter sido excluido com
			// isso
			// não é possível analizar a data de ultima alteração
			if(colecaoCliente == null || colecaoCliente.isEmpty()){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// Procura o cliente na base
			Cliente clienteNaBase = colecaoCliente.iterator().next();

			// Verificar se o cliente já foi atualizado por outro usuário durante esta atualização
			if(clienteNaBase.getUltimaAlteracao().after(cliente.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// Altualiza o indicadorGeraArquivotexto
			cliente.setIndicadorGeraArquivoTexto(clienteNaBase.getIndicadorGeraArquivoTexto());

			// Atualiza a data de última alteração
			cliente.setUltimaAlteracao(new Date());

			// Validar Cliente com Número do Benefício Já Existente
			if(!Util.isVazioOuBranco(cliente.getNumeroBeneficio())){

				filtroCliente.limparListaParametros();
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.NUMERO_BENEFICIO, cliente.getNumeroBeneficio()));

				Collection<Cliente> colecaoClienteBeneficioExistente = getControladorUtil().pesquisar(filtroCliente,
								Cliente.class.getName());

				if(!Util.isVazioOrNulo(colecaoClienteBeneficioExistente)){

					Cliente clienteNumeroBeneficioExistente = colecaoClienteBeneficioExistente.iterator().next();

					if(!clienteNumeroBeneficioExistente.getId().equals(cliente.getId())){

						throw new ControladorException("atencao.numero_beneficio_cliente_ja_cadastrado", null,
										clienteNumeroBeneficioExistente.getId().toString(), cliente.getNumeroBeneficio());
					}
				}
			}

			// [UC] - Registrar Transação
			// Início - Registrando as transações
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_ATUALIZAR, cliente.getId(), cliente
							.getId(), new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_CLIENTE_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);
			operacaoEfetuada.setArgumentoValor(cliente.getId());

			cliente.setOperacaoEfetuada(operacaoEfetuada);
			cliente.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(cliente);
			// Fim - Registrando as transações
			
			// Se a opção "Conta Impressa em Braille?" for alterada de NÂO para SIM, deve ser gerada uma RA
			if(clienteNaBase.getIndicadorContaBraille().equals(ConstantesSistema.NAO)
							&& cliente.getIndicadorContaBraille().equals(ConstantesSistema.SIM)){

				String idSolicitacaoTipoEspecificacaoContaBraille = ParametroCadastro.P_TIPO_SOLICITACAO_ESPECIFICACAO_CONTA_BRAILLE
								.executar();
				if(!idSolicitacaoTipoEspecificacaoContaBraille.equals(ConstantesSistema.VALOR_NAO_INFORMADO)){
					this.getControladorRegistroAtendimento().inserirRegistroAtendimentoContaBraille(cliente,
									Integer.parseInt(idSolicitacaoTipoEspecificacaoContaBraille), usuario, telefones);
				}
			}

			// Atualiza o cliente
			getControladorUtil().atualizar(cliente);			

			// ********************** Inicio de Inserir/Manter os fone do cliente
			// *********************
			Iterator<ClienteFone> iteratorTelefones = telefones.iterator();

			FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
			filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, cliente.getId()));
			filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteFone.CLIENTE);
			filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteFone.FONE_TIPO);

			Collection<ClienteFone> clientesFones = getControladorUtil().pesquisar(filtroClienteFone, ClienteFone.class.getName());

			if(clientesFones != null || !clientesFones.isEmpty()){
				for(ClienteFone clienteFone : clientesFones){

					// ------------ REGISTRAR TRANSAÇÃO ----------------
					if(clienteFone == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizacao.timestamp");
					}

					RegistradorOperacao registradorClienteFone = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_FONE_REMOVER,
									clienteFone.getId(), clienteFone.getId(), new UsuarioAcaoUsuarioHelper(usuario,
													UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					Operacao operacaoRemoverClienteFone = new Operacao();
					operacaoRemoverClienteFone.setId(Operacao.OPERACAO_CLIENTE_FONE_REMOVER);

					OperacaoEfetuada operacaoEfetuadaRemoverClienteFone = new OperacaoEfetuada();
					operacaoEfetuadaRemoverClienteFone.setOperacao(operacaoRemoverClienteFone);
					operacaoEfetuadaRemoverClienteFone.setArgumentoValor(cliente.getId());

					clienteFone.setOperacaoEfetuada(operacaoEfetuadaRemoverClienteFone);

					registradorClienteFone.registrarOperacao(clienteFone);
					// ------------ REGISTRAR TRANSAÇÃO ----------------

					getControladorUtil().remover(clienteFone);
				}
			}

			// Adicionar os telefones novos informados para o cliente
			while(iteratorTelefones.hasNext()){
				ClienteFone clienteFone = iteratorTelefones.next();
				clienteFone.setUltimaAlteracao(new Date());
				clienteFone.setCliente(cliente);

				// ------------ REGISTRAR TRANSAÇÃO----------------------------
				RegistradorOperacao registradorOperacaoInserirClienteFone = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_FONE_INSERIR,
								clienteFone.getCliente().getId(), clienteFone.getId(), new UsuarioAcaoUsuarioHelper(usuario,
												UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacaoInserirClienteFone = new Operacao();
				operacaoInserirClienteFone.setId(Operacao.OPERACAO_CLIENTE_FONE_INSERIR);

				OperacaoEfetuada operacaoEfetuadaInserirClienteFone = new OperacaoEfetuada();
				operacaoEfetuadaInserirClienteFone.setOperacao(operacaoInserirClienteFone);
				operacaoEfetuadaInserirClienteFone.setArgumentoValor(cliente.getId());

				clienteFone.setOperacaoEfetuada(operacaoEfetuadaInserirClienteFone);
				clienteFone.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacaoInserirClienteFone.registrarOperacao(clienteFone);
				// ------------ REGISTRAR TRANSAÇÃO----------------------------

				Integer idClienteFone = (Integer) getControladorUtil().inserir(clienteFone);
				clienteFone.setId(idClienteFone);
			}
			// ********************** Fim de Inserir/Manter os fone do cliente *********************

			// ********************** Inicio de Inserir/Manter os endereços do cliente
			// *********************
			Iterator<ClienteEndereco> iteratorEnderecos = enderecos.iterator();

			// Remover a lista dos endereços do cliente para adicionar a nova lista depois
			// repositorioCliente.removerTodosEnderecosPorCliente(cliente.getId());

			FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
			filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, cliente.getId()));
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.CLIENTE);
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.ENDERECO_TIPO);
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.ENDERECO_REFERENCIA);
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.LOGRADOURO_CEP);
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.LOGRADOURO_BAIRRO);

			Collection<ClienteEndereco> clientesEnderecos = getControladorUtil().pesquisar(filtroClienteEndereco,
							ClienteEndereco.class.getName());

			if(clientesEnderecos != null || !clientesEnderecos.isEmpty()){
				for(ClienteEndereco clienteEndereco : clientesEnderecos){
					// ------------ REGISTRAR TRANSAÇÃO ----------------
					if(clienteEndereco == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizacao.timestamp");
					}

					RegistradorOperacao registradorClienteEndereco = new RegistradorOperacao(
									Operacao.OPERACAO_ATUALIZAR_CLIENTE_ENDERECO_REMOVER, clienteEndereco.getId(), clienteEndereco.getId(),
									new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					Operacao operacaoRemoverClienteEndereco = new Operacao();
					operacao.setId(Operacao.OPERACAO_ATUALIZAR_CLIENTE_ENDERECO_REMOVER);

					OperacaoEfetuada operacaoEfetuadaRemoverClienteEndereco = new OperacaoEfetuada();
					operacaoEfetuadaRemoverClienteEndereco.setOperacao(operacaoRemoverClienteEndereco);
					operacaoEfetuadaRemoverClienteEndereco.setArgumentoValor(cliente.getId());

					clienteEndereco.setOperacaoEfetuada(operacaoEfetuadaRemoverClienteEndereco);

					registradorClienteEndereco.registrarOperacao(clienteEndereco);
					// ------------ REGISTRAR TRANSAÇÃO ----------------

					getControladorUtil().remover(clienteEndereco);
				}
			}

			// Adicionar os endereços novos informados para o cliente
			while(iteratorEnderecos.hasNext()){
				ClienteEndereco clienteEndereco = iteratorEnderecos.next();
				clienteEndereco.setUltimaAlteracao(new Date());
				clienteEndereco.setCliente(cliente);

				// ------------ REGISTRAR TRANSAÇÃO----------------------------
				RegistradorOperacao registradorOperacaoInserirClienteEndereco = new RegistradorOperacao(
								Operacao.OPERACAO_CLIENTE_ENDERECO_INSERIR, clienteEndereco.getCliente().getId(), clienteEndereco.getId(),
								new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacaoInserirClienteEndereco = new Operacao();
				operacaoInserirClienteEndereco.setId(Operacao.OPERACAO_CLIENTE_ENDERECO_INSERIR);

				OperacaoEfetuada operacaoEfetuadaInserirClienteEndereco = new OperacaoEfetuada();
				operacaoEfetuadaInserirClienteEndereco.setOperacao(operacaoInserirClienteEndereco);
				operacaoEfetuadaInserirClienteEndereco.setArgumentoValor(cliente.getId());

				clienteEndereco.setOperacaoEfetuada(operacaoEfetuadaInserirClienteEndereco);
				clienteEndereco.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacaoInserirClienteEndereco.registrarOperacao(clienteEndereco);
				// ------------ REGISTRAR TRANSAÇÃO----------------------------

				getControladorUtil().inserir(clienteEndereco);
			}

			// ********************** Fim de Inserir/Manter os endereços do cliente
			// *********************

			// ****************** Inserir ou Manter o Cliente Responsável ***************
			if(!Util.isVazioOuBranco(indDadosAdicionais) && new Boolean(indDadosAdicionais).booleanValue()){

				if(!Util.isVazioOuBranco(idRegistroAtualizacao)){
					responsavelCliente.setId(Integer.valueOf(idRegistroAtualizacao));
					getControladorCadastro().atualizarClienteResponsavel(responsavelCliente, usuario);
				}else{
					getControladorCadastro().inserirClienteResponsavel(responsavelCliente, usuario);
				}
			}else{
				if(!Util.isVazioOuBranco(idRegistroAtualizacao)){
					responsavelCliente.setId(Integer.valueOf(idRegistroAtualizacao));
					String[] idResp = {responsavelCliente.getId().toString()};
					getControladorCadastro().removerClienteResponsavel(idResp, usuario);
				}
			}
			// ****************** Inserir ou Manter o Cliente Responsável ***************

		}catch(ControladorException ex){
			sessionContext.setRollbackOnly();
			throw ex;
		}
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param clienteImovel
	 *            Descrição do parâmetro
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void inserirClienteImovel(ClienteImovel clienteImovel, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Integer.valueOf(Operacao.OPERACAO_CLIENTE_IMOVEL_INSERIR),
						clienteImovel.getImovel().getId(), clienteImovel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CLIENTE_IMOVEL_INSERIR);

		clienteImovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(clienteImovel);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setArgumentoValor(clienteImovel.getImovel().getId());
		clienteImovel.setOperacaoEfetuada(operacaoEfetuada);

		clienteImovel.setDataInicioRelacao(new Date());

		getControladorUtil().inserir(clienteImovel);
	}

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Collection pesquisarClienteImovel(FiltroClienteImovel filtroClienteImovel, Integer numeroPagina) throws ControladorException{

		try{
			return repositorioClienteImovel.pesquisarClienteImovel(filtroClienteImovel, numeroPagina);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Pesquisa uma a quantidade de cliente imovel com uma query especifica
	 * [UC0015] Filtrar Imovel
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @author Rafael Santos
	 * @since 26/06/2006
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public int pesquisarQuantidadeClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ControladorException{

		try{
			return repositorioClienteImovel.pesquisarQuantidadeClienteImovel(filtroClienteImovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Collection pesquisarClienteImovelTarifaSocial(FiltroClienteImovel filtroClienteImovel, Integer numeroPagina)
					throws ControladorException{

		try{
			return repositorioClienteImovel.pesquisarClienteImovel(filtroClienteImovel, numeroPagina);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Metodo que retorno todos os clinte do filtro passado
	 * 
	 * @param filtroCliente
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @autor thiago toscano
	 * @date 15/12/2005
	 * @throws ControladorException
	 */
	public Collection pesquisarCliente(FiltroCliente filtroCliente) throws ControladorException{

		Collection coll = getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());
		return coll;
	}

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteEndereco
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Collection pesquisarClienteEndereco(FiltroClienteEndereco filtroClienteEndereco) throws ControladorException{

		try{
			return repositorioClienteEndereco.pesquisarClienteEndereco(filtroClienteEndereco);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * atualiza um cliente imovel economia com a data final da relação e o
	 * motivo.
	 * 
	 * @param clientesImoveisEconomia
	 *            Description of the Parameter
	 * @throws ControladorException
	 */
	public void atualizarClienteImovelEconomia(Collection clientesImoveisEconomia) throws ControladorException{

		// try {
		// -------------Parte que atualiza um cliente imovel economia na
		// base---------------------

		Iterator clienteImovelEconomiaIterator = clientesImoveisEconomia.iterator();

		while(clienteImovelEconomiaIterator.hasNext()){
			ClienteImovelEconomia clienteImovelEconomia = (ClienteImovelEconomia) clienteImovelEconomiaIterator.next();

			// Parte de Validacao com Timestamp
			FiltroClienteImovelEconomia filtroClienteImovelEconomia = new FiltroClienteImovelEconomia();

			// Seta o filtro para buscar o cliente imovel economia na base
			filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.ID, clienteImovelEconomia
							.getId()));

			// Procura o cliente na base
			ClienteImovelEconomia clienteImovelEconomiaNaBase = (ClienteImovelEconomia) ((List) (getControladorUtil().pesquisar(
							filtroClienteImovelEconomia, ClienteImovelEconomia.class.getName()))).get(0);

			// Verificar se o cliente já foi atualizado por outro usuário
			// durante
			// esta atualização
			if(clienteImovelEconomiaNaBase.getUltimaAlteracao().after(clienteImovelEconomia.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// Atualiza a data de última alteração
			clienteImovelEconomia.setUltimaAlteracao(new Date());

			// Atualiza o cliente
			getControladorUtil().atualizar(clienteImovelEconomia);
		}
		/*
		 * } catch (ErroRepositorioException ex) {
		 * sessionContext.setRollbackOnly(); throw new
		 * ControladorException("erro.sistema", ex); }
		 */

	}

	/**
	 * Pesquisa ClienteEndereco percorrendo o ClienteImovel
	 * 
	 * @param filtroClienteEndereco
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Cliente> pesquisarClienteEnderecoClienteImovel(FiltroClienteEndereco filtroClienteEndereco)
					throws ControladorException{

		try{
			return repositorioClienteEndereco.pesquisarClienteEnderecoClienteImovel(filtroClienteEndereco);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	public Collection<Cliente> pesquisarClienteDadosClienteEndereco(FiltroCliente filtroCliente, Integer numeroPagina)
					throws ControladorException{

		try{
			return repositorioCliente.pesquisarClienteDadosClienteEndereco(filtroCliente, numeroPagina);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	public Collection<Cliente> pesquisarClienteDadosClienteEnderecoRelatorio(FiltroCliente filtroCliente) throws ControladorException{

		try{
			return repositorioCliente.pesquisarClienteDadosClienteEnderecoRelatorio(filtroCliente);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * Retrona a quantidade de endereços que existem para o Cliente
	 * pesquisarClienteDadosClienteEnderecoCount
	 * 
	 * @author Roberta Costa
	 * @date 29/06/2006
	 * @param filtroCliente
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarClienteDadosClienteEnderecoCount(FiltroCliente filtroCliente) throws ControladorException{

		try{
			return repositorioCliente.pesquisarClienteDadosClienteEnderecoCount(filtroCliente);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	public Collection<Cliente> pesquisarClienteOutrosCriterios(FiltroCliente filtroCliente) throws ControladorException{

		try{
			return repositorioCliente.pesquisarClienteOutrosCriterios(filtroCliente);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Este fluxo secundário tem como objetivo pesquisar o cliente digitado pelo
	 * usuário
	 * [FS0011] - Verificar existência do código do cliente
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idClienteDigitado
	 * @return
	 * @throws ControladorException
	 */
	public Cliente pesquisarClienteDigitado(Integer idClienteDigitado) throws ControladorException{

		// Cria a variável que vai armazenar o cliente pesquisado
		Cliente clienteDigitado = null;

		// Pesquisa o cliente informado pelo usuário no sistema
		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idClienteDigitado));
		Collection<Cliente> colecaoCliente = getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName());

		// Caso exista o cliente no sistema
		// Retorna para o usuário o cliente retornado pela pesquisa
		// Caso contrário retorna um objeto nulo
		if(colecaoCliente != null && !colecaoCliente.isEmpty()){
			clienteDigitado = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
		}

		// Retorna o cliente encontrado ou nulo se não existir
		return clienteDigitado;
	}

	/**
	 * Pesquisa um cliente carregando os dados do relacionamento com ClienteTipo
	 * [UC0321] Emitir Fatura de Cliente Responsável
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2006
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public Cliente pesquisarCliente(Integer idCliente) throws ControladorException{

		// Retorna o cliente encontrado ou vazio se não existir
		try{
			return repositorioCliente.pesquisarCliente(idCliente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ControladorException{

		// Retorna o cliente encontrado ou vazio se não existir
		try{
			return repositorioClienteImovel.pesquisarClienteImovel(filtroClienteImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection<Imovel> pesquisarClienteImovelRelatorio(FiltroClienteImovel filtroClienteImovel) throws ControladorException{

		// Retorna o cliente encontrado ou vazio se não existir
		try{
			return repositorioClienteImovel.pesquisarClienteImovelRelatorio(filtroClienteImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Pesquisa um cliente pelo id
	 * 
	 * @author Rafael Corrêa
	 * @date 01/08/2006
	 * @return Cliente
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Cliente pesquisarObjetoClienteRelatorio(Integer idCliente) throws ControladorException{

		try{
			// pesquisa as gerencias regionais existentes no sisitema
			Object[] objetoCliente = repositorioCliente.pesquisarObjetoClienteRelatorio(idCliente);

			Cliente cliente = new Cliente();

			cliente.setId((Integer) objetoCliente[0]);
			cliente.setNome((String) objetoCliente[1]);

			return cliente;

			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa as quantidades de imóveis e as quantidades de economias
	 * associadas a um cliente
	 * 
	 * @author Rafael Corrêa
	 * @date 23/08/2007
	 * @return Object[]
	 * @exception ControladorException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarQtdeImoveisEEconomiasCliente(Integer idCliente) throws ControladorException{

		try{
			// pesquisa as gerencias regionais existentes no sisitema
			return repositorioCliente.pesquisarQtdeImoveisEEconomiasCliente(idCliente);

			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * O método abaixo realiza uma pesquisa em cliente e verifica se ele existe
	 */
	public Integer verificarExistenciaCliente(Integer codigoCliente) throws ControladorException{

		// Retorna o cliente encontrado ou vazio se não existir
		try{
			return repositorioCliente.verificarExistenciaCliente(codigoCliente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Pesquisar ClienteImovel
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idCliente
	 *            ,
	 *            idImovel
	 * @return Colletion
	 * @throws ControladorException
	 */
	public ClienteImovel pesquisarClienteImovel(Integer idCliente, Integer idImovel) throws ControladorException{

		Collection colecaoClienteImovel = null;

		ClienteImovel retorno = null;

		try{
			colecaoClienteImovel = this.repositorioClienteImovel.pesquisarClienteImovel(idCliente, idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

			retorno = new ClienteImovel();
			Cliente cliente = new Cliente();

			Iterator raIterator = colecaoClienteImovel.iterator();

			Object[] arrayClienteIMovel = (Object[]) raIterator.next();

			cliente.setId((Integer) arrayClienteIMovel[0]);

			cliente.setNome((String) arrayClienteIMovel[1]);

			retorno.setCliente(cliente);
		}

		return retorno;
	}

	/**
	 * Pesquisa todos os telefones de um cliente
	 * 
	 * @author Raphael Rossiter
	 * @date 23/08/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection<ClienteFone> pesquisarClienteFone(Integer idCliente) throws ControladorException{

		Collection colecaoFone = null;
		Collection colecaoRetorno = null;

		try{
			colecaoFone = this.repositorioCliente.pesquisarClienteFone(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoFone != null && !colecaoFone.isEmpty()){

			Iterator foneIterator = colecaoFone.iterator();
			ClienteFone clienteFone = null;
			FoneTipo foneTipo = null;

			colecaoRetorno = new ArrayList();

			while(foneIterator.hasNext()){

				clienteFone = new ClienteFone();

				Object[] arrayFone = (Object[]) foneIterator.next();

				clienteFone.setId((Integer) arrayFone[0]);

				clienteFone.setUltimaAlteracao((Date) arrayFone[5]);

				if(arrayFone[1] != null){
					clienteFone.setDdd((String) arrayFone[1]);
				}

				clienteFone.setTelefone((String) arrayFone[2]);

				if(arrayFone[3] != null){
					clienteFone.setIndicadorTelefonePadrao((Short) arrayFone[3]);
				}

				foneTipo = new FoneTipo();
				foneTipo.setDescricao((String) arrayFone[4]);

				clienteFone.setFoneTipo(foneTipo);

				colecaoRetorno.add(clienteFone);
			}
		}

		return colecaoRetorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecosClienteAbreviado(Integer idCliente) throws ControladorException{

		Collection colecaoEndereco = null;
		Collection colecaoRetorno = null;

		try{
			colecaoEndereco = this.repositorioClienteEndereco.pesquisarEnderecosClienteAbreviado(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoEndereco != null && !colecaoEndereco.isEmpty()){

			Iterator enderecoIterator = colecaoEndereco.iterator();
			ClienteEndereco clienteEndereco = null;

			colecaoRetorno = new ArrayList();

			while(enderecoIterator.hasNext()){

				clienteEndereco = new ClienteEndereco();

				// cria um array de objetos para pegar os parametros de
				// retorno da pesquisa
				Object[] arrayEndereco = (Object[]) enderecoIterator.next();

				LogradouroCep logradouroCep = null;
				if(arrayEndereco[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayEndereco[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayEndereco[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayEndereco[19]);
						logradouro.setNome("" + arrayEndereco[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição logradouro tipo
					if(arrayEndereco[22] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição logradouro titulo
					if(arrayEndereco[23] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep cep = null;
					if(arrayEndereco[10] != null){
						cep = new Cep();
						cep.setCepId((Integer) arrayEndereco[10]);
						cep.setCodigo((Integer) arrayEndereco[16]);
					}

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(cep);
				}

				clienteEndereco.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayEndereco[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayEndereco[21]);

					Bairro bairro = null;
					// nome bairro
					if(arrayEndereco[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) arrayEndereco[3]);
						bairro.setNome("" + arrayEndereco[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(arrayEndereco[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayEndereco[5]);
						municipio.setNome("" + arrayEndereco[6]);

						// id da unidade federação
						if(arrayEndereco[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayEndereco[7]);
							unidadeFederacao.setSigla("" + arrayEndereco[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);
				}

				clienteEndereco.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(arrayEndereco[24] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricao("" + arrayEndereco[24]);
					clienteEndereco.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(arrayEndereco[17] != null){
					clienteEndereco.setNumero("" + arrayEndereco[17]);
				}

				// complemento endereço
				if(arrayEndereco[18] != null){
					clienteEndereco.setComplemento("" + arrayEndereco[18]);
				}

				// indicador endereço correspondencia
				if(arrayEndereco[25] != null){
					clienteEndereco.setIndicadorEnderecoCorrespondencia((Short) arrayEndereco[25]);
				}

				clienteEndereco.setId((Integer) arrayEndereco[26]);

				clienteEndereco.setUltimaAlteracao((Date) arrayEndereco[27]);

				colecaoRetorno.add(clienteEndereco);
			}
		}

		return colecaoRetorno;
	}

	/**
	 * Pesquisa os dados do Cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Cliente consultarCliente(Integer idCliente) throws ControladorException{

		Collection colecaoClientes = null;
		// Collection colecaoRetorno = null;
		Cliente cliente = null;

		try{
			colecaoClientes = this.repositorioCliente.consultarCliente(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoClientes != null && !colecaoClientes.isEmpty()){

			Object[] objetoCliente = (Object[]) colecaoClientes.iterator().next();

			cliente = new Cliente();

			// 0 - Nome do Cliente
			if(objetoCliente[0] != null){
				cliente.setNome((String) objetoCliente[0]);
			}

			// 1 - Nome do Cliente Abreviado
			if(objetoCliente[1] != null){
				cliente.setNomeAbreviado((String) objetoCliente[1]);
			}

			// 2 - Data Vencimento
			if(objetoCliente[2] != null){
				cliente.setDataVencimento((Short) objetoCliente[2]);
			}

			// 3 - Cliente Tipo Descricao
			ClienteTipo clienteTipo = null;
			if(objetoCliente[3] != null){
				clienteTipo = new ClienteTipo();
				clienteTipo.setDescricao((String) objetoCliente[3]);
				cliente.setClienteTipo(clienteTipo);
			}

			// 4 - Indicado Pessoa Fisica ou Juridica
			if(objetoCliente[4] != null){
				if(clienteTipo == null){
					clienteTipo = new ClienteTipo();
				}
				clienteTipo.setIndicadorPessoaFisicaJuridica((Short) objetoCliente[4]);
				cliente.setClienteTipo(clienteTipo);
			}

			// 5 - E-mail
			if(objetoCliente[5] != null){
				cliente.setEmail((String) objetoCliente[5]);
			}

			// 6 - Indicador de Acao Cobranca
			if(objetoCliente[6] != null){
				cliente.setIndicadorAcaoCobranca((Short) objetoCliente[6]);
			}

			// 7 - CPF
			if(objetoCliente[7] != null){
				cliente.setCpf((String) objetoCliente[7]);
			}

			// 8 - RG
			if(objetoCliente[8] != null){
				cliente.setRg((String) objetoCliente[8]);
			}

			// 9 - Data Emissao RG
			if(objetoCliente[9] != null){
				cliente.setDataEmissaoRg((Date) objetoCliente[9]);
			}

			// 10 - Orgao Expedidor RG
			OrgaoExpedidorRg orgaoExpedidorRg = null;
			if(objetoCliente[10] != null){
				orgaoExpedidorRg = new OrgaoExpedidorRg();
				orgaoExpedidorRg.setDescricaoAbreviada((String) objetoCliente[10]);
				cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
			}

			// 11 - Unidade Federacao
			UnidadeFederacao unidadeFederacao = null;
			if(objetoCliente[11] != null){
				unidadeFederacao = new UnidadeFederacao();
				unidadeFederacao.setSigla((String) objetoCliente[11]);
				cliente.setUnidadeFederacao(unidadeFederacao);
			}

			// 12 - Data Nascimento
			if(objetoCliente[12] != null){
				cliente.setDataNascimento((Date) objetoCliente[12]);
			}

			// 13 - Profissao
			Profissao profissao = null;
			if(objetoCliente[13] != null){
				profissao = new Profissao();
				profissao.setDescricao((String) objetoCliente[13]);
				cliente.setProfissao(profissao);
			}

			// 14 - Pessoa Sexo
			PessoaSexo pessoaSexo = null;
			if(objetoCliente[14] != null){
				pessoaSexo = new PessoaSexo();
				pessoaSexo.setDescricao((String) objetoCliente[14]);
				cliente.setPessoaSexo(pessoaSexo);
			}

			// 15 - CNPJ
			if(objetoCliente[15] != null){
				cliente.setCnpj((String) objetoCliente[15]);
			}

			// 16 - Ramo Atividade
			RamoAtividade ramoAtividade = null;
			if(objetoCliente[16] != null){
				ramoAtividade = new RamoAtividade();
				ramoAtividade.setDescricao((String) objetoCliente[16]);
				cliente.setRamoAtividade(ramoAtividade);
			}

			// 17 - Codigo Cliente Responsavel
			Cliente clienteResponsavel = null;
			if(objetoCliente[17] != null){
				clienteResponsavel = new Cliente();
				clienteResponsavel.setId((Integer) objetoCliente[17]);
				cliente.setCliente(cliente);
			}

			// 18 - Nome Cliente Responsavel
			if(objetoCliente[18] != null){
				if(clienteResponsavel == null){
					clienteResponsavel = new Cliente();
				}
				clienteResponsavel.setNome((String) objetoCliente[18]);
				cliente.setCliente(cliente);
			}

			if(objetoCliente[19] != null){
				cliente.setInscricaoEstadual((String) objetoCliente[19]);
			}

			if(objetoCliente[20] != null){

				AtividadeEconomica atividadeEconomica = new AtividadeEconomica();
				atividadeEconomica.setCodigo(objetoCliente[20].toString());
				atividadeEconomica.setDescricao(objetoCliente[21].toString());
				cliente.setAtividadeEconomica(atividadeEconomica);
			}

			if(objetoCliente[22] != null){

				cliente.setNumeroBeneficio(objetoCliente[22].toString());
			}

		}

		return cliente;
	}

	/**
	 * Pesquisa todos os endereços do cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecoCliente(Integer idCliente) throws ControladorException{

		Collection colecaoClienteEndereco = null;
		Collection colecaoRetorno = null;

		try{
			colecaoClienteEndereco = this.repositorioCliente.pesquisarEnderecosCliente(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoClienteEndereco != null && !colecaoClienteEndereco.isEmpty()){

			Iterator clienteEnderecoIterator = colecaoClienteEndereco.iterator();
			ClienteEndereco clienteEndereco = null;
			colecaoRetorno = new ArrayList();

			while(clienteEnderecoIterator.hasNext()){

				Object[] array = (Object[]) clienteEnderecoIterator.next();

				clienteEndereco = new ClienteEndereco();

				// 0- Endereco Tipo
				EnderecoTipo enderecoTipo = null;
				if(array[0] != null){
					enderecoTipo = new EnderecoTipo();
					enderecoTipo.setDescricao((String) array[0]);
					clienteEndereco.setEnderecoTipo(enderecoTipo);
				}

				// 1- Indicador Endereco Correspondencia
				if(array[0] != null){
					clienteEndereco.setIndicadorEnderecoCorrespondencia((Short) array[1]);
				}
				colecaoRetorno.add(clienteEndereco);
			}
		}

		return colecaoRetorno;

	}

	/**
	 * Pesquisa o nome do cliente a partir do imóvel Autor: Sávio Luiz Data:
	 * 21/12/2005
	 */
	public String pesquisarNomeClientePorImovel(Integer idImovel) throws ControladorException{

		String nomeCliente = "";

		try{
			nomeCliente = this.repositorioClienteImovel.pesquisarNomeClientePorImovel(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return nomeCliente;
	}

	/**
	 * Pesquisa o nome, cnpj e id do cliente a partir do imóvel Autor: Rafael Corrêa Data:
	 * 25/09/2006
	 */
	public Cliente pesquisarDadosClienteRelatorioParcelamentoPorImovel(Integer idImovel) throws ControladorException{

		Cliente cliente = null;
		Object[] dadosCliente = null;

		try{
			dadosCliente = this.repositorioClienteImovel.pesquisarDadosClienteRelatorioParcelamentoPorImovel(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(dadosCliente != null){

			cliente = new Cliente();

			if(dadosCliente[0] != null){
				// Id
				cliente.setId((Integer) dadosCliente[0]);
			}

			if(dadosCliente[1] != null){
				// Nome
				cliente.setNome((String) dadosCliente[1]);
			}else{
				cliente.setNome("");
			}

			if(dadosCliente[2] != null){
				// CPF
				cliente.setCpf((String) dadosCliente[2]);
			}

			if(dadosCliente[3] != null){
				// CNPJ
				cliente.setCnpj((String) dadosCliente[3]);
			}

			if(dadosCliente[4] != null){
				// orgão expedidor
				OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
				orgaoExpedidorRg.setDescricao((String) dadosCliente[4]);
				cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
			}

			if(dadosCliente[5] != null){
				// unidade federativa
				UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
				unidadeFederacao.setSigla((String) dadosCliente[5]);
				cliente.setUnidadeFederacao(unidadeFederacao);
			}

			if(dadosCliente[6] != null){
				// rg
				cliente.setRg((String) dadosCliente[6]);
			}

		}

		return cliente;
	}

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * Pesquisa o telefone principal do Cliente para o
	 * Relatório de OS
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */

	public String pesquisarClienteFonePrincipal(Integer idCliente) throws ControladorException{

		Object[] dadosClienteFone = null;
		String telefoneFormatado = "";

		try{

			dadosClienteFone = this.repositorioCliente.pesquisarClienteFonePrincipal(idCliente);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(dadosClienteFone != null){

			// DDD
			if(dadosClienteFone[0] != null){
				telefoneFormatado = telefoneFormatado + "(" + dadosClienteFone[0].toString() + ")";
			}

			// Número do Telefone
			if(dadosClienteFone[1] != null){
				telefoneFormatado = telefoneFormatado + dadosClienteFone[1];
			}

			// Ramal
			if(dadosClienteFone[2] != null){
				telefoneFormatado = telefoneFormatado + "/" + dadosClienteFone[2];
			}

		}

		return telefoneFormatado;

	}

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Pesquisa os dados do cliente para a emissão do boletim
	 * 
	 * @author Rafael Corrêa
	 * @date 16/05/2007
	 * @param idImovel
	 *            , clienteRelacaoTipo
	 * @throws ControladorException
	 */
	public ClienteEmitirBoletimCadastroHelper pesquisarClienteEmitirBoletimCadastro(Integer idImovel, Integer clienteRelacaoTipo)
					throws ControladorException{

		Collection colecaoDadosCliente = null;
		ClienteEmitirBoletimCadastroHelper clienteEmitirBoletimCadastroHelper = null;

		try{

			colecaoDadosCliente = this.repositorioCliente.pesquisarClienteEmitirBoletimCadastro(idImovel, clienteRelacaoTipo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoDadosCliente != null && !colecaoDadosCliente.isEmpty()){

			Iterator colecaoDadosClienteIterator = colecaoDadosCliente.iterator();

			boolean primeiroRegistro = true;

			Collection clientesFone = new ArrayList();

			while(colecaoDadosClienteIterator.hasNext()){

				Object[] dadosCliente = (Object[]) colecaoDadosClienteIterator.next();

				if(primeiroRegistro){

					clienteEmitirBoletimCadastroHelper = new ClienteEmitirBoletimCadastroHelper();
					Cliente cliente = new Cliente();
					ClienteEndereco clienteEndereco = new ClienteEndereco();
					LogradouroCep logradouroCep = new LogradouroCep();
					LogradouroBairro logradouroBairro = new LogradouroBairro();

					// Dados do Cliente
					// Id do Cliente
					if(dadosCliente[0] != null){ // 0
						cliente.setId((Integer) dadosCliente[0]);
					}

					// Nome do Cliente
					if(dadosCliente[1] != null){ // 1
						cliente.setNome((String) dadosCliente[1]);
					}

					// Tipo do Cliente
					if(dadosCliente[2] != null){ // 2
						ClienteTipo clienteTipo = new ClienteTipo();
						clienteTipo.setId((Integer) dadosCliente[2]);
						cliente.setClienteTipo(clienteTipo);
					}

					// CPF do Cliente
					if(dadosCliente[3] != null){ // 3
						cliente.setCpf((String) dadosCliente[3]);
					}

					// CNPJ do Cliente
					if(dadosCliente[4] != null){ // 4
						cliente.setCnpj((String) dadosCliente[4]);
					}

					// RG do Cliente
					if(dadosCliente[5] != null){ // 5
						cliente.setRg((String) dadosCliente[5]);
					}

					// Data de Emissão RG
					if(dadosCliente[6] != null){ // 6
						cliente.setDataEmissaoRg((Date) dadosCliente[6]);
					}

					// Órgão Expedidor RG
					if(dadosCliente[7] != null){ // 7
						OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
						orgaoExpedidorRg.setDescricaoAbreviada((String) dadosCliente[7]);
						cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
					}

					// Unidade Federação
					if(dadosCliente[8] != null){ // 8
						UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
						unidadeFederacao.setSigla((String) dadosCliente[8]);
						cliente.setUnidadeFederacao(unidadeFederacao);
					}

					// Data de Nascimento
					if(dadosCliente[9] != null){ // 9
						cliente.setDataNascimento((Date) dadosCliente[9]);
					}

					// Profissão
					if(dadosCliente[10] != null){ // 10
						Profissao profissao = new Profissao();
						profissao.setDescricao((String) dadosCliente[10]);
						cliente.setProfissao(profissao);
					}

					// Sexo
					if(dadosCliente[11] != null){ // 11
						PessoaSexo pessoaSexo = new PessoaSexo();
						pessoaSexo.setId((Integer) dadosCliente[11]);
						cliente.setPessoaSexo(pessoaSexo);
					}

					// Nome da Mãe
					if(dadosCliente[12] != null){ // 12
						cliente.setNomeMae((String) dadosCliente[12]);
					}

					// Indicador de Uso
					if(dadosCliente[13] != null){ // 13
						cliente.setIndicadorUso((Short) dadosCliente[13]);
					}

					// Inscrição Estadual
					if(dadosCliente[27] != null){ // 27
						cliente.setInscricaoEstadual((String) dadosCliente[27]);
					}

					// E-Mail
					if(dadosCliente[28] != null){ // 28
						cliente.setEmail((String) dadosCliente[28]);
					}

					clienteEmitirBoletimCadastroHelper.setCliente(cliente);

					// Dados do Endereço do Cliente
					// Tipo de Endereço
					if(dadosCliente[14] != null){ // 14
						EnderecoTipo enderecoTipo = new EnderecoTipo();
						enderecoTipo.setId((Integer) dadosCliente[14]);
						clienteEndereco.setEnderecoTipo(enderecoTipo);
					}

					// Id do Logradouro
					if(dadosCliente[15] != null){ // 15
						Logradouro logradouro = new Logradouro();
						logradouro.setId((Integer) dadosCliente[15]);
						logradouroCep.setLogradouro(logradouro);
						logradouroBairro.setLogradouro(logradouro);
					}

					// Endereço Abreviado do Cliente
					String enderecoAbreviado = getControladorEndereco().pesquisarEnderecoClienteAbreviado(cliente.getId());

					if(enderecoAbreviado != null && !enderecoAbreviado.trim().equals("")){
						clienteEmitirBoletimCadastroHelper.setEnderecoFormatado(enderecoAbreviado);
					}

					// CEP
					if(dadosCliente[16] != null){ // 16
						Cep cep = new Cep();
						cep.setCodigo((Integer) dadosCliente[16]);
						logradouroCep.setCep(cep);
					}

					// Id do Bairro
					if(dadosCliente[17] != null){ // 17
						Bairro bairro = new Bairro();
						bairro.setCodigo((Integer) dadosCliente[17]);
						logradouroBairro.setBairro(bairro);
					}

					// Endereço de Referência
					if(dadosCliente[18] != null){ // 18
						EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
						enderecoReferencia.setId((Integer) dadosCliente[18]);
						clienteEndereco.setEnderecoReferencia(enderecoReferencia);
					}

					// Número do Imóvel
					if(dadosCliente[19] != null){ // 19
						clienteEndereco.setNumero((String) dadosCliente[19]);
					}

					// Complemento
					if(dadosCliente[20] != null){ // 20
						clienteEndereco.setComplemento((String) dadosCliente[20]);
					}

					clienteEndereco.setLogradouroCep(logradouroCep);
					clienteEndereco.setLogradouroBairro(logradouroBairro);
					clienteEmitirBoletimCadastroHelper.setClienteEndereco(clienteEndereco);

					primeiroRegistro = false;

				}

				ClienteFone clienteFone = new ClienteFone();

				// Dados do Telefone do Cliente
				// Tipo do Telefone
				if(dadosCliente[21] != null){ // 21
					FoneTipo foneTipo = new FoneTipo();
					foneTipo.setId((Integer) dadosCliente[21]);
					clienteFone.setFoneTipo(foneTipo);
				}

				// DDD
				if(dadosCliente[22] != null){ // 22
					clienteFone.setDdd((String) dadosCliente[22]);
				}

				// Número Telefone
				if(dadosCliente[23] != null){ // 23
					clienteFone.setTelefone((String) dadosCliente[23]);
				}

				// Ramal
				if(dadosCliente[24] != null){ // 24
					clienteFone.setRamal((String) dadosCliente[24]);
				}

				clientesFone.add(clienteFone);

				// Ramo Atividade
				if(dadosCliente[25] != null){ // 25
					clienteEmitirBoletimCadastroHelper.setRamoAtividade((String) dadosCliente[25]);
				}

				// Cliente Tipo Descrição
				if(dadosCliente[26] != null){ // 26
					clienteEmitirBoletimCadastroHelper.setDsClienteTipo((String) dadosCliente[26]);
				}

			}

			clienteEmitirBoletimCadastroHelper.setClienteFone(clientesFone);

		}

		return clienteEmitirBoletimCadastroHelper;

	}

	/**
	 * Usado pelo Filtrar Cliente
	 * Filtra o Cliente usando os paramentos informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 * @param inscricaoEstadual
	 *            Adicionado à consulta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarCliente(String codigo, String cpf, String rg, String cnpj, String nome, String nomeMae, String cep,
					String idMunicipio, String idBairro, String idLogradouro, String indicadorUso, String tipoPesquisa,
					String tipoPesquisaNomeMae, String clienteTipo, Integer numeroPagina, String inscricaoEstadual,
					String indicadorContaBraille, String documentoValidado, String numeroBeneficio)
					throws ControladorException{

		Collection colecaoDadosCliente = null;
		Collection colecaoClientes = null;

		try{

			colecaoDadosCliente = this.repositorioCliente.filtrarCliente(codigo, cpf, rg, cnpj, nome, nomeMae, cep, idMunicipio, idBairro,
							idLogradouro, indicadorUso, tipoPesquisa, tipoPesquisaNomeMae, clienteTipo, numeroPagina, inscricaoEstadual,
							indicadorContaBraille, documentoValidado, numeroBeneficio);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoDadosCliente != null && !colecaoDadosCliente.isEmpty()){

			Iterator iteratorColecaoDadosCliente = colecaoDadosCliente.iterator();
			Cliente cliente = null;
			colecaoClientes = new ArrayList();

			while(iteratorColecaoDadosCliente.hasNext()){

				cliente = new Cliente();

				Object[] array = (Object[]) iteratorColecaoDadosCliente.next();

				// codigo
				if(array[0] != null){
					cliente.setId((Integer) array[0]);
				}

				// nome
				if(array[1] != null){
					cliente.setNome((String) array[1]);
				}

				// rg
				if(array[2] != null){
					cliente.setRg((String) array[2]);
				}

				// cpf
				if(array[3] != null){
					cliente.setCpf((String) array[3]);
				}

				// cnpj
				if(array[4] != null){
					cliente.setCnpj((String) array[4]);
				}

				ClienteTipo tipoCliente = null;
				// indicadorPessoaFisicaJuridica
				if(array[5] != null){
					tipoCliente = new ClienteTipo();
					tipoCliente.setIndicadorPessoaFisicaJuridica((Short) array[5]);
					cliente.setClienteTipo(tipoCliente);
				}

				// descricao orgaoExpedidorRg
				if(array[6] != null){
					OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
					orgaoExpedidorRg.setDescricao((String) array[6]);
					cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
				}

				// silga orgaoExpedidorRg
				if(array[7] != null){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();

					unidadeFederacao.setSigla((String) array[7]);
					cliente.setUnidadeFederacao(unidadeFederacao);
				}
				// descricao tipo cliente
				if(array[8] != null){
					if(tipoCliente == null){
						tipoCliente = new ClienteTipo();
					}
					tipoCliente.setDescricao((String) array[8]);
					cliente.setClienteTipo(tipoCliente);
				}

				// indicador uso
				if(array[9] != null){
					cliente.setIndicadorUso((Short) array[9]);
				}

				// Documento Validado

				if(array[10] != null){
					cliente.setDocumentoValidado((Short) array[10]);
				}

				colecaoClientes.add(cliente);
			}

		}

		return colecaoClientes;
	}

	/**
	 * Usado pelo Filtrar Cliente
	 * Filtra a quantidade de Clientes usando os paramentos informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 * @param inscricaoEstadual
	 *            Adicionado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object filtrarQuantidadeCliente(String codigo, String cpf, String rg, String cnpj, String nome, String nomeMae, String cep,
					String idMunicipio, String idBairro, String idLogradouro, String indicadorUso, String tipoPesquisa,
					String tipoPesquisaNomeMae, String clienteTipo, String inscricaoEstadual, String indicadorContaBraille,
					String documentoValidado, String numeroBeneficio)
					throws ControladorException{

		Object quantidade = null;
		Integer retorno = null;

		try{
			quantidade = repositorioCliente.filtrarQuantidadeCliente(codigo, cpf, rg, cnpj, nome, nomeMae, cep, idMunicipio, idBairro,
							idLogradouro, indicadorUso, tipoPesquisa, tipoPesquisaNomeMae, clienteTipo, inscricaoEstadual,
							indicadorContaBraille, documentoValidado, numeroBeneficio);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(quantidade != null){
			retorno = (Integer) quantidade;

		}

		return retorno;
	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Imóveis pelo id do Cliente, indicador de uso, motivo
	 * do fim da relação, pelo perfil do imóvel e pelo tipo da relação do cliente carregando o
	 * imóvel
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloClienteTarifaSocial(Integer idCliente) throws ControladorException{

		try{
			return repositorioClienteImovel.pesquisarClienteImovelPeloClienteTarifaSocial(idCliente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Imóveis pelo id do Imóvel carregando o imóvel, o cliente, o perfil do
	 * imóvel,
	 * o orgão expedidor do RG e a unidade da federação
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloImovelTarifaSocial(Integer idImovel) throws ControladorException{

		try{
			return repositorioClienteImovel.pesquisarClienteImovelPeloImovelTarifaSocial(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Imóveis pelo id do Imóvel carregando os dados necessários para retornar
	 * o seu endereço
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloImovelParaEndereco(Integer idImovel) throws ControladorException{

		try{
			return repositorioClienteImovel.pesquisarClienteImovelPeloImovelParaEndereco(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Verifica se é usuario iquilino ou não
	 * 
	 * @author Sávio Luiz
	 * @date 08/01/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificaUsuarioinquilino(Integer idImovel) throws ControladorException{

		Collection colecao = null;
		Integer idClienteUsuario = null;
		boolean naoInquilino = false;
		try{
			idClienteUsuario = repositorioClienteImovel.retornaIdClienteUsuario(idImovel);
			colecao = repositorioClienteImovel.retornaClientesRelacao(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		if(colecao != null && !colecao.isEmpty()){
			Iterator iteParmsCliente = colecao.iterator();
			while(iteParmsCliente.hasNext()){
				Integer idClienteBase = (Integer) iteParmsCliente.next();
				if(idClienteBase.equals(idClienteUsuario)){
					naoInquilino = true;
					break;
				}

			}
		}else{
			naoInquilino = true;
		}
		return naoInquilino;
	}

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ControladorException{

		try{

			this.repositorioClienteEndereco.atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException{

		try{

			this.repositorioClienteEndereco.atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0544] Gerar Arwuivo Texto do Faturamento
	 * Pesquisar ClienteImovel
	 * 
	 * @author Flávio Cordeiro
	 * @date 4/04/2006
	 * @return Colletion
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteImovelGerarArquivoFaturamento() throws ControladorException{

		try{

			Collection clientes = new HashSet();

			Collection colecaoObjetos = this.repositorioClienteImovel.pesquisarClienteImovelGerarArquivoFaturamento();
			if(!colecaoObjetos.isEmpty()){
				Iterator iterator = colecaoObjetos.iterator();
				while(iterator.hasNext()){
					Object[] objeto = (Object[]) iterator.next();

					Cliente cliente = new Cliente();
					if(objeto[0] != null){
						cliente.setId((Integer) objeto[0]);
					}
					if(objeto[1] != null){
						cliente.setNome((String) objeto[1]);
					}
					clientes.add(cliente);
				}
			}
			return clientes;

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0320] Gerar Fatura de Cliente Responsável
	 * 
	 * @author Saulo Lima
	 * @date 16/12/2008
	 * @return Colletion<Cliente>
	 * @throws ErroRepositorioException
	 */
	public Collection<Cliente> pesquisarClienteImovelGerarFaturaClienteResponsavel() throws ControladorException{

		try{
			Collection<Cliente> clientes = new ArrayList<Cliente>();

			Collection<Object> colecaoObjetos = this.repositorioClienteImovel.pesquisarClienteImovelGerarFaturaClienteResponsavel();
			if(!colecaoObjetos.isEmpty()){
				Iterator<Object> iterator = colecaoObjetos.iterator();
				while(iterator.hasNext()){
					Object[] objeto = (Object[]) iterator.next();

					Cliente cliente = new Cliente();
					if(objeto[0] != null){
						cliente.setId((Integer) objeto[0]);
					}
					if(objeto[1] != null){
						cliente.setNome((String) objeto[1]);
					}
					clientes.add(cliente);
				}
			}
			return clientes;

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa o rg do cliente do parcelamento a partir do idParcelamento Autor: Vivianne Sousa
	 * Data: 20/06/2007
	 */
	public Cliente pesquisarDadosClienteDoParcelamentoRelatorioParcelamento(Integer idParcelamento) throws ControladorException{

		Cliente cliente = null;
		Object[] dadosCliente = null;

		try{
			dadosCliente = this.repositorioClienteImovel.pesquisarDadosClienteDoParcelamentoRelatorioParcelamento(idParcelamento);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(dadosCliente != null){

			cliente = new Cliente();

			if(dadosCliente[0] != null){
				// orgão expedidor
				OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
				orgaoExpedidorRg.setDescricaoAbreviada((String) dadosCliente[0]);
				cliente.setOrgaoExpedidorRg(orgaoExpedidorRg);
			}

			if(dadosCliente[1] != null){
				// unidade federativa
				UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
				unidadeFederacao.setSigla((String) dadosCliente[1]);
				cliente.setUnidadeFederacao(unidadeFederacao);
			}

			if(dadosCliente[2] != null){
				// rg
				cliente.setRg((String) dadosCliente[2]);
			}

		}

		return cliente;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 27/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente obterIdENomeCliente(String cpf) throws ControladorException{

		Object[] dadosCliente = null;
		Cliente cliente = null;

		try{

			dadosCliente = this.repositorioCliente.obterIdENomeCliente(cpf);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(dadosCliente != null){
			cliente = new Cliente();
			// id do Cliente
			if(dadosCliente[0] != null){
				cliente.setId((Integer) dadosCliente[0]);
			}

			// nome de Cliente
			if(dadosCliente[1] != null){
				cliente.setNome((String) dadosCliente[1]);
			}

		}

		return cliente;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarCpfCnpjCliente(String cpfCnpj, Integer idCliente, String indicadorPessoaFisicaJuridica)
					throws ControladorException{

		try{

			this.repositorioCliente.atualizarCpfCnpjCliente(cpfCnpj, idCliente, indicadorPessoaFisicaJuridica);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0864] Gerar Certidão Negativa por Cliente
	 * 
	 * @author Rafael Corrêa
	 * @date 25/09/2008
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarClientesAssociadosResponsavel(Integer idCliente) throws ControladorException{

		try{

			return this.repositorioCliente.pesquisarClientesAssociadosResponsavel(idCliente);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * @throws ControladorException
	 */
	public Collection pesquisarCadastroClientesRedundantes() throws ControladorException{

		System.out.print("Inicio da pesquisa de Clientes Redundantes");
		Collection retorno = new ArrayList();
		Collection retornoTemp;
		try{
			retornoTemp = this.repositorioCliente.pesquisarCadastroClientesRedundantes();
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		Integer qtdLidos = 0;
		Object[] itemAux = null;
		Object[] itemRetorno = null;

		Collection idClienteManter = new ArrayList();
		Collection idsClienteSubstituir = new ArrayList();
		try{
			Cliente cliente;
			for(Object[] item : (Collection<Object[]>) retornoTemp){
				if(qtdLidos > 0){
					if(((item[1] != null && item[2] == null) || (item[1] == null && item[2] != null)) // Nao
									// trata
									// casos
									// que
									// possue
									// ambos
									// cpf
									// e
									// cnpj
									&& ((itemAux[1] != null && ((String) itemAux[1]).equals((String) item[1])) // Cpf
									// iguais
									|| (itemAux[2] != null && ((String) itemAux[2]).equals((String) item[2]))) // Cnpj
									// iguais
									&& (((String) item[1] != null && CpfCnpj.isValid((String) item[1]) && !((String) item[1])
													.equals("00000000000")) // 
									|| (((String) item[2] != null && CpfCnpj.isValid((String) item[2]))))

									&& ((String) item[4] != null && ((String) itemAux[4] != null) && ((String) itemAux[4])
													.equals((String) item[4]))){ // validacao por
						// nome

						if(idsClienteSubstituir == null){
							idsClienteSubstituir = new ArrayList();
						}
						cliente = new Cliente();
						cliente.setId((Integer) item[0]);
						cliente.setCpf((String) item[1]);
						cliente.setCnpj((String) item[2]);
						cliente.setUltimaAlteracao((Date) item[3]);
						idsClienteSubstituir.add(cliente);
						qtdLidos++;

					}else{
						if(idsClienteSubstituir != null && !idsClienteSubstituir.isEmpty()){
							cliente = new Cliente();
							cliente.setId((Integer) itemAux[0]);
							cliente.setCpf((String) itemAux[1]);
							cliente.setCnpj((String) itemAux[2]);
							cliente.setUltimaAlteracao((Date) itemAux[3]);
							idClienteManter.add(cliente);
							itemRetorno = new Object[2];
							itemRetorno[0] = idClienteManter;
							itemRetorno[1] = idsClienteSubstituir;
							retorno.add(itemRetorno);
							idClienteManter = new ArrayList();
							idsClienteSubstituir = null;
						}
						itemAux = item;
						qtdLidos++;
					}

				}else{
					itemAux = item;
					qtdLidos++;
				}
			}
			if(idsClienteSubstituir != null && !idsClienteSubstituir.isEmpty()){
				cliente = new Cliente();
				cliente.setId((Integer) itemAux[0]);
				cliente.setCpf((String) itemAux[1]);
				cliente.setCnpj((String) itemAux[2]);
				cliente.setUltimaAlteracao((Date) itemAux[3]);
				idClienteManter.add(cliente);
				itemRetorno = new Object[2];
				itemRetorno[0] = idClienteManter;
				itemRetorno[1] = idsClienteSubstituir;
				retorno.add(itemRetorno);
				idClienteManter = new ArrayList();
				idsClienteSubstituir = null;
			}
		}catch(Exception e){
			System.out.print(itemAux.toString());
		}
		System.out.print("Fim da pesquisa de clientes redundantes");
		return retorno;
	}

	/**
	 * 
	 */
	public void corrigirCadastroClientesRedundantes(Collection colecao) throws ControladorException{

		System.out.print("Inicio da correção dos clientes redundantes");
		System.out.print("tamanho da coleçao: " + colecao.size());
		if(colecao != null && !colecao.isEmpty()){
			Collection colecaoClienteASerMantido = new ArrayList();
			Collection clientesASeremSubstituidos = new ArrayList();
			Cliente clienteASerMantido = null;
			StringBuilder textoArquivo = new StringBuilder();
			try{

				for(Object[] item : (Collection<Object[]>) colecao){

					colecaoClienteASerMantido = (Collection<Cliente>) item[0];
					clienteASerMantido = (Cliente) colecaoClienteASerMantido.iterator().next();

					clientesASeremSubstituidos = (Collection<String[]>) item[1];

					textoArquivo.append("Matricula mantida:;" + clienteASerMantido.getId() + ";");
					textoArquivo.append("Matriculas Substituidas:;");
					for(Cliente c : (Collection<Cliente>) clientesASeremSubstituidos){
						textoArquivo.append(c.getId() + ";");
					}
					textoArquivo.append(";\n");
					// ARRECADADOR
					textoArquivo.append("ARRECADADOR");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesArrecadador(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// ARRECADADOR_CONTRATO
					textoArquivo.append("ARRECADADOR_CONTRATO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesArrecadadorContrato(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// ATENDIMENTO_INCOMPLETO
					textoArquivo.append("ATENDIMENTO_INCOMPLETO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesAtendimento(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CLIENTE_CONTA
					textoArquivo.append("CLIENTE_CONTA");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesClienteConta(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CLIENTE_CONTA_HISTORICO
					textoArquivo.append("CLIENTE_CONTA_HISTORICO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesClienteContaHistorico(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CLIENTE_ENDERECO
					textoArquivo.append("CLIENTE_ENDERECO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesClienteEndereco(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CLIENTE_FONE
					textoArquivo.append("CLIENTE_FONE");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesClienteFone(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CLIENTE_GUIA_PAGAMENTO
					textoArquivo.append("CLIENTE_GUIA_PAGAMENTO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesClienteGuiaPagamento(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CLIENTE_GUIA_PAGAMENTO_HIST
					textoArquivo.append("CLIENTE_GUIA_PAGAMENTO_HIST");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesClienteGuiaPagamentoHist(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CLIENTE_IMOVEL
					textoArquivo.append("CLIENTE_IMOVEL");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesClienteImovel(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CLIENTE_IMOVEL_ECONOMIA
					textoArquivo.append("CLIENTE_IMOVEL_ECONOMIA");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesClienteImovelEconomia(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// COBRANCA_ACAO_ATIVIDADE_COMAND (2 COLUNAS)
					textoArquivo.append("COBRANCA_ACAO_ATIVIDADE_COMAND");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesCobrancaAcaoAtividadeComando(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// COBRANCA_DOCUMENTO
					textoArquivo.append("COBRANCA_DOCUMENTO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesCobrancaDocumento(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// CONTA_IMPRESSAO
					textoArquivo.append("CONTA_IMPRESSAO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesContaImpressao(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// DEVOLUCAO
					textoArquivo.append("DEVOLUCAO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesDevolucao(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// DEVOLUCAO_HISTORICO
					textoArquivo.append("DEVOLUCAO_HISTORICO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesDevolucaoHistorico(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// DOCUMENTO_NAO_ENTREGUE
					textoArquivo.append("DOCUMENTO_NAO_ENTREGUE");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesDocumentoNaoEntregue(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// ENTIDADE_BENEFICENTE
					textoArquivo.append("ENTIDADE_BENEFICENTE");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesEntidadeBeneficente(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// FATURA
					textoArquivo.append("FATURA");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente
									.atualizaClientesRedundantesFatura(clienteASerMantido, clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// GUIA_DEVOLUCAO
					textoArquivo.append("GUIA_DEVOLUCAO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesGuiaDevolucao(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// GUIA_PAGAMENTO
					textoArquivo.append("GUIA_PAGAMENTO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesGuiaPagamento(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// GUIA_PAGAMENTO_HISTORICO
					textoArquivo.append("GUIA_PAGAMENTO_HISTORICO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesGuiaPagamentoHistorico(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// IMOVEL_COBRANCA_SITUACAO (3 COLUNAS)
					textoArquivo.append("IMOVEL_COBRANCA_SITUACAO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesImovelCobrancaSituacao(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// LEITURISTA
					textoArquivo.append("LEITURISTA");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesLeiturista(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// LOCALIDADE
					textoArquivo.append("LOCALIDADE");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesLocalidade(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// NEGATIVACAO_CRITERIO
					textoArquivo.append("NEGATIVACAO_CRITERIO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesNegativacaoCriterio(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// NEGATIVADOR
					textoArquivo.append("NEGATIVADOR");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesNegativador(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// NEGATIVADOR_MOVIMENTO_REG
					textoArquivo.append("NEGATIVADOR_MOVIMENTO_REG");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesNegativadorMovimentoReg(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// PAGAMENTO
					textoArquivo.append("PAGAMENTO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesPagamento(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// PAGAMENTO_HISTORICO
					textoArquivo.append("PAGAMENTO_HISTORICO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesPagamentoHistorico(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// PARCELAMENTO
					textoArquivo.append("PARCELAMENTO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesParcelamento(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// REGISTRO_ATENDIMENTO_SOLICITANTE
					textoArquivo.append("REGISTRO_ATENDIMENTO_SOLICITANTE");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesRegistroAtendimentoSolicitante(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// SISTEMA_PARAMETRO (2 COLUNAS)
					textoArquivo.append("SISTEMA_PARAMETRO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesSistemaParametro(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");
					// VENCIMENTO_ALTERNATIVO
					textoArquivo.append("VENCIMENTO_ALTERNATIVO");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesVencimentoAlternativo(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");

					// CLIENTE
					textoArquivo.append("CLIENTE");
					textoArquivo.append(";");
					textoArquivo.append(repositorioCliente.atualizaClientesRedundantesCliente(clienteASerMantido,
									clientesASeremSubstituidos));
					textoArquivo.append(";\n");

					// getControladorUtil().removerColecaoObjetos(clientesASeremSubstituidos);
					textoArquivo.append("========================================= \n");
				}
				File arquivo = File.createTempFile("RelatorioExecucaoFuncionaliade", ".csv");
				BufferedWriter out = null;
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo.getAbsolutePath())));

				out.write(textoArquivo.toString());
				out.newLine();

				out.flush();
				out.close();

				// criar o arquivo zip
				String nomeZip = "RelatorioExecucaoFuncionaliade";
				File compactado = new File(nomeZip + ".zip"); // nomeZip
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));
				ZipUtil.adicionarArquivo(zos, arquivo);

				// close the stream
				zos.close();

				EnvioEmail envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.UNIFICACAO_CADASTRO_CLIENTES_REDUNDANTES);
				ServicosEmail.enviarMensagemArquivoAnexado(envioEmail.getEmailReceptor(), envioEmail.getEmailRemetente(), envioEmail
								.getCorpoMensagem(), "", compactado);

				System.out.print("Fim da execução funcionalidade UNIFICAÇÃO CLIENTES REDUNDANTES :" + arquivo.getAbsolutePath());
				System.out.print("arquivo enviado :" + compactado.getAbsolutePath());
			}catch(IOException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}catch(Exception e){
				sessionContext.setRollbackOnly();
				e.printStackTrace();
			}
		}
	}

	public Cliente retornarDadosClienteUsuario(Integer idImovel) throws ControladorException{

		try{
			return repositorioClienteImovel.retornarDadosClienteUsuario(idImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	public Collection pesquisarClienteFonePorCliente(Integer idCliente) throws ControladorException{

		try{
			return repositorioCliente.pesquisarClienteFonePorCliente(idCliente);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Remover Cliente
	 * 
	 * @author isilva
	 * @date 15/02/2011
	 * @param ids
	 *            , usuarioLogado
	 * @throws ControladorException
	 */
	public void removerCliente(String[] ids, Usuario usuarioLogado) throws ControladorException{

		try{

			for(int i = 0; i < ids.length; i++){

				// Inicio de Remove os responsáveis
				FiltroClienteResponsavel filtroClienteResponsavel = new FiltroClienteResponsavel();
				filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.CLIENTE_ID, new Integer(ids[i])));

				Collection colResposavel = getControladorUtil().pesquisar(filtroClienteResponsavel, ClienteResponsavel.class.getName());

				if(colResposavel != null && !colResposavel.isEmpty()){

					String[] idsResponsaveis = new String[colResposavel.size()];

					Iterator itResposavel = colResposavel.iterator();

					int j = 0;

					while(itResposavel.hasNext()){
						ClienteResponsavel clienteResponsavel = (ClienteResponsavel) itResposavel.next();
						idsResponsaveis[j++] = clienteResponsavel.getId().toString();
					}

					getControladorCadastro().removerClienteResponsavel(idsResponsaveis, usuarioLogado);
				}
				// Fim de Remove os responsáveis

				// ------------ REGISTRAR TRANSAÇÃO ----------------
				FiltroCliente filtro = new FiltroCliente();
				filtro.adicionarParametro(new ParametroSimples(FiltroCliente.ID, new Integer(ids[i])));

				Cliente clienteRemocao = (Cliente) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtro,
								Cliente.class.getName()));

				if(clienteRemocao == null){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				RegistradorOperacao registradorCliente = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_REMOVER, clienteRemocao.getId(),
								clienteRemocao.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
												UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_CLIENTE_REMOVER);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				clienteRemocao.setOperacaoEfetuada(operacaoEfetuada);

				registradorCliente.registrarOperacao(clienteRemocao);
				// ------------ REGISTRAR TRANSAÇÃO ----------------

				this.getControladorUtil().remover(clienteRemocao);

			}
		}catch(Exception e){
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			throw new ControladorException("atencao.dependencias.existentes");
		}
	}

	/**
	 * [UC0204]
	 * 
	 * @author Hebert Falcão
	 * @date 20/05/2011
	 */
	public Collection<ClienteConta> pesquisarClientesContaPeloImovelEConta(Integer idImovel, Integer idConta) throws ControladorException{

		Collection<ClienteConta> clientesConta = null;

		Collection colecaoPesquisa = null;

		try{
			colecaoPesquisa = this.repositorioCliente.pesquisarClientesContaPeloImovelEConta(idImovel, idConta);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(!Util.isVazioOrNulo(colecaoPesquisa)){
			clientesConta = new ArrayList<ClienteConta>();

			for(Object obj : colecaoPesquisa){
				Object[] arrayObj = (Object[]) obj;

				Cliente cliente = new Cliente();
				ClienteRelacaoTipo clienteRelacaoTipo = null;
				Set clienteFones = null;
				Set clienteImoveis = null;

				if(arrayObj[0] != null){
					cliente.setNome((String) arrayObj[0]);
				}

				if(arrayObj[1] != null){
					cliente.setCpf((String) arrayObj[1]);
				}

				if(arrayObj[2] != null){
					cliente.setCnpj((String) arrayObj[2]);
				}

				if(arrayObj[3] != null){
					clienteRelacaoTipo = new ClienteRelacaoTipo();
					clienteRelacaoTipo.setDescricao((String) arrayObj[3]);
				}

				if(arrayObj[5] != null){
					ClienteFone clienteFone = new ClienteFone();
					clienteFone.setTelefone((String) arrayObj[5]);

					if(arrayObj[4] != null){
						clienteFone.setDdd((String) arrayObj[4]);
					}

					clienteFones = new TreeSet();
					clienteFones.add(clienteFone);
				}

				if(arrayObj[6] != null){
					ClienteImovel clienteImovel = new ClienteImovel();
					clienteImovel.setDataInicioRelacao((Date) arrayObj[6]);

					clienteImoveis = new TreeSet();
					clienteImoveis.add(clienteImovel);
				}

				cliente.setClienteFones(clienteFones);
				cliente.setClienteImoveis(clienteImoveis);

				ClienteConta clienteConta = new ClienteConta();
				clienteConta.setCliente(cliente);
				clienteConta.setClienteRelacaoTipo(clienteRelacaoTipo);

				clientesConta.add(clienteConta);
			}
		}

		return clientesConta;
	}

	/**
	 * Retorna o cliente proprietario apartir do id do imovel
	 * 
	 * @author Hebert Falcão
	 * @date 26/05/2011
	 */
	public Cliente retornarDadosClienteProprietario(Integer idImovel) throws ControladorException{

		Cliente cliente = null;
		Object[] clienteArray = null;

		try{
			clienteArray = this.repositorioClienteImovel.retornarDadosClienteProprietario(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(!Util.isVazioOrNulo(clienteArray)){
			cliente = new Cliente();

			// Id
			if(clienteArray[0] != null){
				cliente.setId((Integer) clienteArray[0]);
			}

			// Nome
			if(clienteArray[1] != null){
				cliente.setNome((String) clienteArray[1]);
			}

			// CPF
			if(clienteArray[2] != null){
				cliente.setCpf((String) clienteArray[2]);
			}

			// CNPJ
			if(clienteArray[3] != null){
				cliente.setCnpj((String) clienteArray[3]);
			}

			// RG
			if(clienteArray[4] != null){
				cliente.setRg((String) clienteArray[4]);
			}

			// Orgão Expedidor
			if(clienteArray[4] != null && clienteArray[5] != null){
				cliente.setRg(cliente.getRg().trim() + "-" + (String) clienteArray[5]);
			}

			// Unidade Federação
			if(clienteArray[4] != null && clienteArray[5] != null && clienteArray[6] != null){
				cliente.setRg(cliente.getRg().trim() + "/" + (String) clienteArray[6]);
			}

			// Cliente Tipo
			if(clienteArray[7] != null){
				ClienteTipo clienteTipo = new ClienteTipo();
				clienteTipo.setIndicadorPessoaFisicaJuridica((Short) clienteArray[7]);
				cliente.setClienteTipo(clienteTipo);
			}

		}

		return cliente;
	}

	/**
	 * Retorna a Coleção de ClienteImovel que será setada no objeto Imovel.
	 * 
	 * @author Ailton Sousa
	 * @date 06/10/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Imovel obterClienteImovelResponsavel(Integer idImovel) throws ControladorException{

		try{
			return repositorioClienteImovel.obterClienteImovelResponsavel(idImovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0004] – Apresenta Relacionamento do Cliente com os Imóveis
	 * 
	 * @author Hugo Lima
	 * @date 10/08/2012
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ClienteImovelRelacaoHelper> obterDadosImoveisClienteRelacao(Integer idCliente) throws ControladorException{

		try{
			return repositorioClienteImovel.obterDadosImoveisClienteRelacao(idCliente);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Ordena uma coleção de clientes a partir do nome de forma alfabética
	 * 
	 * @author Luciano Galvao
	 * @date 28/06/2013
	 */
	public Collection ordernarColecaoClientesPorNome(Collection<Cliente> colecaoCliente){

		List<Cliente> list = new ArrayList<Cliente>();

		if(!Util.isVazioOrNulo(colecaoCliente)){
			list.addAll(colecaoCliente);

			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("nome"));

			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort((List) list, multiSort);

		}

		return list;

	}

	/**
	 * Verifica se o cliente possui algum telefone de um determinado tipo.
	 * 
	 * @param idCliente
	 * @param idFoneTipo
	 * @return
	 * @throws ControladorException
	 * @date 12/09/2013
	 * @author Felipe Rosacruz
	 */
	public boolean verificarClientePossuiFoneTipo(Integer idCliente, Integer idFoneTipo) throws ControladorException{

		FiltroClienteFone filtroClienteFone = new FiltroClienteFone();

		filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, idCliente));

		filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.FONE_TIPO_ID, idFoneTipo));

		Collection<ClienteFone> colecaoClienteFone = getControladorUtil().pesquisar(filtroClienteFone, ClienteFone.class.getName());

		if(colecaoClienteFone.isEmpty()){
			return false;
		}
		return true;
	}

	/**
	 * @param clienteJSONHelper
	 * @throws ControladorException
	 */
	public void inserirCliente(ClienteJSONHelper clienteJSONHelper) throws ControladorException{

		Cliente cliente = new Cliente();
		this.popularClienteComNovasInformacoes(cliente, clienteJSONHelper, ConstantesSistema.ACAO_INSERIR);

	}

	/**
	 * [UC3149] Inserir Atividade Econômica
	 * 
	 * @author Anderson Italo
	 * @date 29/06/2014
	 */
	public Object inserirAtividadeEconomica(AtividadeEconomica atividadeEconomica, Usuario usuario) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		RegistradorOperacao registradorOperacaoAtividadeEconomica = new RegistradorOperacao(Operacao.OPERACAO_ATIVIDADE_ECONOMICA_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoAtividadeEconomica = new Operacao();
		operacaoAtividadeEconomica.setId(Operacao.OPERACAO_ATIVIDADE_ECONOMICA_INSERIR);

		OperacaoEfetuada operacaoEfetuadaAtividadeEconomica = new OperacaoEfetuada();
		operacaoEfetuadaAtividadeEconomica.setOperacao(operacaoAtividadeEconomica);

		atividadeEconomica.setOperacaoEfetuada(operacaoEfetuadaAtividadeEconomica);
		atividadeEconomica.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoAtividadeEconomica.registrarOperacao(atividadeEconomica);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		return this.getControladorUtil().inserir(atividadeEconomica);
	}

	/**
	 * [UC3150] Manter Atividade Econômica
	 * [SB0001] - Atualizar Atividade Econômica
	 * 
	 * @author Anderson Italo
	 * @date 29/06/2014
	 */
	public void atualizarAtividadeEconomica(AtividadeEconomica atividadeEconomica, Usuario usuario) throws ControladorException{

		// [FS0002 – Atualização realizada por outro usuário]
		FiltroAtividadeEconomica filtroAtividadeEconomica = new FiltroAtividadeEconomica();
		filtroAtividadeEconomica.adicionarParametro(new ParametroSimples(FiltroAtividadeEconomica.ID, atividadeEconomica.getId()));

		Collection colecaoRetorno = getControladorUtil().pesquisar(filtroAtividadeEconomica, AtividadeEconomica.class.getName());

		if(colecaoRetorno == null || colecaoRetorno.isEmpty()){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		AtividadeEconomica atividadeEconomicaNaBase = (AtividadeEconomica) Util.retonarObjetoDeColecao(colecaoRetorno);

		// Verificar se já foi atualizado por outro
		// usuário durante esta atualização
		if(atividadeEconomicaNaBase.getUltimaAlteracao().after(atividadeEconomica.getUltimaAlteracao())){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// ------------ REGISTRAR TRANSAÇÃO ---------------------
		RegistradorOperacao registradorAtividadeEconomica = new RegistradorOperacao(Operacao.OPERACAO_ATIVIDADE_ECONOMICA_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATIVIDADE_ECONOMICA_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setArgumentoValor(atividadeEconomica.getId());

		atividadeEconomica.setOperacaoEfetuada(operacaoEfetuada);
		atividadeEconomica.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorAtividadeEconomica.registrarOperacao(atividadeEconomica);

		// ------------ REGISTRAR TRANSAÇÃO --------------------

		getControladorUtil().atualizar(atividadeEconomica);
	}

	/**
	 * [UC3150] Manter Atividade Econômica
	 * [SB0002] - Excluir Atividade Econômica
	 * 
	 * @author Anderson Italo
	 * @date 29/06/2014
	 */
	public void removerAtividadeEconomica(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			FiltroAtividadeEconomica filtro = new FiltroAtividadeEconomica();
			filtro.adicionarParametro(new ParametroSimples(FiltroAtividadeEconomica.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, AtividadeEconomica.class.getName());

			AtividadeEconomica atividadeEconomicaRemocao = (AtividadeEconomica) Util.retonarObjetoDeColecao(colecaoRetorno);

			RegistradorOperacao registradorAtividadeEconomica = new RegistradorOperacao(Operacao.OPERACAO_ATIVIDADE_ECONOMICA_REMOVER,
							new UsuarioAcaoUsuarioHelper(
											usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_ATIVIDADE_ECONOMICA_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			atividadeEconomicaRemocao.setOperacaoEfetuada(operacaoEfetuada);
			operacaoEfetuada.setArgumentoValor(atividadeEconomicaRemocao.getId());

			registradorAtividadeEconomica.registrarOperacao(atividadeEconomicaRemocao);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			this.getControladorUtil().remover(atividadeEconomicaRemocao);

		}
	}


}
