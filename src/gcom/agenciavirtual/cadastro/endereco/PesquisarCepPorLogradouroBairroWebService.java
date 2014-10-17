
package gcom.agenciavirtual.cadastro.endereco;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Pesquisar Cep Por Logradouro e Bairro
 * Parametros no resquest.
 * URL de acesso: /agenciavirtual/PesquisarCepPorLogradouroBairroWebService.do
 * 
 * @author Anderson Italo
 */
public class PesquisarCepPorLogradouroBairroWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer idBairro = recuperarParametroInteiroObrigatorio("idBairro", "Bairro", true, request);
		Integer idLogradouro = recuperarParametroInteiroObrigatorio("idLogradouro", "Logradouro", true, request);

		List<CepJSONHelper> colecaoCepJSONHelper = new ArrayList<CepJSONHelper>();

		List<Object[]> colecaoCeps = Fachada.getInstancia().pesquisarCepPorLogradouroEBairro(idLogradouro, idBairro);

		if(!Util.isVazioOrNulo(colecaoCeps)){

			for(Object[] dadosCep : colecaoCeps){

				CepJSONHelper cepJSONHelper = new CepJSONHelper();
				cepJSONHelper.setIdCep(Util.obterInteger(dadosCep[0].toString()));
				cepJSONHelper.setCodigoCep(Util.obterInteger(dadosCep[1].toString()));
				colecaoCepJSONHelper.add(cepJSONHelper);
			}
		}

		if(Util.isVazioOrNulo(colecaoCepJSONHelper)){

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

		adicionarListaAoBody("listaCep", colecaoCepJSONHelper);


	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
