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
 * Ailton Francisco de Sousa Junior
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

package gcom.operacional;

import gcom.atendimentopublico.ordemservico.MaterialRedeAgua;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DadoDistritoOperacional
				extends ObjetoTransacao
				implements Comparable<DadoDistritoOperacional> {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	private Integer anoMesReferencia;

	private BigDecimal unidadeCapacidade;

	private BigDecimal aducaoCapacidade;

	private BigDecimal volumeCapacidade;

	private BigDecimal extensaoAdutora;

	private BigDecimal diametroAdutora;

	private MaterialRedeAgua materialAdutora;

	private BigDecimal demandaEnergia;

	private BigDecimal qtidadeSulfatoAluminio;

	private BigDecimal qtidadeCloroGasoso;

	private BigDecimal qtidadeHipocloritoSodio;

	private BigDecimal qtidadeFluor;

	private BigDecimal qtidadeHorasParalisadas;

	private Usuario usuarioManutencao;

	private DistritoOperacional distritoOperacional;

	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** full constructor */
	public DadoDistritoOperacional(Integer id, Integer anoMesReferencia, BigDecimal unidadeCapacidade, BigDecimal aducaoCapacidade,
									BigDecimal volumeCapacidade, BigDecimal extensaoAdutora, BigDecimal diametroAdutora,
									MaterialRedeAgua materialAdutora, BigDecimal demandaEnergia, BigDecimal qtidadeSulfatoAluminio,
									BigDecimal qtidadeCloroGasoso, BigDecimal qtidadeHipocloritoSodio, BigDecimal qtidadeFluor,
									BigDecimal qtidadeHorasParalisadas, Usuario usuarioManutencao, Short indicadorUso, Date ultimaAlteracao) {

		super();
		this.id = id;
		this.anoMesReferencia = anoMesReferencia;
		this.unidadeCapacidade = unidadeCapacidade;
		this.aducaoCapacidade = aducaoCapacidade;
		this.volumeCapacidade = volumeCapacidade;
		this.extensaoAdutora = extensaoAdutora;
		this.diametroAdutora = diametroAdutora;
		this.materialAdutora = materialAdutora;
		this.demandaEnergia = demandaEnergia;
		this.qtidadeSulfatoAluminio = qtidadeSulfatoAluminio;
		this.qtidadeCloroGasoso = qtidadeCloroGasoso;
		this.qtidadeHipocloritoSodio = qtidadeHipocloritoSodio;
		this.qtidadeFluor = qtidadeFluor;
		this.qtidadeHorasParalisadas = qtidadeHorasParalisadas;
		this.usuarioManutencao = usuarioManutencao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/** default constructor */
	public DadoDistritoOperacional() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
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

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public BigDecimal getUnidadeCapacidade(){

		return unidadeCapacidade;
	}

	public void setUnidadeCapacidade(BigDecimal unidadeCapacidade){

		this.unidadeCapacidade = unidadeCapacidade;
	}

	public BigDecimal getAducaoCapacidade(){

		return aducaoCapacidade;
	}

	public void setAducaoCapacidade(BigDecimal aducaoCapacidade){

		this.aducaoCapacidade = aducaoCapacidade;
	}

	public BigDecimal getVolumeCapacidade(){

		return volumeCapacidade;
	}

	public void setVolumeCapacidade(BigDecimal volumeCapacidade){

		this.volumeCapacidade = volumeCapacidade;
	}

	public BigDecimal getExtensaoAdutora(){

		return extensaoAdutora;
	}

	public void setExtensaoAdutora(BigDecimal extensaoAdutora){

		this.extensaoAdutora = extensaoAdutora;
	}

	public BigDecimal getDiametroAdutora(){

		return diametroAdutora;
	}

	public void setDiametroAdutora(BigDecimal diametroAdutora){

		this.diametroAdutora = diametroAdutora;
	}

	public MaterialRedeAgua getMaterialAdutora(){

		return materialAdutora;
	}

	public void setMaterialAdutora(MaterialRedeAgua materialAdutora){

		this.materialAdutora = materialAdutora;
	}

	public BigDecimal getDemandaEnergia(){

		return demandaEnergia;
	}

	public void setDemandaEnergia(BigDecimal demandaEnergia){

		this.demandaEnergia = demandaEnergia;
	}

	public BigDecimal getQtidadeSulfatoAluminio(){

		return qtidadeSulfatoAluminio;
	}

	public void setQtidadeSulfatoAluminio(BigDecimal qtidadeSulfatoAluminio){

		this.qtidadeSulfatoAluminio = qtidadeSulfatoAluminio;
	}

	public BigDecimal getQtidadeCloroGasoso(){

		return qtidadeCloroGasoso;
	}

	public void setQtidadeCloroGasoso(BigDecimal qtidadeCloroGasoso){

		this.qtidadeCloroGasoso = qtidadeCloroGasoso;
	}

	public BigDecimal getQtidadeHipocloritoSodio(){

		return qtidadeHipocloritoSodio;
	}

	public void setQtidadeHipocloritoSodio(BigDecimal qtidadeHipocloritoSodio){

		this.qtidadeHipocloritoSodio = qtidadeHipocloritoSodio;
	}

	public BigDecimal getQtidadeFluor(){

		return qtidadeFluor;
	}

	public void setQtidadeFluor(BigDecimal qtidadeFluor){

		this.qtidadeFluor = qtidadeFluor;
	}

	public Usuario getUsuarioManutencao(){

		return usuarioManutencao;
	}

	public BigDecimal getQtidadeHorasParalisadas(){

		return qtidadeHorasParalisadas;
	}

	public void setQtidadeHorasParalisadas(BigDecimal qtidadeHorasParalisadas){

		this.qtidadeHorasParalisadas = qtidadeHorasParalisadas;
	}

	public void setUsuarioManutencao(Usuario usuarioManutencao){

		this.usuarioManutencao = usuarioManutencao;
	}

	public DistritoOperacional getDistritoOperacional(){

		return distritoOperacional;
	}

	public void setDistritoOperacional(DistritoOperacional distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "descricao", "sistemaEsgoto.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"ID", "Descricao", "Sist Esg."};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();

		filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.ID, this.getId()));
		filtroSubsistemaEsgoto.adicionarCaminhoParaCarregamentoEntidade(FiltroSubsistemaEsgoto.SISTEMAESGOTO);

		return filtroSubsistemaEsgoto;
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

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((anoMesReferencia == null) ? 0 : anoMesReferencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		boolean retorno = true;
		if(this == obj){
			retorno = true;
		}else{
			if(obj == null){
				retorno = false;
			}else{
				if(getClass() == obj.getClass()){
					final DadoDistritoOperacional other = (DadoDistritoOperacional) obj;
					if(anoMesReferencia == null){
						if(other.anoMesReferencia != null){
							retorno = false;
						}
					}else if(anoMesReferencia.intValue() != other.anoMesReferencia.intValue()){
						retorno = false;

					}
					if(id == null){
						if(other.id != null){
							retorno = false;
						}
					}else if(!id.equals(other.id)){
						retorno = false;
					}
				}else{
					retorno = false;
				}
			}

		}
		return retorno;
	}

	public int compareTo(DadoDistritoOperacional o){

		return anoMesReferencia.compareTo(o.anoMesReferencia);
	}

	public String getMesReferenciaFormatado(){

		return Util.formatarAnoMesParaMesAno(getAnoMesReferencia());
	}

}
