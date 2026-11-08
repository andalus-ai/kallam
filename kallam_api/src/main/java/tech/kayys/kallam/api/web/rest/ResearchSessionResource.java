package tech.kayys.kallam.api.web.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;
import java.util.NoSuchElementException;
import tech.kayys.kallam.api.application.ResearchSessionService;
import tech.kayys.kallam.api.wire.ResearchSessionDto;
import tech.kayys.kallam.api.wire.ResearchSessionDtoMapper;

/**
 * Thin JAX-RS adapter: parses requests, calls
 * {@link ResearchSessionService}, maps the result through
 * {@link ResearchSessionDtoMapper}. No domain logic lives here - see the
 * README for the endpoint list this mirrors on the Flutter side's
 * {@code QuarkusResearchRepository}.
 *
 * Scholar identity ({@code scholarId}) is read from request context in a
 * real deployment (e.g. an authenticated principal via
 * {@code SecurityContext}); left as an explicit body/query field here to
 * keep auth wiring out of scope for this scaffold.
 */
@Path("/api/v1/research-sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResearchSessionResource {

    @Inject
    ResearchSessionService service;

    @Inject
    ResearchSessionDtoMapper sessionDtoMapper;

    public record CreateSessionRequest(String question, String language) {}

    public record BlockActionRequest(String scholarId, String note) {}

    public record RevisionRequest(String scholarId, String instruction) {}

    @POST
    public Response create(CreateSessionRequest request) {
        String sessionId = service.createSession(request.question(), request.language());
        return Response.accepted(Map.of("id", sessionId)).build();
    }

    @GET
    @Path("/{sessionId}")
    public ResearchSessionDto get(@PathParam("sessionId") String sessionId) {
        return sessionDtoMapper.toDto(notFoundAsException(() -> service.loadSession(sessionId)));
    }

    @GET
    @Path("/{sessionId}/at-revision/{revisionId}")
    public ResearchSessionDto getAtRevision(
            @PathParam("sessionId") String sessionId, @PathParam("revisionId") String revisionId) {
        return sessionDtoMapper.toDto(
                notFoundAsException(() -> service.loadSessionAtRevision(sessionId, revisionId)));
    }

    @POST
    @Path("/{sessionId}/blocks/{blockId}/accept")
    public Response accept(
            @PathParam("sessionId") String sessionId,
            @PathParam("blockId") String blockId,
            BlockActionRequest request) {
        service.acceptBlock(sessionId, blockId, request.scholarId());
        return Response.noContent().build();
    }

    @POST
    @Path("/{sessionId}/blocks/{blockId}/reject")
    public Response reject(
            @PathParam("sessionId") String sessionId,
            @PathParam("blockId") String blockId,
            BlockActionRequest request) {
        service.rejectBlock(sessionId, blockId, request.scholarId(), request.note());
        return Response.noContent().build();
    }

    @POST
    @Path("/{sessionId}/blocks/{blockId}/revise")
    public Response revise(
            @PathParam("sessionId") String sessionId,
            @PathParam("blockId") String blockId,
            RevisionRequest request) {
        service.requestRevision(sessionId, blockId, request.scholarId(), request.instruction());
        return Response.accepted().build();
    }

    private <T> T notFoundAsException(java.util.function.Supplier<T> query) {
        try {
            return query.get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
