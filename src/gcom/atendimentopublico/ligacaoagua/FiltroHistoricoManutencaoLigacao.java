/**
 * 
 */
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

package gcom.atendimentopublico.ligacaoagua;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro do Histórico de Manutenção de Ligação
 * 
 * @author Luciano Galvão
 * @created 14/09/2012
 */
public class FiltroHistoricoManutencaoLigacao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroHistoricoManutencaoLigacao object
	 */
	public FiltroHistoricoManutencaoLigacao() {

	}

	/**
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroHistoricoManutencaoLigacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */

	public final static String ID_ASSOCIADO = "idAssociado";

	public final static String CODIGO_SITUACAO_OS = "codigoSituacaoOS";

	public final static String NUMERO_LEITURA_EXECUCAO = "numeroLeituraExecucao";

	public final static String VALOR_DEBITO = "valorDebito";

	public final static String DATA_SITUACAO_DEBITO = "dataSituacaoDebito";

	public final static String DATA_SITUACAO_DOCUMENTO = "dataSituacaoDocumento";

	public final static String DATA_EMISSAO = "dataEmissao";

	public final static String DATA_INCLUSAO = "dataInclusao";

	public final static String CODIGO_SETOR_COMERCIAL = "codigoSetorComercial";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String LOCALIDADE = "localidade";

	public final static String LOCALIDADE_id = "localidade.id";

	public final static String SETOR_COMERCIAL = "setorComercial";

	public final static String SETOR_COMERCIAL_ID = "setorComercial.id";

	public final static String IMOVEL_PERFIL = "imovelPerfil";

	public final static String IMOVEL_PERFIL_ID = "imovelPerfil.id";

	public final static String ORDEM_SERVICO = "ordemServico";

	public final static String ORDEM_SERVICO_ID = "ordemServico.id";

	public final static String COBRANCA_DOCUMENTO_ORIGEM_SERVICO = "cobrancaDocumentoOrigemServico";

	public final static String COBRANCA_DOCUMENTO_ORIGEM_SERVICO_ID = "cobrancaDocumentoOrigemServico.id";

	public final static String REGISTRO_ATENDIMENTO_ORIGEM_SERVICO = "registroAtendimentoOrigemServico";

	public final static String REGISTRO_ATENDIMENTO_ORIGEM_SERVICO_ID = "registroAtendimentoOrigemServico.id";

	public final static String SERVICO_TIPO = "servicoTipo";

	public final static String SERVICO_TIPO_ID = "servicoTipo.id";

	public final static String ATENDIMENTO_MOTIVO_ENCERRAMENTO = "atendimentoMotivoEncerramento";

	public final static String ATENDIMENTO_MOTIVO_ENCERRAMENTO_ID = "atendimentoMotivoEncerramento.id";

	public final static String USUARIO_GERACAO = "usuarioGeracao";

	public final static String USUARIO_GERACAO_ID = "usuarioGeracao.id";

	public final static String USUARIO_ENCERRAMENTO = "usuarioEncerramento";

	public final static String USUARIO_ENCERRAMENTO_ID = "usuarioEncerramento.id";

	public final static String USUARIO_EXECUCAO = "usuarioExecucao";

	public final static String USUARIO_EXECUCAO_ID = "usuarioExecucao.id";

	public final static String CORTE_TIPO = "corteTipo";

	public final static String CORTE_TIPO_ID = "corteTipo.id";

	public final static String COBRANCA_DOCUMENTO = "cobrancaDocumento";

	public final static String COBRANCA_DOCUMENTO_ID = "cobrancaDocumento.id";

	public final static String DOCUMENTO_EMISSAO_FORMA = "documentoEmissaoForma";

	public final static String DOCUMENTO_EMISSAO_FORMA_ID = "documentoEmissaoForma.id";

	public final static String DOCUMENTO_TIPO = "documentoTipo";

	public final static String DOCUMENTO_TIPO_ID = "documentoTipo.id";

	public final static String COBRANCA_DEBITO_SITUACAO = "cobrancaDebitoSituacao";

	public final static String COBRANCA_DEBITO_SITUACAO_ID = "cobrancaDebitoSituacao.id";

	public final static String COBRANCA_ACAO_SITUACAO = "cobrancaAcaoSituacao";

	public final static String COBRANCA_ACAO_SITUACAO_ID = "cobrancaAcaoSituacao.id";

	public final static String MOTIVO_NAO_ENTREGA_DOCUMENTO = "motivoNaoEntregaDocumento";

	public final static String MOTIVO_NAO_ENTREGA_DOCUMENTO_ID = "motivoNaoEntregaDocumento.id";

	public final static String FISCALIZACAO_SITUACAO = "fiscalizacaoSituacao";

	public final static String FISCALIZACAO_SITUACAO_ID = "fiscalizacaoSituacao.id";

}
