
package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.FiltroEnderecoReferencia;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna os Referências Endereço
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarReferenciasEnderecoWebService.do
 * 
 * @author Ado Rocha
 */
public class ConsultarReferenciasEnderecoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		FiltroEnderecoReferencia filtroEnderecoReferencia = new FiltroEnderecoReferencia();

		filtroEnderecoReferencia.setCampoOrderBy(FiltroEnderecoReferencia.ID);

		Collection<EnderecoReferencia> colecaoEnderecoReferencia = fachada.pesquisar(filtroEnderecoReferencia,
						EnderecoReferencia.class.getName());

		Collection<EnderecoReferenciaJSONHelper> colecaoEnderecoReferenciaJSONHelper = new ArrayList<EnderecoReferenciaJSONHelper>();

		for(EnderecoReferencia enderecoReferencia : colecaoEnderecoReferencia){
			EnderecoReferenciaJSONHelper helper = new EnderecoReferenciaJSONHelper();
			helper.setId(enderecoReferencia.getId());
			helper.setDescricao(enderecoReferencia.getDescricao());
			helper.setDescricaoAbreviada(enderecoReferencia.getDescricaoAbreviada());
			helper.setIndicadorUso(enderecoReferencia.getIndicadorUso());
			helper.setUltimaAlteracao(enderecoReferencia.getUltimaAlteracao());
			colecaoEnderecoReferenciaJSONHelper.add(helper);
		}

		adicionarListaAoBody("enderecoReferencia", colecaoEnderecoReferenciaJSONHelper);

		if(Util.isVazioOrNulo(colecaoEnderecoReferencia)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
