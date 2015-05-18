
package gcom.gui.relatorio.faturamento;

import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que faz a exibição da tela para o usuário setar os parâmetros
 * necessário para a geração do relatório
 * [UC0635] Gerar Relatórios de Contas em Revisão
 * 
 * @author Rafael Corrêa
 * @since 20/09/2007
 */
public class ExibirGerarRelatorioContasEmRevisaoAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioContasEmRevisao");

		GerarRelatorioContasEmRevisaoActionForm gerarRelatorioContasEmRevisaoActionForm = (GerarRelatorioContasEmRevisaoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Carrega as coleções que serão exibidas na tela
		// Gerência Regional
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.NOME);

		Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);

		// Unidade de Negócio
		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio(FiltroUnidadeNegocio.NOME);

		Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);

		// Motivo de Revisão
		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao(FiltroContaMotivoRevisao.DESCRICAO);

		Collection colecaoContaMotivoRevisao = fachada.pesquisar(filtroContaMotivoRevisao, ContaMotivoRevisao.class.getName());

		sessao.setAttribute("colecaoContaMotivoRevisao", colecaoContaMotivoRevisao);

		// Perfil do Imóvel
		FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil(FiltroImovelPerfil.DESCRICAO);

		Collection colecaoImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

		sessao.setAttribute("colecaoImovelPerfil", colecaoImovelPerfil);

		// Valida a parte de quando o usuário pesquisa pelo enter
		// Elo
		String idElo = gerarRelatorioContasEmRevisaoActionForm.getIdElo();

		if(idElo != null && !idElo.equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idElo));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, idElo));

			Collection colecaoElos = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoElos != null && !colecaoElos.isEmpty()){
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoElos);

				gerarRelatorioContasEmRevisaoActionForm.setIdElo(localidade.getId().toString());
				gerarRelatorioContasEmRevisaoActionForm.setNomeElo(localidade.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "idLocalidadeInicial");

			}else{
				gerarRelatorioContasEmRevisaoActionForm.setIdElo("");
				gerarRelatorioContasEmRevisaoActionForm.setNomeElo("Elo Inexistente");

				httpServletRequest.setAttribute("idEloNaoEncontrado", "true");

				httpServletRequest.setAttribute("nomeCampo", "idElo");
			}
		}

		// Localidade Inicial
		String idLocalidadeInicial = gerarRelatorioContasEmRevisaoActionForm.getIdLocalidadeInicial();

		if(idLocalidadeInicial != null && !idLocalidadeInicial.equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeInicial));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);

				gerarRelatorioContasEmRevisaoActionForm.setIdLocalidadeInicial(localidade.getId().toString());
				gerarRelatorioContasEmRevisaoActionForm.setNomeLocalidadeInicial(localidade.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "idLocalidadeFinal");

			}else{
				gerarRelatorioContasEmRevisaoActionForm.setIdLocalidadeInicial("");
				gerarRelatorioContasEmRevisaoActionForm.setNomeLocalidadeInicial("Localidade Inexistente");

				httpServletRequest.setAttribute("idLocalidadeInicialNaoEncontrada", "true");

				httpServletRequest.setAttribute("nomeCampo", "idLocalidadeInicial");
			}
		}

		// Localidade Final
		String idLocalidadeFinal = gerarRelatorioContasEmRevisaoActionForm.getIdLocalidadeFinal();

		if(idLocalidadeFinal != null && !idLocalidadeFinal.equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeFinal));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);

				gerarRelatorioContasEmRevisaoActionForm.setIdLocalidadeFinal(localidade.getId().toString());
				gerarRelatorioContasEmRevisaoActionForm.setNomeLocalidadeFinal(localidade.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "idMotivoRevisao");

			}else{
				gerarRelatorioContasEmRevisaoActionForm.setIdLocalidadeFinal("");
				gerarRelatorioContasEmRevisaoActionForm.setNomeLocalidadeFinal("Localidade Inexistente");

				httpServletRequest.setAttribute("idLocalidadeFinalNaoEncontrada", "true");

				httpServletRequest.setAttribute("nomeCampo", "idLocalidadeFinal");
			}
		}

		return retorno;

	}

}
