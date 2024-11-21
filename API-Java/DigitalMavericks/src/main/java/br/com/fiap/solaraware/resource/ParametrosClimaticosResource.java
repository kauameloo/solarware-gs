package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.ParametrosClimaticosDao;
import br.com.fiap.solaraware.dto.parametrosClimaticos.AtualizacaoParametrosClimaticosDto;
import br.com.fiap.solaraware.dto.parametrosClimaticos.CadastroParametrosClimaticosDto;
import br.com.fiap.solaraware.dto.parametrosClimaticos.DetalhesParametrosClimaticosDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.ParametrosClimaticos;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/parametros-climaticos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParametrosClimaticosResource {
    private ParametrosClimaticosDao parametrosClimaticosDao;
    private ModelMapper modelMapper;

    public ParametrosClimaticosResource() throws Exception{
        parametrosClimaticosDao = new ParametrosClimaticosDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroParametrosClimaticosDto dto , @Context UriInfo uriInfo) throws SQLException {
        ParametrosClimaticos parametrosClimaticos = modelMapper.map(dto, ParametrosClimaticos.class);

        parametrosClimaticosDao.cadastrar(parametrosClimaticos);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(parametrosClimaticos.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(parametrosClimaticos, DetalhesParametrosClimaticosDto.class)).build();
    }

    @GET
    public List<DetalhesParametrosClimaticosDto> listar() throws SQLException{
        return parametrosClimaticosDao.listar().stream()
                .map(pc -> modelMapper.map(pc, DetalhesParametrosClimaticosDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesParametrosClimaticosDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        ParametrosClimaticos parametrosClimaticos = parametrosClimaticosDao.pesquisarPorId(id);
        DetalhesParametrosClimaticosDto dto = modelMapper.map(parametrosClimaticos, DetalhesParametrosClimaticosDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoParametrosClimaticosDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        ParametrosClimaticos parametrosClimaticos = modelMapper.map(dto, ParametrosClimaticos.class);

        parametrosClimaticos.setId(id);
        parametrosClimaticosDao.atualizar(parametrosClimaticos);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid DetalhesParametrosClimaticosDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        ParametrosClimaticos parametrosClimaticosBanco = parametrosClimaticosDao.pesquisarPorId(id);
        if (dto.getLocalizacao() != null)
            parametrosClimaticosBanco.setLocalizacao(dto.getLocalizacao());
        if (dto.getInsolacaoMedia() != null)
            parametrosClimaticosBanco.setInsolacaoMedia(dto.getInsolacaoMedia());
        if (dto.getTemperaturaMedia() != null)
            parametrosClimaticosBanco.setTemperaturaMedia(dto.getTemperaturaMedia());
        if (dto.getDataRegistro() != null)
            parametrosClimaticosBanco.setDataRegistro(dto.getDataRegistro());
        parametrosClimaticosDao.atualizar(parametrosClimaticosBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        parametrosClimaticosDao.excluir(id);
        return Response.noContent().build();
    }
}
