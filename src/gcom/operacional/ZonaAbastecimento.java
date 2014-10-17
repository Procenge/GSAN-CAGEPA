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

package gcom.operacional;

import gcom.cadastro.localidade.Localidade;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class ZonaAbastecimento
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR = 1509;

	public static final int ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR = 1511;

	public static final int ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER = 16524;

	/**
	 * @since 15/05/2007
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private Integer codigo;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private String descricao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private String descricaoAbreviada;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private Short indicadorUso;

	/** persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroZonaAbastecimento.SISTEMA_ABASTECIMENTO, funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private SistemaAbastecimento sistemaAbastecimento;

	@ControleAlteracao(value = FiltroZonaAbastecimento.DISTRITO_OPERACIONAL, funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private DistritoOperacional distritoOperacional;

	@ControleAlteracao(value = FiltroZonaAbastecimento.LOCALIDADE, funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private Localidade localidade;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private BigDecimal faixaPressaoInferior;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ZONA_ABASTECIMENTO_INSERIR, ATRIBUTOS_ZONA_ABASTECIMENTO_ATUALIZAR, ATRIBUTOS_ZONA_ABASTECIMENTO_REMOVER})
	private BigDecimal faixaPressaoSuperior;

	/** full constructor */
	public ZonaAbastecimento(String descricao, String descricaoAbreviada, short indicadorUso, Date ultimaAlteracao) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/** default constructor */
	public ZonaAbastecimento() {

	}

	/** minimal constructor */
	public ZonaAbastecimento(String descricao, short indicadorUso, Date ultimaAlteracao) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
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

	/**
	 * @return the distritoOperacional
	 */
	public DistritoOperacional getDistritoOperacional(){

		return distritoOperacional;
	}

	/**
	 * @param distritoOperacional
	 *            the distritoOperacional to set
	 */
	public void setDistritoOperacional(DistritoOperacional distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	/**
	 * @return the localidade
	 */
	public Localidade getLocalidade(){

		return localidade;
	}

	/**
	 * @param localidade
	 *            the localidade to set
	 */
	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	/**
	 * @return the faixaPressaoInferior
	 */
	public BigDecimal getFaixaPressaoInferior(){

		return faixaPressaoInferior;
	}

	/**
	 * @param faixaPressaoInferior
	 *            the faixaPressaoInferior to set
	 */
	public void setFaixaPressaoInferior(BigDecimal faixaPressaoInferior){

		this.faixaPressaoInferior = faixaPressaoInferior;
	}

	/**
	 * @return the faixaPressaoSuperior
	 */
	public BigDecimal getFaixaPressaoSuperior(){

		return faixaPressaoSuperior;
	}

	/**
	 * @param faixaPressaoSuperior
	 *            the faixaPressaoSuperior to set
	 */
	public void setFaixaPressaoSuperior(BigDecimal faixaPressaoSuperior){

		this.faixaPressaoSuperior = faixaPressaoSuperior;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Integer getCodigo(){

		return codigo;
	}

	public void setCodigo(Integer codigo){

		this.codigo = codigo;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();
		filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.SISTEMA_ABASTECIMENTO);
		filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL);
		filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.LOCALIDADE);
		filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.ID, this.getId()));

		return filtroZonaAbastecimento;
	}

	/**
	 * @param sistemaAbastecimento
	 *            the sistemaAbastecimento to set
	 */
	public void setSistemaAbastecimento(SistemaAbastecimento sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	/**
	 * @return the sistemaAbastecimento
	 */
	public SistemaAbastecimento getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public String getDescricaoComCodigo(){

		String descricaoComCodigo = null;
		if(this.getCodigo() != null){
			if(this.getCodigo().compareTo(10) == -1){
				descricaoComCodigo = "000" + getCodigo() + " - " + getDescricao();
			}else if(this.getId().compareTo(100) == -1){
				descricaoComCodigo = "00" + getCodigo() + " - " + getDescricao();
			}else if(this.getId().compareTo(1000) == -1){
				descricaoComCodigo = "0" + getCodigo() + " - " + getDescricao();
			}else{
				descricaoComCodigo = getCodigo() + " - " + getDescricao();
			}
		}else{
			descricaoComCodigo = getDescricao();
		}

		return descricaoComCodigo;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}
}
