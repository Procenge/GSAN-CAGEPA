
package gcom.gui.faturamento.guiapagamento;

import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3027] Filtrar Guia de Pagamento
 * 
 * @author Anderson Italo
 * @date 25/10/2011
 */
public class ExibirFiltrarGuiaPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarGuiaPagamento");
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarGuiaPagamentoActionForm formulario = (FiltrarGuiaPagamentoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Verificação para marcar ou não o checkBox Atualizar
		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){

			sessao.setAttribute("indicadorAtualizar", ConstantesSistema.SIM.toString());
		}

		// Variável que indica quando o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");
		String objetoConsultaRA = httpServletRequest.getParameter("ConsultarRA");

		// Imóvel da Guia
		if((!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1"))
						|| !Util.isVazioOuBranco(formulario.getMatriculaImovel())){

			// [UC0013] - Pesquisar Imovel
			this.pesquisarImovel(formulario, httpServletRequest);
		}

		// Tipo da Relação do Cliente
		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();
		filtroClienteRelacaoTipo.setCampoOrderBy(FiltroClienteRelacaoTipo.DESCRICAO);

		Collection<ClienteRelacaoTipo> colecaoClienteRelacaoTipo = fachada.pesquisar(filtroClienteRelacaoTipo, ClienteRelacaoTipo.class
						.getName());

		if(!Util.isVazioOrNulo(colecaoClienteRelacaoTipo)){

			httpServletRequest.setAttribute("colecaoClienteRelacaoTipo", colecaoClienteRelacaoTipo);
		}else{

			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "CLIENTE_RELACAO_TIPO");
		}

		// Exibir o campo "No. Contrato Parcel. Órgão Público"
		try{
			if(!ParametroFaturamento.P_TIPO_DEBITO_PARCELAMENTO_ORGAO_PUBLICO.executar()
							.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				sessao.setAttribute("exibirNuContratoParcOrgaoPublico", ConstantesSistema.SIM);
			}else{
				sessao.removeAttribute("exibirNuContratoParcOrgaoPublico");
			}

		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		// Cliente Reponsável
		if((!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("2"))
						|| !Util.isVazioOuBranco(formulario.getCodigoClienteResponsavel())){

			// [UC0012] - Pesquisar Cliente
			this.pesquisarClienteResponsavel(formulario, httpServletRequest);
		}

		// Cliente da Guia
		if((!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("3"))
						|| !Util.isVazioOuBranco(formulario.getCodigoClienteGuia())){

			// [UC0012] - Pesquisar Cliente
			this.pesquisarClienteGuia(formulario, httpServletRequest);
		}

		// Localidade
		if((!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("4"))
						|| !Util.isVazioOuBranco(formulario.getIdLocalidade())){

			// [UC0023 - Pesquisar Localidade]
			this.pesquisarLocalidade(formulario, httpServletRequest);
		}

		// Registro de Atendimeto
		if((!Util.isVazioOuBranco(objetoConsultaRA) && objetoConsultaRA.trim().equals("S"))
						|| !Util.isVazioOuBranco(formulario.getNumeroRA())){

			// Registro de Atendimeto
			this.pesquisarRegistroAtendimento(formulario);
		}

		// Tipo do Documento
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.DESCRICAO);

		Collection<DocumentoTipo> colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

		if(!Util.isVazioOrNulo(colecaoDocumentoTipo)){

			httpServletRequest.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);
		}else{

			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "DOCUMENTO_TIPO");
		}

		// Tipo do Débito
		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
		filtroDebitoTipo.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);

		Collection<DebitoTipo> colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

		if(!Util.isVazioOrNulo(colecaoDebitoTipo)){

			httpServletRequest.setAttribute("colecaoDebitoTipo", colecaoDebitoTipo);
		}else{

			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "DEBITO_TIPO");
		}

		return retorno;
	}

	/**
	 * [UC0013 - Pesquisar Imóvel]
	 * 
	 * @author Anderson Italo
	 * @date 25/10/2011
	 */
	private void pesquisarImovel(FiltrarGuiaPagamentoActionForm form, HttpServletRequest request){

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, form.getMatriculaImovel()));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);

		Collection colecaoImovel = Fachada.getInstancia().pesquisar(filtroImovel, Imovel.class.getName());

		// [FS0001] - Verificar existência da matrícula do imóvel
		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			form.setMatriculaImovel(imovel.getId().toString());
			form.setInscricaoImovel(imovel.getInscricaoFormatada());
			request.setAttribute("matriculaImovelEncontrada", true);

		}else{

			form.setMatriculaImovel("");
			form.setInscricaoImovel("Matrícula inexistente");
		}
	}

	/**
	 * [UC0012 - Pesquisar Cliente]
	 * 
	 * @author Anderson Italo
	 * @date 25/10/2011
	 */
	private void pesquisarClienteResponsavel(FiltrarGuiaPagamentoActionForm form, HttpServletRequest request){

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, new Integer(form.getCodigoClienteResponsavel())));

		Collection colecaoCliente = Fachada.getInstancia().pesquisar(filtroCliente, Cliente.class.getName());

		// [FS0002 – Verificar existência do código do cliente
		if(colecaoCliente != null && !colecaoCliente.isEmpty()){

			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

			form.setCodigoClienteResponsavel(cliente.getId().toString());
			form.setNomeClienteResponsavel(cliente.getNome());
			request.setAttribute("codigoClienteResponsavelEncontrado", true);

		}else{

			form.setCodigoClienteResponsavel("");
			form.setNomeClienteResponsavel("Cliente inexistente");
		}
	}

	/**
	 * [UC0012 - Pesquisar Cliente]
	 * 
	 * @author Anderson Italo
	 * @date 25/10/2011
	 */
	private void pesquisarClienteGuia(FiltrarGuiaPagamentoActionForm form, HttpServletRequest request){

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, new Integer(form.getCodigoClienteGuia())));

		Collection colecaoCliente = Fachada.getInstancia().pesquisar(filtroCliente, Cliente.class.getName());

		// [FS0002 – Verificar existência do código do cliente
		if(colecaoCliente != null && !colecaoCliente.isEmpty()){

			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

			form.setCodigoClienteGuia(cliente.getId().toString());
			form.setNomeClienteGuia(cliente.getNome());
			request.setAttribute("codigoClienteResponsavelEncontrado", true);

		}else{

			form.setCodigoClienteGuia("");
			form.setNomeClienteGuia("Cliente inexistente");
		}
	}

	/**
	 * [UC0023 - Pesquisar Localidade]
	 * 
	 * @author Anderson Italo
	 * @date 26/10/2011
	 */
	private void pesquisarLocalidade(FiltrarGuiaPagamentoActionForm form, HttpServletRequest request){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(form.getIdLocalidade())));

		Collection<Localidade> colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// [FS0008 – Verificar existência da localidade]
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setIdLocalidade(localidade.getId().toString());
			form.setNomeLocalidade(localidade.getDescricao());
			request.setAttribute("idLocalidadeEncontrado", true);

		}else{

			form.setIdLocalidade("");
			form.setNomeLocalidade("Localidade Inexistente");
		}
	}

	private void pesquisarRegistroAtendimento(FiltrarGuiaPagamentoActionForm form){

		// Filtro para obter o localidade ativo de id informado
		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();

		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, new Integer(form.getNumeroRA())));
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao");

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoRegistros = Fachada.getInstancia().pesquisar(filtroRegistroAtendimento, RegistroAtendimento.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoRegistros != null && !colecaoRegistros.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			RegistroAtendimento registroAtendimento = (RegistroAtendimento) Util.retonarObjetoDeColecao(colecaoRegistros);

			form.setNumeroRA(registroAtendimento.getId().toString());
			form.setDescricaoRA(registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao());

		}else{

			form.setDescricaoRA("RA - Registro de Atendimento inexistente");
			form.setNumeroRA("");

		}
	}
}
