
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.IRepositorioAtendimentoPublico;
import gcom.atendimentopublico.RepositorioAtendimentoPublicoHBM;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.IRepositorioOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.RepositorioOrdemServicoHBM;
import gcom.atendimentopublico.registroatendimento.IRepositorioRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.RepositorioRegistroAtendimentoHBM;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.FiltroImovelSubCategoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.imovel.RepositorioImovelHBM;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.Quadra;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andre Nishimura
 * @date 28 de Dezembro de 2009
 */
public class GeradorDadosRelatorioSubstituicaoHidrometro
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioRegistroAtendimento repositorioRegistroAtendimento = null;

	private IRepositorioImovel repositorioImovel = null;

	private IRepositorioAtendimentoPublico repositorioAtendimentoPublico = null;

	private IRepositorioOrdemServico repositorioOrdemServico = null;

	public GeradorDadosRelatorioSubstituicaoHidrometro() {

		super();
		repositorioRegistroAtendimento = RepositorioRegistroAtendimentoHBM.getInstancia();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
		repositorioAtendimentoPublico = RepositorioAtendimentoPublicoHBM.getInstancia();
		repositorioOrdemServico = RepositorioOrdemServicoHBM.getInstancia();

	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = new ArrayList<DadosRelatorioOrdemServico>();
		DadosRelatorioOrdemServico dadosRelatorio = new DadosRelatorioOrdemServico();

		dadosRelatorio.setNumeroOS(Util.adicionarZerosEsqueda(ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO, os
						.getId().toString()));

		try{

			// Tipo Relação Cliente Imóvel
			String tipoRelacao = null;
			if(os != null && os.getServicoTipo() != null && os.getServicoTipo().getOrdemServicoLayout() != null){

				tipoRelacao = getControladorOrdemServico().recuperaRelacaoOSClienteImovel(
								os.getServicoTipo().getOrdemServicoLayout().getId());
			}

			// Proprietário
			if(os.getImovel() != null){
				Collection<ClienteImovel> colecaoClienteImovel = repositorioImovel.pesquisarClientesImovel(os.getImovel().getId());
				if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

					Object[] clienteImovel = (Object[]) Util.retonarObjetoDeColecao(colecaoClienteImovel);
					if(clienteImovel != null){
						dadosRelatorio.setProprietario(String.valueOf(clienteImovel[1]));
						dadosRelatorio.setTipoRelacao(String.valueOf(clienteImovel[2]));
					}

					for(Object objeto : colecaoClienteImovel){

						Object[] clienteImovelAux = (Object[]) objeto;

						if(clienteImovelAux[2].equals(tipoRelacao)){
							dadosRelatorio.setProprietario(String.valueOf(clienteImovelAux[1]));
							dadosRelatorio.setTipoRelacao(String.valueOf(clienteImovelAux[2]));
						}
					}

				}
			}else if(os.getRegistroAtendimento() != null){
				Collection<RegistroAtendimentoSolicitante> colecaoRaSolicitante = getControladorRegistroAtendimento().obterRASolicitante(
								os.getRegistroAtendimento().getId());
				if(colecaoRaSolicitante != null && !colecaoRaSolicitante.isEmpty()){
					RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) Util
									.retonarObjetoDeColecao(colecaoRaSolicitante);
					dadosRelatorio.setProprietario(getControladorRegistroAtendimento().obterNomeSolicitanteRA(
									registroAtendimentoSolicitante.getID()));
				}
			}

			// campos do imovel
			if(os.getImovel() != null){
				Imovel imovel = repositorioImovel.pesquisarImovel(os.getImovel().getId());

				// matricula
				dadosRelatorio.setIdImovel(imovel.getIdParametrizado());

				// setor hidraulico
				if(imovel.getQuadra() != null){
					FiltroQuadra filtroQuadra = new FiltroQuadra();
					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, imovel.getQuadra().getId()));
					filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("distritoOperacional");
					Collection<Quadra> colecaoQuadra = getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

					if(colecaoQuadra != null && !colecaoQuadra.isEmpty()){
						Quadra quadra = colecaoQuadra.iterator().next();
						if(quadra != null && quadra.getDistritoOperacional() != null) dadosRelatorio.setSetorHidraulico(String
										.valueOf(quadra.getDistritoOperacional().getId()));
					}
				}
				// grupo
				Integer grupo = imovel.getRota().getFaturamentoGrupo().getId();
				dadosRelatorio.setGrupo(String.valueOf(grupo));

				// inscricao
				dadosRelatorio.setInscricao(getControladorImovel().pesquisarInscricaoImovel(imovel.getId(), true));

				// situação
				String situacao = "";
				LigacaoAguaSituacao ligacaoAguaSituacao = getControladorImovel().pesquisarLigacaoAguaSituacao(imovel.getId());
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = getControladorImovel().pesquisarLigacaoEsgotoSituacao(imovel.getId());
				if(ligacaoAguaSituacao != null){
					situacao = ligacaoAguaSituacao.getDescricaoAbreviado();
				}
				situacao = situacao + "/";
				if(ligacaoEsgotoSituacao != null){
					situacao = situacao + ligacaoEsgotoSituacao.getDescricaoAbreviado();
				}
				dadosRelatorio.setSituacao(situacao);

				// endereço
				if(imovel.getLogradouroBairro() != null){

					// Logradouro
					if(imovel.getLogradouroBairro().getLogradouro() != null){
						dadosRelatorio.setEndereco(imovel.getLogradouroBairro().getLogradouro().getNome());
					}

					// Bairro
					if(imovel.getLogradouroBairro().getBairro() != null){
						dadosRelatorio.setBairro(imovel.getLogradouroBairro().getBairro().getNome());
					}

					// Complemento
					if(imovel.getComplementoEndereco() != null){
						dadosRelatorio.setComplemento(", " + imovel.getComplementoEndereco());
					}else{
						dadosRelatorio.setComplemento("");
					}
				}
				dadosRelatorio.setNumero(imovel.getNumeroImovel());

				// categoria
				FiltroImovelSubCategoria filtroImovelSubCategoria = new FiltroImovelSubCategoria();
				filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));
				Collection<ImovelSubcategoria> colecaoImovelSubCategoria = getControladorUtil().pesquisar(filtroImovelSubCategoria,
								ImovelSubcategoria.class.getName());

				String categoria = "";
				if(colecaoImovelSubCategoria != null && !colecaoImovelSubCategoria.isEmpty()){
					for(Iterator<ImovelSubcategoria> colecaoIterator = colecaoImovelSubCategoria.iterator(); colecaoIterator.hasNext();){
						ImovelSubcategoria imovelSubcategoria = colecaoIterator.next();
						if(imovelSubcategoria != null && imovelSubcategoria.getComp_id() != null
										&& imovelSubcategoria.getComp_id().getSubcategoria() != null){
							Subcategoria subcategoria = imovelSubcategoria.getComp_id().getSubcategoria();
							FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
							filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, String.valueOf(subcategoria
											.getId())));
							filtroSubCategoria.adicionarCaminhoParaCarregamentoEntidade("categoria");
							Collection<Subcategoria> colecaoSubcategoria = getControladorUtil().pesquisar(filtroSubCategoria,
											Subcategoria.class.getName());

							if(colecaoSubcategoria != null && !colecaoSubcategoria.isEmpty()){
								subcategoria = colecaoSubcategoria.iterator().next();
								if(subcategoria != null){
									if(categoria.equals("")){
										categoria = subcategoria.getCategoria().getDescricaoAbreviada();
									}else{
										categoria = "/" + subcategoria.getCategoria().getDescricaoAbreviada();
									}
								}
							}
						}
					}
				}

				dadosRelatorio.setCategoria(categoria);

				// me
				if(imovel.getNumeroCelpe() != null){
					dadosRelatorio.setMe(imovel.getNumeroCelpe().toString());
				}

				// hidrometro cadastrado
				String numeroHidrometro = "";

				if(imovel != null && imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
								&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro() != null){

					// HIDI_ID da ligação de água
					numeroHidrometro = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro();

				}else if(imovel.getHidrometroInstalacaoHistorico() != null){

					// HIDI_ID do imovel
					numeroHidrometro = imovel.getHidrometroInstalacaoHistorico().getNumeroHidrometro();
				}

				dadosRelatorio.setNumeroHidrometro(numeroHidrometro);

				// codigo leitura
				FiltroMedicaoHistorico filtroMedicaoHistoricoCodigoLeitura = new FiltroMedicaoHistorico();
				if(imovel.getLigacaoAgua() != null){
					filtroMedicaoHistoricoCodigoLeitura.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID,
									imovel.getLigacaoAgua().getId()));
				}else{
					filtroMedicaoHistoricoCodigoLeitura.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.IMOVEL_ID, imovel
									.getId()));
				}
				filtroMedicaoHistoricoCodigoLeitura.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.MEDICAO_TIPO_ID, 1));
				filtroMedicaoHistoricoCodigoLeitura.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeFaturamento");
				filtroMedicaoHistoricoCodigoLeitura.setCampoOrderBy(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO + " desc ");
				Collection colecaoMedicaoHistorico = getControladorUtil().pesquisar(filtroMedicaoHistoricoCodigoLeitura,
								MedicaoHistorico.class.getName());

				if(colecaoMedicaoHistorico != null && !colecaoMedicaoHistorico.isEmpty()){
					Iterator iterator = colecaoMedicaoHistorico.iterator();
					MedicaoHistorico medicaoHistorico = (MedicaoHistorico) iterator.next();
					if(medicaoHistorico != null && medicaoHistorico.getLeituraAnormalidadeFaturamento() != null){
						dadosRelatorio.setCodigoLeitura(String.valueOf(medicaoHistorico.getLeituraAnormalidadeFaturamento().getId()));
					}

					// 3 ultimas referencias medidas

					MedicaoHistorico[] arrayMedicaoHistorico = new MedicaoHistorico[3];
					arrayMedicaoHistorico[0] = medicaoHistorico;
					Integer index = 1;
					while(iterator.hasNext()){
						arrayMedicaoHistorico[index] = (MedicaoHistorico) iterator.next();
						index++;
						if(index == 3){
							break;
						}
					}

					if(arrayMedicaoHistorico[2] != null){
						dadosRelatorio.setMesMedido1("MEDIDO "
										+ Util.retornaDescricaoMes(Integer.valueOf(arrayMedicaoHistorico[2].getMesAno().substring(0, 2)))
														.toUpperCase());
						dadosRelatorio.setQtdMedido1(String.valueOf(arrayMedicaoHistorico[2].getNumeroConsumoMes() == null ? ""
										: arrayMedicaoHistorico[2].getNumeroConsumoMes()));
					}else{
						dadosRelatorio.setMesMedido1("");
						dadosRelatorio.setQtdMedido1("");
					}

					if(arrayMedicaoHistorico[1] != null){
						dadosRelatorio.setMesMedido2("MEDIDO "
										+ Util.retornaDescricaoMes(Integer.valueOf(arrayMedicaoHistorico[1].getMesAno().substring(0, 2)))
														.toUpperCase());
						dadosRelatorio.setQtdMedido2(String.valueOf(arrayMedicaoHistorico[1].getNumeroConsumoMes() == null ? ""
										: arrayMedicaoHistorico[1].getNumeroConsumoMes()));
					}else{
						dadosRelatorio.setMesMedido2("");
						dadosRelatorio.setQtdMedido2("");
					}

					if(arrayMedicaoHistorico[0] != null){
						dadosRelatorio.setMesMedido3("MEDIDO "
										+ Util.retornaDescricaoMes(Integer.valueOf(arrayMedicaoHistorico[0].getMesAno().substring(0, 2)))
														.toUpperCase());
						dadosRelatorio.setQtdMedido3(String.valueOf(arrayMedicaoHistorico[0].getNumeroConsumoMes() == null ? ""
										: arrayMedicaoHistorico[0].getNumeroConsumoMes()));
						// media medida
						dadosRelatorio.setMediaMedido(String.valueOf(arrayMedicaoHistorico[0].getConsumoMedioHidrometro()));
					}else{
						dadosRelatorio.setMesMedido3("");
						dadosRelatorio.setQtdMedido3("");
						dadosRelatorio.setMediaMedido("");
					}

				}

				FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();

				filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, imovel.getId()));
				filtroConsumoHistorico.setCampoOrderBy(FiltroConsumoHistorico.ANO_MES_FATURAMENTO + " desc");
				Collection colecaoConsumoHistorico = getControladorUtil().pesquisar(filtroConsumoHistorico,
								ConsumoHistorico.class.getName());

				if(colecaoConsumoHistorico != null && !colecaoConsumoHistorico.isEmpty()){
					Iterator iterator = colecaoConsumoHistorico.iterator();
					ConsumoHistorico consumoHistorico = (ConsumoHistorico) iterator.next();
					// media faturada
					if(consumoHistorico != null){
						dadosRelatorio.setMediaFaturado(String.valueOf(consumoHistorico.getConsumoMedio()));
					}

					// 3 ultimas referencias faturadas
					ConsumoHistorico[] arrayConsumoHistorico = new ConsumoHistorico[3];
					arrayConsumoHistorico[0] = consumoHistorico;
					Integer index = 1;
					while(iterator.hasNext()){
						arrayConsumoHistorico[index] = (ConsumoHistorico) iterator.next();
						index++;
						if(index == 3){
							break;
						}
					}

					if(arrayConsumoHistorico[2] != null){
						dadosRelatorio.setMesFaturamento1("FATURADO "
										+ Util.retornaDescricaoMes(Integer.valueOf(arrayConsumoHistorico[2].getMesAno().substring(0, 2)))
														.toUpperCase());
						dadosRelatorio.setQtdFaturado1(String.valueOf(arrayConsumoHistorico[2].getNumeroConsumoFaturadoMes() == null ? ""
										: arrayConsumoHistorico[2].getNumeroConsumoFaturadoMes()));
					}else{
						dadosRelatorio.setMesFaturamento1("");
						dadosRelatorio.setQtdFaturado1("");
					}

					if(arrayConsumoHistorico[1] != null){
						dadosRelatorio.setMesFaturamento2("FATURADO "
										+ Util.retornaDescricaoMes(Integer.valueOf(arrayConsumoHistorico[1].getMesAno().substring(0, 2)))
														.toUpperCase());
						dadosRelatorio.setQtdFaturado2(String.valueOf(arrayConsumoHistorico[1].getNumeroConsumoFaturadoMes() == null ? ""
										: arrayConsumoHistorico[1].getNumeroConsumoFaturadoMes()));
					}else{
						dadosRelatorio.setMesFaturamento2("");
						dadosRelatorio.setQtdFaturado2("");
					}

					if(arrayConsumoHistorico[0] != null){
						dadosRelatorio.setMesFaturamento3("FATURADO "
										+ Util.retornaDescricaoMes(Integer.valueOf(arrayConsumoHistorico[0].getMesAno().substring(0, 2)))
														.toUpperCase());
						dadosRelatorio.setQtdFaturado3(String.valueOf(arrayConsumoHistorico[0].getNumeroConsumoFaturadoMes() == null ? ""
										: arrayConsumoHistorico[0].getNumeroConsumoFaturadoMes()));
					}else{
						dadosRelatorio.setMesFaturamento3("");
						dadosRelatorio.setQtdFaturado3("");
					}

				}
			}
		}catch(ErroRepositorioException e){
			throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
		}catch(ControladorException e){
			throw new GeradorRelatorioOrdemServicoException("erro.sistema", e);
		}

		dados.add(dadosRelatorio);

		return dados;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		// TODO Auto-generated method stub
		return null;
	}

}
