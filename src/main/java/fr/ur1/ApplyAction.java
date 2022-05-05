package fr.ur1;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

import avro.monitor.commands.ElementEvent;
import avro.monitor.commands.SetXMICommand;
import io.smallrye.reactive.messaging.MutinyEmitter;

@Path("/monitor")
public class ApplyAction {

    @OnOverflow(OnOverflow.Strategy.DROP)
    @Inject
    @Channel("model-input-event")
    MutinyEmitter<ElementEvent> eventEmitter;

    @OnOverflow(OnOverflow.Strategy.DROP)
    @Inject
    @Channel("model-input-set-model")
    MutinyEmitter<SetXMICommand> setModelEmitter;

    @POST
    @Path("event")
    public ElementEvent applyAction(ElementEvent event) throws IOException {
        System.out.println("send");
        eventEmitter.sendAndAwait(event);
        System.out.println("sent");
        return event;
    }

    @POST
    @Path("set_model")
    public SetXMICommand setModel(SetXMICommand modelData) throws IOException {
        System.out.println("send");
        setModelEmitter.sendAndAwait(modelData);
        System.out.println("sent");
        return modelData;
    }

}
