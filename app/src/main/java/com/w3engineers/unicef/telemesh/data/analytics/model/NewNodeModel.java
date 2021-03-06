package com.w3engineers.unicef.telemesh.data.analytics.model;

/*
 * ============================================================================
 * Copyright (C) 2019 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * ============================================================================
 */

public class NewNodeModel {

    private String userId;
    private long userAddingTime;

    public String getUserId() {
        return userId;
    }

    public NewNodeModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public long getUserAddingTime() {
        return userAddingTime;
    }

    public NewNodeModel setUserAddingTime(long userAddingTime) {
        this.userAddingTime = userAddingTime;
        return this;
    }
}
