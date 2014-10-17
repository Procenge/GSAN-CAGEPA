
package gcom.gui.cadastro.geografico;

import gcom.cadastro.geografico.FiltroMicrorregiao;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.FiltroRegiao;
import gcom.cadastro.geografico.FiltroRegiaoDesenvolvimento;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.Microrregiao;
import gcom.cadastro.geografico.Regiao;
import gcom.cadastro.geografico.RegiaoDesenvolvimento;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.geografico.RelatorioManterMunicipio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de Municipio manter
 * 
 * @author P�ricles Tavares
 * @created 03/02/2011
 */

public class GerarRelatorioMunicipioManterAction
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
		FiltrarMunicipioActionForm form = (FiltrarMunicipioActionForm) actionForm;

		// Recupera todos os campos da p�gina para ser colocada no filtro
		// posteriormente
		String codigoMunicipio = form.getCodigoMunicipio();
		String nomeMunicipio = form.getNomeMunicipio();
		String tipoPesquisa = form.getTipoPesquisa();
		String regiaoDesenv = form.getRegiaoDesenv();
		String regiao = form.getRegiao();
		String microregiao = form.getMicroregiao();
		String unidadeFederacao = form.getUnidadeFederacao();
		String indicadorUso = form.getIndicadorUso();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltroMunicipio filtroMunicipio = (FiltroMunicipio) sessao.getAttribute("filtroMunicipio");

		boolean peloMenosUmParametroInformado = false;
		Map parametros = new HashMap();

		// Nome do Municipio
		if(nomeMunicipio != null && !nomeMunicipio.equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;
			parametros.put("nomeMunicipio", nomeMunicipio);
		}

		// Regi�o de Desenvolvimento
		if(regiaoDesenv != null && !regiaoDesenv.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;
			FiltroRegiaoDesenvolvimento filtroRegiaoDesenvolvimento = new FiltroRegiaoDesenvolvimento();
			filtroRegiaoDesenvolvimento.adicionarParametro(new ParametroSimples(FiltroRegiaoDesenvolvimento.CODIGO, regiaoDesenv));
			RegiaoDesenvolvimento regiaoDesenvolvimento = (RegiaoDesenvolvimento) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroRegiaoDesenvolvimento, RegiaoDesenvolvimento.class.getName()));
			parametros.put("regiaoDesenvolvimento", regiaoDesenvolvimento.getNome());
		}

		// Regi�o
		if(regiao != null && !regiao.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			FiltroRegiao filtroRegiao = new FiltroRegiao();
			filtroRegiao.adicionarParametro(new ParametroSimples("id", regiao));
			Regiao regiaoMunicipio = (Regiao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroRegiao, Regiao.class.getName()));
			parametros.put("regiao", regiaoMunicipio.getNome());
		}

		// Microrregi�o
		if(microregiao != null && !microregiao.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			peloMenosUmParametroInformado = true;

			FiltroMicrorregiao filtroMicrorregiao = new FiltroMicrorregiao();
			filtroMicrorregiao.adicionarParametro(new ParametroSimples(FiltroMicrorregiao.ID, microregiao));
			Microrregiao microregiaoMunicipio = (Microrregiao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroMicrorregiao,
							Microrregiao.class.getName()));

			parametros.put("microregiao", microregiaoMunicipio.getNome());
		}

		// Unidade da Federa��o
		if(unidadeFederacao != null && !unidadeFederacao.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
			filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, unidadeFederacao));
			UnidadeFederacao unidadeFederacaoMuncipio = (UnidadeFederacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroUnidadeFederacao, UnidadeFederacao.class.getName()));

			parametros.put("unidadeFederacao", unidadeFederacaoMuncipio.getSigla());
		}

		// Indicador de Uso
		if(indicadorUso != null && !indicadorUso.equalsIgnoreCase("") && !indicadorUso.equalsIgnoreCase("3")){

			peloMenosUmParametroInformado = true;
			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				parametros.put("indicadorUso", "Ativo");
			}else{
				parametros.put("indicadorUso", "Inativo");
			}
		}

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioManterMunicipio relatorio = new RelatorioManterMunicipio((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"), "/relatorioManterMunicipio.jasper");
		relatorio.addParametro("filtroMunicipioSessao", filtroMunicipio);
		relatorio.addParametro("municipioParametros", parametros);
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}
}
