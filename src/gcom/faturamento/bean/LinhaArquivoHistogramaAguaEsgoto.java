package gcom.faturamento.bean;

import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;


public class LinhaArquivoHistogramaAguaEsgoto
				implements Serializable {

	public LinhaArquivoHistogramaAguaEsgoto(EmitirHistogramaAguaEsgotoEconomiaHelper helper, Integer anoMesFaturamento,
											Integer idGerenciaRegional, Integer IdLocalidadeVinculada, Integer IdLocalidade,
											int tipoAguaEsgoto, Integer idCategoria, BigDecimal totalLigacoes, BigDecimal totalEconomias) {

		this.anoMesRef = Util.completarStringCaractereDireitaEsquerda(anoMesFaturamento.toString(), 6, '0', 2);

		this.regional = Util.completarStringCaractereDireitaEsquerda(idGerenciaRegional != null ? String.valueOf(idGerenciaRegional) : "",
						1, '0', 2);

		this.localVinc = Util.completarStringCaractereDireitaEsquerda(IdLocalidadeVinculada != null ? String.valueOf(IdLocalidadeVinculada)
						: "", 3, '0', 2);

		this.local = Util.completarStringCaractereDireitaEsquerda(IdLocalidade != null ? String.valueOf(IdLocalidade) : "", 3, '0', 2);

		this.tipoAguaEsg = Util.completarStringCaractereDireitaEsquerda(String.valueOf(tipoAguaEsgoto), 1, '0', 2);

		this.percentual = Util.completarStringCaractereDireitaEsquerda(String.valueOf(helper.getPercentualEsgoto()), 3, '0', 2);

		this.categoria = Util.completarStringCaractereDireitaEsquerda(idCategoria.toString(), 1, '0', 2);

		this.econChd = Util.completarStringCaractereDireitaEsquerda(String.valueOf(helper.getTotalEconomiasMedido()), 8, '0', 2);

		this.volConFaixChd = Util.completarStringCaractereDireitaEsquerda(String.valueOf(helper.getTotalVolumeConsumoMedido()), 8, '0', 2);

		this.volFatFaixaChd = Util
						.completarStringCaractereDireitaEsquerda(String.valueOf(helper.getTotalVolumeFaturadoMedido()), 8, '0', 2);

		this.receitaChd = Util.completarStringCaractereDireitaEsquerda(
						Util.formataBigDecimal(helper.getTotalReceitaMedido(), 2, Boolean.TRUE),
						13, '0', 2);

		this.econShd = Util.completarStringCaractereDireitaEsquerda(helper.getTotalEconomiasNaoMedido().toString(), 8, '0', 2);

		this.volConFaixShd = Util.completarStringCaractereDireitaEsquerda(String.valueOf(helper.getTotalVolumeConsumoNaoMedido()), 8, '0',
						2);

		this.volFatFaixaShd = Util.completarStringCaractereDireitaEsquerda(String.valueOf(helper.getTotalVolumeFaturadoNaoMedido()), 8,
						'0', 2);

		this.receitaShd = Util.completarStringCaractereDireitaEsquerda(
						Util.formataBigDecimal(helper.getTotalReceitaNaoMedido(), 2, Boolean.TRUE), 13, '0', 2);

		this.ligacaoChd = Util.completarStringCaractereDireitaEsquerda(String.valueOf(helper.getTotalLigacoesMedido()), 8, '0', 2);

		this.ligacaoShd = Util.completarStringCaractereDireitaEsquerda(String.valueOf(helper.getTotalLigacoesNaoMedido()), 8, '0', 2);

		this.ligacaoTotal = Util.completarStringCaractereDireitaEsquerda(String.valueOf(totalLigacoes), 8, '0', 2);

		this.economiaTotal = Util.completarStringCaractereDireitaEsquerda(String.valueOf(totalEconomias), 8, '0', 2);

	}

	// 03 DHA-ANO-MES-REFERENCIA PIC 9(06).
	private String anoMesRef = null;

	// 03 DHA-REGIONAL PIC 9(01).
	private String regional = null;

	// 03 DHA-LOCAL-VINC PIC 9(03).
	private String localVinc = null;

	// 03 DHA-LOCAL PIC 9(03).
	private String local = null;

	// 03 DHA-AG-ESG-PERC.
	// 05 DHA-TIPO-AGU-ESG PIC 9(01)
	private String tipoAguaEsg = null;

	// 05 DHA-PERCENTUAL PIC 9(03).
	private String percentual = null;

	// 03 DHA-CATEGORIA PIC 9(01).
	private String categoria = null;

	// 03 DHA-FAIXA-CONSUMO.
	// 05 DHA-ECON-CHD PIC 9(08).
	private String econChd = null;

	// 05 DHA-VOL-CON-NFAIX-CHD PIC 9(08).
	private String volConFaixChd = null;

	// 05 DHA-VOL-FAT-PFAIX-CHD PIC 9(08).
	private String volFatFaixaChd = null;

	// 05 DHA-RECEITA-CHD PIC 9999999999,99.
	private String receitaChd = null;

	// 05 DHA-ECON-SHD PIC 9(08).
	private String econShd = null;

	// 05 DHA-VOL-CON-NFAIX-SHD PIC 9(08).
	private String volConFaixShd = null;

	// 05 DHA-VOL-FAT-PFAIX-SHD PIC 9(08).
	private String volFatFaixaShd = null;

	// 05 DHA-RECEITA-SHD PIC 9999999999,99.
	private String receitaShd = null;

	// 03 DHA-LIGACAO-CHD PIC 9(08).
	private String ligacaoChd = null;

	// 03 DHA-LIGACAO-SHD PIC 9(08).
	private String ligacaoShd = null;

	// 03 DHA-LIGACAO-TOTAL PIC 9(08).
	private String ligacaoTotal = null;

	// 03 DHA-ECONOMIA-TOTAL PIC 9(08).
	private String economiaTotal = null;

	public StringBuffer getLinhaFormatada(){

		StringBuffer sb = new StringBuffer();

		sb.append(this.anoMesRef);
		sb.append(";");
		sb.append(this.regional);
		sb.append(";");
		sb.append(this.localVinc);
		sb.append(";");
		sb.append(this.local);
		sb.append(";");
		sb.append(this.tipoAguaEsg);
		sb.append(";");
		sb.append(this.percentual);
		sb.append(";");
		sb.append(this.categoria);
		sb.append(";");
		sb.append(this.econChd);
		sb.append(";");
		sb.append(this.volConFaixChd);
		sb.append(";");
		sb.append(this.volFatFaixaChd);
		sb.append(";");
		sb.append(this.receitaChd);
		sb.append(";");
		sb.append(this.econShd);
		sb.append(";");
		sb.append(this.volConFaixShd);
		sb.append(";");
		sb.append(this.volFatFaixaShd);
		sb.append(";");
		sb.append(this.receitaShd);
		sb.append(";");
		sb.append(this.ligacaoChd);
		sb.append(";");
		sb.append(this.ligacaoShd);
		sb.append(";");
		sb.append(this.ligacaoTotal);
		sb.append(";");
		sb.append(this.economiaTotal);

		return sb;

	}

	public String getAnoMesRef(){

		return anoMesRef;
	}

	public void setAnoMesRef(String anoMesRef){

		this.anoMesRef = anoMesRef;

	}

	public String getRegional(){

		return regional;
	}

	public String getLocalVinc(){

		return localVinc;
	}

	public String getLocal(){

		return local;
	}

	public String getTipoAguaEsg(){

		return tipoAguaEsg;
	}

	public String getCategoria(){

		return categoria;
	}

}
