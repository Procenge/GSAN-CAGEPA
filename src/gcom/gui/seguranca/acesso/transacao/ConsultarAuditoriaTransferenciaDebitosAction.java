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

public class ConsultarAuditoriaTransferenciaDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarAuditoriaTransferenciaDebitos");

		ConsultarAuditoriaTransferenciaDebitosActionForm form = (ConsultarAuditoriaTransferenciaDebitosActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		String idUsuarioOrigem = form.getIdUsuarioOrigem();
		String idUsuarioDestino = form.getIdUsuarioDestino();
		String idFuncionario = form.getIdFuncionario();
		String dataInicial = form.getDataInicial();
		String dataFinal = form.getDataFinal();

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
		if(idUsuarioOrigem != null && !idUsuarioOrigem.equals("") || idUsuarioDestino != null && !idUsuarioOrigem.equals("")){
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, new Integer(idUsuarioOrigem)));

			Collection colecaoImovel = (Collection) fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				throw new ActionServletException("atencao.matricula_inexistente");
			}
		}
		
		// FS0003 - Verificar se foi informado o funcionário ou o usuário
		if(idUsuarioOrigem == null && idFuncionario == null){

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
		Date dataInicialDate = null;
		Date dataFinalDate = null;

		if(dataInicial != null && !dataInicial.equals("") && dataFinal != null && !dataFinal.equals("")){
			dataInicialDate = Util.converterStringParaData(dataInicial);
			dataFinalDate = Util.converterStringParaData(dataFinal);

			Integer resultado = Util.compararData(dataInicialDate, dataFinalDate);

			if(resultado.equals(new Integer(1))){
				throw new ActionServletException("atencao.data_final_anterior_inicial");
			}

		}

		Collection colecao = null;

		// Parte do código que trata se foi escolhido o usuário
		if(idUsuarioOrigem != null && !idUsuarioOrigem.equals("") || idUsuarioDestino != null && !idUsuarioDestino.equals("")){

			Integer idUsuarioOrigemInt = null;
			Integer idUsuarioDestinoInt = null;

			if(!idUsuarioOrigem.equals("")){
				idUsuarioOrigemInt = Integer.valueOf(idUsuarioOrigem);
			}
			if(!idUsuarioDestino.equals("")){
				idUsuarioDestinoInt = Integer.valueOf(idUsuarioDestino);
			}
			colecao = (Collection) fachada.consultarAuditoriaTransferenciaDebitosUsuario(idUsuarioOrigemInt, idUsuarioDestinoInt,
							dataInicialDate, dataFinalDate);
		}

		// Parte do código que trata se foi escolhido o funcionário
		if(idFuncionario != null && !idFuncionario.equals("")){
			colecao = (Collection) fachada.consultarAuditoriaTransferenciaDebitosFuncionario(Integer.valueOf(idFuncionario),
							dataInicialDate, dataFinalDate);
		}

		// Trata nenhum resultado
		if(Util.isVazioOrNulo(colecao)){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		sessao.setAttribute("colecao", colecao);

		return retorno;
	}
}