/*
 * Copyright (C) 2007-2007 the GSAN  Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place  Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN  Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.cadastro;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.arrecadacao.debitoautomatico.FiltroDebitoAutomatico;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocal;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocalHome;
import gcom.atendimentopublico.bean.DadosLigacoesBoletimCadastroHelper;
import gcom.atendimentopublico.ligacaoagua.ControladorLigacaoAguaLocal;
import gcom.atendimentopublico.ligacaoagua.ControladorLigacaoAguaLocalHome;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.registroatendimento.FiltroMeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.batch.*;
import gcom.cadastro.aguaparatodos.FiltroImovelAguaParaTodos;
import gcom.cadastro.aguaparatodos.ImovelAguaParaTodos;
import gcom.cadastro.cliente.*;
import gcom.cadastro.cliente.bean.ClienteEmitirBoletimCadastroHelper;
import gcom.cadastro.dadocensitario.FiltroLocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.FiltroMunicipioDadosCensitario;
import gcom.cadastro.dadocensitario.LocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.MunicipioDadosCensitario;
import gcom.cadastro.empresa.IRepositorioEmpresa;
import gcom.cadastro.empresa.RepositorioEmpresaHBM;
import gcom.cadastro.endereco.*;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.*;
import gcom.cadastro.imovel.bean.ImovelArquivoAgenciaVirtualWebHelper;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.FiltroNacionalFeriado;
import gcom.cadastro.sistemaparametro.FiltroSistemaAlteracaoHistorico;
import gcom.cadastro.sistemaparametro.SistemaAlteracaoHistorico;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.sistemaparametro.bean.DadosEnvioEmailHelper;
import gcom.cadastro.sistemaparametro.bean.FeriadoHelper;
import gcom.cadastro.unidade.ControladorUnidadeLocal;
import gcom.cadastro.unidade.ControladorUnidadeLocalHome;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cadastro.unidadeoperacional.FiltroUnidadeOperacional;
import gcom.cadastro.unidadeoperacional.UnidadeOperacional;
import gcom.cobranca.*;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.EmitirDocumentoCobrancaBoletimCadastroHelper;
import gcom.cobranca.bean.FiltroRelatorioDebitoPorResponsavelHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.campanhapremiacao.*;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.*;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.faturamento.consumofaixaareacategoria.FiltroConsumoFaixaAreaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.provisaoreceita.ProvisaoReceita;
import gcom.gui.util.FiltroRecursosExternos;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.operacional.FiltroSetorAbastecimento;
import gcom.operacional.SetorAbastecimento;
import gcom.relatorio.cadastro.RelatorioAtualizacaoCadastralHelper;
import gcom.relatorio.cadastro.imovel.*;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.seguranca.transacao.ControladorTransacaoLocal;
import gcom.seguranca.transacao.ControladorTransacaoLocalHome;
import gcom.util.*;
import gcom.util.email.ServicosEmail;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.cadastro.ExecutorParametrosCadastro;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;

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

public class ControladorCadastroSEJB
				implements SessionBean, Parametrizacao {

	public static final String RELATORIO_AGENCIA_VIRTUAL_IMOVEIS = "RELATORIOAGENCIAVIRTUALIMOVEIS";

	public static final String RELATORIO_AGENCIA_VIRTUAL_DEBITOS = "RELATORIOAGENCIAVIRTUALDEBITOS";

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	private IRepositorioEmpresa repositorioEmpresa = null;

	private IRepositorioCadastro repositorioCadastro = null;

	private IRepositorioSetorComercial repositorioSetorComercial = null;

	private IRepositorioCobranca repositorioCobranca = null;

	private IRepositorioClienteImovel repositorioClienteImovel = null;

	private IRepositorioImovel repositorioImovel = null;

	private IRepositorioFaturamento repositorioFaturamento = null;

	private IRepositorioLocalidade repositorioLocalidade = null;

	private static final Logger LOGGER = Logger.getLogger(ControladorCadastroSEJB.class);

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioEmpresa = RepositorioEmpresaHBM.getInstancia();
		repositorioCadastro = RepositorioCadastroHBM.getInstancia();
		repositorioSetorComercial = RepositorioSetorComercialHBM.getInstancia();
		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();
		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
		repositorioLocalidade = RepositorioLocalidadeHBM.getInstancia();
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

	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosCadastro.getInstancia();
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

	private ControladorUnidadeLocal getControladorUnidade(){

		ControladorUnidadeLocalHome localHome = null;
		ControladorUnidadeLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUnidadeLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UNIDADE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	private ControladorLigacaoAguaLocal getControladorLigacaoAgua(){

		ControladorLigacaoAguaLocalHome localHome = null;
		ControladorLigacaoAguaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorLigacaoAguaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LIGACAO_AGUA_SEJB);
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
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
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
	 * Retorna o valor de controladorAtendimentoPublico
	 * 
	 * @return O valor de controladorAtendimentoPublico
	 */
	private ControladorAtendimentoPublicoLocal getControladorAtendimentoPublico(){

		ControladorAtendimentoPublicoLocalHome localHome = null;
		ControladorAtendimentoPublicoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorAtendimentoPublicoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ATENDIMENTO_PUBLICO_SEJB);
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
	 * Retorna o valor de controladorCliente
	 * 
	 * @return O valor de controladorImovel
	 */
	private ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
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
	 * Retorna o valor do ControladorBatch
	 * 
	 * @author Rafael Corrêa
	 * @date 31/05/2007
	 * @return O valor de ControladorBatch
	 */
	private ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
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

	protected ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
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

	private ControladorTransacaoLocal getControladorTransacao(){

		ControladorTransacaoLocalHome localHome = null;
		ControladorTransacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorTransacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_TRANSACAO_SEJB);
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

	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
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

	private ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
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
	 * Permite inserir um Sistema Alteracao Historico
	 * [UC0217] Inserir Sistema Alteracao Historico
	 * 
	 * @author Thiago Tenório
	 * @date 30/03/2006
	 */
	public Integer inserirHistoricoAlteracaoSistema(SistemaAlteracaoHistorico sistemaAlteracaoHistorico) throws ControladorException{

		FiltroSistemaAlteracaoHistorico filtroSistemaAlteracaoHistorico = new FiltroSistemaAlteracaoHistorico();
		filtroSistemaAlteracaoHistorico.adicionarParametro(new ParametroSimples(FiltroSistemaAlteracaoHistorico.NOME,
						sistemaAlteracaoHistorico.getNome()));

		Collection colecaoSistemaAlteracaoHistorico = getControladorUtil().pesquisar(filtroSistemaAlteracaoHistorico,
						SistemaAlteracaoHistorico.class.getName());

		if(colecaoSistemaAlteracaoHistorico != null && !colecaoSistemaAlteracaoHistorico.isEmpty()){
			throw new ControladorException("atencao.numero_resolucao_ja_existente");
		}

		sistemaAlteracaoHistorico.setUltimaAlteracao(new Date());

		Integer id = (Integer) getControladorUtil().inserir(sistemaAlteracaoHistorico);

		return id;

	}

	/**
	 * Permite inserir uma Gerencia Regional
	 * [UC0217] Inserir Gerencia Regional
	 * 
	 * @author Thiago Tenório
	 * @date 30/03/2006
	 */
	public Integer inserirGerenciaRegional(GerenciaRegional gerenciaRegional) throws ControladorException{

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.NOME, gerenciaRegional.getNome()));

		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.NOME_ABREVIADO, gerenciaRegional
						.getNomeAbreviado()));

		// Collection colecaoEnderecos = getControladorUtil().pesquisar(
		// filtroGerenciaRegional, GerenciaRegional.class.getName());

		// if (colecaoEnderecos != null && !colecaoEnderecos.isEmpty()) {
		// throw new ControladorException(
		// "atencao.endereco_localidade_nao_informado");
		// }

		Integer id = (Integer) getControladorUtil().inserir(gerenciaRegional);

		return id;

	}

	/**
	 * [UC0298] Manter Gerência Regional [] Atualizar Gerencia Regional Metodo
	 * que atualiza a Gerencia Regional
	 * 
	 * @author Thiago Tenório
	 * @date 25/05/2006
	 * @throws ControladorException
	 */

	public void atualizarGerenciaRegional(GerenciaRegional gerenciaRegional) throws ControladorException{

		// Verifica se todos os campos obrigatorios foram preenchidos

		if((gerenciaRegional.getId() == null || gerenciaRegional.getId().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (gerenciaRegional.getNome() == null || gerenciaRegional.getNome().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (gerenciaRegional.getNomeAbreviado() == null || gerenciaRegional.getNomeAbreviado().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (gerenciaRegional.getFone() == null || gerenciaRegional.getFone().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.filtro.nenhum_parametro_informado");

		}

		// Verifica se o campo Nome foi preenchido

		if(gerenciaRegional.getNome() == null || gerenciaRegional.getNome().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Nome");
		}

		// Verifica se o campo Nome Abreviado foi preenchido
		if(gerenciaRegional.getNomeAbreviado() == null
						|| gerenciaRegional.getNomeAbreviado().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Nome Abreviado");
		}

		// Verifica se o campo Telefone foi preenchido
		if(gerenciaRegional.getFone() == null || gerenciaRegional.getFone().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Telefone");
		}

		// [FS0003] - Atualização realizada por outro usuário
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, gerenciaRegional.getId()));

		Collection colecaoGerenciaRegionalBase = getControladorUtil().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(colecaoGerenciaRegionalBase == null || colecaoGerenciaRegionalBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		GerenciaRegional gerenciaRegionalBase = (GerenciaRegional) colecaoGerenciaRegionalBase.iterator().next();

		if(gerenciaRegionalBase.getUltimaAlteracao().after(gerenciaRegional.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		gerenciaRegional.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(gerenciaRegional);

	}

	/**
	 * Pesquisa as empresas que serão processadas no emitir contas
	 * 
	 * @author Sávio Luiz
	 * @date 09/01/2007
	 */

	public Collection pesquisarIdsEmpresa() throws ControladorException{

		try{
			return repositorioEmpresa.pesquisarIdsEmpresa();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Informar Parametros do Sistema
	 * 
	 * @author Rômulo Aurélio
	 * @date 09/01/2007
	 * @author eduardo henrique
	 * @date 06/06/2008
	 *       Adição de validação para Unidade Organizacional da Presidência informada e
	 *       registro da
	 *       Operação de Inclusão de Parâmetro
	 * @author Saulo Lima
	 * @date 15/09/2009
	 *       Adição de validação para NumeroDiasEsperaExtratoDebito
	 */
	public void informarParametrosSistema(SistemaParametro sistemaParametro, Usuario usuarioLogado) throws ControladorException{

		try{

			/*
			 * Validação de Campos Obrigatórios
			 */

			if(sistemaParametro.getNomeEstado().equals("")){
				// O Nome do Estado é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Nome do Estado");
			}

			if(sistemaParametro.getNomeEmpresa().equals("")){
				// O Nome da Empresa é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Nome da Empresa");
			}

			if(sistemaParametro.getNomeAbreviadoEmpresa().equals("")){
				// O Abreviatura da Empresa é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Abreviatura da Empresa");
			}

			if(sistemaParametro.getCnpjEmpresa().equals("")){
				// CNPJ é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "CNPJ");
			}

			if(sistemaParametro.getLogradouro().equals("")){
				// O Logradouro é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Logradouro");
			}

			if(sistemaParametro.getAnoMesFaturamento() == null || sistemaParametro.getAnoMesFaturamento().equals("")){
				// O Mês e Ano de Referência é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Mês e Ano de Referência");
			}

			if(sistemaParametro.getAnoMesArrecadacao() == null || sistemaParametro.getAnoMesArrecadacao().equals("")){
				// O Mês e Ano de Referência é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Mês e Ano de Referência");
			}

			if(sistemaParametro.getMenorConsumoGrandeUsuario() == null || sistemaParametro.getMenorConsumoGrandeUsuario().equals("")){
				// O Menor Consumo para ser Grande Usuário é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Menor Consumo para ser Grande Usuário");
			}

			if(sistemaParametro.getValorMinimoEmissaoConta() == null || sistemaParametro.getValorMinimoEmissaoConta().equals("")){
				// O Menor Valor para Emissão de Contas é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Menor Valor para Emissão de Contas");
			}

			if(sistemaParametro.getMenorEconomiasGrandeUsuario() == null || sistemaParametro.getMenorEconomiasGrandeUsuario().equals("")){
				// O Qtde de Economias para ser Grande Usuário é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Qtde de Economias para ser Grande Usuário");
			}

			if(sistemaParametro.getMesesMediaConsumo() == null || sistemaParametro.getMesesMediaConsumo().equals("")){
				// O Menor Valor para Emissão de Contas é obrigatório
				throw new ControladorException("atencao.informe_campo", null, "Meses para Cálculo de Média de Consumo");
			}

			if(sistemaParametro.getNumeroMinimoDiasEmissaoVencimento() == null
							|| sistemaParametro.getNumeroMinimoDiasEmissaoVencimento().equals("")){
				throw new ControladorException("atencao.informe_campo", null, "Número de Dias entre o Vencimento e o Início da Cobrança");
			}

			String valorMenor = ConstantesAplicacao.get("sistema_parametro.numero_dias_espera.menor");
			String valorMaior = ConstantesAplicacao.get("sistema_parametro.numero_dias_espera.maior");
			if(sistemaParametro.getNumeroDiasEsperaExtratoDebito() == null){
				throw new ControladorException("atencao.informe_campo", null, "Dias em Espera para Extrato de Débito");
			}else if(valorMenor != null && !valorMenor.equals("")
							&& sistemaParametro.getNumeroDiasEsperaExtratoDebito().intValue() < Integer.valueOf(valorMenor)){
				throw new ControladorException("atencao.sistema_parametro.numero_dias_espera.menor", null, valorMenor);
			}else if(valorMaior != null && !valorMaior.equals("")
							&& sistemaParametro.getNumeroDiasEsperaExtratoDebito().intValue() > Integer.valueOf(valorMaior)){
				throw new ControladorException("atencao.sistema_parametro.numero_dias_espera.maior", null, valorMaior);
			}

			if(sistemaParametro.getIncrementoMaximoConsumoRateio() == null
							|| sistemaParametro.getIncrementoMaximoConsumoRateio().equals("")){
				throw new ControladorException("atencao.informe_campo", null, "Incremento Máximo de Consumo por economia em Rateio");
			}

			if(sistemaParametro.getDecrementoMaximoConsumoRateio() == null
							|| sistemaParametro.getDecrementoMaximoConsumoRateio().equals("")){
				throw new ControladorException("atencao.informe_campo", null, "Decremento Máximo de Consumo por economia em Rateio");
			}

			if(sistemaParametro.getDiasMaximoAlterarOS() == null || sistemaParametro.getDiasMaximoAlterarOS().equals("")){
				throw new ControladorException("atencao.informe_campo", null, "Dias Máximo para Alterar Dados da OS");

			}

			if(sistemaParametro.getUltimoRAManual() == null || sistemaParametro.getUltimoRAManual().equals("")){
				throw new ControladorException("atencao.informe_campo", null, "Último ID Utilizado para Geração do RA Manual");

			}
			// Verifica se a Unidade Informada é Válida
			if(sistemaParametro.getUnidadeOrganizacionalIdPresidencia() == null){
				throw new ControladorException("atencao.informe_campo", null, "Unidade Organizacional da Presidência");

			}else{ // [FS0008]
				FiltroUnidadeOrganizacional filtroUnidade = new FiltroUnidadeOrganizacional();
				filtroUnidade.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, sistemaParametro
								.getUnidadeOrganizacionalIdPresidencia().getId()));

				Collection<UnidadeOrganizacional> colecaoUnidadesOrganizacionais = this.getControladorUtil().pesquisar(filtroUnidade,
								UnidadeOrganizacional.class.getName());

				if(colecaoUnidadesOrganizacionais == null || colecaoUnidadesOrganizacionais.isEmpty()){
					// // levanta a exceção para a próxima camada
					throw new ControladorException("atencao.unidade_organizacional.inexistente", null, ""
									+ sistemaParametro.getUnidadeOrganizacionalIdPresidencia().getId());
				}
			}
			if(sistemaParametro.getTituloPagina() == null || sistemaParametro.getTituloPagina().equals("")){
				throw new ControladorException("atencao.informe_campo", null, "Títulos de Relatório");

			}

			if(sistemaParametro.getSolicitacaoTipoEspecificacaoDefault() != null
							&& sistemaParametro.getSolicitacaoTipoEspecificacaoDefault().equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
				sistemaParametro.setSolicitacaoTipoEspecificacaoDefault(null);
			}

			sistemaParametro.setUltimaAlteracao(new Date());

			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SISTEMA_PARAMETROS_INFORMAR,
							sistemaParametro.getParmId(), sistemaParametro.getParmId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacao.registrarOperacao(sistemaParametro);

			this.getControladorTransacao().registrarTransacao(sistemaParametro);

			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			this.getControladorUtil().atualizar(sistemaParametro);

			// Salva a imagem de logomarca da empresa na tabela de Parâmetros
			this.repositorioCadastro.atualizarImagemRelatorio(sistemaParametro.getParmId(), sistemaParametro.getArquivoImagemRelatorio());

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException(e, "Erro na camada de persistência");
		}
	}

	/**
	 * [UC0534] Inserir Feriado
	 * 
	 * @author Kassia Albuquerque
	 * @date 17/01/2007
	 */
	public Integer inserirFeriado(NacionalFeriado nacionalFeriado, MunicipioFeriado municipioFeriado, Usuario usuarioLogado)
					throws ControladorException{

		if(nacionalFeriado != null){

			// [FS0003] - Verificando a existência do Feriado Nacional pela
			// descrição

			FiltroNacionalFeriado filtroNacionalFeriado = new FiltroNacionalFeriado();

			filtroNacionalFeriado.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.NOME, nacionalFeriado.getDescricao()));
			filtroNacionalFeriado.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.DATA, nacionalFeriado.getData()));

			Collection colecaoNacionalFeriado = getControladorUtil().pesquisar(filtroNacionalFeriado, NacionalFeriado.class.getName());

			if(colecaoNacionalFeriado != null && !colecaoNacionalFeriado.isEmpty()){
				throw new ControladorException("atencao.nacional_feriado.decricao.existente");
			}

			// Verificando existência de mais de um Feriado Nacional numa mesma
			// data
			filtroNacionalFeriado.limparListaParametros();

			filtroNacionalFeriado.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.DATA, nacionalFeriado.getData()));

			colecaoNacionalFeriado = getControladorUtil().pesquisar(filtroNacionalFeriado, NacionalFeriado.class.getName());

			if(colecaoNacionalFeriado != null && !colecaoNacionalFeriado.isEmpty()){
				throw new ControladorException("atencao.nacional_feriado_com_data_existente");
			}

			nacionalFeriado.setUltimaAlteracao(new Date());

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_FERIADO_INSERIR,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_FERIADO_INSERIR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			nacionalFeriado.setOperacaoEfetuada(operacaoEfetuada);
			nacionalFeriado.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(nacionalFeriado);
			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			Integer idFeriado = (Integer) getControladorUtil().inserir(nacionalFeriado);

			return idFeriado;

		}else{

			// Verificando existencia de data Feriado Municipal numa mesma data
			// de Feriado Nacional

			FiltroNacionalFeriado filtroNacionalFeriado = new FiltroNacionalFeriado();

			filtroNacionalFeriado.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.DATA, municipioFeriado.getDataFeriado()));

			Collection colecaoNacionalFeriado = getControladorUtil().pesquisar(filtroNacionalFeriado, NacionalFeriado.class.getName());

			if(colecaoNacionalFeriado != null && !colecaoNacionalFeriado.isEmpty()){

				throw new ControladorException("atencao.nacional_feriado_com_data_existente");
			}

			// [FS0003] - Verificando a existência do Feriado Municipal pela
			// descrição

			FiltroMunicipioFeriado filtroMunicipioFeriado = new FiltroMunicipioFeriado();

			filtroMunicipioFeriado.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.NOME, municipioFeriado
							.getDescricaoFeriado()));
			filtroMunicipioFeriado.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.DATA, municipioFeriado
.getDataFeriado()));

			filtroMunicipioFeriado.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.ID_MUNICIPIO, municipioFeriado
							.getMunicipio().getId()));

			Collection colecaoMunicipioFeriado = getControladorUtil().pesquisar(filtroMunicipioFeriado, MunicipioFeriado.class.getName());

			if(colecaoMunicipioFeriado != null && !colecaoMunicipioFeriado.isEmpty()){
				throw new ControladorException("atencao.municipio_feriado_ja_existente");
			}

			// Verificando se existe Feriado Municipal com mesma data do que
			// esta sendo atualizado
			FiltroMunicipioFeriado filtroMunicipioFeriado2 = new FiltroMunicipioFeriado();

			filtroMunicipioFeriado2
							.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.DATA, municipioFeriado.getDataFeriado()));

			filtroMunicipioFeriado2.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.ID_MUNICIPIO, municipioFeriado
							.getMunicipio().getId()));

			Collection colecaoMunicipioFeriado2 = getControladorUtil().pesquisar(filtroMunicipioFeriado2, MunicipioFeriado.class.getName());

			if(colecaoMunicipioFeriado2 != null && !colecaoMunicipioFeriado2.isEmpty()){
				throw new ControladorException("atencao.municipio_feriado_com_data_existente");
			}

		}
		municipioFeriado.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_FERIADO_INSERIR, new UsuarioAcaoUsuarioHelper(
						usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_FERIADO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		municipioFeriado.setOperacaoEfetuada(operacaoEfetuada);
		municipioFeriado.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(municipioFeriado);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		Integer idFeriado = (Integer) getControladorUtil().inserir(municipioFeriado);
		return idFeriado;
	}

	/**
	 * Pesquisa os feriados(nacionais e municipais)
	 * 
	 * @author Kássia Albuquerque
	 * @date 22/01/2007
	 */
	public Collection pesquisarFeriado(Short tipoFeriado, String descricao, Date dataFeriadoInicio, Date dataFeriadoFim,
					Integer idMunicipio, Integer numeroPagina) throws ControladorException{

		Collection colecaoObject = new ArrayList();

		Collection colecaoFeriado = new ArrayList();

		try{
			colecaoObject = repositorioCadastro.pesquisarFeriado(tipoFeriado, descricao, dataFeriadoInicio, dataFeriadoFim, idMunicipio,
							numeroPagina);
			Iterator iteratorObject = colecaoObject.iterator();
			while(iteratorObject.hasNext()){
				Object[] arrayObject = (Object[]) iteratorObject.next();
				if(arrayObject != null){
					// instancia um FeriadoHelper que é um helper
					FeriadoHelper feriadoHelper = new FeriadoHelper();
					// tipo do feriado
					if(arrayObject[0] != null){
						if(arrayObject[0].toString().equals(NacionalFeriado.ESTADUAL)){
							feriadoHelper.setTipoFeriado((short) 3);
						}else if(arrayObject[0].toString().equals(NacionalFeriado.NACIONAL)){
							feriadoHelper.setTipoFeriado((short) 1);
						}else{
							feriadoHelper.setTipoFeriado((short) 2);
						}
					}
					// código do feriado
					if(arrayObject[1] != null){
						feriadoHelper.setId((Integer) arrayObject[1]);
					}
					// descrição do feriado
					if(arrayObject[2] != null){
						feriadoHelper.setDescricao((String) arrayObject[2]);
					}
					// descrição do município
					if(arrayObject[3] != null){
						feriadoHelper.setDescricaoMunicipio((String) arrayObject[3]);
					}
					// data do feriado
					if(arrayObject[4] != null){
						feriadoHelper.setData((Date) arrayObject[4]);
					}

					colecaoFeriado.add(feriadoHelper);
				}
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return colecaoFeriado;
	}

	/**
	 * Pesquisar quantidade de registro dos feriados(nacionais e municipais)
	 * 
	 * @author Kássia Albuquerque
	 * @date 22/01/2007
	 */
	public Integer pesquisarFeriadoCount(Short tipoFeriado, String descricao, Date dataFeriadoInicio, Date dataFeriadoFim,
					Integer idMunicipio) throws ControladorException{

		try{
			return repositorioCadastro.pesquisarFeriadoCount(tipoFeriado, descricao, dataFeriadoInicio, dataFeriadoFim, idMunicipio);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0535] Manter Feriado [SB0001] Atualizar Feriado
	 * 
	 * @author Kassia Albuquerque
	 * @date 27/01/2006
	 * @pparam feriado
	 * @throws ControladorException
	 */

	public void atualizarFeriado(NacionalFeriado nacionalFeriado, MunicipioFeriado municipioFeriado, Usuario usuarioLogado)
					throws ControladorException{

		if(nacionalFeriado != null){

			// [FS0003] - Verificando a existência do Feriado Nacional pela
			// descrição

			FiltroNacionalFeriado filtroNacionalFeriado = new FiltroNacionalFeriado();

			filtroNacionalFeriado.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.NOME, nacionalFeriado.getDescricao()));
			filtroNacionalFeriado.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.DATA, nacionalFeriado.getData()));

			filtroNacionalFeriado.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroNacionalFeriado.ID, nacionalFeriado.getId()));

			Collection colecaoNacionalFeriado = getControladorUtil().pesquisar(filtroNacionalFeriado, NacionalFeriado.class.getName());

			if(colecaoNacionalFeriado != null && !colecaoNacionalFeriado.isEmpty()){
				throw new ControladorException("atencao.nacional_feriado.decricao.existente");
			}

			// Verificando existência de mais de um Feriado Nacional numa mesma
			// data
			filtroNacionalFeriado.limparListaParametros();

			filtroNacionalFeriado.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.DATA, nacionalFeriado.getData()));

			filtroNacionalFeriado.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroNacionalFeriado.ID, nacionalFeriado.getId()));

			colecaoNacionalFeriado = new ArrayList();

			colecaoNacionalFeriado = getControladorUtil().pesquisar(filtroNacionalFeriado, NacionalFeriado.class.getName());

			if(!colecaoNacionalFeriado.isEmpty()){
				throw new ControladorException("atencao.nacional_feriado_com_data_existente");
			}

			nacionalFeriado.setUltimaAlteracao(new Date());

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_FERIADO_ATUALIZAR,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_FERIADO_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			nacionalFeriado.setOperacaoEfetuada(operacaoEfetuada);
			nacionalFeriado.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(nacionalFeriado);
			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			// [FS0004] - Atualização realizada por outro usuário

			FiltroNacionalFeriado filtroNacionalFeriadoBase = new FiltroNacionalFeriado();

			// Seta o filtro para buscar o FERIADO na base
			filtroNacionalFeriadoBase.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.ID, nacionalFeriado.getId()));

			// Procura servicoPerfilTipo na base
			Collection feriadoAtualizados = getControladorUtil().pesquisar(filtroNacionalFeriadoBase, NacionalFeriado.class.getName());

			NacionalFeriado nacionalFeriadoNaBase = (NacionalFeriado) Util.retonarObjetoDeColecao(feriadoAtualizados);

			if(nacionalFeriadoNaBase == null){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			// Verificar se o feriado já foi atualizado por outro usuário
			// durante esta atualização

			if(nacionalFeriadoNaBase.getUltimaAlteracao().after(nacionalFeriado.getUltimaAlteracao())){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			nacionalFeriado.setUltimaAlteracao(new Date());

			// Atualiza o objeto na base
			getControladorUtil().atualizar(nacionalFeriado);

		}else{

			// Verificando existencia de data Feriado Municipal numa mesma data
			// de Feriado Nacional
			FiltroNacionalFeriado filtroNacionalFeriado = new FiltroNacionalFeriado();

			filtroNacionalFeriado.adicionarParametro(new ParametroSimples(FiltroNacionalFeriado.DATA, municipioFeriado.getDataFeriado()));

			Collection colecaoNacionalFeriado = getControladorUtil().pesquisar(filtroNacionalFeriado, NacionalFeriado.class.getName());

			if(colecaoNacionalFeriado != null && !colecaoNacionalFeriado.isEmpty()){

				throw new ControladorException("atencao.nacional_feriado_com_data_existente");
			}

			// [FS0003] - Verificando a existência do Feriado Municipal pela
			// descrição
			FiltroMunicipioFeriado filtroMunicipioFeriado = new FiltroMunicipioFeriado();

			filtroMunicipioFeriado.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.NOME, municipioFeriado
							.getDescricaoFeriado()));
			filtroMunicipioFeriado.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.DATA, municipioFeriado
.getDataFeriado()));

			filtroMunicipioFeriado.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.ID_MUNICIPIO, municipioFeriado
							.getMunicipio().getId()));

			filtroMunicipioFeriado.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroMunicipioFeriado.ID, municipioFeriado.getId()));

			Collection colecaoMunicipioFeriado = getControladorUtil().pesquisar(filtroMunicipioFeriado, MunicipioFeriado.class.getName());

			if(colecaoMunicipioFeriado != null && !colecaoMunicipioFeriado.isEmpty()){
				throw new ControladorException("atencao.municipio_feriado.decricao.existente");
			}

			// Verificando se existe Feriado Municipal com mesma data do que
			// esta sendo atualizado
			FiltroMunicipioFeriado filtroMunicipioFeriado2 = new FiltroMunicipioFeriado();

			filtroMunicipioFeriado2
							.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.DATA, municipioFeriado.getDataFeriado()));

			filtroMunicipioFeriado2.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.ID_MUNICIPIO, municipioFeriado
							.getMunicipio().getId()));

			filtroMunicipioFeriado2
							.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroMunicipioFeriado.ID, municipioFeriado.getId()));

			Collection colecaoMunicipioFeriado2 = getControladorUtil().pesquisar(filtroMunicipioFeriado2, MunicipioFeriado.class.getName());

			if(colecaoMunicipioFeriado2 != null && !colecaoMunicipioFeriado2.isEmpty()){
				throw new ControladorException("atencao.municipio_feriado_com_data_existente");
			}

			municipioFeriado.setUltimaAlteracao(new Date());

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_FERIADO_ATUALIZAR,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_FERIADO_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			municipioFeriado.setOperacaoEfetuada(operacaoEfetuada);
			municipioFeriado.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(municipioFeriado);
			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			// [FS0004] - Atualização realizada por outro usuário

			FiltroMunicipioFeriado filtroMunicipioFeriadoBase = new FiltroMunicipioFeriado();
			// Seta o filtro para buscar o FERIADO na base
			filtroMunicipioFeriadoBase.adicionarParametro(new ParametroSimples(FiltroMunicipioFeriado.ID, municipioFeriado.getId()));

			// Procura feriado na base
			Collection feriadoAtualizados = getControladorUtil().pesquisar(filtroMunicipioFeriadoBase, MunicipioFeriado.class.getName());

			MunicipioFeriado municipioFeriadoNaBase = (MunicipioFeriado) Util.retonarObjetoDeColecao(feriadoAtualizados);

			if(municipioFeriadoNaBase == null){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			// Verificar se o feriado já foi atualizado por outro usuário
			// durante esta atualização

			if(municipioFeriadoNaBase.getUltimaAlteracao().after(municipioFeriado.getUltimaAlteracao())){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			municipioFeriado.setUltimaAlteracao(new Date());

			// Atualiza o objeto na base
			getControladorUtil().atualizar(municipioFeriado);

		}
	}

	/**
	 * [UC0535] Manter Feriado
	 * Remover Feriado
	 * 
	 * @author Kassia Albuquerque
	 * @date 29/01/2007
	 * @pparam feriado
	 * @throws ControladorException
	 */
	public void removerFeriado(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_FERIADO_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		if(ids != null && ids.length != 0){
			for(int i = 0; i < ids.length; i++){
				String[] idsColecao = ids[i].split(";");
				if(idsColecao[1].equals("2")){
					this.getControladorUtil().removerUm(Integer.valueOf(idsColecao[0]), MunicipioFeriado.class.getName(), operacaoEfetuada,
									colecaoUsuarios);
				}else{
					this.getControladorUtil().removerUm(Integer.valueOf(idsColecao[0]), NacionalFeriado.class.getName(), operacaoEfetuada,
									colecaoUsuarios);
				}

			}
		}
	}

	/**
	 * Pesquisar os ids do Setor comercial pela localidade
	 * 
	 * @author Ana Maria
	 * @date 07/02/2007
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsSetorComercial(Integer idLocalidade) throws ControladorException{

		try{
			return repositorioSetorComercial.pesquisarIdsSetorComercial(idLocalidade);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Informar Mensagem do Sistema
	 * 
	 * @author Kássia Albuquerque
	 * @date 02/03/2007
	 */
	public void atualizarMensagemSistema(SistemaParametro sistemaParametro, Usuario usuarioLogado) throws ControladorException{

		// [FS0003] - Atualização realizada por outro usuário
		if(sistemaParametro == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se já foi atualizado por outro usuário durante esta
		// atualização
		SistemaParametro sistemaParametroBase = getControladorUtil().pesquisarParametrosDoSistema();

		if(sistemaParametroBase.getUltimaAlteracao().after(sistemaParametro.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
		try{
			// Atualiza o objeto na base
			repositorioCadastro.atualizarMensagemSistema(sistemaParametro.getMensagemSistema());
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa os dados do email do batch para ser enviado
	 * 
	 * @author Sávio Luiz
	 * @date 13/03/2007
	 */
	public EnvioEmail pesquisarEnvioEmail(Integer idEnvioEmail) throws ControladorException{

		try{
			return repositorioCadastro.pesquisarEnvioEmail(idEnvioEmail);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public DadosEnvioEmailHelper pesquisarDadosEmailSistemaParametros() throws ControladorException{

		try{
			return repositorioCadastro.pesquisarDadosEmailSistemaParametros();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC????] Inserir Funcionario
	 * 
	 * @author Rômulo Aurélio
	 * @date 12/04/2007
	 * @param funcionario
	 *            ,
	 *            usuarioLogado
	 */
	public void inserirFuncionario(Funcionario funcionario, Usuario usuarioLogado) throws ControladorException{

		if(funcionario.getDescricaoCargo() == null || funcionario.getDescricaoCargo().equalsIgnoreCase("")){
			throw new ControladorException("atencao.required", null, "Descrição do Cargo");
		}

		if(funcionario.getEmpresa() != null
						&& funcionario.getEmpresa().getId().toString().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.required", null, "Empresa");

		}

		if(funcionario.getEmpresa() != null
						&& funcionario.getEmpresa().getId().toString().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.required", null, "Empresa");

		}

		if(funcionario.getNome() == null || funcionario.getNome().equalsIgnoreCase("")){
			throw new ControladorException("atencao.required", null, "Nome");
		}

		FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, funcionario.getId().toString()));

		// Pesquisa se existe algum funcionario com a matricula informada

		Collection colecaoFuncionarioMatricula = new ArrayList();

		colecaoFuncionarioMatricula = getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());

		if(colecaoFuncionarioMatricula != null && !colecaoFuncionarioMatricula.isEmpty()){
			throw new ControladorException("atencao.funcionario_matricula_ja_existente");

		}

		filtroFuncionario.limparListaParametros();

		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.NOME, funcionario.getNome()));
		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.UNIDADE_EMPRESA, funcionario.getEmpresa().getId()
						.toString()));
		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.UNIDADE_ORGANIZACIONAL_ID, funcionario
						.getUnidadeOrganizacional().getId().toString()));

		Collection colecaoFuncionario = new ArrayList();
		colecaoFuncionario = getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());

		if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){
			throw new ControladorException("atencao.funcionario_ja_existente");

		}

		funcionario.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_FUNCIONARIO_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_FUNCIONARIO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		funcionario.setOperacaoEfetuada(operacaoEfetuada);
		funcionario.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(funcionario);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		getControladorUtil().inserir(funcionario);

	}

	/**
	 * [UC????] Atualizar Funcionario
	 * 
	 * @author Rômulo Aurélio
	 * @date 17/04/2007
	 * @param funcionario
	 *            ,
	 *            usuarioLogado, idFuncionario
	 */
	public void atualizarFuncionario(Funcionario funcionario, Usuario usuarioLogado) throws ControladorException{

		if(funcionario.getDescricaoCargo() == null || funcionario.getDescricaoCargo().equalsIgnoreCase("")){
			throw new ControladorException("atencao.required", null, "Descrição do Cargo");
		}

		if(funcionario.getEmpresa() != null
						&& funcionario.getEmpresa().getId().toString().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.required", null, "Empresa");

		}

		if(funcionario.getEmpresa() != null
						&& funcionario.getEmpresa().getId().toString().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.required", null, "Empresa");

		}

		if(funcionario.getNome() == null || funcionario.getNome().equalsIgnoreCase("")){
			throw new ControladorException("atencao.required", null, "Nome");
		}

		FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.NOME, funcionario.getNome()));
		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.UNIDADE_EMPRESA, funcionario.getEmpresa().getId()
						.toString()));
		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.UNIDADE_ORGANIZACIONAL_ID, funcionario
						.getUnidadeOrganizacional().getId().toString()));

		Collection colecaoFuncionario = new ArrayList();
		colecaoFuncionario = getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());

		if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){

			Funcionario funcionarioBase = (Funcionario) colecaoFuncionario.iterator().next();

			if(!funcionarioBase.getId().toString().equalsIgnoreCase(funcionario.getId().toString())){
				throw new ControladorException("atencao.funcionario_ja_existente");
			}

		}
		funcionario.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_FUNCIONARIO_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_FUNCIONARIO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		funcionario.setOperacaoEfetuada(operacaoEfetuada);
		funcionario.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(funcionario);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		getControladorUtil().atualizar(funcionario);


	}

	/**
	 * Pesquisar todos ids dos setores comerciais.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsSetorComercial() throws ControladorException{

		try{
			return repositorioCadastro.pesquisarTodosIdsSetorComercial();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Este caso de uso permite a emissão de boletins de cadastro
	 * [UC0582] Emitir Boletim de Cadastro
	 * 
	 * @author Rafael Corrêa
	 * @data 15/05/2007
	 * @param
	 * @return void
	 */
	public void emitirBoletimCadastro(CobrancaAcaoAtividadeCronograma cronogramaAtividadeAcaoCobranca,
					CobrancaAcaoAtividadeComando comandoAtividadeAcaoCobranca, Date dataAtualPesquisa, CobrancaAcao cobrancaAcao,
					int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		if(comandoAtividadeAcaoCobranca != null){
			idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
							UnidadeProcessamento.COB_ACAO_ATIV_COMAND, comandoAtividadeAcaoCobranca.getId());
		}else{
			idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
							UnidadeProcessamento.COB_ACAO_ATIV_CRONOG, cronogramaAtividadeAcaoCobranca.getId());
		}

		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;
		if(cronogramaAtividadeAcaoCobranca != null && cronogramaAtividadeAcaoCobranca.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cronogramaAtividadeAcaoCobranca.getId();
		}
		if(comandoAtividadeAcaoCobranca != null && comandoAtividadeAcaoCobranca.getId() != null){
			idComandoAtividadeAcaoCobranca = comandoAtividadeAcaoCobranca.getId();
		}

		// Caso seja cobrança ação atividade cronograma e seja Fiscalização
		// cortado ou suprimido então gera boletin de cadastro
		// Caso seja cobrança ação atividade comando e o indicador de emissão de
		// boletim seja "SIM"(1) então gera boletin de cadastro
		if((idCronogramaAtividadeAcaoCobranca != null && (cobrancaAcao.getId().equals(CobrancaAcao.FISCALIZACAO_SUPRIMIDO) || cobrancaAcao
						.getId().equals(CobrancaAcao.FISCALIZACAO_CORTADO)))
						|| (idComandoAtividadeAcaoCobranca != null && comandoAtividadeAcaoCobranca.getIndicadorBoletim() != null && comandoAtividadeAcaoCobranca
										.getIndicadorBoletim().equals(CobrancaAcaoAtividadeComando.INDICADOR_BOLETIM_SIM))){

			System.out.println("******************** INICIO BOLETIM CADASTRO ********************");

			try{

				boolean flagFimPesquisa = false;
				final int quantidadeCobrancaDocumento = 500;
				int quantidadeCobrancaDocumentoInicio = 0;
				StringBuilder boletimCadastroTxt = new StringBuilder();
				int sequencialImpressao = 0;
				int pagina = 0;

				while(!flagFimPesquisa){

					pagina++;

					Collection colecaoEmitirBoletimCadastro = null;
					try{

						colecaoEmitirBoletimCadastro = repositorioCobranca.pesquisarCobrancaDocumentoBoletimCadastro(
										idCronogramaAtividadeAcaoCobranca, idComandoAtividadeAcaoCobranca, dataAtualPesquisa, cobrancaAcao
														.getId(), quantidadeCobrancaDocumentoInicio);

					}catch(ErroRepositorioException ex){
						ex.printStackTrace();
						throw new ControladorException("erro.sistema", ex);
					}

					if(colecaoEmitirBoletimCadastro != null && !colecaoEmitirBoletimCadastro.isEmpty()){

						System.out.println("***************** QUANTIDADE COBRANÇA:" + colecaoEmitirBoletimCadastro.size()
										+ " ******************");

						if(colecaoEmitirBoletimCadastro.size() < quantidadeCobrancaDocumento){
							flagFimPesquisa = true;
						}else{
							quantidadeCobrancaDocumentoInicio = quantidadeCobrancaDocumentoInicio + 500;
						}

						Iterator colecaoEmitirBoletimCadastroIterator = colecaoEmitirBoletimCadastro.iterator();
						int count = 0;

						EmitirDocumentoCobrancaBoletimCadastroHelper emitirDocumentoCobrancaBoletimCadastroHelper = null;
						while(colecaoEmitirBoletimCadastroIterator.hasNext()){

							emitirDocumentoCobrancaBoletimCadastroHelper = (EmitirDocumentoCobrancaBoletimCadastroHelper) colecaoEmitirBoletimCadastroIterator
											.next();

							count++;

							System.out.println("VEZ QUE ENTRA:" + pagina + " / " + count + " / IMÓVEL:"
											+ emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel().toString());

							sequencialImpressao++;

							if(emitirDocumentoCobrancaBoletimCadastroHelper != null){

								criarDadosTxtBoletimCadastroModelo1(boletimCadastroTxt, emitirDocumentoCobrancaBoletimCadastroHelper);

							}

							emitirDocumentoCobrancaBoletimCadastroHelper = null;

							boletimCadastroTxt.append(System.getProperty("line.separator"));

						}
					}else{
						flagFimPesquisa = true;
					}
				}

				System.out.println("******************** FIM BOLETIM CADASTRO ********************");

				Date dataAtual = new Date();

				String nomeZip = null;
				String tituloComandoEventual = null;
				if(comandoAtividadeAcaoCobranca != null && comandoAtividadeAcaoCobranca.getId() != null){
					tituloComandoEventual = comandoAtividadeAcaoCobranca.getDescricaoTitulo();
					nomeZip = "BOL_CAD " + tituloComandoEventual + " " + Util.formatarDataComHora(dataAtual);
				}else{
					nomeZip = "BOLETIM_CADASTRO " + Util.formatarDataComHora(dataAtual);
				}

				nomeZip = nomeZip.replace("/", "_");
				nomeZip = nomeZip.replace(" ", "_");
				nomeZip = nomeZip.replace(":", "_");

				nomeZip = nomeZip.replace("/", "_");

				try{
					if(boletimCadastroTxt != null && boletimCadastroTxt.length() != 0){

						boletimCadastroTxt.append("\u0004");
						// criar o arquivo zip
						File compactado = new File(nomeZip + ".zip"); // nomeZip
						ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

						File leitura = new File(nomeZip + ".txt");
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leitura.getAbsolutePath())));
						out.write(boletimCadastroTxt.toString());
						out.flush();
						out.close();
						ZipUtil.adicionarArquivo(zos, leitura);

						// close the stream
						zos.close();
						leitura.delete();
					}

					System.out.println("******************** FIM GERAÇÃO ARQUIVO ********************");

				}catch(IOException e){
					e.printStackTrace();
					throw new ControladorException("erro.sistema", e);
				}catch(Exception e){
					e.printStackTrace();
					throw new ControladorException("erro.sistema", e);
				}

				// --------------------------------------------------------
				getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

			}catch(Exception e){
				// Este catch serve para interceptar qualquer exceção que o
				// processo
				// batch venha a lançar e garantir que a unidade de
				// processamento do
				// batch será atualizada com o erro ocorrido

				e.printStackTrace();

				getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

				throw new EJBException(e);
			}
		}else{
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);
		}

	}

	public void criarDadosTxtBoletimCadastroModelo1(StringBuilder boletimCadastroTxt,
					EmitirDocumentoCobrancaBoletimCadastroHelper emitirDocumentoCobrancaBoletimCadastroHelper) throws ControladorException{

		ClienteEmitirBoletimCadastroHelper clienteEmitirBoletimCadastroHelperProprietario = null;
		ClienteEmitirBoletimCadastroHelper clienteEmitirBoletimCadastroHelperUsuario = null;

		clienteEmitirBoletimCadastroHelperProprietario = getControladorCliente().pesquisarClienteEmitirBoletimCadastro(
						emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel(), ClienteRelacaoTipo.PROPRIETARIO);

		clienteEmitirBoletimCadastroHelperUsuario = getControladorCliente().pesquisarClienteEmitirBoletimCadastro(
						emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel(), ClienteRelacaoTipo.USUARIO);

		// Início do processo de geração do
		// arquivo txt

		// Número Documento/Referência
		boletimCadastroTxt.append(Util.completaString("", 8));

		// Dados do Cliente Proprietário
		if(clienteEmitirBoletimCadastroHelperProprietario != null){
			adicionarDadosClienteEmitirBoletimCadastroTxt(boletimCadastroTxt, clienteEmitirBoletimCadastroHelperProprietario);
		}else{
			boletimCadastroTxt.append(Util.completaString("", 244));
		}

		// Dados do Cliente Usuário
		if(clienteEmitirBoletimCadastroHelperUsuario != null){
			adicionarDadosClienteEmitirBoletimCadastroTxt(boletimCadastroTxt, clienteEmitirBoletimCadastroHelperUsuario);
		}else{
			boletimCadastroTxt.append(Util.completaString("", 244));
		}

		// Dados do Imóvel
		boletimCadastroTxt.append(Util.completaString("", 1));

		// Inscrição
		Imovel imovel = new Imovel();
		Localidade localidadeImovel = new Localidade();
		localidadeImovel.setId(emitirDocumentoCobrancaBoletimCadastroHelper.getIdLocalidade());
		imovel.setLocalidade(localidadeImovel);
		if(Util.isVazioOuBranco(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroQuadra())){

			imovel.setQuadra(new Quadra(0));
			imovel.getQuadra().setNumeroQuadra(0);
		}else{

			imovel.setQuadra(new Quadra());
			imovel.getQuadra().setNumeroQuadra(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroQuadra());
		}

		// Setor Comercial
		if(!Util.isVazioOuBranco(emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoSetorComercial())
						&& emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoSetorComercial().intValue() > 0){

			SetorComercial setor = new SetorComercial();
			setor.setCodigo(emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoSetorComercial());
			imovel.setSetorComercial(setor);
		}else{

			SetorComercial setor = new SetorComercial();
			setor.setId(0);
			setor.setCodigo(0);
			imovel.setSetorComercial(setor);
		}

		// Número da Rota;
		Integer idRota = emitirDocumentoCobrancaBoletimCadastroHelper.getIdRota();

		if(idRota != null){
			Rota rota = new Rota();
			rota.setId(idRota);

			imovel.setRota(rota);
		}

		// Número do Segmento
		if(!Util.isVazioOuBranco(emitirDocumentoCobrancaBoletimCadastroHelper.getSegmento())){
			imovel.setNumeroSegmento(emitirDocumentoCobrancaBoletimCadastroHelper.getSegmento());
		}else{
			imovel.setNumeroSegmento(Util.obterShort("0"));
		}

		// Número do Lote
		if(!Util.isVazioOuBranco(emitirDocumentoCobrancaBoletimCadastroHelper.getLote())
						&& emitirDocumentoCobrancaBoletimCadastroHelper.getLote() > 0){
			imovel.setLote(emitirDocumentoCobrancaBoletimCadastroHelper.getLote());
		}else{
			imovel.setLote(Util.obterShort("0"));
		}

		// Número do Sublote
		if(!Util.isVazioOuBranco(emitirDocumentoCobrancaBoletimCadastroHelper.getSubLote())
						&& emitirDocumentoCobrancaBoletimCadastroHelper.getSubLote() > 0){
			imovel.setSubLote(emitirDocumentoCobrancaBoletimCadastroHelper.getSubLote());
		}else{
			imovel.setSubLote(Util.obterShort("0"));
		}

		// inscricao formatada do imovel
		emitirDocumentoCobrancaBoletimCadastroHelper.setInscricaoImovel(imovel.getInscricaoFormatada());

		boletimCadastroTxt.append(emitirDocumentoCobrancaBoletimCadastroHelper.getInscricaoImovel());

		// Matrícula do imóvel
		String matriculaImovelFormatada = Util.adicionarZerosEsquedaNumero(9,
						"" + emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel());

		boletimCadastroTxt.append(Util.completaString(matriculaImovelFormatada, 9));

		// Código do Cliente Proprietário
		String idClienteProprietario = "";

		if(clienteEmitirBoletimCadastroHelperProprietario != null){
			idClienteProprietario = Util.adicionarZerosEsquedaNumero(12, clienteEmitirBoletimCadastroHelperProprietario.getCliente()
							.getId().toString());
		}

		boletimCadastroTxt.append(Util.completaString(idClienteProprietario, 12));

		// Inscrição Atual
		boletimCadastroTxt.append(Util.completaString("", 16));

		// Número de Moradores
		String numeroMoradores = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroMorador() != null){
			numeroMoradores = Util.adicionarZerosEsquedaNumero(4, emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroMorador()
							.toString());
		}

		boletimCadastroTxt.append(Util.completaString(numeroMoradores, 4));

		// Nome na Conta
		String nomeConta = "";
		Integer idRelacaoTipo = null;

		try{

			idRelacaoTipo = repositorioClienteImovel.retornaTipoRelacaoClienteImovelNomeConta(emitirDocumentoCobrancaBoletimCadastroHelper
							.getIdImovel());

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(idRelacaoTipo != null){
			if(idRelacaoTipo.toString().equals(ClienteRelacaoTipo.PROPRIETARIO.toString())){
				nomeConta = "P";
			}else if(idRelacaoTipo.toString().equals(ClienteRelacaoTipo.USUARIO.toString())){
				nomeConta = "U";
			}else{
				nomeConta = "R";
			}
		}

		boletimCadastroTxt.append(Util.completaString(nomeConta, 1));

		// Código do Cliente Usuário
		String idClienteUsuario = "";

		if(clienteEmitirBoletimCadastroHelperUsuario != null){
			idClienteUsuario = Util.adicionarZerosEsquedaNumero(12, clienteEmitirBoletimCadastroHelperUsuario.getCliente().getId()
							.toString());
		}

		boletimCadastroTxt.append(Util.completaString(idClienteUsuario, 12));

		// Logradouro
		String idLogradouro = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroMorador() != null){
			idLogradouro = Util.adicionarZerosEsquedaNumero(9, emitirDocumentoCobrancaBoletimCadastroHelper.getIdLogradouro().toString());
		}

		boletimCadastroTxt.append(Util.completaString(idLogradouro, 9));

		// Endereço Abreviado
		String enderecoAbreviadoImovel = getControladorEndereco().obterEnderecoAbreviadoImovel(
						emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel());

		if(enderecoAbreviadoImovel == null){
			enderecoAbreviadoImovel = "";
		}

		boletimCadastroTxt.append(Util.completaString(enderecoAbreviadoImovel, 26));

		// CEP
		String cep = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoCep() != null){
			cep = Util.adicionarZerosEsquedaNumero(8, emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoCep().toString());
		}

		boletimCadastroTxt.append(Util.completaString(cep, 8));

		// Bairro
		String bairro = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoBairro() != null){
			bairro = Util.adicionarZerosEsquedaNumero(3, emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoBairro().toString());
		}

		boletimCadastroTxt.append(Util.completaString(bairro, 3));

		// Referência
		String referencia = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getReferencia() != null){
			referencia = emitirDocumentoCobrancaBoletimCadastroHelper.getReferencia().toString();
		}

		boletimCadastroTxt.append(Util.completaString(referencia, 1));

		// Número do Imóvel
		String numeroImovel = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroImovel() != null){
			numeroImovel = Util.adicionarZerosEsquedaNumero(5, emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroImovel().toString());
		}

		boletimCadastroTxt.append(Util.completaString(numeroImovel, 5));

		// Complemento
		String complemento = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getComplemento() != null){
			complemento = emitirDocumentoCobrancaBoletimCadastroHelper.getComplemento();
		}

		boletimCadastroTxt.append(Util.completaString(complemento, 19));

		// Dados das Subcategorias
		Collection colecaoSubcategorias = getControladorImovel().obterQuantidadeEconomiasSubCategoria(
						emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel());

		String subcategorias = "";

		if(colecaoSubcategorias != null && !colecaoSubcategorias.isEmpty()){
			Iterator colecaoSubcategoriasIterator = colecaoSubcategorias.iterator();

			for(int i = 0; i < 6; i++){

				if(colecaoSubcategoriasIterator.hasNext()){

					Subcategoria subcategoria = (Subcategoria) colecaoSubcategoriasIterator.next();

					subcategorias = subcategorias + subcategoria.getId().toString()
									+ Util.adicionarZerosEsquedaNumero(4, subcategoria.getQuantidadeEconomias().toString());

				}else{
					break;
				}
			}
		}

		boletimCadastroTxt.append(Util.completaString(subcategorias, 36));

		// Qtde Apartamentos (Hotel)
		boletimCadastroTxt.append(Util.completaString("", 6));

		// Área Construída
		String areaConstruida = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getAreaConstruida() != null){
			areaConstruida = "" + emitirDocumentoCobrancaBoletimCadastroHelper.getAreaConstruida().intValue();
		}

		boletimCadastroTxt.append(Util.completaString(areaConstruida, 6));

		// Situação de Água
		String situacaoAgua = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdLigacaoAguaSituacao() != null){
			situacaoAgua = emitirDocumentoCobrancaBoletimCadastroHelper.getIdLigacaoAguaSituacao().toString();
		}

		boletimCadastroTxt.append(Util.completaString(situacaoAgua, 1));

		// Obtém os dados das ligações de água e esgoto
		DadosLigacoesBoletimCadastroHelper dadosLigacoesBoletimCadastroHelper = getControladorAtendimentoPublico()
						.obterDadosLigacaoAguaEsgoto(emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel());

		// Diâmetro Ligação Água
		String diametroLigAgua = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getLigacaoAguaDiametro() != null){

			diametroLigAgua = dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getLigacaoAguaDiametro().getId().toString();

		}

		boletimCadastroTxt.append(Util.completaString(diametroLigAgua, 1));

		// Material Ligação Água
		String materialLigAgua = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getLigacaoAguaMaterial() != null){

			materialLigAgua = dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getLigacaoAguaMaterial().getId().toString();

		}

		boletimCadastroTxt.append(Util.completaString(materialLigAgua, 1));

		// Volume Reservatório Inferior
		String volumeReservatorioInferior = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getVolumeReservatorioInferior() != null){
			volumeReservatorioInferior = emitirDocumentoCobrancaBoletimCadastroHelper.getVolumeReservatorioInferior().toString();
		}

		boletimCadastroTxt.append(Util.completaString(volumeReservatorioInferior, 1));

		// Volume Reservatório Superior
		String volumeReservatorioSuperior = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getVolumeReservatorioSuperior() != null){
			volumeReservatorioSuperior = emitirDocumentoCobrancaBoletimCadastroHelper.getVolumeReservatorioSuperior().toString();
		}

		boletimCadastroTxt.append(Util.completaString(volumeReservatorioSuperior, 1));

		// Volume Piscina
		String volumePiscina = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getVolumePiscina() != null){
			volumePiscina = emitirDocumentoCobrancaBoletimCadastroHelper.getVolumePiscina().toString();
		}

		boletimCadastroTxt.append(Util.completaString(volumePiscina, 1));

		// Jardim
		String jardim = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getJardim() != null){
			jardim = emitirDocumentoCobrancaBoletimCadastroHelper.getJardim().toString();
		}

		boletimCadastroTxt.append(Util.completaString(jardim, 1));

		// Pavimento Calçada
		String pavimentoCalcada = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdPavimentoCalcada() != null){
			pavimentoCalcada = Util.adicionarZerosEsquedaNumero(2, emitirDocumentoCobrancaBoletimCadastroHelper.getIdPavimentoCalcada()
							.toString());
		}

		boletimCadastroTxt.append(Util.completaString(pavimentoCalcada, 2));

		// Pavimento Rua
		String pavimentoRua = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdPavimentoRua() != null){
			pavimentoRua = Util.adicionarZerosEsquedaNumero(2, emitirDocumentoCobrancaBoletimCadastroHelper.getIdPavimentoRua().toString());
		}

		boletimCadastroTxt.append(Util.completaString(pavimentoRua, 2));

		// Fonte de Abastecimento
		String fonteAbastecimento = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdFonteAbastecimento() != null){
			fonteAbastecimento = emitirDocumentoCobrancaBoletimCadastroHelper.getIdFonteAbastecimento().toString();
		}

		boletimCadastroTxt.append(Util.completaString(fonteAbastecimento, 1));

		// Tipo de Poço
		String pocoTipo = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdPoco() != null){
			pocoTipo = emitirDocumentoCobrancaBoletimCadastroHelper.getIdPoco().toString();
		}

		boletimCadastroTxt.append(Util.completaString(pocoTipo, 1));

		// Número de Pontos
		String numeroPontos = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroPontosUtilizacao() != null){
			numeroPontos = Util.adicionarZerosEsquedaNumero(4, emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroPontosUtilizacao()
							.toString());
		}

		boletimCadastroTxt.append(Util.completaString(numeroPontos, 4));

		// Situação de Esgoto
		String situacaoEsgoto = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdLigacaoEsgotoSituacao() != null){
			situacaoEsgoto = emitirDocumentoCobrancaBoletimCadastroHelper.getIdLigacaoEsgotoSituacao().toString();
		}

		boletimCadastroTxt.append(Util.completaString(situacaoEsgoto, 1));

		// Diâmetro Ligação Esgoto
		String diametroLigEsgoto = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoEsgoto() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoEsgoto().getLigacaoEsgotoDiametro() != null){

			diametroLigEsgoto = dadosLigacoesBoletimCadastroHelper.getLigacaoEsgoto().getLigacaoEsgotoDiametro().getId().toString();

		}

		boletimCadastroTxt.append(Util.completaString(diametroLigEsgoto, 1));

		// Material Ligação Esgoto
		String materialLigEsgoto = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoEsgoto() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoEsgoto().getLigacaoEsgotoMaterial() != null){

			materialLigEsgoto = dadosLigacoesBoletimCadastroHelper.getLigacaoEsgoto().getLigacaoEsgotoMaterial().getId().toString();

		}

		boletimCadastroTxt.append(Util.completaString(materialLigEsgoto, 1));

		// Perfil do Imóvel
		String perfilImovel = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovelPerfil() != null){
			perfilImovel = emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovelPerfil().toString();
		}

		boletimCadastroTxt.append(Util.completaString(perfilImovel, 1));

		// Despejo
		String despejo = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdDespejo() != null){
			despejo = emitirDocumentoCobrancaBoletimCadastroHelper.getIdDespejo().toString();
		}

		boletimCadastroTxt.append(Util.completaString(despejo, 1));

		// Dados do Hidrômetro na Ligação de Água
		// Leitura Inicial
		String leituraInicial = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){

			leituraInicial = Util.adicionarZerosEsquedaNumero(6, dadosLigacoesBoletimCadastroHelper.getLigacaoAgua()
							.getHidrometroInstalacaoHistorico().getNumeroLeituraInstalacao().toString());

		}

		boletimCadastroTxt.append(Util.completaString(leituraInicial, 6));

		// Capacidade
		String capacidadeHidrometro = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro()
										.getHidrometroCapacidade() != null){

			capacidadeHidrometro = Util.adicionarZerosEsquedaNumero(2, dadosLigacoesBoletimCadastroHelper.getLigacaoAgua()
							.getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroCapacidade().getId().toString());

		}

		boletimCadastroTxt.append(Util.completaString(capacidadeHidrometro, 2));

		// Marca
		String marcaHidrometro = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro()
										.getHidrometroMarca() != null){

			marcaHidrometro = Util.adicionarZerosEsquedaNumero(2, dadosLigacoesBoletimCadastroHelper.getLigacaoAgua()
							.getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroMarca().getId().toString());

		}

		boletimCadastroTxt.append(Util.completaString(marcaHidrometro, 2));

		// Local de Instalação do Hidrômetro
		String localInstalacaoHidrometro = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico()
										.getHidrometroLocalInstalacao() != null){

			localInstalacaoHidrometro = Util.adicionarZerosEsquedaNumero(2, dadosLigacoesBoletimCadastroHelper.getLigacaoAgua()
							.getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao().getId().toString());

		}

		boletimCadastroTxt.append(Util.completaString(localInstalacaoHidrometro, 2));

		// Proteção
		String protecaoHidrometro = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometroProtecao() != null){

			protecaoHidrometro = dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico()
							.getHidrometroProtecao().getId().toString();

		}

		boletimCadastroTxt.append(Util.completaString(protecaoHidrometro, 1));

		// Indicador Cavalete
		String indicadorCavalete = "";

		if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
						&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico()
										.getIndicadorExistenciaCavalete() != null){

			protecaoHidrometro = dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico()
							.getIndicadorExistenciaCavalete().toString();

		}

		boletimCadastroTxt.append(Util.completaString(indicadorCavalete, 1));

		// Número IPTU
		String numeroIptu = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroIptu() != null){
			numeroIptu = Util.adicionarZerosEsquedaNumero(26, "" + emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroIptu().intValue());
		}

		boletimCadastroTxt.append(Util.completaString(numeroIptu, 26));

		// Número Contrato CELPE
		String numeroCelpe = "";

		if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroCelpe() != null){
			numeroCelpe = Util.adicionarZerosEsquedaNumero(10, emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroCelpe().toString());
		}

		boletimCadastroTxt.append(Util.completaString(numeroCelpe, 10));
	}


	private Collection<RelatorioBoletimCadastralModelo2Bean> gerarRelatorioBoletimCadastralModelo2Beans(
					Collection colecaoEmitirBoletimCadastro) throws ControladorException{

		Collection<RelatorioBoletimCadastralModelo2Bean> colecaoRetorno = new ArrayList<RelatorioBoletimCadastralModelo2Bean>();
		Iterator itEmitirBoletimCadastroIterator = colecaoEmitirBoletimCadastro.iterator();

		EmitirDocumentoCobrancaBoletimCadastroHelper emitirDocumentoCobrancaBoletimCadastroHelper = null;

		while(itEmitirBoletimCadastroIterator.hasNext()){

			RelatorioBoletimCadastralModelo2Bean relatorioBoletimCadastralModelo2Bean = new RelatorioBoletimCadastralModelo2Bean();

			emitirDocumentoCobrancaBoletimCadastroHelper = (EmitirDocumentoCobrancaBoletimCadastroHelper) itEmitirBoletimCadastroIterator
							.next();


			ClienteEmitirBoletimCadastroHelper clienteEmitirBoletimCadastroHelperProprietario = null;
			ClienteEmitirBoletimCadastroHelper clienteEmitirBoletimCadastroHelperUsuario = null;
			ClienteEmitirBoletimCadastroHelper clienteEmitirBoletimCadastroHelperResponsavel = null;

			clienteEmitirBoletimCadastroHelperProprietario = getControladorCliente().pesquisarClienteEmitirBoletimCadastro(
							emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel(), ClienteRelacaoTipo.PROPRIETARIO);

			clienteEmitirBoletimCadastroHelperUsuario = getControladorCliente().pesquisarClienteEmitirBoletimCadastro(
							emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel(), ClienteRelacaoTipo.USUARIO);

			clienteEmitirBoletimCadastroHelperResponsavel = getControladorCliente().pesquisarClienteEmitirBoletimCadastro(
							emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel(), ClienteRelacaoTipo.RESPONSAVEL);

			Imovel imovel = null;

			if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel() != null){
				imovel = this.getControladorImovel().consultarImovelDadosCadastrais(
								emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel());
			}

			// Preencher Imóvel
			// this.preencherBeanImovel(relatorioBoletimCadastralModelo2Bean, );

			// Cidade
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getMunicipioImovel() != null
							&& emitirDocumentoCobrancaBoletimCadastroHelper.getMunicipioImovel().getNome() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelCidade(emitirDocumentoCobrancaBoletimCadastroHelper.getMunicipioImovel()
								.getNome());
			}

			// Local
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdLocalidade() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelLocal(emitirDocumentoCobrancaBoletimCadastroHelper.getIdLocalidade()
								.toString());
			}

			// Matrícula
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelMatricula(emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel()
								.toString());
			}
			
			// Setor Comercial
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoSetorComercial() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelSetor(emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoSetorComercial()
								.toString());
			}

			// Quadra
			relatorioBoletimCadastralModelo2Bean.setImovelQuadra(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroQuadra() + "");

			// Lote
			relatorioBoletimCadastralModelo2Bean.setImovelLote(emitirDocumentoCobrancaBoletimCadastroHelper.getLote() + "");

			// SubLote
			relatorioBoletimCadastralModelo2Bean.setImovelSubLote(emitirDocumentoCobrancaBoletimCadastroHelper.getSubLote() + "");

			// Rota
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getIdRota() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelRota(emitirDocumentoCobrancaBoletimCadastroHelper.getIdRota().toString());
			}

			// Controle: Não tem

			// Endereço
			String enderecoAbreviadoImovel = this.getControladorEndereco().pesquisarEndereco(
							emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel());

			if(enderecoAbreviadoImovel != null){
				relatorioBoletimCadastralModelo2Bean.setImovelEndereco(enderecoAbreviadoImovel);
			}
			
			// Número do Imóvel
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroImovel() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelNumero(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroImovel().toString());
			}

			// Código do Logradouro: Não tem

			// Bairro
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getNomeBairro() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelBairro(emitirDocumentoCobrancaBoletimCadastroHelper.getNomeBairro());
			}

			// Referência
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getEnderecoReferencia() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelReferencia(emitirDocumentoCobrancaBoletimCadastroHelper
								.getEnderecoReferencia().getDescricao());
			}

			// Complemento
			if(imovel != null && imovel.getComplementoEndereco() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelComplemento(imovel.getComplementoEndereco());
			}

			// CEP
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getCodigoCep() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelCEP(Util.formatarCEPSemPonto(emitirDocumentoCobrancaBoletimCadastroHelper
								.getCodigoCep().toString()));
			}

			// Categoria
			Collection imovelSubcategorias = this.getControladorImovel().pesquisarCategoriasImovel(
							emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel());

			if(!imovelSubcategorias.isEmpty()){
				
				ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) Util.retonarObjetoDeColecao(imovelSubcategorias);

				if(imovelSubcategoria.getComp_id() != null
								&& imovelSubcategoria.getComp_id().getSubcategoria() != null
								&& imovelSubcategoria.getComp_id().getSubcategoria().getCategoria() != null
								&& imovelSubcategoria.getComp_id().getSubcategoria().getCategoria().getDescricao() != null){

					relatorioBoletimCadastralModelo2Bean.setImovelCategoria(imovelSubcategoria.getComp_id().getSubcategoria()
									.getCategoria().getDescricao());
				}
				
				// SubCategoria
				if(imovelSubcategoria.getComp_id() != null && imovelSubcategoria.getComp_id().getSubcategoria() != null
								&& imovelSubcategoria.getComp_id().getSubcategoria().getDescricao() != null){

					String subCategoria = imovelSubcategoria.getComp_id().getSubcategoria().getDescricao();
					if(subCategoria.length() > 23){
						subCategoria = subCategoria.substring(0, 23);
					}

					relatorioBoletimCadastralModelo2Bean.setImovelSubCategoria(subCategoria);
				}
				
				// Nº de Economias
				if(imovelSubcategoria.getQuantidadeEconomias() > 0){
					relatorioBoletimCadastralModelo2Bean.setImovelNuEconomias(imovelSubcategoria.getQuantidadeEconomias() + "");
				}

			}

			// Pavimento Rua
			if(imovel != null && imovel.getPavimentoRua() != null){

				relatorioBoletimCadastralModelo2Bean.setImovelPavRua(imovel.getPavimentoRua().getDescricao());
			}

			// Pavimento Calçada
			if(imovel != null && imovel.getPavimentoCalcada() != null){

				relatorioBoletimCadastralModelo2Bean.setImovelPavCalcada(imovel.getPavimentoCalcada().getDescricao());
			}
			
			// Situação Ligação de Água
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getDsLigacaoAguaSituacao() != null){

				String sitLigAgua = emitirDocumentoCobrancaBoletimCadastroHelper.getDsLigacaoAguaSituacao();

				if(sitLigAgua.length() > 13){
					sitLigAgua = sitLigAgua.substring(0, 13);
				}

				relatorioBoletimCadastralModelo2Bean.setImovelSitLigAgua(sitLigAgua);
			}

			// Situação Ligação de Esgoto
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getDsLigacaoEsgotoSituacao() != null){

				String sitLigEsgoto = emitirDocumentoCobrancaBoletimCadastroHelper.getDsLigacaoEsgotoSituacao();

				if(sitLigEsgoto.length() > 12){
					sitLigEsgoto = sitLigEsgoto.substring(0, 12);
				}

				relatorioBoletimCadastralModelo2Bean.setImovelSitLigEsgoto(sitLigEsgoto);
			}

			// Fonte de abastecimento
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getFonteAbastecimento() != null){

				String fonteAbastecimento = emitirDocumentoCobrancaBoletimCadastroHelper.getFonteAbastecimento();

				if(fonteAbastecimento.length() > 10){
					fonteAbastecimento = fonteAbastecimento.substring(0, 10);
				}

				relatorioBoletimCadastralModelo2Bean.setImovelFonteAbastecimento(fonteAbastecimento);
			}
			
			// Nº de Pontos
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroPontosUtilizacao() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelNuPontos(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroPontosUtilizacao().toString());
			}
			
			// Nº de Moradores
			if(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroMorador() != null){
				relatorioBoletimCadastralModelo2Bean.setImovelNuMoradores(emitirDocumentoCobrancaBoletimCadastroHelper.getNumeroMorador().toString());
			}

			// ///////////////
			// PROPRIETÁRIO//
			// ///////////////

			if(clienteEmitirBoletimCadastroHelperProprietario != null){

				
				if(clienteEmitirBoletimCadastroHelperProprietario.getCliente() != null) {
					
					// Nome
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getNome() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioNome(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getNome());
					}
				
					// Código do Cliente
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getId() != null){
						
						relatorioBoletimCadastralModelo2Bean.setProprietarioCodCliente(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getId().toString());
					}
					
					// Tipo de Cliente
					if(clienteEmitirBoletimCadastroHelperProprietario.getDsClienteTipo() != null){
						
						relatorioBoletimCadastralModelo2Bean.setProprietarioTipoCliente(clienteEmitirBoletimCadastroHelperProprietario
										.getDsClienteTipo());
					}

					// CPF
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getCpfFormatado() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioCPF(clienteEmitirBoletimCadastroHelperProprietario.getCliente()
										.getCpfFormatado());
					}
					
					// RG
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getRg() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioRG(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getRg());
					}
					
					// Data de Expedição
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getDataEmissaoRg() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioDtExpedicao(Util
										.formatarData(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getDataEmissaoRg()));
					}
					
					// Órgão Expedidor
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getOrgaoExpedidorRg() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioOrgaoExpedidor(clienteEmitirBoletimCadastroHelperProprietario
										.getCliente().getOrgaoExpedidorRg().getDescricaoAbreviada().toString());
					}

					// Estado
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getUnidadeFederacao() != null
									&& clienteEmitirBoletimCadastroHelperProprietario.getCliente().getUnidadeFederacao().getSigla() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioEstado(clienteEmitirBoletimCadastroHelperProprietario
										.getCliente().getUnidadeFederacao().getSigla());
					}

					// Data de Nascimento
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getDataNascimento() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioDtNascimento(Util
										.formatarData(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getDataNascimento()));
					}

					// CNPJ
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getCnpjFormatado() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioCNPJ(clienteEmitirBoletimCadastroHelperProprietario
										.getCliente().getCnpjFormatado());
					}

					// Inscrição Estadual
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getInscricaoEstadualFormatada() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioInscEstadual(clienteEmitirBoletimCadastroHelperProprietario
										.getCliente().getInscricaoEstadualFormatada());
					}

					// Nome Mãe
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getNomeMae() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioNomeMae(clienteEmitirBoletimCadastroHelperProprietario
										.getCliente().getNomeMae());
					}
					
					// E-Mail
					if(clienteEmitirBoletimCadastroHelperProprietario.getCliente().getEmail() != null){

						relatorioBoletimCadastralModelo2Bean.setProprietarioEmail(clienteEmitirBoletimCadastroHelperProprietario
										.getCliente().getEmail());
					}

				}// Fim do .getCliente()

				// Endereço
				if(clienteEmitirBoletimCadastroHelperProprietario.getEnderecoFormatado() != null){

					relatorioBoletimCadastralModelo2Bean
									.setProprietarioEndereco(clienteEmitirBoletimCadastroHelperProprietario.getEnderecoFormatado());

					if(clienteEmitirBoletimCadastroHelperProprietario.getClienteEndereco() != null){

						// Número
						if(clienteEmitirBoletimCadastroHelperProprietario.getClienteEndereco().getNumero() != null){

							relatorioBoletimCadastralModelo2Bean.setProprietarioNumero(clienteEmitirBoletimCadastroHelperProprietario
											.getClienteEndereco().getNumero());
						}

						// Código do Logradouro: Não tem

					}// Fim do getClienteEndereco()
				}

				if(clienteEmitirBoletimCadastroHelperProprietario.getRamoAtividade() != null){
					relatorioBoletimCadastralModelo2Bean.setProprietarioRamoAtividade(clienteEmitirBoletimCadastroHelperProprietario
									.getRamoAtividade());
				}
				
				if(clienteEmitirBoletimCadastroHelperProprietario.getClientesFone() != null
								&& !clienteEmitirBoletimCadastroHelperProprietario.getClientesFone().isEmpty()){
					
					ClienteFone fone = (ClienteFone) Util.retonarObjetoDeColecao(clienteEmitirBoletimCadastroHelperProprietario
									.getClientesFone());
					
					if(fone.getDdd() != null && fone.getTelefone() != null){
						relatorioBoletimCadastralModelo2Bean.setProprietarioFone("(" + fone.getDdd() + ")"
										+ Util.formatarFone(fone.getTelefone()));
					}else if(fone.getTelefone() != null){
						relatorioBoletimCadastralModelo2Bean.setProprietarioFone(Util.formatarFone(fone.getTelefone()));
					}
				}
					

			}

			// //////////
			// USUÁRIO //
			// //////////
			if(clienteEmitirBoletimCadastroHelperUsuario != null){

				if(clienteEmitirBoletimCadastroHelperUsuario.getCliente() != null){

					// Nome
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getNome() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioNome(clienteEmitirBoletimCadastroHelperUsuario.getCliente()
										.getNome());
					}

					// Código do Cliente
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getId() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioCodCliente(clienteEmitirBoletimCadastroHelperUsuario.getCliente()
										.getId().toString());
					}

					// Tipo de Cliente
					if(clienteEmitirBoletimCadastroHelperUsuario.getDsClienteTipo() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioTipoCliente(clienteEmitirBoletimCadastroHelperUsuario
										.getDsClienteTipo());
					}

					// CPF
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getCpfFormatado() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioCPF(clienteEmitirBoletimCadastroHelperUsuario.getCliente()
										.getCpfFormatado());
					}

					// RG
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getRg() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioRG(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getRg());
					}

					// Data de Expedição
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getDataEmissaoRg() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioDtExpedicao(Util
										.formatarData(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getDataEmissaoRg()));
					}

					// Órgão Expedidor
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getOrgaoExpedidorRg() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioOrgaoExpedidor(clienteEmitirBoletimCadastroHelperUsuario
										.getCliente().getOrgaoExpedidorRg().getDescricaoAbreviada().toString());
					}

					// Estado
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getUnidadeFederacao() != null
									&& clienteEmitirBoletimCadastroHelperUsuario.getCliente().getUnidadeFederacao().getSigla() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioEstado(clienteEmitirBoletimCadastroHelperUsuario.getCliente()
										.getUnidadeFederacao().getSigla());
					}

					// Data de Nascimento
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getDataNascimento() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioDtNascimento(Util
										.formatarData(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getDataNascimento()));
					}

					// CNPJ
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getCnpjFormatado() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioCNPJ(clienteEmitirBoletimCadastroHelperUsuario.getCliente()
										.getCnpjFormatado());
					}

					// Inscrição Estadual
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getInscricaoEstadualFormatada() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioInscEstadual(clienteEmitirBoletimCadastroHelperUsuario.getCliente()
										.getInscricaoEstadualFormatada());
					}

					// Nome Mãe
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getNomeMae() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioNomeMae(clienteEmitirBoletimCadastroHelperUsuario.getCliente()
										.getNomeMae());
					}

					// E-Mail
					if(clienteEmitirBoletimCadastroHelperUsuario.getCliente().getEmail() != null){

						relatorioBoletimCadastralModelo2Bean.setUsuarioEmail(clienteEmitirBoletimCadastroHelperUsuario.getCliente()
										.getEmail());
					}

				}// Fim do .getCliente()
				
				// Endereço
				if(clienteEmitirBoletimCadastroHelperUsuario.getEnderecoFormatado() != null){

					relatorioBoletimCadastralModelo2Bean
									.setUsuarioEndereco(clienteEmitirBoletimCadastroHelperUsuario.getEnderecoFormatado());

					if(clienteEmitirBoletimCadastroHelperUsuario.getClienteEndereco() != null){

						// Número
						if(clienteEmitirBoletimCadastroHelperUsuario.getClienteEndereco().getNumero() != null){

							relatorioBoletimCadastralModelo2Bean.setUsuarioNumero(clienteEmitirBoletimCadastroHelperUsuario
											.getClienteEndereco().getNumero());
						}

						// Código do Logradouro: Não tem

					}// Fim do getClienteEndereco()
				}



				if(clienteEmitirBoletimCadastroHelperUsuario.getRamoAtividade() != null){
					relatorioBoletimCadastralModelo2Bean.setUsuarioRamoAtividade(clienteEmitirBoletimCadastroHelperUsuario
									.getRamoAtividade());
				}

				if(clienteEmitirBoletimCadastroHelperUsuario.getClientesFone() != null
								&& !clienteEmitirBoletimCadastroHelperUsuario.getClientesFone().isEmpty()){

					ClienteFone fone = (ClienteFone) Util.retonarObjetoDeColecao(clienteEmitirBoletimCadastroHelperUsuario
									.getClientesFone());

					if(fone.getDdd() != null && fone.getTelefone() != null){
						relatorioBoletimCadastralModelo2Bean.setUsuarioFone("(" + fone.getDdd() + ")"
										+ Util.formatarFone(fone.getTelefone()));
					}else if(fone.getTelefone() != null){
						relatorioBoletimCadastralModelo2Bean.setUsuarioFone(Util.formatarFone(fone.getTelefone()));
					}
				}

			}

			// ///////////////
			// RESPONSÁVEL //
			// ///////////////

			if(clienteEmitirBoletimCadastroHelperResponsavel != null){

				if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente() != null){

					// Nome
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getNome() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelNome(clienteEmitirBoletimCadastroHelperResponsavel.getCliente()
										.getNome());
					}

					// Código do Cliente
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getId() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelCodCliente(clienteEmitirBoletimCadastroHelperResponsavel
										.getCliente().getId().toString());
					}

					// Tipo de Cliente
					if(clienteEmitirBoletimCadastroHelperResponsavel.getDsClienteTipo() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelTipoCliente(clienteEmitirBoletimCadastroHelperResponsavel
										.getDsClienteTipo());
					}

					// CPF
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getCpfFormatado() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelCPF(clienteEmitirBoletimCadastroHelperResponsavel.getCliente()
										.getCpfFormatado());
					}

					// RG
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getRg() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelRG(clienteEmitirBoletimCadastroHelperResponsavel.getCliente()
										.getRg());
					}

					// Data de Expedição
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getDataEmissaoRg() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelDtExpedicao(Util
										.formatarData(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getDataEmissaoRg()));
					}

					// Órgão Expedidor
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getOrgaoExpedidorRg() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelOrgaoExpedidor(clienteEmitirBoletimCadastroHelperResponsavel
										.getCliente().getOrgaoExpedidorRg().getDescricaoAbreviada().toString());
					}

					// Estado
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getUnidadeFederacao() != null
									&& clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getUnidadeFederacao().getSigla() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelEstado(clienteEmitirBoletimCadastroHelperResponsavel
										.getCliente().getUnidadeFederacao().getSigla());
					}

					// Data de Nascimento
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getDataNascimento() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelDtNascimento(Util
										.formatarData(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getDataNascimento()));
					}

					// CNPJ
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getCnpjFormatado() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelCNPJ(clienteEmitirBoletimCadastroHelperResponsavel.getCliente()
										.getCnpjFormatado());
					}

					// Inscrição Estadual
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getInscricaoEstadualFormatada() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelInscEstadual(clienteEmitirBoletimCadastroHelperResponsavel
										.getCliente().getInscricaoEstadualFormatada());
					}

					// Nome Mãe
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getNomeMae() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelNomeMae(clienteEmitirBoletimCadastroHelperResponsavel
										.getCliente().getNomeMae());
					}

					// E-Mail
					if(clienteEmitirBoletimCadastroHelperResponsavel.getCliente().getEmail() != null){

						relatorioBoletimCadastralModelo2Bean.setResponsavelEmail(clienteEmitirBoletimCadastroHelperResponsavel.getCliente()
										.getEmail());
					}

				}// Fim do .getCliente()

				// Endereço
				if(clienteEmitirBoletimCadastroHelperResponsavel.getEnderecoFormatado() != null){

					relatorioBoletimCadastralModelo2Bean
									.setResponsavelEndereco(clienteEmitirBoletimCadastroHelperResponsavel.getEnderecoFormatado());
					
					if(clienteEmitirBoletimCadastroHelperResponsavel.getClienteEndereco() != null){

						// Número
						if(clienteEmitirBoletimCadastroHelperResponsavel.getClienteEndereco().getNumero() != null){

							relatorioBoletimCadastralModelo2Bean.setResponsavelNumero(clienteEmitirBoletimCadastroHelperResponsavel
											.getClienteEndereco().getNumero());
						}

						// Código do Logradouro: Não tem

					}// Fim do getClienteEndereco()
				}				

				if(clienteEmitirBoletimCadastroHelperResponsavel.getRamoAtividade() != null){
					relatorioBoletimCadastralModelo2Bean.setResponsavelRamoAtividade(clienteEmitirBoletimCadastroHelperResponsavel
									.getRamoAtividade());
				}

				if(clienteEmitirBoletimCadastroHelperResponsavel.getClientesFone() != null
								&& !clienteEmitirBoletimCadastroHelperResponsavel.getClientesFone().isEmpty()){

					ClienteFone fone = (ClienteFone) Util.retonarObjetoDeColecao(clienteEmitirBoletimCadastroHelperResponsavel
									.getClientesFone());

					if(fone.getDdd() != null && fone.getTelefone() != null){
						relatorioBoletimCadastralModelo2Bean.setResponsavelFone("(" + fone.getDdd() + ")"
										+ Util.formatarFone(fone.getTelefone()));
					}else if(fone.getTelefone() != null){
						relatorioBoletimCadastralModelo2Bean.setResponsavelFone(Util.formatarFone(fone.getTelefone()));
					}
				}

			}

			// ////////////////////
			// DADOS DA LIGAÇÃO //
			// ////////////////////

			// Obtém os dados das ligações de água e esgoto
			DadosLigacoesBoletimCadastroHelper dadosLigacoesBoletimCadastroHelper = getControladorAtendimentoPublico()
							.obterDadosLigacaoAguaEsgoto(emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel());

			// Data da Ligação de Água
			if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null
							&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getDataLigacao() != null){

				relatorioBoletimCadastralModelo2Bean.setDataLigacao(Util.formatarData(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua()
								.getDataLigacao()));

			}

			if(dadosLigacoesBoletimCadastroHelper != null && dadosLigacoesBoletimCadastroHelper.getLigacaoAgua() != null){

				// Data do Corte
				if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getDataCorte() != null){

					relatorioBoletimCadastralModelo2Bean.setDataCorte(Util.formatarData(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua()
									.getDataCorte()));

				}

				// Data da Religação
				if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getDataReligacao() != null){

					relatorioBoletimCadastralModelo2Bean.setDataReligacao(Util.formatarData(dadosLigacoesBoletimCadastroHelper
									.getLigacaoAgua().getDataReligacao()));

				}

				// Data da Supressão
				if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getDataSupressao() != null){

					relatorioBoletimCadastralModelo2Bean.setDataSupressao(Util.formatarData(dadosLigacoesBoletimCadastroHelper
									.getLigacaoAgua().getDataSupressao()));

				}

				// Data da Restabelecimento
				if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getDataRestabelecimentoAgua() != null){

					relatorioBoletimCadastralModelo2Bean.setDataRestabelecimento(Util.formatarData(dadosLigacoesBoletimCadastroHelper
									.getLigacaoAgua().getDataRestabelecimentoAgua()));

				}

				// Nº Hidrômetro
				if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
								&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro() != null
								&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro()
												.getNumero() != null){

					relatorioBoletimCadastralModelo2Bean.setHidrometro(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua()
									.getHidrometroInstalacaoHistorico().getHidrometro().getNumero());

				}

				// Data da Instalação de Hidrômetro
				if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
								&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico()
												.getDataInstalacao() != null){

					relatorioBoletimCadastralModelo2Bean.setDataInstalacao(Util.formatarData(dadosLigacoesBoletimCadastroHelper
									.getLigacaoAgua().getHidrometroInstalacaoHistorico().getDataInstalacao()));

				}
				
				// Proteção
				if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
								&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometroProtecao() != null
								&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometroProtecao().getDescricao() != null){

					String dsProtecao = dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico()
									.getHidrometroProtecao().getDescricao();

					if(dsProtecao.length() > 32){
						dsProtecao = dsProtecao.substring(0, 32);
					}

					relatorioBoletimCadastralModelo2Bean.setProtecao(dsProtecao);

				}

				// Cavalete
				if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
								&& dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico()
												.getIndicadorExistenciaCavalete() != null){

					boolean inCavalete = false;
					if(dadosLigacoesBoletimCadastroHelper.getLigacaoAgua().getHidrometroInstalacaoHistorico()
									.getIndicadorExistenciaCavalete().equals(ConstantesSistema.SIM)){
						inCavalete = true;
					}

					relatorioBoletimCadastralModelo2Bean.setCavalete(inCavalete);
				}
			}

			// Collection parmsclienteImovelLigacaoAgua =
			// this.getControladorMicromedicao().pesquiarImovelExcecoesApresentaDados(
			// emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel(), true);
			//
			// Object[] parmClienteImovel = null;
			// if(!parmsclienteImovelLigacaoAgua.isEmpty()){
			//
			// parmClienteImovel = (Object[]) parmsclienteImovelLigacaoAgua.iterator().next();
			//
			// // N° Hidrômetro
			// if(parmClienteImovel[9] != null){
			// relatorioBoletimCadastralModelo2Bean.setHidrometro((String) parmClienteImovel[9]);
			// }
			//
			// // data da instalação de hidrometro
			// if(parmClienteImovel[10] != null){
			// relatorioBoletimCadastralModelo2Bean.setDataInstalacao(Util.formatarData((Date)
			// parmClienteImovel[10]));
			// }
			// }

			MedicaoHistorico microMedicao = null;

			Collection parmsMedicaoHistoricoLigacaoAgua = this.getControladorMicromedicao().carregarDadosMedicao(
							emitirDocumentoCobrancaBoletimCadastroHelper.getIdImovel(), true);

			if(parmsMedicaoHistoricoLigacaoAgua != null && !parmsMedicaoHistoricoLigacaoAgua.isEmpty()){

				// recupera o último registro (registro mais atual)
				microMedicao = (MedicaoHistorico) parmsMedicaoHistoricoLigacaoAgua.toArray()[parmsMedicaoHistoricoLigacaoAgua.size() - 1];

				// leitura informada (medida)
				if(microMedicao.getLeituraAtualInformada() != null){
					relatorioBoletimCadastralModelo2Bean.setLeitura(microMedicao.getLeituraAtualInformada().toString());
				}
			}

			colecaoRetorno.add(relatorioBoletimCadastralModelo2Bean);
		}

		return colecaoRetorno;
	}

	private BigDecimal consultarValorDebitoImovel(String idImovel) throws ControladorException{

		BigDecimal valorConta = BigDecimal.ZERO;
		// seta valores constantes para chamar o metodo que consulta debitos do imovel
		Integer tipoImovel = Integer.valueOf(1);
		Integer indicadorPagamento = Integer.valueOf(1);
		Integer indicadorConta = Integer.valueOf(1);
		Integer indicadorDebito = Integer.valueOf(1);
		Integer indicadorCredito = Integer.valueOf(1);
		Integer indicadorNotas = Integer.valueOf(1);
		Integer indicadorGuias = Integer.valueOf(1);
		Integer indicadorAtualizar = Integer.valueOf(1);
		Short indicadorConsiderarPagamentoNaoClassificado = 1;
		
		String anoMesInicial = "000101";
		String anoMesFinal = "999912";

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Date dataVencimentoDebitoI;
		Date dataVencimentoDebitoF;

		try{
			dataVencimentoDebitoI = formatoData.parse("01/01/0001");
		}catch(ParseException ex){
			dataVencimentoDebitoI = null;
		}
		try{
			dataVencimentoDebitoF = formatoData.parse("31/12/9999");
		}catch(ParseException ex){
			dataVencimentoDebitoF = null;
		}

		// Obtendo os débitos do imovel
		ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = this.getControladorCobranca().obterDebitoImovelOuCliente(
						tipoImovel.intValue(), idImovel.trim(), null, null, anoMesInicial, anoMesFinal, dataVencimentoDebitoI,
						dataVencimentoDebitoF, indicadorPagamento.intValue(), indicadorConta.intValue(), indicadorDebito.intValue(),
						indicadorCredito.intValue(), indicadorNotas.intValue(), indicadorGuias.intValue(), indicadorAtualizar.intValue(),
						null, null, new Date(), ConstantesSistema.SIM, indicadorConsiderarPagamentoNaoClassificado, ConstantesSistema.SIM,
						ConstantesSistema.SIM, ConstantesSistema.SIM);

		Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoImovel.getColecaoContasValores();

		if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){

			Iterator<ContaValoresHelper> colecaoContaValoresIterator = colecaoContaValores.iterator();

			ContaValoresHelper dadosConta = null;

			// percorre a colecao de conta somando o valor para obter um valor total
			while(colecaoContaValoresIterator.hasNext()){

				dadosConta = colecaoContaValoresIterator.next();
				valorConta = valorConta.add(dadosConta.getConta().getValorTotal()
								.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

			}
		}

		return valorConta;
	}

	public void adicionarDadosClienteEmitirBoletimCadastroTxt(StringBuilder boletimCadastroTxt,
					ClienteEmitirBoletimCadastroHelper clienteEmitirBoletimCadastroHelper){

		// Dados do Cliente
		// Id do Cliente
		String idClienteFormatado = Util.adicionarZerosEsquedaNumero(9, clienteEmitirBoletimCadastroHelper.getCliente().getId().toString());

		boletimCadastroTxt.append(idClienteFormatado);

		// Nome do Cliente
		String nomeCliente = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getNome() != null){
			nomeCliente = clienteEmitirBoletimCadastroHelper.getCliente().getNome();
		}

		boletimCadastroTxt.append(Util.completaString(nomeCliente, 23));

		// Tipo do Cliente
		String tipoCliente = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getClienteTipo() != null){
			tipoCliente = Util.adicionarZerosEsquedaNumero(2, clienteEmitirBoletimCadastroHelper.getCliente().getClienteTipo().getId()
							.toString());
		}

		boletimCadastroTxt.append(Util.completaString(tipoCliente, 2));

		// CPF/CNPJ
		String cpfCnpj = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getCpf() != null){
			cpfCnpj = Util.adicionarZerosEsquedaNumero(14, clienteEmitirBoletimCadastroHelper.getCliente().getCpf());
		}

		if(clienteEmitirBoletimCadastroHelper.getCliente().getCnpj() != null){
			cpfCnpj = Util.adicionarZerosEsquedaNumero(14, clienteEmitirBoletimCadastroHelper.getCliente().getCnpj());
		}

		boletimCadastroTxt.append(Util.completaString(cpfCnpj, 14));

		// RG
		String rg = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getRg() != null){
			rg = Util.adicionarZerosEsquedaNumero(13, clienteEmitirBoletimCadastroHelper.getCliente().getRg());
		}

		boletimCadastroTxt.append(Util.completaString(rg, 13));

		// Data de Emissão RG
		String dataEmissaoRG = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getDataEmissaoRg() != null){
			dataEmissaoRG = Util.formatarData(clienteEmitirBoletimCadastroHelper.getCliente().getDataEmissaoRg()).replace("/", "");
		}

		boletimCadastroTxt.append(Util.completaString(dataEmissaoRG, 8));

		// Órgão Expedidor RG
		String orgaoExpedidorRG = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getOrgaoExpedidorRg() != null){
			orgaoExpedidorRG = clienteEmitirBoletimCadastroHelper.getCliente().getOrgaoExpedidorRg().getDescricaoAbreviada();
		}

		boletimCadastroTxt.append(Util.completaString(orgaoExpedidorRG, 4));

		// Unidade Federação
		String unidadeFederacao = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getUnidadeFederacao() != null){
			unidadeFederacao = clienteEmitirBoletimCadastroHelper.getCliente().getUnidadeFederacao().getSigla();
		}

		boletimCadastroTxt.append(Util.completaString(unidadeFederacao, 2));

		// Data de Nascimento
		String dataNascimento = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getDataNascimento() != null){
			dataNascimento = Util.formatarData(clienteEmitirBoletimCadastroHelper.getCliente().getDataNascimento()).replace("/", "");
		}

		boletimCadastroTxt.append(Util.completaString(dataNascimento, 8));

		// Profissão
		String profissao = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getProfissao() != null){
			profissao = clienteEmitirBoletimCadastroHelper.getCliente().getProfissao().getDescricao();
		}

		boletimCadastroTxt.append(Util.completaString(profissao, 18));

		// Pessoa Sexo
		String sexo = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getPessoaSexo() != null){
			sexo = clienteEmitirBoletimCadastroHelper.getCliente().getPessoaSexo().getId().toString();
		}

		boletimCadastroTxt.append(Util.completaString(sexo, 1));

		// Nome da Mãe
		String nomeMae = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getNomeMae() != null){
			nomeMae = clienteEmitirBoletimCadastroHelper.getCliente().getNomeMae();
		}

		boletimCadastroTxt.append(Util.completaString(nomeMae, 32));

		// Indicador de Uso
		String indicadorUso = "";

		if(clienteEmitirBoletimCadastroHelper.getCliente().getIndicadorUso() != null){
			indicadorUso = clienteEmitirBoletimCadastroHelper.getCliente().getIndicadorUso().toString();
		}

		boletimCadastroTxt.append(Util.completaString(indicadorUso, 1));

		// Dados do Endereço do Cliente
		// Tipo de Endereço
		String tipoEndereco = "";

		if(clienteEmitirBoletimCadastroHelper.getClienteEndereco().getEnderecoTipo() != null){
			tipoEndereco = clienteEmitirBoletimCadastroHelper.getClienteEndereco().getEnderecoTipo().getId().toString();
		}

		boletimCadastroTxt.append(Util.completaString(tipoEndereco, 1));

		// Logradouro
		String logradouro = "";

		if(clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroCep() != null
						&& clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroCep().getLogradouro() != null){
			logradouro = Util.adicionarZerosEsquedaNumero(9, clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroCep()
							.getLogradouro().getId().toString());
		}

		boletimCadastroTxt.append(Util.completaString(logradouro, 9));

		// Endereço Abreviado
		String endereco = "";

		if(clienteEmitirBoletimCadastroHelper.getEnderecoFormatado() != null){
			endereco = clienteEmitirBoletimCadastroHelper.getEnderecoFormatado();
		}

		boletimCadastroTxt.append(Util.completaString(endereco, 25));

		// CEP
		String cep = "";

		if(clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroCep() != null
						&& clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroCep().getCep() != null){
			cep = Util.adicionarZerosEsquedaNumero(8, clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroCep().getCep()
							.getCodigo().toString());
		}

		boletimCadastroTxt.append(Util.completaString(cep, 8));

		// Bairro
		String bairro = "";

		if(clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroBairro() != null
						&& clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroBairro().getBairro() != null){
			bairro = Util.adicionarZerosEsquedaNumero(3, ""
							+ clienteEmitirBoletimCadastroHelper.getClienteEndereco().getLogradouroBairro().getBairro().getCodigo());
		}

		boletimCadastroTxt.append(Util.completaString(bairro, 3));

		// Referência
		String referencia = "";

		if(clienteEmitirBoletimCadastroHelper.getClienteEndereco().getEnderecoReferencia() != null){
			referencia = clienteEmitirBoletimCadastroHelper.getClienteEndereco().getEnderecoReferencia().getId().toString();
		}

		boletimCadastroTxt.append(Util.completaString(referencia, 1));

		// Número do Imóvel
		String numeroImovel = "";

		if(clienteEmitirBoletimCadastroHelper.getClienteEndereco().getNumero() != null){
			numeroImovel = Util.adicionarZerosEsquedaNumero(5, clienteEmitirBoletimCadastroHelper.getClienteEndereco().getNumero()
							.toString());
		}

		boletimCadastroTxt.append(Util.completaString(numeroImovel, 5));

		// Complemento
		String complemento = "";

		if(clienteEmitirBoletimCadastroHelper.getClienteEndereco().getComplemento() != null){
			complemento = clienteEmitirBoletimCadastroHelper.getClienteEndereco().getComplemento();
		}

		boletimCadastroTxt.append(Util.completaString(complemento, 19));

		// Dados do Telefone do Cliente
		// Tipo do Telefone
		Collection clientesFone = clienteEmitirBoletimCadastroHelper.getClientesFone();

		if(clientesFone != null && !clientesFone.isEmpty()){

			Iterator clientesFoneIterator = clientesFone.iterator();

			int tamanho = clientesFone.size();

			while(clientesFoneIterator.hasNext()){

				ClienteFone clienteFone = (ClienteFone) clientesFoneIterator.next();

				String tipoTelefone = "";

				if(clienteFone.getFoneTipo() != null){
					tipoTelefone = clienteFone.getFoneTipo().getId().toString();
				}

				boletimCadastroTxt.append(Util.completaString(tipoTelefone, 1));

				// DDD
				String ddd = "";

				if(clienteFone.getDdd() != null){
					ddd = Util.adicionarZerosEsquedaNumero(2, clienteFone.getDdd());
				}

				boletimCadastroTxt.append(Util.completaString(ddd, 2));

				// Número do Telefone
				String numeroTelefone = "";

				if(clienteFone.getTelefone() != null){
					numeroTelefone = clienteFone.getTelefone();
				}

				boletimCadastroTxt.append(Util.completaString(numeroTelefone, 8));

				// Ramal
				String ramal = "";

				if(clienteFone.getRamal() != null){
					ramal = clienteFone.getRamal();
				}

				boletimCadastroTxt.append(Util.completaString(ramal, 4));

			}

			if(tamanho == 1){
				boletimCadastroTxt.append(Util.completaString("", 15));
			}

		}else{
			boletimCadastroTxt.append(Util.completaString("", 30));
		}

		boletimCadastroTxt.append(Util.completaString("", 8));
	}

	/**
	 * Permite inserir uma Anormalidade de Leitura
	 * [UC0217] Inserir Anormalidade Leitura
	 * 
	 * @author Thiago Tenório
	 * @date 30/03/2006
	 */
	public Integer inserirClienteTipo(ClienteTipo clienteTipo, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_TIPO_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CLIENTE_TIPO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		clienteTipo.setOperacaoEfetuada(operacaoEfetuada);
		clienteTipo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(clienteTipo);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		Integer id = (Integer) getControladorUtil().inserir(clienteTipo);

		return id;

	}

	/**
	 * Permite inserir um Anormalidade de Leitura
	 * [UC0217] Inserir Anormalidade de Leitura
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 20/01/2011
	 */
	public Integer inserirClienteResponsavel(ClienteResponsavel clienteResponsavel, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_RESPONSAVEL_INSERIR, clienteResponsavel
						.getCliente().getId(), clienteResponsavel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CLIENTE_RESPONSAVEL_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		clienteResponsavel.setOperacaoEfetuada(operacaoEfetuada);
		clienteResponsavel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(clienteResponsavel);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		Integer id = (Integer) getControladorUtil().inserir(clienteResponsavel);
		clienteResponsavel.setId(id);

		return id;

	}

	/**
	 * Permite inserir um Anormalidade de Leitura
	 * [UC0217] Inserir Anormalidade de Leitura
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 20/01/2011
	 */
	public Integer inserirImovelAguaParaTodos(ImovelAguaParaTodos imovelAguaParaTodos, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_AGUA_PARA_TODOS_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_IMOVEL_AGUA_PARA_TODOS_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		imovelAguaParaTodos.setOperacaoEfetuada(operacaoEfetuada);
		registradorOperacao.registrarOperacao(imovelAguaParaTodos);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		Integer id = (Integer) getControladorUtil().inserir(imovelAguaParaTodos);

		return id;

	}

	/**
	 * [UC0298] Manter Agência bancária [] Atualizar Agência Bancária Metodo que
	 * atualiza a Agência Bancária
	 * 
	 * @author Thiago Tenório
	 * @date 25/05/2006
	 * @throws ControladorException
	 */

	public void atualizarClienteTipo(ClienteTipo clienteTipo) throws ControladorException{

		// Verifica se todos os campos obrigatorios foram preenchidos

		if((clienteTipo.getId() == null || clienteTipo.getId().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (clienteTipo.getDescricao() == null || clienteTipo.getDescricao().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (clienteTipo.getEsferaPoder() == null || clienteTipo.getEsferaPoder().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (clienteTipo.getIndicadorPessoaFisicaJuridica() == null || clienteTipo.getIndicadorPessoaFisicaJuridica()
										.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.filtro.nenhum_parametro_informado");

		}

		// Verifica se o campo Descrição foi preenchido

		if(clienteTipo.getDescricao() == null || clienteTipo.getDescricao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Descrição");
		}

		// Verifica se o campo Esfera Poder foi preenchido
		if(clienteTipo.getEsferaPoder() == null || clienteTipo.getEsferaPoder().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Esfera Poder");
		}

		// Verifica se o campo Referência do Tipo de Serviço foi preenchido
		if(clienteTipo.getIndicadorPessoaFisicaJuridica() == null
						|| clienteTipo.getIndicadorPessoaFisicaJuridica().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Tipo de Pessoa");
		}

		// [FS0003] - Atualização realizada por outro usuário
		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
		filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, clienteTipo.getId()));

		Collection colecaoClienteTipoBase = getControladorUtil().pesquisar(filtroClienteTipo, ClienteTipo.class.getName());

		if(colecaoClienteTipoBase == null || colecaoClienteTipoBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		ClienteTipo clienteTipoBase = (ClienteTipo) colecaoClienteTipoBase.iterator().next();

		if(clienteTipoBase.getUltimaAlteracao().after(clienteTipo.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		clienteTipo.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(clienteTipo);

	}

	/**
	 * Atualiza Cliente Responsavel
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 24/01/2011
	 * @param clienteResponsavel
	 * @param usuario
	 * @throws ControladorException
	 */
	public void atualizarClienteResponsavel(ClienteResponsavel clienteResponsavel, Usuario usuario) throws ControladorException{

		// Atualização realizada por outro usuário
		FiltroClienteResponsavel filtroClienteResponsavel = new FiltroClienteResponsavel();
		filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.ID, clienteResponsavel.getId()));

		Collection colecaoClienteResponsavel = getControladorUtil().pesquisar(filtroClienteResponsavel, ClienteResponsavel.class.getName());

		if(colecaoClienteResponsavel == null || colecaoClienteResponsavel.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		ClienteResponsavel clienteResponsavelBase = (ClienteResponsavel) colecaoClienteResponsavel.iterator().next();

		if(clienteResponsavelBase.getUltimaAlteracao().after(clienteResponsavel.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		clienteResponsavel.setUltimaAlteracao(new Date());

		// [UC] - Registrar Transação
		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_RESPONSAVEL_ATUALIZAR,
						clienteResponsavel.getId(), clienteResponsavel.getId(), new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CLIENTE_RESPONSAVEL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		clienteResponsavel.setOperacaoEfetuada(operacaoEfetuada);
		clienteResponsavel.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(clienteResponsavel);
		// Fim - Registrando as transações

		// Atualiza o objeto na base
		getControladorUtil().atualizar(clienteResponsavel);

	}

	/**
	 * [UCXXXX] Manter Cliente Responsável
	 * [SB] - Excluir Cliente Responsável
	 * 
	 * @author isilva
	 * @date 15/02/2011
	 * @param ids
	 *            , usuarioLogado
	 * @throws ControladorException
	 */
	public void removerClienteResponsavel(String[] ids, Usuario usuarioLogado) throws ControladorException{

		try{

			for(int i = 0; i < ids.length; i++){

				// ------------ REGISTRAR TRANSAÇÃO ----------------
				FiltroClienteResponsavel filtro = new FiltroClienteResponsavel();
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.ID, new Integer(ids[i])));
				filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteResponsavel.CLIENTE);

				ClienteResponsavel clienteResponsavelRemocao = (ClienteResponsavel) Util.retonarObjetoDeColecao(getControladorUtil()
								.pesquisar(filtro, ClienteResponsavel.class.getName()));

				if(clienteResponsavelRemocao == null){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				RegistradorOperacao registradorClienteResponsavel = new RegistradorOperacao(Operacao.OPERACAO_CLIENTE_RESPONSAVEL_REMOVER,
								clienteResponsavelRemocao.getCliente().getId(), clienteResponsavelRemocao.getId(),
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_CLIENTE_RESPONSAVEL_REMOVER);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				clienteResponsavelRemocao.setOperacaoEfetuada(operacaoEfetuada);

				registradorClienteResponsavel.registrarOperacao(clienteResponsavelRemocao);
				// ------------ REGISTRAR TRANSAÇÃO ----------------

				this.getControladorUtil().remover(clienteResponsavelRemocao);

			}
		}catch(Exception e){
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
	}

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 *       Insere a Profissão
	 * @param profissao
	 *            Profissão a ser inserido
	 * @return código da Profissão que foi inserida
	 * @throws ControladorException
	 */
	public Integer inserirProfissao(Profissao profissao, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_PROFISSAO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_PROFISSAO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		FiltroProfissao filtro = new FiltroProfissao();
		filtro.adicionarParametro(new ParametroSimples(FiltroProfissao.CODIGO, profissao.getCodigo()));

		Collection colFiltros = getControladorUtil().pesquisar(filtro, profissao.getClass().getName());
		if(colFiltros != null && colFiltros.size() > 0){
			throw new ControladorException("atencao.profissao.existente");
		}

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		profissao.setOperacaoEfetuada(operacaoEfetuada);
		profissao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(profissao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		return (Integer) this.getControladorUtil().inserir(profissao);
	}

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 */

	public void removerProfissao(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_PROFISSAO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_PROFISSAO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		for(String id : ids){

			FiltroProfissao filtroProfissao = new FiltroProfissao();
			filtroProfissao.adicionarParametro(new ParametroSimples(FiltroProfissao.ID, id));

			Collection profissaoAtualizados = getControladorUtil().pesquisar(filtroProfissao, Profissao.class.getName());

			Profissao profissao = (Profissao) Util.retonarObjetoDeColecao(profissaoAtualizados);

			if(profissao == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			profissao.setUltimaAlteracao(new Date());
			profissao.setIndicadorUso(new Short("2"));
			// ------------------------ REGISTRO OPERACAO
			profissao.setOperacaoEfetuada(operacaoEfetuada);
			profissao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(profissao);
			// ------------------------ REGISTRO OPERACAO
			// atualiza cada objeto
			getControladorUtil().atualizar(profissao);
		}
	}

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 */
	public void atualizarProfissao(Profissao profissao, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_PROFISSAO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_PROFISSAO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		profissao.setOperacaoEfetuada(operacaoEfetuada);
		profissao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(profissao);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroProfissao filtroProfissao = new FiltroProfissao();

		filtroProfissao.adicionarParametro(new ParametroSimples(FiltroProfissao.ID, profissao.getId()));

		Collection profissaoAtualizados = getControladorUtil().pesquisar(filtroProfissao, Profissao.class.getName());

		Profissao profissaoNaBase = (Profissao) Util.retonarObjetoDeColecao(profissaoAtualizados);

		if(profissaoNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}
		if(profissaoNaBase.getUltimaAlteracao().after(profissao.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		profissao.setUltimaAlteracao(new Date());
		if(!profissaoNaBase.getCodigo().equals(profissao.getCodigo())){
			// verifica se o codigo já existe

			FiltroProfissao filtro = new FiltroProfissao();
			filtro.adicionarParametro(new ParametroSimples(FiltroProfissao.CODIGO, profissao.getCodigo()));
			Collection colFiltros = getControladorUtil().pesquisar(filtro, profissao.getClass().getName());
			if(colFiltros != null && colFiltros.size() > 0){
				throw new ControladorException("atencao.profissao.existente");
			}
		}
		// Atualiza o objeto na base
		getControladorUtil().atualizar(profissao);

	}

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 *       Insere o Perfil do Imóvel
	 * @param imovelPerfil
	 *            Perfil do Imóvel a ser inserido
	 * @return código do Perfil do Imóvel que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirImovelPerfil(ImovelPerfil imovelPerfil, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_IMOVEL_PERFIL,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_IMOVEL_PERFIL);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		FiltroImovelPerfil filtro = new FiltroImovelPerfil();
		filtro.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.DESCRICAO, imovelPerfil.getDescricao()));

		Collection colFiltros = getControladorUtil().pesquisar(filtro, imovelPerfil.getClass().getName());
		if(colFiltros != null && colFiltros.size() > 0){
			throw new ControladorException("atencao.imovel_perfil.existente");
		}

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		imovelPerfil.setOperacaoEfetuada(operacaoEfetuada);
		imovelPerfil.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(imovelPerfil);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		return (Integer) this.getControladorUtil().inserir(imovelPerfil);
	}

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 */

	public void removerImovelPerfil(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_IMOVEL_PERFIL,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_IMOVEL_PERFIL);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		for(String id : ids){

			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
			filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, id));

			Collection imovelPerfilAtualizados = getControladorUtil().pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

			ImovelPerfil imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(imovelPerfilAtualizados);

			if(imovelPerfil == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			imovelPerfil.setUltimaAlteracao(new Date());
			imovelPerfil.setIndicadorUso(new Short("2"));
			// ------------------------ REGISTRO OPERACAO
			imovelPerfil.setOperacaoEfetuada(operacaoEfetuada);
			imovelPerfil.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(imovelPerfil);
			// ------------------------ REGISTRO OPERACAO
			// atualiza cada objeto
			getControladorUtil().atualizar(imovelPerfil);
		}
	}

	/**
	 * @author Victon Santos
	 * @date 15/07/2013
	 */
	public void atualizarImovelPerfil(ImovelPerfil imovelPerfil, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_IMOVEL_PERFIL,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_IMOVEL_PERFIL);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		imovelPerfil.setOperacaoEfetuada(operacaoEfetuada);
		imovelPerfil.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(imovelPerfil);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();

		filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, imovelPerfil.getId()));

		Collection imovelPerfilAtualizados = getControladorUtil().pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

		ImovelPerfil imovelPerfilNaBase = (ImovelPerfil) Util.retonarObjetoDeColecao(imovelPerfilAtualizados);

		if(imovelPerfilNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		if(imovelPerfilNaBase.getUltimaAlteracao().after(imovelPerfil.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		imovelPerfil.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(imovelPerfil);

	}
	
	/**
	 * @author Victon Santos
	 * @date 17/07/2013
	 *       Insere o Padrão de Construção
	 * @param padraoConstrucao
	 *            Padrão de Construção a ser inserido
	 * @return código do Padrão de Construção que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirPadraoConstrucao(PadraoConstrucao padraoConstrucao, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_PADRAO_CONSTRUCAO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_PADRAO_CONSTRUCAO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		FiltroPadraoConstrucao filtro = new FiltroPadraoConstrucao();
		filtro.adicionarParametro(new ParametroSimples(FiltroPadraoConstrucao.DESCRICAO, padraoConstrucao.getDescricao()));

		Collection colFiltros = getControladorUtil().pesquisar(filtro, padraoConstrucao.getClass().getName());
		if(colFiltros != null && colFiltros.size() > 0){
			throw new ControladorException("atencao.padrao_construcao.existente");
		}

		// ------------ REGISTRAR TRANSAÇÃO ----------------------------
		padraoConstrucao.setOperacaoEfetuada(operacaoEfetuada);
		padraoConstrucao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(padraoConstrucao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------------------

		return (Integer) this.getControladorUtil().inserir(padraoConstrucao);
	}

	/**
	 * @author Victon Santos
	 * @date 17/07/2013
	 */

	public void removerPadraoConstrucao(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REMOVER_PADRAO_CONSTRUCAO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REMOVER_PADRAO_CONSTRUCAO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		for(String id : ids){

			FiltroPadraoConstrucao filtroPadraoConstrucao = new FiltroPadraoConstrucao();
			filtroPadraoConstrucao.adicionarParametro(new ParametroSimples(FiltroPadraoConstrucao.ID, id));

			Collection padraoConstrucaoAtualizados = getControladorUtil().pesquisar(filtroPadraoConstrucao,
							PadraoConstrucao.class.getName());

			PadraoConstrucao padraoConstrucao = (PadraoConstrucao) Util.retonarObjetoDeColecao(padraoConstrucaoAtualizados);

			if(padraoConstrucao == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			padraoConstrucao.setUltimaAlteracao(new Date());
			padraoConstrucao.setIndicadorUso(new Short("2"));
			// ------------------------ REGISTRO OPERACAO
			padraoConstrucao.setOperacaoEfetuada(operacaoEfetuada);
			padraoConstrucao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(padraoConstrucao);
			// ------------------------ REGISTRO OPERACAO
			// atualiza cada objeto
			getControladorUtil().atualizar(padraoConstrucao);
		}
	}

	/**
	 * @author Victon Santos
	 * @date 17/07/2013
	 */
	public void atualizarPadraoConstrucao(PadraoConstrucao padraoConstrucao, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_PADRAO_CONSTRUCAO,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_PADRAO_CONSTRUCAO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		padraoConstrucao.setOperacaoEfetuada(operacaoEfetuada);
		padraoConstrucao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(padraoConstrucao);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário

		FiltroPadraoConstrucao filtroPadraoConstrucao = new FiltroPadraoConstrucao();

		filtroPadraoConstrucao.adicionarParametro(new ParametroSimples(FiltroPadraoConstrucao.ID, padraoConstrucao.getId()));

		Collection padraoConstrucaoAtualizados = getControladorUtil().pesquisar(filtroPadraoConstrucao, PadraoConstrucao.class.getName());

		PadraoConstrucao padraoConstrucaoNaBase = (PadraoConstrucao) Util.retonarObjetoDeColecao(padraoConstrucaoAtualizados);

		if(padraoConstrucaoNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		if(padraoConstrucaoNaBase.getUltimaAlteracao().after(padraoConstrucao.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		padraoConstrucao.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(padraoConstrucao);

	}

	/**
	 * Atualiza Imovel Agua Para Todos
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 25/01/2011
	 * @throws ControladorException
	 */
	public void atualizarImovelAguaParaTodos(ImovelAguaParaTodos imovelAguaParaTodos, Imovel imovel, Usuario usuario)
					throws ControladorException{

		// Atualização realizada por outro usuário
		FiltroImovelAguaParaTodos filtroImovelAguaParaTodos = new FiltroImovelAguaParaTodos();
		filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID, imovelAguaParaTodos.getId()));

		Collection colecaoImovelAguaParaTodos = getControladorUtil().pesquisar(filtroImovelAguaParaTodos,
						ImovelAguaParaTodos.class.getName());

		if(colecaoImovelAguaParaTodos == null || colecaoImovelAguaParaTodos.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		ImovelAguaParaTodos imovelAguaParaTodosBase = (ImovelAguaParaTodos) colecaoImovelAguaParaTodos.iterator().next();

		if(imovelAguaParaTodosBase.getUltimaAlteracao() != null && imovelAguaParaTodos.getUltimaAlteracao() != null
						&& imovelAguaParaTodosBase.getUltimaAlteracao().after(imovelAguaParaTodos.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		imovelAguaParaTodos.setUltimaAlteracao(new Date());
		imovel.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_AGUA_PARA_TODOS_INSERIR,
						imovelAguaParaTodos.getId(), imovelAguaParaTodos.getId(), new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_IMOVEL_AGUA_PARA_TODOS_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		imovelAguaParaTodos.setOperacaoEfetuada(operacaoEfetuada);
		registradorOperacao.registrarOperacao(imovelAguaParaTodos);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		getControladorUtil().atualizar(imovelAguaParaTodos);
		getControladorImovel().atualizarImovel(imovel, usuario);
	}

	/**
	 * Migração dos dados do município de Ribeirão - O sistema
	 * gerar as tabelas cliente, cliente_endereço, imovel, cliente_imovel,
	 * imovel_subcategoria, ligacao_agua a parti da tabela Cadastro_ribeirao;
	 * 
	 * @author Ana Maria
	 * @throws ControladorException
	 */
	public void inserirRiberao() throws ControladorException{

		/*
		 * try {
		 * List colecaoCadastroRibeiraop = (List) repositorioCadastro
		 * .pesquisarCadastroRibeiraop();
		 * Integer idBairro = repositorioCadastro.pesquisarBairro();
		 * Integer cepPesquisado = repositorioCadastro.pesquisarCEP();
		 * Object[] setorQuadra = repositorioCadastro
		 * .pesquisarSetorQuadra(118);
		 * int limiteSuperior;
		 * int limiteInferior;
		 * int limiteMaximo = colecaoCadastroRibeiraop.size();
		 * int quantidadeMaximaPorColecao = 500;
		 * int indece = 6394;
		 * for (int i = 0; i < limiteMaximo; i = i
		 * + quantidadeMaximaPorColecao) {
		 * if (limiteMaximo < quantidadeMaximaPorColecao) {
		 * limiteInferior = 0;
		 * limiteSuperior = limiteMaximo;
		 * } else {
		 * limiteInferior = i;
		 * limiteSuperior = i + quantidadeMaximaPorColecao;
		 * if (limiteSuperior > limiteMaximo) {
		 * limiteSuperior = limiteMaximo;
		 * }
		 * }
		 * List colecaoTemporaria = new ArrayList();
		 * colecaoTemporaria.addAll(colecaoCadastroRibeiraop.subList(
		 * limiteInferior, limiteSuperior));
		 * if (colecaoTemporaria != null && !colecaoTemporaria.isEmpty()) {
		 * Iterator colecaoTemporariaIterator = colecaoTemporaria
		 * .iterator();
		 * while (colecaoTemporariaIterator.hasNext()) {
		 * //CadastroRibeiraop cr = (CadastroRibeiraop) colecaoTemporariaIterator.next();
		 * // Inserir Cliente
		 * Cliente cliente = new Cliente();
		 * cliente.setNome(cr.getNome());
		 * ClienteTipo ct = new ClienteTipo();
		 * if (cr.getClasse().equals("PAR")) {
		 * ct.setId(25);
		 * } else if (cr.getClasse().equals("PBM")) {
		 * ct.setId(7);
		 * } else if (cr.getClasse().equals("PBF")) {
		 * ct.setId(17);
		 * } else if (cr.getClasse().equals("PBE")) {
		 * ct.setId(8);
		 * }
		 * cliente.setClienteTipo(ct);
		 * cliente
		 * .setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		 * cliente.setUltimaAlteracao(new Date());
		 * cliente
		 * .setIndicadorAcaoCobranca(ConstantesSistema.INDICADOR_USO_ATIVO);
		 * cliente
		 * .setIndicadorCobrancaAcrescimos(ConstantesSistema.INDICADOR_USO_ATIVO);
		 * cliente
		 * .setIndicadorGeraArquivoTexto(ConstantesSistema.INDICADOR_USO_DESATIVO);
		 * Integer idCliente = (Integer) getControladorUtil()
		 * .inserir(cliente);
		 * System.out.println("idCliente:" + idCliente);
		 * // InserirImovel
		 * Imovel imovel = new Imovel();
		 * Localidade lc = new Localidade();
		 * lc.setId(new Integer(118));
		 * imovel.setLocalidade(lc);
		 * SetorComercial st = new SetorComercial();
		 * st.setId((Integer) setorQuadra[0]);
		 * imovel.setSetorComercial(st);
		 * Quadra qd = new Quadra();
		 * qd.setId((Integer) setorQuadra[1]);
		 * imovel.setQuadra(qd);
		 * imovel.setLote((short) indece);
		 * System.out.println(indece);
		 * indece = indece + 1;
		 * imovel.setSubLote(new Short("0"));
		 * String numeroImovelMenor = null;
		 * String numeroImovelMaior = null;
		 * if (cr.getNumeroImovel().length() <= 5) {
		 * imovel.setNumeroImovel(cr.getNumeroImovel());
		 * numeroImovelMenor = "'" + cr.getNumeroImovel()
		 * + "'";
		 * } else {
		 * imovel.setComplementoEndereco(cr.getNumeroImovel());
		 * numeroImovelMaior = "'" + cr.getNumeroImovel()
		 * + "'";
		 * }
		 * imovel.setIndicadorImovelCondominio(new Short("2"));
		 * LigacaoAguaSituacao ligSit = new LigacaoAguaSituacao();
		 * ligSit.setId(new Integer(cr.getSituacaoAgua()));
		 * imovel.setLigacaoAguaSituacao(ligSit);
		 * LigacaoEsgotoSituacao ligESit = new LigacaoEsgotoSituacao();
		 * ligESit.setId(1);
		 * imovel.setLigacaoEsgotoSituacao(ligESit);
		 * ImovelPerfil imovPerf = new ImovelPerfil();
		 * imovPerf.setId(5);
		 * imovel.setImovelPerfil(imovPerf);
		 * imovel.setNumeroParcelamento(new Short("0"));
		 * imovel.setNumeroReparcelamento(new Short("0"));
		 * imovel.setNumeroReparcelamentoConsecutivos(new Short(
		 * "0"));
		 * imovel.setIndicadorEmissaoExtratoFaturamento(new Short(
		 * "2"));
		 * imovel.setIndicadorDebitoConta(new Short("2"));
		 * imovel.setIndicadorExclusao(new Short("2"));
		 * imovel.setUltimaAlteracao(new Date());
		 * ConsumoTarifa ctarifa = new ConsumoTarifa();
		 * if (cr.getCategoria().equals("R-3")) {
		 * ctarifa.setId(2);
		 * } else {
		 * ctarifa.setId(1);
		 * }
		 * imovel.setConsumoTarifa(ctarifa);
		 * EnderecoReferencia endRef = new EnderecoReferencia();
		 * endRef.setId(1);
		 * imovel.setEnderecoReferencia(endRef);
		 * imovel.setQuantidadeEconomias(new Short("1"));
		 * imovel
		 * .setIndicadorSuspensaoAbastecimento(new Short(
		 * "2"));
		 * Integer idLogB = repositorioCadastro
		 * .pesquisarLogradouroBairro(cr
		 * .getCodigoLogradouro());
		 * LogradouroBairro logB = new LogradouroBairro();
		 * logB.setId(idLogB);
		 * imovel.setLogradouroBairro(logB);
		 * Integer idLogC = repositorioCadastro
		 * .pesquisarLogradouroCep(cr
		 * .getCodigoLogradouro());
		 * LogradouroCep logC = new LogradouroCep();
		 * logC.setId(idLogC);
		 * imovel.setLogradouroCep(logC);
		 * imovel.setIndicadorJardim(new Short("2"));
		 * ImovelContaEnvio icte = new ImovelContaEnvio();
		 * icte.setId(2);
		 * imovel.setImovelContaEnvio(icte);
		 * Integer idImovel = (Integer) getControladorUtil()
		 * .inserir(imovel);
		 * System.out.println("idImovel:" + idImovel);
		 * // Inserir imovel no CadastroRiberao
		 * repositorioCadastro.atualizarImovelRibeirao(idImovel,
		 * cr.getCodigo());
		 * // Data
		 * Calendar dataCalendar = new GregorianCalendar();
		 * StringBuffer dataBD = new StringBuffer();
		 * dataCalendar.setTime(new Date());
		 * dataBD.append("'" + dataCalendar.get(Calendar.YEAR));
		 * // Obs.: Janeiro no Calendar é mês zero
		 * if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
		 * dataBD.append("-"
		 * + dataCalendar.get(Calendar.MONTH) + 1);
		 * } else {
		 * dataBD.append("-" + "0"
		 * + (dataCalendar.get(Calendar.MONTH) + 1));
		 * }
		 * if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
		 * dataBD.append("-"
		 * + dataCalendar.get(Calendar.DAY_OF_MONTH)
		 * + "'");
		 * } else {
		 * dataBD.append("-" + "0"
		 * + dataCalendar.get(Calendar.DAY_OF_MONTH)
		 * + "'");
		 * }
		 * String data = dataBD.toString();
		 * // Inserir Cliente_Endereco
		 * repositorioCadastro.inserirClienteEndereco(idCliente,
		 * numeroImovelMenor, numeroImovelMaior,
		 * cepPesquisado, idBairro, cr
		 * .getCodigoLogradouro(), idLogB, idLogC);
		 * // Inserir Cliente_Imovel
		 * repositorioCadastro.inserirClienteImovel(idCliente,
		 * idImovel, data);
		 * // Inserir Imovel_Subcategoria
		 * Integer idSubcategoria = null;
		 * if (cr.getCategoria().substring(0, 1).equals("R")) {
		 * idSubcategoria = 10;
		 * } else if (cr.getCategoria().equals("C-1")) {
		 * idSubcategoria = 20;
		 * } else if (cr.getCategoria().substring(0, 1)
		 * .equals("I")) {
		 * idSubcategoria = 30;
		 * } else if (cr.getCategoria().equals("C-3")) {
		 * idSubcategoria = 40;
		 * } else {
		 * idSubcategoria = 10;
		 * }
		 * repositorioCadastro.inserirImovelSubcategoria(idImovel,
		 * idSubcategoria);
		 * //Inserir Ligacao_Agua
		 * repositorioCadastro.inserirLigacaoAgua(idImovel, data);
		 * }
		 * }
		 * }
		 * } catch (ErroRepositorioException e) {
		 * throw new ControladorException("erro.sistema", e);
		 * }
		 */
	}

	/**
	 * Este caso de uso permite a emissão de boletins de cadastro
	 * [UC0582] Emitir Boletim de Cadastro Modelo 1
	 * 
	 * @author Rafael Corrêa
	 * @data 15/05/2007
	 * @param
	 * @return void
	 */
	public byte[] emitirBoletimCadastroModelo1(FiltrarRelatorioBoletimCadastroHelper filtro)
					throws ControladorException{

		boolean flagFimPesquisa = false;
		final int quantidadePorPagina = 500;
		int totalPaginacao = 0;
		StringBuilder boletimCadastroTxt = new StringBuilder();
		int sequencialImpressao = 0;
		int pagina = 0;

		while(!flagFimPesquisa){

			pagina++;
			Collection colecaoImoveis = null;
			Collection colecaoEmitirBoletimCadastro = null;

			try{

				colecaoImoveis = repositorioImovel.pesquisarRelatorioBoletimCadastro(filtro, totalPaginacao);

				if(!colecaoImoveis.isEmpty()){

					colecaoEmitirBoletimCadastro = this.gerarColecaoBoletimHelper(colecaoImoveis);
				}

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(colecaoEmitirBoletimCadastro != null && !colecaoEmitirBoletimCadastro.isEmpty()){

				if(colecaoEmitirBoletimCadastro.size() < quantidadePorPagina){

					flagFimPesquisa = true;
				}else{

					totalPaginacao += quantidadePorPagina;
				}

				Iterator colecaoEmitirBoletimCadastroIterator = colecaoEmitirBoletimCadastro.iterator();
				int count = 0;

				EmitirDocumentoCobrancaBoletimCadastroHelper emitirDocumentoCobrancaBoletimCadastroHelper = null;
				while(colecaoEmitirBoletimCadastroIterator.hasNext()){

					emitirDocumentoCobrancaBoletimCadastroHelper = (EmitirDocumentoCobrancaBoletimCadastroHelper) colecaoEmitirBoletimCadastroIterator
									.next();

					count++;

					sequencialImpressao++;

					if(emitirDocumentoCobrancaBoletimCadastroHelper != null) this.criarDadosTxtBoletimCadastroModelo1(boletimCadastroTxt,
									emitirDocumentoCobrancaBoletimCadastroHelper);

					emitirDocumentoCobrancaBoletimCadastroHelper = null;

					boletimCadastroTxt.append(System.getProperty("line.separator"));
				}
			}else{

				flagFimPesquisa = true;
				throw new ControladorException("atencao.relatorio.vazio");
			}
		}

		Date dataAtual = new Date();

		String nomeZip = null;
		nomeZip = "BOLETIM_CADASTRO " + Util.formatarDataComHora(dataAtual);

		nomeZip = nomeZip.replace("/", "_");
		nomeZip = nomeZip.replace(" ", "_");
		nomeZip = nomeZip.replace(":", "_");

		nomeZip = nomeZip.replace("/", "_");

		byte[] retornoArray = null;

		try{

			if(boletimCadastroTxt != null && boletimCadastroTxt.length() != 0){

				boletimCadastroTxt.append("\u0004");

				// criar o arquivo zip
				File compactado = File.createTempFile("zipHtml" + nomeZip, ".zip");
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

				File leitura = new File(nomeZip + ".txt");
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leitura.getAbsolutePath())));
				out.write(boletimCadastroTxt.toString());
				out.flush();
				out.close();
				ZipUtil.adicionarArquivo(zos, leitura);

				// close the stream
				zos.close();

				ByteArrayOutputStream retorno = new ByteArrayOutputStream();

				FileInputStream inputStream = new FileInputStream(compactado);

				int INPUT_BUFFER_SIZE = 1024;
				byte[] temp = new byte[INPUT_BUFFER_SIZE];
				int numBytesRead = 0;

				while((numBytesRead = inputStream.read(temp, 0, INPUT_BUFFER_SIZE)) != -1){
					retorno.write(temp, 0, numBytesRead);
				}

				inputStream.close();
				leitura.delete();
				retornoArray = retorno.toByteArray();
			}

			System.out.println("******************** FIM GERAÇÃO ARQUIVO ********************");

		}catch(IOException e){

			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}catch(Exception e){

			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		return retornoArray;
	}

	/**
	 * Este caso de uso permite a emissão de boletins de cadastro
	 * [UC0582] Emitir Boletim de Cadastro Modelo 2
	 * 
	 * @author Hiroshi Gonçalves
	 * @data 15/08/2013
	 * @param
	 * @return void
	 */

	public Collection<RelatorioBoletimCadastralModelo2Bean> consultarDadosBoletimCadastralModelo2(
					FiltrarRelatorioBoletimCadastroHelper filtro)
					throws ControladorException{

		int totalPaginacao = 0;

		Collection colecaoImoveis = null;
		Collection colecaoEmitirBoletimCadastro = null;
		Collection<RelatorioBoletimCadastralModelo2Bean> colecaoRelatorioBoletimCadastralBean = null;

		try{

			colecaoImoveis = repositorioImovel.pesquisarRelatorioBoletimCadastro(filtro, totalPaginacao);

			if(!colecaoImoveis.isEmpty()){

				colecaoEmitirBoletimCadastro = this.gerarColecaoBoletimHelper(colecaoImoveis);

				colecaoRelatorioBoletimCadastralBean = this.gerarRelatorioBoletimCadastralModelo2Beans(colecaoEmitirBoletimCadastro);
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRelatorioBoletimCadastralBean;
	}

	/**
	 * Gerar a coleção de Helper's para emissão dos boletins de cadastro
	 * [UC0582] Emitir Boletim de Cadastro pelo
	 * 
	 * @author Anderson Italo
	 * @date 20/04/2011
	 */
	private Collection<EmitirDocumentoCobrancaBoletimCadastroHelper> gerarColecaoBoletimHelper(Collection colecaoImoveis){

		Collection<EmitirDocumentoCobrancaBoletimCadastroHelper> retorno = new ArrayList<EmitirDocumentoCobrancaBoletimCadastroHelper>();

		Iterator iteratorColecaoImovel = colecaoImoveis.iterator();

		while(iteratorColecaoImovel.hasNext()){

			Object[] imovel = (Object[]) iteratorColecaoImovel.next();

			EmitirDocumentoCobrancaBoletimCadastroHelper helper = new EmitirDocumentoCobrancaBoletimCadastroHelper();

			if(imovel[0] != null) helper.setIdImovel((Integer) imovel[0]);

			if(imovel[1] != null) helper.setIdLocalidade((Integer) imovel[1]);

			if(imovel[2] != null) helper.setCodigoSetorComercial((Integer) imovel[2]);

			if(imovel[3] != null) helper.setNumeroQuadra((Integer) imovel[3]);

			if(imovel[4] != null) helper.setLote((Short) imovel[4]);

			if(imovel[5] != null) helper.setSubLote((Short) imovel[5]);

			if(imovel[6] != null) helper.setIdCobrancaGrupo((Integer) imovel[6]);

			if(imovel[7] != null) helper.setIdLigacaoAguaSituacao((Integer) imovel[7]);

			if(imovel[8] != null) helper.setIdLigacaoEsgotoSituacao((Integer) imovel[8]);

			if(imovel[9] != null) helper.setNumeroMorador((Short) imovel[9]);

			if(imovel[10] != null) helper.setAreaConstruida((BigDecimal) imovel[10]);

			if(imovel[11] != null) helper.setIdLogradouro((Integer) imovel[11]);

			if(imovel[12] != null) helper.setCodigoCep((Integer) imovel[12]);

			if(imovel[13] != null) helper.setCodigoBairro((Integer) imovel[13]);

			if(imovel[14] != null) helper.setReferencia((Integer) imovel[14]);

			if(imovel[15] != null) helper.setNumeroImovel((String) imovel[15]);

			if(imovel[16] != null) helper.setComplemento((String) imovel[16]);

			if(imovel[17] != null) helper.setVolumeReservatorioInferior((Integer) imovel[17]);

			if(imovel[18] != null) helper.setVolumeReservatorioSuperior((Integer) imovel[18]);

			if(imovel[19] != null) helper.setVolumePiscina((Integer) imovel[19]);

			if(imovel[20] != null) helper.setJardim((Short) imovel[20]);

			if(imovel[21] != null) helper.setIdPavimentoRua((Integer) imovel[21]);

			if(imovel[22] != null) helper.setIdPavimentoCalcada((Integer) imovel[22]);

			if(imovel[23] != null) helper.setNumeroPontosUtilizacao((Short) imovel[23]);

			if(imovel[24] != null) helper.setIdImovelPerfil((Integer) imovel[24]);

			if(imovel[25] != null) helper.setIdDespejo((Integer) imovel[25]);

			if(imovel[26] != null) helper.setIdPoco((Integer) imovel[26]);

			if(imovel[27] != null) helper.setIdFonteAbastecimento((Integer) imovel[27]);

			if(imovel[28] != null) helper.setNumeroIptu((BigDecimal) imovel[28]);

			if(imovel[29] != null) helper.setNumeroCelpe((Long) imovel[29]);

			if(imovel[30] != null) helper.setIdRota((Integer) imovel[30]);

			if(imovel[31] != null) helper.setSegmento((Short) imovel[31]);

			if(imovel[32] != null) helper.setNomeBairro((String) imovel[32]);

			if(imovel[33] != null) helper.setLocalidadeImovel((Localidade) imovel[33]);

			if(imovel[34] != null) helper.setMunicipioImovel((Municipio) imovel[34]);

			if(imovel[35] != null) helper.setEnderecoReferencia((EnderecoReferencia) imovel[35]);

			if(imovel[36] != null) helper.setDsLigacaoAguaSituacao((String) imovel[36]);

			if(imovel[37] != null) helper.setDsLigacaoEsgotoSituacao((String) imovel[37]);

			if(imovel[38] != null) helper.setFonteAbastecimento((String) imovel[38]);

			retorno.add(helper);
		}

		return retorno;
	}

	/**
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0001] Gerar Atividade de Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Sávio Luiz
	 * @created 23/11/2007
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClientesSubordinados(Integer idCliente) throws ControladorException{

		try{
			// chama o metódo de pesquisar do repositório
			return repositorioCadastro.pesquisarClientesSubordinados(idCliente);

			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0624] Gerar Relatório para Atualização Cadastral
	 * 
	 * @author Flávio Cordeiro
	 */
	public Collection pesquisarDadosRelatorioAtualizacaoCadastral(int anoMesFaturamento, Integer idFaturamentoGrupo,
					int indicadorLocalidadeInformatizada, Collection idLocalidades, Collection idSetores, Collection idQuadras,
					String rotaInicial, String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String tipoRelatorio,
					Usuario usuarioLogado) throws ControladorException{

		Collection retorno = new ArrayList();
		try{
			Collection colecaoObjeto = repositorioCadastro.pesquisarRelatorioAtualizacaoCadastral(idLocalidades, idSetores, idQuadras,
							rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal);
			if(colecaoObjeto != null && !colecaoObjeto.isEmpty()){
				Iterator iterator = colecaoObjeto.iterator();

				while(iterator.hasNext()){
					RelatorioAtualizacaoCadastralHelper relatorioAtualizacaoCadastralHelper = new RelatorioAtualizacaoCadastralHelper();
					Object[] objeto = (Object[]) iterator.next();
					// idImovel
					if(objeto[0] != null){
						relatorioAtualizacaoCadastralHelper.setIdImovel((Integer) objeto[0]);
						relatorioAtualizacaoCadastralHelper.setInscricao(getControladorImovel().pesquisarInscricaoImovel(
										relatorioAtualizacaoCadastralHelper.getIdImovel(), true));
					}
					// matricula Imovel
					if(objeto[1] != null){
						relatorioAtualizacaoCadastralHelper.setIdImovel((Integer) objeto[1]);
					}
					// nome cliente
					if(objeto[2] != null){
						relatorioAtualizacaoCadastralHelper.setNomeCliente((String) objeto[2]);
					}
					// localidade id
					if(objeto[3] != null){
						relatorioAtualizacaoCadastralHelper.setIdLocalidade((Integer) objeto[3]);
					}
					// localidade descricao
					if(objeto[4] != null){
						relatorioAtualizacaoCadastralHelper.setLocalidadeDescricao((String) objeto[4]);
					}
					// setor comercial codigo
					if(objeto[5] != null){
						relatorioAtualizacaoCadastralHelper.setCodigoSetorComercial((Integer) objeto[5]);
					}
					// setor comercial descricao
					if(objeto[6] != null){
						relatorioAtualizacaoCadastralHelper.setSetorComercialDescricao((String) objeto[6]);
					}
					// unidade negocio nome
					if(objeto[7] != null){
						relatorioAtualizacaoCadastralHelper.setUnidadeNegocioDescricao((String) objeto[7]);
					}
					// rota codigo
					if(objeto[8] != null){
						String rota = (String) objeto[8];
						// imovel numero sequencial rota
						if(objeto[9] != null){
							rota = rota + "." + (String) objeto[9];
						}
						relatorioAtualizacaoCadastralHelper.setRotaSequencialRota(rota);
					}

					// imovel indicador exclusao
					if(objeto[10] != null){
						relatorioAtualizacaoCadastralHelper.setIndicadorExclusao((String) objeto[10]);
					}

					// Unidade de negocio id
					if(objeto[11] != null){
						relatorioAtualizacaoCadastralHelper.setIdUnidadeNegocio((Integer) objeto[11]);
					}

					String endereco = getControladorEndereco().obterEnderecoAbreviadoImovel(
									relatorioAtualizacaoCadastralHelper.getIdImovel());
					if(endereco != null && !endereco.trim().equals("")){
						relatorioAtualizacaoCadastralHelper.setEndereco(endereco);
					}

					Collection existeLigacaoAgua = getControladorLigacaoAgua().verificaExistenciaLigacaoAgua(
									relatorioAtualizacaoCadastralHelper.getIdImovel());
					if(existeLigacaoAgua != null){

					}

					retorno.add(relatorioAtualizacaoCadastralHelper);

				}

				// parte nova para o relatório ter o processamento em batch
				// cria uma instância da classe do relatório

			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * [UC0725] Gerar Relatório de Imóveis por Situação da Ligação de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 03/12/2007
	 * @param FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisSituacaoLigacaoAguaHelper> pesquisarRelatorioImoveisSituacaoLigacaoAgua(
					FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper filtro) throws ControladorException{

		Collection<RelatorioImoveisSituacaoLigacaoAguaHelper> retorno = new ArrayList<RelatorioImoveisSituacaoLigacaoAguaHelper>();

		Collection<Object[]> colecaoPesquisa = null;

		try{
			colecaoPesquisa = this.repositorioCadastro.pesquisarRelatorioImoveisSituacaoLigacaoAgua(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			while(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				RelatorioImoveisSituacaoLigacaoAguaHelper helper = new RelatorioImoveisSituacaoLigacaoAguaHelper();

				Integer idImovel = (Integer) objeto[0];
				Integer localidade = (Integer) objeto[5];
				Integer codigoSetorComercial = (Integer) objeto[7];
				Integer numeroQuadra = (Integer) objeto[9];

				Short lote = (Short) objeto[15];
				Short subLote = (Short) objeto[16];

				helper.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(idImovel));
				helper.setGerenciaRegional((Integer) objeto[1]);
				helper.setNomeGerenciaRegional((String) objeto[2]);
				helper.setUnidadeNegocio((Integer) objeto[3]);
				helper.setNomeUnidadeNegocio((String) objeto[4]);
				helper.setLocalidade(localidade);
				helper.setDescricaoLocalidade((String) objeto[6]);
				helper.setSetorComercial(codigoSetorComercial);
				helper.setDescricaoSetorComercial((String) objeto[8]);
				helper.setNumeroQuadra(numeroQuadra);
				helper.setNomeCliente((String) objeto[10]);
				helper.setSituacaoLigacaoAgua((String) objeto[11]);

				if(objeto[12] != null){
					helper.setSituacaoLigacaoEsgoto((String) objeto[12]);
				}

				if(!Util.isVazioOuBranco(objeto[19])){
					helper.setIdRota((Integer) objeto[19]);
				}

				helper.setSequencialRota((Integer) objeto[14]);

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				Localidade local = new Localidade();
				local.setId(localidade);
				imovel.setLocalidade(local);

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(codigoSetorComercial);
				imovel.setSetorComercial(setorComercial);

				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra(numeroQuadra);
				imovel.setQuadra(quadra);

				imovel.setLote(lote);
				imovel.setSubLote(subLote);

				Integer idRota = helper.getIdRota();

				if(idRota != null){
					Rota rota = new Rota();
					rota.setId(idRota);

					imovel.setRota(rota);
				}

				if(!Util.isVazioOuBranco(objeto[20])){

					imovel.setNumeroSegmento((Short) objeto[20]);
				}

				helper.setInscricaoImovel(imovel.getInscricaoFormatada());

				String endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				helper.setEndereco(endereco);

				retorno.add(helper);
			}
		}else{
			throw new ControladorException("atencao.relatorio.vazio");
		}

		return retorno;
	}

	/**
	 * [UC0726] Gerar Relatório de Imóveis com Faturas em Atraso
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Collection<RelatorioImoveisSituacaoLigacaoAguaHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisFaturasAtrasoHelper> pesquisarRelatorioImoveisFaturasAtraso(
					FiltrarRelatorioImoveisFaturasAtrasoHelper filtro) throws ControladorException{

		Collection<RelatorioImoveisFaturasAtrasoHelper> retorno = new ArrayList<RelatorioImoveisFaturasAtrasoHelper>();

		Collection<Object[]> colecaoPesquisa = null;

		try{
			colecaoPesquisa = this.repositorioCadastro.pesquisarRelatorioImoveisFaturasAtraso(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			while(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				RelatorioImoveisFaturasAtrasoHelper helper = new RelatorioImoveisFaturasAtrasoHelper();

				Integer idImovel = (Integer) objeto[0];
				Integer localidade = (Integer) objeto[5];
				Integer codigoSetorComercial = (Integer) objeto[7];
				Integer numeroQuadra = (Integer) objeto[9];

				Short lote = (Short) objeto[15];
				Short subLote = (Short) objeto[16];

				helper.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(idImovel));
				helper.setGerenciaRegional((Integer) objeto[1]);
				helper.setNomeGerenciaRegional((String) objeto[2]);
				helper.setUnidadeNegocio((Integer) objeto[3]);
				helper.setNomeUnidadeNegocio((String) objeto[4]);
				helper.setLocalidade(localidade);
				helper.setDescricaoLocalidade((String) objeto[6]);
				helper.setSetorComercial(codigoSetorComercial);
				helper.setDescricaoSetorComercial((String) objeto[8]);
				helper.setNumeroQuadra(numeroQuadra);
				helper.setNomeCliente((String) objeto[10]);
				helper.setSituacaoLigacaoAgua((String) objeto[11]);
				helper.setSituacaoLigacaoEsgoto((String) objeto[12]);
				helper.setRota((Short) objeto[13]);
				helper.setSequencialRota((Integer) objeto[14]);
				helper.setQuantidadeFaturasAtraso((Long) objeto[19]);
				helper.setValorFaturasAtraso((BigDecimal) objeto[20]);
				helper.setReferenciaFaturasAtrasoInicial(filtro.getReferenciaFaturasAtrasoInicial());
				helper.setReferenciaFaturasAtrasoFinal(filtro.getReferenciaFaturasAtrasoFinal());

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				Localidade local = new Localidade();
				local.setId(localidade);
				imovel.setLocalidade(local);

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(codigoSetorComercial);
				imovel.setSetorComercial(setorComercial);

				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra(numeroQuadra);
				imovel.setQuadra(quadra);

				imovel.setLote(lote);
				imovel.setSubLote(subLote);

				helper.setInscricaoImovel(imovel.getInscricaoFormatada());

				String endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				helper.setEndereco(endereco);

				retorno.add(helper);
			}
		}

		return retorno;
	}

	/**
	 * [UC0725] Gerar Relatório de Imóveis por Situação da Ligação de Agua
	 * Pesquisa o Total Registro
	 * 
	 * @author Rafael Pinto
	 * @date 04/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisSituacaoLigacaoAgua(FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper filtro)
					throws ControladorException{

		try{
			return this.repositorioCadastro.pesquisarTotalRegistroRelatorioImoveisSituacaoLigacaoAgua(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0726] Gerar Relatório de Imóveis com Faturas em Atraso
	 * Pesquisa o Total Registro
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisFaturasAtraso(FiltrarRelatorioImoveisFaturasAtrasoHelper filtro)
					throws ControladorException{

		Collection<Object[]> retorno = null;
		Long quantidade = Long.valueOf(-1);

		try{
			retorno = this.repositorioCadastro.pesquisarTotalRegistroRelatorioImoveisFaturasAtraso(filtro);

			if(retorno != null && !retorno.isEmpty()){

				Iterator ite = retorno.iterator();

				while(ite.hasNext()){

					Object[] linha = (Object[]) ite.next();

					quantidade += Long.parseLong(Util.formatarBigDecimalParaString((BigDecimal) linha[1], 2));
				}
			}else{
				throw new ControladorException("atencao.nenhum.resultado");
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return Integer.valueOf(quantidade.toString());
	}

	/**
	 * Pesquisa os imoveis para o relatorio de imoveis por consumo medio
	 * 
	 * @author Bruno Barros
	 * @data 17/12/2007
	 * @param filtro
	 * @return Collection<RelatorioImoveisConsumoMedioHelper>
	 * @throws FachadaException
	 */
	public Collection<RelatorioImoveisConsumoMedioHelper> pesquisarRelatorioImoveisConsumoMedio(
					FiltrarRelatorioImoveisConsumoMedioHelper filtro) throws ControladorException{

		Collection<RelatorioImoveisConsumoMedioHelper> retorno = new ArrayList<RelatorioImoveisConsumoMedioHelper>();

		Collection<RelatorioImoveisConsumoMedioHelper> colecaoPesquisa = null;

		try{
			colecaoPesquisa = this.repositorioCadastro.pesquisarRelatorioImoveisConsumoMedio(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			while(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				RelatorioImoveisConsumoMedioHelper helper = new RelatorioImoveisConsumoMedioHelper();

				Integer idImovel = (Integer) objeto[14];
				Integer localidade = (Integer) objeto[4];
				Integer codigoSetorComercial = (Integer) objeto[6];
				Integer numeroQuadra = (Integer) objeto[20];

				helper.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(idImovel));
				helper.setGerenciaRegional((Integer) objeto[0]);
				helper.setNomeGerenciaRegional((String) objeto[1]);
				helper.setUnidadeNegocio((Integer) objeto[2]);
				helper.setNomeUnidadeNegocio((String) objeto[3]);
				helper.setLocalidade(localidade);
				helper.setDescricaoLocalidade((String) objeto[5]);
				helper.setSetorComercial(codigoSetorComercial);
				helper.setDescricaoSetorComercial((String) objeto[7]);
				helper.setNomeCliente((String) objeto[9]);
				helper.setSituacaoLigacaoAgua((String) objeto[10]);

				helper.setConsumoMedioAgua((Integer) objeto[11]);
				helper.setCodigoRota((Short) objeto[12]);
				helper.setSequencialRota((Integer) objeto[13]);
				helper.setSituacaoLigacaoEsgoto((String) objeto[16]);
				helper.setConsumoMedioEsgoto((Integer) objeto[17]);

				// Montamos um objeto imovel para poder pesquisar sua inscrição
				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				Localidade local = new Localidade();
				local.setId(localidade);
				imovel.setLocalidade(local);

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(codigoSetorComercial);
				imovel.setSetorComercial(setorComercial);

				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra(numeroQuadra);
				imovel.setQuadra(quadra);

				imovel.setLote((Short) objeto[18]);
				imovel.setSubLote((Short) objeto[19]);

				helper.setInscricaoImovel(imovel.getInscricaoFormatada());
				// ------------------------------------------------------------

				// Selecionamos o endereço
				String endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				helper.setEndereco(endereco);

				retorno.add(helper);
			}
		}

		return retorno;
	}

	/**
	 * [UC0727] Gerar Relatório de Imóveis por Consumo Medio
	 * Pesquisa a quantidade de imoveis para o relatorio de imoveis por consumo medio
	 * 
	 * @author Bruno Barros
	 * @data 17/12/2007
	 * @param filtro
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisConsumoMedio(FiltrarRelatorioImoveisConsumoMedioHelper filtro)
					throws ControladorException{

		try{
			return this.repositorioCadastro.pesquisarTotalRegistroRelatorioImoveisConsumoMedio(filtro);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0731] Gerar Relatório de Imóveis com os Ultimos Consumos de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 19/12/2007
	 * @param FiltrarRelatorioImoveisUltimosConsumosAguaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisUltimosConsumosAgua(FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro)
					throws ControladorException{

		try{
			return this.repositorioCadastro.pesquisarTotalRegistroRelatorioImoveisUltimosConsumosAgua(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0731] Gerar Relatório de Imóveis com os Ultimos Consumos de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 18/12/2007
	 * @param FiltrarRelatorioImoveisUltimosConsumosAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisUltimosConsumosAguaHelper> pesquisarRelatorioImoveisUltimosConsumosAgua(
					FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro) throws ControladorException{

		Collection<RelatorioImoveisUltimosConsumosAguaHelper> retorno = new ArrayList<RelatorioImoveisUltimosConsumosAguaHelper>();

		Collection<Object[]> colecaoPesquisa = null;

		try{
			colecaoPesquisa = this.repositorioCadastro.pesquisarRelatorioImoveisUltimosConsumosAgua(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			while(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				RelatorioImoveisUltimosConsumosAguaHelper helper = new RelatorioImoveisUltimosConsumosAguaHelper();

				Integer idImovel = (Integer) objeto[0];
				Integer localidade = (Integer) objeto[5];
				Integer codigoSetorComercial = (Integer) objeto[7];
				Integer numeroQuadra = (Integer) objeto[7];
				Short lote = (Short) objeto[15];
				Short subLote = (Short) objeto[16];
				helper.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(idImovel));
				helper.setGerenciaRegional((Integer) objeto[1]);
				helper.setNomeGerenciaRegional((String) objeto[2]);
				helper.setUnidadeNegocio((Integer) objeto[3]);
				helper.setNomeUnidadeNegocio((String) objeto[4]);
				helper.setLocalidade(localidade);
				helper.setDescricaoLocalidade((String) objeto[6]);
				helper.setSetorComercial(codigoSetorComercial);
				helper.setDescricaoSetorComercial((String) objeto[8]);

				helper.setNomeCliente((String) objeto[10]);
				helper.setSituacaoLigacaoAgua((String) objeto[11]);

				if(objeto[12] != null){
					helper.setSituacaoLigacaoEsgoto((String) objeto[12]);
				}

				helper.setRota((Short) objeto[13]);
				helper.setSequencialRota((Integer) objeto[14]);

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				Localidade local = new Localidade();
				local.setId(localidade);
				imovel.setLocalidade(local);

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(codigoSetorComercial);
				imovel.setSetorComercial(setorComercial);

				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra(numeroQuadra);
				imovel.setQuadra(quadra);

				imovel.setLote(lote);
				imovel.setSubLote(subLote);

				helper.setInscricaoImovel(imovel.getInscricaoFormatada());

				String endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				helper.setEndereco(endereco);

				Categoria categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

				ImovelSubcategoria imovelSubCategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

				helper.setSubCategoria(imovelSubCategoria.getComp_id().getSubcategoria().getId());
				helper.setEconomias((Short) objeto[19]);

				int anoMes = this.getControladorUtil().pesquisarParametrosDoSistema().getAnoMesFaturamento();

				String consumoAgua = "";
				String descricaoConsumo = "";

				// 1-Consumo Agua
				String[] retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 1);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo1(descricaoConsumo);
				helper.setConsumoAgua1(consumoAgua);

				// 2-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 2);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo2(descricaoConsumo);
				helper.setConsumoAgua2(consumoAgua);

				// 3-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 3);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo3(descricaoConsumo);
				helper.setConsumoAgua3(consumoAgua);

				// 4-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 4);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo4(descricaoConsumo);
				helper.setConsumoAgua4(consumoAgua);

				// 5-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 5);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo5(descricaoConsumo);
				helper.setConsumoAgua5(consumoAgua);

				// 6-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 6);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo6(descricaoConsumo);
				helper.setConsumoAgua6(consumoAgua);

				// 7-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 7);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo7(descricaoConsumo);
				helper.setConsumoAgua7(consumoAgua);

				// 8-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 8);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo8(descricaoConsumo);
				helper.setConsumoAgua8(consumoAgua);

				// 9-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 9);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo9(descricaoConsumo);
				helper.setConsumoAgua9(consumoAgua);

				// 10-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 10);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo10(descricaoConsumo);
				helper.setConsumoAgua10(consumoAgua);

				// 11-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 11);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo11(descricaoConsumo);
				helper.setConsumoAgua11(consumoAgua);

				// 12-Consumo Agua
				retornoConsumo = montarConsumoHistorico(idImovel, anoMes, 12);

				descricaoConsumo = retornoConsumo[0];
				consumoAgua = retornoConsumo[1];

				helper.setDescricaoConsumo12(descricaoConsumo);
				helper.setConsumoAgua12(consumoAgua);

				retorno.add(helper);
			}
		}

		return retorno;
	}

	/**
	 * [UC0731] Gerar Relatório de Imóveis com os Ultimos Consumos de Agua
	 * Monta os consumos anteriores do imovel
	 * 
	 * @author Rafael Pinto
	 * @date 19/12/2007
	 * @param idImovel
	 * @param anoMes
	 * @return String[3]
	 * @throws ErroRepositorioException
	 */
	private String[] montarConsumoHistorico(int idImovel, int anoMes, int qtdMeses) throws ControladorException{

		String[] retorno = new String[3];

		String consumoAgua = "";

		int anoMesSubtraido = Util.subtrairMesDoAnoMes(anoMes, qtdMeses);
		String descricaoConsumo = Util.retornaDescricaoAnoMes("" + anoMesSubtraido);

		Object[] consumoHistorico = this.getControladorMicromedicao().obterConsumoAnteriorAnormalidadeDoImovel(idImovel, anoMesSubtraido,
						LigacaoTipo.LIGACAO_AGUA);

		if(consumoHistorico != null){
			if(consumoHistorico[0] != null){
				consumoAgua = "" + (Integer) consumoHistorico[0];
			}
		}

		retorno[0] = descricaoConsumo;
		retorno[1] = consumoAgua;

		return retorno;
	}

	/**
	 * [UC00728] Gerar Relatório de Imóveis Ativos e Não Medidos
	 * 
	 * @author Rafael Pinto
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisAtivosNaoMedidosHelper
	 * @return Collection<RelatorioImoveisAtivosNaoMedidosHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisAtivosNaoMedidosHelper> pesquisarRelatorioImoveisAtivosNaoMedidos(
					FiltrarRelatorioImoveisAtivosNaoMedidosHelper filtro) throws ControladorException{

		Collection<RelatorioImoveisAtivosNaoMedidosHelper> retorno = new ArrayList<RelatorioImoveisAtivosNaoMedidosHelper>();

		Collection<Object[]> colecaoPesquisa = null;

		try{
			Integer[] idsSituacaoAguaImovelAtivo = null;

			String[] idAtual = ((String) ParametroCadastro.P_SITUACAO_AGUA_IMOVEL_ATIVO.executar()).split(",");

			idsSituacaoAguaImovelAtivo = new Integer[idAtual.length];

			for(int i = 0; i < idAtual.length; i++){
				idsSituacaoAguaImovelAtivo[i] = Util.obterInteger(idAtual[i]);
			}
			
			filtro.setIdsSituacaoAguaImovelAtivo(idsSituacaoAguaImovelAtivo);
			
			colecaoPesquisa = this.repositorioCadastro.pesquisarRelatorioImoveisAtivosNaoMedidos(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			while(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				RelatorioImoveisAtivosNaoMedidosHelper helper = new RelatorioImoveisAtivosNaoMedidosHelper();

				Integer idImovel = (Integer) objeto[0];
				helper.setGerenciaRegional((Integer) objeto[1]);
				helper.setNomeGerenciaRegional((String) objeto[2]);
				helper.setUnidadeNegocio((Integer) objeto[3]);
				helper.setNomeUnidadeNegocio((String) objeto[4]);
				Integer localidade = (Integer) objeto[5];
				helper.setDescricaoLocalidade((String) objeto[6]);
				Integer codigoSetorComercial = (Integer) objeto[7];
				helper.setDescricaoSetorComercial((String) objeto[8]);
				Integer numeroQuadra = (Integer) objeto[9];
				helper.setNomeCliente((String) objeto[10]);
				helper.setSituacaoLigacaoAgua((String) objeto[11]);
				if(objeto[12] != null){
					helper.setSituacaoLigacaoEsgoto((String) objeto[12]);
				}
				Short codigoQuadra = null;
				if(objeto[13] != null){
					codigoQuadra = (Short) objeto[13];
				}
				if(objeto[14] != null){
					helper.setSequencialRota((Integer) objeto[14]);
				}
				Short lote = (Short) objeto[15];
				Short subLote = (Short) objeto[16];
				if(objeto[18] != null){
					helper.setIdRota((Integer) objeto[18]);
				}

				helper.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(idImovel));
				helper.setLocalidade(localidade);
				helper.setSetorComercial(codigoSetorComercial);
				helper.setNumeroQuadra(numeroQuadra);

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				Localidade local = new Localidade();
				local.setId(localidade);
				imovel.setLocalidade(local);

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(codigoSetorComercial);
				imovel.setSetorComercial(setorComercial);

				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra(numeroQuadra);
				imovel.setQuadra(quadra);

				imovel.setLote(lote);
				imovel.setSubLote(subLote);

				Integer idRota = helper.getIdRota();

				if(idRota != null){
					Rota rota = new Rota();
					rota.setId(idRota);
					rota.setCodigo(codigoQuadra);
					imovel.setRota(rota);
				}

				helper.setInscricaoImovel(imovel.getInscricaoFormatada());

				String endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				helper.setEndereco(endereco);

				retorno.add(helper);
			}
		}else{

			throw new ControladorException("atencao.relatorio.vazio");
		}

		return retorno;
	}

	/**
	 * [UC00728] Gerar Relatório de Imóveis Ativos e Não Medidos
	 * 
	 * @author Rafael Pinto
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisAtivosNaoMedidosHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisAtivosNaoMedidos(FiltrarRelatorioImoveisAtivosNaoMedidosHelper filtro)
					throws ControladorException{

		try{

			Integer[] idsSituacaoAguaImovelAtivo = null;

			String[] idAtual = ((String) ParametroCadastro.P_SITUACAO_AGUA_IMOVEL_ATIVO.executar()).split(",");

			idsSituacaoAguaImovelAtivo = new Integer[idAtual.length];

			for(int i = 0; i < idAtual.length; i++){
				idsSituacaoAguaImovelAtivo[i] = Util.obterInteger(idAtual[i]);
			}

			filtro.setIdsSituacaoAguaImovelAtivo(idsSituacaoAguaImovelAtivo);

			return this.repositorioCadastro.pesquisarTotalRegistroRelatorioImoveisAtivosNaoMedidos(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC00728] Gerar Relatório de Imóveis Excluidos
	 * 
	 * @author Alcira Rocha
	 * @date 31/01/2013
	 * @param FiltrarRelatorioImoveisExcluidosHelper
	 * @return Collection<RelatorioImoveisExcluidosHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisExcluidosHelper> pesquisarRelatorioImoveisExcluidos(FiltrarRelatorioImoveisExcluidosHelper filtro)
					throws ControladorException{

		Collection<RelatorioImoveisExcluidosHelper> retorno = new ArrayList<RelatorioImoveisExcluidosHelper>();

		Collection<Object[]> colecaoPesquisa = null;

		try{
			colecaoPesquisa = this.repositorioCadastro.pesquisarRelatorioImoveisExcluidos(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			while(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				RelatorioImoveisExcluidosHelper helper = new RelatorioImoveisExcluidosHelper();

				Integer idImovel = (Integer) objeto[0];
				Integer localidade = (Integer) objeto[5];
				Integer codigoSetorComercial = (Integer) objeto[7];
				Integer numeroQuadra = (Integer) objeto[7];

				Short lote = (Short) objeto[15];
				Short subLote = (Short) objeto[16];

				helper.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(idImovel));
				helper.setGerenciaRegional((Integer) objeto[1]);
				helper.setNomeGerenciaRegional((String) objeto[2]);
				helper.setUnidadeNegocio((Integer) objeto[3]);
				helper.setNomeUnidadeNegocio((String) objeto[4]);
				helper.setLocalidade(localidade);
				helper.setDescricaoLocalidade((String) objeto[6]);
				helper.setSetorComercial(codigoSetorComercial);
				helper.setDescricaoSetorComercial((String) objeto[8]);
				helper.setNumeroQuadra(numeroQuadra);
				helper.setNomeCliente((String) objeto[10]);
				helper.setSituacaoLigacaoAgua((String) objeto[11]);

				if(objeto[12] != null){
					helper.setSituacaoLigacaoEsgoto((String) objeto[12]);
				}

				if(objeto[18] != null){
					helper.setIdRota((Integer) objeto[18]);
				}

				if(objeto[14] != null){
					helper.setSequencialRota((Integer) objeto[14]);
				}

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				Localidade local = new Localidade();
				local.setId(localidade);
				imovel.setLocalidade(local);

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(codigoSetorComercial);
				imovel.setSetorComercial(setorComercial);

				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra(numeroQuadra);
				imovel.setQuadra(quadra);

				imovel.setLote(lote);
				imovel.setSubLote(subLote);

				Integer idRota = helper.getIdRota();

				if(idRota != null){
					Rota rota = new Rota();
					rota.setId(idRota);

					imovel.setRota(rota);
				}

				helper.setInscricaoImovel(imovel.getInscricaoFormatada());

				String endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				helper.setEndereco(endereco);

				retorno.add(helper);
			}
		}else{

			throw new ControladorException("atencao.relatorio.vazio");
		}

		return retorno;
	}

	/**
	 * [UC00728] Gerar Relatório de Imóveis Excluidos
	 * 
	 * @author Alcira Rocha
	 * @date 31/01/2013
	 * @param FiltrarRelatorioImoveisExcluidosHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisExcluidos(FiltrarRelatorioImoveisExcluidosHelper filtro)
					throws ControladorException{

		try{
			return this.repositorioCadastro.pesquisarTotalRegistroRelatorioImoveisExcluidos(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0729] Gerar Relatório de Imóveis por Tipo de Consumo
	 * 
	 * @author Bruno Barros
	 * @date 10/01/2008
	 * @param RelatorioImoveisTipoConsumoHelper
	 * @return Collection<RelatorioImoveisTipoConsumoHelper>
	 * @throws FachadaException
	 */
	public Collection<RelatorioImoveisTipoConsumoHelper> pesquisarRelatorioImoveisTipoConsumo(
					FiltrarRelatorioImoveisTipoConsumoHelper filtro) throws ControladorException{

		Collection<RelatorioImoveisTipoConsumoHelper> retorno = new ArrayList<RelatorioImoveisTipoConsumoHelper>();

		Collection<Object[]> colecaoPesquisa = null;

		try{
			colecaoPesquisa = this.repositorioCadastro.pesquisarRelatorioImoveisTipoConsumo(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			while(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				RelatorioImoveisTipoConsumoHelper helper = new RelatorioImoveisTipoConsumoHelper();

				Integer idImovel = (Integer) objeto[8];
				Integer localidade = (Integer) objeto[4];
				Integer codigoSetorComercial = (Integer) objeto[6];
				Integer numeroQuadra = (Integer) objeto[15];

				helper.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(idImovel));
				helper.setGerenciaRegional((Integer) objeto[0]);
				helper.setNomeGerenciaRegional((String) objeto[1]);
				helper.setUnidadeNegocio((Integer) objeto[2]);
				helper.setNomeUnidadeNegocio((String) objeto[3]);
				helper.setLocalidade(localidade);
				helper.setDescricaoLocalidade((String) objeto[5]);
				helper.setSetorComercial(codigoSetorComercial);
				helper.setDescricaoSetorComercial((String) objeto[7]);
				helper.setNomeCliente((String) objeto[9]);
				helper.setSituacaoLigacaoAgua((String) objeto[10]);

				helper.setTipoConsumo((String) objeto[11]);
				helper.setCodigoRota((Short) objeto[12]);
				helper.setSequencialRota((Integer) objeto[13]);
				helper.setSituacaoLigacaoEsgoto((String) objeto[14]);

				// Montamos um objeto imovel para poder pesquisar sua inscrição
				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				Localidade local = new Localidade();
				local.setId(localidade);
				imovel.setLocalidade(local);

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(codigoSetorComercial);
				imovel.setSetorComercial(setorComercial);

				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra(numeroQuadra);
				imovel.setQuadra(quadra);

				imovel.setLote((Short) objeto[16]);
				imovel.setSubLote((Short) objeto[17]);

				helper.setInscricaoImovel(imovel.getInscricaoFormatada());
				// ------------------------------------------------------------

				// Selecionamos o endereço
				String endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				helper.setEndereco(endereco);

				retorno.add(helper);
			}
		}

		return retorno;
	}

	/**
	 * [UC0729] Gerar Relatório de Imóveis por Tipo de Consumo
	 * 
	 * @author Bruno Barros
	 * @date 10/01/2008
	 * @param RelatorioImoveisTipoConsumoHelper
	 * @return Collection<RelatorioImoveisTipoConsumoHelper>
	 * @throws FachadaException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisTipoConsumo(FiltrarRelatorioImoveisTipoConsumoHelper filtro)
					throws ControladorException{

		try{
			return this.repositorioCadastro.pesquisarTotalRegistroRelatorioImoveisTipoConsumo(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC00730] Gerar Relatório de Imóveis com Faturas Recentes em Dia e Faturas Antigas em
	 * Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 * @param FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper
	 * @return Collection<RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper> pesquisarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
					FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro) throws ControladorException{

		Collection<RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper> retorno = new ArrayList<RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper>();

		Collection<Object[]> colecaoPesquisa = null;

		try{
			colecaoPesquisa = this.repositorioCadastro.pesquisarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			while(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper helper = new RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper();

				Integer idImovel = (Integer) objeto[0];
				Integer localidade = (Integer) objeto[5];
				Integer codigoSetorComercial = (Integer) objeto[7];
				Integer numeroQuadra = (Integer) objeto[7];

				Short lote = (Short) objeto[15];
				Short subLote = (Short) objeto[16];

				helper.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(idImovel));
				helper.setGerenciaRegional((Integer) objeto[1]);
				helper.setNomeGerenciaRegional((String) objeto[2]);
				helper.setUnidadeNegocio((Integer) objeto[3]);
				helper.setNomeUnidadeNegocio((String) objeto[4]);
				helper.setLocalidade(localidade);
				helper.setDescricaoLocalidade((String) objeto[6]);
				helper.setSetorComercial(codigoSetorComercial);
				helper.setDescricaoSetorComercial((String) objeto[8]);

				helper.setNomeCliente((String) objeto[10]);
				helper.setSituacaoLigacaoAgua((String) objeto[11]);
				helper.setSituacaoLigacaoEsgoto((String) objeto[12]);

				helper.setRota((Short) objeto[13]);
				helper.setSequencialRota((Integer) objeto[14]);

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				Localidade local = new Localidade();
				local.setId(localidade);
				imovel.setLocalidade(local);

				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(codigoSetorComercial);
				imovel.setSetorComercial(setorComercial);

				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra(numeroQuadra);
				imovel.setQuadra(quadra);

				imovel.setLote(lote);
				imovel.setSubLote(subLote);

				helper.setInscricaoImovel(imovel.getInscricaoFormatada());

				String endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				helper.setEndereco(endereco);

				Categoria categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

				ImovelSubcategoria imovelSubCategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

				helper.setSubCategoria(imovelSubCategoria.getComp_id().getSubcategoria().getId());
				helper.setEconomias((Short) objeto[19]);

				try{
					Object[] dadosConta = this.repositorioFaturamento.pesquisarQuantidadeFaturasValorFaturas(idImovel);

					helper.setQuantidadeFaturasAtraso((Integer) dadosConta[0]);
					helper.setValorFaturasAtras((BigDecimal) dadosConta[1]);

					Object[] dadosRefenciaAntigaConta = this.repositorioFaturamento.pesquisarReferenciaAntigaContaSemPagamento(idImovel);

					helper.setReferenciaInicial((Integer) dadosRefenciaAntigaConta[0]);

					Object[] dadosRefenciaAtualConta = this.repositorioFaturamento.pesquisarReferenciaAtualContaSemPagamento(idImovel);

					helper.setReferenciaFinal((Integer) dadosRefenciaAtualConta[0]);

				}catch(ErroRepositorioException e){
					throw new ControladorException("erro.sistema", e);
				}

				retorno.add(helper);
			}
		}

		return retorno;
	}

	/**
	 * [UC00730] Gerar Relatório de Imóveis com Faturas Recentes em Dia e Faturas Antigas em
	 * Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 * @param FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
					FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro) throws ControladorException{

		try{
			return this.repositorioCadastro.pesquisarTotalRegistroRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(filtro);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Metódo responsável por retornar o próximo id da empresa para cadastro (id máximo +1)
	 * 
	 * @return
	 * @throws ControladorException
	 */
	public int pesquisarProximoIdEmpresa() throws ControladorException{

		try{
			return repositorioEmpresa.pesquisarProximoIdEmpresa();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método responsável por obter o endereço da empresa formatado.
	 * 
	 * @author Virgínia Melo
	 * @date 09/06/2009
	 * @return Array de String com as seguintes informações:
	 *         [0] - Endereço da empresa formatado (Logradouro, num, bairro, cep)
	 *         [1] - Email empresa
	 *         [2] - Telefone empresa
	 *         [3] - Logo empresa
	 *         [4] - Site empresa
	 */
	public String[] obterDadosEmpresa() throws ControladorException{

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		String[] dadosEmpresa = new String[5];

		String enderecoEmpresa = "";
		String emailEmpresa = "";
		String telefoneEmpresa = "";
		String logoEmpresa = "";
		String siteEmpresa = "";

		// Endereco Empresa
		boolean virgula = false;

		if(sistemaParametro.getLogradouro() != null){
			Logradouro logradouro = sistemaParametro.getLogradouro();
			FiltroLogradouro filtro = new FiltroLogradouro();
			filtro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, logradouro.getId()));
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouro.LOGRADOUROTITULO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouro.LOGRADOUROTIPO);
			logradouro = (Logradouro) Util.retonarObjetoDeColecao((getControladorUtil().pesquisar(filtro, Logradouro.class.getName())));

			if(logradouro != null && logradouro.getNome() != null && !logradouro.getNome().equals("")){
				enderecoEmpresa = logradouro.getDescricaoFormatada();
				virgula = true;
			}
		}

		// Adiciona o número
		if(sistemaParametro.getNumeroImovel() != null){
			enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getNumeroImovel();
			virgula = true;
		}

		// Adiciona o complemento
		if(sistemaParametro.getComplementoEndereco() != null){
			enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getComplementoEndereco();
			virgula = true;
		}

		// Adiciona o bairro
		if(sistemaParametro.getBairro() != null){
			FiltroBairro filtro = new FiltroBairro();
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, sistemaParametro.getBairro().getId()));
			Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtro, Bairro.class.getName()));
			enderecoEmpresa += (virgula == true ? ", " : "") + bairro.getNome();
			virgula = true;
		}

		// Adiciona o bairro
		if(sistemaParametro.getCep() != null){
			FiltroCep filtro = new FiltroCep();
			filtro.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, sistemaParametro.getCep().getCepId()));
			Cep cep = (Cep) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtro, Cep.class.getName()));
			enderecoEmpresa += (virgula == true ? ", Cep: " : "") + cep.getCepFormatado();
		}

		// Email Empresa
		emailEmpresa = sistemaParametro.getDsEmailResponsavel();

		// Telefone Empresa
		telefoneEmpresa = sistemaParametro.getNumeroTelefone();

		// Logo Empresa
		logoEmpresa = sistemaParametro.getImagemRelatorio();

		// Site Empresa
		siteEmpresa = ParametroCadastro.P_SITE_EMPRESA.executar();

		dadosEmpresa[0] = enderecoEmpresa;
		dadosEmpresa[1] = emailEmpresa;
		dadosEmpresa[2] = telefoneEmpresa;
		dadosEmpresa[3] = logoEmpresa;
		dadosEmpresa[4] = siteEmpresa;

		return dadosEmpresa;
	}

	/**
	 * Método responsável por inserir uma unidade operacional.
	 * 
	 * @author Péricles Tavares
	 * @date 28/01/2011
	 * @return id inserido
	 */
	public Integer inserirUnidadeOperacional(UnidadeOperacional unidadeOperacional, Usuario usuario) throws ControladorException{

		// [FS0003] - Verificar existência da Unidade Operacional
		FiltroUnidadeOperacional filtroUnidadeOperacional = new FiltroUnidadeOperacional();

		filtroUnidadeOperacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOperacional.CODIGO_UNIDADE_OPERACIONAL,
						unidadeOperacional.getCodigoUnidadeOperacional()));

		Collection colecaoUnidadeOperacional = getControladorUtil().pesquisar(filtroUnidadeOperacional, UnidadeOperacional.class.getName());

		if(colecaoUnidadeOperacional != null && !colecaoUnidadeOperacional.isEmpty()){
			throw new ControladorException("atencao.unidadeOperacional.existente");
		}

		unidadeOperacional.setUltimaAlteracao(new Date());
		unidadeOperacional.setIndicadorAtivo(ConstantesSistema.SIM);

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_UNIDADE_OPERACIONAL_INSERIR,
						((int) unidadeOperacional.getCodigoUnidadeOperacional()), unidadeOperacional.getId(), new UsuarioAcaoUsuarioHelper(
										usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_UNIDADE_OPERACIONAL_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		unidadeOperacional.setOperacaoEfetuada(operacaoEfetuada);
		unidadeOperacional.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(unidadeOperacional);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		Integer idUnidadeOperacional = (Integer) getControladorUtil().inserir(unidadeOperacional);
		unidadeOperacional.setId(idUnidadeOperacional);

		return idUnidadeOperacional;

	}

	/**
	 * Método responsável por atualizar uma unidade operacional.
	 * 
	 * @author Péricles Tavares
	 * @date 31/01/2011
	 * @pparam UnidadeOperacional
	 * @throws ControladorException
	 */
	public void atualizarUnidadeOperacional(UnidadeOperacional unidadeOperacional, Usuario usuario) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_UNIDADE_OPERACIONAL_ATUALIZAR,
						((int) unidadeOperacional.getCodigoUnidadeOperacional()), unidadeOperacional.getId(), new UsuarioAcaoUsuarioHelper(
										usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_UNIDADE_OPERACIONAL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		unidadeOperacional.setOperacaoEfetuada(operacaoEfetuada);
		unidadeOperacional.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(unidadeOperacional);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		// [FS0002] - Atualização realizada por outro usuário
		FiltroUnidadeOperacional filtroUnidadeOperacional = new FiltroUnidadeOperacional();
		// Seta o filtro para buscar o municipio na base
		filtroUnidadeOperacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOperacional.ID, unidadeOperacional.getId()));

		// Procura unidades na base
		Collection unidadesOperacionaisBase = getControladorUtil().pesquisar(filtroUnidadeOperacional, UnidadeOperacional.class.getName());

		UnidadeOperacional unidadeOperacionalBase = (UnidadeOperacional) Util.retonarObjetoDeColecao(unidadesOperacionaisBase);

		if(unidadeOperacionalBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o municipio já foi atualizado por outro usuário
		// durante esta atualização

		if(unidadeOperacionalBase.getUltimaAlteracao().after(unidadeOperacional.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		unidadeOperacional.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(unidadeOperacional);

	}

	/**
	 * Método responsável por atualizar uma unidade operacional.
	 * 
	 * @author Péricles Tavares
	 * @date 31/01/2011
	 * @pparam UnidadeOperacional
	 * @throws ControladorException
	 */
	public void removerUnidadeOperacional(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			FiltroUnidadeOperacional filtro = new FiltroUnidadeOperacional();
			filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOperacional.ID, new Integer(ids[i])));

			Collection colecaoRetorno = this.pesquisarUnidadeOperacionalFiltro(filtro);
			FiltroSetorAbastecimento filtroSetorAbastecimento = new FiltroSetorAbastecimento();
			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.UNIDADE_OPERACIONAL_ID, new Integer(
							ids[i])));
			Collection colecaoSetorAbastecimento = getControladorUtil().pesquisar(filtroSetorAbastecimento,
							SetorAbastecimento.class.getName());

			if(colecaoSetorAbastecimento != null && !colecaoSetorAbastecimento.isEmpty()){
				throw new ControladorException("atencao.dependencias.outras.informacoes.existentes");
			}

			UnidadeOperacional unidadeOperacional = (UnidadeOperacional) Util.retonarObjetoDeColecao(colecaoRetorno);
			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_UNIDADE_OPERACIONAL_REMOVER,
							((int) unidadeOperacional.getCodigoUnidadeOperacional()), unidadeOperacional.getId(),
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_UNIDADE_OPERACIONAL_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			unidadeOperacional.setOperacaoEfetuada(operacaoEfetuada);
			registradorOperacao.registrarOperacao(unidadeOperacional);

			// ------------ REGISTRAR TRANSAÇÃO ----------------

			// [FS0003]  Unidade Operacional possui vínculos no sistema
			// TODO - Fazer assim que tiver as outras entidades
			this.getControladorUtil().remover(unidadeOperacional);
		}

	}

	/**
	 * [UCXXXX] Manter Unidade Operacional
	 * Pesquisa Unidade Operacional pelo filtro
	 * 
	 * @author Péricles Tavares
	 * @date 04/02/2011
	 * @param filtroUnidadeOperacional
	 * @throws ControladorException
	 */
	public Collection pesquisarUnidadeOperacionalFiltro(FiltroUnidadeOperacional filtroUnidadeOperacional) throws ControladorException{

		Collection retorno = null;
		retorno = getControladorUtil().pesquisar(filtroUnidadeOperacional, UnidadeOperacional.class.getName());

		return retorno;
	}

	/**
	 * [UC0053] Inserir Dados Censitários
	 * Método inserção dos Dados Censitários da Localidade
	 * 
	 * @author Anderson Italo
	 * @date 09/02/2011
	 * @param localidadeDadosCensitario
	 *            , usuario
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirLocalidadeDadosCensitario(LocalidadeDadosCensitario localidadeDadosCensitario, Usuario usuario)
					throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO LOCALIDADE
		// DADOS CENSITARIO ----------------------------
		RegistradorOperacao registradorOperacaoSistemaAbastecimento = new RegistradorOperacao(Operacao.OPERACAO_DADOS_CENSITARIOS_INSERIR,
						localidadeDadosCensitario.getLocalidade().getId(), localidadeDadosCensitario.getId(), new UsuarioAcaoUsuarioHelper(
										usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoUnidade = new Operacao();
		operacaoUnidade.setId(Operacao.OPERACAO_DADOS_CENSITARIOS_INSERIR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoUnidade);

		localidadeDadosCensitario.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		localidadeDadosCensitario.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoSistemaAbastecimento.registrarOperacao(localidadeDadosCensitario);
		// ------------ REGISTRAR TRANSAÇÃO LOCALIDADE
		// DADOS CENSITARIO ----------------------------

		return this.getControladorUtil().inserir(localidadeDadosCensitario);
	}

	/**
	 * [UC0053] Inserir Dados Censitários
	 * Método inserção dos Dados Censitários de Município
	 * 
	 * @author Anderson Italo
	 * @date 09/02/2011
	 * @param municipioDadosCensitario
	 *            , usuario
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirMunicipioDadosCensitario(MunicipioDadosCensitario municipioDadosCensitario, Usuario usuario)
					throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO MUNICIPIO
		// DADOS CENSITARIO ----------------------------
		RegistradorOperacao registradorOperacaoSistemaAbastecimento = new RegistradorOperacao(Operacao.OPERACAO_DADOS_CENSITARIOS_INSERIR,
						municipioDadosCensitario.getMunicipio().getId(), municipioDadosCensitario.getId(), new UsuarioAcaoUsuarioHelper(
										usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoUnidade = new Operacao();
		operacaoUnidade.setId(Operacao.OPERACAO_DADOS_CENSITARIOS_INSERIR);

		OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
		operacaoEfetuadaUnidade.setOperacao(operacaoUnidade);

		municipioDadosCensitario.setOperacaoEfetuada(operacaoEfetuadaUnidade);
		municipioDadosCensitario.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoSistemaAbastecimento.registrarOperacao(municipioDadosCensitario);
		// ------------ REGISTRAR TRANSAÇÃO LOCALIDADE
		// DADOS CENSITARIO ----------------------------

		return this.getControladorUtil().inserir(municipioDadosCensitario);
	}

	/**
	 * [UCXXXX] Manter Dados Censitarios
	 * [SB0002] - Excluir Dados Censitarios
	 * 
	 * @author Anderson Italo
	 * @date 11/02/2011
	 * @param ids
	 *            , usuarioLogado, dadosLocalidade
	 * @throws ControladorException
	 */
	public void removerDadosCensitarios(String[] ids, Usuario usuarioLogado, boolean dadosLocalidade) throws ControladorException{

		for(int i = 0; i < ids.length; i++){

			if(dadosLocalidade){
				// ------------ REGISTRAR TRANSAÇÃO ----------------
				FiltroLocalidadeDadosCensitario filtro = new FiltroLocalidadeDadosCensitario();
				filtro.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadosCensitario.ID, new Integer(ids[i])));

				Collection colecaoRetorno = this.getControladorUtil().pesquisar(filtro, LocalidadeDadosCensitario.class.getName());

				LocalidadeDadosCensitario localidadeDadosCensitarioRemocao = (LocalidadeDadosCensitario) Util
								.retonarObjetoDeColecao(colecaoRetorno);

				RegistradorOperacao registradorDadosCensitarios = new RegistradorOperacao(Operacao.OPERACAO_DADOS_CENSITARIOS_REMOVER,
								localidadeDadosCensitarioRemocao.getLocalidade().getId(), localidadeDadosCensitarioRemocao.getId(),
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_DADOS_CENSITARIOS_REMOVER);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				localidadeDadosCensitarioRemocao.setOperacaoEfetuada(operacaoEfetuada);

				registradorDadosCensitarios.registrarOperacao(localidadeDadosCensitarioRemocao);
				// ------------ REGISTRAR TRANSAÇÃO ----------------

				this.getControladorUtil().remover(localidadeDadosCensitarioRemocao);
			}else{

				// ------------ REGISTRAR TRANSAÇÃO ----------------
				FiltroMunicipioDadosCensitario filtro = new FiltroMunicipioDadosCensitario();
				filtro.adicionarParametro(new ParametroSimples(FiltroMunicipioDadosCensitario.ID, new Integer(ids[i])));

				Collection colecaoRetorno = this.getControladorUtil().pesquisar(filtro, MunicipioDadosCensitario.class.getName());

				MunicipioDadosCensitario municipioDadosCensitarioRemocao = (MunicipioDadosCensitario) Util
								.retonarObjetoDeColecao(colecaoRetorno);

				RegistradorOperacao registradorDadosCensitarios = new RegistradorOperacao(Operacao.OPERACAO_DADOS_CENSITARIOS_REMOVER,
								municipioDadosCensitarioRemocao.getMunicipio().getId(), municipioDadosCensitarioRemocao.getId(),
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_DADOS_CENSITARIOS_REMOVER);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				municipioDadosCensitarioRemocao.setOperacaoEfetuada(operacaoEfetuada);

				registradorDadosCensitarios.registrarOperacao(municipioDadosCensitarioRemocao);
				// ------------ REGISTRAR TRANSAÇÃO ----------------

				this.getControladorUtil().remover(municipioDadosCensitarioRemocao);
			}

		}
	}

	/**
	 * [UCXXXX] Manter Dados Censitarios
	 * [SB0003] - Atualizar Dados Censitarios
	 * 
	 * @author Anderson Italo
	 * @date 14/02/2011
	 * @param dadosCensitarios
	 *            , usuario
	 * @throws ControladorException
	 */
	public void atualizarDadosCensitarios(Object dadosCensitarios, Usuario usuarioLogado) throws ControladorException{

		// [FS0003  Atualização realizada por outro usuário]
		// caso seja dados de uma localidade
		if(dadosCensitarios instanceof LocalidadeDadosCensitario){

			LocalidadeDadosCensitario localidadeDadosCensitarios = (LocalidadeDadosCensitario) dadosCensitarios;

			FiltroLocalidadeDadosCensitario filtro = new FiltroLocalidadeDadosCensitario();

			filtro.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadosCensitario.ID, localidadeDadosCensitarios.getId()));
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidadeDadosCensitario.LOCALIDADE);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidadeDadosCensitario.FONTE_DADOS_CENSITARIO);

			// Procura a localidadeDadosCensitario na base
			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, LocalidadeDadosCensitario.class.getName());

			if(colecaoRetorno == null || colecaoRetorno.isEmpty()){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			LocalidadeDadosCensitario localidadeDadosCensitarioNaBase = (LocalidadeDadosCensitario) Util
							.retonarObjetoDeColecao(colecaoRetorno);

			// Verificar se os dados censitários da localidade já foi atualizado por outro
			// usuário durante esta atualização
			if(localidadeDadosCensitarioNaBase.getUltimaAlteracao().after(localidadeDadosCensitarioNaBase.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// ------------ REGISTRAR TRANSAÇÃO ---------------------
			RegistradorOperacao registrador = new RegistradorOperacao(Operacao.OPERACAO_DADOS_CENSITARIOS_ATUALIZAR,
							localidadeDadosCensitarios.getLocalidade().getId(), localidadeDadosCensitarios.getId(),
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_DADOS_CENSITARIOS_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
			operacaoEfetuadaUnidade.setOperacao(operacao);

			localidadeDadosCensitarios.setOperacaoEfetuada(operacaoEfetuadaUnidade);
			localidadeDadosCensitarios.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registrador.registrarOperacao(localidadeDadosCensitarios);
			// ------------ REGISTRAR TRANSAÇÃO --------------------

			getControladorUtil().atualizar(localidadeDadosCensitarios);

		}else{

			// caso contrário são dados de um município
			MunicipioDadosCensitario municipioDadosCensitarios = (MunicipioDadosCensitario) dadosCensitarios;

			FiltroMunicipioDadosCensitario filtro = new FiltroMunicipioDadosCensitario();

			filtro.adicionarParametro(new ParametroSimples(FiltroMunicipioDadosCensitario.ID, municipioDadosCensitarios.getId()));
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroMunicipioDadosCensitario.MUNICIPIO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroMunicipioDadosCensitario.FONTE_DADOS_CENSITARIO);

			// Procura o municipioDadosCensitario na base
			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, MunicipioDadosCensitario.class.getName());

			if(colecaoRetorno == null || colecaoRetorno.isEmpty()){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			MunicipioDadosCensitario municipioDadosCensitarioNaBase = (MunicipioDadosCensitario) Util
							.retonarObjetoDeColecao(colecaoRetorno);

			// Verificar se os dados censitários do município já foi atualizado por outro
			// usuário durante esta atualização
			if(municipioDadosCensitarioNaBase.getUltimaAlteracao().after(municipioDadosCensitarioNaBase.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// ------------ REGISTRAR TRANSAÇÃO ---------------------
			RegistradorOperacao registrador = new RegistradorOperacao(Operacao.OPERACAO_DADOS_CENSITARIOS_ATUALIZAR,
							municipioDadosCensitarios.getMunicipio().getId(), municipioDadosCensitarios.getId(),
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_DADOS_CENSITARIOS_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuadaUnidade = new OperacaoEfetuada();
			operacaoEfetuadaUnidade.setOperacao(operacao);

			municipioDadosCensitarios.setOperacaoEfetuada(operacaoEfetuadaUnidade);
			municipioDadosCensitarios.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registrador.registrarOperacao(municipioDadosCensitarios);
			// ------------ REGISTRAR TRANSAÇÃO --------------------

			getControladorUtil().atualizar(municipioDadosCensitarios);
		}
	}

	/**
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2011
	 *       Obter dados dos Grandes Consumidores pelos parametros informados
	 */
	public Collection pesquisaRelatorioGrandesConsumidores(Integer idGerencia, Integer idLocalidadeInicial, Integer idLocalidadeFinal)
					throws ControladorException{

		Collection colecaoRetorno = null;
		try{

			colecaoRetorno = this.repositorioCadastro.pesquisaRelatorioGrandesConsumidores(idGerencia, idLocalidadeInicial,
							idLocalidadeFinal);

			if(colecaoRetorno == null || colecaoRetorno.isEmpty()) throw new ControladorException("atencao.relatorio.vazio");

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 18/02/2011
	 *       Obter total de registros do Relatório Grandes
	 *       Consumidores
	 */
	public Integer pesquisaTotalRegistrosRelatorioGrandesConsumidores(Integer idGerencia, Integer idLocalidadeInicial,
					Integer idLocalidadeFinal) throws ControladorException{

		Integer retorno = null;
		try{
			retorno = this.repositorioCadastro.pesquisarTotalRegistrosRelatorioGrandesConsumidores(idGerencia, idLocalidadeInicial,
							idLocalidadeFinal);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Relatório Usuários em Débito Automático
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2011 Obter dados dos Usuários com Débito Automático
	 */
	public Collection pesquisaRelatorioUsuarioDebitoAutomatico(Integer idBancoInicial, Integer idBancoFinal) throws ControladorException{

		Collection colecaoRetorno = null;
		try{

			colecaoRetorno = this.repositorioCadastro.pesquisaRelatorioUsuarioDebitoAutomatico(idBancoInicial, idBancoFinal);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC0XXX] Relatório Usuários em Débito Automático
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2011 Obter Total de Registros do Relatório Usuários com
	 *       Débito Automático
	 */
	public Integer pesquisarTotalRegistrosRelatorioUsuarioDebitoAutomatico(Integer idBancoInicial, Integer idBancoFinal)
					throws ControladorException{

		Integer retorno = null;
		try{
			retorno = this.repositorioCadastro.pesquisarTotalRegistrosRelatorioUsuarioDebitoAutomatico(idBancoInicial, idBancoFinal);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Permite inserir um Consumo por Faixa de Área e Categoria
	 * [UCXXXX] Inserir Consumo por Faixa de Área e Categoria
	 * 
	 * @author Ailton Sousa
	 * @date 01/03/2011
	 */
	public int inserirConsumoFaixaAreaCategoria(ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria, Usuario usuarioLogado)
					throws ControladorException{

		// [FS0003] - Verificando a existência do Consumo por Faixa de Área e Categoria
		FiltroConsumoFaixaAreaCategoria filtroConsumoFaixaAreaCategoria = new FiltroConsumoFaixaAreaCategoria();

		filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.CATEGORIA_ID,
						consumoFaixaAreaCategoria.getCategoria().getId()));
		filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.FAIXA_INICIAL_AREA,
						consumoFaixaAreaCategoria.getFaixaInicialArea()));
		filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.FAIXA_FINAL_AREA,
						consumoFaixaAreaCategoria.getFaixaFinalArea()));

		Collection colecaoConsumoFaixaAreaCategoria = getControladorUtil().pesquisar(filtroConsumoFaixaAreaCategoria,
						ConsumoFaixaAreaCategoria.class.getName());

		if(colecaoConsumoFaixaAreaCategoria != null && !colecaoConsumoFaixaAreaCategoria.isEmpty()){
			throw new ControladorException("atencao.entidade.ja.cadastrada", null, "Consumo por Faixa de Área e Categoria");
		}

		// Verifica se a faixa inserida não está dentro de uma outra faixa já cadastrada
		if(this.validarInclusaoFaixaCategoria(consumoFaixaAreaCategoria)){
			throw new ControladorException("atencao.faixa.ja.cadastrada", null);
		}

		consumoFaixaAreaCategoria.setUltimaAlteracao(new Date());

		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		consumoFaixaAreaCategoria.setOperacaoEfetuada(operacaoEfetuada);
		consumoFaixaAreaCategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(consumoFaixaAreaCategoria);
		// Fim - Registrando as transações

		Integer idConsumoFaixaAreaCategoria = (Integer) getControladorUtil().inserir(consumoFaixaAreaCategoria);
		consumoFaixaAreaCategoria.setId(idConsumoFaixaAreaCategoria);

		return idConsumoFaixaAreaCategoria;
	}

	/**
	 * Verifica se já existe a faixa inserida no intervalo de faixas já cadastradas por Categoria
	 * 
	 * @author Ailton Sousa
	 * @data 01/03/2011
	 * @param consumoFaixaAreaCategoria
	 * @return true (caso a faixa esteja entre um intervalo ja cadastrado) ou false (caso contrario)
	 * @throws ControladorException
	 */
	public boolean validarInclusaoFaixaCategoria(ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria) throws ControladorException{

		boolean existe = false;

		// [FS0003] - Verificando a existência do Consumo por Faixa de Área e Categoria
		FiltroConsumoFaixaAreaCategoria filtroConsumoFaixaAreaCategoria = new FiltroConsumoFaixaAreaCategoria();

		filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.CATEGORIA_ID,
						consumoFaixaAreaCategoria.getCategoria().getId()));

		Collection colecaoConsumoFaixaAreaCategoria = getControladorUtil().pesquisar(filtroConsumoFaixaAreaCategoria,
						ConsumoFaixaAreaCategoria.class.getName());

		if(colecaoConsumoFaixaAreaCategoria != null && !colecaoConsumoFaixaAreaCategoria.isEmpty()){

			Iterator iter = colecaoConsumoFaixaAreaCategoria.iterator();
			while(iter.hasNext()){
				ConsumoFaixaAreaCategoria consumoFaixaAreaCategoriaConsulta = (ConsumoFaixaAreaCategoria) iter.next();

				if(consumoFaixaAreaCategoriaConsulta.getFaixaInicialArea() != null
								&& consumoFaixaAreaCategoriaConsulta.getFaixaFinalArea() != null){

					if((consumoFaixaAreaCategoria.getFaixaInicialArea().compareTo(consumoFaixaAreaCategoriaConsulta.getFaixaInicialArea()) >= 0 && consumoFaixaAreaCategoria
									.getFaixaInicialArea().compareTo(consumoFaixaAreaCategoriaConsulta.getFaixaFinalArea()) <= 0)
									|| (consumoFaixaAreaCategoria.getFaixaFinalArea().compareTo(
													consumoFaixaAreaCategoriaConsulta.getFaixaInicialArea()) >= 0 && consumoFaixaAreaCategoria
													.getFaixaFinalArea().compareTo(consumoFaixaAreaCategoriaConsulta.getFaixaFinalArea()) <= 0)){

						// Caso seja na alteração, verifica se o que está na base não é ele
						// mesmo.
						if(consumoFaixaAreaCategoria.getId() != null){
							if(!consumoFaixaAreaCategoria.getId().equals(consumoFaixaAreaCategoriaConsulta.getId())){
								existe = true;
							}
						}else{
							existe = true;
						}
					}

				}

				if(existe){
					break;
				}

			}
		}

		return existe;

	}

	/**
	 * [UC3006] Manter Consumo por Faixa de Área e Categoria [SB0001]Atualizar Consumo por Faixa de
	 * Área e Categoria
	 * 
	 * @author Ailton Sousa
	 * @date 02/03/2011
	 */
	public void atualizarConsumoFaixaAreaCategoria(ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria, Usuario usuarioLogado)
					throws ControladorException{

		// [FS0002] - Atualização realizada por outro usuário

		FiltroConsumoFaixaAreaCategoria filtroConsumoFaixaAreaCategoria = new FiltroConsumoFaixaAreaCategoria();
		// Seta o filtro para buscar o Consumo por Faixa de Área e Categoria na base
		filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.ID,
						consumoFaixaAreaCategoria.getId()));

		// Procura o Consumo por Faixa de Área e Categoria na base
		Collection consumoFaixaAreaCategoriaAtualizados = getControladorUtil().pesquisar(filtroConsumoFaixaAreaCategoria,
						ConsumoFaixaAreaCategoria.class.getName());

		ConsumoFaixaAreaCategoria consumoFaixaAreaCategoriaNaBase = (ConsumoFaixaAreaCategoria) Util
						.retonarObjetoDeColecao(consumoFaixaAreaCategoriaAtualizados);

		if(consumoFaixaAreaCategoriaNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o Consumo por Faixa de Área e Categoria já foi atualizado por outro
		// usuário
		// durante esta atualização

		if(consumoFaixaAreaCategoriaNaBase.getUltimaAlteracao().after(consumoFaixaAreaCategoria.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Verifica se a faixa alterada não está dentro de uma outra faixa já cadastrada
		if(this.validarInclusaoFaixaCategoria(consumoFaixaAreaCategoria)){
			throw new ControladorException("atencao.faixa.ja.cadastrada", null);
		}

		consumoFaixaAreaCategoria.setUltimaAlteracao(new Date());

		// [UC] - Registrar Transação
		// Início - Registrando as transações
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR,
						consumoFaixaAreaCategoria.getId(), consumoFaixaAreaCategoria.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		consumoFaixaAreaCategoria.setOperacaoEfetuada(operacaoEfetuada);
		consumoFaixaAreaCategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(consumoFaixaAreaCategoria);
		// Fim - Registrando as transações

		// Atualiza o objeto na base
		getControladorUtil().atualizar(consumoFaixaAreaCategoria);

	}

	/**
	 * [UC3006] Manter Consumo por Faixa de Área e Categoria [SB0002]Remover Consumo por Faixa de
	 * Área e Categoria
	 * 
	 * @author Ailton Sousa
	 * @date 02/03/2011
	 */
	public void removerConsumoFaixaAreaCategoria(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			FiltroConsumoFaixaAreaCategoria filtro = new FiltroConsumoFaixaAreaCategoria();
			filtro.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.ID, new Integer(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, ConsumoFaixaAreaCategoria.class.getName());

			ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria = (ConsumoFaixaAreaCategoria) Util.retonarObjetoDeColecao(colecaoRetorno);

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorConsumoFaixaAreaCategoria = new RegistradorOperacao(
							Operacao.OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, consumoFaixaAreaCategoria.getId(),
							consumoFaixaAreaCategoria.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			consumoFaixaAreaCategoria.setOperacaoEfetuada(operacaoEfetuada);

			registradorConsumoFaixaAreaCategoria.registrarOperacao(consumoFaixaAreaCategoria);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			// [FS0003]Consumo por Faixa de Área e Categoria possui vinculos no sistema
			this.getControladorUtil().remover(consumoFaixaAreaCategoria);
		}
	}

	/**
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoLocalidade(Integer idLocalidade) throws ControladorException{

		try{
			return repositorioLocalidade.obterDescricaoLocalidade(idLocalidade);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Este caso de uso permite Gerar Arquivo Agencia Virtual WEB
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * 
	 * @autor Josenildo Neves
	 * @date 20/03/2012
	 * @param
	 * @return void
	 */
	public void gerarArquivoAgenciaVirtualWeb(String anoBase, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, Integer.parseInt(anoBase));

		Date dataGravacaoArquivo = new Date();

		try{

			long dif = 0L, ini = 0L;

			// ini = System.currentTimeMillis();
			// this.gerarDadosArquivoQuitacaoDebitoTxt(anoBase, dataGravacaoArquivo);
			// dif = System.currentTimeMillis() - ini;
			// System.out.println("1.0 - ############################## -> " + dif);

			// ini = System.currentTimeMillis();
			// this.gerarDadosArquivoLogradouro(dataGravacaoArquivo);
			// dif = System.currentTimeMillis() - ini;
			// System.out.println("2.0 - ############################## -> " + dif);

			// ini = System.currentTimeMillis();
			// Collection<ImovelArquivoAgenciaVirtualWebHelper> collImoveis =
			// repositorioImovel.pesquisarImovelArquivoAgenciaVirtualWeb();
			// dif = System.currentTimeMillis() - ini;
			// System.out.println("3.0 - ############################## -> " + dif);

			// ini = System.currentTimeMillis();
			// this.gerarDadosArquivoImoveisTxt(collImoveis, dataGravacaoArquivo);
			// dif = System.currentTimeMillis() - ini;
			// System.out.println("4.0 - ############################## -> " + dif);

			// ini = System.currentTimeMillis();
			// this.gerarDadosArquivoDebitosImoveisTxt(collImoveis, dataGravacaoArquivo);
			// dif = System.currentTimeMillis() - ini;
			// System.out.println("5.0 - ############################## -> " + dif);

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

	}

	/**
	 * Este metódo gera um arquivo de logradouro para agencia virtual web.
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * 
	 * @autor Josenildo Neves
	 * @date 23/03/2012
	 */
	public void gerarDadosArquivoLogradouro(int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{

			StringBuilder arquivoTxtLinha = new StringBuilder();
			// // Calendar a = Calendar.getInstance();
			// FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
			// filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouro.LOGRADOUROTIPO);
			// filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouro.LOGRADOUROTITULO);
			// filtroLogradouro.setCampoOrderBy(FiltroLogradouro.ID);
			//
			// Collection collLogradouro = this.getControladorUtil().pesquisar(filtroLogradouro,
			// Logradouro.class.getName());
			Collection collLogradouro;
			try{
				collLogradouro = repositorioCadastro.obterLogradouro();
				if(!Util.isVazioOrNulo(collLogradouro)){
					Iterator iter = collLogradouro.iterator();
					while(iter.hasNext()){
						Object[] retornoArray = (Object[]) iter.next();
						if(retornoArray[2] != null){
							arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda((Integer) retornoArray[2], "0", 3));
						}else{
							arquivoTxtLinha.append(Util.adicionarZerosEsqueda(3, ""));
						}
						if(retornoArray[0] != null){
							arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda((Integer) retornoArray[0], "0", 6));
						}
						if(retornoArray[3] != null){
							arquivoTxtLinha.append(Util.completaString((String) retornoArray[3], 3));
						}else{
							arquivoTxtLinha.append(Util.completaString("", 3));
						}
						if(retornoArray[4] != null){
							arquivoTxtLinha.append(Util.completaString((String) retornoArray[4], 3));
						}else{
							arquivoTxtLinha.append(Util.completaString("", 3));
						}
						if(retornoArray[1] != null){
							arquivoTxtLinha.append(Util.completaString((String) retornoArray[1], 30));
						}
						arquivoTxtLinha.append(System.getProperty("line.separator"));
					}
				}
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}
			// arquivoTxtLinha.append("tempo total escrever: " + Util.calcularDiferencaTempo(a));
			this.gravarArquivoAgenciaVirtualZip(arquivoTxtLinha, "relatorioAgenciaVirtualLogradouros", null);

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

		// this.enviarArquivoAgenciaVirtualLogradourosBatch(arquivoTxtLinha,
		// "relatorioAgenciaVirtualLogradouros.txt", usuario);
	}

	/**
	 * Este metódo gera um arquivo de Quitação de débitos para agencia virtual web.
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * 
	 * @autor Josenildo Neves
	 * @date 04/09/2012
	 */
	public void gerarDadosArquivoQuitacaoDebitoTxt(String anoBase, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{

			StringBuilder arquivoTxtLinhaQuitacaoDebito = new StringBuilder();
			Collection<QuitacaoDebitoAnual> colecaoQuitacaoDebitoAnual;
			try{
				colecaoQuitacaoDebitoAnual = repositorioCobranca.consultarQuitacaoDebitoAnual(anoBase);
			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
			// Calendar a = Calendar.getInstance();
			if(!Util.isVazioOrNulo(colecaoQuitacaoDebitoAnual)){
				for(QuitacaoDebitoAnual quitacaoDebitoAnual : colecaoQuitacaoDebitoAnual){
					Imovel imovel = quitacaoDebitoAnual.getImovel();
					arquivoTxtLinhaQuitacaoDebito.append(Util.completarNumeroComValorEsquerda(imovel.getId(), "0", 10));
					if(imovel.getLocalidade() != null){
						arquivoTxtLinhaQuitacaoDebito.append(Util.completarNumeroComValorEsquerda(imovel.getLocalidade().getId(), "0", 3));
					}else{
						arquivoTxtLinhaQuitacaoDebito.append(Util.adicionarZerosEsquedaNumero(3, ""));
					}
					if(imovel.getSetorComercial() != null){
						arquivoTxtLinhaQuitacaoDebito.append(Util.completarNumeroComValorEsquerda(imovel.getSetorComercial().getCodigo(),
										"0", 2));
					}else{
						arquivoTxtLinhaQuitacaoDebito.append(Util.adicionarZerosEsquedaNumero(2, ""));
					}
					if(imovel.getRota() != null){
						arquivoTxtLinhaQuitacaoDebito.append(Util.completarNumeroComValorEsquerda(imovel.getRota().getCodigo().intValue(),
										"0", 2));
					}else{
						arquivoTxtLinhaQuitacaoDebito.append(Util.adicionarZerosEsquedaNumero(2, ""));
					}
					if(imovel.getNumeroSegmento() != null){
						arquivoTxtLinhaQuitacaoDebito.append(Util.completarNumeroComValorEsquerda(imovel.getNumeroSegmento().intValue(),
										"0", 2));
					}else{
						arquivoTxtLinhaQuitacaoDebito.append(Util.adicionarZerosEsquedaNumero(2, ""));
					}
					arquivoTxtLinhaQuitacaoDebito.append(Util.completarNumeroComValorEsquerda(Integer.parseInt(imovel.getLote() + ""), "0",
									4));
					arquivoTxtLinhaQuitacaoDebito.append(Util.completarNumeroComValorEsquerda(Integer.parseInt(imovel.getSubLote() + ""),
									"0", 2));
					arquivoTxtLinhaQuitacaoDebito.append(Util.completaString(quitacaoDebitoAnual.getClienteUsuario().getNome(), 28));
					// [UC0085]Obter Endereco
					String enderecoFormatado = this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(
									quitacaoDebitoAnual.getClienteUsuario().getId(), false);
					arquivoTxtLinhaQuitacaoDebito.append(Util.completaString(enderecoFormatado, 50));
					arquivoTxtLinhaQuitacaoDebito.append(System.getProperty("line.separator"));
				}
			}
			Usuario usuario = new Usuario();
			usuario.setId(Usuario.ID_USUARIO_ADM_SISTEMA);
			// arquivoTxtLinhaQuitacaoDebito.append("tempo total escrever: " +
			// Util.calcularDiferencaTempo(a));
			this.gravarArquivoAgenciaVirtualZip(arquivoTxtLinhaQuitacaoDebito, "relatorioAgenciaVirtualQuitDebitos", null);

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

		/*
		 * this.enviarArquivoAgenciaVirtualQuitacaoDebitosBatch(arquivoTxtLinhaQuitacaoDebito,
		 * "relatorioAgenciaVirtualQuitDebitos.txt",
		 * usuario);
		 */

	}

	/**
	 * Este caso de uso permite Gerar Arquivo Agencia Virtual WEB
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * Gera os dados do arquivo de imoveis.
	 * 
	 * @autor Josenildo Neves
	 * @date 21/03/2012
	 * @throws ControladorException
	 */
	public void gerarDadosArquivoImoveisTxt(Integer idSetorComercial, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetorComercial);

		try{

			long dif = 0L, ini = 0L;
			ini = System.currentTimeMillis();
			Collection<ImovelArquivoAgenciaVirtualWebHelper> collImoveis = repositorioImovel
							.pesquisarImovelArquivoAgenciaVirtualWebPorSetorComercial(idSetorComercial);
			dif = System.currentTimeMillis() - ini;
			System.out.println("Setor Comercial: " + idSetorComercial + " - ############################## -> " + dif);
			StringBuilder arquivoTxtLinha = new StringBuilder();
			Integer idClienteResponsavel = null;
			// Integer idEsferaPoder = null;
			for(ImovelArquivoAgenciaVirtualWebHelper helper : collImoveis){
				Integer sumCategoriaResidencial = 0;
				Integer sumCategoriaComercial = 0;
				Integer sumCategoriaIndustrial = 0;
				Integer sumCategoriaPublica = 0;
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(10, helper.getIdImovel().toString()));
				if(helper.getIdLocalidade() != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(helper.getIdLocalidade(), "0", 3));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, ""));
				}
				if(helper.getCdSetorComercial() != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(helper.getCdSetorComercial(), "0", 2));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, ""));
				}
				if(helper.getCdRota() != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(helper.getCdRota().intValue(), "0", 2));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, ""));
				}
				if(helper.getNumeroSeguimento() != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(helper.getNumeroSeguimento().intValue(), "0", 2));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, ""));
				}
				if(helper.getNumeroLote() != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(helper.getNumeroLote().intValue(), "0", 4));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, ""));
				}
				if(helper.getNumeroSubLote() != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(helper.getNumeroSubLote().intValue(), "0", 2));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, ""));
				}
				arquivoTxtLinha.append(Util.completaString(helper.getNomeCliente(), 28));
				// [UC0085]Obter Endereco
				Object[] arrayEndereco = this.getControladorEndereco().pesquisarEnderecoClienteAbreviadoLista(helper.getIdCliente(), false);
				if(arrayEndereco[1] != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda((Integer) arrayEndereco[1], "0", 6));
				}else{
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(0, "0", 6));
				}
				if(arrayEndereco[2] != null){
					arquivoTxtLinha.append(Util.completaString(String.valueOf(arrayEndereco[2]), 5));
				}else{
					arquivoTxtLinha.append(Util.completaString("", 5));
				}
				if(arrayEndereco[0] != null){
					arquivoTxtLinha.append(Util.completaString(String.valueOf(arrayEndereco[0]), 50));
				}else{
					arquivoTxtLinha.append(Util.completaString("", 50));
				}
				Collection collSomaQuantidadeEconomias = getControladorImovel().pesquisarObterQuantidadeEconomias(helper.getIdImovel());
				if(collSomaQuantidadeEconomias != null && !collSomaQuantidadeEconomias.isEmpty()){
					Iterator it = collSomaQuantidadeEconomias.iterator();
					while(it.hasNext()){
						Object[] retorno = (Object[]) it.next();
						Integer idCategoria = (Integer) retorno[1];
						// TODO: Josenildo Neves.
						// Durante os testes foi identificado um erro null point no idCategoria
						// e na variável retorno[0]
						// Coloquei o if if(!Util.isVazioOuBranco(idCategoria) &&
						// !Util.isVazioOuBranco(retorno[0])) para testar
						// mas, tem que saber se é certo ou não. Pode ser problema de banco.
						if(!Util.isVazioOuBranco(idCategoria) && !Util.isVazioOuBranco(retorno[0])){
							if(idCategoria.equals(Categoria.RESIDENCIAL)){
								sumCategoriaResidencial = (Integer) retorno[0];
							}
							if(idCategoria.equals(Categoria.COMERCIAL)){
								sumCategoriaComercial = (Integer) retorno[0];
							}
							if(idCategoria.equals(Categoria.INDUSTRIAL)){
								sumCategoriaIndustrial = (Integer) retorno[0];
							}
							if(idCategoria.equals(Categoria.PUBLICO)){
								sumCategoriaPublica = (Integer) retorno[0];
							}
						}
					}
				}
				// [UC0085]Obter Endereco
				String enderecoFormatado = this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(helper.getIdCliente(), false);
				arquivoTxtLinha.append(Util.completaString(enderecoFormatado, 50));
				if(sumCategoriaResidencial != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(sumCategoriaResidencial.intValue(), "0", 3));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, ""));
				}
				if(sumCategoriaComercial != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(sumCategoriaComercial.intValue(), "0", 3));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, ""));
				}
				if(sumCategoriaIndustrial != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(sumCategoriaIndustrial.intValue(), "0", 3));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, ""));
				}
				if(sumCategoriaPublica != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(sumCategoriaPublica.intValue(), "0", 3));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, ""));
				}
				if(helper.getNumeroHidormetro() != null){
					arquivoTxtLinha.append(Util.completaString(helper.getNumeroHidormetro(), 10));
				}else{
					arquivoTxtLinha.append(Util.completaString("", 10));
				}
				Collection collClienteResponsavel = null;
				idClienteResponsavel = null;
				try{
					collClienteResponsavel = repositorioImovel.pesquisarClienteResponsavelArquivoAgenciaVirtualWeb(helper.getIdImovel());
					if(collClienteResponsavel != null && !collClienteResponsavel.isEmpty()){
						Iterator it = collClienteResponsavel.iterator();
						idClienteResponsavel = (Integer) it.next();
					}
				}catch(ErroRepositorioException e){
					e.printStackTrace();
				}
				if(idClienteResponsavel != null){
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(idClienteResponsavel, "0", 8));
				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(8, ""));
				}
				FiltroDebitoAutomatico filtroDebitoAutomatico = new FiltroDebitoAutomatico();
				filtroDebitoAutomatico.adicionarParametro(new ParametroSimples(FiltroDebitoAutomatico.IMOVEL_ID, helper.getIdImovel()));
				filtroDebitoAutomatico.adicionarParametro(new ParametroNulo(FiltroDebitoAutomatico.DATA_EXCLUSAO));
				Collection collDebitoAutomatico = getControladorUtil().pesquisar(filtroDebitoAutomatico, DebitoAutomatico.class.getName());
				if(!Util.isVazioOrNulo(collDebitoAutomatico)){
					arquivoTxtLinha.append("S");
				}else{
					arquivoTxtLinha.append("N");
				}
				arquivoTxtLinha.append(System.getProperty("line.separator"));
			}
			// arquivoTxtLinha.append("Quantidade de registro: " + collImoveis.size());
			// arquivoTxtLinha.append("tempo total escrever: " + Util.calcularDiferencaTempo(a));
			Usuario usuario = new Usuario();
			usuario.setId(Usuario.ID_USUARIO_ADM_SISTEMA);
			this.gravarArquivoAgenciaVirtualZip(arquivoTxtLinha, "relatorioAgenciaVirtualImoveis", idSetorComercial);

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

	}

	/**
	 * Este caso de uso permite Gerar Arquivo Agencia Virtual WEB
	 * [UC3054] Gerar Arquivo Agencia Virtual WEB
	 * Gera os dados do arquivo de debitos dos imoveis.
	 * 
	 * @autor Josenildo Neves
	 * @date 22/03/2012
	 * @throws ControladorException
	 */
	public void gerarDadosArquivoDebitosImoveisTxt(Integer idSetorComercial, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetorComercial);
		Integer imovelId = null;
		try{

			long dif = 0L, ini = 0L;
			long dif_imovel = 0L, ini_imovel = 0L;

			ini = System.currentTimeMillis();
			Collection<ImovelArquivoAgenciaVirtualWebHelper> collImoveis = repositorioImovel
							.pesquisarImovelArquivoAgenciaVirtualWebPorSetorComercial(idSetorComercial);
			dif = System.currentTimeMillis() - ini;
			LOGGER.debug("[" + Thread.currentThread().getId() + "]INICIO SETOR COMERCIAL: " + idSetorComercial + " TOTAL IMOVEIS = "
							+ collImoveis.size() + " TEMPO CONSULTA IMOVEIS: " + dif);

			// System.out.println("Total imóveis = " + collImoveis.size());

			// long dif = 0L, ini = 0L;
			StringBuilder arquivoTxtLinha = new StringBuilder();

			String anoMesInicialReferenciaDebito = "150001";
			String anoMesFinalReferenciaDebito = "999912";

			Date dtInicialVenciamento = Util.converteStringParaDate("01/01/1500");
			Date dtFinalVenciamento = Util.converteStringParaDate("31/12/9999");

			Long dias = Long.parseLong("0");

			// Calendar a = Calendar.getInstance();
			for(ImovelArquivoAgenciaVirtualWebHelper helper : collImoveis){

				ini_imovel = System.currentTimeMillis();
				imovelId = helper.getIdImovel();
				Collection<ContaValoresHelper> collContaValoresHelpers = this.obterColecaoContasParaArquivo(imovelId.toString(),
								dtInicialVenciamento, dtFinalVenciamento, anoMesInicialReferenciaDebito, anoMesFinalReferenciaDebito);
				dif_imovel = System.currentTimeMillis() - ini_imovel;
				LOGGER.debug("[" + Thread.currentThread().getId() + "] TEMPO CONSULTA CONTAS DOS IMOVEIS: COLLCONTAVALORESHELPERS -> "
								+ dif_imovel);

				for(ContaValoresHelper contaValoresHelper : collContaValoresHelpers){

					// FiltroConta filtroConta = new FiltroConta();
					// filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID,
					// contaValoresHelper.getConta().getId()));
					// filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LOCALIDADE);
					// filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LIGACAO_AGUA_SITUACAO);
					// filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LIGACAO_ESGOTO_SITUACAO);
					//
					// Collection collContas = this.getControladorUtil().pesquisar(filtroConta,
					// Conta.class.getName());
					//
					// Conta conta = (Conta) Util.retonarObjetoDeColecao(collContas);

					Conta conta = contaValoresHelper.getConta();

					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(helper.getIdImovel(), "0", 10));
					arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(conta.getReferencia(), "0", 6));

					if(conta.getDataVencimentoConta() != null){
						arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(Integer.parseInt(Util.formatarDataAAAAMMDD(conta
										.getDataVencimentoConta())), "0", 8));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(8, ""));
					}

					if(conta.getLigacaoAguaSituacao() != null){
						arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(conta.getLigacaoAguaSituacao().getId(), "0", 1));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(1, ""));
					}

					if(conta.getLigacaoEsgotoSituacao() != null){
						arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(conta.getLigacaoEsgotoSituacao().getId(), "0", 1));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(1, ""));
					}

					// ini = System.currentTimeMillis();
					// FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
					// filtroMedicaoHistorico
					// .adicionarParametro(new
					// ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID,
					// helper.getIdImovel()));
					// filtroMedicaoHistorico.adicionarParametro(new
					// ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO, conta
					// .getReferencia()));
					//
					// Collection collMedicaoHistorico =
					// getControladorUtil().pesquisar(filtroMedicaoHistorico,
					// MedicaoHistorico.class.getName());
					MedicaoHistorico medicaoHistorico = getControladorMicromedicao().pesquisarMedicaoHistorico(helper.getIdImovel(),
									conta.getReferencia(), MedicaoTipo.LIGACAO_AGUA);

					// dif = System.currentTimeMillis() - ini;
					// System.out.println("5.2 - ############################## -> " + dif);

					dias = Long.parseLong("0");

					// MedicaoHistorico medicaoHistorico = null;

					if(medicaoHistorico != null){

						// medicaoHistorico = (MedicaoHistorico)
						// Util.retonarObjetoDeColecao(collMedicaoHistorico);

						if(medicaoHistorico.getDataLeituraAtualFaturamento() != null
										&& medicaoHistorico.getDataLeituraAnteriorFaturamento() != null){
							dias = Util.diferencaDias(medicaoHistorico.getDataLeituraAnteriorFaturamento(), medicaoHistorico
											.getDataLeituraAtualFaturamento());
						}

						arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(dias.intValue(), "0", 2));

						if(medicaoHistorico.getLeituraAtualFaturamento() != null){
							arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(medicaoHistorico.getLeituraAtualFaturamento(), "0",
											6));
						}else{
							arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, ""));
						}

						if(medicaoHistorico.getNumeroConsumoMes() != null){
							arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(medicaoHistorico.getNumeroConsumoMes(), "0", 6));
						}else{
							arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, ""));
						}

					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, ""));
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, ""));
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, ""));
					}

					if(conta.getValorAgua() != null){
						arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(Integer.parseInt(Util.formatarBigDecimalParaString(
										conta.getValorAgua(), 2)), "0", 11));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, ""));
					}
					if(conta.getValorEsgoto() != null){
						arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(Integer.parseInt(Util.formatarBigDecimalParaString(
										conta.getValorEsgoto(), 2)), "0", 11));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, ""));
					}
					if(conta.getValorEsgoto() != null){
						arquivoTxtLinha.append(Util.completarNumeroComValorEsquerda(Integer.parseInt(Util.formatarBigDecimalParaString(
										conta.getDebitos(), 2)), "0", 11));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, ""));
					}

					String codigoBarrasContaFormatado = this.obterCodigoBarrasFormatadoConta(conta);

					arquivoTxtLinha.append(Util.completaString(codigoBarrasContaFormatado, 52));

					if(dias.equals(99) || dias.equals(0)){

						arquivoTxtLinha.append(Util.completaString("", 10));
						arquivoTxtLinha.append(Util.completaString("", 10));

					}else{

						if(medicaoHistorico != null && medicaoHistorico.getDataLeituraAtualFaturamento() != null){

							Date dataLeituraAtualFaturamento = Util.subtrairNumeroDiasDeUmaData(medicaoHistorico
											.getDataLeituraAtualFaturamento(), Integer.parseInt("30"));
							arquivoTxtLinha.append(Util.formatarData(dataLeituraAtualFaturamento));
							arquivoTxtLinha.append(Util.formatarData(medicaoHistorico.getDataLeituraAtualFaturamento()));

						}else{
							arquivoTxtLinha.append(Util.completaString("", 10));
							arquivoTxtLinha.append(Util.completaString("", 10));
						}
					}
					arquivoTxtLinha.append(System.getProperty("line.separator"));
				}
			}

			dif = System.currentTimeMillis() - ini;
			LOGGER.debug("[" + Thread.currentThread().getId() + "] FIM SETOR COMERCIAL: " + idSetorComercial + " TOTAL IMOVEIS = "
							+ collImoveis.size() + " TEMPO consulta imoveis: " + dif);

			Usuario usuario = new Usuario();
			usuario.setId(Usuario.ID_USUARIO_ADM_SISTEMA);

			// arquivoTxtLinha.append("tempo total escrever: " + Util.calcularDiferencaTempo(a));
			this.gravarArquivoAgenciaVirtualZip(arquivoTxtLinha, "relatorioAgenciaVirtualDebitos", idSetorComercial);

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			LOGGER.error("ERRO PROCESSANDO SETOR COMERCIAL / IMOVEL: " + idSetorComercial + "/" + imovelId);
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

		// this.enviarArquivoAgenciaVirtualDebitosBatch(arquivoTxtLinha,
		// "relatorioAgenciaVirtualDebitos.txt", usuario);

	}

	/**
	 * @param conta
	 * @return
	 * @throws ControladorException
	 */
	private String obterCodigoBarrasFormatadoConta(Conta conta) throws ControladorException{

		BigDecimal valorAgua = conta.getValorAgua();
		BigDecimal valorEsgoto = conta.getValorEsgoto();
		BigDecimal valorDebitos = conta.getDebitos();

		if(valorAgua == null){
			valorAgua = BigDecimal.ZERO;
		}

		if(valorEsgoto == null){
			valorEsgoto = BigDecimal.ZERO;
		}

		if(valorDebitos == null){
			valorDebitos = BigDecimal.ZERO;
		}

		BigDecimal valorCadigoBarras = Util.somaBigDecimal(valorAgua, valorEsgoto);
		valorCadigoBarras = Util.somaBigDecimal(valorCadigoBarras, valorDebitos);

		String codigoBarrasConta = this.getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(3, valorCadigoBarras,
						conta.getLocalidade().getId(), conta.getImovel().getId(), conta.getReferencia() + "",
						((int) conta.getDigitoVerificadorConta()), null, null, null, 1, null, null, null, null, null, null);

		// Para atender à solicitação do cliente é ncessário incluir nas posições 34-41
		// (fica 36-43 contando com os digitos verificadores) do código de barras do
		// documento CONTA a matrícula do imóvel. Observação: As posições foram
		// referenciadas considerando os 44 caracteres do código de barras, sem os
		// dígitos verificadores. A matrícula ficaria nas posições destacadas no
		// exemplo:
		// 11111111111 D 22222222222 D 33333333333 D 44444444444 D
		// codigoBarrasConta = Util.inserirMatriculaImovelCodigoBarra(conta.getImovel().getId(),
		// codigoBarrasConta);

		String codigoBarrasContaFormatado = Util.formatarCodigoBarraComEspacos(codigoBarrasConta);
		return codigoBarrasContaFormatado;
	}

	/**
	 * @param collImoveis
	 * @param anoBase
	 * @param dtVencimentoInicial
	 * @param dtVencimentoFinal
	 * @param mesReferenciaInicial
	 * @param mesReferenciaFinal
	 * @param arquivoTxtLinha
	 * @throws ControladorException
	 */
	public Collection<ContaValoresHelper> obterColecaoContasParaArquivo(String idImovel, Date dtVencimentoInicial, Date dtVencimentoFinal,
					String anoMesReferenciaInicial, String anoMesReferenciaFinal) throws ControladorException{

		long dif = 0L, ini = 0L;
		ini = System.currentTimeMillis();

		// ObterDebitoImovelOuClienteHelper debitoImovelOuClienteHelper =
		// getControladorCobranca().obterDebitoImovelOuClienteContas(1,
		// idImovel, null, null, anoMesReferenciaInicial, anoMesReferenciaFinal,
		// dtVencimentoInicial, dtVencimentoFinal, 2, 1,
		// 2, true, null, null);

		ObterDebitoImovelOuClienteHelper debitoImovelOuClienteHelper = getControladorCobranca().obterDebitoImovelContasAgenciaVirtual(1,
						idImovel, anoMesReferenciaInicial, anoMesReferenciaFinal, dtVencimentoInicial, dtVencimentoFinal);

		dif = System.currentTimeMillis() - ini;

		Collection<ContaValoresHelper> collContaValoresHelpers = debitoImovelOuClienteHelper.getColecaoContasValores();

		LOGGER.debug("[" + Thread.currentThread().getId() + "] OBTERCOLECAOCONTASPARAARQUIVO -------->  IMOVEL = " + idImovel
						+ "   QTD CONTAS =" + collContaValoresHelpers.size() + " TEMPO CONSULTA DEBITOS: " + dif);

		Collection<ContaValoresHelper> novaCollContaValoresHelpers = new ArrayList<ContaValoresHelper>();

		if(!Util.isVazioOrNulo(collContaValoresHelpers)){

			for(ContaValoresHelper contaValoresHelper : collContaValoresHelpers){

				try{

					if(repositorioCobranca.verificarDebitoImovelValidoAgenciaVirtual(contaValoresHelper.getConta().getId())){

						// contaValoresHelper.getConta().getId());

						novaCollContaValoresHelpers.add(contaValoresHelper);

					}
					// else{
					// System.out.println("NÃO TEM DEBITO ############################## " +
					// contaValoresHelper.getConta().getId());
					// }

				}catch(ErroRepositorioException ex){
					ex.printStackTrace();
					throw new ControladorException("erro.sistema", ex);
				}

			}

			// dif = System.currentTimeMillis() - ini;
			// System.out.println("5.1.2 - ############################## -> " + dif);
		}

		return novaCollContaValoresHelpers;
	}

	// /**
	// * @param idImovel
	// * @param idCategoria
	// * @return
	// * @throws ControladorException
	// */
	// private Short obterSomaQuantidadeEconomias(Imovel imovel, Categoria categoria) throws
	// ControladorException{
	//
	// Short sumQtEconomias = null;
	//
	// try{
	//
	// sumQtEconomias = repositorioImovel.pesquisarObterQuantidadeEconomias(imovel, categoria);
	//
	// }catch(ErroRepositorioException e){
	//
	// throw new ControladorException(e, "Erro ao obeter a soma da quantidade de economias");
	//
	// }
	//
	// return sumQtEconomias;
	//
	// }

	// private void enviarArquivoAgenciaVirtualImoveisBatch(StringBuffer sb, String nomeArquivo,
	// Usuario usuario) throws ControladorException{
	//
	// RelatorioArquivoAgenciaVirtualImoveis relatorio = new
	// RelatorioArquivoAgenciaVirtualImoveis(usuario);
	// relatorio.addParametro("arquivoTexto", sb);
	// relatorio.addParametro("nomeArquivo", nomeArquivo);
	// relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
	// this.getControladorBatch().iniciarProcessoRelatorio(relatorio);
	//
	// }
	//
	// private void enviarArquivoAgenciaVirtualDebitosBatch(StringBuffer sb, String nomeArquivo,
	// Usuario usuario) throws ControladorException{
	//
	// RelatorioArquivoAgenciaVirtualDebitos relatorio = new
	// RelatorioArquivoAgenciaVirtualDebitos(usuario);
	// relatorio.addParametro("arquivoTexto", sb);
	// relatorio.addParametro("nomeArquivo", nomeArquivo);
	// relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
	// this.getControladorBatch().iniciarProcessoRelatorio(relatorio);
	//
	// }
	//
	// private void enviarArquivoAgenciaVirtualLogradourosBatch(StringBuffer sb, String nomeArquivo,
	// Usuario usuario)
	// throws ControladorException{
	//
	// RelatorioArquivoAgenciaVirtualLogradouro relatorio = new
	// RelatorioArquivoAgenciaVirtualLogradouro(usuario);
	// relatorio.addParametro("arquivoTexto", sb);
	// relatorio.addParametro("nomeArquivo", nomeArquivo);
	// relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
	// this.getControladorBatch().iniciarProcessoRelatorio(relatorio);
	//
	// }
	//
	// private void enviarArquivoAgenciaVirtualQuitacaoDebitosBatch(StringBuilder sb, String
	// nomeArquivo, Usuario usuario)
	// throws ControladorException{
	//
	// RelatorioArquivoAgenciaVirtualQuitacaoDebitos relatorio = new
	// RelatorioArquivoAgenciaVirtualQuitacaoDebitos(usuario);
	// relatorio.addParametro("arquivoTexto", sb);
	// relatorio.addParametro("nomeArquivo", nomeArquivo);
	// relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
	// this.getControladorBatch().iniciarProcessoRelatorio(relatorio);
	//
	// }

	private void gravarArquivoAgenciaVirtualZip(StringBuilder boletimCadastroTxt, String nomeZip, Integer idSetorComercial)
					throws ControladorException{

		System.out.println("******************** INICIO GERAÇÃO ARQUIVO: " + nomeZip.toUpperCase() + "********************");

		try{
			if(boletimCadastroTxt != null && boletimCadastroTxt.length() != 0){

				// boletimCadastroTxt.append("\u0004");
				// criar o arquivo zip

				String dataFolder = System.getProperty("file.separator");

				// File mkdir = new File("C:" + dataFolder + "GSAN" + dataFolder +
				// "ARQUIVOSAGENCIAVIRTUAL");
				File mkdir = new File(FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS + "ARQUIVOSAGENCIAVIRTUAL");

				mkdir.mkdirs();

				File compactado = null;
				if(Util.isVazioOuBranco(idSetorComercial)){
					compactado = new File(mkdir.getPath() + dataFolder + nomeZip.toUpperCase() + ".zip"); // nomeZip
				}else{
					compactado = new File(mkdir.getPath() + dataFolder + nomeZip.toUpperCase() + idSetorComercial + ".zip"); // nomeZip
				}
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

				File leitura = null;
				if(Util.isVazioOuBranco(idSetorComercial)){
					// leitura = new File("C:" + dataFolder + "GSAN" + dataFolder +
					// nomeZip.toUpperCase() + ".txt");
					leitura = new File(FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS + nomeZip.toUpperCase() + ".txt");
				}else{
					// leitura = new File("C:" + dataFolder + "GSAN" + dataFolder +
					// nomeZip.toUpperCase() + idSetorComercial + ".txt");
					leitura = new File(FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS + nomeZip.toUpperCase() + idSetorComercial + ".txt");
				}
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leitura.getAbsolutePath())));
				out.write(boletimCadastroTxt.toString());
				out.flush();
				out.close();
				ZipUtil.adicionarArquivo(zos, leitura);

				// close the stream
				zos.close();
				leitura.delete();
			}

			System.out.println("******************** FIM GERAÇÃO ARQUIVO: " + nomeZip.toUpperCase() + " ********************");

		}catch(IOException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}catch(Exception e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}
	}

	public void concatenarTodosArquivosAgenciaVirtualZip(Integer idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{

			// String dataFolder = System.getProperty("file.separator");

			// File mkdir = new File("C:" + dataFolder + "GSAN" + dataFolder +
			// "ARQUIVOSAGENCIAVIRTUAL");

			File mkdir = new File(FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS + "ARQUIVOSAGENCIAVIRTUAL");

			Util.obtainFileListByPatterns(mkdir, RELATORIO_AGENCIA_VIRTUAL_IMOVEIS);

			Util.obtainFileListByPatterns(mkdir, RELATORIO_AGENCIA_VIRTUAL_DEBITOS);

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

	}

	/**
	 * Consulta os motivos de exclusão do programa Água Para Todos
	 * 
	 * @author Luciano Galvão
	 * @date 20/03/2012
	 * @throws ControladorException
	 */
	public List consultarAguaParaTodosMotivosExclusao() throws ControladorException{

		try{
			return repositorioCadastro.consultarAguaParaTodosMotivosExclusao();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Habilita um imóvel no programa água para todos
	 * 
	 * @author Luciano Galvão
	 * @date 21/03/2012
	 * @param imovelAguaParaTodos
	 * @param imovel
	 * @param usuario
	 * @throws ControladorException
	 */
	public void habilitarImovelAguaParaTodos(ImovelAguaParaTodos imovelAguaParaTodos, Imovel imovel, Usuario usuario)
					throws ControladorException{

		imovelAguaParaTodos.setUltimaAlteracao(new Date());
		imovelAguaParaTodos.setDataHabilitacao(new Date());
		imovelAguaParaTodos.setCodigoSituacao(ImovelAguaParaTodos.HABILITADO);

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Integer.valueOf(imovel.getId())));

		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");

		Imovel imovelAnoMesRef = (Imovel) getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()).iterator().next();

		FiltroFaturamentoAtividadeCronograma filtroFatAtvCron = new FiltroFaturamentoAtividadeCronograma();
		filtroFatAtvCron.adicionarParametro(new ParametroSimples(FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID,
						FaturamentoAtividade.FATURAR_GRUPO));
		filtroFatAtvCron.adicionarParametro(new ParametroSimples(
						FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ANO_MES_REFERENCIA, sistemaParametro
										.getAnoMesFaturamento()));
		filtroFatAtvCron.adicionarParametro(new ParametroSimples(
						FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_FATURAMENTO_GRUPO_ID, imovelAnoMesRef
										.getRota().getFaturamentoGrupo().getId()));

		Collection<FaturamentoAtividadeCronograma> colecaoFaturamentoAtividadeCronograma = getControladorUtil().pesquisar(filtroFatAtvCron,
						FaturamentoAtividadeCronograma.class.getName());

		FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = null;
		if(colecaoFaturamentoAtividadeCronograma != null && !colecaoFaturamentoAtividadeCronograma.isEmpty()){
			faturamentoAtividadeCronograma = colecaoFaturamentoAtividadeCronograma.iterator().next();
		}

		Integer anoMesInicial = sistemaParametro.getAnoMesFaturamento();
		if(faturamentoAtividadeCronograma != null){
			if(faturamentoAtividadeCronograma.getDataRealizacao() != null){
				String ano = sistemaParametro.getAnoMesFaturamento().toString().substring(0, 4);
				String mes = sistemaParametro.getAnoMesFaturamento().toString().substring(4);
				anoMesInicial = sistemaParametro.getAnoMesFaturamento() + 1;
				if(mes.equals("12")){
					anoMesInicial = new Integer(ano) + 1;
					String aux = anoMesInicial.toString() + "01";
					anoMesInicial = new Integer(aux);
				}
			}
		}

		imovelAguaParaTodos.setAnoMesReferenciaInicial(anoMesInicial);
		// Alterado para colocar o código da tarifa e a data da Validade da mesma de acordo com
		// a tabela de Parâmetros.
		if(sistemaParametro.getCodTarifaAguaParaTodos() != null){
			FiltroConsumoTarifa filtroConsumoTarifa = null;
			filtroConsumoTarifa = new FiltroConsumoTarifa();
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, sistemaParametro
							.getCodTarifaAguaParaTodos()));
			Collection colecaoConsumoTarifa = getControladorUtil().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());
			Iterator iter;

			if(colecaoConsumoTarifa != null){
				iter = colecaoConsumoTarifa.iterator();

				if(iter.hasNext()){
					ConsumoTarifa consumoTarifa = (ConsumoTarifa) iter.next();
					imovel.setConsumoTarifaTemporaria(consumoTarifa);
				}
			}
		}
		if(sistemaParametro.getNumeroMaxDiasVigenciaTarifaAguaParaTodos() != null){
			imovel.setDataValidadeTarifaTemporaria(Util.adicionarNumeroDiasDeUmaData(new Date(), sistemaParametro
							.getNumeroMaxDiasVigenciaTarifaAguaParaTodos().intValue()));
		}
		imovel.setImovelAguaParaTodos(imovelAguaParaTodos);
		atualizarImovelAguaParaTodos(imovelAguaParaTodos, imovel, usuario);
	}

	public List<Integer> consultarSetoresPorFaturamentoAtividadeCronograma(Integer idFaturamentoAtividadeCronograma)
					throws ControladorException{

		List<Integer> retorno = null;
		try{
			retorno = repositorioImovel.consultarSetoresPorFaturamentoAtividadeCronograma(idFaturamentoAtividadeCronograma);
		}catch(ErroRepositorioException e){
			throw new ControladorException("Erro ao realizar consulta", e);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Relatório Débito por Reponsável
	 * 
	 * @author Anderson Italo
	 * @date 25/09/2012
	 */
	public Collection<Object[]> pesquisarRelatorioDebitoPorReponsavel(FiltroRelatorioDebitoPorResponsavelHelper filtro)
					throws ControladorException{

		Collection<Object[]> retorno = null;
		try{

			retorno = repositorioCadastro.pesquisarRelatorioDebitoPorReponsavel(filtro);
		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Relatório Débito por Reponsável
	 * 
	 * @author Anderson Italo
	 * @date 25/09/2012
	 */
	public Integer pesquisarTotalRegistrosRelatorioDebitoPorReponsavel(FiltroRelatorioDebitoPorResponsavelHelper filtro)
					throws ControladorException{

		Integer retorno = null;
		try{

			retorno = repositorioCadastro.pesquisarTotalRegistrosRelatorioDebitoPorReponsavel(filtro);

			if(retorno.intValue() == 0){

				throw new ControladorException("atencao.relatorio.vazio");
			}

		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	protected ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
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

	public void gerarProvisaoReceita(int idFuncionalidadeIniciada, Integer idSetorComercial) throws ControladorException{

		int idUnidadeIniciada = 0;
		try{

			// Registrar o início do processamento da unidade de processamento do batch
			idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
							UnidadeProcessamento.SETOR_COMERCIAL, idSetorComercial);

			System.out.println("##### -> INÍCIO GERAR PROVISÃO RECEITA SETOR: " + idSetorComercial.toString());

			Integer anoMesProvisao = Util.obterInteger(ParametroCadastro.P_ANO_MES_REFERENCIA_PROVISAO_RECEITA.executar().toString());
			LigacaoTipo ligacaoTipoAgua = new LigacaoTipo();
			ligacaoTipoAgua.setId(LigacaoTipo.LIGACAO_AGUA);
			LigacaoTipo ligacaoTipoEsgoto = new LigacaoTipo();
			ligacaoTipoEsgoto.setId(LigacaoTipo.LIGACAO_ESGOTO);
			LigacaoAgua ligacaoAgua = null;
			boolean medido = false;
			Integer diferencaDias = 0;
			MedicaoHistorico medicaoHistorico = null;
			BigDecimal consumoEstimadoDia = BigDecimal.ZERO;
			Integer consumoAFaturar = 0;
			Collection colecaoCalcularValoresAguaEsgotoHelper = null;
			Collection colecaoCategorias = null;
			BigDecimal valorAguaMinimoDiaCategoria = BigDecimal.ZERO;
			BigDecimal valorEsgotoMinimoDiaCategoria = BigDecimal.ZERO;
			BigDecimal valorConsumoMinimoDiaCategoria = BigDecimal.ZERO;
			Collection colecaoProvisao = null;
			ProvisaoReceita provisaoReceita = null;
			Date dataLeituraMesProvisao = null;
			Integer consumoMedio = null;
			ConsumoHistorico consumoHistoricoEsgoto = null;
			ConsumoHistorico consumoHistoricoAgua = null;
			Integer idImovelComProvisaoExistente = null;

			Collection<Imovel> colecaoImoveis = repositorioImovel.pesquisarImoveisGerarProvisaoReceita(idSetorComercial);
			colecaoProvisao = new ArrayList<ProvisaoReceita>();

			for(Imovel imovel : colecaoImoveis){

				idImovelComProvisaoExistente = repositorioFaturamento.verificaExistenciaProvisaoReceitaAnoMes(anoMesProvisao, imovel
								.getId());

				// Caso já tenha provisão gerada passa para o próximo imóvel
				if(idImovelComProvisaoExistente != null && idImovelComProvisaoExistente.intValue() > 0){

					continue;
				}

				Object[] dadosConta = repositorioFaturamento.verificaExistenciaContaOuContaHistoricoGerarProvisaoReceita(imovel.getId(),
								anoMesProvisao);

				if(dadosConta != null && dadosConta.length > 0){

					ligacaoAgua = getControladorLigacaoAgua().pesquisarLigacaoAgua(imovel.getId());
					medido = false;

					if(((ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null) || (imovel
									.getHidrometroInstalacaoHistorico() != null))
									&& imovel.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)){

						medido = true;
					}

					if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null){

						medicaoHistorico = getControladorMicromedicao().pesquisarMedicaoHistoricoTipoAgua(imovel.getId(), anoMesProvisao);
					}else{

						medicaoHistorico = getControladorMicromedicao().pesquisarMedicaoHistoricoTipoPoco(imovel.getId(), anoMesProvisao);
					}

					consumoHistoricoAgua = getControladorMicromedicao().obterConsumoHistoricoCompleto(imovel, ligacaoTipoAgua,
									anoMesProvisao);
					consumoHistoricoEsgoto = getControladorMicromedicao().obterConsumoHistoricoCompleto(imovel, ligacaoTipoEsgoto,
									anoMesProvisao);
					Integer consumoAguaAFaturar = 0;
					Integer consumoEsgotoAFaturar = 0;

					// Caso não tenha consumo histórico passa para o próximo imóvel
					if(consumoHistoricoAgua == null && consumoHistoricoEsgoto == null){

						// Caso a conta nãqo tenha consumo de água e nem consumo de esgoto
						if((dadosConta[1] == null || dadosConta[1].toString().equals(ConstantesSistema.ZERO.toString()))
										&& (dadosConta[2] == null || dadosConta[2].toString().equals(ConstantesSistema.ZERO.toString()))){

							continue;
						}

					}

					// [UC0108] - Obter Quantidade de Economias por Categoria
					colecaoCategorias = this.getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);

					// [UC0105] - Obter Consumo Mínimo da Ligação
					int consumoMinimoLigacao = getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, null);

					// Determina a data de leitura anterior do faturamento
					Date dataLeituraAnteriorFaturamento = null;
					try{

						dataLeituraAnteriorFaturamento = (Date) repositorioFaturamento
										.pesquisarFaturamentoAtividadeCronogramaDataRealizacao(imovel.getRota().getFaturamentoGrupo()
														.getId(), FaturamentoAtividade.EFETUAR_LEITURA, Integer.valueOf(Util
														.subtrairMesDoAnoMes(anoMesProvisao, 1)));

					}catch(ErroRepositorioException ex){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", ex);
					}

					// Determina a data de leitura atual do faturamento
					Date dataLeituraAtualFaturamento = null;
					try{
						dataLeituraAtualFaturamento = (Date) repositorioFaturamento.pesquisarFaturamentoAtividadeCronogramaDataRealizacao(
										imovel.getRota().getFaturamentoGrupo().getId(), FaturamentoAtividade.EFETUAR_LEITURA,
										anoMesProvisao);

					}catch(ErroRepositorioException ex){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", ex);
					}

					if(medicaoHistorico != null){

						if(medicaoHistorico.getDataLeituraAnteriorFaturamento() != null){

							dataLeituraAnteriorFaturamento = medicaoHistorico.getDataLeituraAnteriorFaturamento();
						}

						if(medicaoHistorico.getDataLeituraAtualFaturamento() != null){

							dataLeituraAtualFaturamento = medicaoHistorico.getDataLeituraAtualFaturamento();
						}
					}

					FaturamentoAtivCronRota faturamentoAtivCronRota = getControladorFaturamento()
									.obterFaturamentoAtividadeCronogramaPorGrupoFaturamentoRota(
													FaturamentoAtividade.REGISTRAR_FATURAMENTO_IMEDIATO, anoMesProvisao,
													imovel.getRota().getFaturamentoGrupo(), imovel.getRota());

					if(dataLeituraAnteriorFaturamento == null || dataLeituraAtualFaturamento == null){

						Date periodoLeitura[] = getControladorFaturamento().gerarPeriodoLeituraFaturamento(dataLeituraAtualFaturamento,
										dataLeituraAnteriorFaturamento, faturamentoAtivCronRota,
										Integer.valueOf(Util.subtrairMesDoAnoMes(anoMesProvisao, 1)), anoMesProvisao);
						dataLeituraAnteriorFaturamento = periodoLeitura[0];
						dataLeituraAtualFaturamento = periodoLeitura[1];
					}

					if(medido){

						if(medicaoHistorico != null){

							diferencaDias = Util.obterDiferencaEntreDatasEmDias(medicaoHistorico.getDataLeituraAtualInformada(), Util
											.gerarDataApartirAnoMesRefencia(anoMesProvisao));
							dataLeituraMesProvisao = medicaoHistorico.getDataLeituraAtualInformada();
						}else{

							diferencaDias = Util.obterDiferencaEntreDatasEmDias(dataLeituraAtualFaturamento, Util
											.gerarDataApartirAnoMesRefencia(anoMesProvisao));
							dataLeituraMesProvisao = dataLeituraAtualFaturamento;
						}

						if(diferencaDias > 30){

							diferencaDias = 30;
						}

						if(consumoHistoricoAgua != null){

							consumoEstimadoDia = Util.dividirArredondando(new BigDecimal(consumoHistoricoAgua.getConsumoMedio()),
											new BigDecimal("30"), 4);
							consumoMedio = consumoHistoricoAgua.getConsumoMedio();
						}

						consumoAFaturar = consumoEstimadoDia.multiply(new BigDecimal(diferencaDias)).setScale(0, BigDecimal.ROUND_HALF_UP)
										.intValue();

					}else{

						diferencaDias = 15;

						if(consumoHistoricoAgua != null){

							if(consumoHistoricoAgua.getNumeroConsumoFaturadoMes() != null){

								consumoEstimadoDia = Util.dividirArredondando(new BigDecimal(consumoHistoricoAgua
												.getNumeroConsumoFaturadoMes()), new BigDecimal("30"), 4);
							}

							consumoMedio = consumoHistoricoAgua.getNumeroConsumoFaturadoMes();
						}else if(consumoHistoricoEsgoto != null){

							if(consumoHistoricoEsgoto.getNumeroConsumoFaturadoMes() != null){

								consumoEstimadoDia = Util.dividirArredondando(new BigDecimal(consumoHistoricoEsgoto
												.getNumeroConsumoFaturadoMes()), new BigDecimal("30"), 4);
							}

							consumoMedio = consumoHistoricoEsgoto.getNumeroConsumoFaturadoMes();
						}else{

							if(dadosConta[1] != null){

								consumoEstimadoDia = Util.dividirArredondando(new BigDecimal(dadosConta[1].toString()),
												new BigDecimal("30"), 4);
							}
							if(dadosConta[2] != null){

								consumoEstimadoDia = Util.dividirArredondando(new BigDecimal(dadosConta[2].toString()),
												new BigDecimal("30"), 4);
							}
						}

						consumoAFaturar = consumoEstimadoDia.multiply(new BigDecimal(diferencaDias)).setScale(0, BigDecimal.ROUND_HALF_UP)
										.intValue();
					}

					// Seta valores iniciais para os indicadores de faturamento de água e esgoto.
					Short indicadorFaturamentoAgua = ConstantesSistema.NAO;
					Short indicadorFaturamentoEsgoto = ConstantesSistema.NAO;

					if(consumoHistoricoAgua != null){
						if((imovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao().intValue() == LigacaoAguaSituacao.FATURAMENTO_ATIVO
										.intValue())
										|| (imovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao().intValue() == LigacaoAguaSituacao.NAO_FATURAVEL
														.intValue()
														&& consumoHistoricoAgua.getNumeroConsumoFaturadoMes() != null && consumoHistoricoAgua
														.getNumeroConsumoFaturadoMes().intValue() > 0)){

							indicadorFaturamentoAgua = Short.valueOf("1");

							if(consumoHistoricoAgua.getIndicadorFaturamento() != null){

								indicadorFaturamentoAgua = consumoHistoricoAgua.getIndicadorFaturamento();
							}

							if(consumoHistoricoAgua.getNumeroConsumoFaturadoMes() != null){

								consumoAguaAFaturar = consumoAFaturar;
							}
						}
					}

					BigDecimal percentualEsgoto = BigDecimal.ZERO;

					// Caso o imóvel seja ligado de esgoto
					if(imovel.getLigacaoEsgoto() != null
									&& imovel.getLigacaoEsgotoSituacao() != null
									&& (imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIGADO.intValue() || (imovel
													.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA) && imovel
													.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)))){

						// Recupera o percentual de esgoto.
						percentualEsgoto = getControladorFaturamento().obterPercentualLigacaoEsgotoImovel(imovel.getId());
					}

					if(consumoHistoricoEsgoto != null
									&& (imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIGADO.intValue() || (imovel
													.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA) && imovel
													.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)))){

						if(consumoHistoricoEsgoto.getIndicadorFaturamento() != null){

							indicadorFaturamentoEsgoto = consumoHistoricoEsgoto.getIndicadorFaturamento();
						}

						if(consumoHistoricoEsgoto.getNumeroConsumoFaturadoMes() != null){

							consumoEsgotoAFaturar = consumoAFaturar;
						}
					}

					Short pVerificarConsumoLigacaoCortada = Short.valueOf((String) ParametroFaturamento.P_VERIFICAR_CONSUMO_LIGACAO_CORTADA
									.executar());

					// Caso o imóvel seja (ligado) ou (cortado com ou sem consumo de água)
					if(imovel.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)
									|| (imovel.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.CORTADO) && (pVerificarConsumoLigacaoCortada
													.equals(ConstantesSistema.NAO) || (pVerificarConsumoLigacaoCortada
													.equals(ConstantesSistema.SIM)
													&& consumoHistoricoAgua != null
													&& consumoHistoricoAgua.getNumeroConsumoFaturadoMes() != null && consumoHistoricoAgua
													.getNumeroConsumoFaturadoMes().intValue() != 0)))){

						if(consumoHistoricoAgua != null && consumoHistoricoAgua.getIndicadorFaturamento() != null){

							indicadorFaturamentoAgua = consumoHistoricoAgua.getIndicadorFaturamento();
						}else{

							indicadorFaturamentoAgua = ConstantesSistema.NAO;
						}
					}else{

						indicadorFaturamentoAgua = ConstantesSistema.NAO;
					}

					// Caso o imóvel seja ligado de esgoto:
					if(indicadorFaturamentoEsgoto != null && indicadorFaturamentoEsgoto.equals(ConstantesSistema.SIM)){

						Short pEsgotoSuprimido = Short.valueOf((String) ParametroFaturamento.P_ISENCAO_ESGOTO_SUPRIMIDO_CORTADO_SEM_CONSUMO
										.executar());

						// Caso o parâmetro de isenção de esgoto para suprimido esteja
						// ativado
						// (PASI_VLPARAMETRO = 1 na tabela PARAMETRO_SISTEMA para
						// PASI_DSPARAMETRO
						if(pEsgotoSuprimido.equals(ConstantesSistema.SIM)){

							boolean imovelSuprimidoSemConsumo = imovel.getLigacaoAguaSituacao().getId().equals(
											LigacaoAguaSituacao.SUPRIMIDO)
											&& consumoAguaAFaturar.intValue() == 0;

							// Imóvel cortardo sem consumo e com o parâmetro de verificar
							// consumo ativo
							boolean imovelCortadoSemConsumoComVerificacao = imovel.getLigacaoAguaSituacao().getId().equals(
											LigacaoAguaSituacao.CORTADO)
											&& pVerificarConsumoLigacaoCortada.equals(ConstantesSistema.SIM)
											&& consumoAguaAFaturar.intValue() == 0;

							if(imovelSuprimidoSemConsumo || imovelCortadoSemConsumoComVerificacao){

								indicadorFaturamentoEsgoto = ConstantesSistema.NAO;

							}else{

								// Caso contrário, atribuir CSHI_ICFATURAMENTO da
								// tabela
								// CONSUMO_HISTORICO com IMOV_ID=IMOV_ID da tabela IMOVEL,
								// LGTP_ID com o valor
								// correspondente a ligação de esgoto e CSHI_AMFATURAMENTO=Ano
								// e mês de
								// referência.

								if(consumoHistoricoEsgoto.getIndicadorFaturamento() != null){

									indicadorFaturamentoEsgoto = consumoHistoricoEsgoto.getIndicadorFaturamento();
								}
							}
						}else{

							// Caso contrário, ou seja, caso o parâmetro de isenção de esgoto
							// para suprimido
							// esteja desativado (PASI_VLPARAMETRO = 2 na tabela
							// PARAMETRO_SISTEMA para
							// PASI_DSPARAMETRO = 
							// P_ISENCAO_ESGOTO_SUPRIMIDO_CORTADO_SEM_CONSUMO),
							// atribuir CSHI_ICFATURAMENTO da tabela CONSUMO_HISTORICO com
							// IMOV_ID=IMOV_ID da
							// tabela IMOVEL, LGTP_ID com o valor correspondente a ligação de
							// esgoto e
							// CSHI_AMFATURAMENTO=Ano e mês de referência,

							indicadorFaturamentoEsgoto = consumoHistoricoEsgoto.getIndicadorFaturamento();
						}
					}else{

						// Caso contrário, ou seja, caso o imóvel não seja ligado de
						// esgoto, atribuir o
						// valor um (2).
						indicadorFaturamentoEsgoto = ConstantesSistema.NAO;

					}

					// Tarifa para o imóvel (Caso exista tarifa temporária para o imóvel
					// (CSTF_IDTEMPORARIA com o valor diferente de nulo na tabela IMOVEL)
					// e a tarifa temporária esteja vigente (IMOV_DTVALIDADETARIFATEMP maior ou
					// igual à data
					// corrente), atribuir CSTF_IDTEMPORARIA;
					// Caso contrário, atribuir CSTF_ID da tabela IMOVEL).
					ConsumoTarifa consumoTarifaConta = null;

					if(imovel.getConsumoTarifaTemporaria() != null && imovel.getDataValidadeTarifaTemporaria() != null
									&& Util.compararData(imovel.getDataValidadeTarifaTemporaria(), new Date()) >= 0){

						consumoTarifaConta = imovel.getConsumoTarifaTemporaria();

						// [FS0011] E [FS0014]
						consumoTarifaConta = getControladorFaturamento().verificarPerdaBeneficioDaTarifaSocialParaFaturamento(
										consumoAguaAFaturar, consumoMinimoLigacao, imovel);

					}else{

						// Obtém a tarifa do imóvel
						consumoTarifaConta = imovel.getConsumoTarifa();
					}

					// [UC0120] - Calcular Valores de Água e/ou Esgoto
					colecaoCalcularValoresAguaEsgotoHelper = getControladorFaturamento().calcularValoresAguaEsgoto(anoMesProvisao,
									imovel.getLigacaoAguaSituacao().getId(), imovel.getLigacaoEsgotoSituacao().getId(),
									indicadorFaturamentoAgua, indicadorFaturamentoEsgoto, colecaoCategorias, consumoAguaAFaturar,
									consumoEsgotoAFaturar, consumoMinimoLigacao, dataLeituraAnteriorFaturamento,
									dataLeituraAtualFaturamento, percentualEsgoto, consumoTarifaConta.getId(), imovel.getId());

					if(consumoAFaturar.intValue() <= consumoMinimoLigacao){

						for(Iterator iteratorColecaoCalcularValoresAguaEsgotoHelper = colecaoCalcularValoresAguaEsgotoHelper.iterator(); iteratorColecaoCalcularValoresAguaEsgotoHelper
										.hasNext();){

							CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) iteratorColecaoCalcularValoresAguaEsgotoHelper
											.next();
							valorAguaMinimoDiaCategoria = BigDecimal.ZERO;
							valorEsgotoMinimoDiaCategoria = BigDecimal.ZERO;
							valorConsumoMinimoDiaCategoria = BigDecimal.ZERO;

							if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){

								valorAguaMinimoDiaCategoria = Util.dividirArredondando(calcularValoresAguaEsgotoHelper
												.getValorFaturadoAguaCategoria(), new BigDecimal("30"), 4);

								valorConsumoMinimoDiaCategoria = valorConsumoMinimoDiaCategoria.add(valorAguaMinimoDiaCategoria);
							}

							if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){
								valorEsgotoMinimoDiaCategoria = Util.dividirArredondando(calcularValoresAguaEsgotoHelper
												.getValorFaturadoEsgotoCategoria(), new BigDecimal("30"), 4);

								valorConsumoMinimoDiaCategoria = valorConsumoMinimoDiaCategoria.add(valorEsgotoMinimoDiaCategoria);
							}

							calcularValoresAguaEsgotoHelper.setValorFaturadoAguaCategoria(valorAguaMinimoDiaCategoria.multiply(
											new BigDecimal(diferencaDias)).setScale(2, BigDecimal.ROUND_HALF_UP));

							calcularValoresAguaEsgotoHelper.setValorFaturadoEsgotoCategoria(valorEsgotoMinimoDiaCategoria.multiply(
											new BigDecimal(diferencaDias)).setScale(2, BigDecimal.ROUND_HALF_UP));

							if(consumoAFaturar.intValue() == 0){

								if(calcularValoresAguaEsgotoHelper.getConsumoFaturadoAguaCategoria() != null
												&& calcularValoresAguaEsgotoHelper.getConsumoFaturadoAguaCategoria().intValue() > 0){

									consumoAFaturar = calcularValoresAguaEsgotoHelper.getConsumoFaturadoAguaCategoria();
								}else if(calcularValoresAguaEsgotoHelper.getConsumoFaturadoAguaCategoria() != null){

									consumoAFaturar = calcularValoresAguaEsgotoHelper.getConsumoFaturadoEsgotoCategoria();
								}
							}

							provisaoReceita = new ProvisaoReceita(anoMesProvisao, imovel.getLocalidade().getGerenciaRegional().getId(),
											imovel.getLocalidade().getId(), imovel.getId(), dataLeituraMesProvisao, Util.obterShort("30"),
											consumoMedio, diferencaDias.shortValue(), consumoEstimadoDia, consumoAFaturar,
											calcularValoresAguaEsgotoHelper.getQuantidadeEconomiasCategoria(),
											valorConsumoMinimoDiaCategoria,
											calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria(),
											calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria(),
											calcularValoresAguaEsgotoHelper.getValorTotalCategoria(), new Date(),
											calcularValoresAguaEsgotoHelper.getIdCategoria().shortValue());

							colecaoProvisao.add(provisaoReceita);
						}
					}else{

						for(Iterator iteratorColecaoCalcularValoresAguaEsgotoHelper = colecaoCalcularValoresAguaEsgotoHelper.iterator(); iteratorColecaoCalcularValoresAguaEsgotoHelper
										.hasNext();){

							CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) iteratorColecaoCalcularValoresAguaEsgotoHelper
											.next();

							valorConsumoMinimoDiaCategoria = BigDecimal.ZERO;

							provisaoReceita = new ProvisaoReceita(anoMesProvisao, imovel.getLocalidade().getGerenciaRegional().getId(),
											imovel.getLocalidade().getId(), imovel.getId(), dataLeituraMesProvisao, Util.obterShort("30"),
											consumoMedio, diferencaDias.shortValue(), consumoEstimadoDia, consumoAFaturar,
											calcularValoresAguaEsgotoHelper.getQuantidadeEconomiasCategoria(),
											valorConsumoMinimoDiaCategoria,
											calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria(),
											calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria(),
											calcularValoresAguaEsgotoHelper.getValorTotalCategoria(), new Date(),
											calcularValoresAguaEsgotoHelper.getIdCategoria().shortValue());

							colecaoProvisao.add(provisaoReceita);
						}
					}

				}

				// limpa variáveis para processar próximo imóvel
				diferencaDias = 0;
				medicaoHistorico = null;
				consumoEstimadoDia = BigDecimal.ZERO;
				consumoAFaturar = 0;
				colecaoCalcularValoresAguaEsgotoHelper = null;
				colecaoCategorias = null;
				valorAguaMinimoDiaCategoria = BigDecimal.ZERO;
				valorEsgotoMinimoDiaCategoria = BigDecimal.ZERO;
				valorConsumoMinimoDiaCategoria = BigDecimal.ZERO;
				provisaoReceita = null;
				dataLeituraMesProvisao = null;
				consumoMedio = null;
				consumoHistoricoEsgoto = null;
				consumoHistoricoAgua = null;
			}

			if(!Util.isVazioOrNulo(colecaoProvisao)){

				this.getControladorBatch().inserirColecaoObjetoParaBatch(colecaoProvisao);
			}

			System.out.println("##### -> FIM GERAR PROVISÃO RECEITA SETOR: " + idSetorComercial.toString());

			// Registrar o fim do processamento da unidade de processamento do batch
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){

			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}
	}
	
	public Collection verificarInscricaoImovelCampanha(String idImovel, Campanha campanha) throws ControladorException{

		// [FS0002 – Verificar existência do imóvel]
		Imovel imovel = null;

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(filtroImovel.LIGACAO_AGUA_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(filtroImovel.LIGACAO_ESGOTO_SITUACAO);

		Collection colecaoImovel = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		if(colecaoImovel.isEmpty()){
			throw new ControladorException("atencao.pesquisa.imovel.inexistente", null, idImovel);
		}

		imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

		// Caso o imóvel informado/selecionado não esteja inscrito na campanha de premiação mais
		// recente da empresa
		FiltroCampanhaCadastro filtroCampanhaCadastro = null;
		filtroCampanhaCadastro = new FiltroCampanhaCadastro();
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastro.CAMPANHA);
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastro.ORGAO_EXPEDIDOR);
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastro.UNIDADE_ORGANIZACIONAL);
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastro.UNIDADE_FEDERACAO);
		filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.IMOVEL_ID, idImovel));
		filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.CAMPANHA_ID, campanha.getId()));
		Collection colecaoCampanhaCadastro = getControladorUtil().pesquisar(filtroCampanhaCadastro, CampanhaCadastro.class.getName());

		if(colecaoCampanhaCadastro == null || colecaoCampanhaCadastro.isEmpty()){

			// [FS0003] – Verificar prazo inscrição na campanha
			Date dataAtual = new Date();
			if(Util.compararData(campanha.getTmInscricaoFim(), dataAtual) == -1){
				throw new ControladorException("atencao.campanhapremiacao.prazo_inscricao_campanha_expirado", null,
								campanha.getDsTituloCampanha(),
								Util.formatarDataComHora(campanha.getTmInscricaoInicio()), Util.formatarDataComHora(campanha
												.getTmInscricaoFim()));
			}

			// [FS0004] – Verificar inscrição bloqueada para o imóvel
			if(campanha.getIndicadorInscricaoBloqueio().equals(ConstantesSistema.SIM)){
				
				String tipobloqueio = this.verificarInscricaoBloqueadaImovel(imovel, campanha);

				if(tipobloqueio != null){
					if(tipobloqueio.equals(ConstantesSistema.BLOQUEIO_IMOVEL_CAMPANHA_CATEGORIA_NAO_CONTEMPLADA)){

						Categoria categoriaImovel = getControladorImovel().obterPrincipalCategoriaImovel(imovel.getId());

						throw new ControladorException("atencao.campanhapremiacao.categoria_nao_contemplada_campanha", null, imovel.getId()
										+ " - " + categoriaImovel.getDescricao(), campanha.getDsTituloCampanha());
					}else if(tipobloqueio.equals(ConstantesSistema.BLOQUEIO_IMOVEL_CAMPANHA_SITUACAO_LIGACAO_NAO_CONTEMPLADA)){

						String dsSituacaoLigacaoAgua = (imovel.getLigacaoAguaSituacao() != null
										&& imovel.getLigacaoAguaSituacao().getDescricao() != null ? imovel.getLigacaoAguaSituacao()
										.getDescricao() : "null");

						String dsSituacaoLigacaoEsgoto = (imovel.getLigacaoEsgotoSituacao() != null
										&& imovel.getLigacaoEsgotoSituacao().getDescricao() != null ? imovel.getLigacaoEsgotoSituacao()
										.getDescricao() : "null");

						throw new ControladorException("atencao.campanhapremiacao.situacao_ligacao_nao_contemplada_campanha", null,
										imovel.getId() + " - " + dsSituacaoLigacaoAgua + " - " + dsSituacaoLigacaoEsgoto,
										campanha.getDsTituloCampanha());
					}
				}

			}

			// Efetuar a inscrição do imóvel na campanha de premiação
			return null;

		}
		// Caso contrário, ou seja, caso o imóvel informado/selecionado esteja inscrito na
		// campanha
		// de premiação mais recente da empresa
		else{
				
			CampanhaCadastro campanhaCadastro = (CampanhaCadastro) Util.retonarObjetoDeColecao(colecaoCampanhaCadastro);
			
			// Consulta os telefones
			FiltroCampanhaCadastroFone filtroCampanhaCadastroFone = new FiltroCampanhaCadastroFone();
			filtroCampanhaCadastroFone.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastroFone.CAMPANHA_CADASTRO,
							campanhaCadastro.getId()));
			filtroCampanhaCadastroFone.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastroFone.FONE_TIPO);

			Collection colCampanhaCadastroFone = this.getControladorUtil().pesquisar(filtroCampanhaCadastroFone,
							CampanhaCadastroFone.class.getName());

			// Indicador Exibir Botão Emitir Comprovante
			Short indicadorExibirBotaoEmitirComprovante = ConstantesSistema.NAO;
			if(Util.compararData(campanhaCadastro.getCampanha().getDataLiberacaoSorteio(), new Date()) == 1){
				if(campanhaCadastro.getIndicadorComprovanteBloqueado().equals(ConstantesSistema.NAO)){
					indicadorExibirBotaoEmitirComprovante = ConstantesSistema.SIM;
				}else{
					this.determinarSituacaoComprovanteInscricao(campanhaCadastro);

					if(campanhaCadastro.getIndicadorComprovanteBloqueado().equals(ConstantesSistema.NAO)){
						indicadorExibirBotaoEmitirComprovante = ConstantesSistema.SIM;
					}
				}
			}

			ArrayList retorno = new ArrayList();
			
			// Retorna o Cadastro e os Fones para serem exibidos na Consulta.
			retorno.add(campanhaCadastro);
			retorno.add(colCampanhaCadastroFone);
			retorno.add(indicadorExibirBotaoEmitirComprovante);
			
			return retorno;
		}

	}

	private boolean verificarSituacaoLigacaoImovelSatisfeita(Collection<CampanhaSituacaoLigacao> colCampanhaSituacaoLigacao, Imovel imovel)
					throws ControladorException{

		boolean retorno = false;

		// Carregar Situa��o Liga��o de �gua e Esgoto do Im�vel
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));

		imovel = (Imovel) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()));

		for(CampanhaSituacaoLigacao campanhaSituacaoLigacao : colCampanhaSituacaoLigacao){

			// Condição 1
			if(campanhaSituacaoLigacao.getLigacaoAguaSituacao() != null && campanhaSituacaoLigacao.getLigacaoEsgotoSituacao() != null){

				if(campanhaSituacaoLigacao.getLigacaoAguaSituacao().getId().equals(imovel.getLigacaoAguaSituacao().getId())
								&& campanhaSituacaoLigacao.getLigacaoEsgotoSituacao().getId()
												.equals(imovel.getLigacaoEsgotoSituacao().getId())){

					retorno = true;
					break;
				}
			}
			// Condição 2
			else if(campanhaSituacaoLigacao.getLigacaoAguaSituacao() != null && campanhaSituacaoLigacao.getLigacaoEsgotoSituacao() == null){

				if(campanhaSituacaoLigacao.getLigacaoAguaSituacao().getId().equals(imovel.getLigacaoAguaSituacao().getId())){

					retorno = true;
					break;
				}

			}
			// Condição 3
			else if(campanhaSituacaoLigacao.getLigacaoAguaSituacao() == null && campanhaSituacaoLigacao.getLigacaoEsgotoSituacao() != null){

				if(campanhaSituacaoLigacao.getLigacaoEsgotoSituacao().getId().equals(imovel.getLigacaoEsgotoSituacao().getId())){

					retorno = true;
					break;
				}

			}
		}

		return retorno;

	}
	
	/**
	 * [UC3105] Efetuar Inscrição Campanha Premiaçao
	 * [SB0003] – Efetuar Inscrição
	 */
	public CampanhaCadastro efetuarInscricaoCampanhaPremiacaoAction(Usuario usuario, CampanhaCadastro campanhaCadastro,
					Collection<CampanhaCadastroFone> colecaoCampanhaCadastroFone) throws ControladorException{

		try{

			if(campanhaCadastro != null){
				// Validar CPF - CNPJ
				Integer tipoDocumento = this.verificarDocumentoImpedido(campanhaCadastro.getCampanha().getId(),
								campanhaCadastro.getNumeroCPF(),
								campanhaCadastro.getNumeroCNPJ());

				if(tipoDocumento != null){
					if(tipoDocumento.equals(ConstantesSistema.DOCUMENTO_IMPEDIDO_CPF)){
						throw new ControladorException("atencao.campanhapremiacao.documento_impedido_cpf");
					}else if(tipoDocumento.equals(ConstantesSistema.DOCUMENTO_IMPEDIDO_CNPJ)){
						throw new ControladorException("atencao.campanhapremiacao.documento_impedido_cnpj");
					}
				}

				// [SB1000] – Gerar Número Inscrição – Modelo 1
				String nuInscricao = this.gerarNumeroInscricaoCampanhaPremiacaoModelo1(campanhaCadastro);
				
				campanhaCadastro.setNumeroInscricao(nuInscricao);
				campanhaCadastro.setTmInscricao(new Date());
				
				// Meio de Solicitação
				if(usuario != null){
					UnidadeOrganizacional unidOrganizacionalUsuario = this.getControladorUnidade().pesquisarUnidadeUsuario(usuario.getId());
					campanhaCadastro.setMeioSolicitacao(unidOrganizacionalUsuario.getMeioSolicitacao());
					campanhaCadastro.setUnidadeOrganizacional(unidOrganizacionalUsuario);
					campanhaCadastro.setIdInscricao(usuario.getId());
				}else{
					FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();
					filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.INDICADOR_USO,
									ConstantesSistema.SIM));

					Collection<MeioSolicitacao> colMeioSolicitacao = this.getControladorUtil().pesquisar(filtroMeioSolicitacao,
									MeioSolicitacao.class.getName());

					for(MeioSolicitacao meioSolicitacao : colMeioSolicitacao){
						if(meioSolicitacao.getDescricao().equals("INTERNET")){
							campanhaCadastro.setMeioSolicitacao(meioSolicitacao);
							break;
						}
					}

					FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
					filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.SIM));

					Collection<UnidadeOrganizacional> colUnidadeOrganizacional = this.getControladorUtil().pesquisar(
									filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

					for(UnidadeOrganizacional unidadeOrganizacional : colUnidadeOrganizacional){
						if(unidadeOrganizacional.getDescricao().equals("INTERNET")){
							campanhaCadastro.setUnidadeOrganizacional(unidadeOrganizacional);
							break;
						}
					}
				}

				// [SB0005] – Determinar Situação do Comprovante de Inscrição
				this.determinarSituacaoComprovanteInscricao(campanhaCadastro);

				campanhaCadastro.setUltimaAlteracao(new Date());

				/*
				 * [UC0107] Registrar Transação
				 */

				// INICIO ------------ REGISTRAR TRANSAÇÃO ----------------
				Integer idImovel = campanhaCadastro.getImovel().getId();
				RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EFETUAR_INSCRICAO_CAMPANHA_PREMIACAO,
								idImovel, idImovel, new UsuarioAcaoUsuarioHelper(
												usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_EFETUAR_INSCRICAO_CAMPANHA_PREMIACAO);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);
				operacaoEfetuada.setArgumentoValor(idImovel);
				campanhaCadastro.setOperacaoEfetuada(operacaoEfetuada);
				campanhaCadastro.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(campanhaCadastro);

				// FIM ------------ REGISTRAR TRANSAÇÃO ----------------
				this.getControladorUtil().inserir(campanhaCadastro);


				if(colecaoCampanhaCadastroFone != null && !colecaoCampanhaCadastroFone.isEmpty()){
					for(CampanhaCadastroFone campanhaCadastroFone : colecaoCampanhaCadastroFone){
						campanhaCadastroFone.setCampanhaCadastro(campanhaCadastro);

						this.getControladorUtil().inserir(campanhaCadastroFone);
					}

				}

				// O sistema gera as ocorrências cadastrais da inscrição
				// [SB0006 – Gerar Ocorrências Cadastrais].

				gerarOcorrenciasCadastrais(campanhaCadastro, colecaoCampanhaCadastroFone);

				// [FS0014] - Verificar sucesso da transação
				// Este fluxo está sendo feito na Action

			}

			return campanhaCadastro;


		}catch(ControladorException e){
			sessionContext.setRollbackOnly();

			// Exceção levantada por violação de chave única (idImovel)
			if(e.getCause() instanceof ErroRepositorioException){
				if(e.getCause().getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException){
					throw new ControladorException("atencao.campanhapremiacao.duplicidade_imovel_ja_inscrito", null, campanhaCadastro
									.getImovel().getId().toString(), campanhaCadastro.getCampanha().getDsTituloCampanha());
				}
			}

			throw new ControladorException(e.getMessage(), e);
		}
	}

	/**
	 * [SB1000] – Gerar Número Inscrição – Modelo 1
	 */
	private String gerarNumeroInscricaoCampanhaPremiacaoModelo1(CampanhaCadastro campanhaCadastro) throws ControladorException{

		String nuInscricao = "";
		// Consultar UNEG_NMABREVIADO
		FiltroImovel filtroImovel = new FiltroImovel();
		
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.UNIDADE_NEGOCIO);
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, campanhaCadastro.getImovel().getId()));
		
		Collection colImovel = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
		
		Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colImovel);
		
		String dsAbreviadoUnidadeNegocio = Util.completarStringZeroEsquerda(imovel.getLocalidade().getUnidadeNegocio().getNomeAbreviado(), 4);

		// Próximo valor da sequence
		String nuProxSequencial = this.getProximoSequencial(imovel.getLocalidade().getUnidadeNegocio().getId(), campanhaCadastro
						.getCampanha().getId());
		String nuProxSequencialCompleto = Util.adicionarZerosEsqueda(6, nuProxSequencial);

		Integer digVerificadorModulo11 = Util.obterDigitoVerificadorModulo11Correto(nuProxSequencialCompleto);

		nuInscricao = dsAbreviadoUnidadeNegocio + nuProxSequencialCompleto + digVerificadorModulo11;


		return nuInscricao;
	}

	private String getProximoSequencial(Integer idUnidadeNegocio, Integer idCampanha) throws ControladorException{
		Integer nuProxSequencial = null;
		// Consultar o último sequencial
		FiltroCampanhaUnidadePremiacao filtroCampanhaUnidadePremiacao = new FiltroCampanhaUnidadePremiacao();
		filtroCampanhaUnidadePremiacao.adicionarParametro(new ParametroSimples(FiltroCampanhaUnidadePremiacao.CAMPANHA, idCampanha));
		filtroCampanhaUnidadePremiacao.adicionarParametro(new ParametroSimples(FiltroCampanhaUnidadePremiacao.UNIDADE_NEGOCIO,
						idUnidadeNegocio));
		
		Collection colCampanhaUnidadePremiacao = this.getControladorUtil().pesquisar(filtroCampanhaUnidadePremiacao,
						CampanhaUnidadePremiacao.class.getName());
		
		if(!colCampanhaUnidadePremiacao.isEmpty()){
			CampanhaUnidadePremiacao campanhaUnidadePremiacao = (CampanhaUnidadePremiacao) Util.retonarObjetoDeColecao(colCampanhaUnidadePremiacao);
			
			String nomeSequence = ConstantesSistema.CAMPANHA_PREMIACAO_PREFIXO_NOME_SEQUENCE + campanhaUnidadePremiacao.getId();
			
			try{
				nuProxSequencial = repositorioCadastro.obterNextValSequence(nomeSequence);

			}catch(ErroRepositorioException ex){
				// Excecao levantada quando o sequence não existir (SQLGrammarException)
				if(ex.getCause() instanceof SQLGrammarException){
					try{
						repositorioCadastro.criarSequence(nomeSequence);

						nuProxSequencial = repositorioCadastro.obterNextValSequence(nomeSequence);
					}catch(ErroRepositorioException e){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", ex);
					}

				}else{
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", ex);
				}
			}

		}
						
		return nuProxSequencial.toString();
	}

	// [SB0005] – Determinar Situação do Comprovante de Inscrição
	private void determinarSituacaoComprovanteInscricao(CampanhaCadastro campanhaCadastro) throws ControladorException{

		Short indComprovanteBloqueado = ConstantesSistema.NAO;

		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		String amReferenciaFinal = sistemaParametro.getAnoMesFaturamento().toString();

		Date dataVencimentoInicial = Util.criarData(1, 1, 1000);

		Date dataVencimentoFinal = new Date();

		if(campanhaCadastro.getCampanha().getIndicadorAdimplencia().equals(ConstantesSistema.SIM)){
			// [UC0067] Obter Débito do Imóvel ou Cliente
			ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = null;
			colecaoDebitoImovel = this.getControladorCobranca().obterDebitoImovelOuCliente(
							1, // Indicador
																								// débito
																								// imóvel
							campanhaCadastro.getImovel().getId().toString(), // Matrícula do
																				// imóvel
							null, // Código do cliente
							null, // Tipo de relação do cliento com o
							// imóvel
							"100001", // Referência
																					// inicial do
							// débito
							amReferenciaFinal, // Referência
																					// final do
							// débito
							dataVencimentoInicial, // Inicio
							// Vencimento
							dataVencimentoFinal, // Final
							// Vencimento
							campanhaCadastro.getCampanha().getIndicadorPagamento(), // Indicador
																					// pagamento
							campanhaCadastro.getCampanha().getIndicadorContaRevisao(), // Indicador
																						// conta em
																						// revisão
							campanhaCadastro.getCampanha().getIndicadorDebitoACobrar(), // Indicador
																						// débito a
																						// cobrar
							1, // Indicador crédito a realizar
							1, // Indicador notas promissórias
							1, // Indicador guias de pagamento
							1, // Indicador acréscimos por impontualidade
							true, // Indicador Contas
							null, null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
							ConstantesSistema.SIM);

			// Caso as lista de contas, guias de pagamento e débitos a cobrar retornadas pelo
			// [UC0067] não estejam vazias
			if((colecaoDebitoImovel.getColecaoContasValores() != null && !colecaoDebitoImovel.getColecaoContasValores().isEmpty())
							|| (colecaoDebitoImovel.getColecaoContasValoresImovel() != null && !colecaoDebitoImovel
											.getColecaoContasValoresImovel().isEmpty())
							|| (colecaoDebitoImovel.getColecaoGuiasPagamentoValores() != null && !colecaoDebitoImovel
											.getColecaoGuiasPagamentoValores().isEmpty())
							|| (colecaoDebitoImovel.getColecaoDebitoACobrar() != null && !colecaoDebitoImovel.getColecaoDebitoACobrar()
											.isEmpty())){
				indComprovanteBloqueado = ConstantesSistema.SIM;
			}
		}

		campanhaCadastro.setIndicadorComprovanteBloqueado(indComprovanteBloqueado);
	}

	// [SB0006 – Gerar Ocorrências Cadastrais]
	private void gerarOcorrenciasCadastrais(CampanhaCadastro campanhaCadastro, Collection<CampanhaCadastroFone> colecaoCadastroFones)
					throws ControladorException{

			Cliente clienteUsuarioNaBase = getControladorImovel().pesquisarClienteUsuarioImovel(campanhaCadastro.getImovel().getId());
			Cliente clienteResposavelNaBase = getControladorImovel().pesquisarClienteResponsavelImovel(campanhaCadastro.getImovel().getId());
		Set<Cliente> clientesNaBase = new HashSet<Cliente>();
			
			Integer idCampannhaCadastro = campanhaCadastro.getId();
			

		if(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				
				if(clienteUsuarioNaBase == null){
					
					gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(CampanhaCadastro.TIPO_RELACAO_USUARIO,
									CampanhaMotivoOcorrencia.DESCRICAO_NOME_CLIENTE), campanhaCadastro, campanhaCadastro.getNomeCliente(),
									null);
					
				}else{
				clientesNaBase.add(clienteUsuarioNaBase);
				gerarOcorrenciasCadastraisCliente(campanhaCadastro, clientesNaBase, CampanhaCadastro.TIPO_RELACAO_USUARIO);

				}

		}else if(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){

					if(clienteResposavelNaBase == null){
						
					gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(
CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL,
										CampanhaMotivoOcorrencia.DESCRICAO_NOME_CLIENTE), campanhaCadastro, campanhaCadastro.getNomeCliente(),
 null);
						
			}else{
				clientesNaBase.add(clienteResposavelNaBase);
				gerarOcorrenciasCadastraisCliente(campanhaCadastro, clientesNaBase, CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL);
			}

		}else if(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){

					if(clienteResposavelNaBase == null || clienteUsuarioNaBase == null){
						
					gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(
									CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL,
										CampanhaMotivoOcorrencia.DESCRICAO_NOME_CLIENTE), campanhaCadastro, campanhaCadastro.getNomeCliente(),
 null);
					}else{

				clientesNaBase.add(clienteUsuarioNaBase);
				clientesNaBase.add(clienteResposavelNaBase);

						gerarOcorrenciasCadastraisCliente(campanhaCadastro, clientesNaBase,
										CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL);

					}

				}
			

			Collection<ClienteFone> colecaoClienteFonesNaBase = new ArrayList<ClienteFone>();

		if(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)
						|| campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){

				if(clienteUsuarioNaBase != null){
					FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
					filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, clienteUsuarioNaBase.getId()));

					colecaoClienteFonesNaBase.addAll(getControladorUtil().pesquisar(filtroClienteFone, ClienteFone.class.getName()));
				}

			}
		if(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)
						|| campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				if(clienteResposavelNaBase != null){
					FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
					filtroClienteFone
									.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, clienteResposavelNaBase.getId()));

					colecaoClienteFonesNaBase.addAll(getControladorUtil().pesquisar(filtroClienteFone, ClienteFone.class.getName()));
				}
			}

			for(CampanhaCadastroFone cadastroFone : colecaoCadastroFones){
				boolean clientePossuiFoneTipo = false;
			if(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				if(!clientePossuiFoneTipo && clienteUsuarioNaBase != null){
						clientePossuiFoneTipo = getControladorCliente().verificarClientePossuiFoneTipo(clienteUsuarioNaBase.getId(),
										cadastroFone.getFoneTipo().getId());
					}
				}
			if(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				if(!clientePossuiFoneTipo && clienteResposavelNaBase != null){
						clientePossuiFoneTipo = getControladorCliente().verificarClientePossuiFoneTipo(clienteResposavelNaBase.getId(),
										cadastroFone.getFoneTipo().getId());
					}
				}
			if(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){

				if(!clientePossuiFoneTipo && clienteUsuarioNaBase != null && clienteResposavelNaBase != null){

					boolean usuarioFoneTipo = getControladorCliente().verificarClientePossuiFoneTipo(clienteUsuarioNaBase.getId(),
									cadastroFone.getFoneTipo().getId());
					boolean responsavelFoneTipo = getControladorCliente().verificarClientePossuiFoneTipo(clienteResposavelNaBase.getId(),
									cadastroFone.getFoneTipo().getId());

					clientePossuiFoneTipo = usuarioFoneTipo || responsavelFoneTipo;

				}

			}

				gerarOcorrenciasCadastraisFone(campanhaCadastro, colecaoClienteFonesNaBase, cadastroFone,
							campanhaCadastro.getCodigoTipoRelacaoClienteImovel(), clientePossuiFoneTipo, clientesNaBase);
			}

	}

	private void gerarOcorrenciasCadastraisCliente(CampanhaCadastro campanhaCadastro, Set<Cliente> clientesNaBase,
					Integer tipoRelacaoCliente)
					throws ControladorException{

		FiltroCliente filtroCliente = new FiltroCliente();
		Collection<Integer> idsClientes = new ArrayList<Integer>();
		for(Cliente cliente : clientesNaBase){
			idsClientes.add(cliente.getId());
		}

		filtroCliente.adicionarParametro(new ParametroSimplesColecao(FiltroCliente.ID, idsClientes));
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ORGAO_EXPEDIDOR_RG);

		Collection<Cliente> colecaoClientesBase = getControladorUtil()
						.pesquisar(filtroCliente, Cliente.class.getName());

		UnidadeFederacao unidadeFederacaoCampanha = null;
		OrgaoExpedidorRg orgaoExpedidorRgCampanha = null;

		if(campanhaCadastro.getUnidadeFederacao() != null){
		FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
		filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, campanhaCadastro.getUnidadeFederacao()
						.getId()));
			unidadeFederacaoCampanha = (UnidadeFederacao) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroUnidadeFederacao, UnidadeFederacao.class.getName()));
		}

		if(campanhaCadastro.getOrgaoExpedidorRG() != null){
		FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
		filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.ID, campanhaCadastro.getOrgaoExpedidorRG()
						.getId()));
			orgaoExpedidorRgCampanha = (OrgaoExpedidorRg) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
						filtroOrgaoExpedidorRg, OrgaoExpedidorRg.class.getName()));
		}

		if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){

			Cliente cliente1 = ((ArrayList<Cliente>) colecaoClientesBase).get(0);
			Cliente cliente2 = null;

			// tratamento - caso o cliente usuario seja igual ao respons�vel
			if(colecaoClientesBase.size() == 1){
				cliente2 = cliente1;
			}else{
				cliente2 = ((ArrayList<Cliente>) colecaoClientesBase).get(1);
			}

			if(!campanhaCadastro.getNomeCliente().equals(cliente1.getNome())
							|| !campanhaCadastro.getNomeCliente().equals(cliente2.getNome())){
				String nomeClienteNaBase = cliente1.getNome() + " ; " + cliente2.getNome();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_NOME_CLIENTE), campanhaCadastro, campanhaCadastro.getNomeCliente(),
								nomeClienteNaBase);
			}

			if(campanhaCadastro.getNumeroCPF() != null
							&& (!campanhaCadastro.getNumeroCPF().equals(cliente1.getCpf()) || !campanhaCadastro.getNumeroCPF().equals(
											cliente2.getCpf()))){
				String cpfNaBase = cliente1.getCpf() + " ; " + cliente2.getCpf();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_CPF_CNPJ), campanhaCadastro, campanhaCadastro.getNumeroCPF(), cpfNaBase);
			}else if(campanhaCadastro.getNumeroCNPJ() != null
							&& (!campanhaCadastro.getNumeroCNPJ().equals(cliente1.getCnpj()) || !campanhaCadastro.getNumeroCNPJ().equals(
											cliente2.getCnpj()))){
				String cnpjNaBase = cliente1.getCnpj() + " ; " + cliente2.getCnpj();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_CPF_CNPJ), campanhaCadastro, campanhaCadastro.getNumeroCNPJ(),
								cnpjNaBase);
			}

			if(campanhaCadastro.getNumeroRG() != null
							&& (!campanhaCadastro.getNumeroRG().equals(cliente1.getRg()) || !campanhaCadastro.getNumeroRG().equals(
											cliente2.getRg()))){
				String rgNaBase = cliente1.getRg() + " ; " + cliente2.getRg();

				gerarOcorrenciasCadastrais(
								CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente, CampanhaMotivoOcorrencia.DESCRICAO_RG),
								campanhaCadastro, campanhaCadastro.getNumeroRG(), rgNaBase);
			}

			if(campanhaCadastro.getDataRGEmissao() != null
							&& (!campanhaCadastro.getDataRGEmissao().equals(cliente1.getDataEmissaoRg()) || !campanhaCadastro
											.getDataRGEmissao().equals(cliente2.getDataEmissaoRg()))){
				String dataEmissaoNaBase = Util.formatarData(cliente1.getDataEmissaoRg()) + " ; "
								+ Util.formatarData(cliente2.getDataEmissaoRg());

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_DT_EMISSAO_RG), campanhaCadastro, Util.formatarData(campanhaCadastro
								.getDataRGEmissao()), dataEmissaoNaBase);
			}

			if(orgaoExpedidorRgCampanha != null
							&& (cliente1.getOrgaoExpedidorRg() == null
											|| !campanhaCadastro.getOrgaoExpedidorRG().getId()
															.equals(cliente1.getOrgaoExpedidorRg().getId())
											|| cliente2.getOrgaoExpedidorRg() == null || !campanhaCadastro.getOrgaoExpedidorRG().getId()
											.equals(cliente2.getOrgaoExpedidorRg().getId()))){

				String orgaoExpedidorNaBase = null;
				if(cliente1.getOrgaoExpedidorRg() != null){
					orgaoExpedidorNaBase = cliente1.getOrgaoExpedidorRg().getDescricao();
				}
				if(cliente2.getOrgaoExpedidorRg() != null){
					if(orgaoExpedidorNaBase == null){
						orgaoExpedidorNaBase = "";
					}else{
						orgaoExpedidorNaBase += " ; ";
					}
					orgaoExpedidorNaBase += cliente2.getOrgaoExpedidorRg().getDescricao();
				}
				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_ORGAO_EXP), campanhaCadastro, orgaoExpedidorRgCampanha.getDescricao(),
								orgaoExpedidorNaBase);
			}

			if(unidadeFederacaoCampanha != null
							&& (cliente1.getUnidadeFederacao() == null
											|| !campanhaCadastro.getUnidadeFederacao().getId()
															.equals(cliente1.getUnidadeFederacao().getId())
											|| cliente2.getUnidadeFederacao() == null || !campanhaCadastro.getUnidadeFederacao().getId()
											.equals(cliente2.getUnidadeFederacao().getId()))){
				String unidadeFederacaoNaBase = null;

				if(cliente1.getUnidadeFederacao() != null){
					unidadeFederacaoNaBase = cliente1.getUnidadeFederacao().getDescricao();
				}
				if(cliente2.getUnidadeFederacao() != null){
					if(unidadeFederacaoCampanha == null){
						unidadeFederacaoNaBase = "";
					}else{
						unidadeFederacaoNaBase += " ; ";
					}

					unidadeFederacaoNaBase += cliente2.getUnidadeFederacao().getDescricao();
				}

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_ESTADO_RG), campanhaCadastro, unidadeFederacaoCampanha.getDescricao(),
								unidadeFederacaoNaBase);
			}

			if(campanhaCadastro.getDataNascimento() != null
							&& (!campanhaCadastro.getDataNascimento().equals(cliente1.getDataNascimento()) || !campanhaCadastro
											.getDataNascimento().equals(cliente2.getDataNascimento()))){

				String dataNascimentoNaBase = Util.formatarData(cliente1.getDataNascimento()) + " ; "
								+ Util.formatarData(cliente2.getDataNascimento());

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_DT_NASC), campanhaCadastro, Util.formatarData(campanhaCadastro
								.getDataNascimento()), dataNascimentoNaBase);
			}

			if(campanhaCadastro.getNomeMae() != null
							&& (!campanhaCadastro.getNomeMae().equals(cliente1.getNomeMae()) || !campanhaCadastro.getNomeMae().equals(
											cliente2.getNomeMae()))){

				String nomeMaeNaBase = cliente1.getNomeMae() + " ; " + cliente2.getNomeMae();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_NOME_MAE), campanhaCadastro, campanhaCadastro.getNomeMae(),
								nomeMaeNaBase);
			}

			if(campanhaCadastro.getDsEmail() != null
							&& (!campanhaCadastro.getDsEmail().equals(cliente1.getEmail()) || !campanhaCadastro.getDsEmail().equals(
											cliente2.getEmail()))){
				String emailNaBase = cliente1.getEmail() + " ; " + cliente2.getEmail();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_EMAIL), campanhaCadastro, campanhaCadastro.getDsEmail(), emailNaBase);
			}

		}else{
			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoClientesBase);
			if(!campanhaCadastro.getNomeCliente().equals(cliente.getNome())){
				String nomeClienteNaBase = cliente.getNome();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_NOME_CLIENTE), campanhaCadastro, campanhaCadastro.getNomeCliente(),
								nomeClienteNaBase);
			}

			if(campanhaCadastro.getNumeroCPF() != null && !campanhaCadastro.getNumeroCPF().equals(cliente.getCpf())){
				String cpfNaBase = cliente.getCpf();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_CPF_CNPJ), campanhaCadastro, campanhaCadastro.getNumeroCPF(), cpfNaBase);
			}else if(campanhaCadastro.getNumeroCNPJ() != null && !campanhaCadastro.getNumeroCNPJ().equals(cliente.getCnpj())){
				String cnpjNaBase = cliente.getCnpj();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_CPF_CNPJ), campanhaCadastro, campanhaCadastro.getNumeroCNPJ(),
								cnpjNaBase);
			}

			if(campanhaCadastro.getNumeroRG() != null && !campanhaCadastro.getNumeroRG().equals(cliente.getRg())){
				String rgNaBase = cliente.getRg();

				gerarOcorrenciasCadastrais(
								CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente, CampanhaMotivoOcorrencia.DESCRICAO_RG),
								campanhaCadastro, campanhaCadastro.getNumeroRG(), rgNaBase);
			}

			if(campanhaCadastro.getDataRGEmissao() != null && !campanhaCadastro.getDataRGEmissao().equals(cliente.getDataEmissaoRg())){
				String dataEmissaoNaBase = Util.formatarData(cliente.getDataEmissaoRg());

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_DT_EMISSAO_RG), campanhaCadastro, Util.formatarData(campanhaCadastro
								.getDataRGEmissao()), dataEmissaoNaBase);
			}

			if(orgaoExpedidorRgCampanha != null
							&& (cliente.getOrgaoExpedidorRg() == null || !campanhaCadastro.getOrgaoExpedidorRG().getId()
											.equals(cliente.getOrgaoExpedidorRg().getId()))){

				String orgaoExpedidorNaBase = null;
				if(cliente.getOrgaoExpedidorRg() != null){
					orgaoExpedidorNaBase = cliente.getOrgaoExpedidorRg().getDescricao();
				}

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_ORGAO_EXP), campanhaCadastro, orgaoExpedidorRgCampanha.getDescricao(),
								orgaoExpedidorNaBase);
			}

			if(unidadeFederacaoCampanha != null
							&& (cliente.getUnidadeFederacao() == null || !campanhaCadastro.getUnidadeFederacao().getId()
											.equals(cliente.getUnidadeFederacao().getId()))){
				String unidadeFederacaoNaBase = null;
				if(cliente.getUnidadeFederacao() != null){
					unidadeFederacaoNaBase = cliente.getUnidadeFederacao().getDescricao();
				}

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_ESTADO_RG), campanhaCadastro, unidadeFederacaoCampanha.getDescricao(),
								unidadeFederacaoNaBase);
			}

			if(campanhaCadastro.getDataNascimento() != null && !campanhaCadastro.getDataNascimento().equals(cliente.getDataNascimento())){

				String dataNascimentoNaBase = Util.formatarData(cliente.getDataNascimento());

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_DT_NASC), campanhaCadastro, Util.formatarData(campanhaCadastro
								.getDataNascimento()), dataNascimentoNaBase);
			}

			if(campanhaCadastro.getNomeMae() != null && !campanhaCadastro.getNomeMae().equals(cliente.getNomeMae())){

				String nomeMaeNaBase = cliente.getNomeMae();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_NOME_MAE), campanhaCadastro, campanhaCadastro.getNomeMae(),
								nomeMaeNaBase);
			}

			if(campanhaCadastro.getDsEmail() != null && !campanhaCadastro.getDsEmail().equals(cliente.getEmail())){
				String emailNaBase = cliente.getEmail();

				gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
								CampanhaMotivoOcorrencia.DESCRICAO_EMAIL), campanhaCadastro, campanhaCadastro.getDsEmail(), emailNaBase);
			}

		}

	}

	private void gerarOcorrenciasCadastraisFone(CampanhaCadastro campanhaCadastro, Collection<ClienteFone> colecaoClienteFonesNaBase,
					CampanhaCadastroFone cadastroFone,
 Integer tipoRelacaoCliente, boolean clientePossuiFoneTipo,
					Set<Cliente> clientesNaBase)
					throws ControladorException{

		FiltroFoneTipo filtroFoneTipo = new FiltroFoneTipo();
		filtroFoneTipo.adicionarParametro(new ParametroSimples(FiltroFoneTipo.ID, cadastroFone.getFoneTipo().getId()));

		FoneTipo foneTipo = (FoneTipo) Util
						.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroFoneTipo, FoneTipo.class.getName()));

		String dadoSTelefoneCadastro = foneTipo.getDescricao() + " - " + cadastroFone.getCodigoDDD() + " - " + cadastroFone.getNumeroFone();
		if(cadastroFone.getNumeroFoneRamal() != null){
			dadoSTelefoneCadastro += " - " + cadastroFone.getNumeroFoneRamal();
		}

		if(!clientePossuiFoneTipo){
			gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
							CampanhaMotivoOcorrencia.DESCRICAO_TIPO_TELEFONE), campanhaCadastro, dadoSTelefoneCadastro, null);
		}else{

			boolean contemDadosNabase = false;
			StringBuffer clienteFonesString = new StringBuffer();

			for(ClienteFone clienteFoneNaBase : colecaoClienteFonesNaBase){
				clienteFonesString.append(clienteFoneNaBase.getFoneTipo().getId() + ";" + clienteFoneNaBase.getDdd() + ";"
								+ clienteFoneNaBase.getTelefone() + ";" + clienteFoneNaBase.getRamal() + "/");
			}

			for(Cliente cliente : clientesNaBase){
				contemDadosNabase = false;

				for(ClienteFone clienteFoneNaBase : colecaoClienteFonesNaBase){

					if(clienteFoneNaBase.getCliente().getId().equals(cliente.getId())){

						if(clienteFoneNaBase.getFoneTipo().getId().equals(cadastroFone.getFoneTipo().getId())){
							if(clienteFoneNaBase.getDdd() != null && clienteFoneNaBase.getDdd().equals(cadastroFone.getCodigoDDD())){
								if(clienteFoneNaBase.getTelefone() != null
												&& clienteFoneNaBase.getTelefone().equals(cadastroFone.getNumeroFone())){
									if((clienteFoneNaBase.getRamal() == null && cadastroFone.getNumeroFoneRamal() == null)
													|| (clienteFoneNaBase.getRamal() != null && clienteFoneNaBase.getRamal().equals(
																	cadastroFone.getNumeroFoneRamal()))){
										contemDadosNabase = true;
									}
							}
						}
						}
					}
				}
				if(!contemDadosNabase){

					gerarOcorrenciasCadastrais(CampanhaMotivoOcorrencia.retornaDescricaoMotivo(tipoRelacaoCliente,
									CampanhaMotivoOcorrencia.DESCRICAO_DADOS_FONE), campanhaCadastro, dadoSTelefoneCadastro,
									clienteFonesString.toString());
				}

			}
		}
			

		}


	private void gerarOcorrenciasCadastrais(String descricaoMotivo, CampanhaCadastro campannhaCadastro, String dadoNaInscricao,
					String dadoNoSistema)
					throws ControladorException{

		CampanhaCadastroOcorrencia cadastroOcorrencia = new CampanhaCadastroOcorrencia();

		FiltroCampanhaMotivoOcorrencia filtroCampanhaMotivoOcorrencia = new FiltroCampanhaMotivoOcorrencia();

		filtroCampanhaMotivoOcorrencia.adicionarParametro(new ParametroSimples(FiltroCampanhaMotivoOcorrencia.DESCRICAO, descricaoMotivo));

		Collection<CampanhaMotivoOcorrencia> colecaoCampanhaMotivoOcorrencia = getControladorUtil().pesquisar(
						filtroCampanhaMotivoOcorrencia, CampanhaMotivoOcorrencia.class.getName());

		CampanhaMotivoOcorrencia campanhaMotivoOcorrencia = (CampanhaMotivoOcorrencia) Util
						.retonarObjetoDeColecao(colecaoCampanhaMotivoOcorrencia);

		cadastroOcorrencia.setCampanhaMotivoOcorrencia(campanhaMotivoOcorrencia);
		cadastroOcorrencia.setCampanhaCadastro(campannhaCadastro);
		cadastroOcorrencia.setConteudoDadoCadastroCampanha(dadoNaInscricao);
		cadastroOcorrencia.setConteudoDadoCadastroSistema(dadoNoSistema);
		cadastroOcorrencia.setUltimaAlteracao(new Date());

		getControladorUtil().inserir(cadastroOcorrencia);

	}

	public void emitirComprovanteInscricaoCampanhaPremiacao(Usuario usuarioLogado, CampanhaCadastro campanhaCadastro,
					String indicadorEnvioComprovanteEmail) throws ControladorException{

		// Executar o método correspondente à emissão do comprovante de inscrição na campanha
		// (CMPN_NMCLASSEEMITECOMPROVANTE da tabela CAMPANHA).

		if(indicadorEnvioComprovanteEmail != null && indicadorEnvioComprovanteEmail.equals(ConstantesSistema.SIM.toString())){
			// Enviar por E-Mail
			this.enviarEmailComprovanteInscricaoCampanhaPremiacao(campanhaCadastro.getDsEmail(), campanhaCadastro.getCampanha()
							.getDsEmailEnvioComprovante(), campanhaCadastro.getCampanha().getDsTituloCampanha(), null);
			throw new ControladorException("atencao.campanhapremiacao.comprovante_enviado_email");
		}else{
			// Exibir Comprovante
		}

		// Atualizar dados da emissão do comprovante
		campanhaCadastro.setIdEmissaoComprovante(usuarioLogado.getId());

		if(campanhaCadastro.getDataEmissaoComprovante() == null){
			campanhaCadastro.setDataEmissaoComprovante(new Date());
		}

		campanhaCadastro.setIndicadorComprovanteBloqueado(ConstantesSistema.NAO);

		campanhaCadastro.setUltimaAlteracao(new Date());

		this.getControladorUtil().atualizar(campanhaCadastro);
	}

	/**
	 * Enviar e-mail com o documento de cobrança da negociação
	 * [UC3105] Efetuar Inscrição Campanha Premiação
	 * 
	 * @author Hiroshi Goncalves
	 * @date 16/09/2013
	 */
	public void enviarEmailComprovanteInscricaoCampanhaPremiacao(String emailReceptor, String emailRemetente, String dsTituloEmail,
					byte[] relatorioGerado)
					throws ControladorException{

		try{
			File leitura = File.createTempFile("ComprovanteInscricao", ".pdf");

			FileOutputStream out = new FileOutputStream(leitura.getAbsolutePath());
			out.write(relatorioGerado);
			out.flush();
			out.close();

			ServicosEmail.enviarMensagemArquivoAnexado(emailReceptor, emailRemetente, dsTituloEmail, "", leitura);

			leitura.delete();
		}catch(Exception e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	public Integer verificarDocumentoImpedido(Integer idCampanha, String nuCPF, String nuCNPJ) throws ControladorException{

		Integer tipoDocumentoImpedido = null;

		FiltroCampanhaDocumentoImpedido filtroCampanhaDocumentoImpedido = new FiltroCampanhaDocumentoImpedido();
		filtroCampanhaDocumentoImpedido.adicionarParametro(new ParametroSimples(FiltroCampanhaDocumentoImpedido.CAMPANHA_ID, idCampanha));

		if(nuCPF != null && !nuCPF.equals("")){

			filtroCampanhaDocumentoImpedido.adicionarParametro(new ParametroSimples(FiltroCampanhaDocumentoImpedido.NUMERO_CPF, nuCPF));

		}else{
			filtroCampanhaDocumentoImpedido.adicionarParametro(new ParametroSimples(FiltroCampanhaDocumentoImpedido.NUMERO_CNPJ, nuCNPJ));
		}

		Collection colCampanhaDocumentoImpedido = this.getControladorUtil().pesquisar(filtroCampanhaDocumentoImpedido,
						CampanhaDocumentoImpedido.class.getName());

		if(!colCampanhaDocumentoImpedido.isEmpty()){
			CampanhaDocumentoImpedido campanhaDocumentoImpedido = (CampanhaDocumentoImpedido) Util
							.retonarObjetoDeColecao(colCampanhaDocumentoImpedido);

			if(campanhaDocumentoImpedido.getNumeroCpf() != null){
				tipoDocumentoImpedido = ConstantesSistema.DOCUMENTO_IMPEDIDO_CPF;

			}else{
				tipoDocumentoImpedido = ConstantesSistema.DOCUMENTO_IMPEDIDO_CNPJ;

			}
		}
		return tipoDocumentoImpedido;
	}

	// [UC3105] - EfetuarInscricaoCampanhaPremiacao
	// [FS0004] – Verificar inscrição bloqueada para o imóvel
	public String verificarInscricaoBloqueadaImovel(Imovel imovel, Campanha campanha) throws ControladorException{

		String tipoBloqueio = null;

		// [UC0306] - Obter Principal CAtegoria do Imovel
		Categoria categoriaImovel = getControladorImovel().obterPrincipalCategoriaImovel(imovel.getId());

		// Caso exista restrição quanto à categoria do imóvel
		if(categoriaImovel != null){
			FiltroCampanhaCategoria filtroCampanhaCategoria = new FiltroCampanhaCategoria();
			filtroCampanhaCategoria.adicionarParametro(new ParametroSimples(FiltroCampanhaCategoria.CAMPANHA_ID, campanha.getId()));

			Collection<CampanhaCategoria> colecaoCampanhaCategoria = this.getControladorUtil().pesquisar(filtroCampanhaCategoria,
							CampanhaCategoria.class.getName());

			if(!colecaoCampanhaCategoria.isEmpty()){

				CampanhaCategoria campanhaCategoriaImovel = new CampanhaCategoria();
				campanhaCategoriaImovel.setCampanha(campanha);
				campanhaCategoriaImovel.setCategoria(categoriaImovel);

				if(!colecaoCampanhaCategoria.contains(campanhaCategoriaImovel)){
					return ConstantesSistema.BLOQUEIO_IMOVEL_CAMPANHA_CATEGORIA_NAO_CONTEMPLADA;
				}

			}
		}

		// Caso exista restrição quanto à situação da ligação de água e esgoto do imóvel
		FiltroCampanhaSituacaoLigacao filtroCampanhaSituacaoLigacao = new FiltroCampanhaSituacaoLigacao();
		filtroCampanhaSituacaoLigacao.adicionarParametro(new ParametroSimples(FiltroCampanhaSituacaoLigacao.CAMPANHA, campanha.getId()));
		filtroCampanhaSituacaoLigacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaSituacaoLigacao.LIGACAO_AGUA_SITUACAO);
		filtroCampanhaSituacaoLigacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaSituacaoLigacao.LIGACAO_ESGOTO_SITUACAO);

		Collection<CampanhaSituacaoLigacao> colecaoCampanhaSituacaoLigacao = this.getControladorUtil().pesquisar(
						filtroCampanhaSituacaoLigacao, CampanhaSituacaoLigacao.class.getName());

		if(!colecaoCampanhaSituacaoLigacao.isEmpty()){

			boolean isCondicaoSatisfeita = verificarSituacaoLigacaoImovelSatisfeita(colecaoCampanhaSituacaoLigacao, imovel);

			if(!isCondicaoSatisfeita){

				return ConstantesSistema.BLOQUEIO_IMOVEL_CAMPANHA_SITUACAO_LIGACAO_NAO_CONTEMPLADA;
			}

		}
		return tipoBloqueio;
	}

	/**
	 * [UC3109][SB0001]
	 * 
	 * @author Hiroshi Goncalves
	 * @param usuarioLogado
	 * @param campanha
	 * @return
	 * @date 01/10/2013
	 */
	public Integer efetuarSorteioCampanha(Usuario usuarioLogado, Campanha campanha) throws ControladorException{
		Integer retorno = null;

		// [FS0002 - Verificar possibilidade do sorteio para a campanha]
		if(Util.compararData(campanha.getTmInscricaoFim(), new Date()) >= 0){
			throw new ControladorException("atencao.campanhapremiacao.sorteio.prazo_inscricao_aberto", null,
							campanha.getDsTituloCampanha(), Util.formatarDataComHora(campanha.getTmInscricaoInicio()),
							Util.formatarDataComHora(campanha.getTmInscricaoFim()));
		}

		if(Util.compararData(campanha.getDataLiberacaoSorteio(), new Date()) == 1){
			throw new ControladorException("atencao.campanhapremiacao.sorteio.prazo_sorteio_nao_liberado", null,
							campanha.getDsTituloCampanha(), Util.formatarDataComHora(campanha.getDataLiberacaoSorteio()));
		}

		// [FS0003] - Verificar exist�ncia de sorteio em processamento para a campanha
		FiltroProcessoIniciado filtroProcessoIniciado = new FiltroProcessoIniciado();
		filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.ID_PROCESSO,
						Processo.SORTEIO_CAMPANHA_PREMIACAO));
		filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.DATA_HORA_INICIO, new Date()));
		filtroProcessoIniciado.adicionarParametro(new ParametroNulo(FiltroProcessoIniciado.DATA_HORA_TERMINO));
		filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
						ProcessoSituacao.EM_PROCESSAMENTO));

		Collection colProcessoIniciado = this.getControladorUtil().pesquisar(filtroProcessoIniciado, ProcessoIniciado.class.getName());

		if(!colProcessoIniciado.isEmpty()){
			throw new ControladorException("atencao.campanhapremiacao.sorteio.sorteio_sendo_efetuado", null, campanha.getDsTituloCampanha());
		}
		
		//Seleciona os pr�mios da campanha para sorteio 
		try{
			Collection<CampanhaPremio> colCampanhaPremio = repositorioCobranca.pesquisarPremiosParaSorteio(campanha.getId());
			
			if(colCampanhaPremio.isEmpty()){				
				throw new ControladorException("atencao.campanhapremiacao.sorteio.nao_exite_premio", null, campanha.getDsTituloCampanha());
			}

			int qtdPremiacao = 0;

			// Inclui CAMPANHA_SORTEIO
			CampanhaSorteio campanhaSorteio = new CampanhaSorteio();

			Date dataAtual = new Date();

			campanhaSorteio.setCampanha(campanha);
			campanhaSorteio.setUsuario(usuarioLogado);
			campanhaSorteio.setDataSorteio(dataAtual);
			campanhaSorteio.setSorteioInicio(dataAtual);
			campanhaSorteio.setSorteioFim(dataAtual);
			campanhaSorteio.setQuantidadePremiacoes(0);
			campanhaSorteio.setQuantidadePremiacoesCanceladas(0);
			campanhaSorteio.setUltimaAlteracao(dataAtual);

			this.getControladorUtil().inserir(campanhaSorteio);

			CampanhaPremio campanhaPremioAnterior = null;
			ArrayList<CampanhaCadastro> colCampanhaCadastro = new ArrayList<CampanhaCadastro>();

			for(CampanhaPremio campanhaPremio : colCampanhaPremio){

				// [SB0002 - Selecionar Inscri��es conforme a Unidade de Premia��o dos Pr�mios]
				if(mudouGrupo(campanhaPremio, campanhaPremioAnterior)){

					colCampanhaCadastro = (ArrayList<CampanhaCadastro>) this
									.pesquisarInscricoesPorUnidadePremiacao(campanha.getId(), campanhaPremio);

					campanhaPremioAnterior = campanhaPremio;
				}

				// [SB0003] - Realizar Sorteio do Pr�mio
				Short icInscricaoAptaPremiacao = null;
				int indiceSorteio = 0;

				while(campanhaPremio.getQuantidadePremio() > campanhaPremio.getQuantidadePremioSorteada() && !colCampanhaCadastro.isEmpty()){
					Collections.shuffle(colCampanhaCadastro);

					CampanhaCadastro campanhaCadastroSorteado = colCampanhaCadastro.get(indiceSorteio);

					// [SB0004 - Verificar Inscri��o Sorteada Apta para Premia��o]
					icInscricaoAptaPremiacao = this.verificarInscricaoSorteadaAptaPremiacao(campanhaSorteio, campanhaPremio,
									colCampanhaCadastro, indiceSorteio);

					// Caso tenha sorteado alguma inscri��o apta para a premia��o
					if(icInscricaoAptaPremiacao.equals(ConstantesSistema.SIM)){
						CampanhaPremiacao campanhaPremiacao = new CampanhaPremiacao();

						campanhaPremiacao.setCampanhaCadastro(campanhaCadastroSorteado);
						campanhaPremiacao.setCampanhaPremio(campanhaPremio);
						campanhaPremiacao.setCampanhaSorteio(campanhaSorteio);
						campanhaPremiacao.setUltimaAlteracao(dataAtual);

						this.getControladorUtil().inserir(campanhaPremiacao);

						// Atualiza a quantidade sorteada do pr�mio na tabela CAMPANHA_PREMIO
						campanhaPremio.setQuantidadePremioSorteada(campanhaPremio.getQuantidadePremioSorteada() + 1);
						campanhaPremio.setUltimaAlteracao(dataAtual);
						this.getControladorUtil().atualizar(campanhaPremio);

						// Acumula a quantidade de pr�mios sorteados
						qtdPremiacao++;

						// Retira a inscri��o premiada da lista das inscri��es da unidade de
						// premia��o
						colCampanhaCadastro.remove(indiceSorteio);

					}
				}
			}

			// Atualiza os dados do sorteio da campanha na tabela CAMPANHA_SORTEIO
			campanhaSorteio.setSorteioFim(new Date());
			campanhaSorteio.setQuantidadePremiacoes(qtdPremiacao);
			campanhaSorteio.setUltimaAlteracao(new Date());

			this.getControladorUtil().atualizar(campanhaSorteio);

			retorno = campanhaSorteio.getId();
		} catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		
		return retorno;
	}

	private boolean mudouGrupo(CampanhaPremio campanhaPremio, CampanhaPremio campanhaPremioAnterior)
					throws ControladorException{

		boolean retorno = false;
		
		if(campanhaPremioAnterior == null){
			return true;
		}

		if(campanhaPremio.getGerenciaRegional() != null){			
			if(campanhaPremioAnterior.getGerenciaRegional() == null
							|| !campanhaPremio.getGerenciaRegional().getId().equals(campanhaPremioAnterior.getGerenciaRegional().getId())){
				retorno = true;
			}
				
		}else if(campanhaPremio.getUnidadeNegocio() != null){
			if(campanhaPremioAnterior.getUnidadeNegocio() == null
							|| !campanhaPremio.getUnidadeNegocio().getId().equals(campanhaPremioAnterior.getUnidadeNegocio().getId())){
				retorno = true;
			}

		}else if(campanhaPremio.getEloPremio() != null){
			if(campanhaPremioAnterior.getEloPremio() == null
							|| !campanhaPremio.getEloPremio().getId().equals(campanhaPremioAnterior.getEloPremio().getId())){
				retorno = true;
			}

		}else if(campanhaPremio.getLocalidade() != null){
			if(campanhaPremioAnterior.getLocalidade() == null
							|| !campanhaPremio.getLocalidade().getId().equals(campanhaPremioAnterior.getLocalidade().getId())){
				retorno = true;
			}

		}else{
			if (campanhaPremioAnterior.getGerenciaRegional() != null
							|| campanhaPremioAnterior.getUnidadeNegocio() != null
							|| campanhaPremioAnterior.getEloPremio() != null
							|| campanhaPremioAnterior.getLocalidade() != null){
				retorno = true;
			}
		}

		return retorno;
	}

	/**
	 * [UC3109][SB0002]
	 * 
	 * @author Hiroshi Goncalves
	 * @param usuarioLogado
	 * @param campanha
	 * @return
	 * @date 03/10/2013
	 */
	public Collection<CampanhaCadastro> pesquisarInscricoesPorUnidadePremiacao(Integer idCampanha, CampanhaPremio campanhaPremio)
					throws ControladorException{

		Collection<CampanhaCadastro> colecaoRetorno = null;
		try{
			colecaoRetorno = this.repositorioCobranca.pesquisarInscricoesPorUnidadePremiacao(idCampanha, campanhaPremio);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC3109][SB0004]
	 * 
	 * @author Hiroshi Goncalves
	 * @param usuarioLogado
	 * @param campanha
	 * @return
	 * @date 03/10/2013
	 */
	private Short verificarInscricaoSorteadaAptaPremiacao(CampanhaSorteio campanhaSorteio, CampanhaPremio campanhaPremio,
					ArrayList<CampanhaCadastro> colCampanhaCadastro, int indiceSorteado)
					throws ControladorException{		
		
		CampanhaCadastro cadastroSorteado = colCampanhaCadastro.get(indiceSorteado);
		Short icInscricaoAptaPremiacao = ConstantesSistema.SIM;

		// [UC3105] - [FS0004 - Verificar inscri��o bloqueada para o im�vel]
		String mensagemBloqueio = this.verificarInscricaoBloqueadaImovel(cadastroSorteado.getImovel(), campanhaPremio.getCampanha());
		
		if(mensagemBloqueio != null){
			colCampanhaCadastro.remove(indiceSorteado);
			icInscricaoAptaPremiacao = ConstantesSistema.NAO;
			String dsSorteioMotivoExclusao = null;
			

			if(mensagemBloqueio.equals(ConstantesSistema.BLOQUEIO_IMOVEL_CAMPANHA_CATEGORIA_NAO_CONTEMPLADA)){
				dsSorteioMotivoExclusao = CampanhaSorteioMotExclusao.DESCRICAO_CATEGORIA_NAO_CONTEMPLADA;

			}else{
				dsSorteioMotivoExclusao = CampanhaSorteioMotExclusao.DESCRICAO_SITUACAO_NAO_CONTEMPLADA;
			}
			
			this.registrarImpedimentoPremiacaoInscricao(campanhaSorteio, cadastroSorteado, dsSorteioMotivoExclusao);

		}



			Integer retornoDocImpedido = null;
		if(cadastroSorteado.getNumeroCPF() != null && !cadastroSorteado.getNumeroCPF().equals("")){
			retornoDocImpedido = this.verificarDocumentoImpedido(cadastroSorteado.getCampanha().getId(), cadastroSorteado.getNumeroCPF(),
							null);

		}else if(cadastroSorteado.getNumeroCNPJ() != null && !cadastroSorteado.getNumeroCNPJ().equals("")){
			retornoDocImpedido = this.verificarDocumentoImpedido(cadastroSorteado.getCampanha().getId(), null,
							cadastroSorteado.getNumeroCNPJ());
			}

			if(retornoDocImpedido != null){
				colCampanhaCadastro.remove(indiceSorteado);
				icInscricaoAptaPremiacao = ConstantesSistema.NAO;
				String dsSorteioMotivoExclusao = null;

				this.registrarImpedimentoPremiacaoInscricao(campanhaSorteio, cadastroSorteado,
								CampanhaSorteioMotExclusao.DESCRICAO_DOCUMENTO_IMPEDIDO_PARTICIPAR_CAMPANHA);
			}


		// [UC3105] - [SB0005 - Determinar Situa��o do Comprovante de Inscri��o]
		this.determinarSituacaoComprovanteInscricao(cadastroSorteado);

		if(cadastroSorteado.getIndicadorComprovanteBloqueado().equals(ConstantesSistema.SIM)){
			colCampanhaCadastro.remove(indiceSorteado);
			icInscricaoAptaPremiacao = ConstantesSistema.NAO;
			String dsSorteioMotivoExclusao = null;

			this.registrarImpedimentoPremiacaoInscricao(campanhaSorteio, cadastroSorteado,
							CampanhaSorteioMotExclusao.DESCRICAO_IMOVEL_COM_DEBITO);
		}

		return icInscricaoAptaPremiacao;
	}

	/**
	 * [UC3109][SB0005]
	 * 
	 * @author Hiroshi Goncalves
	 * @param usuarioLogado
	 * @param campanha
	 * @return
	 * @date 03/10/2013
	 */
	private void registrarImpedimentoPremiacaoInscricao(CampanhaSorteio campanhaSorteio, CampanhaCadastro campanhaCadastro,
					String dsSorteioMotExclusao) throws ControladorException{

		FiltroCampanhaSorteioMotExclusao filtro = new FiltroCampanhaSorteioMotExclusao();
		filtro.adicionarParametro(new ParametroSimples(FiltroCampanhaSorteioMotExclusao.INDICADOR_USO, ConstantesSistema.SIM));
		filtro.adicionarParametro(new ParametroSimples(FiltroCampanhaSorteioMotExclusao.DESCRICAO, dsSorteioMotExclusao));

		CampanhaSorteioMotExclusao campanhaSorteioMotExclusao = (CampanhaSorteioMotExclusao) Util.retonarObjetoDeColecao(this
						.getControladorUtil().pesquisar(filtro, CampanhaSorteioMotExclusao.class.getName()));

		CampanhaSorteioInscrExclusao objeto = new CampanhaSorteioInscrExclusao();
		objeto.setCampanhaSorteio(campanhaSorteio);
		objeto.setCampanhaCadastro(campanhaCadastro);
		objeto.setCampanhaSorteioMotExclusao(campanhaSorteioMotExclusao);
		objeto.setUltimaAlteracao(new Date());

		this.getControladorUtil().inserir(objeto);
	}

	/**
	 * @author Victon Santos
	 * @param sequence
	 * @return
	 * @date 30/10/2013
	 */
	public Integer obterNextValSequence(String nomeSequence) throws ControladorException{

		try{
			Number sequenceVal = repositorioCadastro.obterNextValSequence(nomeSequence);
			return sequenceVal.intValue();
		}catch(ErroRepositorioException ex){
			// TODO Auto-generated catch block
			throw new ControladorException("erro.sistema", ex);
		}
	}
}
