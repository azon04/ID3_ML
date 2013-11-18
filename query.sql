SELECT DISTINCT lelang.id, nama_agency,pagu,(pagu-hps)*100/pagu AS "Selisih HPS dengan Pagu" ,(hps-harga_penawaran)*100/hps AS "Selisih HPS dengan Penawaran",
(hps-harga_akhir)*100/hps AS harga_penawaran_2, ((hps-harga_penawaran) - (hps-harga_akhir))*100/hps
FROM lelang,peserta_lelang 
WHERE lelang.id = peserta_lelang.id and peserta_lelang.tahapan >= 2  and peserta_lelang.nama != nama_pemenang and peserta_lelang.harga_akhir != 0 and harga_penawaran != 0
ORDER BY lelang.id ASC