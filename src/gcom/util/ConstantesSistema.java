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
 * Constantes do sistema
 * 
 * @author rodrigo
 */
public interface ConstantesSistema {

	String COLECAO_UNIDADES_PROCESSAMENTO_BATCH = "ColecaoBatch";

	Short SET_ZERO = Short.valueOf("0");

	Short ZERO = Short.valueOf("0");

	Short UM = Short.valueOf("1");

	Integer INVALIDO_ID = -1;

	int NUMERO_NAO_INFORMADO = -1;

	String NUMERO_NAO_INFORMADO_STRING = "" + NUMERO_NAO_INFORMADO;

	int NUMERO_MAXIMO_REGISTROS_MANUTENCAO = 200;

	int NUMERO_MAXIMO_CONSUMO_MINIMO_FIXADO = 100;

	int NUMERO_MAXIMO_REGISTROS_PESQUISA = 50;

	int NUMERO_MAXIMO_REGISTROS_BOLETO_BANCARIO = 1000;

	// --INDICADOR_USO
	Short INDICADOR_USO_ATIVO = Short.valueOf("1");

	Short INDICADOR_USO_DESATIVO = Short.valueOf("2");

	// --TIPO PESQUISA
	Short TIPO_PESQUISA_INICIAL = Short.valueOf("1");

	Short TIPO_PESQUISA_COMPLETA = Short.valueOf("2");

	// --INDICADOR IMÓVEL EXCLUSÃO
	int INDICADOR_IMOVEL_EXCLUIDO = 1;

	int INDICADOR_IMOVEL_ATIVO = 2;

	// --INDICADOR_PESSOA
	Short INDICADOR_PESSOA_FISICA = Short.valueOf("1");

	Short INDICADOR_PESSOA_JURIDICA = Short.valueOf("2");

	// --MENSAGENS
	String CODIGO_INEXISTENTE = "Código Inexistente";

	// --MENSAGENS
	String CODIGO_LOCAL_ARMAZENAGEM_INEXISTENTE = "Local de Armazenagem Inexistente";

	String CODIGO_LOCALIDADE_INEXISTENTE = "Localidade Inexistente";

	String CODIGO_IMOVEL_INEXISTENTE = "Matrícula Inexistente";

	String CODIGO_CLIENTE_INEXISTENTE = "Cliente Inexistente";

	String CODIGO_GUIA_DEVOLUCAO_INEXISTENTE = "Guia de Devolução Inexistente";

	String CODIGO_TIPO_DEBITO_INEXISTENTE = "Tipo de Débito Inexistente";

	String SITUACAO_TAMPONADO = "TAMPONADO";

	String SITUACAO_LIGADO_FORA_DE_USO = "LIGADO FORA DE USO";

	String SITUACAO_LIGADO_RESTABELECIMENTO = "LIGADO RESTABELECIMENTO";

	String SITUACAO_LIGADO_REATIVACAO = "LIGADO REATIVAÇÃO";

	// --CLIENTE_IMOVEL_TIPO
	Integer CLIENTE_IMOVEL_TIPO_RESPONSAVEL = Integer.valueOf(3);

	Integer CLIENTE_IMOVEL_TIPO_USUARIO = Integer.valueOf(2);

	Integer CLIENTE_IMOVEL_TIPO_PROPIETARIO = Integer.valueOf(1);

	// --INDICADOR
	Short INDICADOR_ENDERECO_CORRESPONDENCIA = Short.valueOf("1");

	Short INDICADOR_NAO_ENDERECO_CORRESPONDENCIA = Short.valueOf("2");

	Short INDICADOR_TELEFONE_PRINCIPAL = Short.valueOf("1");

	Short INDICADOR_NAO_TELEFONE_PRINCIPAL = Short.valueOf("2");

	// --INDICADOR DE USO PARA IMOVEL-ENVIO-CONTA
	String IMOVEL_ENVIO_CONTA = "RESPONSAVEL";

	// --INDICADOR QUADRA REDE AGUA
	Short INDICADOR_QUADRA_REDE_AGUA_INEXISTENTE = Short.valueOf("1");

	String DESCRICAO_QUADRA_REDE_AGUA_INEXISTENTE = "1";

	Short INDICADOR_QUADRA_REDE_AGUA_EXISTENTE = Short.valueOf("2");

	String DESCRICAO_QUADRA_REDE_AGUA_EXISTENTE = "2";

	Short INDICADOR_QUADRA_REDE_AGUA_PARCIAL_EXISTENTE = Short.valueOf("3");

	String DESCRICAO_QUADRA_REDE_AGUA_PARCIAL_EXISTENTE = "1";

	String DESCRICAO_QUADRA_REDE_AGUA_PARCIAL_EXISTENTE_2 = "2";

	// --INDICADOR QUADRA REDE AGUA
	Short INDICADOR_QUADRA_REDE_ESGOTO_INEXISTENTE = Short.valueOf("1");

	String DESCRICAO_QUADRA_REDE_ESGOTO_INEXISTENTE = "1";

	Short INDICADOR_QUADRA_REDE_ESGOTO_EXISTENTE = Short.valueOf("2");

	String DESCRICAO_QUADRA_REDE_ESGOTO_EXISTENTE = "2";

	Short INDICADOR_QUADRA_REDE_ESGOTO_PARCIAL_EXISTENTE = Short.valueOf("3");

	String DESCRICAO_QUADRA_REDE_ESGOTO_PARCIAL_EXISTENTE = "1";

	String DESCRICAO_QUADRA_REDE_ESGOTO_PARCIAL_EXISTENTE_2 = "2";

	// ---

	// SIM E NÃO

	Short SIM = Short.valueOf("1");

	Short NAO = Short.valueOf("2");

	Short TODOS = Short.valueOf("3");

	// -- TIPO DE ANORMALIDADE
	String ANORMALIDADE_LEITURA_INFORMADA = "1";

	String ANORMALIDADE_LEITURA_FATURADA = "2";

