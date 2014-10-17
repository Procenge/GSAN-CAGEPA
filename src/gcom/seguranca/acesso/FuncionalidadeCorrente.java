
package gcom.seguranca.acesso;

public class FuncionalidadeCorrente {

	private static ThreadLocal<Funcionalidade> threadLocal = new ThreadLocal<Funcionalidade>();

	public static void set(Funcionalidade funcionalidade){

		threadLocal.set(funcionalidade);
	}

	public static Funcionalidade get(){

		return threadLocal.get();
	}
}
