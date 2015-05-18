package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * <p>
 * [OC1307416]
 * </p>
 * 
 * @author Magno Silveira (magno.silveira@procenge.com.br)
 * @since 12/10/2014
 */
public abstract class GeradorDadosRelatorioOrdemServicoBaseComSubrelatorios
				extends GeradorDadosRelatorioOrdemServico {

	public static final String TIPO_ASSINATURA_ENCARREGADO_MONITOR_CARRO = "1";

	public static final String TIPO_ASSINATURA_ENCARREGADO_MOTORISTA_CARROPLACA = "2";

	public static final String TIPO_ASSINATURA_ENCARREGADOMONITOR_CARRO = "3";

	public static final String TIPO_ASSINATURA_ENCARREGADO_MONITOR_CARROPLACA = "4";

	public GeradorDadosRelatorioOrdemServicoBaseComSubrelatorios() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.relatorio.ordemservico.GeradorDadosRelatorioOrdemServico#gerarDados(gcom.atendimentopublico
	 * .ordemservico.OrdemServico)
	 */
	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = new ArrayList<DadosRelatorioOrdemServico>();
		DadosRelatorioOrdemServico dadosRelatorio = new DadosRelatorioOrdemServico();

		dadosRelatorio.setNumeroOS(Util.adicionarZerosEsquedaNumero(ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO,
						os.getId().toString()));

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(sistemaParametro != null){
			addParametro("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		}

		Collection<Integer> colecaoIdOs = new ArrayList<Integer>();
		colecaoIdOs.add(os.getId());

		try{
			String[] dadosEmpresa = getControladorCadastro().obterDadosEmpresa();
			dadosRelatorio.setEnderecoEmpresa(dadosEmpresa[0]);
			dadosRelatorio.setTelefoneEmpresa(dadosEmpresa[2]);
			dadosRelatorio.setImagem(dadosEmpresa[3]);

			dadosRelatorio.setExibirRodapePadrao("true");

			Collection<OSRelatorioEstruturaHelper> colecaoOSRelatorioEstruturaHelper = getControladorOrdemServico()
							.pesquisarOSRelatorioEstruturaCabecalho(colecaoIdOs);

			if(!Util.isVazioOrNulo(colecaoOSRelatorioEstruturaHelper)){
				for(OSRelatorioEstruturaHelper helper : colecaoOSRelatorioEstruturaHelper){

					// Data da Geração
					String dataGeracao = "";
					String horaGeracao = "";
					if(helper.getDataGeracao() != null){
						dataGeracao = Util.formatarData(helper.getDataGeracao());
						horaGeracao = Util.formatarHoraSemData(helper.getDataGeracao());
					}
					// Em dados relatório não temos o atributo hora de geração, por isso usei esta
					dadosRelatorio.setHoraSolicitacao(horaGeracao);
					dadosRelatorio.setDataGeracao(dataGeracao);

					// Data e Hora da Solicitação
					String dataSolicitacao = "";
					if(helper.getDataHoraSolicitacao() != null){
						dataSolicitacao = Util.formatarData(helper.getDataHoraSolicitacao());
					}
					dadosRelatorio.setDataSolicitacao(dataSolicitacao);

					// Id Atendente
					String idAtendente = "";
					if(helper.getIdAtendente() != null){
						idAtendente = helper.getIdAtendente().toString();
					}
					dadosRelatorio.setIdAtendente(idAtendente);

					// Id da Solicitacao
					String idSolicitacao = "";
					if(helper.getIdSolicitacao() != null){
						idSolicitacao = helper.getIdSolicitacao().toString();
					}
					dadosRelatorio.setIdSolicitacao(idSolicitacao);

					// Quantidade Economias
					String categoriaQtdeEconomias = "";
					if(!Util.isVazioOuBranco(helper.getDescricaoCategoria())){
						categoriaQtdeEconomias = helper.getDescricaoAbreviadaCategoria() + "/" + helper.getQuantidadeEconomias();
						dadosRelatorio.setCategoriaQtdeEconomias(categoriaQtdeEconomias);
					}

					// Endereceo
					dadosRelatorio.setEndereco(helper.getEndereco());

					// Inscrição
					dadosRelatorio.setInscricaoImovel(helper.getInscricaoImovel());

					// Local ocorrencia
					dadosRelatorio.setLocalOcorrencia(helper.getEndereco());

					// Nome Atendente
					String nomeAtendente = "";
					if(helper.getNomeAtendente() != null){
						nomeAtendente = helper.getNomeAtendente();
					}
					dadosRelatorio.setNomeAtendente(nomeAtendente);

					// Matrícula do Imóvel
					String idImovel = "";
					if(helper.getIdImovel() != null){
						idImovel = Util.retornaMatriculaImovelParametrizada(helper.getIdImovel());
						dadosRelatorio.setIdImovel(idImovel);
					}

					// Recuperar número do hidrômetro
					String numeroHidrometro = "";
					if(!Util.isVazioOuBranco(idImovel)){

						Imovel imovel = fachada.pesquisarImovel(Integer.valueOf(idImovel));

						if(imovel != null && imovel.getLigacaoAgua() != null
										&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
										&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro() != null){

							// HIDI_ID da ligação de água
							numeroHidrometro = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro();

						}else if(imovel.getHidrometroInstalacaoHistorico() != null){

							// HIDI_ID do imovel
							numeroHidrometro = imovel.getHidrometroInstalacaoHistorico().getNumeroHidrometro();
						}
					}
					dadosRelatorio.setNumeroHidrometro(numeroHidrometro);

					// Obs OS
					dadosRelatorio.setObservacaoOS(helper.getObservacaoOS());

					// Ponto Referencia
					dadosRelatorio.setPontoReferencia(helper.getPontoReferencia());

					// Serviço Solicitado
					String servicoSolicitado = "";
					if(helper.getIdServicoSolicitado() != null){
						servicoSolicitado = helper.getIdServicoSolicitado() + " - " + helper.getDescricaoServicoSolicitado();
					}
					dadosRelatorio.setServicoSolicitado(servicoSolicitado);

					// Solicitante
					String solicitante = "";
					if(!Util.isVazioOuBranco(helper.getNomeSolicitante())){
						if(helper.getIdSolicitante() != null){
							solicitante += helper.getIdSolicitante().toString() + " - ";
						}

						solicitante += helper.getNomeSolicitante().trim();
					}
					dadosRelatorio.setSolicitante(solicitante);

					// Telefone
					dadosRelatorio.setTelefone(helper.getTelefone());

					gerarDetalhes(dadosRelatorio);
					validarExibicaoRodape(dadosRelatorio);
					validarTipoAssinaturaRodape(dadosRelatorio);

				}
			}

		}catch(ControladorException e){
			throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
		}

		dados.add(dadosRelatorio);

		return dados;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		return null;
	}

	public abstract void gerarDetalhes(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException;

	public abstract void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException;

	public abstract void validarTipoAssinaturaRodape(DadosRelatorioOrdemServico dadosRelatorio)
					throws GeradorRelatorioOrdemServicoException;

}
