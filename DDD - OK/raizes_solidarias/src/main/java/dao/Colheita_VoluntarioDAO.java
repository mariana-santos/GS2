package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.validation.Valid;
import model.Colheita_Voluntario;
import model.Colheita;
import model.Voluntario;

/**
 * Classe de acesso a dados para Colheita_Voluntario.
 * 
 * Essa classe oferece métodos para manipulação dos dados relacionados a tabela Colheita_Voluntario no banco de dados.
 * Ela estende a classe Repository, que fornece a funcionalidade de conexão com o banco de dados.
 * 
 * @since 1.0
 * @version 1.0
 * 
 * @see model.Colheita_Voluntario
 * @see services.Colheita_VoluntarioService
 * @see controller.Colheita_VoluntarioResource
 * @see model.Colheita
 * @see model.Voluntario
 * @see dao.Repository
 * 
 * @author Raízes Solidárias
 * 
 */
public class Colheita_VoluntarioDAO extends Repository {
	
	/**
	 * Retorna uma lista de todos os Colheita_Voluntarios cadastrados no banco de dados.
	 *
	 * @return uma lista de Colheita_Voluntarios.
	 */
	public ArrayList<Colheita_Voluntario> listarColheita_Voluntarios() {
	    String sql = "SELECT cv.id_colheita, c.data_colheita, c.descricao_colheita,"
	            + " u.id_usuario, u.cpf_usuario, u.nome_usuario, u.email_usuario, u.cel_usuario, u.senha_usuario, u.status_usuario,"
	            + " v.data_registro_voluntario"
	            + " FROM Colheita_Voluntario cv"
	            + " JOIN Colheita c ON cv.id_colheita = c.id_colheita"
	            + " JOIN Usuario u ON cv.id_usuario = u.id_usuario"
	            + " JOIN Voluntario v ON u.id_usuario = v.id_usuario"
	            + " ORDER BY cv.id_colheita";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Colheita_Voluntario> listaColheita_Voluntarios = new ArrayList<>();

	    try {
	        ps = getConnection().prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Colheita_Voluntario colheita_voluntario = new Colheita_Voluntario();

	            Colheita colheita = new Colheita();
	            colheita.setId_colheita(rs.getInt("id_colheita"));
	            colheita.setData_colheita(rs.getDate("data_colheita"));
	            colheita.setDescricao_colheita(rs.getString("descricao_colheita"));

	            colheita_voluntario.setColheita(colheita);

	            Voluntario voluntario = new Voluntario();
	            voluntario.setId_usuario(rs.getInt("id_usuario"));
	            voluntario.setCpf_usuario(rs.getString("cpf_usuario"));
	            voluntario.setNome_usuario(rs.getString("nome_usuario"));
	            voluntario.setEmail_usuario(rs.getString("email_usuario"));
	            voluntario.setCel_usuario(rs.getString("cel_usuario"));
	            voluntario.setSenha_usuario(rs.getString("senha_usuario"));
	            voluntario.setStatus_usuario(rs.getString("status_usuario"));
	            voluntario.setData_registro_voluntario(rs.getDate("data_registro_voluntario"));

	            colheita_voluntario.setVoluntario(voluntario);

	            listaColheita_Voluntarios.add(colheita_voluntario);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível consultar a listagem da tabela COLHEITA_VOLUNTARIO: " + e.getMessage());
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

	    return listaColheita_Voluntarios;
	}
	
	/**
	 * Retorna uma lista de todos os Colheita_Voluntarios cadastrados no banco de dados de acordo com o ID da Colheita.
	 *
	 * @param id_colheita O ID da colheita para filtrar os registros.
	 * @return Uma lista de Colheita_Voluntarios correspondente ao ID da Colheita fornecido.
	 */
	public ArrayList<Colheita_Voluntario> buscarColheita_VoluntarioPorIdColheita(int id_colheita) {
	    String sql = "SELECT cv.id_colheita, c.data_colheita, c.descricao_colheita,"
	            + " u.id_usuario, u.cpf_usuario, u.nome_usuario, u.email_usuario, u.cel_usuario, u.senha_usuario, u.status_usuario,"
	            + " v.data_registro_voluntario"
	            + " FROM Colheita_Voluntario cv"
	            + " JOIN Colheita c ON cv.id_colheita = c.id_colheita"
	            + " JOIN Usuario u ON cv.id_usuario = u.id_usuario"
	            + " JOIN Voluntario v ON u.id_usuario = v.id_usuario"
	            + " WHERE cv.id_colheita = ?";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Colheita_Voluntario> listaColheita_Voluntarios = new ArrayList<>();

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_colheita);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Colheita_Voluntario colheita_voluntario = new Colheita_Voluntario();

	            Colheita colheita = new Colheita();
	            colheita.setId_colheita(rs.getInt("id_colheita"));
	            colheita.setData_colheita(rs.getDate("data_colheita"));
	            colheita.setDescricao_colheita(rs.getString("descricao_colheita"));

	            colheita_voluntario.setColheita(colheita);

	            Voluntario voluntario = new Voluntario();
	            voluntario.setId_usuario(rs.getInt("id_usuario"));
	            voluntario.setCpf_usuario(rs.getString("cpf_usuario"));
	            voluntario.setNome_usuario(rs.getString("nome_usuario"));
	            voluntario.setEmail_usuario(rs.getString("email_usuario"));
	            voluntario.setCel_usuario(rs.getString("cel_usuario"));
	            voluntario.setSenha_usuario(rs.getString("senha_usuario"));
	            voluntario.setStatus_usuario(rs.getString("status_usuario"));
	            voluntario.setData_registro_voluntario(rs.getDate("data_registro_voluntario"));

	            colheita_voluntario.setVoluntario(voluntario);

	            listaColheita_Voluntarios.add(colheita_voluntario);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Colheita_Voluntario com o ID da Colheita " + id_colheita + ": " + e.getMessage());
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

	    return listaColheita_Voluntarios;
	}
	
