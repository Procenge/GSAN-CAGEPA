
package gcom.cobranca.bean;

import java.math.BigDecimal;

public class RelatorioFechamentoCobrancaHelper {

	private Integer idComando;

	private Integer idCronograma;

	private String acaoNome;

	private String comandoTitulo;

	private String cronogramaGrupo;

	private Integer qtdContas;

	private BigDecimal valorContas;

	private Integer qtdParcelas;

	private BigDecimal valorParcelas;

	private Integer qtd30;

	private BigDecimal valor30;

	private Integer qtd60;

	private BigDecimal valor60;

	private Integer qtd90;

	private BigDecimal valor90;

	private Integer qtd120;

	private BigDecimal valor120;

	private Integer qtd150;

	private BigDecimal valor150;

	private Integer qtd180;

	private BigDecimal valor180;

	private Integer qtdMaior180;

	private BigDecimal valorMaior180;

	private final BigDecimal ZERO = BigDecimal.ZERO;

	public String getAcaoNome(){

		return acaoNome;
	}

	public void setAcaoNome(String acaoNome){

		this.acaoNome = acaoNome;
	}

	public String getComandoTitulo(){

		return comandoTitulo;
	}

	public void setComandoTitulo(String comandoTitulo){

		this.comandoTitulo = comandoTitulo;
	}

	public String getCronogramaGrupo(){

		return cronogramaGrupo;
	}

	public void setCronogramaGrupo(String cronogramaGrupo){

		this.cronogramaGrupo = cronogramaGrupo;
	}

	public Integer getQtdContas(){

		if(qtdContas == null){
			return 0;
		}
		return qtdContas;
	}

	public void setQtdContas(Integer qtdContas){

		this.qtdContas = qtdContas;
	}

	public BigDecimal getValorContas(){

		if(valorContas == null){
			return ZERO;
		}
		return valorContas;
	}

	public void setValorContas(BigDecimal valorContas){

		this.valorContas = valorContas;
	}

	public Integer getQtdParcelas(){

		if(qtdParcelas == null){
			return 0;
		}
		return qtdParcelas;
	}

	public void setQtdParcelas(Integer qtdParcelas){

		this.qtdParcelas = qtdParcelas;
	}

	public BigDecimal getValorParcelas(){

		if(valorParcelas == null){
			return ZERO;
		}
		return valorParcelas;
	}

	public void setValorParcelas(BigDecimal valorParcelas){

		this.valorParcelas = valorParcelas;
	}

	public Integer getQtd30(){

		if(qtd30 == null){
			return 0;
		}
		return qtd30;
	}

	public void setQtd30(Integer qtd30){

		this.qtd30 = qtd30;
	}

	public BigDecimal getValor30(){

		if(valor30 == null){
			return ZERO;
		}
		return valor30;
	}

	public void setValor30(BigDecimal valor30){

		this.valor30 = valor30;
	}

	public Integer getQtd60(){

		if(qtd60 == null){
			return 0;
		}
		return qtd60;
	}

	public void setQtd60(Integer qtd60){

		this.qtd60 = qtd60;
	}

	public BigDecimal getValor60(){

		if(valor60 == null){
			return ZERO;
		}
		return valor60;
	}

	public void setValor60(BigDecimal valor60){

		this.valor60 = valor60;
	}

	public Integer getQtd90(){

		if(qtd90 == null){
			return 0;
		}
		return qtd90;
	}

	public void setQtd90(Integer qtd90){

		this.qtd90 = qtd90;
	}

	public BigDecimal getValor90(){

		if(valor90 == null){
			return ZERO;
		}
		return valor90;
	}

	public void setValor90(BigDecimal valor90){

		this.valor90 = valor90;
	}

	public Integer getQtd120(){

		if(qtd120 == null){
			return 0;
		}
		return qtd120;
	}

	public void setQtd120(Integer qtd120){

		this.qtd120 = qtd120;
	}

	public BigDecimal getValor120(){

		if(valor120 == null){
			return ZERO;
		}
		return valor120;
	}

	public void setValor120(BigDecimal valor120){

		this.valor120 = valor120;
	}

	public Integer getQtd150(){

		if(qtd150 == null){
			return 0;
		}
		return qtd150;
	}

	public void setQtd150(Integer qtd150){

		this.qtd150 = qtd150;
	}

	public BigDecimal getValor150(){

		if(valor150 == null){
			return ZERO;
		}
		return valor150;
	}

	public void setValor150(BigDecimal valor150){

		this.valor150 = valor150;
	}

	public Integer getQtd180(){

		if(qtd180 == null){
			return 0;
		}
		return qtd180;
	}

	public void setQtd180(Integer qtd180){

		this.qtd180 = qtd180;
	}

	public BigDecimal getValor180(){

		if(valor180 == null){
			return ZERO;
		}
		return valor180;
	}

	public void setValor180(BigDecimal valor180){

		this.valor180 = valor180;
	}

	public Integer getQtdMaior180(){

		if(qtdMaior180 == null){
			return 0;
		}
		return qtdMaior180;
	}

	public void setQtdMaior180(Integer qtdMaior180){

		this.qtdMaior180 = qtdMaior180;
	}

	public BigDecimal getValorMaior180(){

		if(valorMaior180 == null){
			return ZERO;
		}
		return valorMaior180;
	}

	public void setValorMaior180(BigDecimal valorMaior180){

		this.valorMaior180 = valorMaior180;
	}

	public Integer getIdComando(){

		return idComando;
	}

	public void setIdComando(Integer idComando){

		this.idComando = idComando;
	}

	public Integer getIdCronograma(){

		return idCronograma;
	}

	public void setIdCronograma(Integer idCronograma){

		this.idCronograma = idCronograma;
	}

}