	String ANORMALIDADE_CONSUMO = "3";

	// --

	Integer INDICADOR_TARIFA_SOCIAL = Integer.valueOf("4");

	Integer INDICADOR_CATEGORIA_RESIDENCIA = Integer.valueOf("1");

	Integer NOVEMBRO = Integer.valueOf("11");

	// Essa senha especial será definida na parte de segurança do sistema. (É
	// provisório!)
	boolean SENHA_ESPECIAL = true;

	boolean NAO_SENHA_ESPECIAL = false;

	// Calcular valores de água e/ou esgoto

	String CALCULAR_AGUA = "AGUA";

	String CALCULAR_ESGOTO = "ESGOTO";

	// Confirmada e Não Confirmada

	String CONFIRMADA = "1";

	String NAO_CONFIRMADA = "2";

	Short INDICADOR_CREDITO = Short.valueOf("1");

	Short INDICADOR_DEBITO = Short.valueOf("2");

	String DEBITO_AUTOMATICO = "DEBITO AUTOMATICO";

	String CODIGO_BARRAS = "CODIGO BARRAS";

	String CODIGO_DE_BARRAS = "CODIGO DE BARRAS";

	String FICHA_DE_COMPENSACAO = "FICHA DE COMPENSACAO";

	String COBRANCA_BANCARIA = "COBRANCA BANCARIA";

	String CATEGORIA_MISTA = "MISTA";

	int COM_ITENS = 2;

	int SEM_ITENS = 1;

	int MOVIMENTO_ABERTO = 1;

	int MOVIMENTO_FECHADO = 2;

	String ABERTO = "ABERTO";

	String FECHADO = "FECHADO";

	String ENVIO = "Envio";

	String RETORNO = "Retorno";

	String OK = "OK";

	Short REGISTROS_NAO_ACEITOS = Short.valueOf("2");

	int CODIGO_ENVIO = 1;

	int CODIGO_RETORNO = 2;

	// Cliente Relacao Tipo
	String CLIENTE_RELACAO_TIPO_USUARIO_EXTENSO = "USUARIO";

	String CLIENTE_RELACAO_TIPO_PROPRIETARIO_EXTENSO = "PROPRIETARIO";

	Integer ANO_LIMITE = 1985;

	// Data Limite Criterio Cobranca
	String DATA_LIMITE = "31/12/2010";

	// Tipo de Pagamento
	Integer CODIGO_TIPO_PAGAMENTO_CONTA = 3;

	// Tipo de Pagamento
	Integer CODIGO_TIPO_PAGAMENTO_CONTA_LEGADO_CAERN = 0;

	// TIPO_PAGAMENTO - CONTA CAER
	String CODIGO_TIPO_PAGAMENTO_CONTA_CAER = "01";

	// TIPO_PAGAMENTO - CONTA ADA
	String CODIGO_TIPO_PAGAMENTO_CONTA_ADA = "001";

	// TIPO_PAGAMENTO - FATURA CAER
	String CODIGO_TIPO_PAGAMENTO_FATURA_CAER = "02";

	String TIPO_PAGAMENTO_FATURA_CAER = "FATURA";

	// TIPO_PAGAMENTO - REAVISO CAER
	String CODIGO_TIPO_PAGAMENTO_REAVISO_CAER = "04";

	String TIPO_PAGAMENTO_REAVISO_CAER = "REAVISO DE DEBITOS";

	// TIPO_PAGAMENTO - GUIA PAGAMENTO CLIENTE CAER
	String CODIGO_TIPO_PAGAMENTO_GUIA_PAGAMENTO_CLIENTE_CAER = "06";

	String TIPO_PAGAMENTO_GUIA_PAGAMENTO_CLIENTE_CAER = "GUIA PAGAMENTO CLIENTE";

	// TIPO_PAGAMENTO - GUIA PAGAMENTO IMOVEL CAER
	String CODIGO_TIPO_PAGAMENTO_GUIA_PAGAMENTO_IMOVEL_CAER = "09";

	String TIPO_PAGAMENTO_GUIA_PAGAMENTO_IMOVEL_CAER = "GUIA PAGAMENTO IMOVEL";

	// TIPO_PAGAMENTO - DOCUMENTO COBRANÇA CLIENTE RESPONSAVEL CAER
	String CODIGO_TIPO_PAGAMENTO_DOC_COBRANCA_CLIENTE_RESPONSAVEL_CAER = "08";

	String TIPO_PAGAMENTO_DOC_COBRANCA_CLIENTE_RESPONSAVEL_CAER = "DOCUMENTO COBRANCA CLIENTE";

	// TIPO_PAGAMENTO - FATURA CLIENTE RESPONSAVEL CAER
	String CODIGO_TIPO_PAGAMENTO_FATURA_CLIENTE_RESPONSAVEL_CAER = "07";

	String TIPO_PAGAMENTO_FATURA_CLIENTE_RESPONSAVEL_CAER = "FATURA CLIENTE RESP";

	// TIPO_PAGAMENTO - DOCUMENTO COBRANCA IMOVEL CAER
	String CODIGO_TIPO_PAGAMENTO_DOC_COBRANCA_IMOVEL_CAER = "10";

	String TIPO_PAGAMENTO_DOC_COBRANCA_IMOVEL_CAER = "DOCUMENTO COBRANCA IMOVEL";

	String TIPO_PAGAMENTO_DEBITO_A_COBRAR = "DEBITO A COBRAR";

	String TIPO_PAGAMENTO_CONTA = "CONTA";

	Integer CODIGO_TIPO_PAGAMENTO_GUIA_PAGAMENTO = 4;

	Integer CODIGO_TIPO_PAGAMENTO_GUIA_PAGAMENTO_IMOVEL = 4;

	Integer CODIGO_TIPO_PAGAMENTO_GUIA_PAGAMENTO_CLIENTE = 6;

	Integer CODIGO_TIPO_PAGAMENTO_NOTA_RECEBIMENTO = 1;

