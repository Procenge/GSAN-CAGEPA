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

package gcom.atendimentopublico.ordemservico;

import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ServicoTipoSubgrupo
				implements Serializable {

	/**
	 * VALORES GSAN ADA
	 */
	private static final long serialVersionUID = 1L;

	// public final static Short CORTE = Short.valueOf("25");
	//
	// public final static Short SUPRESSAO = Short.valueOf("26");
	//
	// public final static Short FISCALIZACAO = Short.valueOf("29");
	//
	// public final static Short RELIGACAO = Short.valueOf("27");
	//
	// public final static Short VERIFICACAO_IRREGULARIDADES = Short.valueOf("30");

	/**
	 * Valores padrão GSAN PCG
	 */
	// public final static Short CORTE = Short.valueOf("1");
	// public final static Short SUPRESSAO = Short.valueOf("2");
	// public final static Short FISCALIZACAO = Short.valueOf("3");
	// public final static Short RELIGACAO = Short.valueOf("25");

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private String descricaoAbreviada;

	/** persistent field */
	private Date dataCadastramento;

	/** persistent field */
	private short indicadorUso;

	/** persistent field */
	private Date ultimaAlteracao;

	private String codigoConstante;

	// --CONSTANTES

	public static Integer LIGACAO_AGUA;

	public static Integer LIGACAO_ESGOTO;

	public static Integer INSTALACAO_HIDROMETRO;

	public static Integer SUBSTITUICAO_HIDROMETRO;

	public static Integer RETIRADA_HIDROMETRO;

	public static Integer SUPRESSAO_PARCIAL;

	public static Integer CORTE_FALTA_PAGAMENTO;

	public static Integer CORTE_INFRACAO;

	public static Integer CORTE_PEDIDO;

	public static Integer RELIGACAO;

	public static Integer DESATIVA_ESGOTO_POTENCI;

	public static Integer REATIVACAO_ESGOTO;

	public static Integer FISCALIZACAO;

	public static Integer RESTABELECIMENTO;

	public static Integer SUPRESSAO_TOTAL;

	public static Integer SUPRESSAO_DEFINITIVA;

	public static Integer RELIGACAO_INST_HIDR;

	public static Integer RELICAGAO_SUBS_HIDR;

	public static Integer NORMAL;

	public static Integer LIG_ESGOTO_CONDOMINIAL;

	public static Integer DESATIVA_ESGOTO_FACTIVE;

	public static Integer CORTE_MEDICAO_INDIVIDUAL;

	public static Integer ACERTOS_HSC_HCO;

	public static Integer RESTABELECE_INST_HIDR;

	public static Integer RESTABELECE_SUBS_HIDR;

	public static Integer AVISO_DEBITO;

	public static Integer MANUTENCAO_HIDROMETRO;

	public static Integer MANUTENCAO_FISCALIZACAO_HIDROMETRO;

	// Indicadores de Situação de Faturamento --
	public final static Short FATURAMENTO_ATIVO = Short.valueOf("1");

	public final static Short NAO_FATURAVEL = Short.valueOf("2");

	public final static Integer LIGADO_A_REVELIA = Integer.valueOf("4");

	public final static Integer LIGADO_EM_ANALISE = Integer.valueOf("4");

	/** persistent field */
	private gcom.atendimentopublico.ordemservico.ServicoTipoGrupo servicoTipoGrupo;

	/** full constructor */
	public ServicoTipoSubgrupo(String descricao, String descricaoAbreviada, Date dataCadastramento, short indicadorUso,
								Date ultimaAlteracao, gcom.atendimentopublico.ordemservico.ServicoTipoGrupo servicoTipoGrupo) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.dataCadastramento = dataCadastramento;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.servicoTipoGrupo = servicoTipoGrupo;
	}

	/** default constructor */
	public ServicoTipoSubgrupo() {

	}

	/** minimal constructor */
	public ServicoTipoSubgrupo(Date dataCadastramento, short indicadorUso, Date ultimaAlteracao,
								gcom.atendimentopublico.ordemservico.ServicoTipoGrupo servicoTipoGrupo) {

		this.dataCadastramento = dataCadastramento;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.servicoTipoGrupo = servicoTipoGrupo;
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

	public Date getDataCadastramento(){

		return this.dataCadastramento;
	}

	public void setDataCadastramento(Date dataCadastramento){

		this.dataCadastramento = dataCadastramento;
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

	public gcom.atendimentopublico.ordemservico.ServicoTipoGrupo getServicoTipoGrupo(){

		return this.servicoTipoGrupo;
	}

	public void setServicoTipoGrupo(gcom.atendimentopublico.ordemservico.ServicoTipoGrupo servicoTipoGrupo){

		this.servicoTipoGrupo = servicoTipoGrupo;
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

	public String getDescricaoParaRegistroTransacao(){

		String retorno = null;

		if(this.getId() != null && this.getDescricao() != null){
			retorno = this.getId() + " - " + this.getDescricao();
		}else{
			retorno = null;
		}

		return retorno;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public static enum ServicoTipoSubgrupoEnum {

		LIGACAO_AGUA, LIGACAO_ESGOTO, INSTALACAO_HIDROMETRO, SUBSTITUICAO_HIDROMETRO, RETIRADA_HIDROMETRO, SUPRESSAO_PARCIAL,
		CORTE_FALTA_PAGAMENTO, CORTE_INFRACAO, CORTE_PEDIDO, RELIGACAO, DESATIVA_ESGOTO_POTENCI, REATIVACAO_ESGOTO, FISCALIZACAO,
		RESTABELECIMENTO, SUPRESSAO_TOTAL, SUPRESSAO_DEFINITIVA, RELIGACAO_INST_HIDR, RELICAGAO_SUBS_HIDR, NORMAL, LIG_ESGOTO_CONDOMINIAL,
		DESATIVA_ESGOTO_FACTIVE, CORTE_MEDICAO_INDIVIDUAL, ACERTOS_HSC_HCO, RESTABELECE_INST_HIDR, RESTABELECE_SUBS_HIDR, AVISO_DEBITO,
		MANUTENCAO_HIDROMETRO, MANUTENCAO_FISCALIZACAO_HIDROMETRO;

		private Integer id = -1;

		private ServicoTipoSubgrupoEnum() {

			try{
				ServicoTipoSubgrupo servicoTipoSubgrupo = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								ServicoTipoSubgrupo.class);

				if(servicoTipoSubgrupo != null){

					id = servicoTipoSubgrupo.getId();
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

		LIGACAO_AGUA = ServicoTipoSubgrupoEnum.LIGACAO_AGUA.getId();
		LIGACAO_ESGOTO = ServicoTipoSubgrupoEnum.LIGACAO_ESGOTO.getId();
		INSTALACAO_HIDROMETRO = ServicoTipoSubgrupoEnum.INSTALACAO_HIDROMETRO.getId();
		SUBSTITUICAO_HIDROMETRO = ServicoTipoSubgrupoEnum.SUBSTITUICAO_HIDROMETRO.getId();
		RETIRADA_HIDROMETRO = ServicoTipoSubgrupoEnum.RETIRADA_HIDROMETRO.getId();
		SUPRESSAO_PARCIAL = ServicoTipoSubgrupoEnum.SUPRESSAO_PARCIAL.getId();
		CORTE_FALTA_PAGAMENTO = ServicoTipoSubgrupoEnum.CORTE_FALTA_PAGAMENTO.getId();
		CORTE_INFRACAO = ServicoTipoSubgrupoEnum.CORTE_INFRACAO.getId();
		CORTE_PEDIDO = ServicoTipoSubgrupoEnum.CORTE_PEDIDO.getId();
		RELIGACAO = ServicoTipoSubgrupoEnum.RELIGACAO.getId();
		DESATIVA_ESGOTO_POTENCI = ServicoTipoSubgrupoEnum.DESATIVA_ESGOTO_POTENCI.getId();
		REATIVACAO_ESGOTO = ServicoTipoSubgrupoEnum.REATIVACAO_ESGOTO.getId();
		FISCALIZACAO = ServicoTipoSubgrupoEnum.FISCALIZACAO.getId();
		RESTABELECIMENTO = ServicoTipoSubgrupoEnum.RESTABELECIMENTO.getId();
		SUPRESSAO_TOTAL = ServicoTipoSubgrupoEnum.SUPRESSAO_TOTAL.getId();
		SUPRESSAO_DEFINITIVA = ServicoTipoSubgrupoEnum.SUPRESSAO_DEFINITIVA.getId();
		RELIGACAO_INST_HIDR = ServicoTipoSubgrupoEnum.RELIGACAO_INST_HIDR.getId();
		RELICAGAO_SUBS_HIDR = ServicoTipoSubgrupoEnum.RELICAGAO_SUBS_HIDR.getId();
		NORMAL = ServicoTipoSubgrupoEnum.NORMAL.getId();
		LIG_ESGOTO_CONDOMINIAL = ServicoTipoSubgrupoEnum.LIG_ESGOTO_CONDOMINIAL.getId();
		DESATIVA_ESGOTO_FACTIVE = ServicoTipoSubgrupoEnum.DESATIVA_ESGOTO_FACTIVE.getId();
		CORTE_MEDICAO_INDIVIDUAL = ServicoTipoSubgrupoEnum.CORTE_MEDICAO_INDIVIDUAL.getId();
		ACERTOS_HSC_HCO = ServicoTipoSubgrupoEnum.ACERTOS_HSC_HCO.getId();
		RESTABELECE_INST_HIDR = ServicoTipoSubgrupoEnum.RESTABELECE_INST_HIDR.getId();
		RESTABELECE_SUBS_HIDR = ServicoTipoSubgrupoEnum.RESTABELECE_SUBS_HIDR.getId();
		AVISO_DEBITO = ServicoTipoSubgrupoEnum.AVISO_DEBITO.getId();
		MANUTENCAO_HIDROMETRO = ServicoTipoSubgrupoEnum.MANUTENCAO_HIDROMETRO.getId();
		MANUTENCAO_FISCALIZACAO_HIDROMETRO = ServicoTipoSubgrupoEnum.MANUTENCAO_FISCALIZACAO_HIDROMETRO.getId();
	}

}
