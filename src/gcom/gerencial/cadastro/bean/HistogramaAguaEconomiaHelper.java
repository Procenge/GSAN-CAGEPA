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

package gcom.gerencial.cadastro.bean;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.CategoriaTipo;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.faturamento.HistogramaAguaEconomia;
import gcom.faturamento.consumotarifa.ConsumoTarifa;

import java.math.BigDecimal;
import java.util.Date;

public class HistogramaAguaEconomiaHelper {

	private Integer idGerenciaRegional;

	private Integer idUnidadeNegocio;

	private Integer idElo;

	private Integer idLocalidade;

	private Integer idSetorComercial;

	private Integer codigoSetorComercial;

	private Integer idQuadra;

	private Integer idNumeroQuadra;

	private Integer idTipoCategoria;

	private Integer idCategoria;

	private Integer idConsumoTarifa;

	private Integer idPerfilImovel;

	private Integer idEsferaPoder;

	private Integer idSituacaoLigacaoAgua;

	private Short idConsumoReal;

	private Short idExistenciaHidrometro;

	private Short idExistenciaPoco;

	private Short idExistenciaVolumeFixoAgua;

	private Integer quantidadeConsumo;

	private Integer quantidadeEconomia = 0;

	private Integer quantidadeLigacoes = 0;

	private BigDecimal valorFaturadoEconomia = BigDecimal.ZERO;

	private int volumeFaturadoEconomia = 0;

	private Integer anoMesReferencia;

	private Integer quantidadeEconomiaRefaturamento = 0;

	private Integer quantidadeLigacoesRefaturamento = 0;

	private BigDecimal valorFaturadoEconomiaRefaturamento = BigDecimal.ZERO;

