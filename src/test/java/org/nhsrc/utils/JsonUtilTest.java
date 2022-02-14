package org.nhsrc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonUtilTest {
    @Test
    public void codedValues() throws JsonProcessingException {
        List<String> strings = new ArrayList<>();
        strings.add("Yes");
        strings.add("No");
        assertEquals("[\"Yes\",\"No\"]", JsonUtil.OBJECT_MAPPER.writeValueAsString(strings));
    }
}
