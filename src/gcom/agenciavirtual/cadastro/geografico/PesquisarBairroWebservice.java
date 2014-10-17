
package gcom.agenciavirtual.cadastro.geografico;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.fachada.Fachada;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

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
 * <li>nomeBairro [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/pesquisarBairroWebservice.do
 * 
 * @author Marlos Ribeiro
 */
public class PesquisarBairroWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer idMunicipio = recuperarParametroInteiroObrigatorio("idMunicipio", "Município", true, request);
		String nomeBairro = recuperarParametroString("nomeBairro", "Bairro", false, true, request);

		Fachada fachada = Fachada.getInstancia();
		if(!Util.isVazioOuBranco(nomeBairro)){
		nomeBairro = Util.substituirCaracteresEspeciais(nomeBairro);
		}

		FiltroBairro filtro = new FiltroBairro();
		filtro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipio));
		filtro.setCampoOrderBy(FiltroBairro.NOME);
		if(!Util.isVazioOuBranco(nomeBairro)){
			filtro.adicionarParametro(new ComparacaoTextoCompleto(FiltroBairro.NOME, nomeBairro));
		}

		Collection bairros = fachada.pesquisar(filtro, Bairro.class.getName());
		Collection<BairroJSONHelper> listBairroJSON = new ArrayList<BairroJSONHelper>();
		for(Object object : bairros){
			Bairro bairro = (Bairro) object;
			BairroJSONHelper helper = new BairroJSONHelper();
			helper.setIdBairro(bairro.getId());
			helper.setNomeBairro(bairro.getNome());
			listBairroJSON.add(helper);
		}

		adicionarListaAoBody("bairros", listBairroJSON);

		if(Util.isVazioOrNulo(listBairroJSON)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
