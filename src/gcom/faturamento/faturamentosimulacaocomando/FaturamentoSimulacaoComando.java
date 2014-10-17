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

package gcom.faturamento.faturamentosimulacaocomando;

import gcom.cadastro.localidade.*;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.interceptor.ObjetoGcom;
import gcom.micromedicao.Rota;
import gcom.util.Util;

import java.util.Date;

public class FaturamentoSimulacaoComando
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricaoTitulo;

	private Date dataComando;

	private Date dataRealizacao;

	private Date ultimaAlteracao;

	private Short loteInicial;

	private Short loteFinal;

	private Short codigoTipoConsumoAgua;

	private Short codigoTipoConsumoEsgoto;

	private FaturamentoGrupo faturamentoGrupo;

	private GerenciaRegional gerenciaRegional;

	private UnidadeNegocio unidadeNegocio;

	private Localidade localidadeInicial;

	private Localidade localidadeFinal;

	private SetorComercial setorComercialInicial;

	private SetorComercial setorComercialFinal;

	private Quadra quadraInicial;

	private Quadra quadraFinal;

	private Rota rotaInicial;

	private Rota rotaFinal;

	private ConsumoTarifa consumoTarifa;

	// constantes
	public final static Short CONSUMO_ANTERIOR = Short.valueOf("1");

	public final static Short CONSUMO_MINIMO = Short.valueOf("2");

	public final static Short CONSUMO_MEDIO = Short.valueOf("3");

	public FaturamentoSimulacaoComando() {

	}

	public FaturamentoSimulacaoComando(Integer id, String descricaoTitulo, Date dataComando, Date ultimaAlteracao, Short loteInicial,
										Short loteFinal, Short codigoTipoConsumoAgua, Short codigoTipoConsumoEsgoto,
										FaturamentoGrupo faturamentoGrupo, GerenciaRegional gerenciaRegional,
										UnidadeNegocio unidadeNegocio, Localidade localidadeInicial, Localidade localidadeFinal,
										SetorComercial setorComercialInicial, SetorComercial setorComercialFinal, Quadra quadraInicial,
										Quadra quadraFinal, Rota rotaInicial, Rota rotaFinal, ConsumoTarifa consumoTarifa) {

		super();
		this.id = id;
		this.descricaoTitulo = descricaoTitulo;
		this.dataComando = dataComando;
		this.ultimaAlteracao = ultimaAlteracao;
		this.loteInicial = loteInicial;
		this.loteFinal = loteFinal;
		this.codigoTipoConsumoAgua = codigoTipoConsumoAgua;
		this.codigoTipoConsumoEsgoto = codigoTipoConsumoEsgoto;
		this.faturamentoGrupo = faturamentoGrupo;
		this.gerenciaRegional = gerenciaRegional;
		this.unidadeNegocio = unidadeNegocio;
		this.localidadeInicial = localidadeInicial;
		this.localidadeFinal = localidadeFinal;
		this.setorComercialInicial = setorComercialInicial;
		this.setorComercialFinal = setorComercialFinal;
		this.quadraInicial = quadraInicial;
		this.quadraFinal = quadraFinal;
		this.rotaInicial = rotaInicial;
		this.rotaFinal = rotaFinal;
		this.consumoTarifa = consumoTarifa;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricaoTitulo(){

		return descricaoTitulo;
	}

	public void setDescricaoTitulo(String descricaoTitulo){

		this.descricaoTitulo = descricaoTitulo;
	}

	public Date getDataComando(){

		return dataComando;
	}

	public void setDataComando(Date dataComando){

		this.dataComando = dataComando;
	}

	public Date getDataRealizacao(){

		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao){

		this.dataRealizacao = dataRealizacao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Short getLoteInicial(){

		return loteInicial;
	}

	public void setLoteInicial(Short loteInicial){

		this.loteInicial = loteInicial;
	}

	public Short getLoteFinal(){

		return loteFinal;
	}

	public void setLoteFinal(Short loteFinal){

		this.loteFinal = loteFinal;
	}

	public Short getCodigoTipoConsumoAgua(){

		return codigoTipoConsumoAgua;
	}

	public void setCodigoTipoConsumoAgua(Short codigoTipoConsumoAgua){

		this.codigoTipoConsumoAgua = codigoTipoConsumoAgua;
	}

	public Short getCodigoTipoConsumoEsgoto(){

		return codigoTipoConsumoEsgoto;
	}

	public void setCodigoTipoConsumoEsgoto(Short codigoTipoConsumoEsgoto){

		this.codigoTipoConsumoEsgoto = codigoTipoConsumoEsgoto;
	}

	public FaturamentoGrupo getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(FaturamentoGrupo faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public GerenciaRegional getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public UnidadeNegocio getUnidadeNegocio(){

		return unidadeNegocio;
	}

	public void setUnidadeNegocio(UnidadeNegocio unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	public Localidade getLocalidadeInicial(){

		return localidadeInicial;
	}

	public void setLocalidadeInicial(Localidade localidadeInicial){

		this.localidadeInicial = localidadeInicial;
	}

	public Localidade getLocalidadeFinal(){

		return localidadeFinal;
	}

	public void setLocalidadeFinal(Localidade localidadeFinal){

		this.localidadeFinal = localidadeFinal;
	}

	public SetorComercial getSetorComercialInicial(){

		return setorComercialInicial;
	}

	public void setSetorComercialInicial(SetorComercial setorComercialInicial){

		this.setorComercialInicial = setorComercialInicial;
	}

	public SetorComercial getSetorComercialFinal(){

		return setorComercialFinal;
	}

	public void setSetorComercialFinal(SetorComercial setorComercialFinal){

		this.setorComercialFinal = setorComercialFinal;
	}

	public Quadra getQuadraInicial(){

		return quadraInicial;
	}

	public void setQuadraInicial(Quadra quadraInicial){

		this.quadraInicial = quadraInicial;
	}

	public Quadra getQuadraFinal(){

		return quadraFinal;
	}

	public void setQuadraFinal(Quadra quadraFinal){

		this.quadraFinal = quadraFinal;
	}

	public Rota getRotaInicial(){

		return rotaInicial;
	}

	public void setRotaInicial(Rota rotaInicial){

		this.rotaInicial = rotaInicial;
	}

	public Rota getRotaFinal(){

		return rotaFinal;
	}

	public void setRotaFinal(Rota rotaFinal){

		this.rotaFinal = rotaFinal;
	}

	public ConsumoTarifa getConsumoTarifa(){

		return consumoTarifa;
	}

	public void setConsumoTarifa(ConsumoTarifa consumoTarifa){

		this.consumoTarifa = consumoTarifa;
	}

	public String getDataComandoFormatada(){

		String retorno = "";

		if(dataComando != null){

			retorno = Util.formatarData(dataComando);
		}

		return retorno;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

}
