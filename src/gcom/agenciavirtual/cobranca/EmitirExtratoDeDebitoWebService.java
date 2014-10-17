
package gcom.agenciavirtual.cobranca;

import gcom.agenciavirtual.AbstractAgenciaVirtualBinarioWebservice;
import gcom.fachada.Fachada;
import gcom.relatorio.cobranca.parcelamento.RelatorioExtratoDebito;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

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
 * URL de acesso: /agenciavirtual/EmitirExtratoDeDebitoWebService.do
 * 
 * @author Yara Souza
 */
public class EmitirExtratoDeDebitoWebService
				extends AbstractAgenciaVirtualBinarioWebservice {

	@Override
	protected void processarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		Integer idImovel = recuperarParametroInteiroObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request);

		Fachada fachada = Fachada.getInstancia();

		try{

			// Filtra usuário
			FiltroUsuario filtroUsuarioResponsavel = new FiltroUsuario();
			filtroUsuarioResponsavel.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, Usuario.USUARIO_AGENCIA_VIRTUAL.getId()));
			// Recupera usuário
			Collection colecaoUsuario = fachada.pesquisar(filtroUsuarioResponsavel, Usuario.class.getName());
			Usuario usuario = (Usuario) Util.retonarObjetoDeColecao(colecaoUsuario);
				
			RelatorioExtratoDebito relatorioExtratoDebito = fachada.gerarRelatorioRelatorioExtratoDebitoParaEmissaoPorImovel(idImovel,
							usuario);

			if(relatorioExtratoDebito != null){

				processarExibicaoRelatorio(relatorioExtratoDebito, TarefaRelatorio.TIPO_PDF, request, response, mapping);
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
