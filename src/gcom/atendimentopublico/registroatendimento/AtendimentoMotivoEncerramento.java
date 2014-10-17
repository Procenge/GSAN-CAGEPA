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

package gcom.atendimentopublico.registroatendimento;

import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AtendimentoMotivoEncerramento
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private String descricaoAbreviada;

	/** persistent field */
	private short indicadorUso;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private short indicadorExecucao;

	/** persistent field */
	private short indicadorDuplicidade;

	private String codigoConstante;

	// indicadores

	public final static Short INDICADOR_DUPLICIDADE_ATIVO = new Short("1");

	public final static Short INDICADOR_DUPLICIDADE_INATIVO = new Short("2");

	public final static short INDICADOR_EXECUCAO_SIM = 1;

	public final static short INDICADOR_EXECUCAO_NAO = 2;

	// constantes

	public static Integer CANCELADO_POR_DERCURSO_DE_PRAZO;

	public static Integer CONCLUSAO_SERVICO;

	public static Integer ENCERRAMENTO_AUTOMATICO;

	public static Integer ENCERRAMENTO_DEBITO_PAGO;

	public static Integer PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_NO_VENCIMENTO;

	public static Integer PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_APOS_VENCIMENTO;

	public static Integer PRIMEIRA_PARCELA_NAO_PAGA;

	public static Integer TARIFA_SOCIAL_IMPLANTADA;

	public static Integer CANCELAMENTO;

	/** full constructor */
	public AtendimentoMotivoEncerramento(String descricao, String descricaoAbreviada, short indicadorUso, Date ultimaAlteracao,
											short indicadorExecucao, short indicadorDuplicidade) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorExecucao = indicadorExecucao;
		this.indicadorDuplicidade = indicadorDuplicidade;
	}

	/** default constructor */
	public AtendimentoMotivoEncerramento() {

	}

	/** minimal constructor */
	public AtendimentoMotivoEncerramento(short indicadorUso, Date ultimaAlteracao, short indicadorExecucao) {

		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorExecucao = indicadorExecucao;
	}

	public short getIndicadorDuplicidade(){

		return indicadorDuplicidade;
	}

	public void setIndicadorDuplicidade(short indicadorDuplicidade){

		this.indicadorDuplicidade = indicadorDuplicidade;
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

	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public short getIndicadorExecucao(){

		return this.indicadorExecucao;
	}

	public void setIndicadorExecucao(short indicadorExecucao){

		this.indicadorExecucao = indicadorExecucao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	/**
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Ítalo Almeida
	 * @date 18/06/2012
	 */
	public static void inicializarConstantes(){

		CANCELADO_POR_DERCURSO_DE_PRAZO = AtendimentoMotivoEncerramentoEnum.CANCELADO_POR_DERCURSO_DE_PRAZO.getId();

		CONCLUSAO_SERVICO = AtendimentoMotivoEncerramentoEnum.CONCLUSAO_SERVICO.getId();

		ENCERRAMENTO_AUTOMATICO = AtendimentoMotivoEncerramentoEnum.ENCERRAMENTO_AUTOMATICO.getId();

		ENCERRAMENTO_DEBITO_PAGO = AtendimentoMotivoEncerramentoEnum.ENCERRAMENTO_DEBITO_PAGO.getId();

		PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_NO_VENCIMENTO = AtendimentoMotivoEncerramentoEnum.PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_NO_VENCIMENTO
						.getId();

		PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_APOS_VENCIMENTO = AtendimentoMotivoEncerramentoEnum.PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_APOS_VENCIMENTO
						.getId();

		PRIMEIRA_PARCELA_NAO_PAGA = AtendimentoMotivoEncerramentoEnum.PRIMEIRA_PARCELA_NAO_PAGA.getId();

		TARIFA_SOCIAL_IMPLANTADA = AtendimentoMotivoEncerramentoEnum.TARIFA_SOCIAL_IMPLANTADA.getId();

		CANCELAMENTO = AtendimentoMotivoEncerramentoEnum.CANCELAMENTO.getId();

	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Ítalo Almeida
	 * @date 18/06/2012
	 */
	public static enum AtendimentoMotivoEncerramentoEnum {
		CANCELADO_POR_DERCURSO_DE_PRAZO, CONCLUSAO_SERVICO, ENCERRAMENTO_AUTOMATICO, ENCERRAMENTO_DEBITO_PAGO,
		PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_NO_VENCIMENTO, PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_APOS_VENCIMENTO, PRIMEIRA_PARCELA_NAO_PAGA,
		TARIFA_SOCIAL_IMPLANTADA, CANCELAMENTO;

		private Integer id = -1;

		private AtendimentoMotivoEncerramentoEnum() {

			try{
				AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								AtendimentoMotivoEncerramento.class);

				if(atendimentoMotivoEncerramento != null){
					id = atendimentoMotivoEncerramento.getId();
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

}
