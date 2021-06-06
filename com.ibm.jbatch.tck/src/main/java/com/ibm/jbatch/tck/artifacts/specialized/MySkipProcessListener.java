/*
 * Copyright 2012, 2020 International Business Machines Corp. and others
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.jbatch.tck.artifacts.specialized;

import java.util.logging.Logger;

import jakarta.batch.api.chunk.listener.SkipProcessListener;
import jakarta.batch.runtime.context.JobContext;
import jakarta.batch.runtime.context.StepContext;
import jakarta.inject.Inject;

import com.ibm.jbatch.tck.utils.Reporter;

import com.ibm.jbatch.tck.artifacts.chunktypes.ReadRecord;
import com.ibm.jbatch.tck.artifacts.reusable.MyParentException;

@jakarta.inject.Named("mySkipProcessListener")
public class MySkipProcessListener implements SkipProcessListener {

    @Inject
    JobContext jobCtx;

    @Inject
    StepContext stepCtx;

    private final static String sourceClass = MySkipProcessListener.class.getName();
    private final static Logger logger = Logger.getLogger(sourceClass);

    public static final String GOOD_EXIT_STATUS = "MySkipProcessListener: GOOD STATUS, GOOD OBJ PASSED";
    public static final String BAD_EXIT_STATUS = "MySkipProcessListener: BAD STATUS";

    @Override
    public void onSkipProcessItem(Object item, Exception e) {
        Reporter.log("In onSkipProcessItem()" + e + "<p>");

        ReadRecord input = (ReadRecord) item;

        if (item != null) {
            logger.finer("In onSkipProcessItem(), item count = " + input.getCount());

            if (e instanceof MyParentException) {
                Reporter.log("SKIPLISTENER: onSkipProcessItem, exception is an instance of: MyParentException<p>");
                jobCtx.setExitStatus(GOOD_EXIT_STATUS);
            } else {
                Reporter.log("SKIPLISTENER: onSkipProcessItem, exception is NOT an instance of: MyParentException<p>");
                jobCtx.setExitStatus(BAD_EXIT_STATUS);
            }
        }
    }
}
