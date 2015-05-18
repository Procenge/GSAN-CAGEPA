
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.EspecificacaoTramite;
import gcom.atendimentopublico.registroatendimento.FiltroTramiteEspecificacao;
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

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Atualizar Trâmite Especificação
 * 
 * @author Hebert Falcão
 * @date 25/03/2011
 */
public class ExibirAtualizarTramiteEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarTramiteEspecificacao");

		AtualizarTramiteEspecificacaoActionForm form = (AtualizarTramiteEspecificacaoActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null && !objetoConsulta.trim().equals("")){
			String idLocalidade = form.getIdLocalidade();
			String codigoSetorComercial = form.getCodigoSetorComercial();
			String idMunicipio = form.getIdMunicipio();
			String codigoBairro = form.getCodigoBairro();
			String idUnidadeOrganizacionalOrigem = form.getIdUnidadeOrganizacionalOrigem();
			String idUnidadeOrganizacionalDestino = form.getIdUnidadeOrganizacionalDestino();
			String indicadorPrimeiroTramite = form.getIndicadorPrimeiroTramite();

			// Limpando campos de descrição quando o id/código é vazio
			if(idLocalidade != null && idLocalidade.trim().equals("")){
				form.setDescricaoLocalidade("");
			}

			if(codigoSetorComercial != null && codigoSetorComercial.trim().equals("")){
				form.setDescricaoSetorComercial("");
			}

			if(idMunicipio != null && idMunicipio.trim().equals("")){
				form.setNomeMunicipio("");
			}

			if(codigoBairro != null && codigoBairro.trim().equals("")){
				form.setNomeBairro("");
			}

			if(idUnidadeOrganizacionalOrigem != null && idUnidadeOrganizacionalOrigem.trim().equals("")){
				form.setDescricaoUnidadeOrganizacionalOrigem("");
			}

			if(idUnidadeOrganizacionalDestino != null && idUnidadeOrganizacionalDestino.trim().equals("")){
				form.setDescricaoUnidadeOrganizacionalDestino("");
			}

			if(indicadorPrimeiroTramite != null && indicadorPrimeiroTramite.trim().equals("")){
				form.setIndicadorPrimeiroTramite(ConstantesSistema.NAO.toString());
			}

			if(objetoConsulta.equals("1")){
				// Localidade
				if(idLocalidade != null && !idLocalidade.trim().equals("")){
					FiltroLocalidade filtro = new FiltroLocalidade();
					filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

					Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());

					if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
						Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

						String id = Util.adicionarZerosEsqueda(3, idLocalidade);
						form.setIdLocalidade(id);

						String descricao = localidade.getDescricao();
						form.setDescricaoLocalidade(descricao);
					}else{
						httpServletRequest.setAttribute("localidadeNaoEncontrada", "true");

						form.setIdLocalidade("");
						form.setDescricaoLocalidade("");

						form.setCodigoSetorComercial("");
						form.setDescricaoSetorComercial("");

						httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
					}
				}
			}else if(objetoConsulta.equals("2")){
				// Setor Comercial
				if(codigoSetorComercial != null && !codigoSetorComercial.trim().equals("")){
					if(idLocalidade != null && !idLocalidade.trim().equals("")){
						FiltroSetorComercial filtro = new FiltroSetorComercial();
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));
						filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercial));

						Collection<SetorComercial> colecaoSetorComercial = fachada.pesquisar(filtro, SetorComercial.class.getName());

						if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){
							SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

							String codigo = Util.adicionarZerosEsqueda(3, codigoSetorComercial);
							form.setCodigoSetorComercial(codigo);

							String descricao = setorComercial.getDescricao();
							form.setDescricaoSetorComercial(descricao);
						}else{
							httpServletRequest.setAttribute("setorComercialNaoEncontrado", "true");

							form.setCodigoSetorComercial("");
							form.setDescricaoSetorComercial("");

							httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
						}
					}

				}else{
					form.setCodigoSetorComercial("");
					form.setDescricaoSetorComercial("");
				}
			}else if(objetoConsulta.equals("3")){
				// Município
				if(idMunicipio != null && !idMunicipio.trim().equals("")){
					FiltroMunicipio filtro = new FiltroMunicipio();
					filtro.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));

					Collection<Municipio> colecaoMunicipio = fachada.pesquisar(filtro, Municipio.class.getName());

					if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){
						Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);

						String id = Util.adicionarZerosEsqueda(4, idMunicipio);
						form.setIdMunicipio(id);

						String nome = municipio.getNome();
						form.setNomeMunicipio(nome);

						httpServletRequest.setAttribute("nomeCampo", "codigoBairro");
					}else{
						httpServletRequest.setAttribute("municipioNaoEncontrado", "true");

						form.setIdMunicipio("");
						form.setNomeMunicipio("");

						form.setCodigoBairro("");
						form.setNomeBairro("");

						httpServletRequest.setAttribute("nomeCampo", "idMunicipio");
					}
				}
			}else if(objetoConsulta.equals("4")){
				// Bairro
				if(codigoBairro != null && !codigoBairro.trim().equals("")){
					if(idMunicipio != null && !idMunicipio.trim().equals("")){
						FiltroBairro filtro = new FiltroBairro();
						filtro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
						filtro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipio));
						filtro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, codigoBairro));

						Collection<Bairro> colecaoBairro = fachada.pesquisar(filtro, Bairro.class.getName());

						if(colecaoBairro != null && !colecaoBairro.isEmpty()){
							Bairro setorComercial = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

							String codigo = Util.adicionarZerosEsqueda(4, codigoBairro);
							form.setCodigoBairro(codigo);

							String nome = setorComercial.getNome();
							form.setNomeBairro(nome);
						}else{
							httpServletRequest.setAttribute("bairroNaoEncontrado", "true");

							form.setCodigoBairro("");
							form.setNomeBairro("");

							httpServletRequest.setAttribute("nomeCampo", "codigoBairro");
						}
					}

				}else{
					form.setCodigoBairro("");
					form.setNomeBairro("");
				}
			}else if(objetoConsulta.equals("5")){
				// Unidade Organizacional Origem
				if(idUnidadeOrganizacionalOrigem != null && !idUnidadeOrganizacionalOrigem.trim().equals("")){
					FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalOrigem));

					Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
									.getName());

					if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){
						UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
										.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

						String id = Util.adicionarZerosEsqueda(8, idUnidadeOrganizacionalOrigem);
						form.setIdUnidadeOrganizacionalOrigem(id);

						String descricao = unidadeOrganizacional.getDescricao();
						form.setDescricaoUnidadeOrganizacionalOrigem(descricao);
					}else{
						httpServletRequest.setAttribute("unidadeOrganizacionalOrigemNaoEncontrado", "true");

						form.setIdUnidadeOrganizacionalOrigem("");
						form.setDescricaoUnidadeOrganizacionalOrigem("");

						httpServletRequest.setAttribute("nomeCampo", "idUnidadeOrganizacionalOrigem");
					}
				}
			}else if(objetoConsulta.equals("6")){
				// Unidade Organizacional Destino
				if(idUnidadeOrganizacionalDestino != null && !idUnidadeOrganizacionalDestino.trim().equals("")){
					FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacionalDestino));

					Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtro, UnidadeOrganizacional.class
									.getName());

					if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){
						UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
										.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

						String id = Util.adicionarZerosEsqueda(8, idUnidadeOrganizacionalDestino);
						form.setIdUnidadeOrganizacionalDestino(id);

						String descricao = unidadeOrganizacional.getDescricao();
						form.setDescricaoUnidadeOrganizacionalDestino(descricao);
					}else{
						httpServletRequest.setAttribute("unidadeOrganizacionalDestinoNaoEncontrado", "true");

						form.setIdUnidadeOrganizacionalDestino("");
						form.setDescricaoUnidadeOrganizacionalDestino("");

						httpServletRequest.setAttribute("nomeCampo", "idUnidadeOrganizacionalDestino");
					}
				}
			}
		}else{
			String idEspecificacaoTramite = form.getId();

			if(idEspecificacaoTramite == null || idEspecificacaoTramite.trim().equals("")){
				idEspecificacaoTramite = httpServletRequest.getParameter("idEspecificacaoTramite");

				if(idEspecificacaoTramite == null || idEspecificacaoTramite.trim().equals("")){
					idEspecificacaoTramite = (String) sessao.getAttribute("idEspecificacaoTramite");
				}
			}

			if(idEspecificacaoTramite == null || idEspecificacaoTramite.trim().equals("")){
				throw new ActionServletException("atencao.id_especificacao_tramite_nao_informado");
			}

			FiltroTramiteEspecificacao filtroTramiteEspecificacao = new FiltroTramiteEspecificacao();
			filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.ID, idEspecificacaoTramite));
			filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SOLICITACAO_TIPO);
			filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO);
			filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.LOCALIDADE);
			filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.SETOR_COMERCIAL);
			filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.BAIRRO);
			filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.MUNICIPIO);

			filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.UNID_ORGANIZACIONAL_ORIGEM);
			filtroTramiteEspecificacao.adicionarCaminhoParaCarregamentoEntidade(FiltroTramiteEspecificacao.UNID_ORGANIZACIONAL_DESTINO);

			Collection<EspecificacaoTramite> colecaoPesquisa = fachada.pesquisar(filtroTramiteEspecificacao, EspecificacaoTramite.class
							.getName());

			EspecificacaoTramite especificacaoTramite = null;

			if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
				especificacaoTramite = (EspecificacaoTramite) Util.retonarObjetoDeColecao(colecaoPesquisa);
			}

			if(especificacaoTramite == null){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}

			sessao.setAttribute("especificacaoTramite", especificacaoTramite);

			form.setFormValues(especificacaoTramite);





		}

		return retorno;
	}




}