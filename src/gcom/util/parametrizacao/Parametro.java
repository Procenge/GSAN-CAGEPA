
package gcom.util.parametrizacao;

import gcom.fachada.Fachada;
import gcom.seguranca.acesso.FuncionalidadeCorrente;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.SistemaException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.procenge.comum.exception.PCGException;
import br.com.procenge.parametrosistema.api.ParametroSistema;
import br.com.procenge.parametrosistema.api.TipoParametroSistema;

public abstract class Parametro {

	private String codigo;

	protected Parametro(String codigo) {

		this.codigo = codigo;
	}

	public String getCodigo(){

		return codigo;
	}

	private Integer getIdFuncionalidadeCorrente(){

		if(FuncionalidadeCorrente.get() != null){

			return FuncionalidadeCorrente.get().getId();
		}
		return null;
	}

	/**
	 * Executa o par�metro, pode ser ESTATICO ou DINAMICO, e tenta utilizar a Funcionalidade de onde
	 * est� sendo chamado, mas pode ocorrer de n�o conseguir resolver a Funcionalidade.
	 * 
	 * @param exec
	 *            Objeto que ir� executar o par�metro caso ele seja DINAMICO
	 * @param parte
	 *            Indica qual parte da execu��o ser� chamada no Executor do Par�metro
	 * @param args
	 *            Param�tros necess�rios para execu��o do Par�metro do Sistema
	 * @return Qualquer valor (de qualquer tipo) ap�s a execu��o do par�metro
	 */
	public Object executar(Parametrizacao obj, Integer parte, Object... args) throws ControladorException{

		return executar(obj, parte, getIdFuncionalidadeCorrente(), args);
	}

	/**
	 * Executa o par�metro, pode ser ESTATICO ou DINAMICO, de acordo com o
	 * ID da Funcionalidade passada no par�metro idFuncionalidade.
	 * 
	 * @param exec
	 *            Objeto que ir� executar o par�metro caso ele seja DINAMICO
	 * @param parte
	 *            Indica qual parte da execu��o ser� chamada no Executor do Par�metro
	 * @param idFuncionalidade
	 *            ID de Funcionalidade que est� executando o Par�metro de Sistema
	 * @param args
	 *            Param�tros necess�rios para execu��o do Par�metro do Sistema
	 * @return Qualquer valor (de qualquer tipo) ap�s a execu��o do par�metro
	 */
	public Object executar(Parametrizacao obj, Integer parte, Integer idFuncionalidade, Object... args) throws ControladorException{

		return executar(obj.getExecutorParametro(), parte, idFuncionalidade, args);
	}

	/**
	 * Executa o par�metro sempre como ESTATICO e tenta utilizar a Funcionalidade de onde est� sendo
	 * chamado, mas pode ocorrer de n�o conseguir resolver a Funcionalidade.
	 * 
	 * @return Valor do par�metro
	 * @throws ControladorException
	 */
	public String executar() throws ControladorException{

		return executar(getIdFuncionalidadeCorrente());
	}

	/**
	 * Executa o par�metro sempre como ESTATICO e utiliza a Funcionalidade cujo o ID � passada no
	 * par�metro idFuncionalidade.
	 * 
	 * @param idFuncionalidade
	 *            ID de Funcionalidade que est� executando o Par�metro de Sistema
	 * @return Valor do par�metro
	 * @throws ControladorException
	 */
	public String executar(Integer idFuncionalidade) throws ControladorException{

		return getParametroSistema(idFuncionalidade).getValor();
	}

	/**
	 * Retorna o valor do par�metro de acordo com o ID da Funcionalidade passada no
	 * par�metro idFuncionalidade.
	 * 
	 * @param idFuncionalidade
	 *            ID de Funcionalidade que est� executando o Par�metro de Sistema
	 * @return Objeto de ParametroSistema
	 * @throws ControladorException
	 */
	public ParametroSistema getParametroSistema(Integer idFuncionalidade) throws ControladorException{

		try{
			return Fachada.getInstancia().obterParametroSistema(getCodigo(), idFuncionalidade);
		}catch(PCGException e){
			throw new ControladorException("erro.parametro.sistema", e, e.getMessage());
		}
	}

	/**
	 * Executa o par�metro, pode ser ESTATICO ou DINAMICO, e tenta utilizar a Funcionalidade de onde
	 * est� sendo chamado, mas pode ocorrer de n�o conseguir resolver a Funcionalidade.
	 * 
	 * @param param
	 *            Objeto que ir� informar quem � o executor do par�metro caso ele seja DINAMICO
	 * @return Qualquer valor (de qualquer tipo) ap�s a execu��o do par�metro
	 */
	public Object executar(Parametrizacao param) throws ControladorException{

		return executar(param.getExecutorParametro(), null, getIdFuncionalidadeCorrente());
	}

	/**
	 * Executa o par�metro, pode ser ESTATICO ou DINAMICO, e utiliza a Funcionalidade cujo o ID �
	 * passada no
	 * par�metro idFuncionalidade.
	 * 
	 * @param param
	 *            Objeto que ir� informar quem � o executor do par�metro caso ele seja DINAMICO
	 * @param idFuncionalidade
	 *            ID de Funcionalidade que est� executando o Par�metro de Sistema
	 * @return Qualquer valor (de qualquer tipo) ap�s a execu��o do par�metro
	 * @throws ControladorException
	 */
	public Object executar(Parametrizacao param, Integer idFuncionalidade) throws ControladorException{

		return executar(param.getExecutorParametro(), idFuncionalidade);
	}

