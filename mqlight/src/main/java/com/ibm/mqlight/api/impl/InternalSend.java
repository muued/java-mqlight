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

import com.ibm.mqlight.api.QOS;

class InternalSend<T> extends Message implements QueueableWork {
    final String topic;
    final QOS qos;
    final byte[] data;
    final int length;
    final CompletionFuture<T> future;
    InternalSend(NonBlockingClientImpl client, String topic, QOS qos, byte[] data, int length) {
        this.future = new CompletionFuture<T>(client);
        this.topic = topic;
        this.qos = qos;
        this.data = data;
        this.length = length;
    }
}