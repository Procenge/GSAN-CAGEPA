/**
 * 
 */

package gcom.faturamento.histograma;

import java.math.BigDecimal;

/**
 * @author ebandeira
 *         Classe que representa o "agrupamento de negócio" que identifica um
 *         histograma.
 */
public class FiltroAgrupamentoHistograma {

	private Integer idGerenciaRegional;

	private Integer idUnidadeNegocio;

	private Integer idLocalidade;

	private Integer codigoElo;

	private Integer idSetorComercial;

	private Integer idQuadra;

	private Integer idCategoriaTipo;

	private Integer idCategoria;

	private Integer idConsumoTarifa;

	private Integer idImovelPerfil;

	private Integer idEsferaPoder;

	private Integer idLigacaoAguaSituacao;

	private Integer idLigacaoEsgotoSituacao;

	private Short idConsumoTipo; // indicadorConsumoReal

	private Short indicadorHidrometro;

	private Short indicadorPoco;

	private Short indicadorVolumeFixoAgua;

	private Short indicadorVolumeFixoEsgoto;

	private BigDecimal percentualEsgoto;

	private Integer consumoFaturadoAgua;

	private Integer consumoFaturadoEsgoto;

	private Short indicadorLigacaoMista;

	private Integer anoMesReferencia;

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Short getIndicadorLigacaoMista(){

		return indicadorLigacaoMista;
	}

	public void setIndicadorLigacaoMista(Short indicadorLigacaoMista){

		this.indicadorLigacaoMista = indicadorLigacaoMista;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(Integer idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getCodigoElo(){

		return codigoElo;
	}

	public void setCodigoElo(Integer codigoElo){

		this.codigoElo = codigoElo;
	}

	public Integer getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(Integer idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public Integer getIdQuadra(){

		return idQuadra;
	}

	public void setIdQuadra(Integer idQuadra){

		this.idQuadra = idQuadra;
	}

	public Integer getIdCategoriaTipo(){

		return idCategoriaTipo;
	}

	public void setIdCategoriaTipo(Integer idCategoriaTipo){

		this.idCategoriaTipo = idCategoriaTipo;
	}

	public Integer getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria){

		this.idCategoria = idCategoria;
	}

	public Integer getIdConsumoTarifa(){

		return idConsumoTarifa;
	}

	public void setIdConsumoTarifa(Integer idConsumoTarifa){

		this.idConsumoTarifa = idConsumoTarifa;
	}

	public Integer getIdImovelPerfil(){

		return idImovelPerfil;
	}

	public void setIdImovelPerfil(Integer idImovelPerfil){

		this.idImovelPerfil = idImovelPerfil;
	}

	public Integer getIdEsferaPoder(){

		return idEsferaPoder;
	}

	public void setIdEsferaPoder(Integer idEsferaPoder){

		this.idEsferaPoder = idEsferaPoder;
	}

	public Integer getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public Integer getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(Integer idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public Short getIdConsumoTipo(){

		return idConsumoTipo;
	}

	public void setIdConsumoTipo(Short idConsumoTipo){

		this.idConsumoTipo = idConsumoTipo;
	}

	public Short getIndicadorHidrometro(){

		return indicadorHidrometro;
	}

	public void setIndicadorHidrometro(Short indicadorHidrometro){

		this.indicadorHidrometro = indicadorHidrometro;
	}

	public Short getIndicadorPoco(){

		return indicadorPoco;
	}

	public void setIndicadorPoco(Short indicadorPoco){

		this.indicadorPoco = indicadorPoco;
	}

	public Short getIndicadorVolumeFixoAgua(){

		return indicadorVolumeFixoAgua;
	}

	public void setIndicadorVolumeFixoAgua(Short indicadorVolumeFixoAgua){

		this.indicadorVolumeFixoAgua = indicadorVolumeFixoAgua;
	}

	public Short getIndicadorVolumeFixoEsgoto(){

		return indicadorVolumeFixoEsgoto;
	}

	public void setIndicadorVolumeFixoEsgoto(Short indicadorVolumeFixoEsgoto){

		this.indicadorVolumeFixoEsgoto = indicadorVolumeFixoEsgoto;
	}

	public BigDecimal getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(BigDecimal percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	public Integer getConsumoFaturadoAgua(){

		return consumoFaturadoAgua;
	}

	public void setConsumoFaturadoAgua(Integer consumoFaturadoAgua){

		this.consumoFaturadoAgua = consumoFaturadoAgua;
	}

	public Integer getConsumoFaturadoEsgoto(){

		return consumoFaturadoEsgoto;
	}

	public void setConsumoFaturadoEsgoto(Integer consumoFaturadoEsgoto){

		this.consumoFaturadoEsgoto = consumoFaturadoEsgoto;
	}

}
