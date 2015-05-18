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

package gcom.cadastro;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.sistemaparametro.bean.DadosEnvioEmailHelper;
import gcom.cobranca.bean.FiltroRelatorioDebitoPorResponsavelHelper;
import gcom.relatorio.cadastro.imovel.*;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Administrador
 */
public interface IRepositorioCadastro {

	/**
	 * Pesquisa os feriados(nacionais e municipais)
	 * 
	 * @author K�ssia Albuquerque
	 * @date 22/01/2007
	 */
	public Collection pesquisarFeriado(Short tipoFeriado, String descricao, Date dataFeriadoInicio, Date dataFeriadoFim,
					Integer idMunicipio, Integer numeroPagina) throws ErroRepositorioException;

	/**
	 * Pesquisar quantidade de registro dos feriados(nacionais e municipais)
	 * 
	 * @author K�ssia Albuquerque
	 * @date 22/01/2007
	 */
	public Integer pesquisarFeriadoCount(Short tipoFeriado, String descricao, Date dataFeriadoInicio, Date dataFeriadoFim,
					Integer idMunicipio) throws ErroRepositorioException;

	/**
	 * Faz um Update na base
	 * 
	 * @author Kassia Albuquerque e Ana Maria
	 * @date 06/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarMensagemSistema(String mensagemSistema) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados do email do batch para ser enviado
	 * 
	 * @author S�vio Luiz
	 * @date 13/03/2007
	 */
	public EnvioEmail pesquisarEnvioEmail(Integer idEnvioEmail) throws ErroRepositorioException;

	public DadosEnvioEmailHelper pesquisarDadosEmailSistemaParametros() throws ErroRepositorioException;

	/**
	 * Pesquisar todos ids dos setores comerciais.
	 * [UC0564 - Gerar Resumo das Instala��es de Hidr�metros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsSetorComercial() throws ErroRepositorioException;

	/**
	 * Migra��o dos dados do munic�pio de Ribeir�o - O sistema
	 * gerar as tabelas cliente, cliente_endere�o, imovel, cliente_imovel,
	 * imovel_subcategoria, ligacao_agua a parti da tabela Cadastro_ribeirao;
	 * 
	 * @author Ana Maria
	 * @throws ControladorException
	 */
	public Object[] pesquisarSetorQuadra(Integer idLocalidade) throws ErroRepositorioException;

	public Integer pesquisarCEP() throws ErroRepositorioException;

	public Integer pesquisarBairro() throws ErroRepositorioException;

	public Integer pesquisarLogradouroBairro(Integer codigoLogradouro) throws ErroRepositorioException;

	public Integer pesquisarLogradouroCep(Integer codigoLogradouro) throws ErroRepositorioException;

	public void inserirClienteEndereco(Integer idCliente, String numeroImovelMenor, String numeroImovelMaior, Integer idCep,
					Integer idBairro, Integer idLograd, Integer idLogradBairro, Integer idLogradCep) throws ErroRepositorioException;

	public void inserirClienteImovel(Integer idCliente, Integer idImovel, String data) throws ErroRepositorioException;

	public void inserirImovelSubcategoria(Integer idImovel, Integer idSubcategoria) throws ErroRepositorioException;

	public void inserirLigacaoAgua(Integer idImovel, String dataBD) throws ErroRepositorioException;

	public Collection pesquisarCadastroRibeiraop() throws ErroRepositorioException;

	public void atualizarImovelRibeirao(Integer idImovel, Integer codigo) throws ErroRepositorioException;

	/**
	 * Fim - Migra��o dos dados do munic�pio de Ribeir�o
	 */

	/**
	 * Pesquisa os im�veis do cliente de acordo com o tipo de rela��o
	 * [UC0251] Gerar Atividade de A��o de Cobran�a [SB0001] Gerar Atividade de
	 * A��o de Cobran�a para os Im�veis do Cliente
	 * 
	 * @author S�vio Luiz
	 * @created 23/11/2007
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarClientesSubordinados(Integer idCliente)

	throws ErroRepositorioException;

	/**
	 * [UC0624] Gerar Relat�rio para Atualiza��o Cadastral
	 */

