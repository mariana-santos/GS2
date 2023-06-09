package controller;

import java.net.URI;
import java.util.ArrayList;

import dao.Plantio_VoluntarioDAO;
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
import model.Plantio_Voluntario;
import services.Plantio_VoluntarioService;

/**
 * Classe que representa o recurso de Plantio_Voluntario do sistema.
 *
 * Esta classe define as operações CRUD para os Plantio_Voluntarios, incluindo listar, buscar por ID,
 * cadastrar, atualizar e deletar Plantio_Voluntarios.
 * Os Plantio_Voluntarios são registros de plantios associados a voluntarios e vice-versa.
 *
 * @since 1.0
 * @version 1.0
 *
 * @see dao.Plantio_VoluntarioDAO
 * @see services.Plantio_VoluntarioService
 * @see model.Plantio_Voluntario
 *
 * @author Raízes Solidárias
 */
@Path("/plantio_voluntario")
public class Plantio_VoluntarioResource {
	
	/**
	 * Recupera a lista de Plantio_Voluntarios cadastrados no sistema.
	 *
	 * @return uma resposta contendo a lista de Plantio_Voluntarios em formato JSON.
	 */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPlantio_Voluntarios() {
        Plantio_VoluntarioDAO repositorio = new Plantio_VoluntarioDAO();
        ArrayList<Plantio_Voluntario> retorno = repositorio.listarPlantio_Voluntarios();
        ResponseBuilder response = Response.ok();
        response.entity(retorno);
        return response.build();
    }

    /**
	 * Recupera um Plantio_Voluntario pelo seu ID Plantio.
	 *
	 * @param id_plantio o ID do Plantio a ser buscado.
	 * @return uma resposta contendo uma lista de Plantio_Voluntario em formato JSON.
	 */
    @GET
    @Path("/plantio/{id_plantio}")
    public Response exibirPlantio_VoluntarioPorIdPlantio(@PathParam("id_plantio") int id_plantio) {
        ArrayList<Plantio_Voluntario> plantio_voluntario_buscado = Plantio_VoluntarioDAO
                .buscarPlantio_VoluntarioPorIdPlantio(id_plantio);

        if (plantio_voluntario_buscado != null) {
            ResponseBuilder response = Response.ok();
            response.entity(plantio_voluntario_buscado);
            return response.build();
        } else {
            ResponseBuilder response = Response.status(404)
                    .entity("{\"error\": \"Não foi possível encontrar o PLANTIO_VOLUNTARIO de id_plantio: "
                            + id_plantio + "\"}");
            return response.build();
        }
    }

    /**
	 * Recupera um Plantio_Voluntario pelo seu ID Voluntário (Usuário).
	 *
	 * @param id_usuario o ID do Voluntário (Usuário) a ser buscado.
	 * @return uma resposta contendo uma lista de Plantio_Voluntario em formato JSON.
	 */
    @GET
    @Path("/voluntario/{id_usuario}")
    public Response exibirPlantio_VoluntarioPorIdUsuario(@PathParam("id_usuario") int id_usuario) {
        ArrayList<Plantio_Voluntario> plantio_voluntario_buscado = Plantio_VoluntarioDAO
                .buscarPlantio_VoluntarioPorIdUsuario(id_usuario);

        if (plantio_voluntario_buscado != null) {
            ResponseBuilder response = Response.ok();
            response.entity(plantio_voluntario_buscado);
            return response.build();
        } else {
            ResponseBuilder response = Response.status(404)
                    .entity("{\"error\": \"Não foi possível encontrar o PLANTIO_VOLUNTARIO de id_usuario: "
                            + id_usuario + "\"}");
            return response.build();
        }
    }

