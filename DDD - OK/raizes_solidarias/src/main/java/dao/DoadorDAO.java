package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.validation.Valid;
import model.Doador;

/**
 * Classe de acesso a dados para Doador.
 * 
 * Essa classe oferece métodos para manipulação dos dados relacionados aos Doadores no banco de dados.
 * Ela estende a classe Repository, que fornece a funcionalidade de conexão com o banco de dados.
 * 
 * 
 * @since 1.0
 * @version 1.0
 * 
 * @see model.Doador
 * @see services.DoadorService
 * @see controller.DoadorResource
 * @see dao.Repository
 * @see model.Usuario
 * 
 * @author Raízes Solidárias
 * 
 */
public class DoadorDAO extends Repository {
	
	/**
	 * Retorna uma lista de todos os doadores cadastrados no banco de dados.
	 *
	 * @return ArrayList contendo os objetos Doador correspondentes aos registros encontrados, ou uma lista vazia se nenhum registro for encontrado.
	 */
	public ArrayList<Doador> listarDoadores() {
		String sql = "SELECT usuario.id_usuario, usuario.cpf_usuario, usuario.nome_usuario, usuario.email_usuario, usuario.cel_usuario, usuario.senha_usuario, usuario.status_usuario, doador.nivel_doador, doador.moedas_doador FROM usuario INNER JOIN doador ON usuario.id_usuario = doador.id_usuario ORDER BY doador.id_usuario";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Doador> listaDoadores = new ArrayList<>();

		try {

			ps = getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					Doador doador = new Doador();
					doador.setId_usuario(rs.getInt("id_usuario"));
					doador.setCpf_usuario(rs.getString("cpf_usuario"));
					doador.setNome_usuario(rs.getString("nome_usuario"));
					doador.setEmail_usuario(rs.getString("email_usuario"));
					doador.setCel_usuario(rs.getString("cel_usuario"));
					doador.setSenha_usuario(rs.getString("senha_usuario"));
					doador.setStatus_usuario(rs.getString("status_usuario"));
					doador.setNivel_doador(rs.getInt("nivel_doador"));
					doador.setMoedas_doador(rs.getInt("moedas_doador"));
					
					listaDoadores.add(doador);
				}
			} else {
				System.out.println("Não foi possível encontrar registros na tabela DOADOR do banco de dados");
			}

		} catch (SQLException e) {
			System.out.println("Não foi possível consultar a listagem da tabela DOADOR: " + e.getMessage());
		} finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                System.out.println("Erro ao fechar ResultSet: " + e.getMessage());
	            }
	        }
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                System.out.println("Erro ao fechar PreparedStatement: " + e.getMessage());
	            }
	        }
	    }

	    return listaDoadores;
	}
	
	/**
	 * Busca um doador no banco de dados pelo ID do usuário.
	 *
	 * @param id_usuario O ID do usuário do doador a ser buscado.
	 * @return O objeto Doador correspondente ao registro encontrado, ou null se nenhum registro for encontrado.
	 */
	public static Doador buscarDoadorPorId(int id_usuario) {
		String sql = "SELECT usuario.id_usuario, usuario.cpf_usuario, usuario.nome_usuario, usuario.email_usuario, usuario.cel_usuario, usuario.senha_usuario, usuario.status_usuario, doador.nivel_doador, doador.moedas_doador FROM usuario INNER JOIN doador ON usuario.id_usuario = doador.id_usuario WHERE doador.id_usuario = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConnection().prepareStatement(sql);
			ps.setInt(1, id_usuario);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				Doador doador = new Doador();
				while (rs.next()) {
					doador.setId_usuario(rs.getInt("id_usuario"));
					doador.setCpf_usuario(rs.getString("cpf_usuario"));
					doador.setNome_usuario(rs.getString("nome_usuario"));
					doador.setEmail_usuario(rs.getString("email_usuario"));
					doador.setCel_usuario(rs.getString("cel_usuario"));
					doador.setSenha_usuario(rs.getString("senha_usuario"));
					doador.setStatus_usuario(rs.getString("status_usuario"));
					doador.setNivel_doador(rs.getInt("nivel_doador"));
					doador.setMoedas_doador(rs.getInt("moedas_doador"));
				}

				return doador;

			} else {
				System.out.println(
						"Não foi possível encontrar o id: " + id_usuario + " na tabela DOADOR do banco de dados");
			}

		} catch (SQLException e) {
			System.out.println("Não foi possível consultar o DOADOR no banco de dados: " + e.getMessage());
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("Não foi possível fechar o Prepared Statement: " + e.getMessage());
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("Não foi possível fechar o Result Set: " + e.getMessage());
				}
			}
		}

		return null;
	}
	
	/**
	 * Busca um doador no banco de dados pelo email do usuário.
	 *
	 * @param email_usuario O email do usuário do doador a ser buscado.
	 * @return O objeto Doador correspondente ao registro encontrado, ou null se nenhum registro for encontrado.
	 */
	public static Doador buscarDoadorPorEmail(String email_usuario) {
		String sql = "SELECT usuario.id_usuario, usuario.cpf_usuario, usuario.nome_usuario, usuario.email_usuario, usuario.cel_usuario, usuario.senha_usuario, usuario.status_usuario, doador.nivel_doador, doador.moedas_doador FROM usuario INNER JOIN doador ON usuario.id_usuario = doador.id_usuario WHERE UPPER(usuario.email_usuario) = UPPER(?)";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, email_usuario);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				Doador doador = new Doador();
				while (rs.next()) {
					doador.setId_usuario(rs.getInt("id_usuario"));
					doador.setCpf_usuario(rs.getString("cpf_usuario"));
					doador.setNome_usuario(rs.getString("nome_usuario"));
					doador.setEmail_usuario(rs.getString("email_usuario"));
					doador.setCel_usuario(rs.getString("cel_usuario"));
					doador.setSenha_usuario(rs.getString("senha_usuario"));
					doador.setStatus_usuario(rs.getString("status_usuario"));
					doador.setNivel_doador(rs.getInt("nivel_doador"));
					doador.setMoedas_doador(rs.getInt("moedas_doador"));
				}

				return doador;

			} else {
				System.out.println("Não foi possível encontrar o email: " + email_usuario + " na tabela DOADOR do banco de dados");
				return null;
			}

		} catch (SQLException e) {
			System.out.println("Não foi possível consultar o DOADOR no banco de dados: " + e.getMessage());
			return null;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("Não foi possível fechar o Prepared Statement: " + e.getMessage());
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("Não foi possível fechar o Result Set: " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Atualiza as informações de um doador no banco de dados.
	 *
	 * @param doador O objeto Doador com as informações atualizadas.
	 * @return true se o Doador foi atualizado com sucesso, false caso contrário.
	 */
	public static boolean atualizarDoador(@Valid Doador doador) {
		String sql = "UPDATE doador SET nivel_doador = ?, moedas_doador = ? WHERE id_usuario = ?";
		CallableStatement cs = null;

		try {
			cs = getConnection().prepareCall(sql);
			cs.setInt(1, doador.getNivel_doador());
			cs.setInt(2, doador.getMoedas_doador());
			cs.setInt(3, doador.getId_usuario());
			
			int rowsAffected = cs.executeUpdate();

	        if (rowsAffected > 0) {
	            return true;
	        }

		} catch (SQLException e) {
			System.out.println("Não foi possível atualizar o DOADOR no banco de dados: " + e.getMessage());
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
					System.out.println("Não foi possível fechar o Callable Statement: " + e.getMessage());
				}
			}
		}

		return false;
	}
	
	/**
	 * Cadastra um novo doador no banco de dados.
	 *
	 * @param doador_novo O objeto Doador contendo as informações do novo doador.
	 * @return O objeto Doador cadastrado, ou null se o cadastro falhar.
	 */
	public static Doador cadastrarDoador(@Valid Doador doador_novo) {
	// @formatter:off
    String sql = "INSERT INTO doador ("
            + " id_usuario,"
    		+ " nivel_doador,"
            + " moedas_doador"
            + ") VALUES ("
            + " ?,"
            + " ?,"
            + " ?"
            + ") ";
    // @formatter:on

		CallableStatement cs = null;

		try {
			cs = getConnection().prepareCall(sql);
			cs.setInt(1, doador_novo.getId_usuario());
			cs.setInt(2, doador_novo.getNivel_doador());
			cs.setInt(3, doador_novo.getMoedas_doador());
			cs.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Não foi possível cadastrar novo DOADOR no banco de dados: " + e.getMessage());
			return null;
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
					System.out.println("Não foi possível fechar o Callable Statement: " + e.getMessage());
				}
			}
		}

		return doador_novo;
	}
	
	/**
	 * Altera o status de um usuário para "excluído" no banco de dados.
	 *
	 * @param id_usuario o ID do usuário a ter o status alterado
	 * @return true se o status foi alterado com sucesso, false caso contrário
	 */
	public static boolean deletarDoador(int id_usuario) {
	    String sql = "UPDATE usuario SET status_usuario = ? WHERE id_usuario = ?";
	    PreparedStatement ps = null;

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setString(1, "Inativo");
	        ps.setInt(2, id_usuario);
	        int rowsAffected = ps.executeUpdate();
	        
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        System.out.println("Não foi possível alterar o status do usuário no banco de dados: " + e.getMessage());
	    } finally {
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                System.out.println("Não foi possível fechar o Prepared Statement: " + e.getMessage());
	            }
	        }
	    }

	    return false;
	}
}