	/**
	 * Retorna uma lista de todos os Colheita_Voluntarios cadastrados no banco de dados de acordo com o ID do Voluntario (Usuario).
	 *
	 * @param id_usuario O ID do Voluntario (Usuario) para filtrar os registros.
	 * @return Uma lista de Colheita_Voluntarios correspondente ao ID do Voluntario (Usuario) fornecido.
	 */
	public ArrayList<Colheita_Voluntario> buscarColheita_VoluntarioPorIdUsuario(int id_usuario) {
	    String sql = "SELECT cv.id_colheita, c.data_colheita, c.descricao_colheita,"
	            + " u.id_usuario, u.cpf_usuario, u.nome_usuario, u.email_usuario, u.cel_usuario, u.senha_usuario, u.status_usuario,"
	            + " v.data_registro_voluntario"
	            + " FROM Colheita_Voluntario cv"
	            + " JOIN Colheita c ON cv.id_colheita = c.id_colheita"
	            + " JOIN Usuario u ON cv.id_usuario = u.id_usuario"
	            + " JOIN Voluntario v ON u.id_usuario = v.id_usuario"
	            + " WHERE cv.id_usuario = ?";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Colheita_Voluntario> listaColheita_Voluntarios = new ArrayList<>();

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_usuario);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Colheita_Voluntario colheita_voluntario = new Colheita_Voluntario();

	            Colheita colheita = new Colheita();
	            colheita.setId_colheita(rs.getInt("id_colheita"));
	            colheita.setData_colheita(rs.getDate("data_colheita"));
	            colheita.setDescricao_colheita(rs.getString("descricao_colheita"));

	            colheita_voluntario.setColheita(colheita);

	            Voluntario voluntario = new Voluntario();
	            voluntario.setId_usuario(rs.getInt("id_usuario"));
	            voluntario.setCpf_usuario(rs.getString("cpf_usuario"));
	            voluntario.setNome_usuario(rs.getString("nome_usuario"));
	            voluntario.setEmail_usuario(rs.getString("email_usuario"));
	            voluntario.setCel_usuario(rs.getString("cel_usuario"));
	            voluntario.setSenha_usuario(rs.getString("senha_usuario"));
	            voluntario.setStatus_usuario(rs.getString("status_usuario"));
	            voluntario.setData_registro_voluntario(rs.getDate("data_registro_voluntario"));

	            colheita_voluntario.setVoluntario(voluntario);

	            listaColheita_Voluntarios.add(colheita_voluntario);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Colheita_Voluntario com o ID do usuário " + id_usuario + ": " + e.getMessage());
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

