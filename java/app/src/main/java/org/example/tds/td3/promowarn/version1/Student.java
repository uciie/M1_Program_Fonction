package org.example.tds.td3.promowarn.version1;

import org.example.tds.td3.promowarn.common.mail.EMailAddress;

public interface Student {
    Integer id();

    EMailAddress email();

    Double grade();

    void grade(final double grade);
}
