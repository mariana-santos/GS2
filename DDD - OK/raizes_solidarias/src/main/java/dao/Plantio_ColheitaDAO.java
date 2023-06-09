package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.validation.Valid;
import model.Alimento;
import model.Colheita;
import model.Plantio;
import model.Plantio_Colheita;

/**
 * Classe de acesso a dados para Plantio_Colheita.
 * 
 * Essa classe oferece métodos para manipulação dos dados relacionados a tabela Plantio_Colheita no banco de dados.
 * Ela estende a classe Repository, que fornece a funcionalidade de conexão com o banco de dados.
 * 
 * @since 1.0
 * @version 1.0
 * 
 * @see model.Plantio_Colheita
 * @see services.Plantio_ColheitaService
 * @see controller.Plantio_ColheitaResource
 * @see model.Plantio
 * @see model.Colheita
 * @see dao.Repository
 * 
 * @author Raízes Solidárias
 * 
 */
public class Plantio_ColheitaDAO extends Repository {
	
	/**
	 * Retorna uma lista de todos os Plantio_Colheitas cadastrados no banco de dados.
	 *
	 * @return uma lista de Plantio_Colheitas.
	 */
	public ArrayList<Plantio_Colheita> listarPlantio_Colheitas() {
		String sql = "SELECT pc.id_plantio, p.data_plantio, p.espaco_plantio, " +
	             "p.id_alimento, a.nome_alimento, a.tempo_colheita, a.qtd_irrigacao, a.preco_alimento, a.qtd_alimento, " +
	             "pc.id_colheita, c.data_colheita, c.descricao_colheita " +
	             "FROM Plantio_Colheita pc " +
	             "JOIN Plantio p ON pc.id_plantio = p.id_plantio " +
	             "JOIN Alimento a ON a.id_alimento = p.id_alimento " +
	             "JOIN Colheita c ON pc.id_colheita = c.id_colheita " +
	             "ORDER BY pc.id_colheita";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Plantio_Colheita> listaPlantio_Colheitas = new ArrayList<>();

	    try {
	        ps = getConnection().prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Plantio_Colheita plantio_colheita = new Plantio_Colheita();

	            Plantio plantio = new Plantio();
	            plantio.setId_plantio(rs.getInt("id_plantio"));
	            plantio.setData_plantio(rs.getDate("data_plantio"));
	            plantio.setEspaco_plantio(rs.getInt("espaco_plantio"));
	            
	            Alimento alimento = new Alimento();
	            alimento.setId_alimento(rs.getInt("id_alimento"));
	            alimento.setNome_alimento(rs.getString("nome_alimento"));
	            alimento.setTempo_colheita(rs.getInt("tempo_colheita"));
	            alimento.setQtd_irrigacao(rs.getInt("qtd_irrigacao"));
	            alimento.setPreco_alimento(rs.getInt("preco_alimento"));
	            alimento.setQtd_alimento(rs.getInt("qtd_alimento"));
	            
	            plantio.setAlimento(alimento);

	            plantio_colheita.setPlantio(plantio);

	            Colheita colheita = new Colheita();
	            colheita.setId_colheita(rs.getInt("id_colheita"));
	            colheita.setData_colheita(rs.getDate("data_colheita"));
	            colheita.setDescricao_colheita(rs.getString("descricao_colheita"));

	            plantio_colheita.setColheita(colheita);

	            listaPlantio_Colheitas.add(plantio_colheita);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível consultar a listagem da tabela PLANTIO_COLHEITA: " + e.getMessage());
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

	    return listaPlantio_Colheitas;
	}
	
	/**
	 * Retorna o Plantio_Colheita cadastrado no banco de dados de acordo com o ID do Plantio.
	 *
	 * @param id_plantio o ID do Plantio a ser pesquisado
	 * @return o Plantio_Colheita de acordo com o ID do Plantio
	 */
	public static Plantio_Colheita buscarPlantio_ColheitaPorIdPlantio(int id_plantio) {
		String sql = "SELECT pc.id_plantio, p.data_plantio, p.espaco_plantio, " +
                "p.id_alimento, a.nome_alimento, a.tempo_colheita, a.qtd_irrigacao, a.preco_alimento, a.qtd_alimento, " +
                "pc.id_colheita, c.data_colheita, c.descricao_colheita " +
                "FROM Plantio_Colheita pc " +
                "JOIN Plantio p ON pc.id_plantio = p.id_plantio " +
                "JOIN Alimento a ON a.id_alimento = p.id_alimento " +
                "JOIN Colheita c ON pc.id_colheita = c.id_colheita " +
                "WHERE pc.id_plantio = ? " +
                "ORDER BY pc.id_colheita";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Plantio_Colheita plantio_colheita_buscado = new Plantio_Colheita();
	    
	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_plantio);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Plantio plantio = new Plantio();
	            plantio.setId_plantio(rs.getInt("id_plantio"));
	            plantio.setData_plantio(rs.getDate("data_plantio"));
	            plantio.setEspaco_plantio(rs.getInt("espaco_plantio"));

	            Alimento alimento = new Alimento();
	            alimento.setId_alimento(rs.getInt("id_alimento"));
	            alimento.setNome_alimento(rs.getString("nome_alimento"));
	            alimento.setTempo_colheita(rs.getInt("tempo_colheita"));
	            alimento.setQtd_irrigacao(rs.getInt("qtd_irrigacao"));
	            alimento.setPreco_alimento(rs.getInt("preco_alimento"));
	            alimento.setQtd_alimento(rs.getInt("qtd_alimento"));
	            
	            plantio.setAlimento(alimento);

	            plantio_colheita_buscado.setPlantio(plantio);

	            Colheita colheita = new Colheita();
	            colheita.setId_colheita(rs.getInt("id_colheita"));
	            colheita.setData_colheita(rs.getDate("data_colheita"));
	            colheita.setDescricao_colheita(rs.getString("descricao_colheita"));

	            plantio_colheita_buscado.setColheita(colheita);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Plantio_Colheita com o ID do Plantio " + id_plantio + ": " + e.getMessage());
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

	    return plantio_colheita_buscado;
	}
	
