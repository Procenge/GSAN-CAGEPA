
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OsSeletivaComando;
import gcom.atendimentopublico.ordemservico.bean.OrdemServicoSeletivaComandoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarComandoOsSeletivaAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = this.getFachada();

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirResultadoPesquisaComandoOsSeletiva");

		ConsultarComandoOsSeletivaActionForm consultarComandoOsSeletivaActionForm = (ConsultarComandoOsSeletivaActionForm) actionForm;
		OrdemServicoSeletivaComandoHelper ordemServicoSeletivaComandoHelper = new OrdemServicoSeletivaComandoHelper();
		boolean filtroInformado = false;
		Collection<OsSeletivaComando> colecaoComandos=null;

		if(consultarComandoOsSeletivaActionForm.getTitulo() != null && !consultarComandoOsSeletivaActionForm.getTitulo().equals("")){

			filtroInformado = true;

			ordemServicoSeletivaComandoHelper.setTitulo(consultarComandoOsSeletivaActionForm.getTitulo());
			ordemServicoSeletivaComandoHelper.setTipoPesquisa(consultarComandoOsSeletivaActionForm.getTipoPesquisa());
		}
		if(consultarComandoOsSeletivaActionForm.getFirma() != null){

			filtroInformado = true;

			Integer[] colecaoFirma = this.converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getFirma());

			ordemServicoSeletivaComandoHelper.setFirma(colecaoFirma);

		}
		if(consultarComandoOsSeletivaActionForm.getServicoTipo() != null){

			filtroInformado = true;

			Integer[] colecaoServicoTipo = this
							.converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getServicoTipo());

			ordemServicoSeletivaComandoHelper.setServicoTipo(colecaoServicoTipo);

		}
		// Data Comando
		if(consultarComandoOsSeletivaActionForm.getDataComandoInicial() != null
						&& !consultarComandoOsSeletivaActionForm.getDataComandoInicial().equals("")){

			if(Util.validarDiaMesAno(consultarComandoOsSeletivaActionForm.getDataComandoInicial())){
				throw new ActionServletException("atencao.data.invalida", null, "Data do Comando Inicial ");
			}else{

				filtroInformado = true;

				ordemServicoSeletivaComandoHelper.setDataComandoInicial(consultarComandoOsSeletivaActionForm.getDataComandoInicial());

			}

		}

		if(consultarComandoOsSeletivaActionForm.getDataComandoFinal() != null
						&& !consultarComandoOsSeletivaActionForm.getDataComandoFinal().equals("")){

			if(Util.validarDiaMesAno(consultarComandoOsSeletivaActionForm.getDataComandoFinal())){
				throw new ActionServletException("atencao.data.invalida", null, "Data do Comando Final");
			}else{

				filtroInformado = true;

				ordemServicoSeletivaComandoHelper.setDataComandoFinal(consultarComandoOsSeletivaActionForm.getDataComandoFinal());
			}
		}

		if(consultarComandoOsSeletivaActionForm.getDataComandoInicial() != null
						&& !consultarComandoOsSeletivaActionForm.getDataComandoInicial().equals("")
						&& consultarComandoOsSeletivaActionForm.getDataComandoFinal() != null
						&& !consultarComandoOsSeletivaActionForm.getDataComandoFinal().equals("")){

			Date comandoOsSeletivaInicial = Util.converteStringParaDate(consultarComandoOsSeletivaActionForm.getDataComandoInicial());
			Date comandoOsSeletivaFinal = Util.converteStringParaDate(consultarComandoOsSeletivaActionForm.getDataComandoFinal());

			if(Util.compararData(comandoOsSeletivaInicial, comandoOsSeletivaFinal) == 1){
				throw new ActionServletException("atencao.data_final_periodo.anterior.data_inicial_periodo");
			}
		}
		// Data Realização
		if(consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoInicial() != null
						&& !consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoInicial().equals("")){

			if(Util.validarDiaMesAno(consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoInicial())){
				throw new ActionServletException("atencao.data.invalida", null, "Data de Realização do Comando Inicial ");
			}else{

				filtroInformado = true;

				ordemServicoSeletivaComandoHelper.setDataRealizacaoComandoInicial(consultarComandoOsSeletivaActionForm
								.getDataRealizacaoComandoInicial());
			}

		}

		if(consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoFinal() != null
						&& !consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoFinal().equals("")){

			if(Util.validarDiaMesAno(consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoFinal())){
				throw new ActionServletException("atencao.data.invalida", null, "Data de Realização do Comando Final");
			}else{
				filtroInformado = true;

				ordemServicoSeletivaComandoHelper.setDataRealizacaoComandoFinal(consultarComandoOsSeletivaActionForm
								.getDataRealizacaoComandoFinal());
			}
		}

		if(consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoInicial() != null
						&& !consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoInicial().equals("")
						&& consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoFinal() != null
						&& !consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoFinal().equals("")){

			Date comandoOsRealizacaoInicial = Util.converteStringParaDate(consultarComandoOsSeletivaActionForm
							.getDataRealizacaoComandoInicial());
			Date comandoOsRealizacaoFinal = Util.converteStringParaDate(consultarComandoOsSeletivaActionForm
							.getDataRealizacaoComandoFinal());

			if(Util.compararData(comandoOsRealizacaoInicial, comandoOsRealizacaoFinal) == 1){
				throw new ActionServletException("atencao.data_final_periodo.anterior.data_inicial_periodo");
			}
		}

		if(consultarComandoOsSeletivaActionForm.getElo() != null){

			filtroInformado = true;
			Integer[] colecaoElos = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getElo());

			ordemServicoSeletivaComandoHelper.setElo(colecaoElos);
		}
		if(consultarComandoOsSeletivaActionForm.getFaturamentoGrupo() != null){

			filtroInformado = true;
			Integer[] colecaoFaturamentoGrupo = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm
							.getFaturamentoGrupo());

			ordemServicoSeletivaComandoHelper.setFaturamentoGrupo(colecaoFaturamentoGrupo);
		}
		if(consultarComandoOsSeletivaActionForm.getGerenciaRegional() != null){

			filtroInformado = true;
			Integer[] colecaoGerenciaRegional = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm
							.getGerenciaRegional());

			ordemServicoSeletivaComandoHelper.setGerenciaRegional(colecaoGerenciaRegional);
		}
		if(consultarComandoOsSeletivaActionForm.getLocalidade() != null){

			filtroInformado = true;
			Integer[] colecaoLocalidade = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getLocalidade());

			ordemServicoSeletivaComandoHelper.setLocalidade(colecaoLocalidade);
		}
		if(consultarComandoOsSeletivaActionForm.getSetor() != null){

			filtroInformado = true;
			Integer[] colecaoSetor = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getSetor());

			ordemServicoSeletivaComandoHelper.setSetor(colecaoSetor);
		}
		if(consultarComandoOsSeletivaActionForm.getQuadra() != null){

			filtroInformado = true;
			Integer[] colecaoQuadra = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getQuadra());

			ordemServicoSeletivaComandoHelper.setQuadra(colecaoQuadra);
		}
		if(consultarComandoOsSeletivaActionForm.getRota() != null){

			filtroInformado = true;
			Integer[] colecaoRota = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getRota());

			ordemServicoSeletivaComandoHelper.setRota(colecaoRota);
		}
		if(consultarComandoOsSeletivaActionForm.getLote() != null){

			filtroInformado = true;
			Integer[] colecaoLote = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getLote());

			ordemServicoSeletivaComandoHelper.setLote(colecaoLote);
		}
		if(consultarComandoOsSeletivaActionForm.getBairro() != null){

			filtroInformado = true;
			Integer[] colecaoBairro = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getBairro());

			ordemServicoSeletivaComandoHelper.setBairro(colecaoBairro);

		}
		if(consultarComandoOsSeletivaActionForm.getLogradouro() != null){

			filtroInformado = true;
			Integer[] colecaoLogradouro = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getLogradouro());

			ordemServicoSeletivaComandoHelper.setLogradouro(colecaoLogradouro);

		}
		if(consultarComandoOsSeletivaActionForm.getPerfilImovel() != null){

			filtroInformado = true;
			Integer[] colecaoPerfilImovel = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getPerfilImovel());

			ordemServicoSeletivaComandoHelper.setPerfilImovel(colecaoPerfilImovel);

		}

		if(consultarComandoOsSeletivaActionForm.getCategoria() != null){

			filtroInformado = true;
			Integer[] colecaoCategoria = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getCategoria());

			ordemServicoSeletivaComandoHelper.setCategoria(colecaoCategoria);

		}
		if(consultarComandoOsSeletivaActionForm.getSubCategoria() != null){

			filtroInformado = true;
			Integer[] colecaoSubCategoria = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm.getSubCategoria());

			ordemServicoSeletivaComandoHelper.setSubCategoria(colecaoSubCategoria);

		}
		if(consultarComandoOsSeletivaActionForm.getLigacaoAguaSituacao() != null){

			filtroInformado = true;
			Integer[] colecaoLigacaoAguaSituacao = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm
							.getLigacaoAguaSituacao());

			ordemServicoSeletivaComandoHelper.setLigacaoAguaSituacao(colecaoLigacaoAguaSituacao);

		}
		if(consultarComandoOsSeletivaActionForm.getLigacaoAguaSituacao() != null){
			filtroInformado = true;
			Integer[] colecaoLigacaoEsgotoSituacao = converterColecaoParaStringParaInteger(consultarComandoOsSeletivaActionForm
							.getLigacaoAguaSituacao());

			ordemServicoSeletivaComandoHelper.setLigacaoEsgotoSituacao(colecaoLigacaoEsgotoSituacao);

		}

		// Período de realização não deve ser informado para situação do comando não realizado
		if(consultarComandoOsSeletivaActionForm.getSituacaoComando() != null
						&& consultarComandoOsSeletivaActionForm.getSituacaoComando().equals("2")
						&& (consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoInicial() != null && !consultarComandoOsSeletivaActionForm
										.getDataRealizacaoComandoInicial().equals(""))
						&& (consultarComandoOsSeletivaActionForm.getDataRealizacaoComandoFinal() != null && !consultarComandoOsSeletivaActionForm
										.getDataRealizacaoComandoFinal().equals(""))){

			throw new ActionServletException("atencao.periodo_realizacao_comando");

		}

		if(consultarComandoOsSeletivaActionForm.getSituacaoComando() != null){
			if(consultarComandoOsSeletivaActionForm.getSituacaoComando().equals("0")){
				ordemServicoSeletivaComandoHelper.setSituacaoComando("Todos");
			}else if(consultarComandoOsSeletivaActionForm.getSituacaoComando().equals("1")){
				ordemServicoSeletivaComandoHelper.setSituacaoComando("Realizado");
			}else if(consultarComandoOsSeletivaActionForm.getSituacaoComando().equals("2")){
				ordemServicoSeletivaComandoHelper.setSituacaoComando("Não Realizado");
			}

		}

		if(filtroInformado == false){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");

		}else{

			colecaoComandos = fachada.filtrarComandoOSSeletiva(ordemServicoSeletivaComandoHelper);

			sessao.setAttribute("osSeletivaComandoHelper", ordemServicoSeletivaComandoHelper);
		}

		if(colecaoComandos == null || colecaoComandos.size() == 0){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");

		}else if(colecaoComandos.size() == 1){

			OsSeletivaComando osc = (OsSeletivaComando) Util.retonarObjetoDeColecao(colecaoComandos);

			sessao.setAttribute("numeroComando", osc.getId());
			retorno = actionMapping.findForward("consultarComandoOsSeletiva");

		}else{

			int totalRegistros = colecaoComandos.size();

			retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

			int numeroPaginasPesquisa = ((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa")).intValue();

			ordemServicoSeletivaComandoHelper.setNumeroPaginasPesquisa(numeroPaginasPesquisa);

			colecaoComandos = fachada.filtrarComandoOSSeletiva(ordemServicoSeletivaComandoHelper);

			Collection colecaodados = loadColecaoOSHelper(colecaoComandos);
			sessao.setAttribute("ordemServicoSeletivaComandoHelper", ordemServicoSeletivaComandoHelper);
			sessao.setAttribute("colecaoComandoHelper", colecaodados);
			sessao.setAttribute("parametroInformado", new Boolean(filtroInformado));

		}

		return retorno;

	}

	private Collection loadColecaoOSHelper(Collection<OsSeletivaComando> colecaoComandoOS){

		Collection colecaoOSHelper = new ArrayList();

		if(Util.isVazioOrNulo(colecaoComandoOS)){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}else{
			for(Iterator iter = colecaoComandoOS.iterator(); iter.hasNext();){

				OsSeletivaComando osSeletivaComando = (OsSeletivaComando) iter.next();

				OrdemServicoSeletivaComandoHelper helper = new OrdemServicoSeletivaComandoHelper();

				helper.setId(osSeletivaComando.getId());
				helper.setTitulo(osSeletivaComando.getDescricaoTitulo());

				if(osSeletivaComando.getServicoTipo() != null){

					helper.setServicoTipoDescricao(osSeletivaComando.getServicoTipo().getDescricao());

				}

				if(osSeletivaComando.getEmpresa() != null){

					helper.setFirmaDescricao(osSeletivaComando.getEmpresa().getDescricao());

				}

				helper.setDataComando(osSeletivaComando.getTempoComando().toString());

				if(osSeletivaComando.getTempoRealizacao() != null){

					helper.setDataRealizacaoComando(osSeletivaComando.getTempoRealizacao().toString());
				}

				if(osSeletivaComando.getQuantidadeMaximaOrdens() != null){

					FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
					filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.OS_SELETIVA_COMANDO_ID, osSeletivaComando
									.getIdAntigo()));

					Collection colecaoOrdemServico = this.getFachada().pesquisar(filtroOrdemServico, OrdemServico.class.getName());

					Integer quantidadeMax = colecaoOrdemServico.size();
					
					if(colecaoOrdemServico != null){

						helper.setQuantidadeMaximaOrdem(quantidadeMax.toString());
					}
				}

				colecaoOSHelper.add(helper);
			}
		}

		return colecaoOSHelper;
	}

	public Integer[] converterColecaoParaStringParaInteger(String[] colecao){

		Integer[] colecaoInteger = new Integer[colecao.length];
		for(int i = 0; i < colecao.length; i++){
			colecaoInteger[i] = Util.obterInteger(colecao[i]);
		}
		return colecaoInteger;

	}

}
