
package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.FiltroCep;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ado Rocha
 * @date jul/2013
 */

public class FiltrarCepAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterCep");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarCepActionForm form = (FiltrarCepActionForm) actionForm;

		String codigo = (String) form.getCodigo();
		String municipio = (String) form.getMunicipio();
		String bairro = (String) form.getBairro();
		String logradouro = (String) form.getLogradouro();
		String indicadorUso = (String) form.getIndicadorUso();
		String atualizar = httpServletRequest.getParameter("indicadorAtualizar");

		Fachada fachada = Fachada.getInstancia();

		boolean peloMenosUmParametroInformado = false;

		if(form.getCodigoLogradouro() != null && !form.getCodigoLogradouro().equalsIgnoreCase("")){
			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, new Integer(form.getCodigoLogradouro())));

			// Pesquisa de acordo com os parâmetros informados no filtro
			Collection colecaoLogradouro = Fachada.getInstancia().pesquisar(filtroLogradouro, Logradouro.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a coleção
			if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){

				// Obtém o objeto da coleção pesquisada
				Logradouro objetoLogradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);
				logradouro = objetoLogradouro.getNome();
			}
		}
		FiltroCep filtroCep = new FiltroCep();

		if(codigo != null && !codigo.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO, new Integer(codigo)));
		}

		if(municipio != null && !municipio.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroCep.adicionarParametro(new ComparacaoTexto(FiltroCep.MUNICIPIO, municipio.toUpperCase()));
			
		}

		if(bairro != null && !bairro.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;
			filtroCep.adicionarParametro(new ComparacaoTexto(FiltroCep.BAIRRO, bairro.toUpperCase()));
			
		}

		if(logradouro != null && !logradouro.trim().equalsIgnoreCase("")){
			
			peloMenosUmParametroInformado = true;
			filtroCep.adicionarParametro(new ComparacaoTexto(FiltroCep.LOGRADOURO, logradouro.toUpperCase()));
			
		}

		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("") && !indicadorUso.equals("3")){
				peloMenosUmParametroInformado = true;
				filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.INDICADORUSO, indicadorUso));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		Collection colecaoCep = (Collection) fachada.pesquisar(filtroCep, Cep.class.getName());

		if(colecaoCep != null && !colecaoCep.isEmpty()){
			if(atualizar != null && colecaoCep.size() == 1){
				Cep orgao = (Cep) colecaoCep.iterator().next();
				httpServletRequest.setAttribute("idRegistroAtualizacao", orgao.getCepId());
				sessao.setAttribute("idRegistroAtualizacao", orgao.getCepId());

				retorno = actionMapping.findForward("exibirAtualizarCep");

			}else{
				retorno = actionMapping.findForward("retornarFiltroCep");
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		httpServletRequest.setAttribute("filtroCep", filtroCep);
		sessao.setAttribute("filtroCep", filtroCep);
		
		sessao.setAttribute("filtrarCepActionForm", form);

		return retorno;
	}
}
