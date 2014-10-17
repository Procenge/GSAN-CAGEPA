
package gcom.gui.batch;

import gcom.batch.FuncionalidadeIniciada;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.IoUtil;

import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;

/**
 * Action responsável pela exibição do Log da funcionalidade iniciada do processo.
 * 
 * @author Ricardo Rodrigues
 * @created 08/03/2012
 */
public class ExibirFuncionalidadeIniciadaLogAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		ActionForward retorno = actionMapping.findForward("exibirLogFuncionalidadeIniciada");

		Integer idFuncionalidadeIniciada = Integer.parseInt(request.getParameter("idFuncionalidadeIniciada"));

		Fachada facade = Fachada.getInstancia();
		FuncionalidadeIniciada funcionalidadeIniciada = (FuncionalidadeIniciada) facade.pesquisar(idFuncionalidadeIniciada,
						FuncionalidadeIniciada.class);

		StringBuilder builder = new StringBuilder();

		// thiagosantos: deveria ser chamado apenas uma vez já que deve-se usar o objeto blob. Do
		// jeito que estava criava 2 blobs desnecessariamente. Ainda é possível deixar de criar o
		// blob e usar diretamente o array
		// de bytes.
		Blob blob = null;

		byte[] textoLogExecucaoBlob = funcionalidadeIniciada.getTextoLogExecucaoBlob();

		if(textoLogExecucaoBlob != null){
			blob = Hibernate.createBlob(textoLogExecucaoBlob);
		}

		if(blob != null){
			builder = IoUtil.lerConteudoCampoBlobTipoTxt(blob);
		}

		request.setAttribute("logFuncionalidadeIniciada", builder.toString());

		return retorno;
	}

}
