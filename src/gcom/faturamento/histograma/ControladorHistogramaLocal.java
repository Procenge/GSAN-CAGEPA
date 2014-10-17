/**
 * 
 */

package gcom.faturamento.histograma;

import gcom.faturamento.conta.Conta;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * @author ebandeira
 */
public interface ControladorHistogramaLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Método que contabiliza o histograma a partir das contas faturadas
	 * 
	 * @author Thiago Toscano
	 * @date 27/08/2009
	 * @param Collection
	 *            <Conta> colecaoConta
	 * @param boolean adicao
	 * @throws ControladorException
	 */
	public void gerarHistogramaAguaEsgoto(Collection<Conta> colecaoConta, Short funcaoExecutada) throws ControladorException;

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