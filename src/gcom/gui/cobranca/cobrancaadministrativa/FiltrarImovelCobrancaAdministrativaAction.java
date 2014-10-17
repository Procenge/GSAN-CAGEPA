
package gcom.gui.cobranca.cobrancaadministrativa;

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.bean.FiltroImovelCobrancaAdministrativaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3070] Filtrar Imóvel Cobrança Administrativa
 * 
 * @author Anderson Italo
 * @date 07/09/2012
 */
public class FiltrarImovelCobrancaAdministrativaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterImovelCobrancaAdministrativa");
		FiltrarImovelCobrancaAdministrativaActionForm form = (FiltrarImovelCobrancaAdministrativaActionForm) actionForm;
		FiltroImovelCobrancaAdministrativaHelper filtro = new FiltroImovelCobrancaAdministrativaHelper();
		Fachada fachada = Fachada.getInstancia();
		boolean parametroInformado = false;

		// Comando de Cobrança
		if(!Util.isVazioOuBranco(form.getIdComando())){

			CobrancaAcaoAtividadeComando comando = (CobrancaAcaoAtividadeComando) fachada.pesquisar(Util.obterInteger(form.getIdComando()),
							CobrancaAcaoAtividadeComando.class);

			if(comando != null){

				// [FS0001 – Verificar seleção do comando]
				if(!comando.getCobrancaAcao().getId().equals(CobrancaAcao.COBRANCA_ADMINISTRATIVA)){

					ActionServletException ex = new ActionServletException("atencao.comando_nao_cobranca_administrativa");
					ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
					throw ex;
				}

				if(comando.getRealizacao() == null){

					ActionServletException ex = new ActionServletException("atencao.comando_nao_realizado");
					ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
					throw ex;
				}
			}

			filtro.setIdComando(Util.obterInteger(form.getIdComando()));
			parametroInformado = true;
		}

		// Empresa de Cobrança
		if(!Util.isVazioOrNulo(form.getIdsEmpresaSelecionadas())
						&& form.getIdsEmpresaSelecionadas()[0].intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
						&& form.getIdsEmpresaSelecionadas()[0].intValue() != ConstantesSistema.ZERO.intValue()){

			filtro.setIdsEmpresa(form.getIdsEmpresaSelecionadas());
			parametroInformado = true;
		}

		// Imóvel
		if(!Util.isVazioOuBranco(form.getIdImovel())){

			filtro.setIdImovel(Util.obterInteger(form.getIdImovel()));
			parametroInformado = true;
		}

		// Cliente
		if(!Util.isVazioOuBranco(form.getIdCliente())){

			filtro.setIdCliente(Util.obterInteger(form.getIdCliente()));
			parametroInformado = true;
		}

		// Gerência Regional
		if(!Util.isVazioOrNulo(form.getIdsGerenciaRegionalSelecionadas())
						&& form.getIdsGerenciaRegionalSelecionadas()[0].intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
						&& form.getIdsGerenciaRegionalSelecionadas()[0].intValue() != ConstantesSistema.ZERO.intValue()){

			filtro.setIdsGerenciaRegional(form.getIdsGerenciaRegionalSelecionadas());
			parametroInformado = true;
		}

		// Unidade Negócio
		if(!Util.isVazioOrNulo(form.getIdsUnidadeNegocioSelecionadas())
						&& form.getIdsUnidadeNegocioSelecionadas()[0].intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
						&& form.getIdsUnidadeNegocioSelecionadas()[0].intValue() != ConstantesSistema.ZERO.intValue()){

			filtro.setIdsUnidadeNegocio(form.getIdsUnidadeNegocioSelecionadas());
			parametroInformado = true;
		}

		// Dados de Inscrição
		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial())){

			// Localidade
			filtro.setIdLocalidadeInicial(Util.obterInteger(form.getIdLocalidadeInicial()));
			parametroInformado = true;

			if(!Util.isVazioOuBranco(form.getIdLocalidadeFinal())){

				filtro.setIdLocalidadeFinal(Util.obterInteger(form.getIdLocalidadeFinal()));
			}else{

				filtro.setIdLocalidadeFinal(Util.obterInteger(form.getIdLocalidadeInicial()));
			}

			if(!Util.isVazioOuBranco(form.getCodigoSetorComercialInicial())){

				// Setor Comercial
				filtro.setCodigoSetorComercialInicial(Util.obterShort(form.getCodigoSetorComercialInicial()));

				if(!Util.isVazioOuBranco(form.getCodigoSetorComercialFinal())){

					filtro.setCodigoSetorComercialFinal(Util.obterShort(form.getCodigoSetorComercialFinal()));
				}else{

					filtro.setCodigoSetorComercialFinal(Util.obterShort(form.getCodigoSetorComercialInicial()));
				}

				if(!Util.isVazioOuBranco(form.getNumeroQuadraInicial())){

					// Quadra
					filtro.setNumeroQuadraInicial(Util.obterInteger(form.getNumeroQuadraInicial()));

					if(!Util.isVazioOuBranco(form.getNumeroQuadraFinal())){

						filtro.setNumeroQuadraFinal(Util.obterInteger(form.getNumeroQuadraFinal()));
					}else{

						filtro.setNumeroQuadraFinal(Util.obterInteger(form.getNumeroQuadraInicial()));
					}
				}
			}
		}

		// Arquivo de Imóveis
		if(!Util.isVazioOrNulo(form.getIdsImoveis())){

			filtro.setIdsImoveis(form.getIdsImoveis());
			parametroInformado = true;
		}

		// Categoria
		if(!Util.isVazioOrNulo(form.getIdsCategoriaSelecionadas())
						&& form.getIdsCategoriaSelecionadas()[0].intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
						&& form.getIdsCategoriaSelecionadas()[0].intValue() != ConstantesSistema.ZERO.intValue()){

			filtro.setIdsCategoria(form.getIdsCategoriaSelecionadas());
			parametroInformado = true;
		}

		// Subcategoria
		if(!Util.isVazioOrNulo(form.getIdsSubcategoriaSelecionadas())
						&& form.getIdsSubcategoriaSelecionadas()[0].intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
						&& form.getIdsSubcategoriaSelecionadas()[0].intValue() != ConstantesSistema.ZERO.intValue()){

			filtro.setIdsSubcategoria(form.getIdsSubcategoriaSelecionadas());
			parametroInformado = true;
		}

		// Situação da Ligação de Água
		if(!Util.isVazioOrNulo(form.getIdsLigacaoAguaSituacaoSelecionadas())
						&& form.getIdsLigacaoAguaSituacaoSelecionadas()[0].intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
						&& form.getIdsLigacaoAguaSituacaoSelecionadas()[0].intValue() != ConstantesSistema.ZERO.intValue()){

			filtro.setIdsLigacaoAguaSituacao(form.getIdsLigacaoAguaSituacaoSelecionadas());
			parametroInformado = true;
		}

		// Situação da Ligação de Esgoto
		if(!Util.isVazioOrNulo(form.getIdsLigacaoEsgotoSituacaoSelecionadas())
						&& form.getIdsLigacaoEsgotoSituacaoSelecionadas()[0].intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
						&& form.getIdsLigacaoEsgotoSituacaoSelecionadas()[0].intValue() != ConstantesSistema.ZERO.intValue()){

			filtro.setIdsLigacaoEsgotoSituacao(form.getIdsLigacaoEsgotoSituacaoSelecionadas());
			parametroInformado = true;
		}

		// Valor do Débito
		if(!Util.isVazioOuBranco(form.getValorDebitoInicial())){

			filtro.setValorDebitoInicial(Util.formatarMoedaRealparaBigDecimal(form.getValorDebitoInicial()));
			parametroInformado = true;

			if(!Util.isVazioOuBranco(form.getValorDebitoFinal())){

				filtro.setValorDebitoFinal(Util.formatarMoedaRealparaBigDecimal(form.getValorDebitoFinal()));
			}else{

				filtro.setValorDebitoFinal(Util.formatarMoedaRealparaBigDecimal(form.getValorDebitoInicial()));
			}
		}

		// Período de Inclusão
		if(!Util.isVazioOuBranco(form.getPeriodoInclusaoInicial())){

			filtro.setPeriodoInclusaoInicial(Util.converteStringParaDate(form.getPeriodoInclusaoInicial()));
			parametroInformado = true;

			if(!Util.isVazioOuBranco(form.getPeriodoInclusaoFinal())){

				filtro.setPeriodoInclusaoFinal(Util.converteStringParaDate(form.getPeriodoInclusaoFinal()));
			}else{
				filtro.setPeriodoInclusaoFinal(Util.converteStringParaDate(form.getPeriodoInclusaoInicial()));
			}
		}

		// Período de Retirada
		if(!Util.isVazioOuBranco(form.getPeriodoRetiradaInicial())){

			filtro.setPeriodoRetiradaInicial(Util.converteStringParaDate(form.getPeriodoRetiradaInicial()));
			parametroInformado = true;

			if(!Util.isVazioOuBranco(form.getPeriodoRetiradaFinal())){

				filtro.setPeriodoRetiradaFinal(Util.converteStringParaDate(form.getPeriodoRetiradaFinal()));
			}else{
				filtro.setPeriodoRetiradaFinal(Util.converteStringParaDate(form.getPeriodoRetiradaInicial()));
			}
		}

		// Situação da Cobrança Administrativa
		if(!Util.isVazioOuBranco(form.getIndicadorSituacaoCobrancaAdministrativa())
						&& !form.getIndicadorSituacaoCobrancaAdministrativa().equals(ConstantesSistema.TODOS.toString())){

			filtro.setIndicadorSituacaoCobrancaAdministrativa(Util.obterShort(form.getIndicadorSituacaoCobrancaAdministrativa()));
			parametroInformado = true;
		}

		// Motivo da Retirada
		if(!Util.isVazioOrNulo(form.getIdsMotivoRetiradaSelecionados())
						&& form.getIdsMotivoRetiradaSelecionados()[0].intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
						&& form.getIdsMotivoRetiradaSelecionados()[0].intValue() != ConstantesSistema.ZERO.intValue()){

			filtro.setIdsMotivoRetirada(form.getIdsMotivoRetiradaSelecionados());
			parametroInformado = true;
		}

		// [FS0014] – Verificar preenchimento dos campos
		if(!parametroInformado){

			ActionServletException ex = new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
			throw ex;
		}

		this.getSessao(httpServletRequest).setAttribute("filtroImovelCobrancaAdministrativaHelper", filtro);

		return retorno;
	}
}
