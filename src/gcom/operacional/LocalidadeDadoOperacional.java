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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.operacional;

import gcom.cadastro.localidade.Localidade;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author isilva
 * @date 25/01/2011
 */
@ControleAlteracao()
public class LocalidadeDadoOperacional
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR = 1495;

	public static final int ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR = 1497;

	public static final int ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER = 3057;

	private static final long serialVersionUID = 1L;

	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Short indicadorUso;

	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer anoMesReferencia;

	@ControleAlteracao(value = FiltroLocalidadeDadoOperacional.LOCALIDADE, funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Localidade localidade;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private BigDecimal volumeProduzido;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private BigDecimal extensaoRedeAgua;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private BigDecimal extensaoRedeEsgoto;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer qtdFuncionariosAdministracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer qtdFuncionariosAdministracaoTercerizados;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer qtdFuncionariosOperacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer qtdFuncionariosOperacaoTercerizados;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer qtdSulfatoAluminio;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer qtdCloroGasoso;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer qtdHipocloritoSodio;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private Integer quantidadeFluor;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private BigDecimal qtdConsumoEnergia;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_INSERIR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_ATUALIZAR, ATRIBUTOS_LOCALIDADE_DADO_OPERACIONAL_REMOVER})
	private BigDecimal qtdHorasParalisadas;

	public LocalidadeDadoOperacional() {

	}

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the indicadorUso
	 */
	public Short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return the anoMesReferencia
	 */
	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	/**
	 * @param anoMesReferencia
	 *            the anoMesReferencia to set
	 */
	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
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
	 * @return the volumeProduzido
	 */
	public BigDecimal getVolumeProduzido(){

		return volumeProduzido;
	}

	/**
	 * @param volumeProduzido
	 *            the volumeProduzido to set
	 */
	public void setVolumeProduzido(BigDecimal volumeProduzido){

		this.volumeProduzido = volumeProduzido;
	}

	/**
	 * @return the extensaoRedeAgua
	 */
	public BigDecimal getExtensaoRedeAgua(){

		return extensaoRedeAgua;
	}

	/**
	 * @param extensaoRedeAgua
	 *            the extensaoRedeAgua to set
	 */
	public void setExtensaoRedeAgua(BigDecimal extensaoRedeAgua){

		this.extensaoRedeAgua = extensaoRedeAgua;
	}

	/**
	 * @return the extensaoRedeEsgoto
	 */
	public BigDecimal getExtensaoRedeEsgoto(){

		return extensaoRedeEsgoto;
	}

	/**
	 * @param extensaoRedeEsgoto
	 *            the extensaoRedeEsgoto to set
	 */
	public void setExtensaoRedeEsgoto(BigDecimal extensaoRedeEsgoto){

		this.extensaoRedeEsgoto = extensaoRedeEsgoto;
	}

	/**
	 * @return the qtdFuncionariosAdministracao
	 */
	public Integer getQtdFuncionariosAdministracao(){

		return qtdFuncionariosAdministracao;
	}

	/**
	 * @param qtdFuncionariosAdministracao
	 *            the qtdFuncionariosAdministracao to set
	 */
	public void setQtdFuncionariosAdministracao(Integer qtdFuncionariosAdministracao){

		this.qtdFuncionariosAdministracao = qtdFuncionariosAdministracao;
	}

	/**
	 * @return the qtdFuncionariosAdministracaoTercerizados
	 */
	public Integer getQtdFuncionariosAdministracaoTercerizados(){

		return qtdFuncionariosAdministracaoTercerizados;
	}

	/**
	 * @param qtdFuncionariosAdministracaoTercerizados
	 *            the qtdFuncionariosAdministracaoTercerizados to set
	 */
	public void setQtdFuncionariosAdministracaoTercerizados(Integer qtdFuncionariosAdministracaoTercerizados){

		this.qtdFuncionariosAdministracaoTercerizados = qtdFuncionariosAdministracaoTercerizados;
	}

	/**
	 * @return the qtdFuncionariosOperacao
	 */
	public Integer getQtdFuncionariosOperacao(){

		return qtdFuncionariosOperacao;
	}

	/**
	 * @param qtdFuncionariosOperacao
	 *            the qtdFuncionariosOperacao to set
	 */
	public void setQtdFuncionariosOperacao(Integer qtdFuncionariosOperacao){

		this.qtdFuncionariosOperacao = qtdFuncionariosOperacao;
	}

	/**
	 * @return the qtdFuncionariosOperacaoTercerizados
	 */
	public Integer getQtdFuncionariosOperacaoTercerizados(){

		return qtdFuncionariosOperacaoTercerizados;
	}

	/**
	 * @param qtdFuncionariosOperacaoTercerizados
	 *            the qtdFuncionariosOperacaoTercerizados to set
	 */
	public void setQtdFuncionariosOperacaoTercerizados(Integer qtdFuncionariosOperacaoTercerizados){

		this.qtdFuncionariosOperacaoTercerizados = qtdFuncionariosOperacaoTercerizados;
	}

	/**
	 * @return the qtdSulfatoAluminio
	 */
	public Integer getQtdSulfatoAluminio(){

		return qtdSulfatoAluminio;
	}

	/**
	 * @param qtdSulfatoAluminio
	 *            the qtdSulfatoAluminio to set
	 */
	public void setQtdSulfatoAluminio(Integer qtdSulfatoAluminio){

		this.qtdSulfatoAluminio = qtdSulfatoAluminio;
	}

	/**
	 * @return the qtdCloroGasoso
	 */
	public Integer getQtdCloroGasoso(){

		return qtdCloroGasoso;
	}

	/**
	 * @param qtdCloroGasoso
	 *            the qtdCloroGasoso to set
	 */
	public void setQtdCloroGasoso(Integer qtdCloroGasoso){

		this.qtdCloroGasoso = qtdCloroGasoso;
	}

	/**
	 * @return the qtdHipocloritoSodio
	 */
	public Integer getQtdHipocloritoSodio(){

		return qtdHipocloritoSodio;
	}

	/**
	 * @param qtdHipocloritoSodio
	 *            the qtdHipocloritoSodio to set
	 */
	public void setQtdHipocloritoSodio(Integer qtdHipocloritoSodio){

		this.qtdHipocloritoSodio = qtdHipocloritoSodio;
	}

	/**
	 * @return the quantidadeFluor
	 */
	public Integer getQuantidadeFluor(){

		return quantidadeFluor;
	}

	/**
	 * @param quantidadeFluor
	 *            the quantidadeFluor to set
	 */
	public void setQuantidadeFluor(Integer quantidadeFluor){

		this.quantidadeFluor = quantidadeFluor;
	}

	/**
	 * @return the qtdConsumoEnergia
	 */
	public BigDecimal getQtdConsumoEnergia(){

		return qtdConsumoEnergia;
	}

	/**
	 * @param qtdConsumoEnergia
	 *            the qtdConsumoEnergia to set
	 */
	public void setQtdConsumoEnergia(BigDecimal qtdConsumoEnergia){

		this.qtdConsumoEnergia = qtdConsumoEnergia;
	}

	/**
	 * @return the qtdHorasParalisadas
	 */
	public BigDecimal getQtdHorasParalisadas(){

		return qtdHorasParalisadas;
	}

	/**
	 * @param qtdHorasParalisadas
	 *            the qtdHorasParalisadas to set
	 */
	public void setQtdHorasParalisadas(BigDecimal qtdHorasParalisadas){

		this.qtdHorasParalisadas = qtdHorasParalisadas;
	}

	public String getMesAnoComBarra(){

		return Util.formatarAnoMesSemBarraParaMesAnoComBarra(this.getAnoMesReferencia());
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
	 */
	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param other
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof LocalidadeDadoOperacional)){
			return false;
		}
		LocalidadeDadoOperacional castOther = (LocalidadeDadoOperacional) other;

		return (this.getId().equals(castOther.getId()));
	}

	public Filtro retornaFiltro(){

		FiltroLocalidadeDadoOperacional filtroLocalidadeDadoOperacional = new FiltroLocalidadeDadoOperacional();
		filtroLocalidadeDadoOperacional.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadoOperacional.ID, this.getId()));
		filtroLocalidadeDadoOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidadeDadoOperacional.LOCALIDADE);
		return filtroLocalidadeDadoOperacional;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "mesAnoComBarra"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"ID", "Mes/Ano"};
		return labels;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}
}