	/**
	 * Retorna uma lista de todos os Plantio_Colheitas cadastrados no banco de dados de acordo com o ID da Colheita.
	 *
	 * @param id_colheita o ID da Colheita a ser pesquisada
	 * @return uma lista de Plantio_Colheitas de acordo com o ID da Colheita
	 */
	public static ArrayList<Plantio_Colheita> buscarPlantio_ColheitaPorIdColheita(int id_colheita) {
		String sql = "SELECT pc.id_plantio, p.data_plantio, p.espaco_plantio, " +
                "p.id_alimento, a.nome_alimento, a.tempo_colheita, a.qtd_irrigacao, a.preco_alimento, a.qtd_alimento, " +
                "pc.id_colheita, c.data_colheita, c.descricao_colheita " +
                "FROM Plantio_Colheita pc " +
                "JOIN Plantio p ON pc.id_plantio = p.id_plantio " +
                "JOIN Alimento a ON a.id_alimento = p.id_alimento " +
                "JOIN Colheita c ON pc.id_colheita = c.id_colheita " +
                "WHERE pc.id_colheita = ? " +
                "ORDER BY pc.id_colheita";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Plantio_Colheita> listaPlantio_Colheitas = new ArrayList<>();

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_colheita);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	Plantio_Colheita plantio_colheita_buscado = new Plantio_Colheita();
	        	
	        	Plantio plantio = new Plantio();
	            plantio.setId_plantio(rs.getInt("id_plantio"));
	            plantio.setData_plantio(rs.getDate("data_plantio"));
	            plantio.setEspaco_plantio(rs.getInt("espaco_plantio"));

	            Alimento alimento = new Alimento();
	            alimento.setId_alimento(rs.getInt("id_alimento"));
	            alimento.setNome_alimento(rs.getString("nome_alimento"));
	            alimento.setTempo_colheita(rs.getInt("tempo_colheita"));
	            alimento.setQtd_irrigacao(rs.getInt("qtd_irrigacao"));
	            alimento.setPreco_alimento(rs.getInt("preco_alimento"));
	            alimento.setQtd_alimento(rs.getInt("qtd_alimento"));
	            
	            plantio.setAlimento(alimento);

	            plantio_colheita_buscado.setPlantio(plantio);

	            Colheita colheita = new Colheita();
	            colheita.setId_colheita(rs.getInt("id_colheita"));
	            colheita.setData_colheita(rs.getDate("data_colheita"));
	            colheita.setDescricao_colheita(rs.getString("descricao_colheita"));

	            plantio_colheita_buscado.setColheita(colheita);

	            listaPlantio_Colheitas.add(plantio_colheita_buscado);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Plantio_Colheita com o ID da colheita " + id_colheita + ": " + e.getMessage());
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

