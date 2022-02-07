package com.topicos.jena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.topicos.jena.dto.Message;
import com.topicos.jena.service.AnimalService;


@RestController
@RequestMapping("/animal")
@CrossOrigin(origins = "http://localhost:4200")
public class AnimalController {
    @Autowired
    AnimalService animalService;
    
    
    @GetMapping("/init")
    public ResponseEntity<?> init(){
    	try {
			String message = animalService.init();
			return new ResponseEntity(new Message(message), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Message("No se pudo iniciar la ontolgía: "+ e.getMessage()), HttpStatus.FAILED_DEPENDENCY);
		}
    }
    
    @GetMapping("/getdata")
    public ResponseEntity<?> getData(){
    	try {
			return new ResponseEntity(animalService.getData(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Message("No se pudo iniciar la ontolgía: "+ e.getMessage()), HttpStatus.FAILED_DEPENDENCY);
		}
    }
    
    @PostMapping("/setdata/{clase}/{propiedad}/{valor}")
    public ResponseEntity<?> setdata(@PathVariable String clase, @PathVariable String propiedad, @PathVariable String valor){
    	try {
			return new ResponseEntity(animalService.setData(clase, propiedad, valor), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Message("No se pudo iniciar la ontolgía: "+ e.getMessage()), HttpStatus.FAILED_DEPENDENCY);
		}
    }
    
    @GetMapping("/chargedatainit")
    public ResponseEntity<?> chargeDataInit(){
    	try {
    		animalService.chargeDataInit();
			return new ResponseEntity("Ontología cargada", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Message("No se pudo iniciar la ontolgía: "+ e.getMessage()), HttpStatus.FAILED_DEPENDENCY);
		}
    }
}
