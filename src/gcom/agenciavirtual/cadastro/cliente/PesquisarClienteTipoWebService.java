
package gcom.agenciavirtual.cadastro.cliente;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroClienteTipo;
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
 * Parametros no resquest.
 * URL de acesso: /agenciavirtual/pesquisarClienteTipoWebservice.do
 * 
 * @author Yara Souza
 */
public class PesquisarClienteTipoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{


		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
		filtroClienteTipo.setCampoOrderBy(FiltroClienteTipoEspecial.DESCRICAO);
		Collection<ClienteTipo> colecaoClienteTipo = ServiceLocator.getInstancia().getControladorUtil()
						.pesquisar(filtroClienteTipo, ClienteTipo.class.getName());



		Collection<ClienteTipoJSONHelper> colecaoClienteTipoJSONHelper = new ArrayList<ClienteTipoJSONHelper>();

		for(ClienteTipo clienteTipo : colecaoClienteTipo){

			ClienteTipoJSONHelper helper = new ClienteTipoJSONHelper();
			helper.setId(clienteTipo.getId());
			helper.setDescricao(clienteTipo.getDescricao());
			helper.setIndicadorPessoaFisicaJuridica(clienteTipo.getIndicadorPessoaFisicaJuridica());
			helper.setIndicadorUso(clienteTipo.getIndicadorUso());
			helper.setEsferaPoder(clienteTipo.getEsferaPoder());
			colecaoClienteTipoJSONHelper.add(helper);
		}

		adicionarListaAoBody("tiposCliente", colecaoClienteTipoJSONHelper);

		if(Util.isVazioOrNulo(colecaoClienteTipoJSONHelper)){

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
