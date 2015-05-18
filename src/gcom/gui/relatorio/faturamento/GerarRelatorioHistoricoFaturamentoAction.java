
package gcom.gui.relatorio.faturamento;

import gcom.cadastro.cliente.ClienteImovelSimplificado;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.cadastro.imovel.ConsultarImovelActionForm;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioHistoricoFaturamento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioHistoricoFaturamentoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws Exception{

		ActionForward retorno = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarImovelActionForm form = (ConsultarImovelActionForm) actionForm;

		String idImovel = null;

		Usuario usuarioLogado = (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado");

		Collection colecaoCompleta = (Collection) sessao.getAttribute("colecaoCompleta");

		// Collection colecaoRetorno = (Collection)
		// httpServletRequest.getAttribute("colecaoContaImovel");
		// colecaoRetorno.addAll((Collection)
		// httpServletRequest.getAttribute("colecaoContaHistoricoImovel"));

		RelatorioHistoricoFaturamento relatorio = new RelatorioHistoricoFaturamento(usuarioLogado,
						ConstantesRelatorios.RELATORIO_HISTORICO_FATURAMENTO_IMOVEL);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = String.valueOf(TarefaRelatorio.TIPO_PDF);
		}
		// relatorio.addParametro("colecaoRetorno", colecaoRetorno);

		if(sessao.getAttribute("idImovelPrincipalAba") != null && !sessao.getAttribute("idImovelPrincipalAba").equals("")){
			idImovel = (String) sessao.getAttribute("idImovelPrincipalAba");
		}else{
			idImovel = form.getIdImovel();
		}

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
		filtroClienteImovel
						.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID, ClienteRelacaoTipo.USUARIO));
		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
		Collection<ClienteImovelSimplificado> colecaoClienteImovel = Fachada.getInstancia().pesquisarClienteImovel(filtroClienteImovel);
		String nomeCliente = "";
		if(!Util.isVazioOrNulo(colecaoClienteImovel)){
			nomeCliente = colecaoClienteImovel.iterator().next().getCliente().getNome();
		}

		relatorio.addParametro("colecaoCompleta", colecaoCompleta);
		relatorio.addParametro("idImovel", idImovel);
		relatorio.addParametro("tipoFormatoRelatorio", tipoRelatorio);
		relatorio.addParametro("relatorioTipo", tipoRelatorio);
		relatorio.addParametro("nomeCliente", nomeCliente);

		try{
			retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);
		}catch(SistemaException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a página no request atual
			// reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			// retorno = actionMapping.findForward("telaAtencaoPopup");
			throw new ActionServletException("atencao.relatorio.vazio");
		}
		// cabeçalho

		return retorno;
	}
}
