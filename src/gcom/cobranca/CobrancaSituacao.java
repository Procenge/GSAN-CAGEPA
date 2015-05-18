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
 * Vitor Hora
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

package gcom.cobranca;

import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class CobrancaSituacao
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

	/** nullable persistent field */
	private Short indicadorExigenciaAdvogado;

	private Short indicadorBloqueioInclusao;

	private Short indicadorBloqueioRetirada;

	private Short indicadorInibeParcelamento;

	private ContaMotivoRevisao contaMotivoRevisao;

	private Set imovelCobrancaSituacoes;

	private String codigoConstante;


	// public final static Integer COBRANCA_BANCARIA = 23;
	//
	// public final static Integer COBRANCA_ADMINISTRATIVA = Integer.valueOf(4);
	//
	// public final static Integer CHEQUE_DEVOLVIDO = Integer.valueOf(1);
	//
	// public final static Integer CARTA_ENVIADA_A_SERASA = Integer.valueOf(14);
	//
	// public final static Integer CARTA_ENVIADA_AO_SPC_SP = Integer.valueOf(13);
	//
	// public final static Integer CARTA_ENVIADA_AO_SPC_BRASIL = Integer.valueOf(16);
	//
	// public final static Integer EM_ANALISE_PARA_NEGATIVACAO_SERASA = Integer.valueOf(15);
	//
	// public final static Integer EM_ANALISE_PARA_NEGATIVACAO_SPC_SP = Integer.valueOf(18);
	//
	// public final static Integer EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL = Integer.valueOf(19);
	//
	// public final static Integer NEGATIVADO_AUTOMATICAMENTE_NA_SERASA = Integer.valueOf(20);
	//
	// public final static Integer NEGATIVADO_AUTOMATICAMENTE_NO_SPC_SP = Integer.valueOf(21);
	//
	// public final static Integer NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BRASIL = Integer.valueOf(22);
	//
	// public final static Integer NEGATIVADO_LEGADO = Integer.valueOf(24);
	//
	// public final static Integer EM_COBRANCA_JUDICIAL = Integer.valueOf(5);

	public static Integer COBRANCA_BANCARIA;

	public static Integer COBRANCA_ADMINISTRATIVA;

	public static Integer CHEQUE_DEVOLVIDO;

	public static Integer CARTA_ENVIADA_A_SERASA;

	public static Integer CARTA_ENVIADA_AO_SPC_SP;

	public static Integer CARTA_ENVIADA_AO_SPC_BRASIL;

	public static Integer CARTA_ENVIADA_AO_SPC_BOA_VISTA;

	public static Integer EM_ANALISE_PARA_NEGATIVACAO_SERASA;

	public static Integer EM_ANALISE_PARA_NEGATIVACAO_SPC_SP;

	public static Integer EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA;

	public static Integer EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL;



	public static Integer NEGATIVADO_AUTOMATICAMENTE_NA_SERASA;

	public static Integer NEGATIVADO_AUTOMATICAMENTE_NO_SPC_SP;

	public static Integer NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BRASIL;

	public static Integer NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BOA_VISTA;



	public static Integer NEGATIVADO_LEGADO;

	public static Integer EM_COBRANCA_JUDICIAL;

	public static Integer COBRANCA_EXTRA_JUDICIAL;

	public static Integer COBRANCA_JUDICIAL_DEBITO_EM_ANALISE;

	public static Integer EXECUCAO_FISCAL;

	/** full constructor */
	public CobrancaSituacao(String descricao, Short indicadorUso, Date ultimaAlteracao) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/** default constructor */
	public CobrancaSituacao() {

	}

	public CobrancaSituacao(Integer id) {

		this.id = id;
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

	public ContaMotivoRevisao getContaMotivoRevisao(){

		return contaMotivoRevisao;
	}

	public void setContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao){

		this.contaMotivoRevisao = contaMotivoRevisao;
	}

	/**
	 * @return the imovelCobrancaSituacoes
	 */
	public Set getImovelCobrancaSituacoes(){

		return imovelCobrancaSituacoes;
	}

	/**
	 * @param imovelCobrancaSituacoes
	 *            the imovelCobrancaSituacoes to set
	 */
	public void setImovelCobrancaSituacoes(Set imovelCobrancaSituacoes){

		this.imovelCobrancaSituacoes = imovelCobrancaSituacoes;
	}

	public Short getIndicadorExigenciaAdvogado(){

		return indicadorExigenciaAdvogado;
	}

	public void setIndicadorExigenciaAdvogado(Short indicadorExigenciaAdvogado){

		this.indicadorExigenciaAdvogado = indicadorExigenciaAdvogado;
	}

	public Short getIndicadorBloqueioInclusao(){

		return indicadorBloqueioInclusao;
	}

	public void setIndicadorBloqueioInclusao(Short indicadorBloqueioInclusao){

		this.indicadorBloqueioInclusao = indicadorBloqueioInclusao;
	}

	public Short getIndicadorBloqueioRetirada(){

		return indicadorBloqueioRetirada;
	}

	public void setIndicadorBloqueioRetirada(Short indicadorBloqueioRetirada){

		this.indicadorBloqueioRetirada = indicadorBloqueioRetirada;
	}

	public Short getIndicadorInibeParcelamento(){

		return indicadorInibeParcelamento;
	}

	public void setIndicadorInibeParcelamento(Short indicadorInibeParcelamento){

		this.indicadorInibeParcelamento = indicadorInibeParcelamento;
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

		FiltroCobrancaSituacao filtroCobrancaSituacao = new FiltroCobrancaSituacao();

		filtroCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaSituacao.ID, this.getId()));
		filtroCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaSituacao.CONTA_MOTIVO_REVISAO);
		return filtroCobrancaSituacao;
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

		return getId() + " - " + getDescricao();
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Yara
	 * @date 07/10/2013
	 */
	public static enum CobrancaSituacaoEnum {

		COBRANCA_BANCARIA, COBRANCA_ADMINISTRATIVA, CHEQUE_DEVOLVIDO, CARTA_ENVIADA_A_SERASA, CARTA_ENVIADA_AO_SPC_SP,
		CARTA_ENVIADA_AO_SPC_BRASIL, EM_ANALISE_PARA_NEGATIVACAO_SERASA, EM_ANALISE_PARA_NEGATIVACAO_SPC_SP,
		EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA,
		EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL, NEGATIVADO_AUTOMATICAMENTE_NA_SERASA, NEGATIVADO_AUTOMATICAMENTE_NO_SPC_SP,
		NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BRASIL, NEGATIVADO_LEGADO, EM_COBRANCA_JUDICIAL, COBRANCA_EXTRA_JUDICIAL,
 COBRANCA_JUDICIAL_DEBITO_EM_ANALISE, EXECUCAO_FISCAL, CARTA_ENVIADA_AO_SPC_BOA_VISTA,
		NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BOA_VISTA;

		private Integer id = -1;

		private CobrancaSituacaoEnum() {

			try{
				CobrancaSituacao cobrancaSituacao = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(), CobrancaSituacao.class);

				if(cobrancaSituacao != null){

					id = cobrancaSituacao.getId();
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
	 * @author Yara
	 * @date 07/10/2013
	 */
	public static void inicializarConstantes(){

		COBRANCA_BANCARIA = CobrancaSituacaoEnum.COBRANCA_BANCARIA.getId();
		COBRANCA_ADMINISTRATIVA = CobrancaSituacaoEnum.COBRANCA_ADMINISTRATIVA.getId();
		CHEQUE_DEVOLVIDO = CobrancaSituacaoEnum.CHEQUE_DEVOLVIDO.getId();
		CARTA_ENVIADA_A_SERASA = CobrancaSituacaoEnum.CARTA_ENVIADA_A_SERASA.getId();
		CARTA_ENVIADA_AO_SPC_SP = CobrancaSituacaoEnum.CARTA_ENVIADA_AO_SPC_SP.getId();
		CARTA_ENVIADA_AO_SPC_BRASIL = CobrancaSituacaoEnum.CARTA_ENVIADA_AO_SPC_BRASIL.getId();
		EM_ANALISE_PARA_NEGATIVACAO_SERASA = CobrancaSituacaoEnum.EM_ANALISE_PARA_NEGATIVACAO_SERASA.getId();
		EM_ANALISE_PARA_NEGATIVACAO_SPC_SP = CobrancaSituacaoEnum.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP.getId();
		EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA = CobrancaSituacaoEnum.EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA.getId();
		EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL = CobrancaSituacaoEnum.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL.getId();
		NEGATIVADO_AUTOMATICAMENTE_NA_SERASA = CobrancaSituacaoEnum.NEGATIVADO_AUTOMATICAMENTE_NA_SERASA.getId();
		NEGATIVADO_AUTOMATICAMENTE_NO_SPC_SP = CobrancaSituacaoEnum.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_SP.getId();
		NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BRASIL = CobrancaSituacaoEnum.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BRASIL.getId();
		NEGATIVADO_LEGADO = CobrancaSituacaoEnum.NEGATIVADO_LEGADO.getId();
		EM_COBRANCA_JUDICIAL = CobrancaSituacaoEnum.EM_COBRANCA_JUDICIAL.getId();
		COBRANCA_EXTRA_JUDICIAL = CobrancaSituacaoEnum.COBRANCA_EXTRA_JUDICIAL.getId();
		COBRANCA_JUDICIAL_DEBITO_EM_ANALISE = CobrancaSituacaoEnum.COBRANCA_JUDICIAL_DEBITO_EM_ANALISE.getId();
		EXECUCAO_FISCAL = CobrancaSituacaoEnum.EXECUCAO_FISCAL.getId();
		CARTA_ENVIADA_AO_SPC_BOA_VISTA = CobrancaSituacaoEnum.CARTA_ENVIADA_AO_SPC_BOA_VISTA.getId();
		EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA = CobrancaSituacaoEnum.EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA.getId();
		NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BOA_VISTA = CobrancaSituacaoEnum.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BOA_VISTA.getId();

	}

}
