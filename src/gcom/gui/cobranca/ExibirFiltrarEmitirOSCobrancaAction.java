
package gcom.gui.cobranca;

import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeCronograma;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltrarEmitirOSCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		String retorno = "sucesso";
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = request.getSession();

		FiltrarEmitirOSCobrancaActionForm filtrarForm = (FiltrarEmitirOSCobrancaActionForm) form;

		if(sessao.getAttribute("cobrancaGrupos") == null){
			FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
			filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);
			Collection<CobrancaGrupo> cobrancaGrupos = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
			sessao.setAttribute("cobrancaGrupos", cobrancaGrupos);
		}

		if(sessao.getAttribute("cobrancaAcoes") == null){
			FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
			filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
			Collection<CobrancaAcao> cobrancaAcoes = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());
			sessao.setAttribute("cobrancaAcoes", cobrancaAcoes);
		}

		// Recupera o Tipo do Filtro
		String tipoFiltro = filtrarForm.getTipoFiltro();
		if(tipoFiltro != null && !tipoFiltro.equals("")){

			if(tipoFiltro.equals("cronograma")){

				// Id do Comando Cronograma
				String idComandoCronograma = (String) filtrarForm.getIdComandoCronograma();

				// Verifica se o código foi digitado
				if(idComandoCronograma != null && !idComandoCronograma.trim().equals("") && Integer.parseInt(idComandoCronograma) > 0){

					// Manda para a página a informação do campo para que seja focado no retorno da
					// pesquisa
					request.setAttribute("nomeCampo", "idComandoCronograma");

					FiltroCobrancaAcaoAtividadeCronograma filtroCobrancaAcaoAtividadeCronograma = new FiltroCobrancaAcaoAtividadeCronograma();
					filtroCobrancaAcaoAtividadeCronograma.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeCronograma.ID,
									Integer.valueOf(idComandoCronograma)));
					filtroCobrancaAcaoAtividadeCronograma
									.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeCronograma.COBRANCA_GRUPO_CRONOGRAMA_MES_COBRANCA_GRUPO);

					Collection<CobrancaAcaoAtividadeCronograma> colecaoCobrancaAcaoAtividadeCronograma = fachada.pesquisar(
									filtroCobrancaAcaoAtividadeCronograma, CobrancaAcaoAtividadeCronograma.class.getName());

					if(colecaoCobrancaAcaoAtividadeCronograma != null && !colecaoCobrancaAcaoAtividadeCronograma.isEmpty()){

						// O comando foi encontrado
						CobrancaAcaoAtividadeCronograma comandoCronograma = (CobrancaAcaoAtividadeCronograma) Util
										.retonarObjetoDeColecao(colecaoCobrancaAcaoAtividadeCronograma);

						String descricaoGrupo = "Comando Existente";
						if(comandoCronograma.getCobrancaAcaoCronograma() != null
										&& comandoCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes() != null
										&& comandoCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes().getCobrancaGrupo() != null){
							descricaoGrupo = comandoCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes()
											.getCobrancaGrupo().getDescricao();
						}

						filtrarForm.setIdComandoCronograma("" + comandoCronograma.getId());
						filtrarForm.setNomeComandoCronograma(descricaoGrupo);
						request.setAttribute("idComandoCronograma", "true");
						request.setAttribute("nomeCampo", "idComandoCronograma");

					}else{

						// O comando não foi encontrado
						filtrarForm.setIdComandoCronograma("");
						filtrarForm.setNomeComandoCronograma("Comando Inexistente");
						request.setAttribute("corNomeComandoCronograma", "exception");
					}
				}

			}else if(tipoFiltro.equals("eventual")){

				// Id do Localidade Eventual
				String idLocalidadeEventual = (String) filtrarForm.getIdLocalidadeEventual();
				boolean localidadeEncontrada = false;

				// Verifica se o código foi digitado
				if(idLocalidadeEventual != null && !idLocalidadeEventual.trim().equals("") && Integer.parseInt(idLocalidadeEventual) > 0){

					// Manda para a página a informação do campo para que seja focado no retorno da
					// pesquisa
					request.setAttribute("nomeCampo", "idLocalidadeEventual");

					Localidade localidade = fachada.pesquisarLocalidadeDigitada(Integer.valueOf(idLocalidadeEventual));

					if(localidade != null){

						// Localidade foi encontrada
						filtrarForm.setIdLocalidadeEventual("" + localidade.getId());
						filtrarForm.setNomeLocalidadeEventual(localidade.getDescricao());
						request.setAttribute("idLocalidadeEventual", "true");
						localidadeEncontrada = true;

					}else{

						// Localidade não foi encontrada
						filtrarForm.setIdComandoEventual("");
						filtrarForm.setNomeLocalidadeEventual("Localidade Inexistente");
						request.setAttribute("corLocalidadeEventual", "exception");
					}
				}

				if(localidadeEncontrada){

					// Id do Setor Comercial
					String idSetorComercialEventual = (String) filtrarForm.getIdSetorComercialEventual();

					// Verifica se o código foi digitado
					if(idSetorComercialEventual != null && !idSetorComercialEventual.trim().equals("")
									&& Integer.parseInt(idSetorComercialEventual) > 0){

						// Manda para a página a informação do campo para que seja focado no retorno
						// da pesquisa
						request.setAttribute("nomeCampo", "idSetorComercialEventual");

						FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeCronograma.ID, Integer
										.valueOf(idSetorComercialEventual)));

						Collection<SetorComercial> colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class
										.getName());

						if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

							// O Setor Comercial foi encontrado
							SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

							filtrarForm.setIdSetorComercialEventual("" + setorComercial.getId());
							filtrarForm.setNomeSetorComercialEventual(setorComercial.getDescricao());
							request.setAttribute("idSetorComercialEventual", "true");
							request.setAttribute("nomeCampo", "idSetorComercialEventual");

						}else{

							// O Setor Comercial não foi encontrado
							filtrarForm.setIdSetorComercialEventual("");
							filtrarForm.setNomeSetorComercialEventual("Setor Comercial Inexistente");
							request.setAttribute("corNomeSetorComercial", "exception");
						}
					}

				}else{
					// Limpar Setor Comercial
					filtrarForm.setIdSetorComercialEventual("");
					filtrarForm.setNomeSetorComercialEventual("");
				}

			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Tipo de Ação de Cobrança");
			}
		}

		return mapping.findForward(retorno);
	}
}
