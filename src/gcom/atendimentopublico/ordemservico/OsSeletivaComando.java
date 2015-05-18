
package gcom.atendimentopublico.ordemservico;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.faturamento.FaturamentoGrupo;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.IoUtil;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OsSeletivaComando
				extends ObjetoTransacao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Date tempoComando;

	private String descricaoTitulo;

	private byte[] arquivoImovel;

	private Integer numeroPontosUtilizacaoInicial;

	private Integer numeroPontosUtilizacaoFinal;

	private Integer numeroFaixaConsumoInicial;

	private Integer numeroFaixaConsumoFinal;

	private Integer quantidadeDebitoInicial;

	private Integer quantidadeDebitoFinal;

	private BigDecimal valorDebito;

	private Integer numeroOcorrenciasConsecutivas;

	private Integer quantidadeOrdensServicoGeradas;

	private FaturamentoGrupo faturamentoGrupo;

	private AreaConstruidaFaixa areaConstruidaFaixa;

	/** nullable persistent field */
	private Date tempoRealizacao;

	/** persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Integer quantidadeMaximaOrdens;

	/** nullable persistent field */
	private Integer codigoElo;

	/** nullable persistent field */
	// private Integer codigoSetorComercialInicial;

	/** nullable persistent field */
	// private Integer codigoSetorComercialFinal;

	/** nullable persistent field */
	// private Integer numeroQuadraInicial;

	/** nullable persistent field */
	// private Integer numeroQuadraFinal;

	/** nullable persistent field */
	private Integer indicadorImovelCondominio;

	/** nullable persistent field */
	// private Integer anoMesInstalacaoHidrometro;

	/** nullable persistent field */
	// private Integer quantidadeGerada;

	/** nullable persistent field */
	// private Integer numeroConsumoMedio;

	/** nullable persistent field */
	private Integer numeroConsumoEconomia;

	/** nullable persistent field */
	private Integer quantidadeEconomiasInicial;

	/** nullable persistent field */
	private Integer quantidadeEconomiasFinal;

	/** nullable persistent field */
	private Integer quantidadeDocumentosInicial;

	/** nullable persistent field */
	private Integer quantidadeDocumentosFinal;

	/** nullable persistent field */
	// private Integer quantidadeOcorrenciasConsecutivas;

	/** nullable persistent field */
	private BigDecimal numeroAreaConstruidaInicial;

	/** nullable persistent field */
	private BigDecimal numeroAreaConstruidaFinal;

	/** nullable persistent field */
	private Integer numeroMoradoresInicial;

	/** nullable persistent field */
	private Integer numeroMoradoresFinal;

	/** persistent field */
	// private LeituraAnormalidade leituraAnormalidade;

	/** persistent field */
	// private Localidade localidadeFinal;

	/** persistent field */
	// private Localidade localidadeInicial;

	/** persistent field */
	// private Quadra quadraInicial;

	/** persistent field */
	// private Quadra quadraFinal;

	/** persistent field */
	// private ImovelPerfil imovelPerfil;

	/** persistent field */
	// private HidrometroMarca hidrometroMarca;

	/** persistent field */
	// private Rota rotaFinal;

	/** persistent field */
	// private Rota rotaInicial;

	/** persistent field */
	// private SetorComercial setorComercialFinal;

	/** persistent field */
	// private SetorComercial setorComercialInicial;

	/** persistent field */
	private Usuario usuario;

	/** persistent field */
	private Empresa empresa;

	/** persistent field */
	// private Categoria categoria;

	/** persistent field */
	// private HidrometroCapacidade hidrometroCapacidade;

	/** persistent field */
	private MedicaoTipo medicaoTipo;

	/** persistent field */
	// private Subcategoria subcategoria;

	/** persistent field */
	private ServicoTipo servicoTipo;

	private Collection<OsSeletivaComandoLocalGeo> osSeletivaComandoLocalGeo;

	private Collection<OsSeletivaComandoImovelPerfil> osSeletivaComandoImovelPerfil;

	private Collection<OsSeletivaComandoCategoriaSubcategoria> osSeletivaComandoCategoriaSubcategoria;

	private Collection<OsSeletivaComandoLigacaoAgua> osSeletivaComandoLigacaoAgua;

	private Collection<OsSeletivaComandoLigacaoEsgoto> osSeletivaComandoLigacaoEsgoto;

	private Collection<OsSeletivaComandoConsumoMedio> osSeletivaComandoConsumoMedio;

	private Collection<OsSeletivaComandoHidrometroMarca> osSeletivaComandoHidrometroMarca;

	private Collection<OsSeletivaComandoHidrometroClasse> osSeletivaComandoHidrometroClasse;

	private Collection<OsSeletivaComandoHidrometroProtecao> osSeletivaComandoHidrometroProtecao;

	private Collection<OsSeletivaComandoHidrometroLocalInstacao> osSeletivaComandoHidrometroLocalInstacao;

	private Collection<OsSeletivaComandoLeituraAnormalidade> osSeletivaComandoLeituraAnormalidade;

	private Collection<OsSeletivaComandoHidrometroDiametro> osSeletivaComandoHidrometroDiametro;

	private Collection<OrdemServico> ordemServico;

	private Integer referenciaUltimaAfericaoHidrometro;

	/** full constructor */
	public OsSeletivaComando(Integer id, Date tempoComando, Date tempoRealizacao, Date ultimaAlteracao, Integer quantidadeMaximaOrdens,
								Integer codigoElo, Integer indicadorImovelCondominio, Integer numeroConsumoEconomia,
								Integer quantidadeEconomiasInicial, Integer quantidadeEconomiasFinal, Integer quantidadeDocumentosInicial,
								Integer quantidadeDocumentosFinal, BigDecimal numeroAreaConstruidaInicial,
								BigDecimal numeroAreaConstruidaFinal, Integer numeroMoradoresInicial, Integer numeroMoradoresFinal,
								Usuario usuario, Empresa empresa, MedicaoTipo medicaoTipo, ServicoTipo servicoTipo, String descricaoTitulo,
								Blob arquivoImovel, Integer numeroPontosUtilizacaoInicial, Integer numeroPontosUtilizacaoFinal,
								Integer numeroFaixaConsumoInicial, Integer numeroFaixaConsumoFinal, Integer quantidadeDebitoInicial,
								Integer quantidadeDebitoFinal, BigDecimal valorDebito, Integer numeroOcorrenciasConsecutivas,
								Integer quantidadeOrdensServicoGeradas, FaturamentoGrupo faturamentoGrupo,
								AreaConstruidaFaixa areaConstruidaFaixa, Collection<OsSeletivaComandoLocalGeo> osSeletivaComandoLocalGeo,
								Collection<OsSeletivaComandoImovelPerfil> osSeletivaComandoImovelPerfil,
								Collection<OsSeletivaComandoCategoriaSubcategoria> osSeletivaComandoCategoriaSubcategoria,
								Collection<OsSeletivaComandoLigacaoAgua> osSeletivaComandoLigacaoAgua,
								Collection<OsSeletivaComandoLigacaoEsgoto> osSeletivaComandoLigacaoEsgoto,
								Collection<OsSeletivaComandoConsumoMedio> osSeletivaComandoConsumoMedio,
								Collection<OsSeletivaComandoHidrometroMarca> osSeletivaComandoHidrometroMarca,
								Collection<OsSeletivaComandoHidrometroClasse> osSeletivaComandoHidrometroClasse,
								Collection<OsSeletivaComandoHidrometroProtecao> osSeletivaComandoHidrometroProtecao,
								Collection<OsSeletivaComandoHidrometroLocalInstacao> osSeletivaComandoHidrometroLocalInstacao,
								Collection<OsSeletivaComandoLeituraAnormalidade> osSeletivaComandoLeituraAnormalidade,
								Collection<OsSeletivaComandoHidrometroDiametro> osSeletivaComandoHidrometroDiametro,
								Collection<OrdemServico> ordemServico) {

		this.id = id;
		this.tempoComando = tempoComando;
		this.tempoRealizacao = tempoRealizacao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.quantidadeMaximaOrdens = quantidadeMaximaOrdens;
		this.codigoElo = codigoElo;
		this.indicadorImovelCondominio = indicadorImovelCondominio;
		this.numeroConsumoEconomia = numeroConsumoEconomia;
		this.quantidadeEconomiasInicial = quantidadeEconomiasInicial;
		this.quantidadeEconomiasFinal = quantidadeEconomiasFinal;
		this.quantidadeDocumentosInicial = quantidadeDocumentosInicial;
		this.quantidadeDocumentosFinal = quantidadeDocumentosFinal;
		this.numeroAreaConstruidaInicial = numeroAreaConstruidaInicial;
		this.numeroAreaConstruidaFinal = numeroAreaConstruidaFinal;
		this.numeroMoradoresInicial = numeroMoradoresInicial;
		this.numeroMoradoresFinal = numeroMoradoresFinal;
		this.usuario = usuario;
		this.empresa = empresa;
		this.medicaoTipo = medicaoTipo;
		this.servicoTipo = servicoTipo;
		this.descricaoTitulo = descricaoTitulo;
		this.arquivoImovel = IoUtil.toByteArray(arquivoImovel);
		this.numeroPontosUtilizacaoInicial = numeroPontosUtilizacaoInicial;
		this.numeroPontosUtilizacaoFinal = numeroPontosUtilizacaoFinal;
		this.numeroFaixaConsumoInicial = numeroFaixaConsumoInicial;
		this.numeroFaixaConsumoFinal = numeroFaixaConsumoFinal;
		this.quantidadeDebitoInicial = quantidadeDebitoInicial;
		this.quantidadeDebitoFinal = quantidadeDebitoFinal;
		this.valorDebito = valorDebito;
		this.numeroOcorrenciasConsecutivas = numeroOcorrenciasConsecutivas;
		this.quantidadeOrdensServicoGeradas = quantidadeOrdensServicoGeradas;
		this.faturamentoGrupo = faturamentoGrupo;
		this.areaConstruidaFaixa = areaConstruidaFaixa;
	}

	/** default constructor */
	public OsSeletivaComando() {

	}

	/** minimal constructor */
	public OsSeletivaComando(Integer id, Date tempoComando, Date ultimaAlteracao, Usuario usuario, Empresa empresa,
								MedicaoTipo medicaoTipo, gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo,
								String descricaoTitulo) {

		this.id = id;
		this.tempoComando = tempoComando;
		this.ultimaAlteracao = ultimaAlteracao;
		this.usuario = usuario;
		this.empresa = empresa;
		this.medicaoTipo = medicaoTipo;
		this.servicoTipo = servicoTipo;
		this.descricaoTitulo = descricaoTitulo;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getTempoComando(){

		return this.tempoComando;
	}

	public void setTempoComando(Date tempoComando){

		this.tempoComando = tempoComando;
	}

	public Date getTempoRealizacao(){

		return this.tempoRealizacao;
	}

	public void setTempoRealizacao(Date tempoRealizacao){

		this.tempoRealizacao = tempoRealizacao;
	}

	@Override
	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getQuantidadeMaximaOrdens(){

		return this.quantidadeMaximaOrdens;
	}

	public void setQuantidadeMaximaOrdens(Integer quantidadeMaximaOrdens){

		this.quantidadeMaximaOrdens = quantidadeMaximaOrdens;
	}

	public Integer getCodigoElo(){

		return this.codigoElo;
	}

	public void setCodigoElo(Integer codigoElo){

		this.codigoElo = codigoElo;
	}

	public Integer getIndicadorImovelCondominio(){

		return this.indicadorImovelCondominio;
	}

	public void setIndicadorImovelCondominio(Integer indicadorImovelCondominio){

		this.indicadorImovelCondominio = indicadorImovelCondominio;
	}

	public Integer getNumeroConsumoEconomia(){

		return this.numeroConsumoEconomia;
	}

	public void setNumeroConsumoEconomia(Integer numeroConsumoEconomia){

		this.numeroConsumoEconomia = numeroConsumoEconomia;
	}

	public Integer getQuantidadeEconomiasInicial(){

		return this.quantidadeEconomiasInicial;
	}

	public void setQuantidadeEconomiasInicial(Integer quantidadeEconomiasInicial){

		this.quantidadeEconomiasInicial = quantidadeEconomiasInicial;
	}

	public Integer getQuantidadeEconomiasFinal(){

		return this.quantidadeEconomiasFinal;
	}

	public void setQuantidadeEconomiasFinal(Integer quantidadeEconomiasFinal){

		this.quantidadeEconomiasFinal = quantidadeEconomiasFinal;
	}

	public Integer getQuantidadeDocumentosInicial(){

		return this.quantidadeDocumentosInicial;
	}

	public void setQuantidadeDocumentosInicial(Integer quantidadeDocumentosInicial){

		this.quantidadeDocumentosInicial = quantidadeDocumentosInicial;
	}

	public Integer getQuantidadeDocumentosFinal(){

		return this.quantidadeDocumentosFinal;
	}

	public void setQuantidadeDocumentosFinal(Integer quantidadeDocumentosFinal){

		this.quantidadeDocumentosFinal = quantidadeDocumentosFinal;
	}

	public BigDecimal getNumeroAreaConstruidaInicial(){

		return this.numeroAreaConstruidaInicial;
	}

	public void setNumeroAreaConstruidaInicial(BigDecimal numeroAreaConstruidaInicial){

		this.numeroAreaConstruidaInicial = numeroAreaConstruidaInicial;
	}

	public BigDecimal getNumeroAreaConstruidaFinal(){

		return this.numeroAreaConstruidaFinal;
	}

	public void setNumeroAreaConstruidaFinal(BigDecimal numeroAreaConstruidaFinal){

		this.numeroAreaConstruidaFinal = numeroAreaConstruidaFinal;
	}

	public Integer getNumeroMoradoresInicial(){

		return this.numeroMoradoresInicial;
	}

	public void setNumeroMoradoresInicial(Integer numeroMoradoresInicial){

		this.numeroMoradoresInicial = numeroMoradoresInicial;
	}

	public Integer getNumeroMoradoresFinal(){

		return this.numeroMoradoresFinal;
	}

	public void setNumeroMoradoresFinal(Integer numeroMoradoresFinal){

		this.numeroMoradoresFinal = numeroMoradoresFinal;
	}

	public Usuario getUsuario(){

		return this.usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	public Empresa getEmpresa(){

		return this.empresa;
	}

	public void setEmpresa(Empresa empresa){

		this.empresa = empresa;
	}

	public MedicaoTipo getMedicaoTipo(){

		return this.medicaoTipo;
	}

	public void setMedicaoTipo(MedicaoTipo medicaoTipo){

		this.medicaoTipo = medicaoTipo;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipo getServicoTipo(){

		return this.servicoTipo;
	}

	public void setServicoTipo(gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String getDescricaoTitulo(){

		return descricaoTitulo;
	}

	public void setDescricaoTitulo(String descricaoTitulo){

		this.descricaoTitulo = descricaoTitulo;
	}

	public byte[] getArquivoImovel(){

		return arquivoImovel;
	}

	public void setArquivoImovel(Blob arquivoImovel){

		this.arquivoImovel = IoUtil.toByteArray(arquivoImovel);
	}

	public void setArquivoImovel(byte[] arquivoImovel){

		this.arquivoImovel = arquivoImovel;
	}

	public Integer getNumeroPontosUtilizacaoInicial(){

		return numeroPontosUtilizacaoInicial;
	}

	public void setNumeroPontosUtilizacaoInicial(Integer numeroPontosUtilizacaoInicial){

		this.numeroPontosUtilizacaoInicial = numeroPontosUtilizacaoInicial;
	}

	public Integer getNumeroPontosUtilizacaoFinal(){

		return numeroPontosUtilizacaoFinal;
	}

	public void setNumeroPontosUtilizacaoFinal(Integer numeroPontosUtilizacaoFinal){

		this.numeroPontosUtilizacaoFinal = numeroPontosUtilizacaoFinal;
	}

	public Integer getNumeroFaixaConsumoInicial(){

		return numeroFaixaConsumoInicial;
	}

	public void setNumeroFaixaConsumoInicial(Integer numeroFaixaConsumoInicial){

		this.numeroFaixaConsumoInicial = numeroFaixaConsumoInicial;
	}

	public Integer getNumeroFaixaConsumoFinal(){

		return numeroFaixaConsumoFinal;
	}

	public void setNumeroFaixaConsumoFinal(Integer numeroFaixaConsumoFinal){

		this.numeroFaixaConsumoFinal = numeroFaixaConsumoFinal;
	}

	public Integer getQuantidadeDebitoInicial(){

		return quantidadeDebitoInicial;
	}

	public void setQuantidadeDebitoInicial(Integer quantidadeDebitoInicial){

		this.quantidadeDebitoInicial = quantidadeDebitoInicial;
	}

	public Integer getQuantidadeDebitoFinal(){

		return quantidadeDebitoFinal;
	}

	public void setQuantidadeDebitoFinal(Integer quantidadeDebitoFinal){

		this.quantidadeDebitoFinal = quantidadeDebitoFinal;
	}

	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	public Integer getNumeroOcorrenciasConsecutivas(){

		return numeroOcorrenciasConsecutivas;
	}

	public void setNumeroOcorrenciasConsecutivas(Integer numeroOcorrenciasConsecutivas){

		this.numeroOcorrenciasConsecutivas = numeroOcorrenciasConsecutivas;
	}

	public Integer getQuantidadeOrdensServicoGeradas(){

		return quantidadeOrdensServicoGeradas;
	}

	public void setQuantidadeOrdensServicoGeradas(Integer quantidadeOrdensServicoGeradas){

		this.quantidadeOrdensServicoGeradas = quantidadeOrdensServicoGeradas;
	}

	public FaturamentoGrupo getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(FaturamentoGrupo faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public AreaConstruidaFaixa getAreaConstruidaFaixa(){

		return areaConstruidaFaixa;
	}

	public void setAreaConstruidaFaixa(AreaConstruidaFaixa areaConstruidaFaixa){

		this.areaConstruidaFaixa = areaConstruidaFaixa;
	}

	public Collection<OsSeletivaComandoLocalGeo> getOsSeletivaComandoLocalGeo(){

		return osSeletivaComandoLocalGeo;
	}

	public void setOsSeletivaComandoLocalGeo(Collection<OsSeletivaComandoLocalGeo> osSeletivaComandoLocalGeo){

		this.osSeletivaComandoLocalGeo = osSeletivaComandoLocalGeo;
	}

	public Collection<OsSeletivaComandoImovelPerfil> getOsSeletivaComandoImovelPerfil(){

		return osSeletivaComandoImovelPerfil;
	}

	public void setOsSeletivaComandoImovelPerfil(Collection<OsSeletivaComandoImovelPerfil> osSeletivaComandoImovelPerfil){

		this.osSeletivaComandoImovelPerfil = osSeletivaComandoImovelPerfil;
	}

	public Collection<OsSeletivaComandoCategoriaSubcategoria> getOsSeletivaComandoCategoriaSubcategoria(){

		return osSeletivaComandoCategoriaSubcategoria;
	}

	public void setOsSeletivaComandoCategoriaSubcategoria(
					Collection<OsSeletivaComandoCategoriaSubcategoria> osSeletivaComandoCategoriaSubcategoria){

		this.osSeletivaComandoCategoriaSubcategoria = osSeletivaComandoCategoriaSubcategoria;
	}

	public Collection<OsSeletivaComandoLigacaoAgua> getOsSeletivaComandoLigacaoAgua(){

		return osSeletivaComandoLigacaoAgua;
	}

	public void setOsSeletivaComandoLigacaoAgua(Collection<OsSeletivaComandoLigacaoAgua> osSeletivaComandoLigacaoAgua){

		this.osSeletivaComandoLigacaoAgua = osSeletivaComandoLigacaoAgua;
	}

	public Collection<OsSeletivaComandoLigacaoEsgoto> getOsSeletivaComandoLigacaoEsgoto(){

		return osSeletivaComandoLigacaoEsgoto;
	}

	public void setOsSeletivaComandoLigacaoEsgoto(Collection<OsSeletivaComandoLigacaoEsgoto> osSeletivaComandoLigacaoEsgoto){

		this.osSeletivaComandoLigacaoEsgoto = osSeletivaComandoLigacaoEsgoto;
	}

	public Collection<OsSeletivaComandoConsumoMedio> getOsSeletivaComandoConsumoMedio(){

		return osSeletivaComandoConsumoMedio;
	}

	public void setOsSeletivaComandoConsumoMedio(Collection<OsSeletivaComandoConsumoMedio> osSeletivaComandoConsumoMedio){

		this.osSeletivaComandoConsumoMedio = osSeletivaComandoConsumoMedio;
	}

	public Collection<OsSeletivaComandoHidrometroMarca> getOsSeletivaComandoHidrometroMarca(){

		return osSeletivaComandoHidrometroMarca;
	}

	public void setOsSeletivaComandoHidrometroMarca(Collection<OsSeletivaComandoHidrometroMarca> osSeletivaComandoHidrometroMarca){

		this.osSeletivaComandoHidrometroMarca = osSeletivaComandoHidrometroMarca;
	}

	public Collection<OsSeletivaComandoHidrometroClasse> getOsSeletivaComandoHidrometroClasse(){

		return osSeletivaComandoHidrometroClasse;
	}

	public void setOsSeletivaComandoHidrometroClasse(Collection<OsSeletivaComandoHidrometroClasse> osSeletivaComandoHidrometroClasse){

		this.osSeletivaComandoHidrometroClasse = osSeletivaComandoHidrometroClasse;
	}

	public Collection<OsSeletivaComandoHidrometroProtecao> getOsSeletivaComandoHidrometroProtecao(){

		return osSeletivaComandoHidrometroProtecao;
	}

	public void setOsSeletivaComandoHidrometroProtecao(Collection<OsSeletivaComandoHidrometroProtecao> osSeletivaComandoHidrometroProtecao){

		this.osSeletivaComandoHidrometroProtecao = osSeletivaComandoHidrometroProtecao;
	}

	public Collection<OsSeletivaComandoHidrometroLocalInstacao> getOsSeletivaComandoHidrometroLocalInstacao(){

		return osSeletivaComandoHidrometroLocalInstacao;
	}

	public void setOsSeletivaComandoHidrometroLocalInstacao(
					Collection<OsSeletivaComandoHidrometroLocalInstacao> osSeletivaComandoHidrometroLocalInstacao){

		this.osSeletivaComandoHidrometroLocalInstacao = osSeletivaComandoHidrometroLocalInstacao;
	}

	public Collection<OsSeletivaComandoLeituraAnormalidade> getOsSeletivaComandoLeituraAnormalidade(){

		return osSeletivaComandoLeituraAnormalidade;
	}

	public void setOsSeletivaComandoLeituraAnormalidade(
					Collection<OsSeletivaComandoLeituraAnormalidade> osSeletivaComandoLeituraAnormalidade){

		this.osSeletivaComandoLeituraAnormalidade = osSeletivaComandoLeituraAnormalidade;
	}

	public Collection<OsSeletivaComandoHidrometroDiametro> getOsSeletivaComandoHidrometroDiametro(){

		return osSeletivaComandoHidrometroDiametro;
	}

	public void setOsSeletivaComandoHidrometroDiametro(Collection<OsSeletivaComandoHidrometroDiametro> osSeletivaComandoHidrometroDiametro){

		this.osSeletivaComandoHidrometroDiametro = osSeletivaComandoHidrometroDiametro;
	}

	public Collection<OrdemServico> getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(Collection<OrdemServico> ordemServico){

		this.ordemServico = ordemServico;
	}

	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof OsSeletivaComando)){
			return false;
		}
		OsSeletivaComando castOther = (OsSeletivaComando) other;

		return (this.getId().equals(castOther.getId()));
	}

	public Filtro retornaFiltro(){

		FiltroOsSeletivaComando filtroOsSeletivaComando = new FiltroOsSeletivaComando();

		filtroOsSeletivaComando.adicionarParametro(new ParametroSimples(FiltroOsSeletivaComando.ID, this.getId()));
		filtroOsSeletivaComando.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, this.getId()));

		return filtroOsSeletivaComando;
	}
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Integer getReferenciaUltimaAfericaoHidrometro(){

		return referenciaUltimaAfericaoHidrometro;
	}

	public void setReferenciaUltimaAfericaoHidrometro(Integer referenciaUltimaAfericaoHidrometro){

		this.referenciaUltimaAfericaoHidrometro = referenciaUltimaAfericaoHidrometro;
	}

}
