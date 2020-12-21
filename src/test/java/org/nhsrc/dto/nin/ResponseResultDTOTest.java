package org.nhsrc.dto.nin;

import org.junit.Assert;
import org.junit.Test;

public class ResponseResultDTOTest {
    @Test
    public void isSyncComplete() {
        ResponseResultDTO result = new ResponseResultDTO();
        result.setOffset(0);
        result.setCount(50);
        result.setTotalRecords("125");
        Assert.assertFalse(result.isSyncComplete());
        result.setOffset(50);
        result.setCount(50);
        result.setTotalRecords("125");
        Assert.assertFalse(result.isSyncComplete());
        result.setOffset(100);
        result.setCount(25);
        result.setTotalRecords("125");
        Assert.assertTrue(result.isSyncComplete());
    }
}