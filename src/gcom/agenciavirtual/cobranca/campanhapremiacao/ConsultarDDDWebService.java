
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna os Tipos de Telefone
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarTipoTelefoneWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class ConsultarDDDWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

		Collection<Municipio> colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

		Collection<DDDJSONHelper> colecaoDddjsonHelpers = new ArrayList<DDDJSONHelper>();

		for(Municipio municipio : colecaoMunicipio){
			DDDJSONHelper helper = new DDDJSONHelper();
			helper.setDddCodigo(municipio.getDdd().intValue());
			helper.setDddMunicipio(municipio.getDddComMunicipio());
			colecaoDddjsonHelpers.add(helper);
		}

		adicionarListaAoBody("ddd", colecaoDddjsonHelpers);

		if(Util.isVazioOrNulo(colecaoDddjsonHelpers)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
