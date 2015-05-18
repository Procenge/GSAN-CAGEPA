
package gcom.gui.relatorio.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Fatura;
import gcom.faturamento.conta.FiltroFatura;
import gcom.interceptor.RegistradorOperacao;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioFaturaClienteResponsavel;
import gcom.seguranca.acesso.Argumento;
import gcom.seguranca.acesso.DocumentoEmissaoEfetuada;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmitirRelatorioFaturaClienteResponsavelAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<Fatura> idsFaturas = (Collection<Fatura>) sessao.getAttribute("idsFaturas");
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		String nomeEmpresa = sistemaParametro.getNomeAbreviadoEmpresa();

		RelatorioFaturaClienteResponsavel relatorioFaturaClienteResponsavel = new RelatorioFaturaClienteResponsavel(usuario);

		relatorioFaturaClienteResponsavel.setUsuario(usuario);
		relatorioFaturaClienteResponsavel.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		relatorioFaturaClienteResponsavel.addParametro("nomeEmpresa", nomeEmpresa);
		relatorioFaturaClienteResponsavel.addParametro("idsFaturas", idsFaturas);

		try{
			retorno = processarExibicaoRelatorio(relatorioFaturaClienteResponsavel, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(RelatorioVazioException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		DocumentoEmissaoEfetuada documentoEmissaoEfetuada = new DocumentoEmissaoEfetuada();

		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		if(!Util.isVazioOrNulo(idsFaturas)){

			Iterator it = idsFaturas.iterator();
			while(it.hasNext()){
				Integer idfatura = (Integer) it.next();

				FiltroFatura filtroFatura = new FiltroFatura();
				filtroFatura.adicionarParametro(new ParametroSimples(FiltroFatura.ID, idfatura));
				filtroFatura.adicionarCaminhoParaCarregamentoEntidade(FiltroFatura.CLIENTE);
				Fatura fatura = (Fatura) Util
								.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroFatura, Fatura.class.getName()));

				documentoEmissaoEfetuada.setCliente(fatura.getCliente());
				documentoEmissaoEfetuada.setUsuario(usuario);
				DocumentoTipo documentoTipo = new DocumentoTipo();
				documentoTipo.setId(DocumentoTipo.FATURA_CLIENTE);
				documentoEmissaoEfetuada.setDocumentoTipo(documentoTipo);
				documentoEmissaoEfetuada.setUltimaAlteracao(new Date());

				Argumento argumento = new Argumento();
				argumento.setId(Argumento.CLIENTE);

				RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EMITIR_FATURA_CLIENTE_RESPONSAVEL,
								fatura.getCliente().getId(), argumento, fatura.getCliente().getId(),
								new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
				RegistradorOperacao.set(registradorOperacao);
				documentoEmissaoEfetuada.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(documentoEmissaoEfetuada);
				this.getFachada().inserir(documentoEmissaoEfetuada);

			}

		}


		// ------------ REGISTRAR TRANSAÇÃO ----------------

		return retorno;
	}

}
