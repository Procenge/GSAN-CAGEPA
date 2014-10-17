
package gcom.agenciavirtual.cadastro.geografico;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.geografico.Municipio;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Pesquisa o(s) municipios
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>nomeMunicipio [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/pesquisarMunicipioWebservice.do
 * 
 * @author Jose Claudio
 */
public class PesquisarMunicipioWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		String nomeMunicipio = recuperarParametroString("nomeMunicipio", "Município", false, true,
						request);
		if(!Util.isVazioOuBranco(nomeMunicipio)){
			nomeMunicipio = Util.substituirCaracteresEspeciais(nomeMunicipio.toUpperCase());
		}

		Collection<MunicipioJSONHelper> collMunicipioJSONHelper = criarMunicipioJSONHelper(nomeMunicipio);

		adicionarListaAoBody("municipios", collMunicipioJSONHelper);

		if(Util.isVazioOrNulo(collMunicipioJSONHelper)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	private Collection<MunicipioJSONHelper> criarMunicipioJSONHelper(String nomeMunicipio) throws ControladorException{

		MunicipioJSONHelper municipioJSONHelper = null;
		Collection<Municipio> collMunicipios = ServiceLocator.getInstancia().getControladorGeografico()
						.pesquisarMunicipioPeloNome(nomeMunicipio);

		if(Util.isVazioOrNulo(collMunicipios)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

		Collection<MunicipioJSONHelper> collMunicipioJsonHelper = new ArrayList<MunicipioJSONHelper>();

		for(Municipio municipio : collMunicipios){

			municipioJSONHelper = new MunicipioJSONHelper();

			municipioJSONHelper.setId(municipio.getId());
			municipioJSONHelper.setDescricao(municipio.getNome().toUpperCase());

			collMunicipioJsonHelper.add(municipioJSONHelper);

		}

		return collMunicipioJsonHelper;
	}

}
