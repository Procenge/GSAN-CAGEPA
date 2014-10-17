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
 */

package gcom.micromedicao.medicao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.Rota;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraFaixaFalsa;
import gcom.micromedicao.leitura.LeituraFiscalizacao;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class MedicaoHistorico
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO = 629; // Operacao.OPERACAO_ATUALIZAR_EXECECOES_LEITURAS_RESUMO;

	public static final int OPERACAO_ALTERAR_DADOS_FATURAMENTO = 612; // Operacao.OPERACAO_ALTERAR_DADOS_FATURAMENTO

	public static final int OPERACAO_CONSULTAR_DADOS_FATURAMENTO = 436; // Operacao.OPERACAO_CONSULTAR_DADOS_FATURAMENTO

	public static final int ATRIBUTOS_ATUALIZAR_LEITURAS_ANTERIORES = 219577;

	public static final int ATRIBUTOS_RETIFICAR_CONTA = 261;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();

		filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ID, this.getId()));
		filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel");

		return filtroMedicaoHistorico;
	}

	/** identifier field */
	private Integer id;

	/** persistent field */
	private int anoMesReferencia;

	/** nullable persistent field */
	private Short numeroVezesConsecutivasOcorrenciaAnormalidade;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Date dataLeituraAnteriorFaturamento;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO, ATRIBUTOS_ATUALIZAR_LEITURAS_ANTERIORES, ATRIBUTOS_RETIFICAR_CONTA})
	private Integer leituraAnteriorFaturamento;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Integer leituraAnteriorInformada;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Date dataLeituraAtualInformada;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Integer leituraAtualInformada;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Date dataLeituraAtualFaturamento;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_LEITURAS_ANTERIORES, ATRIBUTOS_RETIFICAR_CONTA})
	private Integer leituraAtualFaturamento;

	/** nullable persistent field */
	private Integer numeroConsumoMes;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Integer numeroConsumoInformado;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Integer consumoMedioHidrometro;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Integer consumoCreditoAnterior;

	/** nullable persistent field */
	private LeituraFaixaFalsa leituraFaixaFalsa;

	/** nullable persistent field */
	private LeituraFiscalizacao leituraFiscalizacao;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private LeituraSituacao leituraSituacaoAnterior;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private LeituraSituacao leituraSituacaoAtual;

	/** persistent field */
	private HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private LeituraAnormalidade leituraAnormalidadeFaturamento;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private LeituraAnormalidade leituraAnormalidadeInformada;

	/** persistent field */
	private LigacaoAgua ligacaoAgua;

	/** persistent field */
	private Funcionario funcionario;

	/** persistent field */
	private gcom.micromedicao.medicao.MedicaoTipo medicaoTipo;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_EXECECOES_LEITURAS_RESUMO, OPERACAO_ALTERAR_DADOS_FATURAMENTO, OPERACAO_CONSULTAR_DADOS_FATURAMENTO})
	private Integer consumoCreditoGerado;

	private Date leituraProcessamentoMovimento;

	// Criados para o relat�rios de anormalidade e leituras
	private Localidade localidade;

	private Rota rota;

	private Short permiteAlterarConsumoMedio;

	private Integer consumoMedioAux;

	/**
	 * [UC0082] - INICIO Registrar Leituras e Anormalidades Autor: S�vio Luiz
	 * Data: 04/01/2006
	 * Autor : eduardo henrique
	 * Data : 11/09/2008
	 */
	// respons�vel para criar uma string com o(s) motivo(s) das leituras e
	// anormalidades n�o serem registradas no banco.
	private String gerarRelatorio;

	// respons�vel para criar uma string com a data de leitura do txt
	// para quando chegar no controlador saber se a data � valida ou n�o.
	private String dataLeituraParaRegistrar;

	// respons�vel para criar uma string com o indicador de confirma��o de
	// leitura do txt
	// para quando chegar no controlador saber se o indicador � 0 ou 1.
	private String indicadorConfirmacaoLeitura;

	// armazena o valor do consumo faturado para �gua, se for o caso. utilizado no [UC0082]
	private Integer numeroConsumoFaturadoLeitura;

	// armazena o valor do consumo faturado para �gua, se for o caso. utilizado no [UC0082]
	private Integer numeroConsumoFaturadoAguaLeitura;

	// armazena o valor do consumo faturado para esgoto, se for o caso. utilizado no [UC0082]
	private Integer numeroConsumoFaturadoEsgotoLeitura;

	public final static Short INDICADOR_CONFIRMACAO_LEITURA_ZERO = Short.valueOf("0");

	public final static Short INDICADOR_CONFIRMACAO_LEITURA_UM = Short.valueOf("1");

	public final static Short INDICADOR_LEITURA_CONFIRMADA = Short.valueOf("1");

	public final static Short INDICADOR_LEITURA_NAO_CONFIRMADA = Short.valueOf("2");

	/**
	 * [UC0082] - FIM Registrar Leituras e Anormalidades Autor: S�vio Luiz Data:
	 * 04/01/2006
	 */

	/**
	 * full constructor
	 * 
	 * @param consumoCreditoGerado
	 *            TODO
	 */
	public MedicaoHistorico(int anoMesReferencia, Short numeroVezesConsecutivasOcorrenciaAnormalidade, Date dataLeituraAnteriorFaturamento,
							Integer leituraAnteriorFaturamento, Integer leituraAnteriorInformada, Date dataLeituraAtualInformada,
							Integer leituraAtualInformada, Date dataLeituraAtualFaturamento, Integer leituraAtualFaturamento,
							Integer numeroConsumoMes, Integer numeroConsumoInformado, Integer consumoCreditoAnterior, Date ultimaAlteracao,
							Integer consumoMedioHidrometro, Date leituraProcessamentoMovimento, LeituraFaixaFalsa leituraFaixaFalsa,
							LeituraFiscalizacao leituraFiscalizacao, Imovel imovel, LeituraSituacao leituraSituacaoAnterior,
							LeituraSituacao leituraSituacaoAtual, HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
							LeituraAnormalidade leituraAnormalidadeFaturamento, LeituraAnormalidade leituraAnormalidadeInformada,
							LigacaoAgua ligacaoAgua, Funcionario funcionario, gcom.micromedicao.medicao.MedicaoTipo medicaoTipo,
							Integer consumoCreditoGerado) {

		this.anoMesReferencia = anoMesReferencia;
		this.numeroVezesConsecutivasOcorrenciaAnormalidade = numeroVezesConsecutivasOcorrenciaAnormalidade;
		this.dataLeituraAnteriorFaturamento = dataLeituraAnteriorFaturamento;
		this.leituraAnteriorFaturamento = leituraAnteriorFaturamento;
		this.leituraAnteriorInformada = leituraAnteriorInformada;
		this.dataLeituraAtualInformada = dataLeituraAtualInformada;
		this.leituraAtualInformada = leituraAtualInformada;
		this.dataLeituraAtualFaturamento = dataLeituraAtualFaturamento;
		this.leituraAtualFaturamento = leituraAtualFaturamento;
		this.numeroConsumoMes = numeroConsumoMes;
		this.numeroConsumoInformado = numeroConsumoInformado;
		this.ultimaAlteracao = ultimaAlteracao;
		this.consumoMedioHidrometro = consumoMedioHidrometro;
		this.leituraFaixaFalsa = leituraFaixaFalsa;
		this.leituraFiscalizacao = leituraFiscalizacao;
		this.imovel = imovel;
		this.leituraSituacaoAnterior = leituraSituacaoAnterior;
		this.leituraSituacaoAtual = leituraSituacaoAtual;
		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
		this.leituraAnormalidadeFaturamento = leituraAnormalidadeFaturamento;
		this.leituraAnormalidadeInformada = leituraAnormalidadeInformada;
		this.ligacaoAgua = ligacaoAgua;
		this.funcionario = funcionario;
		this.medicaoTipo = medicaoTipo;
		this.leituraProcessamentoMovimento = leituraProcessamentoMovimento;
		this.consumoCreditoAnterior = consumoCreditoAnterior;
		this.consumoCreditoGerado = consumoCreditoGerado;
	}

	/** default constructor */
	public MedicaoHistorico() {

	}

	/** minimal constructor */
	public MedicaoHistorico(int anoMesReferencia, Date dataLeituraAnteriorFaturamento, Integer leituraAnteriorFaturamento,
							Date dataLeituraAtualInformada, Integer leituraAtualInformada, Date dataLeituraAtualFaturamento,
							Integer leituraAtualFaturamento, Imovel imovel, LeituraSituacao leituraSituacaoAnterior,
							LeituraSituacao leituraSituacaoAtual, HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
							LeituraAnormalidade leituraAnormalidadeFaturamento, LeituraAnormalidade leituraAnormalidadeInformada,
							LigacaoAgua ligacaoAgua, Funcionario funcionario, gcom.micromedicao.medicao.MedicaoTipo medicaoTipo) {

		this.anoMesReferencia = anoMesReferencia;
		this.dataLeituraAnteriorFaturamento = dataLeituraAnteriorFaturamento;
		this.leituraAnteriorFaturamento = leituraAnteriorFaturamento;
		this.dataLeituraAtualInformada = dataLeituraAtualInformada;
		this.leituraAtualInformada = leituraAtualInformada;
		this.dataLeituraAtualFaturamento = dataLeituraAtualFaturamento;
		this.leituraAtualFaturamento = leituraAtualFaturamento;
		this.imovel = imovel;
		this.leituraSituacaoAnterior = leituraSituacaoAnterior;
		this.leituraSituacaoAtual = leituraSituacaoAtual;
		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
		this.leituraAnormalidadeFaturamento = leituraAnormalidadeFaturamento;
		this.leituraAnormalidadeInformada = leituraAnormalidadeInformada;
		this.ligacaoAgua = ligacaoAgua;
		this.funcionario = funcionario;
		this.medicaoTipo = medicaoTipo;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public int getAnoMesReferencia(){

		return this.anoMesReferencia;
	}

	public void setAnoMesReferencia(int anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Short getNumeroVezesConsecutivasOcorrenciaAnormalidade(){

		return this.numeroVezesConsecutivasOcorrenciaAnormalidade;
	}

	public void setNumeroVezesConsecutivasOcorrenciaAnormalidade(Short numeroVezesConsecutivasOcorrenciaAnormalidade){

		this.numeroVezesConsecutivasOcorrenciaAnormalidade = numeroVezesConsecutivasOcorrenciaAnormalidade;
	}

	public Date getDataLeituraAnteriorFaturamento(){

		return this.dataLeituraAnteriorFaturamento;
	}

	public void setDataLeituraAnteriorFaturamento(Date dataLeituraAnteriorFaturamento){

		this.dataLeituraAnteriorFaturamento = dataLeituraAnteriorFaturamento;
	}

	public Integer getLeituraAnteriorFaturamento(){

		return this.leituraAnteriorFaturamento;
	}

	public void setLeituraAnteriorFaturamento(Integer leituraAnteriorFaturamento){

		this.leituraAnteriorFaturamento = leituraAnteriorFaturamento;
	}

	public Integer getLeituraAnteriorInformada(){

		return this.leituraAnteriorInformada;
	}

	public void setLeituraAnteriorInformada(Integer leituraAnteriorInformada){

		this.leituraAnteriorInformada = leituraAnteriorInformada;
	}

	public Date getDataLeituraAtualInformada(){

		return this.dataLeituraAtualInformada;
	}

	public void setDataLeituraAtualInformada(Date dataLeituraAtualInformada){

		this.dataLeituraAtualInformada = dataLeituraAtualInformada;
	}

	public Integer getLeituraAtualInformada(){

		return this.leituraAtualInformada;
	}

	public void setLeituraAtualInformada(Integer leituraAtualInformada){

		this.leituraAtualInformada = leituraAtualInformada;
	}

	public Date getDataLeituraAtualFaturamento(){

		return this.dataLeituraAtualFaturamento;
	}

	public void setDataLeituraAtualFaturamento(Date dataLeituraAtualFaturamento){

		this.dataLeituraAtualFaturamento = dataLeituraAtualFaturamento;
	}

	public Integer getLeituraAtualFaturamento(){

		return this.leituraAtualFaturamento;
	}

	public void setLeituraAtualFaturamento(Integer leituraAtualFaturamento){

		this.leituraAtualFaturamento = leituraAtualFaturamento;
	}

	public Integer getNumeroConsumoMes(){

		return this.numeroConsumoMes;
	}

	public void setNumeroConsumoMes(Integer numeroConsumoMes){

		this.numeroConsumoMes = numeroConsumoMes;
	}

	public Integer getNumeroConsumoInformado(){

		return this.numeroConsumoInformado;
	}

	public void setNumeroConsumoInformado(Integer numeroConsumoInformado){

		this.numeroConsumoInformado = numeroConsumoInformado;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getConsumoMedioHidrometro(){

		return this.consumoMedioHidrometro;
	}

	public void setConsumoMedioHidrometro(Integer consumoMedioHidrometro){

		this.consumoMedioHidrometro = consumoMedioHidrometro;
	}

	public LeituraFaixaFalsa getLeituraFaixaFalsa(){

		return this.leituraFaixaFalsa;
	}

	public void setLeituraFaixaFalsa(LeituraFaixaFalsa leituraFaixaFalsa){

		this.leituraFaixaFalsa = leituraFaixaFalsa;
	}

	public LeituraFiscalizacao getLeituraFiscalizacao(){

		return this.leituraFiscalizacao;
	}

	public void setLeituraFiscalizacao(LeituraFiscalizacao leituraFiscalizacao){

		this.leituraFiscalizacao = leituraFiscalizacao;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public LeituraSituacao getLeituraSituacaoAnterior(){

		return this.leituraSituacaoAnterior;
	}

	public void setLeituraSituacaoAnterior(LeituraSituacao leituraSituacaoAnterior){

		this.leituraSituacaoAnterior = leituraSituacaoAnterior;
	}

	public LeituraSituacao getLeituraSituacaoAtual(){

		return this.leituraSituacaoAtual;
	}

	public void setLeituraSituacaoAtual(LeituraSituacao leituraSituacaoAtual){

		this.leituraSituacaoAtual = leituraSituacaoAtual;
	}

	public HidrometroInstalacaoHistorico getHidrometroInstalacaoHistorico(){

		return this.hidrometroInstalacaoHistorico;
	}

	public void setHidrometroInstalacaoHistorico(HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico){

		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
	}

	public LeituraAnormalidade getLeituraAnormalidadeFaturamento(){

		return this.leituraAnormalidadeFaturamento;
	}

	public void setLeituraAnormalidadeFaturamento(LeituraAnormalidade leituraAnormalidadeFaturamento){

		this.leituraAnormalidadeFaturamento = leituraAnormalidadeFaturamento;
	}

	public LeituraAnormalidade getLeituraAnormalidadeInformada(){

		return this.leituraAnormalidadeInformada;
	}

	public void setLeituraAnormalidadeInformada(LeituraAnormalidade leituraAnormalidadeInformada){

		this.leituraAnormalidadeInformada = leituraAnormalidadeInformada;
	}

	public LigacaoAgua getLigacaoAgua(){

		return this.ligacaoAgua;
	}

	public void setLigacaoAgua(LigacaoAgua ligacaoAgua){

		this.ligacaoAgua = ligacaoAgua;
	}

	public Funcionario getFuncionario(){

		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario){

		this.funcionario = funcionario;
	}

	public gcom.micromedicao.medicao.MedicaoTipo getMedicaoTipo(){

		return this.medicaoTipo;
	}

	public void setMedicaoTipo(gcom.micromedicao.medicao.MedicaoTipo medicaoTipo){

		this.medicaoTipo = medicaoTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String getGerarRelatorio(){

		return gerarRelatorio;
	}

	public void setGerarRelatorio(String gerarRelatorio){

		this.gerarRelatorio = gerarRelatorio;
	}

	public String getDataLeituraParaRegistrar(){

		return dataLeituraParaRegistrar;
	}

	public void setDataLeituraParaRegistrar(String dataLeituraParaRegistrar){

		this.dataLeituraParaRegistrar = dataLeituraParaRegistrar;
	}

	public String getIndicadorConfirmacaoLeitura(){

		return indicadorConfirmacaoLeitura;
	}

	public void setIndicadorConfirmacaoLeitura(String indicadorConfirmacaoLeitura){

		this.indicadorConfirmacaoLeitura = indicadorConfirmacaoLeitura;
	}

	public Integer getConsumoCreditoAnterior(){

		return consumoCreditoAnterior;
	}

	public void setConsumoCreditoAnterior(Integer consumoCreditoAnterior){

		this.consumoCreditoAnterior = consumoCreditoAnterior;
	}

	/**
	 * Retorna o valor de mesAno
	 * 
	 * @return O valor de mesAno
	 */
	public String getMesAno(){

		// o metodo serve para transformar o AnoMesReferencia do banco
		// em mes/Ano para demonstra�ao para o usuario.
		// Ex.: 200508 para 08/2005
		String mesAno = null;

		String mes = null;
		String ano = null;

		if(this.anoMesReferencia != 0){
			String anoMes = "" + this.anoMesReferencia;

			mes = anoMes.substring(4, 6);
			ano = anoMes.substring(0, 4);
			mesAno = mes + "/" + ano;
		}
		return mesAno;
	}

	public Date getLeituraProcessamentoMovimento(){

		return leituraProcessamentoMovimento;
	}

	public void setLeituraProcessamentoMovimento(Date leituraProcessamentoMovimento){

		this.leituraProcessamentoMovimento = leituraProcessamentoMovimento;
	}

	/**
	 * Description of the Method
	 * 
	 * @param other
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}

		if(!(other instanceof MedicaoHistorico)){
			return false;
		}

		boolean retorno = true;

		MedicaoHistorico castOther = (MedicaoHistorico) other;

		if(this.getId() != null){

			if(!this.getId().equals(castOther.getId())){
				retorno = false;
			}

		}

		if(this.getLigacaoAgua() != null && castOther.getLigacaoAgua() != null){

			if(!this.getLigacaoAgua().getId().equals(castOther.getLigacaoAgua().getId())){
				retorno = false;
			}

		}

		if(this.getImovel() != null && castOther.getImovel() != null){

			if(!this.getImovel().getId().equals(castOther.getImovel().getId())){
				retorno = false;
			}

		}

		if(this.getMedicaoTipo() != null){

			if(!this.getMedicaoTipo().getId().equals(castOther.getMedicaoTipo().getId())){
				retorno = false;
			}
		}

		if(this.getAnoMesReferencia() != 0){
			if(!(this.getAnoMesReferencia() == castOther.getAnoMesReferencia())){
				retorno = false;
			}
		}
		return retorno;

	}

	/**
	 * @return the consumoCreditoGerado
	 */
	public Integer getConsumoCreditoGerado(){

		return consumoCreditoGerado;
	}

	/**
	 * @param consumoCreditoGerado
	 *            the consumoCreditoGerado to set
	 */
	public void setConsumoCreditoGerado(Integer consumoCreditoGerado){

		this.consumoCreditoGerado = consumoCreditoGerado;
	}

	/**
	 * @return the numeroConsumoFaturadoAguaLeitura
	 */
	public Integer getNumeroConsumoFaturadoAguaLeitura(){

		return numeroConsumoFaturadoAguaLeitura;
	}

	/**
	 * @param numeroConsumoFaturadoAguaLeitura
	 *            the numeroConsumoFaturadoAguaLeitura to set
	 */
	public void setNumeroConsumoFaturadoAguaLeitura(Integer numeroConsumoFaturadoAguaLeitura){

		this.numeroConsumoFaturadoAguaLeitura = numeroConsumoFaturadoAguaLeitura;
	}

	/**
	 * @return the numeroConsumoFaturadoEsgotoLeitura
	 */
	public Integer getNumeroConsumoFaturadoEsgotoLeitura(){

		return numeroConsumoFaturadoEsgotoLeitura;
	}

	/**
	 * @param numeroConsumoFaturadoEsgotoLeitura
	 *            the numeroConsumoFaturadoEsgotoLeitura to set
	 */
	public void setNumeroConsumoFaturadoEsgotoLeitura(Integer numeroConsumoFaturadoEsgotoLeitura){

		this.numeroConsumoFaturadoEsgotoLeitura = numeroConsumoFaturadoEsgotoLeitura;
	}

	/**
	 * @return the numeroConsumoFaturadoLeitura
	 */
	public Integer getNumeroConsumoFaturadoLeitura(){

		return numeroConsumoFaturadoLeitura;
	}

	/**
	 * @param numeroConsumoFaturadoLeitura
	 *            the numeroConsumoFaturadoLeitura to set
	 */
	public void setNumeroConsumoFaturadoLeitura(Integer numeroConsumoFaturadoLeitura){

		this.numeroConsumoFaturadoLeitura = numeroConsumoFaturadoLeitura;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"mesAno", "id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Referencia", "Medicao Historico"};
		return labels;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public void initializeLazy(){

		if(leituraFiscalizacao != null){
			leituraFiscalizacao.initializeLazy();
		}
		if(imovel != null){
			imovel.initializeLazy();
		}
		if(hidrometroInstalacaoHistorico != null){
			hidrometroInstalacaoHistorico.initializeLazy();
		}
		if(leituraAnormalidadeFaturamento != null){
			leituraAnormalidadeFaturamento.initializeLazy();
		}
		if(leituraAnormalidadeInformada != null){
			leituraAnormalidadeInformada.initializeLazy();
		}
		if(ligacaoAgua != null){
			ligacaoAgua.initializeLazy();
		}
		if(funcionario != null){
			funcionario.initializeLazy();
		}
		if(leituraSituacaoAtual != null){
			leituraSituacaoAtual.initializeLazy();
		}

	}

	public Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public Rota getRota(){

		return rota;
	}

	public void setRota(Rota rota){

		this.rota = rota;
	}

	public Short getPermiteAlterarConsumoMedio(){

		return permiteAlterarConsumoMedio;
	}

	public void setPermiteAlterarConsumoMedio(Short permiteAlterarConsumoMedio){

		this.permiteAlterarConsumoMedio = permiteAlterarConsumoMedio;
	}

	public Integer getConsumoMedioAux(){

		return consumoMedioAux;
	}

	public void setConsumoMedioAux(Integer consumoMedioAux){

		this.consumoMedioAux = consumoMedioAux;
	}

}
