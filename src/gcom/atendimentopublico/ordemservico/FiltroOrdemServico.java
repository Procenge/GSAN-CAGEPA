/**
 * 
 */
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

package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtar da Ordem de Servi�o
 * 
 * @author Rafael Santos
 * @since 09/1/2006
 */
public class FiltroOrdemServico
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroOrdemServico object
	 */
	public FiltroOrdemServico() {

	}

	/**
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroOrdemServico(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	// este campo ainda n�o foi defenido (s� na Itera��o 7)
	public final static String DATA_ENCERRAMENTO = "dataEncerramento";

	public final static String REGISTRO_ATENDIMENTO_ID = "registroAtendimento.id";

	public final static String ID_IMOVEL = "imovel.id";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_LOCALIDADE = "imovel.localidade";

	public final static String IMOVEL_SETOR = "imovel.setorComercial";

	public final static String IMOVEL_QUADRA = "imovel.quadra";

	public final static String ATENDIMENTO_MOTIVO_ENCERRAMENTO = "atendimentoMotivoEncerramento";

	public final static String REGISTRO_ATENDIMENTO = "registroAtendimento";

	public final static String DEBITO_TIPO = "servicoTipo.debitoTipo";

	public final static String SOLICITACAO_TIPO_ESPECIFICACAO = "registroAtendimento.solicitacaoTipoEspecificacao";

	public final static String CREDITO_TIPO = "servicoTipo.creditoTipo";

	public final static String SERVICO_TIPO = "servicoTipo";

	public final static String SERVICO_TIPO_ID = "servicoTipo.id";

	public final static String SERVICO_TIPO_ORDEM_SERVICO_LAYOUT = "servicoTipo.ordemServicoLayout";

	public final static String SERVICO_TIPO_ORDEM_SERVICO_LAYOUT_ID = "servicoTipo.ordemServicoLayout.id";

	public final static String SERVICO_TIPO_SERVICOS_ASSOCIADOS = "servicoTipo.servicosAssociados";

	public final static String SERVICO_TIPO_PRIORIDADE_ATUAL = "servicoTipoPrioridadeAtual";

	public final static String SITUACAO = "situacao";

	public final static String SERVICO_TIPO_REFERENCIA = "servicoTipo.servicoTipoReferencia";

	public static final String DATA_GERACAO = "dataGeracao";

	public final static String SERVICO_TIPO_SUBGRUPO = "servicoTipo.servicoTipoSubgrupo";

	public final static String LIGACAO_AGUA = "ligacaoAgua";

	public final static String LIGACAO_AGUA_ID = "ligacaoAgua.id";

	public final static String HIDROMETRO_INSTALACAO_HISTORICO = "hidrometroInstalacaoHistorico";

	public final static String HIDROMETRO_INSTALACAO_HISTORICO_ID = "hidrometroInstalacaoHistorico.id";

	public final static String COBRANCA_DOCUMENTO_ID = "cobrancaDocumento.id";

	public final static String COBRANCA_DOCUMENTO = "cobrancaDocumento";

	public final static String COBRANCA_DOCUMENTO_IMOVEL = "cobrancaDocumento.imovel";

	public final static String COBRANCA_DOCUMENTO_COBRANCA_ACAO_SITUACAO = "cobrancaDocumento.cobrancaAcaoSituacao";

	public final static String COBRANCA_DOCUMENTO_COBRANCA_ACAO_ATIVIDADE_COMANDO = "cobrancaDocumento.cobrancaAcaoAtividadeComando";

	public final static String COBRANCA_DOCUMENTO_COBRANCA_ACAO_ID = "cobrancaDocumento.cobrancaAcao.id";

	public final static String COBRANCA_DOCUMENTO_COBRANCA_ACAO = "cobrancaDocumento.cobrancaAcao";

	public final static String COBRANCA_DOCUMENTO_COBRANCA_ACAO_COBRANCA_ACAO_PREDECESSORA = "cobrancaDocumento.cobrancaAcao.cobrancaAcaoPredecessora";

	public final static String COBRANCA_ACAO_ATIVIDADE_COMANDO = "cobrancaAcaoAtividadeComando";

	public final static String COBRANCA_ACAO_ATIVIDADE_COMANDO_ID = "cobrancaAcaoAtividadeComando.id";

	public final static String COBRANCA_ACAO_ATIVIDADE_COMANDO_COBRANCA_ACAO = "cobrancaAcaoAtividadeComando.cobrancaAcao";

	public final static String COBRANCA_ACAO_ATIVIDADE_COMANDO_COBRANCA_ACAO_ID = "cobrancaAcaoAtividadeComando.cobrancaAcao.id";

	public final static String COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA = "cobrancaAcaoAtividadeCronograma";

	public final static String COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID = "cobrancaAcaoAtividadeCronograma.id";

	public final static String COBRANCA_ACAO_ATIVIDADE_CRONOGRAMACOBRANCA_ACAOCRONOGRAMA = "cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma";

	public final static String COBRANCA_ACAO_ATIVIDADE_CRONOGRAMACOBRANCA_ACAOCRONOGRAMA_ID = "cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma.id";

	public final static String COBRANCA_ACAO_ATIVIDADE_CRONOGRAMACOBRANCA_ACAOCRONOGRAMA_COBRANCAACAO = "cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma.cobrancaAcao";

	public final static String COBRANCA_ACAO_ATIVIDADE_CRONOGRAMACOBRANCA_ACAOCRONOGRAMA_COBRANCAACAO_ID = "cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma.cobrancaAcao.id";

	public final static String DIAMETRO_REDE_AGUA = "diametroRedeAgua";

	public final static String DIAMETRO_RAMAL_AGUA = "diametroRamalAgua";

	public final static String DIAMETRO_CAVALETE_AGUA = "diametroCavaleteAgua";

	public final static String DIAMETRO_REDE_ESGOTO = "diametroRedeEsgoto";

	public final static String DIAMETRO_RAMAL_ESGOTO = "diametroRamalEsgoto";

	public final static String MATERIAL_REDE_AGUA = "materialRedeAgua";

	public final static String MATERIAL_RAMAL_AGUA = "materialRamalAgua";

	public final static String MATERIAL_CAVALETE_AGUA = "materialCavaleteAgua";

	public final static String MATERIAL_REDE_ESGOTO = "materialRedeEsgoto";

	public final static String MATERIAL_RAMAL_ESGOTO = "materialRamalEsgoto";

	public final static String CAUSA_VAZAMENTO = "causaVazamento";

	public final static String AGENTE_EXTERNO = "agenteExterno";

	public final static String OS_SELETIVA_COMANDO_ID = "osSeletivaComando.id";

	public final static String ORDEMSERVICOUNIDADES = "ordemServicoUnidades";

	public final static String IMOVEL_LOGRADOURO_CEP = "imovel.logradouroCep";

	public final static String IMOVEL_LOGRADOURO_CEP_CEP = "imovel.logradouroCep.cep";

	public final static String IMOVEL_LOGRADOURO_BAIRRO = "imovel.logradouroBairro";

	public final static String IMOVEL_LOGRADOURO_BAIRRO_BAIRRO = "imovel.logradouroBairro.bairro";

	public final static String IMOVEL_LOGRADOURO_BAIRRO_MUNICIPIO = "imovel.logradouroBairro.bairro.municipio";

	public final static String IMOVEL_LOGRADOURO_BAIRRO_MUNICIPIO_UNIDADE_FEDERACAO = "imovel.logradouroBairro.bairro.municipio.unidadeFederacao";

	public final static String IMOVEL_LOGRADOURO_BAIRRO_LOGRADOURO = "imovel.logradouroBairro.logradouro";

	public final static String IMOVEL_LOGRADOURO_BAIRRO_LOGRADOURO_TIPO = "imovel.logradouroBairro.logradouro.logradouroTipo";

	public final static String IMOVEL_LOGRADOURO_BAIRRO_LOGRADOURO_TITULO = "imovel.logradouroBairro.logradouro.logradouroTitulo";

	public final static String IMOVEL_CLIENTE_IMOVEIS = "imovel.clienteImoveis";

}
