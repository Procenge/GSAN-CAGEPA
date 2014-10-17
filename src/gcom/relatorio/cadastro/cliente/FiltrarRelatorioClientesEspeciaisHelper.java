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

package gcom.relatorio.cadastro.cliente;

import java.util.Date;

/**
 * [UC0591] - Gerar Relatório de Clientes Especiais
 * Classe que irá auxiliar no formato de entrada dos parametros
 * da pesquisa
 * 
 * @author Anderson Italo
 * @date 11/07/2011
 */
public class FiltrarRelatorioClientesEspeciaisHelper {

	private static final long serialVersionUID = 1L;

	private String idUnidadeNegocio;

	private String idGerenciaRegional;

	private String idLocalidadeInicial;

	private String idLocalidadeFinal;

	private String[] idsPerfilImovel;

	private String[] idsCategoria;

	private String[] idsSubcategoria;

	private String idSituacaoAgua;

	private String idSituacaoEsgoto;

	private String qtdeEconomiasInicial;

	private String qtdeEconomiasFinal;

	private String intervaloConsumoAguaInicial;

	private String intervaloConsumoAguaFinal;

	private String intervaloConsumoEsgotoInicial;

	private String intervaloConsumoEsgotoFinal;

	private String idClienteResponsavel;

	private String intervaloConsumoResponsavelInicial;

	private String intervaloConsumoResponsavelFinal;

	private Date dataInstalacaoHidrometroInicial;

	private Date dataInstalacaoHidrometroFinal;

	private String[] idsCapacidadesHidrometro;

	private String[] idsTarifasConsumo;

	private Integer anoMesFaturamento;

	private String idLeituraAnormalidade;

	private String leituraAnormalidade;

	private String idConsumoAnormalidade;

	private String consumoAnormalidade;

	private String[] idsClienteTipoEspecial;


	public FiltrarRelatorioClientesEspeciaisHelper() {

	}

	public FiltrarRelatorioClientesEspeciaisHelper(String idUnidadeNegocio, String idGerenciaRegional, String idLocalidadeInicial,
													String idLocalidadeFinal, String[] idsPerfilImovel, String[] idsCategoria,
													String[] idsSubcategoria, String idSituacaoAgua, String idSituacaoEsgoto,
													String qtdeEconomiasInicial, String qtdeEconomiasFinal,
													String intervaloConsumoAguaInicial, String intervaloConsumoAguaFinal,
													String intervaloConsumoEsgotoInicial, String intervaloConsumoEsgotoFinal,
													String idClienteResponsavel, String intervaloConsumoResponsavelInicial,
													String intervaloConsumoResponsavelFinal, Date dataInstalacaoHidrometroInicial,
													Date dataInstalacaoHidrometroFinal, String[] idsCapacidadesHidrometro,
													String[] idsTarifasConsumo, Integer anoMesFaturamento, String idLeituraAnormalidade,
													String leituraAnormalidade, String idConsumoAnormalidade, String consumoAnormalidade,
													String[] idsClienteTipoEspecial) {

		super();
		this.idUnidadeNegocio = idUnidadeNegocio;
		this.idGerenciaRegional = idGerenciaRegional;
		this.idLocalidadeInicial = idLocalidadeInicial;
		this.idLocalidadeFinal = idLocalidadeFinal;
		this.idsPerfilImovel = idsPerfilImovel;
		this.idsCategoria = idsCategoria;
		this.idsSubcategoria = idsSubcategoria;
		this.idSituacaoAgua = idSituacaoAgua;
		this.idSituacaoEsgoto = idSituacaoEsgoto;
		this.qtdeEconomiasInicial = qtdeEconomiasInicial;
		this.qtdeEconomiasFinal = qtdeEconomiasFinal;
		this.intervaloConsumoAguaInicial = intervaloConsumoAguaInicial;
		this.intervaloConsumoAguaFinal = intervaloConsumoAguaFinal;
		this.intervaloConsumoEsgotoInicial = intervaloConsumoEsgotoInicial;
		this.intervaloConsumoEsgotoFinal = intervaloConsumoEsgotoFinal;
		this.idClienteResponsavel = idClienteResponsavel;
		this.intervaloConsumoResponsavelInicial = intervaloConsumoResponsavelInicial;
		this.intervaloConsumoResponsavelFinal = intervaloConsumoResponsavelFinal;
		this.dataInstalacaoHidrometroInicial = dataInstalacaoHidrometroInicial;
		this.dataInstalacaoHidrometroFinal = dataInstalacaoHidrometroFinal;
		this.idsCapacidadesHidrometro = idsCapacidadesHidrometro;
		this.idsTarifasConsumo = idsTarifasConsumo;
		this.anoMesFaturamento = anoMesFaturamento;
		this.idLeituraAnormalidade = idLeituraAnormalidade;
		this.leituraAnormalidade = leituraAnormalidade;
		this.idConsumoAnormalidade = idConsumoAnormalidade;
		this.consumoAnormalidade = consumoAnormalidade;
		this.idsClienteTipoEspecial = idsClienteTipoEspecial;
	}

