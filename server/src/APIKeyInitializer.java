import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APIKeyInitializer implements ServletContextListener {

    static final String ATTRIBUTE_ACCESS_KEY = "apiKey";

    private static final String PATH = "./api.key";

    private final Logger logger = Logger.getLogger(getClass().getName());

    public void contextInitialized(ServletContextEvent event) {
        logger.info("Reading " + PATH + " from resources (probably from " +
                "WEB-INF/classes");
        String key = getKey();
        event.getServletContext().setAttribute(ATTRIBUTE_ACCESS_KEY, key);
        System.out.println(key);
    }

    /**
     * Gets the access key.
     */
    protected String getKey() {
        System.out.println(Thread.currentThread().getContextClassLoader().toString());
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH);
        if (stream == null) {
            throw new IllegalStateException("Could not find file " + PATH + " on web resources)");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Could not read file " + PATH, e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Exception closing " + PATH, e);
            }
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
