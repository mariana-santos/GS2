package controller;

import java.net.URI;
import java.util.ArrayList;

import dao.ReceptorDAO;
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
import model.Receptor;
import services.ReceptorService;

/**
 * Classe que representa o recurso de Receptor (Usuario) do sistema.
 *
 * Esta classe define as operações CRUD para os Receptores, incluindo listar, buscar por ID,
 * cadastrar, atualizar e deletar Receptores.
 *
 * @since 1.0
 * @version 1.0
 *
 * @see dao.ReceptorDAO
 * @see services.ReceptorService
 * @see model.Receptor
 * @see model.Usuario
 *
 * @author Raízes Solidárias
 */
@Path("/receptor")
public class ReceptorResource {

	/**
	 * Recupera a lista de Receptores cadastrados no sistema.
	 *
	 * @return uma resposta contendo a lista de Receptores em formato JSON.
	 */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarReceptores() {
        ReceptorDAO repositorio = new ReceptorDAO();
        ArrayList<Receptor> retorno = repositorio.listarReceptores();
        ResponseBuilder response = Response.ok();
        response.entity(retorno);
        return response.build();
    }

    /**
	 * Recupera um Receptor pelo seu ID.
	 *
	 * @param id_usuario o ID do Receptor (Usuario) a ser buscado.
	 * @return uma resposta contendo o Receptor em formato JSON.
	 */
    @GET
    @Path("/{id}")
    public Response exibirReceptorPorId(@PathParam("id") int id_usuario) {
        Receptor receptor_buscado = ReceptorDAO.buscarReceptorPorId(id_usuario);

        if (receptor_buscado != null) {
            ResponseBuilder response = Response.ok();
            response.entity(receptor_buscado);
            return response.build();
        } else {
            ResponseBuilder response = Response.status(404)
                    .entity("{\"error\": \"Não foi possível encontrar o RECEPTOR de id_usuario: " + id_usuario + "\"}");
            return response.build();
        }
    }

    /**
	 * Cadastra um novo Receptor no sistema.
	 *
	 * @param receptor_novo     O objeto Receptor contendo os dados do Receptor (Usuario) a ser cadastrado.
	 * @return uma resposta contendo o Receptor cadastrado em formato JSON.
	 */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarReceptor(@Valid Receptor receptor_novo) {
        Receptor resp = ReceptorService.cadastrarReceptor(receptor_novo);
        final URI receptorUri = UriBuilder.fromResource(ReceptorResource.class).path("/receptor/{id}")
                .build(resp.getId_usuario());
        ResponseBuilder response = Response.created(receptorUri);
        response.entity(resp);
        return response.build();
    }

    /**
	 * Atualiza os dados de um Receptor existente no sistema.
	 *
	 * @param id_usuario o ID do Receptor (Usuario) a ser atualizado.
	 * @param receptor o objeto Receptor contendo os novos dados do Receptor.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarReceptor(@PathParam("id") int id_usuario, @Valid Receptor receptor) {
        if (ReceptorService.atualizarReceptor(id_usuario, receptor)) {
            return Response.ok().build();
        } else {
            ResponseBuilder response = Response.status(404)
                    .entity("{\"error\": \"Não foi possível atualizar o RECEPTOR de id_usuario: " + id_usuario
                            + ". O id da URI e o ID do objeto JSON devem ser iguais e deve existir no banco de dados.\"}");
            return response.build();
        }
    }

    /**
	 * Remove um Receptor do sistema.
	 *
	 * @param id_usuario o ID do Receptor (Usuario) a ser removido.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
    @DELETE
    @Path("/{id}")
    public Response deletarReceptor(@PathParam("id") int id_usuario) {
        if (ReceptorService.deletarReceptor(id_usuario)) {
            ResponseBuilder response = Response.noContent();
            return response.build();
        } else {
            System.out.println("Não foi possível remover o RECEPTOR: " + id_usuario);
            ResponseBuilder response = Response.status(404)
                    .entity("{\"error\": \"Não foi possível remover o RECEPTOR de id_usuario: " + id_usuario + "\"}");
            return response.build();
        }
    }

    /**
	 * Valida o login de um receptor.
	 *
	 * @param receptorLogin O objeto Receptor contendo o email e a senha do receptor a serem validados.
	 * @return A resposta HTTP com o status e o objeto Receptor logado em caso de sucesso,
	 *         ou uma resposta HTTP de erro com uma mensagem em caso de falha na validação do login.
	 */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validarLoginReceptor(Receptor receptorLogin) {
        String email_usuario = receptorLogin.getEmail_usuario();
        String senha_usuario = receptorLogin.getSenha_usuario();

        try {
            Receptor receptor_logado = ReceptorService.validarLoginReceptor(email_usuario, senha_usuario);

            if (receptor_logado != null) {
                ResponseBuilder response = Response.ok();
                response.entity(receptor_logado);
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
