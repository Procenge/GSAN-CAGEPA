
package gcom.util.parametrizacao.cadastro;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.RepositorioImovelHBM;
import gcom.cobranca.IRepositorioCobranca;
import gcom.cobranca.RepositorioCobrancaHBM;
import gcom.util.*;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;

import javax.ejb.CreateException;

import br.com.procenge.parametrosistema.api.ParametroSistema;

public class ExecutorParametrosCadastro
				implements ExecutorParametro {

	private static final ExecutorParametrosCadastro instancia = new ExecutorParametrosCadastro();

	private IRepositorioImovel repositorioImovel = null;

	private IRepositorioCobranca repositorioCobranca;

	private ExecutorParametrosCadastro() {

		repositorioImovel = RepositorioImovelHBM.getInstancia();
	}

	public void ejbCreate() throws CreateException{

		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();
	}

	public static ExecutorParametrosCadastro getInstancia(){

		return instancia;
	}

	/**
	 * Retorna a instância de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	protected ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorCadastro
	 * 
	 * @return O valor de controladorCadastro
	 */
	protected ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * [UC0269] Consultar Resumo das Ligações / Economias
	 * Retorna o metodo obterSubConsultaDetalheResumoLigacaoEconomia de acordo com a Empresa DESO.
	 * 
	 * @author Josenilldo Neves
	 * @created 30/01/2013
	 */
	public Object[] execParamObterSubConsultaDetalheResumoLigacaoEconomiaModelo1(ParametroSistema parametroSistema, Integer grupoDetalhe)
					throws ControladorException{

		Object[] dadosRetorno = new Object[3];

		String clusulaGrupoDetalhe = null;
		Integer grupoDetalheId = null;
		String grupoDetalheDesc = null;

		switch(grupoDetalhe){
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA:
				clusulaGrupoDetalhe = " AND rle.LAST_ID = 3 AND rle.LEST_ID NOT IN (3,4)  "; // DESO
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA;
				grupoDetalheDesc = "AGUA";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_ESGOTO:
				clusulaGrupoDetalhe = " AND rle.LAST_ID NOT IN (3,5) AND rle.LEST_ID IN (3,4) "; // DESO
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_ESGOTO;
				grupoDetalheDesc = "ESGOTO";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA_ESGOTO:
				clusulaGrupoDetalhe = " AND rle.LAST_ID = 3 AND rle.LEST_ID IN (3,4) "; // DESO
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA_ESGOTO;
				grupoDetalheDesc = "AGUA-ESG";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA:
				clusulaGrupoDetalhe = " AND ((rle.LAST_ID IN (4,5,6,7) AND rle.LEST_ID IN (1,2,6,9)) OR (rle.LAST_ID = 5 AND rle.LEST_ID IN (3,4))) "; // DESO
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA;
				grupoDetalheDesc = "AGUA";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_ESGOTO:
				clusulaGrupoDetalhe = " AND rle.LAST_ID NOT IN (3,4,5) AND rle.LEST_ID = 5 "; // DESO
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_ESGOTO;
				grupoDetalheDesc = "ESGOTO";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA_ESGOTO:
				clusulaGrupoDetalhe = " AND rle.LAST_ID IN (4,5) AND rle.LEST_ID = 5 "; // DESO
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA_ESGOTO;
				grupoDetalheDesc = "AGUA-ESG";
				break;
		}

		dadosRetorno[0] = clusulaGrupoDetalhe;
		dadosRetorno[1] = grupoDetalheId;
		dadosRetorno[2] = grupoDetalheDesc;

		return dadosRetorno;

	}

	/**
	 * [UC0269] Consultar Resumo das Ligações / Economias
	 * Retorna o metodo obterSubConsultaDetalheResumoLigacaoEconomia de acordo com a Empresa CASAL.
	 * 
	 * @author Josenilldo Neves
	 * @created 30/01/2013
	 */
	public Object[] execParamObterSubConsultaDetalheResumoLigacaoEconomiaModelo2(ParametroSistema parametroSistema, Integer grupoDetalhe)
					throws ControladorException{

		Object[] dadosRetorno = new Object[3];

		String clusulaGrupoDetalhe = null;
		Integer grupoDetalheId = null;
		String grupoDetalheDesc = null;

		switch(grupoDetalhe){
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA:
				clusulaGrupoDetalhe = " AND rle.LAST_ID = 3 AND rle.LEST_ID NOT IN (3,4)  "; // CASAL
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA;
				grupoDetalheDesc = "AGUA";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_ESGOTO:
				clusulaGrupoDetalhe = " AND rle.LAST_ID NOT IN (3,5) AND rle.LEST_ID IN (3,4) "; // CASAL
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_ESGOTO;
				grupoDetalheDesc = "ESGOTO";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA_ESGOTO:
				clusulaGrupoDetalhe = " AND rle.LAST_ID = 3 AND rle.LEST_ID IN (3,4) "; // CASAL
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA_ESGOTO;
				grupoDetalheDesc = "AGUA-ESG";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA:
				clusulaGrupoDetalhe = " AND (rle.LAST_ID = 5 AND rle.LEST_ID IN (1,2,6,7)) "; // CASAL
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA;
				grupoDetalheDesc = "AGUA";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_ESGOTO:
				clusulaGrupoDetalhe = " AND rle.LAST_ID NOT IN (3) AND rle.LEST_ID = 5 "; // CASAL
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_ESGOTO;
				grupoDetalheDesc = "ESGOTO";
				break;
			case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA_ESGOTO:
				clusulaGrupoDetalhe = " AND rle.LAST_ID = 5 AND rle.LEST_ID IN (3,4,5) "; // CASAL
				grupoDetalheId = ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA_ESGOTO;
				grupoDetalheDesc = "AGUA-ESG";
				break;
		}

		dadosRetorno[0] = clusulaGrupoDetalhe;
		dadosRetorno[1] = grupoDetalheId;
		dadosRetorno[2] = grupoDetalheDesc;

		return dadosRetorno;

	}

	/**
	 * [UC0269] Consultar Resumo das Ligações / Economias
	 * Retorna o metodo obterFiltroListaLigacaoAguaSituacao de acordo com a Empresa CASAL.
	 * 
	 * @author Josenilldo Neves
	 * @created 30/01/2013
	 */
	public FiltroLigacaoAguaSituacao execParamObterFiltroListaLigacaoAguaSituacaoModelo1(ParametroSistema parametroSistema)
					throws ControladorException{

		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.ID);
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.POTENCIAL,
						ConectorOr.CONECTOR_OR));
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.FACTIVEL));

		return filtroLigacaoAguaSituacao;

	}

	/**
	 * [UC0269] Consultar Resumo das Ligações / Economias
	 * Retorna o metodo obterFiltroListaLigacaoAguaSituacao de acordo com a Empresa CASAL.
	 * 
	 * @author Josenilldo Neves
	 * @created 30/01/2013
	 */
	public FiltroLigacaoAguaSituacao execParamObterFiltroListaLigacaoAguaSituacaoModelo2(ParametroSistema parametroSistema)
					throws ControladorException{

		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.ID);
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.POTENCIAL,
						ConectorOr.CONECTOR_OR));
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.FACTIVEL,
						ConectorOr.CONECTOR_OR));
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.SUPRIMIDO,
						ConectorOr.CONECTOR_OR));
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.SUPR_PARC,
						ConectorOr.CONECTOR_OR));
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
						LigacaoAguaSituacao.SUPRIMIDO_DEFINITIVO));

		return filtroLigacaoAguaSituacao;

	}
}
