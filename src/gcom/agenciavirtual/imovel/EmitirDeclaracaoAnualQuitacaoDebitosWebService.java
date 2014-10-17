
package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualBinarioWebservice;
import gcom.fachada.Fachada;
import gcom.relatorio.faturamento.RelatorioArquivoDeclaracaoAnualQuitacaoDebitos;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Retorna a Declaração Anual Quitação Debitos
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idImovel [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/emitirDeclaracaoAnualQuitacaoDebitosWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class EmitirDeclaracaoAnualQuitacaoDebitosWebService
				extends AbstractAgenciaVirtualBinarioWebservice {

	@Override
	protected void processarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		Integer imovelId = recuperarParametroInteiroObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request);

		Fachada fachada = Fachada.getInstancia();

		try{
			RelatorioArquivoDeclaracaoAnualQuitacaoDebitos relatorioArquivoDeclaracaoAnualQuitacaoDebitos = fachada
							.emitirDeclaracaoAnualQuitacaoDebitos(null, imovelId, Usuario.USUARIO_AGENCIA_VIRTUAL, null);
			if(relatorioArquivoDeclaracaoAnualQuitacaoDebitos != null){

			relatorioArquivoDeclaracaoAnualQuitacaoDebitos.addParametro(RelatorioArquivoDeclaracaoAnualQuitacaoDebitos.P_IC_BATCH,
							ConstantesSistema.INATIVO);
		
			processarExibicaoRelatorio(relatorioArquivoDeclaracaoAnualQuitacaoDebitos, TarefaRelatorio.TIPO_PDF, request, response, mapping);
			}
		}catch(Exception e){
			throw e;
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
