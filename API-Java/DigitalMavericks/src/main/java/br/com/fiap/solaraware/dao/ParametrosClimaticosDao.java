package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.ParametrosClimaticos;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParametrosClimaticosDao {
    private static final String INSERT_SQL = "INSERT INTO T_PARAMETROS_CLIMATICOS (cd_parametro, ds_localizacao, ds_insolacao_media, ds_temperatura_media, dt_registro) VALUES (sq_t_dgma_parametros_climaticos.nextval, ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_PARAMETROS_CLIMATICOS";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_PARAMETROS_CLIMATICOS WHERE cd_parametro = ?";
    private static final String UPDATE_SQL = "UPDATE T_PARAMETROS_CLIMATICOS SET ds_localizacao = ?, ds_insolacao_media = ?, ds_temperatura_media = ?, dt_registro = ? WHERE cd_parametro = ?";
    private static final String DELETE_SQL = "DELETE FROM T_PARAMETROS_CLIMATICOS WHERE cd_parametro = ?";

    private Connection conexao;

    //Construtor
    public ParametrosClimaticosDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(ParametrosClimaticos parametrosClimaticos) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_parametro"})) {
            //Setando os valores
            preenchimentoDados(parametrosClimaticos, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            parametrosClimaticos.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<ParametrosClimaticos> listar() throws SQLException {
        //Criando uma lista de parametros climaticos
        List<ParametrosClimaticos> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando os parametros climaticos
            while (rs.next()) {
                ParametrosClimaticos parametrosClimaticos = parseParametroClimatico(rs);
                lista.add(parametrosClimaticos);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public ParametrosClimaticos pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Parametro climático não encontrado.");
                }
                return parseParametroClimatico(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(ParametrosClimaticos parametrosClimaticos) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(parametrosClimaticos, stmt);
            stmt.setInt(5, parametrosClimaticos.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Parametro climático não encontrado.");
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
                throw new IdNaoEncontradoException("Parametro climático não encontrado.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(ParametrosClimaticos parametrosClimaticos, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, parametrosClimaticos.getLocalizacao());
        stmt.setDouble(2, parametrosClimaticos.getInsolacaoMedia());
        stmt.setDouble(3, parametrosClimaticos.getTemperaturaMedia());
        stmt.setDate(4, Date.valueOf(parametrosClimaticos.getDataRegistro()));
    }

    //Método para recuperar o impacto acumuldo do banco
    private ParametrosClimaticos parseParametroClimatico(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_parametro");
        String localizacao = rs.getString("ds_localizacao");
        double insolacaoMedia = rs.getDouble("ds_insolacao_media");
        double temperaturaMedia = rs.getDouble("ds_temperatura_media");
        LocalDate dataRegistro = rs.getDate("dt_registro").toLocalDate();
        //Retornando um cliente
        return new ParametrosClimaticos(id, localizacao, insolacaoMedia, temperaturaMedia, dataRegistro);
    }
}
