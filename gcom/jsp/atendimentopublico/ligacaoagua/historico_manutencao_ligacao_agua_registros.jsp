<table width="100%" border="0">
	<tr>
		<td colspan="4">
			<table width="100%" align="center" bgcolor="#90c7fc" border="0">
				<tr bordercolor="#79bbfd">
					<td colspan="9" align="center" bgcolor="#79bbfd"><strong>Manutenções da Ligação de Água</strong></td>
				</tr>
				<tr bordercolor="#000000">
					<td width="11%" bgcolor="#90c7fc" align="center">
					<div class="style9"><font color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"> <strong>Imóvel</strong> </font></div>
					</td>
					<td width="11%" bgcolor="#90c7fc" align="center">
					<div class="style9"><font color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dt.Emissão</strong> </font></div>
					</td>
					<td width="11%" bgcolor="#90c7fc" align="center">
					<div class="style9"><font color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"> <strong>Doc/OS</strong> </font></div>
					</td>
					<td width="11%" bgcolor="#90c7fc" align="center">
					<div class="style9"><font color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"> <strong>Doc.Cobr.</strong></font></div>
					</td>
					<td width="11%" bgcolor="#90c7fc" align="center">
					<div class="style9"><font color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"> <strong>Sit. Doc.</strong></font>
					</div>
					</td>
					<td width="11%" bgcolor="#90c7fc" align="center"><font
						color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"><strong>Dt.Sit.Doc.</strong>
					</font></td>
					<td width="11%" bgcolor="#90c7fc" align="center"><font
						color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"><strong>Serv.Associado</strong>
					</font></td>
					<td width="11%" bgcolor="#90c7fc" align="center"><font
						color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"><strong>OS</strong>
					</font></td>
					<td width="11%" bgcolor="#90c7fc" align="center"><font
						color="#000000" style="font-size:9px"
						face="Verdana, Arial, Helvetica, sans-serif"><strong>Sit. OS</strong>
					</font></td>
				</tr>
				
			<%--Esquema de paginação--%>
				<pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" 
				          maxPageItems="10" items="${sessionScope.totalRegistros}">
					<pg:param name="pg" />
					<pg:param name="q" />
				
				
				<!--conteúdo da tabela-->
				<%int cont = 0;%>
				<logic:notEmpty name="colecaoHistoricoManutencaoLigacao">
					<logic:iterate name="colecaoHistoricoManutencaoLigacao" id="historicoManutencaoLigacao"
						type="HistoricoManutencaoLigacaoHelper">
					 <pg:item>
					  
						<%cont = cont + 1;
                                 if (cont % 2 == 0) {%>
						<tr bgcolor="#cbe5fe">
							<%} else {%>
						<tr bgcolor="#FFFFFF">
							<%}%>
							<td width="11%" align="left">
								<div class="style9"><font color="#000000"
									style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
									<bean:write name="historicoManutencaoLigacao" property="imovelId" />
								    </font>
								</div>
							</td>
							<td width="11%" align="left">
								<div class="style9"><font color="#000000"
									style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
									<bean:write name="historicoManutencaoLigacao" property="dataEmissao" />
								    </font>
								</div>
							</td>
							<td width="11%" align="left">
								<div class="style9"><font color="#000000"
									style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
									<bean:write name="historicoManutencaoLigacao" property="docOS" />
								    </font>
								</div>
							</td>
							
							<logic:notEmpty name="historicoManutencaoLigacao" property="descricaoDocumentoTipo">												
								<td width="11%" align="left" title="${historicoManutencaoLigacao.descricaoDocumentoTipo}">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<logic:notEmpty name="historicoManutencaoLigacao" property="documentoCobranca">
											<a href="javascript:consultarDocCobranca('<bean:write name="historicoManutencaoLigacao" property="documentoCobranca"/>');">
											    <bean:write name="historicoManutencaoLigacao" property="documentoCobranca" />
											</a>																														
										</logic:notEmpty>
									    </font>
									</div>
								</td>
							</logic:notEmpty>
							<logic:empty name="historicoManutencaoLigacao" property="descricaoDocumentoTipo">
								<td width="11%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<logic:notEmpty name="historicoManutencaoLigacao" property="documentoCobranca">
											<a href="javascript:consultarDocCobranca('<bean:write name="historicoManutencaoLigacao" property="documentoCobranca"/>');">
											    <bean:write name="historicoManutencaoLigacao" property="documentoCobranca" />
											</a>																														
										</logic:notEmpty>
									    </font>
									</div>
								</td>
							</logic:empty>
							
							
							<logic:notEmpty name="historicoManutencaoLigacao" property="descricaoSituacaoAcao">												
								<td width="11%" align="left" title="${historicoManutencaoLigacao.descricaoSituacaoAcao}">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="historicoManutencaoLigacao" property="situacaoDocumento" />
									    </font>
									</div>
								</td>												
							</logic:notEmpty>
							<logic:empty name="historicoManutencaoLigacao" property="descricaoSituacaoAcao">
								<td width="11%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="historicoManutencaoLigacao" property="situacaoDocumento" />
									    </font>
									</div>
								</td>
							</logic:empty>
							<td width="11%" align="left">
								<div class="style9"><font color="#000000"
									style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
									<bean:write name="historicoManutencaoLigacao" property="dataSituacaoDocumento" />
								    </font>
								</div>
							</td>
							<logic:notEmpty name="historicoManutencaoLigacao" property="hintServicoAssociado">												
								<td width="11%" align="left" title="${historicoManutencaoLigacao.hintServicoAssociado}">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<logic:notEmpty name="historicoManutencaoLigacao" property="servicoAssociado">
											<a href="javascript:consultarOs('<bean:write name="historicoManutencaoLigacao" property="servicoAssociado"/>');">
											    <bean:write name="historicoManutencaoLigacao" property="servicoAssociado" />
											</a>																														
										</logic:notEmpty>															
									    </font>
									</div>
								</td>
							</logic:notEmpty>
							<logic:empty name="historicoManutencaoLigacao" property="hintServicoAssociado">
								<td width="11%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<logic:notEmpty name="historicoManutencaoLigacao" property="servicoAssociado">
											<a href="javascript:consultarOs('<bean:write name="historicoManutencaoLigacao" property="servicoAssociado"/>');">
											    <bean:write name="historicoManutencaoLigacao" property="servicoAssociado" />
											</a>																														
										</logic:notEmpty>															
									    </font>
									</div>
								</td>
							</logic:empty>
							<logic:notEmpty name="historicoManutencaoLigacao" property="descricaoServicoTipo">												
								<td width="11%" align="left" title="${historicoManutencaoLigacao.descricaoServicoTipo}">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<logic:notEmpty name="historicoManutencaoLigacao" property="ordemServico">
											<a href="javascript:consultarOs('<bean:write name="historicoManutencaoLigacao" property="ordemServico"/>');">
											    <bean:write name="historicoManutencaoLigacao" property="ordemServico" />
											</a>																														
										</logic:notEmpty>															
									    </font>
									</div>
								</td>
							</logic:notEmpty>
							<logic:empty name="historicoManutencaoLigacao" property="descricaoServicoTipo">
								<td width="11%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<logic:notEmpty name="historicoManutencaoLigacao" property="ordemServico">
											<a href="javascript:consultarOs('<bean:write name="historicoManutencaoLigacao" property="ordemServico"/>');">
											    <bean:write name="historicoManutencaoLigacao" property="ordemServico" />
											</a>																														
										</logic:notEmpty>															
									    </font>
									</div>
								</td>
							</logic:empty>
							<logic:notEmpty name="historicoManutencaoLigacao" property="hintSituacaoOS">												
								<td width="11%" align="left" title="${historicoManutencaoLigacao.hintSituacaoOS}">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="historicoManutencaoLigacao" property="situacaoOS" />
									    </font>
									</div>
								</td>												</logic:notEmpty>
							<logic:empty name="historicoManutencaoLigacao" property="hintSituacaoOS">
								<td width="11%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="historicoManutencaoLigacao" property="situacaoOS" />
									    </font>
									</div>
								</td>
							</logic:empty>
						</tr>
					  </pg:item>									
					</logic:iterate>
				</logic:notEmpty>
			</table>
			</td>
		</tr>
		
           <!-- Ações -->
	<tr>
		<td colspan="2">
		<div align="center"><strong><%@ include file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
		</td>
	</tr>
  </pg:pager>
</table>
  