	private int volumeFaturadoEconomiaRefaturamento = 0;

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria){

		this.idCategoria = idCategoria;
	}

	public Short getIdConsumoReal(){

		return idConsumoReal;
	}

	public void setIdConsumoReal(Short idConsumoReal){

		this.idConsumoReal = idConsumoReal;
	}

	public Integer getIdConsumoTarifa(){

		return idConsumoTarifa;
	}

	public void setIdConsumoTarifa(Integer idConsumoTarifa){

		this.idConsumoTarifa = idConsumoTarifa;
	}

	public Integer getIdElo(){

		return idElo;
	}

	public void setIdElo(Integer idElo){

		this.idElo = idElo;
	}

	public Integer getIdEsferaPoder(){

		return idEsferaPoder;
	}

	public void setIdEsferaPoder(Integer idEsferaPoder){

		this.idEsferaPoder = idEsferaPoder;
	}

	public Short getIdExistenciaHidrometro(){

		return idExistenciaHidrometro;
	}

	public void setIdExistenciaHidrometro(Short idExistenciaHidrometro){

		this.idExistenciaHidrometro = idExistenciaHidrometro;
	}

	public Short getIdExistenciaPoco(){

		return idExistenciaPoco;
	}

	public void setIdExistenciaPoco(Short idExistenciaPoco){

		this.idExistenciaPoco = idExistenciaPoco;
	}

	public Short getIdExistenciaVolumeFixoAgua(){

		return idExistenciaVolumeFixoAgua;
	}

	public void setIdExistenciaVolumeFixoAgua(Short idExistenciaVolumeFixoAgua){

		this.idExistenciaVolumeFixoAgua = idExistenciaVolumeFixoAgua;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getIdNumeroQuadra(){

		return idNumeroQuadra;
	}

	public void setIdNumeroQuadra(Integer idNumeroQuadra){

		this.idNumeroQuadra = idNumeroQuadra;
	}

	public Integer getIdPerfilImovel(){

		return idPerfilImovel;
	}

	public void setIdPerfilImovel(Integer idPerfilImovel){

		this.idPerfilImovel = idPerfilImovel;
	}

	public Integer getIdQuadra(){

		return idQuadra;
	}

	public void setIdQuadra(Integer idQuadra){

		this.idQuadra = idQuadra;
	}

	public Integer getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(Integer idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public Integer getIdSituacaoLigacaoAgua(){

		return idSituacaoLigacaoAgua;
	}

	public void setIdSituacaoLigacaoAgua(Integer idSituacaoLigacaoAgua){

		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
	}

	public Integer getIdTipoCategoria(){

		return idTipoCategoria;
	}

	public void setIdTipoCategoria(Integer idTipoCategoria){

		this.idTipoCategoria = idTipoCategoria;
	}

	public Integer getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(Integer idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public Integer getQuantidadeConsumo(){

		return quantidadeConsumo;
	}

	public void setQuantidadeConsumo(Integer quantidadeConsumo){

		this.quantidadeConsumo = quantidadeConsumo;
	}

	public Integer getQuantidadeEconomia(){

		return quantidadeEconomia;
	}

	public void setQuantidadeEconomia(Integer quantidadeEconomia){

		this.quantidadeEconomia = quantidadeEconomia;
	}

	public HistogramaAguaEconomiaHelper(Integer anoMesReferencia, Integer idGerenciaRegional, Integer idUnidadeNegocio, Integer idElo,
										Integer idLocalidade, Integer idSetorComercial, Integer codigoSetorComercial, Integer idQuadra,
										Integer idNumeroQuadra, Integer idTipoCategoria, Integer idCategoria, Integer idConsumoTarifa,
										Integer idPerfilImovel, Integer idEsferaPoder, Integer idSituacaoLigacaoAgua, Short idConsumoReal,
										Short idExistenciaHidrometro, Short idExistenciaPoco, Short idExistenciaVolumeFixoAgua,
										Integer quantidadeConsumo, Integer quantidadeLigacoes) {

		super();
		this.anoMesReferencia = anoMesReferencia;
		this.idGerenciaRegional = idGerenciaRegional;
		this.idUnidadeNegocio = idUnidadeNegocio;
		this.idElo = idElo;
		this.idLocalidade = idLocalidade;
		this.idSetorComercial = idSetorComercial;
		this.codigoSetorComercial = codigoSetorComercial;
		this.idQuadra = idQuadra;
		this.idNumeroQuadra = idNumeroQuadra;
		this.idTipoCategoria = idTipoCategoria;
		this.idCategoria = idCategoria;
		this.idConsumoTarifa = idConsumoTarifa;
		this.idPerfilImovel = idPerfilImovel;
		this.idEsferaPoder = idEsferaPoder;
		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
		this.idConsumoReal = idConsumoReal;
		this.idExistenciaHidrometro = idExistenciaHidrometro;
		this.idExistenciaPoco = idExistenciaPoco;
		this.idExistenciaVolumeFixoAgua = idExistenciaVolumeFixoAgua;
		this.quantidadeConsumo = quantidadeConsumo;
		this.quantidadeLigacoes = quantidadeLigacoes;
	}

	/**
	 * Compara duas properiedades inteiras, comparando
	 * seus valores para descobrirmos se sao iguais
	 * 
	 * @param pro1
	 *            Primeira propriedade
	 * @param pro2
	 *            Segunda propriedade
	 * @return Se iguais, retorna true
	 */
	private boolean propriedadesIguais(Integer pro1, Integer pro2){

		if(pro2 != null){
			if(!pro2.equals(pro1)){
				return false;
			}
		}else if(pro1 != null){
			return false;
		}

		// Se chegou ate aqui quer dizer que as propriedades sao iguais
		return true;
	}

	/**
	 * Compara duas properiedades inteiras, comparando
	 * seus valores para descobrirmos se sao iguais
	 * 
	 * @param pro1
	 *            Primeira propriedade
	 * @param pro2
	 *            Segunda propriedade
	 * @return Se iguais, retorna true
	 */
	private boolean propriedadesIguais(Short pro1, Short pro2){

		if(pro2 != null){
			if(!pro2.equals(pro1)){
				return false;
			}
		}else if(pro1 != null){
			return false;
		}

		// Se chegou ate aqui quer dizer que as propriedades sao iguais
		return true;
	}

	/**
	 * Compara dois objetos levando em consideracao apenas as propriedades
	 * que identificam o agrupamento
	 * 
	 * @param obj
	 *            Objeto a ser comparado com a instancia deste objeto
	 */
	public boolean equals(Object obj){

		if(!(obj instanceof HistogramaAguaEconomiaHelper)){
			return false;
		}else{
			HistogramaAguaEconomiaHelper resumoTemp = (HistogramaAguaEconomiaHelper) obj;

			// Verificamos se todas as propriedades que identificam o objeto sao iguais
			return propriedadesIguais(this.idGerenciaRegional, resumoTemp.idGerenciaRegional)
							&& propriedadesIguais(this.idUnidadeNegocio, resumoTemp.idUnidadeNegocio)
							&& propriedadesIguais(this.idElo, resumoTemp.idElo)
							&& propriedadesIguais(this.idLocalidade, resumoTemp.idLocalidade)
							&& propriedadesIguais(this.idSetorComercial, resumoTemp.idSetorComercial)
							&& propriedadesIguais(this.codigoSetorComercial, resumoTemp.codigoSetorComercial)
							&& propriedadesIguais(this.idQuadra, resumoTemp.idQuadra)
							&& propriedadesIguais(this.idNumeroQuadra, resumoTemp.idNumeroQuadra)
							&& propriedadesIguais(this.idTipoCategoria, resumoTemp.idTipoCategoria)
							&& propriedadesIguais(this.idCategoria, resumoTemp.idCategoria)
							&& propriedadesIguais(this.idConsumoTarifa, resumoTemp.idConsumoTarifa)
							&& propriedadesIguais(this.idPerfilImovel, resumoTemp.idPerfilImovel)
							&& propriedadesIguais(this.idEsferaPoder, resumoTemp.idEsferaPoder)
							&& propriedadesIguais(this.idSituacaoLigacaoAgua, resumoTemp.idSituacaoLigacaoAgua)
							&& propriedadesIguais(this.idConsumoReal, resumoTemp.idConsumoReal)
							&& propriedadesIguais(this.idExistenciaHidrometro, resumoTemp.idExistenciaHidrometro)
							&& propriedadesIguais(this.idExistenciaPoco, resumoTemp.idExistenciaPoco)
							&& propriedadesIguais(this.idExistenciaVolumeFixoAgua, resumoTemp.idExistenciaVolumeFixoAgua)
							&& propriedadesIguais(this.quantidadeConsumo, resumoTemp.quantidadeConsumo)
							&& propriedadesIguais(this.anoMesReferencia, resumoTemp.anoMesReferencia);
		}
	}

	public BigDecimal getValorFaturadoEconomia(){

		return valorFaturadoEconomia;
	}

	public void setValorFaturadoEconomia(BigDecimal valorFaturadoEconomia){

		this.valorFaturadoEconomia = valorFaturadoEconomia;
	}

	public int getVolumeFaturadoEconomia(){

		return volumeFaturadoEconomia;
	}

	public void setVolumeFaturadoEconomia(int volumeFaturadoEconomia){

		this.volumeFaturadoEconomia = volumeFaturadoEconomia;
	}

	public Integer getQuantidadeLigacoes(){

		return quantidadeLigacoes;
	}

	public void setQuantidadeLigacoes(Integer quantidadeLigacoes){

		this.quantidadeLigacoes = quantidadeLigacoes;
	}

	public HistogramaAguaEconomia gerarHistogramaAguaEconomia(){

		HistogramaAguaEconomia retorno = new HistogramaAguaEconomia();
		retorno.setAnoMesReferencia(this.getAnoMesReferencia());
		retorno.setCodigoSetorComercial(this.getCodigoSetorComercial());
		retorno.setNumeroQuadra(this.getIdNumeroQuadra());
		retorno.setIndicadorConsumoReal(((this.getIdConsumoReal()) != null) ? this.getIdConsumoReal().shortValue() : null);
		retorno.setIndicadorHidrometro(((this.getIdExistenciaHidrometro()) != null) ? this.getIdExistenciaHidrometro().shortValue() : null);
		retorno.setIndicadorPoco(((this.getIdExistenciaPoco()) != null) ? this.getIdExistenciaPoco().shortValue() : null);
		retorno.setIndicadorVolFixadoAgua(((this.getIdExistenciaVolumeFixoAgua()) != null) ? this.getIdExistenciaVolumeFixoAgua()
						.shortValue() : null);
		retorno.setQuantidadeConsumo(this.getQuantidadeConsumo());
		retorno.setQuantidadeEconomia(this.getQuantidadeEconomia());
		retorno.setValorFaturadoEconomia(this.getValorFaturadoEconomia());
		retorno.setVolumeFaturadoEconomia(this.getVolumeFaturadoEconomia());
		retorno.setQuantidadeLigacao(this.getQuantidadeLigacoes());
		retorno.setUltimaAlteracao(new Date());
		retorno.setQuantidadeEconomiaRefaturamento(this.getQuantidadeEconomiaRefaturamento());
		retorno.setValorFaturadoEconomiaRefaturamento(this.getValorFaturadoEconomiaRefaturamento());
		retorno.setVolumeFaturadoEconomiaRefaturamento(this.getVolumeFaturadoEconomiaRefaturamento());
		retorno.setQuantidadeLigacaoRefaturamento(this.getQuantidadeLigacoesRefaturamento());

		if(getIdGerenciaRegional() != null){
			GerenciaRegional gerenciaRegional = new GerenciaRegional();
			gerenciaRegional.setId(getIdGerenciaRegional());
			retorno.setGerenciaRegional(gerenciaRegional);
		}
		if(getIdLocalidade() != null){
			Localidade obj = new Localidade();
			obj.setId(getIdLocalidade());
			retorno.setLocalidade(obj);
		}
		if(getIdElo() != null){
			Localidade obj = new Localidade();
			obj.setId(getIdElo());
			retorno.setLocalidadeElo(obj);
		}
		if(getIdQuadra() != null){
			Quadra obj = new Quadra();
			obj.setId(getIdQuadra());
			retorno.setQuadra(obj);
		}
		if(getIdQuadra() != null){
			Quadra obj = new Quadra();
			obj.setId(getIdQuadra());
			retorno.setQuadra(obj);
		}
		if(getIdConsumoTarifa() != null){
			ConsumoTarifa obj = new ConsumoTarifa();
			obj.setId(getIdConsumoTarifa());
			retorno.setConsumoTarifa(obj);
		}
		if(getIdPerfilImovel() != null){
			ImovelPerfil obj = new ImovelPerfil();
			obj.setId(getIdPerfilImovel());
			retorno.setImovelPerfil(obj);
		}
		if(getIdSituacaoLigacaoAgua() != null){
			LigacaoAguaSituacao obj = new LigacaoAguaSituacao();
			obj.setId(getIdSituacaoLigacaoAgua());
			retorno.setLigacaoAguaSituacao(obj);
		}
		if(getIdUnidadeNegocio() != null){
			UnidadeNegocio obj = new UnidadeNegocio();
			obj.setId(getIdUnidadeNegocio());
			retorno.setUnidadeNegocio(obj);
		}
		if(getIdEsferaPoder() != null){
			EsferaPoder obj = new EsferaPoder();
			obj.setId(getIdEsferaPoder());
			retorno.setEsferaPoder(obj);
		}
		if(getIdCategoria() != null){
			Categoria obj = new Categoria();
			obj.setId(getIdCategoria());
			retorno.setCategoria(obj);
		}
		if(getIdTipoCategoria() != null){
			CategoriaTipo obj = new CategoriaTipo();
			obj.setId(getIdTipoCategoria());
			retorno.setCategoriaTipo(obj);
		}
		if(getIdSetorComercial() != null){
			SetorComercial obj = new SetorComercial();
			obj.setId(getIdSetorComercial());
			retorno.setSetorComercial(obj);
		}
		return retorno;
	}

	public Integer getQuantidadeEconomiaRefaturamento(){

		return quantidadeEconomiaRefaturamento;
	}

	public void setQuantidadeEconomiaRefaturamento(Integer quantidadeEconomiaRefaturamento){

		this.quantidadeEconomiaRefaturamento = quantidadeEconomiaRefaturamento;
	}

	public Integer getQuantidadeLigacoesRefaturamento(){

		return quantidadeLigacoesRefaturamento;
	}

	public void setQuantidadeLigacoesRefaturamento(Integer quantidadeLigacoesRefaturamento){

		this.quantidadeLigacoesRefaturamento = quantidadeLigacoesRefaturamento;
	}

	public BigDecimal getValorFaturadoEconomiaRefaturamento(){

		return valorFaturadoEconomiaRefaturamento;
	}

	public void setValorFaturadoEconomiaRefaturamento(BigDecimal valorFaturadoEconomiaRefaturamento){

		this.valorFaturadoEconomiaRefaturamento = valorFaturadoEconomiaRefaturamento;
	}

	public int getVolumeFaturadoEconomiaRefaturamento(){

		return volumeFaturadoEconomiaRefaturamento;
	}

	public void setVolumeFaturadoEconomiaRefaturamento(int volumeFaturadoEconomiaRefaturamento){

		this.volumeFaturadoEconomiaRefaturamento = volumeFaturadoEconomiaRefaturamento;
	}

}