    /**
	 * Recupera um Plantio_Voluntario pelo seu ID Plantio e ID Voluntario (Usuario).
	 *
	 * @param id_plantio o ID do Plantio a ser buscado.
	 * @param id_usuario o ID do Voluntario (Usuario) a ser buscado.
	 * @return uma resposta contendo o Plantio_Voluntario em formato JSON.
	 */
    @GET
    @Path("/{id_plantio}/{id_usuario}")
    public Response exibirPlantio_VoluntarioPorIds(@PathParam("id_plantio") int id_plantio,
            @PathParam("id_usuario") int id_usuario) {
        Plantio_Voluntario plantio_voluntario_buscado = Plantio_VoluntarioDAO.buscarPlantio_VoluntarioPorIds(
                id_plantio, id_usuario);

        if (plantio_voluntario_buscado != null) {
            ResponseBuilder response = Response.ok();
            response.entity(plantio_voluntario_buscado);
            return response.build();
        } else {
            ResponseBuilder response = Response.status(404).entity("{\"error\": \"Não foi possível encontrar o PLANTIO_VOLUNTARIO de id_plantio: "
                    + id_plantio + " e id_usuario: " + id_usuario + "\"}");
            return response.build();
        }
    }

    /**
	 * Cadastra um novo Plantio_Voluntario no sistema.
	 *
	 * @param plantio_voluntario_novo o objeto Plantio_Voluntario contendo os dados do Plantio_Voluntario a ser cadastrado.
	 * @return uma resposta contendo o Plantio_Voluntario cadastrado em formato JSON.
	 */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarPlantio_Voluntario(@Valid Plantio_Voluntario plantio_voluntario_novo) {
        Plantio_Voluntario resp = Plantio_VoluntarioService
                .cadastrarPlantio_Voluntario(plantio_voluntario_novo);
        final URI plantio_voluntarioUri = UriBuilder.fromResource(Plantio_VoluntarioResource.class)
                .path("/plantio_voluntario/{id}").build(resp);
        ResponseBuilder response = Response.created(plantio_voluntarioUri);
        response.entity(resp);
        return response.build();
    }

    /**
	 * Atualiza os dados de um Plantio_Voluntario existente no sistema.
	 *
	 * @param id_plantio_novo	o ID do Plantio a ser atualizado.
	 * @param id_plantio_antigo o ID do Plantio que será atualizado.
	 * @param id_usuario_novo 	o ID do Usuario a ser atualizado.
	 * @param id_usuario_antigo	o ID do Usuario que será atualizado.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
    @PUT
    @Path("/{id_plantio_antigo}-{id_usuario_antigo}/{id_plantio_novo}-{id_usuario_novo}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarPlantio_Voluntario(@PathParam("id_plantio_novo") int id_plantio_novo,
            @PathParam("id_plantio_antigo") int id_plantio_antigo,
            @PathParam("id_usuario_novo") int id_usuario_novo,
            @PathParam("id_usuario_antigo") int id_usuario_antigo) {
        if (Plantio_VoluntarioService.atualizarPlantio_Voluntario(id_plantio_novo, id_plantio_antigo,
                id_usuario_novo, id_usuario_antigo)) {
            return Response.ok().build();
        } else {
            return Response.status(404)
                    .entity("{\"error\": \"Não foi possível atualizar o PLANTIO_VOLUNTARIO de id_plantio: "
                            + id_plantio_antigo + " e id_usuario: " + id_usuario_antigo
                            + ". O id da URI e o ID do objeto JSON devem ser iguais e deve existir no banco de dados.\"}")
                    .build();
        }

    }

    /**
	 * Remove um Plantio_Voluntario do sistema.
	 *
	 * @param id_plantio 	o ID do Plantio a ser removido.
	 * @param id_usuario	o ID da Voluntario a ser removida.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
    @DELETE
    @Path("/{id_plantio}/{id_usuario}")
    public Response deletarPlantio_Voluntario(@PathParam("id_plantio") int id_plantio,
            @PathParam("id_usuario") int id_usuario) {
        if (Plantio_VoluntarioService.deletarPlantio_Voluntario(id_plantio, id_usuario)) {
            ResponseBuilder response = Response.noContent();
            return response.build();
        } else {
            System.out.println(
                    "Não foi possível remover o PLANTIO_VOLUNTARIO de id_plantio: " + id_plantio + " e id_usuario: "
                            + id_usuario);
            ResponseBuilder response = Response.status(404)
                    .entity("{\"error\": \"Não foi possível remover o PLANTIO_VOLUNTARIO de id_plantio: " + id_plantio
                            + " e id_usuario: " + id_usuario + "\"}");
            return response.build();
        }
    }
}
