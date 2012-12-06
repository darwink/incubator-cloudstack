// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package org.apache.cloudstack.api.admin.network.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.BaseCmd;
import org.apache.cloudstack.api.BaseListCmd;
import org.apache.cloudstack.api.IdentityMapper;
import org.apache.cloudstack.api.Implementation;
import org.apache.cloudstack.api.Parameter;
import org.apache.cloudstack.api.ServerApiException;
import com.cloud.api.response.ListResponse;
import com.cloud.api.response.StorageNetworkIpRangeResponse;
import com.cloud.dc.StorageNetworkIpRange;
import com.cloud.exception.ConcurrentOperationException;
import com.cloud.exception.InsufficientCapacityException;
import com.cloud.exception.ResourceAllocationException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.user.Account;

@Implementation(description="List a storage network IP range.", responseObject=StorageNetworkIpRangeResponse.class, since="3.0.0")
public class ListStorageNetworkIpRangeCmd extends BaseListCmd {
    public static final Logger s_logger = Logger.getLogger(ListStorageNetworkIpRangeCmd.class);

    String s_name = "liststoragenetworkiprangeresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @IdentityMapper(entityTableName="dc_storage_network_ip_range")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, description="optional parameter. Storaget network IP range uuid, if specicied, using it to search the range.")
    private Long rangeId;

    @IdentityMapper(entityTableName="host_pod_ref")
    @Parameter(name=ApiConstants.POD_ID, type=CommandType.LONG, description="optional parameter. Pod uuid, if specicied and range uuid is absent, using it to search the range.")
    private Long podId;

    @IdentityMapper(entityTableName="data_center")
    @Parameter(name=ApiConstants.ZONE_ID, type=CommandType.LONG, description="optional parameter. Zone uuid, if specicied and both pod uuid and range uuid are absent, using it to search the range.")
    private Long zoneId;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getRangeId() {
        return rangeId;
    }

    public Long getPodId() {
        return podId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
            ResourceAllocationException {
        try {
            List<StorageNetworkIpRange> results = _storageNetworkService.listIpRange(this);
            ListResponse<StorageNetworkIpRangeResponse> response = new ListResponse<StorageNetworkIpRangeResponse>();
            List<StorageNetworkIpRangeResponse> resList = new ArrayList<StorageNetworkIpRangeResponse>(results.size());
            for (StorageNetworkIpRange r : results) {
                StorageNetworkIpRangeResponse resp = _responseGenerator.createStorageNetworkIpRangeResponse(r);
                resList.add(resp);
            }
            response.setResponses(resList);
            response.setResponseName(getCommandName());
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.warn("Failed to list storage network ip range for rangeId=" + getRangeId() + " podId=" + getPodId() + " zoneId=" + getZoneId());
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }

}