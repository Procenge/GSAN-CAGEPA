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

import gcom.interceptor.ObjetoGcom;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LigacaoAguaPerfil
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private Integer indicadorUso;

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
	
	public final static Integer AGUA_LIGACAO_CLANDESTINA = Integer.valueOf("6");

	/** full constructor */
	public LigacaoAguaPerfil(String descricao, Integer indicadorUso, Date ultimaAlteracao) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/** default constructor */
	public LigacaoAguaPerfil() {

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

	public Integer getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(Integer indicadorUso){

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

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Ítalo Antônio Nunes de Almeida
	 * @date 14/06/2012
	 */
	public static enum LigacaoAguaPerfilEnum {

		POTENCIAL, FACTIVEL, LIGADO, EM_FISCALIZACAO, CORTADO, SUPRIMIDO, SUPR_PARC, SUPR_PARC_PEDIDO, EM_CANCELAMENTO, CORTADO_A_PEDIDO,
		SUPRIMIDO_DEFINITIVO, VIRTUAL, CORT_MED_INDIVIDUAL;

		private Integer id = -1;

		private LigacaoAguaPerfilEnum() {

			try{
				LigacaoAguaPerfil ligacaoAguaPerfil = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(), LigacaoAguaPerfil.class);

				if(ligacaoAguaPerfil != null){

					id = ligacaoAguaPerfil.getId();
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
	 * @author Ítalo Antônio Nunes de Almeida
	 * @date 14/06/2012
	 */
	public static void inicializarConstantes(){

		POTENCIAL = LigacaoAguaPerfilEnum.POTENCIAL.getId();
		FACTIVEL = LigacaoAguaPerfilEnum.FACTIVEL.getId();
		LIGADO = LigacaoAguaPerfilEnum.LIGADO.getId();
		EM_FISCALIZACAO = LigacaoAguaPerfilEnum.EM_FISCALIZACAO.getId();
		CORTADO = LigacaoAguaPerfilEnum.CORTADO.getId();
		SUPRIMIDO = LigacaoAguaPerfilEnum.SUPRIMIDO.getId();
		SUPR_PARC = LigacaoAguaPerfilEnum.SUPR_PARC.getId();
		SUPR_PARC_PEDIDO = LigacaoAguaPerfilEnum.SUPR_PARC_PEDIDO.getId();
		EM_CANCELAMENTO = LigacaoAguaPerfilEnum.EM_CANCELAMENTO.getId();
		CORTADO_PEDIDO = LigacaoAguaPerfilEnum.CORTADO_A_PEDIDO.getId();
		SUPRIMIDO_DEFINITIVO = LigacaoAguaPerfilEnum.SUPRIMIDO_DEFINITIVO.getId();
		VIRTUAL = LigacaoAguaPerfilEnum.VIRTUAL.getId();
		CORT_MED_INDIVIDUAL = LigacaoAguaPerfilEnum.CORT_MED_INDIVIDUAL.getId();
	}

}
