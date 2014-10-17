
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
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
import gcom.faturamento.IRepositorioFaturamento;
import gcom.faturamento.RepositorioFaturamentoHBM;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.procenge.comum.exception.PCGException;

public class GeradorDadosRelatorioVerificacaoLigacaoAguaCCI
				extends GeradorDadosRelatorioOrdemServico {

	private IRepositorioImovel repositorioImovel = null;

	private IRepositorioFaturamento repositorioFaturamento = null;

	public GeradorDadosRelatorioVerificacaoLigacaoAguaCCI() {

		super();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();

	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = new ArrayList<DadosRelatorioOrdemServico>();
		DadosRelatorioOrdemServico dadosRelatorio = new DadosRelatorioOrdemServico();

		try{
			// numero OS
			dadosRelatorio.setNumeroOS(Util.adicionarZerosEsqueda(ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO,
							os.getId().toString()));

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
			}else{
				// grupo
				dadosRelatorio.setGrupo("");
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
						dadosRelatorio.setComplemento(imovel.getComplementoEndereco());
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
					}else{
						dadosRelatorio.setCodigoLeitura("");
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

						if(arrayMedicaoHistorico[2].getMesAno() != null){
							dadosRelatorio.setMesMedido1(Util.retornaDescricaoMes(
											Integer.valueOf(arrayMedicaoHistorico[2].getMesAno().substring(0, 2))).toUpperCase());
						}else{
							dadosRelatorio.setMesMedido1("");
						}
						if(arrayMedicaoHistorico[2].getNumeroConsumoMes() != null){
							dadosRelatorio.setQtdMedido1(String.valueOf(arrayMedicaoHistorico[2].getNumeroConsumoMes()));
						}else{
							dadosRelatorio.setQtdMedido1("");
						}

					}else{
						dadosRelatorio.setMesMedido1("");
						dadosRelatorio.setQtdMedido1("");
					}

					if(arrayMedicaoHistorico[1] != null){

						if(arrayMedicaoHistorico[1].getMesAno() != null){
							dadosRelatorio.setMesMedido2(Util.retornaDescricaoMes(
											Integer.valueOf(arrayMedicaoHistorico[1].getMesAno().substring(0, 2))).toUpperCase());
						}else{
							dadosRelatorio.setMesMedido2("");
						}

						if(arrayMedicaoHistorico[1].getNumeroConsumoMes() != null){
							dadosRelatorio.setQtdMedido2(String.valueOf(arrayMedicaoHistorico[1].getNumeroConsumoMes()));
						}else{
							dadosRelatorio.setQtdMedido2("");
						}

					}else{
						dadosRelatorio.setMesMedido2("");
						dadosRelatorio.setQtdMedido2("");
					}

					if(arrayMedicaoHistorico[0] != null){

						if(arrayMedicaoHistorico[0].getMesAno() != null){
							dadosRelatorio.setMesMedido3(Util.retornaDescricaoMes(
											Integer.valueOf(arrayMedicaoHistorico[0].getMesAno().substring(0, 2))).toUpperCase());
						}else{
							dadosRelatorio.setMesMedido3("");
						}

						if(arrayMedicaoHistorico[0].getNumeroConsumoMes() != null){
							dadosRelatorio.setQtdMedido3(String.valueOf(arrayMedicaoHistorico[0].getNumeroConsumoMes()));
						}else{
							dadosRelatorio.setQtdMedido3("");
						}

						if(arrayMedicaoHistorico[0].getConsumoMedioHidrometro() != null){
							// media medida
							dadosRelatorio.setMediaMedido(String.valueOf(arrayMedicaoHistorico[0].getConsumoMedioHidrometro()));
						}else{
							dadosRelatorio.setMediaMedido("");
						}

					}else{
						dadosRelatorio.setMesMedido3("");
						dadosRelatorio.setQtdMedido3("");
						dadosRelatorio.setMediaMedido("");
					}

				}

				// -----------------------------------------------------------------------
				// media faturada
				dadosRelatorio.setMediaFaturado("");
				// -----------------------------------------------------------------------

				FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
				filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, imovel.getId()));
				filtroConsumoHistorico.setCampoOrderBy(FiltroConsumoHistorico.ANO_MES_FATURAMENTO + " desc");
				Collection colecaoConsumoHistorico = getControladorUtil().pesquisar(filtroConsumoHistorico,
								ConsumoHistorico.class.getName());

				if(colecaoConsumoHistorico != null && !colecaoConsumoHistorico.isEmpty()){
					Iterator iterator = colecaoConsumoHistorico.iterator();
					ConsumoHistorico consumoHistorico = (ConsumoHistorico) iterator.next();
					// // media faturada
					// if (consumoHistorico != null && consumoHistorico.getConsumoMedio()!= null) {
					// dadosRelatorio.setMediaFaturado(String.valueOf(consumoHistorico.getConsumoMedio()));
					// }
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

						if(arrayConsumoHistorico[2].getMesAno() != null){
							dadosRelatorio.setMesFaturamento1(Util.retornaDescricaoMes(
											Integer.valueOf(arrayConsumoHistorico[2].getMesAno().substring(0, 2))).toUpperCase());
						}else{
							dadosRelatorio.setMesFaturamento1("");
						}

						if(arrayConsumoHistorico[2].getNumeroConsumoFaturadoMes() != null){
							dadosRelatorio.setQtdFaturado1(String.valueOf(arrayConsumoHistorico[2].getNumeroConsumoFaturadoMes()));
						}else{
							dadosRelatorio.setQtdFaturado1("");
						}

					}else{
						dadosRelatorio.setMesFaturamento1("");
						dadosRelatorio.setQtdFaturado1("");
					}

					if(arrayConsumoHistorico[1] != null){

						if(arrayConsumoHistorico[1].getMesAno() != null){
							dadosRelatorio.setMesFaturamento2(Util.retornaDescricaoMes(
											Integer.valueOf(arrayConsumoHistorico[1].getMesAno().substring(0, 2))).toUpperCase());
						}else{
							dadosRelatorio.setMesFaturamento2("");
						}

						if(arrayConsumoHistorico[1].getNumeroConsumoFaturadoMes() != null){
							dadosRelatorio.setQtdFaturado2(String.valueOf(arrayConsumoHistorico[1].getNumeroConsumoFaturadoMes()));
						}else{
							dadosRelatorio.setQtdFaturado2("");
						}

					}else{
						dadosRelatorio.setMesFaturamento2("");
						dadosRelatorio.setQtdFaturado2("");
					}

					if(arrayConsumoHistorico[0] != null){

						if(arrayConsumoHistorico[0].getMesAno() != null){
							dadosRelatorio.setMesFaturamento3(Util.retornaDescricaoMes(
											Integer.valueOf(arrayConsumoHistorico[0].getMesAno().substring(0, 2))).toUpperCase());
						}else{
							dadosRelatorio.setMesFaturamento3("");
						}

						if(arrayConsumoHistorico[0].getNumeroConsumoFaturadoMes() != null){
							dadosRelatorio.setQtdFaturado3(String.valueOf(arrayConsumoHistorico[0].getNumeroConsumoFaturadoMes()));
						}else{
							dadosRelatorio.setQtdFaturado3("");
						}

						if(arrayConsumoHistorico[0].getConsumoMedio() != null){
							dadosRelatorio.setMediaFaturado(String.valueOf(consumoHistorico.getConsumoMedio()));
						}else{
							dadosRelatorio.setMediaFaturado("");
						}

					}else{
						dadosRelatorio.setMesFaturamento3("");
						dadosRelatorio.setQtdFaturado3("");
						dadosRelatorio.setMediaFaturado("");
					}
				}
				// Total de faturas em debito
				Object[] quantidadeFaturasEValorDebitos = repositorioFaturamento.listarSomatorioEValorFaturasDebito(imovel.getId(), null);

				dadosRelatorio.setQuantidadeFaturasDebito("");
				if(quantidadeFaturasEValorDebitos[0] != null){
					dadosRelatorio.setQuantidadeFaturasDebito(quantidadeFaturasEValorDebitos[0].toString());
				}

				// Valor total das faturas em debito ( - valorCreditos - ValorImpostos )
				dadosRelatorio.setDebitosValorTotal("");
				if(quantidadeFaturasEValorDebitos[1] != null){

					try{
						String valor = br.com.procenge.util.Util.converterCampoValorParaString("",
										(BigDecimal) quantidadeFaturasEValorDebitos[1]);
						dadosRelatorio.setDebitosValorTotal(valor);
					}catch(PCGException e){

					}
				}

			}else if(os.getRegistroAtendimento() != null){

				FiltroRegistroAtendimento filtro = new FiltroRegistroAtendimento();
				filtro.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, os.getRegistroAtendimento().getId()));
				filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.LOGRADOURO_BAIRRO);
				filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.LOGRADOURO_BAIRRO_BAIRRO);

				Collection<RegistroAtendimento> colecao = this.getControladorUtil().pesquisar(filtro, RegistroAtendimento.class.getName());
				RegistroAtendimento ra = (RegistroAtendimento) Util.retonarObjetoDeColecao(colecao);

				// Bairro
				if(ra.getLogradouroBairro() != null && ra.getLogradouroBairro().getBairro() != null){
					dadosRelatorio.setBairro(ra.getLogradouroBairro().getBairro().getNome());
				}

				// Endereço
				dadosRelatorio.setEndereco(this.getControladorEndereco().pesquisarEnderecoRegistroAtendimentoFormatado(
								os.getRegistroAtendimento().getId()));

				// Complemento
				dadosRelatorio.setComplemento("");

				// Número
				dadosRelatorio.setNumero("");

				// Grupo
				dadosRelatorio.setGrupo("");
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