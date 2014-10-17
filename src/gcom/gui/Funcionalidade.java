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

package gcom.gui;

public interface Funcionalidade {

	// *** Caminhos das funcionalidades de sistema ***//
	// TABELAAUXILIAR
	String TABELA_AUXILIAR_INSERIR = "exibirInserirTabelaAuxiliarAction.do";

	String TABELA_AUXILIAR_MANTER = "exibirManterTabelaAuxiliarAction.do";

	String TABELA_AUXILIAR_FILTRAR = "exibirFiltrarTabelaAuxiliarAction.do";

	// TABELAAUXILIARABREVIADA
	String TABELA_AUXILIAR_ABREVIADA_INSERIR = "exibirInserirTabelaAuxiliarAbreviadaAction.do";

	String TABELA_AUXILIAR_ABREVIADA_MANTER = "exibirManterTabelaAuxiliarAbreviadaAction.do";

	String TABELA_AUXILIAR_ABREVIADA_FILTRAR = "exibirFiltrarTabelaAuxiliarAbreviadaAction.do";

	// TABELAAUXILIARFAIXA
	String TABELA_AUXILIAR_FAIXA_INSERIR = "exibirInserirTabelaAuxiliarFaixaAction.do";

	String TABELA_AUXILIAR_FAIXA_FILTRAR = "exibirFiltrarTabelaAuxiliarAction.do";

	String TABELA_AUXILIAR_FAIXA_MANTER = "exibirManterTabelaAuxiliarFaixaAction.do";

	// TABELAAULIARFAIXAREAL

	String TABELA_AUXILIAR_FAIXA_REAL_INSERIR = "exibirInserirTabelaAuxiliarFaixaRealAction.do";

	String TABELA_AUXILIAR_FAIXA_REAL_MANTER = "exibirManterTabelaAuxiliarFaixaRealAction.do";

	String TABELA_AUXILIAR_FAIXA_REAL_FILTRAR = "exibirFiltrarTabelaAuxiliarFaixaRealAction.do";

	// TABELAAULIARINDICADOR

	String TABELA_AUXILIAR_INDICADOR_INSERIR = "exibirInserirTabelaAuxiliarIndicadorAction.do";

	String TABELA_AUXILIAR_INDICADOR_MANTER = "exibirManterTabelaAuxiliarIndicadorAction.do";

	String TABELA_AUXILIAR_INDICADOR_FILTRAR = "exibirFiltrarTabelaAuxiliarIndicadorAction.do";

	// TABELAAUXILIARTIPO
	String TABELA_AUXILIAR_TIPO_INSERIR = "exibirInserirTabelaAuxiliarTipoAction.do";

	String TABELA_AUXILIAR_TIPO_MANTER = "exibirManterTabelaAuxiliarTipoAction.do";

	String TABELA_AUXILIAR_TIPO_FILTRAR = "exibirFiltrarTabelaAuxiliarTipoAction.do";

	// TABELAAULIARFAIXAREAL

	String TABELA_AUXILIAR_FAIXA_INTEIRO_INSERIR = "exibirInserirTabelaAuxiliarFaixaInteiroAction.do";

	String TABELA_AUXILIAR_FAIXA_INTEIRO_MANTER = "exibirManterTabelaAuxiliarFaixaInteiroAction.do";

	String TABELA_AUXILIAR_FAIXA_INTEIRO_FILTRAR = "exibirFiltrarTabelaAuxiliarFaixaInteiroAction.do";

	// *** Parametros passados no get das telas (objetos) do sistema ***//
	// TABELAAUXILIAR

	String TELA_OPERACAO = "?tela=operacao";

	String TELA_TIPO_PAVIMENTO_CALCADA = "?tela=tipoPavimentoCalcada";

	String TELA_TIPO_PAVIMENTO_RUA = "?tela=tipoPavimentoRua";

	String TELA_LOCALIDADE_CLASSE = "?tela=localidadeClasse";

	String TELA_LOCALIDADE_PORTE = "?tela=localidadePorte";

	String TELA_FONTE_DADO_CENSITARIO = "?tela=fonteDadoCensitario";

	String TELA_SETOR_CENSITARIO = "?tela=setorCensitario";

	String TELA_CEP_TIPO = "?tela=cepTipo";

