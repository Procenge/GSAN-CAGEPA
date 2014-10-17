
package gcom.integracao.cagepa.faturamento;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Date;

public class MetLigacao
				extends ObjetoTransacao {

	private MetLigacaoPK comp_id;

	private Integer localidadeId;

	private Integer codigoSetorComercial;

	private Integer numeroQuadra;

	private Short numeroLote;

	private Short sequenciaMacro;

	private String nomeConsumidor;

	private String tipoResponsavel;

	private Short emiteFatura;

	private Integer responsavelId;

	private Integer tarifaUsuarioId;

	private Integer situacaoAguaId;

	private Integer situacaoEsgotoId;

	private Integer grandeCliente;

	private Integer cep;

	private Integer consumoFixoAgua;

	private Integer consumoFixoEsgoto;

	private Integer bancoId;

	private String logradouro;

	private String bairro;

	private String numeroAltera;

	private Date dataVencimentoFatura;

	private Short numeroEconomiasResidenciais;

	private Short numeroEconomiasComerciais;

	private Short numeroEconomiasIndustriais;

	private Short numeroEconomiasPublicas;

	private Integer ligacaoMacroId;

	private Date dataUltimoCorte;

	private Integer percentualTarifa;

	private Short numeroDigitosHidrometro;

	private Integer fone;

	private Short lacreHidrometroId;

	private String numeroHidrometro;

	private String instalacaoHidrometro;

	private String marcaHidrometro;

	private String capacidadeHidrometro;

	private Integer mediaConsumo;

	private Integer valorLeituraAnterior;

	private Date dataLeituraAnterior;

	private Integer indicadorLeituraAnterior;

	private Date dataInstalacaoHidrometro;

	private Integer valorLeituraMinima;

	private Integer valorLeituraMaxima;

	private String indicadorConsumoReal2Meses;

	private Short indicadorAnormalidadeAC6Meses;

	private Short indicadorAnormalidadeBC4Meses;

	private Short indicadorCpfCnpj;

	private Long numeroCpfCnpj;

	private BigDecimal percentualImposto;

	private Integer mesLeitura1;

	private Integer ocorrenciaLeitura1;

	private Integer consumoLeitura1;

	private Integer mesLeitura2;

	private Integer ocorrenciaLeitura2;

	private Integer consumoLeitura2;

	private Integer mesLeitura3;

	private Integer ocorrenciaLeitura3;

	private Integer consumoLeitura3;

	private Integer mesLeitura4;

	private Integer ocorrenciaLeitura4;

	private Integer consumoLeitura4;

	private Integer mesLeitura5;

	private Integer ocorrenciaLeitura5;

	private Integer consumoLeitura5;

	private Integer mesLeitura6;

	private Integer ocorrenciaLeitura6;

	private Integer consumoLeitura6;

	private BigDecimal valorCredito;

	private String descricaoRubrica1;

	private BigDecimal valorRubrica1;

	private String descricaoRubrica2;

	private BigDecimal valorRubrica2;

	private String descricaoRubrica3;

	private BigDecimal valorRubrica3;

	private String descricaoRubrica4;

	private BigDecimal valorRubrica4;

	private String descricaoRubrica5;

	private BigDecimal valorRubrica5;

	private String descricaoRubrica6;

	private BigDecimal valorRubrica6;

	private String descricaoRubrica7;

	private BigDecimal valorRubrica7;

	private String descricaoRubrica8;

	private BigDecimal valorRubrica8;

	private String descricaoRubrica9;

	private BigDecimal valorRubrica9;

	private String descricaoRubrica10;

	private BigDecimal valorRubrica10;

	private Integer valorLeituraAtual;

	private Integer valorLeituraAtribuida;

	private Integer consumo;

	private Integer consumoFaturado;

	private Integer consumoMinimo;

	private Integer consumoRateio;

	private Integer ocorrenciaId;

	private String ocorrenciaConsumoId;

	private Integer faturaEmitida;

	private Integer localidadeFaturaEntregue;

	private Integer diasFaturados;

	private Date dataLeituraAtual;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorServico;

	private BigDecimal valorDevolvido;

	private BigDecimal valorSaldoDevolvido;

	private Short indicadorGrandeConsumoFaturado;

	private String indicadorContatarUsuario;

	private Short indicadorReligarAgua;

	private Integer servicoReligacaoId;

	private BigDecimal valorReligacao;

	private Integer servicoSancaoId;

	private BigDecimal valorSancao;

	private String tipoConsumo;

	private Short condicaoLeitura;

	private String alteNumeroImovel;

	private String idDvCorteShdLigacao;

	private String idDvNumeroHidrometro;

	private String idDvCategoriaEconomia;

	private String idDvLogradouro;

	private String idDvRevisaoQuadra;

	private String idDvFiscConsumo;

	private String idDvUsuarioNaoMdChd;

	private Short indicadorHidrometroNaoLacrado;

	private Integer numeroFoneContacto;

	private Integer funcionarioId;

	private String cidade;

	private Integer cicloId;

	private Short tipoColetaLeitura;

	private Short debitoAutomatico;

	private Date dataProximaLeitura;

	private Date dataGeracaoGCS;

	private Integer livroSetor;

	private Integer reavisoLigacaoId;

	private Integer quantidadeContasRevisao;

	private BigDecimal valorTotalRevisao;

	private Integer regionalId;

	private BigDecimal valorImpostoRetorno;

	private Date dataEmissao;

	private Integer statusId;

	private Date timedownload;

	private BigDecimal totalEmitido;

	private Date dataTarefa;

	private Integer geradoGeraOk;

	private Integer loginGeraOk;

	private Date dataGeraOk;

	private Integer loginDistribuido;

	private Date dataDistribuido;

	private Integer loginRemanejado;

	private Date dataRemanejado;

	private Integer loginEnviadoCol;

	private Date dataEnviadoCol;

	private Integer enviadoCol;

	private Integer indicadorFaturaRetida;

	private String versao;

	private Integer reimpressa;

	private Integer reimpressaNotificacao;

	private Date dataLiberacao;

	private Integer indicadorExisteDebito;

	private Integer descarregamento;

	private String teveFaltaLeituraAnterior;

	private Integer valorUltimaLeituraReal;

	private Integer numeroMesesUltimaLeituraReal;

	public MetLigacao() {

	}

	public MetLigacaoPK getComp_id(){

		return comp_id;
	}

	public void setComp_id(MetLigacaoPK comp_id){

		this.comp_id = comp_id;
	}

	public Integer getLocalidadeFaturaEntregue(){

		return localidadeFaturaEntregue;
	}

	public void setLocalidadeFaturaEntregue(Integer localidadeFaturaEntregue){

		this.localidadeFaturaEntregue = localidadeFaturaEntregue;
	}

	public Integer getLocalidadeId(){

		return localidadeId;
	}

	public void setLocalidadeId(Integer localidadeId){

		this.localidadeId = localidadeId;
	}

	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Short getNumeroLote(){

		return numeroLote;
	}

	public void setNumeroLote(Short numeroLote){

		this.numeroLote = numeroLote;
	}

	public Short getSequenciaMacro(){

		return sequenciaMacro;
	}

	public void setSequenciaMacro(Short sequenciaMacro){

		this.sequenciaMacro = sequenciaMacro;
	}

	public String getNomeConsumidor(){

		return nomeConsumidor;
	}

	public void setNomeConsumidor(String nomeConsumidor){

		this.nomeConsumidor = nomeConsumidor;
	}

	public String getTipoResponsavel(){

		return tipoResponsavel;
	}

	public void setTipoResponsavel(String tipoResponsavel){

		this.tipoResponsavel = tipoResponsavel;
	}

	public Short getEmiteFatura(){

		return emiteFatura;
	}

	public void setEmiteFatura(Short emiteFatura){

		this.emiteFatura = emiteFatura;
	}

	public Integer getResponsavelId(){

		return responsavelId;
	}

	public void setResponsavelId(Integer responsavelId){

		this.responsavelId = responsavelId;
	}

	public Integer getTarifaUsuarioId(){

		return tarifaUsuarioId;
	}

	public void setTarifaUsuarioId(Integer tarifaUsuarioId){

		this.tarifaUsuarioId = tarifaUsuarioId;
	}

	public Integer getSituacaoAguaId(){

		return situacaoAguaId;
	}

	public void setSituacaoAguaId(Integer situacaoAguaId){

		this.situacaoAguaId = situacaoAguaId;
	}

	public Integer getSituacaoEsgotoId(){

		return situacaoEsgotoId;
	}

	public void setSituacaoEsgotoId(Integer situacaoEsgotoId){

		this.situacaoEsgotoId = situacaoEsgotoId;
	}

	public Integer getGrandeCliente(){

		return grandeCliente;
	}

	public void setGrandeCliente(Integer grandeCliente){

		this.grandeCliente = grandeCliente;
	}

	public Integer getCep(){

		return cep;
	}

	public void setCep(Integer cep){

		this.cep = cep;
	}

	public Integer getConsumoFixoAgua(){

		return consumoFixoAgua;
	}

	public void setConsumoFixoAgua(Integer consumoFixoAgua){

		this.consumoFixoAgua = consumoFixoAgua;
	}

	public Integer getConsumoFixoEsgoto(){

		return consumoFixoEsgoto;
	}

	public void setConsumoFixoEsgoto(Integer consumoFixoEsgoto){

		this.consumoFixoEsgoto = consumoFixoEsgoto;
	}

	public Integer getBancoId(){

		return bancoId;
	}

	public void setBancoId(Integer bancoId){

		this.bancoId = bancoId;
	}

	public String getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(String logradouro){

		this.logradouro = logradouro;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getNumeroAltera(){

		return numeroAltera;
	}

	public void setNumeroAltera(String numeroAltera){

		this.numeroAltera = numeroAltera;
	}

	public Date getDataVencimentoFatura(){

		return dataVencimentoFatura;
	}

	public void setDataVencimentoFatura(Date dataVencimentoFatura){

		this.dataVencimentoFatura = dataVencimentoFatura;
	}

	public Short getNumeroEconomiasResidenciais(){

		return numeroEconomiasResidenciais;
	}

	public void setNumeroEconomiasResidenciais(Short numeroEconomiasResidenciais){

		this.numeroEconomiasResidenciais = numeroEconomiasResidenciais;
	}

	public Short getNumeroEconomiasComerciais(){

		return numeroEconomiasComerciais;
	}

	public void setNumeroEconomiasComerciais(Short numeroEconomiasComerciais){

		this.numeroEconomiasComerciais = numeroEconomiasComerciais;
	}

	public Short getNumeroEconomiasIndustriais(){

		return numeroEconomiasIndustriais;
	}

	public void setNumeroEconomiasIndustriais(Short numeroEconomiasIndustriais){

		this.numeroEconomiasIndustriais = numeroEconomiasIndustriais;
	}

	public Short getNumeroEconomiasPublicas(){

		return numeroEconomiasPublicas;
	}

	public void setNumeroEconomiasPublicas(Short numeroEconomiasPublicas){

		this.numeroEconomiasPublicas = numeroEconomiasPublicas;
	}

	public Integer getLigacaoMacroId(){

		return ligacaoMacroId;
	}

	public void setLigacaoMacroId(Integer ligacaoMacroId){

		this.ligacaoMacroId = ligacaoMacroId;
	}

	public Date getDataUltimoCorte(){

		return dataUltimoCorte;
	}

	public void setDataUltimoCorte(Date dataUltimoCorte){

		this.dataUltimoCorte = dataUltimoCorte;
	}

	public Integer getPercentualTarifa(){

		return percentualTarifa;
	}

	public void setPercentualTarifa(Integer percentualTarifa){

		this.percentualTarifa = percentualTarifa;
	}

	public Short getNumeroDigitosHidrometro(){

		return numeroDigitosHidrometro;
	}

	public void setNumeroDigitosHidrometro(Short numeroDigitosHidrometro){

		this.numeroDigitosHidrometro = numeroDigitosHidrometro;
	}

	public Integer getFone(){

		return fone;
	}

	public void setFone(Integer fone){

		this.fone = fone;
	}

	public Short getLacreHidrometroId(){

		return lacreHidrometroId;
	}

	public void setLacreHidrometroId(Short lacreHidrometroId){

		this.lacreHidrometroId = lacreHidrometroId;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getInstalacaoHidrometro(){

		return instalacaoHidrometro;
	}

	public void setInstalacaoHidrometro(String instalacaoHidrometro){

		this.instalacaoHidrometro = instalacaoHidrometro;
	}

	public String getMarcaHidrometro(){

		return marcaHidrometro;
	}

	public void setMarcaHidrometro(String marcaHidrometro){

		this.marcaHidrometro = marcaHidrometro;
	}

	public String getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	public void setCapacidadeHidrometro(String capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
	}

	public Integer getMediaConsumo(){

		return mediaConsumo;
	}

	public void setMediaConsumo(Integer mediaConsumo){

		this.mediaConsumo = mediaConsumo;
	}

	public Integer getValorLeituraAnterior(){

		return valorLeituraAnterior;
	}

	public void setValorLeituraAnterior(Integer valorLeituraAnterior){

		this.valorLeituraAnterior = valorLeituraAnterior;
	}

	public Date getDataLeituraAnterior(){

		return dataLeituraAnterior;
	}

	public void setDataLeituraAnterior(Date dataLeituraAnterior){

		this.dataLeituraAnterior = dataLeituraAnterior;
	}

	public Integer getIndicadorLeituraAnterior(){

		return indicadorLeituraAnterior;
	}

	public void setIndicadorLeituraAnterior(Integer indicadorLeituraAnterior){

		this.indicadorLeituraAnterior = indicadorLeituraAnterior;
	}

	public Date getDataInstalacaoHidrometro(){

		return dataInstalacaoHidrometro;
	}

	public void setDataInstalacaoHidrometro(Date dataInstalacaoHidrometro){

		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}

	public Integer getValorLeituraMinima(){

		return valorLeituraMinima;
	}

	public void setValorLeituraMinima(Integer valorLeituraMinima){

		this.valorLeituraMinima = valorLeituraMinima;
	}

	public Integer getValorLeituraMaxima(){

		return valorLeituraMaxima;
	}

	public void setValorLeituraMaxima(Integer valorLeituraMaxima){

		this.valorLeituraMaxima = valorLeituraMaxima;
	}

	public String getIndicadorConsumoReal2Meses(){

		return indicadorConsumoReal2Meses;
	}

	public void setIndicadorConsumoReal2Meses(String indicadorConsumoReal2Meses){

		this.indicadorConsumoReal2Meses = indicadorConsumoReal2Meses;
	}

	public Short getIndicadorAnormalidadeAC6Meses(){

		return indicadorAnormalidadeAC6Meses;
	}

	public void setIndicadorAnormalidadeAC6Meses(Short indicadorAnormalidadeAC6Meses){

		this.indicadorAnormalidadeAC6Meses = indicadorAnormalidadeAC6Meses;
	}

	public Short getIndicadorAnormalidadeBC4Meses(){

		return indicadorAnormalidadeBC4Meses;
	}

	public void setIndicadorAnormalidadeBC4Meses(Short indicadorAnormalidadeBC4Meses){

		this.indicadorAnormalidadeBC4Meses = indicadorAnormalidadeBC4Meses;
	}

	public Short getIndicadorCpfCnpj(){

		return indicadorCpfCnpj;
	}

	public void setIndicadorCpfCnpj(Short indicadorCpfCnpj){

		this.indicadorCpfCnpj = indicadorCpfCnpj;
	}

	public Long getNumeroCpfCnpj(){

		return numeroCpfCnpj;
	}

	public void setNumeroCpfCnpj(Long numeroCpfCnpj){

		this.numeroCpfCnpj = numeroCpfCnpj;
	}

	public BigDecimal getPercentualImposto(){

		return percentualImposto;
	}

	public void setPercentualImposto(BigDecimal percentualImposto){

		this.percentualImposto = percentualImposto;
	}

	public Integer getMesLeitura1(){

		return mesLeitura1;
	}

	public void setMesLeitura1(Integer mesLeitura1){

		this.mesLeitura1 = mesLeitura1;
	}

	public Integer getOcorrenciaLeitura1(){

		return ocorrenciaLeitura1;
	}

	public void setOcorrenciaLeitura1(Integer ocorrenciaLeitura1){

		this.ocorrenciaLeitura1 = ocorrenciaLeitura1;
	}

	public Integer getConsumoLeitura1(){

		return consumoLeitura1;
	}

	public void setConsumoLeitura1(Integer consumoLeitura1){

		this.consumoLeitura1 = consumoLeitura1;
	}

	public Integer getMesLeitura2(){

		return mesLeitura2;
	}

	public void setMesLeitura2(Integer mesLeitura2){

		this.mesLeitura2 = mesLeitura2;
	}

	public Integer getOcorrenciaLeitura2(){

		return ocorrenciaLeitura2;
	}

	public void setOcorrenciaLeitura2(Integer ocorrenciaLeitura2){

		this.ocorrenciaLeitura2 = ocorrenciaLeitura2;
	}

	public Integer getConsumoLeitura2(){

		return consumoLeitura2;
	}

	public void setConsumoLeitura2(Integer consumoLeitura2){

		this.consumoLeitura2 = consumoLeitura2;
	}

	public Integer getMesLeitura3(){

		return mesLeitura3;
	}

	public void setMesLeitura3(Integer mesLeitura3){

		this.mesLeitura3 = mesLeitura3;
	}

	public Integer getOcorrenciaLeitura3(){

		return ocorrenciaLeitura3;
	}

	public void setOcorrenciaLeitura3(Integer ocorrenciaLeitura3){

		this.ocorrenciaLeitura3 = ocorrenciaLeitura3;
	}

	public Integer getConsumoLeitura3(){

		return consumoLeitura3;
	}

	public void setConsumoLeitura3(Integer consumoLeitura3){

		this.consumoLeitura3 = consumoLeitura3;
	}

	public Integer getMesLeitura4(){

		return mesLeitura4;
	}

	public void setMesLeitura4(Integer mesLeitura4){

		this.mesLeitura4 = mesLeitura4;
	}

	public Integer getOcorrenciaLeitura4(){

		return ocorrenciaLeitura4;
	}

	public void setOcorrenciaLeitura4(Integer ocorrenciaLeitura4){

		this.ocorrenciaLeitura4 = ocorrenciaLeitura4;
	}

	public Integer getConsumoLeitura4(){

		return consumoLeitura4;
	}

	public void setConsumoLeitura4(Integer consumoLeitura4){

		this.consumoLeitura4 = consumoLeitura4;
	}

	public Integer getMesLeitura5(){

		return mesLeitura5;
	}

	public void setMesLeitura5(Integer mesLeitura5){

		this.mesLeitura5 = mesLeitura5;
	}

	public Integer getOcorrenciaLeitura5(){

		return ocorrenciaLeitura5;
	}

	public void setOcorrenciaLeitura5(Integer ocorrenciaLeitura5){

		this.ocorrenciaLeitura5 = ocorrenciaLeitura5;
	}

	public Integer getConsumoLeitura5(){

		return consumoLeitura5;
	}

	public void setConsumoLeitura5(Integer consumoLeitura5){

		this.consumoLeitura5 = consumoLeitura5;
	}

	public Integer getMesLeitura6(){

		return mesLeitura6;
	}

	public void setMesLeitura6(Integer mesLeitura6){

		this.mesLeitura6 = mesLeitura6;
	}

	public Integer getOcorrenciaLeitura6(){

		return ocorrenciaLeitura6;
	}

	public void setOcorrenciaLeitura6(Integer ocorrenciaLeitura6){

		this.ocorrenciaLeitura6 = ocorrenciaLeitura6;
	}

	public Integer getConsumoLeitura6(){

		return consumoLeitura6;
	}

	public void setConsumoLeitura6(Integer consumoLeitura6){

		this.consumoLeitura6 = consumoLeitura6;
	}

	public BigDecimal getValorCredito(){

		return valorCredito;
	}

	public void setValorCredito(BigDecimal valorCredito){

		this.valorCredito = valorCredito;
	}

	public String getDescricaoRubrica1(){

		return descricaoRubrica1;
	}

	public void setDescricaoRubrica1(String descricaoRubrica1){

		this.descricaoRubrica1 = descricaoRubrica1;
	}

	public BigDecimal getValorRubrica1(){

		return valorRubrica1;
	}

	public void setValorRubrica1(BigDecimal valorRubrica1){

		this.valorRubrica1 = valorRubrica1;
	}

	public String getDescricaoRubrica2(){

		return descricaoRubrica2;
	}

	public void setDescricaoRubrica2(String descricaoRubrica2){

		this.descricaoRubrica2 = descricaoRubrica2;
	}

	public BigDecimal getValorRubrica2(){

		return valorRubrica2;
	}

	public void setValorRubrica2(BigDecimal valorRubrica2){

		this.valorRubrica2 = valorRubrica2;
	}

	public String getDescricaoRubrica3(){

		return descricaoRubrica3;
	}

	public void setDescricaoRubrica3(String descricaoRubrica3){

		this.descricaoRubrica3 = descricaoRubrica3;
	}

	public BigDecimal getValorRubrica3(){

		return valorRubrica3;
	}

	public void setValorRubrica3(BigDecimal valorRubrica3){

		this.valorRubrica3 = valorRubrica3;
	}

	public String getDescricaoRubrica4(){

		return descricaoRubrica4;
	}

	public void setDescricaoRubrica4(String descricaoRubrica4){

		this.descricaoRubrica4 = descricaoRubrica4;
	}

	public BigDecimal getValorRubrica4(){

		return valorRubrica4;
	}

	public void setValorRubrica4(BigDecimal valorRubrica4){

		this.valorRubrica4 = valorRubrica4;
	}

	public String getDescricaoRubrica5(){

		return descricaoRubrica5;
	}

	public void setDescricaoRubrica5(String descricaoRubrica5){

		this.descricaoRubrica5 = descricaoRubrica5;
	}

	public BigDecimal getValorRubrica5(){

		return valorRubrica5;
	}

	public void setValorRubrica5(BigDecimal valorRubrica5){

		this.valorRubrica5 = valorRubrica5;
	}

	public String getDescricaoRubrica6(){

		return descricaoRubrica6;
	}

	public void setDescricaoRubrica6(String descricaoRubrica6){

		this.descricaoRubrica6 = descricaoRubrica6;
	}

	public BigDecimal getValorRubrica6(){

		return valorRubrica6;
	}

	public void setValorRubrica6(BigDecimal valorRubrica6){

		this.valorRubrica6 = valorRubrica6;
	}

	public String getDescricaoRubrica7(){

		return descricaoRubrica7;
	}

	public void setDescricaoRubrica7(String descricaoRubrica7){

		this.descricaoRubrica7 = descricaoRubrica7;
	}

	public BigDecimal getValorRubrica7(){

		return valorRubrica7;
	}

	public void setValorRubrica7(BigDecimal valorRubrica7){

		this.valorRubrica7 = valorRubrica7;
	}

	public String getDescricaoRubrica8(){

		return descricaoRubrica8;
	}

	public void setDescricaoRubrica8(String descricaoRubrica8){

		this.descricaoRubrica8 = descricaoRubrica8;
	}

	public BigDecimal getValorRubrica8(){

		return valorRubrica8;
	}

	public void setValorRubrica8(BigDecimal valorRubrica8){

		this.valorRubrica8 = valorRubrica8;
	}

	public String getDescricaoRubrica9(){

		return descricaoRubrica9;
	}

	public void setDescricaoRubrica9(String descricaoRubrica9){

		this.descricaoRubrica9 = descricaoRubrica9;
	}

	public BigDecimal getValorRubrica9(){

		return valorRubrica9;
	}

	public void setValorRubrica9(BigDecimal valorRubrica9){

		this.valorRubrica9 = valorRubrica9;
	}

	public String getDescricaoRubrica10(){

		return descricaoRubrica10;
	}

	public void setDescricaoRubrica10(String descricaoRubrica10){

		this.descricaoRubrica10 = descricaoRubrica10;
	}

	public BigDecimal getValorRubrica10(){

		return valorRubrica10;
	}

	public void setValorRubrica10(BigDecimal valorRubrica10){

		this.valorRubrica10 = valorRubrica10;
	}

	public Integer getValorLeituraAtual(){

		return valorLeituraAtual;
	}

	public void setValorLeituraAtual(Integer valorLeituraAtual){

		this.valorLeituraAtual = valorLeituraAtual;
	}

	public Integer getValorLeituraAtribuida(){

		return valorLeituraAtribuida;
	}

	public void setValorLeituraAtribuida(Integer valorLeituraAtribuida){

		this.valorLeituraAtribuida = valorLeituraAtribuida;
	}

	public Integer getConsumo(){

		return consumo;
	}

	public void setConsumo(Integer consumo){

		this.consumo = consumo;
	}

	public Integer getConsumoFaturado(){

		return consumoFaturado;
	}

	public void setConsumoFaturado(Integer consumoFaturado){

		this.consumoFaturado = consumoFaturado;
	}

	public Integer getConsumoMinimo(){

		return consumoMinimo;
	}

	public void setConsumoMinimo(Integer consumoMinimo){

		this.consumoMinimo = consumoMinimo;
	}

	public Integer getConsumoRateio(){

		return consumoRateio;
	}

	public void setConsumoRateio(Integer consumoRateio){

		this.consumoRateio = consumoRateio;
	}

	public Integer getOcorrenciaId(){

		return ocorrenciaId;
	}

	public void setOcorrenciaId(Integer ocorrenciaId){

		this.ocorrenciaId = ocorrenciaId;
	}

	public String getOcorrenciaConsumoId(){

		return ocorrenciaConsumoId;
	}

	public void setOcorrenciaConsumoId(String ocorrenciaConsumoId){

		this.ocorrenciaConsumoId = ocorrenciaConsumoId;
	}

	public Integer getFaturaEmitida(){

		return faturaEmitida;
	}

	public void setFaturaEmitida(Integer faturaEmitida){

		this.faturaEmitida = faturaEmitida;
	}

	public Integer getDiasFaturados(){

		return diasFaturados;
	}

	public void setDiasFaturados(Integer diasFaturados){

		this.diasFaturados = diasFaturados;
	}

	public Date getDataLeituraAtual(){

		return dataLeituraAtual;
	}

	public void setDataLeituraAtual(Date dataLeituraAtual){

		this.dataLeituraAtual = dataLeituraAtual;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public BigDecimal getValorServico(){

		return valorServico;
	}

	public void setValorServico(BigDecimal valorServico){

		this.valorServico = valorServico;
	}

	public BigDecimal getValorDevolvido(){

		return valorDevolvido;
	}

	public void setValorDevolvido(BigDecimal valorDevolvido){

		this.valorDevolvido = valorDevolvido;
	}

	public BigDecimal getValorSaldoDevolvido(){

		return valorSaldoDevolvido;
	}

	public void setValorSaldoDevolvido(BigDecimal valorSaldoDevolvido){

		this.valorSaldoDevolvido = valorSaldoDevolvido;
	}

	public Short getIndicadorGrandeConsumoFaturado(){

		return indicadorGrandeConsumoFaturado;
	}

	public void setIndicadorGrandeConsumoFaturado(Short indicadorGrandeConsumoFaturado){

		this.indicadorGrandeConsumoFaturado = indicadorGrandeConsumoFaturado;
	}

	public String getIndicadorContatarUsuario(){

		return indicadorContatarUsuario;
	}

	public void setIndicadorContatarUsuario(String indicadorContatarUsuario){

		this.indicadorContatarUsuario = indicadorContatarUsuario;
	}

	public Short getIndicadorReligarAgua(){

		return indicadorReligarAgua;
	}

	public void setIndicadorReligarAgua(Short indicadorReligarAgua){

		this.indicadorReligarAgua = indicadorReligarAgua;
	}

	public Integer getServicoReligacaoId(){

		return servicoReligacaoId;
	}

	public void setServicoReligacaoId(Integer servicoReligacaoId){

		this.servicoReligacaoId = servicoReligacaoId;
	}

	public BigDecimal getValorReligacao(){

		return valorReligacao;
	}

	public void setValorReligacao(BigDecimal valorReligacao){

		this.valorReligacao = valorReligacao;
	}

	public Integer getServicoSancaoId(){

		return servicoSancaoId;
	}

	public void setServicoSancaoId(Integer servicoSancaoId){

		this.servicoSancaoId = servicoSancaoId;
	}

	public BigDecimal getValorSancao(){

		return valorSancao;
	}

	public void setValorSancao(BigDecimal valorSancao){

		this.valorSancao = valorSancao;
	}

	public String getTipoConsumo(){

		return tipoConsumo;
	}

	public void setTipoConsumo(String tipoConsumo){

		this.tipoConsumo = tipoConsumo;
	}

	public Short getCondicaoLeitura(){

		return condicaoLeitura;
	}

	public void setCondicaoLeitura(Short condicaoLeitura){

		this.condicaoLeitura = condicaoLeitura;
	}

	public String getAlteNumeroImovel(){

		return alteNumeroImovel;
	}

	public void setAlteNumeroImovel(String alteNumeroImovel){

		this.alteNumeroImovel = alteNumeroImovel;
	}

	public String getIdDvCorteShdLigacao(){

		return idDvCorteShdLigacao;
	}

	public void setIdDvCorteShdLigacao(String idDvCorteShdLigacao){

		this.idDvCorteShdLigacao = idDvCorteShdLigacao;
	}

	public String getIdDvNumeroHidrometro(){

		return idDvNumeroHidrometro;
	}

	public void setIdDvNumeroHidrometro(String idDvNumeroHidrometro){

		this.idDvNumeroHidrometro = idDvNumeroHidrometro;
	}

	public String getIdDvCategoriaEconomia(){

		return idDvCategoriaEconomia;
	}

	public void setIdDvCategoriaEconomia(String idDvCategoriaEconomia){

		this.idDvCategoriaEconomia = idDvCategoriaEconomia;
	}

	public String getIdDvLogradouro(){

		return idDvLogradouro;
	}

	public void setIdDvLogradouro(String idDvLogradouro){

		this.idDvLogradouro = idDvLogradouro;
	}

	public String getIdDvRevisaoQuadra(){

		return idDvRevisaoQuadra;
	}

	public void setIdDvRevisaoQuadra(String idDvRevisaoQuadra){

		this.idDvRevisaoQuadra = idDvRevisaoQuadra;
	}

	public String getIdDvFiscConsumo(){

		return idDvFiscConsumo;
	}

	public void setIdDvFiscConsumo(String idDvFiscConsumo){

		this.idDvFiscConsumo = idDvFiscConsumo;
	}

	public String getIdDvUsuarioNaoMdChd(){

		return idDvUsuarioNaoMdChd;
	}

	public void setIdDvUsuarioNaoMdChd(String idDvUsuarioNaoMdChd){

		this.idDvUsuarioNaoMdChd = idDvUsuarioNaoMdChd;
	}

	public Short getIndicadorHidrometroNaoLacrado(){

		return indicadorHidrometroNaoLacrado;
	}

	public void setIndicadorHidrometroNaoLacrado(Short indicadorHidrometroNaoLacrado){

		this.indicadorHidrometroNaoLacrado = indicadorHidrometroNaoLacrado;
	}

	public Integer getNumeroFoneContacto(){

		return numeroFoneContacto;
	}

	public void setNumeroFoneContacto(Integer numeroFoneContacto){

		this.numeroFoneContacto = numeroFoneContacto;
	}

	public Integer getFuncionarioId(){

		return funcionarioId;
	}

	public void setFuncionarioId(Integer funcionarioId){

		this.funcionarioId = funcionarioId;
	}

	public String getCidade(){

		return cidade;
	}

	public void setCidade(String cidade){

		this.cidade = cidade;
	}

	public Integer getCicloId(){

		return cicloId;
	}

	public void setCicloId(Integer cicloId){

		this.cicloId = cicloId;
	}

	public Short getTipoColetaLeitura(){

		return tipoColetaLeitura;
	}

	public void setTipoColetaLeitura(Short tipoColetaLeitura){

		this.tipoColetaLeitura = tipoColetaLeitura;
	}

	public Short getDebitoAutomatico(){

		return debitoAutomatico;
	}

	public void setDebitoAutomatico(Short debitoAutomatico){

		this.debitoAutomatico = debitoAutomatico;
	}

	public Date getDataProximaLeitura(){

		return dataProximaLeitura;
	}

	public void setDataProximaLeitura(Date dataProximaLeitura){

		this.dataProximaLeitura = dataProximaLeitura;
	}

	public Date getDataGeracaoGCS(){

		return dataGeracaoGCS;
	}

	public void setDataGeracaoGCS(Date dataGeracaoGCS){

		this.dataGeracaoGCS = dataGeracaoGCS;
	}

	public Integer getLivroSetor(){

		return livroSetor;
	}

	public void setLivroSetor(Integer livroSetor){

		this.livroSetor = livroSetor;
	}

	public Integer getReavisoLigacaoId(){

		return reavisoLigacaoId;
	}

	public void setReavisoLigacaoId(Integer reavisoLigacaoId){

		this.reavisoLigacaoId = reavisoLigacaoId;
	}

	public Integer getQuantidadeContasRevisao(){

		return quantidadeContasRevisao;
	}

	public void setQuantidadeContasRevisao(Integer quantidadeContasRevisao){

		this.quantidadeContasRevisao = quantidadeContasRevisao;
	}

	public BigDecimal getValorTotalRevisao(){

		return valorTotalRevisao;
	}

	public void setValorTotalRevisao(BigDecimal valorTotalRevisao){

		this.valorTotalRevisao = valorTotalRevisao;
	}

	public Integer getRegionalId(){

		return regionalId;
	}

	public void setRegionalId(Integer regionalId){

		this.regionalId = regionalId;
	}

	public BigDecimal getValorImpostoRetorno(){

		return valorImpostoRetorno;
	}

	public void setValorImpostoRetorno(BigDecimal valorImpostoRetorno){

		this.valorImpostoRetorno = valorImpostoRetorno;
	}

	public Date getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public Integer getStatusId(){

		return statusId;
	}

	public void setStatusId(Integer statusId){

		this.statusId = statusId;
	}

	public Date getTimedownload(){

		return timedownload;
	}

	public void setTimedownload(Date timedownload){

		this.timedownload = timedownload;
	}

	public BigDecimal getTotalEmitido(){

		return totalEmitido;
	}

	public void setTotalEmitido(BigDecimal totalEmitido){

		this.totalEmitido = totalEmitido;
	}

	public Date getDataTarefa(){

		return dataTarefa;
	}

	public void setDataTarefa(Date dataTarefa){

		this.dataTarefa = dataTarefa;
	}

	public Integer getGeradoGeraOk(){

		return geradoGeraOk;
	}

	public void setGeradoGeraOk(Integer geradoGeraOk){

		this.geradoGeraOk = geradoGeraOk;
	}

	public Integer getLoginGeraOk(){

		return loginGeraOk;
	}

	public void setLoginGeraOk(Integer loginGeraOk){

		this.loginGeraOk = loginGeraOk;
	}

	public Date getDataGeraOk(){

		return dataGeraOk;
	}

	public void setDataGeraOk(Date dataGeraOk){

		this.dataGeraOk = dataGeraOk;
	}

	public Integer getLoginDistribuido(){

		return loginDistribuido;
	}

	public void setLoginDistribuido(Integer loginDistribuido){

		this.loginDistribuido = loginDistribuido;
	}

	public Date getDataDistribuido(){

		return dataDistribuido;
	}

	public void setDataDistribuido(Date dataDistribuido){

		this.dataDistribuido = dataDistribuido;
	}

	public Integer getLoginRemanejado(){

		return loginRemanejado;
	}

	public void setLoginRemanejado(Integer loginRemanejado){

		this.loginRemanejado = loginRemanejado;
	}

	public Date getDataRemanejado(){

		return dataRemanejado;
	}

	public void setDataRemanejado(Date dataRemanejado){

		this.dataRemanejado = dataRemanejado;
	}

	public Integer getLoginEnviadoCol(){

		return loginEnviadoCol;
	}

	public void setLoginEnviadoCol(Integer loginEnviadoCol){

		this.loginEnviadoCol = loginEnviadoCol;
	}

	public Date getDataEnviadoCol(){

		return dataEnviadoCol;
	}

	public void setDataEnviadoCol(Date dataEnviadoCol){

		this.dataEnviadoCol = dataEnviadoCol;
	}

	public Integer getEnviadoCol(){

		return enviadoCol;
	}

	public void setEnviadoCol(Integer enviadoCol){

		this.enviadoCol = enviadoCol;
	}

	public Integer getIndicadorFaturaRetida(){

		return indicadorFaturaRetida;
	}

	public void setIndicadorFaturaRetida(Integer indicadorFaturaRetida){

		this.indicadorFaturaRetida = indicadorFaturaRetida;
	}

	public String getVersao(){

		return versao;
	}

	public void setVersao(String versao){

		this.versao = versao;
	}

	public Integer getReimpressa(){

		return reimpressa;
	}

	public void setReimpressa(Integer reimpressa){

		this.reimpressa = reimpressa;
	}

	public Integer getReimpressaNotificacao(){

		return reimpressaNotificacao;
	}

	public void setReimpressaNotificacao(Integer reimpressaNotificacao){

		this.reimpressaNotificacao = reimpressaNotificacao;
	}

	public Date getDataLiberacao(){

		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao){

		this.dataLiberacao = dataLiberacao;
	}

	public Integer getIndicadorExisteDebito(){

		return indicadorExisteDebito;
	}

	public void setIndicadorExisteDebito(Integer indicadorExisteDebito){

		this.indicadorExisteDebito = indicadorExisteDebito;
	}

	public Integer getDescarregamento(){

		return descarregamento;
	}

	public void setDescarregamento(Integer descarregamento){

		this.descarregamento = descarregamento;
	}

	public String getTeveFaltaLeituraAnterior(){

		return teveFaltaLeituraAnterior;
	}

	public void setTeveFaltaLeituraAnterior(String teveFaltaLeituraAnterior){

		this.teveFaltaLeituraAnterior = teveFaltaLeituraAnterior;
	}

	public Integer getValorUltimaLeituraReal(){

		return valorUltimaLeituraReal;
	}

	public void setValorUltimaLeituraReal(Integer valorUltimaLeituraReal){

		this.valorUltimaLeituraReal = valorUltimaLeituraReal;
	}

	public Integer getNumeroMesesUltimaLeituraReal(){

		return numeroMesesUltimaLeituraReal;
	}

	public void setNumeroMesesUltimaLeituraReal(Integer numeroMesesUltimaLeituraReal){

		this.numeroMesesUltimaLeituraReal = numeroMesesUltimaLeituraReal;
	}

	@Override
	public Date getUltimaAlteracao(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		// TODO Auto-generated method stub

	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		if(comp_id != null){
			retorno[0] = comp_id.toString();
		}
		return retorno;
	}

}
