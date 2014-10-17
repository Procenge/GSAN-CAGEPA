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

package gcom.batch.cadastro;

import gcom.cadastro.localidade.SetorComercial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.ConstantesSistema;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;
import java.util.Iterator;

/**
 * Tarefa que manda para batch Gerar Arquivo Agencia Virtual WEB
 * 
 * @author Josenildo Neves
 * @created 19/03/2012
 */
public class TarefaBatchGerarArquivoAgenciaVirtualDebitos
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchGerarArquivoAgenciaVirtualDebitos(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchGerarArquivoAgenciaVirtualDebitos() {

		super(null, 0);
	}

	public Object executar() throws TarefaException{

		Collection colecaoIdsSetorComercialParaExecucao = (Collection) getParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH);
		Iterator iterator = colecaoIdsSetorComercialParaExecucao.iterator();
		while(iterator.hasNext()){
			Integer idSetorComercial = ((SetorComercial) iterator.next()).getId();
			System.out.println("SETOR COMERCAIL GERAR ARQUIVO AGENCIA VIRTUAL DÉBITOS: " + (idSetorComercial)
							+ "*********************************************************");
			enviarMensagemControladorBatch(ConstantesJNDI.BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS_MDB,
							new Object[] {idSetorComercial, this.getIdFuncionalidadeIniciada()});
		}

		// Para testes

		/*
		 * for(Integer idSetorComercial : new Integer[] {68, 76, 75, 74, 73, 72, 71, 70, 69, 51, 49,
		 * 35, 33, 32, 4, 3, 2, 557, 555, 554, 553, 525, 524, 523, 522, 521, 520, 519, 518, 517,
		 * 507, 506, 468, 467, 466, 465, 464, 463, 462, 461, 460, 459, 457, 451, 191, 190, 182, 181,
		 * 180, 178, 177, 174, 172, 171, 170, 169, 166, 165, 164, 163, 161, 156, 147, 146, 145, 139,
		 * 138, 137, 136, 135, 134, 133, 132, 131, 130, 129, 128, 127, 126, 125, 124, 123, 122, 103,
		 * 101, 100, 99, 92, 91, 90, 89, 88, 87, 85, 84, 83, 81, 82, 80, 79, 77, 50, 47, 46, 45, 44,
		 * 43, 42, 41, 40, 39, 38, 37, 36, 34, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 14, 13, 12,
		 * 11, 533, 532, 531, 530, 529, 509, 508, 505, 504, 503, 502, 501, 500, 499, 495, 471, 470,
		 * 469, 456, 453, 450, 188, 187, 186, 185, 184, 183, 179, 176, 175, 173, 168, 167, 162, 160,
		 * 159, 158, 157, 155, 154, 153, 152, 151, 150, 149, 148, 144, 143, 142, 60, 141, 140, 121,
		 * 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 102,
		 * 98, 97, 96, 95, 94, 93, 86, 67, 66, 65, 64, 63, 62, 61, 59, 58, 57, 56, 55, 54, 53, 52,
		 * 48, 31, 30, 29, 28, 17, 16, 15, 10, 9, 8, 7, 6, 5, 1, 560, 559, 558, 556, 552, 551, 550,
		 * 549, 548, 547, 546, 545, 544, 543, 542, 541, 540, 539, 538, 537, 536, 535, 534, 528, 527,
		 * 526, 516, 515, 514, 513, 512, 511, 510, 498, 497, 496, 494, 493, 492, 491, 490, 489, 488,
		 * 487, 486, 485, 484, 483, 482, 481, 480, 479, 478, 477, 476, 475, 474, 473, 472, 458, 455,
		 * 454, 452, 449}){
		 * System.out.println("SETOR COMERCIAL GERAR ARQUIVO AGENCIA VIRTUAL IMOVEIS: " +
		 * (idSetorComercial)
		 * + "*********************************************************");
		 * enviarMensagemControladorBatch(ConstantesJNDI.BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS_MDB
		 * ,
		 * new Object[] {idSetorComercial, this.getIdFuncionalidadeIniciada()});
		 * }
		 */

		return null;
	}

	@Override
	public Collection pesquisarTodasUnidadeProcessamentoBatch(){

		return null;
	}

	@Override
	public Collection pesquisarTodasUnidadeProcessamentoReinicioBatch(){

		return null;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("GerarArquivoAgenciaVirtualDebitosBatch", this);
	}

}
