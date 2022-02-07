package com.topicos.jena.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnimalService {
	OntModel model;
	//Set the default NameSpace for our 
	String NS = "animales";;
	
    private String start(){
    	//Create own model
    	model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    	//Set the default NameSpace for our
    	model.setNsPrefix(NS, "http://www.semanticweb.org/david/ontologies/2022/0/animalesJena");
    	return "Ontolgía creada";
    }
    
    public String init() throws IOException{
    	start();
    	//Classes. They must be created with the NameSpace defined.
    	OntClass animales = model.createClass(NS+":"+"Animales");
    	OntClass vertebrados = model.createClass(NS+":"+"Vertebrados");
    	OntClass invertebrados = model.createClass(NS+":"+"Invertebrados");
    	vertebrados.setDisjointWith(invertebrados);
    	animales.addSubClass(vertebrados);
    	animales.addSubClass(invertebrados);
    	//Create the weight property
    	DatatypeProperty peso = model.createDatatypeProperty(NS+":"+"Peso");
    	peso.addDomain(animales);//Class to which it belongs
    	peso.addRange(XSD.xint);//Type of property
    	peso.convertToFunctionalProperty();//So that it only accepts a value.
    	//Create the instances and give value to the property ç2Peso"
    	Individual leon = model.createIndividual(NS+":"+"Leon",vertebrados);
    	leon.setPropertyValue(peso, model.createTypedLiteral(250));
    	Individual leopardo =
    	model.createIndividual(NS+":"+"Leopardo",vertebrados);
    	leopardo.setPropertyValue(peso, model.createTypedLiteral(200));
    	Individual pulpo = model.createIndividual(NS+":"+"Pulpo",invertebrados);
    	pulpo.setPropertyValue(peso, model.createTypedLiteral(10));
    	Individual sepia = model.createIndividual(NS+":"+"Sepia",invertebrados);
    	sepia.setPropertyValue(peso, model.createTypedLiteral(1));
    	//Store the ontology in an OWL file (Optional)
    	File file = new File("C://Users//david//Documents//Jena//AnimalesJena.owl");
    	//Hay que capturar las Excepciones
    	if (file.exists()){
    		file.delete();
    	}
    	file.createNewFile();
    	model.write(new PrintWriter(file));
    	return "Ontolgía iniciada";
    }
    
    public String getData() {
    	//List<Individual> individuals;
    	List<OntClass> classes = new ArrayList<OntClass>();
    	//Recorremos la ontologia
    	String nombres = "";
    	for (Iterator<OntClass> i = model.listClasses();i.hasNext();){
    		OntClass cls = i.next();
    		//classes.add(cls);
    		System.out.print(cls.getLocalName()+": ");
    		nombres+= cls.getLocalName()+": ";
    		for(Iterator it = cls.listInstances(true);it.hasNext();){
    			Individual ind = (Individual)it.next();
    			if(ind.isIndividual()){
    				System.out.print(ind.getLocalName()+" ");
    				nombres+= ind.getLocalName()+" ";
    			}
    		}
    		nombres+= "\n";
    		System.out.println();
    	}
    	return nombres;
    }
    
    /**
     * Generic version of the Box class.
     * @param <T> the type of the value being boxed
     */
    public <T> String  setData(String clase, String propiedad, T Valor) {
    	//Obtenemos la instancia y la propiedad y le damos un nuevo valor
    	model.getIndividual(NS+":"+clase)
    	 .setPropertyValue(model.getProperty(NS+":"+propiedad),
    	model.createTypedLiteral(Valor));
    	return "Actualizado";
    }
    
    public void chargeDataInit() {
    	model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    	model.read("C://Users//david//Documents//Jena//AnimalesJena.owl");
    }
    
   
}
