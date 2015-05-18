/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio Virginio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.FiltroSistemaParametro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.atendimentopublico.ordemservico.ConsumoMedioImovelHelper;
import gcom.gui.atendimentopublico.ordemservico.DadosDoHidrometroHelper;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

/**
 * [UC0713] - Emitir Ordem de Servico Seletiva
 * 
 * @author Ivan S�rgio
 * @date 06/11/2007
 */
public class RelatorioEmitirOrdemServicoSeletivaSugestao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioEmitirOrdemServicoSeletivaSugestao
	 */
	public RelatorioEmitirOrdemServicoSeletivaSugestao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_SUGESTAO);
	}

	@Deprecated
	public RelatorioEmitirOrdemServicoSeletivaSugestao() {

		super(null, "");
	}

	public boolean existeDados(){

		OrdemServicoSeletivaHelper helper = obterHelper();

		Fachada fachada = Fachada.getInstancia();

		Collection<Integer> colecaoDadosRelatorio = helper.getColecaoImoveis();
		if(colecaoDadosRelatorio == null || colecaoDadosRelatorio.isEmpty()){
			colecaoDadosRelatorio = fachada.filtrarImovelEmissaoOrdensSeletivas(helper);
		}

		return colecaoDadosRelatorio != null && !colecaoDadosRelatorio.isEmpty();
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descri��o sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descri��o sobre o fluxo secund�rio>
	 * <Identificador e nome do fluxo secund�rio>
	 * 
	 * @author Ivan S�rgio
	 * @date 06/11/2007
	 * @return
	 * @throws TarefaException
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		OrdemServicoSeletivaHelper helper = obterHelper();

		Fachada fachada = Fachada.getInstancia();

		Collection<Integer> colecaoDadosRelatorio;
		if(helper.getColecaoImoveis() != null && !helper.getColecaoImoveis().isEmpty()){
			colecaoDadosRelatorio = helper.getColecaoImoveis();
		}else{

			colecaoDadosRelatorio = fachada.filtrarImovelEmissaoOrdensSeletivas(helper);
		}

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		byte[] retorno = null;

		// dados para o relatorio
		if(colecaoDadosRelatorio != null && !colecaoDadosRelatorio.isEmpty()){

			Integer idEmpresa = null;
			Integer idOrdemServico = null;
			Integer idImovel = null;
			Imovel imovel = null;
			Cliente cliente = null;
			ClienteFone clienteFone = null;
			boolean achou = false;
			Collection colecaoEconomiasCategoria = null;
			Categoria entCategoria = null;
			int quantidadeEconomias = 0;
			Hidrometro hidrometro = null;
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;

			StringBuilder documentoTxt = new StringBuilder();

			idEmpresa = Util.converterStringParaInteger(helper.getFirma());

			// Recupera o AnoMesFaturamento de Sistema Parametro
			String anoMesFaturamento = "";
			FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();
			Collection<SistemaParametro> colecaoSistemaParametro = fachada.pesquisar(filtroSistemaParametro,
							SistemaParametro.class.getName());
			Iterator iColecaoSistemaParametro = colecaoSistemaParametro.iterator();
			SistemaParametro sistemaParametro = (SistemaParametro) iColecaoSistemaParametro.next();
			anoMesFaturamento = sistemaParametro.getAnoMesFaturamento().toString();

			// Imprimir pagina com os parametros informados e a quantidade de imoveis selecionados.
			Iterator<Integer> iColecaoDadosRelatorio = colecaoDadosRelatorio.iterator();

			FiltroUsuario filtro = new FiltroUsuario();
			filtro.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, helper.getIdUsuario()));
			Collection<Usuario> colecaoUsuario = fachada.pesquisar(filtro, Usuario.class.getName());
			Usuario usuario;
			if(colecaoUsuario == null || colecaoUsuario.isEmpty()){
				throw new IllegalStateException("pesquisa.usuario.inexistente");
			}

			usuario = colecaoUsuario.iterator().next();

			while(iColecaoDadosRelatorio.hasNext()){
				idImovel = iColecaoDadosRelatorio.next();

				if(!ServicoTipo.INSTALACAO_HIDROMETRO.isEmpty() && !(ServicoTipo.INSTALACAO_HIDROMETRO == null)){
					// Aten��o! Implementado em caso de urg�ncia para homologa��o DESO.
					// O sistema n�o deve emitir OS de Instala��o de Hidr�metros para Im�veis
					// Medidos
					// (que j� tenham hidr�metro instalado).
					// Se n�o existe HIDI_ID em ligacao de �gua E LAST_ID = 3 (NORMAL)
					// ......................................................................................................................
					if(Util.converterStringParaInteger(helper.getServicoTipo()).equals(ServicoTipo.INSTALACAO_HIDROMETRO.iterator().next())){

						try{

							LigacaoAguaSituacao ligacaoAguaSituacao = fachada.pesquisarLigacaoAguaSituacao(idImovel);

							if(ligacaoAguaSituacao != null && !ligacaoAguaSituacao.getId().equals(LigacaoAguaSituacao.LIGADO)){
								continue;
							}

							if(fachada.pesquisarIdHidrometroInstalacaoHistorico(idImovel) != null){
								continue;
							}

						}catch(ControladorException e){
							e.printStackTrace();
						}
					}
				}
				// ......................................................................................................................

				imovel = fachada.pesquisarImovel(idImovel);

				String enderecoAbreviado = "";
				enderecoAbreviado = fachada.pesquisarEndereco(idImovel);

				// Preenche o BEAN do Relatorio
				RelatorioEmitirOrdemServicoSeletivaSugestaoBean relatorioEmitirOrdemServicoSeletivaSugestaoBean = new RelatorioEmitirOrdemServicoSeletivaSugestaoBean();
				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setDescricaoTipoServico(helper.getServicoTipoDescricao());
				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setTipoServico(helper.getServicoTipo());
				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setCodigoElo(helper.getElo());
				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setNomeElo(helper.getNomeElo());

				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setIdFirma(helper.getFirma());
				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setNomeFirma(helper.getNomeFirma());
				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setInscricao(imovel.getInscricaoFormatada());
				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setMatriculaImovel("" + imovel.getId());
				relatorioEmitirOrdemServicoSeletivaSugestaoBean.setEndereco(enderecoAbreviado);

				relatorioBeans.add(relatorioEmitirOrdemServicoSeletivaSugestaoBean);
			}

			SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

			// Par�metros do relat�rio
			Map parametros = new HashMap();

			parametros.put("imagem", sistemaParametros.getImagemRelatorio());

			if(relatorioBeans.isEmpty() || relatorioBeans == null){
				throw new TarefaException("atencao.nenhum.imovel.encontrado");
			}

			RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

			// exporta o relat�rio em pdf e retorna o array de bytes
			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_SUGESTAO, parametros, ds,
							helper.getTipoFormatoRelatorio());

			// ------------------------------------
			// Grava o relat�rio no sistema
			try{
				persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_SUGESTAO, idFuncionalidadeIniciada,
								null);
			}catch(ControladorException e){
				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
			}
			// ------------------------------------
			// retorna o relat�rio gerado

		}else{
			throw new TarefaException("atencao.nenhum.imovel.encontrado");
		}

		return retorno;
	}

	private OrdemServicoSeletivaHelper obterHelper(){

		OrdemServicoSeletivaHelper helper = new OrdemServicoSeletivaHelper();

		// Parametros
		helper.setSimulacao((String) getParametro("simulacao"));
		helper.setFirma((String) getParametro("firma"));
		helper.setNomeFirma((String) getParametro("nomeFirma"));
		helper.setQuantidadeMaxima((String) getParametro("quantidadeMaxima"));
		helper.setElo((String) getParametro("elo"));
		helper.setNomeElo((String) getParametro("nomeElo"));
		helper.setInscricaoTipo((String) getParametro("inscricaoTipo"));
		helper.setRota((String[]) getParametro("rota"));
		helper.setServicoTipo((String) getParametro("servicoTipo"));
		helper.setServicoTipoDescricao((String) getParametro("servicoTipoDescricao"));
		helper.setLocalidade((String[]) getParametro("localidade"));
		helper.setBairro((String[]) getParametro("bairro"));
		helper.setLogradouro((String[]) getParametro("logradouro"));
		helper.setSetor((String[]) getParametro("setor"));
		helper.setQuadra((String[]) getParametro("quadra"));
		helper.setFaturamentoGrupo((String) getParametro("faturamentoGrupo"));
		helper.setGerenciaRegional((String) getParametro("gerenciaRegional"));
		helper.setLote((String[]) getParametro("lote"));

		// Caracteristicas
		helper.setPerfilImovel((String[]) getParametro("perfilImovel"));
		helper.setCategoria((String[]) getParametro("categoria"));
		helper.setSubCategoria((String[]) getParametro("subCategoria"));
		helper.setIntervaloQuantidadeEconomiasInicial((String) getParametro("intervaloQuantidadeEconomiasInicial"));
		helper.setIntervaloQuantidadeEconomiasFinal((String) getParametro("intervaloQuantidadeEconomiasFinal"));
		helper.setIntervaloQuantidadeDocumentosInicial((String) getParametro("intervaloQuantidadeDocumentosInicial"));
		helper.setIntervaloQuantidadeDocumentosFinal((String) getParametro("intervaloQuantidadeDocumentosFinal"));
		helper.setIntervaloNumeroMoradoresInicial((String) getParametro("intervaloNumeroMoradoresInicial"));
		helper.setIntervaloNumeroMoradoresFinal((String) getParametro("intervaloNumeroMoradoresFinal"));
		helper.setIntervaloAreaConstruidaInicial((String) getParametro("intervaloAreaConstruidaInicial"));
		helper.setIntervaloAreaConstruidaFinal((String) getParametro("intervaloAreaConstruidaFinal"));
		helper.setIntervaloAreaConstruidaPredefinida((String) getParametro("intervaloAreaConstruidaPredefinida"));
		helper.setImovelCondominio((String) getParametro("imovelCondominio"));
		helper.setConsumoPorEconomia((String) getParametro("consumoPorEconomia"));
		helper.setTipoMedicao((String) getParametro("tipoMedicao"));
		helper.setLigacaoAguaSituacao((String[]) getParametro("ligacaoAguaSituacao"));
		helper.setLigacaoEsgotoSituacao((String[]) getParametro("ligacaoEsgotoSituacao"));
		helper.setIntervaloDataCorteInicial((String) getParametro("intervaloDataCorteInicial"));
		helper.setIntervaloDataCorteFinal((String) getParametro("intervaloDataCorteFinal"));
		helper.setDataCorteInicial((String) getParametro("dataCorteInicial"));
		helper.setDataCorteFinal((String) getParametro("dataCorteFinal"));
		helper.setIntervaloNumeroPontosUtilizacaoInicial((String) getParametro("intervaloNumeroPontosUtilizacaoInicial"));
		helper.setIntervaloNumeroPontosUtilizacaoFinal((String) getParametro("intervaloNumeroPontosUtilizacaoFinal"));
		helper.setIntervaloNumeroConsumoMesInicial((String) getParametro("intervaloNumeroConsumoMesInicial"));
		helper.setIntervaloNumeroConsumoMesFinal((String) getParametro("intervaloNumeroConsumoMesFinal"));
		helper.setColecaoConsumoMedioImovel((Collection<ConsumoMedioImovelHelper>) getParametro("colecaoConsumoMedioImovel"));
		helper.setIntervaloQuantidadeContasVencidasInicial((String) getParametro("intervaloQuantidadeContasVencidasInicial"));
		helper.setIntervaloQuantidadeContasVencidasFinal((String) getParametro("intervaloQuantidadeContasVencidasFinal"));
		helper.setValorTotalDebitoVencido((String) getParametro("valorTotalDebitoVencido"));
		helper.setIndicadorPreFiscalizados((String) getParametro("indicadorPreFiscalizados"));

		// Hidrometro
		helper.setMarca((String[]) getParametro("marca"));
		helper.setAnormalidadeHidrometro((String[]) getParametro("anormalidadeHidrometro"));
		helper.setNumeroOcorrenciasConsecutivas((String) getParametro("numeroOcorrenciasConsecutivas"));
		helper.setReferenciaUltimaAfericaoAnterior((String) getParametro("referenciaUltimaAfericaoAnterior"));
		helper.setIdUsuario((String) getParametro("idUsuario"));
		helper.setTipoFormatoRelatorio((Integer) getParametro("tipoFormatoRelatorio"));
		helper.setHidrometroClasseMetrologica((String[]) getParametro("hidrometroClasseMetrologica"));
		helper.setHidrometroProtecao((String[]) getParametro("hidrometroProtecao"));
		helper.setHidrometroLocalInstalacao((String[]) getParametro("hidrometroLocalInstalacao"));
		helper.setColecaoDadosDoHidrometro((Collection<DadosDoHidrometroHelper>) getParametro("colecaoDadosDoHidrometro"));

		// Arquivo
		helper.setColecaoImoveis((Collection<Integer>) getParametro("colecaoImoveis"));
		return helper;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 1;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEmitirOrdemServicoSeletivaSugestao", this);
	}
}
