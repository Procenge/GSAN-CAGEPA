package gcom.arrecadacao;

import gcom.cadastro.imovel.Imovel;
import gcom.faturamento.FiltroContratoDemandaConsumo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

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
 * Copyright (C) <2014> 
 * Vicente Zarga
 */
public class ContratoDemandaConsumo
				extends ObjetoTransacao {

	private Integer id;

	private Imovel imovel;

	private Integer numeroContrato;

	private Integer anoMesFaturamentoInicio;

	private Integer anoMesFaturamentoFim;

	private ConsumoTarifa consumoTarifa;

	private Integer numeroConsumoFixo;

	private Integer indicacorEncerramento;

	private Date ultimaAlteracao;

	private String email;

	public ContratoDemandaConsumo(Integer id, Imovel imovel, Integer numeroContrato, Integer anoMesFaturamentoInicio,
			Integer anoMesFaturamentoFim, ConsumoTarifa consumoTarifa, Integer numeroConsumoFixo,
			Integer indicacorEncerramento, Date ultimaAlteracao, String email) {

		super();
		this.id = id;
		this.imovel = imovel;
		this.numeroContrato = numeroContrato;
		this.anoMesFaturamentoInicio = anoMesFaturamentoInicio;
		this.anoMesFaturamentoFim = anoMesFaturamentoFim;
		this.consumoTarifa = consumoTarifa;
		this.numeroConsumoFixo = numeroConsumoFixo;
		this.indicacorEncerramento = indicacorEncerramento;
		this.ultimaAlteracao = ultimaAlteracao;
		this.email = email;
	}

	public ContratoDemandaConsumo() {

	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public Integer getNumeroContrato(){

		return numeroContrato;
	}

	public void setNumeroContrato(Integer numeroContrato){

		this.numeroContrato = numeroContrato;
	}

	public Integer getAnoMesFaturamentoInicio(){

		return anoMesFaturamentoInicio;
	}

	public void setAnoMesFaturamentoInicio(Integer anoMesFaturamentoInicio){

		this.anoMesFaturamentoInicio = anoMesFaturamentoInicio;
	}

	public Integer getAnoMesFaturamentoFim(){

		return anoMesFaturamentoFim;
	}

	public void setAnoMesFaturamentoFim(Integer anoMesFaturamentoFim){

		this.anoMesFaturamentoFim = anoMesFaturamentoFim;
	}

	public ConsumoTarifa getConsumoTarifa(){

		return consumoTarifa;
	}

	public void setConsumoTarifa(ConsumoTarifa consumoTarifa){

		this.consumoTarifa = consumoTarifa;
	}

	public Integer getNumeroConsumoFixo(){

		return numeroConsumoFixo;
	}

	public void setNumeroConsumoFixo(Integer numeroConsumoFixo){

		this.numeroConsumoFixo = numeroConsumoFixo;
	}

	public Integer getIndicacorEncerramento(){

		return indicacorEncerramento;
	}

	public void setIndicacorEncerramento(Integer indicacorEncerramento){

		this.indicacorEncerramento = indicacorEncerramento;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public String getMesAnoFaturamentoInicio(){

		return Util.formatarAnoMesParaMesAno(anoMesFaturamentoInicio);
	}

	public String getMesAnoFaturamentoFim(){

		return Util.formatarAnoMesParaMesAno(anoMesFaturamentoFim);
	}

	public Filtro retornaFiltro(){

		FiltroContratoDemandaConsumo filtroContratoDemandaConsumo = new FiltroContratoDemandaConsumo();

		filtroContratoDemandaConsumo.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.ID, this.getId()));
		return filtroContratoDemandaConsumo;
	}


	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String getDataInicioFormatada(){

		String dataFormatada = null;
		if(this.anoMesFaturamentoInicio != null){

			String ano = anoMesFaturamentoInicio.toString().substring(0, 4);
			String mes = anoMesFaturamentoInicio.toString().substring(4, 6);

			dataFormatada = mes + "/" + ano;
		}
		return dataFormatada;
	}

	public String getDataFimFormatada(){

		String dataFormatada = null;
		if(this.anoMesFaturamentoFim != null){

			String ano = anoMesFaturamentoFim.toString().substring(0, 4);
			String mes = anoMesFaturamentoFim.toString().substring(4, 6);

			dataFormatada = mes + "/" + ano;

		}
		return dataFormatada;
	}

	public String getContratoEncerrado(){

		String contratoEncerrado = null;
		if(this.indicacorEncerramento != null){

			if(indicacorEncerramento.equals(ConstantesSistema.SIM.toString())){
				contratoEncerrado = "Sim";
			}else{
				contratoEncerrado = "Não";
			}
		}
		return contratoEncerrado;
	}

}
