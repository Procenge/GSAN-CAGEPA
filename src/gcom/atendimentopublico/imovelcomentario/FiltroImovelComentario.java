package gcom.atendimentopublico.imovelcomentario;

import gcom.util.filtro.Filtro;

public class FiltroImovelComentario
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public FiltroImovelComentario() {

	}

	public FiltroImovelComentario(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String DESCRICAO = "descricao";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String USUARIO = "usuario";

	public final static String USUARIO_ID = "usuario.id";

	public final static String IMOVEL_COMENTARIO_ANTERIOR = "imovelComentarioAnterior";

	public final static String IMOVEL_COMENTARIO_ANTERIOR_ID = "imovelComentarioAnterior.id";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String ULTIMA_ALTERACAO = "ultimaAlteracao";

	public final static String DATA_INCLUSAO = "dataInclusao";

	public final static String SEQUENCIAL = "sequencial";

}