	Integer CODIGO_TIPO_PAGAMENTO_PRE_PARCELAMENTO = 2;

	String TIPO_PAGAMENTO_GUIA_PAGAMENTO = "GUIA DE PAGAMENTO";

	String CODIGO_TIPO_PAGAMENTO_GUIA_PAGAMENTO_PARCELA_ADA = "002";

	String CODIGO_TIPO_PAGAMENTO_GUIA_PAGAMENTO_TAXAS_DIVERSAS_ADA = "003";

	String CODIGO_TIPO_PAGAMENTO_DOCUMENTO_COBRANCA_ADA = "005";

	Integer CODIGO_TIPO_PAGAMENTO_DOCUMENTO_COBRANCA = 5;

	Integer CODIGO_TIPO_PAGAMENTO_DOCUMENTO_COBRANCA_CLIENTE = 8;

	String TIPO_PAGAMENTO_DOCUMENTO_COBRANCA = "DOCUMENTO DE COBRANÇA";

	Integer CODIGO_TIPO_PAGAMENTO_FATURA_CLIENTE_RESPONSAVEL = 7;

	String TIPO_PAGAMENTO_FATURA_CLIENTE_RESPONSAVEL = "FATURA DO CLIENTE RESPONSÁVEL";

	String TIPO_PAGAMENTO_BOLETO_BANCARIO = "BOLETO BANCÁRIO";

	Integer CODIGO_MOVIMENTO_EXCLUSAO = 1;

	String DESCRICAO_MOVIMENTO_EXCLUSAO = "EXCLUSÃO";

	Integer CODIGO_MOVIMENTO_INCLUSAO = 2;

	String DESCRICAO_MOVIMENTO_INCLUSAO = "INCLUSÃO";

	Integer CODIGO_MOVIMENTO_DEBITO_NORMAL = 0;

	String DESCRICAO_MOVIMENTO_DEBITO_NORMAL = "DÉBITO NORMAL";

	Integer CODIGO_MOVIMENTO_CANCELAMENTO = 1;

	String DESCRICAO_MOVIMENTO_CANCELAMENTO = "CANCELAMENTO";

	// Situacao agência
	String CODIGO_SITUACAO_AGENCIA_ATIVO = "A";

	String DESCRICAO_SITUACAO_AGENCIA_ATIVO = "ATIVO";

	String CODIGO_SITUACAO_AGENCIA_EM_REGIME_ENCERRAMENTO = "B";

	String DESCRICAO_SITUACAO_AGENCIA_EM_REGIME_ENCERRAMENTO = "EM REGIME DE ENCERRAMENTO";

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Variáveis para definir tamanho das tabelas
	 * 
	 * @author Roberta Costa
	 * @date 22/03/2006
	 */
	Integer NUMERO_MAXIMO_REGISTROS_CONTAS_DEBITO = 6;

	Integer NUMERO_MAXIMO_REGISTROS_GUIA_PAGAMENTO = 4;

	Integer NUMERO_MAXIMO_REGISTROS_OPCAO_PARCELAMENTO = 4;

	Integer NUMERO_MAXIMO_REGISTROS_CONTAS = 4;

	/**
	 * Variáveis para definir tamanho máximo das tabelas que precisam do scroll
	 * 
	 * @author Fernanda Paiva
	 * @date 31/03/2006
	 */
	Integer NUMERO_MAXIMO_REGISTROS_PARA_SCROLL = 6;

	/**
	 * Informa os dados para geração de relatório ou consulta
	 * [UC0304] Informar Dados para Geração de Relatório ou Consulta
	 * Variáveis para definir opções de totalização
	 * 
	 * @author Raphael Rossiter
	 * @date 17/05/2006
	 */

	int CODIGO_ESTADO = 1;

	String ESTADO = "ESTADO";

	int CODIGO_ESTADO_GRUPO_FATURAMENTO = 2;

	String ESTADO_GRUPO_FATURAMENTO = "ESTADO POR GRUPO DE FATURAMENTO";

	int CODIGO_ESTADO_GERENCIA_REGIONAL = 3;

	String ESTADO_GERENCIA_REGIONAL = "ESTADO POR GERÊNCIA REGIONAL";

	int CODIGO_ESTADO_ELO_POLO = 4;

	String ESTADO_ELO_POLO = "ESTADO POR ELO PÓLO";

	int CODIGO_ESTADO_LOCALIDADE = 5;

	String ESTADO_LOCALIDADE = "ESTADO POR LOCALIDADE";

	int CODIGO_GRUPO_FATURAMENTO = 6;

	String GRUPO_FATURAMENTO = "GRUPO DE FATURAMENTO";

	int CODIGO_GERENCIA_REGIONAL = 7;

	String GERENCIA_REGIONAL = "GERÊNCIA REGIONAL";

	int CODIGO_GERENCIA_REGIONAL_ELO_POLO = 8;

	String GERENCIA_REGIONAL_ELO_POLO = "GERÊNCIA REGIONAL POR ELO PÓLO";

	int CODIGO_GERENCIA_REGIONAL_LOCALIDADE = 9;

	String GERENCIA_REGIONAL_LOCALIDADE = "GERÊNCIA REGIONAL POR LOCALIDADE";

	int CODIGO_ELO_POLO = 10;

	String ELO_POLO = "ELO PÓLO";

	int CODIGO_ELO_POLO_LOCALIDADE = 11;

	String ELO_POLO_LOCALIDADE = "ELO PÓLO POR LOCALIDADE";

	int CODIGO_ELO_POLO_SETOR_COMERCIAL = 12;

	String ELO_POLO_SETOR_COMERCIAL = "ELO PÓLO POR SETOR COMERCIAL";

	int CODIGO_LOCALIDADE = 13;

	String LOCALIDADE = "LOCALIDADE";

	int CODIGO_LOCALIDADE_SETOR_COMERCIAL = 14;

