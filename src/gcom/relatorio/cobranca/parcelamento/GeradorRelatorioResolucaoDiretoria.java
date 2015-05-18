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

package gcom.relatorio.cobranca.parcelamento;

import gcom.batch.Relatorio;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.FiltroResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.ParametroTarefa;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.io.ByteArrayOutputStream;
import java.util.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import br.com.procenge.comum.exception.NegocioException;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

/**
 * Essa classe deve ser utilizada para gerar os relatórios de parcelamento
 * 
 * @author Cinthya
 */
public class GeradorRelatorioResolucaoDiretoria
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static GeradorRelatorioResolucaoDiretoria instancia = new GeradorRelatorioResolucaoDiretoria();

	String textoHtml;

	private void setTextoHtml(String textoHtml){

		this.textoHtml = textoHtml;

	}

	private String getTextoHtml(){

		return textoHtml;

	}

	public GeradorRelatorioResolucaoDiretoria(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public GeradorRelatorioResolucaoDiretoria(Usuario usuario) {

		super(usuario, "");
	}

	public GeradorRelatorioResolucaoDiretoria() {

		super(null, "");
	}

	/**
	 * Método reponsável por obter uma instância do GeradorRelatorioResolucaoDiretoria.
	 * 
	 * @return Um GeradorRelatorioResolucaoDiretoria
	 */
	public static GeradorRelatorioResolucaoDiretoria getInstancia(){

		return instancia;
	}

	/**
	 * Método responsável por gerar os relatórios de ordem de servico
	 * 
	 * @param listaParcelamento
	 * @return Um PDF.
	 * @throws GeradorRelatorioParcelamentoException
	 *             Casso ocorra algum erro na geração do relatório
	 */
	public byte[] gerarRelatorioResolucaoDiretoria(List<Parcelamento> listaParcelamento,
					Collection<ContaValoresHelper> colecaoContaValores, String stringTextoHtmlEditado)
					throws GeradorRelatorioParcelamentoException{

		String arquivoLayout = null;
		String nomeClasse = null;
		List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados = null;
		byte[] novoPdf = null;
		byte[] retorno = null;
		List<byte[]> pdfsGerados = new ArrayList<byte[]>();
		Parcelamento parcelamento = null;

		Map<Integer, Collection<ResolucaoDiretoria>> mapLayoutResolucaoDiretoria = new TreeMap<Integer, Collection<ResolucaoDiretoria>>();

		if(listaParcelamento != null && !listaParcelamento.isEmpty()){

			// Parâmetros do relatório
			SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

			if(sistemaParametro != null){
				addParametro("imagem", sistemaParametro.getImagemRelatorio());

				addParametro("mesPortugues", Util.retornaDescricaoMes(Util.getMes(new Date())));

				// Empresa
				if(sistemaParametro.getNomeEmpresa() != null){

					addParametro("empresa", sistemaParametro.getNomeEmpresa());

				}else{
					addParametro("empresa", "");
				}

				// Endereco Empresa
				String enderecoEmpresa = "";

				boolean virgula = false;

				if(sistemaParametro.getLogradouro() != null){
					Logradouro logradouro = sistemaParametro.getLogradouro();
					if(logradouro.getNome() != null && !logradouro.getNome().equals("")){
						enderecoEmpresa = sistemaParametro.getLogradouro().getNome();
						virgula = true;
					}
				}

				// Adiciona o número
				if(sistemaParametro.getNumeroImovel() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getNumeroImovel();
					virgula = true;
				}

				// Adiciona o complemento
				if(sistemaParametro.getComplementoEndereco() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getComplementoEndereco();
					virgula = true;
				}

				// Adiciona o bairro
				if(sistemaParametro.getBairro() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getBairro().getNome();
					virgula = true;
				}

				// Adiciona o cep
				if(sistemaParametro.getCep() != null){
					enderecoEmpresa += (virgula == true ? ", Cep: " : "") + sistemaParametro.getCep().getCepFormatado();
				}

				addParametro("enderecoEmpresa", enderecoEmpresa);

				// CNPJ da Empresa
				String cnpjFormatado = "";
				if(sistemaParametro.getCnpjEmpresa() != null){
					cnpjFormatado = Util.formatarCnpj(sistemaParametro.getCnpjEmpresa());
				}
				addParametro("cnpj", cnpjFormatado);

			}

			// Caso seja parcelamento de contas em cobrança bancária imprime termo com layout de
			// cobrança bancária
			if(existeCobrancaBancaria(colecaoContaValores)){
				FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();
				filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.DESCRICAO,
								"Termo de Parcelamento de Contas em Cobrança Bancária"));

				Collection<ResolucaoDiretoriaLayout> colecaoResolucaoDiretoriaLayout = Fachada.getInstancia().pesquisar(
								filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName());

				ResolucaoDiretoriaLayout resolucaoDiretoriaLayoutPadrao = (ResolucaoDiretoriaLayout) Util
								.retonarObjetoDeColecao(colecaoResolucaoDiretoriaLayout);

				nomeClasse = resolucaoDiretoriaLayoutPadrao.getNomeClasse();
				arquivoLayout = resolucaoDiretoriaLayoutPadrao.getNomeRelatorio();
				dados = this.getGeradorDados(nomeClasse, listaParcelamento, this.getIdFuncionalidadeIniciada());
				novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);
				for(int i = 0; i < resolucaoDiretoriaLayoutPadrao.getQuantidadeVias(); i++){
					pdfsGerados.add(novoPdf);
				}
				retorno = this.concatenarArquivos(pdfsGerados);
			}else{

				// Associar resolução diretoria a resolução diretoria layout
				Iterator<Parcelamento> itParcelamento = listaParcelamento.iterator();
				while(itParcelamento.hasNext()){
					Parcelamento parcelamentoAux = itParcelamento.next();
					this.addValorMapLayoutOrdens(mapLayoutResolucaoDiretoria, parcelamentoAux.getResolucaoDiretoria()
									.getResolucaoDiretoriaLayout().getId(), parcelamentoAux.getResolucaoDiretoria());
				}

				Set chaveLayouts = mapLayoutResolucaoDiretoria.keySet();
				Iterator iterMap = chaveLayouts.iterator();

				while(iterMap.hasNext()){
					// Para cada Layout

					Integer chaveLayout = (Integer) iterMap.next();
					Collection<ResolucaoDiretoria> listaResolucaoDiretoria = mapLayoutResolucaoDiretoria.get(chaveLayout);

					ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) Util.retonarObjetoDeColecao(listaResolucaoDiretoria);

					// Caso a resolução diretoria não possua layout associado, seta a resolução
					// diretoria layout padrão
					if(resolucaoDiretoria.getResolucaoDiretoriaLayout() != null){

						// verifica se a resolução diretoria irá imprimir 2 por página
						if(resolucaoDiretoria.getResolucaoDiretoriaLayout().getIndicadorImpressaoDoisPorPagina().intValue() == ConstantesSistema.SIM
										.intValue()){

							nomeClasse = resolucaoDiretoria.getResolucaoDiretoriaLayout().getNomeClasse();
							arquivoLayout = resolucaoDiretoria.getResolucaoDiretoriaLayout().getNomeRelatorio();
							dados = this.getGeradorDados(nomeClasse, listaParcelamento, this.getIdFuncionalidadeIniciada());
							novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);
							for(int i = 0; i < resolucaoDiretoria.getResolucaoDiretoriaLayout().getQuantidadeVias(); i++){
								pdfsGerados.add(novoPdf);
							}
							retorno = this.concatenarArquivos(pdfsGerados);

						}else{

							for(Iterator<Parcelamento> iterator = listaParcelamento.iterator(); iterator.hasNext();){

								parcelamento = (Parcelamento) iterator.next();
								nomeClasse = parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getNomeClasse();
								arquivoLayout = parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getNomeRelatorio();
								dados = this.getGeradorDados(nomeClasse, arquivoLayout, parcelamento, stringTextoHtmlEditado);
								novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);
								for(int i = 0; i < parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getQuantidadeVias(); i++){
									pdfsGerados.add(novoPdf);
								}
							}
							retorno = this.concatenarArquivos(pdfsGerados);
						}
					}else{
						FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();
						filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(
										FiltroResolucaoDiretoriaLayout.INDICADOR_PADRAO, ConstantesSistema.SIM));

						Collection<ResolucaoDiretoriaLayout> colecaoResolucaoDiretoriaLayout = Fachada.getInstancia().pesquisar(
										filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName());

						ResolucaoDiretoriaLayout resolucaoDiretoriaLayoutPadrao = (ResolucaoDiretoriaLayout) Util
										.retonarObjetoDeColecao(colecaoResolucaoDiretoriaLayout);

						nomeClasse = resolucaoDiretoriaLayoutPadrao.getNomeClasse();
						arquivoLayout = resolucaoDiretoriaLayoutPadrao.getNomeRelatorio();
						dados = this.getGeradorDados(nomeClasse, listaParcelamento, this.getIdFuncionalidadeIniciada());
						novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);
						for(int i = 0; i < resolucaoDiretoriaLayoutPadrao.getQuantidadeVias(); i++){
							pdfsGerados.add(novoPdf);
						}
						retorno = this.concatenarArquivos(pdfsGerados);
					}

				}
			}
		}

		return retorno;
	}

	/**
	 * Método responsável por gerar os relatórios de ordem de servico
	 * 
	 * @param idsParcelamento
	 * @return Um PDF.
	 * @throws GeradorRelatorioParcelamentoException
	 *             Casso ocorra algum erro na geração do relatório
	 * @throws ControladorException
	 */
	public byte[] gerarRelatorioResolucaoDiretoria(Collection<Integer> idsParcelamento, Collection<ContaValoresHelper> colecaoContaValores,
					String indicadorParcelamentoCobrancaBancaria, ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas,
					ParcelamentoDadosTermo parcelamentoDadosTermo, String stringTextoHtmlEditado)
					throws GeradorRelatorioParcelamentoException, ControladorException{

		Boolean consultarParcelamentoDebitos = (Boolean) this.getParametro("consultarParcelamentoDebitos");
		String arquivoLayout = null;
		String nomeClasse = null;
		List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados = null;
		byte[] novoPdf = null;
		byte[] retorno = null;
		List<byte[]> pdfsGerados = new ArrayList<byte[]>();
		Parcelamento parcelamento = null;
		Boolean indicadorExecucaoFiscal = false;

		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria");
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria.resolucaoDiretoriaLayout");
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("usuario");

		List<Parcelamento> listaParcelamento = (List<Parcelamento>) Fachada.getInstancia().pesquisar(idsParcelamento, filtroParcelamento,
						Parcelamento.class.getName());

		Map<Integer, Collection<ResolucaoDiretoria>> mapLayoutResolucaoDiretoria = new TreeMap<Integer, Collection<ResolucaoDiretoria>>();

		String P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO = ParametroCobranca.P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO
						.executar();

		if(listaParcelamento != null && !listaParcelamento.isEmpty()){

			// Parâmetros do relatório
			SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

			if(sistemaParametro != null){
				addParametro("imagem", sistemaParametro.getImagemRelatorio());

				// Empresa
				if(sistemaParametro.getNomeEmpresa() != null){

					addParametro("empresa", sistemaParametro.getNomeEmpresa());

				}else{
					addParametro("empresa", "");
				}

				// Endereco Empresa
				String enderecoEmpresa = "";

				boolean virgula = false;

				if(sistemaParametro.getLogradouro() != null){
					Logradouro logradouro = sistemaParametro.getLogradouro();
					if(logradouro.getNome() != null && !logradouro.getNome().equals("")){
						enderecoEmpresa = sistemaParametro.getLogradouro().getNome();
						virgula = true;
					}
				}

				// Adiciona o número
				if(sistemaParametro.getNumeroImovel() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getNumeroImovel();
					virgula = true;
				}

				// Adiciona o complemento
				if(sistemaParametro.getComplementoEndereco() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getComplementoEndereco();
					virgula = true;
				}

				// Adiciona o bairro
				if(sistemaParametro.getBairro() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getBairro().getNome();
					virgula = true;
				}

				// Adiciona o cep
				if(sistemaParametro.getCep() != null){
					enderecoEmpresa += (virgula == true ? ", Cep: " : "") + sistemaParametro.getCep().getCepFormatado();
				}

				addParametro("enderecoEmpresa", enderecoEmpresa);

				// CNPJ da Empresa
				String cnpjFormatado = "";
				if(sistemaParametro.getCnpjEmpresa() != null){
					cnpjFormatado = Util.formatarCnpj(sistemaParametro.getCnpjEmpresa());
				}
				addParametro("cnpj", cnpjFormatado);

			}

			// [UC0252]
			if(consultarParcelamentoDebitos != null && consultarParcelamentoDebitos){

				for(Parcelamento parcelamentoAux : listaParcelamento){


					if(P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO.equals(ConstantesSistema.ATIVO)){
						addParametro("loginUsuario", parcelamentoAux.getUsuario().getLogin());
						addParametro("dataParcelamento", parcelamentoAux.getDataEntradaParcelamento());
						addParametro("mesPortugues", Util.retornaDescricaoMes(Util.getMes(parcelamentoAux.getDataEntradaParcelamento())));
						addParametro("P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO", ConstantesSistema.ATIVO);
					}else{
						if(this.getUsuario().getLogin() != null){
							addParametro("loginUsuario", this.getUsuario().getLogin());
						}

						addParametro("mesPortugues", Util.retornaDescricaoMes(Util.getMes(new Date())));
						addParametro("P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO", ConstantesSistema.INATIVO);
					}

					int caracteristicaParcelamento = Fachada.getInstancia().obterCaracteristicaParcelamento(parcelamentoAux);

					parcelamentoAux.setParcelamentoTermoTestemunhas(parcelamentoTermoTestemunhas);

					FiltroParcelamentoDadosTermo filtroParcelamentoDadosTermo = new FiltroParcelamentoDadosTermo();
					filtroParcelamentoDadosTermo.adicionarParametro(new ParametroSimples(FiltroParcelamentoDadosTermo.PARCELAMENTO_ID,
									parcelamentoAux.getId()));
					Collection<ParcelamentoDadosTermo> colecaoParcelamentoDadosTermo = Fachada.getInstancia().pesquisar(
									filtroParcelamentoDadosTermo, ParcelamentoDadosTermo.class.getName());

					if(colecaoParcelamentoDadosTermo.size() > 0){
						// Mudar o Layout Quando foi armazenados dados relacionados a Execução
						// Fiscal
						parcelamentoAux.setParcelamentoDadosTermo(colecaoParcelamentoDadosTermo.iterator().next());
						if(parcelamentoAux.getParcelamentoDadosTermo().getParcelamentoAcordoTipo() != null
										&& parcelamentoAux.getParcelamentoDadosTermo().getParcelamentoAcordoTipo().getId() != null){
							indicadorExecucaoFiscal = true;

							FiltroParcelamentoAcordoTipo filtroParcelamentoAcordoTipo = new FiltroParcelamentoAcordoTipo();
							filtroParcelamentoAcordoTipo.adicionarParametro(new ParametroSimples(FiltroParcelamentoAcordoTipo.ID,
											parcelamentoAux.getParcelamentoDadosTermo().getParcelamentoAcordoTipo().getId()));

							Collection<ParcelamentoAcordoTipo> colecaoParcelamentoAcordoTipo = Fachada.getInstancia().pesquisar(
											filtroParcelamentoAcordoTipo, ParcelamentoAcordoTipo.class.getName());
							if(colecaoParcelamentoAcordoTipo.size() > 0){
								ParcelamentoAcordoTipo parcelamentoAcordoTipo = colecaoParcelamentoAcordoTipo.iterator().next();

								if(parcelamentoAcordoTipo.getIndicadorParcelamentoNormal().equals(2)){
									if(parcelamentoAcordoTipo.getDescricaoLayoutProcurador() != null){
										ResolucaoDiretoriaLayout resolucaoDiretoriaLayout = new ResolucaoDiretoriaLayout();
										resolucaoDiretoriaLayout.setIndicadorImpressaoDoisPorPagina(2);
										resolucaoDiretoriaLayout.setIndicadorUso(1);
										resolucaoDiretoriaLayout.setIndicadorPadrao(1);
										resolucaoDiretoriaLayout.setIndicadorSolicitaTestemunhas(2);
										resolucaoDiretoriaLayout.setDescricao(parcelamentoAcordoTipo.getDescricaoLayoutProcurador());
										resolucaoDiretoriaLayout.setNomeRelatorio(parcelamentoAcordoTipo.getNomeRelatorioProcurador());
										resolucaoDiretoriaLayout.setNomeClasse(parcelamentoAcordoTipo.getNomeClasseProcurador());
										resolucaoDiretoriaLayout.setQuantidadeVias(1);

										ResolucaoDiretoria resolucaoDiretoria = new ResolucaoDiretoria();
										resolucaoDiretoria.setResolucaoDiretoriaLayout(resolucaoDiretoriaLayout);

										parcelamentoAux.setResolucaoDiretoria(resolucaoDiretoria);
									}
								}
							}
						}
					}

					switch(caracteristicaParcelamento){

					// 13.1. Caso seja parcelamento de cobrança bancária (CBFM_ID da tabela
					// PARCELAMENTO=CBFM_ID da tabela COBRANCA_FORMA com
					// CBFM_DSCOBRANCAFORMA=“BOLETO BANCARIO”):
						case 1:


							// 13.1.1. Caso o termo da RD do parcelamento prevaleça sobre o termo do
							// parcelamento de cobrança bancária (RDIR_ID da tabela PARCELAMENTO
							// contido
							// em PASI_VLPARAMETROS da tabela PARAMETRO_SISTEMA com
							// PASI_CDPARAMETRO=”P_LISTA_RD_COM_TERMO_PREFERENCIAL_AO_TERMO_COBRANCABANCARIA”):
							if((parcelamentoAux.getResolucaoDiretoria() != null && this.termoRdPrevalece(parcelamentoAux
											.getResolucaoDiretoria().getId())) || indicadorExecucaoFiscal){

								// 13.1.1.1. Imprime termo da RD
								// [SB0005 – Emite Termo da RD].
								if(parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

									this.adicionarArquivosGerados(parcelamentoAux, this.obterResolucaoDiretoriaLayoutPadrao(), pdfsGerados,
													stringTextoHtmlEditado);

								}else{

									this.adicionarArquivosGerados(parcelamentoAux, parcelamentoAux.getResolucaoDiretoria()
													.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado);
								}

							}else{

								// 13.1.2. Caso contrário, ou seja, caso o termo da RD do
								// parcelamento
								// não prevaleça sobre o termo do parcelamento de cobrança bancária:
								// 13.1.2.1. Imprime termo da cobrança bancária (RDLY_NMRELATORIO,
								// RDLY_QTVIAS e RDLY_DSLAYOUT da tabela RESOLUCAO_DIRETORIA_LAYOUT
								// com
								// RDLY_ID=PASI_VLPARAMETRO da tabela PARAMETRO_SISTEMA com
								// PASI_CDPARAMETRO=”P_LAYOUT_PARCELAMENTO_COBRANCA_BANCARIA”).
								this.adicionarArquivosGerados(parcelamentoAux, this.obterResolucaoDiretoriaLayoutParcCobrancaBancaria(),
												pdfsGerados, stringTextoHtmlEditado);
							}
							break;
						case 2:

							// 13.2. Caso contrário, ou seja, caso não seja parcelamento de cobrança
							// bancária (CBFM_ID da tabela PARCELAMENTO=CBFM_ID da tabela
							// COBRANCA_FORMA com CBFM_DSCOBRANCAFORMA diferente “BOLETO BANCARIO”):

							parcelamentoAux.setParcelamentoTermoTestemunhas(parcelamentoTermoTestemunhas);

							if(!indicadorExecucaoFiscal){
								// Imprime termo com layout de cobrança administrativa
								this.adicionarArquivosGerados(parcelamentoAux, Fachada.getInstancia()
												.obterResolucaoDiretoriaLayoutParcCobrancaAdministrativa(), pdfsGerados,
												stringTextoHtmlEditado);
							}else{

								if(parcelamentoAux == null || parcelamentoAux.getResolucaoDiretoria() == null
												|| parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

									this.adicionarArquivosGerados(parcelamentoAux, this.obterResolucaoDiretoriaLayoutPadrao(), pdfsGerados,
													stringTextoHtmlEditado);

								}else{

									this.adicionarArquivosGerados(parcelamentoAux, parcelamentoAux.getResolucaoDiretoria()
													.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado);

								}

							}

							break;
						case 3:
							// Caso contrário, ou seja, não seja parcelamento de cobrança
							// administrativa:

							// Imprime termo da RD
							// [SB0037 – Emite Termo da RD]

							if(parcelamentoAux == null || parcelamentoAux.getResolucaoDiretoria() == null
											|| parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

								this.adicionarArquivosGerados(parcelamentoAux, this.obterResolucaoDiretoriaLayoutPadrao(), pdfsGerados,
												stringTextoHtmlEditado);

							}else{

								this.adicionarArquivosGerados(parcelamentoAux, parcelamentoAux.getResolucaoDiretoria()
												.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado);

							}
							break;

						default:
							break;
					}
				}

			}else{

				// [UC0214]
				for(Parcelamento parcelamentoAux : listaParcelamento){

					if(P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO.equals(ConstantesSistema.ATIVO)){
						addParametro("loginUsuario", parcelamentoAux.getUsuario().getLogin());
						addParametro("dataParcelamento", parcelamentoAux.getDataEntradaParcelamento());
						addParametro("mesPortugues", Util.retornaDescricaoMes(Util.getMes(parcelamentoAux.getDataEntradaParcelamento())));
						addParametro("P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO", ConstantesSistema.ATIVO);
					}else{
						if(this.getUsuario().getLogin() != null){
							addParametro("loginUsuario", this.getUsuario().getLogin());
						}

						addParametro("mesPortugues", Util.retornaDescricaoMes(Util.getMes(new Date())));
						addParametro("P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO", ConstantesSistema.INATIVO);
					}

					FiltroParcelamentoDadosTermo filtroParcelamentoDadosTermo = new FiltroParcelamentoDadosTermo();
					filtroParcelamentoDadosTermo.adicionarParametro(new ParametroSimples(FiltroParcelamentoDadosTermo.PARCELAMENTO_ID,
									parcelamentoAux.getId()));
					Collection<ParcelamentoDadosTermo> colecaoParcelamentoDadosTermo = Fachada.getInstancia().pesquisar(
									filtroParcelamentoDadosTermo, ParcelamentoDadosTermo.class.getName());

					if(colecaoParcelamentoDadosTermo.size() > 0){
						// Mudar o Layout Quando foi armazenados dados relacionados a Execução
						// Fiscal
						parcelamentoAux.setParcelamentoDadosTermo(colecaoParcelamentoDadosTermo.iterator().next());
						if(parcelamentoAux.getParcelamentoDadosTermo().getParcelamentoAcordoTipo() != null
										&& parcelamentoAux.getParcelamentoDadosTermo().getParcelamentoAcordoTipo().getId() != null){
							indicadorExecucaoFiscal = true;

							FiltroParcelamentoAcordoTipo filtroParcelamentoAcordoTipo = new FiltroParcelamentoAcordoTipo();
							filtroParcelamentoAcordoTipo.adicionarParametro(new ParametroSimples(FiltroParcelamentoAcordoTipo.ID,
											parcelamentoAux.getParcelamentoDadosTermo().getParcelamentoAcordoTipo().getId()));

							Collection<ParcelamentoAcordoTipo> colecaoParcelamentoAcordoTipo = Fachada.getInstancia().pesquisar(
											filtroParcelamentoAcordoTipo, ParcelamentoAcordoTipo.class.getName());
							if(colecaoParcelamentoAcordoTipo.size() > 0){
								ParcelamentoAcordoTipo parcelamentoAcordoTipo = colecaoParcelamentoAcordoTipo.iterator().next();

								if(parcelamentoAcordoTipo.getIndicadorParcelamentoNormal().equals(2)){
									if(parcelamentoAcordoTipo.getDescricaoLayoutProcurador() != null){
										ResolucaoDiretoriaLayout resolucaoDiretoriaLayout = new ResolucaoDiretoriaLayout();
										resolucaoDiretoriaLayout.setIndicadorImpressaoDoisPorPagina(2);
										resolucaoDiretoriaLayout.setIndicadorUso(1);
										resolucaoDiretoriaLayout.setIndicadorPadrao(1);
										resolucaoDiretoriaLayout.setIndicadorSolicitaTestemunhas(2);
										resolucaoDiretoriaLayout.setDescricao(parcelamentoAcordoTipo.getDescricaoLayoutProcurador());
										resolucaoDiretoriaLayout.setNomeRelatorio(parcelamentoAcordoTipo.getNomeRelatorioProcurador());
										resolucaoDiretoriaLayout.setNomeClasse(parcelamentoAcordoTipo.getNomeClasseProcurador());
										resolucaoDiretoriaLayout.setQuantidadeVias(1);

										ResolucaoDiretoria resolucaoDiretoria = new ResolucaoDiretoria();
										resolucaoDiretoria.setResolucaoDiretoriaLayout(resolucaoDiretoriaLayout);

										parcelamentoAux.setResolucaoDiretoria(resolucaoDiretoria);
									}
								}
							}
						}
					}

					int caracteristicaParcelamento = Fachada.getInstancia().obterCaracteristicaParcelamento(parcelamentoAux);

					parcelamentoAux.setParcelamentoTermoTestemunhas(parcelamentoTermoTestemunhas);

					switch(caracteristicaParcelamento){
						case 1:

							// 7.1.11.1. Caso seja parcelamento de contas em cobrança bancária
							// (campo
							// “Parcelamento de Cobrança Bancária?” com a opção “Sim” selecionada):

							// 7.1.11.1.1. Caso o termo da RD do parcelamento prevaleça sobre o
							// termo do parcelamento de cobrança bancária (RDIR_ID da tabela
							// PARCELAMENTO contido em PASI_VLPARAMETROS da tabela PARAMETRO_SISTEMA
							// com
							// PASI_CDPARAMETRO=”P_LISTA_RD_COM_TERMO_PREFERENCIAL_AO_TERMO_COBRANCABANCARIA”):
							if((parcelamentoAux.getResolucaoDiretoria() != null && this.termoRdPrevalece(parcelamentoAux
											.getResolucaoDiretoria().getId())) || indicadorExecucaoFiscal){

								// 1. Caso a RD não possua layout associado (RDLY_ID com o valor
								// igual a nulo na tabela RESOLUCAO_DIRETORIA):
								// 1.1. Imprime layout padrão (RDLY_NMRELATORIO, RDLY_QTVIAS e
								// RDLY_DSLAYOUT da tabela RESOLUCAO_DIRETORIA_LAYOUT com
								// RDLY_ICPADRAO=1).
								// 2. Caso contrário, ou seja, a RD possui layout associado (RDLY_ID
								// com o valor diferente de nulo na tabela RESOLUCAO_DIRETORIA):
								// 2.1. Imprime layout da RD (RDLY_NMRELATORIO, RDLY_QTVIAS e
								// RDLY_DSLAYOUT da tabela RESOLUCAO_DIRETORIA_LAYOUT com
								// RDLY_ID=RDLY_ID da tabela RESOLUCAO_DIRETORIA).
								if(parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

									this.adicionarArquivosGerados(parcelamentoAux, this.obterResolucaoDiretoriaLayoutPadrao(), pdfsGerados,
													stringTextoHtmlEditado);

								}else{

									this.adicionarArquivosGerados(parcelamentoAux, parcelamentoAux.getResolucaoDiretoria()
													.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado);

								}

							}else{

								// 7.1.11.1.2. Caso contrário, ou seja, caso o termo da RD do
								// parcelamento não prevaleça sobre o termo do parcelamento de
								// cobrança bancária:
								// 7.1.11.1.2.1. Imprime termo com layout de cobrança bancária
								// (RDLY_NMRELATORIO, RDLY_QTVIAS e RDLY_DSLAYOUT da tabela
								// RESOLUCAO_DIRETORIA_LAYOUT com RDLY_ID=PASI_VLPARAMETRO da tabela
								// PARAMETRO_SISTEMA com
								// PASI_CDPARAMETRO=”P_LAYOUT_PARCELAMENTO_COBRANCA_BANCARIA”).
								this.adicionarArquivosGerados(parcelamentoAux, this.obterResolucaoDiretoriaLayoutParcCobrancaBancaria(),
												pdfsGerados, stringTextoHtmlEditado);
							}

							break;

						case 2:

							parcelamentoAux.setParcelamentoTermoTestemunhas(parcelamentoTermoTestemunhas);

							if(!indicadorExecucaoFiscal){
								// 7.1.16.2.2.1. Imprime termo com layout de cobrança administrativa
								this.adicionarArquivosGerados(parcelamentoAux, Fachada.getInstancia()
												.obterResolucaoDiretoriaLayoutParcCobrancaAdministrativa(), pdfsGerados,
												stringTextoHtmlEditado);
							}else{

								if(parcelamentoAux == null || parcelamentoAux.getResolucaoDiretoria() == null
												|| parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

									this.adicionarArquivosGerados(parcelamentoAux, this.obterResolucaoDiretoriaLayoutPadrao(), pdfsGerados,
													stringTextoHtmlEditado);

								}else{

									this.adicionarArquivosGerados(parcelamentoAux, parcelamentoAux.getResolucaoDiretoria()
													.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado);

								}

							}

							break;

						case 3:
							// 7.1.16.2.3. Caso contrário, ou seja, não seja parcelamento de
							// cobrança administrativa:

							// 7.1.11.2.1. Imprime termo da RD
							// [SB0037 – Emite Termo da RD].

							if(parcelamentoAux.getResolucaoDiretoria() == null
											|| parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

								this.adicionarArquivosGerados(parcelamentoAux, this.obterResolucaoDiretoriaLayoutPadrao(), pdfsGerados,
												stringTextoHtmlEditado);

							}else{

								this.adicionarArquivosGerados(parcelamentoAux, parcelamentoAux.getResolucaoDiretoria()
												.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado);

							}

							break;
						default:
							break;
					}
				}
			}

			retorno = this.concatenarArquivos(pdfsGerados);

			if(retorno == null){

				ResolucaoDiretoriaLayout resolucaoDiretoriaLayoutPadrao = this.obterResolucaoDiretoriaLayoutPadrao();

				// Associar resolução diretoria a resolução diretoria layout
				Iterator<Parcelamento> itParcelamento = listaParcelamento.iterator();
				while(itParcelamento.hasNext()){
					Parcelamento parcelamentoAux = itParcelamento.next();

					// Caso a resolução diretoria não possua layout associado, seta a resolução
					// diretoria layout padrão
					if(parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

						parcelamentoAux.getResolucaoDiretoria().setResolucaoDiretoriaLayout(resolucaoDiretoriaLayoutPadrao);
						this.addValorMapLayoutOrdens(mapLayoutResolucaoDiretoria, parcelamentoAux.getResolucaoDiretoria()
										.getResolucaoDiretoriaLayout().getId(), parcelamentoAux.getResolucaoDiretoria());
					}else{

						this.addValorMapLayoutOrdens(mapLayoutResolucaoDiretoria, parcelamentoAux.getResolucaoDiretoria()
										.getResolucaoDiretoriaLayout().getId(), parcelamentoAux.getResolucaoDiretoria());
					}
				}

				Set chaveLayouts = mapLayoutResolucaoDiretoria.keySet();
				Iterator iterMap = chaveLayouts.iterator();

				while(iterMap.hasNext()){
					// Para cada Layout

					Integer chaveLayout = (Integer) iterMap.next();
					Collection<ResolucaoDiretoria> listaResolucoesDiretoria = mapLayoutResolucaoDiretoria.get(chaveLayout);

					ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) Util.retonarObjetoDeColecao(listaResolucoesDiretoria);

					// verifica se a resolução diretoria irá imprimir 2 por página
					if(resolucaoDiretoria.getResolucaoDiretoriaLayout().getIndicadorImpressaoDoisPorPagina().intValue() == ConstantesSistema.SIM
									.intValue()){

						for(Iterator<Parcelamento> iterator = listaParcelamento.iterator(); iterator.hasNext();){
							parcelamento = (Parcelamento) iterator.next();
							nomeClasse = resolucaoDiretoria.getResolucaoDiretoriaLayout().getNomeClasse();
							arquivoLayout = resolucaoDiretoria.getResolucaoDiretoriaLayout().getNomeRelatorio();
							dados = this.getGeradorDados(nomeClasse, arquivoLayout, parcelamento, stringTextoHtmlEditado);
							novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);
							for(int i = 0; i < resolucaoDiretoria.getResolucaoDiretoriaLayout().getQuantidadeVias(); i++){
								pdfsGerados.add(novoPdf);
							}
						}
						retorno = this.concatenarArquivos(pdfsGerados);

					}else{

						for(Iterator<Parcelamento> iterator = listaParcelamento.iterator(); iterator.hasNext();){

							parcelamento = (Parcelamento) iterator.next();
							nomeClasse = parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getNomeClasse();
							arquivoLayout = parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getNomeRelatorio();
							dados = this.getGeradorDados(nomeClasse, arquivoLayout, parcelamento, stringTextoHtmlEditado);
							novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);

							for(int i = 0; i < parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getQuantidadeVias(); i++){

								pdfsGerados.add(novoPdf);

							}

						}

						retorno = this.concatenarArquivos(pdfsGerados);

					}

				}

			}

		}

		return retorno;

	}

	/**
	 * Método responsável por gerar os relatórios de ordem de servico
	 * 
	 * @param idsParcelamento
	 * @return Um PDF.
	 * @throws GeradorRelatorioParcelamentoException
	 *             Casso ocorra algum erro na geração do relatório
	 * @throws ControladorException
	 */
	public byte[] gerarRelatorioResolucaoDiretoria(Parcelamento parcelamentoEfetuado, Collection<ContaValoresHelper> colecaoContaValores,
					String indicadorParcelamentoCobrancaBancaria, ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas,
					ParcelamentoDadosTermo parcelamentoDadosTermo, Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores,
					Collection<DebitoACobrar> colecaoDebitoACobrarItem, Collection<CreditoARealizar> colecaoCreditoARealizar,
					String indicadorCobrancaBancaria, String stringTextoHtmlEditado, Integer numeroDiasVencimentoEntrada)
					throws GeradorRelatorioParcelamentoException, ControladorException{

		String arquivoLayout = null;
		String nomeClasse = null;
		List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados = null;
		byte[] novoPdf = null;
		byte[] retorno = null;
		List<byte[]> pdfsGerados = new ArrayList<byte[]>();
		Parcelamento parcelamento = null;
		Fachada fachada = Fachada.getInstancia();
		boolean indicadorExecucaoFiscal = false;

		Map<Integer, Collection<ResolucaoDiretoria>> mapLayoutResolucaoDiretoria = new TreeMap<Integer, Collection<ResolucaoDiretoria>>();

		String P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO = ParametroCobranca.P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO
						.executar();

		if(parcelamentoEfetuado != null){
			if(parcelamentoTermoTestemunhas != null){
				parcelamentoEfetuado.setParcelamentoTermoTestemunhas(parcelamentoTermoTestemunhas);
			}
			
			if(parcelamentoDadosTermo != null){
				parcelamentoEfetuado.setParcelamentoDadosTermo(parcelamentoDadosTermo);

				// Mudar o Layout Quando foi armazenados dados relacionados a Execução Fiscal
				if(parcelamentoEfetuado.getParcelamentoDadosTermo().getParcelamentoAcordoTipo() != null
								&& parcelamentoEfetuado.getParcelamentoDadosTermo().getParcelamentoAcordoTipo().getId() != null){
					indicadorExecucaoFiscal = true;

					FiltroParcelamentoAcordoTipo filtroParcelamentoAcordoTipo= new FiltroParcelamentoAcordoTipo();
					filtroParcelamentoAcordoTipo.adicionarParametro(new ParametroSimples(FiltroParcelamentoAcordoTipo.ID,
									parcelamentoEfetuado.getParcelamentoDadosTermo().getParcelamentoAcordoTipo().getId()));
					
					Collection<ParcelamentoAcordoTipo> colecaoParcelamentoAcordoTipo = fachada.pesquisar(filtroParcelamentoAcordoTipo,
									ParcelamentoAcordoTipo.class.getName());
					if(colecaoParcelamentoAcordoTipo.size() > 0){
						ParcelamentoAcordoTipo parcelamentoAcordoTipo = colecaoParcelamentoAcordoTipo.iterator().next();

						if(parcelamentoAcordoTipo.getIndicadorParcelamentoNormal().equals(2)){
							if(parcelamentoAcordoTipo.getDescricaoLayoutProcurador() != null){
								ResolucaoDiretoriaLayout resolucaoDiretoriaLayout = new ResolucaoDiretoriaLayout();
								resolucaoDiretoriaLayout.setIndicadorImpressaoDoisPorPagina(2);
								resolucaoDiretoriaLayout.setIndicadorUso(1);
								resolucaoDiretoriaLayout.setIndicadorPadrao(1);
								resolucaoDiretoriaLayout.setIndicadorSolicitaTestemunhas(2);
								resolucaoDiretoriaLayout.setDescricao(parcelamentoAcordoTipo.getDescricaoLayoutProcurador());
								resolucaoDiretoriaLayout.setNomeRelatorio(parcelamentoAcordoTipo.getNomeRelatorioProcurador());
								resolucaoDiretoriaLayout.setNomeClasse(parcelamentoAcordoTipo.getNomeClasseProcurador());
								resolucaoDiretoriaLayout.setQuantidadeVias(1);

								ResolucaoDiretoria resolucaoDiretoria = new ResolucaoDiretoria();
								resolucaoDiretoria.setResolucaoDiretoriaLayout(resolucaoDiretoriaLayout);

								parcelamentoEfetuado.setResolucaoDiretoria(resolucaoDiretoria);
							}
						}
					}
				}

			}

			// Parâmetros do relatório
			SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

			if(sistemaParametro != null){
				addParametro("imagem", sistemaParametro.getImagemRelatorio());

				// Empresa
				if(sistemaParametro.getNomeEmpresa() != null){

					addParametro("empresa", sistemaParametro.getNomeEmpresa());

				}else{
					addParametro("empresa", "");
				}

				// Endereco Empresa
				String enderecoEmpresa = "";

				boolean virgula = false;

				if(sistemaParametro.getLogradouro() != null){
					Logradouro logradouro = sistemaParametro.getLogradouro();
					if(logradouro.getNome() != null && !logradouro.getNome().equals("")){
						enderecoEmpresa = sistemaParametro.getLogradouro().getNome();
						virgula = true;
					}
				}

				// Adiciona o número
				if(sistemaParametro.getNumeroImovel() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getNumeroImovel();
					virgula = true;
				}

				// Adiciona o complemento
				if(sistemaParametro.getComplementoEndereco() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getComplementoEndereco();
					virgula = true;
				}

				// Adiciona o bairro
				if(sistemaParametro.getBairro() != null){
					enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getBairro().getNome();
					virgula = true;
				}

				// Adiciona o cep
				if(sistemaParametro.getCep() != null){
					enderecoEmpresa += (virgula == true ? ", Cep: " : "") + sistemaParametro.getCep().getCepFormatado();
				}

				addParametro("enderecoEmpresa", enderecoEmpresa);

				// CNPJ da Empresa
				String cnpjFormatado = "";
				if(sistemaParametro.getCnpjEmpresa() != null){
					cnpjFormatado = Util.formatarCnpj(sistemaParametro.getCnpjEmpresa());
				}
				addParametro("cnpj", cnpjFormatado);

			}

			// [UC0252]
			// [UC0214]
			Parcelamento parcelamentoAux = parcelamentoEfetuado;
			if(P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO.equals(ConstantesSistema.ATIVO)){
				addParametro("loginUsuario", parcelamentoAux.getUsuario().getLogin());
				addParametro("dataParcelamento", parcelamentoAux.getDataEntradaParcelamento());
				addParametro("mesPortugues", Util.retornaDescricaoMes(Util.getMes(parcelamentoAux.getDataEntradaParcelamento())));
				addParametro("P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO", ConstantesSistema.ATIVO);
			}else{
				if(this.getUsuario().getLogin() != null){
					addParametro("loginUsuario", this.getUsuario().getLogin());
				}

				addParametro("mesPortugues", Util.retornaDescricaoMes(Util.getMes(new Date())));
				addParametro("P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO", ConstantesSistema.INATIVO);
			}

			int caracteristicaParcelamento = fachada.obterCaracteristicaParcelamento(colecaoContaValores, colecaoGuiaPagamentoValores,
							parcelamentoEfetuado.getCobrancaForma().getId(), indicadorCobrancaBancaria, colecaoDebitoACobrarItem);

			switch(caracteristicaParcelamento){
				case 1:

					// 7.1.11.1. Caso seja parcelamento de contas em cobrança bancária
					// (campo
					// “Parcelamento de Cobrança Bancária?” com a opção “Sim” selecionada):

					// 7.1.11.1.1. Caso o termo da RD do parcelamento prevaleça sobre o
					// termo do parcelamento de cobrança bancária (RDIR_ID da tabela
					// PARCELAMENTO contido em PASI_VLPARAMETROS da tabela PARAMETRO_SISTEMA
					// com
					// PASI_CDPARAMETRO=”P_LISTA_RD_COM_TERMO_PREFERENCIAL_AO_TERMO_COBRANCABANCARIA”):
					if((parcelamentoAux.getResolucaoDiretoria() != null
									&& this.termoRdPrevalece(parcelamentoAux.getResolucaoDiretoria().getId())) || indicadorExecucaoFiscal) {

						// 1. Caso a RD não possua layout associado (RDLY_ID com o valor
						// igual a nulo na tabela RESOLUCAO_DIRETORIA):
						// 1.1. Imprime layout padrão (RDLY_NMRELATORIO, RDLY_QTVIAS e
						// RDLY_DSLAYOUT da tabela RESOLUCAO_DIRETORIA_LAYOUT com
						// RDLY_ICPADRAO=1).
						// 2. Caso contrário, ou seja, a RD possui layout associado (RDLY_ID
						// com o valor diferente de nulo na tabela RESOLUCAO_DIRETORIA):
						// 2.1. Imprime layout da RD (RDLY_NMRELATORIO, RDLY_QTVIAS e
						// RDLY_DSLAYOUT da tabela RESOLUCAO_DIRETORIA_LAYOUT com
						// RDLY_ID=RDLY_ID da tabela RESOLUCAO_DIRETORIA).
						if(parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

							this.adicionarPreviewArquivosGerados(parcelamentoAux, colecaoContaValores, colecaoGuiaPagamentoValores,
											colecaoDebitoACobrarItem, colecaoCreditoARealizar, this.obterResolucaoDiretoriaLayoutPadrao(),
											pdfsGerados, stringTextoHtmlEditado, numeroDiasVencimentoEntrada);

						}else{

							this.adicionarPreviewArquivosGerados(parcelamentoAux, colecaoContaValores, colecaoGuiaPagamentoValores,
											colecaoDebitoACobrarItem, colecaoCreditoARealizar, parcelamentoAux.getResolucaoDiretoria()
															.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado,
											numeroDiasVencimentoEntrada);

						}

					}else{

						// 7.1.11.1.2. Caso contrário, ou seja, caso o termo da RD do
						// parcelamento não prevaleça sobre o termo do parcelamento de
						// cobrança bancária:
						// 7.1.11.1.2.1. Imprime termo com layout de cobrança bancária
						// (RDLY_NMRELATORIO, RDLY_QTVIAS e RDLY_DSLAYOUT da tabela
						// RESOLUCAO_DIRETORIA_LAYOUT com RDLY_ID=PASI_VLPARAMETRO da tabela
						// PARAMETRO_SISTEMA com
						// PASI_CDPARAMETRO=”P_LAYOUT_PARCELAMENTO_COBRANCA_BANCARIA”).
						this.adicionarPreviewArquivosGerados(parcelamentoAux, colecaoContaValores, colecaoGuiaPagamentoValores,
										colecaoDebitoACobrarItem, colecaoCreditoARealizar,
										this.obterResolucaoDiretoriaLayoutParcCobrancaBancaria(), pdfsGerados, stringTextoHtmlEditado,
										numeroDiasVencimentoEntrada);
					}

					break;

				case 2:

					parcelamentoAux.setParcelamentoTermoTestemunhas(parcelamentoTermoTestemunhas);

					// 7.1.16.2.2.1. Imprime termo com layout de cobrança administrativa
					if (indicadorExecucaoFiscal == false) {
						this.adicionarPreviewArquivosGerados(parcelamentoAux, colecaoContaValores, colecaoGuiaPagamentoValores,
										colecaoDebitoACobrarItem, colecaoCreditoARealizar, Fachada.getInstancia()
														.obterResolucaoDiretoriaLayoutParcCobrancaAdministrativa(), pdfsGerados,
										stringTextoHtmlEditado, numeroDiasVencimentoEntrada);
					} else {
						if(parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

							this.adicionarPreviewArquivosGerados(parcelamentoAux, colecaoContaValores, colecaoGuiaPagamentoValores,
											colecaoDebitoACobrarItem, colecaoCreditoARealizar, this.obterResolucaoDiretoriaLayoutPadrao(),
											pdfsGerados, stringTextoHtmlEditado, numeroDiasVencimentoEntrada);

						}else{

							this.adicionarPreviewArquivosGerados(parcelamentoAux, colecaoContaValores, colecaoGuiaPagamentoValores,
											colecaoDebitoACobrarItem, colecaoCreditoARealizar, parcelamentoAux.getResolucaoDiretoria()
															.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado,
											numeroDiasVencimentoEntrada);

						}					
					}

					break;

				case 3:
					// 7.1.16.2.3. Caso contrário, ou seja, não seja parcelamento de
					// cobrança administrativa:

					// 7.1.11.2.1. Imprime termo da RD
					// [SB0037 – Emite Termo da RD].

					if(parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

						this.adicionarPreviewArquivosGerados(parcelamentoAux, colecaoContaValores, colecaoGuiaPagamentoValores,
										colecaoDebitoACobrarItem, colecaoCreditoARealizar, this.obterResolucaoDiretoriaLayoutPadrao(),
										pdfsGerados, stringTextoHtmlEditado, numeroDiasVencimentoEntrada);

					}else{

						this.adicionarPreviewArquivosGerados(parcelamentoAux, colecaoContaValores, colecaoGuiaPagamentoValores,
										colecaoDebitoACobrarItem, colecaoCreditoARealizar, parcelamentoAux.getResolucaoDiretoria()
														.getResolucaoDiretoriaLayout(), pdfsGerados, stringTextoHtmlEditado,
										numeroDiasVencimentoEntrada);

					}

					break;
				default:
					break;
			}

			retorno = this.concatenarArquivos(pdfsGerados);

			if(retorno == null){

				ResolucaoDiretoriaLayout resolucaoDiretoriaLayoutPadrao = this.obterResolucaoDiretoriaLayoutPadrao();

				// Associar resolução diretoria a resolução diretoria layout
				parcelamentoAux = parcelamentoEfetuado;

				// Caso a resolução diretoria não possua layout associado, seta a resolução
				// diretoria layout padrão
				if(parcelamentoAux.getResolucaoDiretoria().getResolucaoDiretoriaLayout() == null){

					parcelamentoAux.getResolucaoDiretoria().setResolucaoDiretoriaLayout(resolucaoDiretoriaLayoutPadrao);
					this.addValorMapLayoutOrdens(mapLayoutResolucaoDiretoria, parcelamentoAux.getResolucaoDiretoria()
									.getResolucaoDiretoriaLayout().getId(), parcelamentoAux.getResolucaoDiretoria());
				}else{

					this.addValorMapLayoutOrdens(mapLayoutResolucaoDiretoria, parcelamentoAux.getResolucaoDiretoria()
									.getResolucaoDiretoriaLayout().getId(), parcelamentoAux.getResolucaoDiretoria());
				}

				Set chaveLayouts = mapLayoutResolucaoDiretoria.keySet();
				Iterator iterMap = chaveLayouts.iterator();

				while(iterMap.hasNext()){
					// Para cada Layout

					Integer chaveLayout = (Integer) iterMap.next();
					Collection<ResolucaoDiretoria> listaResolucoesDiretoria = mapLayoutResolucaoDiretoria.get(chaveLayout);

					ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) Util.retonarObjetoDeColecao(listaResolucoesDiretoria);

					// verifica se a resolução diretoria irá imprimir 2 por página
					if(resolucaoDiretoria.getResolucaoDiretoriaLayout().getIndicadorImpressaoDoisPorPagina().intValue() == ConstantesSistema.SIM
									.intValue()){

						parcelamento = parcelamentoEfetuado;
						nomeClasse = resolucaoDiretoria.getResolucaoDiretoriaLayout().getNomeClasse();
						arquivoLayout = resolucaoDiretoria.getResolucaoDiretoriaLayout().getNomeRelatorio();
						dados = this.getGeradorDados(nomeClasse, arquivoLayout, parcelamento, stringTextoHtmlEditado);
						novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);
						for(int i = 0; i < resolucaoDiretoria.getResolucaoDiretoriaLayout().getQuantidadeVias(); i++){
							pdfsGerados.add(novoPdf);
						}
						retorno = this.concatenarArquivos(pdfsGerados);

					}else{

						parcelamento = parcelamentoEfetuado;
						nomeClasse = parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getNomeClasse();
						arquivoLayout = parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getNomeRelatorio();
						dados = this.getGeradorDados(nomeClasse, arquivoLayout, parcelamento, stringTextoHtmlEditado);
						novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);

						for(int i = 0; i < parcelamento.getResolucaoDiretoria().getResolucaoDiretoriaLayout().getQuantidadeVias(); i++){

							pdfsGerados.add(novoPdf);

						}

						retorno = this.concatenarArquivos(pdfsGerados);

					}

				}

			}

		}

		return retorno;

	}

	private List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> getGeradorDados(String nomeClasse, String arquivoLayout,
					Parcelamento parcelamento,
					String stringTextoHtmlEditado)
					throws GeradorRelatorioParcelamentoException{

		List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados = null;
		try{
			Class clazz = Class.forName(nomeClasse);
			GeradorDadosRelatorioResolucaoDiretoria geradorDados = (GeradorDadosRelatorioResolucaoDiretoria) clazz.newInstance();
			dados = geradorDados.gerarDados(parcelamento, this.getIdFuncionalidadeIniciada());

			if(geradorDados.getParametroTarefa() != null && !geradorDados.getParametroTarefa().isEmpty()){
				adicionarParametrosTarefa(geradorDados.getParametroTarefa());
			}

			String stringHtml = "";
			for(int index = 0; index < dados.size(); index++){
				if(stringTextoHtmlEditado.trim().length() == 0){
					stringHtml = geradorDados.gerarTextoHtml(dados.get(index), arquivoLayout, parcelamento);
				}else{
					stringHtml = stringTextoHtmlEditado;
				}

				dados.get(index).setTextoHtml(stringHtml);
				this.setTextoHtml(stringHtml);
			}

		}catch(ClassNotFoundException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(SecurityException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(InstantiationException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(IllegalAccessException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}

		return dados;
	}

	private List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> getGeradorPreviewDados(String nomeClasse, String arquivoLayout,
					Parcelamento parcelamento, Collection<ContaValoresHelper> colecaoContaValores,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores, Collection<DebitoACobrar> colecaoDebitoACobrar,
					Collection<CreditoARealizar> colecaoCreditoARealizar, String stringTextoHtmlEditado, Integer numeroDiasVencimentoEntrada)
					throws GeradorRelatorioParcelamentoException{

		List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados = null;
		try{
			Class clazz = Class.forName(nomeClasse);
			GeradorDadosRelatorioResolucaoDiretoria geradorDados = (GeradorDadosRelatorioResolucaoDiretoria) clazz.newInstance();
			dados = geradorDados.gerarDados(parcelamento, colecaoContaValores, colecaoGuiaPagamentoValores, colecaoDebitoACobrar,
							colecaoCreditoARealizar, numeroDiasVencimentoEntrada);

			if(geradorDados.getParametroTarefa() != null && !geradorDados.getParametroTarefa().isEmpty()){
				adicionarParametrosTarefa(geradorDados.getParametroTarefa());
			}


			String stringHtml = "";
			for(int index = 0; index < dados.size(); index++){
				if(stringTextoHtmlEditado.trim().length() == 0){
					stringHtml = geradorDados.gerarTextoHtml(dados.get(index), arquivoLayout, parcelamento);
				}else{
					stringHtml = stringTextoHtmlEditado;
				}

				dados.get(index).setTextoHtml(stringHtml);
				this.setTextoHtml(stringHtml);
			}

		}catch(ClassNotFoundException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(SecurityException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(InstantiationException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(IllegalAccessException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}

		return dados;
	}

	/**
	 * 2 ResolucaoDiretoria por páginas
	 * 
	 * @author isilva
	 * @param nomeClasse
	 * @param ordensServicos
	 * @return
	 * @throws GeradorRelatorioParcelamentoException
	 */
	private List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> getGeradorDados(String nomeClasse,
					Collection<Parcelamento> colecaoParcelamento, int idFuncionalidadeIniciada)
					throws GeradorRelatorioParcelamentoException{

		List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados = null;
		try{
			Class clazz = Class.forName(nomeClasse);
			GeradorDadosRelatorioResolucaoDiretoria geradorDados = (GeradorDadosRelatorioResolucaoDiretoria) clazz.newInstance();
			dados = geradorDados.gerarDados(colecaoParcelamento, idFuncionalidadeIniciada);

			if(geradorDados.getParametroTarefa() != null && !geradorDados.getParametroTarefa().isEmpty()){
				adicionarParametrosTarefa(geradorDados.getParametroTarefa());
			}

		}catch(ClassNotFoundException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(SecurityException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(InstantiationException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}catch(IllegalAccessException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}

		return dados;
	}

	private byte[] gerarRelatorio(String arquivoLayout, Map<String, Object> parametros,
					Collection<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados)
					throws GeradorRelatorioParcelamentoException{

		byte[] bytes = null;

		try{

			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(this.getClass().getClassLoader()
							.getResourceAsStream(arquivoLayout));

			bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros, new JRBeanCollectionDataSource(dados));

		}catch(JRException e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}

		return bytes;
	}

	private byte[] concatenarArquivos(List<byte[]> pdfsGerados) throws GeradorRelatorioParcelamentoException{

		int pageOffset = 0;
		int f = 0;
		Document document = null;
		PdfCopy writer = null;
		
		byte[] pdfGerado = null;
		byte[] novoPDF = null;
		ByteArrayOutputStream baos = null;

		try{

			if(pdfsGerados != null && !pdfsGerados.isEmpty()){
				Iterator<byte[]> iterator = pdfsGerados.iterator();
				baos = new ByteArrayOutputStream();
				while(iterator.hasNext()){
					pdfGerado = (byte[]) iterator.next();
					PdfReader reader = new PdfReader(pdfGerado);
					int n = reader.getNumberOfPages();
					pageOffset += n;
					if(f == 0){
						document = new Document(reader.getPageSizeWithRotation(1));
						writer = new PdfCopy(document, baos);
						document.open();
					}
					PdfImportedPage page;
					for(int i = 0; i < n;){
						++i;
						page = writer.getImportedPage(reader, i);
						writer.addPage(page);
					}
					PRAcroForm form = reader.getAcroForm();
					if(form != null){
						writer.copyAcroForm(reader);
					}
					f++;
				}
				document.close();
			}

		}catch(Exception e){
			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);
		}

		novoPDF = baos.toByteArray();

		return novoPDF;
	}

	/**
	 * @author Cinthya
	 * @param mapa
	 * @param chave
	 * @param resolucaoDiretoria
	 */
	private void addValorMapLayoutOrdens(Map<Integer, Collection<ResolucaoDiretoria>> mapa, Integer chave,
					ResolucaoDiretoria resolucaoDiretoria){

		Collection<ResolucaoDiretoria> colecaoResolucaoDiretoria = null;

		if(mapa.get(chave) != null){
			// Existe
			colecaoResolucaoDiretoria = mapa.get(chave);
			colecaoResolucaoDiretoria.add(resolucaoDiretoria);
			mapa.remove(chave);
		}else{
			// Não existe
			colecaoResolucaoDiretoria = new ArrayList<ResolucaoDiretoria>();
			colecaoResolucaoDiretoria.add(resolucaoDiretoria);
		}

		mapa.put(chave, colecaoResolucaoDiretoria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int calcularTotalRegistrosRelatorio(){

		if (getParametro("colecaoIdsParcelamento") != null) {
			return ((Collection<Integer>) getParametro("colecaoIdsParcelamento")).size();
		} else {
			return 1;
		}
		
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("GeradorRelatorioResolucaoDiretoria", this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object executar() throws TarefaException{

		byte[] retorno = null;
		
		try{

			String stringTextoHtmlEditado = "";
			if(getParametro("stringTextoHtmlEditado") != null){
				stringTextoHtmlEditado = getParametro("stringTextoHtmlEditado").toString();
			}

			if(getParametro("listaParcelamento") != null){

				retorno = this.gerarRelatorioResolucaoDiretoria((List<Parcelamento>) getParametro("listaParcelamento"),
								(Collection<ContaValoresHelper>) getParametro("colecaoContaValores"), stringTextoHtmlEditado);
			}else if(getParametro("colecaoIdsParcelamento") != null){

				retorno = this.gerarRelatorioResolucaoDiretoria(((Collection<Integer>) getParametro("colecaoIdsParcelamento")),
								(Collection<ContaValoresHelper>) getParametro("colecaoContaValores"),
								(String) getParametro("indicadorParcelamentoCobrancaBancaria"),
								(ParcelamentoTermoTestemunhas) getParametro("parcelamentoTermoTestemunhas"),
								(ParcelamentoDadosTermo) getParametro("parcelamentoDadosTermos"), stringTextoHtmlEditado);
			}else if(getParametro("parcelamento") != null){

				retorno = this.gerarRelatorioResolucaoDiretoria(((Parcelamento) getParametro("parcelamento")),
								(Collection<ContaValoresHelper>) getParametro("colecaoContaValores"),
								(String) getParametro("indicadorParcelamentoCobrancaBancaria"),
								(ParcelamentoTermoTestemunhas) getParametro("parcelamentoTermoTestemunhas"),
								(ParcelamentoDadosTermo) getParametro("parcelamentoDadosTermos"),
								(Collection<GuiaPagamentoValoresHelper>) getParametro("colecaoGuiaPagamentoValores"),
								(Collection<DebitoACobrar>) getParametro("colecaoDebitoACobrar"),
								(Collection<CreditoARealizar>) getParametro("colecaoCreditoARealizar"),
								(String) getParametro("indicadorCobrancaBancaria"), stringTextoHtmlEditado,
								(Integer) getParametro("numeroDiasVencimentoEntrada"));
			}

			this.addParametro("relatorio", retorno);
			this.addParametro("stringTextoHtml", this.getTextoHtml());

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ORDEM_SERVICO_COBRANCA, getIdFuncionalidadeIniciada(), null);

		}catch(GeradorRelatorioParcelamentoException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(ControladorException e){
			e.printStackTrace();
		}

		return retorno;
	}

	public void adicionarParametrosTarefa(Set parametroTarefaRelatorio){

		if(parametroTarefaRelatorio != null && !parametroTarefaRelatorio.isEmpty()){

			Iterator it = parametroTarefaRelatorio.iterator();

			while(it.hasNext()){
				ParametroTarefa parametroTarefa = (ParametroTarefa) it.next();
				addParametro(parametroTarefa.getNome(), parametroTarefa.getValor());
			}
		}
	}

	private Map<String, Object> getParametros(){

		Iterator it = this.getParametroTarefa().iterator();

		Map<String, Object> parametros = null;

		while(it.hasNext()){
			ParametroTarefa parametroTarefa = (ParametroTarefa) it.next();

			if(parametros == null || parametros.isEmpty()){
				parametros = new HashMap<String, Object>();
			}

			parametros.put(parametroTarefa.getNome(), parametroTarefa.getValor());
		}

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		try{
			parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		try{
			parametros.put("P_INSC_EST",
							(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		return parametros;
	}

	private boolean existeCobrancaBancaria(Collection<ContaValoresHelper> colecaoContaValores){

		Collection<Conta> colecaoConta = new ArrayList<Conta>();
		boolean existeCobrancaBancaria = false;

		if(!Util.isVazioOrNulo(colecaoContaValores)){
			for(ContaValoresHelper contaValoresHelper : colecaoContaValores){
				colecaoConta.add(contaValoresHelper.getConta());
			}

			for(Conta conta : colecaoConta){
				if(conta.getContaMotivoRevisao() != null){
					if(isCobrancaBancaria(conta.getContaMotivoRevisao().getId().toString())){
						existeCobrancaBancaria = true;
						break;
					}
				}
			}
		}

		return existeCobrancaBancaria;
	}

	private boolean isCobrancaBancaria(String idMotivoRevisao){

		String[] idsMotivoRevisao = null;
		try{
			idsMotivoRevisao = ((String) ParametroCobranca.P_MOTIVO_REVISAO_COBRANCA_BANCARIA.executar(ExecutorParametrosCobranca
							.getInstancia())).split(",");
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(idsMotivoRevisao != null){

			if(idMotivoRevisao != null){
				for(int i = 0; i < idsMotivoRevisao.length; i++){
					if(idsMotivoRevisao[i].equals(idMotivoRevisao)){
						return true;
					}
				}
			}

		}
		return false;
	}

	private Boolean termoRdPrevalece(Integer idRd) throws GeradorRelatorioParcelamentoException{

		Boolean retorno = Boolean.FALSE;

		String[] idsRdPreferencial = null;

		try{

			idsRdPreferencial = ((String) ParametroCobranca.P_LISTA_RD_COM_TERMO_PREFERENCIAL_AO_TERMO_COBRANCABANCARIA
							.executar(ExecutorParametrosCobranca.getInstancia())).split(",");

		}catch(ControladorException e){

			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);

		}

		if(idsRdPreferencial != null){

			for(String idRdPreferencial : idsRdPreferencial){

				if(Integer.valueOf(idRdPreferencial).equals(idRd)){

					retorno = Boolean.TRUE;
					break;

				}

			}

		}

		return retorno;

	}

	private ResolucaoDiretoriaLayout obterResolucaoDiretoriaLayoutParcCobrancaBancaria() throws GeradorRelatorioParcelamentoException{

		String idResolucaoDiretoriaLayout = null;

		try{

			idResolucaoDiretoriaLayout = ((String) ParametroCobranca.P_LAYOUT_PARCELAMENTO_COBRANCA_BANCARIA
							.executar(ExecutorParametrosCobranca.getInstancia()));

		}catch(ControladorException e){

			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);

		}

		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();
		filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.ID,
						idResolucaoDiretoriaLayout));

		Collection<ResolucaoDiretoriaLayout> colecaoResolucaoDiretoriaLayout = Fachada.getInstancia().pesquisar(
						filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName());

		ResolucaoDiretoriaLayout resolucaoDiretoriaLayoutPadrao = (ResolucaoDiretoriaLayout) Util
						.retonarObjetoDeColecao(colecaoResolucaoDiretoriaLayout);

		return resolucaoDiretoriaLayoutPadrao;

	}

	private ResolucaoDiretoriaLayout obterResolucaoDiretoriaLayoutPadrao(){

		// Obtém o Layout Padrão
		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();
		filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.INDICADOR_PADRAO,
						ConstantesSistema.SIM));

		Collection<ResolucaoDiretoriaLayout> colecaoResolucaoDiretoriaLayout = Fachada.getInstancia().pesquisar(
						filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName());

		ResolucaoDiretoriaLayout resolucaoDiretoriaLayoutPadrao = (ResolucaoDiretoriaLayout) Util
						.retonarObjetoDeColecao(colecaoResolucaoDiretoriaLayout);

		return resolucaoDiretoriaLayoutPadrao;

	}

	private void adicionarArquivosGerados(Parcelamento parcelamento, ResolucaoDiretoriaLayout resolucaoDiretoriaLayout,
					List<byte[]> pdfsGerados, String stringTextoHtmlEditado)
					throws GeradorRelatorioParcelamentoException{

		String nomeClasse = resolucaoDiretoriaLayout.getNomeClasse();
		String arquivoLayout = resolucaoDiretoriaLayout.getNomeRelatorio();
		Integer qtdVias = resolucaoDiretoriaLayout.getQuantidadeVias();

		List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados = this.getGeradorDados(nomeClasse, arquivoLayout, parcelamento,
						stringTextoHtmlEditado);
		byte[] novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);

		for(int i = 0; i < qtdVias; i++){

			pdfsGerados.add(novoPdf);

		}

	}

	private void adicionarPreviewArquivosGerados(Parcelamento parcelamento, Collection<ContaValoresHelper> colecaoContaValores,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores, Collection<DebitoACobrar> colecaoDebitoACobrar,
					Collection<CreditoARealizar> colecaoCreditoARealizar, ResolucaoDiretoriaLayout resolucaoDiretoriaLayout,
					List<byte[]> pdfsGerados, String stringTextoHtmlEditado, Integer numeroDiasVencimentoEntrada)
					throws GeradorRelatorioParcelamentoException{

		String nomeClasse = resolucaoDiretoriaLayout.getNomeClasse();
		String arquivoLayout = resolucaoDiretoriaLayout.getNomeRelatorio();
		Integer qtdVias = resolucaoDiretoriaLayout.getQuantidadeVias();

		List<RelatorioParcelamentoResolucaoDiretoriaLayoutBean> dados = this.getGeradorPreviewDados(nomeClasse, arquivoLayout,
						parcelamento, colecaoContaValores, colecaoGuiaPagamentoValores, colecaoDebitoACobrar, colecaoCreditoARealizar,
						stringTextoHtmlEditado, numeroDiasVencimentoEntrada);
		
		byte[] novoPdf = this.gerarRelatorio(arquivoLayout, this.getParametros(), dados);

		for(int i = 0; i < qtdVias; i++){

			pdfsGerados.add(novoPdf);

		}

	}

}
