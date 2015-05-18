/**
 * 
 */

package gcom.gui.micromedicao.leitura;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.*;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * @author bferreira
 */
public class ExibirInformarDadosLeituraAnormalidadeAction
				extends GcomAction {

	/**
	 * Este caso de uso permite informar dados de leitura e anormalidade
	 * [UC0948] Informar Leitura de Fiscalização
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 29/06/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		retorno = verificarRetorno(actionMapping, httpServletRequest);

		InformarDadosLeituraAnormalidadeActionForm form = (InformarDadosLeituraAnormalidadeActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		inicializarFormulario(httpServletRequest, form, sessao);

		popularEmpresaLeiturista(httpServletRequest, form, sessao);

		// pesquisarRota(httpServletRequest, form, sessao);

		pesquisarImovelRota(httpServletRequest, retorno, form, sessao);

		retorno = atualizarDadosLeituraAnormalidade(httpServletRequest, form, sessao, actionMapping);

		retornarPesquisaImovelRota(httpServletRequest, form, sessao);

		efetuarPaginacao(httpServletRequest, sessao, form);

		preencherFormulario(form, sessao, httpServletRequest);

		Boolean alterarForward = processarArquivoLeituraAnormalidade(httpServletRequest, form);

		if(alterarForward){
			retorno = actionMapping.findForward("telaSucesso");
		}

		sessao.setAttribute("indicadorEntradaDadosDigitacao", ConstantesSistema.INATIVO);
		if(httpServletRequest.getParameter("indicadorEntradaDados") != null){

			form.setIndicadorEntradaDados(httpServletRequest.getParameter("indicadorEntradaDados"));
			if(httpServletRequest.getParameter("indicadorEntradaDados").equals("3")){
				sessao.setAttribute("indicadorEntradaDadosDigitacao", ConstantesSistema.ATIVO);
			}
		}

		return retorno;
	}

	private Boolean processarArquivoLeituraAnormalidade(HttpServletRequest httpServletRequest,
					InformarDadosLeituraAnormalidadeActionForm form){

		Boolean retorno = Boolean.FALSE;
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.PROCESSAR_ARQUIVO))){
			this.validarArquivoUpload(form.getArquivoLeitura());
			File arquivoLeitura = this.formFileToFile(form.getArquivoLeitura(), httpServletRequest);
			getFachada().processarArquivoLeituraAnormalidades(Util.obterInteger(form.getIdGrupoFaturamento()), arquivoLeitura,
							this.getUsuarioLogado(httpServletRequest));

			this.montarPaginaSucesso(httpServletRequest, "Informar Dados Leitura Anormalidade enviada para Processamento", "Voltar",
							"/exibirInformarDadosLeituraAnormalidadeAction.do");
			retorno = Boolean.TRUE;

		}
		return retorno;
	}

	private void retornarPesquisaImovelRota(HttpServletRequest httpServletRequest, InformarDadosLeituraAnormalidadeActionForm form,
					HttpSession sessao){

		if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.RETORNAR_IMOVEL_ROTA) != null){
			Object[] listaImovelRota = (Object[]) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA);

			int j = 0;

			for(int i = 0; i < listaImovelRota.length; i++){
				MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) listaImovelRota[i];

				if(!Util.isVazioOuBranco(form.getHidrometro())){
					j = Util.obterInteger(form.getHidrometro());
					break;
				}

				if(!Util.isVazioOuBranco(form.getMatriculaImovel())){
					j = Util.obterInteger(form.getMatriculaImovel());
					break;
				}

				if(!Util.isVazioOuBranco(form.getInscricaoImovel())){
					j = Util.obterInteger(form.getInscricaoImovel());
					break;
				}

				if(!Util.isVazioOuBranco(form.getSequecialImovelRota())){
					j = Util.obterInteger(form.getSequecialImovelRota());
					break;
				}
			}

			int prox = j + 1;
			int ant = j - 1;

			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA, listaImovelRota[j]);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.POSICAO_ROTA, j + 1);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.PROXIMO, prox);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR, ant);
			form.setSequecialImovelRota("");
			form.setInscricaoImovel("");
			form.setMatriculaImovel("");
			form.setHidrometro("");
		}
	}

	private ActionForward verificarRetorno(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		ActionForward retorno;
		if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_IMOVEL_ROTA_POPUP) != null){

			retorno = actionMapping.findForward(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_ACTION_MAPPING);

		}else{

			retorno = actionMapping.findForward(InformarDadosLeituraAnormalidadeActionForm.DEFAULT_ACTION_MAPPING);

		}
		return retorno;
	}

	private void inicializarFormulario(HttpServletRequest httpServletRequest, InformarDadosLeituraAnormalidadeActionForm form,
					HttpSession sessao){

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("menu"))){
			form.setReferenciaMovimento(Util.formatarAnoMesParaMesAno(getFachada().pesquisarParametrosDoSistema().getAnoMesFaturamento()));
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.ID);
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = getFachada().pesquisar(filtroFaturamentoGrupo,
							FaturamentoGrupo.class.getName());
			if(Util.isVazioOrNulo(colecaoFaturamentoGrupo)){
				levantarExcecaoUrlSetada(new ActionServletException("atencao.entidade.inexistente", null, "GRUPO FATURAMENTO"));
			}
			sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);

			try{
				String indicadoPermitirInformarDadosLeituraLote = ParametroMicromedicao.P_PERMITE_INFORMAR_DADOS_LEITURA_EM_LOTE.executar();
				if(indicadoPermitirInformarDadosLeituraLote.equals("1")){
					form.setIndicadorEntradaDados("3");
					sessao.setAttribute("indicadoPermitirInformarDadosLeituraLote", ConstantesSistema.ATIVO);
				}else{
					form.setIndicadorEntradaDados("2");
					sessao.setAttribute("indicadoPermitirInformarDadosLeituraLote", ConstantesSistema.INATIVO);
				}

			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void levantarExcecaoUrlSetada(ActionServletException actionServletException){

		actionServletException.setUrlBotaoVoltar("/gsan/exibirInformarDadosLeituraAnormalidadeAction.do");
		throw actionServletException;
	}

	private void preencherFormulario(InformarDadosLeituraAnormalidadeActionForm form, HttpSession sessao,
					HttpServletRequest httpServletRequest){

		if(sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA) != null){
			MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) sessao
							.getAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA);

			String dataLeituraSugerida = "";

			if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.DATA_LEITURA) != null){
				dataLeituraSugerida = httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.DATA_LEITURA);
			}

			form.setDataLeitura(movimentoRoteiroEmpresa.getDataLeitura() != null ? Util.formatarData(movimentoRoteiroEmpresa
							.getDataLeitura()) : dataLeituraSugerida);
			form.setLeitura(movimentoRoteiroEmpresa.getNumeroLeitura() != null ? movimentoRoteiroEmpresa.getNumeroLeitura().toString() : "");
			form.setIdOcorrencia(movimentoRoteiroEmpresa.getLeituraAnormalidade() != null ? movimentoRoteiroEmpresa
							.getLeituraAnormalidade().getId().toString() : ConstantesSistema.NUMERO_NAO_INFORMADO + "");
			form.setReferenciaMovimento(Util.formatarAnoMesParaMesAno(movimentoRoteiroEmpresa.getAnoMesMovimento()));
		}
	}

	private void efetuarPaginacao(HttpServletRequest httpServletRequest, HttpSession sessao, InformarDadosLeituraAnormalidadeActionForm form){

		if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.PROXIMO_IMOVEL_ROTA) != null){
			Integer prox = (Integer) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.PROXIMO);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA,
							((Object[]) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA))[prox]);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.PROXIMO, (prox + 1));
			Integer anter = (Integer) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR, (anter + 1));
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.POSICAO_ROTA, (prox + 1));
			MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) sessao
							.getAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA);
			verificaFaturamentoRota(movimentoRoteiroEmpresa.getRota(), sessao, form, movimentoRoteiroEmpresa);
		}

		if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR_IMOVEL_ROTA) != null){
			Integer anter = (Integer) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA,
							((Object[]) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA))[anter]);
			Integer prox = (Integer) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.PROXIMO);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.PROXIMO, (prox - 1));
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR, (anter - 1));
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.POSICAO_ROTA, (anter + 1));
			MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) sessao
							.getAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA);
			verificaFaturamentoRota(movimentoRoteiroEmpresa.getRota(), sessao, form, movimentoRoteiroEmpresa);
		}
		popularOcorrencias(sessao);

	}

	private void popularOcorrencias(HttpSession sessao){

		if(sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.COLECAO_OCORRENCIA) == null){
			FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_USO,
							ConstantesSistema.SIM));
			filtroLeituraAnormalidade.setCampoOrderBy(FiltroLeituraAnormalidade.DESCRICAO);

			Collection colecaoOcorrencia = getFachada().pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class.getName());

			if(Util.isVazioOrNulo(colecaoOcorrencia)){
				levantarExcecaoUrlSetada(new ActionServletException("atencao.entidade.inexistente", null, "LEITURA_ANORMALIDADE"));
			}

			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.COLECAO_OCORRENCIA, colecaoOcorrencia);
		}
	}

	private void popularEmpresaLeiturista(HttpServletRequest httpServletRequest, InformarDadosLeituraAnormalidadeActionForm form,
					HttpSession sessao){

		if(sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.COLECAO_EMPRESA) == null){
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa(FiltroEmpresa.DESCRICAO_ABREVIADA);
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.SIM));

			Collection empresas = getFachada().pesquisar(filtroEmpresa, Empresa.class.getName());
			if(Util.isVazioOrNulo(empresas)){
				levantarExcecaoUrlSetada(new ActionServletException("atencao.entidade.inexistente", null, "EMPRESA"));
			}

			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.COLECAO_EMPRESA, empresas);
		}else if(Util.isVazioOuBranco(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.DESFAZER))){
			if(Util.isVazioOuBranco(form.getIdEmpresa())
							|| !form.getIdEmpresa().equals(ConstantesSistema.NUMERO_NAO_INFORMADO)
							&& !Util.isVazioOuBranco(httpServletRequest
											.getParameter(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_LEITURISTA))){
				FiltroLeiturista filtroLeiturista = new FiltroLeiturista();
				filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.FUNCIONARIO);
				filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.CLIENTE);
				filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.EMPRESA_ID, Integer.valueOf(form.getIdEmpresa())));
				filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.INDICADOR_USO, ConstantesSistema.SIM));

				Collection<Leiturista> leituristas = getFachada().pesquisar(filtroLeiturista, Leiturista.class.getName());

				if(Util.isVazioOrNulo(leituristas)){
					levantarExcecaoUrlSetada(new ActionServletException("atencao.nao_ha_leiturista_empresa"));
				}
				List<Leiturista> listaLeituristasOrdenado = new ArrayList<Leiturista>(leituristas);
				Collections.sort(listaLeituristasOrdenado, new Comparator<Leiturista>() {

					public int compare(Leiturista leiturista0, Leiturista leiturista1){

						int retorno = 0;
						if(leiturista0.getCliente() != null){
							if(leiturista1.getCliente() != null){
								retorno = leiturista0.getCliente().getNome().compareTo(leiturista1.getCliente().getNome());
							}else if(leiturista1.getFuncionario() != null){
								retorno = leiturista0.getCliente().getNome().compareTo(leiturista1.getFuncionario().getNome());
							}
						}else if(leiturista0.getFuncionario() != null){
							if(leiturista1.getCliente() != null){
								retorno = leiturista0.getFuncionario().getNome().compareTo(leiturista1.getCliente().getNome());
							}else if(leiturista1.getFuncionario() != null){
								retorno = leiturista0.getFuncionario().getNome().compareTo(leiturista1.getFuncionario().getNome());
							}
						}
						return retorno;
					}

				});

				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.COLECAO_LEITURISTA, listaLeituristasOrdenado);
				limparSessao(sessao);
			}

		}
	}

	private void limparSessao(HttpSession sessao){

		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.ROTAS, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.TOTAL_REGISTROS, 0);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_IMOVEL_ROTA, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.COLECAO_IMOVEIS_ROTA, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.POSICAO_ROTA, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.PROXIMO, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.QUANTIDADE_IMOVEIS_ROTA, null);
		sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.COLECAO_OCORRENCIA, null);
	}

	private void pesquisarRota(HttpServletRequest httpServletRequest, InformarDadosLeituraAnormalidadeActionForm form, HttpSession sessao){

		if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_ROTA) != null){
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.SIM));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.EMPRESA_ID, Integer.valueOf(form.getIdEmpresa())));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_LEITURISTA_ID, Integer.valueOf(form.getCodigoLeiturista())));
			filtroRota.setCampoOrderBy(FiltroRota.FATURAMENTO_GRUPO_ID, FiltroRota.SETOR_COMERCIAL_ID, FiltroRota.CODIGO_ROTA);

			Collection rotas = getFachada().pesquisar(filtroRota, Rota.class.getName());
			if(Util.isVazioOrNulo(rotas)){
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.ROTAS, null);
				levantarExcecaoUrlSetada(new ActionServletException("atencao.entidade.inexistente", null, "ROTA"));
			}else{
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.ROTAS, rotas);
			}
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.TOTAL_REGISTROS, rotas.size());
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA, null);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_IMOVEL_ROTA, null);
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA, null);
		}
	}

	private void pesquisarImovelRota(HttpServletRequest httpServletRequest, ActionForward actionForward,
					InformarDadosLeituraAnormalidadeActionForm form, HttpSession sessao){

		if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_IMOVEL_ROTA) != null){
			FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
			if(!Util.isVazioOuBranco(form.getReferenciaMovimento())){
				filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
								Integer.valueOf(Util.formatarMesAnoParaAnoMes(form.getReferenciaMovimento()))));
			}
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.SIM));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, Util.obterInteger(form.getIdRota())));

			Rota rota = (Rota) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroRota, Rota.class.getName()));
			if(rota == null){
				levantarExcecaoUrlSetada(new ActionServletException("atencao.pesquisa.rota_inexistente"));
			}else{
				filtroRota.limparListaParametros();
				filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, Util.obterInteger(form.getIdRota())));
				filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_LEITURISTA_ID, Util.obterInteger(form
								.getCodigoLeiturista())));
				rota = (Rota) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroRota, Rota.class.getName()));
				if(rota == null){
					FiltroLeiturista filtroLeiturista = new FiltroLeiturista();
					filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.FUNCIONARIO);
					filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.CLIENTE);
					filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.ID, Integer.valueOf(form
									.getCodigoLeiturista())));
					filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.INDICADOR_USO, ConstantesSistema.SIM));

					Leiturista leiturista = (Leiturista) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroLeiturista,
									Leiturista.class.getName()));
					levantarExcecaoUrlSetada(new ActionServletException("atencao.rota_leiturista_diferente_rota_selecionada",
									leiturista.getNomeLeiturista()));
				}
			}

			filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.EMPRESA_ID, Integer
							.valueOf(form.getIdEmpresa())));
			filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.LEITURISTA_ID, Integer
							.valueOf(form.getCodigoLeiturista())));
			filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ROTA_ID, rota.getId()));

			try{
				String ordenacaoMovimentoRoteiroEmpresaStr = ParametroMicromedicao.P_ORDENACAO_DIGITACAO_LEITURA.executar();

				String[] ordenacaoMovimentoRoteiroEmpresa = null;

				if(ordenacaoMovimentoRoteiroEmpresaStr.contains(",")){
					ordenacaoMovimentoRoteiroEmpresa = ordenacaoMovimentoRoteiroEmpresaStr.split(",");
				}else{
					ordenacaoMovimentoRoteiroEmpresa = new String[] {ordenacaoMovimentoRoteiroEmpresaStr};
				}

				filtroMovimentoRoteiroEmpresa.setCampoOrderBy(ordenacaoMovimentoRoteiroEmpresa);
			}catch(ControladorException e){
				throw new ActionServletException(e.getMessage(), e);
			}

			filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade(FiltroMovimentoRoteiroEmpresa.IMOVEL);
			filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade(FiltroMovimentoRoteiroEmpresa.HIDROMETRO);
			filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade(FiltroMovimentoRoteiroEmpresa.MEDICAO_TIPO);
			filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade(FiltroMovimentoRoteiroEmpresa.LEITURA_ANORMALIDADE);

			Collection colecaoImoveisRota = null;
			if(httpServletRequest.getParameter("indicadorEntradaDados") != null
							&& httpServletRequest.getParameter("indicadorEntradaDados").equals("3")){

				if(sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA) != null){
					Object[] listaImoveisRotasDaTela = (Object[]) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA);
					sessao.setAttribute("listaImoveisRotasDaTela", listaImoveisRotasDaTela);
				}

				Map resultado = controlarPaginacao(httpServletRequest, actionForward, filtroMovimentoRoteiroEmpresa, MovimentoRoteiroEmpresa.class.getName(), 200);
				
				if(resultado == null){
					ActionServletException actionServletException = new ActionServletException("atencao.nao_ha_dados_leitura_leiturista_rota");
					actionServletException.setUrlBotaoVoltar("/gsan/exibirInformarDadosLeituraAnormalidadeAction.do?manter=sim&menu=sim&indicadorEntradaDados=3");
					throw actionServletException;
				}

				colecaoImoveisRota = (Collection) resultado.get("colecaoRetorno");
				Map<Integer, LigacaoAgua> mapLigacaoAgua = new HashMap<Integer, LigacaoAgua>();
				Map<Integer, Hidrometro> mapHidrometro = new HashMap<Integer, Hidrometro>();
				for(Object object : colecaoImoveisRota){
					MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) object;
					LigacaoAgua ligacaoAgua = getFachada().pesquisarLigacaoAgua(movimentoRoteiroEmpresa.getImovel().getId());
					mapLigacaoAgua.put(movimentoRoteiroEmpresa.getId(), ligacaoAgua);
					Hidrometro hidrometro = null;
					if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null
									&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro() != null){
						hidrometro = getFachada().pesquisarHidrometroPeloId(
										ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro().getId());
					}

					mapHidrometro.put(movimentoRoteiroEmpresa.getId(), hidrometro);
				}
				sessao.setAttribute("mapLigacaoAgua", mapLigacaoAgua);
				sessao.setAttribute("mapHidrometro", mapHidrometro);

				if(httpServletRequest.getParameter("page.offset") != null && httpServletRequest.getParameter("page.offset").equals("1")){
					httpServletRequest.setAttribute("indicadorDesativaVoltar", ConstantesSistema.INATIVO);
				}else{
					httpServletRequest.setAttribute("indicadorDesativaVoltar", ConstantesSistema.ATIVO);
				}

				if(Util.isNaoNuloBrancoZero(colecaoImoveisRota) && colecaoImoveisRota.size() < 200){
					httpServletRequest.setAttribute("indicadorDesativaAvancar", ConstantesSistema.INATIVO);
				}else{
					httpServletRequest.setAttribute("indicadorDesativaAvancar", ConstantesSistema.ATIVO);
				}

			}else{
				colecaoImoveisRota = getFachada().pesquisar(filtroMovimentoRoteiroEmpresa, MovimentoRoteiroEmpresa.class.getName());
			}

			if(!Util.isVazioOrNulo(colecaoImoveisRota)){
				Object[] lista = colecaoImoveisRota.toArray();
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.COLECAO_IMOVEIS_ROTA, colecaoImoveisRota);
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA, lista);
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA, lista[0]);
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.POSICAO_ROTA, 1);
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.PROXIMO, 1);
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR, -1);
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.QUANTIDADE_IMOVEIS_ROTA, colecaoImoveisRota.size());
				popularOcorrencias(sessao);
				verificaFaturamentoRota(rota, sessao, form, (MovimentoRoteiroEmpresa) lista[0]);
			}else{
				levantarExcecaoUrlSetada(new ActionServletException("atencao.nao_ha_dados_leitura_leiturista_rota"));
			}
			sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_IMOVEL_ROTA,
							httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.PESQUISAR_IMOVEL_ROTA));

			if(sessao.getAttribute("listaImoveisRotasDadosDigitados") != null){
				Object[] listaImoveisRotasDadosDigitados = (Object[]) sessao.getAttribute("listaImoveisRotasDadosDigitados");
				sessao.setAttribute(InformarDadosLeituraAnormalidadeActionForm.LISTA_IMOVEIS_ROTA, listaImoveisRotasDadosDigitados);
				sessao.removeAttribute("listaImoveisRotasDadosDigitados");
			}
		}
	}

	private void verificaFaturamentoRota(Rota rota, HttpSession sessao, InformarDadosLeituraAnormalidadeActionForm form,
					MovimentoRoteiroEmpresa movimentoRoteiroEmpresa){

		if(MovimentoRoteiroEmpresa.FASE_PROCESSADO.equals(movimentoRoteiroEmpresa.getIndicadorFase())){
			sessao.setAttribute("verificaFaturamentoRota", 1);
		}else{
			sessao.setAttribute("verificaFaturamentoRota", null);
		}

	}

	private ActionForward atualizarDadosLeituraAnormalidade(HttpServletRequest httpServletRequest,
					InformarDadosLeituraAnormalidadeActionForm form, HttpSession sessao, ActionMapping actionMapping){

		if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.ATUALIZAR) != null
						|| (httpServletRequest.getParameter("confirmado") != null && httpServletRequest.getParameter("confirmado").equals(
										"ok"))){

			ActionForward retorno = actionMapping.findForward("telaSucesso");
			MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) sessao
							.getAttribute(InformarDadosLeituraAnormalidadeActionForm.IMOVEL_ROTA);

			Date data = Util.converteStringParaDate(form.getDataLeitura());

			String idOcorrencia = form.getIdOcorrencia();

			String leitura = form.getLeitura();

			if(data == null || httpServletRequest.getParameter("confirmado") != null){
				if(sessao.getAttribute("dataDataLeitura") != null){
					data = Util.converteStringParaDate((String) sessao.getAttribute("dataDataLeitura"));
				}
				if(data == null){
					levantarExcecaoUrlSetada(new ActionServletException("atencao.data_leitura_invalida"));
				}
			}

			if((Util.isVazioOuBranco(idOcorrencia) || idOcorrencia.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))
							|| httpServletRequest.getParameter("confirmado") != null){
				if(sessao.getAttribute("idOcorrencia") != null){
					idOcorrencia = (String) sessao.getAttribute("idOcorrencia");
				}
			}

			if((Util.isVazioOuBranco(leitura)) || httpServletRequest.getParameter("confirmado") != null){
				if(sessao.getAttribute("leitura") != null){
					leitura = (String) sessao.getAttribute("leitura");
				}
			}

			retorno = validaAtualizacao(httpServletRequest, form, sessao, actionMapping, movimentoRoteiroEmpresa, data);
			if(retorno.getName().equals("telaSucesso")){
				movimentoRoteiroEmpresa.setDataLeitura(data);
				movimentoRoteiroEmpresa.setDataProcessamento(new Date());
				movimentoRoteiroEmpresa.setUltimaAlteracao(new Date());
				movimentoRoteiroEmpresa.setNumeroLeitura(Integer.valueOf(leitura));
				if(!idOcorrencia.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
					LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
					leituraAnormalidade.setId(Integer.valueOf(idOcorrencia));
					movimentoRoteiroEmpresa.setLeituraAnormalidade(leituraAnormalidade);
				}
				movimentoRoteiroEmpresa.setIndicadorFase(ConstantesSistema.SIM);

				getFachada().atualizar(movimentoRoteiroEmpresa);

				sessao.setAttribute("dataDataLeitura", null);
				sessao.setAttribute("idOcorrencia", null);
				sessao.setAttribute("leitura", null);

				Integer prox = (Integer) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.PROXIMO);
				Integer qtd = (Integer) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.QUANTIDADE_IMOVEIS_ROTA);
				Integer anter = (Integer) sessao.getAttribute(InformarDadosLeituraAnormalidadeActionForm.ANTERIOR);

				montarPaginaSucesso(httpServletRequest, "Dados de leitura e/ou anormalidade do imóvel "
								+ movimentoRoteiroEmpresa.getImovel().getId() + " alterados com sucesso",
								"Aterar dados de leitura e/ou anormalidade de outra Rota?",
								"exibirInformarDadosLeituraAnormalidadeAction.do?menu=sim",
								(anter > -1 ? "exibirInformarDadosLeituraAnormalidadeAction.do?dataLeitura='" + form.getDataLeitura()
												+ "'&anteriorImovelRota=" + (anter + 1) : ""), (anter > -1 ? "Anterior" : ""),
								(prox < qtd ? "Próximo" : ""),
								(prox < qtd ? "exibirInformarDadosLeituraAnormalidadeAction.do?dataLeitura='" + form.getDataLeitura()
												+ "'&proximoImovelRota=" + (prox) : ""));
			}
			return retorno;

		}else if(httpServletRequest.getParameter(InformarDadosLeituraAnormalidadeActionForm.ATUALIZAR_DIGITACAO) != null){

			Object[] listaImoveisRota = null;
			if(sessao.getAttribute("listaImoveisRotasDaTela") != null){
				listaImoveisRota = (Object[]) sessao.getAttribute("listaImoveisRotasDaTela");
				sessao.removeAttribute("listaImoveisRotasDaTela");
			}else{
				listaImoveisRota = (Object[]) sessao.getAttribute("listaImoveisRota");
			}

			for(Object object : listaImoveisRota){
				MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) object;

				Date data = null;
				Integer dataInt = null;
				if(!Util.isVazioOuBranco(httpServletRequest.getParameter("dataLeitura" + movimentoRoteiroEmpresa.getId()))){
					data = Util.converteStringParaDate(httpServletRequest.getParameter("dataLeitura" + movimentoRoteiroEmpresa.getId()));
					dataInt = Integer.valueOf(Util.formatarDataAAAAMMDD(data));
				}

				String idOcorrencia = null;
				Integer idOcorrenciaInt = null;
				if(!Util.isVazioOuBranco(httpServletRequest.getParameter("ocorrencia" + movimentoRoteiroEmpresa.getId()))){
					idOcorrencia = httpServletRequest.getParameter("ocorrencia" + movimentoRoteiroEmpresa.getId());
					idOcorrenciaInt = Integer.valueOf(idOcorrencia);
				}

				String leitura = null;
				Integer leituraInt = null;
				if(!Util.isVazioOuBranco((httpServletRequest.getParameter("leitura" + movimentoRoteiroEmpresa.getId())))){
					leitura = httpServletRequest.getParameter("leitura" + movimentoRoteiroEmpresa.getId());
					leituraInt = Integer.valueOf(leitura);
				}

				Integer dataLeituraBaseInt = null;
				if(movimentoRoteiroEmpresa.getDataLeitura() != null){
					dataLeituraBaseInt = Integer.valueOf(Util.formatarDataAAAAMMDD(movimentoRoteiroEmpresa.getDataLeitura()));
				}
				Integer numeroLeituraBase = null;
				if(movimentoRoteiroEmpresa.getLeituraAnormalidade() != null){
					numeroLeituraBase = movimentoRoteiroEmpresa.getLeituraAnormalidade().getId();
				}
				Integer leituraAnormalidadeInt = null;
				if(movimentoRoteiroEmpresa.getLeituraAnormalidade() != null){
					leituraAnormalidadeInt = movimentoRoteiroEmpresa.getLeituraAnormalidade().getId();
				}
				if(dataLeituraBaseInt != dataInt || numeroLeituraBase != leituraInt || leituraAnormalidadeInt != idOcorrenciaInt){
					movimentoRoteiroEmpresa.setIndicadorFase(MovimentoRoteiroEmpresa.FASE_LEITURA_RETORNADA);
					movimentoRoteiroEmpresa.setDataLeitura(data);
					movimentoRoteiroEmpresa.setDataProcessamento(new Date());

					movimentoRoteiroEmpresa.setNumeroLeitura(leituraInt);
					if(idOcorrencia != null && !idOcorrencia.equals("")){
						LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
						leituraAnormalidade.setId(idOcorrenciaInt);
						movimentoRoteiroEmpresa.setLeituraAnormalidade(leituraAnormalidade);
					}
				}
			}

			for(Object object : listaImoveisRota){
				MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) object;

				FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
				filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ID,
								movimentoRoteiroEmpresa.getId()));
				MovimentoRoteiroEmpresa movimentoRoteiroEmpresaBase = (MovimentoRoteiroEmpresa) Util.retonarObjetoDeColecao(Fachada
								.getInstancia().pesquisar(filtroMovimentoRoteiroEmpresa, MovimentoRoteiroEmpresa.class.getName()));

				if(!movimentoRoteiroEmpresa.getUltimaAlteracao().equals(movimentoRoteiroEmpresaBase.getUltimaAlteracao())){
					continue;
				}else if(retornaStringParaComparacao(movimentoRoteiroEmpresa.getNumeroLeitura()).equals(
								retornaStringParaComparacao(movimentoRoteiroEmpresaBase.getNumeroLeitura()))
								&& retornaStringParaComparacao(movimentoRoteiroEmpresa.getDataLeituraFormatada()).equals(
												retornaStringParaComparacao(movimentoRoteiroEmpresaBase.getDataLeituraFormatada()))){

					String leituraAnormalidadeTela = "";
					String leituraAnormalidadeBase = "";

					if(movimentoRoteiroEmpresa.getLeituraAnormalidade() != null){
						leituraAnormalidadeTela = movimentoRoteiroEmpresa.getLeituraAnormalidade().getId().toString();
					}
					if(movimentoRoteiroEmpresaBase.getLeituraAnormalidade() != null){
						leituraAnormalidadeBase = movimentoRoteiroEmpresaBase.getLeituraAnormalidade().getId().toString();
					}
					if(leituraAnormalidadeTela.equals(leituraAnormalidadeBase)){
						continue;
					}

				}

				Date data = Util.converteStringParaDate(httpServletRequest.getParameter("dataLeitura" + movimentoRoteiroEmpresa.getId()));

				String idOcorrencia = httpServletRequest.getParameter("ocorrencia" + movimentoRoteiroEmpresa.getId());

				String leitura = httpServletRequest.getParameter("leitura" + movimentoRoteiroEmpresa.getId());

				if((Util.isNaoNuloBrancoZero(data) && Util.isVazioOuBranco(leitura))
								|| (Util.isVazioOuBranco(data) && Util.isNaoNuloBrancoZero(leitura))
								|| (Util.isNaoNuloBrancoZero(data) && (Util.isVazioOuBranco(data) || Util.isVazioOuBranco(leitura)))){
					sessao.setAttribute("listaImoveisRotasDadosDigitados", listaImoveisRota);
					ActionServletException actionServletException = null;
					if(Util.isVazioOuBranco(leitura)){
						actionServletException = new ActionServletException("atencao.campo.obrigatorio.verificar.registro", "Leitura",
										movimentoRoteiroEmpresa.getNumeroInscricao());
					}else{
						actionServletException = new ActionServletException("atencao.campo.obrigatorio.verificar.registro",
										"Data de Leitura", movimentoRoteiroEmpresa.getNumeroInscricao());
					}
					int pageOffSet = Integer.valueOf(httpServletRequest.getParameter("page.offsetAtual"));
					actionServletException
									.setUrlBotaoVoltar("/gsan/exibirInformarDadosLeituraAnormalidadeAction.do?indicadorEntradaDados=3&pesquisaImovelRota=1&page.offset="
													+ pageOffSet);
					throw actionServletException;

				}
				Map<Integer, LigacaoAgua> mapLigacaoAgua = (Map<Integer, LigacaoAgua>) sessao.getAttribute("mapLigacaoAgua");
				FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
				if(movimentoRoteiroEmpresa.getMedicaoTipo() == null || movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(0)){
					if(Util.isVazioOuBranco(form.getIdOcorrencia())){

						filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, Integer
										.valueOf(idOcorrencia)));

						LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) Util.retonarObjetoDeColecao(getFachada().pesquisar(
										filtroLeituraAnormalidade, LeituraAnormalidade.class.getName()));
						if(leituraAnormalidade != null
										&& leituraAnormalidade.getIndicadorImovelSemHidrometro().equals(ConstantesSistema.NAO)
										&& mapLigacaoAgua.get(movimentoRoteiroEmpresa.getId()).getHidrometroInstalacaoHistorico() == null
										&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() == null){
							int pageOffSet = Integer.valueOf(httpServletRequest.getParameter("page.offsetAtual"));
							ActionServletException actionServletException = new ActionServletException(
											"atencao.anormalidade_nao_permitida_imovel_sem_hidrometro");
							actionServletException
											.setUrlBotaoVoltar("/gsan/exibirInformarDadosLeituraAnormalidadeAction.do?indicadorEntradaDados=3&pesquisaImovelRota=1&page.offset="
															+ pageOffSet);
							throw actionServletException;
						}
					}
				}

				if(!Util.isVazioOuBranco(idOcorrencia) && !idOcorrencia.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					filtroLeituraAnormalidade.limparListaParametros();
					filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, Integer
									.valueOf(idOcorrencia)));

					LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) Util.retonarObjetoDeColecao(getFachada().pesquisar(
									filtroLeituraAnormalidade, LeituraAnormalidade.class.getName()));

					if(leituraAnormalidade == null){
						int pageOffSet = Integer.valueOf(httpServletRequest.getParameter("page.offsetAtual"));
						ActionServletException actionServletException = new ActionServletException("atencao.ocorencia_leitura_inexistente");
						actionServletException
										.setUrlBotaoVoltar("/gsan/exibirInformarDadosLeituraAnormalidadeAction.do?indicadorEntradaDados=3&pesquisaImovelRota=1&page.offset="
														+ pageOffSet);
						throw actionServletException;
					}

				}

			}

			for(Object object : listaImoveisRota){
				MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) object;

				FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
				filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ID,
								movimentoRoteiroEmpresa.getId()));
				MovimentoRoteiroEmpresa movimentoRoteiroEmpresaBase = (MovimentoRoteiroEmpresa) Util.retonarObjetoDeColecao(Fachada
								.getInstancia().pesquisar(filtroMovimentoRoteiroEmpresa, MovimentoRoteiroEmpresa.class.getName()));

				if(!movimentoRoteiroEmpresa.getUltimaAlteracao().equals(movimentoRoteiroEmpresaBase.getUltimaAlteracao())){
					continue;
				}else if(retornaStringParaComparacao(movimentoRoteiroEmpresa.getNumeroLeitura()).equals(
								retornaStringParaComparacao(movimentoRoteiroEmpresaBase.getNumeroLeitura()))
								&& retornaStringParaComparacao(movimentoRoteiroEmpresa.getDataLeituraFormatada()).equals(
												retornaStringParaComparacao(movimentoRoteiroEmpresaBase.getDataLeituraFormatada()))){

					String leituraAnormalidadeTela = "";
					String leituraAnormalidadeBase = "";

					if(movimentoRoteiroEmpresa.getLeituraAnormalidade() != null){
						leituraAnormalidadeTela = movimentoRoteiroEmpresa.getLeituraAnormalidade().getId().toString();
					}
					if(movimentoRoteiroEmpresaBase.getLeituraAnormalidade() != null){
						leituraAnormalidadeBase = movimentoRoteiroEmpresaBase.getLeituraAnormalidade().getId().toString();
					}
					if(leituraAnormalidadeTela.equals(leituraAnormalidadeBase)){
						continue;
					}

				}

				movimentoRoteiroEmpresa.setUltimaAlteracao(new Date());
				getFachada().atualizar(movimentoRoteiroEmpresa);
			}

			if(httpServletRequest.getParameter("finalizar") != null){
				montarPaginaSucesso(httpServletRequest, "Dados de Leitura e Anormalidade atualizados com sucesso.", "Voltar",
								"exibirInformarDadosLeituraAnormalidadeAction.do?manter=sim&menu=sim");
				return actionMapping.findForward("telaSucesso");
			}

		}
		return verificarRetorno(actionMapping, httpServletRequest);
	}

	/**
	 * @param httpServletRequest
	 * @param form
	 * @param sessao
	 * @param actionMapping
	 * @param movimentoRoteiroEmpresa
	 * @param data
	 * @return ActionForward
	 * @throws ActionServletException
	 * @throws NumberFormatException
	 */
	private ActionForward validaAtualizacao(HttpServletRequest httpServletRequest, InformarDadosLeituraAnormalidadeActionForm form,
					HttpSession sessao, ActionMapping actionMapping, MovimentoRoteiroEmpresa movimentoRoteiroEmpresa, Date data)
					throws ActionServletException, NumberFormatException{

		// [FS0004] Inicio

		if(data != null){

			if(data.after(new Date())){
				throw new ActionServletException("atencao.data_leitura_invalida");
			}else if(!Util.formataAnoMes(data).equals(movimentoRoteiroEmpresa.getAnoMesMovimento())
							&& !Util.formataAnoMes(data).equals(movimentoRoteiroEmpresa.getAnoMesMovimento() - 1)){
				if(httpServletRequest.getParameter("confirmado") == null){
					levantarExcecaoUrlSetada(new ActionServletException("atencao.data_leitura_incompativel_com_mesano_faturamento"));
				}
			}else if(movimentoRoteiroEmpresa.getDataLeituraAnterior() != null
							&& data.before(movimentoRoteiroEmpresa.getDataLeituraAnterior())){
				if(httpServletRequest.getParameter("confirmado") == null){
					httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/exibirInformarDadosLeituraAnormalidadeAction.do");
					httpServletRequest.setAttribute("cancelamento", "TRUE");
					httpServletRequest.setAttribute("nomeBotao1", "Sim");
					httpServletRequest.setAttribute("nomeBotao2", "Não");
					sessao.setAttribute("dataDataLeitura", form.getDataLeitura());
					sessao.setAttribute("idOcorrencia", form.getIdOcorrencia());
					sessao.setAttribute("leitura", form.getLeitura());
					return montarPaginaConfirmacao("atencao.data_leitura_inferior_data_leitura_anterior", httpServletRequest, actionMapping);
				}
			}

		}
		// [FS0004] Fim
		LigacaoAgua ligacaoAgua = getFachada().pesquisarLigacaoAgua(movimentoRoteiroEmpresa.getImovel().getId());
		// [FS0005] Inicio
		if(httpServletRequest.getParameter("confirmado") == null){

			if(movimentoRoteiroEmpresa.getMedicaoTipo() != null
							&& movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
				if(ligacaoAgua.getHidrometroInstalacaoHistorico() == null){
					levantarExcecaoUrlSetada(new ActionServletException("atencao.leitura_ligacao_agua_sem_hidrometro"));
				}
			}else if(movimentoRoteiroEmpresa.getMedicaoTipo() != null
							&& movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
				if(movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() == null){
					levantarExcecaoUrlSetada(new ActionServletException("atencao.leitura_poco_sem_hidrometro"));
				}
			}else if(movimentoRoteiroEmpresa.getMedicaoTipo() == null || movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(0)){
				if(!Util.isVazioOuBranco(form.getLeitura())){
					if(ligacaoAgua.getHidrometroInstalacaoHistorico() == null
									&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() == null){
						levantarExcecaoUrlSetada(new ActionServletException("atencao.leitura_imovel_sem_hidrometro"));
					}
				}
			}
		}
		// [FS0005] Fim

		// [FS0006] Inicio
		Hidrometro hidrometro = null;
		if(ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro() != null){
			hidrometro = getFachada().pesquisarHidrometroPeloId(ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro().getId());
		}

		if(httpServletRequest.getParameter("confirmado") == null){
			if(movimentoRoteiroEmpresa.getMedicaoTipo() != null
							&& movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
				if(ligacaoAgua.getHidrometroInstalacaoHistorico() != null
								&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro() != null
								&& !Util.isVazioOuBranco(form.getLeitura())
								&& hidrometro.getNumeroDigitosLeitura().intValue() < form.getLeitura().length()){
					httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/exibirInformarDadosLeituraAnormalidadeAction.do");
					httpServletRequest.setAttribute("cancelamento", "TRUE");
					httpServletRequest.setAttribute("nomeBotao1", "Sim");
					httpServletRequest.setAttribute("nomeBotao2", "Não");
					sessao.setAttribute("dataDataLeitura", form.getDataLeitura());
					sessao.setAttribute("idOcorrencia", form.getIdOcorrencia());
					sessao.setAttribute("leitura", form.getLeitura());
					return montarPaginaConfirmacao("atencao.quantidade_digitos_leitura_superior_numero_digitos_leitura_hidrometro_agua",
									httpServletRequest, actionMapping);
				}
			}else if(movimentoRoteiroEmpresa.getMedicaoTipo() != null
							&& movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
				if(movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() != null
								&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico().getHidrometro() != null
								&& Util.isVazioOuBranco(form.getLeitura())
								&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico().getHidrometro()
												.getNumeroDigitosLeitura().intValue() < form.getLeitura().length()){
					httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/exibirInformarDadosLeituraAnormalidadeAction.do");
					httpServletRequest.setAttribute("cancelamento", "TRUE");
					httpServletRequest.setAttribute("nomeBotao1", "Sim");
					httpServletRequest.setAttribute("nomeBotao2", "Não");
					sessao.setAttribute("dataDataLeitura", form.getDataLeitura());
					sessao.setAttribute("idOcorrencia", form.getIdOcorrencia());
					sessao.setAttribute("leitura", form.getLeitura());
					return montarPaginaConfirmacao("atencao.quantidade_digitos_leitura_superior_numero_digitos_leitura_hidrometro_poco",
									httpServletRequest, actionMapping);
				}
			}

		}

		// [FS0006] Fim

		// [FS0007] Inicio
		FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
		if(movimentoRoteiroEmpresa.getMedicaoTipo() == null || movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(0)){
			if(Util.isVazioOuBranco(form.getIdOcorrencia())){

				filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, Integer.valueOf(form
								.getIdOcorrencia())));

				LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) Util.retonarObjetoDeColecao(getFachada().pesquisar(
								filtroLeituraAnormalidade, LeituraAnormalidade.class.getName()));
				if(leituraAnormalidade != null && leituraAnormalidade.getIndicadorImovelSemHidrometro().equals(ConstantesSistema.NAO)
								&& ligacaoAgua.getHidrometroInstalacaoHistorico() == null
								&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() == null){
					levantarExcecaoUrlSetada(new ActionServletException("atencao.anormalidade_nao_permitida_imovel_sem_hidrometro"));
				}
			}
		}

		// [FS0007] Fim

		// [FS0008] Inicio

		if(!Util.isVazioOuBranco(form.getIdOcorrencia())
						&& !form.getIdOcorrencia().equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			filtroLeituraAnormalidade.limparListaParametros();
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, Integer.valueOf(form
							.getIdOcorrencia())));

			LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) Util.retonarObjetoDeColecao(getFachada().pesquisar(
							filtroLeituraAnormalidade, LeituraAnormalidade.class.getName()));

			if(leituraAnormalidade == null){
				levantarExcecaoUrlSetada(new ActionServletException("atencao.ocorencia_leitura_inexistente"));
			}

		}

		// [FS0008] Fim

		return actionMapping.findForward("telaSucesso");
	}

	private void validarArquivoUpload(FormFile arquivo){

		if(arquivo.getFileSize() == 0){
			levantarExcecaoUrlSetada(new ActionServletException("atencao.arquivo_vazio"));
		}

	}

	private File formFileToFile(FormFile formFile, HttpServletRequest httpServletRequest){

		File file = null;
		try{
			file = new File(formFile.getFileName());
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			InputStreamReader reader = new InputStreamReader(formFile.getInputStream());
			BufferedReader buffer = new BufferedReader(reader);
			String linha = null;
			FileOutputStream fout = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fout);
			while((linha = buffer.readLine()) != null){
				pw.println(linha);
			}

			buffer.close();
			reader.close();
			pw.flush();
			pw.close();

		}catch(FileNotFoundException e){

			levantarExcecaoUrlSetada(new ActionServletException("erro.leitura_arquivo"));

		}catch(IOException e){

			levantarExcecaoUrlSetada(new ActionServletException("erro.leitura_arquivo"));

		}
		return file;

	}

	private String retornaStringParaComparacao(Object object){

		String retorno = null;

		if(object == null){
			retorno = "";
		}else{
			retorno = object.toString();
		}
		return retorno;
	}
}
