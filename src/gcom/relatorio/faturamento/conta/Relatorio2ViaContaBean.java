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
 * Virgínia Melo
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

package gcom.relatorio.faturamento.conta;

import gcom.relatorio.RelatorioBean;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC0482] Emitir 2ª Via de Conta
 * 
 * @author Vivianne Sousa
 * @date 15/09/2006
 * @author Virgínia Melo
 * @date 06/01/2009 - 27/02/2009
 *       Adicionado campos de qualidade água
 *       Adicionado campo número hidrômetro
 * @author Yara Souza
 * @date 14/05/2010
 *       Alteração endereço do endereço cliente entrega.
 */
public class Relatorio2ViaContaBean
				implements RelatorioBean {

	private String indicadorPrimeiraPagina;

	private JRBeanCollectionDataSource arrayJRDetail;

	private ArrayList arrayRelatorio2ViaContaDetailBean;

	// Linha 2
	private String descricaoLocalidade;

	// Linha 3
	private String matriculaImovelFormatada;

	private String dataVencimento;

	// Linha 4
	private String nomeCliente;

	// Linha 5
	private String enderecoImovel;

	private String fatura;

	// Linha 6
	private String inscricao;

	// Linha 7
	private String enderecoClienteEntrega;

	private String descricaoAguaSituacao;

	private String descricaoEsgotoSituacao;

	// Linha 9
	private String dadosConsumoMes1;

	private String dadosConsumoMes4;

	// Linha 10
	private String dadosConsumoMes2;

	private String dadosConsumoMes5;

	private String leituraAnterior;

	private String leituraAtual;

	private String consumoFaturamento;

	private String diasConsumo;

	private String consumoMedioDiario;

	// Linha 11
	private String dadosConsumoMes3;

	private String dadosConsumoMes6;

	private String dataLeituraAnterior;

	private String dataLeituraAtual;

	// Linha 12
	private String descricaoTipoConsumo;

	private String descricaoAnormalidadeConsumo;

	// Linha 13
	private String quantidadeEconomiaConta;

	private String consumoEconomia;

	private String codigoAuxiliarString;

	private String mesagemConsumoString;

	// Linha 17
	private String valorContaString;

	// Linha 18
	private String primeiraParte;

	// Linha 19
	private String segundaParte;

	// Linha 20
	private String terceiraParte;

	// Linha 21
	private String nomeGerenciaRegional;

	private String mesAnoFormatado;

	// Linha 22
	private String numeroIndiceTurbidez;

	private String numeroCloroResidual;

	// Linha 24
	private String representacaoNumericaCodBarraFormatada;

	// Linha 25
	private String representacaoNumericaCodBarraSemDigito;

	// Linha28
	private String dataValidade;

	// Linha 31
	private String grupo;

	private String firma;

	private String idConta;

	private String totalPaginasRelatorio;

	private String contaSemCodigoBarras;

	private String numeroHidrometro;

	// Outras informações qualidade água
	private String numeroAmostrasMediaTurbidez;

	private String numeroAmostrasMediaCloro;

	private String numeroAmostrasMediaCor;

	private String numeroAmostrasMediaPH;

	private String numeroAmostrasMediaBacteriasHeterotroficas;

	private String numeroAmostrasMediaColiformesTermotolerantes;

	private String numeroAmostrasMediaColiformesTotais;

	// só aparece na CAERN
	private String rotaEntrega;

	private String debitoCreditoSituacaoAtualConta;

	private String contaPaga;

	private String contaMotivoRetificacao;

	private String funcionario;

	private String tipoDocCliente;

	private String cpfCnpjCliente;

	private String dataEmissaoConta;

	private String nomeRelatorio;

	private String indicadorExibirAcrescimos;

	private String consumoMedido;

	public Relatorio2ViaContaBean(String indicadorPrimeiraPagina, Collection colecaoDetail, String descricaoLocalidade,
									String matriculaImovelFormatada, String dataVencimento, String nomeCliente, String enderecoImovel,
									String fatura, String inscricao, String enderecoClienteEntrega, String descricaoAguaSituacao,
									String descricaoEsgotoSituacao, String dadosConsumoMes1, String dadosConsumoMes4,
									String dadosConsumoMes2, String dadosConsumoMes5, String leituraAnterior, String leituraAtual,
									String consumoFaturamento, String diasConsumo, String consumoMedioDiario, String dadosConsumoMes3,
									String dadosConsumoMes6, String dataLeituraAnterior, String dataLeituraAtual,
									String descricaoTipoConsumo, String descricaoAnormalidadeConsumo, String quantidadeEconomiaConta,
									String consumoEconomia, String codigoAuxiliarString, String mesagemConsumoString,
									String valorContaString, String primeiraParte, String segundaParte, String terceiraParte,
									String nomeGerenciaRegional, String mesAnoFormatado, String numeroIndiceTurbidez,
									String numeroCloroResidual, String representacaoNumericaCodBarraFormatada,
									String representacaoNumericaCodBarraSemDigito, String dataValidade, String grupo, String firma,
									String totalPaginasRelatorio, String idConta, String rotaEntrega, String contaSemCodigoBarras,
									String debitoCreditoSituacaoAtualConta, String contaPaga, String numeroAmostrasMediaTurbidez,
									String numeroAmostrasMediaCloro, String numeroAmostrasMediaCor, String numeroAmostrasMediaPH,
									String numeroAmostrasMediaBacteriasHeterotroficas, String numeroAmostrasMediaColiformesTermotolerantes,
									String numeroAmostrasMediaColiformesTotais, String numeroHidrometro, String contaMotivoRetificacao,
									String funcionario, String consumoMedido) {

		this.indicadorPrimeiraPagina = indicadorPrimeiraPagina;
		this.arrayRelatorio2ViaContaDetailBean = new ArrayList();
		this.arrayRelatorio2ViaContaDetailBean.addAll(colecaoDetail);
		this.arrayJRDetail = new JRBeanCollectionDataSource(this.arrayRelatorio2ViaContaDetailBean);

		this.descricaoLocalidade = descricaoLocalidade;
		this.matriculaImovelFormatada = matriculaImovelFormatada;
		this.dataVencimento = dataVencimento;
		this.nomeCliente = nomeCliente;
		this.enderecoImovel = enderecoImovel;
		this.fatura = fatura;
		this.inscricao = inscricao;
		this.enderecoClienteEntrega = enderecoClienteEntrega;
		this.descricaoAguaSituacao = descricaoAguaSituacao;
		this.descricaoEsgotoSituacao = descricaoEsgotoSituacao;
		this.dadosConsumoMes1 = dadosConsumoMes1;
		this.dadosConsumoMes4 = dadosConsumoMes4;
		this.dadosConsumoMes2 = dadosConsumoMes2;
		this.dadosConsumoMes5 = dadosConsumoMes5;
		this.leituraAnterior = leituraAnterior;
		this.leituraAtual = leituraAtual;
		this.consumoFaturamento = consumoFaturamento;
		this.diasConsumo = diasConsumo;
		this.consumoMedioDiario = consumoMedioDiario;
		this.dadosConsumoMes3 = dadosConsumoMes3;
		this.dadosConsumoMes6 = dadosConsumoMes6;
		this.dataLeituraAnterior = dataLeituraAnterior;
		this.dataLeituraAtual = dataLeituraAtual;
		this.descricaoTipoConsumo = descricaoTipoConsumo;
		this.descricaoAnormalidadeConsumo = descricaoAnormalidadeConsumo;
		this.quantidadeEconomiaConta = quantidadeEconomiaConta;
		this.consumoEconomia = consumoEconomia;
		this.codigoAuxiliarString = codigoAuxiliarString;
		this.mesagemConsumoString = mesagemConsumoString;
		this.valorContaString = valorContaString;
		this.primeiraParte = primeiraParte;
		this.segundaParte = segundaParte;
		this.terceiraParte = terceiraParte;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.mesAnoFormatado = mesAnoFormatado;
		this.numeroIndiceTurbidez = numeroIndiceTurbidez;
		this.numeroCloroResidual = numeroCloroResidual;
		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
		this.dataValidade = dataValidade;
		this.grupo = grupo;
		this.firma = firma;
		this.totalPaginasRelatorio = totalPaginasRelatorio;
		this.idConta = idConta;
		this.rotaEntrega = rotaEntrega;
		this.contaSemCodigoBarras = contaSemCodigoBarras;
		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
		this.contaPaga = contaPaga;
		this.numeroAmostrasMediaTurbidez = numeroAmostrasMediaTurbidez;
		this.numeroAmostrasMediaCloro = numeroAmostrasMediaCloro;
		this.numeroAmostrasMediaCor = numeroAmostrasMediaCor;
		this.numeroAmostrasMediaPH = numeroAmostrasMediaPH;
		this.numeroAmostrasMediaBacteriasHeterotroficas = numeroAmostrasMediaBacteriasHeterotroficas;
		this.numeroAmostrasMediaColiformesTermotolerantes = numeroAmostrasMediaColiformesTermotolerantes;
		this.numeroAmostrasMediaColiformesTotais = numeroAmostrasMediaColiformesTotais;
		this.numeroHidrometro = numeroHidrometro;
		this.contaMotivoRetificacao = contaMotivoRetificacao;
		this.funcionario = funcionario;
		this.consumoMedido = consumoMedido;

	}

	public JRBeanCollectionDataSource getArrayJRDetail(){

		return arrayJRDetail;
	}

	public void setArrayJRDetail(JRBeanCollectionDataSource arrayJRDetail){

		this.arrayJRDetail = arrayJRDetail;
	}

	public ArrayList getArrayRelatorio2ViaContaDetailBean(){

		return arrayRelatorio2ViaContaDetailBean;
	}

	public void setArrayRelatorio2ViaContaDetailBean(ArrayList arrayRelatorio2ViaContaDetailBean){

		this.arrayRelatorio2ViaContaDetailBean = arrayRelatorio2ViaContaDetailBean;
	}

	public String getIndicadorPrimeiraPagina(){

		return indicadorPrimeiraPagina;
	}

	public void setIndicadorPrimeiraPagina(String indicadorPrimeiraPagina){

		this.indicadorPrimeiraPagina = indicadorPrimeiraPagina;
	}

	public String getCodigoAuxiliarString(){

		return codigoAuxiliarString;
	}

	public void setCodigoAuxiliarString(String codigoAuxiliarString){

		this.codigoAuxiliarString = codigoAuxiliarString;
	}

	public String getConsumoEconomia(){

		return consumoEconomia;
	}

	public void setConsumoEconomia(String consumoEconomia){

		this.consumoEconomia = consumoEconomia;
	}

	public String getConsumoFaturamento(){

		return consumoFaturamento;
	}

	public void setConsumoFaturamento(String consumoFaturamento){

		this.consumoFaturamento = consumoFaturamento;
	}

	public String getConsumoMedioDiario(){

		return consumoMedioDiario;
	}

	public void setConsumoMedioDiario(String consumoMedioDiario){

		this.consumoMedioDiario = consumoMedioDiario;
	}

	public String getDadosConsumoMes1(){

		return dadosConsumoMes1;
	}

	public void setDadosConsumoMes1(String dadosConsumoMes1){

		this.dadosConsumoMes1 = dadosConsumoMes1;
	}

	public String getDadosConsumoMes2(){

		return dadosConsumoMes2;
	}

	public void setDadosConsumoMes2(String dadosConsumoMes2){

		this.dadosConsumoMes2 = dadosConsumoMes2;
	}

	public String getDadosConsumoMes3(){

		return dadosConsumoMes3;
	}

	public void setDadosConsumoMes3(String dadosConsumoMes3){

		this.dadosConsumoMes3 = dadosConsumoMes3;
	}

	public String getDadosConsumoMes4(){

		return dadosConsumoMes4;
	}

	public void setDadosConsumoMes4(String dadosConsumoMes4){

		this.dadosConsumoMes4 = dadosConsumoMes4;
	}

	public String getDadosConsumoMes5(){

		return dadosConsumoMes5;
	}

	public void setDadosConsumoMes5(String dadosConsumoMes5){

		this.dadosConsumoMes5 = dadosConsumoMes5;
	}

	public String getDadosConsumoMes6(){

		return dadosConsumoMes6;
	}

	public void setDadosConsumoMes6(String dadosConsumoMes6){

		this.dadosConsumoMes6 = dadosConsumoMes6;
	}

	public String getDataLeituraAnterior(){

		return dataLeituraAnterior;
	}

	public void setDataLeituraAnterior(String dataLeituraAnterior){

		this.dataLeituraAnterior = dataLeituraAnterior;
	}

	public String getDataLeituraAtual(){

		return dataLeituraAtual;
	}

	public void setDataLeituraAtual(String dataLeituraAtual){

		this.dataLeituraAtual = dataLeituraAtual;
	}

	public String getDataValidade(){

		return dataValidade;
	}

	public void setDataValidade(String dataValidade){

		this.dataValidade = dataValidade;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String getDescricaoAguaSituacao(){

		return descricaoAguaSituacao;
	}

	public void setDescricaoAguaSituacao(String descricaoAguaSituacao){

		this.descricaoAguaSituacao = descricaoAguaSituacao;
	}

	public String getDescricaoAnormalidadeConsumo(){

		return descricaoAnormalidadeConsumo;
	}

	public void setDescricaoAnormalidadeConsumo(String descricaoAnormalidadeConsumo){

		this.descricaoAnormalidadeConsumo = descricaoAnormalidadeConsumo;
	}

	public String getDescricaoEsgotoSituacao(){

		return descricaoEsgotoSituacao;
	}

	public void setDescricaoEsgotoSituacao(String descricaoEsgotoSituacao){

		this.descricaoEsgotoSituacao = descricaoEsgotoSituacao;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getDescricaoTipoConsumo(){

		return descricaoTipoConsumo;
	}

	public void setDescricaoTipoConsumo(String descricaoTipoConsumo){

		this.descricaoTipoConsumo = descricaoTipoConsumo;
	}

	public String getDiasConsumo(){

		return diasConsumo;
	}

	public void setDiasConsumo(String diasConsumo){

		this.diasConsumo = diasConsumo;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getFatura(){

		return fatura;
	}

	public void setFatura(String fatura){

		this.fatura = fatura;
	}

	public String getFirma(){

		return firma;
	}

	public void setFirma(String firma){

		this.firma = firma;
	}

	public String getGrupo(){

		return grupo;
	}

	public void setGrupo(String grupo){

		this.grupo = grupo;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(String leituraAnterior){

		this.leituraAnterior = leituraAnterior;
	}

	public String getLeituraAtual(){

		return leituraAtual;
	}

	public void setLeituraAtual(String leituraAtual){

		this.leituraAtual = leituraAtual;
	}

	public String getMatriculaImovelFormatada(){

		return matriculaImovelFormatada;
	}

	public void setMatriculaImovelFormatada(String matriculaImovelFormatada){

		this.matriculaImovelFormatada = matriculaImovelFormatada;
	}

	public String getMesagemConsumoString(){

		return mesagemConsumoString;
	}

	public void setMesagemConsumoString(String mesagemConsumoString){

		this.mesagemConsumoString = mesagemConsumoString;
	}

	public String getMesAnoFormatado(){

		return mesAnoFormatado;
	}

	public void setMesAnoFormatado(String mesAnoFormatado){

		this.mesAnoFormatado = mesAnoFormatado;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNomeGerenciaRegional(){

		return nomeGerenciaRegional;
	}

	public void setNomeGerenciaRegional(String nomeGerenciaRegional){

		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}

	public String getNumeroCloroResidual(){

		return numeroCloroResidual;
	}

	public void setNumeroCloroResidual(String numeroCloroResidual){

		this.numeroCloroResidual = numeroCloroResidual;
	}

	public String getNumeroIndiceTurbidez(){

		return numeroIndiceTurbidez;
	}

	public void setNumeroIndiceTurbidez(String numeroIndiceTurbidez){

		this.numeroIndiceTurbidez = numeroIndiceTurbidez;
	}

	public String getPrimeiraParte(){

		return primeiraParte;
	}

	public void setPrimeiraParte(String primeiraParte){

		this.primeiraParte = primeiraParte;
	}

	public String getQuantidadeEconomiaConta(){

		return quantidadeEconomiaConta;
	}

	public void setQuantidadeEconomiaConta(String quantidadeEconomiaConta){

		this.quantidadeEconomiaConta = quantidadeEconomiaConta;
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

	public String getSegundaParte(){

		return segundaParte;
	}

	public void setSegundaParte(String segundaParte){

		this.segundaParte = segundaParte;
	}

	public String getTerceiraParte(){

		return terceiraParte;
	}

	public void setTerceiraParte(String terceiraParte){

		this.terceiraParte = terceiraParte;
	}

	public String getValorContaString(){

		return valorContaString;
	}

	public void setValorContaString(String valorContaString){

		this.valorContaString = valorContaString;
	}

	public String getTotalPaginasRelatorio(){

		return totalPaginasRelatorio;
	}

	public void setTotalPaginasRelatorio(String totalPaginasRelatorio){

		this.totalPaginasRelatorio = totalPaginasRelatorio;
	}

	public String getIdConta(){

		return idConta;
	}

	public void setIdConta(String idConta){

		this.idConta = idConta;
	}

	public String getRotaEntrega(){

		return rotaEntrega;
	}

	public void setRotaEntrega(String rotaEntrega){

		this.rotaEntrega = rotaEntrega;
	}

	public String getContaSemCodigoBarras(){

		return contaSemCodigoBarras;
	}

	public void setContaSemCodigoBarras(String contaSemCodigoBarras){

		this.contaSemCodigoBarras = contaSemCodigoBarras;
	}

	public String getDebitoCreditoSituacaoAtualConta(){

		return debitoCreditoSituacaoAtualConta;
	}

	public void setDebitoCreditoSituacaoAtualConta(String debitoCreditoSituacaoAtualConta){

		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
	}

	public String getContaPaga(){

		return contaPaga;
	}

	public void setContaPaga(String contaPaga){

		this.contaPaga = contaPaga;
	}

	public String getNumeroAmostrasMediaTurbidez(){

		return numeroAmostrasMediaTurbidez;
	}

	public void setNumeroAmostrasMediaTurbidez(String numeroAmostrasMediaTurbidez){

		this.numeroAmostrasMediaTurbidez = numeroAmostrasMediaTurbidez;
	}

	public String getNumeroAmostrasMediaCloro(){

		return numeroAmostrasMediaCloro;
	}

	public void setNumeroAmostrasMediaCloro(String numeroAmostrasMediaCloro){

		this.numeroAmostrasMediaCloro = numeroAmostrasMediaCloro;
	}

	public String getNumeroAmostrasMediaCor(){

		return numeroAmostrasMediaCor;
	}

	public void setNumeroAmostrasMediaCor(String numeroAmostrasMediaCor){

		this.numeroAmostrasMediaCor = numeroAmostrasMediaCor;
	}

	public String getNumeroAmostrasMediaPH(){

		return numeroAmostrasMediaPH;
	}

	public void setNumeroAmostrasMediaPH(String numeroAmostrasMediaPH){

		this.numeroAmostrasMediaPH = numeroAmostrasMediaPH;
	}

	public String getNumeroAmostrasMediaBacteriasHeterotroficas(){

		return numeroAmostrasMediaBacteriasHeterotroficas;
	}

	public void setNumeroAmostrasMediaBacteriasHeterotroficas(String numeroAmostrasMediaBacteriasHeterotroficas){

		this.numeroAmostrasMediaBacteriasHeterotroficas = numeroAmostrasMediaBacteriasHeterotroficas;
	}

	public String getNumeroAmostrasMediaColiformesTermotolerantes(){

		return numeroAmostrasMediaColiformesTermotolerantes;
	}

	public void setNumeroAmostrasMediaColiformesTermotolerantes(String numeroAmostrasMediaColiformesTermotolerantes){

		this.numeroAmostrasMediaColiformesTermotolerantes = numeroAmostrasMediaColiformesTermotolerantes;
	}

	public String getNumeroAmostrasMediaColiformesTotais(){

		return numeroAmostrasMediaColiformesTotais;
	}

	public void setNumeroAmostrasMediaColiformesTotais(String numeroAmostrasMediaColiformesTotais){

		this.numeroAmostrasMediaColiformesTotais = numeroAmostrasMediaColiformesTotais;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getEnderecoClienteEntrega(){

		return enderecoClienteEntrega;
	}

	public void setEnderecoClienteEntrega(String enderecoClienteEntrega){

		this.enderecoClienteEntrega = enderecoClienteEntrega;
	}

	public String getContaMotivoRetificacao(){

		return contaMotivoRetificacao;
	}

	public void setContaMotivoRetificacao(String contaMotivoRetificacao){

		this.contaMotivoRetificacao = contaMotivoRetificacao;
	}

	public String getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(String funcionario){

		this.funcionario = funcionario;
	}

	public String getTipoDocCliente(){

		return tipoDocCliente;
	}

	public void setTipoDocCliente(String tipoDocCliente){

		this.tipoDocCliente = tipoDocCliente;
	}

	public String getCpfCnpjCliente(){

		return cpfCnpjCliente;
	}

	public void setCpfCnpjCliente(String cpfCnpjCliente){

		this.cpfCnpjCliente = cpfCnpjCliente;
	}

	public String getDataEmissaoConta(){

		return dataEmissaoConta;
	}

	public void setDataEmissaoConta(String dataEmissaoConta){

		this.dataEmissaoConta = dataEmissaoConta;
	}

	public String getNomeRelatorio(){

		return nomeRelatorio;
	}

	public void setNomeRelatorio(String nomeRelatorio){

		this.nomeRelatorio = nomeRelatorio;
	}

	public String getIndicadorExibirAcrescimos(){

		return indicadorExibirAcrescimos;
	}

	public void setIndicadorExibirAcrescimos(String indicadorExibirAcrescimos){

		this.indicadorExibirAcrescimos = indicadorExibirAcrescimos;
	}

	public String getConsumoMedido(){

		return consumoMedido;
	}

	public void setConsumoMedido(String consumoMedido){

		this.consumoMedido = consumoMedido;
	}

}
