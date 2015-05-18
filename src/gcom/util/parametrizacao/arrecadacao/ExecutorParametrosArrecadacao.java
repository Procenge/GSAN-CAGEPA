
package gcom.util.parametrizacao.arrecadacao;

import gcom.arrecadacao.AvisoInteligest;
import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.FiltroAvisoInteligest;
import gcom.arrecadacao.bean.RegistroHelperCodigoA;
import gcom.arrecadacao.bean.RegistroHelperCodigoBarras;
import gcom.arrecadacao.bean.RegistroHelperCodigoBarrasTipoPagamento;
import gcom.arrecadacao.bean.RegistroHelperCodigoF;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimento;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoRetornoCodigo;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.RepositorioImovelHBM;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.IRepositorioFaturamento;
import gcom.faturamento.RepositorioFaturamentoHBM;
import gcom.faturamento.conta.*;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;

import br.com.procenge.parametrosistema.api.ParametroSistema;

public class ExecutorParametrosArrecadacao
				implements ExecutorParametro {

	private static final ExecutorParametrosArrecadacao instancia = new ExecutorParametrosArrecadacao();

	private IRepositorioImovel repositorioImovel = null;

	private IRepositorioFaturamento repositorioFaturamento = null;

	private ExecutorParametrosArrecadacao() {

		repositorioImovel = RepositorioImovelHBM.getInstancia();
		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
	}

	public static ExecutorParametrosArrecadacao getInstancia(){

		return instancia;
	}

	public String[] execParamValidarCamposRegistroCodigoFFormato1(ParametroSistema parametroSistema,
					RegistroHelperCodigoF registroHelperCodigoF, SistemaParametro sistemaParametro, String linhaRegistro)
					throws ControladorException{

		boolean dataExcludentes = false;
		String descricaoOcorrenciaMovimento = "OK";
		String matriculaImovel = null;
		String[] retorno = new String[2];

		// Recupera o par�metro n�mero de d�gitos para matricula do im�vel na tabela de
		// par�metros do sistema
		int numeroDigitosMatriculaImovel = Integer.valueOf(ParametroArrecadacao.P_NUMERO_DIGITOS_MATRICULA_IMOVEL.executar()).intValue();

		descricaoOcorrenciaMovimento = validarDataDebitoPagamento(registroHelperCodigoF, dataExcludentes, descricaoOcorrenciaMovimento);

		descricaoOcorrenciaMovimento = validarValorDebitoRecebido(registroHelperCodigoF, descricaoOcorrenciaMovimento);
		// matricula do im�vel com os P_NUMERO_DIGITOS_MATRICULA_IMOVEL primeiros digitos da
		// identifica��o do cliente na empresa
		matriculaImovel = registroHelperCodigoF.getIdClienteEmpresa().substring(0, numeroDigitosMatriculaImovel);

		matriculaImovel = Imovel.obterMatriculaDebitoAutomatico(matriculaImovel);

		retorno[1] = matriculaImovel;

		descricaoOcorrenciaMovimento = validarMatriculaImovel(descricaoOcorrenciaMovimento, matriculaImovel);

		// valida o namo mes de referencia da conta
		descricaoOcorrenciaMovimento = validarAnoMesReferencia(registroHelperCodigoF, descricaoOcorrenciaMovimento);

		Short codigoEmpresaFebraban = sistemaParametro.getCodigoEmpresaFebraban();
		String adaGrupoFaturamentoConstanteLegado = linhaRegistro.substring(92, 94).trim();
		String adaTipoDocumento = linhaRegistro.substring(66, 69);
		String adaAnoMesReferenciaContaLegado = linhaRegistro.substring(69, 74).trim();
		boolean registroLegadoAda = getControladorArrecadacao().validarRegistroLegadoADA(codigoEmpresaFebraban,
						adaGrupoFaturamentoConstanteLegado, adaAnoMesReferenciaContaLegado, adaTipoDocumento, "F");

		if(registroLegadoAda){

			/*
			 * Caso esteja processando movimento da Empresa=ADA (PARM_CDEMPRESAFEBRABAN=0477 na
			 * tabela SISTEMA_PARAMETROS) e posi��o 93 a 94(campo
			 * F.10) for �04� e posi��o 70 a 74(campo F.08) for �00000�, verifica a conta (a partir
			 * da tabela CONTA com IMOV_ID=[posi��o 1 a 8 da
			 * Identifica��o do Cliente na Empresa (campo F02)] ap�s inser��o do d�gito verificador
			 * e CNTA_ID = AINT_NNSEQUENCIAL da tabela
			 * AVISO_INTELIGEST para AINT_NNAVISO = [posi��o 79 a 87(campo F10)],
			 * AINT_NNANO=[posi��o 75 a 78(campo F08/09/10)] e
			 * AINT_CDTRIBUTO=[posi��o 90 a 92(campo F10)] e situa��o atual (DCST_IDATUAL) com o
			 * valor correspondente a normal, retificada ou
			 * inclu�da);
			 */

			String stringAnoDocumentoADA = linhaRegistro.substring(74, 78).trim();
			Integer anoDocumentoADA = null;
			if(!Util.validarValorNaoNumerico(stringAnoDocumentoADA)){
				anoDocumentoADA = Integer.valueOf(stringAnoDocumentoADA);
			}

			String stringDocumentoPagavelADA = linhaRegistro.substring(78, 87).trim();
			Integer numeroDocumentoPagavelADA = null;
			if(!Util.validarValorNaoNumerico(stringDocumentoPagavelADA)){
				numeroDocumentoPagavelADA = Integer.valueOf(stringDocumentoPagavelADA);
			}

			String stringCodigoTributoADA = linhaRegistro.substring(89, 92).trim();
			Integer codigoTributoADA = null;
			if(!Util.validarValorNaoNumerico(stringCodigoTributoADA)){
				codigoTributoADA = Integer.valueOf(stringCodigoTributoADA);
			}

			if(anoDocumentoADA != null && numeroDocumentoPagavelADA != null && codigoTributoADA != null){
				FiltroAvisoInteligest filtroAvisoInteligest = new FiltroAvisoInteligest();
				filtroAvisoInteligest
								.adicionarParametro(new ParametroSimples(FiltroAvisoInteligest.NUMERO_AVISO, numeroDocumentoPagavelADA));
				filtroAvisoInteligest.adicionarParametro(new ParametroSimples(FiltroAvisoInteligest.ANO, anoDocumentoADA));
				filtroAvisoInteligest.adicionarParametro(new ParametroSimples(FiltroAvisoInteligest.CODIGO_TRIBUTO, codigoTributoADA));

				Collection<AvisoInteligest> colecaoAvisoInteligest = getControladorUtil().pesquisar(filtroAvisoInteligest,
								AvisoInteligest.class.getName());

				AvisoInteligest avisoInteligest = null;
				if(colecaoAvisoInteligest != null && !colecaoAvisoInteligest.isEmpty()){
					avisoInteligest = colecaoAvisoInteligest.iterator().next();
				}

				if(avisoInteligest != null){
					Integer idContaInteligest = avisoInteligest.getNumeroSequencial();

					FiltroConta filtroConta = new FiltroConta();
					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idContaInteligest));
					Collection<Conta> colecaoConta = getControladorUtil().pesquisar(filtroConta, Conta.class.getName());

					Conta conta = null;
					if(colecaoConta != null && !colecaoConta.isEmpty()){
						conta = (Conta) Util.retonarObjetoDeColecao(colecaoConta);
						registroHelperCodigoF.setAnoMesReferenciaConta("" + conta.getReferencia());
					}else{
						FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
						filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID, idContaInteligest));
						Collection<ContaHistorico> colecaoContaHistorico = getControladorUtil().pesquisar(filtroContaHistorico,
										ContaHistorico.class.getName());

						ContaHistorico contaHistorico = null;
						if(colecaoContaHistorico != null && !colecaoContaHistorico.isEmpty()){
							contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);
							registroHelperCodigoF.setAnoMesReferenciaConta("" + contaHistorico.getAnoMesReferenciaConta());
						}else{
							descricaoOcorrenciaMovimento = "IDENTIFICA��O DA CONTA(LEGADO) N�O LOCALIZADA";
						}
					}
				}else{
					descricaoOcorrenciaMovimento = "IDENTIFICA��O DA CONTA(LEGADO) N�O LOCALIZADA";
				}
			}
		}
		retorno[0] = descricaoOcorrenciaMovimento;
		return retorno;
	}

	/**
	 * Author: Vivianne Sousa Data: 1804/03/2006
	 * Retorna o valor do Controlador Util
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

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

	/**
	 * Retorna o valor de controladorArrecadacao
	 * 
	 * @return O valor de controladorCliente
	 */
	private ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
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

	public Object[] execParamAtualizarDebitoAutomaticoConta(ParametroSistema parametroSistema, String descricaoOcorrenciaMovimento,
					Integer idImovelNaBase, RegistroHelperCodigoF registroHelperCodigoF, RegistroHelperCodigoA registroHelperCodigoA)
					throws ControladorException{

		DebitoAutomaticoMovimento debitoAutomaticoMovimento = null;
		Imovel imovel = null;
		Integer idConta = null;
		Integer identificacaoConta = null;
		Integer identificacaoGuiaPagamento = null;
		Integer numeroPrestacao = null;
		Boolean indicadorPagamentoConta = true;
		Boolean indicadorPagamentoGuia = false;

		if(idImovelNaBase != null){
			try{
				imovel = new Imovel();
				imovel.setId(idImovelNaBase);
				idConta = repositorioFaturamento.pesquisarExistenciaContaComSituacaoAtual(imovel, Integer.valueOf(registroHelperCodigoF
								.getAnoMesReferenciaConta()));
				if(idConta != null){
					identificacaoConta = idConta;
					debitoAutomaticoMovimento = repositorioFaturamento.obterDebitoAutomaticoMovimento(idImovelNaBase, Integer
									.valueOf(registroHelperCodigoF.getAnoMesReferenciaConta()));
				}else{
					Collection idsContaHistorico = repositorioFaturamento.pesquisarExistenciaContaHistoricoPorRefEImovel(imovel,
									Integer
									.valueOf(registroHelperCodigoF.getAnoMesReferenciaConta()));
					if(idsContaHistorico == null || idsContaHistorico.isEmpty()){
						descricaoOcorrenciaMovimento = "CONTA INEXISTENTE";
					}else{
						Integer idContaHistorico = (Integer) Util.retonarObjetoDeColecao(idsContaHistorico);
						identificacaoConta = idContaHistorico;
						debitoAutomaticoMovimento = repositorioFaturamento.obterDebitoAutomaticoMovimento(idImovelNaBase, Integer
										.valueOf(registroHelperCodigoF.getAnoMesReferenciaConta()));
					}
				}
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}
		}
		// se debitoAutomaticaMovimento for nula seta o valor para o
		// campo descri��o movimento caso contrario atualiza o
		// debitoAutomaticaMovimento
		if(debitoAutomaticoMovimento != null){

			DebitoAutomaticoRetornoCodigo debitoAutomaticoRetornoCodigo = new DebitoAutomaticoRetornoCodigo();
			debitoAutomaticoRetornoCodigo.setId(Integer.valueOf(registroHelperCodigoF.getCodigoRetorno()));
			debitoAutomaticoMovimento.setDebitoAutomaticoRetornoCodigo(debitoAutomaticoRetornoCodigo);
			debitoAutomaticoMovimento.setRetornoBanco(new Date());
			debitoAutomaticoMovimento
							.setNumeroSequenciaArquivoRecebido(Integer.valueOf(registroHelperCodigoA.getNumeroSequencialArquivo()));
			debitoAutomaticoMovimento.setUltimaAlteracao(new Date());
			getControladorUtil().atualizar(debitoAutomaticoMovimento);
		}

		return new Object[] {identificacaoConta, identificacaoGuiaPagamento, numeroPrestacao, descricaoOcorrenciaMovimento, indicadorPagamentoConta, indicadorPagamentoGuia};
	}

	public String[] execParamValidarCamposRegistroCodigoFFormato2(ParametroSistema parametroSistema,
					RegistroHelperCodigoF registroHelperCodigoF, SistemaParametro sistemaParametro, String linhaRegistro)
					throws ControladorException{

		boolean dataExcludentes = false;
		String[] retorno = new String[2];
		String descricaoOcorrenciaMovimento = "OK";
		String matriculaImovel = null;

		// Recupera o par�metro n�mero de d�gitos para matricula do im�vel na tabela de
		// par�metros do sistema
		int numeroDigitosMatriculaImovel = Integer.valueOf(ParametroArrecadacao.P_NUMERO_DIGITOS_MATRICULA_IMOVEL.executar()).intValue();

		descricaoOcorrenciaMovimento = validarDataDebitoPagamento(registroHelperCodigoF, dataExcludentes, descricaoOcorrenciaMovimento);

		descricaoOcorrenciaMovimento = validarValorDebitoRecebido(registroHelperCodigoF, descricaoOcorrenciaMovimento);
		// matricula do im�vel com os P_NUMERO_DIGITOS_MATRICULA_IMOVEL primeiros digitos da
		// identifica��o do cliente na empresa
		String registroAux = linhaRegistro.substring(84, 96);

		if(Util.isNumero(registroAux, false, 0) && !Util.removerZerosEsquerda(registroAux).equals("0")){
			matriculaImovel = linhaRegistro.substring(96, 106);
		}else{
			matriculaImovel = registroHelperCodigoF.getIdClienteEmpresa().substring(0, numeroDigitosMatriculaImovel);
		}

		retorno[1] = matriculaImovel;
		// verifica se existe a matricula do im�vel na base
		descricaoOcorrenciaMovimento = validarMatriculaImovel(descricaoOcorrenciaMovimento, matriculaImovel);

		/*
		 * Caso Guia de Pagamento (campo F.10.3) seja num�rico e diferente de zero. Passa para o
		 * pr�ximo passo e n�o valida Ano/m�s de refer�ncia da conta (campo F.08)
		 */
		boolean indicadorGuiaPagamento = false;
		if(Util.isNumero(registroHelperCodigoF.getIndicadorPagamentoGuia(), false, 0)
						&& !Util.removerZerosEsquerda(registroHelperCodigoF.getIndicadorPagamentoGuia()).equals("0")){

			indicadorGuiaPagamento = true;
		}

		if(indicadorGuiaPagamento == false){

			descricaoOcorrenciaMovimento = validarAnoMesReferencia(registroHelperCodigoF, descricaoOcorrenciaMovimento);
		}

		retorno[0] = descricaoOcorrenciaMovimento;
		return retorno;
	}

	public StringBuilder execParamFormatarCampoUsoEmpresaFormato1(ParametroSistema parametroSistema, StringBuilder registroTipoE,
					DebitoAutomaticoMovimento debitoAutomaticoMovimento, Integer numeroSequencialArquivo) throws ControladorException{

		ContaGeral contaGeral = debitoAutomaticoMovimento.getContaGeral();

		Short indicadorHistorico = contaGeral.getIndicadorHistorico();
		Integer referencia = null;
		Short digitoVerificador = null;

		if(ConstantesSistema.NAO.equals(indicadorHistorico)){
			Conta conta = contaGeral.getConta();

			if(conta != null){
				referencia = conta.getReferencia();
				digitoVerificador = conta.getDigitoVerificadorConta();
			}
		}else{
			ContaHistorico contaHistorico = contaGeral.getContaHistorico();

			if(contaHistorico != null){
				referencia = contaHistorico.getAnoMesReferenciaConta();
				digitoVerificador = contaHistorico.getVerificadorConta();
			}
		}

		// inicio preenchido conforme segue abaixo(E.07)
		// Ano/M�s de refer�ncia da conta no formato AAAAMM
		registroTipoE.append(referencia);
		// Digito verificador da conta
		registroTipoE.append(digitoVerificador);
		// Id da conta
		registroTipoE.append(Util.completaString("" + contaGeral.getId(), 9));
		// id do grupo de faturamento
		registroTipoE.append(Util.completaString("" + debitoAutomaticoMovimento.getFaturamentoGrupo().getId(), 2));
		// reservado para o futuro
		registroTipoE.append(Util.completaString("", 42));
		// fim preenchido conforme segue abaixo(E.07)

		return registroTipoE;
	}

	public StringBuilder execParamFormatarCampoUsoEmpresaFormato2(ParametroSistema parametroSistema, StringBuilder registroTipoE,
					DebitoAutomaticoMovimento debitoAutomaticoMovimento, Integer numeroSequencialArquivo) throws ControladorException{

		ContaGeral contaGeral = debitoAutomaticoMovimento.getContaGeral();

		// inicio preenchido conforme segue abaixo(E.07)
		// Ano/M�s de refer�ncia da conta no formato AAAAMM
		if(contaGeral != null){
			Short indicadorHistorico = contaGeral.getIndicadorHistorico();
			Integer referencia = null;

			if(ConstantesSistema.NAO.equals(indicadorHistorico)){
				Conta conta = contaGeral.getConta();

				if(conta != null){
					referencia = conta.getReferencia();
				}
			}else{
				ContaHistorico contaHistorico = contaGeral.getContaHistorico();

				if(contaHistorico != null){
					referencia = contaHistorico.getAnoMesReferenciaConta();
				}
			}

			registroTipoE.append(referencia);
		}else{
			registroTipoE.append("000000");
		}
		// id do grupo de faturamento
		registroTipoE.append(Util.adicionarZerosEsquedaNumero(3, "" + debitoAutomaticoMovimento.getFaturamentoGrupo().getId()));
		// N�mero seq�encial do arquivo enviado (ARMV_NNNSA do ARRECADADOR_MOVIMENTO)
		registroTipoE.append(Util.adicionarZerosEsquedaNumero(6, numeroSequencialArquivo.toString()));

		if(contaGeral != null){
			registroTipoE.append("000000000000");
			registroTipoE.append("0000000000");
		}else{
			registroTipoE.append(Util.adicionarZerosEsquedaNumero(9, debitoAutomaticoMovimento.getGuiaPagamento().getId().toString()));
			registroTipoE.append(Util.adicionarZerosEsquedaNumero(3, debitoAutomaticoMovimento.getNumeroPrestacao().toString()));
			registroTipoE.append(Util.adicionarZerosEsquedaNumero(10, debitoAutomaticoMovimento.getGuiaPagamento().getImovel().getId()
							.toString()));
		}

		// reservado para o futuro
		registroTipoE.append(Util.completaString("", 22));

		registroTipoE.append("2");
		// fim preenchido conforme segue abaixo(E.07)

		return registroTipoE;
	}

	/**
	 * UC242 - Registrar Movimento Arrecadadores - [FS0008] � Validar data de d�bito/pagamento
	 * 
	 * @param registroHelperCodigoF
	 * @param dataExcludentes
	 * @param descricaoOcorrenciaMovimento
	 * @return
	 */
	private String validarDataDebitoPagamento(RegistroHelperCodigoF registroHelperCodigoF, boolean dataExcludentes,
					String descricaoOcorrenciaMovimento){

		Date dataDebito;
		// valida a data
		boolean dataInvalida = Util.validarAnoMesDiaSemBarra(registroHelperCodigoF.getDataDebito());
		if(dataInvalida){
			dataExcludentes = true;
			descricaoOcorrenciaMovimento = "DATA DE D�BITO/PAGAMENTO INV�LIDA";
		}
		// caso a data seja inv�lida n�o verifica se � maior que a data atual
		if(!dataExcludentes){
			// verifica se a data de bedito/pagamento � superior a atual
			dataDebito = Util.converteStringInvertidaSemBarraParaDate(registroHelperCodigoF.getDataDebito());
			if(dataDebito.after(new Date())){
				descricaoOcorrenciaMovimento = "DATA DE D�BITO/PAGAMENTO POSTERIOR A DATA CORRENTE";
			}
		}
		return descricaoOcorrenciaMovimento;
	}

	/**
	 * UC242 - Registrar Movimento Arrecadadores - [FS0009] � Validar ano/m�s de refer�ncia
	 * 
	 * @param registroHelperCodigoF
	 * @param descricaoOcorrenciaMovimento
	 * @return
	 */
	private String validarAnoMesReferencia(RegistroHelperCodigoF registroHelperCodigoF, String descricaoOcorrenciaMovimento){

		boolean anoMesReferencia = Util.validarAnoMesSemBarra(registroHelperCodigoF.getAnoMesReferenciaConta());
		if(anoMesReferencia){
			descricaoOcorrenciaMovimento = "ANO/M�S DE REFER�NCIA DA CONTA INV�LIDA";
		}
		return descricaoOcorrenciaMovimento;
	}

	/**
	 * UC242 - Registrar Movimento Arrecadadores - [FS0010] � Validar valor debitado/recebido
	 * 
	 * @param registroHelperCodigoF
	 * @param descricaoOcorrenciaMovimento
	 * @return
	 */
	private String validarValorDebitoRecebido(RegistroHelperCodigoF registroHelperCodigoF, String descricaoOcorrenciaMovimento){

		boolean valorDebitoInvalido;
		valorDebitoInvalido = Util.validarValorNaoNumerico(registroHelperCodigoF.getValorDebito());
		if(valorDebitoInvalido){
			descricaoOcorrenciaMovimento = "VALOR DEBITADO/RECEBIDO N�O NUM�RICO";
		}
		return descricaoOcorrenciaMovimento;
	}

	/**
	 * UC242 - Registrar Movimento Arrecadadores - [FS0011 � Validar matr�cula do im�vel]
	 * 
	 * @param descricaoOcorrenciaMovimento
	 * @param matriculaImovel
	 * @param idImovelNaBase
	 * @return
	 * @throws ControladorException
	 */
	private String validarMatriculaImovel(String descricaoOcorrenciaMovimento, String matriculaImovel) throws ControladorException{

		// valida a matricula do im�vel
		Integer idImovelNaBase = null;
		boolean matriculaImovelInvalida = Util.validarValorNaoNumerico(matriculaImovel);
		if(matriculaImovelInvalida){
			descricaoOcorrenciaMovimento = "M�TRICULA DO IM�VEL INV�LIDA";
		}else{

			try{
				idImovelNaBase = repositorioImovel.recuperarMatriculaImovel(Integer.valueOf(matriculaImovel));
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}

			if(idImovelNaBase == null){
				descricaoOcorrenciaMovimento = "M�TRICULA DO IM�VEL N�O CADASTRADA";
			}
		}
		return descricaoOcorrenciaMovimento;
	}

	public Object[] execParamAtualizarDebitoAutomaticoContaGuia(ParametroSistema parametroSistema, String descricaoOcorrenciaMovimento,
					Integer idImovelNaBase, RegistroHelperCodigoF registroHelperCodigoF, RegistroHelperCodigoA registroHelperCodigoA)
					throws ControladorException{

		DebitoAutomaticoMovimento debitoAutomaticoMovimento = null;
		Imovel imovel = null;
		Integer idConta = null;
		Integer idGuiaPagamento = null;
		Integer identificacaoConta = null;
		Integer identificacaoGuiaPagamento = null;
		Integer numeroPrestacao = null;
		Boolean indicadorPagamentoConta = false;
		Boolean indicadorPagamentoGuia = false;

		if(Util.isNumero(registroHelperCodigoF.getIndicadorPagamentoGuia(), false, 0)
						&& !Util.removerZerosEsquerda(registroHelperCodigoF.getIndicadorPagamentoGuia()).equals("0")){

			indicadorPagamentoGuia = true;
		}else{

			indicadorPagamentoConta = true;
		}

		if(indicadorPagamentoConta){
			if(idImovelNaBase != null){
				try{
					imovel = new Imovel();
					imovel.setId(idImovelNaBase);
					idConta = repositorioFaturamento.pesquisarExistenciaContaComSituacaoAtual(imovel, Integer.valueOf(registroHelperCodigoF
									.getAnoMesReferenciaConta()));
					if(idConta != null){
						identificacaoConta = idConta;
						debitoAutomaticoMovimento = repositorioFaturamento.obterDebitoAutomaticoMovimento(idImovelNaBase, Integer
										.valueOf(registroHelperCodigoF.getAnoMesReferenciaConta()));
					}else{
						Collection idsContaHistorico = repositorioFaturamento.pesquisarExistenciaContaHistoricoPorRefEImovel(imovel,
										Integer
										.valueOf(registroHelperCodigoF.getAnoMesReferenciaConta()));
						if(idsContaHistorico == null || idsContaHistorico.isEmpty()){
							descricaoOcorrenciaMovimento = "CONTA INEXISTENTE";
						}else{
							Integer idContaHistorico = (Integer) Util.retonarObjetoDeColecao(idsContaHistorico);
							identificacaoConta = idContaHistorico;
							debitoAutomaticoMovimento = repositorioFaturamento.obterDebitoAutomaticoMovimento(idImovelNaBase, Integer
											.valueOf(registroHelperCodigoF.getAnoMesReferenciaConta()));
						}
					}
				}catch(ErroRepositorioException e){
					throw new ControladorException("erro.sistema", e);
				}
			}
			// se debitoAutomaticaMovimento for nula seta o valor para o
			// campo descri��o movimento caso contrario atualiza o
			// debitoAutomaticaMovimento
			if(debitoAutomaticoMovimento != null){

				DebitoAutomaticoRetornoCodigo debitoAutomaticoRetornoCodigo = new DebitoAutomaticoRetornoCodigo();
				debitoAutomaticoRetornoCodigo.setId(Integer.valueOf(registroHelperCodigoF.getCodigoRetorno()));
				debitoAutomaticoMovimento.setDebitoAutomaticoRetornoCodigo(debitoAutomaticoRetornoCodigo);
				debitoAutomaticoMovimento.setRetornoBanco(new Date());
				debitoAutomaticoMovimento.setNumeroSequenciaArquivoRecebido(Integer.valueOf(registroHelperCodigoA
								.getNumeroSequencialArquivo()));
				debitoAutomaticoMovimento.setUltimaAlteracao(new Date());
				getControladorUtil().atualizar(debitoAutomaticoMovimento);
			}
		}

		if(indicadorPagamentoGuia){
			if(!Util.isInteger(registroHelperCodigoF.getIdentificacaoGuia())){
				descricaoOcorrenciaMovimento = "IDENTIFICA��O DA GUIA DE PAGAMENTO N�O NUM�RICO";
				return new Object[] {identificacaoConta, identificacaoGuiaPagamento, numeroPrestacao, descricaoOcorrenciaMovimento};
			}
			if(!Util.isInteger(registroHelperCodigoF.getNumeroParcela())){
				descricaoOcorrenciaMovimento = "N�MERO DA PARCELA N�O NUM�RICO";
				return new Object[] {identificacaoConta, identificacaoGuiaPagamento, numeroPrestacao, descricaoOcorrenciaMovimento};
			}

			if(idImovelNaBase != null){
				try{
					idGuiaPagamento = repositorioFaturamento.pesquisarExistenciaGuiaPamentoComSituacaoAtual(idImovelNaBase, Util
									.obterInteger(registroHelperCodigoF.getIdentificacaoGuia()), Util.obterInteger(registroHelperCodigoF
									.getNumeroParcela()));
					if(idGuiaPagamento != null){
						identificacaoGuiaPagamento = idGuiaPagamento;
						numeroPrestacao = Util.obterInteger(registroHelperCodigoF.getNumeroParcela());
					}else{
						Integer idGuiaPagamentoHistorico = repositorioFaturamento.pesquisarExistenciaGuiaPamentoHistoricoComSituacaoAtual(
										idImovelNaBase, Util.obterInteger(registroHelperCodigoF.getIdentificacaoGuia()), Util
														.obterInteger(registroHelperCodigoF.getNumeroParcela()));
						if(idGuiaPagamentoHistorico == null){
							descricaoOcorrenciaMovimento = "GUIA DE PAGAMENTO INEXISTENTE";
						}else{
							identificacaoGuiaPagamento = idGuiaPagamentoHistorico;
							numeroPrestacao = Util.obterInteger(registroHelperCodigoF.getNumeroParcela());
						}
					}

					if(descricaoOcorrenciaMovimento.equals("OK")){
						debitoAutomaticoMovimento = repositorioFaturamento.obterDebitoAutomaticoMovimentoGuiaPagamento(
										identificacaoGuiaPagamento, numeroPrestacao);
					}
					if(debitoAutomaticoMovimento != null){

						DebitoAutomaticoRetornoCodigo debitoAutomaticoRetornoCodigo = new DebitoAutomaticoRetornoCodigo();
						debitoAutomaticoRetornoCodigo.setId(Integer.valueOf(registroHelperCodigoF.getCodigoRetorno()));
						debitoAutomaticoMovimento.setDebitoAutomaticoRetornoCodigo(debitoAutomaticoRetornoCodigo);
						debitoAutomaticoMovimento.setRetornoBanco(new Date());
						debitoAutomaticoMovimento.setNumeroSequenciaArquivoRecebido(Integer.valueOf(registroHelperCodigoA
										.getNumeroSequencialArquivo()));
						debitoAutomaticoMovimento.setUltimaAlteracao(new Date());
						getControladorUtil().atualizar(debitoAutomaticoMovimento);
					}

				}catch(ErroRepositorioException e){
					throw new ControladorException("erro.sistema", e);
				}
			}

		}

		return new Object[] {identificacaoConta, identificacaoGuiaPagamento, numeroPrestacao, descricaoOcorrenciaMovimento, indicadorPagamentoConta, indicadorPagamentoGuia};
	}

	public Object[] execParamDistribuirPagamentoLegadoAda(ParametroSistema parametroSistema, String codigoBarras,
					RegistroHelperCodigoBarras registroHelperCodigoBarras, Integer tipoPagamento) throws ControladorException{

		RegistroHelperCodigoBarrasTipoPagamento registroHelperCodigoBarrasTipoPagamento = null;
		Integer tipoPagamentoAux = null;
		String descricaoOcorrenciaMovimento = null;

		String idPagamento = codigoBarras.substring(19, 44);

		String paramIndicadorTrataLegado = ParametroArrecadacao.P_INDICADOR_TRATA_LEGADO.executar();

		if(ConstantesSistema.SIM.toString().equals(paramIndicadorTrataLegado)){
			Short codigoEmpresaFebraban = Short.parseShort(registroHelperCodigoBarras.getIdEmpresa());
			String adaAnoMesReferenciaContaLegado = idPagamento.substring(0, 5);
			String adaTipoDocumento = idPagamento.substring(20, 23);
			String adaGrupoFaturamentoConstanteLegado = idPagamento.substring(23, 25);
			boolean registroLegadoAda = this.getControladorArrecadacao().validarRegistroLegadoADA(codigoEmpresaFebraban,
							adaGrupoFaturamentoConstanteLegado, adaAnoMesReferenciaContaLegado, adaTipoDocumento, "");

			if(registroLegadoAda){
				// redefine o Tipo de Pagamento do Documento Legado e atribui o helper como legado
				registroHelperCodigoBarras.setLegado(true);

				Object[] pagamentoCodigoBarras = this.getControladorArrecadacao().distribuirDadosCodigoBarrasPorTipoPagamentoLegadoADA(
								idPagamento, tipoPagamento, registroHelperCodigoBarras.getIdEmpresa());

				registroHelperCodigoBarrasTipoPagamento = (RegistroHelperCodigoBarrasTipoPagamento) pagamentoCodigoBarras[0];
				tipoPagamentoAux = (Integer) pagamentoCodigoBarras[1];
				descricaoOcorrenciaMovimento = (String) pagamentoCodigoBarras[2];
			}else{
				// chama o m�todo distribuirDadosCodigoBarrasPorTipoPagamento para distribuir os
				// dados de acordo com o tipo de pagamento
				Object[] pagamentoCodigoBarras = this.getControladorArrecadacao().distribuirDadosCodigoBarrasPorTipoPagamento(idPagamento,
								tipoPagamento, registroHelperCodigoBarras.getIdEmpresa());

				registroHelperCodigoBarrasTipoPagamento = (RegistroHelperCodigoBarrasTipoPagamento) pagamentoCodigoBarras[0];
				tipoPagamentoAux = (Integer) pagamentoCodigoBarras[1];
				descricaoOcorrenciaMovimento = (String) pagamentoCodigoBarras[2];

			}
		}else{
			// [SB0020] � Distribuir C�digo de Barras � Modelo Gsan Parametrizado
			Object[] pagamentoCodigoBarras = this.getControladorArrecadacao().distribuirDadosCodigoBarrasPeloModeloParametrizado(
							idPagamento);

			registroHelperCodigoBarrasTipoPagamento = (RegistroHelperCodigoBarrasTipoPagamento) pagamentoCodigoBarras[0];
			tipoPagamentoAux = (Integer) pagamentoCodigoBarras[1];
			descricaoOcorrenciaMovimento = (String) pagamentoCodigoBarras[2];
		}

		Object[] retorno = new Object[3];

		retorno[0] = registroHelperCodigoBarrasTipoPagamento;
		retorno[1] = tipoPagamentoAux;
		retorno[2] = descricaoOcorrenciaMovimento;

		return retorno;
	}

	public Object[] execParamDistribuirPagamento(ParametroSistema parametroSistema, String codigoBarras,
					RegistroHelperCodigoBarras registroHelperCodigoBarras, Integer tipoPagamento) throws ControladorException{

		// recupera o id pagamento da string
		String idPagamento = codigoBarras.substring(19, 44);

		RegistroHelperCodigoBarrasTipoPagamento registroHelperCodigoBarrasTipoPagamento = null;
		Integer tipoPagamentoAux = null;
		String descricaoOcorrenciaMovimento = null;

		// chama o m�todo distribuirDadosCodigoBarrasPorTipoPagamento para distribuir os dados
		// de acordo com o tipo de pagamento
		Object[] pagamentoCodigoBarras = this.getControladorArrecadacao().distribuirDadosCodigoBarrasPorTipoPagamento(idPagamento,
						tipoPagamento, registroHelperCodigoBarras.getIdEmpresa());

		registroHelperCodigoBarrasTipoPagamento = (RegistroHelperCodigoBarrasTipoPagamento) pagamentoCodigoBarras[0];
		tipoPagamentoAux = (Integer) pagamentoCodigoBarras[1];
		descricaoOcorrenciaMovimento = (String) pagamentoCodigoBarras[2];

		Object[] retorno = new Object[3];

		retorno[0] = registroHelperCodigoBarrasTipoPagamento;
		retorno[1] = tipoPagamentoAux;
		retorno[2] = descricaoOcorrenciaMovimento;

		return retorno;
	}

	/**
	 * Formata Header da movimenta��o do d�bito autom�tico do arquivo txt no formato 1.
	 * 
	 * @param parametroSistema
	 * @param registroTipoE
	 * @param debitoAutomaticoMovimento
	 * @param numeroSequencialArquivo
	 * @return
	 * @throws ControladorException
	 */
	public StringBuilder execParamFormatarHeaderDebitoAutomaticoFormato1(ParametroSistema parametroSistema, StringBuilder arquivoTXT)
					throws ControladorException{

		arquivoTXT.append(Util.completaString("", 51));
		arquivoTXT.append(" ");

		return arquivoTXT;

	}

	/**
	 * Formata Header da movimenta��o do d�bito autom�tico do arquivo txt no formato 2.
	 * 
	 * @param parametroSistema
	 * @param registroTipoE
	 * @param debitoAutomaticoMovimento
	 * @param numeroSequencialArquivo
	 * @return
	 * @throws ControladorException
	 */
	public StringBuilder execParamFormatarHeaderDebitoAutomaticoFormato2(ParametroSistema parametroSistema, StringBuilder arquivoTXT)
					throws ControladorException{

		arquivoTXT.append(Util.completaString("", 51));
		arquivoTXT.append(Util.completaString("0", 1));

		return arquivoTXT;
	}

	/**
	 * Formata Trailler da movimenta��o do d�bito autom�tico do arquivo txt no formato 1.
	 * 
	 * @param parametroSistema
	 * @param registroTipoE
	 * @param debitoAutomaticoMovimento
	 * @param numeroSequencialArquivo
	 * @return
	 * @throws ControladorException
	 */
	public StringBuilder execParamFormatarTraillerDebitoAutomaticoFormato1(ParametroSistema parametroSistema, StringBuilder arquivoTXT)
					throws ControladorException{

		arquivoTXT.append(Util.completaString("", 125));
		arquivoTXT.append(" ");

		return arquivoTXT;
	}

	/**
	 * Formata Trailler da movimenta��o do d�bito autom�tico do arquivo txt no formato 2.
	 * 
	 * @param parametroSistema
	 * @param registroTipoE
	 * @param debitoAutomaticoMovimento
	 * @param numeroSequencialArquivo
	 * @return
	 * @throws ControladorException
	 */
	public StringBuilder execParamFormatarTraillerDebitoAutomaticoFormato2(ParametroSistema parametroSistema, StringBuilder arquivoTXT)
					throws ControladorException{

		arquivoTXT.append(Util.completaString("", 125));
		arquivoTXT.append(Util.completaString("0", 1));

		return arquivoTXT;
	}

	/**
	 * [UC0264] � Distribuir Dados do C�digo de Barras
	 * [SB0020] � Distribuir C�digo de Barras � Modelo Gsan Parametrizado
	 * 
	 * @author Hebert Falc�o
	 * @date 20/06/2012
	 */
	public Object[] execParamDistribuirPagamentoModeloParametrizado(ParametroSistema parametroSistema, String codigoBarras,
					RegistroHelperCodigoBarras registroHelperCodigoBarras, Integer tipoPagamento) throws ControladorException{

		RegistroHelperCodigoBarrasTipoPagamento registroHelperCodigoBarrasTipoPagamento = null;
		Integer tipoPagamentoAux = null;
		String descricaoOcorrenciaMovimento = null;

		String idPagamento = codigoBarras.substring(19, 44);

		Object[] pagamentoCodigoBarras = this.getControladorArrecadacao().distribuirDadosCodigoBarrasPeloModeloParametrizado(idPagamento);

		registroHelperCodigoBarrasTipoPagamento = (RegistroHelperCodigoBarrasTipoPagamento) pagamentoCodigoBarras[0];
		tipoPagamentoAux = (Integer) pagamentoCodigoBarras[1];
		descricaoOcorrenciaMovimento = (String) pagamentoCodigoBarras[2];

		Object[] retorno = new Object[3];

		retorno[0] = registroHelperCodigoBarrasTipoPagamento;
		retorno[1] = tipoPagamentoAux;
		retorno[2] = descricaoOcorrenciaMovimento;

		return retorno;
	}

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB2000] Gerar Remunera��o Cobran�a Administrativa - Modelo 1
	 * 
	 * @author Hebert Falc�o
	 * @date 09/11/2013
	 */
	public void execParamGerarRemuneracaoCobrancaAdministrativaModelo1(ParametroSistema parametroSistema, Integer idPagamento)
					throws ControladorException{

		this.getControladorArrecadacao().gerarRemuneracaoCobrancaAdministrativaModelo1(idPagamento);
	}

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB2001] Gerar Remunera��o Cobran�a Administrativa - Modelo 2
	 * 
	 * @author Hebert Falc�o
	 * @date 09/11/2013
	 */
	public void execParamGerarRemuneracaoCobrancaAdministrativaModelo2(ParametroSistema parametroSistema, Integer idPagamento)
					throws ControladorException{

		this.getControladorArrecadacao().gerarRemuneracaoCobrancaAdministrativaModelo2(idPagamento);
	}

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB1000] Gerar Remunera��o Acr�scimos para Cobran�a Administrativa - Modelo 1
	 * 
	 * @author Hebert Falc�o
	 * @date 09/11/2013
	 */
	public boolean execParamGerarRemuneracaoAcrescimosParaCobrancaAdministrativaModelo1(ParametroSistema parametroSistema,
					Pagamento pagamento) throws ControladorException{

		return this.getControladorArrecadacao().gerarRemuneracaoAcrescimosParaCobrancaAdministrativaModelo1(pagamento);
	}

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB1001] Gerar Remunera��o Acr�scimos para Cobran�a Administrativa - Modelo 2
	 * 
	 * @author Hebert Falc�o
	 * @date 09/11/2013
	 */
	public boolean execParamGerarRemuneracaoAcrescimosParaCobrancaAdministrativaModelo2(ParametroSistema parametroSistema,
					Pagamento pagamento) throws ControladorException{

		return this.getControladorArrecadacao().gerarRemuneracaoAcrescimosParaCobrancaAdministrativaModelo2(pagamento);
	}

}
