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

import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.CategoriaTipo;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.faturamento.HistogramaEsgotoLigacao;
import gcom.faturamento.consumotarifa.ConsumoTarifa;

import java.math.BigDecimal;
import java.util.Date;

/**
 * classe responsável por ajudar o caso de uso [UC0566] Gerar Histograma Agua e Esgoto
 * 
 * @author Bruno Barros
 * @date 14/05/2007
 */
public class HistogramaEsgotoLigacaoHelper {

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

	private Short idLigacaoMista;

	private Integer idConsumoTarifa;

	private Integer idPerfilImovel;

	private Integer idEsferaPoder;

	private Integer idSituacaoLigacaoEsgoto;

	private Short idConsumoReal;

	private Short idExistenciaHidrometro;

	private Short idExistenciaPoco;

	private Short idExistenciaVolumeFixoEsgoto;

	private Short percentualColetaEsgoto;

	private Integer quantidadeConsumo;

	private Integer quantidadeLigacao = 0;

	private Integer quantidadeEconomiaLigacao = 0;

	private BigDecimal valorFaturadoLigacao = BigDecimal.ZERO;

	private Integer volumeFaturadoLigacao = 0;

	private Integer anoMesReferencia;

	private Integer quantidadeLigacaoRefaturamento = 0;

	private Integer quantidadeEconomiaLigacaoRefaturamento = 0;

	private BigDecimal valorFaturadoLigacaoRefaturamento = BigDecimal.ZERO;

	private Integer volumeFaturadoLigacaoRefaturamento = 0;

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

	public Short getIdExistenciaVolumeFixoEsgoto(){

		return idExistenciaVolumeFixoEsgoto;
	}

