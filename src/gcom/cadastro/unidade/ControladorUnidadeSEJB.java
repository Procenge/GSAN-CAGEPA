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

package gcom.cadastro.unidade;

import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocal;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocalHome;
import gcom.atendimentopublico.registroatendimento.EspecificacaoTramite;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.empresa.Empresa;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ControladorUnidadeSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioUnidade repositorioUnidade = null;

	SessionContext sessionContext;

	public void ejbCreate() throws CreateException{

		repositorioUnidade = RepositorioUnidadeHBM.getInstancia();
	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descri��o do m�todo>>
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

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
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

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorRegistroAtendimentoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Obt�m a unidade associada ao usu�rio que estiver efetuando o registro de atendimento
	 * (UNID_ID e UNID_DSUNIDADE da tabela UNIDADE_ORGANIZACIONAL com
	 * UNID_ID=(UNID_ID da tabela USUARIO com USUR_NMLOGIN=
	 * Login do usu�rio que estiver efetuando o registro de atendimento) e UNID_ICABERTURARA=1)
	 * 
	 * @author Raphael Rossiter
	 * @date 25/07/2006
	 * @param login
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeOrganizacionalAberturaRAAtivoUsuario(String loginUsuario) throws ControladorException{

		UnidadeOrganizacional retorno = null;

		try{

			retorno = repositorioUnidade.obterUnidadeOrganizacionalAberturaRAAtivoUsuario(loginUsuario);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a unidade de atendimento n�o tenha autoriza��o para efetuar a abertura de registros de
	 * atendimento
	 * (UNID_ICABERTURARA com o valor correspondente a dois na tabela UNIDADE_ORGANIZACIONAL com
	 * UNID_ID=Id da
	 * Unidade de Atendimento), exibir a mensagem �A unidade <<UNID_NMUNIDADE da tabela
	 * UNIDADE_ORGANIZACIONAL>>
	 * n�o tem autoriza��o para efetuar a abertura de registro de atendimento� e retornar para o
	 * passo
	 * correspondente no fluxo principal.
	 * [FS0004] � Verificar exist�ncia da unidade de atendimento
	 * [FS0033] � Verificar autoriza��o da unidade de atendimento para abertura de registro de
	 * atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 25/07/2006
	 * @param idUnidadeOrganizacional
	 *            , levantarExceptionUnidadeInexistente
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional verificarAutorizacaoUnidadeAberturaRA(Integer idUnidadeOrganizacional,
					boolean levantarExceptionUnidadeInexistente) throws ControladorException{

		UnidadeOrganizacional retorno = null;

		Collection colecaoUnidadeOrganizacional = null;

		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		filtroUnidadeOrganizacional.adicionarCaminhoParaCarregamentoEntidade("meioSolicitacao");

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacional));

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		colecaoUnidadeOrganizacional = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional,
						UnidadeOrganizacional.class.getName());

		if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){

			retorno = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			if(retorno.getIndicadorAberturaRa() == null
							|| retorno.getIndicadorAberturaRa().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){

				throw new ControladorException("atencao.unidade_organizacional_nao_autorizada_registro_atendimento", null, retorno
								.getDescricao());
			}
		}else if(levantarExceptionUnidadeInexistente){

			throw new ControladorException("atencao.label_inexistente", null, "Unidade de Atendimento");
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a unidade destino definida esteja vinculada a uma unidade centralizadora
	 * (UNID_IDCENTRALIZADORA com o
	 * valor diferente de nulo na tabela UNIDADE_ORGANIZACIONAL para UNID_ID=Id da unidade destino),
	 * definir a unidade destino a partir da unidade centralizadora (UNID_ID e UNID_DSUNIDADE da
	 * tabela
	 * UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_IDCENTRALIZADORA).
	 * [FS0018] � Verificar exist�ncia de unidade centralizadora
	 * 
	 * @author Raphael Rossiter
	 * @date 26/07/2006
	 * @param unidadeOrganizacional
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional verificarExistenciaUnidadeCentralizadora(UnidadeOrganizacional unidadeOrganizacional)
					throws ControladorException{

		UnidadeOrganizacional retorno = null;

		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		filtroUnidadeOrganizacional.adicionarCaminhoParaCarregamentoEntidade("unidadeCentralizadora");

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, unidadeOrganizacional.getId()));

		/*
		 * filtroUnidadeOrganizacional.adicionarParametro(new ParametroNaoNulo(
		 * FiltroUnidadeOrganizacional.ID_UNIDADE_CENTRALIZADORA));
		 */

		Collection colecaoUnidadeOrganizacional = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional,
						UnidadeOrganizacional.class.getName());

		if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){

			retorno = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			if(retorno.getUnidadeCentralizadora() != null){

				if(retorno.getUnidadeCentralizadora().getIndicadorTramite() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){
					retorno = retorno.getUnidadeCentralizadora();
				}

			}

		}

		return retorno;
	}

	/**
	 * [UC0373] Inserir Unidade Organizacional
	 * Metodo inser��o da unidade organizacional
	 * [FS0001] � Validar Localidade
	 * [FS0002] � Validar Gerencia Regional
	 * [FS0003] � Verificar exit�ncia da descri��o
	 * [FS0004] � Verificar exit�ncia da sigla
	 * [FS0005] � Validar Empresa
	 * [FS0006] � Validar Unidade Superior
	 * 
	 * @author Raphael Pinto
	 * @date 31/07/2006
	 * @param unidadeOrganizacional
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional, Usuario usuario) throws ControladorException{

		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
		Collection colecaoUnidade = null;

		// Caso 2 - [FS0001] � Validar Localidade
		if(unidadeOrganizacional.getLocalidade() != null){

			String idLocalidade = "" + unidadeOrganizacional.getLocalidade().getId();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID_LOCALIDADE, idLocalidade));

			// Pesquisa de acordo com os par�metros informados no filtro
			colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a cole��o
			if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){
				throw new ControladorException("atencao.inserir_unidade_organizacional_localidade_ja_cadastradado", null, idLocalidade);
			}
		}

		// Caso 2 - [FS0002] � Validar Gerencia Regional
		if(unidadeOrganizacional.getGerenciaRegional() != null){

			String idGerenciaRegional = "" + unidadeOrganizacional.getGerenciaRegional().getId();

			filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.GERENCIAL_REGIONAL,
							idGerenciaRegional));

			// Pesquisa de acordo com os par�metros informados no filtro
			colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a cole��o
			if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){
				throw new ControladorException("atencao.inserir_unidade_organizacional_gerencia_ja_cadastradado", null, idGerenciaRegional);
			}
		}

		// Caso 2 - [FS0003] � Verificar exit�ncia da descri��o
		String descricao = unidadeOrganizacional.getDescricao();

		filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.DESCRICAO, descricao));

		// Pesquisa de acordo com os par�metros informados no filtro
		colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){
			throw new ControladorException("atencao.inserir_unidade_organizacional_descricao_ja_existe", null);
		}

		// Caso 2 - [FS0004] � Verificar exit�ncia da sigla
		if(unidadeOrganizacional.getSigla() != null && !unidadeOrganizacional.getSigla().equals("")){

			String sigla = unidadeOrganizacional.getSigla();

			filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.SIGLA, sigla));

			// Pesquisa de acordo com os par�metros informados no filtro
			colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a cole��o
			if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){
				throw new ControladorException("atencao.inserir_unidade_organizacional_sigla_ja_existe", null);
			}
		}

		// Caso 2 - [FS0005] � Validar Empresa
		String codigoUnidadeTipo = unidadeOrganizacional.getUnidadeTipo().getCodigoTipo();
		if(codigoUnidadeTipo != null && codigoUnidadeTipo.equals(UnidadeTipo.UNIDADE_TIPO_TERCERIZADO)){
			Empresa empresa = unidadeOrganizacional.getEmpresa();

			if(empresa.getIndicadorEmpresaPrincipal().equals(ConstantesSistema.SIM)){
				throw new ControladorException("atencao.inserir_unidade_organizacional_terceirizada_associada", null);
			}
		}

		// Caso 2,3 - [FS0006] � Validar Unidade Superior
		Integer nivelOrganizacional = unidadeOrganizacional.getUnidadeTipo().getNivel();
		UnidadeOrganizacional unidadeSuperior = unidadeOrganizacional.getUnidadeSuperior();

		if(nivelOrganizacional != null){

			if(unidadeSuperior != null && unidadeSuperior.getUnidadeTipo().getNivel() != null){

				Integer nivelSuperior = unidadeSuperior.getUnidadeTipo().getNivel();

				// Caso 3
				if(nivelSuperior.intValue() > nivelOrganizacional.intValue()){
					throw new ControladorException("atencao.inserir_unidade_organizacional_nivel_hierarquico", null);
					// Caso 4
				}else if(nivelSuperior.intValue() < (nivelOrganizacional.intValue() - 1)){
					throw new ControladorException("atencao.inserir_unidade_organizacional_nivel_hierarquico", null);
				}
			}
		}

		// ------------ REGISTRAR TRANSA��O UNIDADE ORGANIZACIONAL----------------------------
		RegistradorOperacao registradorOperacaoUnidade = new RegistradorOperacao(Operacao.OPERACAO_UNIDADE_ORGANIZACIONAL_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoUnidade = new Operacao();
		operacaoUnidade.setId(Operacao.OPERACAO_UNIDADE_ORGANIZACIONAL_INSERIR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoUnidade);

		unidadeOrganizacional.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		unidadeOrganizacional.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoUnidade.registrarOperacao(unidadeOrganizacional);
		// ------------ REGISTRAR TRANSA��O UNIDADE ORGANIZACIONAL----------------------------

		Object retorno = this.getControladorUtil().inserir(unidadeOrganizacional);

		// #### L�gica de inclus�o da Unidade Organizacional Hierarquia ####

		// Seta o Id no objeto que foi inclu�do
		Integer idUnidade = (Integer) retorno;
		unidadeOrganizacional.setId(idUnidade);

		Integer idUnidadeSuperior = null;
		if(unidadeSuperior != null){
			idUnidadeSuperior = unidadeSuperior.getId();
		}

		// Verifica todas os superiores da unidade superior passada
		ArrayList<Integer> superiores = this.recuperarIdsUnidadeOrganizacionalSuperior(idUnidadeSuperior);

		// Incluindo uma hierarquia de todos os superiores da unidade superior passada
		UnidadeOrganizacionalHierarquia unidadeOrganizacionalHierarquia = null;

		if(superiores != null && !superiores.isEmpty()){
			for(Integer id : superiores){
				UnidadeOrganizacional unidadeSuperiorAux = new UnidadeOrganizacional();
				unidadeSuperiorAux.setId(id);

				unidadeOrganizacionalHierarquia = new UnidadeOrganizacionalHierarquia();
				unidadeOrganizacionalHierarquia.setUnidade(unidadeOrganizacional);
				unidadeOrganizacionalHierarquia.setUnidadeSuperior(unidadeSuperiorAux);
				unidadeOrganizacionalHierarquia.setUltimaAlteracao(new Date());

				this.getControladorUtil().inserir(unidadeOrganizacionalHierarquia);
			}
		}

		// Incluindo uma hierarquia com a unidade superior passada
		unidadeOrganizacionalHierarquia = new UnidadeOrganizacionalHierarquia();
		unidadeOrganizacionalHierarquia.setUnidade(unidadeOrganizacional);
		unidadeOrganizacionalHierarquia.setUnidadeSuperior(unidadeSuperior);
		unidadeOrganizacionalHierarquia.setUltimaAlteracao(new Date());

		this.getControladorUtil().inserir(unidadeOrganizacionalHierarquia);

		return retorno;
	}

	/**
	 * Recuperar ids das unidades organizacionais superiores de uma determinada unidade
	 * 
	 * @author Hebert Falc�o
	 * @date 11/04/2011
	 */
	private ArrayList<Integer> recuperarIdsUnidadeOrganizacionalSuperior(Integer idUnidade) throws ControladorException{

		ArrayList<Integer> retorno = new ArrayList<Integer>();

		Integer id = idUnidade;

		while(id != null){
			UnidadeOrganizacional unidadeOrganizacional = this.pesquisarUnidadeOrganizacional(id);

			UnidadeOrganizacional unidadeSuperior = unidadeOrganizacional.getUnidadeSuperior();

			if(unidadeSuperior != null && !unidadeSuperior.getId().equals(id)){
				id = unidadeSuperior.getId();
				retorno.add(id);
			}else{
				id = null;
			}
		}

		return retorno;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 05/08/2006
	 * @param unidadeOrganizacional
	 * @return Collection<UnidadeOrganizacional> unidades subordinadas
	 * @throws ErroRepositorioException
	 */
	public Collection<UnidadeOrganizacional> recuperarUnidadesSubordinadasPorUnidadeSuperior(UnidadeOrganizacional unidadeOrganizacional)
					throws ControladorException{

		Collection<UnidadeOrganizacional> colecaoUnidadeSubordinadas = null;
		try{
			colecaoUnidadeSubordinadas = repositorioUnidade.recuperarUnidadesSubordinadasPorUnidadeSuperior(unidadeOrganizacional);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return colecaoUnidadeSubordinadas;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * Caso exista registro de atendimento que est�o na unidade atual informada
	 * (existe ocorr�ncia na tabela REGISTRO_ATENDIMENTO com TRAMITE=Id da Unidade Atual
	 * e maior TRAM_TMTRAMITE)
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param unidadeOrganizacional
	 * @return RegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional recuperaUnidadeAtualPorRA(RegistroAtendimento registroAtendimento) throws ControladorException{

		UnidadeOrganizacional unidadeAtual = null;
		try{
			unidadeAtual = repositorioUnidade.recuperaUnidadeAtualPorRA(registroAtendimento);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return unidadeAtual;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * [FS007] Verificar exist�ncia de unidades subordinadas
	 * 
	 * @author Leonardo Regis
	 * @date 05/08/2006
	 * @param unidadeOrganizacional
	 * @throws ControladorException
	 */
	public void verificarExistenciaUnidadesSubordinadas(UnidadeOrganizacional unidadeOrganizacional) throws ControladorException{

		int qtdeUnidadesSubordinadas = 0;
		try{
			qtdeUnidadesSubordinadas = repositorioUnidade.consultarTotalUnidadesSubordinadasPorUnidadeSuperior(unidadeOrganizacional);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		if(qtdeUnidadesSubordinadas == 0){
			throw new ControladorException("atencao.filtrar_ra_sem_unidades_subordinadas");
		}
	}

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * Caso a unidade destino informada n�o possa receber registros de
	 * atendimento (UNID_ICTRAMITE=2 na tabela UNIDADE_ORGANIZACIONAL com
	 * UNID_ID=Id da unidade destino informada).
	 * [FS0013] - Verificar possibilidade de encaminhamento para a unidade
	 * destino
	 * 
	 * @author Ana Maria
	 * @date 03/09/2006
	 * @param idUnidadeOrganizacional
	 * @return void
	 * @throws ControladorException
	 */
	public void verificaPossibilidadeEncaminhamentoUnidadeDestino(Integer idUnidadeDestino) throws ControladorException{

		try{
			Short idTramiteUnidade = repositorioUnidade.verificaTramiteUnidade(idUnidadeDestino);
			if(idTramiteUnidade != null && idTramiteUnidade == ConstantesSistema.INDICADOR_USO_DESATIVO.shortValue()){

				throw new ControladorException("atencao.unidade_destino_nao_possivel_encaminhamento");
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<UnidadeOrganizacional> pesquisarUnidadeOrganizacionalPorUnidade(Integer unidadeLotacao) throws ControladorException{

		try{
			return repositorioUnidade.pesquisarUnidadeOrganizacionalPorUnidade(unidadeLotacao);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Pesquisa a Unidade Organizacional do Usu�rio Logado
	 * 
	 * @author Rafael Corr�a
	 * @date 25/09/2006
	 * @param id
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional pesquisarUnidadeUsuario(Integer idUsuario) throws ControladorException{

		try{
			return repositorioUnidade.pesquisarUnidadeUsuario(idUsuario);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0375] Manter Unidade Organizacional
	 * 
	 * @author Ana Maria
	 * @date 24/11/2006
	 * @param unidadeOrganizacional
	 * @throws ControladorException
	 */
	public void atualizarUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional, Usuario usuario) throws ControladorException{

		Integer idUnidade = unidadeOrganizacional.getId();

		validaAtualizacaoUnidadeOrganizacional(unidadeOrganizacional);

		// Seta o filtro para buscar a unidade organizacional na base
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidade));

		// Procura unidade organizacional na base
		Collection colecaoUnidadeNaBase = getControladorUtil()
						.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());
		UnidadeOrganizacional unidadeNaBase = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeNaBase);

		if(unidadeNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se a unidade organizacional j� foi atualizado por outro usu�rio durante esta
		// atualiza��o
		if(unidadeNaBase.getUltimaAlteracao().after(unidadeOrganizacional.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// ------------ REGISTRAR TRANSA��O UNIDADE ORGANIZACIONAL----------------------------
		RegistradorOperacao registradorOperacaoUnidade = new RegistradorOperacao(Operacao.OPERACAO_UNIDADE_ORGANIZACIONAL_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoUnidade = new Operacao();
		operacaoUnidade.setId(Operacao.OPERACAO_UNIDADE_ORGANIZACIONAL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoUnidade);

		unidadeOrganizacional.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		unidadeOrganizacional.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoUnidade.registrarOperacao(unidadeOrganizacional);
		// ------------ REGISTRAR TRANSA��O UNIDADE ORGANIZACIONAL----------------------------

		try{
			// Atualiza a unidadeOrganizacional
			// repositorioUnidade.atualizarUnidadeOrganizacional(unidadeOrganizacional);
			this.getControladorUtil().atualizar(unidadeOrganizacional);

			// #### L�gica de altera��o da Unidade Organizacional Hierarquia ####

			// Id da nova unidade superior
			UnidadeOrganizacional unidadeSuperior = unidadeOrganizacional.getUnidadeSuperior();

			Integer idUnidadeSuperior = null;
			if(unidadeSuperior != null){
				idUnidadeSuperior = unidadeSuperior.getId();
			}

			// Id da antiga unidade superior
			UnidadeOrganizacional unidadeSuperiorNaBase = unidadeNaBase.getUnidadeSuperior();

			Integer idUnidadeSuperiorNaBase = null;
			if(unidadeSuperiorNaBase != null){
				idUnidadeSuperiorNaBase = unidadeSuperiorNaBase.getId();
			}

			if(idUnidadeSuperior != null && idUnidadeSuperiorNaBase != null && !idUnidadeSuperior.equals(idUnidadeSuperiorNaBase)){
				// Cole��o de unidade organizacional hierarquia que tenham como superior o superior
				// da unidade passada
				Collection<UnidadeOrganizacionalHierarquia> colecaoUnidadeOrganizacionalHierarquia = repositorioUnidade
								.pesquisarUnidadeOrganizacionalHierarquiaSuperior(idUnidade);

				// Removendo as hierarquias
				if(colecaoUnidadeOrganizacionalHierarquia != null && !colecaoUnidadeOrganizacionalHierarquia.isEmpty()){
					for(UnidadeOrganizacionalHierarquia unidadeOrganizacionalHierarquia : colecaoUnidadeOrganizacionalHierarquia){
						getControladorUtil().remover(unidadeOrganizacionalHierarquia);
					}
				}

				if(idUnidadeSuperior == null){
					// Removendo a unidade superior (null)

					// Incluindo uma hierarquia com o superior null
					UnidadeOrganizacionalHierarquia unidadeOrganizacionalHierarquia = new UnidadeOrganizacionalHierarquia();
					unidadeOrganizacionalHierarquia.setUnidade(unidadeOrganizacional);
					unidadeOrganizacionalHierarquia.setUnidadeSuperior(unidadeSuperior);
					unidadeOrganizacionalHierarquia.setUltimaAlteracao(new Date());

					this.getControladorUtil().inserir(unidadeOrganizacionalHierarquia);
				}else{
					// Retorna todos os registros que possuem a unidade atual como superior
					Collection<UnidadeOrganizacionalHierarquia> colecaoUnidadeOrganizacionalHierarquiaFilhas = repositorioUnidade
									.pesquisarUnidadeOrganizacionalHierarquiaPeloSuperior(idUnidade);

					// Adiciona a unidade organizacional atual na cole��o de filhas
					UnidadeOrganizacionalHierarquia unidadeOrganizacionalHierarquia = new UnidadeOrganizacionalHierarquia();
					unidadeOrganizacionalHierarquia.setUnidade(unidadeOrganizacional);
					colecaoUnidadeOrganizacionalHierarquiaFilhas.add(unidadeOrganizacionalHierarquia);

					// Retorna todos os registros que s�o superior a unidade superior informada
					Collection<UnidadeOrganizacionalHierarquia> colecaoUnidadeOrganizacionalHierarquiaSuperiores = repositorioUnidade
									.pesquisarUnidadeOrganizacionalHierarquiaPelaUnidade(idUnidadeSuperior);

					// Adiciona a unidade organizacional superior na cole��o de superiores
					unidadeOrganizacionalHierarquia = new UnidadeOrganizacionalHierarquia();
					unidadeOrganizacionalHierarquia.setUnidadeSuperior(unidadeSuperior);
					colecaoUnidadeOrganizacionalHierarquiaSuperiores.add(unidadeOrganizacionalHierarquia);

					for(UnidadeOrganizacionalHierarquia unidadeOrganizacionalHierarquiaFilha : colecaoUnidadeOrganizacionalHierarquiaFilhas){
						UnidadeOrganizacional unidadeAux = unidadeOrganizacionalHierarquiaFilha.getUnidade();

						for(UnidadeOrganizacionalHierarquia unidadeOrganizacionalHierarquiaSuperior : colecaoUnidadeOrganizacionalHierarquiaSuperiores){
							UnidadeOrganizacional unidadeSuperiorAux = unidadeOrganizacionalHierarquiaSuperior.getUnidadeSuperior();

							if(unidadeSuperiorAux != null){
								UnidadeOrganizacionalHierarquia unidadeOrganizacionalHierarquiaAux = new UnidadeOrganizacionalHierarquia();
								unidadeOrganizacionalHierarquiaAux.setUnidade(unidadeAux);
								unidadeOrganizacionalHierarquiaAux.setUnidadeSuperior(unidadeSuperiorAux);
								unidadeOrganizacionalHierarquiaAux.setUltimaAlteracao(new Date());

								// Para cada filho cria uma referencia ao superior da unidade
								// superior
								this.getControladorUtil().inserir(unidadeOrganizacionalHierarquiaAux);
							}
						}
					}
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	private void validaAtualizacaoUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) throws ControladorException{

		Collection colecaoUnidade = null;
		// [FS0001] � Verificar exit�ncia da descri��o
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.DESCRICAO, unidadeOrganizacional
						.getDescricao()));

		// Pesquisa de acordo com os par�metros informados no filtro
		colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){
			UnidadeOrganizacional unidadePesquisada = (UnidadeOrganizacional) ((List) colecaoUnidade).get(0);

			if(!unidadeOrganizacional.getId().equals(unidadePesquisada.getId())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.inserir_unidade_organizacional_descricao_ja_existe", null);
			}
		}

		// [FS0002] � Verificar exit�ncia da sigla
		if(unidadeOrganizacional.getSigla() != null && !unidadeOrganizacional.getSigla().equals("")){

			String sigla = unidadeOrganizacional.getSigla();

			filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.SIGLA, sigla));

			// Pesquisa de acordo com os par�metros informados no filtro
			colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a cole��o
			if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){
				UnidadeOrganizacional unidadePesquisada = (UnidadeOrganizacional) ((List) colecaoUnidade).get(0);

				if(!unidadeOrganizacional.getId().equals(unidadePesquisada.getId())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.inserir_unidade_organizacional_sigla_ja_existe", null);
				}
			}
		}

		// [FS0003] � Validar Empresa
		String codigoUnidadeTipo = unidadeOrganizacional.getUnidadeTipo().getCodigoTipo();
		if(codigoUnidadeTipo != null && codigoUnidadeTipo.equals(UnidadeTipo.UNIDADE_TIPO_TERCERIZADO)){
			Empresa empresa = unidadeOrganizacional.getEmpresa();

			if(empresa.getIndicadorEmpresaPrincipal().equals(ConstantesSistema.SIM)){
				throw new ControladorException("atencao.inserir_unidade_organizacional_terceirizada_associada", null);
			}
		}

		// [FS0006] - Validar Unidade de Esgoto
		try{
			String descricaoDivisao = repositorioUnidade.verificarUnidadeEsgoto(unidadeOrganizacional.getId());

			if(descricaoDivisao != null && unidadeOrganizacional.getIndicadorEsgoto() == ConstantesSistema.NAO){
				throw new ControladorException("atencao.unidade.esgoto", null, descricaoDivisao);
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		// [FS0007] - Validar Unidade Aceita Tramita��o
		// try{
		// String descricaoSolicitacaoTipoEspecificacao =
		// repositorioUnidade.verificarUnidadeTramitacao(unidadeOrganizacional.getId());
		//
		// if(descricaoSolicitacaoTipoEspecificacao != null &&
		// unidadeOrganizacional.getIndicadorTramite() == ConstantesSistema.NAO){
		// throw new ControladorException("atencao.unidade.tramitacao", null);
		// }
		// }catch(ErroRepositorioException ex){
		// ex.printStackTrace();
		// throw new ControladorException("erro.sistema", ex);
		// }

		/**
		 * Filtro para EspecificacaoTramite
		 */
		EspecificacaoTramite especificacaoTramiteAuxiliar = new EspecificacaoTramite();
		especificacaoTramiteAuxiliar.setUnidadeOrganizacionalDestino(unidadeOrganizacional);

		// S� pesquisa os ativos
		especificacaoTramiteAuxiliar.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		UnidadeOrganizacional unidadeOrganizacionalRetorno = null;

		Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = this.getControladorRegistroAtendimento()
						.obterUnidadeDestinoPorEspecificacao(especificacaoTramiteAuxiliar, true);

		if(!Util.isVazioOrNulo(colecaoUnidadeOrganizacional)){
			unidadeOrganizacionalRetorno = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);
		}

		/**
		 * Fim Filtro para EspecificacaoTramite
		 */

		if(unidadeOrganizacionalRetorno != null && unidadeOrganizacional.getIndicadorTramite() == ConstantesSistema.NAO){
			throw new ControladorException("atencao.unidade.tramitacao", null);
		}

		if(unidadeOrganizacional.getUnidadeSuperior() != null && !unidadeOrganizacional.getUnidadeSuperior().equals("")){

			// [FS0004] � Validar Unidade Superior
			Integer nivelOrganizacional = unidadeOrganizacional.getUnidadeTipo().getNivel();
			UnidadeOrganizacional unidadeSuperior = unidadeOrganizacional.getUnidadeSuperior();

			if(unidadeSuperior == null || unidadeSuperior.equals("")){
				throw new ControladorException("atencao.unidade.organizacional.inexistente", null);
			}

			if(nivelOrganizacional != null){

				if(unidadeSuperior != null && unidadeSuperior.getUnidadeTipo().getNivel() != null){

					Integer nivelSuperior = unidadeSuperior.getUnidadeTipo().getNivel();

					// Caso 3
					if(nivelSuperior.intValue() > nivelOrganizacional.intValue()){
						throw new ControladorException("atencao.inserir_unidade_organizacional_nivel_hierarquico", null);
						// Caso 4
					}else if(nivelSuperior.intValue() < (nivelOrganizacional.intValue() - 1)){
						throw new ControladorException("atencao.inserir_unidade_organizacional_nivel_hierarquico", null);
					}
				}
			}
		}
	}

	/**
	 * [UC0375] Manter Unidade Organizacional
	 * 
	 * @author Ana Maria
	 * @date 28/11/2006
	 * @param unidadeOrganizacional
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional pesquisarUnidadeOrganizacional(Integer idUnidadeOrganizacional) throws ControladorException{

		UnidadeOrganizacional retorno = null;
		try{
			retorno = repositorioUnidade.pesquisarUnidadeOrganizacional(idUnidadeOrganizacional);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0374] Filtrar Unidade Organizacional
	 * Pesquisa as unidades organizacionais com os condicionais informados
	 * filtroUnidadeOrganizacional
	 * 
	 * @author Ana Maria
	 * @date 30/11/2006
	 * @param filtro
	 * @return Collection
	 */
	public Collection pesquisarUnidadeOrganizacionalFiltro(FiltroUnidadeOrganizacional filtroUnidadeOrganizacional, Integer numeroPagina)
					throws ControladorException{

		Collection colecaoObject = new ArrayList();

		Collection colecaoUnidadeOrganizacional = new ArrayList();

		try{

			colecaoObject = repositorioUnidade.pesquisarUnidadeOrganizacionalFiltro(filtroUnidadeOrganizacional, numeroPagina);

			Iterator iteratorObject = colecaoObject.iterator();
			while(iteratorObject.hasNext()){
				Object[] arrayObject = (Object[]) iteratorObject.next();
				if(arrayObject != null){

					UnidadeOrganizacional unidade = new UnidadeOrganizacional();

					// id unidade organizacional
					if(arrayObject[0] != null){
						unidade.setId((Integer) arrayObject[0]);
					}
					// instancia tipo unidade
					UnidadeTipo unidadeTipo = new UnidadeTipo();
					// id da tipo unidade
					if(arrayObject[1] != null){
						unidadeTipo.setDescricao((String) arrayObject[1]);
						if(arrayObject[2] != null){
							unidadeTipo.setNivel((Integer) arrayObject[2]);
						}
						unidade.setUnidadeTipo(unidadeTipo);
					}

					unidade.setDescricao((String) arrayObject[3]);

					if(arrayObject[4] != null){
						unidade.setIndicadorAberturaRa((Short) arrayObject[4]);
					}

					if(arrayObject[5] != null){
						unidade.setIndicadorTramite((Short) arrayObject[5]);
					}

					colecaoUnidadeOrganizacional.add(unidade);
				}
			}
		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		return colecaoUnidadeOrganizacional;
	}

	public Integer pesquisarUnidadeOrganizacionalFiltroCount(FiltroUnidadeOrganizacional filtroUnidadeOrganizacional)
					throws ControladorException{

		try{
			return repositorioUnidade.pesquisarUnidadeOrganizacionalFiltroCount(filtroUnidadeOrganizacional);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Pesquisar unidade organizacional por localidade
	 * 
	 * @author S�vio Luiz
	 * @date 03/01/2007
	 * @param idLocalidade
	 * @return String
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional pesquisarUnidadeOrganizacionalLocalidade(Integer idLocalidade) throws ControladorException{

		try{
			return repositorioUnidade.pesquisarUnidadeOrganizacionalLocalidade(idLocalidade);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Remover Unidade Organizacional
	 * 
	 * @author Hebert Falc�o
	 * @date 14/04/2011
	 */
	public void removerUnidadeOrganizacional(String[] ids, Usuario usuario) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			Integer id = new Integer(ids[i]);

			FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
			filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, id));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoRetorno);

			// ------------ REGISTRAR TRANSA��O ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_UNIDADE_ORGANIZACIONAL_REMOVER,
							new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_UNIDADE_ORGANIZACIONAL_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			unidadeOrganizacional.setOperacaoEfetuada(operacaoEfetuada);

			registradorOperacao.registrarOperacao(unidadeOrganizacional);
			// ------------ REGISTRAR TRANSA��O ----------------

			try{
				// #### L�gica de remo��o da Unidade Organizacional Hierarquia ####

				Collection<UnidadeOrganizacionalHierarquia> colecaoUnidadeOrganizacionalHierarquia = repositorioUnidade
								.pesquisarUnidadeOrganizacionalHierarquiaPelaUnidade(id);

				for(UnidadeOrganizacionalHierarquia unidadeOrganizacionalHierarquia : colecaoUnidadeOrganizacionalHierarquia){
					getControladorUtil().remover(unidadeOrganizacionalHierarquia);
				}
			}catch(ErroRepositorioException ex){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", ex);
			}

			this.getControladorUtil().remover(unidadeOrganizacional);
		}
	}

}
