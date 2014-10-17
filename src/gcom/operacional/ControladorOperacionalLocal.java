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

package gcom.operacional;

import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.micromedicao.hidrometro.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;

import java.util.Collection;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorOperacionalLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * [UC0414] - Informar Programa��o de Abastecimento e Manuten��o
	 * [SB0006] - Atualizar Programa��o de Abastecimento na Base de Dados
	 * [SB0007] - Atualizar Programa��o de Manuten��o na Base de Dados
	 * 
	 * @author Rafael Pinto
	 * @created 09/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarProgramacaoAbastecimentoManutencao(Collection colecaoProgramacaoAbastecimento,
					Collection colecaoProgramacaoAbastecimentoRemovidas, Collection colecaoProgramacaoManutencao,
					Collection colecaoProgramacaoManutencaoRemovidas, Usuario usuario) throws ControladorException;

	/**
	 * Permite inserir um Distrito Operacional
	 * [UC0521] Inserir Distrito Operacional
	 * 
	 * @author Eduardo Bianchi
	 * @date 29/01/2007
	 */
	public Integer inserirDistritoOperacional(String descricao, String descricaoAbreviada, Usuario usuarioLogado, String localidade,
					String descricaoLocalidade, String endereco, String telefone, String ramal, String fax, String email,
					String descricaoTipoInstalacao, String numeroCota, String latitude, String longitude, String sistemaAbastecimento,
					String tipoUnidadeOperacional, LogradouroCep cep, Logradouro logradouro, LogradouroBairro bairro,
					EnderecoReferencia enderecoReferencia, String numeroImovel, String complementoEndereco) throws ControladorException;

	/**
	 * [UC0522] Manter Distrito Operacional
	 * Remover Distrito Operacional
	 * 
	 * @author Eduardo Bianchi
	 * @date 05/02/2007
	 */
	public void removerDistritoOperacional(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC005] Manter Distrito Operacional [SB0001] Atualizar Distrito Operacional
	 * 
	 * @author Eduardo Bianchi
	 * @date 09/02/2007
	 * @pparam distritoOperacinal
	 * @throws ControladorException
	 */
	public void atualizarDistritoOperacional(DistritoOperacional distritoOperacional, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Permite inserir um Sistema de Esgoto
	 * [UC0524] Inserir Sistema de Esgoto
	 * 
	 * @author Kassia Albuquerque
	 * @date 09/03/2007
	 */
	public Integer inserirSistemaEsgoto(SistemaEsgoto sistemaEsgoto, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0525] Manter Sistema Esgoto [SB0001]Atualizar Sistema Esgoto
	 * 
	 * @author Kassia Albuquerque
	 * @date 19/03/2007
	 */

	public void atualizarSistemaEsgoto(SistemaEsgoto sistemaEsgoto, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0525] Manter Sistema Esgoto [SB0002]Remover Sistema Esgoto
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/03/2007
	 */
	public void removerSistemaEsgoto(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0081] Manter Marca Hidrometro
	 * 
	 * @author Bruno Barros
	 * @date 03/07/2007
	 */

	public void removerHidrometroMarca(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0081] Manter Hidrometro Marca
	 * 
	 * @author Bruno Barros
	 * @date 04/07/2007
	 */
	public void atualizarHidrometroMarca(HidrometroMarca hidrometroMarca, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC] Manter Diametro Hidrometro
	 * 
	 * @author Deyverson Santos
	 * @date 19/12/2008
	 */

	public void removerHidrometroDiametro(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC] Manter Hidrometro Diametro
	 * 
	 * @author Deyverson Santos
	 * @date 15/12/2008
	 */

	public void atualizarHidrometroDiametro(HidrometroDiametro hidrometroDiametro, Usuario usuarioLogado) throws ControladorException;
	
	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 *       Insere o Tipo do hidr�metro
	 * @param hidrometroTipo
	 *            Tipo do hidrometro a ser inserido
	 * @return c�digo do Tipo que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroTipo(HidrometroTipo hidrometroTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void removerHidrometroTipo(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void atualizarHidrometroTipo(HidrometroTipo hidrometroTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 *       Insere o Motivo de Baixa do hidr�metro
	 * @param hidrometroMotivoBaixa
	 *            Motivo de Baixa do hidrometro a ser inserido
	 * @return c�digo do Motivo de Baixa que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroMotivoBaixa(HidrometroMotivoBaixa hidrometroMotivoBaixa, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */

	public void removerHidrometroMotivoBaixa(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void atualizarHidrometroMotivoBaixa(HidrometroMotivoBaixa hidrometroMotivoBaixa, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 *       Insere o Classe Metrol�gica do hidr�metro
	 * @param hidrometroClasseMetrologica
	 *            Classe Metrol�gica do hidrometro a ser inserido
	 * @return c�digo do Classe Metrol�gica que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroClasseMetrologica(HidrometroClasseMetrologica hidrometroClasseMetrologica, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */

	public void removerHidrometroClasseMetrologica(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void atualizarHidrometroClasseMetrologica(HidrometroClasseMetrologica hidrometroClasseMetrologica, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 *       Insere o Local de Armazenagem do hidr�metro
	 * @param hidrometroLocalArmazenagem
	 *            Local de Armazenagem do hidrometro a ser inserido
	 * @return c�digo do Local de Armazenagem que foi inserido
	 * @throws ControladorException
	 */
	public Integer inserirHidrometroLocalArmazenagem(HidrometroLocalArmazenagem hidrometroLocalArmazenagem, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */

	public void removerHidrometroLocalArmazenagem(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * @author Victon Santos
	 * @date 03/07/2013
	 */
	public void atualizarHidrometroLocalArmazenagem(HidrometroLocalArmazenagem hidrometroLocalArmazenagem, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Permite inserir um Subsistema de Esgoto
	 * [UCXXXX] Inserir Subsistema de Esgoto
	 * 
	 * @author Ailton Sousa
	 * @date 26/01/2011
	 */
	public int inserirSubsistemaEsgoto(SubsistemaEsgoto subsistemaEsgoto, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0525] Manter Subsistema Esgoto [SB0001]Atualizar Subsistema Esgoto
	 * 
	 * @author Ailton Sousa
	 * @date 28/01/2011
	 */
	public void atualizarSubsistemaEsgoto(SubsistemaEsgoto subsistemaEsgoto, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Atualizar SubBacia
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 31/01/2011
	 */
	public void atualizarSubBacia(SubBacia subBacia, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Atualizar ZonaAbastecimento
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 02/02/2011
	 */
	public void atualizarZonaAbastecimento(ZonaAbastecimento zonaAbastecimento, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UCXXXX] Manter Subsistema Esgoto [SB0002]Remover Subsistema Esgoto
	 * 
	 * @author Ailton Sousa
	 * @date 28/01/2011
	 */
	public void removerSubsistemaEsgoto(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Remover Bacia
	 * 
	 * @author Hebert Falc�o
	 * @date 18/02/2011
	 * @throws ControladorException
	 */
	public void removerBacia(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Remover SubBacia
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 28/01/2011
	 */
	public void removerSubBacia(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Remover ZonaAbastecimento
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 02/02/2011
	 */
	public void removerZonaAbastecimento(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC] - Inserir Localidade Dado Operacional
	 * 
	 * @author isilva
	 * @date 26/01/2011
	 * @param localidadeDadoOperacional
	 * @param usuarioLogado
	 * @return
	 */
	public Integer inserirLocalidadeDadoOperacional(LocalidadeDadoOperacional localidadeDadoOperacional, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Remover Localidade Dado Operacional
	 * 
	 * @author Hebert Falc�o
	 * @date 18/02/2011
	 */
	public void removerLocalidadeDadoOperacional(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * UC0042 - Inserir Bacia
	 * 
	 * @author Hebert Falc�o
	 * @created 27 de Janeiro de 2011
	 */
	public Integer inserirBacia(Bacia bacia, Usuario usuario) throws ControladorException;

	/**
	 * Inserir SubBacia
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @created 31 de Janeiro de 2011
	 */
	public Integer inserirSubBacia(SubBacia subBacia, Usuario usuario) throws ControladorException;

	/**
	 * Inserir ZonaAbastecimento
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @created 02 de Fevereiro de 2011
	 */
	public Integer inserirZonaAbastecimento(ZonaAbastecimento zonaAbastecimento, Usuario usuario) throws ControladorException;

	/**
	 * [UCXXXX] Inserir Sistema Abastecimento
	 * Metodo inser��o do Sistema de Abastecimento
	 * 
	 * @author Anderson Italo
	 * @date 28/01/2011
	 * @param sistemaAbastecimento
	 *            , usuario
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirSistemaAbastecimento(SistemaAbastecimento sistemaAbastecimento, Usuario usuario) throws ControladorException;

	/**
	 * [UCXXXX] Manter Sistema Abastecimento
	 * Metodo de atualiza��o do Sistema de Abastecimento
	 * 
	 * @author Anderson Italo
	 * @date 28/01/2011
	 * @param sistemaAbastecimento
	 *            , usuario
	 * @return void
	 * @throws ControladorException
	 */
	public void atualizarSistemaAbastecimento(SistemaAbastecimento sistemaAbastecimento, Usuario usuario) throws ControladorException;

	/**
	 * [UCXXXX] Manter Sistema Abastecimento
	 * Pesquisa Sistema de abastecimento pelo codigo
	 * 
	 * @author Anderson Italo
	 * @date 28/11/2011
	 * @param codigo
	 * @throws ControladorException
	 */
	public SistemaAbastecimento pesquisarSistemaAbastecimento(Integer codigo) throws ControladorException;

	/**
	 * [UCXXXX] Manter Sistema Abastecimento
	 * Pesquisa Sistema de abastecimento pelo filtro
	 * 
	 * @author Anderson Italo
	 * @date 28/11/2011
	 * @param filtroSistemaAbastecimento
	 * @throws ControladorException
	 */
	public Collection pesquisarSistemaAbastecimentoFiltro(FiltroSistemaAbastecimento filtroSistemaAbastecimento)
					throws ControladorException;

	/**
	 * [UC] - Atualizar Localidade Dado Operacional
	 * 
	 * @author isilva
	 * @date 27/01/2011
	 * @param localidadeDadoOperacional
	 * @param usuarioLogado
	 * @return
	 */
	public void atualizarLocalidadeDadoOperacional(LocalidadeDadoOperacional localidadeDadoOperacional, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC] - Atualizar Bacia
	 * 
	 * @author isilva
	 * @date 03/02/2011
	 * @param bacia
	 * @param usuarioLogado
	 */
	public void atualizarBacia(Bacia bacia, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UCXXXX] Manter Sistema Abastecimento
	 * [SB0002] - Excluir Sistema de Abastecimento
	 * 
	 * @author Anderson Italo
	 * @date 02/02/2011
	 * @param ids
	 *            , usuarioLogado
	 * @throws ControladorException
	 */
	public void removerSistemaAbastecimento(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UCXXXX] Inserir Setor Abastecimento
	 * Metodo inser��o do Setor de Abastecimento
	 * 
	 * @author P�ricles Tavares
	 * @date 07/02/2011
	 * @param setorAbastecimento
	 *            , usuario
	 * @return Object
	 * @throws ControladorException
	 */
	public Object inserirSetorAbastecimento(SetorAbastecimento setorAbastecimento, Usuario usuario) throws ControladorException;

	/**
	 * [UCXXXX] Manter Setor Abastecimento
	 * Metodo atualizar do Setor de Abastecimento
	 * 
	 * @author P�ricles Tavares
	 * @date 07/02/2011
	 * @param setorAbastecimento
	 *            , usuario
	 * @throws ControladorException
	 */
	public void atualizarSetorAbastecimento(SetorAbastecimento setorAbastecimento, Usuario usuario) throws ControladorException;

	/**
	 * [UCXXXX] Manter Setor Abastecimento
	 * Metodo remover do Setor de Abastecimento
	 * 
	 * @author P�ricles Tavares
	 * @date 07/02/2011
	 * @param ids
	 *            , usuario
	 * @throws ControladorException
	 */
	public void removerSetorAbastecimento(String[] ids, Usuario usuarioLogado) throws ControladorException;

}
