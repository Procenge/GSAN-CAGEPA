
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.IRepositorioClienteImovel;
import gcom.cadastro.cliente.RepositorioClienteImovelHBM;
import gcom.cadastro.endereco.FiltroLogradouroBairro;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.bean.EmitirOrdemFiscalizacaoSupressaoCobrancaHelper;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * @author isilva
 * @date 21/09/2010
 */
public class GeradorDadosRelatorioOrdemFiscalizacaoSupressao
				extends GeradorDadosRelatorioOrdemServico {

	private static final long serialVersionUID = 1L;

	private IRepositorioClienteImovel repositorioClienteImovel = null;

	public GeradorDadosRelatorioOrdemFiscalizacaoSupressao() {

		super();
		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> collRelatorioOrdemCorteBean = new ArrayList<DadosRelatorioOrdemServico>();

		List<EmitirOrdemFiscalizacaoSupressaoCobrancaHelper> colecaoEmitirOrdemFiscalizacaoSupressaoCobrancaHelper = this
						.emitirOrdemFiscalizacaoSupressao(ordensServicos);
		int totalPaginas = colecaoEmitirOrdemFiscalizacaoSupressaoCobrancaHelper.size();

		List<EmitirOrdemFiscalizacaoSupressaoCobrancaHelper> colecaoOrdenada = (List<EmitirOrdemFiscalizacaoSupressaoCobrancaHelper>) this
						.dividirColecaoOrdenando(colecaoEmitirOrdemFiscalizacaoSupressaoCobrancaHelper);

		EmitirOrdemFiscalizacaoSupressaoCobrancaHelper primeiroHelper = null;
		EmitirOrdemFiscalizacaoSupressaoCobrancaHelper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(EmitirOrdemFiscalizacaoSupressaoCobrancaHelper helper : colecaoOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina("" + pagina1);
				pagina1++;
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina("" + pagina2);
				pagina2++;
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				collRelatorioOrdemCorteBean.add(new DadosRelatorioOrdemServico(primeiroHelper, segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collRelatorioOrdemCorteBean.add(new DadosRelatorioOrdemServico(primeiroHelper, segundoHelper));
		}

		return collRelatorioOrdemCorteBean;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Este caso de uso Gera os Relatórios das Ordens de Fiscalização de Supressão
	 * 
	 * @author isilva
	 * @data 21/09/2010
	 * @param listPesquisaOrdemServico
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public List<EmitirOrdemFiscalizacaoSupressaoCobrancaHelper> emitirOrdemFiscalizacaoSupressao(
					Collection<OrdemServico> listPesquisaOrdemServico){

		List<EmitirOrdemFiscalizacaoSupressaoCobrancaHelper> colecaoEmitirOrdemFiscalizacaoSupressaoCobrancaHelper = new ArrayList<EmitirOrdemFiscalizacaoSupressaoCobrancaHelper>();

		CobrancaAcao cobrancaAcao = null;

		if(listPesquisaOrdemServico != null && !listPesquisaOrdemServico.isEmpty()){

			OrdemServico ordem = (OrdemServico) Util.retonarObjetoDeColecao(listPesquisaOrdemServico);

			if(ordem.getCobrancaAcaoAtividadeCronograma() != null && ordem.getCobrancaAcaoAtividadeCronograma().getId() != null){
				if(ordem.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma() != null){
					cobrancaAcao = ordem.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma().getCobrancaAcao();
				}
			}
			if(ordem.getCobrancaAcaoAtividadeComando() != null && ordem.getCobrancaAcaoAtividadeComando().getId() != null){
				cobrancaAcao = ordem.getCobrancaAcaoAtividadeComando().getCobrancaAcao();
			}

			for(OrdemServico ordemServico : listPesquisaOrdemServico){

				EmitirOrdemFiscalizacaoSupressaoCobrancaHelper emitirOrdemFiscalizacaoSupressaoCobrancaHelper = new EmitirOrdemFiscalizacaoSupressaoCobrancaHelper();

				// ****************************************************
				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setMatricula(ordemServico.getImovel().getIdParametrizado());

				String nomeCliente = null;
				try{
					nomeCliente = repositorioClienteImovel.retornaNomeCliente(ordemServico.getImovel().getId(), ClienteRelacaoTipo.USUARIO);
				}catch(ErroRepositorioException ex){
					ex.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(ex.getMessage(), ex);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setNomeCliente(nomeCliente);

				String endereco = "";

				try{
					endereco = this.getControladorOrdemServico().obterEnderecoCompletoOS(ordemServico.getId());
				}catch(ControladorException e1){
					e1.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(e1.getMessage(), e1);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setEndereco(endereco);

				// RA
				if(ordemServico.getRegistroAtendimento() != null){
					emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setRa(ordemServico.getRegistroAtendimento().getId().toString());
				}else{
					emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setRa("");
				}

				// Número da OS
				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setNumero(ordemServico.getId().toString());

				// RA
				if(ordemServico.getServicoTipo() != null){
					emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setCodDescricaoServico(ordemServico.getServicoTipo().getId().toString()
									+ " / " + ordemServico.getServicoTipo().getDescricao());
				}else{
					emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setCodDescricaoServico("");
				}

				LogradouroBairro logradouroBairro = null;

				try{
					if(ordemServico.getImovel() != null && ordemServico.getImovel().getLogradouroBairro() != null){
						FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();
						filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID, ordemServico.getImovel()
										.getLogradouroBairro().getId()));
						filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouroBairro.BAIRRO);
						logradouroBairro = (LogradouroBairro) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
										filtroLogradouroBairro, LogradouroBairro.class.getName()));
					}

				}catch(ControladorException e1){
					e1.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(e1.getMessage(), e1);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if(logradouroBairro != null){
					emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setBairro(logradouroBairro.getBairro().getNome());
				}

				if(ordemServico.getImovel().getLigacaoAgua() != null
								&& ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){

					if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro() != null){

						emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setHidrometro(ordemServico.getImovel().getLigacaoAgua()
										.getHidrometroInstalacaoHistorico().getNumeroHidrometro());
					}else if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro() != null){

						emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setHidrometro(ordemServico.getImovel().getLigacaoAgua()
										.getHidrometroInstalacaoHistorico().getHidrometro().getNumero());
					}else{
						try{
							HidrometroInstalacaoHistorico hidi = (HidrometroInstalacaoHistorico) getControladorUtil().pesquisar(
											ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getId(),
											HidrometroInstalacaoHistorico.class, false);
							emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setHidrometro(hidi.getNumeroHidrometro());
						}catch(ControladorException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setHidrometro("");
				}

				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setInscricao(ordemServico.getImovel().getInscricaoFormatada());

				Calendar dataAtual = Calendar.getInstance();
				Integer horas = dataAtual.get(Calendar.HOUR_OF_DAY);
				Integer minutos = dataAtual.get(Calendar.MINUTE);
				Integer segundos = dataAtual.get(Calendar.SECOND);
				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setHoraImpressao("" + horas + ":" + minutos + ":" + segundos);
				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setDataImpressao(Util.formatarData(dataAtual.getTime()));

				if(cobrancaAcao != null && cobrancaAcao.getNumeroDiasValidade() != null){
					Util
									.adicionarNumeroDiasDeUmaData(dataAtual.getTime(), Integer
													.valueOf(cobrancaAcao.getNumeroDiasValidade()).intValue());
					emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setDataComparecimento(Util.formatarData(dataAtual.getTime()));
				}

				// obtendo o codigo de barras
				String representacaoNumericaCodBarra = ordemServico.getId().toString();
				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setRepresentacaoNumericaCodBarraFormatada(representacaoNumericaCodBarra);
				emitirOrdemFiscalizacaoSupressaoCobrancaHelper.setRepresentacaoNumericaCodBarraSemDigito(representacaoNumericaCodBarra);
				// **********************************************************

				colecaoEmitirOrdemFiscalizacaoSupressaoCobrancaHelper.add(emitirOrdemFiscalizacaoSupressaoCobrancaHelper);
				emitirOrdemFiscalizacaoSupressaoCobrancaHelper = null;
			}

		}
		return colecaoEmitirOrdemFiscalizacaoSupressaoCobrancaHelper;
	}
}
