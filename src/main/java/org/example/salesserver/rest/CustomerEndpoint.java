package org.example.salesserver.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.example.salesserver.model.Customer;
import org.example.salesserver.repository.CustomerRepository;

/**
 * 
 */
@Stateless
@Path("/customers")
public class CustomerEndpoint {
	
	@Inject
	private CustomerRepository repository;

	@POST
	@Consumes("application/json")
	public Response create(Customer entity) {
		repository.persist(entity);
		return Response.created(UriBuilder.fromResource(CustomerEndpoint.class).path(String.valueOf(entity.getCustomerID())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") String id) {
		Customer entity = repository.findById(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		repository.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") String id) {
		Customer entity = repository.findById(id);

		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Customer> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
		final List<Customer> results = repository.listAll(startPosition, maxResult);

		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(Customer entity) {
		entity = repository.merge(entity);
		return Response.noContent().build();
	}
}