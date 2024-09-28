package parking.guru.exceptions;

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
        // Handle ActiveReservationExistsException
        if (ex instanceof ActiveReservationExistsException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage())
                    .errorType(CustomErrorType.VALIDATION_ERROR)  // Use custom error type
                    .build();
        }

        // Handle UnauthorizedAccessException
        if (ex instanceof UnauthorizedAccessException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage())
                    .errorType(CustomErrorType.UNAUTHORIZED)  // Use custom error type for unauthorized access
                    .build();
        }

        // Handle other exceptions
        return GraphqlErrorBuilder.newError(env)
                .message("Internal server error")
                .errorType(CustomErrorType.INTERNAL_ERROR)  // Use custom error type for internal server errors
                .build();
    }
}
