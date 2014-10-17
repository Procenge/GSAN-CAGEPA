
package gcom.relatorio.seguranca.acesso;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.FiltroUsuarioGrupo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioGrupo;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioManterUsuario
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioManterUsuario(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_USUARIO);
	}

	@Deprecated
	public RelatorioManterUsuario() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroUsuarioGrupo filtroUsuario = (FiltroUsuarioGrupo) getParametro("filtroUsuario");

		Usuario usuarioParametros = (Usuario) getParametro("usuarioParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterUsuarioBean relatorioBean = null;

		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("usuario");

		filtroUsuario.setConsultaSemLimites(true);

		Collection usuarios = fachada.pesquisar(filtroUsuario, UsuarioGrupo.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if(usuarios != null && !usuarios.isEmpty()){

			// Organizar a cole��o

			Collections.sort((List) usuarios, new Comparator() {

				public int compare(Object a, Object b){

					String usuario1 = ((UsuarioGrupo) a).getUsuario().getNomeUsuario();
					String usuario2 = ((UsuarioGrupo) a).getUsuario().getNomeUsuario();
					return usuario1.compareTo(usuario2);
				}
			});

			// coloca a cole��o de par�metros da analise no iterator
			Iterator usuarioIterator = usuarios.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(usuarioIterator.hasNext()){

				UsuarioGrupo usuario = (UsuarioGrupo) usuarioIterator.next();

				String nomeUsuario = "";
				if(usuario.getUsuario().getNomeUsuario() != null){
					nomeUsuario = usuario.getUsuario().getNomeUsuario();
				}

				String loginUsuario = "";
				if(usuario.getUsuario().getLogin() != null){
					loginUsuario = usuario.getUsuario().getLogin();
				}

				String usuarioGrupo = "";
				if(usuario.getUsuario().getUsuarioGrupos() != null){
					usuarioGrupo = usuario.getGrupo().getId().toString();
				}

				String usuarioTipo = "";
				if(usuario.getUsuario().getUsuarioTipo() != null){
					usuarioTipo = usuario.getUsuario().getUsuarioTipo().getDescricao();
				}
				String unidadeOrganizacional = "";
				if(usuario.getUsuario().getUnidadeOrganizacional().getId() != null){
					unidadeOrganizacional = usuario.getUsuario().getUnidadeOrganizacional().getId().toString();
				}
				String situacaoUsuario = "";
				if(usuario.getUsuario().getUsuarioSituacao().getDescricaoUsuarioSituacao() != null){
					situacaoUsuario = usuario.getUsuario().getUsuarioSituacao().getDescricaoUsuarioSituacao();
				}

				String usuarioAbrangencia = "";
				if(usuario.getUsuario().getUsuarioAbrangencia().getDescricao() != null){
					usuarioAbrangencia = usuario.getUsuario().getUsuarioAbrangencia().getDescricao();
				}

				String dataCadastroAcesso = "";

				if(usuario.getUsuario().getDataCadastroAcesso() != null){
					dataCadastroAcesso = Util.formatarData(usuario.getUsuario().getDataCadastroAcesso());
				}

				String dataExpiracaoAcesso = "";
				if(usuario.getUsuario().getDataExpiracaoAcesso() != null){
					dataExpiracaoAcesso = Util.formatarData(usuario.getUsuario().getDataExpiracaoAcesso());
				}

				relatorioBean = new RelatorioManterUsuarioBean(nomeUsuario, loginUsuario, usuarioGrupo, usuarioTipo, unidadeOrganizacional,
								situacaoUsuario, usuarioAbrangencia, dataCadastroAcesso, dataExpiracaoAcesso);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// // remove elementos repetidos da collection
		// HashSet<RelatorioManterUsuarioBean> relatorioBeansSet = new
		// HashSet<RelatorioManterUsuarioBean>();
		// relatorioBeansSet.addAll(relatorioBeans);
		// relatorioBeans.clear();
		// relatorioBeans.addAll(relatorioBeansSet);

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MANTER_USUARIO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MANTER_USUARIO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		// retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroUsuarioGrupo)
		// getParametro("filtroUsuario"),
		// UsuarioGrupo.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterUsuario", this);

	}

}