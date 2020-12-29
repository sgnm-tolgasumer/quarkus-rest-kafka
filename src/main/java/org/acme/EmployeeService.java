package org.acme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.mutiny.kafka.client.producer.KafkaProducerRecord;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EmployeeService {
    public List employees = new ArrayList();

    @Inject @Channel("employees")
    Emitter<String> employeeEmitter;

    // Creating ObjectMapper to parse Object to JSON
    ObjectMapper mapper = new ObjectMapper();

    public Employee create(Employee employeeToCreate){

        // Converting the Object to JSONString
        String employeeJson = null;
        try {
            employeeJson = mapper.writeValueAsString(employeeToCreate);
            System.out.println(employeeJson);
            //employeeEmitter.send(employeeJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // Push created employee to Kafka stream
        employeeEmitter.send(employeeJson);


        employees.add(employeeToCreate);
        return employeeToCreate;
    }

    public List getAll(){
        return employees;
    }
}
