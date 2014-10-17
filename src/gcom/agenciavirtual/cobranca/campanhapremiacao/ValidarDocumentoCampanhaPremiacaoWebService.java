
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cobranca.campanhapremiacao.CampanhaDocumentoImpedido;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaDocumentoImpedido;
import gcom.fachada.Fachada;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna se o documento do cliente é permitido ou não
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>cpfcnpj [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/validarDocumentoCampanhaPremiacaoWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class ValidarDocumentoCampanhaPremiacaoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		String cpfCnpj = recuperarParametroCpfCnpj(true, request);

		Fachada fachada = Fachada.getInstancia();

		FiltroCampanhaDocumentoImpedido filtroCampanhaDocumentoImpedido = new FiltroCampanhaDocumentoImpedido();

		filtroCampanhaDocumentoImpedido.adicionarParametro(new ParametroSimples(FiltroCampanhaDocumentoImpedido.NUMERO_CPF, cpfCnpj,
						ConectorOr.CONECTOR_OR));
		filtroCampanhaDocumentoImpedido.adicionarParametro(new ParametroSimples(FiltroCampanhaDocumentoImpedido.NUMERO_CNPJ, cpfCnpj));

		Collection<CampanhaDocumentoImpedido> colecaoCampanhaDocumentoImpedido = fachada.pesquisar(filtroCampanhaDocumentoImpedido,
						CampanhaDocumentoImpedido.class.getName());

		if(!Util.isVazioOrNulo(colecaoCampanhaDocumentoImpedido)){

			CampanhaDocumentoImpedido campanhaDocumentoImpedido = (CampanhaDocumentoImpedido) Util
							.retonarObjetoDeColecao(colecaoCampanhaDocumentoImpedido);

			if(campanhaDocumentoImpedido.getNumeroCpf() != null){
				informarStatus(STATUS_TIPO_ALERTA,
								MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.campanhapremiacao.documento_impedido_cpf"));
			}else if(campanhaDocumentoImpedido.getNumeroCnpj() != null){
				informarStatus(STATUS_TIPO_ALERTA,
								MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.campanhapremiacao.documento_impedido_cnpj"));
			}

		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
