package parking.guru.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, @NotNull DataFetchingEnvironment env) {
        if (ex instanceof ActiveReservationExistsException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage())
                    .errorType(ErrorType.ValidationError)  // This indicates a client-side issue
                    .build();
        }

        // For other exceptions, provide a general error message
        return GraphqlErrorBuilder.newError(env)
                .message("Internal server error")
                .build();
    }
}
