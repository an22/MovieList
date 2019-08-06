package com.movielist.presenter.activity_presenters;

import com.movielist.model.entity.moviedetails.Genre;
import com.movielist.model.entity.moviedetails.Movie;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class MoviePresenterTest {

    MoviePresenter presenter;
    @Before
    public void setUp() throws Exception {
        Movie model = mock(Movie.class);
        Genre genre = mock(Genre.class);
        Genre[] genres = {genre,genre};
        Genre[] genres2 = {};
        when(genre.getName()).thenReturn("1");

        when(model.getGenres()).thenReturn(genres).thenReturn(genres2);

        when(model.getRuntime()).thenReturn(120).thenReturn(125);

        presenter = new MoviePresenter("",null,model,"",1);
    }

    @Test
    public void formatGenres() {
        assertEquals( presenter.formatGenres(),"Genres: 1, 1 ");

        assertEquals(presenter.formatGenres(),"No genres are set");
    }

    @Test
    public void formatRuntime() {
        assertEquals( presenter.formatRuntime(),"2 h. 0 m.");

        assertEquals(presenter.formatRuntime(),"2 h. 5 m.");
    }
}