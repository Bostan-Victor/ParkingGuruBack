package parking.guru.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;

public enum CustomErrorType implements ErrorClassification {
    VALIDATION_ERROR,
    UNAUTHORIZED,
    INTERNAL_ERROR;

    @Override
    public Object toSpecification(GraphQLError error) {
        return ErrorClassification.super.toSpecification(error);
    }
}
