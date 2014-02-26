/*
 * Copyright 2013, The Sporting Exchange Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Originally from UpdatedComponentTests/StandardValidation/REST/Rest_IDL_PostBodyElement_Map_JSON_KeyNotPassed.xls;
package com.betfair.cougar.tests.updatedcomponenttests.standardvalidation.rest;

import com.betfair.testing.utils.cougar.misc.XMLHelpers;
import com.betfair.testing.utils.JSONHelpers;
import com.betfair.testing.utils.cougar.assertions.AssertionUtils;
import com.betfair.testing.utils.cougar.beans.HttpCallBean;
import com.betfair.testing.utils.cougar.beans.HttpResponseBean;
import com.betfair.testing.utils.cougar.manager.AccessLogRequirement;
import com.betfair.testing.utils.cougar.manager.CougarManager;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that Cougar returns the correct fault, when a REST JSON request contains a map body parameter with a value that has no corresponding key passed
 */
public class RestIDLPostBodyElementMapJSONKeyNotPassedTest {
    @Test
    public void doTest() throws Exception {
        // Create the HttpCallBean
        CougarManager cougarManager1 = CougarManager.getInstance();
        HttpCallBean httpCallBeanBaseline = cougarManager1.getNewHttpCallBean();
        CougarManager cougarManagerBaseline = cougarManager1;
        // Get the cougar logging attribute for getting log entries later
        // Point the created HttpCallBean at the correct service
        httpCallBeanBaseline.setServiceName("baseline", "cougarBaseline");

        httpCallBeanBaseline.setVersion("v2");
        // Set up the Http Call Bean to make the request
        CougarManager cougarManager2 = CougarManager.getInstance();
        HttpCallBean getNewHttpCallBean2 = cougarManager2.getNewHttpCallBean("87.248.113.14");
        cougarManager2 = cougarManager2;

        cougarManager2.setCougarFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");

        getNewHttpCallBean2.setOperationName("simpleMapOperation");

        getNewHttpCallBean2.setServiceName("baseline", "cougarBaseline");

        getNewHttpCallBean2.setVersion("v2");
        // Set the body parameter to a map that contains a value with no corresponding key
        Map map3 = new HashMap();
        map3.put("RESTJSON","{\n    \"simpleMap\": {\n        \"bbb\": \"Value for bbb\",\n        \"aaa\": \"Value for aaa\",\n        \"ccc\": \"Value for ccc\",\n        \"value for not passed key\"\n    }\n}");
        getNewHttpCallBean2.setPostQueryObjects(map3);
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());
        // Make REST JSON call to the operation requesting an XML response
        cougarManager2.makeRestCougarHTTPCall(getNewHttpCallBean2, com.betfair.testing.utils.cougar.enums.CougarMessageProtocolRequestTypeEnum.RESTJSON, com.betfair.testing.utils.cougar.enums.CougarMessageContentTypeEnum.XML);
        // Make REST JSON call to the operation requesting a JSON response
        cougarManager2.makeRestCougarHTTPCall(getNewHttpCallBean2, com.betfair.testing.utils.cougar.enums.CougarMessageProtocolRequestTypeEnum.RESTJSON, com.betfair.testing.utils.cougar.enums.CougarMessageContentTypeEnum.JSON);
        // Create the expected response as an XML document (Fault)
        XMLHelpers xMLHelpers5 = new XMLHelpers();
        Document createAsDocument12 = xMLHelpers5.getXMLObjectFromString("<fault><faultcode>Client</faultcode><faultstring>DSC-0044</faultstring><detail/></fault>");
        // Convert expected response to a JSON object for comparison with JSON actual response
        JSONHelpers jSONHelpers6 = new JSONHelpers();
        JSONObject convertXMLDocumentToJSONObjectRemoveRootElement13 = jSONHelpers6.convertXMLDocumentToJSONObjectRemoveRootElement(createAsDocument12);
        // Check the 2 responses are as expected (Bad Request)
        HttpResponseBean response7 = getNewHttpCallBean2.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(createAsDocument12, response7.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response7.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response7.getHttpStatusText());

        HttpResponseBean response8 = getNewHttpCallBean2.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(convertXMLDocumentToJSONObjectRemoveRootElement13, response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response8.getHttpStatusText());

        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected

        CougarManager cougarManager11 = CougarManager.getInstance();
        cougarManager11.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp9, new AccessLogRequirement("87.248.113.14", "/cougarBaseline/v2/simpleMapOperation", "BadRequest"),new AccessLogRequirement("87.248.113.14", "/cougarBaseline/v2/simpleMapOperation", "BadRequest") );
    }

}
