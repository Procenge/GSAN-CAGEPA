/**
 * 
 */
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

package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.cadastro.imovel.PiscinaVolumeFaixa;
import gcom.cadastro.imovel.ReservatorioVolumeFaixa;
import gcom.gui.Funcionalidade;
import gcom.gui.util.tabelaauxiliar.DadosTelaTabelaAuxiliar;
import gcom.util.HibernateUtil;
import gcom.util.SistemaException;
import gcom.util.tabelaauxiliar.TabelaAuxiliar;
import gcom.util.tabelaauxiliar.TabelaAuxiliarAbstrata;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixaReal;

import java.util.HashMap;

/**
 * @author Administrador
 */
public class DadosTelaTabelaAuxiliarFaixaReal
				extends DadosTelaTabelaAuxiliar {

	// protected DadosTelaTabelaAuxiliarFaixaReal(String titulo, TabelaAuxiliar
	// tabela, String funcionalidadeTabelaAux, String
	// nomeParametroFuncionalidade) {
	// super(titulo, tabela, funcionalidadeTabelaAux,
	// nomeParametroFuncionalidade);
	// // TODO Auto-generated constructor stub
	// }

	public static String FAIXA_VOLUME = "1";

	public static String FAIXA_AREA = "2";

	private String titulo;

	private TabelaAuxiliarAbstrata tabela;

	private String funcionalidadeTabelaAux;

	private String indicadorFaixaVolumeArea;

	// private int tamanhoMaximoCampo;

	private String nomeParametroFuncionalidade;

	private static HashMap telas = new HashMap();

	private static HashMap configuracaoParametrosTelas = new HashMap();

	/**
	 * Este m�todo busca um map de funcionalidades cadastradas e cria uma
	 * inst�ncia da funcionalidade para ser usada na Tabela Auxiliar
	 * 
	 * @param nome
	 * @return
	 */
	public static DadosTelaTabelaAuxiliar obterDadosTelaTabelaAuxiliar(String nome){

		System.out.println("-----------Adicionando itens");
		// Verifica se a funcionalidade desejada j� foi instanciada e j� est� no
		// cache
		if(!telas.containsKey(nome)){
			String[] configuracaoTela = (String[]) configuracaoParametrosTelas.get(nome);

			try{
				// Cria a inst�ncia do objeto DadosTelaTabelaAuxiliar
				DadosTelaTabelaAuxiliarFaixaReal dadosTela = new DadosTelaTabelaAuxiliarFaixaReal(configuracaoTela[1],
								(TabelaAuxiliarFaixaReal) Class.forName(configuracaoTela[0]).newInstance(), configuracaoTela[2], nome,
								configuracaoTela[3]);
				// Coloca a inst�ncia criada no map que representa o cache com
				// as inst�ncia j� criadas
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
			// Se o a funcionalidade j� estiver no cache, ent�o ela � retornada
			// sem a necessidade de passar pelo m�todo
			return (DadosTelaTabelaAuxiliarFaixaReal) telas.get(nome);
		}
	}

	static{

		System.out.println("-----------Adicionando itens");

		configuracaoParametrosTelas
						.put(
										"piscinaVolumeFaixa",
										new String[] {PiscinaVolumeFaixa.class.getName(), "Faixa Volume Piscina", Funcionalidade.TELA_PISCINA_VOLUME_FAIXA, FAIXA_VOLUME});

		configuracaoParametrosTelas
						.put(
										"reservatorioVolumeFaixa",
										new String[] {ReservatorioVolumeFaixa.class.getName(), "Faixa Volume Reservatorio", Funcionalidade.TELA_RESERVATORIO_VOLUME_FAIXA, FAIXA_VOLUME});
		configuracaoParametrosTelas
						.put("areaConstruidaFaixa",
										new String[] {AreaConstruidaFaixa.class.getName(), "Faixa Area Construida", Funcionalidade.TELA_AREA_CONSTRUIDA_FAIXA, FAIXA_AREA});
	}

	/**
	 * Construtor da classe DadosTelaTabelaAuxiliar
	 * 
	 * @param titulo
	 *            Descri��o do par�metro
	 * @param tabela
	 *            Descri��o do par�metro
	 * @param funcionalidadeTabelaAuxManter
	 *            Descri��o do par�metro
	 */
	protected DadosTelaTabelaAuxiliarFaixaReal(String titulo, TabelaAuxiliarAbstrata tabela, String funcionalidadeTabelaAux,
												String nomeParametroFuncionalidade, String indicadorFaixaVolumeArea) {

		super(nomeParametroFuncionalidade, (TabelaAuxiliarAbstrata) tabela, nomeParametroFuncionalidade, nomeParametroFuncionalidade);
		this.titulo = titulo;
		this.tabela = tabela;
		this.funcionalidadeTabelaAux = funcionalidadeTabelaAux;
		this.nomeParametroFuncionalidade = nomeParametroFuncionalidade;
		this.indicadorFaixaVolumeArea = indicadorFaixaVolumeArea;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param dados
	 *            Descri��o do par�metro
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
	 *            Descri��o do par�metro
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
	public TabelaAuxiliarAbstrata getTabelaAuxiliarAbstrata(){

		return tabela;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxManter(){

		return Funcionalidade.TABELA_AUXILIAR_FAIXA_MANTER + funcionalidadeTabelaAux;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaFaixaRealFiltrar(){

		return Funcionalidade.TABELA_AUXILIAR_FAIXA_REAL_FILTRAR + funcionalidadeTabelaAux;
	}

	/**
	 * Retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaFaixaRealInserir(){

		return Funcionalidade.TABELA_AUXILIAR_FAIXA_REAL_INSERIR + funcionalidadeTabelaAux;
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

	/**
	 * Retorna o valor de tabelaAuxiliar
	 * 
	 * @return O valor de tabelaAuxiliar
	 */
	public TabelaAuxiliar getTabela(){

		return super.getTabelaAuxiliar();
	}

	public String getIndicadorFaixaVolumeArea(){

		return indicadorFaixaVolumeArea;
	}
}
