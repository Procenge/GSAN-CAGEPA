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

package gcom.cadastro.localidade;

import gcom.arrecadacao.Concessionaria;
import gcom.arrecadacao.ConcessionariaLocalidade;
import gcom.arrecadacao.FiltroConcessionaria;
import gcom.arrecadacao.FiltroConcessionariaLocalidade;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.funcionario.IRepositorioFuncionario;
import gcom.cadastro.funcionario.RepositorioFuncionarioHBM;
import gcom.cadastro.unidade.IRepositorioUnidade;
import gcom.cadastro.unidade.RepositorioUnidadeHBM;
import gcom.faturamento.*;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.Rota;
import gcom.micromedicao.RoteiroEmpresa;
import gcom.seguranca.acesso.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.*;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author not attributable
 * @created 6 de Setembro de 2005
 * @version 1.0
 */

public class ControladorLocalidadeSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioSetorComercial repositorioSetorComercial = null;

	private IRepositorioQuadra repositorioQuadra = null;

	private IRepositorioLocalidade repositorioLocalidade = null;

	private IRepositorioGerenciaRegional repositorioGerenciaRegional = null;

	private IRepositorioUnidade repositorioUnidade = null;

	protected IRepositorioFuncionario repositorioFuncionario = null;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioSetorComercial = RepositorioSetorComercialHBM.getInstancia();
		repositorioQuadra = RepositorioQuadraHBM.getInstancia();
		repositorioLocalidade = RepositorioLocalidadeHBM.getInstancia();
		repositorioGerenciaRegional = RepositorioGerenciaRegionalHBM.getInstancia();
		repositorioUnidade = RepositorioUnidadeHBM.getInstancia();
		repositorioFuncionario = RepositorioFuncionarioHBM.getInstancia();
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
	 * Retorna o valor de controladorAcesso
	 * 
	 * @return O valor de controladorAcesso
	 */
	private ControladorAcessoLocal getControladorAcesso(){

		ControladorAcessoLocalHome localHome = null;
		ControladorAcessoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorAcessoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ACESSO_SEJB);
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

	protected ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
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
	 * < <Descrição do método>>
	 * 
	 * @param setorComercial
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public void atualizarSetorComercial(SetorComercial setorComercial, Collection<SetorComercialVencimento> colSetorComVencIncluir,
					Collection<SetorComercialVencimento> colSetorComVencAtulizar, Usuario usuarioLogado, Collection rotas)
					throws ControladorException{

		// -----VALIDAÇÃO DOS TIMESTAMP PARA ATUALIZAÇÃO DE CADASTRO

		// Validação para Setor Comercial
		if(setorComercial != null){
			// Cria o filtro
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			// Pega o nome do pacote do objeto
			String nomePacoteObjeto = SetorComercial.class.getName();

			// Seta os parametros do filtro
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, setorComercial.getId()));

			// Pesquisa a coleção de acordo com o filtro passado
			Collection setoresComerciais = getControladorUtil().pesquisar(filtroSetorComercial, nomePacoteObjeto);
			SetorComercial setorComercialNaBase = (SetorComercial) Util.retonarObjetoDeColecao(setoresComerciais);

			// Verifica se a data de alteração do objeto gravado na base é
			// maior que a na instancia
			if((setorComercialNaBase.getUltimaAlteracao().after(setorComercial.getUltimaAlteracao()))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// Seta a data/hora
			setorComercial.setUltimaAlteracao(new Date());

		}

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SETOR_COMERCIAL_ATUALIZAR, setorComercial
						.getCodigo(), setorComercial.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_SETOR_COMERCIAL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		setorComercial.setOperacaoEfetuada(operacaoEfetuada);
		setorComercial.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(setorComercial);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		if(rotas != null && !rotas.isEmpty()){

			Iterator itRotas = rotas.iterator();

			while(itRotas.hasNext()){

				Rota rota = (Rota) itRotas.next();

				getControladorMicromedicao().atualizarRota(rota, setorComercial.getLocalidade().getId().toString(), new ArrayList(),
								usuarioLogado, false);

			}

		}

		// Atualiza objeto
		getControladorUtil().atualizar(setorComercial);

		Iterator<SetorComercialVencimento> it = null;

		// Atualizar dias de vencimento
		if(!colSetorComVencIncluir.isEmpty()){
			it = colSetorComVencIncluir.iterator();
			while(it.hasNext()){
				SetorComercialVencimento setorComercialVencimento = it.next();
				setorComercialVencimento.setSetorComercial(setorComercial);

				// ------------ REGISTRAR TRANSAÇÃO----------------------------
				RegistradorOperacao registradorOperacao1 = new RegistradorOperacao(Operacao.OPERACAO_SETOR_COMERCIAL_VENCIMENTO_INSERIR,
								setorComercialVencimento.getSetorComercial().getCodigo(), setorComercialVencimento.getId(),
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao1 = new Operacao();
				operacao1.setId(Operacao.OPERACAO_SETOR_COMERCIAL_VENCIMENTO_INSERIR);

				OperacaoEfetuada operacaoEfetuada1 = new OperacaoEfetuada();
				operacaoEfetuada1.setOperacao(operacao1);

				setorComercialVencimento.setOperacaoEfetuada(operacaoEfetuada1);
				setorComercialVencimento.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao1.registrarOperacao(setorComercialVencimento);
				// ------------ REGISTRAR TRANSAÇÃO----------------------------

				getControladorUtil().inserir(setorComercialVencimento);
			}
		}

		if(!colSetorComVencAtulizar.isEmpty()){
			it = colSetorComVencAtulizar.iterator();
			while(it.hasNext()){
				SetorComercialVencimento setorComercialVencimento = it.next();
				setorComercialVencimento.setSetorComercial(setorComercial);

				// ------------ REGISTRAR TRANSAÇÃO----------------------------
				RegistradorOperacao registradorOperacao2 = new RegistradorOperacao(Operacao.OPERACAO_SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR,
								setorComercialVencimento.getSetorComercial().getCodigo(), setorComercialVencimento.getId(),
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao2 = new Operacao();
				operacao2.setId(Operacao.OPERACAO_SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR);

				OperacaoEfetuada operacaoEfetuada2 = new OperacaoEfetuada();
				operacaoEfetuada2.setOperacao(operacao2);

				setorComercialVencimento.setOperacaoEfetuada(operacaoEfetuada2);
				setorComercialVencimento.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao2.registrarOperacao(setorComercialVencimento);
				// ------------ REGISTRAR TRANSAÇÃO----------------------------

				getControladorUtil().atualizar(setorComercialVencimento);
			}
		}

	}

	/**
	 * Atualizar Localidade
	 * 
	 * @param localidade
	 *            Descrição do parâmetro
	 * @param usuario
	 * @throws ControladorException
	 */
	public void atualizarLocalidade(Localidade localidade, Usuario usuario, Integer idConcessionaria, String dataInicioVigencia)
					throws ControladorException{

		// -----VALIDAÇÃO DOS TIMESTAMP PARA ATUALIZAÇÃO DE CADASTRO

		// Validação para Setor Comercial
		if(localidade != null){
			// Cria o filtro
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			// Pega o nome do pacote do objeto
			String nomePacoteObjeto = Localidade.class.getName();

			// Seta os parametros do filtro
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidade.getId()));

			// Pesquisa a coleção de acordo com o filtro passado
			Collection localidades = getControladorUtil().pesquisar(filtroLocalidade, nomePacoteObjeto);
			Localidade localidadeNaBase = (Localidade) Util.retonarObjetoDeColecao(localidades);

			// Verifica se a data de alteração do objeto gravado na base é
			// maior que a na instancia
			if((localidadeNaBase.getUltimaAlteracao().after(localidade.getUltimaAlteracao()))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			atualizarConcessionaria(localidade, idConcessionaria, dataInicioVigencia);

			// Seta a data/hora
			localidade.setUltimaAlteracao(new Date());

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_LOCALIDADE_ATUALIZAR, localidade.getId(),
							localidade.getId(), new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_LOCALIDADE_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			localidade.setOperacaoEfetuada(operacaoEfetuada);
			localidade.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(localidade);
			// ------------ REGISTRAR TRANSAÇÃO ----------------
		}

		// Atualiza objeto
		getControladorUtil().atualizar(localidade);
	}

	private void atualizarConcessionaria(Localidade localidade, Integer idConcessionaria, String dataInicioVigencia)
					throws ControladorException{

		if(Util.isNaoNuloBrancoZero(idConcessionaria)){

			FiltroConcessionariaLocalidade filtroConcessionaria = new FiltroConcessionariaLocalidade();
			filtroConcessionaria.adicionarParametro(new ParametroSimples(FiltroConcessionariaLocalidade.ID_LOCALIDADE, localidade.getId()));
			filtroConcessionaria.adicionarParametro(new ParametroNulo(FiltroConcessionariaLocalidade.DATA_VIGENCIA_FIM));

			Collection<ConcessionariaLocalidade> colecaoPesquisa = getControladorUtil().pesquisar(filtroConcessionaria,
							ConcessionariaLocalidade.class.getName());
			ConcessionariaLocalidade concessionariaLocalidade = (ConcessionariaLocalidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
			Date dataInicioDt = Util.converterStringParaData(dataInicioVigencia);
			Date dataVigenciaFim = Util.subtrairNumeroDiasDeUmaData(dataInicioDt, 1);

			concessionariaLocalidade.setDataVigenciaFim(dataVigenciaFim);
			Date ultimaAlteracao = new Date();
			concessionariaLocalidade.setUltimaAlteracao(ultimaAlteracao);

			getControladorUtil().atualizar(concessionariaLocalidade);

			FiltroConcessionaria filtro = new FiltroConcessionaria();
			filtro.adicionarParametro(new ParametroSimples(FiltroConcessionaria.ID, idConcessionaria));
			Collection<Concessionaria> colecao = getControladorUtil().pesquisar(filtro, Concessionaria.class.getName());
			Concessionaria concessionaria = (Concessionaria) Util.retonarObjetoDeColecao(colecao);

			ConcessionariaLocalidade novaConcessionariaLocalidade = new ConcessionariaLocalidade();
			novaConcessionariaLocalidade.setLocalidade(localidade);
			novaConcessionariaLocalidade.setDataVigenciaInicio(Util.converterStringParaData(dataInicioVigencia));
			novaConcessionariaLocalidade.setConcessionaria(concessionaria);
			novaConcessionariaLocalidade.setUltimaAlteracao(ultimaAlteracao);

			getControladorUtil().inserir(novaConcessionariaLocalidade);
		}
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param quadra
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public void atualizarQuadra(Quadra quadra, Usuario usuarioLogado) throws ControladorException{

		// -----VALIDAÇÃO DOS TIMESTAMP PARA ATUALIZAÇÃO DE CADASTRO

		// Validação para quadra
		if(quadra != null){
			// Cria o filtro
			FiltroQuadra filtroQuadra = new FiltroQuadra();
			// Pega o nome do pacote do objeto
			String nomePacoteObjeto = Quadra.class.getName();

			// Seta os parametros do filtro
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, quadra.getId()));

			// Pesquisa a coleção de acordo com o filtro passado
			Collection quadras = getControladorUtil().pesquisar(filtroQuadra, nomePacoteObjeto);
			Quadra quadraNaBase = (Quadra) Util.retonarObjetoDeColecao(quadras);

			// Verifica se a data de alteração do objeto gravado na base é
			// maior que a na instancia
			if((quadraNaBase.getUltimaAlteracao().after(quadra.getUltimaAlteracao()))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// Seta a data/hora
			quadra.setUltimaAlteracao(new Date());

		}

		// ------------ CONTROLE DE ABRANGENCIA ----------------
		Abrangencia abrangencia = new Abrangencia(usuarioLogado, quadra);
		// ------------ CONTROLE DE ABRANGENCIA ----------------

		if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.acesso.negado.abrangencia");
		}else{

			// Verifica se a ROTA informada está em (SETOR COMERCIAL, FATURAMENTO_GRUPO)
			if(!this.getControladorFaturamento().existeVinculoRotaSetorComercialFaturamentoGrupo(quadra)){
				throw new ControladorException("atencao.nao.existe.vinculo.rota.setorcomercial.faturamentogrupo");
			}

			// Atualiza objeto
			getControladorUtil().atualizar(quadra);
		}
	}

	/**
	 * Pesquisa uma coleção de setor comercial com uma query especifica
	 * 
	 * @param idLocalidade
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Collection pesquisarSetorComercial(int idLocalidade) throws ControladorException{

		try{
			return repositorioSetorComercial.pesquisarSetorComercial(idLocalidade);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Pesquisa uma coleção de quadra com uma query especifica
	 * 
	 * @param idSetorComercial
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadra(int idSetorComercial) throws ControladorException{

		Collection colecaoQuadraArray = null;
		Collection<Quadra> colecaoRetorno = new ArrayList();

		try{
			colecaoQuadraArray = repositorioQuadra.pesquisarQuadra(idSetorComercial);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		Iterator colecaoQuadraArrayIt = colecaoQuadraArray.iterator();
		Object[] quadraArray;
		Quadra quadra;
		Rota rota = new Rota();
		FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();

		while(colecaoQuadraArrayIt.hasNext()){

			quadraArray = (Object[]) colecaoQuadraArrayIt.next();

			quadra = new Quadra();

			quadra.setId((Integer) quadraArray[0]);

			quadra.setNumeroQuadra(new Integer(String.valueOf(quadraArray[1])).intValue());

			faturamentoGrupo.setId((Integer) quadraArray[3]);

			rota.setId((Integer) quadraArray[2]);
			rota.setFaturamentoGrupo(faturamentoGrupo);

			quadra.setRota(rota);

			colecaoRetorno.add(quadra);

		}

		return colecaoRetorno;
	}

	/**
	 * Retorna o valor de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	private ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
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
	 * Pesquisa uma coleção de localidades por gerência regional
	 * 
	 * @param idGerenciaRegional
	 *            Código da gerência regional solicitada
	 * @return Coleção de Localidades da Gerência Regional solicitada
	 * @exception ControladorException
	 *                Erro no hibernate
	 */
	public Collection<Localidade> pesquisarLocalidadePorGerenciaRegional(int idGerenciaRegional) throws ControladorException{

		try{
			// pesquisa a coleção de localidades para a gerência regional
			// informada
			return repositorioLocalidade.pesquisarLocalidadePorGerenciaRegional(idGerenciaRegional);
			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa uma coleção de gerências regionais
	 * 
	 * @return Coleção de Gerências Regionais
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection<GerenciaRegional> pesquisarGerenciaRegional() throws ControladorException{

		try{
			// pesquisa as gerencias regionais existentes no sisitema
			return repositorioGerenciaRegional.pesquisarGerenciaRegional();

			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa uma gerência regional pelo id
	 * 
	 * @author Rafael Corrêa
	 * @date 01/08/2006
	 * @return Gerência Regional
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public GerenciaRegional pesquisarObjetoGerenciaRegionalRelatorio(Integer idGerenciaRegional) throws ControladorException{

		try{
			// pesquisa as gerencias regionais existentes no sisitema
			Object[] objetoGerenciaRegional = repositorioGerenciaRegional.pesquisarObjetoGerenciaRegionalRelatorio(idGerenciaRegional);

			GerenciaRegional gerenciaRegional = new GerenciaRegional();

			gerenciaRegional.setId((Integer) objetoGerenciaRegional[0]);
			gerenciaRegional.setNomeAbreviado((String) objetoGerenciaRegional[1]);

			return gerenciaRegional;

			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Inseri um objeto do tipo setor comercial no BD
	 * 
	 * @param setorComercial
	 * @return ID gerado
	 * @throws ControladorException
	 */
	public Integer inserirSetorComercial(SetorComercial setorComercial, Usuario usuarioLogado) throws ControladorException{

		Integer retorno = null;

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, setorComercial.getLocalidade()
						.getId()));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercial
						.getCodigo()));

		// Retorna caso já exista um setor comercial com o código
		// informado
		Collection colecaoPesquisa = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			// Setor comercial já cadastrado
			throw new ControladorException("atencao.pesquisa.setor_comercial_ja_cadastrado", null, "" + setorComercial.getCodigo(),
							setorComercial.getLocalidade().getDescricao());
		}

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SETOR_COMERCIAL_INSERIR, setorComercial
						.getCodigo(), setorComercial.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_SETOR_COMERCIAL_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		setorComercial.setOperacaoEfetuada(operacaoEfetuada);
		setorComercial.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(setorComercial);
		// Fim - Registrando as transações

		retorno = (Integer) this.getControladorUtil().inserir(setorComercial);

		return retorno;
	}

	/**
	 * /** Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Este fluxo secundário tem como objetivo pesquisar a localidade digitada
	 * pelo usuário
	 * [FS0007] - Verificar existência da localidade
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idLocalidadeDigitada
	 * @return
	 * @throws ControladorException
	 */
	public Localidade pesquisarLocalidadeDigitada(Integer idLocalidadeDigitada) throws ControladorException{

		// Varaiável que vai armazenar a localidade digitada pelo usuário
		Localidade localidadeDigitada = null;

		// Pesquisa a localidade informada pelo usuário no sistema
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeDigitada));

		Collection<Localidade> colecaoLocalidade = getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

		// Caso exista a localidade no sistema
		// Retorna para o usuário a localidade retornada pela pesquisa
		// Caso contrário retorna um objeto nulo
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
			localidadeDigitada = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
		}

		// Retorna a localdiade encontrada ou nulo se não existir
		return localidadeDigitada;
	}

	/**
	 * Método que retorna o maior número da quadra de um setor comercial
	 * 
	 * @author Rafael Corrêa
	 * @date 11/07/2006
	 * @param idSetorComercial
	 * @return
	 * @throws ControladorException
	 */

	public int pesquisarMaximoCodigoQuadra(Integer idSetorComercial) throws ControladorException{

		try{
			return repositorioQuadra.pesquisarMaximoCodigoQuadra(idSetorComercial);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Método que retorna o maior código de setor comercial de uma localidade
	 * 
	 * @author Rafael Corrêa
	 * @date 11/07/2006
	 * @param idLocalidade
	 * @return
	 * @throws ControladorException
	 */

	public int pesquisarMaximoCodigoSetorComercial(Integer idLocalidade) throws ControladorException{

		try{
			return repositorioSetorComercial.pesquisarMaximoCodigoSetorComercial(idLocalidade);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Método que retorna o maior id de Localidade
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * @return
	 * @throws ControladorException
	 */

	public int pesquisarMaximoIdLocalidade() throws ControladorException{

		try{
			return repositorioLocalidade.pesquisarMaximoIdLocalidade();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa uma localidade pelo id
	 * 
	 * @author Rafael Corrêa
	 * @date 01/08/2006
	 * @return Localidade
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Localidade pesquisarObjetoLocalidadeRelatorio(Integer idLocalidade) throws ControladorException{

		try{
			// pesquisa as gerencias regionais existentes no sisitema
			Object[] objetoLocalidade = repositorioLocalidade.pesquisarObjetoLocalidadeRelatorio(idLocalidade);

			Localidade localidade = new Localidade();

			localidade.setId((Integer) objetoLocalidade[0]);
			localidade.setDescricao((String) objetoLocalidade[1]);

			return localidade;

			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa um setor comercial pelo código e pelo id da localidade
	 * 
	 * @author Rafael Corrêa
	 * @date 01/08/2006
	 * @return SetorComercial
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public SetorComercial pesquisarObjetoSetorComercialRelatorio(Integer codigoSetorComercial, Integer idLocalidade)
					throws ControladorException{

		SetorComercial setorComercial = new SetorComercial();

		try{
			// pesquisa as gerencias regionais existentes no sisitema
			Object[] objetoSetorComercial = repositorioSetorComercial.pesquisarObjetoSetorComercialRelatorio(codigoSetorComercial,
							idLocalidade);

			setorComercial.setCodigo((Integer) objetoSetorComercial[0]);
			setorComercial.setDescricao((String) objetoSetorComercial[1]);

			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}

		return setorComercial;
	}

	public Integer verificarExistenciaLocalidade(Integer idLocalidade) throws ControladorException{

		// Retorna o cliente encontrado ou vazio se não existir
		try{
			return repositorioLocalidade.verificarExistenciaLocalidade(idLocalidade);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Metódo responsável por inserir uma quadra no sitema
	 * [UC0000 - Inserir Quadra]
	 * 
	 * @author Vivianne Sousa, Pedro Alexandre
	 * @date 27/06/2006, 16/11/2006
	 * @param quadra
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirQuadra(Quadra quadra, Usuario usuarioLogado) throws ControladorException{

		Integer retorno = null;

		// ------------ CONTROLE DE ABRANGENCIA ----------------
		Abrangencia abrangencia = new Abrangencia(usuarioLogado, quadra.getSetorComercial());

		if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.acesso.negado.abrangencia");
		}else{

			// Verifica se a ROTA informada está em (SETOR COMERCIAL, FATURAMENTO_GRUPO)
			if(!this.getControladorFaturamento().existeVinculoRotaSetorComercialFaturamentoGrupo(quadra)){
				throw new ControladorException("atencao.nao.existe.vinculo.rota.setorcomercial.faturamentogrupo");
			}

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_QUADRA_INSERIR,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_QUADRA_INSERIR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			quadra.setOperacaoEfetuada(operacaoEfetuada);
			quadra.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(quadra);
			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			retorno = (Integer) this.getControladorUtil().inserir(quadra);
		}
		// ------------ CONTROLE DE ABRANGENCIA ----------------

		return retorno;
	}

	/**
	 * @author Bruno Ferreira dos Santos
	 * @date 18/02/2011
	 * @param ids
	 * @param idsSetorComercialVencimento
	 * @return
	 * @throws ControladorException
	 */
	/**
	 * Remover SubBacia
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 28/01/2011
	 */
	public void removerSetorComercial(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){

			FiltroSetorComercialVencimento filtroComercialVencimento = new FiltroSetorComercialVencimento();
			filtroComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.SETOR_COMERCIAL_ID,
							new Integer(ids[i])));
			filtroComercialVencimento.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			Collection<SetorComercialVencimento> colSetorComVenc = getControladorUtil().pesquisar(filtroComercialVencimento,
							SetorComercialVencimento.class.getName());

			if(colSetorComVenc != null && !colSetorComVenc.isEmpty()){

				Iterator<SetorComercialVencimento> it = colSetorComVenc.iterator();

				while(it.hasNext()){
					SetorComercialVencimento setorComercialVencimento = it.next();
					// ------------ REGISTRAR TRANSAÇÃO----------------------------
					RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SETOR_COMERCIAL_VENCIMENTO_REMOVER,
									setorComercialVencimento.getSetorComercial().getCodigo(), setorComercialVencimento.getId(),
									new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					Operacao operacao = new Operacao();
					operacao.setId(Operacao.OPERACAO_SETOR_COMERCIAL_VENCIMENTO_REMOVER);

					OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
					operacaoEfetuada.setOperacao(operacao);

					setorComercialVencimento.setOperacaoEfetuada(operacaoEfetuada);
					setorComercialVencimento.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

					registradorOperacao.registrarOperacao(setorComercialVencimento);
					// ------------ REGISTRAR TRANSAÇÃO ----------------

					this.getControladorUtil().remover(setorComercialVencimento);
				}

			}

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			FiltroSetorComercial filtro = new FiltroSetorComercial();
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, SetorComercial.class.getName());

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoRetorno);

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SETOR_COMERCIAL_REMOVER, setorComercial
							.getCodigo(), setorComercial.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
							UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_SETOR_COMERCIAL_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			setorComercial.setOperacaoEfetuada(operacaoEfetuada);
			setorComercial.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(setorComercial);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			this.getControladorUtil().remover(setorComercial);
		}

	}

	/**
	 * metódo responsável por verificar se o usuário que está tentando remover
	 * as quadras tem abrangência
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/11/2006
	 * @param ids
	 * @param pacoteNomeObjeto
	 * @param operacaoEfetuada
	 * @param acaoUsuarioHelper
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void removerQuadra(String[] ids, String pacoteNomeObjeto, OperacaoEfetuada operacaoEfetuada,
					Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper, Usuario usuarioLogado) throws ControladorException{

		if(ids != null && ids.length != 0){
			for(int i = 0; i < ids.length; i++){
				int id = Integer.parseInt(ids[i]);
				Quadra quadra = new Quadra();
				quadra.setId(id);

				// ------------ CONTROLE DE ABRANGENCIA ----------------
				Abrangencia abrangencia = new Abrangencia(usuarioLogado, quadra);
				if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.acesso.negado.abrangencia");
				}
				// ------------ CONTROLE DE ABRANGENCIA ----------------

			}

			getControladorUtil().remover(ids, pacoteNomeObjeto, operacaoEfetuada, acaoUsuarioHelper);
		}
	}

	// /**
	// * < <Descrição do método>>
	// *
	// * @param gerência Regional
	// * Descrição do parâmetro
	// * @throws ControladorException
	// */
	// public void atualizarGerenciaRegional(GerenciaRegional gerenciaRegional)
	// throws ControladorException {
	//
	// // -----VALIDAÇÃO DOS TIMESTAMP PARA ATUALIZAÇÃO DE CADASTRO
	//
	// // Validação para Setor Comercial
	// if (gerenciaRegional != null) {
	// // Cria o filtro
	// FiltroGerenciaRegional filtroGerenciaRegional = new
	// FiltroGerenciaRegional();
	// // Pega o nome do pacote do objeto
	// String nomePacoteObjeto = GerenciaRegional.class.getName();
	//
	// // Seta os parametros do filtro
	// filtroGerenciaRegional.adicionarParametro(new
	// ParametroSimples(FiltroGerenciaRegional.ID, gerenciaRegional.getId()));
	//
	// // Pesquisa a coleção de acordo com o filtro passado
	// Collection gerenciaRegionais =
	// getControladorUtil().pesquisar(filtroGerenciaRegional, nomePacoteObjeto);
	// GerenciaRegional gerenciaRegionalNaBase = (GerenciaRegional)
	// Util.retonarObjetoDeColecao(gerenciaRegionais);
	//
	// // Verifica se a data de alteração do objeto gravado na base é
	// // maior que a na instancia
	// if
	// ((gerenciaRegionalNaBase.getUltimaAlteracao().after(gerenciaRegional.getUltimaAlteracao())))
	// {
	// sessionContext.setRollbackOnly();
	// throw new ControladorException("atencao.atualizacao.timestamp");
	// }
	//
	// // Seta a data/hora
	// gerenciaRegional.setUltimaAlteracao(new Date());
	//
	// }
	//
	// // Atualiza objeto
	// getControladorUtil().atualizar(gerenciaRegional);
	//
	// }

	// /**
	// * Permite inserir uma Gerência Regional
	// *
	// * [UC0217] Inserir Gerencia Regional
	// *
	// * @author Thiago Tenório
	// * @date 30/03/2006
	// *
	// */
	// public Integer inserirGerenciaRegional(GerenciaRegional gerenciaRegional)
	// throws ControladorException {
	//
	// FiltroGerenciaRegional filtroGerenciaRegional = new
	// FiltroGerenciaRegional();
	// filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
	// FiltroGerenciaRegional.ID, gerenciaRegional.getId()));
	//
	// filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
	// FiltroGerenciaRegional.NOME, gerenciaRegional.getNome()));
	//
	// filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
	// FiltroGerenciaRegional.NOME_ABREVIADO, gerenciaRegional
	// .getNomeAbreviado()));
	//
	// Collection colecaoEnderecos = getControladorUtil().pesquisar(
	// filtroGerenciaRegional, GerenciaRegional.class.getName());
	//
	// if (colecaoEnderecos != null && !colecaoEnderecos.isEmpty()) {
	// throw new ControladorException(
	// "atencao.endereco_localidade_nao_informado");
	// }
	//
	// Integer id = (Integer) getControladorUtil().inserir(gerenciaRegional);
	//
	// return id;
	//
	// }

	/**
	 * Pesquisa o id da gerência regional para a qual a localidade pertence.
	 * [UC0267] Encerrar Arrecadação do Mês, [UC0155] Encerrar Faturamento do
	 * Mês
	 * 
	 * @author Pedro Alexandre
	 * @date 05/01/2007
	 * @param idLocalidade
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarIdGerenciaParaLocalidade(Integer idLocalidade) throws ControladorException{

		try{
			return repositorioGerenciaRegional.pesquisarIdGerenciaParaLocalidade(idLocalidade);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
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
	public void atualizarLogradouroCepGerenciaRegional(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo)
					throws ControladorException{

		try{

			this.repositorioLocalidade.atualizarLogradouroCepGerenciaRegional(logradouroCepAntigo, logradouroCepNovo);

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
	public void atualizarLogradouroBairroGerenciaRegional(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException{

		try{

			this.repositorioLocalidade.atualizarLogradouroBairroGerenciaRegional(logradouroBairroAntigo, logradouroBairroNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

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

			this.repositorioLocalidade.atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

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

			this.repositorioLocalidade.atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Pesquisa o id da unidade negocio para a qual a localidade pertence.
	 * [UC0267] Encerrar Arrecadação do Mês, [UC0155] Encerrar Faturamento do
	 * Mês
	 * 
	 * @author Raphael Rossiter
	 * @date 30/05/2007
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public Integer pesquisarIdUnidadeNegocioParaLocalidade(Integer idLocalidade) throws ControladorException{

		try{

			return repositorioUnidade.pesquisarIdUnidadeNegocioParaLocalidade(idLocalidade);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisar os ids da Localidade
	 * 
	 * @author Thiago tenório
	 * @date 07/02/2007
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarTodosIdsLocalidade() throws ControladorException{

		try{
			return repositorioLocalidade.pesquisarTodosIdLocalidade();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Pesquisa uma coleção de quadra com uma query especifica
	 * 
	 * @param idsSetorComercial
	 * @param idFaturamentoGrupo
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadrasPorSetorComercialFaturamentoGrupo(int localidade, int[] idsSetorComercial, Integer idFaturamentoGrupo)
					throws ControladorException{

		Collection colecaoQuadraArray = null;
		Collection<Quadra> colecaoRetorno = new ArrayList();

		try{
			colecaoQuadraArray = repositorioQuadra.pesquisarQuadrasPorSetorComercialFaturamentoGrupo(localidade, idsSetorComercial,
							idFaturamentoGrupo);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		Iterator colecaoQuadraArrayIt = colecaoQuadraArray.iterator();
		Object[] quadraArray;
		Quadra quadra;
		// Rota rota = new Rota();
		// FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();

		while(colecaoQuadraArrayIt.hasNext()){

			quadraArray = (Object[]) colecaoQuadraArrayIt.next();

			quadra = new Quadra();

			quadra.setId((Integer) quadraArray[0]);

			quadra.setNumeroQuadra(new Integer(String.valueOf(quadraArray[1])).intValue());

			// faturamentoGrupo.setId((Integer) quadraArray[3]);

			// rota.setId((Integer) quadraArray[2]);
			// rota.setFaturamentoGrupo(faturamentoGrupo);
			SetorComercial setor = new SetorComercial();
			setor.setId((Integer) quadraArray[2]);
			setor.setCodigo(((Integer) quadraArray[3]).intValue());
			quadra.setSetorComercial(setor);

			// Id roteiro empresa
			if(quadraArray[4] != null){
				RoteiroEmpresa roEm = new RoteiroEmpresa();
				roEm.setId(((Integer) quadraArray[4]).intValue());
				quadra.setRoteiroEmpresa(roEm);
			}

			quadra.setUltimaAlteracao((Date) quadraArray[5]);

			colecaoRetorno.add(quadra);

		}

		return colecaoRetorno;
	}

	/**
	 * [UC608] Efetuar Ligação de Esgoto sem RA
	 * 
	 * @author Sávio Luiz
	 * @date 10/09/2007
	 * @return
	 */

	public Short pesquisarIndicadorRedeEsgotoQuadra(int idImovel) throws ControladorException{

		try{
			return repositorioQuadra.pesquisarIndicadorRedeEsgotoQuadra(idImovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0583] Incluir Roteiro de Empresa
	 * [UC0584] Alterar Roteiro de Empresa
	 * 
	 * @author Sávio Luiz
	 * @date 10/09/2007
	 * @author Eduardo Henrique
	 * @date 02/07/2008 Correção no método
	 * @return
	 */
	public Collection pesquisarQuadrasPorRoteiroEmpresa(int idRoteiroEmpresa) throws ControladorException{

		Collection colecaoQuadraArray = null;
		Collection<Quadra> colecaoRetorno = new ArrayList();

		try{
			colecaoQuadraArray = repositorioQuadra.pesquisarQuadrasPorRoteiroEmpresa(idRoteiroEmpresa);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		Iterator colecaoQuadraArrayIt = colecaoQuadraArray.iterator();
		Object[] quadraArray;
		Quadra quadra;

		while(colecaoQuadraArrayIt.hasNext()){

			quadraArray = (Object[]) colecaoQuadraArrayIt.next();

			quadra = new Quadra();

			quadra.setId((Integer) quadraArray[0]);

			quadra.setNumeroQuadra(new Integer(String.valueOf(quadraArray[1])).intValue());

			SetorComercial setor = new SetorComercial();
			setor.setId((Integer) quadraArray[2]);
			setor.setCodigo(((Integer) quadraArray[3]).intValue());
			quadra.setSetorComercial(setor);

			// Id roteiro empresa
			if(quadraArray[4] != null){
				RoteiroEmpresa roEm = new RoteiroEmpresa();
				roEm.setId(((Integer) quadraArray[4]).intValue());
				quadra.setRoteiroEmpresa(roEm);
			}
			Rota rota = new Rota();
			rota.setId((Integer) quadraArray[5]);
			quadra.setRota(rota);

			FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();
			faturamentoGrupo.setId((Integer) quadraArray[6]);
			rota.setFaturamentoGrupo(faturamentoGrupo);

			Localidade localidade = new Localidade();
			localidade.setId((Integer) quadraArray[7]);
			localidade.setDescricao((String) quadraArray[8]);
			setor.setLocalidade(localidade);

			quadra.setUltimaAlteracao((Date) quadraArray[9]);
			colecaoRetorno.add(quadra);

		}

		return colecaoRetorno;
	}

	/**
	 * Obtém Elo Pólo
	 * 
	 * @author Ana Maria
	 * @date 10/12/2007
	 * @throws ControladorException
	 */
	public Collection pesquisarEloPolo() throws ControladorException{

		try{

			return repositorioLocalidade.pesquisarEloPolo();
			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * @author isilva
	 *         Pesquisa uma coleção de setor comercial pertecente a uma rota, e essa por sua vez
	 *         esteja no grupo
	 *         com identificador igual ao informado pelo por parametro.
	 * @param idGrupo
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarSetorComercialPorGrupoEmRota(int idGrupo) throws ControladorException{

		try{
			return repositorioSetorComercial.pesquisarSetorComercialPorGrupoEmRota(idGrupo);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * @author Andre Nishmura
	 * @date 22/01/2011
	 * @param SetorComercial
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarBairroPorSetorComercial(Integer setorComercial) throws ControladorException{

		try{
			return repositorioLocalidade.pesquisarBairroPorSetorComercial(setorComercial);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Obtém uma coleção de Localidades com dados do intervalo de Id's
	 * 
	 * @author Anderson Italo
	 * @date 27/01/2011
	 * @throws ControladorException
	 */
	public Collection pesquisarLocalidadePorIdIntervalo(Integer idLocalidadeInicial, Integer idLocalidadeFinal) throws ControladorException{

		Collection colecaoRetorno = null;
		try{
			colecaoRetorno = this.repositorioLocalidade.pesquisarLocalidadePorIdIntervalo(idLocalidadeInicial, idLocalidadeFinal);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * Inseri um objeto do tipo setor comercial no BD para o Cliente Deso
	 * 
	 * @author Ailton Sousa
	 * @date 18/02/2011
	 * @param setorComercial
	 * @param diasVencimento
	 * @return ID gerado
	 * @throws ControladorException
	 */
	public Integer inserirSetorComercialComDiasVencimento(SetorComercial setorComercial, String[] diasVencimento, Usuario usuarioLogado)
					throws ControladorException{

		Integer retorno = null;

		SetorComercialVencimento setorComercialVencimento = null;

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, setorComercial.getLocalidade()
						.getId()));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercial
						.getCodigo()));

		// Retorna caso já exista um setor comercial com o código
		// informado
		Collection colecaoPesquisa = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			// Setor comercial já cadastrado
			throw new ControladorException("atencao.pesquisa.setor_comercial_ja_cadastrado", null, "" + setorComercial.getCodigo(),
							setorComercial.getLocalidade().getDescricao());
		}

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SETOR_COMERCIAL_INSERIR, setorComercial
						.getCodigo(), setorComercial.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_SETOR_COMERCIAL_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		setorComercial.setOperacaoEfetuada(operacaoEfetuada);
		setorComercial.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(setorComercial);
		// Fim - Registrando as transações

		retorno = (Integer) this.getControladorUtil().inserir(setorComercial);
		setorComercial.setId(retorno);

		// Trata e Insere os Dias de Vencimento na Entidade Setor_Comercial_Vencimento
		if(diasVencimento != null && diasVencimento.length >= 1 && !diasVencimento[0].equals("")){

			setorComercial.setId(retorno);

			for(int i = 0; i < diasVencimento.length; i++){

				short diaVencimento = Short.parseShort(diasVencimento[i]);

				setorComercialVencimento = new SetorComercialVencimento();
				setorComercialVencimento.setDiaVencimento(diaVencimento);
				setorComercialVencimento.setLocalidade(setorComercial.getLocalidade());
				setorComercialVencimento.setSetorComercial(setorComercial);
				setorComercialVencimento.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
				setorComercialVencimento.setUltimaAlteracao(new Date());

				// Início - Registrando as transações
				RegistradorOperacao registradorOperacao2 = new RegistradorOperacao(Operacao.OPERACAO_SETOR_COMERCIAL_VENCIMENTO_INSERIR,
								setorComercialVencimento.getSetorComercial().getCodigo(), setorComercialVencimento.getId(),
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao2 = new Operacao();
				operacao2.setId(Operacao.OPERACAO_SETOR_COMERCIAL_VENCIMENTO_INSERIR);

				OperacaoEfetuada operacaoEfetuada2 = new OperacaoEfetuada();
				operacaoEfetuada2.setOperacao(operacao2);

				setorComercialVencimento.setOperacaoEfetuada(operacaoEfetuada2);
				setorComercialVencimento.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao2.registrarOperacao(setorComercialVencimento);
				// Fim - Registrando as transações

				this.getControladorUtil().inserir(setorComercialVencimento);
			}
		}

		return retorno;
	}

	/**
	 * Inserir Localidade
	 * 
	 * @author Hebert Falcão
	 * @date 21/02/2011
	 * @param localidade
	 * @param usuario
	 * @throws ControladorException
	 */
	public Integer inserirLocalidade(Localidade localidade, Usuario usuario, Concessionaria concessionaria) throws ControladorException{

		localidade.setUltimaAlteracao(new Date());
		localidade.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_LOCALIDADE_INSERIR, localidade.getId(),
						localidade.getId(), new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_LOCALIDADE_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		localidade.setOperacaoEfetuada(operacaoEfetuada);
		localidade.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(localidade);
		// Fim - Registrando as transações

		Integer idLocalidade = (Integer) getControladorUtil().inserir(localidade);
		localidade.setId(idLocalidade);

		// Inclusão ConcessionariaLocalidade
		ConcessionariaLocalidade concessionariaLocalidade = new ConcessionariaLocalidade();
		concessionariaLocalidade.setConcessionaria(concessionaria);
		concessionariaLocalidade.setLocalidade(localidade);
		Date dataAtual = new Date();
		concessionariaLocalidade.setDataVigenciaInicio(dataAtual);
		concessionariaLocalidade.setUltimaAlteracao(dataAtual);

		getControladorUtil().inserir(concessionariaLocalidade);

		return idLocalidade;
	}

	/**
	 * Remover Localidade
	 * 
	 * @author Hebert Falcão
	 * @date 21/02/2011
	 * @param ids
	 * @param usuario
	 * @throws ControladorException
	 */
	public void removerLocalidade(String[] ids, Usuario usuario) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			FiltroLocalidade filtro = new FiltroLocalidade();
			filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, Localidade.class.getName());

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoRetorno);


			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorLocalidade = new RegistradorOperacao(Operacao.OPERACAO_LOCALIDADE_REMOVER, localidade.getId(),
							localidade.getId(), new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_LOCALIDADE_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			localidade.setOperacaoEfetuada(operacaoEfetuada);
			localidade.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorLocalidade.registrarOperacao(localidade);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			this.getControladorUtil().remover(localidade);

		}
	}


	/**
	 * Consulta que vai retornar uma coleção com todos os ids de setor comercial ativos.
	 * Será usada na execução da Geração de Ação de Cobrança.
	 * 
	 * @author Ailton Sousa
	 * @date 27/10/2011
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterTodosIDsSetorComercial() throws ControladorException{

		try{
			return repositorioSetorComercial.obterTodosIDsSetorComercial();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * @author Diogo Monteiro
	 * @date 04/06/2012
	 * @return
	 * @throws ControladorException
	 */
	public List<Integer> consultarSetoresComerciaisAvisoCorte(Integer idFaturamentoAtvCron) throws ControladorException{

		try{
			return repositorioLocalidade.consultarSetoresComerciaisAvisoCorte(idFaturamentoAtvCron);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta lista de localidades a partir do filtro de localidade e na localização da
	 * quantidade de registro.
	 * 
	 * @param filtro filtro de localidade com atributos de filtro
	 * @param pageOffset indicador da localização de quantidade de registros na consulta.
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Localidade> pesquisarLocalidadePorFiltro(FiltroLocalidade filtro, Integer pageOffset) throws ControladorException{

		try{
			return repositorioLocalidade.pesquisarLocalidadePorFiltro(filtro, pageOffset);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * consultar vinculo localidade e faturamento grupo
	 * 
	 * @author Felipe Rosacruz
	 * @param idFaturamentoGrupo
	 * @param idLocalidade
	 * @date 04/10/2013
	 * @throws ControladorException
	 */
	public boolean consultarVinculoLocalidadeFaturamentoGrupo(Integer idFaturamentoGrupo, Integer idLocalidade) throws ControladorException{

		try{
			boolean retorno = false;
			if(!Util.isVazioOrNulo(repositorioLocalidade.pesquisarLocalidadePorIdEFaturamentoGrupo(idFaturamentoGrupo, idLocalidade))){
				retorno = true;
			}
			return retorno;
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * consultar vinculo setor Comercial e faturamento grupo
	 * 
	 * @author Felipe Rosacruz
	 * @param idFaturamentoGrupo
	 * @param cdSetorComercial
	 * @date 04/10/2013
	 * @throws ControladorException
	 */
	public boolean consultarvinculoSetorComercialFaturamentoGrupo(Integer idFaturamentoGrupo, Integer cdSetorComercial)
					throws ControladorException{

		try{
			boolean retorno = false;
			if(!Util.isVazioOrNulo(repositorioLocalidade
							.pesquisarSetorComercialPorIdEFaturamentoGrupo(idFaturamentoGrupo, cdSetorComercial))){
				retorno = true;
			}
			return retorno;
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * consultar vinculo rota e faturamento grupo
	 * 
	 * @author Felipe Rosacruz
	 * @param idFaturamentoGrupo
	 * @param cdRota
	 * @date 06/10/2013
	 * @throws ControladorException
	 */
	public boolean consultarvinculoRotaFaturamentoGrupo(Integer idFaturamentoGrupo, Integer cdRota) throws ControladorException{

		try{
			boolean retorno = false;
			if(!Util.isVazioOrNulo(repositorioLocalidade.pesquisarRotaPorIdEFaturamentoGrupo(idFaturamentoGrupo, cdRota))){
				retorno = true;
			}
			return retorno;
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}
}
