package com.vaadin.cdi;

import static com.vaadin.cdi.ArchiveProvider.createJavaArchive;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.vaadin.cdi.views.MainUI;

/**
 * 
 * @author adam-bien.com
 */
@RunWith(Arquillian.class)
public class VaadinScopedInjectionIT {

    @Deployment
    public static JavaArchive deploy() {
        return createJavaArchive(VaadinScopedBean.class, MainUI.class,
                VaadinScopedBean.class);
    }

    @Test
    public void scopedIsScoped() {
        Client client = Client.create();
        ClientResponse message = client.resource(
                "http://localhost:8181/test/mainUI").get(ClientResponse.class);
        assertThat(VaadinScopedBean.COUNTER.get(), is(1));
        assertThat(MainUI.COUNTER.get(), is(1));
    }
}
