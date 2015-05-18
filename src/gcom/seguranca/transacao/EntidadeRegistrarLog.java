package gcom.seguranca.transacao;

import gcom.cobranca.ajustetarifa.AjusteTarifa;
import gcom.util.*;

import javax.ejb.CreateException;

/**
 * Classe que armazena as entidades do sistema que usam regsitro de log em coluna específica da
 * tabela
 * 
 * @author Anderson Italo
 * @date 26/09/2014
 */
public class EntidadeRegistrarLog {

	private static String TABELA_AJUSTE_TARIFA = "ajuste_tarifa";

	/**
	 * Método que retorna o RegistradorLogTrasacoes da tabela consultada
	 * 
	 * @author Anderson Italo
	 * @date 26/09/2014
	 */
	public static RegistradorLogTransacoes retornarRegistroTabela(Tabela tabela, Integer idRegistro){

		RegistradorLogTransacoes retorno = null;

		try{
			if(tabela != null && idRegistro != null){

				if(tabela.getNomeTabela().equals(TABELA_AJUSTE_TARIFA)){

					AjusteTarifa ajusteTarifa = (AjusteTarifa) getControladorUtil().pesquisar(idRegistro, AjusteTarifa.class, false);
					retorno = ajusteTarifa;
				}
			}
		}catch(ControladorException e){

			throw new SistemaException(e);
		}

		return retorno;
	}

	private static ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

}
