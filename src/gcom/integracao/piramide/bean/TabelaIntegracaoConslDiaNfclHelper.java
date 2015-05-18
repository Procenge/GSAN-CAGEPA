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
package gcom.integracao.piramide.bean;

import gcom.integracao.piramide.TabelaIntegracaoCmConDiaNfcl;
import gcom.integracao.piramide.TabelaIntegracaoCmConDiaNfclPK;
import gcom.integracao.piramide.TabelaIntegracaoConslDiaNfcl;

import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * @author mgrb
 *
 */
public class TabelaIntegracaoConslDiaNfclHelper {

	private Logger LOGGER = Logger.getLogger(TabelaIntegracaoConslDiaNfcl.class);

	public static final String COD_SIS_ORIGEM = "GSA";

	public static final Integer MODULO_FATURAMENTO = Integer.valueOf(1);

	public static final Integer MODULO_PARCELAMENTO = Integer.valueOf(3);

	public static final Integer MODULO_FINANCIAMENTO = Integer.valueOf(4);

	public static final Integer MODULO_CANCELAMENTO = Integer.valueOf(5);

	public static final Integer MODULO_GUIA_PAGAMENTO = Integer.valueOf(6);

	private Map<Integer, AcumuladorValoresSpedPisCofins> mapConslDiaNfcl = new HashMap<Integer, AcumuladorValoresSpedPisCofins>();

	private Integer idMunicipio;

	private Date dataContabil;

	private String codigoFilialOrigem;

	private Integer referenciaBase;

	private static Integer ultimoSequencial;

	/**
	 * TabelaIntegracaoConslDiaNfclHelper
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoConslDiaNfclHelper.
	 * </p>
	 * 
	 * @param ultimoSequencialConslDiaNfcl2
	 * @param idLocalidade2
	 * @param dtContabil
	 * @param codigoFilialOrigem2
	 * @param dtInicio
	 * @param vlAliquotaCofins
	 * @param vlAliquotaPis
	 * @author Marlos Ribeiro
	 * @param idMunicipio
	 * @since 15/03/2013
	 */
	@SuppressWarnings("static-access")
	public TabelaIntegracaoConslDiaNfclHelper(Integer ultimoSequencial, Integer referenciaBase, Integer idMunicipio, Date dtContabil,
												String codigoFilialOrigem) {

		this(referenciaBase, idMunicipio, dtContabil, codigoFilialOrigem);
		if(this.ultimoSequencial == null) //
		this.ultimoSequencial = (ultimoSequencial == null ? 0 : ultimoSequencial);
	}

	/**
	 * TabelaIntegracaoConslDiaNfclHelper
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoConslDiaNfclHelper.
	 * </p>
	 * 
	 * @param referenciaBase2
	 * @param idLoc
	 * @param dataContabilCorrente
	 * @param codigoFilialOrigem2
	 * @author Marlos Ribeiro
	 * @since 25/03/2013
	 */
	public TabelaIntegracaoConslDiaNfclHelper(Integer referenciaBase, Integer idMunicipio, Date dtContabil, String codigoFilialOrigem) {
		super();
		this.idMunicipio = idMunicipio;
		this.dataContabil = dtContabil;
		this.codigoFilialOrigem = codigoFilialOrigem;
		this.referenciaBase = referenciaBase;
	}

	/**
	 * @return the dataContabil
	 */
	public Date getDataContabil(){

		return dataContabil;
	}

	/**
	 * @return the idLocalidade
	 */
	public Integer getIdMunicipio(){

		return idMunicipio;
	}

	/**
	 * @return the codigoFilialOrigem
	 */
	public String getCodigoFilialOrigem(){

		return codigoFilialOrigem;
	}

