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

package gcom.batch;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Relatorio
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MANTER_BAIRRO = 1;

	public static final int MANTER_CLIENTE = 2;

	public static final int MANTER_SETOR_COMERCIAL = 3;

	public static final int MANTER_LOCALIDADE = 4;

	public static final int MANTER_LOGRADOURO = 5;

	public static final int MANTER_QUADRA = 6;

	public static final int MANTER_IMOVEL = 7;

	public static final int MANTER_TARIFA_SOCIAL = 8;

	public static final int MANTER_SUBCATEGORIA = 9;

	public static final int MANTER_ROTA = 10;

	public static final int MANTER_CATEGORIA = 11;

	public static final int MANTER_CRONOGRAMA_FATURAMENTO = 12;

	public static final int MANTER_CRONOGRAMA_COBRANCA = 13;

	public static final int MANTER_CRITERIO_COBRANCA = 14;

	public static final int MANTER_PERFIL_PARCELAMENTO = 15;

	public static final int MANTER_MOVIMENTO_ARRECADADOR = 16;

	public static final int MANTER_MENSAGEM_CONTA = 17;

	public static final int MANTER_AVISO_BANCARIO = 18;

	public static final int MANTER_GUIA_DEVOLUCAO = 19;

	public static final int MANTER_IMOVEL_OUTROS_CRITERIOS = 20;

	public static final int DADOS_ECONOMIA_IMOVEL = 21;

	public static final int DADOS_TARIFA_SOCIAL = 22;

	public static final int MANTER_HIDROMETRO = 23;

	public static final int MANTER_RESOLUCAO_DIRETORIA = 24;

	public static final int RESUMO_ARRECADACAO = 25;

	public static final int GERAR_RELACAO_DEBITOS = 26;

	public static final int MOVIMENTO_DEBITO_AUTOMATICO_BANCO = 27;

	public static final int RESUMO_FATURAMENTO_SITUACAO_ESPECIAL = 28;

	public static final int DEVOLUCAO = 29;

	public static final int PAGAMENTO = 30;

	public static final int CONSULTAR_REGISTRO_ATENDIMENTO = 31;

	public static final int SEGUNDA_VIA_CONTA = 32;

	public static final int PARCELAMENTO = 33;

	public static final int EXTRATO_DEBITO = 34;

	public static final int EMITIR_GUIA_PAGAMENTO = 35;

	public static final int GUIA_DEVOLUCAO = 36;

	public static final int ROTEIRO_PROGRAMACAO = 37;

	public static final int CONSULTAR_REGISTRO_ATENDIMENTO_VIA_CLIENTE = 38;

	public static final int ORDEM_SERVICO = 39;

	public static final int CONSULTAR_OPERACAO = 40;

	public static final int NUMERACAO_RA_MANUAL = 41;

	public static final int ACOMPANHAMENTO_EXECUCAO_OS = 42;

	public static final int MOVIMENTO_ARRECADADOR = 43;

	public static final int FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA = 44;

	public static final int ANALISE_FATURAMENTO = 45;

	public static final int RESUMO_FATURAMENTO = 46;

	public static final int CONTAS_EMITIDAS = 47;

	public static final int MAPA_CONTROLE_CONTA = 48;

	public static final int MEDICAO_CONSUMO_LIGACAO_AGUA = 49;

	public static final int RESUMO_CONTA_LOCALIDADE = 50;

	public static final int RESUMO_IMOVEL_MEDICAO = 51;

	public static final int SEGUNDA_VIA_CONTA_TIPO_2 = 52;

	public static final int EXTRATO_DEBITO_CLIENTE = 53;

	// public static final int CONSULTAR_DEBITOS = 53;

	public static final int COMPARATIVO_LEITURAS_E_ANORMALIDADES = 54;

	public static final int EMITIR_PROTOCOLO_DOCUMENTO_COBRANCA = 55;

	public static final int RELACAO_PARCELAMENTO = 56;

	public static final int CLIENTES_ESPECIAIS = 57;

	public static final int IMOVEL_ENDERECO = 58;

	public static final int HISTOGRAMA_AGUA_POR_ECONOMIA = 59;

	public static final int HISTOGRAMA_AGUA_LIGACAO = 60;

	public static final int ANALITICO_FATURAMENTO = 61;

	public static final int RELATORIO_CONTA = 62;

	public static final int ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES_POR_NSA = 63;

	public static final int RESUMO_DEVEDORES_DUVIDOSOS = 64;

	public static final int ORDEM_CORTE_ORIGINAL = 65;

	public static final int RELATORIO_AVISO_DEBITO = 66;

	public static final int FATURA_CLIENTE_RESPONSAVEL = 67;

	public static final int BOLETIM_CADASTRO = 68;

	public static final int GERAR_DADOS_PARA_LEITURA = 69;

	public static final int CADASTRO_CONSUMIDORES_INSCRICAO = 70;

	public static final int VOLUMES_FATURADOS = 71;

	public static final int VOLUMES_FATURADOS_RESUMIDO = 72;

	public static final int QUALIDADE_AGUA = 73;

	public static final int CONTAS_EM_REVISAO = 74;

	public static final int CONTAS_EM_REVISAO_RESUMIDO = 75;

	public static final int GERAR_INDICES_ACRESCIMOS_IMPONTUALIDADE = 76;

	public static final int GERAR_CURVA_ABC_DEBITOS = 77;

	public static final int ANORMALIDADE_CONSUMO = 79;

	public static final int HISTOGRAMA_ESGOTO_LIGACAO = 80;

	public static final int HISTOGRAMA_ESGOTO_POR_ECONOMIA = 81;

	public static final int ANALISE_CONSUMO = 82;

	public static final int ORCAMENTO_SINP = 83;

	public static final int IMOVEIS_SITUACAO_LIGACAO_AGUA = 84;

	public static final int IMOVEIS_FATURAS_ATRASO = 85;

	public static final int IMOVEIS_CONSUMO_MEDIO = 86;

	public static final int RELATORIO_RELACAO_OS_ENCERRADAS_PENDENTES = 87;

	public static final int RELATORIO_RESUMO_OS_ENCERRADAS_PENDENTES = 88;

	public static final int IMOVEIS_ULTIMOS_CONSUMOS_AGUA = 89;

	public static final int RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA = 90;

	public static final int RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_SUGESTAO = 91;

	public static final int QUADRO_METAS_ACUMULADO = 92;

	public static final int IMOVEIS_TIPO_CONSUMO = 93;

	public static final int IMOVEIS_ATIVOS_NAO_MEDIDO = 94;

	public static final int RELATORIO_ORDEM_FISCALIZACAO = 95;

	public static final int IMOVEIS_FATURAS_RECENTES_DIA_FATURAS_ANTIGAS_ATRASO = 96;

	public static final int COMPARATIVO_FATURAMENTO_ARRECADACAO_EXPURGO = 97;

	public static final int EVOLUCAO_CONTAS_A_RECEBER_CONTABIL = 98;

	public static final int SALDO_CONTAS_A_RECEBER_CONTABIL = 99;

	public static final int RELATORIO_BOLETIM_CADASTRO = 100;

	public static final int RELATORIO_RESUMO_LEITURA_ANORMALIDADE = 101;

	public static final int RELATORIO_RESUMO_OCORRENCIAS_FATURAMENTO_IMEDIATO = 102;

	public static final int MANTER_NEGATIVADOR_RETORNO_MOTIVO = 214;

	public static final int RELATORIO_TELECOBRANCA = 104;

	public static final int RELATORIO_COBRANCA_JURIDICO = 105;

	public static final int RELATORIO_ORDEM_SERVICO_COBRANCA = 106;

	public static final int RELATORIO_NEGATIVACAO_SPC_SERASA = 107;

	public static final int RELATORIO_FECHAMENTO_COBRANCA = 108;

	public static final int GERAR_RELATORIO_NEGATIVACOES_EXCLUIDAS = 109;

	public static final int MANTER_SUBSISTEMA_ESGOTO = 110;

	public static final int MANTER_SISTEMA_ESGOTAMENTO = 111;

	// public static final int MANTER_LOCALIDADE_DADOS_OPERACIONAIS = 112;

	public static final int MANTER_UNIDADE_OPERACIONAL = 112;

	public static final int MANTER_MUNICIPIO = 113;

	public static final int MANTER_ZONA_ABASTECIMENTO = 114;

	public static final int RELATORIO_RESULTADO_SIMULACAO = 115;

	public static final int RELATORIO_MANTER_SISTEMA_ABASTECIMENTO = 123;

	public static final int RELATORIO_EFICIENCIA_COBRANCA = 139;

	public static final int RELATORIO_ACOMPANHAMENTO_EXECUCAO_SERVICO_COBRANCA = 140;

	public static final int RELATORIO_PRODUTIVIDADE_MENSAL_EXECUCAO_SERVICO = 141;

	public static final int RELATORIO_IMOVEL_ACAO_COBRANCA = 142;

	public static final int MANTER_BACIA = 143;

	public static final int MANTER_SETOR_ABASTECIMENTO = 144;

	public static final int RELATORIO_MICROMEDIDORES = 145;

	public static final int MANTER_CONSUMO_FAIXA_AREA_CATEGORIA = 146;

	public static final int RELATORIO_MACROMEDIDORES_MICROMEDIDOS_ASSOCIADOS_ULTIMOS_CONSUMOS = 147;

	public static final int RELATORIO_GRANDES_CONSUMIDORES = 163;

	public static final int RELATORIO_RELIGACAO_POR_IMOVEL = 171;

	public static final int RELATORIO_ACOMPANHAMENTO_ACAO = 172;

	public static final int RELATORIO_ACOMPANHAMENTO_RELIGACOES_ESPECIAIS = 173;

	public static final int GERAR_RELATORIO_ACOMPANHAMENTO_CLIENTES_NEGATIVADOS = 174;

	// UC01101 - Emitir Carta com Opção de parcelamento
	public static final int RELATORIO_CARTA_OPCAO_PARCELAMENTO = 176;

	public static final int RELATORIO_USUARIO_DEBITO_AUTOMATICO = 183;

	public static final int RELATORIO_ESTATISTICA_ATENDIMENTO_POR_ATENDENTE = 206;

	public static final int RELATORIO_PRODUTIVIDADE_EQUIPE = 207;

	public static final int RELATORIO_RESUMO_OS_ENCERRADA = 208;

	public static final int RELATORIO_OS_NAO_EXECUTADA_EQUIPE = 209;

	public static final int RELATORIO_RESUMO_ORDENS_SERVICO_PENDENTES = 210;

	public static final int RELATORIO_LOGRADOURO_GERAL = 211;

	public static final int CERTIDAO_NEGATIVA = 212;

	public static final int MANTER_NEGATIVADOR_REGISTRO_TIPO = 213;

	public static final int AVISO_CORTE = 103;

	public static final int MANTER_NEGATIVADOR_EXCLUSAO_MOTIVO = 215;

	public static final int MANTER_SUBBACIA = 216;

	public static final int MANTER_NEGATIVADOR_CONTRATO = 217;

	public static final int MANTER_NEGATIVADOR = 218;

	public static final int RELATORIO_EXCLUSAO_NEGATIVACAO_SPC_SERASA = 219;

	public static final int RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_SINTETICO = 222;

	public static final int RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_ANALITICO = 221;

	// CRC 89962
	public static final int RELATORIO_GERAR_COMPROVANTES_LEITURA = 220;

	public static final int ANORMALIDADES_LEITURAS = 223;

	public static final int RELATORIO_PROCESSAR_ARQUIVO_INFORMAR_ANORMALIDADES_LEITURA = 224;

	public static final int GERAR_DADOS_FATURAMENTO_IMEDIATO = 225;

	public static final int GERAR_TABELAS_FATURAMENTO_IMEDIATO = 226;

	public static final int RELATORIO_RESUMO_FATURAMENTO_IMEDIATO = 231;

	public static final int RELATORIO_GERACAO_CARTA_COBRANCA_BANCARIA = 232;

	public static final int RELATORIO_IMOVEIS_ACAO_COBRANCA = 233;

	public static final int RELATORIO_BOLETOS_BANCARIOS = 234;

	public static final int RELATORIO_MOVIMENTO_COBRANCA_BANCARIA = 235;

	public static final int RELATORIO_MANTER_GUIA_PAGAMENTO = 236;

	// CR - 103544
	public static final int RELATORIO_AVISO_CORTE_ARQUIVO_TXT = 237;

	public static final int RELATORIO_EMITIR_RELACAO_DOCUMENTOS_COBRANCA = 238;

	// CR - 103846
	public static final int RELATORIO_ORDEM_CORTE_ARQUIVO_TXT = 239;

	public static final int RELATORIO_IMOVEIS_POR_QUADRA = 240;

	public static final int RELATORIO_RESUMO_ANORMALIDADES_CONSUMO = 241;

	// CR - 108302
	public static final int RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA = 242;

	public static final int RELATORIO_CONTAS_PRE_FATURADAS = 243;

	public static final int RELATORIO_GERACAO_ORDEM_SERVICO_TXT = 244;

	public static final int RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS = 245;

	public static final int RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS = 246;

	public static final int RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS = 247;

	public static final int RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS = 248;

	public static final int RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO = 249;

	public static final int RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO_RESUMO = 250;

	public static final int GERAR_DADOS_TABELAS_FATURAMENTO_IMEDIATO = 251;

	public static final int RESUMO_LIGACOES_ECONOMIA = 253;

	public static final int RELATORIO_ARQUIVO_FATURAMENTO_CONVENCIONAL = 268;

	public static final int RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CASAL = 269;

	public static final int RELATORIO_ACOMPANHAMENTO_FATURAMENTO = 270;

	public static final int RELATORIO_ESTATISTICO_REGISTRO_ATENDIMENTO = 271;

	public static final int RELATORIO_REPOSICAO_PAVIMENTO = 270;

	public static final int RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA = 272;

	public static final int RELATORIO_DEBITO_POR_RESPONSAVEL = 273;

	public static final int RELATORIO_ESPELHO_DE_CADASTRO = 252;

	public static final int ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES = 277;

	public static final int RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL = 278;

	public static final int IMOVEIS_EXCLUIDOS = 280;

	public static final int RELATORIO_CONTAS_A_RECEBER_CORRIGIDO = 279;

	public static final int RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL = 287;

	public static final int CLASSIFICAR_LOTE_PAGAMENTO_NAO_CLASSIFICADO = 276;

	public static final int RELATORIO_IMOVEIS_INSCRICAO_ALTERADA = 281;

	public static final int RELATORIO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA = 288;

	public static final int RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA = 290;

	public static final int RELATORIO_ORDEM_SERVICO_FISCALIZACAO_MODELO_2 = 298;

	public static final int RELATORIO_ORDEM_SERVICO_CARTA_HIDROMETRO_MODELO_1 = 299;

	public static final int RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS = 300;

	public static final int RELATORIO_QUADRO_COMPARATIVO_FATURAMENTO_ARRECADACAO = 301;

	public static final int RELATORIO_MANTER_CEP = 302;

	public static final int RELATORIO_HIDROMETRO_MOVIMENTACAO = 303;

	public static final int RELATORIO_ARQUIVO_RELIGACAO_AUTOMATICA_CONSUMIDOR = 309;

	public static final int RELATORIO_ANALITICO_CONTAS_EMITIDAS = 310;

	public static final int RELATORIO_CONTAS_RETIRADAS_REVISAO = 311;

	public static final int RELATORIO_CONTAS_BLOQUEADAS_ANALISE = 312;

	public static final int COMPROVANTE_INSCRICAO_CAMPANHA_PREMIACAO = 313;
	
	public static final int RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_SINTETICO = 314;

	public static final int RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_ANALITICO = 315;

	public static final int FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL = 316;

	public static final int RELATORIO_SITUACAO_DOS_AVISOS_BANCARIOS = 317;

	public static final int RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS = 319;

	public static final int RELATORIO_AUDITORIA_LEITURA = 318;

	public static final int RELATORIO_PREMIACOES_CAMPANHA_PREMIACAO = 320;

	public static final int RELATORIO_CONCILIACAO_CONTABIL = 321;

	public static final int RELATORIO_RESUMO_RECEBIMENTO_FORA_PRAZO_CONTRATUAL = 323;

	public static final int RELATORIO_RESUMO_RECEBIMENTO_ARRECADADOR = 322;

	public static final int RELATORIO_ACOMP_MOV_ARRECADADORES = 328;

	public static final int RELATORIO_MAIORES_CONSUMIDORES = 326;

	public static final int RELATORIO_MAIORES_DEVEDORES = 327;

	public static final int RELATORIO_IMOVEIS_LIGACAO_CORTADA_COM_CONSUMO = 373;

	public static final int RELATORIO_QUADRO_HIDROMETROS = 329;

	public static final int RELATORIO_QUADRO_HIDROMETROS_ANO_INSTALACAO = 330;

	public static final int RELATORIO_QUADRO_HIDROMETROS_SITUACAO = 331;

	public static final int RELATORIO_OS_ENCERRADA_DENTRO_FORA_PRAZO = 334;

	public static final int RELATORIO_MANTER_USUARIO = 335;

	public static final int RELATORIO_MATERIAIS_APLICADOS = 337;

	public static final int RELATORIO_REGISTRO_ATENDIMENTO_COM_PROCESSO_ADM_JUD = 336;

	public static final int RELATORIO_ESTATISTICO_ATENDIMENTO_POR_RACA_COR = 340;

	public static final int RELATORIO_CONTRATO_DEMANDA_CONSUMO = 338;

	public static final int RELATORIO_CONTA_MODELO_3 = 339;

	public static final int RELATORIO_DADOS_IMOVEIS_COM_CONTA_EM_ATRASO = 343;

	public static final int RELATORIO_SITUACAO_ESPECIAL_DE_FATURAMENTO = 342;

	public static final int RELATORIO_ANALITICO_FATURAMENTO = 345;

	public static final int RELATORIO_DEBITOS_PRESCRITOS = 341;

	public static final int RELATORIO_ACOMPANHAMENTO_DEBITOS_PRESCRITOS = 344;

	/** identifier field */
	/*
	 * private Integer id;
	 *//** nullable persistent field */
	/*
	 * private String descricaoRelatorio;
	 *//** nullable persistent field */
	/*
	 * private String descricaoAbreviada;
	 *//** persistent field */
	/*
	 * private short indicadorUso;
	 *//** nullable persistent field */
	/*
	 * private Date ultimaAlteracao;
	 */

	/** persistent field */
	private Set relatoriosGerados;

	/** full constructor */
	/*
	 * public Relatorio(String descricaoRelatorio, String descricaoAbreviada, short indicadorUso,
	 * Date ultimaAlteracao, Set relatoriosGerados) {
	 * this.descricaoRelatorio = descricaoRelatorio;
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * this.indicadorUso = indicadorUso;
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * this.relatoriosGerados = relatoriosGerados;
	 * }
	 *//** default constructor */
	/*
	 * public Relatorio() {
	 * }
	 *//** minimal constructor */
	/*
	 * public Relatorio(short indicadorUso, Set relatoriosGerados) {
	 * this.indicadorUso = indicadorUso;
	 * this.relatoriosGerados = relatoriosGerados;
	 * }
	 * public Integer getId(){
	 * return this.id;
	 * }
	 * public void setId(Integer id){
	 * this.id = id;
	 * }
	 * public String getDescricaoRelatorio(){
	 * return this.descricaoRelatorio;
	 * }
	 * public void setDescricaoRelatorio(String descricaoRelatorio){
	 * this.descricaoRelatorio = descricaoRelatorio;
	 * }
	 * public String getDescricaoAbreviada(){
	 * return this.descricaoAbreviada;
	 * }
	 * public void setDescricaoAbreviada(String descricaoAbreviada){
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * }
	 * public short getIndicadorUso(){
	 * return this.indicadorUso;
	 * }
	 * public void setIndicadorUso(short indicadorUso){
	 * this.indicadorUso = indicadorUso;
	 * }
	 * public Date getUltimaAlteracao(){
	 * return this.ultimaAlteracao;
	 * }
	 * public void setUltimaAlteracao(Date ultimaAlteracao){
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 */

	public Set getRelatoriosGerados(){

		return this.relatoriosGerados;
	}

	public void setRelatoriosGerados(Set relatoriosGerados){

		this.relatoriosGerados = relatoriosGerados;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

}
