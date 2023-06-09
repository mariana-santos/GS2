package controller;

import java.net.URI;
import java.util.ArrayList;

import dao.UsuarioDAO;
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
import model.Usuario;
import services.UsuarioService;

/**
 * Classe que representa o recurso de Usuario do sistema.
 *
 * Esta classe define as operações CRUD para os Usuarios, incluindo listar, buscar por ID,
 * cadastrar, atualizar e deletar Usuarios.
 *
 * @since 1.0
 * @version 1.0
 *
 * @see dao.UsuarioDAO
 * @see services.UsuarioService
 * @see model.Usuario
 *
 * @author Raízes Solidárias
 */

@Path("/usuario")
public class UsuarioResource {
	
	/**
	 * Recupera a lista de Usuarios cadastrados no sistema.
	 *
	 * @return uma resposta contendo a lista de Usuarios em formato JSON.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarUsuarios() {
		UsuarioDAO repositorio = new UsuarioDAO();
		ArrayList<Usuario> retorno = repositorio.listarUsuarios();
		ResponseBuilder response = Response.ok();
		response.entity(retorno);
		return response.build();
	}
	
	/**
	 * Recupera um Usuario pelo seu ID.
	 *
	 * @param id_usuario o ID do Usuario a ser buscado.
	 * @return uma resposta contendo o Usuario em formato JSON.
	 */
	@GET
	@Path("/{id}")
	public Response exibirUsuarioPorId(@PathParam("id") int id_usuario) {
		Usuario usuario_buscado = UsuarioDAO.buscarUsuarioPorId(id_usuario);

		if (usuario_buscado != null) {
			ResponseBuilder response = Response.ok();
			response.entity(usuario_buscado);
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível encontrar o USUARIO de id_usuario: " + id_usuario + "\"}");
			return response.build();
		}
	}
	
	/**
	 * Cadastra um novo Usuario no sistema.
	 *
	 * @param usuario_novo o objeto Usuario contendo os dados do Usuario a ser cadastrado.
	 * @return uma resposta contendo o Usuario cadastrado em formato JSON.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarUsuario(@Valid Usuario usuario_novo) {
		Usuario resp = UsuarioService.cadastrarUsuario(usuario_novo);

        if (resp != null) {
            final URI usuarioUri = UriBuilder.fromResource(UsuarioResource.class).path("/usuario/{id}")
                    .build(resp.getId_usuario());
            ResponseBuilder response = Response.created(usuarioUri);
            response.entity(resp);
            return response.build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Não foi possível cadastrar o USUARIO.\"}")
                    .build();
        }
    }
	
	/**
	 * Atualiza os dados de um Usuario existente no sistema.
	 *
	 * @param id_usuario o ID do Usuario a ser atualizado.
	 * @param usuario o objeto Usuario contendo os novos dados do Usuario.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizarUsuario(@PathParam("id") int id_usuario, @Valid Usuario usuario) {
		if (UsuarioService.atualizarUsuario(id_usuario, usuario)) {
			return Response.ok().build();
		} else {
			return Response.status(404)
					.entity("{\"error\": \"Não foi possível atualizar o USUARIO de id_usuario: " + id_usuario
							+ ". O id da URI e o ID do objeto JSON devem ser iguais e deve existir no banco de dados.\"}")
					.build();
		}

	}
	
	/**
	 * Remove um Usuario do sistema.
	 *
	 * @param id_usuario o ID do Usuario a ser removido.
	 * @return uma resposta indicando o sucesso ou falha da operação.
	 */
	@DELETE
	@Path("/{id}")
	public Response deletarUsuario(@PathParam("id") int id_usuario) {
		if (UsuarioService.deletarUsuario(id_usuario)) {
			ResponseBuilder response = Response.noContent();
			return response.build();
		} else {
			System.out.println("Não foi possível remover o USUARIO: " + id_usuario);
			ResponseBuilder response = Response.status(404)
					.entity("{\"error\": \"Não foi possível remover o USUARIO de id_usuario: " + id_usuario + "\"}");
			return response.build();
		}
	}

	/**
	 * Valida o login de um usuário.
	 *
	 * @param usuarioLogin O objeto Usuário contendo o email e a senha do usuário a serem validados.
	 * @return A resposta HTTP com o status e o objeto Usuário logado em caso de sucesso,
	 *         ou uma resposta HTTP de erro com uma mensagem em caso de falha na validação do login.
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validarLogin(Usuario usuarioLogin) {
	    String email_usuario = usuarioLogin.getEmail_usuario();
	    String senha_usuario = usuarioLogin.getSenha_usuario();

	    try {
	        Usuario usuario_logado = UsuarioService.validarLogin(email_usuario, senha_usuario);

	        if (usuario_logado != null) {
	            if ("Inativo".equals(usuario_logado.getStatus_usuario())) {
	                return Response.status(401).entity("{\"error\":\"Usuário inativo, favor entrar em contato com a administração.\"}").build();
	            } else {
	                ResponseBuilder response = Response.ok();
	                response.entity(usuario_logado);
	                return response.build();
	            }
	        } else {
	            return Response.status(401).entity("{\"error\":\"Email e/ou senha incorretos.\"}").build();
	        }
	    } catch (NullPointerException e) {
	        e.printStackTrace();
	        return Response.status(401).entity("{\"error\":\"Email e/ou senha incorretos.\"}").build();
	    }
	}
}