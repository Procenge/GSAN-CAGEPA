
package gcom.util.parametrizacao;

import br.com.procenge.parametrosistema.api.ParametroSistema;

public class ExecutorParametrosGeral
				implements ExecutorParametro {

	private static final ExecutorParametrosGeral instancia = new ExecutorParametrosGeral();

	private ExecutorParametrosGeral() {

	}

	public static ExecutorParametrosGeral getInstancia(){

		return instancia;
	}

	public String execParamModulo10(ParametroSistema param){

		return "executou execParamModulo10 # param=[id=" + param.getChavePrimaria() + ", valor='" + param.getValor() + "', tipo='"
						+ param.getTipoParametroSistema().getDescricao() + "']";
	}

	public String execParamModulo11(ParametroSistema param){

		return "executou execParamModulo11 # param=[id=" + param.getChavePrimaria() + ", valor='" + param.getValor() + "', tipo='"
						+ param.getTipoParametroSistema().getDescricao() + "']";
	}
}
