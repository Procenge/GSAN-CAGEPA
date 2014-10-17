-- Anderson Italo
DELETE FROM TABELA_COLUNA WHERE TABE_ID=437 
AND TBCO_ID IN (45067,45068,45069,45070,45071);
DELETE FROM TABELA_COLUNA where TBCO_ID >= 43212 and TBCO_ID <= 43222;
DELETE FROM TABELA_COLUNA where TBCO_ID >= 43658 and TBCO_ID <= 43668;

-- Isaac Silva
DELETE FROM TABELA_COLUNA WHERE TABE_ID=33 AND TBCO_ID >= 36191 and TBCO_ID <= 36215;

-- Isaac Silva -- cliente_fone
delete from tabela_coluna where tabe_id = 665;
delete from tabela_coluna tc where tc.tabe_id = 37 and tc.tbco_id in (41019, 41020, 41021, 41022, 41023, 41024, 41025, 41026);

-- Cliente Endereco
delete from tabela_coluna tc where tc.tabe_id = 666 and tc.tbco_id in (46145,46139,46140,46141,46142,46143,46144);

-- Isaac Silva
delete from tabela_coluna tc where tc.tabe_id = 664 and tc.tbco_id in (46125, 46126, 46127, 46128, 46129, 46130, 46131, 46132, 46133, 46117, 46118, 46119, 46120, 46121, 46122, 46123, 46124);

-- Ailton Sousa
delete from tabela_coluna tc where tc.tabe_id = 436 and
tc.tbco_id in (45061, 45065, 45062, 45064, 45060, 45063, 45066);

-- Isaac Silva
-- imovel
delete from tabela_coluna tc where tabe_id = 223;

DELETE FROM TABELA_COLUNA TC WHERE TC.TABE_ID = 503 AND tc.tbco_id in (46146, 46147, 46148, 45937, 45827, 45828, 45839, 45651);

-- Isaac Silva - Localidade
DELETE FROM TABELA_COLUNA TC WHERE TC.TABE_ID = 265 AND TC.TBCO_ID IN (46229, 46232, 46225, 46233, 46228, 46226, 46227, 46224, 46222, 46223, 46231);

-- Isaac Silva - HIDROMETRO
delete from TABELA_COLUNA TC where TC.TABE_ID = 652 and TC.TBCO_ID in (46061, 46062, 46063, 46064,46065,46066,46067,46068,46069,46070,46071,46072,46073,46074,46075,46076);