	String LOCALIDADE_SETOR_COMERCIAL = "LOCALIDADE POR SETOR COMERCIAL";

	int CODIGO_LOCALIDADE_QUADRA = 15;

	String LOCALIDADE_QUADRA = "LOCALIDADE POR QUADRA";

	int CODIGO_SETOR_COMERCIAL = 16;

	String SETOR_COMERCIAL = "SETOR_COMERCIAL";

	int CODIGO_SETOR_COMERCIAL_QUADRA = 17;

	String SETOR_COMERCIAL_QUADRA = "SETOR_COMERCIAL POR QUADRA";

	int CODIGO_QUADRA = 18;

	String QUADRA = "QUADRA";

	int CODIGO_ESTADO_GRUPO_COBRANCA = 19;

	String ESTADO_GRUPO_COBRANCA = "ESTADO POR GRUPO DE COBRANÇA";

	int CODIGO_GRUPO_COBRANCA = 20;

	String GRUPO_COBRANCA = "GRUPO DE COBRANÇA";

	int CODIGO_UNIDADE_NEGOCIO = 21;

	String UNIDADE_NEGOCIO = "UNIDADE NEGÓCIO";

	int CODIGO_UNIDADE_NEGOCIO_ELO_POLO = 22;

	String UNIDADE_NEGOCIO_ELO_POLO = "UNIDADE NEGÓCIO POR ELO POLO";

	int CODIGO_UNIDADE_NEGOCIO_LOCALIDADE = 23;

	String UNIDADE_NEGOCIO_LOCALIDADE = "UNIDADE NEGÓCIO POR LOCALIDADE";

	int CODIGO_ESTADO_UNIDADE_NEGOCIO = 24;

	String ESTADO_UNIDADE_NEGOCIO = "ESTADO POR UNIDADE NEGÓCIO";

	// --MENSAGENS USUÀRIO
	String USUARIO_INEXISTENTE = "Usuário Inexistente";

	/**
	 * Permite a inclusão de um novo perfil de parcelamento
	 * [UC0220] Inserir Perfil de Parcelamento
	 * Variáveis para definir tamanho das tabelas
	 * 
	 * @author Vivianne Sousa
	 * @date 11/08/2006
	 */
	Integer NUMERO_MAXIMO_REGISTROS_PERFIL_PARCELAMENTO = 4;

	/**
	 * Comandos para a diferença dos relatorios de [UC0164] Filtrar Imovel
	 * Outros Criterios
	 */

	public static Integer GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL = Integer.valueOf(1);

	public static Integer GERAR_RELATORIO_DADOS_TARIFA_SOCIAL = Integer.valueOf(2);

	public static Integer GERAR_RELATORIO_IMOVEL = Integer.valueOf(3);

	public static Integer GERAR_RELATORIO_IMOVEIS_EXCLUIDOS_TARIFA_SOCIAL = Integer.valueOf(4);

	public static Integer GERAR_RELATORIO_DEBITOS = Integer.valueOf(5);

	short ATUALIZACAO_AUTOMATICA = 1;

	short ATUALIZACAO_NENHUMA = 2;

	short ATUALIZACAO_POSTERIOR = 3;

	// Constante para o caso de uso [UC0436] Inserir Tipo de Serviço de
	// Referência
	// Romulo Aurélio - 10/08/2006
	int SITUACAO_REFERENCIA_PENDENTE = 1;

	int SITUACAO_REFERENCIA_ENCERRADA = 2;

	int SITUACAO_REFERENCIA_PENDENTE_AGUARDANDO_RETORNO_OS_REFERENCIA = 4;

	short INDICADOR_NOVO_SOLICITANTE = 2;

	short INDICADOR_INSERIR_SOLICITANTE_RA = 1;

	// Constante para o caso de uso [UC0440] Consultar Programação de
	// Abastecimento e Manutenção
	// Romulo Aurélio - 07/09/2006

	Integer SITUACAO_ABASTECIMENTO_MANUTENCAO = 1;

	Integer SITUACAO_MANUTENCAO = 2;

	Integer SITUACAO_ABASTECIMENTO = 3;

	Integer SITUACAO_ABERTO = 4;

	Integer EM_ABERTO = 1;

	Integer CONCLUIDA = 2;

	// Constante para o caso de uso [UC0455] Exibir Calendário para Elaboração
	// ou Acompanhamento de Roteiro
	// Romulo Aurélio - 22/09/2006

	Integer SITUACAO_ROTEIRO_ELABORACAO_FECHAMENTO_PREENCHIDO = 1;

	Integer SITUACAO_ROTEIRO_ELABORACAO_FECHAMENTO_NULO = 2;

	Integer SITUACAO_ROTEIRO_ELABORACAO_ABERTO = 3;

	Integer SITUACAO_ROTEIRO_ACOMPANHAMENTO_FECHAMENTO_PREENCHIDO = 4;

	Integer SITUACAO_ROTEIRO_ACOMPANHAMENTO_FECHAMENTO_NULO = 5;

	Integer SITUACAO_ROTEIRO_ACOMPANHAMENTO_ABERTO = 6;

	/**
	 * Permite a consulta de pagamentos e/ ou pagamentos histórico de um imóvel
	 * ou de um cliente ou de um aviso bancário ou de um movimento arrecadador.
	 * Permite também geração do relatório dos pagamentos de um intervalo de
	 * localidades
	 * [UC0247] Consultar Pagamentos
	 * Variáveis para definir tamanho das tabelas
	 * 
	 * @author Vivianne Sousa
	 * @date 16/10/2006
	 */
	Integer NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO = 10;

	/**
	 * Permite a consulta de devoluções e/ou devoluções histórico de um imóvel
	 * ou de um cliente ou de um aviso bancário ou de um movimento arrecadador.
	 * Permite também geração do relatório dos pagamentos de um intervalo de
	 * localidades
	 * Consultar Devoluções
	 * Variáveis para definir tamanho das tabelas
	 * 
	 * @author Vivianne Sousa
	 * @date 17/10/2006
	 */
	Integer NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO = 10;

