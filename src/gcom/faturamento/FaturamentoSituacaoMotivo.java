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

package gcom.faturamento;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class FaturamentoSituacaoMotivo
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/**
	 * @since 14/06/2012
	 */
	private String codigoConstante;

	// --CONSTANTES

	public static Integer POTENCIAL;

	public static Integer FACTIVEL;

	public static Integer LIGADO;

	public static Integer EM_FISCALIZACAO;

	public static Integer CORTADO;

	public static Integer SUPRIMIDO;

	public static Integer SUPR_PARC;

	public static Integer SUPR_PARC_PEDIDO;

	public static Integer EM_CANCELAMENTO;

	public static Integer CORTADO_PEDIDO;

	public static Integer SUPRIMIDO_DEFINITIVO;

	public static Integer VIRTUAL;

	public static Integer CORT_MED_INDIVIDUAL;
	
	/**
	 * Description of the Field
	 */
	public final static Integer BENEFICIO_TARIFA_SOCIAL = Integer.valueOf("9");

	/** full constructor */
	public FaturamentoSituacaoMotivo(String descricao, Short indicadorUso, Date ultimaAlteracao) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/** default constructor */
	public FaturamentoSituacaoMotivo() {

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

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroFaturamentoSituacaoMotivo filtroFaturamentoSituacaoMotivo = new FiltroFaturamentoSituacaoMotivo();
		filtroFaturamentoSituacaoMotivo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoMotivo.ID, this.getId()));
		return filtroFaturamentoSituacaoMotivo;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execu��o. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descri��es diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante n�o seja utilizada por um determinado cliente ser� atribu�do -1 ao
	 * seu valor.
	 * 
	 * @author �talo Ant�nio Nunes de Almeida
	 * @date 14/06/2012
	 */
	public static enum FaturamentoSituacaoMotivoEnum {

		POTENCIAL, FACTIVEL, LIGADO, EM_FISCALIZACAO, CORTADO, SUPRIMIDO, SUPR_PARC, SUPR_PARC_PEDIDO, EM_CANCELAMENTO, CORTADO_A_PEDIDO,
		SUPRIMIDO_DEFINITIVO, VIRTUAL, CORT_MED_INDIVIDUAL;

		private Integer id = -1;

		private FaturamentoSituacaoMotivoEnum() {

			try{
				FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								FaturamentoSituacaoMotivo.class);

				if(faturamentoSituacaoMotivo != null){

					id = faturamentoSituacaoMotivo.getId();
				}
			}catch(ErroRepositorioException e){

				e.printStackTrace();
				throw new SistemaException(e, e.getMessage());
			}
		}

		public Integer getId(){

			return id;
		}
	}

	/**
	 * @author �talo Ant�nio Nunes de Almeida
	 * @date 14/06/2012
	 */
	public static void inicializarConstantes(){

		POTENCIAL = FaturamentoSituacaoMotivoEnum.POTENCIAL.getId();
		FACTIVEL = FaturamentoSituacaoMotivoEnum.FACTIVEL.getId();
		LIGADO = FaturamentoSituacaoMotivoEnum.LIGADO.getId();
		EM_FISCALIZACAO = FaturamentoSituacaoMotivoEnum.EM_FISCALIZACAO.getId();
		CORTADO = FaturamentoSituacaoMotivoEnum.CORTADO.getId();
		SUPRIMIDO = FaturamentoSituacaoMotivoEnum.SUPRIMIDO.getId();
		SUPR_PARC = FaturamentoSituacaoMotivoEnum.SUPR_PARC.getId();
		SUPR_PARC_PEDIDO = FaturamentoSituacaoMotivoEnum.SUPR_PARC_PEDIDO.getId();
		EM_CANCELAMENTO = FaturamentoSituacaoMotivoEnum.EM_CANCELAMENTO.getId();
		CORTADO_PEDIDO = FaturamentoSituacaoMotivoEnum.CORTADO_A_PEDIDO.getId();
		SUPRIMIDO_DEFINITIVO = FaturamentoSituacaoMotivoEnum.SUPRIMIDO_DEFINITIVO.getId();
		VIRTUAL = FaturamentoSituacaoMotivoEnum.VIRTUAL.getId();
		CORT_MED_INDIVIDUAL = FaturamentoSituacaoMotivoEnum.CORT_MED_INDIVIDUAL.getId();
	}

}
