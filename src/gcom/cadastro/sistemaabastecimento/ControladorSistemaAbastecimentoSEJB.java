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
 * Péricles Tavares
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

package gcom.cadastro.sistemaabastecimento;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.empresa.Empresa;
import gcom.interceptor.RegistradorOperacao;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesJNDI;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ControladorUtilLocal;
import gcom.util.ControladorUtilLocalHome;
import gcom.util.ErroRepositorioException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ControladorSistemaAbastecimentoSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioSistemaAbastecimento repositorioSistemaAbastecimento = null;

	SessionContext sessionContext;

	public void ejbCreate() throws CreateException{

		repositorioSistemaAbastecimento = RepositorioSistemaAbastecimentoHBM.getInstancia();
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
	 * [UC0373] Inserir Unidade Organizacional
	 * Metodo inserção da unidade organizacional
	 * [FS0001] – Validar Localidade
	 * [FS0002] – Validar Gerencia Regional
	 * [FS0003] – Verificar exitência da descrição
	 * [FS0004] – Verificar exitência da sigla
	 * [FS0005] – Validar Empresa
	 * [FS0006] – Validar Unidade Superior
	 * 
	 * @author Raphael Pinto
	 * @date 31/07/2006
	 * @param unidadeOrganizacional
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirSistemaAbastecimento(SistemaAbastecimento sistemaAbastecimento, Usuario usuario) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO SISTEMA ABASTECIMENTO----------------------------
		RegistradorOperacao registradorOperacaoSistemaAbastecimento = new RegistradorOperacao(
						Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_INSERIR, new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoUnidade = new Operacao();
		operacaoUnidade.setId(Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_INSERIR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoUnidade);

		sistemaAbastecimento.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		sistemaAbastecimento.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoSistemaAbastecimento.registrarOperacao(sistemaAbastecimento);
		// ------------ REGISTRAR TRANSAÇÃO SISTEMA ABASTECIMENTO----------------------------

		return this.getControladorUtil().inserir(sistemaAbastecimento);
	}

	public void atualizarUnidadeOrganizacional(SistemaAbastecimento sistemaAbastecimento, Usuario usuario) throws ControladorException{

		String idSistema = "" + sistemaAbastecimento.getId();

		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
		// Seta o filtro para buscar a unidade organizacional na base
		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.ID, idSistema));

		// Procura unidade organizacional na base
		Collection sistemasAtualizadas = getControladorUtil().pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

		SistemaAbastecimento sistemaAbastecimentoAtualizado = (SistemaAbastecimento) Util.retonarObjetoDeColecao(sistemasAtualizadas);

		if(sistemaAbastecimentoAtualizado == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Procura a rota na base
		SistemaAbastecimento sistemaAbastecimentoNaBase = null;
		sistemaAbastecimentoNaBase = (SistemaAbastecimento) ((List) (this.getControladorUtil().pesquisar(filtroSistemaAbastecimento,
						SistemaAbastecimento.class.getName()))).get(0);

		// Verificar se a rota já foi atualizado por outro usuário
		// durante esta atualização
		if(sistemaAbastecimentoNaBase.getUltimaAlteracao().after(sistemaAbastecimento.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// ------------ REGISTRAR TRANSAÇÃO SISTEMA ABASTECIMENTO----------------------------
		RegistradorOperacao registradorSistemaAbastecimento = new RegistradorOperacao(Operacao.OPERACAO_SISTEMA_ABASTECIMENTO_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoSistemaAbastecimento = new Operacao();
		operacaoSistemaAbastecimento.setId(Operacao.OPERACAO_UNIDADE_ORGANIZACIONAL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoSistemaAbastecimento);

		sistemaAbastecimento.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		sistemaAbastecimento.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorSistemaAbastecimento.registrarOperacao(sistemaAbastecimento);
		// ------------ REGISTRAR TRANSAÇÃO UNIDADE ORGANIZACIONAL----------------------------

		getControladorUtil().atualizar(sistemaAbastecimento);
	}

	/**
	 * [UC0375] Manter Unidade Organizacional
	 * 
	 * @author Ana Maria
	 * @date 28/11/2006
	 * @param unidadeOrganizacional
	 * @throws ControladorException
	 */
	public SistemaAbastecimento pesquisarUnidadeOrganizacional(Integer idUnidadeOrganizacional) throws ControladorException{

		SistemaAbastecimento retorno = null;
		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento(FiltroSistemaAbastecimento.ID);
		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.ID, idUnidadeOrganizacional));
		retorno = (SistemaAbastecimento) getControladorUtil().pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());
		;
		return retorno;
	}

}
