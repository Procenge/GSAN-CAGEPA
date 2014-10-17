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

package gcom.micromedicao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.micromedicao.Rota;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Ailton Sousa
 * @data 11/08/2011
 */
public class ResumoLigacoesEconomia
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Integer anoMesReferencia;

	/** persistent field */
	private Short indicadorHidrometro;

	/** persistent field */
	private Short indicadorVolumeFixadoAgua;

	/** persistent field */
	private Short indicadorVolumeFixadoEsgoto;

	/** persistent field */
	private Short indicadorHidrometroPoco;

	/** persistent field */
	private Short indicadorPoco;

	/** persistent field */
	private Integer numeroQuadra;

	/** persistent field */
	private Integer cdSetorComercial;

	/** persistent field */
	private Integer quantidadeLigacoes;

	/** persistent field */
	private Integer quantidadeEconomias;

	/** persistent field */
	private Integer quantidadeLigacoesNovasAgua;

	/** persistent field */
	private Integer quantidadeLigacoesNovasEsgoto;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private GerenciaRegional gerenciaRegional;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private SetorComercial setorComercial;

	/** persistent field */
	private Rota rota;

	/** persistent field */
	private Quadra quadra;

	/** persistent field */
	private ImovelPerfil imovelPerfil;

	/** persistent field */
	private LigacaoAguaSituacao ligacaoAguaSituacao;

	/** persistent field */
	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;

	/** persistent field */
	private Categoria categoria;

	/** persistent field */
	private EsferaPoder esferaPoder;

	/** persistent field */
	private UnidadeNegocio unidadeNegocio;

	/** persistent field */
	private Localidade eloLocalidade;

	/** persistent field */
	private Subcategoria subcategoria;

	/** persistent field */
	private ClienteTipo clienteTipo;

	/** persistent field */
	private LigacaoAguaPerfil ligacaoAguaPerfil;

	/** persistent field */
	private LigacaoEsgotoPerfil ligacaoEsgotoPerfil;

	/** persistent field */
	private ConsumoTarifa consumoTarifa;

	/** full constructor */
	public ResumoLigacoesEconomia(Integer anoMesReferencia, Short indicadorHidrometro, Short indicadorVolumeFixadoAgua,
									Short indicadorVolumeFixadoEsgoto, Short indicadorHidrometroPoco, Short indicadorPoco,
									Integer numeroQuadra, Integer cdSetorComercial, Integer quantidadeLigacoes,
									Integer quantidadeEconomias, Integer quantidadeLigacoesNovasAgua,
									Integer quantidadeLigacoesNovasEsgoto, GerenciaRegional gerenciaRegional, Localidade localidade,
									SetorComercial setorComercial, Rota rota, Quadra quadra, ImovelPerfil imovelPerfil,
									LigacaoAguaSituacao ligacaoAguaSituacao, LigacaoEsgotoSituacao ligacaoEsgotoSituacao,
									Categoria categoria, EsferaPoder esferaPoder, UnidadeNegocio unidadeNegocio, Localidade eloLocalidade,
									Subcategoria subcategoria, ClienteTipo clienteTipo, LigacaoAguaPerfil ligacaoAguaPerfil,
									LigacaoEsgotoPerfil ligacaoEsgotoPerfil, ConsumoTarifa consumoTarifa) {

		this.anoMesReferencia = anoMesReferencia;
		this.indicadorHidrometro = indicadorHidrometro;
		this.indicadorVolumeFixadoAgua = indicadorVolumeFixadoAgua;
		this.indicadorVolumeFixadoEsgoto = indicadorVolumeFixadoEsgoto;
		this.indicadorHidrometroPoco = indicadorHidrometroPoco;
		this.indicadorPoco = indicadorPoco;
		this.numeroQuadra = numeroQuadra;
		this.cdSetorComercial = cdSetorComercial;
		this.quantidadeLigacoes = quantidadeLigacoes;
		this.quantidadeEconomias = quantidadeEconomias;
		this.quantidadeLigacoesNovasAgua = quantidadeLigacoesNovasAgua;
		this.quantidadeLigacoesNovasEsgoto = quantidadeLigacoesNovasEsgoto;
		this.gerenciaRegional = gerenciaRegional;
		this.localidade = localidade;
		this.setorComercial = setorComercial;
		this.rota = rota;
		this.quadra = quadra;
		this.imovelPerfil = imovelPerfil;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.categoria = categoria;
		this.esferaPoder = esferaPoder;
		this.unidadeNegocio = unidadeNegocio;
		this.eloLocalidade = eloLocalidade;
		this.subcategoria = subcategoria;
		this.clienteTipo = clienteTipo;
		this.ligacaoAguaPerfil = ligacaoAguaPerfil;
		this.ligacaoEsgotoPerfil = ligacaoEsgotoPerfil;
		this.consumoTarifa = consumoTarifa;
		this.ultimaAlteracao = new Date();
	}

	/** default constructor */
	public ResumoLigacoesEconomia() {

	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Short getIndicadorHidrometro(){

		return indicadorHidrometro;
	}

	public void setIndicadorHidrometro(Short indicadorHidrometro){

		this.indicadorHidrometro = indicadorHidrometro;
	}

	public Short getIndicadorVolumeFixadoAgua(){

		return indicadorVolumeFixadoAgua;
	}

	public void setIndicadorVolumeFixadoAgua(Short indicadorVolumeFixadoAgua){

		this.indicadorVolumeFixadoAgua = indicadorVolumeFixadoAgua;
	}

	public Short getIndicadorVolumeFixadoEsgoto(){

		return indicadorVolumeFixadoEsgoto;
	}

	public void setIndicadorVolumeFixadoEsgoto(Short indicadorVolumeFixadoEsgoto){

		this.indicadorVolumeFixadoEsgoto = indicadorVolumeFixadoEsgoto;
	}

	public Short getIndicadorHidrometroPoco(){

		return indicadorHidrometroPoco;
	}

	public void setIndicadorHidrometroPoco(Short indicadorHidrometroPoco){

		this.indicadorHidrometroPoco = indicadorHidrometroPoco;
	}

	public Short getIndicadorPoco(){

		return indicadorPoco;
	}

	public void setIndicadorPoco(Short indicadorPoco){

		this.indicadorPoco = indicadorPoco;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Integer getCdSetorComercial(){

		return cdSetorComercial;
	}

	public void setCdSetorComercial(Integer cdSetorComercial){

		this.cdSetorComercial = cdSetorComercial;
	}

	public Integer getQuantidadeLigacoes(){

		return quantidadeLigacoes;
	}

	public void setQuantidadeLigacoes(Integer quantidadeLigacoes){

		this.quantidadeLigacoes = quantidadeLigacoes;
	}

	public Integer getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	public void setQuantidadeEconomias(Integer quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	public Integer getQuantidadeLigacoesNovasAgua(){

		return quantidadeLigacoesNovasAgua;
	}

	public void setQuantidadeLigacoesNovasAgua(Integer quantidadeLigacoesNovasAgua){

		this.quantidadeLigacoesNovasAgua = quantidadeLigacoesNovasAgua;
	}

	public Integer getQuantidadeLigacoesNovasEsgoto(){

		return quantidadeLigacoesNovasEsgoto;
	}

	public void setQuantidadeLigacoesNovasEsgoto(Integer quantidadeLigacoesNovasEsgoto){

		this.quantidadeLigacoesNovasEsgoto = quantidadeLigacoesNovasEsgoto;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public GerenciaRegional getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public SetorComercial getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public Rota getRota(){

		return rota;
	}

	public void setRota(Rota rota){

		this.rota = rota;
	}

	public Quadra getQuadra(){

		return quadra;
	}

	public void setQuadra(Quadra quadra){

		this.quadra = quadra;
	}

	public ImovelPerfil getImovelPerfil(){

		return imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public EsferaPoder getEsferaPoder(){

		return esferaPoder;
	}

	public void setEsferaPoder(EsferaPoder esferaPoder){

		this.esferaPoder = esferaPoder;
	}

	public UnidadeNegocio getUnidadeNegocio(){

		return unidadeNegocio;
	}

	public void setUnidadeNegocio(UnidadeNegocio unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	public Localidade getEloLocalidade(){

		return eloLocalidade;
	}

	public void setEloLocalidade(Localidade eloLocalidade){

		this.eloLocalidade = eloLocalidade;
	}

	public Subcategoria getSubcategoria(){

		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria){

		this.subcategoria = subcategoria;
	}

	public ClienteTipo getClienteTipo(){

		return clienteTipo;
	}

	public void setClienteTipo(ClienteTipo clienteTipo){

		this.clienteTipo = clienteTipo;
	}

	public LigacaoAguaPerfil getLigacaoAguaPerfil(){

		return ligacaoAguaPerfil;
	}

	public void setLigacaoAguaPerfil(LigacaoAguaPerfil ligacaoAguaPerfil){

		this.ligacaoAguaPerfil = ligacaoAguaPerfil;
	}

	public LigacaoEsgotoPerfil getLigacaoEsgotoPerfil(){

		return ligacaoEsgotoPerfil;
	}

	public void setLigacaoEsgotoPerfil(LigacaoEsgotoPerfil ligacaoEsgotoPerfil){

		this.ligacaoEsgotoPerfil = ligacaoEsgotoPerfil;
	}

	public ConsumoTarifa getConsumoTarifa(){

		return consumoTarifa;
	}

	public void setConsumoTarifa(ConsumoTarifa consumoTarifa){

		this.consumoTarifa = consumoTarifa;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
