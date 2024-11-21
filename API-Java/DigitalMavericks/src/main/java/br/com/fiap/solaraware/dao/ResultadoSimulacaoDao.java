package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.ResultadoSimulacao;
import br.com.fiap.solaraware.model.Simulacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultadoSimulacaoDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_RESULTADO_SIMULACAO (cd_resultado, cd_simulacao, ds_emissao_reduzida, ds_economia_financeira, ds_beneficio_ambiental, ds_custo_total_projetado) VALUES (sq_t_dgma_resultado_simulacao.nextval, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_RESULTADO_SIMULACAO";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_RESULTADO_SIMULACAO WHERE cd_resultado = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_RESULTADO_SIMULACAO SET cd_simulacao = ?, ds_emissao_reduzida = ?, ds_economia_financeira = ?, ds_beneficio_ambiental = ?, ds_custo_total_projetado = ? WHERE cd_resultado = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_RESULTADO_SIMULACAO WHERE cd_resultado = ?";

    private Connection conexao;

    //Construtor
    public ResultadoSimulacaoDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(ResultadoSimulacao resultadoSimulacao) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_resultado"})) {
            //Setando os valores
            preenchimentoDados(resultadoSimulacao, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            resultadoSimulacao.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<ResultadoSimulacao> listar() throws SQLException {
        //Criando uma lista de resultados de simulações
        List<ResultadoSimulacao> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando os resultados das simulções
            while (rs.next()) {
                ResultadoSimulacao resultadoSimulacao = parseResultadoSimulacao(rs);
                lista.add(resultadoSimulacao);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public ResultadoSimulacao pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Resultado de simulação não encontrado.");
                }
                return parseResultadoSimulacao(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(ResultadoSimulacao resultadoSimulacao) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(resultadoSimulacao, stmt);
            stmt.setInt(6, resultadoSimulacao.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Resultado de simulação não encontrado.");
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
                throw new IdNaoEncontradoException("Resultado de simulação não encontrado.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(ResultadoSimulacao resultadoSimulacao, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, resultadoSimulacao.getSimulacao().getId());
        stmt.setDouble(2, resultadoSimulacao.getEmissaoReduzida());
        stmt.setDouble(3, resultadoSimulacao.getEconomiaFinanceira());
        stmt.setString(4, resultadoSimulacao.getBeneficioAmbiental());
        stmt.setDouble(5, resultadoSimulacao.getCustoTotalProjetado());
    }

    //Método para recuperar um Resultado da Simulacao do banco
    private ResultadoSimulacao parseResultadoSimulacao(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_resultado");
        int idSimulacao = rs.getInt("cd_simulacao");
        double emissaoReduzida = rs.getDouble("ds_emissao_reduzida");
        double economiaFinanceira = rs.getDouble("ds_economia_financeira");
        String beneficioAmbiental = rs.getString("ds_beneficio_ambiental");
        double custoTotalProjetado = rs.getDouble("ds_custo_total_projetado");
        //Retornando um cliente
        return new ResultadoSimulacao(id, new Simulacao(idSimulacao), emissaoReduzida, economiaFinanceira, beneficioAmbiental, custoTotalProjetado);
    }
}
