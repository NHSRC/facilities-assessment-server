package org.nhsrc.domain.nin;

import org.junit.Test;

import static org.junit.Assert.*;

public class NinSyncDetailsTest {
    @Test
    public void nextPage() {
        NinSyncDetails ninSyncDetails = new NinSyncDetails();
        ninSyncDetails.setHasMoreForDate(false);
        ninSyncDetails.setOffsetSuccessfullyProcessed(0);
        ninSyncDetails.setDateProcessedUpto("2021-10-11");

        NinSyncDetails.PageToRequest pageToRequest = ninSyncDetails.nextPage();
        assertEquals("2021-10-12", pageToRequest.getDate());
    }
}
