/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.IRepositorioCliente;
import gcom.cadastro.cliente.RepositorioClienteHBM;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author gmatos
 */
public class GeradorDadosRelatorioExemplo
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioCliente repositorioCliente;

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioExemplo() {

		super();
		this.repositorioCliente = RepositorioClienteHBM.getInstancia();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.relatorio.ordemservico.GeradorDadosRelatorioOrdemServico#gerarDados(gcom.atendimentopublico
	 * .ordemservico.OrdemServico)
	 */
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = null;
		DadosRelatorioOrdemServico dadosRelatorioOrdemServico = null;
		dados = new ArrayList<DadosRelatorioOrdemServico>();
		dadosRelatorioOrdemServico = new DadosRelatorioOrdemServico();
		dadosRelatorioOrdemServico.setNumeroOS(Util.adicionarZerosEsqueda(
						ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO, os.getId().toString()));

		// Preenchendo os dados necessários para o relatório
		// dadosRelatorioOrdemServico.setNumeroOs(String.valueOf(os.getId()));
		try{
			Cliente cliente = this.repositorioCliente.pesquisarCliente(0);
			if(cliente != null){
				// dadosRelatorioOrdemServico.setNome(cliente.getNome());
			}
		}catch(ErroRepositorioException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}

		dados.add(dadosRelatorioOrdemServico);

		return dados;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		// TODO Auto-generated method stub
		return null;
	}

}
