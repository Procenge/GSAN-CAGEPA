
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
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.imovel.RelatorioMicromedidores;
import gcom.relatorio.cadastro.imovel.RelatorioMicromedidoresBean;
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
 * [UC0XXX] Gerar Relatório Micromedidores
 * 
 * @author Péricles Tavares
 * @date 15/02/2011
 */
public class ProcessarGerarRelatorioMicromedidoresAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// 4. Caso o usuário confirme o sistema gera o relatório de Logradouro Geral com os campos
		// abaixo,
		// classificado e quebrado por id da localidade.
		ActionForward retorno = null;
		GerarRelatorioMicromedidoresActionForm formulario = (GerarRelatorioMicromedidoresActionForm) actionForm;
		List relatorioBeans = new ArrayList();
		FiltroImovel filtroImovelCondominio = new FiltroImovel(FiltroImovel.ID);
		filtroImovelCondominio.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_CONDOMINIO, ConstantesSistema.SIM));
		filtroImovelCondominio.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, ConstantesSistema.NAO));
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		filtroImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		Fachada fachada = Fachada.getInstancia();
		// obtém as imoveis condominio
		Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovelCondominio, Imovel.class.getName());
		Collection<Imovel> colecaoImovelMicromedidores = null;

		FiltroImovel filtroImovelMicromedidores = null;
		FiltroClienteImovel filtroClienteImovelCondominio;
		FiltroClienteImovel filtroClienteImovelMicromedidor;
		ClienteImovel clienteImovelCondominio;
		ClienteImovel clienteImovelMicromedidor;
		FiltroMedicaoHistorico filtroMedicaoHistorico;
		FiltroLigacaoAgua filtroLigacaoAgua;
		for(Imovel imovelCondominio : colecaoImovel){
			// obtem o usuário daquele imovel
			filtroClienteImovelCondominio = new FiltroClienteImovel();
			filtroClienteImovelCondominio.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovelCondominio.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovelCondominio.getId()));
			filtroClienteImovelCondominio.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
							ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO));
			clienteImovelCondominio = (ClienteImovel) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteImovelCondominio,
							ClienteImovel.class.getName()));
			// obtém todos os imoveis micromedidores
			filtroImovelMicromedidores = new FiltroImovel(FiltroImovel.ID);
			filtroImovelMicromedidores
							.adicionarParametro(new ParametroSimples(FiltroImovel.IMOVEL_CONDOMINIO_ID, imovelCondominio.getId()));
			filtroImovelMicromedidores.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO,
							ConstantesSistema.NAO));
			filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
			filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.MEDICAO_HISTORICOS);
			filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovelMicromedidores.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
			colecaoImovelMicromedidores = fachada.pesquisar(filtroImovelMicromedidores, Imovel.class.getName());

			if(colecaoImovelMicromedidores != null && !colecaoImovelMicromedidores.isEmpty()){
				for(Imovel imovelMicromedidores : colecaoImovelMicromedidores){
					filtroClienteImovelMicromedidor = new FiltroClienteImovel();
					filtroClienteImovelMicromedidor.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
					filtroClienteImovelMicromedidor.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID,
									imovelMicromedidores.getId()));
					filtroClienteImovelMicromedidor.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
									ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO));
					clienteImovelMicromedidor = (ClienteImovel) Util.retonarObjetoDeColecao(fachada.pesquisar(
									filtroClienteImovelMicromedidor, ClienteImovel.class.getName()));
					RelatorioMicromedidoresBean bean = new RelatorioMicromedidoresBean();
					// Seta os valores do cabeçalho
					bean.setMacromedidor(imovelCondominio.getId().toString());
					bean.setNomeClienteMacromedidor(clienteImovelCondominio.getCliente().getNome());
					bean.setNumeroFilhosMacromedidor(colecaoImovelMicromedidores.size() + "");
					bean.setLocalidadeMacromedidor(imovelCondominio.getLocalidade().getDescricaoComId());
					bean.setSetorComercialMacromedidor(imovelCondominio.getSetorComercial().getDescricaoComId());

					// Rota
					String codigoRotaMacromedidorStr = "";
					Rota rotaMacromedidor = imovelCondominio.getRota();

					if(rotaMacromedidor != null){
						Short codigoRotaMacromedidor = rotaMacromedidor.getCodigo();

						if(codigoRotaMacromedidor != null){
							codigoRotaMacromedidorStr = Short.toString(codigoRotaMacromedidor);
						}
					}

					bean.setNumeroRotaMacromedidor(codigoRotaMacromedidorStr);

					// Quadra
					String numeroQuadraMacromedidorStr = "";
					Quadra quadraMacromedidor = imovelCondominio.getQuadra();

					if(quadraMacromedidor != null){
						int numeroQuadraMacromedidor = quadraMacromedidor.getNumeroQuadra();
						numeroQuadraMacromedidorStr = Integer.toString(numeroQuadraMacromedidor);
					}

					bean.setNumeroQuadraMacromedidor(numeroQuadraMacromedidorStr);

					if(imovelCondominio.getNumeroSegmento() != null){
						bean.setNumeroSegmentoMacromedidor(imovelCondominio.getNumeroSegmento().toString());
					}

					bean.setNumeroLoteMacromedidor(imovelCondominio.getLote() + "");
					bean.setNumeroSubLoteMacromedidor(imovelCondominio.getSubLote() + "");
					// Seta os valores do detalhe
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
					filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico");

					LigacaoAgua la = (LigacaoAgua) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLigacaoAgua, LigacaoAgua.class
									.getName()));

					bean.setIndicadorMedido(retornaIndicadorMedido(la));

					filtroMedicaoHistorico = new FiltroMedicaoHistorico();
					filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID,
									imovelMicromedidores.getId()));
					bean.setMedia(retornaUltimaMediaMedicaoHistorico(fachada.pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class
									.getName())));
					relatorioBeans.add(bean);
				}
			}
		}
		RelatorioMicromedidores relatorio = new RelatorioMicromedidores((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
		relatorio.addParametro("totalRegistrosRelatorio", relatorioBeans.size());
		relatorio.addParametro("relatorioBeans", relatorioBeans);
		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}

	private String retornaIndicadorMedido(LigacaoAgua ligacaoAgua){

		String retorno = null;

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
