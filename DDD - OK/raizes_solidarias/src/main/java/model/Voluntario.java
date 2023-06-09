package model;

import java.sql.Date;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

/**
 * Classe que representa um Voluntário da plataforma.
 * 
 * Esta classe herda da classe Usuario e contém atributos e métodos específicos para Voluntários.
 * Os voluntários são usuários registrados no sistema e possuem informações como data de registro.
 *
 * @since 1.0
 * @version 1.0
 * 
 * @see services.VoluntarioService
 * @see dao.VoluntarioDAO
 * @see controller.VoluntarioResource
 * @see model.Usuario
 * 
 * @author Raízes Solidárias
 */ 
public class Voluntario extends Usuario {
	
	/**
	 * Data de registro do Voluntário.
	 */
	@NotNull(message = "A data de registro do Voluntário não pode ser nula.")
	private Date data_registro_voluntario;

	/**
	 * Obtém a data de registro do Voluntário.
	 * 
	 * @return A data de registro do Voluntário.
	 */
	public Date getData_registro_voluntario() {
		return data_registro_voluntario;
	}

	/**
	 * Define a data de registro do Voluntário.
	 * 
	 * @param data_registro_voluntario A data de registro do Voluntário.
	 */
	public void setData_registro_voluntario(Date data_registro_voluntario) {
		this.data_registro_voluntario = data_registro_voluntario;
	}

	/**
	 * Construtor padrão da classe Voluntario.
	 */
	public Voluntario() {
		super();
	}

	/**
	 * Construtor não padrão da classe Voluntario.
	 * 
	 * @param id_usuario        O ID do Usuário (não pode ser nulo)
	 * @param cpf_usuario       O CPF do Usuário (não pode ser nulo)
	 * @param nome_usuario      O Nome do Usuário (não pode ser nulo)
	 * @param email_usuario     O Email do Usuário (não pode ser nulo)
	 * @param cel_usuario       O Número de celular do Usuário (não pode ser nulo)
	 * @param senha_usuario     A Senha do Usuário (não pode ser nula)
	 * @param status_usuario    O Status do Usuário (não pode ser nulo)
	 */
	public Voluntario(@NotNull(message = "O ID do Usuário não pode ser nulo.") int id_usuario, 
	        @NotNull(message = "O CPF do Usuário não pode ser nulo.") String cpf_usuario,
	        @NotNull(message = "O Nome do Usuário não pode ser nulo.") String nome_usuario,
	        @NotNull(message = "O Email do Usuário não pode ser nulo.") String email_usuario,
	        @NotNull(message = "O Celular do Usuário não pode ser nulo.") String cel_usuario,
	        @NotNull(message = "A Senha do Usuário não pode ser nula.") String senha_usuario,
	        @NotNull(message = "O Status do Usuário não pode ser nulo.") String status_usuario) {
	    super(id_usuario, cpf_usuario, nome_usuario, email_usuario, cel_usuario, senha_usuario, status_usuario);
	}


	/**
	 * Construtor não padrão da classe Voluntario.
	 * 
	 * @param data_registro_voluntario A data de registro do Voluntário.
	 */
	public Voluntario(
			@NotNull(message = "A data de registro do Voluntário não pode ser nula.") Date data_registro_voluntario) {
		super();
		this.data_registro_voluntario = data_registro_voluntario;
	}

	/**
	 * Retorna o código hash do Voluntário.
	 *
	 * @return O código hash do Voluntário.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(data_registro_voluntario);
		return result;
	}

	/**
	 * Verifica se o Voluntário é igual a outro objeto.
	 *
	 * @param obj O objeto a ser comparado.
	 * @return true se o Voluntário for igual ao objeto fornecido, false caso contrário.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voluntario other = (Voluntario) obj;
		return Objects.equals(data_registro_voluntario, other.data_registro_voluntario);
	}

	/**
	 * Retorna uma representação em formato de string do objeto Voluntário.
	 * 
	 * @return Uma string representando o objeto Voluntário.
	 */
	@Override
	public String toString() {
		return "Voluntario [data_registro_voluntario=" + data_registro_voluntario + "]";
	}
}