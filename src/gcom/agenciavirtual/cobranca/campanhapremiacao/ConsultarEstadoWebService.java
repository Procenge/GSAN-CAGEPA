
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna os ids e siglas das unidadeFederacao
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarEstadoWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class ConsultarEstadoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();

		Collection<UnidadeFederacao> colecaoUnidadeFederacao = fachada.pesquisar(filtroUnidadeFederacao, UnidadeFederacao.class.getName());

		Collection<UnidadeFederacaoJSONHelper> colecaoUnidadeFederacaoJSON = new ArrayList<UnidadeFederacaoJSONHelper>();

		for(UnidadeFederacao unidadeFederacao : colecaoUnidadeFederacao){
			UnidadeFederacaoJSONHelper helper = new UnidadeFederacaoJSONHelper();
			helper.setIdUnidadeFederacao(unidadeFederacao.getId());
			helper.setSigla(unidadeFederacao.getSigla());
			colecaoUnidadeFederacaoJSON.add(helper);
		}

		adicionarListaAoBody("estados", colecaoUnidadeFederacaoJSON);

		if(Util.isVazioOrNulo(colecaoUnidadeFederacaoJSON)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
