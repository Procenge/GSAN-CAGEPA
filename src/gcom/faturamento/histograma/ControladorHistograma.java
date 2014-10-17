
package gcom.faturamento.histograma;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.faturamento.HistogramaAguaEconomia;
import gcom.faturamento.HistogramaAguaLigacao;
import gcom.faturamento.HistogramaEsgotoEconomia;
import gcom.faturamento.HistogramaEsgotoLigacao;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaCategoria;
import gcom.gerencial.cadastro.IRepositorioGerencialCadastro;
import gcom.gerencial.cadastro.RepositorioGerencialCadastroHBM;
import gcom.gerencial.cadastro.bean.HistogramaAguaEconomiaHelper;
import gcom.gerencial.cadastro.bean.HistogramaAguaLigacaoHelper;
import gcom.gerencial.cadastro.bean.HistogramaEsgotoEconomiaHelper;
import gcom.gerencial.cadastro.bean.HistogramaEsgotoLigacaoHelper;
import gcom.util.*;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.log4j.Logger;

/**
 * Classe responsável por realizar operações relativas a Histogramas de Água e Esgoto
 * 
 * @author ebandeira
 * @date 19/08/2009
 */
public class ControladorHistograma {

	private IRepositorioUtil repositorioUtil = null;

	private IRepositorioHistograma repositorioHistograma = null;

	private IRepositorioLocalidade repositorioLocalidade = null;

	private IRepositorioQuadra repositorioQuadra = null;

	private static final Logger LOG = Logger.getLogger(ControladorHistograma.class);

	private static Class lock = ControladorHistograma.class;

	private IRepositorioGerencialCadastro repositorioGerencialCadastro = null;

	private static ControladorHistograma instancia = null;

	/**
	 * Retorna o valor da instance da Classe
	 * 
	 * @return instance
	 */
	public static ControladorHistograma getInstancia(){

		synchronized(lock){
			if(instancia == null){
				instancia = new ControladorHistograma();
			}
			return instancia;
		}
	}

	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas ï¿½
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorHistograma() {

		repositorioUtil = RepositorioUtilHBM.getInstancia();
		repositorioHistograma = RepositorioHistogramaHBM.getInstancia();
		repositorioGerencialCadastro = RepositorioGerencialCadastroHBM.getInstancia();
		repositorioLocalidade = RepositorioLocalidadeHBM.getInstancia();
		repositorioQuadra = RepositorioQuadraHBM.getInstancia();
	}

	/**
	 * Gerar Histograma de Água e Esgoto
	 * [UC0566] - Fluxo Principal
	 * 
	 * @author Virginia,Thiago Toscano
	 * @date 24/08/2009
	 * @param colecaoConta
	 *            - Coleção de conta
	 */
	public void gerarHistogramaAguaEsgoto(Collection<Conta> colecaoConta, Short funcaoExecutada) throws ControladorException{

		try{

			synchronized(lock){

				List<HistogramaAguaLigacaoHelper> listaSimplificadaAguaLigacao = new ArrayList<HistogramaAguaLigacaoHelper>();
				List<HistogramaAguaEconomiaHelper> listaSimplificadaAguaEconomia = new ArrayList<HistogramaAguaEconomiaHelper>();
				List<HistogramaEsgotoLigacaoHelper> listaSimplificadaEsgotoLigacao = new ArrayList<HistogramaEsgotoLigacaoHelper>();
				List<HistogramaEsgotoEconomiaHelper> listaSimplificadaEsgotoEconomia = new ArrayList<HistogramaEsgotoEconomiaHelper>();

				for(Conta conta : colecaoConta){

					if(conta != null){
						// obtem os dados de localizacao populados
						Localidade localidadeConta = repositorioLocalidade.obterLocalidade(conta.getLocalidade().getId());
						conta.setLocalidade(localidadeConta);

						Collection<ContaCategoria> colecaoContaCategoria = conta.getContaCategorias();

						if((colecaoContaCategoria != null && !colecaoContaCategoria.isEmpty())){

							Object[] retorno = gerarHistograma(conta, colecaoContaCategoria, funcaoExecutada);

							if(retorno[0] != null && retorno[0] instanceof HistogramaAguaLigacaoHelper){

								HistogramaAguaLigacaoHelper helper = (HistogramaAguaLigacaoHelper) retorno[0];

								if(listaSimplificadaAguaLigacao.contains(helper)){

									int posicao = listaSimplificadaAguaLigacao.indexOf(helper);
									HistogramaAguaLigacaoHelper jaCadastrado = (HistogramaAguaLigacaoHelper) listaSimplificadaAguaLigacao
													.get(posicao);

									if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){

										jaCadastrado.setQuantidadeLigacao(jaCadastrado.getQuantidadeLigacao()
														+ helper.getQuantidadeLigacao());
										jaCadastrado.setQuantidadeEconomiaLigacao(jaCadastrado.getQuantidadeEconomiaLigacao()
														+ helper.getQuantidadeEconomiaLigacao());
										jaCadastrado.setValorFaturadoLigacao(jaCadastrado.getValorFaturadoLigacao().add(
														helper.getValorFaturadoLigacao()));
										jaCadastrado.setVolumeFaturadoLigacao(jaCadastrado.getVolumeFaturadoLigacao()
														+ helper.getVolumeFaturadoLigacao());



									}else{

										jaCadastrado.setQuantidadeLigacaoRefaturamento(jaCadastrado.getQuantidadeLigacaoRefaturamento()
														+ helper.getQuantidadeLigacaoRefaturamento());
										jaCadastrado.setQuantidadeEconomiaLigacaoRefaturamento(jaCadastrado
														.getQuantidadeEconomiaLigacaoRefaturamento()
														+ helper.getQuantidadeEconomiaLigacaoRefaturamento());
										jaCadastrado.setValorFaturadoLigacaoRefaturamento(jaCadastrado
														.getValorFaturadoLigacaoRefaturamento().add(
																		helper.getValorFaturadoLigacaoRefaturamento()));
										jaCadastrado.setVolumeFaturadoLigacaoRefaturamento(jaCadastrado
														.getVolumeFaturadoLigacaoRefaturamento()
														+ helper.getVolumeFaturadoLigacaoRefaturamento());
									}

								}else{

									listaSimplificadaAguaLigacao.add(helper);
								}
							}

							if(retorno[1] != null && retorno[1] instanceof HistogramaAguaEconomiaHelper){
								HistogramaAguaEconomiaHelper helper = (HistogramaAguaEconomiaHelper) retorno[1];

								if(listaSimplificadaAguaEconomia.contains(helper)){
									int posicao = listaSimplificadaAguaEconomia.indexOf(helper);

									HistogramaAguaEconomiaHelper jaCadastrado = (HistogramaAguaEconomiaHelper) listaSimplificadaAguaEconomia
													.get(posicao);

									jaCadastrado.setQuantidadeEconomia(jaCadastrado.getQuantidadeEconomia()
													+ helper.getQuantidadeEconomia());
									jaCadastrado.setValorFaturadoEconomia(jaCadastrado.getValorFaturadoEconomia().add(
													helper.getValorFaturadoEconomia()));
									jaCadastrado.setVolumeFaturadoEconomia(jaCadastrado.getVolumeFaturadoEconomia()
													+ helper.getVolumeFaturadoEconomia());
									jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes()
													+ helper.getQuantidadeLigacoes());


								}else{

									listaSimplificadaAguaEconomia.add(helper);
								}
							}

							if(retorno[2] != null && retorno[2] instanceof HistogramaEsgotoLigacaoHelper){
								HistogramaEsgotoLigacaoHelper helper = (HistogramaEsgotoLigacaoHelper) retorno[2];

								if(listaSimplificadaEsgotoLigacao.contains(helper)){

									int posicao = listaSimplificadaEsgotoLigacao.indexOf(helper);
									HistogramaEsgotoLigacaoHelper jaCadastrado = (HistogramaEsgotoLigacaoHelper) listaSimplificadaEsgotoLigacao
													.get(posicao);

									jaCadastrado.setQuantidadeLigacao(jaCadastrado.getQuantidadeLigacao() + helper.getQuantidadeLigacao());
									jaCadastrado.setQuantidadeEconomiaLigacao(jaCadastrado.getQuantidadeEconomiaLigacao()
													+ helper.getQuantidadeEconomiaLigacao());
									jaCadastrado.setValorFaturadoLigacao(jaCadastrado.getValorFaturadoLigacao().add(
													helper.getValorFaturadoLigacao()));
									jaCadastrado.setVolumeFaturadoLigacao(jaCadastrado.getVolumeFaturadoLigacao()
													+ helper.getVolumeFaturadoLigacao());


								}else{
									listaSimplificadaEsgotoLigacao.add(helper);
								}
							}

							if(retorno[3] != null && retorno[3] instanceof HistogramaEsgotoEconomiaHelper){

								HistogramaEsgotoEconomiaHelper helper = (HistogramaEsgotoEconomiaHelper) retorno[3];

								if(listaSimplificadaEsgotoEconomia.contains(helper)){

									int posicao = listaSimplificadaEsgotoEconomia.indexOf(helper);
									HistogramaEsgotoEconomiaHelper jaCadastrado = (HistogramaEsgotoEconomiaHelper) listaSimplificadaEsgotoEconomia
													.get(posicao);

									jaCadastrado.setQuantidadeEconomia(jaCadastrado.getQuantidadeEconomia()
													+ helper.getQuantidadeEconomia());
									jaCadastrado.setValorFaturadoEconomia(jaCadastrado.getValorFaturadoEconomia().add(
													helper.getValorFaturadoEconomia()));
									jaCadastrado.setVolumeFaturadoEconomia(jaCadastrado.getVolumeFaturadoEconomia()
													+ helper.getVolumeFaturadoEconomia());
									jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes()
													+ helper.getQuantidadeLigacoes());
									



								}else{
									listaSimplificadaEsgotoEconomia.add(helper);
								}
							}
						}
					}
				}

