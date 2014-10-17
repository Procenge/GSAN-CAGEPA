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

package gcom.seguranca.acesso;

import gcom.interceptor.ObjetoGcom;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PermissaoEspecial
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	public static final int IMOVEL_EM_SITUACAO_COBRANCA = 6;

	public static final int PARCELAR_SEM_INCLUIR_DEBITO_A_COBRAR = 14;

	public static final int PARCELAR_DEBITO_A_COBRAR = 15;

	public static final int PARCELAR_SEM_INCLUIR_ACRESCIMOS_POR_IMPONTUALIDADE = 18;

	public static final int USAR_PLANO_PAI_PARA_ORGAO_PUBLICO = 19;

	public static final int TESTAR_VAL_MINIMO_PRESTACAO = 21;

	public static final int TESTAR_VAL_MINIMO_ENTRADA = 22;

	public static final int CLIENTE_USUARIO_TARIFA_SOCIAL = 23;

	public static final int ALTERAR_IMOVEL = 24;

	public static final int REMOVER_CATEGORIA_NAO_RESIDENCIAL_IMOVEL = 25;

	public static final int ATUALIZAR_LOGRADOURO_BAIRRO = 28;

	public static final int INSERIR_IMOVEL_MUNICIPIO_LOGRADOURO_DIFERENTE_SETOR = 26;

	public static final int ATUALIZAR_IMOVEL_MUNICIPIO_LOGRADOURO_DIFERENTE_SETOR = 27;

	public static final int INSERIR_DEBITO_A_COBRAR_SEM_ENTRADA_SEM_JUROS = 29;

	public static final int INFORMAR_MOTIVO_NAO_COBRANCA = 30;

	public static final int GERACAO_DEBITO_OS_FISCALIZACAO = 31;

	public static final int INFORMAR_VENCIMENTO_ALTERNATIVO_NOVA_DATA = 32;

	public static final int PARCELAR_NAO_TESTAR_QTDE_DE_PRESTACAO = 33;

	public static final int INFORMAR_VENCIMENTO_ALTERNATIVO_ANTES_DO_PERIODO_VALIDO = 34;

	public static final int ALTERAR_VENCIMENTO_CONTA_SEM_RA = 35;

	public static final int INSERIR_CONTA_FATURAMENTO_ANTECIPADO = 36;

	public static final int INSERIR_DEBITO_A_COBRAR_SEM_RA = 37;

	public static final int INSERIR_GUIA_DE_PAGAMENTO_SEM_RA = 38;

	public static final int ALTERAR_NOME_CLIENTE = 39;

	public static final int INCLUIR_DEVOLUCAO_MAIOR_VALOR_MAXIMO = 40;

	public static final int INCLUIR_CREDITO_A_REALIZAR_VALOR_MAXIMO = 41;

	public static final int INCLUIR_CREDITO_A_REALIZAR_QUANTIDADE_PARCELAS_MAXIMO = 42;

	public static final int INCLUIR_ACRESCIMO_IMPONTUALIDADE_NO_EXTRATO_DE_DEBITOS_COM_DESCONTO = 43;

	public static final int RETIRAR_TAXA_COBRANCA_DO_EXTRATO_DE_DEBITOS = 44;

	public static final int CONSULTAR_DEBITOS_INDICADO_NA_CONTA_OU_TODOS = 45;

	public static final int INSERIR_DEBITO_A_COBRAR_IMOVEL_SITUACAO = 46;

	public static final int REINICIAR_BATCH = 47;

	public static final int RETIFICAR_CONTA_SEM_RA = 48;

	public static final int EXCLUIR_DEBITO_A_COBRAR = 49;

	public static final int GERAR_OS_SELETIVA_HIDROMETRO = 50;

	public static final int CANCELAR_CONTA_SEM_RA = 51;

	public static final int INCLUIR_GUIA_DE_PAGAMENTO_SEM_NUMERO_PARCELAS_MAXIMO = 51;

	public static final int ATUALIZAR_DADOS_FISCALIZACAO = 52;

	public static final int INSERIR_CREDITO_A_REALIZAR_IMOVEL_COM_DEBITO = 52;

	public static final int DESFAZER_FISCALIZACAO_BOLETIM_OS_CONCLUIDA = 53;

	public static final int CANCELAR_CREDITO_A_REALIZAR_IMOVEL_COM_DEBITO = 53;

	public static final int VISUALIZAR_DIA_VENCIMENTO_CLIENTE = 54;

	public static final int CANCELAR_CREDITO_A_REALIZAR_VALOR_SUPERIOR = 54;

	public static final int INSERIR_IMOVEL_COM_PERFIL_CORPORATIVO = 55;

	public static final int CANCELAR_CREDITO_A_REALIZAR_PARCELAS_SUPERIOR = 55;

	public static final int PARCELAMENTO_COBRANCA_BANCARIA = 55;

	public static final int ALTERAR_PERFIL_CORPORATIVO_IMOVEL = 56;

	public static final int ALTERAR_DEBITO_A_COBRAR_VALOR_SERVICO = 56;

	public static final int INFORMAR_DATA_ENC_OS_ANTERIOR_DATA_CORRENTE = 57;

	public static final int RETIFICAR_PARA_MENOR_CONTA_RETIFICADA = 58;

	public static final int ALTERAR_DATA_LEITURA_MANTER_HIDROMETRO = 58;

	public static final int RETIFICAR_DATA_VENCIMENTO_ALEM_PRAZO_PADRAO = 59;

	public static final int RETIFICAR_CONTA_EM_COBRANCA_JUDICIAL_OU_SUBJUDICE = 59;

	public static final int ALTERAR_VENCIMENTO_JA_ALTERADO = 60;

	public static final int ALTERAR_TAXA_DE_JUROS_DE_FINANCIAMENTO = 60;

	public static final int ALTERAR_TARIFA_CONSUMO_RETIFICAR_CONTA = 61;

	public static final int ALTERAR_INDICADOR_USO_SISTEMA_TIPO_SOLICITACAO = 62;

	public static final int ALTERAR_VALOR_SUGERIDO_EM_TIPO_DEBITO = 64;

	public static final int INSERIR_IMOVEL_PARA_ORGAO_PUBLICO = 63;

	public static final int EFETUAR_LIGACAO_DE_AGUA_SEM_RA = 65;

	public static final int ATUALIZAR_LIGACAO_DE_AGUA_SEM_RA = 66;

	public static final int EFETUAR_LIGACAO_DE_ESGOTO_SEM_RA = 67;

	public static final int ATUALIZAR_LIGACAO_DE_ESGOTO_SEM_RA = 68;

	public static final int EFETUAR_LIGACAO_DE_AGUA_COM_INSTALACAO_DE_HIDROMETRO_SEM_RA = 69;

	public static final int ATUALIZAR_INSTALACAO_DO_HIDROMETRO = 70;

	public static final int ALTERAR_VALIDADE_EXTRATO_DEBITO = 71;

	public static final int VALIDAR_ACRESCIMOS_IMPONTUALIDADE = 72;

	public static final int EMITIR_2_VIA_SEM_DOCUMENTO_VALIDO = 73;

	public static final int RETIFICAR_CONJUNTO_CONTA = 74;

	public static final int EMITIR_CERTIDAO_NEGATIVA_COM_CLIENTE_SUPERIOR = 75;

	public static final int RETIRAR_CONTA_REVISAO_POR_ANTIGUIDADE = 78;

	public static final int ALTERAR_PERCENTUAL_COLETA_ESGOTO = 76;

	public static final int ATUALIZAR_RETIFICAR_CONTAS_PAGAS = 77;

	public static final int HABILITAR_BANCO_MANTER_CONTA = 80;

	public static final int EFETUAR_SUPRESSAO_DE_LIGACAO_AGUA = 79;

	public static final int ALTERAR_SITUACAO_LIGACAO_PARA_IMOVEL_COM_DEBITO = 81;

	public static final int GERAR_CERTIDAO_NEGATIVA_RESPONSABILIDAE_ATUAL_DO_IMOVEL = 82;

	public static final int RETIFICAR_CONTA_IMOVEL_PERFIL_BLOQUEADO = 83;

	public static final int RETIFICAR_CONTA_APENAS_VOLUME_ESGOTO = 84;

	public static final int EMITIR_DOCUMENTO_COBRANCA = 85;

	public static final int BLOQUEAR_ALTERACAO_IMOVEIS = 86;

	public static final int INSERIR_GUIA_PAGAMENTO_VALOR_DEBITO_MAIOR_LIMITE = 87;

	public static final int RETIFICAR_DATA_VENCIMENTO_ANTERIOR_OU_POSTERIOR_DATA_CORRENTE = 88;

	public static final int ALTERAR_PERCENTUAL_DE_ESGOTO = 89;

	public static final int IMPRIMIR_CANCELAR_CONTA_EM_COBRANCA_BANCARIA = 90;

	public static final int ACESSAR_DADOS_IMOVEL_COBRANCA_ADMINISTRATIVA = 91;

	public static final int GERAR_ORDEM_SERVICO_COM_RESTRICAO = 92;

	public static final int PARCELAR_SEM_INCLUIR_CREDITO_A_REALIZAR = 93;

	public static final int PARCELAR_CREDITO_A_REALIZAR = 94;

	public static final int PERMITIR_INFORMAR_CAMPO_OBRIGATORIO_SEM_VALOR = 95;

	public static final int PERMITIR_INFORMAR_QTD_PARCELAS_EXCEDENTES = 96;

	public static final int PERMITIR_INFORMAR_PERCENTUAL_COBRANCA_EXCEDENTES = 97;

	public static final int GERAR_DEVOLUCAO_DE_VALORES = 98;

	public static final int INFORMAR_ROTA_NAO_PERTENCENTE_AO_SETOR_COMERCIAL_DO_IMOVEL = 99;

	public static final int PERMITIR_NAO_COBRAR_TAXA_DE_2VIA = 100;

	public static final int ALTERAR_DADOS_PARCELAMENTO_TERMO_TESTEMUNHAS = 101;

	public static final int PERMITIR_NAO_COBRAR_TAXA_DE_EXTRATO_DEBITO = 102;

	public static final int REMOVER_CPF_CLIENTE = 103;

	public static final int PARCELAR_CONTAS_EM_REVISAO_COM_IMPEDIMENTO = 104;

	public static final int PARCELAR_IMOVEL_EM_SITUACAO_COBRANCA_COM_IMPEDIMENTO = 105;

	public static final int PERMITIR_EMITIR_EXTRATO_DEBITO_SEM_ACRESCIMO = 106;

	public static final int RETIRAR_CONTA_REVISAO_SEM_RA = 107;

	public static final int EMITIR_SEGUNDA_VIA_CONTA_RETIDA = 108;

	public static final int EMITIR_EXTRATO_DEBITO_CONTA_RETIDA = 109;

	public static final int MANTER_CONTA_RETIDA = 110;

	public static final int COLOCAR_CONTA_RETIDA_EM_REVISAO = 111;

	public static final int RETIRAR_CONTA_RETIDA_DE_REVISAO = 112;

	public static final int CANCELAR_CONTA_RETIDA = 113;

	public static final int RETIFICAR_CONTA_RETIDA = 114;

	public static final int MANTER_CONJUNTO_CONTAS_RETIDAS = 115;

	public static final int CANCELAR_CONJUNTO_CONTAS_RETIDAS = 116;

	public static final int ALTERAR_VENCIMENTO_CONJUNTO_CONTAS_RETIDAS = 117;

	public static final int RETIRAR_DEBITO_COBRADO_CONJUNTO_CONTAS_RETIDAS = 118;

	public static final int RETIRAR_VALOR_AGUA_ESGOTO_CONJUNTO_CONTAS_RETIDAS = 119;

	public static final int ALTERAR_VENCIMENTO_CONTA_RETIDA = 120;

	public static final int RETIRAR_REVISAO_CONJUNTO_CONTAS_RETIDAS = 122;

	public static final int ALTERAR_INDICADOR_PROCESSO_ADMINISTRATVIVO_JUDICIARIO = 124;

	public static final int ALTERAR_UNIDADE_ORGANIZACIONAL_INDEPENDENTE_DE_TIPO_UNIDADE = 128;

	public static final int COMANDAR_PRESCRICAO_DEBITO = 126;

	public static final int EMITIR_DOCUMENTO_PAGAVEL_PARA_DEBITO_PRESCRITO = 127;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private gcom.seguranca.acesso.Operacao operacao;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	/** full constructor */
	public PermissaoEspecial(String descricao, Short indicadorUso, Date ultimaAlteracao, gcom.seguranca.acesso.Operacao operacao) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.operacao = operacao;
	}

	/** default constructor */
	public PermissaoEspecial() {

	}

	/** minimal constructor */
	public PermissaoEspecial(gcom.seguranca.acesso.Operacao operacao) {

		this.operacao = operacao;
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

	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.seguranca.acesso.Operacao getOperacao(){

		return this.operacao;
	}

	public void setOperacao(gcom.seguranca.acesso.Operacao operacao){

		this.operacao = operacao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param other
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof PermissaoEspecial)){
			return false;
		}
		PermissaoEspecial castOther = (PermissaoEspecial) other;

		return (this.getId().equals(castOther.getId()));
	}

}
