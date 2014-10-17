
package gcom.agenciavirtual.cadastro.geral;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.FiltroProfissao;
import gcom.cadastro.cliente.Profissao;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna as Profissoes
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * </ul>
 * URL de acesso: agenciavirtual/consultarProfissoesWebService.do
 * 
 * @author Eduardo Oliveira
 */
public class ConsultarProfissoesWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		FiltroProfissao filtroProfissao = new FiltroProfissao();

		Collection<Profissao> colecaoProfissao = fachada.pesquisar(filtroProfissao, Profissao.class.getName());

		Collection<ProfissoesJSONHelper> colecaoProfissoesJSonHelpers = new ArrayList<ProfissoesJSONHelper>();

		for(Profissao profissao : colecaoProfissao){
			ProfissoesJSONHelper helper = new ProfissoesJSONHelper();
			helper.setIdProfissao(profissao.getId());
			helper.setCodigoProfissao(profissao.getCodigo());
			helper.setDescricaoProfissao(profissao.getDescricao());
			helper.setIndicadorUso(profissao.getIndicadorUso());
			helper.setUltimaAlteracao(profissao.getUltimaAlteracao());

			colecaoProfissoesJSonHelpers.add(helper);

			BeanComparator comparador = new BeanComparator("codigoProfissao");
			Collections.sort((List) colecaoProfissoesJSonHelpers, comparador);

		}

		if(Util.isVazioOrNulo(colecaoProfissoesJSonHelpers)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

		adicionarListaAoBody("profissoes", colecaoProfissoesJSonHelpers);


	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
