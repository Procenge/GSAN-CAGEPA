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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.integracao.acquagis;

import gcom.batch.ControladorBatchLocal;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.IRepositorioCadastro;
import gcom.cadastro.RepositorioCadastroHBM;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.RepositorioImovelHBM;
import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.conta.ContaGeral;
import gcom.integracao.piramide.ControladorIntegracaoPiramide;
import gcom.seguranca.acesso.ControladorAcessoLocal;
import gcom.seguranca.acesso.IRepositorioAcesso;
import gcom.seguranca.acesso.RepositorioAcessoHBM;
import gcom.util.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

/**
 * Controlador Integra��o AcquaGis
 * 
 * @author Virg�nia Melo
 * @created 07 julho 2009
 */
public class ControladorIntegracaoAcquaGis
				implements SessionBean, IControladorIntegracaoAcquaGis {

	SessionContext sessionContext;

	private static final Logger LOGGER = Logger.getLogger(ControladorIntegracaoPiramide.class);

	private static final int ENDERECO_GRAVADO = 1;

	private static final int LOGIN_INEXISTENTE_INDISPONIVEL = 2;

	private static final int LOGRADOURO_BAIRRO_INVALIDO = 3;

	private static final int LOGRADOURO_CEP_INVALIDO = 4;

	private static final int LOGRADOURO_BAIRRO_INEXISTENTE = 5;

	private static final int LOGRADOURO_CEP_INEXISTENTE = 6;

	private static final int COORDENADA_NORTE_INVALIDA = 7;

	private static final int COORDENADA_LESTE_INVALIDA = 8;

	private static final int IDENTIFICADOR_REFERENCIA_INVALIDO = 9;

	private static final int IDENTIFICADOR_REFERENCIA_INEXISTENTE = 10;

	private static final int IMOVEL_INEXISTENTE = 11;


	private static final int SEM_RESPOSTA = 0;

	protected IRepositorioUtil repositorioUtil = null;

	private IRepositorioCadastro repositorioCadastro = null;

	private IRepositorioAcesso repositorioAcesso = null;

	private IRepositorioIntegracaoAcquaGis repositorioIntegracaoAcquaGis = null;

	private IRepositorioImovel repositorioImovel = null;

	private static ControladorIntegracaoAcquaGis instancia = null;

	/**
	 * Retorna o valor de instance
	 * 
	 * @return O valor de instance
	 */
	public static ControladorIntegracaoAcquaGis getInstancia(){

		if(instancia == null){
			instancia = new ControladorIntegracaoAcquaGis();
		}
		return instancia;
	}

	public ControladorIntegracaoAcquaGis() {

		try{
			this.ejbCreate();
		}catch(CreateException e){
			e.printStackTrace();
		}
	}


	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @exception CreateException
	 *                Descri��o da exce��o
	 */
	public void ejbCreate() throws CreateException{

		repositorioUtil = RepositorioUtilHBM.getInstancia();
		repositorioCadastro = RepositorioCadastroHBM.getInstancia();
		repositorioAcesso = RepositorioAcessoHBM.getInstancia();
		repositorioIntegracaoAcquaGis = RepositorioIntegracaoAcquaGisHBM.getInstancia();
		repositorioImovel = RepositorioImovelHBM.getInstancia();

	}

	public void ejbActivate(){

	}

	public void ejbPassivate(){

	}

	public void ejbRemove(){

	}

	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;

	}

	/**
	 * Retorna o valor do ControladorBatch
	 * 
	 * @return O valor de ControladorBatch
	 */
	private ControladorBatchLocal getControladorBatch(){

		return ServiceLocator.getInstancia().getControladorBatch();
	}

	/**
	 * Retorna o valor do ControladorCobranca
	 * 
	 * @return O valor de ControladorCobranca
	 */
	private ControladorCobrancaLocal getControladorCobranca(){

		return ServiceLocator.getInstancia().getControladorCobranca();
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		return ServiceLocator.getInstancia().getControladorUtil();
	}

	/**
	 * Retorna o valor de controladorAcesso
	 * 
	 * @return O valor de controladorAcesso
	 */
	protected ControladorAcessoLocal getControladorAcesso(){

		return ServiceLocator.getInstancia().getControladorAcesso();
	}

	/**
	 * Retorna o valor de controladorAcesso
	 * 
	 * @return O valor de controladorAcesso
	 */
	protected ControladorImovelLocal getControladorImovel(){

		return ServiceLocator.getInstancia().getControladorImovel();
	}

	/**
	 * M�todo respons�vel por validar os dados recebidos pelo AcquaGis.
	 * 
	 * @author Virg�nia Melo
	 * @date 07/06/2009
	 * @param dadosAcquaGis
	 *            Objeto com os dados recebidos pelo AcquaGis.
	 * @return C�digo de retorno.
	 */
	public Integer validarDadosAcquaGis(DadosAcquaGis dadosAcquaGis) throws ControladorException{

		boolean loginOk = false;
		boolean idLogradouroBairroOk = false;	
		Integer idImovelOk = 0;

		// Validar login
		try{
			loginOk = repositorioAcesso.verificarExistenciaLogin(dadosAcquaGis.getLoginUsuario());
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(!loginOk){
			return LOGIN_INEXISTENTE_INDISPONIVEL;
		}

		// Validar logradouroBairro - se for null ou zero, n�o criticar, dados ser�o informados pela
		// atendente.
		if(dadosAcquaGis.getIdLogradouroBairro() != null && !dadosAcquaGis.getIdLogradouroBairro().equals("0")){

			if(Util.validarNumeroMaiorQueZERO(dadosAcquaGis.getIdLogradouroBairro())){

				try{
					idLogradouroBairroOk = repositorioCadastro.verificarExistenciaLogradouroBairro(Integer.valueOf(dadosAcquaGis
									.getIdLogradouroBairro()));
				}catch(ErroRepositorioException e){
					throw new ControladorException("erro.sistema", e);
				}

				if(!idLogradouroBairroOk){
					return LOGRADOURO_BAIRRO_INEXISTENTE;
				}

			}else{
				return LOGRADOURO_BAIRRO_INVALIDO;
			}

		}
		// Validar logradouroCep ?

		// Validar coordenada norte
		if(Util.validarValorNaoNumericoComoBigDecimal(dadosAcquaGis.getNumeroCoordenadaNorte())){
			return COORDENADA_NORTE_INVALIDA;
		}

		// Validar coordenada leste
		if(Util.validarValorNaoNumericoComoBigDecimal(dadosAcquaGis.getNumeroCoordenadaLeste())){
			return COORDENADA_LESTE_INVALIDA;
		}

		// Validar idReferencia ?
		
		
		//Validar Imovel
		if(dadosAcquaGis.getIdImovel() != null && !dadosAcquaGis.getIdImovel().equals("0")){

			if(Util.validarNumeroMaiorQueZERO(dadosAcquaGis.getIdImovel())){

				try{
					idImovelOk = repositorioImovel.verificarExistenciaImovel(Util.converterStringParaInteger(dadosAcquaGis.getIdImovel()));

					if(idImovelOk == 0){
						return IMOVEL_INEXISTENTE;
					}

				}catch(NumberFormatException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(ErroRepositorioException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		return ENDERECO_GRAVADO;

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.integracao.acquagis.IControladorIntegracaoAcquaGis#
	 * gerarIntegracaoAcquaGisDadosContaAtualizada(int)
	 */
	public void gerarIntegracaoAcquaGisDadosContaAtualizada(int idFuncionalidadeIniciada, Integer idLocalidade) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, idFuncionalidadeIniciada);

		LOGGER.info("=====> INICIO GERA��O DADOS CONTA ATUALIZADA PARA A LOCALIDADE: " + idLocalidade + " <=====");

		Collection<Integer> colecaoImoveis = null;
		TabelaIntegracaoContaAtualizada integracaoContaAtualizada = null;

		// seta valores constantes para chamar o metodo que consulta debitos do imovel
		Integer tipoImovel = Integer.valueOf(1);
		Integer indicadorPagamento = Integer.valueOf(2);
		Integer indicadorConta = Integer.valueOf(1);
		Integer indicadorDebito = Integer.valueOf(1);
		Integer indicadorCredito = Integer.valueOf(2);
		Integer indicadorNotas = Integer.valueOf(2);
		Integer indicadorGuias = Integer.valueOf(2);
		Integer indicadorAtualizar = Integer.valueOf(1);
		Short indicadorConsiderarPagamentoNaoClassificado = 1;
		// Para auxiliar na formata��o de uma data
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		String referenciaInicial = "01/0001";
		String referenciaFinal = "12/9999";
		String dataVencimentoInicial = "01/01/0001";
		String dataVencimentoFinal = "31/12/9999";
		Date dataVencimentoDebitoI;
		Date dataVencimentoDebitoF;

		BigDecimal valorConta = BigDecimal.ZERO;
		BigDecimal valorAcrescimo = BigDecimal.ZERO;

		String mesInicial = referenciaInicial.substring(0, 2);
		String anoInicial = referenciaInicial.substring(3, referenciaInicial.length());
		String anoMesInicial = anoInicial + mesInicial;
		String mesFinal = referenciaFinal.substring(0, 2);
		String anoFinal = referenciaFinal.substring(3, referenciaFinal.length());
		String anoMesFinal = anoFinal + mesFinal;

		try{
			dataVencimentoDebitoI = formatoData.parse(dataVencimentoInicial);
		}catch(ParseException ex){
			dataVencimentoDebitoI = null;
		}
		try{
			dataVencimentoDebitoF = formatoData.parse(dataVencimentoFinal);
		}catch(ParseException ex){
			dataVencimentoDebitoF = null;
		}

		try{
			LOGGER.info("=====> PESQUISANDO IM�VEIS DA LOCALIDADE: " + idLocalidade + " <=====");
			// Pesquisa todos os im�veis da localidade passada.
			colecaoImoveis = getControladorImovel().pesquisarImoveisPorLocalidade(idLocalidade);

			LOGGER.info("=====> CONSULTANDO D�BITOS DAS CONTAS DOS IM�VEIS DA LOCALIDADE: " + idLocalidade + " <=====");
			for(Integer idImovel : colecaoImoveis){

				// 2. Consultar todos os d�bitos de conta. Pode tomar com base os casos de uso
				// [UC0203] Consultar D�bitos / [UC0067] Obter D�bito do Im�vel ou Cliente; (Apenas
				// as contas devem ser taratdas)
				ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = getControladorCobranca().obterDebitoImovelOuCliente(
								tipoImovel.intValue(), idImovel.toString(), null, null, anoMesInicial, anoMesFinal, dataVencimentoDebitoI,
								dataVencimentoDebitoF, indicadorPagamento.intValue(), indicadorConta.intValue(),
								indicadorDebito.intValue(), indicadorCredito.intValue(), indicadorNotas.intValue(),
								indicadorGuias.intValue(), indicadorAtualizar.intValue(), null, null, new Date(), ConstantesSistema.SIM,
								indicadorConsiderarPagamentoNaoClassificado, ConstantesSistema.SIM, ConstantesSistema.SIM,
								ConstantesSistema.SIM);

				LOGGER.info("=====> INCLUINDO OS DADOS NA TABELA CONTA ATUALIZADA DO IM�VEL: " + idImovel + " <=====");
				// 3. Incluir registro com o valor da conta + acrescimos
				// Inserir registros em CONTA ATUALIZADA.
				for(ContaValoresHelper contaValoresHelper : colecaoDebitoImovel.getColecaoContasValores()){

					integracaoContaAtualizada = new TabelaIntegracaoContaAtualizada();

					valorConta = valorConta.add(contaValoresHelper.getConta().getValorTotal()
									.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
					valorAcrescimo = valorAcrescimo.add(contaValoresHelper.getValorTotalContaValores().setScale(
									Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

					// Carregar objeto Conta Atualizada.
					if(Util.isNaoNuloBrancoZero(contaValoresHelper.getConta())
									&& Util.isNaoNuloBrancoZero(contaValoresHelper.getConta().getImovel())){

						ContaGeral contaGeral = new ContaGeral();
						contaGeral.setId(contaValoresHelper.getConta().getId());

						integracaoContaAtualizada.setImovel(contaValoresHelper.getConta().getImovel());
						integracaoContaAtualizada.setContaGeral(contaGeral);
						integracaoContaAtualizada.setContaAtualizadaValor(valorConta.add(valorAcrescimo));
						integracaoContaAtualizada.setUltimaAlteracao(new Date());

						// Chamar Insert.
						getControladorUtil().inserir(integracaoContaAtualizada);
					}

					valorConta = BigDecimal.ZERO;
					valorAcrescimo = BigDecimal.ZERO;
				}
			}

			// Finaliza o processamento batch com sucesso.
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

			LOGGER.info("=====> FIM GERA��O DADOS CONTA ATUALIZADA DA LOCALIDADE: " + idLocalidade + " <=====");
		}catch(Exception e){
			// this.getControladorAcesso().registrarLogExecucaoProcesso(idFuncionalidadeIniciada,
			// e.getMessage() + e.toString() + ".");
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

	}

}
