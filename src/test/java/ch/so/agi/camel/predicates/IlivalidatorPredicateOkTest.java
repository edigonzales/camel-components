package ch.so.agi.camel.predicates;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class IlivalidatorPredicateOkTest extends CamelTestSupport {
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {                
                from("file://src/test/data/?fileName=254900.itf&noop=true")
                .choice()
                    .when(new IlivalidatorPredicate()).to("mock:resultOk")
                    .otherwise().to("mock:resultFail")
                .end();
            }
        };
    }

    @Test
    public void runPredicate_Ok() throws Exception {
        MockEndpoint resultEndpoint = getMockEndpoint("mock:resultOk");
        resultEndpoint.expectedMinimumMessageCount(1);  
        resultEndpoint.setResultWaitTime(30000); // for travis
        
        assertMockEndpointsSatisfied();
    }
}
