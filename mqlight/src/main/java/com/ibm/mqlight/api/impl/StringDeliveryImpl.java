/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ibm.mqlight.api.impl;

import java.util.Map;

import com.ibm.mqlight.api.QOS;
import com.ibm.mqlight.api.StringDelivery;
import com.ibm.mqlight.api.impl.engine.DeliveryRequest;
import com.ibm.mqlight.api.logging.Logger;
import com.ibm.mqlight.api.logging.LoggerFactory;
import org.apache.qpid.proton.amqp.messaging.Properties;

class StringDeliveryImpl extends DeliveryImpl implements StringDelivery {

    private static final Logger logger = LoggerFactory.getLogger(StringDeliveryImpl.class);

    private final String data;

    protected StringDeliveryImpl(NonBlockingClientImpl client, QOS qos, String share, String topic,
                                 String topicPattern, long ttl, String data, Properties properties,
                                 Map<String, Object> applicationProperties, DeliveryRequest deliveryRequest) {
        super(client, qos, share, topic, topicPattern, ttl, properties, applicationProperties, deliveryRequest);
        final String methodName = "<init>";
        logger.entry(this, methodName, client, qos, share, topic, topicPattern, ttl, data, properties, deliveryRequest);

        this.data = data;

        logger.exit(this, methodName);
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }

    @Override
    public String getData() {
        return data;
    }

}
