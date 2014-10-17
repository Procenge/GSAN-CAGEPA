
package gcom.gui.relatorio.cobranca.administrativa;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeComando;
import gcom.cobranca.bean.FiltroImovelCobrancaAdministrativaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.cobrancaadministrativa.RelatorioImoveisEmCobrancaAdministrativa;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3060] Manter Im�vel Cobran�a Administrativa
 * 
 * @author Carlos Chrystian Ramos
 * @date 25/02/2013
 */
public class GerarRelatorioImoveisEmCobrancaAdministrativaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = null;

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Recupera a cole��o selecionada que ser� enviada para o relat�rio
		Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = null;

		FiltroImovelCobrancaAdministrativaHelper filtroImovelCobrancaAdministrativaHelper = null;

		if(sessao.getAttribute("filtroImovelCobrancaAdministrativaHelper") != null){

			filtroImovelCobrancaAdministrativaHelper = (FiltroImovelCobrancaAdministrativaHelper) sessao
							.getAttribute("filtroImovelCobrancaAdministrativaHelper");
		}

		colecaoImovelCobrancaSituacao = fachada.pesquisarImovelCobrancaAdministrativa(filtroImovelCobrancaAdministrativaHelper,
						ConstantesSistema.NUMERO_NAO_INFORMADO);

		// Gera inst�ncia do relat�rio de im�veis em cobran�a administrativa
		RelatorioImoveisEmCobrancaAdministrativa relatorio = new RelatorioImoveisEmCobrancaAdministrativa(usuario);

		// Insere a cole��o como par�metro do relat�rio
		relatorio.addParametro("colecaoImovelCobrancaSituacao", colecaoImovelCobrancaSituacao);

		// Insere o tipo de relat�rio como par�metro do relat�rio
		int tipoRelatorio = TarefaRelatorio.TIPO_PDF;
		relatorio.addParametro("tipoFormatoRelatorio", tipoRelatorio);

		// Par�metro do Filtro "Comando"
		Integer idComando = filtroImovelCobrancaAdministrativaHelper.getIdComando();
		String descricaoComando = "";
		if(!Util.isVazioOuBranco(idComando)){
			FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando = new FiltroCobrancaAcaoAtividadeComando();
			filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeComando.ID, idComando
							.toString()));

			CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = (CobrancaAcaoAtividadeComando) Util.retonarObjetoDeColecao(fachada
							.pesquisar(filtroCobrancaAcaoAtividadeComando, CobrancaAcaoAtividadeComando.class.getName()));

			descricaoComando = cobrancaAcaoAtividadeComando.getId() + " - " + cobrancaAcaoAtividadeComando.getDescricaoTitulo();
		}
		relatorio.addParametro("comando", descricaoComando);

		// Par�metro do Filtro "Empresa"
		int contEmpresa = 0;
		String descricaoEmpresas = "";
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsEmpresa())){
			for(Object idEmpresa : filtroImovelCobrancaAdministrativaHelper.getIdsEmpresa()){
				FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
				filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, idEmpresa.toString()));

				Empresa empresa = (Empresa) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroEmpresa, Empresa.class.getName()));
				contEmpresa += 1;
				if(contEmpresa == filtroImovelCobrancaAdministrativaHelper.getIdsEmpresa().length){
					descricaoEmpresas = descricaoEmpresas + empresa.getDescricaoAbreviada();
				}else{
					descricaoEmpresas = descricaoEmpresas + empresa.getDescricaoAbreviada() + ", ";
				}
			}
		}
		relatorio.addParametro("empresa", descricaoEmpresas);

		// Par�metro do Filtro "Im�vel"
		Integer idImovel = filtroImovelCobrancaAdministrativaHelper.getIdImovel();
		relatorio.addParametro("imovel", idImovel == null ? "" : idImovel.toString());

		// Par�metro do Filtro "Cliente"
		Integer idCliente = filtroImovelCobrancaAdministrativaHelper.getIdCliente();
		String descricaoCliente = "";
		if(!Util.isVazioOuBranco(idCliente)){
			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente.toString()));

			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCliente, Cliente.class.getName()));

			descricaoCliente = cliente.getId() + " - " + cliente.getDescricao();
		}
		relatorio.addParametro("cliente", descricaoCliente);

		// Par�metro do Filtro "Localidade Final"
		Integer idLocalidadeFinal = filtroImovelCobrancaAdministrativaHelper.getIdLocalidadeFinal();
		String descricaoLocalidadeFinal = "";
		if(!Util.isVazioOuBranco(idLocalidadeFinal)){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeFinal.toString()));

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(fachada
							.pesquisar(filtroLocalidade, Localidade.class.getName()));

			descricaoLocalidadeFinal = localidade.getId() + " - " + localidade.getDescricao();
		}
		relatorio.addParametro("localidadeFinal", descricaoLocalidadeFinal);

		// Par�metro do Filtro "Localidade Inicial"
		Integer idLocalidadeInicial = filtroImovelCobrancaAdministrativaHelper.getIdLocalidadeInicial();
		String descricaoLocalidadeInicial = "";
		if(!Util.isVazioOuBranco(idLocalidadeInicial)){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeInicial.toString()));

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(fachada
							.pesquisar(filtroLocalidade, Localidade.class.getName()));

			descricaoLocalidadeInicial = localidade.getId() + " - " + localidade.getDescricao();
		}
		relatorio.addParametro("localidadeInicial", descricaoLocalidadeInicial);

		// Par�metro do Filtro "Setor Comercial Final"
		Short codigoSetorComercialFinal = filtroImovelCobrancaAdministrativaHelper.getCodigoSetorComercialFinal();
		String descricaoSetorComercialFinal = "";
		if(!Util.isVazioOuBranco(codigoSetorComercialFinal)){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, codigoSetorComercialFinal.toString()));

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSetorComercial,
							SetorComercial.class.getName()));

			descricaoSetorComercialFinal = setorComercial.getId() + " - " + setorComercial.getDescricao();
		}
		relatorio.addParametro("setorComercialFinal", descricaoSetorComercialFinal);

		// Par�metro do Filtro "Setor Comercial Inicial"
		Short codigoSetorComercialInicial = filtroImovelCobrancaAdministrativaHelper.getCodigoSetorComercialInicial();
		String descricaoSetorComercialInicial = "";
		if(!Util.isVazioOuBranco(codigoSetorComercialInicial)){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, codigoSetorComercialInicial.toString()));

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSetorComercial,
							SetorComercial.class.getName()));

			descricaoSetorComercialInicial = setorComercial.getId() + " - " + setorComercial.getDescricao();
		}
		relatorio.addParametro("setorComercialInicial", descricaoSetorComercialInicial);

		// Par�metro do Filtro "Quadra Final"
		Integer numeroQuadraFinal = filtroImovelCobrancaAdministrativaHelper.getNumeroQuadraFinal();
		relatorio.addParametro("quadraFinal", numeroQuadraFinal == null ? "" : numeroQuadraFinal.toString());

		// Par�metro do Filtro "Quadra Inicial"
		Integer numeroQuadraInicial = filtroImovelCobrancaAdministrativaHelper.getNumeroQuadraInicial();
		relatorio.addParametro("quadraInicial", numeroQuadraInicial == null ? "" : numeroQuadraInicial.toString());

		// Par�metro do Filtro "Per�odo Inclus�o Final"
		Date periodoInclusaoFinal = filtroImovelCobrancaAdministrativaHelper.getPeriodoInclusaoFinal();
		relatorio.addParametro("periodoInclusaoFinal", periodoInclusaoFinal == null ? "" : Util.formatarData(periodoInclusaoFinal));

		// Par�metro do Filtro "Per�odo Inclus�o Inicial"
		Date periodoInclusaoInicial = filtroImovelCobrancaAdministrativaHelper.getPeriodoInclusaoInicial();
		relatorio.addParametro("periodoInclusaoInicial", periodoInclusaoInicial == null ? "" : Util.formatarData(periodoInclusaoInicial));

		// Par�metro do Filtro "Per�odo Retirada Final"
		Date periodoRetiradaFinal = filtroImovelCobrancaAdministrativaHelper.getPeriodoRetiradaFinal();
		relatorio.addParametro("periodoRetiradaFinal", periodoRetiradaFinal == null ? "" : Util.formatarData(periodoRetiradaFinal));

		// Par�metro do Filtro "Per�odo Retirada Inicial"
		Date periodoRetiradaInicial = filtroImovelCobrancaAdministrativaHelper.getPeriodoRetiradaInicial();
		relatorio.addParametro("periodoRetiradaInicial", periodoRetiradaInicial == null ? "" : Util.formatarData(periodoRetiradaInicial));

		// Par�metro do Filtro "Valor D�bito Final"
		BigDecimal valorDebitoFinal = filtroImovelCobrancaAdministrativaHelper.getValorDebitoFinal();
		relatorio.addParametro("valorDebitoFinal", valorDebitoFinal == null ? "" : Util.formatarMoedaReal(valorDebitoFinal));

		// Par�metro do Filtro "Valor D�bito Inicial"
		BigDecimal valorDebitoInicial = filtroImovelCobrancaAdministrativaHelper.getValorDebitoInicial();
		relatorio.addParametro("valorDebitoInicial", valorDebitoInicial == null ? "" : Util.formatarMoedaReal(valorDebitoInicial));

		// Par�metro do Filtro "Indicador Situa��o Cobran�a Administrativa"
		Short indicadorSituacaoCobrancaAdministrativa = filtroImovelCobrancaAdministrativaHelper
						.getIndicadorSituacaoCobrancaAdministrativa();
		String descricaoIndicadorSituacaoCobrancaAdministrativa = "";
		if(!Util.isVazioOuBranco(indicadorSituacaoCobrancaAdministrativa)){
			switch(indicadorSituacaoCobrancaAdministrativa){
				case 1:
					descricaoIndicadorSituacaoCobrancaAdministrativa = "Pendente";
					break;
				case 2:
					descricaoIndicadorSituacaoCobrancaAdministrativa = "Encerrado";
					break;
				case 3:
					descricaoIndicadorSituacaoCobrancaAdministrativa = "Todas";
					break;
				default:
					break;
			}
		}else{
			descricaoIndicadorSituacaoCobrancaAdministrativa = "Todas";
		}
		relatorio.addParametro("situacaoCobAdministrativa", descricaoIndicadorSituacaoCobrancaAdministrativa);

		// Par�metro do Filtro "Ger�ncia Regional"
		String descricaoGerenciasRegionais = "";
		int contRegional = 0;
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsGerenciaRegional())){
			for(Object idGerenciaRegional : filtroImovelCobrancaAdministrativaHelper.getIdsGerenciaRegional()){
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, idGerenciaRegional.toString()));

				GerenciaRegional gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(fachada.pesquisar(
								filtroGerenciaRegional, GerenciaRegional.class.getName()));

				contRegional += 1;
				if(contRegional == filtroImovelCobrancaAdministrativaHelper.getIdsGerenciaRegional().length){
					descricaoGerenciasRegionais = descricaoGerenciasRegionais + gerenciaRegional.getNomeAbreviado();
				}else{
					descricaoGerenciasRegionais = descricaoGerenciasRegionais + gerenciaRegional.getNomeAbreviado() + ", ";
				}
			}
		}
		relatorio.addParametro("gerenciaReginal", descricaoGerenciasRegionais);

		// Par�metro do Filtro "Unidade de Neg�cio"
		String descricaoUnidadesDeNegocio = "";
		int contUnidadeNegocio = 0;
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsUnidadeNegocio())){
			for(Object idUnidadeNegocio : filtroImovelCobrancaAdministrativaHelper.getIdsUnidadeNegocio()){
				FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, idUnidadeNegocio.toString()));

				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUnidadeNegocio,
								UnidadeNegocio.class.getName()));

				contUnidadeNegocio += 1;
				if(contUnidadeNegocio == filtroImovelCobrancaAdministrativaHelper.getIdsUnidadeNegocio().length){
					descricaoUnidadesDeNegocio = descricaoUnidadesDeNegocio + unidadeNegocio.getNomeAbreviado();
				}else{
					descricaoUnidadesDeNegocio = descricaoUnidadesDeNegocio + unidadeNegocio.getNomeAbreviado() + ", ";
				}
			}
		}
		relatorio.addParametro("unidadeDeNegocio", descricaoUnidadesDeNegocio);

		// Par�metro do Filtro "Categoria"
		String descricaoCategoria = "";
		int contCategoria = 0;
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsCategoria())){
			for(Object idCategoria : filtroImovelCobrancaAdministrativaHelper.getIdsCategoria()){
				FiltroCategoria filtroCategoria = new FiltroCategoria();
				filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria.toString()));

				Categoria categoria = (Categoria) Util
								.retonarObjetoDeColecao(fachada.pesquisar(filtroCategoria, Categoria.class.getName()));

				contCategoria += 1;
				if(contCategoria == filtroImovelCobrancaAdministrativaHelper.getIdsCategoria().length){
					descricaoCategoria = descricaoCategoria + categoria.getDescricao();
				}else{
					descricaoCategoria = descricaoCategoria + categoria.getDescricao() + ", ";
				}
			}
		}
		relatorio.addParametro("categoria", descricaoCategoria);

		// Par�metro do Filtro "Subcategoria"
		String descricaoSubcategorias = "";
		int contSubcategoria = 0;
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsSubcategoria())){
			for(Object idSubcategoria : filtroImovelCobrancaAdministrativaHelper.getIdsSubcategoria()){
				FiltroSubCategoria filtroSubcategoria = new FiltroSubCategoria();
				filtroSubcategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idSubcategoria.toString()));

				Subcategoria subCategoria = (Subcategoria) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSubcategoria,
								Subcategoria.class.getName()));

				contSubcategoria += 1;
				if(contSubcategoria == filtroImovelCobrancaAdministrativaHelper.getIdsSubcategoria().length){
					descricaoSubcategorias = descricaoSubcategorias + subCategoria.getDescricao();
				}else{
					descricaoSubcategorias = descricaoSubcategorias + subCategoria.getDescricao() + ", ";
				}
			}
		}
		relatorio.addParametro("subcategoria", descricaoSubcategorias);

		// Par�metro do Filtro "Liga��o Situa��o �gua"
		String descricaoLigacoesAguaSituacao = "";
		int contLigacoesAguaSituacao = 0;
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsLigacaoAguaSituacao())){
			for(Object idLigacaoAguaSituacao : filtroImovelCobrancaAdministrativaHelper.getIdsLigacaoAguaSituacao()){
				FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
				filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, idLigacaoAguaSituacao
								.toString()));

				LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
								filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName()));

				contLigacoesAguaSituacao += 1;
				if(contLigacoesAguaSituacao == filtroImovelCobrancaAdministrativaHelper.getIdsLigacaoAguaSituacao().length){
					descricaoLigacoesAguaSituacao = descricaoLigacoesAguaSituacao + ligacaoAguaSituacao.getDescricao();
				}else{
					descricaoLigacoesAguaSituacao = descricaoLigacoesAguaSituacao + ligacaoAguaSituacao.getDescricao() + ", ";
				}
			}
		}
		relatorio.addParametro("situacaoLigacaoAgua", descricaoLigacoesAguaSituacao);

		// Par�metro do Filtro "Liga��o Situa��o Esgoto"
		String descricaoLigacoesEsgotoSituacao = "";
		int contLigacoesEsgotoSituacao = 0;
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsLigacaoEsgotoSituacao())){
			for(Object idLigacaoEsgotoSituacao : filtroImovelCobrancaAdministrativaHelper.getIdsLigacaoEsgotoSituacao()){
				FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
				filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID, idLigacaoEsgotoSituacao
								.toString()));

				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
								filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName()));

				contLigacoesEsgotoSituacao += 1;
				if(contLigacoesEsgotoSituacao == filtroImovelCobrancaAdministrativaHelper.getIdsLigacaoEsgotoSituacao().length){
					descricaoLigacoesEsgotoSituacao = descricaoLigacoesEsgotoSituacao + ligacaoEsgotoSituacao.getDescricao();
				}else{
					descricaoLigacoesEsgotoSituacao = descricaoLigacoesEsgotoSituacao + ligacaoEsgotoSituacao.getDescricao() + ", ";
				}
			}
		}
		relatorio.addParametro("situacaoLigacaoEsgoto", descricaoLigacoesEsgotoSituacao);

		// Par�metro do Filtro "Motivo Retirada"
		String descricaoMotivoRetirada = "";
		int contMotivoRetirada = 0;
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsMotivoRetirada())){
			for(Object idImovelCobrancaMotivoRetirada : filtroImovelCobrancaAdministrativaHelper.getIdsMotivoRetirada()){
				FiltroImovelCobrancaMotivoRetirada filtroImovelCobrancaMotivoRetirada = new FiltroImovelCobrancaMotivoRetirada();
				filtroImovelCobrancaMotivoRetirada.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaMotivoRetirada.ID,
								idImovelCobrancaMotivoRetirada.toString()));

				ImovelCobrancaMotivoRetirada imovelCobrancaMotivoRetirada = (ImovelCobrancaMotivoRetirada) Util
								.retonarObjetoDeColecao(fachada.pesquisar(filtroImovelCobrancaMotivoRetirada,
												ImovelCobrancaMotivoRetirada.class.getName()));

				contMotivoRetirada += 1;
				if(contMotivoRetirada == filtroImovelCobrancaAdministrativaHelper.getIdsMotivoRetirada().length){
					descricaoMotivoRetirada = descricaoMotivoRetirada + imovelCobrancaMotivoRetirada.getDescricao();
				}else{
					descricaoMotivoRetirada = descricaoMotivoRetirada + imovelCobrancaMotivoRetirada.getDescricao() + ", ";
				}
			}
		}
		relatorio.addParametro("descricaoMotivosRetirada", descricaoMotivoRetirada);

		// Par�metro do Filtro "Arquivo Im�veis"
		String idsArquivoImoveis = "";
		int contImoveis = 0;
		if(!Util.isVazioOrNulo(filtroImovelCobrancaAdministrativaHelper.getIdsImoveis())){
			for(Object idsImovel : filtroImovelCobrancaAdministrativaHelper.getIdsImoveis()){
				contImoveis += 1;
				if(contImoveis == filtroImovelCobrancaAdministrativaHelper.getIdsImoveis().size()){
					idsArquivoImoveis = idsArquivoImoveis + idsImovel;
				}else{
					idsArquivoImoveis = idsArquivoImoveis + idsImovel + ", ";
				}
			}
		}
		relatorio.addParametro("arquivoImoveis", idsArquivoImoveis);

		try{
			String indRemunCobAdmPorComando = (String) ParametroCobranca.P_IND_REMUNERACAO_COBRANCA_ADMINISTRATIVA_POR_COMANDO.executar();
			relatorio.addParametro("indRemunCobAdmPorComando", indRemunCobAdmPorComando);
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		// Processa a exibi��o do relat�rio
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}