	private TabelaIntegracaoConslDiaNfcl getNovaInstanciaConslDiaNfcl(Integer idMunicipio, Date dtLancamentoContabil){

		ultimoSequencial++;
		TabelaIntegracaoConslDiaNfcl conslDiaNfcl = new TabelaIntegracaoConslDiaNfcl();
		conslDiaNfcl.setCodigoSequenciaOrigem(ultimoSequencial);
		conslDiaNfcl.setCodigoFilialOrigem(codigoFilialOrigem);
		conslDiaNfcl.setCodigoSistemaOrigem(COD_SIS_ORIGEM);
		conslDiaNfcl.setCodigoModelo("29");
		conslDiaNfcl.setCodigoMunicipioOrigem(idMunicipio.toString());
		conslDiaNfcl.setCodigoSerieOrigem(" ");
		conslDiaNfcl.setCodigoSubSerieOrigem(" ");
		conslDiaNfcl.setDataDocumentosConsolidado(dtLancamentoContabil);
		conslDiaNfcl.setValorAcumuladoDescontos(BigDecimal.ZERO);
		conslDiaNfcl.setValorConsumoAcumulado(BigDecimal.ZERO);
		conslDiaNfcl.setValorCobradoTerceiros(BigDecimal.ZERO);
		conslDiaNfcl.setValorDespesasAcessoria(BigDecimal.ZERO);
		conslDiaNfcl.setValorBaseIcms(BigDecimal.ZERO);
		conslDiaNfcl.setValorIcms(BigDecimal.ZERO);
		conslDiaNfcl.setValorIcmsRetidoSubstituicaoTributaria(BigDecimal.ZERO);
		conslDiaNfcl.setValorBaseIcmsSubstituicaoTributaria(BigDecimal.ZERO);
		conslDiaNfcl.setValorAcumuladoPisPasep(BigDecimal.ZERO);
		conslDiaNfcl.setValorAcumuladoCofins(BigDecimal.ZERO);
		conslDiaNfcl.setCodigoOperacaoRegistro("I");
		conslDiaNfcl.setCodigoStatusRegistro("NP");
		conslDiaNfcl.setDescricaoErroRegistro(null);
		return conslDiaNfcl;
	}

	/**
	 * Método getAcumulador
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param codigoConsumoSped
	 * @return
	 * @author Marlos Ribeiro
	 * @since 15/03/2013
	 */
	public AcumuladorValoresSpedPisCofins getAcumulador(Integer codigoConsumoSped){

		AcumuladorValoresSpedPisCofins acumulador = mapConslDiaNfcl.get(codigoConsumoSped);
		return acumulador == null ? new AcumuladorValoresSpedPisCofins() : acumulador;
	}

	/**
	 * Método addAcumulador
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param acumulador
	 * @author Marlos Ribeiro
	 * @param codigoConsumoSped
	 * @since 15/03/2013
	 */
	private void addAcumulador(Integer codigoConsumoSped, AcumuladorValoresSpedPisCofins acumulador){

		mapConslDiaNfcl.put(codigoConsumoSped, acumulador);
	}

