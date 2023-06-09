package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.validation.Valid;
import model.Destino;
import model.Receptor;
import model.Receptor_Destino;

/**
 * Classe de acesso a dados para Receptor_Destino.
 * 
 * Essa classe oferece métodos para manipulação dos dados relacionados a tabela Receptor_Destino no banco de dados.
 * Ela estende a classe Repository, que fornece a funcionalidade de conexão com o banco de dados.
 * 
 * @since 1.0
 * @version 1.0
 * 
 * @see model.Receptor_Destino
 * @see services.Receptor_DestinoService
 * @see controller.Receptor_DestinoResource
 * @see model.Receptor
 * @see model.Destino
 * @see dao.Repository
 * 
 * @author Raízes Solidárias
 * 
 */
public class Receptor_DestinoDAO extends Repository {
	
	/**
	 * Retorna uma lista de todos os Receptor_Destinos cadastrados no banco de dados.
	 *
	 * @return uma lista de Receptor_Destinos.
	 */
	public ArrayList<Receptor_Destino> listarReceptor_Destinos() {
		String sql = "SELECT rd.id_usuario,"
	            + " u.cpf_usuario, u.nome_usuario, u.email_usuario, u.cel_usuario, u.senha_usuario, u.status_usuario,"
	            + " r.carga_receptor, r.endereco_receptor,"
	            + " rd.id_destino,"
	            + " d.id_destino, d.endereco_destino, d.responsavel_destino, d.cel_destino, d.qtd_dependentes_destino"
	            + " FROM Receptor_Destino rd"
	            + " JOIN Receptor r ON rd.id_usuario = r.id_usuario"
	            + " JOIN Usuario u ON rd.id_usuario = u.id_usuario"
	            + " JOIN Destino d ON rd.id_destino = d.id_destino"
	            + " ORDER BY rd.id_usuario";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Receptor_Destino> listaReceptor_Destinos = new ArrayList<>();

	    try {
	        ps = getConnection().prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Receptor_Destino receptor_destino = new Receptor_Destino();

	            Receptor receptor = new Receptor();
				receptor.setId_usuario(rs.getInt("id_usuario"));
				receptor.setCpf_usuario(rs.getString("cpf_usuario"));
				receptor.setNome_usuario(rs.getString("nome_usuario"));
				receptor.setEmail_usuario(rs.getString("email_usuario"));
				receptor.setCel_usuario(rs.getString("cel_usuario"));
				receptor.setSenha_usuario(rs.getString("senha_usuario"));
				receptor.setStatus_usuario(rs.getString("status_usuario"));
				receptor.setCarga_receptor(rs.getInt("carga_receptor"));
				receptor.setEndereco_receptor(rs.getString("endereco_receptor"));
				
				receptor_destino.setReceptor(receptor);
	            
				Destino destino = new Destino();
	            destino.setId_destino(rs.getInt("id_destino"));
	            destino.setEndereco_destino(rs.getString("endereco_destino"));
	            destino.setResponsavel_destino(rs.getString("responsavel_destino"));
	            destino.setCel_destino(rs.getString("cel_destino"));
	            destino.setQtd_dependentes_destino(rs.getInt("qtd_dependentes_destino"));

	            receptor_destino.setDestino(destino);;

	            listaReceptor_Destinos.add(receptor_destino);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível consultar a listagem da tabela RECEPTOR_DESTINO: " + e.getMessage());
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

	    return listaReceptor_Destinos;
	}
	
	/**
	 * Retorna uma lista de Receptor_Destino cadastrados no banco de dados de acordo com o ID do Receptor.
	 *
	 * @param id_usuario o ID do Receptor para buscar os Receptor_Destino correspondentes.
	 * @return uma lista de Receptor_Destino de acordo com o ID do Receptor.
	 */
	public static ArrayList<Receptor_Destino> buscarReceptor_DestinoPorIdUsuario(int id_usuario) {
		String sql = "SELECT rd.id_usuario,"
	            + " u.cpf_usuario, u.nome_usuario, u.email_usuario, u.cel_usuario, u.senha_usuario, u.status_usuario,"
	            + " r.carga_receptor, r.endereco_receptor,"
	            + " rd.id_destino,"
	            + " d.id_destino, d.endereco_destino, d.responsavel_destino, d.cel_destino, d.qtd_dependentes_destino"
	            + " FROM Receptor_Destino rd"
	            + " JOIN Receptor r ON rd.id_usuario = r.id_usuario"
	            + " JOIN Usuario u ON rd.id_usuario = u.id_usuario"
	            + " JOIN Destino d ON rd.id_destino = d.id_destino"
	            + " WHERE rd.id_usuario = ?"
	            + " ORDER BY rd.id_usuario";	            

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Receptor_Destino> listaReceptor_Destinos = new ArrayList<>();
	    
	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_usuario);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	Receptor_Destino receptor_destino_buscado = new Receptor_Destino();

	            Receptor receptor = new Receptor();
				receptor.setId_usuario(rs.getInt("id_usuario"));
				receptor.setCpf_usuario(rs.getString("cpf_usuario"));
				receptor.setNome_usuario(rs.getString("nome_usuario"));
				receptor.setEmail_usuario(rs.getString("email_usuario"));
				receptor.setCel_usuario(rs.getString("cel_usuario"));
				receptor.setSenha_usuario(rs.getString("senha_usuario"));
				receptor.setStatus_usuario(rs.getString("status_usuario"));
				receptor.setCarga_receptor(rs.getInt("carga_receptor"));
				receptor.setEndereco_receptor(rs.getString("endereco_receptor"));
				
				receptor_destino_buscado.setReceptor(receptor);
	            
				Destino destino = new Destino();
	            destino.setId_destino(rs.getInt("id_destino"));
	            destino.setEndereco_destino(rs.getString("endereco_destino"));
	            destino.setResponsavel_destino(rs.getString("responsavel_destino"));
	            destino.setCel_destino(rs.getString("cel_destino"));
	            destino.setQtd_dependentes_destino(rs.getInt("qtd_dependentes_destino"));

	            receptor_destino_buscado.setDestino(destino);
	            
	            listaReceptor_Destinos.add(receptor_destino_buscado);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Receptor_Destino com o ID do Receptor " + id_usuario + ": " + e.getMessage());
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

	    return listaReceptor_Destinos;
	}
	
