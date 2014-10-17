
package gcom.gui.relatorio.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.micromedicao.hidrometro.HidrometroActionForm;
import gcom.micromedicao.hidrometro.FiltroHidrometroMotivoMovimentacao;
import gcom.micromedicao.hidrometro.FiltroHidrometroMovimentacao;
import gcom.micromedicao.hidrometro.HidrometroMotivoMovimentacao;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.micromedicao.hidrometro.RelatorioHidrometroMovimentacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioHidrometroMovimentacaoAction
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

		HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;

		// Recupera os parâmetros do form
		String dataMovimentacaoInicial = hidrometroActionForm.getDataMovimentacaoInicial();
		String dataMovimentacaoFinal = hidrometroActionForm.getDataMovimentacaoFinal();
		String horaMovimentacaoInicial = hidrometroActionForm.getHoraMovimentacaoInicial();
		String horaMovimentacaoFinal = hidrometroActionForm.getHoraMovimentacaoFinal();
		String localArmazenagemDestino = hidrometroActionForm.getLocalArmazenagemDestino();
		String localArmazenagemOrigem = hidrometroActionForm.getLocalArmazenagemOrigem();
		String idMotivoMovimentacao = hidrometroActionForm.getMotivoMovimentacao();
		String usuario = hidrometroActionForm.getUsuario();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoHidrometroMovimentacaoHelper = (Collection) sessao.getAttribute("colecaoHidrometroMovimentacaoHelper");


		Map parametros = new HashMap();

		if(dataMovimentacaoInicial != null){

			parametros.put("dataMovimentacaoInicial", dataMovimentacaoInicial);
		}

		if(dataMovimentacaoFinal != null){

			parametros.put("dataMovimentacaoFinal", dataMovimentacaoFinal);
		}


		if(horaMovimentacaoInicial != null){

			parametros.put("horaMovimentacaoInicial", horaMovimentacaoInicial);
		}

		if(horaMovimentacaoFinal != null){

			parametros.put("horaMovimentacaoFinal", horaMovimentacaoFinal);
		}

		if(localArmazenagemDestino != null){

			parametros.put("localArmazenagemDestino", localArmazenagemDestino);
		}
		if(localArmazenagemOrigem != null){

			parametros.put("localArmazenagemOrigem", localArmazenagemOrigem);
		}
		//
		if(idMotivoMovimentacao != null && !idMotivoMovimentacao.toString().trim().equalsIgnoreCase("")){

			FiltroHidrometroMotivoMovimentacao filtroHidrometroMotivoMovimentacao = new FiltroHidrometroMotivoMovimentacao();

			filtroHidrometroMotivoMovimentacao.adicionarParametro(new ParametroSimples(FiltroHidrometroMotivoMovimentacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroMotivoMovimentacao.setCampoOrderBy(FiltroHidrometroMotivoMovimentacao.DESCRICAO);

			filtroHidrometroMotivoMovimentacao
.adicionarParametro(new ParametroSimples(FiltroHidrometroMovimentacao.ID,
							idMotivoMovimentacao));


			Collection colecaoHidrometroMotivoMovimentacao = Fachada.getInstancia().pesquisar(filtroHidrometroMotivoMovimentacao,
							HidrometroMotivoMovimentacao.class.getName());

			String descricaoMotivoMovimentacao = "";
			if(colecaoHidrometroMotivoMovimentacao != null && !colecaoHidrometroMotivoMovimentacao.isEmpty()){
				descricaoMotivoMovimentacao = ((HidrometroMotivoMovimentacao) colecaoHidrometroMotivoMovimentacao.iterator().next())
								.getDescricao();
			}

			parametros.put("motivoMovimentacao", descricaoMotivoMovimentacao);
		}
		if(usuario != null){

			parametros.put("usuario", usuario);
		}

		// Fim da parte que vai mandar os parametros para o relatório
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");



		RelatorioHidrometroMovimentacao relatorioHidrometroMovimentacao = new RelatorioHidrometroMovimentacao(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"),
						ConstantesRelatorios.RELATORIO_HIDROMETRO_MOVIMENTACAO);

		relatorioHidrometroMovimentacao.addParametro("colecaoHidrometroMovimentacaoHelper", colecaoHidrometroMovimentacaoHelper);
		relatorioHidrometroMovimentacao.addParametro("hidrometroMovimentacaoParametros", parametros);


		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioHidrometroMovimentacao.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioHidrometroMovimentacao, tipoRelatorio, httpServletRequest, httpServletResponse,
						actionMapping);

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}
}