	public Collection pesquisarRelatorioAtualizacaoCadastral(Collection idLocalidades, Collection idSetores, Collection idQuadras,
					String rotaInicial, String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal)
					throws ErroRepositorioException;

	/**
	 * [UC0725] Gerar Relat�rio de Im�veis por Situa��o da Liga��o de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 03/12/2007
	 * @param FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisSituacaoLigacaoAgua(FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0725] Gerar Relat�rio de Im�veis por Situa��o da Liga��o de Agua
	 * Pesquisa o Total Registro
	 * 
	 * @author Rafael Pinto
	 * @date 04/12/2007
	 * @param FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisSituacaoLigacaoAgua(FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0726] Gerar Relat�rio de Im�veis com Faturas em Atraso
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtraso
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisFaturasAtraso(FiltrarRelatorioImoveisFaturasAtrasoHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0726] Gerar Relat�rio de Im�veis com Faturas em Atraso
	 * Pesquisa o Total Registro
	 * 
	 * @author Rafael Pinto
	 * @date 04/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarTotalRegistroRelatorioImoveisFaturasAtraso(FiltrarRelatorioImoveisFaturasAtrasoHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0727] Gerar Relat�rio de Im�veis por Consumo Medio
	 * 
	 * @author Bruno Barros
	 * @date 17/12/2007
	 * @param FiltrarRelatorioImoveisConsumoMedio
	 * @return Collection<FiltrarRelatorioImoveisConsumoMedio[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisConsumoMedioHelper> pesquisarRelatorioImoveisConsumoMedio(
					FiltrarRelatorioImoveisConsumoMedioHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC0727] Gerar Relat�rio de Im�veis por Consumo Medio
	 * Pesquisa a quantidade de imoveis para o relatorio de imoveis por consumo medio
	 * 
	 * @author Bruno Barros
	 * @data 17/12/2007
	 * @param filtro
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisConsumoMedio(FiltrarRelatorioImoveisConsumoMedioHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0731] Gerar Relat�rio de Im�veis com os Ultimos Consumos de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 18/12/2007
	 * @param FiltrarRelatorioImoveisUltimosConsumosAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisUltimosConsumosAgua(FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0731] Gerar Relat�rio de Im�veis com os Ultimos Consumos de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 18/12/2007
	 * @param FiltrarRelatorioImoveisUltimosConsumosAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisUltimosConsumosAgua(FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC00728] Gerar Relat�rio de Im�veis Ativos e N�o Medidos
	 * 
	 * @author Rafael Pinto
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisAtivosNaoMedidosHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisAtivosNaoMedidos(FiltrarRelatorioImoveisAtivosNaoMedidosHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC00728] Gerar Relat�rio de Im�veis Ativos e N�o Medidos
	 * 
	 * @author Rafael Pinto
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisAtivosNaoMedidosHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisAtivosNaoMedidos(FiltrarRelatorioImoveisAtivosNaoMedidosHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC00728] Gerar Relat�rio de Im�veis Excluidos
	 * 
	 * @authorAlcira Rocha
	 * @date 31/01/2013
	 * @param FiltrarRelatorioImoveisExcluidosHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisExcluidos(FiltrarRelatorioImoveisExcluidosHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC00728] Gerar Relat�rio de Im�veis Excluidos
	 * 
	 * @author Alcira Rocha
	 * @date 31/01/2013
	 * @param FiltrarRelatorioImoveisExcluidosHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisExcluidos(FiltrarRelatorioImoveisExcluidosHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC00730] Gerar Relat�rio de Im�veis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 * @param FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
					FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC00730] Gerar Relat�rio de Im�veis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 * @param FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
					FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC0729] Gerar Relat�rio de Im�veis por Tipo Consumo
	 * 
	 * @author Bruno Barros
	 * @date 14/01/2008
	 * @param FiltrarRelatorioImoveisTipoConsumo
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisTipoConsumo(FiltrarRelatorioImoveisTipoConsumoHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0729] Gerar Relat�rio de Im�veis por Tipo Consumo
	 * 
	 * @author Bruno Barros
	 * @date 14/01/2008
	 * @param FiltrarRelatorioImoveisTipoConsumo
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisTipoConsumo(FiltrarRelatorioImoveisTipoConsumoHelper filtro)
					throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por verificar se existe LogradouroBairro com o id informado.
	 * 
	 * @author Virg�nia Melo
	 * @date 07/07/2009
	 * @param idLogradouroBairro
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaLogradouroBairro(Integer idLogradouroBairro) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Relat�rio Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2011
	 *       Obter dados dos Grandes Consumidores pelos parametros informados
	 */
	public Collection pesquisaRelatorioGrandesConsumidores(Integer idGerencia, Integer idLocalidadeInicial, Integer idLocalidadeFinal)
					throws ErroRepositorioException;

