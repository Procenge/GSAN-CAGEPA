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
 * GSANPCG
 * Virgínia Melo
 * Eduardo Henrique
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

package gcom.faturamento;

import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Representa a Qualidade da Agua
 * 
 * @author Thiago Toscano
 * @date 24/05/2006
 * @author eduardo henrique
 * @date 14/07/2008
 *       Adição de novos atributos descritos no [UC0596]
 * @author Virgínia Melo
 * @date 06/01/2009
 *       Adição dos campos numeroAmostrasMediaPH e numeroAmostrasMediaBacteriasHeterotroficas.
 */
public class QualidadeAgua
				extends ObjetoTransacao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date ultimaAlteracao;

	private Integer anoMesReferencia;

	private BigDecimal numeroIndiceTurbidez;

	private BigDecimal numeroCloroResidual;

	private BigDecimal numeroIndicePH;

	private BigDecimal numeroIndiceCor;

	private BigDecimal numeroIndiceFluor;

	private BigDecimal numeroIndiceFerro;

	private BigDecimal numeroIndiceColiformesTotais;

	private BigDecimal numeroIndiceColiformesFecais;

	private BigDecimal numeroNitrato;

	private Localidade localidade;

	private String descricaoFonteCaptacao;

	private SetorComercial setorComercial;

	private BigDecimal numeroAmostrasRealizadasTurbidez;

	private BigDecimal numeroAmostrasRealizadasCor;

	private BigDecimal numeroAmostrasRealizadasCloro;

	private BigDecimal numeroAmostrasRealizadasPH;

	private BigDecimal numeroAmostrasRealizadasFluor;

	private BigDecimal numeroAmostrasRealizadasColiformesTotais;

	private BigDecimal numeroAmostrasRealizadasColiformesTermotolerantes;

	private BigDecimal numeroAmostrasConformesTurbidez;

	private BigDecimal numeroAmostrasConformesCor;

	private BigDecimal numeroAmostrasConformesCloro;

	private BigDecimal numeroAmostrasConformesPH;

	private BigDecimal numeroAmostrasConformesFluor;

	private BigDecimal numeroAmostrasConformesColiformesTotais;

	private BigDecimal numeroAmostrasConformesColiformesTermotolerantes;

	private BigDecimal numeroAmostrasMediaTurbidez;

	private BigDecimal numeroAmostrasMediaCor;

	private BigDecimal numeroAmostrasMediaCloro;

	private BigDecimal numeroAmostrasMediaPH;

	private BigDecimal numeroAmostrasMediaFluor;

	private BigDecimal numeroAmostrasMediaColiformesTotais;

	private BigDecimal numeroAmostrasMediaColiformesTermotolerantes;

	private String descricaoConclusaoAnalisesRealizadas;

	private BigDecimal numeroAmostrasMediaBacteriasHeterotroficas;

	private String descricaoPadraoTurbidez;

	private String descricaoPadraoPh;

	private String descricaoPadraoCor;

	private String descricaoPadraoCloro;

	private String descricaoPadraoFluor;

	private String descricaoPadraoColiformesTotais;

	private String descricaoPadraoColiformesTermoto;

	private BigDecimal numeroAmostrasExigidasTurbidez;

	private BigDecimal numeroAmostrasExigidasCor;

	private BigDecimal numeroAmostrasExigidasCloro;

	private BigDecimal numeroAmostrasExigidasFluor;

	private BigDecimal numeroAmostrasExigidasColiformesTotais;

	private BigDecimal numeroAmostrasExigidasColiformesTermotolerantes;

	private BigDecimal numeroAmostrasExigidasPh;

	public QualidadeAgua() {

	}

	public QualidadeAgua(Integer id, Date ultimaAlteracao, Integer anoMesReferencia, BigDecimal numeroIndiceTurbidez,
							BigDecimal numeroCloroResidual, Localidade localidade, SetorComercial setorComercial,
							BigDecimal numeroAmostrasRealizadasTurbidez, BigDecimal numeroAmostrasRealizadasCor,
							BigDecimal numeroAmostrasRealizadasCloro, BigDecimal numeroAmostrasRealizadasPH,
							BigDecimal numeroAmostrasRealizadasFluor, BigDecimal numeroAmostrasRealizadasColiformesTotais,
							BigDecimal numeroAmostrasRealizadasColiformesTermotolerantes, BigDecimal numeroAmostrasConformesTurbidez,
							BigDecimal numeroAmostrasConformesCor, BigDecimal numeroAmostrasConformesCloro,
							BigDecimal numeroAmostrasConformesPH, BigDecimal numeroAmostrasConformesFluor,
							BigDecimal numeroAmostrasConformesColiformesTotais,
							BigDecimal numeroAmostrasConformesColiformesTermotolerantes, BigDecimal numeroAmostrasMediaTurbidez,
							BigDecimal numeroAmostrasMediaCor, BigDecimal numeroAmostrasMediaCloro, BigDecimal numeroAmostrasMediaPH,
							BigDecimal numeroAmostrasMediaFluor, BigDecimal numeroAmostrasMediaColiformesTotais,
							BigDecimal numeroAmostrasMediaColiformesTermotolerantes, String descricaoConclusaoAnalisesRealizadas,
							BigDecimal numeroAmostrasMediaBacteriasHeterotroficas) {

		super();
		// TODO Auto-generated constructor stub
		this.id = id;
		this.ultimaAlteracao = ultimaAlteracao;
		this.anoMesReferencia = anoMesReferencia;
		this.numeroIndiceTurbidez = numeroIndiceTurbidez;
		this.numeroCloroResidual = numeroCloroResidual;
		this.localidade = localidade;
		this.setorComercial = setorComercial;
		this.numeroAmostrasRealizadasTurbidez = numeroAmostrasRealizadasTurbidez;
		this.numeroAmostrasRealizadasCor = numeroAmostrasRealizadasCor;
		this.numeroAmostrasRealizadasCloro = numeroAmostrasRealizadasCloro;
		this.numeroAmostrasRealizadasPH = numeroAmostrasRealizadasPH;
		this.numeroAmostrasRealizadasFluor = numeroAmostrasRealizadasFluor;
		this.numeroAmostrasRealizadasColiformesTotais = numeroAmostrasRealizadasColiformesTotais;
		this.numeroAmostrasRealizadasColiformesTermotolerantes = numeroAmostrasRealizadasColiformesTermotolerantes;
		this.numeroAmostrasConformesTurbidez = numeroAmostrasConformesTurbidez;
		this.numeroAmostrasConformesCor = numeroAmostrasConformesCor;
		this.numeroAmostrasConformesCloro = numeroAmostrasConformesCloro;
		this.numeroAmostrasConformesPH = numeroAmostrasConformesPH;
		this.numeroAmostrasConformesFluor = numeroAmostrasConformesFluor;
		this.numeroAmostrasConformesColiformesTotais = numeroAmostrasConformesColiformesTotais;
		this.numeroAmostrasConformesColiformesTermotolerantes = numeroAmostrasConformesColiformesTermotolerantes;
		this.numeroAmostrasMediaTurbidez = numeroAmostrasMediaTurbidez;
		this.numeroAmostrasMediaCor = numeroAmostrasMediaCor;
		this.numeroAmostrasMediaCloro = numeroAmostrasMediaCloro;
		this.numeroAmostrasMediaPH = numeroAmostrasMediaPH;
		this.numeroAmostrasMediaFluor = numeroAmostrasMediaFluor;
		this.numeroAmostrasMediaColiformesTotais = numeroAmostrasMediaColiformesTotais;
		this.numeroAmostrasMediaColiformesTermotolerantes = numeroAmostrasMediaColiformesTermotolerantes;
		this.descricaoConclusaoAnalisesRealizadas = descricaoConclusaoAnalisesRealizadas;
		this.numeroAmostrasMediaBacteriasHeterotroficas = numeroAmostrasMediaBacteriasHeterotroficas;
	}

	/**
	 * @return Retorna o campo anoMesReferencia.
	 */
	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	/**
	 * @param anoMesReferencia
	 *            O anoMesReferencia a ser setado.
	 */
	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	/**
	 * @return Retorna o campo id.
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            O id a ser setado.
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return Retorna o campo localidade.
	 */
	public Localidade getLocalidade(){

		return localidade;
	}

	/**
	 * @param localidade
	 *            O localidade a ser setado.
	 */
	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	/**
	 * @return Retorna o campo numeroCloroResidual.
	 */
	public BigDecimal getNumeroCloroResidual(){

		return numeroCloroResidual;
	}

	/**
	 * @param numeroCloroResidual
	 *            O numeroCloroResidual a ser setado.
	 */
	public void setNumeroCloroResidual(BigDecimal numeroCloroResidual){

		this.numeroCloroResidual = numeroCloroResidual;
	}

	/**
	 * @return Retorna o campo numeroIndiceTurbidez.
	 */
	public BigDecimal getNumeroIndiceTurbidez(){

		return numeroIndiceTurbidez;
	}

	/**
	 * @param numeroIndiceTurbidez
	 *            O numeroIndiceTurbidez a ser setado.
	 */
	public void setNumeroIndiceTurbidez(BigDecimal numeroIndiceTurbidez){

		this.numeroIndiceTurbidez = numeroIndiceTurbidez;
	}

	/**
	 * @return Retorna o campo ultimaAlteracao.
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            O ultimaAlteracao a ser setado.
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public BigDecimal getNumeroIndiceColiformesFecais(){

		return numeroIndiceColiformesFecais;
	}

	public void setNumeroIndiceColiformesFecais(BigDecimal numeroIndiceColiformesFecais){

		this.numeroIndiceColiformesFecais = numeroIndiceColiformesFecais;
	}

	public BigDecimal getNumeroIndiceColiformesTotais(){

		return numeroIndiceColiformesTotais;
	}

	public void setNumeroIndiceColiformesTotais(BigDecimal numeroIndiceColiformesTotais){

		this.numeroIndiceColiformesTotais = numeroIndiceColiformesTotais;
	}

	public BigDecimal getNumeroIndiceCor(){

		return numeroIndiceCor;
	}

	public void setNumeroIndiceCor(BigDecimal numeroIndiceCor){

		this.numeroIndiceCor = numeroIndiceCor;
	}

	public BigDecimal getNumeroIndiceFerro(){

		return numeroIndiceFerro;
	}

	public void setNumeroIndiceFerro(BigDecimal numeroIndiceFerro){

		this.numeroIndiceFerro = numeroIndiceFerro;
	}

	public BigDecimal getNumeroIndiceFluor(){

		return numeroIndiceFluor;
	}

	public void setNumeroIndiceFluor(BigDecimal numeroIndiceFluor){

		this.numeroIndiceFluor = numeroIndiceFluor;
	}

	public BigDecimal getNumeroIndicePH(){

		return numeroIndicePH;
	}

	public void setNumeroIndicePH(BigDecimal numeroIndicePH){

		this.numeroIndicePH = numeroIndicePH;
	}

	public BigDecimal getNumeroNitrato(){

		return numeroNitrato;
	}

	public void setNumeroNitrato(BigDecimal numeroNitrato){

		this.numeroNitrato = numeroNitrato;
	}

	public String getDescricaoFonteCaptacao(){

		return descricaoFonteCaptacao;
	}

	public void setDescricaoFonteCaptacao(String descricaoFonteCaptacao){

		this.descricaoFonteCaptacao = descricaoFonteCaptacao;
	}

	public SetorComercial getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Filtro retornaFiltro(){

		FiltroQualidadeAgua filtroQualidadeAgua = new FiltroQualidadeAgua();

		filtroQualidadeAgua.adicionarParametro(new ParametroSimples(FiltroQualidadeAgua.ID, this.getId()));
		filtroQualidadeAgua.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroQualidadeAgua.adicionarCaminhoParaCarregamentoEntidade("setorComercial");

		return filtroQualidadeAgua;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String getMesAno(){

		String anoMesStr = this.anoMesReferencia + "";
		String mesAno = "";

		mesAno = anoMesStr.substring(4, 6) + "/" + anoMesStr.substring(0, 4);

		return mesAno;
	}

	public BigDecimal getNumeroAmostrasRealizadasTurbidez(){

		return numeroAmostrasRealizadasTurbidez;
	}

	public void setNumeroAmostrasRealizadasTurbidez(BigDecimal numeroAmostrasRealizadasTurbidez){

		this.numeroAmostrasRealizadasTurbidez = numeroAmostrasRealizadasTurbidez;
	}

	public BigDecimal getNumeroAmostrasRealizadasCor(){

		return numeroAmostrasRealizadasCor;
	}

	public void setNumeroAmostrasRealizadasCor(BigDecimal numeroAmostrasRealizadasCor){

		this.numeroAmostrasRealizadasCor = numeroAmostrasRealizadasCor;
	}

	public BigDecimal getNumeroAmostrasRealizadasCloro(){

		return numeroAmostrasRealizadasCloro;
	}

	public void setNumeroAmostrasRealizadasCloro(BigDecimal numeroAmostrasRealizadasCloro){

		this.numeroAmostrasRealizadasCloro = numeroAmostrasRealizadasCloro;
	}

	public BigDecimal getNumeroAmostrasRealizadasFluor(){

		return numeroAmostrasRealizadasFluor;
	}

	public void setNumeroAmostrasRealizadasFluor(BigDecimal numeroAmostrasRealizadasFluor){

		this.numeroAmostrasRealizadasFluor = numeroAmostrasRealizadasFluor;
	}

	public BigDecimal getNumeroAmostrasRealizadasColiformesTotais(){

		return numeroAmostrasRealizadasColiformesTotais;
	}

	public void setNumeroAmostrasRealizadasColiformesTotais(BigDecimal numeroAmostrasRealizadasColiformesTotais){

		this.numeroAmostrasRealizadasColiformesTotais = numeroAmostrasRealizadasColiformesTotais;
	}

	public BigDecimal getNumeroAmostrasRealizadasColiformesTermotolerantes(){

		return numeroAmostrasRealizadasColiformesTermotolerantes;
	}

	public void setNumeroAmostrasRealizadasColiformesTermotolerantes(BigDecimal numeroAmostrasRealizadasColiformesTermotolerantes){

		this.numeroAmostrasRealizadasColiformesTermotolerantes = numeroAmostrasRealizadasColiformesTermotolerantes;
	}

	public BigDecimal getNumeroAmostrasConformesTurbidez(){

		return numeroAmostrasConformesTurbidez;
	}

	public void setNumeroAmostrasConformesTurbidez(BigDecimal numeroAmostrasConformesTurbidez){

		this.numeroAmostrasConformesTurbidez = numeroAmostrasConformesTurbidez;
	}

	public BigDecimal getNumeroAmostrasConformesCor(){

		return numeroAmostrasConformesCor;
	}

	public void setNumeroAmostrasConformesCor(BigDecimal numeroAmostrasConformesCor){

		this.numeroAmostrasConformesCor = numeroAmostrasConformesCor;
	}

	public BigDecimal getNumeroAmostrasConformesCloro(){

		return numeroAmostrasConformesCloro;
	}

	public void setNumeroAmostrasConformesCloro(BigDecimal numeroAmostrasConformesCloro){

		this.numeroAmostrasConformesCloro = numeroAmostrasConformesCloro;
	}

	public BigDecimal getNumeroAmostrasConformesFluor(){

		return numeroAmostrasConformesFluor;
	}

	public void setNumeroAmostrasConformesFluor(BigDecimal numeroAmostrasConformesFluor){

		this.numeroAmostrasConformesFluor = numeroAmostrasConformesFluor;
	}

	public BigDecimal getNumeroAmostrasConformesColiformesTotais(){

		return numeroAmostrasConformesColiformesTotais;
	}

	public void setNumeroAmostrasConformesColiformesTotais(BigDecimal numeroAmostrasConformesColiformesTotais){

		this.numeroAmostrasConformesColiformesTotais = numeroAmostrasConformesColiformesTotais;
	}

	public BigDecimal getNumeroAmostrasConformesColiformesTermotolerantes(){

		return numeroAmostrasConformesColiformesTermotolerantes;
	}

	public void setNumeroAmostrasConformesColiformesTermotolerantes(BigDecimal numeroAmostrasConformesColiformesTermotolerantes){

		this.numeroAmostrasConformesColiformesTermotolerantes = numeroAmostrasConformesColiformesTermotolerantes;
	}

	public BigDecimal getNumeroAmostrasMediaTurbidez(){

		return numeroAmostrasMediaTurbidez;
	}

	public void setNumeroAmostrasMediaTurbidez(BigDecimal numeroAmostrasMediaTurbidez){

		this.numeroAmostrasMediaTurbidez = numeroAmostrasMediaTurbidez;
	}

	public BigDecimal getNumeroAmostrasMediaCor(){

		return numeroAmostrasMediaCor;
	}

	public void setNumeroAmostrasMediaCor(BigDecimal numeroAmostrasMediaCor){

		this.numeroAmostrasMediaCor = numeroAmostrasMediaCor;
	}

	public BigDecimal getNumeroAmostrasMediaCloro(){

		return numeroAmostrasMediaCloro;
	}

	public void setNumeroAmostrasMediaCloro(BigDecimal numeroAmostrasMediaCloro){

		this.numeroAmostrasMediaCloro = numeroAmostrasMediaCloro;
	}

	public BigDecimal getNumeroAmostrasMediaFluor(){

		return numeroAmostrasMediaFluor;
	}

	public void setNumeroAmostrasMediaFluor(BigDecimal numeroAmostrasMediaFluor){

		this.numeroAmostrasMediaFluor = numeroAmostrasMediaFluor;
	}

	public BigDecimal getNumeroAmostrasMediaColiformesTotais(){

		return numeroAmostrasMediaColiformesTotais;
	}

	public void setNumeroAmostrasMediaColiformesTotais(BigDecimal numeroAmostrasMediaColiformesTotais){

		this.numeroAmostrasMediaColiformesTotais = numeroAmostrasMediaColiformesTotais;
	}

	public BigDecimal getNumeroAmostrasMediaColiformesTermotolerantes(){

		return numeroAmostrasMediaColiformesTermotolerantes;
	}

	public void setNumeroAmostrasMediaColiformesTermotolerantes(BigDecimal numeroAmostrasMediaColiformesTermotolerantes){

		this.numeroAmostrasMediaColiformesTermotolerantes = numeroAmostrasMediaColiformesTermotolerantes;
	}

	public String getDescricaoConclusaoAnalisesRealizadas(){

		if(descricaoConclusaoAnalisesRealizadas != null && descricaoConclusaoAnalisesRealizadas.length() > 100){

			descricaoConclusaoAnalisesRealizadas = descricaoConclusaoAnalisesRealizadas.substring(0, 100);

		}

		return descricaoConclusaoAnalisesRealizadas;
	}

	public void setDescricaoConclusaoAnalisesRealizadas(String descricaoConclusaoAnalisesRealizadas){

		this.descricaoConclusaoAnalisesRealizadas = descricaoConclusaoAnalisesRealizadas;
	}

	public void initializeLazy(){

		if(setorComercial != null){
			setorComercial.initializeLazy();
		}
	}

	public BigDecimal getNumeroAmostrasMediaPH(){

		return numeroAmostrasMediaPH;
	}

	public void setNumeroAmostrasMediaPH(BigDecimal numeroAmostrasMediaPH){

		this.numeroAmostrasMediaPH = numeroAmostrasMediaPH;
	}

	public BigDecimal getNumeroAmostrasMediaBacteriasHeterotroficas(){

		return numeroAmostrasMediaBacteriasHeterotroficas;
	}

	public void setNumeroAmostrasMediaBacteriasHeterotroficas(BigDecimal numeroAmostrasMediaBacteriasHeterotroficas){

		this.numeroAmostrasMediaBacteriasHeterotroficas = numeroAmostrasMediaBacteriasHeterotroficas;
	}

	public BigDecimal getNumeroAmostrasRealizadasPH(){

		return numeroAmostrasRealizadasPH;
	}

	public void setNumeroAmostrasRealizadasPH(BigDecimal numeroAmostrasRealizadasPH){

		this.numeroAmostrasRealizadasPH = numeroAmostrasRealizadasPH;
	}

	public BigDecimal getNumeroAmostrasConformesPH(){

		return numeroAmostrasConformesPH;
	}

	public void setNumeroAmostrasConformesPH(BigDecimal numeroAmostrasConformesPH){

		this.numeroAmostrasConformesPH = numeroAmostrasConformesPH;
	}

	public String getDescricaoPadraoTurbidez(){

		return descricaoPadraoTurbidez;
	}

	public void setDescricaoPadraoTurbidez(String descricaoPadraoTurbidez){

		this.descricaoPadraoTurbidez = descricaoPadraoTurbidez;
	}

	public String getDescricaoPadraoPh(){

		return descricaoPadraoPh;
	}

	public void setDescricaoPadraoPh(String descricaoPadraoPh){

		this.descricaoPadraoPh = descricaoPadraoPh;
	}

	public String getDescricaoPadraoCor(){

		return descricaoPadraoCor;
	}

	public void setDescricaoPadraoCor(String descricaoPadraoCor){

		this.descricaoPadraoCor = descricaoPadraoCor;
	}

	public String getDescricaoPadraoCloro(){

		return descricaoPadraoCloro;
	}

	public void setDescricaoPadraoCloro(String descricaoPadraoCloro){

		this.descricaoPadraoCloro = descricaoPadraoCloro;
	}

	public String getDescricaoPadraoFluor(){

		return descricaoPadraoFluor;
	}

	public void setDescricaoPadraoFluor(String descricaoPadraoFluor){

		this.descricaoPadraoFluor = descricaoPadraoFluor;
	}

	public String getDescricaoPadraoColiformesTotais(){

		return descricaoPadraoColiformesTotais;
	}

	public void setDescricaoPadraoColiformesTotais(String descricaoPadraoColiformesTotais){

		this.descricaoPadraoColiformesTotais = descricaoPadraoColiformesTotais;
	}

	public String getDescricaoPadraoColiformesTermoto(){

		return descricaoPadraoColiformesTermoto;
	}

	public void setDescricaoPadraoColiformesTermoto(String descricaoPadraoColiformesTermoto){

		this.descricaoPadraoColiformesTermoto = descricaoPadraoColiformesTermoto;
	}

	public BigDecimal getNumeroAmostrasExigidasTurbidez(){

		return numeroAmostrasExigidasTurbidez;
	}

	public void setNumeroAmostrasExigidasTurbidez(BigDecimal numeroAmostrasExigidasTurbidez){

		this.numeroAmostrasExigidasTurbidez = numeroAmostrasExigidasTurbidez;
	}

	public BigDecimal getNumeroAmostrasExigidasCor(){

		return numeroAmostrasExigidasCor;
	}

	public void setNumeroAmostrasExigidasCor(BigDecimal numeroAmostrasExigidasCor){

		this.numeroAmostrasExigidasCor = numeroAmostrasExigidasCor;
	}

	public BigDecimal getNumeroAmostrasExigidasCloro(){

		return numeroAmostrasExigidasCloro;
	}

	public void setNumeroAmostrasExigidasCloro(BigDecimal numeroAmostrasExigidasCloro){

		this.numeroAmostrasExigidasCloro = numeroAmostrasExigidasCloro;
	}

	public BigDecimal getNumeroAmostrasExigidasFluor(){

		return numeroAmostrasExigidasFluor;
	}

	public void setNumeroAmostrasExigidasFluor(BigDecimal numeroAmostrasExigidasFluor){

		this.numeroAmostrasExigidasFluor = numeroAmostrasExigidasFluor;
	}

	public BigDecimal getNumeroAmostrasExigidasColiformesTotais(){

		return numeroAmostrasExigidasColiformesTotais;
	}

	public void setNumeroAmostrasExigidasColiformesTotais(BigDecimal numeroAmostrasExigidasColiformesTotais){

		this.numeroAmostrasExigidasColiformesTotais = numeroAmostrasExigidasColiformesTotais;
	}

	public BigDecimal getNumeroAmostrasExigidasColiformesTermotolerantes(){

		return numeroAmostrasExigidasColiformesTermotolerantes;
	}

	public void setNumeroAmostrasExigidasColiformesTermotolerantes(BigDecimal numeroAmostrasExigidasColiformesTermotolerantes){

		this.numeroAmostrasExigidasColiformesTermotolerantes = numeroAmostrasExigidasColiformesTermotolerantes;
	}

	public BigDecimal getNumeroAmostrasExigidasPh(){

		return numeroAmostrasExigidasPh;
	}

	public void setNumeroAmostrasExigidasPh(BigDecimal numeroAmostrasExigidasPh){

		this.numeroAmostrasExigidasPh = numeroAmostrasExigidasPh;
	}

}