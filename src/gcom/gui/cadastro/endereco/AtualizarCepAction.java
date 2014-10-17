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
import gcom.util.ControladorException;
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
 * Atualizar Cep
 * 
 * @author Ado Rocha
 * @date jul/2013
 */

public class AtualizarCepAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		AtualizarCepActionForm form = (AtualizarCepActionForm) actionForm;

		// Verifica se o cep já existe

		FiltroCep filtroCepVerificacao = new FiltroCep();
		filtroCepVerificacao.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO, new Integer(form.getCodigo())));
		filtroCepVerificacao.adicionarParametro(new ComparacaoTextoCompleto(FiltroCep.MUNICIPIO, form.getMunicipio()));
		filtroCepVerificacao.adicionarParametro(new ComparacaoTextoCompleto(FiltroCep.SIGLA, form.getSigla()));

		if(form.getBairro() != null && !form.getBairro().equalsIgnoreCase("")){
			filtroCepVerificacao.adicionarParametro(new ParametroSimples(FiltroCep.BAIRRO, form.getBairro()));
		}

		if(form.getCodigoLogradouro() != null && !form.getCodigoLogradouro().equalsIgnoreCase("")){
			filtroCepVerificacao.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO_LOGRADOURO, new Integer(form
							.getCodigoLogradouro())));
		}

		if(form.getCodigoLocalidade() != null && !form.getCodigoLocalidade().equalsIgnoreCase("")){
			filtroCepVerificacao.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO_LOCALIDADE, new Integer(form
							.getCodigoLocalidade())));
		}

		Collection colecaoCepVerificacao = (Collection) fachada.pesquisar(filtroCepVerificacao, Cep.class.getName());
		Cep verificaCepNaBase = (Cep) Util.retonarObjetoDeColecao(colecaoCepVerificacao);

		// Fim de Verifica se o cep já existe

		// Verificação de dados do objeto na base com os dados do formulário
		boolean comparadorObjeto = true;

		if(verificaCepNaBase != null){
			if(verificaCepNaBase.getCepId() != null && form.getId() != null
							&& verificaCepNaBase.getCepId().toString().equalsIgnoreCase(form.getId().toString())){
					comparadorObjeto = false;
			}
		}else{
			comparadorObjeto = false;
		}

		if(comparadorObjeto){
			throw new ActionServletException("atencao.cep.cadastrado");
		}else{

		Cep cep = new Cep();
		cep.setCepId(form.getId());
		
		// Campos obrigatórios 
		if(form.getCodigo() == null || form.getCodigo().trim().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_codigo_cep");
		}else{
			cep.setCodigo(Util.converterStringParaInteger(form.getCodigo()));
		}
		
		if(form.getSigla() == null || form.getSigla().trim().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_unidade_federativa");
		}else{
			cep.setSigla(form.getSigla());
		}
		
		if(form.getMunicipio() == null || form.getMunicipio().trim().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_municipio");
		}else{
			cep.setMunicipio(form.getMunicipio());
		}

		if(form.getFaixaInicial() == null || form.getFaixaInicial().trim().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_faixa_inicial_cep");
		}
				
		if(form.getFaixaFinal() == null || form.getFaixaFinal().trim().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_faixa_final_cep");
		}
			// Fim de Campos Obrigatórios
		
		cep.setBairro(form.getBairro());
		cep.setIndicadorUso(form.getIndicadorUso());
		cep.setDescricaoIntervaloNumeracao(form.getIntervaloNumeracao());
		cep.setCodigoLadoCep(form.getCodigoLadoCep());
		CepTipo cepTipo = new CepTipo();
		cepTipo.setId(form.getCepTipo());
		cepTipo.setUltimaAlteracao(new Date());
		cep.setCepTipo(cepTipo);
		cep.setUltimaAlteracao(new Date());
		
			if(form.getCodigoLogradouro() != null && !form.getCodigoLogradouro().equalsIgnoreCase("")){
				cep.setCodigoLogradouro(Util.converterStringParaInteger(form.getCodigoLogradouro()));
			}

		// Setando os valores de Logradouro e Tipo de Logradouro
		if(form.getCodigoLogradouro() != null && !form.getCodigoLogradouro().equalsIgnoreCase("")){
			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, new Integer(form.getCodigoLogradouro())));

			// Pesquisa de acordo com os parâmetros informados no filtro
			Collection colecaoLogradouro = Fachada.getInstancia().pesquisar(filtroLogradouro, Logradouro.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a coleção
			if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){

				// Obtém o objeto da coleção pesquisada
				Logradouro objetoLogradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);
				cep.setLogradouro(objetoLogradouro.getNome());

				// Obtém o tipo do logradouro
				FiltroLogradouroTipo filtroLogradouroTipo = new FiltroLogradouroTipo();

				filtroLogradouroTipo.adicionarParametro(new ParametroSimples(FiltroLogradouroTipo.ID, new Integer(objetoLogradouro
								.getLogradouroTipo().getId())));

				// Pesquisa de acordo com os parâmetros informados no filtro
				Collection colecaoLogradouroTipo = Fachada.getInstancia().pesquisar(filtroLogradouroTipo, LogradouroTipo.class.getName());

				LogradouroTipo logradouroTipo = (LogradouroTipo) Util.retonarObjetoDeColecao(colecaoLogradouroTipo);

				cep.setDescricaoTipoLogradouro(logradouroTipo.getDescricao());
			}
		}
		// Fim de setando os valores de Logradouro e Tipo Logradouro

		// Verificando se a faixa inicial é menor que a faixa final
		Integer faixaInicial = Util.converterStringParaInteger(form.getFaixaInicial());
		
		Integer faixaFinal = Util.converterStringParaInteger(form.getFaixaFinal());
		
		if(faixaInicial < faixaFinal){
			cep.setNumeroFaixaIncial(faixaInicial);
			cep.setNumeroFaixaFinal(faixaFinal);
		}else{
			throw new ActionServletException("atencao.faixa_inicial_maior_final");
		}
		
			if(form.getCodigoLocalidade() != null && !form.getCodigoLocalidade().equalsIgnoreCase("")){
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(form.getCodigoLocalidade())));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.MUNICIPIO_ID, form.getCodigoMunicipio()));

				Collection colecaoLocalidade = (Collection) fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
				Localidade localidadeNaBase = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				if(localidadeNaBase != null){
					cep.setCodigoLocalidade(Util.converterStringParaInteger(form.getCodigoLocalidade()));
				}else{
					throw new ActionServletException("atencao.localidade.inexistente.dados.informados");
				}
			}

		// Verifica se o cep já foi atualizado por outro usuário
		// durante esta atualização
		FiltroCep filtroCep = new FiltroCep();
		filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, new Integer(cep.getCepId())));

		Collection colecaoCep = (Collection)fachada.pesquisar(filtroCep, Cep.class.getName());
		Cep cepNaBase = (Cep) Util.retonarObjetoDeColecao(colecaoCep);

		if(cepNaBase.getUltimaAlteracao().after(cep.getUltimaAlteracao())){
			try {
				throw new ControladorException("atencao.atualizacao.timestamp");
			} catch (ControladorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CEP_ATUALIZAR, new UsuarioAcaoUsuarioHelper(
						usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CEP_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		cep.setOperacaoEfetuada(operacaoEfetuada);
		cep.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(cep);
		// ------------ FIM DE REGISTRAR TRANSAÇÃO ----------------

		fachada.atualizar(cep);

		montarPaginaSucesso(httpServletRequest, "CEP de código  " + form.getCodigo() + " atualizado com sucesso.", "Atualizar outro CEP",
						"exibirFiltrarCepAction.do?menu=sim");

		return retorno;
	}
	}
}
