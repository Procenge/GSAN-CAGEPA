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

package gcom.util;

/**
 * Esta interface serve para definir os servicos que rodarão pelo mecanismo de
 * batch no controlador MDB correspondente
 * 
 * @author Rodrigo Silveira 05/12/2005
 */
public interface MetodosBatch {

	int ENDERECO_INSERIR_CEP_IMPORTADOS = 1;

	int CONSISTIR_LEITURAS_CALCULAR_CONSUMOS = 2;

	int REGISTRAR_MOVIMENTOS_ARRECADADORES = 3;

	int REGISTRAR_LEITURAS_ANORMALIDADES = 4;

	int GERAR_MOVIMENTO_DEBITO_AUTOMATICO_BANCO = 5;

	int REGERAR_MOVIMENTO_DEBITO_AUTOMATICO_BANCO = 6;

	int GERAR_ARQUIVO_TEXTO_FATURAMENTO = 7;

	int GERAR_RELATORIO_ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES = 8;

	int GERAR_RELATORIO_MAPA_CONTROLE_CONTA = 9;

	int REGISTRAR_FATURAMENTO_IMEDIATO = 10;

	int GERAR_MOVIMENTO_COBRANCA_BANCARIA = 11;

	int REGERAR_MOVIMENTO_COBRANCA_BANCARIA = 12;

	int VERIFICAR_FATURAMENTO_IMOVEIS_COM_SERVICO_COBRADO_ERRADO_DESO_RELACAO_CINCO = 13;

	int AJUSTAR_FATURAMENTO_SERVICOS_VALOR_TRUNCADO_DESO = 14;

	int AJUSTAR_FATURAMENTO_TARIFAS_DESO = 15;

	int CANCELAR_DEBITO_A_COBRAR_AJUSTE_FATURAMENTO_DESO = 16;

	int VERIFICAR_FATURAMENTO_IMOVEIS_COM_SERVICO_COBRADO_ERRADO_DESO_RELACAO_UM_E_DOIS = 17;

	int AJUSTAR_FATURAMENTO_CONTA = 18;

	int AJUSTAR_PAGAMENTOS_A_MAIOR = 19;

	int AJUSTAR_CONTA_RETIFICAR = 20;

	int AJUSTAR_CONTABILIDADE_ARRECADACAO_DESO = 21;

	int EXECUTAR_CONVERSAO_CONTAS_PARA_GUIA_DE_PAGAMENTO = 22;

	int CANCELAR_DEBITOS = 23;

	int AJUSTAR_CONTAS_RETIFICAR_RETIRAR_DEBITO_RATEIO_DUPLICADO = 24;

	int INSERIR_DEBITOS = 25;

	int GERAR_RESUMO_FATURAMENTO_SIMULACAO_AJUSTE_GRUPO_CASAL = 26;

	int DESFAZER_PRE_FATURAMENTO_POR_GRUPO_REF = 27;

	int AJUSTAR_VALOR_DEBITOS_COBRADOS_CASAL = 28;

	int AJUSTAR_CONTAS_PRE_FAT = 29;

	int AJUSTAR_CONTAS_HISTORICO = 30;

	int AJUSTAR_CONTAS_HISTORICO_RETIDAS_ZERADAS = 31;

	int AJUSTAR_COORDENADAS_RA = 32;

	int AJUSTAR_MEDIAS_ERRADAS_CONSUMO_HISTORICO = 33;

	int REGERAR_HISTOGRAMA = 34;

	int GERAR_DEBITO_CONTA_COM_VALOR_MENOR = 35;

	int AJUSTAR_FAIXAS_CONTAS_ERRADAS = 36;

	int AJUSTE_ENVIAR_CONTAS_ZERADAS_PARA_HISTORICO = 37;

	int AJUSTE_RESUMO_ACAO_COBRANCA = 38;

	int AJUSTE_BATIMENTO_VALORES_CALCULAR_CONTA = 39;

	int AJUSTE_CLIENTE_DEBITO_A_COBRAR = 40;

	int AJUSTE_CONTAS_LOCALIDADE_062_DESO = 41;

	int AJUSTE_CONVERSAO_ACORDO_TAC = 45;

	int GERAR_CREDITO_A_REALIZAR_AJUSTE = 46;

}
