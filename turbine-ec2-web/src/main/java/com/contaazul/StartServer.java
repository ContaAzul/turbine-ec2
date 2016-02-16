package com.contaazul;

import com.contaazul.turbine.EC2Discovery;
import com.netflix.turbine.init.TurbineInit;
import com.netflix.turbine.plugins.PluginsFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StartServer listener that will start turbine server.
 */
public final class StartServer implements ServletContextListener {
    /**
     * Logger.
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(StartServer.class);

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        StartServer.LOGGER.info("Initing Turbine server...");
        PluginsFactory.setInstanceDiscovery(new EC2Discovery());
        TurbineInit.init();
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        StartServer.LOGGER.info("Stopping Turbine server...");
        TurbineInit.stop();
    }
}
