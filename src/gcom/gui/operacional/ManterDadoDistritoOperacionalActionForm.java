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

package gcom.gui.operacional;

import gcom.atendimentopublico.ordemservico.MaterialRedeAgua;
import gcom.operacional.DadoDistritoOperacional;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.validator.ValidatorForm;

/**
 * [UC005] DISTRITO OPERACIONAL
 * 
 * @author Péricles Tavares
 * @date 28/03/2011
 */

public class ManterDadoDistritoOperacionalActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String id;

	private String anoMesReferencia;

	private String unidadeCapacidade;

	private String aducaoCapacidade;

	private String volumeCapacidade;

	private String extensaoAdutora;

	private String diametroAdutora;

	private String materialAdutora;

	private String demandaEnergia;

	private String qtidadeSulfatoAluminio;

	private String qtidadeCloroGasoso;

	private String qtidadeHipocloritoSodio;

	private String qtidadeFluor;

	private String qtidadeHorasParalisadas;

	private String usuarioManutencao;

	private String distritoOperacional;

	private String indicadorUso;

	private String atualizar;

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(String anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public String getUnidadeCapacidade(){

		return unidadeCapacidade;
	}

	public void setUnidadeCapacidade(String unidadeCapacidade){

		this.unidadeCapacidade = unidadeCapacidade;
	}

	public String getAducaoCapacidade(){

		return aducaoCapacidade;
	}

	public void setAducaoCapacidade(String aducaoCapacidade){

		this.aducaoCapacidade = aducaoCapacidade;
	}

	public String getVolumeCapacidade(){

		return volumeCapacidade;
	}

	public void setVolumeCapacidade(String volumeCapacidade){

		this.volumeCapacidade = volumeCapacidade;
	}

	public String getExtensaoAdutora(){

		return extensaoAdutora;
	}

	public void setExtensaoAdutora(String extensaoAdutora){

		this.extensaoAdutora = extensaoAdutora;
	}

	public String getDiametroAdutora(){

		return diametroAdutora;
	}

	public void setDiametroAdutora(String diametroAdutora){

		this.diametroAdutora = diametroAdutora;
	}

	public String getMaterialAdutora(){

		return materialAdutora;
	}

	public void setMaterialAdutora(String materialAdutora){

		this.materialAdutora = materialAdutora;
	}

	public String getDemandaEnergia(){

		return demandaEnergia;
	}

	public void setDemandaEnergia(String demandaEnergia){

		this.demandaEnergia = demandaEnergia;
	}

	public String getQtidadeSulfatoAluminio(){

		return qtidadeSulfatoAluminio;
	}

	public void setQtidadeSulfatoAluminio(String qtidadeSulfatoAluminio){

		this.qtidadeSulfatoAluminio = qtidadeSulfatoAluminio;
	}

	public String getQtidadeCloroGasoso(){

		return qtidadeCloroGasoso;
	}

	public void setQtidadeCloroGasoso(String qtidadeCloroGasoso){

		this.qtidadeCloroGasoso = qtidadeCloroGasoso;
	}

	public String getQtidadeHipocloritoSodio(){

		return qtidadeHipocloritoSodio;
	}

	public void setQtidadeHipocloritoSodio(String qtidadeHipocloritoSodio){

		this.qtidadeHipocloritoSodio = qtidadeHipocloritoSodio;
	}

	public String getQtidadeFluor(){

		return qtidadeFluor;
	}

	public void setQtidadeFluor(String qtidadeFluor){

		this.qtidadeFluor = qtidadeFluor;
	}

	public String getQtidadeHorasParalisadas(){

		return qtidadeHorasParalisadas;
	}

	public void setQtidadeHorasParalisadas(String qtidadeHorasParalisadas){

		this.qtidadeHorasParalisadas = qtidadeHorasParalisadas;
	}

	public String getUsuarioManutencao(){

		return usuarioManutencao;
	}

	public void setUsuarioManutencao(String usuarioManutencao){

		this.usuarioManutencao = usuarioManutencao;
	}

	public String getDistritoOperacional(){

		return distritoOperacional;
	}

	public void setDistritoOperacional(String distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getAtualizar(){

		return atualizar;
	}

	public void setAtualizar(String atualizar){

		this.atualizar = atualizar;
	}

	public void setFormValues(DadoDistritoOperacional dadoDistritoOperacionalAtual){

		dadoDistritoOperacionalAtual.setAnoMesReferencia(Util.formatarMesAnoComBarraParaAnoMes(getAnoMesReferencia()));

		dadoDistritoOperacionalAtual.setUnidadeCapacidade(Util.formatarStringParaBigDecimal(getUnidadeCapacidade().replace(",", ".")));

		dadoDistritoOperacionalAtual.setAducaoCapacidade(Util.formatarStringParaBigDecimal(getAducaoCapacidade().replace(",", ".")));

		dadoDistritoOperacionalAtual.setVolumeCapacidade(Util.formatarStringParaBigDecimal(getVolumeCapacidade().replace(",", ".")));

		dadoDistritoOperacionalAtual.setExtensaoAdutora(Util.formatarStringParaBigDecimal(getExtensaoAdutora().replace(",", ".")));

		dadoDistritoOperacionalAtual.setDiametroAdutora(Util.formatarStringParaBigDecimal(getDiametroAdutora().replace(",", ".")));

		MaterialRedeAgua materialRamalAgua = new MaterialRedeAgua();
		materialRamalAgua.setId(Util.converterStringParaInteger(getMaterialAdutora()));
		dadoDistritoOperacionalAtual.setMaterialAdutora(materialRamalAgua);

		dadoDistritoOperacionalAtual.setDemandaEnergia(Util.formatarStringParaBigDecimal(getDemandaEnergia().replace(",", ".")));

		dadoDistritoOperacionalAtual.setQtidadeSulfatoAluminio(Util.formatarStringParaBigDecimal(getQtidadeSulfatoAluminio().replace(",",
						".")));

		dadoDistritoOperacionalAtual.setQtidadeCloroGasoso(Util.formatarStringParaBigDecimal(getQtidadeCloroGasoso().replace(",", ".")));

		dadoDistritoOperacionalAtual.setQtidadeHipocloritoSodio(Util.formatarStringParaBigDecimal(getQtidadeHipocloritoSodio().replace(",",
						".")));

		dadoDistritoOperacionalAtual.setQtidadeFluor(Util.formatarStringParaBigDecimal(getQtidadeFluor().replace(",", ".")));

		dadoDistritoOperacionalAtual.setQtidadeHorasParalisadas(Util.formatarStringParaBigDecimal(getQtidadeHorasParalisadas().replace(",",
						".")));
		;
	}

	public void setValuesForm(DadoDistritoOperacional dadoDistritoOperacionalAtual){

		this.setAnoMesReferencia(Util.formatarAnoMesParaMesAno(dadoDistritoOperacionalAtual.getAnoMesReferencia()));

		this.setUnidadeCapacidade(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getUnidadeCapacidade(), 4, false));

		this.setAducaoCapacidade(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getAducaoCapacidade(), 4, false));

		this.setVolumeCapacidade(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getVolumeCapacidade(), 4, false));

		this.setExtensaoAdutora(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getExtensaoAdutora(), 4, false));

		this.setDiametroAdutora(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getDiametroAdutora(), 4, false));

		this.setMaterialAdutora(dadoDistritoOperacionalAtual.getMaterialAdutora().getId().toString());

		this.setDemandaEnergia(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getDemandaEnergia(), 4, false));

		this.setQtidadeSulfatoAluminio(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getQtidadeSulfatoAluminio(), 4, false));

		this.setQtidadeCloroGasoso(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getQtidadeCloroGasoso(), 4, false));

		this.setQtidadeHipocloritoSodio(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getQtidadeHipocloritoSodio(), 4, false));

		this.setQtidadeFluor(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getQtidadeFluor(), 4, false));

		this.setQtidadeHorasParalisadas(Util.formataBigDecimal(dadoDistritoOperacionalAtual.getQtidadeHorasParalisadas(), 2, false));
	}

	public void limparForm(){

		this.setAnoMesReferencia("");
		this.setUnidadeCapacidade("");
		this.setAducaoCapacidade("");
		this.setVolumeCapacidade("");
		this.setExtensaoAdutora("");
		this.setDiametroAdutora("");
		this.setMaterialAdutora("");
		this.setDemandaEnergia("");
		this.setQtidadeSulfatoAluminio("");
		this.setQtidadeCloroGasoso("");
		this.setQtidadeHipocloritoSodio("");
		this.setQtidadeFluor("");
		this.setQtidadeHorasParalisadas("");
		this.setAtualizar(null);
	}

	public void setFormValues(DadoDistritoOperacional dadoDistritoOperacionalAtual, DadoDistritoOperacional dadoDistritoOperacionalAnterior){

		dadoDistritoOperacionalAtual.setAnoMesReferencia(Util.formatarMesAnoComBarraParaAnoMes(getAnoMesReferencia()));

		if(GenericValidator.isBlankOrNull(getUnidadeCapacidade())){
			dadoDistritoOperacionalAtual.setUnidadeCapacidade(dadoDistritoOperacionalAnterior.getUnidadeCapacidade());
		}else{
			dadoDistritoOperacionalAtual.setUnidadeCapacidade(Util.formatarStringParaBigDecimal(getUnidadeCapacidade().replace(",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getAducaoCapacidade())){
			dadoDistritoOperacionalAtual.setAducaoCapacidade(dadoDistritoOperacionalAnterior.getAducaoCapacidade());
		}else{
			dadoDistritoOperacionalAtual.setAducaoCapacidade(Util.formatarStringParaBigDecimal(getAducaoCapacidade().replace(",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getVolumeCapacidade())){
			dadoDistritoOperacionalAtual.setVolumeCapacidade(dadoDistritoOperacionalAnterior.getVolumeCapacidade());
		}else{
			dadoDistritoOperacionalAtual.setVolumeCapacidade(Util.formatarStringParaBigDecimal(getVolumeCapacidade().replace(",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getExtensaoAdutora())){
			dadoDistritoOperacionalAtual.setExtensaoAdutora(dadoDistritoOperacionalAnterior.getExtensaoAdutora());
		}else{
			dadoDistritoOperacionalAtual.setExtensaoAdutora(Util.formatarStringParaBigDecimal(getExtensaoAdutora().replace(",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getDiametroAdutora())){
			dadoDistritoOperacionalAtual.setDiametroAdutora(dadoDistritoOperacionalAnterior.getDiametroAdutora());
		}else{
			dadoDistritoOperacionalAtual.setDiametroAdutora(Util.formatarStringParaBigDecimal(getDiametroAdutora().replace(",", ".")));
		}

		if(Util.obterInteger(getMaterialAdutora()).intValue() == ConstantesSistema.INVALIDO_ID){
			dadoDistritoOperacionalAtual.setMaterialAdutora(dadoDistritoOperacionalAnterior.getMaterialAdutora());
		}else{
			MaterialRedeAgua materialRamalAgua = new MaterialRedeAgua();
			materialRamalAgua.setId(Util.converterStringParaInteger(getMaterialAdutora()));
			dadoDistritoOperacionalAtual.setMaterialAdutora(materialRamalAgua);
		}

		if(GenericValidator.isBlankOrNull(getDemandaEnergia())){
			dadoDistritoOperacionalAtual.setDemandaEnergia(dadoDistritoOperacionalAnterior.getDemandaEnergia());
		}else{
			dadoDistritoOperacionalAtual.setDemandaEnergia(Util.formatarStringParaBigDecimal(getDemandaEnergia().replace(",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getQtidadeSulfatoAluminio())){
			dadoDistritoOperacionalAtual.setQtidadeSulfatoAluminio(dadoDistritoOperacionalAnterior.getQtidadeSulfatoAluminio());
		}else{
			dadoDistritoOperacionalAtual.setQtidadeSulfatoAluminio(Util.formatarStringParaBigDecimal(getQtidadeSulfatoAluminio().replace(
							",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getQtidadeCloroGasoso())){
			dadoDistritoOperacionalAtual.setQtidadeCloroGasoso(dadoDistritoOperacionalAnterior.getQtidadeCloroGasoso());
		}else{
			dadoDistritoOperacionalAtual
							.setQtidadeCloroGasoso(Util.formatarStringParaBigDecimal(getQtidadeCloroGasoso().replace(",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getQtidadeHipocloritoSodio())){
			dadoDistritoOperacionalAtual.setQtidadeHipocloritoSodio(dadoDistritoOperacionalAnterior.getQtidadeHipocloritoSodio());
		}else{
			dadoDistritoOperacionalAtual.setQtidadeHipocloritoSodio(Util.formatarStringParaBigDecimal(getQtidadeHipocloritoSodio().replace(
							",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getQtidadeFluor())){
			dadoDistritoOperacionalAtual.setQtidadeFluor(dadoDistritoOperacionalAnterior.getQtidadeFluor());
		}else{
			dadoDistritoOperacionalAtual.setQtidadeFluor(Util.formatarStringParaBigDecimal(getQtidadeFluor().replace(",", ".")));
		}

		if(GenericValidator.isBlankOrNull(getQtidadeHorasParalisadas())){
			dadoDistritoOperacionalAtual.setQtidadeHorasParalisadas(dadoDistritoOperacionalAnterior.getQtidadeHorasParalisadas());
		}else{
			dadoDistritoOperacionalAtual.setQtidadeHorasParalisadas(Util.formatarStringParaBigDecimal(getQtidadeHorasParalisadas().replace(
							",", ".")));
		}

	}

}
