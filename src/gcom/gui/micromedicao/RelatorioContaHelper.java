/**
 * 
 */

package gcom.gui.micromedicao;

import gcom.cadastro.imovel.Imovel;
import gcom.faturamento.conta.Conta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author isilva
 */
public class RelatorioContaHelper {

	private List<Conta> contas;

	private Imovel imovel;

	public RelatorioContaHelper() {

		// TODO Auto-generated constructor stub
	}

	public RelatorioContaHelper(List<Conta> contas, Imovel imovel) {

		this.contas = contas;
		this.imovel = imovel;
	}

	public List<Conta> getContas(){

		return contas;
	}

	public void setContas(List<Conta> contas){

		if(this.contas == null){
			this.contas = new ArrayList<Conta>();
		}

		this.contas = contas;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public void addConta(Conta conta){

		contas.add(conta);
	}
}
