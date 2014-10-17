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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.faturamento.histograma;

import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * @author mgrb
 *
 */
public class RepositorioHistogramaEsgotoEconomiaHelperFactory {

	/** 
	 * M�todo getGerador
	 * <p>Esse m�todo implementa a fabrica de gerador de SQL para Histograma Esgoto Economia</p> 
	 * RASTREIO: [OC1073038][UC0606]
	 * @param filtro
	 * @return
	 * @author Marlos Ribeiro 
	 * @since 04/06/2013
	 */
	public static RepositorioHistogramaEsgotoEconomiaHelper getGerador(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro){

		RepositorioHistogramaEsgotoEconomiaHelper repositorioImpl = null;
		try{
			Constructor construtor = OpcaoTotalizacaoEnum.get(filtro.getOpcaoTotalizacao()).getRepositorioHelperImplClass().getConstructor();
			repositorioImpl = (RepositorioHistogramaEsgotoEconomiaHelper) construtor.newInstance();
			repositorioImpl.setFiltro(filtro);
		}catch(SecurityException e){
			throw new RuntimeException(e);
		}catch(NoSuchMethodException e){
			throw new RuntimeException(e);
		}catch(IllegalArgumentException e){
			throw new RuntimeException(e);
		}catch(InstantiationException e){
			throw new RuntimeException(e);
		}catch(IllegalAccessException e){
			throw new RuntimeException(e);
		}catch(InvocationTargetException e){
			throw new RuntimeException(e);
		}

		return repositorioImpl;
	}
}
