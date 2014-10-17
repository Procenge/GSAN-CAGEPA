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
 * GSANPCG
 * Saulo Vasconcelos de Lima
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

package gcom.faturamento.bean;

import gcom.cadastro.cliente.Cliente;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.relatorio.faturamento.conta.ContaLinhasDescricaoServicosTarifasTotalHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hibernate CodeGenerator
 * @author Saulo Lima
 * @date 19/04/2010
 *       Implementar a interface Cloneable para efetuar um CLONE do objeto
 */
public class EmitirContaTipo2Helper
				implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer idConta;

	private int sequencialImpressao;

	private int totalContasImpressao;

	private Integer anoMesConta;

	private String cepImovelFormatado;

	private Integer idClienteRelacaoTipo;

	private Integer idClienteResponsavel;

	private String nomeCliente;

	private String nomeImovel;

	private String endereco;

	private Integer codigoElo;

	private Short digitoVerificadorConta;

	private String descricaoLocalidade;

	private Integer inscLocalidade;

	private Integer inscSetorComercial;

	private Integer inscQuadra;

	private Short inscLote;

	private Short inscSubLote;

	private Short econResidencial;

	private Short econComercial;

	private Short econIndustrial;

	private Short econPublica;

	private String hidrometro;

	private HidrometroLocalInstalacao hidrometroLocalInstalacao;

	private String descricaoTipoConsumo;

	private Date dtLeituraAnterior;

	private Date dtLeituraAtual;

	private Integer idLigacaoAguaSituacao;

	private Integer idLigacaoEsgotoSituacao;

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	private Integer idLeituraSituacaoAtual;

	private Integer idLeituraAnormalidadeFaturamento;

	private Integer leituraAnterior;

	private Integer leituraAtual;

	private Integer anoMes1Conta;

	private Integer idConsumoTipo;

	private Integer idTipoContrato;

	private Integer idConsumoAnormalidade;

	private Integer consumo1Conta;

	private Integer anoMes2Conta;

	private Integer consumo2Conta;

	private Integer anoMes3Conta;

	private Integer consumo3Conta;

	private Integer anoMes4Conta;

	private Integer consumo4Conta;

	private Integer anoMes5Conta;

	private Integer consumo5Conta;

	private Integer anoMes6Conta;

	private Integer consumo6Conta;

	private Integer consumoMedio;

	private String msgConta;

	private String msgContaParte1;

	private String msgContaParte2;

	private String msgContaParte3;

	private Integer qtdContasAviso;

	private Date dataVencimento;

	private BigDecimal valorTotalConta;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String inscricao;

	private Short codigoRota;

	private Integer idImovel;

	private Integer idImovelContaEnvio;

	private String enderecoClienteEntrega;

	private Integer consumoAgua;

	private Integer consumoEsgoto;

	private Integer consumoFaturado;

	private String consumoMedidoEstimado;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal debitos;

	private BigDecimal valorCreditos;

	private BigDecimal valorImpostos;

	private BigDecimal percentualEsgotoConta;

	private Integer idGerenciaRegional;

	private String nomeGerenciaRegional;

	private Integer idFaturamentoGrupo;

	private Integer idEmpresa;

	private Integer idRota;

	private List<ContaLinhasDescricaoServicosTarifasTotalHelper> colecaoContaLinhasDescricaoServicosTarifasTotalHelper;

	private Short indicadorCodigoBarras; // Utilizado para exibição ou não na impressão;

	private String descricaoAnormalidadeConsumo;

	private String nomeBanco;

	private String codigoAgencia;

	private List<EmitirContaTipo2Helper> colecaoPaginasAdicionaisFatura = new ArrayList<EmitirContaTipo2Helper>();

	private Short indicadorValorConta = ConstantesSistema.SIM; // Utilizado para exibição ou não na

	// impressão;

	private Short indicadorVencimentoConta = ConstantesSistema.SIM; // Utilizado para exibição ou

	// não na impressão;

	private Short indicadorDebitoAutomatico; // Utilizado para exibição ou não na impressão;

	private Short indicadorFaturaInformativa; // nao exibir o label de conta demonstrativa

	private Integer idLogradouroEnderecoClienteEntrega;

	private String numeroImovelEnderecoClienteEntrega;

	private Integer idLogradouro;

	private String numeroImovel;

	private String grupoImovelCategoriaResidencial;

	private String grupoImovelCategoriaComercial;

	private String grupoImovelCategoriaPublica;

	private String grupoImovelCategoriaIndustrial;

	private Short indicadorPossuiMedicaoHistorico;

	private int idTipoMedicao;

	private int indexDescricaoTarifasServicos;

	private Integer idClienteUsuario;

	private Integer idImovelPerfil;

	private Date dataRevisao;

	private Integer idMotivoRevisao;

	private List<EmitirContaServicoHelper> listaServicos;

	private List<EmitirContaUltimosConsumosLeiturasHelper> listaUltimosConsumosLeituras;

	private Object[] mensagemConta;

	private Cliente clienteResponsavel;

	private Cliente clienteUsuario;

	private Date dataInstalacaoHidrometro;

	private HidrometroMarca hidrometroMarca;

	private HidrometroCapacidade hidrometroCapacidade;

	private Integer idTipoLigacao;

	private Integer idSetorComercial;

	private int consumoMinimoLigacao;

	private Short indicadorContaBraille;

	public Object clone() throws CloneNotSupportedException{

		return super.clone();
	}

	public EmitirContaTipo2Helper() {

		super();
		listaServicos = new ArrayList<EmitirContaServicoHelper>();
		listaUltimosConsumosLeituras = new ArrayList<EmitirContaUltimosConsumosLeiturasHelper>();
		mensagemConta = new Object[3];
	}

	public Integer getIdLogradouroEnderecoClienteEntrega(){

		return idLogradouroEnderecoClienteEntrega;
	}

	public void setIdLogradouroEnderecoClienteEntrega(Integer idLogradouroEnderecoClienteEntrega){

		this.idLogradouroEnderecoClienteEntrega = idLogradouroEnderecoClienteEntrega;
	}

	public String getNumeroImovelEnderecoClienteEntrega(){

		return numeroImovelEnderecoClienteEntrega;
	}

	public void setNumeroImovelEnderecoClienteEntrega(String numeroImovelEnderecoClienteEntrega){

		this.numeroImovelEnderecoClienteEntrega = numeroImovelEnderecoClienteEntrega;
	}

	public Integer getIdLogradouro(){

		return idLogradouro;
	}

	public void setIdLogradouro(Integer idLogradouro){

		this.idLogradouro = idLogradouro;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getNomeBanco(){

		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco){

		this.nomeBanco = nomeBanco;
	}

	public String getCodigoAgencia(){

		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia){

		this.codigoAgencia = codigoAgencia;
	}

	public int getSequencialImpressao(){

		return sequencialImpressao;
	}

	public void setSequencialImpressao(int sequencialImpressao){

		this.sequencialImpressao = sequencialImpressao;
	}

	public Integer getAnoMesConta(){

		return anoMesConta;
	}

	public void setAnoMesConta(Integer anoMesConta){

		this.anoMesConta = anoMesConta;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public Integer getInscLocalidade(){

		return inscLocalidade;
	}

	public void setInscLocalidade(Integer inscLocalidade){

		this.inscLocalidade = inscLocalidade;
	}

	public Integer getInscSetorComercial(){

		return inscSetorComercial;
	}

	public void setInscSetorComercial(Integer inscSetorComercial){

		this.inscSetorComercial = inscSetorComercial;
	}

	public Integer getInscQuadra(){

		return inscQuadra;
	}

	public void setInscQuadra(Integer inscQuadra){

		this.inscQuadra = inscQuadra;
	}

	public Short getInscLote(){

		return inscLote;
	}

	public void setInscLote(Short inscLote){

		this.inscLote = inscLote;
	}

	public Short getInscSubLote(){

		return inscSubLote;
	}

	public void setInscSubLote(Short inscSubLote){

		this.inscSubLote = inscSubLote;
	}

	public Short getEconResidencial(){

		return econResidencial;
	}

	public void setEconResidencial(Short econResidencial){

		this.econResidencial = econResidencial;
	}

	public Short getEconComercial(){

		return econComercial;
	}

	public void setEconComercial(Short econComercial){

		this.econComercial = econComercial;
	}

	public Short getEconIndustrial(){

		return econIndustrial;
	}

	public void setEconIndustrial(Short econIndustrial){

		this.econIndustrial = econIndustrial;
	}

	public Short getEconPublica(){

		return econPublica;
	}

	public void setEconPublica(Short econPublica){

		this.econPublica = econPublica;
	}

	public String getHidrometro(){

		return hidrometro;
	}

	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	public Date getDtLeituraAnterior(){

		return dtLeituraAnterior;
	}

	public void setDtLeituraAnterior(Date dtLeituraAnterior){

		this.dtLeituraAnterior = dtLeituraAnterior;
	}

	public Date getDtLeituraAtual(){

		return dtLeituraAtual;
	}

	public void setDtLeituraAtual(Date dtLeituraAtual){

		this.dtLeituraAtual = dtLeituraAtual;
	}

	public Integer getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(Integer leituraAnterior){

		this.leituraAnterior = leituraAnterior;
	}

	public Integer getLeituraAtual(){

		return leituraAtual;
	}

	public void setLeituraAtual(Integer leituraAtual){

		this.leituraAtual = leituraAtual;
	}

	public Integer getAnoMes1Conta(){

		return anoMes1Conta;
	}

	public void setAnoMes1Conta(Integer anoMes1Conta){

		this.anoMes1Conta = anoMes1Conta;
	}

	public Integer getConsumo1Conta(){

		return consumo1Conta;
	}

	public void setConsumo1Conta(Integer consumo1Conta){

		this.consumo1Conta = consumo1Conta;
	}

	public Integer getAnoMes2Conta(){

		return anoMes2Conta;
	}

	public void setAnoMes2Conta(Integer anoMes2Conta){

		this.anoMes2Conta = anoMes2Conta;
	}

	public Integer getConsumo2Conta(){

		return consumo2Conta;
	}

	public void setConsumo2Conta(Integer consumo2Conta){

		this.consumo2Conta = consumo2Conta;
	}

	public Integer getAnoMes3Conta(){

		return anoMes3Conta;
	}

	public void setAnoMes3Conta(Integer anoMes3Conta){

		this.anoMes3Conta = anoMes3Conta;
	}

	public Integer getConsumo3Conta(){

		return consumo3Conta;
	}

	public void setConsumo3Conta(Integer consumo3Conta){

		this.consumo3Conta = consumo3Conta;
	}

	public Integer getAnoMes4Conta(){

		return anoMes4Conta;
	}

	public void setAnoMes4Conta(Integer anoMes4Conta){

		this.anoMes4Conta = anoMes4Conta;
	}

	public Integer getConsumo4Conta(){

		return consumo4Conta;
	}

	public void setConsumo4Conta(Integer consumo4Conta){

		this.consumo4Conta = consumo4Conta;
	}

	public Integer getAnoMes5Conta(){

		return anoMes5Conta;
	}

	public void setAnoMes5Conta(Integer anoMes5Conta){

		this.anoMes5Conta = anoMes5Conta;
	}

	public Integer getConsumo5Conta(){

		return consumo5Conta;
	}

	public void setConsumo5Conta(Integer consumo5Conta){

		this.consumo5Conta = consumo5Conta;
	}

	public Integer getAnoMes6Conta(){

		return anoMes6Conta;
	}

	public void setAnoMes6Conta(Integer anoMes6Conta){

		this.anoMes6Conta = anoMes6Conta;
	}

	public Integer getConsumo6Conta(){

		return consumo6Conta;
	}

	public void setConsumo6Conta(Integer consumo6Conta){

		this.consumo6Conta = consumo6Conta;
	}

	public Integer getConsumoMedio(){

		return consumoMedio;
	}

	public void setConsumoMedio(Integer consumoMedio){

		this.consumoMedio = consumoMedio;
	}

	public String getMsgConta(){

		return msgConta;
	}

	public void setMsgConta(String msgConta){

		this.msgConta = msgConta;
	}

	public Integer getQtdContasAviso(){

		return qtdContasAviso;
	}

	public void setQtdContasAviso(Integer qtdContasAviso){

		this.qtdContasAviso = qtdContasAviso;
	}

	public Date getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValorTotalConta(){

		return valorTotalConta;
	}

	public void setValorTotalConta(BigDecimal valorTotalConta){

		this.valorTotalConta = valorTotalConta;
	}

	public String getRepresentacaoNumericaCodBarraFormatada(){

		return representacaoNumericaCodBarraFormatada;
	}

	public void setRepresentacaoNumericaCodBarraFormatada(String representacaoNumericaCodBarraFormatada){

		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public Short getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(Short codigoRota){

		this.codigoRota = codigoRota;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	/**
	 * Gets the id attribute of the Imovel object
	 * Utilizado para documentos emitidos
	 * 
	 * @return The id value
	 */
	public String getIdParametrizado(){

		// Caso a matrícula tenha a quantidade de dígitos menor do que a cadastrada no parâmetro
		// P_NUMERO_DIGITOS_MATRICULA_IMOVEL adiciona zeros a esquerda do número
		String matriculaImovel = Util.retornaMatriculaImovelParametrizada(this.idImovel);

		return matriculaImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public List<ContaLinhasDescricaoServicosTarifasTotalHelper> getColecaoContaLinhasDescricaoServicosTarifasTotalHelper(){

		return colecaoContaLinhasDescricaoServicosTarifasTotalHelper;
	}

	public void setColecaoContaLinhasDescricaoServicosTarifasTotalHelper(
					List<ContaLinhasDescricaoServicosTarifasTotalHelper> colecaoContaLinhasDescricaoServicosTarifasTotalHelper){

		this.colecaoContaLinhasDescricaoServicosTarifasTotalHelper = colecaoContaLinhasDescricaoServicosTarifasTotalHelper;
	}

	public Short getIndicadorCodigoBarras(){

		return indicadorCodigoBarras;
	}

	public void setIndicadorCodigoBarras(Short indicadorCodigoBarras){

		this.indicadorCodigoBarras = indicadorCodigoBarras;
	}

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public Integer getIdImovelContaEnvio(){

		return idImovelContaEnvio;
	}

	public void setIdImovelContaEnvio(Integer idImovelContaEnvio){

		this.idImovelContaEnvio = idImovelContaEnvio;
	}

	public String getEnderecoClienteEntrega(){

		return enderecoClienteEntrega;
	}

	public void setEnderecoClienteEntrega(String enderecoClienteEntrega){

		this.enderecoClienteEntrega = enderecoClienteEntrega;
	}

	public Integer getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public Integer getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(Integer idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public Integer getConsumoAgua(){

		return consumoAgua;
	}

	public void setConsumoAgua(Integer consumoAgua){

		this.consumoAgua = consumoAgua;
	}

	public Integer getConsumoEsgoto(){

		return consumoEsgoto;
	}

	public void setConsumoEsgoto(Integer consumoEsgoto){

		this.consumoEsgoto = consumoEsgoto;
	}

	public String getDescricaoTipoConsumo(){

		return descricaoTipoConsumo;
	}

	public void setDescricaoTipoConsumo(String descricaoTipoConsumo){

		this.descricaoTipoConsumo = descricaoTipoConsumo;
	}

	public String getDescricaoAnormalidadeConsumo(){

		return descricaoAnormalidadeConsumo;
	}

	public void setDescricaoAnormalidadeConsumo(String descricaoAnormalidadeConsumo){

		this.descricaoAnormalidadeConsumo = descricaoAnormalidadeConsumo;
	}

	public Integer getConsumoFaturado(){

		return consumoFaturado;
	}

	public void setConsumoFaturado(Integer consumoFaturado){

		this.consumoFaturado = consumoFaturado;
	}

	public String getConsumoMedidoEstimado(){

		return consumoMedidoEstimado;
	}

	public void setConsumoMedidoEstimado(String consumoMedidoEstimado){

		this.consumoMedidoEstimado = consumoMedidoEstimado;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public BigDecimal getDebitos(){

		return debitos;
	}

	public void setDebitos(BigDecimal debitos){

		this.debitos = debitos;
	}

	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public BigDecimal getValorImpostos(){

		return valorImpostos;
	}

	public void setValorImpostos(BigDecimal valorImpostos){

		this.valorImpostos = valorImpostos;
	}

	public BigDecimal getPercentualEsgotoConta(){

		return percentualEsgotoConta;
	}

	public void setPercentualEsgotoConta(BigDecimal percentualEsgotoConta){

		this.percentualEsgotoConta = percentualEsgotoConta;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public String getNomeGerenciaRegional(){

		return nomeGerenciaRegional;
	}

	public void setNomeGerenciaRegional(String nomeGerenciaRegional){

		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}

	public Integer getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(Integer idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

	public Integer getIdEmpresa(){

		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	public Integer getIdRota(){

		return idRota;
	}

	public void setIdRota(Integer idRota){

		this.idRota = idRota;
	}

	public int getTotalContasImpressao(){

		return totalContasImpressao;
	}

	public void setTotalContasImpressao(int totalContasImpressao){

		this.totalContasImpressao = totalContasImpressao;
	}

	public List<EmitirContaTipo2Helper> getColecaoPaginasAdicionaisFatura(){

		return colecaoPaginasAdicionaisFatura;
	}

	public void setColecaoPaginasAdicionaisFatura(List<EmitirContaTipo2Helper> colecaoPaginasAdicionaisFatura){

		this.colecaoPaginasAdicionaisFatura = colecaoPaginasAdicionaisFatura;
	}

	public Short getIndicadorValorConta(){

		return indicadorValorConta;
	}

	public void setIndicadorValorConta(Short indicadorValorConta){

		this.indicadorValorConta = indicadorValorConta;
	}

	public Short getIndicadorVencimentoConta(){

		return indicadorVencimentoConta;
	}

	public void setIndicadorVencimentoConta(Short indicadorVencimentoConta){

		this.indicadorVencimentoConta = indicadorVencimentoConta;
	}

	public Short getIndicadorDebitoAutomatico(){

		return indicadorDebitoAutomatico;
	}

	public void setIndicadorDebitoAutomatico(Short indicadorDebitoAutomatico){

		this.indicadorDebitoAutomatico = indicadorDebitoAutomatico;
	}

	public Short getIndicadorFaturaInformativa(){

		return indicadorFaturaInformativa;
	}

	public void setIndicadorFaturaInformativa(Short indicadorFaturaInformativa){

		this.indicadorFaturaInformativa = indicadorFaturaInformativa;
	}

	public String getNomeImovel(){

		return nomeImovel;
	}

	public void setNomeImovel(String nomeImovel){

		this.nomeImovel = nomeImovel;
	}

	public Integer getIdClienteRelacaoTipo(){

		return idClienteRelacaoTipo;
	}

	public void setIdClienteRelacaoTipo(Integer idClienteRelacaoTipo){

		this.idClienteRelacaoTipo = idClienteRelacaoTipo;
	}

	public String getCepImovelFormatado(){

		return cepImovelFormatado;
	}

	public void setCepImovelFormatado(String cepImovelFormatado){

		this.cepImovelFormatado = cepImovelFormatado;
	}

	public Integer getIdLeituraSituacaoAtual(){

		return idLeituraSituacaoAtual;
	}

	public void setIdLeituraSituacaoAtual(Integer idLeituraSituacaoAtual){

		this.idLeituraSituacaoAtual = idLeituraSituacaoAtual;
	}

	public Integer getIdLeituraAnormalidadeFaturamento(){

		return idLeituraAnormalidadeFaturamento;
	}

	public void setIdLeituraAnormalidadeFaturamento(Integer idLeituraAnormalidadeFaturamento){

		this.idLeituraAnormalidadeFaturamento = idLeituraAnormalidadeFaturamento;
	}

	public Integer getIdConsumoTipo(){

		return idConsumoTipo;
	}

	public void setIdConsumoTipo(Integer idConsumoTipo){

		this.idConsumoTipo = idConsumoTipo;
	}

	public Integer getIdConsumoAnormalidade(){

		return idConsumoAnormalidade;
	}

	public void setIdConsumoAnormalidade(Integer idConsumoAnormalidade){

		this.idConsumoAnormalidade = idConsumoAnormalidade;
	}

	public Integer getIdTipoContrato(){

		return idTipoContrato;
	}

	public void setIdTipoContrato(Integer idTipoContrato){

		this.idTipoContrato = idTipoContrato;
	}


	public HidrometroLocalInstalacao getHidrometroLocalInstalacao(){

		return hidrometroLocalInstalacao;
	}

	public void setHidrometroLocalInstalacao(HidrometroLocalInstalacao hidrometroLocalInstalacao){

		this.hidrometroLocalInstalacao = hidrometroLocalInstalacao;
	}

	public String getGrupoImovelCategoriaResidencial(){

		return grupoImovelCategoriaResidencial;
	}

	public void setGrupoImovelCategoriaResidencial(String grupoImovelCategoriaResidencial){

		this.grupoImovelCategoriaResidencial = grupoImovelCategoriaResidencial;
	}

	public String getGrupoImovelCategoriaComercial(){

		return grupoImovelCategoriaComercial;
	}

	public void setGrupoImovelCategoriaComercial(String grupoImovelCategoriaComercial){

		this.grupoImovelCategoriaComercial = grupoImovelCategoriaComercial;
	}

	public String getGrupoImovelCategoriaPublica(){

		return grupoImovelCategoriaPublica;
	}

	public void setGrupoImovelCategoriaPublica(String grupoImovelCategoriaPublica){

		this.grupoImovelCategoriaPublica = grupoImovelCategoriaPublica;
	}

	public String getGrupoImovelCategoriaIndustrial(){

		return grupoImovelCategoriaIndustrial;
	}

	public void setGrupoImovelCategoriaIndustrial(String grupoImovelCategoriaIndustrial){

		this.grupoImovelCategoriaIndustrial = grupoImovelCategoriaIndustrial;
	}

	public Integer getIdClienteResponsavel(){

		return idClienteResponsavel;
	}

	public void setIdClienteResponsavel(Integer idClienteResponsavel){

		this.idClienteResponsavel = idClienteResponsavel;
	}

	public Short getIndicadorPossuiMedicaoHistorico(){

		return indicadorPossuiMedicaoHistorico;
	}

	public void setIndicadorPossuiMedicaoHistorico(Short indicadorPossuiMedicaoHistorico){

		this.indicadorPossuiMedicaoHistorico = indicadorPossuiMedicaoHistorico;
	}

	public int getIdTipoMedicao(){

		return idTipoMedicao;
	}

	public void setIdTipoMedicao(int idTipoMedicao){

		this.idTipoMedicao = idTipoMedicao;
	}

	public String getMsgContaParte1(){

		return msgContaParte1;
	}

	public void setMsgContaParte1(String msgContaParte1){

		this.msgContaParte1 = msgContaParte1;
	}

	/**
	 * @return the msgContaParte2
	 */
	public String getMsgContaParte2(){

		return msgContaParte2;
	}

	/**
	 * @param msgContaParte2
	 *            the msgContaParte2 to set
	 */
	public void setMsgContaParte2(String msgContaParte2){

		this.msgContaParte2 = msgContaParte2;
	}

	/**
	 * @return the msgContaParte3
	 */
	public String getMsgContaParte3(){

		return msgContaParte3;
	}

	/**
	 * @param msgContaParte3
	 *            the msgContaParte3 to set
	 */
	public void setMsgContaParte3(String msgContaParte3){

		this.msgContaParte3 = msgContaParte3;
	}

	public Short getDigitoVerificadorConta(){

		return digitoVerificadorConta;
	}

	public void setDigitoVerificadorConta(Short digitoVerificadorConta){

		this.digitoVerificadorConta = digitoVerificadorConta;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public Integer getCodigoElo(){

		return codigoElo;
	}

	public void setCodigoElo(Integer codigoElo){

		this.codigoElo = codigoElo;
	}

	public int getIndexDescricaoTarifasServicos(){

		return indexDescricaoTarifasServicos;
	}

	public void setIndexDescricaoTarifasServicos(int indexDescricaoTarifasServicos){

		this.indexDescricaoTarifasServicos = indexDescricaoTarifasServicos;
	}

	public Integer getIdClienteUsuario(){

		return idClienteUsuario;
	}

	public void setIdClienteUsuario(Integer idClienteUsuario){

		this.idClienteUsuario = idClienteUsuario;
	}

	public Integer getIdImovelPerfil(){

		return idImovelPerfil;
	}

	public void setIdImovelPerfil(Integer idImovelPerfil){

		this.idImovelPerfil = idImovelPerfil;
	}

	public List<EmitirContaServicoHelper> getListaServicos(){

		return listaServicos;
	}

	public void setListaServicos(List<EmitirContaServicoHelper> listaServicos){

		this.listaServicos = listaServicos;
	}

	public void addListaServicos(EmitirContaServicoHelper emitirContaServicoHelper){

		this.listaServicos.add(emitirContaServicoHelper);
	}

	public List<EmitirContaUltimosConsumosLeiturasHelper> getListaUltimosConsumosLeituras(){

		return listaUltimosConsumosLeituras;
	}

	public void setListaUltimosConsumosLeituras(List<EmitirContaUltimosConsumosLeiturasHelper> listaUltimosConsumosLeituras){

		this.listaUltimosConsumosLeituras = listaUltimosConsumosLeituras;
	}

	public void addUltimosConsumosLeituras(EmitirContaUltimosConsumosLeiturasHelper listaUltimosConsumosLeituras){

		this.listaUltimosConsumosLeituras.add(listaUltimosConsumosLeituras);
	}

	public Object[] getMensagemConta(){

		return mensagemConta;
	}

	public void setMensagemConta(Object[] mensagemConta){

		this.mensagemConta = mensagemConta;
	}

	public Date getDataRevisao(){

		return dataRevisao;
	}

	public void setDataRevisao(Date dataRevisao){

		this.dataRevisao = dataRevisao;
	}

	public Integer getIdMotivoRevisao(){

		return idMotivoRevisao;
	}

	public void setIdMotivoRevisao(Integer idMotivoRevisao){

		this.idMotivoRevisao = idMotivoRevisao;
	}

	public Cliente getClienteResponsavel(){

		return clienteResponsavel;
	}

	public void setClienteResponsavel(Cliente clienteResponsavel){

		this.clienteResponsavel = clienteResponsavel;
	}

	public Cliente getClienteUsuario(){

		return clienteUsuario;
	}

	public void setClienteUsuario(Cliente clienteUsuario){

		this.clienteUsuario = clienteUsuario;
	}

	public Date getDataInstalacaoHidrometro(){

		return dataInstalacaoHidrometro;
	}

	public void setDataInstalacaoHidrometro(Date dataInstalacaoHidrometro){

		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}

	public HidrometroMarca getHidrometroMarca(){

		return hidrometroMarca;
	}

	public void setHidrometroMarca(HidrometroMarca hidrometroMarca){

		this.hidrometroMarca = hidrometroMarca;
	}

	public HidrometroCapacidade getHidrometroCapacidade(){

		return hidrometroCapacidade;
	}

	public void setHidrometroCapacidade(HidrometroCapacidade hidrometroCapacidade){

		this.hidrometroCapacidade = hidrometroCapacidade;
	}

	public Integer getIdTipoLigacao(){

		return idTipoLigacao;
	}

	public void setIdTipoLigacao(Integer idTipoLigacao){

		this.idTipoLigacao = idTipoLigacao;
	}

	public Integer getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(Integer idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public int getConsumoMinimoLigacao(){

		return consumoMinimoLigacao;
	}

	public void setConsumoMinimoLigacao(int consumoMinimoLigacao){

		this.consumoMinimoLigacao = consumoMinimoLigacao;
	}


	// gets utilizados para ordenação da coleção de contas para impressao

	public Short getIndicadorContaBraille(){

		return indicadorContaBraille;
	}

	public void setIndicadorContaBraille(Short indicadorContaBraille){

		this.indicadorContaBraille = indicadorContaBraille;
	}

	public Integer getIdClienteResponsavelOrdenacao(){

		Integer retorno = 999999999;

		if(idClienteResponsavel != null){

			retorno = idClienteResponsavel;
		}

		return retorno;
	}
}
