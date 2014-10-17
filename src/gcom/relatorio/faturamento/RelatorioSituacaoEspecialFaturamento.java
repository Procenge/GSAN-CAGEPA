
package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.*;
import gcom.micromedicao.Rota;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

/**
 * [XYZ] Gerar Relatório Situação Especial de Faturamento
 * 
 * @author Hebert Falcão
 * @date 16/03/2014
 */
public class RelatorioSituacaoEspecialFaturamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioSituacaoEspecialFaturamento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_SITUACAO_ESPECIAL_FATURAMENTO);
	}

	@Deprecated
	public RelatorioSituacaoEspecialFaturamento() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		RelatorioSituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper = (RelatorioSituacaoEspecialFaturamentoHelper) getParametro("situacaoEspecialFaturamentoHelper");

		Collection<FaturamentoSituacaoHistorico> colecaoFaturamentoSituacaoHistorico = fachada
						.consultarSituacaoEspecialDeFaturamento(situacaoEspecialFaturamentoHelper);

		List<RelatorioSituacaoEspecialFaturamentoBean> relatorioBeans = new ArrayList<RelatorioSituacaoEspecialFaturamentoBean>();

		FaturamentoSituacaoTipo faturamentoSituacaoTipo = null;
		Integer idFaturamentoSituacaoTipo = null;
		String idFaturamentoSituacaoTipoStr = null;
		String descricaoFaturamentoSituacaoTipo = null;

		FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = null;
		Integer idFaturamentoSituacaoMotivo = null;
		String idFaturamentoSituacaoMotivoStr = null;
		String descricaoFaturamentoSituacaoMotivo = null;

		Integer anoMesFaturamentoSituacaoInicio = null;
		String anoMesFaturamentoSituacaoInicioStr = null;

		Integer anoMesFaturamentoSituacaoFim = null;
		String anoMesFaturamentoSituacaoFimStr = null;

		Imovel imovel = null;

		Localidade localidade = null;
		Integer idLocalidade = null;
		String idLocalidadeStr = null;
		String descricaoLocalidade = null;

		SetorComercial setorComercial = null;
		Integer codigoSetorComercial = null;
		String codigoSetorComercialStr = null;
		String descricaoSetorComercial = null;

		String inscricaoFormatada = null;

		Rota rota = null;
		Short codigoRota = null;
		String codigoRotaStr = null;

		Integer idImovel = null;
		String idImovelStr = null;

		String nomeCliente = null;

		String endereco = null;

		if(Util.isVazioOrNulo(colecaoFaturamentoSituacaoHistorico)){
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}else{
			RelatorioSituacaoEspecialFaturamentoBean relatorioBean = null;

			for(FaturamentoSituacaoHistorico faturamentoSituacaoHistorico : colecaoFaturamentoSituacaoHistorico){
				relatorioBean = new RelatorioSituacaoEspecialFaturamentoBean();

				faturamentoSituacaoTipo = faturamentoSituacaoHistorico.getFaturamentoSituacaoTipo();

				if(faturamentoSituacaoTipo != null){
					idFaturamentoSituacaoTipo = faturamentoSituacaoTipo.getId();
					idFaturamentoSituacaoTipoStr = Integer.toString(idFaturamentoSituacaoTipo);
					relatorioBean.setIdFaturamentoSituacaoTipo(idFaturamentoSituacaoTipoStr);

					descricaoFaturamentoSituacaoTipo = faturamentoSituacaoTipo.getDescricao();
					relatorioBean.setDescricaoFaturamentoSituacaoTipo(descricaoFaturamentoSituacaoTipo);
				}

				faturamentoSituacaoMotivo = faturamentoSituacaoHistorico.getFaturamentoSituacaoMotivo();

				if(faturamentoSituacaoMotivo != null){
					idFaturamentoSituacaoMotivo = faturamentoSituacaoMotivo.getId();
					idFaturamentoSituacaoMotivoStr = Integer.toString(idFaturamentoSituacaoMotivo);
					relatorioBean.setIdFaturamentoSituacaoMotivo(idFaturamentoSituacaoMotivoStr);

					descricaoFaturamentoSituacaoMotivo = faturamentoSituacaoMotivo.getDescricao();
					relatorioBean.setDescricaoFaturamentoSituacaoMotivo(descricaoFaturamentoSituacaoMotivo);
				}

				anoMesFaturamentoSituacaoInicio = faturamentoSituacaoHistorico.getAnoMesFaturamentoSituacaoInicio();

				if(anoMesFaturamentoSituacaoInicio != null){
					anoMesFaturamentoSituacaoInicioStr = Util.formatarMesAnoReferencia(anoMesFaturamentoSituacaoInicio);
					relatorioBean.setAnoMesFaturamentoSituacaoInicio(anoMesFaturamentoSituacaoInicioStr);
				}

				anoMesFaturamentoSituacaoFim = faturamentoSituacaoHistorico.getAnoMesFaturamentoSituacaoFim();

				if(anoMesFaturamentoSituacaoFim != null){
					anoMesFaturamentoSituacaoFimStr = Util.formatarMesAnoReferencia(anoMesFaturamentoSituacaoFim);
					relatorioBean.setAnoMesFaturamentoSituacaoFim(anoMesFaturamentoSituacaoFimStr);
				}

				imovel = faturamentoSituacaoHistorico.getImovel();

				if(imovel != null){
					localidade = imovel.getLocalidade();

					if(localidade != null){
						idLocalidade = localidade.getId();
						idLocalidadeStr = Integer.toString(idLocalidade);
						relatorioBean.setIdLocalidade(idLocalidadeStr);

						descricaoLocalidade = localidade.getDescricao();
						relatorioBean.setDescricaoLocalidade(descricaoLocalidade);
					}

					setorComercial = imovel.getSetorComercial();

					if(setorComercial != null){
						codigoSetorComercial = setorComercial.getCodigo();
						codigoSetorComercialStr = Integer.toString(codigoSetorComercial);
						relatorioBean.setCodigoSetorComercial(codigoSetorComercialStr);

						descricaoSetorComercial = setorComercial.getDescricao();
						relatorioBean.setDescricaoSetorComercial(descricaoSetorComercial);
					}

					inscricaoFormatada = imovel.getInscricaoFormatada();
					relatorioBean.setInscricao(inscricaoFormatada);

					rota = imovel.getRota();

					if(rota != null){
						codigoRota = rota.getCodigo();
						codigoRotaStr = Short.toString(codigoRota);
						relatorioBean.setCodigoRota(codigoRotaStr);
					}

					idImovel = imovel.getId();
					idImovelStr = Integer.toString(idImovel);
					relatorioBean.setIdImovel(idImovelStr);

					nomeCliente = fachada.consultarClienteUsuarioImovel(idImovel);
					relatorioBean.setNomeCliente(nomeCliente);

					endereco = fachada.pesquisarEndereco(idImovel);
					relatorioBean.setEndereco(endereco);
				}

				relatorioBeans.add(relatorioBean);
			}
		}

		Map<String, String> parametros = new HashMap<String, String>();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// Referência do Faturamento
		String pMesAnoFaturamento = "";
		Integer pAoMesFaturamento = situacaoEspecialFaturamentoHelper.getAnoMesFaturamento();

		if(pAoMesFaturamento != null){
			pMesAnoFaturamento = Util.formatarMesAnoReferencia(pAoMesFaturamento);
		}

		parametros.put("pMesAnoFaturamento", pMesAnoFaturamento);

		// Localidade
		String pLocalidade = "";
		Integer pIdLocalidade = situacaoEspecialFaturamentoHelper.getIdLocalidade();

		if(pIdLocalidade != null){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, pIdLocalidade));

			Collection<Localidade> colecaoLocalidadeAux = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(!Util.isVazioOrNulo(colecaoLocalidadeAux)){
				Localidade localidadeAux = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidadeAux);

				Integer idLocalidadeAux = localidadeAux.getId();
				String descricaoLocalidadeAux = localidadeAux.getDescricao();

				pLocalidade = idLocalidadeAux + " - " + descricaoLocalidadeAux;
			}
		}

		parametros.put("pLocalidade", pLocalidade);

		// Setor Comercial
		String pSetorComercial = "";
		Integer pCodigoSetorComercial = situacaoEspecialFaturamentoHelper.getCodigoSetorComercial();

		if(pIdLocalidade != null && pCodigoSetorComercial != null){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, pIdLocalidade));
			filtroSetorComercial
							.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, pCodigoSetorComercial));

			Collection<SetorComercial> colecaoSetorComercialAux = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(!Util.isVazioOrNulo(colecaoSetorComercialAux)){
				SetorComercial setorComercialAux = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercialAux);

				Integer codigoSetorComercialAux = setorComercialAux.getCodigo();
				String descricaoSetorComercialAux = setorComercialAux.getDescricao();

				pSetorComercial = codigoSetorComercialAux + " - " + descricaoSetorComercialAux;
			}
		}

		parametros.put("pSetorComercial", pSetorComercial);

		// Quadra
		String pQuadra = "";
		Integer pNumeroQuadra = situacaoEspecialFaturamentoHelper.getNumeroQuadra();

		if(pNumeroQuadra != null){
			pQuadra = Integer.toString(pNumeroQuadra);
		}

		parametros.put("pQuadra", pQuadra);

		// Rota
		String pRota = "";
		Short pCodigoRota = situacaoEspecialFaturamentoHelper.getCodigoRota();

		if(pCodigoRota != null){
			pRota = Short.toString(pCodigoRota);
		}

		parametros.put("pRota", pRota);

		// Tipo da Situação Especial de Faturamento
		String pFaturamentoSituacaoTipo = "";
		Integer pIdFaturamentoSituacaoTipo = situacaoEspecialFaturamentoHelper.getIdFaturamentoSituacaoTipo();

		if(pIdFaturamentoSituacaoTipo != null){
			FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo();
			filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoTipo.ID,
							pIdFaturamentoSituacaoTipo));

			Collection<FaturamentoSituacaoTipo> colecaoFaturamentoSituacaoTipoAux = fachada.pesquisar(filtroFaturamentoSituacaoTipo,
							FaturamentoSituacaoTipo.class.getName());

			if(!Util.isVazioOrNulo(colecaoFaturamentoSituacaoTipoAux)){
				FaturamentoSituacaoTipo faturamentoSituacaoTipoAux = (FaturamentoSituacaoTipo) Util
								.retonarObjetoDeColecao(colecaoFaturamentoSituacaoTipoAux);

				Integer idFaturamentoSituacaoTipoAux = faturamentoSituacaoTipoAux.getId();
				String descricaoFaturamentoSituacaoTipoAux = faturamentoSituacaoTipoAux.getDescricao();

				pFaturamentoSituacaoTipo = idFaturamentoSituacaoTipoAux + " - " + descricaoFaturamentoSituacaoTipoAux;
			}
		}

		parametros.put("pFaturamentoSituacaoTipo", pFaturamentoSituacaoTipo);

		// Motivo da Situação Especial de Faturamento
		String pFaturamentoSituacaoMotivo = "";
		Integer pIdFaturamentoSituacaoMotivo = situacaoEspecialFaturamentoHelper.getIdFaturamentoSituacaoMotivo();

		if(pIdFaturamentoSituacaoMotivo != null){
			FiltroFaturamentoSituacaoMotivo filtroFaturamentoSituacaoMotivo = new FiltroFaturamentoSituacaoMotivo();
			filtroFaturamentoSituacaoMotivo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoMotivo.ID,
							pIdFaturamentoSituacaoMotivo));

			Collection<FaturamentoSituacaoMotivo> colecaoFaturamentoSituacaoMotivoAux = fachada.pesquisar(filtroFaturamentoSituacaoMotivo,
							FaturamentoSituacaoMotivo.class.getName());

			if(!Util.isVazioOrNulo(colecaoFaturamentoSituacaoMotivoAux)){
				FaturamentoSituacaoMotivo faturamentoSituacaoMotivoAux = (FaturamentoSituacaoMotivo) Util
								.retonarObjetoDeColecao(colecaoFaturamentoSituacaoMotivoAux);

				Integer idFaturamentoSituacaoMotivoAux = faturamentoSituacaoMotivoAux.getId();
				String descricaoFaturamentoSituacaoMotivoAux = faturamentoSituacaoMotivoAux.getDescricao();

				pFaturamentoSituacaoMotivo = idFaturamentoSituacaoMotivoAux + " - " + descricaoFaturamentoSituacaoMotivoAux;
			}
		}

		parametros.put("pFaturamentoSituacaoMotivo", pFaturamentoSituacaoMotivo);

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		byte[] retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_SITUACAO_ESPECIAL_FATURAMENTO, parametros, ds,
						tipoFormatoRelatorio);

		try{
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_SITUACAO_ESPECIAL_DE_FATURAMENTO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		Fachada fachada = Fachada.getInstancia();

		RelatorioSituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper = (RelatorioSituacaoEspecialFaturamentoHelper) getParametro("situacaoEspecialFaturamentoHelper");

		return fachada.consultarSituacaoEspecialDeFaturamentoCount(situacaoEspecialFaturamentoHelper);
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioSituacaoEspecialFaturamento", this);

	}

}