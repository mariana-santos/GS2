package controller;

import java.net.URI;
import java.util.ArrayList;

import dao.AgendamentoDAO;
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
import model.Agendamento;
import services.AgendamentoService;

/**
 * Classe que representa o recurso de Agendamento do sistema.
 *
 * Esta classe define as operações CRUD para os Agendamentos, incluindo listar, buscar por ID,
 * cadastrar, atualizar e deletar Agendamentos.
 *
 * @since 1.0
 * @version 1.0
 *
 * @see dao.AgendamentoDAO
 * @see services.AgendamentoService
 * @see model.Agendamento
 *
 * @author Raízes Solidárias
 */

@Path("/agendamento")
public class AgendamentoResource {
	
	/**
	 * Recupera a lista de Agendamentos cadastrados no sistema.
	 *
	 * @return uma resposta contendo a lista de Agendamentos em formato JSON.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarAgendamentos() {
		AgendamentoDAO repositorio = new AgendamentoDAO();
		ArrayList<Agendamento> retorno = repositorio.listarAgendamentos();
		ResponseBuilder response = Response.ok();
		response.entity(retorno);
		return response.build();
	}
	
	/**
	 * Recupera um Agendamento pelo seu ID.
	 *
	 * @param id_agendamento o ID do Agendamento a ser buscado.
	 * @return uma resposta contendo o Agendamento em formato JSON.
	 */
	@GET
	@Path("/{id}")
	public Response exibirAgendamentoPorId(@PathParam("id") int id_agendamento) {
		Agendamento agendamento_buscado = AgendamentoDAO.buscarAgendamentoPorId(id_agendamento);

		if (agendamento_buscado != null) {
			ResponseBuilder response = Response.ok();
			response.entity(agendamento_buscado);
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível encontrar o AGENDAMENTO de id_agendamento: " + id_agendamento + "\"}");
			return response.build();
		}
	}
	
	/**
	 * Recupera a lista de Agendamentos por ID de Usuário no sistema.
	 *
	 * @param id_usuario O ID do usuário para o qual deseja-se obter os agendamentos (vem do path da URL)
	 * @return Uma resposta contendo a lista de Agendamentos por ID de Usuário em formato JSON.
	 */
	@GET
	@Path("/usuario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarAgendamentosPorIdUsuario(@PathParam("id") int id_usuario) {
		AgendamentoDAO repositorio = new AgendamentoDAO();
		ArrayList<Agendamento> retorno = repositorio.listarAgendamentosPorIdUsuario(id_usuario);
		ResponseBuilder response = Response.ok();
		response.entity(retorno);
		return response.build();
	}
	
	/**
	 * Cadastra um novo Agendamento no sistema.
	 *
	 * @param agendamento_novo o objeto Agendamento contendo os dados do Agendamento a ser cadastrado.
	 * @return uma resposta contendo o Agendamento cadastrado em formato JSON.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarAgendamento(@Valid Agendamento agendamento_novo) {
		Agendamento resp = AgendamentoService.cadastrarAgendamento(agendamento_novo);
		final URI agendamentoUri = UriBuilder.fromResource(AgendamentoResource.class).path("/agendamento/{id}")
				.build(resp.getId_agendamento());
		ResponseBuilder response = Response.created(agendamentoUri);
		response.entity(resp);
		return response.build();
	}
	
	/**
	 * Atualiza os dados de um Agendamento existente no sistema.
	 *
	 * @param id_agendamento o ID do Agendamento a ser atualizado.
	 * @param agendamento o objeto Agendamento contendo os novos dados do Agendamento.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizarAgendamento(@PathParam("id") int id_agendamento, @Valid Agendamento agendamento) {
		if (AgendamentoService.atualizarAgendamento(id_agendamento, agendamento)) {
			return Response.ok().build();
		} else {
			return Response.status(404)
					.entity("{\"error\": \"Não foi possível atualizar o AGENDAMENTO de id_agendamento: " + id_agendamento
							+ ". O id da URI e o ID do objeto JSON devem ser iguais e deve existir no banco de dados.\"}")
					.build();
		}

	}
	
	/**
	 * Remove um Agendamento do sistema.
	 *
	 * @param id_agendamento o ID do Agendamento a ser removido.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@DELETE
	@Path("/{id}")
	public Response deletarAgendamento(@PathParam("id") int id_agendamento) {
		if (AgendamentoService.deletarAgendamento(id_agendamento)) {
			ResponseBuilder response = Response.noContent();
			return response.build();
		} else {
			System.out.println("{\"error\": \"Não foi possível remover o AGENDAMENTO: " + id_agendamento + "\"}");
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível remover o AGENDAMENTO de id_agendamento: " + id_agendamento + "\"}");
			return response.build();
		}
	}
}