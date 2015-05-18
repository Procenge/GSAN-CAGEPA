
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoTipoTramite;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pesquisar Serviço Tipo Trâmite
 * [UC0410] Inserir Serviço Tipo
 * [UC0412] Manter Tipo de Serviço
 * 
 * @author Hebert Falcão
 * @date 11/02/2012
 */
public class ExibirPesquisarServicoTipoTramiteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirServicoTipoTramitePopup");

		PesquisarServicoTipoTramiteActionForm form = (PesquisarServicoTipoTramiteActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String operacao = httpServletRequest.getParameter("operacao");
		if(operacao == null){
			operacao = (String) sessao.getAttribute("operacao");
		}
		sessao.setAttribute("operacao", operacao);

		if(operacao != null){
			String origemTelaPrincipal = httpServletRequest.getParameter("origemTelaPrincipal");
			if(operacao.equals("adicionar") && origemTelaPrincipal != null){
				form.limparForm();
			}else if(operacao.equals("editar")){
				String idServicoTipoTramite = httpServletRequest.getParameter("idServicoTipoTramite");
				if(idServicoTipoTramite == null){
					idServicoTipoTramite = (String) sessao.getAttribute("idServicoTipoTramite");
				}
				sessao.setAttribute("idServicoTipoTramite", idServicoTipoTramite);

				if(idServicoTipoTramite == null){
					throw new ActionServletException("atencao.informe_campo", null, "Serviço Tipo Trâmite");
				}

				Collection<ServicoTipoTramite> colecaoServicoTipoTramite = null;

				if(sessao.getAttribute("colecaoServicoTipoTramite") != null){
					colecaoServicoTipoTramite = (ArrayList<ServicoTipoTramite>) sessao.getAttribute("colecaoServicoTipoTramite");
				}else{
					throw new ActionServletException("atencao.campo.informado", null, "Serviço Tipo Tramite");
				}

				if(Util.isVazioOrNulo(colecaoServicoTipoTramite)){
					throw new ActionServletException("atencao.campo.informado", null, "Serviço Tipo Tramite");
				}else{
					ServicoTipoTramite servicoTipoTramiteEditar = null;
					Integer idServicoTipoTramiteAux = null;

					for(ServicoTipoTramite servicoTipoTramiteAux : colecaoServicoTipoTramite){
						idServicoTipoTramiteAux = servicoTipoTramiteAux.getId();

						if(idServicoTipoTramiteAux.toString().equals(idServicoTipoTramite)){
							servicoTipoTramiteEditar = servicoTipoTramiteAux;
							break;
						}
					}

					if(servicoTipoTramiteEditar == null){
						throw new ActionServletException("atencao.campo.invalido", null, "Serviço Tipo Tramite");
					}else{
						form.preencherForm(servicoTipoTramiteEditar);

						httpServletRequest.setAttribute("idServicoTipoTramiteEditar", idServicoTipoTramite);
					}
				}
			}
		}

		String forward = null;
		String tipoConsulta = httpServletRequest.getParameter("tipoConsulta");
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(httpServletRequest.getParameter("forward") != null){
			forward = getRealForward(httpServletRequest.getParameter("forward"));
			retorno = actionMapping.findForward(forward);
		}else if(!Util.isVazioOuBranco(tipoConsulta)){
			String idCampoEnviarDados = httpServletRequest.getParameter("idCampoEnviarDados");
			String descricaoCampoEnviarDados = httpServletRequest.getParameter("descricaoCampoEnviarDados");

			if(tipoConsulta.equals("localidade")){
				form.setIdLocalidade(idCampoEnviarDados);
				form.setDescricaoLocalidade(descricaoCampoEnviarDados);

				retorno = actionMapping.findForward("exibirServicoTipoTramitePopup");
			}else if(tipoConsulta.equals("setorComercial")){
				form.setCodigoSetorComercial(idCampoEnviarDados);
				form.setDescricaoSetorComercial(descricaoCampoEnviarDados);

				retorno = actionMapping.findForward("exibirServicoTipoTramitePopup");
			}else if(tipoConsulta.equals("unidadeAtendimento")){
				String campoUnidade = (String) sessao.getAttribute("campoUnidade");

				if("origem".equals(campoUnidade)){
					form.setIdUnidadeOrganizacionalOrigem(idCampoEnviarDados);
					form.setDescricaoUnidadeOrganizacionalOrigem(descricaoCampoEnviarDados);
				}else if("destino".equals(campoUnidade)){
					form.setIdUnidadeOrganizacionalDestino(idCampoEnviarDados);
					form.setDescricaoUnidadeOrganizacionalDestino(descricaoCampoEnviarDados);
				}

				retorno = actionMapping.findForward("exibirServicoTipoTramitePopup");
			}else if(tipoConsulta.equals("municipio")){
				form.setIdMunicipio(idCampoEnviarDados);
				form.setDescricaoMunicipio(descricaoCampoEnviarDados);
				retorno = actionMapping.findForward("exibirServicoTipoTramitePopup");
			}else if(tipoConsulta.equals("bairro")){
				form.setCodigoBairro(idCampoEnviarDados);
				form.setDescricaoBairro(descricaoCampoEnviarDados);

				retorno = actionMapping.findForward("exibirServicoTipoTramitePopup");
			}
		}else if(!Util.isVazioOuBranco(objetoConsulta)){

			String idLocalidade = form.getIdLocalidade();
			String codigoSetorComercial = form.getCodigoSetorComercial();
			String idUnidadeOrganizacionalOrigem = form.getIdUnidadeOrganizacionalOrigem();
			String idUnidadeOrganizacionalDestino = form.getIdUnidadeOrganizacionalDestino();
			String idMunicipio = form.getIdMunicipio();
			String codigoBairro = form.getCodigoBairro();
			String indicadorPrimeiroTramite = form.getIndicadorPrimeiroTramite();

			// Limpando campos de descrição quando o id/código é vazio
			if(Util.isVazioOuBranco(idLocalidade)){
				form.setDescricaoLocalidade("");
			}

			if(Util.isVazioOuBranco(codigoSetorComercial)){
				form.setDescricaoSetorComercial("");
			}

			if(Util.isVazioOuBranco(idUnidadeOrganizacionalOrigem)){
				form.setDescricaoUnidadeOrganizacionalOrigem("");
			}

			if(Util.isVazioOuBranco(idUnidadeOrganizacionalDestino)){
				form.setDescricaoUnidadeOrganizacionalDestino("");
			}

			if(Util.isVazioOuBranco(idMunicipio)){
				form.setDescricaoMunicipio("");
			}

			if(Util.isVazioOuBranco(codigoBairro)){
				form.setDescricaoBairro("");
			}

			if(Util.isVazioOuBranco(indicadorPrimeiroTramite)){
				form.setIndicadorPrimeiroTramite(ConstantesSistema.NAO.toString());
			}

			if(objetoConsulta.equals("1")){
				// Localidade
				if(!Util.isVazioOuBranco(idLocalidade)){
					FiltroLocalidade filtro = new FiltroLocalidade();
					filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

					Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());

					if(!Util.isVazioOrNulo(colecaoLocalidade)){
						Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

						String descricao = localidade.getDescricao();
						form.setDescricaoLocalidade(descricao);
					}else{
						form.setIdLocalidade("");
						form.setDescricaoLocalidade("LOCALIDADE INEXISTENTE");

						form.setCodigoSetorComercial("");
						form.setDescricaoSetorComercial("");
					}
				}
			}else if(objetoConsulta.equals("2")){
				// Setor Comercial
				if(!Util.isVazioOuBranco(codigoSetorComercial)){
					if(!Util.isVazioOuBranco(idLocalidade)){
						FiltroSetorComercial filtro = new FiltroSetorComercial();
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercial));

						Collection<SetorComercial> colecaoSetorComercial = fachada.pesquisar(filtro, SetorComercial.class.getName());

						if(!Util.isVazioOrNulo(colecaoSetorComercial)){
							SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

							String descricao = setorComercial.getDescricao();
							form.setDescricaoSetorComercial(descricao);
						}else{
							form.setCodigoSetorComercial("");
							form.setDescricaoSetorComercial("SETOR COMERCIAL INEXISTENTE");
						}
					}
				}else{
					form.setCodigoSetorComercial("");
					form.setDescricaoSetorComercial("");
				}
			}else if(objetoConsulta.equals("3")){
				// Unidade Organizacional Origem
				if(!Util.isVazioOuBranco(idUnidadeOrganizacionalOrigem)){
					FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalOrigem));

					Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
									.getName());

					if(!Util.isVazioOrNulo(colecaoUnidadeOrganizacional)){
						UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
										.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

						String descricao = unidadeOrganizacional.getDescricao();
						form.setDescricaoUnidadeOrganizacionalOrigem(descricao);
					}else{
						form.setIdUnidadeOrganizacionalOrigem("");
						form.setDescricaoUnidadeOrganizacionalOrigem("UNIDADE DE ATENDIMENTO INEXISTENTE");
					}
				}else{
					form.setIdUnidadeOrganizacionalOrigem("");
					form.setDescricaoUnidadeOrganizacionalOrigem("");
				}
			}else if(objetoConsulta.equals("4")){
				// Unidade Organizacional Destino
				if(!Util.isVazioOuBranco(idUnidadeOrganizacionalDestino)){
					FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalDestino));

					Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
									.getName());

					if(!Util.isVazioOrNulo(colecaoUnidadeOrganizacional)){
						UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
										.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

						String descricao = unidadeOrganizacional.getDescricao();
						form.setDescricaoUnidadeOrganizacionalDestino(descricao);
					}else{
						form.setIdUnidadeOrganizacionalDestino("");
						form.setDescricaoUnidadeOrganizacionalDestino("UNIDADE DE ATENDIMENTO INEXISTENTE");
					}
				}else{
					form.setIdUnidadeOrganizacionalDestino("");
					form.setDescricaoUnidadeOrganizacionalDestino("");
				}
			}else if(objetoConsulta.equals("5")){
				// Unidade Organizacional Destino
				if(!Util.isVazioOuBranco(idMunicipio)){
					FiltroMunicipio filtro = new FiltroMunicipio();
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));

					Collection<Municipio> colecaoMunicipio = fachada.pesquisar(filtro, Municipio.class.getName());

					if(!Util.isVazioOrNulo(colecaoMunicipio)){
						Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);

						String descricao = municipio.getNome();
						form.setDescricaoMunicipio(descricao);

					}else{
						form.setIdMunicipio("");
						form.setDescricaoMunicipio("MUNICÍPIO INEXISTENTE");
					}
				}else{
					form.setIdMunicipio("");
					form.setDescricaoMunicipio("");
				}
			}else if(objetoConsulta.equals("6")){
				// Unidade Organizacional Destino
				if(!Util.isVazioOuBranco(codigoBairro)){
					FiltroBairro filtro = new FiltroBairro();
					filtro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, codigoBairro));

					if(!Util.isVazioOuBranco(idMunicipio)){
						filtro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipio));
					}else{
						throw new ActionServletException("atencao.informe_campo", null, "Município");
					}

					Collection<Bairro> colecaoBairro = fachada.pesquisar(filtro, Bairro.class.getName());

					if(!Util.isVazioOrNulo(colecaoBairro)){
						Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

						String descricao = bairro.getNome();
						form.setDescricaoBairro(descricao);
						form.setIdBairro(bairro.getId().toString());

					}else{
						form.setCodigoBairro("");
						form.setDescricaoBairro("BAIRRO INEXISTENTE");
					}
				}else{
					form.setCodigoBairro("");
					form.setDescricaoBairro("");
				}
			}
		}

		return retorno;
	}

	private String getRealForward(String upper){

		String forward = "";

		if("exibirServicoTipoTramitePopup".toUpperCase().equals(upper.toUpperCase())){
			forward = "exibirServicoTipoTramitePopup";
		}else{
			throw new IllegalArgumentException();
		}

		return forward;
	}

}
