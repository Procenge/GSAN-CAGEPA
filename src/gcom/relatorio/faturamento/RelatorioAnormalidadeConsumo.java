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
 * Ivan S�rgio da Silva J�nior
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

package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.GerarRelatorioAnormalidadeConsumoHelper;
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

import java.util.*;

/**
 * classe respons�vel por criar o relat�rio de volumes faturados
 * 
 * @author Rafael Corr�a
 * @created 12/09/2007
 */
public class RelatorioAnormalidadeConsumo
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAnormalidadeConsumo(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ANORMALIDADE_CONSUMO);
	}

	@Deprecated
	public RelatorioAnormalidadeConsumo() {

		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer idGrupo = (Integer) getParametro("idGrupo");
		Short cdRota = (Short) getParametro("cdRota");
		Integer idGerenciaRegional = (Integer) getParametro("idGerenciaRegional");
		Integer idUnidadeNegocio = (Integer) getParametro("idUnidadeNegocio");
		Integer idElo = (Integer) getParametro("idElo");
		Integer idLocalidadeInicial = (Integer) getParametro("idLocalidadeInicial");
		Integer idLocalidadeFinal = (Integer) getParametro("idLocalidadeFinal");
		Integer idAnormalidadeLeitura = (Integer) getParametro("idAnormalidadeLeitura");
		Integer idAnormalidadeConsumo = (Integer) getParametro("idAnormalidadeConsumo");
		Integer numeroOcorrencias = (Integer) getParametro("numeroOcorrencias");
		String indicadorOcorrenciasIguais = (String) getParametro("ocorrenciasIguais");
		Integer idImovelPerfil = (Integer) getParametro("idImovelPerfil");
		Integer referencia = (Integer) getParametro("referencia");
		Integer mediaConsumoInicial = (Integer) getParametro("mediaConsumoInicial");
		Integer mediaConsumoFinal = (Integer) getParametro("mediaConsumoFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioAnormalidadeConsumoBean relatorioBean = null;

		Collection colecaoGerarRelatorioAnormalidadeConsumoHelper = fachada.pesquisarDadosRelatorioAnormalidadeConsumo(idGrupo, cdRota,
						idGerenciaRegional, idUnidadeNegocio, idElo, idLocalidadeInicial, idLocalidadeFinal, referencia, idImovelPerfil,
						numeroOcorrencias, indicadorOcorrenciasIguais, mediaConsumoInicial, mediaConsumoFinal, idAnormalidadeConsumo,
						idAnormalidadeLeitura);

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecaoGerarRelatorioAnormalidadeConsumoHelper != null && !colecaoGerarRelatorioAnormalidadeConsumoHelper.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoGerarRelatorioAnormalidadeConsumoHelperIterator = colecaoGerarRelatorioAnormalidadeConsumoHelper.iterator();

			Integer totalGrupo = new Integer("0");
			Integer totalGerenciaRegional = new Integer("0");
			Integer totalUnidadeNegocio = new Integer("0");
			Integer totalElo = new Integer("0");
			Integer totalLocalidade = new Integer("0");

			Integer idGrupoAnterior = null;
			Integer idGerenciaRegionalAnterior = null;
			Integer idUnidadeNegocioAnterior = null;
			Integer idEloAnterior = null;
			Integer idLocalidadeAnterior = null;

			boolean zerarGrupo = false;
			boolean zerarGerenciaRegional = false;
			boolean zerarUnidadeNegocio = false;
			boolean zerarElo = false;
			boolean zerarLocalidade = false;

			// la�o para criar a cole��o de par�metros da analise
			while(colecaoGerarRelatorioAnormalidadeConsumoHelperIterator.hasNext()){

				GerarRelatorioAnormalidadeConsumoHelper gerarRelatorioAnormalidadeConsumoHelper = (GerarRelatorioAnormalidadeConsumoHelper) colecaoGerarRelatorioAnormalidadeConsumoHelperIterator
								.next();

				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio

				// Grupo
				String grupo = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getIdGrupo() != null){
					grupo = gerarRelatorioAnormalidadeConsumoHelper.getIdGrupo() + " - "
									+ gerarRelatorioAnormalidadeConsumoHelper.getNomeGrupo();

					if(idGrupoAnterior == null){
						idGrupoAnterior = gerarRelatorioAnormalidadeConsumoHelper.getIdGrupo();
					}else{
						if(!idGrupoAnterior.equals(gerarRelatorioAnormalidadeConsumoHelper.getIdGrupo())){
							zerarGrupo = true;
						}
					}
				}

				// Ger�ncia Regional
				String gerenciaRegional = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getIdGerenciaRegional() != null){
					gerenciaRegional = gerarRelatorioAnormalidadeConsumoHelper.getIdGerenciaRegional() + " - "
									+ gerarRelatorioAnormalidadeConsumoHelper.getNomeGerenciaRegional();

					if(idGerenciaRegionalAnterior == null){
						idGerenciaRegionalAnterior = gerarRelatorioAnormalidadeConsumoHelper.getIdGerenciaRegional();
					}else{
						if(!idGerenciaRegionalAnterior.equals(gerarRelatorioAnormalidadeConsumoHelper.getIdGerenciaRegional())){
							zerarGerenciaRegional = true;
						}
					}
				}

				// Unidade de Neg�cio
				String unidadeNegocio = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getIdUnidadeNegocio() != null){
					unidadeNegocio = gerarRelatorioAnormalidadeConsumoHelper.getIdUnidadeNegocio() + " - "
									+ gerarRelatorioAnormalidadeConsumoHelper.getNomeUnidadeNegocio();

					if(idUnidadeNegocioAnterior == null){
						idUnidadeNegocioAnterior = gerarRelatorioAnormalidadeConsumoHelper.getIdUnidadeNegocio();
					}else{
						if(!idUnidadeNegocioAnterior.equals(gerarRelatorioAnormalidadeConsumoHelper.getIdUnidadeNegocio())){
							zerarUnidadeNegocio = true;
						}
					}
				}

				// Elo
				String elo = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getIdElo() != null){
					elo = gerarRelatorioAnormalidadeConsumoHelper.getIdElo() + " - " + gerarRelatorioAnormalidadeConsumoHelper.getNomeElo();

					if(idEloAnterior == null){
						idEloAnterior = gerarRelatorioAnormalidadeConsumoHelper.getIdElo();
					}else{
						if(!idEloAnterior.equals(gerarRelatorioAnormalidadeConsumoHelper.getIdElo())){
							zerarElo = true;
						}
					}
				}

				// Localidade
				String localidade = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getIdLocalidade() != null){
					localidade = gerarRelatorioAnormalidadeConsumoHelper.getIdLocalidade() + " - "
									+ gerarRelatorioAnormalidadeConsumoHelper.getNomeLocalidade();

					if(idLocalidadeAnterior == null){
						idLocalidadeAnterior = gerarRelatorioAnormalidadeConsumoHelper.getIdLocalidade();
					}else{
						if(!idLocalidadeAnterior.equals(gerarRelatorioAnormalidadeConsumoHelper.getIdLocalidade())){
							zerarLocalidade = true;
						}
					}
				}

				// Caso tenha mudado de Grupo zera seus totalizadores
				if(zerarGrupo){
					totalGrupo = new Integer("0");
					idGrupoAnterior = null;
					zerarGrupo = false;
				}

				// Caso tenha mudado de Ger�ncia Regional zera seus totalizadores
				if(zerarGerenciaRegional){
					totalGerenciaRegional = new Integer("0");
					idGerenciaRegionalAnterior = null;
					zerarGerenciaRegional = false;
				}

				// Caso tenha mudado de Unidade de Neg�cio zera seus totalizadores
				if(zerarUnidadeNegocio){
					totalUnidadeNegocio = new Integer("0");
					idUnidadeNegocioAnterior = null;
					zerarUnidadeNegocio = false;
				}

				// Caso tenha mudado de Elo zera seus totalizadores
				if(zerarElo){
					totalElo = new Integer("0");
					idEloAnterior = null;
					zerarElo = false;
				}

				// Caso tenha mudado de Localidade zera seus totalizadores
				if(zerarLocalidade){
					totalLocalidade = new Integer("0");
					idLocalidadeAnterior = null;
					zerarLocalidade = false;
				}

				totalGrupo = totalGrupo + 1;
				totalGerenciaRegional = totalGerenciaRegional + 1;
				totalUnidadeNegocio = totalUnidadeNegocio + 1;
				totalElo = totalElo + 1;
				totalLocalidade = totalLocalidade + 1;

				// Im�vel, Endere�o e Categoria
				String idImovel = "";
				String endereco = "";
				String descricaoCategoria = "";
				String inscricaoImovel = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getIdImovel() != null){
					idImovel = gerarRelatorioAnormalidadeConsumoHelper.getIdImovel().toString();
					Imovel imovel = new Imovel();
					imovel.setId(gerarRelatorioAnormalidadeConsumoHelper.getIdImovel());
					endereco = fachada.pesquisarEndereco(gerarRelatorioAnormalidadeConsumoHelper.getIdImovel());
					Categoria categoria = fachada.obterDescricoesCategoriaImovel(imovel);

					Imovel imovelInscricao = (Imovel) fachada.pesquisarImovel(gerarRelatorioAnormalidadeConsumoHelper.getIdImovel());

					inscricaoImovel = imovelInscricao.getInscricaoFormatada();

					if(categoria.getDescricaoAbreviada() != null){
						descricaoCategoria = categoria.getDescricaoAbreviada();
					}

				}

				// Nome do Usu�rio
				String nomeUsuario = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getNomeUsuario() != null){
					nomeUsuario = gerarRelatorioAnormalidadeConsumoHelper.getNomeUsuario();
				}

				// Situa��o da Liga��o de �gua
				String situacaoAgua = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getSituacaoLigacaoAgua() != null){
					situacaoAgua = gerarRelatorioAnormalidadeConsumoHelper.getSituacaoLigacaoAgua().toString();
				}

				// Situa��o de Esgoto
				String situacaoEsgoto = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getSituacaoLigacaoEsgoto() != null){
					situacaoEsgoto = gerarRelatorioAnormalidadeConsumoHelper.getSituacaoLigacaoEsgoto().toString();
				}

				// D�bito Autom�tico
				String debitoAutomatico = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getIndicadorDebito() != null){
					if(gerarRelatorioAnormalidadeConsumoHelper.getIndicadorDebito().equals(ConstantesSistema.SIM)){
						debitoAutomatico = "SIM";
					}else{
						debitoAutomatico = "N�O";
					}
				}

				// M�dia de Consumo
				String media = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getConsumoMedio() != null){
					media = gerarRelatorioAnormalidadeConsumoHelper.getConsumoMedio().toString();
				}

				// Consumo
				String consumo = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getConsumoMes() != null){
					consumo = gerarRelatorioAnormalidadeConsumoHelper.getConsumoMes().toString();
				}

				// Anormalidade de Consumo
				String anormalidadeConsumo = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getDescricaoAbrevConsumoAnormalidade() != null){
					anormalidadeConsumo = gerarRelatorioAnormalidadeConsumoHelper.getDescricaoAbrevConsumoAnormalidade();
				}

				// Anormalidade de Leitura
				String anormalidadeLeitura = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getIdLeituraAnormalidade() != null){
					anormalidadeLeitura = gerarRelatorioAnormalidadeConsumoHelper.getIdLeituraAnormalidade().toString();
				}

				// Quantidade de Economias
				String qtdeEconomias = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getQuantidadeEconomias() != null){
					qtdeEconomias = gerarRelatorioAnormalidadeConsumoHelper.getQuantidadeEconomias().toString();
				}

				// Capacidade do Hidr�metro
				String capacidadeHidrometro = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getCapacidadeHidrometro() != null){
					capacidadeHidrometro = gerarRelatorioAnormalidadeConsumoHelper.getCapacidadeHidrometro();
				}

				// Local de Instala��o do Hidr�metro
				String localInstalacaoHidrometro = "";

				if(gerarRelatorioAnormalidadeConsumoHelper.getLocalInstalacaoHidrometro() != null){
					localInstalacaoHidrometro = gerarRelatorioAnormalidadeConsumoHelper.getLocalInstalacaoHidrometro();
				}

				relatorioBean = new RelatorioAnormalidadeConsumoBean(

				// Grupo
								grupo,

								// Ger�ncia Regional
								gerenciaRegional,

								// Unidade de Neg�cio
								unidadeNegocio,

								// Elo
								elo,

								// Localidade
								localidade,

								// Im�vel
								idImovel,

								// Usu�rio
								nomeUsuario,

								// Endere�o
								endereco,

								// Situa��o de �gua
								situacaoAgua,

								// Situa��o de Esgoto
								situacaoEsgoto,

								// D�bito Autom�tico
								debitoAutomatico,

								// M�dia de Consumo
								media,

								// Consumo
								consumo,

								// Anormalidade de Consumo
								anormalidadeConsumo,

								// Anormalidade de Leitura
								anormalidadeLeitura,

								// Categoria
								descricaoCategoria,

								// Quantidade de Economias
								qtdeEconomias,

								// Tipo de Medi��o
								gerarRelatorioAnormalidadeConsumoHelper.getTipoMedicao(),

								// Capacidade do Hidr�metro
								capacidadeHidrometro,

								// Local de Instala��o do Hidr�metro
								localInstalacaoHidrometro,

								// Total do Grupo
								totalGrupo.toString(),

								// Total da Ger�ncia Regional
								totalGerenciaRegional.toString(),

								// Total da Unidade de Neg�cio
								totalUnidadeNegocio.toString(),

								// Total do Elo
								totalElo.toString(),

								// Total da Localidade
								totalLocalidade.toString(),

								// Inscri��o do Im�vel
								inscricaoImovel);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);

			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("mesAno", Util.formatarAnoMesParaMesAno(referencia));

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ANORMALIDADE_CONSUMO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.ANORMALIDADE_CONSUMO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;

	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAnormalidadeConsumo", this);
	}
}