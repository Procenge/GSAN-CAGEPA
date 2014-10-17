package gcom.gui.seguranca.acesso.transacao;

import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConsultarAuditoriaParcelamentoContasAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarAuditoriaParcelamentoContas");

		ConsultarAuditoriaParcelamentoContasActionForm form = (ConsultarAuditoriaParcelamentoContasActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		String idUsuario = form.getIdUsuario();
		String idFuncionario = form.getIdFuncionario();
		String dataInicial = form.getDataInicial();
		String dataFinal = form.getDataFinal();
		Date dateInicial = null;
		Date dateFinal = null;

		Fachada fachada = Fachada.getInstancia();
		
		// FS0001 - Verificar existência da matrícula do funcionário
		if(idFuncionario != null && !idFuncionario.equals("")){
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, new Integer(idFuncionario)));
			
			Collection colecaoFuncionario = (Collection) fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());
			
			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){
				throw new ActionServletException("atencao.matricula_inexistente");
			}
		}
		
		// FS0002 - Verificar existência da matrícula do usuário
		if(idUsuario != null && !idUsuario.equals("")){
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, new Integer(idUsuario)));

			Collection colecaoImovel = (Collection) fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				throw new ActionServletException("atencao.matricula_inexistente");
			}
		}
		
		// FS0003 - Verificar se foi informado o funcionário ou o usuário
		if((idUsuario == null || (idUsuario != null && idUsuario.equals("")))
						&& (idFuncionario == null || (idFuncionario != null && idFuncionario.equals("")))){
			throw new ActionServletException("atencao.informe_matricula_usuario_funcionario");
		}

		// FS0004 - Validar data
		if(dataInicial == null || dataInicial.equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Data Inicial");
		}

		if(dataFinal == null || dataFinal.equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Data Final");
		}

		if(Util.validarDiaMesAno(dataInicial)){
			throw new ActionServletException("atencao.data.invalida", null, "Data");
		}

		if(Util.validarDiaMesAno(dataFinal)){
			throw new ActionServletException("atencao.data.invalida", null, "Data");
		}

		// FS0005 - Verificar data final menor que data inicial
		Integer dataInicialInt = null;
		Integer dataFinalInt = null;

		if(dataInicial != null && !dataInicial.equals("") && dataFinal != null && !dataFinal.equals("")){
			dateInicial = Util.converterStringParaData(dataInicial);
			dateFinal = Util.converterStringParaData(dataFinal);

			dataInicialInt = new Integer(Util.formatarDataAAAAMMDD(dateInicial));
			dataFinalInt = new Integer(Util.formatarDataAAAAMMDD(dateFinal));

			Integer resultado = Util.compararData(dateInicial, dateFinal);

			if(resultado.equals(new Integer(1))){
				throw new ActionServletException("atencao.data_final_anterior_inicial");
			}

		}

		// FS0006 - Verificar período de cancelamento/revisão de contas
		if(dataInicial != null && !dataInicial.equals("")){
			dateInicial = Util.converterStringParaData(dataInicial);
			Date date = new Date();

			Integer resultado = Util.compararData(dateInicial, date);

			String dateFormatado = Util.formatarData(date);

			if(resultado.equals(new Integer(1))){
				throw new ActionServletException("atencao.data_inicial_posterior_corrente", null, dateFormatado);
			}
		}

		if(dataFinal != null && !dataFinal.equals("")){
			dateFinal = Util.converterStringParaData(dataFinal);
			Date date = new Date();

			Integer resultado = Util.compararData(dateFinal, date);

			String dateFormatado = Util.formatarData(date);

			if(resultado.equals(new Integer(1))){
				throw new ActionServletException("atencao.data_final_posterior_corrente", null, dateFormatado);
			}
		}
		
		Collection colecao = null;

		// Parte do código que trata se foi escolhido o usuário
		if(idUsuario != null && !idUsuario.equals("")){
			colecao = (Collection) fachada.pesquisarParcelamentoContaUsuario(new Integer(idUsuario), dateInicial, dateFinal);
		}

		// Parte do código que trata se foi escolhido o funcionário
		if(idFuncionario != null && !idFuncionario.equals("")){
			colecao = (Collection) fachada.pesquisarParcelamentoContaFuncionario(new Integer(idFuncionario),
 dateInicial, dateFinal);
		}

		// Trata nenhum resultado
		if(Util.isVazioOrNulo(colecao)){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		sessao.setAttribute("colecao", colecao);

		return retorno;
	}
}