/*
 *   <copyright 
 *   notice="oco-source" 
 *   pids="5725-P60" 
 *   years="2015" 
 *   crc="1438874957" > 
 *   IBM Confidential 
 *    
 *   OCO Source Materials 
 *    
 *   5724-H72
 *    
 *   (C) Copyright IBM Corp. 2015
 *    
 *   The source code for the program is not published 
 *   or otherwise divested of its trade secrets, 
 *   irrespective of what has been deposited with the 
 *   U.S. Copyright Office. 
 *   </copyright> 
 */

package com.ibm.mqlight.api;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import junit.framework.AssertionFailedError;
import static org.junit.Assert.*;

import org.junit.Test;

import com.ibm.mqlight.api.impl.Message;

public class TestNonBlockingClient {
    
    private Object NULL = new Object();
    
    private class MockNonBlockingClient extends NonBlockingClient {
        private LinkedList<Object> expectedValues = new LinkedList<Object>();
        private MockNonBlockingClient(Object[] expectedValues) {
            for (Object o : expectedValues) {
                this.expectedValues.addLast(o);
            }
        }
        @Override public String getId() { return null; }
        @Override public String getService() { return null; }
        @Override public ClientState getState() { return null; }
        @Override public <T> NonBlockingClient start(CompletionListener<T> listener, T context) { return null; }
        @Override public <T> void stop(CompletionListener<T> listener, T context) {}
        @Override protected void onReceive(Message message) {}

        private void testAgainstExpectedValues(Object[] values) {
            if (expectedValues == null) {
                throw new AssertionFailedError("Expected only a single method of MockNonBlockingClient to have been called");
            }
            if (expectedValues.size() != values.length) {
                throw new AssertionFailedError("Mis-match between number of expected values (" + expectedValues.size() + 
                        ") and number of actual values (" + values.length  + ")");
            }
            int count = 0;
            for (Object actualValue : values) {
                Object expectedValue = expectedValues.removeFirst();
                if (expectedValue == NULL) {
                    assertNull(""+count, actualValue);
                } else {
                    assertEquals(""+count, expectedValue, actualValue);
                }
                ++count;
            }
            expectedValues = null;
        }
        @Override
        public <T> boolean send(String topic, String data,
                Map<String, Object> properties, SendOptions sendOptions,
                CompletionListener<T> listener, T context)
                throws StateException {
            testAgainstExpectedValues(new Object[] {"send", topic, data, properties, sendOptions, listener, context});
            return false;
        }

        @Override
        public <T> boolean send(String topic, ByteBuffer data,
                Map<String, Object> properties, SendOptions sendOptions,
                CompletionListener<T> listener, T context)
                throws StateException {
            testAgainstExpectedValues(new Object[] {"send", topic, data, properties, sendOptions, listener, context});
            return false;
        }
        
        @Override
        public <T> NonBlockingClient subscribe(String topicPattern, SubscribeOptions subOptions,
                DestinationListener<T> destListener, CompletionListener<T> compListener, T context)
                throws StateException, IllegalArgumentException {
            testAgainstExpectedValues(new Object[] {"subscribe", topicPattern, subOptions, destListener, compListener, context});
            return null;
        }

        @Override
        public <T> NonBlockingClient unsubscribe(String topicPattern,
                String share, int ttl, CompletionListener<T> listener, T context)
                throws StateException, IllegalArgumentException {
            testAgainstExpectedValues(new Object[] {"unsubscribe", topicPattern, share, ttl, listener, context});
            return null;
        }

        @Override
        public <T> NonBlockingClient unsubscribe(String topicPattern,
                String share, CompletionListener<T> listener, T context)
                throws StateException {
            testAgainstExpectedValues(new Object[] {"unsubscribe", topicPattern, share, listener, context});
            return null;
        }
        
    }
    
    private class StubCompletionListener implements CompletionListener<Object> {
        @Override public void onSuccess(NonBlockingClient client, Object context) {}
        @Override public void onError(NonBlockingClient client, Object context, Exception exception) {}
    }

    private class StubDestinationListener implements DestinationListener<Object> {
        @Override public void onMessage(NonBlockingClient client, Object context, Delivery delivery) {}
        @Override public void onMalformed(NonBlockingClient client, Object context, MalformedDelivery delivery) {}
        @Override public void onUnsubscribed(NonBlockingClient client, Object context, String topicPattern, String share) {}
    }

    @Test 
    public void send1() {
        String topic = "topic";
        String data = "data";
        HashMap<String, Object> properties = new HashMap<String, Object>();
        MockNonBlockingClient client = new MockNonBlockingClient(new Object[] {"send",  topic, data, properties, MockNonBlockingClient.defaultSendOptions, NULL, NULL});
        client.send(topic, data, properties);
    }
    
    @Test 
    public void send2() {
        String topic = "topic";
        ByteBuffer data = ByteBuffer.allocate(1);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        MockNonBlockingClient client = new MockNonBlockingClient(new Object[] {"send",  topic, data, properties, MockNonBlockingClient.defaultSendOptions, NULL, NULL});
        client.send(topic, data, properties);
    }
    
    @Test 
    public void send3() {
        String topic = "topic";
        ByteBuffer data = ByteBuffer.allocate(1);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        StubCompletionListener listener = new StubCompletionListener();
        Object context = new Object();
        MockNonBlockingClient client = new MockNonBlockingClient(new Object[] {"send",  topic, data, properties, MockNonBlockingClient.defaultSendOptions, listener, context});
        client.send(topic, data, properties, listener, context);
    }
    
    @Test 
    public void send4() {
        String topic = "topic";
        String data = "data";
        HashMap<String, Object> properties = new HashMap<String, Object>();
        StubCompletionListener listener = new StubCompletionListener();
        Object context = new Object();
        MockNonBlockingClient client = new MockNonBlockingClient(new Object[] {"send",  topic, data, properties, NonBlockingClient.defaultSendOptions, listener, context});
        client.send(topic, data, properties, listener, context);
    }
    
    @Test
    public void subscribe() {
        String topicPattern = "topicPattern";
        StubDestinationListener destListener= new StubDestinationListener();
        StubCompletionListener compListener = new StubCompletionListener();
        Object context = new Object();
        MockNonBlockingClient client = new MockNonBlockingClient(new Object[] {"subscribe", topicPattern, NonBlockingClient.defaultSubscribeOptions, destListener, compListener, context});
        client.subscribe(topicPattern, destListener, compListener, context);
    }
    
    @Test
    public void unsubscribe1() {
        String topicPattern = "topicPattern";
        StubCompletionListener listener = new StubCompletionListener();
        Object context = new Object();
        MockNonBlockingClient client = new MockNonBlockingClient(new Object[] {"unsubscribe", topicPattern, NULL, listener, context});
        client.unsubscribe(topicPattern, listener, context);
    }
    
    @Test
    public void unsubscribe2() {
        String topicPattern = "topicPattern";
        int ttl = 1234;
        StubCompletionListener listener = new StubCompletionListener();
        Object context = new Object();
        MockNonBlockingClient client = new MockNonBlockingClient(new Object[] {"unsubscribe", topicPattern, NULL, ttl, listener, context});
        client.unsubscribe(topicPattern, ttl, listener, context);
    }
}