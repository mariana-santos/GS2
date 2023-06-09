package controller;

import java.net.URI;
import java.util.ArrayList;

import dao.DoadorDAO;
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
import model.Doador;
import services.DoadorService;

/**
 * Classe que representa o recurso de Doador (Usuario) do sistema.
 *
 * Esta classe define as operações CRUD para os Doadores, incluindo listar, buscar por ID,
 * cadastrar, atualizar e deletar Doadores.
 *
 * @since 1.0
 * @version 1.0
 *
 * @see dao.DoadorDAO
 * @see services.DoadorService
 * @see model.Doador
 * @see model.Usuario
 * @author Raízes Solidárias
 */
@Path("/doador")
public class DoadorResource {
	
	/**
	 * Recupera a lista de Doadores cadastrados no sistema.
	 *
	 * @return uma resposta contendo a lista de Doadores em formato JSON.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarDoadores() {
		DoadorDAO repositorio = new DoadorDAO();
		ArrayList<Doador> retorno = repositorio.listarDoadores();
		ResponseBuilder response = Response.ok();
		response.entity(retorno);
		return response.build();
	}
	
	/**
	 * Recupera um Doador pelo seu ID.
	 *
	 * @param id_usuario o ID do Doador (Usuario) a ser buscado.
	 * @return uma resposta contendo o Doador em formato JSON.
	 */
	@GET
	@Path("/{id}")
	public Response exibirDoadorPorId(@PathParam("id") int id_usuario) {
		Doador doador_buscado = DoadorDAO.buscarDoadorPorId(id_usuario);

		if (doador_buscado != null) {
			ResponseBuilder response = Response.ok();
			response.entity(doador_buscado);
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível encontrar o DOADOR de id_usuario: " + id_usuario + "\"}");
			return response.build();
		}
	}
	
	/**
	 * Cadastra um novo Doador no sistema.
	 *
	 * @param doador_novo o objeto Doador contendo os dados do Doador (Usuario) a ser cadastrado.
	 * @return uma resposta contendo o Doador cadastrado em formato JSON.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarDoador(@Valid Doador doador_novo) {
		Doador resp = DoadorService.cadastrarDoador(doador_novo);
		final URI doadorUri = UriBuilder.fromResource(DoadorResource.class).path("/doador/{id}")
				.build(resp.getId_usuario());
		ResponseBuilder response = Response.created(doadorUri);
		response.entity(resp);
		return response.build();
	}
	
	/**
	 * Atualiza os dados de um Doador existente no sistema.
	 *
	 * @param id_usuario o ID do Doador (Usuario) a ser atualizado.
	 * @param doador o objeto Doador contendo os novos dados do Doador.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@PUT
	@Path("/{id_usuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizarDoador(@PathParam("id_usuario") int id_usuario, @Valid Doador doador) {
		if (DoadorService.atualizarDoador(id_usuario, doador)) {
			return Response.ok().build();
		} else {
			return Response.status(404)
					.entity("{\"error\": \"Não foi possível atualizar o DOADOR de id_usuario: " + id_usuario
							+ ". O id da URI e o ID do objeto JSON devem ser iguais e deve existir no banco de dados.\"}")
					.build();
		}

	}
	
	/**
	 * Remove um Doador do sistema.
	 *
	 * @param id_usuario o ID do Doador (Usuario) a ser removido.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@DELETE
	@Path("/{id}")
	public Response deletarDoador(@PathParam("id") int id_usuario) {
		if (DoadorService.deletarDoador(id_usuario)) {
			ResponseBuilder response = Response.noContent();
			return response.build();
		} else {
			System.out.println("Não foi possível remover o DOADOR: " + id_usuario);
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível remover o DOADOR de id_usuario: " + id_usuario + "\"}");
			return response.build();
		}
	}	
	
	/**
	 * Valida o login do doador com base no email e senha fornecidos.
	 *
	 * @param doadorLogin O objeto doador contendo o email e senha do usuário.
	 * @return Uma resposta contendo o doador logado em caso de sucesso de autenticação. Caso contrário, retorna uma resposta de erro com status 401.
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validarLoginDoador(Doador doadorLogin) {
		String email_usuario = doadorLogin.getEmail_usuario();
		String senha_usuario = doadorLogin.getSenha_usuario();

		try {
			Doador doador_logado = DoadorService.validarLoginDoador(email_usuario, senha_usuario);

			if (doador_logado != null) {
				ResponseBuilder response = Response.ok();
				response.entity(doador_logado);
				return response.build();
			} else {
				return Response.status(401).entity("{\"error\": \"Email e/ou senha incorretos.\"}").build();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return Response.status(401).entity("{\"error\": \"Email e/ou senha incorretos.\"}").build();
		}
	}
}
