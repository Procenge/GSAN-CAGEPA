
package gcom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ControleAlteracaoColecao {

	private Collection adicionadas = new ArrayList();

	private Collection removidas = new ArrayList();

	private HashMap alteradas = new HashMap();

	private ControleAlteracaoColecao() {

	}

	public Collection getAdicionadas(){

		return adicionadas;
	}

	public HashMap getAlteradas(){

		return alteradas;
	}

	public Collection getRemovidas(){

		return removidas;
	}

	public static ControleAlteracaoColecao gerarControle(Collection antes, Collection depois){

		ControleAlteracaoColecao controle = new ControleAlteracaoColecao();
		if((antes == null || antes.isEmpty()) && (depois == null || depois.isEmpty())){
			return controle;
		}
		if(antes == null || antes.isEmpty()){
			controle.adicionadas = depois;
		}else if(depois == null || depois.isEmpty()){
			controle.removidas = antes;
		}else{
			boolean statusAntes[] = new boolean[antes.size()];
			boolean statusDepois[] = new boolean[depois.size()];
			Object[] objsAntes = antes.toArray(new Object[0]);
			Object[] objsDepois = depois.toArray(new Object[0]);
			for(int i = 0; i < objsDepois.length; i++){
				for(int j = 0; j < objsAntes.length; j++){
					if(objsDepois[i].equals(objsAntes[j])){
						statusAntes[j] = true;
						statusDepois[i] = true;
						// TODO: verificar se foram alterados
						controle.alteradas.put(objsAntes[j], objsDepois[i]);
						break;
					}
				}
			}
			for(int i = 0; i < objsDepois.length; i++){
				if(!statusDepois[i]){
					controle.adicionadas.add(objsDepois[i]);
				}
			}
			for(int i = 0; i < objsAntes.length; i++){
				if(!statusAntes[i]){
					controle.removidas.add(objsAntes[i]);
				}
			}
		}

		return controle;

	}

}