				gerarResumoHistogramaAguaLigacao(listaSimplificadaAguaLigacao, funcaoExecutada);

				gerarResumoHistogramaAguaEconomia(listaSimplificadaAguaEconomia, funcaoExecutada);

				gerarResumoHistogramaEsgotoLigacao(listaSimplificadaEsgotoLigacao, funcaoExecutada);

				gerarResumoHistogramaEsgotoEconomia(listaSimplificadaEsgotoEconomia, funcaoExecutada);
			}
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * @author eduardo henrique
	 * @date 31/08/2009
	 * @param listaSimplificadaEsgotoEconomia
	 * @param adicao
	 * @throws ErroRepositorioException
	 */
	private void gerarResumoHistogramaEsgotoEconomia(List<HistogramaEsgotoEconomiaHelper> listaSimplificadaEsgotoEconomia,
					Short funcaoExecutada) throws ErroRepositorioException{

		if(listaSimplificadaEsgotoEconomia == null){
			throw new IllegalArgumentException("erro.histograma_esgoto_economia_lista_nula");
		}

		for(HistogramaEsgotoEconomiaHelper helperHistogramaEsgotoEconomia : listaSimplificadaEsgotoEconomia){

			HistogramaEsgotoEconomia histogramaEsgotoEconomia = null;
			histogramaEsgotoEconomia = this.repositorioHistograma.pesquisarHistogramaEsgotoEconomia(helperHistogramaEsgotoEconomia);

			if(histogramaEsgotoEconomia == null){

				HistogramaEsgotoEconomia hist = helperHistogramaEsgotoEconomia.gerarHistogramaEsgotoEconomia();

				// só adiciona se for pra adicionar. se for pra subtrair e nao existir nao adiciona
				if(!funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO)){
					repositorioUtil.inserir(hist);
				}

			}else{

				if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){

					histogramaEsgotoEconomia.setQuantidadeLigacao(histogramaEsgotoEconomia.getQuantidadeLigacao()
									+ helperHistogramaEsgotoEconomia.getQuantidadeLigacoes());
					histogramaEsgotoEconomia.setQuantidadeEconomia(histogramaEsgotoEconomia.getQuantidadeEconomia()
									+ helperHistogramaEsgotoEconomia.getQuantidadeEconomia());
					histogramaEsgotoEconomia.setValorFaturadoEconomia(histogramaEsgotoEconomia.getValorFaturadoEconomia().add(
									helperHistogramaEsgotoEconomia.getValorFaturadoEconomia()));
					histogramaEsgotoEconomia.setVolumeFaturadoEconomia(histogramaEsgotoEconomia.getVolumeFaturadoEconomia()
									+ helperHistogramaEsgotoEconomia.getVolumeFaturadoEconomia());




				}else if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO)){

					histogramaEsgotoEconomia.setQuantidadeLigacaoRefaturamento(histogramaEsgotoEconomia.getQuantidadeLigacaoRefaturamento()
									- helperHistogramaEsgotoEconomia.getQuantidadeLigacoesRefaturamento());
					histogramaEsgotoEconomia.setQuantidadeEconomiaRefaturamento(histogramaEsgotoEconomia
									.getQuantidadeEconomiaRefaturamento()
									- helperHistogramaEsgotoEconomia.getQuantidadeEconomiaRefaturamento());
					histogramaEsgotoEconomia.setValorFaturadoEconomiaRefaturamento(histogramaEsgotoEconomia
									.getValorFaturadoEconomiaRefaturamento().subtract(
													helperHistogramaEsgotoEconomia.getValorFaturadoEconomiaRefaturamento()));
					histogramaEsgotoEconomia.setVolumeFaturadoEconomiaRefaturamento(histogramaEsgotoEconomia
									.getVolumeFaturadoEconomiaRefaturamento()
									- helperHistogramaEsgotoEconomia.getVolumeFaturadoEconomiaRefaturamento());


				}else{

					histogramaEsgotoEconomia.setQuantidadeLigacaoRefaturamento(histogramaEsgotoEconomia.getQuantidadeLigacaoRefaturamento()
									+ helperHistogramaEsgotoEconomia.getQuantidadeLigacoesRefaturamento());
					histogramaEsgotoEconomia.setQuantidadeEconomiaRefaturamento(histogramaEsgotoEconomia
									.getQuantidadeEconomiaRefaturamento()
									+ helperHistogramaEsgotoEconomia.getQuantidadeEconomiaRefaturamento());
					histogramaEsgotoEconomia.setValorFaturadoEconomiaRefaturamento(histogramaEsgotoEconomia
									.getValorFaturadoEconomiaRefaturamento().add(
													helperHistogramaEsgotoEconomia.getValorFaturadoEconomiaRefaturamento()));
					histogramaEsgotoEconomia.setVolumeFaturadoEconomiaRefaturamento(histogramaEsgotoEconomia
									.getVolumeFaturadoEconomiaRefaturamento()
									+ helperHistogramaEsgotoEconomia.getVolumeFaturadoEconomiaRefaturamento());

				}

				histogramaEsgotoEconomia.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(histogramaEsgotoEconomia);
			}
		}
	}

	/**
	 * @author eduardo henrique
	 * @date 31/08/2009
	 * @param listaSimplificadaEsgotoLigacao
	 * @param adicao
	 * @throws ErroRepositorioException
	 */
	private void gerarResumoHistogramaEsgotoLigacao(List<HistogramaEsgotoLigacaoHelper> listaSimplificadaEsgotoLigacao,
					Short funcaoExecutada) throws ErroRepositorioException{

		if(listaSimplificadaEsgotoLigacao == null){
			throw new IllegalArgumentException("erro.histograma_esgoto_ligacao_lista_nula");
		}

		for(HistogramaEsgotoLigacaoHelper helperHistogramaEsgotoLigacao : listaSimplificadaEsgotoLigacao){
			HistogramaEsgotoLigacao histogramaEsgotoLigacao = null;
			histogramaEsgotoLigacao = this.repositorioHistograma.pesquisarHistogramaEsgotoLigacao(helperHistogramaEsgotoLigacao);

			if(histogramaEsgotoLigacao == null){

				HistogramaEsgotoLigacao hist = helperHistogramaEsgotoLigacao.gerarHistogramaEsgotoLigacao();

				// só adiciona se for pra adicionar . se for pra subtrair e nao existir nao adiciona
				if(!funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO)){
					repositorioUtil.inserir(hist);
				}

			}else{

				if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){

					histogramaEsgotoLigacao.setQuantidadeLigacao(histogramaEsgotoLigacao.getQuantidadeLigacao()
									+ helperHistogramaEsgotoLigacao.getQuantidadeLigacao());
					histogramaEsgotoLigacao.setQuantidadeEconomiaLigacao(histogramaEsgotoLigacao.getQuantidadeEconomiaLigacao()
									+ helperHistogramaEsgotoLigacao.getQuantidadeEconomiaLigacao());
					histogramaEsgotoLigacao.setValorFaturadoLigacao(histogramaEsgotoLigacao.getValorFaturadoLigacao().add(
									helperHistogramaEsgotoLigacao.getValorFaturadoLigacao()));
					histogramaEsgotoLigacao.setVolumeFaturadoLigacao(histogramaEsgotoLigacao.getVolumeFaturadoLigacao()
									+ helperHistogramaEsgotoLigacao.getVolumeFaturadoLigacao());



				}else if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO)){

					histogramaEsgotoLigacao.setQuantidadeLigacaoRefaturamento(histogramaEsgotoLigacao.getQuantidadeLigacaoRefaturamento()
									- helperHistogramaEsgotoLigacao.getQuantidadeLigacaoRefaturamento());
					histogramaEsgotoLigacao.setQuantidadeEconomiaLigacaoRefaturamento(histogramaEsgotoLigacao
									.getQuantidadeEconomiaLigacaoRefaturamento()
									- helperHistogramaEsgotoLigacao.getQuantidadeEconomiaLigacaoRefaturamento());
					histogramaEsgotoLigacao.setValorFaturadoLigacaoRefaturamento(histogramaEsgotoLigacao
									.getValorFaturadoLigacaoRefaturamento().subtract(
													helperHistogramaEsgotoLigacao.getValorFaturadoLigacaoRefaturamento()));
					histogramaEsgotoLigacao.setVolumeFaturadoLigacaoRefaturamento(histogramaEsgotoLigacao
									.getVolumeFaturadoLigacaoRefaturamento()
									- helperHistogramaEsgotoLigacao.getVolumeFaturadoLigacaoRefaturamento());

				}else{

					histogramaEsgotoLigacao.setQuantidadeLigacaoRefaturamento(histogramaEsgotoLigacao.getQuantidadeLigacaoRefaturamento()
									+ helperHistogramaEsgotoLigacao.getQuantidadeLigacaoRefaturamento());
					histogramaEsgotoLigacao.setQuantidadeEconomiaLigacaoRefaturamento(histogramaEsgotoLigacao
									.getQuantidadeEconomiaLigacaoRefaturamento()
									+ helperHistogramaEsgotoLigacao.getQuantidadeEconomiaLigacaoRefaturamento());
					histogramaEsgotoLigacao.setValorFaturadoLigacaoRefaturamento(histogramaEsgotoLigacao
									.getValorFaturadoLigacaoRefaturamento().add(
													helperHistogramaEsgotoLigacao.getValorFaturadoLigacaoRefaturamento()));
					histogramaEsgotoLigacao.setVolumeFaturadoLigacaoRefaturamento(histogramaEsgotoLigacao
									.getVolumeFaturadoLigacaoRefaturamento()
									+ helperHistogramaEsgotoLigacao.getVolumeFaturadoLigacaoRefaturamento());
				}

				histogramaEsgotoLigacao.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(histogramaEsgotoLigacao);
			}
		}
	}

	/**
	 * @author eduardo henrique
	 * @date 31/08/2009
	 * @param listaSimplificadaAguaEconomia
	 * @param adicao
	 * @throws ErroRepositorioException
	 */
	private void gerarResumoHistogramaAguaEconomia(List<HistogramaAguaEconomiaHelper> listaSimplificadaAguaEconomia, Short funcaoExecutada)
					throws ErroRepositorioException{

		if(listaSimplificadaAguaEconomia == null){
			throw new IllegalArgumentException("erro.histograma_agua_economia_lista_nula");
		}

		for(HistogramaAguaEconomiaHelper helperHistogramaAguaEconomia : listaSimplificadaAguaEconomia){

			HistogramaAguaEconomia histogramaAguaEconomia = null;
			histogramaAguaEconomia = this.repositorioHistograma.pesquisarHistogramaAguaEconomia(helperHistogramaAguaEconomia);

			if(histogramaAguaEconomia == null){

				HistogramaAguaEconomia hist = helperHistogramaAguaEconomia.gerarHistogramaAguaEconomia();

				// só adiciona se for pra adicionar. se for pra subtrair e nao existir nao adiciona
				if(!funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO)){
					repositorioUtil.inserir(hist);
				}

			}else{

				if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){

					histogramaAguaEconomia.setQuantidadeLigacao(histogramaAguaEconomia.getQuantidadeLigacao()
									+ helperHistogramaAguaEconomia.getQuantidadeLigacoes());
					histogramaAguaEconomia.setQuantidadeEconomia(histogramaAguaEconomia.getQuantidadeEconomia()
									+ helperHistogramaAguaEconomia.getQuantidadeEconomia());
					histogramaAguaEconomia.setValorFaturadoEconomia(histogramaAguaEconomia.getValorFaturadoEconomia().add(
									helperHistogramaAguaEconomia.getValorFaturadoEconomia()));
					histogramaAguaEconomia.setVolumeFaturadoEconomia(histogramaAguaEconomia.getVolumeFaturadoEconomia()
									+ helperHistogramaAguaEconomia.getVolumeFaturadoEconomia());



				}else if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO)){

					histogramaAguaEconomia.setQuantidadeLigacaoRefaturamento(histogramaAguaEconomia.getQuantidadeLigacaoRefaturamento()
									- helperHistogramaAguaEconomia.getQuantidadeLigacoesRefaturamento());
					histogramaAguaEconomia.setQuantidadeEconomiaRefaturamento(histogramaAguaEconomia.getQuantidadeEconomiaRefaturamento()
									- helperHistogramaAguaEconomia.getQuantidadeEconomiaRefaturamento());
					histogramaAguaEconomia.setValorFaturadoEconomiaRefaturamento(histogramaAguaEconomia
									.getValorFaturadoEconomiaRefaturamento().subtract(
													helperHistogramaAguaEconomia.getValorFaturadoEconomiaRefaturamento()));
					histogramaAguaEconomia.setVolumeFaturadoEconomiaRefaturamento(histogramaAguaEconomia
									.getVolumeFaturadoEconomiaRefaturamento()
									- helperHistogramaAguaEconomia.getVolumeFaturadoEconomiaRefaturamento());

				}else{

					histogramaAguaEconomia.setQuantidadeLigacaoRefaturamento(histogramaAguaEconomia.getQuantidadeLigacaoRefaturamento()
									+ helperHistogramaAguaEconomia.getQuantidadeLigacoesRefaturamento());
					histogramaAguaEconomia.setQuantidadeEconomiaRefaturamento(histogramaAguaEconomia.getQuantidadeEconomiaRefaturamento()
									+ helperHistogramaAguaEconomia.getQuantidadeEconomiaRefaturamento());
					histogramaAguaEconomia.setValorFaturadoEconomiaRefaturamento(histogramaAguaEconomia
									.getValorFaturadoEconomiaRefaturamento().add(
													helperHistogramaAguaEconomia.getValorFaturadoEconomiaRefaturamento()));
					histogramaAguaEconomia.setVolumeFaturadoEconomiaRefaturamento(histogramaAguaEconomia
									.getVolumeFaturadoEconomiaRefaturamento()
									+ helperHistogramaAguaEconomia.getVolumeFaturadoEconomiaRefaturamento());
				}

				histogramaAguaEconomia.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(histogramaAguaEconomia);
			}
		}
	}

	/**
	 * @author eduardo henrique
	 * @date 31/08/2009
	 * @param listaSimplificadaAguaLigacao
	 * @param adicao
	 * @throws ErroRepositorioException
	 */
	private void gerarResumoHistogramaAguaLigacao(List<HistogramaAguaLigacaoHelper> listaSimplificadaAguaLigacao, Short funcaoExecutada)
					throws ErroRepositorioException{

		if(listaSimplificadaAguaLigacao == null){
			throw new IllegalArgumentException("erro.histograma_agua_ligacao_lista_nula");
		}

		for(HistogramaAguaLigacaoHelper helperHistogramaAguaLigacaoHelper : listaSimplificadaAguaLigacao){

			HistogramaAguaLigacao histogramaAguaLigacao = null;
			histogramaAguaLigacao = this.repositorioHistograma.pesquisarHistogramaAguaLigacao(helperHistogramaAguaLigacaoHelper);

			if(histogramaAguaLigacao == null){

				HistogramaAguaLigacao hist = helperHistogramaAguaLigacaoHelper.gerarHistogramaAguaLigacao();

				// só adiciona se for pra adicionar. se for pra subtrair e nao existir nao adiciona
				if(!funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO)){
					repositorioUtil.inserir(hist);
				}

			}else{

				if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){

					histogramaAguaLigacao.setQuantidadeLigacao(histogramaAguaLigacao.getQuantidadeLigacao()
									+ helperHistogramaAguaLigacaoHelper.getQuantidadeLigacao());
					histogramaAguaLigacao.setQuantidadeEconomiaLigacao(histogramaAguaLigacao.getQuantidadeEconomiaLigacao()
									+ helperHistogramaAguaLigacaoHelper.getQuantidadeEconomiaLigacao());
					histogramaAguaLigacao.setValorFaturadoLigacao(histogramaAguaLigacao.getValorFaturadoLigacao().add(
									helperHistogramaAguaLigacaoHelper.getValorFaturadoLigacao()));
					histogramaAguaLigacao.setVolumeFaturadoLigacao(histogramaAguaLigacao.getVolumeFaturadoLigacao()
									+ helperHistogramaAguaLigacaoHelper.getVolumeFaturadoLigacao());



				}else if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO)){

					histogramaAguaLigacao.setQuantidadeLigacaoRefaturamento(histogramaAguaLigacao.getQuantidadeLigacaoRefaturamento()
									- helperHistogramaAguaLigacaoHelper.getQuantidadeLigacaoRefaturamento());
					histogramaAguaLigacao.setQuantidadeEconomiaLigacaoRefaturamento(histogramaAguaLigacao
									.getQuantidadeEconomiaLigacaoRefaturamento()
									- helperHistogramaAguaLigacaoHelper.getQuantidadeEconomiaLigacaoRefaturamento());
					histogramaAguaLigacao.setValorFaturadoLigacaoRefaturamento(histogramaAguaLigacao.getValorFaturadoLigacaoRefaturamento()
									.subtract(helperHistogramaAguaLigacaoHelper.getValorFaturadoLigacaoRefaturamento()));
					histogramaAguaLigacao.setVolumeFaturadoLigacaoRefaturamento(histogramaAguaLigacao
									.getVolumeFaturadoLigacaoRefaturamento()
									- helperHistogramaAguaLigacaoHelper.getVolumeFaturadoLigacaoRefaturamento());
				}else{

					histogramaAguaLigacao.setQuantidadeLigacaoRefaturamento(histogramaAguaLigacao.getQuantidadeLigacaoRefaturamento()
									+ helperHistogramaAguaLigacaoHelper.getQuantidadeLigacaoRefaturamento());
					histogramaAguaLigacao.setQuantidadeEconomiaLigacaoRefaturamento(histogramaAguaLigacao
									.getQuantidadeEconomiaLigacaoRefaturamento()
									+ helperHistogramaAguaLigacaoHelper.getQuantidadeEconomiaLigacaoRefaturamento());
					histogramaAguaLigacao.setValorFaturadoLigacaoRefaturamento(histogramaAguaLigacao.getValorFaturadoLigacaoRefaturamento()
									.add(helperHistogramaAguaLigacaoHelper.getValorFaturadoLigacaoRefaturamento()));
					histogramaAguaLigacao.setVolumeFaturadoLigacaoRefaturamento(histogramaAguaLigacao
									.getVolumeFaturadoLigacaoRefaturamento()
									+ helperHistogramaAguaLigacaoHelper.getVolumeFaturadoLigacaoRefaturamento());
				}

				histogramaAguaLigacao.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(histogramaAguaLigacao);
			}
		}
	}

	/**
	 * [UC0566] - Gerar Histograma de Água e Esgoto
	 * [SB1000] - Gerar Histograma
	 * 
	 * @author Virginia, Thiago Toscano, Eduardo Henrique
	 * @date 24/08/2009
	 *       Será retornado um array de object com cada Helper instanciado, conforme o que deva ser
	 *       gerado pela conta.
	 * @param colecaoConta
	 *            - Coleção de conta
	 * @return Object[] - [0] = histogramaAguaLigacaoHelper;
	 *         [1] = histogramaAguaEconomiaHelper;
	 *         [2] = histogramaEsgotoLigacaoHelper;
	 *         [3] = histogramaEsgotoEconomiaHelper;
	 */
	private Object[] gerarHistograma(Conta conta, Collection<ContaCategoria> colecaoContaCategoria, Short funcaoExecutada)
					throws ControladorException{

		try{

			HistogramaAguaLigacaoHelper aguaLigacao = null;
			HistogramaAguaEconomiaHelper aguaEconomia = null;
			HistogramaEsgotoLigacaoHelper esgotoLigacao = null;
			HistogramaEsgotoEconomiaHelper esgotoEconomia = null;

			Integer somatorioVolumeFaturadoEsgoto = Integer.valueOf(0);
			Integer somatorioVolumeFaturadoAgua = Integer.valueOf(0);

			BigDecimal somatorioValorFaturadoAgua = (BigDecimal.ZERO);
			BigDecimal somatorioValorFaturadoEsgoto = (BigDecimal.ZERO);

			Integer somatorioEconomiaLigacoes = Integer.valueOf(0);
			Integer somatorioQtdConsumoAgua = Integer.valueOf(0);
			Integer somatorioQtdConsumoEsgoto = Integer.valueOf(0);

			Integer somatorioQtdConsumoAguaEconomia = Integer.valueOf(0);
			Integer somatorioQtdConsumoEsgotoEconomia = Integer.valueOf(0);

			ContaCategoria contaCategoria = null;

			if(colecaoContaCategoria != null && !colecaoContaCategoria.isEmpty()){

				for(ContaCategoria cat : colecaoContaCategoria){

					if(contaCategoria == null){
						contaCategoria = cat;
					}else if(cat.getQuantidadeEconomia().intValue() > contaCategoria.getQuantidadeEconomia().intValue()){
						contaCategoria = cat;
					}else if(cat.getQuantidadeEconomia().intValue() == contaCategoria.getQuantidadeEconomia().intValue()){
						if(cat.getComp_id().getCategoria().getId().intValue() > contaCategoria.getComp_id().getCategoria().getId()
										.intValue()){
							contaCategoria = cat;
						}
					}




					// Consumo será calculado como o volume total / total de economias
					if(cat.getValorAgua() != null && cat.getValorAgua().doubleValue() > 0){
						Integer volumeAgua = obterVolumeFaturadoAgua(cat);
						somatorioVolumeFaturadoAgua = somatorioVolumeFaturadoAgua.intValue() + volumeAgua.intValue();
						somatorioValorFaturadoAgua = somatorioValorFaturadoAgua.add(cat.getValorAgua());

						if(cat.getConsumoAgua() != null){
							somatorioQtdConsumoAgua = somatorioQtdConsumoAgua.intValue() + cat.getConsumoAgua().intValue();
						}
					}

					if(cat.getValorEsgoto() != null && cat.getValorEsgoto().doubleValue() > 0){
						Integer volumeEsgoto = obterVolumeFaturadoEsgoto(cat);
						somatorioVolumeFaturadoEsgoto = somatorioVolumeFaturadoEsgoto.intValue() + volumeEsgoto.intValue();
						somatorioValorFaturadoEsgoto = somatorioValorFaturadoEsgoto.add(cat.getValorEsgoto());

						if(cat.getConsumoEsgoto() != null){
							somatorioQtdConsumoEsgoto = somatorioQtdConsumoEsgoto.intValue() + cat.getConsumoEsgoto().intValue();
						}
					}

					somatorioEconomiaLigacoes = somatorioEconomiaLigacoes.intValue() + cat.getQuantidadeEconomia().intValue();
				}
				somatorioQtdConsumoAguaEconomia = somatorioQtdConsumoAgua / somatorioEconomiaLigacoes;
				somatorioQtdConsumoEsgotoEconomia = somatorioQtdConsumoEsgoto / somatorioEconomiaLigacoes;
			}

			// Variaveis para preenchimento dos Helper`s
			Integer idGerenciaRegional = contaCategoria.getComp_id().getConta().getLocalidade().getGerenciaRegional().getId();
			Integer idUnidadeNegocio = null;

			if(contaCategoria.getComp_id().getConta() != null && contaCategoria.getComp_id().getConta().getLocalidade() != null
							&& contaCategoria.getComp_id().getConta().getLocalidade().getUnidadeNegocio() != null
							&& contaCategoria.getComp_id().getConta().getLocalidade().getUnidadeNegocio().getId() != null){

				idUnidadeNegocio = contaCategoria.getComp_id().getConta().getLocalidade().getUnidadeNegocio().getId();
			}

			Integer idElo = contaCategoria.getComp_id().getConta().getLocalidade().getLocalidade().getId();
			Integer idLocalidade = contaCategoria.getComp_id().getConta().getLocalidade().getId();

			Quadra quadra = repositorioQuadra.obterQuadra(contaCategoria.getComp_id().getConta().getQuadraConta().getId());
			if(quadra == null){
				throw new IllegalStateException("erro.quadra_histograma_conta_nula");
			}

			Integer idQuadra = quadra.getId();
			Integer numeroQuadra = quadra.getNumeroQuadra();
			Integer idSetorCormecial = quadra.getSetorComercial().getId();
			Integer codigoSetorComercial = quadra.getSetorComercial().getCodigo();

			Integer idTipoCategoria = contaCategoria.getComp_id().getCategoria().getCategoriaTipo().getId();
			Integer idCategoria = contaCategoria.getComp_id().getCategoria().getId();
			Integer idPerfilImovel = contaCategoria.getComp_id().getConta().getImovelPerfil().getId();
			Integer idConsumoTarifa = contaCategoria.getComp_id().getConta().getConsumoTarifa().getId();
			BigDecimal percentualEsgoto = contaCategoria.getComp_id().getConta().getPercentualEsgoto();

			Integer qtdEconomias = this.obterTotalEconomiasCategoria(colecaoContaCategoria);

			Integer idSituacaoAgua = contaCategoria.getComp_id().getConta().getLigacaoAguaSituacao().getId();
			Integer idSituacaoEsgoto = contaCategoria.getComp_id().getConta().getLigacaoEsgotoSituacao().getId();

			int idLigacaoMista = this.obterIndicadorLigacaoMista(colecaoContaCategoria);

			Integer idEsferaPoder = this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(contaCategoria
							.getComp_id().getConta().getImovel().getId());
			if(idEsferaPoder.equals(0)){
				Imovel imovel = new Imovel();
				imovel.setId(contaCategoria.getComp_id().getConta().getImovel().getId());
				Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
				if(clienteTemp != null){
					idEsferaPoder = clienteTemp.getClienteTipo().getEsferaPoder().getId();
				}
			}

			// Integer idConsumoReal =
			// this.repositorioGerencialCadastro.verificarConsumoReal(contaCategoria.getComp_id().getConta().getImovel().getId());
			Integer idConsumoReal = this.repositorioHistograma.verificarConsumoRealNaReferencia(contaCategoria.getComp_id()
							.getConta().getImovel().getId(), contaCategoria.getComp_id().getConta().getReferencia());

			// Verificamos a existencia de hidrômetro
			Integer idHidrometro = this.repositorioHistograma.verificarExistenciaHidrometro(contaCategoria.getComp_id().getConta()
							.getImovel().getId(), funcaoExecutada, contaCategoria.getComp_id().getConta().getReferencia());

			// Verificamos a existencia de poco no imovel
			Integer idPocoAgua = 2;
			Integer idPoco = this.repositorioHistograma.verificarExistenciaPoco(contaCategoria.getComp_id().getConta().getImovel().getId(),
							funcaoExecutada,
							contaCategoria.getComp_id().getConta().getReferencia());

			// Verificamos a existencia de volume fixo de agua
			Integer idVolumeFixoAgua = this.repositorioHistograma.verificarExistenciaVolumeFixoAgua(contaCategoria.getComp_id().getConta()
							.getImovel().getId(), funcaoExecutada, contaCategoria.getComp_id().getConta().getReferencia());

			Integer idVolumeFixoEsgoto = this.repositorioHistograma.verificarExistenciaVolumeFixoEsgoto(contaCategoria.getComp_id()
							.getConta().getImovel().getId(), funcaoExecutada, contaCategoria.getComp_id().getConta().getReferencia());

			// SB001 Preparar dados do histograma para 1 categoria e 1 economia
			// SB002 Preparar dados do histograma para 1 categoria e mais de uma economia
			if(contaCategoria.getComp_id().getConta().getValorAgua() != null
							&& contaCategoria.getComp_id().getConta().getValorAgua().compareTo(BigDecimal.ZERO) > 0){

				// Criamos um helper para os histograma de agua por ligação
				aguaLigacao = new HistogramaAguaLigacaoHelper(contaCategoria.getComp_id().getConta().getReferencia(), idGerenciaRegional,
								idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra, numeroQuadra,
								idTipoCategoria, idCategoria, new Short(idLigacaoMista + ""), idConsumoTarifa, idPerfilImovel,
								idEsferaPoder, idSituacaoAgua, (idConsumoReal != null) ? idConsumoReal.shortValue() : null,
								(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPocoAgua != null) ? idPocoAgua.shortValue()
												: null,
								(idVolumeFixoAgua != null) ? idVolumeFixoAgua.shortValue() : null, somatorioQtdConsumoAgua);

				// Criamos um helper para os histograma de agua por economia
				aguaEconomia = new HistogramaAguaEconomiaHelper(contaCategoria.getComp_id().getConta().getReferencia(), idGerenciaRegional,
								idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra, numeroQuadra,
								idTipoCategoria, idCategoria, idConsumoTarifa, idPerfilImovel, idEsferaPoder, idSituacaoAgua,
								(idConsumoReal != null) ? idConsumoReal.shortValue() : null, (idHidrometro != null) ? idHidrometro
.shortValue() : null, (idPocoAgua != null) ? idPocoAgua.shortValue()
												: null,
								(idVolumeFixoAgua != null) ? idVolumeFixoAgua.shortValue() : null, somatorioQtdConsumoAguaEconomia, 1);

				if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){

					aguaLigacao.setVolumeFaturadoLigacao(somatorioVolumeFaturadoAgua);
					aguaLigacao.setValorFaturadoLigacao(somatorioValorFaturadoAgua);
					aguaLigacao.setQuantidadeLigacao(1);
					aguaLigacao.setQuantidadeEconomiaLigacao(qtdEconomias);

					aguaEconomia.setVolumeFaturadoEconomia(somatorioVolumeFaturadoAgua);
					aguaEconomia.setValorFaturadoEconomia(somatorioValorFaturadoAgua);
					aguaEconomia.setQuantidadeEconomia(qtdEconomias);
					aguaEconomia.setQuantidadeLigacoes(1);
				}else{

					aguaLigacao.setVolumeFaturadoLigacaoRefaturamento(somatorioVolumeFaturadoAgua);
					aguaLigacao.setValorFaturadoLigacaoRefaturamento(somatorioValorFaturadoAgua);
					aguaLigacao.setQuantidadeLigacaoRefaturamento(1);
					aguaLigacao.setQuantidadeEconomiaLigacaoRefaturamento(qtdEconomias);

					aguaEconomia.setVolumeFaturadoEconomiaRefaturamento(somatorioVolumeFaturadoAgua);
					aguaEconomia.setValorFaturadoEconomiaRefaturamento(somatorioValorFaturadoAgua);
					aguaEconomia.setQuantidadeEconomiaRefaturamento(qtdEconomias);
					aguaEconomia.setQuantidadeLigacoesRefaturamento(1);
				}

			}

			if(contaCategoria.getComp_id().getConta().getValorEsgoto() != null
							&& contaCategoria.getComp_id().getConta().getValorEsgoto().compareTo(BigDecimal.ZERO) > 0){

				// Criamos um helper para os histograma de esgoto por ligação
				esgotoLigacao = new HistogramaEsgotoLigacaoHelper(contaCategoria.getComp_id().getConta().getReferencia(),
								idGerenciaRegional, idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial,
								idQuadra, numeroQuadra, idTipoCategoria, idCategoria, new Short(idLigacaoMista + ""), idConsumoTarifa,
								idPerfilImovel, idEsferaPoder, idSituacaoEsgoto, (idConsumoReal != null) ? idConsumoReal.shortValue()
												: null, (idHidrometro != null) ? idHidrometro.shortValue() : null,
								(idPoco != null) ? idPoco.shortValue() : null, (idVolumeFixoEsgoto != null) ? idVolumeFixoEsgoto
												.shortValue() : null, (percentualEsgoto != null) ? percentualEsgoto.shortValue() : null,
								somatorioQtdConsumoEsgoto);

				// Criamos um helper para os histograma de esgoto por economia
				esgotoEconomia = new HistogramaEsgotoEconomiaHelper(contaCategoria.getComp_id().getConta().getReferencia(),
								idGerenciaRegional, idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial,
								idQuadra, numeroQuadra, idTipoCategoria, idCategoria, idConsumoTarifa, idPerfilImovel, idEsferaPoder,
								idSituacaoEsgoto, (idConsumoReal != null) ? idConsumoReal.shortValue() : null,
								(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue() : null,
								(idVolumeFixoEsgoto != null) ? idVolumeFixoEsgoto.shortValue() : null,
								(percentualEsgoto != null) ? percentualEsgoto.shortValue() : null, somatorioQtdConsumoEsgotoEconomia, 1);

				if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){

					esgotoLigacao.setVolumeFaturadoLigacao(somatorioVolumeFaturadoEsgoto);
					esgotoLigacao.setValorFaturadoLigacao(somatorioValorFaturadoEsgoto);
					esgotoLigacao.setQuantidadeLigacao(1);
					esgotoLigacao.setQuantidadeEconomiaLigacao(qtdEconomias);

					esgotoEconomia.setVolumeFaturadoEconomia(somatorioVolumeFaturadoEsgoto);
					esgotoEconomia.setValorFaturadoEconomia(somatorioValorFaturadoEsgoto);
					esgotoEconomia.setQuantidadeEconomia(qtdEconomias);
					esgotoEconomia.setQuantidadeLigacoes(1);
				}else{

					esgotoLigacao.setVolumeFaturadoLigacaoRefaturamento(somatorioVolumeFaturadoEsgoto);
					esgotoLigacao.setValorFaturadoLigacaoRefaturamento(somatorioValorFaturadoEsgoto);
					esgotoLigacao.setQuantidadeLigacaoRefaturamento(1);
					esgotoLigacao.setQuantidadeEconomiaLigacaoRefaturamento(qtdEconomias);

					esgotoEconomia.setVolumeFaturadoEconomiaRefaturamento(somatorioVolumeFaturadoEsgoto);
					esgotoEconomia.setValorFaturadoEconomiaRefaturamento(somatorioValorFaturadoEsgoto);
					esgotoEconomia.setQuantidadeEconomiaRefaturamento(qtdEconomias);
					esgotoEconomia.setQuantidadeLigacoesRefaturamento(1);
				}
			}

			Object[] retorno = new Object[4];
			retorno[0] = aguaLigacao;
			retorno[1] = aguaEconomia;
			retorno[2] = esgotoLigacao;
			retorno[3] = esgotoEconomia;

			return retorno;
		}catch(Exception ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Obtém o valor do volume faturado de agua por categoria.
	 * [UC0566] - SB0004
	 * 
	 * @author lmedeiros
	 * @date 24/08/2009
	 * @param contaCategoria
	 * @return o Volume faturado de agua
	 */
	private Integer obterVolumeFaturadoAgua(ContaCategoria contaCategoria){

		int volumeFaturadoLigacaoAgua = 0;
		if(contaCategoria.getConsumoAgua() != null && contaCategoria.getConsumoMinimoAgua() != null){
			if(contaCategoria.getConsumoAgua() >= (contaCategoria.getConsumoMinimoAgua())){
				volumeFaturadoLigacaoAgua += contaCategoria.getConsumoAgua().intValue();
			}else{
				volumeFaturadoLigacaoAgua += contaCategoria.getConsumoMinimoAgua().intValue();
			}
		}
		return Integer.valueOf(volumeFaturadoLigacaoAgua);
	}

	/**
	 * Obtém o valor do volume faturado de Esgoto por categoria.
	 * [UC0566] - SB0005
	 * 
	 * @author lmedeiros
	 * @date 24/08/2009
	 * @param contaCategoria
	 * @return o Volume faturado de esgoto
	 */
	private Integer obterVolumeFaturadoEsgoto(ContaCategoria contaCategoria){

		int volumeFaturadoLigacaoEsgoto = 0;
		if(contaCategoria.getConsumoEsgoto() != null && contaCategoria.getConsumoMinimoEsgoto() != null){
			if(contaCategoria.getConsumoEsgoto() >= (contaCategoria.getConsumoMinimoEsgoto())){
				volumeFaturadoLigacaoEsgoto += contaCategoria.getConsumoEsgoto().intValue();
			}else{
				volumeFaturadoLigacaoEsgoto += contaCategoria.getConsumoMinimoEsgoto().intValue();
			}
		}
		return Integer.valueOf(volumeFaturadoLigacaoEsgoto);
	}

	/**
	 * Obtém a soma de Economias da conta.
	 * [UC0566] - SB0005
	 * 
	 * @author eduardo henrique
	 * @date 03/09/2009
	 * @param colecaoContaCategorias
	 * @return a Soma de Economias
	 */
	private int obterTotalEconomiasCategoria(Collection<ContaCategoria> colecaoCategoriasConta){

		int totalEconomias = 0;
		if(colecaoCategoriasConta != null){
			for(ContaCategoria contaCategoriaColecao : colecaoCategoriasConta){
				totalEconomias += contaCategoriaColecao.getQuantidadeEconomia().intValue();
			}
		}
		return totalEconomias;
	}

	/**
	 * Obtém o indicador de ligação mista (baseado na quantidade de categorias).
	 * [UC0566] - SB0005
	 * 
	 * @author eduardo henrique
	 * @date 04/09/2009
	 * @param colecaoContaCategorias
	 * @return indicador de ligação mista.
	 */
	private int obterIndicadorLigacaoMista(Collection<ContaCategoria> colecaoCategoriasConta){

		Map<Integer, Categoria> mapCategorias = new HashMap<Integer, Categoria>();
		if(colecaoCategoriasConta != null){
			for(ContaCategoria contaCategoriaColecao : colecaoCategoriasConta){
				if(!mapCategorias.containsKey(contaCategoriaColecao.getComp_id().getCategoria().getId())){
					mapCategorias.put(contaCategoriaColecao.getComp_id().getCategoria().getId(), contaCategoriaColecao.getComp_id()
									.getCategoria());
				}
			}
		}

		int qtdCategorias = mapCategorias.size();
		int idLigacaoMista = (qtdCategorias == 1) ? 2 : 1;

		return idLigacaoMista;
	}

	/**
	 * Obtém a categoria mais representativa (maior numero de economias), baseado na colecao de
	 * contas categorias de uma conta
	 * [UC0566] - SB0005
	 * 
	 * @author eduardo henrique
	 * @date 04/09/2009
	 * @param colecaoContaCategorias
	 * @return numero de categorias distintas na conta
	 */
	private Categoria obterPrincipalCategoriaConta(List<ContaCategoria> colecaoCategoriasConta){

		Categoria principalCategoria = null;
		if(colecaoCategoriasConta != null){
			// ordena a coleção em num. de economias, decrescente
			ComparatorChain sortEconomias = new ComparatorChain();
			sortEconomias.addComparator(new BeanComparator("quantidadeEconomia"), true);
			Collections.sort((List) colecaoCategoriasConta, sortEconomias);

			ContaCategoria contaCategoriaPrincipal = colecaoCategoriasConta.iterator().next();
			principalCategoria = contaCategoriaPrincipal.getComp_id().getCategoria();
		}
		return principalCategoria;
	}

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */

	public void deletarHistogramaAguaLigacao(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		this.repositorioHistograma.deletarHistogramaAguaLigacao(anoMesInicial, anoMesFinal);

	}

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaAguaEconomia(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		this.repositorioHistograma.deletarHistogramaAguaEconomia(anoMesInicial, anoMesFinal);

	}

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaEsgotoLigacao(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		this.repositorioHistograma.deletarHistogramaEsgotoLigacao(anoMesInicial, anoMesFinal);

	}

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaEsgotoEconomia(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		this.repositorioHistograma.deletarHistogramaEsgotoEconomia(anoMesInicial, anoMesFinal);

	}

}
