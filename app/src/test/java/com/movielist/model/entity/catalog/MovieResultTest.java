package com.movielist.model.entity.catalog;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieResultTest {

    private MovieResult mResult;

    @Before
    public void setUp(){
        mResult = new MovieResult("en","us");
    }

    @Test
    public void checkQuery() {
        mResult.setQuery("query");
        mResult.checkQuery("not_query");
        assertEquals(mResult.currentQuery,"not_query");
        assertEquals(mResult.getResults().size(),0);
        mResult.checkQuery("not_query");
        assertEquals(mResult.currentQuery,"not_query");
    }

    @Test
    public void canLoadPage() {
        assertEquals(mResult.canLoadPage(),true);
        mResult.currentPage = 2;
        assertEquals(mResult.canLoadPage(),false);
    }
}