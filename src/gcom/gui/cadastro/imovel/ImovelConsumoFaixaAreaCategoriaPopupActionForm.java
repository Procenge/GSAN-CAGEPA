
package gcom.gui.cadastro.imovel;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * Description of the Class
 * 
 * @author Hebert Falcão
 * @created 20/01/2011
 */
public class ImovelConsumoFaixaAreaCategoriaPopupActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String id;

	private String imovelId;

	private String categoriaId;

	private String comprimentoFrente;

	private String comprimentoLado;

	private String comprimentoTestada;

	private String numeroAndares;

	private String comprimentoAndar;

	private String areaConstruida;

	private String indicadorUso;

	/**
	 * @return the areaConstruida
	 */
	public String getAreaConstruida(){

		return areaConstruida;
	}

	/**
	 * @return the categoriaId
	 */
	public String getCategoriaId(){

		return categoriaId;
	}

	/**
	 * @return the comprimentoAndar
	 */
	public String getComprimentoAndar(){

		return comprimentoAndar;
	}

	/**
	 * @return the comprimentoFrente
	 */
	public String getComprimentoFrente(){

		return comprimentoFrente;
	}

	/**
	 * @return the comprimentoLado
	 */
	public String getComprimentoLado(){

		return comprimentoLado;
	}

	/**
	 * @return the comprimentoTestada
	 */
	public String getComprimentoTestada(){

		return comprimentoTestada;
	}

	/**
	 * @return the id
	 */
	public String getId(){

		return id;
	}

	/**
	 * @return the imovelId
	 */
	public String getImovelId(){

		return imovelId;
	}

	/**
	 * @return the indicadorUso
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @return the numeroAndares
	 */
	public String getNumeroAndares(){

		return numeroAndares;
	}

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 */
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

	}

	/**
	 * @param areaConstruida
	 *            the areaConstruida to set
	 */
	public void setAreaConstruida(String areaConstruida){

		this.areaConstruida = areaConstruida;
	}

	/**
	 * @param categoriaId
	 *            the categoriaId to set
	 */
	public void setCategoriaId(String categoriaId){

		this.categoriaId = categoriaId;
	}

	/**
	 * @param comprimentoAndar
	 *            the comprimentoAndar to set
	 */
	public void setComprimentoAndar(String comprimentoAndar){

		this.comprimentoAndar = comprimentoAndar;
	}

	/**
	 * @param comprimentoFrente
	 *            the comprimentoFrente to set
	 */
	public void setComprimentoFrente(String comprimentoFrente){

		this.comprimentoFrente = comprimentoFrente;
	}

	/**
	 * @param comprimentoLado
	 *            the comprimentoLado to set
	 */
	public void setComprimentoLado(String comprimentoLado){

		this.comprimentoLado = comprimentoLado;
	}

	/**
	 * @param comprimentoTestada
	 *            the comprimentoTestada to set
	 */
	public void setComprimentoTestada(String comprimentoTestada){

		this.comprimentoTestada = comprimentoTestada;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id){

		this.id = id;
	}

	/**
	 * @param imovelId
	 *            the imovelId to set
	 */
	public void setImovelId(String imovelId){

		this.imovelId = imovelId;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @param numeroAndares
	 *            the numeroAndares to set
	 */
	public void setNumeroAndares(String numeroAndares){

		this.numeroAndares = numeroAndares;
	}

}