	/**
	 * Permite que o usuário seleciona qual a origem dos serviços das OS da
	 * pesquisa, podendo ser Solicitados ou Seletivos
	 * [UC0492] - Gerar Relatório Acompanhamento Execução da OS
	 * Variáveis para definir a origem dos Serviços
	 * 
	 * @author Rafael Corrêa
	 * @date 30/10/2006
	 */
	Integer ORIGEM_SERVICO_SOLICITADO = 1;

	Integer ORIGEM_SERVICO_SELETIVO = 2;

	// [UC0494] Gerar Numeração de RA Manual Autor: Raphael Rossiter
	Integer LIMITE_QUANTIDADE_GERACAO_MANUAL = 90;

	// [UC0302] Gerar Débitos a Cobrar de Acréscimos por Impontualidade Autor: Pedro Alexandre
	Short ENCERRAMENTO_ARRECADACAO = Short.valueOf("3");

	// Iniciando criação de constantes para [UC00538] Filtrar RAs na Agencia Reguladora
	// Author: Kassia Albuquerque Data:02/05/2007

	Integer SITUACAO_AGENCIA_TODOS = -1;

	Integer SITUACAO_AGENCIA_PENDENTE = 1;

	Integer SITUACAO_AGENCIA_ENCERRADO = 2;

	Integer SITUACAO_RA_AGENCIA_TODOS = -1;

	Integer SITUACAO_RA_AGENCIA_PENDENTE = 1;

	Integer SITUACAO_RA_AGENCIA_ENCERRADO = 2;

	Integer SITUACAO_RA_AGENCIA_BLOQUEADO = 3;

	// Finalizando criação de constantes para [UC00538] Filtrar RAs na Agencia Reguladora
	// Author: Kassia Albuquerque Data:02/05/2007

	// Constantes utilizadas no caso de uso [UC0682] Filtrar Movimento de Negativador
	// Author: Yara Taciane
	// Date: 22/01/2008
	Short COM_RETORNO = new Short("1");

	Short SEM_RETORNO = new Short("2");

	// Constantes utilizadas no caso de uso [UC0675] Excluir Negativação OnLine
	// Author: Yara Taciane
	// Date: 05/03/2008
	Short ACEITO = new Short("1");

	Short NAO_ACEITO = new Short("2");

	// Constantes utilizadas no caso de uso [UC0682] Filtrar Movimento de Negativador
	// Author: Yara Taciane
	// Date: 22/01/2008
	Short CORRIGIDO = new Short("1");

	Short NAO_CORRIGIDO = new Short("2");

	/**
	 * Permite a inclusão de um novo perfil de parcelamento
	 * [UC0541] Emitir 2a via Conta Emitida
	 * Variáveis para definir tamanho das tabelas
	 * 
	 * @author Vivianne Sousa
	 * @date 05/10/2007
	 */
	Integer NUMERO_MAXIMO_REGISTROS_CONTA = 3;

	// Tipos de Débitos
	Integer DEBITO_A_COBRAR = 1;

	Integer CREDITO_A_REALIZAR = 2;

	Integer CONTA = 3;

	Integer ACRESCIMO = 4;

	Integer GUIA_PAGAMENTO = 5;

	Integer PARCELAMENTO_FORA_CONTA = 6;

	// Constantes usadas no caso de uso [UC0656] Inserir Negativador
	// Author: Thiago Vieira
	// Date: 20/12/2007

	String CODIGO_AGENTE_JA_CADASTRADO = "CÓDIGO DO AGENTE JÁ CADASTRADO";

	// fim se constantes para caso de uso [UC0656] Inserir Negativador

	// Constantes usadas no caso de uso [UC0658] Filtrar Negativador
	// Author: Thiago Vieira
	// Date: 22/12/2007

	String CODIGO_AGENTE_NAO_CADASTRADO = "CÓDIGO DO AGENTE NÃO CADASTRADO";

	// fim se constantes para caso de uso [UC0656] Inserir Negativador

	// Constantes usadas no caso de uso [UC0655] Filtrar Comando de Negativação
	// Author: Thiago Vieira
	// Date: 27/12/2007

	String COMANDO_SIMULADO_SIM = "1";

	String COMANDO_SIMULADO_NAO = "2";

	String COMANDO_SIMULADO_TODOS = "3";

	// fim de constantes para caso de uso [UC0655] Filtrar Comando de Negativação

	// Constantes usadas no caso de uso [UC0662] Inserir motivo de Retorno do Registro do
	// Negativador
	// Author: Thiago Vieira
	// Date: 03/01/2008

	Short INDICADOR_REGISTRO_ACEITO = Short.valueOf("1");

	Short INDICADOR_REGISTRO_NAO_ACEITO = Short.valueOf("2");

	String CODIGO_MOTIVO_JA_CADASTRADO = "CÓDIGO DO MOTIVO JÁ CADASTRADO";

	String CODIGO_MOTIVO_INEXISTENTE = "CÓDIGO DO MOTIVO INEXISTENTE";

	// fim de Constantes usadas no caso de uso [UC0662] Inserir motivo de Retorno do Registro do
	// Negativador

	// Constantes utilizadas no caso de uso [UC0654] Consultar Comandos de Negativação
	String CODIGO_SETOR_COMERCIAL_INEXISTENTE = "CÓDIGO DO SETOR COMERCIAL INEXISTENTE";

	// Author: Thiago Vieira
	// Date: 09/01/2007
	Short TIPO_COMANDO_POR_CRITERIO = Short.valueOf("1");

	Short TIPO_COMANDO_POR_MATRICULA_IMOVEIS = Short.valueOf("2");

	// Fim de Constantes utilizadas no caso de uso [UC0654] Consultar Comandos de Negativação

	// Constantes usadas no caso de uso [UC1000] Inserir Empresa
	String CODIGO_EMPRESA_JA_CADASTRADO = "CÓDIGO DA EMPRESA JÁ CADASTRADO";

