
package gcom.cadastro.imovel;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hebert Falcão
 * @created 17/01/2011
 */
@ControleAlteracao()
public class ImovelConsumoFaixaAreaCategoria
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR = 56925;

	public static final int ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER = 57443;

	public static final int ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR = 57961;

	@ControleAlteracao(value = FiltroImovelConsumoFaixaAreaCategoria.COMP_ID, funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private ImovelConsumoFaixaAreaCategoriaPK comp_id;

	@ControleAlteracao(value = FiltroImovelConsumoFaixaAreaCategoria.CATEGORIA, funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private Categoria categoria;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private int comprimentoFrente;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private int comprimentoLado;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private Integer comprimentoTestada;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private Integer numeroAndares;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private Integer comprimentoAndar;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private int areaConstruida;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private short indicadorUso;

	/**
	 * persistent field
	 */
	private Date ultimaAlteracao;

	/**
	 * @return the comp_id
	 */
	public ImovelConsumoFaixaAreaCategoriaPK getComp_id(){

		return comp_id;
	}

	/**
	 * @param compId
	 *            the comp_id to set
	 */
	public void setComp_id(ImovelConsumoFaixaAreaCategoriaPK compId){

		comp_id = compId;
	}

	/**
	 * @return the categoria
	 */
	public Categoria getCategoria(){

		return categoria;
	}

	/**
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	/**
	 * @return the comprimentoFrente
	 */
	public int getComprimentoFrente(){

		return comprimentoFrente;
	}

	/**
	 * @param comprimentoFrente
	 *            the comprimentoFrente to set
	 */
	public void setComprimentoFrente(int comprimentoFrente){

		this.comprimentoFrente = comprimentoFrente;
	}

	/**
	 * @return the comprimentoLado
	 */
	public int getComprimentoLado(){

		return comprimentoLado;
	}

	/**
	 * @param comprimentoLado
	 *            the comprimentoLado to set
	 */
	public void setComprimentoLado(int comprimentoLado){

		this.comprimentoLado = comprimentoLado;
	}

	/**
	 * @return the comprimentoTestada
	 */
	public Integer getComprimentoTestada(){

		return comprimentoTestada;
	}

	/**
	 * @param comprimentoTestada
	 *            the comprimentoTestada to set
	 */
	public void setComprimentoTestada(Integer comprimentoTestada){

		this.comprimentoTestada = comprimentoTestada;
	}

	/**
	 * @return the numeroAndares
	 */
	public Integer getNumeroAndares(){

		return numeroAndares;
	}

	/**
	 * @param numeroAndares
	 *            the numeroAndares to set
	 */
	public void setNumeroAndares(Integer numeroAndares){

		this.numeroAndares = numeroAndares;
	}

	/**
	 * @return the comprimentoAndar
	 */
	public Integer getComprimentoAndar(){

		return comprimentoAndar;
	}

	/**
	 * @param comprimentoAndar
	 *            the comprimentoAndar to set
	 */
	public void setComprimentoAndar(Integer comprimentoAndar){

		this.comprimentoAndar = comprimentoAndar;
	}

	/**
	 * @return the areaConstruida
	 */
	public int getAreaConstruida(){

		return areaConstruida;
	}

	/**
	 * @param areaConstruida
	 *            the areaConstruida to set
	 */
	public void setAreaConstruida(int areaConstruida){

		this.areaConstruida = areaConstruida;
	}

	/**
	 * @return the indicadorUso
	 */
	public short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"comp_id"};
		return retorno;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"categoria.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Categoria"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroImovelConsumoFaixaAreaCategoria filtroImovelConsumoFaixaAreaCategoria = new FiltroImovelConsumoFaixaAreaCategoria();
		filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(
						FiltroImovelConsumoFaixaAreaCategoria.CONSUMO_FAIXA_AREA_CATEGORIA_ID, this.getComp_id()
										.getConsumoFaixaAreaCategoria().getId()));
		filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL_ID, this
						.getComp_id().getImovel().getId()));
		filtroImovelConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CATEGORIA);
		filtroImovelConsumoFaixaAreaCategoria
						.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CONSUMO_FAIXA_AREA_CATEGORIA);
		filtroImovelConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL);
		return filtroImovelConsumoFaixaAreaCategoria;
	}

	public String toString(){

		return new ToStringBuilder(this).append("comp_id", getComp_id()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getComp_id() + "";
	}

}
