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

package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.bean.ContratoPrestacaoServicoHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.text.DateFormat;
import java.util.*;

/**
 * classe responsável por criar o relatório de bairro manter de água
 * 
 * @author Sávio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioContratoPrestacaoServico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogado;

	public RelatorioContratoPrestacaoServico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTRATO_PRESTACAO_SERVICO);
		this.usuarioLogado = usuario;
	}

	@Deprecated
	public RelatorioContratoPrestacaoServico() {

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

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer idImovel = (Integer) getParametro("idImovel");

		String parametroContratoRa = null;
		try{
			parametroContratoRa = ParametroAtendimentoPublico.P_LAYOUT_CONTRATO_PESTACAO_SERVICO.executar();
		}catch(ControladorException e){
			e.printStackTrace();
		}

		// valor de retorno
		byte[] retorno = null;

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioContratoPrestacaoServicoBean relatorioBean = null;

		Collection colecaoContratoPrestacaoServicoHelper = null;

		colecaoContratoPrestacaoServicoHelper = fachada.obterDadosContratoPrestacaoServico(idImovel);

		// se a coleção de parâmetros da analise não for vazia
		if(colecaoContratoPrestacaoServicoHelper != null && !colecaoContratoPrestacaoServicoHelper.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoContratoPrestacaoServicoHelperIterator = colecaoContratoPrestacaoServicoHelper.iterator();

			Date dataCorrente = new Date();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoContratoPrestacaoServicoHelperIterator.hasNext()){

				ContratoPrestacaoServicoHelper contratoPrestacaoServicoHelper = (ContratoPrestacaoServicoHelper) colecaoContratoPrestacaoServicoHelperIterator
								.next();

				// Faz as validações dos campos necessários e formata a String
				// para a forma como irá aparecer no relatório

				Imovel imovel = null;
				if(contratoPrestacaoServicoHelper.getImovel() != null){
					imovel = contratoPrestacaoServicoHelper.getImovel();
				}
				Cliente cliente = null;
				if(contratoPrestacaoServicoHelper.getCliente() != null){
					cliente = contratoPrestacaoServicoHelper.getCliente();
				}
				Cliente clienteResponsavel = null;
				if(contratoPrestacaoServicoHelper.getClienteResponsavel() != null){
					clienteResponsavel = contratoPrestacaoServicoHelper.getClienteResponsavel();
				}
				Cliente clienteProprietario = null;
				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RG_ORGAO_EXPEDIDOR);
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_UNIDADE_FEDERACAO);
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID, ClienteRelacaoTipo.PROPRIETARIO));

				Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				ClienteImovel clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

				if(clienteImovel != null && clienteImovel.getCliente() != null){
					clienteProprietario = clienteImovel.getCliente();
				}

				// orgaoEmissorRgClienteProprietario
				String orgaoEmissorRgClienteProprietario = "";
				if(clienteProprietario != null && clienteProprietario.getOrgaoExpedidorRg() != null){
					orgaoEmissorRgClienteProprietario = clienteProprietario.getOrgaoExpedidorRg().getDescricaoAbreviada();

				}

				// ufRgClienteProprietario
				String ufRgClienteProprietario = "";
				if(clienteProprietario != null && clienteProprietario.getUnidadeFederacao() != null){
					ufRgClienteProprietario = clienteProprietario.getUnidadeFederacao().getSigla();

				}

				// Pega a Data Atual formatada da seguinte forma: dd de mês(por
				// extenso) de aaaa
				// Ex: 23 de maio de 1985
				DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
				String dataCorrenteFormatada = df.format(new Date());
				
				String anoCorrente = "" + Util.getAno(dataCorrente);

				// indicadorPessoaFisica
				String indicadorPessoaFisica = "";
				FiltroCliente filtroCliente = new FiltroCliente();
				filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId()));
				Cliente clienteAux = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCliente, Cliente.class.getName()));
				if(clienteAux.getClienteTipo().getIndicadorPessoaFisicaJuridica() == 1){
					indicadorPessoaFisica = "1";
				}else{
					indicadorPessoaFisica = "2";
				}

				// numero Termo Contrato
				String numeroContrato = "";
				if(parametroContratoRa.equals(ConstantesSistema.SIM.toString())){
					Integer numeroEmissao = imovel.getNumeroContratoEmissao();

					if(numeroEmissao == null){
						numeroEmissao = 0;
					}

					numeroEmissao = numeroEmissao + 1;

					fachada.atualizarNumeroEmissaoContrato(idImovel);

					Integer qtdAlteracoesImovel = fachada.obterQuantidadeAlteracoesImovel(idImovel);

					numeroContrato = idImovel.toString() + "/" + qtdAlteracoesImovel.toString();
				}else{
					numeroContrato = idImovel.toString() + anoCorrente;
				}


				// nomeCliente
				String nomeCliente = "";
				if(cliente != null && cliente.getNome() != null){
					nomeCliente = cliente.getNome();
				}

				// cpfCliente
				String cpfCliente = "";
				if(cliente != null && cliente.getCpfFormatado() != null){
					cpfCliente = cliente.getCpfFormatado();
				}else if(cliente != null && cliente.getCnpjFormatado() != null){
					cpfCliente = cliente.getCnpjFormatado();
				}

				// rgCliente
				String rgCliente = "";
				if(cliente != null && cliente.getRg() != null){
					rgCliente = cliente.getRg();
				}

				// enderecoImovel
				String enderecoImovel = "";
				if(imovel != null){
					enderecoImovel = fachada.pesquisarEnderecoComDetalhamento(idImovel);
				}

				// enderecoCliente
				String enderecoCliente = "";
				if(cliente != null){
					enderecoCliente = fachada.pesquisarEnderecoClienteAbreviadoComDetalhemento(cliente.getId());
				}

				// municipio
				String municipio = "";
				if(contratoPrestacaoServicoHelper.getNomeMunicipio() != null){
					municipio = contratoPrestacaoServicoHelper.getNomeMunicipio();
				}else{
					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
					filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.MUNICIPIO);
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, imovel.getLocalidade().getId()));
					Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLocalidade,
									Localidade.class.getName()));
					if(localidade != null){
						municipio = localidade.getMunicipio().getNome();
					}

				}

				// nomeUnidadeNegocio
				String nomeUnidadeNegocio = "";
				if(contratoPrestacaoServicoHelper.getNomeUnidadeNegocio() != null){
					nomeUnidadeNegocio = contratoPrestacaoServicoHelper.getNomeUnidadeNegocio();
				}

				// nomeResponsavel
				String nomeResponsavel = "";
				if(clienteResponsavel != null && clienteResponsavel.getNome() != null){
					nomeResponsavel = clienteResponsavel.getNome();
				}

				// cpfResponsavel
				String cpfResponsavel = "";
				if(clienteResponsavel != null && clienteResponsavel.getCpfFormatado() != null){
					cpfResponsavel = clienteResponsavel.getCpfFormatado();
				}

				// rgResponsavel
				String rgResponsavel = "";
				if(clienteResponsavel != null && clienteResponsavel.getRg() != null){
					rgResponsavel = clienteResponsavel.getRg();
				}

				// categoria
				String categoria = "";
				Categoria categoriaImovel = fachada.obterPrincipalCategoriaImovel(idImovel);
				if(categoriaImovel != null){
					categoria = categoriaImovel.getDescricao();
				}

				// consumoMinimo
				String consumoMinimo = "";
				if(cliente != null && cliente.getNome() != null){
					consumoMinimo = contratoPrestacaoServicoHelper.getConsumoMinimo().toString();
				}

				// profissaoCliente
				String profissaoCliente = "";
				if(cliente != null && cliente.getProfissao() != null){
					profissaoCliente = cliente.getProfissao().getDescricao();

					if(cliente.getProfissao().getCodigo().equals(0)){
						profissaoCliente = "Profissão não Informada";
					}
				}

				// nacionalidadeCliente
				String nacionalidadeCliente = " ";
				if(cliente != null && cliente.getNacionalidade() != null){
					nacionalidadeCliente = cliente.getNacionalidade().getDescricao();
				}

				// estadoCivilCliente
				String estadoCivilCliente = " ";
				if(cliente != null && cliente.getEstadoCivil() != null){
					estadoCivilCliente = cliente.getEstadoCivil().getDescricao();
				}

				// orgaoEmissorRgCliente
				String orgaoEmissorRgCliente = "";
				if(cliente != null && cliente.getOrgaoExpedidorRg() != null){
					orgaoEmissorRgCliente = cliente.getOrgaoExpedidorRg().getDescricaoAbreviada();
				}

				// ufRgCliente
				String ufRgCliente = "";
				if(cliente != null && cliente.getUnidadeFederacao() != null){
					ufRgCliente = cliente.getUnidadeFederacao().getSigla();
				}

				// inscricaoImovel
				String inscricaoImovel = "";
				if(imovel != null && imovel.getInscricaoFormatada() != null){
					inscricaoImovel = imovel.getInscricaoFormatada();
				}

				// Nome Atendente
				String nomeAtendente = "";
				// Matricula Atendente
				String matriculaAtendente = "";
				if(usuarioLogado != null){
					nomeAtendente = usuarioLogado.getNomeUsuario();
					matriculaAtendente = usuarioLogado.getId().toString();
				}

				// nomeClienteProprietario
				String nomeClienteProprietario = "";
				if(clienteProprietario != null && clienteProprietario.getNome() != null){
					nomeClienteProprietario = clienteImovel.getCliente().getNome();
				}

				// cpfClienteProprietario
				String cpfClienteProprietario = "";
				if(clienteProprietario != null && clienteProprietario.getCpfFormatado() != null){
					cpfClienteProprietario = clienteImovel.getCliente().getCpfFormatado();
				}else if(clienteProprietario != null && clienteProprietario.getCnpjFormatado() != null){
					cpfClienteProprietario = clienteProprietario.getCnpjFormatado();
				}

				// rgClienteProprietario
				String rgClienteProprietario = "";
				if(clienteProprietario != null && clienteProprietario.getRg() != null){
					rgClienteProprietario = clienteImovel.getCliente().getRg();
				}


				// Dados da 1ª página
				relatorioBean = new RelatorioContratoPrestacaoServicoBean(
				// indicadorPessoaFisica
								indicadorPessoaFisica,

								// numeroPagina
								"1",

								// numeroContrato
								numeroContrato,

								// nomeCliente
								nomeCliente,

								// cpfCliente
								cpfCliente,

								// rgCliente
								rgCliente,

								// idImovel
								idImovel.toString(),

								// enderecoImovel
								enderecoImovel,

								// enderecoCliente
								enderecoCliente,

								// municipio
								municipio,

								// nomeUnidadeNegocio
								nomeUnidadeNegocio,

								// nomeResponsavel
								nomeResponsavel,

								// cpfResponsavel
								cpfResponsavel,

								// rgResponsavel
								rgResponsavel,

								// categoria
								categoria,

								// consumoMinimo
								consumoMinimo.toString(),

								// data
								dataCorrenteFormatada,

								// profissaoCliente
								profissaoCliente,

								// nacionalidadeCliente
								nacionalidadeCliente,

								// estadoCivilCliente
								estadoCivilCliente,

								// orgaoEmissorRgCliente
								orgaoEmissorRgCliente,

								// ufRgCliente
								ufRgCliente,

								// inscricaoImovel
								inscricaoImovel,

								// nomeAtendente
								nomeAtendente,

								// matriculaAtendente
								matriculaAtendente,

								// nomeClienteProprietario
								nomeClienteProprietario,

								// cpfClienteProprietario
								cpfClienteProprietario,

								// rgClienteProprietario
								rgClienteProprietario,

								// orgaoEmissorRgClienteProprietario
								orgaoEmissorRgClienteProprietario,

								// ufRgClienteProprietario
								ufRgClienteProprietario);

				relatorioBeans.add(relatorioBean);

				// Dados da 2ª página
				relatorioBean = new RelatorioContratoPrestacaoServicoBean(
				// indicadorPessoaFisica
								indicadorPessoaFisica,

								// numeroPagina
								"2",

								// numeroContrato
								numeroContrato,

								// nomeCliente
								nomeCliente,

								// cpfCliente
								cpfCliente,

								// rgCliente
								rgCliente,

								// idImovel
								idImovel.toString(),

								// enderecoImovel
								enderecoImovel,

								// enderecoCliente
								enderecoCliente,

								// municipio
								municipio,

								// nomeUnidadeNegocio
								nomeUnidadeNegocio,

								// nomeResponsavel
								nomeResponsavel,

								// cpfResponsavel
								cpfResponsavel,

								// rgResponsavel
								rgResponsavel,

								// categoria
								categoria,

								// consumoMinimo
								consumoMinimo.toString(),

								// data
								dataCorrenteFormatada,

								// profissaoCliente
								profissaoCliente,

								// nacionalidadeCliente
								nacionalidadeCliente,

								// estadoCivilCliente
								estadoCivilCliente,

								// orgaoEmissorRgCliente
								orgaoEmissorRgCliente,

								// ufRgCliente
								ufRgCliente,

								// inscricaoImovel
								inscricaoImovel,

								// nomeAtendente
								nomeAtendente,

								// matriculaAtendente
								matriculaAtendente,

								// nomeClienteProprietario
								nomeClienteProprietario,

								// cpfClienteProprietario
								cpfClienteProprietario,

								// rgClienteProprietario
								rgClienteProprietario,

								// orgaoEmissorRgClienteProprietario
								orgaoEmissorRgClienteProprietario,

								// ufRgClienteProprietario
								ufRgClienteProprietario);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);

				// Dados da 3ª página
				// relatorioBean = new RelatorioContratoPrestacaoServicoBean(
				// // indicadorPessoaFisica
				// indicadorPessoaFisica,
				//
				// // numeroPagina
				// "2",
				//
				// // numeroContrato
				// numeroContrato,
				//
				// // nomeCliente
				// nomeCliente,
				//
				// // cpfCliente
				// cpfCliente,
				//
				// // rgCliente
				// rgCliente,
				//
				// // idImovel
				// idImovel.toString(),
				//
				// // enderecoImovel
				// enderecoImovel,
				//
				// // enderecoCliente
				// enderecoCliente,
				//
				// // municipio
				// municipio,
				//
				// // nomeUnidadeNegocio
				// nomeUnidadeNegocio,
				//
				// // nomeResponsavel
				// nomeResponsavel,
				//
				// // cpfResponsavel
				// cpfResponsavel,
				//
				// // rgResponsavel
				// rgResponsavel,
				//
				// // categoria
				// categoria,
				//
				// // consumoMinimo
				// consumoMinimo.toString(),
				//
				// // data
				// dataCorrenteFormatada,
				//
				// // profissaoCliente
				// profissaoCliente,
				//
				// // nacionalidadeCliente
				// nacionalidadeCliente,
				//
				// // estadoCivilCliente
				// estadoCivilCliente,
				//
				// // orgaoEmissorRgCliente
				// orgaoEmissorRgCliente,
				//
				// // ufRgCliente
				// ufRgCliente,
				//
				// // inscricaoImovel
				// inscricaoImovel,
				//
				// // nomeAtendente
				// nomeAtendente,
				//
				// // matriculaAtendente
				// matriculaAtendente,
				//
				// // nomeClienteProprietario
				// nomeClienteProprietario,
				//
				// // cpfClienteProprietario
				// cpfClienteProprietario,
				//
				// // rgClienteProprietario
				// rgClienteProprietario,
				//
				// // orgaoEmissorRgClienteProprietario
				// orgaoEmissorRgClienteProprietario,
				//
				// // ufRgClienteProprietario
				// ufRgClienteProprietario);

				// adiciona o bean a coleção
				// relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("nomeEmpresaSigla", sistemaParametro.getNomeAbreviadoEmpresa());
		parametros.put("nomeEmpresaCompleto", sistemaParametro.getNomeEmpresa());
		parametros.put("cepEmpresa", sistemaParametro.getCep().getCepFormatado());

		// filtro logradouro
		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
		filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouro.MUNICIPIO);
		filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouro.LOGRADOUROTIPO);
		filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouro.LOGRADOUROTITULO);
		filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouro.MUNICIPIO_UNIDADE_FEDERACAO);
		filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, sistemaParametro.getLogradouro().getId()));
		Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLogradouro, Logradouro.class.getName()));

		parametros.put("enderecoEmpresa", logradouro.getDescricaoFormatada() + ", n° " + sistemaParametro.getNumeroImovel() + ", "
						+ sistemaParametro.getBairro().getNome() + ", CEP: " + sistemaParametro.getCep().getCepFormatado());
		parametros.put("municipioEmpresa", logradouro.getMunicipio().getNome());
		parametros.put("estadoEmpresa", sistemaParametro.getNomeEstado());
		parametros.put("estadoEmpresaSigla", logradouro.getMunicipio().getUnidadeFederacao().getSigla());
		parametros.put("cnpjEmpresa", Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));

		String inscricaoEstadualEmpresa = "";
		try{
			inscricaoEstadualEmpresa = ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.executar();
		}catch(ControladorException e1){
			e1.printStackTrace();
		}

		Date atual = new Date();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(atual);
		int dia = calendario.get(Calendar.DATE);
		int ano = calendario.get(Calendar.YEAR);

		parametros.put("inscricaoEstadualEmpresa", Util.formatarInscricaoEstadual(inscricaoEstadualEmpresa));
		parametros.put("diaAtual", String.valueOf(dia));
		parametros.put("mesAtualPortugues", Util.retornaDescricaoMes(new Date().getMonth() + 1));
		parametros.put("anoAtual", String.valueOf(ano));

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		if(parametroContratoRa.equals(ConstantesSistema.SIM.toString())){
			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTRATO_PRESTACAO_SERVICO_CAERD, parametros, ds, tipoFormatoRelatorio);
		}else{
			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTRATO_PRESTACAO_SERVICO, parametros, ds, tipoFormatoRelatorio);
		}

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_RESOLUCAO_DIRETORIA, idFuncionalidadeIniciada, null);
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

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterResolucaoDiretoria", this);
	}
}