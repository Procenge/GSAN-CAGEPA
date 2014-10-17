package gcom.agenciavirtual.cadastro.cliente;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

public class ConsultarClienteNaBaseWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		String cpfCnpjCliente = recuperarParametroCpfCnpj(true, request);
		Cliente clienteNaBase = null;

		FiltroCliente filtroClienteBase = new FiltroCliente();
		if(cpfCnpjCliente.length() > 11){
			filtroClienteBase.adicionarParametro(new ParametroSimples(FiltroCliente.CNPJ, new Integer(cpfCnpjCliente)));
		}else{
			filtroClienteBase.adicionarParametro(new ParametroSimples(FiltroCliente.CPF, new Integer(cpfCnpjCliente)));
		}

		Collection colecaoClienteBase = (Collection) fachada.pesquisar(filtroClienteBase, Cliente.class.getName());

		if(Util.isVazioOrNulo(colecaoClienteBase)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}else{
			clienteNaBase = (Cliente) Util.retonarObjetoDeColecao(colecaoClienteBase);
			ClienteJSONHelper clienteHelper = new ClienteJSONHelper();
			clienteHelper.setId(clienteNaBase.getId());

			adicionarObjetoAoBody("cliente", clienteHelper);

		}



	}
}
