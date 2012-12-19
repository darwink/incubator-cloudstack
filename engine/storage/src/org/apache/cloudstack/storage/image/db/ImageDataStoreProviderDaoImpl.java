/*
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
package org.apache.cloudstack.storage.image.db;

import org.springframework.stereotype.Component;

import com.cloud.utils.db.GenericDaoBase;
import com.cloud.utils.db.SearchCriteria2;
import com.cloud.utils.db.SearchCriteriaService;
import com.cloud.utils.db.SearchCriteria.Op;

@Component
public class ImageDataStoreProviderDaoImpl extends GenericDaoBase<ImageDataStoreProviderVO, Long> implements ImageDataStoreProviderDao {
    @Override
    public ImageDataStoreProviderVO findByName(String name) {
        SearchCriteriaService<ImageDataStoreProviderVO, ImageDataStoreProviderVO> service = SearchCriteria2.create(ImageDataStoreProviderVO.class);
        service.addAnd(service.getEntity().getName(), Op.EQ, name);
        return service.find();
    }
}