	/**
	 * Retorna uma lista de Receptor_Destinos cadastrados no banco de dados de acordo com o ID do Destino.
	 *
	 * @param id_destino o ID do Destino para buscar os Receptor_Destino correspondentes.
	 * @return uma lista de Receptor_Destinos de acordo com o ID do Destino.
	 */
	public static ArrayList<Receptor_Destino> buscarReceptor_DestinoPorIdDestino(int id_destino) {
		String sql = "SELECT rd.id_usuario,"
	            + " u.cpf_usuario, u.nome_usuario, u.email_usuario, u.cel_usuario, u.senha_usuario, u.status_usuario,"
	            + " r.carga_receptor, r.endereco_receptor,"
	            + " rd.id_destino,"
	            + " d.id_destino, d.endereco_destino, d.responsavel_destino, d.cel_destino, d.qtd_dependentes_destino"
	            + " FROM Receptor_Destino rd"
	            + " JOIN Receptor r ON rd.id_usuario = r.id_usuario"
	            + " JOIN Usuario u ON rd.id_usuario = u.id_usuario"
	            + " JOIN Destino d ON rd.id_destino = d.id_destino"
	            + " WHERE rd.id_destino = ?"
	            + " ORDER BY rd.id_usuario";	 

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Receptor_Destino> listaReceptor_Destinos = new ArrayList<>();
	    
	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_destino);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	Receptor_Destino receptor_destino_buscado = new Receptor_Destino();

