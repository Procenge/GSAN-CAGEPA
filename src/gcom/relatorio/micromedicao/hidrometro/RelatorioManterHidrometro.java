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

package gcom.relatorio.micromedicao.hidrometro;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.FiltroHidrometroHelper;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Rafael Corr�a
 * @created 23 de Setembro de 2005
 * @version 1.0
 */

public class RelatorioManterHidrometro
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the RelatorioManterHidrometro object
	 */
	public RelatorioManterHidrometro(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_HIDROMETRO_MANTER);
	}

	@Deprecated
	public RelatorioManterHidrometro() {

		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param hidrometros
	 *            Description of the Parameter
	 * @param HidrometroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroHidrometroHelper filtroHidrometroHelper = (FiltroHidrometroHelper) getParametro("filtroHidrometroHelper");
		Hidrometro hidrometroParametros = (Hidrometro) getParametro("hidrometroParametros");
		String fixo = (String) getParametro("fixo");
		String faixaInicial = (String) getParametro("faixaInicial");
		String faixaFinal = (String) getParametro("faixaFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterHidrometroBean relatorioBean = null;

		Collection hidrometrosNovos = null;

		if(filtroHidrometroHelper != null){

			filtroHidrometroHelper.setConsultaSemLimites(true);

			// consulta para trazer objeto completo
			hidrometrosNovos = fachada.pesquisarHidrometroFiltro(filtroHidrometroHelper, null);

		}else{
			hidrometrosNovos = fachada.pesquisarNumeroHidrometroFaixaRelatorio(fixo, faixaInicial, faixaFinal);
		}

		if(hidrometrosNovos != null && !hidrometrosNovos.isEmpty()){
			// coloca a cole��o de par�metros da analise no iterator
			Iterator hidrometroNovoIterator = hidrometrosNovos.iterator();

			SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

			// la�o para criar a cole��o de par�metros da analise
			while(hidrometroNovoIterator.hasNext()){

				Hidrometro hidrometroNovo = (Hidrometro) hidrometroNovoIterator.next();

				String situacao = hidrometroNovo.getHidrometroSituacao() == null ? "" : hidrometroNovo.getHidrometroSituacao()
								.getDescricao();

				// alterado por Fl�vio Leonardo
				// Caso a situa�ao do hidrometro seja INSTALADO
				// colocar a matr�cula do im�vel junto com a Situa��o
				if(hidrometroNovo.getHidrometroSituacao().getId().equals(Hidrometro.SITUACAO_INSTALADO)){
					situacao = situacao + "/" + fachada.pesquisarImovelPeloHidrometro(hidrometroNovo.getId());
				}

				relatorioBean = new RelatorioManterHidrometroBean(

				// N�mero
								hidrometroNovo.getNumero(),

								// Data de Aquisi��o
								dataFormatada.format(hidrometroNovo.getDataAquisicao()),

								// Ano de Fabrica��o
								hidrometroNovo.getAnoFabricacao().toString(),

								// Finalidade
								hidrometroNovo.getIndicadorMacromedidor() != null && hidrometroNovo.getIndicadorMacromedidor() == 1 ? "COMERCIAL"
												: "OPERACIONAL",

								// Classe Metrol�gicao
								hidrometroNovo.getHidrometroClasseMetrologica().getDescricao(),

								// Marca
								hidrometroNovo.getHidrometroMarca().getDescricao(),

								// Di�metro
								hidrometroNovo.getHidrometroDiametro().getDescricao(),

								// Capacidade
								hidrometroNovo.getHidrometroCapacidade().getDescricao(),

								// N�mero de Digitos
								hidrometroNovo.getNumeroDigitosLeitura().toString(),

								// Tipo
								hidrometroNovo.getHidrometroTipo().getDescricao(),

								// Situa��o
								situacao);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}

		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dataAquisicao = "";
		if(hidrometroParametros.getDataAquisicao() != null && !hidrometroParametros.getDataAquisicao().equals("")){
			dataAquisicao = format.format(hidrometroParametros.getDataAquisicao());
		}

		parametros.put("numero", hidrometroParametros.getNumero());

		parametros.put("dataAquisicao", dataAquisicao);

		parametros
						.put("anoFabricacao", hidrometroParametros.getAnoFabricacao() == null ? "" : ""
										+ hidrometroParametros.getAnoFabricacao());

		parametros.put("classeMetrologica", hidrometroParametros.getHidrometroClasseMetrologica() == null ? "" : hidrometroParametros
						.getHidrometroClasseMetrologica().getDescricao());

		parametros.put("marca", hidrometroParametros.getHidrometroMarca() == null ? "" : hidrometroParametros.getHidrometroMarca()
						.getDescricao());

		parametros.put("diametro", hidrometroParametros.getHidrometroDiametro() == null ? "" : hidrometroParametros.getHidrometroDiametro()
						.getDescricao());

		parametros.put("capacidade", hidrometroParametros.getHidrometroCapacidade() == null ? "" : hidrometroParametros
						.getHidrometroCapacidade().getDescricao());

		parametros.put("tipo", hidrometroParametros.getHidrometroTipo() == null ? "" : hidrometroParametros.getHidrometroTipo()
						.getDescricao());

		parametros.put("idLocalArmazenagem", hidrometroParametros.getHidrometroLocalArmazenagem().getId() == null ? "" : ""
						+ hidrometroParametros.getHidrometroLocalArmazenagem().getId());

		parametros.put("nomeLocalArmazenagem", hidrometroParametros.getHidrometroLocalArmazenagem().getDescricao());

		parametros.put("fixo", fixo);

		parametros.put("faixaInicial", faixaInicial);

		parametros.put("faixaFinal", faixaFinal);

		String finalidade = "";

		if(hidrometroParametros.getIndicadorMacromedidor() != null && !hidrometroParametros.getIndicadorMacromedidor().equals("")){
			if(hidrometroParametros.getIndicadorMacromedidor().equals(new Short("1"))){
				finalidade = "Comercial";
			}else{
				finalidade = "Operacional";
			}
		}

		parametros.put("finalidade", finalidade);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_HIDROMETRO_MANTER, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_HIDROMETRO, idFuncionalidadeIniciada, null);
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

		if(getParametro("filtroHidrometroHelper") != null){

			retorno = Fachada.getInstancia().pesquisarHidrometroFiltroTotalRegistros(
							(FiltroHidrometroHelper) getParametro("filtroHidrometroHelper"));

		}else{

			String faixaInicial = (String) getParametro("faixaInicial");
			String faixaFinal = (String) getParametro("faixaFinal");
			String fixo = (String) getParametro("fixo");

			int tamanhoParteFixa = fixo.length();
			int tamanhoParteFaixa = 10 - tamanhoParteFixa;

			String numeroFormatadoInicial = Util.adicionarZerosEsquedaNumero(tamanhoParteFaixa, faixaInicial);
			String numeroFormatadoFinal = Util.adicionarZerosEsquedaNumero(tamanhoParteFaixa, faixaFinal);

			Integer totalRegistros = Fachada.getInstancia().pesquisarNumeroHidrometroFaixaCount(fixo, fixo + numeroFormatadoInicial,
							fixo + numeroFormatadoFinal);

			retorno = totalRegistros.intValue();
		}

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterHidrometro", this);
	}

}
