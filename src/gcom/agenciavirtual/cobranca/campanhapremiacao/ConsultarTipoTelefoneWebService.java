
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.FiltroFoneTipo;
import gcom.cadastro.cliente.FoneTipo;
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
public class ConsultarTipoTelefoneWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		FiltroFoneTipo filtroFoneTipo = new FiltroFoneTipo();

		Collection<FoneTipo> colecaoFoneTipo = fachada.pesquisar(filtroFoneTipo, FoneTipo.class.getName());

		Collection<FoneTipoJSONHelper> colecaoFoneTipoJSONHelpers = new ArrayList<FoneTipoJSONHelper>();

		for(FoneTipo foneTipo : colecaoFoneTipo){
			FoneTipoJSONHelper helper = new FoneTipoJSONHelper();
			helper.setIdFoneTipo(foneTipo.getId());
			helper.setDescricao(foneTipo.getDescricao());
			colecaoFoneTipoJSONHelpers.add(helper);
		}

		adicionarListaAoBody("fonetipo", colecaoFoneTipoJSONHelpers);

		if(Util.isVazioOrNulo(colecaoFoneTipoJSONHelpers)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
