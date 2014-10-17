
package gcom.agenciavirtual.cadastro.geral;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.FiltroNacionalidade;
import gcom.cadastro.cliente.Nacionalidade;
import gcom.util.ServiceLocator;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Pesquisa NACIONALIDADE
 * URL de acesso: /agenciavirtual/pesquisarNacionalidadeWebService.do
 * 
 * @author Yara Souza
 */
public class PesquisarNacionalidadeWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{


		FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
		filtroNacionalidade.setCampoOrderBy(FiltroNacionalidade.DESCRICAO);
		Collection<Nacionalidade> colecaoNacionalidade = ServiceLocator.getInstancia().getControladorUtil()
						.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());



		Collection<NacionalidadeJSONHelper> colecaoNacionalidadeJSONHelper = new ArrayList<NacionalidadeJSONHelper>();

		for(Nacionalidade Nacionalidade : colecaoNacionalidade){

			NacionalidadeJSONHelper helper = new NacionalidadeJSONHelper();
			helper.setId(Nacionalidade.getId());
			helper.setDescricao(Nacionalidade.getDescricao());

			colecaoNacionalidadeJSONHelper.add(helper);
		}

		adicionarListaAoBody("listaNacionalidade", colecaoNacionalidadeJSONHelper);

		if(Util.isVazioOrNulo(colecaoNacionalidadeJSONHelper)){

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
