
package gcom.gui.relatorio.cadastro.imovel;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Quadra;
import gcom.fachada.Fachada;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.FiltroConsumoAnormalidade;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.imovel.RelatorioMacromedidoresMicromedidosAssociadosUltimosConsumos;
import gcom.relatorio.cadastro.imovel.RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0XXX] Gerar Relatório de Macromedidores e Micromedidos associados com os últimos 36 consumos
 * ordem decrescente de referência.
 * 
 * @author Péricles Tavares
 * @date 17/02/2011
 */
public class ProcessarGerarRelatorioMacromedidoresMicromedidosAssociadosUltimosConsumosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// 4. Caso o usuário confirme o sistema gera o relatório de Logradouro Geral com os campos
		// abaixo,
		// classificado e quebrado por id da localidade.
		ActionForward retorno = null;
		GerarRelatorioMacromedidoresMicromedidosAssociadosUltimosConsumosActionForm formulario = (GerarRelatorioMacromedidoresMicromedidosAssociadosUltimosConsumosActionForm) actionForm;
		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		FiltroImovel filtroImovelCondominio = new FiltroImovel();
		filtroImovelCondominio.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_CONDOMINIO, ConstantesSistema.SIM));
		filtroImovelCondominio.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, ConstantesSistema.NAO));
		filtroImovelCondominio.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Integer.valueOf(formulario.getImovel())));
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		Fachada fachada = Fachada.getInstancia();
		// obtém as imoveis condominio
		Imovel imovelCondominio = (Imovel) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroImovelCondominio, Imovel.class.getName()));
		Collection<Imovel> colecaoImovelMicromedidores = null;

		FiltroImovel filtroImovelMicromedidores = null;
		FiltroClienteImovel filtroClienteImovelCondominio;
		FiltroClienteImovel filtroClienteImovelMicromedidor;
		ClienteImovel clienteImovelCondominio;
		ClienteImovel clienteImovelMicromedidor;
		FiltroMedicaoHistorico filtroMedicaoHistorico;
		FiltroLigacaoAgua filtroLigacaoAgua;
		ArrayList<MedicaoHistorico> medicoesHistorico = null;
		// obtem o usuário daquele imovel
		filtroClienteImovelCondominio = new FiltroClienteImovel();
		filtroClienteImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
		filtroClienteImovelCondominio.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovelCondominio.getId()));
		filtroClienteImovelCondominio.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
						ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO));
		clienteImovelCondominio = (ClienteImovel) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteImovelCondominio,
						ClienteImovel.class.getName()));
		// obtém todos os imoveis micromedidores
		filtroImovelMicromedidores = new FiltroImovel();
		filtroImovelMicromedidores.adicionarParametro(new ParametroSimples(FiltroImovel.IMOVEL_CONDOMINIO_ID, imovelCondominio.getId()));
		filtroImovelMicromedidores.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, ConstantesSistema.NAO));
		filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
		filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
		filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.MEDICAO_HISTORICOS);
		filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		colecaoImovelMicromedidores = fachada.pesquisar(filtroImovelMicromedidores, Imovel.class.getName());

		FiltroConsumoHistorico filtroConsumoHistorico;
		ArrayList<ConsumoHistorico> consumosHistoricos;
		MedicaoHistorico medicaoHistorico;
		ConsumoHistorico consumoHistorico;
		String sim = "Sim";
		if(colecaoImovelMicromedidores != null && !colecaoImovelMicromedidores.isEmpty()){
			for(Imovel imovelMicromedidores : colecaoImovelMicromedidores){
				filtroClienteImovelMicromedidor = new FiltroClienteImovel();
				filtroClienteImovelMicromedidor.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
				filtroClienteImovelMicromedidor.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovelMicromedidores
								.getId()));
				filtroClienteImovelMicromedidor.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
								ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO));
				clienteImovelMicromedidor = (ClienteImovel) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteImovelMicromedidor,
								ClienteImovel.class.getName()));
				RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean bean = new RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean();

				filtroMedicaoHistorico = new FiltroMedicaoHistorico(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO);
				filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeInformada");
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID, imovelMicromedidores
								.getId()));
				medicoesHistorico = new ArrayList<MedicaoHistorico>(fachada.pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class
								.getName()));
				filtroLigacaoAgua = new FiltroLigacaoAgua();
				filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, imovelMicromedidores.getId()));
				if(retornaIndicadorMedido(
								(LigacaoAgua) Util
												.retonarObjetoDeColecao(fachada.pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName())))
								.equals(sim)){
					for(int i = medicoesHistorico.size(); i > 0; i--){
						if(medicoesHistorico.size() - i >= 36){
							break;
						}
						medicaoHistorico = medicoesHistorico.get(i - 1);
						bean = new RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean();
						// Seta os valores do cabeçalho 1
						setarCabecalho1(imovelCondominio, colecaoImovelMicromedidores, clienteImovelCondominio, bean);
						// Seta os valores da Linha Detalhe 01 – Para cada Micro-medido associado:
						setaLinhaDetalhe1(fachada, clienteImovelMicromedidor, imovelMicromedidores, medicoesHistorico, bean);
						// Seta os valores da Linha Detalhe 02 – Para cada Micro-medido associado
						// deverão ser mostradas 36 últimas referências de consumo em ordem
						// decrescente de referência:
						bean.setReferencia(medicaoHistorico.getMesAno());
						if(medicaoHistorico.getLeituraAtualInformada() != null){
							bean.setLeitura(medicaoHistorico.getLeituraAtualInformada().toString());
						}
						bean.setDataLeitura(Util.formatarData(medicaoHistorico.getDataLeituraAtualInformada()));
						bean.setAnormalidadeConsumo(retornarAnormalidadeConsumo(fachada, medicaoHistorico, imovelMicromedidores, bean));
						bean.setAnormalidadeLeitura(retornarAnormalidadeLeitura(fachada, medicaoHistorico, imovelMicromedidores, bean, i));
						if(medicaoHistorico.getNumeroConsumoInformado() != null){
							bean.setQtidadeConsumo(medicaoHistorico.getNumeroConsumoInformado().toString());
						}
						if(medicaoHistorico.getDataLeituraAtualInformada() != null
										&& medicaoHistorico.getDataLeituraAnteriorFaturamento() != null){
							bean.setNumeroDiasConsumo(Util.obterQuantidadeDiasEntreDuasDatas(medicaoHistorico
											.getDataLeituraAnteriorFaturamento(), medicaoHistorico.getDataLeituraAtualInformada())
											+ "");
						}
						relatorioBeans.add(bean);
					}
				}else{
					filtroConsumoHistorico = new FiltroConsumoHistorico(FiltroConsumoHistorico.ANO_MES_FATURAMENTO);
					filtroConsumoHistorico.adicionarParametro(new ParametroSimples(filtroConsumoHistorico.IMOVEL_ID, imovelMicromedidores
									.getId()));
					consumosHistoricos = new ArrayList<ConsumoHistorico>(fachada.pesquisar(filtroConsumoHistorico, ConsumoHistorico.class
									.getName()));
					for(int i = medicoesHistorico.size(); i > 0; i--){
						if(consumosHistoricos.size() - i >= 36){
							break;
						}
						consumoHistorico = consumosHistoricos.get(i - 1);
						bean = new RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean();
						// Seta os valores do cabeçalho 1
						setarCabecalho1(imovelCondominio, colecaoImovelMicromedidores, clienteImovelCondominio, bean);
						// Seta os valores da Linha Detalhe 01 – Para cada Micro-medido associado:
						setaLinhaDetalhe1(fachada, clienteImovelMicromedidor, imovelMicromedidores, medicoesHistorico, bean);
						// Seta os valores da Linha Detalhe 02 – Para cada Micro-medido associado
						// deverão ser mostradas 36 últimas referências de consumo em ordem
						// decrescente de referência:
						bean.setReferencia(consumoHistorico.getMesAno());
						bean.setLeitura("0");
						bean.setDataLeitura("0");
						bean.setAnormalidadeConsumo(retornarAnormalidadeConsumoHistorico(fachada, consumoHistorico, imovelMicromedidores,
										bean, i));
						bean.setAnormalidadeLeitura("");
						if(consumoHistorico.getNumeroConsumoFaturadoMes() != null){
							bean.setQtidadeConsumo(consumoHistorico.getNumeroConsumoFaturadoMes().toString());
						}
						bean.setNumeroDiasConsumo("30");
						relatorioBeans.add(bean);
					}
				}

			}
		}
		RelatorioMacromedidoresMicromedidosAssociadosUltimosConsumos relatorio = new RelatorioMacromedidoresMicromedidosAssociadosUltimosConsumos(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorio.addParametro("relatorioBeans", relatorioBeans);
		relatorio.addParametro("totalRegistrosRelatorio", relatorioBeans.size());
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
		relatorio.addParametro("imovel", formulario.getImovel());
		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}

	private void setaLinhaDetalhe1(Fachada fachada, ClienteImovel clienteImovelMicromedidor, Imovel imovelMicromedidores,
					ArrayList<MedicaoHistorico> medicoesHistorico, RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean bean){

		FiltroLigacaoAgua filtroLigacaoAgua;
		bean.setMatriculaImovel(imovelMicromedidores.getId().toString());
		bean.setNumeroInscricao(imovelMicromedidores.getInscricaoFormatada());

		// Rota
		String codigoRotaStr = "";
		Rota rota = imovelMicromedidores.getRota();

		if(rota != null){
			Short codigoRota = rota.getCodigo();

			if(codigoRota != null){
				codigoRotaStr = Short.toString(codigoRota);
			}
		}

		bean.setCodigoRota(codigoRotaStr);

		// Segmento
		String numeroSegmentoStr = "";
		Short numeroSegmento = imovelMicromedidores.getNumeroSegmento();

		if(numeroSegmento != null){
			numeroSegmentoStr = Short.toString(numeroSegmento);
		}

		bean.setNumeroSegmento(numeroSegmentoStr);

		bean.setNomeClienteUsuario(clienteImovelMicromedidor.getCliente().getNome());
		bean.setSituacaoLigacaoAgua(imovelMicromedidores.getLigacaoAguaSituacao().getDescricaoComId());
		bean.setSituacaoLigacaoEsgoto(imovelMicromedidores.getLigacaoEsgotoSituacao().getDescricaoComId());
		filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, imovelMicromedidores.getId()));
		bean.setIndicadorMedido(retornaIndicadorMedido((LigacaoAgua) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLigacaoAgua,
						LigacaoAgua.class.getName()))));
		bean.setMedia(retornaUltimaMediaMedicaoHistorico(medicoesHistorico));
	}

	private void setarCabecalho1(Imovel imovelCondominio, Collection<Imovel> colecaoImovelMicromedidores,
					ClienteImovel clienteImovelCondominio, RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean bean){

		bean.setMacromedidor(imovelCondominio.getId().toString());
		bean.setNomeClienteMacromedidor(clienteImovelCondominio.getCliente().getNome());
		bean.setNumeroFilhosMacromedidor(colecaoImovelMicromedidores.size() + "");
		bean.setLocalidadeMacromedidor(imovelCondominio.getLocalidade().getDescricaoComId());
		bean.setSetorComercialMacromedidor(imovelCondominio.getSetorComercial().getDescricaoComId());

		// Rota
		String codigoRotaStr = "";
		Rota rota = imovelCondominio.getRota();

		if(rota != null){
			Short codigoRota = rota.getCodigo();

			if(codigoRota != null){
				codigoRotaStr = Short.toString(codigoRota);
			}
		}

		bean.setNumeroRotaMacromedidor(codigoRotaStr);

		// Quadra
		String numeroQuadraStr = "";
		Quadra quadra = imovelCondominio.getQuadra();

		if(quadra != null){
			int numeroQuadra = quadra.getNumeroQuadra();
			numeroQuadraStr = Integer.toString(numeroQuadra);
		}

		bean.setNumeroQuadraMacromedidor(numeroQuadraStr);

		if(imovelCondominio.getNumeroSegmento() != null){
			bean.setNumeroSegmentoMacromedidor(imovelCondominio.getNumeroSegmento().toString());
		}

		bean.setNumeroLoteMacromedidor(imovelCondominio.getLote() + "");
		bean.setNumeroSubLoteMacromedidor(imovelCondominio.getSubLote() + "");
	}

	private String retornarAnormalidadeConsumoHistorico(Fachada fachada, ConsumoHistorico consumoHistorico, Imovel imovelMicromedidores,
					RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean bean, int i){

		String retorno = "";
		ConsumoAnormalidade consumoAnormalidade;
		FiltroConsumoAnormalidade filtroConsumoAnormalidade;
		filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
		filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID, consumoHistorico.getId()));
		consumoAnormalidade = (ConsumoAnormalidade) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroConsumoAnormalidade,
						ConsumoAnormalidade.class.getName()));
		if(consumoAnormalidade != null){
			retorno = consumoAnormalidade.getDescricaoAbreviada();
		}
		return retorno;
	}

	private String retornarAnormalidadeLeitura(Fachada fachada, MedicaoHistorico medicaoHistorico, Imovel imovelMicromedidores,
					RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean bean, int i){

		String retorno = "";
		FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
		if(medicaoHistorico.getLeituraAnormalidadeInformada() != null){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, medicaoHistorico
							.getLeituraAnormalidadeInformada().getId()));
			retorno = ((LeituraAnormalidade) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLeituraAnormalidade,
							LeituraAnormalidade.class.getName()))).getDescricaoAbreviada();
		}
		return retorno;
	}

	private String retornarAnormalidadeConsumo(Fachada fachada, MedicaoHistorico medicaoHistorico, Imovel imovelMicromedidores,
					RelatorioMacromedidoresMicromedidosAssocidadosUltimosConsumosBean bean){

		String retorno = "";
		FiltroConsumoAnormalidade filtroConsumoAnormalidade;
		ConsumoAnormalidade consumoAnormalidade;
		FiltroConsumoHistorico filtroConsumoHistorico;
		ConsumoHistorico consumoHistorico;
		filtroConsumoHistorico = new FiltroConsumoHistorico();
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, imovelMicromedidores.getId()));
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.ANO_MES_FATURAMENTO, medicaoHistorico
						.getAnoMesReferencia()));
		if(imovelMicromedidores.getLigacaoAgua() != null && imovelMicromedidores.getLigacaoAgua().getId() != null
						&& imovelMicromedidores.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
						&& imovelMicromedidores.getLigacaoAgua().getHidrometroInstalacaoHistorico().getId() != null){
			filtroConsumoHistorico
							.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.LIGACAO_TIPO_ID, MedicaoTipo.LIGACAO_AGUA));
		}else{
			filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.LIGACAO_TIPO_ID, MedicaoTipo.POCO));
		}
		filtroConsumoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoHistorico.CONSUMO_ANORMALIDADE);

		consumoHistorico = (ConsumoHistorico) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroConsumoHistorico, ConsumoHistorico.class
						.getName()));
		if(consumoHistorico.getConsumoAnormalidade() != null){
			filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
			filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID, consumoHistorico
							.getConsumoAnormalidade().getId()));
			consumoAnormalidade = (ConsumoAnormalidade) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroConsumoAnormalidade,
							ConsumoAnormalidade.class.getName()));
			if(consumoAnormalidade != null){
				retorno = consumoAnormalidade.getDescricaoAbreviada();
			}
		}
		return retorno;
	}

	private String retornaIndicadorMedido(LigacaoAgua ligacaoAgua){

		String retorno;
		if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null){
			retorno = "Sim";
		}else{
			retorno = "Não";
		}
		return retorno;
	}

	private String retornaUltimaMediaMedicaoHistorico(Collection medicaoHistoricos){

		String retorno = "";
		if(medicaoHistoricos != null && !medicaoHistoricos.isEmpty()){
			Iterator imovelMedicaoHistoricoIterator = medicaoHistoricos.iterator();
			MedicaoHistorico medicaoHistoricoAuxiliar = new MedicaoHistorico();
			while(imovelMedicaoHistoricoIterator.hasNext()){
				MedicaoHistorico medicaoHistorico = (MedicaoHistorico) imovelMedicaoHistoricoIterator.next();
				if(medicaoHistorico.getAnoMesReferencia() > medicaoHistoricoAuxiliar.getAnoMesReferencia()){
					medicaoHistoricoAuxiliar = medicaoHistorico;
				}
			}
			if(medicaoHistoricoAuxiliar.getConsumoMedioHidrometro() != null){
				retorno = medicaoHistoricoAuxiliar.getConsumoMedioHidrometro().toString();
			}
		}
		return retorno;
	}
}
