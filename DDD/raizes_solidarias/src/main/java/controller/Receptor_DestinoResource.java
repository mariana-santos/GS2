package controller;

import java.net.URI;
import java.util.ArrayList;

import dao.Receptor_DestinoDAO;
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
import model.Receptor_Destino;
import services.Receptor_DestinoService;

/**
 * Classe que representa o recurso de Receptor_Destino do sistema.
 *
 * Esta classe define as operações CRUD para os Receptor_Destinos, incluindo listar, buscar por ID,
 * cadastrar, atualizar e deletar Receptor_Destinos.
 * Os Receptor_Destinos são registros de plantios associados a voluntarios e vice-versa.
 *
 * @since 1.0
 * @version 1.0
 *
 * @see dao.Receptor_DestinoDAO
 * @see services.Receptor_DestinoService
 * @see model.Receptor_Destino
 *
 * @author Raízes Solidárias
 */

@Path("/receptor_destino")
public class Receptor_DestinoResource {
	
	/**
	 * Recupera a lista de Receptor_Destinos cadastrados no sistema.
	 *
	 * @return uma resposta contendo a lista de Receptor_Destinos em formato JSON.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarReceptor_Destinos() {
		Receptor_DestinoDAO repositorio = new Receptor_DestinoDAO();
		ArrayList<Receptor_Destino> retorno = repositorio.listarReceptor_Destinos();
		ResponseBuilder response = Response.ok();
		response.entity(retorno);
		return response.build();
	}
	
	/**
	 * Recupera um Receptor_Destino pelo seu ID Receptor e ID Destino (Usuario).
	 *
	 * @param id_usuario o ID do Receptor a ser buscado.
	 * @param id_destino o ID do Destino (Usuario) a ser buscado.
	 * @return uma resposta contendo o Receptor_Destino em formato JSON.
	 */
	@GET
	@Path("/{id_usuario}/{id_destino}")
	public Response exibirReceptor_DestinoPorIds(@PathParam("id_usuario") int id_usuario, @PathParam("id_destino") int id_destino) {
		Receptor_Destino receptor_destino_buscado = Receptor_DestinoDAO.buscarReceptor_DestinoPorIds(id_usuario, id_destino);

		if (receptor_destino_buscado != null) {
			ResponseBuilder response = Response.ok();
			response.entity(receptor_destino_buscado);
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404)
					.entity("Não foi possível encontrar o RECEPTOR_DESTINO de id_usuario: " + id_usuario + " e id_destino: " + id_destino);
			return response.build();
		}
	}
	
	/**
	 * Cadastra um novo Receptor_Destino no sistema.
	 *
	 * @param receptor_destino_novo o objeto Receptor_Destino contendo os dados do Receptor_Destino a ser cadastrado.
	 * @return uma resposta contendo o Receptor_Destino cadastrado em formato JSON.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarReceptor_Destino(@Valid Receptor_Destino receptor_destino_novo) {
		Receptor_Destino resp = Receptor_DestinoService.cadastrarReceptor_Destino(receptor_destino_novo);
		final URI receptor_destinoUri = UriBuilder.fromResource(Receptor_DestinoResource.class).path("/receptor_destino/{id}")
				.build(resp);
		ResponseBuilder response = Response.created(receptor_destinoUri);
		response.entity(resp);
		return response.build();
	}
	
	/**
	 * Atualiza os dados de um Receptor_Destino existente no sistema.
	 *
	 * @param id_usuario_novo	o ID do Receptor a ser atualizado.
	 * @param id_usuario_antigo o ID do Receptor que será atualizado.
	 * @param id_destino_novo 	o ID do Usuario a ser atualizado.
	 * @param id_destino_antigo	o ID do Usuario que será atualizado.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@PUT
	@Path("/{id_usuario_antigo}/{id_destino_antigo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizarReceptor_Destino(int id_usuario_novo, @PathParam("id_usuario_antigo") int id_usuario_antigo, int id_destino_novo, @PathParam("id_destino_antigo") int id_destino_antigo) {
		if (Receptor_DestinoService.atualizarReceptor_Destino(id_usuario_novo, id_usuario_antigo, id_destino_novo, id_destino_antigo)) {
			return Response.ok().build();
		} else {
			return Response.status(404)
					.entity("Não foi possível atualizar o RECEPTOR_DESTINO de id_usuario: " + id_usuario_antigo + " e id_destino: " + id_destino_antigo
							+ ". O id da URI e o ID do objeto JSON devem ser iguais e deve existir no banco de dados.")
					.build();
		}

	}
	
	/**
	 * Remove um Receptor_Destino do sistema.
	 *
	 * @param id_usuario 	o ID do Receptor a ser removido.
	 * @param id_destino	o ID do Destino a ser removido.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@DELETE
	@Path("/{id_usuario}/{id_destino}")
	public Response deletarReceptor_Destino(@PathParam("id_usuario") int id_usuario, @PathParam("id_destino") int id_destino) {
		if (Receptor_DestinoService.deletarReceptor_Destino(id_usuario, id_destino)) {
			ResponseBuilder response = Response.noContent();
			return response.build();
		} else {
			System.out.println("Não foi possível remover o RECEPTOR_DESTINO de id_usuario: " + id_usuario + " e id_destino: " + id_destino);
			ResponseBuilder response = Response.status(404)
					.entity("Não foi possível remover o RECEPTOR_DESTINO de id_usuario: " + id_usuario + " e id_destino: " + id_destino);
			return response.build();
		}
	}
}