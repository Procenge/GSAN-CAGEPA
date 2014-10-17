
package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.fachada.Fachada;
import gcom.relatorio.faturamento.RelatorioArquivoDeclaracaoAnualQuitacaoDebitos;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

/**
 * Retorna um indicador informando se é possível emitir a Declaração
 * Anual Quitação Debitos para o imovel.
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idImovel [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso:
 * /agenciavirtual/verificarExisteDadosEmitirDeclaracaoAnualQuitacaoDebitosWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class VerificarExisteDadosEmitirDeclaracaoAnualQuitacaoDebitosWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer imovelId = recuperarParametroInteiroObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request);
		try{
		Fachada fachada = Fachada.getInstancia();

		RelatorioArquivoDeclaracaoAnualQuitacaoDebitos relatorioArquivoDeclaracaoAnualQuitacaoDebitos = fachada
							.emitirDeclaracaoAnualQuitacaoDebitos(null, imovelId,
											Usuario.USUARIO_AGENCIA_VIRTUAL, null);
		if(relatorioArquivoDeclaracaoAnualQuitacaoDebitos != null){
			adicionarValorPrimitivoAoBody("indicadorPossivelImprimirDeclaracao", ConstantesSistema.SIM.intValue());
		}else{
			adicionarValorPrimitivoAoBody("indicadorPossivelImprimirDeclaracao", ConstantesSistema.NAO.intValue());
		}

		}catch(FachadaException e){
			if(e.getMensagem() != null){
				informarStatus(STATUS_TIPO_ALERTA, e.getMensagem());
			}
		}
	}



	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
