
package gcom.faturamento.histograma;

import gcom.cadastro.cliente.EsferaPoder;
import gcom.faturamento.*;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.faturamento.bean.HistogramaEsgotoEconomiaDTO;
import gcom.gerencial.cadastro.bean.HistogramaAguaEconomiaHelper;
import gcom.gerencial.cadastro.bean.HistogramaAguaLigacaoHelper;
import gcom.gerencial.cadastro.bean.HistogramaEsgotoEconomiaHelper;
import gcom.gerencial.cadastro.bean.HistogramaEsgotoLigacaoHelper;
import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * < <Descrição da Interface>>
 * 
 * @author Administrador
 */
public interface IRepositorioHistograma {

	/**
	 * Busca um Histograma determinado por seu "agrupamento de regra de negócio"
	 * 
	 * @author ebandeira
	 * @date 19/08/2009
	 * @param histogramaAguaEconomia
	 * @throws ErroRepositorioException
	 */
	public HistogramaAguaEconomia pesquisarHistogramaAguaEconomia(HistogramaAguaEconomiaHelper histogramaAguaEconomiaHelper)
					throws ErroRepositorioException;

	/**
	 * Busca um Histograma determinado por seu "agrupamento de regra de negócio"
	 * 
	 * @author Virgínia Melo
	 * @date 26/08/2009
	 * @param histogramaAguaLigacaoHelper
	 * @return HistogramaAguaLigacao
	 * @throws ErroRepositorioException
	 */
	public HistogramaAguaLigacao pesquisarHistogramaAguaLigacao(HistogramaAguaLigacaoHelper histogramaAguaLigacaoHelper)
					throws ErroRepositorioException;

	/**
	 * Busca um Histograma determinado por seu "agrupamento de regra de negócio"
	 * 
	 * @author Virgínia Melo
	 * @date 26/08/2009
	 * @param histogramaEsgotoLigacaoHelper
	 * @return HistogramaEsgotoLigacao
	 * @throws ErroRepositorioException
	 */
	public HistogramaEsgotoLigacao pesquisarHistogramaEsgotoLigacao(HistogramaEsgotoLigacaoHelper histogramaEsgotoLigacaoHelper)
					throws ErroRepositorioException;

	/**
	 * Busca um Histograma determinado por seu "agrupamento de regra de negócio"
	 * 
	 * @author Virgínia Melo
	 * @date 26/08/2009
	 * @param histogramaEsgotoEconomiaHelper
	 * @return HistogramaEsgotoEconomia
	 * @throws ErroRepositorioException
	 */
	public HistogramaEsgotoEconomia pesquisarHistogramaEsgotoEconomia(HistogramaEsgotoEconomiaHelper histogramaEsgotoEconomiaHelper)
					throws ErroRepositorioException;

	/**
	 * @author Andre Nishimura
	 * @date 25/08/2009
	 */
	public EsferaPoder pesquisarEsferaPoderClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Método que retorna uma colecao de ContaCategoria com valores preenchidos para geração do
	 * histograma;
	 * 
	 * @param idConta
	 * @return Collection - Coleção de ContaCategoria
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaParaHistograma(Integer idConta) throws ErroRepositorioException;

	/**
	 * Método que retorna uma colecao de HistogramaServico
	 * 
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param idCategoria
	 * @param idDebitoTipo
	 * @return Collection - Coleção de HistogramaServico
	 * @throws ErroRepositorioException
	 */
	public HistogramaServico pesquisarHistogramaServico(Integer idLocalidade, Integer idSetorComercial, Integer idCategoria,
					Integer idDebitoTipo) throws ErroRepositorioException;

	/**
	 * Método pesquisarDadosRelatorioHistogramaEsgoto
	 * <p>
	 * Esse método implementa pesquisa dos dados para relatorio de histograma de esgoto economias
	 * </p>
	 * RASTREIO: [OC1073038][UC0606]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @param filtro
	 * @since 04/06/2013
	 */
	public Collection<HistogramaEsgotoEconomiaDTO> pesquisarDadosRelatorioHistogramaEsgoto(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param referencia
	 * @param funcaoExecutada
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer verificarConsumoRealNaReferencia(Integer idImovel, Integer referencia)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param funcaoExecutada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaHidrometro(Integer idImovel, Short funcaoExecutada, Integer referencia)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param funcaoExecutada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaPoco(Integer idImovel, Short funcaoExecutada, Integer referencia) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param funcaoExecutada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaVolumeFixoAgua(Integer idImovel, Short funcaoExecutada, Integer referencia)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param funcaoExecutada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaVolumeFixoEsgoto(Integer idImovel, Short funcaoExecutada, Integer referencia)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param funcaoExecutada
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Float percentualColetaEsgoto(Integer idImovel, Short funcaoExecutada, Integer referencia) throws ErroRepositorioException;

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaAguaLigacao(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException;

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaAguaEconomia(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException;

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaEsgotoLigacao(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException;

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaEsgotoEconomia(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException;

}
