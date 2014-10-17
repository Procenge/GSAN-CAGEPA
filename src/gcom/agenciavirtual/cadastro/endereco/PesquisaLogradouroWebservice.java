
package gcom.agenciavirtual.cadastro.endereco;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.Util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Pesquisa o(s) logradouros
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idMunicipio [OBRIGATORIO]</li>
 * <li>idBairro [OBRIGATORIO]</li>
 * <li>nomeLogradouro [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/pesquisaLogradouroWebservice.do
 * 
 * @author Josenildo Neves
 */
public class PesquisaLogradouroWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer idMunicipio = recuperarParametroInteiroObrigatorio("idMunicipio", "Município", true, request);
		Integer idBairro = recuperarParametroInteiroObrigatorio("idBairro", "Bairro", true, request);
		String nomeLogradouro = recuperarParametroString("nomeLogradouro", "Logradouro", false, true, request);
		if(!Util.isVazioOuBranco(nomeLogradouro)){
			nomeLogradouro = Util.substituirCaracteresEspeciais(nomeLogradouro.toUpperCase());
		}

		List<LogradouroJSONHelper> listaLogradouroJSONHelper = criarListaLogradouroJSONHelper(idMunicipio, idBairro, nomeLogradouro);

		adicionarListaAoBody("listaLogradouros", listaLogradouroJSONHelper);

		if(Util.isVazioOrNulo(listaLogradouroJSONHelper)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	private List<LogradouroJSONHelper> criarListaLogradouroJSONHelper(Integer idMunicipio, Integer idBairro, String nomeLogradouro)
					throws ControladorException{

		List<LogradouroJSONHelper> listaLogradouroJSONHelper = ServiceLocator.getInstancia().getControladorEndereco()
						.pesquisarViewLogradouro(idMunicipio, idBairro, nomeLogradouro);

		if(Util.isVazioOrNulo(listaLogradouroJSONHelper)){

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
		return listaLogradouroJSONHelper;
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
