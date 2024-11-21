package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.CasoEstudo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CasoEstudoDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_CASO_ESTUDO (cd_caso, ds_localizacao, ds_descricao, ds_emissao_reduzida, ds_economia_gerada, ds_beneficios_ambientais) VALUES (sq_t_dgma_casos_estudo.nextval, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_CASO_ESTUDO";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_CASO_ESTUDO WHERE cd_caso = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_CASO_ESTUDO SET ds_localizacao = ?, ds_descricao = ?, ds_emissao_reduzida = ?, ds_economia_gerada = ?, ds_beneficios_ambientais = ? WHERE cd_caso = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_CASO_ESTUDO WHERE cd_caso = ?";

    private Connection conexao;

    //Construtor
    public CasoEstudoDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(CasoEstudo casoEstudo) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_caso"})) {
            //Setando os valores
            preenchimentoDados(casoEstudo, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            casoEstudo.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<CasoEstudo> listar() throws SQLException {
        //Criando uma lista de Casos de Estudo
        List<CasoEstudo> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando os Casos de Estudo
            while (rs.next()) {
                CasoEstudo casoEstudo = parseCasoEstudo(rs);
                lista.add(casoEstudo);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public CasoEstudo pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Caso de Estudo não encontrado.");
                }
                return parseCasoEstudo(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(CasoEstudo casoEstudo) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(casoEstudo, stmt);
            stmt.setInt(6, casoEstudo.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Caso de Estudo não encontrado.");
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
                throw new IdNaoEncontradoException("Caso de Estudo não encontrado.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(CasoEstudo casoEstudo, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, casoEstudo.getLocalizacao());
        stmt.setString(2, casoEstudo.getDescricao());
        stmt.setDouble(3, casoEstudo.getEmissaoReduzida());
        stmt.setDouble(4, casoEstudo.getEconomiaGerada());
        stmt.setString(5, casoEstudo.getBeneficiosAmbientais());

    }

    //Método para recuperar um caso de estudo do banco
    private CasoEstudo parseCasoEstudo(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_caso");
        String localizacao = rs.getString("ds_localizacao");
        String descricao = rs.getString("ds_descricao");
        double emissaoReduzida = rs.getDouble("ds_emissao_reduzida");
        double economiaGerada = rs.getDouble("ds_economia_gerada");
        String beneficiosAmbientais = rs.getString("ds_beneficios_ambientais");
        //Retornando um cliente
        return new CasoEstudo(id, localizacao, descricao, emissaoReduzida, economiaGerada, beneficiosAmbientais);
    }
}
