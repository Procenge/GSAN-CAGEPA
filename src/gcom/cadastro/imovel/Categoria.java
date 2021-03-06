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

package gcom.cadastro.imovel;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class Categoria
				extends ObjetoTransacao
				implements Comparable<Categoria> {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private String descricao;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private String descricaoAbreviada;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer consumoMinimo;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer consumoEstouro;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer quantidadeMaximoEconomiasValidacao;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private BigDecimal vezesMediaEstouro;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer mediaBaixoConsumo;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private BigDecimal porcentagemMediaBaixoConsumo;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer consumoAlto;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private BigDecimal vezesMediaAltoConsumo;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer numeroConsumoMaximoEc;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private CategoriaTipo categoriaTipo;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Short indicadorCobrancaAcrescimos;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer consumoMedioEconomiaMes;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private String descricaoComId;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer consumoViradaHidrometro;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Integer numeroVezesMediaViradaHidrometro;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Short indicadorValidarViradaHidrometro;

	// constantes
	public final static int RESIDENCIAL_INT = 1;

	public final static int COMERCIAL_INT = 2;

	public final static int INDUSTRIAL_INT = 3;

	public final static int PUBLICO_INT = 4;

	/**
	 * Conceito n�o usado no GSAN
	 */
	@Deprecated
	public final static int MISTO_INT = 5;

	public final static Integer RESIDENCIAL = Integer.valueOf(1);

	public final static Integer COMERCIAL = Integer.valueOf(2);

	public final static Integer INDUSTRIAL = Integer.valueOf(3);

	public final static Integer PUBLICO = Integer.valueOf(4);

	/**
	 * Conceito n�o usado no GSAN
	 */
	@Deprecated
	public final static Integer MISTO = Integer.valueOf(5);

	// QUANTIDADE DE ECONOMIAS - UTILIZADO NO CASO DE USO [UC0108] - Obter
	// Quantidade de Economias por Categoria
	private Integer quantidadeEconomiasCategoria;

	/** full constructor */
	public Categoria(String descricao, String descricaoAbreviada, Integer consumoMinimo, Integer consumoEstouro,
						BigDecimal vezesMediaEstouro, Integer mediaBaixoConsumo, BigDecimal porcentagemMediaBaixoConsumo,
						Integer consumoAlto, BigDecimal vezesMediaAltoConsumo, Short indicadorUso, Date ultimaAlteracao) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.consumoMinimo = consumoMinimo;
		this.consumoEstouro = consumoEstouro;
		this.vezesMediaEstouro = vezesMediaEstouro;
		this.mediaBaixoConsumo = mediaBaixoConsumo;
		this.porcentagemMediaBaixoConsumo = porcentagemMediaBaixoConsumo;
		this.consumoAlto = consumoAlto;
		this.vezesMediaAltoConsumo = vezesMediaAltoConsumo;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;

	}

	/** full constructor */
	public Categoria(String descricao, String descricaoAbreviada, Integer consumoMinimo, Integer consumoEstouro,
						BigDecimal vezesMediaEstouro, Integer mediaBaixoConsumo, BigDecimal porcentagemMediaBaixoConsumo,
						Integer consumoAlto, BigDecimal vezesMediaAltoConsumo, Short indicadorUso, Date ultimaAlteracao,
						CategoriaTipo categoriaTipo) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.consumoMinimo = consumoMinimo;
		this.consumoEstouro = consumoEstouro;
		this.vezesMediaEstouro = vezesMediaEstouro;
		this.mediaBaixoConsumo = mediaBaixoConsumo;
		this.porcentagemMediaBaixoConsumo = porcentagemMediaBaixoConsumo;
		this.consumoAlto = consumoAlto;
		this.vezesMediaAltoConsumo = vezesMediaAltoConsumo;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.categoriaTipo = categoriaTipo;
	}

	/** full constructor */
	public Categoria(String descricao, String descricaoAbreviada, Integer consumoMinimo, Integer consumoEstouro,
						BigDecimal vezesMediaEstouro, Integer mediaBaixoConsumo, BigDecimal porcentagemMediaBaixoConsumo,
						Integer consumoAlto, BigDecimal vezesMediaAltoConsumo, Short indicadorUso, Date ultimaAlteracao,
						Integer numeroConsumoMaximoEc) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.consumoMinimo = consumoMinimo;
		this.consumoEstouro = consumoEstouro;
		this.vezesMediaEstouro = vezesMediaEstouro;
		this.mediaBaixoConsumo = mediaBaixoConsumo;
		this.porcentagemMediaBaixoConsumo = porcentagemMediaBaixoConsumo;
		this.consumoAlto = consumoAlto;
		this.vezesMediaAltoConsumo = vezesMediaAltoConsumo;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.numeroConsumoMaximoEc = numeroConsumoMaximoEc;
	}

	
	public Categoria(String descricao, String descricaoAbreviada, Integer consumoMinimo, Integer consumoEstouro,
						BigDecimal vezesMediaEstouro, Integer mediaBaixoConsumo, BigDecimal porcentagemMediaBaixoConsumo,
						Integer consumoAlto, BigDecimal vezesMediaAltoConsumo, Short indicadorUso, Date ultimaAlteracao,
						CategoriaTipo categoriaTipo, Integer consumoViradaHidrometro, Integer numeroVezesMediaViradaHidrometro,
						Short indicadorValidarViradaHidrometro) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.consumoMinimo = consumoMinimo;
		this.consumoEstouro = consumoEstouro;
		this.vezesMediaEstouro = vezesMediaEstouro;
		this.mediaBaixoConsumo = mediaBaixoConsumo;
		this.porcentagemMediaBaixoConsumo = porcentagemMediaBaixoConsumo;
		this.consumoAlto = consumoAlto;
		this.vezesMediaAltoConsumo = vezesMediaAltoConsumo;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.categoriaTipo = categoriaTipo;
		this.consumoViradaHidrometro = consumoViradaHidrometro;
		this.numeroVezesMediaViradaHidrometro = numeroVezesMediaViradaHidrometro;
		this.indicadorValidarViradaHidrometro = indicadorValidarViradaHidrometro;

	}

	
	public Categoria(Integer id) {

		this.id = id;
	}

	public Categoria(Integer id, Integer quantidadeEconomiasCategoria) {

		this.id = id;
		this.quantidadeEconomiasCategoria = quantidadeEconomiasCategoria;
	}

	/** default constructor */
	public Categoria() {

	}

	/**
	 * @return Retorna o campo categoriaTipo.
	 */
	public CategoriaTipo getCategoriaTipo(){

		return categoriaTipo;
	}

	/**
	 * @param categoriaTipo
	 *            O categoriaTipo a ser setado.
	 */
	public void setCategoriaTipo(CategoriaTipo categoriaTipo){

		this.categoriaTipo = categoriaTipo;
	}

	/** minimal constructor */
	public Categoria(String descricao, String descricaoAbreviada) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
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

	public Integer getConsumoMinimo(){

		return this.consumoMinimo;
	}

	public void setConsumoMinimo(Integer consumoMinimo){

		this.consumoMinimo = consumoMinimo;
	}

	public Integer getConsumoEstouro(){

		return this.consumoEstouro;
	}

	public void setConsumoEstouro(Integer consumoEstouro){

		this.consumoEstouro = consumoEstouro;
	}

	public BigDecimal getVezesMediaEstouro(){

		return this.vezesMediaEstouro;
	}

	public void setVezesMediaEstouro(BigDecimal vezesMediaEstouro){

		this.vezesMediaEstouro = vezesMediaEstouro;
	}

	public Integer getMediaBaixoConsumo(){

		return this.mediaBaixoConsumo;
	}

	public void setMediaBaixoConsumo(Integer mediaBaixoConsumo){

		this.mediaBaixoConsumo = mediaBaixoConsumo;
	}

	public BigDecimal getPorcentagemMediaBaixoConsumo(){

		return this.porcentagemMediaBaixoConsumo;
	}

	public void setPorcentagemMediaBaixoConsumo(BigDecimal porcentagemMediaBaixoConsumo){

		this.porcentagemMediaBaixoConsumo = porcentagemMediaBaixoConsumo;
	}

	public Integer getConsumoAlto(){

		return this.consumoAlto;
	}

	public void setConsumoAlto(Integer consumoAlto){

		this.consumoAlto = consumoAlto;
	}

	public BigDecimal getVezesMediaAltoConsumo(){

		return this.vezesMediaAltoConsumo;
	}

	public void setVezesMediaAltoConsumo(BigDecimal vezesMediaAltoConsumo){

		this.vezesMediaAltoConsumo = vezesMediaAltoConsumo;
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

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * Descri��o do m�todo>>
	 * 
	 * @param other
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof Categoria)){
			return false;
		}
		Categoria castOther = (Categoria) other;

		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	/**
	 * Description of the Method
	 * 
	 * @return Description of the Return Value
	 */
	public int hashCode(){

		return new HashCodeBuilder().append(getId()).append(getConsumoAlto()).append(getConsumoEstouro()).append(getConsumoMinimo())
						.append(getDescricao()).append(getDescricaoAbreviada()).append(getIndicadorUso()).append(getMediaBaixoConsumo())
						.append(getPorcentagemMediaBaixoConsumo()).append(getVezesMediaAltoConsumo()).append(getUltimaAlteracao()).append(
										getVezesMediaEstouro()).toHashCode();
	}

	/**
	 * Retorna o valor de quantidadeEconomiasCategoria
	 * 
	 * @return O valor de quantidadeEconomiasCategoria
	 */
	public Integer getQuantidadeEconomiasCategoria(){

		return quantidadeEconomiasCategoria;
	}

	/**
	 * Seta o valor de quantidadeEconomiasCategoria
	 * 
	 * @param quantidadeEconomiasCategoria
	 *            O novo valor de quantidadeEconomiasCategoria
	 */
	public void setQuantidadeEconomiasCategoria(Integer quantidadeEconomiasCategoria){

		this.quantidadeEconomiasCategoria = quantidadeEconomiasCategoria;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, this.getId()));
		filtroCategoria.adicionarCaminhoParaCarregamentoEntidade("categoriaTipo");
		return filtroCategoria;
	}

	public Integer getNumeroConsumoMaximoEc(){

		return numeroConsumoMaximoEc;
	}

	public void setNumeroConsumoMaximoEc(Integer numeroConsumoMaximoEc){

		this.numeroConsumoMaximoEc = numeroConsumoMaximoEc;
	}

	public Short getIndicadorCobrancaAcrescimos(){

		return indicadorCobrancaAcrescimos;
	}

	public void setIndicadorCobrancaAcrescimos(Short indicadorCobrancaAcrescimos){

		this.indicadorCobrancaAcrescimos = indicadorCobrancaAcrescimos;
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 19/09/2007
	 * @return
	 */
	public String getDescricaoComId(){

		if(this.getId().compareTo(10) == -1){
			descricaoComId = "0" + getId() + " - " + getDescricao();
		}else{
			descricaoComId = getId() + " - " + getDescricao();
		}

		return descricaoComId;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return this.getDescricao();
	}

	@Override
	public void initializeLazy(){

		getDescricao();
	}

	public Integer getQuantidadeMaximoEconomiasValidacao(){

		return quantidadeMaximoEconomiasValidacao;
	}

	public void setQuantidadeMaximoEconomiasValidacao(Integer quantidadeMaximoEconomiasValidacao){

		this.quantidadeMaximoEconomiasValidacao = quantidadeMaximoEconomiasValidacao;
	}

	public Integer getConsumoMedioEconomiaMes(){

		return consumoMedioEconomiaMes;
	}

	public void setConsumoMedioEconomiaMes(Integer consumoMedioEconomiaMes){

		this.consumoMedioEconomiaMes = consumoMedioEconomiaMes;
	}

	public void setDescricaoComId(String descricaoComId){

		this.descricaoComId = descricaoComId;
	}

	public int compareTo(Categoria o){

		return this.id.compareTo(o.getId());
	}

	public Integer getConsumoViradaHidrometro(){

		return consumoViradaHidrometro;
	}

	public void setConsumoViradaHidrometro(Integer consumoViradaHidrometro){

		this.consumoViradaHidrometro = consumoViradaHidrometro;
	}

	public Integer getNumeroVezesMediaViradaHidrometro(){

		return numeroVezesMediaViradaHidrometro;
	}

	public void setNumeroVezesMediaViradaHidrometro(Integer numeroVezesMediaViradaHidrometro){

		this.numeroVezesMediaViradaHidrometro = numeroVezesMediaViradaHidrometro;
	}

	public Short getIndicadorValidarViradaHidrometro(){

		return indicadorValidarViradaHidrometro;
	}

	public void setIndicadorValidarViradaHidrometro(Short indicadorValidarViradaHidrometro){

		this.indicadorValidarViradaHidrometro = indicadorValidarViradaHidrometro;
	}

}