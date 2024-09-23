package fr.pantheonsorbonne.miage.resources;

import fr.pantheonsorbonne.miage.dto.Booking;
import fr.pantheonsorbonne.miage.dto.Quota;
import fr.pantheonsorbonne.miage.service.InsufficientQuotaException;
import fr.pantheonsorbonne.miage.service.NoQuotaForVendorAndConcertException;
import fr.pantheonsorbonne.miage.service.QuotaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("vendor/{vendorId}/quota")
public class QuotaResource {


    @Inject
    QuotaService quotaService;

    @Path("{concertId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Quota getQuota(@PathParam("vendorId") int vendorId, @PathParam("concertId") int concertId) {
        return quotaService.getQuota(vendorId,concertId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Quota> getAllQuotasForVendor(@PathParam("vendorId") int vendorId) {
        return quotaService.getQuotaForVendor(vendorId);
    }

    @PUT
    @Path("{concertId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response consumeQuotaForConcert(@PathParam("vendorId") int vendorId, @PathParam("concertId") int concertId, Booking booking) {

        try {
            quotaService.consumeQuota(vendorId, concertId, booking.seating(), booking.standing());
        } catch (NoQuotaForVendorAndConcertException e) {
            throw new WebApplicationException("no such quota for vendor and concert", 404);
        } catch (InsufficientQuotaException e) {
            throw new WebApplicationException("insufficient quota", 400);

        }
        return Response.noContent().build();

    }

}
