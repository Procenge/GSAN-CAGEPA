/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import gcom.cadastro.aguaparatodos.FiltroImovelAguaParaTodos;
import gcom.cadastro.aguaparatodos.ImovelAguaParaTodos;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteImovelSimplificado;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteFone;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelSubCategoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ExibirManterImovelAguaParaTodosAction
				extends GcomAction {

	private static final String TITULO_TELA_INSERIR_PROGRAMA_AGUA_PARA_TODOS = "Inserir Programa Água para Todos";

	private static final String TITULO_TELA_MANTER_PROGRAMA_AGUA_PARA_TODOS = "Manter Programa Água para Todos";

	/**
	 * Este caso de uso permite alterar um valor de Agência Bancária
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 26/01/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterImovelAguaParaTodos");

		String tela = httpServletRequest.getParameter("tela");

		if(tela != null && tela.equalsIgnoreCase("inserir")){

			httpServletRequest.setAttribute("tituloTela", TITULO_TELA_INSERIR_PROGRAMA_AGUA_PARA_TODOS);
			httpServletRequest.setAttribute("tela", "inserir");

		}else{

			httpServletRequest.setAttribute("tituloTela", TITULO_TELA_MANTER_PROGRAMA_AGUA_PARA_TODOS);

		}

		ManterImovelAguaParaTodosActionForm form = (ManterImovelAguaParaTodosActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		form.setFlagOperacao(form.NENHUM);

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equals("S")){
			form.setMatricula("");
		}

		if(form != null && httpServletRequest.getParameter("matriculaImovel") != null
						&& !httpServletRequest.getParameter("matriculaImovel").equals("")){
			Imovel imovel = null;
			try{
				imovel = fachada.pesquisarImovel(Integer.valueOf((String) httpServletRequest.getParameter("matriculaImovel")));
			}catch(NumberFormatException e){
				throw new ActionServletException("atencao.nao.numerico", null, form.getMatricula());
			}

			if(imovel != null){

				FiltroImovelAguaParaTodos filtroImovelAguaParaTodos = new FiltroImovelAguaParaTodos();
				filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID_IMOVEL, imovel.getId()));
				filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovelAguaParaTodos.CODIGO_SITUACAO,
								ImovelAguaParaTodos.EXCLUIDO));

				Collection colImovelAguaParaTodos = fachada.pesquisar(filtroImovelAguaParaTodos, ImovelAguaParaTodos.class.getName());

				ImovelAguaParaTodos imovelAguaParaTodos = null;

				if(!colImovelAguaParaTodos.isEmpty()){
					imovelAguaParaTodos = (ImovelAguaParaTodos) colImovelAguaParaTodos.iterator().next();
				}

				if(imovelAguaParaTodos != null){
					if(imovelAguaParaTodos.getCodigoSituacao().equals(ImovelAguaParaTodos.CADASTRADO)){

						form.setId(imovelAguaParaTodos.getId().toString());
						form.setFlagOperacao(form.HABILITAR);
						form.setNic(imovelAguaParaTodos.getIdContribuinte().toString());
						form.setNomeContribuinte(imovelAguaParaTodos.getNomeParticipante());
						form.setDataCadastramento(Util.formatarData(imovelAguaParaTodos.getDataCadastramento()));
						form.setSituacaoCadastramento(form.SITUACAO_CADASTRADO);

					}else if(imovelAguaParaTodos.getCodigoSituacao().equals(ImovelAguaParaTodos.HABILITADO)){

						form.setId(imovelAguaParaTodos.getId().toString());
						form.setFlagOperacao(form.EXCLUIR);

						// Habilitar a opção de RENOVAR
						if((imovel.getDataValidadeTarifaTemporaria() != null)
										&& (imovel.getDataValidadeTarifaTemporaria().compareTo(new Date()) < 0)){

							form.setFlagOperacao(form.RENOVAR);
							form.setDataRenovacao(Util.formatarData(new Date()));

							Usuario usuario = getUsuarioLogado(httpServletRequest);
							if(usuario != null){
								form.setUsuarioRenovacao(contruirInfoUsuario(usuario.getId()));
							}else{
								form.setUsuarioRenovacao(null);
							}

							form.setDataNovaValidadeTarifa(Util.formatarData(Util.adicionarNumeroMesDeUmaData(new Date(), 12)));

						}

						if((imovel.getConsumoTarifaTemporaria() != null) && (imovel.getConsumoTarifaTemporaria().getId() != null)){
							FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();
							filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, imovel
											.getConsumoTarifaTemporaria().getId()));

							ConsumoTarifa consumoTarifa = (ConsumoTarifa) Fachada.getInstancia()
											.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName()).iterator().next();

							form.setTarifa(consumoTarifa.getDescricao());
						}else{
							form.setTarifa(null);
						}

						form.setDataValidadeTarifa(Util.formatarData(imovel.getDataValidadeTarifaTemporaria()));

						httpServletRequest.setAttribute("aguaParaTodosMotivosExclusao", Fachada.getInstancia()
										.consultarAguaParaTodosMotivosExclusao());

						if(imovelAguaParaTodos.getDataRenovacao() != null){
							form.setSituacaoCadastramento(form.SITUACAO_HABILITADO_RENOVADO);
						}else{
							form.setSituacaoCadastramento(form.SITUACAO_HABILITADO);
						}

						form.setNic(imovelAguaParaTodos.getIdContribuinte().toString());
						form.setNomeContribuinte(imovelAguaParaTodos.getNomeParticipante());
						form.setDataCadastramento(Util.formatarData(imovelAguaParaTodos.getDataCadastramento()));
						form.setDataHabilitacao(Util.formatarData(imovelAguaParaTodos.getDataHabilitacao()));

						if(imovelAguaParaTodos.getIdUsuarioInclusao() != null){
							form.setUsuarioInclusao(contruirInfoUsuario(imovelAguaParaTodos.getIdUsuarioInclusao().getId()));
						}else{
							form.setUsuarioInclusao(null);
						}

						if(imovelAguaParaTodos.getAnoMesReferenciaInicial() != null){
							form.setDataReferenciaFaruramento(imovelAguaParaTodos.getAnoMesReferenciaInicialFormatado());

						}else{
							form.setDataReferenciaFaruramento(null);

						}
					}

					httpServletRequest.setAttribute("tituloTela", TITULO_TELA_MANTER_PROGRAMA_AGUA_PARA_TODOS);
					httpServletRequest.setAttribute("tela", "");

				}else{
					form.setFlagOperacao(form.INSERIR);
					form.setNic("");
					form.setNomeContribuinte("");
				}

				Iterator it = null;

				boolean existeResidencial = false;

				FiltroImovelSubCategoria filtroImovelSubCategoria = new FiltroImovelSubCategoria();
				filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));

				Collection colImovelSubCategoria = fachada.pesquisar(filtroImovelSubCategoria, ImovelSubcategoria.class.getName());

				if(!Util.isVazioOrNulo(colImovelSubCategoria)){
					it = colImovelSubCategoria.iterator();
				}

				String economias = "";

				while(it.hasNext()){
					ImovelSubcategoria subImovelCategoria = (ImovelSubcategoria) it.next();

					FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, subImovelCategoria.getComp_id()
									.getSubcategoria().getId()));

					Subcategoria subCategoria = (Subcategoria) fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName())
									.iterator().next();

					FiltroCategoria filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, subCategoria.getCategoria().getId()));

					Categoria categoria = (Categoria) fachada.pesquisar(filtroCategoria, Categoria.class.getName()).iterator().next();

					if(categoria.getId().equals(Categoria.RESIDENCIAL)){
						existeResidencial = true;
					}

					economias += categoria.getDescricao() + ": " + subImovelCategoria.getQuantidadeEconomias() + " ";
				}

				if(!existeResidencial){
					throw new ActionServletException("atencao.agua.para.todos.nao_imovel_residencial", null, form.getMatricula());
				}

				form.setCategoriasEconomia(economias);

				form.setMatricula(imovel.getId().toString());
				form.setLocalidade(imovel.getLocalidade().getDescricao());
				form.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getId() + " - " + imovel.getLigacaoAguaSituacao().getDescricao());
				form.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId() + " - " + imovel.getLigacaoEsgotoSituacao().getDescricao());
				form.setInscricao(imovel.getInscricaoFormatada());

				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId()));

				Collection colClienteImovel = fachada.pesquisarClienteImovel(filtroClienteImovel);

				it = colClienteImovel.iterator();

				if(it.hasNext()){
					ClienteImovelSimplificado clienteSimples = (ClienteImovelSimplificado) it.next();
					FiltroCliente filtroCliente = new FiltroCliente();
					filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteSimples.getCliente().getId()));
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade("clienteFones");

					Cliente cliente = (Cliente) fachada.pesquisar(filtroCliente, Cliente.class.getName()).iterator().next();

					Collection colFone = cliente.getClienteFones();

					if(colFone != null && !colFone.isEmpty()){
						Iterator itFone = colFone.iterator();

						while(itFone.hasNext()){
							ClienteFone fone = (ClienteFone) itFone.next();
							FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
							filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.ID, fone.getId()));
							filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade("foneTipo");
							fone = (ClienteFone) fachada.pesquisar(filtroClienteFone, ClienteFone.class.getName()).iterator().next();

							if(fone.getFoneTipo().getId().equals(new Integer("1"))){
								String ddd = fone.getDdd() != null ? fone.getDdd() + " - " : "";
								if(fone.getTelefone() != null){
									form.setTelefone(ddd + fone.getTelefone());
								}else{
									form.setTelefone(null);
								}
							}
						}
					}
					form.setNomeCliente(cliente.getNome());
				}

				form.setEndereco(imovel.getEnderecoFormatado());

			}else{
				throw new ActionServletException("atencao.pesquisa.imovel.inexistente", null, form.getMatricula());
			}

		}

		return retorno;
	}

	private String contruirInfoUsuario(Integer idUsuario){

		String retorno = null;

		if(idUsuario != null){
			FiltroUsuario filtroUsuario = new FiltroUsuario();
			filtroUsuario.adicionarParametro(new ParametroSimples(filtroUsuario.ID, idUsuario));

			Usuario usuario = (Usuario) Fachada.getInstancia().pesquisar(filtroUsuario, Usuario.class.getName()).iterator().next();

			if((usuario.getFuncionario() != null) && (usuario.getFuncionario().getId() != null)){
				FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
				filtroFuncionario.adicionarParametro(new ParametroSimples(filtroFuncionario.ID, usuario.getFuncionario().getId()));

				Funcionario funcionario = (Funcionario) Fachada.getInstancia().pesquisar(filtroFuncionario, Funcionario.class.getName())
								.iterator().next();

				retorno = funcionario.getId() + " - " + funcionario.getNome();

			}else{
				retorno = usuario.getLogin() + " - " + usuario.getNomeUsuario();
			}
		}

		return retorno;
	}

}
