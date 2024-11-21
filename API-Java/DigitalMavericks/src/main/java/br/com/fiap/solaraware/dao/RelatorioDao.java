package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.Relatorio;
import br.com.fiap.solaraware.model.Simulacao;
import br.com.fiap.solaraware.model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_RELATORIOS (cd_relatorio, cd_usuario, cd_simulacao, ds_tipo_relatorio, dt_geracao) VALUES (sq_t_dgma_relatorios.nextval, ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_RELATORIOS";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_RELATORIOS WHERE cd_relatorio = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_RELATORIOS SET cd_usuario = ?, cd_simulacao = ?, ds_tipo_relatorio = ?, dt_geracao = ? WHERE cd_relatorio = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_RELATORIOS WHERE cd_relatorio = ?";

    private Connection conexao;

    //Construtor
    public RelatorioDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(Relatorio relatorio) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_relatorio"})) {
            //Setando os valores
            preenchimentoDados(relatorio, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            relatorio.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<Relatorio> listar() throws SQLException {
        //Criando uma lista de relatorios
        List<Relatorio> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando os resultados dos relatorios
            while (rs.next()) {
                Relatorio relatorio = parseRelatorio(rs);
                lista.add(relatorio);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public Relatorio pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Relatorio não encontrado.");
                }
                return parseRelatorio(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(Relatorio relatorio) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(relatorio, stmt);
            stmt.setInt(6, relatorio.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Relatorio não encontrado.");
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
                throw new IdNaoEncontradoException("Relatorio não encontrado.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(Relatorio relatorio, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, relatorio.getUsuario().getId());
        stmt.setInt(2, relatorio.getSimulacao().getId());
        stmt.setString(4, relatorio.getTipoRelatorio());
        stmt.setDate(5, Date.valueOf(relatorio.getDataGeracao()));
    }

    //Método para recuperar um Resultado da Simulacao do banco
    private Relatorio parseRelatorio(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_relatorio");
        int idUsuario = rs.getInt("cd_usuario");
        int idSimulacao = rs.getInt("cd_simulacao");
        String tipoRelatorio = rs.getString("ds_tipo_relatorio");
        LocalDate dataGeracao = rs.getDate("dt_geracao").toLocalDate();
        //Retornando um cliente
        return new Relatorio(id, new Usuario(idUsuario) ,new Simulacao(idSimulacao), tipoRelatorio, dataGeracao);
    }
}
