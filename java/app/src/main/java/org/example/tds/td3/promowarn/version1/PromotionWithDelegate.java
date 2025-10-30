package org.example.tds.td3.promowarn.version1;

public interface PromotionWithDelegate extends Promotion {
    Student delegate();

    void chooseDelegate(final Student e);
}
