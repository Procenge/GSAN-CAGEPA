
package gcom.agenciavirtual.cadastro.geral;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.FiltroRaca;
import gcom.cadastro.cliente.Raca;
import gcom.util.ServiceLocator;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Pesquisa raça
 * URL de acesso: /agenciavirtual/pesquisarRacaWebService.do
 * 
 * @author Yara Souza
 */
public class PesquisarRacaWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{


		FiltroRaca filtroRaca = new FiltroRaca();
		filtroRaca.setCampoOrderBy(FiltroRaca.DESCRICAO);
		Collection<Raca> colecaoRaca = ServiceLocator.getInstancia().getControladorUtil().pesquisar(filtroRaca, Raca.class.getName());



		Collection<RacaJSONHelper> colecaoRacaJSONHelper = new ArrayList<RacaJSONHelper>();

		for(Raca Raca : colecaoRaca){

			RacaJSONHelper helper = new RacaJSONHelper();
			helper.setId(Raca.getId());
			helper.setDescricao(Raca.getDescricao());

			colecaoRacaJSONHelper.add(helper);
		}

		adicionarListaAoBody("listaRaca", colecaoRacaJSONHelper);

		if(Util.isVazioOrNulo(colecaoRacaJSONHelper)){

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
