package br.com.fiap.solaraware.dao;

import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.model.ImpactoAcumulado;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ImpactoAcumuladoDao {
    private static final String INSERT_SQL = "INSERT INTO T_DGMA_IMPACTO_ACUMULADO (cd_impacto, dt_registro, ds_emissao_reduzida_total, ds_economia_total, ds_beneficio_ambiental) VALUES (sq_t_dgma_usuarios.nextval, ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM T_DGMA_IMPACTO_ACUMULADO";
    private static final String GET_BY_ID_SQL = "SELECT * FROM T_DGMA_IMPACTO_ACUMULADO WHERE cd_impacto = ?";
    private static final String UPDATE_SQL = "UPDATE T_DGMA_IMPACTO_ACUMULADO SET dt_registro = ?, ds_emissao_reduzida_total = ?, ds_economia_total = ?, ds_beneficio_ambiental = ? WHERE cd_impacto = ?";
    private static final String DELETE_SQL = "DELETE FROM T_DGMA_IMPACTO_ACUMULADO WHERE cd_impacto = ?";

    private Connection conexao;

    //Construtor
    public ImpactoAcumuladoDao(Connection conexao) {
        this.conexao = conexao;
    }

    //Método para Cadastrar
    public void cadastrar(ImpactoAcumulado impactoAcumulado) throws SQLException {
        try(PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"cd_impacto"})) {
            //Setando os valores
            preenchimentoDados(impactoAcumulado, stmt);
            //Executando o comando
            stmt.executeUpdate();
            //recuperar o id gerado pela sequence
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            impactoAcumulado.setId(resultSet.getInt(1));
        }
    }

    //Método para Listar
    public List<ImpactoAcumulado> listar() throws SQLException {
        //Criando uma lista de impactos acumuldos
        List<ImpactoAcumulado> lista = new ArrayList<>();
        //Criando um PreparedStatement
        try(Statement stmt = conexao.createStatement()) {
            //Criando um ResultSet
            ResultSet rs = stmt.executeQuery(GET_ALL_SQL);
            //Recuperando os impactos acumuldos
            while (rs.next()) {
                ImpactoAcumulado impactoAcumulado = parseImpactoAcumulado(rs);
                lista.add(impactoAcumulado);
            }
        }
        return lista;
    }

    //Método para buscar por ID
    public ImpactoAcumulado pesquisarPorId(int id) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(GET_BY_ID_SQL)) {
            //Setando o valor
            stmt.setInt(1, id);
            //Executando o comando SQL
            try(ResultSet rs = stmt.executeQuery()) {
                //Recuperando o registro, se existir
                if (!rs.next()) {
                    throw new IdNaoEncontradoException("Impacto acumulado não encontrado.");
                }
                return parseImpactoAcumulado(rs);
            }
        }

    }

    //Método para atualizar
    public void atualizar(ImpactoAcumulado impactoAcumulado) throws IdNaoEncontradoException, SQLException{
        //Criando um PreparedStatement
        try(PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL)) {
            //Setando os valores
            preenchimentoDados(impactoAcumulado, stmt);
            stmt.setInt(5, impactoAcumulado.getId());
            //Verificando a quantidade de linhas retornadas, para saber se o ID foi encontrado
            if (stmt.executeUpdate() == 0) {
                throw new IdNaoEncontradoException("Impacto acumulado não encontrado.");
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
                throw new IdNaoEncontradoException("Impacto acumulado não encontrado.");
            }
        }
    }

    //Método para preencher os dados do banco
    private static void preenchimentoDados(ImpactoAcumulado impactoAcumulado, PreparedStatement stmt) throws SQLException {
        stmt.setDate(1, Date.valueOf(impactoAcumulado.getDataRegistro()));
        stmt.setDouble(2, impactoAcumulado.getEmissaoReduzidaTotal());
        stmt.setDouble(3, impactoAcumulado.getEconomiaTotal());
        stmt.setString(4, impactoAcumulado.getBeneficioAmbiental());

    }

    //Método para recuperar o impacto acumuldo do banco
    private ImpactoAcumulado parseImpactoAcumulado(ResultSet rs) throws SQLException {
        int id = rs.getInt("cd_impacto");
        LocalDate dataRegistro = rs.getDate("dt_registro").toLocalDate();
        double emissaoReduzidaTotal = rs.getDouble("ds_emissao_reduzida_total");
        double economiaTotal = rs.getDouble("ds_economia_total");
        String beneficioAmbiental = rs.getString("ds_beneficio_ambiental");
        //Retornando um cliente
        return new ImpactoAcumulado(id, dataRegistro, emissaoReduzidaTotal, economiaTotal, beneficioAmbiental);
    }
}
