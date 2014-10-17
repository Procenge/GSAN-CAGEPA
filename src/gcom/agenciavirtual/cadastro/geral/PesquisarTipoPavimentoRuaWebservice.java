/**
 * 
 */

package gcom.agenciavirtual.cadastro.geral;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.imovel.FiltroPavimentoRua;
import gcom.cadastro.imovel.PavimentoRua;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * @author mgrb
 */
public class PesquisarTipoPavimentoRuaWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice#preencherJSONBody(org.apache.struts
	 * .action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		FiltroPavimentoRua filtroPavimentoRua = new FiltroPavimentoRua(FiltroPavimentoRua.DESCRICAO);
		filtroPavimentoRua
						.adicionarParametro(new ParametroSimples(FiltroPavimentoRua.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroPavimentoRua.setConsultaSemLimites(true);
		Collection colecaoPavimentoRua = Fachada.getInstancia().pesquisar(filtroPavimentoRua, PavimentoRua.class.getName());

		if(Util.isVazioOrNulo(colecaoPavimentoRua)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}else{
			List<ObjetoChaveDescricaoJSON> listaPavimentos = new ArrayList<ObjetoChaveDescricaoJSON>();
			for(Object obj : colecaoPavimentoRua){
				PavimentoRua pavimentoRua = (PavimentoRua) obj;
				listaPavimentos.add(new ObjetoChaveDescricaoJSON(pavimentoRua.getId(), pavimentoRua.getDescricao()));
			}
			adicionarListaAoBody("tiposPavimento", listaPavimentos);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.agenciavirtual.AbstractAgenciaVirtualWebservice#isServicoRestrito()
	 */
	@Override
	protected boolean isServicoRestrito(){

		return false;
	}

}
