package gcom.operacional;

import gcom.util.filtro.Filtro;

public class FiltroDiametroRedeAgua
				extends Filtro {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroDiametroRedeAgua object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroDiametroRedeAgua(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Construtor da classe FiltroDiametroRedeAgua
	 */
	public FiltroDiametroRedeAgua() {

	}

	/**
	 * Description of the Field
	 * private Integer id;
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricao";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO_ABREVIADA = "descricaoAbreviada";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_DE_USO = "indicadorUso";

	public static final String ULTIMA_ALTERACAO = "ultimaAlteracao";



}