	/**
	 * Método acumularValoresFaturamento
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param classeConsumo
	 * @param vlConta
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public void acumularValoresFaturamento(Integer classeConsumo, BigDecimal vlConta){

		AcumuladorValoresSpedPisCofins acumulador = getAcumulador(classeConsumo);
		acumulador.addQtdDoctoNormal(1);
		acumulador.addValDocto(vlConta);
		acumulador.addValDoctoNormal(vlConta);
		addAcumulador(classeConsumo, acumulador);

	}

	/**
	 * Método acumularValoresJurosCorrecaoFaturamento
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param classeConsumo
	 * @param vlConta
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public void acumularValoresJurosCorrecaoFaturamento(Integer classeConsumo, BigDecimal valorJurosCorrecaoFaturamento){

		AcumuladorValoresSpedPisCofins acumulador = getAcumulador(classeConsumo);
		acumulador.addQtdDoctoJuros(1);
		acumulador.addValDoctoJuros(valorJurosCorrecaoFaturamento);
		addAcumulador(classeConsumo, acumulador);

	}

	/**
	 * Método acumularValoresParcelamento
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param classeConsumo
	 * @param vlConta
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public void acumularValoresParcelamento(Integer classeConsumo, BigDecimal valorJurosCorrecaoParcelamento){

		AcumuladorValoresSpedPisCofins acumulador = getAcumulador(classeConsumo);
		acumulador.addQtdDoctoJuros(1);
		acumulador.addValDoctoJuros(valorJurosCorrecaoParcelamento);
		addAcumulador(classeConsumo, acumulador);

	}

	/**
	 * Método getTIConslDiaNfclConsolidados
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @param aliquotaPis
	 * @param aliquotaCofins
	 * @since 18/03/2013
	 */
	public List<TabelaIntegracaoConslDiaNfcl> getTIConslDiaNfclConsolidados(BigDecimal aliquotaPis, BigDecimal aliquotaCofins){

		BigDecimal base_100 = BigDecimal.valueOf(100);

		List<TabelaIntegracaoConslDiaNfcl> retorno = new ArrayList<TabelaIntegracaoConslDiaNfcl>();
		for(Integer codClasseConsumo : mapConslDiaNfcl.keySet()){
			TabelaIntegracaoConslDiaNfcl tiConslDiaNfcl = getNovaInstanciaConslDiaNfcl(idMunicipio, dataContabil);
			AcumuladorValoresSpedPisCofins acumulador = mapConslDiaNfcl.get(codClasseConsumo);
			tiConslDiaNfcl.setCodigoClasseConsumo(codClasseConsumo.toString());
			tiConslDiaNfcl.setQtdDocumentosConsolidados(acumulador.getQtdDoctoNormal() + acumulador.getQtdDoctoJuros());
			tiConslDiaNfcl.setQtdDocumentosCancelados(acumulador.getQtdCancDoctoNormal());
			tiConslDiaNfcl.setDataDocumentosConsolidado(dataContabil);
			tiConslDiaNfcl.setValorTotalDocumentos(acumulador.getValDocto());

			BigDecimal valCancelamento = acumulador.getValCancDoctoNormal();
			BigDecimal valFornecimento = tiConslDiaNfcl.getValorTotalDocumentos().subtract(valCancelamento);
			if(tiConslDiaNfcl.getValorTotalDocumentos().compareTo(valCancelamento) < 0){
				LOGGER.error("VAL_DOC < VAL_CANCELAMENTO => " + tiConslDiaNfcl.getValorTotalDocumentos() + " - "
								+ acumulador.getValCancDoctoNormal() + " = " + valFornecimento);
			}

			tiConslDiaNfcl.setValorAcumuladoFornecimento(valFornecimento);
			tiConslDiaNfcl.setValorServicoNaoTributados(acumulador.getValDoctoJuros().subtract(acumulador.getValCancDoctoJuros()));
			tiConslDiaNfcl.setValorAcumuladoPisPasep(tiConslDiaNfcl.getValorAcumuladoFornecimento().multiply(aliquotaPis).divide(base_100));
			tiConslDiaNfcl.setValorAcumuladoCofins(tiConslDiaNfcl.getValorAcumuladoFornecimento().multiply(aliquotaCofins).divide(base_100));
			retorno.add(tiConslDiaNfcl);
		}
		return retorno;
	}

	/**
	 * Método getReferenciaBase
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 21/03/2013
	 */
	public Integer getReferenciaBase(){

		return referenciaBase;
	}

	/**
	 * Método acumularCancelamentoContaMes
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param vlTotalCancelamento
	 * @param vlFornecimento
	 * @author Marlos Ribeiro
	 * @param codigoConsumoSped
	 * @since 22/03/2013
	 */
	public void acumularCancelamentoContaMes(Integer codigoConsumoSped, BigDecimal vlTotalCancelamento){

		AcumuladorValoresSpedPisCofins acumulador = getAcumulador(codigoConsumoSped);
		acumulador.addQtdCancDoctoNormal(1);
		acumulador.addQtdCancDoctoNormalAnt(1);
		acumulador.addValCancDoctoNormal(vlTotalCancelamento);
		acumulador.addValCancDoctoNormalAnt(vlTotalCancelamento);
		addAcumulador(codigoConsumoSped, acumulador);
	}

	public List<TabelaIntegracaoCmConDiaNfcl> getTICmConDiaNfcl(String codigoClasseConsumo, Integer ultimoSequencialConslDiaNfcl,
					BigDecimal aliquotaPis, BigDecimal aliquotaCofins){

		AcumuladorValoresSpedPisCofins acumulador = getAcumulador(Integer.valueOf(codigoClasseConsumo));

		List<TabelaIntegracaoCmConDiaNfcl> retorno = new ArrayList<TabelaIntegracaoCmConDiaNfcl>();

		retorno.add(getInstanciaTICmConDiaNfcl(ultimoSequencialConslDiaNfcl, aliquotaPis, aliquotaCofins, true, acumulador));

		if(acumulador.getValDoctoJuros().compareTo(acumulador.getValCancDoctoJuros()) > 0){
			retorno.add(getInstanciaTICmConDiaNfcl(ultimoSequencialConslDiaNfcl, aliquotaPis, aliquotaCofins, false, acumulador));
		}

		return retorno;
	}

