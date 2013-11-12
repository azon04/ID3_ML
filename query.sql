SELECT DISTINCT lelang.id, nama_agency,pagu,hps,harga_penawaran,nama_pemenang, nama AS pemenang_2, harga_akhir AS harga_penawaran_2 
FROM lelang,peserta_lelang 
WHERE lelang.id = peserta_lelang.id and peserta_lelang.tahapan >= 2  and peserta_lelang.nama != nama_pemenang and peserta_lelang.harga_akhir != 0
ORDER BY lelang.id ASC