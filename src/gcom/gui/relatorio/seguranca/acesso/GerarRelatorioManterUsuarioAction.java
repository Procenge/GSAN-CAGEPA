
package gcom.gui.relatorio.seguranca.acesso;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.localidade.*;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.seguranca.acesso.usuario.AtualizarUsuarioDadosGeraisActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.seguranca.acesso.RelatorioManterUsuario;
import gcom.seguranca.acesso.usuario.*;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class GerarRelatorioManterUsuarioAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarUsuarioDadosGeraisActionForm atualizarUsuarioDadosGeraisActionForm = (AtualizarUsuarioDadosGeraisActionForm) sessao
						.getAttribute("AtualizarUsuarioDadosGeraisActionForm");

		Fachada fachada = Fachada.getInstancia();

		FiltroUsuarioGrupo filtroUsuario = (FiltroUsuarioGrupo) sessao.getAttribute("filtroUsuario");

		// Inicio da parte que vai mandar os parametros para o relatório

		Usuario usuarioParametros = new Usuario();

		String idUsuarioTipo = (String) atualizarUsuarioDadosGeraisActionForm.getUsuarioTipo();

		UsuarioTipo usuarioTipo = null;

		if(idUsuarioTipo != null && !idUsuarioTipo.equals("")){
			FiltroUsuarioTipo filtroUsuarioTipo = new FiltroUsuarioTipo();

			filtroUsuarioTipo.adicionarParametro(new ParametroSimples(FiltroUsuarioTipo.ID, idUsuarioTipo));
			filtroUsuarioTipo.adicionarParametro(new ParametroSimples(FiltroUsuarioTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection usuariosTipo = fachada.pesquisar(filtroUsuarioTipo, UsuarioTipo.class.getName());

			if(usuariosTipo != null && !usuariosTipo.isEmpty()){

				Iterator usuariotipoIterator = usuariosTipo.iterator();

				usuarioTipo = (UsuarioTipo) usuariotipoIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Usuario Tipo");
			}

		}else{
			usuarioTipo = new UsuarioTipo();
		}

		String idEmpresa = (String) atualizarUsuarioDadosGeraisActionForm.getEmpresa();

		Empresa empresa = null;

		if(idEmpresa != null && !idEmpresa.equals("")){
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, idEmpresa));

			Collection empresas = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

			if(empresas != null && !empresas.isEmpty()){

				Iterator empresaIterator = empresas.iterator();

				empresa = (Empresa) empresaIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Empresa");
			}

		}else{
			empresa = new Empresa();
		}

		String idFuncionario = (String) atualizarUsuarioDadosGeraisActionForm.getIdFuncionario();

		Funcionario funcionario = null;

		if(idFuncionario != null && !idFuncionario.equals("")){
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, idFuncionario));

			Collection funcionarios = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(funcionarios != null && !funcionarios.isEmpty()){

				Iterator funcionarioIterator = funcionarios.iterator();

				funcionario = (Funcionario) funcionarioIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Funcionário");
			}

		}else{
			funcionario = new Funcionario();
		}

		String nomeUsuario = "";
		if(atualizarUsuarioDadosGeraisActionForm.getNome() != null){
			nomeUsuario = (String) atualizarUsuarioDadosGeraisActionForm.getNome();
		}

		String idUnidadeOrganizacional = (String) atualizarUsuarioDadosGeraisActionForm.getIdLotacao();

		UnidadeOrganizacional unidadeOrganizacional = null;

		if(idUnidadeOrganizacional != null && !idUnidadeOrganizacional.equals("")){
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacional));

			Collection unidadesOrganizacionais = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(unidadesOrganizacionais != null && !unidadesOrganizacionais.isEmpty()){

				Iterator unidadeOrganizacionalIterator = unidadesOrganizacionais.iterator();

				unidadeOrganizacional = (UnidadeOrganizacional) unidadeOrganizacionalIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Organizacional");
			}

		}else{
			unidadeOrganizacional = new UnidadeOrganizacional();
		}

		// String descricaoSituacao = (String) atualizarUsuarioDadosGeraisActionForm.getSituacao();
		//
		// UsuarioSituacao situacao = null;
		//
		// if(descricaoSituacao != null && !descricaoSituacao.equals("")){
		// FiltroUsuarioSituacao filtroUsuarioSituacao = new FiltroUsuarioSituacao();
		//
		// filtroUsuarioSituacao.adicionarParametro(new
		// ParametroSimples(FiltroUnidadeOrganizacional.DESCRICAO, descricaoSituacao));
		//
		// Collection situacoes = fachada.pesquisar(filtroUsuarioSituacao,
		// UsuarioSituacao.class.getName());
		//
		// if(situacoes != null && !situacoes.isEmpty()){
		//
		// Iterator usuarioSitucaoIterator = situacoes.iterator();
		//
		// situacao = (UsuarioSituacao) usuarioSitucaoIterator.next();
		//
		// }else{
		// throw new ActionServletException("atencao.pesquisa_inexistente", null,
		// "Usuário Situação");
		// }
		//
		// }else{
		// situacao = new UsuarioSituacao();
		// }

		String idAbrangencia = (String) atualizarUsuarioDadosGeraisActionForm.getAbrangencia();

		UsuarioAbrangencia usuarioAbrangencia = null;

		if(idAbrangencia != null && !idAbrangencia.equals("")){
			FiltroUsuarioAbrangencia filtroUsuarioAbrangencia = new FiltroUsuarioAbrangencia();

			filtroUsuarioAbrangencia.adicionarParametro(new ParametroSimples(FiltroUsuarioAbrangencia.ID, idAbrangencia));

			Collection usuarioAbrangencias = fachada.pesquisar(filtroUsuarioAbrangencia, UsuarioAbrangencia.class.getName());

			if(usuarioAbrangencias != null && !usuarioAbrangencias.isEmpty()){

				Iterator usuarioAbrangenciaIterator = usuarioAbrangencias.iterator();

				usuarioAbrangencia = (UsuarioAbrangencia) usuarioAbrangenciaIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Usuário Abrangência");
			}

		}else{
			usuarioAbrangencia = new UsuarioAbrangencia();
		}

		String login = (String) atualizarUsuarioDadosGeraisActionForm.getLogin();

		String idGerenciaRegional = (String) atualizarUsuarioDadosGeraisActionForm.getGerenciaRegional();

		GerenciaRegional gerenciaRegional = null;

		if(idGerenciaRegional != null && !idGerenciaRegional.equals("")){
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, idGerenciaRegional));

			Collection gerenciasRegionais = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(gerenciasRegionais != null && !gerenciasRegionais.isEmpty()){

				Iterator gerenciaRegionalIterator = gerenciasRegionais.iterator();

				gerenciaRegional = (GerenciaRegional) gerenciaRegionalIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Usuário Abrangência");
			}

		}else{
			gerenciaRegional = new GerenciaRegional();
		}

		String idUnidadeNegocio = (String) atualizarUsuarioDadosGeraisActionForm.getUnidadeNegocio();

		UnidadeNegocio unidadeNegocio = null;

		if(idUnidadeNegocio != null && !idUnidadeNegocio.equals("")){
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, idGerenciaRegional));

			Collection unidadesNegocios = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			if(unidadesNegocios != null && !unidadesNegocios.isEmpty()){

				Iterator unidadeNegocioIterator = unidadesNegocios.iterator();

				unidadeNegocio = (UnidadeNegocio) unidadeNegocioIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade de Negócio");
			}

		}else{
			unidadeNegocio = new UnidadeNegocio();
		}

		String idElo = (String) atualizarUsuarioDadosGeraisActionForm.getIdElo();

		Localidade elo = null;

		if(idElo != null && !idElo.equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idElo));

			Collection elos = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(elos != null && !elos.isEmpty()){

				Iterator eloIterator = elos.iterator();

				elo = (Localidade) eloIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Elo Polo");
			}

		}else{
			elo = new Localidade();
		}

		String idLocalidade = (String) atualizarUsuarioDadosGeraisActionForm.getIdLocalidade();

		Localidade localidade = null;

		if(idLocalidade != null && !idLocalidade.equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idElo));

			Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(localidades != null && !localidades.isEmpty()){

				Iterator localidadeIterator = localidades.iterator();

				localidade = (Localidade) localidadeIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}

		}else{
			localidade = new Localidade();
		}

		// seta os parametros que serão mostrados no relatório
		usuarioParametros.setUsuarioTipo(usuarioTipo);
		usuarioParametros.setEmpresa(empresa);
		usuarioParametros.setFuncionario(funcionario);
		usuarioParametros.setNomeUsuario(nomeUsuario);
		usuarioParametros.setUnidadeOrganizacional(unidadeOrganizacional);
		// usuarioParametros.setUsuarioSituacao("");
		usuarioParametros.setLogin(login);
		usuarioParametros.setUsuarioAbrangencia(usuarioAbrangencia);
		usuarioParametros.setGerenciaRegional(gerenciaRegional);
		usuarioParametros.setUnidadeNegocio(unidadeNegocio);
		usuarioParametros.setLocalidadeElo(elo);
		usuarioParametros.setLocalidade(localidade);


		// Fim da parte que vai mandar os parametros para o relatório
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioManterUsuario relatorio = new RelatorioManterUsuario(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorio.addParametro("filtroUsuario", filtroUsuario);
		relatorio.addParametro("usuarioParametros", usuarioParametros);
		relatorio.addParametro("colecaoUsuarios", sessao.getAttribute("collectionUsuariosGrupos"));
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}
