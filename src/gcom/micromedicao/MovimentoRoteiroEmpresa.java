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

package gcom.micromedicao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.LocalEntregaDocumento;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidadeConsumo;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class MovimentoRoteiroEmpresa
				implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;// 1

	private Integer anoMesMovimento;// 2

	private Integer codigoSetorComercial;// 3

	private Integer numeroQuadra;// 4

	private Short numeroLoteImovel;// 5

	private Short numeroSubLoteImovel;// 6

	private String numeroInscricao;// 7

	private String nomeCliente;// 8

	private String enderecoImovel;// 9

	private String complementoEnderecoImovel;// 10

	private String cepEnderecoImovel;// 11

	private String bairroEnderecoImovel;// 12

	private String municipioEnderecoImovel;// 13

	private String zonaTerritorial;// 14

	private Short quantidadeEconomiasResidencial;// 15

	private Short quantidadeEconomiasComercial;// 16

	private Short quantidadeEconomiasIndustrial;// 17

	private Short quantidadeEconomiasPublica;// 18

	private Short indicadorImpostoFederal;// 19

	private Short indicadorAtualizarLeitura;// 20

	private Short indicadorPoco;// 21

	private Short indicadorBaixaRenda;// 22

	private Short indicadorGrandeCliente;// 23

	private Short indicadorDebitoAutomatico;// 24

	private Short indicadorIsencaoAgua;// 25

	private Short indicadorIsencaoEsgoto;// 26

	private Short indicadorLeituraParalisacao;// 27

	private Short indicadorDescontoAltoConsumo;// 28

	private String numeroHidrometro;// 29

	private Short numeroDigitosLeitura;// 30

	private Date dataInstalacaoHidrometro;// 31

	private String localInstalacaoHidrometro;// 32

	private Integer numeroLeituraAnterior;// 33

	private Date dataLeituraAnterior;// 34

	private Integer numeroConsumoMedio;// 35

	private Integer numeroConsumoMinimo;// 36

	private Integer numeroConsumoAnterior;// 37

	private Integer numeroConsumoCredito;// 38

	private Integer numeroConsumoFixoAgua;// 39

	private Integer numeroConsumoFixoEsgoto;// 40

	private BigDecimal percentualEsgoto;// 41

	private Date dataVencimento;// 42

	private Short indicadorEmissao;// 43

	private String indicadorEmissaoCampo;// 44

	private Integer numeroDocumentoCobranca;// 45

	// private BigDecimal valorCredito;// 46

	private String descricaoRubrica1;// 47

	private Integer referenciaRubrica1;// 48

	private BigDecimal valorRubrica1;// 49

	private String descricaoRubrica2;// 50

	private Integer referenciaRubrica2;// 51

	private BigDecimal valorRubrica2;// 52

	private String descricaoRubrica3;// 53

	private Integer referenciaRubrica3;// 54

	private BigDecimal valorRubrica3;// 55

	private String descricaoRubrica4;// 56

	private Integer referenciaRubrica4;// 57

	private BigDecimal valorRubrica4;// 58

	private String descricaoRubrica5;// 59

	private Integer referenciaRubrica5;// 60

	private BigDecimal valorRubrica5;// 61

	private String descricaoRubrica6;// 62

	private Integer referenciaRubrica6;// 63

	private BigDecimal valorRubrica6;// 64

	private String descricaoRubrica7;// 65

	private Integer referenciaRubrica7;// 66

	private BigDecimal valorRubrica7;// 67

	private String descricaoRubrica8;// 68

	private Integer referenciaRubrica8;// 69

	private BigDecimal valorRubrica8;// 70

	private String descricaoRubrica9;// 71

	private Integer referenciaRubrica9;// 72

	private BigDecimal valorRubrica9;// 73

	private String descricaoRubrica10;// 74

	private Integer referenciaRubrica10;// 75

	private BigDecimal valorRubrica10;// 76

	private String descricaoRubrica11;// 77

	private Integer referenciaRubrica11;// 78

	private BigDecimal valorRubrica11;// 79

	private String descricaoRubrica12;// 80

	private Integer referenciaRubrica12;// 81

	private BigDecimal valorRubrica12;// 82

	private String descricaoRubrica13;// 83

	private Integer referenciaRubrica13;// 84

	private BigDecimal valorRubrica13;// 85

	private String descricaoRubrica14;// 86

	private Integer referenciaRubrica14;// 87

	private BigDecimal valorRubrica14;// 88

	private String descricaoRubrica15;// 89

	private Integer referenciaRubrica15;// 90

	private BigDecimal valorRubrica15;// 91

	private Integer referenciaConsumo1;// 92

	private Integer numeroConsumo1;// 93

	private Integer idAnormalidadeLeitura1;// 94

	private Integer referenciaConsumo2;// 95

	private Integer numeroConsumo2;// 96

	private Integer idAnormalidadeLeitura2;// 97

	private Integer referenciaConsumo3;// 98

	private Integer numeroConsumo3;// 99

	private Integer idAnormalidadeLeitura3;// 100

	private Integer referenciaConsumo4;// 101

	private Integer numeroConsumo4;// 102

	private Integer idAnormalidadeLeitura4;// 103

	private Integer referenciaConsumo5;// 104

	private Integer numeroConsumo5;// 105

	private Integer idAnormalidadeLeitura5;// 106

	private Integer referenciaConsumo6;// 107

	private Integer numeroConsumo6;// 108

	private Integer idAnormalidadeLeitura6;// 109

	private Integer referenciaConsumo7;// 110

	private Integer numeroConsumo7;// 111

	private Integer idAnormalidadeLeitura7;// 112

	private Integer referenciaConsumo8;// 113

	private Integer numeroConsumo8;// 114

	private Integer idAnormalidadeLeitura8;// 115

	private Integer referenciaConsumo9;// 116

	private Integer numeroConsumo9;// 117

	private Integer idAnormalidadeLeitura9;// 118

	private Integer referenciaConsumo10;// 119

	private Integer numeroConsumo10;// 120

	private Integer idAnormalidadeLeitura10;// 121

	private Integer referenciaConsumo11;// 122

	private Integer numeroConsumo11;// 123

	private Integer idAnormalidadeLeitura11;// 124

	private Integer referenciaConsumo12;// 125

	private Integer numeroConsumo12;// 126

	private Integer idAnormalidadeLeitura12;// 127

	private Date dataLeitura;// 128

	private Integer numeroLeitura;// 129

	private Integer numeroLeituraFaturada;// 130

	private Integer numeroConsumoMedido;// 131

	private Integer numeroConsumoCreditoFaturado;// 132

	private Integer numeroConsumoFaturadoAgua;// 133

	private Integer numeroConsumoFaturadoEsgoto;// 134

	private Short numeroDiasConsumo;// 135

	private BigDecimal valorAgua;// 136

	private BigDecimal valorEsgoto;// 137

	private BigDecimal valorDebitos;// 138

	private BigDecimal valorCreditos;// 139

	private BigDecimal valorImpostoFederal;// 140

	private BigDecimal valorTotal;// 141

	private Date dataEmissao;// 142

	private Short indicadorFotoRegistrada;// 143

	private Short indicadorConfirmacaoLeitura;// 144

	private Short indicadorFase;// 145

	private Date dataProgramacaoLeitura;// 146

	private Integer idUsuarioGeracao;// 147

	private Date tempoGeracao;// 148

	private Date tempoDownload;// 149

	private Integer idUsuarioRemanejamento;// 150

	private Date tempoRemanejamento;// 151

	private Short indicadorGeracaoArquivoTexto;// 152

	private Integer idUsuarioGeracaoArquivoTexto;// 153

	private Date tempoGeracaoArquivoTexto;// 154

	private Short indicadorEnvioArquivoTexto;// 155

	private Integer idUsuarioEnvioArquivoTexto;// 156

	private Date tempoEnvioArquivoTexto;// 157

	private Short codigoRota;// 158

	private Rota rota;// 159

	private Leiturista leiturista;// 160

	private FaturamentoGrupo faturamentoGrupo;// 161

	private Empresa empresa;// 162

	private Localidade localidade;// 163

	private Imovel imovel;// 164

	private LigacaoAguaSituacao ligacaoAguaSituacao;// 165

	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;// 166

	private ConsumoTarifa consumoTarifa;// 167

	private LeituraAnormalidadeConsumo leituraAnormalidadeConsumo;// 168

	private MedicaoTipo medicaoTipo;// 169

	private LeituraAnormalidade leituraAnormalidade;// 170

	private ConsumoAnormalidade consumoAnormalidade;// 171

	private ConsumoTipo consumoTipo;// 172

	private LocalEntregaDocumento localEntregaDocumento;// 173

	private Date ultimaAlteracao;// 174

	private Integer idLeituraSituacaoAnterior;// 175

	private Integer idAnormalidadeConsumo1;// 176

	private Integer idAnormalidadeConsumo2;// 177

	private Integer idAnormalidadeConsumo3;// 178

	private Integer idAnormalidadeConsumo4;// 179

	private Integer idAnormalidadeConsumo5;// 180

	private Integer idAnormalidadeConsumo6;// 181

	private Integer idAnormalidadeConsumo7;// 182

	private Integer idAnormalidadeConsumo8;// 183

	private Integer idAnormalidadeConsumo9;// 184

	private Integer idAnormalidadeConsumo10;// 185

	private Integer idAnormalidadeConsumo11;// 186

	private Integer idAnormalidadeConsumo12;// 187

	private Integer leituraFaturada1;// 188

	private Integer leituraFaturada2;// 189

	private Integer leituraFaturada3;// 190

	private Integer leituraFaturada4;// 191

	private Integer leituraFaturada5;// 192

	private Integer leituraFaturada6;// 193

	private Integer leituraFaturada7;// 194

	private Integer leituraFaturada8;// 195

	private Integer leituraFaturada9;// 196

	private Integer leituraFaturada10;// 197

	private Integer leituraFaturada11;// 198

	private Integer leituraFaturada12;// 199

	private Short numeroPrestacaoRubrica1;// 200

	private Short numeroPrestacaoCobradaRubrica1;// 201

	private Short numeroPrestacaoRubrica2;// 202

	private Short numeroPrestacaoCobradaRubrica2;// 203

	private Short numeroPrestacaoRubrica3;// 204

	private Short numeroPrestacaoCobradaRubrica3;// 205

	private Short numeroPrestacaoRubrica4;// 206

	private Short numeroPrestacaoCobradaRubrica4;// 207

	private Short numeroPrestacaoRubrica5;// 208

	private Short numeroPrestacaoCobradaRubrica5;// 209

	private Short numeroPrestacaoRubrica6;// 210

	private Short numeroPrestacaoCobradaRubrica6;// 211

	private Short numeroPrestacaoRubrica7;// 212

	private Short numeroPrestacaoCobradaRubrica7;// 213

	private Short numeroPrestacaoRubrica8;// 214

	private Short numeroPrestacaoCobradaRubrica8;// 215

	private Short numeroPrestacaoRubrica9;// 216

	private Short numeroPrestacaoCobradaRubrica9;// 217

	private Short numeroPrestacaoRubrica10;// 218

	private Short numeroPrestacaoCobradaRubrica10;// 219

	private Short numeroPrestacaoRubrica11;// 220

	private Short numeroPrestacaoCobradaRubrica11;// 221

	private Short numeroPrestacaoRubrica12;// 222

	private Short numeroPrestacaoCobradaRubrica12;// 223

	private Short numeroPrestacaoRubrica13;// 224

	private Short numeroPrestacaoCobradaRubrica13;// 225

	private Short numeroPrestacaoRubrica14;// 226

	private Short numeroPrestacaoCobradaRubrica14;// 227

	private Short numeroPrestacaoRubrica15;// 228

	private Short numeroPrestacaoCobradaRubrica15;// 229

	private Short indicadorSituacaoCorte;// 230

	private Date dataProcessamento;// 231

	private Date dataVencimentoRetorno;// 232

	private Date dataLeituraFaturada;// 233

	private BigDecimal valorRateio;// 234

	private Short indicadorModoFaturarRateio;// 235

	private Integer numeroConsumoRateio;// 236

	private Short numeroPrestacaoCredito1;// 237

	private Short numeroPrestacaoCobradaCredito1;// 238

	private String descricaoCredito1;// 239

	private Integer referenciaCredito1;// 240

	private BigDecimal valorCredito1;// 240

	private Short numeroPrestacaoCredito2;// 240

	private Short numeroPrestacaoCobradaCredito2;// 240

	private String descricaoCredito2;// 240

	private Integer referenciaCredito2;// 240

	private BigDecimal valorCredito2;// 240

	private Short numeroPrestacaoCredito3;// 240

	private Short numeroPrestacaoCobradaCredito3;// 240

	private String descricaoCredito3;// 240

	private Integer referenciaCredito3;// 250

	private BigDecimal valorCredito3;// 250

	private Short numeroPrestacaoCredito4;// 250

	private Short numeroPrestacaoCobradaCredito4;// 250

	private String descricaoCredito4;// 250

	private Integer referenciaCredito4;// 250

	private BigDecimal valorCredito4;// 250

	private Short numeroPrestacaoCredito5;// 250

	private Short numeroPrestacaoCobradaCredito5;// 250

	private String descricaoCredito5;// 250

	private Integer referenciaCredito5;// 260

	private BigDecimal valorCredito5;// 260

	private BigDecimal valorCreditado1;// 260

	private BigDecimal valorCreditado2;// 260

	private BigDecimal valorCreditado3;// 260

	private BigDecimal valorCreditado4;// 260

	private BigDecimal valorCreditado5;// 260

	private BigDecimal valorReligacao;// 260

	private BigDecimal valorSancao;// 260

	private Integer idServicoReligacao;// 260

	private Integer idServicoSancao;// 270

	private Short indicadorReligacaoAgua;// 271

	private String numeroImovelAlteracao;// 272

	private Short indicadorStatusRegistSistLegado;// 273

	private Short indicadorFaturaRetida;// 274

	private String numeroImovel;// 275

	private String numeroHidrometroAlteracao;// 276

	private String descricaoComplementoEnderecoAlteracao;// 277

	private Short quantidadeEconomiaResidencialAlteracao;// 278

	private Short quantidadeEconomiaComercialAlteracao;// 279

	private Short quantidadeEconomiaIndustrialAlteracao;// 280

	private Short quantidadeEconomiaPublicaAlteracao;// 281

	private Integer numeroConsumoFixoPoco;

	private Integer idCategoriaAtualizacaoCadastral;

	private Integer idSubCategoriaAtualizacaoCadastral;

	public static final Short FASE_GERADO = Short.valueOf("4");

	public static final Short FASE_PROGRAMADO = Short.valueOf("3");

	public static final Short FASE_PROCESSADO = Short.valueOf("2");

	public static final Short FASE_LEITURA_RETORNADA = Short.valueOf("1");



	// Indicador Emissão Campo
	public static final String INDICADOR_EMISSAO_CAMPO_CONTA_IMPRESSA = "1";

	public static final String INDICADOR_EMISSAO_CAMPO_CONTA_NAO_IMPRESSA = "2";

	public static final String INDICADOR_EMISSAO_CAMPO_CONTA_RETIDA = "3";

	// Status Registro Sistema Legado (Indica imóveis não lidos)
	public static final Short INDICADOR_STATUS_REGISTRO_NAO_LIDO = Short.valueOf("5");

	// Indicador Modo Faturar Rateio
	public static final Short COBRANCA_EM_DEBITO_A_COBRAR = Short.valueOf("0");

	public static final Short COBRANCA_NA_CONTA_ATUAL = Short.valueOf("1");

	/** default constructor */
	public MovimentoRoteiroEmpresa() {

	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the anoMesMovimento
	 */
	public Integer getAnoMesMovimento(){

		return anoMesMovimento;
	}

	/**
	 * @param anoMesMovimento
	 *            the anoMesMovimento to set
	 */
	public void setAnoMesMovimento(Integer anoMesMovimento){

		this.anoMesMovimento = anoMesMovimento;
	}

	/**
	 * @return the codigoSetorComercial
	 */
	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	/**
	 * @param codigoSetorComercial
	 *            the codigoSetorComercial to set
	 */
	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	/**
	 * @return the numeroQuadra
	 */
	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	/**
	 * @param numeroQuadra
	 *            the numeroQuadra to set
	 */
	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	/**
	 * @return the numeroLoteImovel
	 */
	public Short getNumeroLoteImovel(){

		return numeroLoteImovel;
	}

	/**
	 * @param numeroLoteImovel
	 *            the numeroLoteImovel to set
	 */
	public void setNumeroLoteImovel(Short numeroLoteImovel){

		this.numeroLoteImovel = numeroLoteImovel;
	}

	/**
	 * @return the numeroSubLoteImovel
	 */
	public Short getNumeroSubLoteImovel(){

		return numeroSubLoteImovel;
	}

	/**
	 * @param numeroSubLoteImovel
	 *            the numeroSubLoteImovel to set
	 */
	public void setNumeroSubLoteImovel(Short numeroSubLoteImovel){

		this.numeroSubLoteImovel = numeroSubLoteImovel;
	}

	/**
	 * @return the numeroInscricao
	 */
	public String getNumeroInscricao(){

		return numeroInscricao;
	}

	/**
	 * @param numeroInscricao
	 *            the numeroInscricao to set
	 */
	public void setNumeroInscricao(String numeroInscricao){

		this.numeroInscricao = numeroInscricao;
	}

	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente(){

		return nomeCliente;
	}

	/**
	 * @param nomeCliente
	 *            the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	/**
	 * @return the enderecoImovel
	 */
	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	/**
	 * @param enderecoImovel
	 *            the enderecoImovel to set
	 */
	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	/**
	 * @return the complementoEnderecoImovel
	 */
	public String getComplementoEnderecoImovel(){

		return complementoEnderecoImovel;
	}

	/**
	 * @param complementoEnderecoImovel
	 *            the complementoEnderecoImovel to set
	 */
	public void setComplementoEnderecoImovel(String complementoEnderecoImovel){

		this.complementoEnderecoImovel = complementoEnderecoImovel;
	}

	/**
	 * @return the cepEnderecoImovel
	 */
	public String getCepEnderecoImovel(){

		return cepEnderecoImovel;
	}

	/**
	 * @param cepEnderecoImovel
	 *            the cepEnderecoImovel to set
	 */
	public void setCepEnderecoImovel(String cepEnderecoImovel){

		this.cepEnderecoImovel = cepEnderecoImovel;
	}

	/**
	 * @return the bairroEnderecoImovel
	 */
	public String getBairroEnderecoImovel(){

		return bairroEnderecoImovel;
	}

	/**
	 * @param bairroEnderecoImovel
	 *            the bairroEnderecoImovel to set
	 */
	public void setBairroEnderecoImovel(String bairroEnderecoImovel){

		this.bairroEnderecoImovel = bairroEnderecoImovel;
	}

	/**
	 * @return the municipioEnderecoImovel
	 */
	public String getMunicipioEnderecoImovel(){

		return municipioEnderecoImovel;
	}

	/**
	 * @param municipioEnderecoImovel
	 *            the municipioEnderecoImovel to set
	 */
	public void setMunicipioEnderecoImovel(String municipioEnderecoImovel){

		this.municipioEnderecoImovel = municipioEnderecoImovel;
	}

	/**
	 * @return the zonaTerritorial
	 */
	public String getZonaTerritorial(){

		return zonaTerritorial;
	}

	/**
	 * @param zonaTerritorial
	 *            the zonaTerritorial to set
	 */
	public void setZonaTerritorial(String zonaTerritorial){

		this.zonaTerritorial = zonaTerritorial;
	}

	/**
	 * @return the quantidadeEconomiasResidencial
	 */
	public Short getQuantidadeEconomiasResidencial(){

		return quantidadeEconomiasResidencial;
	}

	/**
	 * @param quantidadeEconomiasResidencial
	 *            the quantidadeEconomiasResidencial to set
	 */
	public void setQuantidadeEconomiasResidencial(Short quantidadeEconomiasResidencial){

		this.quantidadeEconomiasResidencial = quantidadeEconomiasResidencial;
	}

	/**
	 * @return the quantidadeEconomiasComercial
	 */
	public Short getQuantidadeEconomiasComercial(){

		return quantidadeEconomiasComercial;
	}

	/**
	 * @param quantidadeEconomiasComercial
	 *            the quantidadeEconomiasComercial to set
	 */
	public void setQuantidadeEconomiasComercial(Short quantidadeEconomiasComercial){

		this.quantidadeEconomiasComercial = quantidadeEconomiasComercial;
	}

	/**
	 * @return the quantidadeEconomiasIndustrial
	 */
	public Short getQuantidadeEconomiasIndustrial(){

		return quantidadeEconomiasIndustrial;
	}

	/**
	 * @param quantidadeEconomiasIndustrial
	 *            the quantidadeEconomiasIndustrial to set
	 */
	public void setQuantidadeEconomiasIndustrial(Short quantidadeEconomiasIndustrial){

		this.quantidadeEconomiasIndustrial = quantidadeEconomiasIndustrial;
	}

	/**
	 * @return the quantidadeEconomiasPublica
	 */
	public Short getQuantidadeEconomiasPublica(){

		return quantidadeEconomiasPublica;
	}

	/**
	 * @param quantidadeEconomiasPublica
	 *            the quantidadeEconomiasPublica to set
	 */
	public void setQuantidadeEconomiasPublica(Short quantidadeEconomiasPublica){

		this.quantidadeEconomiasPublica = quantidadeEconomiasPublica;
	}

	/**
	 * @return the indicadorImpostoFederal
	 */
	public Short getIndicadorImpostoFederal(){

		return indicadorImpostoFederal;
	}

	/**
	 * @param indicadorPessoaFisica
	 *            the indicadorImpostoFederal to set
	 */
	public void setIndicadorImpostoFederal(Short indicadorImpostoFederal){

		this.indicadorImpostoFederal = indicadorImpostoFederal;
	}

	/**
	 * @return the indicadorPoco
	 */
	public Short getIndicadorPoco(){

		return indicadorPoco;
	}

	/**
	 * @param indicadorPoco
	 *            the indicadorPoco to set
	 */
	public void setIndicadorPoco(Short indicadorPoco){

		this.indicadorPoco = indicadorPoco;
	}

	/**
	 * @return the indicadorBaixaRenda
	 */
	public Short getIndicadorBaixaRenda(){

		return indicadorBaixaRenda;
	}

	/**
	 * @param indicadorBaixaRenda
	 *            the indicadorBaixaRenda to set
	 */
	public void setIndicadorBaixaRenda(Short indicadorBaixaRenda){

		this.indicadorBaixaRenda = indicadorBaixaRenda;
	}

	/**
	 * @return the indicadorGrandeCliente
	 */
	public Short getIndicadorGrandeCliente(){

		return indicadorGrandeCliente;
	}

	/**
	 * @param indicadorGrandeCliente
	 *            the indicadorGrandeCliente to set
	 */
	public void setIndicadorGrandeCliente(Short indicadorGrandeCliente){

		this.indicadorGrandeCliente = indicadorGrandeCliente;
	}

	/**
	 * @return the indicadorDebitoAutomatico
	 */
	public Short getIndicadorDebitoAutomatico(){

		return indicadorDebitoAutomatico;
	}

	/**
	 * @param indicadorDebitoAutomatico
	 *            the indicadorDebitoAutomatico to set
	 */
	public void setIndicadorDebitoAutomatico(Short indicadorDebitoAutomatico){

		this.indicadorDebitoAutomatico = indicadorDebitoAutomatico;
	}

	/**
	 * @return the indicadorIsencaoAgua
	 */
	public Short getIndicadorIsencaoAgua(){

		return indicadorIsencaoAgua;
	}

	/**
	 * @param indicadorIsencaoAgua
	 *            the indicadorIsencaoAgua to set
	 */
	public void setIndicadorIsencaoAgua(Short indicadorIsencaoAgua){

		this.indicadorIsencaoAgua = indicadorIsencaoAgua;
	}

	/**
	 * @return the indicadorIsencaoEsgoto
	 */
	public Short getIndicadorIsencaoEsgoto(){

		return indicadorIsencaoEsgoto;
	}

	/**
	 * @param indicadorIsencaoEsgoto
	 *            the indicadorIsencaoEsgoto to set
	 */
	public void setIndicadorIsencaoEsgoto(Short indicadorIsencaoEsgoto){

		this.indicadorIsencaoEsgoto = indicadorIsencaoEsgoto;
	}

	/**
	 * @return the indicadorLeituraParalisacao
	 */
	public Short getIndicadorLeituraParalisacao(){

		return indicadorLeituraParalisacao;
	}

	/**
	 * @param indicadorLeituraParalisacao
	 *            the indicadorLeituraParalisacao to set
	 */
	public void setIndicadorLeituraParalisacao(Short indicadorLeituraParalisacao){

		this.indicadorLeituraParalisacao = indicadorLeituraParalisacao;
	}

	/**
	 * @return the indicadorDescontoAltoConsumo
	 */
	public Short getIndicadorDescontoAltoConsumo(){

		return indicadorDescontoAltoConsumo;
	}

	/**
	 * @param indicadorDescontoAltoConsumo
	 *            the indicadorDescontoAltoConsumo to set
	 */
	public void setIndicadorDescontoAltoConsumo(Short indicadorDescontoAltoConsumo){

		this.indicadorDescontoAltoConsumo = indicadorDescontoAltoConsumo;
	}

	/**
	 * @return the numeroHidrometro
	 */
	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	/**
	 * @param numeroHidrometro
	 *            the numeroHidrometro to set
	 */
	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	/**
	 * @return the numeroDigitosLeitura
	 */
	public Short getNumeroDigitosLeitura(){

		return numeroDigitosLeitura;
	}

	/**
	 * @param numeroDigitosLeitura
	 *            the numeroDigitosLeitura to set
	 */
	public void setNumeroDigitosLeitura(Short numeroDigitosLeitura){

		this.numeroDigitosLeitura = numeroDigitosLeitura;
	}

	/**
	 * @return the dataInstalacaoHidrometro
	 */
	public Date getDataInstalacaoHidrometro(){

		return dataInstalacaoHidrometro;
	}

	/**
	 * @param dataInstalacaoHidrometro
	 *            the dataInstalacaoHidrometro to set
	 */
	public void setDataInstalacaoHidrometro(Date dataInstalacaoHidrometro){

		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}

	/**
	 * @return the localInstalacaoHidrometro
	 */
	public String getLocalInstalacaoHidrometro(){

		return localInstalacaoHidrometro;
	}

	/**
	 * @param localInstalacaoHidrometro
	 *            the localInstalacaoHidrometro to set
	 */
	public void setLocalInstalacaoHidrometro(String localInstalacaoHidrometro){

		this.localInstalacaoHidrometro = localInstalacaoHidrometro;
	}

	/**
	 * @return the numeroLeituraAnterior
	 */
	public Integer getNumeroLeituraAnterior(){

		return numeroLeituraAnterior;
	}

	/**
	 * @param numeroLeituraAnterior
	 *            the numeroLeituraAnterior to set
	 */
	public void setNumeroLeituraAnterior(Integer numeroLeituraAnterior){

		this.numeroLeituraAnterior = numeroLeituraAnterior;
	}

	/**
	 * @return the dataLeituraAnterior
	 */
	public Date getDataLeituraAnterior(){

		return dataLeituraAnterior;
	}

	/**
	 * @param dataLeituraAnterior
	 *            the dataLeituraAnterior to set
	 */
	public void setDataLeituraAnterior(Date dataLeituraAnterior){

		this.dataLeituraAnterior = dataLeituraAnterior;
	}

	/**
	 * @return the numeroConsumoMedio
	 */
	public Integer getNumeroConsumoMedio(){

		return numeroConsumoMedio;
	}

	/**
	 * @param numeroConsumoMedio
	 *            the numeroConsumoMedio to set
	 */
	public void setNumeroConsumoMedio(Integer numeroConsumoMedio){

		this.numeroConsumoMedio = numeroConsumoMedio;
	}

	/**
	 * @return the numeroConsumoMinimo
	 */
	public Integer getNumeroConsumoMinimo(){

		return numeroConsumoMinimo;
	}

	/**
	 * @param numeroConsumoMinimo
	 *            the numeroConsumoMinimo to set
	 */
	public void setNumeroConsumoMinimo(Integer numeroConsumoMinimo){

		this.numeroConsumoMinimo = numeroConsumoMinimo;
	}

	/**
	 * @return the numeroConsumoAnterior
	 */
	public Integer getNumeroConsumoAnterior(){

		return numeroConsumoAnterior;
	}

	/**
	 * @param numeroConsumoAnterior
	 *            the numeroConsumoAnterior to set
	 */
	public void setNumeroConsumoAnterior(Integer numeroConsumoAnterior){

		this.numeroConsumoAnterior = numeroConsumoAnterior;
	}

	/**
	 * @return the numeroConsumoCredito
	 */
	public Integer getNumeroConsumoCredito(){

		return numeroConsumoCredito;
	}

	/**
	 * @param numeroConsumoCredito
	 *            the numeroConsumoCredito to set
	 */
	public void setNumeroConsumoCredito(Integer numeroConsumoCredito){

		this.numeroConsumoCredito = numeroConsumoCredito;
	}

	/**
	 * @return the numeroConsumoFixoAgua
	 */
	public Integer getNumeroConsumoFixoAgua(){

		return numeroConsumoFixoAgua;
	}

	/**
	 * @param numeroConsumoFixoAgua
	 *            the numeroConsumoFixoAgua to set
	 */
	public void setNumeroConsumoFixoAgua(Integer numeroConsumoFixoAgua){

		this.numeroConsumoFixoAgua = numeroConsumoFixoAgua;
	}

	/**
	 * @return the numeroConsumoFixoEsgoto
	 */
	public Integer getNumeroConsumoFixoEsgoto(){

		return numeroConsumoFixoEsgoto;
	}

	/**
	 * @param numeroConsumoFixoEsgoto
	 *            the numeroConsumoFixoEsgoto to set
	 */
	public void setNumeroConsumoFixoEsgoto(Integer numeroConsumoFixoEsgoto){

		this.numeroConsumoFixoEsgoto = numeroConsumoFixoEsgoto;
	}

	/**
	 * @return the percentualEsgoto
	 */
	public BigDecimal getPercentualEsgoto(){

		return percentualEsgoto;
	}

	/**
	 * @param percentualEsgoto
	 *            the percentualEsgoto to set
	 */
	public void setPercentualEsgoto(BigDecimal percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	/**
	 * @return the dataVencimento
	 */
	public Date getDataVencimento(){

		return dataVencimento;
	}

	/**
	 * @param dataVencimento
	 *            the dataVencimento to set
	 */
	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	/**
	 * @return the numeroDocumentoCobranca
	 */
	public Integer getNumeroDocumentoCobranca(){

		return numeroDocumentoCobranca;
	}

	/**
	 * @param numeroDocumentoCobranca
	 *            the numeroDocumentoCobranca to set
	 */
	public void setNumeroDocumentoCobranca(Integer numeroDocumentoCobranca){

		this.numeroDocumentoCobranca = numeroDocumentoCobranca;
	}

	/**
	 * @return the descricaoRubrica1
	 */
	public String getDescricaoRubrica1(){

		return descricaoRubrica1;
	}

	/**
	 * @param descricaoRubrica1
	 *            the descricaoRubrica1 to set
	 */
	public void setDescricaoRubrica1(String descricaoRubrica1){

		this.descricaoRubrica1 = descricaoRubrica1;
	}

	/**
	 * @return the valorRubrica1
	 */
	public BigDecimal getValorRubrica1(){

		return valorRubrica1;
	}

	/**
	 * @param valorRubrica1
	 *            the valorRubrica1 to set
	 */
	public void setValorRubrica1(BigDecimal valorRubrica1){

		this.valorRubrica1 = valorRubrica1;
	}

	/**
	 * @return the descricaoRubrica2
	 */
	public String getDescricaoRubrica2(){

		return descricaoRubrica2;
	}

	/**
	 * @param descricaoRubrica2
	 *            the descricaoRubrica2 to set
	 */
	public void setDescricaoRubrica2(String descricaoRubrica2){

		this.descricaoRubrica2 = descricaoRubrica2;
	}

	/**
	 * @return the valorRubrica2
	 */
	public BigDecimal getValorRubrica2(){

		return valorRubrica2;
	}

	/**
	 * @param valorRubrica2
	 *            the valorRubrica2 to set
	 */
	public void setValorRubrica2(BigDecimal valorRubrica2){

		this.valorRubrica2 = valorRubrica2;
	}

	/**
	 * @return the descricaoRubrica3
	 */
	public String getDescricaoRubrica3(){

		return descricaoRubrica3;
	}

	/**
	 * @param descricaoRubrica3
	 *            the descricaoRubrica3 to set
	 */
	public void setDescricaoRubrica3(String descricaoRubrica3){

		this.descricaoRubrica3 = descricaoRubrica3;
	}

	/**
	 * @return the valorRubrica3
	 */
	public BigDecimal getValorRubrica3(){

		return valorRubrica3;
	}

	/**
	 * @param valorRubrica3
	 *            the valorRubrica3 to set
	 */
	public void setValorRubrica3(BigDecimal valorRubrica3){

		this.valorRubrica3 = valorRubrica3;
	}

	/**
	 * @return the descricaoRubrica4
	 */
	public String getDescricaoRubrica4(){

		return descricaoRubrica4;
	}

	/**
	 * @param descricaoRubrica4
	 *            the descricaoRubrica4 to set
	 */
	public void setDescricaoRubrica4(String descricaoRubrica4){

		this.descricaoRubrica4 = descricaoRubrica4;
	}

	/**
	 * @return the valorRubrica4
	 */
	public BigDecimal getValorRubrica4(){

		return valorRubrica4;
	}

	/**
	 * @param valorRubrica4
	 *            the valorRubrica4 to set
	 */
	public void setValorRubrica4(BigDecimal valorRubrica4){

		this.valorRubrica4 = valorRubrica4;
	}

	/**
	 * @return the descricaoRubrica5
	 */
	public String getDescricaoRubrica5(){

		return descricaoRubrica5;
	}

	/**
	 * @param descricaoRubrica5
	 *            the descricaoRubrica5 to set
	 */
	public void setDescricaoRubrica5(String descricaoRubrica5){

		this.descricaoRubrica5 = descricaoRubrica5;
	}

	/**
	 * @return the valorRubrica5
	 */
	public BigDecimal getValorRubrica5(){

		return valorRubrica5;
	}

	/**
	 * @param valorRubrica5
	 *            the valorRubrica5 to set
	 */
	public void setValorRubrica5(BigDecimal valorRubrica5){

		this.valorRubrica5 = valorRubrica5;
	}

	/**
	 * @return the descricaoRubrica6
	 */
	public String getDescricaoRubrica6(){

		return descricaoRubrica6;
	}

	/**
	 * @param descricaoRubrica6
	 *            the descricaoRubrica6 to set
	 */
	public void setDescricaoRubrica6(String descricaoRubrica6){

		this.descricaoRubrica6 = descricaoRubrica6;
	}

	/**
	 * @return the valorRubrica6
	 */
	public BigDecimal getValorRubrica6(){

		return valorRubrica6;
	}

	/**
	 * @param valorRubrica6
	 *            the valorRubrica6 to set
	 */
	public void setValorRubrica6(BigDecimal valorRubrica6){

		this.valorRubrica6 = valorRubrica6;
	}

	/**
	 * @return the descricaoRubrica7
	 */
	public String getDescricaoRubrica7(){

		return descricaoRubrica7;
	}

	/**
	 * @param descricaoRubrica7
	 *            the descricaoRubrica7 to set
	 */
	public void setDescricaoRubrica7(String descricaoRubrica7){

		this.descricaoRubrica7 = descricaoRubrica7;
	}

	/**
	 * @return the valorRubrica7
	 */
	public BigDecimal getValorRubrica7(){

		return valorRubrica7;
	}

	/**
	 * @param valorRubrica7
	 *            the valorRubrica7 to set
	 */
	public void setValorRubrica7(BigDecimal valorRubrica7){

		this.valorRubrica7 = valorRubrica7;
	}

	/**
	 * @return the descricaoRubrica8
	 */
	public String getDescricaoRubrica8(){

		return descricaoRubrica8;
	}

	/**
	 * @param descricaoRubrica8
	 *            the descricaoRubrica8 to set
	 */
	public void setDescricaoRubrica8(String descricaoRubrica8){

		this.descricaoRubrica8 = descricaoRubrica8;
	}

	/**
	 * @return the valorRubrica8
	 */
	public BigDecimal getValorRubrica8(){

		return valorRubrica8;
	}

	/**
	 * @param valorRubrica8
	 *            the valorRubrica8 to set
	 */
	public void setValorRubrica8(BigDecimal valorRubrica8){

		this.valorRubrica8 = valorRubrica8;
	}

	/**
	 * @return the descricaoRubrica9
	 */
	public String getDescricaoRubrica9(){

		return descricaoRubrica9;
	}

	/**
	 * @param descricaoRubrica9
	 *            the descricaoRubrica9 to set
	 */
	public void setDescricaoRubrica9(String descricaoRubrica9){

		this.descricaoRubrica9 = descricaoRubrica9;
	}

	/**
	 * @return the valorRubrica9
	 */
	public BigDecimal getValorRubrica9(){

		return valorRubrica9;
	}

	/**
	 * @param valorRubrica9
	 *            the valorRubrica9 to set
	 */
	public void setValorRubrica9(BigDecimal valorRubrica9){

		this.valorRubrica9 = valorRubrica9;
	}

	/**
	 * @return the descricaoRubrica10
	 */
	public String getDescricaoRubrica10(){

		return descricaoRubrica10;
	}

	/**
	 * @param descricaoRubrica10
	 *            the descricaoRubrica10 to set
	 */
	public void setDescricaoRubrica10(String descricaoRubrica10){

		this.descricaoRubrica10 = descricaoRubrica10;
	}

	/**
	 * @return the valorRubrica10
	 */
	public BigDecimal getValorRubrica10(){

		return valorRubrica10;
	}

	/**
	 * @param valorRubrica10
	 *            the valorRubrica10 to set
	 */
	public void setValorRubrica10(BigDecimal valorRubrica10){

		this.valorRubrica10 = valorRubrica10;
	}

	/**
	 * @return the descricaoRubrica11
	 */
	public String getDescricaoRubrica11(){

		return descricaoRubrica11;
	}

	/**
	 * @param descricaoRubrica11
	 *            the descricaoRubrica11 to set
	 */
	public void setDescricaoRubrica11(String descricaoRubrica11){

		this.descricaoRubrica11 = descricaoRubrica11;
	}

	/**
	 * @return the valorRubrica11
	 */
	public BigDecimal getValorRubrica11(){

		return valorRubrica11;
	}

	/**
	 * @param valorRubrica11
	 *            the valorRubrica11 to set
	 */
	public void setValorRubrica11(BigDecimal valorRubrica11){

		this.valorRubrica11 = valorRubrica11;
	}

	/**
	 * @return the descricaoRubrica12
	 */
	public String getDescricaoRubrica12(){

		return descricaoRubrica12;
	}

	/**
	 * @param descricaoRubrica12
	 *            the descricaoRubrica12 to set
	 */
	public void setDescricaoRubrica12(String descricaoRubrica12){

		this.descricaoRubrica12 = descricaoRubrica12;
	}

	/**
	 * @return the valorRubrica12
	 */
	public BigDecimal getValorRubrica12(){

		return valorRubrica12;
	}

	/**
	 * @param valorRubrica12
	 *            the valorRubrica12 to set
	 */
	public void setValorRubrica12(BigDecimal valorRubrica12){

		this.valorRubrica12 = valorRubrica12;
	}

	/**
	 * @return the descricaoRubrica13
	 */
	public String getDescricaoRubrica13(){

		return descricaoRubrica13;
	}

	/**
	 * @param descricaoRubrica13
	 *            the descricaoRubrica13 to set
	 */
	public void setDescricaoRubrica13(String descricaoRubrica13){

		this.descricaoRubrica13 = descricaoRubrica13;
	}

	/**
	 * @return the valorRubrica13
	 */
	public BigDecimal getValorRubrica13(){

		return valorRubrica13;
	}

	/**
	 * @param valorRubrica13
	 *            the valorRubrica13 to set
	 */
	public void setValorRubrica13(BigDecimal valorRubrica13){

		this.valorRubrica13 = valorRubrica13;
	}

	/**
	 * @return the descricaoRubrica14
	 */
	public String getDescricaoRubrica14(){

		return descricaoRubrica14;
	}

	/**
	 * @param descricaoRubrica14
	 *            the descricaoRubrica14 to set
	 */
	public void setDescricaoRubrica14(String descricaoRubrica14){

		this.descricaoRubrica14 = descricaoRubrica14;
	}

	/**
	 * @return the valorRubrica14
	 */
	public BigDecimal getValorRubrica14(){

		return valorRubrica14;
	}

	/**
	 * @param valorRubrica14
	 *            the valorRubrica14 to set
	 */
	public void setValorRubrica14(BigDecimal valorRubrica14){

		this.valorRubrica14 = valorRubrica14;
	}

	/**
	 * @return the descricaoRubrica15
	 */
	public String getDescricaoRubrica15(){

		return descricaoRubrica15;
	}

	/**
	 * @param descricaoRubrica15
	 *            the descricaoRubrica15 to set
	 */
	public void setDescricaoRubrica15(String descricaoRubrica15){

		this.descricaoRubrica15 = descricaoRubrica15;
	}

	/**
	 * @return the valorRubrica15
	 */
	public BigDecimal getValorRubrica15(){

		return valorRubrica15;
	}

	/**
	 * @param valorRubrica15
	 *            the valorRubrica15 to set
	 */
	public void setValorRubrica15(BigDecimal valorRubrica15){

		this.valorRubrica15 = valorRubrica15;
	}

	/**
	 * @return the numeroConsumo1
	 */
	public Integer getNumeroConsumo1(){

		return numeroConsumo1;
	}

	/**
	 * @param numeroConsumo1
	 *            the numeroConsumo1 to set
	 */
	public void setNumeroConsumo1(Integer numeroConsumo1){

		this.numeroConsumo1 = numeroConsumo1;
	}

	/**
	 * @return the idAnormalidadeLeitura1
	 */
	public Integer getIdAnormalidadeLeitura1(){

		return idAnormalidadeLeitura1;
	}

	/**
	 * @param idAnormalidadeLeitura1
	 *            the idAnormalidadeLeitura1 to set
	 */
	public void setIdAnormalidadeLeitura1(Integer idAnormalidadeLeitura1){

		this.idAnormalidadeLeitura1 = idAnormalidadeLeitura1;
	}

	/**
	 * @return the numeroConsumo2
	 */
	public Integer getNumeroConsumo2(){

		return numeroConsumo2;
	}

	/**
	 * @param numeroConsumo2
	 *            the numeroConsumo2 to set
	 */
	public void setNumeroConsumo2(Integer numeroConsumo2){

		this.numeroConsumo2 = numeroConsumo2;
	}

	/**
	 * @return the idAnormalidadeLeitura2
	 */
	public Integer getIdAnormalidadeLeitura2(){

		return idAnormalidadeLeitura2;
	}

	/**
	 * @param idAnormalidadeLeitura2
	 *            the idAnormalidadeLeitura2 to set
	 */
	public void setIdAnormalidadeLeitura2(Integer idAnormalidadeLeitura2){

		this.idAnormalidadeLeitura2 = idAnormalidadeLeitura2;
	}

	/**
	 * @return the numeroConsumo3
	 */
	public Integer getNumeroConsumo3(){

		return numeroConsumo3;
	}

	/**
	 * @param numeroConsumo3
	 *            the numeroConsumo3 to set
	 */
	public void setNumeroConsumo3(Integer numeroConsumo3){

		this.numeroConsumo3 = numeroConsumo3;
	}

	/**
	 * @return the idAnormalidadeLeitura3
	 */
	public Integer getIdAnormalidadeLeitura3(){

		return idAnormalidadeLeitura3;
	}

	/**
	 * @param idAnormalidadeLeitura3
	 *            the idAnormalidadeLeitura3 to set
	 */
	public void setIdAnormalidadeLeitura3(Integer idAnormalidadeLeitura3){

		this.idAnormalidadeLeitura3 = idAnormalidadeLeitura3;
	}

	/**
	 * @return the numeroConsumo4
	 */
	public Integer getNumeroConsumo4(){

		return numeroConsumo4;
	}

	/**
	 * @param numeroConsumo4
	 *            the numeroConsumo4 to set
	 */
	public void setNumeroConsumo4(Integer numeroConsumo4){

		this.numeroConsumo4 = numeroConsumo4;
	}

	/**
	 * @return the idAnormalidadeLeitura4
	 */
	public Integer getIdAnormalidadeLeitura4(){

		return idAnormalidadeLeitura4;
	}

	/**
	 * @param idAnormalidadeLeitura4
	 *            the idAnormalidadeLeitura4 to set
	 */
	public void setIdAnormalidadeLeitura4(Integer idAnormalidadeLeitura4){

		this.idAnormalidadeLeitura4 = idAnormalidadeLeitura4;
	}

	/**
	 * @return the numeroConsumo5
	 */
	public Integer getNumeroConsumo5(){

		return numeroConsumo5;
	}

	/**
	 * @param numeroConsumo5
	 *            the numeroConsumo5 to set
	 */
	public void setNumeroConsumo5(Integer numeroConsumo5){

		this.numeroConsumo5 = numeroConsumo5;
	}

	/**
	 * @return the idAnormalidadeLeitura5
	 */
	public Integer getIdAnormalidadeLeitura5(){

		return idAnormalidadeLeitura5;
	}

	/**
	 * @param idAnormalidadeLeitura5
	 *            the idAnormalidadeLeitura5 to set
	 */
	public void setIdAnormalidadeLeitura5(Integer idAnormalidadeLeitura5){

		this.idAnormalidadeLeitura5 = idAnormalidadeLeitura5;
	}

	/**
	 * @return the numeroConsumo6
	 */
	public Integer getNumeroConsumo6(){

		return numeroConsumo6;
	}

	/**
	 * @param numeroConsumo6
	 *            the numeroConsumo6 to set
	 */
	public void setNumeroConsumo6(Integer numeroConsumo6){

		this.numeroConsumo6 = numeroConsumo6;
	}

	/**
	 * @return the idAnormalidadeLeitura6
	 */
	public Integer getIdAnormalidadeLeitura6(){

		return idAnormalidadeLeitura6;
	}

	/**
	 * @param idAnormalidadeLeitura6
	 *            the idAnormalidadeLeitura6 to set
	 */
	public void setIdAnormalidadeLeitura6(Integer idAnormalidadeLeitura6){

		this.idAnormalidadeLeitura6 = idAnormalidadeLeitura6;
	}

	/**
	 * @return the dataLeitura
	 */
	public Date getDataLeitura(){

		return dataLeitura;
	}

	/**
	 * @param dataLeitura
	 *            the tempoLeitura to set
	 */
	public void setDataLeitura(Date dataLeitura){

		this.dataLeitura = dataLeitura;
	}

	/**
	 * @return the numeroLeitura
	 */
	public Integer getNumeroLeitura(){

		return numeroLeitura;
	}

	/**
	 * @param numeroLeitura
	 *            the numeroLeitura to set
	 */
	public void setNumeroLeitura(Integer numeroLeitura){

		this.numeroLeitura = numeroLeitura;
	}

	/**
	 * @return the numeroLeituraFaturada
	 */
	public Integer getNumeroLeituraFaturada(){

		return numeroLeituraFaturada;
	}

	/**
	 * @param numeroLeituraFaturada
	 *            the numeroLeituraFaturada to set
	 */
	public void setNumeroLeituraFaturada(Integer numeroLeituraFaturada){

		this.numeroLeituraFaturada = numeroLeituraFaturada;
	}

	/**
	 * @return the numeroConsumoMedido
	 */
	public Integer getNumeroConsumoMedido(){

		return numeroConsumoMedido;
	}

	/**
	 * @param numeroConsumoMedido
	 *            the numeroConsumoMedido to set
	 */
	public void setNumeroConsumoMedido(Integer numeroConsumoMedido){

		this.numeroConsumoMedido = numeroConsumoMedido;
	}

	/**
	 * @return the numeroConsumoCreditoFaturado
	 */
	public Integer getNumeroConsumoCreditoFaturado(){

		return numeroConsumoCreditoFaturado;
	}

	/**
	 * @param numeroConsumoCreditoFaturado
	 *            the numeroConsumoCreditoFaturado to set
	 */
	public void setNumeroConsumoCreditoFaturado(Integer numeroConsumoCreditoFaturado){

		this.numeroConsumoCreditoFaturado = numeroConsumoCreditoFaturado;
	}

	/**
	 * @return the numeroConsumoFaturadoAgua
	 */
	public Integer getNumeroConsumoFaturadoAgua(){

		return numeroConsumoFaturadoAgua;
	}

	/**
	 * @param numeroConsumoFaturadoAgua
	 *            the numeroConsumoFaturadoAgua to set
	 */
	public void setNumeroConsumoFaturadoAgua(Integer numeroConsumoFaturadoAgua){

		this.numeroConsumoFaturadoAgua = numeroConsumoFaturadoAgua;
	}

	/**
	 * @return the numeroConsumoFaturadoEsgoto
	 */
	public Integer getNumeroConsumoFaturadoEsgoto(){

		return numeroConsumoFaturadoEsgoto;
	}

	/**
	 * @param numeroConsumoFaturadoEsgoto
	 *            the numeroConsumoFaturadoEsgoto to set
	 */
	public void setNumeroConsumoFaturadoEsgoto(Integer numeroConsumoFaturadoEsgoto){

		this.numeroConsumoFaturadoEsgoto = numeroConsumoFaturadoEsgoto;
	}

	/**
	 * @return the numeroDiasConsumo
	 */
	public Short getNumeroDiasConsumo(){

		return numeroDiasConsumo;
	}

	/**
	 * @param numeroDiasConsumo
	 *            the numeroDiasConsumo to set
	 */
	public void setNumeroDiasConsumo(Short numeroDiasConsumo){

		this.numeroDiasConsumo = numeroDiasConsumo;
	}

	/**
	 * @return the valorAgua
	 */
	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	/**
	 * @param valorAgua
	 *            the valorAgua to set
	 */
	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	/**
	 * @return the valorEsgoto
	 */
	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	/**
	 * @param valorEsgoto
	 *            the valorEsgoto to set
	 */
	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	/**
	 * @return the valorDebitos
	 */
	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	/**
	 * @param valorDebitos
	 *            the valorDebitos to set
	 */
	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	/**
	 * @return the valorCreditos
	 */
	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	/**
	 * @param valorCreditos
	 *            the valorCreditos to set
	 */
	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	/**
	 * @return the valorImpostoFederal
	 */
	public BigDecimal getValorImpostoFederal(){

		return valorImpostoFederal;
	}

	/**
	 * @param valorImpostoFederal
	 *            the valorImpostoFederal to set
	 */
	public void setValorImpostoFederal(BigDecimal valorImpostoFederal){

		this.valorImpostoFederal = valorImpostoFederal;
	}

	/**
	 * @return the valorTotal
	 */
	public BigDecimal getValorTotal(){

		return valorTotal;
	}

	/**
	 * @param valorTotal
	 *            the valorTotal to set
	 */
	public void setValorTotal(BigDecimal valorTotal){

		this.valorTotal = valorTotal;
	}

	/**
	 * @return the dataEmissao
	 */
	public Date getDataEmissao(){

		return dataEmissao;
	}

	/**
	 * @param dataEmissao
	 *            the dataEmissao to set
	 */
	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	/**
	 * @return the indicadorFotoRegistrada
	 */
	public Short getIndicadorFotoRegistrada(){

		return indicadorFotoRegistrada;
	}

	/**
	 * @param indicadorFotoRegistrada
	 *            the indicadorFotoRegistrada to set
	 */
	public void setIndicadorFotoRegistrada(Short indicadorFotoRegistrada){

		this.indicadorFotoRegistrada = indicadorFotoRegistrada;
	}

	/**
	 * @return the indicadorConfirmacaoLeitura
	 */
	public Short getIndicadorConfirmacaoLeitura(){

		return indicadorConfirmacaoLeitura;
	}

	/**
	 * @param indicadorConfirmacaoLeitura
	 *            the indicadorConfirmacaoLeitura to set
	 */
	public void setIndicadorConfirmacaoLeitura(Short indicadorConfirmacaoLeitura){

		this.indicadorConfirmacaoLeitura = indicadorConfirmacaoLeitura;
	}

	/**
	 * @return the indicadorFase
	 */
	public Short getIndicadorFase(){

		return indicadorFase;
	}

	/**
	 * @param indicadorFase
	 *            the indicadorFase to set
	 */
	public void setIndicadorFase(Short indicadorFase){

		this.indicadorFase = indicadorFase;
	}

	/**
	 * @return the dataProgramacaoLeitura
	 */
	public Date getDataProgramacaoLeitura(){

		return dataProgramacaoLeitura;
	}

	/**
	 * @param dataProgramacaoLeitura
	 *            the dataProgramacaoLeitura to set
	 */
	public void setDataProgramacaoLeitura(Date dataProgramacaoLeitura){

		this.dataProgramacaoLeitura = dataProgramacaoLeitura;
	}

	/**
	 * @return the idUsuarioGeracao
	 */
	public Integer getIdUsuarioGeracao(){

		return idUsuarioGeracao;
	}

	/**
	 * @param idUsuarioGeracao
	 *            the idUsuarioGeracao to set
	 */
	public void setIdUsuarioGeracao(Integer idUsuarioGeracao){

		this.idUsuarioGeracao = idUsuarioGeracao;
	}

	/**
	 * @return the tempoGeracao
	 */
	public Date getTempoGeracao(){

		return tempoGeracao;
	}

	/**
	 * @param tempoGeracao
	 *            the tempoGeracao to set
	 */
	public void setTempoGeracao(Date tempoGeracao){

		this.tempoGeracao = tempoGeracao;
	}

	/**
	 * @return the tempoDownload
	 */
	public Date getTempoDownload(){

		return tempoDownload;
	}

	/**
	 * @param tempoDownload
	 *            the tempoDownload to set
	 */
	public void setTempoDownload(Date tempoDownload){

		this.tempoDownload = tempoDownload;
	}

	/**
	 * @return the idUsuarioRemanejamento
	 */
	public Integer getIdUsuarioRemanejamento(){

		return idUsuarioRemanejamento;
	}

	/**
	 * @param idUsuarioRemanejamento
	 *            the idUsuarioRemanejamento to set
	 */
	public void setIdUsuarioRemanejamento(Integer idUsuarioRemanejamento){

		this.idUsuarioRemanejamento = idUsuarioRemanejamento;
	}

	/**
	 * @return the tempoRemanejamento
	 */
	public Date getTempoRemanejamento(){

		return tempoRemanejamento;
	}

	/**
	 * @param tempoRemanejamento
	 *            the tempoRemanejamento to set
	 */
	public void setTempoRemanejamento(Date tempoRemanejamento){

		this.tempoRemanejamento = tempoRemanejamento;
	}

	/**
	 * @return the indicadorGeracaoArquivoTexto
	 */
	public Short getIndicadorGeracaoArquivoTexto(){

		return indicadorGeracaoArquivoTexto;
	}

	/**
	 * @param indicadorGeracaoArquivoTexto
	 *            the indicadorGeracaoArquivoTexto to set
	 */
	public void setIndicadorGeracaoArquivoTexto(Short indicadorGeracaoArquivoTexto){

		this.indicadorGeracaoArquivoTexto = indicadorGeracaoArquivoTexto;
	}

	/**
	 * @return the idUsuarioGeracaoArquivoTexto
	 */
	public Integer getIdUsuarioGeracaoArquivoTexto(){

		return idUsuarioGeracaoArquivoTexto;
	}

	/**
	 * @param idUsuarioGeracaoArquivoTexto
	 *            the idUsuarioGeracaoArquivoTexto to set
	 */
	public void setIdUsuarioGeracaoArquivoTexto(Integer idUsuarioGeracaoArquivoTexto){

		this.idUsuarioGeracaoArquivoTexto = idUsuarioGeracaoArquivoTexto;
	}

	/**
	 * @return the tempoGeracaoArquivoTexto
	 */
	public Date getTempoGeracaoArquivoTexto(){

		return tempoGeracaoArquivoTexto;
	}

	/**
	 * @param tempoGeracaoArquivoTexto
	 *            the tempoGeracaoArquivoTexto to set
	 */
	public void setTempoGeracaoArquivoTexto(Date tempoGeracaoArquivoTexto){

		this.tempoGeracaoArquivoTexto = tempoGeracaoArquivoTexto;
	}

	/**
	 * @return the indicadorEnvioArquivoTexto
	 */
	public Short getIndicadorEnvioArquivoTexto(){

		return indicadorEnvioArquivoTexto;
	}

	/**
	 * @param indicadorEnvioArquivoTexto
	 *            the indicadorEnvioArquivoTexto to set
	 */
	public void setIndicadorEnvioArquivoTexto(Short indicadorEnvioArquivoTexto){

		this.indicadorEnvioArquivoTexto = indicadorEnvioArquivoTexto;
	}

	/**
	 * @return the idUsuarioEnvioArquivoTexto
	 */
	public Integer getIdUsuarioEnvioArquivoTexto(){

		return idUsuarioEnvioArquivoTexto;
	}

	/**
	 * @param idUsuarioEnvioArquivoTexto
	 *            the idUsuarioEnvioArquivoTexto to set
	 */
	public void setIdUsuarioEnvioArquivoTexto(Integer idUsuarioEnvioArquivoTexto){

		this.idUsuarioEnvioArquivoTexto = idUsuarioEnvioArquivoTexto;
	}

	/**
	 * @return the tempoEnvioArquivoTexto
	 */
	public Date getTempoEnvioArquivoTexto(){

		return tempoEnvioArquivoTexto;
	}

	/**
	 * @param tempoEnvioArquivoTexto
	 *            the tempoEnvioArquivoTexto to set
	 */
	public void setTempoEnvioArquivoTexto(Date tempoEnvioArquivoTexto){

		this.tempoEnvioArquivoTexto = tempoEnvioArquivoTexto;
	}

	/**
	 * @return the rota
	 */
	public Rota getRota(){

		return rota;
	}

	/**
	 * @param rota
	 *            the rota to set
	 */
	public void setRota(Rota rota){

		this.rota = rota;
	}

	/**
	 * @return the leiturista
	 */
	public Leiturista getLeiturista(){

		return leiturista;
	}

	/**
	 * @param leiturista
	 *            the leiturista to set
	 */
	public void setLeiturista(Leiturista leiturista){

		this.leiturista = leiturista;
	}

	/**
	 * @return the faturamentoGrupo
	 */
	public FaturamentoGrupo getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	/**
	 * @param faturamentoGrupo
	 *            the faturamentoGrupo to set
	 */
	public void setFaturamentoGrupo(FaturamentoGrupo faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	/**
	 * @return the empresa
	 */
	public Empresa getEmpresa(){

		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Empresa empresa){

		this.empresa = empresa;
	}

	/**
	 * @return the localidade
	 */
	public Localidade getLocalidade(){

		return localidade;
	}

	/**
	 * @param localidade
	 *            the localidade to set
	 */
	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	/**
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the ligacaoAguaSituacao
	 */
	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	/**
	 * @param ligacaoAguaSituacao
	 *            the ligacaoAguaSituacao to set
	 */
	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	/**
	 * @return the ligacaoEsgotoSituacao
	 */
	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	/**
	 * @param ligacaoEsgotoSituacao
	 *            the ligacaoEsgotoSituacao to set
	 */
	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	/**
	 * @return the consumoTarifa
	 */
	public ConsumoTarifa getConsumoTarifa(){

		return consumoTarifa;
	}

	/**
	 * @param consumoTarifa
	 *            the consumoTarifa to set
	 */
	public void setConsumoTarifa(ConsumoTarifa consumoTarifa){

		this.consumoTarifa = consumoTarifa;
	}

	/**
	 * @return the leituraAnormalidadeConsumo
	 */
	public LeituraAnormalidadeConsumo getLeituraAnormalidadeConsumo(){

		return leituraAnormalidadeConsumo;
	}

	/**
	 * @param leituraAnormalidadeConsumo
	 *            the leituraAnormalidadeConsumo to set
	 */
	public void setLeituraAnormalidadeConsumo(LeituraAnormalidadeConsumo leituraAnormalidadeConsumo){

		this.leituraAnormalidadeConsumo = leituraAnormalidadeConsumo;
	}

	/**
	 * @return the medicaoTipo
	 */
	public MedicaoTipo getMedicaoTipo(){

		return medicaoTipo;
	}

	/**
	 * @param medicaoTipo
	 *            the medicaoTipo to set
	 */
	public void setMedicaoTipo(MedicaoTipo medicaoTipo){

		this.medicaoTipo = medicaoTipo;
	}

	/**
	 * @return the leituraAnormalidade
	 */
	public LeituraAnormalidade getLeituraAnormalidade(){

		return leituraAnormalidade;
	}

	/**
	 * @param leituraAnormalidade
	 *            the leituraAnormalidade to set
	 */
	public void setLeituraAnormalidade(LeituraAnormalidade leituraAnormalidade){

		this.leituraAnormalidade = leituraAnormalidade;
	}

	/**
	 * @return the consumoAnormalidade
	 */
	public ConsumoAnormalidade getConsumoAnormalidade(){

		return consumoAnormalidade;
	}

	/**
	 * @param consumoAnormalidade
	 *            the consumoAnormalidade to set
	 */
	public void setConsumoAnormalidade(ConsumoAnormalidade consumoAnormalidade){

		this.consumoAnormalidade = consumoAnormalidade;
	}

	/**
	 * @return the consumoTipo
	 */
	public ConsumoTipo getConsumoTipo(){

		return consumoTipo;
	}

	/**
	 * @param consumoTipo
	 *            the consumoTipo to set
	 */
	public void setConsumoTipo(ConsumoTipo consumoTipo){

		this.consumoTipo = consumoTipo;
	}

	/**
	 * @return the localEntregaDocumento
	 */
	public LocalEntregaDocumento getLocalEntregaDocumento(){

		return localEntregaDocumento;
	}

	/**
	 * @param localEntregaDocumento
	 *            the localEntregaDocumento to set
	 */
	public void setLocalEntregaDocumento(LocalEntregaDocumento localEntregaDocumento){

		this.localEntregaDocumento = localEntregaDocumento;
	}

	/**
	 * @return the indicadorEmissao
	 */
	public Short getIndicadorEmissao(){

		return indicadorEmissao;
	}

	/**
	 * @param indicadorEmissao
	 *            the indicadorEmissao to set
	 */
	public void setIndicadorEmissao(Short indicadorEmissao){

		this.indicadorEmissao = indicadorEmissao;
	}

	/**
	 * @return the referenciaRubrica1
	 */
	public Integer getReferenciaRubrica1(){

		return referenciaRubrica1;
	}

	/**
	 * @param referenciaRubrica1
	 *            the referenciaRubrica1 to set
	 */
	public void setReferenciaRubrica1(Integer referenciaRubrica1){

		this.referenciaRubrica1 = referenciaRubrica1;
	}

	/**
	 * @return the referenciaRubrica2
	 */
	public Integer getReferenciaRubrica2(){

		return referenciaRubrica2;
	}

	/**
	 * @param referenciaRubrica2
	 *            the referenciaRubrica2 to set
	 */
	public void setReferenciaRubrica2(Integer referenciaRubrica2){

		this.referenciaRubrica2 = referenciaRubrica2;
	}

	/**
	 * @return the referenciaRubrica3
	 */
	public Integer getReferenciaRubrica3(){

		return referenciaRubrica3;
	}

	/**
	 * @param referenciaRubrica3
	 *            the referenciaRubrica3 to set
	 */
	public void setReferenciaRubrica3(Integer referenciaRubrica3){

		this.referenciaRubrica3 = referenciaRubrica3;
	}

	/**
	 * @return the referenciaRubrica4
	 */
	public Integer getReferenciaRubrica4(){

		return referenciaRubrica4;
	}

	/**
	 * @param referenciaRubrica4
	 *            the referenciaRubrica4 to set
	 */
	public void setReferenciaRubrica4(Integer referenciaRubrica4){

		this.referenciaRubrica4 = referenciaRubrica4;
	}

	/**
	 * @return the referenciaRubrica5
	 */
	public Integer getReferenciaRubrica5(){

		return referenciaRubrica5;
	}

	/**
	 * @param referenciaRubrica5
	 *            the referenciaRubrica5 to set
	 */
	public void setReferenciaRubrica5(Integer referenciaRubrica5){

		this.referenciaRubrica5 = referenciaRubrica5;
	}

	/**
	 * @return the referenciaRubrica6
	 */
	public Integer getReferenciaRubrica6(){

		return referenciaRubrica6;
	}

	/**
	 * @param referenciaRubrica6
	 *            the referenciaRubrica6 to set
	 */
	public void setReferenciaRubrica6(Integer referenciaRubrica6){

		this.referenciaRubrica6 = referenciaRubrica6;
	}

	/**
	 * @return the referenciaRubrica7
	 */
	public Integer getReferenciaRubrica7(){

		return referenciaRubrica7;
	}

	/**
	 * @param referenciaRubrica7
	 *            the referenciaRubrica7 to set
	 */
	public void setReferenciaRubrica7(Integer referenciaRubrica7){

		this.referenciaRubrica7 = referenciaRubrica7;
	}

	/**
	 * @return the referenciaRubrica8
	 */
	public Integer getReferenciaRubrica8(){

		return referenciaRubrica8;
	}

	/**
	 * @param referenciaRubrica8
	 *            the referenciaRubrica8 to set
	 */
	public void setReferenciaRubrica8(Integer referenciaRubrica8){

		this.referenciaRubrica8 = referenciaRubrica8;
	}

	/**
	 * @return the referenciaRubrica9
	 */
	public Integer getReferenciaRubrica9(){

		return referenciaRubrica9;
	}

	/**
	 * @param referenciaRubrica9
	 *            the referenciaRubrica9 to set
	 */
	public void setReferenciaRubrica9(Integer referenciaRubrica9){

		this.referenciaRubrica9 = referenciaRubrica9;
	}

	/**
	 * @return the referenciaRubrica10
	 */
	public Integer getReferenciaRubrica10(){

		return referenciaRubrica10;
	}

	/**
	 * @param referenciaRubrica10
	 *            the referenciaRubrica10 to set
	 */
	public void setReferenciaRubrica10(Integer referenciaRubrica10){

		this.referenciaRubrica10 = referenciaRubrica10;
	}

	/**
	 * @return the referenciaRubrica11
	 */
	public Integer getReferenciaRubrica11(){

		return referenciaRubrica11;
	}

	/**
	 * @param referenciaRubrica11
	 *            the referenciaRubrica11 to set
	 */
	public void setReferenciaRubrica11(Integer referenciaRubrica11){

		this.referenciaRubrica11 = referenciaRubrica11;
	}

	/**
	 * @return the referenciaRubrica12
	 */
	public Integer getReferenciaRubrica12(){

		return referenciaRubrica12;
	}

	/**
	 * @param referenciaRubrica12
	 *            the referenciaRubrica12 to set
	 */
	public void setReferenciaRubrica12(Integer referenciaRubrica12){

		this.referenciaRubrica12 = referenciaRubrica12;
	}

	/**
	 * @return the referenciaRubrica13
	 */
	public Integer getReferenciaRubrica13(){

		return referenciaRubrica13;
	}

	/**
	 * @param referenciaRubrica13
	 *            the referenciaRubrica13 to set
	 */
	public void setReferenciaRubrica13(Integer referenciaRubrica13){

		this.referenciaRubrica13 = referenciaRubrica13;
	}

	/**
	 * @return the referenciaRubrica14
	 */
	public Integer getReferenciaRubrica14(){

		return referenciaRubrica14;
	}

	/**
	 * @param referenciaRubrica14
	 *            the referenciaRubrica14 to set
	 */
	public void setReferenciaRubrica14(Integer referenciaRubrica14){

		this.referenciaRubrica14 = referenciaRubrica14;
	}

	/**
	 * @return the referenciaRubrica15
	 */
	public Integer getReferenciaRubrica15(){

		return referenciaRubrica15;
	}

	/**
	 * @param referenciaRubrica15
	 *            the referenciaRubrica15 to set
	 */
	public void setReferenciaRubrica15(Integer referenciaRubrica15){

		this.referenciaRubrica15 = referenciaRubrica15;
	}

	/**
	 * @return the referenciaConsumo1
	 */
	public Integer getReferenciaConsumo1(){

		return referenciaConsumo1;
	}

	/**
	 * @param referenciaConsumo1
	 *            the referenciaConsumo1 to set
	 */
	public void setReferenciaConsumo1(Integer referenciaConsumo1){

		this.referenciaConsumo1 = referenciaConsumo1;
	}

	/**
	 * @return the referenciaConsumo2
	 */
	public Integer getReferenciaConsumo2(){

		return referenciaConsumo2;
	}

	/**
	 * @param referenciaConsumo2
	 *            the referenciaConsumo2 to set
	 */
	public void setReferenciaConsumo2(Integer referenciaConsumo2){

		this.referenciaConsumo2 = referenciaConsumo2;
	}

	/**
	 * @return the referenciaConsumo3
	 */
	public Integer getReferenciaConsumo3(){

		return referenciaConsumo3;
	}

	/**
	 * @param referenciaConsumo3
	 *            the referenciaConsumo3 to set
	 */
	public void setReferenciaConsumo3(Integer referenciaConsumo3){

		this.referenciaConsumo3 = referenciaConsumo3;
	}

	/**
	 * @return the referenciaConsumo4
	 */
	public Integer getReferenciaConsumo4(){

		return referenciaConsumo4;
	}

	/**
	 * @param referenciaConsumo4
	 *            the referenciaConsumo4 to set
	 */
	public void setReferenciaConsumo4(Integer referenciaConsumo4){

		this.referenciaConsumo4 = referenciaConsumo4;
	}

	/**
	 * @return the referenciaConsumo5
	 */
	public Integer getReferenciaConsumo5(){

		return referenciaConsumo5;
	}

	/**
	 * @param referenciaConsumo5
	 *            the referenciaConsumo5 to set
	 */
	public void setReferenciaConsumo5(Integer referenciaConsumo5){

		this.referenciaConsumo5 = referenciaConsumo5;
	}

	/**
	 * @return the referenciaConsumo6
	 */
	public Integer getReferenciaConsumo6(){

		return referenciaConsumo6;
	}

	/**
	 * @param referenciaConsumo6
	 *            the referenciaConsumo6 to set
	 */
	public void setReferenciaConsumo6(Integer referenciaConsumo6){

		this.referenciaConsumo6 = referenciaConsumo6;
	}

	public Short getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(Short codigoRota){

		this.codigoRota = codigoRota;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getIndicadorEmissaoCampo(){

		return indicadorEmissaoCampo;
	}

	public void setIndicadorEmissaoCampo(String indicadorEmissaoCampo){

		this.indicadorEmissaoCampo = indicadorEmissaoCampo;
	}

	public Integer getIdLeituraSituacaoAnterior(){

		return idLeituraSituacaoAnterior;
	}

	public void setIdLeituraSituacaoAnterior(Integer idLeituraSituacaoAnterior){

		this.idLeituraSituacaoAnterior = idLeituraSituacaoAnterior;
	}

	public Integer getIdAnormalidadeConsumo1(){

		return idAnormalidadeConsumo1;
	}

	public void setIdAnormalidadeConsumo1(Integer idAnormalidadeConsumo1){

		this.idAnormalidadeConsumo1 = idAnormalidadeConsumo1;
	}

	public Integer getIdAnormalidadeConsumo2(){

		return idAnormalidadeConsumo2;
	}

	public void setIdAnormalidadeConsumo2(Integer idAnormalidadeConsumo2){

		this.idAnormalidadeConsumo2 = idAnormalidadeConsumo2;
	}

	public Integer getIdAnormalidadeConsumo3(){

		return idAnormalidadeConsumo3;
	}

	public void setIdAnormalidadeConsumo3(Integer idAnormalidadeConsumo3){

		this.idAnormalidadeConsumo3 = idAnormalidadeConsumo3;
	}

	public Integer getIdAnormalidadeConsumo4(){

		return idAnormalidadeConsumo4;
	}

	public void setIdAnormalidadeConsumo4(Integer idAnormalidadeConsumo4){

		this.idAnormalidadeConsumo4 = idAnormalidadeConsumo4;
	}

	public Integer getIdAnormalidadeConsumo5(){

		return idAnormalidadeConsumo5;
	}

	public void setIdAnormalidadeConsumo5(Integer idAnormalidadeConsumo5){

		this.idAnormalidadeConsumo5 = idAnormalidadeConsumo5;
	}

	public Integer getIdAnormalidadeConsumo6(){

		return idAnormalidadeConsumo6;
	}

	public void setIdAnormalidadeConsumo6(Integer idAnormalidadeConsumo6){

		this.idAnormalidadeConsumo6 = idAnormalidadeConsumo6;
	}

	public Integer getLeituraFaturada1(){

		return leituraFaturada1;
	}

	public void setLeituraFaturada1(Integer leituraFaturada1){

		this.leituraFaturada1 = leituraFaturada1;
	}

	public Integer getLeituraFaturada2(){

		return leituraFaturada2;
	}

	public void setLeituraFaturada2(Integer leituraFaturada2){

		this.leituraFaturada2 = leituraFaturada2;
	}

	public Integer getLeituraFaturada3(){

		return leituraFaturada3;
	}

	public void setLeituraFaturada3(Integer leituraFaturada3){

		this.leituraFaturada3 = leituraFaturada3;
	}

	public Integer getLeituraFaturada4(){

		return leituraFaturada4;
	}

	public void setLeituraFaturada4(Integer leituraFaturada4){

		this.leituraFaturada4 = leituraFaturada4;
	}

	public Integer getLeituraFaturada5(){

		return leituraFaturada5;
	}

	public void setLeituraFaturada5(Integer leituraFaturada5){

		this.leituraFaturada5 = leituraFaturada5;
	}

	public Integer getLeituraFaturada6(){

		return leituraFaturada6;
	}

	public void setLeituraFaturada6(Integer leituraFaturada6){

		this.leituraFaturada6 = leituraFaturada6;
	}

	public Short getNumeroPrestacaoRubrica1(){

		return numeroPrestacaoRubrica1;
	}

	public void setNumeroPrestacaoRubrica1(Short numeroPrestacaoRubrica1){

		this.numeroPrestacaoRubrica1 = numeroPrestacaoRubrica1;
	}

	public Short getNumeroPrestacaoCobradaRubrica1(){

		return numeroPrestacaoCobradaRubrica1;
	}

	public void setNumeroPrestacaoCobradaRubrica1(Short numeroPrestacaoCobradaRubrica1){

		this.numeroPrestacaoCobradaRubrica1 = numeroPrestacaoCobradaRubrica1;
	}

	public Short getNumeroPrestacaoRubrica2(){

		return numeroPrestacaoRubrica2;
	}

	public void setNumeroPrestacaoRubrica2(Short numeroPrestacaoRubrica2){

		this.numeroPrestacaoRubrica2 = numeroPrestacaoRubrica2;
	}

	public Short getNumeroPrestacaoCobradaRubrica2(){

		return numeroPrestacaoCobradaRubrica2;
	}

	public void setNumeroPrestacaoCobradaRubrica2(Short numeroPrestacaoCobradaRubrica2){

		this.numeroPrestacaoCobradaRubrica2 = numeroPrestacaoCobradaRubrica2;
	}

	public Short getNumeroPrestacaoRubrica3(){

		return numeroPrestacaoRubrica3;
	}

	public void setNumeroPrestacaoRubrica3(Short numeroPrestacaoRubrica3){

		this.numeroPrestacaoRubrica3 = numeroPrestacaoRubrica3;
	}

	public Short getNumeroPrestacaoCobradaRubrica3(){

		return numeroPrestacaoCobradaRubrica3;
	}

	public void setNumeroPrestacaoCobradaRubrica3(Short numeroPrestacaoCobradaRubrica3){

		this.numeroPrestacaoCobradaRubrica3 = numeroPrestacaoCobradaRubrica3;
	}

	public Short getNumeroPrestacaoRubrica4(){

		return numeroPrestacaoRubrica4;
	}

	public void setNumeroPrestacaoRubrica4(Short numeroPrestacaoRubrica4){

		this.numeroPrestacaoRubrica4 = numeroPrestacaoRubrica4;
	}

	public Short getNumeroPrestacaoCobradaRubrica4(){

		return numeroPrestacaoCobradaRubrica4;
	}

	public void setNumeroPrestacaoCobradaRubrica4(Short numeroPrestacaoCobradaRubrica4){

		this.numeroPrestacaoCobradaRubrica4 = numeroPrestacaoCobradaRubrica4;
	}

	public Short getNumeroPrestacaoRubrica5(){

		return numeroPrestacaoRubrica5;
	}

	public void setNumeroPrestacaoRubrica5(Short numeroPrestacaoRubrica5){

		this.numeroPrestacaoRubrica5 = numeroPrestacaoRubrica5;
	}

	public Short getNumeroPrestacaoCobradaRubrica5(){

		return numeroPrestacaoCobradaRubrica5;
	}

	public void setNumeroPrestacaoCobradaRubrica5(Short numeroPrestacaoCobradaRubrica5){

		this.numeroPrestacaoCobradaRubrica5 = numeroPrestacaoCobradaRubrica5;
	}

	public Short getNumeroPrestacaoRubrica6(){

		return numeroPrestacaoRubrica6;
	}

	public void setNumeroPrestacaoRubrica6(Short numeroPrestacaoRubrica6){

		this.numeroPrestacaoRubrica6 = numeroPrestacaoRubrica6;
	}

	public Short getNumeroPrestacaoCobradaRubrica6(){

		return numeroPrestacaoCobradaRubrica6;
	}

	public void setNumeroPrestacaoCobradaRubrica6(Short numeroPrestacaoCobradaRubrica6){

		this.numeroPrestacaoCobradaRubrica6 = numeroPrestacaoCobradaRubrica6;
	}

	public Short getNumeroPrestacaoRubrica7(){

		return numeroPrestacaoRubrica7;
	}

	public void setNumeroPrestacaoRubrica7(Short numeroPrestacaoRubrica7){

		this.numeroPrestacaoRubrica7 = numeroPrestacaoRubrica7;
	}

	public Short getNumeroPrestacaoCobradaRubrica7(){

		return numeroPrestacaoCobradaRubrica7;
	}

	public void setNumeroPrestacaoCobradaRubrica7(Short numeroPrestacaoCobradaRubrica7){

		this.numeroPrestacaoCobradaRubrica7 = numeroPrestacaoCobradaRubrica7;
	}

	public Short getNumeroPrestacaoRubrica8(){

		return numeroPrestacaoRubrica8;
	}

	public void setNumeroPrestacaoRubrica8(Short numeroPrestacaoRubrica8){

		this.numeroPrestacaoRubrica8 = numeroPrestacaoRubrica8;
	}

	public Short getNumeroPrestacaoCobradaRubrica8(){

		return numeroPrestacaoCobradaRubrica8;
	}

	public void setNumeroPrestacaoCobradaRubrica8(Short numeroPrestacaoCobradaRubrica8){

		this.numeroPrestacaoCobradaRubrica8 = numeroPrestacaoCobradaRubrica8;
	}

	public Short getNumeroPrestacaoRubrica9(){

		return numeroPrestacaoRubrica9;
	}

	public void setNumeroPrestacaoRubrica9(Short numeroPrestacaoRubrica9){

		this.numeroPrestacaoRubrica9 = numeroPrestacaoRubrica9;
	}

	public Short getNumeroPrestacaoCobradaRubrica9(){

		return numeroPrestacaoCobradaRubrica9;
	}

	public void setNumeroPrestacaoCobradaRubrica9(Short numeroPrestacaoCobradaRubrica9){

		this.numeroPrestacaoCobradaRubrica9 = numeroPrestacaoCobradaRubrica9;
	}

	public Short getNumeroPrestacaoRubrica10(){

		return numeroPrestacaoRubrica10;
	}

	public void setNumeroPrestacaoRubrica10(Short numeroPrestacaoRubrica10){

		this.numeroPrestacaoRubrica10 = numeroPrestacaoRubrica10;
	}

	public Short getNumeroPrestacaoCobradaRubrica10(){

		return numeroPrestacaoCobradaRubrica10;
	}

	public void setNumeroPrestacaoCobradaRubrica10(Short numeroPrestacaoCobradaRubrica10){

		this.numeroPrestacaoCobradaRubrica10 = numeroPrestacaoCobradaRubrica10;
	}

	public Short getNumeroPrestacaoRubrica11(){

		return numeroPrestacaoRubrica11;
	}

	public void setNumeroPrestacaoRubrica11(Short numeroPrestacaoRubrica11){

		this.numeroPrestacaoRubrica11 = numeroPrestacaoRubrica11;
	}

	public Short getNumeroPrestacaoCobradaRubrica11(){

		return numeroPrestacaoCobradaRubrica11;
	}

	public void setNumeroPrestacaoCobradaRubrica11(Short numeroPrestacaoCobradaRubrica11){

		this.numeroPrestacaoCobradaRubrica11 = numeroPrestacaoCobradaRubrica11;
	}

	public Short getNumeroPrestacaoRubrica12(){

		return numeroPrestacaoRubrica12;
	}

	public void setNumeroPrestacaoRubrica12(Short numeroPrestacaoRubrica12){

		this.numeroPrestacaoRubrica12 = numeroPrestacaoRubrica12;
	}

	public Short getNumeroPrestacaoCobradaRubrica12(){

		return numeroPrestacaoCobradaRubrica12;
	}

	public void setNumeroPrestacaoCobradaRubrica12(Short numeroPrestacaoCobradaRubrica12){

		this.numeroPrestacaoCobradaRubrica12 = numeroPrestacaoCobradaRubrica12;
	}

	public Short getNumeroPrestacaoRubrica13(){

		return numeroPrestacaoRubrica13;
	}

	public void setNumeroPrestacaoRubrica13(Short numeroPrestacaoRubrica13){

		this.numeroPrestacaoRubrica13 = numeroPrestacaoRubrica13;
	}

	public Short getNumeroPrestacaoCobradaRubrica13(){

		return numeroPrestacaoCobradaRubrica13;
	}

	public void setNumeroPrestacaoCobradaRubrica13(Short numeroPrestacaoCobradaRubrica13){

		this.numeroPrestacaoCobradaRubrica13 = numeroPrestacaoCobradaRubrica13;
	}

	public Short getNumeroPrestacaoRubrica14(){

		return numeroPrestacaoRubrica14;
	}

	public void setNumeroPrestacaoRubrica14(Short numeroPrestacaoRubrica14){

		this.numeroPrestacaoRubrica14 = numeroPrestacaoRubrica14;
	}

	public Short getNumeroPrestacaoCobradaRubrica14(){

		return numeroPrestacaoCobradaRubrica14;
	}

	public void setNumeroPrestacaoCobradaRubrica14(Short numeroPrestacaoCobradaRubrica14){

		this.numeroPrestacaoCobradaRubrica14 = numeroPrestacaoCobradaRubrica14;
	}

	public Short getNumeroPrestacaoRubrica15(){

		return numeroPrestacaoRubrica15;
	}

	public void setNumeroPrestacaoRubrica15(Short numeroPrestacaoRubrica15){

		this.numeroPrestacaoRubrica15 = numeroPrestacaoRubrica15;
	}

	public Short getNumeroPrestacaoCobradaRubrica15(){

		return numeroPrestacaoCobradaRubrica15;
	}

	public void setNumeroPrestacaoCobradaRubrica15(Short numeroPrestacaoCobradaRubrica15){

		this.numeroPrestacaoCobradaRubrica15 = numeroPrestacaoCobradaRubrica15;
	}

	public Short getIndicadorSituacaoCorte(){

		return indicadorSituacaoCorte;
	}

	public void setIndicadorSituacaoCorte(Short indicadorSituacaoCorte){

		this.indicadorSituacaoCorte = indicadorSituacaoCorte;
	}

	public Date getDataProcessamento(){

		return dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento){

		this.dataProcessamento = dataProcessamento;
	}

	public Short getIndicadorAtualizarLeitura(){

		return indicadorAtualizarLeitura;
	}

	public void setIndicadorAtualizarLeitura(Short indicadorAtualizarLeitura){

		this.indicadorAtualizarLeitura = indicadorAtualizarLeitura;
	}

	/**
	 * Método Usado para retornar a Inscrição Utilizada por Deso na Geração do arquivo texto do
	 * Faturamento Imediato.
	 * A Inscrição é composta pelos campos:
	 * - Local (LOCA_ID): 03 dígitos
	 * - Setor (MREM_CDSETORCOMERCIAL): 02 dígitos
	 * - Rota (ROTA_CDROTA): 02 dígitos
	 * - Segmento (IMOV_NNSEGMENTO): 02 dígitos
	 * - Lote (MREM_NNLOTEIMOVEL): 04 dígitos
	 * - Sublote (MREM_NNSUBLOTEIMOVEL): 03 dígitos
	 * 
	 * @author Ailton Sousa
	 * @date 06/12/2011
	 * @param imovel
	 * @return
	 */
	public String getInscricaoNaoFormatadaArquivoModelo1(){

		String inscricaoNaoFormatada = "";

		// Localidade
		if(this.getLocalidade() != null && this.getLocalidade().getId() != null){

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(3, this.getLocalidade().getId().toString());
		}else{

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(3, "0");
		}

		// Setor Comercial - Codigo
		if(this.getCodigoSetorComercial() != null){

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(2, this.getCodigoSetorComercial() + "");
		}else{

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(2, "0");
		}

		// Rota
		if(this.getRota() != null){

			inscricaoNaoFormatada = inscricaoNaoFormatada
							+ Util.completarStringComValorEsquerda(String.valueOf(this.getRota().getCodigo()), "0", 2);
		}else{

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(2, "0");
		}

		// Segmento
		if(this.getImovel() != null && this.getImovel().getNumeroSegmento() != null){

			inscricaoNaoFormatada = inscricaoNaoFormatada
							+ Util.completarStringComValorEsquerda(String.valueOf(this.getImovel().getNumeroSegmento()), "0", 2);
		}else{

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(2, "0");
		}

		// Lote
		if(this.getNumeroLoteImovel() != null){

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(4, this.getNumeroLoteImovel() + "");
		}else{

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(4, "0");
		}

		// SubLote
		if(this.getNumeroSubLoteImovel() != null){

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(3, this.getNumeroSubLoteImovel() + "");
		}else{

			inscricaoNaoFormatada = inscricaoNaoFormatada + Util.adicionarZerosEsquedaNumero(3, "0");
		}

		return inscricaoNaoFormatada;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Método que retorna a incrição não formatada.
	 * Inscrição composta pelos campos:
	 * - Local (LOCA_ID): 03 dígitos
	 * - Setor (MREM_CDSETORCOMERCIAL): 02 dígitos
	 * - Quadra (MREM_NNQUADRA): 04 dígitos
	 * - Lote (MREM_NNLOTEIMOVEL): 04 dígitos
	 * 
	 * @author Anderson Italo
	 * @date 04/06/2012
	 * @return
	 */
	public String getInscricaoNaoFormatadaArquivoModelo2(){

		return getInscricaoNaoFormatada(3, 2, 4, 4);
	}

	public String getInscricaoNaoFormatada(int locaSize, int setorSize, int quadraSize, int loteSize){

		StringBuilder stringBuilder = new StringBuilder();

		// LOCALIDADE
		if(localidade == null) stringBuilder.append(Util.formatLPad("", locaSize, '0'));
		else stringBuilder.append(Util.formatLPad(localidade.getId(), locaSize, '0'));
		// SETOR
		stringBuilder.append(Util.formatLPad(codigoSetorComercial, setorSize, '0'));
		// QUADRA
		stringBuilder.append(Util.formatLPad(numeroQuadra, quadraSize, '0'));
		// LOTE
		stringBuilder.append(Util.formatLPad(numeroLoteImovel, loteSize, '0'));

		return stringBuilder.toString();
	}

	public Integer getReferenciaConsumo7(){

		return referenciaConsumo7;
	}

	public void setReferenciaConsumo7(Integer referenciaConsumo7){

		this.referenciaConsumo7 = referenciaConsumo7;
	}

	public Integer getNumeroConsumo7(){

		return numeroConsumo7;
	}

	public void setNumeroConsumo7(Integer numeroConsumo7){

		this.numeroConsumo7 = numeroConsumo7;
	}

	public Integer getIdAnormalidadeLeitura7(){

		return idAnormalidadeLeitura7;
	}

	public void setIdAnormalidadeLeitura7(Integer idAnormalidadeLeitura7){

		this.idAnormalidadeLeitura7 = idAnormalidadeLeitura7;
	}

	public Integer getReferenciaConsumo8(){

		return referenciaConsumo8;
	}

	public void setReferenciaConsumo8(Integer referenciaConsumo8){

		this.referenciaConsumo8 = referenciaConsumo8;
	}

	public Integer getNumeroConsumo8(){

		return numeroConsumo8;
	}

	public void setNumeroConsumo8(Integer numeroConsumo8){

		this.numeroConsumo8 = numeroConsumo8;
	}

	public Integer getIdAnormalidadeLeitura8(){

		return idAnormalidadeLeitura8;
	}

	public void setIdAnormalidadeLeitura8(Integer idAnormalidadeLeitura8){

		this.idAnormalidadeLeitura8 = idAnormalidadeLeitura8;
	}

	public Integer getReferenciaConsumo9(){

		return referenciaConsumo9;
	}

	public void setReferenciaConsumo9(Integer referenciaConsumo9){

		this.referenciaConsumo9 = referenciaConsumo9;
	}

	public Integer getNumeroConsumo9(){

		return numeroConsumo9;
	}

	public void setNumeroConsumo9(Integer numeroConsumo9){

		this.numeroConsumo9 = numeroConsumo9;
	}

	public Integer getIdAnormalidadeLeitura9(){

		return idAnormalidadeLeitura9;
	}

	public void setIdAnormalidadeLeitura9(Integer idAnormalidadeLeitura9){

		this.idAnormalidadeLeitura9 = idAnormalidadeLeitura9;
	}

	public Integer getReferenciaConsumo10(){

		return referenciaConsumo10;
	}

	public void setReferenciaConsumo10(Integer referenciaConsumo10){

		this.referenciaConsumo10 = referenciaConsumo10;
	}

	public Integer getNumeroConsumo10(){

		return numeroConsumo10;
	}

	public void setNumeroConsumo10(Integer numeroConsumo10){

		this.numeroConsumo10 = numeroConsumo10;
	}

	public Integer getIdAnormalidadeLeitura10(){

		return idAnormalidadeLeitura10;
	}

	public void setIdAnormalidadeLeitura10(Integer idAnormalidadeLeitura10){

		this.idAnormalidadeLeitura10 = idAnormalidadeLeitura10;
	}

	public Integer getReferenciaConsumo11(){

		return referenciaConsumo11;
	}

	public void setReferenciaConsumo11(Integer referenciaConsumo11){

		this.referenciaConsumo11 = referenciaConsumo11;
	}

	public Integer getNumeroConsumo11(){

		return numeroConsumo11;
	}

	public void setNumeroConsumo11(Integer numeroConsumo11){

		this.numeroConsumo11 = numeroConsumo11;
	}

	public Integer getIdAnormalidadeLeitura11(){

		return idAnormalidadeLeitura11;
	}

	public void setIdAnormalidadeLeitura11(Integer idAnormalidadeLeitura11){

		this.idAnormalidadeLeitura11 = idAnormalidadeLeitura11;
	}

	public Integer getReferenciaConsumo12(){

		return referenciaConsumo12;
	}

	public void setReferenciaConsumo12(Integer referenciaConsumo12){

		this.referenciaConsumo12 = referenciaConsumo12;
	}

	public Integer getNumeroConsumo12(){

		return numeroConsumo12;
	}

	public void setNumeroConsumo12(Integer numeroConsumo12){

		this.numeroConsumo12 = numeroConsumo12;
	}

	public Integer getIdAnormalidadeLeitura12(){

		return idAnormalidadeLeitura12;
	}

	public void setIdAnormalidadeLeitura12(Integer idAnormalidadeLeitura12){

		this.idAnormalidadeLeitura12 = idAnormalidadeLeitura12;
	}

	public Integer getIdAnormalidadeConsumo7(){

		return idAnormalidadeConsumo7;
	}

	public void setIdAnormalidadeConsumo7(Integer idAnormalidadeConsumo7){

		this.idAnormalidadeConsumo7 = idAnormalidadeConsumo7;
	}

	public Integer getIdAnormalidadeConsumo8(){

		return idAnormalidadeConsumo8;
	}

	public void setIdAnormalidadeConsumo8(Integer idAnormalidadeConsumo8){

		this.idAnormalidadeConsumo8 = idAnormalidadeConsumo8;
	}

	public Integer getIdAnormalidadeConsumo9(){

		return idAnormalidadeConsumo9;
	}

	public void setIdAnormalidadeConsumo9(Integer idAnormalidadeConsumo9){

		this.idAnormalidadeConsumo9 = idAnormalidadeConsumo9;
	}

	public Integer getIdAnormalidadeConsumo10(){

		return idAnormalidadeConsumo10;
	}

	public void setIdAnormalidadeConsumo10(Integer idAnormalidadeConsumo10){

		this.idAnormalidadeConsumo10 = idAnormalidadeConsumo10;
	}

	public Integer getIdAnormalidadeConsumo11(){

		return idAnormalidadeConsumo11;
	}

	public void setIdAnormalidadeConsumo11(Integer idAnormalidadeConsumo11){

		this.idAnormalidadeConsumo11 = idAnormalidadeConsumo11;
	}

	public Integer getIdAnormalidadeConsumo12(){

		return idAnormalidadeConsumo12;
	}

	public void setIdAnormalidadeConsumo12(Integer idAnormalidadeConsumo12){

		this.idAnormalidadeConsumo12 = idAnormalidadeConsumo12;
	}

	public Integer getLeituraFaturada7(){

		return leituraFaturada7;
	}

	public void setLeituraFaturada7(Integer leituraFaturada7){

		this.leituraFaturada7 = leituraFaturada7;
	}

	public Integer getLeituraFaturada8(){

		return leituraFaturada8;
	}

	public void setLeituraFaturada8(Integer leituraFaturada8){

		this.leituraFaturada8 = leituraFaturada8;
	}

	public Integer getLeituraFaturada9(){

		return leituraFaturada9;
	}

	public void setLeituraFaturada9(Integer leituraFaturada9){

		this.leituraFaturada9 = leituraFaturada9;
	}

	public Integer getLeituraFaturada10(){

		return leituraFaturada10;
	}

	public void setLeituraFaturada10(Integer leituraFaturada10){

		this.leituraFaturada10 = leituraFaturada10;
	}

	public Integer getLeituraFaturada11(){

		return leituraFaturada11;
	}

	public void setLeituraFaturada11(Integer leituraFaturada11){

		this.leituraFaturada11 = leituraFaturada11;
	}

	public Integer getLeituraFaturada12(){

		return leituraFaturada12;
	}

	public void setLeituraFaturada12(Integer leituraFaturada12){

		this.leituraFaturada12 = leituraFaturada12;
	}

	public Date getDataVencimentoRetorno(){

		return dataVencimentoRetorno;
	}

	public void setDataVencimentoRetorno(Date dataVencimentoRetorno){

		this.dataVencimentoRetorno = dataVencimentoRetorno;
	}

	public Date getDataLeituraFaturada(){

		return dataLeituraFaturada;
	}

	public void setDataLeituraFaturada(Date dataLeituraFaturada){

		this.dataLeituraFaturada = dataLeituraFaturada;
	}

	public BigDecimal getValorRateio(){

		return valorRateio;
	}

	public void setValorRateio(BigDecimal valorRateio){

		this.valorRateio = valorRateio;
	}

	public Short getIndicadorModoFaturarRateio(){

		return indicadorModoFaturarRateio;
	}

	public void setIndicadorModoFaturarRateio(Short indicadorModoFaturarRateio){

		this.indicadorModoFaturarRateio = indicadorModoFaturarRateio;
	}

	/**
	 * @return the numeroConsumoRateio
	 */
	public Integer getNumeroConsumoRateio(){

		return numeroConsumoRateio;
	}

	/**
	 * @param numeroConsumoRateio
	 *            the numeroConsumoRateio to set
	 */
	public void setNumeroConsumoRateio(Integer numeroConsumoRateio){

		this.numeroConsumoRateio = numeroConsumoRateio;
	}

	/**
	 * Método que obtém o valor da conta (MREM_VLAGUA + MREM_VLESGOGO + MREM_VLDEBITOS –
	 * MREM_VLCREDITOS)
	 * 
	 * @author Anderson Italo
	 * @date 26/06/2012
	 */
	public BigDecimal getValorTotalConta(){

		BigDecimal valorTotalConta = BigDecimal.ZERO;

		// Valor de água
		if(this.getValorAgua() != null){

			valorTotalConta = valorTotalConta.add(this.getValorAgua());
		}

		// Valor de esgoto
		if(this.getValorEsgoto() != null){

			valorTotalConta = valorTotalConta.add(this.getValorEsgoto());
		}

		// Valor dos débitos
		if(this.getValorDebitos() != null){

			valorTotalConta = valorTotalConta.add(this.getValorDebitos());
		}

		// Valor dos créditos
		if(this.getValorCreditos() != null){

			valorTotalConta = valorTotalConta.subtract(this.getValorCreditos());
		}

		return valorTotalConta;
	}

	/**
	 * Método que obtém o valor da conta (MREM_VLAGUA + MREM_VLESGOGO + MREM_VLDEBITOS)
	 * 
	 * @author Anderson Italo
	 * @date 26/06/2012
	 */
	public BigDecimal getValorTotalContaSemCreditos(){

		BigDecimal valorTotalConta = BigDecimal.ZERO;

		// Valor de água
		if(this.getValorAgua() != null){

			valorTotalConta = valorTotalConta.add(this.getValorAgua());
		}

		// Valor de esgoto
		if(this.getValorEsgoto() != null){

			valorTotalConta = valorTotalConta.add(this.getValorEsgoto());
		}

		// Valor dos débitos
		if(this.getValorDebitos() != null){

			valorTotalConta = valorTotalConta.add(this.getValorDebitos());
		}

		return valorTotalConta;
	}

	public Short getNumeroPrestacaoCredito1(){

		return numeroPrestacaoCredito1;
	}

	public void setNumeroPrestacaoCredito1(Short numeroPrestacaoCredito1){

		this.numeroPrestacaoCredito1 = numeroPrestacaoCredito1;
	}

	public Short getNumeroPrestacaoCobradaCredito1(){

		return numeroPrestacaoCobradaCredito1;
	}

	public void setNumeroPrestacaoCobradaCredito1(Short numeroPrestacaoCobradaCredito1){

		this.numeroPrestacaoCobradaCredito1 = numeroPrestacaoCobradaCredito1;
	}

	public String getDescricaoCredito1(){

		return descricaoCredito1;
	}

	public void setDescricaoCredito1(String descricaoCredito1){

		this.descricaoCredito1 = descricaoCredito1;
	}

	public Integer getReferenciaCredito1(){

		return referenciaCredito1;
	}

	public void setReferenciaCredito1(Integer referenciaCredito1){

		this.referenciaCredito1 = referenciaCredito1;
	}

	public BigDecimal getValorCredito1(){

		return valorCredito1;
	}

	public void setValorCredito1(BigDecimal valorCredito1){

		this.valorCredito1 = valorCredito1;
	}

	public Short getNumeroPrestacaoCredito2(){

		return numeroPrestacaoCredito2;
	}

	public void setNumeroPrestacaoCredito2(Short numeroPrestacaoCredito2){

		this.numeroPrestacaoCredito2 = numeroPrestacaoCredito2;
	}

	public Short getNumeroPrestacaoCobradaCredito2(){

		return numeroPrestacaoCobradaCredito2;
	}

	public void setNumeroPrestacaoCobradaCredito2(Short numeroPrestacaoCobradaCredito2){

		this.numeroPrestacaoCobradaCredito2 = numeroPrestacaoCobradaCredito2;
	}

	public String getDescricaoCredito2(){

		return descricaoCredito2;
	}

	public void setDescricaoCredito2(String descricaoCredito2){

		this.descricaoCredito2 = descricaoCredito2;
	}

	public Integer getReferenciaCredito2(){

		return referenciaCredito2;
	}

	public void setReferenciaCredito2(Integer referenciaCredito2){

		this.referenciaCredito2 = referenciaCredito2;
	}

	public BigDecimal getValorCredito2(){

		return valorCredito2;
	}

	public void setValorCredito2(BigDecimal valorCredito2){

		this.valorCredito2 = valorCredito2;
	}

	public Short getNumeroPrestacaoCredito3(){

		return numeroPrestacaoCredito3;
	}

	public void setNumeroPrestacaoCredito3(Short numeroPrestacaoCredito3){

		this.numeroPrestacaoCredito3 = numeroPrestacaoCredito3;
	}

	public Short getNumeroPrestacaoCobradaCredito3(){

		return numeroPrestacaoCobradaCredito3;
	}

	public void setNumeroPrestacaoCobradaCredito3(Short numeroPrestacaoCobradaCredito3){

		this.numeroPrestacaoCobradaCredito3 = numeroPrestacaoCobradaCredito3;
	}

	public String getDescricaoCredito3(){

		return descricaoCredito3;
	}

	public void setDescricaoCredito3(String descricaoCredito3){

		this.descricaoCredito3 = descricaoCredito3;
	}

	public Integer getReferenciaCredito3(){

		return referenciaCredito3;
	}

	public void setReferenciaCredito3(Integer referenciaCredito3){

		this.referenciaCredito3 = referenciaCredito3;
	}

	public BigDecimal getValorCredito3(){

		return valorCredito3;
	}

	public void setValorCredito3(BigDecimal valorCredito3){

		this.valorCredito3 = valorCredito3;
	}

	public Short getNumeroPrestacaoCredito4(){

		return numeroPrestacaoCredito4;
	}

	public void setNumeroPrestacaoCredito4(Short numeroPrestacaoCredito4){

		this.numeroPrestacaoCredito4 = numeroPrestacaoCredito4;
	}

	public Short getNumeroPrestacaoCobradaCredito4(){

		return numeroPrestacaoCobradaCredito4;
	}

	public void setNumeroPrestacaoCobradaCredito4(Short numeroPrestacaoCobradaCredito4){

		this.numeroPrestacaoCobradaCredito4 = numeroPrestacaoCobradaCredito4;
	}

	public String getDescricaoCredito4(){

		return descricaoCredito4;
	}

	public void setDescricaoCredito4(String descricaoCredito4){

		this.descricaoCredito4 = descricaoCredito4;
	}

	public Integer getReferenciaCredito4(){

		return referenciaCredito4;
	}

	public void setReferenciaCredito4(Integer referenciaCredito4){

		this.referenciaCredito4 = referenciaCredito4;
	}

	public BigDecimal getValorCredito4(){

		return valorCredito4;
	}

	public void setValorCredito4(BigDecimal valorCredito4){

		this.valorCredito4 = valorCredito4;
	}

	public Short getNumeroPrestacaoCredito5(){

		return numeroPrestacaoCredito5;
	}

	public void setNumeroPrestacaoCredito5(Short numeroPrestacaoCredito5){

		this.numeroPrestacaoCredito5 = numeroPrestacaoCredito5;
	}

	public Short getNumeroPrestacaoCobradaCredito5(){

		return numeroPrestacaoCobradaCredito5;
	}

	public void setNumeroPrestacaoCobradaCredito5(Short numeroPrestacaoCobradaCredito5){

		this.numeroPrestacaoCobradaCredito5 = numeroPrestacaoCobradaCredito5;
	}

	public String getDescricaoCredito5(){

		return descricaoCredito5;
	}

	public void setDescricaoCredito5(String descricaoCredito5){

		this.descricaoCredito5 = descricaoCredito5;
	}

	public Integer getReferenciaCredito5(){

		return referenciaCredito5;
	}

	public void setReferenciaCredito5(Integer referenciaCredito5){

		this.referenciaCredito5 = referenciaCredito5;
	}

	public BigDecimal getValorCredito5(){

		return valorCredito5;
	}

	public void setValorCredito5(BigDecimal valorCredito5){

		this.valorCredito5 = valorCredito5;
	}

	public BigDecimal getValorCreditado1(){

		return valorCreditado1;
	}

	public void setValorCreditado1(BigDecimal valorCreditado1){

		this.valorCreditado1 = valorCreditado1;
	}

	public BigDecimal getValorCreditado2(){

		return valorCreditado2;
	}

	public void setValorCreditado2(BigDecimal valorCreditado2){

		this.valorCreditado2 = valorCreditado2;
	}

	public BigDecimal getValorCreditado3(){

		return valorCreditado3;
	}

	public void setValorCreditado3(BigDecimal valorCreditado3){

		this.valorCreditado3 = valorCreditado3;
	}

	public BigDecimal getValorCreditado4(){

		return valorCreditado4;
	}

	public void setValorCreditado4(BigDecimal valorCreditado4){

		this.valorCreditado4 = valorCreditado4;
	}

	public BigDecimal getValorCreditado5(){

		return valorCreditado5;
	}

	public void setValorCreditado5(BigDecimal valorCreditado5){

		this.valorCreditado5 = valorCreditado5;
	}

	public BigDecimal getValorReligacao(){

		return valorReligacao;
	}

	public void setValorReligacao(BigDecimal valorReligacao){

		this.valorReligacao = valorReligacao;
	}

	public BigDecimal getValorSancao(){

		return valorSancao;
	}

	public void setValorSancao(BigDecimal valorSancao){

		this.valorSancao = valorSancao;
	}

	public Integer getIdServicoReligacao(){

		return idServicoReligacao;
	}

	public void setIdServicoReligacao(Integer idServicoReligacao){

		this.idServicoReligacao = idServicoReligacao;
	}

	public Integer getIdServicoSancao(){

		return idServicoSancao;
	}

	public void setIdServicoSancao(Integer idServicoSancao){

		this.idServicoSancao = idServicoSancao;
	}

	public Short getIndicadorReligacaoAgua(){

		return indicadorReligacaoAgua;
	}

	public void setIndicadorReligacaoAgua(Short indicadorReligacaoAgua){

		this.indicadorReligacaoAgua = indicadorReligacaoAgua;
	}

	public String getNumeroImovelAlteracao(){

		return numeroImovelAlteracao;
	}

	public void setNumeroImovelAlteracao(String numeroImovelAlteracao){

		this.numeroImovelAlteracao = numeroImovelAlteracao;
	}

	public Short getIndicadorStatusRegistSistLegado(){

		return indicadorStatusRegistSistLegado;
	}

	public void setIndicadorStatusRegistSistLegado(Short indicadorStatusRegistSistLegado){

		this.indicadorStatusRegistSistLegado = indicadorStatusRegistSistLegado;
	}

	public Short getIndicadorFaturaRetida(){

		return indicadorFaturaRetida;
	}

	public void setIndicadorFaturaRetida(Short indicadorFaturaRetida){

		this.indicadorFaturaRetida = indicadorFaturaRetida;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getNumeroHidrometroAlteracao(){

		return numeroHidrometroAlteracao;
	}

	public void setNumeroHidrometroAlteracao(String numeroHidrometroAlteracao){

		this.numeroHidrometroAlteracao = numeroHidrometroAlteracao;
	}

	public Short getQuantidadeEconomiaResidencialAlteracao(){

		return quantidadeEconomiaResidencialAlteracao;
	}

	public void setQuantidadeEconomiaResidencialAlteracao(Short quantidadeEconomiaResidencialAlteracao){

		this.quantidadeEconomiaResidencialAlteracao = quantidadeEconomiaResidencialAlteracao;
	}

	public Short getQuantidadeEconomiaComercialAlteracao(){

		return quantidadeEconomiaComercialAlteracao;
	}

	public void setQuantidadeEconomiaComercialAlteracao(Short quantidadeEconomiaComercialAlteracao){

		this.quantidadeEconomiaComercialAlteracao = quantidadeEconomiaComercialAlteracao;
	}

	public Short getQuantidadeEconomiaIndustrialAlteracao(){

		return quantidadeEconomiaIndustrialAlteracao;
	}

	public void setQuantidadeEconomiaIndustrialAlteracao(Short quantidadeEconomiaIndustrialAlteracao){

		this.quantidadeEconomiaIndustrialAlteracao = quantidadeEconomiaIndustrialAlteracao;
	}

	public Short getQuantidadeEconomiaPublicaAlteracao(){

		return quantidadeEconomiaPublicaAlteracao;
	}

	public void setQuantidadeEconomiaPublicaAlteracao(Short quantidadeEconomiaPublicaAlteracao){

		this.quantidadeEconomiaPublicaAlteracao = quantidadeEconomiaPublicaAlteracao;
	}

	public String getDescricaoComplementoEnderecoAlteracao(){

		return descricaoComplementoEnderecoAlteracao;
	}

	public void setDescricaoComplementoEnderecoAlteracao(String descricaoComplementoEnderecoAlteracao){

		this.descricaoComplementoEnderecoAlteracao = descricaoComplementoEnderecoAlteracao;
	}

	public String getDataLeituraFormatada(){

		return Util.formatarData(getDataLeitura());
	}

	public String getDataLeituraAnteriorFormatada(){

		return Util.formatarData(getDataLeituraAnterior());
	}

	public Integer getNumeroConsumoFixoPoco(){

		return numeroConsumoFixoPoco;
	}

	public void setNumeroConsumoFixoPoco(Integer numeroConsumoFixoPoco){

		this.numeroConsumoFixoPoco = numeroConsumoFixoPoco;
	}

	public Integer getIdCategoriaAtualizacaoCadastral(){

		return idCategoriaAtualizacaoCadastral;
	}

	public void setIdCategoriaAtualizacaoCadastral(Integer idCategoriaAtualizacaoCadastral){

		this.idCategoriaAtualizacaoCadastral = idCategoriaAtualizacaoCadastral;
	}

	public Integer getIdSubCategoriaAtualizacaoCadastral(){

		return idSubCategoriaAtualizacaoCadastral;
	}

	public void setIdSubCategoriaAtualizacaoCadastral(Integer idSubCategoriaAtualizacaoCadastral){

		this.idSubCategoriaAtualizacaoCadastral = idSubCategoriaAtualizacaoCadastral;
	}

}
