package com.movielist.model.entity.catalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class MovieResultTest {

    private MovieResult mResult;

    @BeforeEach
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

    @org.junit.jupiter.api.Test
    public void canLoadPage() {
        assertEquals(mResult.canLoadPage(),true);
        mResult.currentPage = 2;
        assertEquals(mResult.canLoadPage(),false);
    }
}