/**
 * 
 */

package gcom.gui.cadastro.cliente;

import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.arrecadacao.banco.FiltroBanco;
import gcom.cadastro.cliente.ClienteResponsavel;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroClienteResponsavel;
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
public class ExibirAtualizarClienteResponsavelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		DynaValidatorForm clienteActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession session = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("atualizarClienteResponsavel");

		Integer codigoCliente = Integer.valueOf((String) clienteActionForm.get("codigoCliente"));

		FiltroClienteResponsavel filtroClienteResponsavel = new FiltroClienteResponsavel();
		filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.CLIENTE, codigoCliente));
		filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colClienteResponsavel = fachada.pesquisarTabelaAuxiliar(filtroClienteResponsavel, ClienteResponsavel.class.getName());

		ClienteResponsavel clienteResponsavel = null;
		if(colClienteResponsavel != null && !colClienteResponsavel.isEmpty()){
			clienteResponsavel = (ClienteResponsavel) colClienteResponsavel.iterator().next();
		}

		FiltroBanco filtroBanco = new FiltroBanco();
		filtroBanco.setCampoOrderBy(FiltroBanco.NOME_BANCO);

		Collection colecaoBanco = fachada.pesquisarTabelaAuxiliar(filtroBanco, Banco.class.getName());

		Banco banco = (Banco) colecaoBanco.iterator().next();

		Integer idBanco = banco.getId();

		if(clienteActionForm.get("banco") != null && !((Integer) clienteActionForm.get("banco")).equals(-1)){
			idBanco = (Integer) clienteActionForm.get("banco");
		}

		session.setAttribute("checkDadosAdcionais", "0");

		if(httpServletRequest.getParameter("consultaBanco") != null){
			if(httpServletRequest.getParameter("indDadosAdicionais") != null
							&& httpServletRequest.getParameter("indDadosAdicionais").equals("1")){
				session.setAttribute("checkDadosAdcionais", "1");
			}
		}
		// session.setAttribute("idRegistroAtualizacao", "");
		// session.setAttribute("indMulta", new Short("1"));
		// session.setAttribute("indJuros", new Short("1"));
		// session.setAttribute("indCorrecao", new Short("1"));

		ClienteTipo clienteTipo = null;

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
			if(clienteTipo.getEsferaPoder().getId().equals(ClienteTipo.INDICADOR_ESFERA_PODER_FEDERAL.intValue())){
				session.setAttribute("indImpostoFederal", new Short("1"));
			}else{
				session.setAttribute("indImpostoFederal", new Short("2"));
			}
		}else{
			session.setAttribute("esferaPoder", null);
		}

		if(clienteResponsavel != null){
			session.setAttribute("checkDadosAdcionais", "1");
			session.setAttribute("idRegistroAtualizacao", clienteResponsavel.getId().toString());
			session.setAttribute("indMulta", clienteResponsavel.getIndMulta());
			session.setAttribute("indJuros", clienteResponsavel.getIndJuros());
			session.setAttribute("indCorrecao", clienteResponsavel.getIndCorrecao());
			session.setAttribute("indImpostoFederal", clienteResponsavel.getIndImpostoFederal());

			if(clienteActionForm.get("indDadosAdicionais") == null || clienteActionForm.get("indDadosAdicionais").equals("")){
				clienteActionForm.set("indDadosAdicionais", true);
			}
			if(clienteActionForm.get("indMulta") == null || clienteActionForm.get("indMulta").equals("")){
				clienteActionForm.set("indMulta", clienteResponsavel.getIndMulta().equals(ConstantesSistema.SIM) ? true : false);
			}
			if(clienteActionForm.get("indJuros") == null || clienteActionForm.get("indJuros").equals("")){
				clienteActionForm.set("indJuros", clienteResponsavel.getIndJuros().equals(ConstantesSistema.SIM) ? true : false);
			}
			if(clienteActionForm.get("indCorrecao") == null || clienteActionForm.get("indCorrecao").equals("")){
				clienteActionForm.set("indCorrecao", clienteResponsavel.getIndCorrecao().equals(ConstantesSistema.SIM) ? true : false);
			}

			if(httpServletRequest.getParameter("consultaBanco") == null){
				if(clienteResponsavel.getAgencia() != null){

					FiltroAgencia filtroAgencia = new FiltroAgencia();
					filtroAgencia.adicionarParametro(new ParametroSimples(FiltroAgencia.ID, clienteResponsavel.getAgencia().getId()));

					Collection<Agencia> colecaoAgencia = fachada.pesquisarTabelaAuxiliar(filtroAgencia, Agencia.class.getName());
					Collections.sort((List<Agencia>) colecaoAgencia);

					Agencia ag = null;

					if(colecaoAgencia != null && !colecaoAgencia.isEmpty()){
						ag = (Agencia) colecaoAgencia.iterator().next();
					}

					if(ag != null && ag.getBanco() != null){
						idBanco = ag.getBanco().getId();
					}

				}
				if(clienteResponsavel.getAgencia() != null){
					clienteActionForm.set("banco", idBanco);
					clienteActionForm.set("agencia", clienteResponsavel.getAgencia().getId());
				}
			}

			clienteActionForm.set("contaBancaria", clienteResponsavel.getContaBancaria());
		}else{
			session.setAttribute("idRegistroAtualizacao", "");
			session.setAttribute("indMulta", new Short("1"));
			session.setAttribute("indJuros", new Short("1"));
			session.setAttribute("indCorrecao", new Short("1"));
		}

		FiltroAgencia filtroAgencia = new FiltroAgencia();
		filtroAgencia.adicionarParametro(new ParametroSimples(FiltroAgencia.BANCO_ID, idBanco));

		Collection<Agencia> colecaoAgencia = fachada.pesquisarTabelaAuxiliar(filtroAgencia, Agencia.class.getName());
		Collections.sort((List<Agencia>) colecaoAgencia);

		session.setAttribute("colecaoAgencia", colecaoAgencia);
		session.setAttribute("colecaoBanco", colecaoBanco);

		return retorno;
	}

}
