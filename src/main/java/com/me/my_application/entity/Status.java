package com.me.my_application.entity;

import com.me.my_application.entity.converter.JpaEnumConverter;

public enum Status {
    STATUS_1,
    STATUS_2;

    public static class StatusJpaEnumConverter extends JpaEnumConverter<Status> {
        @Override
        public Class<Status> getEnumClass() {
            return Status.class;
        }
    }
}
