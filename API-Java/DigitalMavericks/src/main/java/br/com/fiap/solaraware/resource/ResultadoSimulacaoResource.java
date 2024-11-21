package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.ResultadoSimulacaoDao;
import br.com.fiap.solaraware.dao.SimulacaoDao;
import br.com.fiap.solaraware.dto.resulatadoSimulacao.AtualizacaoParcialResultadoSimulacaoDto;
import br.com.fiap.solaraware.dto.resulatadoSimulacao.AtualizacaoResultadoSimulacaoDto;
import br.com.fiap.solaraware.dto.resulatadoSimulacao.CadastroResultadoSimulacaoDto;
import br.com.fiap.solaraware.dto.resulatadoSimulacao.DetalhesResultadoSimulacaoDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.ResultadoSimulacao;
import br.com.fiap.solaraware.model.Simulacao;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/resultado-simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResultadoSimulacaoResource {
    private ResultadoSimulacaoDao resultadoSimulacaoDao;
    private SimulacaoDao simulacaoDao;
    private ModelMapper modelMapper;

    public ResultadoSimulacaoResource() throws Exception{
        resultadoSimulacaoDao = new ResultadoSimulacaoDao(ConnectionFactory.getConnection());
        simulacaoDao = new SimulacaoDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroResultadoSimulacaoDto dto , @Context UriInfo uriInfo) throws SQLException {
        ResultadoSimulacao resultadoSimulacao = modelMapper.map(dto, ResultadoSimulacao.class);

        resultadoSimulacaoDao.cadastrar(resultadoSimulacao);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(resultadoSimulacao.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(resultadoSimulacao, DetalhesResultadoSimulacaoDto.class)).build();
    }

    @GET
    public List<DetalhesResultadoSimulacaoDto> listar() throws SQLException{
        return simulacaoDao.listar().stream()
                .map(rs -> modelMapper.map(rs, DetalhesResultadoSimulacaoDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesResultadoSimulacaoDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        ResultadoSimulacao resultadoSimulacao = resultadoSimulacaoDao.pesquisarPorId(id);
        DetalhesResultadoSimulacaoDto dto = modelMapper.map(resultadoSimulacao, DetalhesResultadoSimulacaoDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoResultadoSimulacaoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        ResultadoSimulacao resultadoSimulacao = modelMapper.map(dto, ResultadoSimulacao.class);

        resultadoSimulacao.setId(id);
        resultadoSimulacaoDao.atualizar(resultadoSimulacao);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid AtualizacaoParcialResultadoSimulacaoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        ResultadoSimulacao resultadoSimulacaoBanco = resultadoSimulacaoDao.pesquisarPorId(id);
        if (dto.getIdSimulacao() != null) {
            Simulacao simulacao = simulacaoDao.pesquisarPorId(dto.getIdSimulacao());
            resultadoSimulacaoBanco.setSimulacao(simulacao);
        }
        if (dto.getEmissaoReduzida() != null)
            resultadoSimulacaoBanco.setEmissaoReduzida(dto.getEmissaoReduzida());
        if (dto.getEconomiaFinanceira() != null)
            resultadoSimulacaoBanco.setEconomiaFinanceira(dto.getEconomiaFinanceira());
        if (dto.getBeneficioAmbiental() != null)
            resultadoSimulacaoBanco.setBeneficioAmbiental(dto.getBeneficioAmbiental());
        if (dto.getCustoTotalProjetado() != null)
            resultadoSimulacaoBanco.setCustoTotalProjetado(dto.getCustoTotalProjetado());
        resultadoSimulacaoDao.atualizar(resultadoSimulacaoBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        resultadoSimulacaoDao.excluir(id);
        return Response.noContent().build();
    }
}