	// Constantes usadas para Nomear Wizards que tem chamadas a partir de outros (ex. Atendimento
	// chama Manter Imóvel)
	String NOME_WIZARD_ALTERAR_IMOVEL = "statusWizardAlterarImovel";

	String NOME_WIZARD_CONSULTA_ATENDIMENTO_IMOVEL = "statusWizardConsultaAtendimentoImovel";

	String NOME_WIZARD_INSERIR_CLIENTE = "statusWizardIncluirCliente";

	String NOME_WIZARD_ALTERAR_CLIENTE = "statusWizardAlterarCliente";

	String NOME_WIZARD_EFETUAR_PARCELAMENTO = "statusWizardEfetuarParcelamento";

	String NOME_WIZARD_INSERIR_REGISTRO_ATENDIMENTO = "statusWizardInserirRegistroAtendimento";

	// Num. de Contas a serem impressas (por bloco)
	int QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO = 1000;

	Short INDICADOR_VENCIMENTO_GRUPO_REFERENCIA_IGUAL = 1;

	Short INDICADOR_VENCIMENTO_GRUPO_REFERENCIA_POSTERIOR = 2;

	Short INDICADOR_VENCIMENTO_GRUPO_REFERENCIA_ANTERIOR = 3;

	String TEXTO_QUEBRA_PAGINA_FATURA = "CONTINUA ...";

	int PARCELAMENTO_VALOR_GUIA_TOTAL = 1;

	int PARCELAMENTO_VALOR_GUIA_JUROS_MORA = 2;

	int PARCELAMENTO_VALOR_GUIA_ATUALIZACAO_MONETARIA = 3;

	int PARCELAMENTO_VALOR_GUIA_MULTA = 4;

	int PARCELAMENTO_VALOR_GUIA_ACRESCIMO_IMPONTUALIDADE = 5;

	// Constantes usadas no caso de uso [UC0677] Informar Dados para Consulta da Negativação
	// Author: Thiago Vieira
	// Date: 28/01/2008
	String CODIGO_ELO_POLO_INEXISTENTE = "CÓDIGO DO ELO PÓLO INEXISTENTE";

	String CODIGO_QUADRA_INEXISTENTE = "CÓDIGO DA QUADRA INEXISTENTE";

	// Fim de Constantes utilizadas no caso de uso [UC0352] Emitir Contas e Cartas

	int QUITACAO_PARCELAS = 1;

	int ANTECIPACAO_PARCELAS = 2;

	int SERVICO_TIPO_SUBGRUPO_SUBSTITUICAO_HIDROMETRO = 24;

	int SERVICO_TIPO_SUBGRUPO_MANUTENCAO_HIDROMETRO = 31;

	int QUANTIDADE_LIMITE_RELATORIOS_POR_ARQUIVO = 1000;

	int TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO = 7;

	int QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO = 1000;

	int QUANTIDADE_ASSOCIAR_AGENTES_ORDEM_SERVICO = 1000;

	int QUANTIDADE_LIMITE_ITEM_CONSULTA = 1000;

	// Ordem Serviço - REDE/RAMAL
	String INDICADOR_REDE = "1";

	String INDICADOR_RAMAL = "2";

	String INDICADOR_CAVALETE = "3";

	Integer MUNICIPIO_ADA = 1;

	Integer VALIDO = 1;

	Integer INVALIDO = 0;

	String POR_IMOVEL = "1";

	String EM_LOTE = "2";

	// Imovel - Contrato de Consumo
	int INDICADOR_CONTRATO_CONSUMO_CATEGORIA = 2;

	int INDICADOR_CONTRATO_CONSUMO_SUBCATEGORIA = 1;

	int DIFERENCA_DIAS = 3;

	int DIFERENCA_HORAS = 4;

	int DIFERENCA_MINUTOS = 5;

	int DIFERENCA_SEGUNDOS = 6;

	int DIFERENCA_MILISEGUNDOS = 7;

	String NOME_SUBRELATORIO_ORDEM_SERVICO = "nomeSubRelatorioTipoServico";

	String TITULO_TIPO_SERVICO = "tituloTipoServico";

	String DADOS_CENSITARIOS_LOCALIDADE = "1";

	String DADOS_CENSITARIOS_MUNICIPIO = "2";

	Integer NUMERO_MAXIMO_CATEGORIAS = 9999;

	String CODIGO_BANCO_FICHA_COMPENSACAO = "001";

	String CODIGO_MOEDA_FICHA_COMPENSACAO = "9";

	String CARTEIRA_FICHA_COMPENSACAO = "18";

	Short INDICADOR_PERCENTUAL_VALOR_REF_TAXA = Short.valueOf("1");

	Short INDICADOR_VALOR_REF_TAXA = Short.valueOf("2");

	Integer BOLETO_BANCARIO_LANCAMENTO_ENVIO_PEDIDO_DE_BAIXA = 2;

	Integer BOLETO_BANCARIO_LANCAMENTO_ENVIO_SUSTAR_PROTESTO_E_BAIXAR_TITUL = 10;

	// Tipo de Relatórios de Contas Emitidas
	Integer RELATORIO_CONTAS_EMITIDAS_RESPONSAVEL = 1;

	Integer RESUMO_CONTAS_EMITIDAS = 2;

	String GERAR_MOVIMENTO = "1";

	String REGERAR_MOVIMENTO = "2";

	short CD_SITUACAO_RA_ENCERRADO = 2;

	// Integer ATENDIMENTO_MOTIVO_ENCERRAMENTO_ID_PRIMEIRA_PARCELA_NAO_PAGA = 38;

