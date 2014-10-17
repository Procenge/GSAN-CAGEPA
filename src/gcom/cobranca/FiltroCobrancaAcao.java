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

package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author Pedro Alexandre
 * @created 08 de Fevereiro de 2006
 * @author eduardo henrique
 * @date 26/08/2008
 *       Altera��es no [UC0644] para a v0.04
 */

public class FiltroCobrancaAcao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCobrancaAcao object
	 */
	public FiltroCobrancaAcao() {

	}

	/**
	 * Constructor for the FiltroCobrancaAcao object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroCobrancaAcao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String COBRANCA_ACAO_PRECEDENTE_ID = "cobrancaAcaoPredecessora.id";

	public final static String COBRANCA_ACAO_PRECEDENTE = "cobrancaAcaoPredecessora";

	/**
	 * Description of the Field
	 */
	public final static String DOCUMENTO_TIPO = "documentoTipo";

	public final static String NEGATIVADOR = "negativador";

	public final static String ACAO_COBRANCA_EFEITO = "acaoCobrancaEfeito";

	public final static String COBRANCA_CRITERIO = "cobrancaCriterio";

	/**
	 * Description of the Field
	 */
	public final static String SERVICO_TIPO = "servicoTipo";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricaoCobrancaAcao";

	public final static String ORDEM_REALIZACAO = "ordemRealizacao";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String INDICADOR_CRONOGRAMA = "indicadorCronograma";

	public final static String INDICADOR_OBRIGATORIEDADE = "indicadorObrigatoriedade";

	public final static String INDICADOR_REPETICAO = "indicadorRepeticao";

	public final static String INDICADOR_SUSPENSAO_ABASTECIMENTO = "indicadorSuspensaoAbastecimento";

	public final static String NUMERO_DIAS_VALIDADE = "numeroDiasValidade";

	public final static String NUMERO_DIAS_MINIMO_ACAO_PRECEDENTE = "numeroDiasMinimoAcaoPrecedente";

	public final static String INDICADOR_COBRANCA_DEB_A_COBRAR = "indicadorCobrancaDebACobrar";

	public final static String INDICADOR_GERACAO_TAXA = "indicadorGeracaoTaxa";

	public final static String INDICADOR_ACRESCIMO_IMPONTUALIDADE = "indicadorAcrescimoImpontualidade";

	public final static String LIGACAO_ESGOTO_SITUACAO_ID = "ligacaoEsgotoSituacao.id";

	public final static String LIGACAO_ESGOTO_SITUACAO = "ligacaoEsgotoSituacao";

	public final static String LIGACAO_AGUA_SITUACAO_ID = "ligacaoAguaSituacao.id";

	public final static String LIGACAO_AGUA_SITUACAO = "ligacaoAguaSituacao";

	public final static String DOCUMENTO_TIPO_ID = "documentoTipo.id";

	public final static String COBRANCAO_CRITERIO_ID = "cobrancaCriterio.id";

	public final static String INDICADOR_BOLETIM = "indicadorBoletim";

	public final static String INDICADOR_DEBITO = "indicadorDebito";

	public final static String SERVICO_TIPO_ID_ACAO_COBRANCA = "servicoTipo.id";

	public final static String PRIMEIRA_RESOLUCAO_DIRETORIA = "primeiraResolucaoDiretoria";

	public final static String SEGUNDA_RESOLUCAO_DIRETORIA = "segundaResolucaoDiretoria";

	public final static String TERCEIRA_RESOLUCAO_DIRETORIA = "terceiraResolucaoDiretoria";

	public final static String COBRANCA_ESTAGIO_ID = "cobrancaEstagio.id";

	public final static String PRIMEIRA_RESOLUCAO_DIRETORIA_ID = "primeiraResolucaoDiretoria.id";

	public final static String SEGUNDA_RESOLUCAO_DIRETORIA_ID = "segundaResolucaoDiretoria.id";

	public final static String TERCEIRA_RESOLUCAO_DIRETORIA_ID = "terceiraResolucaoDiretoria.id";

	public final static String INDICADOR_CONSIDERA_CREDITO_REALIZAR = "indicadorConsideraCreditoRealizar";

	public final static String INDICADOR_CONSIDERA_GUIA_PAGAMENTO = "indicadorConsideraGuiaPagamento";

	public final static String INDICADOR_ENTREGA_DOCUMENTO = "indicadorEntregaDocumento";

	public final static String QTD_DIAS_REALIZACAO = "qtdDiasRealizacao";

}
