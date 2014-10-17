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
 * Ivan Sérgio da Silva Júnior
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

package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ImovelClientesEspeciaisRelatorioHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.cliente.FiltrarRelatorioClientesEspeciaisHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar o relatório de bairro manter de água
 * 
 * @author Sávio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioClientesEspeciais
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioClientesEspeciais(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CLIENTES_ESPECIAIS);
	}

	@Deprecated
	public RelatorioClientesEspeciais() {

		super(null, "");
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// Recupera os parâmetros
		String idUnidadeNegocio = (String) getParametro("idUnidadeNegocio");
		String idGerenciaRegional = (String) getParametro("idGerenciaRegional");
		String idLocalidadeInicial = (String) getParametro("idLocalidadeInicial");
		String idLocalidadeFinal = (String) getParametro("idLocalidadeFinal");
		String[] idsPerfilImovel = (String[]) getParametro("idsPerfilImovel");
		String[] idsCategoria = (String[]) getParametro("idsCategoria");
		String[] idsSubcategoria = (String[]) getParametro("idsSubcategoria");
		String idSituacaoAgua = (String) getParametro("idSituacaoAgua");
		String idSituacaoEsgoto = (String) getParametro("idSituacaoEsgoto");
		String qtdeEconomiasInicial = (String) getParametro("qtdeEconomiasInicial");
		String qtdeEconomiasFinal = (String) getParametro("qtdeEconomiasFinal");
		String intervaloConsumoAguaInicial = (String) getParametro("intervaloConsumoAguaInicial");
		String intervaloConsumoAguaFinal = (String) getParametro("intervaloConsumoAguaFinal");
		String intervaloConsumoEsgotoInicial = (String) getParametro("intervaloConsumoEsgotoInicial");
		String intervaloConsumoEsgotoFinal = (String) getParametro("intervaloConsumoEsgotoFinal");
		String idClienteResponsavel = (String) getParametro("idClienteResponsavel");
		String intervaloConsumoResponsavelInicial = (String) getParametro("intervaloConsumoResponsavelInicial");
		String intervaloConsumoResponsavelFinal = (String) getParametro("intervaloConsumoResponsavelFinal");
		Date dataInstalacaoHidrometroInicial = (Date) getParametro("dataInstalacaoHidrometroInicial");
		Date dataInstalacaoHidrometroFinal = (Date) getParametro("dataInstalacaoHidrometroFinal");
		String[] idsCapacidadesHidrometro = (String[]) getParametro("idsCapacidadesHidrometro");
		String[] idsTarifasConsumo = (String[]) getParametro("idsTarifasConsumo");
		String idLeituraAnormalidade = (String) getParametro("idLeituraAnormalidade");
		String idConsumoAnormalidade = (String) getParametro("idConsumoAnormalidade");
		String leituraAnormalidade = (String) getParametro("leituraAnormalidade");
		String consumoAnormalidade = (String) getParametro("consumoAnormalidade");
		String[] idsClienteTipoEspecial = (String[]) getParametro("idsClienteTipoEspecial");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		Integer anoMes = Util.subtrairMesDoAnoMes(sistemaParametro.getAnoMesFaturamento(), 1);

		RelatorioClientesEspeciaisBean relatorioBean = null;

		Collection colecaoImovelClientesEspeciaisRelatorioHelper = fachada.pesquisarImovelClientesEspeciaisRelatorio(idUnidadeNegocio,
						idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal, idsPerfilImovel, idsCategoria, idsSubcategoria,
						idSituacaoAgua, idSituacaoEsgoto, qtdeEconomiasInicial, qtdeEconomiasFinal, intervaloConsumoAguaInicial,
						intervaloConsumoAguaFinal, intervaloConsumoEsgotoInicial, intervaloConsumoEsgotoFinal, idClienteResponsavel,
						intervaloConsumoResponsavelInicial, intervaloConsumoResponsavelFinal, dataInstalacaoHidrometroInicial,
						dataInstalacaoHidrometroFinal, idsCapacidadesHidrometro, idsTarifasConsumo, anoMes, idLeituraAnormalidade,
						leituraAnormalidade, idConsumoAnormalidade, consumoAnormalidade, idsClienteTipoEspecial);

		// se a coleção de parâmetros da analise não for vazia
		if(colecaoImovelClientesEspeciaisRelatorioHelper != null && !colecaoImovelClientesEspeciaisRelatorioHelper.isEmpty()){

			// Organizar a coleção

			Collections.sort((List) colecaoImovelClientesEspeciaisRelatorioHelper, new Comparator() {

				public int compare(Object a, Object b){

					String chave1 = ((ImovelClientesEspeciaisRelatorioHelper) a).getIdGerenciaRegional().toString()
									+ ((ImovelClientesEspeciaisRelatorioHelper) a).getIdLocalidade().toString()
									+ ((ImovelClientesEspeciaisRelatorioHelper) a).getIdCategoria().toString()
									+ ((ImovelClientesEspeciaisRelatorioHelper) a).getIdSubcategoria().toString();
					String chave2 = ((ImovelClientesEspeciaisRelatorioHelper) b).getIdGerenciaRegional().toString()
									+ ((ImovelClientesEspeciaisRelatorioHelper) b).getIdLocalidade().toString()
									+ ((ImovelClientesEspeciaisRelatorioHelper) b).getIdCategoria().toString()
									+ ((ImovelClientesEspeciaisRelatorioHelper) b).getIdSubcategoria().toString();

					return chave1.compareTo(chave2);
				}
			});

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoImovelClientesEspeciaisRelatorioHelperIterator = colecaoImovelClientesEspeciaisRelatorioHelper.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoImovelClientesEspeciaisRelatorioHelperIterator.hasNext()){

				ImovelClientesEspeciaisRelatorioHelper imovelClientesEspeciaisRelatorioHelper = (ImovelClientesEspeciaisRelatorioHelper) colecaoImovelClientesEspeciaisRelatorioHelperIterator
								.next();

				Imovel imovel = new Imovel();
				imovel.setId(imovelClientesEspeciaisRelatorioHelper.getIdImovel());

				// Gerência Regional
				String gerenciaRegional = "";
				if(imovelClientesEspeciaisRelatorioHelper.getIdGerenciaRegional() != null){
					gerenciaRegional = imovelClientesEspeciaisRelatorioHelper.getIdGerenciaRegional().toString() + " - "
									+ imovelClientesEspeciaisRelatorioHelper.getNomeGerenciaRegional();
				}

				// Categoria
				String categoria = "";
				if(imovelClientesEspeciaisRelatorioHelper.getIdCategoria() != null){
					categoria = imovelClientesEspeciaisRelatorioHelper.getIdCategoria().toString() + " - "
									+ imovelClientesEspeciaisRelatorioHelper.getDescricaoCategoria();
				}

				// Localidade
				String localidade = "";
				if(imovelClientesEspeciaisRelatorioHelper.getIdLocalidade() != null){
					localidade = imovelClientesEspeciaisRelatorioHelper.getIdLocalidade().toString() + " - "
									+ imovelClientesEspeciaisRelatorioHelper.getNomeLocalidade();
				}

				// Subcategoria
				String subcategoria = "";
				if(imovelClientesEspeciaisRelatorioHelper.getIdSubcategoria() != null){
					subcategoria = imovelClientesEspeciaisRelatorioHelper.getIdSubcategoria().toString();
					// + " - "
					// + imovelClientesEspeciaisRelatorioHelper
					// .getDescricaoSubcategoria();
				}

				// Data da Instalação do Hidrômetro
				String dataInstalacaoFormatada = "";
				if(imovelClientesEspeciaisRelatorioHelper.getDataInstalacaoHidrometro() != null){
					dataInstalacaoFormatada = Util.formatarData(imovelClientesEspeciaisRelatorioHelper.getDataInstalacaoHidrometro());
				}

				// Cliente Usuário
				String clienteUsuario = "";
				if(imovelClientesEspeciaisRelatorioHelper.getIdClienteUsuario() != null){
					clienteUsuario = imovelClientesEspeciaisRelatorioHelper.getIdClienteUsuario().toString() + " - "
									+ imovelClientesEspeciaisRelatorioHelper.getNomeClienteUsuario();
				}

				// Cliente Responsável
				String clienteResponsavel = "";
				if(imovelClientesEspeciaisRelatorioHelper.getIdClienteResponsavel() != null){
					clienteResponsavel = imovelClientesEspeciaisRelatorioHelper.getIdClienteResponsavel().toString() + " - "
									+ imovelClientesEspeciaisRelatorioHelper.getNomeClienteResponsavel();
				}

				// Quantidade de Economias
				String qtdeEconomias = "";
				if(imovelClientesEspeciaisRelatorioHelper.getQtdeEconomias() != null){
					qtdeEconomias = imovelClientesEspeciaisRelatorioHelper.getQtdeEconomias().toString();
				}

				// Consumo de Água
				String consumoAgua = "";
				if(imovelClientesEspeciaisRelatorioHelper.getConsumoAgua() != null){
					consumoAgua = imovelClientesEspeciaisRelatorioHelper.getConsumoAgua().toString();
				}

				// Consumo de Esgoto
				String consumoEsgoto = "";
				if(imovelClientesEspeciaisRelatorioHelper.getConsumoEsgoto() != null){
					consumoEsgoto = imovelClientesEspeciaisRelatorioHelper.getConsumoEsgoto().toString();
				}

				// Consumo Médio do Imóvel
				String consumoMedio = "";

				try{
					int[] consumoMedioImovel = fachada.obterConsumoMedioImovel(imovel, sistemaParametro);
					consumoMedio = "" + consumoMedioImovel[0];
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Consumo Mínimo Esgoto
				String consumoMinimoEsgoto = "";
				if(imovelClientesEspeciaisRelatorioHelper.getConsumoMinimoEsgoto() != null){
					consumoMinimoEsgoto = imovelClientesEspeciaisRelatorioHelper.getConsumoMinimoEsgoto().toString();
				}

				// Débitos Vencidos
				String debitosVencidos = "";
				if(imovelClientesEspeciaisRelatorioHelper.getValorDebitosVencidos() != null){
					debitosVencidos = Util.formatarMoedaReal(imovelClientesEspeciaisRelatorioHelper.getValorDebitosVencidos());
				}

				// Faturas em Atraso
				String faturasEmAtraso = "";
				if(imovelClientesEspeciaisRelatorioHelper.getQtdeDebitosVencidos() != null){
					faturasEmAtraso = imovelClientesEspeciaisRelatorioHelper.getQtdeDebitosVencidos().toString();
				}

				// Valor de Água
				String valorAgua = "";
				if(imovelClientesEspeciaisRelatorioHelper.getValorAgua() != null){
					valorAgua = Util.formatarMoedaReal(imovelClientesEspeciaisRelatorioHelper.getValorAgua());
				}

				// Valor de Esgoto
				String valorEsgoto = "";
				if(imovelClientesEspeciaisRelatorioHelper.getValorEsgoto() != null){
					valorEsgoto = Util.formatarMoedaReal(imovelClientesEspeciaisRelatorioHelper.getValorEsgoto());
				}

				// Valor da Fatura
				String valorFatura = "";
				if(imovelClientesEspeciaisRelatorioHelper.getValorConta() != null){
					valorFatura = Util.formatarMoedaReal(imovelClientesEspeciaisRelatorioHelper.getValorConta());
				}

				relatorioBean = new RelatorioClientesEspeciaisBean(
				// Gerência Regional
								gerenciaRegional,

								// Categoria
								categoria,

								// Localidade
								localidade,

								// Subcategoria
								subcategoria,

								// Inscrição
								imovelClientesEspeciaisRelatorioHelper.getInscricaoImovel(),

								// Capacidade do Hidrômetro
								imovelClientesEspeciaisRelatorioHelper.getDescricaoCapacidadeHidrometro(),

								// Matrícula
								imovelClientesEspeciaisRelatorioHelper.getIdImovel().toString(),

								// Data Instalação
								dataInstalacaoFormatada,

								// Cliente Usuário
								clienteUsuario,

								// Cliente Responsável
								clienteResponsavel,

								// Quantidade de Economias
								qtdeEconomias,

								// Consumo de Água
								consumoAgua,

								// Consumo de Esgoto
								consumoEsgoto,

								// Situação da Ligação de Água
								imovelClientesEspeciaisRelatorioHelper.getDescricaoSituacaoLigacaoAgua(),

								// Consumo Médio
								consumoMedio,

								// Situação da Ligação de Esgoto
								imovelClientesEspeciaisRelatorioHelper.getDescricaoSituacaoLigacaoEsgoto(),

								// Consumo Mínimo de Esgoto
								consumoMinimoEsgoto,

								// Débitos Vencidos
								debitosVencidos,

								// Faturas em Atraso
								faturasEmAtraso,

								// Tarifa de Consumo
								imovelClientesEspeciaisRelatorioHelper.getDescricaoTarifaConsumo(),

								// Valor de Água
								valorAgua,

								// Valor de Esgoto
								valorEsgoto,

								// Valor da Fatura
								valorFatura);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("tipoFormatoRelatorio", "R0591");
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CLIENTES_ESPECIAIS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.CLIENTES_ESPECIAIS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;
		Fachada fachada = Fachada.getInstancia();
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		Integer anoMesFaturamento = Util.subtrairMesDoAnoMes(sistemaParametro.getAnoMesFaturamento(), 1);

		FiltrarRelatorioClientesEspeciaisHelper filtro = new FiltrarRelatorioClientesEspeciaisHelper(
						(String) getParametro("idUnidadeNegocio"), (String) getParametro("idGerenciaRegional"),
						(String) getParametro("idLocalidadeInicial"), (String) getParametro("idLocalidadeFinal"),
						(String[]) getParametro("idsPerfilImovel"), (String[]) getParametro("idsCategoria"),
						(String[]) getParametro("idsSubcategoria"), (String) getParametro("idSituacaoAgua"),
						(String) getParametro("idSituacaoEsgoto"), (String) getParametro("qtdeEconomiasInicial"),
						(String) getParametro("qtdeEconomiasFinal"), (String) getParametro("intervaloConsumoAguaInicial"),
						(String) getParametro("intervaloConsumoAguaFinal"), (String) getParametro("intervaloConsumoEsgotoInicial"),
						(String) getParametro("intervaloConsumoEsgotoFinal"), (String) getParametro("idClienteResponsavel"),
						(String) getParametro("intervaloConsumoResponsavelInicial"),
						(String) getParametro("intervaloConsumoResponsavelFinal"), (Date) getParametro("dataInstalacaoHidrometroInicial"),
						(Date) getParametro("dataInstalacaoHidrometroFinal"), (String[]) getParametro("idsCapacidadesHidrometro"),
						(String[]) getParametro("idsTarifasConsumo"), anoMesFaturamento, (String) getParametro("idLeituraAnormalidade"),
						(String) getParametro("leituraAnormalidade"), (String) getParametro("idConsumoAnormalidade"),
						(String) getParametro("consumoAnormalidade"), (String[]) getParametro("idsClienteTipoEspecial"));

		retorno = fachada.pesquisarTotalRegistrosRelatorioClientesEspeciais(filtro);

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioClientesEspeciais", this);

	}

}