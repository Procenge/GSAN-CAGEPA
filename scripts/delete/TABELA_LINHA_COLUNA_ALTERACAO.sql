-- Isaac Silva - Cliente_fone
delete from TABELA_LINHA_COLUNA_ALTERACAO where tbco_id in (select tbco_id from tabela where tabe_id = 665);

-- Isaac Silva - Cliente Endereco
delete from TABELA_LINHA_COLUNA_ALTERACAO where tbco_id in (select tbco_id from tabela where tabe_id = 666);

-- Isaac Silva
DELETE FROM TABELA_LINHA_COLUNA_ALTERACAO TLCA WHERE TLCA.TBCO_ID IN (46125, 46126, 46127, 46128, 46129, 46130, 46131, 46132, 46133, 46117, 46118, 46119, 46120, 46121, 46122, 46123, 46124);

-- Isaac Silva
-- imovel
delete from TABELA_LINHA_COLUNA_ALTERACAO where tbco_id in (select tbco_id from tabela where tabe_id = 223);

-- Localidade
delete from TABELA_LINHA_COLUNA_ALTERACAO where TBCO_ID in (46229, 46232, 46225, 46233, 46228, 46226, 46227, 46224, 46222, 46223, 46231);

-- HIDROMETRO
delete from TABELA_LINHA_COLUNA_ALTERACAO where TBCO_ID in (46061, 46062, 46063, 46064,46065,46066,46067,46068,46069,46070,46071,46072,46073,46074,46075,46076);