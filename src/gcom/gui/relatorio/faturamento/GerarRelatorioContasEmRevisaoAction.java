
package gcom.gui.relatorio.faturamento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.RelatorioContasEmRevisao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de contas em revis�o
 * 
 * @author Rafael Corr�a
 * @created 20/09/2007
 */
public class GerarRelatorioContasEmRevisaoAction
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

		GerarRelatorioContasEmRevisaoActionForm gerarRelatorioContasEmRevisaoActionForm = (GerarRelatorioContasEmRevisaoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Valida os par�metro passados como consulta
		boolean peloMenosUmParametroInformado = false;

		// Ger�ncia Regional
		Integer idGerenciaRegional = null;

		if(gerarRelatorioContasEmRevisaoActionForm.getIdGerenciaRegional() != null
						&& !gerarRelatorioContasEmRevisaoActionForm.getIdGerenciaRegional().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			idGerenciaRegional = new Integer(gerarRelatorioContasEmRevisaoActionForm.getIdGerenciaRegional());
		}

		// Ger�ncia Regional
		Integer idUnidadeNegocio = null;

		if(gerarRelatorioContasEmRevisaoActionForm.getIdUnidadeNegocio() != null
						&& !gerarRelatorioContasEmRevisaoActionForm.getIdUnidadeNegocio().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			idUnidadeNegocio = new Integer(gerarRelatorioContasEmRevisaoActionForm.getIdUnidadeNegocio());
		}

		// Elo
		Localidade elo = null;

		String idElo = gerarRelatorioContasEmRevisaoActionForm.getIdElo();

		if(idElo != null && !idElo.equals("")){
			peloMenosUmParametroInformado = true;

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idElo));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, idElo));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				elo = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Elo");
			}
		}

		// Localidade Inicial
		Localidade localidadeInicial = null;

		String idLocalidadeInicial = gerarRelatorioContasEmRevisaoActionForm.getIdLocalidadeInicial();

		if(idLocalidadeInicial != null && !idLocalidadeInicial.equals("")){
			peloMenosUmParametroInformado = true;

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeInicial));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				localidadeInicial = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Inicial");
			}
		}

		// Localidade Final
		Localidade localidadeFinal = null;

		String idLocalidadeFinal = gerarRelatorioContasEmRevisaoActionForm.getIdLocalidadeFinal();

		if(idLocalidadeFinal != null && !idLocalidadeFinal.equals("")){
			peloMenosUmParametroInformado = true;

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeFinal));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				localidadeFinal = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Final");
			}
		}

		// Motivo de Revis�o
		Integer idMotivoRevisao = null;

		if(gerarRelatorioContasEmRevisaoActionForm.getIdMotivoRevisao() != null
						&& !gerarRelatorioContasEmRevisaoActionForm.getIdMotivoRevisao()
										.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;

			idMotivoRevisao = new Integer(gerarRelatorioContasEmRevisaoActionForm.getIdMotivoRevisao());
		}

		// Perfil do Im�vel
		Integer idImovelPerfil = null;

		if(gerarRelatorioContasEmRevisaoActionForm.getIdImovelPerfil() != null
						&& !gerarRelatorioContasEmRevisaoActionForm.getIdImovelPerfil().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;

			idImovelPerfil = new Integer(gerarRelatorioContasEmRevisaoActionForm.getIdImovelPerfil());
		}

		// Refer�ncia Inicial
		Integer referenciaInicial = null;

		if(gerarRelatorioContasEmRevisaoActionForm.getReferenciaInicial() != null
						&& !gerarRelatorioContasEmRevisaoActionForm.getReferenciaInicial().equals("")){
			peloMenosUmParametroInformado = true;

			referenciaInicial = Util.formatarMesAnoComBarraParaAnoMes(gerarRelatorioContasEmRevisaoActionForm.getReferenciaInicial());
		}

		// Refer�ncia Final
		Integer referenciaFinal = null;

		if(gerarRelatorioContasEmRevisaoActionForm.getReferenciaFinal() != null
						&& !gerarRelatorioContasEmRevisaoActionForm.getReferenciaFinal().equals("")){
			peloMenosUmParametroInformado = true;

			referenciaFinal = Util.formatarMesAnoComBarraParaAnoMes(gerarRelatorioContasEmRevisaoActionForm.getReferenciaFinal());
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// seta os parametros que ser�o mostrados no relat�rio

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		// Relat�rio Anal�tico
		RelatorioContasEmRevisao relatorioContasEmRevisao = new RelatorioContasEmRevisao((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));
		relatorioContasEmRevisao.addParametro("idGerenciaRegional", idGerenciaRegional);

		if(!Util.isVazioOuBranco(idUnidadeNegocio)){
			relatorioContasEmRevisao.addParametro("idUnidadeNegocio", idUnidadeNegocio);
		}

		if(elo != null){
			relatorioContasEmRevisao.addParametro("idElo", elo.getId());
		}

		if(localidadeInicial != null){
			relatorioContasEmRevisao.addParametro("idLocalidadeInicial", localidadeInicial.getId());
		}

		if(localidadeFinal != null){
			relatorioContasEmRevisao.addParametro("idLocalidadeFinal", localidadeFinal.getId());
		}
		relatorioContasEmRevisao.addParametro("idMotivoRevisao", idMotivoRevisao);
		relatorioContasEmRevisao.addParametro("idImovelPerfil", idImovelPerfil);
		relatorioContasEmRevisao.addParametro("referenciaInicial", referenciaInicial);
		relatorioContasEmRevisao.addParametro("referenciaFinal", referenciaFinal);

		relatorioContasEmRevisao.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorioContasEmRevisao, tipoRelatorio, httpServletRequest, httpServletResponse,
						actionMapping);

		// Relat�rio Resumido -- TODO N�o � necess�rio este trexo de c�digo.
		// RelatorioContasEmRevisaoResumido relatorioContasEmRevisaoResumido = new
		// RelatorioContasEmRevisaoResumido(
		// (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		// relatorioContasEmRevisaoResumido.addParametro("idGerenciaRegional", idGerenciaRegional);
		// if(elo != null){
		// relatorioContasEmRevisaoResumido.addParametro("idElo", elo.getId());
		// }
		//
		// if(localidadeInicial != null){
		// relatorioContasEmRevisaoResumido.addParametro("idLocalidadeInicial",
		// localidadeInicial.getId());
		// }
		//
		// if(localidadeFinal != null){
		// relatorioContasEmRevisaoResumido.addParametro("idLocalidadeFinal",
		// localidadeFinal.getId());
		// }
		// relatorioContasEmRevisaoResumido.addParametro("idMotivoRevisao", idMotivoRevisao);
		// relatorioContasEmRevisaoResumido.addParametro("idImovelPerfil", idImovelPerfil);
		// relatorioContasEmRevisaoResumido.addParametro("referenciaInicial", referenciaInicial);
		// relatorioContasEmRevisaoResumido.addParametro("referenciaFinal", referenciaFinal);
		//
		// relatorioContasEmRevisaoResumido.addParametro("tipoFormatoRelatorio",
		// Integer.parseInt(tipoRelatorio));
		//
		// processarExibicaoRelatorio(relatorioContasEmRevisaoResumido, tipoRelatorio,
		// httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
