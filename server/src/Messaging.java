import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.concurrent.Executors;

public class Messaging {

//    private static final Executor threadPool = Executors.newFixedThreadPool(5);

    protected final static Logger logger = Logger.getLogger(Messaging.class.getName());
    private static Map<String,ClientData> clients = new HashMap<String,ClientData>();

    public static synchronized void registerClient(ClientData clientData){
        if (clients.containsKey(clientData.phone)){
            clients.remove(clientData.phone);
        }
        clients.put(clientData.phone, clientData);
    }

    public static synchronized void unregisterClient(ClientData clientData){
        if (clients.containsKey(clientData.phone)){
            clients.remove(clientData.phone);
        }
    }



    public static void messageClients(ServletConfig config,List<String> phoneNumbers){
        String key = (String)config.getServletContext().getAttribute(APIKeyInitializer.ATTRIBUTE_ACCESS_KEY);
        List<ClientData> sendClients = new ArrayList<ClientData>();
        for (String phone : clients.keySet()){
            if (phoneNumbers.contains(phone)) sendClients.add(clients.get(phone));
        }
        asyncSend(key, sendClients);
    }


    private static void asyncSend(String key,List<ClientData> recipients) {
        // make a copy
        final List<ClientData> _recipients = new ArrayList<ClientData>(recipients);
        final List<String> _recipientIds = new ArrayList<String>();
        for (ClientData cd : _recipients){ _recipientIds.add(cd.gcmKey); }
        final Sender sender = new Sender("AIzaSyB_kYqVQ2rRd3SxIzKHkJsSe-dFF1hMdn0");
//        threadPool.execute(new Runnable() {
//            public void run() {
                Message message = new Message.Builder().addData("somekey","somevalue").build();
                MulticastResult multicastResult;
                try {
                    multicastResult = sender.send(message, _recipientIds, 5);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error posting messages", e);
                    return;
                }
                List<Result> results = multicastResult.getResults();
                // analyze the results
                for (int i = 0; i < _recipients.size(); i++) {
                    ClientData client = _recipients.get(i);
                    Result result = results.get(i);
                    String messageId = result.getMessageId();
                    if (messageId != null) {
                        logger.fine("Succesfully sent message to device: " + client.phone + "; messageId = " + messageId);
                        String canonicalRegId = result.getCanonicalRegistrationId();
                        if (canonicalRegId != null) {
                            // same device has more than one registration id: update it
                            logger.info("canonicalRegId " + canonicalRegId);
                            synchronized (client.phone.intern()){
                                client.gcmKey = canonicalRegId;
                                registerClient(client);
                            }
                        }
                    } else {
                        String error = result.getErrorCodeName();
                        if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                            // application has been removed from device - unregister it
                            logger.info("Unregistered device: " + client);
                            unregisterClient(client);
                        } else {
                            logger.severe("Error sending message to " + client + ": " + error);
                        }
                    }
                }
//            }});
    }

}
