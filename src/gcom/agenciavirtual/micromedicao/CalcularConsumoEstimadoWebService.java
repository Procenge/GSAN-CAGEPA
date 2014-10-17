package gcom.agenciavirtual.micromedicao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.util.Calculos;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

public class CalcularConsumoEstimadoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Date dataLeituraAnterior = Util.converterStringParaData(this.recuperarParametroString("dataLeituraAnterior",
						"DATA LEITURA ANTERIOR", Boolean.TRUE, Boolean.TRUE, request));

		Date dataLeituraAtual = Util.converterStringParaData(this.recuperarParametroString("dataLeituraAtual", "DATA LEITURA ATUAL",
						Boolean.TRUE, Boolean.TRUE, request));
		
		Integer leituraAnterior = this
						.recuperarParametroInteiro("leituraAnterior", "LEITURA ANTERIOR", Boolean.TRUE, Boolean.TRUE, request);
		
		Integer leituraAtual = this.recuperarParametroInteiro("leituraAtual", "LEITURA ATUAL", Boolean.TRUE, Boolean.TRUE, request);
		
		BigDecimal consumoEstimado = Calculos.calcularConsumoEstimado(dataLeituraAnterior, dataLeituraAtual, leituraAnterior, leituraAtual);

		this.adicionarValorPrimitivoAoBody("consumoEstimado", String.valueOf(consumoEstimado));

	}

}
