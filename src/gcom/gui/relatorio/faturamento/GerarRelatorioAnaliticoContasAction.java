
package gcom.gui.relatorio.faturamento;

import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.RelatorioAnaliticoContas;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioAnaliticoContasAction
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

		GerarRelatorioAnaliticoContasActionForm gerarRelatorioAnaliticoContasActionForm = (GerarRelatorioAnaliticoContasActionForm) actionForm;



		// Valida os parâmetro passados como consulta
		boolean peloMenosUmParametroInformado = false;

		// Gerência Regional
		Integer idGerenciaRegional = null;

		if(!gerarRelatorioAnaliticoContasActionForm.getIdGerenciaRegional().equals("")
						&& !gerarRelatorioAnaliticoContasActionForm.getIdGerenciaRegional().equals("-1")){
			peloMenosUmParametroInformado = true;
			idGerenciaRegional = new Integer(gerarRelatorioAnaliticoContasActionForm.getIdGerenciaRegional());
		}

		// idLocalidade
		Integer idLocalidade = null;
		String nomeLocalidade = null;

		if(!gerarRelatorioAnaliticoContasActionForm.getIdLocalidade().equals("")
						&& !gerarRelatorioAnaliticoContasActionForm.getIdLocalidade().equals(null)){
			peloMenosUmParametroInformado = true;
			idLocalidade = new Integer(gerarRelatorioAnaliticoContasActionForm.getIdLocalidade());
			nomeLocalidade = new String(gerarRelatorioAnaliticoContasActionForm.getNomeLocalidade());
		}

		// Categoria
		Integer idCategoria = null;

		if(!gerarRelatorioAnaliticoContasActionForm.getIdCategoria().equals("")
						&& !gerarRelatorioAnaliticoContasActionForm.getIdCategoria().equals("-1")){
			peloMenosUmParametroInformado = true;
			idCategoria = new Integer(gerarRelatorioAnaliticoContasActionForm.getIdCategoria());
		}

		// referencia
		Integer referencia = null;
		if(!gerarRelatorioAnaliticoContasActionForm.getReferencia().equals("")
						&& !gerarRelatorioAnaliticoContasActionForm.getReferencia().equals(null)){
			peloMenosUmParametroInformado = true;
			referencia = Util.formatarMesAnoComBarraParaAnoMes(gerarRelatorioAnaliticoContasActionForm.getReferencia());
		}
		// idCliente
		Integer idCliente = null;
		String nomeCliente = null;

		if(!gerarRelatorioAnaliticoContasActionForm.getIdCliente().equals("")
						&& !gerarRelatorioAnaliticoContasActionForm.getIdCliente().equals(null)){
			peloMenosUmParametroInformado = true;
			idCliente = new Integer(gerarRelatorioAnaliticoContasActionForm.getIdCliente());
			nomeCliente = new String(gerarRelatorioAnaliticoContasActionForm.getNomeCliente());
		}

		// Imóvel
		Integer idImovel = null;
		String inscricao = null;
		if(!gerarRelatorioAnaliticoContasActionForm.getIdImovel().equals("")
						&& !gerarRelatorioAnaliticoContasActionForm.getIdImovel().equals(null)){
			peloMenosUmParametroInformado = true;
			idImovel = new Integer(gerarRelatorioAnaliticoContasActionForm.getIdImovel());
			inscricao = new String(gerarRelatorioAnaliticoContasActionForm.getInscricao());
		}

		// Situacao
		Integer idSituacao = null;
		String nomeSituacao = null;

		if(!gerarRelatorioAnaliticoContasActionForm.getIdSituacao().equals("")
						&& !gerarRelatorioAnaliticoContasActionForm.getIdSituacao().equals("-1")){
			peloMenosUmParametroInformado = true;
			idSituacao = Integer.valueOf(gerarRelatorioAnaliticoContasActionForm.getIdSituacao());
			// nomeSituacao = new String(gerarRelatorioAnaliticoContasActionForm.getNomeSituacao());
		}

		// motivo Retificacao
		Integer motivoRetificacao = null;

		if(!gerarRelatorioAnaliticoContasActionForm.getMotivoRetificacao().equals("")
						&& !gerarRelatorioAnaliticoContasActionForm.getMotivoRetificacao().equals("-1")){
			peloMenosUmParametroInformado = true;
			motivoRetificacao = new Integer(gerarRelatorioAnaliticoContasActionForm.getMotivoRetificacao());
		}

		//setorComercial
		Integer setorComercial = null;
		if(!gerarRelatorioAnaliticoContasActionForm.getSetorComercial().equals("")){
			
			peloMenosUmParametroInformado = true;
			setorComercial = new Integer(gerarRelatorioAnaliticoContasActionForm.getSetorComercial());
		}
		
		// grupoFaturamento
		Integer grupoFaturamento = null;
		if(!gerarRelatorioAnaliticoContasActionForm.getGrupoFaturamento().equals("")){

			peloMenosUmParametroInformado = true;
			grupoFaturamento = new Integer(gerarRelatorioAnaliticoContasActionForm.getGrupoFaturamento());
		}

		// idQuadra
		Integer idQuadra = null;
		if(!gerarRelatorioAnaliticoContasActionForm.getIdQuadra().equals("")){

			peloMenosUmParametroInformado = true;
			idQuadra = new Integer(gerarRelatorioAnaliticoContasActionForm.getIdQuadra());
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}


		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		
		

		// Relatório Analítico
		RelatorioAnaliticoContas relatorioAnaliticoContas = new RelatorioAnaliticoContas(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));


		relatorioAnaliticoContas.addParametro("idGerenciaRegional", idGerenciaRegional);
		relatorioAnaliticoContas.addParametro("idLocalidade", idLocalidade);
		relatorioAnaliticoContas.addParametro("nomeLocalidade", nomeLocalidade);
		relatorioAnaliticoContas.addParametro("idCategoria", idCategoria);
		relatorioAnaliticoContas.addParametro("referencia", referencia);
		relatorioAnaliticoContas.addParametro("idCliente", idCliente);
		relatorioAnaliticoContas.addParametro("nomeCliente", nomeCliente);
		relatorioAnaliticoContas.addParametro("idImovel", idImovel);
		relatorioAnaliticoContas.addParametro("inscricao", inscricao);
		relatorioAnaliticoContas.addParametro("idSituacao", idSituacao);
		relatorioAnaliticoContas.addParametro("nomeSituacao", nomeSituacao);
		relatorioAnaliticoContas.addParametro("motivoRetificacao", motivoRetificacao);
		relatorioAnaliticoContas.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		relatorioAnaliticoContas.addParametro("grupoFaturamento", grupoFaturamento);
		relatorioAnaliticoContas.addParametro("setorComercial", setorComercial);
		relatorioAnaliticoContas.addParametro("idQuadra", idQuadra);
		// if(relatorioAnaliticoContas.calcularTotalRegistrosRelatorio() == 0){
		// throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		// }

		retorno = processarExibicaoRelatorio(relatorioAnaliticoContas, tipoRelatorio, httpServletRequest, httpServletResponse,
						actionMapping);


		return retorno;
	}

}