	/**
	 * @param ultimoSequencialConslDiaNfcl
	 * @param aliquotaPis
	 * @param aliquotaCofins
	 * @param isModelo01
	 * @param acumulador
	 * @return
	 */
	private TabelaIntegracaoCmConDiaNfcl getInstanciaTICmConDiaNfcl(Integer ultimoSequencialConslDiaNfcl, BigDecimal aliquotaPis,
					BigDecimal aliquotaCofins, boolean isModelo01, AcumuladorValoresSpedPisCofins acumulador){

		Integer codSeqItemOrigem = isModelo01 ? 1 : 2;
		String codigoSituacaoTributariaPis = isModelo01 ? "01" : "06";
		String codigoSituacaoTributariaConfins = isModelo01 ? "01" : "06";
		String codigoContribuicaoSocialApuradaPis = "01";
		String codigoContribuicaoSocialApuradaConfins = "01";
		BigDecimal base_100 = BigDecimal.valueOf(100);

		BigDecimal valorItem;
		if(isModelo01){
			valorItem = acumulador.getValDocto().subtract(acumulador.getValCancDoctoNormal());
		}else{
			valorItem = acumulador.getValDoctoJuros().subtract(acumulador.getValCancDoctoJuros());
		}

		TabelaIntegracaoCmConDiaNfcl cmConDiaNfcl = new TabelaIntegracaoCmConDiaNfcl();
		cmConDiaNfcl.setComp_id(new TabelaIntegracaoCmConDiaNfclPK(ultimoSequencialConslDiaNfcl, codSeqItemOrigem));
		cmConDiaNfcl.setCodigoSituacaoTributariaPis(codigoSituacaoTributariaPis);
		cmConDiaNfcl.setCodigoSituacaoTributariaConfins(codigoSituacaoTributariaConfins);
		cmConDiaNfcl.setCodigoContribuicaoSocialApuradaPis(codigoContribuicaoSocialApuradaPis);
		cmConDiaNfcl.setCodigoContribuicaoSocialApuradaConfins(codigoContribuicaoSocialApuradaConfins);
		cmConDiaNfcl.setCodigoContaPisOrigem(null);
		cmConDiaNfcl.setCodigoContaConfinsOrigem(null);
		cmConDiaNfcl.setValorItem(valorItem);
		cmConDiaNfcl.setValorBasePis(valorItem);
		cmConDiaNfcl.setPercentualAlicotaPis(isModelo01 ? aliquotaPis : BigDecimal.ZERO);
		cmConDiaNfcl.setValorPis(isModelo01 ? valorItem.multiply(aliquotaPis).divide(base_100) : BigDecimal.ZERO);
		cmConDiaNfcl.setValorBaseConfins(valorItem);
		cmConDiaNfcl.setPercentualAlicotaConfins(isModelo01 ? aliquotaCofins : BigDecimal.ZERO);
		cmConDiaNfcl.setValorConfins(isModelo01 ? valorItem.multiply(aliquotaCofins).divide(base_100) : BigDecimal.ZERO);
		cmConDiaNfcl.setCodigoTabelaNaturezaReceitaPis(null);
		cmConDiaNfcl.setCodigoNaturezaReceitaPis(null);
		cmConDiaNfcl.setCodigoTabelaNaturezaReceitaConfins(null);
		cmConDiaNfcl.setCodigoNaturezaReceitaConfins(null);
		cmConDiaNfcl.setDescricaoErroRegistro(null);
		return cmConDiaNfcl;
	}

	public void acumularCancelamentoDocumentoJuros(Integer classeConsumo, BigDecimal valorJurosCorrecao){

		AcumuladorValoresSpedPisCofins acumulador = getAcumulador(classeConsumo);
		acumulador.addQtdCanDoctoJuros(1);
		acumulador.addValCanDoctoJuros(valorJurosCorrecao);
		addAcumulador(classeConsumo, acumulador);
	}
}
