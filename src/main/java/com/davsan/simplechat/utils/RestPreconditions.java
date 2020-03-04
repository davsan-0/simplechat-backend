package com.davsan.simplechat.utils;

import com.davsan.simplechat.error.ResourceNotFoundException;

public class RestPreconditions {
    public static <T> T checkFound(T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException();
        }
        return resource;
    }
}
