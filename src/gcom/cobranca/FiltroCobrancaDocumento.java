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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author Pedro Alexandre
 * @created 08 de Fevereiro de 2006
 */

public class FiltroCobrancaDocumento
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCobrancaDocumento object
	 */
	public FiltroCobrancaDocumento() {

	}

	public FiltroCobrancaDocumento(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String DATA_EMISSAO = "emissao";

	public final static String DOCUMENTO_EMISSAO_FORMA = "documentoEmissaoForma";

	public final static String DOCUMENTO_EMISSAO_FORMA_ID = "documentoEmissaoForma.id";

	public final static String ATIVIDADE_CRONOGRAMA = "cobrancaAcaoAtividadeCronograma";

	public final static String ATIVIDADE_CRONOGRAMA_ID = "cobrancaAcaoAtividadeCronograma.id";

	public final static String ATIVIDADE_COMANDO = "cobrancaAcaoAtividadeComando";

	public final static String ATIVIDADE_COMANDO_ID = "cobrancaAcaoAtividadeComando.id";

	public final static String VALOR_DOCUMENTO = "valorDocumento";

	public final static String MOTIVO_NAO_ENTREGA_ID = "motivoNaoEntregaDocumento.id";

	public final static String PERFIL_IMOVEL_ID = "imovelPerfil.id";

	public final static String GERENCIA_REGIONAL = "localidade.gerenciaRegional.id";

	public final static String DOCUMENTO_TIPO_ID = "documentoTipo.id";

	public final static String LOCALIDADE_ID = "localidade.id";

	public final static String LOCALIDADE = "localidade";

	public final static String SETOR_COMERCIAL = "codigoSetorComercial";

	public final static String QUADRA_NM = "quadra.numeroQuadra";

	public final static String ID_COBRANCA_CRITERIO = "cobrancaCriterio.id";

	public final static String ID_UNIDADE_NEGOCIO = "localidade.unidadeNegocio.id";

	public final static String COBRANCA_SITUACAO_ID = "cobrancaAcaoSituacao.id";

	public final static String COBRANCA_ACAO_SITUACAO = "cobrancaAcaoSituacao";

	public final static String COBRANCA_DEBITO_SITUACAO = "cobrancaDebitoSituacao";

	public final static String COBRANCA_ACAO = "cobrancaAcao";

	public final static String ID_COBRANCA_ACAO = "cobrancaAcao.id";

	public final static String ORDEMS_SERVICO = "ordensServico";

	public final static String ORDEMS_SERVICO_ID = "ordensServico.id";

	public final static String COBRANCA_ACAO_COBRANCA_ACAO_PREDECESSORA = "cobrancaAcao.cobrancaAcaoPredecessora";

	// public final static String ORDEMS_SERVICO_ATENDIMENTO_MOTIVO_ENCERRAMENTO =
	// "ordensServico.atendimentoMotivoEncerramento";
	// public final static String ORDEMS_SERVICO_ATENDIMENTO_MOTIVO_ENCERRAMENTO_ID =
	// "ordensServico.atendimentoMotivoEncerramento.id";

	public final static String EMPRESA = "empresa";

	public final static String EMPRESA_ID = "empresa.id";

	public final static String COBRANCA_ACAO_DOCUMENTO_TIPO = "cobrancaAcao.documentoTipo";

	public final static String COBRANCA_ACAO_DOCUMENTO_TIPO_ID = "cobrancaAcao.documentoTipo.id";

	public final static String COBRANCA_ACAO_SERVICO_TIPO = "cobrancaAcao.servicoTipo";

	public final static String COBRANCA_ACAO_SERVICO_TIPO_ID = "cobrancaAcao.servicoTipo.id";

	public final static String IMOVEL_HIDROMETRO_INSTALACAO = "imovel.hidrometroInstalacaoHistorico";

	public final static String IMOVEL_HIDROMETRO_INSTALACAO_ID = "imovel.hidrometroInstalacaoHistorico.id";

	public final static String IMOVEL_LIGACAO_AGUA_SUPRESSAO_TIPO = "imovel.ligacaoAgua.supressaoTipo";

	public final static String IMOVEL_LIGACAO_AGUA_SUPRESSAO_TIPO_ID = "imovel.ligacaoAgua.supressaoTipo.id";

	public final static String IMOVEL_LIGACAO_AGUA_CORTE_TIPO = "imovel.ligacaoAgua.corteTipo";

	public final static String IMOVEL_LIGACAO_AGUA_CORTE_TIPO_ID = "imovel.ligacaoAgua.corteTipo.id";

	public final static String COBRANCA_DOCUMENTO_ITEMS = "cobrancaDocumentoItems";

	public final static String COBRANCA_DOCUMENTO_ITEMS_ID = "cobrancaDocumentoItems.id";

	public final static String COBRANCA_DOCUMENTO_ITEMS_DOCUMENTO_TIPO = "cobrancaDocumentoItems.documentoTipo";

	public final static String IMOVEL_LIGACAO_AGUA_SITUACAO = "imovel.ligacaoAguaSituacao";

	public final static String IMOVEL_LIGACAO_AGUA_SITUACAO_ID = "imovel.ligacaoAguaSituacao.id";

	public final static String IMOVEL_LIGACAO_ESGOTO_SITUACAO = "imovel.ligacaoEsgotoSituacao";

	public final static String IMOVEL_LIGACAO_ESGOTO_SITUACAO_ID = "imovel.ligacaoEsgotoSituacao.id";

	public final static String IMOVEL_LOCALIDADE = "imovel.localidade";

	public final static String IMOVEL_LOCALIDADE_ID = "imovel.localidade.id";

	public final static String IMOVEL_SETOR_COMERCIAL = "imovel.setorComercial";

	public final static String IMOVEL_SETOR_COMERCIAL_ID = "imovel.setorComercial.id";

	public final static String IMOVEL_SETOR_COMERCIAL_CODIGO = "imovel.setorComercial.codigo";

	public final static String IMOVEL_QUADRA = "imovel.quadra";

	public final static String IMOVEL_QUADRA_ID = "imovel.quadra.id";

	public final static String IMOVEL_QUADRA_NUMERO = "imovel.quadra.numeroQuadra";

	public final static String IMOVEL_LOTE = "imovel.lote";

	public final static String IMOVEL_SUBLOTE = "imovel.subLote";

	public final static String CLIENTE = "cliente";

	public final static String CLIENTE_ID = "cliente.id";

	public final static String NUMERO_SEQUENCIAL = "numeroSequenciaDocumento";

	public final static String FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ID = "faturamentoGrupoCronogramaMensal.id";

	public final static String ID_COBRANCA_ACAO_ATIVIDADE_COMANDO = "cobrancaAcaoAtividadeComando.id";

}
