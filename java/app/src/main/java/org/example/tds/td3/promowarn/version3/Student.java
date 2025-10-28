package org.example.td3.promowarn.version3;

import org.example.td3.promowarn.common.mail.EMailAddress;

import java.util.Optional;

public interface Student {
    Integer id();

    EMailAddress email();

    Optional<Double> grade();

    void grade(final double grade);
}
