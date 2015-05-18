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

package gcom.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.bean.DadosAtualizacaoOSParaInstalacaoHidrometroHelper;
import gcom.atendimentopublico.ordemservico.bean.FiltrarOrdemServicoHelper;
import gcom.atendimentopublico.ordemservico.bean.OrdemServicoSeletivaComandoHelper;
import gcom.atendimentopublico.ordemservico.bean.PesquisarOrdemServicoHelper;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.PavimentoCalcada;
import gcom.cadastro.imovel.PavimentoRua;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeTipo;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.InfracaoPerfil;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.atendimentopublico.ordemservico.ConsumoMedioImovelHelper;
import gcom.gui.atendimentopublico.ordemservico.DadosDoHidrometroHelper;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.gui.atendimentopublico.ordemservico.RoteiroOSDadosProgramacaoHelper;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.operacional.DistritoOperacional;
import gcom.relatorio.atendimentopublico.FiltrarRelatorioResumoOrdensServicoPendentesHelper;
import gcom.relatorio.atendimentopublico.RelatorioResumoOrdensServicoEncerradasPendentes;
import gcom.relatorio.atendimentopublico.RelatorioResumoOrdensServicoPendentesHelper;
import gcom.util.*;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class RepositorioOrdemServicoHBM
				implements IRepositorioOrdemServico {

	private static IRepositorioOrdemServico instancia;

	/**
	 * Construtor da classe RepositorioAcessoHBM
	 */

	private RepositorioOrdemServicoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioOrdemServico getInstancia(){

		if(instancia == null){
			instancia = new RepositorioOrdemServicoHBM();
		}

		return instancia;
	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB001] Selecionar Ordem de Servico por Situação [SB002] Selecionar Ordem
	 * de Servico por Situação da Programação [SB003] Selecionar Ordem de
	 * Servico por Matricula do Imovel [SB004] Selecionar Ordem de Servico por
	 * Codigo do Cliente [SB005] Selecionar Ordem de Servico por Unidade
	 * Superior [SB006] Selecionar Ordem de Servico por Município [SB007]
	 * Selecionar Ordem de Servico por Bairro [SB008] Selecionar Ordem de
	 * Servico por Bairro Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdemServico(PesquisarOrdemServicoHelper filtro, Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Collection<OrdemServico> retorno = null;
		Collection<Object[]> retornoConsulta = null;

		Integer numeroRA = filtro.getNumeroRA();
		Integer numeroOS = filtro.getNumeroOS();
		Integer documentoCobranca = filtro.getDocumentoCobranca();
		short situacaoOrdemServico = filtro.getSituacaoOrdemServico();
		short situacaoProgramacao = filtro.getSituacaoProgramacao();
		Integer[] tipoServicos = filtro.getTipoServicos();

		Integer unidadeGeracao = filtro.getUnidadeGeracao();
		Integer unidadeAtual = filtro.getUnidadeAtual();
		Integer unidadeSuperior = filtro.getUnidadeSuperior();
		Integer diasAtraso = filtro.getDiasAtraso();
		Integer equipe = filtro.getEquipe();
		Short programado = filtro.getProgramado();

		Integer matriculaImovel = filtro.getMatriculaImovel();
		Set colecaoIdsOS = filtro.getColecaoIdsOS();

		Date dataAtendimentoInicial = filtro.getDataAtendimentoInicial();
		Date dataAtendimentoFinal = filtro.getDataAtendimentoFinal();

		Date dataGeracaoInicial = filtro.getDataGeracaoInicial();
		Date dataGeracaoFinal = filtro.getDataGeracaoFinal();

		Date dataProgramacaoInicial = filtro.getDataProgramacaoInicial();
		Date dataProgramacaoFinal = filtro.getDataProgramacaoFinal();

		Date dataEncerramentoInicial = filtro.getDataEncerramentoInicial();
		Date dataEncerramentoFinal = filtro.getDataEncerramentoFinal();

		Date dataExecucaoInicial = filtro.getDataExecucaoInicial();
		Date dataExecucaoFinal = filtro.getDataExecucaoFinal();

		Integer municipio = filtro.getMunicipio();
		Integer bairro = filtro.getBairro();
		Integer areaBairro = filtro.getAreaBairro();
		Integer logradouro = filtro.getLogradouro();
		String situacaoDocumentoCobranca = filtro.getSituacaoDocumentoCobranca();

		Date dataPrevisaoClienteInicial = filtro.getDataPrevisaoClienteInicial();
		Date dataPrevisaoClienteFinal = filtro.getDataPrevisaoClienteFinal();

		String indicadorReparo = filtro.getIndicadorReparo();

		Integer quantidadeDiasUnidade = filtro.getQuantidadeDiasUnidade();

		int numeroPagina = filtro.getNumeroPaginasPesquisa();

		if(dataProgramacaoInicial != null || dataProgramacaoFinal != null && situacaoProgramacao != ConstantesSistema.SIM.shortValue()){

			situacaoProgramacao = ConstantesSistema.SIM.shortValue();
		}

		Session session = HibernateUtil.getSession();

		String select = null;
		String from = null;
		String where = null;
		Query query = null;
		Map parameters = new HashMap();
		try{

			select = "SELECT DISTINCT os.id," // 0
							+ "servicotipo.id,"// 1
							+ "servicotipo.descricao,"// 2
							+ "os.imovel.id," // 3
							+ "os.registroAtendimento.id," // 4
							+ "os.cobrancaDocumento.id," // 5
							+ "os.dataGeracao,"// 6
							+ "os.dataEncerramento, "// 7
							+ "os.dataEmissao, " // 8
							+ "cobrancaDocumento.imovel.id, " // 9
							+ "ra.imovel.id, " // 10
							+ "os.dataExecucao, " // 11
							+ "cobDeb.descricao"; // 12

			if(equipe != null || (programado != null && programado.intValue() != ConstantesSistema.TODOS.intValue())){
				from = " FROM OrdemServicoUnidade osUnidade, OrdemServicoProgramacao osp ";
			}else{
				from = " FROM OrdemServicoUnidade osUnidade ";
			}

			from += " INNER JOIN osUnidade.ordemServico os INNER JOIN os.servicoTipo servicotipo "
							+ " LEFT JOIN os.registroAtendimento ra " + " LEFT JOIN os.cobrancaDocumento cobra "
							+ " LEFT JOIN os.imovel imovel  LEFT JOIN os.cobrancaDocumento cobrancaDocumento "
							+ " LEFT JOIN cobrancaDocumento.cobrancaDebitoSituacao cobDeb "
							+ " LEFT JOIN os.registroAtendimento ra ";

			if(situacaoDocumentoCobranca != null && !situacaoDocumentoCobranca.equals("") && !situacaoDocumentoCobranca.equals("-1")){
				from += " INNER JOIN os.cobrancaDocumento cobDoc ";
			}

			where = " WHERE 1 = 1 ";

			if(indicadorReparo != null){
				from += " LEFT JOIN os.ordemServicoReparo ordemServicoReparo ";
				if(indicadorReparo.equals("1")){
					where += " AND servicotipo.indicadorVala = 1 ";
					where += " AND ordemServicoReparo is not null ";
				}else if(indicadorReparo.equals("2")){
					where += " AND servicotipo.indicadorVala = 1 ";
					where += " AND ordemServicoReparo is null ";
				}else{
					where += " AND servicotipo.indicadorVala = 1 ";
				}
			}

			// SITUACAO PENDENTE
			if(situacaoDocumentoCobranca != null && !situacaoDocumentoCobranca.equals("") && situacaoDocumentoCobranca.equals("1")){
				where += " AND cobDoc.cobrancaDebitoSituacao.descricao = (:cobrancaDebitoSituacao) ";
				parameters.put("cobrancaDebitoSituacao", "PENDENTE");
			//DIFERENTE DE PENDENTE	
			}else if(situacaoDocumentoCobranca != null && !situacaoDocumentoCobranca.equals("") && situacaoDocumentoCobranca.equals("2")){
				where += " AND cobDoc.cobrancaDebitoSituacao.descricao <> (:cobrancaDebitoSituacao) ";
				parameters.put("cobrancaDebitoSituacao", "PENDENTE");
				//AMBOS
			}else if(situacaoDocumentoCobranca != null && !situacaoDocumentoCobranca.equals("") && situacaoDocumentoCobranca.equals("0")){
				where += " AND cobDoc is not null ";
			}

			// Período de Previsão para Cliente
			if(dataPrevisaoClienteInicial != null && dataPrevisaoClienteFinal != null){

				where += " AND os.registroAtendimento.dataPrevistaAtual BETWEEN (:dataPrevisaoClienteInicial) AND (:dataPrevisaoClienteFinal) ";

				dataPrevisaoClienteInicial = Util.formatarDataInicial(dataPrevisaoClienteInicial);
				dataPrevisaoClienteFinal = Util.adaptarDataFinalComparacaoBetween(dataPrevisaoClienteFinal);

				parameters.put("dataPrevisaoClienteInicial", dataPrevisaoClienteInicial);
				parameters.put("dataPrevisaoClienteFinal", dataPrevisaoClienteFinal);
			}

			if(numeroRA != null){
				where += " AND os.registroAtendimento.id = (:numeroRA) ";
				parameters.put("numeroRA", numeroRA);
			}

			if(documentoCobranca != null){
				where += " AND cobrancaDocumento.id = (:documentoCobranca) ";
				parameters.put("documentoCobranca", documentoCobranca);
			}

			// [SB0001] - Selecionar Ordem de Servico por Situação
			if(situacaoOrdemServico != ConstantesSistema.NUMERO_NAO_INFORMADO){
				where += " AND os.situacao = (:situacaoOrdemServico) ";
				parameters.put("situacaoOrdemServico", situacaoOrdemServico);
			}

			// Tipo de Serviços
			if(tipoServicos != null && tipoServicos.length > 0){
				where += " AND os.servicoTipo.id in (:idServicoTipo) ";
				parameters.put("idServicoTipo", tipoServicos);
			}

			// [SB0003] - Selecionar Ordem de Servico por Matricula do Imóvel
			if(matriculaImovel != null){
				where += " AND os.imovel.id = (:matriculaImovel) ";
				parameters.put("matriculaImovel", matriculaImovel);
			}

			// Unidade Geração
			if(unidadeGeracao != null){

				where += "AND osUnidade.unidadeOrganizacional.id = :idUnidadeGeracao "
								+ "AND osUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo ";

				parameters.put("idUnidadeGeracao", unidadeGeracao);
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			}

			// Unidade Atual
			if(unidadeAtual != null && permiteTramiteIndependente != null){

				if(permiteTramiteIndependente.intValue() == ConstantesSistema.SIM.intValue()){

					where += " AND EXISTS (SELECT tramite.id FROM Tramite tramite WHERE tramite.ordemServico.id = os.id "
									+ " AND tramite.unidadeOrganizacionalDestino.id = :idUnidadeAtual "
									+ " AND tramite.dataTramite = (SELECT max(tram.dataTramite) FROM Tramite tram "
									+ " WHERE tram.ordemServico.id = tramite.ordemServico.id))";

					parameters.put("idUnidadeAtual", unidadeAtual);
				}else{

					from += " LEFT JOIN ra.tramites tramite ";

					where += " AND ( (tramite.unidadeOrganizacionalDestino.id = :idUnidadeAtual "
									+ " AND tramite.dataTramite = (SELECT max(tram.dataTramite) FROM Tramite tram "
									+ " WHERE tram.registroAtendimento.id = ra.id) ) OR "
									+ " (ra is null AND osUnidade.unidadeOrganizacional.id = :idUnidadeAtual "
									+ " AND osUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo) )";

					parameters.put("idUnidadeAtual", unidadeAtual);
					parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
				}
			}

			// Unidade Superior
			if(unidadeSuperior != null){

				where += " AND osUnidade.unidadeOrganizacional.unidadeSuperior.id = :idUnidadeSuperior "
								+ " AND osUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo ";

				parameters.put("idUnidadeSuperior", unidadeSuperior);
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);

			}

			// Dias de atraso

			if(diasAtraso != null){
				where += " AND (( os.situacao                                                                                                          = :situacao ";
				where += " AND (TRUNC(to_number(SUBSTR((os.dataEncerramento - ra.dataPrevistaAtual),1,instr(os.dataEncerramento - ra.dataPrevistaAtual,' '))))) >= :atraso ) ";
				where += " OR (os.situacao                                                                                                            <> :situacao1 ";
				where += " AND (TRUNC(to_number(SUBSTR((CURRENT_DATE - ra.dataPrevistaAtual),1,instr(CURRENT_DATE - ra.dataPrevistaAtual,' ')))))                >= :atraso1 )) ";

				parameters.put("atraso", diasAtraso);
				parameters.put("situacao", OrdemServico.SITUACAO_ENCERRADO);
				parameters.put("atraso1", diasAtraso);
				parameters.put("situacao1", OrdemServico.SITUACAO_ENCERRADO);
			}

			// Equipe

			if(equipe != null){

				where += " and (osp.ordemServico.id = os.id ";
				where += " 	   and osp.equipe.id = :equipe ";
				where += " 	   and ((osp.indicadorAtivo = 1) ";
				where += " 	   		or (osp.indicadorAtivo = 2 and osp.situacaoFechamento = 2))) ";

				parameters.put("equipe", equipe);
			}else if(programado != null && programado.intValue() == OrdemServico.NAO_PROGRAMADA.intValue()){
				// Não programada
				where += " and (osp.ordemServico.id <> os.id and osp.indicadorAtivo = 2) ";
			}

			// Não programada

			if(programado != null && programado.intValue() != ConstantesSistema.TODOS.intValue()){
				where += " and os.indicadorProgramada = :programada ";

				parameters.put("programada", programado);
			}

			if(numeroOS != null){

				where += " AND os.id = :numeroOS ";
				parameters.put("numeroOS", numeroOS);

				// Ids de Os das unidades filtradas (geracao, atual e superior)
				// Ids de Os dos clientes
			}else if(colecaoIdsOS != null && !colecaoIdsOS.isEmpty()){

				where += " AND os.id in (:listaIdOS) ";
				parameters.put("listaIdOS", colecaoIdsOS);
			}

			// Período de Atendimento
			if(dataAtendimentoInicial != null && dataAtendimentoFinal != null){

				where += " AND ra.registroAtendimento BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal) ";

				parameters.put("dataAtendimentoInicial", Util.formatarDataInicial(dataAtendimentoInicial));
				parameters.put("dataAtendimentoFinal", Util.formatarDataFinal(dataAtendimentoFinal));
			}

			// Período de Data Geracao
			if(dataGeracaoInicial != null && dataGeracaoFinal != null){

				where += " AND os.dataGeracao BETWEEN (:dataGeracaoInicial) AND (:dataGeracaoFinal) ";

				parameters.put("dataGeracaoInicial", Util.formatarDataInicial(dataGeracaoInicial));
				parameters.put("dataGeracaoFinal", Util.formatarDataFinal(dataGeracaoFinal));
			}

			// Período de Programação
			/*
			 * if(dataProgramacaoInicial != null && dataProgramacaoFinal !=
			 * null){
			 * consulta += " AND programacaoRoteiro.dataRoteiro BETWEEN
			 * (:dataProgramacaoInicial) AND (:dataProgramacaoFinal) " + " AND
			 * (osp.indicadorAtivo = 1 or (osp.indicadorAtivo = 2 and
			 * osp.situacaoFechamento = 2)) ";
			 * parameters.put("dataProgramacaoInicial", dataProgramacaoInicial);
			 * parameters.put("dataProgramacaoFinal", dataProgramacaoFinal); }
			 */

			// Período de Data Encerramento
			if(dataEncerramentoInicial != null && dataEncerramentoFinal != null){

				where += " AND os.dataEncerramento BETWEEN (:dataEncerramentoInicial) AND (:dataEncerramentoFinal) ";

				parameters.put("dataEncerramentoInicial", Util.formatarDataInicial(dataEncerramentoInicial));
				parameters.put("dataEncerramentoFinal", Util.formatarDataFinal(dataEncerramentoFinal));
			}

			// Período de Data Execução
			if(dataExecucaoInicial != null && dataExecucaoFinal != null){

				where += " AND os.dataExecucao BETWEEN (:dataExecucaoInicial) AND (:dataExecucaoFinal) ";

				parameters.put("dataExecucaoInicial", Util.formatarDataInicial(dataExecucaoInicial));
				parameters.put("dataExecucaoFinal", Util.formatarDataFinal(dataExecucaoFinal));
			}

			// [SB0006] - Selecionar Ordem de Servico por Município
			if(municipio != null){

				if(from.indexOf(" imovel ") == -1){
					from += " LEFT JOIN os.imovel imovel ";
				}
				if(from.indexOf(" barea ") == -1){
					from += " LEFT JOIN ra.bairroArea barea ";
				}
				if(from.indexOf(" bareabair ") == -1){
					from += " LEFT JOIN barea.bairro bareabair ";
				}
				if(from.indexOf(" imovlogrbair ") == -1){
					from += " LEFT JOIN imovel.logradouroBairro imovlogrbair ";
				}
				if(from.indexOf(" imovbair ") == -1){
					from += " LEFT JOIN imovlogrbair.bairro imovbair ";
				}
				if(from.indexOf(" logrbair ") == -1){
					from += " LEFT JOIN ra.logradouroBairro logrbair ";
				}
				if(from.indexOf(" bair ") == -1){
					from += " LEFT JOIN logrbair.bairro bair ";
				}

				// 1.1.3 Município por Bairro Área
				where += " AND (bareabair.municipio.id = :municipioId "

				// 1.1.2 Município por Imóvel
								+ " OR imovbair.municipio.id = :municipioId "

								// 1.1.1 Município por Logradouro Bairro
								+ " OR bair.municipio.id = :municipioId )";

				parameters.put("municipioId", new Integer(municipio));
			}

			// [SB0007] - Selecionar Ordem de Servico por Bairro
			if(bairro != null && !bairro.equals("")){

				if(from.indexOf(" imovel ") == -1){
					from += " LEFT JOIN os.imovel imovel ";
				}
				if(from.indexOf(" barea ") == -1){
					from += " LEFT JOIN ra.bairroArea barea ";
				}
				if(from.indexOf(" bareabair ") == -1){
					from += " LEFT JOIN barea.bairro bareabair ";
				}
				if(from.indexOf(" imovlogrbair ") == -1){
					from += " LEFT JOIN imovel.logradouroBairro imovlogrbair ";
				}
				if(from.indexOf(" logrbair ") == -1){
					from += " LEFT JOIN ra.logradouroBairro logrbair ";
				}
				if(from.indexOf(" bair ") == -1){
					from += " LEFT JOIN logrbair.bairro bair ";
				}

				// 1.1.3 Bairro por Bairro Área
				where += " AND (bareabair.id = :bairroId "

				// 1.1.2 Bairro por Imóvel
								+ " OR imovlogrbair.bairro.id = :bairroId "

								// 1.1.1 Bairro por Logradouro Bairro
								+ " OR bair.id = :bairroId) ";

				parameters.put("bairroId", new Integer(bairro));

				if(areaBairro != null){

					where += " AND ra.bairroArea.id = :bairroAreaId ";
					parameters.put("bairroAreaId", areaBairro);
				}
			}

			// [SB0008] - Selecionar Ordem de Servico por Logradouro
			if(logradouro != null && !logradouro.equals("")){

				if(from.indexOf(" imovel ") == -1){
					from += " LEFT JOIN os.imovel imovel ";
				}
				if(from.indexOf(" imovlogrbair ") == -1){
					from += " LEFT JOIN imovel.logradouroBairro imovlogrbair ";
				}
				if(from.indexOf(" logrbair ") == -1){
					from += " LEFT JOIN ra.logradouroBairro logrbair ";
				}

				// 1.1.2 Logradouro por Imóvel
				where += " AND (imovlogrbair.logradouro.id = :logradouroId "

				// 1.1.1 Logradouro por Logradouro Bairro
								+ " OR logrbair.logradouro.id = :logradouroId) ";

				parameters.put("logradouroId", new Integer(logradouro));
			}

			if(quantidadeDiasUnidade != null && quantidadeDiasUnidade >= 0){

				if(permiteTramiteIndependente.intValue() == 1){
					from += "left join os.tramites tramites ";
					where += " and trunc(CURRENT_DATE()) - trunc(tramites.dataTramite) >= :quantidadeDiasUnidade ";
					parameters.put("quantidadeDiasUnidade", quantidadeDiasUnidade);
				}else{
					from += "left join os.registroAtendimento.tramites tramites ";
					where += " and trunc(CURRENT_DATE()) - trunc(tramites.dataTramite) >= :quantidadeDiasUnidade ";
					parameters.put("quantidadeDiasUnidade", quantidadeDiasUnidade);
				}
			}

			where += "order by os.id asc";


			query = session.createQuery(select + from + where);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer){
					Integer collection = (Integer) parameters.get(key);
					query.setInteger(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}
			if(numeroPagina == ConstantesSistema.NUMERO_NAO_INFORMADO){
				retornoConsulta = query.list();
			}else{
				retornoConsulta = query.setFirstResult(10 * numeroPagina).setMaxResults(10).list();
			}

			if(retornoConsulta.size() > 0){

				retorno = new ArrayList();

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					Imovel imovel = null;
					OrdemServico os = null;
					ServicoTipo servicoTipo = null;
					RegistroAtendimento registro = null;
					CobrancaDocumento cobranca = null;

					os = new OrdemServico();
					os.setId((Integer) element[0]);

					servicoTipo = new ServicoTipo();
					servicoTipo.setId((Integer) element[1]);
					servicoTipo.setDescricao((String) element[2]);

					imovel = new Imovel();

					if(element[4] != null){

						registro = new RegistroAtendimento();
						registro.setId((Integer) element[4]);
						imovel.setId((Integer) element[10]);
						registro.setImovel(imovel);
					}else if(element[5] != null){

						cobranca = new CobrancaDocumento();
						cobranca.setId((Integer) element[5]);
						imovel.setId((Integer) element[9]);
						cobranca.setImovel(imovel);
					}else{

						imovel.setId((Integer) element[3]);
						os.setImovel(imovel);
					}

					os.setDataGeracao((Date) element[6]);
					os.setDataEncerramento((Date) element[7]);

					if(element[11] != null){
						os.setDataExecucao((Date) element[11]);
					}

					os.setDataEmissao((Date) element[8]);
					os.setCobrancaDocumento(cobranca);
					os.setRegistroAtendimento(registro);
					os.setServicoTipo(servicoTipo);
					if(element[12] != null){
						os.setSituacaoDocumentoDebito((String) element[12]);
					}

					retorno.add(os);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Caso exista ordem de servico abertos para a unidade de geracao informada
	 * (existe ocorrência na tabela ORDEM_SERVICO com ORDEM_SERVICO_UNIDADE=Id
	 * da Unidade de Atendimento e ATTP_ID=1 - ABRIR/REGISTRAR)
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param idUnidadeGeracao
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorUnidadeGeracao(Integer idUnidadeGeracao) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT os.id " + "FROM OrdemServicoUnidade osUnidade " + "INNER JOIN osUnidade.atendimentoRelacaoTipo art  "
							+ "INNER JOIN osUnidade.ordemServico os  " + "INNER JOIN osUnidade.unidadeOrganizacional unid  "
							+ "LEFT JOIN os.registroAtendimento ra  " + "WHERE unid.id = :idUnidade " + "AND art.id = :idAtendimentoTipo ";

			retornoConsulta = session.createQuery(consulta).setInteger("idUnidade", idUnidadeGeracao)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Caso exista ordem de servico abertos para a unidade de geracao informada
	 * (existe ocorrência na tabela ORDEM_SERVICO com ORDEM_SERVICO_UNIDADE=Id
	 * da Unidade de Atendimento e ATTP_ID=1 - ABRIR/REGISTRAR)
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param idUnidadeGeracao
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorUnidadeAtual(Integer recuperaOSPorUnidadeAtual) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT os.id " + "FROM OrdemServicoUnidade osUnidade " + "INNER JOIN osUnidade.atendimentoRelacaoTipo art  "
							+ "INNER JOIN osUnidade.ordemServico os  " + "INNER JOIN osUnidade.unidadeOrganizacional unid  "
							+ "LEFT JOIN os.registroAtendimento ra  " + "WHERE unid.id = :idUnidade " + "AND ra IS NULL "
							+ "AND art.id = :idAtendimentoTipo ";

			retornoConsulta = session.createQuery(consulta).setInteger("idUnidade", recuperaOSPorUnidadeAtual)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	// /**
	// * @author isilva
	// * @return
	// */
	// private String montarConsultaOrdemServico(){
	// String consulta = "SELECT os.id, " // 0
	// + "ra.id," // 1
	// + "servicoTipo.id,"// 2
	// + "servicoTipo.descricao,"// 3
	// + "imovel.id,"// 4
	// + "imovel.lote,"// 5
	// + "imovel.subLote,"// 6
	// + "local.id,"// 7
	// + "setor.id,"// 8
	// + "quadra.id,"// 9
	// + "quadra.numeroQuadra,"// 10
	// + "ligAgua.id,"// 11
	// + "ligAguaSitu.id,"// 12
	// + "ligAguaSitu.descricao,"// 13
	// + "ligEsgoto.id,"// 14
	// + "ligEsgotoSitu.id,"// 15
	// + "ligEsgotoSitu.descricao,"// 16
	// + "consumoTarifa.id,"// 17
	// + "consumoTarifa.descricao,"// 18
	// + "hidroInstHist.id,"// 19
	// + "supressaoTipo.id,"// 20
	// + "supressaoTipo.descricao,"// 21
	// + "supressaoMotivo.id,"// 22
	// + "supressaoMotivo.descricao,"// 23
	// + "ligacaoAguaDiametro.id,"// 24
	// + "ligacaoAguaDiametro.descricao,"// 25
	// + "ligacaoAguaMaterial.id,"// 26
	// + "ligacaoAguaMaterial.descricao,"// 27
	// + "ligacaoAguaPerfil.id,"// 28
	// + "ligacaoAguaPerfil.descricao,"// 29
	// + "hidro.id,"// 30
	// + "hidro.numero,"// 31
	// + "medicaoTipo.id,"// 32
	// + "medicaoTipo.descricao, "// 33
	// + "debitoTipo.id, " // 34
	// + "debitoTipo.descricao, " // 35
	// + "ste.indicadorLigacaoAgua, " // 36
	// + "servicoTipo.tempoMedioExecucao, " // 37
	// + "imovel.indicadorExclusao, " // 38
	// + "ligEsgDiam.id, ligEsgDiam.descricao, " // 39,40
	// + "ligEsgMat.id, ligEsgMat.descricao, " // 41,42
	// + "ligEsgPerf.id, ligEsgPerf.descricao, " // 43,44
	// + "ligEsgoto.percentualAguaConsumidaColetada, " // 45
	// + "hidroLocalInst.id, "// 46
	// + "hidroProtecao.id, "// 47
	// + "hidroInstHist.indicadorExistenciaCavalete, hidroInstHist.numeroLeituraInstalacao, " //
	// 48,49
	// + "quadra.indicadorRedeAgua, " // 50
	// + "os.percentualCobranca, "// 51
	// +
	// "imovHidroInstHist.id, imovHidroInstHist.indicadorExistenciaCavalete, imovHidroInstHist.numeroLeituraInstalacao, "
	// // 52,53,54
	// + "imovHidroLocalInst.id, imovHidroProtecao.id, imovHidro.id, imovHidro.numero, " //
	// 55,56,57,58
	// + "imovMedicaoTipo.id, medicaoTipo.descricao, " // 59,60
	// + "os.situacao, "// 61
	// + "os.dataEncerramento, "// 62
	// + "quadra.indicadorRedeEsgoto, "// 63
	// + "ligEsgoto.consumoMinimo, ligAgua.numeroConsumoMinimoAgua, imovel.numeroImovel, "//
	// 64,65,66
	// + "amen.id, "// 67
	// + "amen.indicadorExecucao, "// 68
	// + "imovHidroSit.id, imovHidroSit.descricao, "// 69, 70
	// + "hidroSit.id, hidroSit.descricao, "// 71,72
	// + "hidroInstHist.dataInstalacao, "// 73
	// + "hidroInstHist.dataImplantacaoSistema, "// 74
	// + "corteTipo.id, "// 75
	// + "corteTipo.descricao, "// 76
	// + "motivoCorte.id, "// 77
	// + "motivoCorte.descricao, "// 78
	// + "ligAgua.numeroSeloCorte, "// 79
	// + "ligAgua.numeroSeloSupressao, "// 80
	// + "hidroInstHist.dataRetirada, "// 81
	// + "hidroInstHist.numeroSelo, imovHidroInstHist.numeroSelo, " // 82,83
	// + "os.ultimaAlteracao, imovel.ultimaAlteracao, "// 84,85
	// + "os.indicadorComercialAtualizado, hidro.dataAquisicao, " // 86,87
	// + "imovHidro.dataAquisicao, imovHidro.anoFabricacao, " // 88,89
	// + "hidro.anoFabricacao, hidro.numeroLeituraAcumulada, " // 90,91
	// + "imovHidro.numeroLeituraAcumulada, " // 92
	// + "ligAgua.ultimaAlteracao, "// 93
	// + "hidroInstHist.ligacaoAgua.id, "// 94
	// + "imovHidroInstHist.ligacaoAgua.id, "// 95
	// + "os.dataGeracao, "// 96
	// + "servicoTipoPrioridadeOriginal.id, "// 97
	// + "servicoTipoPrioridadeAtual.id, "// 98
	// + "ramalLocalInstalacao.id, "// 99
	// + "ramalLocalInstalacao.descricao, "// 100
	// + "amen.descricao, "// 101
	// + "ste.descricao, "// 102
	// + "ligEsgoto.indicadorCaixaGordura, "// 103
	// + "imovelOS.id, "// 104
	// + "locaOS.id, "// 105
	// + "stcmOS.id, "// 106
	// + "stcmOS.codigo, "// 107
	// + "qdraOS.id, "// 108
	// + "qdraOS.numeroQuadra, "// 109
	// + "imovelOS.lote, "// 110
	// + "imovelOS.subLote, "// 111
	// + "setor.codigo, "// 112
	//
	//
	// // Dados do Imóvel da OS
	// + "ligAguaOS.id,"// 113
	// + "ligAguaSituOS.id,"// 114
	// + "ligAguaSituOS.descricao,"// 115
	// + "ligEsgotoOS.id,"// 116
	// + "ligEsgotoSituOS.id,"// 117
	// + "ligEsgotoSituOS.descricao,"// 118
	// + "consumoTarifaOS.id,"// 119
	// + "consumoTarifaOS.descricao,"// 120
	// + "hidroInstHistOS.id,"// 121
	// + "supressaoTipoOS.id,"// 122
	// + "supressaoTipoOS.descricao,"// 123
	// + "supressaoMotivoOS.id,"// 124
	// + "supressaoMotivoOS.descricao,"// 125
	// + "ligacaoAguaDiametroOS.id,"// 126
	// + "ligacaoAguaDiametroOS.descricao,"// 127
	// + "ligacaoAguaMaterialOS.id,"// 128
	// + "ligacaoAguaMaterialOS.descricao,"// 129
	// + "ligacaoAguaPerfilOS.id,"// 130
	// + "ligacaoAguaPerfilOS.descricao,"// 131
	// + "hidroOS.id,"// 132
	// + "hidroOS.numero,"// 133
	// + "medicaoTipoOS.id,"// 134
	// + "medicaoTipoOS.descricao, "// 135
	// + "imovelOS.indicadorExclusao, " // 136
	// + "ligEsgDiamOS.id, ligEsgDiamOS.descricao, " // 137, 138
	// + "ligEsgMatOS.id, ligEsgMatOS.descricao, " // 139, 140
	// + "ligEsgPerfOS.id, ligEsgPerfOS.descricao, " // 141, 142
	// + "ligEsgotoOS.percentualAguaConsumidaColetada, " // 143
	// + "hidroLocalInstOS.id, "// 144
	// + "hidroProtecaoOS.id, "// 145
	//
	// + "hidroInstHistOS.indicadorExistenciaCavalete, hidroInstHistOS.numeroLeituraInstalacao, " //
	// 146,
	// // 147
	// + "qdraOS.indicadorRedeAgua, " // 148
	//
	// + "imovHidroInstHistOS.id, imovHidroInstHistOS.indicadorExistenciaCavalete, " // 149,
	// // 150
	// + "imovHidroInstHistOS.numeroLeituraInstalacao, " // 151
	//
	// + "imovHidroLocalInstOS.id, imovHidroProtecaoOS.id, imovHidroOS.id, " // 152,
	// // 153,
	// // 154
	// + "imovHidroOS.numero, " // 155
	// + "imovMedicaoTipoOS.id, " // 156
	// + "medicaoTipoOS.descricao, " // 157
	// + "qdraOS.indicadorRedeEsgoto, "// 158
	//
	// + "ligEsgotoOS.consumoMinimo, ligAguaOS.numeroConsumoMinimoAgua, imovelOS.numeroImovel, "//
	// 159,
	// // 160,
	// // 161
	// + "imovHidroSitOS.id, imovHidroSitOS.descricao, "// 162, 163
	// + "hidroSitOS.id, hidroSitOS.descricao, "// 164, 165
	// + "hidroInstHistOS.dataInstalacao, "// 166
	// + "hidroInstHistOS.dataImplantacaoSistema, "// 167
	// + "corteTipoOS.id, "// 168
	// + "corteTipoOS.descricao, "// 169
	// + "motivoCorteOS.id, "// 170
	// + "motivoCorteOS.descricao, "// 171
	// + "ligAguaOS.numeroSeloCorte, "// 172
	// + "ligAguaOS.numeroSeloSupressao, "// 173
	// + "hidroInstHistOS.dataRetirada, "// 174
	// + "hidroInstHistOS.numeroSelo, imovHidroInstHistOS.numeroSelo, " // 175, 176
	// + "imovHidroOS.dataAquisicao, imovHidroOS.anoFabricacao, " // 177, 178
	// + "hidroOS.anoFabricacao, hidroOS.numeroLeituraAcumulada, " // 179, 180
	// + "imovHidroOS.numeroLeituraAcumulada, " // 181
	// + "ligAguaOS.ultimaAlteracao, "// 182
	// + "hidroInstHistOS.ligacaoAgua.id, "// 183
	// + "imovHidroInstHistOS.ligacaoAgua.id, "// 184
	// + "ramalLocalInstalacaoOS.id, "// 185
	// + "ramalLocalInstalacaoOS.descricao, "// 186
	// + "ligEsgotoOS.indicadorCaixaGordura, "// 187
	// + "imovelOS.ultimaAlteracao, hidroOS.dataAquisicao, " // 188, 189
	//
	// + "osReferencia.id, osReferencia.situacao, " // 190, 191
	//
	// + "servicoTipo.indicadorPermiteAlterarValor, " // 192
	// + "servicoTipo.indicadorCobrarJuros, " // 193
	// + "ligAgua.numeroLacre, " // 194
	// + "ligAguaOS.numeroLacre, " // 195
	// + "ligAgua.dataLigacao, " // 196
	// + "ligEsgoto.dataLigacao, " // 197
	// + "ligAguaOS.dataLigacao, " // 198
	// + "ligEsgotoOS.dataLigacao, " // 199
	//
	// + "hidroInstHist.indicadorTrocaProtecao, " // 200 (utilizados com os params
	// // 48,49)
	// + "hidroInstHist.indicadorTrocaRegistro,  " // 201
	//
	// + "imovHidroInstHist.indicadorTrocaProtecao, " // 202 (utilizados com os
	// // params 52,53,54)
	// + "imovHidroInstHist.indicadorTrocaRegistro, " // 203
	// + "perfilImovel.id, " // 204
	// + "imovel.numeroPontosUtilizacao, " // 205
	// + "ordemServicoLayoutOS.id, " // 206
	// + "ordemServicoLayoutOS.nomeRelatorio, " // 207
	// + "ordemServicoLayoutOS.nomeClasse, " // 208
	// + "os.observacao, " // 209
	// + "cbdo.id, " // 210
	//
	// + "ordemServicoLayoutOS.indicadorDoisPorPagina, " // 211 (utilizados com os
	// // params 206, 207, 208)
	// + "caac.id, " // 212 ()
	// + "cbcr.id, " // 213 ()
	// + "cbac2.id, " // 214 ()
	// + "cbac2.numeroDiasValidade, " // 215 ()
	// + "cacm.id, " // 216 ()
	// + "cbac.id, " // 217 ()
	// + "cbac.numeroDiasValidade, " // 218 ()
	// + "lgbr.id, " // 219 ()
	// + "supressaoTipo.indicadorTotal, " // 220 ()
	// + "perfilImovelOS.id, " // 221
	// + "servicoTipoPrioridadeOriginal.descricao, "// 222
	// + "servicoTipoPrioridadeAtual.descricao "// 223
	//
	// + "FROM OrdemServico os "
	// + "LEFT JOIN os.registroAtendimento ra  "
	// + "LEFT JOIN os.atendimentoMotivoEncerramento amen "
	// + "LEFT JOIN ra.solicitacaoTipoEspecificacao ste "
	// + "INNER JOIN os.servicoTipo servicoTipo  "
	// + "LEFT JOIN servicoTipo.debitoTipo debitoTipo  "
	// + "LEFT JOIN os.servicoTipoPrioridadeOriginal servicoTipoPrioridadeOriginal  "
	// + "INNER JOIN os.servicoTipoPrioridadeAtual servicoTipoPrioridadeAtual  "
	// + "LEFT JOIN os.osReferencia osReferencia "
	//
	// // Dados do Imóvel do RA da OS
	// + "LEFT JOIN ra.imovel imovel "
	// + "LEFT JOIN imovel.localidade local "
	// + "LEFT JOIN imovel.setorComercial setor  "
	// + "LEFT JOIN imovel.quadra quadra  "
	// + "LEFT JOIN imovel.imovelPerfil perfilImovel "
	// + "LEFT JOIN imovel.ligacaoAgua ligAgua  "
	// + "LEFT JOIN imovel.ligacaoAguaSituacao ligAguaSitu  "
	// + "LEFT JOIN ligAgua.supressaoTipo supressaoTipo  "
	// + "LEFT JOIN ligAgua.supressaoMotivo supressaoMotivo  "
	// + "LEFT JOIN ligAgua.ligacaoAguaDiametro ligacaoAguaDiametro  "
	// + "LEFT JOIN ligAgua.ligacaoAguaMaterial ligacaoAguaMaterial  "
	// + "LEFT JOIN ligAgua.ligacaoAguaPerfil ligacaoAguaPerfil  "
	// + "LEFT JOIN ligAgua.corteTipo corteTipo "
	// + "LEFT JOIN ligAgua.motivoCorte motivoCorte "
	// + "LEFT JOIN ligAgua.ramalLocalInstalacao ramalLocalInstalacao "
	// + "LEFT JOIN ligAgua.hidrometroInstalacaoHistorico hidroInstHist  "
	// + "LEFT JOIN hidroInstHist.hidrometroLocalInstalacao hidroLocalInst  "
	// + "LEFT JOIN hidroInstHist.hidrometroProtecao hidroProtecao  "
	// + "LEFT JOIN hidroInstHist.medicaoTipo  medicaoTipo "
	// + "LEFT JOIN hidroInstHist.hidrometro  hidro "
	// + "LEFT JOIN hidro.hidrometroSituacao hidroSit "
	// + "LEFT JOIN imovel.ligacaoEsgoto ligEsgoto "
	// + "LEFT JOIN imovel.ligacaoEsgotoSituacao ligEsgotoSitu  "
	// + "LEFT JOIN ligEsgoto.ligacaoEsgotoDiametro ligEsgDiam "
	// + "LEFT JOIN ligEsgoto.ligacaoEsgotoMaterial ligEsgMat "
	// + "LEFT JOIN ligEsgoto.ligacaoEsgotoPerfil ligEsgPerf "
	// + "LEFT JOIN imovel.hidrometroInstalacaoHistorico imovHidroInstHist  "
	// + "LEFT JOIN imovHidroInstHist.hidrometroLocalInstalacao imovHidroLocalInst  "
	// + "LEFT JOIN imovHidroInstHist.hidrometroProtecao imovHidroProtecao  "
	// + "LEFT JOIN imovHidroInstHist.medicaoTipo  imovMedicaoTipo "
	// + "LEFT JOIN imovHidroInstHist.hidrometro  imovHidro "
	// + "LEFT JOIN imovHidro.hidrometroSituacao imovHidroSit "
	// + "LEFT JOIN imovel.consumoTarifa consumoTarifa  "
	//
	// // Dados do Imóvel da OS
	// + "LEFT JOIN os.imovel imovelOS "
	// + "LEFT JOIN imovelOS.localidade locaOS "
	// + "LEFT JOIN imovelOS.setorComercial stcmOS  "
	// + "LEFT JOIN imovelOS.quadra qdraOS  "
	// + "LEFT JOIN imovelOS.ligacaoAgua ligAguaOS  "
	// + "LEFT JOIN imovelOS.ligacaoAguaSituacao ligAguaSituOS  "
	// + "LEFT JOIN imovelOS.imovelPerfil perfilImovelOS "
	// + "LEFT JOIN ligAguaOS.supressaoTipo supressaoTipoOS  "
	// + "LEFT JOIN ligAguaOS.supressaoMotivo supressaoMotivoOS  "
	// + "LEFT JOIN ligAguaOS.ligacaoAguaDiametro ligacaoAguaDiametroOS  "
	// + "LEFT JOIN ligAguaOS.ligacaoAguaMaterial ligacaoAguaMaterialOS  "
	// + "LEFT JOIN ligAguaOS.ligacaoAguaPerfil ligacaoAguaPerfilOS  "
	// + "LEFT JOIN ligAguaOS.corteTipo corteTipoOS "
	// + "LEFT JOIN ligAguaOS.motivoCorte motivoCorteOS "
	// + "LEFT JOIN ligAguaOS.ramalLocalInstalacao ramalLocalInstalacaoOS "
	// + "LEFT JOIN ligAguaOS.hidrometroInstalacaoHistorico hidroInstHistOS  "
	// + "LEFT JOIN hidroInstHistOS.hidrometroLocalInstalacao hidroLocalInstOS  "
	// + "LEFT JOIN hidroInstHistOS.hidrometroProtecao hidroProtecaoOS  "
	// + "LEFT JOIN hidroInstHistOS.medicaoTipo  medicaoTipoOS "
	// + "LEFT JOIN hidroInstHistOS.hidrometro  hidroOS "
	// + "LEFT JOIN hidroOS.hidrometroSituacao hidroSitOS "
	// + "LEFT JOIN imovelOS.ligacaoEsgoto ligEsgotoOS "
	// + "LEFT JOIN imovelOS.ligacaoEsgotoSituacao ligEsgotoSituOS  "
	// + "LEFT JOIN ligEsgotoOS.ligacaoEsgotoDiametro ligEsgDiamOS "
	// + "LEFT JOIN ligEsgotoOS.ligacaoEsgotoMaterial ligEsgMatOS "
	// + "LEFT JOIN ligEsgotoOS.ligacaoEsgotoPerfil ligEsgPerfOS "
	// + "LEFT JOIN imovelOS.hidrometroInstalacaoHistorico imovHidroInstHistOS  "
	// + "LEFT JOIN imovHidroInstHistOS.hidrometroLocalInstalacao imovHidroLocalInstOS  "
	// + "LEFT JOIN imovHidroInstHistOS.hidrometroProtecao imovHidroProtecaoOS  "
	// + "LEFT JOIN imovHidroInstHistOS.medicaoTipo  imovMedicaoTipoOS "
	// + "LEFT JOIN imovHidroInstHistOS.hidrometro  imovHidroOS "
	// + "LEFT JOIN imovHidroOS.hidrometroSituacao imovHidroSitOS "
	// + "LEFT JOIN imovelOS.consumoTarifa consumoTarifaOS  "
	// + "LEFT JOIN servicoTipo.ordemServicoLayout ordemServicoLayoutOS  "
	// + "LEFT JOIN os.cobrancaDocumento cbdo "
	// + "LEFT JOIN os.cobrancaAcaoAtividadeComando cacm "
	// + "LEFT JOIN os.cobrancaAcaoAtividadeCronograma caac "
	// + "LEFT JOIN cacm.cobrancaAcao cbac "
	// + "LEFT JOIN caac.cobrancaAcaoCronograma cbcr "
	//
	//
	// + "LEFT JOIN cbcr.cobrancaAcao cbac2 " + "LEFT JOIN imovelOS.logradouroBairro lgbr ";
	//
	// return consulta;
	// }

	/**
	 * @author isilva
	 * @return
	 */
	private String montarConsultaOrdemServico(){

		String consulta = "SELECT os FROM OrdemServico os ";
		consulta += "INNER JOIN FETCH os.servicoTipo st ";
		consulta += "LEFT OUTER JOIN FETCH st.ordemServicoLayout osl ";
		consulta += "LEFT OUTER JOIN FETCH os.registroAtendimento ra ";
		consulta += "LEFT OUTER JOIN FETCH ra.logradouroCep lc ";
		consulta += "LEFT OUTER JOIN FETCH lc.cep c ";
		consulta += "LEFT OUTER JOIN FETCH os.imovel imov ";
		consulta += "LEFT OUTER JOIN FETCH imov.imovelPerfil impf ";
		consulta += "LEFT OUTER JOIN FETCH imov.ligacaoAguaSituacao last ";

		return consulta;
	}

	/**
	 * [UC0367] Atualizar Dados da Ligação Agua
	 * Consulta a ordem de servico pelo id
	 * 
	 * @author Rafael Pinto
	 * @date 24/08/2006
	 * @author eduardo henrique
	 * @date 29/07/2008
	 *       Inclusão dos Novos Atributos Obrigatórios do HidrometroInstalacaoHistorico (v0.03)
	 * @author eduardo henrique
	 * @date 24/11/2008
	 *       Alteração no método para inclusão do Perfil do Imóvel na consulta
	 * @author eduardo henrique
	 * @date 19/03/2009
	 *       Alteração no método para inclusão do Pontos de Utilização do Imóvel na Consulta
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico recuperaOSPorId(Integer idOS) throws ErroRepositorioException{

		OrdemServico ordemServico = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = this.montarConsultaOrdemServico();
			consulta = consulta + " WHERE os.id = :idOS ";

			ordemServico = (OrdemServico) session.createQuery(consulta).setInteger("idOS", idOS).setMaxResults(1).uniqueResult();

			inicializarObjetosOS(ordemServico);
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return ordemServico;
	}

	/**
	 * [UC0367] Atualizar Dados da Ligação Agua
	 * Consulta a ordem de servico pelo id
	 * 
	 * @author isilva
	 * @date 17/09/2010
	 * @param idsOS
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServico> pesquisarOrdensServicos(List<Integer> idsOS) throws ErroRepositorioException{

		Query query = null;

		Collection<OrdemServico> ordensServicos = new ArrayList<OrdemServico>();

		Session session = HibernateUtil.getSession();

		Collection<OrdemServico> retorno = null;

		Map parameters = new HashMap();

		String consulta = "";

		try{

			consulta = this.montarConsultaOrdemServico();

			consulta = consulta + " WHERE os.id in (:idsOS) ";
			parameters.put("idsOS", idsOS);

			query = session.createQuery(consulta);

			query = Util.configuraParamentrosEmQuery(query, parameters);

			retorno = query.list();

			if(retorno != null && !retorno.isEmpty()){

				for(OrdemServico ordemServico : retorno){

					// OrdemServico ordemServico = null;
					//
					// if (retornoConsulta != null) {
					//
					// ordemServico = new OrdemServico();
					// ordemServico.setId((Integer) retornoConsulta[0]);
					//
					// if (retornoConsulta[51] != null) {
					// ordemServico
					// .setPercentualCobranca((BigDecimal) retornoConsulta[51]);
					// }
					// if (retornoConsulta[61] != null) {
					// ordemServico.setSituacao((Short) retornoConsulta[61]);
					// }
					// if (retornoConsulta[62] != null) {
					// ordemServico
					// .setDataEncerramento((Date) retornoConsulta[62]);
					// }
					//
					// if (retornoConsulta[96] != null) {
					// ordemServico.setDataGeracao((Date) retornoConsulta[96]);
					// }
					//
					// if (retornoConsulta[209] != null) {
					// ordemServico.setObservacao((String)retornoConsulta[209]);
					// }
					//
					// if (retornoConsulta[210] != null) {
					// CobrancaDocumento cobrancaDocumento = new CobrancaDocumento();
					// cobrancaDocumento.setId((Integer) retornoConsulta[210]);
					//
					// ordemServico.setCobrancaDocumento(cobrancaDocumento);
					// }
					//
					// if (retornoConsulta[190] != null) {
					//
					// OrdemServico ordemServicoReferencia = new OrdemServico();
					//
					// ordemServicoReferencia.setId((Integer) retornoConsulta[190]);
					// ordemServicoReferencia.setSituacao((Short) retornoConsulta[191]);
					//
					// ordemServico.setOsReferencia(ordemServicoReferencia);
					// }
					//
					// ordemServico.setUltimaAlteracao((Date) retornoConsulta[84]);
					//
					// RegistroAtendimento registro = null;
					//
					// if (retornoConsulta[1] != null) {
					// registro = new RegistroAtendimento();
					// registro.setId((Integer) retornoConsulta[1]);
					//
					// SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new
					// SolicitacaoTipoEspecificacao();
					// solicitacaoTipoEspecificacao
					// .setIndicadorLigacaoAgua((Short) retornoConsulta[36]);
					// solicitacaoTipoEspecificacao
					// .setDescricao((String) retornoConsulta[102]);
					//
					// registro
					// .setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
					//
					// if (retornoConsulta[4] != null) {
					//
					// Imovel imovel = new Imovel();
					// imovel.setId((Integer) retornoConsulta[4]);
					// imovel.setLote((Short) retornoConsulta[5]);
					// imovel.setSubLote((Short) retornoConsulta[6]);
					// imovel
					// .setIndicadorExclusao((Short) retornoConsulta[38]);
					// imovel.setNumeroImovel((String) retornoConsulta[66]);
					// imovel.setUltimaAlteracao((Date) retornoConsulta[85]);
					//
					// if (retornoConsulta[7] != null) {
					// Localidade local = new Localidade();
					// local.setId((Integer) retornoConsulta[7]);
					//
					// imovel.setLocalidade(local);
					// }
					//
					// if (retornoConsulta[8] != null) {
					// SetorComercial setor = new SetorComercial();
					// setor.setId((Integer) retornoConsulta[8]);
					// setor.setCodigo((Integer) retornoConsulta[112]);
					//
					// imovel.setSetorComercial(setor);
					// }
					//
					// if (retornoConsulta[9] != null) {
					// Quadra quadra = new Quadra();
					// quadra.setId((Integer) retornoConsulta[9]);
					// quadra
					// .setNumeroQuadra((Integer) retornoConsulta[10]);
					// quadra
					// .setIndicadorRedeAgua((Short) retornoConsulta[50]);
					// quadra
					// .setIndicadorRedeEsgoto((Short) retornoConsulta[63]);
					// quadra
					// .setIndicadorRedeAgua((Short) retornoConsulta[50]);
					//
					// imovel.setQuadra(quadra);
					// }
					//
					// if (retornoConsulta[204] != null){
					// ImovelPerfil imovelPerfil = new ImovelPerfil();
					// imovelPerfil.setId((Integer) retornoConsulta[204]);
					//
					// imovel.setImovelPerfil(imovelPerfil);
					// }
					//
					// if (retornoConsulta[52] != null) {
					// HidrometroInstalacaoHistorico imovHidrometroInstalacaoHist = new
					// HidrometroInstalacaoHistorico();
					// imovHidrometroInstalacaoHist
					// .setId((Integer) retornoConsulta[52]);
					// imovHidrometroInstalacaoHist
					// .setIndicadorExistenciaCavalete((Short) retornoConsulta[53]);
					// imovHidrometroInstalacaoHist
					// .setNumeroLeituraInstalacao((Integer) retornoConsulta[54]);
					// imovHidrometroInstalacaoHist
					// .setNumeroSelo((String) retornoConsulta[83]);
					// imovHidrometroInstalacaoHist
					// .setIndicadorTrocaProtecao((Short) retornoConsulta[202]);
					// imovHidrometroInstalacaoHist
					// .setIndicadorTrocaRegistro((Short) retornoConsulta[203]);
					//
					// LigacaoAgua ligacaoAgua = new LigacaoAgua();
					// ligacaoAgua.setId((Integer) retornoConsulta[94]);
					//
					// imovHidrometroInstalacaoHist
					// .setLigacaoAgua(ligacaoAgua);
					//
					// HidrometroLocalInstalacao imovHidrometroLocalInstalacao = new
					// HidrometroLocalInstalacao();
					// imovHidrometroLocalInstalacao
					// .setId((Integer) retornoConsulta[55]);
					// imovHidrometroInstalacaoHist
					// .setHidrometroLocalInstalacao(imovHidrometroLocalInstalacao);
					//
					// HidrometroProtecao imovHidrometroProtecao = new HidrometroProtecao();
					// imovHidrometroProtecao
					// .setId((Integer) retornoConsulta[56]);
					// imovHidrometroInstalacaoHist
					// .setHidrometroProtecao(imovHidrometroProtecao);
					//
					// if (retornoConsulta[57] != null) {
					//
					// Hidrometro imovHidrometro = new Hidrometro();
					// imovHidrometro
					// .setId((Integer) retornoConsulta[57]);
					// imovHidrometro
					// .setNumero((String) retornoConsulta[58]);
					// imovHidrometro
					// .setDataAquisicao((Date) retornoConsulta[88]);
					// imovHidrometro
					// .setAnoFabricacao((Short) retornoConsulta[89]);
					// imovHidrometro
					// .setNumeroLeituraAcumulada((Integer) retornoConsulta[92]);
					//
					// imovHidrometroInstalacaoHist
					// .setHidrometro(imovHidrometro);
					// HidrometroSituacao hidrometroSituacao = new HidrometroSituacao();
					// hidrometroSituacao
					// .setId((Integer) retornoConsulta[69]);
					// hidrometroSituacao
					// .setDescricao((String) retornoConsulta[70]);
					// imovHidrometro
					// .setHidrometroSituacao(hidrometroSituacao);
					// }
					//
					// if (retornoConsulta[59] != null) {
					// MedicaoTipo imovMedicaoTipo = new MedicaoTipo();
					// imovMedicaoTipo
					// .setId((Integer) retornoConsulta[59]);
					// imovMedicaoTipo
					// .setDescricao((String) retornoConsulta[60]);
					//
					// imovHidrometroInstalacaoHist
					// .setMedicaoTipo(imovMedicaoTipo);
					// }
					//
					// imovel
					// .setHidrometroInstalacaoHistorico(imovHidrometroInstalacaoHist);
					// }
					//
					// if (retornoConsulta[11] != null) {
					//
					// LigacaoAgua ligacaoAgua = new LigacaoAgua();
					// ligacaoAgua.setId((Integer) retornoConsulta[11]);
					// ligacaoAgua
					// .setNumeroConsumoMinimoAgua((Integer) retornoConsulta[65]);
					// ligacaoAgua
					// .setNumeroSeloCorte((Integer) retornoConsulta[79]);
					// ligacaoAgua
					// .setNumeroSeloSupressao((Integer) retornoConsulta[80]);
					// ligacaoAgua
					// .setUltimaAlteracao((Date) retornoConsulta[93]);
					// ligacaoAgua.setNumeroLacre((String) retornoConsulta[194]);
					//
					// if(retornoConsulta[196] != null){
					// ligacaoAgua.setDataLigacao((Date) retornoConsulta[196]);
					// }
					//
					// if (retornoConsulta[19] != null) {
					//
					// HidrometroInstalacaoHistorico hidroInstHist = new
					// HidrometroInstalacaoHistorico();
					// hidroInstHist
					// .setId((Integer) retornoConsulta[19]);
					// hidroInstHist
					// .setIndicadorExistenciaCavalete((Short) retornoConsulta[48]);
					// hidroInstHist
					// .setNumeroLeituraInstalacao((Integer) retornoConsulta[49]);
					// hidroInstHist
					// .setDataInstalacao((Date) retornoConsulta[73]);
					// hidroInstHist
					// .setDataImplantacaoSistema((Date) retornoConsulta[74]);
					// hidroInstHist
					// .setDataRetirada((Date) retornoConsulta[81]);
					// hidroInstHist
					// .setNumeroSelo((String) retornoConsulta[82]);
					// hidroInstHist
					// .setIndicadorTrocaProtecao((Short) retornoConsulta[200]);
					// hidroInstHist
					// .setIndicadorTrocaRegistro((Short) retornoConsulta[201]);
					//
					// LigacaoAgua ligacaoAguaAgua = new LigacaoAgua();
					// ligacaoAguaAgua
					// .setId((Integer) retornoConsulta[94]);
					//
					// hidroInstHist.setLigacaoAgua(ligacaoAguaAgua);
					//
					// HidrometroLocalInstalacao hidrometroLocalInstalacao = new
					// HidrometroLocalInstalacao();
					// hidrometroLocalInstalacao
					// .setId((Integer) retornoConsulta[46]);
					// hidroInstHist
					// .setHidrometroLocalInstalacao(hidrometroLocalInstalacao);
					//
					// HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
					// hidrometroProtecao
					// .setId((Integer) retornoConsulta[47]);
					// hidroInstHist
					// .setHidrometroProtecao(hidrometroProtecao);
					//
					// if (retornoConsulta[30] != null) {
					// Hidrometro hidrometro = new Hidrometro();
					// hidrometro.setId((Integer) retornoConsulta[30]);
					// hidrometro.setNumero((String) retornoConsulta[31]);
					// hidrometro.setDataAquisicao((Date) retornoConsulta[87]);
					// hidrometro.setAnoFabricacao((Short) retornoConsulta[90]);
					// hidrometro.setNumeroLeituraAcumulada((Integer) retornoConsulta[91]);
					// HidrometroSituacao hidrometroSituacao = new HidrometroSituacao();
					// hidrometroSituacao.setId((Integer) retornoConsulta[71]);
					// hidrometroSituacao.setDescricao((String) retornoConsulta[72]);
					// hidrometro.setHidrometroSituacao(hidrometroSituacao);
					// hidroInstHist.setHidrometro(hidrometro);
					// hidroInstHist.setNumeroHidrometro(hidrometro.getNumero());
					// }
					//
					// if (retornoConsulta[32] != null) {
					// MedicaoTipo medicaoTipo = new MedicaoTipo();
					// medicaoTipo
					// .setId((Integer) retornoConsulta[32]);
					// medicaoTipo
					// .setDescricao((String) retornoConsulta[33]);
					//
					// hidroInstHist.setMedicaoTipo(medicaoTipo);
					// }
					//
					// ligacaoAgua
					// .setHidrometroInstalacaoHistorico(hidroInstHist);
					// }
					//
					// if (retornoConsulta[20] != null) {
					// SupressaoTipo supressaoTipo = new SupressaoTipo();
					// supressaoTipo
					// .setId((Integer) retornoConsulta[20]);
					// supressaoTipo
					// .setDescricao((String) retornoConsulta[21]);
					//
					// ligacaoAgua.setSupressaoTipo(supressaoTipo);
					// }
					//
					// if (retornoConsulta[22] != null) {
					// SupressaoMotivo supressaoMotivo = new SupressaoMotivo();
					// supressaoMotivo
					// .setId((Integer) retornoConsulta[22]);
					// supressaoMotivo
					// .setDescricao((String) retornoConsulta[23]);
					//
					// ligacaoAgua.setSupressaoMotivo(supressaoMotivo);
					// }
					//
					// if (retornoConsulta[24] != null) {
					// LigacaoAguaDiametro ligacaoAguaDiametro = new LigacaoAguaDiametro();
					// ligacaoAguaDiametro
					// .setId((Integer) retornoConsulta[24]);
					// ligacaoAguaDiametro
					// .setDescricao((String) retornoConsulta[25]);
					//
					// ligacaoAgua
					// .setLigacaoAguaDiametro(ligacaoAguaDiametro);
					// }
					//
					// if (retornoConsulta[26] != null) {
					// LigacaoAguaMaterial ligacaoAguaMaterial = new LigacaoAguaMaterial();
					// ligacaoAguaMaterial
					// .setId((Integer) retornoConsulta[26]);
					// ligacaoAguaMaterial
					// .setDescricao((String) retornoConsulta[27]);
					//
					// ligacaoAgua
					// .setLigacaoAguaMaterial(ligacaoAguaMaterial);
					// }
					//
					// if (retornoConsulta[28] != null) {
					// LigacaoAguaPerfil ligacaoAguaPerfil = new LigacaoAguaPerfil();
					// ligacaoAguaPerfil
					// .setId((Integer) retornoConsulta[28]);
					// ligacaoAguaPerfil
					// .setDescricao((String) retornoConsulta[29]);
					//
					// ligacaoAgua
					// .setLigacaoAguaPerfil(ligacaoAguaPerfil);
					// }
					//
					// if (retornoConsulta[75] != null) {
					// CorteTipo corteTipo = new CorteTipo();
					// corteTipo.setId((Integer) retornoConsulta[75]);
					// corteTipo
					// .setDescricao((String) retornoConsulta[76]);
					//
					// ligacaoAgua.setCorteTipo(corteTipo);
					// }
					//
					// if (retornoConsulta[77] != null) {
					// MotivoCorte motivoCorte = new MotivoCorte();
					// motivoCorte
					// .setId((Integer) retornoConsulta[77]);
					// motivoCorte
					// .setDescricao((String) retornoConsulta[78]);
					//
					// ligacaoAgua.setMotivoCorte(motivoCorte);
					// }
					//
					// if (retornoConsulta[99] != null) {
					// RamalLocalInstalacao ramalLocalInstalacao = new RamalLocalInstalacao();
					// ramalLocalInstalacao
					// .setId((Integer) retornoConsulta[99]);
					// ramalLocalInstalacao
					// .setDescricao((String) retornoConsulta[100]);
					//
					// ligacaoAgua
					// .setRamalLocalInstalacao(ramalLocalInstalacao);
					// }
					//
					// imovel.setLigacaoAgua(ligacaoAgua);
					// }
					//
					// if (retornoConsulta[12] != null) {
					// LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
					// ligacaoAguaSituacao
					// .setId((Integer) retornoConsulta[12]);
					// ligacaoAguaSituacao
					// .setDescricao((String) retornoConsulta[13]);
					//
					// imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
					// }
					//
					// if (retornoConsulta[14] != null) {
					// LigacaoEsgoto ligacaoEsgoto = new LigacaoEsgoto();
					// ligacaoEsgoto.setId((Integer) retornoConsulta[14]);
					// ligacaoEsgoto
					// .setIndicadorCaixaGordura((Short) retornoConsulta[103]);
					// ligacaoEsgoto
					// .setConsumoMinimo((Integer) retornoConsulta[64]);
					//
					// if(retornoConsulta[197] != null){
					// ligacaoEsgoto.setDataLigacao((Date) retornoConsulta[197]);
					// }
					//
					// LigacaoEsgotoDiametro ligacaoEsgotoDiametro = new LigacaoEsgotoDiametro();
					// ligacaoEsgotoDiametro
					// .setId((Integer) retornoConsulta[39]);
					// ligacaoEsgotoDiametro
					// .setDescricao((String) retornoConsulta[40]);
					// ligacaoEsgoto
					// .setLigacaoEsgotoDiametro(ligacaoEsgotoDiametro);
					//
					// LigacaoEsgotoMaterial ligacaoEsgotoMaterial = new LigacaoEsgotoMaterial();
					// ligacaoEsgotoMaterial
					// .setId((Integer) retornoConsulta[41]);
					// ligacaoEsgotoMaterial
					// .setDescricao((String) retornoConsulta[42]);
					// ligacaoEsgoto
					// .setLigacaoEsgotoMaterial(ligacaoEsgotoMaterial);
					//
					// LigacaoEsgotoPerfil ligacaoEsgotoPerfil = new LigacaoEsgotoPerfil();
					// ligacaoEsgotoPerfil
					// .setId((Integer) retornoConsulta[43]);
					// ligacaoEsgotoPerfil
					// .setDescricao((String) retornoConsulta[44]);
					// ligacaoEsgoto
					// .setLigacaoEsgotoPerfil(ligacaoEsgotoPerfil);
					//
					// ligacaoEsgoto
					// .setPercentualAguaConsumidaColetada((BigDecimal) retornoConsulta[45]);
					//
					// imovel.setLigacaoEsgoto(ligacaoEsgoto);
					// }
					//
					// if (retornoConsulta[15] != null) {
					// LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					// ligacaoEsgotoSituacao
					// .setId((Integer) retornoConsulta[15]);
					// ligacaoEsgotoSituacao
					// .setDescricao((String) retornoConsulta[16]);
					//
					// imovel
					// .setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
					// }
					//
					// if (retornoConsulta[17] != null) {
					// ConsumoTarifa consumoTarifa = new ConsumoTarifa();
					// consumoTarifa.setId((Integer) retornoConsulta[17]);
					// consumoTarifa
					// .setDescricao((String) retornoConsulta[18]);
					//
					// imovel.setConsumoTarifa(consumoTarifa);
					// }
					//
					// if (retornoConsulta[205] != null) {
					// imovel.setNumeroPontosUtilizacao((Short) retornoConsulta[205]);
					// }
					//
					// registro.setImovel(imovel);
					// }
					// }
					//
					// ServicoTipo servicoTipo = new ServicoTipo();
					// servicoTipo.setId((Integer) retornoConsulta[2]);
					// servicoTipo.setDescricao((String) retornoConsulta[3]);
					// servicoTipo.setTempoMedioExecucao((Short) retornoConsulta[37]);
					// servicoTipo.setIndicadorPermiteAlterarValor((Short) retornoConsulta[192]);
					// servicoTipo.setIndicadorCobrarJuros((Short) retornoConsulta[193]);
					//
					// if (retornoConsulta[34] != null) {
					// DebitoTipo debitoTipo = new DebitoTipo();
					//
					// debitoTipo.setId((Integer) retornoConsulta[34]);
					// debitoTipo.setDescricao((String) retornoConsulta[35]);
					// servicoTipo.setDebitoTipo(debitoTipo);
					// }
					//
					// if (retornoConsulta[206] != null) {
					// OrdemServicoLayout osLayout = new OrdemServicoLayout();
					// osLayout.setId((Integer)retornoConsulta[206]);
					// osLayout.setNomeRelatorio((String) retornoConsulta[207]);
					// osLayout.setNomeClasse((String) retornoConsulta[208]);
					//
					// if (retornoConsulta[211] != null){
					// osLayout.setIndicadorDoisPorPagina((Integer)retornoConsulta[211]);
					// }
					//
					// servicoTipo.setOrdemServicoLayout(osLayout);
					// }
					//
					// ordemServico.setServicoTipo(servicoTipo);
					//
					// // Colocado por Raphael Rossiter em 23/09/2006
					// if (retornoConsulta[67] != null) {
					// AtendimentoMotivoEncerramento amen = new AtendimentoMotivoEncerramento();
					// amen.setId((Integer) retornoConsulta[67]);
					// amen.setIndicadorExecucao(((Short) retornoConsulta[68])
					// .shortValue());
					// amen.setDescricao((String) retornoConsulta[101]);
					// ordemServico.setAtendimentoMotivoEncerramento(amen);
					// }
					//
					// if (retornoConsulta[97] != null) {
					// ServicoTipoPrioridade servicoTipoPrioridade = new ServicoTipoPrioridade();
					// servicoTipoPrioridade.setId((Integer) retornoConsulta[97]);
					// ordemServico
					// .setServicoTipoPrioridadeOriginal(servicoTipoPrioridade);
					// }
					//
					// if (retornoConsulta[98] != null) {
					// ServicoTipoPrioridade servicoTipoPrioridade = new ServicoTipoPrioridade();
					// servicoTipoPrioridade.setId((Integer) retornoConsulta[98]);
					// ordemServico
					// .setServicoTipoPrioridadeAtual(servicoTipoPrioridade);
					// }
					//
					// ordemServico.setRegistroAtendimento(registro);
					// ordemServico
					// .setIndicadorComercialAtualizado((Short) retornoConsulta[86]);
					//
					//
					//
					// //Imóvel da OS - Colocado por Raphael Rossiter em 16/01/2007
					// if (retornoConsulta[104] != null){
					//
					// Imovel imovelOS = new Imovel();
					// imovelOS.setId((Integer) retornoConsulta[104]);
					//
					// Localidade localidadeOS = new Localidade();
					// localidadeOS.setId((Integer)retornoConsulta[105]);
					//
					// SetorComercial setorComercialOS = new SetorComercial();
					// setorComercialOS.setId((Integer)retornoConsulta[106]);
					// setorComercialOS.setCodigo((Integer)retornoConsulta[107]);
					//
					// Quadra quadraOS = new Quadra();
					// quadraOS.setId((Integer)retornoConsulta[108]);
					// quadraOS.setNumeroQuadra((Integer)retornoConsulta[109]);
					// quadraOS.setIndicadorRedeAgua((Short) retornoConsulta[148]);
					// quadraOS.setIndicadorRedeEsgoto((Short) retornoConsulta[158]);
					//
					// if (retornoConsulta[221] != null){
					// ImovelPerfil imovelPerfil = new ImovelPerfil();
					// imovelPerfil.setId((Integer) retornoConsulta[221]);
					//
					// imovelOS.setImovelPerfil(imovelPerfil);
					// }
					//
					// imovelOS.setLocalidade(localidadeOS);
					// imovelOS.setSetorComercial(setorComercialOS);
					// imovelOS.setQuadra(quadraOS);
					//
					// imovelOS.setLote((Short)retornoConsulta[110]);
					// imovelOS.setSubLote((Short)retornoConsulta[111]);
					//
					//
					// //==============================================================================================
					//
					// imovelOS.setIndicadorExclusao((Short) retornoConsulta[136]);
					// imovelOS.setNumeroImovel((String) retornoConsulta[161]);
					// imovelOS.setUltimaAlteracao((Date) retornoConsulta[188]);
					//
					//
					// if (retornoConsulta[149] != null) {
					//
					// HidrometroInstalacaoHistorico imovHidrometroInstalacaoHist = new
					// HidrometroInstalacaoHistorico();
					//
					// imovHidrometroInstalacaoHist
					// .setId((Integer) retornoConsulta[149]);
					// imovHidrometroInstalacaoHist
					// .setIndicadorExistenciaCavalete((Short) retornoConsulta[150]);
					// imovHidrometroInstalacaoHist
					// .setNumeroLeituraInstalacao((Integer) retornoConsulta[151]);
					// imovHidrometroInstalacaoHist
					// .setNumeroSelo((String) retornoConsulta[176]);
					//
					// LigacaoAgua ligacaoAgua = new LigacaoAgua();
					// ligacaoAgua.setId((Integer) retornoConsulta[183]);
					//
					// imovHidrometroInstalacaoHist
					// .setLigacaoAgua(ligacaoAgua);
					//
					// HidrometroLocalInstalacao imovHidrometroLocalInstalacao = new
					// HidrometroLocalInstalacao();
					//
					// imovHidrometroLocalInstalacao
					// .setId((Integer) retornoConsulta[152]);
					// imovHidrometroInstalacaoHist
					// .setHidrometroLocalInstalacao(imovHidrometroLocalInstalacao);
					//
					// HidrometroProtecao imovHidrometroProtecao = new HidrometroProtecao();
					//
					// imovHidrometroProtecao
					// .setId((Integer) retornoConsulta[153]);
					// imovHidrometroInstalacaoHist
					// .setHidrometroProtecao(imovHidrometroProtecao);
					//
					// if (retornoConsulta[154] != null) {
					//
					// Hidrometro imovHidrometro = new Hidrometro();
					//
					// imovHidrometro
					// .setId((Integer) retornoConsulta[154]);
					// imovHidrometro
					// .setNumero((String) retornoConsulta[155]);
					// imovHidrometro
					// .setDataAquisicao((Date) retornoConsulta[177]);
					// imovHidrometro
					// .setAnoFabricacao((Short) retornoConsulta[178]);
					// imovHidrometro
					// .setNumeroLeituraAcumulada((Integer) retornoConsulta[181]);
					//
					// imovHidrometroInstalacaoHist
					// .setHidrometro(imovHidrometro);
					//
					// HidrometroSituacao hidrometroSituacao = new HidrometroSituacao();
					//
					// hidrometroSituacao
					// .setId((Integer) retornoConsulta[162]);
					// hidrometroSituacao
					// .setDescricao((String) retornoConsulta[163]);
					// imovHidrometro
					// .setHidrometroSituacao(hidrometroSituacao);
					// }
					//
					// if (retornoConsulta[156] != null) {
					//
					// MedicaoTipo imovMedicaoTipo = new MedicaoTipo();
					//
					// imovMedicaoTipo
					// .setId((Integer) retornoConsulta[156]);
					// imovMedicaoTipo
					// .setDescricao((String) retornoConsulta[157]);
					//
					// imovHidrometroInstalacaoHist
					// .setMedicaoTipo(imovMedicaoTipo);
					// }
					//
					// imovelOS.setHidrometroInstalacaoHistorico(imovHidrometroInstalacaoHist);
					//
					// }
					//
					// if (retornoConsulta[113] != null) {
					//
					// LigacaoAgua ligacaoAgua = new LigacaoAgua();
					//
					// ligacaoAgua.setId((Integer) retornoConsulta[113]);
					//
					// ligacaoAgua.setNumeroConsumoMinimoAgua((Integer) retornoConsulta[160]);
					// ligacaoAgua.setNumeroSeloCorte((Integer) retornoConsulta[172]);
					// ligacaoAgua.setNumeroSeloSupressao((Integer) retornoConsulta[173]);
					// ligacaoAgua.setUltimaAlteracao((Date) retornoConsulta[182]);
					// ligacaoAgua.setNumeroLacre((String) retornoConsulta[195]);
					//
					// if(retornoConsulta[198] != null){
					// ligacaoAgua.setDataLigacao((Date) retornoConsulta[198]);
					// }
					//
					// if (retornoConsulta[121] != null) {
					//
					// HidrometroInstalacaoHistorico hidroInstHist = new
					// HidrometroInstalacaoHistorico();
					// hidroInstHist
					// .setId((Integer) retornoConsulta[121]);
					// hidroInstHist
					// .setIndicadorExistenciaCavalete((Short) retornoConsulta[146]);
					// hidroInstHist
					// .setNumeroLeituraInstalacao((Integer) retornoConsulta[147]);
					// hidroInstHist
					// .setDataInstalacao((Date) retornoConsulta[166]);
					// hidroInstHist
					// .setDataImplantacaoSistema((Date) retornoConsulta[167]);
					// hidroInstHist
					// .setDataRetirada((Date) retornoConsulta[174]);
					// hidroInstHist
					// .setNumeroSelo((String) retornoConsulta[175]);
					//
					// LigacaoAgua ligacaoAguaAgua = new LigacaoAgua();
					//
					// ligacaoAguaAgua
					// .setId((Integer) retornoConsulta[183]);
					//
					// hidroInstHist.setLigacaoAgua(ligacaoAguaAgua);
					//
					// HidrometroLocalInstalacao hidrometroLocalInstalacao = new
					// HidrometroLocalInstalacao();
					//
					// hidrometroLocalInstalacao
					// .setId((Integer) retornoConsulta[144]);
					// hidroInstHist
					// .setHidrometroLocalInstalacao(hidrometroLocalInstalacao);
					//
					// HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
					//
					// hidrometroProtecao
					// .setId((Integer) retornoConsulta[145]);
					// hidroInstHist
					// .setHidrometroProtecao(hidrometroProtecao);
					//
					// if (retornoConsulta[132] != null) {
					//
					// Hidrometro hidrometro = new Hidrometro();
					//
					// hidrometro
					// .setId((Integer) retornoConsulta[132]);
					// hidrometro
					// .setNumero((String) retornoConsulta[133]);
					// hidrometro
					// .setDataAquisicao((Date) retornoConsulta[189]);
					// hidrometro
					// .setAnoFabricacao((Short) retornoConsulta[179]);
					// hidrometro
					// .setNumeroLeituraAcumulada((Integer) retornoConsulta[180]);
					//
					// HidrometroSituacao hidrometroSituacao = new HidrometroSituacao();
					//
					// hidrometroSituacao
					// .setId((Integer) retornoConsulta[164]);
					// hidrometroSituacao
					// .setDescricao((String) retornoConsulta[165]);
					// hidrometro
					// .setHidrometroSituacao(hidrometroSituacao);
					//
					// hidroInstHist.setHidrometro(hidrometro);
					// }
					//
					// if (retornoConsulta[134] != null) {
					//
					// MedicaoTipo medicaoTipo = new MedicaoTipo();
					//
					// medicaoTipo
					// .setId((Integer) retornoConsulta[134]);
					// medicaoTipo
					// .setDescricao((String) retornoConsulta[135]);
					//
					// hidroInstHist.setMedicaoTipo(medicaoTipo);
					// }
					//
					// ligacaoAgua
					// .setHidrometroInstalacaoHistorico(hidroInstHist);
					// }
					//
					// if (retornoConsulta[122] != null) {
					//
					// SupressaoTipo supressaoTipo = new SupressaoTipo();
					//
					// supressaoTipo
					// .setId((Integer) retornoConsulta[122]);
					// supressaoTipo
					// .setDescricao((String) retornoConsulta[123]);
					//
					// ligacaoAgua.setSupressaoTipo(supressaoTipo);
					// }
					//
					// if (retornoConsulta[124] != null) {
					//
					// SupressaoMotivo supressaoMotivo = new SupressaoMotivo();
					//
					// supressaoMotivo
					// .setId((Integer) retornoConsulta[124]);
					// supressaoMotivo
					// .setDescricao((String) retornoConsulta[125]);
					//
					// ligacaoAgua.setSupressaoMotivo(supressaoMotivo);
					// }
					//
					// if (retornoConsulta[126] != null) {
					//
					// LigacaoAguaDiametro ligacaoAguaDiametro = new LigacaoAguaDiametro();
					//
					// ligacaoAguaDiametro
					// .setId((Integer) retornoConsulta[126]);
					// ligacaoAguaDiametro
					// .setDescricao((String) retornoConsulta[127]);
					//
					// ligacaoAgua
					// .setLigacaoAguaDiametro(ligacaoAguaDiametro);
					// }
					//
					// if (retornoConsulta[128] != null) {
					//
					// LigacaoAguaMaterial ligacaoAguaMaterial = new LigacaoAguaMaterial();
					//
					// ligacaoAguaMaterial
					// .setId((Integer) retornoConsulta[128]);
					// ligacaoAguaMaterial
					// .setDescricao((String) retornoConsulta[129]);
					//
					// ligacaoAgua
					// .setLigacaoAguaMaterial(ligacaoAguaMaterial);
					// }
					//
					// if (retornoConsulta[130] != null) {
					//
					// LigacaoAguaPerfil ligacaoAguaPerfil = new LigacaoAguaPerfil();
					//
					// ligacaoAguaPerfil
					// .setId((Integer) retornoConsulta[130]);
					// ligacaoAguaPerfil
					// .setDescricao((String) retornoConsulta[131]);
					//
					// ligacaoAgua
					// .setLigacaoAguaPerfil(ligacaoAguaPerfil);
					// }
					//
					// if (retornoConsulta[168] != null) {
					//
					// CorteTipo corteTipo = new CorteTipo();
					//
					// corteTipo.setId((Integer) retornoConsulta[168]);
					//
					// corteTipo
					// .setDescricao((String) retornoConsulta[169]);
					//
					// ligacaoAgua.setCorteTipo(corteTipo);
					// }
					//
					// if (retornoConsulta[170] != null) {
					//
					// MotivoCorte motivoCorte = new MotivoCorte();
					//
					// motivoCorte
					// .setId((Integer) retornoConsulta[170]);
					// motivoCorte
					// .setDescricao((String) retornoConsulta[171]);
					//
					// ligacaoAgua.setMotivoCorte(motivoCorte);
					// }
					//
					// if (retornoConsulta[185] != null) {
					//
					// RamalLocalInstalacao ramalLocalInstalacao = new RamalLocalInstalacao();
					//
					// ramalLocalInstalacao
					// .setId((Integer) retornoConsulta[185]);
					// ramalLocalInstalacao
					// .setDescricao((String) retornoConsulta[186]);
					//
					// ligacaoAgua
					// .setRamalLocalInstalacao(ramalLocalInstalacao);
					// }
					//
					// imovelOS.setLigacaoAgua(ligacaoAgua);
					//
					// }
					//
					// if (retornoConsulta[114] != null) {
					//
					// LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
					//
					// ligacaoAguaSituacao
					// .setId((Integer) retornoConsulta[114]);
					// ligacaoAguaSituacao
					// .setDescricao((String) retornoConsulta[115]);
					//
					// imovelOS.setLigacaoAguaSituacao(ligacaoAguaSituacao);
					// }
					//
					// if (retornoConsulta[116] != null) {
					//
					// LigacaoEsgoto ligacaoEsgoto = new LigacaoEsgoto();
					//
					// ligacaoEsgoto.setId((Integer) retornoConsulta[116]);
					//
					// ligacaoEsgoto
					// .setIndicadorCaixaGordura((Short) retornoConsulta[187]);
					// ligacaoEsgoto
					// .setConsumoMinimo((Integer) retornoConsulta[159]);
					//
					// if(retornoConsulta[199] != null){
					// ligacaoEsgoto.setDataLigacao((Date) retornoConsulta[199]);
					// }
					//
					// LigacaoEsgotoDiametro ligacaoEsgotoDiametro = new LigacaoEsgotoDiametro();
					//
					// ligacaoEsgotoDiametro
					// .setId((Integer) retornoConsulta[137]);
					// ligacaoEsgotoDiametro
					// .setDescricao((String) retornoConsulta[138]);
					// ligacaoEsgoto
					// .setLigacaoEsgotoDiametro(ligacaoEsgotoDiametro);
					//
					// LigacaoEsgotoMaterial ligacaoEsgotoMaterial = new LigacaoEsgotoMaterial();
					//
					// ligacaoEsgotoMaterial
					// .setId((Integer) retornoConsulta[139]);
					// ligacaoEsgotoMaterial
					// .setDescricao((String) retornoConsulta[140]);
					// ligacaoEsgoto
					// .setLigacaoEsgotoMaterial(ligacaoEsgotoMaterial);
					//
					// LigacaoEsgotoPerfil ligacaoEsgotoPerfil = new LigacaoEsgotoPerfil();
					// ligacaoEsgotoPerfil
					// .setId((Integer) retornoConsulta[141]);
					// ligacaoEsgotoPerfil
					// .setDescricao((String) retornoConsulta[142]);
					// ligacaoEsgoto
					// .setLigacaoEsgotoPerfil(ligacaoEsgotoPerfil);
					//
					// ligacaoEsgoto
					// .setPercentualAguaConsumidaColetada((BigDecimal) retornoConsulta[143]);
					//
					// imovelOS.setLigacaoEsgoto(ligacaoEsgoto);
					// }
					//
					// if (retornoConsulta[117] != null) {
					//
					// LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					//
					// ligacaoEsgotoSituacao
					// .setId((Integer) retornoConsulta[117]);
					// ligacaoEsgotoSituacao
					// .setDescricao((String) retornoConsulta[118]);
					//
					// imovelOS
					// .setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
					// }
					//
					// if (retornoConsulta[119] != null) {
					//
					// ConsumoTarifa consumoTarifa = new ConsumoTarifa();
					//
					// consumoTarifa.setId((Integer) retornoConsulta[119]);
					//
					// consumoTarifa
					// .setDescricao((String) retornoConsulta[120]);
					//
					// imovelOS.setConsumoTarifa(consumoTarifa);
					// }
					//
					// ordemServico.setImovel(imovelOS);
					// }
					//
					// if (retornoConsulta[212] != null) {
					//
					// CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = new
					// CobrancaAcaoAtividadeCronograma((Integer)retornoConsulta[212]);
					//
					// if (retornoConsulta[213] != null){
					// CobrancaAcaoCronograma cobrancaAcaoCronograma = new
					// CobrancaAcaoCronograma((Integer)retornoConsulta[213]);
					//
					// if (retornoConsulta[214] != null){
					// CobrancaAcao cobrancaAcao = new CobrancaAcao((Integer)retornoConsulta[214]);
					//
					// if (retornoConsulta[215] != null){
					// cobrancaAcao.setNumeroDiasValidade((Short)retornoConsulta[215]);
					// }
					//
					// cobrancaAcaoCronograma.setCobrancaAcao(cobrancaAcao);
					// }
					//
					// cobrancaAcaoAtividadeCronograma.setCobrancaAcaoCronograma(cobrancaAcaoCronograma);
					// }
					//
					// ordemServico.setCobrancaAcaoAtividadeCronograma(cobrancaAcaoAtividadeCronograma);
					// }
					//
					// if (retornoConsulta[216] != null) {
					//
					// CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = new
					// CobrancaAcaoAtividadeComando((Integer)retornoConsulta[216]);
					//
					// if (retornoConsulta[217] != null){
					// CobrancaAcao cobrancaAcao = new CobrancaAcao((Integer)retornoConsulta[217]);
					//
					// if (retornoConsulta[218] != null){
					// cobrancaAcao.setNumeroDiasValidade((Short)retornoConsulta[218]);
					// }
					//
					// cobrancaAcaoAtividadeComando.setCobrancaAcao(cobrancaAcao);
					// }
					//
					// ordemServico.setCobrancaAcaoAtividadeComando(cobrancaAcaoAtividadeComando);
					// }
					// }
					//
					// if (ordemServico != null && ordemServico.getImovel() != null) {
					// if (retornoConsulta[219] != null){
					// LogradouroBairro logradouroBairro = new LogradouroBairro();
					// logradouroBairro.setId((Integer)retornoConsulta[219]);
					// ordemServico.getImovel().setLogradouroBairro(logradouroBairro);
					// }
					// }
					this.inicializarObjetosOS(ordemServico);
					ordensServicos.add(ordemServico);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return ordensServicos;
	}

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Seleciona ordem de serviço por codigo do cliente atraves do RASolicitante
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param codigoCliente
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorCodigoClienteRASolicitante(Integer codigoCliente) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select os.orse_id as idOS " + "from ordem_servico os, " + "registro_atendimento_solctt ras "
							+ "where os.rgat_id = ras.rgat_id " + "and ras.clie_id = :codigoCliente ";

			retornoConsulta = session.createSQLQuery(consulta).addScalar("idOS", Hibernate.INTEGER)
							.setInteger("codigoCliente", codigoCliente).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Seleciona ordem de serviço por codigo do cliente atraves do cliente
	 * imovel
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param codigoCliente
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorCodigoCliente(Integer codigoCliente) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select os.orse_id as idOS " + "from ordem_servico os, " + "registro_atendimento ra, "
							+ "cliente_imovel clienteImov " + "where os.rgat_id = ra.rgat_id " + "and ra.imov_id = clienteImov.imov_id "
							+ "and clienteImov.clie_id = :codigoCliente " + "and clienteImov.clim_dtrelacaofim <> null ";

			retornoConsulta = session.createSQLQuery(consulta).addScalar("idOS", Hibernate.INTEGER)
							.setInteger("codigoCliente", codigoCliente).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Seleciona ordem de serviço por codigo do cliente atraves do documento
	 * cobrança
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param codigoCliente
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaOSPorCodigoClienteCobrancaDocumento(Integer codigoCliente) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select os.orse_id as idOS " + "from ordem_servico os, " + "cobranca_documento cob, "
							+ "cliente_imovel clienteImov " + "where os.cbdo_id = cob.cbdo_id " + "and cob.imov_id = clienteImov.imov_id "
							+ "and clienteImov.clie_id = :codigoCliente " + "and clienteImov.clim_dtrelacaofim <> null ";

			retornoConsulta = session.createSQLQuery(consulta).addScalar("idOS", Hibernate.INTEGER)
							.setInteger("codigoCliente", codigoCliente).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0454] Obter Descrição da situação da OS
	 * Verificar a situação(ORSE_CDSITUACAO) da ordem de serviço
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idOS
	 * @throws ControladorException
	 */
	public Short verificaSituacaoOS(Integer idOrdemServico) throws ErroRepositorioException{

		Short retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select orse.situacao " + "from OrdemServico orse " + "where orse.id = :idOrdemServico ";

			retorno = (Short) session.createQuery(consulta).setInteger("idOrdemServico", idOrdemServico).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0441] Consultar Dados da Ordem de Serviço
	 * 
	 * @author Leonardo Regis
	 * @date 15/08/2006
	 * @param idOrdemServico
	 * @exception ErroRepositorioExceptions
	 */
	public OrdemServico consultarDadosOS(Integer idOrdemServico) throws ErroRepositorioException{

		OrdemServico retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT orse.id, " // 00
							+ " orse.situacao, " // 01
							+ " rgat.id, " // 02
							+ " rgat.codigoSituacao, " // 03
							+ " doc.id, " // 04
							+ " orse.dataGeracao, " // 05
							+ " osRef.id, " // 06
							+ " servTipRef.id, " // 07
							+ " servTipRef.descricao, " // 08
							+ " servTip.id, " // 09
							+ " servTip.descricao, " // 10
							+ " retTipRef.id, " // 11
							+ " retTipRef.descricao, " // 12
							+ " orse.observacao, " // 13
							+ " orse.valorOriginal, " // 14
							+ " orse.valorAtual, " // 15
							+ " servTipPriOri.id, " // 16
							+ " servTipPriOri.descricao, " // 17
							+ " servTipPriAtu.id, " // 18
							+ " servTipPriAtu.descricao, " // 19
							+ " orse.dataEmissao, " // 20
							+ " orse.dataEncerramento, " // 21
							+ " orse.descricaoParecerEncerramento, " // 22
							+ " orse.areaPavimento, " // 23
							+ " orse.indicadorComercialAtualizado, " // 24
							+ " orse.percentualCobranca, " // 25
							+ " servNaoCobMot.id, " // 26
							+ " servNaoCobMot.descricao, " // 27
							+ " osRef.situacao, "// 28
							+ " servTipOsReferencia.descricao, "// 29
							+ " servTipReferencia.id,"// 30
							+ " servTip.indicadorPavimento, "// 31
							+ " servTipReferencia.descricao, "// 32
							+ " orse.ultimaAlteracao, "// 33
							+ " servTip.indicadorAtualizaComercial, "// 34
							+ " servTip.indicadorVistoria, "// 35
							+ " orse.indicadorProgramada, "// 36
							+ " servTip.indicadorFiscalizacaoInfracao, "// 37
							+ " fiscSit.id,"// 38
							+ " doc.imovel.id," // 39
							+ " imov.id," // 40
							+ " loca.id," // 41
							+ " stcm.codigo," // 42
							+ " qdra.numeroQuadra," // 43
							+ " imov.lote," // 44
							+ " imov.subLote," // 45
							+ " rota.codigo, " // 46
							+ " imov.numeroSequencialRota, " // 47
							+ " amen.descricao, " // 48
							+ " servTip.indicadorRedeRamalAgua, " // 49
							+ " servTip.indicadorRedeRamalEsgoto, " // 50
							+ " servTip.indicadorVala, " // 51
							+ " servTip.indicadorCausaOcorrencia, " // 52
							+ " servTip.indicadorFiscalizacaoSituacao, "// 53
							+ " orse.dataExecucao, " // 54
							+ " servTip.indicadorAfericaoHidrometro, " // 55
							+ " orre.id, " // 56
							+ " orse.valorHorasTrabalhadas, " // 57
							+ " orse.valorMateriais, " // 58
							+ " rgat.dataPrevistaAtual " // 59
							+ " FROM OrdemServico orse "
							+ " LEFT JOIN orse.ordemServicoReparo orre "
							+ " LEFT JOIN orse.registroAtendimento rgat "
							+ " LEFT JOIN orse.cobrancaDocumento doc "
							+ " LEFT JOIN orse.servicoTipo servTip "
							+ " LEFT JOIN orse.servicoTipoPrioridadeOriginal servTipPriOri "
							+ " LEFT JOIN orse.servicoTipoPrioridadeAtual servTipPriAtu "
							+ " LEFT JOIN orse.servicoNaoCobrancaMotivo servNaoCobMot "
							+ " LEFT JOIN orse.osReferidaRetornoTipo retTipRef "
							+ " LEFT JOIN orse.atendimentoMotivoEncerramento amen"
							+ " LEFT JOIN orse.osReferencia osRef "
							+ " LEFT JOIN osRef.servicoTipo servTipOsReferencia "
							+ " LEFT JOIN orse.servicoTipoReferencia servTipRef "
							+ " LEFT JOIN servTip.servicoTipoReferencia servTipReferencia "
							+ " LEFT JOIN orse.fiscalizacaoSituacao fiscSit "
							+ " LEFT JOIN orse.imovel imov"
							+ " LEFT JOIN imov.localidade loca" + " LEFT JOIN imov.setorComercial stcm"
							+ " LEFT JOIN imov.quadra qdra"
							+ " LEFT JOIN imov.rota rota" + " WHERE orse.id = :idOrdemServico ";

			Object[] retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idOrdemServico", idOrdemServico)
							.setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){
				retorno = new OrdemServico();

				retorno.setId((Integer) retornoConsulta[0]);
				retorno.setSituacao((Short) retornoConsulta[1]);
				RegistroAtendimento registroAtendimento = null;
				if((Integer) retornoConsulta[2] != null){
					registroAtendimento = new RegistroAtendimento();
					registroAtendimento.setId((Integer) retornoConsulta[2]);
					registroAtendimento.setCodigoSituacao((Short) retornoConsulta[3]);
					registroAtendimento.setDataPrevistaAtual((Date) retornoConsulta[59]);
				}
				retorno.setRegistroAtendimento(registroAtendimento);
				CobrancaDocumento cobranca = null;
				if((Integer) retornoConsulta[4] != null){
					cobranca = new CobrancaDocumento();
					cobranca.setId((Integer) retornoConsulta[4]);
					Imovel imovel = new Imovel();
					imovel.setId((Integer) retornoConsulta[39]);
					cobranca.setImovel(imovel);
				}
				retorno.setCobrancaDocumento(cobranca);
				retorno.setDataGeracao((Date) retornoConsulta[5]);
				OrdemServico osReferencia = null;
				if((Integer) retornoConsulta[6] != null){
					osReferencia = new OrdemServico();
					osReferencia.setId((Integer) retornoConsulta[6]);
					osReferencia.setSituacao((Short) retornoConsulta[28]);
					if(retornoConsulta[29] != null){
						ServicoTipo servicoTipo = new ServicoTipo();
						servicoTipo.setDescricao((String) retornoConsulta[29]);
						osReferencia.setServicoTipo(servicoTipo);
					}
				}
				retorno.setOsReferencia(osReferencia);
				ServicoTipo servicoTipoOsReferencia = null;
				if((Integer) retornoConsulta[7] != null){
					servicoTipoOsReferencia = new ServicoTipo();
					servicoTipoOsReferencia.setId((Integer) retornoConsulta[7]);
					servicoTipoOsReferencia.setDescricao((String) retornoConsulta[8]);
				}
				retorno.setServicoTipoReferencia(servicoTipoOsReferencia);
				ServicoTipo servicoTipo = null;
				if((Integer) retornoConsulta[9] != null){
					servicoTipo = new ServicoTipo();
					servicoTipo.setId((Integer) retornoConsulta[9]);
					servicoTipo.setDescricao((String) retornoConsulta[10]);
					servicoTipo.setIndicadorFiscalizacaoInfracao((Short) retornoConsulta[37]);

					if(retornoConsulta[53] != null){
						servicoTipo.setIndicadorFiscalizacaoSituacao((Short) retornoConsulta[53]);
					}

					servicoTipo.setIndicadorRedeRamalAgua(((Integer) retornoConsulta[49]));
					servicoTipo.setIndicadorRedeRamalEsgoto(((Integer) retornoConsulta[50]));
					servicoTipo.setIndicadorVala(((Integer) retornoConsulta[51]));
					servicoTipo.setIndicadorCausaOcorrencia(((Integer) retornoConsulta[52]));
					if(retornoConsulta[30] != null){
						ServicoTipoReferencia servicoTipoReferencia = new ServicoTipoReferencia();
						servicoTipoReferencia.setId((Integer) retornoConsulta[30]);
						servicoTipoReferencia.setDescricao((String) retornoConsulta[32]);
						servicoTipo.setServicoTipoReferencia(servicoTipoReferencia);
					}
					if(retornoConsulta[31] != null){
						servicoTipo.setIndicadorPavimento((Short) retornoConsulta[31]);

					}
					if(retornoConsulta[34] != null){
						servicoTipo.setIndicadorAtualizaComercial((Short) retornoConsulta[34]);

					}
					if(retornoConsulta[35] != null){
						servicoTipo.setIndicadorVistoria((Short) retornoConsulta[35]);

					}

					if(retornoConsulta[55] != null){

						servicoTipo.setIndicadorAfericaoHidrometro((Short) retornoConsulta[55]);
					}
				}
				retorno.setServicoTipo(servicoTipo);
				OsReferidaRetornoTipo osReferidaRetornoTipo = null;
				if((Integer) retornoConsulta[11] != null){
					osReferidaRetornoTipo = new OsReferidaRetornoTipo();
					osReferidaRetornoTipo.setId((Integer) retornoConsulta[11]);
					osReferidaRetornoTipo.setDescricao((String) retornoConsulta[12]);
				}
				retorno.setOsReferidaRetornoTipo(osReferidaRetornoTipo);
				retorno.setObservacao((String) retornoConsulta[13]);
				retorno.setValorOriginal((BigDecimal) retornoConsulta[14]);
				retorno.setValorAtual((BigDecimal) retornoConsulta[15]);
				ServicoTipoPrioridade servicoTipoPrioridadeOriginal = null;
				if((Integer) retornoConsulta[16] != null){
					servicoTipoPrioridadeOriginal = new ServicoTipoPrioridade();
					servicoTipoPrioridadeOriginal.setId((Integer) retornoConsulta[16]);
					servicoTipoPrioridadeOriginal.setDescricao((String) retornoConsulta[17]);
				}
				retorno.setServicoTipoPrioridadeOriginal(servicoTipoPrioridadeOriginal);
				ServicoTipoPrioridade servicoTipoPrioridadeAtual = null;
				if((Integer) retornoConsulta[18] != null){
					servicoTipoPrioridadeAtual = new ServicoTipoPrioridade();
					servicoTipoPrioridadeAtual.setId((Integer) retornoConsulta[18]);
					servicoTipoPrioridadeAtual.setDescricao((String) retornoConsulta[19]);
				}
				retorno.setServicoTipoPrioridadeAtual(servicoTipoPrioridadeAtual);
				retorno.setDataEmissao((Date) retornoConsulta[20]);
				retorno.setDataEncerramento((Date) retornoConsulta[21]);
				retorno.setDescricaoParecerEncerramento((String) retornoConsulta[22]);
				retorno.setAreaPavimento((BigDecimal) retornoConsulta[23]);
				retorno.setIndicadorComercialAtualizado((Short) retornoConsulta[24]);
				retorno.setPercentualCobranca((BigDecimal) retornoConsulta[25]);
				ServicoNaoCobrancaMotivo servicoNaoCobrancaoMotivo = null;
				if((Integer) retornoConsulta[26] != null){
					servicoNaoCobrancaoMotivo = new ServicoNaoCobrancaMotivo();
					servicoNaoCobrancaoMotivo.setId((Integer) retornoConsulta[26]);
					servicoNaoCobrancaoMotivo.setDescricao((String) retornoConsulta[27]);
				}
				retorno.setServicoNaoCobrancaMotivo(servicoNaoCobrancaoMotivo);
				if(retornoConsulta[33] != null){
					retorno.setUltimaAlteracao((Date) retornoConsulta[33]);
				}

				if(retornoConsulta[36] != null){
					retorno.setIndicadorProgramada((Short) retornoConsulta[36]);
				}

				if(retornoConsulta[38] != null){
					FiscalizacaoSituacao fiscalizacaoSituacao = new FiscalizacaoSituacao();
					fiscalizacaoSituacao.setId((Integer) retornoConsulta[38]);
					retorno.setFiscalizacaoSituacao(fiscalizacaoSituacao);
				}

				if(retornoConsulta[48] != null){
					AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
					atendimentoMotivoEncerramento.setDescricao((String) retornoConsulta[48]);
					retorno.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);
				}

				// Dados do Imóvel
				if(retornoConsulta[40] != null){
					Imovel imovel = new Imovel();
					imovel.setId((Integer) retornoConsulta[40]);
					if(retornoConsulta[41] != null){
						Localidade localidade = new Localidade();
						localidade.setId((Integer) retornoConsulta[41]);

						imovel.setLocalidade(localidade);
					}
					if(retornoConsulta[42] != null){
						SetorComercial setorComercial = new SetorComercial();
						setorComercial.setCodigo((Integer) retornoConsulta[42]);
						imovel.setSetorComercial(setorComercial);
					}
					if(retornoConsulta[43] != null){
						Quadra quadra = new Quadra();
						quadra.setNumeroQuadra((Integer) retornoConsulta[43]);
						Rota rota = null;
						if(retornoConsulta[46] != null){
							rota = new Rota();
							rota.setCodigo((Short) retornoConsulta[46]);
						}
						quadra.setRota(rota);
						imovel.setQuadra(quadra);
						imovel.setRota(rota);
					}

					if(retornoConsulta[47] != null){
						imovel.setNumeroSequencialRota((Integer) retornoConsulta[47]);
					}

					imovel.setLote((Short) retornoConsulta[44]);
					imovel.setSubLote((Short) retornoConsulta[45]);
					retorno.setImovel(imovel);
				}

				if(retornoConsulta[54] != null){

					retorno.setDataExecucao((Date) retornoConsulta[54]);
				}

				OrdemServico osReparo = null;
				if((Integer) retornoConsulta[56] != null){
					osReparo = new OrdemServico();
					osReparo.setId((Integer) retornoConsulta[56]);
				}
				retorno.setOrdemServicoReparo(osReparo);
				
				if(retornoConsulta[57] != null){

					retorno.setValorHorasTrabalhadas((BigDecimal) retornoConsulta[57]);
				}

				if(retornoConsulta[58] != null){

					retorno.setValorMateriais((BigDecimal) retornoConsulta[58]);
				}
				

			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0427] Tramitar Registro Atendimento
	 * Verifica Existência de Ordem de Serviço Programada
	 * 
	 * @author Leonardo Regis
	 * @date 19/08/2006
	 * @param idOS
	 * @return boolean
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaOSProgramada(Integer idOS) throws ErroRepositorioException{

		boolean retorno = false;
		Collection<Object[]> retornoConsulta = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = " SELECT osp.id, " // 0
							+ " os.id " // 1
							+ " FROM OrdemServicoProgramacao osp " + " INNER JOIN osp.ordemServico os  "
							+ " WHERE os.id = :idOS "
							+ " and osp.indicadorAtivo = " + OrdemServicoProgramacao.INDICADOR_ATIVO + " ORDER BY os.id ";

			retornoConsulta = session.createQuery(consulta).setInteger("idOS", idOS).list();

			if(retornoConsulta.size() > 0){
				retorno = true;
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0450] Pesquisar Ordem Serviço
	 * Seleciona ordem de serviço por codigo do cliente atraves do RASolicitante
	 * 
	 * @author Leandro Cavalcanti
	 * @date 19/08/2006
	 * @param codigoCliente
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperarAtividadesServicoTipo(Collection<Integer> atividades) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			if(atividades != null && !atividades.isEmpty()){
				consulta = "select at.ativ_id as idAT " + "from atividade at, " + "servico_tipo_atividade stat "
								+ "where at.ativ_id = stat.ativ_id " + "and stat.svtp_id in (:atividades) ";

				retornoConsulta = session.createSQLQuery(consulta).addScalar("idAT", Hibernate.INTEGER)
								.setParameter("atividades", atividades).list();
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * Pesquisar Materiais
	 * Seleciona os materiais no array de servico tipo.
	 * 
	 * @author Rafael Pinto
	 * @date 19/08/2006
	 * @param Collection
	 *            <Integer>
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperarMaterialServicoTipo(Collection<Integer> materiais) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			if(materiais != null && !materiais.isEmpty()){
				consulta = "select at.ativ_id as idMAT " + "from material_unidade matuni, " + "servico_tipo_material stmat "
								+ "where matuni.mate_id = stmat.mate_id " + "and stmat.svtp_id in (:materiais) ";

				retornoConsulta = session.createSQLQuery(consulta).addScalar("idMAT", Hibernate.INTEGER)
								.setParameter("materiais", materiais).list();
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0413] Pesquisar Tipo de Serviço
	 * select a.svtp_id from ATENDIMENTOPUBLICO.SERVICO_TIPO A,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_ATIVIDADE B,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_MATERIAL C WHERE A.SVTP_DSSERVICOTIPO
	 * LIKE '%DESC%' AND A.SVTP_DSABREVIADO LIKE '%DESC%' AND (A.SVTP_VLSERVICO >=
	 * 000000 AND A.SVTP_VLSERVICO <= 99999) AND A.SVTP_ICPAVIMENTO = 1 OU 2 and
	 * A.SVTP_ICATUALIZACOMERCIAL = 1 OU 2 AND A.SVTP_ICTERCEIRIZADO = 1 OU 2
	 * AND A.SVTP_CDSERVICOTIPO = ("O" OR "C") AND (A.SVTP_NNTEMPOMEDIOEXECUCAO >=
	 * 0000 AND A.SVTP_NNTEMPOMEDIOEXECUCAO <= 9999) AND DBTP_ID = ID INFORMADO
	 * AND AND CRTP_ID = ID INFORMADO AND STSG_ID = ID INFORMADO AND STRF_ID =
	 * ID INFORMADO AND STPR_ID = ID INFORMADO AND A.SVTP_ID = B.SVTP_ID AND
	 * B.ATIV_ID IN (ID's INFORMADOS) AND A.SVTP_ID = C.SVTP_ID AND C.MATE_ID IN
	 * (ID's INFORMADOS)
	 * 
	 * @author Leandro Cavalcanti
	 * @date 17/08/2006
	 */
	public Collection<ServicoTipo> filtrarST(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal,
					String tipoPesquisa, String tipoPesquisaAbreviada, Integer numeroPaginasPesquisa, Integer idUnidadeOrganizacionalDestino)
					throws ErroRepositorioException{

		Collection<ServicoTipo> retorno = null;
		Session session = HibernateUtil.getSession();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		try{

			consulta = "SELECT DISTINCT(svtp.id),svtp.descricao," + "svtp.servicoTipoPrioridade.descricao,"
							+ "svtp.indicadorAtualizaComercial," + "svtp.indicadorPavimento," + "svtp.indicadorTerceirizado"
							+ " FROM ServicoTipo svtp  " + " LEFT JOIN svtp.servicoTipoSubgrupo subg " + " LEFT JOIN svtp.debitoTipo tpdb"
							+ " LEFT JOIN svtp.creditoTipo tpcd " + " LEFT JOIN svtp.servicoTipoPrioridade tppri "
							+ " LEFT JOIN svtp.servicoTipoReferencia tpref  " + " LEFT JOIN svtp.servicoPerfilTipo perftp "
							+ " LEFT JOIN svtp.servicoTipoAtividades srvtpatv " + " LEFT JOIN svtp.servicoTipoMateriais srvtpmat "
							+ " WHERE 1=1 and svtp.indicadorUso = " + ConstantesSistema.INDICADOR_USO_ATIVO;

			if(servicoTipo.getDescricao() != null && !servicoTipo.getDescricao().equals("")){
				if(tipoPesquisa == null || tipoPesquisa.trim().equals("1") || tipoPesquisa.equals("")){
					consulta += " AND svtp.descricao LIKE '" + servicoTipo.getDescricao().toUpperCase() + "%' ";
				}else{
					consulta += " AND svtp.descricao LIKE '%" + servicoTipo.getDescricao().toUpperCase() + "%' ";
				}

			}
			if(servicoTipo.getDescricaoAbreviada() != null && !servicoTipo.getDescricaoAbreviada().equals("")){
				if(tipoPesquisaAbreviada == null || tipoPesquisaAbreviada.trim().equals("1") || tipoPesquisaAbreviada.equals("")){
					consulta += " AND svtp.descricaoAbreviada LIKE '" + servicoTipo.getDescricaoAbreviada().toUpperCase() + "%' ";
				}else{
					consulta += " AND svtp.descricaoAbreviada LIKE '%" + servicoTipo.getDescricaoAbreviada().toUpperCase() + "%' ";
				}
			}

			if(servicoTipo.getServicoTipoSubgrupo() != null && !servicoTipo.getServicoTipoSubgrupo().equals("")){
				consulta += " AND subg.id = (:idSubg) ";
				parameters.put("idSubg", servicoTipo.getServicoTipoSubgrupo().getId());
			}

			if(new Short(servicoTipo.getIndicadorPavimento()) != 0 && new Short(servicoTipo.getIndicadorPavimento()) != 3){
				consulta += " AND svtp.indicadorPavimento = (:idIndpv) ";
				parameters.put("idIndpv", servicoTipo.getIndicadorPavimento());
			}

			if(!valorServicoInicial.equalsIgnoreCase("") && !valorServicoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.valor BETWEEN (:valorInicial) AND (:valorFinal) ";
				parameters.put("valorInicial", valorServicoInicial);
				parameters.put("valorFinal", valorServicoFinal);
			}

			if(new Short(servicoTipo.getIndicadorAtualizaComercial()) != 0){

				if(servicoTipo.getIndicadorAtualizaComercial() == 1){
					consulta += " AND svtp.indicadorAtualizaComercial in (1,3) ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 2){
					consulta += " AND svtp.indicadorAtualizaComercial = 1 ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 3){
					consulta += " AND svtp.indicadorAtualizaComercial = 3 ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 4){
					consulta += " AND svtp.indicadorAtualizaComercial in (1,2,3)  ";
				}else if(servicoTipo.getIndicadorAtualizaComercial() == 5){
					consulta += " AND svtp.indicadorAtualizaComercial = 2 ";
				}
			}

			if(servicoTipo.getIndicadorTerceirizado() != 0){
				consulta += " AND svtp.indicadorTerceirizado = " + servicoTipo.getIndicadorTerceirizado();
			}

			if(servicoTipo.getCodigoServicoTipo() != null && !servicoTipo.getCodigoServicoTipo().equals("")
							&& !servicoTipo.getCodigoServicoTipo().equals("3")){
				consulta += " AND svtp.codigoServicoTipo = (:codSvtp) ";
				if(servicoTipo.getCodigoServicoTipo().toString().equals("1")){
					parameters.put("codSvtp", "O");
				}else{
					parameters.put("codSvtp", "C");
				}
			}

			if(!tempoMedioExecucaoInicial.equalsIgnoreCase("") && !tempoMedioExecucaoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.tempoMedioExecucao BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", tempoMedioExecucaoInicial);
				parameters.put("tempoFinal", tempoMedioExecucaoFinal);

			}else if(!tempoMedioExecucaoInicial.equalsIgnoreCase("") && tempoMedioExecucaoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.tempoMedioExecucao BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", tempoMedioExecucaoInicial);
				parameters.put("tempoFinal", 9999);
			}else if(!tempoMedioExecucaoFinal.equalsIgnoreCase("") && tempoMedioExecucaoInicial.equalsIgnoreCase("")){
				consulta += " AND svtp.tempoMedioExecucao BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", 0000);
				parameters.put("tempoFinal", tempoMedioExecucaoFinal);
			}
			if(servicoTipo.getDebitoTipo() != null){
				if(servicoTipo.getDebitoTipo().getId() != null && !servicoTipo.getDebitoTipo().getId().equals("")){
					consulta += " AND tpdb.id = (:idDeb) ";
					parameters.put("idDeb", servicoTipo.getDebitoTipo().getId());
				}
			}
			if(servicoTipo.getCreditoTipo() != null){
				if(servicoTipo.getCreditoTipo().getId() != null && !servicoTipo.getCreditoTipo().getId().equals("")){
					consulta += " AND tpcd.id = (:idTpcd) ";
					parameters.put("idTpcd", servicoTipo.getCreditoTipo().getId());
				}
			}
			if(servicoTipo.getServicoTipoPrioridade() != null){
				if(servicoTipo.getServicoTipoPrioridade().getId() != null && !servicoTipo.getServicoTipoPrioridade().getId().equals("")){
					consulta += " AND tppri.id = (:idTpcd) ";
					parameters.put("idTpcd", servicoTipo.getServicoTipoPrioridade().getId());
				}
			}
			if(servicoTipo.getServicoPerfilTipo() != null){
				if(servicoTipo.getServicoPerfilTipo().getId() != null && !servicoTipo.getServicoPerfilTipo().getId().equals("")){
					consulta += " AND perftp.id = (:idPerf) ";
					parameters.put("idPerf", servicoTipo.getServicoPerfilTipo().getId());
				}
			}
			if(servicoTipo.getServicoTipoReferencia() != null){
				if(servicoTipo.getServicoTipoReferencia().getId() != null && !servicoTipo.getServicoTipoReferencia().getId().equals("")){
					consulta += " AND tpref.id = (:idRef) ";
					parameters.put("idRef", servicoTipo.getServicoTipoReferencia().getId());
				}
			}

			if(colecaoMaterial != null && !colecaoMaterial.isEmpty()){

				consulta += " AND srvtpmat.comp_id.idMaterial in (:idMat) ";
				parameters.put("idMat", colecaoMaterial);
			}

			if(colecaoAtividades != null && !colecaoAtividades.isEmpty()){

				consulta += " AND srvtpatv.comp_id.idAtividade in (:idAt) ";
				parameters.put("idAt", colecaoAtividades);
			}

			if(idUnidadeOrganizacionalDestino != null){
				consulta += " AND svtp.id in (select distinct sttr.servicoTipo.id ";
				consulta += "                 from ServicoTipoTramite sttr ";
				consulta += "                 where sttr.unidadeOrganizacionalDestino.id = :idUnidadeOrganizacionalDestino) ";

				parameters.put("idUnidadeOrganizacionalDestino", idUnidadeOrganizacionalDestino);
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.setFirstResult(10 * numeroPaginasPesquisa).setMaxResults(10).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<ServicoPerfilTipo> pesquisarServicoPerfilTipoPorUnidade(Integer unidadeLotacao, Integer origemServico,
					Short permiteTramiteIndependente) throws ErroRepositorioException{

		Collection<ServicoPerfilTipo> retorno = new ArrayList<ServicoPerfilTipo>();

		Collection retornoOs = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			String subQuery = this.criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(unidadeLotacao, permiteTramiteIndependente);

			String querySituacao1 = "SELECT count(os) " + "FROM OrdemServico os " + " INNER JOIN os.registroAtendimento ra "
							+ "INNER JOIN ra.tramites tramite " + " INNER JOIN ra.localidade raLocal "
							+ "LEFT JOIN ra.setorComercial raSetor " + " LEFT JOIN ra.quadra raQuadra "
							+ "LEFT JOIN raQuadra.distritoOperacional raDistrito  " + " LEFT JOIN os.cobrancaDocumento cobra "
							+ " WHERE os.servicoTipo.servicoPerfilTipo.id = :idServicoPerfilTipo "
							+ " and tramite.unidadeOrganizacionalDestino.id = " + unidadeLotacao
							+ " and os.situacao in (1, 3) and os.indicadorProgramada = 2 and os.id in (" + subQuery + ")";

			String querySituacao2 = "SELECT count(os) " + "FROM OrdemServico os ";

			if(origemServico != 1 && origemServico != 2){
				querySituacao2 += " LEFT JOIN os.registroAtendimento ra " + "LEFT JOIN ra.tramites tramite "
								+ " LEFT JOIN ra.localidade raLocal " + "LEFT JOIN ra.setorComercial raSetor "
								+ " LEFT JOIN ra.quadra raQuadra " + "LEFT JOIN raQuadra.distritoOperacional raDistrito  "
								+ " LEFT JOIN os.imovel imov  LEFT JOIN imov.localidade imovLocal "
								+ " LEFT JOIN imov.quadra imovQuadra LEFT JOIN imovQuadra.distritoOperacional imovDistrito  "
								+ " LEFT JOIN imov.setorComercial imovSetor ";
			}

			querySituacao2 += "INNER JOIN os.servicoTipo.servicoPerfilTipo SPT " + " WHERE SPT.id = :idServicoPerfilTipo "
							+ " AND os.situacao in (1, 3) and os.indicadorProgramada = 2 and os.registroAtendimento is NULL "
							+ " AND (os.id = (SELECT DISTINCT osu.ordemServico.id FROM OrdemServicoUnidade osu "
							+ "               WHERE osu.unidadeOrganizacional.id = " + unidadeLotacao
							+ "                 and osu.atendimentoRelacaoTipo.id = :idAtendimentoTipo "
							+ "                 and osu.ordemServico.id = os.id) OR "
							+ "      os.osReferencia.id = (SELECT DISTINCT osu.ordemServico.id FROM OrdemServicoUnidade osu "
							+ "                            WHERE osu.unidadeOrganizacional.id = " + unidadeLotacao
							+ "                              and osu.atendimentoRelacaoTipo.id = :idAtendimentoTipo "
							+ "                              and osu.ordemServico.id = osu.unidadeOrganizacional.id))";

			consulta = "SELECT DISTINCT sptp " + "FROM Equipe eqpe " + "INNER JOIN eqpe.unidadeOrganizacional unid "
							+ "INNER JOIN eqpe.servicoPerfilTipo sptp " + "WHERE unid.id = :unidadeLotacao " + "ORDER BY sptp.descricao";

			retorno = session.createQuery(consulta).setInteger("unidadeLotacao", unidadeLotacao).list();
			Collection removeRetorno = new ArrayList();
			if(!retorno.isEmpty()){
				Integer qtidadeOs;
				Iterator iterator = retorno.iterator();
				while(iterator.hasNext()){
					ServicoPerfilTipo servicoPerfilTipo = (ServicoPerfilTipo) iterator.next();
					if(origemServico == ConstantesSistema.SIM.intValue()){
						retornoOs = session.createQuery(querySituacao1).setInteger("idServicoPerfilTipo", servicoPerfilTipo.getId()).list();
						qtidadeOs = ((Long) retornoOs.toArray()[0]).intValue();
					}else if(origemServico == ConstantesSistema.NAO.intValue()){
						retornoOs = session.createQuery(querySituacao2).setInteger("idServicoPerfilTipo", servicoPerfilTipo.getId())
										.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();
						qtidadeOs = ((Long) retornoOs.toArray()[0]).intValue();
					}else{
						retornoOs = session.createQuery(querySituacao1).setInteger("idServicoPerfilTipo", servicoPerfilTipo.getId()).list();
						Integer valorSituacao1 = ((Long) retornoOs.toArray()[0]).intValue();
						retornoOs = session.createQuery(querySituacao2).setInteger("idServicoPerfilTipo", servicoPerfilTipo.getId())
										.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();
						qtidadeOs = ((Long) retornoOs.toArray()[0]).intValue();
						qtidadeOs += valorSituacao1;
					}
					if(qtidadeOs > 0){
						servicoPerfilTipo.setQtidadeOs(qtidadeOs);
					}else{
						removeRetorno.add(servicoPerfilTipo);
					}
				}
				retorno.removeAll(removeRetorno);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0413] Pesquisar Tipo de Serviço
	 * Faz o count para saber a quantidade total de registros retornados
	 * 
	 * @author Flávio
	 * @date 17/08/2006
	 */

	public Integer filtrarSTCount(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal,
					String tipoPesquisa, String tipoPesquisaAbreviada, Integer idUnidadeOrganizacionalDestino)
					throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		try{

			consulta = "SELECT (count(distinct svtp.id)) " + " FROM ServicoTipo svtp " + " LEFT JOIN svtp.servicoTipoSubgrupo subg "
							+ " LEFT JOIN svtp.debitoTipo tpdb" + " LEFT JOIN svtp.creditoTipo tpcd "
							+ " LEFT JOIN svtp.servicoTipoPrioridade tppri " + " LEFT JOIN svtp.servicoTipoReferencia tpref  "
							+ " LEFT JOIN svtp.servicoPerfilTipo perftp " + " LEFT JOIN svtp.servicoTipoAtividades srvtpatv "
							+ " LEFT JOIN svtp.servicoTipoMateriais srvtpmat " + " WHERE 1=1 and svtp.indicadorUso = "
							+ ConstantesSistema.INDICADOR_USO_ATIVO;

			if(servicoTipo.getDescricao() != null && !servicoTipo.getDescricao().equals("")){
				if(tipoPesquisa == null || tipoPesquisa.trim().equals("1") || tipoPesquisa.equals("")){
					consulta += " AND svtp.descricao LIKE '" + servicoTipo.getDescricao().toUpperCase() + "%' ";
				}else{
					consulta += " AND svtp.descricao LIKE '%" + servicoTipo.getDescricao().toUpperCase() + "%' ";
				}

			}
			if(servicoTipo.getDescricaoAbreviada() != null && !servicoTipo.getDescricaoAbreviada().equals("")){
				if(tipoPesquisaAbreviada == null || tipoPesquisaAbreviada.trim().equals("1") || tipoPesquisaAbreviada.equals("")){
					consulta += " AND svtp.descricaoAbreviada LIKE '" + servicoTipo.getDescricaoAbreviada().toUpperCase() + "%' ";
				}else{
					consulta += " AND svtp.descricaoAbreviada LIKE '%" + servicoTipo.getDescricaoAbreviada().toUpperCase() + "%' ";
				}
			}

			if(servicoTipo.getServicoTipoSubgrupo() != null && !servicoTipo.getServicoTipoSubgrupo().equals("")){
				consulta += " AND subg.id = (:idSubg) ";
				parameters.put("idSubg", servicoTipo.getServicoTipoSubgrupo().getId());
			}

			if(new Short(servicoTipo.getIndicadorPavimento()) != 0 && new Short(servicoTipo.getIndicadorPavimento()) != 3){
				consulta += " AND svtp.indicadorPavimento = (:idIndpv) ";
				parameters.put("idIndpv", servicoTipo.getIndicadorPavimento());
			}

			if(!valorServicoInicial.equalsIgnoreCase("") && !valorServicoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.valor BETWEEN (:valorInicial) AND (:valorFinal) ";
				parameters.put("valorInicial", valorServicoInicial);
				parameters.put("valorFinal", valorServicoFinal);
			}

			if(new Short(servicoTipo.getIndicadorAtualizaComercial()) != 0){

				if(servicoTipo.getIndicadorAtualizaComercial() == 1){
					consulta += " AND svtp.indicadorAtualizaComercial in (1,3) ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 2){
					consulta += " AND svtp.indicadorAtualizaComercial = 1 ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 3){
					consulta += " AND svtp.indicadorAtualizaComercial = 3 ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 4){
					consulta += " AND svtp.indicadorAtualizaComercial in (1,2,3)  ";
				}else if(servicoTipo.getIndicadorAtualizaComercial() == 5){
					consulta += " AND svtp.indicadorAtualizaComercial = 2 ";
				}
			}

			if(servicoTipo.getIndicadorTerceirizado() != 0){
				consulta += " AND svtp.indicadorTerceirizado = " + servicoTipo.getIndicadorTerceirizado();
			}

			if(servicoTipo.getCodigoServicoTipo() != null && !servicoTipo.getCodigoServicoTipo().equals("")
							&& !servicoTipo.getCodigoServicoTipo().equals("3")){
				consulta += " AND svtp.codigoServicoTipo = (:codSvtp) ";
				if(servicoTipo.getCodigoServicoTipo().toString().equals("1")){
					parameters.put("codSvtp", "O");
				}else{
					parameters.put("codSvtp", "C");
				}
			}

			if(!tempoMedioExecucaoInicial.equalsIgnoreCase("") && !tempoMedioExecucaoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.tempoMedioExecucao BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", tempoMedioExecucaoInicial);
				parameters.put("tempoFinal", tempoMedioExecucaoFinal);

			}else if(!tempoMedioExecucaoInicial.equalsIgnoreCase("") && tempoMedioExecucaoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.tempoMedioExecucao BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", tempoMedioExecucaoInicial);
				parameters.put("tempoFinal", 9999);
			}else if(!tempoMedioExecucaoFinal.equalsIgnoreCase("") && tempoMedioExecucaoInicial.equalsIgnoreCase("")){
				consulta += " AND svtp.tempoMedioExecucao BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", 0000);
				parameters.put("tempoFinal", tempoMedioExecucaoFinal);
			}
			if(servicoTipo.getDebitoTipo() != null){
				if(servicoTipo.getDebitoTipo().getId() != null && !servicoTipo.getDebitoTipo().getId().equals("")){
					consulta += " AND tpdb.id = (:idDeb) ";
					parameters.put("idDeb", servicoTipo.getDebitoTipo().getId());
				}
			}
			if(servicoTipo.getCreditoTipo() != null){
				if(servicoTipo.getCreditoTipo().getId() != null && !servicoTipo.getCreditoTipo().getId().equals("")){
					consulta += " AND tpcd.id = (:idTpcd) ";
					parameters.put("idTpcd", servicoTipo.getCreditoTipo().getId());
				}
			}
			if(servicoTipo.getServicoTipoPrioridade() != null){
				if(servicoTipo.getServicoTipoPrioridade().getId() != null && !servicoTipo.getServicoTipoPrioridade().getId().equals("")){
					consulta += " AND tppri.id = (:idTpcd) ";
					parameters.put("idTpcd", servicoTipo.getServicoTipoPrioridade().getId());
				}
			}
			if(servicoTipo.getServicoPerfilTipo() != null){
				if(servicoTipo.getServicoPerfilTipo().getId() != null && !servicoTipo.getServicoPerfilTipo().getId().equals("")){
					consulta += " AND perftp.id = (:idPerf) ";
					parameters.put("idPerf", servicoTipo.getServicoPerfilTipo().getId());
				}
			}
			if(servicoTipo.getServicoTipoReferencia() != null){
				if(servicoTipo.getServicoTipoReferencia().getId() != null && !servicoTipo.getServicoTipoReferencia().getId().equals("")){
					consulta += " AND tpref.id = (:idRef) ";
					parameters.put("idRef", servicoTipo.getServicoTipoReferencia().getId());
				}
			}

			if(colecaoMaterial != null && !colecaoMaterial.isEmpty()){

				consulta += " AND srvtpmat.comp_id.idMaterial in (:idMat) ";
				parameters.put("idMat", colecaoMaterial);
			}

			if(colecaoAtividades != null && !colecaoAtividades.isEmpty()){

				consulta += " AND srvtpatv.comp_id.idAtividade in (:idAt) ";
				parameters.put("idAt", colecaoAtividades);
			}

			if(idUnidadeOrganizacionalDestino != null){
				consulta += " AND svtp.id in (select distinct sttr.servicoTipo.id ";
				consulta += "                 from ServicoTipoTramite sttr ";
				consulta += "                 where sttr.unidadeOrganizacionalDestino.id = :idUnidadeOrganizacionalDestino) ";

				parameters.put("idUnidadeOrganizacionalDestino", idUnidadeOrganizacionalDestino);
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = ((Number) query.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC413]- Pesquisar Tipo de Serviço
	 * Pesquisar o Objeto servicoTipo referente ao idTiposervico recebido na
	 * descrição da pesquisa, onde o mesmo sera detalhado.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 23/08/2006
	 * @param idImovel
	 * @param idSolicitacaoTipoEspecificacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarServicoTipo(Integer idTipoServico) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT svtp.descricao, " + "svtp.descricaoAbreviada, " + " sts.descricao, " + "svtp.indicadorPavimento, "
							+ "svtp.valor, " + "svtp.indicadorAtualizaComercial, " + "svtp.indicadorTerceirizado, "
							+ "svtp.codigoServicoTipo, " + "svtp.tempoMedioExecucao, " + "dt.descricao, " + "ct.descricao, "
							+ "stp.descricao, " + "spt.descricao, " + "stri.descricao " + "FROM ServicoTipo svtp "
							+ "LEFT JOIN svtp.servicoTipoReferencia stri " + "LEFT JOIN svtp.creditoTipo ct "
							+ "LEFT JOIN svtp.servicoPerfilTipo spt " + "LEFT JOIN svtp.servicoTipoSubgrupo sts "
							+ "LEFT JOIN svtp.servicoTipoPrioridade stp " + "LEFT JOIN svtp.debitoTipo dt "
							+ "WHERE svtp.id = :idTipoServico";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idTipoServico", idTipoServico).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public ServicoTipo pesquisarServicoTipo2(Integer idTipoServico) throws ErroRepositorioException{

		ServicoTipo retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT svtp " + "FROM ServicoTipo svtp " + "LEFT JOIN FETCH svtp.servicoTipoReferencia stri "
							+ "LEFT JOIN FETCH svtp.creditoTipo ct " + "LEFT JOIN FETCH svtp.servicoPerfilTipo spt "
							+ "LEFT JOIN FETCH svtp.servicoTipoSubgrupo sts " + "LEFT JOIN FETCH svtp.servicoTipoPrioridade stp "
							+ "LEFT JOIN FETCH svtp.debitoTipo dt " + "WHERE svtp.id = :idTipoServico";

			retorno = (ServicoTipo) session.createQuery(consulta).setInteger("idTipoServico", idTipoServico).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisar Materiais pelo idServicoTipo na tabela de ServicoTipoMaterial
	 * Recupera os materiais do servico tipo material
	 * 
	 * @author Leandro Cavalcanti
	 * @date 24/08/2006
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Collection recuperarMaterialServicoTipoConsulta(Integer idServicoTipoMaterial) throws ErroRepositorioException{

		Collection retornoConsulta = null;
		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			if(idServicoTipoMaterial != null){
				consulta = "SELECT mat.descricao," + "svtpmat.quantidadePadrao " + "FROM ServicoTipoMaterial svtpmat "
								+ "LEFT JOIN svtpmat.material mat " + "WHERE svtpmat.comp_id.idServicoTipo = :idServicoTipoMaterial";

				retornoConsulta = (Collection) session.createQuery(consulta).setInteger("idServicoTipoMaterial", idServicoTipoMaterial)
								.list();

				if(retornoConsulta.size() > 0){

					retorno = new ArrayList();

					ServicoTipoMaterial servicoTipoMaterial = null;
					Material material = null;

					for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

						Object[] element = (Object[]) iter.next();

						servicoTipoMaterial = new ServicoTipoMaterial();
						servicoTipoMaterial.setQuantidadePadrao((BigDecimal) element[1]);

						material = new Material();
						material.setDescricao((String) element[0]);

						servicoTipoMaterial.setMaterial(material);

						retorno.add(servicoTipoMaterial);
					}

				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisar Atividades pelo idServicoTipo na tabela de ServicoTipoAtividade
	 * Recupera os Atividades do servico tipo Atividade
	 * 
	 * @author Leandro Cavalcanti
	 * @date 24/08/2006
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Collection recuperarAtividadeServicoTipoConsulta(Integer idServicoTipoAtividade) throws ErroRepositorioException{

		Collection retornoConsulta = null;
		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			if(idServicoTipoAtividade != null){
				consulta = "SELECT at.descricao," + "svtpat.numeroExecucao " + "FROM ServicoTipoAtividade svtpat "
								+ "LEFT JOIN svtpat.atividade at " + "WHERE svtpat.comp_id.idServicoTipo = :idServicoTipoAtividade";

				retornoConsulta = (Collection) session.createQuery(consulta).setInteger("idServicoTipoAtividade", idServicoTipoAtividade)
								.list();

				if(retornoConsulta.size() > 0){

					retorno = new ArrayList();

					ServicoTipoAtividade servicoTipoAtividade = null;
					Atividade atividade = null;

					for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

						Object[] element = (Object[]) iter.next();

						servicoTipoAtividade = new ServicoTipoAtividade();
						servicoTipoAtividade.setNumeroExecucao((Short) element[1]);

						atividade = new Atividade();
						atividade.setDescricao((String) element[0]);

						servicoTipoAtividade.setAtividade(atividade);

						retorno.add(servicoTipoAtividade);
					}

				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 14/08/2006
	 */
	public boolean existeOrdemServicoDiferenteEncerrado(Integer idregistroAtendimento) throws ErroRepositorioException{

		boolean retorno = false;
		Session session = HibernateUtil.getSession();
		try{
			String consulta = "select os from OrdemServico os where os.registroAtendimento.id = " + idregistroAtendimento
							+ " and os.situacao <> " + OrdemServico.SITUACAO_ENCERRADO;
			Collection collection = session.createQuery(consulta).list();
			if(collection != null && !collection.isEmpty()){
				retorno = true;
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 14/08/2006
	 */
	public OrdemServico pesquisarOrdemServicoDiferenteEncerrado(Integer idregistroAtendimento, Integer idServicoTipo)
					throws ErroRepositorioException{

		OrdemServico ordemServico = null;
		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "SELECT os.id,"// 0
							+ "servicoTipo.id,"// 1
							+ "servicoTipo.descricao, "// 2
							+ "ra.id "// 3
							+ "FROM OrdemServico os " + "INNER JOIN os.registroAtendimento ra "
							+ "INNER JOIN os.servicoTipo servicoTipo "
							+ "WHERE servicoTipo.id = :idServicoTipo "
							+ "AND ra.id = :idregistroAtendimento "
							+ "AND os.situacao <> :situacao";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idServicoTipo", idServicoTipo)
							.setInteger("idregistroAtendimento", idregistroAtendimento)
							.setShort("situacao", OrdemServico.SITUACAO_ENCERRADO).setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){

				ordemServico = new OrdemServico();
				ordemServico.setId((Integer) retornoConsulta[0]);

				ServicoTipo servico = new ServicoTipo();
				servico.setId((Integer) retornoConsulta[1]);
				servico.setDescricao((String) retornoConsulta[2]);

				RegistroAtendimento ra = new RegistroAtendimento();
				ra.setId((Integer) retornoConsulta[3]);

				ordemServico.setServicoTipo(servico);
				ordemServico.setRegistroAtendimento(ra);

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return ordemServico;
	}

	/**
	 * [UC0430] Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 17/08/2006
	 */
	public ServicoTipo pesquisarSevicoTipo(Integer id) throws ErroRepositorioException{

		ServicoTipo retorno = null;
		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT svtp " + "FROM ServicoTipo svtp " + " LEFT OUTER JOIN FETCH svtp.servicoTipoPrioridade svtpri "
							+ " LEFT OUTER JOIN FETCH svtp.servicoTipoReferencia svtr " + " LEFT OUTER JOIN FETCH svtr.servicoTipo "
							+ "WHERE svtp.id = " + id + " and svtp.indicadorUso = " + ConstantesSistema.INDICADOR_USO_ATIVO;

			retorno = (ServicoTipo) session.createQuery(consulta).uniqueResult();

			// if (retorno != null) {
			// Hibernate.initialize(retorno.getServicoTipoPrioridade());
			// Hibernate.initialize(retorno.getServicoTipoReferencia());
			// if (retorno.getServicoTipoReferencia() != null) {
			// Hibernate.initialize(retorno.getServicoTipoReferencia()
			// .getServicoTipo());
			// }
			// }

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<ServicoTipo> pesquisarServicoTipoPorUnidade(Integer unidadeLotacao) throws ErroRepositorioException{

		Collection<ServicoTipo> retornoConsulta = new ArrayList();
		Collection<ServicoTipo> retornoQtidadeOs = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT svtp.id, svtp.descricao, count(os) " + "FROM OrdemServicoUnidade osUnidade "
							+ " INNER JOIN osUnidade.atendimentoRelacaoTipo art  " + "INNER JOIN osUnidade.ordemServico os  "
							+ " INNER JOIN osUnidade.unidadeOrganizacional unid  " + "INNER JOIN os.servicoTipo svtp  "
							+ " LEFT JOIN os.cobrancaDocumento cobra " + "LEFT JOIN os.registroAtendimento ra  " + "WHERE ra IS NULL "
							+ "AND unid.id = :unidadeLotacao " + "AND os.situacao in (1,3) and os.indicadorProgramada = 2 "
							+ "AND art.id = :idAtendimentoTipo " + "GROUP BY svtp.id, svtp.descricao ORDER BY svtp.descricao";

			retornoConsulta = session.createQuery(consulta).setInteger("unidadeLotacao", unidadeLotacao)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();
			Object[] retorno = retornoConsulta.toArray();
			Object[] itemLista;
			ServicoTipo servicoTipo = null;
			for(int i = 0; i < retorno.length; i++){
				itemLista = (Object[]) retorno[i];
				servicoTipo = new ServicoTipo();
				servicoTipo.setId((Integer) itemLista[0]);
				servicoTipo.setDescricao((String) itemLista[1]);
				servicoTipo.setQtidadeOs(((Number) itemLista[2]).intValue());
				retornoQtidadeOs.add(servicoTipo);
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoQtidadeOs;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança para chamar o método criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino
	 * @author Hugo Lima
	 * @date 16/02/2012
	 *       Mudança para desvincular a OS do RA
	 */
	public Collection<ServicoTipo> pesquisarServicoTipoPorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Collection<ServicoTipo> retornoConsulta = new ArrayList();
		Collection<ServicoTipo> retornoQtidadeOs = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT svtp.id, svtp.descricao, COUNT(os) FROM OrdemServico os " + " INNER JOIN os.servicoTipo svtp WHERE "
							+ "os.id in( "
							+ criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(idUnidadeDestino, permiteTramiteIndependente)
							+ ") AND os.situacao IN (1, 3) and os.indicadorProgramada = 2 AND os.registroAtendimento is not null "
							+ " GROUP BY svtp.id, svtp.descricao ORDER BY svtp.descricao ";

			retornoConsulta = session.createQuery(consulta).list();

			Object[] retorno = retornoConsulta.toArray();
			Object[] itemLista;

			ServicoTipo servicoTipo = null;
			for(int i = 0; i < retorno.length; i++){
				itemLista = (Object[]) retorno[i];
				servicoTipo = new ServicoTipo();
				servicoTipo.setId((Integer) itemLista[0]);
				servicoTipo.setDescricao((String) itemLista[1]);
				servicoTipo.setQtidadeOs(((Number) itemLista[2]).intValue());
				retornoQtidadeOs.add(servicoTipo);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoQtidadeOs;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança para chamar o método criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino
	 * @author Hugo Lima
	 * @date 16/02/2012
	 *       Mudança para desvincular a OS do RA
	 */
	public Collection<Localidade> pesquisarLocalidadePorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Collection<Localidade> retornoConsulta = new ArrayList<Localidade>();
		Collection<Localidade> retorno = new ArrayList<Localidade>();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			String subQuery = this.criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(idUnidadeDestino, permiteTramiteIndependente);

			consulta = "SELECT local.id, local.descricao, count(os) " + "FROM OrdemServico os "
							+ "INNER JOIN os.registroAtendimento ragt  " + "INNER JOIN ragt.localidade local  "
							+ "WHERE os.situacao in (1,3) and os.indicadorProgramada = 2 " + "AND os.id in (" + subQuery + ") "
							+ "GROUP BY local.id, local.descricao " + "ORDER BY local.descricao";

			retornoConsulta = session.createQuery(consulta).list();
			if(retornoConsulta.size() > 0){
				retorno = new ArrayList<Localidade>();
				Localidade localidade = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Object[] element = (Object[]) iter.next();
					localidade = new Localidade();
					localidade.setId((Integer) element[0]);
					localidade.setDescricao((String) element[1]);
					localidade.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(localidade);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<Localidade> pesquisarLocalidadePorUnidade(Integer unidade) throws ErroRepositorioException{

		Collection<Localidade> retornoConsulta = null;
		Collection<Localidade> retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT local.id, local.descricao, count(os) " + "FROM OrdemServicoUnidade osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  " + "INNER JOIN osUnidade.ordemServico os  "
							+ "INNER JOIN osUnidade.unidadeOrganizacional unid  " + "INNER JOIN os.imovel imov  "
							+ " LEFT JOIN imov.localidade local  " + "LEFT JOIN os.registroAtendimento ra  " + "WHERE ra IS NULL "
							+ "AND os.situacao in (1,3) and os.indicadorProgramada = 2 " + "AND unid.id = :unidade "
							+ "AND art.id = :idAtendimentoTipo " + "GROUP BY local.id, local.descricao " + "ORDER BY local.descricao";

			retornoConsulta = session.createQuery(consulta).setInteger("unidade", unidade)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();
			if(retornoConsulta.size() > 0){
				Localidade localidade = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Object[] element = (Object[]) iter.next();
					localidade = new Localidade();
					localidade.setId((Integer) element[0]);
					localidade.setDescricao((String) element[1]);
					localidade.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(localidade);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<SetorComercial> pesquisarSetorComercialPorUnidade(Integer unidade) throws ErroRepositorioException{

		Collection<SetorComercial> retornoConsulta = new ArrayList();
		Collection<SetorComercial> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT setorComercial.id, setorComercial.descricao, count(os) " + "FROM OrdemServicoUnidade osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  " + "INNER JOIN osUnidade.ordemServico os  "
							+ "INNER JOIN osUnidade.unidadeOrganizacional unid  " + "INNER JOIN os.imovel imov "
							+ "INNER JOIN imov.setorComercial setorComercial  " + "LEFT JOIN os.registroAtendimento ra  "
							+ "WHERE ra IS NULL " + "AND os.situacao in (1,3) and os.indicadorProgramada = 2 " + "AND unid.id = :unidade "
							+ "AND art.id = :idAtendimentoTipo " + "GROUP BY setorComercial.id, setorComercial.descricao "
							+ "ORDER BY setorComercial.descricao";

			retornoConsulta = session.createQuery(consulta).setInteger("unidade", unidade)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

			if(retornoConsulta.size() > 0){
				SetorComercial setorComercial = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Object[] element = (Object[]) iter.next();
					setorComercial = new SetorComercial();
					setorComercial.setId((Integer) element[0]);
					setorComercial.setDescricao((String) element[1]);
					setorComercial.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(setorComercial);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança para chamar o método criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino
	 * @author Hugo Lima
	 * @date 16/02/2012
	 *       Mudança para desvincular a OS do RA
	 */
	public Collection<SetorComercial> pesquisarSetorComercialPorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Collection<SetorComercial> retornoConsulta = new ArrayList();
		Collection<SetorComercial> retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			String subQuery = this.criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(idUnidadeDestino, permiteTramiteIndependente);

			consulta = "SELECT setorComercial.id, setorComercial.descricao, count(os) " + "FROM OrdemServico os "
							+ "INNER JOIN os.registroAtendimento ragt  " + "INNER JOIN ragt.quadra quadra  "
							+ "INNER JOIN quadra.setorComercial setorComercial  "
							+ "WHERE os.situacao in (1,3) and os.indicadorProgramada = 2 " + "AND os.id in (" + subQuery + ") "
							+ "GROUP BY setorComercial.id, setorComercial.descricao " + "ORDER BY setorComercial.descricao";

			retornoConsulta = session.createQuery(consulta).list();
			if(retornoConsulta.size() > 0){
				SetorComercial setorComercial = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Object[] element = (Object[]) iter.next();
					setorComercial = new SetorComercial();
					setorComercial.setId((Integer) element[0]);
					setorComercial.setDescricao((String) element[1]);
					setorComercial.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(setorComercial);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança para chamar o método criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino
	 * @author Hugo Lima
	 * @date 16/02/2012
	 *       Mudança para desvincular a OS do RA
	 */
	public Collection<DistritoOperacional> pesquisarDistritoOperacionalPorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = new ArrayList();
		Collection<DistritoOperacional> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			String subQuery = this.criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(idUnidadeDestino, permiteTramiteIndependente);

			consulta = "SELECT DISTINCT distritoOperacional.id," // 0
							+ "distritoOperacional.descricao, count(os) " // 1,2
							+ "FROM OrdemServico os "
							+ "INNER JOIN os.registroAtendimento ragt  "
							+ "INNER JOIN ragt.quadra quadra  "
							+ "INNER JOIN quadra.distritoOperacional distritoOperacional  "
							+ "WHERE os.situacao in (1,3) and os.indicadorProgramada = 2 " + "AND os.id in (" + subQuery
							+ ") "
							+ "GROUP BY distritoOperacional.id, distritoOperacional.descricao " + "ORDER BY distritoOperacional.descricao";

			retornoConsulta = session.createQuery(consulta).list();

			if(retornoConsulta.size() > 0){

				retorno = new ArrayList();

				DistritoOperacional distritoOperacional = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					distritoOperacional = new DistritoOperacional();

					distritoOperacional.setId((Integer) element[0]);
					distritoOperacional.setDescricao((String) element[1]);
					distritoOperacional.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(distritoOperacional);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Mudança de um repositorio pra outro; e mudança de SQL para HQL.
	 * @author Hugo Lima
	 * @date 16/02/2012
	 *       Mudança para desvincular a OS do RA
	 */
	public Collection<UnidadeOrganizacional> pesquisarUnidadeOrganizacionalPorRA(Integer idUnidadeDestino,
 Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;
		Collection<UnidadeOrganizacional> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			String subQuery = this.criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(idUnidadeDestino, permiteTramiteIndependente);

			consulta = "SELECT unidOrg.id, unidOrg.descricao, count(OS) "
							+ "FROM UnidadeOrganizacional unidOrg, RegistroAtendimentoUnidade RAUnidade, OrdemServico OS "
							+ "WHERE OS.id in (" + subQuery + ") " + "AND OS.situacao in (1, 3) and OS.indicadorProgramada = 2 "
							+ "AND RAUnidade.registroAtendimento.id = OS.registroAtendimento.id "
							+ "AND RAUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo "
							+ "AND unidOrg.id = RAUnidade.unidadeOrganizacional.id " + "GROUP BY unidOrg.id, unidOrg.descricao";

			retornoConsulta = session.createQuery(consulta).setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

			if(retornoConsulta.size() > 0){
				retorno = new ArrayList();
				UnidadeOrganizacional unidade = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();
					unidade = new UnidadeOrganizacional();
					unidade.setId((Integer) element[0]);
					unidade.setDescricao((String) element[1]);
					unidade.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(unidade);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<DistritoOperacional> pesquisarDistritoOperacionalPorUnidade(Integer unidade) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = new ArrayList();
		Collection<DistritoOperacional> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT distritoOperacional.id," // 0
							+ "distritoOperacional.descricao, count(os) " // 1
							+ "FROM OrdemServicoUnidade osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  "
							+ "INNER JOIN osUnidade.ordemServico os  "
							+ "INNER JOIN osUnidade.unidadeOrganizacional unid  "
							+ "INNER JOIN os.imovel imov  "
							+ "INNER JOIN imov.quadra quadra  "
							+ "INNER JOIN quadra.distritoOperacional distritoOperacional  "
							+ "LEFT JOIN os.registroAtendimento ra  "
							+ "WHERE ra IS NULL " + "AND os.situacao in (1,3) and os.indicadorProgramada = 2 "
							+ "AND unid.id = :unidade "
							+ "AND art.id = :idAtendimentoTipo "
							+ "GROUP BY distritoOperacional.id, distritoOperacional.descricao "
							+ "ORDER BY distritoOperacional.descricao";

			retornoConsulta = session.createQuery(consulta).setInteger("unidade", unidade)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

			if(retornoConsulta.size() > 0){

				retorno = new ArrayList();

				DistritoOperacional distritoOperacional = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					distritoOperacional = new DistritoOperacional();

					distritoOperacional.setId((Integer) element[0]);
					distritoOperacional.setDescricao((String) element[1]);
					distritoOperacional.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(distritoOperacional);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<ServicoPerfilTipo> pesquisarServicoPerfilTipoPorUnidade(Integer unidadeLotacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT DISTINCT sptp " + "FROM Equipe eqpe " + "INNER JOIN eqpe.unidadeOrganizacional unid "
							+ "INNER JOIN eqpe.servicoPerfilTipo sptp " + "WHERE unid.id = :unidadeLotacao " + "ORDER BY sptp.descricao";

			retorno = session.createQuery(consulta).setInteger("unidadeLotacao", unidadeLotacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0471] Obter Dados da Equipe
	 * 
	 * @author Raphael Rossiter
	 * @date 01/09/2006
	 * @return idQuipe
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEquipe(Integer idEquipe) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT eqpe.id, eqpe.nome, eqpe.placaVeiculo, eqpe.cargaTrabalho, "
							+ " unid.id, unid.descricao, sptp.id, sptp.descricao, eqtp.id, eqtp.descricao " + " FROM Equipe eqpe "
							+ " INNER JOIN eqpe.unidadeOrganizacional unid " + " INNER JOIN eqpe.servicoPerfilTipo sptp "
							+ " LEFT JOIN eqpe.equipeTipo eqtp "
							+ " WHERE eqpe.id = :idEquipe ";

			retorno = session.createQuery(consulta).setInteger("idEquipe", idEquipe).setMaxResults(1).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0471] Obter Dados da Equipe
	 * 
	 * @author Raphael Rossiter
	 * @date 01/09/2006
	 * @return idQuipe
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEquipeComponentes(Integer idEquipe) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT eqme.id, eqme.indicadorResponsavel, func.id, func.nome, eqme.componentes" + " FROM EquipeComponentes eqme "
							+ " INNER JOIN eqme.equipe eqpe " + " LEFT JOIN eqme.funcionario func " + " WHERE eqpe.id = :idEquipe ";

			retorno = session.createQuery(consulta).setInteger("idEquipe", idEquipe).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0465] Filtrar Ordem Serviço
	 * Recupera OS programadas
	 * 
	 * @author Leonardo Regis
	 * @date 05/09/2006
	 * @param situacaoProgramacao
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> recuperaOSPorProgramacao(short situacaoProgramacao) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consultaOSNaoProgramada = "";
		String consultaOSProgramada = "";
		String consulta = "";
		try{
			consultaOSProgramada = "select os.orse_id as idOSP " + " from ordem_servico os, " + " ordem_servico_programacao osp "
							+ " where os.orse_id = osp.orse_id and osp.ospg_icativo = 1  "
							+ " or (osp.ospg_icativo = 2 and osp.ospg_cdsituacaofechamento = 2)";

			consultaOSNaoProgramada = "select os.orse_id as idOSNP " + " from ordem_servico os "
							+ " LEFT OUTER JOIN ordem_servico_programacao osp ON osp.orse_id=os.orse_id " + " where os.orse_id not in ("
							+ consultaOSProgramada + ") " + " or osp.ospg_icativo = 2 " + " and osp.ospg_cdsituacaofechamento <> 2";

			String alias = "";
			if(situacaoProgramacao == ConstantesSistema.SIM.shortValue()){
				alias = "idOSP";
				consulta = consultaOSProgramada;
			}else if(situacaoProgramacao == ConstantesSistema.NAO.shortValue()){
				consulta = consultaOSNaoProgramada;
				alias = "idOSNP";
			}

			retornoConsulta = session.createSQLQuery(consulta).addScalar(alias, Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB0003] Seleciona Ordem de Servico por Criterio de Seleção
	 * [SB0004] Seleciona Ordem de Servico por Situacao de Diagnostico
	 * [SB0005] Seleciona Ordem de Servico por Situacao de Acompanhamento pela Agencia
	 * [SB0006] Seleciona Ordem de Servico por Critério Geral
	 * 
	 * @author Rafael Pinto
	 * @date 07/09/2006
	 * @author Saulo Lima
	 * @date 01/02/2009
	 *       Determinar o Timestamp para as datas do filtro (dd/MM/aaaa 00:00:00) ou (dd/MM/aaaa
	 *       23:59:59 999)
	 * @author Saulo Lima
	 * @date 13/0/2012
	 *       Correção para contemplar OS Seletiva quando parâmetro de Tramite de OS Independente
	 *       estiver ativo
	 * @param PesquisarOrdemServicoHelper
	 * @return Collection<OrdemServico>
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServico> pesquisarOrdemServicoElaborarProgramacao(PesquisarOrdemServicoHelper filtro,
					Short permiteTramiteIndependente) throws ErroRepositorioException{

		Collection<OrdemServico> retorno = new ArrayList();
		Collection<Object[]> retornoConsulta = new ArrayList();

		Integer criterioSelecao = filtro.getCriterioSelecao();
		int origemServico = filtro.getOrigemServico();

		Integer[] tipoServicos = filtro.getTipoServicos();

		short servicoDiagnosticado = filtro.getServicoDiagnosticado();
		short servicoAcompanhamento = filtro.getServicoAcompanhamento();

		Date dataAtrasoInicial = filtro.getDataAtrasoInicial();
		Date dataAtrasoFinal = filtro.getDataAtrasoFinal();

		Date dataAtendimentoInicial = filtro.getDataAtendimentoInicial();
		Date dataAtendimentoFinal = filtro.getDataAtendimentoFinal();

		Date dataGeracaoInicial = filtro.getDataGeracaoInicial();
		Date dataGeracaoFinal = filtro.getDataGeracaoFinal();

		Date dataPrevisaoClienteInicial = filtro.getDataPrevisaoClienteInicial();
		Date dataPrevisaoClienteFinal = filtro.getDataPrevisaoClienteFinal();

		Date dataPrevisaoAgenciaInicial = filtro.getDataPrevisaoAgenciaInicial();
		Date dataPrevisaoAgenciaFinal = filtro.getDataPrevisaoAgenciaFinal();

		String indicadorProcessoAdmJud = filtro.getIndicadorProcessoAdmJud();

		Integer quantidadeDiasUnidade = filtro.getQuantidadeDiasUnidade();

		Integer[] unidadeLotacao = new Integer[1];
		unidadeLotacao[0] = filtro.getUnidadeLotacao();

		Session session = HibernateUtil.getSession();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		try{

			consulta = "SELECT distinct os.id," // 0
							+ "servicoTipo.id,"// 1
							+ "servicoTipo.descricao,"// 2
							+ "ra.id," // 3
							+ "cobra.id," // 4
							+ "os.dataGeracao,"// 5
							+ "os.dataEncerramento,"// 6
							+ "ra.dataPrevistaAtual,"// 7
							+ "os.situacao, "// 8
							+ "servicoTipoPrioridadeAtual.id, "// 9
							+ "os.servicoTipoPrioridadeOriginal.id "; // 10

			// [OC0902365] Alteração Ordenação por localidade, setor comercial e quadra, mostrar
			// localidade e setor comercial na tela.
			consulta += ", imov.id, " // 11
							+ "imovLocal.id, " // 12
							+ "imovLocal.descricao, " // 13
							+ "imovSetor.id, " // 14
							+ "imovSetor.descricao, " // 15
							+ "imovQuadra.id, " // 16
							+ "os.dataExecucao "; // 17

			consulta += "FROM OrdemServico os " + "INNER JOIN os.ordemServicoUnidades osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  " + "INNER JOIN osUnidade.unidadeOrganizacional unid  "
							+ "INNER JOIN os.servicoTipo servicoTipo "
							+ "INNER JOIN os.servicoTipoPrioridadeAtual servicoTipoPrioridadeAtual "
							+ "INNER JOIN servicoTipo.servicoPerfilTipo servicoPerfilTipo " //
							// [OC0902365] Alteração Ordenação por localidade, setor comercial e
							// quadra, mostrar
							// localidade e setor comercial na tela.
							+ "LEFT JOIN os.cobrancaDocumento cobra " //
							+ "LEFT JOIN os.imovel imov " //
							+ "LEFT JOIN imov.localidade imovLocal " //
							+ "LEFT JOIN imov.quadra imovQuadra " //
							+ "LEFT JOIN imov.setorComercial imovSetor ";

			// Solicitados
			if(origemServico == 1){

				consulta += "INNER JOIN os.registroAtendimento ra " + "INNER JOIN ra.localidade raLocal "
								+ "LEFT JOIN ra.setorComercial raSetor " + "LEFT JOIN ra.quadra raQuadra "
								+ "LEFT JOIN raQuadra.distritoOperacional raDistrito  ";
				// + "LEFT JOIN os.cobrancaDocumento cobra ";

				// Selecionados
			}else if(origemServico == 2){

				// consulta += "LEFT JOIN os.cobrancaDocumento cobra " + "LEFT JOIN os.imovel imov "
				// + "LEFT JOIN imov.localidade imovLocal "
				// + "LEFT JOIN imov.quadra imovQuadra " +
				// "LEFT JOIN imov.setorComercial imovSetor "
				consulta += "LEFT JOIN imovQuadra.distritoOperacional imovDistrito " + "LEFT JOIN os.registroAtendimento ra ";

				// Ambos
			}else{

				consulta += "LEFT JOIN os.registroAtendimento ra " + "LEFT JOIN ra.localidade raLocal "
								+ "LEFT JOIN ra.setorComercial raSetor " + "LEFT JOIN ra.quadra raQuadra "
								+ "LEFT JOIN raQuadra.distritoOperacional raDistrito  ";

				// consulta += "LEFT JOIN os.cobrancaDocumento cobra " + "LEFT JOIN os.imovel imov "
				// + "LEFT JOIN imov.localidade imovLocal "
				// + "LEFT JOIN imov.quadra imovQuadra " +
				// "LEFT JOIN imov.setorComercial imovSetor "
				consulta += "LEFT JOIN imovQuadra.distritoOperacional imovDistrito ";
			}

			if(servicoAcompanhamento == ConstantesSistema.SIM.shortValue() || dataPrevisaoAgenciaInicial != null
							&& dataPrevisaoAgenciaFinal != null){

				consulta += "INNER JOIN ra.raDadosAgenciaReguladoras raDados ";
			}

			if(criterioSelecao.intValue() == 3){
				// Solicitados
				if(origemServico == 1){
					consulta += "INNER JOIN ra.registroAtendimentoUnidades raUnidade ";
					// Ambos
				}else if(origemServico == ConstantesSistema.TODOS){
					consulta += "LEFT JOIN ra.registroAtendimentoUnidades raUnidade ";
				}
			}

			if(quantidadeDiasUnidade != null && quantidadeDiasUnidade > 0){

				if(permiteTramiteIndependente.intValue() == 1){
					consulta += "left join os.tramites tramites ";

				}else{
					consulta += "left join os.registroAtendimento.tramites tramites ";

				}
			}

			consulta += " WHERE os.situacao in (1,3) ";
			if(quantidadeDiasUnidade != null && quantidadeDiasUnidade > 0){
				if(permiteTramiteIndependente.intValue() == 1){
					consulta += " and trunc(CURRENT_DATE()) - trunc(tramites.dataTramite) >= :quantidadeDiasUnidade ";
				parameters.put("quantidadeDiasUnidade", Double.valueOf(quantidadeDiasUnidade));
			}else{
					consulta += " and trunc(CURRENT_DATE()) - trunc(tramites.dataTramite) >= :quantidadeDiasUnidade ";
				parameters.put("quantidadeDiasUnidade", Double.valueOf(quantidadeDiasUnidade));
			}
			}

			consulta += "AND os.indicadorProgramada = :naoProgramada ";
			parameters.put("naoProgramada", OrdemServico.NAO_PROGRAMADA);

			// [SB0003] - Seleciona Ordem de Servico por Criterio de Seleção
			if(tipoServicos != null){
				switch(criterioSelecao.intValue()){

					case 1:

						// Tipo de Serviço
						consulta += " AND servicoTipo.id in (:idServicoTipo) ";
						break;

					case 2:

						// Perfil
						consulta += " AND servicoPerfilTipo.id in (:idServicoTipo) ";
						break;

					case 3:
						// Unidade Atendimento

						// Solicitados
						if(origemServico == 1){

							consulta += " AND raUnidade.unidadeOrganizacional.id in (:idServicoTipo) "
											+ "AND raUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo ";

							// Selecionados
						}else if(origemServico == 2){

							consulta += " AND osUnidade.unidadeOrganizacional.id in (:idServicoTipo) "
											+ "AND osUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo ";

							// Ambos
						}else{

							consulta += " AND ( (raUnidade.unidadeOrganizacional.id in (:idServicoTipo) "
											+ "AND raUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo) ";

							consulta += " OR ( ra is null AND osUnidade.unidadeOrganizacional.id in (:idServicoTipo) "
											+ "AND osUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo) ) ";
						}
						parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);

						break;

					case 4:

						// Localidade

						// Solicitados
						if(origemServico == 1){

							consulta += " AND raLocal.id in (:idServicoTipo) ";

							// Selecionados
						}else if(origemServico == 2){
							consulta += " AND imovLocal.id in (:idServicoTipo) ";

							// Ambos
						}else{

							consulta += " AND ( raLocal.id in (:idServicoTipo) OR ";
							consulta += " imovLocal.id in (:idServicoTipo) ) ";
						}

						break;

					case 5:

						// Setor Comercial

						// Solicitados
						if(origemServico == 1){

							consulta += " AND raSetor.id in (:idServicoTipo) ";

							// Selecionados
						}else if(origemServico == 2){
							consulta += " AND imovSetor.id in (:idServicoTipo) ";

							// Ambos
						}else{

							consulta += " AND ( raSetor.id in (:idServicoTipo) OR ";
							consulta += " imovSetor.id in (:idServicoTipo) ) ";
						}

						break;

					case 6:

						// Distrito Operacional
						// Solicitados
						if(origemServico == 1){

							consulta += " AND  raDistrito.id in (:idServicoTipo) ";

							// Selecionados
						}else if(origemServico == 2){
							consulta += " AND imovDistrito.id in (:idServicoTipo) ";

							// Ambos
						}else{

							consulta += " AND ( raDistrito.id in (:idServicoTipo) OR ";
							consulta += " imovDistrito.id in (:idServicoTipo) ) ";
						}

						break;
					case 7:

						// Bairro
						// Solicitados
						if(origemServico == 1){

							consulta += " AND  exists( select 1 from LogradouroBairro logBairr "
											+ " where ra.logradouroBairro.id = logBairr.id and logBairr.bairro.id in (:idServicoTipo) )";

							// Selecionados
						}else if(origemServico == 2){
							consulta += " AND  exists( select 1 from LogradouroBairro logBairr "
											+ " where imov.logradouroBairro.id = logBairr.id and logBairr.bairro.id in (:idServicoTipo) )";

							// Ambos
						}else{

							consulta += " AND  ( exists( select 1 from LogradouroBairro logBairr "
											+ " where ra.logradouroBairro.id = logBairr.id and logBairr.bairro.id in (:idServicoTipo)) OR ";
							consulta += " exists( select 1 from LogradouroBairro logBairr "
											+ " where imov.logradouroBairro.id = logBairr.id and logBairr.bairro.id in (:idServicoTipo) ) ) ";
						}

						break;
					case 8:

						// Prioridade
						// Solicitados
						if(origemServico == 1){

							consulta += " AND  ra.id is not null and os.servicoTipoPrioridadeAtual.id in (:idServicoTipo) ";

							// Selecionados
						}else if(origemServico == 2){

							consulta += " AND  ra.id is null and os.servicoTipoPrioridadeAtual.id in (:idServicoTipo) ";

							// Ambos
						}else{

							consulta += " AND  ( (ra.id is not null and os.servicoTipoPrioridadeAtual.id in (:idServicoTipo)) OR ";
							consulta += " (ra.id is null and os.servicoTipoPrioridadeAtual.id in (:idServicoTipo)) ) ";
						}

						break;

				}

				parameters.put("idServicoTipo", tipoServicos);
			}

			// [SB0004] - Seleciona Ordem de Servico por Situacao de Diagnostico
			if(servicoDiagnosticado != ConstantesSistema.NUMERO_NAO_INFORMADO){
				consulta += " AND os.indicadorServicoDiagnosticado = :servicoDiagnosticado ";
				parameters.put("servicoDiagnosticado", servicoDiagnosticado);
			}

			// [SB0005] - Seleciona Ordem de Servico por Situacao de
			// Acompanhamento pela Agencia
			if(servicoAcompanhamento == ConstantesSistema.NAO.shortValue()){

				consulta += " AND ra NOT IN " + "(SELECT raDadosAgencia.registroAtendimento FROM RaDadosAgenciaReguladora raDadosAgencia) ";

			}else if(dataPrevisaoAgenciaInicial != null && dataPrevisaoAgenciaFinal != null){

				consulta += " AND raDados.dataPrevisaoAtual BETWEEN (:dataPrevisaoAgenciaInicial) AND (:dataPrevisaoAgenciaFinal) ";

				dataPrevisaoAgenciaInicial = Util.formatarDataInicial(dataPrevisaoAgenciaInicial);
				dataPrevisaoAgenciaFinal = Util.adaptarDataFinalComparacaoBetween(dataPrevisaoAgenciaFinal);

				parameters.put("dataPrevisaoAgenciaInicial", dataPrevisaoAgenciaInicial);
				parameters.put("dataPrevisaoAgenciaFinal", dataPrevisaoAgenciaFinal);

			}

			// Indicador Processo Administrativo / Judicial
			if(!Util.isVazioOuBranco(indicadorProcessoAdmJud)){
				if(!indicadorProcessoAdmJud.equals(ConstantesSistema.TODOS.toString())){
					if(indicadorProcessoAdmJud.equals(ConstantesSistema.SIM.toString())){
						consulta += " AND ra.indicadorProcessoAdmJud = " + ConstantesSistema.SIM;
					}else{
						consulta += " AND ra.indicadorProcessoAdmJud = " + ConstantesSistema.NAO;
					}
				}
			}

			// [SB0006] - Seleciona Ordem de Servico por Critério Geral
			String subQuery = "";

			if(permiteTramiteIndependente.intValue() == ConstantesSistema.SIM.intValue()){

				subQuery = "SELECT DISTINCT ordem.id " + "FROM Tramite tramite " + "INNER JOIN tramite.ordemServico ordem "
								+ "WHERE tramite.unidadeOrganizacionalDestino.id = :unidadeLotacao AND tramite.dataTramite = "
								+ "(SELECT MAX(tram.dataTramite) FROM Tramite tram " + "WHERE tram.ordemServico.id = ordem.id) "
								+ "GROUP BY ordem.id";
			}else{

				subQuery = "SELECT ordem.id FROM OrdemServico ordem "
								+ " WHERE ordem.registroAtendimento.id in (SELECT  reg.id FROM Tramite tramit "
								+ " INNER JOIN tramit.registroAtendimento reg "
								+ " WHERE tramit.unidadeOrganizacionalDestino.id = :unidadeLotacao AND tramit.dataTramite = "
								+ " (SELECT MAX(tram.dataTramite) FROM Tramite tram WHERE tram.registroAtendimento.id = reg.id)) ";
			}

			// Solicitados
			if(origemServico == 1){

				consulta += " AND os.id in (" + subQuery + ")";

				parameters.put("unidadeLotacao", unidadeLotacao);

				// Selecionados
			}else if(origemServico == 2){

				consulta += " AND unid.id = :unidadeLotacao";
				consulta += " AND art.id = :idAtendimentoTipo ";
				consulta += " AND ra is null ";

				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
				parameters.put("unidadeLotacao", unidadeLotacao);

				// Ambos
			}else{

				consulta += " AND (( os.id in (" + subQuery + ") AND ra is not null) OR "
								+ " (ra is null AND unid.id = :unidadeLotacao AND art.id = :idAtendimentoTipo )) ";

				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
				parameters.put("unidadeLotacao", unidadeLotacao);

			}

			// Período de Atraso
			if(dataAtrasoInicial != null && dataAtrasoFinal != null){

				consulta += " AND ( ra.dataPrevistaAtual BETWEEN (:dataAtrasoInicial) AND CURRENT_DATE ";
				consulta += " OR ra.dataPrevistaAtual BETWEEN (:dataAtrasoFinal) AND CURRENT_DATE ) ";

				dataAtrasoInicial = Util.formatarDataInicial(dataAtrasoInicial);
				dataAtrasoFinal = Util.adaptarDataFinalComparacaoBetween(dataAtrasoFinal);

				parameters.put("dataAtrasoInicial", dataAtrasoInicial);
				parameters.put("dataAtrasoFinal", dataAtrasoFinal);

			}else if(dataAtrasoInicial != null){

				dataAtrasoInicial = Util.formatarDataInicial(dataAtrasoInicial);
				consulta += " AND ra.dataPrevistaAtual >= (:dataAtrasoInicial) ";
				parameters.put("dataAtrasoInicial", dataAtrasoInicial);
			}

			// Período de Atendimento
			if(dataAtendimentoInicial != null && dataAtendimentoFinal != null){

				// Solicitados
				if(origemServico == 1){

					consulta += " AND ra.registroAtendimento BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal) ";

					// Selecionados
				}else if(origemServico == 2){

					consulta += " AND os.dataGeracao BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal)" + " AND ra IS NULL ";

					// Ambos
				}else{

					consulta += " AND ( ra.registroAtendimento BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal) OR ";
					consulta += " (ra IS NULL AND os.dataGeracao BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal) ) ) ";

				}

				dataAtendimentoInicial = Util.formatarDataInicial(dataAtendimentoInicial);
				dataAtendimentoFinal = Util.adaptarDataFinalComparacaoBetween(dataAtendimentoFinal);

				parameters.put("dataAtendimentoInicial", dataAtendimentoInicial);
				parameters.put("dataAtendimentoFinal", dataAtendimentoFinal);
			}

			// Período de Data Geracao
			if(dataGeracaoInicial != null && dataGeracaoFinal != null){

				consulta += " AND os.dataGeracao BETWEEN (:dataGeracaoInicial) AND (:dataGeracaoFinal) ";

				dataGeracaoInicial = Util.formatarDataInicial(dataGeracaoInicial);
				dataGeracaoFinal = Util.adaptarDataFinalComparacaoBetween(dataGeracaoFinal);

				parameters.put("dataGeracaoInicial", dataGeracaoInicial);
				parameters.put("dataGeracaoFinal", dataGeracaoFinal);
			}

			// Período de Previsão para Cliente
			if(dataPrevisaoClienteInicial != null && dataPrevisaoClienteFinal != null){

				consulta += " AND ra.dataPrevistaAtual BETWEEN (:dataPrevisaoClienteInicial) AND (:dataPrevisaoClienteFinal) ";

				dataPrevisaoClienteInicial = Util.formatarDataInicial(dataPrevisaoClienteInicial);
				dataPrevisaoClienteFinal = Util.adaptarDataFinalComparacaoBetween(dataPrevisaoClienteFinal);

				parameters.put("dataPrevisaoClienteInicial", dataPrevisaoClienteInicial);
				parameters.put("dataPrevisaoClienteFinal", dataPrevisaoClienteFinal);
			}

			// if(origemServico == 3){
			consulta += " ORDER BY imovLocal.id, imovSetor.id, imovQuadra.id, servicoTipoPrioridadeAtual.id ";
			// }

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Date){
					Date data = (Date) parameters.get(key);
					query.setTimestamp(key, data);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retornoConsulta = query.list();

			if(retornoConsulta.size() > 0){

				retorno = new ArrayList();

				OrdemServico os = null;
				ServicoTipo servicoTipo = null;
				RegistroAtendimento registro = null;
				CobrancaDocumento cobranca = null;
				ServicoTipoPrioridade servicoTipoPrioridade = null;

				Imovel imovel = null;
				Localidade localidade = null;
				SetorComercial setorComercial = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					os = new OrdemServico();
					os.setId((Integer) element[0]);

					servicoTipo = new ServicoTipo();
					servicoTipo.setId((Integer) element[1]);
					servicoTipo.setDescricao((String) element[2]);

					if(element[3] != null){
						registro = new RegistroAtendimento();
						registro.setId((Integer) element[3]);
						registro.setDataPrevistaAtual((Date) element[7]);
					}else{
						registro = null;
					}

					if(element[4] != null){
						cobranca = new CobrancaDocumento();
						cobranca.setId((Integer) element[4]);
					}else{
						cobranca = null;
					}

					if(element[9] != null){
						servicoTipoPrioridade = new ServicoTipoPrioridade();
						servicoTipoPrioridade.setId((Integer) element[9]);
					}else{
						servicoTipoPrioridade = null;
					}

					if(element[10] != null){
						ServicoTipoPrioridade servicoTipoPrioridadeOriginal = new ServicoTipoPrioridade();
						servicoTipoPrioridadeOriginal.setId((Integer) element[10]);
						os.setServicoTipoPrioridadeOriginal(servicoTipoPrioridadeOriginal);
					}

					// [OC0902365] Alteração Ordenação por localidade, setor comercial e quadra,
					// mostrar
					// localidade e setor comercial na tela.
					if(element[12] != null){
						localidade = new Localidade();
						localidade.setId((Integer) element[12]);
						localidade.setDescricao(String.valueOf(element[13]));
					}

					if(element[14] != null){
						setorComercial = new SetorComercial();
						setorComercial.setId((Integer) element[14]);
						setorComercial.setDescricao(String.valueOf(element[15]));
					}

					if(element[11] != null){
						imovel = new Imovel();
						imovel.setId((Integer) element[11]);
						imovel.setLocalidade(localidade);
						imovel.setSetorComercial(setorComercial);
					}

					os.setDataGeracao((Date) element[5]);
					os.setDataEncerramento((Date) element[6]);

					if(element[11] != null){
						os.setDataExecucao((Date) element[17]);
					}

					os.setSituacao((Short) element[8]);

					os.setCobrancaDocumento(cobranca);
					os.setRegistroAtendimento(registro);
					os.setServicoTipo(servicoTipo);
					os.setServicoTipoPrioridadeAtual(servicoTipoPrioridade);

					os.setImovel(imovel);

					retorno.add(os);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0465] Filtrar Ordem Serviço
	 * Recupera OS por Data de Programação
	 * 
	 * @author Leonardo Regis
	 * @date 05/09/2006
	 * @param dataProgramacaoInicial
	 * @param dataProgramacaoFinal
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> recuperaOSPorDataProgramacao(Date dataProgramacaoInicial, Date dataProgramacaoFinal)
					throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "select os.orse_id as idOS " + " from ordem_servico os, " + " ordem_servico_programacao osp, "
							+ " programacao_roteiro pr " + " where osp.orse_id=os.orse_id " + " and osp.pgrt_id=pr.pgrt_id "
							+ " and pr.pgrt_tmroteiro between (:dataProgramacaoInicial) and (:dataProgramacaoFinal) "
							+ " and (osp.ospg_icativo = 1 " + " or (osp.ospg_icativo = 2 and osp.ospg_cdsituacaofechamento = 2)) ";

			retornoConsulta = session.createSQLQuery(consulta).addScalar("idOS", Hibernate.INTEGER)
							.setDate("dataProgramacaoInicial", Util.formatarDataInicial(dataProgramacaoInicial))
							.setDate("dataProgramacaoFinal", Util.formatarDataFinal(dataProgramacaoFinal)).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * obter OsAtividadeMaterialExecucao por OS
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idOS
	 * @return Collection<OsAtividadeMaterialExecucao>
	 * @throws ErroRepositorioException
	 */
	public Collection<OsAtividadeMaterialExecucao> obterOsAtividadeMaterialExecucaoPorOS(Integer idOS) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;
		Collection<OsAtividadeMaterialExecucao> colecaoOsAtividadeMaterialExecucao = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT osame.id, " // 0
							+ " osame.quantidadeMaterial, " // 1
							+ " a.id, " // 2
							+ " a.descricao, " // 3
							+ " m.id, " // 4
							+ " m.descricao, " // 5
							+ " mu.id, " // 6
							+ " mu.descricao " // 7
							+ "FROM OsAtividadeMaterialExecucao osame "
							+ "INNER JOIN osame.material m  "
							+ "INNER JOIN m.materialUnidade mu  "
							+ "INNER JOIN osame.ordemServicoAtividade osa  "
							+ "INNER JOIN osa.atividade a  " + "INNER JOIN osa.ordemServico os  "
							+ "WHERE os.id = :idOS "
							+ "ORDER BY a.descricao ";

			retornoConsulta = (Collection<Object[]>) session.createQuery(consulta).setInteger("idOS", idOS).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){
				OsAtividadeMaterialExecucao osAtividadeMaterialExecucao = null;
				for(Object[] execucao : retornoConsulta){
					osAtividadeMaterialExecucao = new OsAtividadeMaterialExecucao();
					osAtividadeMaterialExecucao.setId((Integer) execucao[0]);
					osAtividadeMaterialExecucao.setQuantidadeMaterial((BigDecimal) execucao[1]);
					OrdemServicoAtividade ordemServicoAtividade = new OrdemServicoAtividade();
					Atividade atividade = new Atividade();
					atividade.setId((Integer) execucao[2]);
					atividade.setDescricao((String) execucao[3]);
					ordemServicoAtividade.setAtividade(atividade);
					OrdemServico ordemServico = new OrdemServico();
					ordemServico.setId(idOS);
					ordemServicoAtividade.setOrdemServico(ordemServico);
					osAtividadeMaterialExecucao.setOrdemServicoAtividade(ordemServicoAtividade);
					Material material = new Material();
					material.setId((Integer) execucao[4]);
					material.setDescricao((String) execucao[5]);
					osAtividadeMaterialExecucao.setMaterial(material);
					MaterialUnidade materialUnidade = new MaterialUnidade();
					materialUnidade.setId((Integer) execucao[6]);
					materialUnidade.setDescricao((String) execucao[7]);
					material.setMaterialUnidade(materialUnidade);
					colecaoOsAtividadeMaterialExecucao.add(osAtividadeMaterialExecucao);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return colecaoOsAtividadeMaterialExecucao;
	}

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * obter OsAtividadePeriodoExecucao por OS
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idOS
	 * @return OsExecucaoEquipe
	 * @throws ErroRepositorioException
	 */
	public Collection<OsExecucaoEquipe> obterOsExecucaoEquipePorOS(Integer idOS) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;
		Collection<OsExecucaoEquipe> colecaoOsExecucaoEquipe = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT DISTINCT osape.id, " // 0
							+ " osape.dataInicio, " // 1
							+ " osape.dataFim, " // 2
							+ " a.id, " // 3
							+ " a.descricao, " // 4
							+ " e.id, " // 5
							+ " e.nome " // 6
							+ "FROM OsExecucaoEquipe osee "
							+ "INNER JOIN osee.equipe e "
							+ "INNER JOIN osee.osAtividadePeriodoExecucao osape  "
							+ "INNER JOIN osape.ordemServicoAtividade osa  "
							+ "INNER JOIN osa.atividade a  " + "INNER JOIN osa.ordemServico os  "
							+ "WHERE os.id = :idOS "
							+ "ORDER BY a.descricao ";

			retornoConsulta = (Collection<Object[]>) session.createQuery(consulta).setInteger("idOS", idOS).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){
				OsExecucaoEquipe osExecucaoEquipe = null;
				for(Object[] periodo : retornoConsulta){
					osExecucaoEquipe = new OsExecucaoEquipe();
					Equipe equipe = new Equipe();
					equipe.setId((Integer) periodo[5]);
					equipe.setNome((String) periodo[6]);
					osExecucaoEquipe.setEquipe(equipe);
					OsAtividadePeriodoExecucao osAtividadePeriodoExecucao = new OsAtividadePeriodoExecucao();
					osAtividadePeriodoExecucao.setId((Integer) periodo[0]);
					osAtividadePeriodoExecucao.setDataInicio((Date) periodo[1]);
					osAtividadePeriodoExecucao.setDataFim((Date) periodo[2]);
					OrdemServicoAtividade ordemServicoAtividade = new OrdemServicoAtividade();
					Atividade atividade = new Atividade();
					atividade.setId((Integer) periodo[3]);
					atividade.setDescricao((String) periodo[4]);
					ordemServicoAtividade.setAtividade(atividade);
					OrdemServico ordemServico = new OrdemServico();
					ordemServico.setId(idOS);
					ordemServicoAtividade.setOrdemServico(ordemServico);
					osAtividadePeriodoExecucao.setOrdemServicoAtividade(ordemServicoAtividade);
					osExecucaoEquipe.setOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao);
					colecaoOsExecucaoEquipe.add(osExecucaoEquipe);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return colecaoOsExecucaoEquipe;
	}

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * obter OsAtividadePeriodoExecucao por OS
	 * 
	 * @author Rafael Pinto
	 * @date 09/09/2006
	 * @param idOS
	 * @return OsAtividadePeriodoExecucao
	 * @throws ErroRepositorioException
	 */
	public Collection<OsAtividadePeriodoExecucao> obterOsAtividadePeriodoExecucaoPorOS(Integer idOS, Date dataRoteiro)
					throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;
		Collection<OsAtividadePeriodoExecucao> colecaoOsPeriodo = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{

			consulta = "SELECT DISTINCT osAtividadePeriodoExecucao.id, " // 0
							+ "osAtividadePeriodoExecucao.dataInicio, " // 1
							+ "osAtividadePeriodoExecucao.dataFim " // 2
							+ "FROM OsAtividadePeriodoExecucao osAtividadePeriodoExecucao "
							+ "INNER JOIN osAtividadePeriodoExecucao.ordemServicoAtividade ordemServicoAtividade "
							// + "INNER JOIN ordemServicoAtividade.ordemServico ordemServico  "
							+ "WHERE ordemServicoAtividade.ordemServico.id = :idOS "
							+ "AND  osAtividadePeriodoExecucao.dataFim <= :dataRoteiro " + "ORDER BY osAtividadePeriodoExecucao.id ";

			retornoConsulta = (Collection<Object[]>) session.createQuery(consulta).setInteger("idOS", idOS)
							.setDate("dataRoteiro", dataRoteiro).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){

				OsAtividadePeriodoExecucao osAtividadePeriodoExecucao = null;

				for(Object[] periodo : retornoConsulta){

					osAtividadePeriodoExecucao = new OsAtividadePeriodoExecucao();

					osAtividadePeriodoExecucao.setId((Integer) periodo[0]);
					osAtividadePeriodoExecucao.setDataInicio((Date) periodo[1]);
					osAtividadePeriodoExecucao.setDataFim((Date) periodo[2]);

					colecaoOsPeriodo.add(osAtividadePeriodoExecucao);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return colecaoOsPeriodo;
	}

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * obter OsAtividadePeriodoExecucao por OS
	 * 
	 * @author Rafael Pinto
	 * @date 09/09/2006
	 * @param idEquipe
	 * @param dataRoteiro
	 * @return OsAtividadePeriodoExecucao
	 * @throws ErroRepositorioException
	 */
	public Collection<OsAtividadePeriodoExecucao> obterOsAtividadePeriodoExecucaoPorEquipe(Integer idEquipe, Date dataRoteiro)
					throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;
		Collection<OsAtividadePeriodoExecucao> colecaoOsPeriodo = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{

			consulta = "SELECT DISTINCT osAtividadePeriodoExecucao.id, " // 0
							+ "osAtividadePeriodoExecucao.dataInicio, " // 1
							+ "osAtividadePeriodoExecucao.dataFim " // 2
							+ "FROM OsExecucaoEquipe osExecucaoEquipe "
							// + "INNER JOIN osExecucaoEquipe.equipe equipe "
							+ "INNER JOIN osExecucaoEquipe.osAtividadePeriodoExecucao osAtividadePeriodoExecucao "
							// +
							// "INNER JOIN osAtividadePeriodoExecucao.ordemServicoAtividade ordemServicoAtividade "
							+ "WHERE osExecucaoEquipe.equipe.id = :idEquipe "
							+ "AND osAtividadePeriodoExecucao.ordemServicoAtividade.id IS NOT NULL "
							+ "AND day(osAtividadePeriodoExecucao.dataFim) = day(cast(:dataRoteiro as date)) "
							+ "AND month(osAtividadePeriodoExecucao.dataFim) = month(cast(:dataRoteiro as date)) "
							+ "AND year(osAtividadePeriodoExecucao.dataFim) = year(cast(:dataRoteiro as date)) "
							+ "ORDER BY osAtividadePeriodoExecucao.id ";

			retornoConsulta = (Collection<Object[]>) session.createQuery(consulta).setInteger("idEquipe", idEquipe)
							.setDate("dataRoteiro", dataRoteiro).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){

				OsAtividadePeriodoExecucao osAtividadePeriodoExecucao = null;

				for(Object[] periodo : retornoConsulta){

					osAtividadePeriodoExecucao = new OsAtividadePeriodoExecucao();

					osAtividadePeriodoExecucao.setId((Integer) periodo[0]);
					osAtividadePeriodoExecucao.setDataInicio((Date) periodo[1]);
					osAtividadePeriodoExecucao.setDataFim((Date) periodo[2]);

					colecaoOsPeriodo.add(osAtividadePeriodoExecucao);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return colecaoOsPeriodo;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 11/09/2006
	 * @param dataRoteiro
	 * @return collection
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiro(Date dataRoteiro) throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN FETCH osProgramacao.programacaoRoteiro programacaoRoteiro  "
							+ "INNER JOIN FETCH osProgramacao.equipe equipe  " + "LEFT JOIN FETCH osProgramacao.ordemServico ordemServico "
							+ "LEFT JOIN FETCH ordemServico.servicoTipo servicoTipo "
							+ "LEFT JOIN FETCH servicoTipo.servicoPerfilTipo servicoPerfilTipo "
							+ "LEFT JOIN FETCH ordemServico.registroAtendimento registroAtendimento "
							+ "INNER JOIN FETCH equipe.servicoPerfilTipo servicoPerfilTipoEquipe  "
							+ "WHERE programacaoRoteiro.dataRoteiro = :dataRoteiro " + "AND (osProgramacao.indicadorAtivo = 1 "
							+ "OR (osProgramacao.indicadorAtivo = 2 AND osProgramacao.situacaoFechamento = 2)) ";

			retornoConsulta = session.createQuery(consulta).setDate("dataRoteiro", dataRoteiro).list();

			/*
			 * if (retornoConsulta != null && !retornoConsulta.isEmpty()) {
			 * Iterator itera = retornoConsulta.iterator();
			 * while (itera.hasNext()) {
			 * OrdemServicoProgramacao ordemServicoProgramacao = (OrdemServicoProgramacao) itera
			 * .next();
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getOrdemServico());
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getProgramacaoRoteiro());
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getOrdemServico().getServicoTipo());
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getOrdemServico().getServicoTipo()
			 * .getServicoPerfilTipo());
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getOrdemServico().getRegistroAtendimento());
			 * Hibernate.initialize(ordemServicoProgramacao.getEquipe());
			 * Hibernate.initialize(ordemServicoProgramacao.getEquipe()
			 * .getServicoPerfilTipo());
			 * }
			 * }
			 */
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author isilva
	 * @date 09/11/2010
	 * @param dataRoteiro
	 * @param equipe
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiro(Date dataRoteiro, Equipe equipe)
					throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN FETCH osProgramacao.programacaoRoteiro programacaoRoteiro  "
							+ "INNER JOIN FETCH osProgramacao.equipe equipe  " + "LEFT JOIN FETCH osProgramacao.ordemServico ordemServico "
							+ "LEFT JOIN FETCH ordemServico.servicoTipo servicoTipo "
							+ "LEFT JOIN FETCH servicoTipo.servicoPerfilTipo servicoPerfilTipo "
							+ "LEFT JOIN FETCH ordemServico.registroAtendimento registroAtendimento "
							+ "INNER JOIN FETCH equipe.servicoPerfilTipo servicoPerfilTipoEquipe  "
							+ "WHERE programacaoRoteiro.dataRoteiro = :dataRoteiro " + "AND equipe.id = :idEquipe "
							+ "AND (osProgramacao.indicadorAtivo = 1 "
							+ "OR (osProgramacao.indicadorAtivo = 2 AND osProgramacao.situacaoFechamento = 2)) ";

			retornoConsulta = session.createQuery(consulta).setDate("dataRoteiro", dataRoteiro).setInteger("idEquipe", equipe.getId())
							.list();

			/*
			 * if (retornoConsulta != null && !retornoConsulta.isEmpty()) {
			 * Iterator itera = retornoConsulta.iterator();
			 * while (itera.hasNext()) {
			 * OrdemServicoProgramacao ordemServicoProgramacao = (OrdemServicoProgramacao) itera
			 * .next();
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getOrdemServico());
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getProgramacaoRoteiro());
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getOrdemServico().getServicoTipo());
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getOrdemServico().getServicoTipo()
			 * .getServicoPerfilTipo());
			 * Hibernate.initialize(ordemServicoProgramacao
			 * .getOrdemServico().getRegistroAtendimento());
			 * Hibernate.initialize(ordemServicoProgramacao.getEquipe());
			 * Hibernate.initialize(ordemServicoProgramacao.getEquipe()
			 * .getServicoPerfilTipo());
			 * }
			 * }
			 */
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 11/09/2006
	 * @author Saulo Lima
	 * @date 11/02/2009
	 *       Mudança de INNER pra LEFT JOIN com a tabela RegistroAtendimento
	 * @param dataRoteiro
	 *            ,idUnidadeOrganizacional
	 * @return collection
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiroUnidade(Date dataRoteiro, Integer idUnidadeOrganizacional)
					throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retorno = new ArrayList();
		Collection<Object[]> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			Date dataRoteiroInicial = Util.formatarDataInicial(dataRoteiro);
			Date dataRoteiroFinal = Util.formatarDataFinal(dataRoteiro);

			consulta = "SELECT osProgramacao.id, " // 0
							+ "osProgramacao.nnSequencialProgramacao," // 1
							+ "osProgramacao.indicadorEquipePrincipal," // 2
							+ "osProgramacao.ultimaAlteracao," // 3
							+ "programacaoRoteiro.id," // 4
							+ "programacaoRoteiro.dataRoteiro," // 5
							+ "programacaoRoteiro.ultimaAlteracao," // 6
							+ "equipe.id," // 7
							+ "equipe.nome," // 8
							+ "equipe.placaVeiculo," // 9
							+ "equipe.cargaTrabalho," // 10
							+ "equipe.indicadorUso," // 11
							+ "equipe.ultimaAlteracao," // 12
							+ "unidade.id," // 13
							+ "unidade.descricao," // 14
							+ "servicoPerfilTipo.id," // 15
							+ "servicoPerfilTipo.descricao," // 16
							+ "ordemServico.id," // 17
							+ "servicoTipo.id," // 18
							+ "servicoTipo.descricao," // 19
							+ "servicoPerfilTipoOs.id," // 20
							+ "servicoPerfilTipoOs.descricao," // 21
							+ "ra.id," // 22
							+ "ra.ultimaAlteracao," // 23
							+ "ra.registroAtendimento, " // 24
							+ "ra.dataPrevistaAtual, " // 25
							+ "osProgramacao.indicadorAtivo " // 26
							// [OC0902365] Alteração Ordenação por localidade, setor comercial e
							// quadra,
							// mostrar
							// localidade e setor comercial na tela.
							+ ", imov.id, " // 27
							+ "imovLocal.id, " // 28
							+ "imovLocal.descricao, " // 29
							+ "imovSetor.id, " // 30
							+ "imovSetor.descricao, " // 31
							+ "imovQuadra.id, " // 32
							+ "osProgramacao.indicadorEnvioAcquaGis " // 33

							+ "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.ordemServico ordemServico  "
							+ "INNER JOIN osProgramacao.programacaoRoteiro programacaoRoteiro  "
							+ "INNER JOIN osProgramacao.equipe equipe  "
							+ "INNER JOIN equipe.unidadeOrganizacional unidade  "
							+ "INNER JOIN equipe.servicoPerfilTipo servicoPerfilTipo  "
							+ "LEFT JOIN ordemServico.registroAtendimento ra "
							+ "INNER JOIN ordemServico.servicoTipo servicoTipo  "
							+ "INNER JOIN servicoTipo.servicoPerfilTipo servicoPerfilTipoOs "
							// [OC0902365] Alteração Ordenação por localidade, setor comercial e
							// quadra,
							// mostrar
							// localidade e setor comercial na tela.
							+ "LEFT JOIN ordemServico.imovel imov " //
							+ "LEFT JOIN imov.localidade imovLocal " //
							+ "LEFT JOIN imov.quadra imovQuadra " //
							+ "LEFT JOIN imov.setorComercial imovSetor "

							+ "WHERE programacaoRoteiro.dataRoteiro BETWEEN :dataRoteiroInicial AND :dataRoteiroFinal "
							+ "AND unidade.id = :idUnidadeOrganizacional "
							+ "AND ordemServico.indicadorProgramada = :programada "
							+ "AND (osProgramacao.indicadorAtivo = :indicadorAtivo "
							+ "OR (osProgramacao.indicadorAtivo = :indicadorAtivoNao AND osProgramacao.situacaoFechamento = :situacaoFechamento )) "
							

							+ "ORDER BY equipe.id,osProgramacao.nnSequencialProgramacao";

			Query query = session.createQuery(consulta).setTimestamp("dataRoteiroInicial", dataRoteiroInicial)
							.setTimestamp("dataRoteiroFinal", dataRoteiroFinal)
							.setInteger("idUnidadeOrganizacional", idUnidadeOrganizacional).setShort("programada", OrdemServico.PROGRAMADA)
							.setShort("indicadorAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO)
							.setShort("indicadorAtivoNao", OrdemServicoProgramacao.INDICADOR_ATIVO_NAO)
							.setShort("situacaoFechamento", OrdemServicoProgramacao.SITUACAO_FECHAMENTO);
			System.out.println("\n\n\n\n");
			System.out.println(query.getQueryString());
			System.out.println("\n\n\n\n");
			retornoConsulta = query.list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){

				Iterator itera = retornoConsulta.iterator();
				while(itera.hasNext()){

					Object[] dadosConsulta = (Object[]) itera.next();

					// OrdemServicoProgramacao
					OrdemServicoProgramacao ordemServicoProgramacao = new OrdemServicoProgramacao();

					ordemServicoProgramacao.setId((Integer) dadosConsulta[0]);

					ordemServicoProgramacao.setNnSequencialProgramacao((Short) dadosConsulta[1]);
					ordemServicoProgramacao.setIndicadorEquipePrincipal((Short) dadosConsulta[2]);
					ordemServicoProgramacao.setIndicadorEnvioAcquaGis((Short) dadosConsulta[33]);
					ordemServicoProgramacao.setUltimaAlteracao((Date) dadosConsulta[3]);
					ordemServicoProgramacao.setIndicadorAtivo((Short) dadosConsulta[26]);

					// ProgramacaoRoteiro
					ProgramacaoRoteiro programacaoRoteiro = new ProgramacaoRoteiro();

					programacaoRoteiro.setId((Integer) dadosConsulta[4]);
					programacaoRoteiro.setDataRoteiro((Date) dadosConsulta[5]);
					programacaoRoteiro.setUltimaAlteracao((Date) dadosConsulta[6]);

					// Equipe
					Equipe equipe = new Equipe();

					equipe.setId((Integer) dadosConsulta[7]);
					equipe.setNome((String) dadosConsulta[8]);
					equipe.setPlacaVeiculo((String) dadosConsulta[9]);
					equipe.setCargaTrabalho((Integer) dadosConsulta[10]);
					equipe.setIndicadorUso((Short) dadosConsulta[11]);
					equipe.setUltimaAlteracao((Date) dadosConsulta[12]);

					// UnidadeOrganizacional
					UnidadeOrganizacional unidade = new UnidadeOrganizacional();

					unidade.setId((Integer) dadosConsulta[13]);
					unidade.setDescricao((String) dadosConsulta[14]);

					// ServicoPerfilTipo
					ServicoPerfilTipo servicoPerfilTipo = new ServicoPerfilTipo();

					servicoPerfilTipo.setId((Integer) dadosConsulta[15]);
					servicoPerfilTipo.setDescricao((String) dadosConsulta[16]);

					// OrdemServico
					OrdemServico os = new OrdemServico();
					os.setId((Integer) dadosConsulta[17]);

					// ServicoTipo
					ServicoTipo servicoTipo = new ServicoTipo();

					servicoTipo.setId((Integer) dadosConsulta[18]);
					servicoTipo.setDescricao((String) dadosConsulta[19]);

					// ServicoPerfilTipo da OS
					ServicoPerfilTipo servicoPerfilTipoOs = new ServicoPerfilTipo();

					servicoPerfilTipoOs.setId((Integer) dadosConsulta[20]);
					servicoPerfilTipoOs.setDescricao((String) dadosConsulta[21]);

					// RegistroAtendimento
					RegistroAtendimento ra = null;

					if(dadosConsulta[22] != null){

						ra = new RegistroAtendimento();

						ra.setId((Integer) dadosConsulta[22]);
						ra.setUltimaAlteracao((Date) dadosConsulta[23]);
						ra.setRegistroAtendimento((Date) dadosConsulta[24]);
						ra.setDataPrevistaAtual((Date) dadosConsulta[25]);
						os.setRegistroAtendimento(ra);
					}

					// [OC0902365] Alteração Ordenação por localidade, setor comercial e quadra,
					// mostrar
					// localidade e setor comercial na tela.
					Imovel imovel = null;
					Localidade localidade = null;
					SetorComercial setorComercial = null;

					if(dadosConsulta[28] != null){
						localidade = new Localidade();
						localidade.setId((Integer) dadosConsulta[28]);
						localidade.setDescricao(String.valueOf(dadosConsulta[29]));
					}

					if(dadosConsulta[30] != null){
						setorComercial = new SetorComercial();
						setorComercial.setId((Integer) dadosConsulta[30]);
						setorComercial.setDescricao(String.valueOf(dadosConsulta[31]));
					}

					if(dadosConsulta[27] != null){
						imovel = new Imovel();
						imovel.setId((Integer) dadosConsulta[27]);
						imovel.setLocalidade(localidade);
						imovel.setSetorComercial(setorComercial);
					}

					os.setImovel(imovel);

					equipe.setUnidadeOrganizacional(unidade);
					equipe.setServicoPerfilTipo(servicoPerfilTipo);
					servicoTipo.setServicoPerfilTipo(servicoPerfilTipoOs);
					os.setServicoTipo(servicoTipo);

					ordemServicoProgramacao.setProgramacaoRoteiro(programacaoRoteiro);
					ordemServicoProgramacao.setEquipe(equipe);
					ordemServicoProgramacao.setOrdemServico(os);

					retorno.add(ordemServicoProgramacao);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * obter OsAtividadeMaterialExecucao por OS
	 * 
	 * @author Leonardo Regis
	 * @date 14/09/2006
	 * @param idOS
	 * @param idAtividade
	 * @return Collection<OsAtividadeMaterialExecucao>
	 * @throws ErroRepositorioException
	 */
	public Collection<OsAtividadeMaterialExecucao> obterOsAtividadeMaterialExecucaoPorOS(Integer idOS, Integer idAtividade)
					throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;
		Collection<OsAtividadeMaterialExecucao> colecaoOsAtividadeMaterialExecucao = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT osame.id, " // 0
							+ " osame.quantidadeMaterial, " // 1
							+ " a.id, " // 2
							+ " a.descricao, " // 3
							+ " m.id, " // 4
							+ " m.descricao, " // 5
							+ " mu.id, " // 6
							+ " mu.descricao, " // 7
							+ " osame.valorMaterial " // 8
							+ "FROM OsAtividadeMaterialExecucao osame "
							+ "INNER JOIN osame.material m  "
							+ "INNER JOIN m.materialUnidade mu  "
							+ "INNER JOIN osame.ordemServicoAtividade osa  "
							+ "INNER JOIN osa.atividade a  " + "INNER JOIN osa.ordemServico os  "
							+ "WHERE os.id = :idOS "
							+ " and a.id = :idAtividade " + "ORDER BY a.descricao ";

			retornoConsulta = (Collection<Object[]>) session.createQuery(consulta).setInteger("idOS", idOS)
							.setInteger("idAtividade", idAtividade).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){
				OsAtividadeMaterialExecucao osAtividadeMaterialExecucao = null;
				for(Object[] execucao : retornoConsulta){
					osAtividadeMaterialExecucao = new OsAtividadeMaterialExecucao();
					osAtividadeMaterialExecucao.setId((Integer) execucao[0]);
					osAtividadeMaterialExecucao.setQuantidadeMaterial((BigDecimal) execucao[1]);
					OrdemServicoAtividade ordemServicoAtividade = new OrdemServicoAtividade();
					Atividade atividade = new Atividade();
					atividade.setId((Integer) execucao[2]);
					atividade.setDescricao((String) execucao[3]);
					ordemServicoAtividade.setAtividade(atividade);
					OrdemServico ordemServico = new OrdemServico();
					ordemServico.setId(idOS);
					ordemServicoAtividade.setOrdemServico(ordemServico);
					osAtividadeMaterialExecucao.setOrdemServicoAtividade(ordemServicoAtividade);
					Material material = new Material();
					material.setId((Integer) execucao[4]);
					material.setDescricao((String) execucao[5]);
					osAtividadeMaterialExecucao.setMaterial(material);
					osAtividadeMaterialExecucao.setValorMaterial((BigDecimal) execucao[8]);
					MaterialUnidade materialUnidade = new MaterialUnidade();
					materialUnidade.setId((Integer) execucao[6]);
					materialUnidade.setDescricao((String) execucao[7]);
					material.setMaterialUnidade(materialUnidade);
					colecaoOsAtividadeMaterialExecucao.add(osAtividadeMaterialExecucao);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return colecaoOsAtividadeMaterialExecucao;
	}

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * obter OsAtividadePeriodoExecucao por OS
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idOS
	 * @param idAtividade
	 * @return OsExecucaoEquipe
	 * @throws ErroRepositorioException
	 */
	public Collection<OsExecucaoEquipe> obterOsExecucaoEquipePorOS(Integer idOS, Integer idAtividade) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;
		Collection<OsExecucaoEquipe> colecaoOsExecucaoEquipe = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT osape.id, " // 0
							+ " osape.dataInicio, " // 1
							+ " osape.dataFim, " // 2
							+ " a.id, " // 3
							+ " a.descricao, " // 4
							+ " e.id, " // 5
							+ " e.nome, " // 6
							+ " osape.valorAtividadePeriodo " // 7
							+ "FROM OsExecucaoEquipe osee "
							+ "INNER JOIN osee.equipe e "
							+ "INNER JOIN osee.osAtividadePeriodoExecucao osape  "
							+ "INNER JOIN osape.ordemServicoAtividade osa  "
							+ "INNER JOIN osa.atividade a  " + "INNER JOIN osa.ordemServico os  "
							+ "WHERE os.id = :idOS "
							+ " and a.id = :idAtividade " + "ORDER BY a.descricao ";

			retornoConsulta = (Collection<Object[]>) session.createQuery(consulta).setInteger("idOS", idOS)
							.setInteger("idAtividade", idAtividade).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){
				OsExecucaoEquipe osExecucaoEquipe = null;
				for(Object[] periodo : retornoConsulta){
					osExecucaoEquipe = new OsExecucaoEquipe();
					Equipe equipe = new Equipe();
					equipe.setId((Integer) periodo[5]);
					equipe.setNome((String) periodo[6]);
					osExecucaoEquipe.setEquipe(equipe);
					OsAtividadePeriodoExecucao osAtividadePeriodoExecucao = new OsAtividadePeriodoExecucao();
					osAtividadePeriodoExecucao.setId((Integer) periodo[0]);
					osAtividadePeriodoExecucao.setDataInicio((Date) periodo[1]);
					osAtividadePeriodoExecucao.setDataFim((Date) periodo[2]);
					osAtividadePeriodoExecucao.setValorAtividadePeriodo((BigDecimal) periodo[7]);
					OrdemServicoAtividade ordemServicoAtividade = new OrdemServicoAtividade();
					Atividade atividade = new Atividade();
					atividade.setId((Integer) periodo[3]);
					atividade.setDescricao((String) periodo[4]);
					ordemServicoAtividade.setAtividade(atividade);
					OrdemServico ordemServico = new OrdemServico();
					ordemServico.setId(idOS);
					ordemServicoAtividade.setOrdemServico(ordemServico);
					osAtividadePeriodoExecucao.setOrdemServicoAtividade(ordemServicoAtividade);
					osExecucaoEquipe.setOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao);
					colecaoOsExecucaoEquipe.add(osExecucaoEquipe);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return colecaoOsExecucaoEquipe;
	}

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * obter programadações ativas por os e situacao
	 * 
	 * @author Leonardo Regis
	 * @date 14/09/2006
	 * @param idOS
	 * @return Collection<OrdemServicoProgramacao>
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> obterProgramacoesAtivasPorOs(Integer idOS) throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT osp FROM OrdemServicoProgramacao osp " // +
			// "INNER JOIN osp.ordemServico os "
							+ "WHERE osp.ordemServico.id = :idOS "
							+ " and (osp.indicadorAtivo = 1 or (osp.indicadorAtivo = 2 and osp.situacaoFechamento = 2))";

			retornoConsulta = (Collection<OrdemServicoProgramacao>) session.createQuery(consulta).setInteger("idOS", idOS).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * obter o somatório de horas para a OS informada e para o dia do roteiro
	 * solicitado
	 * 
	 * @author Leonardo Regis
	 * @date 15/09/2006
	 * @param idEquipe
	 * @param dataRoteiro
	 * @return OsExecucaoEquipe
	 * @throws ErroRepositorioException
	 */
	public Collection<OsExecucaoEquipe> obterOsExecucaoEquipePorEquipe(Integer idEquipe, Date dataRoteiro) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;
		Collection<OsExecucaoEquipe> colecaoOsExecucaoEquipe = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT osape.id, " // 0
							+ " osape.dataInicio, " // 1
							+ " osape.dataFim, " // 2
							+ " a.id, " // 3
							+ " a.descricao, " // 4
							+ " e.id, " // 5
							+ " e.nome " // 6
							+ "FROM OsExecucaoEquipe osee "
							+ "INNER JOIN osee.equipe e "
							+ "INNER JOIN osee.osAtividadePeriodoExecucao osape  "
							+ "INNER JOIN osape.ordemServicoAtividade osa  "
							+ "INNER JOIN osa.atividade a  " + "INNER JOIN osa.ordemServico os  "
							+ "WHERE e.id = :idEquipe "
							+ " and osape.dataFim = :dataRoteiro " + "ORDER BY a.descricao ";

			retornoConsulta = (Collection<Object[]>) session.createQuery(consulta).setInteger("idEquipe", idEquipe)
							.setDate("dataRoteiro", dataRoteiro).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){
				OsExecucaoEquipe osExecucaoEquipe = null;
				for(Object[] periodo : retornoConsulta){
					osExecucaoEquipe = new OsExecucaoEquipe();
					Equipe equipe = new Equipe();
					equipe.setId((Integer) periodo[5]);
					equipe.setNome((String) periodo[6]);
					osExecucaoEquipe.setEquipe(equipe);
					OsAtividadePeriodoExecucao osAtividadePeriodoExecucao = new OsAtividadePeriodoExecucao();
					osAtividadePeriodoExecucao.setId((Integer) periodo[0]);
					osAtividadePeriodoExecucao.setDataInicio((Date) periodo[1]);
					osAtividadePeriodoExecucao.setDataFim((Date) periodo[2]);
					OrdemServicoAtividade ordemServicoAtividade = new OrdemServicoAtividade();
					Atividade atividade = new Atividade();
					atividade.setId((Integer) periodo[3]);
					atividade.setDescricao((String) periodo[4]);
					ordemServicoAtividade.setAtividade(atividade);
					osAtividadePeriodoExecucao.setOrdemServicoAtividade(ordemServicoAtividade);
					osExecucaoEquipe.setOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao);
					colecaoOsExecucaoEquipe.add(osExecucaoEquipe);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return colecaoOsExecucaoEquipe;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 */
	public Integer pesquisarRAOrdemServico(Integer numeroOS) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT ra.id " + "FROM OrdemServico os " + "LEFT JOIN os.registroAtendimento ra  " + "WHERE os.id = :numeroOS ";

			retorno = (Integer) session.createQuery(consulta).setInteger("numeroOS", numeroOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * pesquisa se a OS tenha uma prgramação ativa com a data menor ou igual a
	 * data corrente e não tenha recebido a data de encerramento
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0006] - Verificar Origem do Encerramento da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public Integer pesquisarOSProgramacaoAtiva(Integer numeroOS) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT os.id " + "FROM OrdemServicoProgramacao osProgramacao " + "LEFT JOIN osProgramacao.ordemServico os  "
							+ "LEFT JOIN osProgramacao.programacaoRoteiro progRoteiro " + "WHERE os.id = :numeroOS AND "
							+ "osProgramacao.indicadorAtivo = :indAtivo AND " + "progRoteiro.dataRoteiro <= :dataAtual";

			retorno = (Integer) session.createQuery(consulta).setInteger("numeroOS", numeroOS)
							.setShort("indAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO).setDate("dataAtual", new Date())
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * pesquisa se a OS tenha uma prgramação ativa com a data menor ou igual a
	 * data corrente e não tenha recebido a data de encerramento
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0001] - Encerrar sem execução
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void atualizarParmsOS(Integer numeroOS, Integer idMotivoEncerramento, Date dataEncerramento, String parecerEncerramento,
					String codigoRetornoVistoriaOs, Date dataExecucao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			String atualizarOS = "";

			System.out.println("idMotivoEncerramento = " + idMotivoEncerramento);

			atualizarOS = "update gcom.atendimentopublico.ordemservico.OrdemServico " + "set orse_tmultimaalteracao = :ultimaAlteracao, "
							+ "amen_id = :idMotivoEncerramento, " + "orse_cdsituacao = :encerrado, "
							+ "orse_tmencerramento = :dataEncerramento,  orse_tmexecucao = :dataExecucao ";
			if(codigoRetornoVistoriaOs != null && !codigoRetornoVistoriaOs.equals("")){
				atualizarOS = atualizarOS + ",orse_cdretornovistoria = " + new Short(codigoRetornoVistoriaOs);
			}else{
				atualizarOS = atualizarOS + ",orse_cdretornovistoria = null";
			}
			if(parecerEncerramento != null && !parecerEncerramento.equals("")){
				atualizarOS = atualizarOS + " ,orse_dsparecerencerramento = '" + parecerEncerramento + "'";
			}else{
				atualizarOS = atualizarOS + " ,orse_dsparecerencerramento = null ";
			}
			atualizarOS = atualizarOS + " where orse_id = :idOrdemServico";

			session.createQuery(atualizarOS).setInteger("idOrdemServico", numeroOS).setTimestamp("ultimaAlteracao", new Date())
							.setInteger("idMotivoEncerramento", idMotivoEncerramento)
							.setShort("encerrado", OrdemServico.SITUACAO_ENCERRADO).setTimestamp("dataEncerramento", dataEncerramento)
							.setTimestamp("dataExecucao", dataExecucao)
							.executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * [SB0001] - Encerrar sem execução
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 */
	public Integer pesquisarOSReferencia(Integer numeroOS) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osRef.id " + "FROM OrdemServico os " + "LEFT JOIN os.osReferencia osRef  " + "WHERE os.id = :numeroOS ";

			retorno = (Integer) session.createQuery(consulta).setInteger("numeroOS", numeroOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0001] - Encerrar sem execução
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void atualizarParmsOSReferencia(Integer numeroOSReferencia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			String atualizarOS = "";

			atualizarOS = "update gcom.atendimentopublico.ordemservico.OrdemServico " + "set orse_tmultimaalteracao = :ultimaAlteracao, "
							+ "orse_cdsituacao = :pendente " + "where orse_id = :idOrdemServico";

			session.createQuery(atualizarOS).setInteger("idOrdemServico", numeroOSReferencia).setTimestamp("ultimaAlteracao", new Date())
							.setShort("pendente", OrdemServico.SITUACAO_PENDENTE).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public Collection pesquisarOSProgramacaoRoteiro(Integer numeroOS, Date dataEncerramento) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao.id " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "LEFT JOIN osProgramacao.ordemServico os  " + "LEFT JOIN osProgramacao.programacaoRoteiro progRoteiro "
							+ "WHERE os.id = :numeroOS AND " + "progRoteiro.dataRoteiro > :dataEncerramento";

			retorno = session.createQuery(consulta).setInteger("numeroOS", numeroOS).setTimestamp("dataEncerramento", dataEncerramento)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public Collection pesquisarRoteiro(Integer numeroOS, Date dataExecucao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao.id,progRoteiro.id " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "LEFT JOIN osProgramacao.ordemServico os  " + "LEFT JOIN osProgramacao.programacaoRoteiro progRoteiro "
							+ "WHERE os.id = :numeroOS AND " + "progRoteiro.dataRoteiro > :dataExecucao";

			retorno = session.createQuery(consulta).setInteger("numeroOS", numeroOS).setTimestamp("dataExecucao", dataExecucao)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public Integer verificarExistenciaProgramacaoRoteiroParaOSProgramacao(Integer idOSProgramacao, Integer idRoteiro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT progRoteiro.id " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "LEFT JOIN osProgramacao.programacaoRoteiro progRoteiro " + "WHERE osProgramacao.id <> :idOSProgramacao AND "
							+ "progRoteiro.id = :idRoteiro";

			retorno = (Integer) session.createQuery(consulta).setInteger("idOSProgramacao", idOSProgramacao)
							.setInteger("idRoteiro", idRoteiro).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public OrdemServicoProgramacao pesquisarOSProgramacaoAtivaComDataEncerramento(Integer numeroOS, Date dataExecucao)
					throws ErroRepositorioException{

		OrdemServicoProgramacao retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "LEFT JOIN osProgramacao.ordemServico os  " + "LEFT JOIN osProgramacao.programacaoRoteiro progRoteiro "
							+ "WHERE os.id = :numeroOS AND " + "osProgramacao.indicadorAtivo = :indAtivo AND "
							+ "progRoteiro.dataRoteiro = :dataProgramacaoRoteiro";

			retorno = (OrdemServicoProgramacao) session.createQuery(consulta).setInteger("numeroOS", numeroOS)
							.setShort("indAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO)
							.setTimestamp("dataProgramacaoRoteiro", dataExecucao).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection<Atividade>
	 * @throws ErroRepositorioException
	 */
	public Collection<Atividade> obterAtividadesOrdemServico(Integer numeroOS) throws ErroRepositorioException{

		Collection<Atividade> retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT ativ " + "FROM ServicoTipoAtividade stat, Atividade ativ, OrdemServico orse "
							+ "INNER JOIN orse.servicoTipo svtp " + "WHERE stat.servicoTipo.id = svtp.id AND stat.atividade.id = ativ.id "
							+ "AND orse.id = :numeroOS " + "AND ativ.indicadorUso = 1 AND svtp.indicadorUso = 1 "
							+ "ORDER BY ativ.descricao ";

			retorno = (Collection<Atividade>) session.createQuery(consulta).setInteger("numeroOS", numeroOS).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterEquipesProgramadas(Integer numeroOS) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT eqpe.id, eqpe.nome, eqpe.ultimaAlteracao " + "FROM OrdemServicoProgramacao ospg "
							+ "INNER JOIN ospg.equipe eqpe " + "INNER JOIN ospg.ordemServico orse "
							+ "WHERE eqpe.indicadorUso = 1 AND orse.id = :numeroOS " + "ORDER BY eqpe.nome ";

			retorno = (Collection) session.createQuery(consulta).setInteger("numeroOS", numeroOS).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterMateriaisProgramados(Integer numeroOS) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT mate.id, mate.descricao " + "FROM OrdemServico orse, ServicoTipoMaterial stmt "
							+ "INNER JOIN stmt.material mate " + "INNER JOIN stmt.servicoTipo svtp "
							+ "WHERE orse.servicoTipo.id = svtp.id AND orse.id = :numeroOS "
							+ "AND mate.indicadorUso = 1 AND svtp.indicadorUso = 1 " + "ORDER BY mate.descricao ";

			retorno = (Collection<Atividade>) session.createQuery(consulta).setInteger("numeroOS", numeroOS).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 *            ,
	 *            idMaterial
	 * @return BigDecimal
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterQuantidadePadraoMaterial(Integer numeroOS, Integer idMaterial) throws ErroRepositorioException{

		BigDecimal retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT stmt.quantidadePadrao " + "FROM OrdemServico orse, ServicoTipoMaterial stmt "
							+ "INNER JOIN stmt.material mate " + "INNER JOIN stmt.servicoTipo svtp "
							+ "WHERE orse.servicoTipo.id = svtp.id AND orse.id = :numeroOS "
							+ "AND mate.id = :idMaterial AND mate.indicadorUso = 1 AND svtp.indicadorUso = 1 ";

			retorno = (BigDecimal) session.createQuery(consulta).setInteger("numeroOS", numeroOS).setInteger("idMaterial", idMaterial)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarOSAssociadaDocumentoCobranca(Integer numeroOS) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT COUNT(*) " + "FROM OrdemServico orse " + "INNER JOIN orse.cobrancaDocumento cbdo "
							+ "WHERE orse.id = :numeroOS ";

			retorno = ((Number) session.createQuery(consulta).setInteger("numeroOS", numeroOS).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarOSAssociadaRA(Integer numeroOS) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT COUNT(*) " + "FROM OrdemServico orse " + "INNER JOIN orse.registroAtendimento rgat "
							+ "WHERE orse.id = :numeroOS ";

			retorno = ((Number) session.createQuery(consulta).setInteger("numeroOS", numeroOS).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @author Saulo Lima
	 * @date 04/06/2009
	 *       Correção + alteração para retornar a UnidadeOrganizacional
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeDestinoUltimoTramite(Integer numeroOS) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT tram.unidadeOrganizacionalDestino " + "FROM Tramite tram " + "WHERE tram.id = " + "(SELECT MAX(tramite.id) "
							+ "FROM Tramite tramite, OrdemServico orse " + "INNER JOIN orse.registroAtendimento rgat "
							+ "WHERE orse.id = :numeroOS " + "AND tramite.registroAtendimento = rgat.id)";

			retorno = (UnidadeOrganizacional) session.createQuery(consulta).setInteger("numeroOS", numeroOS).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterEquipesPorUnidade(Integer idUnidade) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT eqpe.id, eqpe.nome " + "FROM Equipe eqpe " + "INNER JOIN eqpe.unidadeOrganizacional unid "
							+ "WHERE eqpe.indicadorUso = 1 AND unid.id = :idUnidade " + "ORDER BY eqpe.nome ";

			retorno = (Collection<Atividade>) session.createQuery(consulta).setInteger("idUnidade", idUnidade).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0362] Efetuar Instalação de Hidrômetro
	 * 
	 * @author Leonardo Regis
	 * @date 19/09/2006
	 * @param dadosOS
	 * @throws ErroRepositorioException
	 */
	public void atualizarOSParaEfetivacaoInstalacaoHidrometro(DadosAtualizacaoOSParaInstalacaoHidrometroHelper dadosOS)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String atualizarOS;
		Query query;
		try{

			atualizarOS = " UPDATE OrdemServico " + " SET ultimaAlteracao = :dataUltimaAlteracao, "
							+ " indicadorComercialAtualizado = :indicadorComercialAtualizado, ";

			if(dadosOS.getServicoNaoCobrancaMotivo().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				atualizarOS += " servicoNaoCobrancaMotivo.id = :servicoNaoCobrancaMotivo, ";
			}
			atualizarOS += " percentualCobranca = :percentualCobranca, " + " valorAtual = :valorAtual " + " WHERE id = :osId";

			query = session.createQuery(atualizarOS).setBigDecimal("valorAtual", dadosOS.getValorDebito())
							.setShort("indicadorComercialAtualizado", dadosOS.getIndicadorComercial())
							.setBigDecimal("percentualCobranca", dadosOS.getPercentualCobranca())
							.setTimestamp("dataUltimaAlteracao", dadosOS.getUltimaAlteracao()).setInteger("osId", dadosOS.getIdOs());
			if(dadosOS.getServicoNaoCobrancaMotivo().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				query.setInteger("servicoNaoCobrancaMotivo", dadosOS.getServicoNaoCobrancaMotivo().getId());
			}
			query.executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * [FS0008] - Verificar possibilidade da inclusão da ordem de serviço
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public OrdemServicoProgramacao pesquisarOSProgramacaoAtivaPorOS(Integer idOS) throws ErroRepositorioException{

		OrdemServicoProgramacao retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.ordemServico os  " + "INNER JOIN FETCH osProgramacao.equipe equipe  "
							+ "INNER JOIN FETCH osProgramacao.programacaoRoteiro " + "WHERE os.id = :numeroOS "
							+ "AND osProgramacao.indicadorAtivo = :indAtivo";

			retorno = (OrdemServicoProgramacao) session.createQuery(consulta).setInteger("numeroOS", idOS)
							.setShort("indAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO).setMaxResults(1).uniqueResult();
			/*
			 * if (retorno != null) {
			 * Hibernate.initialize(retorno.getEquipe());
			 * Hibernate.initialize(retorno.getProgramacaoRoteiro());
			 * }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public OrdemServicoProgramacao pesquisarOSProgramacaoPorId(Integer idOrdemServicoProgramacao) throws ErroRepositorioException{

		OrdemServicoProgramacao retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "WHERE osProgramacao.id = :idOrdemServicoProgramacao ";

			retorno = (OrdemServicoProgramacao) session.createQuery(consulta)
							.setInteger("idOrdemServicoProgramacao", idOrdemServicoProgramacao).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public OrdemServicoProgramacao pesquisarOSProgramacaoAtivaComDataRoteiroIdEquipe(Integer numeroOS, Date dataRoteiro, Integer idEquipe)
					throws ErroRepositorioException{

		OrdemServicoProgramacao retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.ordemServico os  " + "INNER JOIN osProgramacao.equipe equipe  "
							+ "INNER JOIN osProgramacao.programacaoRoteiro progRoteiro " + "WHERE os.id = :numeroOS  "
							+ "AND equipe.id = :idEquipe " + "AND osProgramacao.indicadorAtivo = :indAtivo "
							+ "AND progRoteiro.dataRoteiro = :dataProgramacaoRoteiro";

			retorno = (OrdemServicoProgramacao) session.createQuery(consulta).setInteger("numeroOS", numeroOS)
							.setInteger("idEquipe", idEquipe).setShort("indAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO)
							.setTimestamp("dataProgramacaoRoteiro", dataRoteiro)

							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> pesquisarOSProgramacaoComDataRoteiroIdEquipeDiferenteOS(Integer numeroOS, Date dataRoteiro,
					Integer idEquipe) throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.ordemServico os  " + "INNER JOIN osProgramacao.equipe equipe  "
							+ "INNER JOIN osProgramacao.programacaoRoteiro progRoteiro "
							+ "LEFT JOIN osProgramacao.osProgramNaoEncerMotivo osProgramNaoEncerMotivo " + "WHERE os.id <> :numeroOS  "
							+ "AND equipe.id = :idEquipe " + "AND (osProgramacao.indicadorAtivo = 1 OR (osProgramacao.indicadorAtivo = 2 "
							+ " AND osProgramNaoEncerMotivo IS NULL)) " + "AND progRoteiro.dataRoteiro = :dataProgramacaoRoteiro";

			retorno = (Collection<OrdemServicoProgramacao>) session.createQuery(consulta).setInteger("numeroOS", numeroOS)
							.setInteger("idEquipe", idEquipe).setTimestamp("dataProgramacaoRoteiro", dataRoteiro).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> pesquisarOSProgramacaoComSequencialMaior(Integer numeroOS, Date dataRoteiro,
					Integer idEquipe, short sequencialReferencia) throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.ordemServico os  " + "INNER JOIN osProgramacao.equipe equipe  "
							+ "INNER JOIN osProgramacao.programacaoRoteiro progRoteiro " + "WHERE equipe.id = :idEquipe "
							+ "AND os.id = :numeroOS " + "AND (osProgramacao.indicadorAtivo = 1 "
							+ "OR (osProgramacao.indicadorAtivo = 2 AND osProgramacao.situacaoFechamento = 2)) "
							+ "AND osProgramacao.nnSequencialProgramacao > :sequencialReferencia "
							+ "AND progRoteiro.dataRoteiro = :dataProgramacaoRoteiro";

			retorno = (Collection<OrdemServicoProgramacao>) session.createQuery(consulta).setInteger("numeroOS", numeroOS)
							.setInteger("idEquipe", idEquipe).setShort("sequencialReferencia", sequencialReferencia)
							.setTimestamp("dataProgramacaoRoteiro", dataRoteiro).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> pesquisarOSProgramacaoComDataRoteiroIdEquipe(Date dataRoteiro, Integer idEquipe,
					boolean ordernadoPorSequencial) throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao "
							+ "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.equipe equipe  "
							+ "INNER JOIN osProgramacao.programacaoRoteiro progRoteiro "
							+ "WHERE equipe.id = :idEquipe "
							+ "AND progRoteiro.dataRoteiro = :dataProgramacaoRoteiro "
							+ "AND (osProgramacao.indicadorAtivo = :indicadorAtivo "
							+ "OR (osProgramacao.indicadorAtivo = :indicadorAtivoNao AND osProgramacao.situacaoFechamento = :situacaoFechamento )) ";

			if(ordernadoPorSequencial){
				consulta += "order by osProgramacao.nnSequencialProgramacao asc";
			}

			retorno = (Collection<OrdemServicoProgramacao>) session.createQuery(consulta).setInteger("idEquipe", idEquipe)
							.setTimestamp("dataProgramacaoRoteiro", dataRoteiro)
							.setShort("indicadorAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO)
							.setShort("indicadorAtivoNao", OrdemServicoProgramacao.INDICADOR_ATIVO_NAO)
							.setShort("situacaoFechamento", OrdemServicoProgramacao.SITUACAO_FECHAMENTO).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> pesquisarOSProgramacaoComDataRoteiroIdEquipeOrdenada(Date dataRoteiro, Integer idEquipe)
					throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT osProgramacao "
							+ "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.equipe equipe  "
							+ "INNER JOIN osProgramacao.programacaoRoteiro progRoteiro "
							+ "WHERE equipe.id = :idEquipe "
							+ "AND progRoteiro.dataRoteiro = :dataProgramacaoRoteiro "
							+ "AND (osProgramacao.indicadorAtivo = :indicadorAtivo "
							+ "OR (osProgramacao.indicadorAtivo = :indicadorAtivoNao AND osProgramacao.situacaoFechamento = :situacaoFechamento )) "
							+ "ORDER BY osProgramacao.nnSequencialProgramacao ";

			retorno = (Collection<OrdemServicoProgramacao>) session.createQuery(consulta).setInteger("idEquipe", idEquipe)
							.setTimestamp("dataProgramacaoRoteiro", dataRoteiro)
							.setShort("indicadorAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO)
							.setShort("indicadorAtivoNao", OrdemServicoProgramacao.INDICADOR_ATIVO_NAO)
							.setShort("situacaoFechamento", OrdemServicoProgramacao.SITUACAO_FECHAMENTO).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 25/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Object[] recuperarParametrosServicoTipo(Integer numeroOS) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT servTipo.indicadorAtualizaComercial,debTipo.id,imov.id,servTipo.id, "
							+ "servTipo.indicadorIncluirDebito, servTipo.valor " + "FROM OrdemServico orse "
							+ "INNER JOIN orse.servicoTipo servTipo " + "LEFT JOIN  servTipo.debitoTipo debTipo "
							+ "LEFT JOIN orse.imovel imov " + "WHERE orse.id = :numeroOS ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("numeroOS", numeroOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 25/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public Collection<Equipe> recuperaEquipeDaOSProgramacaoPorDataRoteiro(Integer idOs, Date dataRoteiro) throws ErroRepositorioException{

		Collection<Equipe> retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT equipe " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.programacaoRoteiro programacaoRoteiro  "
							+ "INNER JOIN osProgramacao.equipe equipe  " + "INNER JOIN osProgramacao.ordemServico os  "
							+ "WHERE os.id = :idOs " + "AND programacaoRoteiro.dataRoteiro = :dataRoteiro "
							+ "AND osProgramacao.indicadorAtivo = 1 ";

			retornoConsulta = (Collection<Equipe>) session.createQuery(consulta).setDate("dataRoteiro", dataRoteiro)
							.setInteger("idOs", idOs).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * Atualização Geral de OS
	 * 
	 * @author Leonardo Regis
	 * @date 25/09/2006
	 * @param os
	 * @param updateCorte
	 * @param updateSupressao
	 * @throws ErroRepositorioException
	 */
	public void atualizaOSGeral(OrdemServico os, boolean updateCorte, boolean updateSupressao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;
		Query query;
		try{
			update = "update OrdemServico set " + "valorAtual = :valorAtual, " + "indicadorComercialAtualizado = :indicador, ";
			if(os.getServicoNaoCobrancaMotivo() != null
							&& os.getServicoNaoCobrancaMotivo().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				update += "servicoNaoCobrancaMotivo.id = :motivoNaoCobranca, ";
			}else{
				update += "percentualCobranca = :percentualCobranca, ";
			}

			// Atualiza o tipo de Corte
			if(updateCorte){
				if(os.getCorteTipo() != null && os.getCorteTipo().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					update += "corteTipo.id = :corteTipoId, ";
				}
			}

			// Atualiza o tipo da Supressao
			if(updateSupressao){
				if(os.getSupressaoTipo() != null && os.getSupressaoTipo().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					update += "supressaoTipo.id = :supressaoTipoId, ";
				}
			}

			update += "ultimaAlteracao = :dataCorrente " + "where id = :osId";
			query = session.createQuery(update).setTimestamp("dataCorrente", os.getUltimaAlteracao())
							.setBigDecimal("valorAtual", os.getValorAtual()).setShort("indicador", os.getIndicadorComercialAtualizado());
			if(os.getServicoNaoCobrancaMotivo() != null
							&& os.getServicoNaoCobrancaMotivo().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				query.setInteger("motivoNaoCobranca", os.getServicoNaoCobrancaMotivo().getId());
			}else{
				query.setBigDecimal("percentualCobranca", os.getPercentualCobranca());
			}

			if(updateCorte){
				if(os.getCorteTipo() != null && os.getCorteTipo().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					query.setInteger("corteTipoId", os.getCorteTipo().getId());
				}
			}

			// Atualiza o tipo da Supressao
			if(updateSupressao){
				if(os.getSupressaoTipo() != null && os.getSupressaoTipo().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					query.setInteger("supressaoTipoId", os.getSupressaoTipo().getId());
				}
			}

			query.setInteger("osId", os.getId()).executeUpdate();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 25/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRRepositorioException
	 */
	public Collection pesquisarSolicitacoesServicoTipoOS(Integer numeroOS) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT solTipEspecificacao.especificacaoServicoTipos " + "FROM OrdemServico orse "
							+ "LEFT JOIN orse.registroAtendimento regAtendimento "
							+ "LEFT JOIN regAtendimento.solicitacaoTipoEspecificacao solTipEspecificacao " + "WHERE orse.id = :numeroOS";

			retorno = session.createQuery(consulta).setInteger("numeroOS", numeroOS).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 25/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRRepositorioException
	 */
	public Collection pesquisarServicoTipo(Collection idsServicoTipo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = "SELECT servTipo " + "FROM ServicoTipo servTipo " + "LEFT JOIN servTipo.servicoTipoReferencia servTipoRef "
							+ "WHERE servTipoRef.id is null AND servTipo.id in (:idsServicoTipo) ";

			retorno = session.createQuery(consulta).setParameterList("idsServicoTipo", idsServicoTipo).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 25/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public boolean verificaExitenciaProgramacaoAtivaParaDiasAnteriores(Integer idOs, Date dataRoteiro) throws ErroRepositorioException{

		boolean existeProgramacao = false;
		Collection retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.programacaoRoteiro programacaoRoteiro  "
							+ "INNER JOIN osProgramacao.ordemServico os  " + "WHERE os.id = :idOs "
							+ "AND programacaoRoteiro.dataRoteiro < :dataRoteiro " + "AND osProgramacao.indicadorAtivo = 1 ";

			retornoConsulta = session.createQuery(consulta).setDate("dataRoteiro", dataRoteiro).setInteger("idOs", idOs).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){
				existeProgramacao = true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return existeProgramacao;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 26/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOrdemServicoProgramacaoPorDataRoteiro(Integer idOs, Date dataRoteiro)
					throws ErroRepositorioException{

		Collection<OrdemServicoProgramacao> retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT osProgramacao " + "FROM OrdemServicoProgramacao osProgramacao "
							+ "INNER JOIN osProgramacao.programacaoRoteiro programacaoRoteiro  "
							+ "INNER JOIN osProgramacao.ordemServico os  " + "WHERE os.id = :idOs "
							+ "AND programacaoRoteiro.dataRoteiro = :dataRoteiro " + "AND osProgramacao.indicadorAtivo = 1 ";

			retornoConsulta = (Collection<OrdemServicoProgramacao>) session.createQuery(consulta).setDate("dataRoteiro", dataRoteiro)
							.setInteger("idOs", idOs).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 26/09/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdMotivoEncerramentoOSReferida(Integer idOsReferidaRetornoTipo) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT atenMotEncerramento.id " + "FROM OsReferidaRetornoTipo osRefeRetTipo "
							+ "LEFT JOIN osRefeRetTipo.atendimentoMotivoEncerramento atenMotEncerramento  "
							+ "WHERE osRefeRetTipo.id = :idOsReferidaRetornoTipo ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idOsReferidaRetornoTipo", idOsReferidaRetornoTipo)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 26/09/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Short pesquisarIndDiagnosticoServicoTipoReferencia(Integer idOrdemServicoReferencia) throws ErroRepositorioException{

		Short retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT servTipoRef.indicadorDiagnostico " + "FROM OrdemServico orse " + "LEFT JOIN orse.servicoTipo servTipo "
							+ "LEFT JOIN servTipo.servicoTipoReferencia servTipoRef " + "WHERE orse.id = :idOrdemServicoReferencia ";

			retorno = (Short) session.createQuery(consulta).setInteger("idOrdemServicoReferencia", idOrdemServicoReferencia)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection<UnidadeOrganizacional> pesquisarUnidadeOrganizacionalPorUnidade(Integer unidadeLotacao)
					throws ErroRepositorioException{

		Collection<UnidadeOrganizacional> retorno = new ArrayList();
		Collection retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT unid.id, unid.descricao, count(os) " + "FROM OrdemServicoUnidade osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  " + "INNER JOIN osUnidade.ordemServico os  "
							+ "INNER JOIN osUnidade.unidadeOrganizacional unid  " + "LEFT JOIN os.registroAtendimento ra  "
							+ " INNER JOIN os.imovel imov " + "INNER JOIN imov.localidade imovLocal "
							+ " INNER JOIN imov.quadra imovQuadra " + "INNER JOIN imov.setorComercial imovSetor " + "WHERE ra IS NULL "
							+ "AND os.situacao in (1,3) and os.indicadorProgramada = 2 " + "AND unid.id = :unidadeLotacao "
							+ "AND art.id = :idAtendimentoTipo " + "GROUP BY unid.id, unid.descricao";

			retornoConsulta = session.createQuery(consulta).setInteger("unidadeLotacao", unidadeLotacao)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

			if(!retornoConsulta.isEmpty()){

				Object[] retornoConsultaArray = retornoConsulta.toArray();
				Object[] itemLista;
				UnidadeOrganizacional unidadeOrganizacional = null;

				for(int i = 0; i < retornoConsultaArray.length; i++){
					itemLista = (Object[]) retornoConsultaArray[i];
					unidadeOrganizacional = new UnidadeOrganizacional();
					unidadeOrganizacional.setId((Integer) itemLista[0]);
					unidadeOrganizacional.setDescricao((String) itemLista[1]);
					unidadeOrganizacional.setQtidadeOs(((Number) itemLista[2]).intValue());
					retorno.add(unidadeOrganizacional);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public UnidadeOrganizacional pesquisarUnidadeOrganizacionalPorUnidade(Integer idOs, Integer unidadeLotacao)
					throws ErroRepositorioException{

		UnidadeOrganizacional retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT DISTINCT unid " + "FROM OrdemServicoUnidade osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  " + "INNER JOIN osUnidade.ordemServico os  "
							+ "INNER JOIN osUnidade.unidadeOrganizacional unid  " + "LEFT JOIN os.registroAtendimento ra  "
							+ "WHERE ra IS NULL " + "AND os.id = :idOs " + "AND unid.id = :unidadeLotacao "
							+ "AND art.id = :idAtendimentoTipo ";

			retornoConsulta = (UnidadeOrganizacional) session.createQuery(consulta).setInteger("unidadeLotacao", unidadeLotacao)
							.setInteger("idOs", idOs).setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 29/09/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Object[] verificarExistenciaOSEncerrado(Integer idRA, Integer idServicoTipo) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT orse.id,servTipo.descricao " + "FROM OrdemServico orse "
							+ "LEFT JOIN orse.registroAtendimento regAtendimento " + "LEFT JOIN orse.servicoTipo servTipo "
							+ "WHERE regAtendimento.id = :idRA AND " + "orse.situacao <> :encerrada AND " + "servTipo.id = :idServicoTipo";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRA", idRA).setInteger("idServicoTipo", idServicoTipo)
							.setShort("encerrada", OrdemServico.SITUACAO_ENCERRADO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 29/09/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaServicoTipoReferencia(Integer idServicoTipo) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT servTipoRef.id " + "FROM ServicoTipo servTipo " + "LEFT JOIN servTipo.servicoTipoReferencia servTipoRef "
							+ "WHERE servTipo.id = :idServicoTipo";

			retorno = (Integer) session.createQuery(consulta).setInteger("idServicoTipo", idServicoTipo).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 02/10/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaUnidadeTerceirizada(Integer idUsuario) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT usuario.id " + "FROM Usuario usuario " + "LEFT JOIN usuario.unidadeOrganizacional unidOrganizacional "
							+ "LEFT JOIN unidOrganizacional.unidadeTipo unidTipo "
							+ "WHERE usuario.id = :idUsuario AND unidTipo.codigoTipo = :codigoTipo";

			retorno = (Integer) session.createQuery(consulta).setString("codigoTipo", UnidadeTipo.UNIDADE_TIPO_TERCERIZADO)
							.setInteger("idUsuario", idUsuario).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 02/10/2006
	 * @param idOsReferidaRetornoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection verificarOSparaRA(Integer idOrdemServico, Integer idRA) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT orse.id " + "FROM OrdemServico orse " + "LEFT JOIN orse.registroAtendimento regAtendimento "
							+ "WHERE orse.id <> :idOrdemServico AND " + "regAtendimento = :idRA " + "AND orse.situacao = :situacao";

			retorno = session.createQuery(consulta).setInteger("idOrdemServico", idOrdemServico).setInteger("idRA", idRA)
							.setInteger("situacao", OrdemServico.SITUACAO_PENDENTE).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa os campos da OS que serão impressos no relatório de Ordem de
	 * Servico
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idOrdemServico
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOSRelatorio(Collection idsOrdemServico) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT os.dataGeracao, loc.id, sc.codigo, " // 0,1,2
							+ "quadra.numeroQuadra, imov.lote, imov.subLote, " // 3,4,5
							+ "imov.id, last.descricaoAbreviado, " // 6,7
							+ "lest.descricaoAbreviado, lesg.consumoMinimo, " // 8,9
							+ "prua.descricaoAbreviada, pcal.descricaoAbreviada, " // 10,11
							+ "ms.descricao, usuario.nomeUsuario, " // 12,13
							+ "func.id, ra.pontoReferencia, " // 14,15
							+ "svtp.id, svtp.descricao, " // 16,17
							+ "lcoc.descricao, ra.dataPrevistaAtual, " // 18,19
							+ "ra.observacao, os.observacao, ra.id, " // 20,21,22
							+ "soltpesp.descricao, os.id, svtp.tempoMedioExecucao, " // 23, 24, 25
							+ "unidOrg.descricao, imov.numeroSequencialRota, rotaImovel.codigo, " // 26,
							// 27,
							// 28
							+ "servTpRef.id, servTpRef.descricao,svtp.valor, " // 29, 30,31
							+ "rotaImovel.id, imov.numeroSegmento " // 32, 33
							+ "FROM OrdemServicoUnidade osu "
							+ "INNER JOIN osu.atendimentoRelacaoTipo atrt "
							+ "INNER JOIN osu.usuario usuario "
							+ "INNER JOIN osu.unidadeOrganizacional unidOrg "
							+ "LEFT JOIN usuario.funcionario func " + "INNER JOIN osu.ordemServico os "
							+ "INNER JOIN os.servicoTipo svtp "
							+ "LEFT JOIN os.registroAtendimento ra "
							+ "LEFT JOIN ra.meioSolicitacao ms "
							+ "LEFT JOIN ra.solicitacaoTipoEspecificacao soltpesp "
							+ "LEFT JOIN ra.localOcorrencia lcoc "
							+ "LEFT JOIN os.imovel imov "
							+ "LEFT JOIN imov.ligacaoAguaSituacao last "
							+ "LEFT JOIN imov.ligacaoEsgotoSituacao lest "
							+ "LEFT JOIN imov.ligacaoEsgoto lesg "
							+ "LEFT JOIN imov.localidade loc " + "LEFT JOIN imov.setorComercial sc "
							+ "LEFT JOIN imov.quadra quadra "
							+ "LEFT JOIN imov.pavimentoRua prua "
							+ "LEFT JOIN imov.pavimentoCalcada pcal "
							+ "LEFT JOIN imov.rota rotaImovel "
							+ "LEFT JOIN os.servicoTipoReferencia servTpRef "
							+ "WHERE os.id in (:idsOrdemServico) and " + "atrt.id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR;

			retorno = session.createQuery(consulta).setParameterList("idsOrdemServico", idsOrdemServico).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * Obtém as datas de encerramento e geração de uma ordem de serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 18/10/2006
	 * @param numeroOS
	 * @return Collection
	 */
	public Collection obterDatasGeracaoEncerramentoOS(Integer numeroOS) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT dataGeracao, dataEncerramento " + "FROM OrdemServico orse " + "WHERE orse.id = :numeroOS";

			retorno = session.createQuery(consulta).setInteger("numeroOS", numeroOS).setMaxResults(1).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * Recupera o id do imóvel do registro atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 19/10/2006
	 * @param numeroOS
	 * @return Collection
	 */
	public Integer recuperarIdImovelDoRA(Integer idRA) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT imov.id " + "FROM RegistroAtendimento ra " + "LEFT JOIN ra.imovel imov " + "WHERE ra.id = :idRA";

			retorno = (Integer) session.createQuery(consulta).setInteger("idRA", idRA).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * Recupera o id do imóvel do registro atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 19/10/2006
	 * @param numeroOS
	 * @return Collection
	 */
	public Short recuperarSituacaoOSReferida(Integer idOSReferida) throws ErroRepositorioException{

		Short retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT osReferida.situacaoOsReferencia " + "FROM OsReferidaRetornoTipo osReferida "
							+ "WHERE osReferida.id = :idOSReferida";

			retorno = (Short) session.createQuery(consulta).setInteger("idOSReferida", idOSReferida).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0482] - Obter Endereço Abreviado da Ocorrência do RA
	 * Pesquisa os dados da OS usados para saber de onde será recebido o
	 * endereço abreviado
	 * 
	 * @author Rafael Corrêa
	 * @date 19/10/2006
	 * @author Saulo Lima
	 * @date 11/02/2009
	 *       Adicionado o Id do imovel da Ordem de Serviço
	 * @param idOrdemServico
	 * @throws ErroRepositorioException
	 */

	public Object[] obterDadosPesquisaEnderecoAbreviadoOS(Integer idOrdemServico) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT os.registroAtendimento.id, cd.imovel.id, os.imovel.id FROM OrdemServico os " // +
							// "LEFT JOIN os.registroAtendimento ra "
							+ "LEFT JOIN os.cobrancaDocumento cd " // + "LEFT JOIN cd.imovel imov "
							// +
							// "LEFT JOIN os.imovel imovOS "
							+ "WHERE os.id = :idOS";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idOS", idOrdemServico).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Saulo Lima
	 * @since 26/02/2009
	 * @param Integer
	 *            id da Unidade de Destino
	 * @return String
	 *         Query utilizada como subquery em outras consultas
	 * @author Hugo Lima
	 * @date 16/02/2012
	 *       Mudança para desvincular a OS do RA
	 */
	public String criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(Integer idUnidadeDestino, Short permiteTramiteIndependente){

		String consulta = "";
		if(permiteTramiteIndependente.intValue() == ConstantesSistema.SIM.intValue()){

			consulta = "SELECT DISTINCT os.id " + "FROM Tramite tramite " + "INNER JOIN tramite.ordemServico os "
							+ "WHERE tramite.unidadeOrganizacionalDestino.id = " + idUnidadeDestino + " AND tramite.dataTramite = "
							+ "(SELECT MAX(tram.dataTramite) FROM Tramite tram " + "WHERE tram.ordemServico.id = os.id) "
							+ "GROUP BY os.id";
		}else{

			consulta = "SELECT ordem.id FROM OrdemServico ordem "
							+ " WHERE ordem.registroAtendimento.id in (SELECT  reg.id FROM Tramite tramit "
							+ " INNER JOIN tramit.registroAtendimento reg " + " WHERE tramit.unidadeOrganizacionalDestino.id = "
							+ idUnidadeDestino + " AND tramit.dataTramite = "
							+ " (SELECT MAX(tram.dataTramite) FROM Tramite tram WHERE tram.registroAtendimento.id = reg.id)) ";

		}

		return consulta;
	}

	/**
	 * Imprimir OS
	 * Atualiza a data de emissão e a de última alteração de OS quando gerar o
	 * relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 26/10/2006
	 * @param colecaoIdsOrdemServico
	 * @throws ErroRepositorioException
	 */
	public void atualizarOrdemServicoRelatorio(Collection colecaoIdsOrdemServico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			String atualizarOrdemServico = "update gcom.atendimentopublico.ordemservico.OrdemServico "
							+ "set orse_tmemissao = :dataAtual, orse_tmultimaalteracao = :dataAtual "
							+ "where orse_id in (:colecaoIdsOrdemServico)";

			session.createQuery(atualizarOrdemServico).setTimestamp("dataAtual", new Date())
							.setParameterList("colecaoIdsOrdemServico", colecaoIdsOrdemServico).executeUpdate();

		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
			// } catch (HibernateException e) {
			// levanta a exceção para a próxima camada
			// throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Cria a query de acordo com os parâmetros de pesquisa informados pelo
	 * usuário
	 * [UC0492] - Gerar Relatório Acompanhamento de Execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return Collection
	 */
	public Collection pesquisarOSGerarRelatorioAcompanhamentoExecucao(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String tipoOrdenacao, String idLocalidade)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT DISTINCT os.orse_id as idOS, " // 0
							+ " os.orse_cdsituacao as situacaoOS, " // 1
							+ " servTp.svtp_id as idServTp, " // 2
							+ " servTp.svtp_dsservicotipo as descricaoServTp, " // 3
							+ " ra.rgat_id as idRA, " // 4
							+ " ra.rgat_tmregistroatendimento as dtSolicitacao, " // 5
							+ " os.orse_tmencerramento as dtEncerramentoOS, " // 6
							+ " roteiroProgramacao.pgrt_tmroteiro as dtProgramacao, " // 7
							+ " unidadeAtendimento.unid_id as idUnidadeAtendimento, " // 8
							+ " unidadeAtendimento.unid_dsunidade as nomeUnidadeAtendimento, " // 9
							+ " ra.rgat_tmencerramento"
							+ " as dtEncerramentoRA, " // 10
							+ " solTpEsp.step_nnhorasprazo as horasPrazo, " // 11
							+ " osProgramacao.ospg_icativo as indicadorAtivo, " // 12
							+ " osProgramacao.ospg_icequipeprincipal as  indicadorEquipePrincipal," // 13
							+ " os.imov_id as  idImovel" // 14
							+ " FROM " + " ordem_servico os " + " INNER JOIN "
							+ " servico_tipo servTp "
							+ " on os.svtp_id = servTp.svtp_id " + " INNER JOIN "
							+ " ordem_servico_unidade osUnidade "
							+ " on osUnidade.orse_id = os.orse_id ";
			if(idUnidadeAtual != null && !idUnidadeAtual.equals("")){
				consulta = consulta + "and osUnidade.unid_id = " + idUnidadeAtual + " ";
			}

			if(idLocalidade != null && !idLocalidade.equals("")){
				consulta = consulta + " inner JOIN imovel i on i.imov_id = os.imov_id and  i.loca_id = " + idLocalidade + " ";
			}

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta = consulta + " INNER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " registro_atendimento_unidade raUnidade " + " on raUnidade.rgat_id = ra.rgat_id and "
								+ " raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR + " INNER JOIN "
								+ " unidade_organizacional unidadeAtendimento " + " on unidadeAtendimento.unid_id = raUnidade.unid_id "
								+ " INNER JOIN " + " solicitacao_tipo_especificacao solTpEsp " + " on solTpEsp.step_id = ra.step_id ";

				if(ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar().toString().equals(ConstantesSistema.SIM.toString())){
					consulta = consulta + " INNER JOIN " + " tramite tr " + " on tr.orse_id = os.orse_id ";
				}

			}else{
				consulta = consulta + " LEFT OUTER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id ";
			}

			if(idEquipeExecucao != null && !idEquipeExecucao.equals("")){

				consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_atividade osAtividade "
								+ " on osAtividade.orse_id = os.orse_id " + " LEFT OUTER JOIN "
								+ " os_atividade_periodo_execucao osAtividadePeriodoExecucao "
								+ " on osAtividadePeriodoExecucao.osat_id = osAtividade.osat_id " + " LEFT OUTER JOIN "
								+ " os_execucao_equipe osExecucaoEquipe "
								+ " on osExecucaoEquipe.oape_id = osAtividadePeriodoExecucao.oape_id ";

			}

			consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_programacao osProgramacao "
							+ " on osProgramacao.orse_id = os.orse_id " + " LEFT OUTER JOIN " + " programacao_roteiro roteiroProgramacao "
							+ " on osProgramacao.pgrt_id = roteiroProgramacao.pgrt_id ";

			String condicional = criarCondicionaisOSGerarRelatorio(origemServico, situacaoOS, idsServicosTipos, idUnidadeAtendimento,
							idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
							periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao, idEquipeExecucao, null);
			String subselect = "(roteiroProgramacao.pgrt_id is null or roteiroProgramacao.pgrt_tmroteiro in (select max(roteiroProgramacao.pgrt_tmroteiro) FROM ordem_servico_programacao osProgramacao "
							+ " INNER JOIN programacao_roteiro roteiroProgramacao "
							+ " on osProgramacao.pgrt_id = roteiroProgramacao.pgrt_id " + " WHERE osProgramacao.orse_id = os.orse_id))";

			if(condicional.equals("")){
				consulta = consulta + " where " + subselect;
			}else{
				consulta = consulta + condicional + "and " + subselect;
			}

			if(tipoOrdenacao != null && tipoOrdenacao.equals("1")){
				consulta = consulta + " order by servTp.svtp_id, ra.rgat_id ";
			}

			retorno = session.createSQLQuery(consulta).addScalar("idOS", Hibernate.INTEGER).addScalar("situacaoOS", Hibernate.SHORT)
							.addScalar("idServTp", Hibernate.INTEGER).addScalar("descricaoServTp", Hibernate.STRING)
							.addScalar("idRA", Hibernate.INTEGER).addScalar("dtSolicitacao", Hibernate.TIMESTAMP)
							.addScalar("dtEncerramentoOS", Hibernate.TIMESTAMP).addScalar("dtProgramacao", Hibernate.TIMESTAMP)
							.addScalar("idUnidadeAtendimento", Hibernate.INTEGER).addScalar("nomeUnidadeAtendimento", Hibernate.STRING)
							.addScalar("dtEncerramentoRA", Hibernate.TIMESTAMP).addScalar("horasPrazo", Hibernate.INTEGER)
							.addScalar("indicadorAtivo", Hibernate.SHORT).addScalar("indicadorEquipePrincipal", Hibernate.SHORT)
							.addScalar("idImovel", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Cria as condicionais de acordo com os parâmetros de pesquisa informados
	 * pelo usuário
	 * [UC0492] - Gerar Relatório Acompanhamento de Execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @author Saulo Lima
	 * @date 05/02/2009
	 *       Trabalhar com Timestamp em vez de Date.
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return String
	 */
	public String criarCondicionaisOSGerarRelatorioAcompanhamentoExecucao(String origemServico, String situacaoOS,
					String[] idsServicosTipos, String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento,
					Date periodoAtendimentoInicial, Date periodoAtendimentoFinal, Date periodoEncerramentoInicial,
					Date periodoEncerramentoFinal, String idEquipeProgramacao, String idEquipeExecucao){

		String sql = " where ";

		if(origemServico != null && !origemServico.equals("")){
			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SELETIVO.toString())){
				sql = sql + " os.cbdo_id  is not null " + " and ";
			}
		}

		if(situacaoOS != null && !situacaoOS.equals("")){
			if(situacaoOS.equals(OrdemServico.SITUACAO_ENCERRADO.toString())){
				sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_ENCERRADO + " and ";
			}else if(situacaoOS.equals(OrdemServico.SITUACAO_PENDENTE.toString())){
				sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_PENDENTE + " and ";
			}
		}

		if(idsServicosTipos != null && !idsServicosTipos.equals("")
						&& (idsServicosTipos.length != 1 || !idsServicosTipos[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){

			String valoresIn = "";
			for(int i = 0; i < idsServicosTipos.length; i++){
				if(!idsServicosTipos[i].equals("")){
					valoresIn = valoresIn + idsServicosTipos[i] + ",";
				}
			}
			if(!valoresIn.equals("")){
				sql = sql + " servTp.svtp_id in (" + valoresIn;
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
			}
		}

		if(idUnidadeAtendimento != null && !idUnidadeAtendimento.equals("")){
			sql = sql + " unidadeAtendimento.unid_id = " + idUnidadeAtendimento + " and ";
		}

		try{
			if(ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar().toString().equals(ConstantesSistema.SIM.toString())){

				if(idUnidadeAtual != null && !idUnidadeAtual.equals("")){
					sql = sql + " tr.unid_iddestino = " + idUnidadeAtual
									+ " and tr.tram_tmtramite in (select max(tr.tram_tmtramite) from tramite tr where "
									+ " ra.rgat_id = tr.rgat_id)" + " and ";
				}

			}
		}catch(ControladorException e){
			e.printStackTrace();
		}

		if(idUnidadeEncerramento != null && !idUnidadeEncerramento.equals("")){
			sql = sql + " osUnidade.unid_id = " + idUnidadeEncerramento + " and " + " osUnidade.attp_id = "
							+ AtendimentoRelacaoTipo.ENCERRAR + " and ";
		}

		if(periodoAtendimentoInicial != null && !periodoAtendimentoFinal.equals("")){

			String data1 = Util.recuperaDataInvertida(periodoAtendimentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			// sql = sql + " ra.rgat_tmregistroatendimento >= to_date('"+ data1
			// +"','YYYY-MM-DD') and ";
			sql = sql + " ra.rgat_tmregistroatendimento >= to_timestamp('" + data1
							+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoAtendimentoFinal != null && !periodoAtendimentoFinal.equals("")){
			// String data2 =
			// Util.recuperaDataInvertida(Util.adicionarNumeroDiasDeUmaData(periodoAtendimentoFinal,
			// 1));
			String data2 = Util.recuperaDataInvertida(periodoAtendimentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			// sql = sql + " ra.rgat_tmregistroatendimento <= to_date('"+ data2
			// +"','YYYY-MM-DD') and ";
			sql = sql + " ra.rgat_tmregistroatendimento <= to_timestamp('" + data2
							+ " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoInicial != null && !periodoEncerramentoInicial.equals("")){
			String data1 = Util.recuperaDataInvertida(periodoEncerramentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			// sql = sql + " os.orse_tmencerramento >= to_date('" + data1 + "','YYYY-MM-DD') and ";
			sql = sql + " os.orse_tmencerramento >= to_timestamp('" + data1 + " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoFinal != null && !periodoEncerramentoFinal.equals("")){
			// String data2 =
			// Util.recuperaDataInvertida(Util.adicionarNumeroDiasDeUmaData(periodoEncerramentoFinal,
			// 1));
			String data2 = Util.recuperaDataInvertida(periodoEncerramentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			// sql = sql + " os.orse_tmencerramento <= to_date('" + data2 + "','YYYY-MM-DD') and ";
			sql = sql + " os.orse_tmencerramento <= to_timestamp('" + data2 + " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(idEquipeProgramacao != null && !idEquipeProgramacao.equals("")){
			sql = sql + " osProgramacao.eqpe_id = " + idEquipeProgramacao + " and osProgramacao.ospg_icativo = "
							+ ConstantesSistema.INDICADOR_USO_ATIVO + " and ";
		}

		if(idEquipeExecucao != null && !idEquipeExecucao.equals("")){
			sql = sql + " osExecucaoEquipe.eqpe_id = " + idEquipeExecucao + " and ";
		}

		// retira o " and " q fica sobrando no final da query
		sql = Util.formatarHQL(sql, 4);

		return sql;
	}

	/**
	 * [UC0461] - Manter Dados das Atividades da Ordem de Serviço
	 * Pesquisa os dados da OrdemServicoAtividade
	 * 
	 * @author Raphael Rossiter
	 * @date 01/11/2006
	 * @param idOrdemServico
	 *            ,
	 *            idAtividade
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarOrdemServicoAtividade(Integer numeroOS, Integer idAtividade) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT osat.id, osat.ultimaAlteracao " + "FROM OrdemServicoAtividade osat "
							+ "WHERE osat.ordemServico.id = :numeroOS AND osat.atividade.id = :idAtividade ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("numeroOS", numeroOS).setInteger("idAtividade", idAtividade)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0461] - Manter Dados das Atividades da Ordem de Serviço
	 * Pesquisa os dados da OsAtividadeMaterialExecucao associada à
	 * OrdemServicoAtividade para a data informada
	 * 
	 * @author Raphael Rossiter
	 * @date 01/11/2006
	 * @param idOrdemServicoAtividade
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOsAtividadeMaterialExecucao(Integer idOrdemServicoAtividade) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT oame.id, oame.quantidadeMaterial , oame.material, oame.ultimaAlteracao "
							+ "FROM OsAtividadeMaterialExecucao oame " + "INNER JOIN oame.material mate "
							+ "WHERE oame.ordemServicoAtividade.id = :idOrdemServicoAtividade " + "ORDER BY mate.descricao ";

			retorno = (Collection) session.createQuery(consulta).setInteger("idOrdemServicoAtividade", idOrdemServicoAtividade).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa a equipe principal de uma OS de programação através do id da OS
	 * 
	 * @author Rafael Corrêa
	 * @date 07/11/2006
	 * @param idOS
	 * @throws ErroRepositorioException
	 */
	public Equipe pesquisarEquipePrincipalOSProgramacao(Integer idOS) throws ErroRepositorioException{

		Equipe equipe = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT equipe " + "FROM OrdemServicoProgramacao osProgramacao " + "INNER JOIN osProgramacao.ordemServico os "
							+ "INNER JOIN osProgramacao.equipe equipe " + "WHERE os.id = :idOS AND osProgramacao.indicadorAtivo = "
							+ OrdemServicoProgramacao.INDICADOR_ATIVO + " AND osProgramacao.indicadorEquipePrincipal = 1";

			equipe = (Equipe) session.createQuery(consulta).setInteger("idOS", idOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return equipe;
	}

	/**
	 * Pesquisa a equipe principal de uma OS de execução da equipe através do id
	 * da OS
	 * 
	 * @author Rafael Corrêa
	 * @date 07/11/2006
	 * @param idOS
	 * @throws ErroRepositorioException
	 */
	public Equipe pesquisarEquipePrincipalOSExecucaoEquipe(Integer idOS) throws ErroRepositorioException{

		Equipe equipe = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT equipe " + "FROM OsExecucaoEquipe osExecucaoEquipe " + "INNER JOIN osExecucaoEquipe.equipe equipe "
							+ "INNER JOIN osExecucaoEquipe.osAtividadePeriodoExecucao osAtividadePeriodoExecucao "
							+ "INNER JOIN osAtividadePeriodoExecucao.ordemServicoAtividade ordemServicoAtividade "
							+ "INNER JOIN ordemServicoAtividade.ordemServico os " + "WHERE os.id = :idOS "
							+ "ORDER BY osAtividadePeriodoExecucao.dataFim desc ";

			equipe = (Equipe) session.createQuery(consulta).setInteger("idOS", idOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return equipe;
	}

	/**
	 * Cria o count de acordo com os parâmetros de pesquisa informados pelo
	 * usuário
	 * [UC0492] - Gerar Relatório Acompanhamento de Execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return int
	 */
	public int pesquisarOSGerarRelatorioAcompanhamentoExecucaoCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT COUNT(DISTINCT os.orse_id) as count" + " FROM " + " ordem_servico os " + " INNER JOIN "
							+ " servico_tipo servTp " + " on os.svtp_id = servTp.svtp_id ";

			consulta = consulta + " INNER JOIN " + " ordem_servico_unidade osUnidade " + " on osUnidade.orse_id = os.orse_id ";
			if(idUnidadeAtual != null && !idUnidadeAtual.equals("")){
				consulta = consulta + "and osUnidade.unid_id = " + idUnidadeAtual + " ";
			}

			if(idLocalidade != null && !idLocalidade.equals("")){
				consulta = consulta + " INNER JOIN imovel i on i.imov_id = os.imov_id and i.loca_id = " + idLocalidade + " ";
			}

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta = consulta + " INNER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " registro_atendimento_unidade raUnidade " + " on raUnidade.rgat_id = ra.rgat_id and "
								+ " raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR + " INNER JOIN "
								+ " unidade_organizacional unidadeAtendimento " + " on unidadeAtendimento.unid_id = raUnidade.unid_id ";

				if(ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar().toString().equals(ConstantesSistema.SIM.toString())){

					consulta = consulta + " INNER JOIN " + " tramite tr " + " on tr.orse_id = os.orse_id ";

				}

			}else{

				consulta = consulta + " LEFT OUTER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id ";

			}

			if(idEquipeProgramacao != null && !idEquipeProgramacao.equals("")){
				consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_programacao osProgramacao "
								+ " on osProgramacao.orse_id = os.orse_id ";
			}

			// consulta = consulta
			// + " LEFT OUTER JOIN "
			// + " programacao_roteiro roteiroProgramacao "
			// + " on osProgramacao.pgrt_id = roteiroProgramacao.pgrt_id ";

			if(idEquipeExecucao != null && !idEquipeExecucao.equals("")){
				consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_atividade osAtividade "
								+ " on osAtividade.orse_id = os.orse_id " + " LEFT OUTER JOIN "
								+ " os_atividade_periodo_execucao osAtividadePeriodoExecucao "
								+ " on osAtividadePeriodoExecucao.osat_id = osAtividade.osat_id " + " LEFT OUTER JOIN "
								+ " os_execucao_equipe osExecucaoEquipe "
								+ " on osExecucaoEquipe.oape_id = osAtividadePeriodoExecucao.oape_id ";
			}

			consulta = consulta
							+ criarCondicionaisOSGerarRelatorio(origemServico, situacaoOS, idsServicosTipos, idUnidadeAtendimento,
											idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
											periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao, idEquipeExecucao,
											idLocalidade);

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("count", Hibernate.INTEGER).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa as equipes de acordo com os parâmetros informado pelo usuário
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idEquipe
	 * @param nome
	 * @param placa
	 * @param cargaTrabalho
	 * @param idUnidade
	 * @param idFuncionario
	 * @param idPerfilServico
	 * @param indicadorUso
	 * @param numeroPagina
	 * @return Collection
	 */
	public Collection pesquisarEquipes(String idEquipe, String nome, String placa, String cargaTrabalho, String idUnidade,
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idEquipeTipo, Integer numeroPagina)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT DISTINCT equipe " + "FROM EquipeComponentes eqpCom " + "LEFT JOIN eqpCom.funcionario func "
							+ "INNER JOIN eqpCom.equipe equipe " + "INNER JOIN FETCH equipe.unidadeOrganizacional unidade "
							+ "INNER JOIN FETCH equipe.servicoPerfilTipo servicoPerfilTp "
							+ "LEFT JOIN FETCH equipe.equipeTipo equipeTipo ";

			consulta = consulta
							+ criarCondicionaisPesquisarEquipes(idEquipe, nome, placa, cargaTrabalho, idUnidade, idFuncionario,
											idPerfilServico, indicadorUso, idEquipeTipo);

			retorno = session.createQuery(consulta).setFirstResult(10 * numeroPagina).setMaxResults(10).list();

			// while (iterator.hasNext()) {
			//
			// Equipe equipe = iterator.next();
			//
			// // carrega todos os objetos
			// Hibernate.initialize(equipe.getUnidadeOrganizacional());
			// Hibernate.initialize(equipe.getServicoPerfilTipo());
			//
			// retorno.add(equipe);
			//
			// }

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Cria as condicionais de pesquisa solicitadas pelo usuário
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idEquipe
	 * @param nome
	 * @param placa
	 * @param cargaTrabalho
	 * @param idUnidade
	 * @param idFuncionario
	 * @param idPerfilServico
	 * @param indicadorUso
	 * @return Collection
	 */
	public String criarCondicionaisPesquisarEquipes(String idEquipe, String nome, String placa, String cargaTrabalho, String idUnidade,
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idEquipeTipo){

		String hql = " WHERE ";

		if(idEquipe != null && !idEquipe.trim().equals("")){
			hql = hql + " equipe.id = " + idEquipe + " AND ";
		}

		if(nome != null && !nome.trim().equals("")){
			hql = hql + " equipe.nome LIKE '" + nome + "%'" + " AND ";
		}

		if(placa != null && !placa.trim().equals("")){
			hql = hql + " equipe.placaVeiculo = " + placa + " AND ";
		}

		if(cargaTrabalho != null && !cargaTrabalho.trim().equals("")){
			hql = hql + " equipe.cargaTrabalho = " + cargaTrabalho + " AND ";
		}

		if(idUnidade != null && !idUnidade.trim().equals("")){
			hql = hql + " unidade.id = " + idUnidade + " AND ";
		}

		if(idFuncionario != null && !idFuncionario.trim().equals("")){
			hql = hql + " func.id = " + idFuncionario + " AND ";
		}

		if(idPerfilServico != null && !idPerfilServico.trim().equals("")){
			hql = hql + " servicoPerfilTp.id = " + idPerfilServico + " AND ";
		}

		if(indicadorUso != null && !indicadorUso.equals("")){
			hql = hql + " equipe.indicadorUso = " + indicadorUso + " AND ";
		}

		if(idEquipeTipo != null && idEquipeTipo != ConstantesSistema.NUMERO_NAO_INFORMADO){
			hql = hql + " equipeTipo.id = " + idEquipeTipo + " AND ";
		}

		// retira o " and " q fica sobrando no final da query
		hql = Util.formatarHQL(hql, 4);

		return hql;

	}

	/**
	 * Verifica a quantidade de registros retornados da pesquisa de equipe
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idEquipe
	 * @param nome
	 * @param placa
	 * @param cargaTrabalho
	 * @param idUnidade
	 * @param idFuncionario
	 * @param idPerfilServico
	 * @param indicadorUso
	 * @return int
	 */
	public int pesquisarEquipesCount(String idEquipe, String nome, String placa, String cargaTrabalho, String idUnidade,
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idEquipeTipo)
					throws ErroRepositorioException{

		int retorno = 0;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT COUNT(DISTINCT equipe.id) " + "FROM EquipeComponentes eqpCom " + "LEFT JOIN eqpCom.funcionario func "
							+ "INNER JOIN eqpCom.equipe equipe " + "INNER JOIN equipe.unidadeOrganizacional unidade "
							+ "INNER JOIN equipe.servicoPerfilTipo servicoPerfilTp " + "LEFT JOIN equipe.equipeTipo equipeTipo ";

			consulta = consulta
							+ criarCondicionaisPesquisarEquipes(idEquipe, nome, placa, cargaTrabalho, idUnidade, idFuncionario,
											idPerfilServico, indicadorUso, idEquipeTipo);

			retorno = ((Number) session.createQuery(consulta).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Verifica se o serviço associado á ordem de serviço não corresponde a um
	 * serviço de fiscalização de infração
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 01/11/06
	 * @return Integer
	 */
	public Object[] pesquisarServicoTipoComFiscalizacaoInfracao(Integer idOS) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT servTipo.descricao,os.situacao,cobDocumento.id " + "FROM OrdemServico os "
							+ "INNER JOIN os.servicoTipo servTipo " + "LEFT JOIN os.cobrancaDocumento cobDocumento "
							+ "WHERE os.id = :idOS AND servTipo.indicadorFiscalizacaoInfracao <> :nao";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idOS", idOS).setShort("nao", ConstantesSistema.NAO)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsOS(Integer idOS) throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT servicoTipo.descricao," // 0
							+ "imovel.id, "// 1
							+ "ligAguaSitu.descricao,"// 2
							+ "ligEsgotoSitu.descricao,"// 3
							+ "leitAnormalidade.descricao, "// 4
							+ "ligAguaSitu.id,"// 5
							+ "ligEsgotoSitu.id, "// 6
							+ "servicoTipo.indicadorFiscalizacaoInfracao, " // 7
							+ "os.situacao, " // 8
							+ "cbdo.id " // 9
							+ "FROM OrdemServico os "
							+ "INNER JOIN os.servicoTipo servicoTipo  "
							+ "INNER JOIN os.imovel imovel  "
							+ "LEFT JOIN os.cobrancaDocumento cbdo  "
							+ "LEFT JOIN imovel.ligacaoAguaSituacao ligAguaSitu  "
							+ "LEFT JOIN imovel.ligacaoEsgotoSituacao ligEsgotoSitu  "
							+ "LEFT JOIN imovel.leituraAnormalidade leitAnormalidade  " + "WHERE os.id = :idOS ";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idOS", idOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarIdFiscalizacaoSituacaoAgua(Integer idLigacaoAguaSituacao, Integer idSituacaoEncontrada)
					throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fiscLigAgua.comp_id.idLigacaoAguaSituacao, fiscLigAgua.ligacaoAguaSituacaoByLastIdnova.id, "
							+ "step.id, sotp.id " + "FROM FiscalizacaoSituacaoAgua fiscLigAgua "
							+ "LEFT JOIN fiscLigAgua.solicitacaoTipoEspecificacao step " + "LEFT JOIN step.solicitacaoTipo sotp "
							+ "LEFT JOIN step.servicoTipo svtp "
							+ "WHERE fiscLigAgua.comp_id.idLigacaoAguaSituacao = :idLigacaoAguaSituacao "
							+ "AND fiscLigAgua.comp_id.idFiscalizacaoSituacao = :idSituacaoEncontrada ";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idLigacaoAguaSituacao", idLigacaoAguaSituacao)
							.setInteger("idSituacaoEncontrada", idSituacaoEncontrada).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Obtém o número de meses da fatura a partir da tabela FISCALIZACAO_SITUACAO_AGUA
	 * 
	 * @author Raphael Rossiter
	 * @date 16/03/2007
	 * @param idLigacaoAguaSituacao
	 *            , idSituacaoEncontrada
	 * @return numeroMesesFatura
	 * @throws ErroRepositorioException
	 */
	public Short pesquisarNumeroMesesFaturaAgua(Integer idLigacaoAguaSituacao, Integer idSituacaoEncontrada)
					throws ErroRepositorioException{

		Short retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fiscLigAgua.numeroMesesFatura " + "FROM FiscalizacaoSituacaoAgua fiscLigAgua "
							+ "WHERE fiscLigAgua.comp_id.idLigacaoAguaSituacao = :idLigacaoAguaSituacao "
							+ "AND fiscLigAgua.comp_id.idFiscalizacaoSituacao = :idSituacaoEncontrada ";

			retornoConsulta = (Short) session.createQuery(consulta).setInteger("idLigacaoAguaSituacao", idLigacaoAguaSituacao)
							.setInteger("idSituacaoEncontrada", idSituacaoEncontrada).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Obtém o número de meses da fatura a partir da tabela FISCALIZACAO_SITUACAO_ESGOTO
	 * 
	 * @author Raphael Rossiter
	 * @date 16/03/2007
	 * @param idLigacaoEsgotoSituacao
	 *            , idSituacaoEncontrada
	 * @return numeroMesesFatura
	 * @throws ErroRepositorioException
	 */
	public Short pesquisarNumeroMesesFaturaEsgoto(Integer idLigacaoEsgotoSituacao, Integer idSituacaoEncontrada)
					throws ErroRepositorioException{

		Short retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fiscLigEsgoto.numeroMesesFatura " + "FROM FiscalizacaoSituacaoEsgoto fiscLigEsgoto "
							+ "WHERE fiscLigEsgoto.comp_id.idLigacaoEsgotoSituacao = :idLigacaoEsgotoSituacao "
							+ "AND fiscLigEsgoto.comp_id.idFiscalizacaoSituacao = :idSituacaoEncontrada ";

			retornoConsulta = (Short) session.createQuery(consulta).setInteger("idLigacaoEsgotoSituacao", idLigacaoEsgotoSituacao)
							.setInteger("idSituacaoEncontrada", idSituacaoEncontrada).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarIdFiscalizacaoSituacaoEsgoto(Integer idLigacaoEsgotoSituacao, Integer idSituacaoEncontrada)
					throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fiscLigEsgoto.comp_id.idLigacaoEsgotoSituacao, fiscLigEsgoto.ligacaoEsgotoSituacaoByLestIdnova, "
							+ "step.id, sotp.id " + "FROM FiscalizacaoSituacaoEsgoto fiscLigEsgoto "
							+ "LEFT JOIN fiscLigEsgoto.solicitacaoTipoEspecificacao step " + "LEFT JOIN step.solicitacaoTipo sotp "
							+ "LEFT JOIN step.servicoTipo svtp "
							+ "WHERE fiscLigEsgoto.comp_id.idLigacaoEsgotoSituacao = :idLigacaoEsgotoSituacao "
							+ "AND fiscLigEsgoto.comp_id.idFiscalizacaoSituacao = :idSituacaoEncontrada ";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idLigacaoEsgotoSituacao", idLigacaoEsgotoSituacao)
							.setInteger("idSituacaoEncontrada", idSituacaoEncontrada).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * [SB0001] - Atualizar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void atualizarParmsOrdemFiscalizacao(Integer numeroOS, Integer idSituacaoEncontrada, String indicadorDocumentoEntregue)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			String atualizarOS = "";

			atualizarOS = "update gcom.atendimentopublico.ordemservico.OrdemServico " + "set orse_tmultimaalteracao = :ultimaAlteracao, "
							+ "fzst_id = :idSituacaoEncontrada, " + "orse_dtfiscalizacaosituacao = :ultimaAlteracao ";
			if(indicadorDocumentoEntregue != null && !indicadorDocumentoEntregue.equals("")){
				atualizarOS = atualizarOS + ",orse_cdtiporecebimento = " + new Short(indicadorDocumentoEntregue);
			}else{
				atualizarOS = atualizarOS + ",orse_cdtiporecebimento = null";
			}
			atualizarOS = atualizarOS + " where orse_id = :idOrdemServico";

			session.createQuery(atualizarOS).setInteger("idOrdemServico", numeroOS).setTimestamp("ultimaAlteracao", new Date())
							.setInteger("idSituacaoEncontrada", idSituacaoEncontrada).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarIdFiscalizacaoSituacaoAguaOS(Integer idFiscalizacaoSituacao, Integer idLigacaoAguaSituacao)
					throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fsag.comp_id.idLigacaoAguaSituacao, fsag.indicadorConsumoFixado, "
							+ "fzst.indicadorLigacaoDataAtualiza, fsag.ligacaoAguaSituacaoByLastIdnova.id "
							+ "FROM FiscalizacaoSituacaoAgua fsag " + "LEFT JOIN fsag.fiscalizacaoSituacao fzst "
							+ "WHERE fzst.id = :idFiscalizacaoSituacao "
							+ "AND fsag.comp_id.idLigacaoAguaSituacao = :idLigacaoAguaSituacao ";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idFiscalizacaoSituacao", idFiscalizacaoSituacao)
							.setInteger("idLigacaoAguaSituacao", idLigacaoAguaSituacao).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarIdFiscalizacaoSituacaoEsgotoOS(Integer idFiscalizacaoSituacao, Integer idLigacaoEsgotoSituacao)
					throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fseg.comp_id.idLigacaoEsgotoSituacao, fseg.indicadorConsumoFixado, "
							+ "fseg.ligacaoEsgotoSituacaoByLestIdnova.id " + "FROM FiscalizacaoSituacaoEsgoto fseg "
							+ "LEFT JOIN fseg.fiscalizacaoSituacao fzst " + "WHERE fzst.id = :idFiscalizacaoSituacao "
							+ "AND fseg.comp_id.idLigacaoEsgotoSituacao = :idLigacaoEsgotoSituacao ";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idFiscalizacaoSituacao", idFiscalizacaoSituacao)
							.setInteger("idLigacaoEsgotoSituacao", idLigacaoEsgotoSituacao).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarHidormetroCapacidadeFiscalizacaoAgua(Integer idImovel) throws ErroRepositorioException{

		Integer retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fiscSitHidCapacidade.comp_id.idHidrometroCapacidade "
							+ "FROM FiscalizacaoSituacaoHidrometroCapacidade fiscSitHidCapacidade "
							+ "LEFT JOIN fiscSitHidCapacidade.hidrometroCapacidade hidroCapacidade " + "WHERE hidroCapacidade.id in "
							+ "(SELECT hidroCapac.id " + "FROM HidrometroInstalacaoHistorico hidInstHistorico "
							+ "LEFT JOIN hidInstHistorico.hidrometro hidr " + "LEFT JOIN hidr.hidrometroCapacidade hidroCapac "
							+ "LEFT JOIN hidInstHistorico.ligacaoAgua ligAgua " + "WHERE ligAgua.id = :idImovel )";
			retornoConsulta = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarHidormetroCapacidadeFiscalizacaoPoco(Integer idImovel) throws ErroRepositorioException{

		Integer retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fiscSitHidCapacidade.id " + "FROM FiscalizacaoSituacaoHidrometroCapacidade fiscSitHidCapacidade "
							+ "LEFT JOIN fiscSitHidCapacidade.hidrometroCapacidade hidroCapacidade " + "WHERE hidroCapacidade.id in "
							+ "(SELECT hidroCapac.id " + "FROM HidrometroInstalacaoHistorico hidInstHistorico "
							+ "LEFT JOIN hidInstHistorico.hidrometro hidr " + "LEFT JOIN hidr.hidrometroCapacidade hidroCapac"
							+ "LEFT JOIN hidInstHistorico.imovel imov " + "WHERE imov.id = :idImovel )";
			retornoConsulta = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Collection pesquisarFiscalizacaoSituacaoServicoACobrar(Integer idFiscalizacaoSituacao) throws ErroRepositorioException{

		Collection retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT fiscSitServicoACobrar.consumoCalculo, fiscSitServicoACobrar.comp_id.idDebitoTipo "
							+ "FROM FiscalizacaoSituacaoServicoACobrar fiscSitServicoACobrar "
							+ "LEFT JOIN fiscSitServicoACobrar.fiscalizacaoSituacao fiscalizacaoSit "
							+ "WHERE fiscalizacaoSit.id = :idFiscalizacaoSituacao ";
			// +
			// "WHERE fiscSitServicoACobrar.comp_id.idFiscalizacaoSituacao = :idFiscalizacaoSituacao";

			retornoConsulta = session.createQuery(consulta).setInteger("idFiscalizacaoSituacao", idFiscalizacaoSituacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 20/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsFiscalizacaoSituacaoServicoACobrar(Integer idFiscalizacaoSituacao, Integer idDebitoTipo)
					throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT dbtp.id, fscb.numeroVezesServicoCalculadoValor " + "FROM FiscalizacaoSituacaoServicoACobrar fscb "
							+ "LEFT JOIN fscb.fiscalizacaoSituacao fzst " + "LEFT JOIN fscb.debitoTipo dbtp "
							+ "WHERE fzst.id = :idFiscalizacaoSituacao AND dbtp.id = :idDebitoTipo ";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idFiscalizacaoSituacao", idFiscalizacaoSituacao)
							.setInteger("idDebitoTipo", idDebitoTipo).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico verifica o tamanho da consulta
	 * [SB001] Selecionar Ordem de Servico por Situação [SB002] Selecionar Ordem
	 * de Servico por Situação da Programação [SB003] Selecionar Ordem de
	 * Servico por Matricula do Imovel [SB004] Selecionar Ordem de Servico por
	 * Codigo do Cliente [SB005] Selecionar Ordem de Servico por Unidade
	 * Superior [SB006] Selecionar Ordem de Servico por Município [SB007]
	 * Selecionar Ordem de Servico por Bairro [SB008] Selecionar Ordem de
	 * Servico por Bairro Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarOrdemServicoTamanho(PesquisarOrdemServicoHelper filtro, Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Integer retorno = null;

		Integer numeroRA = filtro.getNumeroRA();
		Integer numeroOS = filtro.getNumeroOS();
		Integer documentoCobranca = filtro.getDocumentoCobranca();
		short situacaoOrdemServico = filtro.getSituacaoOrdemServico();
		short situacaoProgramacao = filtro.getSituacaoProgramacao();
		Integer[] tipoServicos = filtro.getTipoServicos();

		Integer unidadeGeracao = filtro.getUnidadeGeracao();
		Integer unidadeAtual = filtro.getUnidadeAtual();
		Integer unidadeSuperior = filtro.getUnidadeSuperior();
		Integer diasAtraso = filtro.getDiasAtraso();
		Integer equipe = filtro.getEquipe();
		Short programado = filtro.getProgramado();

		Integer matriculaImovel = filtro.getMatriculaImovel();

		Set colecaoIdsOS = filtro.getColecaoIdsOS();

		Date dataAtendimentoInicial = filtro.getDataAtendimentoInicial();
		Date dataAtendimentoFinal = filtro.getDataAtendimentoFinal();

		Date dataGeracaoInicial = filtro.getDataGeracaoInicial();
		Date dataGeracaoFinal = filtro.getDataGeracaoFinal();

		Date dataProgramacaoInicial = filtro.getDataProgramacaoInicial();
		Date dataProgramacaoFinal = filtro.getDataProgramacaoFinal();

		Date dataEncerramentoInicial = filtro.getDataEncerramentoInicial();
		Date dataEncerramentoFinal = filtro.getDataEncerramentoFinal();

		Date dataExecucaoInicial = filtro.getDataExecucaoInicial();
		Date dataExecucaoFinal = filtro.getDataExecucaoFinal();

		Integer municipio = filtro.getMunicipio();
		Integer bairro = filtro.getBairro();
		Integer areaBairro = filtro.getAreaBairro();
		Integer logradouro = filtro.getLogradouro();

		String situacaoDocumentoCobranca = filtro.getSituacaoDocumentoCobranca();

		Date dataPrevisaoClienteInicial = filtro.getDataPrevisaoClienteInicial();
		Date dataPrevisaoClienteFinal = filtro.getDataPrevisaoClienteFinal();

		String indicadorReparo = filtro.getIndicadorReparo();

		if(dataProgramacaoInicial != null || dataProgramacaoFinal != null && situacaoProgramacao != ConstantesSistema.SIM.shortValue()){

			situacaoProgramacao = ConstantesSistema.SIM.shortValue();
		}

		Session session = HibernateUtil.getSession();

		String select = null;
		String from = null;
		String where = null;
		Query query = null;
		Map parameters = new HashMap();

		try{

			select = "SELECT COUNT(distinct os.id) ";

			from = " FROM OrdemServicoUnidade osUnidade " + (equipe != null ? ", OrdemServicoProgramacao osp " : "")
							+ " INNER JOIN osUnidade.ordemServico os  " + " INNER JOIN os.servicoTipo servicotipo ";

			if(situacaoDocumentoCobranca != null && !situacaoDocumentoCobranca.equals("") && !situacaoDocumentoCobranca.equals("-1")){
				from += " INNER JOIN os.cobrancaDocumento cobDoc";
			}
			where = " WHERE 1 = 1 ";

			if(indicadorReparo != null){
				from += " LEFT JOIN os.ordemServicoReparo ordemServicoReparo ";
				if(indicadorReparo.equals("1")){
					where += " AND servicotipo.indicadorVala = 1 ";
					where += " AND ordemServicoReparo is not null ";
				}else if(indicadorReparo.equals("2")){
					where += " AND servicotipo.indicadorVala = 1 ";
					where += " AND ordemServicoReparo is null ";
				}else{
					where += " AND servicotipo.indicadorVala = 1 ";
				}
			}

			// SITUACAO PENDENTE
			if(situacaoDocumentoCobranca != null && !situacaoDocumentoCobranca.equals("") && situacaoDocumentoCobranca.equals("1")){
				where += " AND cobDoc.cobrancaDebitoSituacao.descricao = (:cobrancaDebitoSituacao) ";
				parameters.put("cobrancaDebitoSituacao", "PENDENTE");
				// DIFERENTE DE PENDENTE
			}else if(situacaoDocumentoCobranca != null && !situacaoDocumentoCobranca.equals("") && situacaoDocumentoCobranca.equals("2")){
				where += " AND cobDoc.cobrancaDebitoSituacao.descricao <> (:cobrancaDebitoSituacao) ";
				parameters.put("cobrancaDebitoSituacao", "PENDENTE");
				// AMBOS
			}else if(situacaoDocumentoCobranca != null && !situacaoDocumentoCobranca.equals("") && situacaoDocumentoCobranca.equals("0")){
				where += " AND cobDoc is not null ";
			}

			// Período de Previsão para Cliente
			if(dataPrevisaoClienteInicial != null && dataPrevisaoClienteFinal != null){

				where += " AND os.registroAtendimento.dataPrevistaAtual BETWEEN (:dataPrevisaoClienteInicial) AND (:dataPrevisaoClienteFinal) ";

				dataPrevisaoClienteInicial = Util.formatarDataInicial(dataPrevisaoClienteInicial);
				dataPrevisaoClienteFinal = Util.adaptarDataFinalComparacaoBetween(dataPrevisaoClienteFinal);

				parameters.put("dataPrevisaoClienteInicial", dataPrevisaoClienteInicial);
				parameters.put("dataPrevisaoClienteFinal", dataPrevisaoClienteFinal);
			}

			if(numeroRA != null){
				where += " AND os.registroAtendimento.id = (:numeroRA) ";
				parameters.put("numeroRA", numeroRA);
			}

			if(documentoCobranca != null){
				where += " AND os.cobrancaDocumento.id = (:documentoCobranca) ";
				parameters.put("documentoCobranca", documentoCobranca);
			}

			// [SB0001] - Selecionar Ordem de Servico por Situação
			if(situacaoOrdemServico != ConstantesSistema.NUMERO_NAO_INFORMADO){
				where += " AND os.situacao = (:situacaoOrdemServico) ";
				parameters.put("situacaoOrdemServico", situacaoOrdemServico);
			}

			// Tipo de Serviços
			if(tipoServicos != null && tipoServicos.length > 0){
				where += " AND os.servicoTipo.id in (:idServicoTipo) ";
				parameters.put("idServicoTipo", tipoServicos);
			}

			// [SB0003] - Selecionar Ordem de Servico por Matricula do Imóvel
			if(matriculaImovel != null){
				where += " AND os.imovel.id = (:matriculaImovel) ";
				parameters.put("matriculaImovel", matriculaImovel);
			}

			// Unidade Geração
			if(unidadeGeracao != null){

				where += "AND osUnidade.unidadeOrganizacional.id = :idUnidadeGeracao "
								+ "AND osUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo ";

				parameters.put("idUnidadeGeracao", unidadeGeracao);
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			}

			// Unidade Atual
			if(unidadeAtual != null && permiteTramiteIndependente != null){

				if(permiteTramiteIndependente.intValue() == ConstantesSistema.SIM.intValue()){

					where += " AND EXISTS (SELECT tramite.id FROM Tramite tramite WHERE tramite.ordemServico.id = os.id "
									+ " AND tramite.unidadeOrganizacionalDestino.id = :idUnidadeAtual "
									+ " AND tramite.dataTramite = (SELECT max(tram.dataTramite) FROM Tramite tram "
									+ " WHERE tram.ordemServico.id = tramite.ordemServico.id))";

					parameters.put("idUnidadeAtual", unidadeAtual);
				}else{

					from += " LEFT JOIN os.registroAtendimento ra ";
					from += " LEFT JOIN ra.tramites tramite ";

					where += " AND ( (tramite.unidadeOrganizacionalDestino.id = :idUnidadeAtual "
									+ " AND tramite.dataTramite = (SELECT max(tram.dataTramite) FROM Tramite tram "
									+ " WHERE tram.registroAtendimento.id = ra.id) ) OR "
									+ " (ra is null AND osUnidade.unidadeOrganizacional.id = :idUnidadeAtual "
									+ " AND osUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo) )";

					parameters.put("idUnidadeAtual", unidadeAtual);
					parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
				}
			}

			// Unidade Superior
			if(unidadeSuperior != null){

				where += " AND osUnidade.unidadeOrganizacional.unidadeSuperior.id = :idUnidadeSuperior "
								+ " AND osUnidade.atendimentoRelacaoTipo.id = :idAtendimentoTipo ";

				parameters.put("idUnidadeSuperior", unidadeSuperior);
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);

			}

			// Dias de atraso

			if(diasAtraso != null){
				where += " AND (( os.situacao                                                                                                          = :situacao ";
				where += " AND (TRUNC(to_number(SUBSTR((os.dataEncerramento - ra.dataPrevistaAtual),1,instr(os.dataEncerramento - ra.dataPrevistaAtual,' '))))) >= :atraso ) ";
				where += " OR (os.situacao                                                                                                            <> :situacao1 ";
				where += " AND (TRUNC(to_number(SUBSTR((CURRENT_DATE - ra.dataPrevistaAtual),1,instr(CURRENT_DATE - ra.dataPrevistaAtual,' ')))))                >= :atraso1 )) ";

				parameters.put("atraso", diasAtraso);
				parameters.put("situacao", OrdemServico.SITUACAO_ENCERRADO);
				parameters.put("atraso1", diasAtraso);
				parameters.put("situacao1", OrdemServico.SITUACAO_ENCERRADO);
			}

			// Equipe

			if(equipe != null){

				where += " and (osp.ordemServico.id = os.id ";
				where += " 	   and osp.equipe.id = :equipe ";
				where += " 	   and ((osp.indicadorAtivo = 1) ";
				where += " 	   		or (osp.indicadorAtivo = 2 and osp.situacaoFechamento = 2))) ";

				parameters.put("equipe", equipe);
			}

			// Não programada

			if(programado != null && programado.intValue() != ConstantesSistema.TODOS.intValue()){
				where += " and os.indicadorProgramada = :programada ";

				parameters.put("programada", programado);
			}

			if(numeroOS != null){

				where += " AND os.id = :numeroOS ";
				parameters.put("numeroOS", numeroOS);

				// Ids de Os das unidades filtradas (geracao, atual e superior)
				// Ids de Os dos clientes
			}else if(colecaoIdsOS != null && !colecaoIdsOS.isEmpty()){

				where += " AND os.id in (:listaIdOS) ";
				parameters.put("listaIdOS", colecaoIdsOS);
			}

			// Período de Atendimento
			if(dataAtendimentoInicial != null && dataAtendimentoFinal != null){
				if(from.indexOf(" ra ") == -1){
					from += " LEFT JOIN os.registroAtendimento ra ";
				}

				where += " AND ra.registroAtendimento BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal) ";

				parameters.put("dataAtendimentoInicial", Util.formatarDataInicial(dataAtendimentoInicial));
				parameters.put("dataAtendimentoFinal", Util.formatarDataFinal(dataAtendimentoFinal));
			}

			// Período de Data Geracao
			if(dataGeracaoInicial != null && dataGeracaoFinal != null){

				where += " AND os.dataGeracao BETWEEN (:dataGeracaoInicial) AND (:dataGeracaoFinal) ";

				parameters.put("dataGeracaoInicial", Util.formatarDataInicial(dataGeracaoInicial));
				parameters.put("dataGeracaoFinal", Util.formatarDataFinal(dataGeracaoFinal));
			}

			// Período de Data Encerramento
			if(dataEncerramentoInicial != null && dataEncerramentoFinal != null){

				where += " AND os.dataEncerramento BETWEEN (:dataEncerramentoInicial) AND (:dataEncerramentoFinal) ";

				parameters.put("dataEncerramentoInicial", Util.formatarDataInicial(dataEncerramentoInicial));
				parameters.put("dataEncerramentoFinal", Util.formatarDataFinal(dataEncerramentoFinal));
			}

			// Período de Data Execução
			if(dataExecucaoInicial != null && dataExecucaoFinal != null){

				where += " AND os.dataExecucao BETWEEN (:dataExecucaoInicial) AND (:dataExecucaoFinal) ";

				parameters.put("dataExecucaoInicial", Util.formatarDataInicial(dataExecucaoInicial));
				parameters.put("dataExecucaoFinal", Util.formatarDataFinal(dataExecucaoFinal));
			}

			// [SB0006] - Selecionar Ordem de Servico por Município
			if(municipio != null){

				if(from.indexOf(" ra ") == -1){
					from += " LEFT JOIN os.registroAtendimento ra ";
				}
				if(from.indexOf(" imovel ") == -1){
					from += " LEFT JOIN os.imovel imovel ";
				}
				if(from.indexOf(" barea ") == -1){
					from += " LEFT JOIN ra.bairroArea barea ";
				}
				if(from.indexOf(" bareabair ") == -1){
					from += " LEFT JOIN barea.bairro bareabair ";
				}
				if(from.indexOf(" imovlogrbair ") == -1){
					from += " LEFT JOIN imovel.logradouroBairro imovlogrbair ";
				}
				if(from.indexOf(" imovbair ") == -1){
					from += " LEFT JOIN imovlogrbair.bairro imovbair ";
				}
				if(from.indexOf(" logrbair ") == -1){
					from += " LEFT JOIN ra.logradouroBairro logrbair ";
				}
				if(from.indexOf(" bair ") == -1){
					from += " LEFT JOIN logrbair.bairro bair ";
				}

				// 1.1.3 Município por Bairro Área
				where += " AND (bareabair.municipio.id = :municipioId "

				// 1.1.2 Município por Imóvel
								+ " OR imovbair.municipio.id = :municipioId "

								// 1.1.1 Município por Logradouro Bairro
								+ " OR bair.municipio.id = :municipioId )";

				parameters.put("municipioId", new Integer(municipio));
			}

			// [SB0007] - Selecionar Ordem de Servico por Bairro
			if(bairro != null && !bairro.equals("")){

				if(from.indexOf(" ra ") == -1){
					from += " LEFT JOIN os.registroAtendimento ra ";
				}
				if(from.indexOf(" imovel ") == -1){
					from += " LEFT JOIN os.imovel imovel ";
				}
				if(from.indexOf(" barea ") == -1){
					from += " LEFT JOIN ra.bairroArea barea ";
				}
				if(from.indexOf(" bareabair ") == -1){
					from += " LEFT JOIN barea.bairro bareabair ";
				}
				if(from.indexOf(" imovlogrbair ") == -1){
					from += " LEFT JOIN imovel.logradouroBairro imovlogrbair ";
				}
				if(from.indexOf(" logrbair ") == -1){
					from += " LEFT JOIN ra.logradouroBairro logrbair ";
				}
				if(from.indexOf(" bair ") == -1){
					from += " LEFT JOIN logrbair.bairro bair ";
				}

				// 1.1.3 Bairro por Bairro Área
				where += " AND (bareabair.id = :bairroId "

				// 1.1.2 Bairro por Imóvel
								+ " OR imovlogrbair.bairro.id = :bairroId "

								// 1.1.1 Bairro por Logradouro Bairro
								+ " OR bair.id = :bairroId) ";

				parameters.put("bairroId", new Integer(bairro));

				if(areaBairro != null){

					where += " AND ra.bairroArea.id = :bairroAreaId ";
					parameters.put("bairroAreaId", areaBairro);
				}
			}

			// [SB0008] - Selecionar Ordem de Servico por Logradouro
			if(logradouro != null && !logradouro.equals("")){

				if(from.indexOf(" ra ") == -1){
					from += " LEFT JOIN os.registroAtendimento ra ";
				}
				if(from.indexOf(" imovel ") == -1){
					from += " LEFT JOIN os.imovel imovel ";
				}
				if(from.indexOf(" imovlogrbair ") == -1){
					from += " LEFT JOIN imovel.logradouroBairro imovlogrbair ";
				}
				if(from.indexOf(" logrbair ") == -1){
					from += " LEFT JOIN ra.logradouroBairro logrbair ";
				}

				// 1.1.2 Logradouro por Imóvel
				where += " AND (imovlogrbair.logradouro.id = :logradouroId "

				// 1.1.1 Logradouro por Logradouro Bairro
								+ " OR logrbair.logradouro.id = :logradouroId) ";

				parameters.put("logradouroId", new Integer(logradouro));
			}

			query = session.createQuery(select + from + where);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer){
					Integer collection = (Integer) parameters.get(key);
					query.setInteger(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = ((Number) query.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdServicoTipoDaOS(Integer idOS) throws ErroRepositorioException{

		Integer retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "SELECT servicoTipo.id " + "FROM OrdemServico os " + "INNER JOIN os.servicoTipo servicoTipo "
							+ "WHERE os.id = :idOS ";

			retornoConsulta = (Integer) session.createQuery(consulta).setInteger("idOS", idOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Raphael Rossiter
	 * @date 25/01/2007
	 * @param idOS
	 * @return fiscalizacaoSituacao
	 * @throws ControladorException
	 */
	public FiscalizacaoSituacao pesquisarFiscalizacaoSituacaoPorOS(Integer idOS) throws ErroRepositorioException{

		FiscalizacaoSituacao retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "SELECT fzst " + "FROM OrdemServico os " + "INNER JOIN os.fiscalizacaoSituacao fzst "
							+ "WHERE os.id = :idOS ";

			retornoConsulta = (FiscalizacaoSituacao) session.createQuery(consulta).setInteger("idOS", idOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	/**
	 * Consulta a ordem de servico pelo id
	 * 
	 * @author Sávio Luiz
	 * @date 03/01/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico pesquisarDadosServicoTipoPrioridade(Integer idServicoTipo) throws ErroRepositorioException{

		OrdemServico ordemServico = null;

		Integer retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT ").append("servicoTipoPrioridade.id ")
							// 0
							.append("FROM ServicoTipo servTipo ").append("LEFT JOIN servTipo.servicoTipoPrioridade servicoTipoPrioridade ")
							.append("WHERE servTipo.id = :idServicoTipo ");

			retornoConsulta = (Integer) session.createQuery(consulta.toString()).setInteger("idServicoTipo", idServicoTipo)
							.setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){

				ordemServico = new OrdemServico();

				ServicoTipo servicoTipo = new ServicoTipo();
				ServicoTipoPrioridade servicoTipoPrioridade = new ServicoTipoPrioridade();
				servicoTipoPrioridade.setId(retornoConsulta);
				servicoTipo.setServicoTipoPrioridade(servicoTipoPrioridade);

				ordemServico.setServicoTipo(servicoTipo);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return ordemServico;
	}

	/**
	 * Atualiza os imoveis da OS que refere a RA passada como parametro
	 * 
	 * @author Sávio Luiz
	 * @date 03/01/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void atualizarOsDaRA(Integer idRA, Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String sql = "Update OrdemServico " + " set imov_id = :idImovel, orse_tmultimaalteracao = :data" + " where rgat_id = :idRA";

			session.createQuery(sql).setInteger("idRA", idRA).setInteger("idImovel", idImovel).setTimestamp("data", new Date())
							.executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0455] Exibir Calendário para Elaboração ou Acompanhamento de Roteiro
	 * 
	 * @author Raphael Rossiter
	 * @date 14/02/2007
	 * @param idProgramacaoRoteiro
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaOSProgramacao(Integer idProgramacaoRoteiro) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "SELECT COUNT(*) FROM OrdemServicoProgramacao ospg "
							+ "WHERE ospg.programacaoRoteiro.id = :idProgramacaoRoteiro AND ospg.indicadorAtivo = :ativo ";

			retorno = ((Number) session.createQuery(consulta).setInteger("idProgramacaoRoteiro", idProgramacaoRoteiro)
							.setShort("ativo", OrdemServicoProgramacao.INDICADOR_ATIVO).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa as Os do serviço tipo especifico com RA
	 * 
	 * @author Sávio Luiz
	 * @date 23/02/2007
	 * @param idProgramacaoRoteiro
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOSComServicoTipo(Integer idServicoTipo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "select orse_id as idOS " + "from ordem_servico "
							+ "where orse_cdsituacao = :situacaoOS and  rgat_id is not null and svtp_id = :idServicoTipo";

			retorno = session.createSQLQuery(consulta).addScalar("idOs", Hibernate.INTEGER).setInteger("idServicoTipo", idServicoTipo)
							.setShort("situacaoOS", OrdemServico.SITUACAO_PENDENTE).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa as Os do serviço tipo especifico com RA
	 * 
	 * @author Sávio Luiz
	 * @date 23/02/2007
	 * @param idProgramacaoRoteiro
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void atualizarColecaoOS(Collection colecaoIdsOS) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "update  gcom.atendimentopublico.ordemservico.OrdemServico "
							+ "set  orse_cdsituacao = :situacaoOS,amen_id = :idAtendimentoMotivoEncerramento,"
							+ "orse_tmencerramento = :dataAtual, orse_tmexecucao = :dataAtual, orse_tmultimaalteracao = :dataAtual where orse_id in (:idsOS)";

			session.createQuery(consulta).setShort("situacaoOS", OrdemServico.SITUACAO_ENCERRADO)
							.setInteger("idAtendimentoMotivoEncerramento", AtendimentoMotivoEncerramento.CONCLUSAO_SERVICO)
							.setParameterList("idsOS", colecaoIdsOS).setTimestamp("dataAtual", new Date()).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Pesquisar data e equipe da programação da ordem serviço
	 * 
	 * @author Ana Maria
	 * @date 09/03/2007
	 */
	public OrdemServicoProgramacao pesquisarDataEquipeOSProgramacao(Integer idOs) throws ErroRepositorioException{

		OrdemServicoProgramacao ordemServicoProgramacao = null;
		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "select pgrt.dataRoteiro, eqpe.nome " + "from OrdemServicoProgramacao ospg "
							+ "inner join ospg.ordemServico orse " + "inner join ospg.programacaoRoteiro pgrt "
							+ "inner join ospg.equipe eqpe " + "where orse.id = :idOs " + // and
							// ospg.situacaoFechamento
							// =
							// :situacao";
							"order by ospg.id desc";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idOs", idOs)
			// .setShort("situacao", OrdemServicoProgramacao.SITUACAO_FECHAMENTO)
							.setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){

				ordemServicoProgramacao = new OrdemServicoProgramacao();

				ProgramacaoRoteiro programacaoRoteiro = new ProgramacaoRoteiro();
				programacaoRoteiro.setDataRoteiro((Date) retornoConsulta[0]);

				ordemServicoProgramacao.setProgramacaoRoteiro(programacaoRoteiro);

				Equipe equipe = new Equipe();
				equipe.setNome((String) retornoConsulta[1]);

				ordemServicoProgramacao.setEquipe(equipe);

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return ordemServicoProgramacao;
	}

	public Collection pesquisaParaCriarDebitosNaoGerados() throws ErroRepositorioException{

		// TODO Auto-generated method stub
		return null;
	}

	public Collection pesquisaParaCriarDebitosCategoriaNaoGerados() throws ErroRepositorioException{

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 25/04/2007
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarServicoTipoOperacao(Integer idServicoTipo, Integer idOperacao) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "SELECT stop.comp_id.idOperacao " + "FROM ServicoTipoOperacao stop "
							+ "WHERE stop.comp_id.idServicoTipo = :idServicoTipo and " + "stop.comp_id.idOperacao = :idOperacao";

			retorno = (Integer) session.createQuery(consulta).setInteger("idServicoTipo", idServicoTipo)
							.setInteger("idOperacao", idOperacao).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0711] Filtro para Emissao de Ordens Seletivas
	 * 
	 * @author Ivan Sérgio
	 * @date 08/11/2007
	 * @author eduardo henrique
	 * @date 25/11/2008
	 *       Alteração na construção do HQL , para compatibilização com o Oracle.
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 * @date 03/03/2009
	 *       Alteraçao no SQL: montar filtros de acordo com os parametros solicitados.
	 *       Correçao de falhas.
	 * @author Andre Nishimura
	 * @date 01/06/2009
	 *       Alteração para passar parametros de datas via setDate do hibernate. Alteraçao em
	 *       condição para incluir join
	 * @author Andre Nishimura
	 * @date 13/03/2010
	 *       Modificaçao na emissao de ordens de serviço seletivas para acrescentar novos parametros
	 *       de filtro
	 */
	public List filtrarImovelEmissaoOrdensSeletivas(OrdemServicoSeletivaHelper ordemSeletivaHelper, String anormalidadeHidrometro,
					Boolean substituicaoHidrometro, Boolean corte, Integer anoMesFaturamentoAtual,
					String situacaLigacaoAguaPermitidaManutencao, String situacaLigacaoAguaPermitidaCorte)
					throws ErroRepositorioException{

		List retorno = null;
		String hqlAux = "";
		boolean finaliza = false;

		Map parameters = new HashMap();

		Session session = HibernateUtil.getSession();

		Integer numeroOcorrencias = 1;

		try{
			boolean adicionarANDLigacaoAgua = false;
			String hql = "select DISTINCT " + "	imovel.id, " + "	imovel.localidade.id, " + "	imovel.setorComercial.codigo, "
							+ "	imovel.quadra.numeroQuadra, " + "	imovel.lote, " + "	imovel.subLote " + "  from Imovel imovel ";

			if(ordemSeletivaHelper.getTipoMedicao() != null
							&& ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
				hql += "	inner join imovel.ligacaoAgua ligacaoAgua ";

			}
			if((substituicaoHidrometro != null && substituicaoHidrometro)
							|| ordemSeletivaHelper.getReferenciaUltimaAfericaoAnterior() != null){

				hql += " inner  join ligacaoAgua.hidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoAgua "
								+ " inner  join hidrometroInstalacaoHistoricoAgua.hidrometro hidrometroAgua ";
				// " left  join imovel.hidrometroInstalacaoHistorico hidrometroInstalacaoHistorico "
				// +
				// " left  join hidrometroInstalacaoHistorico.hidrometro hidrometro ";
				adicionarANDLigacaoAgua = true;
			}

			// Perfil Imovel
			// if (ordemSeletivaHelper.getPerfilImovel() != null &&
			// !ordemSeletivaHelper.getPerfilImovel().equals("")) {
			// hql += "	inner join imovel.imovelPerfil imovelPerfil " ;
			// }

			// Categoria
			if(ordemSeletivaHelper.getCategoria() != null && ordemSeletivaHelper.getCategoria().length > 0){
				hql += "	inner join imovel.imovelSubcategorias imovelSubcategoria "
								+ "    inner join imovelSubcategoria.comp_id.subcategoria subcategoria "
								+ "	inner join subcategoria.categoria categoria ";
			}

			// Subcategoria
			// if(ordemSeletivaHelper.getSubCategoria() != null &&
			// !ordemSeletivaHelper.getSubCategoria().equals("")){
			// hql += "	inner join imovel.ImovelCobrancaSituacao imovelCobrancaSituacao ";
			// }

			// Coleção de Consumo Médio do Imóvel
			Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel = ordemSeletivaHelper.getColecaoConsumoMedioImovel();

			// Faixa de Consumo
			String intervaloNumeroConsumoMesInicial = ordemSeletivaHelper.getIntervaloNumeroConsumoMesInicial();
			String intervaloNumeroConsumoMesFinal = ordemSeletivaHelper.getIntervaloNumeroConsumoMesFinal();

			// Media do Imovel
			if(!Util.isVazioOrNulo(colecaoConsumoMedioImovel)
							|| (anormalidadeHidrometro != null && !anormalidadeHidrometro.equals(""))
							|| (!Util.isVazioOuBranco(intervaloNumeroConsumoMesInicial) && !Util
											.isVazioOuBranco(intervaloNumeroConsumoMesFinal))){

				if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
					hql += "	inner join ligacaoAgua.medicaoHistoricos medicaoHistorico ";
				}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
					hql += "	inner join imovel.medicaoHistoricos medicaoHistorico ";
				}
			}

			// Consumo por Economia
			if((ordemSeletivaHelper.getConsumoPorEconomia() != null && !ordemSeletivaHelper.getConsumoPorEconomia().equals(""))
							|| !Util.isVazioOrNulo(colecaoConsumoMedioImovel)){
				hql += "	inner join imovel.consumosHistoricos consumosHistorico ";
			}

			hql += " where ";

			if((!Util.isVazioOuBrancoOuZero(ordemSeletivaHelper.getServicoTipo()))
							&& (Util.converterStringParaInteger(ordemSeletivaHelper.getServicoTipo()).equals(Util
											.retonarObjetoDeColecao(ServicoTipo.INSTALACAO_HIDROMETRO)))){
				hql += " ligacaoAgua.hidrometroInstalacaoHistorico.id is null and ";
			}

			if(adicionarANDLigacaoAgua){
				hqlAux += " ligacaoAgua.hidrometroInstalacaoHistorico.id = hidrometroInstalacaoHistoricoAgua.id and ";
			}

			if(substituicaoHidrometro != null && substituicaoHidrometro){
				if(ordemSeletivaHelper.getTipoMedicao() != null){
					if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
						// hqlAux += "(imovel.ligacaoAguaSituacao.id = " +
						// LigacaoAguaSituacao.LIGADO + " or ";
						// hqlAux += "imovel.ligacaoAguaSituacao.id = " +
						// LigacaoAguaSituacao.CORTADO_A_PEDIDO + ") and ";
						// hqlAux += "hidrometroInstalacaoHistoricoAgua.id is not null and ";
						//
						// finaliza = true;

						hqlAux += "imovel.ligacaoAguaSituacao.id in( " + situacaLigacaoAguaPermitidaManutencao + ") and ";
						hqlAux += "hidrometroInstalacaoHistoricoAgua.id is not null and ";

						finaliza = true;

					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += "imovel.ligacaoEsgotoSituacao.id = " + LigacaoEsgotoSituacao.LIGADO + " and ";
						// hqlAux += "hidrometroInstalacaoHistorico.id is not null and ";

						finaliza = true;
					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += "imovel.ligacaoEsgotoSituacao.id = " + LigacaoEsgotoSituacao.LIGADO + " and ";
						// hqlAux += "hidrometroInstalacaoHistorico.id is not null and ";

						finaliza = true;
					}
				}

				// hqlAux += "imovel.hidrometroInstalacaoHistorico.dataInstalacao < '" +
				// dataInstalacaoHidrometro + "' and ";
				// finaliza = true;

				// Marca Hidrometro SUBSTITUICAO
				if(ordemSeletivaHelper.getMarca() != null && ordemSeletivaHelper.getMarca().length > 0){
					if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
						hqlAux += "(hidrometroAgua.hidrometroMarca.id in (";
						for(String object : ordemSeletivaHelper.getMarca()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += "(hidrometro.hidrometroMarca.id in (";
						for(String object : ordemSeletivaHelper.getMarca()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}
				}

				// Classe
				if(ordemSeletivaHelper.getHidrometroClasseMetrologica() != null
								&& ordemSeletivaHelper.getHidrometroClasseMetrologica().length > 0){
					if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
						hqlAux += "(hidrometroAgua.hidrometroClasseMetrologica.id in (";
						for(String object : ordemSeletivaHelper.getHidrometroClasseMetrologica()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;

					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += "(hidrometro.hidrometroClasseMetrologica.id in (";
						for(String object : ordemSeletivaHelper.getHidrometroClasseMetrologica()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}
				}

				if(adicionarANDLigacaoAgua){
					// Proteção
					if(ordemSeletivaHelper.getHidrometroProtecao() != null && ordemSeletivaHelper.getHidrometroProtecao().length > 0){
						hqlAux += "(hidrometroInstalacaoHistoricoAgua.hidrometroProtecao.id in (";
						for(String object : ordemSeletivaHelper.getHidrometroProtecao()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}

					// Local de Instalação
					if(ordemSeletivaHelper.getHidrometroLocalInstalacao() != null
									&& ordemSeletivaHelper.getHidrometroLocalInstalacao().length > 0){
						hqlAux += "(hidrometroInstalacaoHistoricoAgua.hidrometroLocalInstalacao.id in (";
						for(String object : ordemSeletivaHelper.getHidrometroLocalInstalacao()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}
				}
				// Anormalidade Hidrometro SUBSTITUICAO
				Integer anoMesFaturamentoOcorr = 1;
				if(anormalidadeHidrometro != null){

					Collection collAnoMes = new ArrayList();
					if(!anormalidadeHidrometro.equals("")){
						// ------------------------------------------------------------------------------------------------------
						// ------------------------------------------------------------------------------------------------------
						if(ordemSeletivaHelper.getNumeroOcorrenciasConsecutivas() != null
										&& !"".equals(ordemSeletivaHelper.getNumeroOcorrenciasConsecutivas())){

							numeroOcorrencias = Util.converterStringParaInteger(ordemSeletivaHelper.getNumeroOcorrenciasConsecutivas());

							hqlAux += " ligacaoAgua.id in (";
							hqlAux += " select medicaoHistorico.ligacaoAgua.id from medicaoHistorico where ";

							for(int i = numeroOcorrencias - 1; i > 0; i--){
								anoMesFaturamentoOcorr = Util.subtraiAteSeisMesesAnoMesReferencia(anoMesFaturamentoAtual, i);
								collAnoMes.add(anoMesFaturamentoOcorr);
							}
							collAnoMes.add(anoMesFaturamentoAtual);

							hqlAux += " medicaoHistorico.leituraAnormalidadeFaturamento in (" + anormalidadeHidrometro
											+ ") and  medicaoHistorico.anoMesReferencia in (";

							Iterator it = collAnoMes.iterator();
							while(it.hasNext()){
								Integer anoMes = (Integer) it.next();
								hqlAux += anoMes + ",";
							}

							// remove a ultima virgula
							hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

							hqlAux += ")  ";

							hqlAux += " GROUP BY medicaoHistorico.ligacaoAgua.id  ";
							hqlAux += " HAVING COUNT(*) =  " + numeroOcorrencias + " ) and ";

						}else{
							hqlAux += "medicaoHistorico.leituraAnormalidadeFaturamento in (" + anormalidadeHidrometro + ") and ";
							hqlAux += "medicaoHistorico.anoMesReferencia = " + anoMesFaturamentoAtual + " and ";
						}
						// ------------------------------------------------------------------------------------------------------
						// ------------------------------------------------------------------------------------------------------

						finaliza = true;
					}

				}

				// Última Aferição anterior a
				if(ordemSeletivaHelper.getReferenciaUltimaAfericaoAnterior() != null && adicionarANDLigacaoAgua){

					String anoMesUltimaAfericao = Util.formatarMesAnoParaAnoMes(ordemSeletivaHelper
									.getReferenciaUltimaAfericaoAnterior());

					Date dataUltimaAfericaoAnterior = Util.converterAnoMesParaDataInicial(anoMesUltimaAfericao);
					parameters.put("dataUltimaAfericaoAnterior", dataUltimaAfericaoAnterior);

					hqlAux += " (hidrometroAgua.dataUltimaRevisao is null or hidrometroAgua.dataUltimaRevisao < :dataUltimaAfericaoAnterior) and ";

					finaliza = true;
				}

				// Dados do Hidrômetro
				Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro = ordemSeletivaHelper.getColecaoDadosDoHidrometro();

				if(!Util.isVazioOrNulo(colecaoDadosDoHidrometro)){
					String hqlCondicaoDadosDoHidrometro = "(";

					int sequencial = 0;

					for(DadosDoHidrometroHelper obj : colecaoDadosDoHidrometro){
						hqlCondicaoDadosDoHidrometro += "(";

						// Diâmetro
						Integer idHidrometroDiametro = obj.getIdHidrometroDiametro();

						if(!Util.isVazioOuBranco(idHidrometroDiametro) && idHidrometroDiametro != ConstantesSistema.NUMERO_NAO_INFORMADO){
							if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
								hqlCondicaoDadosDoHidrometro += "hidrometroAgua.hidrometroDiametro.id = ";
							}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
								hqlCondicaoDadosDoHidrometro += "hidrometro.hidrometroDiametro.id = ";
							}

							hqlCondicaoDadosDoHidrometro += idHidrometroDiametro + " and ";
						}

						// Capacidade
						Integer idHidrometroCapacidade = obj.getIdHidrometroCapacidade();

						if(!Util.isVazioOuBranco(idHidrometroCapacidade)
										&& idHidrometroCapacidade != ConstantesSistema.NUMERO_NAO_INFORMADO){
							if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
								hqlCondicaoDadosDoHidrometro += "hidrometroAgua.hidrometroCapacidade.id = ";
							}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
								hqlCondicaoDadosDoHidrometro += "hidrometro.hidrometroCapacidade.id = ";
							}

							hqlCondicaoDadosDoHidrometro += idHidrometroCapacidade + " and ";
						}

						// Período de Instalação
						String mesAnoDataInstalacaoInicial = obj.getIntervaloInstalacaoInicial();
						String mesAnoDataInstalacaoFinal = obj.getIntervaloInstalacaoFinal();

						if(adicionarANDLigacaoAgua && !Util.isVazioOuBranco(mesAnoDataInstalacaoInicial)
										&& !Util.isVazioOuBranco(mesAnoDataInstalacaoFinal)){
							String anoMesDataInstalacaoInicial = Util.formatarMesAnoParaAnoMes(mesAnoDataInstalacaoInicial);
							String anoMesDataInstalacaoFinal = Util.formatarMesAnoParaAnoMes(mesAnoDataInstalacaoFinal);

							Date dataInstalacaoInicial = Util.converterAnoMesParaDataInicial(anoMesDataInstalacaoInicial);
							parameters.put("dataInstalacaoInicial" + sequencial, dataInstalacaoInicial);

							Date dataInstalacaoFinal = Util.converterAnoMesParaDataFinal(anoMesDataInstalacaoFinal);
							parameters.put("dataInstalacaoFinal" + sequencial, dataInstalacaoFinal);

							hqlCondicaoDadosDoHidrometro += " hidrometroInstalacaoHistoricoAgua.dataInstalacao between :dataInstalacaoInicial"
											+ sequencial + " and :dataInstalacaoFinal" + sequencial + " and ";
						}

						// Leitura Acumulada
						Integer numeroLeituraAcumulada = obj.getNumeroLeituraAcumulada();

						if(!Util.isVazioOuBranco(numeroLeituraAcumulada)){
							if(!Util.isVazioOuBranco(numeroLeituraAcumulada)){
								if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
									hqlCondicaoDadosDoHidrometro += "hidrometroAgua.numeroLeituraAcumulada = ";
								}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
									hqlCondicaoDadosDoHidrometro += "hidrometro.numeroLeituraAcumulada = ";
								}

								hqlCondicaoDadosDoHidrometro += numeroLeituraAcumulada + " and ";
							}
						}

						// Tirar último 'and'
						hqlCondicaoDadosDoHidrometro = hqlCondicaoDadosDoHidrometro.substring(0, hqlCondicaoDadosDoHidrometro.length() - 5)
										+ ") or ";

						sequencial = sequencial + 1;
					}

					// Tirar último 'or'
					hqlAux += hqlCondicaoDadosDoHidrometro.substring(0, hqlCondicaoDadosDoHidrometro.length() - 4) + ") and ";

					finaliza = true;

				}
			}else if(corte){
				hqlAux += "imovel.ligacaoAguaSituacao.id in( " + situacaLigacaoAguaPermitidaCorte + ") and ";

				finaliza = true;
			}


			// Condição de Medicao Tipo
			if(ordemSeletivaHelper.getTipoMedicao() != null){
				if(ordemSeletivaHelper.getConsumoPorEconomia() != null && !ordemSeletivaHelper.getConsumoPorEconomia().equals("")){
					if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
						hqlAux += " consumosHistorico.ligacaoTipo.id = " + LigacaoTipo.LIGACAO_AGUA + " and ";
					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += " consumosHistorico.ligacaoTipo.id = " + LigacaoTipo.LIGACAO_ESGOTO + " and ";
					}
					// Referência Faturamento dos Consumos
					hqlAux += " consumosHistorico.referenciaFaturamento = (select anoMesFaturamento from SistemaParametro) " + " and ";
				}
			}
			// Elo
			if(ordemSeletivaHelper.getElo() != null & !ordemSeletivaHelper.getElo().equals("")){
				hqlAux += "imovel.localidade.localidade = " + ordemSeletivaHelper.getElo() + " and ";
				finaliza = true;
			}

			// Grupo de Faturamento
			String faturamentoGrupo = ordemSeletivaHelper.getFaturamentoGrupo();

			if(!Util.isVazioOuBranco(faturamentoGrupo)){
				hqlAux += " imovel.rota.faturamentoGrupo.id = " + faturamentoGrupo + " and ";
				finaliza = true;
			}

			// Regional
			String gerenciaRegional = ordemSeletivaHelper.getGerenciaRegional();

			if(!Util.isVazioOuBranco(gerenciaRegional)){
				hqlAux += " imovel.localidade.gerenciaRegional.id = " + gerenciaRegional + " and ";
				finaliza = true;
			}

			// Localidade
			if(ordemSeletivaHelper.getLocalidade() != null && !(ordemSeletivaHelper.getLocalidade().length == 0)){
				// hqlAux += "(localidade.id between " + localidadeInicial + " and " +
				// localidadeFinal + ") and ";
				if(!ordemSeletivaHelper.getLocalidade()[0].equals("-1")){
					hqlAux += "(imovel.localidade.id in( ";
					for(String object : ordemSeletivaHelper.getLocalidade()){
						hqlAux += object + ",";
					}

					// remove a ultima virgula
					hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

					hqlAux += ")) and ";
					finaliza = true;
				}
			}
			// Setor Comercial
			if(ordemSeletivaHelper.getSetor() != null && !(ordemSeletivaHelper.getSetor().length == 0)){
				// hqlAux += "(setorComercial.id between " + setorComercialInicial + " and " +
				// setorComercialFinal + ") and ";
				hqlAux += "(imovel.setorComercial.id in ( ";
				for(String object : ordemSeletivaHelper.getSetor()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}
			// Quadra
			if(ordemSeletivaHelper.getQuadra() != null && !(ordemSeletivaHelper.getQuadra().length == 0)){
				// hqlAux += "(quadra.id between " + quadraInicial + " and " + quadraFinal +
				// ") and ";
				hqlAux += "(imovel.quadra.id in ( ";
				for(String object : ordemSeletivaHelper.getQuadra()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}
			// Rota
			if(ordemSeletivaHelper.getRota() != null && !(ordemSeletivaHelper.getRota().length == 0)){
				hqlAux += "(imovel.rota.id in (";
				for(String object : ordemSeletivaHelper.getRota()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Lote
			String[] lote = ordemSeletivaHelper.getLote();

			if(!Util.isVazioOrNulo(lote)){
				hqlAux += "imovel.lote in (";

				for(String object : lote){
					hqlAux += object + ",";
				}

				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ") and ";
				finaliza = true;
			}

			// Bairro
			if(ordemSeletivaHelper.getBairro() != null && ordemSeletivaHelper.getBairro().length != 0){
				hqlAux += "(imovel.logradouroBairro.bairro.id in (";
				for(String object : ordemSeletivaHelper.getBairro()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Logradouro
			if(ordemSeletivaHelper.getLogradouro() != null && ordemSeletivaHelper.getLogradouro().length != 0){
				hqlAux += "(imovel.logradouroBairro.logradouro.id in (";
				for(String object : ordemSeletivaHelper.getLogradouro()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Perfil Imovel
			if(ordemSeletivaHelper.getPerfilImovel() != null && ordemSeletivaHelper.getPerfilImovel().length != 0){
				hqlAux += "(imovel.imovelPerfil.id in (";
				for(String object : ordemSeletivaHelper.getPerfilImovel()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Categoria
			if(ordemSeletivaHelper.getCategoria() != null && ordemSeletivaHelper.getCategoria().length != 0){
				hqlAux += "(categoria.id in (";
				for(String object : ordemSeletivaHelper.getCategoria()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Subcategoria
			if(ordemSeletivaHelper.getSubCategoria() != null && ordemSeletivaHelper.getSubCategoria().length != 0){
				hqlAux += "(subcategoria.id in (";
				for(String object : ordemSeletivaHelper.getSubCategoria()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Situação da Ligação de Água
			if(ordemSeletivaHelper.getLigacaoAguaSituacao() != null && ordemSeletivaHelper.getLigacaoAguaSituacao().length != 0){
				hqlAux += "(imovel.ligacaoAguaSituacao.id in (";
				for(String object : ordemSeletivaHelper.getLigacaoAguaSituacao()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Situação da Ligação de Esgoto
			if(ordemSeletivaHelper.getLigacaoEsgotoSituacao() != null && ordemSeletivaHelper.getLigacaoEsgotoSituacao().length != 0){
				hqlAux += "(imovel.ligacaoEsgotoSituacao.id in (";
				for(String object : ordemSeletivaHelper.getLigacaoEsgotoSituacao()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Cortando no Período
			String anoMesDataCorteInicial = ordemSeletivaHelper.getIntervaloDataCorteInicial();
			String anoMesDataCorteFinal = ordemSeletivaHelper.getIntervaloDataCorteFinal();

			String corteInicial = ordemSeletivaHelper.getDataCorteInicial();
			String corteFinal = ordemSeletivaHelper.getDataCorteFinal();

			if(MedicaoTipo.LIGACAO_AGUA.toString().equals(ordemSeletivaHelper.getTipoMedicao())
							&& !Util.isVazioOuBranco(anoMesDataCorteInicial) && !Util.isVazioOuBranco(anoMesDataCorteFinal)){

				Date dataCorteInicial = Util.converterAnoMesParaDataInicial(anoMesDataCorteInicial);
				parameters.put("dataCorteInicial", dataCorteInicial);

				Date dataCorteFinal = Util.converterAnoMesParaDataFinal(anoMesDataCorteFinal);
				parameters.put("dataCorteFinal", dataCorteFinal);

				hqlAux += "(ligacaoAgua.dataCorte between :dataCorteInicial and :dataCorteFinal) and ";
				finaliza = true;
			}

			if(MedicaoTipo.LIGACAO_AGUA.toString().equals(ordemSeletivaHelper.getTipoMedicao())
 && !Util.isVazioOuBranco(corteInicial)
							&& !Util.isVazioOuBranco(corteFinal)){
				Date dataInicioCorte = Util.converterStringParaData(corteInicial);
				parameters.put("dataInicioCorte", dataInicioCorte);

				Date dataFinalCorte = Util.converterStringParaData(corteFinal);
				parameters.put("dataFinalCorte", dataFinalCorte);

				hqlAux += "(ligacaoAgua.dataCorte between :dataInicioCorte and :dataFinalCorte) and ";
				finaliza = true;
			}

			// Quantidade de Economias
			if(ordemSeletivaHelper.getIntervaloQuantidadeEconomiasInicial() != null
							&& !ordemSeletivaHelper.getIntervaloQuantidadeEconomiasInicial().equals("")
							&& ordemSeletivaHelper.getIntervaloQuantidadeEconomiasFinal() != null
							&& !ordemSeletivaHelper.getIntervaloQuantidadeEconomiasFinal().equals("")){
				hqlAux += "(imovel.quantidadeEconomias between " + ordemSeletivaHelper.getIntervaloQuantidadeEconomiasInicial() + " and "
								+ ordemSeletivaHelper.getIntervaloQuantidadeEconomiasFinal() + ")) and ";
				finaliza = true;
			}

			// Intervalo de Pontos de Utilização
			String intervaloNumeroPontosUtilizacaoInicial = ordemSeletivaHelper.getIntervaloNumeroPontosUtilizacaoInicial();
			String intervaloNumeroPontosUtilizacaoFinal = ordemSeletivaHelper.getIntervaloNumeroPontosUtilizacaoFinal();

			if(!Util.isVazioOuBranco(intervaloNumeroPontosUtilizacaoInicial) && !Util.isVazioOuBranco(intervaloNumeroPontosUtilizacaoFinal)){
				hqlAux += "(imovel.numeroPontosUtilizacao between " + intervaloNumeroPontosUtilizacaoInicial + " and "
								+ intervaloNumeroPontosUtilizacaoFinal + ") and ";
				finaliza = true;
			}

			// Numero Moradores
			if(ordemSeletivaHelper.getIntervaloNumeroMoradoresInicial() != null
							&& !ordemSeletivaHelper.getIntervaloNumeroMoradoresInicial().equals("")
							&& ordemSeletivaHelper.getIntervaloNumeroMoradoresFinal() != null
							&& !ordemSeletivaHelper.getIntervaloNumeroMoradoresFinal().equals("")){
				hqlAux += "(imovel.numeroMorador between " + ordemSeletivaHelper.getIntervaloNumeroMoradoresInicial() + " and "
								+ ordemSeletivaHelper.getIntervaloNumeroMoradoresFinal() + ")) and ";
				finaliza = true;
			}
			// Area Construida
			if(ordemSeletivaHelper.getIntervaloAreaConstruidaInicial() != null
							&& !ordemSeletivaHelper.getIntervaloAreaConstruidaInicial().equals("")
							&& ordemSeletivaHelper.getIntervaloAreaConstruidaFinal() != null
							&& !ordemSeletivaHelper.getIntervaloAreaConstruidaFinal().equals("")){
				hqlAux += "(imovel.areaConstruida between " + ordemSeletivaHelper.getIntervaloAreaConstruidaInicial() + " and "
								+ ordemSeletivaHelper.getIntervaloAreaConstruidaFinal() + ")) and ";
				finaliza = true;
			}
			// Imovel Condominio
			if(ordemSeletivaHelper.getImovelCondominio().equals("1")){
				hqlAux += "imovel.indicadorImovelCondominio = " + Imovel.IMOVEL_CONDOMINIO + " and ";
				finaliza = true;
			}
			// Consumo por Economia
			if(ordemSeletivaHelper.getConsumoPorEconomia() != null && !ordemSeletivaHelper.getConsumoPorEconomia().equals("")){
				hqlAux += " (coalesce(consumosHistorico.numeroConsumoFaturadoMes, 1) / " + " coalesce(imovel.quantidadeEconomias, 1)) <= "
								+ ordemSeletivaHelper.getConsumoPorEconomia() + " and ";
				finaliza = true;
			}

			// Faixa de Consumo
			if(!Util.isVazioOuBranco(intervaloNumeroConsumoMesInicial) && !Util.isVazioOuBranco(intervaloNumeroConsumoMesFinal)){
				hqlAux += "(medicaoHistorico.numeroConsumoMes between " + intervaloNumeroConsumoMesInicial + " and "
								+ intervaloNumeroConsumoMesFinal + ") and ";
				finaliza = true;
			}

			// Consumo Médio do Imóvel
			if(!Util.isVazioOrNulo(colecaoConsumoMedioImovel)){
				String hqlCondicaoConsumoMedioImovel = "(";

				for(ConsumoMedioImovelHelper obj : colecaoConsumoMedioImovel){
					Integer consumoMedioInicial = obj.getConsumoMedioInicial();
					Integer consumoMedioFinal = obj.getConsumoMedioFinal();

					if(!Util.isVazioOuBranco(consumoMedioInicial) && !Util.isVazioOuBranco(consumoMedioFinal)){
						hqlCondicaoConsumoMedioImovel += "(consumosHistorico.consumoMedio between " + consumoMedioInicial + " and "
										+ consumoMedioFinal + ") or ";
					}
				}

				// Tirar último 'or'
				hqlAux += hqlCondicaoConsumoMedioImovel.substring(0, hqlCondicaoConsumoMedioImovel.length() - 4) + ") and ";

				finaliza = true;
			}

			// Intervalo de quantidade de débitos
			String intervaloQuantidadeContasVencidasInicial = ordemSeletivaHelper.getIntervaloQuantidadeContasVencidasInicial();
			String intervaloQuantidadeContasVencidasFinal = ordemSeletivaHelper.getIntervaloQuantidadeContasVencidasFinal();

			// Valor do Débito
			String valorTotalDebitoVencido = ordemSeletivaHelper.getValorTotalDebitoVencido();

			if((!Util.isVazioOuBranco(intervaloQuantidadeContasVencidasInicial) && !Util
							.isVazioOuBranco(intervaloQuantidadeContasVencidasFinal)) || !Util.isVazioOuBranco(valorTotalDebitoVencido)){
				StringBuffer consultaConta = new StringBuffer();
				consultaConta.append("select count(*) ");
				consultaConta.append("from Conta cnta ");
				consultaConta.append("inner join cnta.debitoCreditoSituacaoAtual dcst ");
				consultaConta.append("where cnta.imovel = imovel.id ");
				consultaConta.append("  and cnta.debitoCreditoSituacaoAtual.id in (" + DebitoCreditoSituacao.NORMAL + ","
								+ DebitoCreditoSituacao.RETIFICADA + "," + DebitoCreditoSituacao.INCLUIDA + ","
								+ DebitoCreditoSituacao.PARCELADA + ")");
				consultaConta.append("  and cnta.dataVencimentoConta < :dataAtual ");
				consultaConta.append("  and not exists (select pgt.id ");
				consultaConta.append("                  from Pagamento pgt");
				consultaConta.append("                  where pgt.conta = cnta.id) ");
				consultaConta.append("group by cnta.imovel ");
				consultaConta.append("having ");

				if(!Util.isVazioOuBranco(intervaloQuantidadeContasVencidasInicial)
								&& !Util.isVazioOuBranco(intervaloQuantidadeContasVencidasFinal)){
					consultaConta.append("count(*) between " + intervaloQuantidadeContasVencidasInicial + " and "
									+ intervaloQuantidadeContasVencidasFinal + " and ");
				}

				if(!Util.isVazioOuBranco(valorTotalDebitoVencido)){
					consultaConta.append("sum(cnta.valorAgua + cnta.valorEsgoto + cnta.debitos - cnta.valorCreditos - cnta.valorImposto) >= "
									+ valorTotalDebitoVencido + " and ");
				}

				parameters.put("dataAtual", new Date());

				// Removendo último 'and'
				hqlAux += "exists (" + consultaConta.substring(0, consultaConta.length() - 5) + ") and ";
				finaliza = true;
			}

			// Finaliza o HQL
			if(finaliza){
				hql += hqlAux;
				hql = hql.substring(0, hql.length() - 5);
			}else{
				hql = hql.substring(0, hql.length() - 7);
			}

			// Adiciona o Order By de acordo com a incricao do imovel
			hql += " order by " + " imovel.localidade.id, " + " imovel.setorComercial.codigo, " + " imovel.quadra.numeroQuadra, "
							+ " imovel.lote, " + " imovel.subLote ";

			Query query = session.createQuery(hql.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){
				String key = (String) iterMap.next();

				if(parameters.get(key) instanceof Integer){
					Integer parametroInteger = (Integer) parameters.get(key);
					query.setParameter(key, parametroInteger);
				}else if(parameters.get(key) instanceof Date){
					Date parametroDate = (Date) parameters.get(key);
					query.setParameter(key, parametroDate);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0732] Gerar Relatorio Acompanhamento de Oredem de Servico de Hidrometro
	 * 
	 * @author Ivan Sérgio
	 * @date 13/12/2007
	 * @param tipoOrdem
	 * @param situacaoOrdem
	 * @param idLocalidade
	 * @param dataEncerramentoInicial
	 * @param dataEncerramentoFinal
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOrdemServicoGerarRelatorioAcompanhamentoHidrometro(String idEmpresa, String tipoOrdem, Short situacaoOrdem,
					String idLocalidade, String dataEncerramentoInicial, String dataEncerramentoFinal, String tipoRelatorio)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String hql = null;

		

		try{
			

			if(Util.converterStringParaInteger(tipoRelatorio).equals(
							RelatorioResumoOrdensServicoEncerradasPendentes.TIPO_RELATORIO_ANALITICO)
							|| situacaoOrdem.equals(OrdemServico.SITUACAO_PENDENTE)){
				hql = "select " + "	os.id, " + "	imovel.id, " + "	localidade.id, " + "	setorComercial.codigo, " + "	quadra.numeroQuadra, "
								+ "	imovel.lote, " + "	imovel.subLote, " + "	os.dataGeracao, " + "	os.dataEncerramento, "
								+ "	motivo.descricao ";

				hql = hql + "from " + "	gcom.atendimentopublico.ordemservico.OrdemServico os "
								+ " 	inner join os.ordemServicoUnidades ordemServicoUnidade "
								+ " 	inner join ordemServicoUnidade.unidadeOrganizacional unidadeOrganizacional "
								+ " 	inner join os.imovel imovel " + " 	inner join imovel.localidade localidade "
								+ " 	inner join imovel.setorComercial setorComercial " + " 	inner join imovel.quadra quadra "
								+ " 	left  join os.atendimentoMotivoEncerramento motivo " + "where "
								+ "	os.registroAtendimento.id is null " + "	and os.cobrancaDocumento.id is null "
								+ "	and unidadeOrganizacional.empresa.id = " + idEmpresa + " " + "	and os.servicoTipo.id in ( " + tipoOrdem
								+ ") ";

				if(situacaoOrdem.equals(OrdemServico.SITUACAO_ENCERRADO)){
					hql += "	and	os.situacao = " + OrdemServico.SITUACAO_ENCERRADO + " ";
				}else if(situacaoOrdem.equals(OrdemServico.SITUACAO_PENDENTE)){
					hql += "	and	(os.situacao = " + OrdemServico.SITUACAO_PENDENTE + " or " + "	os.situacao = "
									+ OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO + ") ";
				}

				if(idLocalidade != null && !idLocalidade.equals("")){
					hql += "	and localidade.id = " + idLocalidade + " ";
				}

				if(dataEncerramentoInicial != null && !dataEncerramentoInicial.equals("") && dataEncerramentoFinal != null
								&& !dataEncerramentoFinal.equals("")){
					hql += " and os.dataEncerramento between to_date('" + dataEncerramentoInicial + "', 'dd/mm/yyyy')  and to_date('"
									+ dataEncerramentoFinal + "', 'dd/mm/yyyy') ";
				}

				hql += "order by " + "	os.id";

			}else if(Util.converterStringParaInteger(tipoRelatorio).equals(
							RelatorioResumoOrdensServicoEncerradasPendentes.TIPO_RELATORIO_SINTETICO)){
				hql = "select motivo.descricao,count(motivo.descricao) ";

				hql = hql + "from " + "	gcom.atendimentopublico.ordemservico.OrdemServico os "
								+ " 	inner join os.ordemServicoUnidades ordemServicoUnidade "
								+ " 	inner join ordemServicoUnidade.unidadeOrganizacional unidadeOrganizacional "
								+ " 	inner join os.imovel imovel " + " 	inner join imovel.localidade localidade "
								+ " 	inner join imovel.setorComercial setorComercial " + " 	inner join imovel.quadra quadra "
								+ " 	left  join os.atendimentoMotivoEncerramento motivo " + "where "
								+ "	os.registroAtendimento.id is null " + "	and os.cobrancaDocumento.id is null "
								+ "	and unidadeOrganizacional.empresa.id = " + idEmpresa + " " + "	and os.servicoTipo.id in ( " + tipoOrdem
								+ ") ";

				if(situacaoOrdem.equals(OrdemServico.SITUACAO_ENCERRADO)){
					hql += "	and	os.situacao = " + OrdemServico.SITUACAO_ENCERRADO + " ";
				}else if(situacaoOrdem.equals(OrdemServico.SITUACAO_PENDENTE)){
					hql += "	and	(os.situacao = " + OrdemServico.SITUACAO_PENDENTE + " or " + "	os.situacao = "
									+ OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO + ") ";
				}

				if(idLocalidade != null && !idLocalidade.equals("")){
					hql += "	and localidade.id = " + idLocalidade + " ";
				}

				if(dataEncerramentoInicial != null && !dataEncerramentoInicial.equals("") && dataEncerramentoFinal != null
								&& !dataEncerramentoFinal.equals("")){
					hql += " and os.dataEncerramento between to_date('" + dataEncerramentoInicial + "', 'dd/mm/yyyy')  and to_date('"
									+ dataEncerramentoFinal + "', 'dd/mm/yyyy') ";
				}

				hql = hql + "group by motivo.descricao";

			}



			retorno = session.createQuery(hql).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0640] Gerar TXT Ação de Cobrança
	 * 
	 * @author Raphael Rossiter
	 * @date 04/01/2008
	 * @param idCobrancaDocumento
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarOrdemServicoPorCobrancaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{
			consulta = "SELECT orse.id " + "FROM OrdemServico orse " + "INNER JOIN orse.cobrancaDocumento cbdo "
							+ "WHERE cbdo.id = :idCobrancaDocumento ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idCobrancaDocumento", idCobrancaDocumento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método retorna uma instância de ServicoTipo com a Collection de servicosAssociados populada.
	 * 
	 * @param id
	 *            Id do Tipo de Serviço
	 * @return ServicoTipo
	 * @throws ErroRepositorioException
	 * @author eduardo henrique
	 * @date 13/05/2009
	 */
	public ServicoTipo pesquisarServicoTipoPorId(Integer id) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String consulta = " FROM ServicoTipo servicoTipo " + "LEFT JOIN FETCH servicoTipo.servicosAssociados servicosAssociados "
							+ "LEFT JOIN FETCH servicosAssociados.servicoTipoAssociado servicoTipoAssociado "
							+ "WHERE servicoTipo.id = :idServicoTipo ";

			return (ServicoTipo) session.createQuery(consulta).setInteger("idServicoTipo", id).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Método retorna uma instância de AtendimentoMotivoEncerramento
	 * 
	 * @param id
	 *            Id do Motivo de Encerramento
	 * @return AtendimentoMotivoEncerramento
	 * @throws ErroRepositorioException
	 * @author eduardo henrique
	 * @date 21/05/2009
	 */
	public AtendimentoMotivoEncerramento pesquisarAtendimentoMotivoEncerramentoPorId(Integer id) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String consulta = " FROM AtendimentoMotivoEncerramento motivoEncerramento " + " WHERE motivoEncerramento.id = :idMotivo ";

			return (AtendimentoMotivoEncerramento) session.createQuery(consulta).setInteger("idMotivo", id).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Método responsável por pesquisar os layouts de ordem de serviço.
	 * 
	 * @author Virgínia Melo
	 * @date 28/05/2009
	 * @return Coleção de OrdemServicoLayout.
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoLayout> pesquisarOrdemServicoLayouts() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(OrdemServicoLayout.class).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Recupera todos os possíveis motivos de interrupção(ativos).
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Coleção com todos os motivos de interrupcao ativos.
	 */
	public Collection<MotivoInterrupcao> pesquisarMotivoInterrupcao() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(MotivoInterrupcao.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Recupera todos os locais de ocorrência;
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Coleção com todos os locais de ocorrencia ativos.
	 */
	public Collection<LocalOcorrencia> pesquisarLocalOcorrencia() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(LocalOcorrencia.class).add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO))
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Recupera um local de ocorrência;
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Local Ocorrência
	 */
	public LocalOcorrencia pesquisarLocalOcorrencia(Integer idLocalOcorrencia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		LocalOcorrencia retorno = null;

		try{

			Criteria criteria = session.createCriteria(LocalOcorrencia.class).add(Expression.eq("id", idLocalOcorrencia))
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO));

			List<LocalOcorrencia> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Recupera uma coleção de PavimentoRua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Coleção de Pavimento Rua.
	 */
	public Collection<PavimentoRua> pesquisarPavimentoRua() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(PavimentoRua.class).add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO))
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de PavimentoCalcada ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Coleção de Pavimento Calcada.
	 */
	public Collection<PavimentoCalcada> pesquisarPavimentoCalcada() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(PavimentoCalcada.class).add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO))
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de CausaVazamento ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Causa Vazamento
	 */
	public Collection<CausaVazamento> pesquisarCausaVazamento() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(CausaVazamento.class).add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO))
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de Agente Externo ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Agente Externo
	 */
	public Collection<AgenteExterno> pesquisarAgenteExterno() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(AgenteExterno.class).add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO))
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de Diametro Rede Agua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Rede Água
	 */
	public Collection<DiametroRedeAgua> pesquisarDiametroRedeAgua() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(DiametroRedeAgua.class).add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO))
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de Diametro Ramal Agua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Ramal Água
	 */
	public Collection<DiametroRamalAgua> pesquisarDiametroRamalAgua() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(DiametroRamalAgua.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de Diametro Rede Esgoto ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Rede Esgoto
	 */
	public Collection<DiametroRedeEsgoto> pesquisarDiametroRedeEsgoto() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(DiametroRedeEsgoto.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de Diametro Ramal Esgoto ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Ramal Esgoto
	 */
	public Collection<DiametroRamalEsgoto> pesquisarDiametroRamalEsgoto() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(DiametroRamalEsgoto.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de MaterialRedeAgua
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRedeAgua
	 */
	public Collection<MaterialRedeAgua> pesquisarMaterialRedeAgua() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(MaterialRedeAgua.class).add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO))
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de MaterialRamalAgua
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRamalAgua
	 */
	public Collection<MaterialRamalAgua> pesquisarMaterialRamalAgua() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(MaterialRamalAgua.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de MaterialRedeEsgoto
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRedeEsgoto
	 */
	public Collection<MaterialRedeEsgoto> pesquisarMaterialRedeEsgoto() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(MaterialRedeEsgoto.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de MaterialRamalEsgoto
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRamalEsgoto
	 */
	public Collection<MaterialRamalEsgoto> pesquisarMaterialRamalEsgoto() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(MaterialRamalEsgoto.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera um PavimentoRua.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Rua
	 */
	public PavimentoRua pesquisarPavimentoRua(Integer idPavimento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		PavimentoRua retorno = null;

		try{

			Criteria criteria = session.createCriteria(PavimentoRua.class).add(Expression.eq("id", idPavimento))
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO));

			List<PavimentoRua> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Recupera um PavimentoCalcada.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Calcada
	 */
	public PavimentoCalcada pesquisarPavimentoCalcada(Integer idPavimento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		PavimentoCalcada retorno = null;

		try{

			Criteria criteria = session.createCriteria(PavimentoCalcada.class).add(Expression.eq("id", idPavimento))
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO));

			List<PavimentoCalcada> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisar ordem de serviço.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Ordem Servico
	 */
	public OrdemServico pesquisarOrdemServico(Integer idOrdemServico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		OrdemServico retorno = null;

		try{

			Criteria criteria = session.createCriteria(OrdemServico.class).add(Expression.eq("id", idOrdemServico));

			List<OrdemServico> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Recupera uma coleção de OrdemServicoValaPavimento a partir da Ordem de Serviço.
	 * 
	 * @author Virgínia Melo
	 * @date 22/06/2009
	 * @return Coleção de OrdemServicoValaPavimento
	 */
	public Collection<OrdemServicoValaPavimento> pesquisarOrdemServicoValaPavimento(Integer idOrdemServico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(OrdemServicoValaPavimento.class).add(Expression.eq("ordemServico.id", idOrdemServico))
							.setFetchMode("localOcorrencia", FetchMode.JOIN).setFetchMode("pavimentoRua", FetchMode.JOIN)
							.setFetchMode("pavimentoCalcada", FetchMode.JOIN).setFetchMode("entulhoMedida", FetchMode.JOIN).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Recupera uma OrdemServicoProgramacao
	 * 
	 * @author Virgínia Melo
	 * @date 25/06/2009
	 * @return OrdemServicoProgramacao
	 */
	public OrdemServicoProgramacao pesquisarOrdemServicoProgramacao(Integer idProgramacaoRoteiro, Integer idOrdemServico, Integer idEquipe)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		OrdemServicoProgramacao retorno = null;

		try{

			Criteria criteria = session.createCriteria(OrdemServicoProgramacao.class)
							.add(Expression.eq("programacaoRoteiro.id", idProgramacaoRoteiro))
							.add(Expression.eq("ordemServico.id", idOrdemServico)).add(Expression.eq("equipe.id", idEquipe));

			List<OrdemServicoProgramacao> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Recupera uma OrdemServicoDeslocamento através do id da OS Programação.
	 * 
	 * @author Virgínia Melo
	 * @date 25/06/2009
	 * @return OrdemServicoDeslocamento
	 */
	public OrdemServicoDeslocamento pesquisarOrdemServicoDeslocamento(Integer idOsProgramacao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		OrdemServicoDeslocamento retorno = null;

		try{

			Criteria criteria = session.createCriteria(OrdemServicoDeslocamento.class).add(
							Expression.eq("ordemServicoProgramacao.id", idOsProgramacao));

			List<OrdemServicoDeslocamento> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Recupera uma coleção de OSInterrupcaoDeslocamento a partir da OS Programação
	 * 
	 * @author Virgínia Melo
	 * @date 22/06/2009
	 * @return Coleção de OSInterrupcaoDeslocamento
	 */
	public Collection<OrdemServicoInterrupcaoDeslocamento> pesquisarOSInterrupcaoDeslocamento(Integer idOsProgramacao)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(OrdemServicoInterrupcaoDeslocamento.class)
							.add(Expression.eq("ordemServicoProgramacao.id", idOsProgramacao))
							.setFetchMode("motivoInterrupcao", FetchMode.JOIN).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Recupera uma coleção de OSInterrupcaoExecucao a partir da OS Programação
	 * 
	 * @author Virgínia Melo
	 * @date 30/06/2009
	 * @return Coleção de OSInterrupcaoExecucao
	 */
	public Collection<OrdemServicoInterrupcaoExecucao> pesquisarOSInterrupcaoExecucao(Integer idOsProgramacao)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(OrdemServicoInterrupcaoExecucao.class)
							.add(Expression.eq("ordemServicoProgramacao.id", idOsProgramacao))
							.setFetchMode("motivoInterrupcao", FetchMode.JOIN).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Método responsável por retornar a unidade organizacional da OS
	 * 
	 * @author Virgínia Melo
	 * @date 12/06/2009
	 */
	public OrdemServicoUnidade pesquisarOrdemServicoUnidade(Integer idOS) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		OrdemServicoUnidade retorno = null;

		try{

			Criteria criteria = session.createCriteria(OrdemServicoUnidade.class).add(Expression.eq("ordemServico.id", idOS))
							.setFetchMode("unidadeOrganizacional", FetchMode.JOIN);

			List<OrdemServicoUnidade> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Lista uma colecao de OrdemServicoFotoOcorrencia pesquisando por uma
	 * OrdemServicoFotoOcorrencia
	 * 
	 * @param OrdemServicoFotoOcorrencia
	 * @return Collection<OrdemServicoFotoOcorrencia>
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoFotoOcorrencia> listarOSFoto(OrdemServicoFotoOcorrencia osFoto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			Criteria criteria = session.createCriteria(OrdemServicoFotoOcorrencia.class);

			if(osFoto.getId() != null){
				criteria.add(Restrictions.eq("id", osFoto.getId()));
			}
			if(osFoto.getIdOrdemServico() != null){
				criteria.add(Restrictions.eq("idOrdemServico", osFoto.getIdOrdemServico()));
			}
			if(osFoto.getIdOrdemServicoProgramacao() != null){
				criteria.add(Restrictions.eq("idOrdemServicoProgramacao", osFoto.getIdOrdemServicoProgramacao()));
			}
			if(osFoto.getNumeroSequenciaFoto() != null){
				criteria.add(Restrictions.eq("numeroSequenciaFoto", osFoto.getNumeroSequenciaFoto()));
			}

			return criteria.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Pesquisa o último número sequencial das fotos, pelo id da ordem de servico
	 * 
	 * @param idOrdemServico
	 * @return numeroSequencia
	 * @throws ControladorException
	 */
	public Integer pesquisarQuantidadeFotosOrdemServico(Integer idOrdemServico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = 0;
		try{
			Criteria criteria = session.createCriteria(OrdemServicoFotoOcorrencia.class);
			criteria.add(Restrictions.eq("idOrdemServico", idOrdemServico));
			criteria.add(Restrictions.isNull("idOrdemServicoProgramacao"));
			criteria.addOrder(Order.desc("numeroSequenciaFoto"));
			Collection<Object> osFotos = (Collection) criteria.list();
			if(osFotos != null && !osFotos.isEmpty()){
				OrdemServicoFotoOcorrencia osfoto = (OrdemServicoFotoOcorrencia) osFotos.iterator().next();
				retorno = osfoto.getNumeroSequenciaFoto();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		if(retorno == null){
			retorno = 0;
		}
		return retorno;
	}

	/**
	 * Pesquisa o último número sequencial das fotos, pelo id da ordem de servico e o id da ordem
	 * servico programacao
	 * 
	 * @param idOrdemServico
	 * @param idOrdemServicoProgramacao
	 * @return numeroSequencia
	 * @throws ControladorException
	 */
	public Integer pesquisarQuantidadeFotosOrdemServicoProgramacao(Integer idOrdemServico, Integer idOrdemServicoProgramacao)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = 0;
		try{
			Criteria criteria = session.createCriteria(OrdemServicoFotoOcorrencia.class);
			criteria.add(Restrictions.eq("idOrdemServico", idOrdemServico));
			criteria.add(Restrictions.eq("idOrdemServicoProgramacao", idOrdemServicoProgramacao));
			criteria.addOrder(Order.desc("numeroSequenciaFoto"));
			Collection<Object> osFotos = (Collection) criteria.list();
			if(osFotos != null && !osFotos.isEmpty()){
				OrdemServicoFotoOcorrencia osfoto = (OrdemServicoFotoOcorrencia) osFotos.iterator().next();
				retorno = osfoto.getNumeroSequenciaFoto();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		if(retorno == null){
			retorno = 0;
		}
		return retorno;
	}

	/**
     *
	 */
	public Collection<ServicoTipo> pesquisarServicoTipoPorRegistroAtendimento(Integer idRegistroAtendimento, Integer idServicoTipo)
					throws ErroRepositorioException{

		Collection<ServicoTipo> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT DISTINCT svtp " + "FROM OrdemServico os " + "INNER JOIN os.servicoTipo svtp  "
							+ "INNER JOIN os.registroAtendimento ragt  " + "WHERE " + " ragt.id = " + idRegistroAtendimento
							+ " and svtp.id = " + idServicoTipo + "ORDER BY svtp.descricao";

			retornoConsulta = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	public Empresa recuperarAgenteOS(OrdemServico ordemServico){

		Session session = HibernateUtil.getSession();
		Empresa empresa = null;
		try{
			String hql = "select os.agente from OrdemServico os where os.id = :idOS";
			Object result = session.createQuery(hql).setLong("idOS", ordemServico.getId()).uniqueResult();
			if(result != null){
				empresa = (Empresa) result;
				Hibernate.initialize(empresa);
			}
		}catch(Exception ex){

		}finally{
			session.close();
		}

		return empresa;
	}

	/**
	 * Recupera uma coleção de Diametro Cavalete Agua ativos.
	 * 
	 * @author Yara Souza
	 * @date 17/08/2010
	 * @return Coleção de Diametro Rede Água
	 */
	public Collection<DiametroCavaleteAgua> pesquisarDiametroCavaleteAgua() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(DiametroCavaleteAgua.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Recupera uma coleção de Material Cavalete Agua ativos.
	 * 
	 * @author Yara Souza
	 * @date 17/08/2010
	 * @return Coleção de Material Cavalete Água
	 */
	public Collection<MaterialCavaleteAgua> pesquisarMaterialCavaleteAgua() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(MaterialCavaleteAgua.class)
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO)).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * @author isilva
	 * @param idOrdemServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RoteiroOSDadosProgramacaoHelper> pesquisarProgramacaoOrdemServicoRoteiroEquipe(Integer idOrdemServico)
					throws ErroRepositorioException{

		Collection<RoteiroOSDadosProgramacaoHelper> collectionRoteiroOSDadosProgramacaoHelpers = new ArrayList<RoteiroOSDadosProgramacaoHelper>();

		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer();
			hql.append("select ");
			hql.append("osp.id, osp.programacaoRoteiro.dataRoteiro, osp.equipe.id, osp.equipe.nome, ospnem.descricao, osp.programacaoRoteiro.unidadeOrganizacional.id, osp.programacaoRoteiro.unidadeOrganizacional.descricao, osp.usuarioProgramacao.id, osp.usuarioProgramacao.nomeUsuario ");
			hql.append("from OrdemServicoProgramacao osp ");
			hql.append("left join osp.osProgramNaoEncerMotivo ospnem ");
			hql.append("where ");
			hql.append("osp.ordemServico.id = :idOrdemServico ");
			hql.append("order by osp.programacaoRoteiro.dataRoteiro asc");

			Collection<Object[]> retornoConsulta = (Collection<Object[]>) session.createQuery(hql.toString())
							.setInteger("idOrdemServico", idOrdemServico).list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){

				for(Object[] programacao : retornoConsulta){

					RoteiroOSDadosProgramacaoHelper roteiroOSDadosProgramacaoHelper = new RoteiroOSDadosProgramacaoHelper();

					if(programacao[0] != null){
						roteiroOSDadosProgramacaoHelper.setIdOrdemServicoProgramacao((Integer) programacao[0]);
					}

					if(programacao[1] != null){
						roteiroOSDadosProgramacaoHelper.setDataRoteiroProgramacao(Util.formatarData((Date) programacao[1]));
					}

					if(programacao[2] != null){
						roteiroOSDadosProgramacaoHelper.setIdEquipe((Integer) programacao[2]);
					}

					if(programacao[3] != null){
						roteiroOSDadosProgramacaoHelper.setNomeEquipe((String) programacao[3]);
					}

					if(programacao[4] != null){
						roteiroOSDadosProgramacaoHelper.setDescricaoProgramNaoEncerMotivo((String) programacao[4]);
					}

					if(programacao[5] != null){
						roteiroOSDadosProgramacaoHelper.setIdUnidadeOrganizacional((Integer) programacao[5]);
					}

					if(programacao[6] != null){
						roteiroOSDadosProgramacaoHelper.setDescricaoUnidadeOrganizacional((String) programacao[6]);
					}

					if(programacao[7] != null){
						roteiroOSDadosProgramacaoHelper.setIdUsuarioProgramacao((Integer) programacao[7]);
					}

					if(programacao[8] != null){
						roteiroOSDadosProgramacaoHelper.setNomeUsuarioProgramacao((String) programacao[8]);
					}

					collectionRoteiroOSDadosProgramacaoHelpers.add(roteiroOSDadosProgramacaoHelper);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		if(collectionRoteiroOSDadosProgramacaoHelpers.isEmpty()){
			return null;
		}

		return collectionRoteiroOSDadosProgramacaoHelpers;
	}

	/**
	 * @author isilva
	 * @param idOrdemServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarEquipeEDataProgramacaoOrdemServico(Integer idOrdemServico) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = new ArrayList<Object[]>();

		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer();
			hql.append("select ");
			hql.append("ospr.programacaoRoteiro.dataRoteiro, ospr.equipe.id, ospr.equipe.nome ");
			hql.append("from OrdemServicoProgramacao ospr ");
			hql.append("where ");
			hql.append("ospr.ordemServico.id = :idOrdemServico and ");
			hql.append("ospr.programacaoRoteiro.id = (select max(osp.programacaoRoteiro.id) from OrdemServicoProgramacao osp where osp.ordemServico.id = :idOrdemServico ) ");
			hql.append("order by ospr.programacaoRoteiro.id desc");

			retornoConsulta = (Collection<Object[]>) session.createQuery(hql.toString()).setInteger("idOrdemServico", idOrdemServico)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * Retorna o maior Sequêncial da Ordem de Serviço Programação, de uma Data para uma Equipe.
	 * 
	 * @param idEquipe
	 * @param dtRoteiro
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(Integer idEquipe, Date dtRoteiro) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		Query query = null;

		StringBuffer hql = new StringBuffer();
		Map parameters = new HashMap();

		try{

			hql.append("select max(osp.nnSequencialProgramacao) ");
			hql.append("from OrdemServicoProgramacao osp ");
			hql.append("where ");
			hql.append("osp.programacaoRoteiro.dataRoteiro = :dtRoteiro and ");
			hql.append("osp.equipe.id = :idEquipe");

			Date dataRoteiro = Util.formatarDataSemHora(dtRoteiro);

			parameters.put("dtRoteiro", dataRoteiro);
			parameters.put("idEquipe", idEquipe);

			query = session.createQuery(hql.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Integer){
					Integer parametroInteger = (Integer) parameters.get(key);
					query.setParameter(key, parametroInteger);
				}else if(parameters.get(key) instanceof Date){
					Date parametroDate = (Date) parameters.get(key);
					query.setParameter(key, parametroDate);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			Number retornoShort = ((Number) query.setMaxResults(1).uniqueResult());

			if(retornoShort != null){
				retorno = retornoShort.intValue();
			}

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Verifica se já existe Ordem de Servico de
	 * Instalacao/Substituicao de Hidrometro para o Imovel
	 * 
	 * @author Ivan Sérgio
	 * @date 19/11/2007
	 *       Alteraçao na consulta SQL. Formataçao da data incorreta
	 * @author André Nishimura
	 * @date 19/05/2009
	 */
	public Collection verificarOrdemServicoInstalacaoSubstituicaoImovel(Integer idImovel, String dataVencimento, Integer idServicoTipo)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer hql = new StringBuffer("select ");
			hql.append(" os.imovel.id ");
			hql.append(" from ");
			hql.append(" OrdemServico os ");
			hql.append(" where ");
			hql.append(" os.servicoTipo.id = :idServicoTipo ");
			hql.append(" and os.dataEncerramento is null ");
			// "	and os.registroAtendimento.id is null " +
			// "	and os.cobrancaDocumento.id is null " +
			// "   and os.dataEmissao >= :dataVencimento " +
			hql.append(" and os.imovel.id = :idImovel");

			// retorno = (Integer) session.createQuery(hql)
			// // .setInteger("servicoInstalacao",ServicoTipo.TIPO_EFETUAR_INSTALACAO_HIDROMETRO)
			// //
			// .setInteger("servicoSubstituicao",ServicoTipo.TIPO_EFETUAR_SUBSTITUICAO_HIDROMETRO)
			// .setInteger("idServicoTipo",idServicoTipo)
			// .setDate("dataVencimento",
			// Util.converteStringParaDate(Util.formatarData(dataVencimento)))
			// .setInteger("idImovel", idImovel)
			// .setMaxResults(1).uniqueResult();

			retorno = (Collection) session.createQuery(hql.toString()).setInteger("idServicoTipo", idServicoTipo)
			// .setDate("dataVencimento",
			// Util.converteStringParaDate(Util.formatarData(dataVencimento)))
							.setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0711] Filtro para Emissao de Ordens Seletivas
	 * 
	 * @author Anderson Lima
	 * @date 13/03/2010
	 *       Pesquisar o total de registros a serem processados
	 */
	public Integer filtrarImovelEmissaoOrdensSeletivasCount(OrdemServicoSeletivaHelper ordemSeletivaHelper, String anormalidadeHidrometro,
					Boolean substituicaoHidrometro, Boolean corte, Integer anoMesFaturamentoAtual,
					String situacaLigacaoAguaPermitidaManutencao, String situacaLigacaoAguaPermitidaCorte)
					throws ErroRepositorioException{

		Integer retorno = 0;
		String hqlAux = "";
		boolean finaliza = false;

		Map parameters = new HashMap();

		Session session = HibernateUtil.getSession();

		Integer numeroOcorrencias = 1;

		try{
			boolean adicionarANDLigacaoAgua = false;
			String hql = "select COUNT " + "	(DISTINCT imovel.id) " + "  from Imovel imovel ";

			if(ordemSeletivaHelper.getTipoMedicao() != null
							&& ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
				hql += "	inner join imovel.ligacaoAgua ligacaoAgua ";

			}
			if((substituicaoHidrometro != null && substituicaoHidrometro)
							|| ordemSeletivaHelper.getReferenciaUltimaAfericaoAnterior() != null){
				hql += " inner  join ligacaoAgua.hidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoAgua "
								+ " inner  join hidrometroInstalacaoHistoricoAgua.hidrometro hidrometroAgua ";
				// " left  join imovel.hidrometroInstalacaoHistorico hidrometroInstalacaoHistorico "
				// +
				// " left  join hidrometroInstalacaoHistorico.hidrometro hidrometro ";
				adicionarANDLigacaoAgua = true;
			}

			// Perfil Imovel
			if(ordemSeletivaHelper.getPerfilImovel() != null && !ordemSeletivaHelper.getPerfilImovel().equals("")){
				hql += "	inner join imovel.imovelPerfil imovelPerfil ";
			}

			// Categoria
			if(ordemSeletivaHelper.getCategoria() != null && !ordemSeletivaHelper.getCategoria().equals("")){
				hql += "	inner join imovel.imovelSubcategorias imovelSubcategoria "
								+ "    inner join imovelSubcategoria.comp_id.subcategoria subcategoria "
								+ "	inner join subcategoria.categoria categoria ";
			}
			/*
			 * // Subcategoria
			 * if (subCategoria != null && subCategoria.equals("")) {
			 * hql += "	inner join imovelSubcategoria.comp_id.subcategoria subcategoria " ;
			 * }
			 */

			// Coleção de Consumo Médio do Imóvel
			Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel = ordemSeletivaHelper.getColecaoConsumoMedioImovel();

			// Faixa de Consumo
			String intervaloNumeroConsumoMesInicial = ordemSeletivaHelper.getIntervaloNumeroConsumoMesInicial();
			String intervaloNumeroConsumoMesFinal = ordemSeletivaHelper.getIntervaloNumeroConsumoMesFinal();

			// Media do Imovel
			if(!Util.isVazioOrNulo(colecaoConsumoMedioImovel)
							|| (anormalidadeHidrometro != null && !anormalidadeHidrometro.equals(""))
							|| (!Util.isVazioOuBranco(intervaloNumeroConsumoMesInicial) && !Util
											.isVazioOuBranco(intervaloNumeroConsumoMesFinal))){

				if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
					hql += "	inner join ligacaoAgua.medicaoHistoricos medicaoHistorico ";
				}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
					hql += "	inner join imovel.medicaoHistoricos medicaoHistorico ";
				}
			}

			// Consumo por Economia
			if((ordemSeletivaHelper.getConsumoPorEconomia() != null && !ordemSeletivaHelper.getConsumoPorEconomia().equals(""))
							|| !Util.isVazioOrNulo(colecaoConsumoMedioImovel)){
				hql += "	inner join imovel.consumosHistoricos consumosHistorico ";
			}

			hql += " where ";

			if((!Util.isVazioOuBrancoOuZero(ordemSeletivaHelper.getServicoTipo()))
							&& (Util.converterStringParaInteger(ordemSeletivaHelper.getServicoTipo()).equals(Util
											.retonarObjetoDeColecao(ServicoTipo.INSTALACAO_HIDROMETRO)))){

				if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
					hql += " ligacaoAgua.hidrometroInstalacaoHistorico.id is null and ";
				}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
					hql += " imovel.hidrometroInstalacaoHistorico.id is null and ";
				}

			}

			if(adicionarANDLigacaoAgua){
				hqlAux += " ligacaoAgua.hidrometroInstalacaoHistorico.id = hidrometroInstalacaoHistoricoAgua.id and ";
			}

			if(substituicaoHidrometro != null && substituicaoHidrometro){
				if(ordemSeletivaHelper.getTipoMedicao() != null){
					if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){

						hqlAux += "imovel.ligacaoAguaSituacao.id in( " + situacaLigacaoAguaPermitidaManutencao + ") and ";
						hqlAux += "hidrometroInstalacaoHistoricoAgua.id is not null and ";

						finaliza = true;

					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += "imovel.ligacaoEsgotoSituacao.id = " + LigacaoEsgotoSituacao.LIGADO + " and ";
						// hqlAux += "hidrometroInstalacaoHistorico.id is not null and ";

						finaliza = true;
					}
				}

				// hqlAux += "imovel.hidrometroInstalacaoHistorico.dataInstalacao < '" +
				// dataInstalacaoHidrometro + "' and ";
				// finaliza = true;

				// Marca Hidrometro SUBSTITUICAO
				if(ordemSeletivaHelper.getMarca() != null){
					if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
						hqlAux += "hidrometroAgua.hidrometroMarca.id = " + ordemSeletivaHelper.getMarca() + " and ";
						finaliza = true;
					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += "hidrometro.hidrometroMarca.id = " + ordemSeletivaHelper.getMarca() + " and ";
						finaliza = true;
					}
				}

				// Classe
				if(ordemSeletivaHelper.getHidrometroClasseMetrologica() != null
								&& ordemSeletivaHelper.getHidrometroClasseMetrologica().length > 0){
					if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
						hqlAux += "(hidrometroAgua.hidrometroClasseMetrologica.id in( ";
						for(String object : ordemSeletivaHelper.getHidrometroClasseMetrologica()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += "(hidrometro.hidrometroClasseMetrologica.id in( ";
						for(String object : ordemSeletivaHelper.getHidrometroClasseMetrologica()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}
				}

				if(adicionarANDLigacaoAgua){
					// Proteção
					if(ordemSeletivaHelper.getHidrometroProtecao() != null && ordemSeletivaHelper.getHidrometroProtecao().length > 0){
						hqlAux += "(hidrometroInstalacaoHistoricoAgua.hidrometroProtecao.id in( ";
						for(String object : ordemSeletivaHelper.getHidrometroProtecao()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}

					// Local de Instalação
					if(ordemSeletivaHelper.getHidrometroLocalInstalacao() != null
									&& ordemSeletivaHelper.getHidrometroLocalInstalacao().length > 0){
						hqlAux += "(hidrometroInstalacaoHistoricoAgua.hidrometroLocalInstalacao.id in( ";
						for(String object : ordemSeletivaHelper.getHidrometroLocalInstalacao()){
							hqlAux += object + ",";
						}

						// remove a ultima virgula
						hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

						hqlAux += ")) and ";
						finaliza = true;
					}

				}

				// Anormalidade Hidrometro SUBSTITUICAO
				Integer anoMesFaturamentoOcorr = 1;
				if(anormalidadeHidrometro != null){

					Collection collAnoMes = new ArrayList();
					if(!anormalidadeHidrometro.equals("")){
						// ------------------------------------------------------------------------------------------------------
						// ------------------------------------------------------------------------------------------------------
						if(ordemSeletivaHelper.getNumeroOcorrenciasConsecutivas() != null
										&& !"".equals(ordemSeletivaHelper.getNumeroOcorrenciasConsecutivas())){

							numeroOcorrencias = Util.converterStringParaInteger(ordemSeletivaHelper.getNumeroOcorrenciasConsecutivas());

							hqlAux += " ligacaoAgua.id in (";
							hqlAux += " select medicaoHistorico.ligacaoAgua.id from medicaoHistorico where ";

							for(int i = numeroOcorrencias - 1; i > 0; i--){
								anoMesFaturamentoOcorr = Util.subtraiAteSeisMesesAnoMesReferencia(anoMesFaturamentoAtual, i);
								collAnoMes.add(anoMesFaturamentoOcorr);
							}
							collAnoMes.add(anoMesFaturamentoAtual);

							hqlAux += " medicaoHistorico.leituraAnormalidadeFaturamento in (" + anormalidadeHidrometro
											+ ") and  medicaoHistorico.anoMesReferencia in (";

							Iterator it = collAnoMes.iterator();
							while(it.hasNext()){
								Integer anoMes = (Integer) it.next();
								hqlAux += anoMes + ",";
							}

							// remove a ultima virgula
							hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

							hqlAux += ")  ";

							hqlAux += " GROUP BY medicaoHistorico.ligacaoAgua.id  ";
							hqlAux += " HAVING COUNT(*) =  " + numeroOcorrencias + " ) and ";

						}else{
							hqlAux += "medicaoHistorico.leituraAnormalidadeFaturamento in (" + anormalidadeHidrometro + ") and ";
						}
						// ------------------------------------------------------------------------------------------------------
						// ------------------------------------------------------------------------------------------------------

						finaliza = true;
					}

				}

				// Última Aferição anterior a
				if(ordemSeletivaHelper.getReferenciaUltimaAfericaoAnterior() != null && adicionarANDLigacaoAgua){

					String anoMesUltimaAfericao = Util.formatarMesAnoParaAnoMes(ordemSeletivaHelper.getReferenciaUltimaAfericaoAnterior());

					Date dataUltimaAfericaoAnterior = Util.converterAnoMesParaDataInicial(anoMesUltimaAfericao);
					parameters.put("dataUltimaAfericaoAnterior", dataUltimaAfericaoAnterior);

					hqlAux += " (hidrometroAgua.dataUltimaRevisao is null or hidrometroAgua.dataUltimaRevisao < :dataUltimaAfericaoAnterior) and ";

					finaliza = true;
				}

				// Dados do Hidrômetro
				Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro = ordemSeletivaHelper.getColecaoDadosDoHidrometro();

				if(!Util.isVazioOrNulo(colecaoDadosDoHidrometro)){
					String hqlCondicaoDadosDoHidrometro = "(";

					int sequencial = 0;

					for(DadosDoHidrometroHelper obj : colecaoDadosDoHidrometro){
						hqlCondicaoDadosDoHidrometro += "(";

						// Diâmetro
						Integer idHidrometroDiametro = obj.getIdHidrometroDiametro();

						if(!Util.isVazioOuBranco(idHidrometroDiametro) && idHidrometroDiametro != ConstantesSistema.NUMERO_NAO_INFORMADO){
							if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
								hqlCondicaoDadosDoHidrometro += "hidrometroAgua.hidrometroDiametro.id = ";
							}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
								hqlCondicaoDadosDoHidrometro += "hidrometro.hidrometroDiametro.id = ";
							}

							hqlCondicaoDadosDoHidrometro += idHidrometroDiametro + " and ";
						}

						// Capacidade
						Integer idHidrometroCapacidade = obj.getIdHidrometroCapacidade();

						if(!Util.isVazioOuBranco(idHidrometroCapacidade)
										&& idHidrometroCapacidade != ConstantesSistema.NUMERO_NAO_INFORMADO){
							if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
								hqlCondicaoDadosDoHidrometro += "hidrometroAgua.hidrometroCapacidade.id = ";
							}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
								hqlCondicaoDadosDoHidrometro += "hidrometro.hidrometroCapacidade.id = ";
							}

							hqlCondicaoDadosDoHidrometro += idHidrometroCapacidade + " and ";
						}

						// Período de Instalação
						String mesAnoDataInstalacaoInicial = obj.getIntervaloInstalacaoInicial();
						String mesAnoDataInstalacaoFinal = obj.getIntervaloInstalacaoFinal();

						if(adicionarANDLigacaoAgua && !Util.isVazioOuBranco(mesAnoDataInstalacaoInicial)
										&& !Util.isVazioOuBranco(mesAnoDataInstalacaoFinal)){
							String anoMesDataInstalacaoInicial = Util.formatarMesAnoParaAnoMes(mesAnoDataInstalacaoInicial);
							String anoMesDataInstalacaoFinal = Util.formatarMesAnoParaAnoMes(mesAnoDataInstalacaoFinal);

							Date dataInstalacaoInicial = Util.converterAnoMesParaDataInicial(anoMesDataInstalacaoInicial);
							parameters.put("dataInstalacaoInicial" + sequencial, dataInstalacaoInicial);

							Date dataInstalacaoFinal = Util.converterAnoMesParaDataFinal(anoMesDataInstalacaoFinal);
							parameters.put("dataInstalacaoFinal" + sequencial, dataInstalacaoFinal);

							hqlCondicaoDadosDoHidrometro += " hidrometroInstalacaoHistoricoAgua.dataInstalacao between :dataInstalacaoInicial"
											+ sequencial + " and :dataInstalacaoFinal" + sequencial + " and ";
						}

						// Leitura Acumulada
						Integer numeroLeituraAcumulada = obj.getNumeroLeituraAcumulada();

						if(!Util.isVazioOuBranco(numeroLeituraAcumulada)){
							if(!Util.isVazioOuBranco(numeroLeituraAcumulada)){
								if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
									hqlCondicaoDadosDoHidrometro += "hidrometroAgua.numeroLeituraAcumulada = ";
								}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
									hqlCondicaoDadosDoHidrometro += "hidrometro.numeroLeituraAcumulada = ";
								}

								hqlCondicaoDadosDoHidrometro += numeroLeituraAcumulada + " and ";
							}
						}

						// Tirar último 'and'
						hqlCondicaoDadosDoHidrometro = hqlCondicaoDadosDoHidrometro.substring(0, hqlCondicaoDadosDoHidrometro.length() - 5)
										+ ") or ";

						sequencial = sequencial + 1;
					}

					// Tirar último 'or'
					hqlAux += hqlCondicaoDadosDoHidrometro.substring(0, hqlCondicaoDadosDoHidrometro.length() - 4) + ") and ";

					finaliza = true;
				}
			}else if(corte){
				hqlAux += "imovel.ligacaoAguaSituacao.id in( " + situacaLigacaoAguaPermitidaCorte + ") and ";

				finaliza = true;
			}

			// Condição de Medicao Tipo
			if(ordemSeletivaHelper.getTipoMedicao() != null){
				if(ordemSeletivaHelper.getConsumoPorEconomia() != null && !ordemSeletivaHelper.getConsumoPorEconomia().equals("")){
					if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.LIGACAO_AGUA.toString())){
						hqlAux += " consumosHistorico.ligacaoTipo.id = " + LigacaoTipo.LIGACAO_AGUA + " and ";
					}else if(ordemSeletivaHelper.getTipoMedicao().equals(MedicaoTipo.POCO.toString())){
						hqlAux += " consumosHistorico.ligacaoTipo.id = " + LigacaoTipo.LIGACAO_ESGOTO + " and ";
					}
					// Referência Faturamento dos Consumos
					hqlAux += " consumosHistorico.referenciaFaturamento = (select anoMesFaturamento from SistemaParametro) " + " and ";
				}
			}
			// Elo
			if(ordemSeletivaHelper.getElo() != null & !ordemSeletivaHelper.getElo().equals("")){
				hqlAux += "imovel.localidade.localidade = " + ordemSeletivaHelper.getElo() + " and ";
				finaliza = true;
			}

			// Grupo de Faturamento
			String faturamentoGrupo = ordemSeletivaHelper.getFaturamentoGrupo();

			if(!Util.isVazioOuBranco(faturamentoGrupo)){
				hqlAux += " imovel.rota.faturamentoGrupo.id = " + faturamentoGrupo + " and ";
				finaliza = true;
			}

			// Regional
			String gerenciaRegional = ordemSeletivaHelper.getGerenciaRegional();

			if(!Util.isVazioOuBranco(gerenciaRegional)){
				hqlAux += " imovel.localidade.gerenciaRegional.id = " + gerenciaRegional + " and ";
				finaliza = true;
			}

			// Localidade
			if(ordemSeletivaHelper.getLocalidade() != null && !(ordemSeletivaHelper.getLocalidade().length == 0)){
				// hqlAux += "(localidade.id between " + localidadeInicial + " and " +
				// localidadeFinal + ") and ";
				hqlAux += "(imovel.localidade.id in( ";
				for(String object : ordemSeletivaHelper.getLocalidade()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}
			// Setor Comercial
			if(ordemSeletivaHelper.getSetor() != null && !(ordemSeletivaHelper.getSetor().length == 0)){
				// hqlAux += "(setorComercial.id between " + setorComercialInicial + " and " +
				// setorComercialFinal + ") and ";
				hqlAux += "(imovel.setorComercial.id in ( ";
				for(String object : ordemSeletivaHelper.getSetor()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}
			// Quadra
			if(ordemSeletivaHelper.getQuadra() != null && !(ordemSeletivaHelper.getQuadra().length == 0)){
				// hqlAux += "(quadra.id between " + quadraInicial + " and " + quadraFinal +
				// ") and ";
				hqlAux += "(imovel.quadra.id in ( ";
				for(String object : ordemSeletivaHelper.getQuadra()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}
			// Rota
			if(ordemSeletivaHelper.getRota() != null && !(ordemSeletivaHelper.getRota().length == 0)){
				hqlAux += "(imovel.rota.id in (";
				for(String object : ordemSeletivaHelper.getRota()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Lote
			String[] lote = ordemSeletivaHelper.getLote();

			if(!Util.isVazioOrNulo(lote)){
				hqlAux += "imovel.lote in (";

				for(String object : lote){
					hqlAux += object + ",";
				}

				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ") and ";
				finaliza = true;
			}

			// Bairro
			if(ordemSeletivaHelper.getBairro() != null && ordemSeletivaHelper.getBairro().length != 0){
				hqlAux += "(imovel.logradouroBairro.bairro.id in (";
				for(String object : ordemSeletivaHelper.getBairro()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Logradouro
			if(ordemSeletivaHelper.getLogradouro() != null && ordemSeletivaHelper.getLogradouro().length != 0){
				hqlAux += "(imovel.logradouroBairro.logradouro.id in (";
				for(String object : ordemSeletivaHelper.getLogradouro()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Perfil Imovel
			if(ordemSeletivaHelper.getPerfilImovel() != null && ordemSeletivaHelper.getPerfilImovel().length != 0){
				hqlAux += "(imovelPerfil.id in (";
				for(String object : ordemSeletivaHelper.getPerfilImovel()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Categoria
			if(ordemSeletivaHelper.getCategoria() != null && ordemSeletivaHelper.getCategoria().length != 0){
				hqlAux += "(categoria.id in (";
				for(String object : ordemSeletivaHelper.getCategoria()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Subcategoria
			if(ordemSeletivaHelper.getSubCategoria() != null && ordemSeletivaHelper.getSubCategoria().length != 0){
				hqlAux += "(subcategoria.id in (";
				for(String object : ordemSeletivaHelper.getSubCategoria()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Situação da Ligação de Água
			if(ordemSeletivaHelper.getLigacaoAguaSituacao() != null && ordemSeletivaHelper.getLigacaoAguaSituacao().length != 0){
				hqlAux += "(imovel.ligacaoAguaSituacao.id in (";
				for(String object : ordemSeletivaHelper.getLigacaoAguaSituacao()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Situação da Ligação de Esgoto
			if(ordemSeletivaHelper.getLigacaoEsgotoSituacao() != null && ordemSeletivaHelper.getLigacaoEsgotoSituacao().length != 0){
				hqlAux += "(imovel.ligacaoEsgotoSituacao.id in (";
				for(String object : ordemSeletivaHelper.getLigacaoEsgotoSituacao()){
					hqlAux += object + ",";
				}

				// remove a ultima virgula
				hqlAux = hqlAux.substring(0, hqlAux.length() - 1).toString();

				hqlAux += ")) and ";
				finaliza = true;
			}

			// Cortando no Período
			String anoMesDataCorteInicial = ordemSeletivaHelper.getIntervaloDataCorteInicial();
			String anoMesDataCorteFinal = ordemSeletivaHelper.getIntervaloDataCorteFinal();

			if(MedicaoTipo.LIGACAO_AGUA.toString().equals(ordemSeletivaHelper.getTipoMedicao())
							&& !Util.isVazioOuBranco(anoMesDataCorteInicial) && !Util.isVazioOuBranco(anoMesDataCorteFinal)){
				Date dataCorteInicial = Util.converterAnoMesParaDataInicial(anoMesDataCorteInicial);
				parameters.put("dataCorteInicial", dataCorteInicial);

				Date dataCorteFinal = Util.converterAnoMesParaDataFinal(anoMesDataCorteFinal);
				parameters.put("dataCorteFinal", dataCorteFinal);

				hqlAux += "(ligacaoAgua.dataCorte between :dataCorteInicial and :dataCorteFinal) and ";
				finaliza = true;
			}

			if(MedicaoTipo.LIGACAO_AGUA.toString().equals(ordemSeletivaHelper.getTipoMedicao())
							&& !Util.isVazioOuBranco(anoMesDataCorteInicial) && !Util.isVazioOuBranco(anoMesDataCorteFinal)){
				Date corteInicial = Util.converterAnoMesParaDataInicial(anoMesDataCorteInicial);
				parameters.put("corteInicial", corteInicial);

				Date corteFinal = Util.converterAnoMesParaDataFinal(anoMesDataCorteFinal);
				parameters.put("corteFinal", corteFinal);

				hqlAux += "(ligacaoAgua.dataCorte between :corteInicial and :corteFinal) and ";
				finaliza = true;
			}

			// Quantidade de Economias
			if(ordemSeletivaHelper.getIntervaloQuantidadeEconomiasInicial() != null
							&& !ordemSeletivaHelper.getIntervaloQuantidadeEconomiasInicial().equals("")
							&& ordemSeletivaHelper.getIntervaloQuantidadeEconomiasFinal() != null
							&& !ordemSeletivaHelper.getIntervaloQuantidadeEconomiasFinal().equals("")){
				hqlAux += "(imovel.quantidadeEconomias between " + ordemSeletivaHelper.getIntervaloQuantidadeEconomiasInicial() + " and "
								+ ordemSeletivaHelper.getIntervaloQuantidadeEconomiasFinal() + ")) and ";
				finaliza = true;
			}

			// Intervalo de Pontos de Utilização
			String intervaloNumeroPontosUtilizacaoInicial = ordemSeletivaHelper.getIntervaloNumeroPontosUtilizacaoInicial();
			String intervaloNumeroPontosUtilizacaoFinal = ordemSeletivaHelper.getIntervaloNumeroPontosUtilizacaoFinal();

			if(!Util.isVazioOuBranco(intervaloNumeroPontosUtilizacaoInicial) && !Util.isVazioOuBranco(intervaloNumeroPontosUtilizacaoFinal)){
				hqlAux += "(imovel.numeroPontosUtilizacao between " + intervaloNumeroPontosUtilizacaoInicial + " and "
								+ intervaloNumeroPontosUtilizacaoFinal + ") and ";
				finaliza = true;
			}

			// Numero Moradores
			if(ordemSeletivaHelper.getIntervaloNumeroMoradoresInicial() != null
							&& !ordemSeletivaHelper.getIntervaloNumeroMoradoresInicial().equals("")
							&& ordemSeletivaHelper.getIntervaloNumeroMoradoresFinal() != null
							&& !ordemSeletivaHelper.getIntervaloNumeroMoradoresFinal().equals("")){
				hqlAux += "(imovel.numeroMorador between " + ordemSeletivaHelper.getIntervaloNumeroMoradoresInicial() + " and "
								+ ordemSeletivaHelper.getIntervaloNumeroMoradoresFinal() + ")) and ";
				finaliza = true;
			}
			// Area Construida
			if(ordemSeletivaHelper.getIntervaloAreaConstruidaInicial() != null
							&& !ordemSeletivaHelper.getIntervaloAreaConstruidaInicial().equals("")
							&& ordemSeletivaHelper.getIntervaloAreaConstruidaFinal() != null
							&& !ordemSeletivaHelper.getIntervaloAreaConstruidaFinal().equals("")){
				hqlAux += "(imovel.areaConstruida between " + ordemSeletivaHelper.getIntervaloAreaConstruidaInicial() + " and "
								+ ordemSeletivaHelper.getIntervaloAreaConstruidaFinal() + ")) and ";
				finaliza = true;
			}
			// Imovel Condominio
			if(ordemSeletivaHelper.getImovelCondominio().equals("1")){
				hqlAux += "imovel.indicadorImovelCondominio = " + Imovel.IMOVEL_CONDOMINIO + " and ";
				finaliza = true;
			}
			// Consumo por Economia
			if(ordemSeletivaHelper.getConsumoPorEconomia() != null && !ordemSeletivaHelper.getConsumoPorEconomia().equals("")){
				hqlAux += " (coalesce(consumosHistorico.numeroConsumoFaturadoMes, 1) / " + " coalesce(imovel.quantidadeEconomias, 1)) <= "
								+ ordemSeletivaHelper.getConsumoPorEconomia() + " and ";
				finaliza = true;
			}

			// Faixa de Consumo
			if(!Util.isVazioOuBranco(intervaloNumeroConsumoMesInicial) && !Util.isVazioOuBranco(intervaloNumeroConsumoMesFinal)){
				hqlAux += "(medicaoHistorico.numeroConsumoMes between " + intervaloNumeroConsumoMesInicial + " and "
								+ intervaloNumeroConsumoMesFinal + ") and ";
				finaliza = true;
			}

			// Consumo Médio do Imóvel
			if(!Util.isVazioOrNulo(colecaoConsumoMedioImovel)){
				String hqlCondicaoConsumoMedioImovel = "(";

				for(ConsumoMedioImovelHelper obj : colecaoConsumoMedioImovel){
					Integer consumoMedioInicial = obj.getConsumoMedioInicial();
					Integer consumoMedioFinal = obj.getConsumoMedioFinal();

					if(!Util.isVazioOuBranco(consumoMedioInicial) && !Util.isVazioOuBranco(consumoMedioFinal)){
						hqlCondicaoConsumoMedioImovel += "(consumosHistorico.consumoMedio between " + consumoMedioInicial + " and "
										+ consumoMedioFinal + ") or ";
					}
				}

				// Tirar último 'or'
				hqlAux += hqlCondicaoConsumoMedioImovel.substring(0, hqlCondicaoConsumoMedioImovel.length() - 4) + ") and ";

				finaliza = true;
			}

			// Intervalo de quantidade de débitos
			String intervaloQuantidadeContasVencidasInicial = ordemSeletivaHelper.getIntervaloQuantidadeContasVencidasInicial();
			String intervaloQuantidadeContasVencidasFinal = ordemSeletivaHelper.getIntervaloQuantidadeContasVencidasFinal();

			// Valor do Débito
			String valorTotalDebitoVencido = ordemSeletivaHelper.getValorTotalDebitoVencido();

			if((!Util.isVazioOuBranco(intervaloQuantidadeContasVencidasInicial) && !Util
							.isVazioOuBranco(intervaloQuantidadeContasVencidasFinal)) || !Util.isVazioOuBranco(valorTotalDebitoVencido)){
				StringBuffer consultaConta = new StringBuffer();
				consultaConta.append("select count(*) ");
				consultaConta.append("from Conta cnta ");
				consultaConta.append("inner join cnta.debitoCreditoSituacaoAtual dcst ");
				consultaConta.append("where cnta.imovel = imovel.id ");
				consultaConta.append("  and cnta.debitoCreditoSituacaoAtual.id in (" + DebitoCreditoSituacao.NORMAL + ","
								+ DebitoCreditoSituacao.RETIFICADA + "," + DebitoCreditoSituacao.INCLUIDA + ","
								+ DebitoCreditoSituacao.PARCELADA + ")");
				consultaConta.append("  and cnta.dataVencimentoConta < :dataAtual ");
				consultaConta.append("  and not exists (select pgt.id ");
				consultaConta.append("                  from Pagamento pgt");
				consultaConta.append("                  where pgt.conta = cnta.id) ");
				consultaConta.append("group by cnta.imovel ");
				consultaConta.append("having ");

				if(!Util.isVazioOuBranco(intervaloQuantidadeContasVencidasInicial)
								&& !Util.isVazioOuBranco(intervaloQuantidadeContasVencidasFinal)){
					consultaConta.append("count(*) between " + intervaloQuantidadeContasVencidasInicial + " and "
									+ intervaloQuantidadeContasVencidasFinal + " and ");
				}

				if(!Util.isVazioOuBranco(valorTotalDebitoVencido)){
					consultaConta.append("sum(cnta.valorAgua + cnta.valorEsgoto + cnta.debitos - cnta.valorCreditos - cnta.valorImposto) >= "
									+ valorTotalDebitoVencido + " and ");
				}

				parameters.put("dataAtual", new Date());

				// Removendo último 'and'
				hqlAux += "exists (" + consultaConta.substring(0, consultaConta.length() - 5) + ") and ";
				finaliza = true;
			}

			// Finaliza o HQL
			if(finaliza){
				hql += hqlAux;
				hql = hql.substring(0, hql.length() - 5);
			}else{
				hql = hql.substring(0, hql.length() - 7);
			}

			Query query = session.createQuery(hql.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){
				String key = (String) iterMap.next();

				if(parameters.get(key) instanceof Integer){
					Integer parametroInteger = (Integer) parameters.get(key);
					query.setParameter(key, parametroInteger);
				}else if(parameters.get(key) instanceof Date){
					Date parametroDate = (Date) parameters.get(key);
					query.setParameter(key, parametroDate);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = ((Number) query.uniqueResult()).intValue();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection verificarImoveisSemOrdemPendentePorTipoServico(Collection colecaoImoveis, Integer idServicoTipo)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer hql = new StringBuffer(" select imv.id ");
			hql.append(" from Imovel imv ");

			hql.append(" where imv.id in (:colecaoImoveis) ");
			hql.append(" and imv.id not in  (select os.imovel.id ");
			hql.append(" from OrdemServico os ");
			hql.append(" where os.imovel.id = imv.id ");
			hql.append(" and os.servicoTipo.id = :idServicoTipo ");
			hql.append(" and os.dataEncerramento is null) ");

			retorno = session.createQuery(hql.toString()).setInteger("idServicoTipo", idServicoTipo)
							.setParameterList("colecaoImoveis", colecaoImoveis).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public OrdemServico pesquisarOSPorDocumentoCobranca(Integer idCobrancaDocumento) throws ErroRepositorioException{

		Collection colecaoOS = new ArrayList();

		Object[] objetoOs = null;
		OrdemServico ordemServico = null;

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer hql = new StringBuffer("select os.dataEncerramento, ");
			hql.append(" os.situacao, os.dataExecucao ");
			hql.append(" from OrdemServico os ");
			hql.append(" where os.cobrancaDocumento = :idCobrancaDocumento ");

			colecaoOS = session.createQuery(hql.toString()).setInteger("idCobrancaDocumento", idCobrancaDocumento).list();

			if(!colecaoOS.isEmpty()){
				ordemServico = new OrdemServico();

				objetoOs = (Object[]) colecaoOS.iterator().next();

				if(objetoOs[0] != null){
					ordemServico.setDataEncerramento((Date) objetoOs[0]);
				}
				if(objetoOs[1] != null){
					ordemServico.setSituacao((Short) objetoOs[1]);
				}

				if(objetoOs[2] != null){
					ordemServico.setDataExecucao((Date) objetoOs[2]);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return ordemServico;
	}

	public List<OrdemServico> pequisarOrdensServicosOrdenadasPorInscricao(List<Integer> idsOs) throws ErroRepositorioException{

		List<OrdemServico> lista = new ArrayList<OrdemServico>(pesquisarOrdensServicos(idsOs));

		Collections.sort(lista, new Comparator<OrdemServico>() {

			public int compare(OrdemServico os1, OrdemServico os2){

				int ret = os1.getImovel().getLocalidade().getId().compareTo(os2.getImovel().getLocalidade().getId());
				if(ret != 0) return ret;

				ret = ((Integer) os1.getImovel().getSetorComercial().getCodigo())
								.compareTo(os2.getImovel().getSetorComercial().getCodigo());
				if(ret != 0) return ret;

				ret = ((Integer) os1.getImovel().getQuadra().getNumeroQuadra()).compareTo(os2.getImovel().getQuadra().getNumeroQuadra());
				if(ret != 0) return ret;

				ret = ((Short) os1.getImovel().getLote()).compareTo(os2.getImovel().getLote());
				if(ret != 0) return ret;

				ret = ((Short) os1.getImovel().getSubLote()).compareTo(os2.getImovel().getSubLote());
				if(ret != 0) return ret;

				return ret;
			}

		});

		return lista;
	}

	/**
	 * Cria as condicionais de acordo com os parâmetros de pesquisa informados
	 * pelo usuário
	 * [UC0492] - Gerar Relatório Acompanhamento de Execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @author Saulo Lima
	 * @date 05/02/2009
	 *       Trabalhar com Timestamp em vez de Date.
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return String
	 */
	private String criarCondicionaisOSGerarRelatorio(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade){

		String sql = " where ";

		if(origemServico != null && !origemServico.equals("")){
			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SELETIVO.toString())){
				sql = sql + " os.cbdo_id  is not null " + " and ";
			}
		}

		if(situacaoOS != null && !situacaoOS.equals("")){
			if(situacaoOS.equals(OrdemServico.SITUACAO_ENCERRADO.toString())){
				sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_ENCERRADO + " and ";
			}else if(situacaoOS.equals(OrdemServico.SITUACAO_PENDENTE.toString())){
				sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_PENDENTE + " and ";
			}
		}

		if(idsServicosTipos != null && !idsServicosTipos.equals("")
						&& (idsServicosTipos.length != 1 || !idsServicosTipos[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){

			String valoresIn = "";
			for(int i = 0; i < idsServicosTipos.length; i++){
				if(!idsServicosTipos[i].equals("")){
					valoresIn = valoresIn + idsServicosTipos[i] + ",";
				}
			}
			if(!valoresIn.equals("")){
				sql = sql + " servTp.svtp_id in (" + valoresIn;
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
			}
		}

		if(idUnidadeAtendimento != null && !idUnidadeAtendimento.equals("")){
			sql = sql + " unidadeAtendimento.unid_id = " + idUnidadeAtendimento + " and ";
		}

		try{
			if(ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar().toString().equals(ConstantesSistema.SIM.toString())){

				if(idUnidadeAtual != null && !idUnidadeAtual.equals("")
								&& origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SELETIVO.toString())){
					sql = sql + " tr.unid_iddestino = " + idUnidadeAtual
									+ " and tr.tram_tmtramite in (select max(tr.tram_tmtramite) from tramite tr where "
									+ " ra.rgat_id = tr.rgat_id)" + " and ";
				}else if(idUnidadeAtual != null && !idUnidadeAtual.equals("")
								&& origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){
					sql = sql + " tr.unid_iddestino = " + idUnidadeAtual
									+ " and tr.tram_tmtramite in (select max(tr1.tram_tmtramite) from tramite tr1 where "
									+ " os.orse_id = tr1.orse_id)" + " and ";
				}
			}
		}catch(ControladorException e){
			e.printStackTrace();
		}

		if(idUnidadeEncerramento != null && !idUnidadeEncerramento.equals("")){
			sql = sql + " osUnidade.unid_id = " + idUnidadeEncerramento + " and " + " osUnidade.attp_id = "
							+ AtendimentoRelacaoTipo.ENCERRAR + " and ";
		}

		if(periodoAtendimentoInicial != null && !periodoAtendimentoFinal.equals("")){

			String data1 = Util.recuperaDataInvertida(periodoAtendimentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			// sql = sql + " ra.rgat_tmregistroatendimento >= to_date('"+ data1
			// +"','YYYY-MM-DD') and ";
			sql = sql + " ra.rgat_tmregistroatendimento >= to_timestamp('" + data1
							+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoAtendimentoFinal != null && !periodoAtendimentoFinal.equals("")){
			// String data2 =
			// Util.recuperaDataInvertida(Util.adicionarNumeroDiasDeUmaData(periodoAtendimentoFinal,
			// 1));
			String data2 = Util.recuperaDataInvertida(periodoAtendimentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			// sql = sql + " ra.rgat_tmregistroatendimento <= to_date('"+ data2
			// +"','YYYY-MM-DD') and ";
			sql = sql + " ra.rgat_tmregistroatendimento <= to_timestamp('" + data2
							+ " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoInicial != null && !periodoEncerramentoInicial.equals("")){
			String data1 = Util.recuperaDataInvertida(periodoEncerramentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			// sql = sql + " os.orse_tmencerramento >= to_date('" + data1 + "','YYYY-MM-DD') and ";
			sql = sql + " os.orse_tmencerramento >= to_timestamp('" + data1 + " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoFinal != null && !periodoEncerramentoFinal.equals("")){
			// String data2 =
			// Util.recuperaDataInvertida(Util.adicionarNumeroDiasDeUmaData(periodoEncerramentoFinal,
			// 1));
			String data2 = Util.recuperaDataInvertida(periodoEncerramentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			// sql = sql + " os.orse_tmencerramento <= to_date('" + data2 + "','YYYY-MM-DD') and ";
			sql = sql + " os.orse_tmencerramento <= to_timestamp('" + data2 + " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(idEquipeProgramacao != null && !idEquipeProgramacao.equals("")){
			sql = sql + " osProgramacao.eqpe_id = " + idEquipeProgramacao + " and osProgramacao.ospg_icativo = "
							+ ConstantesSistema.INDICADOR_USO_ATIVO + " and ";
		}

		if(idEquipeExecucao != null && !idEquipeExecucao.equals("")){
			sql = sql + " osExecucaoEquipe.eqpe_id = " + idEquipeExecucao + " and ";
		}

		if(sql.length() > 7){
			// retira o " and " q fica sobrando no final da query
			sql = Util.formatarHQL(sql, 4);
		}else{
			// Não houve restrição
			sql = "";
		}

		return sql;
	}

	/**
	 * Cria a query de acordo com os parâmetros de pesquisa informados pelo
	 * usuário
	 * [UC0492] - Gerar Relatório Produtividade de Equipe
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 13/04/2011
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return Collection
	 */
	public Collection pesquisarGerarRelatorioProdutividadeEquipe(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String tipoOrdenacao, String idLocalidade)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT SUM(TEMPO/60)/SUM(QUANTIDADE) TEMPOMEDIO,                                                                            " // 1
							+ "   SUM(QUANTIDADE) QTD,                                                                                                           " // 2
							+ "   IDUNIDADE,                                                                                                                     " // 3
							+ "   NOMEUNIDADE,                                                                                                                   " // 4
							+ "   IDEQUIPE,                                                                                                                      " // 5
							+ "   NOMEEQUIPE,                                                                                                                    " // 6
							+ "   IDSERVICOTIPO,                                                                                                                 " // 7
							+ "   NOMESERVICOTIPO,                                                                                                               " // 8
							+ "   TEMPOPADRAO                                                                                                                    " // 9
							+ " FROM                                                                                                                             "
							+ "   (SELECT SUM(((extract(hour FROM TEMPO))*3600)+((extract(minute FROM TEMPO))*60)+((EXTRACT(SECOND FROM TEMPO))*1000)) AS tempo, "
							+ "     IDUNIDADE,                                                                                                                   "
							+ "     NOMEUNIDADE,                                                                                                                 "
							+ "     IDEQUIPE,                                                                                                                    "
							+ "     NOMEEQUIPE,                                                                                                                  "
							+ "     IDSERVICOTIPO,                                                                                                               "
							+ "     NOMESERVICOTIPO,                                                                                                             "
							+ "     TEMPOPADRAO,                                                                                                                 "
							+ "     quantidade                                                                                                                   "
							+ "   FROM                                                                                                                           "
							+ "     (SELECT COUNT(OS.ORSE_ID)                                                                          AS QUANTIDADE,            "
							+ "       UNIDADEATENDIMENTO.UNID_ID                                                                       AS IDUNIDADE,             "
							+ "       UNIDADEATENDIMENTO.UNID_DSUNIDADE                                                                AS NOMEUNIDADE,           "
							+ "       EQUIPE.EQPE_ID                                                                                   AS IDEQUIPE,              "
							+ "       EQUIPE.EQPE_NMEQUIPE                                                                             AS NOMEEQUIPE,            "
							+ "       SERVTP.SVTP_ID                                                                                   AS IDSERVICOTIPO,         "
							+ "       SERVTP.SVTP_DSSERVICOTIPO                                                                        AS NOMESERVICOTIPO,       "
							+ "       SERVTP.SVTP_NNTEMPOMEDIOEXECUCAO                                                                 AS TEMPOPADRAO,           "
							+ "       osAtividadePeriodoExecucao.oape_tmexecucaofim - osAtividadePeriodoExecucao.oape_tmexecucaoinicio AS tempo                  "
							+ " FROM "
							+ " ordem_servico os "
							+ " INNER JOIN "
							+ " servico_tipo servTp "
							+ " on os.svtp_id = servTp.svtp_id "
							+ " INNER JOIN "
							+ " ordem_servico_unidade osUnidade "
							+ " on osUnidade.orse_id = os.orse_id ";
			if(idLocalidade != null && !idLocalidade.equals("")){
				consulta = consulta + " INNER JOIN imovel i on i.imov_id = os.imov_id and i.loca_id = " + idLocalidade + " ";
			}

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta = consulta + " INNER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " registro_atendimento_unidade raUnidade " + " on raUnidade.rgat_id = ra.rgat_id and "
								+ " raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR + " INNER JOIN "
								+ " unidade_organizacional unidadeAtendimento " + " on unidadeAtendimento.unid_id = raUnidade.unid_id "
								+ " INNER JOIN " + " solicitacao_tipo_especificacao solTpEsp " + " on solTpEsp.step_id = ra.step_id "
								+ " INNER JOIN " + " tramite tr " + " on tr.rgat_id = ra.rgat_id ";

			}else{
				consulta = consulta + " LEFT OUTER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " registro_atendimento_unidade raUnidade " + " on raUnidade.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " unidade_organizacional unidadeAtendimento " + " on unidadeAtendimento.unid_id = raUnidade.unid_id "
								+ " INNER JOIN " + " tramite tr " + " on tr.rgat_id = ra.rgat_id ";
			}

			consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_atividade osAtividade " + " on osAtividade.orse_id = os.orse_id "
							+ " LEFT OUTER JOIN " + " os_atividade_periodo_execucao osAtividadePeriodoExecucao "
							+ " on osAtividadePeriodoExecucao.osat_id = osAtividade.osat_id " + " LEFT OUTER JOIN "
							+ " os_execucao_equipe osExecucaoEquipe "
							+ " on osExecucaoEquipe.oape_id = osAtividadePeriodoExecucao.oape_id " + " INNER JOIN equipe "
							+ " on osExecucaoEquipe.eqpe_id = equipe.eqpe_id ";

			consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_programacao osProgramacao "
							+ " on osProgramacao.orse_id = os.orse_id " + " LEFT OUTER JOIN " + " programacao_roteiro roteiroProgramacao "
							+ " on osProgramacao.pgrt_id = roteiroProgramacao.pgrt_id ";

			consulta = consulta
							+ criarCondicionaisOSGerarRelatorio(origemServico, situacaoOS, idsServicosTipos, idUnidadeAtendimento,
											idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
											periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao, idEquipeExecucao,
											idLocalidade);

			consulta = consulta
							+ " and (roteiroProgramacao.pgrt_id is null or roteiroProgramacao.pgrt_tmroteiro in (select max(roteiroProgramacao.pgrt_tmroteiro) FROM ordem_servico_programacao osProgramacao "
							+ " INNER JOIN programacao_roteiro roteiroProgramacao "
							+ " on osProgramacao.pgrt_id = roteiroProgramacao.pgrt_id " + " WHERE osProgramacao.orse_id = os.orse_id))";

			if(tipoOrdenacao != null && tipoOrdenacao.equals("1")){
				consulta = consulta + " order by servTp.svtp_id, ra.rgat_id ";
			}

			consulta += " GROUP BY UNIDADEATENDIMENTO.UNID_ID,                                                                   "
							+ "       UNIDADEATENDIMENTO.UNID_DSUNIDADE,                                                               "
							+ "       EQUIPE.EQPE_ID,                                                                                  "
							+ "       EQUIPE.EQPE_NMEQUIPE,                                                                            "
							+ "       SERVTP.SVTP_ID,                                                                                  "
							+ "       SERVTP.SVTP_DSSERVICOTIPO,                                                                       "
							+ "       SERVTP.SVTP_NNTEMPOMEDIOEXECUCAO,                                                                "
							+ "       OSATIVIDADEPERIODOEXECUCAO.OAPE_TMEXECUCAOFIM - OSATIVIDADEPERIODOEXECUCAO.OAPE_TMEXECUCAOINICIO "
							+ "     )                                                                                                  "
							+ "   GROUP BY IDUNIDADE,                                                                                  "
							+ "     NOMEUNIDADE,                                                                                       "
							+ "     IDEQUIPE,                                                                                          "
							+ "     NOMEEQUIPE,                                                                                        "
							+ "     IDSERVICOTIPO,                                                                                     "
							+ "     NOMESERVICOTIPO,                                                                                   "
							+ "     TEMPOPADRAO,                                                                                       "
							+ "     QUANTIDADE                                                                                         "
							+ "   )                                                                                                    "
							+ " GROUP BY IDUNIDADE,                                                                                    "
							+ "   NOMEUNIDADE,                                                                                         "
							+ "   IDEQUIPE,                                                                                            "
							+ "   NOMEEQUIPE,                                                                                          "
							+ "   IDSERVICOTIPO,                                                                                       "
							+ "   NOMESERVICOTIPO,                                                                                     "
							+ "   TEMPOPADRAO                                                                                          ";

			retorno = session.createSQLQuery(consulta).addScalar("TEMPOMEDIO", Hibernate.INTEGER).addScalar("QTD", Hibernate.INTEGER)
							.addScalar("IDUNIDADE", Hibernate.INTEGER).addScalar("NOMEUNIDADE", Hibernate.STRING)
							.addScalar("IDEQUIPE", Hibernate.INTEGER).addScalar("NOMEEQUIPE", Hibernate.STRING)
							.addScalar("IDSERVICOTIPO", Hibernate.INTEGER).addScalar("NOMESERVICOTIPO", Hibernate.STRING)
							.addScalar("TEMPOPADRAO", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Cria o count de acordo com os parâmetros de pesquisa informados pelo
	 * usuário
	 * [UC0492] - Gerar Relatório de Produtividade de Equipe
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 13/04/2011
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return int
	 */
	public int pesquisarGerarRelatorioProdutividadeEquipeCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT COUNT(*) AS count FROM (SELECT SUM(TEMPO/60)/SUM(QUANTIDADE) TEMPOMEDIO,                                                                            " // 1
							+ "   SUM(QUANTIDADE) QTD,                                                                                                           " // 2
							+ "   IDUNIDADE,                                                                                                                     " // 3
							+ "   NOMEUNIDADE,                                                                                                                   " // 4
							+ "   IDEQUIPE,                                                                                                                      " // 5
							+ "   NOMEEQUIPE,                                                                                                                    " // 6
							+ "   IDSERVICOTIPO,                                                                                                                 " // 7
							+ "   NOMESERVICOTIPO,                                                                                                               " // 8
							+ "   TEMPOPADRAO                                                                                                                    " // 9
							+ " FROM                                                                                                                             "
							+ "   (SELECT SUM(((extract(hour FROM TEMPO))*3600)+((extract(minute FROM TEMPO))*60)+((EXTRACT(SECOND FROM TEMPO))*1000)) AS tempo, "
							+ "     IDUNIDADE,                                                                                                                   "
							+ "     NOMEUNIDADE,                                                                                                                 "
							+ "     IDEQUIPE,                                                                                                                    "
							+ "     NOMEEQUIPE,                                                                                                                  "
							+ "     IDSERVICOTIPO,                                                                                                               "
							+ "     NOMESERVICOTIPO,                                                                                                             "
							+ "     TEMPOPADRAO,                                                                                                                 "
							+ "     quantidade                                                                                                                   "
							+ "   FROM                                                                                                                           "
							+ "     (SELECT COUNT(OS.ORSE_ID)                                                                          AS QUANTIDADE,            "
							+ "       UNIDADEATENDIMENTO.UNID_ID                                                                       AS IDUNIDADE,             "
							+ "       UNIDADEATENDIMENTO.UNID_DSUNIDADE                                                                AS NOMEUNIDADE,           "
							+ "       EQUIPE.EQPE_ID                                                                                   AS IDEQUIPE,              "
							+ "       EQUIPE.EQPE_NMEQUIPE                                                                             AS NOMEEQUIPE,            "
							+ "       SERVTP.SVTP_ID                                                                                   AS IDSERVICOTIPO,         "
							+ "       SERVTP.SVTP_DSSERVICOTIPO                                                                        AS NOMESERVICOTIPO,       "
							+ "       SERVTP.SVTP_NNTEMPOMEDIOEXECUCAO                                                                 AS TEMPOPADRAO,           "
							+ "       osAtividadePeriodoExecucao.oape_tmexecucaofim - osAtividadePeriodoExecucao.oape_tmexecucaoinicio AS tempo                  "
							+ " FROM "
							+ " ordem_servico os "
							+ " INNER JOIN "
							+ " servico_tipo servTp "
							+ " on os.svtp_id = servTp.svtp_id "
							+ " INNER JOIN "
							+ " ordem_servico_unidade osUnidade "
							+ " on osUnidade.orse_id = os.orse_id ";

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta = consulta + " INNER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " registro_atendimento_unidade raUnidade " + " on raUnidade.rgat_id = ra.rgat_id and "
								+ " raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR + " INNER JOIN "
								+ " unidade_organizacional unidadeAtendimento " + " on unidadeAtendimento.unid_id = raUnidade.unid_id "
								+ " INNER JOIN " + " solicitacao_tipo_especificacao solTpEsp " + " on solTpEsp.step_id = ra.step_id "
								+ " INNER JOIN " + " tramite tr " + " on tr.rgat_id = ra.rgat_id ";

			}else{
				consulta = consulta + " LEFT OUTER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " registro_atendimento_unidade raUnidade " + " on raUnidade.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " unidade_organizacional unidadeAtendimento " + " on unidadeAtendimento.unid_id = raUnidade.unid_id "
								+ " INNER JOIN " + " tramite tr " + " on tr.rgat_id = ra.rgat_id ";
			}

			consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_atividade osAtividade " + " on osAtividade.orse_id = os.orse_id "
							+ " LEFT OUTER JOIN " + " os_atividade_periodo_execucao osAtividadePeriodoExecucao "
							+ " on osAtividadePeriodoExecucao.osat_id = osAtividade.osat_id " + " LEFT OUTER JOIN "
							+ " os_execucao_equipe osExecucaoEquipe "
							+ " on osExecucaoEquipe.oape_id = osAtividadePeriodoExecucao.oape_id " + " INNER JOIN equipe "
							+ " on osExecucaoEquipe.eqpe_id = equipe.eqpe_id ";

			consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_programacao osProgramacao "
							+ " on osProgramacao.orse_id = os.orse_id " + " LEFT OUTER JOIN " + " programacao_roteiro roteiroProgramacao "
							+ " on osProgramacao.pgrt_id = roteiroProgramacao.pgrt_id ";

			consulta = consulta
							+ criarCondicionaisOSGerarRelatorio(origemServico, situacaoOS, idsServicosTipos, idUnidadeAtendimento,
											idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
											periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao, idEquipeExecucao,
											idLocalidade);

			consulta = consulta
							+ " and (roteiroProgramacao.pgrt_id is null or roteiroProgramacao.pgrt_tmroteiro in (select max(roteiroProgramacao.pgrt_tmroteiro) FROM ordem_servico_programacao osProgramacao "
							+ " INNER JOIN programacao_roteiro roteiroProgramacao "
							+ " on osProgramacao.pgrt_id = roteiroProgramacao.pgrt_id " + " WHERE osProgramacao.orse_id = os.orse_id))";

			consulta += " GROUP BY UNIDADEATENDIMENTO.UNID_ID,                                                                   "
							+ "       UNIDADEATENDIMENTO.UNID_DSUNIDADE,                                                               "
							+ "       EQUIPE.EQPE_ID,                                                                                  "
							+ "       EQUIPE.EQPE_NMEQUIPE,                                                                            "
							+ "       SERVTP.SVTP_ID,                                                                                  "
							+ "       SERVTP.SVTP_DSSERVICOTIPO,                                                                       "
							+ "       SERVTP.SVTP_NNTEMPOMEDIOEXECUCAO,                                                                "
							+ "       OSATIVIDADEPERIODOEXECUCAO.OAPE_TMEXECUCAOFIM - OSATIVIDADEPERIODOEXECUCAO.OAPE_TMEXECUCAOINICIO "
							+ "     )                                                                                                  "
							+ "   GROUP BY IDUNIDADE,                                                                                  "
							+ "     NOMEUNIDADE,                                                                                       "
							+ "     IDEQUIPE,                                                                                          "
							+ "     NOMEEQUIPE,                                                                                        "
							+ "     IDSERVICOTIPO,                                                                                     "
							+ "     NOMESERVICOTIPO,                                                                                   "
							+ "     TEMPOPADRAO,                                                                                       "
							+ "     QUANTIDADE                                                                                         "
							+ "   )                                                                                                    "
							+ " GROUP BY IDUNIDADE,                                                                                    "
							+ "   NOMEUNIDADE,                                                                                         "
							+ "   IDEQUIPE,                                                                                            "
							+ "   NOMEEQUIPE,                                                                                          "
							+ "   IDSERVICOTIPO,                                                                                       "
							+ "   NOMESERVICOTIPO,                                                                                     "
							+ "   TEMPOPADRAO)                                                                                          ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("count", Hibernate.INTEGER).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço não Executados por Equipe"
	 * 
	 * @author Péricles TAvares
	 * @date 09/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return int
	 */
	public int pesquisarGerarRelatorioResumoOSNaoExecutadasEquipeCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT COUNT(DISTINCT os.orse_id) as count" + " FROM " + " ordem_servico os " + " INNER JOIN "
							+ " servico_tipo servTp "
							+ " on os.svtp_id = servTp.svtp_id INNER JOIN atendimento_motivo_encrto ame ON os.amen_id =ame.amen_id  ";

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta = consulta + " INNER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " registro_atendimento_unidade raUnidade " + " on raUnidade.rgat_id = ra.rgat_id and "
								+ " raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR + " INNER JOIN "
								+ " unidade_organizacional unidadeAtendimento " + " on unidadeAtendimento.unid_id = raUnidade.unid_id "
								+ " INNER JOIN " + " tramite tr " + " on tr.rgat_id = ra.rgat_id ";

			}else{

				consulta += " INNER JOIN ordem_servico_unidade osUnidade ";
				consulta += " on (osUnidade.orse_id = os.orse_id and osUnidade.attp_id = 3)";
				consulta += " INNER JOIN unidade_organizacional unidadeEncerramento ";
				consulta += " on unidadeEncerramento.unid_id = osUnidade.unid_id ";
			}

			consulta = consulta + " LEFT OUTER JOIN ordem_servico_programacao osProgramacao on osProgramacao.orse_id = os.orse_id "
							+ " LEFT OUTER JOIN os_program_nao_encer_motivo opne on opne.opne_id = osProgramacao.opne_id "
							+ " LEFT JOIN equipe equipeProgramacao on osProgramacao.eqpe_id = equipeProgramacao.eqpe_id";

			if(Util.verificarIdNaoVazio(idUnidadeEncerramento)){
				consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_unidade osUnidade " + " on osUnidade.orse_id = os.orse_id ";
			}

			if(Util.verificarIdNaoVazio(idEquipeExecucao)){
				consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_atividade osAtividade "
								+ " on osAtividade.orse_id = os.orse_id " + " LEFT OUTER JOIN "
								+ " os_atividade_periodo_execucao osAtividadePeriodoExecucao "
								+ " on osAtividadePeriodoExecucao.osat_id = osAtividade.osat_id " + " LEFT OUTER JOIN "
								+ " os_execucao_equipe osExecucaoEquipe "
								+ " on osExecucaoEquipe.oape_id = osAtividadePeriodoExecucao.oape_id LEFT OUTER JOIN equipe "
								+ " on osExecucaoEquipe.eqpe_id = equipe.eqpe_id";
			}

			consulta = consulta
							+ criarCondicionaisOSGerarRelatorioResumoOSNaoExecutadasEquipe(origemServico, situacaoOS, idsServicosTipos,
											idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
											periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal,
											idEquipeProgramacao, idEquipeExecucao, null);

			consulta += "and (ame.amen_icexecucao = " + AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_NAO + ")";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("count", Hibernate.INTEGER).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço não Executados por Equipe"
	 * 
	 * @author Péricles Tavares
	 * @date 09/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return Collection
	 */
	public Collection pesquisarGerarRelatorioResumoOSNaoExecutadasEquipe(String origemServico, String situacaoOS,
					String[] idsServicosTipos, String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento,
					Date periodoAtendimentoInicial, Date periodoAtendimentoFinal, Date periodoEncerramentoInicial,
					Date periodoEncerramentoFinal, String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta += " SELECT unidadeAtendimento.unid_id as IDUNIDADE,"; // 0
				consulta += " unidadeAtendimento.unid_dsunidade AS NOMEUNIDADE,"; // 1
			}else{

				consulta += " SELECT unidadeEncerramento.unid_id as IDUNIDADE,"; // 0
				consulta += " unidadeEncerramento.unid_dsunidade AS NOMEUNIDADE,"; // 1
			}

			consulta += "   equipeProgramacao.eqpe_id AS IDEQUIPE," // 2
							+ "   equipeProgramacao.eqpe_nmequipe AS NOMEEQUIPE," // 3
							+ "   servtp.svtp_id AS IDSERVICO," // 4
							+ "   servtp.svtp_dsservicotipo AS NOMESERVICO, " // 5
							+ "   CASE  WHEN opne.opne_dsosprogramnaoencermot is not null THEN opne.opne_dsosprogramnaoencermot ELSE ame.AMEN_DSMOTIVOENCERRAMENTO END AS MOTIVOENCERRAMENTO, " // 6
							+ "   count(os.orse_id) AS QUANTIDADE " // 7
							+ " FROM " + " ordem_servico os " + " INNER JOIN "
							+ " servico_tipo servTp "
							+ " on os.svtp_id = servTp.svtp_id INNER JOIN atendimento_motivo_encrto ame ON os.amen_id =ame.amen_id  ";

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta = consulta + " INNER JOIN " + " registro_atendimento ra " + " on os.rgat_id = ra.rgat_id " + " INNER JOIN "
								+ " registro_atendimento_unidade raUnidade " + " on raUnidade.rgat_id = ra.rgat_id and "
								+ " raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR + " INNER JOIN "
								+ " unidade_organizacional unidadeAtendimento " + " on unidadeAtendimento.unid_id = raUnidade.unid_id "
								+ " INNER JOIN " + " tramite tr " + " on tr.rgat_id = ra.rgat_id ";

			}else{

				consulta += " INNER JOIN ordem_servico_unidade osUnidade ";
				consulta += " on (osUnidade.orse_id = os.orse_id and osUnidade.attp_id = 3)";
				consulta += " INNER JOIN unidade_organizacional unidadeEncerramento ";
				consulta += " on unidadeEncerramento.unid_id = osUnidade.unid_id ";
			}

			consulta = consulta + " LEFT OUTER JOIN ordem_servico_programacao osProgramacao on osProgramacao.orse_id = os.orse_id "
							+ " LEFT OUTER JOIN os_program_nao_encer_motivo opne on opne.opne_id = osProgramacao.opne_id "
							+ " LEFT JOIN equipe equipeProgramacao on osProgramacao.eqpe_id = equipeProgramacao.eqpe_id";

			if(Util.verificarIdNaoVazio(idUnidadeEncerramento)){
				consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_unidade osUnidade " + " on osUnidade.orse_id = os.orse_id ";
			}

			if(Util.verificarIdNaoVazio(idEquipeExecucao)){
				consulta = consulta + " LEFT OUTER JOIN " + " ordem_servico_atividade osAtividade "
								+ " on osAtividade.orse_id = os.orse_id " + " LEFT OUTER JOIN "
								+ " os_atividade_periodo_execucao osAtividadePeriodoExecucao "
								+ " on osAtividadePeriodoExecucao.osat_id = osAtividade.osat_id " + " LEFT OUTER JOIN "
								+ " os_execucao_equipe osExecucaoEquipe "
								+ " on osExecucaoEquipe.oape_id = osAtividadePeriodoExecucao.oape_id LEFT OUTER JOIN equipe "
								+ " on osExecucaoEquipe.eqpe_id = equipe.eqpe_id";
			}

			consulta = consulta
							+ criarCondicionaisOSGerarRelatorioResumoOSNaoExecutadasEquipe(origemServico, situacaoOS, idsServicosTipos,
											idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
											periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal,
											idEquipeProgramacao, idEquipeExecucao, null);

			consulta += "and (ame.amen_icexecucao = " + AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_NAO + ") ";

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta += " GROUP BY  unidadeAtendimento.unid_id,";
				consulta += " unidadeAtendimento.unid_dsunidade,";
			}else{

				consulta += " GROUP BY  unidadeEncerramento.unid_id,";
				consulta += " unidadeEncerramento.unid_dsunidade,";
			}

			consulta += "equipeProgramacao.eqpe_id,"
							+ "equipeProgramacao.eqpe_nmequipe,"
							+ "servtp.svtp_id,"
							+ "servtp.svtp_dsservicotipo,"
							+ "CASE WHEN opne.opne_dsosprogramnaoencermot is not null THEN opne.opne_dsosprogramnaoencermot ELSE ame.AMEN_DSMOTIVOENCERRAMENTO END";

			retorno = (Collection) session.createSQLQuery(consulta).addScalar("IDUNIDADE", Hibernate.INTEGER)
							.addScalar("NOMEUNIDADE", Hibernate.STRING).addScalar("IDEQUIPE", Hibernate.INTEGER)
							.addScalar("NOMEEQUIPE", Hibernate.STRING).addScalar("IDSERVICO", Hibernate.INTEGER)
							.addScalar("NOMESERVICO", Hibernate.STRING).addScalar("QUANTIDADE", Hibernate.INTEGER)
							.addScalar("MOTIVOENCERRAMENTO", Hibernate.STRING).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa os campos da OS que serão impressos no relatório de Ordem de
	 * Servico
	 * 
	 * @author isilva
	 * @date 19/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOSRelatorioPadraoCabecalho(Collection idsOrdemServico) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("SELECT ");
			consulta.append("new gcom.atendimentopublico.ordemservico.bean.OSRelatorioHelper(");
			consulta.append("os.dataGeracao, loc.id, sc.codigo, "); // 0,1,2
			consulta.append("quadra.numeroQuadra, imov.lote, imov.subLote, "); // 3,4,5
			consulta.append("imov.id, last.descricaoAbreviado, "); // 6,7
			consulta.append("lest.descricaoAbreviado, lesg.consumoMinimo, "); // 8,9
			consulta.append("prua.descricaoAbreviada, pcal.descricaoAbreviada, "); // 10,11
			consulta.append("ms.descricao, func.nome, "); // 12,13
			consulta.append("func.id, ra.pontoReferencia, "); // 14,15
			consulta.append("svtp.id, svtp.descricao, "); // 16,17
			consulta.append("lcoc.descricao, ra.dataPrevistaAtual, "); // 18,19
			consulta.append("ra.observacao, os.observacao, ra.id, "); // 20,21,22
			consulta.append("soltpesp.descricao, os.id, svtp.tempoMedioExecucao, "); // 23, 24, 25
			consulta.append("unidOrg.descricao, imov.numeroSequencialRota, rotaImovel.codigo, "); // 26,
			// 27,
			// 28
			consulta.append("servTpRef.id, servTpRef.descricao,svtp.valor, "); // 29, 30,31
			consulta.append("ra.registroAtendimento, "); // 32
			consulta.append("soltp.id, "); // 33
			consulta.append("soltp.descricao, "); // 34
			consulta.append("rotaImovel.id, "); // 35
			consulta.append("imov.numeroSegmento, "); // 36
			consulta.append("last.id, "); // 38
			consulta.append("last.descricao, "); // 39
			consulta.append("lest.id, "); // 40
			consulta.append("lest.descricao, "); // 41
			consulta.append("prua.id, "); // 42
			consulta.append("prua.descricao, "); // 43
			consulta.append("pcal.id, "); // 44
			consulta.append("pcal.descricao, "); // 45
			consulta.append("soltpesp.id) "); // 46
			consulta.append("FROM ");
			consulta.append("OrdemServicoUnidade osu ");
			consulta.append("INNER JOIN osu.atendimentoRelacaoTipo atrt ");
			consulta.append("INNER JOIN osu.usuario usuario ");
			consulta.append("INNER JOIN osu.unidadeOrganizacional unidOrg ");
			consulta.append("LEFT JOIN usuario.funcionario func ");
			consulta.append("INNER JOIN osu.ordemServico os ");
			consulta.append("INNER JOIN os.servicoTipo svtp ");
			consulta.append("LEFT JOIN os.registroAtendimento ra ");
			consulta.append("LEFT JOIN ra.meioSolicitacao ms ");
			consulta.append("LEFT JOIN ra.solicitacaoTipoEspecificacao soltpesp ");
			consulta.append("LEFT JOIN soltpesp.solicitacaoTipo soltp ");
			consulta.append("LEFT JOIN ra.localOcorrencia lcoc ");
			consulta.append("LEFT JOIN os.imovel imov ");
			consulta.append("LEFT JOIN imov.ligacaoAguaSituacao last ");
			consulta.append("LEFT JOIN imov.ligacaoEsgotoSituacao lest ");
			consulta.append("LEFT JOIN imov.ligacaoEsgoto lesg ");
			consulta.append("LEFT JOIN imov.localidade loc ");
			consulta.append("LEFT JOIN imov.setorComercial sc ");
			consulta.append("LEFT JOIN imov.quadra quadra ");
			consulta.append("LEFT JOIN imov.pavimentoRua prua ");
			consulta.append("LEFT JOIN imov.pavimentoCalcada pcal ");
			consulta.append("LEFT JOIN os.servicoTipoReferencia servTpRef ");
			consulta.append("LEFT JOIN imov.rota rotaImovel ");
			consulta.append("WHERE ");
			consulta.append("os.id in (:idsOrdemServico) and ");
			consulta.append("atrt.id = :idAtendimentoRelacaoTipo ");

			retorno = session.createQuery(consulta.toString()).setParameterList("idsOrdemServico", idsOrdemServico)
							.setParameter("idAtendimentoRelacaoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa os campos da OS que serão impressos no relatório de Ordem de
	 * Servico
	 * 
	 * @author isilva
	 * @date 19/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOSRelatorioCabecalho(Collection idsOrdemServico) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("SELECT ");
			consulta.append("new gcom.relatorio.ordemservico.OSRelatorioEstruturaHelper(");
			consulta.append("os.dataGeracao, loc.id, sc.codigo, "); // 0,1,2
			consulta.append("quadra.numeroQuadra, imov.lote, imov.subLote, "); // 3,4,5
			consulta.append("imov.id, last.descricaoAbreviado, "); // 6,7
			consulta.append("lest.descricaoAbreviado, lesg.consumoMinimo, "); // 8,9
			consulta.append("prua.descricaoAbreviada, pcal.descricaoAbreviada, "); // 10,11
			consulta.append("ms.descricao, func.nome, "); // 12,13
			consulta.append("func.id, ra.pontoReferencia, "); // 14,15
			consulta.append("svtp.id, svtp.descricao, "); // 16,17
			consulta.append("lcoc.descricao, ra.dataPrevistaAtual, "); // 18,19
			consulta.append("ra.observacao, os.observacao, ra.id, "); // 20,21,22
			consulta.append("soltpesp.descricao, os.id, svtp.tempoMedioExecucao, "); // 23, 24, 25
			consulta.append("unidOrg.descricao, imov.numeroSequencialRota, rotaImovel.codigo, "); // 26,
			// 27,
			// 28
			consulta.append("servTpRef.id, servTpRef.descricao,svtp.valor, "); // 29, 30,31
			consulta.append("ra.registroAtendimento, "); // 32
			consulta.append("soltp.id, "); // 33
			consulta.append("soltp.descricao, "); // 34
			consulta.append("rotaImovel.id, "); // 35
			consulta.append("imov.numeroSegmento, "); // 36
			consulta.append("last.id, "); // 38
			consulta.append("last.descricao, "); // 39
			consulta.append("lest.id, "); // 40
			consulta.append("lest.descricao, "); // 41
			consulta.append("prua.id, "); // 42
			consulta.append("prua.descricao, "); // 43
			consulta.append("pcal.id, "); // 44
			consulta.append("pcal.descricao, "); // 45
			consulta.append("soltpesp.id, "); // 46
			consulta.append("unidOrg.id) "); // 47
			consulta.append("FROM ");
			consulta.append("OrdemServicoUnidade osu ");
			consulta.append("INNER JOIN osu.atendimentoRelacaoTipo atrt ");
			consulta.append("INNER JOIN osu.usuario usuario ");
			consulta.append("INNER JOIN osu.unidadeOrganizacional unidOrg ");
			consulta.append("LEFT JOIN usuario.funcionario func ");
			consulta.append("INNER JOIN osu.ordemServico os ");
			consulta.append("INNER JOIN os.servicoTipo svtp ");
			consulta.append("LEFT JOIN os.registroAtendimento ra ");
			consulta.append("LEFT JOIN ra.meioSolicitacao ms ");
			consulta.append("LEFT JOIN ra.solicitacaoTipoEspecificacao soltpesp ");
			consulta.append("LEFT JOIN soltpesp.solicitacaoTipo soltp ");
			consulta.append("LEFT JOIN ra.localOcorrencia lcoc ");
			consulta.append("LEFT JOIN os.imovel imov ");
			consulta.append("LEFT JOIN imov.ligacaoAguaSituacao last ");
			consulta.append("LEFT JOIN imov.ligacaoEsgotoSituacao lest ");
			consulta.append("LEFT JOIN imov.ligacaoEsgoto lesg ");
			consulta.append("LEFT JOIN imov.localidade loc ");
			consulta.append("LEFT JOIN imov.setorComercial sc ");
			consulta.append("LEFT JOIN imov.quadra quadra ");
			consulta.append("LEFT JOIN imov.pavimentoRua prua ");
			consulta.append("LEFT JOIN imov.pavimentoCalcada pcal ");
			consulta.append("LEFT JOIN os.servicoTipoReferencia servTpRef ");
			consulta.append("LEFT JOIN imov.rota rotaImovel ");
			consulta.append("WHERE ");
			consulta.append("os.id in (:idsOrdemServico) and ");
			consulta.append("atrt.id = :idAtendimentoRelacaoTipo ");

			retorno = session.createQuery(consulta.toString()).setParameterList("idsOrdemServico", idsOrdemServico)
							.setParameter("idAtendimentoRelacaoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço Encerradas"
	 * 
	 * @author Anderson Italo
	 * @date 12/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return Integer
	 */
	public Integer pesquisaTotalRegistrosRelatorioResumoOsEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException{

		Integer retorno = 0;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append(" select count(distinct (os.orse_id)) AS QUANTIDADE ");
			consulta.append(" FROM  ordem_servico os ");
			consulta.append(" INNER JOIN servico_tipo servTp on os.svtp_id = servTp.svtp_id ");
			consulta.append(" INNER JOIN atendimento_motivo_encrto ame ON os.amen_id = ame.amen_id ");

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta.append(" INNER JOIN registro_atendimento ra on os.rgat_id = ra.rgat_id ");
				consulta.append(" INNER JOIN registro_atendimento_unidade raUnidade on raUnidade.rgat_id = ra.rgat_id and ");
				consulta.append(" raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
				consulta.append(" INNER JOIN unidade_organizacional unidadeAtendimento on unidadeAtendimento.unid_id = raUnidade.unid_id ");
				consulta.append(" INNER JOIN tramite tr on tr.rgat_id = ra.rgat_id ");
				consulta.append(" INNER JOIN ordem_servico_unidade osUnidade on osUnidade.orse_id = os.orse_id");

			}else{

				consulta.append(" INNER JOIN ordem_servico_unidade osUnidade ");
				consulta.append(" on (osUnidade.orse_id = os.orse_id and osUnidade.attp_id = 3)");
				consulta.append(" INNER JOIN unidade_organizacional unidadeEncerramento ");
				consulta.append(" on unidadeEncerramento.unid_id = osUnidade.unid_id ");
			}

			if(Util.verificarIdNaoVazio(idEquipeProgramacao)){
				consulta.append(" LEFT OUTER JOIN ordem_servico_programacao osProgramacao on osProgramacao.orse_id = os.orse_id ");
				consulta.append(" LEFT OUTER JOIN os_program_nao_encer_motivo opne on opne.opne_id = osProgramacao.opne_id ");
				consulta.append(" INNER JOIN equipe equipeProgramacao on osProgramacao.eqpe_id = equipeProgramacao.eqpe_id ");
			}

			if(Util.verificarIdNaoVazio(idUnidadeEncerramento)){
				if(!Util.verificarIdNaoVazio(idEquipeProgramacao)){
					consulta.append(" LEFT OUTER JOIN ordem_servico_programacao osProgramacao on osProgramacao.orse_id = os.orse_id ");
					consulta.append(" LEFT OUTER JOIN os_program_nao_encer_motivo opne on opne.opne_id = osProgramacao.opne_id ");
				}
			}

			if(Util.verificarIdNaoVazio(idEquipeExecucao)){

				consulta.append(" LEFT OUTER JOIN " + " ordem_servico_atividade osAtividade ");
				consulta.append(" on osAtividade.orse_id = os.orse_id ");
				consulta.append(" LEFT OUTER JOIN os_atividade_periodo_execucao osAtividadePeriodoExecucao ");
				consulta.append(" on osAtividadePeriodoExecucao.osat_id = osAtividade.osat_id ");
				consulta.append(" LEFT OUTER JOIN os_execucao_equipe osExecucaoEquipe ");
				consulta.append(" on osExecucaoEquipe.oape_id = osAtividadePeriodoExecucao.oape_id ");
				consulta.append(" LEFT OUTER JOIN equipe on osExecucaoEquipe.eqpe_id = equipe.eqpe_id ");
			}

			consulta.append(criarCondicionaisOSGerarRelatorioResumoOSEncerradas(origemServico, situacaoOS, idsServicosTipos,
							idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, null));

			Object retornoConsulta = session.createSQLQuery(consulta.toString()).addScalar("QUANTIDADE", Hibernate.INTEGER)
							.setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){
				retorno = (Integer) retornoConsulta;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Monta condicionais da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço Encerradas"
	 * 
	 * @author Anderson Italo
	 * @date 12/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @param idLocalidade
	 * @return
	 */
	private String criarCondicionaisOSGerarRelatorioResumoOSEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade){

		String sql = " where ";

		if(origemServico != null && !origemServico.equals("")){
			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SELETIVO.toString())){
				sql = sql + " os.cbdo_id  is not null " + " and ";
			}
		}

		if(situacaoOS != null && !situacaoOS.equals("")){
			if(situacaoOS.equals(OrdemServico.SITUACAO_ENCERRADO.toString())){
				sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_ENCERRADO + " and ";
			}else if(situacaoOS.equals(OrdemServico.SITUACAO_PENDENTE.toString())){
				sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_PENDENTE + " and ";
			}
		}

		if(idsServicosTipos != null && !idsServicosTipos.equals("")
						&& (idsServicosTipos.length != 1 || !idsServicosTipos[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){

			String valoresIn = "";
			for(int i = 0; i < idsServicosTipos.length; i++){
				if(!idsServicosTipos[i].equals("")){
					valoresIn = valoresIn + idsServicosTipos[i] + ",";
				}
			}
			if(!valoresIn.equals("")){
				sql = sql + " servTp.svtp_id in (" + valoresIn;
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
			}
		}

		if(Util.verificarIdNaoVazio(idUnidadeAtendimento)){
			sql = sql + " unidadeAtendimento.unid_id = " + idUnidadeAtendimento + " and ";
		}

		boolean adicionouUnidade = false;
		if(Util.verificarIdNaoVazio(idUnidadeAtual) && origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){
			sql = sql + " tr.unid_iddestino = " + idUnidadeAtual
							+ " and tr.tram_tmtramite in (select max(tr.tram_tmtramite) from tramite tr where "
							+ " ra.rgat_id = tr.rgat_id)" + " and ";
		}else{
			sql = sql + " osUnidade.unid_id = " + idUnidadeAtual + " and " + " osUnidade.attp_id = " + AtendimentoRelacaoTipo.ENCERRAR
							+ " and ";
			adicionouUnidade = true;
		}

		if(Util.verificarIdNaoVazio(idUnidadeEncerramento) && !adicionouUnidade){
			sql = sql + " osUnidade.unid_id = " + idUnidadeEncerramento + " and " + " osUnidade.attp_id = "
							+ AtendimentoRelacaoTipo.ENCERRAR + " and ";
		}

		if(Util.verificarIdNaoVazio(idLocalidade)){
			sql += " ra.loca_id = " + idLocalidade + " and ";
		}

		if(periodoAtendimentoInicial != null && !periodoAtendimentoFinal.equals("")){

			String data1 = Util.recuperaDataInvertida(periodoAtendimentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			// sql = sql + " ra.rgat_tmregistroatendimento >= to_date('"+ data1
			// +"','YYYY-MM-DD') and ";
			sql = sql + " ra.rgat_tmregistroatendimento >= to_timestamp('" + data1
							+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoAtendimentoFinal != null && !periodoAtendimentoFinal.equals("")){
			// String data2 =
			// Util.recuperaDataInvertida(Util.adicionarNumeroDiasDeUmaData(periodoAtendimentoFinal,
			// 1));
			String data2 = Util.recuperaDataInvertida(periodoAtendimentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			// sql = sql + " ra.rgat_tmregistroatendimento <= to_date('"+ data2
			// +"','YYYY-MM-DD') and ";
			sql = sql + " ra.rgat_tmregistroatendimento <= to_timestamp('" + data2
							+ " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoInicial != null && !periodoEncerramentoInicial.equals("")){
			String data1 = Util.recuperaDataInvertida(periodoEncerramentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			// sql = sql + " os.orse_tmencerramento >= to_date('" + data1 + "','YYYY-MM-DD') and ";
			sql = sql + " os.orse_tmencerramento >= to_timestamp('" + data1 + " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoFinal != null && !periodoEncerramentoFinal.equals("")){
			// String data2 =
			// Util.recuperaDataInvertida(Util.adicionarNumeroDiasDeUmaData(periodoEncerramentoFinal,
			// 1));
			String data2 = Util.recuperaDataInvertida(periodoEncerramentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			// sql = sql + " os.orse_tmencerramento <= to_date('" + data2 + "','YYYY-MM-DD') and ";
			sql = sql + " os.orse_tmencerramento <= to_timestamp('" + data2 + " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(Util.verificarIdNaoVazio(idEquipeProgramacao)){
			sql = sql + " osProgramacao.eqpe_id = " + idEquipeProgramacao + " and osProgramacao.ospg_icativo = "
							+ ConstantesSistema.INDICADOR_USO_ATIVO + " and ";
		}

		if(Util.verificarIdNaoVazio(idEquipeExecucao)){
			sql = sql + " osExecucaoEquipe.eqpe_id = " + idEquipeExecucao + " and ";
		}

		// retira o " and " q fica sobrando no final da query
		sql = Util.formatarHQL(sql, 4);

		return sql;
	}

	/**
	 * Monta condicionais da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço Encerradas"
	 * 
	 * @author Anderson Italo
	 * @date 12/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @param idLocalidade
	 * @return
	 */
	private String criarCondicionaisOSGerarRelatorioResumoOSNaoExecutadasEquipe(String origemServico, String situacaoOS,
					String[] idsServicosTipos, String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento,
					Date periodoAtendimentoInicial, Date periodoAtendimentoFinal, Date periodoEncerramentoInicial,
					Date periodoEncerramentoFinal, String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade){

		String sql = " where ";

		if(origemServico != null && !origemServico.equals("")){
			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SELETIVO.toString())){
				sql = sql + " os.cbdo_id  is not null " + " and ";
			}
		}

		if(situacaoOS != null && !situacaoOS.equals("")){
			if(situacaoOS.equals(OrdemServico.SITUACAO_ENCERRADO.toString())){
				sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_ENCERRADO + " and ";
			}else if(situacaoOS.equals(OrdemServico.SITUACAO_PENDENTE.toString())){
				sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_PENDENTE + " and ";
			}
		}

		if(idsServicosTipos != null && !idsServicosTipos.equals("")
						&& (idsServicosTipos.length != 1 || !idsServicosTipos[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){

			String valoresIn = "";
			for(int i = 0; i < idsServicosTipos.length; i++){
				if(!idsServicosTipos[i].equals("")){
					valoresIn = valoresIn + idsServicosTipos[i] + ",";
				}
			}
			if(!valoresIn.equals("")){
				sql = sql + " servTp.svtp_id in (" + valoresIn;
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
			}
		}

		if(idUnidadeAtendimento != null && !idUnidadeAtendimento.equals("")){
			sql = sql + " unidadeAtendimento.unid_id = " + idUnidadeAtendimento + " and ";
		}

		boolean adicionouUnidade = false;
		if(idUnidadeAtual != null && !idUnidadeAtual.equals("")
						&& origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){
			sql = sql + " tr.unid_iddestino = " + idUnidadeAtual
							+ " and tr.tram_tmtramite in (select max(tr.tram_tmtramite) from tramite tr where "
							+ " ra.rgat_id = tr.rgat_id)" + " and ";
		}else{
			sql = sql + " osUnidade.unid_id = " + idUnidadeAtual + " and " + " osUnidade.attp_id = " + AtendimentoRelacaoTipo.ENCERRAR
							+ " and ";
			adicionouUnidade = true;
		}

		if(idUnidadeEncerramento != null && !idUnidadeEncerramento.equals("") && !adicionouUnidade){
			sql = sql + " osUnidade.unid_id = " + idUnidadeEncerramento + " and " + " osUnidade.attp_id = "
							+ AtendimentoRelacaoTipo.ENCERRAR + " and ";
		}

		if(idLocalidade != null && !idLocalidade.equals("")){
			sql += " ra.loca_id = " + idLocalidade + " and ";
		}

		if(periodoAtendimentoInicial != null && !periodoAtendimentoFinal.equals("")){

			String data1 = Util.recuperaDataInvertida(periodoAtendimentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			// sql = sql + " ra.rgat_tmregistroatendimento >= to_date('"+ data1
			// +"','YYYY-MM-DD') and ";
			sql = sql + " ra.rgat_tmregistroatendimento >= to_timestamp('" + data1
							+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoAtendimentoFinal != null && !periodoAtendimentoFinal.equals("")){
			// String data2 =
			// Util.recuperaDataInvertida(Util.adicionarNumeroDiasDeUmaData(periodoAtendimentoFinal,
			// 1));
			String data2 = Util.recuperaDataInvertida(periodoAtendimentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			// sql = sql + " ra.rgat_tmregistroatendimento <= to_date('"+ data2
			// +"','YYYY-MM-DD') and ";
			sql = sql + " ra.rgat_tmregistroatendimento <= to_timestamp('" + data2
							+ " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoInicial != null && !periodoEncerramentoInicial.equals("")){
			String data1 = Util.recuperaDataInvertida(periodoEncerramentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			// sql = sql + " os.orse_tmencerramento >= to_date('" + data1 + "','YYYY-MM-DD') and ";
			sql = sql + " os.orse_tmencerramento >= to_timestamp('" + data1 + " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoFinal != null && !periodoEncerramentoFinal.equals("")){
			// String data2 =
			// Util.recuperaDataInvertida(Util.adicionarNumeroDiasDeUmaData(periodoEncerramentoFinal,
			// 1));
			String data2 = Util.recuperaDataInvertida(periodoEncerramentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			// sql = sql + " os.orse_tmencerramento <= to_date('" + data2 + "','YYYY-MM-DD') and ";
			sql = sql + " os.orse_tmencerramento <= to_timestamp('" + data2 + " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(idEquipeProgramacao != null && !idEquipeProgramacao.equals("")){
			sql = sql + " osProgramacao.eqpe_id = " + idEquipeProgramacao + " and osProgramacao.ospg_icativo = "
							+ ConstantesSistema.INDICADOR_USO_ATIVO + " and ";
		}

		if(idEquipeExecucao != null && !idEquipeExecucao.equals("")){
			sql = sql + " osExecucaoEquipe.eqpe_id = " + idEquipeExecucao + " and ";
		}

		// retira o " and " q fica sobrando no final da query
		sql = Util.formatarHQL(sql, 4);

		return sql;
	}

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço Encerradas"
	 * 
	 * @author Anderson Italo
	 * @date 12/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return Collection
	 */
	public Collection pesquisaRelatorioResumoOsEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer("");

		try{

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta.append(" SELECT unidadeAtendimento.unid_id as IDUNIDADE,"); // 0
				consulta.append(" unidadeAtendimento.unid_dsunidade AS NOMEUNIDADE,"); // 1
			}else{

				consulta.append(" SELECT unidadeEncerramento.unid_id as IDUNIDADE,"); // 0
				consulta.append(" unidadeEncerramento.unid_dsunidade AS NOMEUNIDADE,"); // 1
			}

			consulta.append(" servtp.svtp_id AS IDTIPOSERVICO,"); // 2
			consulta.append(" servtp.svtp_dsservicotipo AS NOMETIPOSERVICO, "); // 3
			consulta.append(" count(os.orse_id) AS QUANTIDADE, "); // 4
			consulta.append(" sum (CASE  WHEN ame.amen_icexecucao = 1 ");
			consulta.append(" THEN 1 ELSE 0 END) AS QUANTIDADECOMEXECUCAO, "); // 5
			consulta.append(" sum (CASE  WHEN ame.amen_icexecucao = 2 ");
			consulta.append(" THEN 1 ELSE 0 END) AS QUANTIDADESEMEXECUCAO, "); // 6

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){
				consulta.append(" sum (CASE  WHEN ame.amen_icexecucao = 1 AND ra.rgat_dtprevistaatual <= os.orse_tmencerramento");
				consulta.append(" THEN 1 ELSE 0 END) AS QUANTIDADENOPRAZO, "); // 7
				consulta.append(" sum (CASE  WHEN ame.amen_icexecucao = 1 AND ra.rgat_dtprevistaatual > os.orse_tmencerramento");
				consulta.append(" THEN 1 ELSE 0 END) AS QUANTIDADEFORAPRAZO "); // 8
			}else{
				consulta.append(" 0 AS QUANTIDADENOPRAZO, "); // 7
				consulta.append(" 0 AS QUANTIDADEFORAPRAZO "); // 8
			}

			consulta.append(" FROM  ordem_servico os ");
			consulta.append(" INNER JOIN servico_tipo servTp on os.svtp_id = servTp.svtp_id ");
			consulta.append(" INNER JOIN atendimento_motivo_encrto ame ON os.amen_id = ame.amen_id ");

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta.append(" INNER JOIN registro_atendimento ra on os.rgat_id = ra.rgat_id ");
				consulta.append(" INNER JOIN registro_atendimento_unidade raUnidade on raUnidade.rgat_id = ra.rgat_id and ");
				consulta.append(" raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
				consulta.append(" INNER JOIN unidade_organizacional unidadeAtendimento on unidadeAtendimento.unid_id = raUnidade.unid_id ");
				consulta.append(" INNER JOIN tramite tr on tr.rgat_id = ra.rgat_id ");
				consulta.append(" INNER JOIN ordem_servico_unidade osUnidade on osUnidade.orse_id = os.orse_id ");

			}else{

				consulta.append(" INNER JOIN ordem_servico_unidade osUnidade ");
				consulta.append(" on (osUnidade.orse_id = os.orse_id and osUnidade.attp_id = 3)");
				consulta.append(" INNER JOIN unidade_organizacional unidadeEncerramento ");
				consulta.append(" on unidadeEncerramento.unid_id = osUnidade.unid_id ");
			}

			if(Util.verificarIdNaoVazio(idEquipeProgramacao)){
				consulta.append(" LEFT OUTER JOIN ordem_servico_programacao osProgramacao on osProgramacao.orse_id = os.orse_id ");
				consulta.append(" LEFT OUTER JOIN os_program_nao_encer_motivo opne on opne.opne_id = osProgramacao.opne_id ");
				consulta.append(" INNER JOIN equipe equipeProgramacao on osProgramacao.eqpe_id = equipeProgramacao.eqpe_id ");
			}

			if(Util.verificarIdNaoVazio(idUnidadeEncerramento)){
				if(!Util.verificarIdNaoVazio(idEquipeProgramacao)){
					consulta.append(" LEFT OUTER JOIN ordem_servico_programacao osProgramacao on osProgramacao.orse_id = os.orse_id ");
					consulta.append(" LEFT OUTER JOIN os_program_nao_encer_motivo opne on opne.opne_id = osProgramacao.opne_id ");
				}
			}

			if(Util.verificarIdNaoVazio(idEquipeExecucao)){

				consulta.append(" LEFT OUTER JOIN " + " ordem_servico_atividade osAtividade ");
				consulta.append(" on osAtividade.orse_id = os.orse_id ");
				consulta.append(" LEFT OUTER JOIN os_atividade_periodo_execucao osAtividadePeriodoExecucao ");
				consulta.append(" on osAtividadePeriodoExecucao.osat_id = osAtividade.osat_id ");
				consulta.append(" LEFT OUTER JOIN os_execucao_equipe osExecucaoEquipe ");
				consulta.append(" on osExecucaoEquipe.oape_id = osAtividadePeriodoExecucao.oape_id ");
				consulta.append(" LEFT OUTER JOIN equipe on osExecucaoEquipe.eqpe_id = equipe.eqpe_id ");
			}

			consulta.append(criarCondicionaisOSGerarRelatorioResumoOSEncerradas(origemServico, situacaoOS, idsServicosTipos,
							idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, null));

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta.append(" GROUP BY  unidadeAtendimento.unid_id,");
				consulta.append(" unidadeAtendimento.unid_dsunidade,");
			}else{

				consulta.append(" GROUP BY  unidadeEncerramento.unid_id,");
				consulta.append(" unidadeEncerramento.unid_dsunidade,");
			}

			consulta.append(" servtp.svtp_id,");
			consulta.append(" servtp.svtp_dsservicotipo");

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta.append(" ORDER BY  unidadeAtendimento.unid_id,");
				consulta.append(" unidadeAtendimento.unid_dsunidade,");
			}else{

				consulta.append(" ORDER BY  unidadeEncerramento.unid_id,");
				consulta.append(" unidadeEncerramento.unid_dsunidade,");
			}

			consulta.append(" servtp.svtp_id,");
			consulta.append(" servtp.svtp_dsservicotipo");

			retorno = (Collection) session.createSQLQuery(consulta.toString()).addScalar("IDUNIDADE", Hibernate.INTEGER)
							.addScalar("NOMEUNIDADE", Hibernate.STRING).addScalar("IDTIPOSERVICO", Hibernate.INTEGER)
							.addScalar("NOMETIPOSERVICO", Hibernate.STRING).addScalar("QUANTIDADE", Hibernate.INTEGER)
							.addScalar("QUANTIDADECOMEXECUCAO", Hibernate.INTEGER).addScalar("QUANTIDADESEMEXECUCAO", Hibernate.INTEGER)
							.addScalar("QUANTIDADENOPRAZO", Hibernate.INTEGER).addScalar("QUANTIDADEFORAPRAZO", Hibernate.INTEGER)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC1015] Relatório Ordens de Serviço Pendentes
	 * Obter dados para geração relatório.
	 * 
	 * @author Anderson Italo
	 * @date 08/06/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioResumoOrdensServicoPendentesHelper> pesquisaRelatorioResumoOSPendentes(
					FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Map parameters = new HashMap();
		Query query = null;

		try{

			consulta.append("SELECT ");
			consulta.append("new gcom.relatorio.atendimentopublico.RelatorioResumoOrdensServicoPendentesHelper(");
			consulta.append("os.id, gre.id, gre.nome, "); // 0,1,2
			consulta.append("loc.id, loc.descricao, "); // 3,4
			consulta.append("sc.codigo, sc.descricao, quadra.numeroQuadra, "); // 5,6,7
			consulta.append("imov.lote, imov.subLote, rotaImovel.id, "); // 8,9,10
			consulta.append("imov.numeroSegmento, os.dataEmissao, svtp.id, "); // 11,12,13
			consulta.append("svtp.descricao, imov.id, "); // 14,15
			consulta.append("undn.id, undn.nome) ");// 16,17
			consulta.append("FROM ");
			consulta.append("OrdemServicoUnidade osu ");
			consulta.append("INNER JOIN osu.ordemServico os ");
			consulta.append("INNER JOIN os.servicoTipo svtp ");
			consulta.append("INNER JOIN os.registroAtendimento ra ");
			consulta.append("INNER JOIN os.imovel imov ");
			consulta.append("INNER JOIN imov.localidade loc ");
			consulta.append("INNER JOIN imov.setorComercial sc ");
			consulta.append("INNER JOIN imov.quadra quadra ");
			consulta.append("INNER JOIN imov.rota rotaImovel ");
			consulta.append("INNER JOIN loc.gerenciaRegional gre ");
			consulta.append("INNER JOIN loc.unidadeNegocio undn ");
			consulta.append("WHERE ");
			consulta.append("osu.atendimentoRelacaoTipo.id = :idAtendimentoRelacaoTipo ");
			consulta.append("and loc.id between :idLocalidadeInicial and :idLocalidadeFinal ");
			consulta.append("and os.situacao = " + OrdemServico.SITUACAO_PENDENTE + " ");
			consulta.append("or gre.id = :idGerencia or undn.id = :idUnidadeNegocio ");
			// Gerência Regional
			parameters.put("idGerencia", filtro.getIdGerenciaRegional());

			// Localidade
			parameters.put("idLocalidadeInicial", filtro.getIdLocalidadeInicial());
			parameters.put("idLocalidadeFinal", filtro.getIdLocalidadeFinal());

			// Unidade de Negócio
			parameters.put("idUnidadeNegocio", filtro.getIdUnidadeNegocio());

			// Setor Comercial
			if(!Util.isVazioOuBranco(filtro.getCodigoSetorComercialInicial())
							&& !Util.isVazioOuBranco(filtro.getCodigoSetorComercialFinal())){

				consulta.append("and sc.codigo between :codigoSetorInicial and :codigoSetorFinal ");
				parameters.put("codigoSetorInicial", filtro.getCodigoSetorComercialInicial());
				parameters.put("codigoSetorFinal", filtro.getCodigoSetorComercialFinal());
			}

			// Tipo de Serviço
			if(!Util.isVazioOrNulo(filtro.getServicoTipo())){

				Integer idTipoServico = ((Number) Util.retonarObjetoDeColecao(filtro.getServicoTipo())).intValue();

				if(filtro.getServicoTipo().size() > 1 || idTipoServico != -1){
					consulta.append("and svtp.id in (:codigosTipoServico) ");
					parameters.put("codigosTipoServico", filtro.getServicoTipo());
				}
			}

			// Período de Geração
			if(filtro.getDataGeracaoInicial() != null && filtro.getDataGeracaoFinal() != null){

				consulta.append(" and os.dataGeracao between (:dataGeracaoInicial) AND (:dataGeracaoFinal) ");

				parameters.put("dataGeracaoInicial", Util.formatarDataInicial(filtro.getDataGeracaoInicial()));
				parameters.put("dataGeracaoFinal", Util.formatarDataFinal(filtro.getDataGeracaoFinal()));
			}

			consulta.append("order by gre.id, loc.id, sc.codigo, svtp.id, os.dataEmissao ");

			query = session.createQuery(consulta.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){

					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{

					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = query.setParameter("idAtendimentoRelacaoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC1015] Relatório Ordens de Serviço Pendentes
	 * Obtém o total de registros do relatório.
	 * 
	 * @author Anderson Italo
	 * @date 08/06/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistrosRelatorioResumoOSPendentes(FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Map parameters = new HashMap();
		Query query = null;

		try{

			consulta.append("SELECT ");
			consulta.append("count (os.id) ");
			consulta.append("FROM ");
			consulta.append("OrdemServicoUnidade osu ");
			consulta.append("INNER JOIN osu.ordemServico os ");
			consulta.append("INNER JOIN os.servicoTipo svtp ");
			consulta.append("INNER JOIN os.registroAtendimento ra ");
			consulta.append("INNER JOIN os.imovel imov ");
			consulta.append("INNER JOIN imov.localidade loc ");
			consulta.append("INNER JOIN imov.setorComercial sc ");
			consulta.append("INNER JOIN imov.quadra quadra ");
			consulta.append("INNER JOIN loc.gerenciaRegional gre ");
			consulta.append("WHERE ");
			consulta.append("osu.atendimentoRelacaoTipo.id = :idAtendimentoRelacaoTipo ");
			consulta.append("and gre.id = :idGerencia and loc.id between :idLocalidadeInicial and :idLocalidadeFinal ");

			// Gerência Regional
			parameters.put("idGerencia", filtro.getIdGerenciaRegional());

			// Localidade
			parameters.put("idLocalidadeInicial", filtro.getIdLocalidadeInicial());
			parameters.put("idLocalidadeFinal", filtro.getIdLocalidadeFinal());

			// Setor Comercial
			if(!Util.isVazioOuBranco(filtro.getCodigoSetorComercialInicial())
							&& !Util.isVazioOuBranco(filtro.getCodigoSetorComercialFinal())){

				consulta.append("and sc.codigo between :codigoSetorInicial and :codigoSetorFinal ");
				parameters.put("codigoSetorInicial", filtro.getCodigoSetorComercialInicial());
				parameters.put("codigoSetorFinal", filtro.getCodigoSetorComercialFinal());
			}

			// Tipo de Serviço
			if(!Util.isVazioOrNulo(filtro.getServicoTipo())){

				Integer idTipoServico = ((Number) Util.retonarObjetoDeColecao(filtro.getServicoTipo())).intValue();

				if(filtro.getServicoTipo().size() > 1 || idTipoServico != -1){

					consulta.append("and svtp.id in (:codigosTipoServico) ");
					parameters.put("codigosTipoServico", filtro.getServicoTipo());
				}
			}

			// Período de Geração
			if(filtro.getDataGeracaoInicial() != null && filtro.getDataGeracaoFinal() != null){

				consulta.append(" and os.dataGeracao between (:dataGeracaoInicial) AND (:dataGeracaoFinal) ");

				parameters.put("dataGeracaoInicial", Util.formatarDataInicial(filtro.getDataGeracaoInicial()));
				parameters.put("dataGeracaoFinal", Util.formatarDataFinal(filtro.getDataGeracaoFinal()));
			}

			query = session.createQuery(consulta.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){

					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{

					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = ((Number) query.setParameter("idAtendimentoRelacaoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).setMaxResults(1)
							.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Recupera o perfil de infração com o máximo de correspondência com os dados do imóvel.
	 * 
	 * @param infracaoTipoId
	 * @param categoriaId
	 * @param subCategoriaId
	 * @param perfilId
	 * @return nfração perfil
	 * @throws ErroRepositorioException
	 */
	public InfracaoPerfil pesquisarInfracaoPerfil(Integer infracaoTipoId, Integer categoriaId, Integer subCategoriaId, Integer perfilId)
					throws ErroRepositorioException{

		InfracaoPerfil retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("FROM InfracaoPerfil ip where ip.infracaoTipo.id = :INFRACAOTIPOID");
			if(categoriaId != null){
				consulta.append(" and (ip.categoria.id = :CATEGORIA or ip.categoria.id is null)");
			}
			if(subCategoriaId != null){
				consulta.append(" and (ip.subcategoria.id = :SUBCATEGORIA or ip.subcategoria.id is null)");
			}
			if(perfilId != null){
				consulta.append(" and (ip.imovelPerfil.id = :IMOVELPERFIL or ip.imovelPerfil.id is null)");
			}

			consulta.append(" ORDER BY ip.categoria.id, ip.subcategoria.id, ip.imovelPerfil.id");
			retorno = (InfracaoPerfil) session.createQuery(consulta.toString()).setInteger("INFRACAOTIPOID", infracaoTipoId)
							.setInteger("CATEGORIA", categoriaId).setInteger("SUBCATEGORIA", subCategoriaId)
							.setInteger("IMOVELPERFIL", perfilId).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Recupera os debitos tipos para o perfil daquela infração.
	 * 
	 * @param infracaoTipoId
	 * @param categoriaId
	 * @param subCategoriaId
	 * @param perfilId
	 * @return Collection<DebitoTipo>
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoTipo> pesquisarDebitosTipoInfracaoPerfil(Integer infracaoPerfilId) throws ErroRepositorioException{

		Collection<DebitoTipo> retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select ip.debitoTipo FROM InfracaoPerfilDebitoTipo ip ");
			consulta.append("where ip.infracaoPerfil.id = :INFRACAOPERFILID ");
			consulta.append("ORDER BY ip.debitoTipo.id");
			retorno = (Collection<DebitoTipo>) session.createQuery(consulta.toString()).setInteger("INFRACAOPERFILID", infracaoPerfilId)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Recupera a Ocorrencia Infracao pelo nnDoctoInfracao e nnAnoDoctoInfracao.
	 * 
	 * @param nnDoctoInfracao
	 * @param nnAnoDoctoInfracao
	 * @return OcorrenciaInfracao
	 * @throws ControladorException
	 */
	public OcorrenciaInfracao recuperarOcorrenciaInfracao(Integer nnDoctoInfracao, Integer nnAnoDoctoInfracao)
					throws ErroRepositorioException{

		OcorrenciaInfracao retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("FROM OcorrenciaInfracao op ");
			consulta.append("where op.nnDoctoInfracao = :NNDOCTOINFRACAO and op.nnAnoDoctoInfracao = :NNANODOCTOINFRACAO ");
			consulta.append("ORDER BY op.id");
			retorno = (OcorrenciaInfracao) session.createQuery(consulta.toString()).setInteger("NNDOCTOINFRACAO", nnDoctoInfracao)
							.setInteger("NNANODOCTOINFRACAO", nnAnoDoctoInfracao).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Recupera a Ocorrencia Infracao Item pelo nnDoctoInfracao e nnAnoDoctoInfracao da Ocorrencia
	 * Infranção.
	 * 
	 * @param nnDoctoInfracao
	 * @param nnAnoDoctoInfracao
	 * @return OcorrenciaInfracao
	 * @throws ControladorException
	 */
	public Collection<OcorrenciaInfracaoItem> recuperarOcorrenciaInfracaoItem(Integer nnDoctoInfracao, Integer nnAnoDoctoInfracao)
					throws ErroRepositorioException{

		Collection<OcorrenciaInfracaoItem> retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("FROM OcorrenciaInfracaoItem op ");
			consulta.append("LEFT JOIN op.infracaoTipo ");
			consulta.append("where op.ocorrenciaInfracao.nnDoctoInfracao = :NNDOCTOINFRACAO ");
			consulta.append("and op.ocorrenciaInfracao.nnAnoDoctoInfracao = :NNANODOCTOINFRACAO ");
			consulta.append("ORDER BY op.infracaoTipo");
			retorno = (Collection<OcorrenciaInfracaoItem>) session.createQuery(consulta.toString())
							.setInteger("NNDOCTOINFRACAO", nnDoctoInfracao).setInteger("NNANODOCTOINFRACAO", nnAnoDoctoInfracao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	private void inicializarObjetosOS(OrdemServico ordemServico){

		if(ordemServico != null){

			// Hibernate.initialize(ordemServico.getId());
			// Hibernate.initialize(ordemServico.getPercentualCobranca());
			// Hibernate.initialize(ordemServico.getSituacao());
			// Hibernate.initialize(ordemServico.getDataEncerramento());
			// Hibernate.initialize(ordemServico.getDataGeracao());
			// Hibernate.initialize(ordemServico.getObservacao());

			if(ordemServico.getCobrancaDocumento() != null){
				Hibernate.initialize(ordemServico.getCobrancaDocumento());
			}

			if(ordemServico.getOsReferencia() != null){
				Hibernate.initialize(ordemServico.getOsReferencia());
				// Hibernate.initialize(ordemServico.getOsReferencia().getId());
				// Hibernate.initialize(ordemServico.getOsReferencia().getSituacao());
			}

			// Hibernate.initialize(ordemServico.getUltimaAlteracao());

			if(ordemServico.getRegistroAtendimento() != null){
				Hibernate.initialize(ordemServico.getRegistroAtendimento());
				// Hibernate.initialize(ordemServico.getRegistroAtendimento().getId());
				Hibernate.initialize(ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao());
				// if(ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao() !=
				// null){
				// Hibernate.initialize(ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua());
				// Hibernate.initialize(ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getDescricao());
				// }

				if(ordemServico.getRegistroAtendimento().getImovel() != null){
					Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel());
					// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getId());
					// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLote());
					// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getSubLote());
					// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getIndicadorExclusao());
					// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getNumeroImovel());
					// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getUltimaAlteracao());

					if(ordemServico.getRegistroAtendimento().getImovel().getLocalidade() != null){
						Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLocalidade());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLocalidade().getId());
					}

					if(ordemServico.getRegistroAtendimento().getImovel().getSetorComercial() != null){
						Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getSetorComercial());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getSetorComercial().getId());
					}

					if(ordemServico.getRegistroAtendimento().getImovel().getQuadra() != null){
						Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getQuadra());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getQuadra().getId());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getQuadra().getNumeroQuadra());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getQuadra().getIndicadorRedeAgua());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getQuadra().getIndicadorRedeEsgoto());
					}

					if(ordemServico.getRegistroAtendimento().getImovel().getImovelPerfil() != null){
						Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getImovelPerfil());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getImovelPerfil().getId());
					}

					if(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico() != null){
						Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getId());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getIndicadorExistenciaCavalete());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getNumeroLeituraInstalacao());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getNumeroSelo());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getIndicadorTrocaProtecao());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getIndicadorTrocaRegistro());
						Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico()
										.getLigacaoAgua());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getLigacaoAgua().getId());
						Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico()
										.getHidrometroLocalInstalacao());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao().getId());
						Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico()
										.getHidrometroProtecao());
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometroProtecao().getId());

						if(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometro() != null){
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico()
											.getHidrometro());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getNumero());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getDataAquisicao());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getAnoFabricacao());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getNumeroLeituraAcumulada());
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico()
											.getHidrometro().getHidrometroSituacao());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao().getDescricao());

						}

						if(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getMedicaoTipo() != null){
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico()
											.getMedicaoTipo());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getMedicaoTipo().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getHidrometroInstalacaoHistorico().getMedicaoTipo().getDescricao());
							// }

						}

						if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua() != null){
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getNumeroConsumoMinimoAgua());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getNumeroSeloCorte());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getNumeroSeloSupressao());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getUltimaAlteracao());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getNumeroLacre());

							// if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getDataLigacao()
							// != null){
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getDataLigacao());
							// }

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getHidrometroInstalacaoHistorico());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getIndicadorExistenciaCavalete());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroLeituraInstalacao());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getDataInstalacao());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getDataImplantacaoSistema());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getDataRetirada());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroSelo());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getIndicadorTrocaProtecao());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getIndicadorTrocaRegistro());
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getHidrometroInstalacaoHistorico().getLigacaoAgua());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getLigacaoAgua().getId());

								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao().getId());

								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getHidrometroInstalacaoHistorico().getHidrometroProtecao());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometroProtecao().getId());
								if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico()
												.getHidrometro() != null){
									Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
													.getHidrometroInstalacaoHistorico().getHidrometro());
									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getId());
									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getNumero());
									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getDataAquisicao());
									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getAnoFabricacao());
									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getNumeroLeituraAcumulada());

									Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
													.getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao());
									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao().getId());
									Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
													.getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao()
													.getDescricao());

									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro());
								}

								if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico()
												.getMedicaoTipo() != null){
									Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
													.getHidrometroInstalacaoHistorico().getMedicaoTipo());
									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getMedicaoTipo().getId());
									// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getMedicaoTipo().getDescricao());
								}
							}

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getSupressaoTipo() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getSupressaoTipo());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getSupressaoTipo().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getSupressaoTipo().getDescricao());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getSupressaoTipo().getIndicadorTotal());
							}

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getSupressaoMotivo() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getSupressaoMotivo());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getSupressaoMotivo().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getSupressaoMotivo().getDescricao());
							}

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaDiametro() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getLigacaoAguaDiametro());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaDiametro().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaDiametro().getDescricao());
							}

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaMaterial() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getLigacaoAguaMaterial());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaMaterial().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaMaterial().getDescricao());
							}

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaPerfil() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getLigacaoAguaPerfil());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaPerfil().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getLigacaoAguaPerfil().getDescricao());
							}

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getCorteTipo() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getCorteTipo());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getCorteTipo().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getCorteTipo().getDescricao());
							}

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getMotivoCorte() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getMotivoCorte());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getMotivoCorte().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getMotivoCorte().getDescricao());
							}

							if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getRamalLocalInstalacao() != null){
								Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
												.getRamalLocalInstalacao());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getRamalLocalInstalacao().getId());
								// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getRamalLocalInstalacao().getDescricao());
							}

						}

						if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAguaSituacao() != null){
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAguaSituacao());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAguaSituacao().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAguaSituacao().getDescricao());
						}

						if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto() != null){
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getIndicadorCaixaGordura());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getConsumoMinimo());

							// if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getDataLigacao()
							// != null){
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getDataLigacao());
							// }
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto()
											.getLigacaoEsgotoDiametro());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getLigacaoEsgotoDiametro().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getLigacaoEsgotoDiametro().getDescricao());

							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto()
											.getLigacaoEsgotoMaterial());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getLigacaoEsgotoMaterial().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getLigacaoEsgotoMaterial().getDescricao());

							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto()
											.getLigacaoEsgotoPerfil());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getLigacaoEsgotoPerfil().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getLigacaoEsgotoPerfil().getDescricao());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgoto().getPercentualAguaConsumidaColetada());

						}

						if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgotoSituacao() != null){
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgotoSituacao());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgotoSituacao().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getLigacaoEsgotoSituacao().getDescricao());
						}

						if(ordemServico.getRegistroAtendimento().getImovel().getConsumoTarifa() != null){
							Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getConsumoTarifa());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getConsumoTarifa().getId());
							// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getConsumoTarifa().getDescricao());
						}

						// if
						// (ordemServico.getRegistroAtendimento().getImovel().getNumeroPontosUtilizacao()
						// != null) {
						// Hibernate.initialize(ordemServico.getRegistroAtendimento().getImovel().getNumeroPontosUtilizacao());
						// }
					}
				}
			}

			Hibernate.initialize(ordemServico.getServicoTipo());
			// Hibernate.initialize(ordemServico.getServicoTipo().getId());
			// Hibernate.initialize(ordemServico.getServicoTipo().getDescricao());
			// Hibernate.initialize(ordemServico.getServicoTipo().getTempoMedioExecucao());
			// Hibernate.initialize(ordemServico.getServicoTipo().getIndicadorPermiteAlterarValor());
			// Hibernate.initialize(ordemServico.getServicoTipo().getIndicadorCobrarJuros());

			if(ordemServico.getServicoTipo().getDebitoTipo() != null){
				Hibernate.initialize(ordemServico.getServicoTipo().getDebitoTipo());
				// Hibernate.initialize(ordemServico.getServicoTipo().getDebitoTipo().getId());
				// Hibernate.initialize(ordemServico.getServicoTipo().getDebitoTipo().getDescricao());
			}

			if(ordemServico.getServicoTipo().getOrdemServicoLayout() != null){
				Hibernate.initialize(ordemServico.getServicoTipo().getOrdemServicoLayout());
				// Hibernate.initialize(ordemServico.getServicoTipo().getOrdemServicoLayout().getId());
				// Hibernate.initialize(ordemServico.getServicoTipo().getOrdemServicoLayout().getDescricao());
				// Hibernate.initialize(ordemServico.getServicoTipo().getOrdemServicoLayout().getNomeClasse());
				// if
				// (ordemServico.getServicoTipo().getOrdemServicoLayout().getIndicadorDoisPorPagina()
				// != null){
				// Hibernate.initialize(ordemServico.getServicoTipo().getOrdemServicoLayout().getIndicadorDoisPorPagina());
				// }
			}

			// Colocado por Raphael Rossiter em 23/09/2006
			if(ordemServico.getAtendimentoMotivoEncerramento() != null){
				Hibernate.initialize(ordemServico.getAtendimentoMotivoEncerramento());
				// Hibernate.initialize(ordemServico.getAtendimentoMotivoEncerramento().getId());
				// Hibernate.initialize(ordemServico.getAtendimentoMotivoEncerramento().getIndicadorExecucao());
				// Hibernate.initialize(ordemServico.getAtendimentoMotivoEncerramento().getDescricao());
			}

			if(ordemServico.getServicoTipoPrioridadeOriginal() != null){
				Hibernate.initialize(ordemServico.getServicoTipoPrioridadeOriginal());
				// Hibernate.initialize(ordemServico.getServicoTipoPrioridadeOriginal().getId());
			}

			if(ordemServico.getServicoTipoPrioridadeAtual() != null){
				Hibernate.initialize(ordemServico.getServicoTipoPrioridadeAtual());
				// Hibernate.initialize(ordemServico.getServicoTipoPrioridadeAtual().getId());
			}

			Hibernate.initialize(ordemServico.getIndicadorComercialAtualizado());

			// Imóvel da OS - Colocado por Raphael Rossiter em 16/01/2007
			if(ordemServico.getImovel() != null){
				Hibernate.initialize(ordemServico.getImovel());
				// Hibernate.initialize(ordemServico.getImovel().getId());
				Hibernate.initialize(ordemServico.getImovel().getLocalidade());
				// Hibernate.initialize(ordemServico.getImovel().getLocalidade().getId());

				Hibernate.initialize(ordemServico.getImovel().getSetorComercial());
				// Hibernate.initialize(ordemServico.getImovel().getSetorComercial().getId());
				// Hibernate.initialize(ordemServico.getImovel().getSetorComercial().getCodigo());
				//
				Hibernate.initialize(ordemServico.getImovel().getQuadra());
				// Hibernate.initialize(ordemServico.getImovel().getQuadra().getId());
				// Hibernate.initialize(ordemServico.getImovel().getQuadra().getNumeroQuadra());
				// Hibernate.initialize(ordemServico.getImovel().getQuadra().getIndicadorRedeAgua());
				// Hibernate.initialize(ordemServico.getImovel().getQuadra().getIndicadorRedeEsgoto());

				Hibernate.initialize(ordemServico.getImovel().getRota());

				if(ordemServico.getImovel().getImovelPerfil() != null){
					Hibernate.initialize(ordemServico.getImovel().getImovelPerfil());
					// Hibernate.initialize(ordemServico.getImovel().getImovelPerfil().getId());
				}

				// Hibernate.initialize(ordemServico.getImovel().getLote());
				// Hibernate.initialize(ordemServico.getImovel().getSubLote());

				// ==============================================================================================

				// Hibernate.initialize(ordemServico.getImovel().getIndicadorExclusao());
				// Hibernate.initialize(ordemServico.getImovel().getNumeroImovel());
				// Hibernate.initialize(ordemServico.getImovel().getUltimaAlteracao());

				if(ordemServico.getImovel().getHidrometroInstalacaoHistorico() != null){
					Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getId());

					Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getId());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getIndicadorExistenciaCavalete());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getNumeroLeituraInstalacao());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getNumeroSelo());

					Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getLigacaoAgua());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getLigacaoAgua().getId());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao().getId());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometroProtecao());
					// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometroProtecao().getId());

					if(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro() != null){

						Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getId());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getNumero());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getDataAquisicao());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getAnoFabricacao());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getNumeroLeituraAcumulada());

						Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro()
										.getHidrometroSituacao());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao().getId());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao().getDescricao());
					}

					if(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getMedicaoTipo() != null){

						Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getMedicaoTipo());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getMedicaoTipo().getId());
						// Hibernate.initialize(ordemServico.getImovel().getHidrometroInstalacaoHistorico().getMedicaoTipo().getDescricao());

					}

				}

				if(ordemServico.getImovel().getLigacaoAgua() != null){

					Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getId());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getNumeroConsumoMinimoAgua());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getNumeroSeloCorte());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getNumeroSeloSupressao());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getUltimaAlteracao());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getNumeroLacre());

					// if(ordemServico.getImovel().getLigacaoAgua().getDataLigacao() != null){
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getDataLigacao());
					// }

					if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){

						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getIndicadorExistenciaCavalete());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroLeituraInstalacao());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getDataInstalacao());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getDataImplantacaoSistema());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getDataRetirada());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroSelo());

						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getLigacaoAgua());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getLigacaoAgua().getId());

						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico()
										.getHidrometroLocalInstalacao());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometroLocalInstalacao().getId());

						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico()
										.getHidrometroProtecao());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometroProtecao().getId());

						if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro() != null){

							Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico()
											.getHidrometro());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getId());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getNumero());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getDataAquisicao());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getAnoFabricacao());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getNumeroLeituraAcumulada());

							Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico()
											.getHidrometro().getHidrometroSituacao());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao().getId());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getHidrometroSituacao().getDescricao());

						}

						if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getMedicaoTipo() != null){
							Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico()
											.getMedicaoTipo());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getMedicaoTipo().getId());
							// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getMedicaoTipo().getDescricao());

						}

					}

					if(ordemServico.getImovel().getLigacaoAgua().getSupressaoTipo() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getSupressaoTipo());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getSupressaoTipo().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getSupressaoTipo().getDescricao());
					}

					if(ordemServico.getImovel().getLigacaoAgua().getSupressaoMotivo() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getSupressaoMotivo());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getSupressaoMotivo().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getSupressaoMotivo().getDescricao());
					}

					if(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaDiametro() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaDiametro());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaDiametro().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaMaterial().getDescricao());
					}

					if(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaMaterial() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaMaterial());
					}

					if(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaPerfil() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaPerfil());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaPerfil().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getLigacaoAguaPerfil().getDescricao());
					}

					if(ordemServico.getImovel().getLigacaoAgua().getCorteTipo() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getCorteTipo());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getCorteTipo().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getCorteTipo().getDescricao());
					}

					if(ordemServico.getImovel().getLigacaoAgua().getCorteTipo() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getCorteTipo());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getCorteTipo().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getCorteTipo().getDescricao());
					}

					if(ordemServico.getImovel().getLigacaoAgua().getMotivoCorte() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getMotivoCorte());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getMotivoCorte().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getMotivoCorte().getDescricao());
					}

					if(ordemServico.getImovel().getLigacaoAgua().getRamalLocalInstalacao() != null){
						Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getRamalLocalInstalacao());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getRamalLocalInstalacao().getId());
						// Hibernate.initialize(ordemServico.getImovel().getLigacaoAgua().getRamalLocalInstalacao().getDescricao());

					}

				}

				if(ordemServico.getImovel().getLigacaoAguaSituacao() != null){
					Hibernate.initialize(ordemServico.getImovel().getLigacaoAguaSituacao());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAguaSituacao().getId());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoAguaSituacao().getDescricao());

				}

				if(ordemServico.getImovel().getLigacaoEsgoto() != null){
					Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getId());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getIndicadorCaixaGordura());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getConsumoMinimo());

					// if(ordemServico.getImovel().getLigacaoEsgoto().getDataLigacao() != null){
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getDataLigacao());
					// }
					Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoDiametro());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoDiametro().getId());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoDiametro().getDescricao());

					Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoMaterial());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoMaterial().getId());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoMaterial().getDescricao());

					Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoPerfil());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoPerfil().getId());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getLigacaoEsgotoPerfil().getDescricao());

					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgoto().getPercentualAguaConsumidaColetada());

				}

				if(ordemServico.getImovel().getLigacaoEsgotoSituacao() != null){
					Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgotoSituacao());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgotoSituacao().getId());
					// Hibernate.initialize(ordemServico.getImovel().getLigacaoEsgotoSituacao().getDescricao());
				}

				if(ordemServico.getImovel().getConsumoTarifa() != null){
					Hibernate.initialize(ordemServico.getImovel().getConsumoTarifa());
					// Hibernate.initialize(ordemServico.getImovel().getConsumoTarifa().getId());
					// Hibernate.initialize(ordemServico.getImovel().getConsumoTarifa().getDescricao());
				}

			}

			if(ordemServico.getCobrancaAcaoAtividadeCronograma() != null){
				Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeCronograma());
				// Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeCronograma().getId());

				if(ordemServico.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma() != null){
					Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma());
					// Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma().getId());
					if(ordemServico.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma().getCobrancaAcao() != null){
						Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma()
										.getCobrancaAcao());
						// Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma().getCobrancaAcao().getId());
						// if
						// (ordemServico.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma().getCobrancaAcao().getNumeroDiasValidade()
						// != null){
						// Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma().getCobrancaAcao().getNumeroDiasValidade());
						// }

					}

				}
			}

			if(ordemServico.getCobrancaAcaoAtividadeComando() != null){
				Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeComando());
				// Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeComando().getId());

				if(ordemServico.getCobrancaAcaoAtividadeComando().getCobrancaAcao() != null){
					Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeComando().getCobrancaAcao());
					// Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeComando().getCobrancaAcao().getId());
					// if
					// (ordemServico.getCobrancaAcaoAtividadeComando().getCobrancaAcao().getNumeroDiasValidade()
					// != null){
					// Hibernate.initialize(ordemServico.getCobrancaAcaoAtividadeComando().getCobrancaAcao().getNumeroDiasValidade());
					// }

				}
			}

			if(ordemServico != null && ordemServico.getImovel() != null){
				if(ordemServico.getImovel().getLogradouroBairro() != null){
					Hibernate.initialize(ordemServico.getImovel().getLogradouroBairro());
					// Hibernate.initialize(ordemServico.getImovel().getLogradouroBairro().getId());
				}
			}
		}
	}

	/**
	 * Recupera uma OrdemServicoProgramacao
	 * 
	 * @author Hebert Falcão
	 * @date 19/07/2011
	 * @return OrdemServicoProgramacao
	 */
	public OrdemServicoProgramacao pesquisarOrdemServicoProgramacao(Integer idUnidadeOrganizacional, Integer idOrdemServico,
					Integer idEquipe, Date dataRoteiro) throws ErroRepositorioException{

		OrdemServicoProgramacao retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select ospg ");
			consulta.append("from OrdemServicoProgramacao ospg ");
			consulta.append("inner join ospg.programacaoRoteiro pgrt ");
			consulta.append("where pgrt.unidadeOrganizacional.id = :idUnidadeOrganizacional ");
			consulta.append("  and ospg.ordemServico.id = :idOrdemServico ");
			consulta.append("  and ospg.equipe.id = :idEquipe ");
			consulta.append("  and pgrt.dataRoteiro = :dataRoteiro ");

			retorno = (OrdemServicoProgramacao) session.createQuery(consulta.toString())
							.setInteger("idUnidadeOrganizacional", idUnidadeOrganizacional).setInteger("idOrdemServico", idOrdemServico)
							.setInteger("idEquipe", idEquipe).setDate("dataRoteiro", dataRoteiro).setMaxResults(1).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3040] Obter Unidade Atual da OS
	 * Este método permite obter a unidade atual de uma ordem de serviço
	 * 
	 * @author Anderson Italo
	 * @date 08/02/2012
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeAtualOrdemServico(Integer idOrdemServico) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;
		Object retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT tramite.unidadeOrganizacionalDestino ");
			consulta.append("FROM Tramite tramite WHERE tramite.ordemServico.id = :idOrdemServico ");
			consulta.append("AND tramite.dataTramite = (SELECT max(tram.dataTramite) FROM Tramite tram ");
			consulta.append("WHERE tram.ordemServico.id = tramite.ordemServico.id)");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idOrdemServico", idOrdemServico).setMaxResults(1)
							.uniqueResult();

			if(retornoConsulta != null){

				retorno = (UnidadeOrganizacional) retornoConsulta;
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0620] - Obter Indicador de Autorização para Manutenção da OS
	 * Este método permite obter a unidade de geração da ordem de serviço
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2012
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeGeracaoOrdemServico(Integer idOrdemServico) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;
		Object retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT unidade.unidadeOrganizacional ");
			consulta.append("FROM OrdemServicoUnidade unidade WHERE unidade.atendimentoRelacaoTipo.id = ");
			consulta.append(AtendimentoRelacaoTipo.ABRIR_REGISTRAR.toString() + " ");
			consulta.append("AND unidade.ordemServico.id = :idOrdemServico ");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idOrdemServico", idOrdemServico).setMaxResults(1)
							.uniqueResult();

			if(retornoConsulta != null){

				retorno = (UnidadeOrganizacional) retornoConsulta;
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0620] - Obter Indicador de Autorização para Manutenção da OS
	 * Este método permite obter as empresas de cobranca das rotas de uma localidade
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2012
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterIdEmpresasCobrancaRotasPorLocalidade(Integer idLocalidade) throws ErroRepositorioException{

		Collection<Integer> retorno = null;
		Collection retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT distinct rota.empresaCobranca.id ");
			consulta.append("FROM Rota rota INNER JOIN rota.setorComercial setor ");
			consulta.append("WHERE setor.localidade.id = :idLocalidade ");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idLocalidade", idLocalidade).list();

			if(retornoConsulta != null){

				retorno = (Collection<Integer>) retornoConsulta;
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0620] - Obter Indicador de Autorização para Manutenção da OS
	 * Este método permite obter a unidade superior por unidade
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2012
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeSuperiorPorUnidade(Integer idUnidade) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;
		Object retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT superior ");
			consulta.append("FROM UnidadeOrganizacional unidade ");
			consulta.append("INNER JOIN unidade.unidadeSuperior superior ");
			consulta.append("WHERE unidade.id = :idUnidade ");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idUnidade", idUnidade).setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){

				retorno = (UnidadeOrganizacional) retornoConsulta;
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3039] Tramitar Ordem de Serviço
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param idOS
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Tramite pesquisarUltimaDataTramiteOS(Integer idOS) throws ErroRepositorioException{

		Tramite tramite = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT tram " + " FROM Tramite tram " + " WHERE tram.ordemServico.id = :idOS " + " ORDER BY tram.dataTramite desc";

			tramite = (Tramite) session.createQuery(consulta).setInteger("idOS", idOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return tramite;
	}

	/**
	 * [UC3039] Tramitar Ordem de Serviço
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param idOS
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date verificarConcorrenciaOS(Integer idOS) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = " select os.ultimaAlteracao" + " from OrdemServico os" + " where os.id  = :idOS";

			retorno = (Date) session.createQuery(consulta).setInteger("idOS", idOS).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC3036] Obter Unidade Tramite Ordem de Serviço
	 * 
	 * @author Hugo Lima
	 * @date 06/12/2011
	 * @param idServicoTipoTramite
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param idBairro
	 * @param idUnidadeOrigem
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarUnidadeTramiteOS(Integer idServicoTipoTramite, Integer idLocalidade, Integer idSetorComercial,
					Integer idBairro, Integer idUnidadeOrigem, Short indicadorPrimeiroTramite) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		Query query = null;

		StringBuffer consulta = new StringBuffer("");

		Map parameters = new HashMap();

		try{
			consulta.append(" select servicoTipoTramite.unidadeOrganizacionalDestino.id from ServicoTipoTramite servicoTipoTramite ");
			consulta.append(" where ");

			consulta.append(" servicoTipoTramite.servicoTipo.id = :idServicoTipoTramite ");

			consulta.append(" AND servicoTipoTramite.indicadorPrimeiroTramite = :indicadorPrimeiroTramite ");

			parameters.put("idServicoTipoTramite", idServicoTipoTramite);

			parameters.put("indicadorPrimeiroTramite", ConstantesSistema.SIM);

			if(!Util.isVazioOuBranco(idLocalidade) && !Util.isVazioOuBranco(idSetorComercial)){
				// Informou Localidade e Setor Comercial
				consulta.append(" and (servicoTipoTramite.localidade.id = :idLocalidade or servicoTipoTramite.localidade.id is null) ");
				parameters.put("idLocalidade", idLocalidade);

				consulta.append(" and (servicoTipoTramite.setorComercial.id = :idSetorComercial or servicoTipoTramite.setorComercial.id is null) ");
				parameters.put("idSetorComercial", idSetorComercial);
			}else if(!Util.isVazioOuBranco(idLocalidade)){
				// Informou só Localidade
				consulta.append(" and (servicoTipoTramite.localidade.id = :idLocalidade or servicoTipoTramite.localidade.id is null) ");
				parameters.put("idLocalidade", idLocalidade);
			}

			if(!Util.isVazioOuBranco(idBairro)){
				consulta.append(" and (servicoTipoTramite.bairro.id = :idBairro or servicoTipoTramite.bairro.id is null) ");
				parameters.put("idBairro", idBairro);
			}

			if(!Util.isVazioOuBranco(idUnidadeOrigem)){
				consulta.append(" and servicoTipoTramite.unidadeOrganizacionalOrigem.id = :idUnidadeOrigem  ");
				parameters.put("idUnidadeOrigem", idUnidadeOrigem);
			}else{
				consulta.append(" and servicoTipoTramite.indicadorPrimeiroTramite = 1 ");
			}

			if(!Util.isVazioOuBranco(indicadorPrimeiroTramite)){
				consulta.append(" and servicoTipoTramite.indicadorPrimeiroTramite = 1 ");
			}

			consulta.append(" order by  ");
			consulta.append(" servicoTipoTramite.localidade.id, ");
			consulta.append(" servicoTipoTramite.setorComercial.id, ");
			consulta.append(" servicoTipoTramite.bairro.id, ");
			consulta.append(" servicoTipoTramite.sistemaAbastecimento.id, ");
			consulta.append(" servicoTipoTramite.distritoOperacional.id, ");
			consulta.append(" servicoTipoTramite.zonaAbastecimento.id, ");
			consulta.append(" servicoTipoTramite.setorAbastecimento.id, ");
			consulta.append(" servicoTipoTramite.sistemaEsgoto.id, ");
			consulta.append(" servicoTipoTramite.subsistemaEsgoto.id, ");
			consulta.append(" servicoTipoTramite.bacia.id, ");
			consulta.append(" servicoTipoTramite.subBacia.id, ");
			consulta.append(" servicoTipoTramite.unidadeOrganizacionalOrigem.id desc ");

			query = session.createQuery(consulta.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof String){
					String string = (String) parameters.get(key);
					query.setParameter(key, string);
				}else if(parameters.get(key) instanceof Integer){
					Integer integer = (Integer) parameters.get(key);
					query.setParameter(key, integer);
				}else if(parameters.get(key) instanceof Short){
					Short parametroShort = (Short) parameters.get(key);
					query.setParameter(key, parametroShort);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			Collection<Integer> colecaoIds = query.list();
			if(colecaoIds != null && colecaoIds.size() > 0){
				Iterator interatorIds = colecaoIds.iterator();

				retorno = (Integer) interatorIds.next();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Consultar Id de Serviço Tipo com Geração Automática filtrando pelo Id da Solicitação Tipo
	 * Especificação
	 * 
	 * @author Hebet Falcão
	 * @date 17/02/2012
	 */
	public Collection<Integer> consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(Integer idSolicitacaoTipoEspecificacao)
					throws ErroRepositorioException{

		Collection<Integer> colecaoIdServicoTipo = new ArrayList<Integer>();

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT DISTINCT epsv.comp_id.idServicoTipo ");
			consulta.append("FROM EspecificacaoServicoTipo epsv ");
			consulta.append("WHERE epsv.indicadorGeracaoAutomatica = :indicadorGeracaoAutomatica ");
			consulta.append("  AND epsv.solicitacaoTipoEspecificacao.id = :idSolicitacaoTipoEspecificacao ");

			colecaoIdServicoTipo = session.createQuery(consulta.toString()).setInteger("indicadorGeracaoAutomatica", ConstantesSistema.SIM)
							.setInteger("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return colecaoIdServicoTipo;
	}

	/**
	 * [UC0430] Gerar Ordem de Serviço
	 * [FS0010] Verificar restrição de emissão da Ordem de Serviço
	 * 
	 * @author Hugo Lima
	 * @date 07/08/2012
	 * @param idImovel
	 * @param idServicoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date obterEncerramentoOrdemServicoTipoServicoRestricaoPrazoNaoExpirado(Integer idImovel, Integer idServicoTipo)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Query query = null;

		Date retorno = null;

		StringBuffer hql = new StringBuffer();

		try{

			hql.append(" select max(os.dataEncerramento) ");
			hql.append(" from OrdemServico os ");
			hql.append(" inner join os.imovel im ");
			hql.append(" inner join os.servicoTipo st ");
			hql.append(" where os.situacao = :situacao ");
			hql.append(" and im.id = :idImovel ");
			hql.append(" and st.id = :idServicoTipo ");

			query = session.createQuery(hql.toString()).setShort("situacao", OrdemServico.SITUACAO_ENCERRADO)
							.setInteger("idImovel", idImovel).setInteger("idServicoTipo", idServicoTipo);

			retorno = (Date) query.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3063] Efetuar Instalação/Substituição de Registro Magnético
	 * 
	 * @date 28/08/2012
	 * @author Leonardo Angelim
	 */
	public OrdemServico recuperaOrdemServicoPorId(Integer idOS) throws ErroRepositorioException{

		OrdemServico ordemServico = null;

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer hql = new StringBuffer();

			hql.append("select os ");
			hql.append("from OrdemServico os ");
			hql.append("inner join fetch os.servicoTipo svtp ");
			hql.append("left join os.imovel imov ");
			hql.append("left join os.registroAtendimento rgat ");
			hql.append("where os.id = :idOS ");

			Query query = session.createQuery(hql.toString());

			query.setInteger("idOS", idOS);

			ordemServico = (OrdemServico) query.setMaxResults(1).uniqueResult();

			inicializarObjetosOS(ordemServico);
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return ordemServico;
	}

	/**
	 * Recupera o tipo de relação do cliente e imóvel para exibição no relatório de ordem de
	 * serviço.
	 * 
	 * @param idOSLayout
	 * @date 26/12/2012
	 * @author Ítalo Almeida
	 */
	public String recuperaRelacaoOSClienteImovel(Integer idOSLayout) throws ErroRepositorioException{

		String tipoRelacao = null;

		Session session = HibernateUtil.getSession();

		try{
			String sql;

			sql = "select crtp.crtp_dsclienterelacaotipo " + "from ordem_servico_layout osla "
							+ "inner join cliente_relacao_tipo crtp on osla.crtp_id = crtp.crtp_id " + "where osla.osla_id = :idOSLayout";

			tipoRelacao = (String) session.createSQLQuery(sql).setInteger("idOSLayout", idOSLayout).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return tipoRelacao;
	}

	public Collection<Object[]> filtrarOrdemServicoWebService(FiltrarOrdemServicoHelper filtro) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		SQLQuery query = null;

		try{

			StringBuilder sql = new StringBuilder("Select distinct ");
			sql.append("  os.ORSE_ID numeroOs,");
			sql.append("  os.STPR_IDATUAL as codigoPrioridadeExecucao,");
			sql.append("  os.SVTP_ID as codigoServicoOS, ");
			sql.append("  os.ORSE_CDSITUACAO as codigoSituacaoOS, ");
			sql.append("  ra.RGAT_NNCOORDENADALESTE as coordenadaLeste,  ");
			sql.append("  ra.RGAT_NNCOORDENADANORTE as coordenadaNorte, ");
			sql.append("  st.SVTP_DSSERVICOTIPO as descricaoServicoTipo, ");
			sql.append("  loca.LOCA_ID as idLocalidade,  ");
			sql.append("  sc.stcm_cdsetorcomercial as codSetorComercial,  ");
			sql.append("  quadra.qdra_nnquadra as numeroQuadra, ");
			sql.append("  im.IMOV_NNLOTE as numeroLote, ");
			sql.append("  im.IMOV_NNSUBLOTE as numeroSubLote ");

			sql.append(" from ORDEM_SERVICO os  ");
			sql.append(" LEFT JOIN REGISTRO_ATENDIMENTO ra on os.RGAT_ID = ra.RGAT_ID ");
			sql.append(" LEFT JOIN SERVICO_TIPO st on st.SVTP_ID = os.SVTP_ID ");
			sql.append(" LEFT JOIN localidade loca on ra.loca_id = loca.loca_id  ");
			sql.append(" LEFT JOIN imovel im on ra.imov_id = im.imov_id  ");
			sql.append(" LEFT JOIN SETOR_COMERCIAL sc on sc.stcm_id = im.stcm_id  ");
			sql.append(" LEFT JOIN QUADRA quadra on quadra.qdra_id = im.qdra_id  ");

			if(Util.isNaoNuloBrancoZero(filtro.getCodigoUnidadeNegocio()) || Util.isNaoNuloBrancoZero(filtro.getUnidadeExecutora())){
				sql.append(" LEFT JOIN tramite tram on ra.rgat_id = tram.rgat_id ");
			}

			sql.append("WHERE");

			if(Util.isNaoNuloBrancoZero(filtro.getNumeroOS())){
				sql.append(" os.ORSE_ID = " + filtro.getNumeroOS());
			}

			if(Util.isNaoNuloBrancoZero(filtro.getCodigoServicoOs())){
				sql.append(" os.SVTP_ID = " + filtro.getCodigoServicoOs());
			}

			if(Util.isNaoNuloBrancoZero(filtro.getDataInicial()) && Util.isNaoNuloBrancoZero(filtro.getDataFinal())){

				sql.append(" ra.rgat_tmregistroatendimento >= :dataInicial   ");
				sql.append(" AND ra.rgat_tmregistroatendimento <= :dataFinal   ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getCodigoUnidadeNegocio())){
				sql.append(" AND ( tram.unid_iddestino = " + filtro.getCodigoUnidadeNegocio());
				sql.append("  		AND  tram.tram_id in (select max(tr.tram_id) from tramite tr  where tr.rgat_id = ra.rgat_id) ");
				sql.append("     ) ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getUnidadeExecutora())){
				sql.append(" AND ( tram.unid_iddestino = " + filtro.getUnidadeExecutora());
				sql.append("  		AND  tram.tram_id in (select max(tr.tram_id) from tramite tr  where tr.rgat_id = ra.rgat_id) ");
				sql.append("     ) ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getColecaoSituacoes())){
				sql.append(" and os.ORSE_CDSITUACAO in (:idsColecaoSituacao)  ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getColecaoServicos())){
				sql.append(" and os.SVTP_ID in (:idsColecaoServicos)  ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getColecaoPrioridades())){
				sql.append(" and os.STPR_IDORIGINAL in (:idsColecaoPrioridades)  ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getCoordenadaNorteAlta()) && Util.isNaoNuloBrancoZero(filtro.getCoordenadaNorteBaixa())){
				sql.append(" and ra.rgat_nncoordenadanorte BETWEEN (:norteBaixa) AND (:norteAlta)  ");
			}
			if(Util.isNaoNuloBrancoZero(filtro.getCoordenadaLesteDireita())
							&& Util.isNaoNuloBrancoZero(filtro.getCoordenadaLesteEsquerda())){
				sql.append(" and ra.rgat_nncoordenadaleste BETWEEN (:lesteEsquerda) AND (:lesteDireita)  ");
			}

			query = session.createSQLQuery(sql.toString());

			if(Util.isNaoNuloBrancoZero(filtro.getDataInicial()) && Util.isNaoNuloBrancoZero(filtro.getDataFinal())){
				query.setTimestamp("dataInicial", Util.formatarDataInicial(filtro.getDataInicial()));
				query.setTimestamp("dataFinal", Util.formatarDataFinal(filtro.getDataFinal()));
			}
			if(Util.isNaoNuloBrancoZero(filtro.getCoordenadaNorteAlta()) && Util.isNaoNuloBrancoZero(filtro.getCoordenadaNorteBaixa())){
				query.setBigDecimal("norteBaixa", filtro.getCoordenadaNorteBaixa());
				query.setBigDecimal("norteAlta", filtro.getCoordenadaNorteAlta());
			}

			if(Util.isNaoNuloBrancoZero(filtro.getCoordenadaLesteDireita())
							&& Util.isNaoNuloBrancoZero(filtro.getCoordenadaLesteEsquerda())){
				query.setBigDecimal("lesteEsquerda", filtro.getCoordenadaLesteEsquerda());
				query.setBigDecimal("lesteDireita", filtro.getCoordenadaLesteDireita());
			}

			if(Util.isNaoNuloBrancoZero(filtro.getColecaoSituacoes())){
				query.setParameterList("idsColecaoSituacao", filtro.getColecaoSituacoes());
			}

			if(Util.isNaoNuloBrancoZero(filtro.getColecaoServicos())){
				query.setParameterList("idsColecaoServicos", filtro.getColecaoServicos());
			}

			if(Util.isNaoNuloBrancoZero(filtro.getColecaoPrioridades())){
				query.setParameterList("idsColecaoPrioridades", filtro.getColecaoPrioridades());
			}

			query.addScalar("numeroOs", Hibernate.INTEGER);
			query.addScalar("codigoPrioridadeExecucao", Hibernate.INTEGER);
			query.addScalar("codigoServicoOS", Hibernate.INTEGER);
			query.addScalar("codigoSituacaoOS", Hibernate.INTEGER);
			query.addScalar("coordenadaLeste", Hibernate.BIG_DECIMAL);
			query.addScalar("coordenadaNorte", Hibernate.BIG_DECIMAL);
			query.addScalar("descricaoServicoTipo", Hibernate.STRING);
			query.addScalar("idLocalidade", Hibernate.INTEGER);
			query.addScalar("codSetorComercial", Hibernate.INTEGER);
			query.addScalar("numeroQuadra", Hibernate.INTEGER);
			query.addScalar("numeroLote", Hibernate.SHORT);
			query.addScalar("numeroSubLote", Hibernate.SHORT);

			retornoConsulta = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	public Object[] filtrarOrdemServicoDetalhesWebService(FiltrarOrdemServicoHelper filtro) throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		SQLQuery query = null;

		try{

			StringBuilder sql = new StringBuilder("Select distinct ");
			sql.append("  os.ORSE_ID numeroOs, ");
			sql.append("  ra.RGAT_ID as numeroRa, ");
			sql.append("  bairro.BAIR_NMBAIRRO as nomeBairro, ");
			sql.append("  ra.RGAT_NNCOORDENADALESTE as coordenadaLeste,   ");
			sql.append("  ra.RGAT_NNCOORDENADANORTE as coordenadaNorte, ");
			sql.append("  os.ORSE_TMENCERRAMENTO as dataExecucao, ");
			sql.append("  tram.TRAM_TMTRAMITE as dataTramite, ");
			sql.append("  st.SVTP_DSSERVICOTIPO as descricaoServicoTipo, ");
			sql.append("  os.SVTP_ID as codigoServicoOS, ");
			sql.append("  os.ORSE_CDSITUACAO as codigoSituacaoOS, ");
			sql.append("  os.CBDO_ID as documentoCobranca, ");
			sql.append("  sf.SOFO_CDDDD as DDD, ");
			sql.append("  sf.SOFO_NNFONE as telefoneContato, ");
			sql.append("  loca.LOCA_ID as idLocalidade,   ");
			sql.append("  sc.stcm_cdsetorcomercial as codSetorComercial,   ");
			sql.append("  quadra.qdra_nnquadra as numeroQuadra,  ");
			sql.append("  im.IMOV_NNLOTE as numeroLote,  ");
			sql.append("  im.IMOV_NNSUBLOTE as numeroSubLote, ");
			sql.append("  loca.LOCA_NMLOCALIDADE as nomeLocalidade,	 ");
			sql.append("  os.IMOV_ID as matricula, ");
			sql.append("  muni.MUNI_NMMUNICIPIO as municipio, ");
			sql.append("  (select cliente.CLIE_NMCLIENTE from Cliente cliente where cliente.CLIE_ID = ras.CLIE_ID and RASO_ICSOLICITANTEPRINCIPAL = 1 ) as nomeCliente,  ");
			sql.append("  ra.RGAT_DSOBSERVACAO as observacao, ");
			sql.append("  ra.RGAT_DSPONTOREFERENCIA as pontoReferencia, ");
			sql.append("  ame.AMEN_DSMOTIVOENCERRAMENTO as motivoEncerramento, ");
			sql.append("  os.ORSE_DSPARECERENCERRAMENTO as parecer ");
			sql.append("   ");
			sql.append("  from ORDEM_SERVICO os  ");
			sql.append("  LEFT JOIN REGISTRO_ATENDIMENTO ra on os.RGAT_ID = ra.RGAT_ID ");
			sql.append("  LEFT JOIN SERVICO_TIPO st on st.SVTP_ID = os.SVTP_ID  ");
			sql.append("  LEFT JOIN localidade loca on ra.loca_id = loca.loca_id  ");
			sql.append("  LEFT JOIN imovel im on ra.imov_id = im.imov_id  ");
			sql.append("  LEFT JOIN SETOR_COMERCIAL sc on sc.stcm_id = ra.stcm_id  ");
			sql.append("  LEFT JOIN QUADRA quadra on quadra.qdra_id = ra.qdra_id  ");
			sql.append("  left join BAIRRO_AREA bairroArea on bairroArea.BRAR_ID = ra.BRAR_ID ");
			sql.append("  left JOIN BAIRRO bairro on bairro.BAIR_ID = bairroArea.BAIR_ID ");
			sql.append("  left join MUNICIPIO muni on muni.MUNI_ID = bairro.MUNI_ID ");
			sql.append("  LEFT JOIN tramite tram on ra.rgat_id = tram.rgat_id  ");
			sql.append("  left join REGISTRO_ATENDIMENTO_SOLCTT ras on ras.RGAT_ID = ra.RGAT_ID ");
			sql.append("  left join SOLICITANTE_FONE sf on sf.RASO_ID = ras.RASO_ID  ");
			sql.append("  left join ATENDIMENTO_MOTIVO_ENCRTO ame on ame.AMEN_ID = os.AMEN_ID ");

			sql.append("WHERE os.ORSE_ID = " + filtro.getNumeroOS());

			if(Util.isNaoNuloBrancoZero(filtro.getCodigoServicoOs())){
				sql.append(" AND os.SVTP_ID = " + filtro.getCodigoServicoOs());
			}

			if(filtro.isPesquisarMaxTramite()){
				sql.append(" AND  (tram.TRAM_ID = (select MAX(TRAM_ID) FROM TRAMITE t where t.RGAT_ID = ra.RGAT_ID))");
			}

			query = session.createSQLQuery(sql.toString());

			query.addScalar("numeroOs", Hibernate.INTEGER);
			query.addScalar("numeroRa", Hibernate.INTEGER);
			query.addScalar("nomeBairro", Hibernate.STRING);
			query.addScalar("coordenadaLeste", Hibernate.BIG_DECIMAL);
			query.addScalar("coordenadaNorte", Hibernate.BIG_DECIMAL);
			query.addScalar("dataExecucao", Hibernate.DATE);
			query.addScalar("dataTramite", Hibernate.DATE);
			query.addScalar("descricaoServicoTipo", Hibernate.STRING);
			query.addScalar("codigoServicoOS", Hibernate.INTEGER);
			query.addScalar("codigoSituacaoOS", Hibernate.INTEGER);
			query.addScalar("documentoCobranca", Hibernate.INTEGER);
			query.addScalar("DDD", Hibernate.SHORT);
			query.addScalar("telefoneContato", Hibernate.STRING);
			query.addScalar("idLocalidade", Hibernate.INTEGER);
			query.addScalar("codSetorComercial", Hibernate.INTEGER);
			query.addScalar("numeroQuadra", Hibernate.INTEGER);
			query.addScalar("numeroLote", Hibernate.SHORT);
			query.addScalar("numeroSubLote", Hibernate.SHORT);
			query.addScalar("nomeLocalidade", Hibernate.STRING);
			query.addScalar("matricula", Hibernate.INTEGER);
			query.addScalar("municipio", Hibernate.STRING);
			query.addScalar("nomeCliente", Hibernate.STRING);
			query.addScalar("observacao", Hibernate.STRING);
			query.addScalar("pontoReferencia", Hibernate.STRING);
			query.addScalar("motivoEncerramento", Hibernate.STRING);
			query.addScalar("parecer", Hibernate.STRING);

			retornoConsulta = (Object[]) query.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	public Collection filtrarComandoOSSeletiva(OrdemServicoSeletivaComandoHelper filtro) throws ErroRepositorioException{

		// cria a coleçãoque vai armazenar os comandos de OsSeletiva
		Collection<OsSeletivaComando> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		Query query = null;
		Map parameters = new HashMap();

		int numeroPagina = filtro.getNumeroPaginasPesquisa();

		try{

			StringBuilder hql = new StringBuilder("select Distinct osSC ");
			hql.append(" from OsSeletivaComando osSC ");
			hql.append(" LEFT JOIN FETCH osSC.osSeletivaComandoLocalGeo ossclg ");
			hql.append(" LEFT JOIN FETCH osSC.osSeletivaComandoImovelPerfil osscip ");
			hql.append(" LEFT JOIN FETCH osSC.osSeletivaComandoCategoriaSubcategoria osccs ");
			hql.append(" LEFT JOIN FETCH osSC.osSeletivaComandoLigacaoAgua osscla ");
			hql.append(" LEFT JOIN FETCH osSC.osSeletivaComandoLigacaoEsgoto osscle ");
			hql.append(" LEFT JOIN FETCH osSC.empresa emp ");
			hql.append(" LEFT JOIN FETCH osSC.servicoTipo svtp ");
			hql.append(" LEFT JOIN FETCH osSC.faturamentoGrupo fat ");

			hql.append("  Where 1=1 ");

			if(filtro.getTitulo() != null && filtro.getTipoPesquisa() != null
							&& filtro.getTipoPesquisa().equals(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString())){

				hql.append(" AND osSC.descricaoTitulo like '" + filtro.getTitulo() + "%' ");

			}else if(filtro.getTitulo() != null && filtro.getTipoPesquisa() != null
							&& filtro.getTipoPesquisa().equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				hql.append(" AND osSC.descricaoTitulo like '%" + filtro.getTitulo() + "%'  ");

			}
			if(filtro.getFirma() != null){

				hql.append(" AND emp.id in (:colecaoEmpresa)  ");

				parameters.put("colecaoEmpresa", filtro.getFirma());

			}
			if(filtro.getServicoTipo() != null){

				hql.append(" AND svtp.id in (:colecaoServicoTipo)  ");
				parameters.put("colecaoServicoTipo", filtro.getServicoTipo());

			}
			if(filtro.getDataComandoInicial() != null && filtro.getDataComandoFinal() != null){

				String dataInicial = filtro.getDataComandoInicial();
				String dataFinal = filtro.getDataComandoFinal();

				dataInicial = dataInicial.substring(6, 10) + "-" + dataInicial.substring(3, 5) + "-" + dataInicial.substring(0, 2);
				dataFinal = dataFinal.substring(6, 10) + "-" + dataFinal.substring(3, 5) + "-" + dataFinal.substring(0, 2);

				hql.append(" AND  osSC.tempoComando >= to_timestamp('" + dataInicial
								+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF')  ");
				hql.append(" AND  osSC.tempoComando <= to_timestamp('" + dataFinal + " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF')  ");
			}
			if(filtro.getDataRealizacaoComandoInicial() != null && filtro.getDataRealizacaoComandoFinal() != null){

				String dataInicial = filtro.getDataRealizacaoComandoInicial();
				String dataFinal = filtro.getDataRealizacaoComandoFinal();

				dataInicial = dataInicial.substring(6, 10) + "-" + dataInicial.substring(3, 5) + "-" + dataInicial.substring(0, 2);
				dataFinal = dataFinal.substring(6, 10) + "-" + dataFinal.substring(3, 5) + "-" + dataFinal.substring(0, 2);

				hql.append(" AND  osSC.tempoRealizacao >= to_timestamp('" + dataInicial
								+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF')  ");
				hql.append(" AND  osSC.tempoRealizacao <= to_timestamp('" + dataFinal
								+ " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF')  ");

			}
			if(filtro.getSituacaoComando() != null && filtro.getSituacaoComando().equals(ConstantesSistema.SIM)){

				hql.append(" AND osSC.tempoRealizacao IS NOT NULL  ");

			}
			if(filtro.getSituacaoComando() != null && filtro.getSituacaoComando().equals(ConstantesSistema.NAO)){

				hql.append(" AND osSC.tempoRealizacao IS NULL  ");

			}
			if(filtro.getElo() != null){

				parameters.put("colecaoElo", filtro.getElo());
				hql.append(" AND osSC.codigoElo.id in (:colecaoElo)  ");

			}
			if(filtro.getFaturamentoGrupo() != null){

				parameters.put("colecaoFaturamentoGrupo", filtro.getFaturamentoGrupo());
				hql.append(" AND fat.id in (:colecaoFaturamentoGrupo)  ");

			}
			if(filtro.getGerenciaRegional() != null){

				parameters.put("colecaoGereciaRegioanl", filtro.getGerenciaRegional());
				hql.append(" AND ossclg.gerenciaRegional.id in (:colecaoGereciaRegioanl)  ");

			}
			if(filtro.getLocalidade() != null){

				parameters.put("colecaoLocalidade", filtro.getLocalidade());
				hql.append(" AND ossclg.localidade.id in (:colecaoLocalidade)  ");

			}
			if(filtro.getSetor() != null){

				parameters.put("colecaoSetor", filtro.getSetor());
				hql.append(" AND ossclg.setorComercial.id in (:colecaoSetor)  ");

			}
			if(filtro.getQuadra() != null){

				parameters.put("colecaoQuadra", filtro.getQuadra());
				hql.append(" AND ossclg.quadra.id in (:colecaoQuadra)  ");
			}
			if(filtro.getRota() != null){

				parameters.put("colecaoRota", filtro.getRota());
				hql.append(" AND ossclg.rota.id in (:colecaoRota)  ");
			}
			if(filtro.getLote() != null){

				parameters.put("colecaoLote", filtro.getLote());
				hql.append(" AND ossclg.numeroLote in (:colecaoLote)  ");
			}
			if(filtro.getBairro() != null){

				parameters.put("colecaoBairro", filtro.getBairro());
				hql.append(" AND ossclg.bairro.id in (:colecaoBairro)  ");
			}
			if(filtro.getLogradouro() != null){

				parameters.put("colecaoLogradouro", filtro.getLogradouro());
				hql.append(" AND ossclg.logradouro.id in (:colecaoLogradouro)  ");
			}
			if(filtro.getPerfilImovel() != null){

				parameters.put("colecaoPerfilImovel", filtro.getPerfilImovel());
				hql.append(" AND osscip.imovelPerfil.id in (:colecaoPerfilImovel)  ");
			}
			if(filtro.getCategoria() != null){

				parameters.put("colecaoCategoria", filtro.getCategoria());
				hql.append(" AND osccs.categoria.id in (:colecaoCategoria)  ");
			}
			if(filtro.getSubCategoria() != null){

				parameters.put("colecaoSubCategoria", filtro.getSubCategoria());
				hql.append(" AND osccs.subCategoria.id in (:colecaoSubCategoria)  ");
			}
			if(filtro.getLigacaoAguaSituacao() != null){

				parameters.put("colecaoLigacaoAguaSituacao", filtro.getLigacaoAguaSituacao());
				hql.append(" AND osscla.ligacaoAguaSituacao.id in (:colecaoLigacaoAguaSituacao)  ");
			}
			if(filtro.getLigacaoEsgotoSituacao() != null){

				parameters.put("colecaoLigacaoEsgotoSituacao", filtro.getLigacaoEsgotoSituacao());
				hql.append(" AND osscle.ligacaoEsgotoSituacao.id in (:colecaoLigacaoEsgotoSituacao)  ");
			}

			query = session.createQuery(hql.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Date){
					Date data = (Date) parameters.get(key);
					query.setTimestamp(key, data);
				}else if(parameters.get(key) instanceof String){
					String string = (String) parameters.get(key);
					query.setParameter(key, string);
				}
			}

			if(numeroPagina == ConstantesSistema.NUMERO_NAO_INFORMADO){
				retorno = query.list();
			}else{
				retorno = query.setFirstResult(10 * numeroPagina).setMaxResults(10).list();
			}

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection filtrarDadosRelatorioComandoOSSeletiva(OrdemServicoSeletivaComandoHelper filtro) throws ErroRepositorioException{

		// cria a coleçãoque vai armazenar os comandos de OsSeletiva
		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		String criterioTitulo = null;
		Query query = null;
		Map parameters = new HashMap();

		try{

			StringBuilder sql = new StringBuilder("select ");

			sql.append(" osSC.ossc_dstitulo as titulo, ");

			sql.append(" emp.empr_nmabreviadoempresa as empresa, ");

			sql.append(" svtp.svtp_id as servicoTipoId, ");

			sql.append(" svtp.svtp_dsservicotipo as servicoTipo, ");

			sql.append(" to_char(osSC.ossc_tmcomando, 'dd/mm/yyyy') || ' ' || to_char(osSC.ossc_tmcomando, 'hh:mm:ss') as dataHoraComando, ");

			sql.append(" to_char(osSC.ossc_tmrealizacao, 'dd/mm/yyyy') || ' ' || to_char(osSC.ossc_tmrealizacao, 'hh:mm:ss') as dataHoraRealizacao, ");

			sql.append(" count(orse_id) as qtdeOsGerada, ");

			sql.append(" sum(CASE WHEN orse.orse_cdsituacao <> 2 THEN 1 ELSE 0 END) as qtdeOsPendente, ");

			sql.append(" sum(CASE WHEN orse.orse_cdsituacao = 2 AND amen.amen_icexecucao = 2 THEN 1 ELSE 0 END) as qtdeOsCancelada, ");

			sql.append(" sum(CASE WHEN orse.orse_cdsituacao = 2 AND amen.amen_icexecucao = 1 THEN 1 ELSE 0 END) as qtdeOsExecutada ");

			sql.append(" FROM os_seletiva_comando osSC ");
			sql.append(" LEFT JOIN os_selet_comando_local_geo ossclg on ossclg.ossc_id = osSC.ossc_id ");
			sql.append(" LEFT JOIN os_selet_comando_imovel_perfil osscip on osscip.ossc_id = osSC.ossc_id ");
			sql.append(" LEFT JOIN os_selet_comando_cat_subcat osccs on osccs.ossc_id = osSC.ossc_id ");
			sql.append(" LEFT JOIN os_selet_comando_lig_agua osscla on osscla.ossc_id = osSC.ossc_id ");
			sql.append(" LEFT JOIN os_selet_comando_lig_esg osscle on osscle.ossc_id = osSC.ossc_id ");
			sql.append(" LEFT JOIN empresa emp on emp.empr_id = osSC.empr_id ");
			sql.append(" LEFT JOIN servico_tipo svtp on svtp.svtp_id = osSC.svtp_id ");
			sql.append(" LEFT JOIN faturamento_grupo fat on fat.ftgr_id = osSC.ftgr_id ");
			sql.append(" LEFT JOIN ordem_servico orse on orse.ossc_id = osSC.ossc_id ");
			sql.append(" LEFT JOIN atendimento_motivo_encrto amen on orse.amen_id = amen.amen_id ");
			sql.append(" Where ");

			if(filtro.getTitulo() != null && filtro.getTipoPesquisa() != null
							&& filtro.getTipoPesquisa().equals(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString())){

				criterioTitulo = " osSC.ossc_dstitulo like '" + filtro.getTitulo() + "%' AND ";
				sql.append(criterioTitulo);

			}else if(filtro.getTitulo() != null && filtro.getTipoPesquisa() != null
							&& filtro.getTipoPesquisa().equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				criterioTitulo = "osSC.ossc_dstitulo like '%" + filtro.getTitulo() + "%' AND ";

				sql.append(criterioTitulo);

			}
			if(filtro.getFirma() != null){

				sql.append(" emp.empr_id in (:colecaoEmpresa) AND ");

				parameters.put("colecaoEmpresa", filtro.getFirma());

			}
			if(filtro.getServicoTipo() != null){

				sql.append(" svtp.svtp_id in (:colecaoServicoTipo) AND ");
				parameters.put("colecaoServicoTipo", filtro.getServicoTipo());

			}
			if(filtro.getDataComandoInicial() != null && filtro.getDataComandoFinal() != null){

				String dataInicial = filtro.getDataComandoInicial();
				String dataFinal = filtro.getDataComandoFinal();

				dataInicial = dataInicial.substring(6, 10) + "-" + dataInicial.substring(3, 5) + "-" + dataInicial.substring(0, 2);
				dataFinal = dataFinal.substring(6, 10) + "-" + dataFinal.substring(3, 5) + "-" + dataFinal.substring(0, 2);

				sql.append("  osSC.ossc_tmcomando >= to_timestamp('" + dataInicial
								+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') AND ");
				sql.append("  osSC.ossc_tmcomando <= to_timestamp('" + dataFinal + " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') AND ");
			}
			if(filtro.getDataRealizacaoComandoInicial() != null && filtro.getDataRealizacaoComandoFinal() != null){

				String dataInicial = filtro.getDataRealizacaoComandoInicial();
				String dataFinal = filtro.getDataRealizacaoComandoFinal();

				dataInicial = dataInicial.substring(6, 10) + "-" + dataInicial.substring(3, 5) + "-" + dataInicial.substring(0, 2);
				dataFinal = dataFinal.substring(6, 10) + "-" + dataFinal.substring(3, 5) + "-" + dataFinal.substring(0, 2);

				sql.append("  osSC.ossc_tmrealizacao >= to_timestamp('" + dataInicial
								+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') AND ");
				sql.append("  osSC.ossc_tmrealizacao <= to_timestamp('" + dataFinal
								+ " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') AND ");

			}
			if(filtro.getSituacaoComando() != null && filtro.getSituacaoComando().equals(ConstantesSistema.SIM)){

				sql.append(" osSC.ossc_tmrealizacao IS NOT NULL AND ");

			}
			if(filtro.getSituacaoComando() != null && filtro.getSituacaoComando().equals(ConstantesSistema.NAO)){

				sql.append(" osSC.ossc_tmrealizacao IS NULL AND ");

			}
			if(filtro.getElo() != null){

				parameters.put("colecaoElo", filtro.getElo());
				sql.append(" osSC.loca_cdelo in (:colecaoElo) AND ");

			}
			if(filtro.getFaturamentoGrupo() != null){

				parameters.put("colecaoFaturamentoGrupo", filtro.getFaturamentoGrupo());
				sql.append(" fat.ftgr_id in (:colecaoFaturamentoGrupo) AND ");

			}
			if(filtro.getGerenciaRegional() != null){

				parameters.put("colecaoGereciaRegioanl", filtro.getGerenciaRegional());
				sql.append(" ossclg.greg_id in (:colecaoGereciaRegioanl) AND ");

			}
			if(filtro.getLocalidade() != null){

				parameters.put("colecaoLocalidade", filtro.getLocalidade());
				sql.append(" ossclg.loca_id in (:colecaoLocalidade) AND ");

			}
			if(filtro.getSetor() != null){

				parameters.put("colecaoSetor", filtro.getSetor());
				sql.append(" ossclg.stcm_id in (:colecaoSetor) AND ");

			}
			if(filtro.getQuadra() != null){

				parameters.put("colecaoQuadra", filtro.getQuadra());
				sql.append(" ossclg.qdra_id in (:colecaoQuadra) AND ");
			}
			if(filtro.getRota() != null){

				parameters.put("colecaoRota", filtro.getRota());
				sql.append(" ossclg.rota_id in (:colecaoRota) AND ");
			}
			if(filtro.getLote() != null){

				parameters.put("colecaoLote", filtro.getLote());
				sql.append(" ossclg.oscl_nnlote in (:colecaoLote) AND ");
			}
			if(filtro.getBairro() != null){

				parameters.put("colecaoBairro", filtro.getBairro());
				sql.append(" ossclg.bair_id in (:colecaoBairro) AND ");
			}
			if(filtro.getLogradouro() != null){

				parameters.put("colecaoLogradouro", filtro.getLogradouro());
				sql.append(" ossclg.logr_id in (:colecaoLogradouro) AND ");
			}
			if(filtro.getPerfilImovel() != null){

				parameters.put("colecaoPerfilImovel", filtro.getPerfilImovel());
				sql.append(" osscip.iper_id in (:colecaoPerfilImovel) AND ");
			}
			if(filtro.getCategoria() != null){

				parameters.put("colecaoCategoria", filtro.getCategoria());
				sql.append(" osccs.catg_id in (:colecaoCategoria) AND ");
			}
			if(filtro.getSubCategoria() != null){

				parameters.put("colecaoSubCategoria", filtro.getSubCategoria());
				sql.append(" osccs.scat_id in (:colecaoSubCategoria) AND ");
			}
			if(filtro.getLigacaoAguaSituacao() != null){

				parameters.put("colecaoLigacaoAguaSituacao", filtro.getLigacaoAguaSituacao());
				sql.append(" osscla.last_id in (:colecaoLigacaoAguaSituacao) AND ");
			}
			if(filtro.getLigacaoEsgotoSituacao() != null){

				parameters.put("colecaoLigacaoEsgotoSituacao", filtro.getLigacaoEsgotoSituacao());
				sql.append(" osscle.lest_id in (:colecaoLigacaoEsgotoSituacao) AND ");
			}

			String sqlFormatado = Util.formatarHQL(sql.toString(), 4);
			
			// GROUP BY
			sqlFormatado = sqlFormatado
							+ " GROUP BY ossc.ossc_dstitulo, emp.empr_nmabreviadoempresa, svtp.svtp_id, svtp.svtp_dsservicotipo, "
							+ "to_char(ossc.ossc_tmcomando, 'dd/mm/yyyy') || ' ' || to_char(ossc.ossc_tmcomando, 'hh:mm:ss'), "
							+ "to_char(ossc.ossc_tmrealizacao, 'dd/mm/yyyy') || ' ' || to_char(ossc.ossc_tmrealizacao, 'hh:mm:ss') ";

			query = session.createSQLQuery(sqlFormatado).addScalar("titulo", Hibernate.STRING).addScalar("empresa", Hibernate.STRING)
							.addScalar("servicoTipoId", Hibernate.INTEGER).addScalar("servicoTipo", Hibernate.STRING)
							.addScalar("dataHoraComando", Hibernate.STRING).addScalar("dataHoraRealizacao", Hibernate.STRING)
							.addScalar("qtdeOsGerada", Hibernate.INTEGER).addScalar("qtdeOsPendente", Hibernate.INTEGER)
							.addScalar("qtdeOsCancelada", Hibernate.INTEGER).addScalar("qtdeOsExecutada", Hibernate.INTEGER);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Date){
					Date data = (Date) parameters.get(key);
					query.setTimestamp(key, data);
				}else if(parameters.get(key) instanceof String){
					String string = (String) parameters.get(key);
					query.setParameter(key, string);
				}
			}

			retorno = query.list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório de Ordens de Serviço Encerradas Dentro e Fora do Prazo"
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 * @param origemServico
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @return Collection
	 */
	public Collection pesquisaRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("SELECT distinct os.orse_id as ORDEM_SERVICO, " + "os.rgat_id as REG_ATENDIMENTO, "
							+ "os.imov_id as MATRICULA, "
							+ "st.sotp_dssolicitacaotipo as SOLICITACAO, " + "ste.step_dssolicitacaotipoespecifi as TIPO_ESPECIFICACAO, "
							+ "os.orse_tmgeracao as DATA_CRIACAO," + "os.orse_tmencerramento as DATA_ENCERRAMENTO,"
							+ "to_char(unidadeEncerramento.unid_id) ||' - '|| unidadeEncerramento.unid_dsunidade as UN_ENCERRAMENTO,");

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta.append("to_char(unidadeAtendimento.unid_id) ||' - '|| unidadeAtendimento.unid_dsunidade as UN_ATENDIMENTO,"
								+ "ra.RGAT_DTPREVISTAATUAL as PREVISAO," + "ra.RGAT_QTREITERACOES as REITERACAO, "
								+ "ra.RGAT_TMREGISTROATENDIMENTO as DATA_INICIO_RA, ra.RGAT_TMENCERRAMENTO as DATA_FIM_RA ");
			}else{
				consulta.append("to_char(unidadeEncerramento.unid_id) ||' - '|| unidadeEncerramento.unid_dsunidade as UN_ATENDIMENTO, "
								+ "'' as PREVISAO," + "'' as REITERACAO ");
			}

			consulta.append("FROM  ordem_servico os " + "LEFT JOIN imovel im on os.imov_id = im.imov_id "
							+ "INNER JOIN servico_tipo servTp on (os.svtp_id = servTp.svtp_id and servTp.SVTP_ICUSO=1) "
							+ "INNER JOIN atendimento_motivo_encrto ame ON os.amen_id = ame.amen_id ");

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta.append("INNER JOIN registro_atendimento ra on os.rgat_id = ra.rgat_id "
								+ "LEFT JOIN solicitacao_tipo_especificacao ste on ra.step_id = ste.step_id "
								+ "LEFT JOIN solicitacao_tipo st on ste.sotp_id = st.sotp_id "
								+ " INNER JOIN registro_atendimento_unidade raUnidade on (raUnidade.rgat_id = ra.rgat_id and raUnidade.attp_id = "
								+ AtendimentoRelacaoTipo.ENCERRAR + ") "
								+ "INNER JOIN tramite tr on tr.rgat_id = ra.rgat_id "
								+ "INNER JOIN unidade_organizacional unidadeAtendimento on unidadeAtendimento.unid_id = tr.unid_iddestino ");

			}else{
				consulta.append("LEFT JOIN solicitacao_tipo_especificacao ste on servTp.step_id = ste.step_id "
								+ "LEFT JOIN solicitacao_tipo st on ste.sotp_id = st.sotp_id ");
			}

			consulta.append("INNER JOIN ordem_servico_unidade osUnidade on (osUnidade.orse_id = os.orse_id and osUnidade.attp_id = "
							+ AtendimentoRelacaoTipo.ENCERRAR + ") "
							+ "INNER JOIN unidade_organizacional unidadeEncerramento on unidadeEncerramento.unid_id = osUnidade.unid_id ");

			consulta.append(criarCondicionaisOSGerarRelatorioOrdemServicoEncerradaDentroForaPrazo(origemServico, idsServicosTipos,
							idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, null));



			consulta.append(" GROUP BY to_char(unidadeEncerramento.unid_id) ||' - '|| unidadeEncerramento.unid_dsunidade," + "os.orse_id, "
							+ "os.rgat_id, " + "os.imov_id, " + "st.sotp_dssolicitacaotipo, " + "ste.step_dssolicitacaotipoespecifi, "
							+ "os.orse_tmgeracao, " + "os.orse_tmencerramento ");
			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){
				consulta.append(", to_char(unidadeAtendimento.unid_id) ||' - '|| unidadeAtendimento.unid_dsunidade,"
								+ "ra.RGAT_DTPREVISTAATUAL,"
								+ "ra.RGAT_QTREITERACOES, ra.RGAT_TMREGISTROATENDIMENTO, ra.RGAT_TMENCERRAMENTO  ");
			}
			consulta.append(" ORDER BY to_char(unidadeEncerramento.unid_id) ||' - '|| unidadeEncerramento.unid_dsunidade, os.rgat_id ");

			retorno = (Collection) session.createSQLQuery(consulta.toString()).addScalar("ORDEM_SERVICO", Hibernate.STRING)
							.addScalar("REG_ATENDIMENTO", Hibernate.STRING).addScalar("MATRICULA", Hibernate.STRING)
							.addScalar("SOLICITACAO", Hibernate.STRING).addScalar("TIPO_ESPECIFICACAO", Hibernate.STRING)
							.addScalar("DATA_CRIACAO", Hibernate.DATE).addScalar("DATA_ENCERRAMENTO", Hibernate.DATE)
							.addScalar("UN_ENCERRAMENTO", Hibernate.STRING).addScalar("UN_ATENDIMENTO", Hibernate.STRING)
							.addScalar("PREVISAO", Hibernate.DATE).addScalar("DATA_INICIO_RA", Hibernate.DATE)
							.addScalar("DATA_FIM_RA", Hibernate.DATE).addScalar("REITERACAO", Hibernate.STRING).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * "Gerar relatório de Ordens de Serviço Encerradas Dentro e Fora do Prazo"
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 */
	private String criarCondicionaisOSGerarRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade){

		String sql = " where ";

		sql = sql + " os.orse_cdsituacao = " + OrdemServico.SITUACAO_ENCERRADO + " and ";

		if(origemServico != null && !origemServico.equals("") && origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SELETIVO.toString())){
			/*
			 * if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SELETIVO.toString())){
			 * sql = sql + " os.cbdo_id  is not null " + " and ";
			 * }
			 */
			sql = sql + "servTp.SVTP_ICORDEMSELETIVA = " + origemServico + " and ";
		}

		if(idsServicosTipos != null && !idsServicosTipos.equals("")
						&& (idsServicosTipos.length != 1 || !idsServicosTipos[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){

			String valoresIn = "";
			for(int i = 0; i < idsServicosTipos.length; i++){
				if(!idsServicosTipos[i].equals("")){
					valoresIn = valoresIn + idsServicosTipos[i] + ",";
				}
			}
			if(!valoresIn.equals("")){
				sql = sql + " servTp.svtp_id in (" + valoresIn;
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
			}
		}

		if(Util.verificarIdNaoVazio(idUnidadeAtendimento)){
			sql = sql + " unidadeAtendimento.unid_id = " + idUnidadeAtendimento + " and ";
		}

		boolean adicionouUnidade = false;
		if(Util.verificarIdNaoVazio(idUnidadeAtual) && origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){
			sql = sql + " tr.unid_iddestino = " + idUnidadeAtual
							+ " and tr.tram_tmtramite in (select max(tr.tram_tmtramite) from tramite tr where "
							+ " ra.rgat_id = tr.rgat_id)" + " and ";
		}else{
			sql = sql + " osUnidade.unid_id = " + idUnidadeAtual + " and " + " osUnidade.attp_id = " + AtendimentoRelacaoTipo.ENCERRAR
							+ " and ";
			adicionouUnidade = true;
		}

		if(Util.verificarIdNaoVazio(idUnidadeEncerramento) && !adicionouUnidade){
			sql = sql + " osUnidade.unid_id = " + idUnidadeEncerramento + " and " + " osUnidade.attp_id = "
							+ AtendimentoRelacaoTipo.ENCERRAR + " and ";
		}

		if(Util.verificarIdNaoVazio(idLocalidade)){
			sql += " im.loca_id = " + idLocalidade + " and ";
		}

		if(periodoAtendimentoInicial != null && !periodoAtendimentoFinal.equals("")){

			String data1 = Util.recuperaDataInvertida(periodoAtendimentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			sql = sql + " os.orse_tmgeracao >= to_timestamp('" + data1
							+ " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoAtendimentoFinal != null && !periodoAtendimentoFinal.equals("")){
			String data2 = Util.recuperaDataInvertida(periodoAtendimentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			sql = sql + " os.orse_tmgeracao <= to_timestamp('" + data2
							+ " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoInicial != null && !periodoEncerramentoInicial.equals("")){
			String data1 = Util.recuperaDataInvertida(periodoEncerramentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			sql = sql + " os.orse_tmencerramento >= to_timestamp('" + data1 + " 00:00:00.000000000', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		if(periodoEncerramentoFinal != null && !periodoEncerramentoFinal.equals("")){

			String data2 = Util.recuperaDataInvertida(periodoEncerramentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			sql = sql + " os.orse_tmencerramento <= to_timestamp('" + data2 + " 23:59:59.999999999', 'YYYY-MM-DD HH24:MI:SS.FF') and ";
		}

		// retira o " and " q fica sobrando no final da query
		sql = Util.formatarHQL(sql, 4);

		return sql;
	}

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório de Ordens de Serviço Encerradas Dentro e Fora do Prazo"
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 * @param origemServico
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @return Integer
	 */
	public Integer pesquisaTotalRegistrosRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade)
					throws ErroRepositorioException{

		Integer retorno = 0;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("SELECT count(distinct (os.orse_id)) AS QUANTIDADE ");

			consulta.append("FROM  ordem_servico os " + "LEFT JOIN imovel im on os.imov_id = im.imov_id "
							+ "INNER JOIN servico_tipo servTp on (os.svtp_id = servTp.svtp_id and servTp.SVTP_ICUSO=1) "
							+ "INNER JOIN atendimento_motivo_encrto ame ON os.amen_id = ame.amen_id ");

			if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){

				consulta.append("INNER JOIN registro_atendimento ra on os.rgat_id = ra.rgat_id "
								+ "LEFT JOIN solicitacao_tipo_especificacao ste on ra.step_id = ste.step_id "
								+ "LEFT JOIN solicitacao_tipo st on ste.sotp_id = st.sotp_id "
								+ " INNER JOIN registro_atendimento_unidade raUnidade on (raUnidade.rgat_id = ra.rgat_id and raUnidade.attp_id = "
								+ AtendimentoRelacaoTipo.ENCERRAR + ") " + "INNER JOIN tramite tr on tr.rgat_id = ra.rgat_id "
								+ "INNER JOIN unidade_organizacional unidadeAtendimento on unidadeAtendimento.unid_id = tr.unid_iddestino ");

			}else{
				consulta.append("LEFT JOIN solicitacao_tipo_especificacao ste on servTp.step_id = ste.step_id "
								+ "LEFT JOIN solicitacao_tipo st on ste.sotp_id = st.sotp_id ");
			}

			consulta.append("INNER JOIN ordem_servico_unidade osUnidade on (osUnidade.orse_id = os.orse_id and osUnidade.attp_id = "
							+ AtendimentoRelacaoTipo.ENCERRAR + ") "
							+ "INNER JOIN unidade_organizacional unidadeEncerramento on unidadeEncerramento.unid_id = osUnidade.unid_id ");

			consulta.append(criarCondicionaisOSGerarRelatorioOrdemServicoEncerradaDentroForaPrazo(origemServico, idsServicosTipos,
							idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, null));

			Object retornoConsulta = session.createSQLQuery(consulta.toString()).addScalar("QUANTIDADE", Hibernate.INTEGER).uniqueResult();

			if(retornoConsulta != null){
				retorno = (Integer) retornoConsulta;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * 
	 */

	public Collection pesquisarServicoTipoTramite(Integer idServicoTipoTramite, Integer idLocalidade, Integer idSetorComercial,
					Integer idBairro, Integer idUnidadeOrigem) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		Query query = null;

		StringBuffer consulta = new StringBuffer("");

		Map parameters = new HashMap();

		try{
			consulta.append(" select servicoTipoTramite from ServicoTipoTramite servicoTipoTramite ");
			consulta.append(" where ");

			consulta.append(" servicoTipoTramite.servicoTipo.id = :idServicoTipoTramite ");
			parameters.put("idServicoTipoTramite", idServicoTipoTramite);

			if(!Util.isVazioOuBranco(idLocalidade) && !Util.isVazioOuBranco(idSetorComercial)){
				// Informou Localidade e Setor Comercial
				consulta.append(" and (servicoTipoTramite.localidade.id = :idLocalidade or servicoTipoTramite.localidade.id is null) ");
				parameters.put("idLocalidade", idLocalidade);

				consulta.append(" and (servicoTipoTramite.setorComercial.id = :idSetorComercial or servicoTipoTramite.setorComercial.id is null) ");
				parameters.put("idSetorComercial", idSetorComercial);
			}else if(!Util.isVazioOuBranco(idLocalidade)){
				// Informou só Localidade
				consulta.append(" and (servicoTipoTramite.localidade.id = :idLocalidade or servicoTipoTramite.localidade.id is null) ");
				parameters.put("idLocalidade", idLocalidade);
			}

			if(!Util.isVazioOuBranco(idBairro)){
				consulta.append(" and (servicoTipoTramite.bairro.id = :idBairro or servicoTipoTramite.bairro.id is null) ");
				parameters.put("idBairro", idBairro);
			}

			if(!Util.isVazioOuBranco(idUnidadeOrigem)){
				consulta.append(" and (servicoTipoTramite.unidadeOrganizacionalOrigem.id = :idUnidadeOrigem or servicoTipoTramite.unidadeOrganizacionalOrigem.id is null) ");
				parameters.put("idUnidadeOrigem", idUnidadeOrigem);
			}else{
				consulta.append(" and servicoTipoTramite.unidadeOrganizacionalOrigem.id is null ");
			}

			query = session.createQuery(consulta.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof String){
					String string = (String) parameters.get(key);
					query.setParameter(key, string);
				}else if(parameters.get(key) instanceof Integer){
					Integer integer = (Integer) parameters.get(key);
					query.setParameter(key, integer);
				}else if(parameters.get(key) instanceof Short){
					Short parametroShort = (Short) parameters.get(key);
					query.setParameter(key, parametroShort);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = (Collection) query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Recupera uma coleção de Entulho Medida.
	 * 
	 * @author Genival Barbosa
	 * @date 20/09/2014
	 * @return Coleção de EntulhoMedida
	 */
	public Collection<EntulhoMedida> pesquisarEntulhoMedida() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(EntulhoMedida.class).add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO))
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Recupera um Entulho Medida.
	 * 
	 * @author Genival Barbosa
	 * @date 20/09/2014
	 * @return Entulho Medida
	 */
	public EntulhoMedida pesquisarEntulhoMedida(Integer idEntulhoMedida) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		EntulhoMedida retorno = null;

		try{

			Criteria criteria = session.createCriteria(EntulhoMedida.class).add(Expression.eq("id", idEntulhoMedida))
							.add(Expression.eq("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO));

			List<EntulhoMedida> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisar ordem de serviço.
	 * 
	 * @author Genival Barbosa
	 * @date 30/09/2014
	 * @return Ordem Servico
	 */
	public OrdemServico pesquisarOrdemServicoPrincipal(Integer idOrdemServico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		OrdemServico retorno = null;

		try{

			Criteria criteria = session.createCriteria(OrdemServico.class).add(Expression.eq("ordemServicoReparo.id", idOrdemServico));

			List<OrdemServico> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Anderson Italo
	 * @date 09/10/2014
	 */
	public Collection<Bairro> pesquisarBairroPorRA(Integer idUnidadeDestino, Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = new ArrayList();
		Collection<Bairro> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			String subQuery = this.criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(idUnidadeDestino, permiteTramiteIndependente);

			consulta = "SELECT DISTINCT bairr.id," // 0
							+ "bairr.nome, count(os) " // 1,2
							+ "FROM OrdemServico os INNER JOIN os.registroAtendimento ragt  "
							+ "INNER JOIN ragt.logradouroBairro logBairr "
							+ "INNER JOIN logBairr.bairro bairr "
							+ "WHERE os.situacao in (1,3) and os.indicadorProgramada = 2 "
							+ "AND os.id in (" + subQuery + ") GROUP BY bairr.id, bairr.nome ORDER BY bairr.nome";

			retornoConsulta = session.createQuery(consulta).list();

			if(retornoConsulta.size() > 0){

				retorno = new ArrayList();

				Bairro bairro = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					bairro = new Bairro();

					bairro.setId((Integer) element[0]);
					bairro.setNome((String) element[1]);
					bairro.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(bairro);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Anderson Italo
	 * @date 09/10/2014
	 */
	public Collection<Bairro> pesquisarBairroPorUnidade(Integer idUnidade) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = new ArrayList();
		Collection<Bairro> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT DISTINCT bairr.id," // 0
							+ "bairr.nome, count(os) " // 1,2
							+ "FROM OrdemServicoUnidade osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  "
							+ "INNER JOIN osUnidade.ordemServico os  "
							+ "INNER JOIN osUnidade.unidadeOrganizacional unid  "
							+ "LEFT JOIN os.registroAtendimento ra  "
							+ "INNER JOIN os.imovel imov  "
							+ "INNER JOIN imov.logradouroBairro logBairr "
							+ "INNER JOIN logBairr.bairro bairr "
							+ "WHERE ra IS NULL AND os.situacao in (1,3) and os.indicadorProgramada = 2 "
							+ "AND unid.id = :unidade "
							+ "AND art.id = :idAtendimentoTipo GROUP BY bairr.id, bairr.nome ORDER BY bairr.nome";

			retornoConsulta = session.createQuery(consulta).setInteger("unidade", idUnidade)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

			if(retornoConsulta.size() > 0){

				retorno = new ArrayList();

				Bairro bairro = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					bairro = new Bairro();

					bairro.setId((Integer) element[0]);
					bairro.setNome((String) element[1]);
					bairro.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(bairro);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Anderson Italo
	 * @date 09/10/2014
	 */
	public Collection<ServicoTipoPrioridade> pesquisarServicoTipoPrioridadePorRA(Integer idUnidadeDestino,
 Short permiteTramiteIndependente)
					throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = new ArrayList();
		Collection<ServicoTipoPrioridade> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			String subQuery = this.criarQueryRegistroAtendimentoPorMaiorTramiteUnidadeDestino(idUnidadeDestino, permiteTramiteIndependente);

			consulta = "SELECT DISTINCT stp.id," // 0
							+ "stp.descricao, count(os) " // 1,2
							+ "FROM OrdemServico os INNER JOIN os.registroAtendimento ragt  "
							+ "INNER JOIN os.servicoTipoPrioridadeAtual stp "
							+ "WHERE os.situacao in (1,3) and os.indicadorProgramada = 2 " + "AND os.id in ("
							+ subQuery
							+ ") GROUP BY stp.id, stp.descricao ORDER BY stp.descricao";

			retornoConsulta = session.createQuery(consulta).list();

			if(retornoConsulta.size() > 0){

				retorno = new ArrayList();

				ServicoTipoPrioridade servicoTipoPrioridade = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					servicoTipoPrioridade = new ServicoTipoPrioridade();

					servicoTipoPrioridade.setId((Integer) element[0]);
					servicoTipoPrioridade.setDescricao((String) element[1]);
					servicoTipoPrioridade.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(servicoTipoPrioridade);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Anderson Italo
	 * @date 09/10/2014
	 */
	public Collection<ServicoTipoPrioridade> pesquisarServicoTipoPrioridadePorUnidade(Integer idUnidade) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = new ArrayList();
		Collection<ServicoTipoPrioridade> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT DISTINCT stp.id," // 0
							+ "stp.descricao, count(os) " // 1,2
							+ "FROM OrdemServicoUnidade osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  "
							+ "INNER JOIN osUnidade.ordemServico os  "
							+ "INNER JOIN osUnidade.unidadeOrganizacional unid  "
							+ "LEFT JOIN os.registroAtendimento ra  "
							+ "INNER JOIN os.servicoTipoPrioridadeAtual stp "
							+ "WHERE ra IS NULL AND os.situacao in (1,3) and os.indicadorProgramada = 2 "
							+ "AND unid.id = :unidade "
							+ "AND art.id = :idAtendimentoTipo GROUP BY stp.id, stp.descricao ORDER BY stp.descricao";

			retornoConsulta = session.createQuery(consulta).setInteger("unidade", idUnidade)
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

			if(retornoConsulta.size() > 0){

				retorno = new ArrayList();

				ServicoTipoPrioridade servicoTipoPrioridade = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					servicoTipoPrioridade = new ServicoTipoPrioridade();

					servicoTipoPrioridade.setId((Integer) element[0]);
					servicoTipoPrioridade.setDescricao((String) element[1]);
					servicoTipoPrioridade.setQtidadeOs(((Number) element[2]).intValue());
					retorno.add(servicoTipoPrioridade);
				}

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Integer recuperaQuantidadeDiasUnidade(Integer idOS, Short permiteTramiteIndependente) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		int retorno = 0;
		try{
			StringBuffer hql = new StringBuffer();

			hql.append("select trunc(max(tramites.dataTramite)) ");
			hql.append("from OrdemServico os ");
			if(permiteTramiteIndependente.intValue() == 2){
				hql.append("inner join os.registroAtendimento rgat ");
				hql.append("inner join rgat.tramites tramites ");
			}else{
				hql.append("inner join os.tramites tramites ");
			}

			hql.append("where os.id = :idOS ");
			Query query = session.createQuery(hql.toString());
			query.setInteger("idOS", idOS);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date data1 = new Date();
			Date data2;

			if(query.uniqueResult() != null){
				data2 = (Date) query.uniqueResult();
				retorno = Util.diferencaEntreDatas(sdf.format(data1), sdf.format(data2));
			}else{
				hql = new StringBuffer();
				hql.append("select trunc(os.dataGeracao) ");
				hql.append("from OrdemServico os ");
				hql.append("where os.id = :idOS ");

				query = session.createQuery(hql.toString());
				query.setInteger("idOS", idOS);

				if(query.uniqueResult() != null){
					data2 = (Date) query.uniqueResult();
					retorno = Util.diferencaEntreDatas(sdf.format(data1), sdf.format(data2));
				}
			}

		}catch(ParseException pe){
			throw new ErroRepositorioException(pe, "Erro ao calcular data");
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}		
		return retorno;
	}
}