	// Chaves do arquivo de propriedades de Débito Tipo.
	public static enum ChaveMotivoNaoGeracaoDocumento {
		IMOVEL_EXCLUIDO, CLIENTE_SEM_CPF_CNPJ, IMOVEL_SEM_CEP, IMOVEL_SEM_DOC_PRECED_VALIDO, IMOVEL_SEM_OS_EXECUTADA,
		IMOVEL_COM_NEGOCIACAO_DEB_PENDENTE, IMOVEL_COM_NEGATIVACAO_VALIDA, IMOVEL_COM_DOCUMENTO_TIPO_VALIDO, IMOVEL_NAO_SATISFAZ_RD,
		EXCEDEU_QTD_MAX_DOCUMENTO, SIT_LIGACAO_AGUA, SIT_LIGACAO_ESGOTO, IMOVEL_SIT_ESPECIAL_COBRANCA, IMOVEL_SIT_COBRANCA_NAO_PERMITIDA,
		PERFIL_IMOVEL_NAO_PERMITIDO, IMOVEL_NAO_SATISFAZ_CRITERIO, IMOVEL_SEM_DEBITO, IMOVEL_COM_DOCUMENTO_SUCESSOR_VALIDO,
		IMOVEL_EM_COBRANCA_BANCARIA, IMOVEL_COM_ITEM_REMUNERAVEL, IMOVEL_COM_DEBITO_ANTIGO_PARA_ACAO, IMOVEL_COM_DEBITO_SOMENTE_CONTA_MES,
		IMOVEL_COM_DEBITO_SOMENTE_CONTA_ANTIGA, IMOVEL_COM_DEBITO_SOMENTE_CONTA_MES_ANTIGA, CLIENTE_SEM_NOME_CONTA, CLIENTE_SEM_FONE;
	}

	String INDICADOR_COBRANCA_PARC_DEBITO_A_COBRAR = "1";

	String INDICADOR_COBRANCA_PARC_GUIA_PAGAMENTO = "2";

	int CD_CONTROLE_ANORMALIDADE_GRUPO_FATURAMENTO = 1;

	int CD_CONTROLE_ANORMALIDADE_SETOR_COMERCIAL = 2;

	int CD_CONTROLE_ANORMALIDADE_ROTA = 3;

	// Cobrança Acão com situação pendente
	int SITUACAO_COBRANCA_ACAO_PENDENTE = 1;

	// Indicadores Cobrança Documento
	String INDICADOR_ENTREGA_DOCUMENTO = "1";

	String INDICADOR_DEVOLUCAO_DOCUMENTO = "2";

	// TIPO_PAGAMENTO Legado DESO
	String TIPO_PAGAMENTO_LEGADO_CONTAS_AGRUPADAS_DESO = "CONTAS AGRUPADAS";

	String TIPO_PAGAMENTO_LEGADO_CARNE_DESO = "CARNÊ";

	String TIPO_PAGAMENTO_LEGADO_NOTA_RECEBIMENTO_DESO = "NOTA RECEBIMENTO";

	int PAGAMENTO_LEGADO_DESO_UM = 1;

	int PAGAMENTO_LEGADO_DESO_DOIS = 2;

	String CODIGO_TIPO_PAGAMENTO_LEGADO_CONTA_SEGUNDA_VIA_DESO = "82";

	String CODIGO_TIPO_PAGAMENTO_LEGADO_AVISO_CORTE_DEBITO_DESO = "90";

	String CODIGO_TIPO_PAGAMENTO_LEGADO_CONTA_AGRUPADAS_DESO = "91";

	String CODIGO_TIPO_PAGAMENTO_LEGADO_CARNE_DESO = "94";

	String CODIGO_TIPO_PAGAMENTO_LEGADO_NOTA_RECEBIMENTO_DESO = "15";

	// Chaves motivo de cancelamento da conta para registrar lançamento contábil

	Integer CANCELAR_CONTA_PREFEITURA = 77;

	Integer CANCELAR_CONTA_APT = 75;

	Integer CANCELAR_CONTA_PRESCRICAO = 50;

	int CONSULTA_ROTAS_FATURADAS = 1;

	int CONSULTA_ROTAS_NAO_FATURADAS = 2;

	// TIPO GUIA PAGAMENTO
	Short INDICADOR_TIPO_GUIA_VENCIDA = Short.valueOf("1");;

	Short INDICADOR_TIPO_GUIA_NAO_VENCIDA = Short.valueOf("2");;

	Short INDICADOR_TIPO_GUIA_VENCIDA_E_NAO_VENCIDA = Short.valueOf("3");;

	// Situação dos Grupos de Faturamento
	Short FATURADOS = Short.valueOf("1");

	Short NAO_FATURADOS = Short.valueOf("2");

	String SITUACAO_GRUPO_FATURADO = "Faturado";

	String SITUACAO_GRUPO_NAO_FATURADO = "Não Faturado";

	// Tipo do arquivo gerado em OS Seletiva.
	String PDF = "1";

	String TXT = "2";

	// Indicador critério.
	String INDICADOR_CRITERIO_ACAO = "1";

	String INDICADOR_CRITERIO_COMANDO = "2";

	// [UC3052] Gerar e Emitir Aviso de Corte e Ordem de Corte Individual
	Short GERAR = Short.valueOf("1");

	Short GERAR_EMITIR = Short.valueOf("2");

	Short EMITIR = Short.valueOf("3");

	Integer ATIVIDADE_EFETUADA_SEM_SUCESSO = 9;

	Integer ATIVIDADE_EFETUADA_COM_SUCESSO = 0;

	String ATIVO = "1";

	String INATIVO = "2";

	String DIA_VENCIMENTO_PARAMETRIZADO = "1";

	String QUINTO_DIA_UTIL = "2";

	Short CEM = Short.valueOf("100");

	String DATA_INSTALACAO_MAIOR_QUE_DATA_LEITURA = "1";

	String QUANTIDADE_DIAS_CONSUMO = "2";

	Short AVISO_ABERTO_FECHADO = 1;

	Short AVISO_ABERTO = 2;

	Short AVISO_FECHADO = 3;

	String TOKEN_RETORNO_EVENTO_COMERCIAL_BAIXA = "BAIXA";

