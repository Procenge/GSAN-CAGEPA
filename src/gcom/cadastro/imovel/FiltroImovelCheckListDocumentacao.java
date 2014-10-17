package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @author Vicente Zarga
 * @date 05/09/2013
 *       Filtro Imovel documentos Check List
 */
public class FiltroImovelCheckListDocumentacao
				extends Filtro
				implements Serializable {

	public FiltroImovelCheckListDocumentacao() {

	}

	public FiltroImovelCheckListDocumentacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_ID = "imovel.id";

}
