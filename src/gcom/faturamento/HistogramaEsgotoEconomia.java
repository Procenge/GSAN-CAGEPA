
package gcom.faturamento;

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
import gcom.faturamento.consumotarifa.ConsumoTarifa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class HistogramaEsgotoEconomia
				implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private int referencia;

	/** persistent field */
	private int codigoSetorComercial;

	/** persistent field */
	private int numeroQuadra;

	/** persistent field */
	private short indicadorConsumoReal;

	/** persistent field */
	private short indicadorHidrometro;

	/** persistent field */
	private short indicadorPoco;

	/** persistent field */
	private short indicadorVolumeFixadoEsgoto;

	/** persistent field */
	private short percentualEsgoto;

	/** persistent field */
	private int quantidadeConsumo;

	/** persistent field */
	private int quantidadeEconomia;

	/** persistent field */
	private BigDecimal valorFaturadoEconomia;

	/** persistent field */
	private int volumeFaturadoEconomia;

	/** persistent field */
	private int quantidadeLigacao;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private GerenciaRegional gerenciaRegional;

	/** persistent field */
	private Localidade localidadeElo;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private Quadra quadra;

	/** persistent field */
	private ConsumoTarifa consumoTarifa;

	/** persistent field */
	private ImovelPerfil imovelPerfil;

	/** persistent field */
	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;

	/** persistent field */
	private SetorComercial setorComercial;

	/** persistent field */
	private UnidadeNegocio unidadeNegocio;

	/** persistent field */
	private EsferaPoder esferaPoder;

	/** persistent field */
	private Categoria categoria;

	/** persistent field */
	private CategoriaTipo categoriaTipo;

	/** persistent field */
	private int quantidadeEconomiaRefaturamento;

	/** persistent field */
	private BigDecimal valorFaturadoEconomiaRefaturamento;

	/** persistent field */
	private int volumeFaturadoEconomiaRefaturamento;

	/** persistent field */
	private int quantidadeLigacaoRefaturamento;

	/** full constructor */
	public HistogramaEsgotoEconomia(Integer id, int referencia, int codigoSetorComercial, int numeroQuadra, short indicadorConsumoReal,
									short indicadorHidrometro, short indicadorPoco, short indicadorVolumeFixadoEsgoto,
									short percentualEsgoto, int quantidadeConsumo, int quantidadeEconomia,
									BigDecimal valorFaturadoEconomia, int volumeFaturadoEconomia, int quantidadeLigacao,
									Date ultimaAlteracao, GerenciaRegional gerenciaRegional, Localidade localidadeElo,
									Localidade localidade, Quadra quadra, ConsumoTarifa consumoTarifa, ImovelPerfil imovelPerfil,
									LigacaoEsgotoSituacao ligacaoEsgotoSituacao, SetorComercial setorComercial,
									UnidadeNegocio unidadeNegocio, EsferaPoder esferaPoder, Categoria categoria,
									CategoriaTipo categoriaTipo, int quantidadeEconomiaRefaturamento,
									BigDecimal valorFaturadoEconomiaRefaturamento, int volumeFaturadoEconomiaRefaturamento,
									int quantidadeLigacaoRefaturamento) {

		this.id = id;
		this.referencia = referencia;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.indicadorConsumoReal = indicadorConsumoReal;
		this.indicadorHidrometro = indicadorHidrometro;
		this.indicadorPoco = indicadorPoco;
		this.indicadorVolumeFixadoEsgoto = indicadorVolumeFixadoEsgoto;
		this.percentualEsgoto = percentualEsgoto;
		this.quantidadeConsumo = quantidadeConsumo;
		this.quantidadeEconomia = quantidadeEconomia;
		this.valorFaturadoEconomia = valorFaturadoEconomia;
		this.volumeFaturadoEconomia = volumeFaturadoEconomia;
		this.quantidadeLigacao = quantidadeLigacao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.gerenciaRegional = gerenciaRegional;
		this.localidadeElo = localidadeElo;
		this.localidade = localidade;
		this.quadra = quadra;
		this.consumoTarifa = consumoTarifa;
		this.imovelPerfil = imovelPerfil;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.setorComercial = setorComercial;
		this.unidadeNegocio = unidadeNegocio;
		this.esferaPoder = esferaPoder;
		this.categoria = categoria;
		this.categoriaTipo = categoriaTipo;
		this.quantidadeEconomiaRefaturamento = quantidadeEconomiaRefaturamento;
		this.valorFaturadoEconomiaRefaturamento = valorFaturadoEconomiaRefaturamento;
		this.volumeFaturadoEconomiaRefaturamento = volumeFaturadoEconomiaRefaturamento;
		this.quantidadeLigacaoRefaturamento = quantidadeLigacaoRefaturamento;
	}

	/** default constructor */
	public HistogramaEsgotoEconomia() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public int getReferencia(){

		return this.referencia;
	}

	public void setReferencia(int referencia){

		this.referencia = referencia;
	}

	public int getCodigoSetorComercial(){

		return this.codigoSetorComercial;
	}

	public void setCodigoSetorComercial(int codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public int getNumeroQuadra(){

		return this.numeroQuadra;
	}

	public void setNumeroQuadra(int numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public short getIndicadorConsumoReal(){

		return this.indicadorConsumoReal;
	}

	public void setIndicadorConsumoReal(short indicadorConsumoReal){

		this.indicadorConsumoReal = indicadorConsumoReal;
	}

	public short getIndicadorHidrometro(){

		return this.indicadorHidrometro;
	}

	public void setIndicadorHidrometro(short indicadorHidrometro){

		this.indicadorHidrometro = indicadorHidrometro;
	}

	public short getIndicadorPoco(){

		return this.indicadorPoco;
	}

	public void setIndicadorPoco(short indicadorPoco){

		this.indicadorPoco = indicadorPoco;
	}

	public short getIndicadorVolumeFixadoEsgoto(){

		return this.indicadorVolumeFixadoEsgoto;
	}

	public void setIndicadorVolumeFixadoEsgoto(short indicadorVolumeFixadoEsgoto){

		this.indicadorVolumeFixadoEsgoto = indicadorVolumeFixadoEsgoto;
	}

	public short getPercentualEsgoto(){

		return this.percentualEsgoto;
	}

	public void setPercentualEsgoto(short percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	public int getQuantidadeConsumo(){

		return this.quantidadeConsumo;
	}

	public void setQuantidadeConsumo(int quantidadeConsumo){

		this.quantidadeConsumo = quantidadeConsumo;
	}

	public int getQuantidadeEconomia(){

		return this.quantidadeEconomia;
	}

	public void setQuantidadeEconomia(int quantidadeEconomia){

		this.quantidadeEconomia = quantidadeEconomia;
	}

	public BigDecimal getValorFaturadoEconomia(){

		return this.valorFaturadoEconomia;
	}

	public void setValorFaturadoEconomia(BigDecimal valorFaturadoEconomia){

		this.valorFaturadoEconomia = valorFaturadoEconomia;
	}

	public int getVolumeFaturadoEconomia(){

		return this.volumeFaturadoEconomia;
	}

	public void setVolumeFaturadoEconomia(int volumeFaturadoEconomia){

		this.volumeFaturadoEconomia = volumeFaturadoEconomia;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public GerenciaRegional getGerenciaRegional(){

		return this.gerenciaRegional;
	}

	public void setGerenciaRegional(GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public Localidade getLocalidadeElo(){

		return this.localidadeElo;
	}

	public void setLocalidadeElo(Localidade localidadeElo){

		this.localidadeElo = localidadeElo;
	}

	public Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public Quadra getQuadra(){

		return this.quadra;
	}

	public void setQuadra(Quadra quadra){

		this.quadra = quadra;
	}

	public ConsumoTarifa getConsumoTarifa(){

		return this.consumoTarifa;
	}

	public void setConsumoTarifa(ConsumoTarifa consumoTarifa){

		this.consumoTarifa = consumoTarifa;
	}

	public ImovelPerfil getImovelPerfil(){

		return this.imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return this.ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public SetorComercial getSetorComercial(){

		return this.setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public UnidadeNegocio getUnidadeNegocio(){

		return this.unidadeNegocio;
	}

	public void setUnidadeNegocio(UnidadeNegocio unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	public EsferaPoder getEsferaPoder(){

		return this.esferaPoder;
	}

	public void setEsferaPoder(EsferaPoder esferaPoder){

		this.esferaPoder = esferaPoder;
	}

	public Categoria getCategoria(){

		return this.categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public CategoriaTipo getCategoriaTipo(){

		return this.categoriaTipo;
	}

	public void setCategoriaTipo(CategoriaTipo categoriaTipo){

		this.categoriaTipo = categoriaTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public int getQuantidadeLigacao(){

		return quantidadeLigacao;
	}

	public void setQuantidadeLigacao(int quantidadeLigacao){

		this.quantidadeLigacao = quantidadeLigacao;
	}

	public int getQuantidadeEconomiaRefaturamento(){

		return quantidadeEconomiaRefaturamento;
	}

	public void setQuantidadeEconomiaRefaturamento(int quantidadeEconomiaRefaturamento){

		this.quantidadeEconomiaRefaturamento = quantidadeEconomiaRefaturamento;
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

	public int getQuantidadeLigacaoRefaturamento(){

		return quantidadeLigacaoRefaturamento;
	}

	public void setQuantidadeLigacaoRefaturamento(int quantidadeLigacaoRefaturamento){

		this.quantidadeLigacaoRefaturamento = quantidadeLigacaoRefaturamento;
	}

}
