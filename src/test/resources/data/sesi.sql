delete from peserta_pelatihan;
delete from sesi;

insert into sesi(id,id_materi,mulai,sampai)
values('ad','aaa','2015-05-04','2015-08-04');

insert into sesi(id,id_materi,mulai,sampai)
values('ae','aab','2015-03-04','2015-06-04');

insert into sesi(id,id_materi,mulai,sampai)
values('af','aac','2015-02-04','2015-05-04');

insert into peserta_pelatihan(id_sesi,id_peserta)
values('ad','aa');
insert into peserta_pelatihan(id_sesi,id_peserta)
values('ad','ab');
insert into peserta_pelatihan(id_sesi,id_peserta)
values('ad','ac');

insert into peserta_pelatihan(id_sesi,id_peserta)
values('ae','ab');
insert into peserta_pelatihan(id_sesi,id_peserta)
values('ae','aa');

insert into peserta_pelatihan(id_sesi,id_peserta)
values('af','aa');
