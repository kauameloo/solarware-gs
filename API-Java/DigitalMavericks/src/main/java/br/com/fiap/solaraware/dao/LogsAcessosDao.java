package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.LogsAcessos;
import br.com.fiap.solaraware.model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LogsAcessosDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_LOGS_ACESSOS (cd_log, cd_usuario, dt_acesso, ds_acao_realizada) VALUES (sq_t_dgma_logs_acessos.nextval, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_LOGS_ACESSOS";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_LOGS_ACESSOS WHERE cd_log = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_LOGS_ACESSOS SET cd_usuario = ?, dt_acesso = ?, ds_acao_realizada = ? WHERE cd_log = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_LOGS_ACESSOS WHERE cd_log = ?";

    private Connection conexao;

    //Construtor
    public LogsAcessosDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(LogsAcessos logsAcessos) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_log"})) {
            //Setando os valores
            preenchimentoDados(logsAcessos, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            logsAcessos.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<LogsAcessos> listar() throws SQLException {
        //Criando uma lista de Logs de acesso
        List<LogsAcessos> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando os Logs de acesso
            while (rs.next()) {
                LogsAcessos logsAcessos = parseLogsAcessos(rs);
                lista.add(logsAcessos);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public LogsAcessos pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Log de acesso não encontrado.");
                }
                return parseLogsAcessos(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(LogsAcessos logsAcessos) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(logsAcessos, stmt);
            stmt.setInt(4, logsAcessos.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Log de acesso não encontrado.");
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
                throw new IdNaoEncontradoException("Log de acesso não encontrado.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(LogsAcessos logsAcessos, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, logsAcessos.getUsuario().getId());
        stmt.setDate(2, Date.valueOf(logsAcessos.getDataAcesso()));
        stmt.setString(3, logsAcessos.getAcaoRealizada());
    }

    //Método para recuperar um usuario do banco
    private LogsAcessos parseLogsAcessos(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_log");
        int idUsuario = rs.getInt("cd_usuario");
        LocalDate dtAcesso = rs.getDate("dt_acesso").toLocalDate();
        String acaoRealizada = rs.getString("ds_acao_realizada");
        //Retornando um cliente
        return new LogsAcessos(id, new Usuario(idUsuario), dtAcesso, acaoRealizada);
    }
}
