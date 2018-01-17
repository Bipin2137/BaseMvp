package com.dexter.basemvpwithoutdagger.api.response;

import com.dexter.basemvpwithoutdagger.database.beans.SearchBean;

import java.util.List;

/**
 * Created by Khatr on 12/31/2017.
 */

public class SearchResponse {

    /**
     * Search : [{"Title":"Guardians of the Galaxy","Year":"2014","imdbID":"tt2015381","Type":"movie","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMTAwMjU5OTgxNjZeQTJeQWpwZ15BbWU4MDUxNDYxODEx._V1_SX300.jpg"},{"Title":"Guardians of the Galaxy Vol. 2","Year":"2017","imdbID":"tt3896198","Type":"movie","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMTg2MzI1MTg3OF5BMl5BanBnXkFtZTgwNTU3NDA2MTI@._V1_SX300.jpg"},{"Title":"The Hitchhiker's Guide to the Galaxy","Year":"2005","imdbID":"tt0371724","Type":"movie","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BZmU5MGU4MjctNjA2OC00N2FhLWFhNWQtMzQyMGI2ZmQ0Y2YyL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg"},{"Title":"Galaxy Quest","Year":"1999","imdbID":"tt0177789","Type":"movie","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMjA0NjM1ODkyMl5BMl5BanBnXkFtZTcwODY0NDMzMg@@._V1_SX300.jpg"},{"Title":"The Hitchhiker's Guide to the Galaxy","Year":"1981","imdbID":"tt0081874","Type":"series","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMTI2OTMwNDU1NF5BMl5BanBnXkFtZTcwOTIyNzAzMQ@@._V1_SX300.jpg"},{"Title":"Galaxy of Terror","Year":"1981","imdbID":"tt0082431","Type":"movie","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BNTE0MjkzMWItYTI3ZS00ZGRiLTllOTktMGMyNzlkMjRmZWZmXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg"},{"Title":"Super Mario Galaxy","Year":"2007","imdbID":"tt0879874","Type":"game","Poster":"N/A"},{"Title":"Guardians of the Galaxy","Year":"2015\u2013","imdbID":"tt4176370","Type":"series","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BNDM4NDQxMDU2MV5BMl5BanBnXkFtZTgwMDY2MDQ5NjE@._V1_SX300.jpg"},{"Title":"Galaxy Express 999","Year":"1979","imdbID":"tt0080772","Type":"movie","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMjIwNmQ3NjItNjBhYi00ODU0LTliZDktYzZmOTM3ZTM3ZGI2XkEyXkFqcGdeQXVyNzc5MjA3OA@@._V1_SX300.jpg"},{"Title":"Power Rangers Lost Galaxy","Year":"1999\u20132000","imdbID":"tt0175059","Type":"series","Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMjA0NTE0MTU2Nl5BMl5BanBnXkFtZTcwMzk3NzAzMQ@@._V1_SX300.jpg"}]
     * totalResults : 176
     * Response : True
     */

    private String totalResults;
    private String Response;
    private List<SearchBean> Search;

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String Response) {
        this.Response = Response;
    }

    public List<SearchBean> getSearch() {
        return Search;
    }

    public void setSearch(List<SearchBean> Search) {
        this.Search = Search;
    }
}
