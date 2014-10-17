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

package gcom.interceptor;

import gcom.seguranca.acesso.Argumento;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

/**
 * @author Thiago Toscano
 * @date 29/04/2006
 */
public class RegistradorOperacao {

	private static ThreadLocal<RegistradorOperacao> threadLocal = new ThreadLocal<RegistradorOperacao>();

	public static RegistradorOperacao get(){

		return threadLocal.get();
	}

	public static void set(RegistradorOperacao registrador){

		threadLocal.set(registrador);
	}

	/** operacao realizada pelo usuario */
	private OperacaoEfetuada operacaoEfetuada = null;

	private UsuarioAcaoUsuarioHelper[] usuarioAcaoUsuarioHelper;

	/**
	 * que recebe o id da op
	 * Construtor de RegistradorOperacao
	 * 
	 * @param idOperacao
	 * @param usuarioAcaoUsuarioHelper
	 */
	public RegistradorOperacao(Integer idOperacao, UsuarioAcaoUsuarioHelper... usuarioAcaoUsuarioHelper) {

		Operacao operacao = new Operacao();
		operacao.setId(idOperacao);

		operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		this.usuarioAcaoUsuarioHelper = usuarioAcaoUsuarioHelper;
	}

	/**
	 * que recebe o id da op
	 * Construtor de RegistradorOperacao
	 * 
	 * @param idOperacao
	 * @param valorArgumento
	 * @param usuarioAcaoUsuarioHelper
	 */
	public RegistradorOperacao(Integer idOperacao, Integer valorArgumento, UsuarioAcaoUsuarioHelper... usuarioAcaoUsuarioHelper) {

		Operacao operacao = new Operacao();
		operacao.setId(idOperacao);

		operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setArgumentoValor(valorArgumento);

		this.usuarioAcaoUsuarioHelper = usuarioAcaoUsuarioHelper;
	}

	public RegistradorOperacao(Integer idOperacao, Integer valorArgumento, Integer idObjetoPrincipal,
								UsuarioAcaoUsuarioHelper... usuarioAcaoUsuarioHelper) {

		Operacao operacao = new Operacao();
		operacao.setId(idOperacao);

		operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setArgumentoValor(valorArgumento);
		operacaoEfetuada.setIdObjetoPrincipal(idObjetoPrincipal);
		this.usuarioAcaoUsuarioHelper = usuarioAcaoUsuarioHelper;

		if(get() != null && get().operacaoEfetuada.getOperacao().getId().intValue() == operacao.getId().intValue()){

			get().operacaoEfetuada.setArgumentoValor(valorArgumento);
			get().operacaoEfetuada.setIdObjetoPrincipal(idObjetoPrincipal);
		}
	}

	/**
	 * @author Felipe Rosacruz
	 *         Construtor de RegistradorOperacao usado para casos em que a funcionalidade
	 *         pode estar associada a mais de um tipo de argumento diferente.
	 *         Ex.: Na funcionalidade de manter guia de pagamento a guia pode estar associada a um
	 *         imovel ou a um cliente.
	 * @param idOperacao
	 * @param valorArgumento
	 * @param argumento
	 * @param idObjetoPrincipal
	 * @param usuarioAcaoUsuarioHelper
	 * @date 10/06/2013
	 */
	public RegistradorOperacao(Integer idOperacao, Integer valorArgumento, Argumento argumento, Integer idObjetoPrincipal,
								UsuarioAcaoUsuarioHelper... usuarioAcaoUsuarioHelper) {

		Operacao operacao = new Operacao();
		operacao.setId(idOperacao);

		operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setArgumentoValor(valorArgumento);
		operacaoEfetuada.setIdObjetoPrincipal(idObjetoPrincipal);
		operacaoEfetuada.setArgumento(argumento);
		this.usuarioAcaoUsuarioHelper = usuarioAcaoUsuarioHelper;

		if(get() != null && get().operacaoEfetuada.getOperacao().getId().intValue() == operacao.getId().intValue()){

			get().operacaoEfetuada.setArgumentoValor(valorArgumento);
			get().operacaoEfetuada.setIdObjetoPrincipal(idObjetoPrincipal);
		}
	}

	/**
	 * Método que registra os parametros a cada objeto a ser registrado
	 * 
	 * @author Administrador
	 * @date 29/04/2006
	 * @param objetoTransacao
	 */
	public void registrarOperacao(ObjetoTransacao objetoTransacao){

		if(objetoTransacao.getOperacaoEfetuada() != null){

			this.operacaoEfetuada.setArgumentoValor(objetoTransacao.getOperacaoEfetuada().getArgumentoValor());

			this.operacaoEfetuada.setArgumento(objetoTransacao.getOperacaoEfetuada().getArgumento());
		}

		// seta a operacao efetuada
		objetoTransacao.setOperacaoEfetuada(this.operacaoEfetuada);

		// pra cada usuario com sua acao seta no objeto que deseja registra a operacao
		if(usuarioAcaoUsuarioHelper != null){
			for(int i = 0; i < usuarioAcaoUsuarioHelper.length; i++){
				UsuarioAcaoUsuarioHelper uAUH = usuarioAcaoUsuarioHelper[i];
				if(uAUH.getUsuario() != null){
					objetoTransacao.adicionarUsuario(uAUH.getUsuario(), uAUH.getUsuarioAcao());
				}
			}
		}
	}
}