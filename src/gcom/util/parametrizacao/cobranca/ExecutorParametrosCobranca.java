/**
 * 
 */

package gcom.util.parametrizacao.cobranca;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.PagamentoTipo;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.EnvioEmail;
import gcom.cadastro.cliente.*;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cadastro.endereco.FiltroLogradouroBairro;
import gcom.cadastro.endereco.FiltroLogradouroCep;
import gcom.cadastro.geografico.ControladorGeograficoLocal;
import gcom.cadastro.geografico.ControladorGeograficoLocalHome;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.cobranca.bean.CobrancaDocumentoAvisoCorteHelper;
import gcom.cobranca.bean.CobrancaDocumentoColecoesOrdenadasHelper;
import gcom.cobranca.bean.EmitirAvisoCobrancaHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.relatorio.cobranca.RelatorioAvisoCorte;
import gcom.relatorio.cobranca.boletobancario.RelatorioGeracaoCartasCobrancaBancaria;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.email.ServicosEmail;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

import javax.ejb.CreateException;

import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * @author bferreira
 */
public class ExecutorParametrosCobranca
				implements ExecutorParametro {

	private static final String SEPARADOR_LINHA = System.getProperty("line.separator");

	private static final String LINHA_QUEBRA_PAGINA = Util.completaString("%%XGF PAGEBRK", 3550);

	private static final String SEPARACAO_CODIGO_BARRAS = Util.completaString("", 4);

	private static final ExecutorParametrosCobranca instancia = new ExecutorParametrosCobranca();

	protected IRepositorioCobranca repositorioCobranca;

	public static ExecutorParametrosCobranca getInstancia(){

		return instancia;
	}

	private ExecutorParametrosCobranca() {

	}

	public void ejbCreate() throws CreateException{

		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();
	}

	/**
	 * Retorna a instância de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	protected ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna a instância de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	protected ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorCliente
	 * 
	 * @return O valor de controladorImovel
	 */
	protected ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorCobranca
	 * 
	 * @return O valor de controladorCobranca
	 */
	protected ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCobrancaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorCobrancaOrdemCorte
	 * 
	 * @return O valor de controladorCobrancaOrdemCorte
	 */
	protected ControladorCobrancaOrdemCorteLocal getControladorCobrancaOrdemCorte(){

		ControladorCobrancaOrdemCorteLocalHome localHome = null;
		ControladorCobrancaOrdemCorteLocal local = null;
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaOrdemCorteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_ORDEM_CORTE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}


	protected ControladorCobrancaAvisoDebitoLocal getControladorCobrancaAvisoDebito(){

		ControladorCobrancaAvisoDebitoLocalHome localHome = null;
		ControladorCobrancaAvisoDebitoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCobrancaAvisoDebitoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_AVISO_DEBITO_SEJB);
			local = localHome.create();
			return local;

		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorArrecadacao
	 * 
	 * @return O valor de controladorArrecadacao
	 */
	protected ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorEndereco
	 * 
	 * @return O valor de controladorEndereco
	 */
	protected ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorCadastro
	 * 
	 * @return O valor de controladorCadastro
	 */
	protected ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorGeografico
	 * 
	 * @return O valor de controladorGeografico
	 */
	protected ControladorGeograficoLocal getControladorGeografico(){

		ControladorGeograficoLocalHome localHome = null;
		ControladorGeograficoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorGeograficoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_GEOGRAFICO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * [UC3018] Gerar TXT Cartas Cobrança Bancária.
	 * [SB0001 – Gerar Cartas Cobrança Bancária – Modelo 1]
	 * 
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param parametroSistema
	 * @param colecaoCobrancaDocumento
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoTxtCartasCobrancaBancariaModelo1(ParametroSistema parametroSistema,
					Collection<CobrancaDocumento> colecaoCobrancaDocumento, SistemaParametro sistemaParametro, Integer idComandoCobranca,
					Usuario usuario) throws ControladorException, ErroRepositorioException, IOException, CreateException{

		this.ejbCreate();

		StringBuffer cartaTxt = new StringBuffer();
		Iterator itCobrancaDocumento = colecaoCobrancaDocumento.iterator();
		CobrancaDocumento cobrancaDocumento = null;
		BoletoBancario boletoBancario = null;
		Collection colecaoContas = null;
		Imovel imovel = null;
		int numeroBoletos = 0;
		int numeroContas = 0;
		String auxiliar = null;
		int tamanhoString = 0;
		BigDecimal totalDebito = BigDecimal.ZERO;
		Integer numeroDiasVencimento;
		Date dataCorrente;
		String nomeArquivo = "";
		String ano = null;
		String mes = null;
		String dia = null;

		// 1. Gerar a primeira linha de controle do arquivo TXT.
		cartaTxt.append("%!");

		cartaTxt.append(Util.completaString("", 78));

		cartaTxt.append(SEPARADOR_LINHA);

		// 2. Gerar a segunda linha de controle do arquivo TXT.
		cartaTxt.append("(desocarta.jdt) STARTLM");

		cartaTxt.append(Util.completaString("", 57));

		cartaTxt.append(SEPARADOR_LINHA);

		// 3. Para cada documento de cobrança selecionado:
		while(itCobrancaDocumento.hasNext()){
			cobrancaDocumento = (CobrancaDocumento) itCobrancaDocumento.next();

			if(cobrancaDocumento.getImovel() != null && cobrancaDocumento.getImovel().getId() != null){
				imovel = cobrancaDocumento.getImovel();
			}

			// 3.1. Selecionar as contas associadas ao documento de cobrança (a partir da tabela
			// COBRANCA_DOCUMENTO_ITEM com CBDO_ID=CBDO_ID da tabela COBRANCA_DOCUMENTO e
			// CNTA_ID=CNTA_ID da tabela CONTA, ordenando pela referenciada conta
			// (CNTA_AMREFERENCIACONTA)).
			colecaoContas = repositorioCobranca.pesquisarCobrancaDocumentoItemComConta(cobrancaDocumento.getId());

			// 3.2. Gerar as linhas do arquivo texto com os dados da carta.
			// 3.2.1. Linha 1.
			cartaTxt.append("11");

			if(cobrancaDocumento.getCliente() != null && cobrancaDocumento.getCliente().getNome() != null){
				cartaTxt.append(Util.completaString(cobrancaDocumento.getCliente().getNome().trim(), 30));
			}else{
				cartaTxt.append(Util.completaString("", 30));
			}

			cartaTxt.append(Util.completaString("", 35));

			numeroBoletos = numeroBoletos + 1;
			if(numeroBoletos < 1000){
				// if((numeroBoletos + "").length() == 1){
				// cartaTxt.append("    " + numeroBoletos);
				// }else if((numeroBoletos + "").length() == 2){
				// cartaTxt.append("   " + numeroBoletos);
				// }else if((numeroBoletos + "").length() == 3){
				// cartaTxt.append("  " + numeroBoletos);
				// }
				cartaTxt.append(Util.completaStringComEspacoAEsquerda(numeroBoletos + "", 5));
			}else{
				cartaTxt.append(Util.formatarNumeroInteiro(numeroBoletos));
			}

			cartaTxt.append(Util.completaString("", 8));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.2. Linha 2.
			cartaTxt.append(" 1");

			if(imovel != null){
				cartaTxt.append(Util.completaString(this.getControladorEndereco().pesquisarEndereco(imovel.getId()), 60));
			}else{
				cartaTxt.append(Util.completaString("", 60));
			}

			cartaTxt.append(Util.completaString("", 18));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.3. Linha 3.
			cartaTxt.append(" 1");

			if(cobrancaDocumento.getLocalidade() != null && cobrancaDocumento.getLocalidade().getId() != null){
				cartaTxt.append(Util.completaString(this.getControladorCadastro().obterDescricaoLocalidade(
								cobrancaDocumento.getLocalidade().getId()), 70));
			}else{
				cartaTxt.append(Util.completaString("", 70));
			}

			cartaTxt.append(Util.completaString("", 8));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.4. Linha 4.
			cartaTxt.append("04");

			cartaTxt.append(Util.completaString("", 4));

			// TODO Verificar Formatação de acordo com o Caso de uso.
			if(imovel != null){
				tamanhoString = imovel.getId().toString().length();
				// Formata para 99999999.9
				auxiliar = imovel.getId().toString().substring(0, tamanhoString - 1) + "."
								+ imovel.getId().toString().substring(tamanhoString - 1, tamanhoString);
				cartaTxt.append(Util.adicionarZerosEsquedaNumero(10, auxiliar));
			}else{
				cartaTxt.append(Util.adicionarZerosEsquedaNumero(10, "0"));
			}

			cartaTxt.append(Util.completaString("", 64));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.5. Linha 5.
			cartaTxt.append(" 4");

			cartaTxt.append(Util.completaString("", 4));

			// TODO Verificar Formatação de acordo com o Caso de uso.
			if(imovel != null){
				cartaTxt.append(this.getControladorImovel().pesquisarInscricaoImovel(imovel.getId(), true));
			}else{
				cartaTxt.append(Util.adicionarZerosEsquedaNumero(20, "0"));
			}

			cartaTxt.append(Util.completaString("", 54));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.6. Linha 6.
			cartaTxt.append("-3");

			cartaTxt.append(Util.completaString("A V I S O        D E        C O B R A N C A", 50));

			cartaTxt.append(Util.completaString("", 28));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.7. Linha 7.
			cartaTxt.append("-4");

			if(cobrancaDocumento.getLocalidade() != null && cobrancaDocumento.getLocalidade().getId() != null){
				cartaTxt.append(Util.completaString(this.getMunicipioComDataEmissaoPorExtenso(cobrancaDocumento.getLocalidade().getId()),
								54));
			}else{
				cartaTxt.append(Util.completaString("", 54));
			}

			cartaTxt.append(Util.completaString("", 24));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.8. Linha 8.
			cartaTxt.append("04");

			cartaTxt.append(Util.completaString("SR(A) CLIENTE,", 18));

			cartaTxt.append(Util.completaString("", 60));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.9. Linha 9.
			cartaTxt.append("04");

			cartaTxt.append(Util.completaString("CONSTATAMOS EM NOSSOS  REGISTROS QUE  SE ENCONTRA  EM ABERTO,  O  DEBITO", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.10. Linha 10.
			cartaTxt.append(" 4");

			cartaTxt.append(Util.completaString("REFERENTE A(S) CONTA(S) DE AGUA E ESGOTOS EM ATRASO EM VALORES CORRIGIDOS:", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.11. Para cada duas (2) contas selecionadas, emitir dados da conta (caso o número
			// de contas selecionadas seja ímpar, a última conta selecionada será emitida com os
			// dados correspondentes à segunda conta sem valor).
			if(colecaoContas != null && !colecaoContas.isEmpty()){

				numeroContas = colecaoContas.size();
				Iterator itContas = colecaoContas.iterator();
				Conta conta1 = null;
				Conta conta2 = null;
				CobrancaDocumentoItem cobrancaDocumentoItem1 = null;
				CobrancaDocumentoItem cobrancaDocumentoItem2 = null;
				BigDecimal valorConta1 = BigDecimal.ZERO;
				BigDecimal valorConta2 = BigDecimal.ZERO;

				while(itContas.hasNext()){
					conta1 = (Conta) itContas.next();
					cobrancaDocumentoItem1 = repositorioCobranca.pesquisarCobrancaDocumentoItemPelaConta(cobrancaDocumento.getId(), conta1
									.getId());

					if(cobrancaDocumentoItem1.getValorItemCobrado() != null && cobrancaDocumentoItem1.getValorAcrescimos() != null){
						valorConta1 = Util.somaBigDecimal(cobrancaDocumentoItem1.getValorItemCobrado(), cobrancaDocumentoItem1
										.getValorAcrescimos());
					}else if(cobrancaDocumentoItem1.getValorItemCobrado() != null && cobrancaDocumentoItem1.getValorAcrescimos() == null){
						valorConta1 = cobrancaDocumentoItem1.getValorItemCobrado();
					}else if(cobrancaDocumentoItem1.getValorItemCobrado() == null && cobrancaDocumentoItem1.getValorAcrescimos() != null){
						valorConta1 = cobrancaDocumentoItem1.getValorAcrescimos();
					}

					cartaTxt.append("05");

					cartaTxt.append(Util.formatarAnoMesSemBarraParaMesAnoComBarra(conta1.getReferencia()));

					cartaTxt.append(Util.completaString("", 2));

					cartaTxt.append(Util.completaString("VENC. ", 6));

					cartaTxt.append(Util.formatarData(conta1.getDataVencimentoConta()));

					cartaTxt.append(Util.completaString("", 1));

					cartaTxt.append(Util.completaStringComEspacoAEsquerda(valorConta1.toString().replace(".", ","), 9));

					cartaTxt.append(Util.completaString("", 3));

					if(itContas.hasNext()){
						conta2 = (Conta) itContas.next();
						cobrancaDocumentoItem2 = repositorioCobranca.pesquisarCobrancaDocumentoItemPelaConta(cobrancaDocumento.getId(),
										conta2.getId());

						if(cobrancaDocumentoItem2.getValorItemCobrado() != null && cobrancaDocumentoItem2.getValorAcrescimos() != null){
							valorConta2 = Util.somaBigDecimal(cobrancaDocumentoItem2.getValorItemCobrado(), cobrancaDocumentoItem2
											.getValorAcrescimos());
						}else if(cobrancaDocumentoItem2.getValorItemCobrado() != null
										&& cobrancaDocumentoItem2.getValorAcrescimos() == null){
							valorConta2 = cobrancaDocumentoItem2.getValorItemCobrado();
						}else if(cobrancaDocumentoItem2.getValorItemCobrado() == null
										&& cobrancaDocumentoItem2.getValorAcrescimos() != null){
							valorConta2 = cobrancaDocumentoItem2.getValorAcrescimos();
						}

						cartaTxt.append(Util.formatarAnoMesSemBarraParaMesAnoComBarra(conta2.getReferencia()));

						cartaTxt.append(Util.completaString("", 2));

						cartaTxt.append(Util.completaString("VENC. ", 6));

						cartaTxt.append(Util.formatarData(conta2.getDataVencimentoConta()));

						cartaTxt.append(Util.completaString("", 1));

						cartaTxt.append(Util.completaStringComEspacoAEsquerda(valorConta2.toString().replace(".", ","), 9));

						cartaTxt.append(Util.completaString("", 5));

						cartaTxt.append(SEPARADOR_LINHA);
					}else{
						cartaTxt.append(SEPARADOR_LINHA);
					}
				}
			}

			// 3.2.12. Total das contas.
			cartaTxt.append("05");

			cartaTxt.append(Util.completaString("N. CONTA(S) EM ABERTO - ", 24));

			cartaTxt.append(Util.completaString(numeroContas + "", 2));

			cartaTxt.append(Util.completaString("", 5));

			cartaTxt.append(Util.completaString("TOTAL DO DEBITO - ", 20));

			boletoBancario = repositorioCobranca.pesquisarBoletoBancarioPorCobrancaDocumento(cobrancaDocumento.getId());

			if(boletoBancario != null){
				totalDebito = boletoBancario.getValorDebito();

				if(totalDebito != null){
					cartaTxt.append(Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(totalDebito), 13));
				}else{
					cartaTxt.append(Util.completaString("", 13));
				}
			}else{
				cartaTxt.append(Util.completaString("", 13));
			}

			cartaTxt.append(Util.completaString("", 14));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.13. Linha 11.
			cartaTxt.append("04");

			cartaTxt.append(Util.completaString("INFORMAMOS QUE VOSSA SENHORIA RECEBERÁ EM ATÉ ", 46));

			numeroDiasVencimento = repositorioCobranca.pesquisarNumeroDiasVencimentoCobrancaAcao(cobrancaDocumento.getCobrancaAcao()
							.getId());

			if(numeroDiasVencimento != null){
				cartaTxt.append(Util.completaStringComEspacoAEsquerda(numeroDiasVencimento.toString(), 2));
			}else{
				cartaTxt.append(Util.completaString("", 2));
			}

			if(numeroDiasVencimento != null){
				cartaTxt.append(Util.completaString(" (" + Util.numero(numeroDiasVencimento) + ") DIAS,", 30));
			}else{
				cartaTxt.append(Util.completaString("", 30));
			}

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.14. Linha 12.
			cartaTxt.append(" 4");

			cartaTxt.append(Util.completaString("UM BOLETO DE COBRANÇA BANCÁRIA PARA QUITACAO E REGULARIZAÇÃO DA PENDÊNCIA", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.15. Linha 13.
			cartaTxt.append(" 4");

			cartaTxt.append(Util.completaString("ESTANDO SUJEITO A PROTESTO O TITULO BANCARIO APÓS O PRAZO FIXADO PARA A  ", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.16. Linha 14.
			cartaTxt.append(" 4");

			cartaTxt.append(Util.completaString("QUITAÇÃO.", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.17. Linha 15.
			cartaTxt.append("04");

			cartaTxt.append(Util.completaString("PARA ESCLARECIMENTOS ESTAMOS A DISPOSICAO NA  SUPERINTENDENCIA COMERCIAL,", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.18. Linha 16.
			cartaTxt.append(" 4");

			cartaTxt.append(Util.completaString(" SITUADA NA "
							+ (String) ParametroCobranca.P_ENDERECO_SUPERINTENDENCIA_COMERCIAL.executar(ExecutorParametrosCobranca
											.getInstancia()) + ".", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.19. Linha 17.
			cartaTxt.append("04");

			cartaTxt.append(Util.completaString("CASO JA TENHA QUITADO O REFERIDO DEBITO, FAVOR APRESENTAR OS  RECIBOS  NO", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.20. Linha 18.
			cartaTxt.append(" 4");

			cartaTxt.append(Util.completaString(" ENDERECO ACIMA PARA A DEVIDA BAIXA.", 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.2.21. Linha 19
			cartaTxt.append("01");

			cartaTxt.append(Util.completaString(sistemaParametro.getNomeEmpresa(), 78));

			cartaTxt.append(SEPARADOR_LINHA);

			// 3.3. Atualizar a data de geração da carta (atualiza a tabela BOLETO_BANCARIO).
			dataCorrente = new Date();

			boletoBancario.setDataGeracaoCarta(dataCorrente);
			boletoBancario.setUltimaAlteracao(dataCorrente);

			try{
				this.getControladorUtil().atualizar(boletoBancario);
			}catch(Exception e){
				// Gera o Email do Erro.
				this.gerarEmailErroGerarCartaCobrancaBancaria(e, EnvioEmail.GERAR_CARTA_COBRANCA_BANCARIA);
			}

		}

		// 4. Gerar a última linha de controle do arquivo TXT.
		cartaTxt.append("%%EOF");

		cartaTxt.append(Util.completaString("", 75));

		cartaTxt.append(SEPARADOR_LINHA);

		// 5. O sistema atribui o nome para o arquivo TXT.
		Calendar dataCalendar = new GregorianCalendar();

		ano = "" + dataCalendar.get(Calendar.YEAR);
		mes = "" + (dataCalendar.get(Calendar.MONTH) + 1);
		dia = "" + dataCalendar.get(Calendar.DAY_OF_MONTH);

		mes = Util.adicionarZerosEsquedaNumero(2, mes);
		dia = Util.adicionarZerosEsquedaNumero(2, dia);

		Short codigoAgenteArrecadador = repositorioCobranca.pesquisarCodigoAgenteArrecadadorPorComandoCobranca(idComandoCobranca);

		nomeArquivo = "CARTACOBRBANC_ARREC_" + codigoAgenteArrecadador + "_" + dia + mes + ano + ".TXT";

		// Gera o Arquivo.
		this.enviarArquivoGeracaoCartaCobrancaBancariaParaBatch(cartaTxt, nomeArquivo, usuario);
	}

	/**
	 * [UC3018] Gerar TXT Cartas Cobrança Bancária.
	 * [SB0002 – Gerar Cartas Cobrança Bancária – Modelo 2]
	 * 
	 * @author Luciano Galvão
	 * @date 17/04/2012
	 * @param parametroSistema
	 * @param colecaoCobrancaDocumento
	 * @param sistemaParametro
	 * @param idComandoCobranca
	 * @param usuario
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoTxtCartasCobrancaBancariaModelo2(ParametroSistema parametroSistema,
					Collection<CobrancaDocumento> colecaoCobrancaDocumento, SistemaParametro sistemaParametro, Integer idComandoCobranca,
					Usuario usuario) throws ControladorException, ErroRepositorioException, IOException, CreateException{

		this.ejbCreate();

		StringBuffer cartaTxt = new StringBuffer();
		Iterator itCobrancaDocumento = colecaoCobrancaDocumento.iterator();
		CobrancaDocumento cobrancaDocumento = null;
		BoletoBancario boletoBancario = null;
		Imovel imovel = null;
		int sequencial = 0;
		int numeroLinha = 0;
		Date dataCorrente = new Date();

		// 1. Gerar a primeira linha de controle do arquivo TXT.
		cartaTxt.append("%!");
		cartaTxt.append(Util.completaString("", 3548));
		cartaTxt.append(SEPARADOR_LINHA);

		// 2. Gerar a segunda linha de controle do arquivo TXT.
		cartaTxt.append(Util.completaString("(mdavi01.jdt) STARTLM", 30));
		cartaTxt.append(Util.completaString("", 3520));
		cartaTxt.append(SEPARADOR_LINHA);

		// 3. Para cada documento de cobrança selecionado:
		while(itCobrancaDocumento.hasNext()){
			cobrancaDocumento = (CobrancaDocumento) itCobrancaDocumento.next();

			boletoBancario = repositorioCobranca.pesquisarBoletoBancarioPorCobrancaDocumento(cobrancaDocumento.getId());

			if(cobrancaDocumento.getImovel() != null && cobrancaDocumento.getImovel().getId() != null){
				imovel = cobrancaDocumento.getImovel();
				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_BAIRRO + "." + FiltroLogradouroBairro.BAIRRO);
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_CEP + "." + FiltroLogradouroCep.CEP);

				imovel = (Imovel) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()));
			}

			// Selecionar as contas associadas ao documento de cobrança
			Collection colecaoContas = repositorioCobranca.pesquisarCobrancaDocumentoItemComConta(cobrancaDocumento.getId());

			// Inicia a geração de uma linha
			numeroLinha = numeroLinha + 1;

			// 3.2.1 Gerar dados do imóvel
			// Campo 11 - Incrementa o número de boletos
			sequencial = sequencial + 1;

			// indicador se esta é a última cobrança da coleção a ser processada
			boolean ultimaCobranca = !itCobrancaDocumento.hasNext();

			// inicialmente o contador "numeroLinha" computa uma linha por CobrancaDocumento. Aqui
			// identificamos quantas linhas a mais são processadas para uma CobrancaDocumento, para
			// adicionar ao numeroLinha após a geração do conteúdo
			int linhasComplementares = colecaoContas.size() / 73;

			cartaTxt.append(gerarLinhasCobrancaDocumento(cobrancaDocumento, boletoBancario, imovel, colecaoContas, sequencial, numeroLinha,
							false, ultimaCobranca));

			numeroLinha = numeroLinha + linhasComplementares;

			// Atualizar a data de geração da carta (atualiza a tabela BOLETO_BANCARIO).
			boletoBancario.setDataGeracaoCarta(dataCorrente);
			boletoBancario.setUltimaAlteracao(dataCorrente);

			try{
				this.getControladorUtil().atualizar(boletoBancario);
			}catch(Exception e){
				// Gera o Email do Erro.
				this.gerarEmailErroGerarCartaCobrancaBancaria(e, EnvioEmail.GERAR_CARTA_COBRANCA_BANCARIA);
			}

		}

		// Gerar a última linha de controle do arquivo TXT.
		cartaTxt.append(Util.completaString("%%EOF", 80));

		cartaTxt.append(SEPARADOR_LINHA);

		// O sistema atribui o nome para o arquivo TXT.
		String dataCorrenteFormatada = Util.formatarDataSemBarraDDMMAAAA(new Date());

		Short codigoAgenteArrecadador = repositorioCobranca.pesquisarCodigoAgenteArrecadadorPorComandoCobranca(idComandoCobranca);

		String nomeArquivo = "CARTACOBRBANC_ARREC_" + codigoAgenteArrecadador + "_" + dataCorrenteFormatada + ".TXT";

		// Gera o Arquivo.
		this.enviarArquivoGeracaoCartaCobrancaBancariaParaBatch(cartaTxt, nomeArquivo, usuario);
	}

	private StringBuffer gerarLinhasCobrancaDocumento(CobrancaDocumento cobrancaDocumento, BoletoBancario boletoBancario, Imovel imovel,
					Collection colecaoContas, int sequencial, int numeroLinha, boolean indicadorContinuacao, boolean ultimaCobranca)
					throws ErroRepositorioException, ControladorException{

		StringBuffer conteudo = new StringBuffer();
		boolean precisaContinuar = false;

		// Campo 2 - Matrícula do Imóvel
		String matriculaImovel = imovel.getId().toString();
		String matriculaImovelFormatada = matriculaImovel.substring(0, matriculaImovel.length() - 1) + "."
						+ matriculaImovel.substring(matriculaImovel.length() - 1);

		// Campo 5 - Rota do imóvel
		String rotaCodigo = "0";
		if((imovel.getRota() != null) && (imovel.getRota().getCodigo() != null)){
			rotaCodigo = imovel.getRota().getCodigo().toString();
		}

		// Campo 6 - Segmento
		String numeroSegmento = "0";
		if(imovel.getNumeroSegmento() != null){
			numeroSegmento = imovel.getNumeroSegmento().toString();
		}

		// Campo 13 - Total a pagar
		String totalDebitoFormatado = "****.***,**";

		// Se haverá continuação na próxima página, não atribui valor ao total do débito. Este valor
		// aparecerá apenas na última página da carta de cobrança
		if(colecaoContas.size() <= 72){
			BigDecimal totalDebito = BigDecimal.ZERO;
			if(boletoBancario != null){
				totalDebito = boletoBancario.getValorDebito();

				if(totalDebito != null){
					totalDebitoFormatado = Util.formatarMoedaReal(totalDebito);
				}
			}
		}

		// Preenche os Dados do Imóvel - SB9001 (Campos 1 a 13) - Posições 1 a 147
		conteudo.append(Util.completaString(imovel.getLocalidade().getDescricao(), 15));
		conteudo.append(Util.completaStringComEspacoAEsquerda(matriculaImovelFormatada, 10));
		conteudo.append(Util.completarStringComValorEsquerda(imovel.getLocalidade().getId().toString(), "0", 3));
		conteudo.append(Util.completarStringComValorEsquerda(String.valueOf(imovel.getSetorComercial().getCodigo()), "0", 2));
		conteudo.append(Util.completarStringComValorEsquerda(rotaCodigo, "0", 2));
		conteudo.append(Util.completarStringComValorEsquerda(numeroSegmento, "0", 2));
		conteudo.append(Util.completarStringComValorEsquerda(String.valueOf(imovel.getLote()), "0", 4));
		conteudo.append(Util.completarStringComValorEsquerda(String.valueOf(imovel.getSubLote()), "0", 2));

		if(cobrancaDocumento.getCliente() != null && cobrancaDocumento.getCliente().getNome() != null){
			conteudo.append(Util.completaString(cobrancaDocumento.getCliente().getNome(), 30));
		}else{
			conteudo.append(Util.completaString("", 30));
		}

		conteudo.append(Util.completaString(this.getControladorEndereco().pesquisarEndereco(imovel.getId()), 50));
		conteudo.append(Util.completarStringComValorEsquerda(sequencial + "", "0", 6));
		conteudo.append(Util.formatarData(new Date()));
		conteudo.append(Util.completarStringComValorEsquerda(totalDebitoFormatado, "*", 11));

		// [SB9002] - Gerar Dados das Contas

		Collection contasProcessadas = new ArrayList<Conta>();

		if(!Util.isVazioOrNulo(colecaoContas)){

			Object[] contasArray = (Object[]) colecaoContas.toArray();
			Conta conta = null;
			int posConta = 0;

			for(int i = 1; i <= contasArray.length; i++){

				posConta = posConta + 1;

				conta = (Conta) contasArray[i - 1];

				// [SB9003] - Caso seja a primenta conta da linha de continuação
				if(indicadorContinuacao && (i == 1)){
					conteudo.append(Util.completaString("...C O N T I N U A C A O", 28));
					posConta = posConta + 1;

				}

				// Campo 3 - Valor da Conta
				BigDecimal valorConta = BigDecimal.ZERO;

				CobrancaDocumentoItem cobrancaDocumentoItem = repositorioCobranca.pesquisarCobrancaDocumentoItemPelaConta(cobrancaDocumento
								.getId(), conta.getId());

				if(cobrancaDocumentoItem.getValorItemCobrado() != null){
					valorConta = Util.somaBigDecimal(valorConta, cobrancaDocumentoItem.getValorItemCobrado());
				}
				if(cobrancaDocumentoItem.getValorAcrescimos() != null){
					valorConta = Util.somaBigDecimal(valorConta, cobrancaDocumentoItem.getValorAcrescimos());
				}

				// Prencher dados da conta
				conteudo.append(conta.getReferenciaFormatada());
				conteudo.append(Util.formatarData(conta.getDataVencimentoConta()));
				conteudo.append(Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(valorConta), 11));

				// Registra que a conta foi processada nesta linha
				contasProcessadas.add(conta);

				// [SB9004] - Verificar estouro do limite de contas por Linha
				if((posConta == 71) && (contasArray.length > 72)){

					conteudo.append(Util.completaString("C O N T I N U A...", 28));

					// Remove a contas já processadas. O restante será processado na próxima linha
					colecaoContas.removeAll(contasProcessadas);
					precisaContinuar = true;
					break;

				}
			}
		}

		// Complementa espaço reservado para contas
		conteudo = new StringBuffer(Util.completaString(conteudo.toString(), 2163));

		// [SB9005] - Gerar dados finais da linha

		// Campo 1 - Bairro
		String bairro = "";
		if((imovel.getLogradouroBairro() != null) && (imovel.getLogradouroBairro().getBairro() != null)){
			bairro = imovel.getLogradouroBairro().getBairro().getNome();
		}else if((imovel.getLogradouroCep() != null) && (imovel.getLogradouroCep().getCep() != null)){
			bairro = imovel.getLogradouroCep().getCep().getBairro();
		}

		// Campo 2 - CEP
		String cep = "";
		if((imovel.getLogradouroCep() != null) && (imovel.getLogradouroCep().getCep() != null)){
			cep = imovel.getLogradouroCep().getCep().getCodigo().toString();
		}

		// Preencher dados ginais da linha
		conteudo.append(Util.completaString(bairro, 25));
		conteudo.append(Util.completaString(cep, 10));

		// Insere a quebra de linha
		conteudo.append(SEPARADOR_LINHA);

		// Se é uma linha par e haverá novas linhas, ou seja, esta não é a última cobrança ou, neste
		// cobrança, será necessário continuar com a geração de novas linhas
		if(Util.isNumeroPar(numeroLinha) && ((!ultimaCobranca) || (precisaContinuar))){
			conteudo.append(LINHA_QUEBRA_PAGINA);
			conteudo.append(SEPARADOR_LINHA);
		}

		// Verifica se precisará gerar nova linha para esta cobrança
		if(precisaContinuar){
			// Gera uma próxima linha para esta Cobrança. A quantidade de contas excede 72
			conteudo.append(gerarLinhasCobrancaDocumento(cobrancaDocumento, boletoBancario, imovel, colecaoContas, sequencial,
							(numeroLinha + 1), true, ultimaCobranca));
		}

		return conteudo;
	}



	/**
	 * Método que retorna o Municipio formata com data, como se fosse um cabeçalho.
	 * Ex: Recife, 12 DE Outubro DE 2011.
	 * 
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idLocalidade
	 * @return
	 * @throws ControladorException
	 */
	private String getMunicipioComDataEmissaoPorExtenso(Integer idLocalidade) throws ControladorException{

		String retorno = null;
		String ano = null;
		int mes;
		String dia = null;

		Calendar dataCalendar = new GregorianCalendar();

		ano = "" + dataCalendar.get(Calendar.YEAR);
		mes = dataCalendar.get(Calendar.MONTH) + 1;
		dia = "" + dataCalendar.get(Calendar.DAY_OF_MONTH);

		dia = Util.adicionarZerosEsquedaNumero(2, dia);

		retorno = getControladorGeografico().pesquisarNomeMunicipioPorLocalidade(idLocalidade);

		retorno = retorno.trim();

		retorno = retorno + ", " + dia + " DE " + Util.retornaDescricaoMes(mes).toUpperCase() + " DE " + ano;

		return retorno;
	}

	/**
	 * [UC3018] Gerar TXT Cartas Cobrança Bancária.
	 * [SB0001 – Gerar Cartas Cobrança Bancária – Modelo 1]
	 * Gera o arquivo de Cartas de Cobrança Bancária.
	 * 
	 * @author Ailton Sousa
	 * @date 13/10/2011
	 * @param arquivo
	 * @param nomeArquivo
	 * @throws ControladorException
	 */
	public void enviarArquivoGeracaoCartaCobrancaBancariaParaBatch(StringBuffer sb, String nomeArquivo, Usuario usuario)
					throws ControladorException{

		// Usuario usuario = new Usuario();
		// usuario.setId(Usuario.ID_USUARIO_ADM_SISTEMA);

		RelatorioGeracaoCartasCobrancaBancaria relatorio = new RelatorioGeracaoCartasCobrancaBancaria(usuario);

		relatorio.addParametro("arquivoTexto", sb);
		relatorio.addParametro("nomeArquivo", nomeArquivo);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);

		this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

	}

	/**
	 * [UC3018] Gerar TXT Cartas Cobrança Bancária.
	 * [SB0001 – Gerar Cartas Cobrança Bancária – Modelo 1]
	 * Gera o arquivo de Cartas de Cobrança Bancária.
	 * 
	 * @author Ailton Sousa
	 * @date 13/10/2011
	 * @param arquivo
	 * @param nomeArquivo
	 * @throws ControladorException
	 */
	public void enviarArquivoGerarAvisoCorteModeloParaBatch(StringBuffer sb, String nomeArquivo, Usuario usuario)
					throws ControladorException{

		// Usuario usuario = new Usuario();
		// usuario.setId(Usuario.ID_USUARIO_ADM_SISTEMA);

		RelatorioGeracaoCartasCobrancaBancaria relatorio = new RelatorioGeracaoCartasCobrancaBancaria(usuario);

		relatorio.addParametro("arquivoTexto", sb);
		relatorio.addParametro("nomeArquivo", nomeArquivo);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);

		this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

	}

	/**
	 * Gera o email relatando o erro dado no processamento de geração das Cartas de Cobrança
	 * Bancária.
	 * 
	 * @author Ailton Sousa
	 * @date 13/10/2011
	 * @param e
	 * @param idEnvioEmail
	 * @throws ControladorException
	 */
	private void gerarEmailErroGerarCartaCobrancaBancaria(Exception e, Integer idEnvioEmail) throws ControladorException{

		try{
			String mensagem = e.getMessage();
			if(mensagem != null){
				if(!mensagem.startsWith("erro.") && !mensagem.startsWith("atencao.")){
					mensagem = "erro.sistema";
				}
			}else{
				mensagem = "erro.sistema";
			}

			EnvioEmail envioEmailError = getControladorCadastro().pesquisarEnvioEmail(idEnvioEmail);

			String emailRemetente = envioEmailError.getEmailRemetente();
			String tituloMensagem = envioEmailError.getTituloMensagem();
			String emailReceptor = envioEmailError.getEmailReceptor();
			String corpoMensagem = envioEmailError.getCorpoMensagem();
			if(e instanceof ControladorException){
				ServicosEmail.enviarMensagem(emailRemetente, emailReceptor, tituloMensagem, corpoMensagem + "\n"
								+ ((ControladorException) e).getMensagem());
			}else{
				ServicosEmail.enviarMensagem(emailRemetente, emailReceptor, tituloMensagem, corpoMensagem + "\n"
								+ ConstantesAplicacao.get(mensagem));
			}

		}catch(Exception e2){
			e2.printStackTrace();
			throw new ControladorException("erro.sistema", e2);
		}
	}

	/**
	 * Chama o caso de uso [UC0476] Emitir Documento de Cobrança – Ordem de Corte
	 * [SB0002] – Gerar Arquivo TXT Ordem de Corte – Modelo 1
	 * 
	 * @author Hugo Lima
	 * @date 06/06/2012
	 * @param parametroSistema
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param dataAtualPesquisa
	 * @param acaoCobranca
	 * @param grupoCobranca
	 * @param cobrancaCriterio
	 * @param usuario
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarOrdemCorteArquivoTXTModelo1(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException, ErroRepositorioException, IOException, CreateException{

		this.ejbCreate();

		this.getControladorCobranca().emitirDocumentoCobrancaOrdemCorte(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						dataAtualPesquisa, acaoCobranca, grupoCobranca, cobrancaCriterio);

	}

	/**
	 * Chama o caso [UC0476] Emitir Documento de Cobrança – Ordem de Corte
	 * [UC0476] [SB0003] – Gerar Arquivo TXT Ordem de Corte – Modelo 2
	 * 
	 * @author Hugo Lima
	 * @date 06/06/2012
	 * @param parametroSistema
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param dataAtualPesquisa
	 * @param acaoCobranca
	 * @param grupoCobranca
	 * @param cobrancaCriterio
	 * @param usuario
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarOrdemCorteArquivoTXTModelo2(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException, ErroRepositorioException, IOException, CreateException{

		this.ejbCreate();

		this.getControladorCobranca().emitirOrdemCorteArquivoTXT(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando, usuario);

	}

	/**
	 * Chama o caso [UC0476] Emitir Documento de Cobrança – Ordem de Corte
	 * [UC0476] [SB0003] – Gerar Arquivo TXT Ordem de Corte – Modelo 2
	 * 
	 * @author Hugo Lima
	 * @date 06/06/2012
	 * @param parametroSistema
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param dataAtualPesquisa
	 * @param acaoCobranca
	 * @param grupoCobranca
	 * @param cobrancaCriterio
	 * @param usuario
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarOrdemCorteArquivoTXTModelo3(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException, ErroRepositorioException, IOException, CreateException{

		this.ejbCreate();

		this.getControladorCobranca().emitirOrdemCorteArquivoTXTModelo3(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						usuario, idFuncionalidadeIniciada);

	}
	
	/**
	 * Este caso de uso gera os avisos de corte
	 * [UC0349] [SB0008] – Gerar Arquivo TXT Aviso de Corte – Modelo 5
	 * 
	 * @author André Lopes
	 * @date 20/05/2013
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarOrdemCorteArquivoPdfModelo5(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException, CreateException{

		this.ejbCreate();

		getControladorCobrancaOrdemCorte().gerarOrdemCorteArquivoPDFModelo5(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						usuario, idFuncionalidadeIniciada);

	}

	/**
	 * Este caso de uso gera os avisos de corte
	 * [UC0349] [SB0008] – Gerar Arquivo PDF Aviso de Corte – Modelo 4
	 * 
	 * @author Gicevalter Couto
	 * @date 07/04/2015
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarOrdemCorteArquivoPdfModelo4(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException, ErroRepositorioException, IOException, CreateException{

		this.ejbCreate();

		getControladorCobrancaOrdemCorte().gerarOrdemCorteArquivoPDFModelo4(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						usuario, idFuncionalidadeIniciada);

	}


	/**
	 * Este caso de uso gera os avisos de corte
	 * Emitir Aviso de Corte
	 * 
	 * @return void
	 * @throws CreateException
	 */
	public void execParamGerarArquivoTxtAvisoCorteModelo1(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Integer faturamentoGrupoCronogramaMensalId,
					Usuario usuario) throws ControladorException, CreateException{

		this.ejbCreate();

		List<Object> colecaoEmitirAvisoCobrancaHelper = new ArrayList<Object>();
		Collection colecaoCobrancaDocumento = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;
		Integer idAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}
		if(acaoCobranca != null && acaoCobranca.getId() != null){
			idAcaoCobranca = acaoCobranca.getId();
		}

		colecaoCobrancaDocumento = getControladorCobranca().pesquisarTodosCobrancaDocumentoParaEmitir(idCronogramaAtividadeAcaoCobranca,
						idComandoAtividadeAcaoCobranca, dataAtualPesquisa, idAcaoCobranca);

		if(colecaoCobrancaDocumento != null && !colecaoCobrancaDocumento.isEmpty()){

			System.out.println("QUANTIDADE DOC COBRANCA PRA EMITIR = " + colecaoCobrancaDocumento.size());

			int metadeColecao = 0;
			int sequencialImpressao = 0;

			if(colecaoCobrancaDocumento.size() % 2 == 0){
				metadeColecao = colecaoCobrancaDocumento.size() / 2;
			}else{
				metadeColecao = (colecaoCobrancaDocumento.size() / 2) + 1;
			}

			Iterator iteratorCobrancaDocumento = colecaoCobrancaDocumento.iterator();

			try{

				while(iteratorCobrancaDocumento.hasNext()){

					CobrancaDocumento cobrancaDocumento = (CobrancaDocumento) iteratorCobrancaDocumento.next();

					EmitirAvisoCobrancaHelper emitirAvisoCobrancaHelper = new EmitirAvisoCobrancaHelper();

					// gera o numero sequencial da impressao
					int situacao = 0;
					sequencialImpressao++;

					while(situacao < 2){
						if(situacao == 0){
							situacao = 1;
							cobrancaDocumento.setSequencialImpressao(getControladorCobranca().atualizaSequencial(sequencialImpressao,
											situacao, metadeColecao));
						}else{
							situacao = 2;
							cobrancaDocumento.setSequencialImpressao(getControladorCobranca().atualizaSequencial(sequencialImpressao,
											situacao, metadeColecao));
						}
					}

					Collection colecaoCobrancaDocumentoItemConta = null;
					Collection colecaoCobrancaDocumentoItemGuiaPagamento = null;
					Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoDebitoACobrar = null;

					BigDecimal debitosAnteriores = BigDecimal.ZERO;
					BigDecimal valorTotal = BigDecimal.ZERO;

					if(cobrancaDocumento != null){

						// pesquisa todas as contas, debitos e guias
						colecaoCobrancaDocumentoItemConta = this.repositorioCobranca
										.selecionarCobrancaDocumentoItemReferenteConta(cobrancaDocumento);

						if(colecaoCobrancaDocumentoItemConta == null){

							colecaoCobrancaDocumentoItemGuiaPagamento = this.repositorioCobranca
											.selecionarDadosCobrancaDocumentoItemReferenteGuiaPagamento(cobrancaDocumento);
						}
						if(colecaoCobrancaDocumentoItemConta == null && colecaoCobrancaDocumentoItemGuiaPagamento == null){

							colecaoCobrancaDocumentoDebitoACobrar = this.repositorioCobranca
											.selecionarCobrancaDocumentoItemReferenteDebitoACobrar(cobrancaDocumento);
						}

						if(cobrancaDocumento.getCliente() != null && cobrancaDocumento.getCliente().getNome() != null){
							emitirAvisoCobrancaHelper.setNomeCliente(cobrancaDocumento.getCliente().getNome());
						}else{
							emitirAvisoCobrancaHelper.setNomeCliente("");
						}

						// carregando os dados no helper do relatorio de aviso
						// de
						// debito
						emitirAvisoCobrancaHelper.setMatricula(cobrancaDocumento.getImovel().getId().toString());
						emitirAvisoCobrancaHelper.setEndereco(cobrancaDocumento.getImovel().getEnderecoFormatadoAbreviadoSemBairro());
						emitirAvisoCobrancaHelper.setBairro(cobrancaDocumento.getImovel().getLogradouroBairro().getBairro().getNome());
						if(cobrancaDocumento.getImovel().getLigacaoAgua() != null
										&& cobrancaDocumento.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
							emitirAvisoCobrancaHelper.setHidrometro(cobrancaDocumento.getImovel().getLigacaoAgua()
											.getHidrometroInstalacaoHistorico().getNumeroHidrometro());
						}else{
							emitirAvisoCobrancaHelper.setHidrometro("");
						}

						emitirAvisoCobrancaHelper.setAcaoCobranca(acaoCobranca.getId().toString());

						String roteiro = cobrancaDocumento.getImovel().getSetorComercial().getId() + "-"
										+ cobrancaDocumento.getImovel().getRota().getId();

						if(cobrancaDocumento.getImovel().getNumeroSequencialRota() != null){
							roteiro = roteiro + "-" + cobrancaDocumento.getImovel().getNumeroSequencialRota();
						}

						emitirAvisoCobrancaHelper.setRoteiro(roteiro);

						emitirAvisoCobrancaHelper.setInscricao(cobrancaDocumento.getImovel().getInscricaoFormatada());

						Calendar dataAtual = Calendar.getInstance();
						Integer horas = dataAtual.get(Calendar.HOUR_OF_DAY);
						Integer minutos = dataAtual.get(Calendar.MINUTE);
						Integer segundos = dataAtual.get(Calendar.SECOND);
						emitirAvisoCobrancaHelper.setHoraImpressao("" + horas + ":" + minutos + ":" + segundos);
						emitirAvisoCobrancaHelper.setDataImpressao(Util.formatarData(dataAtual.getTime()));

						Util.adicionarNumeroDiasDeUmaData(dataAtual.getTime(), Integer.valueOf(acaoCobranca.getNumeroDiasValidade())
										.intValue());
						emitirAvisoCobrancaHelper.setDataComparecimento(Util.formatarData(dataAtual.getTime()));

						if(colecaoCobrancaDocumentoItemConta != null){
							// contas
							int limitador15Itens = 1;
							Collection<String> mesAno = new ArrayList<String>();
							Collection<String> vencimento = new ArrayList<String>();
							Collection<BigDecimal> valor = new ArrayList<BigDecimal>();

							Iterator<CobrancaDocumentoItem> itContas = colecaoCobrancaDocumentoItemConta.iterator();
							Integer mesAnoMaisAnterior = 300012;
							boolean teveConta = false;

							while(itContas.hasNext()){

								teveConta = true;
								CobrancaDocumentoItem cobrancaDocumentoItem = itContas.next();

								if(limitador15Itens <= 15){
									mesAno.add(Util.formatarAnoMesSemBarraParaMesAnoComBarra(cobrancaDocumentoItem.getContaGeral()
													.getConta().getReferencia()));
									vencimento.add(Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta()
													.getDataVencimentoConta()));
									valor.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
								}else{
									debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getContaGeral().getConta()
													.getValorTotal());
								}
								if(Util.compararAnoMesReferencia(cobrancaDocumentoItem.getContaGeral().getConta().getReferencia(),
												mesAnoMaisAnterior, "<")){
									mesAnoMaisAnterior = cobrancaDocumentoItem.getContaGeral().getConta().getReferencia();
								}
								valorTotal = valorTotal.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
								limitador15Itens++;
							}
							colecaoCobrancaDocumentoItemConta.clear();
							colecaoCobrancaDocumentoItemConta = null;

							if(teveConta){
								emitirAvisoCobrancaHelper.setDataDebitoAnterior(Util
												.formatarAnoMesSemBarraParaMesAnoComBarra(mesAnoMaisAnterior));
							}
							emitirAvisoCobrancaHelper.setMesAno(mesAno);
							emitirAvisoCobrancaHelper.setVencimento(vencimento);
							emitirAvisoCobrancaHelper.setValor(valor);

						}else if(colecaoCobrancaDocumentoItemGuiaPagamento != null){
							// guias
							Iterator<CobrancaDocumentoItem> itGuias = colecaoCobrancaDocumentoItemGuiaPagamento.iterator();
							while(itGuias.hasNext()){
								CobrancaDocumentoItem cobrancaDocumentoItem = itGuias.next();
								valorTotal = valorTotal.add(cobrancaDocumentoItem.getValorItemCobrado());
								debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getValorItemCobrado());
							}
							colecaoCobrancaDocumentoItemGuiaPagamento.clear();
							colecaoCobrancaDocumentoItemGuiaPagamento = null;

						}else if(colecaoCobrancaDocumentoDebitoACobrar != null){
							// debitos a cobrar
							Iterator<CobrancaDocumentoItem> itDebACob = colecaoCobrancaDocumentoDebitoACobrar.iterator();
							while(itDebACob.hasNext()){
								CobrancaDocumentoItem cobrancaDocumentoItem = itDebACob.next();
								valorTotal = valorTotal.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar()
												.getValorTotal());
								debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar()
												.getValorTotal());
							}
							colecaoCobrancaDocumentoDebitoACobrar.clear();
							colecaoCobrancaDocumentoDebitoACobrar = null;
						}
					}

					emitirAvisoCobrancaHelper.setValorDebitosAnteriores(debitosAnteriores);
					emitirAvisoCobrancaHelper.setValorTotal(valorTotal);

					// ini = System.currentTimeMillis();

					// obtendo o codigo de barras
					String representacaoNumericaCodBarra = getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(
									Integer.valueOf(PagamentoTipo.PAGAMENTO_TIPO_COBANCA_MATRICULA_IMOVEL), valorTotal,
									cobrancaDocumento.getImovel().getLocalidade().getId(), cobrancaDocumento.getImovel().getId(), null,
									null, null, null, cobrancaDocumento.getNumeroSequenciaDocumento() + "",
									cobrancaDocumento.getDocumentoTipo().getId(), null, null, null, null, null, null);

					// dif = System.currentTimeMillis() - ini;
					// System.out.println("5 - ############################## -> " + dif);
					// ini = System.currentTimeMillis();

					String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11) + "-"
									+ representacaoNumericaCodBarra.substring(11, 12) + " "
									+ representacaoNumericaCodBarra.substring(12, 23) + "-"
									+ representacaoNumericaCodBarra.substring(23, 24) + " "
									+ representacaoNumericaCodBarra.substring(24, 35) + "-"
									+ representacaoNumericaCodBarra.substring(35, 36) + " "
									+ representacaoNumericaCodBarra.substring(36, 47) + "-"
									+ representacaoNumericaCodBarra.substring(47, 48);
					emitirAvisoCobrancaHelper.setRepresentacaoNumericaCodBarraFormatada(representacaoNumericaCodBarraFormatada);

					String representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarra.substring(0, 11)
									+ representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35)
									+ representacaoNumericaCodBarra.substring(36, 47);

					emitirAvisoCobrancaHelper.setRepresentacaoNumericaCodBarraSemDigito(representacaoNumericaCodBarraSemDigito);

					colecaoEmitirAvisoCobrancaHelper.add(emitirAvisoCobrancaHelper);
				}

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			// Monta os relatorios em blocos de 1000
			if(colecaoEmitirAvisoCobrancaHelper != null && (colecaoEmitirAvisoCobrancaHelper.size() > 0)){

				System.out.println("QUANTIDADE TOTAL = " + colecaoEmitirAvisoCobrancaHelper.size());

				Collection<Collection<Object>> colecaoParcialEmitirAvisoCobrancaHelper = getControladorCobranca().dividirColecaoEmBlocos(
								colecaoEmitirAvisoCobrancaHelper, ConstantesSistema.QUANTIDADE_LIMITE_RELATORIOS_POR_ARQUIVO);

				int totalBlocosContasAEmitir = (Util.dividirArredondarResultado(colecaoEmitirAvisoCobrancaHelper.size(),
								ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO, BigDecimal.ROUND_CEILING));

				if(totalBlocosContasAEmitir == 0){
					totalBlocosContasAEmitir = 1;
				}
				colecaoEmitirAvisoCobrancaHelper.clear();
				colecaoEmitirAvisoCobrancaHelper = null;

				int contadorBlocoContasAEmitir = 1;

				System.out.println("QUANTIDADE BLOCOS = " + colecaoParcialEmitirAvisoCobrancaHelper.size());

				for(Collection bloco : colecaoParcialEmitirAvisoCobrancaHelper){

					Collection<EmitirAvisoCobrancaHelper> tmp = bloco;

					String mensagemArquivo = "PARTE: " + contadorBlocoContasAEmitir + "/" + totalBlocosContasAEmitir;
					RelatorioAvisoCorte relatorioAvisoCorte = new RelatorioAvisoCorte(usuario);
					relatorioAvisoCorte.addParametro("colecaoEmitirAvisoCobrancaHelper", tmp);
					relatorioAvisoCorte.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
					relatorioAvisoCorte.addParametro("descricaoArquivo", mensagemArquivo);

					this.getControladorBatch().iniciarProcessoRelatorio(relatorioAvisoCorte);
					contadorBlocoContasAEmitir++;
				}
				colecaoParcialEmitirAvisoCobrancaHelper.clear();
				colecaoParcialEmitirAvisoCobrancaHelper = null;
			}
		}
		if(colecaoCobrancaDocumento != null){
			colecaoCobrancaDocumento.clear();
			colecaoCobrancaDocumento = null;
		}
	}

	/**
	 * Este caso de uso gera os avisos de corte
	 * [UC0349] [SB0003] – Gerar Arquivo TXT Aviso de Corte – Modelo 2
	 * 
	 * @author Carlos Chrystian
	 * @date 20/12/2011
	 * @param parametroSistema
	 * @param colecaoCobrancaDocumento
	 * @param sistemaParametro
	 * @param idComandoCobranca
	 * @param usuario
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoTxtAvisoCorteModelo2(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Integer faturamentoGrupoCronogramaMensalId,
					Usuario usuario) throws ControladorException, CreateException{

		this.ejbCreate();

		// Os parâmetros dataAtualPesquisa, acaoCobranca, grupoCobranca e cobrancaCriterio são
		// descartados neste caso. Eles precisam ser mantidos na chamada deste método devido à
		// parametrização dinâmica, que exige a passagem dos mesmos parâmetros. Como o método que
		// gera o Modelo1 exige estes parâmetros, eles precisam ser mantidos.
		gerarArquivoTxtAvisoCorteModelo2(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando, usuario);
	}

	/**
	 * Este caso de uso gera os avisos de corte
	 * [UC0349] [SB0003] – Gerar Arquivo TXT Aviso de Corte – Modelo 2
	 * 
	 * @author Carlos Chrystian
	 * @date 20/12/2011
	 * @param parametroSistema
	 * @param colecaoCobrancaDocumento
	 * @param sistemaParametro
	 * @param idComandoCobranca
	 * @param usuario
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	private void gerarArquivoTxtAvisoCorteModelo2(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario) throws ControladorException{

		Collection<CobrancaDocumento> colecaoSetorComercialCobrancaDocumento = null;
		Collection<CobrancaDocumento> colecaoCobrancaDocumento = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;
		StringBuilder cobrancaDocumentoAvisoCorteTxt = new StringBuilder();
		Integer numeracaoArquivo = 0;
		int quantidadePaginas = 0;
		int quantidadeDocumentos = 0;
		int sequencialImpressao = 0;
		int indiceDocumento = 0;
		int tamString = 0;
		int offset = 0;
		String nomeArquivo = "";
		String auxFormatacao = "";
		String mensagemContaEmRevisao = "";

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}

		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}

		try{
			// [SB0003] – Gerar Arquivo TXT Aviso de Corte – Modelo 2
			// 1. Consultar todos os setores comerciais da lista de documentos de cobrança ordenados
			// pelo código do setor comercial.
			colecaoSetorComercialCobrancaDocumento = getControladorCobranca().pesquisarSetorComercialCobrancaDocumento(
							idCronogramaAtividadeAcaoCobranca, idComandoAtividadeAcaoCobranca);

			if(!Util.isVazioOrNulo(colecaoSetorComercialCobrancaDocumento)){
				// 2. Inicializar o campo numeração do arquivo com zero.
				numeracaoArquivo = 0;

				// 3. Para cada setor comercial:
				for(CobrancaDocumento cobrancaDocumentoSetorComercial : colecaoSetorComercialCobrancaDocumento){
					// Pesquisar o código da localidade
					SetorComercial setorComercial = null;
					FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, cobrancaDocumentoSetorComercial
									.getImovel().getSetorComercial().getId()));

					// Retorna Setor Comercial
					Collection colecaoConsultaSetorComercial = getControladorUtil().pesquisar(filtroSetorComercial,
									SetorComercial.class.getName());

					if(!Util.isVazioOrNulo(colecaoConsultaSetorComercial)){
						setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoConsultaSetorComercial);
					}

					// 3.1. Selecionar documentos de cobrança do setor comercial e ordenar por
					// quadra, lote e sublote.
					colecaoCobrancaDocumento = getControladorCobranca().pesquisarCobrancaDocumentoArquivoTXT(
									idCronogramaAtividadeAcaoCobranca, idComandoAtividadeAcaoCobranca,
									cobrancaDocumentoSetorComercial.getImovel().getSetorComercial().getId());

					// Seleciona o ID da Localidade
					String idLocalidade = "";

					if(setorComercial != null){
						idLocalidade = setorComercial.getLocalidade().getId().toString();
					}

					// Seleciona o ID da Localidade
					String codigoSetorComercial = "";

					if(cobrancaDocumentoSetorComercial.getCodigoSetorComercial() != null){
						codigoSetorComercial = cobrancaDocumentoSetorComercial.getCodigoSetorComercial().toString();
					}

					// 3.2. Caso existam documentos de cobrança para este setor
					if(!Util.isVazioOrNulo(colecaoCobrancaDocumento)){
						// 3.2.1. Criar o arquivo TXT
						nomeArquivo = "lasercorte." + Util.adicionarZerosEsquedaNumero(3, idLocalidade)
										+ Util.adicionarZerosEsquedaNumero(2, codigoSetorComercial) + "."
										+ Util.adicionarZerosEsquedaNumero(3, numeracaoArquivo.toString()) + "."
										+ Util.formatarDataComTracoAAAAMMDDHHMMSS(new Date());

						// 3.2.2. Inicializar a quantidade de páginas e o seqüencial de impressão
						// com 1 (um).
						quantidadePaginas = 1;
						sequencialImpressao = 1;

						// 3.2.3. Inicializar o índice do documento com zero.
						indiceDocumento = 0;

						// 3.2.4 Gerar a primeira linha de controle do arquivo TXT.
						cobrancaDocumentoAvisoCorteTxt.append("%!");

						cobrancaDocumentoAvisoCorteTxt.append(System.getProperty("line.separator"));

						// 3.2.5. Gerar a segunda linha de controle do arquivo TXT.
						cobrancaDocumentoAvisoCorteTxt.append("(crcavi03.jdt)  STARTLM");

						cobrancaDocumentoAvisoCorteTxt.append(System.getProperty("line.separator"));

						// 3.2.6. Atribuir à variável offset o valor da divisão da quantidade
						// de documentos de cobrança pela ‘Quantidade de avisos de corte por
						// página arredondado para cima.
						quantidadeDocumentos = colecaoCobrancaDocumento.size();

						Short quantidadeAvisosCortePorPagina = Util
										.converterStringParaShort((String) ParametroCobranca.P_QUANTIDADE_AVISO_CORTE_PAGINA.executar());

						offset = Util.arredondarResultadoDivisaoParaMaior(quantidadeDocumentos, quantidadeAvisosCortePorPagina.intValue());

						Collection<CobrancaDocumento> colecaoIntercaladaDaMetade = Util
										.intercalarObjetosDaColecaoMetadeMetade(colecaoCobrancaDocumento);

						// 3.2.7. Para cada documento de cobrança
						for(CobrancaDocumento cobrancaDocumento : colecaoIntercaladaDaMetade){
							// 3.2.7.1. Gerar conteúdo da linha do aviso de corte [SB0004 Gerar
							// Linha do Aviso de Corte Modelo 2].

							// 1. Gerar uma linha do arquivo TXT
							// 1 - Nome da Localidade
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(cobrancaDocumento.getImovel().getLocalidade()
											.getDescricao(), 15));

							// 2 - Matrícula formatada
							if(cobrancaDocumento.getImovel() != null){
								tamString = cobrancaDocumento.getImovel().getId().toString().length();
								// IMOV_ID, no formato 99999999.9
								auxFormatacao = cobrancaDocumento.getImovel().getId().toString().substring(0, tamString - 1) + "."
												+ cobrancaDocumento.getImovel().getId().toString().substring(tamString - 1, tamString);
								cobrancaDocumentoAvisoCorteTxt.append(Util.adicionarZerosEsquedaNumero(10, auxFormatacao));
							}else{
								cobrancaDocumentoAvisoCorteTxt.append(Util.adicionarZerosEsquedaNumero(10, "0"));
							}

							// 3 - Código da Localidade
							cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringZeroEsquerda(cobrancaDocumento.getImovel()
											.getLocalidade().getId().toString(), 3));

							// 4 - Setor Comercial
							cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringZeroEsquerda(cobrancaDocumento
											.getCodigoSetorComercial().toString(), 2));

							// 5 - Rota
							cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringZeroEsquerda(cobrancaDocumento.getImovel().getRota()
											.getCodigo().toString(), 2));

							// 6 - Segmento
							if(cobrancaDocumento.getImovel().getNumeroSegmento() != null){
								cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringZeroEsquerda(cobrancaDocumento.getImovel()
												.getNumeroSegmento().toString(), 2));
							}else{
								cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringZeroEsquerda("0", 2));
							}

							// 7 - Lote
							cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringZeroEsquerda(new Short(cobrancaDocumento.getImovel()
											.getLote()).toString(), 4));

							// 8 - SubLote
							cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringZeroEsquerda(new Short(cobrancaDocumento.getImovel()
											.getSubLote()).toString(), 2));

							// 9 - Nome do cliente
							if(cobrancaDocumento.getCliente() != null && cobrancaDocumento.getCliente().getNome() != null){
								cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(cobrancaDocumento.getCliente().getNome(), 30));
							}else{
								cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("", 30));
							}

							// 10 - Endereço do imóvel
							// [UC0085]Obter Endereco

							Object[] endereco = getControladorEndereco().pesquisarEnderecoFormatadoLista(
											cobrancaDocumento.getImovel().getId());
							String enderecoImovel = (String) endereco[0];
							// getControladorEndereco().pesquisarEndereco(cobrancaDocumento.getImovel().getId());

							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(enderecoImovel, 50));

							// 11 - Seqüencial de impressão
							cobrancaDocumentoAvisoCorteTxt.append(Util.adicionarZerosEsquedaNumero(6, new Integer(sequencialImpressao)
											.toString()));

							// 12 - Data da Emissão
							cobrancaDocumentoAvisoCorteTxt.append(Util
											.completaString(Util.formatarData(cobrancaDocumento.getEmissao()), 10));

							// 13 - Mensagem de corte com consumo
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("", 9));

							// 14 - Número do documento de cobrança
							cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringZeroEsquerda(new Integer(cobrancaDocumento
											.getNumeroSequenciaDocumento()).toString(), 9));

							// 15 - Número do hidrômetro
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(cobrancaDocumento.getImovel().getLigacaoAgua()
											.getHidrometroInstalacaoHistorico().getNumeroHidrometro(), 10));

							// 16 - Local de Instalação
							HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = repositorioCobranca
											.getHidrometroInstalacaoHistorico(cobrancaDocumento.getImovel().getLigacaoAgua()
															.getHidrometroInstalacaoHistorico().getNumeroHidrometro());

							if(hidrometroInstalacaoHistorico != null){
								cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(hidrometroInstalacaoHistorico
												.getHidrometroLocalInstalacao().getDescricaoAbreviada(), 3));
							}else{
								cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("", 3));
							}

							// 17 - Descrição do documento
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("OSP – ", 6));

							// 18 - Diâmetro
							Hidrometro hidrometro = repositorioCobranca.getHidrometro(cobrancaDocumento.getImovel().getLigacaoAgua()
											.getHidrometroInstalacaoHistorico().getNumeroHidrometro());

							if(hidrometro != null && hidrometro.getHidrometroDiametro() != null){
								cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(
												hidrometro.getHidrometroDiametro().getDescricao(), 6));
							}else{
								cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("*", 6));
							}

							// 19 - Valor Total do documento
							cobrancaDocumentoAvisoCorteTxt.append(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
											cobrancaDocumento.getValorDocumento(), 2, true), "*", 11));

							// Obtém a mensagem para contas em revisão pesquisando pelo Id de
							// cobrança
							// documento.
							mensagemContaEmRevisao = getControladorCobranca().obterMensagemContaEmRevisaoImovel(cobrancaDocumento.getId());

							// 20 - Mensagem de contas em revisão
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(mensagemContaEmRevisao, 73));

							// 21 - Data da Visita
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("", 12));

							// 22 - Representação numérica do código de barras
							// [FS002] – Obter representação numérica do código de barras com
							// formatação
							String representacaoNumericaCodBarra = "";

							// [UC0229] Obtém a representação numérica do código de barra
							representacaoNumericaCodBarra = getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(
											new Integer(5), cobrancaDocumento.getValorDocumento(),
											cobrancaDocumento.getImovel().getLocalidade().getId(), cobrancaDocumento.getImovel().getId(),
											null, null, null, null, cobrancaDocumento.getNumeroSequenciaDocumento() + "",
											cobrancaDocumento.getDocumentoTipo().getId(), null, null, null, null, null, null);

							// Formata a representação númerica do código de barras com o dígito
							// verificador
							String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 12) + " "
											+ representacaoNumericaCodBarra.substring(12, 24) + " "
											+ representacaoNumericaCodBarra.substring(24, 36) + " "
											+ representacaoNumericaCodBarra.substring(36, 48);

							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(representacaoNumericaCodBarraFormatada, 51));

							// 23 - Quantidade de contas
							// Quantidade de contas de itens associados ao documento de cobrança
							int quantidadeContas = (repositorioCobranca.pesquisarQuantidadeCobrancaDocumentoItem(cobrancaDocumento.getId()))
											.intValue();
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("("
											+ Util.adicionarZerosEsquedaNumero(2, Integer.valueOf(quantidadeContas).toString())
											+ " CONTAS)", 11));

							// 24 / 37 - Item notificado 1 ao Item notificado 14
							// [FS003] – Formatar itens cobrados
							// 1. Selecionar itens cobrados associados ao documento de cobrança
							// ordenando pelo mês/ano referência
							Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoItem = repositorioCobranca
											.obterCobrancaDocumentoItemComConta(cobrancaDocumento);

							int quantidadeContasCobrancaDocumentoItem = 0;

							if(!Util.isVazioOrNulo(colecaoCobrancaDocumentoItem)){
								// Guarda a quantidade de itens de cobrança documento
								quantidadeContasCobrancaDocumentoItem = colecaoCobrancaDocumentoItem.size();
								// 2. Caso a quantidade de itens seja maior que 14 (catorze),
								if(quantidadeContasCobrancaDocumentoItem > 14){
									String msgRetornoAgrupados = "";
									String msgRetornoRecentes = "";
									String mesAnoReferencia = "";
									BigDecimal valorTotalItensAgrupados = BigDecimal.ZERO;
									int contadorRegistros = 0;
									int indicadorInicioAgrupar = 0;
									int contadorRegistrosRecentes = 0;
									// agrupar os N (quantidade total – 13) mais antigos (ordenados
									// pelo mês/ano de referência)
									indicadorInicioAgrupar = quantidadeContasCobrancaDocumentoItem - 13;

									for(CobrancaDocumentoItem cobrancaDocumentoItem : colecaoCobrancaDocumentoItem){
										contadorRegistros++;

										// Recupera o maior mês/ano de referência do itens agrupados
										if(contadorRegistros == indicadorInicioAgrupar){
											mesAnoReferencia = Util.formatarMesAnoReferencia(cobrancaDocumentoItem.getContaGeral()
															.getConta().getReferencia());
										}
										// e obter o valor total dos itens agrupados (soma de
										// CDIT_VLITEMCOBRADO)
										// Acumula o valor total de itens agrupados
										if(contadorRegistros <= indicadorInicioAgrupar){
											valorTotalItensAgrupados = valorTotalItensAgrupados.add(cobrancaDocumentoItem
															.getValorItemCobrado());
										}
									}

									// Mensagem da 1ª Linha
									msgRetornoAgrupados = " "
													+ "ANTES DE "
													+ Util.completaString("", 3)
													+ Util.completaString(mesAnoReferencia, 7)
													+ Util.completarStringComValorEsquerda(Util.formataBigDecimal(valorTotalItensAgrupados,
																	2, true), " ", 11);

									cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(msgRetornoAgrupados, 31));

									// Atribuir os segmentos de Item cobrado de 2 a 14
									for(CobrancaDocumentoItem cobrancaDocumentoItem : colecaoCobrancaDocumentoItem){
										contadorRegistrosRecentes++;

										if(contadorRegistrosRecentes > indicadorInicioAgrupar){
											msgRetornoRecentes = " "
															+ Util.completaString(Util.formatarMesAnoReferencia(cobrancaDocumentoItem
																			.getContaGeral().getConta().getReferencia()), 7)
															+ Util.completaString("", 2)
															+ Util.completaString(Util.formatarData(cobrancaDocumentoItem.getContaGeral()
																			.getConta().getDataVencimentoConta()), 10)
															+ Util.completarStringComValorEsquerda(Util.formataBigDecimal(
																			cobrancaDocumentoItem.getValorItemCobrado(), 2, true), " ", 11);

											cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(msgRetornoRecentes, 31));
										}

										msgRetornoRecentes = "";
									}
								}else{
									// 3. Caso contrário, ou seja, a quantidade de itens não seja
									// maior que 14, atribuir os segmentos de item cobrado de 1 a
									// 14, para os que existirem
									int contadorRegistrosInseridos = 0;
									String msgRetorno = "";

									for(CobrancaDocumentoItem cobrancaDocumentoItem : colecaoCobrancaDocumentoItem){
										contadorRegistrosInseridos++;

										msgRetorno = " "
														+ Util.completaString(Util.formatarMesAnoReferencia(cobrancaDocumentoItem
																		.getContaGeral().getConta().getReferencia()), 7)
														+ Util.completaString("", 2)
														+ Util.completaString(Util.formatarData(cobrancaDocumentoItem.getContaGeral()
																		.getConta().getDataVencimentoConta()), 10)
														+ Util.completarStringComValorEsquerda(Util.formataBigDecimal(cobrancaDocumentoItem
																		.getValorItemCobrado(), 2, true), " ", 11);

										cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(msgRetorno, 31));

										msgRetorno = "";
									}

									// Caso não exista o item ou a quantidade seja menor que 14,
									// preencher com espaços em branco:
									while(contadorRegistrosInseridos < 14){
										cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("", 31));
										contadorRegistrosInseridos++;
									}
								}
							}

							// 38 - Item notificado para Corte
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("", 294));

							// 39 - Nome da empresa abreviado
							Empresa empresa = null;
							FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

							filtroEmpresa
											.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, cobrancaDocumento.getEmpresa()
															.getId()));

							// Retorna Empresa
							Collection colecaoConsultaEmpresa = getControladorUtil().pesquisar(filtroEmpresa, Empresa.class.getName());

							if(!Util.isVazioOrNulo(colecaoConsultaEmpresa)){
								empresa = (Empresa) Util.retonarObjetoDeColecao(colecaoConsultaEmpresa);
							}

							if(empresa != null){
								cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(empresa.getDescricaoAbreviada(), 4));
							}else{
								cobrancaDocumentoAvisoCorteTxt.append(Util.completaString("", 4));
							}

							// 40 - Código de Barras
							// Recupera a representação númerica do código de barras sem
							// os dígitos verificadores
							String representacaoCodigoBarrasSemDigitoVerificador = representacaoNumericaCodBarra.substring(0, 11)
											+ representacaoNumericaCodBarra.substring(12, 23)
											+ representacaoNumericaCodBarra.substring(24, 35)
											+ representacaoNumericaCodBarra.substring(36, 47);

							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(representacaoCodigoBarrasSemDigitoVerificador, 44));

							// 41 - Matricula sem formatação
							cobrancaDocumentoAvisoCorteTxt.append(Util.adicionarZerosEsquedaNumero(9, cobrancaDocumento.getImovel().getId()
											.toString()));

							// 42 - Data de emissão sem barras
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(Util.formatarDataSemBarraDDMMAAAA(cobrancaDocumento
											.getEmissao()), 8));

							// 43 - Gerencia Regional
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(cobrancaDocumento.getImovel().getLocalidade()
											.getGerenciaRegional().getNomeAbreviado(), 5));

							// 44 - Bairro
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(cobrancaDocumento.getImovel().getLogradouroBairro()
											.getBairro().getNome(), 25));

							// 45 - CEP
							cobrancaDocumentoAvisoCorteTxt.append(Util.completaString(Util.formatarCEP(cobrancaDocumento.getImovel()
											.getLogradouroCep().getCep().getCodigo().toString()), 10));

							cobrancaDocumentoAvisoCorteTxt.append(System.getProperty("line.separator"));

							// 3.2.7.2. Incrementar o índice do documento.
							indiceDocumento++;

							// 3.2.7.3. Adicionar ao seqüencial de impressão o valor do offset.
							sequencialImpressao += offset;

							// 3.2.7.4. Caso o resto da divisão do indice do documento
							// pelo valor do parâmetro ‘Quantidade de avisos de corte por página’
							// seja zero ou seqüencial de impressão seja maior que a quantidade de
							// documentos
							int restodivisao = 0;
							restodivisao = indiceDocumento % quantidadeAvisosCortePorPagina;

							if((restodivisao == 0) || (sequencialImpressao > quantidadeDocumentos)){
								// 3.2.7.4.1. Gerar quebra de página
								// [FS005 – Gerar Linha de Quebra de página].
								if(indiceDocumento < quantidadeDocumentos){
									cobrancaDocumentoAvisoCorteTxt.append("%%XGF PAGEBRK");

									cobrancaDocumentoAvisoCorteTxt.append(System.getProperty("line.separator"));
								}

								// 3.2.7.4.2. Incrementar a quantidade de páginas.
								quantidadePaginas++;

								// 3.2.7.4.3. Atribuir ao seqüencial de impressão o valor da
								// quantidade de páginas.
								sequencialImpressao = quantidadePaginas;
							}
						}
					}

					// 3.2.8. Gerar linha de fim de arquivo
					// [FS006 – Gerar Linha de Finalização do Arquivo]
					// e fechar o arquivo.
					cobrancaDocumentoAvisoCorteTxt.append("%%EOF");

					// 3.2.9. Incrementar o valor de numeração do arquivo.
					numeracaoArquivo++;

					// Gera o Arquivo.
					this.getControladorCobranca().enviarArquivoTxtCobrancaDocumentoAvisoCorte(cobrancaDocumentoAvisoCorteTxt, nomeArquivo,
									usuario);

					cobrancaDocumentoAvisoCorteTxt = new StringBuilder();
				}
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoCobrancaDocumento != null){
			colecaoCobrancaDocumento.clear();
			colecaoCobrancaDocumento = null;
		}

		if(colecaoSetorComercialCobrancaDocumento != null){
			colecaoSetorComercialCobrancaDocumento.clear();
			colecaoSetorComercialCobrancaDocumento = null;
		}
	}

	/**
	 * Este caso de uso gera os avisos de corte
	 * [UC0349] [SB0003] – Gerar Arquivo TXT Aviso de Corte – Modelo 3
	 * 
	 * @author Luciano Galvão
	 * @date 04/06/2012
	 * @param parametroSistema
	 * @param colecaoCobrancaDocumento
	 * @param sistemaParametro
	 * @param idComandoCobranca
	 * @param usuario
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoTxtAvisoCorteModelo3(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Integer faturamentoGrupoCronogramaMensalId,
					Usuario usuario) throws ControladorException, CreateException{

		this.ejbCreate();

		Collection<CobrancaDocumento> colecaoCobrancaDocumento = new ArrayList<CobrancaDocumento>();
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;
		Integer idAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}
		if(acaoCobranca != null && acaoCobranca.getId() != null){
			idAcaoCobranca = acaoCobranca.getId();
		}

		FiltroCobrancaDocumento filtro = null;

		// Caso este processamento seja oriundo do processo de Faturamento
		if(faturamentoGrupoCronogramaMensalId != null && !faturamentoGrupoCronogramaMensalId.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			filtro = new FiltroCobrancaDocumento();
			filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ID,
							faturamentoGrupoCronogramaMensalId));

			// Caso este processamento seja oriundo do processo de Comando de Cobrança
		}else if(idCronogramaAtividadeAcaoCobranca != null || idComandoAtividadeAcaoCobranca != null){

			filtro = new FiltroCobrancaDocumento();

			if(idCronogramaAtividadeAcaoCobranca != null){
				filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ATIVIDADE_CRONOGRAMA_ID,
								idCronogramaAtividadeAcaoCobranca));
			}

			if(idComandoAtividadeAcaoCobranca != null){
				filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ATIVIDADE_COMANDO_ID, idComandoAtividadeAcaoCobranca));
			}

			if(idAcaoCobranca != null){
				filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_COBRANCA_ACAO, idAcaoCobranca));
			}
		}

		if(filtro != null){
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.LOCALIDADE);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.COBRANCA_ACAO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.ATIVIDADE_COMANDO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.EMPRESA);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.CLIENTE);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL + "." + FiltroImovel.IMOVEL_CONTA_ENVIO);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL + "." + FiltroImovel.LOCALIDADE);

			colecaoCobrancaDocumento = getControladorUtil().pesquisar(filtro, CobrancaDocumento.class.getName());
			System.out.println("QTD DE DOCUMENTOS COBRANCA A SEREM EMITIDOS: " + colecaoCobrancaDocumento.size());
			gerarArquivoTxtAvisoCorteModelo3(parametroSistema, colecaoCobrancaDocumento, usuario);
		}else{
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Batch de Emissão dos Documentos de Cobrança");
			System.out.println("ATENÇÃO: Não foi gerado nenhum conteúdo de arquivo");
			System.out.println("pois as informações de entrada não foram passadas.");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		}

	}

	/**
	 * Método que implementa o modelo 3 de geração do arquivo txt com os avisos de corte a partir de
	 * uma coleção de ids de documentos de cobrança
	 * 
	 * @author Luciano Galvao
	 * @created 11/06/2012
	 * @param parametroSistema
	 * @param cobrancaDocumentoIds
	 * @param usuario
	 * @throws ControladorException
	 */
	public void gerarArquivoTxtAvisoCorteModelo3(ParametroSistema parametroSistema, Collection<CobrancaDocumento> colecaoCobrancaDocumento,
					Usuario usuario) throws ControladorException{

		if(!Util.isVazioOrNulo(colecaoCobrancaDocumento)){

			// Separa e ordena a lista de documentos de cobrança em 4 listas de acordo com a
			// característica de entrega
			CobrancaDocumentoColecoesOrdenadasHelper cobrancaDocumentoColecoesOrdenadas = getControladorCobranca()
							.tratarColecaoDocumentosCobranca(colecaoCobrancaDocumento);

			Collection<CobrancaDocumentoAvisoCorteHelper> documentosAvisoCorteHelper = new ArrayList<CobrancaDocumentoAvisoCorteHelper>();

			// Agrupa as listas ordenadas seguindo a ordem estabelecida no UC0349 - SB0005
			documentosAvisoCorteHelper.addAll(cobrancaDocumentoColecoesOrdenadas.getDocumentosCobrancaEntregaImovel());
			documentosAvisoCorteHelper.addAll(cobrancaDocumentoColecoesOrdenadas.getDocumentosCobrancaEntregaClienteResponsavel());
			documentosAvisoCorteHelper.addAll(cobrancaDocumentoColecoesOrdenadas.getDocumentosCobrancaEntregaClienteUsuario());
			documentosAvisoCorteHelper.addAll(cobrancaDocumentoColecoesOrdenadas.getDocumentosCobrancaEntregaClienteProprietario());

			// StringBuilder utilizado para acumular todo o conteúdo do arquivo
			StringBuilder conteudo = new StringBuilder();

			// Item 4 - Data de Emissão dos Avisos
			Date dataEmissao = ((CobrancaDocumentoAvisoCorteHelper) documentosAvisoCorteHelper.toArray()[0]).getCobrancaDocumento()
							.getEmissao();
			gerarPrimeiraLinhaAvisoCorteModelo3(conteudo, dataEmissao);

			// Gera o arquivo TXT com as informações dos documentos gerados, seguindo UC0349
			gerarLinhasDocumentosAvisoCorteModelo3(conteudo, documentosAvisoCorteHelper);

			gerarUltimaLinhaAvisoCorteModelo3(conteudo);

			String nomeArquivo = "AVISO_CORTE" + Util.formatarDataComTracoAAAAMMDDHHMMSS(new Date()) + ".txt";

			// Gera o Arquivo.
			this.getControladorCobranca().enviarArquivoTxtCobrancaDocumentoAvisoCorte(conteudo, nomeArquivo, usuario);
		}
	}

	/**
	 * Gera a última linha do arquivo de Aviso de Corte - Modelo 3
	 * 
	 * @param conteudo
	 */
	private void gerarUltimaLinhaAvisoCorteModelo3(StringBuilder conteudo){

		// Item 5 - Captura as posições de 63 a 1172 do último documento de cobrança gerado
		String ultimaLinhaDocumentoCobranca = conteudo.substring(conteudo.length() - 1104, conteudo.length() - 2);

		conteudo.append("99999999");
		conteudo.append(Util.completaString("FINAL ARQUIVO CIAFEADE", 62));
		conteudo.append(Util.completaString(ultimaLinhaDocumentoCobranca, 1102));
	}

	/**
	 * A partir de uma lista de documentos (CobrancaDocumento) já ordenada, gera as linhas do
	 * arquivo TXT de Aviso de Corte (Modelo 3)
	 * 
	 * @author Luciano Galvao
	 * @created 05/06/2012
	 * @param documentos
	 */
	public void gerarLinhasDocumentosAvisoCorteModelo3(StringBuilder conteudo,
					Collection<CobrancaDocumentoAvisoCorteHelper> documentosAvisoCorteHelper) throws ControladorException{

		if(!Util.isVazioOrNulo(documentosAvisoCorteHelper)){

			CobrancaDocumentoAvisoCorteHelper documentoAvisoCorteHelper = null;

			// obtem o array de documentos
			Object[] documentosArray = documentosAvisoCorteHelper.toArray();

			int qtdDocumentos = documentosAvisoCorteHelper.size();

			// calcula o limite de impressão, considerando que haverá duas colunas empilhadas ao
			// final
			int limiteImpressao = (qtdDocumentos / 2) + (qtdDocumentos % 2);

			// posição inicial das colunas 1 e 2
			int posColuna1 = 0;
			int posColuna2 = limiteImpressao;

			while(posColuna1 < limiteImpressao){

				// verifica se a posição é válida
				if(posColuna1 < qtdDocumentos){
					// gera documento da primeira coluna
					documentoAvisoCorteHelper = (CobrancaDocumentoAvisoCorteHelper) documentosArray[posColuna1];
					gerarLinhaDocumentoAvisoCorteModelo3(conteudo, documentoAvisoCorteHelper);
				}

				// verifica se a posição é válida
				if(posColuna2 < qtdDocumentos){
					// gera documento da primeira coluna
					documentoAvisoCorteHelper = (CobrancaDocumentoAvisoCorteHelper) documentosArray[posColuna2];
					gerarLinhaDocumentoAvisoCorteModelo3(conteudo, documentoAvisoCorteHelper);
				}

				// incrementa a posição das colunas
				posColuna1++;
				posColuna2++;
			}

		}

	}

	/**
	 * Gera a primeira linha do arquivo de avisos de corte - Modelo 3
	 * 
	 * @param conteudo
	 * @param dataEmissao
	 */
	private void gerarPrimeiraLinhaAvisoCorteModelo3(StringBuilder conteudo, Date dataEmissao){

		String dataEmissaoStr = "00/00/0000";
		if(dataEmissao != null){
			dataEmissaoStr = Util.formatarData(dataEmissao);
		}

		// 6.2.1. Gerar a primeira linha de controle do arquivo TXT
		conteudo.append(Util.completarStringZeroDireita("0", 8));
		conteudo.append("INICIO ARQUIVO CIAFEADE");
		conteudo.append(Util.completaString("", 900));
		conteudo.append(dataEmissaoStr);
		conteudo.append(Util.completaString("", 231));
		conteudo.append(SEPARADOR_LINHA);
	}

	/**
	 * Gerar linha de arquivo para um documento de aviso de corte de acordo com o Modelo 3
	 * [UC0349] - Emitir Documento de Cobrança
	 * [SB0005] - Gerar Arquivo TXT Aviso de Corte - Modelo 3 (Item 6.2.2)
	 * 
	 * @author Luciano Galvão
	 * @created 05/06/2012
	 * @param documento
	 * @param conteudo
	 */
	private void gerarLinhaDocumentoAvisoCorteModelo3(StringBuilder conteudo, CobrancaDocumentoAvisoCorteHelper documentoAvisoCorteHelper)
					throws ControladorException{

		CobrancaDocumento cobrancaDocumento = documentoAvisoCorteHelper.getCobrancaDocumento();
		Imovel imovel = cobrancaDocumento.getImovel();

		Cliente clienteResponsavel = null;
		Cliente clienteUsuario = null;
		Cliente clienteProprietario = null;
		Collection<Categoria> colecaoCategoriasImovel = null;

		// =====================================================
		// Variáveis de conteúdo
		// =====================================================

		String matriculaImovel = "";
		String nomeImovelCliente = "";
		String matriculaImovelFormatada = "";
		String[] enderecoImovel = null;
		String nomeLocalidade = "";
		String codigoSetor = "";
		String numeroQuadra = "";
		String numeroLote = "";
		String codigoClienteResp = "";
		String nomeClienteResp = "";
		String cpfCnpjClienteProprietario = "";
		String qtdEconomias = "";
		String categoriaImovel = "";
		String valorItemFormatado = "";
		int qtdItensExcedentes = 0;
		String qtdItensExcedentesFormatado = "";
		String valorItensExcedentesFormatado = "";
		String valorDocumentoFormatado = "";
		String dataEmissaoFormatada = "00/00/0000";
		String dataApresentacaoFormatada = "00/00/0000";
		String dataLimiteFormatada = "00/00/0000";
		String[] valoresCodigoBarras = null;
		String codigoBarrasCodificado = "";
		String idLocalidadeImovel = "";

		if(imovel != null){
			// =====================================================
			// Consulta o cliente RESPONSAVEL
			// =====================================================
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
							ClienteRelacaoTipo.RESPONSAVEL));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId()));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);

			Collection<ClienteImovel> colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel,
							ClienteImovel.class.getName());
			ClienteImovel clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

			if(clienteImovel != null){
				clienteResponsavel = clienteImovel.getCliente();
			}

			// =====================================================
			// Consulta o cliente USUARIO
			// =====================================================
			filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId()));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
							ClienteRelacaoTipo.USUARIO));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);

			colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
			clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

			if(clienteImovel != null){
				clienteUsuario = clienteImovel.getCliente();
			}

			// =====================================================
			// Consulta o cliente PROPRIETARIO
			// =====================================================
			filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId()));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
							ClienteRelacaoTipo.PROPRIETARIO));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE + "." + FiltroCliente.CLIENTE_TIPO);

			colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
			clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

			if(clienteImovel != null){
				clienteProprietario = clienteImovel.getCliente();
			}

			// =====================================================
			// Consulta a quantidade de economias por categoria
			// =====================================================
			colecaoCategoriasImovel = getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);
		}

		// =====================================================
		// Captura das informações
		// =====================================================

		// Item 1 - Matrícula do imóvel
		if(imovel != null){
			matriculaImovel = imovel.getId().toString();
		}

		// Item 2 - Nome do imóvel ou nome do cliente usuário
		if((imovel != null) && (imovel.getNomeImovel() != null)){
			nomeImovelCliente = imovel.getNomeImovel();

		}else{

			if((clienteUsuario != null) && clienteUsuario.getNome() != null){
				nomeImovelCliente = clienteUsuario.getNome();
			}else{
				nomeImovelCliente = "";
			}
		}

		// Item 3 - Mátricula do imóvel formatada
		if(!Util.isVazioOuBranco(matriculaImovel)){
			matriculaImovelFormatada = matriculaImovel.substring(0, matriculaImovel.length() - 1) + "."
							+ matriculaImovel.substring(matriculaImovel.length() - 1);
		}

		// Item 4 - Endereço do imóvel - parte 1
		// Item 6 - Endereço do imóvel - parte 2
		enderecoImovel = getControladorEndereco().pesquisarEndereco(imovel.getId(), 48);

		// Item 8 - Nome da localidade
		if((cobrancaDocumento.getLocalidade() != null) && (cobrancaDocumento.getLocalidade().getDescricao() != null)){
			nomeLocalidade = cobrancaDocumento.getLocalidade().getDescricao();
		}

		// Item 9 - Setor
		if(cobrancaDocumento.getCodigoSetorComercial() != null){
			codigoSetor = cobrancaDocumento.getCodigoSetorComercial().toString();
		}

		// Item 10 - Quadra
		if(cobrancaDocumento.getNumeroQuadra() != null){
			numeroQuadra = cobrancaDocumento.getNumeroQuadra().toString();
		}

		// Item 11 - Lote
		if(imovel != null){
			numeroLote = String.valueOf(imovel.getLote());
		}
		// Item 12 - Código do cliente responsável
		if(clienteResponsavel != null){
			codigoClienteResp = clienteResponsavel.getId().toString();
		}

		// Item 13 - Nome do cliente responsável
		if((clienteResponsavel != null) && (clienteResponsavel.getNome() != null)){
			nomeClienteResp = clienteResponsavel.getNome();
		}

		// Item 14 - CPF/CNPJ do cliente proprietário
		if((clienteProprietario != null) && (clienteProprietario.getClienteTipo() != null)){
			int length = 0;
			if(clienteProprietario.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
				if(!Util.isVazioOuBranco(clienteProprietario.getCpf())){
					cpfCnpjClienteProprietario = clienteProprietario.getCpf();

					length = cpfCnpjClienteProprietario.length();

					// Formata o CPF
					if(length == 11){
						cpfCnpjClienteProprietario = cpfCnpjClienteProprietario.substring(0, length - 2) + "-"
										+ cpfCnpjClienteProprietario.substring(length - 2);
					}
				}
			}else if(clienteProprietario.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){
				if(!Util.isVazioOuBranco(clienteProprietario.getCnpj())){
					cpfCnpjClienteProprietario = clienteProprietario.getCnpj();

					length = cpfCnpjClienteProprietario.length();

					// Formata o CNPJ
					if(length == 15){
						cpfCnpjClienteProprietario = cpfCnpjClienteProprietario.substring(0, length - 6) + "/"
										+ cpfCnpjClienteProprietario.substring(length - 6, length - 2) + "-"
										+ cpfCnpjClienteProprietario.substring(length - 2);
					}
				}
			}
		}

		// Item 15 - Número de economias do imóvel
		if(imovel != null){
			qtdEconomias = String.valueOf(imovel.getQuantidadeEconomias());
		}

		if(!Util.isVazioOrNulo(colecaoCategoriasImovel)){
			if(colecaoCategoriasImovel.size() > 1){
				categoriaImovel = "MIS";
			}else{
				Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoCategoriasImovel);
				categoriaImovel = categoria.getDescricaoAbreviada();
			}
		}

		// ==========================================================
		// Construção do conteúdo a partir das informações capturadas
		// ==========================================================

		conteudo.append(Util.completarStringZeroEsquerda(matriculaImovel, 8)); // Item 1
		conteudo.append(Util.completaString(nomeImovelCliente, 62)); // Item 2
		conteudo.append(Util.completarStringZeroEsquerda(matriculaImovelFormatada, 9)); // Item 3
		conteudo.append(Util.completaString(enderecoImovel[0], 48)); // Item 4
		conteudo.append(Util.completaString("", 15)); // Item 5
		conteudo.append(Util.completaString(enderecoImovel[1], 48)); // Item 6
		conteudo.append(Util.completaString("", 13)); // Item 7
		conteudo.append(Util.completaString(nomeLocalidade, 56)); // Item 8
		conteudo.append(Util.completarStringZeroEsquerda(codigoSetor, 2)); // Item 9
		conteudo.append(Util.completarStringZeroEsquerda(numeroQuadra, 4)); // Item 10
		conteudo.append(Util.completarStringZeroEsquerda(numeroLote, 4)); // Item 11
		conteudo.append(Util.completaStringComEspacoAEsquerda(codigoClienteResp, 6)); // Item 12
		conteudo.append(Util.completaString(nomeClienteResp, 22)); // Item 13
		conteudo.append(Util.completaString(cpfCnpjClienteProprietario, 20)); // Item 14
		conteudo.append(Util.completarStringZeroEsquerda(qtdEconomias, 3)); // Item 15
		conteudo.append(Util.completaString(categoriaImovel, 3)); // Item 16
		conteudo.append(Util.completaString(enderecoImovel[0], 48)); // Item 17
		conteudo.append(Util.completaString("", 1)); // Item 18
		conteudo.append(Util.completaString(enderecoImovel[1], 47)); // Item 19
		conteudo.append(Util.completaString(nomeLocalidade, 20)); // Item 20

		// Consulta os itens de documento de cobrança
		Collection<CobrancaDocumentoItem> cobrancaDocumentoItens = getControladorCobranca().pesquisarCobrancaDocumentoItens(
						cobrancaDocumento.getId());

		StringBuilder conteudoContas = new StringBuilder();
		BigDecimal valorItensExcedentes = BigDecimal.ZERO;
		BigDecimal valorItem = BigDecimal.ZERO;
		int i = 0;

		Iterator cobrancaDocumentoItensIt = cobrancaDocumentoItens.iterator();

		while(cobrancaDocumentoItensIt.hasNext()){
			CobrancaDocumentoItem item = (CobrancaDocumentoItem) cobrancaDocumentoItensIt.next();
			valorItem = item.getValorItemCobrado().add(item.getValorAcrescimos());

			// Item - Valor do Item de Conta
			valorItemFormatado = Util.formatarMoedaReal(valorItem);

			if(i < 22){
				if((item.getContaGeral() != null) && (item.getContaGeral().getConta() != null)){

					// Preenchimento das informações de cada conta
					conteudoContas.append(Util.completaString(item.getContaGeral().getConta().getReferenciaFormatada(), 7));
					conteudoContas.append(Util.completaStringComEspacoAEsquerda(valorItemFormatado, 14));
				}

				i++;
			}else{

				qtdItensExcedentes++;
				valorItensExcedentes = valorItensExcedentes.add(valorItem);
			}
		}

		// Complementa as posições reservadas às informações de Contas, totalizano 462 caracteres
		conteudo.append(Util.completaString(conteudoContas.toString(), 462));

		// ================================================================
		// Captura das informações
		// Preenchimento das informações finais, após as 22 contas iniciais
		// ================================================================

		// Item 65 - Quantidade de itens de conta que excedeu o limite de 22 contas
		if(qtdItensExcedentes > 0){
			qtdItensExcedentesFormatado = String.valueOf(qtdItensExcedentes);
		}

		// Item 66 - Valor dos itens de conta que excedeu
		if(valorItensExcedentes.compareTo(BigDecimal.ZERO) == 1){
			valorItensExcedentesFormatado = Util.formatarMoedaReal(valorItensExcedentes);
		}

		// Item 67 - Valor do documento de cobrança
		BigDecimal valorDocumentoCalculado = BigDecimal.ZERO;

		if(cobrancaDocumento.getValorDocumento() != null){
			valorDocumentoCalculado = valorDocumentoCalculado.add(cobrancaDocumento.getValorDocumento());
		}
		if(cobrancaDocumento.getValorAcrescimos() != null){
			valorDocumentoCalculado = valorDocumentoCalculado.add(cobrancaDocumento.getValorAcrescimos());
		}

		valorDocumentoFormatado = Util.formatarMoedaReal(valorDocumentoCalculado);

		// Item 68 - Data de emissão
		Date dataEmissao = cobrancaDocumento.getEmissao();
		if(dataEmissao != null){
			dataEmissaoFormatada = Util.formatarData(dataEmissao);
		}

		// Item 69 - Data de apresentação
		Date dataApresentacao = cobrancaDocumento.getEmissao();
		short qtdDiasRealizacao = 0;

		if((cobrancaDocumento.getCobrancaAcaoAtividadeComando() != null)
						&& (cobrancaDocumento.getCobrancaAcaoAtividadeComando().getQuantidadeDiasRealizacao() != null)){

			qtdDiasRealizacao = cobrancaDocumento.getCobrancaAcaoAtividadeComando().getQuantidadeDiasRealizacao();

		}else if((cobrancaDocumento.getCobrancaAcao() != null) && (cobrancaDocumento.getCobrancaAcao().getQtdDiasRealizacao() != null)){

			qtdDiasRealizacao = cobrancaDocumento.getCobrancaAcao().getQtdDiasRealizacao();
		}

		dataApresentacao = Util.adicionarNumeroDiasDeUmaData(dataApresentacao, qtdDiasRealizacao);

		dataApresentacaoFormatada = Util.formatarData(dataApresentacao);

		// Item 72 - Data limite
		int qtdDiasVencimentoConta = Integer.parseInt((String) ParametroCobranca.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA_AVISO_CORTE
						.executar(this));

		Date dataLimite = Util.subtrairNumeroDiasDeUmaData(dataEmissao, qtdDiasVencimentoConta);

		dataLimiteFormatada = Util.formatarData(dataLimite);

		// SB0006 - Obter Dados da Representação Numérica do Documento de Cobrança
		valoresCodigoBarras = obterDadosRepresentacaoNumerica(documentoAvisoCorteHelper);

		// Item 84 - Código de barras (Inclui o UC0340)
		codigoBarrasCodificado = Util.converterCodigoBarrasParaFormato2de5Intercalado(valoresCodigoBarras[1]);

		// Item 85 - Localidade do imóvel
		if((imovel != null) && (imovel.getLocalidade() != null)){
			idLocalidadeImovel = imovel.getLocalidade().getId().toString();
		}

		// ==========================================================
		// Construção do conteúdo a partir das informações capturadas
		// ==========================================================
		conteudo.append(Util.completaStringComEspacoAEsquerda(qtdItensExcedentesFormatado, 2));
		conteudo.append(Util.completaStringComEspacoAEsquerda(valorItensExcedentesFormatado, 14));
		conteudo.append(Util.completaStringComEspacoAEsquerda(valorDocumentoFormatado, 14));
		conteudo.append(dataEmissaoFormatada);
		conteudo.append(dataApresentacaoFormatada);
		conteudo.append(Util.completaStringComEspacoAEsquerda(String.valueOf(cobrancaDocumento.getNumeroSequenciaDocumento()), 9));
		conteudo.append(dataLimiteFormatada);
		conteudo.append(valoresCodigoBarras[0]);
		conteudo.append(codigoBarrasCodificado);
		conteudo.append(Util.completarStringZeroEsquerda(idLocalidadeImovel, 3));
		conteudo.append(Util.completarStringZeroEsquerda(codigoSetor, 2));
		conteudo.append(Util.completarStringZeroEsquerda(numeroQuadra, 4));
		conteudo.append(Util.completarStringZeroEsquerda(numeroLote, 4));
		conteudo.append(Util.completaStringComEspacoAEsquerda(valorDocumentoFormatado, 14));
		conteudo.append(Util.completarStringZeroEsquerda(idLocalidadeImovel, 3));

		// Concatena o separador de linha ao final dos dados
		conteudo.append(SEPARADOR_LINHA);

	}

	/**
	 * Obter representação numérica do código de barras com o seguinte formato:
	 * 817700000000 010936599702 411310797039 0011433708318
	 * Além disto, retorna também o código de barras concatenado e sem o dígito verificador:
	 * 817700000000109365997041131079703001143370831
	 * 
	 * @author Luciano Galvao
	 * @created 11/06/2012
	 * @param documentoAvisoCorteHelper
	 * @return
	 * @throws ControladorException
	 */
	private String[] obterDadosRepresentacaoNumerica(CobrancaDocumentoAvisoCorteHelper documentoAvisoCorteHelper)
					throws ControladorException{

		String[] valoresCodigoBarras = new String[2];
		String codigoBarras = null;
		String codigoBarrasFormatado = null;
		CobrancaDocumento documento = documentoAvisoCorteHelper.getCobrancaDocumento();
		ImovelContaEnvio imovelContaEnvio = null;

		if((documentoAvisoCorteHelper.getCobrancaDocumento() != null)
						&& (documentoAvisoCorteHelper.getCobrancaDocumento().getImovel() != null)
						&& (documentoAvisoCorteHelper.getCobrancaDocumento().getImovel().getImovelContaEnvio() != null)){

			imovelContaEnvio = documentoAvisoCorteHelper.getCobrancaDocumento().getImovel().getImovelContaEnvio();

			if((imovelContaEnvio.getId().equals(ImovelContaEnvio.NAO_PAGAVEL_IMOVEL_PAGAVEL_RESPONSAVEL) && (documentoAvisoCorteHelper
							.getIdTipoLista() == CobrancaDocumentoAvisoCorteHelper.ENTREGA_IMOVEL))
							|| (imovelContaEnvio.getId().equals(ImovelContaEnvio.PAGAVEL_PARA_IMOVEL_E_NAO_PAGAVEL_PARA_RESPOSAVEL) && (documentoAvisoCorteHelper
											.getIdTipoLista() == CobrancaDocumentoAvisoCorteHelper.ENTREGA_CLIENTE_RESPONSAVEL))){

				codigoBarras = "";
			}
		}

		if(codigoBarras == null){
			Integer tipoPagamento = new Integer(5);

			BigDecimal valorCodigoBarra = BigDecimal.ZERO;

			if(documento != null){
				if(documento.getValorDocumento() != null){
					valorCodigoBarra = valorCodigoBarra.add(documento.getValorDocumento());
				}
				if(documento.getValorAcrescimos() != null){
					valorCodigoBarra = valorCodigoBarra.add(documento.getValorAcrescimos());
				}
			}

			Integer idLocalidade = null;

			if(documento.getLocalidade() != null){
				idLocalidade = documento.getLocalidade().getId();
			}

			Integer matriculaImovel = documento.getImovel().getId();
			String mesAnoReferenciaConta = null;
			Integer digitoVerificadorRefContaModulo10 = null;
			Integer idTipoDebito = null;
			String anoEmissaoGuiaPagamento = null;
			String sequencialDocumentoCobranca = String.valueOf(documento.getNumeroSequenciaDocumento());

			Integer idTipoDocumento = null;

			if(documento.getDocumentoTipo() != null){
				idTipoDocumento = documento.getDocumentoTipo().getId();
			}

			Integer idCliente = null;
			Integer seqFaturaClienteResponsavel = null;
			Short numeroPrestacaoDocumento = null;
			Integer idOpcaoPreParcelamento = null;

			// Usa [UC0229] Obter representação numérica do código de barras
			codigoBarras = getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(tipoPagamento, valorCodigoBarra, idLocalidade,
							matriculaImovel, mesAnoReferenciaConta, digitoVerificadorRefContaModulo10, idTipoDebito,
							anoEmissaoGuiaPagamento, sequencialDocumentoCobranca, idTipoDocumento, idCliente, seqFaturaClienteResponsavel,
							numeroPrestacaoDocumento, idOpcaoPreParcelamento, null, null);
		}

		if(!Util.isVazioOuBranco(codigoBarras) && codigoBarras.length() >= 48){

			codigoBarrasFormatado = codigoBarras.substring(0, 12) + SEPARACAO_CODIGO_BARRAS + codigoBarras.substring(12, 24)
							+ SEPARACAO_CODIGO_BARRAS + codigoBarras.substring(24, 36) + SEPARACAO_CODIGO_BARRAS
							+ codigoBarras.substring(36, 48);

			codigoBarras = codigoBarras.substring(0, 11) + codigoBarras.substring(12, 23) + codigoBarras.substring(24, 35)
							+ codigoBarras.substring(36, 47);
		}

		valoresCodigoBarras[0] = codigoBarrasFormatado;
		valoresCodigoBarras[1] = codigoBarras;

		return valoresCodigoBarras;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Calcular valor da prestação do parcelamento - Regra 1
	 * 
	 * @author Hebert Falcão
	 * @created 04/01/2013
	 */
	public BigDecimal execParamCalcularValorPrestacaoParcelamentoRegra1(ParametroSistema parametroSistema, BigDecimal valorAParcelar,
					BigDecimal valorEntrada, BigDecimal taxaJuros, Short quantidadePrestacao, MathContext decimal)
					throws ControladorException{

		BigDecimal valorUm = BigDecimal.ONE;
		BigDecimal fatorPrestacao = BigDecimal.ONE;
		BigDecimal valorPrestacao = BigDecimal.ONE;

		fatorPrestacao = valorUm.add(taxaJuros, decimal);
		fatorPrestacao = fatorPrestacao.pow(quantidadePrestacao);
		fatorPrestacao = valorUm.divide(fatorPrestacao, decimal);

		fatorPrestacao = valorUm.subtract(fatorPrestacao, decimal);

		fatorPrestacao = fatorPrestacao.divide(taxaJuros, decimal);

		if(valorEntrada != null && valorEntrada.compareTo(BigDecimal.ZERO) > 0){
			fatorPrestacao = fatorPrestacao.multiply(valorUm.add(taxaJuros));
		}

		valorPrestacao = valorAParcelar.divide(fatorPrestacao, decimal);

		return valorPrestacao;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Calcular valor da prestação do parcelamento - Regra 2
	 * 
	 * @author Hebert Falcão
	 * @created 04/01/2013
	 */
	public BigDecimal execParamCalcularValorPrestacaoParcelamentoRegra2(ParametroSistema parametroSistema, BigDecimal valorAParcelar,
					BigDecimal valorEntrada, BigDecimal taxaJuros, Short quantidadePrestacao, MathContext decimal)
					throws ControladorException{

		BigDecimal valorUm = BigDecimal.ONE;
		BigDecimal fatorPrestacao = BigDecimal.ONE;
		BigDecimal valorPrestacao = BigDecimal.ONE;

		fatorPrestacao = valorUm.add(taxaJuros, decimal);
		fatorPrestacao = fatorPrestacao.pow(quantidadePrestacao);
		fatorPrestacao = valorUm.divide(fatorPrestacao, decimal);

		fatorPrestacao = valorUm.subtract(fatorPrestacao, decimal);

		valorPrestacao = valorAParcelar.multiply(taxaJuros);
		valorPrestacao = valorPrestacao.divide(fatorPrestacao, decimal);

		return valorPrestacao;
	}

	/**
	 * Este caso de uso gera os avisos de corte
	 * [SB0009] – Gerar Arquivo PDF Aviso de Corte – Modelo 5 (CAERD)
	 * 
	 * @param parametroSistema
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param dataAtualPesquisa
	 * @param acaoCobranca
	 * @param grupoCobranca
	 * @param cobrancaCriterio
	 * @param faturamentoGrupoCronogramaMensalId
	 * @param usuario
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoPdfAvisoCorteModelo5(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Integer faturamentoGrupoCronogramaMensalId,
					Usuario usuario) throws ControladorException, CreateException{

		this.ejbCreate();

		// Os parâmetros dataAtualPesquisa, acaoCobranca, grupoCobranca e cobrancaCriterio são
		// descartados neste caso. Eles precisam ser mantidos na chamada deste método devido à
		// parametrização dinâmica, que exige a passagem dos mesmos parâmetros. Como o método que
		// gera o Modelo1 exige estes parâmetros, eles precisam ser mantidos.
		getControladorCobrancaOrdemCorte().gerarArquivoPdfAvisoCorteModelo5(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						usuario);
		
	}

	/**
	 * Este caso de uso gera os avisos de corte
	 * [SB0008] – Gerar Arquivo PDF Aviso de Corte – Modelo 4 (CAGEPA)
	 * 
	 * @param parametroSistema
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param dataAtualPesquisa
	 * @param acaoCobranca
	 * @param grupoCobranca
	 * @param cobrancaCriterio
	 * @param faturamentoGrupoCronogramaMensalId
	 * @param usuario
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoPdfAvisoCorteModelo4(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Integer faturamentoGrupoCronogramaMensalId,
					Usuario usuario) throws ControladorException, CreateException{

		this.ejbCreate();

		// Os parâmetros dataAtualPesquisa, acaoCobranca, grupoCobranca e cobrancaCriterio são
		// descartados neste caso. Eles precisam ser mantidos na chamada deste método devido à
		// parametrização dinâmica, que exige a passagem dos mesmos parâmetros. Como o método que
		// gera o Modelo1 exige estes parâmetros, eles precisam ser mantidos.
		getControladorCobrancaOrdemCorte().gerarArquivoPdfAvisoCorteModelo4(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						usuario);

	}

	/**
	 * Gera os avisos de débito Modelo 1 (Padrão)
	 */
	public void execParamGerarRelatorioAvisoDebitoModelo1(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario) throws ControladorException,
					CreateException{

		this.ejbCreate();

		// Os parâmetros dataAtualPesquisa, acaoCobranca, grupoCobranca e cobrancaCriterio são
		// descartados neste caso. Eles precisam ser mantidos na chamada deste método devido à
		// parametrização dinâmica, que exige a passagem dos mesmos parâmetros. Como o método que
		// gera o Modelo1 exige estes parâmetros, eles precisam ser mantidos.
		getControladorCobrancaAvisoDebito().gerarRelatorioAvisoDebitoModelo1(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						dataAtualPesquisa, acaoCobranca, grupoCobranca, cobrancaCriterio, usuario);

	}

	/**
	 * Gera os avisos de débito Modelo 2 (CAERD)
	 */
	public void execParamGerarRelatorioAvisoDebitoModelo2(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario) throws ControladorException,
					CreateException{

		this.ejbCreate();

		// Os parâmetros dataAtualPesquisa, acaoCobranca, grupoCobranca e cobrancaCriterio são
		// descartados neste caso. Eles precisam ser mantidos na chamada deste método devido à
		// parametrização dinâmica, que exige a passagem dos mesmos parâmetros. Como o método que
		// gera o Modelo1 exige estes parâmetros, eles precisam ser mantidos.
		getControladorCobrancaAvisoDebito().gerarRelatorioAvisoDebitoModelo2(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						usuario);

	}
	
	
	/**
	 * Gera os avisos de débito Modelo 3 (SAAE)
	 */
	public void execParamGerarRelatorioAvisoDebitoModelo3(ParametroSistema parametroSistema,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario) throws ControladorException,
					CreateException{

		this.ejbCreate();


		getControladorCobrancaAvisoDebito().gerarRelatorioAvisoDebitoModelo3(cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando,
						dataAtualPesquisa, acaoCobranca, grupoCobranca, cobrancaCriterio, usuario);

	}
	

}
