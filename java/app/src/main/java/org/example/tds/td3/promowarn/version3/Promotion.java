package org.example.td3.promowarn.version3;

import java.util.Set;

public interface Promotion {
    Integer id();

    Set<Student> students();

    Promotion register(final Student student);
}
