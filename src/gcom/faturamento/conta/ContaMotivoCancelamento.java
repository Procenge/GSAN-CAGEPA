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

package gcom.faturamento.conta;

import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ContaMotivoCancelamento
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricaoMotivoCancelamentoConta;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	public static Integer TRASFERENCIA_DE_COBRANCA;

	private String codigoConstante;

	/** nullable persistent field */
	private Short indicadorRegistroAtendimento;

	private SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao;

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	/** full constructor */
	public ContaMotivoCancelamento(String descricaoMotivoCancelamentoConta, Short indicadorUso, Date ultimaAlteracao,
									Short indicadorRegistroAtendimento, SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao) {

		this.descricaoMotivoCancelamentoConta = descricaoMotivoCancelamentoConta;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorRegistroAtendimento = indicadorRegistroAtendimento;
		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
	}

	/** default constructor */
	public ContaMotivoCancelamento() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricaoMotivoCancelamentoConta(){

		return this.descricaoMotivoCancelamentoConta;
	}

	public void setDescricaoMotivoCancelamentoConta(String descricaoMotivoCancelamentoConta){

		this.descricaoMotivoCancelamentoConta = descricaoMotivoCancelamentoConta;
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

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Filtro retornaFiltro(){

		Filtro filtro = new FiltroContaMotivoCancelamento();

		filtro.adicionarParametro(new ParametroSimples(FiltroContaMotivoCancelamento.CODIGO, this.id));
		return filtro;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getDescricaoMotivoCancelamentoConta();
	}

	/**
	 * Este m�todo foi criado para inicializar as constantes. A sua implementa��o visa utilizar a
	 * solu��o dada para casos em que haja constantes com descri��es diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Anderson Italo
	 * @date 10/01/2012
	 */
	public static void inicializarConstantes(){

		TRASFERENCIA_DE_COBRANCA = ContaMotivoCancelamentoEnum.TRASFERENCIA_DE_COBRANCA.getId();

	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execu��o. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descri��es diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante n�o seja utilizada por um determinado cliente ser� atribu�do -1 ao
	 * seu valor.
	 * 
	 * @author Leonardo Silva
	 * @date 08/08/2012
	 */
	public static enum ContaMotivoCancelamentoEnum {

		TRASFERENCIA_DE_COBRANCA;

		private Integer id = -1;

		private ContaMotivoCancelamentoEnum() {

			try{
				ContaMotivoCancelamento contaMotivoCancelamento = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								ContaMotivoCancelamento.class);

				if(contaMotivoCancelamento != null){

					id = contaMotivoCancelamento.getId();
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
	 * @return the indicadorRegistroAtendimento
	 */
	public Short getIndicadorRegistroAtendimento(){

		return indicadorRegistroAtendimento;
	}

	/**
	 * @param indicadorRegistroAtendimento
	 *            the indicadorRegistroAtendimento to set
	 */
	public void setIndicadorRegistroAtendimento(Short indicadorRegistroAtendimento){

		this.indicadorRegistroAtendimento = indicadorRegistroAtendimento;
	}

	/**
	 * @return the solicitacaoTipoEspecificacao
	 */
	public SolicitacaoTipoEspecificacao getSolicitacaoTipoEspecificacao(){

		return solicitacaoTipoEspecificacao;
	}

	/**
	 * @param solicitacaoTipoEspecificacao
	 *            the solicitacaoTipoEspecificacao to set
	 */
	public void setSolicitacaoTipoEspecificacao(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao){

		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
	}

}