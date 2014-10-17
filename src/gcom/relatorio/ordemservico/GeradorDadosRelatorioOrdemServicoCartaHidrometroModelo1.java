
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.CartaHidrometroModelo1Helper;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.RepositorioImovelHBM;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * [UC0458] Imprimir Ordem de Serviço
 * Gera os Relatórios das Ordens de Serviço de Fiscalização Modelo2
 * 
 * @author Carlos Chrystian
 * @date 11/04/2013
 */
public class GeradorDadosRelatorioOrdemServicoCartaHidrometroModelo1
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioImovel repositorioImovel = null;

	public GeradorDadosRelatorioOrdemServicoCartaHidrometroModelo1() {

		super();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> collOrdemServicoFiscalizacaoModelo2Helper = new ArrayList<DadosRelatorioOrdemServico>();

		List<CartaHidrometroModelo1Helper> colecaoOrdemServicoFiscalizacaoModelo2Helper = this.cartaHidrometroModelo1Helper(ordensServicos);
		int totalPaginas = colecaoOrdemServicoFiscalizacaoModelo2Helper.size();

		List<CartaHidrometroModelo1Helper> colecaoOrdenada = (List<CartaHidrometroModelo1Helper>) this
						.dividirColecaoOrdenando(colecaoOrdemServicoFiscalizacaoModelo2Helper);

		CartaHidrometroModelo1Helper primeiroHelper = null;
		CartaHidrometroModelo1Helper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(CartaHidrometroModelo1Helper helper : colecaoOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina("" + pagina1);
				pagina1++;
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina("" + pagina2);
				pagina2++;
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				collOrdemServicoFiscalizacaoModelo2Helper.add(new DadosRelatorioOrdemServico(primeiroHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collOrdemServicoFiscalizacaoModelo2Helper.add(new DadosRelatorioOrdemServico(primeiroHelper));
		}

		return collOrdemServicoFiscalizacaoModelo2Helper;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		Collection<OrdemServico> ordensServicos = new ArrayList<OrdemServico>();

		ordensServicos.add(os);

		return this.gerarDados(ordensServicos);
	}

	/**
	 * [UC0458] Imprimir Ordem de Serviço
	 * Gera os Relatórios das Ordens de Serviço de Fiscalização Modelo2
	 * 
	 * @author Carlos Chrystian
	 * @date 11/04/2013
	 * @param listPesquisaOrdemServico
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public List<CartaHidrometroModelo1Helper> cartaHidrometroModelo1Helper(
					Collection<OrdemServico> listPesquisaOrdemServico){

		List<CartaHidrometroModelo1Helper> colecaoCartaHidrometroModelo1Helper = new ArrayList<CartaHidrometroModelo1Helper>();

		if(listPesquisaOrdemServico != null && !listPesquisaOrdemServico.isEmpty()){

			for(OrdemServico ordemServico : listPesquisaOrdemServico){

				CartaHidrometroModelo1Helper cartaHidrometroModelo1Helper = new CartaHidrometroModelo1Helper();

				// ****************************************************

				// NOME DO CLIENTE
				Collection<ClienteImovel> colecaoClienteImovel = null;
				try{
					colecaoClienteImovel = repositorioImovel.pesquisarClientesImovel(ordemServico.getImovel().getId());
				}catch(ErroRepositorioException e2){
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

					for(Object objeto : colecaoClienteImovel){

						Object[] clienteImovel = (Object[]) objeto;

						if(clienteImovel[8].equals(ClienteRelacaoTipo.USUARIO)){
							// NOME DO CLIENTE
							cartaHidrometroModelo1Helper.setClienteNome(String.valueOf(clienteImovel[1]));
						}
					}

				}

				// MATRÍCULA
				cartaHidrometroModelo1Helper.setMatricula(Util.retornaMatriculaImovelParametrizada(ordemServico.getImovel().getId()));

				// ENDEREÇO LOCALIDADE
				String endereco = "";

				try{
					endereco = this.getControladorOrdemServico().obterEnderecoCompletoOS(ordemServico.getId());
				}catch(ControladorException e1){
					e1.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(e1.getMessage(), e1);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				cartaHidrometroModelo1Helper.setEndereco(endereco);

				// ORDEM DE SERVIÇO
				cartaHidrometroModelo1Helper.setOrdemServico(Util.completarStringZeroEsquerda(ordemServico.getId().toString(), 9));

				// **********************************************************

				colecaoCartaHidrometroModelo1Helper.add(cartaHidrometroModelo1Helper);

				cartaHidrometroModelo1Helper = null;
			}

		}
		return colecaoCartaHidrometroModelo1Helper;
	}
}
