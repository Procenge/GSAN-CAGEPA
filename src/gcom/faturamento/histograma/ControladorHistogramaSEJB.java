/**
 * 
 */

package gcom.faturamento.histograma;

import gcom.faturamento.conta.Conta;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * @author ebandeira
 */
public class ControladorHistogramaSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		// controladorHistograma = ControladorHistograma.getInstancia();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException{

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException{

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException{

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException{

		this.sessionContext = sessionContext;
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

			ControladorHistograma.getInstancia().gerarHistogramaAguaEsgoto(colecaoConta, funcaoExecutada);
		}catch(ControladorException ce){

			sessionContext.setRollbackOnly();
			throw ce;
		}catch(Exception ex){

			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */

	public void deletarHistogramaAguaLigacao(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		ControladorHistograma.getInstancia().deletarHistogramaAguaLigacao(anoMesInicial, anoMesFinal);

	}

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaAguaEconomia(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		ControladorHistograma.getInstancia().deletarHistogramaAguaEconomia(anoMesInicial, anoMesFinal);

	}

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaEsgotoLigacao(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		ControladorHistograma.getInstancia().deletarHistogramaEsgotoLigacao(anoMesInicial, anoMesFinal);

	}

	/**
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaEsgotoEconomia(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		ControladorHistograma.getInstancia().deletarHistogramaEsgotoEconomia(anoMesInicial, anoMesFinal);

	}

}
