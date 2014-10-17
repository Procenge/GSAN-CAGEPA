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
 * 
 * 
 * GSANPCG
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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

package gcom.integracao.cagepa.faturamento;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.MovimentoRoteiroEmpresa;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ControladorUtilLocal;
import gcom.util.ControladorUtilLocalHome;
import gcom.util.ErroRepositorioException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

/**
 * Controlador Cobranca PADRÃO
 * 
 * @author Josenildo Neves
 * @date 15/08/2012
 */

public class ControladorIntegracaoCagepaFaturamento
				implements SessionBean, IControladorIntegracaoCagepaFaturamento {

	private static final int INTEGER_ZERO = Integer.parseInt("0");

	private static final int INTEGER_UM = Integer.parseInt("1");

	private static final int INTEGER_100 = Integer.parseInt("100");

	private static final Short SHORT_ZERO = Short.parseShort("0");

	private static final Short SHORT_UM = Short.parseShort("1");

	private static final Short SHORT_DOIS = Short.parseShort("2");

	private static final String STRING_VAZIA = " ";

	private static final String STRING_ZERO = "0";

	private static final String STRING_DOIS = "2";

	private static final String STRING_S = "S";

	private static final String STRING_N = "N";

	private static final Long LONG_ZERO = Long.parseLong("0");

	private static final Date DATA_DEFAULT = Util.criarData(31, 12, 9999);

	private static final Logger LOGGER = Logger.getLogger(ControladorIntegracaoCagepaFaturamento.class);

	protected static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	protected IRepositorioIntegracaoCagepaFaturamento repositorioIntegracaoCagepaFaturamento = null;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioIntegracaoCagepaFaturamento = RepositorioIntegracaoCagepaFaturamentoHBM.getInstancia();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados do faturamento imediato na tabela METLIGACAO
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public void gerarDadosFaturamentoImediatoParaCagepa(Collection colecaoMovimentoRoteiroEmpresa) throws ControladorException{

		if(!Util.isVazioOrNulo(colecaoMovimentoRoteiroEmpresa)){

			// *************************************************************************
			// Variáveis auxiliares utilizadas na busca das informações
			// *************************************************************************
			Collection<MetLigacao> colecaoMetLigacao = new ArrayList<MetLigacao>();
			MetLigacao metLigacao = null;

			LigacaoAgua ligacaoAgua = null;
			Cliente clienteResponsavel = null;
			Cliente clienteProprietario = null;
			ClienteImovel clienteImovel = null;
			int[] faixaLeituraEsperada = null;
			List<Integer> colecaoTipoConsumoUltimos2Meses = null;
			int qtdeConsumosReais = 0;
			List<Integer> colecaoAnormalidadeConsumoUltimosMeses = null;
			int qtdeAnormalidadesConsumoAC = 0;
			int qtdeAnormalidadesConsumoBC = 0;
			Date dataAtual = new Date();
			Integer tipoAnormalidadeConsumo = null;
			Integer ultimoAnoMesConsumoReal = null;
			Integer ultimoAnoMesConsumo = null;

			// *************************************************************************
			// Variáveis utilizadas para capturar as informações
			// *************************************************************************
			Integer mesFatura = null; // Item 1
			Integer anoFatura = null; // Item 2
			Integer localidadeId = null; // Item 3
			Integer codigoSetorComercial = null; // Item 4
			Integer numeroQuadra = null; // Item 5
			Short numeroLote = null; // Item 6
			Short sequenciaMacro = null; // Item 7
			Integer ligacaoId = null; // Item 8
			String nomeConsumidor = null; // Item 9
			String tipoResponsavel = null; // Item 10
			Short emiteFatura = null; // Item 11
			Integer responsavelId = null; // Item 12
			Integer tarifaUsuarioId = null; // Item 13
			Integer situacaoAguaId = null; // Item 14
			Integer situacaoEsgotoId = null; // Item 15
			Integer cep = null; // Item 17
			Integer consumoFixoAgua = null; // Item 18
			Integer consumoFixoEsgoto = null; // Item 19
			Integer bancoId = null; // Item 20
			String logradouro = null; // Item 21
			String bairro = null; // Item 22
			String numeroAltera = null; // Item 23
			Date dataVencimentoFatura = null; // Item 24
			Short numeroEconomiasResidenciais = null; // Item 25
			Short numeroEconomiasComerciais = null; // Item 26
			Short numeroEconomiasIndustriais = null; // Item 27
			Short numeroEconomiasPublicas = null; // Item 28
			Integer ligacaoMacroId = null; // Item 29
			Date dataUltimoCorte = null; // Item 30
			Short numeroDigitosHidrometro = null; // Item 32
			Integer fone = null; // Item 33
			Short lacreHidrometroId = null; // Item 34
			String numeroHidrometro = null; // Item 35
			String instalacaoHidrometro = null; // Item 36
			String marcaHidrometro = null; // Item 37
			String capacidadeHidrometro = null; // Item 38
			Integer mediaConsumo = null; // Item 39
			Integer valorLeituraAnterior = null; // Item 40
			Date dataLeituraAnterior = null; // Item 41
			Integer indicadorLeituraAnterior = null; // Item 42
			Date dataInstalacaoHidrometro = null; // Item 43
			Integer valorLeituraMinima = null; // Item 44
			Integer valorLeituraMaxima = null; // Item 45
			String indicadorConsumoReal2Meses = null; // Item 46
			Short indAnormalidadeAC6Meses = null; // Item 47
			Short indAnormalidadeBC4Meses = null; // Item 48
			Short indicadorCpfCnpj = null; // Item 49
			Long numeroCpfCnpj = null; // Item 50
			BigDecimal percentualImposto = null; // Item 51
			Integer referenciaConsumo1 = null; // Item 52
			Integer idAnormalidadeLeitura1 = null; // Item 53
			Integer numeroConsumo1 = null; // Item 54
			Integer referenciaConsumo2 = null; // Item 55
			Integer idAnormalidadeLeitura2 = null; // Item 56
			Integer numeroConsumo2 = null; // Item 57
			Integer referenciaConsumo3 = null; // Item 58
			Integer idAnormalidadeLeitura3 = null; // Item 59
			Integer numeroConsumo3 = null; // Item 60
			Integer referenciaConsumo4 = null; // Item 61
			Integer idAnormalidadeLeitura4 = null; // Item 62
			Integer numeroConsumo4 = null; // Item 63
			Integer referenciaConsumo5 = null; // Item 64
			Integer idAnormalidadeLeitura5 = null; // Item 65
			Integer numeroConsumo5 = null; // Item 66
			Integer referenciaConsumo6 = null; // Item 67
			Integer idAnormalidadeLeitura6 = null; // Item 68
			Integer numeroConsumo6 = null; // Item 69
			BigDecimal valorCreditos = null; // Item 70
			String descricaoRubrica1 = null; // Item 71
			BigDecimal valorRubrica1 = null; // Item 72
			String descricaoRubrica2 = null; // Item 73
			BigDecimal valorRubrica2 = null; // Item 74
			String descricaoRubrica3 = null; // Item 75
			BigDecimal valorRubrica3 = null; // Item 76
			String descricaoRubrica4 = null; // Item 77
			BigDecimal valorRubrica4 = null; // Item 78
			String descricaoRubrica5 = null; // Item 79
			BigDecimal valorRubrica5 = null; // Item 80
			String descricaoRubrica6 = null; // Item 81
			BigDecimal valorRubrica6 = null; // Item 82
			String descricaoRubrica7 = null; // Item 83
			BigDecimal valorRubrica7 = null; // Item 84
			String descricaoRubrica8 = null; // Item 85
			BigDecimal valorRubrica8 = null; // Item 86
			String descricaoRubrica9 = null; // Item 87
			BigDecimal valorRubrica9 = null; // Item 88
			String descricaoRubrica10 = null; // Item 89
			BigDecimal valorRubrica10 = null; // Item 80
			Integer consumoMinimo = null; // Item 95
			BigDecimal valorDebitos = null; // Item 105
			String cidade = null; // Item 128
			Integer cicloId = null; // Item 129
			Short tipoColetaLeitura = null; // Item 130
			Date dataProximaLeitura = null; // Item 132
			Integer rotaId = null; // Item 134
			Integer numeroDocumentoCobranca = null; // Item 135
			Integer regionalId = null; // Item 138
			Integer indicadorExisteDebito = null; // 160
			String teveFaltaLeituraAnterior = null; // 162
			Integer valorUltimaLeituraReal = null; // Item 163
			Integer numMesesUltimaLeituraReal = null; // Item 64

			// Registrando início da operação no Log
			LOGGER.info("******* Iniciando geração dos dados do Faturamento Imediato para Integração com a CAGEPA *******");

			// Captura o Item 51 (percentualImposto), pois não depende do MovimentoRoteiroEmpresa e
			// pode ser consultado apenas uma vez
			try{
				percentualImposto = repositorioIntegracaoCagepaFaturamento.obterSomaAliquotasImpostos();

				if(percentualImposto == null){
					percentualImposto = BigDecimal.ZERO;
				}
			}catch(ErroRepositorioException e){
				throw new ControladorException(e, "Erro ao consultar Soma das Alíquotas dos Impostos");
			}

			for(MovimentoRoteiroEmpresa movRotEmp : (Collection<MovimentoRoteiroEmpresa>) colecaoMovimentoRoteiroEmpresa){

				// *************************************************************************
				// Informações capturadas diretamente do MovimentoRoteiroEmpresa
				// *************************************************************************
				mesFatura = movRotEmp.getAnoMesMovimento() != null ? Util.obterMes(movRotEmp.getAnoMesMovimento()) : INTEGER_ZERO;

				anoFatura = movRotEmp.getAnoMesMovimento() != null ? Util.obterAno(movRotEmp.getAnoMesMovimento()) : INTEGER_ZERO;

				localidadeId = movRotEmp.getLocalidade() != null ? movRotEmp.getLocalidade().getId() : INTEGER_ZERO;

				codigoSetorComercial = movRotEmp.getCodigoSetorComercial() != null ? movRotEmp.getCodigoSetorComercial() : INTEGER_ZERO;

				numeroQuadra = movRotEmp.getNumeroQuadra() != null ? movRotEmp.getNumeroQuadra() : INTEGER_ZERO;

				numeroLote = movRotEmp.getNumeroLoteImovel() != null ? movRotEmp.getNumeroLoteImovel() : SHORT_ZERO;

				sequenciaMacro = movRotEmp.getNumeroSubLoteImovel() != null ? movRotEmp.getNumeroSubLoteImovel() : SHORT_ZERO;

				ligacaoId = movRotEmp.getImovel() != null ? movRotEmp.getImovel().getId() : INTEGER_ZERO;

				nomeConsumidor = !Util.isVazioOuBranco(movRotEmp.getNomeCliente()) ? Util.completaString(movRotEmp.getNomeCliente(), 30)
								.trim() : STRING_VAZIA;

				tipoResponsavel = !Util.isVazioOuBranco(movRotEmp.getIndicadorImpostoFederal()) ? Util.completaString(
								movRotEmp.getIndicadorImpostoFederal().toString(), 1).trim() : STRING_DOIS;

				emiteFatura = movRotEmp.getIndicadorEmissao() != null ? movRotEmp.getIndicadorEmissao() : SHORT_ZERO;

				tarifaUsuarioId = movRotEmp.getConsumoTarifa() != null ? movRotEmp.getConsumoTarifa().getId() : INTEGER_ZERO;

				situacaoAguaId = movRotEmp.getLigacaoAguaSituacao() != null ? movRotEmp.getLigacaoAguaSituacao().getId() : INTEGER_ZERO;

				situacaoEsgotoId = movRotEmp.getLigacaoEsgotoSituacao() != null ? movRotEmp.getLigacaoEsgotoSituacao().getId()
								: INTEGER_ZERO;

				consumoFixoAgua = movRotEmp.getNumeroConsumoFixoAgua() != null ? movRotEmp.getNumeroConsumoFixoAgua() : INTEGER_ZERO;

				consumoFixoEsgoto = movRotEmp.getNumeroConsumoFixoEsgoto() != null ? movRotEmp.getNumeroConsumoFixoEsgoto() : INTEGER_ZERO;

				logradouro = !Util.isVazioOuBranco(movRotEmp.getEnderecoImovel()) ? Util.completaString(movRotEmp.getEnderecoImovel(), 50)
								.trim() : STRING_VAZIA;

				bairro = !Util.isVazioOuBranco(movRotEmp.getBairroEnderecoImovel()) ? Util.completaString(
								movRotEmp.getBairroEnderecoImovel(), 20).trim() : STRING_VAZIA;

				numeroAltera = movRotEmp.getImovel() != null && !Util.isVazioOuBranco(movRotEmp.getImovel().getNumeroImovel()) ? Util
								.completaString(movRotEmp.getImovel().getNumeroImovel(), 5).trim() : STRING_VAZIA;

				dataVencimentoFatura = movRotEmp.getDataVencimento() != null ? movRotEmp.getDataVencimento() : DATA_DEFAULT;

				numeroEconomiasResidenciais = movRotEmp.getQuantidadeEconomiasResidencial() != null ? movRotEmp
								.getQuantidadeEconomiasResidencial() : SHORT_ZERO;

				numeroEconomiasComerciais = movRotEmp.getQuantidadeEconomiasComercial() != null ? movRotEmp
								.getQuantidadeEconomiasComercial() : SHORT_ZERO;

				numeroEconomiasIndustriais = movRotEmp.getQuantidadeEconomiasIndustrial() != null ? movRotEmp
								.getQuantidadeEconomiasIndustrial() : SHORT_ZERO;

				numeroEconomiasPublicas = movRotEmp.getQuantidadeEconomiasPublica() != null ? movRotEmp.getQuantidadeEconomiasPublica()
								: SHORT_ZERO;

				mediaConsumo = movRotEmp.getNumeroConsumoMedio() != null ? movRotEmp.getNumeroConsumoMedio() : INTEGER_ZERO;

				valorLeituraAnterior = movRotEmp.getNumeroLeituraAnterior() != null ? movRotEmp.getNumeroLeituraAnterior() : INTEGER_ZERO;

				dataLeituraAnterior = movRotEmp.getDataLeituraAnterior() != null ? movRotEmp.getDataLeituraAnterior() : DATA_DEFAULT;

				indicadorLeituraAnterior = movRotEmp.getIdLeituraSituacaoAnterior() != null ? movRotEmp.getIdLeituraSituacaoAnterior()
								: INTEGER_ZERO;

				dataInstalacaoHidrometro = movRotEmp.getDataInstalacaoHidrometro() != null ? movRotEmp.getDataInstalacaoHidrometro()
								: DATA_DEFAULT;

				referenciaConsumo1 = movRotEmp.getReferenciaConsumo1() != null ? Integer.parseInt(Util
								.formatarAnoMesParaMesAnoCom2Digitos(movRotEmp.getReferenciaConsumo1())) : INTEGER_ZERO;

				idAnormalidadeLeitura1 = movRotEmp.getIdAnormalidadeLeitura1() != null ? movRotEmp.getIdAnormalidadeLeitura1()
								: INTEGER_ZERO;

				numeroConsumo1 = movRotEmp.getNumeroConsumo1() != null ? movRotEmp.getNumeroConsumo1() : INTEGER_ZERO;

				referenciaConsumo2 = movRotEmp.getReferenciaConsumo2() != null ? Integer.parseInt(Util
								.formatarAnoMesParaMesAnoCom2Digitos(movRotEmp.getReferenciaConsumo2())) : INTEGER_ZERO;

				idAnormalidadeLeitura2 = movRotEmp.getIdAnormalidadeLeitura2() != null ? movRotEmp.getIdAnormalidadeLeitura2()
								: INTEGER_ZERO;

				numeroConsumo2 = movRotEmp.getNumeroConsumo2() != null ? movRotEmp.getNumeroConsumo2() : INTEGER_ZERO;

				referenciaConsumo3 = movRotEmp.getReferenciaConsumo3() != null ? Integer.parseInt(Util
								.formatarAnoMesParaMesAnoCom2Digitos(movRotEmp.getReferenciaConsumo3())) : INTEGER_ZERO;

				idAnormalidadeLeitura3 = movRotEmp.getIdAnormalidadeLeitura3() != null ? movRotEmp.getIdAnormalidadeLeitura3()
								: INTEGER_ZERO;

				numeroConsumo3 = movRotEmp.getNumeroConsumo3() != null ? movRotEmp.getNumeroConsumo3() : INTEGER_ZERO;

				referenciaConsumo4 = movRotEmp.getReferenciaConsumo4() != null ? Integer.parseInt(Util
								.formatarAnoMesParaMesAnoCom2Digitos(movRotEmp.getReferenciaConsumo4())) : INTEGER_ZERO;

				idAnormalidadeLeitura4 = movRotEmp.getIdAnormalidadeLeitura4() != null ? movRotEmp.getIdAnormalidadeLeitura4()
								: INTEGER_ZERO;

				numeroConsumo4 = movRotEmp.getNumeroConsumo4() != null ? movRotEmp.getNumeroConsumo4() : INTEGER_ZERO;

				referenciaConsumo5 = movRotEmp.getReferenciaConsumo5() != null ? Integer.parseInt(Util
								.formatarAnoMesParaMesAnoCom2Digitos(movRotEmp.getReferenciaConsumo5())) : INTEGER_ZERO;

				idAnormalidadeLeitura5 = movRotEmp.getIdAnormalidadeLeitura5() != null ? movRotEmp.getIdAnormalidadeLeitura5()
								: INTEGER_ZERO;

				numeroConsumo5 = movRotEmp.getNumeroConsumo5() != null ? movRotEmp.getNumeroConsumo5() : INTEGER_ZERO;

				referenciaConsumo6 = movRotEmp.getReferenciaConsumo6() != null ? Integer.parseInt(Util
								.formatarAnoMesParaMesAnoCom2Digitos(movRotEmp.getReferenciaConsumo6())) : INTEGER_ZERO;

				idAnormalidadeLeitura6 = movRotEmp.getIdAnormalidadeLeitura6() != null ? movRotEmp.getIdAnormalidadeLeitura6()
								: INTEGER_ZERO;

				numeroConsumo6 = movRotEmp.getNumeroConsumo6() != null ? movRotEmp.getNumeroConsumo6() : INTEGER_ZERO;

				valorCreditos = movRotEmp.getValorCreditos() != null ? movRotEmp.getValorCreditos() : BigDecimal.ZERO;

				descricaoRubrica1 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica1()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica1(), 30).trim() : STRING_VAZIA;

				valorRubrica1 = movRotEmp.getValorRubrica1() != null ? movRotEmp.getValorRubrica1() : BigDecimal.ZERO;

				descricaoRubrica2 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica2()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica2(), 30).trim() : STRING_VAZIA;

				valorRubrica2 = movRotEmp.getValorRubrica2() != null ? movRotEmp.getValorRubrica2() : BigDecimal.ZERO;

				descricaoRubrica3 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica3()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica3(), 30).trim() : STRING_VAZIA;

				valorRubrica3 = movRotEmp.getValorRubrica3() != null ? movRotEmp.getValorRubrica3() : BigDecimal.ZERO;

				descricaoRubrica4 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica4()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica4(), 30).trim() : STRING_VAZIA;

				valorRubrica4 = movRotEmp.getValorRubrica4() != null ? movRotEmp.getValorRubrica4() : BigDecimal.ZERO;

				descricaoRubrica5 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica5()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica5(), 30).trim() : STRING_VAZIA;

				valorRubrica5 = movRotEmp.getValorRubrica5() != null ? movRotEmp.getValorRubrica5() : BigDecimal.ZERO;

				descricaoRubrica6 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica6()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica6(), 30).trim() : STRING_VAZIA;

				valorRubrica6 = movRotEmp.getValorRubrica6() != null ? movRotEmp.getValorRubrica6() : BigDecimal.ZERO;

				descricaoRubrica7 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica7()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica7(), 30).trim() : STRING_VAZIA;

				valorRubrica7 = movRotEmp.getValorRubrica7() != null ? movRotEmp.getValorRubrica7() : BigDecimal.ZERO;

				descricaoRubrica8 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica8()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica8(), 30).trim() : STRING_VAZIA;

				valorRubrica8 = movRotEmp.getValorRubrica8() != null ? movRotEmp.getValorRubrica8() : BigDecimal.ZERO;

				descricaoRubrica9 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica9()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica9(), 30).trim() : STRING_VAZIA;

				valorRubrica9 = movRotEmp.getValorRubrica9() != null ? movRotEmp.getValorRubrica9() : BigDecimal.ZERO;

				descricaoRubrica10 = !Util.isVazioOuBranco(movRotEmp.getDescricaoRubrica10()) ? Util.completaString(
								movRotEmp.getDescricaoRubrica10(), 30).trim() : STRING_VAZIA;

				valorRubrica10 = movRotEmp.getValorRubrica10() != null ? movRotEmp.getValorRubrica10() : BigDecimal.ZERO;

				numeroDocumentoCobranca = movRotEmp.getNumeroDocumentoCobranca() != null ? movRotEmp.getNumeroDocumentoCobranca()
								: INTEGER_ZERO;

				valorDebitos = movRotEmp.getValorDebitos() != null ? movRotEmp.getValorDebitos() : BigDecimal.ZERO;

				// ****************************************************************************
				// Informações capturadas a partir de alguma consulta ou com maior complexidade
				// ****************************************************************************

				// Consulta o cliente Responsável
				clienteImovel = consultarClienteImovel(movRotEmp, ClienteRelacaoTipo.RESPONSAVEL);

				// Item 12
				if(clienteImovel != null && clienteImovel.getCliente() != null){
					clienteResponsavel = clienteImovel.getCliente();
					responsavelId = clienteResponsavel.getId();
				}else{
					responsavelId = INTEGER_ZERO;
				}

				// Item 17
				if(!Util.isVazioOuBranco(movRotEmp.getCepEnderecoImovel())){
					try{
						cep = Integer.parseInt(movRotEmp.getCepEnderecoImovel());
					}catch(NumberFormatException e){
						throw new ControladorException(e, "Erro ao converter o CEP para Inteiro");
					}
				}else{
					cep = INTEGER_ZERO;
				}

				// Item 20
				if(movRotEmp.getImovel() != null){
					try{
						bancoId = repositorioIntegracaoCagepaFaturamento.obterBancoId(movRotEmp.getImovel().getId());
					}catch(ErroRepositorioException e){
						throw new ControladorException(e, "Erro ao consultar Id do Banco");
					}

					if(bancoId == null){
						bancoId = INTEGER_ZERO;
					}
				}else{
					bancoId = INTEGER_ZERO;
				}

				// Item 29
				if(movRotEmp.getImovel() != null && movRotEmp.getImovel().getImovelCondominio() != null){
					ligacaoMacroId = movRotEmp.getImovel().getImovelCondominio().getId();

					if(ligacaoMacroId == null){
						ligacaoMacroId = INTEGER_ZERO;
					}
				}else{
					ligacaoMacroId = INTEGER_ZERO;
				}

				// Item 30
				ligacaoAgua = consultarLigacaoAgua(movRotEmp);

				if(movRotEmp.getLigacaoAguaSituacao() != null && movRotEmp.getImovel() != null){

					Integer ligAguaSituacaoId = movRotEmp.getLigacaoAguaSituacao().getId();


					if(ligacaoAgua != null){
						// Se LAST_ID corresponde a "Suprimido", "Suprimido Parcial",
						// "Suprimido Total"
						if(ligAguaSituacaoId.equals(LigacaoAguaSituacao.SUPRIMIDO)
										|| ligAguaSituacaoId.equals(LigacaoAguaSituacao.SUPR_PARC)
										|| ligAguaSituacaoId.equals(LigacaoAguaSituacao.SUPRIMIDO_DEFINITIVO)){
							dataUltimoCorte = ligacaoAgua.getDataSupressao();

							// Caso a data LAGU_DTCORTE seja diferente de null
						}else if(ligacaoAgua.getDataCorte() != null){
							dataUltimoCorte = ligacaoAgua.getDataCorte();

							// Caso contrário, atribui a data padrão '9999-12-31'
						}else{
							dataUltimoCorte = DATA_DEFAULT;
						}
					}else{
						dataUltimoCorte = DATA_DEFAULT;
					}
					// Caso LAST_ID ou IMOV_ID seja null, atribui a data padrão '9999-12-31'
				}else{
					dataUltimoCorte = DATA_DEFAULT;
				}

				// Item 32
				if(movRotEmp.getNumeroDigitosLeitura() != null){
					numeroDigitosHidrometro = movRotEmp.getNumeroDigitosLeitura();
				}else{
					numeroDigitosHidrometro = SHORT_ZERO;
				}

				// Item 33
				String foneStr = null;
				if(movRotEmp.getImovel() != null){
					try{
						foneStr = repositorioIntegracaoCagepaFaturamento.obterTelefonePadrao(movRotEmp.getImovel().getId());
					}catch(ErroRepositorioException e){
						throw new ControladorException(e, "Erro ao consultar Telefone Padrão do Imovel");
					}

					if(!Util.isVazioOuBranco(foneStr)){
						try{

							fone = Integer.parseInt(foneStr);

						}catch(NumberFormatException e){
							throw new ControladorException(e, "Erro ao converter o Telefone para Inteiro");
						}
					}else{
						fone = INTEGER_ZERO;
					}
				}else{
					fone = INTEGER_ZERO;
				}

				// Item 34
				if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null
								&& ligacaoAgua.getHidrometroInstalacaoHistorico().getNumeroSelo() != null){
					lacreHidrometroId = SHORT_ZERO;
				}else{
					lacreHidrometroId = SHORT_UM;
				}

				// Item 35
				if(!Util.isVazioOuBranco(movRotEmp.getNumeroHidrometro())){
					numeroHidrometro = Util.completaString(movRotEmp.getNumeroHidrometro(), 10).trim();
				}else{
					numeroHidrometro = STRING_VAZIA;
				}

				// Item 36
				if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null
								&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao() != null){
					instalacaoHidrometro = Util.completaString(
									ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao().getId().toString(), 3)
									.trim();
				}else{
					instalacaoHidrometro = STRING_ZERO;
				}

				// Item 37
				if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null
								&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro() != null
								&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroMarca() != null
								&& !Util.isVazioOuBranco(ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro()
												.getHidrometroMarca().getDescricaoAbreviada())){
					marcaHidrometro = Util.completaString(
									ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroMarca()
													.getDescricaoAbreviada(), 3).trim();
				}else{
					marcaHidrometro = STRING_VAZIA;
				}

				// Item 38
				if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null
								&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro() != null
								&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroCapacidade() != null
								&& !Util.isVazioOuBranco(ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro()
												.getHidrometroCapacidade().getDescricao())){
					capacidadeHidrometro = Util.completaString(ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro()
													.getHidrometroCapacidade().getDescricao(), 6).trim();
				}else{
					capacidadeHidrometro = STRING_VAZIA;
				}

				// Item 44 e 45
				if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null
								&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro() != null){

					// <<Inclui>> [UC0086] Calcular Faixa de Leitura Esperada
					faixaLeituraEsperada = getControladorMicromedicao().calcularFaixaLeituraEsperada(mediaConsumo, null,
									ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro(), valorLeituraAnterior);

					if(faixaLeituraEsperada != null && faixaLeituraEsperada.length == 2){
						valorLeituraMinima = faixaLeituraEsperada[0];
						valorLeituraMaxima = faixaLeituraEsperada[1];
					}else{
						valorLeituraMinima = INTEGER_ZERO;
						valorLeituraMaxima = INTEGER_ZERO;
					}
				}else{
					valorLeituraMinima = INTEGER_ZERO;
					valorLeituraMaxima = INTEGER_ZERO;
				}

				// Item 46
				if(movRotEmp.getImovel() != null){
					try{
						colecaoTipoConsumoUltimos2Meses = repositorioIntegracaoCagepaFaturamento.obterTipoConsumoUltimosMeses(movRotEmp
										.getImovel().getId(), 2);

						if(!Util.isVazioOrNulo(colecaoTipoConsumoUltimos2Meses)){
							for(Integer tipoConsumo : colecaoTipoConsumoUltimos2Meses){
								if(tipoConsumo != null && tipoConsumo.equals(ConsumoTipo.REAL)){
									qtdeConsumosReais++;
								}
							}

							if(qtdeConsumosReais == 2){
								indicadorConsumoReal2Meses = "S";
							}else{
								indicadorConsumoReal2Meses = STRING_VAZIA;
							}
						}else{
							indicadorConsumoReal2Meses = STRING_VAZIA;
						}
					}catch(ErroRepositorioException e){
						throw new ControladorException(e, "Erro ao consultar Tipos de Consumo dos últimos 2 meses");
					}
				}else{
					indicadorConsumoReal2Meses = STRING_VAZIA;
				}

				// Item 47 e Item 48
				qtdeAnormalidadesConsumoAC = 0;
				qtdeAnormalidadesConsumoBC = 0;

				if(movRotEmp.getImovel() != null){

					try{
						colecaoAnormalidadeConsumoUltimosMeses = repositorioIntegracaoCagepaFaturamento
										.obterAnormalidadeConsumoUltimosMeses(movRotEmp.getImovel().getId(), 6);

					}catch(ErroRepositorioException e){
						throw new ControladorException(e, "Erro ao consultar Tipos de Consumo dos últimos 2 meses");
					}

					if(!Util.isVazioOrNulo(colecaoAnormalidadeConsumoUltimosMeses)){
						Integer anormalidadeConsumo;
						for(int i = 0; i < colecaoAnormalidadeConsumoUltimosMeses.size(); i++){

							anormalidadeConsumo = colecaoAnormalidadeConsumoUltimosMeses.get(i);

							if(anormalidadeConsumo != null){

								// TODO LGALVAO: Verificar quais são os tipos de anormalidade
								// consumo referentes a "AF" e "AA". Será visto após a migração dos
								// dados da CAGEPA, pois atualmente não existe estas informações
								// para as empresas já existentes
								if(anormalidadeConsumo.equals(ConsumoAnormalidade.ALTO_CONSUMO)){
									qtdeAnormalidadesConsumoAC++;
								}

								// Verifica entre os 4 primeiros itens, se há anormalidades do
								// tipo BC (Item 48)
								if(i < 4){
									if(anormalidadeConsumo != null && (anormalidadeConsumo.equals(ConsumoAnormalidade.BAIXO_CONSUMO))){
										qtdeAnormalidadesConsumoBC++;
									}
								}
							}
						}

						// Item 47
						if(qtdeAnormalidadesConsumoAC > 0){
							indAnormalidadeAC6Meses = SHORT_UM;
						}else{
							indAnormalidadeAC6Meses = SHORT_ZERO;
						}

						// Item 48
						if(qtdeAnormalidadesConsumoBC > 0){
							indAnormalidadeBC4Meses = SHORT_UM;
						}else{
							indAnormalidadeBC4Meses = SHORT_ZERO;
						}
					}else{
						indAnormalidadeAC6Meses = SHORT_ZERO;
						indAnormalidadeBC4Meses = SHORT_ZERO;
					}

				}else{
					indAnormalidadeAC6Meses = SHORT_ZERO;
					indAnormalidadeBC4Meses = SHORT_ZERO;
				}

				// Consulta o cliente Proprietário
				clienteImovel = consultarClienteImovel(movRotEmp, ClienteRelacaoTipo.PROPRIETARIO);

				// Item 49 e 50
				if(clienteImovel != null && clienteImovel.getCliente() != null){
					clienteProprietario = clienteImovel.getCliente();

					// Item 49
					if(clienteProprietario.getCnpj() != null){
						indicadorCpfCnpj = SHORT_UM;
					}else{
						indicadorCpfCnpj = SHORT_ZERO;
					}

					// Item 50
					String numeroCpfCnpjStr = STRING_ZERO;
					if(!Util.isVazioOuBranco(clienteProprietario.getCnpj())){
						numeroCpfCnpjStr = Util.completaString(clienteProprietario.getCnpj(), 15).trim();
					}else if(!Util.isVazioOuBranco(clienteProprietario.getCpf())){
						numeroCpfCnpjStr = Util.completaString(clienteProprietario.getCpf(), 15).trim();
					}

					if(!Util.isVazioOuBranco(numeroCpfCnpjStr)){
						numeroCpfCnpj = Long.parseLong(numeroCpfCnpjStr);
					}else{
						numeroCpfCnpj = LONG_ZERO;
					}

				}else{
					indicadorCpfCnpj = SHORT_ZERO;
					numeroCpfCnpj = LONG_ZERO;
				}

				// Item 95
				int somaConsumoMinimo = 0;
				if(movRotEmp.getQuantidadeEconomiasResidencial() != null){
					somaConsumoMinimo = somaConsumoMinimo + 10 * movRotEmp.getQuantidadeEconomiasResidencial();
				}
				if(movRotEmp.getQuantidadeEconomiasComercial() != null){
					somaConsumoMinimo = somaConsumoMinimo + 10 * movRotEmp.getQuantidadeEconomiasComercial();
				}
				if(movRotEmp.getQuantidadeEconomiasIndustrial() != null){
					somaConsumoMinimo = somaConsumoMinimo + 10 * movRotEmp.getQuantidadeEconomiasIndustrial();
				}
				if(movRotEmp.getQuantidadeEconomiasPublica() != null){
					somaConsumoMinimo = somaConsumoMinimo + 10 * movRotEmp.getQuantidadeEconomiasPublica();
				}

				consumoMinimo = Integer.valueOf(somaConsumoMinimo);

				// Item 128
				if(movRotEmp.getLocalidade() != null && !Util.isVazioOuBranco(movRotEmp.getLocalidade().getDescricao())){
					cidade = Util.completaString(movRotEmp.getLocalidade().getDescricao(), 25).trim();
				}else{
					cidade = STRING_VAZIA;
				}

				// Item 129
				if(movRotEmp.getFaturamentoGrupo() != null){
					cicloId = movRotEmp.getFaturamentoGrupo().getId();
				}else{
					cicloId = INTEGER_ZERO;
				}

				// Item 130
				boolean verificacaoLigacaoAgua = (ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() == null
								&& movRotEmp.getLigacaoAguaSituacao() != null && movRotEmp.getLigacaoAguaSituacao().getId()
								.equals(LigacaoAguaSituacao.LIGADO));

				boolean verificacaoImovel = false;

				try{
					verificacaoImovel = (movRotEmp.getImovel() != null && repositorioIntegracaoCagepaFaturamento
									.possuiSituacaoFaturamentoImovel(movRotEmp.getImovel().getId(),
													FaturamentoSituacaoTipo.PARALISAR_EMISSAO_CONTAS));
				}catch(ErroRepositorioException e){
					throw new ControladorException(e, "Erro ao consultar Situação de Faturamento do Imóvel");
				}

				if(verificacaoLigacaoAgua || verificacaoImovel){
					tipoColetaLeitura = SHORT_DOIS;
				}else{
					tipoColetaLeitura = SHORT_ZERO;
				}

				// Item 132
				if(movRotEmp.getAnoMesMovimento() != null && movRotEmp.getFaturamentoGrupo() != null){
					try{
						dataProximaLeitura = repositorioIntegracaoCagepaFaturamento.obterDataPrevistaFaturamentoAtivCronograma(Util
										.somaMesAnoMesReferencia(movRotEmp.getAnoMesMovimento(), 1), movRotEmp.getFaturamentoGrupo()
										.getId(), FaturamentoAtividade.GERAR_ARQUIVO_LEITURA);

						if(dataProximaLeitura == null){
							dataProximaLeitura = DATA_DEFAULT;
						}
					}catch(ErroRepositorioException e){
						throw new ControladorException(e, "Erro ao consultar Data Prevista do Cronograma de Atividades do Faturamento");
					}
				}else{
					dataProximaLeitura = DATA_DEFAULT;
				}

				// Item 134
				if(movRotEmp.getRota() != null){
					rotaId = movRotEmp.getRota().getId();
				}else{
					rotaId = INTEGER_ZERO;
				}

				// Item 138
				if(movRotEmp.getLocalidade() != null && movRotEmp.getLocalidade().getGerenciaRegional() != null){
					regionalId = movRotEmp.getLocalidade().getGerenciaRegional().getId();
				}else{
					regionalId = INTEGER_ZERO;
				}

				// Item 160
				Collection colecaoContasPendentes = consultarContasPendentes(dataAtual, movRotEmp);

				if(Util.isVazioOrNulo(colecaoContasPendentes)){
					indicadorExisteDebito = INTEGER_ZERO;
				}else{
					indicadorExisteDebito = INTEGER_UM;
				}

				// Item 162
				tipoAnormalidadeConsumo = null;

				if(movRotEmp.getImovel() != null){
					try{
						tipoAnormalidadeConsumo = repositorioIntegracaoCagepaFaturamento.obterTipoAnormalidadeConsumo(movRotEmp.getImovel()
										.getId());

					}catch(ErroRepositorioException e){
						throw new ControladorException(e, "Erro ao consultar Tipo de Anormalidade de Consumo");
					}

					if(tipoAnormalidadeConsumo != null && tipoAnormalidadeConsumo.equals(ConsumoAnormalidade.LEITURA_NAO_INFORMADA)){
						teveFaltaLeituraAnterior = STRING_S;
					}else{
						teveFaltaLeituraAnterior = STRING_N;
					}

				}else{
					teveFaltaLeituraAnterior = STRING_N;
				}

				// Item 163 e 164
				if(movRotEmp.getImovel() != null){
					Object[] retornoConsulta;
					try{
						retornoConsulta = repositorioIntegracaoCagepaFaturamento.obterUltimaLeituraConsumoReal(movRotEmp.getImovel()
										.getId(), ConsumoTipo.REAL);
					}catch(ErroRepositorioException e){
						throw new ControladorException(e, "Erro ao consultar Última Leitura de Consumo Real");
					}

					// Item 163
					if(!Util.isVazioOrNulo(retornoConsulta)){

						ultimoAnoMesConsumoReal = (Integer) retornoConsulta[0];
						valorUltimaLeituraReal = (Integer) retornoConsulta[1];

					}else{
						ultimoAnoMesConsumoReal = null;
						valorUltimaLeituraReal = INTEGER_ZERO;
					}

					// Item 164
					if(ultimoAnoMesConsumoReal != null){

						try{
							ultimoAnoMesConsumo = repositorioIntegracaoCagepaFaturamento.obterAnoMesFaturamentoConsumoMaisRecente(movRotEmp
											.getImovel().getId());
						}catch(ErroRepositorioException e){
							throw new ControladorException(e, "Erro ao consultar Ano/Mês da Leitura mais recente do imóvel");
						}

						if(ultimoAnoMesConsumo != null){
							numMesesUltimaLeituraReal = ultimoAnoMesConsumo - ultimoAnoMesConsumoReal + 1;
						}else{
							numMesesUltimaLeituraReal = INTEGER_ZERO;
						}
					}else{
						numMesesUltimaLeituraReal = INTEGER_ZERO;
					}
				}else{
					ultimoAnoMesConsumoReal = null;
					valorUltimaLeituraReal = INTEGER_ZERO;
					numMesesUltimaLeituraReal = INTEGER_ZERO;
				}

				// *************************************************************************
				// Cria um novo objeto MetLigacao
				// *************************************************************************
				MetLigacaoPK comp_id = new MetLigacaoPK();
				comp_id.setMesFatura(mesFatura); // Item 1
				comp_id.setAnoFatura(anoFatura); // Item 2
				comp_id.setLigacaoId(ligacaoId); // Item 8

				metLigacao = new MetLigacao();
				metLigacao.setComp_id(comp_id);
				metLigacao.setLocalidadeId(localidadeId); // Item 3
				metLigacao.setCodigoSetorComercial(codigoSetorComercial); // Item 4
				metLigacao.setNumeroQuadra(numeroQuadra); // Item 5
				metLigacao.setNumeroLote(numeroLote); // Item 6
				metLigacao.setSequenciaMacro(sequenciaMacro); // Item 7
				metLigacao.setNomeConsumidor(nomeConsumidor); // Item 9
				metLigacao.setTipoResponsavel(tipoResponsavel); // Item 10
				metLigacao.setEmiteFatura(emiteFatura); // Item 11
				metLigacao.setResponsavelId(responsavelId); // Item 12
				metLigacao.setTarifaUsuarioId(tarifaUsuarioId); // Item 13
				metLigacao.setSituacaoAguaId(situacaoAguaId); // Item 14
				metLigacao.setSituacaoEsgotoId(situacaoEsgotoId); // Item 15
				metLigacao.setGrandeCliente(INTEGER_ZERO); // Item 16
				metLigacao.setCep(cep); // Item 17
				metLigacao.setConsumoFixoAgua(consumoFixoAgua); // Item 18
				metLigacao.setConsumoFixoEsgoto(consumoFixoEsgoto); // Item 19
				metLigacao.setBancoId(bancoId); // Item 20
				metLigacao.setLogradouro(logradouro); // Item 21
				metLigacao.setBairro(bairro); // Item 22
				metLigacao.setNumeroAltera(numeroAltera); // Item 23
				metLigacao.setDataVencimentoFatura(dataVencimentoFatura); // Item 24
				metLigacao.setNumeroEconomiasResidenciais(numeroEconomiasResidenciais); // Item 25
				metLigacao.setNumeroEconomiasComerciais(numeroEconomiasComerciais); // Item 26
				metLigacao.setNumeroEconomiasIndustriais(numeroEconomiasIndustriais); // Item 27
				metLigacao.setNumeroEconomiasPublicas(numeroEconomiasPublicas); // Item 28
				metLigacao.setLigacaoMacroId(ligacaoMacroId); // Item 29
				metLigacao.setDataUltimoCorte(dataUltimoCorte); // Item 30
				metLigacao.setPercentualTarifa(INTEGER_100); // Item 31
				metLigacao.setNumeroDigitosHidrometro(numeroDigitosHidrometro); // Item 32
				metLigacao.setFone(fone); // Item 33
				metLigacao.setLacreHidrometroId(lacreHidrometroId); // Item 34
				metLigacao.setNumeroHidrometro(numeroHidrometro); // Item 35
				metLigacao.setInstalacaoHidrometro(instalacaoHidrometro); // Item 36
				metLigacao.setMarcaHidrometro(marcaHidrometro); // Item 37
				metLigacao.setCapacidadeHidrometro(capacidadeHidrometro); // Item 38
				metLigacao.setMediaConsumo(mediaConsumo); // Item 39
				metLigacao.setValorLeituraAnterior(valorLeituraAnterior); // Item 40
				metLigacao.setDataLeituraAnterior(dataLeituraAnterior); // Item 41
				metLigacao.setIndicadorLeituraAnterior(indicadorLeituraAnterior); // Item 42
				metLigacao.setDataInstalacaoHidrometro(dataInstalacaoHidrometro); // Item 43
				metLigacao.setValorLeituraMinima(valorLeituraMinima); // Item 44
				metLigacao.setValorLeituraMaxima(valorLeituraMaxima); // Item 45
				metLigacao.setIndicadorConsumoReal2Meses(indicadorConsumoReal2Meses); // Item 46
				metLigacao.setIndicadorAnormalidadeAC6Meses(indAnormalidadeAC6Meses); // Item 47
				metLigacao.setIndicadorAnormalidadeBC4Meses(indAnormalidadeBC4Meses); // Item 48
				metLigacao.setIndicadorCpfCnpj(indicadorCpfCnpj); // Item 49
				metLigacao.setNumeroCpfCnpj(numeroCpfCnpj); // Item 50
				metLigacao.setPercentualImposto(percentualImposto); // Item 51
				metLigacao.setMesLeitura1(referenciaConsumo1); // Item 52
				metLigacao.setOcorrenciaLeitura1(idAnormalidadeLeitura1); // Item 53
				metLigacao.setConsumoLeitura1(numeroConsumo1); // Item 54
				metLigacao.setMesLeitura2(referenciaConsumo2); // Item 55
				metLigacao.setOcorrenciaLeitura2(idAnormalidadeLeitura2); // Item 56
				metLigacao.setConsumoLeitura2(numeroConsumo2); // Item 57
				metLigacao.setMesLeitura3(referenciaConsumo3); // Item 58
				metLigacao.setOcorrenciaLeitura3(idAnormalidadeLeitura3); // Item 59
				metLigacao.setConsumoLeitura3(numeroConsumo3); // Item 60
				metLigacao.setMesLeitura4(referenciaConsumo4); // Item 61
				metLigacao.setOcorrenciaLeitura4(idAnormalidadeLeitura4); // Item 62
				metLigacao.setConsumoLeitura4(numeroConsumo4); // Item 63
				metLigacao.setMesLeitura5(referenciaConsumo5); // Item 64
				metLigacao.setOcorrenciaLeitura5(idAnormalidadeLeitura5); // Item 65
				metLigacao.setConsumoLeitura5(numeroConsumo5); // Item 66
				metLigacao.setMesLeitura6(referenciaConsumo6); // Item 67
				metLigacao.setOcorrenciaLeitura6(idAnormalidadeLeitura6); // Item 68
				metLigacao.setConsumoLeitura6(numeroConsumo6); // Item 69
				metLigacao.setValorCredito(valorCreditos); // Item 70
				metLigacao.setDescricaoRubrica1(descricaoRubrica1); // Item 71
				metLigacao.setValorRubrica1(valorRubrica1); // Item 72
				metLigacao.setDescricaoRubrica2(descricaoRubrica2); // Item 73
				metLigacao.setValorRubrica2(valorRubrica2); // Item 74
				metLigacao.setDescricaoRubrica3(descricaoRubrica3); // Item 75
				metLigacao.setValorRubrica3(valorRubrica3); // Item 76
				metLigacao.setDescricaoRubrica4(descricaoRubrica4); // Item 77
				metLigacao.setValorRubrica4(valorRubrica4); // Item 78
				metLigacao.setDescricaoRubrica5(descricaoRubrica5); // Item 79
				metLigacao.setValorRubrica5(valorRubrica5); // Item 80
				metLigacao.setDescricaoRubrica6(descricaoRubrica6); // Item 81
				metLigacao.setValorRubrica6(valorRubrica6); // Item 82
				metLigacao.setDescricaoRubrica7(descricaoRubrica7); // Item 83
				metLigacao.setValorRubrica7(valorRubrica7); // Item 84
				metLigacao.setDescricaoRubrica8(descricaoRubrica8); // Item 85
				metLigacao.setValorRubrica8(valorRubrica8); // Item 86
				metLigacao.setDescricaoRubrica9(descricaoRubrica9); // Item 87
				metLigacao.setValorRubrica9(valorRubrica9); // Item 88
				metLigacao.setDescricaoRubrica10(descricaoRubrica10); // Item 89
				metLigacao.setValorRubrica10(valorRubrica10); // Item 90
				metLigacao.setValorLeituraAtual(INTEGER_ZERO); // Item 91
				metLigacao.setValorLeituraAtribuida(INTEGER_ZERO); // Item 92
				metLigacao.setConsumo(INTEGER_ZERO); // Item 93
				metLigacao.setConsumoFaturado(INTEGER_ZERO); // Item 94
				metLigacao.setConsumoMinimo(consumoMinimo); // Item 95
				metLigacao.setConsumoRateio(INTEGER_ZERO); // Item 96
				metLigacao.setOcorrenciaId(INTEGER_ZERO); // Item 97
				metLigacao.setOcorrenciaConsumoId(STRING_VAZIA); // Item 98
				metLigacao.setFaturaEmitida(INTEGER_ZERO); // Item 99
				metLigacao.setLocalidadeFaturaEntregue(INTEGER_ZERO); // Item 100
				metLigacao.setDiasFaturados(INTEGER_ZERO); // Item 101
				metLigacao.setDataLeituraAtual(dataAtual); // Item 102
				metLigacao.setValorAgua(BigDecimal.ZERO); // Item 103
				metLigacao.setValorEsgoto(BigDecimal.ZERO); // Item 104
				metLigacao.setValorServico(valorDebitos); // Item 105
				metLigacao.setValorDevolvido(BigDecimal.ZERO); // Item 106
				metLigacao.setValorSaldoDevolvido(BigDecimal.ZERO); // Item 107
				metLigacao.setIndicadorGrandeConsumoFaturado(SHORT_ZERO); // Item 108
				metLigacao.setIndicadorContatarUsuario(STRING_VAZIA); // Item 109
				metLigacao.setIndicadorReligarAgua(SHORT_ZERO); // Item 110
				metLigacao.setServicoReligacaoId(INTEGER_ZERO); // Item 111
				metLigacao.setValorReligacao(BigDecimal.ZERO); // Item 112
				metLigacao.setServicoSancaoId(INTEGER_ZERO); // Item 113
				metLigacao.setValorSancao(BigDecimal.ZERO); // Item 114
				metLigacao.setTipoConsumo(STRING_VAZIA); // Item 115
				metLigacao.setCondicaoLeitura(SHORT_ZERO); // Item 116
				metLigacao.setAlteNumeroImovel(STRING_VAZIA); // Item 117
				metLigacao.setIdDvCorteShdLigacao(STRING_VAZIA); // Item 118
				metLigacao.setIdDvNumeroHidrometro(STRING_VAZIA); // Item 119
				metLigacao.setIdDvCategoriaEconomia(STRING_VAZIA); // Item 120
				metLigacao.setIdDvLogradouro(STRING_VAZIA); // Item 121
				metLigacao.setIdDvRevisaoQuadra(STRING_VAZIA); // Item 122
				metLigacao.setIdDvFiscConsumo(STRING_VAZIA); // Item 123
				metLigacao.setIdDvUsuarioNaoMdChd(STRING_VAZIA); // Item 124
				metLigacao.setIndicadorHidrometroNaoLacrado(SHORT_ZERO); // Item 125
				metLigacao.setNumeroFoneContacto(INTEGER_ZERO); // Item 126
				metLigacao.setFuncionarioId(INTEGER_ZERO); // Item 127
				metLigacao.setCidade(cidade); // Item 128
				metLigacao.setCicloId(cicloId); // Item 129
				metLigacao.setTipoColetaLeitura(tipoColetaLeitura); // Item 130
				metLigacao.setDebitoAutomatico(SHORT_ZERO); // Item 131
				metLigacao.setDataProximaLeitura(dataProximaLeitura); // Item 132
				metLigacao.setDataGeracaoGCS(dataAtual); // Item 133
				metLigacao.setLivroSetor(rotaId); // Item 134
				metLigacao.setReavisoLigacaoId(numeroDocumentoCobranca); // Item 135
				metLigacao.setQuantidadeContasRevisao(INTEGER_ZERO); // Item 136
				metLigacao.setValorTotalRevisao(BigDecimal.ZERO); // Item 137
				metLigacao.setRegionalId(regionalId); // Item 138
				metLigacao.setValorImpostoRetorno(BigDecimal.ZERO); // Item 139
				metLigacao.setDataEmissao(dataAtual); // Item 140
				metLigacao.setStatusId(INTEGER_ZERO); // Item 141
				metLigacao.setTimedownload(dataAtual); // Item 142
				metLigacao.setTotalEmitido(BigDecimal.ZERO); // Item 143
				metLigacao.setDataTarefa(dataAtual); // Item 144
				metLigacao.setGeradoGeraOk(INTEGER_ZERO); // Item 145
				metLigacao.setLoginGeraOk(INTEGER_ZERO); // Item 146
				metLigacao.setDataGeraOk(dataAtual); // Item 147
				metLigacao.setLoginDistribuido(INTEGER_ZERO); // Item 148
				metLigacao.setDataDistribuido(dataAtual); // Item 149
				metLigacao.setLoginRemanejado(INTEGER_ZERO); // Item 150
				metLigacao.setDataRemanejado(dataAtual); // Item 151
				metLigacao.setLoginEnviadoCol(INTEGER_ZERO); // Item 152
				metLigacao.setDataEnviadoCol(dataAtual); // Item 153
				metLigacao.setEnviadoCol(INTEGER_ZERO); // Item 154
				metLigacao.setIndicadorFaturaRetida(INTEGER_ZERO); // Item 155
				metLigacao.setVersao(STRING_VAZIA); // Item 156
				metLigacao.setReimpressa(INTEGER_ZERO); // Item 157
				metLigacao.setReimpressaNotificacao(INTEGER_ZERO); // Item 158
				metLigacao.setDataLiberacao(dataAtual); // Item 159
				metLigacao.setIndicadorExisteDebito(indicadorExisteDebito); // Item 160
				metLigacao.setDescarregamento(INTEGER_ZERO); // Item 161
				metLigacao.setTeveFaltaLeituraAnterior(teveFaltaLeituraAnterior); // Item 162
				metLigacao.setValorUltimaLeituraReal(valorUltimaLeituraReal); // Item 163
				metLigacao.setNumeroMesesUltimaLeituraReal(numMesesUltimaLeituraReal); // Item 164

				// Adiciona o objeto MetLigacao criado na coleção que será persistida
				colecaoMetLigacao.add(metLigacao);

			}

			// Persiste a coleção de MetLigacao
			getControladorUtil().inserirColecaoObjetos(colecaoMetLigacao);

			// Registrando conclusão da operação no Log
			LOGGER.info("******* Concluído o processo de geração dos dados do Faturamento Imediato para Integração com a CAGEPA *******");
			int qtdeRegistrosProcessados = colecaoMovimentoRoteiroEmpresa != null ? colecaoMovimentoRoteiroEmpresa.size() : 0;
			LOGGER.info("******* Foram processados " + qtdeRegistrosProcessados + " registros de MOVIMENTO_ROTEIRO_EMPRESA *******");

		}

	}

	private Collection consultarContasPendentes(Date dataVencimento, MovimentoRoteiroEmpresa movRotEmp) throws ControladorException{

		Collection colecaoContasPendentes = null;

		if(dataVencimento != null && movRotEmp.getImovel() != null){
			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, movRotEmp.getImovel().getId()));
			filtroConta.adicionarParametro(new MenorQue(FiltroConta.DATA_VENCIMENTO, dataVencimento));
			colecaoContasPendentes = getControladorUtil().pesquisar(filtroConta, Conta.class.getName());
		}
		return colecaoContasPendentes;
	}

	private LigacaoAgua consultarLigacaoAgua(MovimentoRoteiroEmpresa movRotEmp) throws ControladorException{

		LigacaoAgua ligacaoAgua = null;

		if(movRotEmp.getImovel() != null){
			FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
			filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, movRotEmp.getImovel().getId()));
			filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade(FiltroLigacaoAgua.HIDROMETRO_INSTALACAO_HISTORICO);
			filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade(FiltroLigacaoAgua.HIDROMETRO_INSTALACAO_HISTORICO_HIDROMETRO);
			filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade(FiltroLigacaoAgua.HIDROMETRO_INSTALACAO_HISTORICO_LOCAL_INSTALACAO);
			filtroLigacaoAgua
							.adicionarCaminhoParaCarregamentoEntidade(FiltroLigacaoAgua.HIDROMETRO_INSTALACAO_HISTORICO_HIDROMETRO_HIDROMETRO_MARCA);
			filtroLigacaoAgua
							.adicionarCaminhoParaCarregamentoEntidade(FiltroLigacaoAgua.HIDROMETRO_INSTALACAO_HISTORICO_HIDROMETRO_HIDROMETRO_CAPACIDADE);

			ligacaoAgua = (LigacaoAgua) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroLigacaoAgua,
							LigacaoAgua.class.getName()));
		}
		return ligacaoAgua;
	}

	private ClienteImovel consultarClienteImovel(MovimentoRoteiroEmpresa movRotEmp, Integer clienteRelacaoTipoId)
					throws ControladorException{

		ClienteImovel clienteImovel = null;

		if(movRotEmp.getImovel() != null){
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, movRotEmp.getImovel().getId()));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID, clienteRelacaoTipoId));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);

			clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(ServiceLocator.getInstancia().getControladorUtil()
							.pesquisar(filtroClienteImovel, ClienteImovel.class.getName()));

		}
		return clienteImovel;
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

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

	protected ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}
}