	            Receptor receptor = new Receptor();
				receptor.setId_usuario(rs.getInt("id_usuario"));
				receptor.setCpf_usuario(rs.getString("cpf_usuario"));
				receptor.setNome_usuario(rs.getString("nome_usuario"));
				receptor.setEmail_usuario(rs.getString("email_usuario"));
				receptor.setCel_usuario(rs.getString("cel_usuario"));
				receptor.setSenha_usuario(rs.getString("senha_usuario"));
				receptor.setStatus_usuario(rs.getString("status_usuario"));
				receptor.setCarga_receptor(rs.getInt("carga_receptor"));
				receptor.setEndereco_receptor(rs.getString("endereco_receptor"));
				
				receptor_destino_buscado.setReceptor(receptor);
	            
				Destino destino = new Destino();
	            destino.setId_destino(rs.getInt("id_destino"));
	            destino.setEndereco_destino(rs.getString("endereco_destino"));
	            destino.setResponsavel_destino(rs.getString("responsavel_destino"));
	            destino.setCel_destino(rs.getString("cel_destino"));
	            destino.setQtd_dependentes_destino(rs.getInt("qtd_dependentes_destino"));

	            receptor_destino_buscado.setDestino(destino);
	            
	            listaReceptor_Destinos.add(receptor_destino_buscado);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Receptor_Destino com o ID do Destino " + id_destino + ": " + e.getMessage());
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