	Short CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA = Short.valueOf("1");

	Short CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE = Short.valueOf("2");

	Short INDICADOR_ANTES = 1;

	Short INDICADOR_APOS = 2;

	Short GERACAO_HISTOGRAMA_FATURAMENTO = Short.valueOf("1");

	Short GERACAO_HISTOGRAMA_INCLUSAO = Short.valueOf("2");

	Short GERACAO_HISTOGRAMA_CANCELAMENTO = Short.valueOf("3");

	// OC0756386 Roteiro
	Integer ROTEIRO_PROGRAMACAO_LAYOUT_RETRATO = Integer.valueOf("1");

	Integer ROTEIRO_PROGRAMACAO_LAYOUT_PAISAGEM = Integer.valueOf("2");

	Short INDICADOR_AVISO_ACERTO_ARRECADACAO = Short.valueOf("1");

	Short INDICADOR_AVISO_ACERTO_DEVOLUCAO = Short.valueOf("2");

	Short INDICADOR_ACESSO_LIBERADADO_DADOS_IMOVEL = Short.valueOf("1");

	Short INDICADOR_ACESSO_NAO_LIBERADADO_DADOS_IMOVEL = Short.valueOf("2");

	String CODIGO_HIST_LANC_CONT_SINTETICO_COM_CONTA_BANCARIA = "06.09";

	String CODIGO_HIST_LANC_CONT_SINTETICO_SEM_CONTA_BANCARIA = "01.57";

	String INDICADOR_LIDO = "1";

	String INDICADOR_NAO_LIDO = "0";

	String DEBITOS_AGRUPADOS = "debitosAgrupados";

	String VALOR_NAO_INFORMADO = "-1";

	int VALOR_MENOR = -1;

	int CODIGO_GERENCIA_REGIONAL_UNIDADE_NEGOCIO = 25;

	String GERENCIA_REGIONAL_UNIDADE_NEGOCIO = "GERÊNCIA REGIONAL POR UNIDADE NEGÓCIO";

	int VALOR_MAIOR = 1;

	char CLASSIFICAR = 'C';

	Integer OCORRENCIA_ENTRADA_PAGA_SEM_BOLETOS = 1;

	Integer OCORRENCIA_ENTRADA_VENCIDA_SEM_BOLETOS = 2;

	Integer OCORRENCIA_PERMITE_PEDIDO_BAIXA_SEM_MOVIMENTACAO_ENVIO_PENDENTE = 3;

	Integer OCORRENCIA_PERMITE_PEDIDO_BAIXA_COM_RETORNO_ENTRADA_OU_BOLETOS_CANCELADOS = 4;

	Integer OCORRENCIA_BOLETOS_GERADOS_PARA_SUBSTITUIR_BOLETOS_BAIXADOS = 5;

	String CREDITO_TOTAL_DISPONIVEL = "creditoTotalDisponivel";

	// 841104 Codigo do Lado CEP
	String PAR = "P";

	String IMPAR = "I";

	String AMBOS = "A";

	// [UC0458] Imprimir Ordem de Serviço
	String INDICADOR_DATA_CORTE = "DATA_CORTE";

	String INDICADOR_DATA_SUPRESSAO = "DATA_SUPRESSAO";

	Boolean INDICADOR_CHAMADA_TELA_ORDEM_SERVICO_SELETIVA = Boolean.TRUE;
	
	Integer SEM_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL = -1;

	Integer NUMERO_MAXIMO_REGISTROS_CONSULTA = 10;
	
	int CONSULTA_UC0188_SB0001_ITEM_3 = 1;

	int CONSULTA_UC0188_SB0009_ITEM_1_2 = 2;

	String BOLETIM_CADASTRAL_MODELO_1 = "Modelo1";

	String BOLETIM_CADASTRAL_MODELO_2 = "Modelo2";

	// COBRANCA_ACAO
	String COBRANCA_ADMINISTRATIVA = "37";

	// CAMPANHA PREMIAÇÃO
	String CAMPANHA_PREMIACAO_PREFIXO_NOME_SEQUENCE = "SQ_CAMPANHA_UNID_PREM_";

	String PREMIACAO_DA_UNIDADE = "1";

	String PREMIACAO_GLOBAL = "2";

	String PREMIACAO_AMBAS = "3";

	String INDICADOR_UNIDADE_PREMIACAO_POR_GERENCIA_REGIONAL = "1";

	String INDICADOR_UNIDADE_PREMIACAO_POR_UNIDADE_NEGOCIO = "2";

	String INDICADOR_UNIDADE_PREMIACAO_POR_ELO = "3";

	String INDICADOR_UNIDADE_PREMIACAO_POR_LOCALIDADE = "4";

	String INDICADOR_UNIDADE_PREMIACAO_GLOBAL = "5";

	String BLOQUEIO_IMOVEL_CAMPANHA_CATEGORIA_NAO_CONTEMPLADA = "1";

	String BLOQUEIO_IMOVEL_CAMPANHA_SITUACAO_LIGACAO_NAO_CONTEMPLADA = "2";

	Integer DOCUMENTO_IMPEDIDO_CPF = 1;

	Integer DOCUMENTO_IMPEDIDO_CNPJ = 2;

	int CD_VALOR_A_RETIRAR_AGUA = 1;

	int CD_VALOR_A_RETIRAR_ESGOTO = 2;

	int CD_VALOR_A_RETIRAR_AMBOS = 3;

	String BANCO_ORACLE = "Oracle";

	String BANCO_POSTGRESQL = "PostgreSQL";


	String CONTRATO_DEMANDA_ABERTO = "2";

	String AMBOS_CONTRATO = "3";

	String ACAO_INSERIR = "INSERIR";

	String ACAO_ATUALIZAR = "ATUALIZAR";

	Integer QUANTIDADE_LIMITE_REGISTROS_SIMULACAO_FATURAMENTO = 530000;
}