	String TELA_COBRANCA_SITUACAO = "?tela=cobrancaSituacao";

	String TELA_IMOVEL_PERFIL = "?tela=imovelPerfil";

	String TELA_LIGACAO_ESGOTO_SITUACAO = "?tela=ligacaoEsgotoSituacao";

	String TELA_LIGACAO_AGUA_SITUACAO = "?tela=ligacaoAguaSituacao";

	String TELA_NOME_CONTA = "?tela=nomeConta";

	String TELA_LOGRADOURO_TIPO = "?tela=logradouroTipo";

	String TELA_LOGRADOURO_TITULO = "?tela=logradouroTitulo";

	String TELA_ENDERECO_TIPO = "?tela=enderecoTipo";

	String TELA_FONE_TIPO = "?tela=foneTipo";

	String TELA_PESSOA_SEXO = "?tela=pessoaSexo";

	String TELA_RENDA_TIPO = "?tela=rendaTipo";

	String TELA_TARIFA_SOCIAL_EXCLUSAO_MOTIVO = "?tela=tarifaSocialExclusaoMotivo";

	String TELA_CLIENTE_IMOVEL_TIPO = "?tela=clienteImovelTipo";

	String TELA_USUARIO_ACAO = "?tela=usuarioAcao";

	String TELA_USUARIO_TIPO = "?tela=usuarioTipo";

	String TELA_LIGACAO_ESGOTO_PERFIL = "?tela=ligacaoEsgotoPerfil";

	String TELA_LIGACAO_ESGOTO_MATERIAL = "?tela=ligacaoEsgotoMaterial";

	String TELA_LIGACAO_ESGOTO_DIAMETRO = "?tela=ligacaoEsgotoDiametro";

	String TELA_LIGACAO_AGUA_PERFIL = "?tela=ligacaoAguaPerfil";

	String TELA_LIGACAO_AGUA_DIAMETRO = "?tela=ligacaoAguaDiametro";

	String TELA_LIGACAO_AGUA_MATERIAL = "?tela=ligacaoAguaMaterial";

	String TELA_EMISSAO_ORDEM_COBRANCA_TIPO = "?tela=emissaoOrdemCobrancaTipo";

	String TELA_CONSUMO_TIPO = "?tela=consumoTipo";

	String TELA_CORTE_TIPO = "?tela=corteTipo";

	String TELA_SUPRESSAO_TIPO = "?tela=supressaoTipo";

	String TELA_SUPRESSAO_PARCIAL_TIPO = "?tela=supressaoParcialTipo";

	String TELA_COBRANCA_PARALISACAO_TIPO = "?tela=cobrancaParalisacaoTipo";

	String TELA_COBRANCA_PARALISACAO_MOTIVO = "?tela=cobrancaParalisacaoMotivo";

	String TELA_FATURAMENTO_PARALISACAO_MOTIVO = "?tela=faturamentoParalisacaoMotivo";

	String TELA_POCO = "?tela=poco";

	String TELA_ELO_ANORMALIDADE = "?tela=eloAnormalidade";

	String TELA_RAMO_ATIVIDADE = "?tela=ramoAtividade";

	String TELA_PROFISSAO = "?tela=profissao";

	String TELA_REGIAO = "?tela=regiao";

	String TELA_REGIAO_DESENVOLVIMENTO = "?tela=regiaoDesenvolvimento";

	String TELA_LEITURA_TIPO = "?tela=leituraTipo";

	String TELA_CLIENTE_IMOVEL_FIM_RELACAO_MOTIVO = "?tela=clienteImovelFimRelacaoMotivo";

	String TELA_IBGE_SETOR_CENSITARIO = "?tela=ibgeSetorCensitario";

	// TABELAAUXILIARABREVIADA
	String TELA_CATEGORIA = "?tela=categoria";

	String TELA_ZEIS = "?tela=zeis";

	String TELA_FATURAMENTO_GRUPO = "?tela=faturamentoGrupo";

	String TELA_COBRANCA_GRUPO = "?tela=cobrancaGrupo";

	String TELA_SISTEMA_ABASTECIMENTO = "?tela=sistemaAbastecimento";

	String TELA_UNIDADE_FEDERACAO = "?tela=unidadeFederacao";

