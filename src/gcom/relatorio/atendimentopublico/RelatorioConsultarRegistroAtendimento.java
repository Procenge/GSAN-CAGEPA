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

import gcom.atendimentopublico.registroatendimento.*;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.FiltroClienteFone;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
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
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;

import java.util.*;

import br.com.procenge.comum.exception.NegocioException;

/**
 * classe responsável por criar o relatório de regitro atendimento manter de
 * água
 * 
 * @author Rafael Corrêa
 * @created 11 de Julho de 2005
 */
public class RelatorioConsultarRegistroAtendimento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioConsultarRegistroAtendimento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO);
	}

	@Deprecated
	public RelatorioConsultarRegistroAtendimento() {

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

		Integer idRegistroAtendimento = (Integer) getParametro("idRegistroAtendimento");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioConsultarRegistroAtendimentoBean relatorioBean = null;

		ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper = fachada.obterDadosRegistroAtendimento(idRegistroAtendimento);

		// Seta um valor para um indicador que será comparado no relatório para
		// imprimir o título associado do número e da situação da RA
		String indicadorAssociado = "";

		if(registroAtendimentoHelper.getTituloNumeroRAAssociado() != null
						&& registroAtendimentoHelper.getTituloNumeroRAAssociado().equalsIgnoreCase("Número do RA de Referência:")){
			indicadorAssociado = "1";
		}else if(registroAtendimentoHelper.getTituloNumeroRAAssociado() != null
						&& registroAtendimentoHelper.getTituloNumeroRAAssociado().equalsIgnoreCase("Número do RA Atual:")){
			indicadorAssociado = "2";
		}else if(registroAtendimentoHelper.getTituloNumeroRAAssociado() != null
						&& registroAtendimentoHelper.getTituloNumeroRAAssociado().equalsIgnoreCase("Número do RA Anterior:")){
			indicadorAssociado = "3";
		}

		String unidadeEncerramento = "";
		String usuarioEncerramento = "";

		UnidadeOrganizacional unidadeEncerramentoRA = fachada.obterUnidadeEncerramentoRA(registroAtendimentoHelper.getRegistroAtendimento()
						.getId());

		if(unidadeEncerramentoRA != null){

			unidadeEncerramento = unidadeEncerramentoRA.getId() + " - " + unidadeEncerramentoRA.getDescricao();

			RegistroAtendimentoUnidade registroAtendimentoUnidade = this.consultarRegistroAtendimentoUnidade(registroAtendimentoHelper
							.getRegistroAtendimento().getId(), unidadeEncerramentoRA.getId());

			Usuario usuario = registroAtendimentoUnidade.getUsuario();
			if(usuario != null){
				usuarioEncerramento = usuario.getId() + " - " + usuario.getNomeUsuario();
			}
		}

		RegistroAtendimento registroAtendimento = registroAtendimentoHelper.getRegistroAtendimento();
		Imovel imovel = registroAtendimento.getImovel();

		// Cliente Proprietário
		String clienteProprietarioComIdNome = "";

		if(imovel != null){
			Integer idImovel = imovel.getId();
			Cliente clienteProprietario = fachada.retornarDadosClienteProprietario(idImovel);

			if(clienteProprietario != null){
				Integer idClienteProprietario = clienteProprietario.getId();
				String nomeClienteProprietario = clienteProprietario.getNome();

				clienteProprietarioComIdNome = idClienteProprietario + " - " + nomeClienteProprietario;
			}

		}

		// Informações do Cliente Solicitante
		String tipoPessoa = "";
		String numeroCpfCnpj = "";
		String numeroRG = "";
		String orgaoExpedidorRG = "";
		String unidadeFederacaoRG = "";
		String dddTelefoneSolicitante = "";
		String ramalTelefoneSolicitante = "";

		RegistroAtendimentoSolicitante solicitante = registroAtendimentoHelper.getSolicitante();

		if(solicitante != null){
			Short clienteTipo = solicitante.getClienteTipo();

			if(clienteTipo != null){
				if(clienteTipo == ConstantesSistema.INDICADOR_PESSOA_JURIDICA.shortValue()){
					tipoPessoa = "Pessoa Jurídica";

					// CNPJ
					numeroCpfCnpj = solicitante.getNumeroCnpj();
				}else{
					tipoPessoa = "Pessoa Física";

					// CPF
					numeroCpfCnpj = solicitante.getNumeroCpf();

					// RG
					numeroRG = solicitante.getNumeroRG();

					OrgaoExpedidorRg orgaoExpedidor = solicitante.getOrgaoExpedidorRg();

					if(orgaoExpedidor != null){
						orgaoExpedidorRG = orgaoExpedidor.getDescricaoAbreviada();
					}

					UnidadeFederacao unidadeFederacao = solicitante.getUnidadeFederacaoRG();

					if(unidadeFederacao != null){
						unidadeFederacaoRG = unidadeFederacao.getSigla();
					}
				}
			}
			
			// Caso o solicitante principal seja um cliente, obter fone do Cliente da Tabela
			// CLIENTE_FONE
			Collection<RegistroAtendimentoSolicitante> colecaoSolicitantes = null;

			FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();

			filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(
							FiltroRegistroAtendimentoSolicitante.REGISTRO_ATENDIMENTO_ID,
							registroAtendimentoHelper.getRegistroAtendimento().getId()));

			colecaoSolicitantes = fachada.pesquisar(filtroRegistroAtendimentoSolicitante, RegistroAtendimentoSolicitante.class.getName());

			RegistroAtendimentoSolicitante registroAtendimentoSolicitante = null;

			// Recupera o Indicador de Solicitante Principal
			if(!Util.isVazioOrNulo(colecaoSolicitantes)){
				registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) Util.retonarObjetoDeColecao(colecaoSolicitantes);
			}

			// Recupera o cliente solicitante
			Cliente cliente = solicitante.getCliente();

			if(!Util.isVazioOuBranco(cliente) && !Util.isVazioOuBranco(registroAtendimentoSolicitante)
							&& registroAtendimentoSolicitante.getIndicadorSolicitantePrincipal() == ConstantesSistema.SIM.shortValue()){

				FiltroClienteFone filtroClienteFone = new FiltroClienteFone(FiltroClienteFone.INDICADOR_TELEFONE_PADRAO);
				filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, cliente.getId()));

				Collection<ClienteFone> colecaoClienteFone = Fachada.getInstancia().pesquisar(filtroClienteFone,
								ClienteFone.class.getName());

				if(!Util.isVazioOrNulo(colecaoClienteFone)){
					ClienteFone clienteFone = (ClienteFone) Util.retonarObjetoDeColecao(colecaoClienteFone);

					dddTelefoneSolicitante = clienteFone.getDddTelefone();
					ramalTelefoneSolicitante = clienteFone.getRamal();

				}

			}else{
				// Caso Contrário, obter da Tabela SOLICITANTE_FONE
				FiltroSolicitanteFone filtroSolicitanteFone = new FiltroSolicitanteFone(FiltroSolicitanteFone.INDICADOR_TELEFONE_PADRAO);

				filtroSolicitanteFone.adicionarParametro(new ParametroSimples(FiltroSolicitanteFone.REGISTRO_ATENDIMENTO_SOLICITANTE_ID,
								solicitante.getID()));

				Collection<SolicitanteFone> colecaoSolicitanteFone = null;

				colecaoSolicitanteFone = fachada.pesquisar(filtroSolicitanteFone, SolicitanteFone.class.getName());

				if(colecaoSolicitanteFone != null && !colecaoSolicitanteFone.isEmpty()){
					SolicitanteFone solicitanteFone = (SolicitanteFone) Util.retonarObjetoDeColecao(colecaoSolicitanteFone);

					dddTelefoneSolicitante = solicitanteFone.getDddTelefone();
					ramalTelefoneSolicitante = solicitanteFone.getRamal();

				}
			}

		}

		relatorioBean = new RelatorioConsultarRegistroAtendimentoBean(

		// Dados Gerais

						// Número RA
						registroAtendimentoHelper.getRegistroAtendimento().getId().toString(),

						// Situação RA
						registroAtendimentoHelper.getDescricaoSituacaoRA(),

						// Indicador RA Associado
						indicadorAssociado,

						// Número RA Associado
						registroAtendimentoHelper.getRAAssociado() == null ? "" : registroAtendimentoHelper.getRAAssociado().getId()
										.toString(),

						// Situação RA Associado
						registroAtendimentoHelper.getDescricaoSituacaoRAAssociado(),

						// Tipo Solicitação
						registroAtendimentoHelper.getRegistroAtendimento().getSolicitacaoTipoEspecificacao() == null ? ""
										: registroAtendimentoHelper.getRegistroAtendimento().getSolicitacaoTipoEspecificacao()
														.getSolicitacaoTipo().getDescricao(),

						// Especificação
						registroAtendimentoHelper.getRegistroAtendimento().getSolicitacaoTipoEspecificacao() == null ? ""
										: registroAtendimentoHelper.getRegistroAtendimento().getSolicitacaoTipoEspecificacao()
														.getDescricao(),

						// Data Atendimento
						Util.formatarDataComHora(registroAtendimentoHelper.getRegistroAtendimento().getRegistroAtendimento()),

						// Data Prevista
						Util.formatarData(registroAtendimentoHelper.getRegistroAtendimento().getDataPrevistaAtual()),

						// Meio Solicitação
						registroAtendimentoHelper.getRegistroAtendimento().getMeioSolicitacao() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getMeioSolicitacao().getDescricao(),

						// Unidade Atendimento
						registroAtendimentoHelper.getUnidadeAtendimento() == null ? "" : registroAtendimentoHelper.getUnidadeAtendimento()
										.getDescricao(),

						// Unidade Atual
						registroAtendimentoHelper.getUnidadeAtual() == null ? "" : registroAtendimentoHelper.getUnidadeAtual()
										.getDescricao(),

						// Observação
						registroAtendimentoHelper.getRegistroAtendimento().getObservacao(),

						// Dados do Local da Ocorrência

						// Matrícula do Imóvel
						registroAtendimentoHelper.getRegistroAtendimento().getImovel() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getImovel().getId().toString(),

						// Inscrição do Imóvel
						registroAtendimentoHelper.getRegistroAtendimento().getImovel() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getImovel().getInscricaoFormatada(),

						// Endereço da Ocorrência
						registroAtendimentoHelper.getEnderecoOcorrencia(),

						// Ponto de Referência
						registroAtendimentoHelper.getRegistroAtendimento().getPontoReferencia(),

						// Município
						registroAtendimentoHelper.getRegistroAtendimento().getBairroArea() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getBairroArea().getBairro().getMunicipio().getNome(),

						// Bairro
						registroAtendimentoHelper.getRegistroAtendimento().getBairroArea() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getBairroArea().getBairro().getNome(),

						// Área do Bairro
						registroAtendimentoHelper.getRegistroAtendimento().getBairroArea() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getBairroArea().getNome(),

						// Localidade/Setor/Quadra
						(registroAtendimentoHelper.getRegistroAtendimento().getLocalidade() == null ? "---" : Util
										.adicionarZerosEsquedaNumero(3, registroAtendimentoHelper.getRegistroAtendimento().getLocalidade()
														.getId().toString()))
										+ "/"
										+ (registroAtendimentoHelper.getRegistroAtendimento().getSetorComercial() == null ? "---" : Util
														.adicionarZerosEsquedaNumero(3, ""
																		+ registroAtendimentoHelper.getRegistroAtendimento()
																						.getSetorComercial().getCodigo()))
										+ "/"
										+ (registroAtendimentoHelper.getRegistroAtendimento().getQuadra() == null ? "---" : Util
														.adicionarZerosEsquedaNumero(4, ""
																		+ registroAtendimentoHelper.getRegistroAtendimento().getQuadra()
																						.getNumeroQuadra())),

						// Divisão Esgoto
						registroAtendimentoHelper.getRegistroAtendimento().getDivisaoEsgoto() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getDivisaoEsgoto().getDescricao(),

						// Local da Ocorrência
						registroAtendimentoHelper.getRegistroAtendimento().getLocalOcorrencia() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getLocalOcorrencia().getDescricao(),

						// Pavimento Rua
						registroAtendimentoHelper.getRegistroAtendimento().getPavimentoRua() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getPavimentoRua().getDescricao(),

						// Pavimento Calçada
						registroAtendimentoHelper.getRegistroAtendimento().getPavimentoCalcada() == null ? "" : registroAtendimentoHelper
										.getRegistroAtendimento().getPavimentoCalcada().getDescricao(),

						// Descrição do Local da Ocorrência
						registroAtendimentoHelper.getRegistroAtendimento().getDescricaoLocalOcorrencia(),

						// Dados do Solicitante

						// Código do Cliente
						registroAtendimentoHelper.getSolicitante() == null ? ""
										: registroAtendimentoHelper.getSolicitante().getCliente() == null ? "" : registroAtendimentoHelper
														.getSolicitante().getCliente().getId().toString(),

						// Nome do Cliente
						registroAtendimentoHelper.getSolicitante() == null ? ""
										: registroAtendimentoHelper.getSolicitante().getCliente() == null ? "" : registroAtendimentoHelper
														.getSolicitante().getCliente().getNome(),

						// Unidade Solicitante
						registroAtendimentoHelper.getSolicitante() == null ? "" : registroAtendimentoHelper.getSolicitante()
										.getUnidadeOrganizacional() == null ? "" : registroAtendimentoHelper.getSolicitante()
										.getUnidadeOrganizacional().getDescricao(),

						// Código do Funcionário Responsável
						registroAtendimentoHelper.getSolicitante() == null ? "" : registroAtendimentoHelper.getSolicitante()
										.getFuncionario() == null ? "" : registroAtendimentoHelper.getSolicitante().getFuncionario()
										.getId().toString(),

						// Nome do Funcionário Responsável
						registroAtendimentoHelper.getSolicitante() == null ? "" : registroAtendimentoHelper.getSolicitante()
										.getFuncionario() == null ? "" : registroAtendimentoHelper.getSolicitante().getFuncionario()
										.getNome(),

						// Nome do Solicitante
						registroAtendimentoHelper.getSolicitante() == null ? "" : registroAtendimentoHelper.getSolicitante()
										.getSolicitante(),

						// Dados da Reiteração

						// Quantidade
						registroAtendimentoHelper.getRegistroAtendimento().getQuantidadeReiteracao() == null ? ""
										: registroAtendimentoHelper.getRegistroAtendimento().getQuantidadeReiteracao().toString(),

						// Data Reiteração
						registroAtendimentoHelper.getRegistroAtendimento().getUltimaReiteracao() == null ? "" : Util
										.formatarData(registroAtendimentoHelper.getRegistroAtendimento().getUltimaReiteracao()),

						// Dados do Encerramento

						// Motivo do Encerramento
						registroAtendimentoHelper.getRegistroAtendimento().getAtendimentoMotivoEncerramento() == null ? ""
										: registroAtendimentoHelper.getRegistroAtendimento().getAtendimentoMotivoEncerramento()
														.getDescricao(),

						// Número RA Referência
						registroAtendimentoHelper.getRAAssociado() == null ? "" : registroAtendimentoHelper.getRAAssociado().getId()
										.toString(),

						// Situação RA Referência
						registroAtendimentoHelper.getDescricaoSituacaoRAAssociado() == null ? "" : registroAtendimentoHelper
										.getDescricaoSituacaoRAAssociado(),

						// Data Encerramento
						registroAtendimentoHelper.getRegistroAtendimento().getDataEncerramento() == null ? "" : Util
										.formatarDataComHora(registroAtendimentoHelper.getRegistroAtendimento().getDataEncerramento()),

						// Unidade Encerramento
						unidadeEncerramento,

						// Usuário Encerramento
						usuarioEncerramento,

						// Parecer Encerramento
						registroAtendimentoHelper.getRegistroAtendimento().getParecerEncerramento() == null ? ""
										: registroAtendimentoHelper.getRegistroAtendimento().getParecerEncerramento(),

						// Rota
						registroAtendimentoHelper.getCodigoRota() == null ? "" : registroAtendimentoHelper.getCodigoRota().toString(),

						// Sequencial Rota
						registroAtendimentoHelper.getSequencialRota() == null ? "" : registroAtendimentoHelper.getSequencialRota()
										.toString(),

						// Cliente Proprietário
						clienteProprietarioComIdNome,

						// Tipo de Pessoa
						tipoPessoa,

						// CPF / CNPJ
						numeroCpfCnpj,

						// RG
						numeroRG,

						// Orgão Expedidor do RG
						orgaoExpedidorRG,

						// Estado do RG
						unidadeFederacaoRG,
						
						// (DDD) Telefone
						dddTelefoneSolicitante,

						// Ramal
						ramalTelefoneSolicitante);

		// adiciona o bean a coleção
		relatorioBeans.add(relatorioBean);

		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
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

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.CONSULTAR_REGISTRO_ATENDIMENTO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	/**
	 * Consulta o Registro Atendimento Unidade pelo id da RA
	 * 
	 * @author Rafael Pinto
	 * @created 09/08/2006
	 */
	private RegistroAtendimentoUnidade consultarRegistroAtendimentoUnidade(Integer idRA, Integer idUnidade){

		RegistroAtendimentoUnidade retorno = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoRegistroAtendimentoUnidade = null;

		FiltroRegistroAtendimentoUnidade filtroRegistroAtendimentoUnidade = new FiltroRegistroAtendimentoUnidade();

		filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimentoUnidade.REGISTRO_ATENDIMENTO_ID,
						idRA));

		filtroRegistroAtendimentoUnidade.adicionarParametro(new ParametroSimples(
						FiltroRegistroAtendimentoUnidade.UNIDADE_ORGANIZACIONAL_ID, idUnidade));

		filtroRegistroAtendimentoUnidade.adicionarCaminhoParaCarregamentoEntidade("usuario");

		colecaoRegistroAtendimentoUnidade = fachada.pesquisar(filtroRegistroAtendimentoUnidade, RegistroAtendimentoUnidade.class.getName());

		if(colecaoRegistroAtendimentoUnidade != null && !colecaoRegistroAtendimentoUnidade.isEmpty()){
			retorno = (RegistroAtendimentoUnidade) Util.retonarObjetoDeColecao(colecaoRegistroAtendimentoUnidade);

		}

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioConsultarRegistroAtendimento", this);
	}
}