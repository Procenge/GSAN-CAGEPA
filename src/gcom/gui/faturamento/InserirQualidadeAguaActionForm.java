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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.faturamento;

import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.faturamento.QualidadeAgua;
import gcom.util.Util;

import java.math.RoundingMode;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC0596] Inserir Qualidade de Agua
 * 
 * @author Kássia Albuquerque
 * @date 24/07/2007
 * @author eduardo henrique
 * @date 14/07/2008
 *       Inclusão de Novos Atributos incluídos no [UC0596]
 */

public class InserirQualidadeAguaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String referencia;

	private String idLocalidade;

	private String localidadeDescricao;

	private String idSetorComercial;

	private String setorComercialDescricao;

	private String fonteCaptacao;

	private String indiceMensalTurbidez;

	private String padraoTurbidez;

	private String indiceMensalCor;

	private String padraoCor;

	private String indiceMensalPH;

	private String padraoPH;

	private String indiceMensalFluor;

	private String padraoFluor;

	private String indiceMensalCloro;

	private String padraoCloro;

	private String indiceMensalColiformesTotais;

	private String padraoColiformesTotais;

	private String indiceMensalColiformesTermotolerantes;

	private String padraoColiformesTermotolerantes;

	private String indiceMensalNitrato;

	private String padraoNitrato;

	private String numeroAmostrasRealizadasTurbidez;

	private String numeroAmostrasRealizadasCor;

	private String numeroAmostrasRealizadasCloro;

	private String numeroAmostrasRealizadasPH;

	private String numeroAmostrasRealizadasFluor;

	private String numeroAmostrasRealizadasColiformesTotais;

	private String numeroAmostrasRealizadasColiformesTermotolerantes;

	private String numeroAmostrasConformesTurbidez;

	private String numeroAmostrasConformesCor;

	private String numeroAmostrasConformesCloro;

	private String numeroAmostrasConformesPH;

	private String numeroAmostrasConformesFluor;

	private String numeroAmostrasConformesColiformesTotais;

	private String numeroAmostrasConformesColiformesTermotolerantes;

	private String numeroAmostrasMediaTurbidez;

	private String numeroAmostrasMediaCor;

	private String numeroAmostrasMediaCloro;

	private String numeroAmostrasMediaPH;

	private String numeroAmostrasMediaFluor;

	private String numeroAmostrasMediaColiformesTotais;

	private String numeroAmostrasMediaColiformesTermotolerantes;

	private String descricaoConclusaoAnalisesRealizadas;

	private String numeroAmostrasExigidasTurbidez;

	private String numeroAmostrasExigidasCor;

	private String numeroAmostrasExigidasCloro;

	private String numeroAmostrasExigidasPH;

	private String numeroAmostrasExigidasFluor;

	private String numeroAmostrasExigidasColiformesTotais;

	private String numeroAmostrasExigidasColiformesTermotolerantes;

	public String getFonteCaptacao(){

		return fonteCaptacao;
	}

	public void setFonteCaptacao(String fonteCaptacao){

		this.fonteCaptacao = fonteCaptacao;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(String idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public String getIndiceMensalColiformesTermotolerantes(){

		return indiceMensalColiformesTermotolerantes;
	}

	public void setIndiceMensalColiformesTermotolerantes(String indiceMensalColiformesTermotolerantes){

		this.indiceMensalColiformesTermotolerantes = indiceMensalColiformesTermotolerantes;
	}

	public String getIndiceMensalColiformesTotais(){

		return indiceMensalColiformesTotais;
	}

	public void setIndiceMensalColiformesTotais(String indiceMensalColiformesTotais){

		this.indiceMensalColiformesTotais = indiceMensalColiformesTotais;
	}

	public String getIndiceMensalCor(){

		return indiceMensalCor;
	}

	public void setIndiceMensalCor(String indiceMensalCor){

		this.indiceMensalCor = indiceMensalCor;
	}

	public String getIndiceMensalCloro(){

		return indiceMensalCloro;
	}

	public void setIndiceMensalCloro(String indiceMensalCloro){

		this.indiceMensalCloro = indiceMensalCloro;
	}

	public String getIndiceMensalFluor(){

		return indiceMensalFluor;
	}

	public void setIndiceMensalFluor(String indiceMensalFluor){

		this.indiceMensalFluor = indiceMensalFluor;
	}

	public String getIndiceMensalNitrato(){

		return indiceMensalNitrato;
	}

	public void setIndiceMensalNitrato(String indiceMensalNitrato){

		this.indiceMensalNitrato = indiceMensalNitrato;
	}

	public String getIndiceMensalPH(){

		return indiceMensalPH;
	}

	public void setIndiceMensalPH(String indiceMensalPH){

		this.indiceMensalPH = indiceMensalPH;
	}

	public String getIndiceMensalTurbidez(){

		return indiceMensalTurbidez;
	}

	public void setIndiceMensalTurbidez(String indiceMensalTurbidez){

		this.indiceMensalTurbidez = indiceMensalTurbidez;
	}

	public String getLocalidadeDescricao(){

		return localidadeDescricao;
	}

	public void setLocalidadeDescricao(String localidadeDescricao){

		this.localidadeDescricao = localidadeDescricao;
	}

	public String getPadraoColiformesTermotolerantes(){

		return padraoColiformesTermotolerantes;
	}

	public void setPadraoColiformesTermotolerantes(String padraoColiformesTermotolerantes){

		this.padraoColiformesTermotolerantes = padraoColiformesTermotolerantes;
	}

	public String getPadraoColiformesTotais(){

		return padraoColiformesTotais;
	}

	public void setPadraoColiformesTotais(String padraoColiformesTotais){

		this.padraoColiformesTotais = padraoColiformesTotais;
	}

	public String getPadraoCor(){

		return padraoCor;
	}

	public void setPadraoCor(String padraoCor){

		this.padraoCor = padraoCor;
	}

	public String getPadraoCloro(){

		return padraoCloro;
	}

	public void setPadraoCloro(String padraoCloro){

		this.padraoCloro = padraoCloro;
	}

	public String getPadraoFluor(){

		return padraoFluor;
	}

	public void setPadraoFluor(String padraoFluor){

		this.padraoFluor = padraoFluor;
	}

	public String getPadraoNitrato(){

		return padraoNitrato;
	}

	public void setPadraoNitrato(String padraoNitrato){

		this.padraoNitrato = padraoNitrato;
	}

	public String getPadraoPH(){

		return padraoPH;
	}

	public void setPadraoPH(String padraoPH){

		this.padraoPH = padraoPH;
	}

	public String getPadraoTurbidez(){

		return padraoTurbidez;
	}

	public void setPadraoTurbidez(String padraoTurbidez){

		this.padraoTurbidez = padraoTurbidez;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getSetorComercialDescricao(){

		return setorComercialDescricao;
	}

	public void setSetorComercialDescricao(String setorComercialDescricao){

		this.setorComercialDescricao = setorComercialDescricao;
	}

	public QualidadeAgua setDadosQualidadeAgua(Localidade localidade, SetorComercial setorComercial){

		QualidadeAgua qualidadeAgua = new QualidadeAgua();

		// Referência
		qualidadeAgua.setAnoMesReferencia(Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(this.getReferencia())));

		// Localidade
		if(localidade != null){

			qualidadeAgua.setLocalidade(localidade);
		}

		// Setor Comercial
		if(setorComercial != null){
			qualidadeAgua.setSetorComercial(setorComercial);
		}

		// Fonte Captação

		qualidadeAgua.setDescricaoFonteCaptacao(getFonteCaptacao());

		// Amostras Realizadas
		if(getNumeroAmostrasRealizadasTurbidez() != null && !getNumeroAmostrasRealizadasTurbidez().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasTurbidez(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasTurbidez())
							.setScale(0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasRealizadasCor() != null && !getNumeroAmostrasRealizadasCor().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasCor(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasCor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasRealizadasCloro() != null && !getNumeroAmostrasRealizadasCloro().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasCloro(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasCloro()).setScale(
							0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasRealizadasPH() != null && !getNumeroAmostrasRealizadasPH().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasPH(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasPH()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasRealizadasFluor() != null && !getNumeroAmostrasRealizadasFluor().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasFluor(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasFluor()).setScale(
							0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasRealizadasColiformesTotais() != null && !getNumeroAmostrasRealizadasColiformesTotais().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasColiformesTotais(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasRealizadasColiformesTotais()).setScale(0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasRealizadasColiformesTermotolerantes() != null
						&& !getNumeroAmostrasRealizadasColiformesTermotolerantes().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasColiformesTermotolerantes(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasRealizadasColiformesTermotolerantes()).setScale(0, RoundingMode.HALF_EVEN));
		}

		// Amostras Conformes
		if(getNumeroAmostrasConformesTurbidez() != null && !getNumeroAmostrasConformesTurbidez().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesTurbidez(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesTurbidez())
							.setScale(0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasConformesCor() != null && !getNumeroAmostrasConformesCor().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesCor(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesCor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasConformesCloro() != null && !getNumeroAmostrasConformesCloro().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesCloro(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesCloro()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasConformesPH() != null && !getNumeroAmostrasConformesPH().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesPH(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesPH()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasConformesFluor() != null && !getNumeroAmostrasConformesFluor().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesFluor(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesFluor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasConformesColiformesTotais() != null && !getNumeroAmostrasConformesColiformesTotais().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesColiformesTotais(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasConformesColiformesTotais()).setScale(0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasConformesColiformesTermotolerantes() != null
						&& !getNumeroAmostrasConformesColiformesTermotolerantes().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesColiformesTermotolerantes(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasConformesColiformesTermotolerantes()).setScale(0, RoundingMode.HALF_EVEN));
		}

		// Médias das Amostras
		if(getNumeroAmostrasMediaTurbidez() != null && !getNumeroAmostrasMediaTurbidez().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaTurbidez(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaTurbidez()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasMediaCor() != null && !getNumeroAmostrasMediaCor().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaCor(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaCor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasMediaCloro() != null && !getNumeroAmostrasMediaCloro().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaCloro(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaCloro()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasMediaPH() != null && !getNumeroAmostrasMediaPH().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaPH(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaPH()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasMediaFluor() != null && !getNumeroAmostrasMediaFluor().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaFluor(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaFluor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasMediaColiformesTotais() != null && !getNumeroAmostrasMediaColiformesTotais().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaColiformesTotais(Util
							.formatarStringParaBigDecimal(getNumeroAmostrasMediaColiformesTotais()).setScale(0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasMediaColiformesTermotolerantes() != null && !getNumeroAmostrasMediaColiformesTermotolerantes().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaColiformesTermotolerantes(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasMediaColiformesTermotolerantes()).setScale(0, RoundingMode.HALF_EVEN));
		}

		if(getDescricaoConclusaoAnalisesRealizadas() != null && !getDescricaoConclusaoAnalisesRealizadas().trim().equals("")){
			qualidadeAgua.setDescricaoConclusaoAnalisesRealizadas(getDescricaoConclusaoAnalisesRealizadas());
		}

		if(getPadraoTurbidez() != null && !getPadraoTurbidez().trim().equals("")){
			qualidadeAgua.setDescricaoPadraoTurbidez(getPadraoTurbidez());
		}

		if(getPadraoCor() != null && !getPadraoCor().trim().equals("")){
			qualidadeAgua.setDescricaoPadraoCor(getPadraoCor());

		}

		if(getPadraoCloro() != null && !getPadraoCloro().trim().equals("")){
			qualidadeAgua.setDescricaoPadraoCloro(getPadraoCloro());
		}

		if(getPadraoPH() != null && !getPadraoPH().trim().equals("")){
			qualidadeAgua.setDescricaoPadraoPh(getPadraoPH());
		}

		if(getPadraoFluor() != null && !getPadraoFluor().trim().equals("")){
			qualidadeAgua.setDescricaoPadraoFluor(getPadraoFluor());
		}
		if(getPadraoColiformesTotais() != null && !getPadraoColiformesTotais().trim().equals("")){
			qualidadeAgua.setDescricaoPadraoColiformesTotais(getPadraoColiformesTotais());
		}
		if(getPadraoColiformesTermotolerantes() != null && !getPadraoColiformesTermotolerantes().trim().equals("")){
			qualidadeAgua.setDescricaoPadraoColiformesTermoto(getPadraoColiformesTermotolerantes());
		}

		if(getNumeroAmostrasExigidasTurbidez() != null && !getNumeroAmostrasExigidasTurbidez().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasTurbidez(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasTurbidez())
							.setScale(0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasExigidasCor() != null && !getNumeroAmostrasExigidasCor().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasCor(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasCor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasExigidasCloro() != null && !getNumeroAmostrasExigidasCloro().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasCloro(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasCloro()).setScale(0,
							RoundingMode.HALF_EVEN));

		}

		if(getNumeroAmostrasExigidasPH() != null && !getNumeroAmostrasExigidasPH().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasPh(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasPH()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasExigidasFluor() != null && !getNumeroAmostrasExigidasFluor().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasFluor(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasFluor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasExigidasColiformesTotais() != null && !getNumeroAmostrasExigidasColiformesTotais().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasColiformesTotais(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasExigidasColiformesTotais()).setScale(0, RoundingMode.HALF_EVEN));
		}

		if(getNumeroAmostrasExigidasColiformesTermotolerantes() != null && !getNumeroAmostrasExigidasColiformesTermotolerantes().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasColiformesTermotolerantes(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasExigidasColiformesTermotolerantes()).setScale(0, RoundingMode.HALF_EVEN));
		}

		return qualidadeAgua;

	}

	public String getNumeroAmostrasRealizadasTurbidez(){

		return numeroAmostrasRealizadasTurbidez;
	}

	public void setNumeroAmostrasRealizadasTurbidez(String numeroAmostrasRealizadasTurbidez){

		this.numeroAmostrasRealizadasTurbidez = numeroAmostrasRealizadasTurbidez;
	}

	public String getNumeroAmostrasRealizadasCor(){

		return numeroAmostrasRealizadasCor;
	}

	public void setNumeroAmostrasRealizadasCor(String numeroAmostrasRealizadasCor){

		this.numeroAmostrasRealizadasCor = numeroAmostrasRealizadasCor;
	}

	public String getNumeroAmostrasRealizadasCloro(){

		return numeroAmostrasRealizadasCloro;
	}

	public void setNumeroAmostrasRealizadasCloro(String numeroAmostrasRealizadasCloro){

		this.numeroAmostrasRealizadasCloro = numeroAmostrasRealizadasCloro;
	}

	public String getNumeroAmostrasRealizadasPH(){

		return numeroAmostrasRealizadasPH;
	}

	public void setNumeroAmostrasRealizadasPH(String numeroAmostrasRealizadasPH){

		this.numeroAmostrasRealizadasPH = numeroAmostrasRealizadasPH;
	}

	public String getNumeroAmostrasRealizadasFluor(){

		return numeroAmostrasRealizadasFluor;
	}

	public void setNumeroAmostrasRealizadasFluor(String numeroAmostrasRealizadasFluor){

		this.numeroAmostrasRealizadasFluor = numeroAmostrasRealizadasFluor;
	}

	public String getNumeroAmostrasRealizadasColiformesTotais(){

		return numeroAmostrasRealizadasColiformesTotais;
	}

	public void setNumeroAmostrasRealizadasColiformesTotais(String numeroAmostrasRealizadasColiformesTotais){

		this.numeroAmostrasRealizadasColiformesTotais = numeroAmostrasRealizadasColiformesTotais;
	}

	public String getNumeroAmostrasRealizadasColiformesTermotolerantes(){

		return numeroAmostrasRealizadasColiformesTermotolerantes;
	}

	public void setNumeroAmostrasRealizadasColiformesTermotolerantes(String numeroAmostrasRealizadasColiformesTermotolerantes){

		this.numeroAmostrasRealizadasColiformesTermotolerantes = numeroAmostrasRealizadasColiformesTermotolerantes;
	}

	public String getNumeroAmostrasConformesTurbidez(){

		return numeroAmostrasConformesTurbidez;
	}

	public void setNumeroAmostrasConformesTurbidez(String numeroAmostrasConformesTurbidez){

		this.numeroAmostrasConformesTurbidez = numeroAmostrasConformesTurbidez;
	}

	public String getNumeroAmostrasConformesCor(){

		return numeroAmostrasConformesCor;
	}

	public void setNumeroAmostrasConformesCor(String numeroAmostrasConformesCor){

		this.numeroAmostrasConformesCor = numeroAmostrasConformesCor;
	}

	public String getNumeroAmostrasConformesCloro(){

		return numeroAmostrasConformesCloro;
	}

	public void setNumeroAmostrasConformesCloro(String numeroAmostrasConformesCloro){

		this.numeroAmostrasConformesCloro = numeroAmostrasConformesCloro;
	}

	public String getNumeroAmostrasConformesPH(){

		return numeroAmostrasConformesPH;
	}

	public void setNumeroAmostrasConformesPH(String numeroAmostrasConformesPH){

		this.numeroAmostrasConformesPH = numeroAmostrasConformesPH;
	}

	public String getNumeroAmostrasConformesFluor(){

		return numeroAmostrasConformesFluor;
	}

	public void setNumeroAmostrasConformesFluor(String numeroAmostrasConformesFluor){

		this.numeroAmostrasConformesFluor = numeroAmostrasConformesFluor;
	}

	public String getNumeroAmostrasConformesColiformesTotais(){

		return numeroAmostrasConformesColiformesTotais;
	}

	public void setNumeroAmostrasConformesColiformesTotais(String numeroAmostrasConformesColiformesTotais){

		this.numeroAmostrasConformesColiformesTotais = numeroAmostrasConformesColiformesTotais;
	}

	public String getNumeroAmostrasConformesColiformesTermotolerantes(){

		return numeroAmostrasConformesColiformesTermotolerantes;
	}

	public void setNumeroAmostrasConformesColiformesTermotolerantes(String numeroAmostrasConformesColiformesTermotolerantes){

		this.numeroAmostrasConformesColiformesTermotolerantes = numeroAmostrasConformesColiformesTermotolerantes;
	}

	public String getNumeroAmostrasMediaTurbidez(){

		return numeroAmostrasMediaTurbidez;
	}

	public void setNumeroAmostrasMediaTurbidez(String numeroAmostrasMediaTurbidez){

		this.numeroAmostrasMediaTurbidez = numeroAmostrasMediaTurbidez;
	}

	public String getNumeroAmostrasMediaCor(){

		return numeroAmostrasMediaCor;
	}

	public void setNumeroAmostrasMediaCor(String numeroAmostrasMediaCor){

		this.numeroAmostrasMediaCor = numeroAmostrasMediaCor;
	}

	public String getNumeroAmostrasMediaCloro(){

		return numeroAmostrasMediaCloro;
	}

	public void setNumeroAmostrasMediaCloro(String numeroAmostrasMediaCloro){

		this.numeroAmostrasMediaCloro = numeroAmostrasMediaCloro;
	}

	public String getNumeroAmostrasMediaPH(){

		return numeroAmostrasMediaPH;
	}

	public void setNumeroAmostrasMediaPH(String numeroAmostrasMediaPH){

		this.numeroAmostrasMediaPH = numeroAmostrasMediaPH;
	}

	public String getNumeroAmostrasMediaFluor(){

		return numeroAmostrasMediaFluor;
	}

	public void setNumeroAmostrasMediaFluor(String numeroAmostrasMediaFluor){

		this.numeroAmostrasMediaFluor = numeroAmostrasMediaFluor;
	}

	public String getNumeroAmostrasMediaColiformesTotais(){

		return numeroAmostrasMediaColiformesTotais;
	}

	public void setNumeroAmostrasMediaColiformesTotais(String numeroAmostrasMediaColiformesTotais){

		this.numeroAmostrasMediaColiformesTotais = numeroAmostrasMediaColiformesTotais;
	}

	public String getNumeroAmostrasMediaColiformesTermotolerantes(){

		return numeroAmostrasMediaColiformesTermotolerantes;
	}

	public void setNumeroAmostrasMediaColiformesTermotolerantes(String numeroAmostrasMediaColiformesTermotolerantes){

		this.numeroAmostrasMediaColiformesTermotolerantes = numeroAmostrasMediaColiformesTermotolerantes;
	}

	public String getDescricaoConclusaoAnalisesRealizadas(){

		return descricaoConclusaoAnalisesRealizadas;
	}

	public void setDescricaoConclusaoAnalisesRealizadas(String descricaoConclusaoAnalisesRealizadas){

		this.descricaoConclusaoAnalisesRealizadas = descricaoConclusaoAnalisesRealizadas;
	}

	public String getNumeroAmostrasExigidasTurbidez(){

		return numeroAmostrasExigidasTurbidez;
	}

	public void setNumeroAmostrasExigidasTurbidez(String numeroAmostrasExigidasTurbidez){

		this.numeroAmostrasExigidasTurbidez = numeroAmostrasExigidasTurbidez;
	}

	public String getNumeroAmostrasExigidasCor(){

		return numeroAmostrasExigidasCor;
	}

	public void setNumeroAmostrasExigidasCor(String numeroAmostrasExigidasCor){

		this.numeroAmostrasExigidasCor = numeroAmostrasExigidasCor;
	}

	public String getNumeroAmostrasExigidasCloro(){

		return numeroAmostrasExigidasCloro;
	}

	public void setNumeroAmostrasExigidasCloro(String numeroAmostrasExigidasCloro){

		this.numeroAmostrasExigidasCloro = numeroAmostrasExigidasCloro;
	}

	public String getNumeroAmostrasExigidasPH(){

		return numeroAmostrasExigidasPH;
	}

	public void setNumeroAmostrasExigidasPH(String numeroAmostrasExigidasPH){

		this.numeroAmostrasExigidasPH = numeroAmostrasExigidasPH;
	}

	public String getNumeroAmostrasExigidasFluor(){

		return numeroAmostrasExigidasFluor;
	}

	public void setNumeroAmostrasExigidasFluor(String numeroAmostrasExigidasFluor){

		this.numeroAmostrasExigidasFluor = numeroAmostrasExigidasFluor;
	}

	public String getNumeroAmostrasExigidasColiformesTotais(){

		return numeroAmostrasExigidasColiformesTotais;
	}

	public void setNumeroAmostrasExigidasColiformesTotais(String numeroAmostrasExigidasColiformesTotais){

		this.numeroAmostrasExigidasColiformesTotais = numeroAmostrasExigidasColiformesTotais;
	}

	public String getNumeroAmostrasExigidasColiformesTermotolerantes(){

		return numeroAmostrasExigidasColiformesTermotolerantes;
	}

	public void setNumeroAmostrasExigidasColiformesTermotolerantes(String numeroAmostrasExigidasColiformesTermotolerantes){

		this.numeroAmostrasExigidasColiformesTermotolerantes = numeroAmostrasExigidasColiformesTermotolerantes;
	}

}