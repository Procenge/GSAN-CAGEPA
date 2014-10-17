
package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.*;
import gcom.cadastro.geografico.*;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Exibir Atualizar CEP
 * 
 * @author Ado Rocha
 * @date jul/2013
 */
public class ExibirAtualizarCepAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirAtualizarCep");

		HttpSession sessao = httpServletRequest.getSession();

		AtualizarCepActionForm atualizarCepActionForm = (AtualizarCepActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Recuperando o id para Cep
		Integer idRegistroAtualizacao = null;
		if(httpServletRequest.getAttribute("idRegistroAtualizacao") != null){
			idRegistroAtualizacao = (Integer) httpServletRequest.getAttribute("idRegistroAtualizacao");
		}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
			idRegistroAtualizacao = Util.converterStringParaInteger((String) httpServletRequest.getParameter("idRegistroAtualizacao"));
		}else{
			idRegistroAtualizacao = (Integer) sessao.getAttribute("idRegistroAtualizacao");
		}

		// ---Parte que trata o código quando o usuário tecla enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null){

			// Pesquisar Municipio
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1")){
				this.pesquisarMunicipio(atualizarCepActionForm, httpServletRequest);
				atualizarCepActionForm.setCodigoBairro("");
				atualizarCepActionForm.setBairro("");
				atualizarCepActionForm.setCodigoLogradouro("");
				atualizarCepActionForm.setLogradouro("");
			}

			// Pesquisar Bairro
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("2")){
				this.pesquisarBairro(atualizarCepActionForm, httpServletRequest);
				this.pesquisarMunicipio(atualizarCepActionForm, httpServletRequest);
				atualizarCepActionForm.setCodigoLogradouro("");
				atualizarCepActionForm.setLogradouro("");
			}

			// Pesquisar Logradouro
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("3")){
				this.pesquisarLogradouro(atualizarCepActionForm, httpServletRequest);
				this.pesquisarMunicipio(atualizarCepActionForm, httpServletRequest);
				this.pesquisarBairro(atualizarCepActionForm, httpServletRequest);
			}

			// Pesquisar Localidade
			if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("4")){
				this.pesquisarLocalidade(atualizarCepActionForm, httpServletRequest);
			}

		}else{
			FiltroCep filtro = new FiltroCep();
			filtro.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, Integer.valueOf(idRegistroAtualizacao)));

			Collection colecao = fachada.pesquisar(filtro, Cep.class.getName());
			Cep cep = (Cep) colecao.iterator().next();
			atualizarCepActionForm.setId(cep.getCepId());
			atualizarCepActionForm.setCodigo(cep.getCodigo().toString());
			atualizarCepActionForm.setSigla(cep.getSigla());
			atualizarCepActionForm.setMunicipio(cep.getMunicipio());
			if(cep.getDescricaoTipoLogradouro() != null && !cep.getDescricaoTipoLogradouro().equalsIgnoreCase("")
							&& cep.getLogradouro() != null && !cep.getLogradouro().equalsIgnoreCase("")){
				atualizarCepActionForm.setLogradouro(cep.getDescricaoTipoLogradouro() + " " + cep.getLogradouro());
			}
			atualizarCepActionForm.setBairro(cep.getBairro());
			atualizarCepActionForm.setIndicadorUso(cep.getIndicadorUso());
			atualizarCepActionForm.setIntervaloNumeracao(cep.getDescricaoIntervaloNumeracao());
			atualizarCepActionForm.setFaixaInicial(cep.getNumeroFaixaIncial().toString());
			atualizarCepActionForm.setFaixaFinal(cep.getNumeroFaixaFinal().toString());
			atualizarCepActionForm.setCodigoLadoCep(cep.getCodigoLadoCep());
			atualizarCepActionForm.setCepTipo(cep.getCepTipo().getId());

			// Unidade Federacao
			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao(FiltroUnidadeFederacao.DESCRICAO);
			if(!Util.isVazioOuBranco(cep.getSigla())){
				filtroUnidadeFederacao.adicionarParametro(new ComparacaoTextoCompleto(FiltroUnidadeFederacao.SIGLA, cep.getSigla()));
			}
			Collection colecaoUnidadeFederacao = fachada.pesquisar(filtroUnidadeFederacao, UnidadeFederacao.class.getName());
			if(colecaoUnidadeFederacao == null | colecaoUnidadeFederacao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "unidade federacao");
			}else{
				sessao.setAttribute("colecaoUnidadeFederacao", colecaoUnidadeFederacao);
			}

			// Municipio
			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
			if(cep.getMunicipio() != null){
				filtroMunicipio.adicionarParametro(new ComparacaoTextoCompleto(FiltroMunicipio.NOME, cep.getMunicipio()));
				Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());
				if(colecaoMunicipio == null || colecaoMunicipio.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "municipio");
				}else{
					Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
					atualizarCepActionForm.setCodigoMunicipio(municipio.getId().toString());
				}
			}

			// Bairro
			FiltroBairro filtroBairro = new FiltroBairro();
			if(cep.getBairro() != null){
				filtroBairro.adicionarParametro(new ComparacaoTextoCompleto(FiltroBairro.NOME, cep.getBairro()));
				if(atualizarCepActionForm.getCodigoMunicipio() != null && !atualizarCepActionForm.getCodigoMunicipio().equalsIgnoreCase("")){
					filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, atualizarCepActionForm
									.getCodigoMunicipio()));
				}

				Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());
				if(colecaoBairro != null && !colecaoBairro.isEmpty()){
					Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);
					atualizarCepActionForm.setCodigoBairro("" + bairro.getCodigo());
				}
			}

			// Logradouro
			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
			if(cep.getLogradouro() != null){
				filtroLogradouro.adicionarParametro(new ComparacaoTextoCompleto(FiltroLogradouro.NOME_MUNICIPIO, cep.getMunicipio()));
				filtroLogradouro.adicionarParametro(new ComparacaoTexto(FiltroLogradouro.NOME, cep.getLogradouro()));
				Collection colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());
				if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){
					Iterator iterator = colecaoLogradouro.iterator();
					while(iterator.hasNext()){
						Logradouro logradouro = (Logradouro) iterator.next();
						if(logradouro.getNome().equals(cep.getLogradouro())){
							atualizarCepActionForm.setCodigoLogradouro(logradouro.getId().toString());
							break;
						}
					}
				}
			}

			// Localidade
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			if(cep.getCodigoLocalidade() != null){
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, cep.getCodigoLocalidade()));
				Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
				if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
					Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
					atualizarCepActionForm.setCodigoLocalidade(localidade.getId().toString());
					atualizarCepActionForm.setLocalidade(localidade.getDescricao());
				}
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
	private void pesquisarMunicipio(AtualizarCepActionForm form, HttpServletRequest httpServletRequest){

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
	private void pesquisarBairro(AtualizarCepActionForm form, HttpServletRequest httpServletRequest){

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
	private void pesquisarLogradouro(AtualizarCepActionForm form, HttpServletRequest httpServletRequest){

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

	/**
	 * Pesquisar Localidade
	 * 
	 * @author Ado Rocha
	 * @date nov/2013
	 */
	private void pesquisarLocalidade(AtualizarCepActionForm form, HttpServletRequest httpServletRequest){

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
