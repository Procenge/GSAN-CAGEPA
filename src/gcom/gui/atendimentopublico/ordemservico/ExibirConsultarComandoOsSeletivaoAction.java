
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgoto;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.micromedicao.hidrometro.*;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;

public class ExibirConsultarComandoOsSeletivaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarComandoOsSeletiva");

		ConsultarComandoOsSeletivaActionForm consultarComandoOsSeletivaActionForm = (ConsultarComandoOsSeletivaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = this.getFachada();
		String idComando = null;
		OsSeletivaComando osseletivaComando = null;
		OsSeletivaComandoLocalGeo osSeletivaComandoLocalGeo = null;
		ArrayList<SetorComercial> relacaoSetorComercial = new ArrayList<SetorComercial>();
		ArrayList<Quadra> relacaoQuadra = new ArrayList<Quadra>();
		ArrayList<Rota> relacaoRota = new ArrayList<Rota>();
		ArrayList<String> relacaoLote = new ArrayList<String>();
		ArrayList<Bairro> relacaoBairro = new ArrayList<Bairro>();
		ArrayList<Logradouro> relacaoLogradouro = new ArrayList<Logradouro>();
		ArrayList<ImovelPerfil> relacaoPerfilImovel = new ArrayList<ImovelPerfil>();
		ArrayList<Categoria> relacaoCategoria = new ArrayList<Categoria>();
		ArrayList<Subcategoria> relacaoSubCategoria = new ArrayList<Subcategoria>();
		ArrayList<LigacaoAguaSituacao> relacaoLigacaoAguaSituacao = new ArrayList<LigacaoAguaSituacao>();
		ArrayList<LigacaoEsgotoSituacao> relacaoLigacaoEsgotoSituacao = new ArrayList<LigacaoEsgotoSituacao>();
		ArrayList<OsSeletivaComandoConsumoMedio> relacaoConsumoMedio = new ArrayList<OsSeletivaComandoConsumoMedio>();
		ArrayList<HidrometroMarca> relacaoHidrometroMarca = new ArrayList<HidrometroMarca>();
		// ArrayList<HidrometroClasseMetrologica> relacaoHidrometroClasseMetrologica = new
		// ArrayList<HidrometroClasseMetrologica>();
		// ArrayList<HidrometroProtecao> relacaoHidrometroProtecao = new
		// ArrayList<HidrometroProtecao>();
		// ArrayList<HidrometroLocalInstalacao> relacaoHidrometroLocalInstalacao = new
		// ArrayList<HidrometroLocalInstalacao>();
		ArrayList<LeituraAnormalidade> relacaoHidrometroAnormalidade = new ArrayList<LeituraAnormalidade>();
		ArrayList<DadosDoHidrometroHelper> relacaoDadosHidrometroHelper = new ArrayList<DadosDoHidrometroHelper>();
		ArrayList<DadosOrdemServicoHelper> relacaoDadosOrdemServico = new ArrayList<DadosOrdemServicoHelper>();
		if(httpServletRequest.getParameter("numeroComando") != null || sessao.getAttribute("numeroComando") != null){

			if(httpServletRequest.getParameter("numeroComando") != null){

				idComando = httpServletRequest.getParameter("numeroComando");
			}else{

				idComando = sessao.getAttribute("numeroComando").toString();
			}

			FiltroOsSeletivaComando filtroOsSeletivaComando = new FiltroOsSeletivaComando();
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("empresa");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupo");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoLocalGeo");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoImovelPerfil");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoCategoriaSubcategoria");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoLigacaoAgua");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoLigacaoEsgoto");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoConsumoMedio");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoHidrometroMarca");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoHidrometroClasse");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoHidrometroProtecao");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoHidrometroLocalInstacao");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoLeituraAnormalidade");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("osSeletivaComandoHidrometroDiametro");
			filtroOsSeletivaComando.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
			filtroOsSeletivaComando.adicionarParametro(new ParametroSimples(FiltroOsSeletivaComando.ID, idComando));

			Collection colecaoOsseletivaComando = fachada.pesquisar(filtroOsSeletivaComando, OsSeletivaComando.class.getName());

			// 1.1. Dados do Filtro para Geração do Comando
			if(colecaoOsseletivaComando != null && !colecaoOsseletivaComando.isEmpty()){

				osseletivaComando = (OsSeletivaComando) Util.retonarObjetoDeColecao(colecaoOsseletivaComando);

				if(osseletivaComando != null){

					consultarComandoOsSeletivaActionForm.setTitulo(osseletivaComando.getDescricaoTitulo());

					if(osseletivaComando.getEmpresa() != null){

						consultarComandoOsSeletivaActionForm.setIdFirma(osseletivaComando.getEmpresa().getId().toString());
						consultarComandoOsSeletivaActionForm.setDescricaoFirma(osseletivaComando.getEmpresa().getDescricao());

					}
					if(osseletivaComando.getServicoTipo() != null){

						consultarComandoOsSeletivaActionForm.setIdServicoTipo(osseletivaComando.getServicoTipo().getId().toString());
						consultarComandoOsSeletivaActionForm.setDescricaoServicoTipo(osseletivaComando.getServicoTipo().getDescricao());

					}
					if(osseletivaComando.getCodigoElo() != null){

						FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
						filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, osseletivaComando.getCodigoElo()));

						Collection colecaoELo = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

						if(colecaoELo != null && !colecaoELo.isEmpty()){

							Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoELo);

							consultarComandoOsSeletivaActionForm.setIdElo(localidade.getId().toString());
							consultarComandoOsSeletivaActionForm.setDescricaoElo(localidade.getDescricao());

						}
					}

					if(osseletivaComando.getFaturamentoGrupo() != null){

						consultarComandoOsSeletivaActionForm.setIdFaturamentoGrupo(osseletivaComando.getFaturamentoGrupo().getId()
										.toString());
						consultarComandoOsSeletivaActionForm.setDescricaoFaturamentoGrupo(osseletivaComando.getFaturamentoGrupo()
										.getDescricao());
					}

					if(osseletivaComando.getArquivoImovel() != null){

						try{
							byte[] arrayByte = osseletivaComando.getArquivoImovel();
							ArrayList<Integer> idImoveis = new ArrayList<Integer>();
							FileReader fileReader = null;
							(Hibernate.createBlob(osseletivaComando.getArquivoImovel())).getBinaryStream();
							File arquivo = new File("arquivoImoveis" + System.currentTimeMillis() + ".txt");
							if(!arquivo.exists()){
								arquivo.createNewFile();
							}
							FileOutputStream fos = new FileOutputStream(arquivo);
							fos.write(arrayByte);
							fos.flush();
							fos.close();
							fileReader = new FileReader(arquivo);
							BufferedReader br = new BufferedReader(fileReader);
							String linha = null;
							while((linha = br.readLine()) != null){
								if(Util.isInteger(linha)){
									idImoveis.add(Util.obterInteger(linha));
								}
							}
							fileReader.close();
							br.close();
							arquivo.delete();

							Collections.sort(idImoveis, new Comparator() {

								public int compare(Object o1, Object o2){

									Integer numerol1 = Integer.parseInt(o1.toString());
									Integer numerol2 = Integer.parseInt(o2.toString());

									return numerol1.compareTo(numerol2);
								}
							});

							sessao.setAttribute("colecaoImovel", idImoveis);

						}catch(SQLException e){
							e.printStackTrace();

						}catch(IOException e){

							e.printStackTrace();
						}

					}

					// 1.1.1.8. Dados de Localização Geográfica
					if(osseletivaComando.getOsSeletivaComandoLocalGeo() != null){

						Iterator iterator = osseletivaComando.getOsSeletivaComandoLocalGeo().iterator();
						while(iterator.hasNext()){

							osSeletivaComandoLocalGeo = (OsSeletivaComandoLocalGeo) iterator.next();

							if(osSeletivaComandoLocalGeo.getGerenciaRegional() != null){

								FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
								filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID,
												osSeletivaComandoLocalGeo.getGerenciaRegional().getId()));

								Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional,
												GerenciaRegional.class.getName());

								if(colecaoGerenciaRegional != null && !colecaoGerenciaRegional.isEmpty()){

									GerenciaRegional gerenciaRegional = (GerenciaRegional) Util
													.retonarObjetoDeColecao(colecaoGerenciaRegional);

									consultarComandoOsSeletivaActionForm.setNomeGerenciaRegional(gerenciaRegional.getNome());

								}
							}
							if(osSeletivaComandoLocalGeo.getLocalidade() != null){

								FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
								filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, osSeletivaComandoLocalGeo
												.getLocalidade().getId()));

								Collection colecaoELo = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

								if(colecaoELo != null && !colecaoELo.isEmpty()){

									Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoELo);

									consultarComandoOsSeletivaActionForm.setNomeLocalidade(localidade.getDescricao());

								}

							}
							if(osSeletivaComandoLocalGeo.getCodigoSetorComercial() != null){

								FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
								filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID,
												osSeletivaComandoLocalGeo.getSetorComercial().getId()));

								Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

								SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

								if(!relacaoSetorComercial.contains(setorComercial)){

									relacaoSetorComercial.add(setorComercial);
								}
							}

							if(osSeletivaComandoLocalGeo.getQuadra() != null){

								FiltroQuadra filtroQuadra = new FiltroQuadra();
								filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, osSeletivaComandoLocalGeo.getQuadra()
												.getId()));

								Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

								Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

								if(!relacaoQuadra.contains(quadra)){
									relacaoQuadra.add(quadra);
								}
							}

							if(osSeletivaComandoLocalGeo.getRota() != null){

								FiltroRota filtroRota = new FiltroRota();
								filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, osSeletivaComandoLocalGeo.getRota()
												.getId()));

								Collection colecaoRota = fachada.pesquisar(filtroRota, Rota.class.getName());

								Rota rota = (Rota) Util.retonarObjetoDeColecao(colecaoRota);

								if(!relacaoRota.contains(rota)){

									relacaoRota.add(rota);
								}

								if(osSeletivaComandoLocalGeo.getBairro() != null){

									FiltroBairro filtroBairro = new FiltroBairro();
									filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, osSeletivaComandoLocalGeo
													.getBairro().getId()));

									Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

									Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

									if(!relacaoBairro.equals(bairro)){
										relacaoBairro.add(bairro);
									}
								}

								if(osSeletivaComandoLocalGeo.getLogradouro() != null){

									FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
									filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, osSeletivaComandoLocalGeo
													.getLogradouro().getId()));

									Collection colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

									Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

									if(!relacaoLogradouro.equals(logradouro)){
										relacaoLogradouro.add(logradouro);
									}
								}
							}

							if(osSeletivaComandoLocalGeo.getNumeroLote() != null){

								String numeroLote = osSeletivaComandoLocalGeo.getNumeroLote().toString();

								if(!relacaoLote.contains(numeroLote)){

									relacaoLote.add(numeroLote);
								}

							}
						}

						Collections.sort(relacaoSetorComercial, new Comparator() {

							public int compare(Object o1, Object o2){

								SetorComercial l1 = (SetorComercial) o1;
								SetorComercial l2 = (SetorComercial) o2;
								return l1.getDescricaoComCodigo().compareToIgnoreCase(l2.getDescricaoComCodigo());
							}
						});

						sessao.setAttribute("relacaoSetorComercial", relacaoSetorComercial);

						Collections.sort(relacaoQuadra, new Comparator() {

							public int compare(Object o1, Object o2){

								Quadra l1 = (Quadra) o1;
								Quadra l2 = (Quadra) o2;

								Integer numerol1 = l1.getNumeroQuadra();
								Integer numerol2 = l2.getNumeroQuadra();

								return numerol1.compareTo(numerol2);
							}
						});

						sessao.setAttribute("relacaoQuadra", relacaoQuadra);

						Collections.sort(relacaoRota, new Comparator() {

							public int compare(Object o1, Object o2){

								Rota l1 = (Rota) o1;
								Rota l2 = (Rota) o2;

								Integer numerol1 = l1.getId();
								Integer numerol2 = l2.getId();

								return numerol1.compareTo(numerol2);
							}
						});

						sessao.setAttribute("relacaoRota", relacaoRota);

						Collections.sort(relacaoLote, new Comparator() {

							public int compare(Object o1, Object o2){

								Integer numerol1 = Integer.parseInt(o1.toString());
								Integer numerol2 = Integer.parseInt(o2.toString());

								return numerol1.compareTo(numerol2);
							}
						});

						sessao.setAttribute("relacaoLote", relacaoLote);

						Collections.sort(relacaoBairro, new Comparator() {

							public int compare(Object o1, Object o2){

								Bairro l1 = (Bairro) o1;
								Bairro l2 = (Bairro) o2;
								return l1.getNome().compareToIgnoreCase(l2.getNome());
							}
						});

						sessao.setAttribute("relacaoBairro", relacaoBairro);

						Collections.sort(relacaoLogradouro, new Comparator() {

							public int compare(Object o1, Object o2){

								Logradouro l1 = (Logradouro) o1;
								Logradouro l2 = (Logradouro) o2;
								return l1.getNome().compareToIgnoreCase(l2.getNome());
							}
						});

						sessao.setAttribute("relacaoLogradouro", relacaoLogradouro);
					}

					// 1.1.2.Características
					if(osseletivaComando.getOsSeletivaComandoImovelPerfil() != null){

						Iterator iteratorP = osseletivaComando.getOsSeletivaComandoImovelPerfil().iterator();
						while(iteratorP.hasNext()){

							OsSeletivaComandoImovelPerfil osSeletivaComandoImovelPerfil = (OsSeletivaComandoImovelPerfil) iteratorP.next();

							if(osSeletivaComandoImovelPerfil.getImovelPerfil() != null){

								FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
								filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID,
												osSeletivaComandoImovelPerfil.getImovelPerfil().getId()));

								Collection<ImovelPerfil> colecaoImovelPerfil = fachada.pesquisar(filtroImovelPerfil,
												ImovelPerfil.class.getName());

								ImovelPerfil imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(colecaoImovelPerfil);

								if(!relacaoPerfilImovel.contains(imovelPerfil)){

									relacaoPerfilImovel.add(imovelPerfil);

								}

							}
						}

						Collections.sort(relacaoPerfilImovel, new Comparator() {

							public int compare(Object o1, Object o2){

								ImovelPerfil l1 = (ImovelPerfil) o1;
								ImovelPerfil l2 = (ImovelPerfil) o2;
								return l1.getDescricao().compareToIgnoreCase(l2.getDescricao());
							}
						});

						sessao.setAttribute("relacaoPerfilImovel", relacaoPerfilImovel);

					}

					if(osseletivaComando.getOsSeletivaComandoCategoriaSubcategoria() != null){

						Iterator iteratorP = osseletivaComando.getOsSeletivaComandoCategoriaSubcategoria().iterator();

						while(iteratorP.hasNext()){

							OsSeletivaComandoCategoriaSubcategoria osSeletivaComandoCategoriaSubCategoria = (OsSeletivaComandoCategoriaSubcategoria) iteratorP
											.next();

							if(osSeletivaComandoCategoriaSubCategoria.getCategoria() != null){

								FiltroCategoria filtroCategoria = new FiltroCategoria();
								filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO,
												osSeletivaComandoCategoriaSubCategoria.getCategoria().getId()));

								Collection<Categoria> colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

								Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoCategoria);

								if(!relacaoCategoria.contains(categoria)){

									relacaoCategoria.add(categoria);

								}
							}

							if(osSeletivaComandoCategoriaSubCategoria.getSubCategoria() != null){

								FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
								filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID,
												osSeletivaComandoCategoriaSubCategoria.getSubCategoria().getId()));

								Collection<Subcategoria> colecaoSubCategoria = fachada.pesquisar(filtroSubCategoria,
												Subcategoria.class.getName());

								Subcategoria subcategoria = (Subcategoria) Util.retonarObjetoDeColecao(colecaoSubCategoria);

								if(!relacaoSubCategoria.contains(subcategoria)){

									relacaoSubCategoria.add(subcategoria);

								}
							}
						}

						Collections.sort(relacaoCategoria, new Comparator() {

							public int compare(Object o1, Object o2){

								Categoria l1 = (Categoria) o1;
								Categoria l2 = (Categoria) o2;
								return l1.getDescricao().compareToIgnoreCase(l2.getDescricao());
							}
						});

						sessao.setAttribute("relacaoCategoria", relacaoCategoria);

						Collections.sort(relacaoSubCategoria, new Comparator() {

							public int compare(Object o1, Object o2){

								Subcategoria l1 = (Subcategoria) o1;
								Subcategoria l2 = (Subcategoria) o2;
								return l1.getDescricao().compareToIgnoreCase(l2.getDescricao());
							}
						});

						sessao.setAttribute("relacaoSubCategoria", relacaoSubCategoria);

					}

					if(osseletivaComando.getOsSeletivaComandoLigacaoAgua() != null){

						Iterator iteratorA = osseletivaComando.getOsSeletivaComandoLigacaoAgua().iterator();
						while(iteratorA.hasNext()){

							OsSeletivaComandoLigacaoAgua osSeletivaComandoLigacaoAgua = (OsSeletivaComandoLigacaoAgua) iteratorA.next();

							if(osSeletivaComandoLigacaoAgua.getLigacaoAguaSituacao() != null){

								FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
								filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID,
												osSeletivaComandoLigacaoAgua.getLigacaoAguaSituacao().getId()));

								Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAgua,
												LigacaoAguaSituacao.class.getName());

								LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) Util
												.retonarObjetoDeColecao(colecaoLigacaoAguaSituacao);

								if(!relacaoLigacaoAguaSituacao.contains(ligacaoAguaSituacao)){

									relacaoLigacaoAguaSituacao.add(ligacaoAguaSituacao);

								}
							}
						}

						Collections.sort(relacaoSubCategoria, new Comparator() {

							public int compare(Object o1, Object o2){

								LigacaoAguaSituacao l1 = (LigacaoAguaSituacao) o1;
								LigacaoAguaSituacao l2 = (LigacaoAguaSituacao) o2;
								return l1.getDescricao().compareToIgnoreCase(l2.getDescricao());
							}
						});

						sessao.setAttribute("relacaoLigacaoAguaSituacao", relacaoLigacaoAguaSituacao);

					}

					if(osseletivaComando.getOsSeletivaComandoLigacaoEsgoto() != null){

						Iterator iteratorE = osseletivaComando.getOsSeletivaComandoLigacaoEsgoto().iterator();
						while(iteratorE.hasNext()){

							OsSeletivaComandoLigacaoEsgoto OsSeletivaComandoLigacaoEsgoto = (OsSeletivaComandoLigacaoEsgoto) iteratorE
											.next();

							if(OsSeletivaComandoLigacaoEsgoto.getLigacaoEsgotoSituacao() != null){

								FiltroLigacaoEsgoto filtroLigacaoEsgoto = new FiltroLigacaoEsgoto();
								filtroLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgoto.ID,
												OsSeletivaComandoLigacaoEsgoto.getLigacaoEsgotoSituacao().getId()));

								Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgoto,
												LigacaoEsgotoSituacao.class.getName());

								LigacaoEsgotoSituacao ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) Util
												.retonarObjetoDeColecao(colecaoLigacaoEsgotoSituacao);

								if(!relacaoLigacaoEsgotoSituacao.contains(ligacaoEsgotoSituacao)){

									relacaoLigacaoEsgotoSituacao.add(ligacaoEsgotoSituacao);

								}
							}
						}

						Collections.sort(relacaoSubCategoria, new Comparator() {

							public int compare(Object o1, Object o2){

								LigacaoEsgotoSituacao l1 = (LigacaoEsgotoSituacao) o1;
								LigacaoEsgotoSituacao l2 = (LigacaoEsgotoSituacao) o2;
								return l1.getDescricao().compareToIgnoreCase(l2.getDescricao());
							}
						});

						sessao.setAttribute("relacaoLigacaoEsgotoSituacao", relacaoLigacaoEsgotoSituacao);
					}

					if(osseletivaComando.getQuantidadeEconomiasInicial() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloQuantidadeEconomiasInicial(osseletivaComando
										.getQuantidadeEconomiasInicial().toString());

					}
					if(osseletivaComando.getQuantidadeEconomiasFinal() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloQuantidadeEconomiasFinal(osseletivaComando
										.getQuantidadeEconomiasFinal().toString());

					}

					if(osseletivaComando.getQuantidadeDocumentosInicial() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloQuantidadeDocumentosInicial(osseletivaComando
										.getQuantidadeDocumentosInicial().toString());

					}

					if(osseletivaComando.getQuantidadeDocumentosFinal() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloQuantidadeDocumentosFinal(osseletivaComando
										.getQuantidadeDocumentosFinal().toString());
					}

					if(osseletivaComando.getNumeroMoradoresInicial() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloNumeroMoradoresInicial(osseletivaComando
										.getNumeroMoradoresInicial().toString());

					}
					if(osseletivaComando.getNumeroMoradoresFinal() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloNumeroMoradoresFinal(osseletivaComando.getNumeroMoradoresFinal()
										.toString());

					}
					if(osseletivaComando.getNumeroPontosUtilizacaoInicial() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloNumeroPontosUtilizacaoInicial(osseletivaComando
										.getNumeroPontosUtilizacaoInicial().toString());
					}
					if(osseletivaComando.getNumeroPontosUtilizacaoFinal() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloNumeroPontosUtilizacaoFinal(osseletivaComando
										.getNumeroPontosUtilizacaoFinal().toString());

					}
					if(osseletivaComando.getNumeroAreaConstruidaInicial() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloAreaConstruidaInicial(osseletivaComando
										.getNumeroAreaConstruidaInicial().toString());

					}
					if(osseletivaComando.getNumeroAreaConstruidaFinal() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloAreaConstruidaFinal(osseletivaComando
										.getNumeroAreaConstruidaFinal().toString());

					}
					if(osseletivaComando.getAreaConstruidaFaixa() != null){

						consultarComandoOsSeletivaActionForm.setAreaContruidaInicial(osseletivaComando.getAreaConstruidaFaixa()
										.getVolumeMenorFaixa().toString());
						consultarComandoOsSeletivaActionForm.setAreaConstruidadeFinal(osseletivaComando.getAreaConstruidaFaixa()
										.getVolumeMaiorFaixa().toString());

					}

					if(osseletivaComando.getIndicadorImovelCondominio() != null){

						consultarComandoOsSeletivaActionForm.setImovelCondominio(osseletivaComando.getIndicadorImovelCondominio()
										.toString());

					}

					if(osseletivaComando.getNumeroConsumoEconomia() != null){

						consultarComandoOsSeletivaActionForm.setConsumoPorEconomia(osseletivaComando.getNumeroConsumoEconomia().toString());

					}
					if(osseletivaComando.getNumeroFaixaConsumoInicial() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloNumeroConsumoMesInicial(osseletivaComando
										.getNumeroFaixaConsumoInicial().toString());

					}
					if(osseletivaComando.getNumeroFaixaConsumoFinal() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloNumeroConsumoMesFinal(osseletivaComando
										.getNumeroFaixaConsumoFinal().toString());

					}

					if(osseletivaComando.getOsSeletivaComandoConsumoMedio() != null){

						Iterator iteratorE = osseletivaComando.getOsSeletivaComandoConsumoMedio().iterator();
						while(iteratorE.hasNext()){

							OsSeletivaComandoConsumoMedio osSeletivaComandoConsumoMedio = (OsSeletivaComandoConsumoMedio) iteratorE.next();

							if(!relacaoConsumoMedio.contains(osSeletivaComandoConsumoMedio)){

								relacaoConsumoMedio.add(osSeletivaComandoConsumoMedio);

							}
						}

						Collections.sort(relacaoConsumoMedio, new Comparator() {

							public int compare(Object o1, Object o2){

								OsSeletivaComandoConsumoMedio l1 = (OsSeletivaComandoConsumoMedio) o1;
								OsSeletivaComandoConsumoMedio l2 = (OsSeletivaComandoConsumoMedio) o2;
								return l1.getNumeroConsumoMedioInicial().toString()
												.compareToIgnoreCase(l2.getNumeroConsumoMedioInicial().toString());
							}
						});

						sessao.setAttribute("relacaoConsumoMedio", relacaoConsumoMedio);

					}

					if(osseletivaComando.getQuantidadeDebitoFinal() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloQuantidadeContasVencidasInicial(osseletivaComando
										.getQuantidadeDebitoInicial().toString());

					}
					if(osseletivaComando.getQuantidadeDebitoFinal() != null){

						consultarComandoOsSeletivaActionForm.setIntervaloQuantidadeContasVencidasFinal(osseletivaComando
										.getQuantidadeDebitoFinal().toString());

					}
					if(osseletivaComando.getValorDebito() != null){

						consultarComandoOsSeletivaActionForm.setValorTotalDebitoVencido(osseletivaComando.getValorDebito().toString());

					}
					// Hidrômetro
					if(osseletivaComando.getServicoTipo() != null && osseletivaComando.getServicoTipo().getServicoTipoSubgrupo() != null){

						Boolean substituicaoHidrometro = false;

						try{
							substituicaoHidrometro = fachada.comparaServicoTipoSubgrupoSubstituicaoHidrometro(osseletivaComando
											.getServicoTipo().getId().toString());

						}catch(ControladorException e){

							e.printStackTrace();
						}

						if(substituicaoHidrometro == true){

							if(osseletivaComando.getOsSeletivaComandoHidrometroMarca() != null){

								Iterator iterator = osseletivaComando.getOsSeletivaComandoHidrometroMarca().iterator();
								while(iterator.hasNext()){

									OsSeletivaComandoHidrometroMarca osSeletivaComandoHidrometroMarca = (OsSeletivaComandoHidrometroMarca) iterator
													.next();

									FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();
									filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.ID,
													osSeletivaComandoHidrometroMarca.getHidrometroMarca().getId()));

									Collection colecaoHidrometroMarca = fachada.pesquisar(filtroHidrometroMarca,
													HidrometroMarca.class.getName());

									HidrometroMarca hidrometroMarca = (HidrometroMarca) Util.retonarObjetoDeColecao(colecaoHidrometroMarca);

									if(!relacaoHidrometroMarca.contains(hidrometroMarca)){

										relacaoHidrometroMarca.add(hidrometroMarca);

									}
								}

								Collections.sort(relacaoHidrometroMarca, new Comparator() {

									public int compare(Object o1, Object o2){

										HidrometroMarca l1 = (HidrometroMarca) o1;
										HidrometroMarca l2 = (HidrometroMarca) o2;
										return l1.getDescricao().toString().compareToIgnoreCase(l2.getDescricao().toString());
									}
								});

								sessao.setAttribute("relacaoHidrometroMarca", relacaoHidrometroMarca);

							}
							/*
							 * if(osseletivaComando.getOsSeletivaComandoHidrometroClasse() != null){
							 * Iterator iterator =
							 * osseletivaComando.getOsSeletivaComandoHidrometroClasse().iterator();
							 * while(iterator.hasNext()){
							 * OsSeletivaComandoHidrometroClasse osSeletivaComandoHidrometroClasse =
							 * (OsSeletivaComandoHidrometroClasse) iterator
							 * .next();
							 * FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica =
							 * new
							 * FiltroHidrometroClasseMetrologica();
							 * filtroHidrometroClasseMetrologica.adicionarParametro(new
							 * ParametroSimples(FiltroHidrometroClasseMetrologica.ID,
							 * osSeletivaComandoHidrometroClasse.getHidrometroClasseMetrologica().getId
							 * ()
							 * ));
							 * Collection colecaoHidrometroClasse =
							 * fachada.pesquisar(filtroHidrometroClasseMetrologica,
							 * HidrometroClasseMetrologica.class.getName());
							 * HidrometroClasseMetrologica hidrometroClasseMetrologica =
							 * (HidrometroClasseMetrologica) Util
							 * .retonarObjetoDeColecao(colecaoHidrometroClasse);
							 * if(!relacaoHidrometroClasseMetrologica.contains(
							 * hidrometroClasseMetrologica
							 * )){
							 * relacaoHidrometroClasseMetrologica.add(hidrometroClasseMetrologica);
							 * }
							 * }
							 * Collections.sort(relacaoHidrometroClasseMetrologica, new Comparator()
							 * {
							 * public int compare(Object o1, Object o2){
							 * HidrometroClasseMetrologica l1 = (HidrometroClasseMetrologica) o1;
							 * HidrometroClasseMetrologica l2 = (HidrometroClasseMetrologica) o2;
							 * return
							 * l1.getDescricao().toString().compareToIgnoreCase(l2.getDescricao().
							 * toString
							 * ());
							 * }
							 * });
							 * sessao.setAttribute("relacaoHidrometroClasseMetrologica",
							 * relacaoHidrometroClasseMetrologica);
							 * }
							 * if(osseletivaComando.getOsSeletivaComandoHidrometroProtecao() !=
							 * null){
							 * Iterator iterator =
							 * osseletivaComando.getOsSeletivaComandoHidrometroProtecao().iterator();
							 * while(iterator.hasNext()){
							 * OsSeletivaComandoHidrometroProtecao
							 * osSeletivaComandoHidrometroProtecao =
							 * (OsSeletivaComandoHidrometroProtecao) iterator
							 * .next();
							 * FiltroHidrometroProtecao filtroHidrometroProtecao = new
							 * FiltroHidrometroProtecao();
							 * filtroHidrometroProtecao.adicionarParametro(new
							 * ParametroSimples(FiltroHidrometroProtecao.ID,
							 * osSeletivaComandoHidrometroProtecao.getHidrometroProtecao().getId()));
							 * Collection colecaoHidrometroProtecao =
							 * fachada.pesquisar(filtroHidrometroProtecao,
							 * HidrometroClasseMetrologica.class.getName());
							 * HidrometroProtecao hidrometroProtecao = (HidrometroProtecao) Util
							 * .retonarObjetoDeColecao(colecaoHidrometroProtecao);
							 * if(!relacaoHidrometroProtecao.contains(hidrometroProtecao)){
							 * relacaoHidrometroProtecao.add(hidrometroProtecao);
							 * }
							 * }
							 * Collections.sort(relacaoHidrometroProtecao, new Comparator() {
							 * public int compare(Object o1, Object o2){
							 * HidrometroProtecao l1 = (HidrometroProtecao) o1;
							 * HidrometroProtecao l2 = (HidrometroProtecao) o2;
							 * return
							 * l1.getDescricao().toString().compareToIgnoreCase(l2.getDescricao().
							 * toString
							 * ());
							 * }
							 * });
							 * sessao.setAttribute("relacaohidrometroProtecao",
							 * relacaoHidrometroProtecao);
							 * }
							 * if(osseletivaComando.getOsSeletivaComandoHidrometroLocalInstacao() !=
							 * null){
							 * Iterator iterator =
							 * osseletivaComando.getOsSeletivaComandoHidrometroLocalInstacao().iterator
							 * ()
							 * ;
							 * while(iterator.hasNext()){
							 * OsSeletivaComandoHidrometroLocalInstacao
							 * osSeletivaComandoHidrometroLocalInstacao =
							 * (OsSeletivaComandoHidrometroLocalInstacao) iterator
							 * .next();
							 * FiltroHidrometroLocalInstalacao filtroHidrometroLocalInstalacao = new
							 * FiltroHidrometroLocalInstalacao();
							 * filtroHidrometroLocalInstalacao.adicionarParametro(new
							 * ParametroSimples(FiltroHidrometroLocalInstalacao.ID,
							 * osSeletivaComandoHidrometroLocalInstacao.getHidrometroLocalInstalacao(
							 * ).getId
							 * (
							 * )));
							 * Collection colecaoHidrometroLocalInstalacao =
							 * fachada.pesquisar(filtroHidrometroLocalInstalacao,
							 * HidrometroLocalInstalacao.class.getName());
							 * HidrometroLocalInstalacao hidrometroLocalInstalacao =
							 * (HidrometroLocalInstalacao) Util
							 * .retonarObjetoDeColecao(colecaoHidrometroLocalInstalacao);
							 * if(!relacaoHidrometroLocalInstalacao.contains(hidrometroLocalInstalacao
							 * )){
							 * relacaoHidrometroLocalInstalacao.add(hidrometroLocalInstalacao);
							 * }
							 * }
							 * Collections.sort(relacaoHidrometroLocalInstalacao, new Comparator() {
							 * public int compare(Object o1, Object o2){
							 * HidrometroLocalInstalacao l1 = (HidrometroLocalInstalacao) o1;
							 * HidrometroLocalInstalacao l2 = (HidrometroLocalInstalacao) o2;
							 * return
							 * l1.getDescricao().toString().compareToIgnoreCase(l2.getDescricao().
							 * toString
							 * ());
							 * }
							 * });
							 * sessao.setAttribute("relacaoHidrometroLocalInstalacao",
							 * relacaoHidrometroLocalInstalacao);
							 * }
							 */

							if(osseletivaComando.getOsSeletivaComandoLeituraAnormalidade() != null){

								Iterator iterator = osseletivaComando.getOsSeletivaComandoLeituraAnormalidade().iterator();
								while(iterator.hasNext()){

									OsSeletivaComandoLeituraAnormalidade osSeletivaComandoLeituraAnormalidade = (OsSeletivaComandoLeituraAnormalidade) iterator
													.next();

									FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
									filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID,
													osSeletivaComandoLeituraAnormalidade.getLeituraAnormalidade().getId()));

									Collection colecaoHidrometroLeituraAnormalidade = fachada.pesquisar(filtroLeituraAnormalidade,
													LeituraAnormalidade.class.getName());

									LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) Util
													.retonarObjetoDeColecao(colecaoHidrometroLeituraAnormalidade);

									if(!relacaoHidrometroAnormalidade.contains(leituraAnormalidade)){

										relacaoHidrometroAnormalidade.add(leituraAnormalidade);

									}
								}

								Collections.sort(relacaoHidrometroAnormalidade, new Comparator() {

									public int compare(Object o1, Object o2){

										LeituraAnormalidade l1 = (LeituraAnormalidade) o1;
										LeituraAnormalidade l2 = (LeituraAnormalidade) o2;
										return l1.getDescricao().toString().compareToIgnoreCase(l2.getDescricao().toString());
									}
								});

								sessao.setAttribute("relacaoHidrometroAnormalidade", relacaoHidrometroAnormalidade);

							}

							if(osseletivaComando.getNumeroOcorrenciasConsecutivas() != null){

								consultarComandoOsSeletivaActionForm.setNumeroOcorrenciasConsecutivas(osseletivaComando
												.getNumeroOcorrenciasConsecutivas().toString());

							}

							if(osseletivaComando.getOsSeletivaComandoHidrometroDiametro() != null){

								Iterator iterator = osseletivaComando.getOsSeletivaComandoHidrometroDiametro().iterator();

								while(iterator.hasNext()){

									OsSeletivaComandoHidrometroDiametro osSeletivaComandoHidrometroDiametro = (OsSeletivaComandoHidrometroDiametro) iterator
													.next();

									DadosDoHidrometroHelper dadosDoHidrometroHelper = new DadosDoHidrometroHelper();

									FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();
									filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.ID,
													osSeletivaComandoHidrometroDiametro.getId()));
									filtroHidrometroDiametro.setCampoOrderBy(FiltroHidrometroDiametro.ID);

									Collection colecaoHidrometroDiametro = fachada.pesquisar(filtroHidrometroDiametro,
													HidrometroDiametro.class.getName());

									HidrometroDiametro hidrometroDiametro = (HidrometroDiametro) Util
													.retonarObjetoDeColecao(colecaoHidrometroDiametro);

									if(hidrometroDiametro != null){

										dadosDoHidrometroHelper.setIdHidrometroDiametro(hidrometroDiametro.getId());
										dadosDoHidrometroHelper.setDescricaoHidrometroDiametro(hidrometroDiametro.getDescricao());
									}

									FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();
									filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.ID,
													osSeletivaComandoHidrometroDiametro.getHidrometroCapacidade().getId()));

									Collection colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade,
													HidrometroCapacidade.class.getName());

									HidrometroCapacidade hidrometroCapacidade = (HidrometroCapacidade) Util
													.retonarObjetoDeColecao(colecaoHidrometroCapacidade);

									if(hidrometroCapacidade != null){

										dadosDoHidrometroHelper.setIdHidrometroCapacidade(hidrometroCapacidade.getId());
										dadosDoHidrometroHelper.setDescricaoHidrometroCapacidade(hidrometroCapacidade.getDescricao());

									}

									if(osSeletivaComandoHidrometroDiametro.getReferenciaInicialInstalacaoHidrometro() != null){

										dadosDoHidrometroHelper.setIntervaloInstalacaoInicial(osSeletivaComandoHidrometroDiametro
														.getReferenciaInicialInstalacaoHidrometro().toString());

									}
									if(osSeletivaComandoHidrometroDiametro.getReferenciaFinalInstalacaoHidrometro() != null){

										dadosDoHidrometroHelper.setIntervaloInstalacaoFinal(osSeletivaComandoHidrometroDiametro
														.getReferenciaFinalInstalacaoHidrometro().toString());
									}
									if(osSeletivaComandoHidrometroDiametro.getLeituraAcumuladaHidrometro() != null){

										dadosDoHidrometroHelper.setNumeroLeituraAcumulada(osSeletivaComandoHidrometroDiametro
														.getLeituraAcumuladaHidrometro());

									}

									if(dadosDoHidrometroHelper != null){

										relacaoDadosHidrometroHelper.add(dadosDoHidrometroHelper);

									}
								}

								consultarComandoOsSeletivaActionForm.setColecaoDadosDoHidrometro(relacaoDadosHidrometroHelper);

							}
						}
					}

					if(osseletivaComando.getOrdemServico() != null){

						Iterator iterator = osseletivaComando.getOrdemServico().iterator();

						while(iterator.hasNext()){

							OrdemServico ordemServico = (OrdemServico) iterator.next();

							DadosOrdemServicoHelper dadosOrdemServicoHelper = new DadosOrdemServicoHelper();

							if(ordemServico.getId() != null){

								dadosOrdemServicoHelper.setIdOrdemServico(ordemServico.getId());
								ObterDescricaoSituacaoOSHelper descricaoSituacao = fachada.obterDescricaoSituacaoOS(ordemServico.getId());
								dadosOrdemServicoHelper.setDescricaoAbreviada(descricaoSituacao.getDescricaoAbreviadaSituacao());

							}
							if(ordemServico.getServicoTipo() != null){

								FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
								filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO);
								filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL);
								filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOCALIDADE);
								filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_SETOR);
								filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_QUADRA);
								filtroOrdemServico
												.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
								filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, ordemServico.getId()));
								filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.ORDEMSERVICOUNIDADES);
								Collection colecaoOrdemServico = fachada.pesquisar(filtroOrdemServico, OrdemServico.class.getName());

								OrdemServico ordem = (OrdemServico) Util.retonarObjetoDeColecao(colecaoOrdemServico);

								dadosOrdemServicoHelper.setIdServicoTipo(ordem.getServicoTipo().getId());
								dadosOrdemServicoHelper.setDescricaoServicoTipo(ordem.getServicoTipo().getDescricao());

								if(ordem.getImovel() != null){

									dadosOrdemServicoHelper.setIdImovel(ordem.getImovel().getId());

									if(ordem.getImovel().getLocalidade() != null){

										Localidade localidade = ordem.getImovel().getLocalidade();

										dadosOrdemServicoHelper.setIdLocalidade(localidade.getId());
										dadosOrdemServicoHelper.setDescricaoLocalidade(localidade.getDescricao());

									}
									if(ordem.getImovel().getSetorComercial() != null){

										SetorComercial setorComercial = ordem.getImovel().getSetorComercial();

										dadosOrdemServicoHelper.setCodigoSetor(setorComercial.getCodigo());
										dadosOrdemServicoHelper.setDescricaoSetor(setorComercial.getDescricao());
									}

									if(ordem.getImovel().getQuadra() != null){

										Quadra quadra = ordem.getImovel().getQuadra();

										dadosOrdemServicoHelper.setNumeroQuadra(quadra.getNumeroQuadra());

									}
								}

								if(ordem.getDataGeracao() != null){

									dadosOrdemServicoHelper.setDataGeracao(ordem.getDataGeracao().toString());

								}
								if(ordem.getOrdemServicoUnidades() != null){

									Iterator iteratorr = ordem.getOrdemServicoUnidades().iterator();

									while(iteratorr.hasNext()){

										OrdemServicoUnidade ordemServicoUnidade = (OrdemServicoUnidade) iteratorr.next();

										FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
										filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID,
														ordemServicoUnidade.getUnidadeOrganizacional().getId()));

										Collection colecaoUnidade = fachada.pesquisar(filtroUnidadeOrganizacional,
														UnidadeOrganizacional.class.getName());

										UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
														.retonarObjetoDeColecao(colecaoUnidade);

										if(unidadeOrganizacional != null){

											if(ordemServicoUnidade != null
															&& ordemServicoUnidade.getAtendimentoRelacaoTipo().equals(
																			AtendimentoRelacaoTipo.ENCERRAR)){

												dadosOrdemServicoHelper.setIdUnidadeEncerramento(unidadeOrganizacional.getId());
												dadosOrdemServicoHelper.setDescricaoUnidadeEncerramento(unidadeOrganizacional
																.getDescricao());

											}else{

												dadosOrdemServicoHelper.setIdUnidadeGeracao(unidadeOrganizacional.getId());
												dadosOrdemServicoHelper.setDescricaoUnidadeGeracao(unidadeOrganizacional.getDescricao());

											}
										}
									}
								}

								if(ordemServico.getDataEncerramento() != null){

									dadosOrdemServicoHelper.setDataEncerramento(ordemServico.getDataEncerramento().toString());

								}

								if(ordem.getAtendimentoMotivoEncerramento() != null){

									dadosOrdemServicoHelper.setIdMotivoEncerramento(ordem.getAtendimentoMotivoEncerramento().getId());
									dadosOrdemServicoHelper.setMotivoEncerramento(ordem.getAtendimentoMotivoEncerramento().getDescricao());

								}

							}

							relacaoDadosOrdemServico.add(dadosOrdemServicoHelper);

						}

						consultarComandoOsSeletivaActionForm.setColecaoDadosOrdemServico(relacaoDadosOrdemServico);
						sessao.setAttribute("colecaoDadosOrdemServico", relacaoDadosOrdemServico);
					}
				}
			}
		}

		sessao.setAttribute("consultarComandoOsSeletivaActionForm", consultarComandoOsSeletivaActionForm);

		return retorno;
	}
}
