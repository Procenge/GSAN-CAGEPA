insert into relatorio (rela_id, RELA_DSRELATORIO, RELA_DSABREVIADA, RELA_ICUSO, RELA_TMULTIMAALTERACAO) VALUES
(176, 'Relatorio Carta Opções de Parcelamento', 'RELCOP', 1, CURRENT_TIMESTAMP);

INSERT INTO PROCESSO (PROC_ID, PROC_DSPROCESSO, PROC_DSABREVIADO, PROC_ICUSO, PROC_TMULTIMAALTERACAO, PRTP_ID) VALUES 
(180, 'GERAR REL CARTA OPÇÃO PARCELAMENTO', 'RCOP', 1, CURRENT_TIMESTAMP , 5);

insert into processo_funcionalidade (prfn_id, proc_id, prfn_tmultimaalteracao, unpr_id, prfn_nnsequencialexecucao, fncd_id) values 
(651, 180, CURRENT_TIMESTAMP,4, 1,593 );

alter table GSAN_ADMIN.sistema_parametros
    add (parm_dsmsgcartaopcaoparcel VARCHAR2(200 BYTE));
    
COMMIT;