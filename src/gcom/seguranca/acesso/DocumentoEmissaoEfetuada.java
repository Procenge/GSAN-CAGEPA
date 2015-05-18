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

package gcom.seguranca.acesso;

import gcom.arrecadacao.pagamento.FiltroPagamento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.DocumentoTipo;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.io.Serializable;
import java.util.Date;

@ControleAlteracao()
public class DocumentoEmissaoEfetuada
				extends ObjetoTransacao
				implements Serializable {

	public static final int ATRIBUTOS_DOCUMENTO_EMISSAO_INSERIR = 346;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DOCUMENTO_EMISSAO_INSERIR})
	private Integer id;

	/** nullable persistent field */

	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroDocumentoEmissaoEfetuada.IMOVEL, funcionalidade = {ATRIBUTOS_DOCUMENTO_EMISSAO_INSERIR})
    private Imovel imovel;

	@ControleAlteracao(value = FiltroDocumentoEmissaoEfetuada.CLIENTE, funcionalidade = {ATRIBUTOS_DOCUMENTO_EMISSAO_INSERIR})
    private Cliente cliente;

    private Usuario usuario;

	private DocumentoTipo documentoTipo;
	
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DOCUMENTO_EMISSAO_INSERIR})
	private String referenciaInicialDebito;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DOCUMENTO_EMISSAO_INSERIR})
	private String referenciaFinalDebito;


	public Integer getId(){

		return id;
	}


	public void setId(Integer id){

		this.id = id;
	}


	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}


	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}


	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public Cliente getCliente(){

		return cliente;
	}

	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}


	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}



	
	public String getReferenciaInicialDebito(){

		return referenciaInicialDebito;
	}


	public void setReferenciaInicialDebito(String referenciaInicialDebito){

		this.referenciaInicialDebito = referenciaInicialDebito;
	}


	public String getReferenciaFinalDebito(){

		return referenciaFinalDebito;
	}


	public void setReferenciaFinalDebito(String referenciaFinalDebito){

		this.referenciaFinalDebito = referenciaFinalDebito;
	}


	@Override
	public Filtro retornaFiltro(){

		FiltroDocumentoEmissaoEfetuada filtroDocumentoEmissaoEfetuada = new FiltroDocumentoEmissaoEfetuada();

		filtroDocumentoEmissaoEfetuada.adicionarCaminhoParaCarregamentoEntidade("imovel");
		// filtroDocumentoEmissaoEfetuada.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroDocumentoEmissaoEfetuada.adicionarCaminhoParaCarregamentoEntidade("usuario");
		filtroDocumentoEmissaoEfetuada.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
		filtroDocumentoEmissaoEfetuada.adicionarParametro(new ParametroSimples(FiltroPagamento.ID, this.getId()));
		return filtroDocumentoEmissaoEfetuada;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}


}
