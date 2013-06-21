package com.github.dsaran.video;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
public class SessionComponentFactory implements ComponentFactory<Session> {

	/** Logger.	*/
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionComponentFactory.class);

    /** The global Session Factory instance. */
    private static final SessionFactory SESSION_FACTORY;

    private Session session;

    static {
    	try {
    		Configuration configuration = new Configuration().configure();
    		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
    		.applySettings(configuration.getProperties());
    		SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
    	} catch (RuntimeException e) {
    		LOGGER.error(e.getMessage(), e);
    		throw e;
    	}
    }

    @PostConstruct
    public void create() {
        this.session = SESSION_FACTORY.openSession();
    }

    public Session getInstance() {
        return session;
    }

    @PreDestroy
    public void destroy() {
        this.session.close();
    }

}
