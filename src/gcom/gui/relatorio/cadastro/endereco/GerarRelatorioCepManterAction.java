package gcom.gui.relatorio.cadastro.endereco;

import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.FiltroCep;
import gcom.gui.cadastro.endereco.FiltrarCepActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.endereco.RelatorioManterCep;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.SistemaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioCepManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarCepActionForm filtrarCepActionForm = new FiltrarCepActionForm();
		filtrarCepActionForm = (FiltrarCepActionForm) sessao.getAttribute("filtrarCepActionForm");

		FiltroCep filtroCep = (FiltroCep) sessao.getAttribute("filtroCep");

		// parametros do relatorio
		Cep cepParametros = new Cep();
		if(filtrarCepActionForm.getCodigo() != null && !filtrarCepActionForm.getCodigo().equals("")){
			cepParametros.setCodigo(Integer.parseInt(filtrarCepActionForm.getCodigo()));
		}
		cepParametros.setMunicipio(filtrarCepActionForm.getMunicipio());
		cepParametros.setBairro(filtrarCepActionForm.getBairro());
		cepParametros.setLogradouro(filtrarCepActionForm.getLogradouro());
		if(filtrarCepActionForm.getIndicadorUso() != null && !filtrarCepActionForm.getIndicadorUso().equals("")){
			cepParametros.setIndicadorUso(new Short(filtrarCepActionForm.getIndicadorUso()));
		}

		Integer tipoRelatorio = Integer.valueOf(httpServletRequest.getParameter("tipoRelatorio"));

		RelatorioManterCep relatorioManterCep = new RelatorioManterCep(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		relatorioManterCep.addParametro("filtroCep", filtroCep);
		relatorioManterCep.addParametro("cepParametros", cepParametros);
		relatorioManterCep.addParametro("tipoFormatoRelatorio", tipoRelatorio);

		try{
			retorno = processarExibicaoRelatorio(relatorioManterCep, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(SistemaException ex){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