	    return listaColheita_Voluntarios;
	}
	
	/**
	 * Retorna um Colheita_Voluntarios cadastrado no banco de dados de acordo com o ID da Colheita e o ID do Voluntario (Usuario).
	 *
	 * @param id_colheita O ID da Colheita para filtrar o registro.
	 * @param id_usuario O ID do Voluntario (Usuario) para filtrar o registro.
	 * @return Um Colheita_Voluntarios correspondente ao ID da Colheita e ao ID do Voluntario (Usuario) fornecidos.
	 */
	public static Colheita_Voluntario buscarColheita_VoluntarioPorIds(int id_colheita, int id_usuario) {
		String sql = "SELECT cv.id_colheita, c.data_colheita, c.descricao_colheita,"
	            + " u.id_usuario, u.cpf_usuario, u.nome_usuario, u.email_usuario, u.cel_usuario, u.senha_usuario, u.status_usuario,"
	            + " v.data_registro_voluntario"
	            + " FROM Colheita_Voluntario cv"
	            + " JOIN Colheita c ON cv.id_colheita = c.id_colheita"
	            + " JOIN Usuario u ON cv.id_usuario = u.id_usuario"
	            + " JOIN Voluntario v ON u.id_usuario = v.id_usuario"
	            + " WHERE cv.id_colheita = ? AND cv.id_usuario = ?";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Colheita_Voluntario colheita_voluntario = new Colheita_Voluntario();

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_colheita);
	        ps.setInt(2, id_usuario);
	        rs = ps.executeQuery();

	        while (rs.next()) {

	            Colheita colheita = new Colheita();
	            colheita.setId_colheita(rs.getInt("id_colheita"));
	            colheita.setData_colheita(rs.getDate("data_colheita"));
	            colheita.setDescricao_colheita(rs.getString("descricao_colheita"));

	            colheita_voluntario.setColheita(colheita);

	            Voluntario voluntario = new Voluntario();
	            voluntario.setId_usuario(rs.getInt("id_usuario"));
	            voluntario.setCpf_usuario(rs.getString("cpf_usuario"));
	            voluntario.setNome_usuario(rs.getString("nome_usuario"));
	            voluntario.setEmail_usuario(rs.getString("email_usuario"));
	            voluntario.setCel_usuario(rs.getString("cel_usuario"));
	            voluntario.setSenha_usuario(rs.getString("senha_usuario"));
	            voluntario.setStatus_usuario(rs.getString("status_usuario"));
	            voluntario.setData_registro_voluntario(rs.getDate("data_registro_voluntario"));

	            colheita_voluntario.setVoluntario(voluntario);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Colheita_Voluntario com o ID do usuário " + id_usuario + ": " + e.getMessage());
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

	    return colheita_voluntario;
	}
	
	/**
	 * Atualiza um Colheita_Voluntario no banco de dados.
	 *
	 * @param id_usuario_novo  	o id do usuario que será atualizado.
	 * @param id_usuario_antigo	o id do usuario a ser atualizado.
	 * @param id_colheita 		o id da colheita a ser atualizada.
	 * @return true se o Colheita_Voluntario for atualizado com sucesso, false caso contrário
	 */
	public static boolean atualizarColheita_Voluntario(int id_usuario_novo, int id_usuario_antigo, int id_colheita) {
		String sql = "UPDATE colheita_voluntario SET id_usuario = ? WHERE id_usuario = ? AND id_colheita = ?";
		CallableStatement cs = null;

		try {
			cs = getConnection().prepareCall(sql);
			cs.setInt(1, id_usuario_novo);
			cs.setInt(2, id_usuario_antigo);
			cs.setInt(3, id_colheita);
			
			int rowsAffected = cs.executeUpdate();

	        if (rowsAffected > 0) {
	            return true;
	        }

		} catch (SQLException e) {
			System.out.println("Não foi possível atualizar o COLHEITA_VOLUNTARIO no banco de dados: " + e.getMessage());
			return false;
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
	 * Cadastra um novo Colheita_Voluntario no banco de dados.
	 *
	 * @param colheita_voluntario_novo o novo Colheita_Voluntario a ser cadastrado
	 * @return o Colheita_Voluntario cadastrado ou null se o cadastro falhar
	 */
	public static Colheita_Voluntario cadastrarColheita_Voluntario(@Valid Colheita_Voluntario colheita_voluntario_novo) {
	    String sql = "INSERT INTO colheita_voluntario ("
	            + "    id_colheita,"
	            + "    id_usuario"
	            + ") VALUES ("
	            + "    ?,"
	            + "    ?"
	            + ")";

	    PreparedStatement ps = null;

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, colheita_voluntario_novo.getColheita().getId_colheita());
	        ps.setInt(2, colheita_voluntario_novo.getVoluntario().getId_usuario());
	        ps.executeUpdate();

	        return colheita_voluntario_novo;
	    } catch (SQLException e) {
	        System.out.println("Não foi possível cadastrar novo COLHEITA_VOLUNTARIO no banco de dados: " + e.getMessage());
	    } finally {
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                System.out.println("Não foi possível fechar o PreparedStatement: " + e.getMessage());
	            }
	        }
	    }

	    return null;
	}

	/**
	 * Deleta um Colheita_Voluntario do banco de dados.
	 *
	 * @param id_colheita   o id da Colheita_Voluntario a ser deletado
	 * @param id_usuario 	o id do Voluntario do Colheita_Voluntario a ser deletado
	 * @return true se o Colheita_Voluntario for deletado com sucesso, false caso contrário
	 */
	public static boolean deletarColheita_Voluntario(int id_colheita, int id_usuario) {
	    String sql = "DELETE FROM colheita_voluntario WHERE id_usuario = ? AND id_colheita = ?";
	    PreparedStatement ps = null;

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_usuario);
	        ps.setInt(2, id_colheita);
	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            return true;
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível deletar o COLHEITA_VOLUNTARIO no banco de dados: " + e.getMessage());
	    } finally {
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                System.out.println("Não foi possível fechar o PreparedStatement: " + e.getMessage());
	            }
	        }
	    }

	    return false;
	}
}