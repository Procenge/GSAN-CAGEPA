
package gcom.cobranca;

import gcom.interceptor.ControleAlteracao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

@ControleAlteracao()
public class BoletoBancarioSituacao
				extends TabelaAuxiliarAbreviada {

	public static final int OPERACAO_BOLETO_BANCARIO_CANCELAR = 302466;

	private static final long serialVersionUID = 1L;

	public static final int GERADO_NAO_ENVIADO_AO_BANCO = 1;

	public static final Integer ENVIADO_AO_BANCO = 2;

	public static final Integer EM_CARTEIRA = 3;

	public static final Integer REJEITADO = 4;

	public static final Integer LIQUIDACAO = 5;

	public static final Integer LIQUIDACAO_PENDENTE = 6;

	public static final Integer BAIXA = 7;

	public static final Integer EM_CARTORIO = 8;

	public static final Integer CANCELADO = 9;

	public static final Integer LIQUIDACAO_VIA_EMPRESA = 10;

	public static final Integer CANCELADO_BAIXA_E_PROTESTO = 11;

	/** default constructor */
	public BoletoBancarioSituacao() {

	}

	public Filtro retornaFiltro(){

		FiltroBoletoBancarioSituacao filtroSituacao = new FiltroBoletoBancarioSituacao();
		filtroSituacao.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioSituacao.ID, this.getId()));

		return filtroSituacao;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

}