	    return listaReceptor_Destinos;
	}
	
	/**
	 * Retorna um Receptor_Destino cadastrado no banco de dados de acordo com o ID do Receptor (Usuario) e o ID do Destino.
	 *
	 * @param id_usuario o ID do Receptor (Usuario) para buscar o Receptor_Destino correspondente.
	 * @param id_destino o ID do Destino para buscar o Receptor_Destino correspondente.
	 * @return um Receptor_Destino de acordo com o ID do Receptor (Usuario) e o ID do Destino.
	 */
	public static Receptor_Destino buscarReceptor_DestinoPorIds(int id_usuario, int id_destino) {
		String sql = "SELECT rd.id_usuario,"
	            + " u.cpf_usuario, u.nome_usuario, u.email_usuario, u.cel_usuario, u.senha_usuario, u.status_usuario,"
	            + " r.carga_receptor, r.endereco_receptor,"
	            + " rd.id_destino,"
	            + " d.id_destino, d.endereco_destino, d.responsavel_destino, d.cel_destino, d.qtd_dependentes_destino"
	            + " FROM Receptor_Destino rd"
	            + " JOIN Receptor r ON rd.id_usuario = r.id_usuario"
	            + " JOIN Usuario u ON rd.id_usuario = u.id_usuario"
	            + " JOIN Destino d ON rd.id_destino = d.id_destino"
	            + " WHERE rd.id_usuario = ? AND rd.id_destino = ?"
	            + " ORDER BY rd.id_usuario";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Receptor_Destino receptor_destino_buscado = new Receptor_Destino();
	    
	    
	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_usuario);
	        ps.setInt(2, id_destino);
	        rs = ps.executeQuery();

	        while (rs.next()) {

	            Receptor receptor = new Receptor();
				receptor.setId_usuario(rs.getInt("id_usuario"));
				receptor.setCpf_usuario(rs.getString("cpf_usuario"));
				receptor.setNome_usuario(rs.getString("nome_usuario"));
				receptor.setEmail_usuario(rs.getString("email_usuario"));
				receptor.setCel_usuario(rs.getString("cel_usuario"));
				receptor.setSenha_usuario(rs.getString("senha_usuario"));
				receptor.setStatus_usuario(rs.getString("status_usuario"));
				receptor.setCarga_receptor(rs.getInt("carga_receptor"));
				receptor.setEndereco_receptor(rs.getString("endereco_receptor"));
				
				receptor_destino_buscado.setReceptor(receptor);
	            
				Destino destino = new Destino();
	            destino.setId_destino(rs.getInt("id_destino"));
	            destino.setEndereco_destino(rs.getString("endereco_destino"));
	            destino.setResponsavel_destino(rs.getString("responsavel_destino"));
	            destino.setCel_destino(rs.getString("cel_destino"));
	            destino.setQtd_dependentes_destino(rs.getInt("qtd_dependentes_destino"));

	            receptor_destino_buscado.setDestino(destino);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Receptor_Destino com o ID do Destino " + id_destino + ": " + e.getMessage());
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

	    return receptor_destino_buscado;
	}
	
	/**
	 * Atualiza um Receptor_Destino no banco de dados.
	 *
	 * @param id_destino_novo  	o novo ID do destino a ser atualizado.
	 * @param id_destino_antigo	o ID do destino a ser atualizado.
	 * @param id_usuario_novo  	o novo ID do receptor a ser atualizado.
	 * @param id_usuario_antigo	o ID do receptor a ser atualizado.
	 * @return true se o Receptor_Destino for atualizado com sucesso, false caso contrário
	 */
	public static boolean atualizarReceptor_Destino(int id_destino_novo, int id_destino_antigo, int id_usuario_novo, int id_usuario_antigo) {
		String sql = "UPDATE receptor_destino SET id_usuario = ?, id_destino = ? WHERE id_usuario = ? AND id_destino = ?";
		CallableStatement cs = null;

		try {
			cs = getConnection().prepareCall(sql);
			cs.setInt(1, id_usuario_novo);
			cs.setInt(2, id_destino_novo);
			cs.setInt(3, id_usuario_antigo);
			cs.setInt(4,  id_destino_antigo);
			
			int rowsAffected = cs.executeUpdate();

	        if (rowsAffected > 0) {
	            return true;
	        }

		} catch (SQLException e) {
			System.out.println("Não foi possível atualizar o RECEPTOR_DESTINO no banco de dados: " + e.getMessage());
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
	 * Cadastra um novo Receptor_Destino no banco de dados.
	 *
	 * @param receptor_destino_novo o novo Receptor_Destino a ser cadastrado
	 * @return o Receptor_Destino cadastrado ou null se o cadastro falhar
	 */
	public static Receptor_Destino cadastrarReceptor_Destino(@Valid Receptor_Destino receptor_destino_novo) {
	    String sql = "INSERT INTO receptor_destino ("
	            + "    id_usuario,"
	            + "    id_destino"
	            + ") VALUES ("
	            + "    ?,"
	            + "    ?"
	            + ")";

	    PreparedStatement ps = null;

	    try {
	        ps = getConnection().prepareStatement(sql, new String[] {"id_usuario", "id_destino"});
	        ps.setInt(1, receptor_destino_novo.getReceptor().getId_usuario());
	        ps.setInt(2, receptor_destino_novo.getDestino().getId_destino());
	        ps.executeUpdate();

	        return receptor_destino_novo;
	    } catch (SQLException e) {
	        System.out.println("Não foi possível cadastrar novo RECEPTOR_DESTINO no banco de dados: " + e.getMessage());
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
	 * Deleta um Receptor_Destino do banco de dados.
	 *
	 * @param id_usuario	o id do receptor do Receptor_Destino a ser deletado
	 * @param id_destino 	o id do destino do Receptor_Destino a ser deletado
	 * @return true se o Receptor_Destino for deletado com sucesso, false caso contrário
	 */
	public static boolean deletarReceptor_Destino(int id_usuario, int id_destino) {
	    String sql = "DELETE FROM receptor_destino WHERE id_usuario = ? AND id_destino = ?";
	    PreparedStatement ps = null;

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_usuario);
	        ps.setInt(2, id_destino);
	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            return true;
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível deletar o RECEPTOR_DESTINO no banco de dados: " + e.getMessage());
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