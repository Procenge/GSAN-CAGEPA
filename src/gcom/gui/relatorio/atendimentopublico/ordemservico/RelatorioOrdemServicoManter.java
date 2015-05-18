package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import org.apache.commons.beanutils.BeanComparator;

public class RelatorioOrdemServicoManter
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioOrdemServicoManter(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_ORDEM_SERVICO);
	}

	@Deprecated
	public RelatorioOrdemServicoManter() {

		super(null, "");
	}


	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String ordenador = (String) getParametro("ordenador");
		Collection<OrdemServico> ordemServicoSelecionadaList = (Collection) getParametro("ordemServicoSelecionadaList");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioOrdemServicoManterBean relatorioBean = null;


		// se a coleção de parâmetros da analise não for vazia
		if(!Util.isVazioOrNulo(ordemServicoSelecionadaList)){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator it = ordemServicoSelecionadaList.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(it.hasNext()){

				OrdemServico ordemServico = (OrdemServico) it.next();


				String numeroOS = null;
				if(!Util.isVazioOuBranco(ordemServico.getId())){
					numeroOS = ordemServico.getId().toString();
				}
				String situacaoOS = null;
				if(!Util.isVazioOuBranco(ordemServico.getSituacao())){
					
					if(ordemServico.getSituacao() == OrdemServico.SITUACAO_PENDENTE){
						situacaoOS = OrdemServico.SITUACAO_DESCRICAO_PENDENTE;
					}
					if(ordemServico.getSituacao() == OrdemServico.SITUACAO_ENCERRADO){
						situacaoOS = OrdemServico.SITUACAO_DESCRICAO_ENCERRADO;
					}
					if(ordemServico.getSituacao() == OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO){
						situacaoOS = OrdemServico.SITUACAO_DESCRICAO_AGUARDANDO_LIBERACAO;
					}
					if(ordemServico.getSituacao() == OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO){
						situacaoOS = OrdemServico.SITUACAO_DESCRICAO_EXECUCAO_EM_ANDAMENTO;
					}
					
				}

				String dtGeracaoOs = null;
				if(!Util.isVazioOuBranco(ordemServico.getDataGeracao())){

					dtGeracaoOs = Util.formatarData(ordemServico.getDataGeracao());
				}

				String imovelId = null;
				if(!Util.isVazioOuBranco(ordemServico.getImovel())){
					imovelId = ordemServico.getImovel().getId().toString();
				}

				String unidadeAtual = null;
				if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento())){
					unidadeAtual = fachada.obterUnidadeAtualRA(ordemServico.getRegistroAtendimento().getId())
									.getDescricao();
				}else if(!Util.isVazioOuBranco(ordemServico)){
					unidadeAtual = fachada.obterUnidadeAtualOrdemServico(ordemServico.getId()).getDescricao();
				}


				String numeroRA = null;
				if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento())){
					numeroRA = ordemServico.getRegistroAtendimento().getId().toString();
				}
				
				String situacaoRA = null;
				if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento())){
					
					if(ordemServico.getRegistroAtendimento().getCodigoSituacao() == RegistroAtendimento.SITUACAO_BLOQUEADO){
						situacaoRA = RegistroAtendimento.SITUACAO_DESCRICAO_BLOQUEADO;
					}
					if(ordemServico.getRegistroAtendimento().getCodigoSituacao() == RegistroAtendimento.SITUACAO_ENCERRADO){
						situacaoRA = RegistroAtendimento.SITUACAO_DESCRICAO_ENCERRADO;
					}
					if(ordemServico.getRegistroAtendimento().getCodigoSituacao() == RegistroAtendimento.SITUACAO_PENDENTE){
						situacaoRA = RegistroAtendimento.SITUACAO_DESCRICAO_PENDENTE;
					}
				}

				String dtAberturaRa = null;
				if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento())){
					dtAberturaRa = Util.formatarData(ordemServico.getRegistroAtendimento().getRegistroAtendimento());
				}

				String documentoCobranca = null;
				if(!Util.isVazioOuBranco(ordemServico.getCobrancaDocumento())){
					documentoCobranca = ordemServico.getCobrancaDocumento().getId().toString();
				}

				String enderecoOcorrecia = null;
				if(!Util.isVazioOuBranco(ordemServico)){
					enderecoOcorrecia = fachada.obterEnderecoAbreviadoOS(ordemServico.getId());
				}

				String solicitante = null;
				String foneDDD = null;
				String fone = null;
				String foneRamal = null;
				if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento())){
					if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento().getRegistroAtendimentoSolicitantes())){
						FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();
						filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(
										FiltroRegistroAtendimentoSolicitante.REGISTRO_ATENDIMENTO_ID, ordemServico.getRegistroAtendimento().getId()));
						filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoSolicitante.CLIENTE);
						filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoSolicitante.UNIDADE_ORGANIZACIONAL);
						filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoSolicitante.SOLICITANTE_FONES);
						RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) Util
										.retonarObjetoDeColecao(fachada.pesquisar(filtroRegistroAtendimentoSolicitante,
														RegistroAtendimentoSolicitante.class.getName()));
						
						
						if(!Util.isVazioOuBranco(registroAtendimentoSolicitante)){
							if(!Util.isVazioOuBranco(registroAtendimentoSolicitante.getCliente())
											&& registroAtendimentoSolicitante.getIndicadorSolicitantePrincipal() == RegistroAtendimentoSolicitante.INDICADOR_PRINCIPAL){
								solicitante = registroAtendimentoSolicitante.getCliente().getNomeAbreviado();
							}else if(!Util.isVazioOuBranco(registroAtendimentoSolicitante.getUnidadeOrganizacional())){
								solicitante = registroAtendimentoSolicitante.getUnidadeOrganizacional().getId() +" - "+registroAtendimentoSolicitante.getUnidadeOrganizacional().getDescricao();
							}else{
								solicitante = registroAtendimentoSolicitante.getSolicitante();
							}
						

							FiltroSolicitanteFone filtroSolicitanteFone = new FiltroSolicitanteFone();
							filtroSolicitanteFone.adicionarParametro(new ParametroSimples(
											FiltroSolicitanteFone.REGISTRO_ATENDIMENTO_SOLICITANTE_ID, registroAtendimentoSolicitante.getID()));
							SolicitanteFone solicitanteFone = (SolicitanteFone) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSolicitanteFone, SolicitanteFone.class.getName()));

							if(!Util.isVazioOuBranco(solicitanteFone)){
								if(solicitanteFone.getIndicadorPadrao() == SolicitanteFone.INDICADOR_FONE_PADRAO){
									foneDDD = solicitanteFone.getDdd().toString();
									fone = solicitanteFone.getFone();
									foneRamal = solicitanteFone.getRamal();
								}

							}
						}
					}

				}

				
				String observacaoOS = null;
				if(!Util.isVazioOuBranco(ordemServico.getObservacao())){
					observacaoOS = ordemServico.getObservacao();
				}

				String observacaoRA = null;
				if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento())){
					if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento().getObservacao())){
						observacaoRA = ordemServico.getRegistroAtendimento().getObservacao();
					}
				}

				String total = Integer.toString(ordemServicoSelecionadaList.size());

				String tipoServico = null;
				if(!Util.isVazioOuBranco(ordemServico.getServicoTipo())){
					tipoServico = ordemServico.getServicoTipo().getDescricaoAbreviada();
				}

				relatorioBean = new RelatorioOrdemServicoManterBean(numeroOS, situacaoOS, dtGeracaoOs, imovelId, unidadeAtual, numeroRA,
								situacaoRA, dtAberturaRa, documentoCobranca, enderecoOcorrecia, solicitante, foneDDD, fone, foneRamal,
								observacaoOS, observacaoRA, total, tipoServico);

				relatorioBeans.add(relatorioBean);



			}


			if(ordenador != null){
				if(ordenador.equals("1")){
					BeanComparator comparador = new BeanComparator("imovelId");
					Collections.sort((List) relatorioBeans, comparador);
				}
				if(ordenador.equals("2")){
					BeanComparator comparador = new BeanComparator("enderecoOcorrecia");
					Collections.sort((List) relatorioBeans, comparador);

				}
				if(ordenador.equals("3")){
					BeanComparator comparador = new BeanComparator("solicitante");
					Collections.sort((List) relatorioBeans, comparador);

				}
				if(ordenador.equals("4")){
					BeanComparator comparador = new BeanComparator("dtGeracaoOs");
					Collections.sort((List) relatorioBeans, comparador);
				}
			}
		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// parametros.put("referencia", Util.formatarAnoMesParaMesAno(referencia));

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MANTER_ORDEM_SERVICO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MANTER_ORDEM_SERVICO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;


		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioOrdemServicoManter", this);
	}
}
