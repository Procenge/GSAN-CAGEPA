
package gcom.relatorio.micromedicao;

import gcom.micromedicao.bean.AnormalidadeEntidadeControleDetalheHelper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;

/**
 * [UC3033] Verificar Anormalidades de Consumo nos Movimentos do Faturamento
 * 
 * @author Hugo Lima
 * @date 13/01/2012
 */
public class RelatorioResumoAnormalidadesConsumoBean
				implements RelatorioBean {

	private String descricaoEntidadeControle;

	private String valorDetalheEntidadeControle;

	private Integer idAnormalidadeEntidadeControle;

	private Integer idAnormalidade;

	private Short idExcessoAnormalidades;

	private String percentualLimiteAnormalidades;

	private Integer quantidadeAnormalidades;

	private String quantidadeImoveisHidrometroLigacaoAgua;

	private String limiteAceitavelAnormalidade;

	private String complementoEntidadeControle;

	public RelatorioResumoAnormalidadesConsumoBean(String descricaoEntidadeControle, String valorDetalheEntidadeControle,
													Integer idAnormalidadeEntidadeControle, Integer idAnormalidade,
													Short idExcessoAnormalidades, BigDecimal percentualLimiteAnormalidades,
													Integer quantidadeAnormalidades, Double quantidadeImoveisHidrometroLigacaoAgua,
													BigDecimal limiteAceitavelAnormalidade) {

		this.descricaoEntidadeControle = descricaoEntidadeControle;
		this.valorDetalheEntidadeControle = valorDetalheEntidadeControle;
		this.idAnormalidadeEntidadeControle = idAnormalidadeEntidadeControle;
		this.idAnormalidade = idAnormalidade;
		this.idExcessoAnormalidades = idExcessoAnormalidades;
		this.percentualLimiteAnormalidades = Util.formatarMoedaReal(percentualLimiteAnormalidades, 2);
		this.quantidadeAnormalidades = quantidadeAnormalidades;
		this.quantidadeImoveisHidrometroLigacaoAgua = quantidadeImoveisHidrometroLigacaoAgua.toString();
		this.limiteAceitavelAnormalidade = Util.formatarMoedaReal(limiteAceitavelAnormalidade, 2);
	}

	public RelatorioResumoAnormalidadesConsumoBean(AnormalidadeEntidadeControleDetalheHelper detalheHelper) {

		this.descricaoEntidadeControle = detalheHelper.getDescricaoEntidadeControle();
		this.valorDetalheEntidadeControle = detalheHelper.getValorDetalheEntidadeControle();
		this.idAnormalidadeEntidadeControle = detalheHelper.getIdAnormalidadeEntidadeControle();
		this.idAnormalidade = detalheHelper.getIdAnormalidade();
		this.idExcessoAnormalidades = detalheHelper.getIdExcessoAnormalidades();
		this.percentualLimiteAnormalidades = Util.formatarMoedaReal(detalheHelper.getPercentualLimiteAnormalidades(), 2);
		this.quantidadeAnormalidades = detalheHelper.getQuantidadeAnormalidades();
		this.quantidadeImoveisHidrometroLigacaoAgua = detalheHelper.getQuantidadeImoveisHidrometroLigacaoAgua().intValue() + "";
		this.limiteAceitavelAnormalidade = Util.formatarMoedaReal(detalheHelper.getLimiteAceitavelAnormalidade(), 2);
		this.complementoEntidadeControle = detalheHelper.getComplementoEntidadeControle();
	}

	public String getDescricaoEntidadeControle(){

		return descricaoEntidadeControle;
	}

	public void setDescricaoEntidadeControle(String descricaoEntidadeControle){

		this.descricaoEntidadeControle = descricaoEntidadeControle;
	}

	public String getValorDetalheEntidadeControle(){

		return valorDetalheEntidadeControle;
	}

	public void setValorDetalheEntidadeControle(String valorDetalheEntidadeControle){

		this.valorDetalheEntidadeControle = valorDetalheEntidadeControle;
	}

	public Integer getIdAnormalidadeEntidadeControle(){

		return idAnormalidadeEntidadeControle;
	}

	public void setIdAnormalidadeEntidadeControle(Integer idAnormalidadeEntidadeControle){

		this.idAnormalidadeEntidadeControle = idAnormalidadeEntidadeControle;
	}

	public Integer getIdAnormalidade(){

		return idAnormalidade;
	}

	public void setIdAnormalidade(Integer idAnormalidade){

		this.idAnormalidade = idAnormalidade;
	}

	public Short getIdExcessoAnormalidades(){

		return idExcessoAnormalidades;
	}

	public void setIdExcessoAnormalidades(Short idExcessoAnormalidades){

		this.idExcessoAnormalidades = idExcessoAnormalidades;
	}

	public String getPercentualLimiteAnormalidades(){

		return percentualLimiteAnormalidades;
	}

	public void setPercentualLimiteAnormalidades(String percentualLimiteAnormalidades){

		this.percentualLimiteAnormalidades = percentualLimiteAnormalidades;
	}

	public Integer getQuantidadeAnormalidades(){

		return quantidadeAnormalidades;
	}

	public void setQuantidadeAnormalidades(Integer quantidadeAnormalidades){

		this.quantidadeAnormalidades = quantidadeAnormalidades;
	}

	public String getQuantidadeImoveisHidrometroLigacaoAgua(){

		return quantidadeImoveisHidrometroLigacaoAgua;
	}

	public void setQuantidadeImoveisHidrometroLigacaoAgua(String quantidadeImoveisHidrometroLigacaoAgua){

		this.quantidadeImoveisHidrometroLigacaoAgua = quantidadeImoveisHidrometroLigacaoAgua;
	}

	public String getLimiteAceitavelAnormalidade(){

		return limiteAceitavelAnormalidade;
	}

	public void setLimiteAceitavelAnormalidade(String limiteAceitavelAnormalidade){

		this.limiteAceitavelAnormalidade = limiteAceitavelAnormalidade;
	}

	public String getComplementoEntidadeControle(){

		return complementoEntidadeControle;
	}

	public void setComplementoEntidadeControle(String complementoEntidadeControle){

		this.complementoEntidadeControle = complementoEntidadeControle;
	}

}
