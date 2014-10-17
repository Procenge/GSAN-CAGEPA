/**
 * 
 */

package gcom.gui.cadastro.cliente;

import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.arrecadacao.banco.FiltroBanco;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ExibirInserirClienteResponsavelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		DynaValidatorForm clienteActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession session = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("inserirClienteResponsavel");

		FiltroBanco filtroBanco = new FiltroBanco();
		filtroBanco.setCampoOrderBy(FiltroBanco.NOME_BANCO);

		Collection colecaoBanco = fachada.pesquisarTabelaAuxiliar(filtroBanco, Banco.class.getName());

		Banco banco = (Banco) colecaoBanco.iterator().next();

		Integer idBanco = banco.getId();

		if(clienteActionForm.get("banco") != null && !((Integer) clienteActionForm.get("banco")).equals(-1)){
			idBanco = (Integer) clienteActionForm.get("banco");
		}

		FiltroAgencia filtroAgencia = new FiltroAgencia();
		filtroAgencia.adicionarParametro(new ParametroSimples(FiltroAgencia.BANCO_ID, idBanco));

		Collection<Agencia> colecaoAgencia = fachada.pesquisarTabelaAuxiliar(filtroAgencia, Agencia.class.getName());
		Collections.sort((List<Agencia>) colecaoAgencia);

		session.setAttribute("colecaoAgencia", colecaoAgencia);
		session.setAttribute("colecaoBanco", colecaoBanco);

		ClienteTipo clienteTipo = null;

		if(httpServletRequest.getParameter("indDadosAdicionais") != null
						&& httpServletRequest.getParameter("indDadosAdicionais").equals("1")){
			session.setAttribute("checkDadosAdcionais", "1");
		}else{
			session.setAttribute("checkDadosAdcionais", "0");
		}

		if(clienteActionForm.get("tipoPessoa") != null && !clienteActionForm.get("tipoPessoa").equals("-1")){
			// Pesquisa os Tipos de Clientes
			Short idClienteTipo = (Short) clienteActionForm.get("tipoPessoa");
			FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
			filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, idClienteTipo));

			Collection colClienteTipo = fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName());

			clienteTipo = (ClienteTipo) colClienteTipo.iterator().next();
		}

		if(clienteTipo != null){
			session.setAttribute("esferaPoder", clienteTipo.getEsferaPoder().getId());
		}else{
			session.setAttribute("esferaPoder", null);
		}

		if(clienteActionForm.get("indMulta") == null || clienteActionForm.get("indMulta").equals("")){
			clienteActionForm.set("indMulta", true);
		}
		if(clienteActionForm.get("indJuros") == null || clienteActionForm.get("indJuros").equals("")){
			clienteActionForm.set("indJuros", true);
		}
		if(clienteActionForm.get("indCorrecao") == null || clienteActionForm.get("indCorrecao").equals("")){
			clienteActionForm.set("indCorrecao", true);
		}

		return retorno;
	}

}
