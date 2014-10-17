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

package gcom.cadastro.localidade;

import gcom.cadastro.dadocensitario.IbgeSetorCensitario;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.imovel.Imovel;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.Rota;
import gcom.micromedicao.RoteiroEmpresa;
import gcom.operacional.Bacia;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.ZonaAbastecimento;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Quadra
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public final static Short COM_REDE = Short.valueOf("2");

	public final static Short SEM_REDE = Short.valueOf("1");

	public final static Short REDE_PARCIAL = Short.valueOf("3");

	/** identifier field */
	private Integer id;

	/** persistent field */
	private int numeroQuadra;

	/** persistent field */
	private Bairro bairro;

	/** nullable persistent field */
	private Short indicadorRedeAgua;

	/** nullable persistent field */
	private Short indicadorRedeEsgoto;

	/** nullable persistent field */
	private Short numeroRotaSequencia;

	/** nullable persistent field */
	private Date dataImplantacao;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private Rota rota;

	/** persistent field */
	private IbgeSetorCensitario ibgeSetorCensitario;

	/** persistent field */
	private gcom.cadastro.localidade.SetorComercial setorComercial;

	/** persistent field */
	private DistritoOperacional distritoOperacional;

	/** persistent field */
	private ZonaAbastecimento zonaAbastecimento;

	/** persistent field */
	private Bacia bacia;

	/** persistent field */
	private gcom.cadastro.localidade.Zeis zeis;

	/** persistent field */
	private gcom.cadastro.localidade.QuadraPerfil quadraPerfil;

	private AreaTipo areaTipo;

	private RoteiroEmpresa roteiroEmpresa;

	private Set<Imovel> imoveis;

	public Set<Imovel> getImoveis(){

		return imoveis;
	}

	public void setImoveis(Set<Imovel> imoveis){

		this.imoveis = imoveis;
	}

	/** full constructor */
	public Quadra(int descricao, Short indicadorRedeAgua, Short indicadorRedeEsgoto, Short numeroRotaSequencia, Date dataImplantacao,
					Short indicadorUso, Date ultimaAlteracao, Rota rota, IbgeSetorCensitario ibgeSetorCensitario,
					gcom.cadastro.localidade.SetorComercial setorComercial, DistritoOperacional distritoOperacional, Bacia bacia,
					gcom.cadastro.localidade.Zeis zeis, gcom.cadastro.localidade.QuadraPerfil quadraPerfil, int numeroQuadra, Bairro bairro) {

		this.numeroQuadra = numeroQuadra;
		this.indicadorRedeAgua = indicadorRedeAgua;
		this.indicadorRedeEsgoto = indicadorRedeEsgoto;
		this.numeroRotaSequencia = numeroRotaSequencia;
		this.dataImplantacao = dataImplantacao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.rota = rota;
		this.ibgeSetorCensitario = ibgeSetorCensitario;
		this.setorComercial = setorComercial;
		this.distritoOperacional = distritoOperacional;
		this.bacia = bacia;
		this.zeis = zeis;
		this.quadraPerfil = quadraPerfil;
		this.bairro = bairro;
	}

	/** default constructor */
	public Quadra() {

	}

	/** minimal constructor */
	public Quadra(Integer id, int numeroQuadra, Rota rota) {

		this.id = id;
		this.numeroQuadra = numeroQuadra;
		this.rota = rota;
	}

	// Construido por Sávio Luiz para setar o id no objeto
	public Quadra(Integer id) {

		this.id = id;
	}

	public Quadra(int numeroQuadra) {

		this.numeroQuadra = numeroQuadra;
	}

	/** constructor */
	public Quadra(int descricao, Rota rota, IbgeSetorCensitario ibgeSetorCensitario,
					gcom.cadastro.localidade.SetorComercial setorComercial, DistritoOperacional distritoOperacional, Bacia bacia,
					gcom.cadastro.localidade.Zeis zeis, gcom.cadastro.localidade.QuadraPerfil quadraPerfil, int numeroQuadra, Bairro bairro) {

		this.numeroQuadra = numeroQuadra;
		this.rota = rota;
		this.ibgeSetorCensitario = ibgeSetorCensitario;
		this.setorComercial = setorComercial;
		this.distritoOperacional = distritoOperacional;
		this.bacia = bacia;
		this.zeis = zeis;
		this.quadraPerfil = quadraPerfil;
		this.bairro = bairro;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public int getNumeroQuadra(){

		return this.numeroQuadra;
	}

	/**
	 * @return the bairro
	 */
	public Bairro getBairro(){

		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(Bairro bairro){

		this.bairro = bairro;
	}

	public void setNumeroQuadra(int numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Short getIndicadorRedeAgua(){

		return this.indicadorRedeAgua;
	}

	public void setIndicadorRedeAgua(Short indicadorRedeAgua){

		this.indicadorRedeAgua = indicadorRedeAgua;
	}

	public Short getIndicadorRedeEsgoto(){

		return this.indicadorRedeEsgoto;
	}

	public void setIndicadorRedeEsgoto(Short indicadorRedeEsgoto){

		this.indicadorRedeEsgoto = indicadorRedeEsgoto;
	}

	public Short getNumeroRotaSequencia(){

		return this.numeroRotaSequencia;
	}

	public void setNumeroRotaSequencia(Short numeroRotaSequencia){

		this.numeroRotaSequencia = numeroRotaSequencia;
	}

	public Date getDataImplantacao(){

		return this.dataImplantacao;
	}

	public void setDataImplantacao(Date dataImplantacao){

		this.dataImplantacao = dataImplantacao;
	}

	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Rota getRota(){

		return this.rota;
	}

	public void setRota(Rota rota){

		this.rota = rota;
	}

	public IbgeSetorCensitario getIbgeSetorCensitario(){

		return this.ibgeSetorCensitario;
	}

	public void setIbgeSetorCensitario(IbgeSetorCensitario ibgeSetorCensitario){

		this.ibgeSetorCensitario = ibgeSetorCensitario;
	}

	public gcom.cadastro.localidade.SetorComercial getSetorComercial(){

		return this.setorComercial;
	}

	public void setSetorComercial(gcom.cadastro.localidade.SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public DistritoOperacional getDistritoOperacional(){

		return this.distritoOperacional;
	}

	public void setDistritoOperacional(DistritoOperacional distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	public Bacia getBacia(){

		return this.bacia;
	}

	public void setBacia(Bacia bacia){

		this.bacia = bacia;
	}

	public gcom.cadastro.localidade.Zeis getZeis(){

		return this.zeis;
	}

	public void setZeis(gcom.cadastro.localidade.Zeis zeis){

		this.zeis = zeis;
	}

	public gcom.cadastro.localidade.QuadraPerfil getQuadraPerfil(){

		return this.quadraPerfil;
	}

	public void setQuadraPerfil(gcom.cadastro.localidade.QuadraPerfil quadraPerfil){

		this.quadraPerfil = quadraPerfil;
	}

	public AreaTipo getAreaTipo(){

		return areaTipo;
	}

	public void setAreaTipo(AreaTipo areaTipo){

		this.areaTipo = areaTipo;
	}

	public RoteiroEmpresa getRoteiroEmpresa(){

		return roteiroEmpresa;
	}

	public void setRoteiroEmpresa(RoteiroEmpresa roteiroEmpresa){

		this.roteiroEmpresa = roteiroEmpresa;
	}

	@Override
	public String[] retornarAtributosSelecionadosRegistro(){

		String[] atributos = {"roteiroEmpresa"};
		return atributos;
	}

	@Override
	public void initializeLazy(){

		if(getSetorComercial() != null){
			getSetorComercial().initializeLazy();
		}
	}

	public String getDescricao(){

		return numeroQuadra + "";
	}

	@Override
	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"numeroQuadra", "setorComercial.codigo", "setorComercial.localidade.descricao"};
		return atributos;
	}

	@Override
	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Número da quadra", "Código do Setor Comercial", "Localidade"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, this.getId()));

		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("rota");
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("ibgeSetorCensitario");
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("bairro");
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("distritoOperacional");
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("bacia");
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("zeis");
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("quadraPerfil");
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("areaTipo");

		return filtroQuadra;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public void setZonaAbastecimento(ZonaAbastecimento zonaAbastecimento){

		this.zonaAbastecimento = zonaAbastecimento;
	}

	public ZonaAbastecimento getZonaAbastecimento(){

		return zonaAbastecimento;
	}

	@Override
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof Quadra)){
			return false;
		}
		Quadra castOther = (Quadra) other;

		return (this.getId().equals(castOther.getId()));
	}
}
