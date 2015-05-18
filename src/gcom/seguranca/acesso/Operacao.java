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
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Operacao
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** AVISO_BANCARIO_PESQUISAR */
	public static final Integer OPERACAO_AVISO_BANCARIO_PESQUISAR = Integer.valueOf(1);

	/** AVISO_BANCARIO_REMOVER */
	public static final Integer OPERACAO_AVISO_BANCARIO_REMOVER = Integer.valueOf(2);

	/** AVISO_BANCARIO_ATUALIZAR */
	public static final Integer OPERACAO_AVISO_BANCARIO_ATUALIZAR = Integer.valueOf(3);

	/** AVISO_BANCARIO_INSERIR */
	public static final Integer OPERACAO_AVISO_BANCARIO_INSERIR = Integer.valueOf(4);

	/** AVISO_BANCARIO_MOVIMENTAR_PAGAMENTOS_E_DEVOLUCOES **/
	public static final Integer OPERACAO_AVISO_BANCARIO_MOVIMENTAR_PAGAMENTOS_DEVOLUCOES = Integer.valueOf(719);

	/** LOGRADOURO PESQUISAR */
	public static final Integer OPERACAO_LOGRADOURO_PESQUISAR = Integer.valueOf(5);

	/** IMOVEL INSERIR */
	public static final Integer OPERACAO_IMOVEL_INSERIR = Integer.valueOf(9);

	/** LOCALIDADE INSERIR */
	public static final Integer OPERACAO_LOCALIDADE_INSERIR = Integer.valueOf(10);

	/** LOCALIDADE REMOVER */
	public static final Integer OPERACAO_LOCALIDADE_REMOVER = Integer.valueOf(11);

	/** LOCALIDADE ATUALIZAR */
	public static final Integer OPERACAO_LOCALIDADE_ATUALIZAR = Integer.valueOf(12);

	/** LIGACAO AGUA EFETUAR */
	public static final Integer OPERACAO_LIGACAO_AGUA_EFETUAR = Integer.valueOf(257);

	public static final int OPERACAO_LIGACAO_AGUA_EFETUAR_INT = 257;

	/** BLOQUEAR DESBLOQUEAR ACESSO USUARIO */
	public static final Integer OPERACAO_ACESSO_USUARIO_ATUALIZAR = Integer.valueOf(14);

	/** QUADRA ATUALIZAR */
	public static final Integer OPERACAO_QUADRA_ATUALIZAR = Integer.valueOf(15);

	/** QUADRA INSERIR */
	public static final Integer OPERACAO_QUADRA_INSERIR = Integer.valueOf(16);

	/** IMOVEL ATUALIZAR */
	public static final Integer OPERACAO_IMOVEL_ATUALIZAR = Integer.valueOf(17);

	/** QUADRA REMOVER */
	public static final Integer OPERACAO_QUADRA_REMOVER = Integer.valueOf(18);

	/** SETOR_COMERCIAL_INSERIR */
	public static final Integer OPERACAO_SETOR_COMERCIAL_INSERIR = Integer.valueOf(19);

	/** SETOR_COMERCIAL_ATUALIZAR */
	public static final Integer OPERACAO_SETOR_COMERCIAL_ATUALIZAR = Integer.valueOf(20);

	/** HIDROMETRO_INSERIR */
	public static final Integer OPERACAO_HIDROMETRO_INSERIR = Integer.valueOf(21);

	/** HIDROMETRO_ATUALIZAR */
	public static final Integer OPERACAO_HIDROMETRO_ATUALIZAR = Integer.valueOf(245);

	/** SETOR_COMERCIAL_REMOVER */
	public static final Integer OPERACAO_SETOR_COMERCIAL_REMOVER = Integer.valueOf(23);

	/** HIDROMETRO_REMOVER */
	public static final Integer OPERACAO_HIDROMETRO_REMOVER = Integer.valueOf(244);

	/** ROTA INSERIR */
	public static final Integer OPERACAO_ROTA_INSERIR = Integer.valueOf(25);

	/** ROTA ATUALIZAR */
	public static final Integer OPERACAO_ROTA_ATUALIZAR = Integer.valueOf(26);

	/** ROTA REMOVER */
	public static final Integer OPERACAO_ROTA_REMOVER = Integer.valueOf(27);

	/** CLIENTE INSERIR */
	public static final Integer OPERACAO_CLIENTE_INSERIR = Integer.valueOf(28);

	/** SUBCATEGORIA INSERIR */
	public static final Integer OPERACAO_SUBCATEGORIA_INSERIR = Integer.valueOf(32);

	/** SUBCATEGORIA ATUALIZAR */
	public static final Integer OPERACAO_SUBCATEGORIA_ATUALIZAR = Integer.valueOf(33);

	/** SUBCATEGORIA REMOVER */
	public static final Integer OPERACAO_SUBCATEGORIA_REMOVER = Integer.valueOf(34);

	/** CATEGORIA INSERIR */
	public static final Integer OPERACAO_CATEGORIA_INSERIR = Integer.valueOf(35);

	/** CATEGORIA ATUALIZAR */
	public static final Integer OPERACAO_CATEGORIA_ATUALIZAR = Integer.valueOf(36);

	/** CATEGORIA REMOVER */
	public static final Integer OPERACAO_CATEGORIA_REMOVER = Integer.valueOf(37);

	/** CLIENTE ATUALIZAR */
	public static final Integer OPERACAO_CLIENTE_ATUALIZAR = Integer.valueOf(38);

	/** CLIENTE REMOVER */
	public static final Integer OPERACAO_CLIENTE_REMOVER = Integer.valueOf(39);

	/** LOGRADOURO INSERIR */
	public static final Integer OPERACAO_LOGRADOURO_INSERIR = Integer.valueOf(41);

	/** LOGRADOURO ATUALIZAR */
	public static final Integer OPERACAO_LOGRADOURO_ATUALIZAR = Integer.valueOf(42);

	/** LOGRADOURO REMOVER */
	public static final Integer OPERACAO_LOGRADOURO_REMOVER = Integer.valueOf(43);

	/** BAIRRO_INSERIR */
	public static final Integer OPERACAO_BAIRRO_INSERIR = Integer.valueOf(44);

	/** BAIRRO_ATUALIZAR */
	public static final Integer OPERACAO_BAIRRO_ATUALIZAR = Integer.valueOf(45);

	/** BAIRRO_REMOVER */
	public static final Integer OPERACAO_BAIRRO_REMOVER = Integer.valueOf(46);

	/** REMANEJAMENTO HIDROMETRO EFETUAR */
	public static final Integer OPERACAO_REMANEJAMENTO_HIDROMETRO_EFETUAR = Integer.valueOf(47);

	public static final int OPERACAO_REMANEJAMENTO_HIDROMETRO_EFETUAR_INT = 47;

	/** CORTE DE LIGACAO AGUA EFETUAR */
	public static final Integer OPERACAO_CORTE_LIGACAO_AGUA_EFETUAR = Integer.valueOf(48);

	public static final int OPERACAO_CORTE_LIGACAO_AGUA_EFETUAR_INT = 48;

	/** LIGACAO ESGOTO EFETUAR */
	public static final Integer OPERACAO_INSTALACAO_HIDROMETRO_EFETUAR = Integer.valueOf(49);

	public static final int OPERACAO_INSTALACAO_HIDROMETRO_EFETUAR_INT = 49;

	/** RELIGAÇÃO AGUA EFETUAR */
	public static final Integer OPERACAO_RELIGACAO_AGUA_EFETUAR = Integer.valueOf(50);

	public static final int OPERACAO_RELIGACAO_AGUA_EFETUAR_INT = 50;

	/** RESTABELECIMENTO LIGAÇÃO AGUA EFETUAR */
	public static final Integer OPERACAO_RESTABELECIMENTO_LIGACAO_AGUA_EFETUAR = Integer.valueOf(51);

	public static final int OPERACAO_RESTABELECIMENTO_LIGACAO_AGUA_EFETUAR_INT = 51;

	/** EFETUA ALTERAÇÃO DE SENHA */
	public static final Integer OPERACAO_EFETUAR_ALTERACAO_SENHA = Integer.valueOf(52);

	/** INSERIR CONTA */
	public static final Integer OPERACAO_INSERIR_CONTA = Integer.valueOf(53);

	/** INSERIR VENCIMENTO ALTERNATIVO */
	public static final Integer OPERACAO_INSERIR_VENCIMENTO_ALTERNATIVO = Integer.valueOf(54);

	/** EXCLUIR VENCIMENTO ALTERNATIVO */
	public static final Integer OPERACAO_EXCLUIR_VENCIMENTO_ALTERNATIVO = Integer.valueOf(55);

	/** COLOCAR CONTA EM REVISÃO */
	public static final Integer OPERACAO_COLOCAR_CONTA_REVISAO = Integer.valueOf(56);

	/** RETIRAR CONTA DE REVISÃO */
	public static final Integer OPERACAO_RETIRAR_CONTA_REVISAO = Integer.valueOf(57);

	public static final Integer OPERACAO_USUARIO_INSERIR = Integer.valueOf(58);

	public static final Integer OPERACAO_USUARIO_INSERIR_DADOS_GERAIS = Integer.valueOf(92);

	public static final Integer OPERACAO_USUARIO_INSERIR_ACESSOS = Integer.valueOf(93);

	public static final Integer OPERACAO_USUARIO_ATUALIZAR = Integer.valueOf(59);

	public static final Integer OPERACAO_USUARIO_ATUALIZAR_DADOS_GERAIS = Integer.valueOf(97);

	public static final Integer OPERACAO_USUARIO_ATUALIZAR_ACESSOS = Integer.valueOf(98);

	public static final Integer OPERACAO_USUARIO_REMOVER = Integer.valueOf(60);

	public static final Integer OPERACAO_USUARIO_CONTROLAR_ACESSO = Integer.valueOf(61);

	/** CREDITO A REALIZAR INSERIR */
	public static final Integer OPERACAO_CREDITO_A_REALIZAR_INSERIR = Integer.valueOf(62);

	/** CREDITO A REALIZAR CANCELAR */
	public static final Integer OPERACAO_CREDITO_A_REALIZAR_CANCELAR = Integer.valueOf(66);

	/** CREDITO A REALIZAR ATUALIZAR */
	public static final Integer OPERACAO_CREDITO_A_REALIZAR_ATUALIZAR = Integer.valueOf(67);

	/** INSERIR DEBITO A COBRAR */
	public static final Integer OPERACAO_DEBITO_A_COBRAR_INSERIR = Integer.valueOf(70);

	/** CANCELAR DEBITO A COBRAR */
	public static final Integer OPERACAO_DEBITO_A_COBRAR_CANCELAR = Integer.valueOf(387);

	/** OPERACAO INSERIR */
	public static final Integer OPERACAO_OPERACAO_INSERIR = Integer.valueOf(72);

	/** PERFIL PARCELAMENTO INSERIR */
	public static final Integer OPERACAO_PERFIL_PARCELAMENTO_INSERIR = Integer.valueOf(80);

	/** CONTA MENSAGEM INSERIR */
	public static final Integer OPERACAO_CONTA_MENSAGEM_INSERIR = Integer.valueOf(81);

	/** PERFIL PARCELAMENTO INSERIR */
	public static final Integer OPERACAO_PERFIL_PARCELAMENTO_ATUALIZAR = Integer.valueOf(83);

	/** PERFIL PARCELAMENTO INSERIR */
	public static final Integer OPERACAO_PERFIL_PARCELAMENTO_REMOVER = Integer.valueOf(84);

	/** CONTA MENSAGEM INSERIR */
	public static final Integer OPERACAO_CONTA_MENSAGEM_ATUALIZAR = Integer.valueOf(85);

	/** CONTA MENSAGEM INSERIR */
	public static final Integer OPERACAO_CONTA_MENSAGEM_REMOVER = Integer.valueOf(86);

	/** OPERACAO ATUALIZAR */
	public static final Integer OPERACAO_OPERACAO_ATUALIZAR = Integer.valueOf(88);

	public static final Integer OPERACAO_ATENDIMENTO_PROCEDIMENTO_ATUALIZAR = Integer.valueOf(900243);

	/** OPERACAO REMOVER */
	public static final Integer OPERACAO_OPERACAO_REMOVER = Integer.valueOf(91);

	/** INSERIR CRONOGRAMA DE FATURAMENTO */
	public static final Integer OPERACAO_INSERIR_CRONOGRAMA_FATURAMENTO = Integer.valueOf(102);

	/** INSERIR CRONOGRAMA DE FATURAMENTO */
	public static final Integer OPERACAO_ATUALIZAR_CRONOGRAMA_FATURAMENTO = Integer.valueOf(106);

	/** INSERIR CRONOGRAMA DE COBRANCA */
	public static final Integer OPERACAO_INSERIR_CRONOGRAMA_COBRANCA = Integer.valueOf(108);

	/** INSERIR GUIA PAGAMENTO */
	public static final Integer OPERACAO_GUIA_PAGAMENTO_INSERIR = Integer.valueOf(115);

	/** INSERIR GUIA PAGAMENTO */
	public static final Integer OPERACAO_GUIA_PAGAMENTO_CANCELAR = Integer.valueOf(445);

	/** INSERIR CRONOGRAMA DE COBRANCA */
	public static final Integer OPERACAO_ATUALIZAR_CRONOGRAMA_COBRANCA = Integer.valueOf(117);

	/** CONJUNTO_HIDROMETRO_ATUALIZAR */
	public static final Integer OPERACAO_CONJUNTO_HIDROMETRO_ATUALIZAR = Integer.valueOf(120);

	/** DEVOLUCOES_INSERIR */
	public static final Integer OPERACAO_DEVOLUCOES_INSERIR = Integer.valueOf(126);

	/** DEVOLUCOES_ATUALIZAR */
	public static final Integer OPERACAO_DEVOLUCOES_ATUALIZAR = Integer.valueOf(127);

	/** DEVOLUCOES_REMOVER */
	public static final Integer OPERACAO_DEVOLUCOES_REMOVER = Integer.valueOf(128);

	/** IMSERIR SITUACAO IMOVEL */
	public static final Integer OPERACAO_IMOVEL_SITUACAO_INSERIR = Integer.valueOf(162);

	/** IMSERIR RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_RESOLUCAO_DIRETORIA_INSERIR = Integer.valueOf(138);

	/** LIGACAO ESGOTO EFETUAR */
	public static final Integer OPERACAO_LIGACAO_ESGOTO_EFETUAR = Integer.valueOf(272);

	public static final int OPERACAO_LIGACAO_ESGOTO_EFETUAR_INT = 272;

	/** COBRANCA_CRITERIO_INSERIR */
	public static final Integer OPERACAO_CRITERIO_COBRANCA_INSERIR = Integer.valueOf(139);

	/** ALTERAR INSCRICAO IMOVEL */
	public static final Integer OPERACAO_INSCRICAO_IMOVEL_ALTERAR = Integer.valueOf(142);

	/** COBRANCA_CRITERIO_ATUALIZAR */
	public static final Integer OPERACAO_CRITERIO_COBRANCA_ATUALIZAR = Integer.valueOf(143);

	/** COBRANCA_CRITERIO_ATUALIZAR */
	public static final Integer OPERACAO_CRITERIO_COBRANCA_REMOVER = Integer.valueOf(144);

	/** ATUALIZAR RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_RESOLUCAO_DIRETORIA_ATUALIZAR = Integer.valueOf(145);

	/** REMOVER RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_RESOLUCAO_DIRETORIA_REMOVER = Integer.valueOf(146);

	/** REMOVER RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_INSERIR_COMANDO_ACAO_COBRANCA_EVENTUAL_ROTA = Integer.valueOf(528);

	/** REMOVER RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_INSERIR_COMANDO_ACAO_COBRANCA_EVENTUAL_COMANDO = Integer.valueOf(529);

	/** REMOVER RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_INSERIR_COMANDO_ACAO_COBRANCA_CRONOGRAMA = Integer.valueOf(153);

	/** REMOVER RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_REMOVER_COMANDO_ACAO_COBRANCA_CRONOGRAMA = Integer.valueOf(154);

	/** REMOVER RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_REMOVER_COMANDO_ACAO_COBRANCA_EVENTUAL = Integer.valueOf(155);

	/** REMOVER RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_ATUALIZAR_COMANDO_ACAO_COBRANCA_EVENTUAL_COMANDO = Integer.valueOf(156);

	/** REMOVER RESOLUÇÃO DE DIRETORIA */
	public static final Integer OPERACAO_ATUALIZAR_COMANDO_ACAO_COBRANCA_EVENTUAL_ROTA = Integer.valueOf(157);

	/** FILTRAR RELACAO CLIENTE IMOVEL */
	public static final Integer OPERACAO_FILTRAR_RELACAO_CLIENTE_IMOVEL = Integer.valueOf(158);

	/** PAGAMENTO INSERIR */
	public static final Integer OPERACAO_PAGAMENTO_INSERIR_MANUAL = Integer.valueOf(165);

	public static final Integer OPERACAO_PAGAMENTO_INSERIR_CODIGOBARRAS = Integer.valueOf(167);

	/** PAGAMENTO ATUALIZAR */
	public static final Integer OPERACAO_PAGAMENTO_ATUALIZAR = Integer.valueOf(178);

	/** FILTRAR RELACAO CLIENTE IMOVEL */
	public static final Integer OPERACAO_MOVIMENTAR_HIDROMETRO = Integer.valueOf(179);

	/** PAGAMENTO REMOVER */
	public static final Integer OPERACAO_PAGAMENTO_REMOVER = Integer.valueOf(180);

	/** SUPRESSAO LIGACAO AGUA EFETUAR */
	public static final Integer OPERACAO_SUPRESSAO_LIGACAO_AGUA_EFETUAR = Integer.valueOf(685);

	public static final int OPERACAO_SUPRESSAO_LIGACAO_AGUA_EFETUAR_INT = 685;

	/** SUBSTITUICAO LIGACAO AGUA EFETUAR */
	public static final Integer OPERACAO_SUBSTITUICAO_HIDROMETRO_EFETUAR = Integer.valueOf(335);

	public static final int OPERACAO_SUBSTITUICAO_HIDROMETRO_EFETUAR_INT = 335;

	/** RETIRADA LIGACAO AGUA EFETUAR */
	public static final Integer OPERACAO_RETIRADA_HIDROMETRO_EFETUAR = Integer.valueOf(296);

	public static final int OPERACAO_RETIRADA_HIDROMETRO_EFETUAR_INT = 296;

	/** EFETUAR MUDANÇA DE SITUAÇÃO DE FATURAMENTO DA LIGAÇÃO DE ESGOTO */
	public static final Integer OPERACAO_MUDANCA_SITUACAO_FATURAMENTO_LIGACAO_ESGOTO = Integer.valueOf(247);

	public static final int OPERACAO_MUDANCA_SITUACAO_FATURAMENTO_LIGACAO_ESGOTO_INT = 247;

	/** EQUIPE INSERIR */
	public static final Integer OPERACAO_EQUIPE_INSERIR = Integer.valueOf(248);

	/** VOLUME MINIMO ATUALIZAR */
	public static final Integer OPERACAO_VOLUME_MINIMO_LIGACAO_ESGOTO_ATUALIZAR = Integer.valueOf(277);

	public static final int OPERACAO_VOLUME_MINIMO_LIGACAO_ESGOTO_ATUALIZAR_INT = 277;

	/** INSTALACAO HIDROMETRO ATUALIZAR * */
	public static final Integer OPERACAO_INSTALACAO_HIDROMETRO_ATUALIZAR = Integer.valueOf(281);

	public static final int OPERACAO_INSTALACAO_HIDROMETRO_ATUALIZAR_INT = 281;

	/** CONSUMO MINIMO ATUALIZAR */
	public static final Integer OPERACAO_CONSUMO_MINIMO_LIGACAO_AGUA_ATUALIZAR = Integer.valueOf(393);

	public static final int OPERACAO_CONSUMO_MINIMO_LIGACAO_AGUA_ATUALIZAR_INT = 393;

	/** LIGACAO ESGOTO ATUALIZAR */
	public static final Integer OPERACAO_LIGACAO_ESGOTO_ATUALIZAR = Integer.valueOf(430);

	/** LIGACAO ESGOTO ATUALIZAR */
	public static final Integer OPERACAO_INSERIR_ORDEM_SERVICO_PROGRAMACAO_ACOMPANHAMENTO_ROTEIRO = Integer.valueOf(252);

	/** REGISTRO ATENDIMENTO ENCERRAR */
	public static final Integer OPERACAO_REGISTRO_ATENDIMENTO_ENCERRAR = Integer.valueOf(463);

	public static final Integer OPERACAO_REGISTRO_ATENDIMENTO_CANCELAR = Integer.valueOf(900296);

	/** IMOVEL DOACAO INSERIR */
	public static final Integer OPERACAO_INSERIR_IMOVEL_DOACAO = Integer.valueOf(282);

	/** IMOVEL DOACAO CANCELAR */
	public static final Integer OPERACAO_CANCELAR_IMOVEL_DOACAO = Integer.valueOf(293);

	/** VALOR COBRANÇA SERVIÇO INSERIR */
	public static final Integer OPERACAO_VALOR_COBRANCA_SERVICO_INSERIR = Integer.valueOf(514);

	/** REGISTRO ATENDIMENTO TRAMITAR */
	public static final Integer OPERACAO_REGISTRO_ATENDIMENTO_TRAMITAR = Integer.valueOf(462);

	/** INSERIR PRIORIDADE DO TIPO DE SERVIÇO */
	public static final Integer OPERACAO_SERVICO_TIPO_PRIORIDADE_INSERIR = Integer.valueOf(568);

	/** INSERIR MATERIAL */
	public static final Integer OPERACAO_MATERIAL_INSERIR = Integer.valueOf(515);

	/** INSERIR TIPO RETORNO DA OS REFERIDA */
	public static final Integer OPERACAO__TIPO_RETORNO_OS_REFERIDA_INSERIR = Integer.valueOf(565);

	/** INSERIR SERVICO TIPO REFERENCIA */
	public static final Integer OPERACAO_SERVICO_TIPO_REFERENCIA_INSERIR = Integer.valueOf(567);

	/** ATUALIZAR VALOR DE COBRANCA DE SERVICO */
	public static final Integer OPERACAO_VALOR_COBRANCA_SERVICO_ATUALIZAR = Integer.valueOf(647);

	/** REMOVER VALOR DE COBRANCA DE SERVICO */
	public static final Integer OPERACAO_VALOR_COBRANCA_SERVICO_REMOVER = Integer.valueOf(648);

	/** ATUALIZAR TIPO PERFIL SERVIÇO */
	public static final Integer OPERACAO_TIPO_PERFIL_SERVICO_ATUALIZAR = Integer.valueOf(651);

	/** REMOVER TIPO PERFIL SERVIÇO */
	public static final Integer OPERACAO_TIPO_PERFIL_SERVICO_REMOVER = Integer.valueOf(662);

	/** REMOVER ESPECIFICAÇÃO DA SITUAÇÃO DO IMÓVEL */
	public static final Integer OPERACAO_ESPECIFICACAO_SITUACAO_IMOVEL_REMOVER = Integer.valueOf(668);

	/** REMOVER ESPECIFICAÇÃO DA SITUAÇÃO DO IMÓVEL */
	public static final Integer OPERACAO_ESPECIFICACAO_SITUACAO_IMOVEL_ATUALIZAR = Integer.valueOf(672);

	/** REMOVER EQUIPE */
	public static final Integer OPERACAO_EQUIPE_REMOVER = Integer.valueOf(674);

	/** ATUALIZAR EQUIPE */
	public static final Integer OPERACAO_EQUIPE_ATUALIZAR = Integer.valueOf(676);

	/** ATUALIZAR */
	public static final Integer OPERACAO_PROGRAMACAO_ABASTECIMENTO_MANUTENCAO_ATUALIZAR = Integer.valueOf(687);

	/** REMOVER MATERIAL */
	public static final Integer OPERACAO_MATERIAL_REMOVER = Integer.valueOf(690);

	/** ALTERAR DADOS FATURAMENTO */
	public static final Integer OPERACAO_ALTERAR_DADOS_FATURAMENTO = Integer.valueOf(612);

	/** CONSULTAR DADOS FATURAMENTO */
	public static final Integer OPERACAO_CONSULTAR_DADOS_FATURAMENTO = Integer.valueOf(436);

	/** ATUALIZAR MATERIAL */
	public static final Integer OPERACAO_MATERIAL_ATUALIZAR = Integer.valueOf(692);

	/** UNIDADE ORGANIZACIONAL REMOVER */
	public static final Integer OPERACAO_UNIDADE_ORGANIZACIONAL_REMOVER = Integer.valueOf(704);

	/** ATUALIZAR UNIDADE ORGANIZACIONAL */
	public static final Integer OPERACAO_UNIDADE_ORGANIZACIONAL_ATUALIZAR = Integer.valueOf(702);

	/** INSERIR UNIDADE ORGANIZACIONAL */
	public static final Integer OPERACAO_UNIDADE_ORGANIZACIONAL_INSERIR = Integer.valueOf(689);

	public static final Integer OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO = Integer.valueOf(706);

	public static final int OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO_INT = 706;

	/** INSERIR HISTORICO ALTERAÇÕES SISTEMA */
	public static final Integer OPERACAO_ALTERACAO_HISTORICO_INSERIR = Integer.valueOf(714);

	/** INSERIR OCORRENCIA/ANORMALIDADE DE IMOVEL */
	public static final Integer OPERACAO_OCORRENCIA_ANORMALIDADE_INSERIR = Integer.valueOf(698);

	/** INSERIR MUNICIPIO */
	public static final Integer OPERACAO_MUNICIPIO_INSERIR = Integer.valueOf(726);

	/** INSERIR UNIDADE OPERACIONAL */
	public static final Integer OPERACAO_UNIDADE_OPERACIONAL_INSERIR = Integer.valueOf(1505);

	/** ATUALIZAR UNIDADE OPERACIONAL */
	public static final Integer OPERACAO_UNIDADE_OPERACIONAL_ATUALIZAR = Integer.valueOf(1507);

	/** REMOVER UNIDADE OPERACIONAL */
	public static final Integer OPERACAO_UNIDADE_OPERACIONAL_REMOVER = Integer.valueOf(13931);

	/** INSERIR SITUACAO DE COBRANCA DO IMOVEL */
	public static final Integer OPERACAO_IMOVEL_COBRANCA_SITUACAO_INSERIR = Integer.valueOf(719);

	/** RETIRAR SITUACAO DE COBRANCA DO IMOVEL */
	public static final Integer OPERACAO_IMOVEL_COBRANCA_SITUACAO_RETIRAR = Integer.valueOf(720);

	/** INSERIR TIPO DE SERVICO */
	public static final Integer OPERACAO_TIPO_SERVICO_INSERIR = Integer.valueOf(564);

	/** INSERIR GERÊNCIA REGIONAL */
	public static final Integer OPERACAO_GERENCIA_REGIONAL_INSERIR = Integer.valueOf(730);

	/** REMOVER MUNICIPIO */
	public static final Integer OPERACAO_MUNICIPIO_REMOVER = Integer.valueOf(735);

	/** ATUALIZAR MUNICIPIO */
	public static final Integer OPERACAO_MUNICIPIO_ATUALIZAR = Integer.valueOf(739);

	/** ATUALIZAR GERÊNCIA REGIONAL */
	public static final Integer OPERACAO_GERENCIA_REGIONAL_ATUALIZAR = Integer.valueOf(745);

	/** SISTEMA PARAMETROS INFORMAR (Padrão Antigo de Parâmetros) */
	public static final Integer OPERACAO_SISTEMA_PARAMETROS_INFORMAR = Integer.valueOf(733);

	/** SISTEMA PARAMETROS CONSULTAR (Padrão NOVO de Parâmetros) */
	public static final Integer OPERACAO_SISTEMA_PARAMETROS_CONSULTAR = Integer.valueOf(1186);

	public static final Integer OPERACAO_PARAMETRO_SISTEMA_ALTERAR = Integer.valueOf(271377);

	public static final Integer OPERACAO_PARAMETRO_SISTEMA_VALOR_REMOVER = Integer.valueOf(323902);

	public static final Integer OPERACAO_PARAMETRO_SISTEMA_VALOR_INSERIR = Integer.valueOf(323903);

	public static final Integer OPERACAO_PARAMETRO_SISTEMA_VALOR_ATUALIZAR = Integer.valueOf(323904);

	/** GERÊNCIA REGIONAL REMOVER */
	public static final Integer OPERACAO_GERENCIA_REGIONAL_REMOVER = Integer.valueOf(748);

	/** INSERIR FERIADO */
	public static final Integer OPERACAO_FERIADO_INSERIR = Integer.valueOf(749);

	/** ATUALIZAR FERIADO */
	public static final Integer OPERACAO_FERIADO_ATUALIZAR = Integer.valueOf(770);

	/** REMOVER FERIADO */
	public static final Integer OPERACAO_FERIADO_REMOVER = Integer.valueOf(768);

	/** INSERIR AGÊNCIA BANCARIA */
	public static final Integer OPERACAO_AGENCIA_BANCARIA_INSERIR = Integer.valueOf(772);

	/** INSERIR DISTRITO OPERACIONAL */
	public static final Integer OPERACAO_DISTRITO_OPERACIONAL_INSERIR = Integer.valueOf(778);

	/** INSERIR ARRECADADOR */
	public static final Integer OPERACAO_ARRECADADOR_INSERIR = Integer.valueOf(773);

	/** REMOVER ARRECADADOR */
	public static final Integer OPERACAO_ARRECADADOR_REMOVER = Integer.valueOf(797);

	/** ATUALIZAR ARRECADADOR */
	public static final Integer OPERACAO_ARRECADADOR_ATUALIZAR = Integer.valueOf(792);

	/** ATUALIZAR DISTRITO OPERACIONAL */
	public static final Integer OPERACAO_DISTRITO_OPERACIONAL_ATUALIZAR = Integer.valueOf(778);

	/** REMOVER SERVICO TIPO PRIORIDADE */
	public static final Integer OPERACAO_SERVICO_TIPO_PRIORIDADE_REMOVER = Integer.valueOf(789);

	/** INSERIR DADOS TARIFA SOCIAL */
	public static final Integer OPERACAO_INSERIR_TARIFA_SOCIAL = Integer.valueOf(595);

	/** MANTER DADOS TARIFA SOCIAL */
	public static final Integer OPERACAO_MANTER_TARIFA_SOCIAL = Integer.valueOf(754);

	/** INSERIR CONTRATO DEMANDA */
	// public static final Integer OPERACAO_CONTRATO_DEMANDA_INSERIR = Integer.valueOf(778);

	/** INSERIR ANORMALIDADE LEITURA */
	public static final Integer OPERACAO_ANORMALIDADE_LEITURA_INSERIR = Integer.valueOf(832);

	/** ALTERAR ANORMALIDADE LEITURA */
	public static final Integer OPERACAO_ANORMALIDADE_LEITURA_ALTERAR = Integer.valueOf(838);

	/** INSERIR TIPO DE CREDITO */
	public static final Integer OPERACAO_TIPO_CREDITO_INSERIR = Integer.valueOf(850);

	/** ALTERAR SITUACAO LIGACAO */
	public static final Integer OPERACAO_SITUACAO_LIGACAO_ALTERAR = Integer.valueOf(857);

	/** ATUALIZAR TIPO DE CREDITO */
	public static final Integer OPERACAO_TIPO_CREDITO_ATUALIZAR = Integer.valueOf(860);

	/** REMOVER TIPO DE CREDITO */
	public static final Integer OPERACAO_TIPO_CREDITO_REMOVER = Integer.valueOf(862);

	/** INFORMAR NAO ENTREGA DE DOCUMENTOS */
	public static final Integer OPERACAO_NAO_ENTREGA_DOCUMENTOS_INFORMAR = Integer.valueOf(864);

	/** REMOVER O TIPO DE RETORNO DA OS_REFERIDA */
	public static final Integer OPERACAO_TIPO_RETORNO_OS_REFERIDA_REMOVER = Integer.valueOf(694);

	/** INSERIR FUNCIONARIO */
	public static final Integer OPERACAO_FUNCIONARIO_INSERIR = Integer.valueOf(869);

	/** ATUALIZAR FUNCIONARIO */
	public static final Integer OPERACAO_FUNCIONARIO_ATUALIZAR = Integer.valueOf(872);

	/** EFETUAR RESTABELECIMENTO DA LIGAÇÃO DE ÁGUA COM INSTALAÇÂO DE HIDRÔMETRO */
	public static final Integer OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_INSTALACAO_DE_HIDROMETRO = Integer.valueOf(879);

	/** REMOVER TIPO DE SERVICO */
	public static final Integer OPERACAO_TIPO_SERVICO_REMOVER = Integer.valueOf(747);

	/** CORTE ADMINISTRATIVO LIGACAO AGUA EFETUAR */
	public static final Integer OPERACAO_CORTE_ADMINISTRATIVO_LIGACAO_AGUA_EFETUAR = Integer.valueOf(358);

	public static final int OPERACAO_CORTE_ADMINISTRATIVO_LIGACAO_AGUA_EFETUAR_INT = 358;

	/** RESTABELECIMENTO LIGACAO AGUA COM INSTALACAO HIDROMETRO EFETUAR */
	public static final Integer RESTABELECIMENTO_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO = Integer.valueOf(879);

	public static final int RESTABELECIMENTO_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO_INT = 879;

	/** INFORMAR LEITURA DE FISCALIZACAO */
	public static final Integer OPERACAO_LEITURA_FISCALIZACAO_INFORMAR = Integer.valueOf(889);

	/** ATUALIZA CONTRATO DE ARRECADADOR */
	public static final Integer OPERACAO_ARRECADADOR_CONTRATO_ATUALIZAR = Integer.valueOf(1044);

	/** Desfazer Cancelamento e/ou Retificacao da Conta */
	public static final Integer OPERACAO_CANCELAMENTO_RETIFICACAO_CONTA_DESFAZER = Integer.valueOf(361);

	/** Retificar Conta */
	public static final Integer OPERACAO_CONTA_RETIFICAR = Integer.valueOf(261);

	/** INSERIR SISTEMA DE ESGOTO */
	public static final Integer OPERACAO_INSERIR_SISTEMA_ESGOTO = Integer.valueOf(824);

	/** ATUALIZAR SISTEMA DE ESGOTO */

	public static final Integer OPERACAO_SISTEMA_ESGOTO_ATUALIZAR = Integer.valueOf(841);

	/** REMOVER SISTEMA DE ESGOTO */

	public static final Integer OPERACAO_SISTEMA_ESGOTO_REMOVER = Integer.valueOf(835);

	/** ATUALIZAR TIPO DE DÉBITO */
	public static final Integer OPERACAO_TIPO_DEBITO_ATUALIZAR = Integer.valueOf(830);

	/** INFORMAR RA DADOS AGENCIA REGULADORA */
	public static final Integer OPERACAO_RA_DADOS_AGENCIA_REGULADORA_INFORMAR = Integer.valueOf(867);

	/** INSERIR CLIENTE TIPO */
	public static final Integer OPERACAO_CLIENTE_TIPO_INSERIR = Integer.valueOf(945);

	/** INSERIR CLIENTE RESPONSAVEL */
	public static final Integer OPERACAO_CLIENTE_RESPONSAVEL_INSERIR = Integer.valueOf(1480);

	/** INSERIR IMOVEL AGUA PARA TODOS */
	public static final Integer OPERACAO_IMOVEL_AGUA_PARA_TODOS_INSERIR = Integer.valueOf(2535);

	/** ATUALIZAR CLIENTE RESPONSAVEL */
	public static final Integer OPERACAO_CLIENTE_RESPONSAVEL_ATUALIZAR = Integer.valueOf(1480);

	/** ATUALIZAR CLIENTE RESPONSAVEL */
	public static final Integer OPERACAO_CLIENTE_RESPONSAVEL_REMOVER = Integer.valueOf(1480);

	/** ATUALIZAR CLIENTE TIPO */
	public static final Integer OPERACAO_CLIENTE_TIPO_ATUALIZAR = Integer.valueOf(949);

	/** INSERIR MARCA DE HIDROMETRO */
	public static final Integer OPERACAO_INSERIR_MARCA_HIDROMETRO = Integer.valueOf(940);

	/** REMOVER MARCA DE HIDROMETRO */
	public static final Integer OPERACAO_REMOVER_MARCA_HIDROMETRO = Integer.valueOf(970);

	/** ATUALIZAR MARCA DE HIDROMETRO */
	public static final Integer OPERACAO_ATUALIZAR_MARCA_HIDROMETRO = Integer.valueOf(971);

	/** REMOVER CLIENTE TIPO */
	public static final Integer OPERACAO_CLIENTE_TIPO_REMOVER = Integer.valueOf(952);

	/** COBRANCA_CRONOGRAMA_REMOVER */
	public static final Integer OPERACAO_COBRANCA_CRONOGRAMA_REMOVER = Integer.valueOf(449);

	/** INSERIR CAPACIDADE DE HIDROMETRO */
	public static final Integer OPERACAO_CAPACIDADE_HIDROMETRO_INSERIR = Integer.valueOf(953);

	/** ATUALIZAR CONTRATO DE DEMANDA */
	public static final Integer OPERACAO_CONTRATO_DEMANDA_ATUALIZAR = Integer.valueOf(963);

	/** REMOVER CONTRATO DE DEMANDA */
	public static final Integer OPERACAO_CONTRATO_DEMANDA_REMOVER = Integer.valueOf(964);

	/** ROTEIRO EMPRESA INSERIR */
	public static final Integer OPERACAO_ROTEIRO_EMPRESA_INSERIR = Integer.valueOf(987);

	/** INSERIR LEITURISTA */
	public static final Integer OPERACAO_LEITURISTA_INSERIR = Integer.valueOf(775);

	/** ALTERAR LEITURISTA */
	public static final Integer OPERACAO_LEITURISTA_ALTERAR = Integer.valueOf(798);

	/** REMOVER LEITURISTA */
	public static final Integer OPERACAO_LEITURISTA_REMOVER = Integer.valueOf(777);

	/** QUALIDADE AGUA INSERIR */
	public static final Integer OPERACAO_QUALIDADE_AGUA_INSERIR = Integer.valueOf(990);

	/** QUALIDADE AGUA ALTERAR */
	public static final Integer OPERACAO_QUALIDADE_AGUA_ALTERAR = Integer.valueOf(1027);

	/** QUALIDADE AGUA REMOVER */
	public static final Integer OPERACAO_QUALIDADE_AGUA_REMOVER = Integer.valueOf(1028);

	public static final Integer OPERACAO_LIGACAO_ESGOTO__SEM_RA_EFETUAR = Integer.valueOf(1002);

	public static final Integer OPERACAO_INSERIR_ATIVIDADE_COBRANCA = Integer.valueOf(1009);

	/** IMSERIR Cobranca ação */
	public static final Integer OPERACAO_COBRANCA_ACAO_INSERIR = Integer.valueOf(1010);

	public static final Integer OPERACAO_ROTEIRO_EMPRESA_ATUALIZAR = Integer.valueOf(1010);

	public static final Integer OPERACAO_ROTEIRO_EMPRESA_REMOVER = Integer.valueOf(1011);

	public static final Integer OPERACAO_INFORMAR_INDICES_ACRESCIMOS_IMPONTUALIDADE = Integer.valueOf(1017);

	public static final Integer OPERACAO_ALTERAR_VENCIMENTO_CONTA = Integer.valueOf(412);

	public static final Integer OPERACAO_CANCELAR_CONTA = Integer.valueOf(230);

	public static final Integer OPERACAR_RETIRAR_CONTA_REVISAO = Integer.valueOf(57);

	public static final Integer OPERACAO_IMOVEL_REMOVER = Integer.valueOf(292);

	public static final Integer OPERACAO_COBRANCA_ACAO_REMOVER = Integer.valueOf(1040);

	public static final Integer OPERACAO_COBRANCA_ACAO_ATUALIZAR = 1043;

	public static final Integer OPERACAO_ORDEM_SERVICO_ATUALIZAR = 260;

	public static final Integer OPERACAO_ORDEM_SERVICO_ENCERRAR = 297;

	public static final Integer OPERACAO_INSERIR_NEGATIVADOR = Integer.valueOf(1403); // Código

	// Embasa
	// 1092

	public static final Integer OPERACAO_ATUALIZAR_NEGATIVADOR = Integer.valueOf(1404); // Código

	public static final Integer OPERACAO_ATULIZAR_NORMA_PROCEDIMENTAL = Integer.valueOf(900237);

	public static final Integer OPERACAO_ATULIZAR_ATENDIMENTO_PARECER = Integer.valueOf(900262);

	// Embasa
	// 1093

	public static final Integer OPERACAO_REMOVER_NEGATIVADOR = Integer.valueOf(1405); // Código

	// Embasa
	// 1094

	public static final Integer OPERACAO_INSERIR_CONTRATO_NEGATIVADOR = Integer.valueOf(1402); // Código

	// Embasa
	// 1091

	public static final Integer OPERACAO_ATUALIZAR_CONTRATO_NEGATIVADOR = Integer.valueOf(1408); // Código

	// Embasa
	// 1095

	public static final Integer OPERACAO_REMOVER_CONTRATO_NEGATIVADOR = Integer.valueOf(1409); // Código

	// Embasa
	// 1096

	public static final Integer OPERACAO_INSERIR_NEGATIVADOR_EXCLUSAO_MOTIVO = Integer.valueOf(1420); // Código

	// Embasa
	// 1107

	public static final Integer OPERACAO_ATUALIZAR_NEGATIVADOR_EXCLUSAO_MOTIVO = Integer.valueOf(1421); // Código

	// Embasa
	// 1108

	public static final Integer OPERACAO_INSERIR_NEGATIVADOR_RETORNO_MOTIVO = Integer.valueOf(1425); // Código

	// Embasa
	// 1113

	public static final Integer OPERACAO_ATUALIZAR_NEGATIVADOR_RETORNO_MOTIVO = Integer.valueOf(1427); // Código

	// Embasa
	// 1114

	public static final Integer OPERACAO_REMOVER_NEGATIVADOR_RETORNO_MOTIVO = Integer.valueOf(1428); // Código

	// Embasa
	// 1115

	public static final Integer OPERACAO_INSERIR_NEGATIVADOR_REGISTRO_TIPO = Integer.valueOf(1431); // Código

	// Embasa
	// 1118

	public static final Integer OPERACAO_ATUALIZAR_NEGATIVADOR_REGISTRO_TIPO = Integer.valueOf(1432); // Código

	// Embasa
	// 1119

	public static final Integer OPERACAO_REMOVER_NEGATIVADOR_REGISTRO_TIPO = Integer.valueOf(1435); // Código

	// Embasa
	// 1122

	public static final Integer GERAR_MOVIMENTO_EXCLUSAO_NEGATIVACAO = Integer.valueOf(1401); // Código

	// Embasa
	// -
	// 1090

	public static final Integer GERAR_RESUMO_DIARIO_NEGATIVACAO = Integer.valueOf(1128);

	public static final Integer EXECUTAR_COMANDO_NEGATIVACAO = Integer.valueOf(1129);

	public static final Integer EXCLUIR_NEGATIVACAO_ON_LINE = Integer.valueOf(133);

	// public static final Integer OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO =
	// Integer.valueOf(1130);

	public static final int OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO_INT = 1130;

	public static final Integer OPERACAO_INFORMAR_UNIDADE_NEGOCIO_TESTEMUNHA = Integer.valueOf(1323);

	public static final Integer OPERACAO_ATUALIZAR_DADOS_REGISTRO = Integer.valueOf(1183);

	public static final Integer OPERACAO_ATUALIZAR_LIGACAO_AGUA = Integer.valueOf(422);

	public static final Integer OPERACAO_CONSUMO_AREA_INFORMAR = Integer.valueOf(1300);

	public static final Integer OPERACAO_EXIBIR_FOTO_FACHADA_IMOVEL = Integer.valueOf(1149);

	public static final Integer OPERACAO_EXIBIR_LOGOMARCA_EMPRESA = Integer.valueOf(1309);

	public static final Integer OPERACAO_INSERIR_EMPRESA = Integer.valueOf(1152);

	public static final Integer OPERACAO_INSERIR_NORMA_PROCEDIMENTAL = Integer.valueOf(900235);

	public static final Integer OPERACAO_INSERIR_ATENDIMENTO_PARECER = Integer.valueOf(900260);

	public static final Integer OPERACAO_REMOVER_NORMA_PROCEDIMENTAL = Integer.valueOf(900238);

	public static final Integer OPERACAO_REMOVER_DOCUMENTO_ELETRONICO = Integer.valueOf(900289);

	public static final Integer OPERACAO_INSERIR_DOCUMENTO_ELETRONICA = Integer.valueOf(900286);

	public static final Integer OPERACAO_REMOVER_PARECER_ATENDIMENTO = Integer.valueOf(900263);

	public static final Integer OPERACAO_ATUALIZAR_PROGRAMA_COBRANCA = Integer.valueOf(1159);

	public static final Integer OPERACAO_INSERIR_PROGRAMA_COBRANCA = Integer.valueOf(1158);

	public static final Integer OPERACAO_EXCLUIR_NEGATIVACAO_ONLINE = Integer.valueOf(1132);

	/** INSERIR DIAMETRO HIDROMETRO */
	public static final Integer OPERACAO_INSERIR_DIAMETRO_HIDROMETRO = Integer.valueOf(323942);

	/** REMOVER DIAMETRO HIDROMETRO */
	public static final Integer OPERACAO_REMOVER_DIAMETRO_HIDROMETRO = Integer.valueOf(323907);

	/** ATUALIZAR DIAMETRO HIDROMETRO */
	public static final Integer OPERACAO_ATUALIZAR_DIAMETRO_HIDROMETRO = Integer.valueOf(323909);

	/** INSERIR TIPO HIDROMETRO */
	public static final Integer OPERACAO_INSERIR_TIPO_HIDROMETRO = Integer.valueOf(323915);

	/** REMOVER TIPO HIDROMETRO */
	public static final Integer OPERACAO_REMOVER_TIPO_HIDROMETRO = Integer.valueOf(323917);

	/** ATUALIZAR TIPO HIDROMETRO */
	public static final Integer OPERACAO_ATUALIZAR_TIPO_HIDROMETRO = Integer.valueOf(323919);

	/** INSERIR CLASSE METROLOGICA HIDROMETRO */
	public static final Integer OPERACAO_INSERIR_CLASSE_METROLOGICA_HIDROMETRO = Integer.valueOf(323920);

	/** REMOVER CLASSE METROLOGICA HIDROMETRO */
	public static final Integer OPERACAO_REMOVER_CLASSE_METROLOGICA_HIDROMETRO = Integer.valueOf(323922);

	/** ATUALIZAR CLASSE METROLOGICA HIDROMETRO */
	public static final Integer OPERACAO_ATUALIZAR_CLASSE_METROLOGICA_HIDROMETRO = Integer.valueOf(323924);

	/** INSERIR MOTIVO BAIXA HIDROMETRO */
	public static final Integer OPERACAO_INSERIR_MOTIVO_BAIXA_HIDROMETRO = Integer.valueOf(323925);

	/** REMOVER MOTIVO BAIXA HIDROMETRO */
	public static final Integer OPERACAO_REMOVER_MOTIVO_BAIXA_HIDROMETRO = Integer.valueOf(323927);

	/** ATUALIZAR MOTIVO BAIXA HIDROMETRO */
	public static final Integer OPERACAO_ATUALIZAR_MOTIVO_BAIXA_HIDROMETRO = Integer.valueOf(323929);

	/** INSERIR LOCAL ARMAZENAGEM HIDROMETRO */
	public static final Integer OPERACAO_INSERIR_LOCAL_ARMAZENAGEM_HIDROMETRO = Integer.valueOf(323930);

	/** REMOVER LOCAL ARMAZENAGEM HIDROMETRO */
	public static final Integer OPERACAO_REMOVER_LOCAL_ARMAZENAGEM_HIDROMETRO = Integer.valueOf(323932);

	/** ATUALIZAR LOCAL ARMAZENAGEM HIDROMETRO */
	public static final Integer OPERACAO_ATUALIZAR_LOCAL_ARMAZENAGEM_HIDROMETRO = Integer.valueOf(323934);

	public static final Integer OPERACAO_ESTABELECER_VINCULO = Integer.valueOf(308);

	public static final Integer OPERACAO_DESFAZER_VINCULO = Integer.valueOf(315);

	public static final Integer OPERACAO_CONTA_MOTIVO_CANCELAMENTO_REMOVER = Integer.valueOf(1380);

	public static final Integer OPERACAO_CONTA_MOTIVO_INCLUSAO_REMOVER = Integer.valueOf(1381);

	public static final Integer OPERACAO_CONTA_MOTIVO_REVISAO_REMOVER = Integer.valueOf(1387);

	/** REMOVER CONTA MOTIVO RETIFICACAOO */
	public static final Integer OPERACAO_CONTA_MOTIVO_RETIFICACAO_REMOVER = Integer.valueOf(1386);

	public static final Integer OPERACAO_INSTALACAO_HIDROMETRO_LOTE_EFETUAR = Integer.valueOf(1351);

	public static final Integer OPERACAO_ESTORNAR_PAGAMENTO = Integer.valueOf(1185);

	/** INSERIR FOTO ORDEM SERVICO OCORRENCIA */
	public static final Integer OPERACAO_EXIBIR_FOTO_ORDEM_SERVICO_OCORRENCIA = Integer.valueOf(1190);

	/** ATUALIZAR ANALISE EXECECOES LEITURAS RESUMO */
	public static final Integer OPERACAO_ATUALIZAR_EXECECOES_LEITURAS_RESUMO = Integer.valueOf(629);

	/** SUBSTITUIR CONSUMOS ANTERIORES */
	public static final Integer OPERACAO_SUBSTITUIR_CONSUMOS_ANTERIORES = Integer.valueOf(355);

	public static final Integer OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO = Integer.valueOf(323747);

	public static final Integer OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_SUBSTITUICAO_HIDROMETRO = Integer.valueOf(323746);

	public static final Integer OPERACAO_EFETUAR_CORTE_AGUA_COM_INSTALACAO_HIDROMETRO = Integer.valueOf(1485);

	public static final Integer OPERACAO_EFETUAR_CORTE_AGUA_COM_SUBSTITUICAO_HIDROMETRO = Integer.valueOf(1486);

	public static final Integer OPERACAO_EFETUAR_CORTE_AGUA_COM_RETIRADA_HIDROMETRO = Integer.valueOf(324048);

	/** EFETUAR RESTABELECIMENTO DA LIGAÇÃO DE ÁGUA COM SUBSTITUIÇÂO DE HIDRÔMETRO */
	public static final Integer OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_SUBSTITUICAO_DE_HIDROMETRO = Integer.valueOf(1487);

	public static final int OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_SUBSTITUICAO_DE_HIDROMETRO_INT = 1487;

	public static final Integer OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_TUBETE_MAGNETICO = 323851;

	public static final int OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_TUBETE_MAGNETICO_INT = 323851;

	public static final Integer OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_REGISTRO_MAGNETICO = 323852;

	public static final int OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_REGISTRO_MAGNETICO_INT = 323852;

	public static final Integer OPERACAO_ORGAO_EXTERNO_REMOVER = 1105;

	public static final Integer OPERACAO_INFRACAO_LIGACAO_SITUACAO_REMOVER = 2554;

	public static final Integer OPERACAO_INFRACAO_IMOVEL_SITUACAO_REMOVER = 2550;

	public static final Integer OPERACAO_INFRACAO_TIPO_REMOVER = 2546;

	public static final Integer OPERACAO_INFRACAO_PERFIL_REMOVER = null;

	/** INSERIR SISTEMA ABASTECIMENTO */
	public static final Integer OPERACAO_SISTEMA_ABASTECIMENTO_INSERIR = Integer.valueOf(976);

	/** ATUALIZAR SISTEMA ABASTECIMENTO */
	public static final Integer OPERACAO_SISTEMA_ABASTECIMENTO_ATUALIZAR = Integer.valueOf(978);

	/** INSERIR SUBSISTEMA DE ESGOTO */
	public static final Integer OPERACAO_INSERIR_SUBSISTEMA_ESGOTO = Integer.valueOf(1483);

	/** ATUALIZAR SUBSISTEMA DE ESGOTO */
	public static final Integer OPERACAO_ATUALIZAR_SUBSISTEMA_ESGOTO = Integer.valueOf(1485);

	/** REMOVER SUBSISTEMA DE ESGOTO */
	public static final Integer OPERACAO_REMOVER_SUBSISTEMA_ESGOTO = Integer.valueOf(1519);

	/** INSERIR LOCALIDADE DADO OPERACIONAL */
	public static final Integer OPERACAO_LOCALIDADE_DADO_OPERACIONAL_INSERIR = Integer.valueOf(1495);

	/** INSERIR BACIA **/
	public static final Integer OPERACAO_BACIA_INSERIR = Integer.valueOf(1487);

	/** ATUALIZAR LOCALIDADE DADO OPERACIONAL */
	public static final Integer OPERACAO_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR = Integer.valueOf(1497);

	/** LOCALIDADE DADO OPERACIONAL REMOVER */
	public static final Integer OPERACAO_LOCALIDADE_DADO_OPERACIONAL_REMOVER = Integer.valueOf(3057);

	/** ATUALIZAR SUBBACIA */
	public static final Integer OPERACAO_ATUALIZAR_SUBBACIA = Integer.valueOf(1493);

	/** ATUALIZAR ZONA DE ABASTECIMENTO */
	public static final Integer OPERACAO_ATUALIZAR_ZONA_ABASTECIMENTO = Integer.valueOf(1511);

	/** INSERIR ZONA DE ABASTECIMENTO */
	public static final Integer OPERACAO_INSERIR_ZONA_ABASTECIMENTO = Integer.valueOf(1509);

	/** REMOVER ZONA ABASTECIMENTO */
	public static final Integer OPERACAO_REMOVER_ZONA_ABASTECIMENTO = Integer.valueOf(16524);

	/** REMOVER SUBBACIA */
	public static final Integer OPERACAO_REMOVER_SUBSBACIA = Integer.valueOf(3572);

	/** INSERIR SUB-BACIA **/
	public static final Integer OPERACAO_SUBBACIA_INSERIR = Integer.valueOf(1491);

	/** ATUALIZAR BACIA */
	public static final Integer OPERACAO_BACIA_ATUALIZAR = Integer.valueOf(1489);

	/** LOCALIDADE BACIA */
	public static final Integer OPERACAO_BACIA_REMOVER = Integer.valueOf(16521);

	/** REMOVER SISTEMA ABASTECIMENTO */
	public static final Integer OPERACAO_SISTEMA_ABASTECIMENTO_REMOVER = Integer.valueOf(979);

	/** INSERIR SETOR ABASTECIMENTO */
	public static final Integer OPERACAO_SETOR_ABASTECIMENTO_INSERIR = Integer.valueOf(1513);

	/** ATUALIZAR SETOR ABASTECIMENTO */
	public static final Integer OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR = Integer.valueOf(1515);

	/** REMOVER SETOR ABASTECIMENTO */
	public static final Integer OPERACAO_SETOR_ABASTECIMENTO_REMOVER = Integer.valueOf(25845);

	public static final Integer OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE = Integer.valueOf(22737);

	public static final Integer OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE = Integer.valueOf(23255);

	/** INSERIR DADOS CENSITARIOS */
	public static final Integer OPERACAO_DADOS_CENSITARIOS_INSERIR = Integer.valueOf(23773);

	/** REMOVER DADOS CENSITARIOS */
	public static final Integer OPERACAO_DADOS_CENSITARIOS_REMOVER = Integer.valueOf(33097);

	/** ATUALIZAR DADOS CENSITARIOS */
	public static final Integer OPERACAO_DADOS_CENSITARIOS_ATUALIZAR = Integer.valueOf(34133);

	/** CLIENTE FONE INSERIR */
	public static final Integer OPERACAO_CLIENTE_FONE_INSERIR = Integer.valueOf(189);

	/** CLIENTE FONE REMOVER */
	public static final Integer OPERACAO_CLIENTE_FONE_REMOVER = Integer.valueOf(345);

	/** CLIENTE FONE ATUALIZAR */
	public static final Integer OPERACAO_CLIENTE_FONE_ATUALIZAR = Integer.valueOf(334);

	public static final Integer OPERACAO_CLIENTE_ENDERECO_INSERIR = Integer.valueOf(188);

	public static final Integer OPERACAO_CLIENTE_ENDERECO_ATUALIZAR = Integer.valueOf(331);

	public static final Integer OPERACAO_INSERIR_CLIENTE_ENDERECO_REMOVER = Integer.valueOf(571);

	public static final Integer OPERACAO_ATUALIZAR_CLIENTE_ENDERECO_REMOVER = Integer.valueOf(346);

	/** PROFISSAO INSERIR */
	public static final Integer OPERACAO_INSERIR_PROFISSAO = Integer.valueOf(323943);

	/** PROFISSAO REMOVER */
	public static final Integer OPERACAO_REMOVER_PROFISSAO = Integer.valueOf(323945);

	/** PROFISSAO ATUALIZAR */
	public static final Integer OPERACAO_ATUALIZAR_PROFISSAO = Integer.valueOf(323946);

	/** IMOVEL PERFIL INSERIR */
	public static final Integer OPERACAO_INSERIR_IMOVEL_PERFIL = Integer.valueOf(323949);

	/** IMOVEL PERFIL REMOVER */
	public static final Integer OPERACAO_REMOVER_IMOVEL_PERFIL = Integer.valueOf(323951);

	/** IMOVEL PERFIL ATUALIZAR */
	public static final Integer OPERACAO_ATUALIZAR_IMOVEL_PERFIL = Integer.valueOf(323952);

	/** PADRAO CONSTRUCAO INSERIR */
	public static final Integer OPERACAO_INSERIR_PADRAO_CONSTRUCAO = Integer.valueOf(323955);

	/** PADRAO CONSTRUCAO REMOVER */
	public static final Integer OPERACAO_REMOVER_PADRAO_CONSTRUCAO = Integer.valueOf(323957);

	/** PADRAO CONSTRUCAO ATUALIZAR */
	public static final Integer OPERACAO_ATUALIZAR_PADRAO_CONSTRUCAO = Integer.valueOf(323958);

	/** SETOR_COMERCIAL_VENCIMENTO_INSERIR */
	public static final Integer OPERACAO_SETOR_COMERCIAL_VENCIMENTO_INSERIR = Integer.valueOf(43457);

	/** SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR */
	public static final Integer OPERACAO_SETOR_COMERCIAL_VENCIMENTO_ATUALIZAR = Integer.valueOf(43975);

	/** SETOR_COMERCIAL_VENCIMENTO_REMOVER */
	public static final Integer OPERACAO_SETOR_COMERCIAL_VENCIMENTO_REMOVER = Integer.valueOf(44493);

	/** ClIENTE IMOVEL */
	public static final Integer OPERACAO_CLIENTE_IMOVEL_ATUALIZAR = Integer.valueOf(53817);

	public static final Integer OPERACAO_CLIENTE_IMOVEL_REMOVER = Integer.valueOf(54335);

	public static final Integer OPERACAO_CLIENTE_IMOVEL_INSERIR = Integer.valueOf(54853);

	/** IMOVEL SUBCATEGORIA */
	public static final Integer OPERACAO_IMOVEL_SUBCATEGORIA_ATUALIZAR = Integer.valueOf(55371);

	public static final Integer OPERACAO_IMOVEL_SUBCATEGORIA_REMOVER = Integer.valueOf(55889);

	public static final Integer OPERACAO_IMOVEL_SUBCATEGORIA_INSERIR = Integer.valueOf(56407);

	/** IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA */
	public static final Integer OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR = Integer.valueOf(178140);

	public static final Integer OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER = Integer.valueOf(178141);

	public static final Integer OPERACAO_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR = Integer.valueOf(178142);

	/** CONSUMO_FAIXA_AREA_CATEGORIA */
	public static final Integer OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR = Integer.valueOf(178144);

	public static final Integer OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER = Integer.valueOf(178148);

	public static final Integer OPERACAO_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR = Integer.valueOf(178143);

	/** SUBSTITUIR LEITURAS ANTERIORES */
	public static final Integer OPERACAO_SUBSTITUIR_LEITURAS_ANTERIORES = Integer.valueOf(219577);

	public static final Integer OPERACAO_EXCLUIR_DEBITO_AUTOMATICO = Integer.valueOf(282255);

	/** Boleto Bancáio */
	public static final Integer OPERACAO_BOLETO_BANCARIO_ATUALIZAR = 302462;

	public static final Integer OPERACAO_BOLETO_BANCARIO_CANCELAR = 302466;

	public static final Integer OPERACAO_INFORMAR_ENTREGA_DOCUMENTO_COBRANCA = Integer.valueOf(302704);

	/** TRAMITAR ORDEM SERVIÇO */
	public static final Integer OPERACAO_TRAMITAR_ORDEM_SERVICO = Integer.valueOf(302791);

	/** INFORMAR ENTREGA DEVOLUCAO DOCUMENTO COBRANCA */
	public static final Integer OPERACAO_INFORMAR_ENTREGA_DEVOLUCAO_DOCUMENTO_COBRANCA = Integer.valueOf(313871);

	/** Gerar Débito Automático para Guia de Pagamento */
	public static final Integer OPERACAO_GERAR_DEBITO_AUTOMATICO_GUIA_PAGAMENTO = Integer.valueOf(313877);

	/** INFORMAR ENTREGA DEVOLUCAO DOCUMENTO COBRANCA */
	public static final Integer OPERACAO_GERAR_GUIA_PAGAMENTO_PROGRAMA_AGUA_PARA_TODOS = Integer.valueOf(323704);

	public static final Integer OPERACAO_ENCERRAR_FATURAMENTO = 323717;

	/** Causionar Conta */
	public static final Integer OPERACAO_CONTA_CAUCIONAR = Integer.valueOf(1363);

	/** INSERIR REGISTRO ATENDIMENTO */
	public static final Integer OPERACAO_INSERIR_REGISTRO_ATENDIMENTO = 267;

	/** ATUALIZAR REGISTRO ATENDIMENTO */
	public static final Integer OPERACAO_ATUALIZAR_REGISTRO_ATENDIMENTO = 543;

	/** INSERIR CEP */
	public static final Integer OPERACAO_CEP_INSERIR = 229937;

	/** ATUALIZAR CEP */
	public static final Integer OPERACAO_CEP_ATUALIZAR = 323913;

	/** REMOVER CEP */
	public static final Integer OPERACAO_CEP_REMOVER = 323912;

	/** REMOVER PROCESSO COBRANCA COMANDO */
	public static final Integer OPERACAO_REMOVER_PROCESSO_COBRANCA_COMANDO = Integer.valueOf(323961);

	/** ATUALIZAR PERFIL LIGAÇÃO ESGOTO */
	public static final Integer OPERACAO_ATUALIZAR_PERFIL_LIGACAO_ESGOTO = Integer.valueOf(900120);

	public static final Integer OPERACAO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL = Integer.valueOf(900125);

	public static final Integer OPERACAO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL = Integer.valueOf(900126);

	/** EFETUAR INSCRICAO CAMPANHA PREMIACAO */
	public static final Integer OPERACAO_EFETUAR_INSCRICAO_CAMPANHA_PREMIACAO = Integer.valueOf(323967);

	/** Retificar Conta */
	public static final Integer ALTERAR_CLIENTE_RESPONSAVEL_CONJUNTO_CONTAS = Integer.valueOf(900155);
	
	public static final Integer OPERACAO_REMOVER_CONTRATO_DEMANDA_CONSUMO = Integer.valueOf(324019);

	/** MANTER CRONOGRAMA DE FATURAMENTO */
	public static final Integer OPERACAO_REMOVER_CRONOGRAMA_FATURAMENTO = Integer.valueOf(426);

	public static final Integer OPERACAO_VALIDAR_SITUACAO_ESPECIAL_FATURAMENTO = Integer.valueOf(435);

	public static final Integer DESMARCAR_PRESCRICAO_DEBITOS = Integer.valueOf(900204);

	/** Inserir Comando de Atividade de Faturamento */
	public static final Integer OPERACAO_INSERIR_COMANDO_ATIVIDADE_FATURAMENTO = Integer.valueOf(437);

	/** Remover Comando de Atividade de Faturamento */
	public static final Integer OPERACAO_REMOVER_COMANDO_ATIVIDADE_FATURAMENTO = Integer.valueOf(438);

	/** Atualizar Comando de Atividade de Faturamento */
	public static final Integer OPERACAO_ATUALIZAR_COMANDO_ATIVIDADE_FATURAMENTO = Integer.valueOf(442);

	public static final Integer OPERACAO_ATUALIZAR_COMANDO_ATIVIDADE_DATA_VENCIMENTO_FATURAMENTO = Integer.valueOf(583);

	public static final Integer OPERACAO_ATIVIDADE_ECONOMICA_INSERIR = 900208;

	public static final Integer OPERACAO_ATIVIDADE_ECONOMICA_REMOVER = 900214;

	public static final Integer OPERACAO_ATIVIDADE_ECONOMICA_ATUALIZAR = 900213;

	public static final int OPERACAO_INSERIR_CONSUMO_TARIFA = 367;

	public static final int OPERACAO_ATUALIZAR_CONSUMO_TARIFA = 382;


	public static final Integer OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_NEGATIVA = 900301;

	public static final Integer OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_POSITIVA = 900302;

	public static final Integer OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_NEGATIVA_COM_EFEITO_POSITIVA = 900303;

	public static final Integer OPERACAO_EMITIR_DOCUMENTO_SEGUNDA_VIA_CONTA = 900304;

	public static final Integer OPERACAO_EMITIR_DOCUMENTO_EXTRATO_DEBITO = 900305;

	public static final Integer OPERACAO_EMITIR_DOCUMENTO_GUIA_PAGAMENTO = 900306;

	public static final Integer OPERACAO_EMITIR_FATURA_CLIENTE_RESPONSAVEL = 900307;

	/** nullable persistent field */
	private String descricaoAbreviada;

	/** nullable persistent field */
	private String caminhoUrl;

	/** persistent field */
	private gcom.seguranca.acesso.Funcionalidade funcionalidade;

	private int indicadorAuditoria;

	/** persistent field */
	private gcom.seguranca.acesso.OperacaoTipo operacaoTipo;

	/** persistent field */
	private gcom.seguranca.acesso.Operacao operacaoPesquisa;

	/** persistent field */
	private gcom.seguranca.transacao.TabelaColuna tabelaColuna;

	private gcom.seguranca.transacao.TabelaColuna argumentoPesquisa;

	private String caminhoAjuda;

	/** full constructor */
	public Operacao(String descricao, String descricaoAbreviada, String caminhoUrl, Date ultimaAlteracao,
					gcom.seguranca.acesso.Funcionalidade funcionalidade, gcom.seguranca.acesso.OperacaoTipo operacaoTipo,
					gcom.seguranca.acesso.Operacao operacaoPesquisa, gcom.seguranca.transacao.TabelaColuna tabelaColuna,
					gcom.seguranca.transacao.TabelaColuna argPesquisa) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.caminhoUrl = caminhoUrl;
		this.ultimaAlteracao = ultimaAlteracao;
		this.funcionalidade = funcionalidade;
		this.operacaoTipo = operacaoTipo;
		this.operacaoPesquisa = operacaoPesquisa;
		this.tabelaColuna = tabelaColuna;
		this.argumentoPesquisa = argPesquisa;
	}

	public Operacao(String descricao, String descricaoAbreviada, String caminhoUrl, Date ultimaAlteracao,
					gcom.seguranca.acesso.Funcionalidade funcionalidade) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.caminhoUrl = caminhoUrl;
		this.ultimaAlteracao = ultimaAlteracao;
		this.funcionalidade = funcionalidade;
	}

	/** default constructor */
	public Operacao() {

	}

	/** default constructor */
	public Operacao(Integer idOperacao) {

		this.id = idOperacao;
	}

	/** minimal constructor */
	public Operacao(gcom.seguranca.acesso.Funcionalidade funcionalidade) {

		this.funcionalidade = funcionalidade;
	}

	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getCaminhoUrl(){

		return this.caminhoUrl;
	}

	public void setCaminhoUrl(String caminhoUrl){

		this.caminhoUrl = caminhoUrl;
	}

	public gcom.seguranca.acesso.Funcionalidade getFuncionalidade(){

		return this.funcionalidade;
	}

	public void setFuncionalidade(gcom.seguranca.acesso.Funcionalidade funcionalidade){

		this.funcionalidade = funcionalidade;
	}

	public gcom.seguranca.acesso.Operacao getOperacaoPesquisa(){

		return operacaoPesquisa;
	}

	public void setOperacaoPesquisa(gcom.seguranca.acesso.Operacao operacaoPesquisa){

		this.operacaoPesquisa = operacaoPesquisa;
	}

	/**
	 * @return Retorna o campo operacaoTipo.
	 */
	public gcom.seguranca.acesso.OperacaoTipo getOperacaoTipo(){

		return operacaoTipo;
	}

	/**
	 * @param operacaoTipo
	 *            O operacaoTipo a ser setado.
	 */
	public void setOperacaoTipo(gcom.seguranca.acesso.OperacaoTipo operacaoTipo){

		this.operacaoTipo = operacaoTipo;
	}

	/**
	 * @return Retorna o campo tabelaColuna.
	 */
	public gcom.seguranca.transacao.TabelaColuna getTabelaColuna(){

		return tabelaColuna;
	}

	/**
	 * @param tabelaColuna
	 *            O tabelaColuna a ser setado.
	 */
	public void setTabelaColuna(gcom.seguranca.transacao.TabelaColuna tabelaColuna){

		this.tabelaColuna = tabelaColuna;
	}

	/**
	 * @return Returns the argumentoPesquisa.
	 */
	public gcom.seguranca.transacao.TabelaColuna getArgumentoPesquisa(){

		return argumentoPesquisa;
	}

	/**
	 * @param argumentoPesquisa
	 *            The argumentoPesquisa to set.
	 */
	public void setArgumentoPesquisa(gcom.seguranca.transacao.TabelaColuna argumentoPesquisa){

		this.argumentoPesquisa = argumentoPesquisa;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroOperacao filtroOperacao = new FiltroOperacao();

		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("operacaoTipo");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("idOperacaoPesquisa");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("tabelaColuna");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacao.ARGUMENTO_PESQUISA);
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacao.ARGUMENTO_PESQUISA_TABELA);

		filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.ID, this.getId()));
		return filtroOperacao;
	}

	public void setCaminhoAjuda(String caminhoAjuda){

		this.caminhoAjuda = caminhoAjuda;
	}

	public String getCaminhoAjuda(){

		return caminhoAjuda;
	}

	public int getIndicadorAuditoria(){

		return indicadorAuditoria;
	}

	public void setIndicadorAuditoria(int indicadorAuditoria){

		this.indicadorAuditoria = indicadorAuditoria;
	}
}
