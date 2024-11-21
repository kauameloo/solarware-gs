package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.ConfiguracoesPlataforma;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracoesPlataformaDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_CONFIGURACOES_PLATAFORMA (cd_configuracao, ds_descricao, ds_valor, dt_atualizacao) VALUES (sq_t_dgma_configuracoes_plataforma.nextval, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_CONFIGURACOES_PLATAFORMA";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_CONFIGURACOES_PLATAFORMA WHERE cd_configuracao = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_CONFIGURACOES_PLATAFORMA SET ds_descricao = ?, ds_valor = ?, dt_atualizacao = ? WHERE cd_configuracao = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_CONFIGURACOES_PLATAFORMA WHERE cd_configuracao = ?";

    private Connection conexao;

    //Construtor
    public ConfiguracoesPlataformaDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(ConfiguracoesPlataforma configuracoesPlataforma) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_configuracao"})) {
            //Setando os valores
            preenchimentoDados(configuracoesPlataforma, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            configuracoesPlataforma.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<ConfiguracoesPlataforma> listar() throws SQLException {
        //Criando uma lista de configuracoes da plataforma
        List<ConfiguracoesPlataforma> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando as configuracoes da plataforma
            while (rs.next()) {
                ConfiguracoesPlataforma configuracoesPlataforma = parseConfiguracoesPlataforma(rs);
                lista.add(configuracoesPlataforma);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public ConfiguracoesPlataforma pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Configuração da Plataforma não encontrada.");
                }
                return parseConfiguracoesPlataforma(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(ConfiguracoesPlataforma configuracoesPlataforma) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(configuracoesPlataforma, stmt);
            stmt.setInt(4, configuracoesPlataforma.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Configuração da Plataforma não encontrada.");
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
                throw new IdNaoEncontradoException("Configuração da Plataforma não encontrada.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(ConfiguracoesPlataforma configuracoesPlataforma, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, configuracoesPlataforma.getDescricao());
        stmt.setDouble(2, configuracoesPlataforma.getValor());
        stmt.setDate(3, Date.valueOf(configuracoesPlataforma.getDataAtualizacao()));
    }

    //Método para recuperar um usuario do banco
    private ConfiguracoesPlataforma parseConfiguracoesPlataforma(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_configuracao");
        String descricao = rs.getString("ds_descricao");
        double valor = rs.getDouble("ds_valor");
        LocalDate dtAtualizacao = rs.getDate("dt_atualizacao").toLocalDate();
        //Retornando um cliente
        return new ConfiguracoesPlataforma(id,descricao, valor, dtAtualizacao);
    }
}
