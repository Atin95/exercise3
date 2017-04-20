package wdsr.exercise3.record.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wdsr.exercise3.record.Record;
import wdsr.exercise3.record.RecordInventory;

@Path("/records")
public class RecordResource {
	private static final Logger log = LoggerFactory.getLogger(RecordResource.class);
	
	@Inject
	private RecordInventory recordInventory;
	
	/**
	 * TODO Add methods to this class that will implement the REST API described below.
	 * Use methods on recordInventory to create/read/update/delete records. 
	 * RecordInventory instance (a CDI bean) will be injected at runtime.
	 * Content-Type used throughout this exercise is application/xml.
	 * API invocations that must be supported:
	 */
	
	/**
	 * * GET https://localhost:8091/records
	 * ** Returns a list of all records (as application/xml)
	 * ** Response status: HTTP 200
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllRecords()
	{
		List<Record> records = new ArrayList<>();
		records = recordInventory.getRecords();
		GenericEntity<List<Record>> ge = new GenericEntity<List<Record>>(records) {};
		return Response.ok(ge).build();
	}

	/**
	 * POST https://localhost:8091/records
	 * ** Creates a new record, returns ID of the new record.
	 * ** Consumes: record (as application/xml), ID must be null.
	 * ** Response status if ok: HTTP 201, Location header points to the newly created resource.
	 * ** Response status if submitted record has ID set: HTTP 400
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response addGivenRecord(Record r, @Context UriInfo ui)
	{
		if (r.getId() != null)
			return Response.status(Status.BAD_REQUEST).build();
		recordInventory.addRecord(r);
		UriBuilder ub = ui.getAbsolutePathBuilder();
		ub.path(Integer.toString(r.getId()));
		return Response.created(ub.build()).build();
	}
	
	/**
	 * * GET https://localhost:8091/records/{id}
	 * ** Returns an existing record (as application/xml)
	 * ** Response status if ok: HTTP 200
	 * ** Response status if {id} is not known: HTTP 404
	 */
	 /**
	 * * PUT https://localhost:8091/records/{id}
	 * ** Replaces an existing record in entirety.
	 * ** Submitted record (as application/xml) must have null ID or ID must be identical to {id} in the path.
	 * ** Response status if ok: HTTP 204
	 * ** Response status if {id} is not known: HTTP 404
	 */
	
	/**
	 * * DELETE https://localhost:8091/records/{id}
	 * ** Deletes an existing record.
	 * ** Response status if ok: HTTP 204
	 * ** Response status if {id} is not known: HTTP 404
	 */
}
