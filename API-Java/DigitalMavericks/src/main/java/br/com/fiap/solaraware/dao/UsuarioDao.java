package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_USUARIO (cd_usuario, nm_usuario, ds_email, ds_telefone, dt_registro) VALUES (sq_t_dgma_usuarios.nextval, ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_USUARIO";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_USUARIO WHERE cd_usuario = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_USUARIO SET nm_usuario = ?, ds_email = ?, ds_telefone = ?, dt_registro = ? WHERE cd_usuario = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_USUARIO WHERE cd_usuario = ?";

    private Connection conexao;

    //Construtor
    public UsuarioDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(Usuario usuario) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_usuario"})) {
            //Setando os valores
            preenchimentoDados(usuario, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            usuario.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<Usuario> listar() throws SQLException {
        //Criando uma lista de usuarios
        List<Usuario> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando os usuarios
            while (rs.next()) {
                Usuario usuario = parseUsuario(rs);
                lista.add(usuario);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public Usuario pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Usuário não encontrado.");
                }
                return parseUsuario(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(Usuario usuario) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(usuario, stmt);
            stmt.setInt(5, usuario.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Usuário não encontrado.");
            }
        }
    }

    //Método para excluir
    public void excluir(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(DELETE_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Usuário não encontrado.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(Usuario usuario, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getTelefone());
        stmt.setDate(4, Date.valueOf(usuario.getDataRegistro()));

    }

    //Método para recuperar um usuario do banco
    private Usuario parseUsuario(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_usuario");
        String nome = rs.getString("nm_usuario");
        String email = rs.getString("ds_email");
        String telefone = rs.getString("ds_telefone");
        LocalDate dataRegistro = rs.getDate("dt_registro").toLocalDate();
        //Retornando um cliente
        return new Usuario(id, nome, email, telefone, dataRegistro);
    }
}