	/**
	 * [UC0XXX] Gerar Relat�rio Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2011
	 *       Obter Total de Registros do Relat�rio Grandes
	 *       Consumidores
	 */
	public Integer pesquisarTotalRegistrosRelatorioGrandesConsumidores(Integer idGerencia, Integer idLocalidadeInicial,
					Integer idLocalidadeFinal) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Relat�rio Usu�rios em D�bito Autom�tico
	 * 
	 * @author Anderson Italo
	 * @date 23/02/2011 Obter dados dos Usu�rios com D�bito Autom�tico pelos
	 *       parametros informados
	 */
	public Collection pesquisaRelatorioUsuarioDebitoAutomatico(Integer idBancoInicial, Integer idBancoFinal)
					throws ErroRepositorioException;

	/**
	 * [UC0XXX] Relat�rio Usu�rios em D�bito Autom�tico
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2011 Obter Total de Registros do Relat�rio Usu�rios com
	 *       D�bito Autom�tico
	 */
	public Integer pesquisarTotalRegistrosRelatorioUsuarioDebitoAutomatico(Integer idBancoInicial, Integer idBancoFinal)
					throws ErroRepositorioException;

	/**
	 * Consulta os motivos de exclus�o do programa �gua Para Todos
	 * 
	 * @author Luciano Galv�o
	 * @date 20/03/2012
	 */
	public List consultarAguaParaTodosMotivosExclusao() throws ErroRepositorioException;

	/**
	 * Atualiza a imagem de Relat�rio da empresa na tabela de par�metros do sistema
	 * 
	 * @author lgalvao (Luciano Galv�o)
	 * @date 22/05/2012
	 * @param sistemaParametroId
	 * @param imagemRelatorio
	 * @throws ErroRepositorioException
	 */
	public void atualizarImagemRelatorio(Integer sistemaParametroId, byte[] imagemRelatorio) throws ErroRepositorioException;

	/**
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterLogradouro() throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * Obt�m o id da Esfera de Poder do Cliente Respons�vel.
	 * 
	 * @author Anderson Italo
	 * @date 22/08/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarIdEsferaPoderClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Relat�rio D�bito por Repons�vel
	 * 
	 * @author Anderson Italo
	 * @date 25/09/2012
	 */
	public Collection<Object[]> pesquisarRelatorioDebitoPorReponsavel(FiltroRelatorioDebitoPorResponsavelHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0XXX] Relat�rio D�bito por Repons�vel
	 * 
	 * @author Anderson Italo
	 * @date 25/09/2012
	 */
	public Integer pesquisarTotalRegistrosRelatorioDebitoPorReponsavel(FiltroRelatorioDebitoPorResponsavelHelper filtro)
					throws ErroRepositorioException;

	public void criarSequence(String nomeSequence) throws ErroRepositorioException;

	public Integer obterNextValSequence(String nomeSequence) throws ErroRepositorioException;

	/**
	 * @param cliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarClienteDebitoACobrar(Cliente cliente) throws ErroRepositorioException;

	/**
	 * @param cliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarClienteGuiaPagamento(Cliente cliente) throws ErroRepositorioException;
}