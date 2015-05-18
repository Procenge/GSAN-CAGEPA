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

import gcom.interceptor.ObjetoGcom;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Processo
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	public static final int FATURAR_GRUPO_FATURAMENTO = 2;

	public static final int GERAR_DADOS_PARA_LEITURA = 3;

	public static final int GERAR_RELATORIO_MANTER_BAIRRO = 4;

	public static final int GERAR_RELATORIO_MANTER_SETOR_COMERCIAL = 7;

	public static final int GERAR_RELATORIO_MANTER_LOCALIDADE = 8;

	public static final int GERAR_RELATORIO_MANTER_LOGRADOURO = 9;

	public static final int GERAR_RELATORIO_MANTER_QUADRA = 10;

	public static final int GERAR_RELATORIO_MANTER_IMOVEL = 11;

	public static final int GERAR_RELATORIO_MANTER_TARIFA_SOCIAL = 12;

	public static final int GERAR_RELATORIO_MANTER_SUBCATEGORIA = 13;

	public static final int GERAR_RELATORIO_MANTER_ROTA = 14;

	public static final int GERAR_RELATORIO_MANTER_CATEGORIA = 15;

	public static final int GERAR_RELATORIO_MANTER_CRONOGRAMA_FATURAMENTO = 16;

	public static final int GERAR_RELATORIO_MANTER_CRONOGRAMA_COBRANCA = 17;

	public static final int GERAR_RELATORIO_MANTER_CRITERIO_COBRANCA = 18;

	public static final int GERAR_RELATORIO_MANTER_PERFIL_PARCELAMENTO = 19;

	public static final int GERAR_RELATORIO_MANTER_MOVIMENTO_ARRECADADOR = 20;

	public static final int GERAR_RELATORIO_MANTER_MENSAGEM_CONTA = 21;

	public static final int GERAR_RELATORIO_MANTER_AVISO_BANCARIO = 22;

	public static final int GERAR_RELATORIO_MANTER_GUIA_DEVOLUCAO = 23;

	public static final int GERAR_RELATORIO_MANTER_IMOVEL_OUTROS_CRITERIOS = 24;

	public static final int GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL = 25;

	public static final int GERAR_RELATORIO_DADOS_TARIFA_SOCIAL = 26;

	public static final int GERAR_RELATORIO_MANTER_HIDROMETRO = 27;

	public static final int GERAR_RELATORIO_MANTER_RESOLUCAO_DIRETORIA = 28;

	public static final int GERAR_RELATORIO_RESUMO_ARRECADACAO = 29;

	public static final int GERAR_RELATORIO_GERAR_RELACAO_DEBITOS = 30;

	public static final int GERAR_RELATORIO_MOVIMENTO_DEBITO_AUTOMATICO_BANCO = 31;

	public static final int GERAR_RELATORIO_RESUMO_FATURAMENTO_SITUACAO_ESPECIAL = 32;

	public static final int GERAR_RELATORIO_DEVOLUCAO = 33;

	public static final int GERAR_RELATORIO_PAGAMENTO = 34;

	public static final int GERAR_RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO = 35;

	public static final int GERAR_RELATORIO_SEGUNDA_VIA_CONTA = 36;

	public static final int GERAR_RELATORIO_PARCELAMENTO = 37;

	public static final int GERAR_RELATORIO_EXTRATO_DEBITO = 38;

	public static final int GERAR_RELATORIO_EMITIR_GUIA_PAGAMENTO = 39;

	public static final int GERAR_RELATORIO_GUIA_DEVOLUCAO = 40;

	public static final int GERAR_RELATORIO_ROTEIRO_PROGRAMACAO = 41;

	public static final int GERAR_RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO_VIA_CLIENTE = 42;

	public static final int GERAR_RELATORIO_ORDEM_SERVICO = 43;

	public static final int GERAR_RELATORIO_CONSULTAR_OPERACAO = 44;

	public static final int GERAR_RELATORIO_NUMERACAO_RA_MANUAL = 45;

	public static final int GERAR_RELATORIO_ACOMPANHAMENTO_EXECUCAO_OS = 46;

	public static final int GERAR_RELATORIO_MANTER_CLIENTE = 47;

	public static final int GERAR_RELATORIO_MOVIMENTO_ARRECADADOR = 313;

	public static final int ENCERRAR_ARRECADACAO_MES = 50;

	public static final int GERAR_RELATORIO_MAPA_CONTROLE_CONTA = 59;

	public static final int GERAR_RELATORIO_COMPARATIVO_LEITURAS_E_ANORMALIDADE = 60;

	public static final int GERAR_RELATORIO_SEGUNDA_VIA_CONTA_TIPO_2 = 62;

	public static final int GERAR_RELATORIO_CLIENTES_ESPECIAIS = 73;

	public static final int GERAR_RELATORIO_IMOVEL_ENDERECO = 75;

	public static final int GERAR_RESUMO_DEVEDORES_DUVIDOSOS = 79;

	public static final int GERAR_RESUMO_ACOES_COBRANCA_EVENTUAL = 80;

	public static final int GERAR_RELATORIO_HISTOGRAMA_AGUA_ECONOMIA = 81;

	public static final int GERAR_RELATORIO_HISTOGRAMA_AGUA_LIGACAO = 82;

	public static final int GERAR_RELATORIO_CONTA_TIPO_2 = 83;

	public static final int GERAR_RELATORIO_ORDEM_CORTE_ORIGINAL = 85;

	public static final int GERAR_RELATORIO_FATURA_CLIENTE_RESPONSAVEL = 87;

	public static final int GERAR_BOLETIM_CADASTRO = 90;

	public static final int GERAR_RELATORIO_CADASTRO_CONSUMIDORES_INSCRICAO = 91;

	public static final int GERAR_RELATORIO_VOLUMES_FATURADOS = 297;

	public static final int GERAR_RELATORIO_VOLUMES_FATURADOS_RESUMIDO = 93;

	public static final int GERAR_RELATORIO_CONTAS_EM_REVISAO = 95;

	public static final int GERAR_RELATORIO_CONTAS_EM_REVISAO_RESUMIDO = 96;

	public static final int GERAR_CURVA_ABC_DEBITOS = 97;

	public static final int GERAR_RELATORIO_ANORMALIDADE_CONSUMO = 99;

	public static final int GERAR_RELATORIO_HISTOGRAMA_ESGOTO_LIGACAO = 90026;

	public static final int GERAR_RELATORIO_HISTOGRAMA_ESGOTO_ECONOMIA = 101;

	public static final int GERAR_EMITIR_ORDEM_SERVICO_SELETIVA = 102;

	public static final int GERAR_RELATORIO_ORCAMENTO_SINP = 103;

	public static final int GERAR_RELATORIO_IMOVEIS_SITUACAO_LIGACAO_AGUA = 106;

	public static final int GERAR_RELATORIO_IMOVEIS_FATURAS_ATRASO = 107;

	public static final int GERAR_RELATORIO_IMOVEIS_CONSUMO_MEDIO = 111;

	public static final int GERAR_RELATORIO_IMOVEIS_ULTIMOS_CONSUMOS_AGUA = 114;

	public static final int GERAR_EMITIR_ORDEM_SERVICO_SELETIVA_SUGESTAO = 115;

	public static final int GERAR_RELATORIO_IMOVEIS_TIPO_CONSUMO = 117;

	public static final int GERAR_RELATORIO_IMOVEIS_ATIVOS_NAO_MEDIDOS = 118;

	public static final int GERAR_RELATORIO_ORDEM_FISCALIZACAO = 119;

	public static final int GERAR_RELATORIO_IMOVEIS_FATURAS_RECENTES_DIA_FATURAS_ANTIGAS_ATRASO = 121;

	public static final int GERAR_RELATORIO_RESUMO_LEITURA_FATURAMENTO_IMEDIATO = 126;

	public static final int GERAR_RELATORIO_RESUMO_OCORRENCIAS_FATURAMENTO_IMEDIATO = 127;

	public static final int GERAR_RELATORIO_RESUMO_FATURAMENTO = 128;

	public static final int GERAR_RESUMO_DIARIO_NEGATIVACAO = 135;

	public static final int GERAR_RELATORIO_CERTIDAO_NEGATIVA = 129;

	public static final int GERAR_RELATORIO_AVISO_DEBITO = 160;

	public static final int GERAR_RELATORIO_AVISO_CORTE = 161;

	public static final int GERAR_RELATORIO_TELECOBRANCA = 162;

	public static final int GERAR_RELATORIO_JURIDICO = 163;

	// ------------------------------------------------------------------------
	public static final int GERAR_MOVIMENTO_RETORNO_NEGATIVACAO = 159;

	// ------------------------------------------------------------------------

	public static final int GERAR_RELATORIO_SPC_SERASA = 165;

	public static final int GERAR_RELATORIO_FECHAMENTO_COBRANCA = 166;

	public static final int GERAR_RELATORIO_EFICIENCIA_COBRANCA = 167;

	public static final int GERAR_BAIXA_ORDENS_SERVICO_COBRANCA = 169;

	public static final int GERAR_RELATORIO_IMOVEL_POR_ACAO_COBRANCA = 170;

	public static final int GERAR_RELATORIO_RELIGACOES_POR_IMOVEL = 171;

	public static final int GERAR_PRODUTIVIDADE_MENSAL_EXECUCAO_SERVICO = 172;

	public static final int GERAR_ACOMPANHAMENTO_ACAO = 173;

	public static final int GERAR_RELATORIO_ACOMPANHAMENTO_RELIGACOES_ESPECIAIS = 174;

	public static final int GERAR_RELATORIO_NEGATIVACOES_EXCLUIDAS = 90041;

	public static final int GERAR_RELATORIO_ACOMPANHAMENTO_CLIENTES_NEGATIVADOS = 90040;

	public static final int GERAR_REL_EXC_NEGATIVACAO_SPC_SERASA = 177;

	public static final int GERAR_REL_CARTA_OPCAO_PARCELAMENTO = 180;

	public static final int GERAR_RELATORIO_ANALISE_CONSUMO = 179;

	public static final int GERAR_RELATORIO_LOGRADOURO_GERAL = 131;

	public static final int GERAR_RELATORIO_MANTER_SUBSISTEMA_ESGOTO = 179;

	public static final int GERAR_RELATORIO_MANTER_SISTEMA_ESGOTAMENTO = 180;

	public static final int GERAR_RELATORIO_MANTER_SUBBACIA = 181;

	public static final int GERAR_RELATORIO_MANTER_ZONA_ABASTECIMENTO = 182;

	// public static final int GERAR_RELATORIO_MANTER_LOCALIDADE_DADOS_OPERACIONAIS = 182;

	public static final int GERAR_RELATORIO_MANTER_BACIA = 149;

	public static final int GERAR_RELATORIO_GRANDES_CONSUMIDORES = 169;

	public static final int GERAR_RELATORIO_USUARIO_DEBITO_AUTOMATICO = 189;

	public static final int GERAR_RELATORIO_ESTATISTICA_ATENDIMENTO_POR_ATENDENTE = 213;

	public static final int GERAR_RELATORIO_PRODUTIVIDADE_EQUIPE = 214;

	public static final int GERAR_RELATORIO_RESUMO_OS_ENCERRADAS = 215;

	public static final int GERAR_RELATORIO_RESUMO_OS_PENDENTES = 216;

	public static final int GERAR_RELATORIO_OS_NAO_EXECUTADA_EQUIPE = 217;

	public static final int GERAR_RELATORIO_MICROMEDICAO_ASSOCIADOS = 185;

	public static final int GERAR_RELATORIO_MICROMEDICAO_CONSUMOS = 186;

	public static final int GERAR_RELATORIO_DADOS_LEITURA = 218;

	public static final int GERAR_RELATORIO_COMPROVANTES_LEITURA = 219;

	public static final int GERAR_RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_ANALITICO = 220;

	public static final int GERAR_RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_SINTETICO = 221;

	public static final int GERAR_RELATORIO_ANORMALIDADES_LEITURAS = 222;

	public static final int PROCESSAR_ARQUIVO_DADOS_LEITURA_ANORMALIDADE = 223;

	public static final int GERAR_RELATORIO_DADOS_FATURAMENTO_IMEDIATO = 231;

	public static final int GERAR_RELATORIO_TABELAS_FATURAMENTO_IMEDIATO = 251;

	public static final int GERAR_RELATORIO_RESUMO_FATURAMENTO_IMEDIATO = 252;

	public static final int GERAR_CARTAS_COBRANCA_BANCARIA = 253;

	public static final int INDICAR_COBRANCA_BANCARIA_COM_NEGOCIACAO = 255;

	public static final int GERAR_LISTA_DE_IMOVEIS_ACAO_COBRANCA = 254;

	public static final int GERAR_RELATORIO_BOLETOS_BANCARIOS = 256;

	public static final int GERAR_PROVISAO_DEVEDORES_DUVIDOSOS = 257;

	public static final int GERAR_RELATORIO_GUIA_PAGAMENTO_MANTER = 258;

	public static final int GERAR_RELATORIO_AVISO_CORTE_ARQUIVO_TXT = 259;

	public static final int GERAR_RELATORIO_EMITIR_RELACAO_DOCUMENTOS_COBRANCA = 260;

	public static final int GERAR_RELATORIO_ORDEM_CORTE_ARQUIVO_TXT = 261;

	public static final int GERAR_RELATORIO_IMOVEIS_POR_QUADRA = 262;

	public static final int GERAR_RESUMO_ANORMALIDADES = 55;

	public static final int GERAR_RELATORIO_RESUMO_ANORMALIDADES_CONSUMO = 263;

	public static final int GERAR_RELATORIO_RESUMO_ANORMALIDADES_LEITURA = 70;

	public static final int GERAR_RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA = 264;

	public static final int REGISTRA_MOVIMENTO_ARRECADADORES = 266;

	public static final int REGISTRA_LEITURAS_ANORMALIDADES = 388;

	public static final int GERAR_ENVIAR_RELATORIO_RESUMO_MOVIMENTO_ARRECADACAO = 267;

	public static final int GERAR_RELATORIO_RESUMO_MOVIMENTO_ARRECADACAO = 268;

	public static final int GERAR_RELACAO_DAS_CONTAS_PRE_FATURADAS = 265;

	public static final int GERAR_RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL = 269;

	public static final int GERAR_RELATORIO_GERACAO_ORDEM_SERVICO_TXT = 271;

	public static final int GERAR_RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS = 272;

	public static final int GERAR_RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS = 273;

	public static final int GERAR_RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS = 274;

	public static final int GERAR_RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS = 275;

	public static final int GERAR_RELATORIO_MUNICIPIO_MANTER = 276;

	public static final int GERAR_RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO = 277;

	public static final int GERAR_RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO_RESUMO = 278;

	public static final int GERAR_RELATORIO_DADOS_TABELAS_FATURAMENTO_IMEDIATO = 279;

	public static final int GERAR_RELATORIO_RESUMO_LIGACOES_ECONOMIA = 282;

	public static final int GERAR_RELATORIO_ARQUIVO_FATURAMENTO_CONVENCIONAL = 298;

	public static final int RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CASAL = 299;

	public static final int RELATORIO_ACOMPANHAMENTO_FATURAMENTO = 302;

	public static final int GERAR_RELATORIO_ESTATISTICO_RA = 303;

	public static final int GERAR_RELATORIO_PROCESSAR_ARQUIVO_DADOS_LEITURA_E_ANORMALIDADE = 305;

	public static final int GERAR_RELATORIO_DEBITO_POR_RESPONSAVEL = 306;

	public static final int RELATORIO_LIQUIDOS_RECEBIVEIS = -1;

	public static final int RELATORIO_ESPELHO_CADASTRO = 281;

	public static final int GERAR_RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL = 317;

	public static final int GERAR_RELATORIO_SETOR_ABASTECIMENTO_MANTER = 320;

	public static final int GERAR_RELATORIO_IMOVEIS_EXCLUIDOS = 321;

	public static final int GERAR_RELATORIO_IMOVEIS_INSCRICAO_ALTERADA = 322;

	public static final int GERAR_RELATORIO_RA_COM_PROCESSO_ADM_JUD = 377;

	// public static final int GERAR_RELATORIO_MOVIMENTO_ARRECADADOR = 313;

	public static final int BATCH_ENCERRAR_FATURAMENTO_GERAR_RESUMO_LIGACOES_ECONOMIAS = 325;

	public static final int BATCH_GERAR_RESUMO_LIGACOES_ECONOMIAS = 318;

	public static final int GERAR_PROVISAO_RECEITA = 323;

	public static final int RELATORIO_CONTAS_A_RECEBER_CORRIGIDO = 316;

	public static final int GERAR_RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL = 329;

	public static final int RELATORIO_CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS = 311;

	public static final int CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS = 312;

	public static final int RELATORIO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA = 330;

	public static final int RELATORIO_ANALITICO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA = 330;

	public static final int GERAR_RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA = 332;

	public static final int GERAR_RELATORIO_HISTOGRAMA_AGUA_ESGOTO_ECONOMIA_TXT = 338;

	public static final int ENCERRAR_ARRECADACAO = 324;

	public static final int RELATORIO_ORDEM_SERVICO_FISCALIZACAO_MODELO_2 = 339;

	public static final int RELATORIO_ORDEM_SERVICO_CARTA_HIDROMETRO_MODELO_1 = 340;

	public static final int GERAR_RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS = 341;

	public static final int EMITIR_DECLARACAO_ANUAL_QUITACAO_DEBITOS = 342;

	public static final int GERAR_DECLARACAO_ANUAL_QUITACAO_DEBITOS = 343;

	public static final int GERAR_RELATORIO_HIDROMETRO_MOVIMENTACAO = 347;

	public static final int RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA = 304;

	public static final int RELATORIO_MANTER_CEP = 346;

	public static final int GERAR_AQUIVO_RELIGACAO_AUTOMATICA_CONSUMIDOR = 348;

	public static final int GERAR_RELATORIO_CONTAS_RETIRADAS_REVISAO = 351;

	public static final int GERAR_RELATORIO_CONTAS_BLOQUEADAS_ANALISE = 352;

	public static final int GERAR_RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_SINTETICO = 353;

	public static final int GERAR_RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_ANALITICO = 354;

	public static final int GERAR_RELATORIO_FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL = 355;

	public static final int SORTEIO_CAMPANHA_PREMIACAO = 356;

	public static final int GERAR_RELATORIO_SITUACAO_DOS_AVISOS_BANCARIOS = 357;

	public static final int GERAR_RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS = 358;

	public static final int RELATORIO_AUDITORIA_LEITURA = 359;

	public static final int RELATORIO_COMANDO_OS_SELETIVA = 90026;

	public static final int EFETIVAR_ATUALIZACAO_INSCRICAO_IMOVEL = 331;

	public static final int RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA = 364;

	public static final int RELATORIO_ACOMP_MOV_ARRECADADORES = 368;

	public static final int RELATORIO_MAIORES_CONSUMIDORES = 366;

	public static final int RELATORIO_MAIORES_DEVEDORES = 367;

	public static final int GERAR_RELATORIO_QUADRO_HIDROMETROS = 369;

	public static final int GERAR_RELATORIO_QUADRO_HIDROMETROS_ANO_INSTALACAO = 370;

	public static final int GERAR_RELATORIO_QUADRO_HIDROMETROS_SITUACAO = 371;

	public static final int GERAR_RELATORIO_OS_ENCERRADA_DENTRO_FORA_PRAZO = 375;

	public static final int GERAR_RELATORIO_MANTER_USUARIO = 376;

	public static final int GERAR_RELATORIO_MATERIAIS_APLICADOS = 378;

	public static final int GERAR_RELATORIO_CONTRATO_DEMANDA_CONSUMO = 379;

	public static final int RELATORIO_DADOS_IMOVEIS_COM_CONTA_EM_ATRASO = 384;

	public static final int GERAR_RELATORIO_SITUACAO_ESPECIAL_FATURAMENTO = 383;

	public static final int GERAR_RELATORIO_ANALITICO_FATURAMENTO = 386;

	public static final int COMANDAR_PRESCRICAO_DEBITOS_USUARIO = 380;

	public static final int COMANDAR_PRESCRICAO_DEBITOS_AUTOMATICA = 381;

	public static final int RELATORIO_DEBITOS_PRESCRITOS = 382;

	public static final int RELATORIO_ACOMPANHAMENTO_DEBITOS_PRESCRITOS = 385;

	public static final int RELATORIO_REGISTRAR_LEITURAS_ANORMALIDADES = 389;

	public static final int AJUSTAR_LANCAMENTOS_CONTABEIS_SINTETICOS = 90027;

	public static final int RELATORIO_ANALITICO_CONTAS = 90029;

	public static final int RELATORIO_MANTER_ATIVIDADE_ECONOMICA = 390;

	public static final int RELATORIO_CONTAS_RECALCULADAS = 392;

	public static final int RELATORIO_RESUMO_ORDENS_SERVICO_DOC_COB = 392;

	public static final int IMPORTAR_DADOS_DIVIDA_ATIVA = 90031;

	public static final int RELATORIO_MANTER_ORDEM_SERVICO = 90034;

	public static final int RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS = 90035;

	public static final int RELATORIO_TOTAL_CONTAS_EMITIDAS_LOCALIDADE = 90037;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricaoProcesso;

	/** nullable persistent field */
	private String descricaoAbreviada;

	/** persistent field */
	private short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private Set processosFuncionalidade;

	/** persistent field */
	private Set processosIniciados;

	/** persistent field */
	private ProcessoTipo processoTipo;

	/** full constructor */
	public Processo(String descricaoProcesso, String descricaoAbreviada, short indicadorUso, Date ultimaAlteracao,
					ProcessoTipo processoTipo, Set processosFuncionalidade, Set processosIniciados) {

		this.descricaoProcesso = descricaoProcesso;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.processosFuncionalidade = processosFuncionalidade;
		this.processosIniciados = processosIniciados;
		this.processoTipo = processoTipo;
	}

	/** default constructor */
	public Processo() {

	}

	/** minimal constructor */
	public Processo(short indicadorUso, ProcessoTipo processoTipo, Set processosFuncionalidade, Set processosIniciados) {

		this.indicadorUso = indicadorUso;
		this.processosFuncionalidade = processosFuncionalidade;
		this.processosIniciados = processosIniciados;
		this.processoTipo = processoTipo;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricaoProcesso(){

		return this.descricaoProcesso;
	}

	public void setDescricaoProcesso(String descricaoProcesso){

		this.descricaoProcesso = descricaoProcesso;
	}

	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Set getProcessosFuncionalidade(){

		return this.processosFuncionalidade;
	}

	public void setProcessosFuncionalidade(Set processosFuncionalidade){

		this.processosFuncionalidade = processosFuncionalidade;
	}

	public Set getProcessosIniciados(){

		return this.processosIniciados;
	}

	public void setProcessosIniciados(Set processosIniciados){

		this.processosIniciados = processosIniciados;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public ProcessoTipo getProcessoTipo(){

		return processoTipo;
	}

	public void setProcessoTipo(ProcessoTipo processoTipo){

		this.processoTipo = processoTipo;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

}
