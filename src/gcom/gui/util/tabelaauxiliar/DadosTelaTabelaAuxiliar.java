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

package gcom.gui.util.tabelaauxiliar;

import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.EmissaoOrdemCobrancaTipo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoDiametro;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoMaterial;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.FoneTipo;
import gcom.cadastro.cliente.PessoaSexo;
import gcom.cadastro.dadocensitario.FonteDadosCensitario;
import gcom.cadastro.endereco.CepTipo;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.cadastro.endereco.LogradouroTipo;
import gcom.cadastro.endereco.LogradouroTitulo;
import gcom.cadastro.imovel.EloAnormalidade;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.PavimentoRua;
import gcom.cadastro.imovel.PocoTipo;
import gcom.cadastro.localidade.LocalidadeClasse;
import gcom.cadastro.localidade.LocalidadePorte;
import gcom.cadastro.tarifasocial.RendaTipo;
import gcom.cobranca.CobrancaSituacao;
import gcom.gui.Funcionalidade;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioTipo;
import gcom.util.HibernateUtil;
import gcom.util.SistemaException;
import gcom.util.tabelaauxiliar.TabelaAuxiliar;
import gcom.util.tabelaauxiliar.TabelaAuxiliarAbstrata;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixaReal;

import java.util.HashMap;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class DadosTelaTabelaAuxiliar {

	private String titulo;

	private TabelaAuxiliar tabela;

	private String funcionalidadeTabelaAux;

	// private int tamanhoMaximoCampo;

	private String nomeParametroFuncionalidade;

	private static HashMap telas = new HashMap();

	private static HashMap configuracaoParametrosTelas = new HashMap();

	/**
	 * Este método busca um map de funcionalidades cadastradas e cria uma
	 * instância da funcionalidade para ser usada na Tabela Auxiliar
	 * 
	 * @param nome
	 * @return
	 */
	public static DadosTelaTabelaAuxiliar obterDadosTelaTabelaAuxiliar(String nome){

		System.out.println("-----------Adicionando itens");
		// Verifica se a funcionalidade desejada já foi instanciada e já está no
		// cache
		if(!telas.containsKey(nome)){
			String[] configuracaoTela = (String[]) configuracaoParametrosTelas.get(nome);

			try{
				// Cria a instância do objeto DadosTelaTabelaAuxiliar
				DadosTelaTabelaAuxiliar dadosTela = new DadosTelaTabelaAuxiliar(configuracaoTela[1], (TabelaAuxiliar) Class.forName(
								configuracaoTela[0]).newInstance(), configuracaoTela[2], nome);
				// Coloca a instância criada no map que representa o cache com
				// as instância já criadas
				telas.put(nome, dadosTela);

				return dadosTela;
			}catch(ClassNotFoundException ex){
				throw new SistemaException();
			}catch(IllegalAccessException ex){
				throw new SistemaException();
			}catch(InstantiationException ex){
				throw new SistemaException();
			}
		}else{
			// Se o a funcionalidade já estiver no cache, então ela é retornada
			// sem a necessidade de passar pelo método
			return (DadosTelaTabelaAuxiliar) telas.get(nome);
		}
	}

	static{

		System.out.println("-----------Adicionando itens");

		configuracaoParametrosTelas.put("tipoPavimentoRua",
						new String[] {PavimentoRua.class.getName(), "Tipo Pavimento Rua", Funcionalidade.TELA_TIPO_PAVIMENTO_RUA});
		configuracaoParametrosTelas.put("operacao", new String[] {Operacao.class.getName(), "Operação", Funcionalidade.TELA_OPERACAO});
		configuracaoParametrosTelas.put("localidadeClasse",
						new String[] {LocalidadeClasse.class.getName(), "Localidade Classe", Funcionalidade.TELA_LOCALIDADE_CLASSE});
		configuracaoParametrosTelas.put("localidadePorte",
						new String[] {LocalidadePorte.class.getName(), "Localidade Porte", Funcionalidade.TELA_LOCALIDADE_PORTE});
		configuracaoParametrosTelas
						.put(
										"fonteDadosCensitario",
										new String[] {FonteDadosCensitario.class.getName(), "Fonte Dados Censitários", Funcionalidade.TELA_SETOR_CENSITARIO});
		configuracaoParametrosTelas.put("cepTipo", new String[] {CepTipo.class.getName(), "Tipo de CEP", Funcionalidade.TELA_CEP_TIPO});
		// configuracaoParametrosTelas.put("areaTipo", new String[] {
		// AreaTipo.class.getName(), "Tipo de Área",
		// Funcionalidade.TELA_AREA_TIPO });
		configuracaoParametrosTelas.put("situacaoCobranca",
						new String[] {CobrancaSituacao.class.getName(), "Situação Cobrança", Funcionalidade.TELA_COBRANCA_SITUACAO});
		configuracaoParametrosTelas.put("perfilImovel",
						new String[] {ImovelPerfil.class.getName(), "Perfil do Imóvel", Funcionalidade.TELA_IMOVEL_PERFIL});
		configuracaoParametrosTelas
						.put(
										"situacaoLigacaoEsgoto",
										new String[] {LigacaoEsgotoSituacao.class.getName(), "Situação de Ligação do Esgoto", Funcionalidade.TELA_LIGACAO_ESGOTO_SITUACAO});
		configuracaoParametrosTelas
						.put(
										"situacaoLigacaoAgua",
										new String[] {LigacaoAguaSituacao.class.getName(), "Situação de Ligação da Agua", Funcionalidade.TELA_LIGACAO_AGUA_SITUACAO});
		// configuracaoParametrosTelas.put("tipoOcupacao", new String[] {
		// OcupacaoTipo.class.getName(), "Tipo da Ocupação",
		// Funcionalidade.TELA_OCUPACAO_TIPO });
		configuracaoParametrosTelas.put("tipoLogradouro",
						new String[] {LogradouroTipo.class.getName(), "Tipo do Logradouro", Funcionalidade.TELA_LOGRADOURO_TIPO});
		configuracaoParametrosTelas.put("tituloLogradouro",
						new String[] {LogradouroTitulo.class.getName(), "Titulo do Logradouro", Funcionalidade.TELA_LOGRADOURO_TITULO});
		configuracaoParametrosTelas.put("tipoEndereco",
						new String[] {EnderecoTipo.class.getName(), "Tipo do Endereço", Funcionalidade.TELA_ENDERECO_TIPO});
		configuracaoParametrosTelas.put("tipoTelefone",
						new String[] {FoneTipo.class.getName(), "Tipo do Telefone", Funcionalidade.TELA_FONE_TIPO});
		configuracaoParametrosTelas.put("pessoaSexo",
						new String[] {PessoaSexo.class.getName(), "Pessoa do Sexo", Funcionalidade.TELA_PESSOA_SEXO});
		configuracaoParametrosTelas.put("tipoRenda",
						new String[] {RendaTipo.class.getName(), "Tipo da Renda", Funcionalidade.TELA_RENDA_TIPO});
		// configuracaoParametrosTelas.put("tipoImovelCliente", new String[] {
		// ClienteImovelTipo.class.getName(), "Tipo do Imóvel do Cliente",
		// Funcionalidade.TELA_CLIENTE_IMOVEL_TIPO });
		configuracaoParametrosTelas.put("acaoUsuario",
						new String[] {UsuarioAcao.class.getName(), "Ação do Usuário", Funcionalidade.TELA_USUARIO_ACAO});
		configuracaoParametrosTelas.put("tipoUsuario",
						new String[] {UsuarioTipo.class.getName(), "Tipo do Usuário", Funcionalidade.TELA_USUARIO_TIPO});
		configuracaoParametrosTelas
						.put(
										"perfilLigacaoEsgoto",
										new String[] {LigacaoEsgotoPerfil.class.getName(), "Perfil de Ligação do Esgoto", Funcionalidade.TELA_LIGACAO_ESGOTO_PERFIL});
		configuracaoParametrosTelas
						.put(
										"materialLigacaoEsgoto",
										new String[] {LigacaoEsgotoMaterial.class.getName(), "Material de Ligação do Esgoto", Funcionalidade.TELA_LIGACAO_ESGOTO_MATERIAL});
		configuracaoParametrosTelas
						.put(
										"diametroLigacaoEsgoto",
										new String[] {LigacaoEsgotoDiametro.class.getName(), "Diametro de Ligação do Esgoto", Funcionalidade.TELA_LIGACAO_ESGOTO_DIAMETRO});
		configuracaoParametrosTelas
						.put(
										"diametroLigacaoAgua",
										new String[] {LigacaoAguaDiametro.class.getName(), "Diametro de Ligação da Água", Funcionalidade.TELA_LIGACAO_AGUA_DIAMETRO});
		configuracaoParametrosTelas
						.put(
										"tipoOrdemEmissaoCobranca",
										new String[] {EmissaoOrdemCobrancaTipo.class.getName(), "Tipo da Ordem de Emissão de Cobranca", Funcionalidade.TELA_EMISSAO_ORDEM_COBRANCA_TIPO});
		configuracaoParametrosTelas.put("tipoConsumo",
						new String[] {ConsumoTipo.class.getName(), "Tipo de Consumo", Funcionalidade.TELA_CONSUMO_TIPO});
		configuracaoParametrosTelas
						.put(
										"perfilLigacaoAgua",
										new String[] {LigacaoAguaPerfil.class.getName(), "Perfil de Ligação da Água", Funcionalidade.TELA_LIGACAO_AGUA_PERFIL});
		configuracaoParametrosTelas.put("tipoCorte",
						new String[] {CorteTipo.class.getName(), "Tipo de Corte", Funcionalidade.TELA_CORTE_TIPO});
		configuracaoParametrosTelas.put("tipoSupressao",
						new String[] {SupressaoTipo.class.getName(), "Tipo de Supressao", Funcionalidade.TELA_SUPRESSAO_TIPO});
		configuracaoParametrosTelas.put("ibgeSetorCensitario",
						new String[] {SupressaoTipo.class.getName(), "Setor Censitario Ibge", Funcionalidade.TELA_SUPRESSAO_TIPO});
		// configuracaoParametrosTelas.put("tipoParalizacaoCobranca", new
		// String[] {
		// CobrancaParalisacaoTipo.class.getName(), "Tipo de Paralisação da
		// Cobrança",
		// Funcionalidade.TELA_COBRANCA_PARALISACAO_TIPO });
		// configuracaoParametrosTelas.put("tipoParalizacaoMotivo", new String[]
		// {
		// CobrancaParalisacaoMotivo.class.getName(), "Motivo de Paralisação da
		// Cobrança",
		// Funcionalidade.TELA_COBRANCA_PARALISACAO_MOTIVO });
		// configuracaoParametrosTelas.put("tipoParalizacaoFaturamento", new
		// String[] {
		// FaturamentoParalisacaoTipo.class.getName(), "Tipo de Paralisação do
		// Faturamento",
		// Funcionalidade.TELA_FATURAMENTO_PARALISACAO_TIPO });
		// configuracaoParametrosTelas.put("motivoParalizacaoFaturamento",
		// new String[] { FaturamentoSuspensaoMotivo.class.getName(),
		// "Motivo de Paralisação do Faturamento",
		// Funcionalidade.TELA_FATURAMENTO_PARALISACAO_MOTIVO });
		configuracaoParametrosTelas.put("poco", new String[] {PocoTipo.class.getName(), "Poço", Funcionalidade.TELA_POCO});
		configuracaoParametrosTelas.put("eloAnormalidade",
						new String[] {EloAnormalidade.class.getName(), "Elo Anormalidade", Funcionalidade.TELA_ELO_ANORMALIDADE});
	}

	/**
	 * Construtor da classe DadosTelaTabelaAuxiliar
	 * 
	 * @param titulo
	 *            Descrição do parâmetro
	 * @param tabela
	 *            Descrição do parâmetro
	 * @param funcionalidadeTabelaAuxManter
	 *            Descrição do parâmetro
	 */
	protected DadosTelaTabelaAuxiliar(String titulo, TabelaAuxiliar tabela, String funcionalidadeTabelaAux,
										String nomeParametroFuncionalidade) {

		this.titulo = titulo;
		this.tabela = tabela;
		this.funcionalidadeTabelaAux = funcionalidadeTabelaAux;
		this.nomeParametroFuncionalidade = nomeParametroFuncionalidade;
		System.out.println("inicializando objeto");
	}

	public DadosTelaTabelaAuxiliar(String titulo2, TabelaAuxiliarFaixaReal tabela2, String funcionalidadeTabelaAux2,
									String nomeParametroFuncionalidade2) {

		// TODO Auto-generated constructor stub
	}

	public DadosTelaTabelaAuxiliar(String nomeParametroFuncionalidade2, TabelaAuxiliarAbstrata abstrata,
									String nomeParametroFuncionalidade3, String nomeParametroFuncionalidade4) {

		// TODO Auto-generated constructor stub
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param dados
	 *            Descrição do parâmetro
	 */
	protected static void adicionarDadosTela(DadosTelaTabelaAuxiliar dados){

		String nomeCompletoClasse = dados.getTabelaAuxiliar().getClass().getName();
		String nomeClasse = nomeCompletoClasse.substring(nomeCompletoClasse.lastIndexOf(".") + 1, nomeCompletoClasse.length());
		System.out.println("--------adicionando " + nomeClasse);
		telas.put(nomeClasse.toLowerCase(), dados);
	}

	/**
	 * Retorna o valor de dadosTela
	 * 
	 * @param nome
	 *            Descrição do parâmetro
	 * @return O valor de dadosTela
	 */
	public static DadosTelaTabelaAuxiliar getDadosTela(String nome){

		return (DadosTelaTabelaAuxiliar) telas.get(nome.toLowerCase());
	}

	/**
	 * Retorna o valor de titulo
	 * 
	 * @return O valor de titulo
	 */
	public String getTitulo(){

		return titulo;
	}

	/**
	 * Retorna o valor de tabelaAuxiliar
	 * 
	 * @return O valor de tabelaAuxiliar
	 */
	public TabelaAuxiliar getTabelaAuxiliar(){

		return tabela;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxManter(){

		return Funcionalidade.TABELA_AUXILIAR_MANTER + funcionalidadeTabelaAux;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxFiltrar(){

		return Funcionalidade.TABELA_AUXILIAR_ABREVIADA_FILTRAR + funcionalidadeTabelaAux;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxInserir(){

		return Funcionalidade.TABELA_AUXILIAR_INSERIR + funcionalidadeTabelaAux;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaIndicadorManter(){

		return Funcionalidade.TABELA_AUXILIAR_INDICADOR_MANTER + funcionalidadeTabelaAux;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaIndicadorFiltrar(){

		return Funcionalidade.TABELA_AUXILIAR_INDICADOR_FILTRAR + funcionalidadeTabelaAux;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaIndicadorInserir(){

		return Funcionalidade.TABELA_AUXILIAR_INDICADOR_INSERIR + funcionalidadeTabelaAux;
	}

	/**
	 * Retorna o valor de tamanhoMaximoCampo
	 * 
	 * @return O valor de tamanhoMaximoCampo
	 */
	public int getTamanhoMaximoDescricao(){

		return HibernateUtil.getColumnSize(tabela.getClass(), "descricao");
	}

	public String getNomeParametroFuncionalidade(){

		return nomeParametroFuncionalidade;
	}

	public void setNomeParametroFuncionalidade(String nomeParametroFuncionalidade){

		this.nomeParametroFuncionalidade = nomeParametroFuncionalidade;
	}

}