	/**
	 * Executa o par�metro, pode ser ESTATICO ou DINAMICO, e utiliza a Funcionalidade cujo o ID �
	 * passada no
	 * par�metro idFuncionalidade.
	 * 
	 * @param exec
	 *            Objeto que ir� executar o par�metro caso ele seja DINAMICO
	 * @param idFuncionalidade
	 *            ID de Funcionalidade que est� executando o Par�metro de Sistema
	 * @return Qualquer valor (de qualquer tipo) ap�s a execu��o do par�metro
	 * @throws ControladorException
	 */
	public Object executar(ExecutorParametro exec, Integer idFuncionalidade) throws ControladorException{

		return executar(exec, null, idFuncionalidade);
	}

	/**
	 * Executa o par�metro, pode ser ESTATICO ou DINAMICO, e tenta utilizar a Funcionalidade de onde
	 * est� sendo chamado, mas pode ocorrer de n�o conseguir resolver a Funcionalidade.
	 * 
	 * @param exec
	 *            Objeto que ir� executar o par�metro caso ele seja DINAMICO
	 * @return Qualquer valor (de qualquer tipo) ap�s a execu��o do par�metro
	 * @throws ControladorException
	 */
	public Object executar(ExecutorParametro exec) throws ControladorException{

		return executar(exec, null, getIdFuncionalidadeCorrente(), (Object[]) null);
	}

	/**
	 * Executa o par�metro, pode ser ESTATICO ou DINAMICO, e tenta utilizar a Funcionalidade de onde
	 * est� sendo chamado, mas pode ocorrer de n�o conseguir resolver a Funcionalidade.
	 * 
	 * @param exec
	 *            Objeto que ir� executar o par�metro caso ele seja DINAMICO
	 * @param parte
	 *            Indica qual parte da execu��o ser� chamada no Executor do Par�metro
	 * @param idFuncionalidade
	 *            ID de Funcionalidade que est� executando o Par�metro de Sistema
	 * @param args
	 *            Param�tros necess�rios para execu��o do Par�metro do Sistema
	 * @return Qualquer valor (de qualquer tipo) ap�s a execu��o do par�metro
	 * @throws ControladorException
	 */
	public Object executar(ExecutorParametro exec, Integer parte, Integer idFuncionalidade, Object... args) throws ControladorException{

		ParametroSistema parametroSistema = getParametroSistema(idFuncionalidade);
		String valor = parametroSistema.getValor();

		if(parametroSistema.getTipoParametroSistema().getCodigo() == TipoParametroSistema.TIPO_DINAMICO){

			if(valor != null){

				valor = valor.trim();

				if(valor.length() > 0 && !valor.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){

					if(valor.length() == 1){
						valor = Character.toString(Character.toUpperCase(valor.charAt(0)));
					}else{
						valor = Character.toUpperCase(valor.charAt(0)) + valor.substring(1);
					}

					Class[] tipos = null;
					Object[] valores = null;

					if(args != null){
						tipos = new Class[args.length + 1];
						valores = new Object[args.length + 1];

						for(int i = 0; i < args.length; i++){
							tipos[i + 1] = args[i].getClass();
							valores[i + 1] = args[i];
						}
					}else{
						tipos = new Class[1];
						valores = new Object[1];
					}
					tipos[0] = ParametroSistema.class;
					valores[0] = parametroSistema;

					String nomeMetodo = "execParam" + valor;
					if(parte != null && parte > -1) nomeMetodo += "_" + parte;

					try{
						Method metodo = exec.getClass().getMethod(nomeMetodo, tipos);
						return metodo.invoke(exec, valores);
					}catch(NoSuchMethodException e){
						return executar(exec, nomeMetodo, tipos, valores);
					}catch(InvocationTargetException e){
						if(e.getTargetException() instanceof ControladorException){
							throw (ControladorException) e.getTargetException();
						}
						throw new SistemaException(e, e.getMessage());
					}catch(Exception e){
						e.printStackTrace();
						throw new SistemaException(e, e.getMessage());
					}
				}else{
					valor = null;
				}
			}else{
				valor = null;
			}
		}

		return valor;
	}

	private Object executar(ExecutorParametro exec, String nomeMetodo, Class[] tipos, Object... args) throws ControladorException{

		boolean metodoEncontrado = false;
		Method[] metodos = exec.getClass().getMethods();

		for(Method metodo : metodos){

			if(metodo.getName().equals(nomeMetodo)){

				metodoEncontrado = true;
				Class[] params = metodo.getParameterTypes();

				for(int i = 0; i < params.length; i++){

					if(params[i].isAssignableFrom(tipos[i])) continue;
					metodoEncontrado = false;
				}

				if(metodoEncontrado){
					try{
						return metodo.invoke(exec, args);
					}catch(InvocationTargetException e){
						if(e.getTargetException() instanceof ControladorException){
							throw (ControladorException) e.getTargetException();
						}
						throw new SistemaException(e, e.getMessage());
					}catch(Exception e){
						e.printStackTrace();
						throw new SistemaException(e, e.getMessage());
					}
				}
			}
		}

		if(!metodoEncontrado){
			throw new SistemaException("M�todo '" + nomeMetodo + "' n�o encontrado na classe '" + exec.getClass().getName() + "'");
		}

		return null;
	}

}