	public void setIdExistenciaVolumeFixoEsgoto(Short idExistenciaVolumeFixoEsgoto){

		this.idExistenciaVolumeFixoEsgoto = idExistenciaVolumeFixoEsgoto;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Short getIdLigacaoMista(){

		return idLigacaoMista;
	}

	public void setIdLigacaoMista(Short idLigacaoMista){

		this.idLigacaoMista = idLigacaoMista;
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

	public Integer getIdSituacaoLigacaoEsgoto(){

		return idSituacaoLigacaoEsgoto;
	}

	public void setIdSituacaoLigacaoEsgoto(Integer idSituacaoLigacaoEsgoto){

		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
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

	public Short getPercentualColetaEsgoto(){

		return percentualColetaEsgoto;
	}

	public void setPercentualColetaEsgoto(Short percentualColetaEsgoto){

		this.percentualColetaEsgoto = percentualColetaEsgoto;
	}

	public Integer getQuantidadeConsumo(){

		return quantidadeConsumo;
	}

	public void setQuantidadeConsumo(Integer quantidadeConsumo){

		this.quantidadeConsumo = quantidadeConsumo;
	}

	public Integer getQuantidadeEconomiaLigacao(){

		return quantidadeEconomiaLigacao;
	}

	public void setQuantidadeEconomiaLigacao(Integer quantidadeEconomiaLigacao){

		this.quantidadeEconomiaLigacao = quantidadeEconomiaLigacao;
	}

	public Integer getQuantidadeLigacao(){

		return quantidadeLigacao;
	}

	public void setQuantidadeLigacao(Integer quantidadeLigacao){

		this.quantidadeLigacao = quantidadeLigacao;
	}

	public BigDecimal getValorFaturadoLigacao(){

		return valorFaturadoLigacao;
	}

	public void setValorFaturadoLigacao(BigDecimal valorFaturadoLigacao){

		this.valorFaturadoLigacao = valorFaturadoLigacao;
	}

	public HistogramaEsgotoLigacaoHelper(Integer anoMesReferencia, Integer idGerenciaRegional, Integer idUnidadeNegocio, Integer idElo,
											Integer idLocalidade, Integer idSetorComercial, Integer codigoSetorComercial, Integer idQuadra,
											Integer idNumeroQuadra, Integer idTipoCategoria, Integer idCategoria, Short idLigacaoMista,
											Integer idConsumoTarifa, Integer idPerfilImovel, Integer idEsferaPoder,
											Integer idSituacaoLigacaoEsgoto, Short idConsumoReal, Short idExistenciaHidrometro,
											Short idExistenciaPoco, Short idExistenciaVolumeFixoEsgoto, Short percentualColetaEsgoto,
											Integer quantidadeConsumo) {

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
		this.idLigacaoMista = idLigacaoMista;
		this.idConsumoTarifa = idConsumoTarifa;
		this.idPerfilImovel = idPerfilImovel;
		this.idEsferaPoder = idEsferaPoder;
		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
		this.idConsumoReal = idConsumoReal;
		this.idExistenciaHidrometro = idExistenciaHidrometro;
		this.idExistenciaPoco = (idExistenciaPoco == null ? 0 : idExistenciaPoco);
		this.idExistenciaVolumeFixoEsgoto = idExistenciaVolumeFixoEsgoto;
		this.percentualColetaEsgoto = (percentualColetaEsgoto == null ? 0 : percentualColetaEsgoto);
		this.quantidadeConsumo = quantidadeConsumo;
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
	private boolean propriedadesIguais(BigDecimal pro1, BigDecimal pro2){

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
	 * Compara duas properiedades inteiras, comparando
	 * seus valores para descobrirmos se sao iguais
	 * 
	 * @param pro1
	 *            Primeira propriedade
	 * @param pro2
	 *            Segunda propriedade
	 * @return Se iguais, retorna true
	 */
	private boolean propriedadesIguais(Float pro1, Float pro2){

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

		if(!(obj instanceof HistogramaEsgotoLigacaoHelper)){
			return false;
		}else{
			HistogramaEsgotoLigacaoHelper resumoTemp = (HistogramaEsgotoLigacaoHelper) obj;

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
							&& propriedadesIguais(this.idLigacaoMista, resumoTemp.idLigacaoMista)
							&& propriedadesIguais(this.idConsumoTarifa, resumoTemp.idConsumoTarifa)
							&& propriedadesIguais(this.idPerfilImovel, resumoTemp.idPerfilImovel)
							&& propriedadesIguais(this.idEsferaPoder, resumoTemp.idEsferaPoder)
							&& propriedadesIguais(this.idSituacaoLigacaoEsgoto, resumoTemp.idSituacaoLigacaoEsgoto)
							&& propriedadesIguais(this.idConsumoReal, resumoTemp.idConsumoReal)
							&& propriedadesIguais(this.idExistenciaHidrometro, resumoTemp.idExistenciaHidrometro)
							&& propriedadesIguais(this.idExistenciaPoco, resumoTemp.idExistenciaPoco)
							&& propriedadesIguais(this.idExistenciaVolumeFixoEsgoto, resumoTemp.idExistenciaVolumeFixoEsgoto)
							&& propriedadesIguais(this.percentualColetaEsgoto, resumoTemp.percentualColetaEsgoto)
							&& propriedadesIguais(this.quantidadeConsumo, resumoTemp.quantidadeConsumo)
							&& propriedadesIguais(this.anoMesReferencia, resumoTemp.anoMesReferencia);
		}
	}

	public Integer getVolumeFaturadoLigacao(){

		return volumeFaturadoLigacao;
	}

	public void setVolumeFaturadoLigacao(Integer volumeFaturadoLigacao){

		this.volumeFaturadoLigacao = volumeFaturadoLigacao;
	}

	public HistogramaEsgotoLigacao gerarHistogramaEsgotoLigacao(){

		HistogramaEsgotoLigacao retorno = new HistogramaEsgotoLigacao();

		retorno.setReferencia(this.getAnoMesReferencia());
		retorno.setCodigoSetorComercial(this.getCodigoSetorComercial());
		retorno.setNumeroQuadra(this.getIdNumeroQuadra());
		retorno.setIndicadorLigacaoMista(((this.getIdLigacaoMista()) != null) ? this.getIdLigacaoMista().shortValue() : null);
		retorno.setIndicadorConsumoReal(((this.getIdConsumoReal()) != null) ? this.getIdConsumoReal().shortValue() : null);
		retorno.setIndicadorHidrometro(((this.getIdExistenciaHidrometro()) != null) ? this.getIdExistenciaHidrometro().shortValue() : null);
		retorno.setIndicadorPoco(((this.getIdExistenciaPoco()) != null) ? this.getIdExistenciaPoco().shortValue() : null);
		retorno.setIndicadorVolumeFixadoEsgoto(((this.getIdExistenciaVolumeFixoEsgoto()) != null) ? this.getIdExistenciaVolumeFixoEsgoto()
						.shortValue() : null);
		retorno.setPercentualEsgoto(((this.getPercentualColetaEsgoto()) != null) ? this.getPercentualColetaEsgoto().shortValue() : null);

		retorno.setQuantidadeConsumo(this.getQuantidadeConsumo());
		retorno.setQuantidadeLigacao(this.getQuantidadeLigacao());
		retorno.setQuantidadeEconomiaLigacao(this.getQuantidadeEconomiaLigacao());
		retorno.setValorFaturadoLigacao(this.getValorFaturadoLigacao());
		retorno.setVolumeFaturadoLigacao(this.getVolumeFaturadoLigacao());
		retorno.setUltimaAlteracao(new Date());
		retorno.setQuantidadeLigacaoRefaturamento(this.getQuantidadeLigacaoRefaturamento());
		retorno.setQuantidadeEconomiaLigacaoRefaturamento(this.getQuantidadeEconomiaLigacaoRefaturamento());
		retorno.setValorFaturadoLigacaoRefaturamento(this.getValorFaturadoLigacaoRefaturamento());
		retorno.setVolumeFaturadoLigacaoRefaturamento(this.getVolumeFaturadoLigacaoRefaturamento());

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
		if(getIdSituacaoLigacaoEsgoto() != null){
			LigacaoEsgotoSituacao obj = new LigacaoEsgotoSituacao();
			obj.setId(getIdSituacaoLigacaoEsgoto());
			retorno.setLigacaoEsgotoSituacao(obj);
		}
		if(getIdSetorComercial() != null){
			SetorComercial obj = new SetorComercial();
			obj.setId(getIdSetorComercial());
			retorno.setSetorComercial(obj);
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

		return retorno;
	}

	public Integer getQuantidadeLigacaoRefaturamento(){

		return quantidadeLigacaoRefaturamento;
	}

	public void setQuantidadeLigacaoRefaturamento(Integer quantidadeLigacaoRefaturamento){

		this.quantidadeLigacaoRefaturamento = quantidadeLigacaoRefaturamento;
	}

	public Integer getQuantidadeEconomiaLigacaoRefaturamento(){

		return quantidadeEconomiaLigacaoRefaturamento;
	}

	public void setQuantidadeEconomiaLigacaoRefaturamento(Integer quantidadeEconomiaLigacaoRefaturamento){

		this.quantidadeEconomiaLigacaoRefaturamento = quantidadeEconomiaLigacaoRefaturamento;
	}

	public BigDecimal getValorFaturadoLigacaoRefaturamento(){

		return valorFaturadoLigacaoRefaturamento;
	}

	public void setValorFaturadoLigacaoRefaturamento(BigDecimal valorFaturadoLigacaoRefaturamento){

		this.valorFaturadoLigacaoRefaturamento = valorFaturadoLigacaoRefaturamento;
	}

	public Integer getVolumeFaturadoLigacaoRefaturamento(){

		return volumeFaturadoLigacaoRefaturamento;
	}

	public void setVolumeFaturadoLigacaoRefaturamento(Integer volumeFaturadoLigacaoRefaturamento){

		this.volumeFaturadoLigacaoRefaturamento = volumeFaturadoLigacaoRefaturamento;
	}

}
