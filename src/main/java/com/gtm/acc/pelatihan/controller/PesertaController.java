
package com.gtm.acc.pelatihan.controller;

import com.gtm.acc.pelatihan.dao.PesertaDao;
import com.gtm.acc.pelatihan.entity.Peserta;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PesertaController {
    @Autowired
    private PesertaDao pd;
    
   
    @RequestMapping(value="/peserta",method = RequestMethod.GET)
    public Page<Peserta>cariPeserta(Pageable page){
        return pd.findAll(page);
    }
    
    //REST SIMPAN
    @RequestMapping(value="/peserta",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertPeserta(@RequestBody @Valid Peserta p){
        pd.save(p);
    }
    
    //REST UPDATE
     @RequestMapping(value="/peserta/{id}",method = RequestMethod.PUT)
     @ResponseStatus(HttpStatus.OK)
    public void updatePeserta(@PathVariable("id") String id,@RequestBody @Valid Peserta p){
        p.setId(id);
        pd.save(p);
    }
    
    //REST CARI BY ID
    @RequestMapping(value = "/peserta/{id}",method = RequestMethod.GET)
     @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Peserta> cariPesertaById(@PathVariable("id") String id){
        Peserta hasil=pd.findOne(id);
        if(hasil==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hasil,HttpStatus.OK);
    }
    
    //REST DELETE
     @RequestMapping(value="/peserta/{id}",method = RequestMethod.DELETE)
     @ResponseStatus(HttpStatus.OK)
    public void hapusPeserta(@PathVariable("id") String id){        
        pd.delete(id);
    }
    
    
    
}
