/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio Virginio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.relatorio.atendimentopublico;

import gcom.batch.Relatorio;
import gcom.fachada.Fachada;
import gcom.gui.atendimentopublico.ordemservico.ConsumoMedioImovelHelper;
import gcom.gui.atendimentopublico.ordemservico.DadosDoHidrometroHelper;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;

/**
 * [UC0713] - Emitir Ordem de Servico Seletiva
 * 
 * @author Ivan Sérgio
 * @date 06/11/2007
 */
public class RelatorioEmitirOrdemServicoSeletiva
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static Collection colecaoDadosRelatorio = new ArrayList();

	private Integer[] idsOs;

	/**
	 * Construtor da classe RelatorioEmitirOrdemServicoSeletiva
	 */
	public RelatorioEmitirOrdemServicoSeletiva(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA);
	}

	@Deprecated
	public RelatorioEmitirOrdemServicoSeletiva() {

		super(null, "");
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Ivan Sérgio
	 * @date 06/11/2007
	 * @return
	 * @throws TarefaException
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		OrdemServicoSeletivaHelper helper = this.obterHelper();

		Fachada fachada = Fachada.getInstancia();
		byte[] retornoRelatorioOrdemServico = null;

		try{
			synchronized(colecaoDadosRelatorio){
				// ALERTA: Esse relatório não pode ser executado em paralelo, pois as
				// ordem de serviço estavam sendo geradas em duplicidade

				// Esta consulta faz o order by no HBM mais no ponto de criar o arquivo, executa o sort(); 
				colecaoDadosRelatorio.addAll(fachada.filtrarImovelEmissaoOrdensSeletivas(helper));

				// Gera o relatório de OS para exibição
				retornoRelatorioOrdemServico = fachada.gerarOrdensServicoSeletiva(colecaoDadosRelatorio, helper, idFuncionalidadeIniciada,
								this.nomeRelatorio);

				colecaoDadosRelatorio.clear();
			}

			// Persistir relatório gerado.
			persistirRelatorioConcluido(retornoRelatorioOrdemServico, Relatorio.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA,
							this.getIdFuncionalidadeIniciada(), null);
		}catch(ControladorException e){
			throw new TarefaException(e.getMessage(), e);
		}

		return retornoRelatorioOrdemServico;
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

		// Comando Ordem Servico Seletiva
		helper.setIdComandoOsSeletiva(Util.converterStringParaInteger((String) getParametro("idComandoOsSeletiva")));

		// Arquivo
		helper.setColecaoImoveis((Collection<Integer>) getParametro("colecaoImoveis"));
		return helper;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer retorno = 0;

		OrdemServicoSeletivaHelper helper = this.obterHelper();

		Fachada fachada = Fachada.getInstancia();
		Collection<Integer> colecao = fachada.filtrarImovelEmissaoOrdensSeletivas(helper);

		if(!Util.isVazioOrNulo(colecao)){
			retorno = colecao.size();
		}

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEmitirOrdemServicoSeletiva", this);
	}

	public Integer[] getIdsOs(){

		return idsOs;
	}

	public void setIdsOs(Integer[] idsOs){

		this.idsOs = idsOs;
	}

}
