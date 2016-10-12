/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtm.acc.pelatihan.dao;

import com.gtm.acc.pelatihan.entity.Materi;
import com.gtm.acc.pelatihan.entity.Sesi;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface SesiDao extends PagingAndSortingRepository<Sesi, String>{
    //cari sesi untuk materi tertentu
    public Page<Sesi> findByMateri(Materi m,Pageable page);
    
    @Query("SELECT x from Sesi x where x.mulai >= :m "
                + "and x.mulai < :s "
                + "and x.materi.kode = :k "
                + "order by x.mulai desc")
    public Page<Sesi> cariberdasarkanTanggalMulai(
            @Param("m")Date mulai,
            @Param("s") Date sampai,
            @Param("k")String kode,
            Pageable page);
    
}
