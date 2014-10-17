Insert into processo (proc_id, proc_dsprocesso, proc_dsabreviado, proc_icuso, proc_tmultimaalteracao, prtp_id) values (179,'GERAR RELATORIO ANALISE CONSUMO','GERRAC',1, CURRENT_TIMESTAMP,5);
commit;
Insert into processo_funcionalidade (prfn_id,proc_id,prfn_tmultimaalteracao,unpr_id,prfn_nnsequencialexecucao,fncd_id) values (648,179,current_timestamp,4,1,290);
commit;