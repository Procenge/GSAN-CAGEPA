
package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.FiltroLogradouroTipo;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroTipo;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ado Rocha
 * @date jul/2013
 */
public class ExibirFiltrarCepAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirFiltrarCep");
		
		// Form
		FiltrarCepActionForm filtrarCepActionForm = (FiltrarCepActionForm) actionForm;

		filtrarCepActionForm.setIndicadorUso("3");
		
		// ---Parte que trata o código quando o usuário tecla enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null){

			// Pesquisar Municipio
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1")){
				this.pesquisarMunicipio(filtrarCepActionForm, httpServletRequest);
				filtrarCepActionForm.setCodigoBairro("");
				filtrarCepActionForm.setBairro("");
				filtrarCepActionForm.setCodigoLogradouro("");
				filtrarCepActionForm.setLogradouro("");
			}

			// Pesquisar Bairro
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("2")){
				this.pesquisarBairro(filtrarCepActionForm, httpServletRequest);
				this.pesquisarMunicipio(filtrarCepActionForm, httpServletRequest);
				filtrarCepActionForm.setCodigoLogradouro("");
				filtrarCepActionForm.setLogradouro("");
			}

			// Pesquisar Logradouro
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("3")){
				this.pesquisarLogradouro(filtrarCepActionForm, httpServletRequest);
				this.pesquisarMunicipio(filtrarCepActionForm, httpServletRequest);
				this.pesquisarBairro(filtrarCepActionForm, httpServletRequest);
			}

		}
		return retorno;
	}

	/**
	 * Pesquisar Município
	 * 
	 * @author Ado Rocha
	 * @date jul/2013
	 */
	private void pesquisarMunicipio(FiltrarCepActionForm form, HttpServletRequest httpServletRequest){

		FiltroMunicipio filtroMunicipioEnter = new FiltroMunicipio();
		if(!Util.isVazioOuBranco(form.getCodigoMunicipio())){
			filtroMunicipioEnter.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, form.getCodigoMunicipio()));
			filtroMunicipioEnter.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection municipioEncontrado = Fachada.getInstancia().pesquisar(filtroMunicipioEnter, Municipio.class.getName());

			if(municipioEncontrado != null && !municipioEncontrado.isEmpty()){
				// O municipio foi encontrado
				form.setCodigoMunicipio(((Municipio) ((List) municipioEncontrado).get(0)).getId().toString());
				form.setMunicipio(((Municipio) ((List) municipioEncontrado).get(0)).getNome());

				httpServletRequest.setAttribute("municipioEncontrado", "true");

			}else{
				httpServletRequest.setAttribute("nomeCampo", "idMunicipio");
				httpServletRequest.setAttribute("municipioEncontrado", "exception");
				form.setMunicipio("Município inexistente");

			}
		}
	}

	/**
	 * Pesquisar Bairro
	 * 
	 * @author Ado Rocha
	 * @date jul/2013
	 */
	private void pesquisarBairro(FiltrarCepActionForm form, HttpServletRequest httpServletRequest){

		// [FS0013] - Verificar informação do munícipio
		String codigoMunicipio = form.getCodigoMunicipio();
		if(codigoMunicipio == null || codigoMunicipio.equals("")){
			throw new ActionServletException("atencao.filtrar_informar_municipio");
		}

		FiltroBairro filtroBairro = new FiltroBairro();

		if(form.getCodigoBairro() != null && !form.getCodigoBairro().equalsIgnoreCase("")){
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, new Integer(form.getCodigoBairro())));
		}

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, new Integer(codigoMunicipio)));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoBairro = Fachada.getInstancia().pesquisar(filtroBairro, Bairro.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoBairro != null && !colecaoBairro.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

			form.setCodigoBairro("" + bairro.getCodigo());
			form.setIdBairro("" + bairro.getId());
			form.setBairro(bairro.getNome());
			httpServletRequest.setAttribute("bairroEncontrado", "true");

		}else{
			form.setIdBairro(null);
			httpServletRequest.setAttribute("nomeCampo", "idBairro");
			httpServletRequest.setAttribute("bairroEncontrado", "exception");
			form.setBairro("Bairro inexistente");
		}
		}


	/**
	 * Pesquisar Logradouro
	 * 
	 * @author Ado Rocha
	 * @date jul/2013
	 */
	private void pesquisarLogradouro(FiltrarCepActionForm form, HttpServletRequest httpServletRequest){

		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

		filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, new Integer(form.getCodigoLogradouro())));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoLogradouro = Fachada.getInstancia().pesquisar(filtroLogradouro, Logradouro.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

			// Obtém o tipo do logradouro
			FiltroLogradouroTipo filtroLogradouroTipo = new FiltroLogradouroTipo();

			filtroLogradouroTipo.adicionarParametro(new ParametroSimples(FiltroLogradouroTipo.ID, new Integer(logradouro
							.getLogradouroTipo().getId())));

			// Pesquisa de acordo com os parâmetros informados no filtro
			Collection colecaoLogradouroTipo = Fachada.getInstancia().pesquisar(filtroLogradouroTipo, LogradouroTipo.class.getName());

			LogradouroTipo logradouroTipo = (LogradouroTipo) Util.retonarObjetoDeColecao(colecaoLogradouroTipo);

			form.setCodigoLogradouro(logradouro.getId().toString());
			form.setLogradouro(logradouroTipo.getDescricao() + " " + logradouro.getNome());
			httpServletRequest.setAttribute("logradouroEncontrado", "true");

		}else{
			form.setLogradouro("Logradouro inexistente");
			httpServletRequest.setAttribute("nomeCampo", "idMunicipio");
			httpServletRequest.setAttribute("logradouroEncontrado", "exception");

		}
		}

	}
