
package gcom.agenciavirtual.cadastro.cliente;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.ClienteTipoEspecial;
import gcom.cadastro.cliente.FiltroClienteTipoEspecial;
import gcom.util.ServiceLocator;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Pesquisa o(s) tipos de cliente especial
 * URL de acesso: /agenciavirtual/pesquisarClienteTipoEspecialWebService.do
 * 
 * @author Anderson Italo
 */
public class PesquisarClienteTipoEspecialWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{


		FiltroClienteTipoEspecial filtroClienteTipoEspecial = new FiltroClienteTipoEspecial();
		filtroClienteTipoEspecial.setCampoOrderBy(FiltroClienteTipoEspecial.DESCRICAO);
		Collection<ClienteTipoEspecial> colecaoClienteTipoEspecial = ServiceLocator.getInstancia().getControladorUtil()
						.pesquisar(filtroClienteTipoEspecial, ClienteTipoEspecial.class.getName());



		Collection<ClienteTipoEspecialJSONHelper> colecaoClienteTipoEspecialJSONHelper = new ArrayList<ClienteTipoEspecialJSONHelper>();

		for(ClienteTipoEspecial clienteTipoEspecial : colecaoClienteTipoEspecial){

			ClienteTipoEspecialJSONHelper helper = new ClienteTipoEspecialJSONHelper();
			helper.setId(clienteTipoEspecial.getId());
			helper.setDescricao(clienteTipoEspecial.getDescricao());
			helper.setDescricaoAbreviada(clienteTipoEspecial.getNomeAbreviado());
			helper.setDataUltimaAlteracao(clienteTipoEspecial.getUltimaAlteracao());
			colecaoClienteTipoEspecialJSONHelper.add(helper);
		}

		adicionarListaAoBody("listaTipoClienteEspecial", colecaoClienteTipoEspecialJSONHelper);

		if(Util.isVazioOrNulo(colecaoClienteTipoEspecialJSONHelper)){

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
