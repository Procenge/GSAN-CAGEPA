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

package gcom.operacional;

import gcom.cadastro.cliente.FiltroClienteResponsavel;
import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.*;
import gcom.operacional.abastecimento.AbastecimentoProgramacao;
import gcom.operacional.abastecimento.FiltroAbastecimentoProgramacao;
import gcom.operacional.abastecimento.FiltroManutencaoProgramacao;
import gcom.operacional.abastecimento.ManutencaoProgramacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.*;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Definição da lógica de negócio do Session Bean de ControladorCliente
 * 
 * @author Leandro Cavalcanti
 * @created 12 de junho de 2006
 */
public class ControladorOperacionalSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

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
	 * Faz o controle de concorrencia de programação de abastecimento
	 * 
	 * @author Rafael Pinto
	 * @date 04/12/2006
	 * @throws ControladorException
	 */
	private void verificarAbastecimentoProgramacaoControleConcorrencia(AbastecimentoProgramacao abastecimentoProgramacao)
					throws ControladorException{

		FiltroAbastecimentoProgramacao filtro = new FiltroAbastecimentoProgramacao();
		filtro.adicionarParametro(new ParametroSimples(FiltroAbastecimentoProgramacao.ID, abastecimentoProgramacao.getId()));

		Collection colecao = getControladorUtil().pesquisar(filtro, AbastecimentoProgramacao.class.getName());

		if(colecao == null || colecao.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		AbastecimentoProgramacao abastecimentoProgramacaoAtual = (AbastecimentoProgramacao) Util.retonarObjetoDeColecao(colecao);

		if(abastecimentoProgramacaoAtual.getUltimaAlteracao().after(abastecimentoProgramacao.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
	}

	/**
	 * Faz o controle de concorrencia da programação da manutenção
	 * 
	 * @author Rafael Pinto
	 * @date 04/12/2006
	 * @throws ControladorException
	 */
	private void verificarManutencaoProgramacaoControleConcorrencia(ManutencaoProgramacao manutencaoProgramacao)
					throws ControladorException{

		FiltroManutencaoProgramacao filtro = new FiltroManutencaoProgramacao();
		filtro.adicionarParametro(new ParametroSimples(FiltroManutencaoProgramacao.ID, manutencaoProgramacao.getId()));

		Collection colecao = getControladorUtil().pesquisar(filtro, ManutencaoProgramacao.class.getName());

		if(colecao == null || colecao.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		ManutencaoProgramacao manutencaoProgramacaoAtual = (ManutencaoProgramacao) Util.retonarObjetoDeColecao(colecao);

		if(manutencaoProgramacaoAtual.getUltimaAlteracao().after(manutencaoProgramacaoAtual.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
	}

	/**
	 * [UC0001] Inserir Distrito Operacional
	 * 
	 * @author Eduardo Bianchi
	 * @date 26/01/2007
	 * @param Distrito
	 *            Operaciona Descrição do parâmetro
	 */
	public Integer inserirDistritoOperacional(String descricao, String descricaoAbreviada, Usuario usuarioLogado, String localidade,
					String descricaoLocalidade, String endereco, String telefone, String ramal, String fax, String email,
					String descricaoTipoInstalacao, String numeroCota, String latitude, String longitude, String sistemaAbastecimento,
					String tipoUnidadeOperacional, LogradouroCep cep, Logradouro logradouro, LogradouroBairro bairro,
					EnderecoReferencia enderecoReferencia, String numeroImovel, String complementoEndereco) throws ControladorException{

		DistritoOperacional distritoOperacional = new DistritoOperacional();
		distritoOperacional.setDescricao(descricao);
		distritoOperacional.setDescricaoAbreviada(descricaoAbreviada);
		if(telefone != null && !telefone.equals("")){
			distritoOperacional.setTelefone(Integer.parseInt(telefone));
		}
		if(ramal != null && !ramal.equals("")){
			distritoOperacional.setRamal(Integer.parseInt(ramal));
		}
		if(fax != null && !fax.equals("")){
			distritoOperacional.setFax(Integer.parseInt(fax));
		}
		distritoOperacional.setEmail(email);
		if(numeroCota != null && !numeroCota.equals("")){
			distritoOperacional.setNumeroCota(Integer.parseInt(numeroCota));
		}
		if(latitude != null && !latitude.equals("")){
			distritoOperacional.setLatitude(Integer.parseInt(latitude));
		}
		if(longitude != null && !longitude.equals("")){
			distritoOperacional.setLongitude(Integer.parseInt(longitude));
		}
		if(numeroImovel != null && !numeroImovel.equals("")){
			distritoOperacional.setNumeroImovel(numeroImovel);
		}
		distritoOperacional.setComplementoEndereco(complementoEndereco);
		distritoOperacional.setBairro(bairro);
		distritoOperacional.setEnderecoReferencia(enderecoReferencia);
		distritoOperacional.setCep(cep);

		TipoUnidadeOperacional tipoUnidadeOperacionalID = new TipoUnidadeOperacional();
		tipoUnidadeOperacionalID.setId(Integer.valueOf(tipoUnidadeOperacional));
		distritoOperacional.setTipoUnidadeOperacional(tipoUnidadeOperacionalID);

		Localidade localidadeId = new Localidade();
		localidadeId.setId(Integer.valueOf(localidade));
		distritoOperacional.setLocalidade(localidadeId);

		SistemaAbastecimento sistemaAbastecimentoId = new SistemaAbastecimento();
		sistemaAbastecimentoId.setId(Integer.valueOf(sistemaAbastecimento));
		distritoOperacional.setSistemaAbastecimento(sistemaAbastecimentoId);

		distritoOperacional.setUltimaAlteracao(new Date());
		distritoOperacional.setIndicadorUso(new Integer(1).shortValue());

		FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
		filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.DESCRICAO, descricao));

		Collection colecaoDistritoOperacional = getControladorUtil().pesquisar(filtroDistritoOperacional,
						DistritoOperacional.class.getName());

		Integer idDistritoOperacional = null;

		if(colecaoDistritoOperacional.isEmpty()){
			idDistritoOperacional = (Integer) getControladorUtil().inserir(distritoOperacional);
		}else{
			throw new ControladorException("atencao.distrito_operacional_existente");
		}

		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_DISTRITO_OPERACIONAL_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_DISTRITO_OPERACIONAL_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		distritoOperacional.setOperacaoEfetuada(operacaoEfetuada);
		distritoOperacional.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(distritoOperacional);

		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		return idDistritoOperacional;
	}
	
	public static Date converterStringParaHoraMinutoData(String horaMinuto){

		Date retorno = null;

		// Obtém a hora
		String hora = horaMinuto.substring(0, 2);
		// Obtém os minutos
		String minuto = horaMinuto.substring(3, 5);

		// obtém a data mínima do mês selecionado
		Calendar data = Calendar.getInstance();

		// Seta como data atual
		data.setTime(new Date());

		// Seta a hora
		data.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hora).intValue());
		data.set(Calendar.MINUTE, Integer.valueOf(minuto).intValue());
		data.set(Calendar.SECOND, 0);
		data.set(Calendar.MILLISECOND, 0);

		retorno = data.getTime();

		return retorno;
	}

	/**
	 * [UC0414] - Informar Programação de Abastecimento e Manutenção
	 * [SB0006] - Atualizar Programação de Abastecimento na Base de Dados
	 * [SB0007] - Atualizar Programação de Manutenção na Base de Dados
	 * 
	 * @author Rafael Pinto
	 * @date 09/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarProgramacaoAbastecimentoManutencao(Collection colecaoProgramacaoAbastecimento,
					Collection colecaoProgramacaoAbastecimentoRemovidas, Collection colecaoProgramacaoManutencao,
					Collection colecaoProgramacaoManutencaoRemovidas, Usuario usuario) throws ControladorException{

		// [UC0107] - Registrar Transação
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_PROGRAMACAO_ABASTECIMENTO_MANUTENCAO_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_PROGRAMACAO_ABASTECIMENTO_MANUTENCAO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		Iterator itera = null;

		// [SB0006] - Atualizar Programação de Abastecimento na Base de Dados
		if(colecaoProgramacaoAbastecimento != null && !colecaoProgramacaoAbastecimento.isEmpty()){

			itera = colecaoProgramacaoAbastecimento.iterator();

			while(itera.hasNext()){

				AbastecimentoProgramacao abastecimentoProgramacao = (AbastecimentoProgramacao) itera.next();

				// Se existir id que dizer que existe esse objeto na base,
				// então verifica o controle de concorrencia
				if(abastecimentoProgramacao.getId() != null
								&& abastecimentoProgramacao.getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

					this.verificarAbastecimentoProgramacaoControleConcorrencia(abastecimentoProgramacao);
				}

				abastecimentoProgramacao.setUltimaAlteracao(new Date());

				// [UC0107] - Registrar Transação
				abastecimentoProgramacao.setOperacaoEfetuada(operacaoEfetuada);
				abastecimentoProgramacao.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(abastecimentoProgramacao);

				this.getControladorUtil().inserirOuAtualizar(abastecimentoProgramacao);
			}
		}

		if(colecaoProgramacaoAbastecimentoRemovidas != null && !colecaoProgramacaoAbastecimentoRemovidas.isEmpty()){

			Iterator iter = colecaoProgramacaoAbastecimentoRemovidas.iterator();

			while(iter.hasNext()){

				AbastecimentoProgramacao abastecimentoProgramacao = (AbastecimentoProgramacao) iter.next();

				// Se existir id que dizer que existe esse objeto na base,
				// então verifica o controle de concorrencia
				if(abastecimentoProgramacao.getId() != null
								&& abastecimentoProgramacao.getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

					this.verificarAbastecimentoProgramacaoControleConcorrencia(abastecimentoProgramacao);
				}

				abastecimentoProgramacao.setOperacaoEfetuada(operacaoEfetuada);
				abastecimentoProgramacao.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(abastecimentoProgramacao);

				this.getControladorUtil().remover(abastecimentoProgramacao);
			}
		}

		if(colecaoProgramacaoManutencao != null && !colecaoProgramacaoManutencao.isEmpty()){
			itera = colecaoProgramacaoManutencao.iterator();

			// [SB0007] - Atualizar Programação de Manutenção na Base de Dados
			while(itera.hasNext()){

				ManutencaoProgramacao manutencaoProgramacao = (ManutencaoProgramacao) itera.next();

				// Se existir id que dizer que existe esse objeto na base,
				// então verifica o controle de concorrencia
				if(manutencaoProgramacao.getId() != null
								&& manutencaoProgramacao.getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

					this.verificarManutencaoProgramacaoControleConcorrencia(manutencaoProgramacao);
				}
				
				manutencaoProgramacao.setUltimaAlteracao(new Date());

				// [UC0107] - Registrar Transação
				manutencaoProgramacao.setOperacaoEfetuada(operacaoEfetuada);
				manutencaoProgramacao.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(manutencaoProgramacao);

				this.getControladorUtil().inserirOuAtualizar(manutencaoProgramacao);
			}
		}

		if(colecaoProgramacaoManutencaoRemovidas != null && !colecaoProgramacaoManutencaoRemovidas.isEmpty()){

			Iterator iter = colecaoProgramacaoManutencaoRemovidas.iterator();

			while(iter.hasNext()){

				ManutencaoProgramacao manutencaoProgramacao = (ManutencaoProgramacao) iter.next();

				// Se existir id que dizer que existe esse objeto na base,
				// então verifica o controle de concorrencia
				if(manutencaoProgramacao.getId() != null
								&& manutencaoProgramacao.getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

					this.verificarManutencaoProgramacaoControleConcorrencia(manutencaoProgramacao);
				}

				manutencaoProgramacao.setOperacaoEfetuada(operacaoEfetuada);
				manutencaoProgramacao.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(manutencaoProgramacao);

				this.getControladorUtil().remover(manutencaoProgramacao);
			}
		}

	}

	/**
	 * [UC0522] MANTER DISTRITO OPERACIONAL
	 * Remover Distrito Operacional
	 * 
	 * @author Eduardo Bianchi
	 * @date 05/02/2007
	 * @pparam distritoOperacional
	 * @throws ControladorException
	 */
	public void removerDistritoOperacional(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_MUNICIPIO_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		// [FS0003]Municipiopossui vinculos no sistema
		this.getControladorUtil().remover(ids, DistritoOperacional.class.getName(), null, null);
	}

	/**
	 * [UC0522] Manter Distrito Operacional [SB0001]Atualizar Municipio
	 * 
	 * @author Eduardo Bianchi
	 * @date 09/02/2007
	 * @pparam distritoOperacional
	 * @throws ControladorException
	 */
	public void atualizarDistritoOperacional(DistritoOperacional distritoOperacional, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_DISTRITO_OPERACIONAL_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_DISTRITO_OPERACIONAL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		distritoOperacional.setOperacaoEfetuada(operacaoEfetuada);
		distritoOperacional.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(distritoOperacional);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
		// Seta o filtro para buscar o Distrito Operacional na base
		filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, distritoOperacional.getId()));

		// Procura o Distrito Operacional na base
		Collection distritosOperacionaisAtualizados = getControladorUtil().pesquisar(filtroDistritoOperacional,
						DistritoOperacional.class.getName());

		DistritoOperacional distritoOperacionalNaBase = (DistritoOperacional) Util.retonarObjetoDeColecao(distritosOperacionaisAtualizados);

		if(distritoOperacionalNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o distrito Operacional já foi atualizado por outro usuário
		// durante esta atualização

		if(distritoOperacionalNaBase.getUltimaAlteracao().after(distritoOperacional.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
		// Recupera todos os dados do distrito operacional
		FiltroDadoDistritoOperacional filtroDadoDistritoOperacional = new FiltroDadoDistritoOperacional();
		filtroDadoDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDadoDistritoOperacional.DISTRITO_OPERACIONAL_ID,
						distritoOperacional.getId()));
		HashSet<DadoDistritoOperacional> dadosDistritoOperacional = new HashSet<DadoDistritoOperacional>(getControladorUtil().pesquisar(
						filtroDadoDistritoOperacional, DadoDistritoOperacional.class.getName()));
		// se estiver diferente de vazio percorre
		if(dadosDistritoOperacional != null && !dadosDistritoOperacional.isEmpty()){
			for(DadoDistritoOperacional dadoDistritoOperacional : dadosDistritoOperacional){
				// Se lista atual ñ contem um elemento da lista do banco, remove
				if(!distritoOperacional.getDadosDistritoOperacional().contains(dadoDistritoOperacional)){
					getControladorUtil().remover(dadoDistritoOperacional);
				}
			}

		}
		// Se a lista atual ñ estiver vazia, percorre
		if(!distritoOperacional.getDadosDistritoOperacional().isEmpty()){
			for(DadoDistritoOperacional dadoDistritoOperacional : distritoOperacional.getDadosDistritoOperacional()){
				// Se a lista do banco ontem um elemento da lista do atual, atualiza
				if(dadosDistritoOperacional.contains(dadoDistritoOperacional) && dadoDistritoOperacional.getId() != null){
					getControladorUtil().atualizar(dadoDistritoOperacional);
				}else{
					getControladorUtil().inserir(dadoDistritoOperacional);
				}
			}
		}

		distritoOperacional.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(distritoOperacional);

	}

	/**
	 * [UC0524] Inserir Sistema de Esgoto
	 * 
	 * @author Kassia Albuquerque
	 * @date 09/03/2007
	 */

	public Integer inserirSistemaEsgoto(SistemaEsgoto sistemaEsgoto, Usuario usuarioLogado) throws ControladorException{

		// [FS0003] - Verificando a existência do Sistema de Esgoto
		FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();

		filtroSistemaEsgoto.adicionarParametro(new ComparacaoTextoCompleto(FiltroSistemaEsgoto.DESCRICAO, sistemaEsgoto.getDescricao()));

		Collection colecaoSistemaEsgoto = getControladorUtil().pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

		if(colecaoSistemaEsgoto != null && !colecaoSistemaEsgoto.isEmpty()){
			throw new ControladorException("atencao.divisao_esgoto.existente", null, sistemaEsgoto.getDescricao());
		}

		sistemaEsgoto.setUltimaAlteracao(new Date());

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_SISTEMA_ESGOTO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_SISTEMA_ESGOTO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		sistemaEsgoto.setOperacaoEfetuada(operacaoEfetuada);
		sistemaEsgoto.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(sistemaEsgoto);
		// Fim - Registrando as transações

		Integer idSistemaEsgoto = (Integer) getControladorUtil().inserir(sistemaEsgoto);
		sistemaEsgoto.setId(idSistemaEsgoto);

		return idSistemaEsgoto;
	}

	/**
	 * [UC0525] Manter Sistema Esgoto [SB0001]Atualizar Sistema Esgoto
	 * 
	 * @author Kassia Albuquerque
	 * @date 19/03/2007
	 */

	public void atualizarSistemaEsgoto(SistemaEsgoto sistemaEsgoto, Usuario usuarioLogado) throws ControladorException{

		// [FS0002] - Atualização realizada por outro usuário

		FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();
		// Seta o filtro para buscar o sistema de esgoto na base
		filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.ID, sistemaEsgoto.getId()));

		// Procura sistema de esgoto na base
		Collection sistemaEsgotoAtualizados = getControladorUtil().pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

		SistemaEsgoto sistemaEsgotoNaBase = (SistemaEsgoto) Util.retonarObjetoDeColecao(sistemaEsgotoAtualizados);

		if(sistemaEsgotoNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o sistema de esgoto já foi atualizado por outro usuário
		// durante esta atualização

		if(sistemaEsgotoNaBase.getUltimaAlteracao().after(sistemaEsgoto.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		sistemaEsgoto.setUltimaAlteracao(new Date());

		// [UC] - Registrar Transação
		// Início - Registrando as transações
		RegistradorOperacao registradorSistemaEsgoto = new RegistradorOperacao(Operacao.OPERACAO_SISTEMA_ESGOTO_ATUALIZAR, sistemaEsgoto
						.getId(), sistemaEsgoto.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_SISTEMA_ESGOTO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		sistemaEsgoto.setOperacaoEfetuada(operacaoEfetuada);
		sistemaEsgoto.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorSistemaEsgoto.registrarOperacao(sistemaEsgoto);
		// Fim - Registrando as transações

		// Atualiza o objeto na base
		getControladorUtil().atualizar(sistemaEsgoto);

	}

	/**
	 * [UC0525] Manter Sistema Esgoto [SB0002]Remover Sistema Esgoto
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/03/2007
	 */
	public void removerSistemaEsgoto(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			FiltroSistemaEsgoto filtro = new FiltroSistemaEsgoto();
			filtro.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, SistemaEsgoto.class.getName());

			SistemaEsgoto sistemaEsgoto = (SistemaEsgoto) Util.retonarObjetoDeColecao(colecaoRetorno);

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorSistemaEsgoto = new RegistradorOperacao(Operacao.OPERACAO_SISTEMA_ESGOTO_REMOVER, sistemaEsgoto
							.getId(), sistemaEsgoto.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
							UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_SISTEMA_ESGOTO_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			sistemaEsgoto.setOperacaoEfetuada(operacaoEfetuada);

			registradorSistemaEsgoto.registrarOperacao(sistemaEsgoto);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			// [FS0003]Sistema de Esgoto possui vinculos no sistema
			this.getControladorUtil().remover(sistemaEsgoto);
		}
	}

	/**
	 * [UC0081] Manter Marca Hidrometro
	 * 
	 * @author Bruno Barros
	 * @date 03/07/2007
	 */

	public void removerHidrometroMarca(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_MARCA_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		// [FS0003]Sistema de Esgoto possui vinculos no sistema
		this.getControladorUtil().remover(ids, HidrometroMarca.class.getName(), operacaoEfetuada, colecaoUsuarios);

	}

	/**
	 * [UC0081] Manter Hidrometro Marca
	 * 
	 * @author Bruno Barros
	 * @date 04/07/2007
	 */
	public void atualizarHidrometroMarca(HidrometroMarca hidrometroMarca, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_MARCA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_MARCA_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		hidrometroMarca.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroMarca.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroMarca);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();
		// Seta o filtro para buscar a marca de hidrometro na base
		filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.ID, hidrometroMarca.getId()));

		// Procura sistema de esgoto na base
		Collection hidrometromMarcaAtualizados = getControladorUtil().pesquisar(filtroHidrometroMarca, HidrometroMarca.class.getName());

		HidrometroMarca hidrometromMarcaNaBase = (HidrometroMarca) Util.retonarObjetoDeColecao(hidrometromMarcaAtualizados);

		if(hidrometromMarcaNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}


		// Verificar se o sistema de esgoto já foi atualizado por outro usuário
		// durante esta atualização

		if(hidrometromMarcaNaBase.getUltimaAlteracao().after(hidrometroMarca.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		filtroHidrometroMarca = new FiltroHidrometroMarca();
		filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.DESCRICAO, hidrometroMarca.getDescricao()));
		filtroHidrometroMarca.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroHidrometroMarca.ID, hidrometroMarca.getId()));

		Collection<HidrometroMarca> colecaoHidrometroMarca = this.getControladorUtil().pesquisar(filtroHidrometroMarca,
						HidrometroMarca.class.getName());

		if(!Util.isVazioOrNulo(colecaoHidrometroMarca)){
			throw new ControladorException("atencao.hidrometro_marca.existente");
		}

		hidrometroMarca.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(hidrometroMarca);

	}

	/**
	 * [UC] Manter Diametro Hidrometro
	 * 
	 * @author Deyverson Santos
	 * @date 18/12/2008
	 */

	public void removerHidrometroDiametro(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_DIAMETRO_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_DIAMETRO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		Collection<HidrometroDiametro> colecaoHidrometroDiametro = new ArrayList();
		// this.getControladorUtil().remover(ids, HidrometroDiametro.class.getName(),
		// operacaoEfetuada, colecaoUsuarios);

		for(String id : ids){
			// Seta o filtro para buscar a marca de hidrometro na base
			FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();
			filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.ID, id));

			// Procura sistema de esgoto na base
			Collection hidrometromDiametroAtualizados = getControladorUtil().pesquisar(filtroHidrometroDiametro,
							HidrometroDiametro.class.getName());

			HidrometroDiametro hidrometromDiametro = (HidrometroDiametro) Util.retonarObjetoDeColecao(hidrometromDiametroAtualizados);

			if(hidrometromDiametro == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			hidrometromDiametro.setUltimaAlteracao(new Date());
			hidrometromDiametro.setIndicadorUso(new Short("2"));
			// ------------------------ REGISTRO OPERACAO
			hidrometromDiametro.setOperacaoEfetuada(operacaoEfetuada);
			hidrometromDiametro.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(hidrometromDiametro);
			// ------------------------ REGISTRO OPERACAO
			// atualiza cada objeto
			getControladorUtil().atualizar(hidrometromDiametro);
		}
	}

	/**
	 * [UC] Manter Hidrometro Diametro
	 * 
	 * @author Deyverson Santos
	 * @date 15/12/2008
	 */
	public void atualizarHidrometroDiametro(HidrometroDiametro hidrometroDiametro, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_DIAMETRO_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_DIAMETRO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		hidrometroDiametro.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroDiametro.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroDiametro);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();
		// Seta o filtro para buscar a marca de hidrometro na base
		filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.ID, hidrometroDiametro.getId()));

		// Procura sistema de esgoto na base
		Collection hidrometromDiametroAtualizados = getControladorUtil().pesquisar(filtroHidrometroDiametro,
						HidrometroDiametro.class.getName());

		HidrometroDiametro hidrometromDiametroNaBase = (HidrometroDiametro) Util.retonarObjetoDeColecao(hidrometromDiametroAtualizados);

		if(hidrometromDiametroNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}


		if(hidrometromDiametroNaBase.getUltimaAlteracao().after(hidrometroDiametro.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		hidrometroDiametro.setUltimaAlteracao(new Date());
		
		filtroHidrometroDiametro = new FiltroHidrometroDiametro();
		filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroMotivoBaixa.DESCRICAO, hidrometroDiametro
						.getDescricao()));
		try{
			Collection<HidrometroDiametro> hidrometroDiametros = RepositorioUtilHBM.getInstancia().pesquisar(
							filtroHidrometroDiametro, HidrometroDiametro.class.getName());
			HidrometroDiametro hidrometroDiametroMesmaDescricao = (HidrometroDiametro) Util
							.retonarObjetoDeColecao(hidrometroDiametros);

			if(hidrometroDiametroMesmaDescricao != null
							&& !hidrometroDiametroMesmaDescricao.getDescricao().equalsIgnoreCase(hidrometromDiametroNaBase.getDescricao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.hidrometro_diametro.existente");
			}
		}catch(ErroRepositorioException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// Atualiza o objeto na base
		getControladorUtil().atualizar(hidrometroDiametro);

	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 *       Insere o Tipo do hidrômetro
	 * @param hidrometroTipo
	 *            Tipo do hidrometro a ser inserido
	 * @return código do Tipo que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroTipo(HidrometroTipo hidrometroTipo, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_TIPO_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_TIPO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		FiltroHidrometroTipo filtro = new FiltroHidrometroTipo();
		filtro.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.DESCRICAO, hidrometroTipo.getDescricao()));

		Collection colFiltros = getControladorUtil().pesquisar(filtro, hidrometroTipo.getClass().getName());
		if(colFiltros != null && colFiltros.size() > 0){
			throw new ControladorException("atencao.hidrometro_tipo.existente");
		}

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		hidrometroTipo.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroTipo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroTipo);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		return (Integer) this.getControladorUtil().inserir(hidrometroTipo);
	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */

	public void removerHidrometroTipo(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_TIPO_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_TIPO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		Collection<HidrometroTipo> colecaoHidrometroTipo = new ArrayList();

		for(String id : ids){

			FiltroHidrometroTipo filtroHidrometroTipo = new FiltroHidrometroTipo();
			filtroHidrometroTipo.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.ID, id));

			Collection hidrometroTipoAtualizados = getControladorUtil().pesquisar(filtroHidrometroTipo, HidrometroTipo.class.getName());

			HidrometroTipo hidrometroTipo = (HidrometroTipo) Util.retonarObjetoDeColecao(hidrometroTipoAtualizados);

			if(hidrometroTipo == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			hidrometroTipo.setUltimaAlteracao(new Date());
			hidrometroTipo.setIndicadorUso(new Short("2"));
			// ------------------------ REGISTRO OPERACAO
			hidrometroTipo.setOperacaoEfetuada(operacaoEfetuada);
			hidrometroTipo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(hidrometroTipo);
			// ------------------------ REGISTRO OPERACAO
			// atualiza cada objeto
			getControladorUtil().atualizar(hidrometroTipo);
		}
	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void atualizarHidrometroTipo(HidrometroTipo hidrometroTipo, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_TIPO_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_TIPO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		hidrometroTipo.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroTipo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroTipo);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroHidrometroTipo filtroHidrometroTipo = new FiltroHidrometroTipo();

		filtroHidrometroTipo.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.ID, hidrometroTipo.getId()));

		Collection hidrometroTipoAtualizados = getControladorUtil().pesquisar(filtroHidrometroTipo, HidrometroTipo.class.getName());

		HidrometroTipo hidrometroTipoNaBase = (HidrometroTipo) Util.retonarObjetoDeColecao(hidrometroTipoAtualizados);

		if(hidrometroTipoNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		if(hidrometroTipoNaBase.getUltimaAlteracao().after(hidrometroTipo.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		filtroHidrometroTipo = new FiltroHidrometroTipo();
		filtroHidrometroTipo.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.DESCRICAO, hidrometroTipo.getDescricao()));
		filtroHidrometroTipo.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroHidrometroTipo.ID, hidrometroTipo.getId()));

		Collection<HidrometroTipo> colecaoHidrometroTipo = this.getControladorUtil().pesquisar(filtroHidrometroTipo,
						HidrometroTipo.class.getName());

		if(!Util.isVazioOrNulo(colecaoHidrometroTipo)){
			throw new ControladorException("atencao.hidrometro_tipo.existente");
		}

		hidrometroTipo.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(hidrometroTipo);

	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 *       Insere o Motivo de Baixa do hidrômetro
	 * @param hidrometroMotivoBaixa
	 *            Motivo de Baixa do hidrometro a ser inserido
	 * @return código do Motivo de Baixa que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroMotivoBaixa(HidrometroMotivoBaixa hidrometroMotivoBaixa, Usuario usuarioLogado)
					throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_MOTIVO_BAIXA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_MOTIVO_BAIXA_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		FiltroHidrometroMotivoBaixa filtro = new FiltroHidrometroMotivoBaixa();
		filtro.adicionarParametro(new ParametroSimples(FiltroHidrometroMotivoBaixa.DESCRICAO, hidrometroMotivoBaixa.getDescricao()));

		Collection colFiltros = getControladorUtil().pesquisar(filtro, hidrometroMotivoBaixa.getClass().getName());
		if(colFiltros != null && colFiltros.size() > 0){
			throw new ControladorException("atencao.hidrometro_motivo_baixa.existente");
		}

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		hidrometroMotivoBaixa.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroMotivoBaixa.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroMotivoBaixa);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		return (Integer) this.getControladorUtil().inserir(hidrometroMotivoBaixa);
	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */

	public void removerHidrometroMotivoBaixa(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_MOTIVO_BAIXA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_MOTIVO_BAIXA_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		Collection<HidrometroMotivoBaixa> colecaoHidrometroMotivoBaixa = new ArrayList();

		for(String id : ids){

			FiltroHidrometroMotivoBaixa filtroHidrometroMotivoBaixa = new FiltroHidrometroMotivoBaixa();
			filtroHidrometroMotivoBaixa.adicionarParametro(new ParametroSimples(FiltroHidrometroMotivoBaixa.ID, id));

			Collection hidrometroMotivoBaixaAtualizados = getControladorUtil().pesquisar(filtroHidrometroMotivoBaixa,
							HidrometroMotivoBaixa.class.getName());

			HidrometroMotivoBaixa hidrometroMotivoBaixa = (HidrometroMotivoBaixa) Util
							.retonarObjetoDeColecao(hidrometroMotivoBaixaAtualizados);

			if(hidrometroMotivoBaixa == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			hidrometroMotivoBaixa.setUltimaAlteracao(new Date());
			hidrometroMotivoBaixa.setIndicadorUso(new Short("2"));
			// ------------------------ REGISTRO OPERACAO
			hidrometroMotivoBaixa.setOperacaoEfetuada(operacaoEfetuada);
			hidrometroMotivoBaixa.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(hidrometroMotivoBaixa);
			// ------------------------ REGISTRO OPERACAO
			// atualiza cada objeto
			getControladorUtil().atualizar(hidrometroMotivoBaixa);
		}
	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void atualizarHidrometroMotivoBaixa(HidrometroMotivoBaixa hidrometroMotivoBaixa, Usuario usuarioLogado)
					throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_MOTIVO_BAIXA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_MOTIVO_BAIXA_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		hidrometroMotivoBaixa.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroMotivoBaixa.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroMotivoBaixa);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroHidrometroMotivoBaixa filtroHidrometroMotivoBaixa = new FiltroHidrometroMotivoBaixa();

		filtroHidrometroMotivoBaixa.adicionarParametro(new ParametroSimples(FiltroHidrometroMotivoBaixa.ID, hidrometroMotivoBaixa.getId()));

		Collection hidrometroMotivoBaixaAtualizados = getControladorUtil().pesquisar(filtroHidrometroMotivoBaixa,
						HidrometroMotivoBaixa.class.getName());

		HidrometroMotivoBaixa hidrometroMotivoBaixaNaBase = (HidrometroMotivoBaixa) Util
						.retonarObjetoDeColecao(hidrometroMotivoBaixaAtualizados);

		if(hidrometroMotivoBaixaNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		if(hidrometroMotivoBaixaNaBase.getUltimaAlteracao().after(hidrometroMotivoBaixa.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		hidrometroMotivoBaixa.setUltimaAlteracao(new Date());

		filtroHidrometroMotivoBaixa = new FiltroHidrometroMotivoBaixa();
		filtroHidrometroMotivoBaixa.adicionarParametro(new ParametroSimples(FiltroHidrometroMotivoBaixa.DESCRICAO, hidrometroMotivoBaixa
						.getDescricao()));
		try{
			Collection<HidrometroMotivoBaixa> hidrometroMotivoBaixas = RepositorioUtilHBM.getInstancia().pesquisar(
							filtroHidrometroMotivoBaixa, HidrometroMotivoBaixa.class.getName());
			HidrometroMotivoBaixa hidrometroMotivoBaixaMesmaDescricao = (HidrometroMotivoBaixa) Util
							.retonarObjetoDeColecao(hidrometroMotivoBaixas);

			if(hidrometroMotivoBaixaMesmaDescricao != null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.hidrometro_motivo_baixa.existente");
			}

		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Atualiza o objeto na base
		getControladorUtil().atualizar(hidrometroMotivoBaixa);

	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 *       Insere o Classe Metrológica do hidrômetro
	 * @param hidrometroClasseMetrologica
	 *            Classe Metrológica do hidrometro a ser inserido
	 * @return código do Classe Metrológica que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroClasseMetrologica(HidrometroClasseMetrologica hidrometroClasseMetrologica, Usuario usuarioLogado)
					throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_CLASSE_METROLOGICA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_CLASSE_METROLOGICA_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		FiltroHidrometroClasseMetrologica filtro = new FiltroHidrometroClasseMetrologica();
		filtro.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.DESCRICAO, hidrometroClasseMetrologica
						.getDescricao()));

		Collection colFiltros = getControladorUtil().pesquisar(filtro, hidrometroClasseMetrologica.getClass().getName());
		if(colFiltros != null && colFiltros.size() > 0){
			throw new ControladorException("atencao.hidrometro_classe_metrologica.existente");
		}

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		hidrometroClasseMetrologica.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroClasseMetrologica.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroClasseMetrologica);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		return (Integer) this.getControladorUtil().inserir(hidrometroClasseMetrologica);
	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */

	public void removerHidrometroClasseMetrologica(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_CLASSE_METROLOGICA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_CLASSE_METROLOGICA_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		Collection<HidrometroClasseMetrologica> colecaoHidrometroClasseMetrologica = new ArrayList();

		for(String id : ids){

			FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();
			filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.ID, id));

			Collection hidrometroClasseMetrologicaAtualizados = getControladorUtil().pesquisar(filtroHidrometroClasseMetrologica,
							HidrometroClasseMetrologica.class.getName());

			HidrometroClasseMetrologica hidrometroClasseMetrologica = (HidrometroClasseMetrologica) Util
							.retonarObjetoDeColecao(hidrometroClasseMetrologicaAtualizados);

			if(hidrometroClasseMetrologica == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			hidrometroClasseMetrologica.setUltimaAlteracao(new Date());
			hidrometroClasseMetrologica.setIndicadorUso(new Short("2"));
			// ------------------------ REGISTRO OPERACAO
			hidrometroClasseMetrologica.setOperacaoEfetuada(operacaoEfetuada);
			hidrometroClasseMetrologica.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(hidrometroClasseMetrologica);
			// ------------------------ REGISTRO OPERACAO
			// atualiza cada objeto
			getControladorUtil().atualizar(hidrometroClasseMetrologica);
		}
	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void atualizarHidrometroClasseMetrologica(HidrometroClasseMetrologica hidrometroClasseMetrologica, Usuario usuarioLogado)
					throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_CLASSE_METROLOGICA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_CLASSE_METROLOGICA_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		hidrometroClasseMetrologica.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroClasseMetrologica.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroClasseMetrologica);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();

		filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.ID,
						hidrometroClasseMetrologica.getId()));

		Collection hidrometroClasseMetrologicaAtualizados = getControladorUtil().pesquisar(filtroHidrometroClasseMetrologica,
						HidrometroClasseMetrologica.class.getName());

		HidrometroClasseMetrologica hidrometroClasseMetrologicaNaBase = (HidrometroClasseMetrologica) Util
						.retonarObjetoDeColecao(hidrometroClasseMetrologicaAtualizados);

		if(hidrometroClasseMetrologicaNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		if(hidrometroClasseMetrologicaNaBase.getUltimaAlteracao().after(hidrometroClasseMetrologica.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();
		filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.DESCRICAO,
						hidrometroClasseMetrologica.getDescricao()));
		filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroHidrometroClasseMetrologica.ID,
						hidrometroClasseMetrologica.getId()));

		Collection<HidrometroClasseMetrologica> colecaoHidrometroClasseMetrologica = this.getControladorUtil().pesquisar(
						filtroHidrometroClasseMetrologica, HidrometroClasseMetrologica.class.getName());

		if(!Util.isVazioOrNulo(colecaoHidrometroClasseMetrologica)){
			throw new ControladorException("atencao.hidrometro_classe_metrologica.existente");
		}

		hidrometroClasseMetrologica.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(hidrometroClasseMetrologica);

	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 *       Insere o Local de Armazenagem do hidrômetro
	 * @param hidrometroLocalArmazenagem
	 *            Local de Armazenagem do hidrometro a ser inserido
	 * @return código do Local de Armazenagem que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroLocalArmazenagem(HidrometroLocalArmazenagem hidrometroLocalArmazenagem, Usuario usuarioLogado)
					throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_LOCAL_ARMAZENAGEM_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_LOCAL_ARMAZENAGEM_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		FiltroHidrometroLocalArmazenagem filtro = new FiltroHidrometroLocalArmazenagem();
		filtro.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.DESCRICAO, hidrometroLocalArmazenagem
						.getDescricao()));

		Collection colFiltros = getControladorUtil().pesquisar(filtro, hidrometroLocalArmazenagem.getClass().getName());
		if(colFiltros != null && colFiltros.size() > 0){
			throw new ControladorException("atencao.hidrometro_local_armazenagem.existente");
		}

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		hidrometroLocalArmazenagem.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroLocalArmazenagem.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroLocalArmazenagem);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		return (Integer) this.getControladorUtil().inserir(hidrometroLocalArmazenagem);
	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */

	public void removerHidrometroLocalArmazenagem(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_LOCAL_ARMAZENAGEM_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_LOCAL_ARMAZENAGEM_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		Collection<HidrometroLocalArmazenagem> colecaoHidrometroLocalArmazenagem = new ArrayList();

		for(String id : ids){

			FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();
			filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.ID, id));

			Collection hidrometroLocalArmazenagemAtualizados = getControladorUtil().pesquisar(filtroHidrometroLocalArmazenagem,
							HidrometroLocalArmazenagem.class.getName());

			HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) Util
							.retonarObjetoDeColecao(hidrometroLocalArmazenagemAtualizados);

			if(hidrometroLocalArmazenagem == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			hidrometroLocalArmazenagem.setUltimaAlteracao(new Date());
			hidrometroLocalArmazenagem.setIndicadorUso(new Short("2"));
			// ------------------------ REGISTRO OPERACAO
			hidrometroLocalArmazenagem.setOperacaoEfetuada(operacaoEfetuada);
			hidrometroLocalArmazenagem.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(hidrometroLocalArmazenagem);
			// ------------------------ REGISTRO OPERACAO
			// atualiza cada objeto
			getControladorUtil().atualizar(hidrometroLocalArmazenagem);
		}
	}

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void atualizarHidrometroLocalArmazenagem(HidrometroLocalArmazenagem hidrometroLocalArmazenagem, Usuario usuarioLogado)
					throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_LOCAL_ARMAZENAGEM_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_LOCAL_ARMAZENAGEM_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		hidrometroLocalArmazenagem.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroLocalArmazenagem.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroLocalArmazenagem);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();

		filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.ID,
						hidrometroLocalArmazenagem.getId()));

		Collection hidrometroLocalArmazenagemAtualizados = getControladorUtil().pesquisar(filtroHidrometroLocalArmazenagem,
						HidrometroLocalArmazenagem.class.getName());

		HidrometroLocalArmazenagem hidrometroLocalArmazenagemNaBase = (HidrometroLocalArmazenagem) Util
						.retonarObjetoDeColecao(hidrometroLocalArmazenagemAtualizados);

		if(hidrometroLocalArmazenagemNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		if(hidrometroLocalArmazenagemNaBase.getUltimaAlteracao().after(hidrometroLocalArmazenagem.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		hidrometroLocalArmazenagem.setUltimaAlteracao(new Date());

		filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();
		filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.DESCRICAO,
						hidrometroLocalArmazenagem.getDescricao()));
		try{
			Collection<HidrometroLocalArmazenagem> colecaoHidrometroLocalArmazenagem = RepositorioUtilHBM.getInstancia().pesquisar(
							filtroHidrometroLocalArmazenagem, HidrometroLocalArmazenagem.class.getName());
			HidrometroLocalArmazenagem hidrometroLocalArmazenagemMesmaDescricao = (HidrometroLocalArmazenagem) Util
							.retonarObjetoDeColecao(colecaoHidrometroLocalArmazenagem);

			if(hidrometroLocalArmazenagemMesmaDescricao != null
							&& !hidrometroLocalArmazenagemMesmaDescricao.getDescricao().equalsIgnoreCase(
											hidrometroLocalArmazenagemNaBase.getDescricao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.hidrometro_local_armazenagem.existente");
			}
		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Atualiza o objeto na base
		getControladorUtil().atualizar(hidrometroLocalArmazenagem);

	}

	/**
	 * Permite inserir um Subsistema de Esgoto
	 * [UCXXXX] Inserir Subsistema de Esgoto
	 * 
	 * @author Ailton Sousa
	 * @date 26/01/2011
	 */
	public int inserirSubsistemaEsgoto(SubsistemaEsgoto subsistemaEsgoto, Usuario usuarioLogado) throws ControladorException{

		validarSubsistemaEsgoto(subsistemaEsgoto, usuarioLogado);

		// [FS0003] - Verificando a existência do Subsistema de Esgoto
		FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();

		filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.CODIGO, subsistemaEsgoto.getCodigo()));
		filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID, subsistemaEsgoto
						.getSistemaEsgoto().getId()));

		Collection colecaoSubsistemaEsgoto = getControladorUtil().pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

		if(colecaoSubsistemaEsgoto != null && !colecaoSubsistemaEsgoto.isEmpty()){
			throw new ControladorException("atencao.entidade.ja.cadastrada", null, "SubSistema de Esgoto");
		}

		subsistemaEsgoto.setUltimaAlteracao(new Date());

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_SUBSISTEMA_ESGOTO, subsistemaEsgoto
						.getCodigo(), subsistemaEsgoto.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_SUBSISTEMA_ESGOTO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		subsistemaEsgoto.setOperacaoEfetuada(operacaoEfetuada);
		subsistemaEsgoto.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(subsistemaEsgoto);
		// Fim - Registrando as transações

		Integer idSubsistemaEsgoto = (Integer) getControladorUtil().inserir(subsistemaEsgoto);
		subsistemaEsgoto.setId(idSubsistemaEsgoto);

		return idSubsistemaEsgoto;
	}

	/**
	 * @author isilva
	 * @param subsistemaEsgoto
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	private void validarSubsistemaEsgoto(SubsistemaEsgoto subsistemaEsgoto, Usuario usuarioLogado) throws ControladorException{

		if(Util.isVazioOuBranco(subsistemaEsgoto.getCodigo())){
			throw new ControladorException("atencao.required", null, "Código");
		}

		if(Util.isVazioOuBranco(subsistemaEsgoto.getDescricao())){
			throw new ControladorException("atencao.required", null, "Descrição");
		}

		if(subsistemaEsgoto.getSistemaEsgoto() == null || subsistemaEsgoto.getSistemaEsgoto().getId() == null){
			throw new ControladorException("atencao.required", null, "Sistema de Esgoto");
		}

		if(subsistemaEsgoto.getLocalidade() == null || subsistemaEsgoto.getLocalidade().getId() == null){
			throw new ControladorException("atencao.required", null, "Localidade");
		}

		if(subsistemaEsgoto.getEsgotoTratamentoTipo() == null || subsistemaEsgoto.getEsgotoTratamentoTipo().getId() == null){
			throw new ControladorException("atencao.required", null, "Tipo de Tratamento");
		}
	}

	/**
	 * [UC0525] Manter Subsistema Esgoto [SB0001]Atualizar Subsistema Esgoto
	 * 
	 * @author Ailton Sousa
	 * @date 28/01/2011
	 */
	public void atualizarSubsistemaEsgoto(SubsistemaEsgoto subsistemaEsgoto, Usuario usuarioLogado) throws ControladorException{

		validarSubsistemaEsgoto(subsistemaEsgoto, usuarioLogado);

		// [FS0002] - Atualização realizada por outro usuário

		FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
		// Seta o filtro para buscar o sistema de esgoto na base
		filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.ID, subsistemaEsgoto.getId()));

		// Procura sistema de esgoto na base
		Collection subsistemaEsgotoAtualizados = getControladorUtil().pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

		SubsistemaEsgoto subsistemaEsgotoNaBase = (SubsistemaEsgoto) Util.retonarObjetoDeColecao(subsistemaEsgotoAtualizados);

		if(subsistemaEsgotoNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o sistema de esgoto já foi atualizado por outro usuário
		// durante esta atualização

		if(subsistemaEsgotoNaBase.getUltimaAlteracao().after(subsistemaEsgoto.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		subsistemaEsgoto.setUltimaAlteracao(new Date());

		// [UC] - Registrar Transação
		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_SUBSISTEMA_ESGOTO, subsistemaEsgoto
						.getCodigo(), subsistemaEsgoto.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_SUBSISTEMA_ESGOTO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		subsistemaEsgoto.setOperacaoEfetuada(operacaoEfetuada);
		subsistemaEsgoto.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(subsistemaEsgoto);
		// Fim - Registrando as transações

		// Atualiza o objeto na base
		getControladorUtil().atualizar(subsistemaEsgoto);

	}

	/**
	 * Atualizar SubBacia
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 31/01/2011
	 */
	public void atualizarSubBacia(SubBacia subBacia, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		getControladorUtil().registrarOperacaoCodId(subBacia, usuarioLogado, Operacao.OPERACAO_ATUALIZAR_SUBBACIA);

		// [FS0002] - Atualização realizada por outro usuário

		FiltroSubBacia filtroSubBacia = new FiltroSubBacia();
		// Seta o filtro para buscar o sistema de esgoto na base
		filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.ID, subBacia.getId()));

		// Procura sistema de esgoto na base
		Collection subBaciaAtualizados = getControladorUtil().pesquisar(filtroSubBacia, SubBacia.class.getName());

		SubBacia subBaciaNaBase = (SubBacia) Util.retonarObjetoDeColecao(subBaciaAtualizados);

		if(subBaciaNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o sub-bacia já foi atualizado por outro usuário
		// durante esta atualização

		if(subBaciaNaBase.getUltimaAlteracao().after(subBacia.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		subBacia.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(subBacia);

	}

	/**
	 * Atualizar ZonaAbastecimento
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 02/02/2011
	 */
	public void atualizarZonaAbastecimento(ZonaAbastecimento zonaAbastecimento, Usuario usuarioLogado) throws ControladorException{

		validarZonaAbastecimento(zonaAbastecimento);

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_ZONA_ABASTECIMENTO, zonaAbastecimento
						.getCodigo(), zonaAbastecimento.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_ZONA_ABASTECIMENTO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		zonaAbastecimento.setOperacaoEfetuada(operacaoEfetuada);

		registradorOperacao.registrarOperacao(zonaAbastecimento);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();
		// Seta o filtro para buscar o sistema de esgoto na base
		filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.ID, zonaAbastecimento.getId()));

		// Procura sistema de esgoto na base
		Collection zonaAbastecimentoAtualizados = getControladorUtil()
						.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

		ZonaAbastecimento zonaAbastecimentoNaBase = (ZonaAbastecimento) Util.retonarObjetoDeColecao(zonaAbastecimentoAtualizados);

		if(zonaAbastecimentoNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o sub-bacia já foi atualizado por outro usuário
		// durante esta atualização

		if(zonaAbastecimentoNaBase.getUltimaAlteracao().after(zonaAbastecimento.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		zonaAbastecimento.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(zonaAbastecimento);

	}

	/**
	 * [UCXXXX] Manter Subsistema Esgoto [SB0002]Remover Subsistema Esgoto
	 * 
	 * @author Ailton Sousa
	 * @date 28/01/2011
	 */
	public void removerSubsistemaEsgoto(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			FiltroSubsistemaEsgoto filtro = new FiltroSubsistemaEsgoto();
			filtro.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, SubsistemaEsgoto.class.getName());

			SubsistemaEsgoto subsistemaEsgoto = (SubsistemaEsgoto) Util.retonarObjetoDeColecao(colecaoRetorno);

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorSubsistemaEsgoto = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_SUBSISTEMA_ESGOTO,
							subsistemaEsgoto.getCodigo(), subsistemaEsgoto.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_REMOVER_SUBSISTEMA_ESGOTO);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			subsistemaEsgoto.setOperacaoEfetuada(operacaoEfetuada);

			registradorSubsistemaEsgoto.registrarOperacao(subsistemaEsgoto);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			// [FS0003]Subsistema de Esgoto possui vinculos no sistema
			this.getControladorUtil().remover(subsistemaEsgoto);
		}
	}

	/**
	 * Remover Bacia
	 * 
	 * @author Hebert Falcão
	 * @date 18/02/2011
	 * @throws ControladorException
	 */
	public void removerBacia(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			FiltroBacia filtro = new FiltroBacia();
			filtro.adicionarParametro(new ParametroSimples(FiltroBacia.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, Bacia.class.getName());

			Bacia bacia = (Bacia) Util.retonarObjetoDeColecao(colecaoRetorno);

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorBacia = new RegistradorOperacao(Operacao.OPERACAO_BACIA_REMOVER, bacia.getId(), bacia.getId(),
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_BACIA_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			bacia.setOperacaoEfetuada(operacaoEfetuada);

			registradorBacia.registrarOperacao(bacia);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			this.getControladorUtil().remover(bacia);
		}
	}

	/**
	 * Remover SubBacia
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 28/01/2011
	 */
	public void removerSubBacia(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			FiltroSubBacia filtro = new FiltroSubBacia();
			filtro.adicionarParametro(new ParametroSimples(FiltroSubBacia.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, SubBacia.class.getName());

			SubBacia subBacia = (SubBacia) Util.retonarObjetoDeColecao(colecaoRetorno);

			RegistradorOperacao registradorSubBacia = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_SUBSBACIA, subBacia.getCodigo(),
							subBacia.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_REMOVER_SUBSBACIA);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			subBacia.setOperacaoEfetuada(operacaoEfetuada);

			registradorSubBacia.registrarOperacao(subBacia);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			this.getControladorUtil().remover(subBacia);

		}

	}

	/**
	 * Remover ZonaAbastecimento
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 02/02/2011
	 */
	public void removerZonaAbastecimento(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			FiltroZonaAbastecimento filtro = new FiltroZonaAbastecimento();
			filtro.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, ZonaAbastecimento.class.getName());

			ZonaAbastecimento zonaAbastecimento = (ZonaAbastecimento) Util.retonarObjetoDeColecao(colecaoRetorno);

			RegistradorOperacao registradorZonaAbastecimento = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_ZONA_ABASTECIMENTO,
							zonaAbastecimento.getCodigo(), zonaAbastecimento.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_REMOVER_ZONA_ABASTECIMENTO);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			zonaAbastecimento.setOperacaoEfetuada(operacaoEfetuada);

			registradorZonaAbastecimento.registrarOperacao(zonaAbastecimento);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			this.getControladorUtil().remover(zonaAbastecimento);

		}
	}

	/**
	 * [UC] - Inserir Localidade Dado Operacional
	 * 
	 * @author isilva
	 * @date 26/01/2011
	 * @param localidadeDadoOperacional
	 * @param usuarioLogado
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer inserirLocalidadeDadoOperacional(LocalidadeDadoOperacional localidadeDadoOperacional, Usuario usuarioLogado)
					throws ControladorException{

		Integer idLocalidadeDadoOperacional = null;

		try{

			// Validação
			validarLocalidadeDadoOperacional(localidadeDadoOperacional, usuarioLogado, true);

			// Última Alteração e Indicador de Uso
			localidadeDadoOperacional.setUltimaAlteracao(new Date());
			localidadeDadoOperacional.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

			// Verifica existência com mesmo AnoMes para a localidade
			FiltroLocalidadeDadoOperacional filtroLocalidadeDadoOperacional = new FiltroLocalidadeDadoOperacional();
			filtroLocalidadeDadoOperacional.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadoOperacional.ANOMES_REFERENCIA,
							localidadeDadoOperacional.getAnoMesReferencia()));
			filtroLocalidadeDadoOperacional.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadoOperacional.LOCALIDADE_ID,
							localidadeDadoOperacional.getLocalidade().getId()));

			Collection<LocalidadeDadoOperacional> colecaoLocalidadeDadoOperacional = getControladorUtil().pesquisar(
							filtroLocalidadeDadoOperacional, LocalidadeDadoOperacional.class.getName());

			if(colecaoLocalidadeDadoOperacional != null && !colecaoLocalidadeDadoOperacional.isEmpty()){
				throw new ControladorException("atencao.dados.operacionais.ja.existem.cadastro.localidade", null, localidadeDadoOperacional
								.getLocalidade().getId().toString());
			}

			// Início - Registrando as transações
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_LOCALIDADE_DADO_OPERACIONAL_INSERIR,
							localidadeDadoOperacional.getLocalidade().getId(), localidadeDadoOperacional.getId(),
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_LOCALIDADE_DADO_OPERACIONAL_INSERIR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			localidadeDadoOperacional.setOperacaoEfetuada(operacaoEfetuada);
			localidadeDadoOperacional.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(localidadeDadoOperacional);
			// Fim - Registrando as transações

			idLocalidadeDadoOperacional = (Integer) getControladorUtil().inserir(localidadeDadoOperacional);

			localidadeDadoOperacional.setId(idLocalidadeDadoOperacional);

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;
		}

		return idLocalidadeDadoOperacional;
	}

	/**
	 * Remover Localidade Dado Operacional
	 * 
	 * @author Hebert Falcão
	 * @date 18/02/2011
	 * @throws ControladorException
	 */
	public void removerLocalidadeDadoOperacional(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			FiltroLocalidadeDadoOperacional filtro = new FiltroLocalidadeDadoOperacional();
			filtro.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadoOperacional.ID, new Integer(ids[i])));
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidadeDadoOperacional.LOCALIDADE);
			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, LocalidadeDadoOperacional.class.getName());

			LocalidadeDadoOperacional localidadeDadoOperacional = (LocalidadeDadoOperacional) Util.retonarObjetoDeColecao(colecaoRetorno);

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorLocalidadeDadoOperacional = new RegistradorOperacao(
							Operacao.OPERACAO_LOCALIDADE_DADO_OPERACIONAL_REMOVER, localidadeDadoOperacional.getLocalidade().getId(),
							localidadeDadoOperacional.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_LOCALIDADE_DADO_OPERACIONAL_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			localidadeDadoOperacional.setOperacaoEfetuada(operacaoEfetuada);

			registradorLocalidadeDadoOperacional.registrarOperacao(localidadeDadoOperacional);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			this.getControladorUtil().remover(localidadeDadoOperacional);
		}
	}

	/**
	 * @author isilva
	 * @date 26/01/2011
	 * @param localidadeDadoOperacional
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	private void validarLocalidadeDadoOperacional(LocalidadeDadoOperacional localidadeDadoOperacional, Usuario usuarioLogado,
					boolean operacaoInserir) throws ControladorException{

		if(localidadeDadoOperacional.getAnoMesReferencia() == null || localidadeDadoOperacional.getAnoMesReferencia() == 0){
			throw new ControladorException("atencao.required", null, "Referência");
		}

		// *****************************************************************
		/*
		if(operacaoInserir == true){

			Integer anoMesInformado = localidadeDadoOperacional.getAnoMesReferencia();
			Integer anoMesCorrente = Util.getAnoMesComoInteger(new Date());

			if(Util.compararAnoMesReferencia(anoMesInformado, anoMesCorrente, "<")){
			 throw new ControladorException("atencao.mes.ou.ano.menor.que.corrente");
			 }
		}*/
		// *****************************************************************

		if(localidadeDadoOperacional.getLocalidade() == null || localidadeDadoOperacional.getLocalidade().getId() == null){
			throw new ControladorException("atencao.required", null, "Localidade");
		}

		// Cria Filtros
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeDadoOperacional.getLocalidade().getId()));
		// filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<Localidade> localidades = getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(localidades == null || localidades.isEmpty()){
			throw new ControladorException("atencao.localidade.inexistente");
		}

		/*
		 * Verifica se o usuário informou ao menos um campo com o valor diferente de branco ou Zero
		 */
		boolean informouAlgo = false;

		if(!isVazioOuZero(localidadeDadoOperacional.getVolumeProduzido(), "Volume Produzido")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getExtensaoRedeAgua(), "Extensão da Rede de Água")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getExtensaoRedeEsgoto(), "Extensão da Rede de Esgoto")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdFuncionariosAdministracao(), "Num. Funcionários da Adm.")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdFuncionariosAdministracaoTercerizados(), "Num. Funcionários da Adm. Terceiros")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdFuncionariosOperacao(), "Num. Funcionários da Oper.")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdFuncionariosOperacaoTercerizados(), "Num. Funcionários da Oper. Terceiros")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdSulfatoAluminio(), "Qtd. Sulfato de Aluminio")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdCloroGasoso(), "Qtd. Cloro Gasoso")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdHipocloritoSodio(), "Qtd. Hipocl. De Sodio")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQuantidadeFluor(), "Qtd. Fluor")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdConsumoEnergia(), "Consumo de Energia")){
			informouAlgo = true;
		}

		if(!isVazioOuZero(localidadeDadoOperacional.getQtdHorasParalisadas(), "Qtd. Horas Paralisadas")){
			informouAlgo = true;
		}

		if(!informouAlgo){
			throw new ControladorException("atencao.nenhum.dado.parametro.foi.informado");
		}

		if(Util.isVazioOuBranco(String.valueOf(localidadeDadoOperacional.getIndicadorUso()))){
			throw new ControladorException("atencao.campo.informado.invalido", null, "Indicador de Uso");
		}

		if(usuarioLogado == null){
			throw new ControladorException("atencao.usuario.vazia");
		}
		// ****************************************************************
	}

	/**
	 * @author isilva
	 * @date 26/01/2011
	 * @param campo
	 * @param nomeCampo
	 * @return
	 * @throws ControladorException
	 */
	private boolean isVazioOuZero(Object campo, String nomeCampo) throws ControladorException{

		boolean retorno = false;

		if(campo == null){
			return true;
		}

		try{
			if(campo instanceof Integer){
				if((Integer) campo == 0){
					retorno = true;
				}
			}else if(campo instanceof BigDecimal){
				if(((BigDecimal) campo).compareTo(BigDecimal.ZERO) == 0){
					retorno = true;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new ControladorException("atencao.campo.informado.invalido", null, nomeCampo);
		}

		return retorno;
	}

	/**
	 * UC0042 - Inserir Bacia
	 * 
	 * @author Hebert Falcão
	 * @created 27 de Janeiro de 2011
	 */
	public Integer inserirBacia(Bacia bacia, Usuario usuario) throws ControladorException{

		if(bacia.getCodigo() == null){
			throw new ControladorException("atencao.required", null, "Código");
		}

		if(bacia.getSubsistemaEsgoto() == null || bacia.getSubsistemaEsgoto().getId() == null){
			throw new ControladorException("atencao.required", null, "Subsistema Esgoto");
		}

		// [FS0003 - Verificar a existência da Bacia]
		FiltroBacia filtroBacia = new FiltroBacia();
		filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.CODIGO, bacia.getCodigo()));
		filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO, bacia.getSubsistemaEsgoto().getId()));

		Collection colecaoPesquisa = getControladorUtil().pesquisar(filtroBacia, Bacia.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			throw new ControladorException("atencao.pesquisa_bacia_ja_cadastrada", null, Integer.toString(bacia.getCodigo()));
		}

		bacia.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		bacia.setUltimaAlteracao(new Date());

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_BACIA_INSERIR, bacia.getCodigo(),
						bacia.getId(), new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_BACIA_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		bacia.setOperacaoEfetuada(operacaoEfetuada);
		bacia.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(bacia);
		// Fim - Registrando as transações

		Integer idBacia = (Integer) getControladorUtil().inserir(bacia);
		bacia.setId(idBacia);

		return idBacia;
	}

	/**
	 * Inserir SubBacia
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @created 31 de Janeiro de 2011
	 */
	public Integer inserirSubBacia(SubBacia subBacia, Usuario usuario) throws ControladorException{

		if(subBacia.getCodigo() == null){
			throw new ControladorException("atencao.required", null, "Código");
		}

		if(subBacia.getBacia() == null || subBacia.getBacia().getId() == null){
			throw new ControladorException("atencao.required", null, "Bacia");
		}

		// [FS0003 - Verificar a existência da Bacia]
		FiltroSubBacia filtroSubBacia = new FiltroSubBacia();
		filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.CODIGO, subBacia.getCodigo()));
		filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.BACIA_ID, subBacia.getBacia().getId()));
		filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoPesquisa = getControladorUtil().pesquisar(filtroSubBacia, SubBacia.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			throw new ControladorException("atencao.pesquisa_subbacia_ja_cadastrada", null, Integer.toString(subBacia.getCodigo()));
		}

		subBacia.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		subBacia.setUltimaAlteracao(new Date());

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SUBBACIA_INSERIR, subBacia.getCodigo(),
						subBacia.getId(), new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_SUBBACIA_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		subBacia.setOperacaoEfetuada(operacaoEfetuada);
		subBacia.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(subBacia);
		// Fim - Registrando as transações

		Integer idSubBacia = (Integer) getControladorUtil().inserir(subBacia);
		subBacia.setId(idSubBacia);

		return idSubBacia;
	}

	/**
	 * Inserir ZonaAbastecimento
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @created 02 de Fevereiro de 2011
	 */
	public Integer inserirZonaAbastecimento(ZonaAbastecimento zonaAbastecimento, Usuario usuario) throws ControladorException{

		validarZonaAbastecimento(zonaAbastecimento);

		if(zonaAbastecimento.getDistritoOperacional() == null || zonaAbastecimento.getDistritoOperacional().getId() == null){
			throw new ControladorException("atencao.required", null, "Distrito Operacional");
		}

		// [FS0003 - Verificar a existência da Unidade Operacional]
		FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();
		filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.CODIGO, zonaAbastecimento.getCodigo()));
		filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL_ID, zonaAbastecimento
						.getDistritoOperacional().getId()));
		filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoPesquisa = getControladorUtil().pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			throw new ControladorException("atencao.pesquisa_zonaabastecimento_ja_cadastrada", null, Integer.toString(zonaAbastecimento
							.getCodigo()));
		}

		zonaAbastecimento.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		zonaAbastecimento.setUltimaAlteracao(new Date());

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_ZONA_ABASTECIMENTO, zonaAbastecimento
						.getCodigo(), zonaAbastecimento.getId(), new UsuarioAcaoUsuarioHelper(usuario,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_ZONA_ABASTECIMENTO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		zonaAbastecimento.setOperacaoEfetuada(operacaoEfetuada);

		registradorOperacao.registrarOperacao(zonaAbastecimento);
		// Fim - Registrando as transações

		Integer idZonaAbastecimento = (Integer) getControladorUtil().inserir(zonaAbastecimento);
		zonaAbastecimento.setId(idZonaAbastecimento);

		return idZonaAbastecimento;
	}

	/**
	 * @author isilva
	 * @param inserirZonaAbastecimentoActionForm
	 * @throws ControladorException
	 */
	private void validarZonaAbastecimento(ZonaAbastecimento zonaAbastecimento) throws ControladorException{

		if(Util.isVazioOuBranco(zonaAbastecimento.getCodigo())){
			throw new ControladorException("atencao.required", null, "Código");
		}

		if(Util.isVazioOuBranco(zonaAbastecimento.getDescricao())){
			throw new ControladorException("atencao.required", null, "Descrição");
		}

		if(zonaAbastecimento.getSistemaAbastecimento() == null || zonaAbastecimento.getSistemaAbastecimento().getId() == null){
			throw new ControladorException("atencao.required", null, "Sistema de Abastecimento");
		}

		if(zonaAbastecimento.getDistritoOperacional() == null || zonaAbastecimento.getDistritoOperacional().getId() == null){
			throw new ControladorException("atencao.required", null, "Distrito Operacional");
		}

		if(zonaAbastecimento.getLocalidade() == null || zonaAbastecimento.getLocalidade().getId() == null){
			throw new ControladorException("atencao.required", null, "Localidade");
		}

		if(zonaAbastecimento.getFaixaPressaoInferior() == null){
			throw new ControladorException("atencao.required", null, "Faixa Pressão Inferior");
		}

		if(zonaAbastecimento.getFaixaPressaoSuperior() == null){
			throw new ControladorException("atencao.required", null, "Faixa Pressão Superior");
		}

	}

	/**
	 * [UCXXXX] Inserir Sistema Abastecimento
	 * Metodo inserção do Sistema de Abastecimento
	 * 
	 * @author Anderson Italo
	 * @date 28/01/2011
	 * @param sistemaAbastecimento
	 *            , usuario
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirSistemaAbastecimento(SistemaAbastecimento sistemaAbastecimento, Usuario usuario) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO SISTEMA
		// ABASTECIMENTO----------------------------
		RegistradorOperacao registradorOperacaoSistemaAbastecimento = new RegistradorOperacao(
						Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_INSERIR, sistemaAbastecimento.getCodigo(), sistemaAbastecimento.getId(),
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoUnidade = new Operacao();
		operacaoUnidade.setId(Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_INSERIR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoUnidade);

		sistemaAbastecimento.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		sistemaAbastecimento.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoSistemaAbastecimento.registrarOperacao(sistemaAbastecimento);
		// ------------ REGISTRAR TRANSAÇÃO SISTEMA
		// ABASTECIMENTO----------------------------

		return this.getControladorUtil().inserir(sistemaAbastecimento);
	}

	/**
	 * [UCXXXX] Manter Sistema Abastecimento
	 * Metodo de atualização do Sistema de Abastecimento
	 * 
	 * @author Anderson Italo
	 * @date 28/01/2011
	 * @param sistemaAbastecimento
	 *            , usuario
	 * @return void
	 * @throws ControladorException
	 */
	public void atualizarSistemaAbastecimento(SistemaAbastecimento sistemaAbastecimento, Usuario usuario) throws ControladorException{

		// [FS0003 – Atualização realizada por outro usuário]
		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.ID, sistemaAbastecimento.getId()));

		// Procura o sistema de abastecimento na base
		Collection colecaoRetorno = getControladorUtil().pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

		if(colecaoRetorno == null || colecaoRetorno.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		SistemaAbastecimento sistemaAbastecimentoNaBase = (SistemaAbastecimento) Util.retonarObjetoDeColecao(colecaoRetorno);

		// Verificar se o sistema de abastecimento já foi atualizado por outro
		// usuário durante esta atualização
		if(sistemaAbastecimentoNaBase.getUltimaAlteracao().after(sistemaAbastecimento.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// ------------ REGISTRAR TRANSAÇÃO SISTEMA
		// ABASTECIMENTO---------------------
		RegistradorOperacao registradorSistemaAbastecimento = new RegistradorOperacao(Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_ATUALIZAR,
						sistemaAbastecimento.getCodigo(), sistemaAbastecimento.getId(), new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoSistemaAbastecimento = new Operacao();
		operacaoSistemaAbastecimento.setId(Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoSistemaAbastecimento);

		sistemaAbastecimento.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		sistemaAbastecimento.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorSistemaAbastecimento.registrarOperacao(sistemaAbastecimento);
		// ------------ REGISTRAR TRANSAÇÃO SISTEMA
		// ABASTECIMENTO--------------------

		getControladorUtil().atualizar(sistemaAbastecimento);
	}

	/**
	 * [UCXXXX] Manter Sistema Abastecimento
	 * Pesquisa Sistema de abastecimento pelo codigo
	 * 
	 * @author Anderson Italo
	 * @date 28/11/2011
	 * @param codigo
	 * @throws ControladorException
	 */
	public SistemaAbastecimento pesquisarSistemaAbastecimento(Integer codigo) throws ControladorException{

		SistemaAbastecimento retorno = null;
		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();

		if(codigo != null && codigo.intValue() > 0){
			filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.CODIGO, codigo));
		}

		Collection resultado = getControladorUtil().pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

		if(resultado != null && !resultado.isEmpty()){
			retorno = (SistemaAbastecimento) Util.retonarObjetoDeColecao(resultado);
		}

		return retorno;
	}

	/**
	 * [UCXXXX] Manter Sistema Abastecimento
	 * Pesquisa Sistema de abastecimento pelo filtro
	 * 
	 * @author Anderson Italo
	 * @date 28/11/2011
	 * @param filtroSistemaAbastecimento
	 * @throws ControladorException
	 */
	public Collection pesquisarSistemaAbastecimentoFiltro(FiltroSistemaAbastecimento filtroSistemaAbastecimento)
					throws ControladorException{

		Collection retorno = null;
		retorno = getControladorUtil().pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

		return retorno;
	}

	/**
	 * [UC] - Atualizar Localidade Dado Operacional
	 * 
	 * @author isilva
	 * @date 27/01/2011
	 * @param localidadeDadoOperacional
	 * @param usuarioLogado
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void atualizarLocalidadeDadoOperacional(LocalidadeDadoOperacional localidadeDadoOperacional, Usuario usuarioLogado)
					throws ControladorException{

		// Validação
		validarLocalidadeDadoOperacional(localidadeDadoOperacional, usuarioLogado, false);

		// Verifica existência com mesmo AnoMes para a localidade
		// ********************* Atualização realizada por outro usuário *********************
		FiltroLocalidadeDadoOperacional filtroLocalidadeDadoOperacional = new FiltroLocalidadeDadoOperacional();
		filtroLocalidadeDadoOperacional.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.ID, localidadeDadoOperacional
						.getId()));

		Collection<LocalidadeDadoOperacional> colecaoLocalidadeDadoOperacional = getControladorUtil().pesquisar(
						filtroLocalidadeDadoOperacional, LocalidadeDadoOperacional.class.getName());

		if(colecaoLocalidadeDadoOperacional == null || colecaoLocalidadeDadoOperacional.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		LocalidadeDadoOperacional localidadeDadoOperacionalBase = (LocalidadeDadoOperacional) colecaoLocalidadeDadoOperacional.iterator()
						.next();

		if(localidadeDadoOperacionalBase.getUltimaAlteracao().after(localidadeDadoOperacional.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// ************************************************************************************

		try{

			localidadeDadoOperacional.setUltimaAlteracao(new Date());

			// [UC] - Registrar Transação
			// Início - Registrando as transações
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR,
							localidadeDadoOperacional.getLocalidade().getId(), localidadeDadoOperacional.getId(),
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			localidadeDadoOperacional.setOperacaoEfetuada(operacaoEfetuada);
			localidadeDadoOperacional.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(localidadeDadoOperacional);
			// Fim - Registrando as transações

			getControladorUtil().atualizar(localidadeDadoOperacional);

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;
		}
	}

	/**
	 * [UC] - Atualizar Bacia
	 * 
	 * @author isilva
	 * @date 03/02/2011
	 * @param bacia
	 * @param usuarioLogado
	 */
	@SuppressWarnings("unchecked")
	public void atualizarBacia(Bacia bacia, Usuario usuarioLogado) throws ControladorException{

		// Validação
		validarBacia(bacia);

		// ********************* Atualização realizada por outro usuário *********************
		// [FS - Verificar a existência da Bacia]
		FiltroBacia filtroBacia = new FiltroBacia();
		filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.ID, bacia.getId()));

		Collection<Bacia> colecaoBacia = getControladorUtil().pesquisar(filtroBacia, Bacia.class.getName());

		if(colecaoBacia == null || colecaoBacia.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		Bacia baciaBase = (Bacia) colecaoBacia.iterator().next();

		if(baciaBase.getUltimaAlteracao().after(bacia.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// ************************************************************************************

		try{

			bacia.setUltimaAlteracao(new Date());

			// [UC] - Registrar Transação
			// Início - Registrando as transações
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_BACIA_ATUALIZAR, bacia.getCodigo(), bacia
							.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_BACIA_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			bacia.setOperacaoEfetuada(operacaoEfetuada);
			bacia.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(bacia);
			// Fim - Registrando as transações

			getControladorUtil().atualizar(bacia);

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;
		}
	}

	/**
	 * @author isilva
	 * @date 03/02/2011
	 * @param bacia
	 * @throws ControladorException
	 */
	private void validarBacia(Bacia bacia) throws ControladorException{

		if(bacia.getCodigo() == null){
			throw new ControladorException("atencao.required", null, "Código");
		}

		if(Util.isVazioOuBranco(bacia.getDescricao())){
			throw new ControladorException("atencao.required", null, "Decrição");
		}

		if(bacia.getSubsistemaEsgoto() == null || bacia.getSubsistemaEsgoto().getId() == null){
			throw new ControladorException("atencao.required", null, "Subsistema Esgoto");
		}

		if(bacia.getSubsistemaEsgoto().getSistemaEsgoto() == null || bacia.getSubsistemaEsgoto().getSistemaEsgoto().getId() == null){
			throw new ControladorException("atencao.required", null, "Sistema de Esgoto");
		}

		// if (Util.isVazioOuBranco(bacia.getDescricaoAbreviada())) {
		// throw new ControladorException("atencao.required", null, "Decrição Abreviada");
		// }

		if(Util.isVazioOuBranco(bacia.getIndicadorUso())){
			throw new ControladorException("atencao.required", null, "Indicador de Uso");
		}
	}

	/**
	 * [UCXXXX] Manter Sistema Abastecimento
	 * [SB0002] - Excluir Sistema de Abastecimento
	 * 
	 * @author Anderson Italo
	 * @date 02/02/2011
	 * @param ids
	 *            , usuarioLogado
	 * @throws ControladorException
	 */
	public void removerSistemaAbastecimento(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			FiltroSistemaAbastecimento filtro = new FiltroSistemaAbastecimento();
			filtro.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.ID, new Integer(ids[i])));

			Collection colecaoRetorno = this.pesquisarSistemaAbastecimentoFiltro(filtro);

			SistemaAbastecimento sistemaAbastecimentoRemocao = (SistemaAbastecimento) Util.retonarObjetoDeColecao(colecaoRetorno);

			RegistradorOperacao registradorSistemaAbastecimento = new RegistradorOperacao(Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_REMOVER,
							sistemaAbastecimentoRemocao.getCodigo(), sistemaAbastecimentoRemocao.getId(), new UsuarioAcaoUsuarioHelper(
											usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			sistemaAbastecimentoRemocao.setOperacaoEfetuada(operacaoEfetuada);

			registradorSistemaAbastecimento.registrarOperacao(sistemaAbastecimentoRemocao);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			this.getControladorUtil().remover(sistemaAbastecimentoRemocao);

		}
	}

	/**
	 * [UCXXXX] Inserir Setor Abastecimento
	 * Metodo inserção do Setor de Abastecimento
	 * 
	 * @author Péricles Tavares
	 * @date 07/02/2011
	 * @param setorAbastecimento
	 *            , usuario
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirSetorAbastecimento(SetorAbastecimento setorAbastecimento, Usuario usuario) throws ControladorException{

		// [FS0002] - Verificar preenchimento dos campos
		if(setorAbastecimento.getCodigoSetorAbastecimento() == null){
			throw new ControladorException("atencao.codigo_setor_abastecimento_nao_informado");
		}
		if(setorAbastecimento.getDescricao() == null){
			throw new ControladorException("atencao.descricao_setor_abastecimento_nao_informado");
		}
		if(setorAbastecimento.getSistemaAbastecimento() == null){
			throw new ControladorException("atencao.sistema_abastecimento_setor_abastecimento_nao_informado");
		}

		if(setorAbastecimento.getDistritoOperacional() == null){
			throw new ControladorException("atencao.distrito_operacional_setor_abastecimento_nao_informado");
		}

		if(setorAbastecimento.getZonaAbastecimento() == null){
			throw new ControladorException("atencao.zona_abastecimento_setor_abastecimento_nao_informado");
		}

		// [FS0003] - Verificar existência do Setor de Abastecimento
		FiltroSetorAbastecimento filtroSistemaAbastecimento = new FiltroSetorAbastecimento();

		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.SISTEMA_ABASTECIMENTO_ID,
						setorAbastecimento.getSistemaAbastecimento().getId()));

		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.DISTRITO_OPERACIONAL_ID,
						setorAbastecimento.getSistemaAbastecimento().getId()));
		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO_ID,
						setorAbastecimento.getZonaAbastecimento().getId()));
		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.CODIGO_SETOR_ABASTECIMENTO,
						setorAbastecimento.getCodigoSetorAbastecimento()));

		Collection colecaoSetorAbastecimento = getControladorUtil().pesquisar(filtroSistemaAbastecimento,
						SetorAbastecimento.class.getName());

		if(colecaoSetorAbastecimento != null && !colecaoSetorAbastecimento.isEmpty()){
			throw new ControladorException("atencao.setorAbastecimento.existente");
		}
		setorAbastecimento.setUltimaAlteracao(new Date());
		setorAbastecimento.setIndicadorUso(ConstantesSistema.SIM);

		// ------------ REGISTRAR TRANSAÇÃO SETOR ABASTECIMENTO----------------------------
		RegistradorOperacao registradorOperacaoSetorAbastecimento = new RegistradorOperacao(Operacao.OPERACAO_SETOR_ABASTECIMENTO_INSERIR,
						setorAbastecimento.getCodigoSetorAbastecimento(), setorAbastecimento.getId(), new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoUnidade = new Operacao();
		operacaoUnidade.setId(Operacao.OPERACAO_SETOR_ABASTECIMENTO_INSERIR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoUnidade);

		setorAbastecimento.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		setorAbastecimento.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoSetorAbastecimento.registrarOperacao(setorAbastecimento);
		// ------------ REGISTRAR TRANSAÇÃO SETOR ABASTECIMENTO----------------------------

		return this.getControladorUtil().inserir(setorAbastecimento);
	}

	/**
	 * [UCXXXX] Manter Setor Abastecimento
	 * Metodo atualizar do Setor de Abastecimento
	 * 
	 * @author Péricles Tavares
	 * @date 07/02/2011
	 * @param setorAbastecimento
	 *            , usuario
	 * @throws ControladorException
	 */
	public void atualizarSetorAbastecimento(SetorAbastecimento setorAbastecimento, Usuario usuario) throws ControladorException{

		// [FS0003 – Atualização realizada por outro usuário]
		FiltroSetorAbastecimento filtroSetorAbastecimento = new FiltroSetorAbastecimento();
		filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ID, setorAbastecimento.getId()));

		// Procura o sistema de abastecimento na base
		Collection colecaoRetorno = getControladorUtil().pesquisar(filtroSetorAbastecimento, SetorAbastecimento.class.getName());

		if(colecaoRetorno == null || colecaoRetorno.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		SetorAbastecimento setorAbastecimentoBase = (SetorAbastecimento) Util.retonarObjetoDeColecao(colecaoRetorno);

		// Verificar se o setor de abastecimento já foi atualizado por outro usuário durante esta
		// atualização
		if(setorAbastecimento.getUltimaAlteracao().after(setorAbastecimentoBase.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
		setorAbastecimento.setUltimaAlteracao(new Date());
		// ------------ REGISTRAR TRANSAÇÃO SETOR ABASTECIMENTO----------------------------
		RegistradorOperacao registradorOperacaoSetorAbastecimento = new RegistradorOperacao(
						Operacao.OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR, setorAbastecimento.getCodigoSetorAbastecimento(),
						setorAbastecimento.getId(), new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoUnidade = new Operacao();
		operacaoUnidade.setId(Operacao.OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoUnidade);

		setorAbastecimento.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		setorAbastecimento.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoSetorAbastecimento.registrarOperacao(setorAbastecimento);
		// ------------ REGISTRAR TRANSAÇÃO SETOR ABASTECIMENTO----------------------------

		this.getControladorUtil().atualizar(setorAbastecimento);
	}

	/**
	 * [UCXXXX] Manter Setor Abastecimento
	 * [SB0002] - Excluir Setor de Abastecimento
	 * 
	 * @author Péricles Tavares
	 * @date 09/02/2011
	 * @param ids
	 *            , usuarioLogado
	 * @throws ControladorException
	 */
	public void removerSetorAbastecimento(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			// Remoção de Setor Abastecimento de Distrito Operacional
			/*
			 * FiltroDistritoOperacional filtroDistritoOperacional = new
			 * FiltroDistritoOperacional();
			 * filtroDistritoOperacional.adicionarParametro(new
			 * ParametroSimples(FiltroDistritoOperacional.SETORABASTECIMENTO, new Integer(ids[i])));
			 * 
			 * filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade("setorAbastecimento"
			 * );
			 * Collection colecaoDistritoOperacional =
			 * getControladorUtil().pesquisar(filtroDistritoOperacional,
			 * DistritoOperacional.class.getName());
			 * if (colecaoDistritoOperacional != null && !colecaoDistritoOperacional.isEmpty()) {
			 * throw new ControladorException("atencao.dependencias.outras.informacoes.existentes");
			 * }
			 */

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			FiltroSetorAbastecimento filtro = new FiltroSetorAbastecimento();
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, SetorAbastecimento.class.getName());

			SetorAbastecimento setorAbastecimentoRemocao = (SetorAbastecimento) Util.retonarObjetoDeColecao(colecaoRetorno);

			RegistradorOperacao registradorSistemaAbastecimento = new RegistradorOperacao(Operacao.OPERACAO_SETOR_ABASTECIMENTO_REMOVER,
							setorAbastecimentoRemocao.getCodigoSetorAbastecimento(), setorAbastecimentoRemocao.getId(),
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_SETOR_ABASTECIMENTO_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			setorAbastecimentoRemocao.setOperacaoEfetuada(operacaoEfetuada);

			registradorSistemaAbastecimento.registrarOperacao(setorAbastecimentoRemocao);
			// ------------ REGISTRAR TRANSAÇÃO ----------------
			setorAbastecimentoRemocao.setUltimaAlteracao(new Date());
			this.getControladorUtil().remover(setorAbastecimentoRemocao);

		}
	}
}