	public String getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(String idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public String getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(String idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public String getIdLocalidadeInicial(){

		return idLocalidadeInicial;
	}

	public void setIdLocalidadeInicial(String idLocalidadeInicial){

		this.idLocalidadeInicial = idLocalidadeInicial;
	}

	public String getIdLocalidadeFinal(){

		return idLocalidadeFinal;
	}

	public void setIdLocalidadeFinal(String idLocalidadeFinal){

		this.idLocalidadeFinal = idLocalidadeFinal;
	}

	public String[] getIdsPerfilImovel(){

		return idsPerfilImovel;
	}

	public void setIdsPerfilImovel(String[] idsPerfilImovel){

		this.idsPerfilImovel = idsPerfilImovel;
	}

	public String[] getIdsCategoria(){

		return idsCategoria;
	}

	public void setIdsCategoria(String[] idsCategoria){

		this.idsCategoria = idsCategoria;
	}

	public String[] getIdsSubcategoria(){

		return idsSubcategoria;
	}

	public void setIdsSubcategoria(String[] idsSubcategoria){

		this.idsSubcategoria = idsSubcategoria;
	}

	public String getIdSituacaoAgua(){

		return idSituacaoAgua;
	}

	public void setIdSituacaoAgua(String idSituacaoAgua){

		this.idSituacaoAgua = idSituacaoAgua;
	}

	public String getIdSituacaoEsgoto(){

		return idSituacaoEsgoto;
	}

	public void setIdSituacaoEsgoto(String idSituacaoEsgoto){

		this.idSituacaoEsgoto = idSituacaoEsgoto;
	}

	public String getQtdeEconomiasInicial(){

		return qtdeEconomiasInicial;
	}

	public void setQtdeEconomiasInicial(String qtdeEconomiasInicial){

		this.qtdeEconomiasInicial = qtdeEconomiasInicial;
	}

	public String getQtdeEconomiasFinal(){

		return qtdeEconomiasFinal;
	}

	public void setQtdeEconomiasFinal(String qtdeEconomiasFinal){

		this.qtdeEconomiasFinal = qtdeEconomiasFinal;
	}

	public String getIntervaloConsumoAguaInicial(){

		return intervaloConsumoAguaInicial;
	}

	public void setIntervaloConsumoAguaInicial(String intervaloConsumoAguaInicial){

		this.intervaloConsumoAguaInicial = intervaloConsumoAguaInicial;
	}

	public String getIntervaloConsumoAguaFinal(){

		return intervaloConsumoAguaFinal;
	}

	public void setIntervaloConsumoAguaFinal(String intervaloConsumoAguaFinal){

		this.intervaloConsumoAguaFinal = intervaloConsumoAguaFinal;
	}

	public String getIntervaloConsumoEsgotoInicial(){

		return intervaloConsumoEsgotoInicial;
	}

	public void setIntervaloConsumoEsgotoInicial(String intervaloConsumoEsgotoInicial){

		this.intervaloConsumoEsgotoInicial = intervaloConsumoEsgotoInicial;
	}

	public String getIntervaloConsumoEsgotoFinal(){

		return intervaloConsumoEsgotoFinal;
	}

	public void setIntervaloConsumoEsgotoFinal(String intervaloConsumoEsgotoFinal){

		this.intervaloConsumoEsgotoFinal = intervaloConsumoEsgotoFinal;
	}

	public String getIdClienteResponsavel(){

		return idClienteResponsavel;
	}

	public void setIdClienteResponsavel(String idClienteResponsavel){

		this.idClienteResponsavel = idClienteResponsavel;
	}

	public String getIntervaloConsumoResponsavelInicial(){

		return intervaloConsumoResponsavelInicial;
	}

	public void setIntervaloConsumoResponsavelInicial(String intervaloConsumoResponsavelInicial){

		this.intervaloConsumoResponsavelInicial = intervaloConsumoResponsavelInicial;
	}

	public String getIntervaloConsumoResponsavelFinal(){

		return intervaloConsumoResponsavelFinal;
	}

	public void setIntervaloConsumoResponsavelFinal(String intervaloConsumoResponsavelFinal){

		this.intervaloConsumoResponsavelFinal = intervaloConsumoResponsavelFinal;
	}

	public Date getDataInstalacaoHidrometroInicial(){

		return dataInstalacaoHidrometroInicial;
	}

	public void setDataInstalacaoHidrometroInicial(Date dataInstalacaoHidrometroInicial){

		this.dataInstalacaoHidrometroInicial = dataInstalacaoHidrometroInicial;
	}

	public Date getDataInstalacaoHidrometroFinal(){

		return dataInstalacaoHidrometroFinal;
	}

	public void setDataInstalacaoHidrometroFinal(Date dataInstalacaoHidrometroFinal){

		this.dataInstalacaoHidrometroFinal = dataInstalacaoHidrometroFinal;
	}

	public String[] getIdsCapacidadesHidrometro(){

		return idsCapacidadesHidrometro;
	}

	public void setIdsCapacidadesHidrometro(String[] idsCapacidadesHidrometro){

		this.idsCapacidadesHidrometro = idsCapacidadesHidrometro;
	}

	public String[] getIdsTarifasConsumo(){

		return idsTarifasConsumo;
	}

	public void setIdsTarifasConsumo(String[] idsTarifasConsumo){

		this.idsTarifasConsumo = idsTarifasConsumo;
	}

	public Integer getAnoMesFaturamento(){

		return anoMesFaturamento;
	}

	public void setAnoMesFaturamento(Integer anoMesFaturamento){

		this.anoMesFaturamento = anoMesFaturamento;
	}

	public String getIdLeituraAnormalidade(){

		return idLeituraAnormalidade;
	}

	public void setIdLeituraAnormalidade(String idLeituraAnormalidade){

		this.idLeituraAnormalidade = idLeituraAnormalidade;
	}

	public String getLeituraAnormalidade(){

		return leituraAnormalidade;
	}

	public void setLeituraAnormalidade(String leituraAnormalidade){

		this.leituraAnormalidade = leituraAnormalidade;
	}

	public String getIdConsumoAnormalidade(){

		return idConsumoAnormalidade;
	}

	public void setIdConsumoAnormalidade(String idConsumoAnormalidade){

		this.idConsumoAnormalidade = idConsumoAnormalidade;
	}

	public String getConsumoAnormalidade(){

		return consumoAnormalidade;
	}

	public void setConsumoAnormalidade(String consumoAnormalidade){

		this.consumoAnormalidade = consumoAnormalidade;
	}

	public String[] getIdsClienteTipoEspecial(){

		return idsClienteTipoEspecial;
	}

	public void setIdsClienteTipoEspecial(String[] idsClienteTipoEspecial){

		this.idsClienteTipoEspecial = idsClienteTipoEspecial;
	}

}
