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

package gcom.faturamento.conta;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ContaMotivoRevisao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricaoMotivoRevisaoConta;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	private Integer prazoValidade;

	private String codigoConstante;

	private Short indicadorInibeParcelamento;

	public static Integer RETIDA_PARA_ANALISE;

	public static Integer REVISAO_POR_COBRANCA_BANCARIA;

	public static Integer REVISAO_POR_PAGAMENTO_COMPROVADO;

	public static Integer REVISAO_AUTOMATICA_ESTOURO_CONSUMO;

	public static Integer REVISAO_AUTOMATICA_BAIXO_CONSUMO;

	public static Integer REVISAO_ENTRADA_DE_PARCELAMENTO;

	public static Integer REVISAO_SUBJUDICE;

	public static Integer REVISAO_SPC;

	public static Integer CAUCIONAMENTO;

	/** full constructor */
	public ContaMotivoRevisao(String descricaoMotivoRevisaoConta, Short indicadorUso, Date ultimaAlteracao) {

		this.descricaoMotivoRevisaoConta = descricaoMotivoRevisaoConta;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/** default constructor */
	public ContaMotivoRevisao() {

	}

	public ContaMotivoRevisao(Integer id) {

		this.id = id;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricaoMotivoRevisaoConta(){

		return this.descricaoMotivoRevisaoConta;
	}

	public void setDescricaoMotivoRevisaoConta(String descricaoMotivoRevisaoConta){

		this.descricaoMotivoRevisaoConta = descricaoMotivoRevisaoConta;
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

	public Integer getPrazoValidade(){

		return prazoValidade;
	}

	public void setPrazoValidade(Integer prazoValidade){

		this.prazoValidade = prazoValidade;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		Filtro filtro = new FiltroContaMotivoRevisao();

		filtro.adicionarParametro(new ParametroSimples(FiltroContaMotivoRevisao.ID, this.id));
		return filtro;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getDescricaoMotivoRevisaoConta();
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public Short getIndicadorInibeParcelamento(){

		return indicadorInibeParcelamento;
	}

	public void setIndicadorInibeParcelamento(Short indicadorInibeParcelamento){

		this.indicadorInibeParcelamento = indicadorInibeParcelamento;
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Yara Souza
	 * @date 04/06/2012
	 */
	public static enum ContaMotivoRevisaoEnum {

		RETIDA_PARA_ANALISE, REVISAO_POR_COBRANCA_BANCARIA, REVISAO_POR_PAGAMENTO_COMPROVADO, REVISAO_AUTOMATICA_ESTOURO_CONSUMO,
		REVISAO_ENTRADA_DE_PARCELAMENTO, REVISAO_SUBJUDICE, REVISAO_AUTOMATICA_BAIXO_CONSUMO, CAUCIONAMENTO, REVISAO_SPC;

		private Integer id = -1;

		private ContaMotivoRevisaoEnum() {

			try{
				ContaMotivoRevisao contaMotivoRevisao = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								ContaMotivoRevisao.class);

				if(contaMotivoRevisao != null){

					id = contaMotivoRevisao.getId();
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

	public static void inicializarConstantes(){

		RETIDA_PARA_ANALISE = ContaMotivoRevisaoEnum.RETIDA_PARA_ANALISE.getId();
		REVISAO_POR_COBRANCA_BANCARIA = ContaMotivoRevisaoEnum.REVISAO_POR_COBRANCA_BANCARIA.getId();
		REVISAO_POR_PAGAMENTO_COMPROVADO = ContaMotivoRevisaoEnum.REVISAO_POR_PAGAMENTO_COMPROVADO.getId();
		REVISAO_AUTOMATICA_ESTOURO_CONSUMO = ContaMotivoRevisaoEnum.REVISAO_AUTOMATICA_ESTOURO_CONSUMO.getId();
		REVISAO_ENTRADA_DE_PARCELAMENTO = ContaMotivoRevisaoEnum.REVISAO_ENTRADA_DE_PARCELAMENTO.getId();
		REVISAO_SUBJUDICE = ContaMotivoRevisaoEnum.REVISAO_SUBJUDICE.getId();
		REVISAO_AUTOMATICA_BAIXO_CONSUMO = ContaMotivoRevisaoEnum.REVISAO_AUTOMATICA_BAIXO_CONSUMO.getId();
		CAUCIONAMENTO = ContaMotivoRevisaoEnum.CAUCIONAMENTO.getId();
		REVISAO_SPC = ContaMotivoRevisaoEnum.REVISAO_SPC.getId();
	}

}
