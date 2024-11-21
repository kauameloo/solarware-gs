package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.Contatos;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContatosDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_CONTATOS (cd_contato, nm_contato, ds_email, ds_telefone, ds_mensagem, dt_contato) VALUES (sq_t_dgma_contato.nextval, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_CONTATOS";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_CONTATOS WHERE cd_contato = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_CONTATOS SET nm_contato = ?, ds_email = ?, ds_telefone= ?, ds_mensagem = ?, dt_contato = ? WHERE cd_contato = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_CONTATOS WHERE cd_contato = ?";

    private Connection conexao;

    //Construtor
    public ContatosDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(Contatos contatos) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_contato"})) {
            //Setando os valores
            preenchimentoDados(contatos, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            contatos.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<Contatos> listar() throws SQLException {
        //Criando uma lista de contatos
        List<Contatos> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando os contatos
            while (rs.next()) {
                Contatos contatos = parseContato(rs);
                lista.add(contatos);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public Contatos pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Contato não encontrado.");
                }
                return parseContato(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(Contatos contatos) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(contatos, stmt);
            stmt.setInt(6, contatos.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Contato não encontrado.");
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
                throw new IdNaoEncontradoException("Contato não encontrado.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(Contatos contatos, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, contatos.getNome());
        stmt.setString(2, contatos.getEmail());
        stmt.setString(3, contatos.getTelefone());
        stmt.setString(4, contatos.getMensagem());
        stmt.setDate(5, Date.valueOf(contatos.getDataContato()));
    }

    //Método para recuperar um contato do banco
    private Contatos parseContato(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_contato");
        String nome = rs.getString("nm_contato");
        String email = rs.getString("ds_email");
        String telefone = rs.getString("ds_telefone");
        String mensagem = rs.getString("ds_mensagem");
        LocalDate dataContato = rs.getDate("dt_contato").toLocalDate();
        //Retornando um cliente
        return new Contatos(id, nome, email, telefone, mensagem, dataContato);
    }
}
