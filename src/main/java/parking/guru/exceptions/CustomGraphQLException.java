package parking.guru.exceptions;

import graphql.GraphQLException;

public class CustomGraphQLException extends GraphQLException {
    public CustomGraphQLException(String message) {
        super(message);
    }

//    public CustomGraphQLException(String message, Throwable cause) {
//        super(message, cause);
//    }
}
