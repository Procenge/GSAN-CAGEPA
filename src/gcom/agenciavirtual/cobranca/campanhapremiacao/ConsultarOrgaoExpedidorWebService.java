
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.FiltroOrgaoExpedidorRg;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna os órgãos expedidores de RG
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarOrgaoExpedidorWebService.do
 * 
 * @author Hiroshi Goncalves, Anderson Italo
 */
public class ConsultarOrgaoExpedidorWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
		
		filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroOrgaoExpedidorRg.setCampoOrderBy(FiltroOrgaoExpedidorRg.DESCRICAO);

		Collection colOrgaoExpedidor = fachada.pesquisar(filtroOrgaoExpedidorRg, OrgaoExpedidorRg.class.getName());

		Collection<OrgaoExpedidorJSONHelper> listOrgaoExpedidorJSON = new ArrayList<OrgaoExpedidorJSONHelper>();
			
		for(Object object : colOrgaoExpedidor){

			OrgaoExpedidorRg orgaoExpedidorRg = (OrgaoExpedidorRg) object;
			OrgaoExpedidorJSONHelper helper = new OrgaoExpedidorJSONHelper();
			helper.setId(orgaoExpedidorRg.getId());
			helper.setDescricao(orgaoExpedidorRg.getDescricao());
			helper.setDescricaoAbreviada(orgaoExpedidorRg.getDescricaoAbreviada());
			helper.setIndicadorUso(orgaoExpedidorRg.getIndicadorUso());
			helper.setDataUltimaAlteracao(orgaoExpedidorRg.getUltimaAlteracao());
			listOrgaoExpedidorJSON.add(helper);
		}

		adicionarListaAoBody("colOrgaoExpedidor", listOrgaoExpedidorJSON);


		if(Util.isVazioOrNulo(listOrgaoExpedidorJSON)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}