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

import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.QualidadeAgua;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.RoundingMode;
import java.util.Collection;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC0597] Inserir Qualidade de Agua
 * 
 * @author Kássia Albuquerque
 * @date 24/07/2007
 * @author eduardo henrique
 * @date 17/07/2008
 *       Adição dos novos atributos contidos no [UC0597]
 */

public class AtualizarQualidadeAguaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idQualidadeAgua;

	private String referencia;

	private String idLocalidade;

	private String localidadeDescricao;

	private String idSetorComercial;

	private String setorComercialDescricao;

	private String fonteCaptacao;

	private String indiceMensalTurbidez;

	private String padraoTurbidez;

	private String indiceMensalCloro;

	private String padraoCloro;

	private String indiceMensalPH;

	private String padraoPH;

	private String indiceMensalCor;

	private String padraoCor;

	private String indiceMensalFluor;

	private String padraoFluor;

	private String indiceMensalFerro;

	private String padraoFerro;

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

	public String getIndiceMensalCloro(){

		return indiceMensalCloro;
	}

	public void setIndiceMensalCloro(String indiceMensalCloro){

		this.indiceMensalCloro = indiceMensalCloro;
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

	public String getIndiceMensalFerro(){

		return indiceMensalFerro;
	}

	public void setIndiceMensalFerro(String indiceMensalFerro){

		this.indiceMensalFerro = indiceMensalFerro;
	}

	public String getIndiceMensalFluor(){

		return indiceMensalFluor;
	}

	public void setIndiceMensalFluor(String indiceMensalFluor){

		this.indiceMensalFluor = indiceMensalFluor;
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

	public String getPadraoCloro(){

		return padraoCloro;
	}

	public void setPadraoCloro(String padraoCloro){

		this.padraoCloro = padraoCloro;
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

	public String getPadraoFerro(){

		return padraoFerro;
	}

	public void setPadraoFerro(String padraoFerro){

		this.padraoFerro = padraoFerro;
	}

	public String getPadraoFluor(){

		return padraoFluor;
	}

	public void setPadraoFluor(String padraoFluor){

		this.padraoFluor = padraoFluor;
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

	public String getIdQualidadeAgua(){

		return idQualidadeAgua;
	}

	public void setIdQualidadeAgua(String idQualidadeAgua){

		this.idQualidadeAgua = idQualidadeAgua;
	}

	// SETA TODOS OS CAMPOS DO OBJETO QUALIDADE AGUA

	public QualidadeAgua setDadosQualidadeAgua(QualidadeAgua qualidadeAgua){

		Fachada fachada = Fachada.getInstancia();

		// Referencia
		qualidadeAgua.setAnoMesReferencia(new Integer(Util.formatarMesAnoParaAnoMesSemBarra(this.getReferencia())));

		// Localidade
		if(getIdLocalidade() != null && !getIdLocalidade().equals("")){

			Localidade localidade = new Localidade();
			localidade.setId(Integer.parseInt(getIdLocalidade()));
			qualidadeAgua.setLocalidade(localidade);
		}

		// Setor Comercial

		if(getIdSetorComercial() != null && !getIdSetorComercial().toString().trim().equalsIgnoreCase("")){
			if(getIdLocalidade() != null && !getIdLocalidade().toString().trim().equalsIgnoreCase("")){

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

				filtroSetorComercial.limparListaParametros();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, new Integer(
								getIdLocalidade())));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, new Integer(
								getIdSetorComercial())));

				Collection colecaoSetorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				SetorComercial setorComercialPesquisa = new SetorComercial();
				setorComercialPesquisa = (SetorComercial) colecaoSetorComerciais.iterator().next();
				SetorComercial setorComercial = new SetorComercial();

				setorComercial.setId(setorComercialPesquisa.getId());
				qualidadeAgua.setSetorComercial(setorComercial);

			}

		}else{
			qualidadeAgua.setSetorComercial(null);
		}

		// Fonte Captação

		qualidadeAgua.setDescricaoFonteCaptacao(getFonteCaptacao());

		// Amostras Realizadas
		if(getNumeroAmostrasRealizadasTurbidez() != null && !getNumeroAmostrasRealizadasTurbidez().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasTurbidez(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasTurbidez())
							.setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasRealizadasTurbidez(null);
		}

		if(getNumeroAmostrasRealizadasCor() != null && !getNumeroAmostrasRealizadasCor().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasCor(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasCor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasRealizadasCor(null);
		}

		if(getNumeroAmostrasRealizadasCloro() != null && !getNumeroAmostrasRealizadasCloro().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasCloro(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasCloro()).setScale(
							0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasRealizadasCloro(null);
		}

		if(getNumeroAmostrasRealizadasPH() != null && !getNumeroAmostrasRealizadasPH().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasPH(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasPH()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasRealizadasPH(null);
		}

		if(getNumeroAmostrasRealizadasFluor() != null && !getNumeroAmostrasRealizadasFluor().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasFluor(Util.formatarStringParaBigDecimal(getNumeroAmostrasRealizadasFluor()).setScale(
							0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasRealizadasFluor(null);
		}

		if(getNumeroAmostrasRealizadasColiformesTotais() != null && !getNumeroAmostrasRealizadasColiformesTotais().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasColiformesTotais(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasRealizadasColiformesTotais()).setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasRealizadasColiformesTotais(null);
		}

		if(getNumeroAmostrasRealizadasColiformesTermotolerantes() != null
						&& !getNumeroAmostrasRealizadasColiformesTermotolerantes().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasRealizadasColiformesTermotolerantes(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasRealizadasColiformesTermotolerantes()).setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasRealizadasColiformesTermotolerantes(null);
		}

		// Amostras Conformes
		if(getNumeroAmostrasConformesTurbidez() != null && !getNumeroAmostrasConformesTurbidez().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesTurbidez(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesTurbidez())
							.setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasConformesTurbidez(null);
		}

		if(getNumeroAmostrasConformesCor() != null && !getNumeroAmostrasConformesCor().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesCor(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesCor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasConformesCor(null);
		}

		if(getNumeroAmostrasConformesCloro() != null && !getNumeroAmostrasConformesCloro().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesCloro(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesCloro()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasConformesCloro(null);
		}

		if(getNumeroAmostrasConformesPH() != null && !getNumeroAmostrasConformesPH().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesPH(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesPH()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasConformesPH(null);
		}

		if(getNumeroAmostrasConformesFluor() != null && !getNumeroAmostrasConformesFluor().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesFluor(Util.formatarStringParaBigDecimal(getNumeroAmostrasConformesFluor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasConformesFluor(null);
		}

		if(getNumeroAmostrasConformesColiformesTotais() != null && !getNumeroAmostrasConformesColiformesTotais().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesColiformesTotais(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasConformesColiformesTotais()).setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasConformesColiformesTotais(null);
		}

		if(getNumeroAmostrasConformesColiformesTermotolerantes() != null
						&& !getNumeroAmostrasConformesColiformesTermotolerantes().trim().equals("")){
			qualidadeAgua.setNumeroAmostrasConformesColiformesTermotolerantes(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasConformesColiformesTermotolerantes()).setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasConformesColiformesTermotolerantes(null);
		}

		// Médias das Amostras
		if(getNumeroAmostrasMediaTurbidez() != null && !getNumeroAmostrasMediaTurbidez().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaTurbidez(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaTurbidez()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasMediaTurbidez(null);
		}

		if(getNumeroAmostrasMediaCor() != null && !getNumeroAmostrasMediaCor().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaCor(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaCor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasMediaCor(null);
		}

		if(getNumeroAmostrasMediaCloro() != null && !getNumeroAmostrasMediaCloro().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaCloro(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaCloro()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasMediaCloro(null);
		}

		if(getNumeroAmostrasMediaPH() != null && !getNumeroAmostrasMediaPH().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaPH(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaPH()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasMediaPH(null);
		}

		if(getNumeroAmostrasMediaFluor() != null && !getNumeroAmostrasMediaFluor().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaFluor(Util.formatarStringParaBigDecimal(getNumeroAmostrasMediaFluor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasMediaFluor(null);
		}

		if(getNumeroAmostrasMediaColiformesTotais() != null && !getNumeroAmostrasMediaColiformesTotais().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaColiformesTotais(Util
							.formatarStringParaBigDecimal(getNumeroAmostrasMediaColiformesTotais()).setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasMediaColiformesTotais(null);
		}

		if(getNumeroAmostrasMediaColiformesTermotolerantes() != null && !getNumeroAmostrasMediaColiformesTermotolerantes().equals("")){
			qualidadeAgua.setNumeroAmostrasMediaColiformesTermotolerantes(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasMediaColiformesTermotolerantes()).setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasMediaColiformesTermotolerantes(null);
		}

		if(getDescricaoConclusaoAnalisesRealizadas() != null && !getDescricaoConclusaoAnalisesRealizadas().trim().equals("")){
			qualidadeAgua.setDescricaoConclusaoAnalisesRealizadas(getDescricaoConclusaoAnalisesRealizadas());
		}else{
			qualidadeAgua.setDescricaoConclusaoAnalisesRealizadas(null);
		}

		if(getNumeroAmostrasExigidasTurbidez() != null && !getNumeroAmostrasExigidasTurbidez().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasTurbidez(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasTurbidez())
							.setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasExigidasTurbidez(null);
		}

		if(getNumeroAmostrasExigidasCor() != null && !getNumeroAmostrasExigidasCor().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasCor(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasCor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasExigidasCor(null);
		}

		if(getNumeroAmostrasExigidasCloro() != null && !getNumeroAmostrasExigidasCloro().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasCloro(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasCloro()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasExigidasCloro(null);
		}

		if(getNumeroAmostrasExigidasPH() != null && !getNumeroAmostrasExigidasPH().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasPh(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasPH()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasExigidasPh(null);
		}

		if(getNumeroAmostrasExigidasFluor() != null && !getNumeroAmostrasExigidasFluor().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasFluor(Util.formatarStringParaBigDecimal(getNumeroAmostrasExigidasFluor()).setScale(0,
							RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasExigidasFluor(null);
		}

		if(getNumeroAmostrasExigidasColiformesTotais() != null && !getNumeroAmostrasExigidasColiformesTotais().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasColiformesTotais(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasExigidasColiformesTotais()).setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasExigidasColiformesTotais(null);
		}

		if(getNumeroAmostrasExigidasColiformesTermotolerantes() != null && !getNumeroAmostrasExigidasColiformesTermotolerantes().equals("")){
			qualidadeAgua.setNumeroAmostrasExigidasColiformesTermotolerantes(Util.formatarStringParaBigDecimal(
							getNumeroAmostrasExigidasColiformesTermotolerantes()).setScale(0, RoundingMode.HALF_EVEN));
		}else{
			qualidadeAgua.setNumeroAmostrasExigidasColiformesTermotolerantes(null);
		}

		return qualidadeAgua;

	}

	public String getMesAno(Integer anoMes){

		String anoMesStr = anoMes + "";
		String mesAno = "";

		mesAno = anoMesStr.substring(0, 4) + "/" + anoMesStr.substring(4, 6);

		return mesAno;
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

	public String getNumeroAmostrasExigidasPH(){

		return numeroAmostrasExigidasPH;
	}

	public void setNumeroAmostrasExigidasPH(String numeroAmostrasExigidasPH){

		this.numeroAmostrasExigidasPH = numeroAmostrasExigidasPH;
	}

	public void setNumeroAmostrasExigidasCloro(String numeroAmostrasExigidasCloro){

		this.numeroAmostrasExigidasCloro = numeroAmostrasExigidasCloro;
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

	public String getIndiceMensalColiformesTermotolerantes(){

		return indiceMensalColiformesTermotolerantes;
	}

	public void setIndiceMensalColiformesTermotolerantes(String indiceMensalColiformesTermotolerantes){

		this.indiceMensalColiformesTermotolerantes = indiceMensalColiformesTermotolerantes;
	}

	public String getPadraoColiformesTermotolerantes(){

		return padraoColiformesTermotolerantes;
	}

	public void setPadraoColiformesTermotolerantes(String padraoColiformesTermotolerantes){

		this.padraoColiformesTermotolerantes = padraoColiformesTermotolerantes;
	}

	public String getIndiceMensalNitrato(){

		return indiceMensalNitrato;
	}

	public void setIndiceMensalNitrato(String indiceMensalNitrato){

		this.indiceMensalNitrato = indiceMensalNitrato;
	}

	public String getPadraoNitrato(){

		return padraoNitrato;
	}

	public void setPadraoNitrato(String padraoNitrato){

		this.padraoNitrato = padraoNitrato;
	}

}