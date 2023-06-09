package controller;

import java.net.URI;
import java.util.ArrayList;

import dao.Plantio_ColheitaDAO;
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
import model.Plantio_Colheita;
import services.Plantio_ColheitaService;

/**
 * Classe que representa o recurso de Plantio_Colheita do sistema.
 *
 * Esta classe define as operações CRUD para os Plantio_Colheitas, incluindo listar, buscar por ID,
 * cadastrar, atualizar e deletar Plantio_Colheitas.
 * Os Plantio_Colheitas são registros de plantios associados a colheitas e vice-versa.
 *
 * @since 1.0
 * @version 1.0
 *
 * @see dao.Plantio_ColheitaDAO
 * @see services.Plantio_ColheitaService
 * @see model.Plantio_Colheita
 *
 * @author Raízes Solidárias
 */
@Path("/plantio_colheita")
public class Plantio_ColheitaResource {

	/**
	 * Recupera a lista de Plantio_Colheitas cadastrados no sistema.
	 *
	 * @return uma resposta contendo a lista de Plantio_Colheitas em formato JSON.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarPlantio_Colheitas() {
		Plantio_ColheitaDAO repositorio = new Plantio_ColheitaDAO();
		ArrayList<Plantio_Colheita> retorno = repositorio.listarPlantio_Colheitas();
		ResponseBuilder response = Response.ok();
		response.entity(retorno);
		return response.build();
	}

	/**
	 * Recupera um Plantio_Colheita pelo seu ID Plantio.
	 *
	 * @param id_plantio o ID do Plantio a ser buscado.
	 * @return uma resposta contendo o Plantio_Colheita em formato JSON.
	 */
	@GET
	@Path("/plantio/{id_plantio}")
	public Response exibirPlantio_ColheitaPorIdPlantio(@PathParam("id_plantio") int id_plantio) {
		Plantio_Colheita plantio_colheita_buscado = Plantio_ColheitaDAO.buscarPlantio_ColheitaPorIdPlantio(id_plantio);

		if (plantio_colheita_buscado != null) {
			ResponseBuilder response = Response.ok();
			response.entity(plantio_colheita_buscado);
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível encontrar o PLANTIO_COLHEITA de id_plantio: " + id_plantio + "\"}");
			return response.build();
		}
	}

	/**
	 * Recupera uma lista de Plantio_Colheita pelo seu ID Colheita.
	 *
	 * @param id_colheita o ID da Colheita a ser buscada.
	 * @return uma resposta contendo uma lista de Plantio_Colheita em formato JSON.
	 */
	@GET
	@Path("/colheita/{id_colheita}")
	public Response exibirPlantio_ColheitaPorIdColheita(@PathParam("id_colheita") int id_colheita) {
		ArrayList<Plantio_Colheita> plantio_colheita_buscado = Plantio_ColheitaDAO
				.buscarPlantio_ColheitaPorIdColheita(id_colheita);

		if (plantio_colheita_buscado != null) {
			ResponseBuilder response = Response.ok();
			response.entity(plantio_colheita_buscado);
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível encontrar o PLANTIO_COLHEITA de id_colheita: " + id_colheita + "\"}");
			return response.build();
		}
	}

	/**
	 * Recupera um Plantio_Colheita pelo seu ID Plantio e ID Colheita.
	 *
	 * @param id_plantio o ID do Plantio a ser buscado.
	 * @param id_colheita o ID da Colheita a ser buscada.
	 * @return uma resposta contendo o Plantio_Colheita em formato JSON.
	 */
	@GET
	@Path("/{id_plantio}/{id_colheita}")
	public Response exibirPlantio_ColheitaPorIds(@PathParam("id_plantio") int id_plantio,
			@PathParam("id_colheita") int id_colheita) {
		Plantio_Colheita plantio_colheita_buscado = Plantio_ColheitaDAO.buscarPlantio_ColheitaPorIds(id_plantio,
				id_colheita);

		if (plantio_colheita_buscado != null) {
			ResponseBuilder response = Response.ok();
			response.entity(plantio_colheita_buscado);
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404).entity("{\"error\": \"Não foi possível encontrar o PLANTIO_COLHEITA de id_plantio: " + id_plantio + " e id_colheita: " + id_colheita + "\"}");
			return response.build();
		}
	}

	/**
	 * Cadastra um novo Plantio_Colheita no sistema.
	 *
	 * @param plantio_colheita_novo o objeto Plantio_Colheita contendo os dados do Plantio_Colheita a ser cadastrado.
	 * @return uma resposta contendo o Plantio_Colheita cadastrado em formato JSON.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarPlantio_Colheita(@Valid Plantio_Colheita plantio_colheita_novo) {
		Plantio_Colheita resp = Plantio_ColheitaService.cadastrarPlantio_Colheita(plantio_colheita_novo);
		final URI plantio_colheitaUri = UriBuilder.fromResource(Plantio_ColheitaResource.class)
				.path("/plantio_colheita/{id}").build(resp);
		ResponseBuilder response = Response.created(plantio_colheitaUri);
		response.entity(resp);
		return response.build();
	}

	/**
	 * Atualiza os dados de um Plantio_Colheita existente no sistema.
	 *
	 * @param id_plantio_novo 	o ID do Plantio a ser atualizado.
	 * @param id_plantio_antigo	o ID do Plantio que será atualizado.
	 * @param id_colheita 		o ID da Colheita que será atualizada.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@PUT
	@Path("/{id_colheita}/{id_plantio_antigo}/{id_plantio_novo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizarPlantio_Colheita(@PathParam("id_plantio_novo") int id_plantio_novo,
			@PathParam("id_plantio_antigo") int id_plantio_antigo, @PathParam("id_colheita") int id_colheita) {
		if (Plantio_ColheitaService.atualizarPlantio_Colheita(id_plantio_novo, id_plantio_antigo, id_colheita)) {
			return Response.ok().build();
		} else {
			return Response.status(404)
					.entity("{\"error\": \"Não foi possível atualizar o PLANTIO_COLHEITA de id_colheita: " + id_colheita
							+ " e id_plantio: " + id_plantio_antigo
							+ ". O id da URI e o ID do objeto JSON devem ser iguais e deve existir no banco de dados.\"}")
					.build();
		}
	}

	/**
	 * Remove um Plantio_Colheita do sistema.
	 *
	 * @param id_plantio 	o ID do Plantio a ser removido.
	 * @param id_colheita	o ID da Colheita a ser removida.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@DELETE
	@Path("/{id_plantio}/{id_colheita}")
	public Response deletarPlantio_Colheita(@PathParam("id_plantio") int id_plantio,
			@PathParam("id_colheita") int id_colheita) {
		if (Plantio_ColheitaService.deletarPlantio_Colheita(id_plantio, id_colheita)) {
			ResponseBuilder response = Response.noContent();
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível remover o PLANTIO_COLHEITA de id_plantio: " + id_plantio
							+ " e id_colheita: " + id_colheita + "\"}");
			return response.build();
		}
	}
}
