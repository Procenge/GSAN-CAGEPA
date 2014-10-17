
package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.*;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inserir CEP
 * 
 * @author Ado Rocha
 * @date jul/2013
 */
public class InserirCepAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Recuperando dados
		InserirCepActionForm form = (InserirCepActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		String codigoCep = form.getCodigo();
		String sigla = form.getSigla();
		String municipio = form.getMunicipio();
		String bairro = form.getBairro();
		String logradouro = form.getLogradouro();
		String codigoLogradouro = form.getCodigoLogradouro();
		String descricaoTipoLogradouro = form.getDescricaoTipoLogradouro();
		String codigoLocalidade = form.getCodigoLocalidade();

		if(form.getCodigoLogradouro() != null && !form.getCodigoLogradouro().equalsIgnoreCase("")){
			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, new Integer(form.getCodigoLogradouro())));

			// Pesquisa de acordo com os parâmetros informados no filtro
			Collection colecaoLogradouro = Fachada.getInstancia().pesquisar(filtroLogradouro, Logradouro.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a coleção
			if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){

				// Obtém o objeto da coleção pesquisada
				Logradouro objetoLogradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);
				logradouro = objetoLogradouro.getNome();

				// Obtém o tipo do logradouro
				FiltroLogradouroTipo filtroLogradouroTipo = new FiltroLogradouroTipo();

				filtroLogradouroTipo.adicionarParametro(new ParametroSimples(FiltroLogradouroTipo.ID, new Integer(objetoLogradouro
								.getLogradouroTipo().getId())));

				// Pesquisa de acordo com os parâmetros informados no filtro
				Collection colecaoLogradouroTipo = Fachada.getInstancia().pesquisar(filtroLogradouroTipo, LogradouroTipo.class.getName());

				LogradouroTipo logradouroTipo = (LogradouroTipo) Util.retonarObjetoDeColecao(colecaoLogradouroTipo);

				descricaoTipoLogradouro = logradouroTipo.getDescricao();
			}
		}

		// Verifica se o cep já existe

		FiltroCep filtroCep = new FiltroCep();
		filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO, new Integer(codigoCep)));
		filtroCep.adicionarParametro(new ComparacaoTextoCompleto(FiltroCep.MUNICIPIO, municipio));
		filtroCep.adicionarParametro(new ComparacaoTextoCompleto(FiltroCep.SIGLA, sigla));

		if(bairro != null && !bairro.equalsIgnoreCase("")){
			filtroCep.adicionarParametro(new ComparacaoTextoCompleto(FiltroCep.BAIRRO, bairro));
		}

		if(codigoLogradouro != null && !codigoLogradouro.equalsIgnoreCase("")){
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO_LOGRADOURO, new Integer(codigoLogradouro)));
		}
		
		if(codigoLocalidade != null && !codigoLocalidade.equalsIgnoreCase("")){
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO_LOCALIDADE, new Integer(codigoLocalidade)));
		}

		Collection colecaoCep = (Collection) fachada.pesquisar(filtroCep, Cep.class.getName());
		Cep cepNaBase = (Cep) Util.retonarObjetoDeColecao(colecaoCep);

		if(cepNaBase != null){
			throw new ActionServletException("atencao.cep.cadastrado");
		}else{

			// Montando o objeto CEP
			Cep cep = new Cep();
			cep.setCodigo(Util.converterStringParaInteger(codigoCep));
			cep.setSigla(sigla);
			cep.setMunicipio(municipio);
			cep.setBairro(bairro);
			cep.setLogradouro(logradouro);
			cep.setDescricaoTipoLogradouro(descricaoTipoLogradouro);
			cep.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
			cep.setUltimaAlteracao(new Date());
			CepTipo cepTipo = new CepTipo();
			cepTipo.setId(CepTipo.INICIAL);
			cep.setCepTipo(cepTipo);

			if(codigoLogradouro != null && !codigoLogradouro.equalsIgnoreCase("")){
				cep.setCodigoLogradouro(Util.converterStringParaInteger(codigoLogradouro));
			}

			if(codigoLocalidade != null && !codigoLocalidade.equalsIgnoreCase("")){
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(codigoLocalidade)));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.MUNICIPIO_ID, form.getCodigoMunicipio()));

				Collection colecaoLocalidade = (Collection) fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
				Localidade localidadeNaBase = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				if(localidadeNaBase != null){
					cep.setCodigoLocalidade(Util.converterStringParaInteger(codigoLocalidade));
				}else{
					throw new ActionServletException("atencao.localidade.inexistente.dados.informados");
				}
			}
			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CEP_INSERIR, new UsuarioAcaoUsuarioHelper(
							usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_CEP_INSERIR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			cep.setOperacaoEfetuada(operacaoEfetuada);
			cep.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(cep);
			// ------------ FIM DE REGISTRAR TRANSAÇÃO ----------------

			fachada.inserir(cep);
		}

		montarPaginaSucesso(httpServletRequest, "CEP de código  " + form.getCodigo() + " inserido com sucesso.", "Inserir outro CEP",
						"exibirInserirCepAction.do?menu=sim");

		return retorno;
	}
}