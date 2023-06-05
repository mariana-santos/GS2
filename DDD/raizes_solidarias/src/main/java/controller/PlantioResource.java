package controller;

import java.net.URI;
import java.util.ArrayList;

import dao.PlantioDAO;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.UriBuilder;
import model.Plantio;
import services.PlantioService;

@Path("/plantio")
public class PlantioResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPlantios() {
        PlantioDAO repositorio = new PlantioDAO();
        ArrayList<Plantio> retorno = repositorio.listarPlantios();
        ResponseBuilder response = Response.ok();
        response.entity(retorno);
        return response.build();
    }

    @GET
    @Path("/{id}")
    public Response exibirPlantioPorId(@PathParam("id") int id_plantio) {
        Plantio plantio_buscado = PlantioDAO.buscarPlantioPorId(id_plantio);

        if (plantio_buscado != null) {
            ResponseBuilder response = Response.ok();
            response.entity(plantio_buscado);
            return response.build();
        } else {
            ResponseBuilder response = Response.status(404)
                    .entity("{\"error\": \"Não foi possível encontrar o PLANTIO de id_plantio: " + id_plantio + "\"}");
            return response.build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarPlantio(@Valid Plantio plantio_novo) {
        Plantio resp = PlantioService.cadastrarPlantio(plantio_novo);
        final URI plantioUri = UriBuilder.fromResource(PlantioResource.class).path("/plantio/{id}")
                .build(resp.getId_plantio());
        ResponseBuilder response = Response.created(plantioUri);
        response.entity(resp);
        return response.build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public static Response cadastrarPlantios(ArrayList<Plantio> plantios_novos) {
        ArrayList<Plantio> retorno = PlantioDAO.cadastrarPlantios(plantios_novos);
        ResponseBuilder response = Response.ok();
        response.entity(retorno);
        return response.build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarPlantio(@PathParam("id") int id_plantio, @Valid Plantio plantio) {
        if (PlantioService.atualizarPlantio(id_plantio, plantio)) {
            return Response.ok().build();
        } else {
            return Response.status(404)
                    .entity("{\"error\": \"Não foi possível atualizar o PLANTIO de id_plantio: " + id_plantio
                            + ". O id da URI e o ID do objeto JSON devem ser iguais e deve existir no banco de dados.\"}")
                    .build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response deletarPlantio(@PathParam("id") int id_plantio) {
        if (PlantioService.deletarPlantio(id_plantio)) {
            ResponseBuilder response = Response.noContent();
            return response.build();
        } else {
            System.out.println("Não foi possível remover o PLANTIO: " + id_plantio);
            ResponseBuilder response = Response.status(404)
                    .entity("{\"error\": \"Não foi possível remover o PLANTIO de id_plantio: " + id_plantio + "\"}");
            return response.build();
        }
    }
}
