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

package gcom.atendimentopublico.ligacaoesgoto;

import gcom.atendimentopublico.ligacaoagua.RamalLocalInstalacao;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class LigacaoEsgoto
				extends ObjetoTransacao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_LIGACAO_ESGOTO_INSERIR = 272;

	public static final int ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR = 430;

	/** identifier field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private Integer id;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private Date dataLigacao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private Integer consumoMinimo;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private BigDecimal percentual;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private BigDecimal percentualAguaConsumidaColetada;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private Date ultimaAlteracao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private Short indicadorCaixaGordura;

	/** nullable persistent field */
	@ControleAlteracao(value = "imovel", funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private Imovel imovel;

	/** persistent field */
	@ControleAlteracao(value = "ligacaoEsgotoPerfil", funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil ligacaoEsgotoPerfil;

	/** persistent field */
	@ControleAlteracao(value = "ligacaoEsgotoDiametro", funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoDiametro ligacaoEsgotoDiametro;

	/** persistent field */
	@ControleAlteracao(value = "ligacaoEsgotoMaterial", funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoMaterial ligacaoEsgotoMaterial;

	/** persistent field */
	@ControleAlteracao(value = "funcionarioLigacaoEsgoto", funcionalidade = {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private Funcionario funcionarioLigacaoEsgoto;

	// @ControleAlteracao(value = "ramalLocalInstalacao", funcionalidade =
	// {ATRIBUTOS_LIGACAO_ESGOTO_INSERIR, ATRIBUTOS_LIGACAO_ESGOTO_ATUALIZAR})
	private RamalLocalInstalacao ramalLocalInstalacao;

	private Integer numeroConsumoFixoPoco;

	/** full constructor */
	public LigacaoEsgoto(Date dataLigacao, Integer consumoMinimo, BigDecimal percentual, BigDecimal percentualAguaConsumidaColetada,
							Date ultimaAlteracao, Short indicadorCaixaGordura, Imovel imovel,
							gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil ligacaoEsgotoPerfil,
							gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoDiametro ligacaoEsgotoDiametro,
							gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoMaterial ligacaoEsgotoMaterial) {

		this.dataLigacao = dataLigacao;
		this.consumoMinimo = consumoMinimo;
		this.percentual = percentual;
		this.percentualAguaConsumidaColetada = percentualAguaConsumidaColetada;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorCaixaGordura = indicadorCaixaGordura;
		this.imovel = imovel;
		this.ligacaoEsgotoPerfil = ligacaoEsgotoPerfil;
		this.ligacaoEsgotoDiametro = ligacaoEsgotoDiametro;
		this.ligacaoEsgotoMaterial = ligacaoEsgotoMaterial;
	}

	/** default constructor */
	public LigacaoEsgoto() {

	}

	/** minimal constructor */
	public LigacaoEsgoto(gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil ligacaoEsgotoPerfil,
							gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoDiametro ligacaoEsgotoDiametro,
							gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoMaterial ligacaoEsgotoMaterial) {

		this.ligacaoEsgotoPerfil = ligacaoEsgotoPerfil;
		this.ligacaoEsgotoDiametro = ligacaoEsgotoDiametro;
		this.ligacaoEsgotoMaterial = ligacaoEsgotoMaterial;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataLigacao(){

		return this.dataLigacao;
	}

	public void setDataLigacao(Date dataLigacao){

		this.dataLigacao = dataLigacao;
	}

	public Integer getConsumoMinimo(){

		return this.consumoMinimo;
	}

	public void setConsumoMinimo(Integer consumoMinimo){

		this.consumoMinimo = consumoMinimo;
	}

	public BigDecimal getPercentual(){

		return this.percentual;
	}

	public void setPercentual(BigDecimal percentual){

		this.percentual = percentual;
	}

	public BigDecimal getPercentualAguaConsumidaColetada(){

		return this.percentualAguaConsumidaColetada;
	}

	public void setPercentualAguaConsumidaColetada(BigDecimal percentualAguaConsumidaColetada){

		this.percentualAguaConsumidaColetada = percentualAguaConsumidaColetada;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil getLigacaoEsgotoPerfil(){

		return this.ligacaoEsgotoPerfil;
	}

	public void setLigacaoEsgotoPerfil(gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil ligacaoEsgotoPerfil){

		this.ligacaoEsgotoPerfil = ligacaoEsgotoPerfil;
	}

	public gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoDiametro getLigacaoEsgotoDiametro(){

		return this.ligacaoEsgotoDiametro;
	}

	public void setLigacaoEsgotoDiametro(gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoDiametro ligacaoEsgotoDiametro){

		this.ligacaoEsgotoDiametro = ligacaoEsgotoDiametro;
	}

	public gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoMaterial getLigacaoEsgotoMaterial(){

		return this.ligacaoEsgotoMaterial;
	}

	public void setLigacaoEsgotoMaterial(gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoMaterial ligacaoEsgotoMaterial){

		this.ligacaoEsgotoMaterial = ligacaoEsgotoMaterial;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @return Retorna o campo indicadorCaixaGordura.
	 */
	public Short getIndicadorCaixaGordura(){

		return indicadorCaixaGordura;
	}

	/**
	 * @param indicadorCaixaGordura
	 *            O indicadorCaixaGordura a ser setado.
	 */
	public void setIndicadorCaixaGordura(Short indicadorCaixaGordura){

		this.indicadorCaixaGordura = indicadorCaixaGordura;
	}

	public Filtro retornaFiltro(){

		FiltroLigacaoEsgoto filtroLigacaoEsgoto = new FiltroLigacaoEsgoto();

		filtroLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, this.getId()));
		filtroLigacaoEsgoto.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoPerfil");
		filtroLigacaoEsgoto.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoDiametro");
		filtroLigacaoEsgoto.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoMaterial");
		filtroLigacaoEsgoto.adicionarCaminhoParaCarregamentoEntidade("imovel");

		return filtroLigacaoEsgoto;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"imovel.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Matr"};
		return labels;
	}

	public void setFuncionarioLigacaoEsgoto(Funcionario funcionarioLigacaoEsgoto){

		this.funcionarioLigacaoEsgoto = funcionarioLigacaoEsgoto;
	}

	public Funcionario getFuncionarioLigacaoEsgoto(){

		return funcionarioLigacaoEsgoto;
	}

	public void setRamalLocalInstalacao(RamalLocalInstalacao ramalLocalInstalacao){

		this.ramalLocalInstalacao = ramalLocalInstalacao;
	}

	public RamalLocalInstalacao getRamalLocalInstalacao(){

		return ramalLocalInstalacao;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "  " + "";
	}

	public Integer getNumeroConsumoFixoPoco(){

		return numeroConsumoFixoPoco;
	}

	public void setNumeroConsumoFixoPoco(Integer numeroConsumoFixoPoco){

		this.numeroConsumoFixoPoco = numeroConsumoFixoPoco;
	}

}
