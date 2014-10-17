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
package gcom.cobranca.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 * Classe helper que foi criada para as 4 listas ordenadas de cobran�a de documentos.
 */
public class CobrancaDocumentoColecoesOrdenadasHelper {

	/**
	 * Primeira lista do arquivo.
	 */
	private List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteResponsavel;

	/**
	 * Segunda lista do arquivo.
	 */
	private List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteUsuario;

	/**
	 * Terceira lista do arquivo.
	 */
	private List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteProprietario;

	/**
	 * Quarta lista do arquivo.
	 */
	private List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaImovel;

	/**
	 * Construtor.
	 * 
	 * @param documentosCobrancaEntregaClienteResponsavel
	 * @param documentosCobrancaEntregaClienteUsuario
	 * @param documentosCobrancaEntregaClienteProprietario
	 * @param documentosCobrancaEntregaImovel
	 */
	public CobrancaDocumentoColecoesOrdenadasHelper(List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteResponsavel,
													List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteUsuario,
													List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteProprietario,
													List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaImovel) {

		super();

		ordenarLista(documentosCobrancaEntregaClienteResponsavel, true);
		ordenarLista(documentosCobrancaEntregaClienteUsuario, true);
		ordenarLista(documentosCobrancaEntregaClienteProprietario, true);
		ordenarLista(documentosCobrancaEntregaImovel, false);

		this.documentosCobrancaEntregaClienteResponsavel = documentosCobrancaEntregaClienteResponsavel;
		this.documentosCobrancaEntregaClienteUsuario = documentosCobrancaEntregaClienteUsuario;
		this.documentosCobrancaEntregaClienteProprietario = documentosCobrancaEntregaClienteProprietario;
		this.documentosCobrancaEntregaImovel = documentosCobrancaEntregaImovel;
	}

	/**
	 * M�todo que ordena a lista passado por par�metro pelos campos do helper.
	 * 
	 * @param lista lista a ser ordenada
	 * @param ordenarPorCliente condi��o se ordena por cliente.
	 */
	protected void ordenarLista(List<CobrancaDocumentoAvisoCorteHelper> lista, boolean ordenarPorCliente){

		if(lista != null){
			// Ordenar a cole��o por mais de um campo
			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("idEmpresa"));
			if(ordenarPorCliente){
				sortFields.add(new BeanComparator("idClienteGenerico"));
			}
			sortFields.add(new BeanComparator("idLocalidade"));
			sortFields.add(new BeanComparator("idSetor"));
			sortFields.add(new BeanComparator("numeroQuadra"));
			sortFields.add(new BeanComparator("numeroLote"));
			sortFields.add(new BeanComparator("numeroSubLote"));
			sortFields.add(new BeanComparator("enderecoGenerico"));

			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort(lista, multiSort);
		}
	}

	/**
	 * @return the documentosCobrancaEntregaClienteResponsavel
	 */
	public List<CobrancaDocumentoAvisoCorteHelper> getDocumentosCobrancaEntregaClienteResponsavel(){
		return documentosCobrancaEntregaClienteResponsavel;
	}

	/**
	 * @param documentosCobrancaEntregaClienteResponsavel the documentosCobrancaEntregaClienteResponsavel to set
	 */
	public void setDocumentosCobrancaEntregaClienteResponsavel(
					List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteResponsavel){
		this.documentosCobrancaEntregaClienteResponsavel = documentosCobrancaEntregaClienteResponsavel;
	}

	/**
	 * @return the documentosCobrancaEntregaClienteUsuario
	 */
	public List<CobrancaDocumentoAvisoCorteHelper> getDocumentosCobrancaEntregaClienteUsuario(){
		return documentosCobrancaEntregaClienteUsuario;
	}

	/**
	 * @param documentosCobrancaEntregaClienteUsuario the documentosCobrancaEntregaClienteUsuario to set
	 */
	public void setDocumentosCobrancaEntregaClienteUsuario(List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteUsuario){
		this.documentosCobrancaEntregaClienteUsuario = documentosCobrancaEntregaClienteUsuario;
	}

	/**
	 * @return the documentosCobrancaEntregaClienteProprietario
	 */
	public List<CobrancaDocumentoAvisoCorteHelper> getDocumentosCobrancaEntregaClienteProprietario(){
		return documentosCobrancaEntregaClienteProprietario;
	}

	/**
	 * @param documentosCobrancaEntregaClienteProprietario the documentosCobrancaEntregaClienteProprietario to set
	 */
	public void setDocumentosCobrancaEntregaClienteProprietario(
					List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaClienteProprietario){
		this.documentosCobrancaEntregaClienteProprietario = documentosCobrancaEntregaClienteProprietario;
	}

	/**
	 * @return the documentosCobrancaEntregaImovel
	 */
	public List<CobrancaDocumentoAvisoCorteHelper> getDocumentosCobrancaEntregaImovel(){
		return documentosCobrancaEntregaImovel;
	}

	/**
	 * @param documentosCobrancaEntregaImovel the documentosCobrancaEntregaImovel to set
	 */
	public void setDocumentosCobrancaEntregaImovel(List<CobrancaDocumentoAvisoCorteHelper> documentosCobrancaEntregaImovel){
		this.documentosCobrancaEntregaImovel = documentosCobrancaEntregaImovel;
	}

}
