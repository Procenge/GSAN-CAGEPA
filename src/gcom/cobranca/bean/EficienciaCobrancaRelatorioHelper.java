
package gcom.cobranca.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author isilva
 */
public class EficienciaCobrancaRelatorioHelper {

	public static final String TIPO_COMANDO = "comando";

	public static final String TIPO_CRONOGRAMA = "cronograma";

	/**
	 * Programa
	 */
	private Integer idComando;

	/**
	 * Descrição do Grupo ou Título do Comando
	 */
	private String descricaoGrupoOuTituloComando;

	/**
	 * Descrição da Ação
	 */
	private String descricaoAcao;

	/**
	 *(Comando) data execucao da ação
	 */
	private Date inicioCobranca;

	/**
	 * (Ação) execução + dias validade
	 */
	private Date fimCobranca;

	// ****** Inicio Cobrança

	/**
	 * Quantidade de documentos
	 */
	private Integer quantidadeClientesInicioCobranca;

	/**
	 * Itens
	 */
	private Integer quantidadeDebitosInicioCobranca;

	/**
	 * Valor do documento
	 */
	private BigDecimal dividaCobradaInicioCobranca;

	// ****** Recumperação da Dívida
	// Cobrança - Suecesso
	// Cobrança - Produtividade

	/**
	 * 
	 */
	private Integer quantidadeClientesRecuperacaoDivida;

	/**
	 * 
	 */
	private Integer quantidadeDebitosRecuperacaoDivida;

	private BigDecimal dividaRecuperada;

	private BigDecimal porcentagemRecuperada;

	public EficienciaCobrancaRelatorioHelper() {

	}

	/**
	 * @return the idComando
	 */
	public Integer getIdComando(){

		return idComando;
	}

	/**
	 * @param idComando
	 *            the idComando to set
	 */
	public void setIdComando(Integer idComando){

		this.idComando = idComando;
	}

	/**
	 * @return the descricaoGrupoOuTituloComando
	 */
	public String getDescricaoGrupoOuTituloComando(){

		return descricaoGrupoOuTituloComando;
	}

	/**
	 * @param descricaoGrupoOuTituloComando
	 *            the descricaoGrupoOuTituloComando to set
	 */
	public void setDescricaoGrupoOuTituloComando(String descricaoGrupoOuTituloComando){

		this.descricaoGrupoOuTituloComando = descricaoGrupoOuTituloComando;
	}

	/**
	 * @return the descricaoAcao
	 */
	public String getDescricaoAcao(){

		return descricaoAcao;
	}

	/**
	 * @param descricaoAcao
	 *            the descricaoAcao to set
	 */
	public void setDescricaoAcao(String descricaoAcao){

		this.descricaoAcao = descricaoAcao;
	}

	/**
	 * @return the inicioCobranca
	 */
	public Date getInicioCobranca(){

		return inicioCobranca;
	}

	/**
	 * @param inicioCobranca
	 *            the inicioCobranca to set
	 */
	public void setInicioCobranca(Date inicioCobranca){

		this.inicioCobranca = inicioCobranca;
	}

	/**
	 * @return the fimCobranca
	 */
	public Date getFimCobranca(){

		return fimCobranca;
	}

	/**
	 * @param fimCobranca
	 *            the fimCobranca to set
	 */
	public void setFimCobranca(Date fimCobranca){

		this.fimCobranca = fimCobranca;
	}

	/**
	 * @return the quantidadeClientesInicioCobranca
	 */
	public Integer getQuantidadeClientesInicioCobranca(){

		return quantidadeClientesInicioCobranca;
	}

	/**
	 * @param quantidadeClientesInicioCobranca
	 *            the quantidadeClientesInicioCobranca to set
	 */
	public void setQuantidadeClientesInicioCobranca(Integer quantidadeClientesInicioCobranca){

		this.quantidadeClientesInicioCobranca = quantidadeClientesInicioCobranca;
	}

	/**
	 * @return the quantidadeDebitosInicioCobranca
	 */
	public Integer getQuantidadeDebitosInicioCobranca(){

		return quantidadeDebitosInicioCobranca;
	}

	/**
	 * @param quantidadeDebitosInicioCobranca
	 *            the quantidadeDebitosInicioCobranca to set
	 */
	public void setQuantidadeDebitosInicioCobranca(Integer quantidadeDebitosInicioCobranca){

		this.quantidadeDebitosInicioCobranca = quantidadeDebitosInicioCobranca;
	}

	/**
	 * @return the dividaCobradaInicioCobranca
	 */
	public BigDecimal getDividaCobradaInicioCobranca(){

		return dividaCobradaInicioCobranca;
	}

	/**
	 * @param dividaCobradaInicioCobranca
	 *            the dividaCobradaInicioCobranca to set
	 */
	public void setDividaCobradaInicioCobranca(BigDecimal dividaCobradaInicioCobranca){

		this.dividaCobradaInicioCobranca = dividaCobradaInicioCobranca;
	}

	/**
	 * @return the quantidadeClientesRecuperacaoDivida
	 */
	public Integer getQuantidadeClientesRecuperacaoDivida(){

		return quantidadeClientesRecuperacaoDivida;
	}

	/**
	 * @param quantidadeClientesRecuperacaoDivida
	 *            the quantidadeClientesRecuperacaoDivida to set
	 */
	public void setQuantidadeClientesRecuperacaoDivida(Integer quantidadeClientesRecuperacaoDivida){

		this.quantidadeClientesRecuperacaoDivida = quantidadeClientesRecuperacaoDivida;
	}

	/**
	 * @return the quantidadeDebitosRecuperacaoDivida
	 */
	public Integer getQuantidadeDebitosRecuperacaoDivida(){

		return quantidadeDebitosRecuperacaoDivida;
	}

	/**
	 * @param quantidadeDebitosRecuperacaoDivida
	 *            the quantidadeDebitosRecuperacaoDivida to set
	 */
	public void setQuantidadeDebitosRecuperacaoDivida(Integer quantidadeDebitosRecuperacaoDivida){

		this.quantidadeDebitosRecuperacaoDivida = quantidadeDebitosRecuperacaoDivida;
	}

	/**
	 * @return the dividaRecuperada
	 */
	public BigDecimal getDividaRecuperada(){

		return dividaRecuperada;
	}

	/**
	 * @param dividaRecuperada
	 *            the dividaRecuperada to set
	 */
	public void setDividaRecuperada(BigDecimal dividaRecuperada){

		this.dividaRecuperada = dividaRecuperada;
	}

	public BigDecimal getPorcentagemRecuperada(){

		return porcentagemRecuperada;
	}

	public void setPorcentagemRecuperada(BigDecimal porcentagemRecuperada){

		this.porcentagemRecuperada = porcentagemRecuperada;
	}

}
