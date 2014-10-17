
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.fachada.Fachada;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

/**
 * Retorna o endereco do Imovel
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idImovel [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarEnderecoImovelWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class ConsultarEnderecoImovelWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer idImovel = recuperarParametroInteiroObrigatorio("idImovel", "Imóvel", true, request);

		Fachada fachada = Fachada.getInstancia();

		String endereco = fachada.pesquisarEnderecoFormatado(Integer.valueOf(idImovel));

		adicionarValorPrimitivoAoBody("endereco", endereco);

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
