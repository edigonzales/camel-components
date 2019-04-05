package ch.so.agi.camel.processors;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class Av2chProcessorTest extends CamelTestSupport {
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {
                from("file://src/test/data/?fileName=254900.itf&noop=true")
                //.process(new Av2chProcessor())
                .to("mock:result");
            }
        };
    }

    @Test
    public void runProcessor_Ok() throws Exception {
        MockEndpoint resultEndpoint = getMockEndpoint("mock:result");
        resultEndpoint.expectedMinimumMessageCount(1);  

        Thread.sleep(5000);
        
        assertMockEndpointsSatisfied();
        
        Exchange exchange = resultEndpoint.getExchanges().get(0);
        File resultFile = exchange.getIn().getBody(File.class);
        
        long resultSize = resultFile.length();
        assertTrue(resultSize > 590000);
        
        String content = new String(Files.readAllBytes(Paths.get(resultFile.getAbsolutePath())));
        assertTrue(content.contains("MODL DM01AVCH24LV95D"));
    }
}
