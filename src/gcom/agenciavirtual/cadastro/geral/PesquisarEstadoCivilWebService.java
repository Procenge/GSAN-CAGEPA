
package gcom.agenciavirtual.cadastro.geral;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.EstadoCivil;
import gcom.cadastro.cliente.FiltroEstadoCivil;
import gcom.util.ServiceLocator;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Pesquisa EstadoCivil
 * URL de acesso: /agenciavirtual/pesquisarEstadoCivilWebService.do
 * 
 * @author Yara Souza
 */
public class PesquisarEstadoCivilWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{


		FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
		filtroEstadoCivil.setCampoOrderBy(FiltroEstadoCivil.DESCRICAO);
		Collection<EstadoCivil> colecaoEstadoCivil = ServiceLocator.getInstancia().getControladorUtil()
						.pesquisar(filtroEstadoCivil, EstadoCivil.class.getName());



		Collection<EstadoCivilJSONHelper> colecaoEstadoCivilJSONHelper = new ArrayList<EstadoCivilJSONHelper>();

		for(EstadoCivil EstadoCivil : colecaoEstadoCivil){

			EstadoCivilJSONHelper helper = new EstadoCivilJSONHelper();
			helper.setId(EstadoCivil.getId());
			helper.setDescricao(EstadoCivil.getDescricao());

			colecaoEstadoCivilJSONHelper.add(helper);
		}

		adicionarListaAoBody("listaEstadoCivil", colecaoEstadoCivilJSONHelper);

		if(Util.isVazioOrNulo(colecaoEstadoCivilJSONHelper)){

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
