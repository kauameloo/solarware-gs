package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.Simulacao;
import br.com.fiap.solaraware.model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimulacaoDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_SIMULACOES (cd_simulacao, cd_usuario, ds_percent_energia_solar, ds_custo_inicial, ds_duracao_anos, dt_simulacao) VALUES (sq_t_dgma_simulacoes.nextval, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_SIMULACOES";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_SIMULACOES WHERE cd_simulacao = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_SIMULACOES SET cd_usuario = ?, ds_percent_energia_solar = ?, ds_custo_inicial = ?, ds_duracao_anos = ?, dt_simulacao = ? WHERE cd_simulacao = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_SIMULACOES WHERE cd_simulacao = ?";

    private Connection conexao;

    //Construtor
    public SimulacaoDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(Simulacao simulacao) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_simulacao"})) {
            //Setando os valores
            preenchimentoDados(simulacao, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            simulacao.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<Simulacao> listar() throws SQLException {
        //Criando uma lista de simulações
        List<Simulacao> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando as simulções
            while (rs.next()) {
                Simulacao simulacao = parseSimulacao(rs);
                lista.add(simulacao);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public Simulacao pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Simulação não encontrada.");
                }
                return parseSimulacao(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(Simulacao simulacao) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(simulacao, stmt);
            stmt.setInt(6, simulacao.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Simulação não encontrada.");
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
                throw new IdNaoEncontradoException("Simulação não encontrada.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(Simulacao simulacao, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, simulacao.getUsuario().getId());
        stmt.setDouble(2, simulacao.getPercentEnergiaSolar());
        stmt.setDouble(3, simulacao.getCustoInicial());
        stmt.setInt(4, simulacao.getDuracaoAnos());
        stmt.setDate(5, Date.valueOf(simulacao.getDataSimulacao()));
    }

    //Método para recuperar uma simulacao do banco
    private Simulacao parseSimulacao(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_simulacao");
        int idUsuario = rs.getInt("cd_usuario");
        double porcentagemEnergialSol = rs.getDouble("ds_percent_energia_solar");
        double custoInicial = rs.getDouble("ds_custo_inicial");
        int duracaoAnos = rs.getInt("ds_duracao_anos");
        LocalDate dataRegistro = rs.getDate("dt_simulacao").toLocalDate();
        //Retornando um cliente
        return new Simulacao(id, new Usuario(idUsuario), porcentagemEnergialSol, custoInicial, duracaoAnos, dataRegistro);
    }
}
