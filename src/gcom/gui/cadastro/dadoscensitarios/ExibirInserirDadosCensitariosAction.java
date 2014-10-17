
package gcom.gui.cadastro.dadoscensitarios;

import gcom.cadastro.dadocensitario.FiltroFonteDadosCensitario;
import gcom.cadastro.dadocensitario.FonteDadosCensitario;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC1017] Inserir Dados Censitários
 * 
 * @author Anderson Italo
 * @date 08/02/2011
 */
public class ExibirInserirDadosCensitariosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirDadosCensitariosAction");

		InserirDadosCensitariosActionForm form = (InserirDadosCensitariosActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		if(form.getIndicadorLocalidadeMunicipio() == null){
			form.setIndicadorLocalidadeMunicipio(ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE);
		}

		// 3.1. Seleciona a fonte de informação a partir da tabela FONTE_DADOS_CENSITARIOS
		// FS0001 – Verificar existência de dados].
		FiltroFonteDadosCensitario filtroFonteDadosCensitario = new FiltroFonteDadosCensitario();
		filtroFonteDadosCensitario.setCampoOrderBy(FiltroFonteDadosCensitario.DESCRICAO);

		Collection colecaoFonteDadosCensitarios = fachada.pesquisar(filtroFonteDadosCensitario, FonteDadosCensitario.class.getName());

		if(colecaoFonteDadosCensitarios != null && !colecaoFonteDadosCensitarios.isEmpty()){
			httpServletRequest.setAttribute("colecaoFonteDadosCensitarios", colecaoFonteDadosCensitarios);
		}else{
			if(colecaoFonteDadosCensitarios == null || colecaoFonteDadosCensitarios.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "FONTE_DADOS_CENSITARIOS");
			}
		}

		// caso tenha informado que se trate de dados de localidade
		if(form.getIndicadorLocalidadeMunicipio().equals(ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE)){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
				httpServletRequest.setAttribute("colecaoLocalidade", colecaoLocalidade);
			}

		}else{
			// caso contrário se trata de dados de município
			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
			filtroMunicipio.setCampoOrderBy(FiltroMunicipio.NOME);

			Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

			if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){
				httpServletRequest.setAttribute("colecaoMunicipio", colecaoMunicipio);
			}
		}

		// Carregar a data corrente do sistema
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataCorrente = new GregorianCalendar();

		// Data Corrente
		httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));

		return retorno;
	}

}
