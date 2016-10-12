
package com.gtm.acc.pelatihan.dao;

import com.gtm.acc.pelatihan.PelatihanApplication;
import com.gtm.acc.pelatihan.entity.Peserta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

 @RunWith(SpringJUnit4ClassRunner.class)
 @SpringApplicationConfiguration(classes = PelatihanApplication.class)
 @Sql(
         executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
         scripts = "/data/peserta.sql"
                 )
public class PersetaDaoTest {
   @Autowired
    private PesertaDao pesertaDao;
   @Autowired
   private DataSource ds;
   
 
   
   /* @After
    public void hapusdata(){        
        pesertaDao.deleteAll();
    }*/
    
   
   
    @Test
    public  void testInsert() throws SQLException{
        Peserta p=new Peserta();
        p.setNama("Perseta 1");
        p.setEmail("Peserta1@gmail.com");
        p.setTanggallahir(new Date());
        pesertaDao.save(p);
        
        
        
        
        String sql="select count(*) as jumlah from peserta where email='Peserta1@gmail.com'";
        
       try (Connection c = ds.getConnection()) {
           ResultSet rs=c.createStatement().executeQuery(sql);
           Assert.assertTrue(rs.next());
           Long jumlahRow=rs.getLong("jumlah");
           Assert.assertEquals(1L, jumlahRow.longValue());
       }
    }
    
    @Test
    public void testHitung(){
        Long jumlah=pesertaDao.count();
        Assert.assertEquals(3L, jumlah.longValue());
    }
    
    @Test
    public void testCariById(){
        Peserta p=pesertaDao.findOne("aa");
        Assert.assertNotNull(p);
        Assert.assertEquals("peserta1", p.getNama());
        
    }
    
  
    
}
