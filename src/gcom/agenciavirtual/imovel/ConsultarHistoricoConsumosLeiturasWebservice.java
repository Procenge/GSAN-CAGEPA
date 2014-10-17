/**
 * 
 */

package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.imovel.bean.ImovelConsumoLeituraHistorico;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Consulta o historico de consumos e leituras
 * <b>Este serviço não é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>matricula [OBRIGATORIO]</li>
 * <li>cpfcnpj [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarHistoricoConsumosLeiturasWebservice.do
 * 
 * @author Marlos Ribeiro
 */
public class ConsultarHistoricoConsumosLeiturasWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice#preencherJSONBody(org.apache.struts
	 * .action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		// Validar Matricula e CPF/CNPJ
		String matricula = recuperarParametroStringObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request);
		Collection<ImovelConsumoLeituraHistorico> listaConsumoLeituraHistorico = Fachada.getInstancia().consultarConsumoLeituraHistorico(
						matricula);
		adicionarListaAoBody("consumoLeituraHistorico", listaConsumoLeituraHistorico);

		if(Util.isVazioOrNulo(listaConsumoLeituraHistorico)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}
}
