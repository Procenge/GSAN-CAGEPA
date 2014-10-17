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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.cobranca.campanhapremiacao;


import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

public class CampanhaPremio
				extends ObjetoTransacao
				implements Comparable<CampanhaPremio> {

	private static final long serialVersionUID = 1L;

	public static final int TIPO_UNID_PREM_GERENCIA_REGIONAL = 1;

	public static final int TIPO_UNID_PREM_UNIDADE_NEGOCIO = 2;

	public static final int TIPO_UNID_PREM_ELO = 3;

	public static final int TIPO_UNID_PREM_LOCALIDADE = 4;

	public static final int TIPO_UNID_PREM_NENHUM = 5;

	private Integer id;

	private String descricao;
	
	private Integer quantidadePremio;
	
	private Integer numeroOrdemPremiacao;
	
	private Integer quantidadePremioSorteada;
	
	private GerenciaRegional gerenciaRegional; 
	
	private UnidadeNegocio unidadeNegocio;
	
	private Localidade eloPremio;
	
	private Localidade localidade;

	private Campanha campanha;

	private Date ultimaAlteracao;
	
	
	public CampanhaPremio() {

		// TODO Auto-generated constructor stub
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public boolean equals(Object objeto){

		try{

			return ((CampanhaPremio) objeto).getCampanha().getId().equals(this.getCampanha().getId());

		}catch(ClassCastException ex){
			return false;
		}
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Campanha getCampanha(){

		return campanha;
	}

	public void setCampanha(Campanha campanha){

		this.campanha = campanha;
	}


	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}


	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	
	public String getDescricao(){
	
		return descricao;
	}

	
	public void setDescricao(String descricao){
	
		this.descricao = descricao;
	}

	
	public Integer getQuantidadePremio(){
	
		return quantidadePremio;
	}

	
	public void setQuantidadePremio(Integer quantidadePremio){
	
		this.quantidadePremio = quantidadePremio;
	}

	
	public Integer getNumeroOrdemPremiacao(){
	
		return numeroOrdemPremiacao;
	}

	
	public void setNumeroOrdemPremiacao(Integer numeroOrdemPremiacao){
	
		this.numeroOrdemPremiacao = numeroOrdemPremiacao;
	}

	
	public Integer getQuantidadePremioSorteada(){
	
		return quantidadePremioSorteada;
	}

	
	public void setQuantidadePremioSorteada(Integer quantidadePremioSorteada){
	
		this.quantidadePremioSorteada = quantidadePremioSorteada;
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

	public Localidade getLocalidade(){
	
		return localidade;
	}

	
	public void setLocalidade(Localidade localidade){
	
		this.localidade = localidade;
	}

	public Localidade getEloPremio(){

		return eloPremio;
	}

	public void setEloPremio(Localidade eloPremio){

		this.eloPremio = eloPremio;
	}

	public String getDescricaoAbreviada(){

		String retorno = descricao;

		if(descricao.length() > 10){
			retorno = retorno.substring(0, 10);
		}

		return retorno;
	}

	public int compareTo(CampanhaPremio o){

		int retorno = 0;

		if(gerenciaRegional == null && o.getGerenciaRegional() != null){
			retorno = -1;
		}else if(gerenciaRegional != null && o.getGerenciaRegional() == null){
			retorno = 1;
		}else if(gerenciaRegional != null && o.getGerenciaRegional() != null){
			retorno = gerenciaRegional.getId().compareTo(o.getGerenciaRegional().getId());
		}

		if(retorno == 0){
			if(unidadeNegocio == null && o.getUnidadeNegocio() != null){
				retorno = -1;
			}else if(unidadeNegocio != null && o.getUnidadeNegocio() == null){
				retorno = 1;
			}else if(unidadeNegocio != null && o.getUnidadeNegocio() != null){
				retorno = unidadeNegocio.getId().compareTo(o.getUnidadeNegocio().getId());
			}
		}

		if(retorno == 0){
			if(eloPremio == null && o.getEloPremio() != null){
				retorno = -1;
			}else if(eloPremio != null && o.getEloPremio() == null){
				retorno = 1;
			}else if(eloPremio != null && o.getEloPremio() != null){
				retorno = eloPremio.getId().compareTo(o.getEloPremio().getId());
			}
		}

		if(retorno == 0){
			if(localidade == null && o.getLocalidade() != null){
				retorno = -1;
			}else if(localidade != null && o.getLocalidade() == null){
				retorno = 1;
			}else if(localidade != null && o.getLocalidade() != null){
				retorno = localidade.getId().compareTo(o.getLocalidade().getId());
			}
		}

		if(retorno == 0){
			if(numeroOrdemPremiacao == null && o.getNumeroOrdemPremiacao() != null){
				retorno = -1;
			}else if(numeroOrdemPremiacao != null && o.getNumeroOrdemPremiacao() == null){
				retorno = 1;
			}else if(numeroOrdemPremiacao != null && o.getNumeroOrdemPremiacao() != null){
				retorno = numeroOrdemPremiacao.compareTo(o.getNumeroOrdemPremiacao());
			}
		}

		return retorno;
	}

	public static boolean mudouGrupo(CampanhaPremio campanhaPremio, CampanhaPremio campanhaPremioAnterior){

		boolean retorno = false;

		if(campanhaPremioAnterior == null){
			return true;
		}

		if(campanhaPremio.getGerenciaRegional() != null){
			if(campanhaPremioAnterior.getGerenciaRegional() == null
							|| !campanhaPremio.getGerenciaRegional().getId().equals(campanhaPremioAnterior.getGerenciaRegional().getId())){
				retorno = true;
			}

		}else if(campanhaPremio.getUnidadeNegocio() != null){
			if(campanhaPremioAnterior.getUnidadeNegocio() == null
							|| !campanhaPremio.getUnidadeNegocio().getId().equals(campanhaPremioAnterior.getUnidadeNegocio().getId())){
				retorno = true;
			}

		}else if(campanhaPremio.getEloPremio() != null){
			if(campanhaPremioAnterior.getEloPremio() == null
							|| !campanhaPremio.getEloPremio().getId().equals(campanhaPremioAnterior.getEloPremio().getId())){
				retorno = true;
			}

		}else if(campanhaPremio.getLocalidade() != null){
			if(campanhaPremioAnterior.getLocalidade() == null
							|| !campanhaPremio.getLocalidade().getId().equals(campanhaPremioAnterior.getLocalidade().getId())){
				retorno = true;
			}

		}else{
			if(campanhaPremioAnterior.getGerenciaRegional() != null || campanhaPremioAnterior.getUnidadeNegocio() != null
							|| campanhaPremioAnterior.getEloPremio() != null || campanhaPremioAnterior.getLocalidade() != null){
				retorno = true;
			}
		}

		return retorno;
	}

}
