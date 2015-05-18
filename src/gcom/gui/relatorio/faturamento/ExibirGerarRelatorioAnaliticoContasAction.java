package gcom.gui.relatorio.faturamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.conta.ContaMotivoRetificacao;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.faturamento.conta.FiltroMotivoRetificacaoConta;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.FiltroDebitoCreditoSituacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirGerarRelatorioAnaliticoContasAction
				extends GcomAction {

	private Collection colecaoPesquisa = null;

	private String localidadeID = null;

	private String setorComercialCD = null;

	private String setorComercialID = null;

	private String quadraNM = null;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioAnaliticoContas");

		GerarRelatorioAnaliticoContasActionForm form = (GerarRelatorioAnaliticoContasActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Grupo Faturamento
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.DESCRICAO);

		Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);

		//

		// -----------Parte 2 Pesqusia Localidade
		if((form != null && form.getIdLocalidade() != null && !form.getIdLocalidade().equals(""))
						&& (form.getNomeLocalidade() == null || form.getNomeLocalidade().equals(""))){
			pesquisarLocalidade(form, fachada, httpServletRequest);
		}
		// -----------Parte 3 Pesquisa Setor Comercial
		if((form != null && form.getSetorComercial() != null && !form.getSetorComercial().equals(""))){
			pesquisarSetorComercial(form, fachada, httpServletRequest);
		}
		// -----------Parte 4 Pesquisa Quadra
		if((form != null && form.getIdQuadra() != null && !form.getIdQuadra().equals(""))){
			pesquisarQuadra(form, fachada, httpServletRequest);
		}
		// ----------Parte 5 Adicionar
		String adicionar = httpServletRequest.getParameter("adicionar");
		if(adicionar != null && !adicionar.trim().equals("")){
			if(adicionar.trim().equalsIgnoreCase("localidade") && form.getIdLocalidade() != null && !form.getIdLocalidade().equals("")){
				Collection colecaoLocalidades = (Collection) sessao.getAttribute("colecaoLocalidades");
				Localidade localidade = new Localidade();
				localidade.setId(new Integer(form.getIdLocalidade()));
				localidade.setDescricao(form.getNomeLocalidade());
				if(!colecaoLocalidades.contains(localidade)){
					colecaoLocalidades.add(localidade);
					form.setIdLocalidade("");
					form.setNomeLocalidade("");
					if(colecaoLocalidades.size() > 1){
						sessao.setAttribute("bloqueiaSetor", "s");
						sessao.setAttribute("bloqueiaQuadra", "s");
					}else if(colecaoLocalidades.size() == 1){
						sessao.removeAttribute("bloqueiaSetor");
						sessao.removeAttribute("bloqueiaQuadra");
					}
				}
			}else if(adicionar.trim().equalsIgnoreCase("setor") && form.getSetorComercial() != null
							&& !form.getSetorComercial().equals("")){
				Collection colecaoSetor = (Collection) sessao.getAttribute("colecaoSetor");
				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo(new Integer(form.getSetorComercial()));
				setorComercial.setDescricao(form.getSetorComercialNome());
				if(form.getSetorComercial() != null && !form.getSetorComercial().trim().equals("")){
					setorComercial.setId(new Integer(form.getSetorComercial()));
				}
				if(!colecaoSetor.contains(setorComercial)){
					colecaoSetor.add(setorComercial);
					form.setSetorComercial("");
					form.setSetorComercialNome("");
					if(colecaoSetor.size() > 1){
						sessao.setAttribute("bloqueiaQuadra", "s");
						sessao.setAttribute("bloqueiaLocalidade", "s");
						sessao.removeAttribute("colecaoQuadras");
						sessao.setAttribute("colecaoQuadras", new ArrayList());
					}else if(colecaoSetor.size() == 1){
						sessao.removeAttribute("bloqueiaQuadra");
						sessao.setAttribute("bloqueiaLocalidade", "s");
					}
				}
			}else if(adicionar.trim().equalsIgnoreCase("quadra")){
				Collection colecaoQuadras = (Collection) sessao.getAttribute("colecaoQuadras");
				Quadra quadra = new Quadra();
				if(form.getIdQuadra() != null && !form.getIdQuadra().trim().equals("")){
					quadra.setId(new Integer(form.getIdQuadra()));
					quadra.setNumeroQuadra(new Integer(form.getDescricaoQuadra()));
					if(!colecaoQuadras.contains(quadra)){
						colecaoQuadras.add(quadra);
						form.setIdQuadra("");
						sessao.setAttribute("bloqueiaLocalidade", "s");
					}
				}else{
					throw new ActionServletException("atencao.quadra.inexistente");
				}
			}
		}else{
			if(sessao.getAttribute("colecaoLocalidades") == null){
				sessao.setAttribute("colecaoLocalidades", new ArrayList());
			}
			if(sessao.getAttribute("colecaoSetor") == null){
				sessao.setAttribute("colecaoSetor", new ArrayList());
			}
			if(sessao.getAttribute("colecaoQuadras") == null){
				sessao.setAttribute("colecaoQuadras", new ArrayList());
			}
		}

		String remover = httpServletRequest.getParameter("remover");
		if(remover != null && !remover.trim().equals("")){
			if(remover.equalsIgnoreCase("localidade")){
				Collection localidades = (Collection) sessao.getAttribute("colecaoLocalidades");
				Iterator iterator = localidades.iterator();
				String idRemocaoLocalidade = httpServletRequest.getParameter("idLocalidadeRemocao");
				while(iterator.hasNext()){
					Localidade localidade = (Localidade) iterator.next();
					if(localidade.getId().toString().equals(idRemocaoLocalidade)){
						iterator.remove();
					}
				}
			}
		}



		// Carrega as coleções que serão exibidas na tela
		// Gerência Regional
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.NOME);

		Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);



		// categoria
		FiltroCategoria filtroCategoria = new FiltroCategoria();
		filtroCategoria.setCampoOrderBy("descricaoAbreviada");
		Collection colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
		sessao.setAttribute("colecaoCategoria", colecaoCategoria);


		// cliente
		String idCliente = form.getIdCliente();

		if(idCliente != null && !idCliente.equals("")){
			FiltroCliente FiltroCliente = new FiltroCliente();

			FiltroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));

			Collection colecaoCliente = fachada.pesquisar(FiltroCliente, Cliente.class.getName());

			if(colecaoCliente != null && !colecaoCliente.isEmpty()){
				Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

				form.setIdCliente(cliente.getId().toString());
				form.setNomeCliente(cliente.getNome());
				httpServletRequest.setAttribute("nomeCampo", "idCliente");

			}else{
				form.setIdCliente("");
				form.setNomeCliente("Cliente Inexistente");

				httpServletRequest.setAttribute("idClienteNaoEncontrado", "true");

				httpServletRequest.setAttribute("nomeCampo", "idCliente");
			}
		}


		// Imovel
		String idImovel = form.getIdImovel();

		if(idImovel != null && !idImovel.equals("")){
			FiltroImovel filtroImovel = new FiltroImovel();

			filtroImovel.adicionarParametro(new ParametroSimples(filtroImovel.ID, idImovel));

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel != null && !colecaoImovel.isEmpty()){
				Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

				form.setIdImovel(imovel.getId().toString());
				form.setInscricao(fachada.pesquisarInscricaoImovel(Util.obterInteger(idImovel), true));
				httpServletRequest.setAttribute("nomeCampo", "idImovel");

			}else{
				form.setIdCliente("");
				form.setInscricao("Imóvel Inexistente");

				httpServletRequest.setAttribute("idImovelNaoEncontrada", "true");

				httpServletRequest.setAttribute("nomeCampo", "idImovel");
			}
		}

		// Situação da conta
		FiltroDebitoCreditoSituacao filtroDebitoCreditoSituacao = new FiltroDebitoCreditoSituacao(FiltroDebitoCreditoSituacao.ID);

		Collection colecaoSituacao = fachada.pesquisar(filtroDebitoCreditoSituacao, DebitoCreditoSituacao.class.getName());

		sessao.setAttribute("colecaoSituacao", colecaoSituacao);



		// Motivo de Revisão
		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao(FiltroContaMotivoRevisao.ID);

		Collection colecaoContaMotivoRevisao = fachada.pesquisar(filtroContaMotivoRevisao, ContaMotivoRevisao.class.getName());

		sessao.setAttribute("colecaoContaMotivoRevisao", colecaoContaMotivoRevisao);


		// Motivo Retificacao
		FiltroMotivoRetificacaoConta filtroMotivoRetificacaoConta = new FiltroMotivoRetificacaoConta(FiltroMotivoRetificacaoConta.ID);

		Collection colecaoMotivoRetificacao = fachada.pesquisar(filtroMotivoRetificacaoConta, ContaMotivoRetificacao.class.getName());

		sessao.setAttribute("colecaoMotivoRetificacao", colecaoMotivoRetificacao);


		

		return retorno;

	}

	private void pesquisarLocalidade(GerarRelatorioAnaliticoContasActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){


		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		localidadeID = (String) form.getIdLocalidade();
		String nomeLocalidade = form.getNomeLocalidade();

		if(localidadeID != null && !localidadeID.equals("") && (nomeLocalidade == null || nomeLocalidade.equals(""))){

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Localidade nao encontrada
				// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
				// formulário
				form.setIdLocalidade("");
				form.setNomeLocalidade("localidade inexistente");
				httpServletRequest.setAttribute("codigoLocalidadeNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeFiltro");
				// httpServletRequest.setAttribute("corLocalidadeOrigem","exception");
			}else{
				Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setIdLocalidade(String.valueOf(objetoLocalidade.getId()));
				form.setNomeLocalidade(objetoLocalidade.getDescricao());
			}
		}
	}

	// PESQUISAR DE SETOR COMERCIAL
	private void pesquisarSetorComercial(GerarRelatorioAnaliticoContasActionForm form, Fachada fachada,
					HttpServletRequest httpServletRequest){

		if(form.getIdLocalidade() == null || form.getIdLocalidade() == ""){
			throw new ActionServletException("atencao.informe_campo", null, "Localidade");
		}

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		// Recebe o valor do campo localidadeID do formulário.
		localidadeID = (String) form.getIdLocalidade();

		setorComercialCD = (String) form.getSetorComercial();

		// O campo localidadeOrigemID será obrigatório
		if(setorComercialCD != null && !setorComercialCD.trim().equalsIgnoreCase("")){

			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){
				// Adiciona o id da localidade que está no formulário para
				// compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));
			}
			// Adiciona o código do setor comercial que esta no formulário
			// para compor a pesquisa.
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna setorComercial
			colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){

				form.setSetorComercial("");
				form.setSetorComercialNome("Setor comercial inexistente.");
				httpServletRequest.setAttribute("codigoSetorComercialNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialFiltro");
			}else{
				SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setSetorComercial(String.valueOf(objetoSetorComercial.getCodigo()));
				form.setSetorComercialNome(objetoSetorComercial.getDescricao());
			}
		}
	}

	// PESQUISA DE QUADRA
	private void pesquisarQuadra(GerarRelatorioAnaliticoContasActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		String setorID = null;

		if(form.getSetorComercial() == null || form.getSetorComercial() == ""){
			throw new ActionServletException("atencao.informe_campo", null, "Setor Comercial");
		}

		setorID = (String) form.getSetorComercial();
		quadraNM = (String) form.getIdQuadra();
		
		// Os campos setorComercialOrigemCD e setorComercialID serão
		// obrigatórios
		if(quadraNM != null && !quadraNM.trim().equalsIgnoreCase("")){

			if(setorID != null && !setorID.trim().equalsIgnoreCase("")){
				// Adiciona o id do setor comercial que está no formulário
				// para compor a pesquisa.
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, setorID));
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Setor Comercial");
			}

			// Adiciona o número da quadra que esta no formulário para compor a pesquisa.
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNM));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna quadra
			colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Quadra nao encontrada
				// Limpa os campos quadraOrigemNM e quadraOrigemID do
				// formulário
				form.setIdQuadra("");
				form.setDescricaoQuadra("");
				// Mensagem de tela
				form.setDescricaoQuadra("QUADRA INEXISTENTE.");
				httpServletRequest.setAttribute("idQuadraNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idQuadra");
			}else{
				Quadra objetoQuadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setDescricaoQuadra(String.valueOf(objetoQuadra.getDescricao()));
				form.setIdQuadra(String.valueOf(objetoQuadra.getNumeroQuadra()));


			}
		}
	}
}