	String TELA_DESPEJO = "?tela=despejo";

	String TELA_FONTE_ABASTECIMENTO = "?tela=fonteAbastecimento";

	String TELA_EMPRESA = "?tela=empresa";

	String TELA_ENDERECO_IMOVEL_REFERENCIA = "?tela=enderecoImovelReferencia";

	String TELA_HIDROMETRO_LOCAL_ARMAZENAGEM = "?tela=hidrometroLocalArmazenagem";

	String TELA_HIDROMETRO_LOCAL_ARMAZENAGEM_ORIGEM = "?tela=hidrometroLocalArmazenagemOrigem";

	String TELA_HIDROMETRO_LOCAL_ARMAZENAGEM_DESTINO = "?tela=hidrometroLocalArmazenagemDestino";

	String TELA_EQUIPAMENTOS_ESPECIAIS = "?tela=equipamentosEspeciais";

	String TELA_TABELA = "?tela=tabela";

	String TELA_BANCO = "?tela=banco";

	String TELA_AREA_TIPO = "?tela=areaTipo";

	String TELA_HIDROMETRO_CLASSE_METROLOGICA = "?tela=hidrometroClasseMetrologica";

	String TELA_HIDROMETRO_LOCAL_INSTALACAO = "?tela=hidrometroLocalInstalacao";

	String TELA_HIDROMETRO_PROTECAO = "?tela=hidrometroProtecao";

	String TELA_HIDROMETRO_TIPO = "?tela=hidrometroTipo";

	String TELA_INFRACAO_TIPO = "?tela=infracaoTipo";

	String TELA_IMPOSTO_TIPO = "?tela=impostoTipo";

	String TELA_INFRACAO_IMOVEL_SITUACAO = "?tela=infracaoImovelSituacao";

	String TELA_INFRACAO_LIGACAO_SITUACAO = "?tela=infracaoLigacaoSituacao";

	String TELA_LANCAMENTO_ITEM = "?tela=lancamentoItem";

	String TELA_LOCAL_ENTREGA_DOCUMENTO = "?tela=localEntregaDocumento";

	String TELA_MATERIAL_CAVA_AGUA = "?tela=materialCavaleteAgua";

	String TELA_MATERIAL_RAMAL_AGUA = "?tela=materialRamalAgua";

	String TELA_MATERIAL_RAMAL_ESGOTO = "?tela=materialRamalEsgoto";

	String TELA_MATERIAL_REDE_AGUA = "?tela=materialRedeAgua";

	String TELA_MATERIAL_REDE_ESGOTO = "?tela=materialRedeEsgoto";

	String TELA_MEIO_SOLICITACAO = "?tela=meioSolicitacao";

	String TELA_MOTIVO_INTERRUPCAO = "?tela=motivoInterrupcao";

	String TELA_MOTIVO_NAO_ENTREGA_DOCUMENTO = "?tela=motivoNaoEntregaDocumento";

	String TELA_ORGAO_EXPEDIDOR_RG = "?tela=orgaoExpedidorRg";

	String TELA_OS_PROGRAM_NAO_ENCER_MOTIVO = "?tela=osProgramNaoEncerMotivo";

	String TELA_PAGAMENTO_SITUACAO = "?tela=pagamentoSituacao";

	String TELA_PAVIMENTO_CALCADA = "?tela=pavimentoCalcada";

	String TELA_PAVIMENTO_RUA = "?tela=pavimentoRua";

	String TELA_PROCESSO_SITUACAO = "?tela=processoSituacao";

	String TELA_RAMAL_LOCAL_INSTALACAO = "?tela=ramalLocalInstalacao";

	String TELA_RA_MOTIVO_REATIVACAO = "?tela=raMotivoReativacao";

	String TELA_RELATORIO = "?tela=relatorio";

	String TELA_SERVICO_TIPO_GRUPO = "?tela=servicoTipoGrupo";

	String TELA_TRAMITE_MOTIVO = "?tela=tramiteMotivo";

	String TELA_UNIDADE_PROCESSAMENTO = "?tela=unidadeProcessamento";

	String TELA_UNIDADE_SITUACAO = "?tela=unidadeSituacao";

	String TELA_ACAO_COBRANCA_EFEITO = "?tela=acaoCobrancaEfeito";

