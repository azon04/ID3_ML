SELECT id,(pagu-hps)*100/pagu AS "% Selisih pagu-hps", (hps-harga_penawaran)*100/hps AS"% Selisih pagu dan harga penawaran"
FROM lelang WHERE hps != 0