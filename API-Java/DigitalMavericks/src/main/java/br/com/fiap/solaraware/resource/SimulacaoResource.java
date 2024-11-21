package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.SimulacaoDao;
import br.com.fiap.solaraware.dao.UsuarioDao;
import br.com.fiap.solaraware.dto.simulacao.AtualizacaoParcialSimulacaoDto;
import br.com.fiap.solaraware.dto.simulacao.AtualizacaoSimulacaoDto;
import br.com.fiap.solaraware.dto.simulacao.CadastroSimulacaoDto;
import br.com.fiap.solaraware.dto.simulacao.DetalhesSimulacaoDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.Simulacao;
import br.com.fiap.solaraware.model.Usuario;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {
    private SimulacaoDao simulacaoDao;
    private UsuarioDao usuarioDao;
    private ModelMapper modelMapper;

    public SimulacaoResource() throws Exception{
        simulacaoDao = new SimulacaoDao(ConnectionFactory.getConnection());
        usuarioDao = new UsuarioDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroSimulacaoDto dto , @Context UriInfo uriInfo) throws SQLException {
        Simulacao simulacao = modelMapper.map(dto, Simulacao.class);

        simulacaoDao.cadastrar(simulacao);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(simulacao.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(simulacao, DetalhesSimulacaoDto.class)).build();
    }

    @GET
    public List<DetalhesSimulacaoDto> listar() throws SQLException{
        return simulacaoDao.listar().stream()
                .map(s -> modelMapper.map(s, DetalhesSimulacaoDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesSimulacaoDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Simulacao simulacao = simulacaoDao.pesquisarPorId(id);
        DetalhesSimulacaoDto dto = modelMapper.map(simulacao, DetalhesSimulacaoDto.class);
        return dto;
    }

    @GET
    @Path("/{id}/economia-acumulada")
    public Response calcularEconomiaAcumulada(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Simulacao simulacao = simulacaoDao.pesquisarPorId(id);
        double economia = simulacao.calcularEconomiaAcumulada();
        return Response.ok().entity(economia).build();
    }

    @GET
    @Path("/{id}/reducao-emissoes")
    public Response estimarReducaoEmissoes(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Simulacao simulacao = simulacaoDao.pesquisarPorId(id);
        double reducaoEmissoes = simulacao.estimarReducaoEmissoes();
        return Response.ok().entity(reducaoEmissoes).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoSimulacaoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Simulacao simulacao = modelMapper.map(dto, Simulacao.class);

        simulacao.setId(id);
        simulacaoDao.atualizar(simulacao);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid AtualizacaoParcialSimulacaoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Simulacao simulacaoBanco = simulacaoDao.pesquisarPorId(id);
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioDao.pesquisarPorId(dto.getIdUsuario());
            simulacaoBanco.setUsuario(usuario);
        }
        if (dto.getPercentEnergiaSolar() != null)
            simulacaoBanco.setPercentEnergiaSolar(dto.getPercentEnergiaSolar());
        if (dto.getCustoInicial() != null)
            simulacaoBanco.setCustoInicial(dto.getCustoInicial());
        if (dto.getDuracaoAnos() != null)
            simulacaoBanco.setDuracaoAnos(dto.getDuracaoAnos());
        if (dto.getDataSimulacao() != null)
            simulacaoBanco.setDataSimulacao(dto.getDataSimulacao());
        simulacaoDao.atualizar(simulacaoBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        simulacaoDao.excluir(id);
        return Response.noContent().build();
    }
}
