
package gcom.cadastro.imovel;

import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hebert Falcão
 * @created 17/01/2011
 */
@ControleAlteracao()
public class ImovelConsumoFaixaAreaCategoriaPK
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	@ControleAlteracao(value = FiltroImovelConsumoFaixaAreaCategoria.CONSUMO_FAIXA_AREA_CATEGORIA, funcionalidade = {ImovelConsumoFaixaAreaCategoria.ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ImovelConsumoFaixaAreaCategoria.ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ImovelConsumoFaixaAreaCategoria.ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria;

	@ControleAlteracao(value = FiltroImovelConsumoFaixaAreaCategoria.IMOVEL, funcionalidade = {ImovelConsumoFaixaAreaCategoria.ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ImovelConsumoFaixaAreaCategoria.ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER, ImovelConsumoFaixaAreaCategoria.ATRIBUTOS_IMOVEL_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR})
	private Imovel imovel;

	/**
	 * @return the consumoFaixaAreaCategoria
	 */
	public ConsumoFaixaAreaCategoria getConsumoFaixaAreaCategoria(){

		return consumoFaixaAreaCategoria;
	}

	/**
	 * @param consumoFaixaAreaCategoria
	 *            the consumoFaixaAreaCategoria to set
	 */
	public void setConsumoFaixaAreaCategoria(ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria){

		this.consumoFaixaAreaCategoria = consumoFaixaAreaCategoria;
	}

	/**
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public boolean equals(Object other){

		if((this == other)){
			return true;
		}

		if(!(other instanceof ImovelConsumoFaixaAreaCategoriaPK)){
			return false;
		}

		ImovelConsumoFaixaAreaCategoriaPK castOther = (ImovelConsumoFaixaAreaCategoriaPK) other;

		return new EqualsBuilder().append(this.getConsumoFaixaAreaCategoria(), castOther.getConsumoFaixaAreaCategoria()).append(
						this.getImovel(), castOther.getImovel()).isEquals();
	}

	public int hashCode(){

		return new HashCodeBuilder().append(getConsumoFaixaAreaCategoria()).append(getImovel()).toHashCode();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[2];
		retorno[0] = "consumoFaixaAreaCategoria";
		retorno[1] = "imovel";
		return retorno;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"consumoFaixaAreaCategoria.id", "imovel.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Grupo Usuario", "Imovel"};
		return labels;
	}

	public Filtro retornaFiltro(){

		return null;
	}

	public String toString(){

		return new ToStringBuilder(this).append("consumoFaixaAreaCategoria", getConsumoFaixaAreaCategoria()).append("imovel", getImovel())
						.toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getConsumoFaixaAreaCategoria().getId() + " " + getImovel().getId();
	}

	@Override
	public Date getUltimaAlteracao(){

		return null;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		// TODO Auto-generated method stub

	}

}
