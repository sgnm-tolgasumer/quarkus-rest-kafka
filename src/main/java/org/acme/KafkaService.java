package org.acme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class KafkaService {
    // Creating ObjectMapper to parse Object to JSON
    ObjectMapper mapper = new ObjectMapper();

    @Inject
    @Channel("employees")
    Emitter<String> employeesEmitter;

    @Inject
    @Channel("customer")
    Emitter<String> customerEmitter;

    public void publishAsJson(Object obj, int channelId) {
        String objJson = null;
        try {
            objJson = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // Push created Json to Kafka stream
        //employeesEmitter.send(objJson);

        if(channelId == 0) {
            employeesEmitter.send(objJson);
        }
        else if(channelId == 1){
            customerEmitter.send(objJson);
        }
    }
}
