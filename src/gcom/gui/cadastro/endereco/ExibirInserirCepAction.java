package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.FiltroLogradouroTipo;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroTipo;
import gcom.cadastro.geografico.*;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inserir CEP
 * 
 * @author Ado Rocha
 * @date jul/2013
 */
public class ExibirInserirCepAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirCep");

		// Form
		InserirCepActionForm inserirCepActionForm = (InserirCepActionForm) actionForm;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Fachada
		Fachada fachada = Fachada.getInstancia();

		// ---Parte que trata o código quando o usuário tecla enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null){

			// Pesquisar Municipio
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1")){
				this.pesquisarMunicipio(inserirCepActionForm, httpServletRequest);
				inserirCepActionForm.setCodigoBairro("");
				inserirCepActionForm.setBairro("");
				inserirCepActionForm.setCodigoLogradouro("");
				inserirCepActionForm.setLogradouro("");
			}

			// Pesquisar Bairro
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("2")){
				this.pesquisarBairro(inserirCepActionForm, httpServletRequest);
				this.pesquisarMunicipio(inserirCepActionForm, httpServletRequest);
				inserirCepActionForm.setCodigoLogradouro("");
				inserirCepActionForm.setLogradouro("");
			}

			// Pesquisar Logradouro
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("3")){
				this.pesquisarLogradouro(inserirCepActionForm, httpServletRequest);
				this.pesquisarMunicipio(inserirCepActionForm, httpServletRequest);
				this.pesquisarBairro(inserirCepActionForm, httpServletRequest);
			}

			// Pesquisar Localidade
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("4")){
				this.pesquisarLocalidade(inserirCepActionForm, httpServletRequest);
			}

		}else{
			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao(FiltroUnidadeFederacao.DESCRICAO);
			Collection colecaoUnidadeFederacao = fachada.pesquisar(filtroUnidadeFederacao, UnidadeFederacao.class.getName());

			if(colecaoUnidadeFederacao == null || colecaoUnidadeFederacao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "unidade federativa");
			}else{
				sessao.setAttribute("colecaoUnidadeFederacao", colecaoUnidadeFederacao);
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
	private void pesquisarMunicipio(InserirCepActionForm form, HttpServletRequest httpServletRequest){

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
	private void pesquisarBairro(InserirCepActionForm form, HttpServletRequest httpServletRequest){

		// [FS0013] - Verificar informação do munícipio
		String codigoMunicipio = form.getCodigoMunicipio();
		if(codigoMunicipio == null || codigoMunicipio.equals("")){
			throw new ActionServletException("atencao.filtrar_informar_municipio");
		}

		FiltroBairro filtroBairro = new FiltroBairro();

		if(form.getCodigoBairro() != null && !form.getCodigoBairro().equalsIgnoreCase("")){
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, new Integer(form.getCodigoBairro())));
		}

		if(codigoMunicipio != null && !codigoMunicipio.equalsIgnoreCase("")){
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, new Integer(codigoMunicipio)));
		}

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
	private void pesquisarLogradouro(InserirCepActionForm form, HttpServletRequest httpServletRequest){

		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

		filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, new Integer(form.getCodigoLogradouro())));
		filtroLogradouro.adicionarParametro(new ComparacaoTextoCompleto(FiltroLogradouro.NOME_MUNICIPIO, form.getMunicipio()));

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

	/**
	 * Pesquisar Localidade
	 * 
	 * @author Ado Rocha
	 * @date nov/2013
	 */
	private void pesquisarLocalidade(InserirCepActionForm form, HttpServletRequest httpServletRequest){

		// [FS0013] - Verificar informação da Localidade
		String codigoLocalidade = form.getCodigoLocalidade();
		if(codigoLocalidade == null || codigoLocalidade.equals("")){
			throw new ActionServletException("atencao.localidade_nao_informada");
		}

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(form.getCodigoLocalidade())));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setCodigoLocalidade("" + localidade.getId());
			form.setLocalidade(localidade.getDescricao());
			httpServletRequest.setAttribute("localidadeEncontrado", "true");

		}else{
			form.setCodigoLocalidade(null);
			httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
			httpServletRequest.setAttribute("localidadeEncontrado", "exception");
			form.setLocalidade("Localidade inexistente");
		}
	}

}