	String TELA_AGENCIA_REGULADORA_MOT_RETORNO = "?tela=agenciaReguladoraMotRetorno";

	String TELA_AGENCIA_REGULADORA_MOT_RECLAMACAO = "?tela=agenciaReguladoraMotReclamacao";

	String TELA_AGENTE_EXTERNO = "?tela=agenteExterno";

	String TELA_ATENDIMENTO_INCOMPLETO_MOTIVO = "?tela=atendimentoIncompletoMotivo";

	String TELA_BOLETO_BANCARIO_MOT_CANCEL = "?tela=boletoBancarioMotivoCancelamento";

	String TELA_BOLETO_BANCARIO_SITUACAO = "?tela=boletoBancarioSituacao";

	String TELA_CAUSA_VAZAMENTO = "?tela=causaVazamento";

	String TELA_COBRANCA_ACAO_SITUACAO = "?tela=cobrancaAcaoSituacao";

	String TELA_COBRANCA_FORMA = "?tela=cobrancaForma";

	String TELA_CONSUMO_ANORMALIDADE = "?tela=consumoAnormalidade";

	String TELA_CONSUMO_FIXO_UNIDADE = "?tela=consumoFixoUnidade";

	String TELA_CREDITO_ORIGEM = "?tela=creditoOrigem";

	String TELA_DEBITO_AUTOM_MOVTO_CANC_MOTIVO = "?tela=debitoAutomaticoMovimentoCancelamentoMotivo";

	String TELA_DEDUCAO_TIPO = "?tela=deducaoTipo";

	String TELA_DEVOLUCAO_SITUACAO = "?tela=devolucaoSituacao";

	String TELA_DIAMETRO_CAVA_AGUA = "?tela=diametroCavaleteAgua";

	String TELA_DIAMETRO_RAMAL_AGUA = "?tela=diametroRamalAgua";

	String TELA_DIAMETRO_RAMAL_ESGOTO = "?tela=diametroRamalEsgoto";

	String TELA_DIAMETRO_REDE_AGUA = "?tela=diametroRedeAgua";

	String TELA_ENDERECO_REFERENCIA = "?tela=enderecoReferencia";

	String TELA_ESGOTO_TRATAMENTO_TIPO = "?tela=esgotoTratamentoTipo";

	String TELA_EVENTO_GERACAO = "?tela=eventoGeracao";

	String TELA_DIAMETRO_REDE_ESGOTO = "?tela=diametroRedeEsgoto";

	String TELA_FORMA_ENCERRAMENTO = "?tela=formaEncerramento";

	String TELA_FORMA_GERACAO = "?tela=formaGeracao";

	String TELA_FORMA_PROGRAMACAO = "?tela=formaProgramacao";

	String TELA_FORMA_SELECAO_EQUIPE = "?tela=formaSelecaoEquipe";

	String TELA_FORMA_TRAMITE = "?tela=formaTramite";

	// TABELAAUXILIARAREACONSTRUIDA
	String TELA_AREA_CONSTRUIDA = "?tela=areaConstruida";

	// TABELAAUXILIABACIA
	String TELA_BACIA = "?tela=bacia";

	// TABELAAUXILIARUNIDADE
	String TELA_MATERIAL = "?tela=material";

	// TABELAAUXILIARFAIXAREAL

	String TELA_PISCINA_VOLUME_FAIXA = "?tela=piscinaVolumeFaixa";

	String TELA_RESERVATORIO_VOLUME_FAIXA = "?tela=reservatorioVolumeFaixa";

	// TABELAAUXILIARINDICADOR

	String TELA_QUADRA_PERFIL = "?tela=quadraPerfil";

	// TABELA AUXILIAR FAIXA INTEIRO

	String TELA_AREA_CONSTRUIDA_FAIXA = "?tela=areaConstruidaFaixa";

	String TELA_FUNCIONALIDADE = "?tela=funcionalidade";

	String TELA_MOTIVO_NAO_GERACAO_DOCUMENTO = "?tela=motivoNaoGeracaoDocumento";

	String TELA_RACA = "?tela=raca";

	String TELA_ESTADO_CIVIL = "?tela=estadoCivil";

	String TELA_NACIONALIDADE = "?tela=nacionalidade";

}