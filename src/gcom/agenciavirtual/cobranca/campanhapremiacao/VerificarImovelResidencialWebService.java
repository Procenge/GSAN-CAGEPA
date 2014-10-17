
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

/**
 * Retorna uma flag com o valor:
 * 1 - se imóvel é residencial e possui até 2 economias
 * 2 - se imóvel não é residencial ou se possui mais de 2 economias
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idImovel [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/verificarImovelResidencialWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class VerificarImovelResidencialWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer idImovel = recuperarParametroInteiroObrigatorio("idImovel", "Imóvel", true, request);

		Fachada fachada = Fachada.getInstancia();

		Categoria categoriaPrincipalImovel = fachada.obterPrincipalCategoriaImovel(idImovel.intValue());
		int qtEconomiasImovel = fachada.obterQuantidadeEconomias(new Imovel(idImovel.intValue()));

		if(categoriaPrincipalImovel.getId().equals(Categoria.RESIDENCIAL) && qtEconomiasImovel <= 2){
			adicionarValorPrimitivoAoBody("flagResidencial", ConstantesSistema.SIM);
		}else{
			adicionarValorPrimitivoAoBody("flagResidencial", ConstantesSistema.NAO);
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
