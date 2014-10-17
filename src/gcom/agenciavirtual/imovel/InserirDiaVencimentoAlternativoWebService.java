
package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroVencimentoAlternativo;
import gcom.faturamento.VencimentoAlternativo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>matricula [OBRIGATORIO]</li>
 * <li>diaVencimento [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/inserirDiaVencimentoAlternativoWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class InserirDiaVencimentoAlternativoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer idImovel = recuperarParametroInteiroObrigatorio("matricula", "Imóvel", true, request);
		Integer diaVencimento = recuperarParametroInteiroObrigatorio("diaVencimento", "Novo Dia de Vencimento", true, request);
		Fachada fachada = Fachada.getInstancia();
		
		Imovel imovel = fachada.consultarImovel(idImovel);
		if(imovel == null){
			informarStatus(STATUS_TIPO_ALERTA,
							MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.matricula.imovel.inexistente"));
		}
		Cliente cliente = fachada.pesquisarClienteUsuarioImovel(imovel.getId());

		VencimentoAlternativo vencimentoAlternativo = null;
		FiltroVencimentoAlternativo filtroVencimentoAlternativo = new FiltroVencimentoAlternativo(
						FiltroVencimentoAlternativo.DATA_IMPLANTACAO);

		filtroVencimentoAlternativo.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.IMOVEL_ID, imovel.getId()));

		filtroVencimentoAlternativo.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.CLIENTE_ID, cliente.getId()));

		filtroVencimentoAlternativo.adicionarParametro(new ParametroNulo(FiltroVencimentoAlternativo.DATA_EXCLUSAO));

		Collection vencimentosAlternativos = fachada.pesquisar(filtroVencimentoAlternativo, VencimentoAlternativo.class.getName());

		if(vencimentosAlternativos != null && !vencimentosAlternativos.isEmpty()){
			vencimentoAlternativo = (VencimentoAlternativo) Util.retonarObjetoDeColecao(vencimentosAlternativos);
		}
		Short novoDiaVencimento = new Short(diaVencimento.shortValue());

		fachada.inserirVencimentoAlternativo(vencimentoAlternativo, imovel, cliente, novoDiaVencimento, Usuario.USUARIO_AGENCIA_VIRTUAL);
		
	}


	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
