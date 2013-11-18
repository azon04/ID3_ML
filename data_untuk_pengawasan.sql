SELECT DISTINCT lelang.id, (hps-harga_penawaran)*100/hps AS "Selisih HPS dengan Penawaran",
hps,harga_akhir,(hps-harga_akhir)*100/hps AS "Selisih Pemenang ke dua", ((hps-harga_penawaran) - (hps-harga_akhir))*100/hps AS "Perbedaan Pemenang dengan calon pemenang"
FROM lelang,peserta_lelang 
WHERE lelang.id = peserta_lelang.id and lelang.id = 6647011 and peserta_lelang.tahapan >= 2  and peserta_lelang.nama != nama_pemenang and peserta_lelang.harga_akhir != 0 and harga_penawaran != 0
ORDER BY lelang.id ASC