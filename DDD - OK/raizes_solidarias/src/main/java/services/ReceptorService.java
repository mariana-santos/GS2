package services;

import dao.ReceptorDAO;
import model.Receptor;

/**
 * Classe de serviços para Receptor.
 *
 * @since 1.0
 * @version 1.0
 *
 * @see model.Receptor
 * @see dao.ReceptorDAO
 * @see controller.ReceptorResource
 * @see model.Usuario
 * @see model.Receptor
 * 
 * @author Raízes Solidárias
 *
 */
public class ReceptorService {

	/**
	 * Verifica se um Receptor com o ID especificado existe.
	 *
	 * @param id_usuario o ID do Usuario.
	 * @return true se um Receptor com o ID especificado existe, caso contrário, false.
	 */
	public static boolean validarIdReceptor(int id_usuario) {
		return ReceptorDAO.buscarReceptorPorId(id_usuario) != null;
	}

	/**
	 * Busca e retorna um Receptor pelo ID.
	 *
	 * @param id_usuario o ID do Usuario.
	 * @return o Receptor correspondente ao ID, ou null se não encontrado.
	 */
	public static Receptor exibirReceptorPorId(int id_usuario) {
		return ReceptorDAO.buscarReceptorPorId(id_usuario);
	}

	/**
	 * Atualiza um Receptor com as informações fornecidas.
	 *
	 * @param id_usuario o ID do Usuario a ser atualizado.
	 * @param receptor o Receptor com as novas informações.
	 * @return true se o Receptor foi atualizado com sucesso, caso contrário, false.
	 */
	public static boolean atualizarReceptor(int id_usuario, Receptor receptor) {
		Receptor usuario_atualizar = exibirReceptorPorId(id_usuario);

		if (usuario_atualizar == null || usuario_atualizar.getId_usuario() != receptor.getId_usuario()) {
			return false;
		} else {
			if (ReceptorDAO.atualizarReceptor(receptor))
				return true;
			
			else
				return false;
		}
	}

	/**
	 * Cadastra um novo Receptor.
	 *
	 * @param receptor_novo o novo Receptor a ser cadastrado.
	 * @return o Receptor cadastrado.
	 */
	public static Receptor cadastrarReceptor(Receptor receptor_novo) {
		return ReceptorDAO.cadastrarReceptor(receptor_novo);
	}

	/**
	 * Exclui um Receptor pelo ID.
	 *
	 * @param id_usuario o ID do Usuario a ser excluído.
	 * @return true se o Receptor foi excluído com sucesso, caso contrário, false.
	 */
	public static boolean deletarReceptor(int id_usuario) {
		if (validarIdReceptor(id_usuario)) {
			return ReceptorDAO.deletarReceptor(id_usuario);
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica se as credenciais de login do Receptor são válidas.
	 *
	 * @param email_usuario o email do Receptor
	 * @param senha_usuario a senha do Receptor
	 * @return o Receptor correspondente às credenciais de login válidas, ou null se as credenciais forem inválidas
	 */
	public static Receptor validarLoginReceptor(String email_usuario, String senha_usuario) {
	    Receptor receptor_login = ReceptorDAO.buscarReceptorPorEmail(email_usuario);

	    if (receptor_login != null && senha_usuario.equals(receptor_login.getSenha_usuario())) {
	        return receptor_login;
	    } else {
	        return null;
	    }
	}
}