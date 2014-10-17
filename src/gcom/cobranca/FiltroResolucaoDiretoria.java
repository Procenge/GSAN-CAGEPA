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

/**
 * [UC0214] - Efetuar Parcelamento de D�bitos
 * Filtro para Resolu��o de Diretoria
 * 
 * @author Roberta Costa
 * @created 17/02/2006
 */
public class FiltroResolucaoDiretoria
				extends Filtro {

	private static final long serialVersionUID = 1L;

	/**
	 * Description of the Field
	 */
	public final static String CODIGO = "id";

	/**
	 * Description of the Field
	 */
	public final static String NUMERO = "numeroResolucaoDiretoria";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricaoAssunto";

	/**
	 * Description of the Field
	 */
	public final static String DATA_VIGENCIA_INICIO = "dataVigenciaInicio";

	/**
	 * Description of the Field
	 */
	public final static String DATA_VIGENCIA_FIM = "dataVigenciaFim";

	/**
	 * Description of the Field
	 */
	public final static String RESOLUCAO_DIRETORIA_LAYOUT = "resolucaoDiretoriaLayout";

	/**
	 * Description of the Field
	 * OBS: N�o reflete a base, serve apenas para configurar o tipo de pesquisa referente ao campo
	 * assunto/descri��o
	 */
	public final static String TIPO_PESQUISA = "tipoPesquisa";

	/**
	 * Description of the Field
	 */
	public final static String RESOLUCAO_DIRETORIA_GRUPOS = "resolucaoDiretoriaGrupos";

	public final static String RESOLUCAO_DIRETORIA_GRUPO = "resolucaoDiretoriaGrupos.id";

	public final static String RESOLUCAO_DIRETORIA_GRUPO_ID = "resolucaoDiretoriaGrupos.grupo.id";

	public final static String INDICADOR_PARCELAMENTO_UNICO = "indicadorParcelamentoUnico";

	public final static String INDICADOR_UTILIZACAO_LIVRE = "indicadorUtilizacaoLivre";

	public final static String INDICADOR_DESCONTO_SANCOES = "indicadorDescontoSancoes";

	public final static String INDICADOR_EMISSAO_ASSUNTO_CONTA = "indicadorEmissaoAssuntoConta";

	public final static String INDICADOR_TRATA_MEDIA_ATUALIZACAO_MONETARIA = "indicadorTrataMediaAtualizacaoMonetaria";

	public final static String INDICADOR_COBRAR_DESCONTOS_ARRASTO = "indicadorCobrarDescontosArrasto";

	public final static String INDICADOR_ARRASTO = "indicadorArrasto";

	public final static String PARCELAMENTO_SITUACAO_ESPECIAL = "parcelamentosSituacaoEspecial";

	public final static String PARCELAMENTO_SITUACAO_ESPECIAL_LOCALIDADE = "parcelamentosSituacaoEspecial.localidade";

	public final static String PARCELAMENTO_SITUACAO_ESPECIAL_LOCALIDADE_ID = "parcelamentosSituacaoEspecial.localidade.id";

	public final static String PARCELAMENTO_SITUACAO_ESPECIAL_ANOMES_DEBITO_INICIO = "parcelamentosSituacaoEspecial.anoMesReferenciaDebitoInicio";

	public final static String PARCELAMENTO_SITUACAO_ESPECIAL_ANOMES_DEBITO_FIM = "parcelamentosSituacaoEspecial.anoMesReferenciaDebitoFim";

	public final static String RESOLUCAO_DIRETORIA_CONDICOES_PAGTO_A_VISTA = "resolucaoDiretoriaCondicoesPagtoAVista";

	public final static String RESOLUCAO_DIRETORIA_CONDICOES_VALOR_ENTRADA = "resolucaoDiretoriaCondicoesValorEntrada";

	/**
	 * Construtor da classe FiltroCategoria
	 */
	public FiltroResolucaoDiretoria() {

	}

	/**
	 * Construtor da classe FiltroCategoria
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroResolucaoDiretoria(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}
}