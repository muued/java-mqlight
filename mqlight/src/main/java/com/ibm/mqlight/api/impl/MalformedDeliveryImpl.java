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

import java.nio.ByteBuffer;
import java.util.Map;

import com.ibm.mqlight.api.MalformedDelivery;
import com.ibm.mqlight.api.QOS;
import com.ibm.mqlight.api.impl.engine.DeliveryRequest;
import com.ibm.mqlight.api.logging.Logger;
import com.ibm.mqlight.api.logging.LoggerFactory;
import org.apache.qpid.proton.amqp.messaging.Properties;

public class MalformedDeliveryImpl extends BytesDeliveryImpl implements MalformedDelivery {

    private static final Logger logger = LoggerFactory.getLogger(MalformedDeliveryImpl.class);

    private final MalformedReason reason;
    private final String description;
    private final String format;
    private final int ccsid;

    protected MalformedDeliveryImpl(NonBlockingClientImpl client, QOS qos, String shareName, String topic, String topicPattern, long ttl,
                                    ByteBuffer data, Properties properties,
                                    Map<String, Object> applicationProperties, DeliveryRequest req, MalformedReason reason,
                                    String malformedDescription, String malformedMQMDFormat, int malformedMQMDCCSID) {
        super(client, qos, shareName, topic, topicPattern, ttl, data, properties, applicationProperties, req);

        final String methodName = "<init>";
        logger.entry(this, methodName, client, qos, shareName, topic, topicPattern, ttl, data, properties, req, reason, malformedDescription, malformedMQMDFormat, malformedMQMDCCSID);

        this.reason = reason;
        this.description = malformedDescription;
        this.format = malformedMQMDFormat;
        this.ccsid = malformedMQMDCCSID;

        logger.exit(this, methodName);
    }

    @Override
    public Type getType() {
        return Type.MALFORMED;
    }

    public MalformedReason getReason() {
        return reason;
    }

    public String getDescription() {
        return description;
    }

    public String getMQMDFormat() {
        return format;
    }

    public int getMQMDCodedCharSetId() {
        return ccsid;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName()
                + "@" + Integer.toHexString(hashCode())
                + " [data=" + getData()
                + ", description=" + getDescription()
                + ", mqmd ccsid=" + getMQMDCodedCharSetId()
                + ", properties=" + getProperties()
                + ", qos=" + getQOS()
                + ", reason=" + getReason()
                + ", share=" + getShare()
                + ", topic=" + getTopic()
                + ", topic pattern=" + getTopicPattern()
                + ", ttl=" + getTtl()
                + ", type=" + getType() + "]";
    }
}