	    return listaPlantio_Colheitas;
	}
	
	/**
	 * Retorna o Plantio_Colheita cadastrado no banco de dados de acordo com o ID do Plantio e o ID da Colheita.
	 *
	 * @param id_plantio o ID do Plantio a ser pesquisado
	 * @param id_colheita o ID da Colheita a ser pesquisada
	 * @return o Plantio_Colheita de acordo com o ID do Plantio e o ID da Colheita
	 */
	public static Plantio_Colheita buscarPlantio_ColheitaPorIds(int id_plantio, int id_colheita) {
		String sql = "SELECT pc.id_plantio, p.data_plantio, p.espaco_plantio, " +
                "p.id_alimento, a.nome_alimento, a.tempo_colheita, a.qtd_irrigacao, a.preco_alimento, a.qtd_alimento, " +
                "pc.id_colheita, c.data_colheita, c.descricao_colheita " +
                "FROM Plantio_Colheita pc " +
                "JOIN Plantio p ON pc.id_plantio = p.id_plantio " +
                "JOIN Alimento a ON a.id_alimento = p.id_alimento " +
                "JOIN Colheita c ON pc.id_colheita = c.id_colheita " +
                "WHERE pc.id_plantio = ? AND pc.id_colheita = ? " +
                "ORDER BY pc.id_colheita";

	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Plantio_Colheita plantio_colheita_buscado = new Plantio_Colheita();
	    
	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_plantio);
	        ps.setInt(2, id_colheita);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Plantio plantio = new Plantio();
	            plantio.setId_plantio(rs.getInt("id_plantio"));
	            plantio.setData_plantio(rs.getDate("data_plantio"));
	            plantio.setEspaco_plantio(rs.getInt("espaco_plantio"));

	            Alimento alimento = new Alimento();
	            alimento.setId_alimento(rs.getInt("id_alimento"));
	            alimento.setNome_alimento(rs.getString("nome_alimento"));
	            alimento.setTempo_colheita(rs.getInt("tempo_colheita"));
	            alimento.setQtd_irrigacao(rs.getInt("qtd_irrigacao"));
	            alimento.setPreco_alimento(rs.getInt("preco_alimento"));
	            alimento.setQtd_alimento(rs.getInt("qtd_alimento"));
	            
	            plantio.setAlimento(alimento);

	            plantio_colheita_buscado.setPlantio(plantio);

	            Colheita colheita = new Colheita();
	            colheita.setId_colheita(rs.getInt("id_colheita"));
	            colheita.setData_colheita(rs.getDate("data_colheita"));
	            colheita.setDescricao_colheita(rs.getString("descricao_colheita"));

	            plantio_colheita_buscado.setColheita(colheita);
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível buscar o Plantio_Colheita com o ID do Plantio " + id_plantio + ": " + e.getMessage());
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

	    return plantio_colheita_buscado;
	}
	
	/**
	 * Atualiza um Plantio_Colheita no banco de dados.
	 *
	 * @param id_plantio_novo  	o id do plantio que será atualizado.
	 * @param id_plantio_antigo	o id do plantio a ser atualizado.
	 * @param id_colheita 		o id da colheita a ser atualizada.
	 * @return true se o Plantio_Colheita for atualizado com sucesso, false caso contrário
	 */
	public static boolean atualizarPlantio_Colheita(int id_plantio_novo, int id_plantio_antigo, int id_colheita) {
		String sql = "UPDATE plantio_colheita SET id_plantio = ? WHERE id_plantio = ? AND id_colheita = ?";
		CallableStatement cs = null;

		try {
			cs = getConnection().prepareCall(sql);
			cs.setInt(1, id_plantio_novo);
			cs.setInt(2, id_plantio_antigo);
			cs.setInt(3, id_colheita);
			
			int rowsAffected = cs.executeUpdate();

	        if (rowsAffected > 0) {
	            return true;
	        }

		} catch (SQLException e) {
			System.out.println("Não foi possível atualizar o PLANTIO_COLHEITA no banco de dados: " + e.getMessage());
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
	 * Cadastra um novo Plantio_Colheita no banco de dados.
	 *
	 * @param plantio_colheita_novo o novo Plantio_Colheita a ser cadastrado
	 * @return o Plantio_Colheita cadastrado ou null se o cadastro falhar
	 */
	public static Plantio_Colheita cadastrarPlantio_Colheita(@Valid Plantio_Colheita plantio_colheita_novo) {
	    String sql = "INSERT INTO plantio_colheita ("
	            + "    id_plantio,"
	            + "    id_colheita"
	            + ") VALUES ("
	            + "    ?,"
	            + "    ?"
	            + ")";

	    PreparedStatement ps = null;

	    try {
	        ps = getConnection().prepareStatement(sql, new String[] {"id_plantio", "id_colheita"});
	        ps.setInt(1, plantio_colheita_novo.getPlantio().getId_plantio());
	        ps.setInt(2, plantio_colheita_novo.getColheita().getId_colheita());
	        ps.executeUpdate();

	        return plantio_colheita_novo;
	    } catch (SQLException e) {
	        System.out.println("Não foi possível cadastrar novo PLANTIO_COLHEITA no banco de dados: " + e.getMessage());
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
	 * Deleta um Plantio_Colheita do banco de dados.
	 *
	 * @param id_plantio	o id do plantio do Plantio_Colheita a ser deletado
	 * @param id_colheita 	o id do colheita do Plantio_Colheita a ser deletado
	 * @return true se o Plantio_Colheita for deletado com sucesso, false caso contrário
	 */
	public static boolean deletarPlantio_Colheita(int id_plantio, int id_colheita) {
	    String sql = "DELETE FROM plantio_colheita WHERE id_plantio = ? AND id_colheita = ?";
	    PreparedStatement ps = null;

	    try {
	        ps = getConnection().prepareStatement(sql);
	        ps.setInt(1, id_plantio);
	        ps.setInt(2, id_colheita);
	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            return true;
	        }

	    } catch (SQLException e) {
	        System.out.println("Não foi possível deletar o PLANTIO_COLHEITA no banco de dados: " + e.getMessage());
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