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

package gcom.seguranca.acesso;

import gcom.util.filtro.Filtro;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Funcionalidade
				extends TabelaAuxiliarAbreviada {

	private static final long serialVersionUID = 1L;

	public static final int GERAR_FATURAMENTO_IMEDIATO = 918;

	public static final int REGISTRAR_FATURAMENTO_IMEDIATO = 919;

	public static final int CONSISTIR_LEITURAS_E_CALCULAR_CONSUMOS = 52;

	public static final int GERAR_DADOS_PARA_LEITURA = 185;

	public static final int FATURAR_GRUPO_FATURAMENTO = 63;

	public static final int EFETUAR_RATEIO_CONSUMO = 186;

	public static final int VERIFICAR_ANORMALIDADES_CONSUMO = 2838;

	public static final int GERAR_TAXA_ENTREGA_CONTA_OUTRO_ENDERECO = 187;

	public static final int GERAR_DEBITOS_A_COBRAR_ACRESCIMOS_IMPONTUALIDADE = 188;

	public static final int GERAR_DADOS_DIARIOS_ARRECADACAO = 457;

	public static final int GERAR_ATIVIDADE_ACAO_COBRANCA = 539;

	public static final int ENCERRAR_ATIVIDADE_ACAO_COBRANCA = 457; // AJEITAR

	public static final int EMITIR_CONTAS = 64;

	public static final int GERAR_DEBITO_COBRAR_DOACAO = 547;

	public static final int CLASSIFICAR_PAGAMENTOS_DEVOLUCOES = 545;

	public static final int ENCERRAR_ARRECADACAO_MES = 546;

	public static final int ENCERRAR_FATURAMENTO_MES = 552;

	public static final int GERAR_RESUMO_LIGACOES_ECONOMIAS = 570;

	public static final int GERAR_RESUMO_SITUACAO_ESPECIAL_FATURAMENTO = 573;

	public static final int GERAR_RESUMO_ACOES_COBRANCA_CRONOGRAMA = 576;

	public static final int GERAR_RESUMO_ACOES_COBRANCA_CRONOGRAMA_ENCERRAR_OS = 1056;

	public static final int GERAR_RESUMO_ACOES_COBRANCA_EVENTUAL = 750;

	public static final int EMITIR_CONTAS_ORGAO_PUBLICO = 794;

	public static final int INSERIR_RESUMO_ACOES_COBRANCA_CRONOGRAMA = 672;

	public static final int INSERIR_RESUMO_ACOES_COBRANCA_EVENTUAL = 751;

	public static final int GERAR_RESUMO_PENDENCIA = 582;

	public static final int GERAR_RESUMO_ANORMALIDADES = 592;

	public static final int GERAR_RESUMO_SITUACAO_ESPECIAL_COBRANCA = 598;

	public static final int EMITIR_EXTRATO_CONSUMO_IMOVEL_CONDOMINIO = 611;

	public static final int GERAR_FATURA_CLIENTE_RESPONSAVEL = 629;

	public static final int GERAR_HISTORICO_PARA_ENCERRAR_ARRECADACAO_MES = 642;

	public static final int GERAR_HISTORICO_PARA_ENCERRAR_FATURAMENTO_MES = 647;

	public static final int DESFAZER_PARCELAMENTO_POR_ENTRADA_NAO_PAGA = 652;

	public static final int GERAR_HISTORICO_CONTA = 653;

	public static final int GERAR_RESUMO_INSTALACOES_HIDROMETROS = 670;

	public static final int GERAR_RESUMO_CONSUMO_AGUA = 671;

	public static final int GERAR_RESUMO_LEITURA_ANORMALIDADE = 673;

	public static final int GERAR_RESUMO_ARRECADACAO = 694;

	public static final int GERAR_RESUMO_PARCELAMENTO = 703;

	public static final int GERAR_RESUMO_FATURAMENTO_AGUA_ESGOTO = 695;

	public static final int GERAR_RESUMO_REFATURAMENTO = 910;

	public static final int GERAR_LANCAMENTOS_CONTABEIS_FATURAMENTO = 681;

	public static final int GERAR_LANCAMENTOS_CONTABEIS_ARRECADACAO = 700;

	public static final int GERAR_RESUMO_HISTOGRAMA_AGUA_ESGOTO = 706;

	public static final int GERAR_RESUMO_HIDROMETRO = 685;

	public static final int GERAR_RESUMO_REGISTRO_ATENDIMENTO = 720;

	public static final int EMITIR_BOLETIM_CADASTRO = 877;

	public static final int GERAR_RESUMO_DEVEDORES_DUVIDOSOS = 714;

	public static final int GERAR_LANCAMENTOS_CONTABEIS_DEVEDORES_DUVIDOSOS = 755;

	public static final int GERAR_RESUMO_METAS = 783;

	public static final int GERAR_RESUMO_METAS_ACUMULADO = 786;

	public static final int GERAR_ARQUIVO_TEXTO_PARA_LEITURISTA = 804;

	public static final int GERAR_RESUMO_COLETA_ESGOTO = 820;

	public static final int GERAR_CONTAS_A_RECEBER_CONTABIL = 847;

	public static final int GERAR_MOVIMENTO_EXCLUSAO_NEGATIVACAO_ONLINE = 1061; // Código Embasa -

	// 854

	public static final int GERAR_RESUMO_DIARIO_NEGATIVACAO = 875;

	public static final int DETERMINAR_CONFIRMACAO_NEGATIVACAO = 2521;

	public static final int EXECUTAR_COMANDO_DE_NEGATIVACAO = 876;

	public static final int EXECUTAR_COMANDO_DE_ENCERRAMENTO_RA = 894;

	public static final int GERAR_VALOR_VOLUMES_CONSUMIDOS_NAO_FATURADOS = 898;

	public static final int GERAR_RESUMO_INDICADORES_COMERCIALIZACAO = 901;

	public static final int GERAR_RESUMO_INDICADORES_MICROMEDICAO = 902;

	public static final int GERAR_RESUMO_INDICADORES_FATURAMENTO = 906;

	public static final int GERAR_RESUMO_INDICADORES_COBRANCA = 907;

	public static final int ATUALIZA_QUANTIDADE_PARCELA_PAGA_CONSECUTIVA_PARCELA_BONUS = 895;

	public static final int PRE_FATURAR_GRUPO_FATURAMENTO = 0;

	public static final int GERAR_RELATORIO_CONTAS_BAIXADAS_CONTABILMENTE = 916;

	public static final int ATUALIZAR_LIGACAO_AGUA_LIGADO_ANALISE_PARA_LIGADO = 1082;

	public static final int GERAR_TALELAS_TEMPORARIAS_ATUALIZACAO_CADASTRAL = 1088;

	public static final int GERAR_ARQUIVO_TEXTO_ATUALIZACAO_CADASTRAL = 1152;

	public static final int GERAR_DIFERENCA_ARQUIVO_TEXTO_ATUALIZACAO_CADASTRAL = 1207;

	public static final int INSERIR_REGISTRO_ATENDIMENTO = 214;

	public static final int REATIVAR_REGISTRO_ATENDIMENTO = 403;

	public static final int GERAR_RESUMO_FATURAMENTO = 1103;

	public static final int INCLUIR_DEBITO_A_COBRAR_ENTRADA_PARCELAMENTO_NAO_PAGA = 1118;

	public static final int ATUALIZAR_DIFERENCA_ACUMULADA_NO_MES = 1119;

	public static final int ATUALIZAR_PAGAMENTOS_CONTAS_COBRANCA = 1154;

	public static final int GERAR_MOVIMENTO_CONTAS_COBRANCA_POR_EMPRESA = 1158;

	public static final int ATUALIZAR_NUMERO_EXECUCAO_RESUMO_NEGATIVACAO = 1168;

	// public static final int GERAR_MOVIMENTO_EXCLUSAO_NEGATIVACAO = 1169;
	public static final int GERAR_MOVIMENTO_EXCLUSAO_NEGATIVACAO = 854;

	public static final int GERAR_RESUMO_REFATURAMENTO_OLAP = 1164;

	public static final int GERAR_MOVIMENTO_RETORNO_NEGATIVACAO = 1073; // Código Embasa 892

	public static final int GERAR_CREDITO_SITUACAO_ESPECIAL_FATURAMENTO = 1183;

	public static final int GERAR_MOVIMENTO_EXTENSAO_CONTAS_COBRANCA_POR_EMPRESA = 1189;

	public static final int ATUALIZAR_AUTOS_INFRACAO_PRAZO_RECURSO_VENCIDO = 1208;

	public static final int MOVIMENTO_HIDROMETRO = 145;

	public static final int GERAR_CARTAS_CAMPANHA_SOLIDARIEDADE_CRIANCA_PARA_NEGOCIACAO_A_VISTA = 1216;

	public static final int EMITIR_BOLETOS = 1219;

	public static final int RETIFICAR_CONJUNTO_CONTA = 1160;

	public static final int GERAR_BONUS_TARIFA_SOCIAL = 1234;

	public static final int EXCLUIR_IMOVEIS_DA_TARIFA_SOCIAL = 1236;

	public static final int ACOMPANHAR_PAGAMENTO_DO_PARCELAMENTO = 1254;

	public static final int GERAR_CARTAS_DE_FINAL_DE_ANO = 1255;

	public static final int BAIXA_ORDEM_SERVICO_COBRANCA = 1091;

	public static final int EMITIR_RELATORIO = 696;

	public static final int INFORMAR_DADOS_LEITURA_E_ANORMALIDADE = 2377;

	public static final int GERAR_ARQUIVO_TEXTO_FATURAMENTO_IMEDIATO = 2462;

	public static final int INDICAR_COBRANCA_BANCARIA_COM_NEGOCIACAO = 2526;

	public static final int GERAR_ARQUIVO_IMOVEIS_COBRANCA = 2525;

	public static final int GERAR_PROVISAO_DEVEDORES_DUVIDOSOS = 2761;

	public static final int EMITIR_RELACAO_DOCUMENTOS_COBRANCA = 2831;

	public static final int GERAR_MULTA_POR_DESCUMPRIMENTO_PARCELAMENTO = 2840;

	public static final int REGISTRAR_MOVIMENTO_ARRECADADORES = 398;

	public static final int GERAR_ENVIAR_RELATORIO_RESUMO_MOVIMENTO_ARRECADACAO = 2845;

	public static final int GERAR_ARQUIVO_AGENCIA_VIRTUAL_WEB = 2848;

	public static final int RETIRAR_CONTA_REVISAO_PRAZO_VENCIDO = 2867;

	public static final int GERAR_AVISO_CORTE_FATURAMENTO = 2868;

	public static final int GERAR_RESUMO_ANORMALIDADE_CONSUMO = 2873;

	public static final int GERAR_INTEGRACAO_CONTABIL = 2882;

	public static final int GERAR_INTEGRACAO_CONTABIL_AJUSTE_TEMP = 2961;

	public static final int GERAR_INTEGRACAO_RETENCAO = 2883;

	public static final int GERAR_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS = 2893;

	public static final int GERAR_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS = 2896;

	public static final int GERAR_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS = 2895;

	public static final int GERAR_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS = 2894;

	public static final int CONCATENAR_ARQUIVOS_AGENCIA_VIRTUAL = 2899;

	public static final int GERAR_INTEGRACAO_DEFERIMENTO = 2908;

	public static final int ATUALIZAR_SITUACAO_DEBITO_E_DA_ACAO_DOS_AVISOS_CORTE_E_CORTE_INDIVIDUAL = 2913;

	public static final int BATCH_ENCERRAR_FATURAMENTO_GERAR_RESUMO_LIGACOES_ECONOMIAS = 2919;

	public static final int BATCH_GERAR_RESUMO_LIGACOES_ECONOMIAS = 2917;

	public static final int GERAR_PROVISAO_RECEITA = 2921;

	public static final int ATUALIZAR_PDD_PARA_ENCERRAR_ARRECADACAO = 2922;

	public static final int ENCERRAR_ARRECADACAO = 2923;

	public static final int CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS = 2911;

	public static final int EFETIVAR_ATUALIZACAO_INSCRICAO_IMOVEL = 2927;

	public static final int GERAR_INTEGRACAO_SPED_PIS_COFINS = 2959;

	public static final int GERAR_DECLARACAO_ANUAL_QUITACAO_DEBITOS = 2938;

	public static final int EMITIR_DECLARACAO_ANUAL_QUITACAO_DEBITOS = 2939;

	public static final int MARCAR_ITENS_REMUNERAVEIS_COBRANCA_ADMINISTRATIVA = 2941;

	public static final int GERAR_INTEGRACAO_ACQUAGIS_CONTA_ATUALIZADA = 2944;

	public static final int CANCELAR_AVISO_DE_CORTE_PENDENTE = 2963;

	public static final int BATCH_RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA_MDB = 534;

	public static final int BATCH_RELATORIO_ACOMP_MOV_ARRECADADORES_MDB = 638;

	public static final int CONSULTAR_CONTA = 161;

	public static final int EMITIR_EXTRATO_DEBITO = 797;

	public static final int MANTER_CONTA = 44;

	public static final int COLOCAR_CONTA_EM_REVISAO = 196;

	public static final int RETIRAR_CONTA_DE_REVISAO = 197;

	public static final int CANCELAR_CONTA = 198;

	public static final int RETIFICAR_CONTA = 200;

	public static final int MANTER_CONJUNTO_CONTAS_IMOVEL = 633;

	public static final int CANCELAR_CONJUNTO_CONTAS_IMOVEL = 635;

	public static final int ALTERAR_VENCIMENTO_CONJUNTO_CONTAS_IMOVEL = 636;

	public static final int RETIRAR_DEBITO_COBRADO_CONJUNTO_CONTAS_IMOVEL = 640;

	public static final int RETIRAR_VALOR_AGUA_ESGOTO_CONJUNTO_CONTAS_IMOVEL = 2977;

	public static final int ALTERAR_VENCIMENTO_CONTA = 199;

	public static final int RETIRAR_REVISAO_CONJUNTO_CONTAS_IMOVEL = 9258;

	public static final int AJUSTAR_ARRECADADOR_MOVIMENTO_ITEM = 2988;

	public static final int SIMULAR_FATURAMENTO = 9262;

	public static final int GERAR_QUADRO_COMPARATIVO_FATURAMENTO_ARRECADACAO = 9313;

	public static final int COMANDAR_PRESCRICAO_DEBITOS_USUARIO = 9286;

	public static final int COMANDAR_PRESCRICAO_DEBITOS_AUTOMATICA = 9289;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private String descricaoAbreviada;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private String caminhoMenu;

	/** nullable persistent field */
	private String caminhoUrl;

	/** nullable persistent field */
	private Short indicadorPontoEntrada;

	private Long numeroOrdemMenu;

	private Short indicadorNovaJanela;

	/** persistent field */
	private gcom.seguranca.acesso.Modulo modulo;

	private Set funcionalidadeDependencias = null;

	private Set funcionalidadeDependenciasByFncdIddependencia = null;

	private Set operacoes = null;

	private Set<Argumento> argumentos = null;

	private String caminhoAjuda;

	/** full constructor */
	public Funcionalidade(String descricao, String descricaoAbreviada, Date ultimaAlteracao, String caminhoMenu, String caminhoUrl,
							Short indicadorPontoEntrada, gcom.seguranca.acesso.Modulo modulo) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.ultimaAlteracao = ultimaAlteracao;
		this.caminhoMenu = caminhoMenu;
		this.caminhoUrl = caminhoUrl;
		this.indicadorPontoEntrada = indicadorPontoEntrada;
		this.modulo = modulo;
	}

	/** full constructor */
	public Funcionalidade(String descricao, String descricaoAbreviada, Date ultimaAlteracao, String caminhoMenu, String caminhoUrl,
							Short indicadorPontoEntrada, gcom.seguranca.acesso.Modulo modulo, Set funcionalidadeDependencias,
							Set funcionalidadeDependenciasByFncdIddependencia, Set operacoes) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.ultimaAlteracao = ultimaAlteracao;
		this.caminhoMenu = caminhoMenu;
		this.caminhoUrl = caminhoUrl;
		this.indicadorPontoEntrada = indicadorPontoEntrada;
		this.modulo = modulo;
		this.funcionalidadeDependencias = funcionalidadeDependencias;
		this.funcionalidadeDependenciasByFncdIddependencia = funcionalidadeDependenciasByFncdIddependencia;
		this.operacoes = operacoes;
	}

	/** default constructor */
	public Funcionalidade() {

	}

	/**
	 * @return Retorna o campo argumento.
	 */
	public Set<Argumento> getArgumentos(){

		return argumentos;
	}

	/**
	 * @param argumentos
	 *            Os argumentos a ser setado.
	 */
	public void setArgumentos(Set<Argumento> argumentos){

		this.argumentos = argumentos;
	}

	/**
	 * @return Retorna o campo operacoes.
	 */
	public Set getOperacoes(){

		return operacoes;
	}

	/**
	 * @param operacoes
	 *            O operacoes a ser setado.
	 */
	public void setOperacoes(Set operacoes){

		this.operacoes = operacoes;
	}

	/** minimal constructor */
	public Funcionalidade(gcom.seguranca.acesso.Modulo modulo) {

		this.modulo = modulo;
	}

	/**
	 * @return Retorna o campo funcionalidadeDependencias.
	 */
	public Set getFuncionalidadeDependencias(){

		return funcionalidadeDependencias;
	}

	/**
	 * @param funcionalidadeDependencias
	 *            O funcionalidadeDependencias a ser setado.
	 */
	public void setFuncionalidadeDependencias(Set funcionalidadeDependencias){

		this.funcionalidadeDependencias = funcionalidadeDependencias;
	}

	/**
	 * @return Retorna o campo funcionalidadeDependenciasByFncdIddependencia.
	 */
	public Set getFuncionalidadeDependenciasByFncdIddependencia(){

		return funcionalidadeDependenciasByFncdIddependencia;
	}

	/**
	 * @param funcionalidadeDependenciasByFncdIddependencia
	 *            O funcionalidadeDependenciasByFncdIddependencia a ser setado.
	 */
	public void setFuncionalidadeDependenciasByFncdIddependencia(Set funcionalidadeDependenciasByFncdIddependencia){

		this.funcionalidadeDependenciasByFncdIddependencia = funcionalidadeDependenciasByFncdIddependencia;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return this.descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getCaminhoMenu(){

		return this.caminhoMenu;
	}

	public void setCaminhoMenu(String caminhoMenu){

		this.caminhoMenu = caminhoMenu;
	}

	public String getCaminhoUrl(){

		return this.caminhoUrl;
	}

	public void setCaminhoUrl(String caminhoUrl){

		this.caminhoUrl = caminhoUrl;
	}

	public Short getIndicadorPontoEntrada(){

		return this.indicadorPontoEntrada;
	}

	public void setIndicadorPontoEntrada(Short indicadorPontoEntrada){

		this.indicadorPontoEntrada = indicadorPontoEntrada;
	}

	public gcom.seguranca.acesso.Modulo getModulo(){

		return this.modulo;
	}

	public void setModulo(gcom.seguranca.acesso.Modulo modulo){

		this.modulo = modulo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Long getNumeroOrdemMenu(){

		return numeroOrdemMenu;
	}

	public void setNumeroOrdemMenu(Long numeroOrdemMenu){

		this.numeroOrdemMenu = numeroOrdemMenu;
	}

	public Short getIndicadorNovaJanela(){

		return indicadorNovaJanela;
	}

	public void setIndicadorNovaJanela(Short indicadorNovaJanela){

		this.indicadorNovaJanela = indicadorNovaJanela;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	public void setCaminhoAjuda(String caminhoAjuda){

		this.caminhoAjuda = caminhoAjuda;
	}

	public String getCaminhoAjuda(){

		return caminhoAjuda;
	}

}
