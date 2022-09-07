package com.me.my_application.resource.api;

import java.util.UUID;

public record MyEntityApi(
        UUID uuid,
        String name,
        StatusApi status
) {
}
