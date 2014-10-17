
package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

public class FiltroImovelCobrancaAdministrivaItem
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public FiltroImovelCobrancaAdministrivaItem() {

	}

	public FiltroImovelCobrancaAdministrivaItem(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String IMOVEL_COBRANCA_SITUACAO = "imovelCobrancaSituacao";

	public final static String IMOVEL_COBRANCA_SITUACAO_ID = "imovelCobrancaSituacao.id";

	public final static String CONTA_GERAL = "contaGeral";

	public final static String CONTA_GERAL_ID = "contaGeral.id";

	public final static String GUIA_PAGAMENTO_GERAL = "guiaPagamentoGeral";

	public final static String GUIA_PAGAMENTO_GERAL_ID = "guiaPagamentoGeral.id";

	public final static String NUMERO_PRESTACAO = "numeroPrestacao";

	public final static String DOCUMENTO_TIPO = "documentoTipo";

	public final static String DOCUMENTO_TIPO_ID = "documentoTipo.id";

	public final static String ID = "id";

	public final static String DEBITO_A_COBRAR_GERAL = "debitoACobrarGeral";

	public final static String DEBITO_A_COBRAR_GERAL_ID = "debitoACobrarGeral.id";

	public final static String COBRANCA_ADMINISTRATIVA_REMUNERACAO_MENSAL = "cobrancaAdministrativaRemuneracaoMensal.id";

}
