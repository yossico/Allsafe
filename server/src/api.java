import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.common.base.StringUtil;

public class api extends javax.servlet.http.HttpServlet {


    final String queryGroup = "queryGroup";
    final String registration = "register";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Map<String,String[]> params  = request.getParameterMap();
        if (!params.containsKey(queryGroup) && !params.containsKey(registration)){
            response.getWriter().write("bad request!");
            response.setStatus(500);
            return;
        }

        if (params.containsKey(queryGroup)){
            String requested = params.get(queryGroup)[0];
            String[] clients = requested.split(",");
            System.out.println(clients[0]);
            System.out.println(clients[1]);
        }

        if (params.containsKey(registration)){
            String[] _params = params.get(registration)[0].split(",");
            ClientData clientData = new ClientData(_params[1],_params[0]);
            Messaging.registerClient(clientData);
            List<String> ackPhone = new ArrayList<String>();
            ackPhone.add(clientData.phone);
            Messaging.messageClients(this, ackPhone);
        }

        response.getWriter().write("git it");
    }
}
