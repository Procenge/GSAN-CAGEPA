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
 * Vitor Hora
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

package gcom.cadastro.imovel;

import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.arrecadacao.pagamento.*;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocal;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocalHome;
import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.IRepositorioLigacaoEsgoto;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.RepositorioLigacaoEsgotoHBM;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacao;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.cliente.*;
import gcom.cadastro.cliente.bean.ClienteImovelEconomiaHelper;
import gcom.cadastro.endereco.*;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.bean.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.tarifasocial.FiltroTarifaSocialDadoEconomia;
import gcom.cadastro.tarifasocial.TarifaSocialDadoEconomia;
import gcom.cobranca.*;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.bean.SituacaoEspecialCobrancaHelper;
import gcom.cobranca.contrato.CobrancaContrato;
import gcom.cobranca.contrato.FiltroCobrancaContrato;
import gcom.fachada.Fachada;
import gcom.faturamento.*;
import gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gui.faturamento.bean.FiltrarImovelInserirManterContaHelper;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.FiltroMovimentoRoteiroEmpresa;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.MovimentoRoteiroEmpresa;
import gcom.micromedicao.Rota;
import gcom.micromedicao.bean.RelatorioAnaliseConsumoHelper;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.operacional.*;
import gcom.relatorio.cadastro.cliente.FiltrarRelatorioClientesEspeciaisHelper;
import gcom.relatorio.cadastro.imovel.FiltrarRelatorioBoletimCadastroHelper;
import gcom.relatorio.cadastro.imovel.RelatorioImoveisInscricaoAlterada;
import gcom.seguranca.ControladorPermissaoEspecialLocal;
import gcom.seguranca.ControladorPermissaoEspecialLocalHome;
import gcom.seguranca.acesso.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.seguranca.transacao.ControladorTransacaoLocal;
import gcom.seguranca.transacao.ControladorTransacaoLocalHome;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.filtro.*;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;
import gcom.util.parametrizacao.webservice.ParametrosAgenciaVirtual;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @created 7 de Junho de 2004
 */
public class ControladorImovelSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	private IRepositorioImovel repositorioImovel;

	private IRepositorioCategoria repositorioCategoria;

	private IRepositorioFaturamento repositorioFaturamento;

	private IRepositorioCobranca repositorioCobranca;

	private IRepositorioClienteImovel repositorioClienteImovel;

	private IRepositorioLigacaoEsgoto repositorioLigacaoEsgoto;

	private IRepositorioUtil repositorioUtil;

	private static Logger logger = Logger.getLogger(ControladorImovelSEJB.class);

	/**
	 * [SB001] Atualizar Imóvel Campos Atualiza campos de imovel na execução de
	 * ordem Serviço
	 * 
	 * @author Leandro Cavalcanti
	 * @throws ControladorException
	 */
	public void atualizarImovelLigacaoAgua(Imovel imovel, Integer idLigacaoAguaSituacao) throws ControladorException{

		try{
			repositorioImovel.atualizarImovelLigacaoAgua(imovel, idLigacaoAguaSituacao);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Atualiza campos de imovel na execução de ordem Serviço
	 * 
	 * @author Leandro Cavalcanti
	 * @throws ControladorException
	 */
	public void atualizarImovelExecucaoOrdemServicoLigacaoAgua(Imovel imovel, LigacaoAguaSituacao situacaoAgua) throws ControladorException{

		try{
			repositorioImovel.atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, situacaoAgua);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Atualiza campos de imovel na execução de ordem Serviço
	 * 
	 * @author Leandro Cavalcanti
	 * @throws ControladorException
	 */
	public void atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(Imovel imovel, LigacaoEsgotoSituacao situacaoEsgoto)
					throws ControladorException{

		try{
			repositorioImovel.atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, situacaoEsgoto);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Retorna o valor de controladorCliente
	 * 
	 * @return O valor de controladorCliente
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

	/**
	 * Retorna o valor de controladorCobranca
	 * 
	 * @return O valor de controladorCobranca
	 */
	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
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
	 * Retorna o valor de controladorCobranca
	 * 
	 * @return O valor de controladorCobranca
	 */
	private ControladorFaturamentoLocal getControladorFaturamento(){

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
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioImovel = RepositorioImovelHBM.getInstancia();

		repositorioCategoria = RepositorioCategoriaHBM.getInstancia();

		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();

		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();

		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();

		repositorioLigacaoEsgoto = RepositorioLigacaoEsgotoHBM.getInstancia();

		repositorioUtil = RepositorioUtilHBM.getInstancia();
	}

	/**
	 * Método chamado no momento que o bean é removido do contexto do container
	 */
	public void ejbRemove(){

	}

	/**
	 * Método chamado no momento que o bean é ativado no container
	 */
	public void ejbActivate(){

	}

	/**
	 * Método chamado no momento que o bean é passivado no container
	 */
	public void ejbPassivate(){

	}

	/**
	 * * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */

	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * inseri um imvoel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public void inserirImovel(Imovel imovel) throws ControladorException{

		try{
			repositorioImovel.inserirImovel(imovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * inseri o imoóvel economia e o cliente imovel economia do imóvel
	 * subcategoria
	 * 
	 * @param imoveisEconomias
	 *            Description of the Parameter
	 * @throws ControladorException
	 */
	public void informarImovelEconomias(Collection imoveisEconomias, Usuario usuarioLogado) throws ControladorException{

		// cria um iterator para a coleção de imoveis subcategorias
		Iterator imovelEconomiasIterator = imoveisEconomias.iterator();

		// percorre a coleção de imoveis subcategoria para inserir os imoveis
		// economias e c
		// cliente imoveis economia
		while(imovelEconomiasIterator.hasNext()){
			ImovelEconomia imovelEconomia = (ImovelEconomia) imovelEconomiasIterator.next();

			Collection clienteImovelEconomias = imovelEconomia.getClienteImovelEconomias();

			Iterator clienteImovelEconomiaIterator = clienteImovelEconomias.iterator();

			// cria uma coleção dos clientes imoveis economia que serão
			// atualizadas.
			Collection clientesImovelsAtualizadas = new HashSet();

			// cria uma coleção dos clientes imoveis economia que serão
			// atualizadas.
			Collection clientesImovelsInseridas = new HashSet();

			// coleção de clientes imoveis economia que vai ser inserido no
			// Set de
			// fr imovel economia
			Collection<ClienteImovelEconomia> clientesImoveisEconomiasHashSet = new HashSet();

			// cria um laço para a inserção dos clientes imovel economia
			// objeto de imovel economia contém uma coleção de clientes
			// imovel economia
			while(clienteImovelEconomiaIterator.hasNext()){
				ClienteImovelEconomia clienteImovelEconomia = (ClienteImovelEconomia) clienteImovelEconomiaIterator.next();

				clienteImovelEconomia.setImovelEconomia(imovelEconomia);
				if(clienteImovelEconomia.getId() != null && !clienteImovelEconomia.getId().equals("")){
					// adiciona o cliente imovel economia na coleção que
					// serão atualizadas
					clientesImovelsAtualizadas.add(clienteImovelEconomia);
				}else{
					// adiciona o cliente imovel economia na coleção que
					// serão inseridas
					clientesImovelsInseridas.add(clienteImovelEconomia);
				}
				if(clienteImovelEconomia != null && !clienteImovelEconomia.equals("")){
					clientesImoveisEconomiasHashSet.add(clienteImovelEconomia);
				}
			}

			if(clientesImovelsAtualizadas != null && !clientesImovelsAtualizadas.isEmpty()){
				this.getControladorCliente().atualizarClienteImovelEconomia(clientesImovelsAtualizadas);
			}

			// remove todos os clientes imoveis economias do imovel economia
			/*
			 * if (imovelEconomia.getId() != null &&
			 * !imovelEconomia.getId().equals("")) { repositorioImovel
			 * .removerClienteImovelEconomia(imovelEconomia .getId()); }
			 */

			// seta para null a coleção de clientes imoveis economia
			imovelEconomia.setClienteImovelEconomias(null);

			Integer idImovelEconomia = null;

			/**
			 * alterado por pedro alexandre dia 19/11/2006 alterado para acoplar
			 * o esquema de segurança de acesso por abragência
			 */
			// ------------ CONTROLE DE ABRANGENCIA ----------------
			Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovelEconomia.getImovelSubcategoria().getComp_id().getImovel());

			if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.acesso.negado.abrangencia");
			}else{
				// recupera o id do imovel economia
				// para ser inserido no cliente imovel economia
				idImovelEconomia = (Integer) getControladorUtil().inserirOuAtualizar(imovelEconomia);
			}
			// ------------ FIM CONTROLE DE ABRANGENCIA ----------------

			// caso o id do imovel economia for diferente de nulo
			// ou seja caso seja atualização não precisa setar o id
			if(idImovelEconomia != null){

				imovelEconomia.setId(idImovelEconomia);
			}
			Iterator clienteImovelEconomiaInserirIterator = clientesImovelsInseridas.iterator();

			// cria um laço para a inserção dos clientes imovel economia
			// objeto de imovel economia contém uma coleção de clientes
			// imovel economia
			while(clienteImovelEconomiaInserirIterator.hasNext()){
				ClienteImovelEconomia clienteImovelEconomia = (ClienteImovelEconomia) clienteImovelEconomiaInserirIterator.next();

				clienteImovelEconomia.setImovelEconomia(imovelEconomia);
				if(clienteImovelEconomia.getDataFimRelacao() == null || clienteImovelEconomia.getDataFimRelacao().equals("")){
					getControladorUtil().inserir(clienteImovelEconomia);
				}
			}

			imovelEconomia.setClienteImovelEconomias(new HashSet(clientesImoveisEconomiasHashSet));

		}

	}

	/**
	 * Retorna o cep do imóvel
	 * 
	 * @param imovel
	 * @return Descrição do retorno
	 * @exception ControladorException
	 */
	public Cep pesquisarCepImovel(Imovel imovel) throws ControladorException{

		try{

			// Pesquisa e retornar o CEP
			return repositorioImovel.pesquisarCepImovel(imovel);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Retorna a quantidade de economias de um imóvel
	 * 
	 * @param imovel
	 *            Imóvel que será consultado
	 * @return Quantidade de economias
	 * @throws ControladorException
	 */
	public int obterQuantidadeEconomias(Imovel imovel) throws ControladorException{

		Short quantidadeEconomias = null;

		try{

			// Pesquisa e retornar a quantidade de economias em um objeto
			Object objetoQuantidadeEconomias = repositorioImovel.pesquisarObterQuantidadeEconomias(imovel);

			// Obtém a quantidade de economias em integer
			quantidadeEconomias = ((Number) objetoQuantidadeEconomias).shortValue();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return quantidadeEconomias.intValue();
	}

	/**
	 * Retorna a coleção de economias de um imóvel
	 * 
	 * @param imovel
	 *            Imóvel que será consultado
	 * @return Quantidade de economias
	 * @throws ControladorException
	 */
	public Collection obterColecaoImovelSubcategorias(Imovel imovel, Integer quantidadeMinimaEconomia) throws ControladorException{

		Collection retorno = null;

		// // Filtro para obter o imóvel subcategoria
		// FiltroImovelSubCategoria filtroImovelSubCategoria = new
		// FiltroImovelSubCategoria();
		//
		// // Objetos que serão retornados pelo Hibernate
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade("comp_id.subcategoria.categoria");
		//
		// // Objetos que serão utilizados para formatar o endereço
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade("comp_id.imovel.localidade");
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade("comp_id.imovel.setorComercial.municipio");
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade("comp_id.imovel.logradouroBairro.bairro.municipio.unidadeFederacao");
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade("comp_id.imovel.logradouroCep.cep");
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade("comp_id.imovel.logradouroCep.logradouro.logradouroTipo");
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade("comp_id.imovel.logradouroCep.logradouro.logradouroTitulo");
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade("comp_id.imovel.enderecoReferencia");
		//
		// // filtroImovelSubCategoria
		// // .setCampoDistinct(FiltroImovelSubCategoria.IMOVEL);
		//
		// filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(
		// FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));
		//
		// filtroImovelSubCategoria
		// .adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.IMOVEL_ECONOMIAS);

		// Pesquisa imóvel subcategoria passado como parâmetro um imóvel
		Collection colecaoImovelSubCategoria = null;

		try{
			colecaoImovelSubCategoria = repositorioImovel.pesquisarImovelSubcategorias(imovel.getId());
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovelSubCategoria != null && !colecaoImovelSubCategoria.isEmpty()){

			Iterator colecaoImovelSubCategoriaIterator = colecaoImovelSubCategoria.iterator();

			while(colecaoImovelSubCategoriaIterator.hasNext()){

				// Obtém o imóvel subcategoria
				ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) colecaoImovelSubCategoriaIterator.next();

				if(colecaoImovelSubCategoria.size() < quantidadeMinimaEconomia
								&& imovelSubcategoria.getQuantidadeEconomias() < quantidadeMinimaEconomia){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.quantidade_economia_por_subcategoria");
				}
			}

		}else{
			// Caso a coleção não tenha retornado objetos
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.pesquisa.nenhumresultado", null, "imóvel subcategoria");
		}

		retorno = colecaoImovelSubCategoria;

		return retorno;
	}

	/**
	 * remove o imóvel economia e o cliente imovel economia do imóvel
	 * subcategoria
	 * 
	 * @param imovelEconomia
	 *            Description of the Parameter
	 * @throws ControladorException
	 */
	public void removerImovelEconomia(ImovelEconomia imovelEconomia, Usuario usuarioLogado) throws ControladorException{

		if(!imovelEconomia.getImovelSubcategoria().getComp_id().getImovel().getImovelPerfil().getId().equals(ImovelPerfil.TARIFA_SOCIAL)){
			/**
			 * alterado por pedro alexandre dia 19/11/2006 alteração feita para
			 * acoplar o controle de abrangência de usuário
			 */
			// ------------ CONTROLE DE ABRANGENCIA ----------------
			Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovelEconomia.getImovelSubcategoria().getComp_id().getImovel());

			if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.acesso.negado.abrangencia");
			}else{

				FiltroTarifaSocialDadoEconomia filtroTarifaSocialDadoEconomia = new FiltroTarifaSocialDadoEconomia();
				filtroTarifaSocialDadoEconomia.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.IMOVEL_ECONOMIA_ID,
								imovelEconomia.getId()));

				Collection colecaoTarifaSocial = getControladorUtil().pesquisar(filtroTarifaSocialDadoEconomia,
								TarifaSocialDadoEconomia.class.getName());

				if(colecaoTarifaSocial != null && !colecaoTarifaSocial.isEmpty()){

					Iterator colecaoTarifaSocialIterator = colecaoTarifaSocial.iterator();

					while(colecaoTarifaSocialIterator.hasNext()){

						TarifaSocialDadoEconomia tarifaSocialDadoEconomia = (TarifaSocialDadoEconomia) colecaoTarifaSocialIterator.next();

						getControladorUtil().remover(tarifaSocialDadoEconomia);
					}
				}

				getControladorUtil().removerUm(Integer.parseInt(imovelEconomia.getId().toString()), ImovelEconomia.class.getName(), null,
								null);
			}
			// ------------ FIM CONTROLE DE ABRANGENCIA ----------------
		}else{
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.nao_exclusao_dados_economia");
		}

	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param subCategorias
	 *            Descrição do parâmetro
	 * @param enderecoImoveis
	 *            Descrição do parâmetro
	 * @param clientes
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public Integer inserirImovelRetorno(Imovel imovel, Collection subCategorias, Collection consumoFaixaAreaCategoria,
					Collection enderecoImoveis, Collection clientes, Usuario usuarioLogado) throws ControladorException{

		Integer id = null;

		/**
		 * alterado por pedro alexandre dia 17/11/2006 alteração feita para
		 * acoplar o controle de abrangência de usuário
		 */
		// ------------ CONTROLE DE ABRANGENCIA ----------------
		Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovel.getLocalidade());
		// ------------ CONTROLE DE ABRANGENCIA ----------------

		if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.acesso.negado.abrangencia");
		}else{

			this.verificarAlteracaoRota(imovel.getLocalidade().getId(), imovel.getSetorComercial().getCodigo(), imovel.getQuadra()
							.getNumeroQuadra(), imovel.getRota().getId());

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_INSERIR,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			// RegistradorOperacao registradorOperacao = new RegistradorOperacao(
			// Operacao.OPERACAO_IMOVEL_INSERIR, imovel.getSetorComercial().getId(),
			// imovel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
			// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_IMOVEL_INSERIR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);


			// ------------ REGISTRAR TRANSAÇÃO ----------------

			id = (Integer) getControladorUtil().inserir(imovel);
			imovel.setId(id);

			operacaoEfetuada.setIdObjetoPrincipal(id);
			operacaoEfetuada.setArgumentoValor(id);
			imovel.setOperacaoEfetuada(operacaoEfetuada);
			imovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(imovel);
		}
		/** fim alteração de controle de abrangência ************************* */

		Imovel imovelNovo = new Imovel();

		imovelNovo.setId(id);

		Collection clientesImoveis = clientes;
		Iterator iteratorCliente = clientesImoveis.iterator();
		ClienteImovel clienteImovel = null;

		while(iteratorCliente.hasNext()){
			clienteImovel = null;
			clienteImovel = (ClienteImovel) iteratorCliente.next();
			clienteImovel.setImovel(imovelNovo);
			clienteImovel.setUltimaAlteracao(new Date());
			this.getControladorCliente().inserirClienteImovel(clienteImovel, usuarioLogado);
		}

		// Obtem os valores que vem na coleção de
		// subCategorias(economia)para cadastrar em Imovel_Subcategoria
		Collection subcategorias = subCategorias;
		Iterator iteratorSubCategoria = subcategorias.iterator();
		ImovelSubcategoria imovelSubcategoria = null;

		while(iteratorSubCategoria.hasNext()){
			imovelSubcategoria = null;
			imovelSubcategoria = (ImovelSubcategoria) iteratorSubCategoria.next();
			imovelSubcategoria.getComp_id().setImovel(imovelNovo);
			this.inserirImovelSubCategoria(imovelSubcategoria, usuarioLogado);
		}
		if(consumoFaixaAreaCategoria != null){
			// Obtem os valores que vem na coleção de
			// consumoFaixaAreaCategoria para cadastrar em IMOVEL_CONSUMO_FAIXA_CAT
			Collection consumoFaixaAreaCategoriaAux = consumoFaixaAreaCategoria;
			Iterator iteratorConsumoFaixaAreaCategoria = consumoFaixaAreaCategoriaAux.iterator();
			ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = null;

			while(iteratorConsumoFaixaAreaCategoria.hasNext()){
				imovelConsumoFaixaAreaCategoria = null;
				imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) iteratorConsumoFaixaAreaCategoria.next();
				imovelConsumoFaixaAreaCategoria.getComp_id().setImovel(imovelNovo);
				imovelConsumoFaixaAreaCategoria.setUltimaAlteracao(new Date());

				// ------------ REGISTRAR TRANSAÇÃO ----------------
				RegistradorOperacao registradorOperacao = new RegistradorOperacao(
								Operacao.OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR, imovelConsumoFaixaAreaCategoria.getComp_id()
												.getImovel().getId(), imovelConsumoFaixaAreaCategoria.getComp_id().getImovel().getId(),
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);
				operacaoEfetuada.setArgumentoValor(imovelConsumoFaixaAreaCategoria.getComp_id().getImovel().getId());

				imovelConsumoFaixaAreaCategoria.setOperacaoEfetuada(operacaoEfetuada);
				imovelConsumoFaixaAreaCategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(imovelConsumoFaixaAreaCategoria);
				// ------------ REGISTRAR TRANSAÇÃO ----------------

				getControladorUtil().inserir(imovelConsumoFaixaAreaCategoria);
			}
		}

		return id;
	}

	/**
	 * [UC0011] Inserir Imóvel
	 * [UC0014] Manter Imóvel
	 * 
	 * @date 21/01/2014
	 * @author Saulo Lima
	 * @param idLocalidade
	 * @param codigoSetorComercial
	 * @param numeroQuadra
	 * @param idRota
	 */
	public void verificarAlteracaoRota(Integer idLocalidade, Integer codigoSetorComercial, Integer numeroQuadra, Integer idRota)
					throws ControladorException{

		if(idLocalidade != null && codigoSetorComercial != null && numeroQuadra != null && idRota != null){

			String gruposPermitidos = null;
			try{
				gruposPermitidos = ParametroCadastro.P_EXIBIR_ROTA_GRUPOS_PERMITIDOS.executar();
			}catch(ControladorException e){
				throw new ControladorException("atencao.sistemaparametro_inexistente", null, "P_EXIBIR_ROTA_GRUPOS_PERMITIDOS");
			}

			if(!gruposPermitidos.equals(ConstantesSistema.INVALIDO_ID.toString())){

				FiltroQuadra filtroQuadra = new FiltroQuadra();

				// Objetos que serão retornados pelo Hibernate
				filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ROTA);

				// coloca parametro no filtro
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, new Integer(idLocalidade)));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, new Integer(codigoSetorComercial)));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, new Integer(numeroQuadra)));

				// pesquisa
				Collection quadras = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

				Integer idRotaDaQuadra = null;
				if(!Util.isVazioOrNulo(quadras)){

					Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(quadras);

					idRotaDaQuadra = quadra.getRota().getId();
				}else{
					throw new ControladorException("atencao.quadra.inexistente");
				}

				if(!idRotaDaQuadra.equals(idRota)){

					boolean rotaAutorizada = false;

					String[] valoresGruposPermitidos = gruposPermitidos.split(",");

					for(int x = 0; x < valoresGruposPermitidos.length; x++){

						FiltroRota filtroRota = new FiltroRota();
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, valoresGruposPermitidos[x]));

						Collection<Rota> rotas = (Collection<Rota>) this.getControladorUtil().pesquisar(filtroRota, Rota.class.getName());

						if(!Util.isVazioOrNulo(rotas)){

							for(Rota rota : rotas){
								if(rota.getId().equals(idRota)){
									rotaAutorizada = true;
									break;
								}
							}

							if(rotaAutorizada){
								break;
							}
						}
					}

					if(!rotaAutorizada){
						throw new ControladorException("atencao.rota.grupo.nao_autorizada");
					}
				}
			}
		}
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @throws ControladorException
	 */
	public void atualizarImovel(Imovel imovel, Usuario usuarioLogado) throws ControladorException{

		try{
			// filtro imovel
			FiltroImovel filtroImovel = new FiltroImovel();

			// Parte de Validacao com Timestamp

			filtroImovel.limparListaParametros();

			// Seta o filtro para buscar o cliente na base
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));

			// Procura o cliente na base
			Imovel imovelNaBase = (Imovel) ((List) (getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()))).get(0);

			// Verificar se o cliente já foi atualizado por outro usuário
			// durante
			// esta atualização
			if(imovelNaBase.getUltimaAlteracao().after(imovel.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			imovel.setUltimaAlteracao(new Date());

			// [UC] - Registrar Transação
			// Início - Registrando as transações
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_ATUALIZAR, imovel.getId(),
							imovel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_IMOVEL_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			imovel.setOperacaoEfetuada(operacaoEfetuada);
			imovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(imovel);

			getControladorTransacao().registrarTransacao(imovel);
			// Fim - Registrando as transações

			// atualiza o imovel na base
			repositorioImovel.atualizarImovel(imovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * atualiza Imovel na base
	 * [UC0014] Manter Imóvel.
	 * [SB0001] Atualizar Imóvel
	 * 
	 * @author eduardo henrique
	 * @date 18/03/2009
	 *       Alteração no [SB0001] para determinação automática do Consumo mínimo da Ligação,
	 *       caso não tenha sido informado hidrômetro.
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param subcategorias
	 *            Descrição do parâmetro
	 * @param enderecoImovel
	 *            Descrição do parâmetro
	 * @param clientes
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public void atualizarImovel(Imovel imovel, Collection subcategorias, Collection consumoFaixaAreaCategoria, Collection enderecoImovel,
					Collection clientes, Collection colecaoClientesImoveisRemovidos, Collection colecaoImovelSubcategoriasRemovidas,
					Usuario usuarioLogado, boolean prepararAlteracaoInscricao) throws ControladorException{

		try{
			// filtro imovel
			FiltroImovel filtroImovel = new FiltroImovel();

			Boolean indicadorInserirDataRelacaoFim = false;
			if(ParametroCadastro.P_INDICADOR_INFORMAR_DATA_RELACAO_FIM_INSERIR_CLIENTE_IMOVEL.executar().equals(
							ConstantesSistema.SIM.toString())){
				indicadorInserirDataRelacaoFim = true;
			}

			// [UC] - Registrar Transação
			// Início - Registrando as transações
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_ATUALIZAR, imovel.getId(),
							imovel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_IMOVEL_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);
			operacaoEfetuada.setArgumentoValor(imovel.getId());

			imovel.setOperacaoEfetuada(operacaoEfetuada);
			imovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(imovel);
			// Fim - Registrando as transações

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);

			// coleção com resultado da pesquisa de imovel
			Collection imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

			// imovel encontrado
			Imovel imovelNaBase = (Imovel) imoveis.iterator().next();

			// Verificar se o imovel já foi atualizada por outro usuário durante esta atualização
			if(imovelNaBase.getUltimaAlteracao().after(imovel.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			if(indicadorInserirDataRelacaoFim){
				if(!this.verificarTipoRelacaoUsuariaAtivo(clientes)){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.data_fim_relacao_valida.cliente_imovel_usuario");
				}
			}

			this.verificarAlteracaoRota(imovel.getLocalidade().getId(), imovel.getSetorComercial().getCodigo(), imovel.getQuadra()
							.getNumeroQuadra(), imovel.getRota().getId());

			// [SB0005] – Preparar Alteração da Inscrição/Rota no Encerramento Faturamento
			if(prepararAlteracaoInscricao){
				prepararAlteracaoInscricaoNoEncerramentoFaturamento(imovel, imovelNaBase);
			}else{
				if(!Util.isVazioOuBranco(imovel.getLocalidade()) && !Util.isVazioOuBranco(imovel.getSetorComercial())
								&& !Util.isVazioOuBranco(imovel.getQuadra()) && !Util.isVazioOuBranco(imovel.getLote())
								&& !Util.isVazioOuBranco(imovel.getSubLote())){

					filtroImovel = new FiltroImovel();
					filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.ID, imovel.getId()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, imovel.getLocalidade().getId()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_CODIGO, imovel.getSetorComercial()
									.getCodigo()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_NUMERO, imovel.getQuadra().getNumeroQuadra()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOTE, imovel.getLote()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SUBLOTE, imovel.getSubLote()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, ConstantesSistema.NAO));

					imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

					if(!Util.isVazioOrNulo(imoveis)){
						Imovel imovelPesquisa = (Imovel) imoveis.iterator().next();

						throw new ControladorException("atencao.imovel_ja_cadastrado", null, imovelPesquisa.getId().toString());
					}
				}


			}


			// Atualiza a data de última alteração
			imovel.setUltimaAlteracao(new Date());

			// this.removerTodos(ClienteImovel.class.getName(), FiltroClienteImovel.IMOVEL_ID,
			// imovel.getId());

			// cliente imoveis removidos do banco
			if(colecaoClientesImoveisRemovidos != null && !colecaoClientesImoveisRemovidos.isEmpty()){

				Iterator iteratorClientesImoveisRemovidos = colecaoClientesImoveisRemovidos.iterator();
				ClienteImovel clienteImovelRemovido = null;

				while(iteratorClientesImoveisRemovidos.hasNext()){
					clienteImovelRemovido = (ClienteImovel) iteratorClientesImoveisRemovidos.next();

					FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
					filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
					filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId()));
					filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

					Collection colecaoClienteImovel = getControladorUtil().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

					boolean existe = this.verificarExistenciaClienteImovel(colecaoClienteImovel, clienteImovelRemovido);

					// atualiza apenas se já existir na base. a coleção poderia ter um registro que
					// foi adicionado (sem existir na base) e depois removido
					if(existe){
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						FiltroClienteImovel filtroClienteImovelDuplicidade = new FiltroClienteImovel();
						filtroClienteImovelDuplicidade.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel
										.getId()));
						filtroClienteImovelDuplicidade.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID,
										clienteImovelRemovido.getCliente().getId()));

						if(indicadorInserirDataRelacaoFim){
							filtroClienteImovelDuplicidade.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroClienteImovel.ID,
											clienteImovelRemovido.getId()));
						}

						if(clienteImovelRemovido.getDataInicioRelacao() == null){
							filtroClienteImovelDuplicidade.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_INICIO_RELACAO));
						}else{
							if(!indicadorInserirDataRelacaoFim){
								filtroClienteImovelDuplicidade.adicionarParametro(new ParametroSimples(
												FiltroClienteImovel.DATA_INICIO_RELACAO, simpleDateFormat.format(clienteImovelRemovido
																.getDataInicioRelacao())));
							}else{
								filtroClienteImovelDuplicidade.adicionarParametro(new MaiorQue(FiltroClienteImovel.DATA_INICIO_RELACAO,
												simpleDateFormat.format(clienteImovelRemovido.getDataInicioRelacao())));
							}
						}
						if(clienteImovelRemovido.getDataFimRelacao() == null){
							filtroClienteImovelDuplicidade.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
						}else{
							if(!indicadorInserirDataRelacaoFim){
								filtroClienteImovelDuplicidade.adicionarParametro(new ParametroSimples(
												FiltroClienteImovel.DATA_FIM_RELACAO, simpleDateFormat.format(clienteImovelRemovido
																.getDataFimRelacao())));
							}else{
								filtroClienteImovelDuplicidade.adicionarParametro(new MaiorQue(FiltroClienteImovel.DATA_FIM_RELACAO,
												simpleDateFormat.format(clienteImovelRemovido.getDataInicioRelacao())));
							}
						}
						filtroClienteImovelDuplicidade.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
										clienteImovelRemovido.getClienteRelacaoTipo().getId()));
						Collection colecaoClienteImovelDuplicidade = getControladorUtil().pesquisar(filtroClienteImovelDuplicidade,
										ClienteImovel.class.getName());

						if(colecaoClienteImovelDuplicidade != null && !colecaoClienteImovelDuplicidade.isEmpty()){
							throw new ControladorException("atencao.ja_cadastradado.cliente_imovel.data_termino", null);
						}

						// registradorOperacao.registrarOperacao(clienteImovelRemovido);

						if(imovel.getDiaVencimento() != null){

							FiltroVencimentoAlternativo filtroVencimentoAlternativo = new FiltroVencimentoAlternativo();
							filtroVencimentoAlternativo.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.IMOVEL_ID,
											imovel.getId()));
							filtroVencimentoAlternativo.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.CLIENTE_ID,
											clienteImovelRemovido.getCliente().getId()));

							Collection colecaoVencimentoAlternativo = this.getControladorUtil().pesquisar(filtroVencimentoAlternativo,
											VencimentoAlternativo.class.getName());

							if(colecaoVencimentoAlternativo != null && !colecaoVencimentoAlternativo.isEmpty()){

								Iterator iterator = colecaoVencimentoAlternativo.iterator();

								while(iterator.hasNext()){

									VencimentoAlternativo vencimentoAlternativo = (VencimentoAlternativo) iterator.next();

									if(vencimentoAlternativo.getDateExclusao() == null){

										vencimentoAlternativo.setDateExclusao(new Date());
										vencimentoAlternativo.setUltimaAlteracao(new Date());

										getControladorUtil().atualizar(vencimentoAlternativo);

									}
								}
							}
						}

						// ------------ REGISTRAR TRANSAÇÃO ----------------
						RegistradorOperacao registradorOperacaoClienteImovel = new RegistradorOperacao(
										Operacao.OPERACAO_CLIENTE_IMOVEL_ATUALIZAR, clienteImovelRemovido.getId(),
										clienteImovelRemovido.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
														UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

						Operacao operacaoClienteImovel = new Operacao();
						operacaoClienteImovel.setId(Operacao.OPERACAO_CLIENTE_IMOVEL_ATUALIZAR);

						OperacaoEfetuada operacaoEfetuadaClienteImovel = new OperacaoEfetuada();
						operacaoEfetuadaClienteImovel.setOperacao(operacaoClienteImovel);

						clienteImovelRemovido.setOperacaoEfetuada(operacaoEfetuadaClienteImovel);
						clienteImovelRemovido.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacaoClienteImovel.registrarOperacao(clienteImovelRemovido);
						// ------------ REGISTRAR TRANSAÇÃO ----------------

						clienteImovelRemovido.setUltimaAlteracao(new Date());

						getControladorUtil().atualizar(clienteImovelRemovido);
					}
				}
			}

			// clientes imoves na lista do imovel adicionar ao imovel
			if(clientes != null && !clientes.isEmpty()){

				Iterator iteratorCliente = clientes.iterator();
				ClienteImovel clienteImovel = null;

				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_IMOVEL_FIM_RELACAO_MOTIVO);
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId()));
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

				Collection colecaoClienteImovel = this.getControladorUtil().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				// atualiza ou insere o cliente imovel
				while(iteratorCliente.hasNext()){

					clienteImovel = (ClienteImovel) iteratorCliente.next();

					boolean existe = this.verificarExistenciaClienteImovel(colecaoClienteImovel, clienteImovel);

					// registradorOperacao.registrarOperacao(clienteImovel);

					if(existe){// se ja existe na base apenas atualiza para
						// o cliente imovel
						clienteImovel.setImovel(imovel);
						clienteImovel.setUltimaAlteracao(new Date());

						// ------------ REGISTRAR TRANSAÇÃO ----------------
						RegistradorOperacao registradorOperacaoClienteImovel = new RegistradorOperacao(
										Operacao.OPERACAO_CLIENTE_IMOVEL_ATUALIZAR, clienteImovel.getId(), clienteImovel.getId(),
										new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

						Operacao operacaoClienteImovel = new Operacao();
						operacaoClienteImovel.setId(Operacao.OPERACAO_CLIENTE_IMOVEL_ATUALIZAR);

						OperacaoEfetuada operacaoEfetuadaClienteImovel = new OperacaoEfetuada();
						operacaoEfetuadaClienteImovel.setOperacao(operacaoClienteImovel);

						clienteImovel.setOperacaoEfetuada(operacaoEfetuadaClienteImovel);
						clienteImovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacaoClienteImovel.registrarOperacao(clienteImovel);
						// ------------ REGISTRAR TRANSAÇÃO ----------------

						getControladorUtil().atualizar(clienteImovel);
					}else{// insere o cliente imovel par ao imovel caso não
						// exista na base
						clienteImovel.setImovel(imovel);
						clienteImovel.setUltimaAlteracao(new Date());

						// ------------ REGISTRAR TRANSAÇÃO ----------------
						RegistradorOperacao registradorOperacaoClienteImovel = new RegistradorOperacao(
										Operacao.OPERACAO_CLIENTE_IMOVEL_INSERIR, clienteImovel.getImovel().getId(), clienteImovel
														.getImovel().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
														UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

						Operacao operacaoClienteImovel = new Operacao();
						operacaoClienteImovel.setId(Operacao.OPERACAO_CLIENTE_IMOVEL_INSERIR);

						OperacaoEfetuada operacaoEfetuadaClienteImovel = new OperacaoEfetuada();
						operacaoEfetuadaClienteImovel.setOperacao(operacaoClienteImovel);

						clienteImovel.setOperacaoEfetuada(operacaoEfetuadaClienteImovel);
						clienteImovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacaoClienteImovel.registrarOperacao(clienteImovel);
						// ------------ REGISTRAR TRANSAÇÃO ----------------

						getControladorUtil().inserir(clienteImovel);

						// Transereir Débitos para o novo Cliente
						if(clienteImovel.indicadorAtualizarDebitos != null
										&& clienteImovel.indicadorAtualizarDebitos.equals(ConstantesSistema.SIM)){
							Calendar dataRelacaoCliente = Calendar.getInstance();

							// Vencimeto
							dataRelacaoCliente.setTime(clienteImovel.getDataInicioRelacao());

							Date dataVencimentoInicial = dataRelacaoCliente.getTime();
							Date dataVencimentoFinal = Util.criarData(31, 12, 9999);
							
							if(clienteImovel.getDataFimRelacao() != null){
								dataRelacaoCliente.setTime(clienteImovel.getDataFimRelacao());
								dataVencimentoFinal = dataRelacaoCliente.getTime();
							}

							// Referencia
							SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyyMM");
							dataRelacaoCliente.setTime(clienteImovel.getDataInicioRelacao());

							String dataInicioReferencia = dataFormatada.format(dataRelacaoCliente.getTime());
							String dataFimReferencia = "999912";

							if(clienteImovel.getDataFimRelacao() != null){
								dataRelacaoCliente.setTime(clienteImovel.getDataFimRelacao());
								dataFimReferencia = dataFormatada.format(dataRelacaoCliente.getTime());
							}
							
							Integer idImovelOrigemTransf = clienteImovel.getImovel().getId();
							Integer idClienteDestinoTransf = clienteImovel.getCliente().getId();
							
							List<Integer> colecaoIdsClienteOrigem = new ArrayList();
							List<Integer> colecaoIdsRelacaoClienteOrigem = new ArrayList();
							List<String> colecaoIdsContas = new ArrayList();
							List<String> colecaoIdsDebitosCobrar = new ArrayList();
							List<String> colecaoIdsCreditoARealizar = new ArrayList();
							List<String> colecaoIdsGuiasPagamento = new ArrayList();

							Collection<ClienteImovel> colecaoRelacaoImovel = new ArrayList<ClienteImovel>();
							colecaoRelacaoImovel = getControladorCobranca().obterListaClientesRelacaoDevedor(idImovelOrigemTransf, Integer.valueOf("000101"),
											Integer.valueOf("999912"), 1, 1, 1, 1, 1, 1, 1, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
											ConstantesSistema.SIM, 2, null, null);							
							
							for(ClienteImovel clienteImovelTransf : colecaoRelacaoImovel){
								if (clienteImovelTransf.getClienteRelacaoTipo().getId().equals(clienteImovel.getClienteRelacaoTipo().getId())) {
									/////////////////////////////////////////
									// Carregar variáveis transferencia
									//////////////////////////////////////////
									colecaoIdsClienteOrigem.add(clienteImovelTransf.getCliente().getId());
									colecaoIdsRelacaoClienteOrigem.add(clienteImovelTransf.getClienteRelacaoTipo().getId());
									
									ObterDebitoImovelOuClienteHelper imovelDebitoCredito = this.getControladorCobranca()
													.obterDebitoImovelOuCliente(1, imovel.getId().toString(),
																	clienteImovelTransf.getCliente().getId().toString(),
																	clienteImovelTransf.getClienteRelacaoTipo().getId(),
																	dataInicioReferencia,
																	dataFimReferencia, dataVencimentoInicial, dataVencimentoFinal, 1, 1, 1,
																	1, 1, 1, 1, true, null, null, null, null, ConstantesSistema.SIM,
																	ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null);
									
									// Carregar Ids Débitos e Crétidos
									// Carregar Contas
									String sSeparador = "";
									String sIdsContas = "";

									if(imovelDebitoCredito.getColecaoContasValoresImovel() != null){
										Iterator itColecaoContas = imovelDebitoCredito.getColecaoContasValoresImovel().iterator();
										ContaValoresHelper contaValoresHelper = null;

										while(itColecaoContas.hasNext()){
											contaValoresHelper = (ContaValoresHelper) itColecaoContas.next();
											
											sIdsContas = sIdsContas + sSeparador + contaValoresHelper.getConta().getId().toString();
											sSeparador = ",";
										}
									}	
									
									colecaoIdsContas.add(sIdsContas);
									
									
									// Carregar Debitos A Cobrar
									sSeparador = "";
									String sIdsDebitoCobrar = "";

									if(imovelDebitoCredito.getColecaoDebitoACobrar() != null){
										Iterator itColecaoDebitosCobrar = imovelDebitoCredito.getColecaoDebitoACobrar().iterator();
										DebitoACobrar debitoACobrar = null;

										while(itColecaoDebitosCobrar.hasNext()){
											debitoACobrar = (DebitoACobrar) itColecaoDebitosCobrar.next();
											
											sIdsDebitoCobrar = sIdsDebitoCobrar + sSeparador + debitoACobrar.getId().toString();
											sSeparador = ",";
										}
									}
									
									colecaoIdsDebitosCobrar.add(sIdsDebitoCobrar);
									
									
									// Carregar Credito a Realizar
									sSeparador = "";
									String sIdsCreditoRealizar = "";

									if(imovelDebitoCredito.getColecaoCreditoARealizar() != null){
										Iterator itColecaoCreditoRealizar = imovelDebitoCredito.getColecaoCreditoARealizar().iterator();
										CreditoARealizar creditoRealizar = null;

										while(itColecaoCreditoRealizar.hasNext()){
											creditoRealizar = (CreditoARealizar) itColecaoCreditoRealizar.next();
											
											sIdsCreditoRealizar = sIdsCreditoRealizar + sSeparador + creditoRealizar.getId().toString();
											sSeparador = ",";
										}
									}
									
									colecaoIdsCreditoARealizar.add(sIdsCreditoRealizar);
									
									sSeparador = "";
									String sIdsGuiaPagamentos = "";									
									
									// Carregar Guias
									if(imovelDebitoCredito.getColecaoGuiasPagamentoValores() != null){
										Iterator itColecaoGuias = imovelDebitoCredito.getColecaoGuiasPagamentoValores().iterator();
										GuiaPagamentoValoresHelper guiaPagamentoValoresHelper = null;

										while(itColecaoGuias.hasNext()){
											guiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) itColecaoGuias.next();

											FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
											filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID,
															guiaPagamentoValoresHelper.getIdGuiaPagamento()));
											Collection<GuiaPagamento> colecaoGuiaPesquisada = getControladorUtil().pesquisar(filtroGuiaPagamento,
															GuiaPagamento.class.getName());
											if(colecaoGuiaPesquisada != null && !colecaoGuiaPesquisada.isEmpty()){
												sIdsGuiaPagamentos = sIdsGuiaPagamentos + sSeparador + colecaoGuiaPesquisada.iterator().next().getId().toString();
												sSeparador = ",";
											}

										}
									}
									
									colecaoIdsCreditoARealizar.add(sIdsGuiaPagamentos);
									
								}
							}
							


							if (!colecaoIdsClienteOrigem.isEmpty() && !colecaoIdsRelacaoClienteOrigem.isEmpty() &&
											colecaoIdsClienteOrigem.size()>0 && colecaoIdsRelacaoClienteOrigem.size()>0) {
								
								this.getControladorCobranca().transferirDebitosCreditosCliente(null, idImovelOrigemTransf,
												colecaoIdsClienteOrigem, colecaoIdsRelacaoClienteOrigem, idClienteDestinoTransf,
												colecaoIdsContas, colecaoIdsDebitosCobrar, colecaoIdsCreditoARealizar,
												colecaoIdsGuiasPagamento, usuarioLogado);
								
								

							}
						}

						if(imovel.getDiaVencimento() != null){

							VencimentoAlternativo vencimentoAlternativo = new VencimentoAlternativo();

							vencimentoAlternativo.setImovel(imovel);
							vencimentoAlternativo.setDataImplantacao(new Date());
							vencimentoAlternativo.setDateVencimento(imovel.getDiaVencimento());
							vencimentoAlternativo.setUltimaAlteracao(new Date());
							vencimentoAlternativo.setCliente(clienteImovel.getCliente());

							getControladorUtil().inserir(vencimentoAlternativo);

						}
					}
				}
			}

			// remover todas os imoveis subcategorias da base
			if(colecaoImovelSubcategoriasRemovidas != null && !colecaoImovelSubcategoriasRemovidas.isEmpty()){
				Iterator iColecaoImovelSubcategoriasRemovidas = colecaoImovelSubcategoriasRemovidas.iterator();
				ImovelSubcategoria imovelSubcategoria = null;
				while(iColecaoImovelSubcategoriasRemovidas.hasNext()){
					imovelSubcategoria = (ImovelSubcategoria) iColecaoImovelSubcategoriasRemovidas.next();
					if(imovelSubcategoria.getComp_id() != null){
						// ------------ REGISTRAR TRANSAÇÃO ----------------
						RegistradorOperacao registradorOperacaoClienteImovel = new RegistradorOperacao(
										Operacao.OPERACAO_IMOVEL_SUBCATEGORIA_REMOVER, imovelSubcategoria.getComp_id().getImovel().getId(),
										imovelSubcategoria.getComp_id().getImovel().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
														UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

						Operacao operacaoClienteImovel = new Operacao();
						operacaoClienteImovel.setId(Operacao.OPERACAO_IMOVEL_SUBCATEGORIA_REMOVER);

						OperacaoEfetuada operacaoEfetuadaClienteImovel = new OperacaoEfetuada();
						operacaoEfetuadaClienteImovel.setOperacao(operacaoClienteImovel);

						imovelSubcategoria.setOperacaoEfetuada(operacaoEfetuadaClienteImovel);
						imovelSubcategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacaoClienteImovel.registrarOperacao(imovelSubcategoria);
						// ------------ REGISTRAR TRANSAÇÃO ----------------

						getControladorUtil().remover(imovelSubcategoria);

					}
				}
			}

			// imovel sub categoria
			if(subcategorias != null && !subcategorias.isEmpty()){

				Iterator iteratorImovelSubCategorias = subcategorias.iterator();
				ImovelSubcategoria imovelSubcategoria = null;

				// FiltroImovelSubCategoria

				FiltroImovelSubCategoria filtroImovelSubcategoria = new FiltroImovelSubCategoria();
				filtroImovelSubcategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.SUBCATEGORIA);
				filtroImovelSubcategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));

				Collection colecaoImovelSubcategoria = getControladorUtil().pesquisar(filtroImovelSubcategoria,
								ImovelSubcategoria.class.getName());

				// atualiza ou insere o imovel sub categoria
				while(iteratorImovelSubCategorias.hasNext()){

					imovelSubcategoria = null;
					imovelSubcategoria = (ImovelSubcategoria) iteratorImovelSubCategorias.next();

					boolean existe = this.verificarExistenciaImovelSubcategoria(colecaoImovelSubcategoria, imovelSubcategoria);

					// registradorOperacao.registrarOperacao(imovelSubcategoria);

					if(existe){// se ja existe na base apenas atualiza para
						// o imovel sub categoria
						imovelSubcategoria.getComp_id().setImovel(imovel);
						imovelSubcategoria.setUltimaAlteracao(new Date());

						// ------------ REGISTRAR TRANSAÇÃO ----------------
						RegistradorOperacao registradorOperacaoImovelSubcategoria = new RegistradorOperacao(
										Operacao.OPERACAO_IMOVEL_SUBCATEGORIA_ATUALIZAR, imovelSubcategoria.getComp_id().getImovel()
														.getId(), imovelSubcategoria.getComp_id().getImovel().getId(),
										new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

						Operacao operacaoImovelSubcategoria = new Operacao();
						operacaoImovelSubcategoria.setId(Operacao.OPERACAO_IMOVEL_SUBCATEGORIA_ATUALIZAR);

						OperacaoEfetuada operacaoEfetuadaImovelSubcategoria = new OperacaoEfetuada();
						operacaoEfetuadaImovelSubcategoria.setOperacao(operacaoImovelSubcategoria);

						imovelSubcategoria.setOperacaoEfetuada(operacaoEfetuadaImovelSubcategoria);
						imovelSubcategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacaoImovelSubcategoria.registrarOperacao(imovelSubcategoria);
						// ------------ REGISTRAR TRANSAÇÃO ----------------

						this.atualizarImovelSubCategoria(imovelSubcategoria);
					}else{// insere o imovel sub categoria para o imovel caso
							// não exista na base
						imovelSubcategoria.getComp_id().setImovel(imovel);
						imovelSubcategoria.setUltimaAlteracao(new Date());
						// getControladorUtil().inserir(imovelSubcategoria);

						// ------------ REGISTRAR TRANSAÇÃO ----------------
						// RegistradorOperacao registradorOperacaoImovelSubcategoria = new
						// RegistradorOperacao(
						// Operacao.OPERACAO_IMOVEL_SUBCATEGORIA_INSERIR,
						// imovelSubcategoria.getComp_id().getImovel().getId(),
						// imovelSubcategoria.getComp_id().getImovel().getId(), new
						// UsuarioAcaoUsuarioHelper(usuarioLogado,
						// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
						//
						// Operacao operacaoImovelSubcategoria = new Operacao();
						// operacaoImovelSubcategoria.setId(Operacao.OPERACAO_IMOVEL_SUBCATEGORIA_INSERIR);
						//
						// OperacaoEfetuada operacaoEfetuadaImovelSubcategoria = new
						// OperacaoEfetuada();
						// operacaoEfetuadaImovelSubcategoria.setOperacao(operacaoImovelSubcategoria);
						//
						// imovelSubcategoria.setOperacaoEfetuada(operacaoEfetuadaImovelSubcategoria);
						// imovelSubcategoria.adicionarUsuario(usuarioLogado,
						// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						// registradorOperacaoImovelSubcategoria.registrarOperacao(imovelSubcategoria);
						// // ------------ REGISTRAR TRANSAÇÃO ----------------

						this.inserirImovelSubCategoria(imovelSubcategoria, usuarioLogado);
					}
				}
			}else{
				throw new ControladorException("atencao.required", null, " a Subcategoria do Imóvel");
			}

			// INÍCIO -------- Imovel Categoria Grupo Usuário --------

			// Desativa todos os registros filtrando pelo Imóvel
			FiltroImovelConsumoFaixaAreaCategoria filtroImovelConsumoFaixaAreaCategoria = new FiltroImovelConsumoFaixaAreaCategoria();
			filtroImovelConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CATEGORIA);
			filtroImovelConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL);
			filtroImovelConsumoFaixaAreaCategoria
							.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CONSUMO_FAIXA_AREA_CATEGORIA);
			filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL_ID,
							imovel.getId()));
			filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(
							FiltroImovelConsumoFaixaAreaCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoImovelConsumoFaixaAreaCategoria = (Collection) getControladorUtil().pesquisar(
							filtroImovelConsumoFaixaAreaCategoria, ImovelConsumoFaixaAreaCategoria.class.getName());

			if(colecaoImovelConsumoFaixaAreaCategoria != null && !colecaoImovelConsumoFaixaAreaCategoria.isEmpty()){
				Iterator iteratorImovelConsumoFaixaAreaCategoria = colecaoImovelConsumoFaixaAreaCategoria.iterator();
				ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = null;

				while(iteratorImovelConsumoFaixaAreaCategoria.hasNext()){
					imovelConsumoFaixaAreaCategoria = null;
					imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) iteratorImovelConsumoFaixaAreaCategoria.next();
					imovelConsumoFaixaAreaCategoria.setUltimaAlteracao(new Date());
					imovelConsumoFaixaAreaCategoria.setIndicadorUso(ConstantesSistema.INDICADOR_USO_DESATIVO);

					// ------------ REGISTRAR TRANSAÇÃO ----------------
					RegistradorOperacao registradorOperacaoImovelConsumoFaixaAreaCategoria = new RegistradorOperacao(
									Operacao.OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, imovelConsumoFaixaAreaCategoria
													.getComp_id().getImovel().getId(), imovelConsumoFaixaAreaCategoria.getComp_id()
													.getImovel().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
													UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
					Operacao operacaoImovelConsumoFaixaAreaCategoria = new Operacao();
					operacaoImovelConsumoFaixaAreaCategoria.setId(Operacao.OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR);

					OperacaoEfetuada operacaoEfetuadaImovelConsumoFaixaAreaCategoria = new OperacaoEfetuada();
					operacaoEfetuadaImovelConsumoFaixaAreaCategoria.setOperacao(operacaoImovelConsumoFaixaAreaCategoria);

					imovelConsumoFaixaAreaCategoria.setOperacaoEfetuada(operacaoEfetuadaImovelConsumoFaixaAreaCategoria);
					imovelConsumoFaixaAreaCategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacaoImovelConsumoFaixaAreaCategoria.registrarOperacao(imovelConsumoFaixaAreaCategoria);
					// ------------ REGISTRAR TRANSAÇÃO ----------------

					getControladorUtil().atualizar(imovelConsumoFaixaAreaCategoria);
				}
			}

			// Processa todos os registros da coleção
			filtroImovelConsumoFaixaAreaCategoria.limparListaParametros();
			filtroImovelConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CATEGORIA);
			filtroImovelConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL);
			filtroImovelConsumoFaixaAreaCategoria
							.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CONSUMO_FAIXA_AREA_CATEGORIA);
			filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL_ID,
							imovel.getId()));

			colecaoImovelConsumoFaixaAreaCategoria = (Collection) getControladorUtil().pesquisar(filtroImovelConsumoFaixaAreaCategoria,
							ImovelConsumoFaixaAreaCategoria.class.getName());

			if(consumoFaixaAreaCategoria != null && !consumoFaixaAreaCategoria.isEmpty()){
				Iterator iteratorImovelConsumoFaixaAreaCategoria = consumoFaixaAreaCategoria.iterator();
				ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = null;

				while(iteratorImovelConsumoFaixaAreaCategoria.hasNext()){
					imovelConsumoFaixaAreaCategoria = null;
					imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) iteratorImovelConsumoFaixaAreaCategoria.next();
					imovelConsumoFaixaAreaCategoria.getComp_id().setImovel(imovel);
					imovelConsumoFaixaAreaCategoria.setUltimaAlteracao(new Date());
					imovelConsumoFaixaAreaCategoria.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

					int idConsumoFaixaAreaCategoria = imovelConsumoFaixaAreaCategoria.getComp_id().getConsumoFaixaAreaCategoria().getId();
					boolean existe = this.verificarExistenciaImovelConsumoFaixaAreaCategoria(colecaoImovelConsumoFaixaAreaCategoria,
									idConsumoFaixaAreaCategoria);

					if(existe){

						// ------------ REGISTRAR TRANSAÇÃO ----------------
						RegistradorOperacao registradorOperacaoImovelConsumoFaixaAreaCategoria = new RegistradorOperacao(
										Operacao.OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, imovelConsumoFaixaAreaCategoria
														.getComp_id().getImovel().getId(), imovelConsumoFaixaAreaCategoria.getComp_id()
														.getImovel().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
														UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

						Operacao operacaoImovelConsumoFaixaAreaCategoria = new Operacao();
						operacaoImovelConsumoFaixaAreaCategoria.setId(Operacao.OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR);

						OperacaoEfetuada operacaoEfetuadaImovelConsumoFaixaAreaCategoria = new OperacaoEfetuada();
						operacaoEfetuadaImovelConsumoFaixaAreaCategoria.setOperacao(operacaoImovelConsumoFaixaAreaCategoria);

						imovelConsumoFaixaAreaCategoria.setOperacaoEfetuada(operacaoEfetuadaImovelConsumoFaixaAreaCategoria);
						imovelConsumoFaixaAreaCategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacaoImovelConsumoFaixaAreaCategoria.registrarOperacao(imovelConsumoFaixaAreaCategoria);
						// ------------ REGISTRAR TRANSAÇÃO ----------------

						getControladorUtil().atualizar(imovelConsumoFaixaAreaCategoria);
					}else{

						// ------------ REGISTRAR TRANSAÇÃO ----------------
						RegistradorOperacao registradorOperacaoImovelConsumoFaixaAreaCategoria = new RegistradorOperacao(
										Operacao.OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR, imovelConsumoFaixaAreaCategoria
														.getComp_id().getImovel().getId(), imovelConsumoFaixaAreaCategoria.getComp_id()
														.getImovel().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
														UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

						Operacao operacaoImovelConsumoFaixaAreaCategoria = new Operacao();
						operacaoImovelConsumoFaixaAreaCategoria.setId(Operacao.OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR);

						OperacaoEfetuada operacaoEfetuadaImovelConsumoFaixaAreaCategoria = new OperacaoEfetuada();
						operacaoEfetuadaImovelConsumoFaixaAreaCategoria.setOperacao(operacaoImovelConsumoFaixaAreaCategoria);

						imovelConsumoFaixaAreaCategoria.setOperacaoEfetuada(operacaoEfetuadaImovelConsumoFaixaAreaCategoria);
						imovelConsumoFaixaAreaCategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						registradorOperacaoImovelConsumoFaixaAreaCategoria.registrarOperacao(imovelConsumoFaixaAreaCategoria);
						// ------------ REGISTRAR TRANSAÇÃO ----------------

						getControladorUtil().inserir(imovelConsumoFaixaAreaCategoria);
					}
				}
			}

			// FIM -------- Imovel Categoria Grupo Usuário --------

			// Verificação da Atualização de Consumo Fixo para imóveis sem Hidrômetro na Água
			if(imovel.getNumeroPontosUtilizacao() != null){
				FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
				filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico");
				filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, imovel.getId()));
				Collection<LigacaoAgua> colecaoLigacaoAgua = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());
				if(colecaoLigacaoAgua != null && !colecaoLigacaoAgua.isEmpty()){
					LigacaoAgua ligacaoAguaImovel = colecaoLigacaoAgua.iterator().next();
					ligacaoAguaImovel.setImovel(imovel);
					ligacaoAguaImovel.setUltimaAlteracao(new Date());
					getControladorAtendimentoPublico().determinarConsumoMinimoNovaLigacaoAgua(ligacaoAguaImovel);

					// ------------ REGISTRAR TRANSAÇÃO ----------------
					RegistradorOperacao registradorOperacaoLigacaoAgua = new RegistradorOperacao(Operacao.OPERACAO_LIGACAO_AGUA_EFETUAR,
									ligacaoAguaImovel.getId(), ligacaoAguaImovel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
													UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					Operacao operacaoLigacaoAgua = new Operacao();
					operacaoLigacaoAgua.setId(Operacao.OPERACAO_LIGACAO_AGUA_EFETUAR);

					OperacaoEfetuada operacaoEfetuadaLigacaoAgua = new OperacaoEfetuada();
					operacaoEfetuadaLigacaoAgua.setOperacao(operacaoLigacaoAgua);

					ligacaoAguaImovel.setOperacaoEfetuada(operacaoEfetuadaLigacaoAgua);
					ligacaoAguaImovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacaoLigacaoAgua.registrarOperacao(ligacaoAguaImovel);
					// ------------ REGISTRAR TRANSAÇÃO ----------------

					getControladorUtil().atualizar(ligacaoAguaImovel);
				}
			}

			// alterado por pedro alexandre dia 17/11/2006 alteração feita para acoplar o controle
			// de abrangência de usuário
			// ------------ CONTROLE DE ABRANGENCIA ----------------
			Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovel);
			// ------------ CONTROLE DE ABRANGENCIA ----------------

			if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.acesso.negado.abrangencia");
			}else{
				// atualiza o imovel na base
				repositorioImovel.atualizarImovel(imovel);
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;
		}
	}
	
	/**
	 * @author Gicevalter Couto
	 * @date 17/09/2014
	 */
	public boolean verificarTipoRelacaoUsuariaAtivo(Collection<ClienteImovel> colecaoClientesImovel) throws ControladorException{

		boolean retorno = false;
		Calendar dataCorrente = Calendar.getInstance();
		Calendar dataFimRelacao = Calendar.getInstance();

		dataCorrente.set(Calendar.SECOND, 0);
		dataCorrente.set(Calendar.MILLISECOND, 0);
		dataCorrente.set(Calendar.HOUR, 0);
		dataCorrente.set(Calendar.HOUR_OF_DAY, 0);
		dataCorrente.set(Calendar.MINUTE, 0);

		for(ClienteImovel clienteImovel : colecaoClientesImovel){
			if(clienteImovel.getClienteRelacaoTipo().getId().equals(ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO)){
				if(clienteImovel.getDataFimRelacao() == null){
					retorno = true;
				}else{
					dataFimRelacao.setTime(clienteImovel.getDataFimRelacao());
					dataFimRelacao.set(Calendar.SECOND, 0);
					dataFimRelacao.set(Calendar.MILLISECOND, 0);
					dataFimRelacao.set(Calendar.HOUR, 0);
					dataFimRelacao.set(Calendar.HOUR_OF_DAY, 0);
					dataFimRelacao.set(Calendar.MINUTE, 0);

					if(dataFimRelacao.after(dataCorrente) || dataFimRelacao.equals(dataCorrente)){
						retorno = true;
					}
				}
			}
		}

		return retorno;
	}

	private boolean verificarExistenciaImovelConsumoFaixaAreaCategoria(Collection colecaoImovelConsumoFaixaAreaCategoria,
					int idConsumoFaixaAreaCategoria){

		boolean retorno = false;

		if(colecaoImovelConsumoFaixaAreaCategoria != null && !colecaoImovelConsumoFaixaAreaCategoria.isEmpty()){
			Iterator iteratorImovelConsumoFaixaAreaCategoria = colecaoImovelConsumoFaixaAreaCategoria.iterator();
			ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = null;

			while(iteratorImovelConsumoFaixaAreaCategoria.hasNext()){
				imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) iteratorImovelConsumoFaixaAreaCategoria.next();

				ImovelConsumoFaixaAreaCategoriaPK compId = imovelConsumoFaixaAreaCategoria.getComp_id();
				if(compId.getConsumoFaixaAreaCategoria().getId() == idConsumoFaixaAreaCategoria){
					retorno = true;
					break;
				}
			}
		}

		return retorno;
	}

	/**
	 * Verifica se o ClienteImovel existe na Coleção do Cliente Imovel do Imovel
	 * Author: Rafael Santos Data: 01/02/2006
	 * 
	 * @param colecaoClienteImovel
	 *            Coleção Cliente Imovel
	 * @param clienteImovel
	 *            Cliente Imovel
	 * @return true se o cliente imovel existe, false caso contrário
	 */
	public boolean verificarExistenciaClienteImovel(Collection colecaoClienteImovel, ClienteImovel clienteImovel){

		boolean retorno = false;
		boolean achou = false;
		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

			Iterator iColecaoClienteImovel = colecaoClienteImovel.iterator();

			while(iColecaoClienteImovel.hasNext() && !achou){
				ClienteImovel clienteImovelNaBase = (ClienteImovel) iColecaoClienteImovel.next();

				if(clienteImovel.getId() != null && clienteImovelNaBase.getId().intValue() == clienteImovel.getId().intValue()){
					retorno = true;
					achou = true;
				}

			}

		}
		return retorno;
	}

	/**
	 * Verifica se ImovelSubCategoria existe na Coleção de SubCategoria do
	 * Imovel Author: Rafael Santos Data: 01/02/2006
	 * 
	 * @param colecaoSubCategoria
	 *            Coleção Imovel Sub Categoria
	 * @param imovelSubcategoria
	 *            ImovelSubcategoria
	 * @return true se a Imovel SubCategoria existe, false caso contrário
	 */
	public boolean verificarExistenciaImovelSubcategoria(Collection colecaoImovelSubCategoria, ImovelSubcategoria imovelSubcategoria){

		boolean retorno = false;
		boolean achou = false;
		if(colecaoImovelSubCategoria != null && !colecaoImovelSubCategoria.isEmpty()){

			Iterator iColecaoImovelSubCategoria = colecaoImovelSubCategoria.iterator();

			while(iColecaoImovelSubCategoria.hasNext() && !achou){
				ImovelSubcategoria ImovolSubCategoriaNaBase = (ImovelSubcategoria) iColecaoImovelSubCategoria.next();

				if(ImovolSubCategoriaNaBase.getComp_id().getSubcategoria().getId().intValue() == imovelSubcategoria.getComp_id()
								.getSubcategoria().getId().intValue()){
					retorno = true;
					achou = true;
				}

			}

		}
		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovelSubcategoria
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public void inserirImovelSubCategoria(ImovelSubcategoria imovelSubcategoria, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_SUBCATEGORIA_INSERIR, imovelSubcategoria
						.getComp_id().getImovel().getId(), imovelSubcategoria.getComp_id().getImovel().getId(),
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_IMOVEL_SUBCATEGORIA_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setArgumentoValor(imovelSubcategoria.getComp_id().getImovel().getId());
		imovelSubcategoria.setOperacaoEfetuada(operacaoEfetuada);
		imovelSubcategoria.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(imovelSubcategoria);

		// ------------ REGISTRAR TRANSAÇÃO ----------------

		getControladorUtil().inserir(imovelSubcategoria);

	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovelSubcategoria
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public void atualizarImovelSubCategoria(ImovelSubcategoria imovelSubcategoria) throws ControladorException{

		try{
			repositorioImovel.atualizarImovelSubCategoria(imovelSubcategoria);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param objeto
	 *            Descrição do parâmetro
	 * @param condicional
	 *            Descrição do parâmetro
	 * @param id
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public void removerTodos(String objeto, String condicional, Integer id) throws ControladorException{

		try{
			repositorioImovel.removerTodos(objeto, condicional, id);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public Collection obterQuantidadeEconomiasCategoria(Imovel imovel) throws ControladorException{

		return obterQuantidadeEconomiasCategoria(imovel.getId(), true);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public Collection obterQuantidadeEconomiasContaCategoria(Conta conta) throws ControladorException{

		return obterQuantidadeEconomiasContaCategoria(conta.getId());
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @author eduardo henrique
	 * @date 03/09/2009
	 *       Alteracao na consulta para busca da CategoriaTipo
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public Collection<Categoria> obterQuantidadeEconomiasCategoria(Integer imovel, boolean construirObjetoCompleto)
					throws ControladorException{

		// Criação das coleções
		Collection<Categoria> colecaoCategoria = new ArrayList<Categoria>();
		Collection colecaoImovelSubCategoriaArray = null;

		try{
			colecaoImovelSubCategoriaArray = repositorioImovel.pesquisarObterQuantidadeEconomiasCategoria(imovel, construirObjetoCompleto);
		}catch(ErroRepositorioException ex){
			// sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovelSubCategoriaArray != null && !colecaoImovelSubCategoriaArray.isEmpty()){

			Iterator colecaoImovelSubCategoriaArrayIterator = colecaoImovelSubCategoriaArray.iterator();

			while(colecaoImovelSubCategoriaArrayIterator.hasNext()){

				// Obtém o imóvel subcategoria
				Object[] imovelSubcategoriaArray = (Object[]) colecaoImovelSubCategoriaArrayIterator.next();

				// Cria os objetos categoria
				Categoria categoria = new Categoria();

				// Seta a categoria
				categoria.setId((Integer) imovelSubcategoriaArray[0]);

				if(construirObjetoCompleto){

					// Seta a descrição
					categoria.setDescricao(String.valueOf(imovelSubcategoriaArray[1]));

					// Seta o consumo estouro
					categoria.setConsumoEstouro((Integer) imovelSubcategoriaArray[2]);
					// Seta número de vezes média estouro
					categoria.setVezesMediaEstouro((BigDecimal) imovelSubcategoriaArray[3]);
					// Seta a quantidade de economias por categoria
					categoria.setQuantidadeEconomiasCategoria(((Number) imovelSubcategoriaArray[4]).intValue());
					// Seta o consumo alto
					categoria.setConsumoAlto((Integer) imovelSubcategoriaArray[6]);

					// Seta a média baixo consumo
					categoria.setMediaBaixoConsumo((Integer) imovelSubcategoriaArray[7]);
					// Seta o número de vezes média consumo alto
					categoria.setVezesMediaAltoConsumo((BigDecimal) imovelSubcategoriaArray[8]);
					// Seta o percentual da média baixo consumo
					categoria.setPorcentagemMediaBaixoConsumo((BigDecimal) imovelSubcategoriaArray[9]);

					// Seta a descricao abreviada
					if((String) imovelSubcategoriaArray[10] != null){
						categoria.setDescricaoAbreviada((String) imovelSubcategoriaArray[10]);
					}
					categoria.setNumeroConsumoMaximoEc((Integer) imovelSubcategoriaArray[11]);

					categoria.setIndicadorCobrancaAcrescimos((Short) imovelSubcategoriaArray[12]);

					if(imovelSubcategoriaArray[13] != null){
						CategoriaTipo categoriaTipo = new CategoriaTipo();
						categoriaTipo.setId((Integer) imovelSubcategoriaArray[13]);

						categoria.setCategoriaTipo(categoriaTipo);
					}

					categoria.setConsumoMedioEconomiaMes((Integer) imovelSubcategoriaArray[14]);

					categoria.setConsumoMinimo((Integer) imovelSubcategoriaArray[15]);
					categoria.setQuantidadeMaximoEconomiasValidacao((Integer) imovelSubcategoriaArray[16]);
					categoria.setConsumoViradaHidrometro((Integer) imovelSubcategoriaArray[17]);
					categoria.setNumeroVezesMediaViradaHidrometro((Integer) imovelSubcategoriaArray[18]);
					categoria.setIndicadorValidarViradaHidrometro((Short) imovelSubcategoriaArray[19]);
				}else{

					// Seta a quantidade de economias por categoria
					categoria.setQuantidadeEconomiasCategoria(((Number) imovelSubcategoriaArray[1]).intValue());
				}


				colecaoCategoria.add(categoria);
			}

		}else{
			// Caso a coleção não tenha retornado objetos
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.nao_cadastrado.imovel_subcategoria", null);
		}

		return colecaoCategoria;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public Collection obterQuantidadeEconomiasContaCategoria(Integer conta) throws ControladorException{

		// Criação das coleções
		Collection colecaoCategoria = new ArrayList();
		Collection colecaoContaCategoriaArray = null;

		try{
			colecaoContaCategoriaArray = repositorioImovel.obterQuantidadeEconomiasCategoria(conta);
		}catch(ErroRepositorioException ex){
			// sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoContaCategoriaArray != null && !colecaoContaCategoriaArray.isEmpty()){

			Iterator colecaoContaCategoriaArrayIterator = colecaoContaCategoriaArray.iterator();

			while(colecaoContaCategoriaArrayIterator.hasNext()){

				// Obtém o imóvel subcategoria
				Object[] contaCategoriaArray = (Object[]) colecaoContaCategoriaArrayIterator.next();

				// Cria os objetos categoria
				Categoria categoria = new Categoria();

				// Seta a categoria
				categoria.setId((Integer) contaCategoriaArray[0]);

				// Seta a descrição
				categoria.setDescricao(String.valueOf(contaCategoriaArray[1]));

				// Seta o consumo estouro
				categoria.setConsumoEstouro((Integer) contaCategoriaArray[2]);
				// Seta número de vezes média estouro
				categoria.setVezesMediaEstouro((BigDecimal) contaCategoriaArray[3]);
				// Seta a quantidade de economias por categoria
				categoria.setQuantidadeEconomiasCategoria(((Number) contaCategoriaArray[4]).intValue());
				// Seta o consumo alto
				categoria.setConsumoAlto((Integer) contaCategoriaArray[6]);
				// Seta a média baixo consumo
				categoria.setMediaBaixoConsumo((Integer) contaCategoriaArray[7]);
				// Seta o número de vezes média consumo alto
				categoria.setVezesMediaAltoConsumo((BigDecimal) contaCategoriaArray[8]);
				// Seta o percentual da média baixo consumo
				categoria.setPorcentagemMediaBaixoConsumo((BigDecimal) contaCategoriaArray[9]);
				// Seta a descricao abreviada
				if((String) contaCategoriaArray[10] != null){
					categoria.setDescricaoAbreviada((String) contaCategoriaArray[10]);
				}

				colecaoCategoria.add(categoria);
			}

		}else{
			// Caso a coleção não tenha retornado objetos
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.nao_cadastrado.conta_categoria", null);
		}

		return colecaoCategoria;
	}

	/**
	 * Metodo que verifica se ja tem um imovel ou um imovel economia com o
	 * numero do iptu passado caso a pessoa passe o idSetorComercial verifica
	 * apenas no mesmo municipio
	 * 
	 * @param numeroIptu
	 * @param idSetorComercial
	 * @throws ControladorException
	 */
	public void verificarExistenciaIPTU(String numeroIptu, Integer idSetorComercial) throws ControladorException{

		Collection setorComerciais = null;
		if(idSetorComercial != null){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, Integer.valueOf(idSetorComercial)));
			filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorComercial.MUNICIPIO);
			setorComerciais = getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());
		}

		FiltroImovel filtroImovel = new FiltroImovel();
		if(setorComerciais != null && !setorComerciais.isEmpty()){
			SetorComercial setorComercial = (SetorComercial) setorComerciais.iterator().next();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_MUNICIPIO_ID, setorComercial.getMunicipio()
							.getId()));
		}
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.IPTU, numeroIptu));

		Collection imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		if(imoveis != null && !imoveis.isEmpty()){
			String idMatricula = "" + ((Imovel) ((List) imoveis).get(0)).getId();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.imovel.iptu_jacadastrado", null, idMatricula);
		}

		FiltroImovelEconomia filtroImovelEconomia = new FiltroImovelEconomia();
		// filtroImovelEconomia.adicionarParametro(new
		// ParametroSimples(FiltroImovelEconomia.IMOVEL_ID, imovel.getId()));
		if(setorComerciais != null && !setorComerciais.isEmpty()){
			SetorComercial setorComercial = (SetorComercial) setorComerciais.iterator().next();
			filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.MUNICIPIO_ID, setorComercial.getMunicipio()
							.getId()));
		}
		filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.IPTU, numeroIptu));
		filtroImovelEconomia.adicionarCaminhoParaCarregamentoEntidade("imovelSubcategoria");
		filtroImovelEconomia.adicionarCaminhoParaCarregamentoEntidade("imovelSubcategoria.comp_id.imovel");

		Collection imoveisEconomiaPesquisadas = getControladorUtil().pesquisar(filtroImovelEconomia, ImovelEconomia.class.getName());

		if(!imoveisEconomiaPesquisadas.isEmpty()){
			ImovelEconomia imovelEconomia = (ImovelEconomia) ((List) imoveisEconomiaPesquisadas).get(0);

			String idMatricula = "" + imovelEconomia.getImovelSubcategoria().getComp_id().getImovel().getId();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.imovel.iptu_jacadastrado", null, idMatricula);
		}
	}

	/**
	 * Metodo que verifica se ja tem um imovel ou um imovel economia com o
	 * numero do iptu passado caso a pessoa passe o idSetorComercial verifica
	 * apenas no mesmo municipio
	 * 
	 * @param numeroIptu
	 * @param idSetorComercial
	 * @throws ControladorException
	 */
	public void verificarExistenciaCELPE(String numeroCelp, Integer idSetorComercial) throws ControladorException{

		Collection setorComerciais = null;
		if(idSetorComercial != null){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, Integer.valueOf(idSetorComercial)));
			filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorComercial.MUNICIPIO);
			setorComerciais = getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());
		}

		FiltroImovel filtroImovel = new FiltroImovel();
		if(setorComerciais != null && !setorComerciais.isEmpty()){
			SetorComercial setorComercial = (SetorComercial) setorComerciais.iterator().next();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_MUNICIPIO_ID, setorComercial.getMunicipio()
							.getId()));
		}
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.NUMERO_CELPE, numeroCelp));

		Collection imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		if(imoveis != null && !imoveis.isEmpty()){
			String idMatricula = "" + ((Imovel) ((List) imoveis).get(0)).getId();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.imovel.numero_celpe_jacadastrado", null, idMatricula);
		}

		FiltroImovelEconomia filtroImovelEconomia = new FiltroImovelEconomia();
		// filtroImovelEconomia.adicionarParametro(new
		// ParametroSimples(FiltroImovelEconomia.IMOVEL_ID, imovel.getId()));
		if(setorComerciais != null && !setorComerciais.isEmpty()){
			SetorComercial setorComercial = (SetorComercial) setorComerciais.iterator().next();
			filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.MUNICIPIO_ID, setorComercial.getMunicipio()
							.getId()));
		}
		filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.NUMERO_CELPE, numeroCelp));
		filtroImovelEconomia.adicionarCaminhoParaCarregamentoEntidade("imovelSubcategoria");
		filtroImovelEconomia.adicionarCaminhoParaCarregamentoEntidade("imovelSubcategoria.comp_id.imovel");

		Collection imoveisEconomiaPesquisadas = getControladorUtil().pesquisar(filtroImovelEconomia, ImovelEconomia.class.getName());

		if(!imoveisEconomiaPesquisadas.isEmpty()){
			ImovelEconomia imovelEconomia = (ImovelEconomia) ((List) imoveisEconomiaPesquisadas).get(0);

			String idMatricula = "" + imovelEconomia.getImovelSubcategoria().getComp_id().getImovel().getId();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.imovel.numero_celpe_jacadastrado", null, idMatricula);
		}
	}

	/**
	 * verifica se existe algum iptu no imovel ou imovelEconomia
	 * 
	 * @param imoveisEconomia
	 *            Description of the Parameter
	 * @param imovel
	 *            Description of the Parameter
	 * @param numeroIptu
	 *            Description of the Parameter
	 * @param dataUltimaAlteracao
	 *            Description of the Parameter
	 * @throws ControladorException
	 */
	public void verificarExistenciaIPTU(Collection imoveisEconomia, Imovel imovel, String numeroIptu, Date dataUltimaAlteracao)
					throws ControladorException{

		// Cria Filtros
		FiltroImovel filtroImovel = new FiltroImovel();

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_MUNICIPIO_ID, imovel.getSetorComercial()
						.getMunicipio().getId()));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.IPTU, numeroIptu));

		Collection imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		if(imoveis != null && !imoveis.isEmpty()){
			String idMatricula = "" + ((Imovel) ((List) imoveis).get(0)).getId();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.imovel.iptu_jacadastrado", null, idMatricula);
		}

		// comparando com a data diferente de null
		if(dataUltimaAlteracao != null){

			FiltroImovelEconomia filtroImovelEconomia = new FiltroImovelEconomia();

			filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.IMOVEL_ID, imovel.getId()));
			filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.MUNICIPIO_ID, imovel.getSetorComercial()
							.getMunicipio().getId()));
			filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.IPTU, numeroIptu));

			filtroImovelEconomia.adicionarCaminhoParaCarregamentoEntidade("imovelSubcategoria.comp_id.imovel");

			Collection imoveisEconomiaPesquisadas = getControladorUtil().pesquisar(filtroImovelEconomia, ImovelEconomia.class.getName());

			if(!imoveisEconomiaPesquisadas.isEmpty()){
				ImovelEconomia imovelEconomia = (ImovelEconomia) ((List) imoveisEconomiaPesquisadas).get(0);

				if(imovelEconomia.getUltimaAlteracao().getTime() != dataUltimaAlteracao.getTime()){
					String idMatricula = "" + imovelEconomia.getImovelSubcategoria().getComp_id().getImovel().getId();

					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.imovel.iptu_jacadastrado", null, idMatricula);
				}
			}
		}

		if(imoveisEconomia != null){
			Iterator imovelEconomiaIterator = imoveisEconomia.iterator();

			while(imovelEconomiaIterator.hasNext()){

				ImovelEconomia imovelEconomia = (ImovelEconomia) imovelEconomiaIterator.next();

				// caso seja o mesmo imovel economia não faça a verificação
				if(imovelEconomia.getUltimaAlteracao().getTime() != dataUltimaAlteracao.getTime()){
					// verifica se o numero da iptu que veio do imovel economia
					// é
					// diferente de nulo.
					if(imovelEconomia.getNumeroIptu() != null && !imovelEconomia.getNumeroIptu().equals("")){

						// se o numero da iptu do imovel economia for diferente
						// de
						// nulo então é verificado se o numero da iptu
						// que o veio do form é igual ao do objeto imovel
						// economia
						// cadastrado.
						if(imovelEconomia.getNumeroIptu().equals(new BigDecimal(numeroIptu))){

							sessionContext.setRollbackOnly();
							throw new ControladorException("atencao.imovel.iptu_jacadastrado", null, imovelEconomia.getImovelSubcategoria()
											.getComp_id().getImovel().getId().toString());
						}
					}
				}
				// caso o imvel economia
			}
		}else{

		}

	}

	/**
	 * verifica se existe algum numero da celpe no imovel ou imovelEconomia
	 * 
	 * @param imoveisEconomia
	 *            Description of the Parameter
	 * @param imovel
	 *            Description of the Parameter
	 * @param numeroCelpe
	 *            Description of the Parameter
	 * @param dataUltimaAlteracao
	 *            Description of the Parameter
	 * @throws ControladorException
	 */
	public void verificarExistenciaCelpe(Collection imoveisEconomia, Imovel imovel, String numeroCelpe, Date dataUltimaAlteracao)
					throws ControladorException{

		// Cria Filtros
		FiltroImovel filtroImovel = new FiltroImovel();

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.NUMERO_CELPE, numeroCelpe));

		Collection imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		if(!imoveis.isEmpty()){
			String idMatricula = "" + ((Imovel) ((List) imoveis).get(0)).getId();

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.imovel.numero_celpe_jacadastrado", null, idMatricula);
		}

		if(dataUltimaAlteracao != null){

			FiltroImovelEconomia filtroImovelEconomia = new FiltroImovelEconomia();

			filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.IMOVEL_ID, imovel.getId()));
			filtroImovelEconomia.adicionarParametro(new ParametroSimples(FiltroImovelEconomia.NUMERO_CELPE, numeroCelpe));

			filtroImovelEconomia.adicionarCaminhoParaCarregamentoEntidade("imovelSubcategoria.comp_id.imovel");

			Collection imoveisEconomiaPesquisadas = getControladorUtil().pesquisar(filtroImovelEconomia, ImovelEconomia.class.getName());

			if(!imoveisEconomiaPesquisadas.isEmpty()){
				ImovelEconomia imovelEconomia = (ImovelEconomia) ((List) imoveisEconomiaPesquisadas).get(0);

				if(imovelEconomia.getUltimaAlteracao().getTime() != dataUltimaAlteracao.getTime()){

					String idMatricula = "" + imovelEconomia.getImovelSubcategoria().getComp_id().getImovel().getId();

					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.imovel.numero_celpe_jacadastrado", null, idMatricula);
				}
			}
		}

		if(imoveisEconomia != null){

			Iterator imovelEconomiaIterator = imoveisEconomia.iterator();

			while(imovelEconomiaIterator.hasNext()){

				ImovelEconomia imovelEconomia = (ImovelEconomia) imovelEconomiaIterator.next();

				// caso seja o mesmo imovel economia não faça a verificação
				if(imovelEconomia.getUltimaAlteracao().getTime() != dataUltimaAlteracao.getTime()){

					// verifica se o numero da da celpe que veio do imovel
					// economia
					// é diferente de nulo.
					if(imovelEconomia.getNumeroCelpe() != null && !imovelEconomia.getNumeroCelpe().equals("")){

						Long numeroCelpeLong = Long.valueOf(numeroCelpe);

						// se o numero da iptu do imovel economia for diferente
						// de
						// nulo então é verificado se o numero da iptu
						// que o veio do form é igual ao do objeto imovel
						// economia
						// cadastrado.
						if(imovelEconomia.getNumeroCelpe().equals(numeroCelpeLong)){

							sessionContext.setRollbackOnly();

							throw new ControladorException("atencao.imovel.numero_celpe_jacadastrado", null, ""
											+ imovelEconomia.getImovelSubcategoria().getComp_id().getImovel().getId());
						}
					}
				}
			}
		}
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/11/2006
	 * @param ids
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void removerImovel(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// filtro imovel
		FiltroImovel filtroImovel = new FiltroImovel();
		Imovel imovel = new Imovel();

		// Parte de Validacao com Timestamp

		if(ids.length > 0 && ids != null){
			for(int i = 0; i < ids.length; i++){
				String dadosImovel = ids[i];
				String[] idUltimaAlteracao = dadosImovel.split("-");

				filtroImovel.limparListaParametros();
				// pesquissou o imovel na base
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idUltimaAlteracao[0].trim()));

				// coleção com resultado da pesquisa de imovel
				Collection imoveisNaBase = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
				// imovel encontrado
				Imovel imovelNaBase = (Imovel) imoveisNaBase.iterator().next();

				// Verificar se o imovel já foi atualizada por outro usuário
				// durante
				// esta atualização
				Calendar data = new GregorianCalendar();
				data.setTimeInMillis(Long.valueOf(idUltimaAlteracao[1].trim()).longValue());

				if(imovelNaBase.getUltimaAlteracao().after(data.getTime())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				// filtroImovel.limparListaParametros();
				imovel = imovelNaBase;

				// [FS0021] – Verificar Existência de Alteração de Inscrição Pendente para o Imóvel
				verificarExistenciaAlteracaoInscricaoPendente(imovel, true);

				// imovel = (Imovel) imoveis.iterator().next();
				imovel.setIndicadorExclusao(Imovel.IMOVEL_EXCLUIDO);
				imovel.setUltimaAlteracao(new Date());

				/**
				 * alterado por pedro alexandre dia 18/11/2006 alteração feita
				 * para acoplar o controle de abrangência de usuário
				 */
				// ------------ CONTROLE DE ABRANGENCIA --------------------
				Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovel);

				if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.acesso.negado.abrangencia");
				}else{

					// ------------ REGISTRAR TRANSAÇÃO ----------------
					RegistradorOperacao registradorBacia = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_REMOVER, imovel.getId(),
									imovel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					Operacao operacao = new Operacao();
					operacao.setId(Operacao.OPERACAO_IMOVEL_REMOVER);

					OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
					operacaoEfetuada.setOperacao(operacao);

					imovel.setOperacaoEfetuada(operacaoEfetuada);
					imovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

					registradorBacia.registrarOperacao(imovel);
					// ------------ REGISTRAR TRANSAÇÃO ----------------

					getControladorUtil().atualizar(imovel);
				}
				// ------------ CONTROLE DE ABRANGENCIA ---------------------
			}
		}

	}

	/**
	 * Pesquisa uma coleção de imóveis com uma query especifica
	 * 
	 * @param idLocalidade
	 *            parametros para a consulta
	 * @param idSetorComercial
	 *            parametros para a consulta
	 * @param idQuadra
	 *            parametros para a consulta
	 * @param lote
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Collection pesquisarImovel(Integer idLocalidade, Integer idSetorComercial, Integer idQuadra, Short lote)
					throws ControladorException{

		Collection colecaoImovelArray = null;
		Collection<Imovel> colecaoRetorno = new ArrayList();

		try{
			colecaoImovelArray = repositorioImovel.pesquisarImovel(idLocalidade, idSetorComercial, idQuadra, lote,
							Imovel.IMOVEL_EXCLUIDO.intValue());
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		Iterator colecaoImovelArrayIt = colecaoImovelArray.iterator();
		Object[] imovelArray;
		Imovel imovel;
		Quadra quadra;
		Rota rota = new Rota();
		FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();
		FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();

		while(colecaoImovelArrayIt.hasNext()){

			imovelArray = (Object[]) colecaoImovelArrayIt.next();

			imovel = new Imovel();
			imovel.setId((Integer) imovelArray[0]);
			imovel.setLote(Short.valueOf(String.valueOf(imovelArray[4])));
			imovel.setSubLote(Short.valueOf(String.valueOf(imovelArray[5])));

			quadra = (Quadra) imovelArray[3];
			imovel.setQuadra(quadra);

			rota.setId((Integer) imovelArray[6]);
			faturamentoGrupo.setId((Integer) imovelArray[7]);
			rota.setFaturamentoGrupo(faturamentoGrupo);
			imovel.setRota(rota);

			imovel.setLocalidade((Localidade) imovelArray[1]);
			imovel.setSetorComercial((SetorComercial) imovelArray[2]);

			if(imovelArray[8] != null){
				faturamentoSituacaoTipo.setId((Integer) imovelArray[8]);
				imovel.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);
			}

			colecaoRetorno.add(imovel);

		}

		return colecaoRetorno;

	}

	/**
	 * @author Carlos Chrystian
	 * @date 28/01/2012
	 *       Consulta do imóvel pela inscrição.
	 * @param idLocalidade
	 *            parametros para a consulta
	 * @param codigoSetorComercial
	 *            parametros para a consulta
	 * @param idQuadra
	 *            parametros para a consulta
	 * @param lote
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Collection pesquisarImovelPorInscricao(Integer idLocalidade, Integer codigoSetorComercial, Integer nnQuadra, Short nnLote)
					throws ControladorException{

		Collection colecaoImovelArray = null;
		Collection<Imovel> colecaoRetorno = new ArrayList();

		try{
			colecaoImovelArray = repositorioImovel.pesquisarImovelPorInscricao(idLocalidade, codigoSetorComercial, nnQuadra, nnLote,
							Imovel.IMOVEL_EXCLUIDO.intValue());
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		Iterator colecaoImovelArrayIt = colecaoImovelArray.iterator();
		Object[] imovelArray;
		Imovel imovel = null;

		while(colecaoImovelArrayIt.hasNext()){

			imovelArray = (Object[]) colecaoImovelArrayIt.next();

			imovel = new Imovel();
			imovel.setLocalidade(new Localidade());
			imovel.setSetorComercial(new SetorComercial());
			imovel.setQuadra(new Quadra());
			imovel.getQuadra().setRota(new Rota());
			imovel.setRota(new Rota());

			// 00 - imovel.id
			imovel.setId((Integer) imovelArray[0]);
			// 01 - imovel.lote
			imovel.setLote(((Short) imovelArray[1]).shortValue());
			// 02 - imovel.subLote
			imovel.setSubLote(((Short) imovelArray[2]).shortValue());
			// 03 - loca.id
			imovel.getLocalidade().setId((Integer) imovelArray[3]);
			// 04 - loca.descricao
			imovel.getLocalidade().setDescricao((String) imovelArray[4]);
			// 05 - sc.id
			imovel.getSetorComercial().setId((Integer) imovelArray[5]);
			// 06 - sc.codigo
			imovel.getSetorComercial().setCodigo(((Integer) imovelArray[6]).intValue());
			// 07 - sc.descricao
			imovel.getSetorComercial().setDescricao((String) imovelArray[7]);
			// 08 - quadra.id
			imovel.getQuadra().setId((Integer) imovelArray[8]);
			// 09 - quadra.numeroQuadra
			imovel.getQuadra().setNumeroQuadra(((Integer) imovelArray[9]).intValue());
			// 10 - rota.id
			imovel.getRota().setId((Integer) imovelArray[10]);
			// 11 - rota.codigo
			imovel.getRota().setCodigo((Short) imovelArray[11]);
			// 12 - rtqdra.id
			imovel.getQuadra().getRota().setId((Integer) imovelArray[12]);

			colecaoRetorno.add(imovel);
		}

		return colecaoRetorno;

	}

	/**
	 * Atualiza apenas os dados (Localidade, Setor, Quadra e lote) do imóvel
	 * 
	 * @author Ailton Sousa
	 * @date 09/02/2011
	 *       Alteração para permitir o controle de transação.
	 * @param imovel
	 *            parametros para a consulta
	 * @throws ControladorException
	 */
	public void atualizarImovelInscricao(Map<Integer, Collection<Imovel>> mapaRotaImoveis, Usuario usuarioLogado,
					String indicadorAlteracaoRota, boolean usuarioConfirmou) throws ControladorException, AlteracaoInscricaoImovelException{

		// lista de imóveis com alteração agendada para o encerramento do faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteracaoAgendadaEncerramento = new ArrayList<ImovelAlterarInscricaoHelper>();
		// lista de imóveis alterados com mudança de quadra, com mudança de rota e com mudança
		// do grupo de faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlterados = new ArrayList<ImovelAlterarInscricaoHelper>();
		// lista de imóveis alterados com mudança de quadra, com mudança de rota e sem mudança
		// do grupo de faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteradosSemMudancaGrupoFaturamento = new ArrayList<ImovelAlterarInscricaoHelper>();
		// lista de imóveis alterados com mudança de quadra, sem mudança de rota e sem mudança
		// do grupo de faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteradosSemMudancaRotaGrupoFaturamento = new ArrayList<ImovelAlterarInscricaoHelper>();
		// lista de imóveis alterados sem mudança de quadra, sem mudança de rota e sem mudança
		// do grupo de faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteradosSemMudancaQuadraRotaGrupoFaturamento = new ArrayList<ImovelAlterarInscricaoHelper>();

		// Variáveis
		Rota rotaAnterior = null;
		Rota rotaAtual = null;
		Collection<Imovel> colecaoImoveis = null;
		Imovel imovelNaBase = null;
		Quadra quadraDestino = null;
		Object[] retorno = null;

		if(mapaRotaImoveis != null && !mapaRotaImoveis.isEmpty()){

			// Consulta os parametros do sistema
			SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

			// 5.2.2.1. Para cada conjunto de imóveis da Inscrição Inicial com a mesma
			// rota (ROTA_ID da tabela IMOVEL)
			for(Integer rotaId : mapaRotaImoveis.keySet()){

				colecaoImoveis = mapaRotaImoveis.get(rotaId);

				if(!Util.isVazioOrNulo(colecaoImoveis)){

					if(indicadorAlteracaoRota != null){

						// 5.2.2. Caso o usuário tenha selecionado a opção “Com a substituição
						// das rotas
						// de todos os imóveis da quadra origem para as rotas correspondentes da
						// quadra
						// destino”:
						if(indicadorAlteracaoRota.equals("1")){

							// Recupera algumas informações importantes: imóvel na base, quadra
							// destino, rota anterior, rota atual
							retorno = recuperarAlteracoesImovel(colecaoImoveis);

							imovelNaBase = (Imovel) retorno[0];
							quadraDestino = (Quadra) retorno[1];
							rotaAnterior = (Rota) retorno[2];
							rotaAtual = (Rota) retorno[3];

							// 5.2.2.1.3. Verificar a impossibilidade de efetuar a alteração das
							// inscrições em tempo real
							// [SB0003 - Verificar Alteração das Inscrições dos Imóveis].

							retorno = verificarAlteracaoInscricaoImoveis(rotaAnterior, rotaAtual, colecaoImoveis, imovelNaBase,
											quadraDestino, sistemaParametro.getAnoMesFaturamento());

							// Guarda as informações de imóveis separadas em listas distintas
							listaImoveisAlteracaoAgendadaEncerramento.addAll((Collection<ImovelAlterarInscricaoHelper>) retorno[0]);
							listaImoveisAlterados.addAll((Collection<ImovelAlterarInscricaoHelper>) retorno[1]);
							listaImoveisAlteradosSemMudancaGrupoFaturamento.addAll((Collection<ImovelAlterarInscricaoHelper>) retorno[2]);
							listaImoveisAlteradosSemMudancaRotaGrupoFaturamento
											.addAll((Collection<ImovelAlterarInscricaoHelper>) retorno[3]);

							// 5.2.4. Caso o usuário tenha selecionado a opção “Com a
							// substituição dasrotas dos imóveis da quadra origem com a mesma
							// rota para as rotas correspondentes da quadra destino,
							// permanecendo os demais com suas atuais rotas”:
						}else if(indicadorAlteracaoRota.equals("2")){

							Collection<Imovel> colecaoImoveisSubstituirRotas = new ArrayList<Imovel>();
							Collection<Imovel> colecaoImoveisNaoSubstituirRotas = new ArrayList<Imovel>();

							// Separando os imóveis que possuem a rota igual à rota de sua
							// quadra dos imóveis que possuem uma rota diferente
							retorno = separarImoveisComRotaDiferente(colecaoImoveis);

							colecaoImoveisSubstituirRotas = (Collection<Imovel>) retorno[0];
							colecaoImoveisNaoSubstituirRotas = (Collection<Imovel>) retorno[1];

							// 5.2.4.1. ...e que terão as rotas substituídas:
							if(!Util.isVazioOrNulo(colecaoImoveisSubstituirRotas)){

								// Recupera algumas informações importantes: Imóvel alterado,
								// imóvel na base, quadra destino, rota anterior, rota atual
								retorno = recuperarAlteracoesImovel(colecaoImoveisSubstituirRotas);

								imovelNaBase = (Imovel) retorno[0];
								quadraDestino = (Quadra) retorno[1];
								rotaAnterior = (Rota) retorno[2];
								rotaAtual = (Rota) retorno[3];

								// 5.2.4.1.3. Verificar a impossibilidade de efetuar a alteração
								// das inscrições em tempo real
								// [SB0003 - Verificar Alteração das Inscrições dos Imóveis].
								retorno = verificarAlteracaoInscricaoImoveis(rotaAnterior, rotaAtual, colecaoImoveis, imovelNaBase,
												quadraDestino, sistemaParametro.getAnoMesFaturamento());

								// Guarda as informações de imóveis separadas em listas
								// distintas
								listaImoveisAlteracaoAgendadaEncerramento.addAll((Collection<ImovelAlterarInscricaoHelper>) retorno[0]);
								listaImoveisAlterados.addAll((Collection<ImovelAlterarInscricaoHelper>) retorno[1]);
								listaImoveisAlteradosSemMudancaGrupoFaturamento
												.addAll((Collection<ImovelAlterarInscricaoHelper>) retorno[2]);
								listaImoveisAlteradosSemMudancaRotaGrupoFaturamento
												.addAll((Collection<ImovelAlterarInscricaoHelper>) retorno[3]);

							}

							// 5.2.4.2. Para cada conjunto de imóveis da Inscrição Inicial com a
							// mesma rota (ROTA_ID da tabela IMOVEL) e que não terão as rotas
							// substituídas:
							if(!Util.isVazioOrNulo(colecaoImoveisNaoSubstituirRotas)){

								// Recupera algumas informações importantes: Imóvel alterado,
								// imóvel na base, quadra destino, rota anterior, rota atual
								retorno = recuperarAlteracoesImovel(colecaoImoveisNaoSubstituirRotas);

								imovelNaBase = (Imovel) retorno[0];
								quadraDestino = (Quadra) retorno[1];
								rotaAnterior = (Rota) retorno[2];
								rotaAtual = rotaAnterior;

								// 5.2.4.2.3. Criar lista de imóveis alterados com mudança de
								// quadra, sem mudança de rota e sem mudança do grupo de
								// faturamento com os seguintes dados:
								// 5.2.4.2.3.1. Dados do Imóvel [SB0007 – Atribuir Valores do
								// Imóvel].
								listaImoveisAlteradosSemMudancaRotaGrupoFaturamento
												.addAll(atribuirValoresImovel(
																colecaoImoveisNaoSubstituirRotas,
																imovelNaBase,
																rotaAnterior,
																rotaAtual,
																quadraDestino,
																ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS_SEM_MUDANCA_ROTA_GRUPO_FATURAMENTO));

							}
						}

						// 5.5. Caso contrário, ou seja, caso a alteração da inscrição não
						// provoque mudança da quadra:
						// (indicadorAlteracaoRota == null)
					}else{

						// Recupera algumas informações importantes: Imóvel alterado,
						// imóvel na base, quadra destino, rota anterior, rota atual
						retorno = recuperarAlteracoesImovel(colecaoImoveis);

						imovelNaBase = (Imovel) retorno[0];
						quadraDestino = (Quadra) retorno[1];
						rotaAnterior = (Rota) retorno[2];
						rotaAtual = rotaAnterior;

						// 5.5.3. Criar lista de imóveis alterados sem mudança de quadra, sem
						// mudança de rota e sem mudança do grupo de faturamento com os
						// seguintes dados:
						// 5.5.3.1. Dados do Imóvel [SB0007 – Atribuir Valores do Imóvel].
						listaImoveisAlteradosSemMudancaQuadraRotaGrupoFaturamento
										.addAll(atribuirValoresImovel(
														colecaoImoveis,
														imovelNaBase,
														rotaAnterior,
														rotaAtual,
														quadraDestino,
														ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS_SEM_MUDANCA_QUADRA_ROTA_GRUPO_FATURAMENTO));
					}
				}
			}

			// 5.6. Caso tenha ocorrido agendamento da alteração da inscrição/rota para o
			// encerramento do faturamento (existem dados na lista de imóveis com alteração
			// agendada para o encerramento do faturamento):
			if(!Util.isVazioOrNulo(listaImoveisAlteracaoAgendadaEncerramento)){

				// O usuário precisa confirmar o agendamento da alteração de inscrição
				if(!usuarioConfirmou){
					throw new AlteracaoInscricaoImovelException("atencao.confirmar.alteracao.inscricao.imovel.07", null,
									listaImoveisAlteracaoAgendadaEncerramento);

				}else{

					// 5.6.4. Caso o usuário confirme, preparar alteração da inscrição/rota no
					// encerramento do faturamento
					// [SB0004 – Preparar Alteração da Inscrição/Rota no Encerramento
					// Faturamento].
					prepararAlteracaoInscricaoNoEncerramentoFaturamento(listaImoveisAlteracaoAgendadaEncerramento);
				}
			}

			// 5.7. Caso tenha ocorrido alteração da inscrição/rota em tempo real (existem dados
			// em alguma das listas de imóveis alterados):

			// Para a lista de imóveis alterados sem mudança de quadra, sem mudança de rota e
			// sem mudança do grupo de faturamento
			atualizarListaImoveisInscricao(listaImoveisAlteradosSemMudancaQuadraRotaGrupoFaturamento, usuarioLogado);

			// Para a lista de imóveis alterados com mudança de quadra, com mudança de rota e com
			// mudança do grupo de faturamento
			atualizarListaImoveisInscricao(listaImoveisAlterados, usuarioLogado);

			// Para a lista de imóveis alterados com mudança de quadra, com mudança de rota e sem
			// mudança do grupo de faturamento
			atualizarListaImoveisInscricao(listaImoveisAlteradosSemMudancaGrupoFaturamento, usuarioLogado);

			// Para a lista de imóveis alterados com mudança de quadra, sem mudança de rota e sem
			// mudança do grupo de faturamento
			atualizarListaImoveisInscricao(listaImoveisAlteradosSemMudancaRotaGrupoFaturamento, usuarioLogado);

			// [SB0006] - Emitir Relatório dos Imóveis Com Inscrição Alterada
			emitirRelatorioImoveisInscricaoAlterada(listaImoveisAlteracaoAgendadaEncerramento,
							listaImoveisAlteradosSemMudancaQuadraRotaGrupoFaturamento, listaImoveisAlterados,
							listaImoveisAlteradosSemMudancaGrupoFaturamento, listaImoveisAlteradosSemMudancaRotaGrupoFaturamento,
							usuarioLogado);
		}
	}

	/**
	 * [UC0074] - Alterar Inscrição de Imóvel
	 * [SB0006] - Emitir Relatório dos Imóveis Com Inscrição Alterada
	 * 
	 * @author Luciano Galvao
	 * @date 15/02/2013
	 */
	private void emitirRelatorioImoveisInscricaoAlterada(
					Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteracaoAgendadaEncerramento,
					Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteradosSemMudancaQuadraRotaGrupoFaturamento,
					Collection<ImovelAlterarInscricaoHelper> listaImoveisAlterados,
					Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteradosSemMudancaGrupoFaturamento,
					Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteradosSemMudancaRotaGrupoFaturamento, Usuario usuarioLogado)
					throws ControladorException{

		Collection<ImovelAlterarInscricaoHelper> listaImoveisCompleta = new ArrayList<ImovelAlterarInscricaoHelper>();

		// 1. Para cada lista de imóveis com alteração da inscrição (lista de imóveis alterados sem
		// mudança de quadra, sem mudança de rota e sem mudança do grupo de faturamento, lista de
		// imóveis alterados com mudança de quadra, com mudança de rota e com mudança do grupo de
		// faturamento, lista de imóveis alterados com mudança de quadra, com mudança de rota e sem
		// mudança do grupo de faturamento, lista de imóveis alterados com mudança de quadra, sem
		// mudança de rota e sem mudança do grupo de faturamento, lista de imóveis com alteração
		// agendada para o encerramento do faturamento)
		if(!Util.isVazioOrNulo(listaImoveisAlteradosSemMudancaQuadraRotaGrupoFaturamento)){
			listaImoveisCompleta.addAll(listaImoveisAlteradosSemMudancaQuadraRotaGrupoFaturamento);
		}
		if(!Util.isVazioOrNulo(listaImoveisAlterados)){
			listaImoveisCompleta.addAll(listaImoveisAlterados);
		}
		if(!Util.isVazioOrNulo(listaImoveisAlteradosSemMudancaGrupoFaturamento)){
			listaImoveisCompleta.addAll(listaImoveisAlteradosSemMudancaGrupoFaturamento);
		}
		if(!Util.isVazioOrNulo(listaImoveisAlteradosSemMudancaRotaGrupoFaturamento)){
			listaImoveisCompleta.addAll(listaImoveisAlteradosSemMudancaRotaGrupoFaturamento);
		}
		if(!Util.isVazioOrNulo(listaImoveisAlteracaoAgendadaEncerramento)){
			listaImoveisCompleta.addAll(listaImoveisAlteracaoAgendadaEncerramento);
		}

		// O sistema emite o relatório dos imóveis com alteração da inscrição
		RelatorioImoveisInscricaoAlterada relatorioImoveisInscricaoAlterada = new RelatorioImoveisInscricaoAlterada(usuarioLogado);
		relatorioImoveisInscricaoAlterada.addParametro("colecaoImoveisInscricaoAlterada", listaImoveisCompleta);
		relatorioImoveisInscricaoAlterada.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		// Envia o Relatório para ser processado em batch
		this.getControladorBatch().iniciarProcessoRelatorio(relatorioImoveisInscricaoAlterada);
	}

	/**
	 * [UC0074] - Alterar Inscrição de Imóvel
	 * [SB0005] – Efetivar Alteração Inscrição/Rota
	 * 
	 * @author Luciano Galvao
	 * @date 30/01/2013
	 */
	private void atualizarListaImoveisInscricao(Collection<ImovelAlterarInscricaoHelper> listaImoveis, Usuario usuarioLogado)
					throws ControladorException{

		try{
			// 5.7. Caso tenha ocorrido alteração da inscrição/rota em tempo real
			if(!Util.isVazioOrNulo(listaImoveis)){

				Imovel imovel = null;

				FiltroImovel filtroImovel = null;

				Collection<Imovel> imoveis = null;

				// 5.7.1.1. Efetivar a alteração da inscrição/rota na tabela IMOVEL para o
				// conjunto de imóveis da lista
				// [SB0005 – Efetivar Alteração Inscrição/Rota].
				for(ImovelAlterarInscricaoHelper imovelAlteracao : listaImoveis){
					filtroImovel = new FiltroImovel();
					filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.ID, imovelAlteracao.getIdImovel()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, imovelAlteracao.getIdLocalidadeAtual()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_CODIGO, imovelAlteracao
									.getCodigoSetorComercialAtual()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_NUMERO, imovelAlteracao.getNumeroQuadraAtual()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOTE, imovelAlteracao.getLoteAtual()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SUBLOTE, imovelAlteracao.getSubLoteAtual()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, ConstantesSistema.NAO));

					imoveis = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

					if(!Util.isVazioOrNulo(imoveis)){
						Imovel imovelPesquisa = (Imovel) Util.retonarObjetoDeColecao(imoveis);

						throw new ControladorException("atencao.imovel_ja_cadastrado", null, imovelPesquisa.getId().toString());
					}

					// Atualiza inscrição do imóvel
					repositorioImovel.atualizarImovelInscricao(imovelAlteracao.getIdImovel(), imovelAlteracao.getIdLocalidadeAtual(),
									imovelAlteracao.getIdSetorComercialAtual(), imovelAlteracao.getCodigoSetorComercialAtual(),
									imovelAlteracao.getIdQuadraAtual(), imovelAlteracao.getNumeroQuadraAtual(),
									imovelAlteracao.getLoteAtual(), imovelAlteracao.getSubLoteAtual(),
									imovelAlteracao.getTestadaLoteAtual(), imovelAlteracao.getIdRotaAtual());

					// [UC0107] - Registrar Transação
					// ------------ REGISTRAR TRANSAÇÃO----------------------------
					RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_ATUALIZAR,
									imovelAlteracao.getIdImovel(), imovelAlteracao.getIdImovel(), new UsuarioAcaoUsuarioHelper(
													usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					Operacao operacao = new Operacao();
					operacao.setId(Operacao.OPERACAO_IMOVEL_ATUALIZAR);

					OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
					operacaoEfetuada.setOperacao(operacao);

					imovel = this.pesquisarImovel(imovelAlteracao.getIdImovel());

					imovel.setOperacaoEfetuada(operacaoEfetuada);
					registradorOperacao.registrarOperacao(imovel);
					// ------------ REGISTRAR TRANSAÇÃO----------------------------
				}
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0074] - Alterar Inscrição de Imóvel
	 * [SB0004] – Preparar Alteração Inscrição no Encerramento Faturamento
	 * 
	 * @author Luciano Galvao
	 * @throws ControladorException
	 * @date 30/01/2013
	 */
	private void prepararAlteracaoInscricaoNoEncerramentoFaturamento(Collection<ImovelAlterarInscricaoHelper> listaImoveis)
					throws ControladorException{

		ImovelInscricaoAlterada imovelInscricaoAlterada = null;
		Imovel imovel = null;
		Date dataAtual = new Date();

		Collection<ImovelInscricaoAlterada> colecaoImovelInscricaoAlterada = new ArrayList<ImovelInscricaoAlterada>();

		if(!Util.isVazioOrNulo(listaImoveis)){
			for(ImovelAlterarInscricaoHelper imovelHelper : listaImoveis){

				imovel = new Imovel(imovelHelper.getIdImovel());

				// [FS0009] – Verificar Existência de Alteração de Inscrição Pendente para o Imóvel
				verificarExistenciaAlteracaoInscricaoPendente(imovel, false);

				// [FS0010] – Verificar Duplicidade de Inscrição
				verificarDuplicidadeInscricao(imovelHelper);

				imovelInscricaoAlterada = new ImovelInscricaoAlterada();
				imovelInscricaoAlterada.setImovel(imovel);
				imovelInscricaoAlterada.setFaturamentoGrupo(new FaturamentoGrupo(imovelHelper.getIdGrupoFaturamentoAtual()));

				// Inscrição anterior
				imovelInscricaoAlterada.setLocalidadeAnterior(new Localidade(imovelHelper.getIdLocalidadeAnterior()));
				imovelInscricaoAlterada.setSetorComercialAnterior(new SetorComercial(imovelHelper.getIdSetorComercialAnterior()));
				imovelInscricaoAlterada.setQuadraAnterior(new Quadra(imovelHelper.getIdQuadraAnterior()));
				imovelInscricaoAlterada.setLoteAnterior(imovelHelper.getLoteAnterior());
				imovelInscricaoAlterada.setSubLoteAnterior(imovelHelper.getSubLoteAnterior());
				if(imovelHelper.getTestadaLoteAnterior() != null){
					imovelInscricaoAlterada.setTestadaLoteAnterior(imovelHelper.getTestadaLoteAnterior());
				}else{
					imovelInscricaoAlterada.setTestadaLoteAnterior(new Short("0"));
				}

				// Inscrição atual
				imovelInscricaoAlterada.setLocalidadeAtual(new Localidade(imovelHelper.getIdLocalidadeAtual()));
				imovelInscricaoAlterada.setSetorComercialAtual(new SetorComercial(imovelHelper.getIdSetorComercialAtual()));
				imovelInscricaoAlterada.setQuadraAtual(new Quadra(imovelHelper.getIdQuadraAtual()));
				imovelInscricaoAlterada.setLoteAtual(imovelHelper.getLoteAtual());
				imovelInscricaoAlterada.setSubLoteAtual(imovelHelper.getSubLoteAtual());
				if(imovelHelper.getTestadaLoteAtual() != null){
					imovelInscricaoAlterada.setTestadaLoteAtual(imovelHelper.getTestadaLoteAtual());
				}else{
					imovelInscricaoAlterada.setTestadaLoteAtual(new Short("0"));
				}

				// Dados da rota
				imovelInscricaoAlterada.setRotaAnterior(new Rota(imovelHelper.getIdRotaAnterior()));
				imovelInscricaoAlterada.setRotaAtual(new Rota(imovelHelper.getIdRotaAtual()));

				// Indicadores
				imovelInscricaoAlterada.setIndicadorAtualizado(ConstantesSistema.NAO);
				imovelInscricaoAlterada.setIndicadorAlteracaoExcluida(ConstantesSistema.NAO);
				imovelInscricaoAlterada.setIndicadorImovelExcluido(ConstantesSistema.NAO);

				// Datas
				imovelInscricaoAlterada.setDataAlteracaoOnline(dataAtual);
				imovelInscricaoAlterada.setUltimaAlteracao(dataAtual);

				colecaoImovelInscricaoAlterada.add(imovelInscricaoAlterada);
			}

			// Insere a alteração de inscrição do imóvel
			getControladorUtil().inserirColecaoObjetos(colecaoImovelInscricaoAlterada);
		}
	}

	/**
	 * Recupera algumas informações importantes em mais de um trecho do método que atualiza a
	 * inscrição de imóvel. Como é utilizado em mais de um ponto, foi transformado em um método.
	 * 
	 * @author Luciano Galvão
	 * @date 30/01/2013
	 */
	private Object[] recuperarAlteracoesImovel(Collection<Imovel> colecaoImoveis) throws ControladorException{

		Rota rotaAnterior = null;
		Rota rotaAtual = null;

		// Imóvel com a inscrição atual (proveniente da alteração)
		Imovel imovelAlterado = colecaoImoveis.iterator().next();

		// Recuperando o imóvel da base
		Imovel imovelNaBase = pesquisarImovel(imovelAlterado.getId());

		// 5.2.4.1.1. Atribuir ROTA_ID da tabela IMOVEL à ROTA_IDANTERIOR.
		if(imovelNaBase != null && imovelNaBase.getRota() != null){
			rotaAnterior = pesquisarRota(imovelNaBase.getRota().getId());
		}

		// Pesquisa a quadra de destino do imóvel
		Quadra quadraDestino = pesquisarQuadraDestino(imovelAlterado, imovelNaBase);

		// 5.2.4.1.2. Atribuir Id da rota da quadra destino (ROTA_ID da
		// tabela QUADRA com QDRA_ID=Id da Quadra Destino) à ROTA_IDATUAL
		if(quadraDestino != null && quadraDestino.getRota() != null){
			rotaAtual = quadraDestino.getRota();
		}

		Object[] retorno = new Object[4];

		retorno[0] = imovelNaBase;
		retorno[1] = quadraDestino;
		retorno[2] = rotaAnterior;
		retorno[3] = rotaAtual;

		return retorno;
	}

	/**
	 * Separando os imóveis que possuem a rota igual à rota de sua quadra dos imóveis que possuem
	 * uma rota diferente
	 * 
	 * @author Luciano Galvao
	 * @date 30/01/2013
	 */
	private Object[] separarImoveisComRotaDiferente(Collection<Imovel> colecaoImoveis){

		Collection<Imovel> colecaoImoveisSubstituirRotas = new ArrayList<Imovel>();
		Collection<Imovel> colecaoImoveisNaoSubstituirRotas = new ArrayList<Imovel>();

		for(Imovel imovel2 : colecaoImoveis){
			if(imovel2.getRota() != null && imovel2.getQuadra() != null && imovel2.getQuadra().getRota() != null
							&& imovel2.getRota().equals(imovel2.getQuadra().getRota())){
				colecaoImoveisSubstituirRotas.add(imovel2);
			}else{
				colecaoImoveisNaoSubstituirRotas.add(imovel2);
			}
		}

		Object[] retorno = new Object[2];
		retorno[0] = colecaoImoveisSubstituirRotas;
		retorno[1] = colecaoImoveisNaoSubstituirRotas;

		return retorno;
	}

	/**
	 * [UC0074] - Alterar Inscrição de Imóvel
	 * [SB0003] – Verificar Alteração da Inscrição dos Imóveis
	 * 
	 * @author Luciano Galvao
	 * @date 30/01/2013
	 */
	private Object[] verificarAlteracaoInscricaoImoveis(Rota rotaAnterior, Rota rotaAtual, Collection<Imovel> colecaoImoveis,
					Imovel imovelNaBase, Quadra quadraDestino, Integer anoMesReferenciaFaturamento) throws ControladorException{

		// lista de imóveis com alteração agendada para o encerramento do faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteracaoAgendadaEncerramento = new ArrayList<ImovelAlterarInscricaoHelper>();
		// lista de imóveis alterados com mudança de quadra, com mudança de rota e com mudança do
		// grupo de faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlterados = new ArrayList<ImovelAlterarInscricaoHelper>();
		// lista de imóveis alterados com mudança de quadra, com mudança de rota e sem mudança do
		// grupo de faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteradosSemMudancaGrupoFaturamento = new ArrayList<ImovelAlterarInscricaoHelper>();
		// lista de imóveis alterados com mudança de quadra, sem mudança de rota e sem mudança do
		// grupo de faturamento
		Collection<ImovelAlterarInscricaoHelper> listaImoveisAlteradosSemMudancaRotaGrupoFaturamento = new ArrayList<ImovelAlterarInscricaoHelper>();

		// Variáveis
		Collection<MovimentoRoteiroEmpresa> colecaoMovimentos = null;
		FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = null;

		// 1. Caso a rota tenha sido alterada (ROTA_IDANTERIOR é diferente de ROTA_IDATUAL):
		if(rotaAnterior != null && rotaAtual != null && !rotaAnterior.equals(rotaAtual)){

			FaturamentoGrupo faturamentoGrupoOrigem = rotaAnterior.getFaturamentoGrupo();
			FaturamentoGrupo faturamentoGrupoDestino = rotaAtual.getFaturamentoGrupo();

			// 1.1. Caso a alteração da rota acarrete a mudança do grupo de faturamento dos imóveis
			// (grupo de faturamento origem (FTGR_ID da tabela ROTA com ROTA_ID=ROTA_IDANTERIOR) é
			// diferente do grupo de faturamento destino (FTGR_ID da tabela ROTA com
			// ROTA_ID=ROTA_IDATUAL)):
			if(faturamentoGrupoOrigem != null && faturamentoGrupoDestino != null && !faturamentoGrupoOrigem.equals(faturamentoGrupoDestino)){

				// 1.1.1. Caso o grupo de faturamento origem já tenha sido faturado
				// (FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para FTGR_ID=grupo de faturamento
				// origem maior que PARM_AMREFERENCIAFATURAMENTO da tabela SISTEMA_PARAMETROS):
				if(faturamentoGrupoOrigem.getAnoMesReferencia() != null
								&& faturamentoGrupoOrigem.getAnoMesReferencia().compareTo(anoMesReferenciaFaturamento) > 0){

					filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
					filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(
									FiltroMovimentoRoteiroEmpresa.FATURAMENTO_GRUPO_ID, faturamentoGrupoOrigem.getId()));
					filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
									faturamentoGrupoOrigem.getAnoMesReferencia()));

					colecaoMovimentos = getControladorUtil().pesquisar(filtroMovimentoRoteiroEmpresa,
									MovimentoRoteiroEmpresa.class.getName());

					// 1.1.1.1. Caso já tenha ocorrido a geração dos dados de leitura do grupo de
					// faturamento origem para o próximo faturamento (existe ocorrência na tabela
					// MOVIMENTO_ROTEIRO_EMPRESA para FTGR_ID=grupo de faturamento origem e
					// MREM_AMMOVIMENTO=FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para
					// FTGR_ID=grupo de faturamento origem):
					if(!Util.isVazioOrNulo(colecaoMovimentos)){

						// 1.1.1.1.1. Criar lista de imóveis com alteração agendada para o
						// encerramento do faturamento com os seguintes dados:
						// 1.1.1.1.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do Imóvel].
						listaImoveisAlteracaoAgendadaEncerramento.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase, rotaAnterior,
										rotaAtual, quadraDestino,
										ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERACAO_AGENDADA_ENCERRAMENTO));

						// 1.1.1.2. Caso contrário (ainda não houve a geração dos dados de leitura
						// do grupo de origem para o próximo faturamento):
					}else{

						// 1.1.1.2.1. Caso o grupo de faturamento destino já tenha sido faturado
						// (FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para FTGR_ID=grupo de
						// faturamento destino maior que PARM_AMREFERENCIAFATURAMENTO da tabela
						// SISTEMA_PARAMETROS):
						if(faturamentoGrupoDestino.getAnoMesReferencia() != null
										&& faturamentoGrupoDestino.getAnoMesReferencia().compareTo(anoMesReferenciaFaturamento) > 0){

							filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
							filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(
											FiltroMovimentoRoteiroEmpresa.FATURAMENTO_GRUPO_ID, faturamentoGrupoDestino.getId()));
							filtroMovimentoRoteiroEmpresa
											.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
															faturamentoGrupoDestino.getAnoMesReferencia()));

							colecaoMovimentos = getControladorUtil().pesquisar(filtroMovimentoRoteiroEmpresa,
											MovimentoRoteiroEmpresa.class.getName());

							// 1.1.1.2.1.1. Caso já tenha ocorrido a geração dos dados de leitura do
							// grupo de faturamento destino para o próximo faturamento (existe
							// ocorrência na tabela MOVIMENTO_ROTEIRO_EMPRESA para FTGR_ID=grupo de
							// faturamento destino e MREM_AMMOVIMENTO=FTGR_AMREFERENCIA da tabela
							// FATURAMENTO_GRUPO para FTGR_ID=grupo de faturamento destino):
							if(!Util.isVazioOrNulo(colecaoMovimentos)){

								// 1.1.1.2.1.1.1. Criar lista de imóveis com alteração agendada para
								// o encerramento do faturamento com os seguintes dados:
								// 1.1.1.2.1.1.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do
								// Imóvel].
								listaImoveisAlteracaoAgendadaEncerramento.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase,
												rotaAnterior, rotaAtual, quadraDestino,
												ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERACAO_AGENDADA_ENCERRAMENTO));

								// 1.1.1.2.1.2. Caso contrário (ainda não houve a geração dos dados
								// de leitura do grupo de destino para o próximo faturamento):
							}else{

								// 1.1.1.2.1.2.1. Criar lista de imóveis alterados com mudança de
								// quadra, com mudança de rota e com mudança do grupo de faturamento
								// com os seguintes dados:
								// 1.1.1.2.1.2.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do
								// Imóvel].
								listaImoveisAlterados.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase, rotaAnterior, rotaAtual,
												quadraDestino, ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS));
							}

							// 1.1.1.2.2. Caso contrário (grupo de faturamento destino ainda não
							// faturado):
						}else{

							// 1.1.1.2.2.1. Criar lista de imóveis com alteração agendada para o
							// encerramento do faturamento com os seguintes dados:
							// 1.1.1.2.2.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do Imóvel].
							listaImoveisAlteracaoAgendadaEncerramento.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase,
											rotaAnterior, rotaAtual, quadraDestino,
											ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERACAO_AGENDADA_ENCERRAMENTO));
						}
					}

					// 1.1.2. Caso contrário (grupo de faturamento origem ainda não faturado):
				}else{

					filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
					filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(
									FiltroMovimentoRoteiroEmpresa.FATURAMENTO_GRUPO_ID, faturamentoGrupoOrigem.getId()));
					filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
									faturamentoGrupoOrigem.getAnoMesReferencia()));

					colecaoMovimentos = getControladorUtil().pesquisar(filtroMovimentoRoteiroEmpresa,
									MovimentoRoteiroEmpresa.class.getName());

					// 1.1.2.1. Caso já tenha ocorrido a geração dos dados de leitura do grupo de
					// faturamento origem para o faturamento corrente (existe ocorrência na tabela
					// MOVIMENTO_ROTEIRO_EMPRESA para FTGR_ID=grupo de faturamento origem e
					// MREM_AMMOVIMENTO=FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para
					// FTGR_ID=grupo de faturamento origem):
					if(!Util.isVazioOrNulo(colecaoMovimentos)){

						// 1.1.2.1.1. Criar lista de imóveis com alteração agendada para o
						// encerramento do faturamento com os seguintes dados:
						// 1.1.2.1.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do Imóvel].
						listaImoveisAlteracaoAgendadaEncerramento.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase, rotaAnterior,
										rotaAtual, quadraDestino,
										ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERACAO_AGENDADA_ENCERRAMENTO));

						// 1.1.2.2. Caso contrário (ainda não houve a geração dos dados de leitura
						// do grupo de origem):
					}else{

						// 1.1.2.2.1. Caso o grupo de faturamento destino já tenha sido faturado
						// (FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para FTGR_ID=grupo de
						// faturamento destino maior que PARM_AMREFERENCIAFATURAMENTO da tabela
						// SISTEMA_PARAMETROS):
						if(faturamentoGrupoDestino.getAnoMesReferencia() != null
										&& faturamentoGrupoDestino.getAnoMesReferencia().compareTo(anoMesReferenciaFaturamento) > 0){

							// 1.1.2.2.1.1. Criar lista de imóveis com alteração agendada para o
							// encerramento do faturamento com os seguintes dados:
							// 1.1.2.2.1.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do Imóvel].
							listaImoveisAlteracaoAgendadaEncerramento.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase,
											rotaAnterior, rotaAtual, quadraDestino,
											ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERACAO_AGENDADA_ENCERRAMENTO));

							// 1.1.2.2.2. Caso contrário (grupo de faturamento destino ainda não
							// faturado)
						}else{

							filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
							filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(
											FiltroMovimentoRoteiroEmpresa.FATURAMENTO_GRUPO_ID, faturamentoGrupoDestino.getId()));
							filtroMovimentoRoteiroEmpresa
											.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
															faturamentoGrupoDestino.getAnoMesReferencia()));

							colecaoMovimentos = getControladorUtil().pesquisar(filtroMovimentoRoteiroEmpresa,
											MovimentoRoteiroEmpresa.class.getName());

							// 1.1.2.2.2.1. Caso já tenha ocorrido a geração dos dados de leitura do
							// grupo de faturamento destino para o faturamento corrente (existe
							// ocorrência na tabela MOVIMENTO_ROTEIRO_EMPRESA para FTGR_ID=grupo de
							// faturamento destino e MREM_AMMOVIMENTO=FTGR_AMREFERENCIA da tabela
							// FATURAMENTO_GRUPO para FTGR_ID=grupo de faturamento destino):
							if(!Util.isVazioOrNulo(colecaoMovimentos)){

								// 1.1.2.2.2.1.1. Criar lista de imóveis com alteração agendada para
								// o encerramento do faturamento com os seguintes dados:
								// 1.1.2.2.2.1.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do
								// Imóvel].
								listaImoveisAlteracaoAgendadaEncerramento.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase,
												rotaAnterior, rotaAtual, quadraDestino,
												ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERACAO_AGENDADA_ENCERRAMENTO));

								// 1.1.2.2.2.2. Caso contrário (ainda não houve a geração dos dados
								// de leitura do grupo de destino):
							}else{

								// 1.1.2.2.2.2.1. Criar lista de imóveis alterados com mudança de
								// quadra, com mudança de rota e com mudança do grupo de faturamento
								// com os seguintes dados:
								// 1.1.2.2.2.2.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do
								// Imóvel].
								listaImoveisAlterados.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase, rotaAnterior, rotaAtual,
												quadraDestino, ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS));

							}
						}
					}
				}

				// 1.2. Caso contrário, ou seja, caso a alteração da rota não acarrete a mudança do
				// grupo de faturamento dos imóveis (grupo de faturamento origem (FTGR_ID da tabela
				// ROTA com ROTA_ID=ROTA_IDANTERIOR) é igual ao grupo de faturamento destino
				// (FTGR_ID da tabela ROTA com ROTA_ID=ROTA_IDATUAL)):
			}else if(faturamentoGrupoOrigem != null && faturamentoGrupoDestino != null
							&& faturamentoGrupoOrigem.equals(faturamentoGrupoDestino)){

				// 1.2.1. Criar lista de imóveis alterados com mudança de quadra, com mudança de
				// rota e sem mudança do grupo de faturamento com os seguintes dados:
				// 1.2.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do Imóvel].
				listaImoveisAlteradosSemMudancaGrupoFaturamento.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase, rotaAnterior,
								rotaAtual, quadraDestino,
								ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS_SEM_MUDANCA_GRUPO_FATURAMENTO));
			}

			// 2. Caso contrário, ou seja, caso a rota não tenha sido alterada (ROTA_IDANTERIOR é
			// igual à ROTA_IDATUAL):
		}else if(rotaAnterior != null && rotaAtual != null && rotaAnterior.equals(rotaAtual)){

			// 2.1. Criar lista de imóveis alterados com mudança de quadra, sem mudança de rota e
			// sem mudança do grupo de faturamento com os seguintes dados:
			// 2.1.1. Dados do Imóvel [SB0007 – Atribuir Valores do Imóvel].
			listaImoveisAlteradosSemMudancaRotaGrupoFaturamento.addAll(atribuirValoresImovel(colecaoImoveis, imovelNaBase, rotaAnterior,
							rotaAtual, quadraDestino,
							ImovelAlterarInscricaoHelper.TITULO_RELACAO_LISTA_IMOVEIS_ALTERADOS_SEM_MUDANCA_ROTA_GRUPO_FATURAMENTO));
		}

		Object[] retorno = new Object[4];

		retorno[0] = listaImoveisAlteracaoAgendadaEncerramento;
		retorno[1] = listaImoveisAlterados;
		retorno[2] = listaImoveisAlteradosSemMudancaGrupoFaturamento;
		retorno[3] = listaImoveisAlteradosSemMudancaRotaGrupoFaturamento;

		return retorno;
	}

	/**
	 * [UC0074] - Alterar Inscrição do Imóvel
	 * [SB0007] - Atribuir Valores do Imóvel
	 * 
	 * @author Luciano Galvao
	 * @date 29/01/2013
	 */
	private Collection<ImovelAlterarInscricaoHelper> atribuirValoresImovel(Collection<Imovel> colecaoImoveis, Imovel imovelNaBase,
					Rota rotaAnterior, Rota rotaAtual, Quadra quadraDestino, String tituloRelacaoLista) throws ControladorException{

		Collection<ImovelAlterarInscricaoHelper> colecaoHelpers = new ArrayList<ImovelAlterarInscricaoHelper>();
		ImovelAlterarInscricaoHelper helper = null;

		if(!Util.isVazioOrNulo(colecaoImoveis)){

			for(Imovel imovelAlterado : colecaoImoveis){

				helper = new ImovelAlterarInscricaoHelper(tituloRelacaoLista);

				// 1.1. Matrícula (IMOV_ID da tabela IMOVEL).
				helper.setIdImovel(imovelAlterado.getId());

				// [UC0085 - Obter Endereço]
				helper.setEnderecoImovel(getControladorEndereco().pesquisarEndereco(imovelAlterado.getId()));

				// 1.2. Inscrição Anterior:
				if(imovelNaBase.getLocalidade() != null){
					helper.setIdLocalidadeAnterior(imovelNaBase.getLocalidade().getId());
				}
				if(imovelNaBase.getSetorComercial() != null){
					helper.setIdSetorComercialAnterior(imovelNaBase.getSetorComercial().getId());
					helper.setCodigoSetorComercialAnterior(imovelNaBase.getSetorComercial().getCodigo());
				}
				if(imovelNaBase.getQuadra() != null){
					helper.setIdQuadraAnterior(imovelNaBase.getQuadra().getId());
					helper.setNumeroQuadraAnterior(imovelNaBase.getQuadra().getNumeroQuadra());
				}
				helper.setLoteAnterior(imovelNaBase.getLote());
				helper.setSubLoteAnterior(imovelNaBase.getSubLote());

				if(imovelNaBase.getTestadaLote() != null){
					helper.setTestadaLoteAnterior(imovelNaBase.getTestadaLote());
				}
				if(rotaAnterior != null){
					helper.setIdRotaAnterior(rotaAnterior.getId());
					helper.setCodigoRotaAnterior(rotaAnterior.getCodigo());
				}
				if(rotaAnterior.getFaturamentoGrupo() != null){
					helper.setIdGrupoFaturamentoAnterior(rotaAnterior.getFaturamentoGrupo().getId());
				}

				// 1.6. Inscrição Atual:
				if(imovelAlterado.getLocalidade() != null){
					helper.setIdLocalidadeAtual(imovelAlterado.getLocalidade().getId());
				}

				// 1.6.2. Id do Setor Comercial (STCM_ID da tabela SETOR_COMERCIAL para
				// LOCA_ID=Localidade
				// da Inscrição Final e STCM_CDSETORCOMERCIAL=Setor Comercial da Inscrição Final,
				// caso o
				// Setor Comercial da Inscrição Final tenha sido informado; caso contrário, STCM_ID
				// da
				// tabela SETOR_COMERCIAL para LOCA_ID=Localidade da Inscrição Final e
				// STCM_CDSETORCOMERCIAL=STCM_CDSETORCOMERCIAL da tabela IMOVEL).

				SetorComercial setorComercialAtual = null;
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, imovelAlterado
								.getLocalidade().getId()));

				if(imovelAlterado.getSetorComercial() != null){
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
									imovelAlterado.getSetorComercial().getCodigo()));

					setorComercialAtual = (SetorComercial) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroSetorComercial,
									SetorComercial.class.getName()));

				}else if(imovelNaBase.getSetorComercial() != null){
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, imovelNaBase
									.getSetorComercial().getCodigo()));

					setorComercialAtual = (SetorComercial) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroSetorComercial,
									SetorComercial.class.getName()));
				}

				if(setorComercialAtual != null){
					helper.setIdSetorComercialAtual(setorComercialAtual.getId());
				}

				// 1.6.3. Código do Setor Comercial (Setor Comercial da Inscrição Final, caso o
				// Setor
				// Comercial da Inscrição Final tenha sido informado; caso contrário,
				// STCM_CDSETORCOMERCIAL
				// da tabela IMOVEL).
				if(imovelAlterado.getSetorComercial() != null){
					helper.setCodigoSetorComercialAtual(imovelAlterado.getSetorComercial().getCodigo());
				}else if(imovelNaBase.getSetorComercial() != null){
					helper.setCodigoSetorComercialAtual(imovelNaBase.getSetorComercial().getCodigo());
				}

				// 1.6.4. Id da Quadra (QDRA_ID da tabela QUADRA para STCM_ID=Id do Setor Comercial
				// da
				// Inscrição Final e QDRA_NNQUADRA=Quadra da Inscrição Final, caso a Quadra da
				// Inscrição
				// Final tenha sido informada; caso contrário, QDRA_ID da tabela QUADRA com
				// STCM_ID=Id do
				// Setor Comercial da Inscrição Final e QDRA_NNQUADRA=QDRA_NNQUADRA da tabela
				// IMOVEL, caso o
				// Setor Comercial da Inscrição Final tenha sido informado; caso contrário, QDRA_ID
				// da
				// tabela QUADRA com STCM_ID=(STCM_ID da tabela SETOR_COMERCIAL com
				// LOCA_ID=Localidade da
				// Inscrição Final e STCM_CDSETORCOMERCIAL=STCM_CDSETORCOMERCIAL da tabela IMOVEL) e
				// QDRA_NNQUADRA=QDRA_NNQUADRA da tabela IMOVEL).
				if(quadraDestino != null){
					helper.setIdQuadraAtual(quadraDestino.getId());
				}

				// 1.6.5. Número da Quadra (Quadra da Inscrição Final, caso a Quadra da Inscrição
				// Final
				// tenha sido informada; caso contrário, QDRA_NNQUADRA da tabela IMOVEL).
				if(imovelAlterado.getQuadra() != null){
					helper.setNumeroQuadraAtual(imovelAlterado.getQuadra().getNumeroQuadra());
				}else if(imovelNaBase.getQuadra() != null){
					helper.setNumeroQuadraAtual(imovelNaBase.getQuadra().getNumeroQuadra());
				}

				// 1.6.6. Lote (Lote da Inscrição Final, caso o Lote da Inscrição Final tenha sido
				// informado; caso contrário, IMOV_NNLOTE da tabela IMOVEL).
				if(imovelAlterado.getLote() > 0){
					helper.setLoteAtual(imovelAlterado.getLote());
				}else{
					helper.setLoteAtual(imovelNaBase.getLote());
				}

				helper.setSubLoteAtual(imovelNaBase.getSubLote());

				if(imovelNaBase.getTestadaLote() != null){
					helper.setTestadaLoteAtual(imovelNaBase.getTestadaLote());
				}
				if(rotaAtual != null){
					helper.setIdRotaAtual(rotaAtual.getId());
					helper.setCodigoRotaAtual(rotaAtual.getCodigo());
				}

				if(rotaAtual.getFaturamentoGrupo() != null){
					helper.setIdGrupoFaturamentoAtual(rotaAtual.getFaturamentoGrupo().getId());
				}

				colecaoHelpers.add(helper);
			}
		}

		return colecaoHelpers;

	}

	/**
	 * [UC0074] Alterar Inscrição do Imóvel
	 * Pesquisa a quadra destino da alteração de inscrição do imóvel
	 * 
	 * @author Luciano Galvao
	 * @date 29/01/2013
	 */
	private Quadra pesquisarQuadraDestino(Imovel imovelAlterado, Imovel imovelNaBase) throws ControladorException{

		FiltroQuadra filtroQuadra;
		filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ROTA);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.FATURAMENTO_GRUPO);

		// caso a Quadra da Inscrição Final tenha sido informada
		if(imovelAlterado.getQuadra() != null){

			// 5.2.2.1.2.1. Id da Quadra Destino será QDRA_ID da tabela QUADRA
			// para STCM_ID=Id do Setor Comercial da Inscrição Final e
			// QDRA_NNQUADRA=Quadra da Inscrição Final
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, imovelAlterado.getSetorComercial().getId()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, imovelAlterado.getQuadra().getNumeroQuadra()));

			// caso o Setor Comercial da Inscrição Final tenha sido informado
		}else if(imovelAlterado.getSetorComercial() != null){

			// QDRA_ID da tabela QUADRA com STCM_ID=Id do Setor Comercial da
			// Inscrição Final e QDRA_NNQUADRA=QDRA_NNQUADRA da tabela IMOVEL
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, imovelAlterado.getSetorComercial().getId()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, imovelNaBase.getQuadra().getNumeroQuadra()));
		}else{
			// QDRA_ID da tabela QUADRA com STCM_ID=(STCM_ID da tabela
			// SETOR_COMERCIAL com LOCA_ID=Localidade da Inscrição Final e
			// STCM_CDSETORCOMERCIAL=STCM_CDSETORCOMERCIAL da tabela IMOVEL) e
			// QDRA_NNQUADRA=QDRA_NNQUADRA da tabela IMOVEL

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, imovelAlterado.getLocalidade()
							.getId()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, imovelNaBase
							.getSetorComercial().getCodigo()));
			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroSetorComercial, SetorComercial.class.getName()));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, imovelNaBase.getQuadra().getNumeroQuadra()));
		}

		Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName()));
		return quadra;
	}

	private Rota pesquisarRota(Integer rotaId) throws ControladorException{

		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, rotaId));
		filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.FATURAMENTO_GRUPO);

		return (Rota) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroRota, Rota.class.getName()));
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

	public Collection filtrarImovelOutrosCriterios(String tipoMedicao) throws ControladorException{

		Collection<Imovel> collectionImovelOutrosCriterios = null;

		FiltroImovel filtroImovel = new FiltroImovel();

		if(tipoMedicao != null){
			if(tipoMedicao.equals("1")){ // Tipo Medição correspondente ligação de água
				filtroImovel.adicionarParametro(new ParametroNaoNulo(FiltroImovel.LIGACAO_AGUA_HIDROMETRO_INSTALACAO_HISTORICO_ID));
				collectionImovelOutrosCriterios = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
			}else if(tipoMedicao.equals("2")){ // Tipo Medição correspondente poço
				filtroImovel.adicionarParametro(new ParametroNaoNulo(FiltroImovel.IMOVEL_HIDROMETRO_INSTALACAO_HISTORICO_ID));
				collectionImovelOutrosCriterios = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
			}else if(tipoMedicao.equals("")){ // Tipo Medição correspondente sem medição
				filtroImovel.adicionarParametro(new ParametroNulo(FiltroImovel.IMOVEL_HIDROMETRO_INSTALACAO_HISTORICO_ID));
				collectionImovelOutrosCriterios = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
			}
		}

		return collectionImovelOutrosCriterios;
	}

	public Collection<Imovel> pesquisarImovelParametrosClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelParametrosClienteImovel(filtroClienteImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	// ----- Metodo Para Carregar o Objeto ImovelMicromedicao
	// ----- Flávio Leonardo
	public Collection carregarImovelMicromedicao(Collection imoveisMicromedicao) throws ControladorException{

		Collection retorno = new ArrayList();
		ImovelMicromedicao imovelMicromedicao = null;
		FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
		FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
		Collection pesquisaMedicao = new ArrayList();
		Collection pesquisaConsumo = new ArrayList();

		Iterator iterator = imoveisMicromedicao.iterator();
		while(iterator.hasNext()){
			imovelMicromedicao = (ImovelMicromedicao) iterator.next();
			// ---- Ferifica se já está preenchido com medicaoHistorico
			if(imovelMicromedicao.getMedicaoHistorico() == null && imovelMicromedicao.getImovel() != null
							&& imovelMicromedicao.getImovel().getId() != null){
				filtroMedicaoHistorico.limparListaParametros();
				pesquisaMedicao = new ArrayList();
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("medicaoTipo");
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.rota.faturamentoGrupo");
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.imovelPerfil");
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeFaturamento");
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeInformada");
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.hidrometroInstalacaoHistorico");
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("ligacaoAgua");

				if(imovelMicromedicao.getImovel() != null){
					filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.IMOVEL_ID, imovelMicromedicao
									.getImovel().getId(), ParametroSimples.CONECTOR_OR));

					filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID,
									imovelMicromedicao.getImovel().getId()));
					if(imovelMicromedicao.getImovel() != null && imovelMicromedicao.getImovel().getRota() != null
									&& imovelMicromedicao.getImovel().getRota().getFaturamentoGrupo() != null
									&& imovelMicromedicao.getImovel().getRota().getFaturamentoGrupo().getAnoMesReferencia() != null){
						filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(
										FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO, imovelMicromedicao.getImovel().getRota()
														.getFaturamentoGrupo().getAnoMesReferencia()));
					}

					pesquisaMedicao = getControladorUtil().pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class.getName());
					if(!pesquisaMedicao.isEmpty()){
						imovelMicromedicao.setMedicaoHistorico((MedicaoHistorico) pesquisaMedicao.iterator().next());
					}
				}
			}
			if(imovelMicromedicao.getConsumoHistorico() == null && imovelMicromedicao.getImovel() != null
							&& imovelMicromedicao.getImovel().getId() != null){
				filtroConsumoHistorico.limparListaParametros();
				filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade("ligacaoTipo");
				filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
				filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
				filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.rota.faturamentoGrupo");
				filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.imovelPerfil");
				filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade("consumoAnormalidade");
				filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.hidrometroInstalacaoHistorico");
				if(imovelMicromedicao.getImovel() != null){
					filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, imovelMicromedicao
									.getImovel().getId()));
					if(imovelMicromedicao.getImovel() != null && imovelMicromedicao.getImovel().getRota() != null
									&& imovelMicromedicao.getImovel().getRota().getFaturamentoGrupo() != null
									&& imovelMicromedicao.getImovel().getRota().getFaturamentoGrupo().getAnoMesReferencia() != null){
						filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.ANO_MES_FATURAMENTO,
										imovelMicromedicao.getImovel().getRota().getFaturamentoGrupo().getAnoMesReferencia()));
					}
					pesquisaConsumo = getControladorUtil().pesquisar(filtroConsumoHistorico, ConsumoHistorico.class.getName());

					if(!pesquisaConsumo.isEmpty()){
						imovelMicromedicao.setConsumoHistorico((ConsumoHistorico) pesquisaConsumo.iterator().next());
					}
				}
			}
			if(imovelMicromedicao.getImovel() != null && imovelMicromedicao.getImovel().getId() != null){
				retorno.add(imovelMicromedicao);
			}
		}

		return retorno;
	}

	/**
	 * [UC0123] - Obter Descriçoes da Categoria do Imóvel Autor: Sávio Luiz
	 * Data: 27/12/2005
	 */

	public Categoria obterDescricoesCategoriaImovel(Imovel imovel) throws ControladorException{

		Collection descricoes = null;
		try{
			descricoes = repositorioImovel.obterDescricoesCategoriaImovel(imovel);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Iterator descricoesIterator = descricoes.iterator();
		// Cria os objetos categoria
		Categoria categoria = new Categoria();
		boolean primeiraVez = true;
		boolean mesmaCategoria = false;
		String idCategoriaVerificar = null;
		String descricaoCategoria = null;
		String descricaoAbreviadaCategoria = null;

		while(descricoesIterator.hasNext()){
			// cria um array de objetos para pegar os parametros de
			// retorno da pesquisa
			Object[] arrayDescricoes = (Object[]) descricoesIterator.next();

			if(arrayDescricoes[0] != null){
				// id categoria
				if(primeiraVez){
					idCategoriaVerificar = "" + arrayDescricoes[0];
					mesmaCategoria = true;
				}else{
					if(idCategoriaVerificar.equals("" + arrayDescricoes[0])){
						mesmaCategoria = true;
					}else{
						mesmaCategoria = false;
					}
				}

			}
			// descrição da categoria
			if(arrayDescricoes[1] != null){

				if(primeiraVez){
					descricaoCategoria = "" + arrayDescricoes[1];
				}
			}
			// descrição abreviada da categoria
			if(arrayDescricoes[2] != null){

				if(primeiraVez){
					descricaoAbreviadaCategoria = "" + arrayDescricoes[2];
				}
			}

			primeiraVez = false;

		}
		if(mesmaCategoria){
			categoria.setDescricao(descricaoCategoria);
			categoria.setDescricaoAbreviada(descricaoAbreviadaCategoria);
		}else{
			categoria.setDescricao("MISTO");
			categoria.setDescricaoAbreviada("MIS");
		}
		return categoria;
	}

	/**
	 * [UC0185] Obter VAlor por Categoria Author: Rafael Santos Data: 29/12/2005
	 * Rateia um determinado valor entre as categorias do imóvel
	 * 
	 * @param colecaoCategorias
	 *            Coleção de Categorias
	 * @param valor
	 *            Valor
	 * @return Coleção com os valores por categorias
	 */
	public Collection obterValorPorCategoria(Collection<Categoria> colecaoCategorias, BigDecimal valor){

		List<BigDecimal> colecaoValoresPorCategoria = new ArrayList();

		// acuama a quantidae de ecnomias das acategorias
		int somatorioQuantidadeEconomiasCadaCategoria = 0;
		if(colecaoCategorias != null && !colecaoCategorias.isEmpty()){
			Iterator iteratorColecaoCategorias = colecaoCategorias.iterator();

			while(iteratorColecaoCategorias.hasNext()){
				Categoria categoria = (Categoria) iteratorColecaoCategorias.next();
				somatorioQuantidadeEconomiasCadaCategoria = somatorioQuantidadeEconomiasCadaCategoria
								+ categoria.getQuantidadeEconomiasCategoria().intValue();
			}

		}

		// calcula o fator de multiplicação
		BigDecimal somatorioEconomiasCadaCategoria = new BigDecimal(somatorioQuantidadeEconomiasCadaCategoria);

		BigDecimal fatorMultiplicacao = BigDecimal.ONE;
		if(somatorioEconomiasCadaCategoria.compareTo(BigDecimal.ZERO) == 1){
			fatorMultiplicacao = valor.divide(somatorioEconomiasCadaCategoria, 2, BigDecimal.ROUND_DOWN);
		}

		BigDecimal valorPorCategoriaAcumulado = new BigDecimal(0);

		// para cada categoria, calcula o Valor por Cageoria
		if(colecaoCategorias != null && !colecaoCategorias.isEmpty()){
			Iterator iteratorColecaoCategorias = colecaoCategorias.iterator();

			while(iteratorColecaoCategorias.hasNext()){
				Categoria categoria = (Categoria) iteratorColecaoCategorias.next();

				BigDecimal valorPorCategoria = new BigDecimal(0);

				valorPorCategoria = fatorMultiplicacao.multiply(new BigDecimal(categoria.getQuantidadeEconomiasCategoria()));

				valorPorCategoriaAcumulado = valorPorCategoriaAcumulado.add(valorPorCategoria.setScale(2, BigDecimal.ROUND_DOWN));

				colecaoValoresPorCategoria.add(valorPorCategoria.setScale(2, BigDecimal.ROUND_DOWN));
			}
		}

		// caso o valor por categoria acumulado seja menor que o valor
		// acuma a diferença no valor por cageoria da primeira
		if(valorPorCategoriaAcumulado.compareTo(valor) != 0){

			BigDecimal diferenca = valor.subtract(valorPorCategoriaAcumulado);

			colecaoValoresPorCategoria.set(0, colecaoValoresPorCategoria.get(0).add(diferenca));
		}

		return colecaoValoresPorCategoria;

	}

	/**
	 * Atualiza uma categoria no sistema
	 * 
	 * @author Roberta Costa
	 * @date 30/12/2005
	 * @param categoria
	 *            Categoria a ser atualizada
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public void atualizarCategoria(Categoria categoria) throws ControladorException{

		// Validações
		if(categoria.getConsumoEstouro() < categoria.getConsumoMinimo()){
			throw new ControladorException("atencao.categoria.consumoEstouro_menor_consumoMinimo", null, "");
		}
		if(categoria.getVezesMediaEstouro().compareTo(new BigDecimal(1)) == -1){
			throw new ControladorException("atencao.categoria.vezesMediaEstouro_menor_um", null, "");
		}
		if(categoria.getPorcentagemMediaBaixoConsumo().shortValue() < 1){
			throw new ControladorException("atencao.categoria.porcentagemMediaBaixoConsumo_menor_um", null, "");
		}
		if(categoria.getMediaBaixoConsumo() < categoria.getConsumoMinimo()){
			throw new ControladorException("atencao.categoria.mediaBaixoConsumo_menor_consumoMinimo", null, "");
		}
		if(categoria.getConsumoAlto() < categoria.getConsumoMinimo()){
			throw new ControladorException("atencao.categoria.consumoAlto_menor_consumoMinimo", null, "");
		}
		if(categoria.getConsumoAlto() > categoria.getConsumoEstouro()){
			throw new ControladorException("atencao.categoria.consumoAlto_maior_consumoEstouro", null, "");
		}
		if(categoria.getVezesMediaAltoConsumo().compareTo(new BigDecimal(1)) == -1){
			throw new ControladorException("atencao.categoria.vezesMediaAltoConsumo_menor_um", null, "");
		}
		if(categoria.getVezesMediaAltoConsumo().compareTo(categoria.getVezesMediaEstouro()) == 1){
			throw new ControladorException("atencao.categoria.vezesMediaAltoConsumo_maior_vezesMediaEstouro", null, "");
		}

		// Verifica se a descrição da Categoria já foi cadastrada

		FiltroCategoria filtroCategoria = new FiltroCategoria();

		if(categoria.getDescricao() != null && !categoria.getDescricao().equals("")){

			filtroCategoria.adicionarParametro(new ComparacaoTexto(FiltroCategoria.DESCRICAO, categoria.getDescricao()));

			Collection categorias = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

			if(categorias != null && !categorias.isEmpty()){
				Categoria categoriaNaBase = (Categoria) ((List) categorias).get(0);
				if(!categoria.equals(categoriaNaBase)){
					throw new ControladorException("atencao.categoria_ja_existente", null, "descricao");
				}
			}
		}

		// -------------Parte que atualiza um cliente na
		// base----------------------

		// Parte de Validacao com Timestamp
		filtroCategoria.limparListaParametros();

		// Seta o filtro para buscar categoria na base
		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, categoria.getId()));

		// Procura categoria na base
		Collection categoriaAtualizadas = getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

		Categoria categoriaAtualizada = (Categoria) Util.retonarObjetoDeColecao(categoriaAtualizadas);

		if(categoriaAtualizada == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Procura categoria na base
		Categoria categoriaNaBase = (Categoria) ((List) (getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName())))
						.get(0);

		// Verificar se categoria já foi atualizada por outro usuário durante
		// esta atualização
		if(categoriaNaBase.getUltimaAlteracao().after(categoria.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Atualiza a data de última alteração
		categoria.setUltimaAlteracao(new Date());

		// Atualiza categoria
		getControladorUtil().atualizar(categoria);

		// -------------Fim da parte que atualiza uma categoria na
		// base---------------
	}

	/**
	 * Insere uma categoria no sistema
	 * 
	 * @author Roberta Costa
	 * @date 04/01/2006
	 * @param categoria
	 *            Categoria a ser inserida
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public Integer inserirCategoria(Categoria categoria) throws ControladorException{

		// Validações
		if(categoria.getConsumoEstouro() < categoria.getConsumoMinimo()){
			throw new ControladorException("atencao.categoria.consumoEstouro_menor_consumoMinimo", null, "");
		}
		if(categoria.getVezesMediaEstouro().compareTo(new BigDecimal(1)) == -1){
			throw new ControladorException("atencao.categoria.vezesMediaEstouro_menor_um", null, "");
		}
		if(categoria.getMediaBaixoConsumo() < categoria.getConsumoMinimo()){
			throw new ControladorException("atencao.categoria.mediaBaixoConsumo_menor_consumoMinimo", null, "");
		}
		if(categoria.getPorcentagemMediaBaixoConsumo().compareTo(new BigDecimal("1")) == -1){
			throw new ControladorException("atencao.categoria.porcentagemMediaBaixoConsumo_menor_um", null, "");
		}
		if(categoria.getConsumoAlto() < categoria.getConsumoMinimo()){
			throw new ControladorException("atencao.categoria.consumoAlto_menor_consumoMinimo", null, "");
		}
		if(categoria.getConsumoAlto() > categoria.getConsumoEstouro()){
			throw new ControladorException("atencao.categoria.consumoAlto_maior_consumoEstouro", null, "");
		}
		if(categoria.getVezesMediaAltoConsumo().compareTo(new BigDecimal(1)) == -1){
			throw new ControladorException("atencao.categoria.vezesMediaAltoConsumo_menor_um", null, "");
		}
		if(categoria.getVezesMediaAltoConsumo().compareTo(categoria.getVezesMediaEstouro()) == 1){
			throw new ControladorException("atencao.categoria.vezesMediaAltoConsumo_maior_vezesMediaEstouro", null, "");
		}

		// Verifica se a descrição da Categoria já foi cadastrada
		FiltroCategoria filtroCategoria = new FiltroCategoria();

		if(categoria.getDescricao() != null && !categoria.getDescricao().equals("")){

			filtroCategoria.adicionarParametro(new ComparacaoTexto(FiltroCategoria.DESCRICAO, categoria.getDescricao()));

			Collection categorias = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

			if(categorias != null && !categorias.isEmpty()){
				throw new ControladorException("atencao.categoria_ja_existente", null, "descricao");
			}
		}

		// -------------Parte que insere uma categoria na
		// base----------------------

		Integer chaveCategoriaGerada = (Integer) getControladorUtil().inserir(categoria);

		categoria.setId(chaveCategoriaGerada);

		return chaveCategoriaGerada;
		// -------------Fim da parte que insere um categoria na
		// base---------------
	}

	/**
	 * Atualiza uma Subcategoria no sistema
	 * [UC0059] Manter Subcategoria
	 * 
	 * @author Fernanda Paiva
	 * @date 09/01/2006
	 * @param subcategoria
	 *            Subcategoria a ser atualizada
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public void atualizarSubcategoria(Subcategoria subcategoria, Subcategoria subcategoriaVelha) throws ControladorException{

		FiltroSubCategoria filtroSubcategoria = new FiltroSubCategoria();

		// Validações
		if(subcategoria.getDescricao() != null && !subcategoria.getDescricao().equals("")){

			filtroSubcategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CODIGO, subcategoria.getCodigo()));

			filtroSubcategoria
							.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, subcategoria.getCategoria().getId()));

			filtroSubcategoria.adicionarCaminhoParaCarregamentoEntidade("categoria");

			Collection subcategorias = this.getControladorUtil().pesquisar(filtroSubcategoria, Subcategoria.class.getName());

			if(subcategorias != null && !subcategorias.isEmpty()){

				Subcategoria subcategoriaNaBase = (Subcategoria) ((List) subcategorias).get(0);
				if(!subcategoria.equals(subcategoriaNaBase)){
					throw new ControladorException("atencao.subcategoria_ja_existente", null, "" + subcategoriaNaBase.getCodigo(),
									subcategoriaNaBase.getCategoria().getDescricao());
				}
			}
		}

		// -------------Parte que atualiza uma Subcategoria na
		// base----------------------

		// Parte de Validacao com Timestamp
		filtroSubcategoria.limparListaParametros();

		// Seta o filtro para buscar Subcategoria na base
		filtroSubcategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, subcategoria.getId()));

		Subcategoria subcategoriaNaBase = (Subcategoria) ((List) (getControladorUtil().pesquisar(filtroSubcategoria,
						Subcategoria.class.getName()))).get(0);

		// Verificar se Subcategoria já foi atualizada por outro usuário durante
		// esta atualização
		if(subcategoriaNaBase.getUltimaAlteracao().after(subcategoria.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Atualiza a data de última alteração
		subcategoria.setUltimaAlteracao(new Date());

		// Atualiza Subcategoria
		getControladorUtil().atualizar(subcategoria);

		// -------------Fim da parte que atualiza uma subcategoria na
		// base---------------
	}

	/**
	 * [UC0164] Filtrar Imovel Por Outros Criterios
	 * 
	 * @author Rhawi Dantas
	 * @created 29/12/2005
	 * @author Eduardo Henrique
	 * @date 24/04/2008
	 */
	public Collection pesquisarImovelOutrosCriterios(FiltrarImovelOutrosCriteriosHelper filtrarImovelOutrosCriteriosHelper)
					throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelOutrosCriterios(filtrarImovelOutrosCriteriosHelper);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 */
	public Collection pesquisarImovelSituacaoEspecialFaturamento(String valor,
					SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelSituacaoEspecialFaturamento(valor, situacaoEspecialFaturamentoHelper);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0177] Informar Situacao Especial de Cobranca
	 * 
	 * @author Sávio Luiz
	 * @created 07/03/2006
	 */
	public Collection pesquisarImovelSituacaoEspecialCobranca(String valor, SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper)
					throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelSituacaoEspecialCobranca(valor, situacaoEspecialCobrancaHelper);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 */
	public int validarMesAnoReferencia(SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper) throws ControladorException{

		try{
			return this.repositorioImovel.validarMesAnoReferencia(situacaoEspecialFaturamentoHelper);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa o maior ano mes de referencia da tabela de faturamento grupo
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public Integer validarMesAnoReferenciaCobranca(SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper)
					throws ControladorException{

		try{
			return this.repositorioImovel.validarMesAnoReferenciaCobranca(situacaoEspecialCobrancaHelper);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa uma coleção de categorias
	 * 
	 * @return Coleção de Categorias
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection<Categoria> pesquisarCategoria() throws ControladorException{

		try{
			return repositorioCategoria.pesquisarCategoria();
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * @return Quantidade de categorias cadastradas no BD
	 * @throws ControladorException
	 */
	public Object pesquisarObterQuantidadeCategoria() throws ControladorException{

		try{
			return repositorioCategoria.pesquisarObterQuantidadeCategoria();
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 * @author Felipe Rosacruz
	 * @date 16/04/2014
	 * @param collectionFaturmentoSituaoHistorico
	 *            Alteração para inserir o registro de transação
	 */
	public void atualizarFaturamentoSituacaoTipo(Integer idFaturamentoTipo,
					Collection<SituacaoEspecialFaturamentoHelper> helperParaSeremInseridos, Usuario usuarioLogado,
					Collection<SituacaoEspecialFaturamentoHelper> helperParaSeremRemovidos) throws ControladorException{

		Collection<Integer> imoveisParaSerInseridos = null;
		Collection<FaturamentoSituacaoHistorico> collectionFaturmentoSituaoHistorico = null;

		try{

			collectionFaturmentoSituaoHistorico = new ArrayList<FaturamentoSituacaoHistorico>();

			if(!Util.isVazioOrNulo(helperParaSeremInseridos)){

				imoveisParaSerInseridos = new ArrayList<Integer>();

				Iterator<SituacaoEspecialFaturamentoHelper> iterator = helperParaSeremInseridos.iterator();

				while(iterator.hasNext()){

					SituacaoEspecialFaturamentoHelper helper = iterator.next();


					// Inicio - Setando as Variaveis

					// Inicio - Construindo as variaveis
					FaturamentoSituacaoHistorico faturamentoSituacaoHistorico = new FaturamentoSituacaoHistorico();
					Imovel imovel = new Imovel();
					imovel.setId(new Integer(helper.getIdImovel()));

					imoveisParaSerInseridos.add(new Integer(helper.getIdImovel()));

					FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = new FaturamentoSituacaoMotivo();
					faturamentoSituacaoMotivo.setId(new Integer(helper.getIdFaturamentoSituacaoMotivo()));

					FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
					faturamentoSituacaoTipo.setId(new Integer(helper.getIdFaturamentoSituacaoTipo()));


					faturamentoSituacaoHistorico.setImovel(imovel);
					faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoInicio(new Integer(Util
									.formatarMesAnoComBarraParaAnoMes(helper.getMesAnoReferenciaFaturamentoInicial())));
					faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoFim(new Integer(Util.formatarMesAnoComBarraParaAnoMes(helper
									.getMesAnoReferenciaFaturamentoInicial())));
					faturamentoSituacaoHistorico.setAnoMesFaturamentoRetirada(null);
					faturamentoSituacaoHistorico.setFaturamentoSituacaoMotivo(faturamentoSituacaoMotivo);
					faturamentoSituacaoHistorico.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);
					faturamentoSituacaoHistorico.setUltimaAlteracao(Calendar.getInstance().getTime());
					faturamentoSituacaoHistorico.setUsuario(usuarioLogado);

					if(helper.getVolume() != null && !helper.getVolume().equals("")){
						faturamentoSituacaoHistorico.setVolume(new Integer(helper.getVolume()));
					}
					if(helper.getPercentualEsgoto() != null && !helper.getPercentualEsgoto().equals("")){

						Double pctEsgotoDouble = Double.parseDouble(helper.getPercentualEsgoto().replace(',', '.'));
						faturamentoSituacaoHistorico.setPercentualEsgoto(new BigDecimal(pctEsgotoDouble));
					}
					// Fim - Setando as Variaveis

					// Inicio - Adiciona à coleção
					collectionFaturmentoSituaoHistorico.add(faturamentoSituacaoHistorico);
					// Fim - Adiciona à coleção
				}

			}

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_VALIDAR_SITUACAO_ESPECIAL_FATURAMENTO,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			getControladorFaturamento().inserirFaturamentoSituacaoHistorico(collectionFaturmentoSituaoHistorico, registradorOperacao);
			this.repositorioImovel.atualizarFaturamentoSituacaoTipo(imoveisParaSerInseridos, idFaturamentoTipo);

			if(helperParaSeremRemovidos != null && !helperParaSeremRemovidos.isEmpty()){
				this.retirarSituacaoEspecialFaturamento(helperParaSeremRemovidos, usuarioLogado);
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Atualiza o id da cobrança situação tipo da tabela imóvel com o id da
	 * situação escolhido pelo usuario
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public void atualizarCobrancaSituacaoTipo(Collection colecaoIdsImoveis, Integer idCobrancaTipo) throws ControladorException{

		try{
			this.repositorioImovel.atualizarCobrancaSituacaoTipo(colecaoIdsImoveis, idCobrancaTipo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @param usuarioLogado
	 * @created 16/01/2006
	 */
	public void retirarSituacaoEspecialFaturamento(Collection<SituacaoEspecialFaturamentoHelper> colecaoSituacaoEspecialFaturamentoHelper,
					Usuario usuarioLogado) throws ControladorException{

		try{
			Collection pesquisarImoveisParaSerRemovidos = new ArrayList();
			Iterator<SituacaoEspecialFaturamentoHelper> colecaoFaturamentoSituacaoHistoricoIterator = colecaoSituacaoEspecialFaturamentoHelper
							.iterator();

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_VALIDAR_SITUACAO_ESPECIAL_FATURAMENTO,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			Operacao operacao = new Operacao(Operacao.OPERACAO_VALIDAR_SITUACAO_ESPECIAL_FATURAMENTO);
			operacaoEfetuada.setOperacao(operacao);

			while(colecaoFaturamentoSituacaoHistoricoIterator.hasNext()){

				SituacaoEspecialFaturamentoHelper helper = colecaoFaturamentoSituacaoHistoricoIterator.next();
				Integer anoMesReferenciaFaturamentoGrupo = repositorioImovel.validarMesAnoReferencia(helper);

				if(helper != null && !helper.getIdImovel().equals("")){
					FiltroFaturamentoSituacaoHistorico filtroFaturamentoSituacaoHistorico = new FiltroFaturamentoSituacaoHistorico();
					filtroFaturamentoSituacaoHistorico.adicionarParametro(new ParametroSimples("imovel.id", helper.getIdImovel()));
					filtroFaturamentoSituacaoHistorico.adicionarParametro(new ParametroNulo("anoMesFaturamentoRetirada"));
					Collection colecaoFaturamentoSituacaoHistorico = repositorioUtil.pesquisar(filtroFaturamentoSituacaoHistorico,
									FaturamentoSituacaoHistorico.class.getName());
					for(Object object : colecaoFaturamentoSituacaoHistorico){
						FaturamentoSituacaoHistorico faturamentoSituacaoHistorico = (FaturamentoSituacaoHistorico) object;
						faturamentoSituacaoHistorico.setAnoMesFaturamentoRetirada(anoMesReferenciaFaturamentoGrupo);
						faturamentoSituacaoHistorico.setUltimaAlteracao(new Date());

						faturamentoSituacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
						registradorOperacao.registrarOperacao(faturamentoSituacaoHistorico);

					}
					repositorioUtil.atualizarColecaoObjetos(colecaoFaturamentoSituacaoHistorico);
				}else{
					Collection<Integer> collectionIdImoveis = this.repositorioFaturamento
									.consultaIdImovelParaAtualizarAnoMesFaturamentoSituacaoHistorico(helper);
					if(collectionIdImoveis != null && !collectionIdImoveis.isEmpty()){
						Iterator icolecaoMatriculasImoveis = collectionIdImoveis.iterator();

						while(icolecaoMatriculasImoveis.hasNext()){

							Integer id = (Integer) icolecaoMatriculasImoveis.next();

							FiltroFaturamentoSituacaoHistorico filtroFaturamentoSituacaoHistorico = new FiltroFaturamentoSituacaoHistorico();
							filtroFaturamentoSituacaoHistorico.adicionarParametro(new ParametroSimples("imovel.id", id));
							filtroFaturamentoSituacaoHistorico.adicionarParametro(new ParametroNulo("anoMesFaturamentoRetirada"));
							Collection colecaoFaturamentoSituacaoHistorico = repositorioUtil.pesquisar(filtroFaturamentoSituacaoHistorico,
											FaturamentoSituacaoHistorico.class.getName());
							for(Object object : colecaoFaturamentoSituacaoHistorico){
								FaturamentoSituacaoHistorico faturamentoSituacaoHistorico = (FaturamentoSituacaoHistorico) object;

								faturamentoSituacaoHistorico.setAnoMesFaturamentoRetirada(anoMesReferenciaFaturamentoGrupo);
								faturamentoSituacaoHistorico.setUltimaAlteracao(new Date());

								faturamentoSituacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
								registradorOperacao.registrarOperacao(faturamentoSituacaoHistorico);

							}
							repositorioUtil.atualizarColecaoObjetos(colecaoFaturamentoSituacaoHistorico);

						}
					}

				}
				pesquisarImoveisParaSerRemovidos.add(Integer.valueOf(helper.getIdImovel()).intValue());
			}

			this.repositorioImovel.retirarSituacaoEspecialFaturamento(pesquisarImoveisParaSerRemovidos);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Seta para null o id da cobrança situação tipo da tabela imóvel
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */
	public void retirarSituacaoEspecialCobranca(SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper, Usuario usuarioExclusao)
					throws ControladorException{

		try{
			Integer anoMesReferenciaFaturamentoGrupo = repositorioImovel.validarMesAnoReferenciaCobranca(situacaoEspecialCobrancaHelper);

			this.repositorioCobranca.atualizarAnoMesCobrancaSituacaoHistorico(situacaoEspecialCobrancaHelper,
							anoMesReferenciaFaturamentoGrupo, usuarioExclusao.getId());

			Collection pesquisarImoveisParaSerRemovidos = repositorioImovel.pesquisarImovelSituacaoEspecialCobranca("COM",
							situacaoEspecialCobrancaHelper);

			this.repositorioImovel.retirarSituacaoEspecialCobranca(pesquisarImoveisParaSerRemovidos);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Collection<Integer> pesquisarImoveisIds(Integer rotaId) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImoveisIds(rotaId);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [FS0005] - Verificar existência da conta
	 * 
	 * @author Anderson Italo
	 * @date 02/08/2011
	 * @return Object
	 * @exception ErroRepositorioException
	 */
	public Object pesquisarImovelIdComConta(Integer imovelId, Integer anoMesReferencia) throws ControladorException{

		try{

			return this.repositorioImovel.pesquisarImovelIdComConta(imovelId, anoMesReferencia);
		}catch(ErroRepositorioException ex){

			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Integer verificarExistenciaImovel(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.verificarExistenciaImovel(idImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Obtém as Opções de Parcelamento do Débito do Imóvel
	 * [SF0002] Obter Opções Parcelamento
	 * Obtém o perfil do imóvel
	 * 
	 * @author Roberta Costa
	 * @date 21/03/2006
	 * @param situacaoAguaId
	 * @param situacaoEsgotoId
	 * @return ImovelSituacao
	 * @throws ControladorException
	 */
	public ImovelSituacao obterSituacaoImovel(Integer situacaoAguaId, Integer situacaoEsgotoId) throws ControladorException{

		FiltroImovelSituacao filtroImovelSituacao = new FiltroImovelSituacao();

		filtroImovelSituacao.adicionarParametro(new ParametroSimples(FiltroImovelSituacao.LIGACAO_AGUA_SITUACAO_ID, situacaoAguaId));
		if(situacaoEsgotoId != null && !situacaoEsgotoId.equals("")){
			filtroImovelSituacao
							.adicionarParametro(new ParametroSimples(FiltroImovelSituacao.LIGACAO_ESGOTO_SITUACAO_ID, situacaoEsgotoId));
		}else{
			filtroImovelSituacao.adicionarParametro(new ParametroNulo(FiltroImovelSituacao.LIGACAO_ESGOTO_SITUACAO_ID));
		}
		filtroImovelSituacao.adicionarCaminhoParaCarregamentoEntidade("imovelSituacaoTipo");

		Collection<ImovelSituacao> colecaoImovelSituacao = this.getControladorUtil().pesquisar(filtroImovelSituacao,
						ImovelSituacao.class.getName());

		ImovelSituacao imovelSituacao = null;

		if(colecaoImovelSituacao != null && !colecaoImovelSituacao.isEmpty()){
			// Pega a primeira ocorrência da coleção
			imovelSituacao = (ImovelSituacao) Util.retonarObjetoDeColecao(colecaoImovelSituacao);
		}

		return imovelSituacao;
	}

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Este fluxo secundário tem como objetivo pesquisar o imóvel digitado pelo
	 * usuário
	 * [FS0008] - Verificar existência da matrícula do imóvel
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovelDigitado
	 * @return
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelDigitado(Integer idImovelDigitado) throws ControladorException{

		// Cria a variável que vai armazenar o imóvel pesquisado
		Imovel imovelDigitado = null;

		// Pesquisa o imóvel informado pelo usuário no sistema
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		/*
		 * filtroImovel.adicionarCaminhoParaCarregamentoEntidade("lote");
		 * filtroImovel.adicionarCaminhoParaCarregamentoEntidade("subLote");
		 */
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgoto");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAgua");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovelDigitado));
		Collection colecaoImovel = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		// Caso exista o imóvel no sistema
		// Retorna para o usuário o imóvel retornado pela pesquisa
		// Caso contrário retorna um objeto nulo
		if(colecaoImovel != null && !colecaoImovel.isEmpty()){
			imovelDigitado = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
		}

		// Retorna o imóvel encontrado ou nulo se não existir
		return imovelDigitado;
	}

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica se a localidade informada é a mesma do imóvel informado
	 * [FS0009] - Verificar localidade da matrícula do imóvel
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idLocalidadeInformada
	 * @param imovelInformado
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarLocalidadeMatriculaImovel(String idLocalidadeInformada, Imovel imovelInformado) throws ControladorException{

		// Caso o imóvel tenha sido informado
		if(imovelInformado != null){
			// Recupera a localidade do imóvel
			Localidade localidadeImovel = imovelInformado.getLocalidade();

			Integer codigoLocalidade = Integer.valueOf(idLocalidadeInformada);

			// Caso a localidade informada pelo usuário seja a mesma do imóvel
			// Retorna "true" indicandoque a localidade é a mesma
			// Caso contrário retorna "false" indicando que a localidade é
			// diferente
			if(codigoLocalidade.intValue() == localidadeImovel.getId().intValue()){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica se o usuário informou o imóvel ou o cliente, não pode existir os
	 * doi nem nenhum
	 * [FS0010] Verificar preenchimento do imóvel e do cliente
	 * 
	 * @author Pedro Alexandre
	 * @date 24/03/2006
	 * @param idImovel
	 * @param idCliente
	 * @throws ControladorException
	 */
	public void verificarPreeenchimentoImovelECliente(String idImovel, String idCliente) throws ControladorException{

		// Caso o usuário não tenha informado o imóvel
		if(idImovel == null || idImovel.trim().equalsIgnoreCase("")){
			// Caso o usuário não tenha informado o cliente, levanta uma exceção
			// indicando que o
			// usuário não informou nem o imóvel nem o cliente
			if(idCliente == null || idCliente.trim().equalsIgnoreCase("")){
				throw new ControladorException("atencao.naoinformado", null, "Matrícula do Imóvel ou Código do Cliente");
			}
		}else{
			// Caso o usuário tenha informado o imóvel e o cliente, levanta uma
			// exceção para
			// indicando que só pode informar um dos dois
			if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
				throw new ControladorException("atencao.cliente_imovel_informado");
			}
		}
	}

	/**
	 * [UC0224] Inserir Situação do imóvel
	 * Verifica se o usuário informou o tipo da situação do imóvel
	 * [FS0001] Verificar existencia de dados
	 * 
	 * @author Rômulo Aurélio
	 * @date 29/03/2006
	 * @param idImovelSituacaoTipo
	 * @param idLigacaoAguaSituacao
	 * @param idLigacaoEsgotoSituacao
	 * @throws ControladorException
	 */
	public Integer inserirSituacaoImovel(String idImovelSituacaoTipo, String idLigacaoAguaSituacao, String idLigacaoEsgotoSituacao)
					throws ControladorException{

		String novoIdLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		if(idImovelSituacaoTipo == null || idImovelSituacaoTipo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.imovel_situacao_tipo_nao_informado");
		}
		if(idLigacaoAguaSituacao == null || idLigacaoAguaSituacao.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.imovel_situacao_agua_ligacao_nao_informado");
		}

		if(novoIdLigacaoEsgotoSituacao.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			novoIdLigacaoEsgotoSituacao = null;
		}

		FiltroImovelSituacao filtroImovelSituacao = new FiltroImovelSituacao();
		filtroImovelSituacao.adicionarParametro(new ParametroSimples(FiltroImovelSituacao.SITUACAO_TIPO, idImovelSituacaoTipo));
		filtroImovelSituacao.adicionarParametro(new ParametroSimples(FiltroImovelSituacao.LIGACAO_AGUA_SITUACAO_ID, idLigacaoAguaSituacao));
		if(novoIdLigacaoEsgotoSituacao != null){
			filtroImovelSituacao.adicionarParametro(new ParametroSimples(FiltroImovelSituacao.LIGACAO_ESGOTO_SITUACAO_ID,
							novoIdLigacaoEsgotoSituacao));
		}else{
			filtroImovelSituacao.adicionarParametro(new ParametroNulo(FiltroImovelSituacao.LIGACAO_ESGOTO_SITUACAO_ID));
		}

		Collection colecaoImovelSituacao = getControladorUtil().pesquisar(filtroImovelSituacao, ImovelSituacao.class.getName());

		if(colecaoImovelSituacao != null && !colecaoImovelSituacao.isEmpty()){
			throw new ControladorException("atencao.imovel_situacao_ja_existe_no_cadastro");
		}

		ImovelSituacaoTipo imovelSituacaoTipo = new ImovelSituacaoTipo();
		imovelSituacaoTipo.setId(Integer.valueOf(idImovelSituacaoTipo));

		LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
		ligacaoAguaSituacao.setId(Integer.valueOf(idLigacaoAguaSituacao));

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
		ImovelSituacao imovelSituacao = new ImovelSituacao();

		if(novoIdLigacaoEsgotoSituacao != null && !novoIdLigacaoEsgotoSituacao.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			ligacaoEsgotoSituacao.setId(Integer.valueOf(novoIdLigacaoEsgotoSituacao));
			imovelSituacao.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);

		}

		imovelSituacao.setImovelSituacaoTipo(imovelSituacaoTipo);
		imovelSituacao.setLigacaoAguaSituacao(ligacaoAguaSituacao);
		imovelSituacao.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_SITUACAO_INSERIR,
						new UsuarioAcaoUsuarioHelper(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_IMOVEL_SITUACAO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		imovelSituacao.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(imovelSituacao);

		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		Integer idImovelSituacao = (Integer) getControladorUtil().inserir(imovelSituacao);

		return idImovelSituacao;

	}

	/**
	 * Obtém a principal categoria do imóvel
	 * [UC0306] Obter Principal Categoria do Imóvel
	 * 
	 * @author Pedro Alexandre, Ivan Sérgio
	 * @date 18/04/2006, 14/08/2007
	 * @alteracao - Correcao no [FS0001 - Verificar mais de uma categoria...]
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Categoria obterPrincipalCategoriaImovel(Integer idImovel) throws ControladorException{

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		return obterPrincipalCategoriaImovel(idImovel, sistemaParametro.getCodigoEmpresaFebraban(), true);
	}

	/**
	 * Obtém a principal categoria do imóvel
	 * [UC0306] Obter Principal Categoria do Imóvel
	 * 
	 * @author Pedro Alexandre, Ivan Sérgio
	 * @date 18/04/2006, 14/08/2007
	 * @alteracao - Correcao no [FS0001 - Verificar mais de uma categoria...]
	 * @author Luciano Galvao - Este método já recebe o código da empresa Febraban, parâmetro do
	 *         sistema necessário neste caso de uso. Foi mantido o método
	 *         obterPrincipalCategoriaImovel(Integer idImovel), que consulta os parâmetros do
	 *         sistema e passa o código para este método. Esta separação foi feita para possibilitar
	 *         o uso deste método com o consulta prévia dos parâmetros do sistema em uma única vez.
	 *         Se este método é chamado dentro de um laço, é interessante utilizá-lo para evitar
	 *         consultas desnecessárias a tabela sistema_parametros
	 * @date 15/03/2013
	 * @param idImovel
	 * @param construirObjetoCompleto
	 *            <true> se for necessário carregar o objeto Categoria completo. <false> caso seja
	 *            necessário apenas o Id e a quantidade de economias
	 * @return
	 * @throws ControladorException
	 */
	public Categoria obterPrincipalCategoriaImovel(Integer idImovel, Short codigoEmpresaFebraban, boolean construirObjetoCompleto)
					throws ControladorException{

		// Cria a variável que vai armazenar a categoria principal do imóvel
		Categoria categoriaPrincipal = null;

		// Cria a coleção que vai armazenar as categorias com maiorquantidade de
		// economias
		Collection<Categoria> colecaoCategoriasComMaiorQtdEconomias = new ArrayList();

		// [UC0108] Obtém a quantidade de economias por categoria
		Collection<Categoria> colecaoCategoriasImovel = this.obterQuantidadeEconomiasCategoria(idImovel, construirObjetoCompleto);

		// Inicializa a quantidade de categoria
		int quantidadeCategoria = -1;

		// Caso a coleção de categorias do imóvel não esteja nula
		if(colecaoCategoriasImovel != null){
			// Laço para verificar qual a categoria com maior quantidade de
			// economia
			for(Categoria categoriaImovel : colecaoCategoriasImovel){
				if(quantidadeCategoria < categoriaImovel.getQuantidadeEconomiasCategoria().intValue()){
					quantidadeCategoria = categoriaImovel.getQuantidadeEconomiasCategoria().intValue();

					colecaoCategoriasComMaiorQtdEconomias = new ArrayList();
					colecaoCategoriasComMaiorQtdEconomias.add(categoriaImovel);
				}else if(quantidadeCategoria == categoriaImovel.getQuantidadeEconomiasCategoria().intValue()){
					colecaoCategoriasComMaiorQtdEconomias.add(categoriaImovel);
				}
			}
		}

		// [FS0001]Verificar mais de uma categoria com a maior quantidade de
		// economias

		// Caso só exista um objeto na coleção, recuperaa categoria a atribui a
		// categoria principal
		// Caso contrário recupera a categoria com o menor id
		if(colecaoCategoriasComMaiorQtdEconomias.size() == 1){
			categoriaPrincipal = colecaoCategoriasComMaiorQtdEconomias.iterator().next();
		}else if(colecaoCategoriasComMaiorQtdEconomias.size() > 1){
			/*
			 * for (Categoria categoriaImovel :
			 * colecaoCategoriasComMaiorQtdEconomias) { int idTemp = -1; if
			 * (idTemp < categoriaImovel.getId().intValue()) { idTemp =
			 * categoriaImovel.getId().intValue(); categoriaPrincipal =
			 * categoriaImovel; } }
			 */
			int idTemp = -1;
			if(!codigoEmpresaFebraban.equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAERN)){
				for(Categoria categoriaImovel : colecaoCategoriasComMaiorQtdEconomias){
					if(idTemp == -1){
						idTemp = categoriaImovel.getId().intValue();
						categoriaPrincipal = categoriaImovel;
					}else if(categoriaImovel.getId().intValue() < idTemp){
						idTemp = categoriaImovel.getId().intValue();
						categoriaPrincipal = categoriaImovel;
					}
				}
			}else{
				for(Categoria categoriaImovel : colecaoCategoriasComMaiorQtdEconomias){
					if(idTemp == -1){
						idTemp = categoriaImovel.getId().intValue();
						categoriaPrincipal = categoriaImovel;
					}else if(categoriaImovel.getId().intValue() > idTemp){
						idTemp = categoriaImovel.getId().intValue();
						categoriaPrincipal = categoriaImovel;
					}
				}
			}

		}

		// Retorna a categoria principal
		return categoriaPrincipal;
	}

	/**
	 * Obtém o indicador de existência de hidrômetro para o imóvel, caso exista
	 * retorna 1(um) indicando SIM caso contrário retorna 2(dois) indicando NÃO
	 * [UC0307] Obter Indicador de Existência de Hidrômetro no Imóvel
	 * 
	 * @author Pedro Alexandre
	 * @date 18/04/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public int obterIndicadorExistenciaHidrometroImovel(Integer idImovel) throws ControladorException{

		// Inicia o indicador para 2(dois) NÃO
		int retorno = 2;

		try{

			Integer indicador = this.repositorioImovel.obterIndicadorExistenciaHidrometroImovel(idImovel);

			if(indicador != null){
				retorno = indicador.intValue();
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		// // Pesquisa o imóvel informado
		// Imovel imovel = this.pesquisarImovelDigitado(idImovel);
		//
		// // Caso a situação da ligação de água do imóvel seja igual a "Ligado"
		// ou
		// // "Cortado" e
		// // exista hidrômetro instalado na ligação de água, atribui 1(um)
		// // indicando SIM
		// // Caso contrário e caso a situação da ligação de esgoto corresponda
		// a
		// // "Ligado" e exista hidrômetro
		// // instalado no poço, atribui 1(um) indicando SIM
		// if ((imovel.getLigacaoAguaSituacao().getId() ==
		// LigacaoAguaSituacao.LIGADO || imovel
		// .getLigacaoAguaSituacao().getId() == LigacaoAguaSituacao.CORTADO)
		// && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() !=
		// null) {
		// indicadorExistenciaHidrometro = 1;
		// } else if (imovel.getLigacaoEsgotoSituacao().getId() ==
		// LigacaoEsgotoSituacao.LIGADO
		// && imovel.getHidrometroInstalacaoHistorico() != null) {
		// indicadorExistenciaHidrometro = 1;
		// }

		// Retorna o indicador de existência de hidrômetro para o imóvel
		return retorno;
	}

	public Collection pesquisarSubcategoriasImovelRelatorio(Integer idImovel) throws ControladorException{

		Collection retorno = null;

		try{

			retorno = this.repositorioImovel.pesquisarSubcategoriasImovelRelatorio(idImovel);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Gerar Relatório de Imóveis Outros Critérios
	 * 
	 * @author Rafael Corrêa
	 * @date 25/07/2006
	 * @param idImovelCondominio
	 * @param idImovelPrincipal
	 * @param idNomeConta
	 * @param idSituacaoLigacaoAgua
	 * @param consumoMinimoInicialAgua
	 * @param consumoMinimoFinalAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param consumoMinimoInicialEsgoto
	 * @param consumoMinimoFinalEsgoto
	 * @param intervaloValorPercentualEsgotoInicial
	 * @param intervaloValorPercentualEsgotoFinal
	 * @param intervaloMediaMinimaImovelInicial
	 * @param intervaloMediaMinimaImovelFinal
	 * @param intervaloMediaMinimaHidrometroInicial
	 * @param intervaloMediaMinimaHidrometroFinal
	 * @param idImovelPerfil
	 * @param idPocoTipo
	 * @param idFaturamentoSituacaoTipo
	 * @param idCobrancaSituacaoTipo
	 * @param idSituacaoEspecialCobranca
	 * @param idEloAnormalidade
	 * @param areaConstruidaInicial
	 * @param areaConstruidaFinal
	 * @param idCadastroOcorrencia
	 * @param idConsumoTarifa
	 * @param idGerenciaRegional
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorComercialInicial
	 * @param setorComercialFinal
	 * @param quadraInicial
	 * @param quadraFinal
	 * @param loteOrigem
	 * @param loteDestno
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param municipio
	 * @param idTipoMedicao
	 * @param indicadorMedicao
	 * @param idSubCategoria
	 * @param idCategoria
	 * @param quantidadeEconomiasInicial
	 * @param quantidadeEconomiasFinal
	 * @param diaVencimento
	 * @param idCliente
	 * @param idClienteTipo
	 * @param idClienteRelacaoTipo
	 * @param numeroPontosInicial
	 * @param numeroPontosFinal
	 * @param numeroMoradoresInicial
	 * @param numeroMoradoresFinal
	 * @param idAreaConstruidaFaixa
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarRelatorioImovelOutrosCriterios(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String cdRotaInicial,
					String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String consumoFixadoEsgotoPocoInicial,
					String consumoFixadoEsgotoPocoFinal) throws ControladorException{

		Collection colecaoImoveis = null;

		try{
			// remove primeiro as linhas do critério cobrança
			colecaoImoveis = repositorioImovel.gerarRelatorioImovelOutrosCriterios(idImovelCondominio, idImovelPrincipal,
							idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,
							consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial,
							intervaloValorPercentualEsgotoFinal,

							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal,

							idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,
							idEloAnormalidade, areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,
							idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal, setorComercialInicial, setorComercialFinal,
							quadraInicial, quadraFinal, loteOrigem, loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao,
							indicadorMedicao, idSubCategoria, idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal,
							diaVencimento, idCliente, idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal,
							numeroMoradoresInicial, numeroMoradoresFinal, idAreaConstruidaFaixa, idUnidadeNegocio, cdRotaInicial,
							cdRotaFinal, sequencialRotaInicial, sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial,
							subloteFinal, consumoFixadoEsgotoPocoInicial, consumoFixadoEsgotoPocoFinal);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// colecao com os dados para o relatorio
		Collection colecaoGerarRelatorioImovelOutrosCriterios = null;

		if(colecaoImoveis == null || colecaoImoveis.isEmpty()){
			throw new ControladorException("atencao.filtro_consumo_tarifa_sem_records");
		}

		// para cada imovel pega as conta, debitos, creditos e guias
		if(colecaoImoveis != null && !colecaoImoveis.isEmpty()){

			Iterator iteratorColecaoImoveis = colecaoImoveis.iterator();
			colecaoGerarRelatorioImovelOutrosCriterios = new ArrayList();

			ImovelRelatorioHelper imovelRelatorioHelper = null;
			// GerarRelacaoDebitosHelper gerarRelacaoDebitosHelper = null;
			while(iteratorColecaoImoveis.hasNext()){

				Object[] contasDadosRelatorio = (Object[]) iteratorColecaoImoveis.next();

				imovelRelatorioHelper = new ImovelRelatorioHelper();

				// gerarRelacaoDebitosHelper = new GerarRelacaoDebitosHelper();

				// id gerencia regional
				if(contasDadosRelatorio[0] != null){ // 0
					imovelRelatorioHelper.setIdGerenciaRegional(((Integer) contasDadosRelatorio[0]));
				}
				// nome abreviado gerencia regional
				if(contasDadosRelatorio[1] != null){ // 1
					imovelRelatorioHelper.setDescricaoGerenciaRegional((String) contasDadosRelatorio[1]);
				}
				// id localidade
				if(contasDadosRelatorio[2] != null){ // 2
					imovelRelatorioHelper.setIdLocalidade(((Integer) contasDadosRelatorio[2]));
				}
				// descricao localidade
				if(contasDadosRelatorio[3] != null){ // 3
					imovelRelatorioHelper.setDescricaoLocalidade((String) contasDadosRelatorio[3]);
				}

				// id unidade negócio
				if(contasDadosRelatorio[87] != null){ // 87
					imovelRelatorioHelper.setIdUnidadeNegocio((Integer) contasDadosRelatorio[87]);
				}
				// unidade negocio
				if(contasDadosRelatorio[88] != null){ // 88
					imovelRelatorioHelper.setNomeUnidadeNegocio((String) contasDadosRelatorio[88]);
				}

				// id imovel
				if(contasDadosRelatorio[4] != null){ // 4
					imovelRelatorioHelper.setMatriculaImovel(((Integer) contasDadosRelatorio[4]));
				}
				// quantidade de economias
				if(contasDadosRelatorio[5] != null){ // 5
					imovelRelatorioHelper.setQuantidadeEconomia(Integer.valueOf(((Short) contasDadosRelatorio[5])));
				}
				// codigo setor comercial
				if(contasDadosRelatorio[6] != null){ // 6
					imovelRelatorioHelper.setCodigoSetorComercial(((Integer) contasDadosRelatorio[6]));
				}
				// descrição setor comercial
				if(contasDadosRelatorio[7] != null){ // 7
					imovelRelatorioHelper.setDescricaoSetorComercial(((String) contasDadosRelatorio[7]));
				}
				// numero quadra
				if(contasDadosRelatorio[8] != null){ // 8
					imovelRelatorioHelper.setNumeroQuadra(((Integer) contasDadosRelatorio[8]));
				}
				// lote
				if(contasDadosRelatorio[9] != null){ // 9
					imovelRelatorioHelper.setNumeroLote(((Short) contasDadosRelatorio[9]));
				}
				// sub lote
				if(contasDadosRelatorio[10] != null){ // 10
					imovelRelatorioHelper.setNumeroSubLote(((Short) contasDadosRelatorio[10]));
				}
				// descricao ligacao agua situacao
				if(contasDadosRelatorio[11] != null){ // 11
					imovelRelatorioHelper.setLigacaoAguaSituacao((String) contasDadosRelatorio[11]);
				}
				// descricao ligacao esgoto situacao
				if(contasDadosRelatorio[12] != null){ // 12
					imovelRelatorioHelper.setLigacaoEsgotoSituacao((String) contasDadosRelatorio[12]);
				}
				// percentual
				if(contasDadosRelatorio[13] != null){ // 13
					imovelRelatorioHelper.setPercentual((BigDecimal) contasDadosRelatorio[13]);
				}
				// data ligação esgoto
				if(contasDadosRelatorio[14] != null){ // 14
					imovelRelatorioHelper.setDataLigacaoEsgoto((Date) contasDadosRelatorio[14]);
				}
				// data ligação água
				if(contasDadosRelatorio[15] != null){ // 15
					imovelRelatorioHelper.setDataLigacaoAgua((Date) contasDadosRelatorio[15]);
				}
				// código cliente usuario
				if(contasDadosRelatorio[16] != null){ // 16
					imovelRelatorioHelper.setClienteUsuarioId((Integer) contasDadosRelatorio[16]);
				}
				// nome cliente usuario
				if(contasDadosRelatorio[17] != null){ // 17
					imovelRelatorioHelper.setClienteUsuarioNome((String) contasDadosRelatorio[17]);
				}
				// nome cliente resposanvel
				if(contasDadosRelatorio[18] != null){ // 18
					imovelRelatorioHelper.setClienteResponsavelId((Integer) contasDadosRelatorio[18]);
				}
				// nome cliente resposanvel
				if(contasDadosRelatorio[19] != null){ // 19
					imovelRelatorioHelper.setClienteResponsavelNome((String) contasDadosRelatorio[19]);
				}
				// nome logradouro
				if(contasDadosRelatorio[20] != null){ // 20
					imovelRelatorioHelper.setNomeLogradouro(((String) contasDadosRelatorio[20]));
				}
				// tipo logradouro
				if(contasDadosRelatorio[21] != null){ // 21
					imovelRelatorioHelper.setTipoLogradouro((String) contasDadosRelatorio[21]);
				}
				// titulo logradouro
				if(contasDadosRelatorio[22] != null){ // 22
					imovelRelatorioHelper.setTituloLogradouro((String) contasDadosRelatorio[22]);
				}
				// cep
				if(contasDadosRelatorio[23] != null){ // 23
					Cep cepImovel = new Cep();
					cepImovel.setCodigo((Integer) contasDadosRelatorio[23]);
					imovelRelatorioHelper.setCepFormatado(cepImovel.getCepFormatado());
				}
				// endereço referência
				if(contasDadosRelatorio[24] != null){ // 24
					imovelRelatorioHelper.setComplementoImovel((String) contasDadosRelatorio[24]);
				}

				// comlplemente endereco
				if(contasDadosRelatorio[25] != null){ // 25
					imovelRelatorioHelper.setComplementoImovel((String) contasDadosRelatorio[25]);
				}

				// número imóvel
				if(contasDadosRelatorio[26] != null){ // 26
					imovelRelatorioHelper.setNumeroImovel((String) contasDadosRelatorio[26]);
				}
				// nome bairro
				if(contasDadosRelatorio[27] != null){ // 27
					imovelRelatorioHelper.setNomeBairro((String) contasDadosRelatorio[27]);
				}
				// nome município
				if(contasDadosRelatorio[28] != null){ // 28
					imovelRelatorioHelper.setNomeMunicipio((String) contasDadosRelatorio[28]);
				}
				// sigla uf
				if(contasDadosRelatorio[29] != null){ // 29
					imovelRelatorioHelper.setUnidadeFederacao((String) contasDadosRelatorio[29]);
				}
				// indicador imóvel condomínio
				if(contasDadosRelatorio[30] != null){ // 30
					imovelRelatorioHelper.setIndicadorImovelCondominio(((Integer) contasDadosRelatorio[30]).shortValue());
				}

				// número moradores
				if(contasDadosRelatorio[31] != null){ // 31
					imovelRelatorioHelper.setNumeroMoradores(((Integer) contasDadosRelatorio[31]).shortValue());
				}
				// matrícula imóvel condomínio
				if(contasDadosRelatorio[32] != null){ // 32
					imovelRelatorioHelper.setMatriculaImovelCondominio((Integer) contasDadosRelatorio[32]);
				}
				// matrícula imóvel principal
				if(contasDadosRelatorio[33] != null){ // 33
					imovelRelatorioHelper.setMatriculaImovelPrincipal((Integer) contasDadosRelatorio[33]);
				}
				// número pontos utilização
				if(contasDadosRelatorio[34] != null){ // 34
					imovelRelatorioHelper.setNumeroPontosUtilzacao(((Integer) contasDadosRelatorio[34]).shortValue());
				}
				// perfil imóvel
				if(contasDadosRelatorio[35] != null){ // 35
					imovelRelatorioHelper.setPerfilImovel((String) contasDadosRelatorio[35]);
				}
				// área construída maior faixa
				if(contasDadosRelatorio[36] != null){ // 36
					imovelRelatorioHelper.setAreaConstruidaMaiorFaixa((Integer) contasDadosRelatorio[36]);
				}
				// área construída menor faixa
				if(contasDadosRelatorio[37] != null){ // 37
					imovelRelatorioHelper.setAreaConstruidaMenorFaixa((Integer) contasDadosRelatorio[37]);
				}
				// área construída
				if(contasDadosRelatorio[38] != null){ // 38
					imovelRelatorioHelper.setAreaConstruidaImovel((BigDecimal) contasDadosRelatorio[38]);
				}
				// pavimento calçada
				if(contasDadosRelatorio[39] != null){ // 39
					imovelRelatorioHelper.setTipoPavimentoCalcada((String) contasDadosRelatorio[39]);
				}
				// pavimento rua
				if(contasDadosRelatorio[40] != null){ // 40
					imovelRelatorioHelper.setTipoPavimentoRua((String) contasDadosRelatorio[40]);
				}

				// despejo
				if(contasDadosRelatorio[41] != null){ // 41
					imovelRelatorioHelper.setTipoDespejo(((String) contasDadosRelatorio[41]));
				}
				// volume reservatorio superior menor faixa
				if(contasDadosRelatorio[42] != null){ // 42
					imovelRelatorioHelper.setVolumeReservatorioSuperiorMenorFaixa((BigDecimal) contasDadosRelatorio[42]);
				}
				// volume reservatorio superior maior faixa
				if(contasDadosRelatorio[43] != null){ // 43
					imovelRelatorioHelper.setVolumeReservatorioSuperiorMaiorFaixa((BigDecimal) contasDadosRelatorio[43]);
				}
				// volume reservatorio superior
				if(contasDadosRelatorio[44] != null){ // 44
					imovelRelatorioHelper.setVolumeReservatorioSuperior((BigDecimal) contasDadosRelatorio[44]);
				}
				// volume reservatorio inferior menor faixa
				if(contasDadosRelatorio[45] != null){ // 45
					imovelRelatorioHelper.setVolumeReservatorioInferiorMenorFaixa((BigDecimal) contasDadosRelatorio[45]);
				}
				// volume reservatorio inferior maior faixa
				if(contasDadosRelatorio[46] != null){ // 46
					imovelRelatorioHelper.setVolumeReservatorioInferiorMaiorFaixa((BigDecimal) contasDadosRelatorio[46]);
				}
				// volume reservatorio inferior
				if(contasDadosRelatorio[47] != null){ // 47
					imovelRelatorioHelper.setVolumeReservatorioInferior((BigDecimal) contasDadosRelatorio[47]);
				}
				// volume piscina menor faixa
				if(contasDadosRelatorio[48] != null){ // 48
					imovelRelatorioHelper.setVolumePiscinaMenorFaixa((BigDecimal) contasDadosRelatorio[48]);
				}
				// volume piscina maior faixa
				if(contasDadosRelatorio[49] != null){ // 49
					imovelRelatorioHelper.setVolumePiscinaMaiorFaixa((BigDecimal) contasDadosRelatorio[49]);
				}
				// volume piscina
				if(contasDadosRelatorio[50] != null){ // 50
					imovelRelatorioHelper.setVolumePiscina((BigDecimal) contasDadosRelatorio[50]);
				}

				// tipo de poço
				if(contasDadosRelatorio[51] != null){ // 51
					imovelRelatorioHelper.setDescricaoTipoPoco(((String) contasDadosRelatorio[51]));
				}
				// diâmetro da ligação de água
				if(contasDadosRelatorio[52] != null){ // 52
					imovelRelatorioHelper.setDiametroLigacaoAgua((String) contasDadosRelatorio[52]);
				}
				// material da ligação de água
				if(contasDadosRelatorio[53] != null){ // 53
					imovelRelatorioHelper.setMaterialLigacaoAgua((String) contasDadosRelatorio[53]);
				}
				// diâmetro da ligação de esgoto
				if(contasDadosRelatorio[54] != null){ // 54
					imovelRelatorioHelper.setDiametroLigacaoEsgoto((String) contasDadosRelatorio[54]);
				}
				// material da ligação de esgoto
				if(contasDadosRelatorio[55] != null){ // 55
					imovelRelatorioHelper.setMaterialLigacaoEsgoto((String) contasDadosRelatorio[55]);
				}
				// consumo mínimo esgoto
				if(contasDadosRelatorio[56] != null){ // 56
					imovelRelatorioHelper.setConsumoMinimoFixadoLigacaoEsgoto((Integer) contasDadosRelatorio[56]);
				}
				// percentual água coletada
				if(contasDadosRelatorio[57] != null){ // 57
					imovelRelatorioHelper.setPercentualAguaConsumidaColetada((BigDecimal) contasDadosRelatorio[57]);
				}
				// percentual esgoto
				if(contasDadosRelatorio[58] != null){ // 58
					imovelRelatorioHelper.setPercentual((BigDecimal) contasDadosRelatorio[58]);
				}
				// número hidrômetro água
				if(contasDadosRelatorio[59] != null){ // 59
					imovelRelatorioHelper.setNumeroHidrometroAgua((String) contasDadosRelatorio[59]);
				}
				// ano fabricação hidrômetro água
				if(contasDadosRelatorio[60] != null){ // 60
					imovelRelatorioHelper.setAnoFabricaocaHidrometroAgua(((Integer) contasDadosRelatorio[60]).shortValue());
				}
				// capacidade hidrômetro água
				if(contasDadosRelatorio[61] != null){ // 61
					imovelRelatorioHelper.setCapacidadeHidrometroAgua(((String) contasDadosRelatorio[61]));
				}
				// marca hidrômetro água
				if(contasDadosRelatorio[62] != null){ // 62
					imovelRelatorioHelper.setMarcaHidrometroAgua((String) contasDadosRelatorio[62]);
				}
				// diâmetro hidrômetro água
				if(contasDadosRelatorio[63] != null){ // 63
					imovelRelatorioHelper.setDiametroHidrometroAgua((String) contasDadosRelatorio[63]);
				}
				// tipo hidrômetro água
				if(contasDadosRelatorio[64] != null){ // 64
					imovelRelatorioHelper.setTipoHidrometroAgua((String) contasDadosRelatorio[64]);
				}
				// data instalação hidrômetro água
				if(contasDadosRelatorio[65] != null){ // 65
					imovelRelatorioHelper.setDataInstalacaoHidrometroAgua((Date) contasDadosRelatorio[65]);
				}
				// local instalação hidrômetro água
				if(contasDadosRelatorio[66] != null){ // 66
					imovelRelatorioHelper.setLocalInstalacaoHidrometroAgua((String) contasDadosRelatorio[66]);
				}
				// proteção hidrômetro água
				if(contasDadosRelatorio[67] != null){ // 67
					imovelRelatorioHelper.setTipoProtecaoHidrometroAgua((String) contasDadosRelatorio[67]);
				}
				// indicador cavalete hidrômetro água
				if(contasDadosRelatorio[68] != null){ // 68
					imovelRelatorioHelper.setIndicadorExistenciaCavaleteAgua(((Integer) contasDadosRelatorio[68]).shortValue());
				}
				// número hidrômetro poço
				if(contasDadosRelatorio[69] != null){ // 69
					imovelRelatorioHelper.setNumeroHidrometroPoco((String) contasDadosRelatorio[69]);
				}
				// ano fabricação hidrômetro poço
				if(contasDadosRelatorio[70] != null){ // 70
					imovelRelatorioHelper.setAnoFabricacaoHidrometroPoco(((Integer) contasDadosRelatorio[70]).shortValue());
				}
				// capacidade hidrômetro poço
				if(contasDadosRelatorio[71] != null){ // 71
					imovelRelatorioHelper.setCapacidadeHidrometroPoco(((String) contasDadosRelatorio[71]));
				}
				// marca hidrômetro poço
				if(contasDadosRelatorio[72] != null){ // 72
					imovelRelatorioHelper.setMarcaHidrometroPoco((String) contasDadosRelatorio[72]);
				}
				// diâmetro hidrômetro poço
				if(contasDadosRelatorio[73] != null){ // 73
					imovelRelatorioHelper.setDiametroHidrometroPoco((String) contasDadosRelatorio[73]);
				}
				// tipo hidrômetro poço
				if(contasDadosRelatorio[74] != null){ // 74
					imovelRelatorioHelper.setTipoHidrometroPoco((String) contasDadosRelatorio[74]);
				}
				// data instalação hidrômetro poço
				if(contasDadosRelatorio[75] != null){ // 75
					imovelRelatorioHelper.setDataInstalacaoHidrometroPoco((Date) contasDadosRelatorio[75]);
				}
				// local instalação hidrômetro poço
				if(contasDadosRelatorio[76] != null){ // 76
					imovelRelatorioHelper.setLocalInstalacaoHidrometroPoco((String) contasDadosRelatorio[76]);
				}
				// proteção hidrômetro poço
				if(contasDadosRelatorio[77] != null){ // 77
					imovelRelatorioHelper.setTipoProtecaoHidrometroPoco((String) contasDadosRelatorio[77]);
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[78] != null){ // 78
					imovelRelatorioHelper.setIndicadorExistenciaCavaletePoco(((Integer) contasDadosRelatorio[78]).shortValue());
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[79] != null){ // 79
					imovelRelatorioHelper.setConsumoMinimoFixadoAgua((Integer) contasDadosRelatorio[79]);
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[80] != null){ // 80
					imovelRelatorioHelper.setConsumoMinimoFixadoLigacaoEsgoto((Integer) contasDadosRelatorio[80]);
				}
				// indicador Jardim
				if(contasDadosRelatorio[81] != null){ // 81
					imovelRelatorioHelper.setJardim(((Short) contasDadosRelatorio[81]).toString());
				}

				// Rota
				if(contasDadosRelatorio[86] != null){ // 86
					imovelRelatorioHelper.setIdRota((Integer) contasDadosRelatorio[86]);
				}

				// Número Sequencial Rota
				if(contasDadosRelatorio[83] != null){ // 83
					imovelRelatorioHelper.setNumeroSequencialRota((Integer) contasDadosRelatorio[83]);
				}

				// id do Logradouro
				if(contasDadosRelatorio[84] != null){ // 84
					imovelRelatorioHelper.setIdLogradouro("" + (Integer) contasDadosRelatorio[84]);
				}

				// Segmento
				if(contasDadosRelatorio[85] != null){ // 85
					imovelRelatorioHelper.setSegmento((Short) contasDadosRelatorio[85]);
				}

				// consumo Medio
				Integer consumoMedio = getControladorCobranca().pesquisarConsumoMedioConsumoHistoricoImovel(
								Integer.valueOf(imovelRelatorioHelper.getMatriculaImovel()));
				if(consumoMedio != null){
					imovelRelatorioHelper.setConsumoMedioImovel(consumoMedio);
				}

				// Consumo Fixado Poço
				if(contasDadosRelatorio[89] != null){

					imovelRelatorioHelper.setConsumoFixadoPoco(contasDadosRelatorio[89].toString());
				}

				Imovel imovel = new Imovel();

				Localidade localidadeImovel = new Localidade();
				localidadeImovel.setId(imovelRelatorioHelper.getIdLocalidade());
				SetorComercial setorComercialImovel = new SetorComercial();
				setorComercialImovel.setCodigo(imovelRelatorioHelper.getCodigoSetorComercial().intValue());
				Quadra quadraImovel = new Quadra();
				quadraImovel.setNumeroQuadra(imovelRelatorioHelper.getNumeroQuadra().intValue());

				imovel.setLocalidade(localidadeImovel);
				imovel.setSetorComercial(setorComercialImovel);
				imovel.setQuadra(quadraImovel);
				imovel.setLote(Short.valueOf(imovelRelatorioHelper.getNumeroLote()).shortValue());
				imovel.setSubLote(Short.valueOf(imovelRelatorioHelper.getNumeroSubLote()).shortValue());

				Integer idRota = imovelRelatorioHelper.getIdRota();

				if(idRota != null){
					Rota rota = new Rota();
					rota.setId(idRota);

					imovel.setRota(rota);
				}

				imovel.setNumeroSegmento(imovelRelatorioHelper.getSegmento());

				// inscricao formatada do imovel
				imovelRelatorioHelper.setInscricaoImovel(imovel.getInscricaoFormatada());

				// obter endereco
				imovelRelatorioHelper.setEndereco(getControladorEndereco().pesquisarEndereco(
								Integer.valueOf(imovelRelatorioHelper.getMatriculaImovel())));

				Collection colecaoSubcategoria = this.pesquisarSubcategoriasImovelRelatorio(imovelRelatorioHelper.getMatriculaImovel());

				// ------subcategoria
				String[] arraySubcatgegoriasQtdEconomias = new String[colecaoSubcategoria.size()];
				if(!colecaoSubcategoria.isEmpty()){

					Iterator iterator = colecaoSubcategoria.iterator();

					int i = 0;

					while(iterator.hasNext()){

						Object[] arraySubcategoria = (Object[]) iterator.next();

						arraySubcatgegoriasQtdEconomias[i] = arraySubcategoria[0].toString() + "/" + arraySubcategoria[1].toString();

						i++;

					}
					imovelRelatorioHelper.setSubcategoriaQtdEconomia(arraySubcatgegoriasQtdEconomias);
				}

				// [UC0307 Obter Indicador de Existência de Hidrômetro no
				// Imóvel]
				imovelRelatorioHelper.setIndicadorExistenciaHidrometro(obterIndicadorExistenciaHidrometroImovel(imovelRelatorioHelper
								.getMatriculaImovel()));

				// [UC123 Obter Descrições da Categoria do Imóvel]
				imovelRelatorioHelper.setDescricaoAbreviadaCategoria(obterDescricoesCategoriaImovel(
								new Imovel(imovelRelatorioHelper.getMatriculaImovel())).getDescricaoAbreviada());

				colecaoGerarRelatorioImovelOutrosCriterios.add(imovelRelatorioHelper);
			}
		}

		return colecaoGerarRelatorioImovelOutrosCriterios;
	}

	/**
	 * [UC0164] Filtrar Imoveis por Outros Criterios
	 * Filtra para saber a quantidade de imoveis antes de executar o filtro
	 * 
	 * @author Rafael Santos
	 * @date 01/08/2006
	 */
	public Integer obterQuantidadadeRelacaoImoveisDebitos(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, Integer relatorio,
					String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal) throws ControladorException{

		Integer quantidade = null;

		try{
			// remove primeiro as linhas do critério cobrança
			quantidade = repositorioImovel.obterQuantidadadeRelacaoImoveisDebitos(idImovelCondominio, idImovelPrincipal,
							idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,
							consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial,
							intervaloValorPercentualEsgotoFinal, intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal,
							intervaloMediaMinimaHidrometroInicial, intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo,
							idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade,
							areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional,
							idLocalidadeInicial, idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal,
							loteOrigem, loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria,
							idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
							idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
							idAreaConstruidaFaixa, idUnidadeNegocio, relatorio, cdRotaInicial, cdRotaFinal, sequencialRotaInicial,
							sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal,
							consumoFixadoEsgotoPocoInicial, consumoFixadoEsgotoPocoFinal);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return quantidade;
	}

	/**
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição do imóvel para exibição.
	 * Aqui é montada a inscrição formatada e com os pontos, caso o parametro "inscricaoFormatada"
	 * seja true. Caso seja false, a inscrição nao virá formatada.
	 */
	public String pesquisarInscricaoImovel(Integer idImovel, boolean inscricaoFormatada) throws ControladorException{

		String inscricao = null;
		Imovel imovel = new Imovel();
		Localidade localidade = new Localidade();
		SetorComercial setorComercial = new SetorComercial();
		Quadra quadra = new Quadra();
		try{
			Object[] objetoImovel = this.repositorioImovel.pesquisarInscricaoImovel(idImovel);
			if(objetoImovel != null){
				// parte da montagem da inscrição
				// primeiro o id da localidade
				localidade.setId(((Integer) objetoImovel[0]));
				imovel.setLocalidade(localidade);
				// codigo do setor comercial
				setorComercial.setCodigo(((Integer) objetoImovel[1]).intValue());
				imovel.setSetorComercial(setorComercial);
				// número da quadra
				quadra.setNumeroQuadra(((Integer) objetoImovel[2]));
				imovel.setQuadra(quadra);
				// lote
				imovel.setLote(((Short) objetoImovel[3]));
				// sublote
				imovel.setSubLote(((Short) objetoImovel[4]));

				// rota
				if(objetoImovel[5] != null){
					Integer idRota = (Integer) objetoImovel[5];

					Rota rota = new Rota();
					rota.setId(idRota);

					imovel.setRota(rota);
				}

				// segmento
				imovel.setNumeroSegmento(((Short) objetoImovel[6]));

				if(inscricaoFormatada){
					inscricao = imovel.getInscricaoFormatada();
				}else{
					inscricao = imovel.getInscricaoNaoFormatada();
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return inscricao;
	}

	/**
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição do imóvel para exibição.
	 * aki é montada a inscrição
	 */
	public Object[] pesquisarLocalidadeSetorImovel(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarLocalidadeSetorImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Gerar Relatório de Dados das Economias do Imóvel
	 * 
	 * @author Rafael Corrêa
	 * @date 01/08/2006
	 * @param idImovelCondominio
	 * @param idImovelPrincipal
	 * @param idNomeConta
	 * @param idSituacaoLigacaoAgua
	 * @param consumoMinimoInicialAgua
	 * @param consumoMinimoFinalAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param consumoMinimoInicialEsgoto
	 * @param consumoMinimoFinalEsgoto
	 * @param intervaloValorPercentualEsgotoInicial
	 * @param intervaloValorPercentualEsgotoFinal
	 * @param intervaloMediaMinimaImovelInicial
	 * @param intervaloMediaMinimaImovelFinal
	 * @param intervaloMediaMinimaHidrometroInicial
	 * @param intervaloMediaMinimaHidrometroFinal
	 * @param idImovelPerfil
	 * @param idPocoTipo
	 * @param idFaturamentoSituacaoTipo
	 * @param idCobrancaSituacaoTipo
	 * @param idSituacaoEspecialCobranca
	 * @param idEloAnormalidade
	 * @param areaConstruidaInicial
	 * @param areaConstruidaFinal
	 * @param idCadastroOcorrencia
	 * @param idConsumoTarifa
	 * @param idGerenciaRegional
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorComercialInicial
	 * @param setorComercialFinal
	 * @param quadraInicial
	 * @param quadraFinal
	 * @param loteOrigem
	 * @param loteDestno
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param municipio
	 * @param idTipoMedicao
	 * @param indicadorMedicao
	 * @param idSubCategoria
	 * @param idCategoria
	 * @param quantidadeEconomiasInicial
	 * @param quantidadeEconomiasFinal
	 * @param diaVencimento
	 * @param idCliente
	 * @param idClienteTipo
	 * @param idClienteRelacaoTipo
	 * @param numeroPontosInicial
	 * @param numeroPontosFinal
	 * @param numeroMoradoresInicial
	 * @param numeroMoradoresFinal
	 * @param idAreaConstruidaFaixa
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarRelatorioDadosEconomiaImovel(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String segmentoInicial,
					String segmentoFinal, String rotaInicial, String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String subloteInicial, String subloteFinal, String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal)
					throws ControladorException{

		Collection colecaoImoveis = null;

		if(quantidadeEconomiasInicial != null && !quantidadeEconomiasInicial.equals("")){
			if(Integer.valueOf(quantidadeEconomiasInicial).intValue() < 2){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atenca.quantidade_inicial_economias.menor");
			}
		}

		try{
			// remove primeiro as linhas do critério cobrança
			colecaoImoveis = repositorioImovel.gerarRelatorioDadosEconomiasImovelOutrosCriterios(idImovelCondominio, idImovelPrincipal,
							idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,
							consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial,
							intervaloValorPercentualEsgotoFinal,

							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal,

							idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,
							idEloAnormalidade, areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,
							idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal, setorComercialInicial, setorComercialFinal,
							quadraInicial, quadraFinal, loteOrigem, loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao,
							indicadorMedicao, idSubCategoria, idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal,
							diaVencimento, idCliente, idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal,
							numeroMoradoresInicial, numeroMoradoresFinal, idAreaConstruidaFaixa, idUnidadeNegocio, segmentoInicial,
							segmentoFinal, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal, subloteInicial,
							subloteFinal, consumoFixadoEsgotoPocoInicial, consumoFixadoEsgotoPocoFinal);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// colecao com os dados para o relatorio
		Collection colecaoGerarRelatorioDadosEconomiasImovel = null;

		if(colecaoImoveis == null || colecaoImoveis.isEmpty()){
			throw new ControladorException("atencao.filtro_consumo_tarifa_sem_records");
		}

		if(colecaoImoveis != null && !colecaoImoveis.isEmpty()){

			Iterator iteratorColecaoImoveis = colecaoImoveis.iterator();
			colecaoGerarRelatorioDadosEconomiasImovel = new ArrayList();

			ImovelRelatorioHelper imovelRelatorioHelper = null;

			while(iteratorColecaoImoveis.hasNext()){

				Object[] imovelDadosRelatorio = (Object[]) iteratorColecaoImoveis.next();

				imovelRelatorioHelper = new ImovelRelatorioHelper();

				// id
				if(imovelDadosRelatorio[0] != null){ // 0
					imovelRelatorioHelper.setMatriculaImovel(((Integer) imovelDadosRelatorio[0]));
				}

				// id gerencia regional
				if(imovelDadosRelatorio[1] != null){ // 1
					imovelRelatorioHelper.setIdGerenciaRegional(((Integer) imovelDadosRelatorio[1]));
				}
				// descricao abreviada da gerencia regional
				if(imovelDadosRelatorio[2] != null){ // 2
					imovelRelatorioHelper.setDescricaoGerenciaRegional(((String) imovelDadosRelatorio[2]));
				}
				// id localidade
				if(imovelDadosRelatorio[3] != null){ // 3
					imovelRelatorioHelper.setIdLocalidade(((Integer) imovelDadosRelatorio[3]));
				}
				// descricao localidade
				if(imovelDadosRelatorio[4] != null){ // 4
					imovelRelatorioHelper.setDescricaoLocalidade(((String) imovelDadosRelatorio[4]));
				}
				// codigo setor comercial
				if(imovelDadosRelatorio[5] != null){ // 5
					imovelRelatorioHelper.setCodigoSetorComercial(((Integer) imovelDadosRelatorio[5]));
				}
				// descricao setor comercial
				if(imovelDadosRelatorio[6] != null){ // 6
					imovelRelatorioHelper.setDescricaoSetorComercial(((String) imovelDadosRelatorio[6]));
				}

				// endereço
				String endereco = getControladorEndereco().pesquisarEndereco(imovelRelatorioHelper.getMatriculaImovel());
				imovelRelatorioHelper.setEndereco(endereco);

				String inscricaoFormatada = this.pesquisarInscricaoImovel(imovelRelatorioHelper.getMatriculaImovel(), true);
				imovelRelatorioHelper.setInscricaoImovel(inscricaoFormatada);

				Collection colecaoSubcategorias = null;
				try{
					// coleção com os dados da sub categoria
					colecaoSubcategorias = repositorioImovel.gerarRelatorioDadosEconomiasImovelSubcategoria(imovelRelatorioHelper
									.getMatriculaImovel().toString());
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				if(colecaoSubcategorias != null && !colecaoSubcategorias.isEmpty()){

					Iterator colecaoSubcategoriasIterator = colecaoSubcategorias.iterator();

					Collection colecaoSubcategoriasRelatorioHelper = new ArrayList();

					ImovelSubcategoriaHelper imovelSubcategoriaHelper = null;

					while(colecaoSubcategoriasIterator.hasNext()){
						Object[] subcategoriasDadosRelatorio = (Object[]) colecaoSubcategoriasIterator.next();

						imovelSubcategoriaHelper = new ImovelSubcategoriaHelper();

						// descrição
						if(subcategoriasDadosRelatorio[0] != null){
							imovelSubcategoriaHelper.setSubcategoria((String) subcategoriasDadosRelatorio[0]);
						}

						// categoria
						if(subcategoriasDadosRelatorio[1] != null){
							imovelSubcategoriaHelper.setCategoria((String) subcategoriasDadosRelatorio[1]);
						}

						// quantidade de economias
						if(subcategoriasDadosRelatorio[2] != null){
							imovelSubcategoriaHelper.setQuantidadeEconomias((Short) subcategoriasDadosRelatorio[2]);
						}

						String idSubcategoria = null;
						// is Sub CAtegoria
						if(subcategoriasDadosRelatorio[3] != null){
							idSubcategoria = ((Integer) subcategoriasDadosRelatorio[3]).toString();
						}

						Collection colecaoEconomias = null;
						try{
							// coleção com os dados da sub categoria
							colecaoEconomias = repositorioImovel.gerarRelatorioDadosEconomiasImovelEconomia(imovelRelatorioHelper
											.getMatriculaImovel().toString(), idSubcategoria);
						}catch(ErroRepositorioException e){
							sessionContext.setRollbackOnly();
							throw new ControladorException("erro.sistema", e);
						}

						if(colecaoEconomias != null && !colecaoEconomias.isEmpty()){

							Iterator colecaoEconomiasIterator = colecaoEconomias.iterator();

							ImovelEconomiaHelper imovelEconomiaHelper = null;

							Collection colecaoEconomiasRelatorioHelper = new ArrayList();

							while(colecaoEconomiasIterator.hasNext()){
								Object[] economiasDadosRelatorio = (Object[]) colecaoEconomiasIterator.next();

								imovelEconomiaHelper = new ImovelEconomiaHelper();

								// complemento endereço
								if(economiasDadosRelatorio[0] != null){
									imovelEconomiaHelper.setComplementoEndereco((String) economiasDadosRelatorio[0]);
								}
								// número de pontos de utilização
								if(economiasDadosRelatorio[1] != null){
									imovelEconomiaHelper.setNumeroPontosUtilizacao((Short) economiasDadosRelatorio[1]);
								}
								// número de moradores
								if(economiasDadosRelatorio[2] != null){
									imovelEconomiaHelper.setNumeroMoradores((Short) economiasDadosRelatorio[2]);
								}
								// número do iptu
								if(economiasDadosRelatorio[3] != null){
									imovelEconomiaHelper.setNumeroIptu((BigDecimal) economiasDadosRelatorio[3]);
								}
								// número contrato celpe
								if(economiasDadosRelatorio[4] != null){
									imovelEconomiaHelper.setNumeroContratoCelpe((Long) economiasDadosRelatorio[4]);
								}
								// área construída
								if(economiasDadosRelatorio[5] != null){
									imovelEconomiaHelper.setAreaConstruidaImovelEconomia((BigDecimal) economiasDadosRelatorio[5]);
								}

								// id Imovel Economia
								String idImovelEconomia = null;
								if(economiasDadosRelatorio[6] != null){
									idImovelEconomia = ((Integer) economiasDadosRelatorio[6]).toString();
								}

								Collection colecaoClientesImoveis = null;
								try{
									// coleção com os dados da sub categoria
									colecaoClientesImoveis = repositorioImovel
													.gerarRelatorioDadosEconomiasImovelClienteEconomia(idImovelEconomia);
								}catch(ErroRepositorioException e){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema", e);
								}

								if(colecaoClientesImoveis != null && !colecaoClientesImoveis.isEmpty()){

									Iterator colecaoClientesImoveisIterator = colecaoClientesImoveis.iterator();

									Collection colecaoClientesImoveisRelatorioHelper = new ArrayList();

									ClienteImovelEconomiaHelper clienteImovelEconomiaHelper = null;

									while(colecaoClientesImoveisIterator.hasNext()){
										Object[] clienteImovelDadosRelatorio = (Object[]) colecaoClientesImoveisIterator.next();

										clienteImovelEconomiaHelper = new ClienteImovelEconomiaHelper();

										// nome cliente
										if(clienteImovelDadosRelatorio[0] != null){
											clienteImovelEconomiaHelper.setClienteNome((String) clienteImovelDadosRelatorio[0]);
										}
										// tipo da relação
										if(clienteImovelDadosRelatorio[1] != null){
											clienteImovelEconomiaHelper.setRelacaoTipo((String) clienteImovelDadosRelatorio[1]);
										}
										// Cria um cliente para em seguida setar
										// o cpf e cnpj e utilizar o método de
										// formatação desses campos existentes
										// na classe cliente
										Cliente cliente = new Cliente();
										// cpf
										if(clienteImovelDadosRelatorio[2] != null){
											cliente.setCpf((String) clienteImovelDadosRelatorio[2]);
											clienteImovelEconomiaHelper.setCpf(cliente.getCpfFormatado());
										}
										// cnpj
										if(clienteImovelDadosRelatorio[3] != null){
											cliente.setCnpj((String) clienteImovelDadosRelatorio[3]);
											clienteImovelEconomiaHelper.setCnpj(cliente.getCnpjFormatado());
										}
										// data início relação
										if(clienteImovelDadosRelatorio[4] != null){
											clienteImovelEconomiaHelper.setRelacaoDataInicio((Date) clienteImovelDadosRelatorio[4]);
										}
										// data fim relação
										if(clienteImovelDadosRelatorio[5] != null){
											clienteImovelEconomiaHelper.setRelacaoDataFim((Date) clienteImovelDadosRelatorio[5]);
										}
										// motivo fim relação
										if(clienteImovelDadosRelatorio[6] != null){
											clienteImovelEconomiaHelper.setMotivoFimRelacao((String) clienteImovelDadosRelatorio[6]);
										}

										colecaoClientesImoveisRelatorioHelper.add(clienteImovelEconomiaHelper);
									}

									imovelEconomiaHelper.setClienteImovelEconomiaHelper(colecaoClientesImoveisRelatorioHelper);
								}

								colecaoEconomiasRelatorioHelper.add(imovelEconomiaHelper);
							}

							imovelSubcategoriaHelper.setColecaoImovelEconomia(colecaoEconomiasRelatorioHelper);

						}

						colecaoSubcategoriasRelatorioHelper.add(imovelSubcategoriaHelper);

					}

					imovelRelatorioHelper.setSubcategorias(colecaoSubcategoriasRelatorioHelper);

				}

				colecaoGerarRelatorioDadosEconomiasImovel.add(imovelRelatorioHelper);
			}
		}
		return colecaoGerarRelatorioDadosEconomiasImovel;
	}

	/**
	 * Esse método é usado para fzazer uma pesquisa na tabela imóvel e confirmar
	 * se o id passado é de um imóvel excluído(idExclusao)
	 * Flávio Cordeiro
	 * 
	 * @throws ErroRepositorioException
	 */

	public Boolean confirmarImovelExcluido(Integer idImovel){

		boolean retorno = false;

		try{
			retorno = repositorioImovel.confirmarImovelExcluido(idImovel);
		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * Permite pesquisar entidades beneficentes [UC0389] Inserir Autorização
	 * para Doação Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param idEntidadeBeneficente
	 *            -
	 *            Código da entidade beneficente
	 * @return Collection<EntidadeBeneficente> - Coleção de entidades
	 *         beneficentes
	 * @throws ControladorException
	 */
	public Collection<EntidadeBeneficente> pesquisarEntidadeBeneficente(Integer idEntidadeBeneficente) throws ControladorException{

		/** * Declara variáveis locais ** */
		Collection<EntidadeBeneficente> colecaoEntidadeBeneficente = null;
		FiltroEntidadeBeneficente filtroEntidadeBeneficente = null;

		filtroEntidadeBeneficente = new FiltroEntidadeBeneficente();
		filtroEntidadeBeneficente.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficente.CLIENTE);

		/** * Avalia se o parãmetro idEntidadeBeneficente veio preenchido ** */
		if((idEntidadeBeneficente != null) && (idEntidadeBeneficente.intValue() != 0)){
			filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID, idEntidadeBeneficente));
		}
		try{
			/** * executa a pesquisa de entidade beneficente ** */
			colecaoEntidadeBeneficente = repositorioImovel.pesquisarEntidadeBeneficente(filtroEntidadeBeneficente);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return colecaoEntidadeBeneficente;
	}

	/**
	 * Permite pesquisar imóveis doação [UC0389] Inserir Autorização para Doação
	 * Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param idImovelDoacao
	 *            -
	 *            Código do imóvel doação
	 * @return Collection<ImovelDoacao> - Coleção de imóveis doação
	 * @throws ControladorException
	 */
	public Collection<ImovelDoacao> pesquisarImovelDoacao(FiltroImovelDoacao filtroImovelDoacao) throws ControladorException{

		/** * Declara as variáveis locais ** */
		Collection<ImovelDoacao> colecaoImovelDoacao = null;
		try{
			/** * Executa a pesquisa de imovel doacao ** */
			colecaoImovelDoacao = repositorioImovel.pesquisarImovelDoacao(filtroImovelDoacao);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return colecaoImovelDoacao;
	}

	/**
	 * Permite verificar se existe um determinado imóvel doação [UC0389] Inserir
	 * Autorização para Doação Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param idImovel
	 *            -
	 *            Código do imóvel
	 * @param idEntidadeBeneficenteContrato
	 *            -
	 *            Código do Contrato da Entidade Beneficente
	 * @return ImovelDoacao - Retorna um imóvel doação caso a combinação de
	 *         imóvel e entidade beneficente exista.
	 * @throws ControladorException
	 */
	public ImovelDoacao verificarExistenciaImovelDoacao(Integer idImovel, Integer idEntidadeBeneficenteContrato)
					throws ControladorException{

		/** * Declara as variáveis locais ** */
		ImovelDoacao retorno = null;
		FiltroImovelDoacao filtroImovelDoacao = null;
		Collection<ImovelDoacao> colecaoImovelDoacao = null;

		/** * Prepara o filtro com os parâmetros passados ** */
		filtroImovelDoacao = new FiltroImovelDoacao();
		filtroImovelDoacao.adicionarParametro(new ParametroSimples(FiltroImovelDoacao.ID_IMOVEL, idImovel));
		filtroImovelDoacao.adicionarParametro(new ParametroSimples(FiltroImovelDoacao.ID_ENTIDADE_BENEFICENTE_CONTRATO,
						idEntidadeBeneficenteContrato));
		filtroImovelDoacao.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelDoacao.ENTIDADE_BENEFICENTE_CONTRATO);
		filtroImovelDoacao.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelDoacao.ENTIDADE_BENEFICENTE);
		
		try{
			/** * Executa a pesquisa de imóvel doação ** */
			colecaoImovelDoacao = repositorioImovel.pesquisarImovelDoacao(filtroImovelDoacao);

			/** * Avalia se existe algum imóvel doação ** */
			if(colecaoImovelDoacao != null && colecaoImovelDoacao.size() > 0){
				/** * Caso exista imóvel doação, retorna-o ** */
				retorno = colecaoImovelDoacao.iterator().next();
			}
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * Permite atualizar as informações do imóvel doação [UC0390] Manter
	 * Autorização para Doação Mensal
	 * 
	 * @author César Araújo, Pedro Alexandre
	 * @date 30/08/2006, 17/11/2006
	 * @param imovelDoacao
	 *            -
	 *            Código do imóveo doação
	 * @throws ControladorException
	 */
	public void atualizarImovelDoacao(ImovelDoacao imovelDoacao, Usuario usuarioLogado) throws ControladorException{

		/** * Declara as variáveis locais ** */
		FiltroImovelDoacao filtroImovelDoacao = null;
		ImovelDoacao imovelDoacaoNaBase = null;

		try{
			/** * Instancia e define o filtro para localização do imóvel doação ** */
			filtroImovelDoacao = new FiltroImovelDoacao();
			filtroImovelDoacao.limparListaParametros();
			filtroImovelDoacao.adicionarParametro(new ParametroSimples(FiltroImovelDoacao.ID, imovelDoacao.getId()));
			imovelDoacaoNaBase = this.pesquisarImovelDoacao(filtroImovelDoacao).iterator().next();

			/*******************************************************************
			 * * Avalia se a data de alteração do imóvel doação na base de dados
			 * é mais recente do que o registro em ediçao
			 ******************************************************************/
			if(imovelDoacaoNaBase.getUltimaAlteracao().after(imovelDoacao.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// ------------ CONTROLE DE ABRANGENCIA ----------------
			Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovelDoacao.getImovel());
			// ------------ CONTROLE DE ABRANGENCIA ----------------

			if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.acesso.negado.abrangencia");
			}else{
				/**
				 * * Seta a nova data para o registro e chama o método para
				 * atualizar os dados **
				 */
				imovelDoacao.setUltimaAlteracao(new Date());
			}
			repositorioImovel.atualizarImovelDoacao(imovelDoacao);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisa um imóvel a partir do seu id.Retorna os dados que compõem a
	 * inscrição e o endereço do mesmo
	 * 
	 * @author Raphael Rossiter
	 * @date 01/08/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelRegistroAtendimento(Integer idImovel) throws ControladorException{

		Imovel imovel = null;
		Collection colecaoImovel = null;

		try{

			colecaoImovel = this.repositorioImovel.pesquisarImovelRegistroAtendimento(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			imovel = new Imovel();

			imovel.setId(idImovel);

			Iterator imovelIterator = colecaoImovel.iterator();

			Object[] arrayImovel = (Object[]) imovelIterator.next();

				LogradouroCep logradouroCep = null;
				if(arrayImovel[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayImovel[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayImovel[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayImovel[19]);
						logradouro.setNome("" + arrayImovel[0]);
					}
					LogradouroTipo logradouroTipo = null;
				// Descrição logradouro tipo
					if(arrayImovel[22] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricao("" + arrayImovel[22]);
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
				// Descrição logradouro titulo
					if(arrayImovel[23] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricao("" + arrayImovel[23]);
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep cep = null;
					if(arrayImovel[10] != null){
						cep = new Cep();
						cep.setCepId((Integer) arrayImovel[10]);
						cep.setCodigo((Integer) arrayImovel[16]);
					}

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(cep);
				}

				imovel.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayImovel[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayImovel[21]);

					Bairro bairro = null;
					// nome bairro
					if(arrayImovel[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) arrayImovel[3]);
						bairro.setCodigo((Integer) arrayImovel[37]);
						bairro.setNome("" + arrayImovel[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(arrayImovel[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayImovel[5]);
						municipio.setNome("" + arrayImovel[6]);

					// id da unidade federação
						if(arrayImovel[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayImovel[7]);
							unidadeFederacao.setSigla("" + arrayImovel[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);
				}

				imovel.setLogradouroBairro(logradouroBairro);

			// descricao de endereço referência
				if(arrayImovel[24] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setId((Integer) arrayImovel[42]);
					enderecoReferencia.setDescricao("" + arrayImovel[24]);
					imovel.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(arrayImovel[17] != null){
					imovel.setNumeroImovel("" + arrayImovel[17]);
				}

			// complemento endereço
				if(arrayImovel[18] != null){
					imovel.setComplementoEndereco("" + arrayImovel[18]);
				}


			Localidade localidade = null;
			// id localidade
			if(arrayImovel[25] != null){
				localidade = new Localidade();
				localidade.setId((Integer) arrayImovel[25]);
				localidade.setDescricao((String) arrayImovel[33]);
				imovel.setLocalidade(localidade);
			}

			SetorComercial setorComercial = null;
			// codigo setorComercial
			if(arrayImovel[26] != null){
				setorComercial = new SetorComercial();
				setorComercial.setCodigo((Integer) arrayImovel[26]);
				setorComercial.setDescricao((String) arrayImovel[34]);
				setorComercial.setId((Integer) arrayImovel[35]);
				imovel.setSetorComercial(setorComercial);
			}

			Quadra quadra = null;
			// número quadra
			if(arrayImovel[27] != null){
				quadra = new Quadra();
				quadra.setNumeroQuadra((Integer) arrayImovel[27]);
				quadra.setId((Integer) arrayImovel[36]);
				imovel.setQuadra(quadra);
			}

			// lote
			if(arrayImovel[28] != null){
				imovel.setLote((Short) arrayImovel[28]);
			}

			// sublote
			if(arrayImovel[29] != null){
				imovel.setSubLote((Short) arrayImovel[29]);
			}

			Bacia bacia = null;
			SistemaEsgoto sistemaEsgoto = null;
			DivisaoEsgoto divisaoEsgoto = null;
			// id divisaoEsgoto
			if(arrayImovel[30] != null){
				divisaoEsgoto = new DivisaoEsgoto();
				divisaoEsgoto.setId((Integer) arrayImovel[30]);
				sistemaEsgoto = new SistemaEsgoto();
				sistemaEsgoto.setDivisaoEsgoto(divisaoEsgoto);
				bacia = new Bacia();
				bacia.setSistemaEsgoto(sistemaEsgoto);

				if(imovel.getQuadra() != null){
					imovel.getQuadra().setBacia(bacia);
				}
			}

			PavimentoRua pavimentoRua = null;
			// id pavimentoRua
			if(arrayImovel[31] != null){
				pavimentoRua = new PavimentoRua();
				pavimentoRua.setId((Integer) arrayImovel[31]);
				imovel.setPavimentoRua(pavimentoRua);
			}

			PavimentoCalcada pavimentoCalcada = null;
			// id pavimentoCalcada
			if(arrayImovel[32] != null){
				pavimentoCalcada = new PavimentoCalcada();
				pavimentoCalcada.setId((Integer) arrayImovel[32]);
				imovel.setPavimentoCalcada(pavimentoCalcada);
			}

			LigacaoAguaSituacao ligacaoAguaSituacao = null;
			// id ligacaoAguaSituacao
			if(arrayImovel[38] != null){
				ligacaoAguaSituacao = new LigacaoAguaSituacao();
				ligacaoAguaSituacao.setId((Integer) arrayImovel[38]);
				ligacaoAguaSituacao.setDescricao((String) arrayImovel[39]);
				imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
			}

			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			// id ligacaoEsgotoSituacao
			if(arrayImovel[40] != null){
				ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
				ligacaoEsgotoSituacao.setId((Integer) arrayImovel[40]);
				ligacaoEsgotoSituacao.setDescricao((String) arrayImovel[41]);
				imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
			}

			ImovelPerfil imovelPerfil = null;
			// id imovelPerfil
			if(arrayImovel[43] != null){
				imovelPerfil = new ImovelPerfil();
				imovelPerfil.setId((Integer) arrayImovel[43]);
				imovel.setImovelPerfil(imovelPerfil);
			}

			if(arrayImovel[44] != null){
				Integer idRota = (Integer) arrayImovel[44];

				Rota rota = new Rota();
				rota.setId(idRota);

				imovel.setRota(rota);
			}

			imovel.setNumeroSegmento((Short) arrayImovel[45]);

			if(arrayImovel[46] != null){
				imovel.setCoordenadaX((BigDecimal) arrayImovel[46]);
			}

			if(arrayImovel[47] != null){
				imovel.setCoordenadaY((BigDecimal) arrayImovel[47]);
			}
		}


		return imovel;
	}

	/**
	 * Verifica a existência do hidômetro de acordo com tipo de medição
	 * informado (MedicaoTipo.LIGACAO_AGUA ou MedicaoTipo.POCO).
	 * [UC0368] Atualizar Instalação do Hidrômetro
	 * [FS0003] - Validar existência do hidrômetro
	 * 
	 * @author lms
	 * @created 24/07/2006
	 * @throws ControladorException
	 */
	public void validarExistenciaHidrometro(Imovel imovel, Integer medicaoTipo) throws ControladorException{

		// [FS0003] - Validar existência do hidrômetro

		// Caso o tipo da medição informada seja Ligação de Água
		if(MedicaoTipo.LIGACAO_AGUA.equals(medicaoTipo)){
			/*
			 * Verificar se a situação da ligação de água do imóvel está
			 * diferente de POTENCIAL e FACTÌVEL. Caso contrário, exibir a
			 * mensagem: "Ligação de Água do imóvel <<imov_id>> está <<last_dsligacaoaguasituacao>>"
			 * e retornar para o passo correspondente do fluxo principal
			 */
			if(!LigacaoAguaSituacao.POTENCIAL.equals(imovel.getLigacaoAguaSituacao().getId())
							&& !LigacaoAguaSituacao.FACTIVEL.equals(imovel.getLigacaoAguaSituacao().getId())){
			}else{
				throw new ControladorException("atencao.imovel.ligacao_agua_situacao.incompativel", null,
								new String[] {Integer.toString(imovel.getId()), imovel.getLigacaoAguaSituacao().getDescricao()});
			}

			/*
			 * Caso o tipo de medição seja igual à "Ligação de Água" e não
			 * exista hidrômetro instalado na ligação de água, exibir a
			 * mensagem: "Não existe hidrômetro instalado na ligação de água
			 * deste imóvel"
			 */
			if(imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel.ligacao_agua.hidrometro.inexistente");
			}

		}else if(MedicaoTipo.POCO.equals(medicaoTipo)){
			// Caso o tipo da medição informada seja Poço

			/*
			 * Caso não exista hidrômetro instalado no poço, exibe a mensagem:
			 * "Não existe hidrômetro instalado na saída deste imóvel"
			 */
			if(imovel.getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel.poco.hidrometro.inexistente");
			}

		}

	}

	/**
	 * Verifica a existência da matrícula do imóvel. Caso exista, verifica se o
	 * imóvel está ativo.
	 * [UC0368] Atualizar Instalação do Hidrômetro
	 * [FS0001] - Verificar a existência da matrícula do imóvel [FS0002] -
	 * Verificar a situação do imóvel
	 * 
	 * @author lms
	 * @created 19/07/2006
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelSituacaoAtiva(FiltroImovel filtroImovel) throws ControladorException{

		Imovel imovel = null;
		Collection imoveis = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
		// [FS0001] - Verificar existência da matrícula do imóvel
		if(imoveis == null || imoveis.isEmpty()){
			throw new ControladorException("atencao.matricula.imovel.inexistente");
		}
		imovel = (Imovel) imoveis.iterator().next();
		// [FS0002] - Verificar situação do imóvel
		if(imovel.getIndicadorExclusao() == ConstantesSistema.INDICADOR_IMOVEL_EXCLUIDO){
			throw new ControladorException("atencao.imovel.inativo", null, imovel.getId().toString());
		}
		return imovel;
	}

	/**
	 * Faz o controle de concorrencia do imovel
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	public void verificarImovelControleConcorrencia(Imovel imovel) throws ControladorException{

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));

		Collection colecaoImovel = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		if(colecaoImovel == null || colecaoImovel.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		Imovel imovelAtualizado = (Imovel) colecaoImovel.iterator().next();

		if(imovelAtualizado.getUltimaAlteracao().after(imovel.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

	}

	/**
	 * @author eduardo henrique
	 * @date 17/08/2009
	 *       Retorna uma instância de Imovel, com todos os atributos preenchidos
	 * @param idImovel
	 * @return Imovel
	 * @throws ControladorException
	 */
	public Imovel consultarImovel(Integer idImovel) throws ControladorException{

		Imovel imovel = null;

		if(idImovel != null){
			FiltroImovel filtroImovel = new FiltroImovel();

			// Objetos que serão retornados pelo Hibernate
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("esgotamento");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("padraoConstrucao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("funcionario");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rendaFamiliarFaixa");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgoto");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelCondominio");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("eloAnormalidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("areaConstruidaFaixa");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("pavimentoCalcada");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("reservatorioVolumeFaixaSuperior");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("reservatorioVolumeFaixaInferior");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacaoTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("pavimentoRua");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("cadastroOcorrencia");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("pocoTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("despejo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("faturamentoSituacaoTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("fonteAbastecimento");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("piscinaVolumeFaixa");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("consumoTarifa");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("faturamentoSituacaoMotivo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("faturamentoTipo");

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.localidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.logradouroCep.cep");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.logradouroCep.logradouro.logradouroTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.logradouroCep.logradouro.logradouroTitulo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.enderecoReferencia");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.ligacaoAguaSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.esgotamento");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.padraoConstrucao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.ligacaoEsgotoSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.imovelPrincipal");

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Integer.valueOf(idImovel)));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CLIENTES_IMOVEIS);

			Collection<Imovel> imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
			if(imoveis != null && !imoveis.isEmpty()){
				for(Imovel imovelColecao : imoveis){
					imovel = imovelColecao;
				}
			}
		}

		return imovel;
	}

	/**
	 * Consulta os Dados Cadastrais do Imovel [UC0472] Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 07/09/2006
	 * @param idImovel
	 * @return Imovel
	 * @throws ControladorException
	 */
	public Imovel consultarImovelDadosCadastrais(Integer idImovel) throws ControladorException{

		Imovel imovel = null;
		Collection colecaoImovel = null;

		try{

			colecaoImovel = this.repositorioImovel.consultarImovelDadosCadastrais(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			imovel = new Imovel();
			imovel.setId(idImovel);
			Quadra quadra = new Quadra();
			Iterator imovelIterator = colecaoImovel.iterator();
			Object[] arrayImovel = (Object[]) imovelIterator.next();
			LigacaoAguaSituacao ligacaoAguaSituacao = null;

			// id ligacaoAguaSituacao - 0
			if(arrayImovel[0] != null){
				ligacaoAguaSituacao = new LigacaoAguaSituacao();

				ligacaoAguaSituacao.setDescricao((String) arrayImovel[0]);
				imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
			}

			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			// id ligacaoEsgotoSituacao - 1
			if(arrayImovel[1] != null){
				ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();

				ligacaoEsgotoSituacao.setDescricao((String) arrayImovel[1]);
				imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
			}

			ImovelPerfil imovelPerfil = null;
			// descricao imovelPerfil - 2
			if(arrayImovel[2] != null){
				imovelPerfil = new ImovelPerfil();

				imovelPerfil.setDescricao((String) arrayImovel[2]);
				imovel.setImovelPerfil(imovelPerfil);
			}

			Despejo despejo = null;
			// descricao despejo - 3
			if(arrayImovel[3] != null){
				despejo = new Despejo();

				despejo.setDescricao((String) arrayImovel[3]);
				imovel.setDespejo(despejo);
			}

			// area construida - 4
			if(arrayImovel[4] != null){
				imovel.setAreaConstruida((BigDecimal) arrayImovel[4]);
			}

			// area construida faixa - menor 5
			AreaConstruidaFaixa areaConstruidaFaixa = null;
			if(arrayImovel[5] != null){
				areaConstruidaFaixa = new AreaConstruidaFaixa();
				areaConstruidaFaixa.setVolumeMenorFaixa((BigDecimal) arrayImovel[5]);
			}

			// area construida faixa - maior 6
			if(arrayImovel[6] != null){
				if(areaConstruidaFaixa == null){
					areaConstruidaFaixa = new AreaConstruidaFaixa();
				}
				areaConstruidaFaixa.setVolumeMaiorFaixa((BigDecimal) arrayImovel[6]);
			}

			imovel.setAreaConstruidaFaixa(areaConstruidaFaixa);

			// testada do lote - 7
			if(arrayImovel[7] != null){
				imovel.setTestadaLote((Short) arrayImovel[7]);
			}

			// volumente reservatorio inferior 8
			if(arrayImovel[8] != null){
				imovel.setVolumeReservatorioInferior((BigDecimal) arrayImovel[8]);
			}

			// Volume Reservatorio Inferior - menor 9
			ReservatorioVolumeFaixa reservatorioVolumeFaixaInferior = null;
			if(arrayImovel[9] != null){
				reservatorioVolumeFaixaInferior = new ReservatorioVolumeFaixa();
				reservatorioVolumeFaixaInferior.setVolumeMenorFaixa((BigDecimal) arrayImovel[9]);
			}

			// Volume Reservatorio Inferior - maior 10
			if(arrayImovel[10] != null){
				if(reservatorioVolumeFaixaInferior == null){
					reservatorioVolumeFaixaInferior = new ReservatorioVolumeFaixa();
				}
				reservatorioVolumeFaixaInferior.setVolumeMaiorFaixa((BigDecimal) arrayImovel[10]);
			}

			imovel.setReservatorioVolumeFaixaInferior(reservatorioVolumeFaixaInferior);

			// volumente reservatorio superior 11
			if(arrayImovel[11] != null){
				imovel.setVolumeReservatorioSuperior((BigDecimal) arrayImovel[11]);
			}

			// Volume Reservatorio Superior - menor 12
			ReservatorioVolumeFaixa reservatorioVolumeFaixaSuperior = null;
			if(arrayImovel[12] != null){
				reservatorioVolumeFaixaSuperior = new ReservatorioVolumeFaixa();
				reservatorioVolumeFaixaSuperior.setVolumeMenorFaixa((BigDecimal) arrayImovel[12]);
			}

			// Volume Reservatorio Superior - maior 13
			if(arrayImovel[13] != null){
				if(reservatorioVolumeFaixaSuperior == null){
					reservatorioVolumeFaixaSuperior = new ReservatorioVolumeFaixa();
				}
				reservatorioVolumeFaixaSuperior.setVolumeMaiorFaixa((BigDecimal) arrayImovel[13]);
			}

			imovel.setReservatorioVolumeFaixaSuperior(reservatorioVolumeFaixaSuperior);

			// Volume Piscina - 14
			if(arrayImovel[14] != null){
				imovel.setVolumePiscina((BigDecimal) arrayImovel[14]);
			}

			// Volume Piscina - menor 15
			PiscinaVolumeFaixa piscinaVolumeFaixa = null;
			if(arrayImovel[15] != null){
				piscinaVolumeFaixa = new PiscinaVolumeFaixa();
				piscinaVolumeFaixa.setVolumeMenorFaixa((BigDecimal) arrayImovel[15]);
			}

			// Volume Piscina - maior 16
			if(arrayImovel[16] != null){
				if(piscinaVolumeFaixa == null){
					piscinaVolumeFaixa = new PiscinaVolumeFaixa();
				}
				piscinaVolumeFaixa.setVolumeMaiorFaixa((BigDecimal) arrayImovel[16]);
			}

			imovel.setPiscinaVolumeFaixa(piscinaVolumeFaixa);

			// Fonte Abastecimento- 17
			FonteAbastecimento fonteAbastecimento = null;
			if(arrayImovel[17] != null){
				fonteAbastecimento = new FonteAbastecimento();
				fonteAbastecimento.setDescricao((String) arrayImovel[17]);
				imovel.setFonteAbastecimento(fonteAbastecimento);
			}

			// Poco Tipo- 18
			PocoTipo pocoTipo = null;
			if(arrayImovel[18] != null){
				pocoTipo = new PocoTipo();
				pocoTipo.setDescricao((String) arrayImovel[18]);
				imovel.setPocoTipo(pocoTipo);
			}

			// Distrito Operacional- 19
			DistritoOperacional distritoOperacional = null;
			if(arrayImovel[19] != null){
				distritoOperacional = new DistritoOperacional();
				distritoOperacional.setDescricao((String) arrayImovel[19]);

				quadra.setDistritoOperacional(distritoOperacional);
			}

			// Pavimento Rua- 20
			PavimentoRua pavimentoRua = null;
			if(arrayImovel[20] != null){
				pavimentoRua = new PavimentoRua();
				pavimentoRua.setDescricao((String) arrayImovel[20]);
				imovel.setPavimentoRua(pavimentoRua);
			}

			// Pavimento Calçada- 21
			PavimentoCalcada pavimentoCalcada = null;
			if(arrayImovel[21] != null){
				pavimentoCalcada = new PavimentoCalcada();
				pavimentoCalcada.setDescricao((String) arrayImovel[21]);
				imovel.setPavimentoCalcada(pavimentoCalcada);
			}

			// Numero IPTU- 22
			if(arrayImovel[22] != null){
				imovel.setNumeroIptu((BigDecimal) arrayImovel[22]);
			}

			// Numero CELPE- 23
			if(arrayImovel[23] != null){
				imovel.setNumeroCelpe((Long) arrayImovel[23]);
			}

			// Coordenada X- 24
			if(arrayImovel[24] != null){
				imovel.setCoordenadaX((BigDecimal) arrayImovel[24]);
			}
			// Coordenada Y- 25
			if(arrayImovel[25] != null){
				imovel.setCoordenadaY((BigDecimal) arrayImovel[25]);
			}

			// Cadastro Ocorrencia- 26
			CadastroOcorrencia cadastroOcorrencia = null;
			if(arrayImovel[26] != null){
				cadastroOcorrencia = new CadastroOcorrencia();
				cadastroOcorrencia.setDescricao((String) arrayImovel[26]);
				imovel.setCadastroOcorrencia(cadastroOcorrencia);
			}
			// Elo Anormalidade- 27
			EloAnormalidade eloAnormalidade = null;
			if(arrayImovel[27] != null){
				eloAnormalidade = new EloAnormalidade();
				eloAnormalidade.setDescricao((String) arrayImovel[27]);
				imovel.setEloAnormalidade(eloAnormalidade);
			}

			// Indicador Imovel Condominio- 28
			if(arrayImovel[28] != null){
				imovel.setIndicadorImovelCondominio((Short) arrayImovel[28]);
			}

			// Imovel Condominio- 29
			Imovel imovelCondominio = null;
			if(arrayImovel[29] != null){
				imovelCondominio = new Imovel();
				imovelCondominio.setId((Integer) arrayImovel[29]);
				imovel.setImovelCondominio(imovelCondominio);
			}

			// Imovel Principal- 30
			Imovel imovelPrincipal = null;
			if(arrayImovel[30] != null){
				imovelPrincipal = new Imovel();
				imovelPrincipal.setId((Integer) arrayImovel[30]);
				imovel.setImovelPrincipal(imovelPrincipal);
			}

			// Numero Pontos Utilizacao- 31
			if(arrayImovel[31] != null){
				imovel.setNumeroPontosUtilizacao((Short) arrayImovel[31]);
			}

			// Numero Moradores- 32
			if(arrayImovel[32] != null){
				imovel.setNumeroMorador((Short) arrayImovel[32]);
			}

			// Jardim- 33
			if(arrayImovel[33] != null){
				imovel.setIndicadorJardim((Short) arrayImovel[33]);
			}

			// Divisão de Esgoto- 34
			Bacia bacia = null;
			if(arrayImovel[34] != null){
				bacia = new Bacia();
				bacia.setDescricao((String) arrayImovel[34]);

				// Divisão de Esgoto
				if(arrayImovel[36] != null){
					SistemaEsgoto sistemaEsgoto = new SistemaEsgoto();
					DivisaoEsgoto divisaoEsgoto = new DivisaoEsgoto();

					divisaoEsgoto.setDescricao((String) arrayImovel[36]);

					sistemaEsgoto.setDivisaoEsgoto(divisaoEsgoto);
					bacia.setSistemaEsgoto(sistemaEsgoto);

				}

				quadra.setBacia(bacia);
			}
			imovel.setQuadra(quadra);

			// Esgotamento- 37
			if(arrayImovel[37] != null){
				Esgotamento esgotamento = new Esgotamento();
				esgotamento.setDescricao((String) arrayImovel[37]);
				imovel.setEsgotamento(esgotamento);
			}

			// Padrao Construcao- 38
			if(arrayImovel[38] != null){
				PadraoConstrucao padraoConstrucao = new PadraoConstrucao();
				padraoConstrucao.setDescricao((String) arrayImovel[38]);
				imovel.setPadraoConstrucao(padraoConstrucao);
			}

			// Numero Quarto- 39
			if(arrayImovel[39] != null){
				imovel.setNumeroQuarto((Short) arrayImovel[39]);
			}

			// Numero Banheiro- 40
			if(arrayImovel[40] != null){
				imovel.setNumeroBanheiro((Short) arrayImovel[40]);
			}

			// Renda Familiar Faixa- 41 - 42
			if(arrayImovel[41] != null && arrayImovel[42] != null){
				RendaFamiliarFaixa rendaFamiliarFaixa = new RendaFamiliarFaixa();
				rendaFamiliarFaixa.setMenorFaixa((Integer) arrayImovel[41]);
				rendaFamiliarFaixa.setMaiorFaixa((Integer) arrayImovel[42]);
				imovel.setRendaFamiliarFaixa(rendaFamiliarFaixa);
			}

			// Numero Adulto- 43
			if(arrayImovel[43] != null){
				imovel.setNumeroAdulto((Short) arrayImovel[43]);
			}

			// Numero Criança- 44
			if(arrayImovel[44] != null){
				imovel.setNumeroCrianca((Short) arrayImovel[44]);
			}

			// Numero Criança- 45
			if(arrayImovel[45] != null){
				imovel.setNumeroMoradorTrabalhador((Short) arrayImovel[45]);
			}

			// Foto Fachada- 46
			if(arrayImovel[46] != null){
				byte[] fotoFachadaImovel = (byte[]) arrayImovel[46];

				imovel.setFotoFachada(fotoFachadaImovel);
			}

			// imovelContaEnvio- 47
			if(arrayImovel[47] != null){
				ImovelContaEnvio imovelContaEnvio = ((ImovelContaEnvio) arrayImovel[47]);

				imovel.setImovelContaEnvio(imovelContaEnvio);
			}

			// Indicador de Contrato de Consumo
			if(arrayImovel[48] != null){
				Short indicadorContratoConsumo = (Short) arrayImovel[48];
				imovel.setIndicadorContratoConsumo(indicadorContratoConsumo);
			}

			// Número da Quadra
			if(arrayImovel[49] != null){
				int numeroQuadra = (Integer) arrayImovel[49];
				quadra.setNumeroQuadra(numeroQuadra);
			}

			// Setor de Abastecimento
			if(arrayImovel[53] != null){
				String descricaoSetorAbastecimento = (String) arrayImovel[53];

				SetorAbastecimento setorAbastecimento = new SetorAbastecimento();
				setorAbastecimento.setDescricao(descricaoSetorAbastecimento);

				imovel.setSetorAbastecimento(setorAbastecimento);

				// Sistema de Abastecimento
				if(arrayImovel[52] != null){
					String descricaoSistemaAbastecimento = (String) arrayImovel[52];

					SistemaAbastecimento sistemaAbastecimento = new SistemaAbastecimento();
					sistemaAbastecimento.setDescricao(descricaoSistemaAbastecimento);

					setorAbastecimento.setSistemaAbastecimento(sistemaAbastecimento);
				}
			}

			// Sub-Bacia
			if(arrayImovel[51] != null){
				String descricaoSubBacia = (String) arrayImovel[51];

				SubBacia subBaciaDoImovel = new SubBacia();
				subBaciaDoImovel.setDescricao(descricaoSubBacia);

				imovel.setSubBacia(subBaciaDoImovel);

				// Bacia
				if(arrayImovel[50] != null){
					String descricaoBaciaDoImovel = (String) arrayImovel[50];

					Bacia baciaDoImovel = new Bacia();
					baciaDoImovel.setDescricao(descricaoBaciaDoImovel);

					subBaciaDoImovel.setBacia(baciaDoImovel);
				}

			}

			// Distrito Operacional do Imóvel
			if(arrayImovel[54] != null){
				String distritoOperacionalDescricao = (String) arrayImovel[54];
				DistritoOperacional distritoOperacionalImovel = new DistritoOperacional();
				distritoOperacionalImovel.setDescricao(distritoOperacionalDescricao);
				imovel.setDistritoOperacional(distritoOperacionalImovel);
			}
			
			// Complemento do Endereço
			if(arrayImovel[55] != null){
				String complementoEndereco = (String) arrayImovel[55];
				imovel.setComplementoEndereco(complementoEndereco);
			}
		}

		return imovel;
	}

	/**
	 * Consulta os Clientes do Imovel [UC0472] Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 07/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarClientesImovel(Integer idImovel) throws ControladorException{

		Collection colecaoClientes = null;
		Collection<ClienteImovel> clienteImoveis = null;

		try{

			colecaoClientes = this.repositorioImovel.pesquisarClientesImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoClientes != null && !colecaoClientes.isEmpty()){

			clienteImoveis = new ArrayList();

			Iterator iteratorColecaoClientes = colecaoClientes.iterator();

			while(iteratorColecaoClientes.hasNext()){

				ClienteImovel clienteImovel = new ClienteImovel();

				Object[] arrayCliente = (Object[]) iteratorColecaoClientes.next();

				Cliente cliente = new Cliente();
				ClienteFone clienteFone = null;
				ClienteTipo clienteTipo = null;

				// 0 - id co cliente
				if(arrayCliente[0] != null){
					cliente.setId((Integer) arrayCliente[0]);
				}
				// 1 - nome do cliente
				if(arrayCliente[1] != null){
					cliente.setNome((String) arrayCliente[1]);
				}
				// 2 - descricao cliente relação tipo
				// 8 - id cliente relação tipo
				if(arrayCliente[2] != null){
					ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();
					clienteRelacaoTipo.setDescricao((String) arrayCliente[2]);
					clienteRelacaoTipo.setId((Integer) arrayCliente[8]);
					clienteImovel.setClienteRelacaoTipo(clienteRelacaoTipo);
				}
				// 3 - data inicio relação
				if(arrayCliente[3] != null){
					clienteImovel.setDataInicioRelacao((Date) arrayCliente[3]);
				}
				// 4 - telefone
				if(arrayCliente[4] != null){
					clienteFone = new ClienteFone();
					clienteFone.setTelefone((String) arrayCliente[4]);
				}
				// 5 - cnpj
				if(arrayCliente[5] != null){
					cliente.setCnpj((String) arrayCliente[5]);
				}
				// 6 - cpf
				if(arrayCliente[6] != null){
					cliente.setCpf((String) arrayCliente[6]);
				}

				// 7 - ddd
				if(arrayCliente[7] != null && clienteFone != null){
					clienteFone.setDdd((String) arrayCliente[7]);
				}

				// 9 - Id do ClienteImovel
				if(arrayCliente[9] != null){
					clienteImovel.setId((Integer) arrayCliente[9]);
				}

				Set clienteFones = new TreeSet();
				if(clienteFone != null){
					clienteFones.add(clienteFone);
					cliente.setClienteFones(clienteFones);
				}

				// 10 - E-mail
				if(arrayCliente[10] != null){
					cliente.setEmail((String) arrayCliente[10]);
				}

				// 11 - Id do ClienteTipo
				if(arrayCliente[11] != null){
					clienteTipo = new ClienteTipo();
					clienteTipo.setId((Integer) arrayCliente[11]);

					cliente.setClienteTipo(clienteTipo);
				}

				// 12 - Indicador de Pessoa Fisica / Juridica
				if(arrayCliente[12] != null){
					if(clienteTipo == null){
						clienteTipo = new ClienteTipo();
					}

					clienteTipo.setIndicadorPessoaFisicaJuridica((Short) arrayCliente[12]);

					cliente.setClienteTipo(clienteTipo);
				}

				// cliente.setclImovel.setCliente(cliente);
				clienteImovel.setCliente(cliente);

				clienteImoveis.add(clienteImovel);

			}// fim do while
		}
		return clienteImoveis;
	}

	/**
	 * Pesquisa a coleção de categorias do imovel [UC0472] Consultar Imovel
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarCategoriasImovel(Integer idImovel) throws ControladorException{

		Collection colecaoCategorias = null;
		Collection<ImovelSubcategoria> imoveisSubCategoria = null;

		try{

			colecaoCategorias = this.repositorioImovel.pesquisarCategoriasImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoCategorias != null && !colecaoCategorias.isEmpty()){

			imoveisSubCategoria = new ArrayList();

			Iterator iteratorColecaoCategorias = colecaoCategorias.iterator();

			while(iteratorColecaoCategorias.hasNext()){

				Object[] arrayCategoria = (Object[]) iteratorColecaoCategorias.next();

				ImovelSubcategoria imovelSubcategoria = new ImovelSubcategoria();

				ImovelSubcategoriaPK imovelSubcategoriaPK = new ImovelSubcategoriaPK();
				Subcategoria subcategoria = new Subcategoria();

				// 0 - descricao da categoria
				if(arrayCategoria[0] != null){
					Categoria categoria = new Categoria();
					categoria.setDescricao((String) arrayCategoria[0]);
					categoria.setId(((Categoria) arrayCategoria[3]).getId());
					categoria.setQuantidadeEconomiasCategoria(((Short) arrayCategoria[2]).intValue());

					subcategoria.setCategoria(categoria);
				}
				// 1 - descricao da subcategoria
				if(arrayCategoria[1] != null){
					subcategoria.setDescricao((String) arrayCategoria[1]);
				}

				// 2 - quantidade de economias
				if(arrayCategoria[2] != null){
					imovelSubcategoria.setQuantidadeEconomias(((Short) arrayCategoria[2]).shortValue());
				}

				imovelSubcategoriaPK.setSubcategoria(subcategoria);

				imovelSubcategoria.setComp_id(imovelSubcategoriaPK);

				imoveisSubCategoria.add(imovelSubcategoria);

			}// fim do while
		}
		return imoveisSubCategoria;

	}

	/**
	 * [UC0475] Consultar Perfil Imovel
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idImovel
	 * @return Perfil do Imóvel
	 * @exception ControladorException
	 */
	public ImovelPerfil obterImovelPerfil(Integer idImovel) throws ControladorException{

		ImovelPerfil imovelPerfil = null;
		try{
			imovelPerfil = this.repositorioImovel.obterImovelPerfil(idImovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return imovelPerfil;
	}

	/**
	 * Consulta os Dados Complementares do Imovel
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarImovelDadosComplementares(Integer idImovel) throws ControladorException{

		Imovel imovel = null;
		Collection colecaoImovel = null;

		try{

			colecaoImovel = this.repositorioImovel.consultarImovelDadosComplementares(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			imovel = new Imovel();

			imovel.setId(idImovel);

			Iterator imovelIterator = colecaoImovel.iterator();

			Object[] arrayImovel = (Object[]) imovelIterator.next();

			LigacaoAgua ligacaoAgua = null;
			LigacaoAguaSituacao ligacaoAguaSituacao = null;

			// id ligacaoAguaSituacao - 0
			if(arrayImovel[0] != null){
				ligacaoAguaSituacao = new LigacaoAguaSituacao();

				ligacaoAguaSituacao.setDescricao((String) arrayImovel[0]);
				imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
			}

			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			// id ligacaoEsgotoSituacao - 1
			if(arrayImovel[1] != null){
				ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();

				ligacaoEsgotoSituacao.setDescricao((String) arrayImovel[1]);
				imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
			}

			ConsumoTarifa consumoTarifa = null;
			boolean tarifaTemporariaVigente = false;

			// descricao consumo Tarifa Temporária - 15
			if(arrayImovel[15] != null){
				consumoTarifa = new ConsumoTarifa();
				consumoTarifa.setDescricao((String) arrayImovel[15]);

				// data validade consumo Tarifa Temporária - 16
				if(arrayImovel[16] != null){
					Date dataValidadeTarifaTemporaria = (Date) arrayImovel[16];

					int comparacaoDatas = Util.compararData(dataValidadeTarifaTemporaria, new Date());

					if(comparacaoDatas != -1){
						tarifaTemporariaVigente = true;
						imovel.setConsumoTarifa(consumoTarifa);
						imovel.setDataValidadeTarifaTemporaria(dataValidadeTarifaTemporaria);
					}
				}
			}

			// descricao consumo Tarifa - 2
			if(arrayImovel[2] != null && !tarifaTemporariaVigente){
				consumoTarifa = new ConsumoTarifa();

				consumoTarifa.setDescricao((String) arrayImovel[2]);
				imovel.setConsumoTarifa(consumoTarifa);
			}

			// numero Retificacao - 3
			if(arrayImovel[3] != null){
				imovel.setNumeroRetificacao((Short) arrayImovel[3]);
			}

			// numero Parcelamento - 4
			if(arrayImovel[4] != null){
				imovel.setNumeroParcelamento((Short) arrayImovel[4]);
			}

			// numero Reparcelamento - 5
			if(arrayImovel[5] != null){
				imovel.setNumeroReparcelamento((Short) arrayImovel[5]);
			}

			// numero Reparcelamento Consecutivos - 6
			if(arrayImovel[6] != null){
				imovel.setNumeroReparcelamentoConsecutivos((Short) arrayImovel[6]);
			}

			// cobranca situacao
			CobrancaSituacao cobrancaSituacao = null;
			try{
				cobrancaSituacao = repositorioImovel.pesquisarImovelCobrancaSituacao(idImovel);
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

			if(cobrancaSituacao != null){
				imovel.setCobrancaSituacao(cobrancaSituacao);
			}

			// cadastro ocorrencia - 8
			CadastroOcorrencia cadastroOcorrencia = null;
			if(arrayImovel[8] != null){
				cadastroOcorrencia = new CadastroOcorrencia();
				cadastroOcorrencia.setDescricao((String) arrayImovel[8]);
				imovel.setCadastroOcorrencia(cadastroOcorrencia);
			}

			// Elo Anormalidade - 9
			EloAnormalidade eloAnormalidade = null;
			if(arrayImovel[9] != null){
				eloAnormalidade = new EloAnormalidade();
				eloAnormalidade.setDescricao((String) arrayImovel[9]);
				imovel.setEloAnormalidade(eloAnormalidade);
			}

			// Id Funcionario - 10
			// Nome Funcionario - 11
			if(arrayImovel[10] != null){
				Funcionario funcionario = new Funcionario();

				funcionario.setId((Integer) arrayImovel[10]);
				funcionario.setNome((String) arrayImovel[11]);

				imovel.setFuncionario(funcionario);
			}

			if(arrayImovel[12] != null){
				if(ligacaoAgua == null){
					ligacaoAgua = new LigacaoAgua();
				}

				Integer numeroCorte = (Integer) arrayImovel[12];
				ligacaoAgua.setNumeroCorte(numeroCorte);
			}

			if(arrayImovel[13] != null){
				if(ligacaoAgua == null){
					ligacaoAgua = new LigacaoAgua();
				}

				Integer numeroCorteAdministrativo = (Integer) arrayImovel[13];
				ligacaoAgua.setNumeroCorteAdministrativo(numeroCorteAdministrativo);
			}

			if(arrayImovel[14] != null){
				if(ligacaoAgua == null){
					ligacaoAgua = new LigacaoAgua();
				}

				Integer numeroSupressaoAgua = (Integer) arrayImovel[14];
				ligacaoAgua.setNumeroSupressaoAgua(numeroSupressaoAgua);
			}

			imovel.setLigacaoAgua(ligacaoAgua);
		}

		return imovel;

	}

	/**
	 * Pesquisa a coleção de vencimento alternativos do imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarVencimentoAlternativoImovel(Integer idImovel) throws ControladorException{

		Collection colecaoVencimentosAlternativos = null;
		Collection<VencimentoAlternativo> imoveisVencimentosAlternativos = null;

		try{

			colecaoVencimentosAlternativos = this.repositorioImovel.pesquisarVencimentoAlternativoImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoVencimentosAlternativos != null && !colecaoVencimentosAlternativos.isEmpty()){

			imoveisVencimentosAlternativos = new ArrayList();

			Iterator iteratorColecaoVencimentoAlternativo = colecaoVencimentosAlternativos.iterator();

			while(iteratorColecaoVencimentoAlternativo.hasNext()){

				Object[] array = (Object[]) iteratorColecaoVencimentoAlternativo.next();

				VencimentoAlternativo vencimentoAlternativo = new VencimentoAlternativo();

				// ImovelSubcategoriaPK imovelSubcategoriaPK = new
				// ImovelSubcategoriaPK();
				// Subcategoria subcategoria = new Subcategoria();

				// 0 - date vencimento
				if(array[0] != null){
					vencimentoAlternativo.setDateVencimento((Short) array[0]);
				}
				// 1 - data implantacao
				if(array[1] != null){
					vencimentoAlternativo.setDataImplantacao((Date) array[1]);
				}

				// 2 - data exclusao
				if(array[2] != null){
					vencimentoAlternativo.setDateExclusao((Date) array[2]);
				}

				imoveisVencimentosAlternativos.add(vencimentoAlternativo);

			}// fim do while
		}
		return imoveisVencimentosAlternativos;
	}

	/**
	 * Pesquisa a coleção de situações de cobrança do imovel
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Vitor Hora
	 * @date 15/07/2008
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarSituacoesCobrancaImovel(Integer idImovel) throws ControladorException{

		Collection colecaoSituacoesCobranca = null;
		Collection<ImovelCobrancaSituacao> imoveisSituacoesCobranca = null;

		try{

			colecaoSituacoesCobranca = this.repositorioImovel.pesquisarSituacoesCobrancaImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoSituacoesCobranca != null && !colecaoSituacoesCobranca.isEmpty()){

			imoveisSituacoesCobranca = new ArrayList();

			Iterator iteratorColecaoSituacoesCobranca = colecaoSituacoesCobranca.iterator();

			while(iteratorColecaoSituacoesCobranca.hasNext()){

				Object[] array = (Object[]) iteratorColecaoSituacoesCobranca.next();

				ImovelCobrancaSituacao imovelCobrancaSituacao = new ImovelCobrancaSituacao();

				// 0 - id do do imovel cobrança situação
				if(array[0] != null){
					imovelCobrancaSituacao.setId((Integer) array[0]);
				}
				// 1 - descricao da cobrança situação
				if(array[1] != null){
					CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
					cobrancaSituacao.setDescricao((String) array[1]);
					imovelCobrancaSituacao.setCobrancaSituacao(cobrancaSituacao);
				}

				// 2 - mes referencia inicio
				if(array[2] != null){
					imovelCobrancaSituacao.setAnoMesReferenciaInicio((Integer) array[2]);
				}

				// 3 - mes referencia final
				if(array[3] != null){
					imovelCobrancaSituacao.setAnoMesReferenciaFinal((Integer) array[3]);
				}

				// 4 - mes data implantação cobrança
				if(array[4] != null){
					imovelCobrancaSituacao.setDataImplantacaoCobranca((Date) array[4]);
				}

				// 5 - mes data retirada cobranca
				if(array[5] != null){
					imovelCobrancaSituacao.setDataRetiradaCobranca((Date) array[5]);
				}

				// 6 - mes data retirada cobranca
				if(array[6] != null){
					Cliente cliente = new Cliente();
					cliente.setId((Integer) array[6]);
					imovelCobrancaSituacao.setCliente(cliente);
				}

				// 7 - nome escritorio
				if(array[7] != null){
					Cliente cliente = new Cliente();
					cliente.setNome((String) array[7]);
					imovelCobrancaSituacao.setEscritorio(cliente);
				}

				// 8 - nome advogado
				if(array[8] != null){
					Cliente cliente = new Cliente();
					cliente.setNome((String) array[8]);
					imovelCobrancaSituacao.setAdvogado(cliente);
				}

				// 9 - Numero do Processo
				if(array[9] != null){
					imovelCobrancaSituacao.setNumeroProcessoAdministrativoExecucaoFiscal((Integer) array[9]);
				}

				imoveisSituacoesCobranca.add(imovelCobrancaSituacao);

			}// fim do while
		}
		return imoveisSituacoesCobranca;
	}

	/**
	 * Pesquisa a coleção de Debitos Automaticos do imovel [UC0473] Consultar
	 * Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarDebitosAutomaticosImovel(Integer idImovel) throws ControladorException{

		Collection colecaoDebitosAutomaticos = null;
		Collection<DebitoAutomatico> imoveisDebitosAutomaticos = null;

		try{

			colecaoDebitosAutomaticos = this.repositorioImovel.pesquisarDebitosAutomaticosImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoDebitosAutomaticos != null && !colecaoDebitosAutomaticos.isEmpty()){

			imoveisDebitosAutomaticos = new ArrayList();

			Iterator iteratorColecaoDebitosAutomaticos = colecaoDebitosAutomaticos.iterator();

			while(iteratorColecaoDebitosAutomaticos.hasNext()){

				Object[] array = (Object[]) iteratorColecaoDebitosAutomaticos.next();

				DebitoAutomatico debitoAutomatico = new DebitoAutomatico();

				// 0 - nome abreviado banco
				Agencia agencia = null;
				if(array[0] != null){
					Banco banco = new Banco();
					banco.setDescricaoAbreviada((String) array[0]);
					agencia = new Agencia();
					agencia.setBanco(banco);
					debitoAutomatico.setAgencia(agencia);
				}
				// 1 - codigo agencia
				if(array[1] != null){
					if(agencia == null){
						agencia = new Agencia();
					}
					agencia.setCodigoAgencia((String) array[1]);
				}

				debitoAutomatico.setAgencia(agencia);

				// 2 - identicacao do cliente banco
				if(array[2] != null){
					debitoAutomatico.setIdentificacaoClienteBanco((String) array[2]);
				}
				// 3 - data da opção
				if(array[3] != null){
					debitoAutomatico.setDataOpcaoDebitoContaCorrente((Date) array[3]);
				}
				// 4 - data da inclusão
				if(array[4] != null){
					debitoAutomatico.setDataInclusaoNovoDebitoAutomatico((Date) array[4]);
				}

				// 5 - data exclusao
				if(array[5] != null){
					debitoAutomatico.setDataExclusao((Date) array[5]);
				}

				imoveisDebitosAutomaticos.add(debitoAutomatico);

			}// fim do while
		}
		return imoveisDebitosAutomaticos;
	}

	/**
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarFaturamentosSituacaoHistorico(Integer idImovel) throws ControladorException{

		Collection colecaoFaturamentosSituacaoHistorico = null;
		Collection<FaturamentoSituacaoHistorico> imoveisFaturamentosSituacaoHistorico = null;

		try{

			colecaoFaturamentosSituacaoHistorico = this.repositorioImovel.pesquisarFaturamentosSituacaoHistorico(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoFaturamentosSituacaoHistorico != null && !colecaoFaturamentosSituacaoHistorico.isEmpty()){

			imoveisFaturamentosSituacaoHistorico = new ArrayList();

			Iterator iteratorColecaoFaturamentosSituacaoHistorico = colecaoFaturamentosSituacaoHistorico.iterator();

			while(iteratorColecaoFaturamentosSituacaoHistorico.hasNext()){

				Object[] array = (Object[]) iteratorColecaoFaturamentosSituacaoHistorico.next();

				FaturamentoSituacaoHistorico faturamentoSituacaoHistorico = new FaturamentoSituacaoHistorico();

				// 0 - id do fatutamento situacao tipo
				// 1 - descricao do fatutamento situacao tipo
				FaturamentoSituacaoTipo faturamentoSituacaoTipo = null;
				if(array[0] != null && array[1] != null){
					faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
					faturamentoSituacaoTipo.setId((Integer) array[0]);
					faturamentoSituacaoTipo.setDescricao((String) array[1]);

					faturamentoSituacaoHistorico.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);
				}

				// 2 - id do fatutamento situacao motivo
				// 3 - descricao do fatutamento situacao motivo
				FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = null;
				if(array[2] != null && array[3] != null){
					faturamentoSituacaoMotivo = new FaturamentoSituacaoMotivo();
					faturamentoSituacaoMotivo.setId((Integer) array[2]);
					faturamentoSituacaoMotivo.setDescricao((String) array[3]);

					faturamentoSituacaoHistorico.setFaturamentoSituacaoMotivo(faturamentoSituacaoMotivo);
				}

				// 4 - mes nao faturamento inicio
				if(array[4] != null){
					faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoInicio((Integer) array[4]);
				}

				// 5 - mes nao faturamento fim
				if(array[5] != null){
					faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoFim((Integer) array[5]);
				}

				// 6 - mes nao faturamento retirada
				if(array[6] != null){
					faturamentoSituacaoHistorico.setAnoMesFaturamentoRetirada((Integer) array[6]);
				}

				Usuario usuario = null;
				// 7 - usuasrio
				if(array[7] != null){
					usuario = new Usuario();
					usuario.setNomeUsuario((String) array[7]);
					faturamentoSituacaoHistorico.setUsuario(usuario);
				}

				imoveisFaturamentosSituacaoHistorico.add(faturamentoSituacaoHistorico);

			}// fim do while
		}
		return imoveisFaturamentosSituacaoHistorico;
	}

	/**
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel
	 * UC0069-ManterDadosTarifaSocial
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarFaturamentoSituacaoHistorico(Integer idImovel) throws ControladorException{

		Collection colecaoFaturamentosSituacaoHistorico = null;

		try{

			colecaoFaturamentosSituacaoHistorico = this.repositorioImovel.pesquisarFaturamentoSituacaoHistorico(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoFaturamentosSituacaoHistorico;
	}

	// /**
	// * Pesquisa a coleção de Faturamento Situação Historico do Imovel Ativos [UC0156]
	// *
	// * @author Saulo Lima
	// * @date 22/08/2008
	// * @param Integer
	// * idFaturamentoSituacaoTipo
	// * @return Collection
	// * FaruramentoSituacaoHistorico
	// *
	// * @exception ControladorException
	// */
	// public Collection pesquisarFaturamentosSituacaoHistoricoAtivos(Integer
	// idFaturamentoSituacaoTipo)
	// throws ControladorException{
	//
	// Collection colecaoFaturamentosSituacaoHistoricoAtivos = null;
	// try {
	// colecaoFaturamentosSituacaoHistoricoAtivos =
	// this.repositorioImovel.pesquisarFaturamentosSituacaoHistoricoAtivos(idFaturamentoSituacaoTipo);
	// } catch (ErroRepositorioException ex) {
	// sessionContext.setRollbackOnly();
	// ex.printStackTrace();
	// throw new ControladorException("erro.sistema", ex);
	// }
	// return colecaoFaturamentosSituacaoHistoricoAtivos;
	//
	// }

	/**
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel Ativos [UC0156]
	 * 
	 * @author Saulo Lima
	 * @date 22/08/2008
	 * @param Integer
	 *            idFaturamentoSituacaoTipo
	 * @return Collection
	 *         FaruramentoSituacaoHistorico
	 * @exception ControladorException
	 */
	public Collection pesquisarFaturamentosSituacaoHistoricoAtivos(SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper)
					throws ControladorException{

		Collection colecaoFaturamentosSituacaoHistoricoAtivos = null;
		try{
			colecaoFaturamentosSituacaoHistoricoAtivos = this.repositorioImovel
							.pesquisarFaturamentosSituacaoHistoricoAtivos(situacaoEspecialFaturamentoHelper);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return colecaoFaturamentosSituacaoHistoricoAtivos;

	}

	/**
	 * Pesquisa a coleção de cobranças Situação Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarCobrancasSituacaoHistorico(Integer idImovel) throws ControladorException{

		Collection colecaoCobrancasSituacaoHistorico = null;
		Collection<CobrancaSituacaoHistorico> imoveisCobrancasSituacaoHistorico = null;

		try{

			colecaoCobrancasSituacaoHistorico = this.repositorioImovel.pesquisarCobrancasSituacaoHistorico(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoCobrancasSituacaoHistorico != null && !colecaoCobrancasSituacaoHistorico.isEmpty()){

			imoveisCobrancasSituacaoHistorico = new ArrayList();

			Iterator iteratorColecaoCobrancasSituacaoHistorico = colecaoCobrancasSituacaoHistorico.iterator();

			Usuario usuarioInclusao = null;
			Usuario usuarioExclusao = null;

			while(iteratorColecaoCobrancasSituacaoHistorico.hasNext()){

				Object[] array = (Object[]) iteratorColecaoCobrancasSituacaoHistorico.next();

				CobrancaSituacaoHistorico cobrancaSituacaoHistorico = new CobrancaSituacaoHistorico();

				// 0 - descricao do cobranca situacao tipo
				CobrancaSituacaoTipo cobrancaSituacaoTipo = null;
				if(array[0] != null){
					cobrancaSituacaoTipo = new CobrancaSituacaoTipo();
					cobrancaSituacaoTipo.setId((Integer) array[9]);
					cobrancaSituacaoTipo.setDescricao((String) array[0]);
					cobrancaSituacaoHistorico.setCobrancaSituacaoTipo(cobrancaSituacaoTipo);
				}

				// 1 - descricao do fatutamento situacao motivo
				CobrancaSituacaoMotivo cobrancaSituacaoMotivo = null;
				if(array[1] != null){
					cobrancaSituacaoMotivo = new CobrancaSituacaoMotivo();
					cobrancaSituacaoMotivo.setId((Integer) array[10]);
					cobrancaSituacaoMotivo.setDescricao((String) array[1]);

					cobrancaSituacaoHistorico.setCobrancaSituacaoMotivo(cobrancaSituacaoMotivo);
				}

				// 2 - mes nao faturamento inicio
				if(array[2] != null){
					cobrancaSituacaoHistorico.setAnoMesCobrancaSituacaoInicio((Integer) array[2]);
				}

				// 3 - mes nao faturamento fim
				if(array[3] != null){
					cobrancaSituacaoHistorico.setAnoMesCobrancaSituacaoFim((Integer) array[3]);
				}

				// 4 - mes nao faturamento retirada
				if(array[4] != null){
					cobrancaSituacaoHistorico.setAnoMesCobrancaRetirada((Integer) array[4]);
				}

				// 5- usuasrio inclusão
				if(array[5] != null){
					usuarioInclusao = new Usuario();
					usuarioInclusao.setNomeUsuario((String) array[5]);
					cobrancaSituacaoHistorico.setUsuarioLogadoInclusao(usuarioInclusao);
				}

				// 6 - Data hora Inclusão
				if(array[6] != null){
					cobrancaSituacaoHistorico.setDataHoraInclusao((Date) array[6]);
				}

				// 7- usuasrio exclusão
				if(array[7] != null){
					usuarioExclusao = new Usuario();
					usuarioExclusao.setNomeUsuario((String) array[7]);
					cobrancaSituacaoHistorico.setUsuarioLogadoExclusao(usuarioExclusao);
				}

				// 8 - Data hora Exclusão
				if(array[8] != null){
					cobrancaSituacaoHistorico.setDataHoraExclusao((Date) array[8]);
				}

				// 12 - descricao do orgão externo
				if(array[12] != null){
					OrgaoExterno orgaoExterno = new OrgaoExterno();
					orgaoExterno.setId((Integer) array[11]);
					orgaoExterno.setDescricao((String) array[12]);

					cobrancaSituacaoHistorico.setOrgaoExterno(orgaoExterno);
				}

				// 13 - Número do Process
				if(array[13] != null){
					cobrancaSituacaoHistorico.setNumeroProcesso((String) array[13]);
				}

				imoveisCobrancasSituacaoHistorico.add(cobrancaSituacaoHistorico);

			}// fim do while
		}
		return imoveisCobrancasSituacaoHistorico;
	}

	/**
	 * Consutlar os Dados de Analise da Medição e Consumo do Imovel [UC0473]
	 * Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 12/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarImovelAnaliseMedicaoConsumo(Integer idImovel) throws ControladorException{

		Imovel imovel = null;
		Collection colecaoImovel = null;

		try{

			colecaoImovel = this.repositorioImovel.consultarImovelAnaliseMedicaoConsumo(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			imovel = new Imovel();

			imovel.setId(idImovel);

			Iterator imovelIterator = colecaoImovel.iterator();

			Object[] arrayImovel = (Object[]) imovelIterator.next();

			LigacaoAguaSituacao ligacaoAguaSituacao = null;
			// id ligacaoAguaSituacao - 0
			if(arrayImovel[0] != null){
				ligacaoAguaSituacao = new LigacaoAguaSituacao();

				ligacaoAguaSituacao.setDescricao((String) arrayImovel[0]);
				imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
			}

			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			// id ligacaoEsgotoSituacao - 1
			if(arrayImovel[1] != null){
				ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();

				ligacaoEsgotoSituacao.setDescricao((String) arrayImovel[1]);
				imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
			}

			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;
			// id hidrometroInstalacaoHistorico - 2
			if(arrayImovel[2] != null){
				hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();

				hidrometroInstalacaoHistorico.setId((Integer) arrayImovel[2]);
				imovel.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			}

			LigacaoAgua ligacaoAgua = null;
			// id ligacao Agua - 3
			if(arrayImovel[3] != null){
				ligacaoAgua = new LigacaoAgua();
				HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoAgua = new HidrometroInstalacaoHistorico();
				hidrometroInstalacaoHistoricoAgua.setId((Integer) arrayImovel[3]);
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistoricoAgua);
				imovel.setLigacaoAgua(ligacaoAgua);
			}

			// Segmento
			if(arrayImovel[4] != null){
				Short numeroSegmento = (Short) arrayImovel[4];
				imovel.setNumeroSegmento(numeroSegmento);
			}
		}

		return imovel;

	}

	/**
	 * Registrar leituras e anormalidades
	 * 
	 * @author Sávio Luiz
	 * @date 12/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public void atualizarImovelLeituraAnormalidade(Map<Integer, MedicaoHistorico> mapAtualizarLeituraAnormalidadeImovel, Date dataAtual)
					throws ControladorException{

		try{

			this.repositorioImovel.atualizarImovelLeituraAnormalidade(mapAtualizarLeituraAnormalidadeImovel, dataAtual);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Consutlar os Dados do Historico de Faturamento [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarImovelHistoricoFaturamento(Integer idImovel) throws ControladorException{

		Imovel imovel = null;
		Collection colecaoImovel = null;

		try{

			colecaoImovel = this.repositorioImovel.consultarImovelDadosCadastrais(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			imovel = new Imovel();

			imovel.setId(idImovel);

			Iterator imovelIterator = colecaoImovel.iterator();

			Object[] arrayImovel = (Object[]) imovelIterator.next();

			LigacaoAguaSituacao ligacaoAguaSituacao = null;
			// id ligacaoAguaSituacao - 0
			if(arrayImovel[0] != null){
				ligacaoAguaSituacao = new LigacaoAguaSituacao();

				ligacaoAguaSituacao.setDescricao((String) arrayImovel[0]);
				imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
			}

			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			// id ligacaoEsgotoSituacao - 1
			if(arrayImovel[1] != null){
				ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();

				ligacaoEsgotoSituacao.setDescricao((String) arrayImovel[1]);
				imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
			}

			CobrancaSituacao cobrancaSituacao = null;
			try{
				cobrancaSituacao = repositorioImovel.pesquisarImovelCobrancaSituacao(idImovel);
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

			if(cobrancaSituacao != null){
				imovel.setCobrancaSituacao(cobrancaSituacao);
			}

		}

		return imovel;
	}

	/**
	 * Pesquisas todas as Situações Especiais de Cobrança ativas de um determinado imóvel
	 * 
	 * @author Saulo Lima
	 * @date 09/09/2013
	 * @param idImovel
	 * @return Collection<ImovelCobrancaSituacao>
	 * @throws ControladorException
	 */
	public Collection<ImovelCobrancaSituacao> pesquisarImovelCobrancaSituacaoAtivas(Integer idImovel) throws ControladorException{

		Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = null;

		try{
			colecaoImovelCobrancaSituacao = this.repositorioImovel.pesquisarImovelCobrancaSituacaoAtivas(idImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoImovelCobrancaSituacao;
	}

	/**
	 * Consutlar o cliente usuário do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public String consultarClienteUsuarioImovel(Integer idImovel) throws ControladorException{

		String nomeClienteUsuario = null;

		try{

			nomeClienteUsuario = this.repositorioImovel.consultarClienteUsuarioImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return nomeClienteUsuario;
	}

	/**
	 * Consutlar as contas do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author Eduardo Henrique
	 * @date 02/10/2008
	 *       Adição de parâmetro para opção de Ordenação de referência
	 * @author Saulo Lima
	 * @date 10/08/2009
	 *       Novo atributo preenchido na colecao de contas: valorImposto
	 * @param idImovel
	 * @param ordemAscendente
	 *            true -> ASC ; false -> DESC
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection<Conta> consultarContasImovel(Integer idImovel, boolean ordemAscendente) throws ControladorException{

		Collection colecaoContas = null;
		Collection<Conta> imoveisContas = null;

		try{
			colecaoContas = this.repositorioImovel.consultarContasImovel(idImovel, true);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoContas != null && !colecaoContas.isEmpty()){

			imoveisContas = new ArrayList<Conta>();
			Iterator iteratorColecaoContas = colecaoContas.iterator();

			while(iteratorColecaoContas.hasNext()){

				Object[] array = (Object[]) iteratorColecaoContas.next();
				Conta conta = new Conta();

				// 0 - id Conta
				if(array[0] != null){
					conta.setId((Integer) array[0]);
				}

				// 1 - Referencia
				if(array[1] != null){
					conta.setReferencia((Integer) array[1]);
				}

				// 2 - Data Vencimento
				if(array[2] != null){
					conta.setDataVencimentoConta((Date) array[2]);
				}

				// 3 - Valor Agua
				if(array[3] != null){
					conta.setValorAgua((BigDecimal) array[3]);
				}

				// 4 - Valor Esgoto
				if(array[4] != null){
					conta.setValorEsgoto((BigDecimal) array[4]);
				}

				// 5 - Valor Debitos
				if(array[5] != null){
					conta.setDebitos((BigDecimal) array[5]);
				}

				// 6 - Valor Creditos
				if(array[6] != null){
					conta.setValorCreditos((BigDecimal) array[6]);
				}

				// 7 - debito Credito Situacao Atual
				DebitoCreditoSituacao debitoCreditoSituacaoAtual = null;
				if(array[7] != null){
					debitoCreditoSituacaoAtual = new DebitoCreditoSituacao();
					debitoCreditoSituacaoAtual.setDescricaoAbreviada((String) array[7]);
					debitoCreditoSituacaoAtual.setId(Util.obterInteger(array[10].toString()));

					conta.setDebitoCreditoSituacaoAtual(debitoCreditoSituacaoAtual);
				}

				// 8 - Motivo Revisão
				if(array[8] != null){
					ContaMotivoRevisao contaMotivoRevisao = new ContaMotivoRevisao();
					contaMotivoRevisao.setId((Integer) array[8]);
					conta.setContaMotivoRevisao(contaMotivoRevisao);
				}

				// 9 - Valor Imposto
				if(array[9] != null){
					conta.setValorImposto((BigDecimal) array[9]);
				}

				imoveisContas.add(conta);

			}// fim do while
		}

		return imoveisContas;
	}

	/**
	 * Consutlar as contas Historicos do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author Saulo Lima
	 * @date 10/08/2009
	 *       Novo atributo preenchido na colecao de ContaHistorico: valorImposto
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection<ContaHistorico> consultarContasHistoricosImovel(Integer idImovel) throws ControladorException{

		Collection colecaoContasHistoricos = null;
		Collection<ContaHistorico> imoveisContasHistoricos = null;

		try{
			colecaoContasHistoricos = this.repositorioImovel.consultarContasHistoricosImovel(idImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoContasHistoricos != null && !colecaoContasHistoricos.isEmpty()){

			imoveisContasHistoricos = new ArrayList();
			Iterator iteratorColecaoContasHistoricos = colecaoContasHistoricos.iterator();

			while(iteratorColecaoContasHistoricos.hasNext()){

				Object[] array = (Object[]) iteratorColecaoContasHistoricos.next();
				ContaHistorico contaHistorico = new ContaHistorico();

				// 0 - id Conta
				if(array[0] != null){
					contaHistorico.setId((Integer) array[0]);
				}

				// 1 - Referencia Contabil
				if(array[1] != null){
					contaHistorico.setAnoMesReferenciaContabil((Integer) array[1]);
				}

				// 2 - Data Vencimento
				if(array[2] != null){
					contaHistorico.setDataVencimentoConta((Date) array[2]);
				}

				// 3 - Valor Agua
				if(array[3] != null){
					contaHistorico.setValorAgua((BigDecimal) array[3]);
				}

				// 4 - Valor Esgoto
				if(array[4] != null){
					contaHistorico.setValorEsgoto((BigDecimal) array[4]);
				}

				// 5 - Valor Debitos
				if(array[5] != null){
					contaHistorico.setValorDebitos((BigDecimal) array[5]);
				}

				// 6 - Valor Creditos
				if(array[6] != null){
					contaHistorico.setValorCreditos((BigDecimal) array[6]);
				}

				// 7 - debito Credito Situacao Atual
				DebitoCreditoSituacao debitoCreditoSituacaoAtual = null;
				if(array[7] != null){
					debitoCreditoSituacaoAtual = new DebitoCreditoSituacao();
					debitoCreditoSituacaoAtual.setDescricaoAbreviada((String) array[7]);
					debitoCreditoSituacaoAtual.setId(Util.obterInteger(array[10].toString()));

					contaHistorico.setDebitoCreditoSituacaoAtual(debitoCreditoSituacaoAtual);
				}

				// 8 - Motivo Revisão
				if(array[8] != null){
					ContaMotivoRevisao contaMotivoRevisao = new ContaMotivoRevisao();
					contaMotivoRevisao.setId((Integer) array[8]);
					contaHistorico.setContaMotivoRevisao(contaMotivoRevisao);
				}

				// 9 - Valor Imposto
				if(array[9] != null){
					contaHistorico.setValorImposto((BigDecimal) array[9]);
				}

				imoveisContasHistoricos.add(contaHistorico);

			}// fim do while
		}

		return imoveisContasHistoricos;
	}

	/**
	 * Consultar os dados de parcelamentos do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 20/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Imovel consultarParcelamentosDebitosImovel(Integer idImovel) throws ControladorException{

		Imovel imovel = null;
		Collection colecaoImovel = null;

		try{

			colecaoImovel = this.repositorioImovel.consultarParcelamentosDebitosImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			imovel = new Imovel();

			imovel.setId(idImovel);

			Iterator imovelIterator = colecaoImovel.iterator();

			Object[] arrayImovel = (Object[]) imovelIterator.next();

			LigacaoAguaSituacao ligacaoAguaSituacao = null;
			// id ligacaoAguaSituacao - 0
			if(arrayImovel[0] != null){
				ligacaoAguaSituacao = new LigacaoAguaSituacao();

				ligacaoAguaSituacao.setDescricao((String) arrayImovel[0]);
				imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
			}

			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			// id ligacaoEsgotoSituacao - 1
			if(arrayImovel[1] != null){
				ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();

				ligacaoEsgotoSituacao.setDescricao((String) arrayImovel[1]);
				imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
			}

			// id numero de parcelamentos - 3
			if(arrayImovel[2] != null){
				imovel.setNumeroParcelamento((Short) arrayImovel[2]);
			}
			// id numero de reparcelamentos - 3
			if(arrayImovel[3] != null){
				imovel.setNumeroReparcelamento((Short) arrayImovel[3]);
			}
			// id numero de reparcelamentos consecutivos - 4
			if(arrayImovel[4] != null){
				imovel.setNumeroReparcelamentoConsecutivos((Short) arrayImovel[4]);
			}

		}

		return imovel;

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 27/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Cliente pesquisarClienteUsuarioImovel(Integer idImovel) throws ControladorException{

		Cliente cliente = null;

		try{

			cliente = this.repositorioImovel.pesquisarClienteUsuarioImovel(idImovel);

		}catch(ErroRepositorioException ex){

			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return cliente;
	}

	/**
	 * [UC0146] Manter Conta
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 11/08/2011
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Cliente pesquisarClienteResponsavelImovel(Integer idImovel) throws ControladorException{

		Cliente cliente = null;

		try{

			cliente = this.repositorioImovel.pesquisarClienteResponsavelImovel(idImovel);

		}catch(ErroRepositorioException ex){

			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return cliente;
	}

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização [SB0002] Atualizar
	 * Imóvel/Ligação de Água
	 * 
	 * @date 14/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelLigacaoAguaEsgoto(Integer idImovel, Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao,
					short indicadorLigacaoAtualizada, short indiacadorConsumoFixado, Integer consumoDefinido, short indicadorVolumeFixado)
					throws ControladorException{

		try{
			repositorioImovel.atualizarImovelLigacaoAguaEsgoto(idImovel, idLigacaoAguaSituacao, idLigacaoEsgotoSituacao);
			// 3.Caso a situação encontrada determine que deve ser alterada a
			// data da ligação
			if(indicadorLigacaoAtualizada == FiscalizacaoSituacao.INDICADOR_SIM){
				// 3.1.Caso tenha sido alterado a situação de ligação de água
				if(idLigacaoAguaSituacao != null && !idLigacaoAguaSituacao.equals("")){
					repositorioImovel.atualizarLigacaoAgua(idLigacaoAguaSituacao, consumoDefinido, indiacadorConsumoFixado);
				}
				// 3.2.Caso tenha sido alterado a situação de ligação de esgoto
				if(idLigacaoEsgotoSituacao != null && !idLigacaoEsgotoSituacao.equals("")){
					repositorioImovel.atualizarLigacaoEsgoto(idLigacaoEsgotoSituacao, consumoDefinido, indicadorVolumeFixado);
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Responsável pela inserção de um Imóvel Doação
	 * [UC0389] - Inserir Imovel Doacao
	 * 
	 * @author César Araújo, Pedro Alexandre
	 * @date 03/08/2006, 16/11/2006
	 * @param imovelDoacao
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirImovelDoacaoRetorno(ImovelDoacao imovelDoacao, Usuario usuarioLogado) throws ControladorException{

		Integer retorno = null;

		// ------------ CONTROLE DE ABRANGENCIA ----------------
		Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovelDoacao.getImovel());
		// ------------ CONTROLE DE ABRANGENCIA ----------------

		if(!getControladorAcesso().verificarAcessoAbrangencia(abrangencia)){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.acesso.negado.abrangencia");
		}else{
			retorno = (Integer) this.getControladorUtil().inserir(imovelDoacao);
		}
		return retorno;
	}

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * 
	 * @date 14/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */
	public Integer recuperarIdConsumoTarifa(Integer idImovel) throws ControladorException{

		Integer idConsumoTarifa = 0;
		if(idImovel != null && !idImovel.equals("")){
			try{
				idConsumoTarifa = repositorioImovel.pesquisarConsumoTarifa(idImovel);
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}
		}
		return idConsumoTarifa;
	}

	/**
	 * Filtrar o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 */
	public Collection pesquisarImovel(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometroHistInst, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelManterVinculo, boolean pesquisarImovelCondominio,
					Integer numeroPagina) throws ControladorException{

		Collection colecaoImovel = null;
		Collection colecaoDadosImoveis = null;
		try{
			colecaoDadosImoveis = repositorioImovel.pesquisarImovel(idImovel, idLocalidade, codigoSetorComercial, numeroQuadra,
							idHidrometroHistInst, lote, subLote, codigoCliente, idMunicipio, cep, idBairro, idLogradouro,
							pesquisarImovelManterVinculo, pesquisarImovelCondominio, numeroPagina);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		Imovel imovel = null;
		Cliente cliente = null;
		ClienteImovel clienteImovel = null;

		if(colecaoDadosImoveis != null && !colecaoDadosImoveis.isEmpty()){

			colecaoImovel = new ArrayList();

			Iterator iteratorColecaoDadosImoveis = colecaoDadosImoveis.iterator();

			while(iteratorColecaoDadosImoveis.hasNext()){

				Object[] array = (Object[]) iteratorColecaoDadosImoveis.next();
				cliente = new Cliente();
				imovel = new Imovel();
				clienteImovel = new ClienteImovel();

				LogradouroCep logradouroCep = null;
				if(array[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) array[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(array[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) array[19]);
						logradouro.setNome("" + array[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição logradouro tipo
					if(array[22] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricao("" + array[22]);
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição logradouro titulo
					if(array[23] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricao("" + array[23]);
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep objetoCep = null;
					if(array[10] != null){
						objetoCep = new Cep();
						objetoCep.setCepId((Integer) array[10]);
						objetoCep.setCodigo((Integer) array[16]);
					}

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(objetoCep);
				}

				imovel.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(array[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) array[21]);

					Bairro bairro = null;
					// nome bairro
					if(array[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) array[3]);
						bairro.setNome("" + array[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(array[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) array[5]);
						municipio.setNome("" + array[6]);

						// id da unidade federação
						if(array[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) array[7]);
							unidadeFederacao.setSigla("" + array[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);
				}

				imovel.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(array[24] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricao("" + array[24]);
					imovel.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(array[17] != null){
					imovel.setNumeroImovel("" + array[17]);
				}

				// complemento endereço
				if(array[18] != null){
					imovel.setComplementoEndereco("" + array[18]);
				}

				// id imovel
				if(array[25] != null){
					imovel.setId((Integer) array[25]);
				}

				if(pesquisarImovelManterVinculo){
					// nome cliente
					if(array[26] != null){
						cliente.setNome((String) array[26]);
					}

					// id lote
					if(array[27] != null){
						imovel.setLote(((Short) array[27]).shortValue());
					}

					// id subLote
					if(array[28] != null){
						imovel.setSubLote(((Short) array[28]).shortValue());
					}

					// id localidade
					if(array[29] != null){
						Localidade localidade = new Localidade();
						localidade.setId((Integer) array[29]);
						imovel.setLocalidade(localidade);
					}

					// codigo setor comercial
					if(array[30] != null){
						SetorComercial setorComercial = new SetorComercial();
						setorComercial.setCodigo(((Integer) array[30]).intValue());
						imovel.setSetorComercial(setorComercial);
					}

					// numeroQuadra
					if(array[31] != null){
						Quadra qudra = new Quadra();
						qudra.setNumeroQuadra(((Integer) array[31]).intValue());
						imovel.setQuadra(qudra);
					}

					if(array[32] != null){
						imovel.setUltimaAlteracao((Date) array[32]);
					}

					if(array[34] != null){
						Integer idRota = (Integer) array[34];

						Rota rota = new Rota();
						rota.setId(idRota);

						imovel.setRota(rota);
					}

					if(array[35] != null){
						imovel.setNumeroSegmento((Short) array[35]);
					}

				}else{
					// id lote
					if(array[26] != null){
						imovel.setLote(((Short) array[26]).shortValue());
					}

					// id subLote
					if(array[27] != null){
						imovel.setSubLote(((Short) array[27]).shortValue());
					}

					// id localidade
					if(array[28] != null){
						Localidade localidade = new Localidade();
						localidade.setId((Integer) array[28]);
						imovel.setLocalidade(localidade);
					}

					// codigo setor comercial
					if(array[29] != null){
						SetorComercial setorComercial = new SetorComercial();
						setorComercial.setCodigo(((Integer) array[29]).intValue());
						imovel.setSetorComercial(setorComercial);
					}

					// numeroQuadra
					if(array[30] != null){
						Quadra qudra = new Quadra();
						qudra.setNumeroQuadra(((Integer) array[30]).intValue());
						imovel.setQuadra(qudra);
					}

					if(array[31] != null){
						imovel.setUltimaAlteracao((Date) array[31]);
					}

					if(array[33] != null){
						Integer idRota = (Integer) array[33];

						Rota rota = new Rota();
						rota.setId(idRota);

						imovel.setRota(rota);
					}

					if(array[34] != null){
						imovel.setNumeroSegmento((Short) array[34]);
					}

				}

				clienteImovel.setImovel(imovel);
				clienteImovel.setCliente(cliente);

				colecaoImovel.add(clienteImovel);
			}
		}

		return colecaoImovel;
	}

	/**
	 * Pesquisa a quantidade de Imoveis
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 16/12/2008
	 *       Alteração no filtro de Num. de Hidrômetro do Pesquisar Imóvel.
	 * @author eduardo henrique
	 * @date 30/01/2009
	 *       Inclusao do Filtro de Numero do Imovel.
	 */
	public Integer pesquisarQuantidadeImovel(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometroHistInst, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelManterVinculo, boolean pesquisarImovelCondominio,
					String numeroHidrometroImovel, String numeroImovel) throws ControladorException{

		Object quantidade = null;
		Integer retorno = null;

		String pCriterioSelecaoVinculoRateio = null;

		try{

			pCriterioSelecaoVinculoRateio = ParametroMicromedicao.P_CRITERIO_SELECAO_VINCULO_RATEIO.executar();
		}catch(ControladorException e){

			throw new ControladorException("atencao.sistemaparametro_inexistente", null, "P_CRITERIO_SELECAO_VINCULO_RATEIO");
		}

		try{
			quantidade = repositorioImovel.pesquisarQuantidadeImovel(idImovel, idLocalidade, codigoSetorComercial, numeroQuadra,
							idHidrometroHistInst, lote, subLote, codigoCliente, idMunicipio, cep, idBairro, idLogradouro,
							pesquisarImovelManterVinculo, pesquisarImovelCondominio, numeroHidrometroImovel, numeroImovel,
							pCriterioSelecaoVinculoRateio);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(quantidade != null){
			retorno = (Integer) quantidade;

		}

		return retorno;
	}

	/**
	 * Usado pelo Pesquisar Imovel Retorno o Imovel, com o Nome do Cliente,
	 * Matricula e Endereço
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 */
	/*
	 * public Collection pesquisarImovelInscricao(String idImovel, String
	 * idLocalidade, String codigoSetorComercial, String numeroQuadra, String
	 * lote, String subLote, String codigoCliente, String idMunicipio, String
	 * cep, String idBairro, String idLogradouro, boolean
	 * pesquisarImovelCondominio, Integer numeroPagina) throws
	 * ControladorException {
	 * Collection colecaoClienteImovel = null; Collection colecaoDadosImoveis =
	 * null; try { colecaoDadosImoveis =
	 * repositorioImovel.pesquisarImovelInscricao( idImovel, idLocalidade,
	 * codigoSetorComercial, numeroQuadra, lote, subLote, codigoCliente,
	 * idMunicipio, cep, idBairro, idLogradouro, pesquisarImovelCondominio,
	 * numeroPagina); } catch (ErroRepositorioException ex) { throw new
	 * ControladorException("erro.sistema", ex); }
	 * Imovel imovel = null; ClienteImovel clienteImovel = null;
	 * if (colecaoDadosImoveis != null && !colecaoDadosImoveis.isEmpty()) {
	 * colecaoClienteImovel = new ArrayList();
	 * Iterator iteratorColecaoDadosImoveis = colecaoDadosImoveis .iterator();
	 * while (iteratorColecaoDadosImoveis.hasNext()) {
	 * Object[] array = (Object[]) iteratorColecaoDadosImoveis.next();
	 * imovel = new Imovel();
	 * LogradouroCep logradouroCep = null; if (array[20] != null) {
	 * logradouroCep = new LogradouroCep(); logradouroCep.setId((Integer)
	 * array[20]); // id do Logradouro Logradouro logradouro = null; if
	 * (array[19] != null) { logradouro = new Logradouro();
	 * logradouro.setId((Integer) array[19]); logradouro.setNome("" + array[0]); }
	 * LogradouroTipo logradouroTipo = null; // Descrição logradouro tipo if
	 * (array[22] != null) { logradouroTipo = new LogradouroTipo();
	 * logradouroTipo.setDescricao("" + array[22]);
	 * logradouro.setLogradouroTipo(logradouroTipo); } LogradouroTitulo
	 * logradouroTitulo = null; // Descrição logradouro titulo if (array[23] !=
	 * null) { logradouroTitulo = new LogradouroTitulo();
	 * logradouroTitulo.setDescricao("" + array[23]);
	 * logradouro.setLogradouroTitulo(logradouroTitulo); } // id do CEP Cep
	 * objetoCep = null; if (array[10] != null) { objetoCep = new Cep();
	 * objetoCep.setCepId((Integer) array[10]); objetoCep.setCodigo((Integer)
	 * array[16]); }
	 * logradouroCep.setLogradouro(logradouro); logradouroCep.setCep(objetoCep); }
	 * imovel.setLogradouroCep(logradouroCep);
	 * LogradouroBairro logradouroBairro = null; if (array[21] != null) {
	 * logradouroBairro = new LogradouroBairro();
	 * logradouroBairro.setId((Integer) array[21]);
	 * Bairro bairro = null; // nome bairro if (array[3] != null) { bairro = new
	 * Bairro(); bairro.setId((Integer) array[3]); bairro.setNome("" +
	 * array[4]); }
	 * Municipio municipio = null; // nome municipio if (array[5] != null) {
	 * municipio = new Municipio(); municipio.setId((Integer) array[5]);
	 * municipio.setNome("" + array[6]); // id da unidade federação if (array[7] !=
	 * null) { UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
	 * unidadeFederacao.setId((Integer) array[7]); unidadeFederacao.setSigla("" +
	 * array[8]); municipio.setUnidadeFederacao(unidadeFederacao); }
	 * bairro.setMunicipio(municipio); }
	 * logradouroBairro.setBairro(bairro); }
	 * imovel.setLogradouroBairro(logradouroBairro); // descricao de endereço
	 * referência if (array[24] != null) { EnderecoReferencia enderecoReferencia =
	 * new EnderecoReferencia(); enderecoReferencia.setDescricao("" +
	 * array[24]); imovel.setEnderecoReferencia(enderecoReferencia); } // numero
	 * imovel if (array[17] != null) { imovel.setNumeroImovel("" + array[17]); } //
	 * complemento endereço if (array[18] != null) {
	 * imovel.setComplementoEndereco("" + array[18]); } // id imovel if
	 * (array[25] != null) { imovel.setId((Integer) array[25]); } // id lote if
	 * (array[26] != null) { imovel.setLote(((Short) array[26]).shortValue()); } //
	 * id subLote if (array[27] != null) { imovel.setSubLote(((Short)
	 * array[27]).shortValue()); } // id localidade if (array[28] != null) {
	 * Localidade localidade = new Localidade(); localidade.setId((Integer)
	 * array[28]); imovel.setLocalidade(localidade); } // id imovel if
	 * (array[29] != null) { SetorComercial setorComercial = new
	 * SetorComercial(); setorComercial.setCodigo(((Integer)
	 * array[29]).intValue()); imovel.setSetorComercial(setorComercial); } // id
	 * numeroQuadra if (array[30] != null) { Quadra qudra = new Quadra();
	 * qudra.setNumeroQuadra(((Integer) array[30]).intValue());
	 * imovel.setQuadra(qudra); }
	 * Cliente cliente = new Cliente(); // nome cliente if (array[31] != null) {
	 * cliente.setNome((String) array[31]); }
	 * clienteImovel = new ClienteImovel(); clienteImovel.setImovel(imovel);
	 * clienteImovel.setCliente(cliente);
	 * colecaoClienteImovel.add(clienteImovel); } }
	 * return colecaoClienteImovel; }
	 */

	/**
	 * Usado pelo Pesquisar Imovel Retorno o Imovel, com o Nome do Cliente,
	 * Matricula e Endereço
	 * 
	 * @author Rafael Santos, Raphael Rossiter
	 * @date 27/11/2006
	 * @param numeroImovel
	 *            TODO
	 * @author eduardo henrique
	 * @date 30/01/2009
	 *       Correção no método para desativação definitiva da Classe filtro e
	 *       inclusao do numero do imovel na consulta.
	 */
	public Collection pesquisarImovelInscricao(String idImovel, String idLocalidade, String codigoSetorComercial, String numeroQuadra,
					String idHidrometro, String lote, String subLote, String codigoCliente, String idMunicipio, String cep,
					String idBairro, String idLogradouro, boolean pesquisarImovelCondominio, String numeroImovel, Integer numeroPagina)
					throws ControladorException{

		Collection colecaoClienteImovel = null;
		Collection colecaoDadosImoveis = null;
		try{
			colecaoDadosImoveis = repositorioImovel.pesquisarImovelInscricaoNew(idImovel, idLocalidade, codigoSetorComercial, numeroQuadra,
							idHidrometro, lote, subLote, codigoCliente, idMunicipio, cep, idBairro, idLogradouro,
							pesquisarImovelCondominio, numeroImovel, numeroPagina);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		Imovel imovel = null;
		ClienteImovel clienteImovel = null;

		if(colecaoDadosImoveis != null && !colecaoDadosImoveis.isEmpty()){

			colecaoClienteImovel = new ArrayList();

			Iterator iteratorColecaoDadosImoveis = colecaoDadosImoveis.iterator();

			while(iteratorColecaoDadosImoveis.hasNext()){

				Object[] array = (Object[]) iteratorColecaoDadosImoveis.next();

				imovel = new Imovel();

				LogradouroCep logradouroCep = null;
				if(array[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) array[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(array[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) array[19]);
						logradouro.setNome("" + array[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição logradouro tipo
					if(array[22] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricao("" + array[22]);
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição logradouro titulo
					if(array[23] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricao("" + array[23]);
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep objetoCep = null;
					if(array[10] != null){
						objetoCep = new Cep();
						objetoCep.setCepId((Integer) array[10]);
						objetoCep.setCodigo((Integer) array[16]);
					}

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(objetoCep);
				}

				imovel.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(array[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) array[21]);

					Bairro bairro = null;
					// nome bairro
					if(array[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) array[3]);
						bairro.setNome("" + array[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(array[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) array[5]);
						municipio.setNome("" + array[6]);

						// id da unidade federação
						if(array[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) array[7]);
							unidadeFederacao.setSigla("" + array[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);
				}

				imovel.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(array[24] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricao("" + array[24]);
					imovel.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(array[17] != null){
					imovel.setNumeroImovel("" + array[17]);
				}

				// complemento endereço
				if(array[18] != null){
					imovel.setComplementoEndereco("" + array[18]);
				}

				// id imovel
				if(array[25] != null){
					imovel.setId((Integer) array[25]);
				}

				// id lote
				if(array[26] != null){
					imovel.setLote(((Short) array[26]).shortValue());
				}

				// id subLote
				if(array[27] != null){
					imovel.setSubLote(((Short) array[27]).shortValue());
				}

				// id localidade
				if(array[28] != null){
					Localidade localidade = new Localidade();
					localidade.setId((Integer) array[28]);
					imovel.setLocalidade(localidade);
				}

				// id imovel
				if(array[29] != null){
					SetorComercial setorComercial = new SetorComercial();
					setorComercial.setCodigo(((Integer) array[29]).intValue());
					imovel.setSetorComercial(setorComercial);
				}

				// id numeroQuadra
				if(array[30] != null){
					Quadra qudra = new Quadra();
					qudra.setNumeroQuadra(((Integer) array[30]).intValue());
					imovel.setQuadra(qudra);
				}

				Cliente cliente = new Cliente();
				// nome cliente
				if(array[31] != null){
					cliente.setNome((String) array[31]);
				}

				clienteImovel = new ClienteImovel();
				clienteImovel.setImovel(imovel);
				clienteImovel.setCliente(cliente);

				colecaoClienteImovel.add(clienteImovel);
			}
		}

		return colecaoClienteImovel;
	}

	/**
	 * [UC0491] Informar Ocorrencia de Cadastro e/ou Anormalidade de Elo
	 * 
	 * @author Tiago Moreno
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 21/07/2008 adição dos atributos de funcionário em OcorrenciaCadastro e AnormalidadeElo
	 * @param idImovel
	 * @param idOcorrenciaCadastro
	 * @param idAnormalidadeElo
	 * @param dataOcorrenciaCadastro
	 * @param dataAnormalidadeElo
	 * @param uploadPictureCadastro
	 * @param uploadPictureAnormalidade
	 * @param idFuncionarioOcorrencia
	 * @param idFuncionarioAnormalidade
	 * @return
	 * @throws ControladorException
	 */
	public void informarOcorrenciaCadastroAnormalidadeElo(String idImovel, String idOcorrenciaCadastro, String idAnormalidadeElo,
					String dataOcorrenciaCadastro, String dataAnormalidadeElo, byte[] uploadPictureCadastro,
					byte[] uploadPictureAnormalidade, String idFuncionarioOcorrencia, String idFuncionarioAnormalidade,
					Usuario usuarioLogado) throws ControladorException{

		// validando se o imovel existe na base
		if(this.verificarExistenciaImovel(Integer.valueOf(idImovel)) == 0){
			throw new ControladorException("atencao.imovel.inexistente");
		}

		// validando se o imovel foi excluído
		Imovel imovelDigitado = this.pesquisarImovelDigitado(Integer.valueOf(idImovel));
		if(imovelDigitado.getIndicadorExclusao() != null && imovelDigitado.getIndicadorExclusao().equals("1")){
			throw new ControladorException("atencao.imovel.excluido");
		}

		// validando se foi informado ocorrencia de cadastro ou anormalidade de
		// elo
		if((idOcorrenciaCadastro == null || idOcorrenciaCadastro.equalsIgnoreCase(""))
						&& (idAnormalidadeElo == null || idAnormalidadeElo.equalsIgnoreCase(""))){

			throw new ControladorException("atencao.informe_ocorrencia_cadastro_anormalidade_elo");
		}

		// Valida se foi setado o funcionario da ocorrencia caso a mesma esteja marcada
		if((idOcorrenciaCadastro != null && !idOcorrenciaCadastro.equals(""))
						&& (idFuncionarioOcorrencia == null || idFuncionarioOcorrencia.equals(""))){

			throw new ControladorException("atencao.informe_funcionario_ocorrencia");

		}

		// Valida se foi setado o funcionario da anormalidade caso a mesma esteja marcada
		if((idAnormalidadeElo != null && !idAnormalidadeElo.equals(""))
						&& (idFuncionarioAnormalidade == null || idFuncionarioAnormalidade.equals(""))){

			throw new ControladorException("atencao.informe_funcionario_anormalidade");

		}

		if(idFuncionarioOcorrencia != null && !idFuncionarioOcorrencia.trim().equalsIgnoreCase("")){
			// [FS0003]
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, idFuncionarioOcorrencia));

			Collection colecaoFuncionarios = getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionarios == null || colecaoFuncionarios.isEmpty()){
				// // levanta a exceção para a próxima camada
				throw new ControladorException("atencao.ocorrencia_funcionario_inexistente", null, "" + idFuncionarioOcorrencia);
			}
		}

		if(idFuncionarioAnormalidade != null && !idFuncionarioAnormalidade.trim().equalsIgnoreCase("")){
			// [FS0003]
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, idFuncionarioAnormalidade));

			Collection colecaoFuncionarios = getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionarios == null || colecaoFuncionarios.isEmpty()){
				// // levanta a exceção para a próxima camada
				throw new ControladorException("atencao.anormalidade_funcionario_inexistente", null, "" + idFuncionarioAnormalidade);
			}
		}

		// instanciando o Objeto ImovelCadastroOcorrencia
		ImovelCadastroOcorrencia imovelCadastroOcorrencia = new ImovelCadastroOcorrencia();

		// instanciando o Objeto ImovelEloAnormalidade
		ImovelEloAnormalidade imovelEloAnormalidade = new ImovelEloAnormalidade();

		// ------------ REGISTRAR TRANSAÇÃO ROTA----------------------------
		RegistradorOperacao registradorOperacaoRota = new RegistradorOperacao(Operacao.OPERACAO_OCORRENCIA_ANORMALIDADE_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_OCORRENCIA_ANORMALIDADE_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		imovelCadastroOcorrencia.setOperacaoEfetuada(operacaoEfetuada);
		imovelCadastroOcorrencia.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoRota.registrarOperacao(imovelCadastroOcorrencia);

		imovelEloAnormalidade.setOperacaoEfetuada(operacaoEfetuada);
		imovelEloAnormalidade.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacaoRota.registrarOperacao(imovelEloAnormalidade);
		// ------------ REGISTRAR TRANSAÇÃO ROTA----------------------------

		// verifica se foi informado Ocorrencia de cadastro
		if(idOcorrenciaCadastro != null && !idOcorrenciaCadastro.equalsIgnoreCase("")){

			// Instancia o Objeto Imovel e setando o valor do Id preenchido
			Imovel imovel = new Imovel();
			imovel.setId(Integer.valueOf(idImovel));

			// setando o valor do Imovel do Objeto Principal
			imovelCadastroOcorrencia.setImovel(imovel);

			// Instancia o Objeto CadastroOcorrencia e setando o valor do id
			// escolhido
			CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
			cadastroOcorrencia.setId(Integer.valueOf(idOcorrenciaCadastro));

			// setando o valor de Cadastro Ocorrencia do Objeto Principal
			imovelCadastroOcorrencia.setCadastroOcorrencia(cadastroOcorrencia);

			// setando a data da ocorrencia do cadastro
			if(dataOcorrenciaCadastro != null && !dataOcorrenciaCadastro.equalsIgnoreCase("")){

				Calendar data = Calendar.getInstance();

				data.setTime(Util.converteStringParaDate(dataOcorrenciaCadastro));

				if(data.get(Calendar.YEAR) < 1985){
					throw new ControladorException("atencao.data.ocorrencia.nao.inferior.1985");
				}else{
					if(data.getTime().after(new Date())){
						throw new ControladorException("atencao.data_inicio_ocorrencia");
					}else{
						imovelCadastroOcorrencia.setDataOcorrencia(Util.converteStringParaDate(dataOcorrenciaCadastro));
					}
				}
			}

			// setando a foto da ocorrencia do cadastro
			if(uploadPictureCadastro != null){
				imovelCadastroOcorrencia.setFotoOcorrencia(uploadPictureCadastro);
			}

			// setando funcionario
			if(idFuncionarioOcorrencia != null && !idFuncionarioOcorrencia.trim().equalsIgnoreCase("")){
				Funcionario funcionarioOcorrencia = new Funcionario();
				funcionarioOcorrencia.setId(Integer.valueOf(idFuncionarioOcorrencia));

				imovelCadastroOcorrencia.setFuncionario(funcionarioOcorrencia);
			}

			// setando a data/hora no campo Ultima Alteracao
			imovelCadastroOcorrencia.setUltimaAlteracao(new Date());

			// inserindo no banco
			getControladorUtil().inserir(imovelCadastroOcorrencia);
		}

		// verifica se foi informado Anormalidade de Elo
		if(idAnormalidadeElo != null && !idAnormalidadeElo.equalsIgnoreCase("")){

			// Instancia o Objeto Imovel e setando o valor do Id preenchido
			Imovel imovel = new Imovel();
			imovel.setId(Integer.valueOf(idImovel));

			// setando o valor do Imovel do Objeto Principal
			imovelEloAnormalidade.setImovel(imovel);

			// Instancia o Objeto CadastroOcorrencia e setando o valor do id
			// escolhido
			EloAnormalidade eloAnormalidade = new EloAnormalidade();
			eloAnormalidade.setId(Integer.valueOf(idAnormalidadeElo));

			// setando o valor de Cadastro Ocorrencia do Objeto Principal
			imovelEloAnormalidade.setEloAnormalidade(eloAnormalidade);

			// setando a data da ocorrencia do cadastro
			if(dataAnormalidadeElo != null && !dataAnormalidadeElo.equalsIgnoreCase("")){

				Calendar data = Calendar.getInstance();

				data.setTime(Util.converteStringParaDate(dataAnormalidadeElo));

				if(data.get(Calendar.YEAR) < 1985){
					throw new ControladorException("atencao.data.anormalidade.nao.inferior.1985");
				}else{
					if(data.getTime().after(new Date())){
						throw new ControladorException("atencao.data_inicio_anormalidade");
					}else{
						imovelEloAnormalidade.setDataAnormalidade(Util.converteStringParaDate(dataAnormalidadeElo));
					}
				}
			}

			// setando a foto da ocorrencia do cadastro
			if(uploadPictureAnormalidade != null){
				imovelEloAnormalidade.setFotoAnormalidade(uploadPictureAnormalidade);
			}

			// setando funcionario
			if(idFuncionarioAnormalidade != null && !idFuncionarioAnormalidade.trim().equalsIgnoreCase("")){
				Funcionario funcionarioAnormalidade = new Funcionario();
				funcionarioAnormalidade.setId(Integer.valueOf(idFuncionarioAnormalidade));

				imovelEloAnormalidade.setFuncionario(funcionarioAnormalidade);
			}

			// setando a data de ultima alteracao
			imovelEloAnormalidade.setUltimaAlteracao(new Date());

			// inserindo no banco
			getControladorUtil().inserir(imovelEloAnormalidade);
		}
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligação de agua
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdLigacaoAguaSituacao(Integer idImovel) throws ControladorException{

		Integer retorno = null;

		try{
			retorno = repositorioImovel.pesquisaridLigacaoAguaSituacao(idImovel);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligação de esgoto
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdLigacaoEsgotoSituacao(Integer idImovel) throws ControladorException{

		Integer retorno = null;

		try{
			retorno = repositorioImovel.pesquisaridLigacaoEsgotoSituacao(idImovel);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligacao de agua
	 * 
	 * @author Sávio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarTarifaImovel(Integer idImovel) throws ControladorException{

		Integer retorno = null;

		try{
			retorno = repositorioImovel.pesquisaridLigacaoEsgotoSituacao(idImovel);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0490] Informar Situação de Cobrança
	 * 
	 * @author Tiago Moreno
	 * @date 09/12/2006
	 * @param imovel
	 * @param situacaoCobranca
	 * @param cliente
	 * @param dataImplantação
	 * @param anoMesInicio
	 * @param anoMesFim
	 * @return
	 * @throws ControladorException
	 */
	public void inserirImovelSitucaoCobranca(Imovel imovel, CobrancaSituacao cobrancaSituacao, Cliente cliente, Cliente clienteEscritorio,
					Cliente clienteAdvogado, Date dataImplantacao, Integer anoMesInicio, Integer anoMesFim, Usuario usuarioLogado)
					throws ControladorException{

		ImovelCobrancaSituacao imovelCobrancaSituacao = new ImovelCobrancaSituacao();

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		Integer existe = this.verificarExistenciaImovel(Integer.valueOf(imovel.getId()));

		if(existe != null && existe != 0){
			imovelCobrancaSituacao.setImovel(imovel);
		}else{
			throw new ControladorException("atencao.imovel.inexistente");
		}

		if(cliente.getId() != null){
			Cliente cliente2 = getControladorCliente().pesquisarClienteDigitado(Integer.valueOf(cliente.getId()));
			if(cliente2 != null){
				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, cliente.getId().toString()));
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId().toString()));
				Collection colecaoClienteImovel = getControladorUtil().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				if(colecaoClienteImovel == null || colecaoClienteImovel.isEmpty()){
					throw new ControladorException("atencao.cliente_informado_nao_corresponde_imovel");
				}else{
					imovelCobrancaSituacao.setCliente(cliente);
				}
			}else{
				throw new ControladorException("atencao.cliente.inexistente");
			}
		}

		// TESTANDO O CLIENTE ESCRITORIO DE ADVOCACIA (Kassia Albuquerque)

		if(clienteEscritorio != null && !clienteEscritorio.equals("")){

			if(clienteEscritorio.getClienteTipo() == null
							|| !clienteEscritorio.getClienteTipo().getIndicadorPessoaFisicaJuridica()
											.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){
				throw new ControladorException("atencao.escritorio_pessoa_juridica");
			}else if(clienteEscritorio.getRamoAtividade() == null
							|| !clienteEscritorio.getRamoAtividade().getId().equals(RamoAtividade.ASSESSORIA_JURIDICA)){
				throw new ControladorException("atencao.ramo_atividade_juridica");
			}else{
				imovelCobrancaSituacao.setEscritorio(clienteEscritorio);
			}
		}

		// TESTANDO O CLIENTE ADVOGADO (Kassia Albuquerque)

		if(clienteAdvogado != null && !clienteAdvogado.equals("")){

			if(clienteAdvogado.getClienteTipo() == null
							|| !clienteAdvogado.getClienteTipo().getIndicadorPessoaFisicaJuridica()
											.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
				throw new ControladorException("atencao.advogado_pessoa_fisica");
			}else if(clienteAdvogado.getProfissao() == null || !clienteAdvogado.getProfissao().getId().equals(Profissao.ADVOGADO)){
				throw new ControladorException("atencao.profissao_advogado");
			}else{
				imovelCobrancaSituacao.setAdvogado(clienteAdvogado);
			}
		}

		imovelCobrancaSituacao.setCobrancaSituacao(cobrancaSituacao);
		imovelCobrancaSituacao.setDataImplantacaoCobranca(dataImplantacao);

		if(cobrancaSituacao.getContaMotivoRevisao() != null){

			imovelCobrancaSituacao.setContaMotivoRevisao(cobrancaSituacao.getContaMotivoRevisao());
		}

		if(anoMesFim != null){
			if(sistemaParametro.getAnoMesFaturamento().intValue() < anoMesFim){
				throw new ControladorException("atencao.ano_mes_informado_maior_que.ano.mes.faturamento", null,
								Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesFaturamento().intValue()));
			}else{
				imovelCobrancaSituacao.setAnoMesReferenciaFinal(anoMesFim);
			}
		}
		if(anoMesInicio != null){
			imovelCobrancaSituacao.setAnoMesReferenciaInicio(anoMesInicio);
		}

		imovelCobrancaSituacao.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_COBRANCA_SITUACAO_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		registradorOperacao.registrarOperacao(imovelCobrancaSituacao);

		getControladorUtil().inserir(imovelCobrancaSituacao);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		Imovel imovelAtualiza = this.pesquisarImovelDigitado(imovel.getId());

		if(imovelAtualiza.getIndicadorExclusao().equals(ConstantesSistema.SIM)){

			throw new ControladorException("atencao.imovel.excluido");
		}

		FiltroCobrancaSituacao filtroCobrancaSituacao = new FiltroCobrancaSituacao();
		filtroCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaSituacao.ID, cobrancaSituacao.getId()));
		filtroCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("contaMotivoRevisao");
		Collection cCS = (Collection) getControladorUtil().pesquisar(filtroCobrancaSituacao, CobrancaSituacao.class.getName());
		CobrancaSituacao cS = (CobrancaSituacao) cCS.iterator().next();
		if(cS.getContaMotivoRevisao() != null){
			Collection conta = (Collection) getControladorFaturamento().obterContasImovelIntervalo(imovel.getId(),
							DebitoCreditoSituacao.NORMAL, DebitoCreditoSituacao.INCLUIDA, DebitoCreditoSituacao.RETIFICADA,
							imovelCobrancaSituacao.getAnoMesReferenciaInicio(), imovelCobrancaSituacao.getAnoMesReferenciaFinal(), null);

			if(conta != null && !conta.isEmpty()){
				getControladorFaturamento().colocarRevisaoConta(conta, null, cS.getContaMotivoRevisao(), usuarioLogado);
			}
		}
	}

	/**
	 * [UC0490] Informar Situação de Cobrança
	 * 
	 * @author Tiago Moreno
	 * @date 09/12/2006
	 * @param imovel
	 * @param situacaoCobranca
	 * @param cliente
	 * @param dataImplantação
	 * @param anoMesInicio
	 * @param anoMesFim
	 * @return
	 * @throws ControladorException
	 */

	public void retirarImovelSitucaoCobranca(String[] idRemocao, Usuario usuarioLogado) throws ControladorException{

		FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
		for(int i = 0; i < idRemocao.length; i++){
			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID, idRemocao[i],
							ConectorOr.CONECTOR_OR));
		}
		filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelCobrancaSituacao.IMOVEL);
		filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelCobrancaSituacao.COBRANCA_SITUACAO);

		Collection colecaoICS = getControladorUtil().pesquisar(filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class.getName());

		Imovel imovel = null;

		if(colecaoICS != null && !colecaoICS.isEmpty()){

			for(Iterator iter = colecaoICS.iterator(); iter.hasNext();){
				ImovelCobrancaSituacao imovelCobrancaSituacao = (ImovelCobrancaSituacao) iter.next();

				if(imovel == null){

					Integer existe = this.verificarExistenciaImovel(imovelCobrancaSituacao.getImovel().getId());

					if(existe == null || existe == 0){

						throw new ControladorException("atencao.imovel.inexistente");
					}
				}

				imovelCobrancaSituacao.setDataRetiradaCobranca(new Date());
				imovelCobrancaSituacao.setUltimaAlteracao(new Date());

				imovel = this.pesquisarImovelDigitado(imovelCobrancaSituacao.getImovel().getId());

				CobrancaSituacao cobrancaSituacao = imovelCobrancaSituacao.getCobrancaSituacao();

				FiltroCobrancaSituacao filtroCobrancaSituacao = new FiltroCobrancaSituacao();
				filtroCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaSituacao.ID, cobrancaSituacao.getId()));
				filtroCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaSituacao.CONTA_MOTIVO_REVISAO);
				Collection colecaoCobrancaSituacao = (Collection) getControladorUtil().pesquisar(filtroCobrancaSituacao,
								CobrancaSituacao.class.getName());

				cobrancaSituacao = (CobrancaSituacao) Util.retonarObjetoDeColecao(colecaoCobrancaSituacao);

				if(cobrancaSituacao.getContaMotivoRevisao() != null){

					if(imovelCobrancaSituacao.getAnoMesReferenciaInicio() != null
									&& imovelCobrancaSituacao.getAnoMesReferenciaFinal() != null){

						Collection colecaoContasEmRevisao = (Collection) getControladorFaturamento()
										.obterContasImovelIntervalo(imovel.getId(), DebitoCreditoSituacao.NORMAL,
														DebitoCreditoSituacao.INCLUIDA, DebitoCreditoSituacao.RETIFICADA,
										imovelCobrancaSituacao.getAnoMesReferenciaInicio(),
														imovelCobrancaSituacao.getAnoMesReferenciaFinal(),
														cobrancaSituacao.getContaMotivoRevisao().getId());

						if(colecaoContasEmRevisao != null && !colecaoContasEmRevisao.isEmpty()){

							getControladorFaturamento().retirarRevisaoConta(colecaoContasEmRevisao, null, usuarioLogado);
						}
					}
				}

				RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_COBRANCA_SITUACAO_RETIRAR,
								imovelCobrancaSituacao.getId(), imovelCobrancaSituacao.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
												UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				registradorOperacao.registrarOperacao(imovelCobrancaSituacao);

				getControladorUtil().atualizar(imovelCobrancaSituacao);
			}
		}

	}

	/**
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Leonardo Vieira
	 * @created 12/12/2006
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisClientesRelacao(Cliente cliente, ClienteRelacaoTipo relacaoClienteImovel, Integer numeroInicial,
					Integer idSetorComercial) throws ControladorException{

		Collection retorno = null;

		try{
			retorno = repositorioImovel.pesquisarImoveisClientesRelacao(cliente, relacaoClienteImovel, numeroInicial, idSetorComercial);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;

	}

	public Integer pesquisarExistenciaImovelSubCategoria(Integer idImovel, Integer idCategoria) throws ControladorException{

		Integer retorno = null;

		try{

			retorno = repositorioImovel.pesquisarExistenciaImovelSubCategoria(idImovel, idCategoria);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;

	}

	public Collection pesquisarImoveisPorRota(Integer idRota) throws ControladorException{

		Collection retorno = null;

		try{
			retorno = repositorioImovel.pesquisarImoveisPorRota(idRota);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	public Collection pesquisarImoveisPorRotaComPaginacao(Integer idRota, int numeroInicial) throws ControladorException{

		Collection retorno = null;

		try{
			retorno = repositorioImovel.pesquisarImoveisPorRotaComPaginacao(idRota, numeroInicial);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * Pesquisa os imóveis com determinada tarifa de consumo
	 * [UC0378] Associar Tarifa de Consumo a Imóveis
	 * 
	 * @author Rômulo Aurelio
	 * @created 19/12/2006
	 * @param idLocalidadeInicial
	 *            ,
	 *            idLocalidadeFinal, codigoSetorComercialInicial,
	 *            codigoSetorComercialFinal, quadraInicial, quadraFinal,
	 *            loteInicial, loteFinal, subLoteInicial, subLoteFinal,
	 *            idTarifaAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImoveisTarifaConsumo(String idLocalidadeInicial, String idLocalidadeFinal,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String quadraInicial, String quadraFinal,
					String loteInicial, String loteFinal, String subLoteInicial, String subLoteFinal, String idTarifaAnterior,
					String idsCategorias, String idsSubcategorias) throws ControladorException{

		Collection colecaoDadosImoveis = null;

		try{
			colecaoDadosImoveis = repositorioImovel.pesquisarImoveisTarifaConsumo(idLocalidadeInicial, idLocalidadeFinal,
							codigoSetorComercialInicial, codigoSetorComercialFinal, quadraInicial, quadraFinal, loteInicial, loteFinal,
							subLoteInicial, subLoteFinal, idTarifaAnterior, idsCategorias, idsSubcategorias);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		// Collection colecaoDadosImoveis = retorno;

		Imovel imovel = null;

		Collection colecaoImoveis = null;

		if(colecaoDadosImoveis != null && !colecaoDadosImoveis.isEmpty()){

			colecaoImoveis = new ArrayList();

			Iterator iteratorColecaoDadosImoveis = colecaoDadosImoveis.iterator();

			while(iteratorColecaoDadosImoveis.hasNext()){

				Integer idImovel = (Integer) iteratorColecaoDadosImoveis.next();

				imovel = new Imovel();

				if(idImovel != null){
					imovel.setId(idImovel);
				}

				colecaoImoveis.add(imovel);
			}
		}else{
			throw new ControladorException("atencao.imoveis_parametros_nao_existentes", null);

		}
		return colecaoDadosImoveis;

	}

	/**
	 * Atualiza a tarifa de consumo de um ou mais imoveis
	 * [UC0378] Associar Tarifa de Consumo a Imóveis
	 * 
	 * @author Rômulo Aurelio
	 * @created 19/12/2006
	 * @author Hugo Lima
	 * @date 06/03/2012
	 *       Alteracao na consulta para atualizacao por mais dois novos campos (tarifaEspecial,
	 *       dataValidadeTarifaEspecial)
	 * @param matricula
	 * @param tarifaAtual
	 * @param tarifaEspecial
	 * @param dataValidadeTarifaEspecial
	 * @param colecaoImoveis
	 * @throws ControladorException
	 */

	public void atualizarImoveisTarifaConsumo(String matricula, String tarifaAtual, String tarifaEspecial, Date dataValidadeTarifaEspecial,
					Collection colecaoImoveis) throws ControladorException{

		if(colecaoImoveis == null || !colecaoImoveis.isEmpty()){

			try{
				repositorioImovel.atualizarImoveisTarifaConsumo(matricula, tarifaAtual, tarifaEspecial, dataValidadeTarifaEspecial,
								colecaoImoveis);
			}catch(ErroRepositorioException ex){
				throw new ControladorException("erro.sistema", ex);
			}

		}

	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Atualiza o perfil do imóvel para o perfil normal
	 * 
	 * @date 04/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */
	public void atualizarImovelPerfilNormal(Integer idImovel, boolean manter) throws ControladorException{

		try{
			repositorioImovel.atualizarImovelPerfilNormal(idImovel, manter);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0490] - Informar Situação de Cobrança
	 * Pesquisa o imóvel com a situação da ligação de água e a de esgoto
	 * 
	 * @date 13/01/2007
	 * @author Rafael Corrêa
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelComSituacaoAguaEsgoto(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelComSituacaoAguaEsgoto(idImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0069] - Manter Dados da Tarifa Social
	 * [FS0006] - Verificar número de IPTU
	 * Verifica se já existe outro imóvel ou economia com o mesmo número de IPTU
	 * no mesmo município
	 * 
	 * @date 18/01/2007
	 * @author Rafael Corrêa
	 * @throws ControladorException
	 */
	public Integer verificarNumeroIptu(BigDecimal numeroIptu, Integer idImovel, Integer idImovelEconomia, Integer idMunicipio)
					throws ControladorException{

		try{
			return this.repositorioImovel.verificarNumeroIptu(numeroIptu, idImovel, idImovelEconomia, idMunicipio);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0069] - Manter Dados da Tarifa Social
	 * [FS0007] - Verificar número de contrato da companhia de energia elétrica
	 * Verifica se já existe outro imóvel ou economia com o mesmo número de
	 * contrato da companhia elétrica
	 * 
	 * @date 18/01/2007
	 * @author Rafael Corrêa
	 * @throws ControladorException
	 */
	public Integer verificarNumeroCompanhiaEletrica(Long numeroCompanhiaEletrica, Integer idImovel, Integer idImovelEconomia)
					throws ControladorException{

		try{
			return this.repositorioImovel.verificarNumeroCompanhiaEletrica(numeroCompanhiaEletrica, idImovel, idImovelEconomia);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Obtém o quantidade de economias da subCategoria por imovel
	 * 
	 * @param idImovel
	 *            O identificador do imóvel
	 * @return Coleção de SubCategorias
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection<Subcategoria> obterQuantidadeEconomiasSubCategoria(Integer idImovel) throws ControladorException{

		// Criação das coleções
		Collection<Subcategoria> colecaoSubcategoria = new ArrayList();
		Collection colecaoImovelSubCategoriaArray = null;

		try{
			colecaoImovelSubCategoriaArray = repositorioImovel.obterQuantidadeEconomiasSubCategoria(idImovel);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoImovelSubCategoriaArray != null && !colecaoImovelSubCategoriaArray.isEmpty()){

			Iterator colecaoImovelSubCategoriaArrayIterator = colecaoImovelSubCategoriaArray.iterator();

			while(colecaoImovelSubCategoriaArrayIterator.hasNext()){

				// Obtém o imóvel subcategoria
				Object[] imovelSubcategoriaArray = (Object[]) colecaoImovelSubCategoriaArrayIterator.next();

				// Cria os objetos Subcategoria
				Subcategoria subCategoria = new Subcategoria();

				// Seta a subCategoria
				subCategoria.setId((Integer) imovelSubcategoriaArray[0]);

				// Seta o codigo
				subCategoria.setCodigo((Integer) imovelSubcategoriaArray[1]);

				// Seta a descrição
				subCategoria.setDescricao((String) imovelSubcategoriaArray[2]);

				// Seta a quantidade de economias por subCategoria
				subCategoria.setQuantidadeEconomias(((Number) imovelSubcategoriaArray[3]).intValue());

				// Seta o codigo tarifa social
				subCategoria.setCodigoTarifaSocial((String) imovelSubcategoriaArray[4]);

				// Seta o numero fator fiscalização
				subCategoria.setNumeroFatorFiscalizacao((Short) imovelSubcategoriaArray[5]);

				// Seta o indicador tarifa consumo
				subCategoria.setIndicadorTarifaConsumo((Short) imovelSubcategoriaArray[6]);

				Categoria categoria = new Categoria();

				categoria.setId((Integer) imovelSubcategoriaArray[7]);
				categoria.setDescricao((String) imovelSubcategoriaArray[8]);

				subCategoria.setCategoria(categoria);

				colecaoSubcategoria.add(subCategoria);
			}

		}else{
			// Caso a coleção não tenha retornado objetos
			throw new ControladorException("atencao.nao_cadastrado.imovel_subcategoria", null);
		}

		return colecaoSubcategoria;
	}

	/**
	 * @date 21/02/2007
	 * @author Vivianne Sousa
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovel(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovel(idImovel);
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

			this.repositorioImovel.atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

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

			this.repositorioImovel.atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0302] Gerar Débitos a Cobrar de Acréscimos por Impontualidade
	 * Pequisa o identificador de cobranca de acréscimo pro impontualidade para
	 * o imóvel do cliente responsável.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/02/2007
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Short obterIndicadorGeracaoAcrescimosClienteImovel(Integer idImovel) throws ControladorException{

		try{
			return repositorioImovel.obterIndicadorGeracaoAcrescimosClienteImovel(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * Obter o consumo médio como não medido para o imóvel passado
	 * 
	 * @author Raphael Rossiter
	 * @date 06/03/2007
	 */
	public Integer obterConsumoMedioNaoMedidoImovel(Imovel imovel) throws ControladorException{

		try{

			return repositorioImovel.obterConsumoMedioNaoMedidoImovel(imovel);

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Obter a situação de cobrança para o imóvel passado
	 * 
	 * @author Vivianne Sousa
	 * @date 07/03/2007
	 */
	public String obterSituacaoCobrancaImovel(Integer idImovel) throws ControladorException{

		try{

			return repositorioImovel.obterSituacaoCobrancaImovel(idImovel);

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Pesquisa coleção de imóveis
	 * 
	 * @author Ana Maria
	 * @date 16/03/2007
	 */
	public Collection pesquisarColecaoImovel(FiltrarImovelInserirManterContaHelper filtro) throws ControladorException{

		try{
			return repositorioImovel.pesquisarColecaoImovel(filtro);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa coleção de imóveis do cliente
	 * 
	 * @author Ana Maria
	 * @date 16/03/206
	 */
	public Collection pesquisarColecaoImovelCliente(Integer codigoCliente, Integer relacaoTipo) throws ControladorException{

		try{

			return repositorioImovel.pesquisarColecaoImovelCliente(codigoCliente, relacaoTipo);

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Seleciona a Subcategoria principal de uma categoria
	 * 
	 * @param idCategoria
	 * @return
	 * @throws ControladorException
	 */
	public ImovelSubcategoria obterPrincipalSubcategoria(Integer idCategoria, Integer idImovel) throws ControladorException{

		try{

			return repositorioImovel.obterSubCategoriasPorCategoria(idCategoria, idImovel);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0150] - Retificar Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 16/04/2007
	 */
	public Collection obterQuantidadeEconomiasContaCategoriaPorSubcategoria(Conta conta) throws ControladorException{

		return obterQuantidadeEconomiasContaCategoriaPorSubcategoria(conta.getId());
	}

	/**
	 * [UC0150] - Retificar Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 16/04/2007
	 */
	public Collection obterQuantidadeEconomiasContaCategoriaPorSubcategoria(Integer conta) throws ControladorException{

		Collection colecaoSubcategoria = new ArrayList();
		Collection colecaoContaCategoriaArray = null;

		try{

			colecaoContaCategoriaArray = repositorioImovel.obterQuantidadeEconomiasCategoriaPorSubcategoria(conta);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoContaCategoriaArray != null && !colecaoContaCategoriaArray.isEmpty()){

			Iterator colecaoContaCategoriaArrayIterator = colecaoContaCategoriaArray.iterator();

			while(colecaoContaCategoriaArrayIterator.hasNext()){

				Object[] contaCategoriaArray = (Object[]) colecaoContaCategoriaArrayIterator.next();

				Categoria categoria = new Categoria();
				Subcategoria subcategoria = new Subcategoria();

				// ID da categoria
				categoria.setId((Integer) contaCategoriaArray[8]);

				// DESCRIÇÃO da categoria
				categoria.setDescricao((String) contaCategoriaArray[9]);

				// DESCRIÇÃO ABREVIADA da categoria
				categoria.setDescricaoAbreviada((String) contaCategoriaArray[10]);

				// CATEGORIA da subcategoria
				subcategoria.setCategoria(categoria);

				// ID da subcategoria
				subcategoria.setId((Integer) contaCategoriaArray[0]);

				// CÓDIGO da subcategoria
				subcategoria.setCodigo((Integer) contaCategoriaArray[1]);

				// DESCRIÇÃO da subcategoria
				subcategoria.setDescricao((String) contaCategoriaArray[2]);

				// CÓDIGO TARIFA SOCIAL da subcategoria
				subcategoria.setCodigoTarifaSocial((String) contaCategoriaArray[3]);

				// QUANTIDADE ECONOMIAS da subcategoria
				if(contaCategoriaArray[4] != null){
					subcategoria.setQuantidadeEconomias(((Number) contaCategoriaArray[4]).intValue());
				}

				// NÚMERO FATOR FISCALIZAÇÃO da subcategoria
				subcategoria.setNumeroFatorFiscalizacao((Short) contaCategoriaArray[5]);

				// NÚMERO FATOR FISCALIZAÇÃO da subcategoria
				subcategoria.setIndicadorTarifaConsumo((Short) contaCategoriaArray[6]);

				colecaoSubcategoria.add(subcategoria);
			}

		}else{
			// Caso a coleção não tenha retornado objetos
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.nao_cadastrado.imovel_subcategoria", null);
		}

		return colecaoSubcategoria;
	}

	/**
	 * Autor: Raphael Rossiter Data: 18/04/2007
	 * 
	 * @return Quantidade de subcategorias
	 * @throws ControladorException
	 */
	public Object pesquisarObterQuantidadeSubcategoria() throws ControladorException{

		try{
			return repositorioCategoria.pesquisarObterQuantidadeSubcategoria();
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa todos os ids do perfil do imóvel.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsPerfilImovel() throws ControladorException{

		try{
			return repositorioImovel.pesquisarTodosIdsPerfilImovel();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisa o cliente usuario do imovel
	 * [UC 0275] Gerar resumo ligacoes economias
	 * 
	 * @author Bruno Barros
	 * @date 27/04/2007
	 * @return Cliente
	 * @throws ControladorException
	 */
	public Cliente consultarClienteUsuarioImovel(Imovel imovel) throws ControladorException{

		try{
			return repositorioImovel.consultarClienteUsuarioImovel(imovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisar Grupo do Imovel
	 * 
	 * @author Flavio Cordeiro
	 * @date 18/05/2007
	 * @param idImovel
	 * @return
	 */
	public FaturamentoGrupo pesquisarGrupoImovel(Integer idImovel){

		FaturamentoGrupo retorno = null;

		try{
			retorno = repositorioImovel.pesquisarGrupoImovel(idImovel);
		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * Gerar Relatório de Imóveis Outros Critérios
	 * 
	 * @author Rafael Corrêa
	 * @date 25/07/2006
	 * @param idImovelCondominio
	 * @param idImovelPrincipal
	 * @param idNomeConta
	 * @param idSituacaoLigacaoAgua
	 * @param consumoMinimoInicialAgua
	 * @param consumoMinimoFinalAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param consumoMinimoInicialEsgoto
	 * @param consumoMinimoFinalEsgoto
	 * @param intervaloValorPercentualEsgotoInicial
	 * @param intervaloValorPercentualEsgotoFinal
	 * @param intervaloMediaMinimaImovelInicial
	 * @param intervaloMediaMinimaImovelFinal
	 * @param intervaloMediaMinimaHidrometroInicial
	 * @param intervaloMediaMinimaHidrometroFinal
	 * @param idImovelPerfil
	 * @param idPocoTipo
	 * @param idFaturamentoSituacaoTipo
	 * @param idCobrancaSituacaoTipo
	 * @param idSituacaoEspecialCobranca
	 * @param idEloAnormalidade
	 * @param areaConstruidaInicial
	 * @param areaConstruidaFinal
	 * @param idCadastroOcorrencia
	 * @param idConsumoTarifa
	 * @param idGerenciaRegional
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorComercialInicial
	 * @param setorComercialFinal
	 * @param quadraInicial
	 * @param quadraFinal
	 * @param loteOrigem
	 * @param loteDestno
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param municipio
	 * @param idTipoMedicao
	 * @param indicadorMedicao
	 * @param idSubCategoria
	 * @param idCategoria
	 * @param quantidadeEconomiasInicial
	 * @param quantidadeEconomiasFinal
	 * @param diaVencimento
	 * @param idCliente
	 * @param idClienteTipo
	 * @param idClienteRelacaoTipo
	 * @param numeroPontosInicial
	 * @param numeroPontosFinal
	 * @param numeroMoradoresInicial
	 * @param numeroMoradoresFinal
	 * @param idAreaConstruidaFaixa
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarRelatorioImovelEnderecoOutrosCriterios(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String rotaInicial,
					String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, Short tipoEmpresa, String indicadorOrdenacao,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal) throws ControladorException{

		Collection colecaoImoveis = null;

		try{
			// remove primeiro as linhas do critério cobrança
			colecaoImoveis = repositorioImovel.gerarRelatorioImovelEnderecoOutrosCriterios(idImovelCondominio, idImovelPrincipal,
							idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,
							consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial,
							intervaloValorPercentualEsgotoFinal,

							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal,

							idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,
							idEloAnormalidade, areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,
							idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal, setorComercialInicial, setorComercialFinal,
							quadraInicial, quadraFinal, loteOrigem, loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao,
							indicadorMedicao, idSubCategoria, idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal,
							diaVencimento, idCliente, idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal,
							numeroMoradoresInicial, numeroMoradoresFinal, idAreaConstruidaFaixa, idUnidadeNegocio, rotaInicial, rotaFinal,
							sequencialRotaInicial, sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal,
							indicadorOrdenacao, consumoFixadoEsgotoPocoInicial, consumoFixadoEsgotoPocoFinal);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// colecao com os dados para o relatorio
		Collection colecaoGerarRelatorioImovelOutrosCriterios = null;

		if(colecaoImoveis == null || colecaoImoveis.isEmpty()){
			throw new ControladorException("atencao.filtro_consumo_tarifa_sem_records");
		}

		// para cada imovel pega as conta, debitos, creditos e guias
		if(colecaoImoveis != null && !colecaoImoveis.isEmpty()){

			Iterator iteratorColecaoImoveis = colecaoImoveis.iterator();
			colecaoGerarRelatorioImovelOutrosCriterios = new ArrayList();

			ImovelRelatorioHelper imovelRelatorioHelper = null;
			// GerarRelacaoDebitosHelper gerarRelacaoDebitosHelper = null;
			while(iteratorColecaoImoveis.hasNext()){

				Object[] contasDadosRelatorio = (Object[]) iteratorColecaoImoveis.next();

				imovelRelatorioHelper = new ImovelRelatorioHelper();

				// gerarRelacaoDebitosHelper = new GerarRelacaoDebitosHelper();

				// id gerencia regional
				if(contasDadosRelatorio[0] != null){ // 0
					imovelRelatorioHelper.setIdGerenciaRegional(((Integer) contasDadosRelatorio[0]));
				}
				// nome abreviado gerencia regional
				if(contasDadosRelatorio[1] != null){ // 1
					imovelRelatorioHelper.setDescricaoGerenciaRegional((String) contasDadosRelatorio[1]);
				}
				// id localidade
				if(contasDadosRelatorio[2] != null){ // 2
					imovelRelatorioHelper.setIdLocalidade(((Integer) contasDadosRelatorio[2]));
				}
				// descricao localidade
				if(contasDadosRelatorio[3] != null){ // 3
					imovelRelatorioHelper.setDescricaoLocalidade((String) contasDadosRelatorio[3]);
				}
				// id imovel
				if(contasDadosRelatorio[4] != null){ // 4
					imovelRelatorioHelper.setMatriculaImovel(((Integer) contasDadosRelatorio[4]));
				}
				// quantidade de economias
				if(contasDadosRelatorio[5] != null){ // 5
					imovelRelatorioHelper.setQuantidadeEconomia(Integer.valueOf(((Short) contasDadosRelatorio[5])));
				}
				// codigo setor comercial
				if(contasDadosRelatorio[6] != null){ // 6
					imovelRelatorioHelper.setCodigoSetorComercial(((Integer) contasDadosRelatorio[6]));
				}
				// descrição setor comercial
				if(contasDadosRelatorio[7] != null){ // 7
					imovelRelatorioHelper.setDescricaoSetorComercial(((String) contasDadosRelatorio[7]));
				}
				// numero quadra
				if(contasDadosRelatorio[8] != null){ // 8
					imovelRelatorioHelper.setNumeroQuadra(((Integer) contasDadosRelatorio[8]));
				}
				// lote
				if(contasDadosRelatorio[9] != null){ // 9
					imovelRelatorioHelper.setNumeroLote(((Short) contasDadosRelatorio[9]));
				}
				// sub lote
				if(contasDadosRelatorio[10] != null){ // 10
					imovelRelatorioHelper.setNumeroSubLote(((Short) contasDadosRelatorio[10]));
				}
				// descricao ligacao agua situacao
				if(contasDadosRelatorio[11] != null){ // 11
					imovelRelatorioHelper.setLigacaoAguaSituacao((String) contasDadosRelatorio[11]);
				}
				// descricao ligacao esgoto situacao
				if(contasDadosRelatorio[12] != null){ // 12
					imovelRelatorioHelper.setLigacaoEsgotoSituacao((String) contasDadosRelatorio[12]);
				}
				// percentual
				if(contasDadosRelatorio[13] != null){ // 13
					imovelRelatorioHelper.setPercentual((BigDecimal) contasDadosRelatorio[13]);
				}
				// data ligação esgoto
				if(contasDadosRelatorio[14] != null){ // 14
					imovelRelatorioHelper.setDataLigacaoEsgoto((Date) contasDadosRelatorio[14]);
				}
				// data ligação água
				if(contasDadosRelatorio[15] != null){ // 15
					imovelRelatorioHelper.setDataLigacaoAgua((Date) contasDadosRelatorio[15]);
				}
				// código cliente usuario
				if(contasDadosRelatorio[16] != null){ // 16
					imovelRelatorioHelper.setClienteUsuarioId((Integer) contasDadosRelatorio[16]);
				}
				// nome cliente usuario
				if(contasDadosRelatorio[17] != null){ // 17
					imovelRelatorioHelper.setClienteUsuarioNome((String) contasDadosRelatorio[17]);
				}
				// nome cliente resposanvel
				if(contasDadosRelatorio[18] != null){ // 18
					imovelRelatorioHelper.setClienteResponsavelId((Integer) contasDadosRelatorio[18]);
				}
				// nome cliente resposanvel
				if(contasDadosRelatorio[19] != null){ // 19
					imovelRelatorioHelper.setClienteResponsavelNome((String) contasDadosRelatorio[19]);
				}
				// nome logradouro
				if(contasDadosRelatorio[20] != null){ // 20
					imovelRelatorioHelper.setNomeLogradouro(((String) contasDadosRelatorio[20]));
				}
				// tipo logradouro
				if(contasDadosRelatorio[21] != null){ // 21
					imovelRelatorioHelper.setTipoLogradouro((String) contasDadosRelatorio[21]);
				}
				// titulo logradouro
				if(contasDadosRelatorio[22] != null){ // 22
					imovelRelatorioHelper.setTituloLogradouro((String) contasDadosRelatorio[22]);
				}
				// cep
				if(contasDadosRelatorio[23] != null){ // 23
					Cep cepImovel = new Cep();
					cepImovel.setCodigo((Integer) contasDadosRelatorio[23]);
					imovelRelatorioHelper.setCepFormatado(cepImovel.getCepFormatado());
				}
				// endereço referência
				if(contasDadosRelatorio[24] != null){ // 24
					imovelRelatorioHelper.setEnderecoReferencia((String) contasDadosRelatorio[24]);
				}

				// comlplemente endereco
				if(contasDadosRelatorio[25] != null){ // 25
					imovelRelatorioHelper.setComplementoImovel((String) contasDadosRelatorio[25]);
				}

				// número imóvel
				if(contasDadosRelatorio[26] != null){ // 26
					imovelRelatorioHelper.setNumeroImovel((String) contasDadosRelatorio[26]);
				}
				// nome bairro
				if(contasDadosRelatorio[27] != null){ // 27
					imovelRelatorioHelper.setNomeBairro((String) contasDadosRelatorio[27]);
				}
				// nome município
				if(contasDadosRelatorio[28] != null){ // 28
					imovelRelatorioHelper.setNomeMunicipio((String) contasDadosRelatorio[28]);
				}
				// sigla uf
				if(contasDadosRelatorio[29] != null){ // 29
					imovelRelatorioHelper.setUnidadeFederacao((String) contasDadosRelatorio[29]);
				}
				// indicador imóvel condomínio
				if(contasDadosRelatorio[30] != null){ // 30
					imovelRelatorioHelper.setIndicadorImovelCondominio(((Integer) contasDadosRelatorio[30]).shortValue());
				}

				// número moradores
				if(contasDadosRelatorio[31] != null){ // 31
					imovelRelatorioHelper.setNumeroMoradores(((Integer) contasDadosRelatorio[31]).shortValue());
				}
				// matrícula imóvel condomínio
				if(contasDadosRelatorio[32] != null){ // 32
					imovelRelatorioHelper.setMatriculaImovelCondominio((Integer) contasDadosRelatorio[32]);
				}
				// matrícula imóvel principal
				if(contasDadosRelatorio[33] != null){ // 33
					imovelRelatorioHelper.setMatriculaImovelPrincipal((Integer) contasDadosRelatorio[33]);
				}
				// número pontos utilização
				if(contasDadosRelatorio[34] != null){ // 34
					imovelRelatorioHelper.setNumeroPontosUtilzacao(((Integer) contasDadosRelatorio[34]).shortValue());
				}
				// perfil imóvel
				if(contasDadosRelatorio[35] != null){ // 35
					imovelRelatorioHelper.setPerfilImovel((String) contasDadosRelatorio[35]);
				}
				// área construída maior faixa
				if(contasDadosRelatorio[36] != null){ // 36
					imovelRelatorioHelper.setAreaConstruidaMaiorFaixa((Integer) contasDadosRelatorio[36]);
				}
				// área construída menor faixa
				if(contasDadosRelatorio[37] != null){ // 37
					imovelRelatorioHelper.setAreaConstruidaMenorFaixa((Integer) contasDadosRelatorio[37]);
				}
				// área construída
				if(contasDadosRelatorio[38] != null){ // 38
					imovelRelatorioHelper.setAreaConstruidaImovel((BigDecimal) contasDadosRelatorio[38]);
				}
				// pavimento calçada
				if(contasDadosRelatorio[39] != null){ // 39
					imovelRelatorioHelper.setTipoPavimentoCalcada((String) contasDadosRelatorio[39]);
				}
				// pavimento rua
				if(contasDadosRelatorio[40] != null){ // 40
					imovelRelatorioHelper.setTipoPavimentoRua((String) contasDadosRelatorio[40]);
				}

				// despejo
				if(contasDadosRelatorio[41] != null){ // 41
					imovelRelatorioHelper.setTipoDespejo(((String) contasDadosRelatorio[41]));
				}
				// volume reservatorio superior menor faixa
				if(contasDadosRelatorio[42] != null){ // 42
					imovelRelatorioHelper.setVolumeReservatorioSuperiorMenorFaixa((BigDecimal) contasDadosRelatorio[42]);
				}
				// volume reservatorio superior maior faixa
				if(contasDadosRelatorio[43] != null){ // 43
					imovelRelatorioHelper.setVolumeReservatorioSuperiorMaiorFaixa((BigDecimal) contasDadosRelatorio[43]);
				}
				// volume reservatorio superior
				if(contasDadosRelatorio[44] != null){ // 44
					imovelRelatorioHelper.setVolumeReservatorioSuperior((BigDecimal) contasDadosRelatorio[44]);
				}
				// volume reservatorio inferior menor faixa
				if(contasDadosRelatorio[45] != null){ // 45
					imovelRelatorioHelper.setVolumeReservatorioInferiorMenorFaixa((BigDecimal) contasDadosRelatorio[45]);
				}
				// volume reservatorio inferior maior faixa
				if(contasDadosRelatorio[46] != null){ // 46
					imovelRelatorioHelper.setVolumeReservatorioInferiorMaiorFaixa((BigDecimal) contasDadosRelatorio[46]);
				}
				// volume reservatorio inferior
				if(contasDadosRelatorio[47] != null){ // 47
					imovelRelatorioHelper.setVolumeReservatorioInferior((BigDecimal) contasDadosRelatorio[47]);
				}
				// volume piscina menor faixa
				if(contasDadosRelatorio[48] != null){ // 48
					imovelRelatorioHelper.setVolumePiscinaMenorFaixa((BigDecimal) contasDadosRelatorio[48]);
				}
				// volume piscina maior faixa
				if(contasDadosRelatorio[49] != null){ // 49
					imovelRelatorioHelper.setVolumePiscinaMaiorFaixa((BigDecimal) contasDadosRelatorio[49]);
				}
				// volume piscina
				if(contasDadosRelatorio[50] != null){ // 50
					imovelRelatorioHelper.setVolumePiscina((BigDecimal) contasDadosRelatorio[50]);
				}

				// tipo de poço
				if(contasDadosRelatorio[51] != null){ // 51
					imovelRelatorioHelper.setDescricaoTipoPoco(((String) contasDadosRelatorio[51]));
				}
				// diâmetro da ligação de água
				if(contasDadosRelatorio[52] != null){ // 52
					imovelRelatorioHelper.setDiametroLigacaoAgua((String) contasDadosRelatorio[52]);
				}
				// material da ligação de água
				if(contasDadosRelatorio[53] != null){ // 53
					imovelRelatorioHelper.setMaterialLigacaoAgua((String) contasDadosRelatorio[53]);
				}
				// diâmetro da ligação de esgoto
				if(contasDadosRelatorio[54] != null){ // 54
					imovelRelatorioHelper.setDiametroLigacaoEsgoto((String) contasDadosRelatorio[54]);
				}
				// material da ligação de esgoto
				if(contasDadosRelatorio[55] != null){ // 55
					imovelRelatorioHelper.setMaterialLigacaoEsgoto((String) contasDadosRelatorio[55]);
				}
				// consumo mínimo esgoto
				if(contasDadosRelatorio[56] != null){ // 56
					imovelRelatorioHelper.setConsumoMinimoFixadoLigacaoEsgoto((Integer) contasDadosRelatorio[56]);
				}
				// percentual água coletada
				if(contasDadosRelatorio[57] != null){ // 57
					imovelRelatorioHelper.setPercentualAguaConsumidaColetada((BigDecimal) contasDadosRelatorio[57]);
				}
				// percentual esgoto
				if(contasDadosRelatorio[58] != null){ // 58
					imovelRelatorioHelper.setPercentual((BigDecimal) contasDadosRelatorio[58]);
				}
				// número hidrômetro água
				if(contasDadosRelatorio[59] != null){ // 59
					imovelRelatorioHelper.setNumeroHidrometroAgua((String) contasDadosRelatorio[59]);
				}
				// ano fabricação hidrômetro água
				if(contasDadosRelatorio[60] != null){ // 60
					imovelRelatorioHelper.setAnoFabricaocaHidrometroAgua(((Integer) contasDadosRelatorio[60]).shortValue());
				}
				// capacidade hidrômetro água
				if(contasDadosRelatorio[61] != null){ // 61
					imovelRelatorioHelper.setCapacidadeHidrometroAgua(((String) contasDadosRelatorio[61]));
				}
				// marca hidrômetro água
				if(contasDadosRelatorio[62] != null){ // 62
					imovelRelatorioHelper.setMarcaHidrometroAgua((String) contasDadosRelatorio[62]);
				}
				// diâmetro hidrômetro água
				if(contasDadosRelatorio[63] != null){ // 63
					imovelRelatorioHelper.setDiametroHidrometroAgua((String) contasDadosRelatorio[63]);
				}
				// tipo hidrômetro água
				if(contasDadosRelatorio[64] != null){ // 64
					imovelRelatorioHelper.setTipoHidrometroAgua((String) contasDadosRelatorio[64]);
				}
				// data instalação hidrômetro água
				if(contasDadosRelatorio[65] != null){ // 65
					imovelRelatorioHelper.setDataInstalacaoHidrometroAgua((Date) contasDadosRelatorio[65]);
				}
				// local instalação hidrômetro água
				if(contasDadosRelatorio[66] != null){ // 66
					imovelRelatorioHelper.setLocalInstalacaoHidrometroAgua((String) contasDadosRelatorio[66]);
				}
				// proteção hidrômetro água
				if(contasDadosRelatorio[67] != null){ // 67
					imovelRelatorioHelper.setTipoProtecaoHidrometroAgua((String) contasDadosRelatorio[67]);
				}
				// indicador cavalete hidrômetro água
				if(contasDadosRelatorio[68] != null){ // 68
					imovelRelatorioHelper.setIndicadorExistenciaCavaleteAgua(((Integer) contasDadosRelatorio[68]).shortValue());
				}
				// número hidrômetro poço
				if(contasDadosRelatorio[69] != null){ // 69
					imovelRelatorioHelper.setNumeroHidrometroPoco((String) contasDadosRelatorio[69]);
				}
				// ano fabricação hidrômetro poço
				if(contasDadosRelatorio[70] != null){ // 70
					imovelRelatorioHelper.setAnoFabricacaoHidrometroPoco(((Integer) contasDadosRelatorio[70]).shortValue());
				}
				// capacidade hidrômetro poço
				if(contasDadosRelatorio[71] != null){ // 71
					imovelRelatorioHelper.setCapacidadeHidrometroPoco(((String) contasDadosRelatorio[71]));
				}
				// marca hidrômetro poço
				if(contasDadosRelatorio[72] != null){ // 72
					imovelRelatorioHelper.setMarcaHidrometroPoco((String) contasDadosRelatorio[72]);
				}
				// diâmetro hidrômetro poço
				if(contasDadosRelatorio[73] != null){ // 73
					imovelRelatorioHelper.setDiametroHidrometroPoco((String) contasDadosRelatorio[73]);
				}
				// tipo hidrômetro poço
				if(contasDadosRelatorio[74] != null){ // 74
					imovelRelatorioHelper.setTipoHidrometroPoco((String) contasDadosRelatorio[74]);
				}
				// data instalação hidrômetro poço
				if(contasDadosRelatorio[75] != null){ // 75
					imovelRelatorioHelper.setDataInstalacaoHidrometroPoco((Date) contasDadosRelatorio[75]);
				}
				// local instalação hidrômetro poço
				if(contasDadosRelatorio[76] != null){ // 76
					imovelRelatorioHelper.setLocalInstalacaoHidrometroPoco((String) contasDadosRelatorio[76]);
				}
				// proteção hidrômetro poço
				if(contasDadosRelatorio[77] != null){ // 77
					imovelRelatorioHelper.setTipoProtecaoHidrometroPoco((String) contasDadosRelatorio[77]);
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[78] != null){ // 78
					imovelRelatorioHelper.setIndicadorExistenciaCavaletePoco(((Integer) contasDadosRelatorio[78]).shortValue());
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[79] != null){ // 79
					imovelRelatorioHelper.setConsumoMinimoFixadoAgua((Integer) contasDadosRelatorio[79]);
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[80] != null){ // 80
					imovelRelatorioHelper.setConsumoMinimoFixadoLigacaoEsgoto((Integer) contasDadosRelatorio[80]);
				}
				// indicador Jardim
				if(contasDadosRelatorio[81] != null){ // 81
					imovelRelatorioHelper.setJardim(((Short) contasDadosRelatorio[81]).toString());
				}

				// Rota
				if(contasDadosRelatorio[84] != null){ // 82
					imovelRelatorioHelper.setIdRota((Integer) contasDadosRelatorio[84]);
				}

				// Número Sequencial Rota
				if(contasDadosRelatorio[83] != null){ // 83
					imovelRelatorioHelper.setNumeroSequencialRota((Integer) contasDadosRelatorio[83]);
				}

				// Segmento
				if(contasDadosRelatorio[85] != null){ // 85
					imovelRelatorioHelper.setSegmento((Short) contasDadosRelatorio[85]);
				}

				// consumo Medio
				Integer consumoMedio = getControladorCobranca().pesquisarConsumoMedioConsumoHistoricoImovel(
								Integer.valueOf(imovelRelatorioHelper.getMatriculaImovel()));
				if(consumoMedio != null){
					imovelRelatorioHelper.setConsumoMedioImovel(consumoMedio);
				}

				Imovel imovel = new Imovel();

				Localidade localidadeImovel = new Localidade();
				localidadeImovel.setId(imovelRelatorioHelper.getIdLocalidade());
				SetorComercial setorComercialImovel = new SetorComercial();
				setorComercialImovel.setCodigo(imovelRelatorioHelper.getCodigoSetorComercial().intValue());
				Quadra quadraImovel = new Quadra();
				quadraImovel.setNumeroQuadra(imovelRelatorioHelper.getNumeroQuadra().intValue());

				imovel.setLocalidade(localidadeImovel);
				imovel.setSetorComercial(setorComercialImovel);
				imovel.setQuadra(quadraImovel);
				imovel.setLote(Short.valueOf(imovelRelatorioHelper.getNumeroLote()).shortValue());
				imovel.setSubLote(Short.valueOf(imovelRelatorioHelper.getNumeroSubLote()).shortValue());

				Integer idRota = imovelRelatorioHelper.getIdRota();

				if(idRota != null){
					Rota rota = new Rota();
					rota.setId(idRota);

					imovel.setRota(rota);
				}

				imovel.setNumeroSegmento(imovelRelatorioHelper.getSegmento());

				// inscricao formatada do imovel
				imovelRelatorioHelper.setInscricaoImovel(imovel.getInscricaoFormatada());

				// obter endereco
				imovelRelatorioHelper.setEndereco(getControladorEndereco().pesquisarEndereco(
								Integer.valueOf(imovelRelatorioHelper.getMatriculaImovel())));

				Collection colecaoSubcategoria = this.pesquisarSubcategoriasImovelRelatorio(imovelRelatorioHelper.getMatriculaImovel());

				// ------subcategoria
				String[] arraySubcatgegoriasQtdEconomias = new String[colecaoSubcategoria.size()];
				if(!colecaoSubcategoria.isEmpty()){

					Iterator iterator = colecaoSubcategoria.iterator();

					int i = 0;

					while(iterator.hasNext()){

						Object[] arraySubcategoria = (Object[]) iterator.next();

						arraySubcatgegoriasQtdEconomias[i] = arraySubcategoria[0].toString() + "/" + arraySubcategoria[1].toString();

						i++;

					}
					imovelRelatorioHelper.setSubcategoriaQtdEconomia(arraySubcatgegoriasQtdEconomias);
				}

				// [UC0307 Obter Indicador de Existência de Hidrômetro no
				// Imóvel]
				imovelRelatorioHelper.setIndicadorExistenciaHidrometro(obterIndicadorExistenciaHidrometroImovel(imovelRelatorioHelper
								.getMatriculaImovel()));

				// [UC123 Obter Descrições da Categoria do Imóvel]
				imovelRelatorioHelper.setDescricaoAbreviadaCategoria(obterDescricoesCategoriaImovel(
								new Imovel(imovelRelatorioHelper.getMatriculaImovel())).getDescricaoAbreviada());

				colecaoGerarRelatorioImovelOutrosCriterios.add(imovelRelatorioHelper);
			}

			if(!SistemaParametro.INDICADOR_EMPRESA_DESO.equals(tipoEmpresa)){

				// Organizar por endereço
				Collections.sort((List) colecaoGerarRelatorioImovelOutrosCriterios, new Comparator() {

					public int compare(Object a, Object b){

						String chave1 = "";
						if(((ImovelRelatorioHelper) a).getIdLocalidade() != null){
							chave1 = chave1 + Util.adicionarZerosEsquedaNumero(3, ((ImovelRelatorioHelper) a).getIdLocalidade().toString());
						}

						if(((ImovelRelatorioHelper) a).getTipoLogradouro() != null){
							chave1 = chave1 + ((ImovelRelatorioHelper) a).getTipoLogradouro();
						}

						if(((ImovelRelatorioHelper) a).getTituloLogradouro() != null){
							chave1 = chave1 + ((ImovelRelatorioHelper) a).getTituloLogradouro();
						}

						if(((ImovelRelatorioHelper) a).getNomeLogradouro() != null){
							chave1 = chave1 + ((ImovelRelatorioHelper) a).getNomeLogradouro();
						}

						if(((ImovelRelatorioHelper) a).getNumeroImovel() != null){
							chave1 = chave1 + Util.adicionarZerosEsquedaNumero(5, ((ImovelRelatorioHelper) a).getNumeroImovel().trim());
						}

						String chave2 = "";
						if(((ImovelRelatorioHelper) b).getIdLocalidade() != null){
							chave2 = chave2 + Util.adicionarZerosEsquedaNumero(3, ((ImovelRelatorioHelper) b).getIdLocalidade().toString());
						}

						if(((ImovelRelatorioHelper) b).getTipoLogradouro() != null){
							chave2 = chave2 + ((ImovelRelatorioHelper) b).getTipoLogradouro();
						}

						if(((ImovelRelatorioHelper) b).getTituloLogradouro() != null){
							chave2 = chave2 + ((ImovelRelatorioHelper) b).getTituloLogradouro();
						}

						if(((ImovelRelatorioHelper) b).getNomeLogradouro() != null){
							chave2 = chave2 + ((ImovelRelatorioHelper) b).getNomeLogradouro();
						}

						if(((ImovelRelatorioHelper) b).getNumeroImovel() != null){
							chave2 = chave2 + Util.adicionarZerosEsquedaNumero(5, ((ImovelRelatorioHelper) b).getNumeroImovel().trim());
						}

						return chave1.compareTo(chave2);

					}
				});
			}

		}

		return colecaoGerarRelatorioImovelOutrosCriterios;
	}

	/**
	 * [UC0591] - Gerar Relatório de Clientes Especiais
	 * Pesquisas os imóveis de acordo com os parâmetros da pesquisa
	 * 
	 * @author Rafael Corrêa
	 * @date 31/05/2007
	 */
	public Collection pesquisarImovelClientesEspeciaisRelatorio(String idUnidadeNegocio, String idGerenciaRegional,
					String idLocalidadeInicial, String idLocalidadeFinal, String[] idsPerfilImovel, String[] idsCategoria,
					String[] idsSubcategoria, String idSituacaoAgua, String idSituacaoEsgoto, String qtdeEconomiasInicial,
					String qtdeEconomiasFinal, String intervaloConsumoAguaInicial, String intervaloConsumoAguaFinal,
					String intervaloConsumoEsgotoInicial, String intervaloConsumoEsgotoFinal, String idClienteResponsavel,
					String intervaloConsumoResponsavelInicial, String intervaloConsumoResponsavelFinal,
					Date dataInstalacaoHidrometroInicial, Date dataInstalacaoHidrometroFinal, String[] idsCapacidadesHidrometro,
					String[] idsTarifasConsumo, Integer anoMesFaturamento, String idLeituraAnormalidade, String leituraAnormalidade,
					String idConsumoAnormalidade, String consumoAnormalidade, String[] idsClienteTipoEspecial) throws ControladorException{

		Collection colecaoImovelClientesEspeciaisHelper = null;
		Collection colecaoDadosImoveis = null;
		try{
			colecaoDadosImoveis = repositorioImovel.pesquisarImovelClientesEspeciaisRelatorio(idUnidadeNegocio, idGerenciaRegional,
							idLocalidadeInicial, idLocalidadeFinal, idsPerfilImovel, idsCategoria, idsSubcategoria, idSituacaoAgua,
							idSituacaoEsgoto, qtdeEconomiasInicial, qtdeEconomiasFinal, intervaloConsumoAguaInicial,
							intervaloConsumoAguaFinal, intervaloConsumoEsgotoInicial, intervaloConsumoEsgotoFinal, idClienteResponsavel,
							intervaloConsumoResponsavelInicial, intervaloConsumoResponsavelFinal, dataInstalacaoHidrometroInicial,
							dataInstalacaoHidrometroFinal, idsCapacidadesHidrometro, idsTarifasConsumo, anoMesFaturamento,
							idLeituraAnormalidade, leituraAnormalidade, idConsumoAnormalidade, consumoAnormalidade, idsClienteTipoEspecial);

			if(colecaoDadosImoveis == null || colecaoDadosImoveis.isEmpty()) throw new ControladorException("atencao.relatorio.vazio");

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoDadosImoveis != null && !colecaoDadosImoveis.isEmpty()){

			colecaoImovelClientesEspeciaisHelper = new ArrayList();

			Iterator colecaoDadosImoveisIterator = colecaoDadosImoveis.iterator();

			while(colecaoDadosImoveisIterator.hasNext()){

				Object[] dadosImovel = (Object[]) colecaoDadosImoveisIterator.next();

				ImovelClientesEspeciaisRelatorioHelper imovelClientesEspeciaisRelatorioHelper = new ImovelClientesEspeciaisRelatorioHelper();

				Imovel imovel = new Imovel();
				Conta conta = null;

				// Gerência Regional
				if(dadosImovel[0] != null){
					imovelClientesEspeciaisRelatorioHelper.setIdGerenciaRegional((Integer) dadosImovel[0]);
					imovelClientesEspeciaisRelatorioHelper.setNomeGerenciaRegional((String) dadosImovel[1]);
				}

				// Localidade
				if(dadosImovel[2] != null){
					imovelClientesEspeciaisRelatorioHelper.setIdLocalidade((Integer) dadosImovel[2]);
					imovelClientesEspeciaisRelatorioHelper.setNomeLocalidade((String) dadosImovel[3]);

					Localidade localidade = new Localidade();
					localidade.setId((Integer) dadosImovel[2]);
					imovel.setLocalidade(localidade);
				}

				// Setor Comercial
				if(dadosImovel[4] != null){
					SetorComercial setorComercial = new SetorComercial();
					setorComercial.setCodigo((Integer) dadosImovel[4]);
					imovel.setSetorComercial(setorComercial);
				}

				// Quadra
				if(dadosImovel[5] != null){
					Quadra quadra = new Quadra();
					quadra.setNumeroQuadra((Integer) dadosImovel[5]);
					imovel.setQuadra(quadra);
				}

				// Lote
				if(dadosImovel[6] != null){
					imovel.setLote((Short) dadosImovel[6]);
				}

				// SubLote
				if(dadosImovel[7] != null){
					imovel.setSubLote((Short) dadosImovel[7]);
				}

				// Inscrição
				imovelClientesEspeciaisRelatorioHelper.setInscricaoImovel(imovel.getInscricaoFormatada());

				// Id do Imóvel
				if(dadosImovel[8] != null){
					imovelClientesEspeciaisRelatorioHelper.setIdImovel((Integer) dadosImovel[8]);
				}

				// Categoria
				Categoria categoria = this.obterPrincipalCategoriaImovel(imovelClientesEspeciaisRelatorioHelper.getIdImovel());

				if(categoria != null){
					imovelClientesEspeciaisRelatorioHelper.setIdCategoria(categoria.getId());
					imovelClientesEspeciaisRelatorioHelper.setDescricaoCategoria(categoria.getDescricao());
				}

				// Subcategoria
				ImovelSubcategoria imovelSubcategoria = this.obterPrincipalSubcategoria(categoria.getId(),
								imovelClientesEspeciaisRelatorioHelper.getIdImovel());

				if(imovelSubcategoria != null){
					imovelClientesEspeciaisRelatorioHelper.setIdSubcategoria(imovelSubcategoria.getComp_id().getSubcategoria().getId());

					try{

						String descricaoSubcategoria = repositorioImovel.obterDescricaoSubcategoria(imovelClientesEspeciaisRelatorioHelper
										.getIdSubcategoria());

						imovelClientesEspeciaisRelatorioHelper.setDescricaoSubcategoria(descricaoSubcategoria);

					}catch(ErroRepositorioException ex){
						throw new ControladorException("erro.sistema", ex);
					}

				}

				// Cliente Usuário
				if(dadosImovel[9] != null){
					imovelClientesEspeciaisRelatorioHelper.setIdClienteUsuario((Integer) dadosImovel[9]);
					imovelClientesEspeciaisRelatorioHelper.setNomeClienteUsuario((String) dadosImovel[10]);
				}

				// Quantidade de Economias
				if(dadosImovel[11] != null){
					imovelClientesEspeciaisRelatorioHelper.setQtdeEconomias((Short) dadosImovel[11]);
				}

				// Descrição da Situação da Ligação de Água
				if(dadosImovel[12] != null){
					imovelClientesEspeciaisRelatorioHelper.setDescricaoSituacaoLigacaoAgua((String) dadosImovel[12]);
				}else{
					imovelClientesEspeciaisRelatorioHelper.setDescricaoSituacaoLigacaoAgua("");
				}

				// Descrição da Situação da Ligação de Esgoto
				if(dadosImovel[13] != null){
					imovelClientesEspeciaisRelatorioHelper.setDescricaoSituacaoLigacaoEsgoto((String) dadosImovel[13]);
				}else{
					imovelClientesEspeciaisRelatorioHelper.setDescricaoSituacaoLigacaoEsgoto("");
				}

				// Débitos Vencidos e Faturas em Aberto
				Calendar dataInicioVencimentoDebito = new GregorianCalendar();
				dataInicioVencimentoDebito.set(Calendar.YEAR, Integer.valueOf("0001").intValue());
				dataInicioVencimentoDebito.set(Calendar.MONTH, 0);
				dataInicioVencimentoDebito.set(Calendar.DATE, Integer.valueOf("01").intValue());

				// data final de vencimento de debito
				Calendar dataFimVencimentoDebito = new GregorianCalendar();
				dataFimVencimentoDebito.add(Calendar.DATE, -15);

				ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = getControladorCobranca().obterDebitoImovelOuCliente(1,
								imovelClientesEspeciaisRelatorioHelper.getIdImovel().toString(), null, null, "000101", "999912",
								dataInicioVencimentoDebito.getTime(), dataFimVencimentoDebito.getTime(), 1, 1, 2, 2, 1, 1, 1, null, null,
								null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null);

				Integer qtdeDebitos = Integer.valueOf("0");
				BigDecimal valorDebitos = new BigDecimal("0.00");

				// Contas
				if(obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel() != null
								&& !obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().isEmpty()){
					qtdeDebitos = qtdeDebitos + obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().size();

					Iterator colecaoContaValoresIterator = obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().iterator();

					while(colecaoContaValoresIterator.hasNext()){

						ContaValoresHelper contaValoresHelper = (ContaValoresHelper) colecaoContaValoresIterator.next();

						valorDebitos = valorDebitos.add(contaValoresHelper.getValorTotalConta());

					}
				}

				// Guias de Pagamento
				if(obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores() != null
								&& !obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores().isEmpty()){
					qtdeDebitos = qtdeDebitos + obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores().size();

					Iterator colecaoGuiasPagamentoValoresIterator = obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores()
									.iterator();

					while(colecaoGuiasPagamentoValoresIterator.hasNext()){
						GuiaPagamentoValoresHelper guiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) colecaoGuiasPagamentoValoresIterator
										.next();
						valorDebitos = valorDebitos.add(guiaPagamentoValoresHelper.getValorTotalPrestacao());
					}
				}

				imovelClientesEspeciaisRelatorioHelper.setQtdeDebitosVencidos(qtdeDebitos);
				imovelClientesEspeciaisRelatorioHelper.setValorDebitosVencidos(valorDebitos);

				// Descrição da Capacidade do Hidrômetro
				if(dadosImovel[14] != null){
					imovelClientesEspeciaisRelatorioHelper.setDescricaoCapacidadeHidrometro((String) dadosImovel[14]);
				}else{
					imovelClientesEspeciaisRelatorioHelper.setDescricaoCapacidadeHidrometro("");
				}

				// Data de Instalação do Hidrômetro
				if(dadosImovel[15] != null){
					imovelClientesEspeciaisRelatorioHelper.setDataInstalacaoHidrometro((Date) dadosImovel[15]);
				}

				// Cliente Responsável
				if(dadosImovel[16] != null){
					imovelClientesEspeciaisRelatorioHelper.setIdClienteResponsavel((Integer) dadosImovel[16]);
					imovelClientesEspeciaisRelatorioHelper.setNomeClienteResponsavel((String) dadosImovel[17]);
				}

				// Consumo Água
				if(dadosImovel[18] != null){
					imovelClientesEspeciaisRelatorioHelper.setConsumoAgua((Integer) dadosImovel[18]);
				}

				// Consumo Esgoto
				if(dadosImovel[19] != null){
					imovelClientesEspeciaisRelatorioHelper.setConsumoEsgoto((Integer) dadosImovel[19]);
				}

				// Consumo Mínimo
				if(dadosImovel[20] != null){
					imovelClientesEspeciaisRelatorioHelper.setConsumoMinimoEsgoto((Integer) dadosImovel[20]);
				}

				// Descrição da Tarifa de Consumo
				if(dadosImovel[21] != null){
					imovelClientesEspeciaisRelatorioHelper.setDescricaoTarifaConsumo((String) dadosImovel[21]);
				}else{
					imovelClientesEspeciaisRelatorioHelper.setDescricaoTarifaConsumo("");
				}

				// Valor Água
				if(dadosImovel[22] != null){
					conta = new Conta();
					conta.setValorAgua((BigDecimal) dadosImovel[22]);
					imovelClientesEspeciaisRelatorioHelper.setValorAgua((BigDecimal) dadosImovel[22]);
				}

				// Valor Esgoto
				if(dadosImovel[23] != null){
					conta.setValorEsgoto((BigDecimal) dadosImovel[23]);
					imovelClientesEspeciaisRelatorioHelper.setValorEsgoto((BigDecimal) dadosImovel[23]);
				}

				// Valor Débitos
				if(dadosImovel[24] != null){
					conta.setDebitos((BigDecimal) dadosImovel[24]);
				}

				// Valor Créditos
				if(dadosImovel[25] != null){
					conta.setValorCreditos((BigDecimal) dadosImovel[25]);
				}

				// Valor da Conta
				if(conta != null){
					imovelClientesEspeciaisRelatorioHelper.setValorConta(conta.getValorTotal());
				}

				colecaoImovelClientesEspeciaisHelper.add(imovelClientesEspeciaisRelatorioHelper);
			}
		}

		return colecaoImovelClientesEspeciaisHelper;
	}

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * Recupera a situação da ligação de esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 07/06/2007
	 * @param idImovel
	 * @return LigacaoEsgotoSituacao
	 * @throws ErroRepositorioException
	 */
	public LigacaoEsgotoSituacao pesquisarLigacaoEsgotoSituacao(Integer idImovel) throws ControladorException{

		try{

			return repositorioImovel.pesquisarLigacaoEsgotoSituacao(idImovel);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * Recupera a situação da ligação de agua
	 * 
	 * @author Raphael Rossiter
	 * @date 07/06/2007
	 * @param idImovel
	 * @return LigacaoAguaSituacao
	 * @throws ErroRepositorioException
	 */
	public LigacaoAguaSituacao pesquisarLigacaoAguaSituacao(Integer idImovel) throws ControladorException{

		try{

			return repositorioImovel.pesquisarLigacaoAguaSituacao(idImovel);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Gerar Relatório de Imóveis Outros Critérios
	 * 
	 * @author Rafael Corrêa
	 * @date 25/07/2006
	 * @param idImovelCondominio
	 * @param idImovelPrincipal
	 * @param idNomeConta
	 * @param idSituacaoLigacaoAgua
	 * @param consumoMinimoInicialAgua
	 * @param consumoMinimoFinalAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param consumoMinimoInicialEsgoto
	 * @param consumoMinimoFinalEsgoto
	 * @param intervaloValorPercentualEsgotoInicial
	 * @param intervaloValorPercentualEsgotoFinal
	 * @param intervaloMediaMinimaImovelInicial
	 * @param intervaloMediaMinimaImovelFinal
	 * @param intervaloMediaMinimaHidrometroInicial
	 * @param intervaloMediaMinimaHidrometroFinal
	 * @param idImovelPerfil
	 * @param idPocoTipo
	 * @param idFaturamentoSituacaoTipo
	 * @param idCobrancaSituacaoTipo
	 * @param idSituacaoEspecialCobranca
	 * @param idEloAnormalidade
	 * @param areaConstruidaInicial
	 * @param areaConstruidaFinal
	 * @param idCadastroOcorrencia
	 * @param idConsumoTarifa
	 * @param idGerenciaRegional
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorComercialInicial
	 * @param setorComercialFinal
	 * @param quadraInicial
	 * @param quadraFinal
	 * @param loteOrigem
	 * @param loteDestno
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param municipio
	 * @param idTipoMedicao
	 * @param indicadorMedicao
	 * @param idSubCategoria
	 * @param idCategoria
	 * @param quantidadeEconomiasInicial
	 * @param quantidadeEconomiasFinal
	 * @param diaVencimento
	 * @param idCliente
	 * @param idClienteTipo
	 * @param idClienteRelacaoTipo
	 * @param numeroPontosInicial
	 * @param numeroPontosFinal
	 * @param numeroMoradoresInicial
	 * @param numeroMoradoresFinal
	 * @param idAreaConstruidaFaixa
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarRelatorioCadastroConsumidoresInscricao(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String rotaInicial,
					String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String indicadorOrdenacao,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal) throws ControladorException{

		Collection colecaoImoveis = null;

		try{

			colecaoImoveis = repositorioImovel.gerarRelatorioCadastroConsumidoreInscricao(idImovelCondominio, idImovelPrincipal,
							idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,
							consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial,
							intervaloValorPercentualEsgotoFinal,

							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal,

							idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,
							idEloAnormalidade, areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,
							idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal, setorComercialInicial, setorComercialFinal,
							quadraInicial, quadraFinal, loteOrigem, loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao,
							indicadorMedicao, idSubCategoria, idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal,
							diaVencimento, idCliente, idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal,
							numeroMoradoresInicial, numeroMoradoresFinal, idAreaConstruidaFaixa, idUnidadeNegocio, rotaInicial, rotaFinal,
							sequencialRotaInicial, sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal,
							indicadorOrdenacao, consumoFixadoEsgotoPocoInicial, consumoFixadoEsgotoPocoFinal);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		// colecao com os dados para o relatorio
		Collection colecaoGerarRelatorioImovelOutrosCriterios = null;

		if(colecaoImoveis == null || colecaoImoveis.isEmpty()){
			throw new ControladorException("atencao.filtro_consumo_tarifa_sem_records");
		}

		// para cada imovel pega as conta, debitos, creditos e guias
		if(colecaoImoveis != null && !colecaoImoveis.isEmpty()){

			Iterator iteratorColecaoImoveis = colecaoImoveis.iterator();
			colecaoGerarRelatorioImovelOutrosCriterios = new ArrayList();

			ImovelRelatorioHelper imovelRelatorioHelper = null;
			// GerarRelacaoDebitosHelper gerarRelacaoDebitosHelper = null;
			while(iteratorColecaoImoveis.hasNext()){

				Object[] contasDadosRelatorio = (Object[]) iteratorColecaoImoveis.next();

				imovelRelatorioHelper = new ImovelRelatorioHelper();

				// gerarRelacaoDebitosHelper = new GerarRelacaoDebitosHelper();

				// id unidade negocio
				if(contasDadosRelatorio[0] != null){ // 0
					imovelRelatorioHelper.setIdUnidadeNegocio(((Integer) contasDadosRelatorio[0]));
				}

				// nome unidade negocio
				if(contasDadosRelatorio[1] != null){ // 1
					imovelRelatorioHelper.setNomeUnidadeNegocio((String) contasDadosRelatorio[1]);
				}
				// id gerencia regional
				if(contasDadosRelatorio[2] != null){ // 2
					imovelRelatorioHelper.setIdGerenciaRegional(((Integer) contasDadosRelatorio[2]));
				}
				// nome abreviado gerencia regional
				if(contasDadosRelatorio[3] != null){ // 3
					imovelRelatorioHelper.setDescricaoGerenciaRegional((String) contasDadosRelatorio[3]);
				}
				// id localidade
				if(contasDadosRelatorio[4] != null){ // 4
					imovelRelatorioHelper.setIdLocalidade(((Integer) contasDadosRelatorio[4]));
				}
				// descricao localidade
				if(contasDadosRelatorio[5] != null){ // 5
					imovelRelatorioHelper.setDescricaoLocalidade((String) contasDadosRelatorio[5]);
				}
				// id imovel
				if(contasDadosRelatorio[6] != null){ // 6
					imovelRelatorioHelper.setMatriculaImovel(((Integer) contasDadosRelatorio[6]));
				}
				// quantidade de economias
				if(contasDadosRelatorio[7] != null){ // 7
					imovelRelatorioHelper.setQuantidadeEconomia(Integer.valueOf(((Short) contasDadosRelatorio[7])));
				}
				// codigo setor comercial
				if(contasDadosRelatorio[8] != null){ // 8
					imovelRelatorioHelper.setCodigoSetorComercial(((Integer) contasDadosRelatorio[8]));
				}
				// descrição setor comercial
				if(contasDadosRelatorio[9] != null){ // 9
					imovelRelatorioHelper.setDescricaoSetorComercial(((String) contasDadosRelatorio[9]));
				}
				// numero quadra
				if(contasDadosRelatorio[10] != null){ // 10
					imovelRelatorioHelper.setNumeroQuadra(((Integer) contasDadosRelatorio[10]));
				}
				// lote
				if(contasDadosRelatorio[11] != null){ // 11
					imovelRelatorioHelper.setNumeroLote(((Short) contasDadosRelatorio[11]));
				}
				// sub lote
				if(contasDadosRelatorio[12] != null){ // 12
					imovelRelatorioHelper.setNumeroSubLote(((Short) contasDadosRelatorio[12]));
				}
				// descricao ligacao agua situacao
				if(contasDadosRelatorio[13] != null){ // 13
					imovelRelatorioHelper.setLigacaoAguaSituacao((Integer) contasDadosRelatorio[13] + "");
				}
				// descricao ligacao esgoto situacao
				if(contasDadosRelatorio[14] != null){ // 14
					imovelRelatorioHelper.setLigacaoEsgotoSituacao((Integer) contasDadosRelatorio[14] + "");
				}
				// percentual
				if(contasDadosRelatorio[15] != null){ // 15
					imovelRelatorioHelper.setPercentual((BigDecimal) contasDadosRelatorio[15]);
				}
				// data ligação esgoto
				if(contasDadosRelatorio[16] != null){ // 16
					imovelRelatorioHelper.setDataLigacaoEsgoto((Date) contasDadosRelatorio[16]);
				}
				// data ligação água
				if(contasDadosRelatorio[17] != null){ // 17
					imovelRelatorioHelper.setDataLigacaoAgua((Date) contasDadosRelatorio[17]);
				}
				// código cliente usuario
				if(contasDadosRelatorio[18] != null){ // 18
					imovelRelatorioHelper.setClienteUsuarioId((Integer) contasDadosRelatorio[18]);
				}
				// nome cliente usuario
				if(contasDadosRelatorio[19] != null){ // 19
					imovelRelatorioHelper.setClienteUsuarioNome((String) contasDadosRelatorio[19]);
				}
				// nome cliente resposanvel
				if(contasDadosRelatorio[20] != null){ // 20
					imovelRelatorioHelper.setClienteResponsavelId((Integer) contasDadosRelatorio[20]);
				}
				// nome cliente resposanvel
				if(contasDadosRelatorio[21] != null){ // 21
					imovelRelatorioHelper.setClienteResponsavelNome((String) contasDadosRelatorio[21]);
				}
				// nome logradouro
				if(contasDadosRelatorio[22] != null){ // 22
					imovelRelatorioHelper.setNomeLogradouro(((String) contasDadosRelatorio[22]));
				}
				// tipo logradouro
				if(contasDadosRelatorio[23] != null){ // 23
					imovelRelatorioHelper.setTipoLogradouro((String) contasDadosRelatorio[23]);
				}
				// titulo logradouro
				if(contasDadosRelatorio[24] != null){ // 24
					imovelRelatorioHelper.setTituloLogradouro((String) contasDadosRelatorio[24]);
				}
				// cep
				if(contasDadosRelatorio[25] != null){ // 25
					Cep cepImovel = new Cep();
					cepImovel.setCodigo((Integer) contasDadosRelatorio[25]);
					imovelRelatorioHelper.setCepFormatado(cepImovel.getCepFormatado());
				}
				// endereço referência
				if(contasDadosRelatorio[26] != null){ // 26
					imovelRelatorioHelper.setComplementoImovel((String) contasDadosRelatorio[26]);
				}

				// comlplemente endereco
				if(contasDadosRelatorio[27] != null){ // 27
					imovelRelatorioHelper.setComplementoImovel((String) contasDadosRelatorio[27]);
				}

				// número imóvel
				if(contasDadosRelatorio[28] != null){ // 28
					imovelRelatorioHelper.setNumeroImovel((String) contasDadosRelatorio[28]);
				}
				// nome bairro
				if(contasDadosRelatorio[29] != null){ // 29
					imovelRelatorioHelper.setNomeBairro((String) contasDadosRelatorio[29]);
				}
				// nome município
				if(contasDadosRelatorio[30] != null){ // 30
					imovelRelatorioHelper.setNomeMunicipio((String) contasDadosRelatorio[30]);
				}
				// sigla uf
				if(contasDadosRelatorio[31] != null){ // 31
					imovelRelatorioHelper.setUnidadeFederacao((String) contasDadosRelatorio[31]);
				}
				// indicador imóvel condomínio
				if(contasDadosRelatorio[32] != null){ // 32
					imovelRelatorioHelper.setIndicadorImovelCondominio(((Integer) contasDadosRelatorio[32]).shortValue());
				}

				// número moradores
				if(contasDadosRelatorio[33] != null){ // 33
					imovelRelatorioHelper.setNumeroMoradores(((Integer) contasDadosRelatorio[33]).shortValue());
				}
				// matrícula imóvel condomínio
				if(contasDadosRelatorio[34] != null){ // 34
					imovelRelatorioHelper.setMatriculaImovelCondominio((Integer) contasDadosRelatorio[34]);
				}
				// matrícula imóvel principal
				if(contasDadosRelatorio[35] != null){ // 35
					imovelRelatorioHelper.setMatriculaImovelPrincipal((Integer) contasDadosRelatorio[35]);
				}
				// número pontos utilização
				if(contasDadosRelatorio[36] != null){ // 36
					imovelRelatorioHelper.setNumeroPontosUtilzacao(((Integer) contasDadosRelatorio[36]).shortValue());
				}
				// perfil imóvel
				if(contasDadosRelatorio[37] != null){ // 37
					imovelRelatorioHelper.setPerfilImovel((String) contasDadosRelatorio[37]);
				}
				// área construída maior faixa
				if(contasDadosRelatorio[38] != null){ // 38
					imovelRelatorioHelper.setAreaConstruidaMaiorFaixa((Integer) contasDadosRelatorio[38]);
				}
				// área construída menor faixa
				if(contasDadosRelatorio[39] != null){ // 39
					imovelRelatorioHelper.setAreaConstruidaMenorFaixa((Integer) contasDadosRelatorio[39]);
				}
				// área construída
				if(contasDadosRelatorio[40] != null){ // 40
					imovelRelatorioHelper.setAreaConstruidaImovel((BigDecimal) contasDadosRelatorio[40]);
				}
				// pavimento calçada
				if(contasDadosRelatorio[41] != null){ // 41
					imovelRelatorioHelper.setTipoPavimentoCalcada((Integer) contasDadosRelatorio[41] + "");
				}
				// pavimento rua
				if(contasDadosRelatorio[42] != null){ // 42
					imovelRelatorioHelper.setTipoPavimentoRua((Integer) contasDadosRelatorio[42] + "");
				}

				// despejo
				if(contasDadosRelatorio[43] != null){ // 43
					imovelRelatorioHelper.setTipoDespejo((Integer) contasDadosRelatorio[43] + "");
				}
				// volume reservatorio superior menor faixa
				if(contasDadosRelatorio[44] != null){ // 44
					imovelRelatorioHelper.setVolumeReservatorioSuperiorMenorFaixa((BigDecimal) contasDadosRelatorio[44]);
				}
				// volume reservatorio superior maior faixa
				if(contasDadosRelatorio[45] != null){ // 45
					imovelRelatorioHelper.setVolumeReservatorioSuperiorMaiorFaixa((BigDecimal) contasDadosRelatorio[45]);
				}
				// volume reservatorio superior
				if(contasDadosRelatorio[46] != null){ // 46
					imovelRelatorioHelper.setVolumeReservatorioSuperior(new BigDecimal((Integer) contasDadosRelatorio[46]));
				}
				// volume reservatorio inferior menor faixa
				if(contasDadosRelatorio[47] != null){ // 47
					imovelRelatorioHelper.setVolumeReservatorioInferiorMenorFaixa((BigDecimal) contasDadosRelatorio[47]);
				}
				// volume reservatorio inferior maior faixa
				if(contasDadosRelatorio[48] != null){ // 48
					imovelRelatorioHelper.setVolumeReservatorioInferiorMaiorFaixa((BigDecimal) contasDadosRelatorio[48]);
				}
				// volume reservatorio inferior
				if(contasDadosRelatorio[49] != null){ // 49
					imovelRelatorioHelper.setVolumeReservatorioInferior(new BigDecimal((Integer) contasDadosRelatorio[49]));
				}
				// volume piscina menor faixa
				if(contasDadosRelatorio[50] != null){ // 50
					imovelRelatorioHelper.setVolumePiscinaMenorFaixa((BigDecimal) contasDadosRelatorio[50]);
				}
				// volume piscina maior faixa
				if(contasDadosRelatorio[51] != null){ // 51
					imovelRelatorioHelper.setVolumePiscinaMaiorFaixa((BigDecimal) contasDadosRelatorio[51]);
				}
				// volume piscina
				if(contasDadosRelatorio[52] != null){ // 52
					imovelRelatorioHelper.setVolumePiscina(new BigDecimal((Integer) contasDadosRelatorio[52]));
				}

				// tipo de poço
				if(contasDadosRelatorio[53] != null){ // 53
					imovelRelatorioHelper.setDescricaoTipoPoco(((String) contasDadosRelatorio[53]));
				}
				// diâmetro da ligação de água
				if(contasDadosRelatorio[54] != null){ // 54
					imovelRelatorioHelper.setDiametroLigacaoAgua(contasDadosRelatorio[54] + "");
				}
				// material da ligação de água
				if(contasDadosRelatorio[55] != null){ // 55
					imovelRelatorioHelper.setMaterialLigacaoAgua((String) contasDadosRelatorio[55]);
				}
				// diâmetro da ligação de esgoto
				if(contasDadosRelatorio[56] != null){ // 56
					imovelRelatorioHelper.setDiametroLigacaoEsgoto(contasDadosRelatorio[56] + "");
				}
				// material da ligação de esgoto
				if(contasDadosRelatorio[57] != null){ // 57
					imovelRelatorioHelper.setMaterialLigacaoEsgoto((String) contasDadosRelatorio[57]);
				}
				// consumo mínimo esgoto
				if(contasDadosRelatorio[58] != null){ // 58
					imovelRelatorioHelper.setConsumoMinimoFixadoLigacaoEsgoto((Integer) contasDadosRelatorio[58]);
				}
				// percentual água coletada
				if(contasDadosRelatorio[59] != null){ // 59
					imovelRelatorioHelper.setPercentualAguaConsumidaColetada((BigDecimal) contasDadosRelatorio[59]);
				}
				// percentual esgoto
				if(contasDadosRelatorio[60] != null){ // 60
					imovelRelatorioHelper.setPercentual((BigDecimal) contasDadosRelatorio[60]);
				}
				// número hidrômetro água
				if(contasDadosRelatorio[61] != null){ // 61
					imovelRelatorioHelper.setNumeroHidrometroAgua((String) contasDadosRelatorio[61]);
				}
				// ano fabricação hidrômetro água
				if(contasDadosRelatorio[62] != null){ // 62
					imovelRelatorioHelper.setAnoFabricaocaHidrometroAgua(((Integer) contasDadosRelatorio[62]).shortValue());
				}
				// capacidade hidrômetro água
				if(contasDadosRelatorio[63] != null){ // 63
					imovelRelatorioHelper.setCapacidadeHidrometroAgua(contasDadosRelatorio[63] + "");
				}
				// marca hidrômetro água
				if(contasDadosRelatorio[64] != null){ // 64
					imovelRelatorioHelper.setMarcaHidrometroAgua(contasDadosRelatorio[64] + "");
				}
				// diâmetro hidrômetro água
				if(contasDadosRelatorio[65] != null){ // 65
					imovelRelatorioHelper.setDiametroHidrometroAgua(contasDadosRelatorio[65] + "");
				}
				// tipo hidrômetro água
				if(contasDadosRelatorio[66] != null){ // 66
					imovelRelatorioHelper.setTipoHidrometroAgua(contasDadosRelatorio[66] + "");
				}
				// data instalação hidrômetro água
				if(contasDadosRelatorio[67] != null){ // 67
					imovelRelatorioHelper.setDataInstalacaoHidrometroAgua((Date) contasDadosRelatorio[67]);
				}
				// local instalação hidrômetro água
				if(contasDadosRelatorio[68] != null){ // 68
					imovelRelatorioHelper.setLocalInstalacaoHidrometroAgua("" + contasDadosRelatorio[68]);
				}
				// proteção hidrômetro água
				if(contasDadosRelatorio[69] != null){ // 69
					imovelRelatorioHelper.setTipoProtecaoHidrometroAgua(contasDadosRelatorio[69] + "");
				}
				// indicador cavalete hidrômetro água
				if(contasDadosRelatorio[70] != null){ // 70
					imovelRelatorioHelper.setIndicadorExistenciaCavaleteAgua(((Integer) contasDadosRelatorio[70]).shortValue());
				}
				// número hidrômetro poço
				if(contasDadosRelatorio[71] != null){ // 71
					imovelRelatorioHelper.setNumeroHidrometroPoco((String) contasDadosRelatorio[71]);
				}
				// ano fabricação hidrômetro poço
				if(contasDadosRelatorio[72] != null){ // 72
					imovelRelatorioHelper.setAnoFabricacaoHidrometroPoco(((Integer) contasDadosRelatorio[72]).shortValue());
				}
				// capacidade hidrômetro poço
				if(contasDadosRelatorio[73] != null){ // 73
					imovelRelatorioHelper.setCapacidadeHidrometroPoco(((String) contasDadosRelatorio[73]));
				}
				// marca hidrômetro poço
				if(contasDadosRelatorio[74] != null){ // 74
					imovelRelatorioHelper.setMarcaHidrometroPoco((String) contasDadosRelatorio[74]);
				}
				// diâmetro hidrômetro poço
				if(contasDadosRelatorio[75] != null){ // 75
					imovelRelatorioHelper.setDiametroHidrometroPoco((String) contasDadosRelatorio[75]);
				}
				// tipo hidrômetro poço
				if(contasDadosRelatorio[76] != null){ // 76
					imovelRelatorioHelper.setTipoHidrometroPoco((String) contasDadosRelatorio[76]);
				}
				// data instalação hidrômetro poço
				if(contasDadosRelatorio[77] != null){ // 77
					imovelRelatorioHelper.setDataInstalacaoHidrometroPoco((Date) contasDadosRelatorio[77]);
				}
				// local instalação hidrômetro poço
				if(contasDadosRelatorio[78] != null){ // 78
					imovelRelatorioHelper.setLocalInstalacaoHidrometroPoco((String) contasDadosRelatorio[78]);
				}
				// proteção hidrômetro poço
				if(contasDadosRelatorio[79] != null){ // 79
					imovelRelatorioHelper.setTipoProtecaoHidrometroPoco((String) contasDadosRelatorio[79]);
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[80] != null){ // 80
					imovelRelatorioHelper.setIndicadorExistenciaCavaletePoco(((Integer) contasDadosRelatorio[80]).shortValue());
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[81] != null){ // 81
					imovelRelatorioHelper.setConsumoMinimoFixadoAgua((Integer) contasDadosRelatorio[81]);
				}
				// indicador cavalete hidrômetro poço
				if(contasDadosRelatorio[82] != null){ // 82
					imovelRelatorioHelper.setConsumoMinimoFixadoLigacaoEsgoto((Integer) contasDadosRelatorio[82]);
				}
				// indicador Jardim
				if(contasDadosRelatorio[83] != null){ // 83
					imovelRelatorioHelper.setJardim(((Short) contasDadosRelatorio[83]).toString());
				}

				// Rota
				if(contasDadosRelatorio[91] != null){ // 91
					imovelRelatorioHelper.setIdRota((Integer) contasDadosRelatorio[91]);
				}

				// Código da Rota
				if(contasDadosRelatorio[84] != null){ // 84
					imovelRelatorioHelper.setCodigoRota((Short) contasDadosRelatorio[84]);
				}

				// Número Sequencial Rota
				if(contasDadosRelatorio[85] != null){ // 85
					imovelRelatorioHelper.setNumeroSequencialRota((Integer) contasDadosRelatorio[85]);
				}

				// Tipo Faturamento
				if(contasDadosRelatorio[86] != null){ // 86
					imovelRelatorioHelper.setTipoFaturamento(contasDadosRelatorio[86] + "");
				}

				// Id Logradouro
				if(contasDadosRelatorio[87] != null){ // 87
					imovelRelatorioHelper.setIdLogradouro(contasDadosRelatorio[87] + "");
				}

				// DDD
				if(contasDadosRelatorio[88] != null && contasDadosRelatorio[89] != null){ // 88, 89
					imovelRelatorioHelper.setFone(contasDadosRelatorio[88] + "." + contasDadosRelatorio[89]);
				}

				// Segmento
				if(!Util.isVazioOuBranco(contasDadosRelatorio[90])){
					imovelRelatorioHelper.setSegmento(Util.obterShort(contasDadosRelatorio[90].toString()));
				}

				// consumo Medio
				Integer consumoMedio = getControladorCobranca().pesquisarConsumoMedioConsumoHistoricoImovel(
								Integer.valueOf(imovelRelatorioHelper.getMatriculaImovel()));
				if(consumoMedio != null){
					imovelRelatorioHelper.setConsumoMedioImovel(consumoMedio);
				}

				// Inscrição formatada do imóvel
				Imovel imovel = new Imovel();
				Localidade localidadeImovel = new Localidade();
				localidadeImovel.setId(imovelRelatorioHelper.getIdLocalidade());
				imovel.setLocalidade(localidadeImovel);
				if(Util.isVazioOuBranco(imovelRelatorioHelper.getNumeroQuadra())){

					imovel.setQuadra(new Quadra(0));
					imovel.getQuadra().setNumeroQuadra(0);
				}else{

					imovel.setQuadra(new Quadra());
					imovel.getQuadra().setNumeroQuadra(imovelRelatorioHelper.getNumeroQuadra().intValue());
				}

				// Setor Comercial
				if(!Util.isVazioOuBranco(imovelRelatorioHelper.getCodigoSetorComercial())
								&& imovelRelatorioHelper.getCodigoSetorComercial().intValue() > 0){

					SetorComercial setor = new SetorComercial();
					setor.setCodigo(imovelRelatorioHelper.getCodigoSetorComercial());
					imovel.setSetorComercial(setor);
				}else{

					SetorComercial setor = new SetorComercial();
					setor.setId(0);
					setor.setCodigo(0);
					imovel.setSetorComercial(setor);
				}

				// Número da Rota;
				Integer idRota = imovelRelatorioHelper.getIdRota();

				if(idRota != null){
					Rota rota = new Rota();
					rota.setId(idRota);

					imovel.setRota(rota);
				}

				// Número do Segmento
				if(!Util.isVazioOuBranco(imovelRelatorioHelper.getSegmento())){
					imovel.setNumeroSegmento(imovelRelatorioHelper.getSegmento());
				}else{
					imovel.setNumeroSegmento(Util.obterShort("0"));
				}

				// Número do Lote
				if(!Util.isVazioOuBranco(imovelRelatorioHelper.getNumeroLote()) && imovelRelatorioHelper.getNumeroLote() > 0){
					imovel.setLote(imovelRelatorioHelper.getNumeroLote());
				}else{
					imovel.setLote(Util.obterShort("0"));
				}

				// Número do Sublote
				if(!Util.isVazioOuBranco(imovelRelatorioHelper.getNumeroSubLote()) && imovelRelatorioHelper.getNumeroSubLote() > 0){
					imovel.setSubLote(imovelRelatorioHelper.getNumeroSubLote());
				}else{
					imovel.setSubLote(Util.obterShort("0"));
				}

				// inscricao formatada do imovel
				imovelRelatorioHelper.setInscricaoImovel(imovel.getInscricaoFormatada());

				// obter endereco
				imovelRelatorioHelper.setEndereco(getControladorEndereco().pesquisarEndereco(
								Integer.valueOf(imovelRelatorioHelper.getMatriculaImovel())));

				Collection colecaoSubcategoria = this.pesquisarSubcategoriasImovelRelatorio(imovelRelatorioHelper.getMatriculaImovel());

				// ------subcategoria
				String[] arraySubcatgegoriasQtdEconomias = new String[colecaoSubcategoria.size()];
				if(!colecaoSubcategoria.isEmpty()){

					Iterator iterator = colecaoSubcategoria.iterator();

					int i = 0;

					while(iterator.hasNext()){

						Object[] arraySubcategoria = (Object[]) iterator.next();

						arraySubcatgegoriasQtdEconomias[i] = arraySubcategoria[0].toString() + "/" + arraySubcategoria[1].toString();

						i++;

					}
					imovelRelatorioHelper.setSubcategoriaQtdEconomia(arraySubcatgegoriasQtdEconomias);
				}

				// [UC0307 Obter Indicador de Existência de Hidrômetro no
				// Imóvel]
				imovelRelatorioHelper.setIndicadorExistenciaHidrometro(obterIndicadorExistenciaHidrometroImovel(imovelRelatorioHelper
								.getMatriculaImovel()));

				// [UC123 Obter Descrições da Categoria do Imóvel]
				imovelRelatorioHelper.setDescricaoAbreviadaCategoria(obterDescricoesCategoriaImovel(
								new Imovel(imovelRelatorioHelper.getMatriculaImovel())).getDescricaoAbreviada());

				colecaoGerarRelatorioImovelOutrosCriterios.add(imovelRelatorioHelper);
			}
		}

		return colecaoGerarRelatorioImovelOutrosCriterios;

	}

	/**
	 * Filtra o Pagamento pelo seu id carregando os dados necessários
	 * [UC0549] Consultar Dados do Pagamento
	 * 
	 * @author Kássia Albuquerque
	 * @date 12/07/2007
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoPeloId(Integer idPagamento) throws ControladorException{

		try{

			return repositorioImovel.pesquisarPagamentoPeloId(idPagamento);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Filtra o Pagamento Historico pelo seu id carregando os dados necessários
	 * [UC0549] Consultar Dados do Pagamento
	 * 
	 * @author Kássia Albuquerque
	 * @date 12/07/2007
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoPeloId(Integer idPagamentoHistorico) throws ControladorException{

		try{

			return repositorioImovel.pesquisarPagamentoHistoricoPeloId(idPagamentoHistorico);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0549] Consultar Dados de um Pagamento
	 * 
	 * @author Kassia Albuquerque
	 * @date 05/07/2007
	 * @throws ControladorException
	 */
	public Cliente obterDescricaoIdCliente(Integer idImovel) throws ControladorException{

		Cliente cliente = null;

		try{
			Object[] objetoCliente = this.repositorioImovel.obterDescricaoIdCliente(idImovel);

			if(objetoCliente != null){
				cliente = new Cliente();
				cliente.setId((Integer) objetoCliente[0]);
				cliente.setNome((String) objetoCliente[1]);
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return cliente;
	}

	/**
	 * [UC0549] Consultar Dados de um Pagamento
	 * 
	 * @author Kassia Albuquerque
	 * @date 09/07/2007
	 * @throws ControladorException
	 */
	public String pesquisarNomeAgenteArrecadador(Integer idPagamentoHistorico) throws ControladorException{

		String nomeCliente = null;

		try{
			String objetoNomeCliente = this.repositorioImovel.pesquisarNomeAgenteArrecadador(idPagamentoHistorico);

			if(objetoNomeCliente != null){

				nomeCliente = objetoNomeCliente;
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return nomeCliente;
	}

	/**
	 * Obtém a o 117º caracter de uma String
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/07/2007
	 * @return String
	 * @throws ControladorException
	 */

	public String pesquisarCaracterRetorno(Integer idConteudoRegistro) throws ControladorException{

		try{

			return repositorioImovel.pesquisarCaracterRetorno(idConteudoRegistro);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0623] - GERAR RESUMO DE METAS EXECUTDO NO MÊS(CAERN)
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2007
	 */
	public ImovelSubcategoria obterPrincipalSubcategoriaComCodigoGrupo(Integer idCategoria, Integer idImovel) throws ControladorException{

		try{
			Object[] dadosSubCategorias = repositorioImovel.obterSubCategoriasComCodigoGrupoPorCategoria(idCategoria, idImovel);

			ImovelSubcategoria subcategoriaPrincipal = new ImovelSubcategoria();

			if(dadosSubCategorias != null){
				Integer idSubCategoria = (Integer) dadosSubCategorias[0];
				Short quantidadeEconomias = (Short) dadosSubCategorias[1];
				Date dataUltimaAlteracao = (Date) dadosSubCategorias[2];
				Integer codigoGrupoSubcategoria = (Integer) dadosSubCategorias[3];
				Subcategoria subcategoria = new Subcategoria();
				subcategoria.setId(idSubCategoria);
				if(codigoGrupoSubcategoria != null){
					subcategoria.setCodigoGrupoSubcategoria(codigoGrupoSubcategoria);
				}
				ImovelSubcategoriaPK imovelSubcategoriaPK = new ImovelSubcategoriaPK();
				imovelSubcategoriaPK.setSubcategoria(subcategoria);
				subcategoriaPrincipal.setComp_id(imovelSubcategoriaPK);
				subcategoriaPrincipal.setUltimaAlteracao(dataUltimaAlteracao);
				subcategoriaPrincipal.setQuantidadeEconomias(quantidadeEconomias);
			}

			return subcategoriaPrincipal;
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Obtém a principal categoria da Conta
	 * [UC0000] Obter Principal Categoria da Conta
	 * 
	 * @author Ivan Sérgio
	 * @date 08/08/2007
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */
	public Categoria obterPrincipalCategoriaConta(Integer idConta) throws ControladorException{

		// Cria a variável que vai armazenar a categoria principal da Conta
		Categoria categoriaPrincipal = null;

		// Cria a coleção que vai armazenar as categorias com maiorquantidade de
		// economias
		Collection<Categoria> colecaoCategoriasComMaiorQtdEconomias = new ArrayList();

		// Obtém a quantidade de economias por categoria
		Collection<Categoria> colecaoCategoriasConta = this.obterQuantidadeEconomiasContaCategoria(idConta);

		// Inicializa a quantidade de categoria
		int quantidadeCategoria = -1;

		// Caso a coleção de categorias da Conta não esteja nula
		if(colecaoCategoriasConta != null){
			// Laço para verificar qual a categoria com maior quantidade de
			// economia
			for(Categoria categoriaConta : colecaoCategoriasConta){
				if(quantidadeCategoria < categoriaConta.getQuantidadeEconomiasCategoria().intValue()){
					quantidadeCategoria = categoriaConta.getQuantidadeEconomiasCategoria().intValue();

					colecaoCategoriasComMaiorQtdEconomias = new ArrayList();
					colecaoCategoriasComMaiorQtdEconomias.add(categoriaConta);
				}else if(quantidadeCategoria == categoriaConta.getQuantidadeEconomiasCategoria().intValue()){
					colecaoCategoriasComMaiorQtdEconomias.add(categoriaConta);
				}
			}
		}

		// Caso só exista um objeto na coleção, recuperar a categoria e atribui
		// a
		// categoria principal
		// Caso contrário recupera a categoria com o menor id
		if(colecaoCategoriasComMaiorQtdEconomias.size() == 1){
			categoriaPrincipal = colecaoCategoriasComMaiorQtdEconomias.iterator().next();
		}else if(colecaoCategoriasComMaiorQtdEconomias.size() > 1){
			int idTemp = -1;
			for(Categoria categoriaImovel : colecaoCategoriasComMaiorQtdEconomias){
				if(idTemp == -1){
					idTemp = categoriaImovel.getId().intValue();
					categoriaPrincipal = categoriaImovel;
				}else if(categoriaImovel.getId().intValue() < idTemp){
					idTemp = categoriaImovel.getId().intValue();
					categoriaPrincipal = categoriaImovel;
				}

				/*
				 * if (idTemp < categoriaImovel.getId().intValue()) { idTemp =
				 * categoriaImovel.getId().intValue(); categoriaPrincipal =
				 * categoriaImovel; }
				 */
			}
		}

		// Retorna a categoria principal
		return categoriaPrincipal;
	}

	/**
	 * @author Sávio Luiz
	 * @date 24/08/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarSequencialRota(Integer idImovel) throws ControladorException{

		try{
			return repositorioImovel.pesquisarSequencialRota(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
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

	/**
	 * [UC0541] Emitir 2 Via de Conta Internet
	 * 
	 * @author Vivianne Sousa
	 * @date 02/09/2007
	 * @throws ErroRepositorioException
	 */

	public Imovel pesquisarDadosImovel(Integer idImovel) throws ControladorException{

		try{
			return repositorioImovel.pesquisarDadosImovel(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Permite Pesquisar as categorias do Imóvel [UC0394] Gerar Débitos a Cobrar
	 * de Doações
	 * 
	 * @author César Araújo
	 * @date 10/09/2006
	 * @param Imovel
	 *            imovel - objeto imovel
	 * @return Collection<Categoria> - Coleção de categorias
	 * @throws ErroRepositorioException
	 */

	public Collection<Categoria> pesquisarCategoriasImovel(Imovel imovel) throws ControladorException{

		try{
			return repositorioImovel.pesquisarCategoriasImovel(imovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição ,de rota e endereço para exibição.
	 * 
	 * @author Vivianne Sousa
	 * @date 06/11/2007
	 */

	public Collection pesquisarDadosImovel(String idsImovel) throws ControladorException{

		try{

			Collection colecaoRelatorioAnaliseConsumoHelper = null;

			Collection colecaoImoveleRota = new ArrayList();
			if(idsImovel.length() < 1000){
				colecaoImoveleRota = repositorioImovel.pesquisarInscricaoImoveleRota(idsImovel);
			}else{
				String novoIdsImoveis = "";
				String[] idsImoveis = idsImovel.split(",");
				int contador = 0;
				Collection temp;
				for(String string : idsImoveis){
					novoIdsImoveis = novoIdsImoveis + "," + string;
					contador++;
					if(contador % 1000 == 0){
						novoIdsImoveis = novoIdsImoveis.substring(1);
						// if (colecaoImoveleRota == null){
						// colecaoImoveleRota = new ArrayList();
						// }
						temp = repositorioImovel.pesquisarInscricaoImoveleRota(novoIdsImoveis);
						if(temp != null && !temp.equals("")){
							colecaoImoveleRota.addAll(temp);
						}
						novoIdsImoveis = "";
					}
				}
				if(contador > 0){
					novoIdsImoveis = novoIdsImoveis.substring(1);
					temp = repositorioImovel.pesquisarInscricaoImoveleRota(novoIdsImoveis);
					if(temp != null && !temp.equals("")){
						colecaoImoveleRota.addAll(temp);
					}
				}
			}

			if(colecaoImoveleRota != null && !colecaoImoveleRota.isEmpty()){

				Iterator iteratorColecaoImoveiseRota = colecaoImoveleRota.iterator();
				colecaoRelatorioAnaliseConsumoHelper = new ArrayList();

				RelatorioAnaliseConsumoHelper relatorioAnaliseConsumoHelper = null;
				while(iteratorColecaoImoveiseRota.hasNext()){

					Object[] imoveleRota = (Object[]) iteratorColecaoImoveiseRota.next();
					relatorioAnaliseConsumoHelper = new RelatorioAnaliseConsumoHelper();

					// id imovel
					if(imoveleRota[0] != null){
						relatorioAnaliseConsumoHelper.setMatriculaImovel(String.valueOf(imoveleRota[0]));

						relatorioAnaliseConsumoHelper.setEndereco(getControladorEndereco().pesquisarEnderecoFormatado(
										(Integer) imoveleRota[0]));

						Categoria categoria = this.obterPrincipalCategoriaImovel((Integer) imoveleRota[0]);
						relatorioAnaliseConsumoHelper.setCategoria(categoria.getDescricaoAbreviada());
					}

					// id localidade
					if(imoveleRota[1] != null){
						relatorioAnaliseConsumoHelper.setIdLocalidade(String.valueOf(imoveleRota[1]));
					}

					// codigo do setor comercial
					if(imoveleRota[2] != null){
						relatorioAnaliseConsumoHelper.setCodigoSetorComercial(String.valueOf(imoveleRota[2]));
					}

					// numer da quadra
					if(imoveleRota[3] != null){
						relatorioAnaliseConsumoHelper.setNumeroQuadra(String.valueOf(imoveleRota[3]));
					}

					// lote
					if(imoveleRota[4] != null){
						relatorioAnaliseConsumoHelper.setLote(String.valueOf(imoveleRota[4]));
					}

					// subLote
					if(imoveleRota[5] != null){
						relatorioAnaliseConsumoHelper.setSubLote(String.valueOf(imoveleRota[5]));
					}

					// sequencial de rota
					if(imoveleRota[6] != null){
						relatorioAnaliseConsumoHelper.setSeqRota(String.valueOf(imoveleRota[6]));
					}

					// codigo de rota
					if(imoveleRota[7] != null){
						relatorioAnaliseConsumoHelper.setRota(String.valueOf(imoveleRota[7]));
					}

					// quantidade de economias
					if(imoveleRota[8] != null){
						relatorioAnaliseConsumoHelper.setQtdEconomias(String.valueOf(imoveleRota[8]));
					}

					Cliente cliente = this
									.pesquisarClienteUsuarioImovel(Integer.valueOf(relatorioAnaliseConsumoHelper.getMatriculaImovel()));

					if(cliente != null){
						if(cliente.getNome() != null && !"".equals(cliente.getNome())){
							relatorioAnaliseConsumoHelper.setUsuario(cliente.getNome());
						}
					}

					colecaoRelatorioAnaliseConsumoHelper.add(relatorioAnaliseConsumoHelper);
				}
			}

			return colecaoRelatorioAnaliseConsumoHelper;

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Sávio Luiz
	 * @created 23/11/2007
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImoveisClientesRelacao(Collection idsCliente, Integer numeroInicial, Integer idSetorComercial)

	throws ControladorException{

		try{
			return repositorioImovel.pesquisarImoveisClientesRelacao(idsCliente, numeroInicial, idSetorComercial);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * @author eduardo henrique
	 * @date 12/11/2008
	 *       Alteração no método para adequação dos campos blob oracle
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarOcorrenciasCadastro(String idImovel)

	throws ControladorException{

		Collection<ImovelCadastroOcorrencia> colecaoResultadoImoveisCadastroOcorrencia = null;
		Collection<ImovelCadastroOcorrencia> colecaoImoveisCadastroOcorrencia = new ArrayList<ImovelCadastroOcorrencia>();

		try{
			colecaoResultadoImoveisCadastroOcorrencia = repositorioImovel.pesquisarOcorrenciasCadastro(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		for(Iterator iterator = colecaoResultadoImoveisCadastroOcorrencia.iterator(); iterator.hasNext();){
			Object[] objeto = (Object[]) iterator.next();
			ImovelCadastroOcorrencia imovelCadastroOcorrencia = new ImovelCadastroOcorrencia();

			if(objeto[0] != null){
				CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
				cadastroOcorrencia.setDescricao("" + objeto[0]);
				imovelCadastroOcorrencia.setCadastroOcorrencia(cadastroOcorrencia);

			}

			if(objeto[1] != null){
				imovelCadastroOcorrencia.setDataOcorrencia((Date) objeto[1]);
			}

			if(objeto[2] != null){
				Funcionario funcionario = new Funcionario();
				funcionario.setNome("" + objeto[2]);
				imovelCadastroOcorrencia.setFuncionario(funcionario);
			}

			if(objeto[3] != null){
				imovelCadastroOcorrencia.setFotoOcorrencia((byte[]) objeto[3]);
			}

			if(objeto[4] != null){
				imovelCadastroOcorrencia.setId((Integer) objeto[4]);
			}

			colecaoImoveisCadastroOcorrencia.add(imovelCadastroOcorrencia);
		}

		return colecaoImoveisCadastroOcorrencia;

	}

	/**
	 * @author eduardo henrique
	 * @date 12/11/2008
	 *       Alteração no método para adequação dos campos blob oracle
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarEloAnormalidade(String idImovel)

	throws ControladorException{

		Collection<ImovelEloAnormalidade> colecaoResultadoImoveisEloAnormalidade = null;
		Collection<ImovelEloAnormalidade> colecaoImoveisEloAnormalidade = new ArrayList<ImovelEloAnormalidade>();

		try{
			colecaoResultadoImoveisEloAnormalidade = repositorioImovel.pesquisarEloAnormalidade(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		for(Iterator iterator = colecaoResultadoImoveisEloAnormalidade.iterator(); iterator.hasNext();){
			Object[] objeto = (Object[]) iterator.next();
			ImovelEloAnormalidade imovelEloAnormalidade = new ImovelEloAnormalidade();

			if(objeto[0] != null){
				EloAnormalidade eloAnormalidade = new EloAnormalidade();
				eloAnormalidade.setDescricao("" + objeto[0]);
				imovelEloAnormalidade.setEloAnormalidade(eloAnormalidade);

			}

			if(objeto[1] != null){
				imovelEloAnormalidade.setDataAnormalidade((Date) objeto[1]);
			}

			if(objeto[2] != null){
				Funcionario funcionario = new Funcionario();
				funcionario.setNome("" + objeto[2]);
				imovelEloAnormalidade.setFuncionario(funcionario);
			}

			if(objeto[3] != null){
				imovelEloAnormalidade.setFotoAnormalidade((byte[]) objeto[3]);
			}

			if(objeto[4] != null){
				imovelEloAnormalidade.setId((Integer) objeto[4]);
			}

			colecaoImoveisEloAnormalidade.add(imovelEloAnormalidade);
		}

		return colecaoImoveisEloAnormalidade;

	}

	/**
	 * Pesquisa todos os imóveis de determinada rota .
	 * 
	 * @author eduardo henrique
	 * @date 15/10/2008
	 * @param idRota
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Imovel> pesquisarImoveisPorRotaComLocalidade(Integer idRota) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImoveisPorRotaComLocalidade(idRota);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Método responsável por obter o proprietário do imóvel.
	 * 
	 * @author Virgínia Melo
	 * @date 04/06/2009
	 * @param idImovel
	 * @return ClienteImovel
	 */
	public ClienteImovel pesquisarClienteProprietarioImovel(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarClienteProprietarioImovel(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	public Integer verificarExistenciaImovelParaCliente(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.verificarExistenciaImovelParaCliente(idImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Atualiza a situação de cobrança do imóvel
	 */
	public void atualizarSituacaoCobrancaImovel(Integer idSituacaoCobranca, Integer idImovel) throws ControladorException{

		try{
			repositorioImovel.atualizarSituacaoCobrancaImovel(idSituacaoCobranca, idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Consulta se uma conta já existe no histórico como parcelada, evitando assim a geração da
	 * conta de novo
	 * 
	 * @param imovelId
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Object pesquisarImovelIdComContaHistoricoParcelado(Integer imovelId, Integer anoMesReferencia) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelIdComContaHistoricoParcelado(imovelId, anoMesReferencia);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Collection<Object[]> pesquisarImoveisPorRotaCriterioCobranca(Integer idRota, Integer idCriterioCobranca)
					throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImoveisPorRotaCriterioCobranca(idRota, idCriterioCobranca);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	public Integer consultarClienteRelacaoTipoPorImovel(Integer idImovel, Short idClienteRelacaoTipo) throws ControladorException{

		try{
			return this.repositorioImovel.consultarClienteRelacaoTipoPorImovel(idImovel, idClienteRelacaoTipo);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Collection<Object[]> pesquisarImoveisPorSetorComercialCriterioCobranca(Integer idSetorComercial, Integer idCriterioCobranca,
					Date dataCortado, Integer idGrupoCobranca, Integer[] idsTipoDocumentoAIgnorar, Integer idAcaoCobranca,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImoveisPorSetorComercialCriterioCobranca(idSetorComercial, idCriterioCobranca,
							dataCortado, idGrupoCobranca, idsTipoDocumentoAIgnorar, idAcaoCobranca, cobrancaAcaoAtividadeComando);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Obtem o ConsumoFaixaAreaCategoria da categoria com a area informada
	 * 
	 * @author isilva
	 * @date 21/01/2011
	 * @param idCategoria
	 * @param area
	 * @return
	 * @throws ControladorException
	 */
	public ConsumoFaixaAreaCategoria pesquisarConsumoFaixaAreaCategoriaPorCategoriaArea(Integer idCategoria, Integer area)
					throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarConsumoFaixaAreaCategoriaPorCategoriaArea(idCategoria, area);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [FS0003] - Verificar se imóvel já esta suprimido Judicialmente.
	 * 
	 * @author isilva
	 * @date 08/02/2011
	 * @return
	 * @throws ControladorException
	 */
	public Boolean verificarImovelSuprimidoJudicial(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.verificarImovelSuprimidoJudicial(idImovel);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [FS0003] - Suprimido Judicialmente a Ligação de Esgoto de um Imóvel.
	 * 
	 * @author isilva
	 * @date 08/02/2011
	 * @param idImovel
	 * @param usuario
	 * @return
	 */
	public Integer efetuarSuprimirImovelEsgotoJudicial(Integer idImovel, Usuario usuario) throws ControladorException{

		Integer idImovelEsgotoJudicial = null;

		try{
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()));

			if(imovel == null){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Imóvel");
			}

			Collection idClientesRelacao = this.repositorioClienteImovel.retornaClientesRelacao(idImovel);

			if(idClientesRelacao == null || idClientesRelacao.isEmpty()){
				throw new ControladorException("atencao.cliente.imovel.usuario.com.relacao.nao.existe", null, "Proprietário");
			}

			// [FS0003] - Verificar se imóvel já esta suprimido Judicialmente.
			Boolean existeSuprimido = this.verificarImovelSuprimidoJudicial(idImovel);

			if(existeSuprimido){
				throw new ControladorException("atencao.imovel.ja.suprimido.judicialmente");
			}
			if(!LigacaoEsgotoSituacao.LIG_FORA_DE_USO.equals(-1)){
				ImovelEsgotoJudicial imovelEsgotoJudicial = new ImovelEsgotoJudicial();

				imovelEsgotoJudicial.setImovel(imovel);
				imovelEsgotoJudicial.setLigacaoEsgotoSituacaoInclusao(imovel.getLigacaoEsgotoSituacao());

				Date dataCorrente = new Date();

				SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
				imovelEsgotoJudicial.setAnoMesProximoFaturamentoInclusao(Util.somaUmMesAnoMesReferencia(sistemaParametro
								.getAnoMesFaturamento()));
				imovelEsgotoJudicial.setDataInclusao(dataCorrente);
				imovelEsgotoJudicial.setLigacaoEsgotoSituacaoExclusao(null);
				imovelEsgotoJudicial.setAnoMesProximoFaturamentoExclusao(null);
				imovelEsgotoJudicial.setDataExclusao(null);
				imovelEsgotoJudicial.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
				imovelEsgotoJudicial.setUltimaAlteracao(dataCorrente);

				// Início - Registrando as transações
				RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE,
								imovelEsgotoJudicial.getImovel().getId(), imovelEsgotoJudicial.getId(), new UsuarioAcaoUsuarioHelper(
												usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				imovelEsgotoJudicial.setOperacaoEfetuada(operacaoEfetuada);
				imovelEsgotoJudicial.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(imovelEsgotoJudicial);
				// Fim - Registrando as transações

				idImovelEsgotoJudicial = (Integer) getControladorUtil().inserir(imovelEsgotoJudicial);
				imovelEsgotoJudicial.setId(idImovelEsgotoJudicial);

				// Atualiza a situação da ligação de esgoto do imóvel
				repositorioLigacaoEsgoto.atualizarLigacaoEsgotoSituacaoImovel(imovel.getId(), LigacaoEsgotoSituacao.LIG_FORA_DE_USO);
			}else{
				throw new ControladorException("atencao.imovel.ligacao_esgoto.inexistente");
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return idImovelEsgotoJudicial;

	}

	/**
	 * [FS0003] - Religacao Suprimido Judicialmente a Ligação de Esgoto de um Imóvel.
	 * 
	 * @author isilva
	 * @date 08/02/2011
	 * @param idImovel
	 * @param usuario
	 * @return
	 */
	public void efetuarReligacaoSuprimirImovelEsgotoJudicial(Integer idImovel, Usuario usuario) throws ControladorException{

		try{

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()));

			if(imovel == null){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Imóvel");
			}

			Collection idClientesRelacao = this.repositorioClienteImovel.retornaClientesRelacao(idImovel);

			if(idClientesRelacao == null || idClientesRelacao.isEmpty()){
				throw new ControladorException("atencao.cliente.imovel.usuario.com.relacao.nao.existe", null, "Proprietário");
			}

			ImovelEsgotoJudicial imovelEsgotoJudicial = null;

			// [FS0003] - Verificar se imóvel já esta suprimido Judicialmente.
			FiltroImovelEsgotoJudicial filtroImovelEsgotoJudicial = new FiltroImovelEsgotoJudicial();
			filtroImovelEsgotoJudicial.adicionarParametro(new ParametroSimples(FiltroImovelEsgotoJudicial.IMOVEL_ID, Integer
							.valueOf(idImovel)));
			filtroImovelEsgotoJudicial.adicionarParametro(new ParametroSimples(FiltroImovelEsgotoJudicial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroImovelEsgotoJudicial.adicionarParametro(new ParametroNulo(FiltroImovelEsgotoJudicial.DATAEXCLUSAO));

			Collection colecaoImovelEsgotoJudicial = this.getControladorUtil().pesquisar(filtroImovelEsgotoJudicial,
							ImovelEsgotoJudicial.class.getName());

			if(colecaoImovelEsgotoJudicial == null || colecaoImovelEsgotoJudicial.isEmpty()){
				throw new ControladorException("atencao.imovel.nao.suprimido.judicialmente");
			}else{
				imovelEsgotoJudicial = (ImovelEsgotoJudicial) Util.retonarObjetoDeColecao(colecaoImovelEsgotoJudicial);
			}

			if(imovelEsgotoJudicial != null){

				Date dataCorrente = new Date();

				Integer ligacaoEsgotoSituacaoInclusao = imovelEsgotoJudicial.getLigacaoEsgotoSituacaoInclusao().getId();

				imovelEsgotoJudicial.setImovel(imovel);

				SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
				imovelEsgotoJudicial.setLigacaoEsgotoSituacaoExclusao(imovel.getLigacaoEsgotoSituacao());
				imovelEsgotoJudicial.setAnoMesProximoFaturamentoExclusao(Util.somaUmMesAnoMesReferencia(sistemaParametro
								.getAnoMesFaturamento()));
				imovelEsgotoJudicial.setDataExclusao(dataCorrente);
				imovelEsgotoJudicial.setIndicadorUso(ConstantesSistema.INDICADOR_USO_DESATIVO);
				imovelEsgotoJudicial.setUltimaAlteracao(dataCorrente);

				// Início - Registrando as transações
				RegistradorOperacao registradorOperacao = new RegistradorOperacao(
								Operacao.OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, imovelEsgotoJudicial.getImovel().getId(),
								imovelEsgotoJudicial.getId(), new UsuarioAcaoUsuarioHelper(usuario,
												UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				imovelEsgotoJudicial.setOperacaoEfetuada(operacaoEfetuada);
				imovelEsgotoJudicial.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(imovelEsgotoJudicial);
				// Fim - Registrando as transações

				// Atualiza imovelEsgotoJudicial
				getControladorUtil().atualizar(imovelEsgotoJudicial);

				// Atualiza a situação da ligação de esgoto do imóvel
				repositorioLigacaoEsgoto.atualizarLigacaoEsgotoSituacaoImovel(imovel.getId(), ligacaoEsgotoSituacaoInclusao);
			}else{
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * @author Ailton Sousa
	 * @date 11/02/2011
	 *       Pesquisa uma coleção de imóveis com uma query especifica
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param rota
	 * @param segmento
	 * @param lote
	 * @param sublote
	 * @return Description of the Return Value
	 * @exception ControladorException
	 */
	public Collection pesquisarImovel(Integer idLocalidade, Integer idSetorComercial, Integer idRota, Short segmento, Short lote,
					Short sublote) throws ControladorException{

		Collection colecaoImovelArray = null;
		Collection<Imovel> colecaoRetorno = new ArrayList();

		try{
			colecaoImovelArray = repositorioImovel.pesquisarImovel(idLocalidade, idSetorComercial, idRota, segmento, lote, sublote,
							Imovel.IMOVEL_EXCLUIDO.intValue());
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		Iterator colecaoImovelArrayIt = colecaoImovelArray.iterator();
		Object[] imovelArray;
		Imovel imovel;
		FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();

		while(colecaoImovelArrayIt.hasNext()){

			imovelArray = (Object[]) colecaoImovelArrayIt.next();

			imovel = new Imovel();
			imovel.setId((Integer) imovelArray[0]);
			imovel.setLocalidade((Localidade) imovelArray[1]);
			imovel.setSetorComercial((SetorComercial) imovelArray[2]);
			if(imovelArray[3] != null){
				Integer idRotaAux = (Integer) imovelArray[3];

				Rota rota = new Rota();
				rota.setId(idRotaAux);

				imovel.setRota(rota);
			}
			if(imovelArray[4] != null){
				imovel.setNumeroSegmento(Short.valueOf(String.valueOf(imovelArray[4])));
			}
			imovel.setLote(Short.valueOf(String.valueOf(imovelArray[5])));
			imovel.setSubLote(Short.valueOf(String.valueOf(imovelArray[6])));

			if(imovelArray[7] != null){
				faturamentoSituacaoTipo.setId((Integer) imovelArray[7]);
				imovel.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);
			}

			colecaoRetorno.add(imovel);

		}

		return colecaoRetorno;

	}

	/**
	 * Inserir Vencimento Alternativo
	 * 
	 * @author Hebert Falcão
	 * @created 18/02/2010
	 * @param vencimentoAlternativo
	 * @param imovel
	 * @param cliente
	 * @param novoDiaVencimento
	 * @param usuario
	 * @exception ControladorException
	 */
	public void inserirVencimentoAlternativo(VencimentoAlternativo vencimentoAlternativo, Imovel imovel, Cliente cliente,
					Short novoDiaVencimento, Usuario usuario) throws ControladorException{

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		if(vencimentoAlternativo == null){
			FiltroVencimentoAlternativo filtroVencimentoAlternativo = new FiltroVencimentoAlternativo(
							FiltroVencimentoAlternativo.DATA_IMPLANTACAO);
			filtroVencimentoAlternativo.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.IMOVEL_ID, imovel.getId()));
			filtroVencimentoAlternativo.adicionarParametro(new ParametroNulo(FiltroVencimentoAlternativo.DATA_EXCLUSAO));

			Collection vencimentosAlternativos = getControladorUtil().pesquisar(filtroVencimentoAlternativo,
							VencimentoAlternativo.class.getName());

			if(vencimentosAlternativos != null && !vencimentosAlternativos.isEmpty()){
				Iterator it = vencimentosAlternativos.iterator();

				while(it.hasNext()){
					VencimentoAlternativo vencimentoAlternativoBase = (VencimentoAlternativo) it.next();
					vencimentoAlternativoBase.setCliente(cliente);

					// excluirVencimentoAlternativo(vencimentoAlternativoBase, imovel, usuario);
					vencimentoAlternativoBase.setDateExclusao(new Date());
					getControladorUtil().atualizar(vencimentoAlternativoBase);
				}
			}
		}else{
			Short numeroMesesMinimoVencimentoAlternativo = sistemaParametro.getNumeroMesesMinimoAlteracaoVencimento();

			// verifica se usuario possue permissão especial para informar
			// o vencimento alternativo antes do período válido
			boolean temPermissaoInformarVencimentoAlternativoAntesDoPeriodoValido = getControladorPermissaoEspecial()
							.verificarPermissaoInformarVencimentoAlternativoAntesDoPeriodoValido(usuario);

			Date dataAtual = new Date();
			Date dataImplantacao = vencimentoAlternativo.getDataImplantacao();

			int diferencaMes = 0;
			if(dataImplantacao != null){
				diferencaMes = Util.dataDiff(dataAtual, dataImplantacao);
			}

			if(diferencaMes < numeroMesesMinimoVencimentoAlternativo
							&& (!temPermissaoInformarVencimentoAlternativoAntesDoPeriodoValido && usuario != Usuario.USUARIO_AGENCIA_VIRTUAL)){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.imovel.vencimento.alterado", null,
								numeroMesesMinimoVencimentoAlternativo.toString());
			}else{
				vencimentoAlternativo.setCliente(cliente);

				// excluirVencimentoAlternativo(vencimentoAlternativo, imovel, usuario);
				vencimentoAlternativo.setDateExclusao(new Date());
				getControladorUtil().atualizar(vencimentoAlternativo);
			}
		}

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(new Short(sistemaParametro.getParmId().shortValue()))){
			if(novoDiaVencimento != null){
				FiltroSetorComercialVencimento filtroSetorComercialVencimento = new FiltroSetorComercialVencimento();
				filtroSetorComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.LOCALIDADE_ID, imovel
								.getLocalidade().getId()));
				filtroSetorComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.SETOR_COMERCIAL_ID,
								imovel.getSetorComercial().getId()));
				filtroSetorComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroSetorComercialVencimento.adicionarParametro(new ParametroSimples(FiltroSetorComercialVencimento.DIA_VENCIMENTO,
								novoDiaVencimento));

				Collection<SetorComercialVencimento> colecaoSetorComercialVencimentos = getControladorUtil().pesquisar(
								filtroSetorComercialVencimento, SetorComercialVencimento.class.getName());

				if(colecaoSetorComercialVencimentos == null || colecaoSetorComercialVencimentos.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.pesquisa_inexistente", null, "Novo Dia de Vencimento");
				}
			}
		}

		// Atualizando Imovel
		imovel.setDiaVencimento(novoDiaVencimento);
		imovel.setUltimaAlteracao(new Date());

		this.getControladorUtil().atualizar(imovel);

		// Incluindo Vencimento Alternativo
		VencimentoAlternativo vencimentoAlternativoInserir = new VencimentoAlternativo();
		vencimentoAlternativoInserir.setImovel(imovel);
		vencimentoAlternativoInserir.setDataImplantacao(new Date());
		vencimentoAlternativoInserir.setDateVencimento(novoDiaVencimento);
		vencimentoAlternativoInserir.setDateExclusao(null);
		vencimentoAlternativoInserir.setCliente(cliente);
		vencimentoAlternativoInserir.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		Integer imovelId = imovel.getId();

		RegistradorOperacao registradorOperacaoVencimentoAlternativo = new RegistradorOperacao(
						Operacao.OPERACAO_INSERIR_VENCIMENTO_ALTERNATIVO, imovelId, imovelId, new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_VENCIMENTO_ALTERNATIVO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		vencimentoAlternativoInserir.setOperacaoEfetuada(operacaoEfetuada);
		vencimentoAlternativoInserir.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacaoVencimentoAlternativo.registrarOperacao(vencimentoAlternativoInserir);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		this.getControladorUtil().inserir(vencimentoAlternativoInserir);
	}

	/**
	 * Excluir Vencimento Alternativo
	 * 
	 * @author Hebert Falcão
	 * @created 18/02/2010
	 * @param vencimentoAlternativo
	 * @param imovel
	 * @param usuario
	 * @exception ControladorException
	 */
	public void excluirVencimentoAlternativo(VencimentoAlternativo vencimentoAlternativo, Imovel imovel, Usuario usuario)
					throws ControladorException{

		// Atualizando Imovel
		imovel.setDiaVencimento(null);
		imovel.setUltimaAlteracao(new Date());

		this.getControladorUtil().atualizar(imovel);

		// Atualizando Vencimento Alternativo
		vencimentoAlternativo.setDateExclusao(new Date());
		vencimentoAlternativo.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		Integer imovelId = vencimentoAlternativo.getImovel().getId();

		RegistradorOperacao registradorOperacaoVencimentoAlternativo = new RegistradorOperacao(
						Operacao.OPERACAO_EXCLUIR_VENCIMENTO_ALTERNATIVO, imovelId, imovelId, new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EXCLUIR_VENCIMENTO_ALTERNATIVO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		vencimentoAlternativo.setOperacaoEfetuada(operacaoEfetuada);
		vencimentoAlternativo.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacaoVencimentoAlternativo.registrarOperacao(vencimentoAlternativo);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		this.getControladorUtil().atualizar(vencimentoAlternativo);
	}

	private ControladorPermissaoEspecialLocal getControladorPermissaoEspecial(){

		try{
			// pega a instância do ServiceLocator.
			ServiceLocator locator = ServiceLocator.getInstancia();

			ControladorPermissaoEspecialLocalHome localHome = (ControladorPermissaoEspecialLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_PERMISSAO_ESPECIAL_SEJB);

			// guarda a referencia de um objeto capaz de fazer chamadas remotamente
			ControladorPermissaoEspecialLocal local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * [UC0XXX] Gerar Relatório Boletim de Cadastro
	 * 
	 * @author Anderson Italo
	 * @date 25/04/2011
	 *       Obter Total dos Imóveis pelos parametros informados
	 */
	public Integer pesquisarTotalRegistrosRelatorioBoletimCadastro(FiltrarRelatorioBoletimCadastroHelper filtro)
					throws ControladorException{

		Integer retorno = null;
		try{
			retorno = this.repositorioImovel.pesquisarTotalRegistrosRelatorioBoletimCadastro(filtro);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Obterm o Funcionário que executou a operação em conta
	 * 
	 * @author isilva
	 * @date 02/05/2011
	 * @param idConta
	 * @param tipo
	 * @return
	 */
	public Funcionario obterFuncionarioExecutouOperacaoEmConta(Integer idConta, String tipo) throws ControladorException{

		Funcionario retorno = null;
		try{
			retorno = this.repositorioImovel.obterFuncionarioExecutouOperacaoEmConta(idConta, tipo);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Gerar Relatório Imóveis por Endereço
	 * Obter Total dos Imóveis pelos parametros informados
	 * 
	 * @date 14/06/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioImovelEndereco(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String rotaInicial,
					String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String consumoFixadoEsgotoPocoInicial,
					String consumoFixadoEsgotoPocoFinal) throws ControladorException{

		Integer retorno = null;
		try{

			retorno = this.repositorioImovel.pesquisarTotalRegistrosRelatorioImovelEndereco(idImovelCondominio, idImovelPrincipal,
							idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,
							consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial,
							intervaloValorPercentualEsgotoFinal, intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal,
							intervaloMediaMinimaHidrometroInicial, intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo,
							idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade,
							areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional,
							idLocalidadeInicial, idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal,
							loteOrigem, loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria,
							idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
							idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
							idAreaConstruidaFaixa, idUnidadeNegocio, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal,
							segmentoInicial, segmentoFinal, subloteInicial, subloteFinal, consumoFixadoEsgotoPocoInicial,
							consumoFixadoEsgotoPocoFinal);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0591] - Gerar Relatório de Clientes Especiais
	 * Pesquisas Total de Registros de acordo com os parâmetros da pesquisa
	 * 
	 * @author Anderson Italo
	 * @date 11/07/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioClientesEspeciais(FiltrarRelatorioClientesEspeciaisHelper filtro)
					throws ControladorException{

		Integer retorno = null;
		try{

			retorno = this.repositorioImovel.pesquisarTotalRegistrosRelatorioClientesEspeciais(filtro);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0003] – Obter Grupo do Imóvel
	 * Obtem o ConsumoFaixaAreaCategoria da categoria pelo imovel e categoria.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 * @param idCategoria
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ImovelConsumoFaixaAreaCategoria pesquisarImovelConsumoFaixaAreaCategoriaPorCategoriaImovel(Integer idCategoria, Integer idImovel)
					throws ControladorException{

		ImovelConsumoFaixaAreaCategoria retorno = null;
		try{

			retorno = this.repositorioImovel.pesquisarImovelConsumoFaixaAreaCategoriaPorCategoriaImovel(idCategoria, idImovel);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Verifica se o indicador de debito automático do imovel está ativo.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 */
	public boolean isIndicadorDebitoAutomaticoImovel(Integer idImovel) throws ControladorException{

		boolean retorno = false;
		try{

			retorno = this.repositorioImovel.isIndicadorDebitoAutomaticoImovel(idImovel);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Retorna true caso exista imoveis para a coleçao de rotas recebidas.
	 * 
	 * @author Ailton Sousa
	 * @date 15/09/2011
	 * @param colecaoRotas
	 * @return
	 * @throws ControladorException
	 */
	public boolean isImovelPorColecaoRotas(Collection<Rota> colecaoRotas) throws ControladorException{

		try{
			return this.repositorioImovel.isImovelPorColecaoRotas(colecaoRotas);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public String pesquisarNomeImovel(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarNomeImovel(idImovel);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Obtem os dados da Subcategoria do Imovel para o Relatorio de Dados
	 * Economias para o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection gerarRelatorioDadosEconomiasImovelSubcategoria(String idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.gerarRelatorioDadosEconomiasImovelSubcategoria(idImovel);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Retorna o ImovelSubcategoria com a maior quantidade de economia.
	 * 
	 * @author Ailton Sousa
	 * @date 28/12/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public ImovelSubcategoria pesquisarImovelSubcategoriaComMaiorQuantidadeEconomia(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelSubcategoriaComMaiorQuantidadeEconomia(idImovel);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudança de Rota da Quadra
	 * Método que obtém o total de imóveis com rota igual à rota anterior da quadra.
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 * @throws ControladorException
	 */
	public Integer obterTotalImoveisRotaIgualAnteriorQuadra(Integer idQuadra, Integer idRota) throws ControladorException{

		Integer retorno = null;
		try{

			retorno = this.repositorioImovel.obterTotalImoveisRotaIgualAnteriorQuadra(idQuadra, idRota);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0005] - Verificar Mudança de Distrito Operacional da Quadra
	 * Método que obtém o total de imóveis com distrito operacional igual ao distrito operacional
	 * anterior da quadra.
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ControladorException
	 */
	public Integer obterTotalImoveisDistritoOperacionalIgualAnteriorQuadra(Integer idQuadra, Integer idDistritoOperacional)
					throws ControladorException{

		Integer retorno = null;
		try{

			retorno = this.repositorioImovel.obterTotalImoveisDistritoOperacionalIgualAnteriorQuadra(idQuadra, idDistritoOperacional);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudança de Rota da Quadra
	 * Método que obtém o total de imóveis com rota diferente à rota anterior da quadra.
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 * @throws ControladorException
	 */
	public Integer obterTotalImoveisRotaDiferenteAnteriorQuadra(Integer idQuadra, Integer idRota) throws ControladorException{

		Integer retorno = null;
		try{

			retorno = this.repositorioImovel.obterTotalImoveisRotaDiferenteAnteriorQuadra(idQuadra, idRota);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0005] - Verificar Mudança de Distrito Operacional da Quadra
	 * Método que obtém o total de imóveis com distrito operacional diferente do distrito
	 * operacional anterior da quadra.
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ControladorException
	 */
	public Integer obterTotalImoveisDistritoOperacionalDiferenteAnteriorQuadra(Integer idQuadra, Integer idDistritoOperacional)
					throws ControladorException{

		Integer retorno = null;
		try{

			retorno = this.repositorioImovel.obterTotalImoveisDistritoOperacionalDiferenteAnteriorQuadra(idQuadra, idDistritoOperacional);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0001] - Atualizar Quadra
	 * Método que atualiza a rota dos imóveis pela quadra e rota(informada ou não).
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2012
	 * @throws ErroRepositorioException
	 */
	public void atualizarRotaImoveisPorQuadra(Integer idQuadra, Integer idRotaAtualizar, Integer idRotaAnterior)
					throws ControladorException{

		try{

			this.repositorioImovel.atualizarRotaImoveisPorQuadra(idQuadra, idRotaAtualizar, idRotaAnterior);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0001] - Atualizar Quadra
	 * Método que atualiza o distrito operacional dos imóveis pela quadra e distrito operacional
	 * anterior(informada ou não).
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ControladorException
	 */
	public void atualizarDistritoOperacionalImoveisPorQuadra(Integer idQuadra, Integer idDistritoOperacionalAtualizar,
					Integer idDistritoOperacionalAnterior) throws ControladorException{

		try{

			this.repositorioImovel.atualizarDistritoOperacionalImoveisPorQuadra(idQuadra, idDistritoOperacionalAtualizar,
							idDistritoOperacionalAnterior);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relatório dos Imóveis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2011
	 */
	public Collection pesquisarRelatorioImoveisPorQuadra(Integer idQuadra) throws ControladorException{

		Collection colecaoRetorno = null;
		try{

			colecaoRetorno = this.repositorioImovel.pesquisarRelatorioImoveisPorQuadra(idQuadra);

			if(Util.isVazioOrNulo(colecaoRetorno)){

				throw new ControladorException("atencao.relatorio.vazio");
			}

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relatório dos Imóveis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioImoveisPorQuadra(Integer idQuadra) throws ControladorException{

		Integer retorno = null;
		try{

			retorno = this.repositorioImovel.pesquisarTotalRegistrosRelatorioImoveisPorQuadra(idQuadra);
		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	public Object pesquisarImovelIdComContaHistorico(Integer imovelId, Integer anoMesReferencia) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelIdComContaHistorico(imovelId, anoMesReferencia);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */

	public Collection pesquisarObterQuantidadeEconomias(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarObterQuantidadeEconomias(idImovel);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * 18. Lista de Imóveis (selecionar os imóveis das rotas do comando (a partir da tabela
	 * IMOVEL com ROTA_ID=ROTA_ID da tabela FATURAMENTO_ATIV_CRON_ROTA com FTAC_ID=FTAC_ID da
	 * tabela FATURAMENTO_ATIVIDADE_CRONOGRAMA
	 * 
	 * @param idFaturamentoAtvCron
	 * @param idFaturamentoAtvCron
	 * @return Uma lista de objetos representado o imovel
	 * @throws ControladorException
	 */
	public Collection<Object[]> consultarImoveisAvisoCorte(Integer idSetor, Integer idFaturamentoAtvCron) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImoveisPorFaturamentoAtividadeCronograma(idSetor, idFaturamentoAtvCron);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * @author Saulo Lima
	 * @date 27/06/2012
	 * @param Integer
	 *            idImovel
	 * @return Object[] idLocalidade, idSetorComercial, idBairro
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosLocalizacaoImovel(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarDadosLocalizacaoImovel(idImovel);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Este método é chamado por 2 Casos de Uso diferentes:
	 * [UC0203] Consultar Débitos
	 * [SB0005] - Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * e
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0024] - Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * 
	 * @author Saulo Lima
	 * @date 25/07/2013
	 * @param usuario
	 * @param idImovel
	 * @param colecaoDebitoImovel
	 * @return 0 = (Permite tudo) Não identificou nenhum dos casos
	 *         1 = (Bloqueia tudo) Se tem usuario NÃO tem autorização, imóvel possui débito com
	 *         remuneração Cob. Administ. e último Comando de Ação de Cobrança NÃO é da empresa do
	 *         usuário
	 *         2 = (Bloqueia parcial) Se tem usuario NÃO tem autorização, imóvel possui débito com
	 *         remuneração Cob. Administ. e último Comando de Ação de Cobrança é da empresa do
	 *         usuário
	 *         3 = (Bloqueia tudo) Se tem usuario NÃO tem autorização e imóvel NÃO possui débito com
	 *         remuneração Cob. Administ.
	 *         4 = (Bloqueia parcial) Se tem usuario tem autorização e empresa do usuário tem
	 *         contrato de cobrança
	 * @throws ControladorException
	 */
	public Integer validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, Integer idImovel,
					ObterDebitoImovelOuClienteHelper colecaoDebitoImovel) throws ControladorException{

		Integer retorno = 0;

		// 1.2. Caso o usuário não tenha autorização de acesso ao imóvel
		if(this.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, idImovel,
						ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA).equals(ConstantesSistema.NAO)){

			boolean achouRemuneracao = this.verificarIndicadorRemuneraCobrancaAdministrativa(colecaoDebitoImovel);

			// 1.2.2. Caso algum dos itens das listas retornadas pelo [UC0067] esteja marcado como
			// remunerável
			if(achouRemuneracao){

				CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = this.getControladorCobranca()
								.pesquisarUltimaCobrancaAdministrativaImovel(idImovel);

				// 1.2.2.1. Caso a última cobrança administrativa do imóvel não seja da empresa do
				// usuário
				if(cobrancaAcaoAtividadeComando != null
								&& !cobrancaAcaoAtividadeComando.getEmpresa().getId().equals(usuario.getEmpresa().getId())){

					retorno = 1;
				}else{

					// 1.2.2.2. Caso contrário, ou seja, a última cobrança administrativa do imóvel
					// seja da empresa do usuário
					retorno = 2;
				}

			}else{
				// 1.2.3. Caso contrário, ou seja, o imóvel não tenha débitos remuneráveis:
				retorno = 3;
			}

			// 1.3. Caso contrário, ou seja, caso o usuário tenha autorização de acesso ao imóvel
		}else{

			// 1.3.1. Caso o usuário logado pertença a uma empresa de cobrança administrativa
			if(this.getControladorCobranca().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){
				retorno = 4;
			}
		}

		return retorno;
	}

	/**
	 * [UC0204] Consultar Conta
	 * [SB0001] – Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * 
	 * @author Saulo Lima
	 * @date 14/08/2013
	 * @param usuario
	 * @param idImovel
	 * @param conta
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, Integer idImovel, Conta conta)
					throws ControladorException{

		boolean permissaoAcesso2aVia = true;

		// 1.2. Caso o usuário não tenha autorização de acesso ao imóvel
		if(this.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, idImovel,
						ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA).equals(ConstantesSistema.NAO)){

			// 1.2.2. Caso a conta esteja marcada como remunerável
			if(conta.getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.SIM)){


				CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = this.getControladorCobranca()
								.pesquisarUltimaCobrancaAdministrativaImovel(idImovel);

				// 1.2.2.1. Caso a última cobrança administrativa do imóvel não seja da empresa do
				// usuário
				if(cobrancaAcaoAtividadeComando != null
								&& !cobrancaAcaoAtividadeComando.getEmpresa().getId().equals(usuario.getEmpresa().getId())){

					// 1.2.2.1.1. Bloquear o botão “Emitir 2ª. Via de Conta”.
					permissaoAcesso2aVia = false;
				}

			}else{
				// 1.2.3. Caso contrário, ou seja, o imóvel não tenha débitos remuneráveis:

				// 1.2.3.1. Bloquear o botão “Emitir 2ª. Via de Conta”.
				permissaoAcesso2aVia = false;
			}
		}

		return permissaoAcesso2aVia;
	}

	/**
	 * Este método é chamado por 2 Casos de Uso diferentes:
	 * [UC0203] Consultar Débitos
	 * [SB0006] - Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
	 * da empresa contratante
	 * e
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0025] - Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
	 * da empresa contratante
	 * 
	 * @return Retorna:
	 *         1 - Caso o usuário não tenha autorização de acesso ao imóvel
	 *         2 - Caso o usuário logado no sistema não possua permissão especial
	 *         para acesso aos dados do imóvel em cobrança administrativa
	 *         null - Caso o usuário tenha permissão
	 * @author Saulo Lima
	 * @date 31/07/2013
	 * @param usuario
	 * @param idImovel
	 * @param colecaoDebitoImovel
	 * @throws ControladorException
	 */
	public Integer validarAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante(Usuario usuario, Integer idImovel,
					ObterDebitoImovelOuClienteHelper colecaoDebitoImovel) throws ControladorException{

		Integer permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante = null;

		boolean existeOcorrenciaCobrancaAdministrativa = this.getControladorCobranca().existeContaOuGuiaPagamentoDebitoImovelOuCliente(
						colecaoDebitoImovel.getColecaoContasValores(), colecaoDebitoImovel.getColecaoGuiasPagamentoValores());

		// 1.1. Caso existam, na lista de contas retornada pelo [UC0067], contas em cobrança
		// administrativa ou caso existam, na lista de prestações das guias de pagamento retornada
		// pelo [UC0067], prestações em cobrança administrativa
		if(existeOcorrenciaCobrancaAdministrativa){

			// 1.1.2. Caso o usuário não tenha autorização de acesso ao imóvel
			if(this.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, Integer.valueOf(idImovel),
							ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE).equals(ConstantesSistema.NAO)){
				permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante = 1;
			}

		}else{
			// 1.2. Caso contrário, ou seja, não existam, na lista de contas retornada pelo
			// [UC0067], contas em cobrança administrativa ou não existam, na lista de prestações
			// das guias de pagamento retornada pelo [UC0067], prestações em cobrança administrativa

			// 1.2.1. Caso o usuário logado no sistema não pertença a uma empresa de cobrança
			// administrativa
			if(!this.getControladorCobranca().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

				boolean achouRemuneracao = this.verificarIndicadorRemuneraCobrancaAdministrativa(colecaoDebitoImovel);

				// 1.2.1.1. Caso algum dos itens das listas retornadas pelo [UC0067] esteja marcado
				// como remunerável
				if(achouRemuneracao){
					boolean permissao = this.getControladorPermissaoEspecial().verificarPermissaoEspecial(
									PermissaoEspecial.ACESSAR_DADOS_IMOVEL_COBRANCA_ADMINISTRATIVA, usuario);

					// 1.2.1.1.1. Caso o usuário logado no sistema não possua permissão especial
					// para acesso aos dados do imóvel em cobrança administrativa
					if(!permissao){
						permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante = 2;
					}
				}
			}
		}

		return permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante;
	}

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0005] Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * 
	 * @author Saulo Lima
	 * @date 25/07/2013
	 * @param colecaoDebitoImovel
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarIndicadorRemuneraCobrancaAdministrativa(ObterDebitoImovelOuClienteHelper colecaoDebitoImovel)
					throws ControladorException{

		boolean achouRemuneracao = false;

		Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoImovel.getColecaoContasValores();
		if(!Util.isVazioOrNulo(colecaoContaValores)){
			for(ContaValoresHelper contaValoresHelper : colecaoContaValores){

				if(contaValoresHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.SIM)){
					achouRemuneracao = true;
					break;
				}

				if(!Util.isVazioOrNulo(contaValoresHelper.getConta().getDebitoCobrados())){
					Collection<DebitoCobrado> debitosCobrados = contaValoresHelper.getConta().getDebitoCobrados();
					for(DebitoCobrado debitoCobrado : debitosCobrados){
						if(debitoCobrado.getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.SIM)){
							achouRemuneracao = true;
							break;
						}
					}
					if(achouRemuneracao){
						break;
					}
				}
			}
		}

		if(!achouRemuneracao){

			Collection<GuiaPagamentoValoresHelper> colecaoGuiaValores = colecaoDebitoImovel.getColecaoGuiasPagamentoValores();
			if(!Util.isVazioOrNulo(colecaoGuiaValores)){
				for(GuiaPagamentoValoresHelper guiaValoresHelper : colecaoGuiaValores){

					Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = guiaValoresHelper.getGuiaPagamentoPrestacoes();
					for(GuiaPagamentoPrestacao guiaPagamentoPrestacao : colecaoGuiaPagamentoPrestacao){
						if(guiaPagamentoPrestacao.getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.SIM)){
							achouRemuneracao = true;
							break;
						}
					}
					if(achouRemuneracao){
						break;
					}
				}
			}
		}

		if(!achouRemuneracao){
			Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoImovel.getColecaoDebitoACobrar();
			if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){
				for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){
					if(debitoACobrar.getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.SIM)){
						achouRemuneracao = true;
						break;
					}
				}
			}
		}

		return achouRemuneracao;
	}

	/**
	 * [UC3062] Validar Autorização Acesso Imóvel - Cobrança Administrativa
	 * Valida a autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa e valida a autorização de acesso ao imóvel em cobrança administrativa pelos
	 * usuários da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param usuario
	 * @param idImovel
	 * @param cdTipoValidacaoIdentificado
	 * @return
	 */
	public Short obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(Usuario usuario, Integer idImovel,
					Short cdTipoValidacaoIdentificado) throws ControladorException{

		// 1.1. Atribui o valor 1 (sim) ao Indicador de Liberação de Acesso aos Dados do Imóvel
		Short indicadorLiberacaoAcessoDadosImovel = ConstantesSistema.SIM;

		try{
			// 1.2. Caso a validação seja referente a um usuário da empresa de cobrança
			// administrativa (código de tipo de validação recebido com o valor 1 (um)):
			if(cdTipoValidacaoIdentificado.equals(ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA)){
				// 1.2.1. Caso o usuário logado no sistema pertença a uma empresa de cobrança
				// administrativa
				if(this.repositorioCobranca.existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){
					// 1.2.1.1. Caso o usuário recebido esteja em processo de cobrança
					// administrativa
					if(this.repositorioCobranca.existeProcessoCobrancaAdministrativa(idImovel)){
						// 1.2.1.1.1. Caso o usuário logado no sistema não pertença a nenhuma das
						// empresas de cobrança administrativa responsáveis pela cobrança do imóvel
						if(!this.repositorioCobranca.existeEmpresaProcessoCobrancaAdministrativa(usuario.getEmpresa().getId(), idImovel)){
							// 1.2.1.1.1.1. Atribui o valor 2 (dois) ao Indicador de Liberação de
							// Acesso aos Dados do Imóvel
							indicadorLiberacaoAcessoDadosImovel = ConstantesSistema.NAO;
						}

					}else{
						// 1.2.1.2. Caso contrário, ou seja, caso o usuário recebido não esteja em
						// processo de cobrança administrativa:
						// 1.2.1.2.1. Atribui o valor 2 (dois) ao Indicador de Liberação de Acesso
						// aos Dados do Imóvel.
						indicadorLiberacaoAcessoDadosImovel = ConstantesSistema.NAO;
					}
				}

			}

			// 1.3. Caso a validação seja referente a um usuário não pertencente a uma empresa de
			// cobrança administrativa (código de tipo de validação recebido com o valor 2 (dois))
			if(cdTipoValidacaoIdentificado.equals(ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE)){
				// Indicador de que o usuário não pertece a empresa de cobrança administrativa
				boolean isUsuarioNaoPertenceEmpresaCobrancaAdministrativa = true;

				// Consulta se o usuário pertence a uma empresa de cobrança administrativa
				FiltroCobrancaContrato filtroCobrancaContrato = new FiltroCobrancaContrato();
				filtroCobrancaContrato.adicionarParametro(new ParametroSimples(FiltroCobrancaContrato.EMPRESA_ID, usuario.getEmpresa()
								.getId()));

				// Verifica se EMPR_ID da tabela USUARIO com USUR_NMLOGIN=Login do Usuário recebido
				// não está contido em nenhum dos EMPR_ID da tabela COBRANCA_CONTRATO
				Collection<CobrancaContrato> colecaoCobrancaContrato = this.repositorioUtil.pesquisar(filtroCobrancaContrato,
								CobrancaContrato.class.getName());

				if(!Util.isVazioOrNulo(colecaoCobrancaContrato)){
					isUsuarioNaoPertenceEmpresaCobrancaAdministrativa = false;
				}

				// 1.3.1. Caso o usuário logado no sistema não pertença a uma empresa de cobrança
				// administrativa
				if(isUsuarioNaoPertenceEmpresaCobrancaAdministrativa){
					// 1.3.1.1. Caso o imóvel recebido esteja em processo de cobrança
					// administrativa
					if(this.repositorioCobranca.existeProcessoCobrancaAdministrativa(idImovel)){
						// 1.3.1.1.1. Caso o usuário logado no sistema não possua permissão especial
						// para acesso aos dados do imóvel em cobrança administrativa
						if(!this.getControladorPermissaoEspecial().verificarPermissaoAcessarDadosImovelCobrancaAdministrativa(usuario)){
							// 1.3.1.1.1.1. Atribui o valor 2 (dois) ao Indicador de Liberação de
							// Acesso aos Dados do Imóvel
							indicadorLiberacaoAcessoDadosImovel = ConstantesSistema.NAO;
						}
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return indicadorLiberacaoAcessoDadosImovel;

	}

	/**
	 * Verifica se um cliente representado pelo CNPJ/CPF tem relação ativa com o imóvel representado
	 * pela matrícula.
	 * 
	 * @author Marlos Ribeiro
	 * @param cpfCnpjCliente
	 * @param matriculaImovel
	 * @exception ControladorException
	 *                se o cliente nao tiver relações com o imóvel.
	 * @throws NegocioException
	 */
	public void validarPermissaoClienteImovel(String cpfCnpjCliente, String matriculaImovel) throws ControladorException, NegocioException{

		verificarMatriculaImovel(matriculaImovel);
		boolean validarCpfCnpj = ParametrosAgenciaVirtual.isCpfCnpjObrigatorio();
		if(validarCpfCnpj){
			verificarFormatoCpfCnpj(cpfCnpjCliente);
			varificarRelacaoImovelCliente(cpfCnpjCliente, matriculaImovel);
		}
	}

	/**
	 * Verifica se um cliente representado pelo CNPJ/CPF tem relação ativa com o imóvel representado
	 * pela matrícula.
	 * 
	 * @author Marlos Ribeiro
	 * @param cpfCnpjCliente
	 * @param matriculaImovel
	 * @exception ControladorException
	 *                se o cliente nao tiver relações com o imóvel.
	 * @throws NegocioException
	 */
	public void validarPermissaoClienteImovel(String matriculaImovel) throws ControladorException, NegocioException{

		verificarMatriculaImovel(matriculaImovel);

	}

	private void verificarFormatoCpfCnpj(String cpfCnpjCliente) throws NegocioException{

		if(Util.isVazioOuBranco(cpfCnpjCliente)){
			throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.erro.campo_obrigatorio_masculino",
							"CPF / CNPJ"));
		}

		if(!cpfCnpjCliente.matches("[0-9]*")){
			throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
							"atencao.erro.campo_deve_conter_apenas_numeros", "CPF / CNPJ"));
		}else if(!(cpfCnpjCliente.length() == 11 || cpfCnpjCliente.length() == 14)){
			throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.campo.invalido", "CPF / CNPJ"));
		}
	}

	private void verificarMatriculaImovel(String matriculaImovel) throws NegocioException, ControladorException{

		if(Util.isVazioOuBranco(matriculaImovel)){
			throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.erro.campo_obrigatorio_feminino",
							"Matrícula do imóvel"));
		}

		if(!matriculaImovel.matches("[0-9]*")){
			throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
							"atencao.erro.campo_deve_conter_apenas_numeros", "Matrícula do imóvel"));
		}
		Imovel imovel = null;
		try{
			imovel = consultarImovel(Integer.valueOf(matriculaImovel));
		}catch(NumberFormatException e){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.campo.invalido", "Matrícula Imóvel");
		}

		if(imovel == null){
			throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.matricula.imovel.inexistente"));
		}
	}

	private void varificarRelacaoImovelCliente(String cpfCnpjCliente, String matriculaImovel) throws ControladorException, NegocioException{

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, matriculaImovel));
		Collection<ClienteImovel> clienteImoveis = (Collection<ClienteImovel>) getControladorUtil().pesquisar(filtroClienteImovel,
						ClienteImovel.class.getName());
		boolean clienteOk = false;
		boolean isCpfCnpjCadastrado = false;
		for(ClienteImovel clienteImovelSimplificado : clienteImoveis){
			Cliente cliente = clienteImovelSimplificado.getCliente();
			if(Util.isVazioOuBranco(cliente.getCpf()) && Util.isVazioOuBranco(cliente.getCnpj())){
				continue;
			}else{
				isCpfCnpjCadastrado = true;
			}
			String documento = cpfCnpjCliente.length() == 11 ? cliente.getCpf() : cliente.getCnpj();
			if(cpfCnpjCliente.equals(documento)
							&& (clienteImovelSimplificado.getDataFimRelacao() == null || Calendar.getInstance().getTime()
											.compareTo(clienteImovelSimplificado.getDataFimRelacao()) < 1)){
				clienteOk = true;
				break;
			}
		}

		if(!isCpfCnpjCadastrado){
			throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.imovel_sem_cpf_cnpj_cadastrado"));
		}

		if(!clienteOk){
			throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.cleinte_relacao_imovel_inexistente"));
		}
	}



	/**
	 * [UC0630] Solicitar Emissão do Extrato de Débitos
	 * Método responsável por verificar se o usuário tem acesso a impressaão de extrato de débito
	 * 
	 * @param usuarioLogado
	 * @param idImovel
	 * @param helper
	 * @return
	 * @throws ControladorException
	 */
	public Boolean isImpressaoExtratoDebitoLiberada(Usuario usuarioLogado, Integer idImovel, ObterDebitoImovelOuClienteHelper helper)
					throws ControladorException{

		Boolean retorno = Boolean.TRUE;

		// [SB0001] – Validar autorização de acesso ao imóvel pelos usuários das empresas de
		// cobrança administrativa
		Integer resposta = this.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuarioLogado, idImovel, helper);

		if(resposta.equals(Integer.valueOf(1)) || resposta.equals(Integer.valueOf(3))){

			retorno = Boolean.FALSE;

		}else{

			// [SB0002] – Validar autorização de acesso ao imóvel em cobrança administrativa pelos
			// usuários da empresa contratante
			if(this.validarAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante(usuarioLogado, idImovel, helper) != null){
				retorno = Boolean.FALSE;
			}

		}

		return retorno;

	}

	/**
	 * [UC0203] [SB0008]
	 * Método responsável por exibir a mensagem de procedimento de corte do imovel, caso exista
	 * 
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public String obterMsgImovelProcessoCorte(Integer idImovel) throws ControladorException{

		String msg = null;

		try{

			List<CobrancaDocumento> collCobDoc = this.repositorioCobranca.pesquisarCobrancaDocumentoImovelProcessoCorte(idImovel,
							CobrancaAcaoSituacao.PENDENTE);

			if(!collCobDoc.isEmpty()){

				msg = "AVISO DE CORTE EMITIDO";

			}else{

				// Verificar se o aviso de corte foi entregue
				collCobDoc = this.repositorioCobranca
								.pesquisarCobrancaDocumentoImovelProcessoCorte(idImovel, CobrancaAcaoSituacao.ENTREGUE);

				if(!collCobDoc.isEmpty()){

					msg = "AVISO DE CORTE ENTREGUE";

				}else{

					Boolean osCortePendente = this.repositorioCobranca.existeOsCortePendente(idImovel);

					if(osCortePendente){

						msg = "CORTE EM ANDAMENTO";

					}

				}

			}

		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);

		}

		return msg;

	}

	/**
	 * Pesquisar Rotas dos Imóveis
	 * 
	 * @author Hebert Falcão
	 * @date 12/11/2012
	 */
	public Collection<Rota> pesquisarRotasDosImoveis(Collection<Integer> idImoveis) throws ControladorException{

		try{
			return repositorioImovel.pesquisarRotasDosImoveis(idImoveis);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);

		}
	}

	public Short obterCodigoRota(Integer idRota) throws ControladorException{

		try{

			return repositorioImovel.obterCodigoRota(idRota);

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);

		}

	}

	/**
	 * Gerar Relatório de Imóveis Outros Critérios Contador
	 * 
	 * @author Ítalo Almeida
	 * @date 10/01/2013
	 * @param idImovelCondominio
	 * @param idImovelPrincipal
	 * @param idNomeConta
	 * @param idSituacaoLigacaoAgua
	 * @param consumoMinimoInicialAgua
	 * @param consumoMinimoFinalAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param consumoMinimoInicialEsgoto
	 * @param consumoMinimoFinalEsgoto
	 * @param intervaloValorPercentualEsgotoInicial
	 * @param intervaloValorPercentualEsgotoFinal
	 * @param intervaloMediaMinimaImovelInicial
	 * @param intervaloMediaMinimaImovelFinal
	 * @param intervaloMediaMinimaHidrometroInicial
	 * @param intervaloMediaMinimaHidrometroFinal
	 * @param idImovelPerfil
	 * @param idPocoTipo
	 * @param idFaturamentoSituacaoTipo
	 * @param idCobrancaSituacaoTipo
	 * @param idSituacaoEspecialCobranca
	 * @param idEloAnormalidade
	 * @param areaConstruidaInicial
	 * @param areaConstruidaFinal
	 * @param idCadastroOcorrencia
	 * @param idConsumoTarifa
	 * @param idGerenciaRegional
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorComercialInicial
	 * @param setorComercialFinal
	 * @param quadraInicial
	 * @param quadraFinal
	 * @param loteOrigem
	 * @param loteDestno
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param municipio
	 * @param idTipoMedicao
	 * @param indicadorMedicao
	 * @param idSubCategoria
	 * @param idCategoria
	 * @param quantidadeEconomiasInicial
	 * @param quantidadeEconomiasFinal
	 * @param diaVencimento
	 * @param idCliente
	 * @param idClienteTipo
	 * @param idClienteRelacaoTipo
	 * @param numeroPontosInicial
	 * @param numeroPontosFinal
	 * @param numeroMoradoresInicial
	 * @param numeroMoradoresFinal
	 * @param idAreaConstruidaFaixa
	 * @return
	 * @throws ControladorException
	 */
	public int gerarRelatorioImovelOutrosCriteriosCount(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String cdRotaInicial,
					String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String consumoFixadoEsgotoPocoInicial,
					String consumoFixadoEsgotoPocoFinal) throws ControladorException{

		int quantidadeRegistros = 0;

		try{
			quantidadeRegistros = repositorioImovel.gerarRelatorioImovelOutrosCriteriosCount(idImovelCondominio, idImovelPrincipal,
							idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,
							consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial,
							intervaloValorPercentualEsgotoFinal, intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal,
							intervaloMediaMinimaHidrometroInicial, intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo,
							idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade,
							areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional,
							idLocalidadeInicial, idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal,
							loteOrigem, loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria,
							idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
							idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
							idAreaConstruidaFaixa, idUnidadeNegocio, cdRotaInicial, cdRotaFinal, sequencialRotaInicial,
							sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal,
							consumoFixadoEsgotoPocoInicial, consumoFixadoEsgotoPocoFinal);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return quantidadeRegistros;

	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 01/04/2013
	 */
	public Cliente pesquisarClientePropietarioImovel(Integer idImovel) throws ControladorException{

		Cliente cliente = null;

		try{

			cliente = this.repositorioImovel.pesquisarClientePropietarioImovel(idImovel);

		}catch(ErroRepositorioException ex){

			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return cliente;
	}

	/**
	 * Retorna <true> se existir alteração de inscrição pendente para o imóvel ou para o imóvel
	 * condomínio do dado imóvel, caso exista. Retorna <false> caso contrário.
	 * 
	 * @author Luciano Galvao
	 * @date 18/01/2013
	 */
	public boolean existeAlteracaoInscricaoPendente(Integer imovelId) throws ControladorException{

		FiltroImovelInscricaoAlterada filtroImovInscrAlterada = new FiltroImovelInscricaoAlterada();
		Collection<ImovelInscricaoAlterada> alteracoesInscricao = null;
		boolean resultado = false;

		if(imovelId != null){
			filtroImovInscrAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.IMOVEL_ID, imovelId));
			filtroImovInscrAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ATUALIZADO,
							ConstantesSistema.NAO));
			filtroImovInscrAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ALTERACAO_EXCLUIDA,
							ConstantesSistema.NAO));
			filtroImovInscrAlterada.adicionarParametro(new ParametroNulo(FiltroImovelInscricaoAlterada.INDICADOR_ERRO_ALTERACAO));

			alteracoesInscricao = getControladorUtil().pesquisar(filtroImovInscrAlterada, ImovelInscricaoAlterada.class.getName());

			// 1.1.1. Caso exista alteração de inscrição pendente para o imóvel (existe ocorrência
			// na
			// tabela IMOVEL_INSCR_ALTERADA para IMOV_ID=IMOV_ID da tabela IMOVEL e
			// IMIA_ICATUALIZADO=2
			// (não) e IMIA_ICALTERACAOEXCLUIDA=2 (não) e IMIA_ICERROALTERACAO=nulo)
			if(!Util.isVazioOrNulo(alteracoesInscricao)){
				resultado = true;
			}else{
				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelId));
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.IMOVEL_CONDOMINIO);

				Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()));

				// Caso exista imóvel condomínio para o imóvel (IMOV_IDIMOVELCONDOMINIO diferente de
				// nulo na tabela IMOVEL)
				if(imovel != null && imovel.getImovelCondominio() != null){
					filtroImovInscrAlterada = new FiltroImovelInscricaoAlterada();
					filtroImovInscrAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.IMOVEL_ID, imovel
									.getImovelCondominio().getId()));
					filtroImovInscrAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ATUALIZADO,
									ConstantesSistema.NAO));
					filtroImovInscrAlterada.adicionarParametro(new ParametroSimples(
									FiltroImovelInscricaoAlterada.INDICADOR_ALTERACAO_EXCLUIDA, ConstantesSistema.NAO));
					filtroImovInscrAlterada.adicionarParametro(new ParametroNulo(FiltroImovelInscricaoAlterada.INDICADOR_ERRO_ALTERACAO));

					alteracoesInscricao = getControladorUtil().pesquisar(filtroImovInscrAlterada, ImovelInscricaoAlterada.class.getName());

					// e exista alteração de inscrição pendente para o imóvel condomínio do
					// imóvel (existe ocorrência na tabela IMOVEL_INSCR_ALTERADA para
					// IMOV_ID=IMOV_IDIMOVELCONDOMINIO da tabela IMOVEL e IMIA_ICATUALIZADO=2 (não)
					// e
					// IMIA_ICALTERACAOEXCLUIDA=2 (não) e IMIA_ICERROALTERACAO=nulo):
					if(!Util.isVazioOrNulo(alteracoesInscricao)){
						resultado = true;
					}
				}
			}
		}

		return resultado;
	}

	/**
	 * Verifica se a alteração da inscrição/rota do imóvel impacta no grupo de faturamento.
	 * 
	 * @author Luciano Galvão
	 * @date 22/01/2013
	 * @throws AlteracaoInscricaoImovelException
	 */
	public void verificarAlteracaoInscricaoImovel(Integer imovelId, Rota rotaAtual) throws ControladorException,
					AlteracaoInscricaoImovelException{

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelId));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA + "." + FiltroRota.FATURAMENTO_GRUPO);

		// coleção com resultado da pesquisa de imovel
		Collection imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		// imovel encontrado
		Imovel imovelNaBase = !Util.isVazioOrNulo(imoveis) ? (Imovel) imoveis.iterator().next() : null;

		// throw new
		// AlteracaoInscricaoImovelException("atencao.confirmar.alteracao.inscricao.imovel.01",
		// null, new String[] {"2", "4", "5"});

		if(imovelNaBase != null){

			Rota rotaAnterior = imovelNaBase.getRota();
			FaturamentoGrupo faturamentoGrupoOrigem = null;
			FaturamentoGrupo faturamentoGrupoDestino = null;
			Collection<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresa = null;
			FiltroMovimentoRoteiroEmpresa filtroMovimento = null;

			SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

			// 1. Caso a rota tenha sido alterada (ROTA_ID correspondente ao campo Rota é diferente
			// de ROTA_ID da tabela IMOVEL):
			if(rotaAtual != null && rotaAnterior != null && !rotaAtual.equals(rotaAnterior)){

				faturamentoGrupoOrigem = rotaAnterior.getFaturamentoGrupo();
				faturamentoGrupoDestino = rotaAtual.getFaturamentoGrupo();

				// 1.3. Caso a alteração da rota acarrete a mudança do grupo de faturamento do
				// imóvel (grupo de faturamento origem (FTGR_ID da tabela ROTA com
				// ROTA_ID=ROTA_IDANTERIOR) é diferente do grupo de faturamento destino (FTGR_ID da
				// tabela ROTA com ROTA_ID=ROTA_IDATUAL)):
				if(faturamentoGrupoOrigem != null && faturamentoGrupoDestino != null
								&& !faturamentoGrupoOrigem.equals(faturamentoGrupoDestino)){

					// 1.3.1. Caso o grupo de faturamento origem já tenha sido faturado
					// (FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para FTGR_ID=grupo de
					// faturamento origem maior que PARM_AMREFERENCIAFATURAMENTO da tabela
					// SISTEMA_PARAMETROS):
					if(faturamentoGrupoOrigem.getAnoMesReferencia() != null
									&& faturamentoGrupoOrigem.getAnoMesReferencia().compareTo(sistemaParametros.getAnoMesFaturamento()) > 0){

						filtroMovimento = new FiltroMovimentoRoteiroEmpresa();
						filtroMovimento.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.FATURAMENTO_GRUPO_ID,
										faturamentoGrupoOrigem.getId()));
						filtroMovimento.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
										faturamentoGrupoOrigem.getAnoMesReferencia()));

						colecaoMovimentoRoteiroEmpresa = getControladorUtil().pesquisar(filtroMovimento,
										MovimentoRoteiroEmpresa.class.getName());

						// 1.3.1.1. Caso já tenha ocorrido a geração dos dados de leitura do grupo
						// de faturamento origem para o próximo faturamento (existe ocorrência na
						// tabela MOVIMENTO_ROTEIRO_EMPRESA para FTGR_ID=grupo de faturamento origem
						// e MREM_AMMOVIMENTO=FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para
						// FTGR_ID=grupo de faturamento origem):
						if(!Util.isVazioOrNulo(colecaoMovimentoRoteiroEmpresa)){

							throw new AlteracaoInscricaoImovelException(
											"atencao.confirmar.alteracao.inscricao.imovel.01",
											null,
											new String[] {imovelId.toString(), faturamentoGrupoDestino.getId().toString(), faturamentoGrupoOrigem
															.getId().toString()});

							// 1.3.1.2. Caso contrário (ainda não houve a geração dos dados de
							// leitura do grupo de origem para o próximo faturamento):
						}else{
							// 1.3.1.2.1. Caso o grupo de faturamento destino já tenha sido faturado
							// (FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para FTGR_ID=grupo de
							// faturamento
							// destino maior que PARM_AMREFERENCIAFATURAMENTO da tabela
							// SISTEMA_PARAMETROS):
							if(faturamentoGrupoDestino.getAnoMesReferencia() != null
											&& faturamentoGrupoDestino.getAnoMesReferencia().compareTo(
															sistemaParametros.getAnoMesFaturamento()) > 0){

								filtroMovimento = new FiltroMovimentoRoteiroEmpresa();
								filtroMovimento.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.FATURAMENTO_GRUPO_ID,
												faturamentoGrupoDestino.getId()));
								filtroMovimento.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
												faturamentoGrupoDestino.getAnoMesReferencia()));

								colecaoMovimentoRoteiroEmpresa = getControladorUtil().pesquisar(filtroMovimento,
												MovimentoRoteiroEmpresa.class.getName());

								// 1.3.1.2.1.1. Caso já tenha ocorrido a geração dos dados de
								// leitura do grupo
								// de faturamento destino para o próximo faturamento (existe
								// ocorrência na
								// tabela MOVIMENTO_ROTEIRO_EMPRESA para FTGR_ID=grupo de
								// faturamento destino e
								// MREM_AMMOVIMENTO=FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO
								// para
								// FTGR_ID=grupo de faturamento destino):
								if(!Util.isVazioOrNulo(colecaoMovimentoRoteiroEmpresa)){

									throw new AlteracaoInscricaoImovelException("atencao.confirmar.alteracao.inscricao.imovel.02", null,
													new String[] {imovelId.toString(), faturamentoGrupoDestino.getId().toString()});

								}

								// 1.3.1.2.2. Caso contrário (grupo de faturamento destino ainda não
								// faturado):
							}else{
								throw new AlteracaoInscricaoImovelException(
												"atencao.confirmar.alteracao.inscricao.imovel.03",
												null,
												new String[] {imovelId.toString(), faturamentoGrupoDestino.getId().toString(), faturamentoGrupoOrigem
																.getId().toString()});
							}
						}

						// 1.3.2. Caso contrário (grupo de faturamento origem ainda não faturado):
					}else{
						filtroMovimento = new FiltroMovimentoRoteiroEmpresa();
						filtroMovimento.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.FATURAMENTO_GRUPO_ID,
										faturamentoGrupoOrigem.getId()));
						filtroMovimento.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
										faturamentoGrupoOrigem.getAnoMesReferencia()));

						colecaoMovimentoRoteiroEmpresa = getControladorUtil().pesquisar(filtroMovimento,
										MovimentoRoteiroEmpresa.class.getName());

						// 1.3.2.1. Caso já tenha ocorrido a geração dos dados de leitura do grupo
						// de faturamento origem para o faturamento corrente (existe ocorrência na
						// tabela MOVIMENTO_ROTEIRO_EMPRESA para FTGR_ID=grupo de faturamento origem
						// e MREM_AMMOVIMENTO=FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para
						// FTGR_ID=grupo de faturamento origem):
						if(!Util.isVazioOrNulo(colecaoMovimentoRoteiroEmpresa)){

							throw new AlteracaoInscricaoImovelException(
											"atencao.confirmar.alteracao.inscricao.imovel.04",
											null,
											new String[] {imovelId.toString(), faturamentoGrupoDestino.getId().toString(), faturamentoGrupoOrigem
															.getId().toString()});
							// 1.3.2.2. Caso contrário (ainda não houve a geração dos dados de
							// leitura do grupo de origem):
						}else{
							// 1.3.2.2.1. Caso o grupo de faturamento destino já tenha sido faturado
							// (FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO para FTGR_ID=grupo de
							// faturamento destino maior que PARM_AMREFERENCIAFATURAMENTO da tabela
							// SISTEMA_PARAMETROS):
							if(faturamentoGrupoDestino.getAnoMesReferencia() != null
											&& faturamentoGrupoDestino.getAnoMesReferencia().compareTo(
															sistemaParametros.getAnoMesFaturamento()) > 0){

								throw new AlteracaoInscricaoImovelException("atencao.confirmar.alteracao.inscricao.imovel.05", null,
												new String[] {imovelId.toString(), faturamentoGrupoDestino.getId().toString()});

								// 1.3.2.2.2. Caso contrário (grupo de faturamento destino ainda não
								// faturado)
							}else{

								filtroMovimento = new FiltroMovimentoRoteiroEmpresa();
								filtroMovimento.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.FATURAMENTO_GRUPO_ID,
												faturamentoGrupoDestino.getId()));
								filtroMovimento.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
												faturamentoGrupoDestino.getAnoMesReferencia()));

								colecaoMovimentoRoteiroEmpresa = getControladorUtil().pesquisar(filtroMovimento,
												MovimentoRoteiroEmpresa.class.getName());

								// 1.3.2.2.2.1. Caso já tenha ocorrido a geração dos dados de
								// leitura do grupo de faturamento destino para o faturamento
								// corrente (existe ocorrência na tabela MOVIMENTO_ROTEIRO_EMPRESA
								// para FTGR_ID=grupo de faturamento destino e
								// MREM_AMMOVIMENTO=FTGR_AMREFERENCIA da tabela FATURAMENTO_GRUPO
								// para FTGR_ID=grupo de faturamento destino):
								if(!Util.isVazioOrNulo(colecaoMovimentoRoteiroEmpresa)){

									throw new AlteracaoInscricaoImovelException("atencao.confirmar.alteracao.inscricao.imovel.06", null,
													new String[] {imovelId.toString(), faturamentoGrupoDestino.getId().toString()});

								}
							}

						}
					}
				}
			}
		}
	}

	/**
	 * [UC0014] - Manter Imóvel
	 * [SB0005] – Preparar Alteração da Inscrição/Rota no Encerramento Faturamento
	 * Não efetiva a alteração da inscrição rota e grava as informações de inscrição para serem
	 * alteradas no Encerramento do Faturamento
	 * 
	 * @author Luciano Galvão
	 * @date 24/01/2013
	 */
	private void prepararAlteracaoInscricaoNoEncerramentoFaturamento(Imovel imovel, Imovel imovelNaBase) throws ControladorException{

		// Id do Setor Comercial (STCM_ID da tabela SETOR_COMERCIAL para LOCA_ID=Localidade e
		// STCM_CDSETORCOMERCIAL=Setor Comercial)
		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, imovel.getLocalidade().getId()));
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, imovel
						.getSetorComercial().getCodigo()));

		SetorComercial setorComercialAtual = (SetorComercial) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
						filtroSetorComercial, SetorComercial.class.getName()));

		// Id da Quadra (QDRA_ID da tabela QUADRA para STCM_ID=Id do Setor Comercial e
		// QDRA_NNQUADRA=Quadra)
		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercialAtual.getId()));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, imovel.getQuadra().getNumeroQuadra()));

		Quadra quadraAtual = (Quadra) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName()));

		// [FS0021] – Verificar Existência de Alteração de Inscrição Pendente para o Imóvel
		verificarExistenciaAlteracaoInscricaoPendente(imovelNaBase, false);

		// [FS0022] – Verificar Duplicidade de Inscrição
		verificarDuplicidadeInscricao(imovel, setorComercialAtual, quadraAtual);

		// Cria objeto ImovelInscricaoAlterada, que registra as alterações de inscrição para serem
		// efetivadas no encerramento do faturamento
		ImovelInscricaoAlterada imovelInscricaoAlterada = new ImovelInscricaoAlterada();
		imovelInscricaoAlterada.setImovel(imovelNaBase);
		imovelInscricaoAlterada.setFaturamentoGrupo(imovel.getRota().getFaturamentoGrupo());

		imovelInscricaoAlterada.setLocalidadeAnterior(imovelNaBase.getLocalidade());
		imovelInscricaoAlterada.setSetorComercialAnterior(imovelNaBase.getSetorComercial());
		imovelInscricaoAlterada.setQuadraAnterior(imovelNaBase.getQuadra());
		imovelInscricaoAlterada.setLoteAnterior(imovelNaBase.getLote());
		imovelInscricaoAlterada.setSubLoteAnterior(imovelNaBase.getSubLote());
		if(imovelNaBase.getTestadaLote() != null){
			imovelInscricaoAlterada.setTestadaLoteAnterior(imovelNaBase.getTestadaLote());
		}else{
			imovelInscricaoAlterada.setTestadaLoteAnterior(new Short("0"));
		}
		imovelInscricaoAlterada.setRotaAnterior(imovelNaBase.getRota());

		imovelInscricaoAlterada.setLocalidadeAtual(imovel.getLocalidade());
		imovelInscricaoAlterada.setSetorComercialAtual(setorComercialAtual);
		imovelInscricaoAlterada.setQuadraAtual(quadraAtual);
		imovelInscricaoAlterada.setLoteAtual(imovel.getLote());
		imovelInscricaoAlterada.setSubLoteAtual(imovel.getSubLote());
		if(imovel.getTestadaLote() != null){
			imovelInscricaoAlterada.setTestadaLoteAtual(imovel.getTestadaLote());
		}else{
			imovelInscricaoAlterada.setTestadaLoteAtual(new Short("0"));
		}
		imovelInscricaoAlterada.setRotaAtual(imovel.getRota());

		imovelInscricaoAlterada.setIndicadorAtualizado(ConstantesSistema.NAO);
		imovelInscricaoAlterada.setIndicadorAlteracaoExcluida(ConstantesSistema.NAO);
		imovelInscricaoAlterada.setIndicadorImovelExcluido(ConstantesSistema.NAO);
		imovelInscricaoAlterada.setIndicadorErroAlteracao(null);
		imovelInscricaoAlterada.setDataAlteracaoOnline(new Date());
		imovelInscricaoAlterada.setDataAlteracaoBatch(null);
		imovelInscricaoAlterada.setUltimaAlteracao(new Date());

		// Insere registro da alteração de inscrição do imóvel
		getControladorUtil().inserir(imovelInscricaoAlterada);

		// 1. Não efetivar a alteração da inscrição/rota (LOCA_ID, STCM_ID, STCM_CDSETORCOMERCIAL,
		// QDRA_ID, QDRA_NNQUADRA, IMOV_NNLOTE, IMOV_NNSUBLOTE, IMOV_NNTESTADALOTE, ROTA_ID) na
		// tabela IMOVEL.
		imovel.setLocalidade(imovelNaBase.getLocalidade());
		imovel.setSetorComercial(imovelNaBase.getSetorComercial());
		imovel.setQuadra(imovelNaBase.getQuadra());
		imovel.setLote(imovelNaBase.getLote());
		imovel.setSubLote(imovelNaBase.getSubLote());
		imovel.setTestadaLote(imovelNaBase.getTestadaLote());
		imovel.setRota(imovelNaBase.getRota());

	}

	/**
	 * No UC0014 - Manter Imóvel:
	 * [FS0021] – Verificar Existência de Alteração de Inscrição Pendente para o Imóvel
	 * No UC0074 - Alterar Inscrição de Imóvel:
	 * [FS0009] – Verificar Existência de Alteração de Inscrição Pendente para o Imóvel
	 * 
	 * @param exclusao
	 *            - <true> se a verificação de existência está sendo realizada durante a Exclusão do
	 *            imóvel
	 *            <false> caso esta verificação esteja ocorrendo durante a Atualização do imóvel
	 * @author Luciano Galvão
	 * @throws ControladorException
	 * @date 24/01/2012
	 */
	private void verificarExistenciaAlteracaoInscricaoPendente(Imovel imovel, boolean exclusao) throws ControladorException{

		FiltroImovelInscricaoAlterada filtroImovelInscricaoAlterada = new FiltroImovelInscricaoAlterada();
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.IMOVEL_ID, imovel.getId()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ATUALIZADO,
						ConstantesSistema.NAO));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ALTERACAO_EXCLUIDA,
						ConstantesSistema.NAO));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroNulo(FiltroImovelInscricaoAlterada.INDICADOR_ERRO_ALTERACAO));

		Collection<ImovelInscricaoAlterada> colecaoImovelInscricaoAlterada = getControladorUtil().pesquisar(filtroImovelInscricaoAlterada,
						ImovelInscricaoAlterada.class.getName());

		// Caso exista alteração de inscrição pendente para o imóvel (existe ocorrência na tabela
		// IMOVEL_INSCR_ALTERADA para IMOV_ID=IMOV_ID da tabela IMOVEL e IMIA_ICATUALIZADO=2 (não) e
		// IMIA_ICALTERACAOEXCLUIDA=2 (não) e IMIA_ICERROALTERACAO=nulo):
		if(!Util.isVazioOrNulo(colecaoImovelInscricaoAlterada)){

			// Caso o imóvel esteja sendo excluído, indicar exclusão da alteração pendente em razão
			// da exclusão do imóvel (atualizar tabela IMOVEL_INSCR_ALTERADA com os seguintes
			// valores: IMIA_ICALTERACAOEXCLUIDA=1 (um), IMIA_ICIMOVELEXCLUIDO=1 (um) e
			// IMIA_TMULTIMAALTERACAO=Data e hora correntes).
			if(exclusao){
				for(ImovelInscricaoAlterada imovelInscricaoAlterada : colecaoImovelInscricaoAlterada){

					imovelInscricaoAlterada.setIndicadorAlteracaoExcluida(ConstantesSistema.SIM);
					imovelInscricaoAlterada.setIndicadorImovelExcluido(ConstantesSistema.SIM);
					imovelInscricaoAlterada.setUltimaAlteracao(new Date());
					getControladorUtil().atualizar(imovelInscricaoAlterada);
				}

				// Caso contrário, indicar exclusão da alteração pendente em razão de nova
				// alteração (atualizar tabela IMOVEL_INSCR_ALTERADA com os seguintes valores:
				// IMIA_ICALTERACAOEXCLUIDA=1 (um) e IMIA_TMULTIMAALTERACAO=Data e hora correntes).
			}else{
				for(ImovelInscricaoAlterada imovelInscricaoAlterada : colecaoImovelInscricaoAlterada){

					imovelInscricaoAlterada.setIndicadorAlteracaoExcluida(ConstantesSistema.SIM);
					imovelInscricaoAlterada.setUltimaAlteracao(new Date());
					getControladorUtil().atualizar(imovelInscricaoAlterada);
				}
			}
		}
	}

	/**
	 * [UC0014] Manter Imóvel
	 * [FS0022] Verificar Duplicidade de Inscrição
	 * 
	 * @author Luciano Galvao
	 * @date 25/01/2013
	 */
	private void verificarDuplicidadeInscricao(Imovel imovelAlterado, SetorComercial setorComercialAtual, Quadra quadraAtual)
					throws ControladorException{

		FiltroImovelInscricaoAlterada filtroImovelInscricaoAlterada = new FiltroImovelInscricaoAlterada();
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ATUALIZADO,
						ConstantesSistema.NAO));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ALTERACAO_EXCLUIDA,
						ConstantesSistema.NAO));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroNulo(FiltroImovelInscricaoAlterada.INDICADOR_ERRO_ALTERACAO));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.LOCALIDADE_ATUAL_ID,
						imovelAlterado.getLocalidade().getId()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.SETOR_COMERCIAL_ATUAL_ID,
						setorComercialAtual.getId()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.QUADRA_ATUAL_ID, quadraAtual
						.getId()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.LOTE_ATUAL, imovelAlterado
						.getLote()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.SUBLOTE_ATUAL, imovelAlterado
						.getSubLote()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.TESTADA_LOTE_ATUAL,
						imovelAlterado.getTestadaLote()));

		Collection<ImovelInscricaoAlterada> colecaoImovelInscricaoAlterada = getControladorUtil().pesquisar(filtroImovelInscricaoAlterada,
						ImovelInscricaoAlterada.class.getName());

		// Caso exista algum imóvel pendente de alteração com a mesma inscrição, exibir mensagem e
		// não permitir a atualização
		if(!Util.isVazioOrNulo(colecaoImovelInscricaoAlterada)){
			throw new ControladorException("atencao.duplicidade.alteracao.inscricao.imovel.01", null, imovelAlterado.getId().toString());
		}

	}

	/**
	 * [UC0074] Alterar Inscrição de Imóvel
	 * [FS0010] – Verificar Duplicidade de Inscrição
	 * 
	 * @author Luciano Galvao
	 * @date 30/01/2013
	 */
	private void verificarDuplicidadeInscricao(ImovelAlterarInscricaoHelper imovelAlterado) throws ControladorException{

		FiltroImovelInscricaoAlterada filtroImovelInscricaoAlterada = new FiltroImovelInscricaoAlterada();
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ATUALIZADO,
						ConstantesSistema.NAO));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ALTERACAO_EXCLUIDA,
						ConstantesSistema.NAO));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroNulo(FiltroImovelInscricaoAlterada.INDICADOR_ERRO_ALTERACAO));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.LOCALIDADE_ATUAL_ID,
						imovelAlterado.getIdLocalidadeAtual()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.SETOR_COMERCIAL_ATUAL_ID,
						imovelAlterado.getIdSetorComercialAtual()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.QUADRA_ATUAL_ID, imovelAlterado
						.getIdQuadraAtual()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.LOTE_ATUAL, imovelAlterado
						.getLoteAtual()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.SUBLOTE_ATUAL, imovelAlterado
						.getSubLoteAtual()));
		filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.TESTADA_LOTE_ATUAL,
						imovelAlterado.getTestadaLoteAtual()));

		Collection<ImovelInscricaoAlterada> colecaoImovelInscricaoAlterada = getControladorUtil().pesquisar(filtroImovelInscricaoAlterada,
						ImovelInscricaoAlterada.class.getName());

		// Caso exista algum imóvel pendente de alteração com a mesma inscrição, exibir mensagem e
		// não permitir a atualização
		if(!Util.isVazioOrNulo(colecaoImovelInscricaoAlterada)){
			throw new ControladorException("atencao.duplicidade.alteracao.inscricao.imovel.02", null, imovelAlterado.getIdImovel()
							.toString());
		}

	}

	/**
	 * [UC3090] Efetivar Alterar Inscrição de Imóvel
	 * Efetiva a alteração da inscrição dos imóveis que no momento da alteração on-line não puderam
	 * ter a inscrição alterada em razão de já estarem em processo de faturamento ficando a
	 * atualização para o fechamento do faturamento do grupo
	 * 
	 * @author Luciano Galvão
	 * @throws ControladorException
	 * @date 18/02/2013
	 */
	public void efetivarAlteracaoInscricaoImovel(int idFuncionalidadeIniciada, Usuario usuarioLogado) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, 0);
		try{

			Short indicadorErroAlteracao = ConstantesSistema.NAO;
			Imovel imovel = null;

			// Seleciona os imóveis com inscrição pendente de alteração (a partir da tabela
			// IMOVEL_INSCR_ALTERADA com IMIA_ICATUALIZADO=2 (não) e IMIA_ICALTERACAOEXCLUIDA=2
			// (não) e
			// IMIA_ICERROALTERACAO=nulo)
			FiltroImovelInscricaoAlterada filtroImovelInscricaoAlterada = new FiltroImovelInscricaoAlterada();
			filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.INDICADOR_ATUALIZADO,
							ConstantesSistema.NAO));
			filtroImovelInscricaoAlterada.adicionarParametro(new ParametroSimples(
							FiltroImovelInscricaoAlterada.INDICADOR_ALTERACAO_EXCLUIDA, ConstantesSistema.NAO));
			filtroImovelInscricaoAlterada.adicionarParametro(new ParametroNulo(FiltroImovelInscricaoAlterada.INDICADOR_ERRO_ALTERACAO));
			filtroImovelInscricaoAlterada.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.IMOVEL);
			filtroImovelInscricaoAlterada.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.LOCALIDADE_ATUAL);
			filtroImovelInscricaoAlterada.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.SETOR_COMERCIAL_ATUAL);
			filtroImovelInscricaoAlterada.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.QUADRA_ATUAL);
			filtroImovelInscricaoAlterada.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.ROTA_ATUAL);

			Collection<ImovelInscricaoAlterada> colecaoImovelInscricaoAlterada = getControladorUtil().pesquisar(
							filtroImovelInscricaoAlterada, ImovelInscricaoAlterada.class.getName());

			// [FS0001] – Verificar existência de inscrições pendentes de alteração
			if(!Util.isVazioOrNulo(colecaoImovelInscricaoAlterada)){

				// 2. Para cada um dos imóveis selecionados
				for(ImovelInscricaoAlterada imovelInscricaoAlterada : colecaoImovelInscricaoAlterada){

					// 2.1. Atribuir o valor 2 (dois) a Indicador Erro Alteração.
					indicadorErroAlteracao = ConstantesSistema.NAO;

					// [FS0002] – Verificar duplicidade de inscrição
					if(verificarDuplicidadeInscricaoImovel(imovelInscricaoAlterada)){

						// Atribuir o valor 1 (um) a Indicador Erro Alteração
						indicadorErroAlteracao = ConstantesSistema.SIM;

					}else{

						try{
							Integer imovelId = imovelInscricaoAlterada.getImovel().getId();

							// 2.2. Atualiza a tabela IMOVEL para IMOV_ID=IMOV_ID da tabela
							// IMOVEL_INSCR_ALTERADA com os seguintes dados
							repositorioImovel.atualizarImovelInscricao(imovelId, imovelInscricaoAlterada.getLocalidadeAtual().getId(),
											imovelInscricaoAlterada.getSetorComercialAtual().getId(), imovelInscricaoAlterada
															.getSetorComercialAtual().getCodigo(), imovelInscricaoAlterada.getQuadraAtual()
															.getId(), imovelInscricaoAlterada.getQuadraAtual().getNumeroQuadra(),
											imovelInscricaoAlterada.getLoteAtual(), imovelInscricaoAlterada.getSubLoteAtual(),
											imovelInscricaoAlterada.getTestadaLoteAtual(), imovelInscricaoAlterada.getRotaAtual().getId());

							// Registrar a transação de alteração do imóvel
							// [UC0107] - Registrar Transação
							// ------------ REGISTRAR TRANSAÇÃO----------------------------
							RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_IMOVEL_ATUALIZAR, imovelId,
											imovelId,
											new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

							Operacao operacao = new Operacao();
							operacao.setId(Operacao.OPERACAO_IMOVEL_ATUALIZAR);

							OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
							operacaoEfetuada.setOperacao(operacao);

							imovel = this.pesquisarImovel(imovelId);

							imovel.setOperacaoEfetuada(operacaoEfetuada);
							registradorOperacao.registrarOperacao(imovel);
							// ------------ REGISTRAR TRANSAÇÃO----------------------------

						}catch(ErroRepositorioException e){

							// Caso o código de retorno da operação efetuada no banco de dados seja
							// diferente de zero, atribuir o valor 1 (um) a Indicador Erro
							// Alteração;
							indicadorErroAlteracao = ConstantesSistema.SIM;
						}

					}

					// Caso o Indicador Erro Alteração esteja com o valor 2 (dois), atribuir o valor
					// 1 (um); caso contrário, atribuir o valor 2 (dois).
					if(indicadorErroAlteracao.equals(ConstantesSistema.NAO)){
						imovelInscricaoAlterada.setIndicadorAtualizado(ConstantesSistema.SIM);
					}else{
						imovelInscricaoAlterada.setIndicadorAtualizado(ConstantesSistema.NAO);
					}

					imovelInscricaoAlterada.setIndicadorErroAlteracao(indicadorErroAlteracao);
					imovelInscricaoAlterada.setDataAlteracaoBatch(new Date());
					imovelInscricaoAlterada.setUltimaAlteracao(new Date());

					// 2.3. Atualiza a tabela IMOVEL_INSCR_ALTERADA
					getControladorUtil().atualizar(imovelInscricaoAlterada);
				}
			}

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			ex.printStackTrace();
			sessionContext.setRollbackOnly();

			throw new EJBException(ex);
		}
	}

	/**
	 * [UC3090] Efetivar Alterar Inscrição de Imóvel
	 * [FS0002] Verificar duplicidade de inscrição
	 * 
	 * @author Luciano Galvao
	 * @date 25/02/2013
	 */
	private boolean verificarDuplicidadeInscricaoImovel(ImovelInscricaoAlterada imovelInscricaoAlterada) throws ControladorException{

		// Caso a alteração da inscrição provoque duplicidade de local, setor, quadra, lote, sublote
		// e testada de lote (já existe ocorrência na tabela IMOVEL para LOCA_ID=LOCA_IDATUAL e
		// STCM_ID=STCM_IDATUAL e QDRA_ID=QDRA_IDATUAL e IMOV_NNLOTE=IMIA_NNLOTEATUAL e
		// IMOV_NNSUBLOTE=IMIA_NNSUBLOTEATUAL e IMOV_NNTESTADALOTE=IMIA_NNTESTADALOTEATUAL):

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, imovelInscricaoAlterada.getLocalidadeAtual()
						.getId()));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_ID, imovelInscricaoAlterada
						.getSetorComercialAtual().getId()));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_ID, imovelInscricaoAlterada.getQuadraAtual().getId()));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOTE, imovelInscricaoAlterada.getLoteAtual()));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SUBLOTE, imovelInscricaoAlterada.getSubLoteAtual()));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ROTA_ID, imovelInscricaoAlterada.getRotaAtual().getId()));
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, new Integer("2")));

		Collection<Imovel> colecaoImoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		return !Util.isVazioOrNulo(colecaoImoveis);
	}

	/**
	 * Retorna o valor de controladorBatch
	 * 
	 * @return O valor de controladorBatch
	 */
	protected ControladorBatchLocal getControladorBatch(){

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

	/**
	 * @param idRota
	 * @return
	 * @throws ControladorException
	 */

	public String obterRota(Integer idRota) throws ControladorException{

		
		StringBuffer descricaoRota = new StringBuffer();


			FiltroRota filtroRota = new FiltroRota();

			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL_LOCALIDADE);
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, idRota));

			Collection colecao = this.getControladorUtil().pesquisar(filtroRota, Rota.class.getName());

			Rota rota = (Rota) Util.retonarObjetoDeColecao(colecao);

			SetorComercial setorComercial = rota.getSetorComercial();
			Localidade localidade = setorComercial.getLocalidade();

			Integer idLocalidadeAux = localidade.getId();
			String idLocalidadeAuxStr = Integer.toString(idLocalidadeAux);
			idLocalidadeAuxStr = Util.adicionarZerosEsquedaNumero(3, idLocalidadeAuxStr);

			int codigoSetorComercialAux = setorComercial.getCodigo();
			String codigoSetorComercialAuxStr = Integer.toString(codigoSetorComercialAux);
			codigoSetorComercialAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoSetorComercialAuxStr);

			Short codigoRotaAux = rota.getCodigo();
			String codigoRotaAuxStr = Short.toString(codigoRotaAux);
			codigoRotaAuxStr = Util.adicionarZerosEsquedaNumero(3, codigoRotaAuxStr);

		descricaoRota.append(" LOCAL: " + idLocalidadeAuxStr + ";");
		descricaoRota.append(" SETOR: " + codigoSetorComercialAuxStr + ";");
		descricaoRota.append(" ROTA: " + codigoRotaAuxStr);

		return descricaoRota.toString();

		

	}
	
	/**
	 * Pesquisa todos os imóveis de uma determinada Localidade.
	 * 
	 * @author Josenildo Neves
	 * @date 02/07/2013
	 * @param idLocalidade
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarImoveisPorLocalidade(Integer idLocalidade) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImoveisPorLocalidade(idLocalidade);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}
	
	/**
	 * [UC0098] ? Manter Vinculos Rateio Consumo
	 * [FS0011] ? Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 * @date 09/07/2013
	 */
	public Boolean verificarImovelEmProcessoDeFaturamento(Integer idImovel) throws ControladorException{

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		Boolean retorno = false;

		Integer amReferenciaFaturamentoGrupoImovel = null;

		try{

			amReferenciaFaturamentoGrupoImovel = this.repositorioImovel.retornarAnoMesReferenciaFaturamentoGrupoImovel(idImovel);
			
			// verifica se o faturamento foi iniciado para o grupo do imovel
			Boolean faturamentoGrupoIniciado = this.repositorioImovel.verificarFaturamentoGrupoImovelIniciado(idImovel,
							FaturamentoAtividade.GERAR_ARQUIVO_LEITURA_DESCRICAO);

			// retorna o tipo da leitura do faturamento
			Integer tipoLeituraFaturamento = this.repositorioImovel.retornaTipoLeituraFaturamento(idImovel);

			if(amReferenciaFaturamentoGrupoImovel.equals(sistemaParametro.getAnoMesFaturamento()) && faturamentoGrupoIniciado){

				if(tipoLeituraFaturamento.equals(LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA)){

					// verifica se o faturamento foi iniciado para o grupo do imovel
					retorno = !this.repositorioImovel.verificarFaturamentoGrupoImovelEncerrado(idImovel,
									FaturamentoAtividade.REGISTRAR_FATURAMENTO_IMEDIATO_DESCRICAO);

				}else if(tipoLeituraFaturamento.equals(LeituraTipo.CONVENCIONAL)){

					// verifica se o faturamento foi iniciado para o grupo do imovel
					retorno = !this.repositorioImovel.verificarFaturamentoGrupoImovelEncerrado(idImovel,
									FaturamentoAtividade.FATURAR_GRUPO_DESCRICAO);
				}
			}

		}catch(ErroRepositorioException ex){

			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}
	
	public void atualizarNumeroEmissaoContrato(Integer idImovel) throws ControladorException{

		try{
			this.repositorioImovel.atualizarNumeroEmissaoContrato(idImovel);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0084] Gerar Faturamento Imediato
	 * [SB0007] Gerar Movimento Roteiro da Empresa
	 * Verifica se o imóvel é uma Repartição Pública Federal, levando em consideração sua categoria
	 * e subcategoria
	 * 
	 * @author Luciano Galvao
	 * @date 16/10/2013
	 */
	public boolean verificarImovelReparticaoPublicaFederal(Integer idImovel) throws ControladorException{

		boolean reparticaoPublicaFederal = false;

		try{

			String pSubcategoriasFederaisCategoriaPublico = ParametroFaturamento.P_SUBCATEGORIAS_FEDERAIS_CATG_PUBLICO.executar();

			if(!Util.isVazioOuBranco(pSubcategoriasFederaisCategoriaPublico)
							&& !pSubcategoriasFederaisCategoriaPublico.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){

				if(repositorioImovel.verificarImovelSubcategoriaReparticoesPublicasFederais(idImovel, Categoria.PUBLICO,
								pSubcategoriasFederaisCategoriaPublico).intValue() > 0){

					reparticaoPublicaFederal = true;
				}
			}

			// Se ainda não se enquadrou como uma repartição pública federal a partir da categoria
			// Pública, verifica a partir da categoria Comercial
			if(!reparticaoPublicaFederal){

				String pSubcategoriasFederaisCategoriaComercial = ParametroFaturamento.P_SUBCATEGORIAS_FEDERAIS_CATG_COMERCIAL.executar();

				if(!Util.isVazioOuBranco(pSubcategoriasFederaisCategoriaComercial)
								&& !pSubcategoriasFederaisCategoriaComercial.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){

					if(repositorioImovel.verificarImovelSubcategoriaReparticoesPublicasFederais(idImovel, Categoria.COMERCIAL,
									pSubcategoriasFederaisCategoriaComercial).intValue() > 0){

						reparticaoPublicaFederal = true;
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return reparticaoPublicaFederal;
	}

	/**
	 * @autor Eduardo Oliveira
	 * @date 16/12/2013
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterQuantidadeAlteracoesImovel(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.obterQuantidadeAlteracoesImovel(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * @author Anderson Italo
	 * @date 11/02/2014
	 */
	public List<Object[]> pesquisarDadosImoveisComContaEmAtraso(boolean apenasPublicos) throws ControladorException{

		try{

			return this.repositorioImovel.pesquisarDadosImoveisComContaEmAtraso(apenasPublicos);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * @author Anderson Italo
	 * @date 11/02/2014
	 */
	public Collection<Conta> pesquisarContasEmAtrasoPorImovel(Integer idImovel) throws ControladorException{

		try{

			return this.repositorioImovel.pesquisarContasEmAtrasoPorImovel(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Retorna o ImovelSubcategoria principal (O registro com maior quantidade de economias).
	 * 
	 * @author Anderson Italo
	 * @date 14/06/2014
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ImovelSubcategoria pesquisarImovelSubcategoriaPrincipal(Integer idImovel) throws ControladorException{

		try{

			return this.repositorioImovel.pesquisarImovelSubcategoriaPrincipal(idImovel);
		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * @author Gicevalter Couto
	 * @date 21/09/2014
	 * @throws ControladorException
	 */
	public void ajustarRelacaoUsuarioClienteImovel(int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;
		try{

			idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
							UnidadeProcessamento.FUNCIONALIDADE, 0);

			//
			// 1 - Obtem a Lista de Usuário Cliente/Imoveis Vencidos
			//
			Calendar dataCorrente = Calendar.getInstance();
			dataCorrente.setTime(new Date());

			dataCorrente.set(Calendar.SECOND, 0);
			dataCorrente.set(Calendar.MILLISECOND, 0);
			dataCorrente.set(Calendar.HOUR, 0);
			dataCorrente.set(Calendar.HOUR_OF_DAY, 0);
			dataCorrente.set(Calendar.MINUTE, 0);

			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarParametro(new ParametroNaoNulo(FiltroClienteImovel.DATA_PREVISTA_FIM_RELACAO));

			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RELACAO_TIPO);

			Collection<ClienteImovel> colecaoClienteImoveis = this.getControladorUtil().pesquisar(filtroClienteImovel,
							ClienteImovel.class.getName());

			for(ClienteImovel clienteImovel : colecaoClienteImoveis){
				Calendar dataPrevistaFimRelacao = Calendar.getInstance();
				dataPrevistaFimRelacao.setTime(clienteImovel.getDataPrevistaFimRelacao());

				dataPrevistaFimRelacao.set(Calendar.SECOND, 0);
				dataPrevistaFimRelacao.set(Calendar.MILLISECOND, 0);
				dataPrevistaFimRelacao.set(Calendar.HOUR, 0);
				dataPrevistaFimRelacao.set(Calendar.HOUR_OF_DAY, 0);
				dataPrevistaFimRelacao.set(Calendar.MINUTE, 0);

				if(dataPrevistaFimRelacao.before(dataCorrente)){

					clienteImovel.setDataFimRelacao(clienteImovel.getDataPrevistaFimRelacao());
					getControladorUtil().atualizar(clienteImovel);

					ClienteImovel clienteImovelNovo = new ClienteImovel();

					clienteImovelNovo.setImovel(clienteImovel.getImovel());
					clienteImovelNovo.setClienteRelacaoTipo(clienteImovel.getClienteRelacaoTipo());
					clienteImovelNovo.setIndicadorNomeConta(clienteImovel.getIndicadorNomeConta());

					dataPrevistaFimRelacao.add(Calendar.DAY_OF_YEAR, 1);
					clienteImovelNovo.setDataInicioRelacao(dataPrevistaFimRelacao.getTime());

					FiltroClienteImovel filtroClienteImovelProprietario = new FiltroClienteImovel();
					filtroClienteImovelProprietario.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, clienteImovel
									.getImovel().getId()));
					filtroClienteImovelProprietario.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
					filtroClienteImovelProprietario.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID, ClienteRelacaoTipo.PROPRIETARIO ) );
					filtroClienteImovelProprietario.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
					
					ClienteImovel clienteImovelProprietario = (ClienteImovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
									filtroClienteImovelProprietario, ClienteImovel.class.getName()));
					clienteImovelNovo.setCliente(clienteImovelProprietario.getCliente());
					
					clienteImovelNovo.setUltimaAlteracao(new Date());
					
					getControladorUtil().inserir(clienteImovelNovo);

				}
			}


			// --------------------------------------------------------
			//
			// Registrar o fim da execução da Unidade de Processamento
			//
			// --------------------------------------------------------
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

	/**
	 * <p>
	 * [OC1372979]
	 * </p>
	 * 
	 * @author Magno Silveira (magno.silveira@procenge.com.br)
	 * @since 22/10/2014
	 * @param imovelCondominioId
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarImoveisSubcategoriasParaCondominio(Integer imovelCondominioId) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImoveisSubcategoriasParaCondominio(imovelCondominioId);
		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

	}


	/**
	 * <p>
	 * [OC1366820]
	 * </p>
	 * 
	 * @author Magno Silveira { @literal <magno.silveira@procenge.com.br> }
	 * @since 07/10/2014
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void atualizarImoveisComSupressaoDefinitiva(int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		try{
			
			logger.info("*** Iniciando Supressão Definitiva de Imóveis");

			idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
							UnidadeProcessamento.FUNCIONALIDADE, 0);
			logger.info("*** Unidade Iniciada: " + idUnidadeIniciada);
			
			Integer last_id = LigacaoAguaSituacao.SUPRIMIDO_DEFINITIVO;
			logger.info("*** Ligação Água Situação (last_id): " + last_id);

			if(!Util.isVazioOuBrancoOuZero(last_id)){

				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LIGACAO_AGUA_SITUACAO_ID, last_id));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, ConstantesSistema.NAO));
				Collection<Imovel> imoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

				logger.info("*** Imóveis filtrados: " + imoveis.size());

				if(!Util.isVazioOrNulo(imoveis)){

					String log = "";

					boolean imovelPossuiDebitos = false;

					for(Imovel imovel : imoveis){
						imovelPossuiDebitos = this.getControladorCobranca().imovelPossuiDebitos(imovel.getId());

						log = "*** Imóvel " + imovel.getId() + ((imovelPossuiDebitos) ? " " : " não ") + "possui débitos ";

						if(!imovelPossuiDebitos){
							imovel.setIndicadorExclusao(ConstantesSistema.SIM);
							imovel.setUltimaAlteracao(new Date());
							Fachada.getInstancia().atualizar(imovel);

							log += "e foi removido!";
						}

						logger.info(log);
					}
				}
			}
			
			this.getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);
			logger.info("*** Unidade de Processamento encerrada normalmente!");
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			this.getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			logger.info("*** Unidade de Processamento encerrada com erro!");
			throw new EJBException(e);
		}
	}

	/**
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarImovelSubcategorias(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioImovel.pesquisarImovelSubcategorias(idImovel);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * @param idImovel
	 * @param idRotaAtualizar
	 * @throws ControladorException
	 */

	public void atualizarRotaImovel(Integer idImovel, Integer idRotaAtualizar) throws ControladorException{

		try{
			this.repositorioImovel.atualizarRotaImovel(idImovel, idRotaAtualizar);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	public Collection<Categoria> obterCategorias(Integer idImovel, Integer anoMesMovimento){

		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();

		filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.IMOVEL_ID, idImovel));
		filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
						anoMesMovimento));

		ArrayList<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresa = (ArrayList<MovimentoRoteiroEmpresa>) Fachada.getInstancia()
						.pesquisar(filtroMovimentoRoteiroEmpresa, MovimentoRoteiroEmpresa.class.getName());

		if(!Util.isVazioOrNulo(colecaoMovimentoRoteiroEmpresa)){
			for(MovimentoRoteiroEmpresa movimentoRoteiroEmpresa : colecaoMovimentoRoteiroEmpresa){

				Short comercial = movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial();
				Short industrial = movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial();
				Short publica = movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica();
				Short residencial = movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial();

				if(comercial != null && comercial > 0){

					FiltroCategoria filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, Categoria.COMERCIAL_INT));
					Collection<Categoria> categoriaCollecion = Fachada.getInstancia().pesquisar(filtroCategoria, Categoria.class.getName());

					for(Categoria categoria : categoriaCollecion){
						categoria.setQuantidadeEconomiasCategoria(new Integer(comercial));
					}

					categorias.addAll(categoriaCollecion);

				}

				if(industrial != null && industrial > 0){

					FiltroCategoria filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, Categoria.INDUSTRIAL_INT));
					Collection<Categoria> categoriaCollecion = Fachada.getInstancia().pesquisar(filtroCategoria, Categoria.class.getName());

					for(Categoria categoria : categoriaCollecion){
						categoria.setQuantidadeEconomiasCategoria(new Integer(industrial));
					}

					categorias.addAll(categoriaCollecion);

				}

				if(publica != null && publica > 0){

					FiltroCategoria filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, Categoria.PUBLICO_INT));
					Collection<Categoria> categoriaCollecion = Fachada.getInstancia().pesquisar(filtroCategoria, Categoria.class.getName());

					for(Categoria categoria : categoriaCollecion){
						categoria.setQuantidadeEconomiasCategoria(new Integer(publica));
					}

					categorias.addAll(categoriaCollecion);

				}

				if(residencial != null && residencial > 0){

					FiltroCategoria filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, Categoria.RESIDENCIAL_INT));
					Collection<Categoria> categoriaCollecion = Fachada.getInstancia().pesquisar(filtroCategoria, Categoria.class.getName());

					for(Categoria categoria : categoriaCollecion){
						categoria.setQuantidadeEconomiasCategoria(new Integer(residencial));
					}

					categorias.addAll(categoriaCollecion);

				}

			}
		}

		return categorias;
	}

	public Integer retornarAnoMesReferenciaFaturamentoGrupoImovel(Integer idImovel) throws ControladorException{

		Integer amReferenciaFaturamentoGrupoImovel = null;

		try{

			amReferenciaFaturamentoGrupoImovel = this.repositorioImovel.retornarAnoMesReferenciaFaturamentoGrupoImovel(idImovel);

		}catch(ErroRepositorioException ex){

			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return amReferenciaFaturamentoGrupoImovel;
	}

}
