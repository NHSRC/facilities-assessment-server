package org.nhsrc.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DistrictTest {
    final Date switchOverDate = date(2021, 1);
    final Date justAfterSwitchOverDate = date(2021, 2);

    @Test
    public void test() {
        List<District> districts = new ArrayList<>();
        create(date(2017, 0), switchOverDate, true, districts, 1);
        create(date(2017, 1), switchOverDate, true, districts, 2);
        create(date(2021, 3), date(2021, 3), false, districts, 3);
        create(date(2021, 4), date(2021, 4), false, districts, 4);
        create(date(2021, 5), date(2021, 5), false, districts, 5);
        create(date(2021, 6), date(2021, 6), true, districts, 6);
        create(date(2021, 7), date(2021, 8), true, districts, 7);

        assertEquality(districts, date(1900, 0), 5);
        assertEquality(districts, date(2017, 0), 7);
        assertEquality(districts, date(2017, 1), 7);
        assertEquality(districts, date(2017, 2), 7);
        assertEquality(districts, date(2017, 3), 7);
        assertEquality(districts, date(2021, 0), 7);
        assertEquality(districts, date(2021, 1), 5);

        assertEquality(districts, date(2021, 3), 4);
        assertEquality(districts, date(2021, 4), 3);
        assertEquality(districts, date(2021, 5), 2);
        assertEquality(districts, date(2021, 6), 1);
        assertEquality(districts, date(2021, 7), 1);
        assertEquality(districts, date(2021, 8), 0);
    }

    private void assertEquality(List<District> districts, Date date, int numberOfMatches) {
        List<District> matchedDistricts = match(districts, date);
        String districtsMatched = matchedDistricts.stream().map(district -> district.getId().toString()).collect(Collectors.joining(", "));
        Assert.assertEquals(districtsMatched, numberOfMatches, matchedDistricts.size());
    }

    private List<District> match(List<District> districts, Date requestDate) {
        if (requestDate.getYear() + 1900 == 1900) {
            requestDate = justAfterSwitchOverDate;
        }
        Date finalRequestDate = requestDate;

        return districts.stream().filter(district -> {
            boolean after = district.getLastModifiedDate().after(finalRequestDate);
            boolean afterSwitchOver = district.getCreatedDate().after(switchOverDate);
            boolean beforeSwitchOver = (district.getCreatedDate().before(switchOverDate) || district.getCreatedDate().equals(switchOverDate));
            return after && (beforeSwitchOver || afterSwitchOver);
        }).collect(Collectors.toList());
    }

    private Date date(int year, int month) {
        return new GregorianCalendar(year, month, 1, 0, 0, 0).getTime();
    }

    private void create(Date createdDateTime, Date lastModifiedDateTime, boolean inactive, List<District> districts, int id) {
        District district = new District();
        district.setId(id);
        district.setCreatedDate(new java.sql.Date(createdDateTime.getTime()));
        district.setLastModifiedDate(new java.sql.Date(lastModifiedDateTime.getTime()));
        district.setInactive(inactive);
        districts.add(district);
    }
}