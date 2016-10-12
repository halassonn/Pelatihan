
package com.gtm.acc.pelatihan.sesiTest;

import com.gtm.acc.pelatihan.dao.*;
import com.gtm.acc.pelatihan.PelatihanApplication;
import com.gtm.acc.pelatihan.entity.Materi;
import com.gtm.acc.pelatihan.entity.Peserta;
import com.gtm.acc.pelatihan.entity.Sesi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sql.DataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

 @RunWith(SpringJUnit4ClassRunner.class)
 @SpringApplicationConfiguration(classes = PelatihanApplication.class)
 @Sql(
         executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
         scripts = {"/data/peserta.sql","/data/materi.sql","/data/sesi.sql"}
                 )
public class SesiDaoTest {
   @Autowired
    private SesiDao sd;
   @Autowired
   private DataSource ds;
   
   @Test
   public void testcariByMateri(){
       Materi m=new Materi();
       PageRequest page=new PageRequest(0, 5);
       m.setId("aaa");
       Page<Sesi> hasilQuery=sd.findByMateri(m,page);
       Assert.assertEquals(1L, hasilQuery.getTotalElements());
       
       Sesi s=hasilQuery.getContent().get(0);
       Assert.assertNotNull(s);
       Assert.assertEquals("Java fundemental", s.getMateri().getNama());
       
   }
   @Test
   public void testCariberdasarkanTanggalMulai() throws Exception{
        PageRequest page=new PageRequest(0, 5);
        
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
        Date sejak=formater.parse("2015-05-04");
        Date sampai=formater.parse("2015-08-04");
        
        
       Page<Sesi> hasil=sd.cariberdasarkanTanggalMulai(
               sejak, sampai, "Jf-001", page);
       
       Assert.assertEquals(1L, hasil.getTotalElements());
       Assert.assertFalse(hasil.getContent().isEmpty());
       
       Sesi s= hasil.getContent().get(0);
       Assert.assertEquals("Java fundemental", s.getMateri().getNama());
   }
           
@Test
public void testSaveSesi() throws ParseException{
    Peserta p1=new Peserta();
    p1.setId("ab");
    
    Peserta p2=new Peserta();
    p2.setId("ac");
    
    Materi m=new Materi();
    m.setId("aaa");
    
    SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
        Date sejak=formater.parse("2015-01-04");
        Date sampai=formater.parse("2015-01-04");
    
        Sesi s=new Sesi();
        s.setMateri(m);
        s.setMulai(sejak);
        s.setSampai(sampai);
        s.getDaftarpeserta().add(p1);
        s.getDaftarpeserta().add(p2);
        sd.save(s);
        String idSesiBaru=s.getId();
        Assert.assertNotNull(idSesiBaru);
        System.out.println("Id Baru :" +s.getId());
        
        String sql="select count(*) from sesi where id_materi='aaa'";
        String sqlManyToMany="select count(*) from peserta_pelatihan where id_sesi = ?";
        
         try {
        Connection c=ds.getConnection();
        //cek tabel sesi
            ResultSet rs= c.createStatement().executeQuery(sql);
            Assert.assertNotNull(rs.next());
            Assert.assertEquals(3L, rs.getLong(3));
            
            //cek tabel relasi many to many
            PreparedStatement ps=c.prepareStatement(sqlManyToMany);
            ps.setString(1, idSesiBaru);
            ResultSet rs2= ps.executeQuery();
            Assert.assertNotNull(rs2.next());
            Assert.assertEquals(2L, rs2.getLong(1));
            
    } catch (Exception e) {
    }
       
}

   
 


  
    
}
